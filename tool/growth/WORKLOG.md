# SnapDisk — Work Log

The only task tracker for this product. One row per task: what, who,
state, evidence, date.

## WL-42 — Back up OnePlus photos to USB article, 13th how-to (DONE)

- Owner: SEO/GEO lane lead. State: DONE.
  New docs/oneplus-photos-to-usb.html ("Back up OnePlus photos
  to a USB drive (no computer)"), following the WL-41
  article template exactly (navy/cyan, TL;DR first, no JS,
  text-only, reuses docs/screenshots-01-settings-defaults.png as
  the lead shot — same reuse as the 8th, 9th, 10th, 11th, and
  12th articles). Facts verified from code on fresh main: SAF
  tree picker (MainActivity.kt OpenDocumentTree +
  takePersistableUriPermission); oldest-first via MediaStore
  DATE_TAKEN ascending; free 5 GB per run
  (ProPrefs.FREE_BACKUP_BYTES_LIMIT); Pro $9.99 one-time =
  auto-backup on connect + unlimited size; no INTERNET
  permission in AndroidManifest.xml; FAT32 files >=4 GiB skipped
  with reason (FilesystemInfo.FAT32_MAX_FILE_BYTES); raw
  byte-stream copy (BackupEngine.copyWithResume — no
  decode/re-encode) with size verification after copy. Search
  evidence (Nov 16, 2026 live web search): top results are all
  manual copy — AnExplorer guide (manual select-copy-paste,
  anexplorer.io/transfer/phone-to-usb; the only one showing a
  progress bar for the manual copy, still no resume or
  verification), Pixoneye (manual file-manager copy, one small
  batch at a time; notes ThePhotoStick Omni, a dedicated stick
  with its own app, not an ordinary flash drive), Dr.Fone (PC
  drag-and-drop / Google Drive / AirDroid / OTG manual copy),
  YouTube PYcbV25CczI (OTG + file manager manual copy, suggests
  Move), Coolmuster (OTG manual copy, PC tool, cloud). Google
  Photos is cloud, not USB. OnePlus specifics stated honestly:
  OTG off by default — Settings, System Settings, OTG
  Connection (older OxygenOS: Settings, System, OTG storage),
  auto-off after 10 minutes with no peripheral; stock file
  manager on recent OnePlus is My Files; most OnePlus phones
  have no microSD slot. Honest contrast stated in the TL;DR:
  My Files works but is manual select-copy-paste with no
  progress, no resume, no verification; Google Photos backup is
  cloud, not a USB copy; SnapDisk = enable OTG, plug in, tap
  Backup, whole library oldest-first with %, GB copied/total,
  speed, ETA, Pause/Stop, resume on re-plug, size-verified,
  copies never deletes. Query 12 scored Nov 16, 2026
  (searches.md): "back up photos from OnePlus to USB flash
  drive without computer" — real how-to gap. Volume unknown
  (Play publishes no volume); difficulty low-medium. Linked
  from blog index (JSON-LD + post block), sitemap.xml,
  llms.txt, llms-full.txt (full section), feed.xml (now 13
  items). JSON-LD validated by parse; sitemap.xml and feed.xml
  parse as XML. Title 48 chars, meta 145 chars. No app code or
  Play listing files touched; applicationId untouched
  (dev.dzianisv.snapdisk).
  Evidence: PR #47 (commit 2a2b295, merge e58984c, merged
  2026-11-16T23:36:59Z); CI run 43980357137 (build pass, 1m0s)
  green; post-merge main runs green (Android PR 43980826298,
  pages 43980823982). Live fetch after Pages deploy: article
  200, 22066 bytes, title "Back up OnePlus photos to a USB
  drive — SnapDisk" present, "November 16, 2026" present;
  blog index 200 with the new link; feed.xml 200 (13 items);
  sitemap.xml 200 with the new link.

## WL-41 — Back up Google Pixel photos to USB article, 12th how-to (DONE)

- Owner: SEO/GEO lane lead. State: DONE.
  New docs/google-pixel-photos-to-usb.html ("Back up Google Pixel
  photos to a USB drive (no computer)"), following the WL-40
  article template exactly (navy/cyan, TL;DR first, no JS,
  text-only, reuses docs/screenshots-01-settings-defaults.png as
  the lead shot — same reuse as the 8th, 9th, 10th, and 11th
  articles). Facts verified from code on fresh main: SAF tree
  picker (MainActivity.kt OpenDocumentTree +
  takePersistableUriPermission); oldest-first via MediaStore
  DATE_TAKEN ascending; free 5 GB per run
  (ProPrefs.FREE_BACKUP_BYTES_LIMIT); Pro $9.99 one-time =
  auto-backup on connect + unlimited size; no INTERNET
  permission in AndroidManifest.xml; FAT32 files >=4 GiB skipped
  with reason (FilesystemInfo.FAT32_MAX_FILE_BYTES); raw
  byte-stream copy (BackupEngine.copyWithResume — no
  decode/re-encode) with size verification after copy. Search
  evidence (Nov 16, 2026 live web search): top results are all
  manual copy — YouTube PYcbV25CczI (OTG + file manager),
  AnExplorer guide (manual select-copy-paste,
  anexplorer.io/transfer/phone-to-usb), cellularnews.com (Files
  app manual copy), Dr.Fone (PC drag-and-drop / OTG manual
  copy), manualdobruxo.com.br (Files by Google manual copy).
  Google Photos is cloud, not USB. Pixel specifics stated
  honestly: no SD-card slot and no My Files — Files by Google
  is the stock route. Honest contrast stated in the TL;DR:
  Files by Google works but is manual select-copy-paste with no
  progress, no resume, no verification; Google Photos backup is
  cloud, not a USB copy; SnapDisk = plug in, tap Backup, whole
  library oldest-first with %, GB copied/total, speed, ETA,
  Pause/Stop, resume on re-plug, size-verified, copies never
  deletes. Query 11 scored Nov 16, 2026 (searches.md): "back
  up photos from Google Pixel to USB flash drive without
  computer" — real how-to gap. Volume unknown (Play publishes
  no volume); difficulty low-medium. Linked from blog index
  (JSON-LD + post block), sitemap.xml, llms.txt, llms-full.txt
  (full section), feed.xml (now 12 items). JSON-LD validated by
  parse; sitemap.xml and feed.xml parse as XML. Title 53 chars,
  meta 150 chars. No app code or Play listing files touched;
  applicationId untouched (dev.dzianisv.snapdisk).
  Evidence: PR #45 (commit 8aaa5da, merge 41ccc22, merged
  2026-11-16T22:33:56Z); CI run 43971010249 (build pass, 1m6s)
  green; post-merge main runs green (Android PR 43971276366,
  pages 43971273960). Live fetch after Pages deploy: article
  200, 20659 bytes, title "Back up Google Pixel photos to a
  USB drive — SnapDisk" present, "November 16, 2026" present;
  blog index, feed.xml (12 items) and sitemap.xml all live with
  the new link.

