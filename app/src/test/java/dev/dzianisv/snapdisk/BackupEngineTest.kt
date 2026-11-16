package dev.dzianisv.snapdisk

import dev.dzianisv.snapdisk.backup.BackupEngine
import dev.dzianisv.snapdisk.backup.CopyControl
import dev.dzianisv.snapdisk.backup.CopyResult
import dev.dzianisv.snapdisk.backup.PhotoItem
import java.io.ByteArrayInputStream
import java.io.File
import java.io.InputStream
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicReference
import kotlin.concurrent.thread
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

/**
 * Unit tests for the copy engine: oldest-first order, skip-if-exists,
 * resume of a partial `.part` file, pause/resume, stop, and size
 * verification (TDD C2, O1–O3, R1–R2, S1).
 */
class BackupEngineTest {

    @get:Rule
    val tmp = TemporaryFolder()

    private fun photo(
        name: String,
        taken: Long,
        added: Long = 0,
        modified: Long = 0,
        size: Long = 10,
    ): PhotoItem =
        PhotoItem(
            id = name.hashCode().toLong(),
            displayName = name,
            dateTakenMillis = taken,
            dateAddedSeconds = added,
            sizeBytes = size,
            contentUri = "content://test/$name",
            dateModifiedMillis = modified,
        )

    @Test
    fun oldestFirstOrdersByDateTakenAscending() {
        val newest = photo("new.jpg", taken = 3_000)
        val oldest = photo("old.jpg", taken = 1_000)
        val middle = photo("mid.jpg", taken = 2_000)
        val sorted = BackupEngine.sortOldestFirst(listOf(newest, oldest, middle))
        assertEquals(listOf("old.jpg", "mid.jpg", "new.jpg"), sorted.map { it.displayName })
    }

    @Test
    fun oldestFirstFallsBackToDateModifiedWhenTakenIsZero() {
        val a = photo("a.jpg", taken = 0, modified = 200_000)
        val b = photo("b.jpg", taken = 0, modified = 100_000)
        val sorted = BackupEngine.sortOldestFirst(listOf(a, b))
        assertEquals(listOf("b.jpg", "a.jpg"), sorted.map { it.displayName })
    }

    @Test
    fun oldestFirstTieBreaksById() {
        val a = photo("b.jpg", taken = 1_000)
        val b = photo("a.jpg", taken = 1_000)
        val sorted = BackupEngine.sortOldestFirst(listOf(a, b))
        // Same date → id order (MediaStore _ID), deterministic on re-run.
        val byId = listOf(a, b).sortedBy { it.id }.map { it.displayName }
        assertEquals(byId, sorted.map { it.displayName })
    }

    @Test
    fun skipWhenDestinationAlreadyHasSameSize() {
        val dest = File(tmp.root, "photo.jpg")
        dest.writeBytes(ByteArray(100))
        assertTrue(BackupEngine.shouldSkip(dest, 100))
        assertFalse(BackupEngine.shouldSkip(dest, 99))
        assertFalse(BackupEngine.shouldSkip(File(tmp.root, "missing.jpg"), 100))
    }

    @Test
    fun verifyChecksSizeAfterCopy() {
        val dest = File(tmp.root, "photo.jpg")
        dest.writeBytes(ByteArray(50))
        assertTrue(BackupEngine.verify(dest, 50))
        assertFalse(BackupEngine.verify(dest, 51))
        assertFalse(BackupEngine.verify(File(tmp.root, "missing.jpg"), 50))
    }

    @Test
    fun copyWritesFullFileAndVerifies() {
        val data = ByteArray(10_000) { (it % 251).toByte() }
        val dest = File(tmp.root, "photo.jpg")
        val result = BackupEngine.copyWithResume(
            input = ByteArrayInputStream(data),
            destination = dest,
            expectedSizeBytes = data.size.toLong(),
            control = CopyControl(),
        )
        assertTrue(result is CopyResult.Copied)
        assertEquals(data.size.toLong(), (result as CopyResult.Copied).bytes)
        assertTrue(dest.readBytes().contentEquals(data))
        assertTrue(BackupEngine.verify(dest, data.size.toLong()))
        // The .part file is renamed away — never left behind on success.
        assertFalse(BackupEngine.partFileFor(dest).exists())
    }

    @Test
    fun copySkipsWhenAlreadyThere() {
        val data = ByteArray(100)
        val dest = File(tmp.root, "photo.jpg")
        dest.writeBytes(data)
        val result = BackupEngine.copyWithResume(
            input = ByteArrayInputStream(data),
            destination = dest,
            expectedSizeBytes = 100,
            control = CopyControl(),
        )
        assertTrue(result is CopyResult.Skipped)
    }

