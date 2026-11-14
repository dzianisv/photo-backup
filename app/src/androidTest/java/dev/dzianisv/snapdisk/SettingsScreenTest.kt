package dev.dzianisv.snapdisk

import android.Manifest
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.test.rule.GrantPermissionRule
import dev.dzianisv.snapdisk.backup.BackupPrefs
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule

/**
 * Smoke test for the settings screen — the app's only screen
 * (owner wireframe v1): From /sdcard/DCIM + folder picker,
 * To /disk/DCIM + folder picker, Start backup button, Pro card.
 */
class SettingsScreenTest {

    private val composeRule = createAndroidComposeRule<MainActivity>()

    // Permissions are granted up front (RuleChain: grant runs before
    // the activity launches) so no system permission dialog can appear
    // and cover the activity: on first launch MainActivity requests
    // both permissions, and in smoke run 43618997791 the
    // GrantPermissionsActivity stayed up undismissed for ~100 s while
    // the Compose test waited behind it, then the screen timed out and
    // the lookup failed with "No compose hierarchies found". The test
    // asserts the settings screen, not the permission flow — granting
    // here is environment setup, not a weakened assertion.
    @get:Rule
    val ruleChain: TestRule = RuleChain
        .outerRule(
            GrantPermissionRule.grant(
                Manifest.permission.POST_NOTIFICATIONS,
                Manifest.permission.READ_MEDIA_IMAGES,
            ),
        )
        .around(composeRule)

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
