# Photo Backup — Competitor Study (Nov 14, 2026)

Scope: photo-backup / USB-backup apps on Google Play — apps that copy
photos from a phone to a USB disk / SD card with no cloud. Cloud-only
backup apps (Google Photos, IDrive, Asurion, Hivenet) are out of scope
except where noted. All figures verified Nov 14, 2026 from the live
Google Play listing text (via browser.open) and AppBrain / androidrank
mirrors of Play data. Play listing fetches show description + reviews
but not the rating/install header, so rating and install band come from
AppBrain/androidrank snapshots of the same Play data — labelled as
such per app. This is search evidence, not a live Play Console check.

## Top 7

### 1. Photos to USB: OTG Backup — the closest direct competitor
- Developer: Akshay C P · Package: com.arzdrz.photos_to_usb
- Rating: **no ratings yet (0 reviews)** · Installs: **100+ band, ~270 total** (AppBrain)
- Launched: **October 2026** (40 days old) · Last update: **Oct 1, 2026** (Play) / Oct 8, 2026 (AppBrain)
- Price: free, with ads; one-time purchase removes ads.
- What it does: plug in a pen drive, one tap copies the whole gallery
  (camera, WhatsApp, every folder); next time only new photos; resume
  after accidental unplug, half-copied files never left looking
  finished; byte-for-byte original quality, location kept; folders
  sorted by month; FAT32/exFAT supported, NTFS read-only warning,
  >4 GB files on FAT32 flagged and skipped.
- Weakness to beat: **it is brand new and unrated** — the niche is
  open right now. Its own listing admits "Keep the app open while it
  copies. The screen stays on by itself" — no background copy, no
  notification-led flow. That is exactly our edge (background
  ForegroundService + notification progress).
- 1-star complaints: none yet (0 reviews). Watch this listing —
  it is the one most likely to grow into our direct rival.
- Screenshots bar: clean Play listing copy, one-tap story, month
  folders. Beat it with: real background copy (no screen-on
  requirement), oldest-first order shown in the notification, and a
  progress notification with Pause/Stop.

### 2. SANDISK app (ex Memory Zone) — the giant
- Developer: SanDisk Corporation · Package: com.sandisk.smz2
- Rating: **~4.7–4.8★ from ~35k ratings** (AppBrain: 4.76/5, 35,614;
  Play snippet: 4.7, 22.9K reviews) · Installs: **10,000,000+**
  (androidrank: 164,866 ratings, 4.48 avg on the old Memory Zone
  listing; AppBrain: 11M installs before the old listing was removed
  Apr 29, 2025)
- Last update: **Oct 12, 2026** (v3.4.2) — actively maintained.
- Price: free. Works best with SanDisk drives; auto-backup when
  connected.
- What it does: back up / manage / protect photos, videos, contacts
  to SanDisk USB drives, SSDs, microSD; automatic backup on connect;
  free-up-space transfer.
- Top 1-star complaints (newest, from Play listing text):
  - "ZERO IMPROVEMENTS ON A17/OUI9. NO DRIVE CONNECT! BROKEN
    MULTI-PORT/MULTI-DRIVE OTG DETECT! MISREADS FREESPACE! … NO
    INTELLIGENT RESUME, NO R/W NTFS … sometimes displays phantom
    dupe devices." (v3.4.2, Oct 2026)
  - "The copy/backup process stops when it encounters a file larger
    than 4GB because the drives are formatted as FAT32, and there is
    no feature … to skip the file and continue." (Mar 2026)
  - "No option to 'move' files … I found it very slow. A 2 minute
    transfer took more than 20 minutes." (Jan 2025)
  - Rebrand backlash: "If you have the old Memory Zone, keep it …
    I still couldn't find a way to move a file."
- Demand this proves: **reliable OTG detection, intelligent resume,
  skip-and-continue past oversize files, NTFS honesty, and a move
  option** are the features users beg for. Our PRD already covers
  resume + skip + full errors; add an explicit FAT32 >4 GB
  skip-and-continue rule.
