# SnapDisk — Work Log

The only task tracker for this product. One row per task: what, who,
state, evidence, date.

## 2026-11-14 — v0.1.0 MVP shipped

- Owner: Implementation + Publish lead. State: DONE (GitHub Release
  v0.1.0, emulator smoke green — see release notes for run IDs).
  Evidence: repo `dzianisv/photo-backup`, tag `v0.1.0`.
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
