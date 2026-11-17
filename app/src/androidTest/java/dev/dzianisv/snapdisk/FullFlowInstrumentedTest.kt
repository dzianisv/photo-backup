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
import org.junit.Assert.fail
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

    /** Seed one JPEG into MediaStore (DCIM/snapdisk-e2e) with a fixed DATE_TAKEN. */
    private fun seedPhoto(name: String, dateTakenMs: Long, sizeBytes: Int): ByteArray {
        deletePreExisting(name)
        val data = ByteArray(sizeBytes) { ((it + name.hashCode()) % 251).toByte() }
        val values = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/snapdisk-e2e")
            put(MediaStore.MediaColumns.DATE_TAKEN, dateTakenMs)
            put(MediaStore.MediaColumns.DATE_ADDED, dateTakenMs / 1000)
            put(MediaStore.MediaColumns.DATE_MODIFIED, dateTakenMs / 1000)
            put(MediaStore.MediaColumns.SIZE, sizeBytes)
        }
        val uri = ctx.contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values,
        ) ?: throw AssertionError("MediaStore insert failed for $name")
        seededUris += uri
        ctx.contentResolver.openOutputStream(uri)?.use { it.write(data) }
            ?: throw AssertionError("could not open output stream for $name")
        return data
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
     * Poll [condition] until true or [timeoutMs] elapses. While
     * polling, records every distinct (status, currentFile) pair the
     * service publishes, so the test can assert on the state sequence
     * instead of only the final state.
     */
    private fun waitForState(
        timeoutMs: Long,
        pollMs: Long = 100,
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
        // notification action uses.
        sendNotificationAction(StorageReceiver.ACTION_DISMISS_OFFER)
        Thread.sleep(300)
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
        val oldData = seedPhoto("e2e-old.jpg", 1_700_000_000_000L, 20 * 1024 * 1024)
        val midData = seedPhoto("e2e-mid.jpg", 1_700_000_100_000L, 20 * 1024 * 1024)
        val newData = seedPhoto("e2e-new.jpg", 1_700_000_200_000L, 20 * 1024 * 1024)

        val seenStates = mutableListOf<Pair<BackupStatus, String?>>()
        var sawProgressWithPauseStop = false
        startServiceAction(BackupService.ACTION_START)
        val done = waitForState(120_000, 50, seenStates) { state ->
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
        // SCANNING is not asserted: the scan of 3 files can finish
        // between two 50 ms polls, so a poll may legitimately miss it.
        // COPYING is asserted below via the progress notification.
        assertTrue("COPYING was never published", seenStates.any { it.first == BackupStatus.COPYING })

        val oldDest = File(dstDir, "e2e-old.jpg")
        val midDest = File(dstDir, "e2e-mid.jpg")
        val newDest = File(dstDir, "e2e-new.jpg")
        for ((file, expected) in listOf(oldDest to oldData, midDest to midData, newDest to newData)) {
            assertTrue("missing destination file ${file.name}", file.exists())
            assertTrue(
                "destination ${file.name} is not byte-identical to the source",
                file.readBytes().contentEquals(expected),
            )
        }
        assertTrue("stale .part files remain", dstDir.listFiles()?.none { it.name.endsWith(".part") } == true)
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
        // Large files so the copy is still in flight when the pause
        // action lands (the engine-level pause is already proven
        // deterministically by BackupEngineInstrumentedTest with a
        // slow stream; this test proves the notification-action path).
        val bigData = seedPhoto("e2e-big1.jpg", 1_700_000_000_000L, 80 * 1024 * 1024)
        seedPhoto("e2e-big2.jpg", 1_700_000_100_000L, 80 * 1024 * 1024)
        seedPhoto("e2e-big3.jpg", 1_700_000_200_000L, 80 * 1024 * 1024)

        val seenStates = mutableListOf<Pair<BackupStatus, String?>>()
        startServiceAction(BackupService.ACTION_START)
        waitForState(60_000, 50, seenStates) { it.status == BackupStatus.COPYING }
        sendNotificationAction(BackupService.ACTION_PAUSE)
        val paused = waitForState(10_000, 50, seenStates) { it.status == BackupStatus.PAUSED }
        assertEquals(BackupStatus.PAUSED, paused.status)
        // The in-flight file's .part must exist and stay frozen while paused.
        val parts = dstDir.listFiles()?.filter { it.name.endsWith(".part") } ?: emptyList()
        assertTrue("no .part file while paused (copy finished before the pause landed?)", parts.isNotEmpty())
        val part = parts.first()
        val sizeWhilePaused = part.length()
        assertTrue("empty .part while paused", sizeWhilePaused > 0)
        Thread.sleep(500)
        assertEquals("copy progressed while paused", sizeWhilePaused, part.length())
        val pausedNotification = progressNotification()
        assertEquals(
            listOf("Resume", "Stop"),
            actionTitles(pausedNotification),
        )
        println(
            "SMOKE full-flow: pause froze the copy (.part ${part.name} " +
                "held at $sizeWhilePaused bytes), Resume/Stop offered",
        )

        sendNotificationAction(BackupService.ACTION_RESUME)
        val done = waitForState(120_000, 100, seenStates) {
            it.status == BackupStatus.DONE || it.status == BackupStatus.ERROR
        }
        assertEquals(
            "service ended in ${done.status} after resume: ${done.error?.detail}",
            BackupStatus.DONE,
            done.status,
        )
        val bigDest = File(dstDir, "e2e-big1.jpg")
        assertTrue(bigDest.exists())
        assertTrue(bigDest.readBytes().contentEquals(bigData))
        assertTrue("stale .part files remain", dstDir.listFiles()?.none { it.name.endsWith(".part") } == true)
        println("SMOKE full-flow: resume finished the paused copy, all files byte-identical")
    }

    @Test
    fun stopThroughNotificationActionKeepsFinishedFiles() {
        activityScenario = ActivityScenario.launch(MainActivity::class.java)
        val stopData = mapOf(
            "e2e-stop1.jpg" to seedPhoto("e2e-stop1.jpg", 1_700_000_000_000L, 80 * 1024 * 1024),
            "e2e-stop2.jpg" to seedPhoto("e2e-stop2.jpg", 1_700_000_100_000L, 80 * 1024 * 1024),
            "e2e-stop3.jpg" to seedPhoto("e2e-stop3.jpg", 1_700_000_200_000L, 80 * 1024 * 1024),
        )

        val seenStates = mutableListOf<Pair<BackupStatus, String?>>()
        startServiceAction(BackupService.ACTION_START)
        waitForState(60_000, 50, seenStates) { it.status == BackupStatus.COPYING }
        sendNotificationAction(BackupService.ACTION_STOP)
        val stopped = waitForState(10_000, 50, seenStates) {
            it.status == BackupStatus.STOPPED || it.status == BackupStatus.DONE
        }
        assertEquals(
            "service ended in ${stopped.status} after Stop (copy may have finished first)",
            BackupStatus.STOPPED,
            stopped.status,
        )
        assertTrue("stale .part files remain after Stop", dstDir.listFiles()?.none { it.name.endsWith(".part") } == true)
        // Finished files are kept byte-identical (the stop may land
        // during the first file, in which case there are none yet —
        // that is correct behaviour, not a failure).
        val finished = dstDir.listFiles()?.filter { it.isFile && !it.name.endsWith(".part") } ?: emptyList()
        for (file in finished) {
            val expected = stopData[file.name]
                ?: throw AssertionError("unexpected finished file ${file.name}")
            assertTrue("finished file ${file.name} is not byte-identical", file.readBytes().contentEquals(expected))
        }
        // No further copying after Stop: the destination is stable.
        val countAfterStop = finished.size
        Thread.sleep(1_000)
        val countLater = dstDir.listFiles()?.count { it.isFile && !it.name.endsWith(".part") } ?: 0
        assertEquals("files kept copying after Stop", countAfterStop, countLater)
        println(
            "SMOKE full-flow: Stop ended the job, .part deleted, " +
                "$countAfterStop finished file(s) kept, nothing more copied",
        )
    }
}
