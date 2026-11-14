package dev.dzianisv.snapdisk

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import dev.dzianisv.snapdisk.backup.BackupPrefs
import dev.dzianisv.snapdisk.backup.ProPrefs
import dev.dzianisv.snapdisk.billing.BillingManager
import dev.dzianisv.snapdisk.service.BackupService
import dev.dzianisv.snapdisk.service.NotificationHelper
import dev.dzianisv.snapdisk.service.StorageReceiver
import dev.dzianisv.snapdisk.ui.SettingsScreen
import dev.dzianisv.snapdisk.ui.theme.PhotoBackupTheme

/**
 * The app's only screen: settings (From / To folders with SAF pickers),
 * backup status, and the Pro upgrade.
 * All backup progress lives in the notification; this screen mirrors it.
 */
class MainActivity : ComponentActivity() {

    private lateinit var prefs: BackupPrefs
    private lateinit var proPrefs: ProPrefs
    private lateinit var billing: BillingManager
    private var fromLabel by mutableStateOf(BackupPrefs.DEFAULT_FROM_LABEL)
    private var toLabel by mutableStateOf(BackupPrefs.DEFAULT_TO_LABEL)
    private var isPro by mutableStateOf(false)
    private var proAvailable by mutableStateOf(false)
    private var notificationsEnabled by mutableStateOf(true)
    private val storageReceiver = StorageReceiver()

    // One launcher for both runtime permissions. Two separate
    // RequestPermission launchers fired back-to-back in onCreate were
    // dropped by the framework ("Can request only one set of
    // permissions at a time", smoke run 43618997791 logcat 17:18:11),
    // so READ_MEDIA_IMAGES was never requested on launch — and the
    // notifications dialog stayed up undismissed, blocking the
    // settings screen in the smoke test. RequestMultiplePermissions
    // sequences them through one system flow; on a real device both
    // permissions are still asked on first launch, as before.
    private val requestPermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            refreshNotificationsEnabled()
        }

    private val pickFromFolder =
        registerForActivityResult(ActivityResultContracts.OpenDocumentTree()) { uri: Uri? ->
            if (uri != null) {
                takeTreePermission(uri)
                prefs.fromTreeUri = uri.toString()
                fromLabel = prefs.fromLabel(this)
            }
        }

    private val pickToFolder =
        registerForActivityResult(ActivityResultContracts.OpenDocumentTree()) { uri: Uri? ->
            if (uri != null) {
                takeTreePermission(uri)
                prefs.toTreeUri = uri.toString()
                toLabel = prefs.toLabel(this)
            }
        }

    private fun takeTreePermission(uri: Uri) {
        try {
            contentResolver.takePersistableUriPermission(
                uri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION or
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION,
            )
        } catch (_: SecurityException) {
            // The picker may grant a transient permission only; the URI
            // still works for this session.
        }
    }

    private fun refreshNotificationsEnabled() {
        notificationsEnabled = NotificationHelper.areNotificationsEnabled(this)
    }

    private fun serviceIntent(action: String): Intent =
        Intent(this, BackupService::class.java).setAction(action)

    private fun startServiceAction(action: String) {
        ContextCompat.startForegroundService(this, serviceIntent(action))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = BackupPrefs(this)
        proPrefs = ProPrefs(this)
        isPro = proPrefs.isPro
        fromLabel = prefs.fromLabel(this)
        toLabel = prefs.toLabel(this)
        NotificationHelper.ensureChannels(this)
        refreshNotificationsEnabled()

        billing = BillingManager(this).apply {
            listener = object : BillingManager.Listener {
                override fun onProChanged(isProNow: Boolean) {
                    // Qualified: inside BillingManager.apply, bare `isPro`
                    // would resolve to BillingManager's read-only val.
                    this@MainActivity.isPro = isProNow
                }

                override fun onBillingUnavailable(message: String) {
                    proAvailable = false
                }
            }
            startConnection { connected ->
                if (connected) queryProProduct { product -> proAvailable = product != null }
            }
        }

        // ACTION_MEDIA_MOUNTED cannot be received from the manifest on
        // modern Android — register dynamically while the app runs.
        val filter = StorageReceiver.intentFilter()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(storageReceiver, filter, RECEIVER_NOT_EXPORTED)
        } else {
            registerReceiver(storageReceiver, filter)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val missing = listOf(
                Manifest.permission.POST_NOTIFICATIONS,
                Manifest.permission.READ_MEDIA_IMAGES,
            ).filter {
                ContextCompat.checkSelfPermission(this, it) !=
                    PackageManager.PERMISSION_GRANTED
            }
            if (missing.isNotEmpty()) {
                requestPermissions.launch(missing.toTypedArray())
            }
        }

        // If a removable disk is already mounted when the app opens,
        // surface the same offer the mount broadcast would have posted.
        if (StorageReceiver.hasExternalVolume(this)) {
            NotificationHelper.postOfferNotification(this)
        }

        setContent {
            PhotoBackupTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    val state by BackupService.stateFlow.collectAsState()
                    SettingsScreen(
                        fromLabel = fromLabel,
                        toLabel = toLabel,
                        onPickFrom = { pickFromFolder.launch(null) },
                        onPickTo = { pickToFolder.launch(null) },
                        state = state,
                        notificationsEnabled = notificationsEnabled,
                        isPro = isPro,
                        proAvailable = proAvailable,
                        onUpgradeToPro = { billing.launchPurchase(this@MainActivity) },
                        onStart = { startServiceAction(BackupService.ACTION_START) },
                        onPause = { startServiceAction(BackupService.ACTION_PAUSE) },
                        onResume = { startServiceAction(BackupService.ACTION_RESUME) },
                        onStop = { startServiceAction(BackupService.ACTION_STOP) },
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        try { unregisterReceiver(storageReceiver) } catch (_: Exception) { /* already gone */ }
        billing.destroy()
        super.onDestroy()
    }
}
