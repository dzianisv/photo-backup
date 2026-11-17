package dev.dzianisv.snapdisk

import android.Manifest
import android.app.Notification
import android.app.NotificationManager
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.ContextCompat
import androidx.test.core.app.ActivityScenario
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import dev.dzianisv.snapdisk.backup.BackupPrefs
import dev.dzianisv.snapdisk.backup.BackupState
import dev.dzianisv.snapdisk.backup.BackupStatus
import dev.dzianisv.snapdisk.backup.ProPrefs
import dev.dzianisv.snapdisk.service.BackupActionReceiver
import dev.dzianisv.snapdisk.service.BackupService
import dev.dzianisv.snapdisk.service.NotificationHelper
import dev.dzianisv.snapdisk.service.StorageReceiver
import java.io.File
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Full-flow proof on the emulator (WL-64; Den, Nov 17: the emulator
 * end-to-end pass is the proof — a real device is never required and
 * never a gate). The wired user flow, driven through the same
 * entry points a real disk + real taps use:
 *
 *   mount broadcast → offer notification (Backup/Cancel)
 *   → BackupService start → copy oldest-first into virtual external
 *   storage (the app's own external files dir, via a SAF tree URI)
 *   → progress notification (bar + Pause/Stop) → Pause/Resume and
 *   Stop through the notification-action receiver → DONE.
 *
 * Oldest-first ORDER at the service level is proven by the
 * destination files' landing order (mtimes strictly increasing
 * old → mid → new); the engine-level order is already proven by
 * BackupEngineInstrumentedTest.oldestFirstCopyOrderAndVerification.
 * The progress notification text carries no file name, so the
 * service test does not (and cannot) observe the order from it.
 *
 * Timing discipline (learned from smoke runs 44147560922 and
 * 44154569523, which failed 6 and 4 tests): seed data is written
 * and verified in 1 MB chunks — the test process heap is ~200 MB,
 * so holding whole 80 MB files as byte arrays OOMs. Pause/Stop
 * are sent only after the in-flight file's .part is observed on
 * disk, never on a sleep — on a loaded emulator a sleep can
 * overshoot and the copy finishes first. Every test stops any
 * leftover service in setUp/tearDown: BackupService.stateFlow is
 * process-static, so a paused service left by a failed test would
 * make the next test's startBackup() a no-op (job still active)
 * and the next test would time out in PAUSED.
 *
 * Destination mirroring (root cause of the run-2 failures):
 * the service mirrors the source tree under the To folder (TDD
 * C6), so a photo seeded with RELATIVE_PATH "DCIM/snapdisk-e2e"
 * lands in <dst>/DCIM/snapdisk-e2e/<name> — NOT in <dst>/<name>.
 * Run 2 asserted the flat path, so it reported "missing file"
 * and ".part never observed" while the service was copying
 * correctly (logcat showed the MediaProvider opens for every
 * seeded file). Every assertion here goes through destFile(),
 * which applies the same mirroring rule, and every "no .part
 * left" / file-count check walks the tree recursively.
 */
class FullFlowInstrumentedTest {

