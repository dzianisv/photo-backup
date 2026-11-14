package dev.dzianisv.snapdisk

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import dev.dzianisv.snapdisk.backup.BackupEngine
import dev.dzianisv.snapdisk.backup.BackupError
import dev.dzianisv.snapdisk.backup.CopyControl
import dev.dzianisv.snapdisk.backup.CopyResult
import dev.dzianisv.snapdisk.backup.PhotoItem
import dev.dzianisv.snapdisk.service.BackupService
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicReference
import kotlin.concurrent.thread
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test

/**
 * Instrumented end-to-end tests for the backup engine on a real
 * Android device: oldest-first order, pause/resume, stop, copy
 * verification, and disk-detached handling (pause with full error,
 * resumable). Runs under `connectedDebugAndroidTest` on the emulator.
 */
class BackupEngineInstrumentedTest {

    private lateinit var ctx: Context
    private lateinit var parentDir: File
    private lateinit var srcDir: File
    private lateinit var dstDir: File

    @Before
    fun setUp() {
        ctx = InstrumentationRegistry.getInstrumentation().targetContext
        parentDir = File(ctx.cacheDir, "e2e").apply { mkdirs(); setWritable(true) }
        srcDir = File(parentDir, "src").apply { mkdirs() }
        dstDir = File(parentDir, "dst").apply { mkdirs() }
        srcDir.listFiles()?.forEach { it.delete() }
        dstDir.listFiles()?.forEach { it.delete() }
    }

    private fun photoFile(name: String, size: Int, taken: Long): PhotoItem {
        val file = File(srcDir, name)
        file.writeBytes(ByteArray(size) { (it % 251).toByte() })
        file.setLastModified(taken)
        return PhotoItem(
            id = name.hashCode().toLong(),
            displayName = name,
            dateTakenMillis = taken,
            dateAddedSeconds = taken / 1000,
            sizeBytes = size.toLong(),
            contentUri = file.absolutePath,
        )
    }

    private fun copyAll(items: List<PhotoItem>, control: CopyControl = CopyControl()): List<String> {
        val copied = mutableListOf<String>()
        for (item in BackupEngine.sortOldestFirst(items)) {
            val dest = File(dstDir, item.displayName)
            val result = FileInputStream(File(srcDir, item.displayName)).use { input ->
                BackupEngine.copyWithResume(
                    input = input,
                    destination = dest,
                    expectedSizeBytes = item.sizeBytes,
                    control = control,
                )
            }
            when (result) {
                is CopyResult.Copied, is CopyResult.Skipped -> copied += item.displayName
                is CopyResult.Failed -> fail("copy failed for ${item.displayName}: ${result.error.detail}")
                is CopyResult.Paused -> fail("unexpected pause for ${item.displayName}")
                is CopyResult.Stopped -> fail("unexpected stop for ${item.displayName}")
            }
        }
        return copied
    }

    @Test
    fun oldestFirstCopyOrderAndVerification() {
        val items = listOf(
            photoFile("new.jpg", 100, taken = 3_000),
            photoFile("old.jpg", 100, taken = 1_000),
            photoFile("mid.jpg", 100, taken = 2_000),
        )
        val copied = copyAll(items)
        assertEquals(listOf("old.jpg", "mid.jpg", "new.jpg"), copied)
        for (item in items) {
            val dest = File(dstDir, item.displayName)
            assertTrue("dest missing for ${item.displayName}", dest.exists())
            assertTrue(BackupEngine.verify(dest, item.sizeBytes))
            assertTrue(dest.readBytes().contentEquals(File(srcDir, item.displayName).readBytes()))
            assertTrue("stale .part for ${item.displayName}", !BackupEngine.partFileFor(dest).exists())
        }
        println("SMOKE e2e: oldest-first copy + verify passed (3 files)")
    }

