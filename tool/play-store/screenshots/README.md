# SnapDisk — Play screenshots

Real screenshots from smoke-test runs only, never mockups.
Filled after the first green smoke run.

Planned (5, in listing order):
1. `01-settings.png` — settings screen: From /sdcard/DCIM + To
   /disk/DCIM pickers, Start backup, Pro card. Caption: "Pick your
   folders once, then one tap".
2. `02-offer.png` — offer notification: "External storage is
   connected. Backup photos?" [Backup] [Cancel]. Caption: "Plug in a
   disk, tap Backup".
3. `03-progress.png` — progress notification: bar + "25%, 25GB/100GB,
   125MB/s, ETA 34m." [Pause] [Stop]. Caption: "Real progress, Pause
   and Stop".
4. `04-done.png` — done notification with real counts. Caption:
   "Proof it copied — counts, not promises".
5. `05-pro.png` — Pro card: auto-backup + unlimited, $9.99 once.
   Caption: "Pro: auto-backup, unlimited size".

Source: the Emulator Smoke Test workflow's `smoke-evidence`
artifact (`tool/smoke-screenshots/<run-id>/screenshots/`), copied
here after the run is verified green. The emulator screenshots are
interim until the Pixel 9a + real-disk screenshots exist — the
listing must say nothing a real-disk test has not proven.
