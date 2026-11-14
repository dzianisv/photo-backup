package dev.dzianisv.snapdisk

import dev.dzianisv.snapdisk.util.FilesystemInfo
import dev.dzianisv.snapdisk.util.FormatUtils
import dev.dzianisv.snapdisk.util.SpeedWindow
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class FormatUtilsTest {

    @Test
    fun formatBytesUnits() {
        assertEquals("0 B", FormatUtils.formatBytes(0))
        assertEquals("500 B", FormatUtils.formatBytes(500))
        assertEquals("1.0 KB", FormatUtils.formatBytes(1024))
        assertEquals("1.5 KB", FormatUtils.formatBytes(1536))
        assertEquals("25.0 GB", FormatUtils.formatBytes(25L * 1024 * 1024 * 1024))
        assertEquals("100.0 GB", FormatUtils.formatBytes(100L * 1024 * 1024 * 1024))
    }

    @Test
    fun formatProgressMatchesWireframeLine() {
        // Wireframe: "25%, 25GB/100GB, 125mb/s, ETA 34m."
        val total = 100L * 1024 * 1024 * 1024
        val copied = 25L * 1024 * 1024 * 1024
        val speed = 125.0 * 1024 * 1024
        val line = FormatUtils.formatProgress(copied, total, speed)
        assertEquals("25%, 25.0 GB/100.0 GB, 125.0 MB/s, ETA 10m", line)
    }

    @Test
    fun formatEtaCalculatingUntilSpeedExists() {
        // TDD P3: ETA hidden until 5s of copying.
        assertEquals("Calculating…", FormatUtils.formatEta(0, 100, 0.0))
        assertEquals("Calculating…", FormatUtils.formatEta(0, 0, 100.0))
    }

    @Test
    fun formatDurationSeconds() {
        assertEquals("45s", FormatUtils.formatDurationSeconds(45))
        assertEquals("34m", FormatUtils.formatDurationSeconds(34 * 60))
        assertEquals("1h 5m", FormatUtils.formatDurationSeconds(65 * 60))
    }
}

class SpeedWindowTest {

    @Test
    fun speedIsZeroUntilFiveSecondsOfSamples() {
        val window = SpeedWindow()
        window.add(0, 0)
        window.add(1_000, 1_000_000)
        window.add(3_000, 3_000_000)
        assertEquals(0.0, window.speedBytesPerSec(3_000), 0.001)
    }

    @Test
    fun speedIsRollingFiveSecondAverage() {
        val window = SpeedWindow()
        window.add(0, 0)
        window.add(1_000, 1_000_000)
        window.add(5_000, 5_000_000)
        assertEquals(1_000_000.0, window.speedBytesPerSec(5_000), 0.001)
        // A later burst does not leak into the window once it is old.
        window.add(11_000, 11_000_000)
        assertEquals(1_000_000.0, window.speedBytesPerSec(11_000), 0.001)
    }
}

class FilesystemInfoTest {

    private val mounts = """
        /dev/block/vda / ext4 rw,relatime 0 0
        /dev/block/sda1 /storage/ABCD-1234 vfat rw,relatime 0 0
        /dev/fuse /storage/emulated fuse rw,nosuid,nodev 0 0
    """.trimIndent()

    @Test
    fun parseFstypeMatchesLongestMountPrefix() {
        assertEquals("vfat", FilesystemInfo.parseFstype(mounts, "/storage/ABCD-1234/DCIM/a.jpg"))
        assertEquals("fuse", FilesystemInfo.parseFstype(mounts, "/storage/emulated/0/DCIM/a.jpg"))
        assertEquals("ext4", FilesystemInfo.parseFstype(mounts, "/system/bin/sh"))
        assertEquals(null, FilesystemInfo.parseFstype(mounts, "/nonexistent/path"))
    }

    @Test
    fun fat32LimitIsFourGib() {
        assertEquals(4L * 1024 * 1024 * 1024, FilesystemInfo.FAT32_MAX_FILE_BYTES)
    }
}
