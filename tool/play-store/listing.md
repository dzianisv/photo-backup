# SnapDisk — Play Store listing

Status: DRAFT (2026-11-14). Publish is blocked on exactly one
owner step: create the $25 Play Console developer account (and grant
access). Everything else in this folder is publish-ready. The AAB
comes from the CI release workflow (`v*` tag); it is signed with the
CI throwaway keystore — the first production upload must use the
stable upload keystore in the owner's custody.

## Title (50 chars max)

SnapDisk – Photo Backup to USB (30 chars)

## Short description (80 chars max)

Back up photos & videos to a USB disk. One tap, no cloud. (57 chars)

## Full description (4000 chars max)

Plug in a USB disk. SnapDisk offers a backup in a notification —
tap Backup and walk away. Photos and videos copy oldest-first to
your disk, in the background, with a live progress notification
(percent, size, speed, time left) and Pause/Stop.

- One tap: disk connected → notification → Backup. No account,
  no cloud, no upload — everything stays on your devices.
- Oldest first: your earliest photos copy first, and the folder
  structure is kept (Camera, Screenshots, …).
- Honest progress: the notification shows real percent, copied /
  total size, speed and time left. Pause and resume any time.
- Proves it copied: every file is size-verified after copying.
  A bad file is retried once, then skipped with its reason — it
  never stalls the whole backup. A file too big for FAT32 is
  skipped and listed, not a dead end.
- Unplug mid-copy? The backup pauses with the full error and
  resumes where it stopped when the disk is back. Already-copied
  files are never copied twice.
- Errors show the full error with Copy — never one truncated line.

Free: manual backup, 5 GB per backup run.
SnapDisk Pro ($9.99, one-time): auto-backup when a disk is
connected + unlimited backup size. No subscription.

SnapDisk is for anyone with a phone full of photos and a USB disk
in a drawer: no cloud account, no subscription, no file-manager
maze. Plug in, tap once, trust the counts.

## Category

Tools (primary). Tags/keywords (from discovery research, to score):
photo backup to usb, backup photos to external drive, copy photos
to usb stick, photo backup app offline, backup dcim to otg.

## In-app product

- `pro_unlock` — SnapDisk Pro, one-time purchase, $9.99 USD.
  Auto-backup when a disk is connected + unlimited backup size.
  Wired in the app (Google Play Billing). Sandbox purchase NOT yet
  verified — needs this Play Console app + the product to exist.

## Creatives

- Icon: `icon-512.png` (512x512, generated from the launcher glyph
  by `generate_creatives.py`).
- Feature graphic: `feature-graphic-1024x500.png` (1024x500).
- Screenshots: `screenshots/` — real screenshots from smoke-test
  runs only, never mockups. (Filled after the first green smoke run:
  1. settings screen with From/To pickers, 2. offer notification,
  3. progress notification, 4. done notification with counts,
  5. Pro card.)
- Promo video: not yet — 30–60s of the core loop, after the first
  real-disk test.

## Privacy policy URL

`PRIVACY.md` in the repo root (collects nothing). Hosted URL needed
for the listing — publish at the repo's GitHub Pages or the product
site when the landing page ships.

## Data safety + content rating

See `data-safety.md` and `content-rating.md` in this folder.