## WL-40 — Back up Samsung Galaxy photos to USB article, 11th how-to (DONE)

- Owner: SEO/GEO lane lead. State: DONE.
  New docs/samsung-galaxy-photos-to-usb.html ("Back up Samsung
  Galaxy photos to a USB drive (no computer)"), following the
  WL-39 article template exactly (navy/cyan, TL;DR first, no JS,
  text-only, reuses docs/screenshots-01-settings-defaults.png as
  the lead shot — same reuse as the 8th, 9th, and 10th articles).
  PIVOT (per the WL-40 brief's fallback): the brief's
  original-quality query, "back up photos from Android to USB
  without losing quality", is NOT a gap — the top result,
  Photos to USB: OTG Backup (Play listing), already advertises
  "Original quality. Every file is copied byte for byte, nothing
  is compressed", and the FotoSafe guide covers the same
  phone → USB original-quality route. No article was published
  on that query. Closest real gap found: the Samsung Galaxy
  query — no top result automates the photo library (Smart
  Switch = whole-device backup, My Files = manual copy). The
  quality angle is covered honestly inside the Galaxy article
  (SnapDisk also copies byte-for-byte, but is not the only app
  that does). Facts verified from code on fresh main: SAF tree
  picker (MainActivity.kt OpenDocumentTree +
  takePersistableUriPermission); oldest-first via MediaStore
  DATE_TAKEN ascending; free 5 GB per run
  (ProPrefs.FREE_BACKUP_BYTES_LIMIT); Pro $9.99 one-time =
  auto-backup on connect + unlimited size; no INTERNET
  permission in AndroidManifest.xml; FAT32 files >=4 GiB skipped
  with reason (FilesystemInfo.FAT32_MAX_FILE_BYTES); raw
  byte-stream copy (BackupEngine.copyWithResume — no
  decode/re-encode) with size verification after copy. Search
  evidence (Nov 16, 2026 live web search): top results are
  Samsung Smart Switch whole-device backup
  (umatechnology.org, summarising Samsung support pages), My
  Files manual copy (sekin.in; Samsung Members — Samsung says
  files cannot be transferred directly to USB from Gallery),
  Coolmuster (PC drag-and-drop, OTG manual copy, PC tool,
  cloud), and cloud (Samsung Cloud, Google Photos Storage
  saver). Honest contrast stated in the TL;DR: Smart Switch
  works but backs up the whole device, not a re-runnable photo
  library; My Files has no progress, no resume, no
  verification; SnapDisk = plug in, tap Backup, whole library
  oldest-first with %, GB copied/total, speed, ETA, Pause/Stop,
  resume on re-plug, size-verified, copies never deletes. Query
  10 scored Nov 16, 2026 (searches.md): "back up photos from
  Samsung Galaxy to USB flash drive without computer" — real
  how-to gap. Volume unknown (Play publishes no volume);
  difficulty low-medium. Linked from blog index (JSON-LD + post
  block), sitemap.xml, llms.txt, llms-full.txt (full section),
  feed.xml (now 11 items). JSON-LD validated by parse;
  sitemap.xml and feed.xml parse as XML. Title 58 chars, meta
  152 chars. No app code or Play listing files touched;
  applicationId untouched (dev.dzianisv.snapdisk).
  Evidence: PR #43 (commit b987e61, merge a0a1a3c, merged
  2026-11-16T21:36:26Z); CI run 43961298964 (build pass, 1m31s)
  green; post-merge main runs green (Android PR 43961797661,
  pages 43961796839). Live fetch after Pages deploy: article
  200, 21169 bytes, title "Back up Samsung Galaxy photos to a
  USB drive — SnapDisk" present, "November 16, 2026" present;
  blog index, feed.xml (11 items) and sitemap.xml all live with
  the new link.

## WL-39 — Transfer photos to USB automatically article, 10th how-to (DONE)

- Owner: SEO/GEO lane lead. State: DONE.
  New docs/transfer-photos-to-usb-automatically.html ("Transfer
  photos from Android to a USB drive automatically (no manual
  copy)"), following the WL-38 article template exactly
  (navy/cyan, TL;DR first, no JS, text-only, reuses
  docs/screenshots-01-settings-defaults.png as the lead shot —
  same reuse as the 8th and 9th articles). Facts verified from
  code on fresh main: SAF tree picker (MainActivity.kt
  OpenDocumentTree + takePersistableUriPermission;
  PhotoQuery.listTreeFiles, BackupService.kt:629); oldest-first
  via MediaStore DATE_TAKEN ascending; free 5 GB per run
  (ProPrefs.FREE_BACKUP_BYTES_LIMIT); Pro $9.99 one-time =
  auto-backup on connect + unlimited size; no INTERNET
  permission in AndroidManifest.xml; FAT32 files >=4 GB skipped
  with reason. Search evidence (Nov 16, 2026 live web search):
  top results are manual file-manager copy — YouTube
  PYcbV25CczI, Dr.Fone (drfone.wondershare.com), sekin.in,
  thegadgetians.com, usbmemorydirect.com — plus PC drag-and-drop
  and cloud (Google Drive, AirDroid); no result offers an
  automated whole-library backup with progress, Pause/Stop, and
  resume. Honest contrast stated in the TL;DR: manual method =
  pick files one by one, no progress, no resume, no
  verification; SnapDisk = plug in, tap Backup, whole library
  oldest-first with %, GB copied/total, speed, ETA, Pause/Stop,
  resume on re-plug, size-verified, copies never deletes. Query
  9 scored Nov 16, 2026 (searches.md): "transfer photos from
  Android to USB flash drive without computer" — real how-to
  gap. Volume unknown (Play publishes no volume); difficulty
  low-medium. Linked from blog index (JSON-LD + post block),
  sitemap.xml, llms.txt, llms-full.txt (full section), feed.xml.
  JSON-LD validated by parse; sitemap.xml and feed.xml parse as
  XML. Title 47 chars, meta 154 chars. No app code or Play
  listing files touched; applicationId untouched
  (dev.dzianisv.snapdisk).
  Evidence: PR #41 (commit 6bfe639, merge 4d59ff1, merged
  2026-11-16T20:33:16Z); CI run 43950699654 (build pass, 1m23s)
  green; post-merge main runs green (Android PR 43950981099,
  pages 43950979225). Live fetch after Pages deploy: article
  200, 18115 bytes, title "Transfer photos from Android to a
  USB drive automatically (no manual copy)" present,
  "November 16, 2026" present; blog index, feed.xml (10 items)
  and sitemap.xml all live with the new link.

## WL-38 — Back up WhatsApp photos to USB article, 9th how-to (DONE)

- Owner: SEO/GEO lane lead. State: DONE.
  New docs/whatsapp-photos-to-usb.html ("Back up WhatsApp photos
  to a USB drive on Android (no computer)"), following the 8th
  article template exactly (navy/cyan, TL;DR first, no JS,
  text-only, reuses docs/screenshots-01-settings-defaults.png as
  the lead shot — same reuse as the 7th article). Facts verified
  from code on fresh main: SAF tree picker (MainActivity.kt
  OpenDocumentTree + takePersistableUriPermission;
  BackupService.kt:629 lists via PhotoQuery.listTreeFiles);
  recursive DocumentFile walk, image/video extensions only,
  sorted global oldest-first, destination mirrors source tree;
  free 5 GB per run (ProPrefs.FREE_BACKUP_BYTES_LIMIT); Pro
  $9.99 one-time = auto-backup on connect + unlimited size; no
  INTERNET permission in AndroidManifest.xml; FAT32 files >=4 GB
  skipped with reason. WhatsApp paths from the Nov 16, 2026
  search result (umatechnology.org): newer Android — Android >
  media > com.whatsapp > WhatsApp > Media > WhatsApp Images;
  older installs — WhatsApp > Media > WhatsApp Images; path
  varies by Android version/install, stated in the article.
  Honest limits stated prominently in the TL;DR: media files
  only — no chat history (msgstore.db.crypt14), no voice notes
  (.opus), no stickers outside the picked folder, no documents;
  WhatsApp Settings > Chats > Chat backup (Google Drive) is the
  route for chats; SnapDisk copies, never deletes; free 5 GB per
  run, run again past the cap. Query 8 scored Nov 16, 2026
  (searches.md): "back up WhatsApp photos to USB drive Android
  without computer" — real how-to gap, top results PC-based
  (Wondershare MobileTrans), SD-card moves, or cloud; no top
  result offers phone → USB without a computer. Volume unknown
  (Play publishes no volume); difficulty low-medium. Linked from
  blog index (JSON-LD + post block), sitemap.xml, llms.txt,
  llms-full.txt (full section + Blog posts list), feed.xml.
  JSON-LD validated by parse; sitemap.xml and feed.xml parse as
  XML. Title 52 chars, meta 159 chars. No app code or Play
  listing files touched; applicationId untouched
  (dev.dzianisv.snapdisk).
  Evidence: PR #39 (commit b46f412, merge 93a3c64, merged
  2026-11-16T19:35:22Z); CI run 43940537748 (build pass, 1m9s)
  green. Live fetch after Pages deploy: article 200, 18408
  bytes, title "Back up WhatsApp photos to USB on Android —
  SnapDisk" present, "November 16, 2026" present; blog index,
  feed.xml (9 items) and sitemap.xml all live with the new
  link.

## WL-37 — SnapDisk vs VaultDrive comparison article, 8th how-to (DONE)

- Owner: SEO/GEO lane lead. State: DONE.
  New docs/snapdisk-vs-vaultdrive.html ("SnapDisk vs VaultDrive:
  USB photo backup on Android, compared"), following the 7th
  article template exactly (navy/cyan, TL;DR first, no JS,
  text-only, no new screenshots). Facts only from competitors.md
  #6 (VaultDrive, verified WL-35, Nov 16 2026) and the Play
  listing: Pro $9.99 vs $19.99 one-time, free 5 GB/run vs 200
  files or 2 GB, oldest-first + Pause/Stop in the notification,
  and VaultDrive's own FAQ on freeing up space ("planned for an
  upcoming release"). Install counts dated (as of Nov 14, 2026);
  Android scope only; no invented numbers, no ratings, no demand
  claims. Linked from blog index (JSON-LD + post block),
  sitemap.xml, llms.txt, llms-full.txt (full section + Blog posts
  list), feed.xml. JSON-LD validated by parse; sitemap.xml and
  feed.xml parse as XML. Title 62 chars, meta 149 chars. No app
  code or Play listing files touched; applicationId untouched
  (dev.dzianisv.snapdisk).
  Evidence: PR #38 (commit 8b1b416, merge f6ed14f, merged
  2026-11-16T11:29:41Z); CI run 43858010069 (build pass, 1m20s)
  green. Live fetch after Pages deploy: article 200, 14563
  bytes, title "SnapDisk vs VaultDrive: USB photo backup on
  Android — SnapDisk" present, "November 16, 2026" present;
  blog index, feed.xml and sitemap.xml all live with the new
  link.

## WL-36 — Host the Privacy Policy on GitHub Pages, point the listing at it (DONE)

- Owner: Hourly supervisor. State: DONE.
  The Play listing's privacy-policy URL was still marked "hosted URL
  needed" even though PRIVACY.md existed in the repo root and the
  landing page had shipped Nov 14. Published it: new
  docs/privacy.html mirrors PRIVACY.md verbatim in the site template
  (navy/cyan, JSON-LD WebPage + BreadcrumbList, canonical URL),
  added to docs/sitemap.xml, docs/llms.txt and docs/llms-full.txt;
  every site footer Privacy link (landing, blog, FAQ, all 7
  articles) and tool/play-store/listing.md now point at
  https://dzianisv.github.io/photo-backup/privacy.html. No stale
  GitHub-blob privacy links remain in docs/ or tool/. The publish
  package is now complete on the listing side: title, descriptions,
  icon, feature graphic, 5 screenshots, data safety, content
  rating, privacy URL, v0.1.2 AAB — the only remaining Play gate
  is the owner's Console sign-in (WL-31). No numbers invented;
  applicationId untouched (dev.dzianisv.snapdisk).
  Evidence: PR #36 (commit e7592c2, merge 88d49a0, merged
  2026-11-16T09:28Z); CI run 43839793523 (build pass, 1m12s)
  green. Live fetch after Pages deploy: privacy.html 200, 6176
  bytes, title "SnapDisk Privacy Policy — photo backup to USB on
  Android", JSON-LD present, "Last updated: November 14, 2026"
  present.

