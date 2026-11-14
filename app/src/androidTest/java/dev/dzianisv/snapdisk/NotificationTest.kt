package dev.dzianisv.snapdisk

import android.app.Notification
import androidx.test.platform.app.InstrumentationRegistry
import dev.dzianisv.snapdisk.backup.BackupState
import dev.dzianisv.snapdisk.backup.BackupStatus
import dev.dzianisv.snapdisk.backup.SkippedFile
import dev.dzianisv.snapdisk.service.NotificationHelper
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Notification tests: the disk-connected offer (Backup / Cancel) and
 * the progress notification (progress bar + wireframe progress line),
 * plus the done notification with skipped files.
 */
class NotificationTest {

    private lateinit var ctx: android.content.Context

    @Before
    fun setUp() {
        ctx = InstrumentationRegistry.getInstrumentation().targetContext
        NotificationHelper.ensureChannels(ctx)
    }

    @Test
    fun offerNotificationHasBackupAndCancelActions() {
        val notification = NotificationHelper.buildOfferNotification(ctx)
        val actions = notification.actions ?: emptyArray()
        assertEquals(2, actions.size)
        assertEquals("Backup", actions[0].title.toString())
        assertEquals("Cancel", actions[1].title.toString())
        println("SMOKE notifications: offer has Backup + Cancel actions")
    }

    @Test
    fun progressNotificationShowsProgressAndPauseStop() {
        val state = BackupState(
            status = BackupStatus.COPYING,
            copiedBytes = 25L * 1024 * 1024 * 1024,
            totalBytes = 100L * 1024 * 1024 * 1024,
            speedBytesPerSec = 125.0 * 1024 * 1024,
        )
        val notification = NotificationHelper.buildProgressNotification(ctx, state)
        // Progress bar: max 100, progress 25.
        assertEquals(100, notification.extras.getInt(Notification.EXTRA_PROGRESS_MAX))
        assertEquals(25, notification.extras.getInt(Notification.EXTRA_PROGRESS))
        val actions = notification.actions ?: emptyArray()
        assertEquals(2, actions.size)
        assertEquals("Pause", actions[0].title.toString())
        assertEquals("Stop", actions[1].title.toString())
        val text = notification.extras.getCharSequence(Notification.EXTRA_TEXT).toString()
        assertTrue(text.startsWith("25%"))
        assertTrue(text.contains("25.0 GB/100.0 GB"))
        assertTrue(text.contains("125.0 MB/s"))
        println("SMOKE notifications: progress notification line: $text")
    }

    @Test
    fun doneNotificationReportsSkippedFilesHonestly() {
        val state = BackupState(
            status = BackupStatus.DONE,
            copiedBytes = 100,
            copiedFiles = 2,
            skippedFiles = listOf(
                SkippedFile("huge.mp4", "File is larger than 4 GB and the disk is FAT32 — use exFAT"),
            ),
        )
        val notification = NotificationHelper.buildProgressNotification(ctx, state)
        val text = notification.extras.getCharSequence(Notification.EXTRA_TEXT).toString()
        assertTrue(text.contains("2 photos"))
        assertTrue(text.contains("1 skipped"))
        println("SMOKE notifications: done notification line: $text")
    }
}
