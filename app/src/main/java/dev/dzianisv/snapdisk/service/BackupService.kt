package dev.dzianisv.snapdisk.service

import android.app.ForegroundServiceStartNotAllowedException
import android.app.NotificationManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ServiceInfo
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.os.storage.StorageManager
import dev.dzianisv.snapdisk.backup.BackupEngine
import dev.dzianisv.snapdisk.backup.BackupError
import dev.dzianisv.snapdisk.backup.BackupPrefs
import dev.dzianisv.snapdisk.backup.BackupState
import dev.dzianisv.snapdisk.backup.BackupStatus
import dev.dzianisv.snapdisk.backup.CopyControl
import dev.dzianisv.snapdisk.backup.CopyResult
import dev.dzianisv.snapdisk.backup.PhotoItem
import dev.dzianisv.snapdisk.backup.PhotoQuery
import dev.dzianisv.snapdisk.backup.ProPrefs
import dev.dzianisv.snapdisk.backup.SkippedFile
import dev.dzianisv.snapdisk.util.FilesystemInfo
import dev.dzianisv.snapdisk.util.SpeedWindow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File

/**
 * Foreground service that copies photos + videos oldest-first,
 * internal → external, mirroring the source folder tree.
 * Progress (percent, copied/total, rolling 5s speed, ETA) + Pause/Stop
 * live in the notification; the settings screen observes [state] for
 * the same data. Notification updates are throttled to ≤1/sec (TDD P4).
 *
 * One bad file never stalls the job (TDD C5/E7): it is retried once
 * on a verification failure, then recorded in the skipped list with
 * its reason and the job continues. A file ≥4 GiB on FAT32 is
 * skipped up front with its reason.
 *
 * Disk detach mid-copy: the write fails, the service pauses with the
 * FULL error state (resumable via Resume) instead of dropping the job.
 * Resuming on a DIFFERENT disk is refused (TDD X3) — the job never
 * writes to the wrong disk.
 * Pause is honoured inside the engine (it blocks between buffer
 * writes until resumed) so a paused copy resumes the same file,
 * byte-exact, from its `.part` file. Stop deletes the `.part` file
 * (TDD S1) and keeps finished files.
 */
class BackupService : Service() {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private var job: Job? = null
    private val control = CopyControl()
    private val speedWindow = SpeedWindow()
    /**
     * Set when `startForeground` is refused because the 6-hour dataSync
     * budget is exhausted (WL-34): the run must end in the ERROR state
     * the refusal produced, not overwrite it with STOPPED.
     */
    @Volatile
    private var fgsStartDenied: Boolean = false

    // What was already copied in this run, so a resume after detach or
    // pause does not re-scan or re-copy from scratch (TDD O4: the scan
    // is a snapshot frozen at job start).
    private var pending: List<PhotoItem> = emptyList()
    private var pendingIndex: Int = 0
    private var skipped: MutableList<SkippedFile> = mutableListOf()