## WL-35 — Verify the VaultDrive $19.99 Pro-Lifetime undercut (DONE)

- Owner: Hourly supervisor. State: DONE.
  Closes the week-1 reflection gap: the $9.99 Pro price reasoning
  cited "VaultDrive charges $19.99 once for Pro Lifetime" with no
  competitor row. Verified Nov 16, 2026 from live sources:
  vendor site coticsy.com/vaultdrive.html (crawled 2026-11-16
  04:26 UTC) — "Pro is $19.99 once", "Pro Lifetime is $19.99
  (US), no subscription", "US prices as of November 2026", free
  up to 200 files or 2 GB per backup; App Store IAP list —
  "VaultDrive: USB Backup $19.99"; Google Play listing text via
  AppBrain mirror (com.coticsy.vaultdrive, v1.0.7, launched Oct
  2026, ~870 installs, 0 reviews, no ads, last update Nov 3,
  2026) confirms "one-time purchase, with no subscription, ever"
  (dollar amount not shown in the mirror). Added as competitor
  #6 in tool/growth/competitors.md (was Top 5, now Top 6):
  VaultDrive is the direct price rival — same niche (phone to USB,
  no cloud, no account), launched Oct 2026, unrated, ~20
  installs/day; its own FAQ says it does NOT free up space yet
  ("planned for an upcoming release") — the free-up-space story
  our 7th article owns. Our edge vs VaultDrive: half-price Pro
  ($9.99 vs $19.99), oldest-first order, Pause/Stop in the
  notification. The $9.99 undercut justification is now verified
  and usable in listing copy. No numbers invented; no money
  spent; applicationId untouched (dev.dzianisv.snapdisk).
  Evidence: PR #34 (commit a40d438, merge e27d2ae, merged
  2026-11-16T04:30:23Z); CI run 43804295519 (build pass, 1m8s)
  green.

