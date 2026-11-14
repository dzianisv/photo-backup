package dev.dzianisv.snapdisk.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import dev.dzianisv.snapdisk.MainActivity
import dev.dzianisv.snapdisk.R
import dev.dzianisv.snapdisk.backup.BackupState
import dev.dzianisv.snapdisk.backup.BackupStatus
import dev.dzianisv.snapdisk.util.FormatUtils

/**
 * All notifications: the "disk connected, back up?" offer and the
 * foreground-service progress notification (progress bar + percent,
 * copied/total, speed, ETA + Pause/Stop), exactly as the wireframes.
 * One channel, importance HIGH so the offer pops (spec §2); progress
 * updates are throttled to ≤1/sec and only alert once (TDD P4).
 */
object NotificationHelper {
    const val CHANNEL_BACKUP = "snapdisk_backup"
    const val NOTIFICATION_ID_PROGRESS = 1
    const val NOTIFICATION_ID_OFFER = 2

    fun ensureChannels(context: Context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return
        val manager = context.getSystemService(NotificationManager::class.java) ?: return
        val channel = NotificationChannel(
            CHANNEL_BACKUP,
            context.getString(R.string.notification_channel_name),
            NotificationManager.IMPORTANCE_HIGH,
        ).apply {
            description = context.getString(R.string.notification_channel_description)
            setShowBadge(false)
        }
        manager.createNotificationChannel(channel)
    }

    fun areNotificationsEnabled(context: Context): Boolean {
        val manager = context.getSystemService(NotificationManager::class.java) ?: return false
        return manager.areNotificationsEnabled()
    }

    private fun openAppIntent(context: Context): PendingIntent {
        val intent = Intent(context, MainActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        return PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
    }

    /** Notification actions go through the manifest receiver (work with the app closed). */
    private fun actionIntent(context: Context, action: String, requestCode: Int): PendingIntent {
        val intent = Intent(context, BackupActionReceiver::class.java).setAction(action)
        return PendingIntent.getBroadcast(
            context, requestCode, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
    }

    /** "External storage is connected. Backup photos?" [Backup] [Cancel] */
    fun buildOfferNotification(context: Context): Notification =
        NotificationCompat.Builder(context, CHANNEL_BACKUP)
            .setSmallIcon(R.drawable.ic_backup)
            .setContentTitle(context.getString(R.string.offer_title))
            .setContentText(context.getString(R.string.offer_text))
            .setContentIntent(openAppIntent(context))
            .setAutoCancel(true)
            .addAction(
                R.drawable.ic_backup,
                context.getString(R.string.backup),
                actionIntent(context, BackupService.ACTION_START, 1),
            )
            .addAction(
                R.drawable.ic_close,
                context.getString(R.string.cancel),
                actionIntent(context, StorageReceiver.ACTION_DISMISS_OFFER, 5),
            )
            .build()

    fun postOfferNotification(context: Context) {
        ensureChannels(context)
        val manager = context.getSystemService(NotificationManager::class.java) ?: return
        manager.notify(NOTIFICATION_ID_OFFER, buildOfferNotification(context))
    }

    fun dismissOfferNotification(context: Context) {
        context.getSystemService(NotificationManager::class.java)
            ?.cancel(NOTIFICATION_ID_OFFER)
    }

    /** Foreground-service notification: progress bar + wireframe progress line. */
    fun buildProgressNotification(context: Context, state: BackupState): Notification {
        val builder = NotificationCompat.Builder(context, CHANNEL_BACKUP)
            .setSmallIcon(R.drawable.ic_backup)
            .setContentIntent(openAppIntent(context))
            .setOngoing(state.status == BackupStatus.COPYING || state.status == BackupStatus.PAUSED)
            .setOnlyAlertOnce(true)

        when (state.status) {
            BackupStatus.SCANNING -> {
                builder.setContentTitle(context.getString(R.string.scanning_photos))
                    .setContentText(context.getString(R.string.scanning_photos_detail))
                    .setProgress(0, 0, true)
            }
            BackupStatus.COPYING, BackupStatus.PAUSED -> {
                val line = FormatUtils.formatProgress(
                    state.copiedBytes, state.totalBytes, state.speedBytesPerSec,
                )
                builder.setContentTitle(
                    if (state.status == BackupStatus.PAUSED) {
                        context.getString(R.string.backup_paused)
                    } else {
                        context.getString(R.string.backing_up_photos)
                    },
                )
                    .setContentText(line)
                    .setProgress(100, state.percent, false)
                if (state.status == BackupStatus.COPYING) {
                    builder.setStyle(NotificationCompat.BigTextStyle().bigText(line))
                    builder.addAction(
                        R.drawable.ic_pause, context.getString(R.string.pause),
                        actionIntent(context, BackupService.ACTION_PAUSE, 2),
                    )
                } else {
                    builder.addAction(
                        R.drawable.ic_play, context.getString(R.string.resume),
                        actionIntent(context, BackupService.ACTION_RESUME, 3),
                    )
                }
                builder.addAction(
                    R.drawable.ic_close, context.getString(R.string.stop),
                    actionIntent(context, BackupService.ACTION_STOP, 4),
                )
            }
            BackupStatus.DONE -> {
                val detail = if (state.skippedFiles.isEmpty()) {
                    context.getString(
                        R.string.backup_done_detail,
                        state.copiedFiles, FormatUtils.formatBytes(state.copiedBytes),
                    )
                } else {
                    // Done with skips: honest counts, never "0 failed"
                    // when files were skipped (spec §2 / TDD C5, E7).
                    context.getString(
                        R.string.backup_done_detail_skipped,
                        state.copiedFiles,
                        FormatUtils.formatBytes(state.copiedBytes),
                        state.skippedFiles.size,
                    )
                }
                builder.setContentTitle(context.getString(R.string.backup_done))
                    .setContentText(detail)
                    .setStyle(NotificationCompat.BigTextStyle().bigText(detail))
                    .setProgress(0, 0, false)
                    .setAutoCancel(true)
            }
            BackupStatus.LIMIT_REACHED -> {
                builder.setContentTitle(context.getString(R.string.limit_reached_title))
                    .setContentText(
                        context.getString(
                            R.string.limit_reached_detail,
                            FormatUtils.formatBytes(state.copiedBytes),
                        ),
                    )
                    .setProgress(0, 0, false)
                    .setAutoCancel(true)
                builder.addAction(
                    R.drawable.ic_play, context.getString(R.string.resume),
                    actionIntent(context, BackupService.ACTION_RESUME, 3),
                )
            }
            BackupStatus.ERROR -> {
                builder.setContentTitle(context.getString(R.string.backup_error))
                    .setContentText(state.error?.summary ?: context.getString(R.string.backup_error))
                    .setStyle(
                        NotificationCompat.BigTextStyle()
                            .bigText(state.error?.summary ?: ""),
                    )
                    .setProgress(0, 0, false)
                    .setAutoCancel(true)
                builder.addAction(
                    R.drawable.ic_play, context.getString(R.string.resume),
                    actionIntent(context, BackupService.ACTION_RESUME, 3),
                )
            }
            else -> {
                builder.setContentTitle(context.getString(R.string.app_name))
                    .setProgress(0, 0, false)
            }
        }
        return builder.build()
    }
}
