package dev.dzianisv.snapdisk.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.usb.UsbManager
import android.os.storage.StorageManager
import androidx.core.content.ContextCompat
import dev.dzianisv.snapdisk.backup.BackupError
import dev.dzianisv.snapdisk.backup.ProPrefs

/**
 * Storage mount/unmount receiver.
 *
 * - Disk connected (ACTION_MEDIA_MOUNTED / USB mass-storage volume
 *   mounted) → Pro: auto-backup starts right away; free: post the
 *   "External storage is connected. Backup photos?" offer
 *   notification (Backup / Cancel).
 * - Disk removed while a backup runs → the service pauses with the full
 *   error state; Resume continues when the disk is back.
 * - Mount events are debounced: Android can emit duplicates within
 *   2s for the same volume (spec §1).
 *
 * Note: ACTION_MEDIA_MOUNTED cannot be received from the manifest on
 * modern Android — it is registered dynamically in MainActivity.
 * USB_DEVICE_ATTACHED is on the manifest-broadcast exemption list, so
 * the offer fires even when the app is not running.
 */
class StorageReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            Intent.ACTION_MEDIA_MOUNTED,
            UsbManager.ACTION_USB_DEVICE_ATTACHED -> {
                if (!debounceMount()) return
                NotificationHelper.ensureChannels(context)
                if (ProPrefs(context).isPro) {
                    // Pro: auto-backup when a disk is connected.
                    val serviceIntent = Intent(context, BackupService::class.java)
                        .setAction(BackupService.ACTION_START)
                    ContextCompat.startForegroundService(context, serviceIntent)
                } else {
                    NotificationHelper.postOfferNotification(context)
                }
            }
            Intent.ACTION_MEDIA_UNMOUNTED,
            Intent.ACTION_MEDIA_EJECT,
            Intent.ACTION_MEDIA_REMOVED,
            Intent.ACTION_MEDIA_BAD_REMOVAL,
            UsbManager.ACTION_USB_DEVICE_DETACHED -> {
                NotificationHelper.dismissOfferNotification(context)
                // Tell a running service the destination may be gone;
                // the service itself verifies on its next write and
                // pauses with the full error if the disk is really gone.
                context.sendBroadcast(
                    Intent(ACTION_DESTINATION_DETACHED).setPackage(context.packageName),
                )
            }
            ACTION_DISMISS_OFFER -> {
                NotificationHelper.dismissOfferNotification(context)
            }
        }
    }

    companion object {
        const val ACTION_DISMISS_OFFER =
            "dev.dzianisv.snapdisk.action.DISMISS_OFFER"
        const val ACTION_DESTINATION_DETACHED =
            "dev.dzianisv.snapdisk.action.DESTINATION_DETACHED"

        @Volatile
        private var lastMountMs: Long = 0

        /** Ignore mount events within 2s of the previous one (spec §1). */
        internal fun debounceMount(nowMs: Long = System.currentTimeMillis()): Boolean {
            return if (nowMs - lastMountMs < 2_000) {
                false
            } else {
                lastMountMs = nowMs
                true
            }
        }

        /** Dynamic registration: media-mount broadcasts need this. */
        fun intentFilter(): IntentFilter = IntentFilter().apply {
            addAction(Intent.ACTION_MEDIA_MOUNTED)
            addAction(Intent.ACTION_MEDIA_UNMOUNTED)
            addAction(Intent.ACTION_MEDIA_EJECT)
            addAction(Intent.ACTION_MEDIA_REMOVED)
            addAction(Intent.ACTION_MEDIA_BAD_REMOVAL)
            addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED)
            addAction(UsbManager.ACTION_USB_DEVICE_DETACHED)
            addAction(ACTION_DISMISS_OFFER)
            addDataScheme("file")
        }

        /** True when any mounted volume looks like removable/external storage. */
        fun hasExternalVolume(context: Context): Boolean {
            return try {
                val storage = context.getSystemService(StorageManager::class.java) ?: return false
                storage.storageVolumes.any { it.isRemovable }
            } catch (_: Exception) {
                false
            }
        }

        /** Full error record for a detached disk (shown, never truncated). */
        fun detachedError(): BackupError = BackupError(
            summary = "External storage was disconnected",
            detail = "Operation: writing backup file\n" +
                "Error: the external storage was unmounted or unplugged " +
                "while the backup was running.\n" +
                "The backup is paused, not lost: reconnect the disk and " +
                "tap Resume — already-copied files are skipped and the " +
                "interrupted file resumes from where it stopped.",
        )
    }
}
