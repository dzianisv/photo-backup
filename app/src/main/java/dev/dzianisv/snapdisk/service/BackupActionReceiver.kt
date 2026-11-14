package dev.dzianisv.snapdisk.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat

/**
 * Manifest-registered receiver for notification actions (spec §2):
 * all actions work with the app closed — the receiver forwards them
 * to [BackupService] (startForegroundService). The offer's Cancel
 * action just dismisses the offer notification.
 */
class BackupActionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            BackupService.ACTION_START,
            BackupService.ACTION_PAUSE,
            BackupService.ACTION_RESUME,
            BackupService.ACTION_STOP -> {
                val serviceIntent = Intent(context, BackupService::class.java)
                    .setAction(intent.action)
                ContextCompat.startForegroundService(context, serviceIntent)
            }
            StorageReceiver.ACTION_DISMISS_OFFER -> {
                NotificationHelper.dismissOfferNotification(context)
            }
        }
    }
}
