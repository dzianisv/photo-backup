# SnapDisk — Play screenshots

Real screenshots from smoke-test runs only, never mockups.

## Present (v0.1.2, smoke run 43624496788, green)

1. `01-settings-defaults.png` — settings screen: From /sdcard/DCIM +
   To /disk/DCIM pickers, Start backup, Pro card. Viewed and
   verified 2026-11-14. Caption: "Pick your folders once, then one
   tap".
   Source: `tool/smoke-screenshots/43624496788-20261114-180942/screenshots/01-settings-defaults.png`
   in the run's `smoke-evidence` artifact.

## Present (smoke run 43644126583, green, 14/14 tests)

All four viewed and verified 2026-11-14. Source:
`tool/smoke-screenshots/43644126583-20261114-214808/screenshots/`
in the run's `smoke-evidence` artifact.

2. `02-offer.png` — offer notification in the shade: "External
   storage is connected. Backup photos?" [BACKUP] [CANCEL].
   Caption: "Plug in a disk, tap Backup".
   How it was produced: posted through the production
   `postOfferNotification` path (the call StorageReceiver makes on
   a mount broadcast) — the emulator has no USB mount event, so the
   mount trigger itself is not exercised, only the real
   notification, channel and actions.
3. `03-progress.png` — progress notification in the shade: bar +
   "7%, 872.0 KB/12.0 MB, 168.6 KB/s, ETA 1m" [PAUSE] [STOP].
   Caption: "Real progress, Pause and Stop".
   How it was produced: photographed mid-copy of a real 12 MiB
   file; the numbers are the live copy's measured values (live
   `.part` size + measured throughput), and the copy finished +
   verified after the shot.
4. `04-done.png` — done notification in the shade: "Backup
   complete — Backed up 3 photos (585.9 KB)". Caption: "Proof it
   copied — counts, not promises".
   How it was produced: a real 3-file copy (100 + 200 + 300 KB)
   finished first; the counts are the real totals.
5. `05-pro.png` — Pro card: auto-backup + unlimited, "Upgrade to
   Pro — $9.99 once". Caption: "Pro: auto-backup, unlimited size".
   How it was produced: the REAL SettingsScreen composable rendered
   with the billing-connected state (`proAvailable=true`) — the
   emulator has no Play Store, so MainActivity can never reach that
   state there (see BillingTest). The card art is the app's own UI
   (real strings, layout, button); the state is injected, not
   reached through the live billing flow. Replace with a shot
   through the live billing flow on a real device once the Play
   listing + `pro_unlock` product exist (owner-gated).

Notes on the shade shots (02/03/04): the Android System silent
notifications below SnapDisk ("Serial console enabled", "AT
Translated Set 2 keyboard configured") are emulator noise, not the
app. The emulator screenshots are interim until the Pixel 9a +
real-disk screenshots exist — the listing must say nothing a
real-disk test has not proven.

Source: the Emulator Smoke Test workflow's `smoke-evidence`
artifact (`tool/smoke-screenshots/<run-id>/screenshots/`), copied
here after the run is verified green.
