package dev.dzianisv.snapdisk.util

import java.io.File

/**
 * Filesystem type of the volume a file lives on, from /proc/mounts.
 * Used for the FAT32 >4 GB skip-and-continue rule (TDD C5): FAT32
 * cannot hold a file of 4 GiB or more, so such a file is skipped
 * with a reason instead of stalling the whole job.
 */
object FilesystemInfo {

    /** FAT32 files cannot be 4 GiB or larger (4 GiB - 1 byte max). */
    const val FAT32_MAX_FILE_BYTES: Long = 4L * 1024 * 1024 * 1024

    /** Pure parser: the fstype of the longest mount-point prefix of [path]. */
    fun parseFstype(mountsText: String, path: String): String? {
        var bestMount = ""
        var bestType: String? = null
        for (line in mountsText.lines()) {
            val parts = line.split(" ")
            if (parts.size < 3) continue
            val mountPoint = parts[1].replace("\\040", " ")
            val fstype = parts[2]
            if (path == mountPoint || path.startsWith("$mountPoint/")) {
                if (mountPoint.length > bestMount.length) {
                    bestMount = mountPoint
                    bestType = fstype
                }
            }
        }
        return bestType
    }

    /** Best-effort fstype for [file]'s volume; null when undetectable. */
    fun fstypeFor(file: File): String? {
        return try {
            val mounts = File("/proc/mounts").readText()
            parseFstype(mounts, file.absolutePath)
        } catch (_: Exception) {
            null
        }
    }

    /** True when [file] lives on FAT32 and cannot hold [sizeBytes]. */
    fun tooLargeForFat32(file: File, sizeBytes: Long): Boolean {
        if (sizeBytes < FAT32_MAX_FILE_BYTES) return false
        return fstypeFor(file) == "vfat"
    }
}
