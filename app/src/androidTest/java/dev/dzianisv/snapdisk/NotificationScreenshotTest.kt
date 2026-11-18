package dev.dzianisv.snapdisk

import android.Manifest
import android.app.Notification
import android.app.NotificationManager
import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.provider.MediaStore
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import dev.dzianisv.snapdisk.backup.BackupEngine
import dev.dzianisv.snapdisk.backup.BackupState
import dev.dzianisv.snapdisk.backup.BackupStatus
import dev.dzianisv.snapdisk.backup.CopyControl
import dev.dzianisv.snapdisk.backup.CopyResult
import dev.dzianisv.snapdisk.service.NotificationHelper
import dev.dzianisv.snapdisk.util.FormatUtils
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicReference
import kotlin.concurrent.thread
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Play Store screenshots 02/03/04: the notifications as System UI
 * actually renders them in the shade — not the Compose root, so the
 * in-process `captureScreenshot` cannot see them. UiDevice opens the
 * real notification shade and photographs the whole screen.
 *
 * Honesty rules for these shots (the numbers must be real):
 * - 02 offer: posted through the production `postOfferNotification`
 *   path — the same call StorageReceiver makes on a mount broadcast.
 *   The emulator has no USB mount event, so the trigger is the test;
 *   the notification itself (builder, channel, actions) is production.
 * - 03 progress: a REAL copy is running (a 12 MiB file dripped
 *   slowly); the posted state is built from the live `.part` size and
 *   the measured throughput — percent, copied/total, speed and ETA are
 *   whatever the copy actually did.
 * - 04 done: a REAL copy of 3 files finished first; the posted state
 *   carries the real file count and byte total.
 */
class NotificationScreenshotTest {

    private lateinit var ctx: Context
    private lateinit var mgr: NotificationManager
    private lateinit var parentDir: File
    private lateinit var srcDir: File
    private lateinit var dstDir: File

