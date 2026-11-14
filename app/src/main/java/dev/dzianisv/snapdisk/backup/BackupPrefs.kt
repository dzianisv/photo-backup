package dev.dzianisv.snapdisk.backup

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

/**
 * Persisted folders. The labels the owner drew ("/sdcard/DCIM" and
 * "/disk/DCIM") are the defaults shown until the user picks a folder
 * with the SAF document-tree picker; the picked tree URI is then
 * persisted (with persistable permission) and shown instead.
 */
class BackupPrefs(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    var fromTreeUri: String?
        get() = prefs.getString(KEY_FROM_URI, null)
        set(value) = prefs.edit { putString(KEY_FROM_URI, value) }

    var toTreeUri: String?
        get() = prefs.getString(KEY_TO_URI, null)
        set(value) = prefs.edit { putString(KEY_TO_URI, value) }

    /** Display label for the source folder. */
    fun fromLabel(context: Context): String =
        fromTreeUri?.let { displayNameFor(context, it) } ?: DEFAULT_FROM_LABEL

    /** Display label for the destination folder. */
    fun toLabel(context: Context): String =
        toTreeUri?.let { displayNameFor(context, it) } ?: DEFAULT_TO_LABEL

    private fun displayNameFor(context: Context, treeUri: String): String {
        return try {
            val uri = android.net.Uri.parse(treeUri)
            val doc = androidx.documentfile.provider.DocumentFile.fromTreeUri(context, uri)
            doc?.name?.let { "/$it" } ?: treeUri
        } catch (_: Exception) {
            treeUri
        }
    }

    companion object {
        const val PREFS_NAME = "photo_backup_prefs"
        const val KEY_FROM_URI = "from_tree_uri"
        const val KEY_TO_URI = "to_tree_uri"

        /** Default source label (owner wireframe v1). */
        const val DEFAULT_FROM_LABEL = "/sdcard/DCIM"

        /** Default destination label (owner wireframe v1). */
        const val DEFAULT_TO_LABEL = "/disk/DCIM"
    }
}
