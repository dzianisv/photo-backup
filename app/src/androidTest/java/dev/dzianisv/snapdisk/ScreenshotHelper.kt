package dev.dzianisv.snapdisk

import android.content.ContentValues
import android.graphics.Bitmap
import android.os.Build
import android.provider.MediaStore
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.onRoot
import androidx.test.platform.app.InstrumentationRegistry

/**
 * Captures the current Compose root as a PNG into the public
 * Pictures/PhotoBackup-shots/ directory (adb can pull it off the
 * emulator; app-private storage cannot be read on API 30+).
 * Best-effort evidence: it never fails the test.
 */
fun captureScreenshot(
    rule: SemanticsNodeInteractionsProvider,
    name: String,
) {
    try {
        val bitmap = rule.onRoot().captureToImage().asAndroidBitmap()
        val ctx = InstrumentationRegistry.getInstrumentation().targetContext
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "$name.png")
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/PhotoBackup-shots")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.Images.Media.IS_PENDING, 1)
            }
        }
        val uri = ctx.contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            values,
        ) ?: run {
            println("SMOKE screenshot FAILED for $name: MediaStore insert returned null")
            return
        }
        ctx.contentResolver.openOutputStream(uri)?.use { out ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ctx.contentResolver.update(
                uri,
                ContentValues().apply {
                    put(MediaStore.Images.Media.IS_PENDING, 0)
                },
                null,
                null,
            )
        }
        println("SMOKE screenshot saved: $name.png (${bitmap.width}x${bitmap.height})")
    } catch (e: Throwable) {
        println("SMOKE screenshot FAILED for $name: ${e.message}")
    }
}