## 2026-11-15 — FAQ page live (seo-geo lane)

- Owner: Growth lead (seo-geo lane). State: DONE.
  Page: "SnapDisk FAQ" —
  https://dzianisv.github.io/photo-backup/faq.html
  (docs/faq.html, same navy/cyan template as the six articles,
  TL;DR answer first, no JS, text-only — no new screenshots).
  8 questions, the support FAQ and the first-visitor page in one:
  how a backup works, free 5 GB per run, Pro $9.99 one-time,
  privacy (no INTERNET permission), resume on re-plug, disk
  formats (FAT32 >4 GB skipped, exFAT no limit, NTFS honestly
  UNTESTED on a real disk), default folders, Play status,
  where to report issues. JSON-LD FAQPage + BreadcrumbList in
  the head. Linked from the landing index footer and
  docs/blog/index.html; added to docs/sitemap.xml, docs/llms.txt
  + docs/llms-full.txt (Guides/FAQ section).
  Content: v0.1.2 real steps (plug in, tap Backup, oldest-first
  background copy, progress + Pause / Stop, size-verified, resume
  on re-plug); free 5 GB per run verified from code
  (ProPrefs.FREE_BACKUP_BYTES_LIMIT =
  5L * 1024 * 1024 * 1024, app/src/main/java/dev/dzianisv/
  snapdisk/backup/ProPrefs.kt:43, enforced in
  BackupService.kt:312); Pro $9.99 one-time, auto-backup on
  connect + unlimited size (tool/growth/DECISIONS.md,
  tool/play-store/listing.md); no INTERNET permission verified
  from app/src/main/AndroidManifest.xml; FAT32 files >=4 GB
  skipped with reason (BackupService.kt:325-330,
  FilesystemInfo.kt:14,53). No download counts, no ratings, no
  search-volume or demand claims. applicationId untouched
  (dev.dzianisv.snapdisk). No money spent, no credentials used,
  no accounts created.
  Evidence: PR #26 (commit 96cdd63, merge 8e57473, merged
  2026-11-16T01:58:02Z); CI run 43789040086 (build pass, 1m17s)
  green. Live fetch after Pages deploy: faq.html 200, 11649
  bytes, title "SnapDisk FAQ — photo backup to USB on Android",
  FAQPage JSON-LD present. This follow-up PR records the merge
  SHA + live fetch in the Work Log.
  Note: the first commit of this work landed on the wrong local
  branch (a sibling agent's article-free-up-space checkout in
  the shared clone); it was cherry-picked onto faq-page as
  96cdd63 and the stray commit removed from the sibling branch
  before the PR was opened — no sibling files were touched.

## 2026-11-16 WITA (2026-11-15 PST) — Weekly reflection, week 1 days 1–2

- Owner: Product Lead (weekly reflection job). State: DONE.
  Reflection written to the goal file (scoreboard / what worked /
  what did not / support / next three actions). Scoreboard: downloads
  unknown (not on Play), rating none, 0 open issues, 0 open PRs,
  v0.1.2 latest, 6 articles live, blog + RSS live, landing 200,
  0 directory submissions, Sentry/PostHog not installed. Week 1
  foundation is done except owner gates. Live re-verified in this
  run: landing https://dzianisv.github.io/photo-backup/ 200
  (crawled 2026-11-16 01:50 UTC; progress screenshot leads,
  Free/$9.99 Pro pricing, Play status honest "Not yet"). Repo HEAD
  7ff4eef (PR #24 merge), 0 open issues, 0 open PRs.

## WL-31 — Resolve Play Console sign-in, verify/upload SnapDisk (OPEN, owner-gated)

- Owner: Dzianis. Due: Nov 16. State: OPEN.
  Google rejected the saved password twice Nov 15 (15:33 and 16:22
  UTC); the Console was never reached, so his claimed SnapDisk push
  stays unverified. Paths: re-enter the password carefully, use
  "Forgot password?" recovery, or sign in himself in the live
  browser. Once in: verify the push, or upload the v0.1.2 AAB
  (tool/play-store/); first production review usually 1–3 days,
  up to 7. Unblocks: listing verification, sandbox Pro purchase,
  SnapDisk name check on Play. Do not retry the saved login from
  here; the side chat owns the sign-in thread.

## WL-32 — Submit SnapDisk to AlternativeTo (OPEN, blocked on owner account)

- Owner: Growth lead (account: Dzianis). Due: Nov 18. State: OPEN.
  Copy ready verbatim in tool/growth/directories.md (WL-30, PR #23,
  merge 8b4a2e4): name SnapDisk, tagline, short/long descriptions,
  category Backup & Sync, targets Photos to USB: OTG Backup,
  Buffalo Photo Backup, VaultDrive, PhotoSaver, SanDisk app,
  MeBackup. Needs a free AlternativeTo account (his sign-in).
  Never pay the $5 priority review without his explicit approval.
  Do not submit twice to one directory. Product Hunt / Show HN /
  Indie Hackers stay planned — Product Hunt waits for the Play
  listing.

## WL-33 — Medium publication + cross-post the 10 articles (OPEN, blocked on owner account)

- Owner: Growth lead (publication: Dzianis). Due: Nov 19. State: OPEN.
  Blog home + RSS live at https://dzianisv.github.io/photo-backup/blog/
  with 10 articles; Medium recommended over Substack (import sets
  canonical back to GitHub Pages). Needs him to create a Medium
  publication for SnapDisk and grant access; then cross-post all 10
  articles with canonical URLs kept on GitHub Pages.

## WL-34 — Answer the Android 15 six-hour dataSync timeout before first release (DONE)

- Owner: Implementation lead. Due: Nov 19. State: DONE.
  Evidence: PR #30 (commit 12fe757, merge 81ed0e4); CI run
  43791513967 green (build pass, 1m47s).
  `BackupService.onTimeout` pauses with the same clean paused state
  as a disk detach (PAUSED + full error, `.part` kept, finished
  files kept), plain notification, then `stopSelf()`; `CopyControl.timedOut`
  unwinds the engine to `CopyResult.Paused` at the next buffer
  boundary; resume reuses the detach path (open the app first to
  reset the timer). A refused `startForeground` after the budget is
  exhausted surfaces as a full ERROR state, never a crash. Two unit
  tests prove the timeout path pauses cleanly and resumes byte-exact.
  Target-SDK note added to the implementation spec §2 (workspace spec
  mirror `docs/implementation-spec.md` — the repo carries no spec .md;
  the Notion hub page carries the same paragraph — verified present
  Nov 16, see the follow-up entry below). Timeout
  behavior verified against Android Developers "Foreground service
  timeouts". Still owed: real-device check on the Pixel 9a with the
  adb timeout commands from the spec note.

## 2026-11-16 WITA — WL-34 Notion follow-up closed (hourly supervisor)

- Owner: Hourly supervisor. State: DONE.
  The WL-34 row said the Notion hub page "still needs the same
  paragraph". Verified by re-fetch Nov 16 03:26 UTC: the
  Implementation Spec page
  (https://app.notion.com/p/418ac25eb49f819fa47dc3e0c7742d0a)
  already carries the full Android 15 six-hour dataSync timeout
  paragraph under §2 ("answered in v0.1.2 before the first
  release — PR #30, merge 81ed0e4", onTimeout answer, resume path,
  Pixel 9a adb verification steps). No edit needed; the owed item
  is closed, and the WL-34 row above is corrected to match.
  Board check in the same run (03:24 UTC): 0 open issues, 0 open
  PRs, latest CI green (Android PR run 43792113941, pages run
  43792114143), live site verified — landing, blog home, FAQ,
  feed.xml (7 items), 7th article all 200. Open rows stay
  owner-gated: WL-31 Play Console sign-in (due Nov 16), WL-32
  AlternativeTo (due Nov 18), WL-33 Medium (due Nov 19).
  Evidence: PR #32 (commit 94d1bfc, merge a6b41c4, merged
  2026-11-16T03:29:45Z); CI run 43798302111 (build pass, 1m9s)
  green; post-merge main runs green (Android PR 43798516732,
  pages 43798516369).

## 2026-11-15 — Directory submissions copy ready (social-marketing lane, WL-30)

- Owner: Growth lead (social-marketing lane). State: DONE (copy only —
  nothing submitted to any directory).
  `tool/growth/directories.md` — submission-ready copy, verbatim: name,
  URL, tagline (52 chars), short description (296 chars), long
  description (496 chars), category, tags, icon
  `tool/play-store/icon-512.png` (512×512, verified on disk),
  AlternativeTo targets from competitors.md + searches.md (Photos to
  USB: OTG Backup, Buffalo Photo Backup, VaultDrive, PhotoSaver
  Phone-to-Drive, SanDisk app, MeBackup), status table per directory,
  and the rule block (never pay without owner approval; every
  directory needs an owner account). Structure and rules mirrored
  from Convert264 WL-30.
  Facts verified against the repo before writing: v0.1.2,
  applicationId dev.dzianisv.snapdisk, minSdk 24, free 5 GB per run
  (ProPrefs.FREE_BACKUP_BYTES_LIMIT, enforced in BackupService.kt),
  Pro $9.99 one-time, no INTERNET permission in the manifest. No
  download counts, no ratings, no search-volume claims.
  F-Droid: NOT possible as-is — `app/build.gradle.kts:72` depends on
  `libs.play.billing.ktx` (Google Play Billing, proprietary); a foss
  flavor without billing would be needed, deferred until Play is
  live; still needs a GitLab account + fdroiddata MR (owner).
  BetaList ruled out (paid only + own domain required, landing is a
  github.io subdomain). Product Hunt / Show HN / Indie Hackers
  planned — need his accounts; do not launch Product Hunt until the
  Play listing exists. DevHunt / SaaSHub weak fit, noted briefly.
  applicationId untouched (dev.dzianisv.snapdisk). No money spent,
  no credentials used, no accounts created.
  Evidence: PR #23 (commit 3447101, merge 8b4a2e4, merged
  2026-11-15T19:32:39Z); CI run 43752875819 (build pass, 1m5s)
  green. Merge SHA recorded in follow-up PR #24.

