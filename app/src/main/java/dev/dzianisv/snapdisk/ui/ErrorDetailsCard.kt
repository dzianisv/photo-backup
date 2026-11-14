package dev.dzianisv.snapdisk.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.dzianisv.snapdisk.BuildConfig
import dev.dzianisv.snapdisk.R
import dev.dzianisv.snapdisk.backup.BackupError

/**
 * Error display (Convert264 WL-17 lesson): the row shows a one-line
 * plain-language summary; the COMPLETE technical detail lives behind an
 * explicit "Show details" affordance — selectable, scrollable, with
 * Copy. Never truncated, never one line with an ellipsis.
 */
@Composable
fun ErrorDetailsCard(error: BackupError, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    Card(modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = stringResource(R.string.error_title))
            Text(
                text = error.summary,
                modifier = Modifier.padding(top = 8.dp),
            )
            TextButton(onClick = { expanded = !expanded }) {
                Text(
                    text = if (expanded) stringResource(R.string.hide_details)
                    else stringResource(R.string.show_details),
                )
            }
            if (expanded) {
                SelectionContainer {
                    Text(
                        text = error.detail,
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState())
                            .padding(top = 4.dp),
                    )
                }
                Button(
                    onClick = { copyError(context, error) },
                    modifier = Modifier.padding(top = 8.dp),
                ) {
                    Text(text = stringResource(R.string.copy_error))
                }
            }
        }
    }
}

// TDD G2: the clipboard holds the summary + FULL detail + app version +
// Android version + device model, untruncated.
private fun copyError(context: Context, error: BackupError) {
    val clipboard = context.getSystemService(ClipboardManager::class.java) ?: return
    val text = buildString {
        appendLine("SnapDisk ${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})")
        appendLine("Android ${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT}), ${Build.MANUFACTURER} ${Build.MODEL}")
        appendLine()
        appendLine(error.summary)
        appendLine()
        append(error.detail)
    }
    clipboard.setPrimaryClip(ClipData.newPlainText("SnapDisk error", text))
    Toast.makeText(context, R.string.error_copied, Toast.LENGTH_SHORT).show()
}
