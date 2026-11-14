package dev.dzianisv.snapdisk.backup

import java.io.File
import java.io.InputStream
import java.io.OutputStream

/**
 * One photo or video found in the source. [dateTakenMillis] is the
 * MediaStore DATE_TAKEN (0 when unknown); the copy order is
 * oldest-first on the effective date (DATE_TAKEN, else
 * DATE_MODIFIED), tie-broken by [id] (TDD O1–O3). [relativePath] is
 * the path under the From folder ("" for a flat source) so the
 * destination mirrors the source tree (TDD C6 / PRD A7).
 */
data class PhotoItem(
    val id: Long,
    val displayName: String,
    val dateTakenMillis: Long,
    val dateAddedSeconds: Long,
    val sizeBytes: Long,
    val contentUri: String,
    val dateModifiedMillis: Long = 0L,
    val relativePath: String = "",
) {
    /** Effective sort date: DATE_TAKEN, else DATE_MODIFIED, else 0. */
    val effectiveDateMillis: Long
        get() = if (dateTakenMillis > 0) dateTakenMillis else dateModifiedMillis
}

/** Controls a running copy. Polled by the engine between buffer writes. */
class CopyControl {
    @Volatile var paused: Boolean = false
    @Volatile var stopped: Boolean = false
}

sealed class CopyResult {
    /** Destination already held a file of the same size — not re-copied. */
    data object Skipped : CopyResult()

    /** Fully copied and size-verified. */
    data class Copied(val bytes: Long) : CopyResult()

    /** Pause was requested mid-copy; the .part file stays for resume. */
    data class Paused(val bytesCopied: Long) : CopyResult()

    /** Stop was requested mid-copy; the .part file stays until the caller deletes it. */
    data class Stopped(val bytesCopied: Long) : CopyResult()

    /** Failed with the full, untruncated error (never a one-line summary). */
    data class Failed(val error: BackupError) : CopyResult()
}

/**
 * Full error record. [summary] is the one-line plain-language row;
 * [detail] is the complete technical error (exception class + message +
 * stack trace + file + operation) — shown in full behind "Show details",
 * selectable, with Copy. Never truncated.
 */
data class BackupError(
    val summary: String,
    val detail: String,
    val fileName: String? = null,
) {
    companion object {
        fun fromThrowable(
            summary: String,
            throwable: Throwable,
            fileName: String? = null,
            operation: String? = null,
        ): BackupError {
            val detail = buildString {
                if (operation != null) appendLine("Operation: $operation")
                if (fileName != null) appendLine("File: $fileName")
                appendLine("Error: ${throwable::class.qualifiedName}: ${throwable.message}")
                throwable.cause?.let { appendLine("Cause: ${it::class.qualifiedName}: ${it.message}") }
                appendLine("Stack trace:")
                append(throwable.stackTraceToString())
            }
            return BackupError(summary = summary, detail = detail, fileName = fileName)
        }
    }
}

object BackupEngine {

    /** Oldest first: effective date ascending, then id, then name (TDD O1–O3). */
    fun sortOldestFirst(items: List<PhotoItem>): List<PhotoItem> =
        items.sortedWith(
            compareBy<PhotoItem> { it.effectiveDateMillis }
                .thenBy { it.id }
                .thenBy { it.displayName.lowercase() },
        )

    /** Skip only when the destination already holds a file of the same size. */
    fun shouldSkip(destination: File, expectedSizeBytes: Long): Boolean =
        destination.exists() && destination.isFile && destination.length() == expectedSizeBytes

    /** True when the destination file exists and its size matches the source. */
    fun verify(destination: File, expectedSizeBytes: Long): Boolean =
        destination.exists() && destination.isFile && destination.length() == expectedSizeBytes

    /** In-progress copy target: `<name>.part` next to the destination (PRD A3). */
    fun partFileFor(destination: File): File =
        File(destination.parentFile, "${destination.name}.part")

    /** True when [error] is a size-verification failure (retry once, TDD C2). */
    fun isVerifyFailure(error: BackupError): Boolean =
        error.detail.contains("size verification after copy")