## 2026-11-15 — Seventh article live (seo-geo lane, pulled forward from Nov 26)

- Owner: Growth lead (seo-geo lane). State: DONE (pending live
  verification after merge — PR number + CI run + live fetch
  recorded below on merge).
  Article: "How to free up space by moving photos to a USB drive" —
  https://dzianisv.github.io/photo-backup/free-up-space-move-photos-to-usb.html
  (docs/free-up-space-move-photos-to-usb.html, same navy/cyan template as
  the first six articles, TL;DR answer first, real steps, manual
  alternative, callout, footer link). Targets the NEW phrase
  "move photos to USB to free up space on Android" — the free-up-space
  intent, not covered by any of the six existing articles (all six are
  backup/copy intent). Picked over the other two candidates after live
  web search Nov 15: "transfer photos from phone to USB without a
  computer" overlaps the existing copy/USB articles (same OTG method,
  same top results — Coolmuster, Dr.Fone, AnExplorer, YouTube); "back
  up WhatsApp photos to USB drive" is thinner and mostly PC-based
  methods (MobiKin, fireebok), and the WhatsApp folder path varies by
  install. The free-up-space phrase's top results are real how-to
  pages (YouTube OTG guide, Alibaba free-up-space guide, ceedo,
  epictechtips, Coolmuster) — real how-to demand, no dominant app —
  framed honestly as a how-to article, no search-volume, demand,
  download, or rating claims.
  Content: SnapDisk v0.1.2 real steps (plug in, tap Backup,
  oldest-first background copy, progress + Pause / Stop,
  size-verified, resume on re-plug, run again past the free 5 GB
  per-run limit — already-copied files are skipped); free 5 GB per
  run verified from code (ProPrefs.FREE_BACKUP_BYTES_LIMIT =
  5L * 1024 * 1024 * 1024, enforced in BackupService.kt) — no
  other numbers claimed, no download counts, no ratings. "No
  internet permission" verified from
  app/src/main/AndroidManifest.xml (no INTERNET permission
  declared). Honest core of the article: SnapDisk COPIES, it never
  deletes — the delete step is the reader's, only after opening a
  few photos on the drive to verify the copy; stated in the TL;DR,
  the steps, and the FAQ. Manual alternative: the Files app's
  "Move to", which copies and deletes in one step, with its limits
  (deletes as it copies, no resume, no verification) stated
  honestly. One real screenshot only:
  docs/screenshots-01-settings-defaults.png, already in docs/
  (real settings screen — From /sdcard/DCIM, To /disk/DCIM —
  from green smoke run 43644126583; reused as lead shot — it was
  already the lead shot of the third article — because it shows
  the From/To folders the copy-then-delete flow is set up in; no
  new file needed). No mockups. JSON-LD Article + FAQPage +
  BreadcrumbList in the head. Linked from the landing index
  footer, added to docs/sitemap.xml, docs/blog/index.html (list +
  JSON-LD blogPost), docs/feed.xml, docs/llms.txt (Guides)
  and docs/llms-full.txt. applicationId untouched
  (dev.dzianisv.snapdisk). No money spent, no credentials used.
  Note: a parallel FAQ-page task (PR #26, branch faq-page) was
  working in the same checkout during this run — it landed
  docs/faq.html + footer/sitemap/llms links (merged 8e57473)
  while this article was being written, and this article's first
  commit briefly landed on the FAQ agent's follow-up branch
  (worklog-faq-merge-sha) before being moved here. This branch is
  now based on post-FAQ main (8e57473, rebased onto a800da4 after
  the FAQ follow-up PR #27 merged); the cherry-pick conflicted
  on the shared footer/sitemap/llms touchpoints and was resolved
  keeping both the FAQ and the article links (a regex-based first
  resolution dropped the index.html closing tags and the llms.txt
  Blog/Support + llms-full.txt Blog sections — caught by diff
  review and repaired before this commit).
  Evidence: PR #28 (commit 0a4e011, merge 35bae38, merged
  2026-11-16T02:10:08Z); CI run 43790320527 (build pass, 1m15s)
  green. Live fetch after merge (2026-11-16T02:11Z): article,
  blog home, feed.xml, sitemap.xml, llms.txt, llms-full.txt,
  screenshots-01-settings-defaults.png — all 200; blog home
  serves 7 posts, sitemap + feed contain the new article URL.
  Merge SHA + live fetch recorded in follow-up PR #29.

## 2026-11-15 — Sixth article live (seo-geo lane, pulled forward from Nov 26)

- Owner: Growth lead (seo-geo lane). State: DONE (pending live
  verification after merge — PR number + CI run + live fetch
  recorded below on merge).
  Article: "How to back up photos to an external drive on Android" —
  https://dzianisv.github.io/photo-backup/backup-photos-to-external-drive.html
  (docs/backup-photos-to-external-drive.html, same navy/cyan template as
  the first five articles, TL;DR answer first, real steps, manual
  alternative, callout, footer link). Targets the searches.md
  query 2 phrase "backup photos to external drive android" — MEDIUM
  difficulty, broader intent, full-description phrase — framed honestly
  as a how-to article, no search-volume, demand, download, or rating
  claims.
  Content: SnapDisk v0.1.2 real steps (plug in, tap Backup,
  oldest-first background copy, progress + Pause / Stop,
  size-verified, resume on re-plug); free 5 GB per run verified
  from code (ProPrefs.FREE_BACKUP_BYTES_LIMIT =
  5L * 1024 * 1024 * 1024, enforced in BackupService.kt) — no
  other numbers claimed, no download counts, no ratings. "No
  internet permission" verified from
  app/src/main/AndroidManifest.xml (no INTERNET permission
  declared). Manual alternative: copy photos via the Files app,
  with its limits (no resume, no verification) stated honestly.
  One real screenshot only: docs/screenshots-05-pro.png,
  copied from tool/play-store/screenshots/05-pro.png (the real
  SettingsScreen Pro card — auto-backup + unlimited, $9.99 once;
  the only screenshot not already used as a lead shot by another
  article; note per tool/play-store/screenshots/README.md the
  billing-connected state is rendered, not reached through the
  live billing flow — replace with a shot through live billing
  on a real device once the Play listing + `pro_unlock` product
  exist). No mockups. JSON-LD Article + FAQPage +
  BreadcrumbList in the head. Linked from the landing index
  footer, added to docs/sitemap.xml, docs/blog/index.html (list +
  JSON-LD blogPost), docs/feed.xml, docs/llms.txt (Guides)
  and docs/llms-full.txt. applicationId untouched
  (dev.dzianisv.snapdisk). No money spent, no credentials used.
  Evidence: PR #21 (commit e26432b, merge f69e62a, merged
  2026-11-15T17:30:00Z); CI run 43741311271 (build pass, 1m8s)
  green. Live fetch after merge (2026-11-15T17:31:30Z): article,
  blog home, feed.xml, sitemap.xml, llms.txt, llms-full.txt,
  screenshots-05-pro.png — all 200; blog home serves 6 posts,
  sitemap + feed contain the new article URL.
  Merge SHA + live fetch recorded in follow-up PR #22.

