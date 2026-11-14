package dev.dzianisv.snapdisk.backup

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

/**
 * Pro state + job volume identity, persisted on-device.
 *
 * Free: manual backup of photos + videos, oldest-first, no ads, no
 * account, no cloud. Free backup size is capped at
 * [FREE_BACKUP_BYTES_LIMIT] per run; Pro is unlimited.
 * Pro (one-time purchase, no subscription): auto-backup when a disk
 * is connected + unlimited backup size.
 */
class ProPrefs(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    /** True once the Pro purchase has been acknowledged. */
    var isPro: Boolean
        get() = prefs.getBoolean(KEY_IS_PRO, false)
        set(value) = prefs.edit { putBoolean(KEY_IS_PRO, value) }

    /** UUID of the destination volume the current job writes to (TDD X3). */
    var jobVolumeUuid: String?
        get() = prefs.getString(KEY_JOB_VOLUME_UUID, null)
        set(value) = prefs.edit { putString(KEY_JOB_VOLUME_UUID, value) }

    companion object {
        const val PREFS_NAME = "snapdisk_pro_prefs"
        const val KEY_IS_PRO = "is_pro"
        const val KEY_JOB_VOLUME_UUID = "job_volume_uuid"

        /** Google Play Billing product id for the one-time Pro unlock. */
        const val PRO_PRODUCT_ID = "pro_unlock"

        /** Pro price as shown in the store listing (one-time, USD). */
        const val PRO_PRICE_LABEL = "$9.99"

        /** Free backup size cap per run (5 GB). Pro is unlimited. */
        const val FREE_BACKUP_BYTES_LIMIT: Long = 5L * 1024 * 1024 * 1024
    }
}
