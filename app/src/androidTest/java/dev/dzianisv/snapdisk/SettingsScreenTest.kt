package dev.dzianisv.snapdisk

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import dev.dzianisv.snapdisk.backup.BackupPrefs
import org.junit.Rule
import org.junit.Test

/**
 * Smoke test for the settings screen — the app's only screen
 * (owner wireframe v1): From /sdcard/DCIM + folder picker,
 * To /disk/DCIM + folder picker, Start backup button, Pro card.
 */
class SettingsScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun settingsScreenShowsDefaultFoldersStartAndPro() {
        composeRule.onNodeWithText("SnapDisk").assertIsDisplayed()
        composeRule.onNodeWithText("From").assertIsDisplayed()
        composeRule.onNodeWithText(BackupPrefs.DEFAULT_FROM_LABEL).assertIsDisplayed()
        composeRule.onNodeWithText("To").assertIsDisplayed()
        composeRule.onNodeWithText(BackupPrefs.DEFAULT_TO_LABEL).assertIsDisplayed()
        composeRule.onNodeWithContentDescription("Choose source folder").assertExists()
        composeRule.onNodeWithContentDescription("Choose destination folder").assertExists()
        composeRule.onNodeWithText("Start backup").assertIsDisplayed()
        composeRule.onNodeWithText("SnapDisk Pro").assertIsDisplayed()
        captureScreenshot(composeRule, "01-settings-defaults")
        println("SMOKE settings screen: defaults visible, Start backup + Pro card displayed")
    }
}