## 2026-11-14 — Landing hero screenshot swapped to progress notification (owner feedback)

- Owner: Growth lead (seo-geo lane). State: DONE (pending live
  verification after merge — PR number + CI run + live fetch
  recorded below on merge).
  Owner feedback Nov 14 (side chat, with screenshot): the progress
  notification shot "seems to be a better screenshot for a main page"
  than the settings screen. The image he sent is pixel-identical
  (PIL diff: max channel diff 0) to the repo's existing
  docs/screenshots-03-progress.png — the real v0.1.2 smoke-test
  shot of "Backing up photos — 7%, 872.0 KB/12.0 MB, 168.6 KB/s,
  ETA 1m" with Pause / Stop — so no new file was needed.
  Change: docs/index.html Screenshot section now leads with
  screenshots-03-progress.png (new alt + caption); the settings
  screenshot stays as a second figure. applicationId untouched
  (dev.dzianisv.snapdisk). No money spent, no credentials used.
  Evidence: PR #19 (commit 0c51192, merge 9984772); CI run
  43676820467 build pass (1m27s). Live fetch after merge:
  landing page 200, screenshots-03-progress.png is the lead
  screenshot and screenshots-01-settings-defaults.png the second.

## 2026-11-14 — Blog home + RSS on GitHub Pages (owner instruction: real blog, not Muse pages)

- Owner: Growth lead (seo-geo lane). State: DONE (pending live
  verification after merge — PR number + CI run + live fetch
  recorded below on merge).
  Owner instruction Nov 14 19:19 PT: articles must live on a webpage
  with a blog, or Medium/Substack — NOT Muse pages, which he believes
  will disappear. The 5 articles already lived as loose HTML files in
  docs/ on GitHub Pages (his repo, content in git — not a Muse
  artifact), but with no blog home, no index, no feed. This task
  gives them one.
  What shipped: docs/blog/index.html — blog home at
  /photo-backup/blog/ listing all 5 articles (title, Nov 14 2026
  date, 1–2 line excerpt, link), same navy/cyan template, no JS,
  JSON-LD Blog + BlogPosting + BreadcrumbList, one line stating the
  blog lives in this GitHub repo (dzianisv/photo-backup, docs/) —
  version-controlled, no platform to disappear. docs/feed.xml —
  RSS 2.0 feed with all 5 articles. Landing page: top nav
  (SnapDisk · Download · Blog) + Blog link in footer. Every article
  page: back-link to the blog home in the header ("← Blog") and a
  Blog link in the footer. docs/sitemap.xml: blog home added
  (priority 0.9, weekly). docs/llms.txt + docs/llms-full.txt:
  Blog section added (blog home + RSS feed). No article content
  changed; applicationId untouched (dev.dzianisv.snapdisk). No money
  spent, no credentials used, no account created.
  Medium vs Substack (cross-posting, NOT done): recommendation
  Medium — its import tool sets the canonical URL back to the
  GitHub Pages article (no duplicate-content penalty) and reaches
  readers already searching how-tos; Substack is better for an
  email list but has no per-post canonical field and weaker how-to
  discovery. Canonical URLs stay the GitHub Pages ones either way.
  Owner step: create a Medium publication for SnapDisk and grant
  access — then cross-posting of the 5 articles can start.
  Evidence: PR #17 (commits 0a24b59 + 721d6a6, merge 37c4848,
  merged 2026-11-15T03:25:25Z); CI run 43672084303 success (1m14s,
  on head 721d6a6). Live fetch after merge (2026-11-15T03:25:57Z):
  landing, blog home, all 5 articles, feed.xml, sitemap.xml,
  llms.txt, robots.txt — all 200; blog home serves 5 posts +
  durability line, article back-link "← Blog" live, landing nav
  Blog link live, sitemap contains blog URL, feed has 5 items.
  Merge SHA + live fetch recorded in follow-up PR #18.