    @get:Rule
    val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        Manifest.permission.POST_NOTIFICATIONS,
    )

    @Before
    fun setUp() {
        ctx = InstrumentationRegistry.getInstrumentation().targetContext
        mgr = ctx.getSystemService(NotificationManager::class.java)!!
        NotificationHelper.ensureChannels(ctx)
        parentDir = File(ctx.cacheDir, "shots").apply { mkdirs() }
        srcDir = File(parentDir, "src").apply { mkdirs() }
        dstDir = File(parentDir, "dst").apply { mkdirs() }
        srcDir.listFiles()?.forEach { it.delete() }
        dstDir.listFiles()?.forEach { it.delete() }
        mgr.cancelAll()
    }

    @After
    fun tearDown() {
        try {
            UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).pressBack()
        } catch (_: Throwable) {
            // Shade already closed — nothing to do.
        }
        mgr.cancelAll()
    }

    @Test
    fun offerNotificationScreenshot() {
        NotificationHelper.postOfferNotification(ctx)
        // Poll: on a loaded emulator the NotificationManagerService
        // can take seconds to list a just-posted notification
        // (smoke run 44175300406 failed here on the immediate read).
        val deadline = System.currentTimeMillis() + 5_000
        while (System.currentTimeMillis() < deadline &&
            mgr.activeNotifications.none { it.id == NotificationHelper.NOTIFICATION_ID_OFFER }
        ) {
            Thread.sleep(200)
        }
        assertTrue(
            "offer notification was not posted",
            mgr.activeNotifications.any { it.id == NotificationHelper.NOTIFICATION_ID_OFFER },
        )
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        openShade(device)
        assertTrue(
            "offer notification not visible in the shade",
            device.wait(Until.hasObject(By.textContains("Backup photos?")), 15_000),
        )
        Thread.sleep(1_000)
        saveShadeScreenshot(device, "02-offer")
        println("SMOKE notifications: 02-offer shot — real offer notification in the shade")
    }

    @Test
    fun progressNotificationScreenshot() {
        // Real copy, dripped slowly: 12 MiB at ~800 KB/s takes ~15 s,
        // so it is still running when the shade opens. The progress
        // state comes from the live .part size + measured throughput.
        val totalBytes = 12L * 1024 * 1024
        val src = File(srcDir, "big.jpg").apply {
            writeBytes(ByteArray(totalBytes.toInt()) { (it % 251).toByte() })
        }
        val dest = File(dstDir, "big.jpg")
        val part = BackupEngine.partFileFor(dest)
        val control = CopyControl()
        val resultRef = AtomicReference<CopyResult?>()
        val done = CountDownLatch(1)
        val startedAt = System.currentTimeMillis()
        thread {
            resultRef.set(
                BackupEngine.copyWithResume(
                    input = SlowInputStream(FileInputStream(src)),
                    destination = dest,
                    expectedSizeBytes = totalBytes,
                    control = control,
                ),
            )
            done.countDown()
        }
        // Wait for >= 5 s of real copying so the speed is measured
        // (not 0 — otherwise the line would read "Calculating…").
        var copiedBytes = 0L
        var speedBytesPerSec = 0.0
        val deadline = System.currentTimeMillis() + 30_000
        while (System.currentTimeMillis() < deadline) {
            Thread.sleep(500)
            if (part.exists()) copiedBytes = part.length()
            val elapsedSec = (System.currentTimeMillis() - startedAt) / 1000.0
            if (elapsedSec >= 5.0 && copiedBytes > 0) {
                speedBytesPerSec = copiedBytes / elapsedSec
                break
            }
        }
        assertTrue("copy produced no bytes in 30 s", copiedBytes > 0)
        assertTrue("measured speed is 0", speedBytesPerSec > 0)
        val state = BackupState(
            status = BackupStatus.COPYING,
            copiedBytes = copiedBytes,
            totalBytes = totalBytes,
            speedBytesPerSec = speedBytesPerSec,
        )
        mgr.notify(
            NotificationHelper.NOTIFICATION_ID_PROGRESS,
            NotificationHelper.buildProgressNotification(ctx, state),
        )
        val posted = run {
            // Poll: on a loaded emulator the NotificationManagerService
            // can take seconds to list a just-posted notification
            // (smoke run 44154569523 failed here with
            // NoSuchElementException on the immediate read, twice).
            val deadline = System.currentTimeMillis() + 10_000
            var found: Notification? = null
            while (System.currentTimeMillis() < deadline && found == null) {
                found = mgr.activeNotifications.firstOrNull {
                    it.id == NotificationHelper.NOTIFICATION_ID_PROGRESS
                }?.notification
                if (found == null) Thread.sleep(200)
            }
            assertTrue(
                "progress notification was not listed after posting it",
                found != null,
            )
            found!!
        }
        assertEquals(
            "posted progress bar does not match the real copy",
            state.percent,
            posted.extras.getInt(Notification.EXTRA_PROGRESS),
        )
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        openShade(device)
        assertTrue(
            "progress notification not visible in the shade",
            device.wait(Until.hasObject(By.textContains("/12.0 MB")), 15_000),
        )
        Thread.sleep(1_000)
        saveShadeScreenshot(device, "03-progress")
        println(
            "SMOKE notifications: 03-progress shot at ${state.percent}% " +
                "(${FormatUtils.formatProgress(copiedBytes, totalBytes, speedBytesPerSec)})",
        )
        assertTrue(
            "copy did not finish",
            done.await(180, TimeUnit.SECONDS),
        )
        assertTrue("copy did not complete", resultRef.get() is CopyResult.Copied)
        assertTrue(BackupEngine.verify(dest, totalBytes))
        println("SMOKE notifications: the copy behind the shot finished + verified")
    }

    @Test
    fun doneNotificationScreenshot() {
        // Real copy of 3 files first; the done notification carries
        // the real file count and byte total.
        val sizes = listOf(100_000, 200_000, 300_000)
        var totalCopied = 0L
        for ((i, size) in sizes.withIndex()) {
            val name = "photo${i + 1}.jpg"
            val src = File(srcDir, name).apply {
                writeBytes(ByteArray(size) { (it % 251).toByte() })
            }
            val dest = File(dstDir, name)
            val result = FileInputStream(src).use { input ->
                BackupEngine.copyWithResume(
                    input = input,
                    destination = dest,
                    expectedSizeBytes = size.toLong(),
                    control = CopyControl(),
                )
            }
            assertTrue("copy failed for $name", result is CopyResult.Copied)
            assertTrue(BackupEngine.verify(dest, size.toLong()))
            totalCopied += size
        }
        val state = BackupState(
            status = BackupStatus.DONE,
            copiedBytes = totalCopied,
            copiedFiles = sizes.size,
        )
        mgr.notify(
            NotificationHelper.NOTIFICATION_ID_PROGRESS,
            NotificationHelper.buildProgressNotification(ctx, state),
        )
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        openShade(device)
        assertTrue(
            "done notification not visible in the shade",
            device.wait(Until.hasObject(By.textContains("3 photos")), 15_000),
        )
        Thread.sleep(1_000)
        saveShadeScreenshot(device, "04-done")
        println(
            "SMOKE notifications: 04-done shot — 3 photos, " +
                "${FormatUtils.formatBytes(totalCopied)} copied for real",
        )
    }

    /** Opens the notification shade, retrying once — System UI is slow on first boot. */
    private fun openShade(device: UiDevice) {
        if (device.openNotification()) return
        Thread.sleep(2_000)
        assertTrue("could not open the notification shade", device.openNotification())
    }

    /**
     * Photographs the whole screen (shade included) with UiDevice and
     * publishes the PNG to Pictures/PhotoBackup-shots/ via MediaStore —
     * the same directory smoke-test.sh pulls, so the shot lands in the
     * run's smoke-evidence artifact. Best-effort evidence: it never
     * fails the test.
     */
    private fun saveShadeScreenshot(device: UiDevice, name: String) {
        try {
            val tmp = File(ctx.cacheDir, "$name.png")
            if (!device.takeScreenshot(tmp) || !tmp.exists() || tmp.length() == 0L) {
                println("SMOKE screenshot FAILED for $name: UiDevice.takeScreenshot produced no file")
                return
            }
            val values = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, "$name.png")
                put(MediaStore.Images.Media.MIME_TYPE, "image/png")
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/PhotoBackup-shots")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    put(MediaStore.Images.Media.IS_PENDING, 1)
                }
            }
            val uri = ctx.contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                values,
            ) ?: run {
                println("SMOKE screenshot FAILED for $name: MediaStore insert returned null")
                return
            }
            ctx.contentResolver.openOutputStream(uri)?.use { out ->
                FileInputStream(tmp).use { it.copyTo(out) }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ctx.contentResolver.update(
                    uri,
                    ContentValues().apply {
                        put(MediaStore.Images.Media.IS_PENDING, 0)
                    },
                    null,
                    null,
                )
            }
            println("SMOKE screenshot saved: $name.png (${tmp.length()} bytes)")
        } catch (e: Throwable) {
            println("SMOKE screenshot FAILED for $name: ${e.message}")
        }
    }

    /** Drips the wrapped stream slowly so the copy is still running when the shade opens. */
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