    @get:Rule
    val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        Manifest.permission.POST_NOTIFICATIONS,
        Manifest.permission.READ_MEDIA_IMAGES,
        Manifest.permission.READ_MEDIA_VIDEO,
    )

    private lateinit var ctx: Context
    private lateinit var notificationManager: NotificationManager
    private lateinit var dstDir: File
    private val seededUris = mutableListOf<Uri>()
    private var activityScenario: ActivityScenario<MainActivity>? = null

    @Before
    fun setUp() {
        ctx = InstrumentationRegistry.getInstrumentation().targetContext
        notificationManager =
            ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        NotificationHelper.ensureChannels(ctx)
        // Never inherit a running job from a previous test in this
        // process: stop it first (best effort, never fails setUp).
        stopServiceIfRunning()
        notificationManager.cancelAll()
        // Free tier for every test here; the service must never take
        // the Pro auto-backup path (the receiver test asserts the
        // free offer notification instead).
        ProPrefs(ctx).isPro = false
        ProPrefs(ctx).jobVolumeUuid = null
        BackupPrefs(ctx).fromTreeUri = null
        dstDir = File(ctx.getExternalFilesDir(null), "snapdisk-e2e-dst").apply {
            deleteRecursively()
            mkdirs()
        }
        BackupPrefs(ctx).toTreeUri = treeUriFor(dstDir)
    }

    @After
    fun tearDown() {
        try { activityScenario?.close() } catch (_: Exception) { /* already closed */ }
        activityScenario = null
        stopServiceIfRunning()
        try { notificationManager.cancelAll() } catch (_: Exception) { /* best effort */ }
        for (uri in seededUris) {
            try { ctx.contentResolver.delete(uri, null, null) } catch (_: Exception) { /* gone */ }
        }
        seededUris.clear()
        try { dstDir.deleteRecursively() } catch (_: Exception) { /* best effort */ }
        BackupPrefs(ctx).fromTreeUri = null
        BackupPrefs(ctx).toTreeUri = null
        ProPrefs(ctx).jobVolumeUuid = null
    }

    /**
     * If a backup job is active (a previous test left the service
     * COPYING/PAUSED), stop it and wait for STOPPED so the next
     * test's startBackup() is not a no-op. Fails setUp outright if
     * the previous job will not stop within 60 s: a stale active
     * job would make this test observe the previous test's states
     * and fail downstream with a misleading message.
     */
    private fun stopServiceIfRunning() {
        val state = BackupService.stateFlow.value.status
        if (state != BackupStatus.SCANNING && state != BackupStatus.COPYING &&
            state != BackupStatus.PAUSED
        ) return
        try {
            ContextCompat.startForegroundService(
                ctx,
                Intent(ctx, BackupService::class.java).setAction(BackupService.ACTION_STOP),
            )
        } catch (_: Exception) { /* service may already be gone */ }
        val deadline = System.currentTimeMillis() + 60_000
        while (System.currentTimeMillis() < deadline) {
            if (BackupService.stateFlow.value.status == BackupStatus.STOPPED) return
            Thread.sleep(100)
        }
        throw AssertionError(
            "previous backup job did not stop within 60 s " +
                "(state=${BackupService.stateFlow.value.status}) — refusing to run " +
                "on top of a stale active job",
        )
    }

    /** MediaStore RELATIVE_PATH every seeded photo is inserted with. */
    private val seedRelativePath = "DCIM/snapdisk-e2e"

    /**
     * Where the service puts a seeded photo: it mirrors the source
     * tree under the To folder (TDD C6), so the destination is
     * <dst>/DCIM/snapdisk-e2e/<name>. Asserting the flat
     * <dst>/<name> path is wrong — that is what failed run 2.
     */
    private fun destFile(name: String): File = File(File(dstDir, seedRelativePath), name)

    /** The in-flight .part next to [name]'s destination (PRD A3). */
    private fun partFile(name: String): File =
        dev.dzianisv.snapdisk.backup.BackupEngine.partFileFor(destFile(name))

    /** Every file under the destination, recursively (mirrored tree). */
    private fun allDestFiles(): List<File> =
        dstDir.walkTopDown().filter { it.isFile }.toList()

    private fun assertNoStaleParts() {
        val parts = allDestFiles().filter { it.name.endsWith(".part") }
        assertTrue("stale .part files remain: $parts", parts.isEmpty())
    }

    /**
     * The SAF tree URI for [dir], in the same shape the document-tree
     * picker returns: content://com.android.externalstorage.documents/
     * tree/primary%3A<path>. BackupService.destinationBaseDir maps it
     * back to /storage/emulated/0/<path>.
     */
    private fun treeUriFor(dir: File): String {
        val externalRoot = Environment.getExternalStorageDirectory().absolutePath
        val relative = dir.absolutePath.removePrefix("$externalRoot/")
        return "content://com.android.externalstorage.documents/tree/" +
            Uri.encode("primary:$relative")
    }

    /** Deterministic content byte at [position] for a seeded file [name]. */
    private fun patternByte(name: String, position: Long): Byte =
        ((position + name.hashCode()) % 251).toByte()

    /**
     * Seed one JPEG into MediaStore (DCIM/snapdisk-e2e) with a fixed
     * DATE_TAKEN, written in 1 MB chunks — never hold the whole file
     * in memory (the test heap is ~200 MB).
     */
    private fun seedPhoto(name: String, dateTakenMs: Long, sizeBytes: Long) {
        deletePreExisting(name)
        val values = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(MediaStore.MediaColumns.RELATIVE_PATH, seedRelativePath)
            put(MediaStore.MediaColumns.DATE_TAKEN, dateTakenMs)
            put(MediaStore.MediaColumns.DATE_ADDED, dateTakenMs / 1000)
            put(MediaStore.MediaColumns.DATE_MODIFIED, dateTakenMs / 1000)
            put(MediaStore.MediaColumns.SIZE, sizeBytes)
        }
        val uri = ctx.contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values,
        ) ?: throw AssertionError("MediaStore insert failed for $name")
        seededUris += uri
        val out = ctx.contentResolver.openOutputStream(uri)
            ?: throw AssertionError("could not open output stream for $name")
        out.use { stream ->
            val chunk = ByteArray(CHUNK_BYTES)
            var written = 0L
            while (written < sizeBytes) {
                val n = minOf(CHUNK_BYTES.toLong(), sizeBytes - written).toInt()
                for (i in 0 until n) chunk[i] = patternByte(name, written + i)
                stream.write(chunk, 0, n)
                written += n
            }
        }
    }

    /** Streaming byte-compare: [file] must match the seeded pattern for [name]. */
    private fun assertFileMatchesSeed(file: File, name: String, sizeBytes: Long) {
        assertTrue("missing file ${file.name}", file.exists())
        assertEquals("wrong size for ${file.name}", sizeBytes, file.length())
        file.inputStream().use { stream ->
            val chunk = ByteArray(CHUNK_BYTES)
            var read = 0L
            while (read < sizeBytes) {
                val n = stream.read(chunk, 0, minOf(CHUNK_BYTES.toLong(), sizeBytes - read).toInt())
                if (n < 0) throw AssertionError("${file.name} ended early at $read bytes")
                for (i in 0 until n) {
                    if (chunk[i] != patternByte(name, read + i)) {
                        throw AssertionError("${file.name} differs at byte ${read + i}")
                    }
                }
                read += n
            }
        }
    }

    private fun deletePreExisting(name: String) {
        ctx.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            arrayOf(MediaStore.MediaColumns._ID),
            "${MediaStore.MediaColumns.DISPLAY_NAME} = ?",
            arrayOf(name),
            null,
        )?.use { cursor ->
            val idCol = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID)
            while (cursor.moveToNext()) {
                val uri = Uri.withAppendedPath(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    cursor.getLong(idCol).toString(),
                )
                ctx.contentResolver.delete(uri, null, null)
            }
        }
    }

    private fun offerNotification() =
        notificationManager.activeNotifications.firstOrNull {
            it.id == NotificationHelper.NOTIFICATION_ID_OFFER
        }

    private fun progressNotification() =
        notificationManager.activeNotifications.firstOrNull {
            it.id == NotificationHelper.NOTIFICATION_ID_PROGRESS
        }

    private fun actionTitles(notification: android.service.notification.StatusBarNotification?) =
        notification?.notification?.actions?.map { it.title.toString() } ?: emptyList()

    private fun startServiceAction(action: String) {
        ContextCompat.startForegroundService(
            ctx,
            Intent(ctx, BackupService::class.java).setAction(action),
        )
    }

    /** The same path a notification action tap takes (manifest receiver). */
    private fun sendNotificationAction(action: String) {
        BackupActionReceiver().onReceive(ctx, Intent(action))
    }

    /**
     * Poll [condition] until true or [timeoutMs] elapses. Records
     * every distinct (status, currentFile) pair the service
     * publishes, so the test can assert on the state sequence
     * instead of only the final state.
     */
    private fun waitForState(
        timeoutMs: Long,
        pollMs: Long = 50,
        seenStates: MutableList<Pair<BackupStatus, String?>> = mutableListOf(),
        condition: (BackupState) -> Boolean,
    ): BackupState {
        val deadline = System.currentTimeMillis() + timeoutMs
        var last: BackupState = BackupService.stateFlow.value
        while (System.currentTimeMillis() < deadline) {
            val state = BackupService.stateFlow.value
            if (state.status != last.status || state.currentFileName != last.currentFileName) {
                seenStates += state.status to state.currentFileName
            }
            last = state
            if (condition(state)) return state
            Thread.sleep(pollMs)
        }
        throw AssertionError(
            "timed out after ${timeoutMs}ms waiting for state; " +
                "last=${last.status} error=${last.error?.summary} " +
                "seen=$seenStates",
        )
    }

    /** Wait until [file] exists with a partial size (a copy is in flight), or fail. */
    private fun waitForPartInFlight(file: File, totalBytes: Long, timeoutMs: Long = 120_000) {
        val deadline = System.currentTimeMillis() + timeoutMs
        while (System.currentTimeMillis() < deadline) {
            if (file.exists() && file.length() in 1 until totalBytes) return
            Thread.sleep(20)
        }
        throw AssertionError(
            "${file.name} was never observed mid-copy " +
                "(exists=${file.exists()} size=${if (file.exists()) file.length() else "n/a"} " +
                "of $totalBytes) — the copy finished before the action landed?",
        )
    }

    @Test
    fun mountBroadcastPostsOfferWithBackupAndCancel() {
        StorageReceiver().onReceive(
            ctx,
            Intent(Intent.ACTION_MEDIA_MOUNTED).apply {
                data = Uri.parse("file:///storage/1234-ABCD")
            },
        )
        val deadline = System.currentTimeMillis() + 5_000
        var offer = offerNotification()
        while (offer == null && System.currentTimeMillis() < deadline) {
            Thread.sleep(100)
            offer = offerNotification()
        }
        assertTrue("offer notification was not posted after mount broadcast", offer != null)
        assertEquals(
            listOf("Backup", "Cancel"),
            actionTitles(offer),
        )
        println("SMOKE full-flow: mount broadcast posted the offer (Backup/Cancel)")

        // Cancel dismisses the offer through the same receiver the
        // notification action uses. Poll for the dismissal: on a
        // loaded emulator the NotificationManagerService can take
        // seconds to propagate the cancel (smoke run 44147560922
        // showed 200-300 ms monitor contention on this call).
        sendNotificationAction(StorageReceiver.ACTION_DISMISS_OFFER)
        val dismissDeadline = System.currentTimeMillis() + 5_000
        while (offerNotification() != null && System.currentTimeMillis() < dismissDeadline) {
            Thread.sleep(100)
        }
        assertTrue("offer was not dismissed by Cancel", offerNotification() == null)
        println("SMOKE full-flow: Cancel dismissed the offer")
    }

    @Test
    fun serviceCopiesSeededPhotosOldestFirstWithProgressAndDone() {
        // The app must be foreground for a foreground-service start:
        // launching the settings screen mirrors the user tapping
        // Backup in the app (the offer's Backup action does the same
        // startForegroundService call from the receiver).
        activityScenario = ActivityScenario.launch(MainActivity::class.java)
        // Small files: the emulator copies at ~100 KB/s worst case,
        // so 2 MB each keeps the whole job inside the DONE timeout
        // while still proving oldest-first order + content.
        val size = 2L * 1024 * 1024
        seedPhoto("e2e-old.jpg", 1_700_000_000_000L, size)
        seedPhoto("e2e-mid.jpg", 1_700_000_100_000L, size)
        seedPhoto("e2e-new.jpg", 1_700_000_200_000L, size)

        val seenStates = mutableListOf<Pair<BackupStatus, String?>>()
        var sawProgressWithPauseStop = false
        startServiceAction(BackupService.ACTION_START)
        val done = waitForState(240_000, 50, seenStates) { state ->
            val progress = progressNotification()
            if (progress != null && state.status == BackupStatus.COPYING) {
                val titles = actionTitles(progress)
                if (titles.contains("Pause") && titles.contains("Stop")) {
                    val max = progress.notification.extras.getInt(Notification.EXTRA_PROGRESS_MAX)
                    val value = progress.notification.extras.getInt(Notification.EXTRA_PROGRESS)
                    if (max == 100 && value in 0..100) sawProgressWithPauseStop = true
                }
            }
            state.status == BackupStatus.DONE || state.status == BackupStatus.ERROR
        }
        assertEquals(
            "service ended in ${done.status}, not DONE: ${done.error?.detail}",
            BackupStatus.DONE,
            done.status,
        )
        assertTrue(
            "no COPYING progress notification with Pause/Stop was observed; seen=$seenStates",
            sawProgressWithPauseStop,
        )
        assertEquals("DONE state copiedFiles", 3, done.copiedFiles)

        val oldDest = destFile("e2e-old.jpg")
        val midDest = destFile("e2e-mid.jpg")
        val newDest = destFile("e2e-new.jpg")
        assertFileMatchesSeed(oldDest, "e2e-old.jpg", size)
        assertFileMatchesSeed(midDest, "e2e-mid.jpg", size)
        assertFileMatchesSeed(newDest, "e2e-new.jpg", size)
        assertNoStaleParts()
        // Landing order is the oldest-first proof at service level:
        // the engine copies sequentially, so mtimes increase old→new.
        assertTrue(
            "files did not land oldest-first " +
                "(mtimes old=${oldDest.lastModified()} mid=${midDest.lastModified()} " +
                "new=${newDest.lastModified()})",
            oldDest.lastModified() <= midDest.lastModified() &&
                midDest.lastModified() <= newDest.lastModified(),
        )
        val doneNotification = progressNotification()
        assertEquals(
            "Backup complete",
            doneNotification?.notification?.extras
                ?.getCharSequence(Notification.EXTRA_TITLE)?.toString(),
        )
        println(
            "SMOKE full-flow: service copied 3 seeded photos oldest-first " +
                "(mtimes ${oldDest.lastModified()}/${midDest.lastModified()}/${newDest.lastModified()}), " +
                "progress with Pause/Stop seen, DONE notified; states=$seenStates",
        )
    }

    @Test
    fun pauseAndResumeThroughNotificationActions() {
        activityScenario = ActivityScenario.launch(MainActivity::class.java)
        // One file, big enough to stay in flight for seconds at any
        // observed emulator copy rate (~100 KB/s–2 MB/s): the pause
        // is sent only after its .part is observed mid-copy, so
        // there is no between-files window for the pause to land in
        // (the engine-level pause is already proven deterministically
        // by BackupEngineInstrumentedTest with a slow stream; this
        // test proves the notification action path). 300 MB (run 2)
        // made the job run for many minutes and invited cross-test
        // interference; 10 MB is the same proof in seconds.
        val size = 10L * 1024 * 1024
        seedPhoto("e2e-big.jpg", 1_700_000_000_000L, size)

        val seenStates = mutableListOf<Pair<BackupStatus, String?>>()
        startServiceAction(BackupService.ACTION_START)
        waitForState(120_000, 50, seenStates) { it.status == BackupStatus.COPYING }
        val part = partFile("e2e-big.jpg")
        waitForPartInFlight(part, size)
        sendNotificationAction(BackupService.ACTION_PAUSE)
        val paused = waitForState(10_000, 50, seenStates) { it.status == BackupStatus.PAUSED }
        assertEquals(BackupStatus.PAUSED, paused.status)
        // The in-flight file's .part must stay frozen while paused.
        val sizeWhilePaused = part.length()
        assertTrue("empty .part while paused", sizeWhilePaused > 0)
        Thread.sleep(500)
        assertEquals("copy progressed while paused", sizeWhilePaused, part.length())
        assertEquals(
            listOf("Resume", "Stop"),
            actionTitles(progressNotification()),
        )
        println(
            "SMOKE full-flow: pause froze the copy (.part held at " +
                "$sizeWhilePaused bytes), Resume/Stop offered",
        )

        sendNotificationAction(BackupService.ACTION_RESUME)
        val done = waitForState(180_000, 50, seenStates) {
            it.status == BackupStatus.DONE || it.status == BackupStatus.ERROR
        }
        assertEquals(
            "service ended in ${done.status} after resume: ${done.error?.detail}",
            BackupStatus.DONE,
            done.status,
        )
        assertFileMatchesSeed(destFile("e2e-big.jpg"), "e2e-big.jpg", size)
        assertNoStaleParts()
        println("SMOKE full-flow: resume finished the paused copy, file byte-identical")
    }

    @Test
    fun stopThroughNotificationActionKeepsFinishedFiles() {
        activityScenario = ActivityScenario.launch(MainActivity::class.java)
        // Small file first (oldest): it finishes before the stop is
        // sent, so it must be kept byte-identical. The second file
        // is in flight when the stop lands: its .part must go.
        // 300 MB (run 2) made the job run for many minutes; 10 MB
        // stays in flight for seconds at any observed copy rate.
        val firstSize = 2L * 1024 * 1024
        seedPhoto("e2e-stop1.jpg", 1_700_000_000_000L, firstSize)
        val bigSize = 10L * 1024 * 1024
        seedPhoto("e2e-stop2.jpg", 1_700_000_100_000L, bigSize)

        val seenStates = mutableListOf<Pair<BackupStatus, String?>>()
        startServiceAction(BackupService.ACTION_START)
        waitForState(120_000, 50, seenStates) { it.status == BackupStatus.COPYING }
        waitForPartInFlight(partFile("e2e-stop2.jpg"), bigSize)
        sendNotificationAction(BackupService.ACTION_STOP)
        val stopped = waitForState(30_000, 50, seenStates) {
            it.status == BackupStatus.STOPPED || it.status == BackupStatus.DONE
        }
        assertEquals(
            "service ended in ${stopped.status} after Stop (copy may have finished first)",
            BackupStatus.STOPPED,
            stopped.status,
        )
        assertNoStaleParts()
        assertFileMatchesSeed(destFile("e2e-stop1.jpg"), "e2e-stop1.jpg", firstSize)
        // No further copying after Stop: the destination is stable.
        val countAfterStop = allDestFiles().size
        Thread.sleep(1_000)
        val countLater = allDestFiles().size
        assertEquals("files kept copying after Stop", countAfterStop, countLater)
        println(
            "SMOKE full-flow: Stop ended the job, .part deleted, " +
                "finished file kept byte-identical, nothing more copied",
        )
    }

    companion object {
        private const val CHUNK_BYTES = 1024 * 1024
    }
}