## 2026-11-14 — Fifth article live (seo-geo lane, pulled forward from Nov 26)

- Owner: Growth lead (seo-geo lane). State: DONE.
  Article: "How to back up photos offline on Android — no cloud,
  no account" —
  https://dzianisv.github.io/photo-backup/backup-photos-offline.html
  (docs/backup-photos-offline.html, same navy/cyan template as the
  first three articles, TL;DR answer first, real steps, manual
  alternative, callout, footer link). Targets the searches.md
  query 4 phrase "photo backup app offline" — low-medium
  difficulty, low relevance (searchers want offline/privacy
  generally, not USB backup specifically) — framed honestly as
  a how-to article on the offline / no-cloud differentiator, no
  search-volume, demand, download, or rating claims.
  Content: SnapDisk v0.1.2 real steps (plug in, tap Backup,
  oldest-first background copy, progress + Pause / Stop,
  size-verified, resume on re-plug); free 5 GB per run verified
  from code (ProPrefs.FREE_BACKUP_BYTES_LIMIT =
  5L * 1024 * 1024 * 1024, enforced in BackupService.kt) — no
  other numbers claimed, no download counts, no ratings. "No
  internet permission" verified from
  app/src/main/AndroidManifest.xml (no INTERNET permission
  declared). Manual alternative: copy photos via the Files app,
  with its limits (no resume, no verification) stated honestly.
  One real screenshot only: docs/screenshots-03-progress.png,
  copied from tool/play-store/screenshots/03-progress.png (real
  12 MiB mid-copy — 7%, measured live in smoke run
  43644126583); no mockups. JSON-LD Article + FAQPage +
  BreadcrumbList in the head. Linked from the landing index
  footer, added to docs/sitemap.xml, docs/llms.txt (Guides)
  and docs/llms-full.txt. applicationId untouched
  (dev.dzianisv.snapdisk). No money spent, no credentials used.
  Numbering: this article was tasked as the fourth, but PR #13
  ("How to copy photos to a USB stick on Android") merged first
  (2026-11-15T01:57:36Z, merge 3af16f3), so this is the fifth
  article live; the branch was rebased on the new main and the
  shared footer / sitemap / llms / Work Log touchpoints
  re-resolved to keep both articles.
  Evidence: PR #14 (commit 8b04bf4, merge a5e81a9); CI run
  43666164946 (build pass, 1m28s) green.
  Merge SHA recorded in follow-up PR #16. Work Log row in the
  same PR.

## 2026-11-14 — Fourth article live (seo-geo lane, pulled forward from Nov 26)

- Owner: Growth lead (seo-geo lane). State: DONE.
  Article: "How to copy photos to a USB stick on Android" —
  https://dzianisv.github.io/photo-backup/copy-photos-to-usb-stick.html
  (docs/copy-photos-to-usb-stick.html, same navy/cyan template as
  the first three articles, TL;DR answer first, real steps, manual
  alternative, callout, footer link). Targets the searches.md
  query 3 phrase "copy photos to usb stick android" — HIGH
  difficulty, how-to intent, dominated by how-to web articles —
  framed honestly as a how-to article, no search-volume, demand,
  download, or rating claims.
  Content: SnapDisk v0.1.2 real steps (plug in, tap Backup,
  oldest-first background copy, progress + Pause / Stop,
  size-verified, resume on re-plug); free 5 GB per run verified
  from code (ProPrefs.FREE_BACKUP_BYTES_LIMIT =
  5L * 1024 * 1024 * 1024, enforced in BackupService.kt) — no
  other numbers claimed, no download counts, no ratings. Manual
  alternative: copy DCIM via the Files app, with its limits
  (no resume, no verification) stated honestly. One real
  screenshot only: docs/screenshots-02-offer.png, copied from
  tool/play-store/screenshots/02-offer.png (real notification —
  "External storage is connected. Backup photos?" — from the
  v0.1.2 smoke run; not used as the lead shot by the other
  three articles); no mockups. JSON-LD
  Article + FAQPage + BreadcrumbList in the head. Linked from
  the landing index footer, added to docs/sitemap.xml,
  docs/llms.txt (Guides) and docs/llms-full.txt. applicationId
  untouched (dev.dzianisv.snapdisk). No money spent, no
  credentials used.
  Evidence: PR #13 (commit fa63f6e, merge
  3af16f3); CI run 43665296144 (build pass, 1m16s) green.
  Merge SHA recorded in follow-up PR #15. Work Log row
  in the same PR.

## 2026-11-14 — Third article live (seo-geo lane, pulled forward from Nov 26)

- Owner: Growth lead (seo-geo lane). State: DONE.
  Article: "How to back up DCIM to OTG storage on Android" —
  https://dzianisv.github.io/photo-backup/backup-dcim-to-otg.html
  (docs/backup-dcim-to-otg.html, same navy/cyan template as the
  first two articles, TL;DR answer first, real steps, manual
  alternative, callout, footer link). Targets the searches.md
  query 5 phrase "backup dcim to otg" — very low difficulty,
  how-to phrase, landing-page article material, not a
  store-title keyword; honest framing in the copy (low-volume
  niche how-to phrase, no search-volume or demand claims).
  Content: SnapDisk v0.1.2 real steps (plug in, tap Backup,
  oldest-first background copy, progress + Pause / Stop,
  size-verified, resume on re-plug); free 5 GB per run verified
  from code (ProPrefs.FREE_BACKUP_BYTES_LIMIT =
  5L * 1024 * 1024 * 1024, enforced in BackupService.kt) — no
  other numbers claimed, no download counts, no ratings. Manual
  alternative: copy DCIM via the Files app, with its limits
  (no resume, no verification) stated honestly. One real
  screenshot only: docs/screenshots-01-settings-defaults.png,
  copied from tool/play-store/screenshots/01-settings-defaults.png
  (real settings screen — From /sdcard/DCIM, To /disk/DCIM —
  from green smoke run 43644126583); no mockups. JSON-LD
  Article + FAQPage + BreadcrumbList in the head. Linked from
  the landing index footer, added to docs/sitemap.xml,
  docs/llms.txt (Guides) and docs/llms-full.txt. applicationId
  untouched (dev.dzianisv.snapdisk). No money spent, no
  credentials used.
  Evidence: PR #11 (commits 51cf782 + 18ff5a0, merge
  89bd5db); CI run 43663465126 (build pass, 1m8s) green.
  Merge SHA recorded in follow-up PR #12. Work Log row
  in the same PR.

## 2026-11-14 — Second article live (seo-geo lane, pulled forward from Nov 26)

