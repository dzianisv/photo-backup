# SnapDisk — directory submissions (WL-30)

Week 2 plan item (directories in the first week, social-marketing lane).
Researched 2026-11-15 by the hourly supervisor. Sources: Convert264
WL-30 (`tool/growth/directories.md` in dzianisv/h264-converter — structure
and rules mirrored here), SnapDisk `tool/growth/competitors.md`
(verified Nov 14), `tool/growth/searches.md`, `tool/play-store/listing.md`.

## Submission copy (use verbatim everywhere)

- **Name:** SnapDisk
- **URL:** https://dzianisv.github.io/photo-backup/
- **Tagline (52 chars):** Free on-device photo backup to a USB disk — no cloud
- **Short description (296 chars):** SnapDisk backs up photos and videos
  to a USB disk, oldest first, in the background. Plug in, tap Backup,
  walk away — live progress with Pause/Stop, every file verified. No
  cloud, no account, no ads, no internet permission. Free: 5 GB per run;
  Pro ($9.99 once) adds auto-backup and unlimited size.
- **Long description (496 chars):** SnapDisk is a free Android app that
  backs up your photos and videos to a USB disk — no cloud, no account,
  no ads, no internet permission. Plug in a disk, tap Backup in the
  notification, and your photos copy oldest-first in the background, with
  live progress (percent, size, speed, time left) and Pause/Stop. Every
  file is size-verified after copying; interrupted backups resume where
  they stopped. Free: manual backup, 5 GB per run. Pro ($9.99, one-time):
  auto-backup on connect + unlimited size.
- **Category:** Tools (Play) / Backup & Sync (AlternativeTo)
- **Tags:** photo backup, usb backup, otg backup, offline backup, no cloud
- **Logo/icon:** `tool/play-store/icon-512.png` (512×512, verified on disk)
- **AlternativeTo "alternative to" targets (from competitors.md +
  searches.md, verified Nov 14):** Photos to USB: OTG Backup (Akshay C P,
  `com.arzdrz.photos_to_usb`), Buffalo Photo Backup
  (`jp.buffalo.photobackup`), VaultDrive: USB Photo Backup, PhotoSaver
  Phone-to-Drive (ClevX), SanDisk app (`com.sandisk.smz2`), MeBackup

## Status

| Directory | Cost | Account needed | Status 2026-11-15 |
| --- | --- | --- | --- |
| AlternativeTo | Free ($5 priority review — do not pay) | Yes — free account, sign-in required to add a product | BLOCKED — no owner account; needs his sign-in |
| Product Hunt | Free | Yes — personal account; new accounts wait ≥1 week; 1 launch / 6 months; Tue–Thu 12:01am PT | PLANNED — needs his account + launch timing; do not launch until the Play listing exists (a Play link converts better than the GitHub APK) |
| Show HN | Free | Yes — HN account with history; restricted for new users since Mar 2026 | PLANNED — needs his HN account |
| Indie Hackers | Free | Yes — account | PLANNED — needs his account |
| BetaList | Paid only (no free option since 2026) + own domain required (github.io subdomain rejected) | — | RULED OUT — paid and our landing is a github.io subdomain |
| DevHunt | Free listing (nofollow); $19 launch / $49 boosted | Yes — GitHub login; dev-tools only | WEAK FIT — consumer Android app, not a dev tool; free queue ~6 months; revisit only if a GitHub-login route opens |
| SaaSHub | Free to submit | Yes — account | WEAK FIT — SaaS directory, not an Android-app directory |
| F-Droid | Free | GitLab account for the fdroiddata MR | NOT POSSIBLE AS-IS — verified Nov 15: `app/build.gradle.kts:72` depends on `libs.play.billing.ktx` (Google Play Billing, proprietary). A `foss` flavor without billing would be needed; flavor work deferred until Play is live (F-Droid is a side channel, not the goal); still needs a GitLab account + MR (owner). Do NOT claim F-Droid is possible as-is |
| Uneed / Microlaunch / Fazier / TinyLaunch | Free queues (weeks–months out) | Yes — account on each | NOT STARTED — same account gate as above |

## Facts used (all verified against the repo, 2026-11-15)

- v0.1.2, applicationId `dev.dzianisv.snapdisk` (never change it),
  minSdk 24 — `app/build.gradle.kts`.
- Free: manual backup, oldest-first background copy, size-verified,
  resume, progress notification with Pause/Stop, 5 GB per run
  (`ProPrefs.FREE_BACKUP_BYTES_LIMIT = 5L * 1024 * 1024 * 1024`,
  enforced in `BackupService.kt`).
- Pro: one-time $9.99, auto-backup on connect + unlimited size
  (`tool/growth/DECISIONS.md`, Decision 2).
- No ads, no account, no cloud, no internet permission
  (`app/src/main/AndroidManifest.xml` declares no INTERNET permission).
- Store title: "SnapDisk – Photo Backup to USB"
  (`tool/play-store/listing.md`).
- No download counts, no ratings, no search-volume claims anywhere
  in this file — the app is not on Play yet.

## Rule for this lane

- Never pay for a listing, fast-track, or boost without the owner's
  explicit approval (framework rule: never spend his money).
- Every directory above needs an owner account except the ruled-out ones.
  The one action only he can take: create (or hand over saved logins for)
  AlternativeTo + Product Hunt accounts. Everything else in this file is
  ready to paste the day those exist.
- Do not submit the same product twice to one directory.
- Nothing in this file has been submitted anywhere — copy only, until
  the owner accounts exist.