    @Test
    fun copyResumesPartialPartFile() {
        val data = ByteArray(10_000) { (it % 251).toByte() }
        val dest = File(tmp.root, "photo.jpg")
        // Interrupted earlier copy: only the .part file exists.
        BackupEngine.partFileFor(dest).writeBytes(data.copyOfRange(0, 4_000))
        assertFalse(dest.exists())
        val result = BackupEngine.copyWithResume(
            input = ByteArrayInputStream(data),
            destination = dest,
            expectedSizeBytes = data.size.toLong(),
            control = CopyControl(),
        )
        assertTrue(result is CopyResult.Copied)
        assertTrue(dest.readBytes().contentEquals(data))
        assertTrue(BackupEngine.verify(dest, data.size.toLong()))
        assertFalse(BackupEngine.partFileFor(dest).exists())
    }

    @Test
    fun copyFinishesCompletePartFileWithoutRecopying() {
        val data = ByteArray(10_000) { (it % 251).toByte() }
        val dest = File(tmp.root, "photo.jpg")
        // Crash between write and rename: a complete .part, no dest.
        BackupEngine.partFileFor(dest).writeBytes(data)
        val result = BackupEngine.copyWithResume(
            input = ByteArrayInputStream(ByteArray(0)),
            destination = dest,
            expectedSizeBytes = data.size.toLong(),
            control = CopyControl(),
        )
        assertTrue(result is CopyResult.Copied)
        assertTrue(dest.readBytes().contentEquals(data))
    }

    @Test
    fun copyFailsVerificationOnSizeMismatch() {
        val data = ByteArray(100)
        val dest = File(tmp.root, "photo.jpg")
        val result = BackupEngine.copyWithResume(
            input = ByteArrayInputStream(data),
            destination = dest,
            expectedSizeBytes = 200, // source claims more than the stream holds
            control = CopyControl(),
        )
        assertTrue(result is CopyResult.Failed)
        val error = (result as CopyResult.Failed).error
        // Full error, never one truncated line: detail names the sizes.
        assertTrue(error.detail.contains("Expected size: 200"))
        assertTrue(error.detail.contains("Actual size: 100"))
        assertTrue(error.summary.isNotBlank())
        assertTrue(BackupEngine.isVerifyFailure(error))
        // A failed verify deletes the .part — no corrupt file left.
        assertFalse(BackupEngine.partFileFor(dest).exists())
        assertFalse(dest.exists())
    }

    /** An InputStream that drips one buffer per [delayMs]. */
    private class SlowInputStream(
        private val data: ByteArray,
        private val chunk: Int = 1024,
        private val delayMs: Long = 5,
    ) : InputStream() {
        private var pos = 0
        override fun read(): Int {
            val b = ByteArray(1)
            return if (read(b, 0, 1) < 0) -1 else b[0].toInt() and 0xFF
        }

        override fun read(b: ByteArray, off: Int, len: Int): Int {
            if (pos >= data.size) return -1
            val n = minOf(len, chunk, data.size - pos)
            System.arraycopy(data, pos, b, off, n)
            pos += n
            Thread.sleep(delayMs)
            return n
        }
    }

    @Test
    fun pauseHoldsCopyAndResumeFinishesIt() {
        val data = ByteArray(200_000) { (it % 251).toByte() }
        val dest = File(tmp.root, "photo.jpg")
        val part = BackupEngine.partFileFor(dest)
        val control = CopyControl()
        val resultRef = AtomicReference<CopyResult?>()
        val done = CountDownLatch(1)
        thread {
            resultRef.set(
                BackupEngine.copyWithResume(
                    input = SlowInputStream(data),
                    destination = dest,
                    expectedSizeBytes = data.size.toLong(),
                    control = control,
                ),
            )
            done.countDown()
        }
        Thread.sleep(300)
        control.paused = true
        // Let the chunk already in flight land (one 5 ms read + write),
        // so the snapshot below is the paused size, not a mid-flight one.
        Thread.sleep(200)
        // While paused the copy writes to the .part file; the finished
        // destination must not exist yet and the .part size is frozen.
        assertFalse(dest.exists())
        val sizeWhilePaused = part.length()
        assertTrue(sizeWhilePaused > 0)
        Thread.sleep(600)
        assertEquals(sizeWhilePaused, part.length())
        assertFalse(done.await(100, TimeUnit.MILLISECONDS))
        control.paused = false
        assertTrue(done.await(30, TimeUnit.SECONDS))
        assertTrue(resultRef.get() is CopyResult.Copied)
        assertTrue(dest.readBytes().contentEquals(data))
        assertFalse(part.exists())
    }

