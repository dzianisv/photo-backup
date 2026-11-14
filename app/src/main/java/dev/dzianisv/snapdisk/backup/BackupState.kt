package dev.dzianisv.snapdisk.backup

/**
 * The backup's observable state, shared between the foreground service
 * and the settings screen. [error] carries the FULL error (see
 * [BackupError]) — the UI shows its summary in the row and the complete
 * detail behind "Show details", never a truncated line.
 */
enum class BackupStatus {
    IDLE, SCANNING, COPYING, PAUSED, STOPPED, DONE, ERROR,
    /** Free backup size limit reached — upgrade to Pro to continue. */
    LIMIT_REACHED,
}

/** A file that was skipped (not copied) with the reason why (TDD C5/E7). */
data class SkippedFile(val name: String, val reason: String)

data class BackupState(
    val status: BackupStatus = BackupStatus.IDLE,
    val copiedBytes: Long = 0L,
    val totalBytes: Long = 0L,
    val copiedFiles: Int = 0,
    val totalFiles: Int = 0,
    val speedBytesPerSec: Double = 0.0,
    val currentFileName: String? = null,
    val error: BackupError? = null,
    val pausedByDetach: Boolean = false,
    /** Files skipped with a reason (too large for FAT32, failed, ...). */
    val skippedFiles: List<SkippedFile> = emptyList(),
) {
    val percent: Int
        get() = if (totalBytes > 0) (copiedBytes * 100 / totalBytes).toInt() else 0
}
