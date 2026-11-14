# SnapDisk — Work Log

The only task tracker for this product. One row per task: what, who,
state, evidence, date.

## 2026-11-14 — v0.1.2 shipped (smoke green, first verified version)

- Owner: Implementation + Publish lead. State: DONE.
  Evidence: repo `dzianisv/photo-backup`, tag `v0.1.2`,
  APK https://github.com/dzianisv/photo-backup/releases/download/v0.1.2/app-release.apk
  (11,535,007 bytes; aapt2: dev.dzianisv.snapdisk, versionCode 3,
  versionName 0.1.2, minSdk 24, targetSdk 36).
  CI: PR #2 (commits b0a9a63 + 5d16085, merge 1324c75; Android PR
  first failed run 43622100612 on missing androidx.test:rules, then
  green 17:42:52Z after 5d16085), branch smoke 43622646593 success
  (10/10, 0 failures), Release 43624496774 success, tag Smoke
  43624496788 success (13m59s; 10/10, 0 failures — BackupEngine 4
  + Billing 2 + Notification 3 + Settings 1, from the run's
  `smoke-evidence` artifact). Correction: the "20 instrumented
  tests" count above double-counted branch + tag runs, and the
  per-suite split (BackupEngine 10, Notification 4, Settings 2,
  Billing 3) was wrong — each run has 10 tests with the split
  above, verified from the JUnit XML.
  What v0.1.2 fixes over v0.1.1 (PR #2, merged 1324c75): the pause
  race (engine re-checks paused/stopped after `input.read()` and
  holds the chunk) and the double permission launchers (one
  `RequestMultiplePermissions` call); instrumented pause test gets
  the 200 ms settle; SettingsScreenTest grants both permissions up
  front; smoke-test.sh keeps the emulator display on.
  Screenshots: real `01-settings-defaults.png` from the v0.1.2
  smoke artifact copied to `tool/play-store/screenshots/` and
  viewed/verified; 4 notification screenshots still to capture.
  v0.1.1 stays released but was never smoke-verified: its smoke
  run 43621462440 attempt 2 ran all 10 tests and failed the same 2
  (pause race + settings screen — JUnit XML in the run's
  smoke-evidence artifact), so "boot flake twice, no test ran" was
  wrong for attempt 2. v0.1.2 is the current shipped version.
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
  Smoke 43621462440 FAILED — attempt 2 (rerun) ran all 10
  instrumented tests: 8 passed, 2 failed (pauseResumeAndStop +
  settingsScreenShowsDefaultFoldersStartAndPro, same as v0.1.0;
  JUnit XML in the run's smoke-evidence artifact). The earlier
  "both attempts failed on the boot flake, no test ran" note was
  wrong for attempt 2; attempt 1's failure mode was not verified.
  No green tag smoke for v0.1.1: it is released but NOT
  smoke-verified; v0.1.2 is the first smoke-verified release.
  The 2 real failures (found by v0.1.0 smoke run 43618997791):
  (1) pause race — the engine wrote one 4 KB chunk read while
  paused (expected 28672, got 32768); (2) MainActivity fired two
  permission launchers back-to-back, the second was dropped, the
  notifications dialog blocked the settings screen (`No compose
  hierarchies found`). Both fixed in PR #2 (merge 1324c75); v0.1.2
  (branch smoke 43622646593 10/10 + tag smoke 43624496788 10/10)
  is green.
  v0.1.0 was tagged, then DELETED (never announced): its release run
  43618997754 failed lintVitalRelease (registerForActivityResult
  needs Fragment >= 1.3.0 — fixed by adding fragment-ktx 1.8.5),
  and its smoke run 43618997791 ran all 10 tests — 8 passed, 2
  failed on real assertions (pause race, expected 28672 got 32768;
  settings screen "No compose hierarchies found" after 154.5 s),
  not a boot flake. Build fixes on the way to green: kotlin-jvm/android plugin
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
  Keyword scoring DONE Nov 14 (commit 07f7171, CI 43626786426
  green): 7 queries scored in `tool/growth/searches.md`, winners
  added to `tool/play-store/listing.md`. Re-score after launch.
- PRD + TDD + implementation spec + project doc DONE Nov 14
  (Notion, verified by re-fetch — links in the product file).
- Follow-ups: NTFS read-only detection (needs real-disk test),
  first article due Nov 26.

## 2026-11-14 — Landing page live (seo-geo lane)

- Owner: Product Lead agent. State: DONE (page live, verified by fetch).
  Live URL: https://dzianisv.github.io/photo-backup/ (GitHub Pages,
  main branch, /docs folder; enabled via API after merge).
  What shipped: docs/index.html (static, semantic HTML, one H1,
  title 30 chars, meta description 128 chars, tagline 8 words,
  real v0.1.2 settings screenshot above the fold, pricing shown
  Free + Pro $9.99 one-time, CTA "Get started free"), docs/robots.txt
  (AI crawlers allowed by name), docs/sitemap.xml, docs/llms.txt +
  docs/llms-full.txt, JSON-LD SoftwareApplication + FAQPage
  (validated by parse), icon + feature graphic copied to docs/.
  Copy matches tool/play-store/listing.md; no invented numbers,
  ratings, or download counts.
  Evidence: PR #3 (commit 48ba788, merge 1ae0e7f, merged
  2026-11-14T20:31:31Z); CI run 43637755510 build pass (1m0s);
  live fetch after Pages enable: index 200 with title + CTA
  present, robots.txt / sitemap.xml / llms.txt / screenshot all 200.
  Landing-done checklist: page live at canonical URL ✓; title <60
  and meta description <160 ✓; robots.txt, sitemap.xml, llms.txt
  present and correct ✓; schema validated by JSON parse (Rich
  Results Test not run — needs no account, still unrun) ◐; page
  renders without JS ✓ (verified over local HTTP + live fetch).
  STILL OPEN: sitemap submitted to Google Search Console and Bing
  Webmaster Tools — blocked, needs the owner's Google account;
  do not submit anything that needs his credentials. Rich Results
  Test validation unrun. Re-score keywords after launch.