    private val detachReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (_stateFlow.value.status == BackupStatus.COPYING) {
                onDestinationDetached(StorageReceiver.detachedError())
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        NotificationHelper.ensureChannels(this)
        val filter = IntentFilter(StorageReceiver.ACTION_DESTINATION_DETACHED)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(detachReceiver, filter, RECEIVER_NOT_EXPORTED)
        } else {
            registerReceiver(detachReceiver, filter)
        }
    }

    override fun onDestroy() {
        try { unregisterReceiver(detachReceiver) } catch (_: Exception) { /* already gone */ }
        job?.cancel()
        scope.cancel()
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> startBackup()
            ACTION_PAUSE -> pauseBackup()
            ACTION_RESUME -> resumeBackup()
            ACTION_STOP -> stopBackup()
        }
        return START_NOT_STICKY
    }

    /**
     * Android 15+ dataSync 6-hour budget exhausted (WL-34; targetSdk 36,
     * so the limit applies). The system calls this on API 35+ and the
     * service has a few seconds to stop, or the app crashes with a
     * `RemoteServiceException` ("did not stop within its timeout").
     * Same clean paused state as a disk detach: the engine stops at
     * the next buffer boundary (control.timedOut), the in-flight file
     * keeps its `.part` for resume, finished files are kept, and the
     * user resumes from the same state — after opening the app, which
     * resets Android's 6-hour timer. Must NOT call `publish()` here:
     * after onTimeout the service is no longer foreground and the
     * budget is exhausted, so `startForeground` would throw — post a
     * plain notification instead, then stop promptly.
     */
    override fun onTimeout(startId: Int, fgsType: Int) {
        val current = _stateFlow.value
        if (current.status == BackupStatus.COPYING || current.status == BackupStatus.SCANNING) {
            control.paused = true
            control.timedOut = true
            val error = BackupError(
                summary = "Backup paused — Android's 6-hour limit for background backup was reached",
                detail = "Operation: background backup (dataSync foreground service)\n" +
                    "Android 15 and later allow a dataSync foreground service to run " +
                    "for 6 hours in 24 hours (this app targets SDK 36, so the limit applies). " +
                    "The limit was reached, so Android stopped the backup.\n" +
                    "Already-copied files are kept, and the file in progress keeps its " +
                    "partial copy — nothing is lost and nothing is reported as done.\n" +
                    "To continue: open SnapDisk, then tap Resume. Opening the app resets " +
                    "Android's 6-hour timer; resuming without opening the app first fails " +
                    "until the timer resets.",
            )
            _stateFlow.update {
                it.copy(status = BackupStatus.PAUSED, error = error, pausedByDetach = false)
            }
            getSystemService(NotificationManager::class.java)?.notify(
                NotificationHelper.NOTIFICATION_ID_PROGRESS,
                NotificationHelper.buildProgressNotification(this, _stateFlow.value),
            )
        }
        // A few seconds to stop, or the system crashes the app.
        stopSelf()
    }

    private fun startForegroundWith(state: BackupState) {
        val notification = NotificationHelper.buildProgressNotification(this, state)
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                startForeground(
                    NotificationHelper.NOTIFICATION_ID_PROGRESS,
                    notification,
                    ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC,
                )
            } else {
                startForeground(NotificationHelper.NOTIFICATION_ID_PROGRESS, notification)
            }
        } catch (e: ForegroundServiceStartNotAllowedException) {
            // The 6-hour dataSync budget is exhausted and the user has not
            // foregrounded the app since (which resets the timer) — e.g.
            // Resume tapped in the notification after a timeout-pause.
            // Surface as a full ERROR state, never a crash (WL-34).
            fgsStartDenied = true
            control.stopped = true
            val error = BackupError(
                summary = "Could not restart the backup — Android's 6-hour background limit is still in effect",
                detail = "Operation: start dataSync foreground service\n" +
                    "Error: ${e::class.qualifiedName}: ${e.message}\n" +
                    "Android 15 and later allow 6 hours of dataSync background work in " +
                    "24 hours. Open SnapDisk first — opening the app resets the timer — " +
                    "then tap Resume. Already-copied files are kept.",
            )
            _stateFlow.update { it.copy(status = BackupStatus.ERROR, error = error) }
            getSystemService(NotificationManager::class.java)?.notify(
                NotificationHelper.NOTIFICATION_ID_PROGRESS,
                NotificationHelper.buildProgressNotification(this, _stateFlow.value),
            )
            stopSelf()
        }
    }

    private fun publish(state: BackupState) {
        _stateFlow.update { state }
        val manager = getSystemService(NOTIFICATION_SERVICE) as android.app.NotificationManager
        if (state.status == BackupStatus.DONE || state.status == BackupStatus.ERROR ||
            state.status == BackupStatus.LIMIT_REACHED
        ) {
            // Detach from the foreground FIRST, then post the
            // terminal notification: a notification posted while
            // the service is still foreground is removed when the
            // service stops (smoke run 44199483316: after DONE +
            // stopSelf() the shade was empty — active=[] after a
            // 10 s poll — so the user never saw "Backup complete").
            // Posted after stopForeground(false) it is a plain
            // notification and outlives the service.
            stopForeground(false)
            manager.notify(
                NotificationHelper.NOTIFICATION_ID_PROGRESS,
                NotificationHelper.buildProgressNotification(this, state),
            )
        } else {
            startForegroundWith(state)
        }
    }

    private fun fail(error: BackupError) {
        val detached = isDetachError(error)
        if (detached) control.paused = true
        publish(
            _stateFlow.value.copy(
                status = if (detached) BackupStatus.PAUSED else BackupStatus.ERROR,
                error = error,
                pausedByDetach = detached,
                skippedFiles = skipped.toList(),
            ),
        )
        stopSelf()
    }

    fun startBackup() {
        if (job?.isActive == true) return
        control.paused = false
        control.stopped = false
        control.timedOut = false
        fgsStartDenied = false
        pending = emptyList()
        pendingIndex = 0
        skipped = mutableListOf()
        speedWindow.reset()
        _stateFlow.update { BackupState(status = BackupStatus.SCANNING) }
        job = scope.launch { runBackup() }
    }

    fun pauseBackup() {
        if (_stateFlow.value.status != BackupStatus.COPYING) return
        control.paused = true
        publish(_stateFlow.value.copy(status = BackupStatus.PAUSED))
    }

    fun resumeBackup() {
        val current = _stateFlow.value
        if (current.status != BackupStatus.PAUSED &&
            current.status != BackupStatus.ERROR &&
            current.status != BackupStatus.LIMIT_REACHED
        ) return
        control.paused = false
        control.stopped = false
        control.timedOut = false
        fgsStartDenied = false
        publish(current.copy(status = BackupStatus.COPYING, error = null, pausedByDetach = false))
        // startForeground refused (WL-34: 6-hour budget exhausted) —
        // publish() already set the ERROR state; do not launch a job.
        if (fgsStartDenied) return
        if (job?.isActive != true) {
            job = scope.launch { runBackup() }
        }
    }

    fun stopBackup() {
        control.stopped = true
        control.paused = false
        // No running job (e.g. Stop tapped on a timeout-paused
        // notification after the service was destroyed): nothing will
        // stop the service for us, so stop now instead of lingering.
        if (job?.isActive != true) stopSelf()
    }

    /** Called when the destination disk goes away mid-copy. */
    fun onDestinationDetached(error: BackupError) {
        control.paused = true
        _stateFlow.update {
            it.copy(status = BackupStatus.PAUSED, error = error, pausedByDetach = true)
        }
        publish(_stateFlow.value)
    }

    private fun recordSkipped(item: PhotoItem, reason: String, error: BackupError? = null) {
        skipped += SkippedFile(item.displayName, reason)
        // Keep the first failure's full detail visible in the UI.
        if (error != null && _stateFlow.value.error == null) {
            _stateFlow.update { it.copy(error = error) }
        }
        pendingIndex++
    }

    private suspend fun runBackup() {
        val prefs = BackupPrefs(this)
        val proPrefs = ProPrefs(this)
        try {
            if (pending.isEmpty()) {
                publish(_stateFlow.value.copy(status = BackupStatus.SCANNING))
                pending = enumeratePhotos(prefs)
                pendingIndex = 0
                if (pending.isEmpty()) {
                    // Empty source: an error, not a crash and not a fake success (TDD T6).
                    fail(
                        BackupError(
                            summary = "No photos found to back up",
                            detail = "Operation: scan source folder\n" +
                                "Source: ${prefs.fromLabel(this)}\n" +
                                "The source folder holds no photos or videos. " +
                                "Choose a different From folder, or take some photos first.",
                        ),
                    )
                    return
                }
                // Remember which disk this job writes to (TDD X3).
                destinationBaseDir(prefs)?.let { base ->
                    volumeUuidFor(base)?.let { uuid -> proPrefs.jobVolumeUuid = uuid }
                }
            } else {
                // Continuing a paused job: refuse a DIFFERENT disk (TDD X3).
                val storedUuid = proPrefs.jobVolumeUuid
                if (storedUuid != null) {
                    val currentUuid = destinationBaseDir(prefs)?.let { volumeUuidFor(it) }
                    if (currentUuid != null && currentUuid != storedUuid) {
                        fail(
                            BackupError(
                                summary = "This is a different disk",
                                detail = "Operation: resume backup after reconnect\n" +
                                    "The backup started on another disk (volume $storedUuid) " +
                                    "and cannot continue on this one (volume $currentUuid). " +
                                    "Reconnect the original disk and tap Resume, or tap " +
                                    "Start backup to begin a new backup on this disk.",
                            ),
                        )
                        return
                    }
                }
            }
            val totalBytes = pending.sumOf { it.sizeBytes }
            // startForeground refused while publishing above (WL-34) —
            // the ERROR state is already set; do not scan or copy.
            if (fgsStartDenied) return
            // Completed files are exactly pending[0..<pendingIndex]:
            // each was skipped (size-verified) or copied + verified.
            // Deriving it from the list — not from the last published
            // progress value — keeps a resume byte-exact even when the
            // in-flight file's partial progress was last reported.
            var copiedBytes = pending.take(pendingIndex).sumOf { it.sizeBytes }
            var copiedFiles = pendingIndex
            publish(
                _stateFlow.value.copy(
                    status = BackupStatus.COPYING,
                    totalBytes = totalBytes,
                    totalFiles = pending.size,
                    copiedBytes = copiedBytes,
                    copiedFiles = copiedFiles,
                    skippedFiles = skipped.toList(),
                ),
            )
            val runStartMs = System.currentTimeMillis()
            var lastReportMs = runStartMs
            speedWindow.reset()
            speedWindow.add(runStartMs, copiedBytes)

            while (pendingIndex < pending.size) {
                if (control.stopped) {
                    // Stop: delete the .part file (TDD S1), keep finished files.
                    // Except when startForeground was refused (WL-34): the
                    // ERROR state above is the answer — do not overwrite it.
                    if (fgsStartDenied) {
                        stopSelf()
                        return
                    }
                    pending.getOrNull(pendingIndex)?.let { item ->
                        destinationFile(prefs, item)?.let { dest ->
                            BackupEngine.partFileFor(dest).delete()
                        }
                    }
                    publish(
                        _stateFlow.value.copy(
                            status = BackupStatus.STOPPED,
                            copiedBytes = copiedBytes,
                            copiedFiles = copiedFiles,
                            skippedFiles = skipped.toList(),
                        ),
                    )
                    stopSelf()
                    return
                }
                val item = pending[pendingIndex]
                val destFile = destinationFile(prefs, item)
                if (destFile == null) {
                    fail(
                        BackupError(
                            summary = "No destination folder chosen",
                            detail = "Operation: resolve destination file for ${item.displayName}\n" +
                                "The destination folder has not been picked yet. " +
                                "Open SnapDisk and choose the To folder.",
                            fileName = item.displayName,
                        ),
                    )
                    return
                }
                // Free size cap: Pro is unlimited (ProPrefs).
                if (!proPrefs.isPro &&
                    copiedBytes + item.sizeBytes > ProPrefs.FREE_BACKUP_BYTES_LIMIT
                ) {
                    publish(
                        _stateFlow.value.copy(
                            status = BackupStatus.LIMIT_REACHED,
                            copiedBytes = copiedBytes,
                            copiedFiles = copiedFiles,
                            skippedFiles = skipped.toList(),
                        ),
                    )
                    stopSelf()
                    return
                }
                // FAT32 cannot hold a file ≥4 GiB — skip it with its
                // reason and continue, never stall the job (TDD C5).
                if (FilesystemInfo.tooLargeForFat32(destFile, item.sizeBytes)) {
                    recordSkipped(
                        item,
                        "File is larger than 4 GB and the disk is FAT32 — use exFAT",
                    )
                    continue
                }
                if (BackupEngine.shouldSkip(destFile, item.sizeBytes)) {
                    copiedBytes += item.sizeBytes
                    copiedFiles++
                    pendingIndex++
                    continue
                }
                val beforeFileBytes = copiedBytes
                val result = copyOneFile(item, destFile) { fileCopied ->
                    val nowMs = System.currentTimeMillis()
                    speedWindow.add(nowMs, beforeFileBytes + fileCopied)
                }
                when (result) {
                    is CopyResult.Skipped,
                    is CopyResult.Copied -> {
                        if (result is CopyResult.Copied) {
                            copiedBytes = beforeFileBytes + result.bytes
                        } else {
                            copiedBytes += item.sizeBytes
                        }
                        copiedFiles++
                        pendingIndex++
                    }
                    is CopyResult.Paused -> {
                        copiedBytes = beforeFileBytes + result.bytesCopied
                        // Timeout-pause (WL-34): onTimeout already published
                        // the PAUSED state and stopped the service. Do NOT
                        // publish here — startForeground would throw with
                        // the budget exhausted. Just exit quietly.
                        if (control.timedOut) return
                        publish(
                            _stateFlow.value.copy(
                                status = BackupStatus.PAUSED,
                                copiedBytes = copiedBytes,
                                copiedFiles = copiedFiles,
                                currentFileName = item.displayName,
                                skippedFiles = skipped.toList(),
                            ),
                        )
                    }
                    is CopyResult.Stopped -> {
                        BackupEngine.partFileFor(destFile).delete()
                        publish(
                            _stateFlow.value.copy(
                                status = BackupStatus.STOPPED,
                                copiedBytes = beforeFileBytes + result.bytesCopied,
                                copiedFiles = copiedFiles,
                                skippedFiles = skipped.toList(),
                            ),
                        )
                        stopSelf()
                        return
                    }
                    is CopyResult.Failed -> {
                        if (isDetachError(result.error)) {
                            fail(result.error)
                            return
                        }
                        // One bad file never stalls the job: record it
                        // with its full error and continue (TDD C2/E7).
                        recordSkipped(item, result.error.summary, result.error)
                        continue
                    }
                }
                // Throttled progress publish (≤1/sec, TDD P4).
                val nowMs = System.currentTimeMillis()
                if (nowMs - lastReportMs >= 1000) {
                    publish(
                        _stateFlow.value.copy(
                            status = if (control.paused) BackupStatus.PAUSED else BackupStatus.COPYING,
                            copiedBytes = copiedBytes,
                            copiedFiles = copiedFiles,
                            currentFileName = item.displayName,
                            speedBytesPerSec = speedWindow.speedBytesPerSec(nowMs),
                            skippedFiles = skipped.toList(),
                        ),
                    )
                    lastReportMs = nowMs
                }
            }
            publish(
                _stateFlow.value.copy(
                    status = BackupStatus.DONE,
                    copiedBytes = copiedBytes,
                    copiedFiles = copiedFiles,
                    totalBytes = copiedBytes,
                    speedBytesPerSec = 0.0,
                    currentFileName = null,
                    skippedFiles = skipped.toList(),
                ),
            )
            stopSelf()
        } catch (t: Throwable) {
            fail(
                BackupError.fromThrowable(
                    summary = "Backup failed: ${t.message ?: t::class.simpleName}",
                    throwable = t,
                    operation = "backup run",
                ),
            )
        }
    }

    /**
     * Copy one file, retrying once with a fresh stream on a
     * size-verification failure (TDD C2). A read failure on the
     * source is reported as Failed (the caller records it as skipped).
     * [onFileProgress] receives the bytes copied within this file
     * (including any resumed offset) for the rolling speed window.
     */
    private fun copyOneFile(
        item: PhotoItem,
        destFile: File,
        onFileProgress: (bytesCopiedInFile: Long) -> Unit,
    ): CopyResult {
        var attempt = 0
        while (true) {
            val input = try {
                PhotoQuery.openInput(this, item)
            } catch (t: Throwable) {
                return CopyResult.Failed(
                    BackupError.fromThrowable(
                        summary = "Could not read ${item.displayName}",
                        throwable = t,
                        fileName = item.displayName,
                        operation = "open source file ${item.contentUri}",
                    ),
                )
            } ?: return CopyResult.Failed(
                BackupError(
                    summary = "Could not read ${item.displayName}",
                    detail = "Operation: open source file\nFile: ${item.displayName}\n" +
                        "Error: ContentResolver returned a null stream for ${item.contentUri}",
                    fileName = item.displayName,
                ),
            )
            val result = input.use { stream ->
                BackupEngine.copyWithResume(
                    input = stream,
                    destination = destFile,
                    expectedSizeBytes = item.sizeBytes,
                    control = control,
                    onProgress = onFileProgress,
                )
            }
            if (result is CopyResult.Failed && BackupEngine.isVerifyFailure(result.error) &&
                attempt == 0
            ) {
                attempt++
                continue
            }
            return result
        }
    }

    /** Base directory of the To folder, or null when no To folder is set. */
    private fun destinationBaseDir(prefs: BackupPrefs): File? {
        val treeUriString = prefs.toTreeUri ?: return null
        return try {
            val uri = Uri.parse(treeUriString)
            val treeId = uri.pathSegments.getOrNull(1) ?: return null
            val decoded = Uri.decode(treeId)
            val colon = decoded.indexOf(':')
            if (colon < 0) return null
            val volume = decoded.substring(0, colon)
            val path = decoded.substring(colon + 1)
            val base = if (volume == "primary") "/storage/emulated/0" else "/storage/$volume"
            File("$base/$path")
        } catch (_: Exception) {
            null
        }
    }

    /**
     * Destination file for [item], mirroring the source tree under the
     * To folder (TDD C6). SAF tree URIs (content://.../tree/<volume>%3A<path>)
     * are mapped back to /storage/<volume>/<path> so the copy can use
     * plain files with resume + size verification. Returns null when no
     * To folder is set or its tree URI cannot be mapped to a path.
     */
    private fun destinationFile(prefs: BackupPrefs, item: PhotoItem): File? {
        val base = destinationBaseDir(prefs) ?: return null
        return if (item.relativePath.isEmpty()) {
            File(base, item.displayName)
        } else {
            File(File(base, item.relativePath), item.displayName)
        }
    }

    private fun volumeUuidFor(dir: File): String? {
        return try {
            val storage = getSystemService(StorageManager::class.java) ?: return null
            storage.getStorageVolume(dir)?.uuid
        } catch (_: Exception) {
            null
        }
    }

    private fun enumeratePhotos(prefs: BackupPrefs): List<PhotoItem> {
        val fromUri = prefs.fromTreeUri
        return if (fromUri != null) {
            PhotoQuery.listTreeFiles(this, Uri.parse(fromUri))
        } else {
            PhotoQuery.queryMediaStoreOldestFirst(this)
        }
    }

    /** Heuristic: I/O errors that mean the disk went away, not a bad file. */
    internal fun isDetachError(error: BackupError): Boolean {
        val text = (error.summary + "\n" + error.detail).lowercase()
        return DETACH_KEYWORDS.any { text.contains(it) }
    }

    companion object {
        const val ACTION_START = "dev.dzianisv.snapdisk.action.START"
        const val ACTION_PAUSE = "dev.dzianisv.snapdisk.action.PAUSE"
        const val ACTION_RESUME = "dev.dzianisv.snapdisk.action.RESUME"
        const val ACTION_STOP = "dev.dzianisv.snapdisk.action.STOP"

        private val DETACH_KEYWORDS = listOf(
            "no such device",
            "transport endpoint is not connected",
            "device not found",
            "not connected",
            "i/o error",
        )

        private val _stateFlow = MutableStateFlow(BackupState())
        val stateFlow: StateFlow<BackupState> = _stateFlow.asStateFlow()
    }
}