- Owner: Growth lead (seo-geo lane). State: DONE.
  Article: "USB photo backup on Android: back up photos to an
  external drive without uploading them" —
  https://dzianisv.github.io/photo-backup/usb-photo-backup.html
  (docs/usb-photo-backup.html, same navy/cyan template as the
  first article, TL;DR answer first, real steps, manual
  alternative, callout, footer link). Targets the searches.md
  supporting winner "usb photo backup" plus the secondary
  phrase "backup photos to external drive android". Content:
  SnapDisk v0.1.2 real steps (plug in, tap Backup, oldest-first
  background copy, progress + Pause / Stop, size-verified,
  resume on re-plug); free 5 GB per run verified from code
  (ProPrefs.FREE_BACKUP_BYTES_LIMIT = 5L * 1024 * 1024 * 1024,
  enforced in BackupService.kt) — no other numbers claimed, no
  download counts, no ratings. Manual alternative: copy DCIM
  via the Files app, with its limits (no resume, no
  verification) stated honestly. One real screenshot only:
  docs/screenshots-04-done.png, copied from
  tool/play-store/screenshots/04-done.png (real 3-file copy —
  "Backed up 3 photos (585.9 KB)" from green smoke run
  43644126583); no mockups. JSON-LD Article + FAQPage +
  BreadcrumbList in the head. Linked from the landing index
  footer, added to docs/sitemap.xml, docs/llms.txt (Guides)
  and docs/llms-full.txt. applicationId untouched
  (dev.dzianisv.snapdisk). No money spent, no credentials used.
  Evidence: PR #9 (commits 1e11fd5 + dec2618, merge da67bac);
  CI runs 43658403126 (build pass, 1m20s) and 43658555360
  (build pass) green; merge SHA recorded in follow-up PR #10
  (CI run 43658798347 green). Work Log row in the same PR. "Second
  article due Nov 26" follow-up in Open is now DONE — pulled
  forward Nov 14.

## 2026-11-14 — First article live (seo-geo lane, pulled forward from Nov 26)

- Owner: Growth lead (seo-geo lane). State: DONE.
  Article: "How to back up photos to a USB drive without
  uploading them" — https://dzianisv.github.io/photo-backup/backup-photos-to-usb.html
  (docs/backup-photos-to-usb.html, mirrored on the Convert264
  first-article pattern: same navy/cyan template, TL;DR answer
  first, real steps, manual alternative, callout, footer link).
  Targets the searches.md winners "photo backup to usb" and
  "photos to usb". Content: SnapDisk v0.1.2 real steps (plug in,
  tap Backup, oldest-first background copy, progress + Pause /
  Stop, size-verified, resume on re-plug); free 5 GB per run
  verified from code (ProPrefs.FREE_BACKUP_BYTES_LIMIT =
  5L * 1024 * 1024 * 1024, enforced in BackupService.kt) — no
  other numbers claimed, no download counts, no ratings. Manual
  alternative: copy DCIM via the Files app, with its limits
  (no resume, no verification) stated honestly. One real
  screenshot only: docs/screenshots-03-progress.png, copied
  from tool/play-store/screenshots/03-progress.png (real
  12 MiB copy, 7%, measured live in smoke run 43644126583);
  no mockups. JSON-LD Article + FAQPage + BreadcrumbList in
  the head. Linked from the landing index footer, added to
  docs/sitemap.xml, docs/llms.txt (Guides) and
  docs/llms-full.txt. applicationId untouched
  (dev.dzianisv.snapdisk). No money spent, no credentials used.
  Evidence: PR #7 (commits 06c2f26 + ddef732, merge c02fecf);
  CI runs 43653618672 (build pass, 1m21s) and 43653778884
  (build pass, 1m4s) green; merge SHA recorded in follow-up
  PR #8 (CI run 43653983406 green, build pass, 1m37s).
  Work Log row in the same PR. "First article due Nov 26"
  follow-up in Open is now DONE — pulled forward Nov 14.

## 2026-11-14 — Play screenshots 02–05 captured (smoke green, all 4 real)

- Owner: Implementation lead. State: DONE.
  Evidence: PR #6 (commits b2557ed + 7e8a8c4, merge 7a2bbfa);
  Android PR green 43643647911 (3m8s) then 43646434186 (3m36s)
  after the screenshot commit; branch smoke 43644126583 success
  (23m7s; 14/14, 0 failures — BackupEngine 4 + Billing 2 +
  Notification 3 + NotificationScreenshot 3 + ProCardScreenshot 1
  + Settings 1, from the run's `smoke-evidence` artifact).
  Screenshots (all viewed + verified 2026-11-14, copied from the
  smoke artifact to `tool/play-store/screenshots/`):
  02-offer (real offer notification in the shade — production post
  path; the emulator has no USB mount event, so the mount trigger
  itself is not exercised), 03-progress (mid-copy of a real 12 MiB
  file — 7%, 872.0 KB/12.0 MB, 168.6 KB/s, ETA 1m, all measured
  live; copy finished + verified after the shot), 04-done (real
  3-file copy — "Backed up 3 photos (585.9 KB)"), 05-pro (real
  SettingsScreen with the billing-connected state injected —
  emulator has no Play Store, so the $9.99 button state is
  labelled in the screenshots README; replace with a live-billing
  shot on a real device once the Play listing + `pro_unlock`
  exist). Play screenshot set now complete (01–05); all 5 are
  emulator shots, interim until Pixel 9a + real-disk shots exist.
  Also fixed smoke-test.sh verdict greps (JUnit XML is under
  connected/debug/, SMOKE lines in per-test logcat files — the old
  greps printed nothing). No production code changed, no version
  bump (v0.1.2 still current), applicationId untouched.

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
  viewed/verified; the remaining 4 (02–05) were captured later the
  same day from green smoke 43644126583 — see the entry above.
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
- Follow-ups: NTFS read-only detection (needs real-disk test).
  First article DONE Nov 14 (pulled forward from Nov 26 — see
  entry above).

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
  present and correct ✓; schema validated by JSON parse + Rich
  Results Test VALID Nov 14 ✓ (see below); page
  renders without JS ✓ (verified over local HTTP + live fetch).
  Rich Results Test RUN Nov 14 (13:26 PST, no account, no sitemap
  submitted): VALID — 1 valid item (SoftwareApplication: SnapDisk,
  UtilitiesApplication, Android, 0.1.2, $0, author Dzianis
  Vashchuk, downloadUrl = v0.1.2 APK), 0 errors; 1 optional
  warning only (aggregateRating missing — expected, no ratings
  yet). FAQPage JSON-LD present in code (2nd @graph node) but not
  surfaced as a detected item by the tool; no error on it.
  Result: https://search.google.com/test/rich-results/result?id=DSSfoBC4CKgasjyPQ-FYIA
  STILL OPEN: sitemap submitted to Google Search Console and Bing
  Webmaster Tools — blocked, needs the owner's Google account;
  do not submit anything that needs his credentials.
  Re-score keywords after launch.
