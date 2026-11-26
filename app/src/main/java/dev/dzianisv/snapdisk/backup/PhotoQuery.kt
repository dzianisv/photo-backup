package dev.dzianisv.snapdisk.backup

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.documentfile.provider.DocumentFile

/**
 * Lists photos AND videos from the source (PRD A5: DCIM holds both;
 * every competitor copies both).
 *
 * Primary path: MediaStore images + videos ordered by effective date
 * ascending (oldest first). When the user picked a source folder via
 * SAF, files are listed from that document tree instead (MediaStore
 * has no folder filter that matches a SAF tree reliably), recursing
 * into subfolders so the order is global oldest-first, not
 * folder-by-folder (TDD O5), and the destination mirrors the source
 * tree (TDD C6 / PRD A7).
 */
object PhotoQuery {

    fun queryMediaStoreOldestFirst(context: Context, limit: Int = 100_000): List<PhotoItem> {
        val items = mutableListOf<PhotoItem>()
        items += queryMediaTable(
            context = context,
            uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            limit = limit,
        )
        items += queryMediaTable(
            context = context,
            uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            limit = limit,
        )
        return BackupEngine.sortOldestFirst(items)
    }

    private fun queryMediaTable(
        context: Context,
        uri: Uri,
        limit: Int,
    ): List<PhotoItem> {
        val items = mutableListOf<PhotoItem>()
        val projection = arrayOf(
            MediaStore.MediaColumns._ID,
            MediaStore.MediaColumns.DISPLAY_NAME,
            MediaStore.MediaColumns.DATE_TAKEN,
            MediaStore.MediaColumns.DATE_MODIFIED,
            MediaStore.MediaColumns.DATE_ADDED,
            MediaStore.MediaColumns.SIZE,
            MediaStore.MediaColumns.RELATIVE_PATH,
        )
        // Order in the query itself: the cursor order is the copy
        // order (spec §3). Missing DATE_TAKEN falls back to
        // DATE_MODIFIED (TDD O2).
        val sortOrder = "CASE WHEN ${MediaStore.MediaColumns.DATE_TAKEN} IS NULL " +
            "THEN ${MediaStore.MediaColumns.DATE_MODIFIED} * 1000 " +
            "ELSE ${MediaStore.MediaColumns.DATE_TAKEN} END ASC, " +
            "${MediaStore.MediaColumns._ID} ASC"
        context.contentResolver.query(uri, projection, null, null, sortOrder)
            ?.use { cursor ->
                val idCol = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID)
                val nameCol = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME)
                val takenCol = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_TAKEN)
                val modifiedCol =
                    cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_MODIFIED)
                val addedCol = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_ADDED)
                val sizeCol = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.SIZE)
                val relCol = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.RELATIVE_PATH)
                while (cursor.moveToNext() && items.size < limit) {
                    val id = cursor.getLong(idCol)
                    val relativePath = (cursor.getString(relCol) ?: "")
                        .trimEnd('/')
                    items += PhotoItem(
                        id = id,
                        displayName = cursor.getString(nameCol) ?: "media-$id",
                        dateTakenMillis = cursor.getLong(takenCol),
                        dateAddedSeconds = cursor.getLong(addedCol),
                        sizeBytes = cursor.getLong(sizeCol),
                        contentUri = ContentUris.withAppendedId(uri, id).toString(),
                        dateModifiedMillis = cursor.getLong(modifiedCol) * 1000,
                        relativePath = relativePath,
                    )
                }
            }
        return items
    }

    /** List image/video files under a SAF document tree, recursively. */
    fun listTreeFiles(context: Context, treeUri: Uri): List<PhotoItem> {
        val root = DocumentFile.fromTreeUri(context, treeUri) ?: return emptyList()
        val items = mutableListOf<PhotoItem>()
        walkTree(root, "", items)
        return BackupEngine.sortOldestFirst(items)
    }

    private fun walkTree(dir: DocumentFile, relativePath: String, out: MutableList<PhotoItem>) {
        for (file in dir.listFiles()) {
            if (file.isDirectory) {
                val childPath = if (relativePath.isEmpty()) {
                    file.name ?: continue
                } else {
                    "$relativePath/${file.name}"
                }
                walkTree(file, childPath, out)
                continue
            }
            if (!file.isFile) continue
            val name = file.name ?: continue
            if (!isMediaName(name)) continue
            out += PhotoItem(
                id = file.uri.toString().hashCode().toLong(),
                displayName = name,
                dateTakenMillis = file.lastModified(),
                dateAddedSeconds = file.lastModified() / 1000,
                sizeBytes = file.length(),
                contentUri = file.uri.toString(),
                dateModifiedMillis = file.lastModified(),
                relativePath = relativePath,
            )
        }
    }

    // internal (not private) so the unit test can call it directly.
    internal fun isMediaName(name: String): Boolean {
        val lower = name.lowercase()
        return IMAGE_EXTENSIONS.any { lower.endsWith(it) } ||
            VIDEO_EXTENSIONS.any { lower.endsWith(it) }
    }

    private val IMAGE_EXTENSIONS = listOf(
        ".jpg", ".jpeg", ".png", ".heic", ".heif", ".webp",
        ".gif", ".bmp", ".dng", ".raw", ".avif", ".jxl",
    )
    private val VIDEO_EXTENSIONS = listOf(
        ".mp4", ".mov", ".mkv", ".webm", ".3gp", ".m4v", ".avi",
    )

    /** Open a [PhotoItem] for reading, whatever its URI scheme is. */
    fun openInput(context: Context, item: PhotoItem) =
        context.contentResolver.openInputStream(Uri.parse(item.contentUri))
}