    @Test
    fun pauseResumeAndStop() {
        val data = ByteArray(500_000) { (it % 251).toByte() }
        val src = File(srcDir, "big.jpg").apply { writeBytes(data) }
        val dest = File(dstDir, "big.jpg")
        val part = BackupEngine.partFileFor(dest)
        val control = CopyControl()
        val resultRef = AtomicReference<CopyResult?>()
        val done = CountDownLatch(1)
        thread {
            resultRef.set(
                BackupEngine.copyWithResume(
                    input = SlowInputStream(FileInputStream(src)),
                    destination = dest,
                    expectedSizeBytes = data.size.toLong(),
                    control = control,
                ),
            )
            done.countDown()
        }
        Thread.sleep(300)
        control.paused = true
        // Let a write already in flight when pause was set land (one
        // write syscall, < 1 ms): no engine can un-write it, so the
        // snapshot below is taken after it, not mid-flight. The engine
        // fix guarantees nothing further is written while paused — the
        // 500 ms freeze assertion below is the real check. Same settle
        // the unit test got in 3cc9518.
        Thread.sleep(200)
        assertTrue("no .part file while copying", part.exists())
        val sizeWhilePaused = part.length()
        assertTrue(sizeWhilePaused > 0)
        Thread.sleep(500)
        assertEquals("copy progressed while paused", sizeWhilePaused, part.length())
        assertTrue("finished file must not exist while paused", !dest.exists())
        control.paused = false
        assertTrue(done.await(30, TimeUnit.SECONDS))
        assertTrue(resultRef.get() is CopyResult.Copied)
        assertTrue(BackupEngine.verify(dest, data.size.toLong()))
        assertTrue(!part.exists())
        println("SMOKE e2e: pause held the copy (.part frozen), resume finished it")

        // Stop: a fresh copy aborted mid-way keeps its .part file.
        val dest2 = File(dstDir, "big2.jpg")
        val part2 = BackupEngine.partFileFor(dest2)
        val control2 = CopyControl()
        val result2 = AtomicReference<CopyResult?>()
        val done2 = CountDownLatch(1)
        thread {
            result2.set(
                BackupEngine.copyWithResume(
                    input = SlowInputStream(FileInputStream(src)),
                    destination = dest2,
                    expectedSizeBytes = data.size.toLong(),
                    control = control2,
                ),
            )
            done2.countDown()
        }
        Thread.sleep(300)
        control2.stopped = true
        assertTrue(done2.await(30, TimeUnit.SECONDS))
        assertTrue(result2.get() is CopyResult.Stopped)
        val partial = (result2.get() as CopyResult.Stopped).bytesCopied
        assertTrue(partial in 1 until data.size.toLong())
        assertTrue(part2.exists())
        assertEquals(partial, part2.length())
        println("SMOKE e2e: stop aborted the copy, .part kept ($partial bytes)")
    }

    @Test
    fun diskDetachedFailsCopyWithFullErrorAndResumes() {
        // Simulate the disk going away mid-copy: the destination
        // directory is deleted AND its parent is made read-only, so
        // the engine cannot recreate it — the write fails instead of
        // silently succeeding into an unlinked file.
        val data = ByteArray(500_000) { (it % 251).toByte() }
        val src = File(srcDir, "disk.jpg").apply { writeBytes(data) }
        val dest = File(dstDir, "disk.jpg")
        val control = CopyControl()
        val resultRef = AtomicReference<CopyResult?>()
        val done = CountDownLatch(1)
        thread {
            resultRef.set(
                BackupEngine.copyWithResume(
                    input = SlowInputStream(FileInputStream(src)),
                    destination = dest,
                    expectedSizeBytes = data.size.toLong(),
                    control = control,
                ),
            )
            done.countDown()
        }
        Thread.sleep(300)
        dstDir.deleteRecursively()
        parentDir.setWritable(false)
        try {
            assertTrue(done.await(30, TimeUnit.SECONDS))
        } finally {
            parentDir.setWritable(true)
        }
        val result = resultRef.get()
        assertTrue("expected Failed when the disk vanished, got $result", result is CopyResult.Failed)
        val error = (result as CopyResult.Failed).error
        assertTrue(error.detail.isNotBlank())
        assertTrue(error.detail.length > error.summary.length)
        println("SMOKE e2e: disk-detach failed the copy with a full error: ${error.summary}")

        // Disk comes back: recreate it, resume the same file to completion.
        dstDir.mkdirs()
        val resumed = BackupEngine.copyWithResume(
            input = FileInputStream(src),
            destination = File(dstDir, "disk.jpg"),
            expectedSizeBytes = data.size.toLong(),
            control = CopyControl(),
        )
        assertTrue(resumed is CopyResult.Copied)
        assertTrue(BackupEngine.verify(File(dstDir, "disk.jpg"), data.size.toLong()))
        println("SMOKE e2e: after the disk came back, the copy resumed to completion")
    }

    @Test
    fun detachErrorClassifier() {
        val service = BackupService()
        val detached = BackupError.fromThrowable(
            "Copy failed",
            java.io.IOException("No such device"),
            operation = "copy",
        )
        assertTrue(service.isDetachError(detached))
        val ordinary = BackupError.fromThrowable(
            "Copy failed",
            IllegalArgumentException("bad argument"),
            operation = "copy",
        )
        assertTrue(!service.isDetachError(ordinary))
    }

    /** Drips the wrapped stream slowly so pause/stop can land mid-copy. */
    private class SlowInputStream(
        private val wrapped: InputStream,
        private val chunk: Int = 4096,
        private val delayMs: Long = 5,
    ) : InputStream() {
        override fun read(): Int {
            val b = ByteArray(1)
            return if (read(b, 0, 1) < 0) -1 else b[0].toInt() and 0xFF
        }

        override fun read(b: ByteArray, off: Int, len: Int): Int {
            val n = wrapped.read(b, off, minOf(len, chunk))
            if (n > 0) Thread.sleep(delayMs)
            return n
        }

        override fun close() = wrapped.close()
    }
}
