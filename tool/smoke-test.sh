#!/usr/bin/env bash
# Photo Backup emulator smoke test (run inside android-emulator-runner).
# Runs the in-process Compose instrumented tests
# (app/src/androidTest/.../SettingsScreenTest.kt +
#  BackupEngineInstrumentedTest.kt + NotificationTest.kt +
#  NotificationScreenshotTest.kt + ProCardScreenshotTest.kt) via
# connectedDebugAndroidTest — no uiautomator dumps, no adb UI
# driving, which proved unreliable on the slow GitHub Actions
# emulator (System UI ANRs, empty dumps for minutes at a time).
# (The notification screenshot tests DO use UiDevice, but only to
# open the shade and photograph it — no UI driving.)
#
# Evidence: everything lands in tool/smoke-screenshots/<run-id>/
# (run-id is unique per run, so nothing is ever overwritten):
#   - in-test screenshots (settings screen) pulled from the public
#     Pictures/PhotoBackup-shots/ directory
#   - screen-recorded video chunks of the whole run (best effort)
#   - smoke.log + the SMOKE lines from the JUnit XML
# Afterwards the workflow uploads tool/smoke-screenshots/ as an
# artifact (retention 30 days).
set -u
RUN_ID="${GITHUB_RUN_ID:-local}-$(date +%Y%m%d-%H%M%S)"
OUT="tool/smoke-screenshots/$RUN_ID"
mkdir -p "$OUT"
LOG="$OUT/smoke.log"
: > "$LOG"

say() { echo "[smoke] $*" | tee -a "$LOG"; }
say "run id: $RUN_ID"

adb wait-for-device
say "device ready: $(adb shell getprop ro.build.model | tr -d '\r') / $(adb shell getprop ro.build.version.release | tr -d '\r')"
say "waiting for boot_completed"
adb shell 'while [ "$(getprop sys.boot_completed)" != "1" ]; do sleep 2; done'
# The runner reports boot completed while the device is still
# unresponsive often enough that Gradle then fails with
# "Skipping device 'emulator-5554': Unknown API Level" — do not
# start the build until the API level is actually queryable.
say "waiting for device properties to become queryable (API level)"
API=""
for i in $(seq 1 36); do
  API=$(timeout 20 adb shell getprop ro.build.version.sdk 2>/dev/null | tr -d '\r')
  if [ "$API" = "35" ]; then
    say "device API level: $API"
    break
  fi
  sleep 5
done
if [ "$API" != "35" ]; then
  say "device never became queryable (last API level='$API') — emulator infra failure, retry the run"
  exit 2
fi
# boot_completed + a queryable API level is NOT enough: on some
# runs the system server is still coming up and the package/activity
# services are not registered yet, so the test APK install then
# fails with "cmd: Can't find service: package". Do not start Gradle
# until both services answer.
say "waiting for package + activity services to be registered"
SERVICES_OK=""
for i in $(seq 1 36); do
  PKG=$(adb shell service check package 2>/dev/null | tr -d '\r')
  ACT=$(adb shell service check activity 2>/dev/null | tr -d '\r')
  if echo "$PKG" | grep -q "found" && echo "$ACT" | grep -q "found"; then
    SERVICES_OK=1
    say "package + activity services ready ($PKG / $ACT)"
    break
  fi
  sleep 5
done
if [ -z "$SERVICES_OK" ]; then
  say "package/activity services never became available — emulator infra failure, retry the run"
  exit 2
fi
say "boot completed; letting the system settle (first-boot dexopt, ANRs)"
sleep 90
# Keep the display on for the whole run: in smoke run 43618997791
# the screen timed out mid-test (both app + dialog windows went
# invisible 17:19:55) while the Compose test waited behind an
# undismissed permission dialog, and the test then failed. Stay-on
# while charging + a long timeout so the display can never go dark
# under a slow test again.
adb shell svc power stayon true 2>&1 | tee -a "$LOG" || true
adb shell settings put system screen_off_timeout 1800000 2>&1 | tee -a "$LOG" || true
# Digital Wellbeing ANRs on first boot; absent on AOSP images —
# the error below is harmless there.
adb shell pm disable-user --user 0 com.google.android.apps.wellbeing 2>&1 | tee -a "$LOG" || true
# The Settings app ANRs on slow first boots; force-stop it so no
# dialog blocks the app from drawing.
adb shell am force-stop com.android.settings 2>&1 | tee -a "$LOG" || true

