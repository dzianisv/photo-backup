package dev.dzianisv.snapdisk.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.dzianisv.snapdisk.R
import dev.dzianisv.snapdisk.backup.BackupState
import dev.dzianisv.snapdisk.backup.BackupStatus
import dev.dzianisv.snapdisk.util.FormatUtils

/**
 * The app's only screen (owner wireframe v1): From / To folder rows
 * with folder pickers, then the backup status + progress, then the
 * Pro upgrade. Standard Material 3 components only.
 */
@Composable
fun SettingsScreen(
    fromLabel: String,
    toLabel: String,
    onPickFrom: () -> Unit,
    onPickTo: () -> Unit,
    state: BackupState,
    notificationsEnabled: Boolean,
    isPro: Boolean,
    proAvailable: Boolean,
    onUpgradeToPro: () -> Unit,
    onStart: () -> Unit,
    onPause: () -> Unit,
    onResume: () -> Unit,
    onStop: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val busy = state.status == BackupStatus.COPYING ||
        state.status == BackupStatus.SCANNING ||
        state.status == BackupStatus.PAUSED
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
    ) {
        Text(text = stringResource(R.string.app_name))

        // Notifications denied → no offer pops on connect (TDD N5/A8):
        // say so here instead of failing silently.
        if (!notificationsEnabled) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = stringResource(R.string.notifications_denied_banner))
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = stringResource(R.string.from))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OutlinedTextField(
                value = fromLabel,
                onValueChange = {},
                readOnly = true,
                singleLine = true,
                enabled = !busy,
                modifier = Modifier.weight(1f),
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = onPickFrom, enabled = !busy) {
                Icon(
                    imageVector = Icons.Default.FolderOpen,
                    contentDescription = stringResource(R.string.choose_from_folder),
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = stringResource(R.string.to))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OutlinedTextField(
                value = toLabel,
                onValueChange = {},
                readOnly = true,
                singleLine = true,
                enabled = !busy,
                modifier = Modifier.weight(1f),
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = onPickTo, enabled = !busy) {
                Icon(
                    imageVector = Icons.Default.FolderOpen,
                    contentDescription = stringResource(R.string.choose_to_folder),
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        when (state.status) {
            BackupStatus.COPYING, BackupStatus.PAUSED -> {
                LinearProgressIndicator(
                    progress = { state.percent / 100f },
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = FormatUtils.formatProgress(
                        state.copiedBytes, state.totalBytes, state.speedBytesPerSec,
                    ),
                )
                state.currentFileName?.let {
                    Text(text = it, modifier = Modifier.padding(top = 4.dp))
                }
                if (state.pausedByDetach) {
                    Text(
                        text = stringResource(R.string.paused_disk_removed),
                        modifier = Modifier.padding(top = 8.dp),
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row {
                    if (state.status == BackupStatus.COPYING) {
                        OutlinedButton(onClick = onPause) {
                            Text(text = stringResource(R.string.pause))
                        }
                    } else {
                        Button(onClick = onResume) {
                            Text(text = stringResource(R.string.resume))
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(onClick = onStop) {
                        Text(text = stringResource(R.string.stop))
                    }
                }
                state.error?.let {
                    Spacer(modifier = Modifier.height(16.dp))
                    ErrorDetailsCard(error = it)
                }
            }
            BackupStatus.DONE -> {
                Text(
                    text = if (state.skippedFiles.isEmpty()) {
                        stringResource(
                            R.string.backup_done_detail,
                            state.copiedFiles,
                            FormatUtils.formatBytes(state.copiedBytes),
                        )
                    } else {
                        stringResource(
                            R.string.backup_done_detail_skipped,
                            state.copiedFiles,
                            FormatUtils.formatBytes(state.copiedBytes),
                            state.skippedFiles.size,
                        )
                    },
                )
                SkippedFilesList(state = state)
                Spacer(modifier = Modifier.height(12.dp))
                Button(onClick = onStart) {
                    Text(text = stringResource(R.string.start_backup))
                }
            }
            BackupStatus.ERROR -> {
                state.error?.let { ErrorDetailsCard(error = it) }
                SkippedFilesList(state = state)
                Spacer(modifier = Modifier.height(12.dp))
                Row {
                    Button(onClick = onResume) {
                        Text(text = stringResource(R.string.resume))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(onClick = onStart) {
                        Text(text = stringResource(R.string.start_backup))
                    }
                }
            }
            BackupStatus.LIMIT_REACHED -> {
                Text(text = stringResource(R.string.limit_reached_title))
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(
                        R.string.limit_reached_detail,
                        FormatUtils.formatBytes(state.copiedBytes),
                    ),
                )
                Spacer(modifier = Modifier.height(12.dp))
                ProCard(
                    isPro = isPro,
                    proAvailable = proAvailable,
                    onUpgradeToPro = onUpgradeToPro,
                )
                Spacer(modifier = Modifier.height(12.dp))
                TextButton(onClick = onResume) {
                    Text(text = stringResource(R.string.resume))
                }
            }
            BackupStatus.STOPPED -> {
                Text(
                    text = stringResource(
                        R.string.backup_stopped_detail,
                        state.copiedFiles,
                        FormatUtils.formatBytes(state.copiedBytes),
                    ),
                )
                SkippedFilesList(state = state)
                Spacer(modifier = Modifier.height(12.dp))
                Button(onClick = onStart) {
                    Text(text = stringResource(R.string.start_backup))
                }
            }
            else -> {
                Text(text = stringResource(R.string.idle_explainer))
                Spacer(modifier = Modifier.height(12.dp))
                Button(onClick = onStart) {
                    Text(text = stringResource(R.string.start_backup))
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        ProCard(
            isPro = isPro,
            proAvailable = proAvailable,
            onUpgradeToPro = onUpgradeToPro,
        )
    }
}

@Composable
private fun SkippedFilesList(state: BackupState) {
    if (state.skippedFiles.isEmpty()) return
    Spacer(modifier = Modifier.height(12.dp))
    Text(text = stringResource(R.string.skipped_files_title))
    for (skipped in state.skippedFiles) {
        Text(
            text = "${skipped.name} — ${skipped.reason}",
            modifier = Modifier.padding(top = 4.dp),
        )
    }
}

@Composable
private fun ProCard(
    isPro: Boolean,
    proAvailable: Boolean,
    onUpgradeToPro: () -> Unit,
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = stringResource(R.string.pro_title))
            Spacer(modifier = Modifier.height(8.dp))
            if (isPro) {
                Text(text = stringResource(R.string.pro_unlocked))
            } else {
                Text(text = stringResource(R.string.pro_pitch))
                Spacer(modifier = Modifier.height(12.dp))
                if (proAvailable) {
                    Button(onClick = onUpgradeToPro) {
                        Text(text = stringResource(R.string.pro_price))
                    }
                } else {
                    Text(text = stringResource(R.string.pro_unavailable))
                }
            }
        }
    }
}