    /**
     * Copy [input] to [destination], resuming a partial `.part` file if
     * one exists. A partial file is resumed only when its size is
     * smaller than the expected size; when [expectedSizeBytes] is
     * unknown (-1) the destination is overwritten from scratch.
     * The copy writes to `<name>.part` and renames to the destination
     * only after the size verification passes, so a partial file is
     * never mistaken for a finished one.
     * Returns [CopyResult.Copied] only after [verify] passes.
     * A retry after a verification failure needs a fresh stream, so
     * the caller (service) retries once with a new stream (TDD C2).
     */
    fun copyWithResume(
        input: InputStream,
        destination: File,
        expectedSizeBytes: Long,
        control: CopyControl,
        onProgress: (bytesCopied: Long) -> Unit = {},
    ): CopyResult {
        destination.parentFile?.mkdirs()
        if (expectedSizeBytes > 0 && shouldSkip(destination, expectedSizeBytes)) {
            return CopyResult.Skipped
        }
        val part = partFileFor(destination)
        // A complete .part that was never renamed (crash between write
        // and rename) is finished, not re-copied.
        if (expectedSizeBytes > 0 && part.exists() && part.isFile &&
            part.length() == expectedSizeBytes
        ) {
            return if (part.renameTo(destination) && verify(destination, expectedSizeBytes)) {
                CopyResult.Copied(expectedSizeBytes)
            } else {
                CopyResult.Failed(
                    BackupError(
                        summary = "Could not finish ${destination.name}",
                        detail = "Operation: rename ${part.absolutePath} to ${destination.absolutePath}\n" +
                            "Expected size: $expectedSizeBytes bytes\n" +
                            "Part size: ${part.length()} bytes\n" +
                            "Destination exists: ${destination.exists()}",
                        fileName = destination.name,
                    ),
                )
            }
        }
        var resumeOffset = 0L
        if (expectedSizeBytes > 0 && part.exists() && part.isFile) {
            val existing = part.length()
            if (existing in 1 until expectedSizeBytes) resumeOffset = existing
        }
        return try {
            var skipped = 0L
            while (skipped < resumeOffset) {
                val n = input.skip(resumeOffset - skipped)
                if (n <= 0) break
                skipped += n
            }
            if (skipped != resumeOffset) {
                // Stream could not skip to the resume point: restart clean.
                part.delete()
                resumeOffset = 0
            }
            var copied = resumeOffset
            val out: OutputStream = java.io.FileOutputStream(part, resumeOffset > 0)
            out.use { sink ->
                val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
                while (true) {
                    if (control.stopped) return CopyResult.Stopped(copied)
                    while (control.paused && !control.stopped) Thread.sleep(100)
                    if (control.stopped) return CopyResult.Stopped(copied)
                    val read = input.read(buffer)
                    if (read < 0) break
                    sink.write(buffer, 0, read)
                    copied += read
                    onProgress(copied)
                }
                sink.flush()
            }
            if (expectedSizeBytes > 0 && part.length() != expectedSizeBytes) {
                val actualSize = part.length()
                part.delete()
                return CopyResult.Failed(
                    BackupError(
                        summary = "Copy finished but the file size does not match",
                        detail = "File: ${destination.absolutePath}\n" +
                            "Expected size: $expectedSizeBytes bytes\n" +
                            "Actual size: $actualSize bytes\n" +
                            "Operation: size verification after copy",
                        fileName = destination.name,
                    ),
                )
            }
            if (!part.renameTo(destination)) {
                return CopyResult.Failed(
                    BackupError(
                        summary = "Could not finish ${destination.name}",
                        detail = "Operation: rename ${part.absolutePath} to ${destination.absolutePath}\n" +
                            "Part size: ${part.length()} bytes\n" +
                            "Destination exists: ${destination.exists()}",
                        fileName = destination.name,
                    ),
                )
            }
            if (expectedSizeBytes > 0 && !verify(destination, expectedSizeBytes)) {
                return CopyResult.Failed(
                    BackupError(
                        summary = "Copy finished but the file size does not match",
                        detail = "File: ${destination.absolutePath}\n" +
                            "Expected size: $expectedSizeBytes bytes\n" +
                            "Actual size: ${destination.length()} bytes\n" +
                            "Operation: size verification after copy",
                        fileName = destination.name,
                    ),
                )
            }
            CopyResult.Copied(copied)
        } catch (t: Throwable) {
            CopyResult.Failed(
                BackupError.fromThrowable(
                    summary = "Copy failed: ${t.message ?: t::class.simpleName}",
                    throwable = t,
                    fileName = destination.name,
                    operation = "copy to ${destination.absolutePath}",
                ),
            )
        }
    }
}