- Screenshots bar: polished brand listing, auto-backup story. Beat
  it with: works with ANY drive (not just SanDisk), no account, no
  upsell modals, and a notification you can act on without opening
  the app.

### 3. Buffalo 写真バックアップ (Photo Backup) — the name collision
- Developer: BUFFALO INC. (Japan) · Package: jp.buffalo.photobackup
- Rating: **4.5★ from 61 ratings** (Play) · Installs: **1,000+ band,
  ~4k total** (AppBrain, Jan 2026 snapshot)
- Launched: **September 2025** (Android; iOS version hit 100k
  downloads in 4 months) · Last update: **Dec 16, 2025** (Play) /
  Sep 27, 2025 (AppBrain)
- Price: free. Android 13+, USB-C phones, Buffalo SSDs / USB drives /
  card readers (verified devices list on Buffalo's site).
- What it does: open the app, connect SSD/USB/SD, backup starts
  automatically; browse backed-up photos in the app; restore in an
  emergency.
- Top 1-star complaints: too few reviews to sample newest 1-stars
  (61 ratings total, Japan-only listing in Japanese). Known friction
  from the listing: Japan-only verified-device list, Android 13+
  only, and a Sept 2025 fix for "some phones can't proceed past the
  licence/consent screen depending on font size settings" — a
  first-run blocker.
- **Naming impact: this app's English name on Play is literally
  "Photo Backup".** Our working name collides with a live,
  hardware-backed competitor. See the name check in the PRD.
- Screenshots bar: hardware-brand trust, auto-start on connect.
  Beat it with: global availability, any drive, no Android-13 floor
  if we can support it, and a notification-first flow (theirs needs
  the app open).

### 4. ThePhotoStick Omni — the hardware-locked incumbent
- Developer: PRAIRIE IT, Inc · Package: com.prairieit.thephotostick
- Rating: **4.0★ from ~1,655 ratings** (Play, Jul 2026 snapshot;
  AppBrain 3.98, 1,409) · Installs: **100,000+ band, ~340k total**
  (AppBrain)
- Last update: **Oct 8, 2026** — actively maintained.
- Price: free app, **but only works with ThePhotoStick Omni drives
  ($69.99–$149.99 hardware)**. That lock-in is its weakness.
- What it does: one-button backup of photos/videos to the stick;
  sorts into folders; 100+ file types.
- Top 1-star complaints (newest, from Play listing text):
  - "It proceeds like it is saving my photos. Today it was
    uploading for 3 hours … I plugged it into my laptop to see if
    any pictures downloaded. Not one picture. Each folder says it
    is empty." (182 people found this helpful)
  - "I'll ask the device to download, for example, 118 photos …
    and it will randomly only download 50!!!" (Feb 2026)
  - "App is very sluggish when backing up files. Viewing files
    takes forever … now I have duplicates of everything … ads
    indicate that any new pictures would be backed up, not the
    entire library."
  - "Downloaded from play store for my Samsung phone, follow the
    directions, the photo stick is not working … always says wrong
    drive."
- Demand this proves: **verify-after-copy and an honest completion
  report are the #1 trust feature.** Users' worst experience is a
  3-hour "backup" that copied nothing. Our verify-size-after-copy
  rule + a completion notification ("X photos, Y GB, 0 failed")
  directly answers the top complaint in this category.
- Screenshots bar: one-button simplicity story, hardware bundle.
  Beat it with: any USB drive, no $70 hardware toll, and proof of
  completion (counts + verify).

### 5. Files To SD Card, USB or Wi-Fi — the volume leader
- Developer: Michal Bukáček · Package: cz.bukacek.filestosdcard
- Rating: **4.5★ from ~358k–381k reviews** (Play snippet: 4.5,
  358k reviews; rating value 4.469 from 381,480 ratings) ·
  Installs: **10,000,000+** (Play band; AppBrain "Move Files to SD
  Card & USB" variant)
- Last update: **Oct 7, 2026** — actively maintained.
- Price: free (same developer also sells "Backup To PC: Photo
  Backup", 11k installs, for PC/NAS over Wi-Fi).
- What it does: move/copy photos, videos, music, documents to SD
  card or USB via OTG; copy keeps originals (backup) vs move frees
  space; Wi-Fi transfer to PC.
- Top 1-star complaints (newest, from Play listing text):
  - "v1.7021[SDK35]: A16/OUI8 moves choking badly w/ no
    intelligent resume from interrupt. rarely finds & stays
    connected to extUSB storage on AOK USB-OTG dongle(s) …
    can't R/W NTFS." (Android 16 regression)
  - "I counted nearly 100 instances of 'legitimate consent' abuse
    in the startup dialogue! It could not be rejected with one
    click as per EU law. This app is another scammy spyware."
  - Power-user praise that doubles as a gap: "It even sorted out
    the bad or corrupted files and skipped them and asked me
    first" — users love skip-and-ask.
- Demand this proves: **resume after interrupt and stable OTG
  reconnect are the retention features; consent-dialog spam is the
  churn feature.** Our design (one notification, no startup
  dialogs, no consent walls) is the opposite of the complaint.
- Screenshots bar: 10M+ installs, 4.5★, generic file mover.
  Beat it with: photo-first simplicity (no file-manager maze),
  oldest-first order, and a single-purpose flow a non-technical
  user finishes in one tap.

### 6. VaultDrive — the direct price rival (added Nov 16, 2026)
- Developer: Coticsy LLC · Package: com.coticsy.vaultdrive
- Rating: **no ratings yet (0 reviews)** · Installs: **500+ band,
  ~870 total** (AppBrain, Nov 16, 2026) · ~20 installs/day over the
  last 30 days (490 in 30 days)
- Launched: **October 2026** · Last update: **Nov 3, 2026** (v1.0.7)
  — actively maintained.
- Price: free to try (up to **200 files or 2 GB per backup**); **Pro
  Lifetime $19.99 once, no subscription, ever** — verified Nov 16,
  2026 from the vendor site (coticsy.com/vaultdrive.html: "Pro is
  $19.99 once", "Pro Lifetime is $19.99 (US), no subscription";
  "US prices as of November 2026") and the App Store IAP list
  ("VaultDrive: USB Backup $19.99"). The Google Play listing text
  (via AppBrain mirror) confirms "one-time purchase, with no
  subscription, ever" but does not show the dollar amount in the
  mirror. This is the price anchor behind our $9.99 Pro:
  VaultDrive charges $19.99 once for the same one-time model;
  comparable utility one-time unlocks are €4.99 (Portafogli Pro);
  $9.99 sits between the two and undercuts the direct competitor.
  See tool/growth/DECISIONS.md.
- What it does: plug in a USB-C flash drive / SSD / SD card reader,
  choose what to back up, tap Start; original quality, byte for byte,
  dates and places kept; Fresh or Incremental (Pro: copy only what's
  new, skip files already on the drive); resume after unplug; every
  copy checked, skipped files listed with reasons; folders by date
  or by album, one folder per phone; backup reminders; App Lock;
  73 languages; Android 8+ / iOS 17+; works offline, no account.
- Weakness to beat: **it is brand new and unrated, and it does not
  free up space** — its own FAQ: "Not yet. Today VaultDrive only
  copies, so nothing is removed from your phone. Freeing up space
  … is planned for an upcoming release." Our 7th article already
  owns the free-up-space intent; a future move feature would widen
  the gap. Its Android listing also keeps the screen on, dimmed,
  until the backup is done on iPhone ("Keeps the screen on, dimmed,
  until the backup is done") while Android "keeps running in the
  background, with a progress notification" — the notification-led
  flow is table stakes here, not our edge against VaultDrive; our
  edge is oldest-first order shown in the notification, Pause/Stop
  in the notification, and half our Pro price.
- 1-star complaints: one 5★ review only (Lakshmi, Jul 16, 2026:
  "20 GB in under 10 minutes"). No 1-stars yet — watch this
  listing; it is the rival most likely to grow into our direct
  competitor alongside Photos to USB.
- Screenshots bar: clean one-tap copy story, 73-language badge.
  Beat it with: half-price Pro ($9.99 vs $19.99), oldest-first
  order, Pause/Stop in the notification, and the free-up-space
  story it cannot tell yet.

### 7. FotoSafe — the guided-preview direct rival (added Nov 24, 2026)
- Developer: not named in the fetched Play listing text (vendor
  site fotosafe.weidisoft.net) · Package: at.weidi.fotobackup
- Rating: **not verifiable** — the fetched Play listing text shows
  no rating/install header, and no AppBrain mirror surfaced for
  this package (searched Nov 24, 2026) · Installs: **unknown, same
  reason** · Launched / last update: **unknown** — no dates in the
  fetched listing text or vendor site.
- Price: free; **one successful trial run of up to 100 media files
  free, then further runs require a one-time FotoSafe Pro purchase
  (Google Play in-app purchase, no subscription)** — verified Nov
  24, 2026 from the vendor site (fotosafe.weidisoft.net/en/: "one
  successful backup run with up to 100 photos or videos is free.
  FotoSafe Pro then unlocks further runs with a one-time Google Play
  purchase — no subscription") and the Play listing ("Includes one
  successful free trial backup of up to 100 photos/videos. A
  one-time FotoSafe Pro purchase unlocks unlimited backup runs. No
  subscription."). **The Pro dollar price is NOT verified** — the
  vendor site says "Google Play shows the current price" and the
  fetched Play listing text shows no amount; do not quote a FotoSafe
  Pro price until a Play price is read.
- What it does: guided Android-to-USB photo/video backup without a
  computer; Android 9+ with USB host/OTG; connect the drive, pick
  the destination through Android's system picker, review the media
  preview (destination, new files, already-backed-up files,
  required space), start copying; backups land in a /FotoSafe/
  folder; already-backed-up files are skipped on later runs; a
  visible progress notification shows during longer backups;
  originals stay on the phone; no cloud, no account, no ads, no
  tracking.
- Weakness to beat: **its free tier is one run of 100 files — a
  stricter free tier than ours (5 GB per run, every run)** — and it
  is a guided manual-start flow with no oldest-first order, no
  Pause/Stop in the notification, and no stated resume of an
  interrupted run (only skip-if-already-backed-up on the next run).
  Our edge: unlimited free runs up to 5 GB each, oldest-first order
  shown in the notification, Pause/Stop in the notification, resume
  where it stopped. The price comparison against FotoSafe stays
  unmade until its Pro price is verified — the $9.99-vs-$19.99
  undercut is against VaultDrive only.
- 1-star complaints: none verifiable — no reviews surfaced in the
  fetched Play listing text or search (Nov 24, 2026). Watch this
  listing — it is the rival whose free tier is closest to ours in
  shape (one free run) and whose guided-preview story is the most
  polished of the new entrants. Already cited as a rival guide in
  our own articles (secondary-user, restricted-profile, guest,
  Private Space, work-profile, Ulefone, Karbonn, Intex) — this row
  closes the gap that it was never in this file.
- Screenshots bar: guided three-step story (connect → review →
  copy-and-verify) with a preview screen. Beat it with:
  oldest-first order, Pause/Stop + resume in the notification, and
  a free tier measured in gigabytes per run, not one 100-file run.

## Also noted (not top 7)
- **FolderSync** (Tacit Dynamics, dk.tacit.android.foldersync.lite):
  4.2★, ~33k ratings, 1M+ installs, updated Oct 22, 2026. Generic
  folder sync (cloud/SMB/SFTP), not photo-to-USB-first; newest
  1-stars: Android 17 broke SFTP/SMB sync. Benchmark for sync
  reliability, not a direct rival.
- **USB Vault** (Filoti Creations, usbvault.filotimocreations.com):
  local-first USB photo/video backup, £5/year after trial,
  byte-for-byte copies, no internet. **Could not verify a live
  Google Play listing or rating from search on Nov 14** — no Play
  page surfaced; treat as a web-presence-only competitor until a
  Play listing is found. Its pitch (no cloud, own your files) is
  the closest to ours in words.
- **DEVOLTER: Photos to USB Stick** (PC-DEVOLTER): 1.8k installs,
  0 ratings, launched Aug 2026 — same niche as Photos to USB,
  even smaller.
- **Photo Backup Stick DC** (Rawerks): 2.4★ from 74 reviews,
  hardware-locked, "photobackup stick not connected" errors —
  a cautionary tale for detection reliability.
- **USB OTG Connector Phone to USB** (Alvva Inc.): 4.1★, 2.39k
  reviews — mostly an OTG checker + file manager; newest 1-star:
  "endless ads … spam … freezes up constantly."

## The repeated complaint = the feature list
Across all seven, the same 1-star themes recur:
1. **Copy says done but copied nothing / partial copy** (PhotoStick:
   3h → empty folders; 118 asked → 50 copied). → Verify every
   file, report real counts.
2. **No resume after interrupt / unplug** (SanDisk, Files To SD).
   → Resume + skip-if-exists, never restart from zero.
3. **Stalls on one bad file** (SanDisk FAT32 >4 GB stops everything).
   → Skip-and-continue, list skipped files at the end.
4. **Drive not detected / NTFS surprises** (SanDisk, Files To SD,
   Photo Backup Stick). → Detect honestly, name the filesystem,
   say what will happen before copying.
5. **Ads / upsell / consent walls in the way of the one job** (USB
   OTG Connector, Files To SD). → No ads in the copy path, no
   startup dialogs.
6. **Slowness with no progress honesty** (PhotoStick "very sluggish",
   SanDisk "20 minutes for a 2 minute transfer"). → Live speed +
   ETA in the notification, updated honestly.

## Screenshots bar to beat
- SanDisk: brand-polished, auto-backup story, 10M installs badge.
- Photos to USB: clearest one-tap copy story; month folders.
- Buffalo: hardware trust + auto-start on connect.
- FotoSafe: guided three-step story (connect → review →
  copy-and-verify) with a preview screen.
- Our bar: a real notification screenshot (offer → progress with
  %/GB/speed/ETA → done with counts), a real settings screenshot
  (From/To pickers), and a real completion proof — all real
  screenshots from a Pixel + real disk, never mockups. No
  capability claim in a screenshot without a test proving it.

## Sources
- Play listings (browser.open, crawled Nov 14, 2026):
  play.google.com/store/apps/details?id=com.arzdrz.photos_to_usb,
  com.sandisk.smz2, jp.buffalo.photobackup,
  com.prairieit.thephotostick, cz.bukacek.filestosdcard,
  dk.tacit.android.foldersync.lite
- AppBrain mirrors: appbrain.com/app/photos-to-usb-otg-backup/,
  /sandisk/, /thephotostick-omni/,
  /%E5%86%99%E7%9C%9F%E3%83%90%E3%83%83%E3%82%AF%E3%82%A2%E3%83%83%E3%83%97…/jp.buffalo.photobackup,
  /foldersync/
- androidrank.org: SanDisk Memory Zone, FolderSync, IDrive Photo Backup
- USB Vault site: usbvault.filotimocreations.com
- FotoSafe (Nov 24, 2026): Play listing
  play.google.com/store/apps/details?id=at.weidi.fotobackup
  (fetched live; title "FotoSafe — Photo Backup to USB", free,
  $0 USD; no rating/install header in the fetched text) and vendor
  site fotosafe.weidisoft.net/en/ + /en/guides/back-up-android-photos-to-usb/
  (free trial = one successful run, up to 100 media files; Pro =
  one-time Google Play purchase, no subscription; Pro dollar price
  not shown on either page)
- Review quotes are verbatim from Play listing review excerpts
  surfaced by search on Nov 14, 2026; newest-1-star sampling is
  limited to what Play/search surfaced — re-sample after launch
  from the live Play Console reviews feed.
