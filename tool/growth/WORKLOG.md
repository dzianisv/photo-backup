# SnapDisk — Work Log

The only task tracker for this product. One row per task: what, who,
state, evidence, date.

## 2026-11-14 — v0.1.2 shipped (smoke green, first verified version)

- Owner: Implementation + Publish lead. State: DONE.
  Evidence: repo `dzianisv/photo-backup`, tag `v0.1.2`,
  APK https://github.com/dzianisv/photo-backup/releases/download/v0.1.2/app-release.apk
  (11,535,007 bytes; aapt2: dev.dzianisv.snapdisk, versionCode 3,
  versionName 0.1.2, minSdk 24, targetSdk 36).
  CI: Release 43624496774 success, Smoke 43624496788 success
  (13m59s; 20 instrumented tests, 0 failures, 0 errors, from the
  run's `smoke-evidence` artifact — BackupEngine 10 + Notification
  4 + Settings 2 + Billing 3 + 1 more, all green).
  What v0.1.2 fixes over v0.1.1 (PR #2, merged 1324c75): the pause
  race (engine re-checks paused/stopped after `input.read()` and
  holds the chunk) and the double permission launchers (one
  `RequestMultiplePermissions` call); instrumented pause test gets
  the 200 ms settle; SettingsScreenTest grants both permissions up
  front; smoke-test.sh keeps the emulator display on.
  Screenshots: real `01-settings-defaults.png` from the v0.1.2
  smoke artifact copied to `tool/play-store/screenshots/` and
  viewed/verified; 4 notification screenshots still to capture.
  v0.1.1 stays released but was never smoke-verified (boot flake
  twice); v0.1.2 is the current shipped version.
  Blocked (owner): Play Console account ($25 — the one remaining
  Play gate), stable upload keystore, PostHog project, Sentry DSN,
  real-device proof (Pixel 9a + real USB disk), sandbox Pro
  purchase (needs the Play Console app + product).

## 2026-11-14 — v0.1.1 MVP shipped (v0.1.0 deleted, unannounced)

- Owner: Implementation + Publish lead. State: DONE (GitHub Release
  v0.1.1, PR + Release green; emulator smoke run 43621462440 —
  verdict below).
  Evidence: repo `dzianisv/photo-backup`, tag `v0.1.1`,
  APK https://github.com/dzianisv/photo-backup/releases/download/v0.1.1/app-release.apk
  (11,535,003 bytes; aapt2: dev.dzianisv.snapdisk, versionCode 2,
  versionName 0.1.1, minSdk 24, targetSdk 36; apksigner verify OK).
  CI: PR 43621458782 success, Release 43621462565 success,
  Smoke 43621462440 FAILED — both attempts (1 + rerun) on the
  emulator boot flake (`adb: device offline`, emulator never came
  online; no instrumented test ran on the tag). No green tag smoke
  yet: v0.1.1 is released but NOT smoke-verified.
  The same code (v0.1.0) DID run the instrumented tests once, in
  smoke run 43618997791, and found 2 real failures, fixed in open
  PR #2 (`fix/smoke-pause-and-permissions`): (1) pause race — the
  engine wrote one 4 KB chunk read while paused (expected 28672,
  got 32768); (2) MainActivity fired two permission launchers
  back-to-back, the second was dropped, the notifications dialog
  blocked the settings screen (`No compose hierarchies found`).
  PR #2 is green (PR run 43622332641) and its own smoke dispatch
  43622646593 on the fixed branch PASSED. Next step (not done by
  this lead — PR #2's session owns it): merge PR #2, tag v0.1.2
  from main, verify its smoke run. Screenshots for the Play
  package are still pending a green tag smoke run.
  v0.1.0 was tagged, then DELETED (never announced): its release run
  43618997754 failed lintVitalRelease (registerForActivityResult
  needs Fragment >= 1.3.0 — fixed by adding fragment-ktx 1.8.5),
  and its smoke run 43618997791 failed on an emulator boot flake
  (adb exit 1, emulator never reachable — no test ran, not an app
  bug). Build fixes on the way to green: kotlin-jvm/android plugin
  collision (compose plugin already registers the `kotlin`
  extension — compose plugin only, no kotlinOptions), 4 compile
  errors (themes.xml Material3 parent, `isPro` scope in
  BillingManager.apply, BillingManager Context not stored, missing
  ServiceInfo import), AppCompat `?attr/colorControlNormal` tint in
  notification icons (removed; paths already white), broken vector
  XML after the tint removal, SpeedWindow window-boundary prune,
  FilesystemInfo root-mount `/` never matching, racy pause test
  (in-flight chunk after the snapshot).
  Scope: settings screen (From/To SAF pickers, defaults
  /sdcard/DCIM + /disk/DCIM), mount detection (USB attach +
  MEDIA_MOUNTED), offer notification (Backup/Cancel), foreground
  service copy with progress notification (%, copied/total, rolling
  5s speed, ETA) + Pause/Stop, oldest-first (photos + videos,
  MediaStore DATE_TAKEN asc), skip-if-exists + `.part` resume +
  size verification, verify retry-once + skip-and-continue, FAT32
  >4 GB skip, disk-detach pause with full error + resume, different
  -disk refusal, full-error screen with Copy (incl. version +
  Android version), free 5 GB cap, Pro billing wired ($9.99
  `pro_unlock`, auto-backup on connect).
  Decisions recorded: SnapDisk name + $9.99 Pro (Product Lead,
  delegated by owner, 2026-11-14).
  Product-framework push: products/photo-backup.md pushed cleanly
  (commit 5712671, no force-push).
  Blocked (owner): Play Console account ($25 — the one remaining
  Play gate), stable upload keystore, PostHog project, Sentry DSN,
  real-device proof (Pixel 9a + real USB disk), sandbox Pro
  purchase (needs the Play Console app + product).

## Open

- Discovery done: competitor study + name check committed
  (`tool/growth/competitors.md` — discovery lead, Nov 14).
  Keyword scoring (searches.md) still to do.
- PRD: draft alongside this build (owner 24h window).
- Follow-ups: NTFS read-only detection (needs real-disk test),
  keyword scoring due Nov 20, first article due Nov 26.