# --- Screen recording (best effort; never fails the run) ---
# screenrecord caps a single recording at 180s, so restart it in
# chunks for the whole (long) test. Killed when the test finishes.
RECORD_PID=""
if adb shell ls /system/bin/screenrecord >/dev/null 2>&1; then
  (
    n=1
    while true; do
      adb shell screenrecord --time-limit 170 "/data/local/tmp/smoke-video-$n.mp4" >> "$OUT/screenrecord.log" 2>&1 || break
      n=$((n + 1))
    done
  ) &
  RECORD_PID=$!
  say "screen recording started (background pid $RECORD_PID)"
else
  say "screenrecord not available on this emulator — video evidence skipped"
fi
stop_recording() {
  if [ -n "$RECORD_PID" ]; then
    kill "$RECORD_PID" 2>/dev/null || true
    # A chunk may still be recording; give it a moment, then stop it.
    sleep 3
    adb shell pkill screenrecord 2>/dev/null || true
    sleep 2
  fi
}

say "running instrumented smoke tests"
gradle connectedDebugAndroidTest --no-daemon 2>&1 | tee -a "$LOG"
RC=${PIPESTATUS[0]}
say "connectedDebugAndroidTest exit code: $RC"

# Infra flake: the emulator sometimes goes unresponsive during
# the (multi-minute) build, and Gradle then fails at install time
# with "Skipping device 'emulator-5554': Unknown API Level" /
# "No compatible devices connected" / "Cannot install split APKs
# with API level < 21" (ddmlib loses the device's API level when
# it cannot query it) — 0 tests ran, nothing about the app was
# exercised. Retry once after the device answers again instead of
# failing the run on it. The tests are idempotent, so a retry is safe.
if [ "$RC" != "0" ] && grep -q -E "Unknown API Level|No compatible devices|Cannot install split APKs|Failed to install split APK" "$LOG"; then
  say "infra flake detected (device unresponsive at install time) — retrying once"
  API=""
  for i in $(seq 1 36); do
    API=$(timeout 20 adb shell getprop ro.build.version.sdk 2>/dev/null | tr -d '\r')
    if [ "$API" = "35" ]; then
      say "device API level queryable again: $API"
      break
    fi
    sleep 5
  done
  if [ "$API" = "35" ]; then
    gradle connectedDebugAndroidTest --no-daemon 2>&1 | tee -a "$LOG"
    RC=${PIPESTATUS[0]}
    say "retry connectedDebugAndroidTest exit code: $RC"
  else
    say "device never became queryable again — emulator infra failure, retry the run"
  fi
fi

stop_recording

# --- Pull screen-recorded video chunks (written to /data/local/tmp:
# screenrecord cannot open files under /sdcard on API 35 —
# "Permission denied" — but /data/local/tmp is shell-writable) ---
adb shell ls /data/local/tmp/smoke-video-*.mp4 2>/dev/null | tr -d '\r' | while read -r f; do
  adb pull "$f" "$OUT/" 2>&1 | tee -a "$LOG" || true
done
ls -l "$OUT"/smoke-video-*.mp4 2>/dev/null | tee -a "$LOG" || say "no video chunks pulled"

# --- Pull in-test screenshots (public Pictures dir — see ScreenshotHelper.kt) ---
adb pull /sdcard/Pictures/PhotoBackup-shots "$OUT/screenshots" 2>&1 | tee -a "$LOG" || say "no in-test screenshots pulled"
find "$OUT/screenshots" -name "*.png" 2>/dev/null | tee -a "$LOG" || true

# --- Verdict: count the SMOKE lines + JUnit results ---
say "JUnit XML results:"
# AGP writes results to connected/debug/, not connected/.
grep -h "testsuite name" app/build/outputs/androidTest-results/connected/debug/*.xml 2>/dev/null | tee -a "$LOG" || say "no JUnit XML found"
say "SMOKE lines:"
# println() output lands in the per-test logcat files, not the JUnit XML.
grep -rh "System.out: SMOKE" app/build/outputs/androidTest-results/connected/debug/*/logcat-*.txt 2>/dev/null | sed 's/.*System.out: //' | tee -a "$LOG" || true

exit "$RC"
