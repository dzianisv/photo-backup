package dev.dzianisv.snapdisk

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollTo
import androidx.test.platform.app.InstrumentationRegistry
import dev.dzianisv.snapdisk.backup.BackupPrefs
import dev.dzianisv.snapdisk.backup.BackupState
import dev.dzianisv.snapdisk.ui.SettingsScreen
import dev.dzianisv.snapdisk.ui.theme.PhotoBackupTheme
import org.junit.Rule
import org.junit.Test

/**
 * Play Store screenshot 05-pro: the Pro card with the "$9.99 once"
 * button — the state a real device reaches when Play Billing connects
 * and the pro_unlock product exists.
 *
 * The emulator has no Play Store (see BillingTest: the billing
 * connection degrades gracefully and Pro stays off), so MainActivity
 * can never reach proAvailable=true there. This test renders the REAL
 * SettingsScreen composable — same code MainActivity shows, same
 * strings, same layout — with the billing-connected state injected.
 * The card art is the app's own UI, not a drawing; the state is
 * labelled as injected in the screenshots README. A shot taken
 * through the live billing flow needs a Play listing + real device
 * (owner-gated) and replaces this one when it exists.
 */
class ProCardScreenshotTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun proCardShowsPriceWhenBillingConnected() {
        val ctx = InstrumentationRegistry.getInstrumentation().targetContext
        composeRule.setContent {
            PhotoBackupTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    SettingsScreen(
                        fromLabel = BackupPrefs.DEFAULT_FROM_LABEL,
                        toLabel = BackupPrefs.DEFAULT_TO_LABEL,
                        onPickFrom = {},
                        onPickTo = {},
                        state = BackupState(),
                        notificationsEnabled = true,
                        isPro = false,
                        proAvailable = true,
                        onUpgradeToPro = {},
                        onStart = {},
                        onPause = {},
                        onResume = {},
                        onStop = {},
                    )
                }
            }
        }
        composeRule.onNodeWithText(ctx.getString(R.string.pro_title)).performScrollTo()
        composeRule.onNodeWithText(ctx.getString(R.string.pro_pitch)).assertIsDisplayed()
        composeRule.onNodeWithText(ctx.getString(R.string.pro_price)).assertIsDisplayed()
        captureScreenshot(composeRule, "05-pro")
        println("SMOKE pro card: 05-pro shot — real Pro card, billing-connected state ($9.99 once)")
    }
}
