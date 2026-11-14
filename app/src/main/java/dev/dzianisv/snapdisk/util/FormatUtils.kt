package dev.dzianisv.snapdisk.util

import java.util.Locale
import kotlin.math.max

object FormatUtils {

    fun formatBytes(bytes: Long): String {
        if (bytes < 0) return "?"
        val units = arrayOf("B", "KB", "MB", "GB", "TB")
        var value = bytes.toDouble()
        var unit = 0
        while (value >= 1024 && unit < units.lastIndex) {
            value /= 1024
            unit++
        }
        return if (unit == 0) "$bytes B"
        else String.format(Locale.US, "%.1f %s", value, units[unit])
    }

    /** "25%, 25.0 GB/100.0 GB, 125.0 MB/s, ETA 34m" — the wireframe's progress line. */
    fun formatProgress(
        copiedBytes: Long,
        totalBytes: Long,
        speedBytesPerSec: Double,
    ): String {
        val percent = if (totalBytes > 0) (copiedBytes * 100 / totalBytes).toInt() else 0
        val eta = formatEta(copiedBytes, totalBytes, speedBytesPerSec)
        return "$percent%, ${formatBytes(copiedBytes)}/${formatBytes(totalBytes)}, " +
            "${formatBytes(speedBytesPerSec.toLong())}/s, ETA $eta"
    }

    /**
     * ETA from the remaining bytes and the rolling speed. "Calculating…"
     * until there is a real speed (TDD P3: hidden until 5s of copying).
     */
    fun formatEta(copiedBytes: Long, totalBytes: Long, speedBytesPerSec: Double): String {
        if (totalBytes <= 0 || speedBytesPerSec <= 0) return "Calculating…"
        val remaining = max(0L, totalBytes - copiedBytes)
        val seconds = (remaining / speedBytesPerSec).toLong()
        return formatDurationSeconds(seconds)
    }

    fun formatDurationSeconds(seconds: Long): String {
        if (seconds < 60) return "${seconds}s"
        val minutes = seconds / 60
        if (minutes < 60) return "${minutes}m"
        val hours = minutes / 60
        return "${hours}h ${minutes % 60}m"
    }

    fun formatSpeed(bytesPerSecond: Double): String = "${formatBytes(bytesPerSecond.toLong())}/s"
}

/**
 * Rolling speed over the last 5 seconds (TDD P2/P3): samples of
 * (time, bytes copied); speed is bytes-per-second across the window,
 * or 0 until 5 seconds of samples exist (ETA stays "Calculating…").
 */
class SpeedWindow {
    private val samples = ArrayDeque<Pair<Long, Long>>()

    fun add(nowMs: Long, totalBytesCopied: Long) {
        samples.addLast(nowMs to totalBytesCopied)
        // Keep the samples inside the last 5 s, plus the single sample
        // just before the window, so a span >= 5 s stays computable:
        // pruning the boundary sample would leave one sample and the
        // speed would collapse to 0 exactly when the window fills.
        while (samples.size > 2 && nowMs - samples[1].first > 5_000) {
            samples.removeFirst()
        }
    }

    fun speedBytesPerSec(nowMs: Long): Double {
        if (samples.size < 2) return 0.0
        val first = samples.first()
        val last = samples.last()
        val spanSec = (last.first - first.first) / 1000.0
        if (spanSec < 5.0) return 0.0
        return (last.second - first.second) / spanSec
    }

    fun reset() = samples.clear()
}