    @Test
    fun stopAbortsCopyAndKeepsPartForResume() {
        val data = ByteArray(500_000) { (it % 251).toByte() }
        val dest = File(tmp.root, "photo.jpg")
        val part = BackupEngine.partFileFor(dest)
        val control = CopyControl()
        val resultRef = AtomicReference<CopyResult?>()
        val done = CountDownLatch(1)
        thread {
            resultRef.set(
                BackupEngine.copyWithResume(
                    input = SlowInputStream(data),
                    destination = dest,
                    expectedSizeBytes = data.size.toLong(),
                    control = control,
                ),
            )
            done.countDown()
        }
        Thread.sleep(300)
        control.stopped = true
        assertTrue(done.await(30, TimeUnit.SECONDS))
        val result = resultRef.get()
        assertTrue(result is CopyResult.Stopped)
        val partial = (result as CopyResult.Stopped).bytesCopied
        assertTrue(partial in 1 until data.size.toLong())
        // The .part file stays: a fresh run resumes it to completion.
        assertTrue(part.exists())
        assertEquals(partial, part.length())
        val resumed = BackupEngine.copyWithResume(
            input = ByteArrayInputStream(data),
            destination = dest,
            expectedSizeBytes = data.size.toLong(),
            control = CopyControl(),
        )
        assertTrue(resumed is CopyResult.Copied)
        assertTrue(dest.readBytes().contentEquals(data))
    }

    @Test
    fun timeoutWhilePausedReturnsPausedAndKeepsPartForResume() {
        // WL-34: the Android 15 dataSync 6-hour timeout. The service sets
        // control.timedOut from onTimeout; the engine must return Paused
        // promptly (not block in the pause gate until resume) with the
        // .part file intact, so the same resume path as a detach-pause
        // finishes the file byte-exact.
        val data = ByteArray(200_000) { (it % 251).toByte() }
        val dest = File(tmp.root, "photo.jpg")
        val part = BackupEngine.partFileFor(dest)
        val control = CopyControl()
        val resultRef = AtomicReference<CopyResult?>()
        val done = CountDownLatch(1)
        thread {
            resultRef.set(
                BackupEngine.copyWithResume(
                    input = SlowInputStream(data),
                    destination = dest,
                    expectedSizeBytes = data.size.toLong(),
                    control = control,
                ),
            )
            done.countDown()
        }
        Thread.sleep(300)
        control.paused = true
        Thread.sleep(200)
        assertFalse(dest.exists())
        val sizeWhilePaused = part.length()
        assertTrue(sizeWhilePaused > 0)
        // The timeout fires while paused: the copy must unwind now,
        // not wait for a resume that may never come (the service stops).
        control.timedOut = true
        assertTrue(done.await(30, TimeUnit.SECONDS))
        val result = resultRef.get()
        assertTrue(result is CopyResult.Paused)
        val partial = (result as CopyResult.Paused).bytesCopied
        assertTrue(partial in 1 until data.size.toLong())
        assertEquals(partial, part.length())
        assertFalse(dest.exists())
        // Same resume path as a detach-pause: a fresh run finishes it.
        val resumed = BackupEngine.copyWithResume(
            input = ByteArrayInputStream(data),
            destination = dest,
            expectedSizeBytes = data.size.toLong(),
            control = CopyControl(),
        )
        assertTrue(resumed is CopyResult.Copied)
        assertTrue(dest.readBytes().contentEquals(data))
        assertFalse(part.exists())
    }

    @Test
    fun timeoutBeforeStartReturnsPausedWithoutWriting() {
        // Timeout already set when the copy starts (e.g. onTimeout fired
        // during the scan): nothing is written, no destination appears.
        val data = ByteArray(10_000) { (it % 251).toByte() }
        val dest = File(tmp.root, "photo.jpg")
        val control = CopyControl().apply {
            paused = true
            timedOut = true
        }
        val result = BackupEngine.copyWithResume(
            input = ByteArrayInputStream(data),
            destination = dest,
            expectedSizeBytes = data.size.toLong(),
            control = control,
        )
        assertTrue(result is CopyResult.Paused)
        assertEquals(0L, (result as CopyResult.Paused).bytesCopied)
        assertFalse(dest.exists())
        // No bytes were written: the .part file is absent or empty
        // (the stream is opened before the first pause gate, so an
        // empty .part may exist — it resumes as offset 0, not as data).
        val part = BackupEngine.partFileFor(dest)
        assertEquals(0L, if (part.exists()) part.length() else 0L)
    }
}
