# SnapDisk — Work Log

## WL-74 — Back up AGM photos to USB article, 43rd how-to (DONE)

- Owner: SEO/GEO lane lead. State: DONE.
  New docs/agm-photos-to-usb.html ("Back up AGM photos to
  a USB drive (no computer)"), following the
  WL-54/WL-55/WL-56/WL-57/WL-58/WL-59/WL-60/WL-61/WL-62/WL-63/
  WL-64/WL-65/WL-66/WL-67/WL-68/WL-69/WL-70/WL-71/WL-72/WL-73
  article template exactly (navy/cyan, TL;DR first, no JS,
  text-only, reuses docs/screenshots-01-settings-defaults.png
  as the lead shot — same reuse as the 8th through 42nd
  articles). Facts verified from code on fresh main: SAF tree
  picker (MainActivity.kt OpenDocumentTree +
  takePersistableUriPermission); oldest-first via MediaStore
  DATE_TAKEN ascending (PhotoQuery.kt, DATE_TAKEN else
  DATE_MODIFIED fallback); free 5 GB per run
  (ProPrefs.FREE_BACKUP_BYTES_LIMIT); Pro $9.99 one-time =
  auto-backup on connect + unlimited size (strings.xml
  pro_price); no INTERNET permission in AndroidManifest.xml;
  FAT32 files >=4 GiB skipped with reason
  (FilesystemInfo.FAT32_MAX_FILE_BYTES); raw byte-stream copy
  (BackupEngine.copyWithResume — no decode/re-encode) with size
  verification after copy. Search evidence (Nov 17, 2026 live
  web search): there is no AGM-specific phone-to-USB guide in
  the top results at all — the AGM-specific results are user
  manuals and a screenshot guide, and the rest are generic
  manual guides. Google Photos Community, "How do I back up
  photos to a plug in drive"
  (support.google.com/photos/thread/443333707/how-do-i-back-up-photos-to-a-plug-in-drive?hl=en
  — download the photos from Google Photos cloud storage to
  the phone, then move the photos and files to USB with Files
  by Google. Generic Android, and the route starts in the
  cloud, not on the phone); Android Central forum, "Moving
  photos to usb"
  (forums.androidcentral.com/threads/moving-photos-to-usb.973408/
  — use an OTG adapter to plug in a USB drive, and it shows
  up as external storage in the file manager, where you can
  move files to it. Generic Android, and a manual
  file-manager guide); Pixoneye, "How to Transfer Photos from
  Your Phone to a USB Stick"
  (pixoneye.com/how-to-transfer-photos-from-phone-to-usb-stick/
  — copy local originals to compatible USB storage with a
  file manager, or import to a computer first and then copy
  to USB. Generic Android, and the direct route is manual
  select-copy-paste); YouTube, "How to Connect USB Drive to
  Android Phone to Move Photos & Videos (Best Method)"
  (youtube.com/watch?v=PYcbV25CczI — OTG connection and the
  phone's built-in file manager, no computer or internet
  needed. Generic Android, and a manual file-manager guide);
  Dr.Fone, "4 Practical Ways to Transfer Photos from Android
  to USB Flash Drive"
  (drfone.wondershare.com/android-transfer/how-to-transfer-photos-from-android-to-usb.html
  — direct transfer with an OTG adaptor, a PC as a middleman
  via drag and drop, Google Drive, or AirDroid. Generic
  Android, and three of the four ways need a computer or a
  cloud account); CellularNews, "How to Move Pictures to a
  Flash Drive From a Google Phone"
  (cellularnews.com/device-reviews-and-comparisons/smartphones/how-to-move-pictures-to-a-flash-drive-from-google-phone/
  — connect a USB storage device using an OTG adapter and
  copy the photos using the Files app. Generic Android, and
  the direct route is manual select-copy-paste);
  TheGadgetians, "How to Transfer Files From Phone to USB
  Without Computer: Ultimate Guide"
  (thegadgetians.com/how-to-transfer-files-from-phone-to-usb-without-computer/
  — connect a USB drive to Android with an OTG adapter, open
  the file manager, select and copy files, then paste onto
  the USB drive. Generic Android, and the direct route is
  manual select-copy-paste); Photos to USB: OTG Backup (Play
  listing, com.arzdrz.photos_to_usb — the one-tap app
  alternative, known from earlier scoring of this niche; it
  did not surface in this query's top results: copies the
  whole gallery to a pen drive in one tap, copies only new
  photos next time, warns about the FAT32 4 GB limit. Free
  with ads, one-time purchase to remove them. Generic
  Android, not an AGM guide, and it does not promise
  oldest-first order, detailed progress with Pause and Stop,
  or size verification of every file); AGM H6 specs
  (kimovil.com/en/where-to-buy-agm-h6-global-8gb-256gb — USB
  "Mass Storage Yes, USB On-The-Go (OTG) Yes, USB Type C
  Yes", Android 13, 50MP primary camera. A spec listing, not
  a guide — and the confirmation that the H6 has OTG); AGM H6
  Pro specs (kimovil.com/en/where-to-buy-agm-h6-pro — USB
  "Mass Storage Yes, USB On-The-Go (OTG) Yes, USB Type C
  Yes". A spec listing, not a guide — and the confirmation
  that the H6 Pro has OTG); AGM H6 Lite specs
  (phonesdata.com/en/smartphones/agm/agm-h6-lite-5464528/ —
  "USB Yes, USB Type C, USB On-The-Go (OTG)", microSDXC on a
  dedicated slot. A spec listing, not a guide — and the
  confirmation that the H6 Lite has OTG); AGM H6 official
  product page (eu.agmmobile.com/products/agm-h6/ — lists
  "OTG" among the H6's features, a 3-card slot with two SIM
  slots and a dedicated microSD slot that can support up to
  512GB of storage, Android 13, IP68/IP69K/MIL-STD-810H. A
  product page, not a guide — and the confirmation that the
  H6 has OTG and a dedicated card slot); AGM H6 user manual
  (manuals.plus/asin/B0F32DGQR9.pdf — "USB-C Port: For
  charging and data transfer", 50MP primary lens with a 2MP
  macro lens and an 8MP front camera, Android 13, and
  "Drive: Store your content on the cloud, access it from
  anywhere, and share it with others". A manual, not a guide
  — and the confirmation that AGM's own storage route is the
  cloud, not a USB drive); cloud routes — Google Photos syncs
  the gallery to Google's servers, Google backup restores
  data to a new phone from Google's cloud, not to a drive you
  hold; Google Photos' "Storage saver" setting compresses
  photos, and a cloud backup stops working the day you stop
  paying for storage. Neither route gives a folder of plain
  photo files on a USB drive.
  AGM specifics: OTG is across the current AGM H6 family:
  spec tables list USB On-The-Go as Yes for the H6 and the
  H6 Pro, and USB Type-C with USB On-The-Go for the H6 Lite,
  and AGM's own product page lists OTG among the H6's
  features; AGM has no phone-to-USB backup tool of its own —
  the H6 manual's storage mention is Drive, Google's cloud,
  and the stock photo backup is Google Photos; there is no
  AGM backup FAQ on devicesfaq at all — the AGM pages there
  are a scrolling-screenshot guide and a hard-reset guide.
  The H6's 3-card slot gives the microSD card its own
  dedicated slot alongside two Nano SIMs (up to 512GB on
  AGM's own product page), so a card never costs a second
  SIM — unlike the hybrid slots on other rugged phones — but
  a card is still not a USB drive: it stays in the phone and
  needs a reader elsewhere.
  AGM's own tools: Google backup (Settings > Google > All
  services > Backup, "Backup by Google One" on, to Google's
  cloud, 15 GB free) backs the phone up to Google's cloud;
  Google Photos (Photos settings > Backup on) backs photos up
  to Google's cloud; the H6 manual's Drive mention ("Store
  your content on the cloud") is Google's cloud storage; a
  computer copy (USB-C port "for charging and data transfer",
  USB cable, copy the files with the file browser) backs the
  phone up to the PC; a microSD card (dedicated slot, up to
  512GB) is a real local copy, but the card stays in the
  phone and needs a reader elsewhere — none of them writes
  plain photo files to a USB drive automatically.
- Verdict: how-to article material, not a listing keyword.
  Article published Nov 17, 2026 (WL-74, 43rd how-to):
  docs/agm-photos-to-usb.html. Query 42 scored in
  tool/growth/searches.md. Blog index (JSON-LD + post block),
  sitemap.xml, llms.txt, llms-full.txt, feed.xml (43 items)
  updated. PR #116 (article), merge 7026566, CI run 44238026304
  (Android PR, success).

## WL-73 — Back up Oukitel photos to USB article, 42nd how-to (DONE)

- Owner: SEO/GEO lane lead. State: DONE.
  New docs/oukitel-photos-to-usb.html ("Back up Oukitel photos to
  a USB drive (no computer)"), following the
  WL-54/WL-55/WL-56/WL-57/WL-58/WL-59/WL-60/WL-61/WL-62/WL-63/
  WL-64/WL-65/WL-66/WL-67/WL-68/WL-69/WL-70/WL-71/WL-72 article
  template exactly (navy/cyan, TL;DR first, no JS, text-only,
  reuses docs/screenshots-01-settings-defaults.png as the lead
  shot — same reuse as the 8th through 41st articles). Facts
  verified from code on fresh main: SAF tree picker
  (MainActivity.kt OpenDocumentTree +
  takePersistableUriPermission); oldest-first via MediaStore
  DATE_TAKEN ascending (PhotoQuery.kt, DATE_TAKEN else
  DATE_MODIFIED fallback); free 5 GB per run
  (ProPrefs.FREE_BACKUP_BYTES_LIMIT); Pro $9.99 one-time =
  auto-backup on connect + unlimited size (strings.xml
  pro_price); no INTERNET permission in AndroidManifest.xml;
  FAT32 files >=4 GiB skipped with reason
  (FilesystemInfo.FAT32_MAX_FILE_BYTES); raw byte-stream copy
  (BackupEngine.copyWithResume — no decode/re-encode) with size
  verification after copy. Search evidence (Nov 17, 2026 live
  web search): there is no Oukitel-specific phone-to-USB guide
  in the top results at all — the Oukitel-specific results are
  per-model FAQ pages whose backup advice is a computer copy
  or a cloud backup, and the rest are generic manual guides.
  devicesfaq, "Frequently asked questions Oukitel OT6"
  (devicesfaq.com/en/frequently-asked-questions/oukitel-ot6 —
  "How to backup my Oukitel OT6?" The most economical method is
  to connect the Oukitel OT6 to a computer using the USB cable
  and make a copy of the files using the file browser; for a
  cloud backup use Google Drive (15 GB free); for photos and
  videos only, use Google Photos, configured to upload
  automatically or when on Wi-Fi. Oukitel-specific, but
  PC-or-cloud. No phone-to-USB route in it at all);
  devicesfaq, "Frequently asked questions Oukitel OKT3"
  (devicesfaq.com/en/frequently-asked-questions/oukitel-okt3 —
  the same backup advice, word for word, for the OKT3 tablet:
  computer copy as the most economical method, Google Drive for
  cloud backup, Google Photos for photos and videos.
  Oukitel-specific, but PC-or-cloud. No phone-to-USB route in
  it at all); devicesfaq, "Frequently asked questions Oukitel
  Ouk C32"
  (devicesfaq.com/en/frequently-asked-questions/oukitel-ouk-c32
  — the same backup advice again for the C32: computer copy,
  Google Drive, Google Photos. Oukitel-specific, but
  PC-or-cloud. No phone-to-USB route in it at all); Dr.Fone,
  "4 Practical Ways to Transfer Photos from Android to USB
  Flash Drive"
  (drfone.wondershare.com/android-transfer/how-to-transfer-photos-from-android-to-usb.html
  — direct transfer with an OTG adaptor, a PC as a middleman
  via drag and drop, Google Drive, or AirDroid. Generic
  Android, and three of the four ways need a computer or a
  cloud account); YouTube, "How to Connect USB Drive to
  Android Phone to Move Photos & Videos (Best Method)"
  (youtube.com/watch?v=PYcbV25CczI — OTG connection and the
  phone's built-in file manager, no computer or internet
  needed. Generic Android, and a manual file-manager guide);
  EpicTechTips, "How to Transfer Pictures from Android Phone
  to USB Flash Drive"
  (epictechtips.com/transfer-pictures-from-android-phone-to-usb-flash-drive/
  — plug the drive in, tap the USB notification, open the file
  manager, select the photos in DCIM, and Move or Copy them to
  the drive — or use a PC as a middleman. Generic Android, and
  the direct route is manual select-copy-paste);
  connect.gmb.org.uk, "How to Transfer Pictures from Android
  to a Flash Drive A Simple Guide"
  (connect.gmb.org.uk/how-to-transfer-pictures-from-android-to-flash-drive/
  — a USB OTG cable for the direct route, or a computer as a
  middleman. Generic Android, and the direct route is manual
  select-copy-paste with the file manager); SoftHandTech,
  "Transfer Photos from Phone to Memory Stick: A
  Comprehensive Guide"
  (softhandtech.com/can-you-transfer-photos-from-phone-to-memory-stick/
  — a computer copy, a direct OTG transfer, or cloud services.
  Generic Android, and the direct route is manual
  select-copy-paste); Photos to USB: OTG Backup (Play listing,
  com.arzdrz.photos_to_usb — the one-tap app alternative, known
  from earlier scoring of this niche; it did not surface in
  this query's top results: copies the whole gallery to a pen
  drive in one tap, copies only new photos next time, warns
  about the FAT32 4 GB limit. Free with ads, one-time purchase
  to remove them. Generic Android, not an Oukitel guide, and it
  does not promise oldest-first order, detailed progress with
  Pause and Stop, or size verification of every file); Oukitel
  WP39 specs
  (mysmartprice.com/mobile/oukitel-wp39-msp619305 — USB "USB
  2.0, USB Type-C, Mass storage device, USB charging, USB
  On-The-Go" with USB OTG Support "Yes", microSD expandable up
  to 2 TB (Hybrid Slot). A spec listing, not a guide — and the
  confirmation that the WP39 has OTG); Oukitel WP35 specs
  (mobileinto.com/gh/Oukitel-WP35/ — "USB Type-C 2.0, OTG",
  11,000 mAh battery, microSD up to 2 TB (Hybrid Slot). A spec
  listing, not a guide — and the confirmation that the WP35 has
  OTG); OUKITEL WP33 Pro user manual
  (manuals.plus/asin/B0CSYNG2FH — "18W Reverse Charging (OTG):
  Use your WP33 Pro as a power bank to charge other devices via
  an OTG cable", microSD expandable up to 1 TB, 22,000 mAh
  battery. A manual, not a guide — and the confirmation that
  the WP33 Pro has OTG); Oukitel WP210 specs
  (oukitel.store/products/oukitel-wp210 — "USB, Type-C, OTG,
  Support", Android 15, 8,800 mAh battery. A store spec listing,
  not a guide — and the confirmation that the WP210 has OTG);
  Oukitel WP100 Plus specs
  (oukitel.store/products/oukitel-wp100-plus-rugged-phone —
  "USB, Type-C, OTG, Yes", 33,000 mAh battery. A store spec
  listing, not a guide — and the confirmation that the WP100
  Plus has OTG); cloud routes — Google Photos syncs the gallery
  to Google's servers, Google backup restores data to a new
  phone from Google's cloud, not to a drive you hold; Google
  Photos' "Storage saver" setting compresses photos, and a
  cloud backup stops working the day you stop paying for
  storage. Neither route gives a folder of plain photo files
  on a USB drive.
  Oukitel specifics: OTG is across the current Oukitel WP
  rugged range: spec tables list USB Type-C 2.0 with OTG for
  the WP39 and the WP35, the WP33 Pro manual lists 18W reverse
  charging over OTG, and the WP210 and WP100 Plus store
  listings list OTG support too; Oukitel has no phone-to-USB
  backup tool of its own — the per-model backup guides all
  point to a computer copy, Google Drive, or Google Photos,
  with a microSD card as the local option; the WP39 and the
  WP35 both take a microSD card up to 2 TB in a hybrid slot
  (card shares the SIM tray with the second SIM), and the WP33
  Pro takes one up to 1 TB, while the stock photo backup is
  Google Photos.
  Oukitel's own tools: Google backup (Settings > Google > All
  services > Backup, "Backup by Google One" on, to Google's
  cloud, 15 GB free) backs the phone up to Google's cloud;
  Google Photos (Photos settings > Backup on) backs photos up
  to Google's cloud; the per-model guides' most economical
  method (USB cable, copy the files with the file browser)
  backs the phone up to the PC; a microSD card (expandable
  with microSD, up to 2 TB on the WP39 and the WP35 in a hybrid
  slot, up to 1 TB on the WP33 Pro) is a real local copy, but
  the card stays in the phone and needs a reader elsewhere —
  none of them writes plain photo files to a USB drive
  automatically.
- Verdict: how-to article material, not a listing keyword.
  Article published Nov 17, 2026 (WL-73, 42nd how-to):
  docs/oukitel-photos-to-usb.html. Query 41 scored in
  tool/growth/searches.md. Blog index (JSON-LD + post block),
  sitemap.xml, llms.txt, llms-full.txt, feed.xml (42 items)
  updated. PR #114 (article), merge 04bc7a3, CI run 44231656679
  (Android PR, success); this row PR #115.

## WL-72 — Back up CAT photos to USB article, 41st how-to (DONE)

- Owner: SEO/GEO lane lead. State: DONE.
  New docs/cat-photos-to-usb.html ("Back up CAT photos to
  a USB drive (no computer)"), following the
  WL-54/WL-55/WL-56/WL-57/WL-58/WL-59/WL-60/WL-61/WL-62/WL-63/
  WL-64/WL-65/WL-66/WL-67/WL-68/WL-69/WL-70/WL-71 article
  template exactly (navy/cyan, TL;DR first, no JS, text-only,
  reuses docs/screenshots-01-settings-defaults.png as the lead
  shot — same reuse as the 8th through 40th articles). Facts
  verified from code on fresh main: SAF tree picker
  (MainActivity.kt OpenDocumentTree +
  takePersistableUriPermission); oldest-first via MediaStore
  DATE_TAKEN ascending (PhotoQuery.kt, DATE_TAKEN else
  DATE_MODIFIED fallback); free 5 GB per run
  (ProPrefs.FREE_BACKUP_BYTES_LIMIT); Pro $9.99 one-time =
  auto-backup on connect + unlimited size (strings.xml
  pro_price); no INTERNET permission in AndroidManifest.xml;
  FAT32 files >=4 GiB skipped with reason
  (FilesystemInfo.FAT32_MAX_FILE_BYTES); raw byte-stream copy
  (BackupEngine.copyWithResume — no decode/re-encode) with size
  verification after copy. Search evidence (Nov 17, 2026 live
  web search): there is no CAT-specific phone-to-USB guide in
  the top results at all — the CAT-specific results are
  per-model FAQ pages whose backup advice is a computer copy
  or a cloud backup, and the rest are generic manual guides.
  devicesfaq, "Frequently asked questions Cat S75"
  (devicesfaq.com/en/frequently-asked-questions/cat-s75 —
  "How to backup my Cat S75?" The most economical method is
  to connect the Cat S75 to a computer using the USB cable
  and make a copy of the files using the file browser; for a
  cloud backup use Google Drive (15 GB free); for photos and
  videos only, use Google Photos, configured to upload
  automatically or when on Wi-Fi. CAT-specific, but
  PC-or-cloud. No phone-to-USB route in it at all);
  VaultDrive: Back Up Phone Photos to a USB Drive, No
  Computer (coticsy.com/vaultdrive.html — a phone-to-USB
  backup app page: plug a flash drive or SSD into your phone,
  choose what to back up and tap Start, copies original
  photos, videos and files straight to the drive. Free to try,
  Pro $19.99 once, no account, Android 8 or later. Generic,
  not a CAT guide); AskFilo, "how to transfer pictures from
  an android phone to a flashdrive"
  (askfilo.com/user-question-answers-smart-solutions/how-to-transfer-pictures-from-an-android-phone-to-a-3436303837303432
  — USB OTG adapter plus a file manager: find the DCIM
  folder, open Camera, select the photos, copy to the USB
  drive — or use a PC or Mac as a middleman. Generic Android,
  and the direct route is manual select-copy-paste); YouTube,
  "How to Connect USB Drive to Android Phone to Move Photos &
  Videos (Best Method)" (youtube.com/watch?v=PYcbV25CczI —
  OTG connection and the phone's built-in file manager, no
  computer or internet needed. Generic Android, and a manual
  file-manager guide); Photos to USB: OTG Backup (Play
  listing, com.arzdrz.photos_to_usb — the one-tap app
  alternative; it did surface in this query's top results:
  copies the whole gallery to a pen drive in one tap, copies
  only new photos next time, warns about the FAT32 4 GB
  limit. Free with ads, one-time purchase to remove them.
  Generic Android, not a CAT guide, and it does not promise
  oldest-first order, detailed progress with Pause and Stop,
  or size verification of every file); Alibaba, "How To
  Quickly Backup Your Phone Photos Without Using Cloud
  Storage"
  (alibaba.com/product-insights/how-to-quickly-backup-your-phone-photos-without-using-cloud-storage.html
  — USB cable + computer, portable SSD/flash drive with OTG +
  file manager, NAS, Wi-Fi transfer apps. Generic Android,
  and the direct route is manual select-copy-paste with the
  file manager); Google Photos Help, "Back up photos &
  videos" (support.google.com/photos/answer/6193313 —
  automatically save your photos and videos to your Google
  Account when you turn on backup. A cloud backup to Google's
  servers — not a phone-to-USB copy); Google Android Help,
  "Back up your phone"
  (support.google.com/android/answer/12760783 — Settings >
  Google > All services > Backup, turn on Backup by Google
  One, tap Back up now. A cloud backup to Google's servers —
  not a phone-to-USB copy); Cat S62 Pro vs Cat S75 specs
  (smartprix.com/mobiles/cat_s62_pro_vs_cat_s75-cpd1xabx2rup_pd1kvjohf8x.php
  — USB "Yes, USB-C v2.0", USB features "USB on-the-go, USB
  Charging" for both models. A spec comparison, not a guide —
  and the confirmation that the S75 and the S62 Pro have
  OTG); Cat S53 specs (m.gsmarena.com/cat_s53-ampp-11766.php
  — "USB Type-C 3.0, OTG", microSDXC card slot using the
  shared SIM slot, 128 GB of storage, 6 GB RAM, 5,500 mAh
  battery. A spec listing, not a guide — and the
  confirmation that the S53 has OTG); Notebookcheck, "The
  CAT S62 Pro Rugged Smartphone Review"
  (notebookcheck.net/The-CAT-S62-Pro-Rugged-Smartphone-Review-Robustness-burdened-by-big-compromises.496845.0.html
  — "Thanks to USB OTG functionality, users can connect
  peripheral devices to the CAT smartphone." A review, not a
  guide — and a second confirmation that the S62 Pro has
  OTG); cloud routes — Google Photos syncs the gallery to
  Google's servers, Google backup restores data to a new
  phone from Google's cloud, not to a drive you hold; Google
  Photos' "Storage saver" setting compresses photos, and a
  cloud backup stops working the day you stop paying for
  storage. Neither route gives a folder of plain photo files
  on a USB drive.
  CAT specifics: OTG is across the current CAT rugged range:
  spec tables list USB Type-C 2.0 with OTG for the S75 and
  the S62 Pro, and USB Type-C 3.0 with OTG for the S53; CAT
  has no phone-to-USB backup tool of its own — the
  per-model backup guides all point to a computer copy,
  Google Drive, or Google Photos, with a microSD card as the
  local option; the S75 and the S53 both take a microSDXC
  card that uses the shared SIM slot (up to 512 GB on the
  S53), while the S62 Pro has a dedicated microSDXC slot,
  while the stock photo backup is Google Photos.
  CAT's own tools: Google backup (Settings > Google > All
  services > Backup, "Backup by Google One" on, to Google's
  cloud, 15 GB free) backs the phone up to Google's cloud;
  Google Photos (Photos settings > Backup on) backs photos up
  to Google's cloud; the per-model guides' most economical
  method (USB cable, copy the files with the file browser)
  backs the phone up to the PC; a microSD card (expandable
  with microSD, uses the second SIM slot) is a real local
  copy, but the card stays in the phone and needs a reader
  elsewhere — none of them writes plain photo files to a USB
  drive automatically.
- Verdict: how-to article material, not a listing keyword.
  Article published Nov 17, 2026 (WL-72, 41st how-to):
  docs/cat-photos-to-usb.html. Query 40 scored in
  tool/growth/searches.md. Blog index (JSON-LD + post block),
  sitemap.xml, llms.txt, llms-full.txt, feed.xml (41 items)
  updated. PR #111 (article), merge 9507fc0, CI run 44225039300
  (Android PR, success); this row PR #112.

## WL-71 — Back up Doogee photos to USB article, 40th how-to (DONE)

- Owner: SEO/GEO lane lead. State: DONE.
  New docs/doogee-photos-to-usb.html ("Back up Doogee
  photos to a USB drive (no computer)"), following the
  WL-54/WL-55/WL-56/WL-57/WL-58/WL-59/WL-60/WL-61/WL-62/WL-63/
  WL-64/WL-65/WL-66/WL-67/WL-68/WL-69/WL-70 article template
  exactly (navy/cyan, TL;DR first, no JS, text-only, reuses
  docs/screenshots-01-settings-defaults.png as the lead shot
  — same reuse as the 8th through 39th articles). Facts
  verified from code on fresh main: SAF tree picker
  (MainActivity.kt OpenDocumentTree +
  takePersistableUriPermission); oldest-first via MediaStore
  DATE_TAKEN ascending (PhotoQuery.kt, DATE_TAKEN else
  DATE_MODIFIED fallback); free 5 GB per run
  (ProPrefs.FREE_BACKUP_BYTES_LIMIT); Pro $9.99 one-time =
  auto-backup on connect + unlimited size (strings.xml
  pro_price); no INTERNET permission in AndroidManifest.xml;
  FAT32 files >=4 GiB skipped with reason
  (FilesystemInfo.FAT32_MAX_FILE_BYTES); raw byte-stream copy
  (BackupEngine.copyWithResume — no decode/re-encode) with size
  verification after copy. Search evidence (Nov 17, 2026 live
  web search): there is no Doogee-specific phone-to-USB
  guide in the top results at all — the Doogee-specific
  results are per-model FAQ pages whose backup advice is a
  computer copy or a cloud backup, and the rest are generic
  manual guides.
  devicesfaq, "Frequently asked questions Doogee S118 Pro"
  (devicesfaq.com/en/frequently-asked-questions/doogee-s118-pro
  — "How to backup my Doogee S118 Pro?" The most economical
  method is to connect the phone to a computer using the USB
  cable and make a copy of the files using the file browser;
  for a cloud backup use Google Drive (15 GB free); for photos
  and videos only, use Google Photos, configured to upload
  automatically or when on Wi-Fi. Doogee-specific, but
  PC-or-cloud. No phone-to-USB route in it at all);
  manuals.plus, "DOOGEE S118 Rugged Phone User Manual"
  (manuals.plus/asin/B0CXJ21G46.pdf — storage management:
  utilize cloud storage services for backups, move large files
  to an installed microSD card. Doogee-specific, but
  cloud-or-card. No phone-to-USB route in it at all); Google
  Android Help, "Back up your phone"
  (support.google.com/android/answer/12760783 — Settings >
  Google > All services > Backup, turn on Backup by Google One,
  tap Back up now. A cloud backup to Google's servers — not a
  phone-to-USB copy); YouTube, "How to Connect USB Drive to
  Android Phone to Move Photos & Videos (Best Method)"
  (youtube.com/watch?v=PYcbV25CczI — OTG connection and the
  phone's built-in file manager, no computer or internet needed.
  Generic Android, and a manual file-manager guide); AnExplorer,
  "How to Transfer Files from Android to USB Drive (OTG, No
  Root)" (anexplorer.io/transfer/phone-to-usb — USB OTG lets the
  phone act as a USB host: plug in a flash drive and copy with
  the file manager, no cloud, no Wi-Fi, no PC. Generic Android,
  and a manual file-manager guide — with a useful warning that
  FAT32 has a 4 GB maximum single file size); YouTube, "How to
  Transfer Photos, Videos and any Files from Android Phone to
  USB Pendrive (USB Drive Tutorial)"
  (youtube.com/watch?v=ch1evyu5PPM — check OTG compatibility,
  connect the drive with an OTG adapter, open the file manager,
  find DCIM, copy or move to the drive. Generic Android, and a
  manual file-manager guide); Dr.Fone, "4 Practical Ways to
  Transfer Photos from Android to USB Flash Drive"
  (drfone.wondershare.com/android-transfer/how-to-transfer-photos-from-android-to-usb.html
  — direct transfer with an OTG adaptor, a PC as a middleman,
  Google Drive, or AirDroid. Generic Android, and three of the
  four ways need a computer or a cloud account); AskFilo, "how
  to transfer pictures from an android phone to a flashdrive"
  (askfilo.com/user-question-answers-smart-solutions/how-to-transfer-pictures-from-an-android-phone-to-a-3436303837303432
  — USB OTG adapter plus a file manager: find the DCIM folder,
  open Camera, select the photos, copy to the USB drive — or use
  a PC or Mac as a middleman. Generic Android, and the direct
  route is manual select-copy-paste); Coolmuster, "4 Ways to
  Transfer Files from Android to USB Flash Drive"
  (coolmuster.com/android/transfer-files-from-android-to-usb-flash-drive.html
  — OTG transfer, a PC tool, a computer copy, or a cloud
  service. Generic Android, and three of the four ways need a
  computer or a cloud account); Photos to USB: OTG Backup (Play
  listing, com.arzdrz.photos_to_usb — the one-tap app
  alternative, known from earlier scoring of this niche; it did
  not surface in this query's top results: copies the whole
  gallery to a pen drive in one tap, copies only new photos
  next time, warns about the FAT32 4 GB limit. Free with ads,
  one-time purchase to remove them. Generic Android, not a
  Doogee guide, and it does not promise oldest-first order,
  detailed progress with Pause and Stop, or size verification
  of every file); Doogee S118 Pro specs
  (m.gsmarena.com/doogee_s118_pro-13631.php — "USB Type-C 2.0,
  OTG", Android 14, 512 GB of storage, microSDXC card slot,
  10,800 mAh battery. A spec listing, not a guide — and the
  confirmation that the S118 Pro has OTG); Doogee S118 Pro
  manual
  (manuals.plus/m/ca5516b6189523d77aea148e8314f310f44b5d975f32fb09053a91350c4d6260
  — "OTG: Support (supports reverse charging)", 512 GB of
  storage, TF card up to 2 TB. A manual, not a guide — and a
  second confirmation that the S118 Pro has OTG); Doogee S98 vs
  S118 Pro specs
  (devicespecifications.com/en/comparison/225f24f7b2 — USB
  features for both models list "Charging, Mass storage,
  On-The-Go". A spec comparison, not a guide — and the
  confirmation that the S98 has OTG too); cloud routes — Google
  Photos syncs the gallery to Google's servers, Google backup
  restores data to a new phone from Google's cloud, not to a
  drive you hold; Google Photos' "Storage saver" setting
  compresses photos, and a cloud backup stops working the day
  you stop paying for storage. Neither route gives a folder of
  plain photo files on a USB drive.
  Doogee specifics: OTG is across the current Doogee rugged
  range: spec tables list USB Type-C 2.0 with OTG for the S118
  Pro, the S118 Pro manual lists OTG with reverse charging, and
  the S98 lists On-The-Go among its USB features; Doogee has no
  phone-to-USB backup tool of its own — the per-model backup
  guides all point to a computer copy, Google Drive, or Google
  Photos, with a microSD card as the local option; the S118 Pro
  and the S118 both have a TF/microSD card slot up to 2 TB, and
  on the S118 Pro the TF card shares the SIM tray with the
  second SIM, while the stock photo backup is Google Photos.
  Doogee's own tools: Google backup (Settings > Google > All
  services > Backup, "Backup by Google One" on, to Google's
  cloud, 15 GB free) backs the phone up to Google's cloud;
  Google Photos (Photos settings > Backup on) backs photos up
  to Google's cloud; the per-model guides' most economical
  method (USB cable, copy the files with the file browser)
  backs the phone up to the PC; a microSD card (move large
  files to an installed card) is a real local copy, but the card
  stays in the phone and needs a reader elsewhere — none of them
  writes plain photo files to a USB drive automatically.
- Verdict: how-to article material, not a listing keyword.
  Article published Nov 17, 2026 (WL-71, 40th how-to):
  docs/doogee-photos-to-usb.html. Query 39 scored in
  tool/growth/searches.md. Blog index (JSON-LD + post block),
  sitemap.xml, llms.txt, llms-full.txt, feed.xml (40 items)
  updated. PR #108 (article), merge e61bd2a, CI run 44219885942
  (Android PR, success); this row PR #109.

## WL-70 — Back up Ulefone photos to USB article, 39th how-to (DONE)

- Owner: SEO/GEO lane lead. State: DONE.
  New docs/ulefone-photos-to-usb.html ("Back up Ulefone
  photos to a USB drive (no computer)"), following the
  WL-54/WL-55/WL-56/WL-57/WL-58/WL-59/WL-60/WL-61/WL-62/WL-63/
  WL-64/WL-65/WL-66/WL-67/WL-68/WL-69 article template exactly
  (navy/cyan, TL;DR first, no JS, text-only, reuses
  docs/screenshots-01-settings-defaults.png as the lead shot
  — same reuse as the 8th through 38th articles). Facts
  verified from code on fresh main: SAF tree picker
  (MainActivity.kt OpenDocumentTree +
  takePersistableUriPermission); oldest-first via MediaStore
  DATE_TAKEN ascending (PhotoQuery.kt, DATE_TAKEN else
  DATE_MODIFIED fallback); free 5 GB per run
  (ProPrefs.FREE_BACKUP_BYTES_LIMIT); Pro $9.99 one-time =
  auto-backup on connect + unlimited size (strings.xml
  pro_price); no INTERNET permission in AndroidManifest.xml;
  FAT32 files >=4 GiB skipped with reason
  (FilesystemInfo.FAT32_MAX_FILE_BYTES); raw byte-stream copy
  (BackupEngine.copyWithResume — no decode/re-encode) with size
  verification after copy. Search evidence (Nov 17, 2026 live
  web search): there is no Ulefone-specific phone-to-USB
  guide in the top results at all — the Ulefone-specific
  results are per-model FAQ pages whose backup advice is a
  computer copy or a cloud backup, and the rest are generic
  manual guides.
  devicesfaq, "Frequently asked questions Ulefone Armor 25T"
  (devicesfaq.com/en/frequently-asked-questions/ulefone-armor-25t
  — "How to backup my Ulefone Armor 25T?" The most economical
  method is to connect the phone to a computer using the USB
  cable and make a copy of the files using the file browser;
  for a cloud backup use Google Drive (15 GB free); for photos
  and videos only, use Google Photos, configured to upload
  automatically or when on Wi-Fi. Ulefone-specific, but
  PC-or-cloud — and the same page confirms the Armor 25T has a
  USB Type-C 2.0, OTG charging port. No phone-to-USB route in
  it at all); devicesfaq, "Frequently asked questions Ulefone
  Armor Pad"
  (devicesfaq.com/en/frequently-asked-questions/ulefone-armor-pad
  — the same backup answer in the same shape: connect the Armor
  Pad to a computer with the USB cable and copy the files, or
  use Google Drive for a cloud backup, or Google Photos for
  photos and videos. Ulefone-specific, but PC-or-cloud — and the
  same page confirms the Armor Pad has a USB Type-C 2.0, OTG,
  magnetic connector charging port. No phone-to-USB route in it
  at all); tutorial.info, "How to Enable Google Backup in
  ULEFONE Armor 5s"
  (tutorial.info/video/how-to-enable-google-backup-in-ulefone-armor-5s-find-google-backup-section
  — Settings > System > Backup, enable "Back up to Google
  Drive", add your Google account. Ulefone-specific, but a cloud
  backup to Google Drive — not a phone-to-USB copy);
  mobilespecs, "How to backup Ulefone Armor 27T"
  (mobilespecs.net/phone/backup/Ulefone/Ulefone_Armor_27T.html —
  the same per-model backup page exists for the Armor 2S, Armor
  2, Power 3L, Power Armor 18 Ultra, and MIX: sync data with
  cloud storage (Google account sync, Google Photos for photos),
  or the expert methods — TWRP Recovery, CWM Recovery with ROM
  Manager, Titanium Backup (needs root). Ulefone-specific, but
  cloud-or-expert — and no phone-to-USB route in any of them);
  AskFilo, "how to transfer pictures from an android phone to
  a flashdrive"
  (askfilo.com/user-question-answers-smart-solutions/how-to-
  transfer-pictures-from-an-android-phone-to-a-3436303837303432
  — USB OTG adapter plus a file manager: find the DCIM folder,
  open Camera, select the photos, copy to the USB drive — or use
  a PC or Mac as a middleman. Generic Android, and the direct
  route is manual select-copy-paste); FotoSafe, "Back up Android
  photos to a USB | FotoSafe"
  (fotosafe.weidisoft.net/en/guides/back-up-android-photos-to-usb/
  — Android 9 or later with USB host/OTG support, a
  data-capable OTG adapter where needed: connect the drive,
  select it as the destination in FotoSafe, review the media,
  start copying — and Android file managers can often copy
  folders such as DCIM/Camera to USB for free. A real
  phone-to-USB guide for a rival app — but generic Android, not
  a Ulefone guide, and it does not promise oldest-first order,
  detailed progress with Pause and Stop, or size verification of
  every file); YouTube, "How to Connect USB Drive to Android
  Phone to Move Photos & Videos (Best Method)"
  (youtube.com/watch?v=PYcbV25CczI — OTG connection and the
  phone's built-in file manager, no computer or internet needed.
  Generic Android, and a manual file-manager guide); TechBloat,
  "How to Transfer Photos from Android to a USB Drive"
  (techbloat.com/how-to-transfer-photos-from-android-to-a-usb-drive.html
  — direct transfer using USB OTG, a PC transfer, or cloud
  services. Generic Android, and two of the three ways need a
  computer or a cloud account); SoftHandTech guide
  (softhandtech.com/can-you-transfer-photos-from-phone-to-memory-stick/
  — a computer, direct OTG transfer, or cloud services;
  generic Android, and two of the three ways need a computer or
  a cloud account); Photos to USB: OTG Backup (Play listing,
  com.arzdrz.photos_to_usb — the one-tap app alternative, known
  from earlier scoring of this niche; it did not surface in this
  query's top results: copies the whole gallery to a pen drive
  in one tap, copies only new photos next time, warns about the
  FAT32 4 GB limit, free with ads, one-time purchase removes ads
  — generic Android, not a Ulefone guide, and it does not
  promise oldest-first order, detailed progress with Pause and
  Stop, or size verification of every file).
  Wider search: Ulefone Armor 28 Ultra specs
  (alza.hu/EN/ulefone-armor-28-ultra-16gb-1tb-black-sleva-d13192025.htm
  — "with fast charging and OTG support", 1 TB of storage, a TF
  card slot. A retailer spec listing, not a guide — and the
  confirmation that the Armor 28 Ultra has OTG); Ulefone Armor
  34 Pro specs
  (alza.de/EN/ulefone-armor-34-pro-16gb-512gb-pulse-black-d12990246.htm
  — Functions list includes OTG, Android 15, 512 GB of storage,
  a TF card slot. A retailer spec listing, not a guide — and the
  confirmation that the Armor 34 Pro has OTG); Ulefone Armor 27
  Pro specs (mobilemaya.net/product/ulefone-armor-27-pro/ —
  "USB Type-C 2.0 with OTG support", 256 GB of storage. A spec
  listing, not a guide — and the confirmation that the Armor 27
  Pro has OTG); Ulefone Armor Mini 20 specs
  (mobilemaya.net/product/ulefone-armor-mini-20/ — storage
  "expandable via USB OTG", USB Type-C 2.0, 256 GB of storage. A
  spec listing, not a guide — and the confirmation that the
  Armor Mini 20 supports USB OTG).
  Ulefone specifics: OTG is across the current Ulefone Armor
  range: spec tables list OTG support for the Armor 28 Ultra
  and the Armor 34 Pro, USB Type-C 2.0 with OTG for the Armor
  27 Pro, and USB OTG expansion for the Armor Mini 20; Ulefone
  has no phone-to-USB backup tool of its own — the per-model
  backup guides all point to a computer copy, Google Drive, or
  Google Photos, with TWRP, CWM, and Titanium Backup (root) for
  experts; the Armor 28 Ultra and the Armor 28 Pro both have a
  TF card slot, and on the Armor 28 Pro the slot is separate
  from the SIM tray, while the Armor 27 Pro has no microSD card
  support mentioned; the stock photo backup is Google Photos.
  Ulefone's own tools: Google backup (Settings > System >
  Backup, "Back up to Google Drive", to Google's cloud, 15 GB
  free) backs the phone up to Google's cloud; Google Photos
  (Photos settings > Backup on) backs photos up to Google's
  cloud; the per-model guides' most economical method (USB
  cable, copy the files with the file browser) backs the phone
  up to the PC; TWRP, CWM, and Titanium Backup are expert
  methods needing an unlocked bootloader or root — none of them
  writes plain photo files to a USB drive automatically.
- Verdict: how-to article material, not a listing keyword.
  Article published Nov 17, 2026 (WL-70, 39th how-to):
  docs/ulefone-photos-to-usb.html. Query 38 scored in
  tool/growth/searches.md. Blog index (JSON-LD + post block),
  sitemap.xml, llms.txt, llms-full.txt, feed.xml (39 items)
  updated. PR #106 (article), merge ddd2daa, CI run 44216765567
  (Android PR, success); this row PR #107.

## WL-69 — Back up Blackview photos to USB article, 38th how-to (DONE)

- Owner: SEO/GEO lane lead. State: DONE.
  New docs/blackview-photos-to-usb.html ("Back up Blackview
  photos to a USB drive (no computer)"), following the
  WL-54/WL-55/WL-56/WL-57/WL-58/WL-59/WL-60/WL-61/WL-62/WL-63/
  WL-64/WL-65/WL-66/WL-67/WL-68 article template exactly
  (navy/cyan, TL;DR first, no JS, text-only, reuses
  docs/screenshots-01-settings-defaults.png as the lead shot
  — same reuse as the 8th through 37th articles). Facts
  verified from code on fresh main: SAF tree picker
  (MainActivity.kt OpenDocumentTree +
  takePersistableUriPermission); oldest-first via MediaStore
  DATE_TAKEN ascending (PhotoQuery.kt, DATE_TAKEN else
  DATE_MODIFIED fallback); free 5 GB per run
  (ProPrefs.FREE_BACKUP_BYTES_LIMIT); Pro $9.99 one-time =
  auto-backup on connect + unlimited size (strings.xml
  pro_price); no INTERNET permission in AndroidManifest.xml;
  FAT32 files >=4 GiB skipped with reason
  (FilesystemInfo.FAT32_MAX_FILE_BYTES); raw byte-stream copy
  (BackupEngine.copyWithResume — no decode/re-encode) with size
  verification after copy. Search evidence (Nov 17, 2026 live
  web search): there is no Blackview-specific phone-to-USB
  guide in the top results at all — the Blackview-specific
  result is Blackview's own generic Android backup guide, and
  the rest are generic manual guides.
  Blackview blog, "6 Ways to backup data on your Android
  phone"
  (blackview.hk/blog/how-to/backup-data-on-android-phone —
  Blackview's own backup guide: 1. Google Account Backup, 2.
  Local Backup to Computer (connect the phone to a computer
  with a USB cable and copy files to the computer or an
  external hard drive), 3. Third-party backup apps (Google
  One, Dropbox, MEGA, Box, Samsung Smart Switch, OneDrive,
  Titanium Backup with root), 4. Backup to External Storage
  ("many Android phones support the use of a microSD card or
  USB OTG storage — you can use these external storage options
  to manually copy your important files, photos, and other data
  for a local backup"), 5. ADB backup (needs a computer and
  command-line tools), 6. Custom recovery and full system
  backup (TWRP, unlock the bootloader, expert level, may void
  warranties). Blackview-specific, but generic Android — and
  the one USB route in it is a manual copy, not an automated
  backup); Blackview store blog, "How do I transfer data from
  my phone's storage if it won't turn on?"
  (store.blackview.hk/en-us/blogs/gadgets-guides/how-transfer-
  data-phone-not-turn-on — connect the phone to a computer,
  check it appears as external storage, navigate through
  folders like DCIM (photos), Downloads, or Documents, and copy
  files directly to the computer — or check cloud backups
  (Google Drive, Dropbox, OneDrive). A PC-or-cloud recovery
  guide, not a phone-to-USB backup); Blackview Active 6 store
  page, Data Migration
  (store.blackview.hk/products/active-6-tablet-price — Data
  Migration transfers all your data from an old device to a new
  one in a few taps. Phone-to-phone, not phone-to-USB);
  AskFilo, "how to transfer pictures from an android phone to
  a flashdrive"
  (askfilo.com/user-question-answers-smart-solutions/how-to-
  transfer-pictures-from-an-android-phone-to-a-3436303837303432
  — USB OTG adapter plus a file manager: find the DCIM folder,
  open Camera, select the photos, copy to the USB drive — or use
  a PC or Mac as a middleman. Generic Android, and the direct
  route is manual select-copy-paste); YouTube, "How to Connect
  USB Drive to Android Phone to Move Photos & Videos (Best
  Method)" (youtube.com/watch?v=PYcbV25CczI — OTG connection
  and the phone's built-in file manager, no computer or
  internet needed. Generic Android, and a manual file-manager
  guide); Dr.Fone guide
  (drfone.wondershare.com/android-transfer/how-to-transfer-
  photos-from-android-to-usb.html — OTG copy, PC drag-and-drop,
  Google Drive, or AirDroid over Wi-Fi; generic Android, and
  most of the ways need a computer or a cloud account);
  Android Central forum, "Moving photos to usb"
  (forums.androidcentral.com/threads/moving-photos-to-
  usb.973408/ — use the OTG adapter, plug in any supported
  external device, and it shows up as external storage in the
  File Manager. Generic Android, and a forum thread, not a
  guide); SoftHandTech guide
  (softhandtech.com/can-you-transfer-photos-from-phone-to-
  memory-stick/ — a computer, direct OTG transfer, or cloud
  services; generic Android, and two of the three ways need a
  computer or a cloud account); EpicTechTips guide
  (epictechtips.com/transfer-pictures-from-android-phone-to-
  usb-flash-drive/ — plug the flash drive in, tap the USB
  notification, select "Transfer photos", open the file
  manager, select the photos, Move to the USB drive — or cloud
  storage, email, or a computer transfer. Generic Android, and
  a manual file-manager guide); Photos to USB: OTG Backup (Play
  listing, com.arzdrz.photos_to_usb — a real phone-to-USB app,
  one-tap whole-gallery copy, only new photos next time,
  warns about the FAT32 4 GB limit, free with ads, one-time
  purchase removes ads — generic Android, not a Blackview
  guide, and it does not promise oldest-first order,
  detailed progress with Pause and Stop, or size
  verification of every file).
  Wider search: Blackview BV9300 specs
  (m.gsmarena.com/_bv9300-12266.php — USB Type-C, OTG; spec
  tables, not a guide — and the confirmation that the BV9300
  has OTG); Blackview BV9300 Pro user manual
  (manuals.plus/asin/B0DHRXS57Q.pdf — OTG functionality is
  supported, enabling the phone to charge other devices; 5W OTG
  reverse charging. A manual, not a guide — and the confirmation
  that the BV9300 Pro has OTG); Blackview BV6200 Pro user
  manual (manuals.plus/ae/1005005996698718 — OTG is listed
  among the features, and reverse charging uses an OTG cable to
  charge other devices. A manual, not a guide — and the
  confirmation that the BV6200 Pro has OTG).
  Blackview specifics: OTG is across the current Blackview
  rugged range: spec tables list USB Type-C with OTG for the
  BV9300, OTG reverse charging for the BV9300 Pro, and OTG for
  the BV6200 Pro; Blackview's own backup guide lists USB OTG as
  an external-storage option you can copy files to by hand; the
  BV9300 and the BV6200 Pro both have a microSDXC slot up to
  1 TB, and on the BV9300 the card uses the shared SIM slot;
  Blackview has no phone-to-USB backup tool of its own — its
  own guide's six ways are a Google account backup, a local
  backup to a computer, third-party backup apps, a manual copy
  to external storage, ADB, and custom recovery, and none of
  them is an automated phone-to-USB copy; Data Migration is
  Blackview's phone-to-phone tool, not a USB backup; the other
  routes are Google's (Google Photos, Google backup). Blackview's
  own tools: Google Account Backup (Settings > Google > Backup,
  "Back up to Google One", to Google's cloud, 15 GB free — which
  Blackview's own guide says may not be sufficient for photos
  and videos) backs the phone up to Google's cloud; Local Backup
  to Computer (USB cable, File Explorer or Finder, copy to the
  computer or an external hard drive) backs the phone up to the
  PC; Backup to External Storage (manually copy files to microSD
  or USB OTG storage) is the manual copy in Method 2 — none of
  them writes plain photo files to a USB drive automatically.
- Verdict: how-to article material, not a listing keyword.
  Article published Nov 17, 2026 (WL-69, 38th how-to):
  docs/blackview-photos-to-usb.html. Query 37 scored in
  tool/growth/searches.md. Blog index (JSON-LD + post block),
  sitemap.xml, llms.txt, llms-full.txt, feed.xml (38 items)
  updated. PR #104 (article), merge a7dca3c, CI run 44209086755
  (Android PR, success); this row PR #105.

## WL-68 — Back up HTC photos to USB article, 37th how-to (DONE)

- Owner: SEO/GEO lane lead. State: DONE.
  New docs/htc-photos-to-usb.html ("Back up HTC
  photos to a USB drive (no computer)"), following the
  WL-54/WL-55/WL-56/WL-57/WL-58/WL-59/WL-60/WL-61/WL-62/WL-63/
  WL-64/WL-65/WL-66/WL-67 article template exactly (navy/cyan,
  TL;DR first, no JS, text-only, reuses
  docs/screenshots-01-settings-defaults.png as the lead shot
  — same reuse as the 8th through 36th articles). Facts
  verified from code on fresh main: SAF tree picker
  (MainActivity.kt OpenDocumentTree +
  takePersistableUriPermission); oldest-first via MediaStore
  DATE_TAKEN ascending (PhotoQuery.kt, DATE_TAKEN else
  DATE_MODIFIED fallback); free 5 GB per run
  (ProPrefs.FREE_BACKUP_BYTES_LIMIT); Pro $9.99 one-time =
  auto-backup on connect + unlimited size (strings.xml
  pro_price); no INTERNET permission in AndroidManifest.xml;
  FAT32 files >=4 GiB skipped with reason
  (FilesystemInfo.FAT32_MAX_FILE_BYTES); raw byte-stream copy
  (BackupEngine.copyWithResume — no decode/re-encode) with size
  verification after copy. Search evidence (Nov 17, 2026 live
  web search): there is no HTC-specific phone-to-USB
  guide in the top results at all — the HTC-specific
  results are HTC's own OTG support pages, a storage-expansion
  guide for the HTC One, and HTC's own PC backup app, and the
  rest are generic manual guides.
  HTC support, "How do I view the files and folders from my USB
  drive?" (U12+)
  (htc.com/mea-en/support/htc-u12-plus/faq/how-do-i-view-the-
  files-and-folders-from-my-usb-drive.html?showcontent=1 —
  HTC's own support page: if the phone supports USB OTG,
  connect a USB OTG connector to the phone and the external USB
  drive, go to Settings > Storage, tap the name of the drive,
  and navigate to the file or folder; HTC File Manager can
  manage the files too. An OTG support page, not a backup
  guide); HTC support, "How do I view the files and folders
  from my USB drive?" (U12 life)
  (htc.com/eu/support/htc-u12-life/howto/how-do-i-view-the-
  files-and-folders-from-my-usb-drive.html — the same page for
  the U12 life: USB OTG, connect the connector, Settings >
  Storage, tap the drive name. An OTG support page, not a
  backup guide); HTC support, "File Manager — Managing files
  and folders in an external USB drive"
  (htc.com/hk-en/support/apps/file-manager/managing-files-and-
  folders-in-an-external-usb-drive.html — HTC's own File
  Manager page: open the app, connect a USB adapter to the
  phone and the drive, and on Android 6 or later a popup walks
  you through granting access to the USB storage, then swipe to
  the USB Storage tab. A manual file-manager guide, not an
  automated backup); AndroidAyuda, "Complete guide to
  expanding mobile storage without a MicroSD slot"
  (en.androidayuda.com/Expand-the-storage-of-your-mobile-even-
  if-it-has-a-microsd-slot/ — written around the HTC One, which
  has no microSD card slot: expand storage with a USB flash
  drive and a USB OTG cable, plug it in, and the phone enters
  USB host mode. A storage-expansion guide, not a
  photo-backup guide — it is about opening files stored on the
  drive, not copying the camera roll to it); HTC Sync Manager
  (htc.com/au/support/htc-sync-manager/howto/backing-up-phone-
  to-computer.html — HTC's own support page: connect the phone
  to a computer, open HTC Sync Manager, Transfer & Backup, Back
  up now. HTC-specific, but it is a desktop app — it backs up
  to the PC, not to a USB drive as plain files — and it is built
  around backing up to a computer, not backing up a healthy
  phone to a drive you hold); Filecroco, "HTC Sync Manager"
  (filecroco.com/download-htc-sync-manager/ — HTC Sync Manager
  has been discontinued, support has ended, and it will never
  be updated. A discontinued PC app, not a phone-to-USB copy);
  Wondershare MobileTrans, "HTC Backup App: An Ultimate Guide
  and Best Alternatives"
  (mobiletrans.wondershare.com/htc/htc-backup-app.html — HTC
  Sync Manager is a freely available desktop application from
  HTC that backs up and restores data to a PC, and the HTC
  Backup app backs up to an HTC or Google account. Both routes
  land on a PC or in a cloud account — neither writes your
  photos as plain files to a USB drive); AnExplorer guide
  (anexplorer.io/features/usb-otg — plug a USB drive in via OTG
  and AnExplorer gives full read/write access, FAT32, exFAT,
  and NTFS; generic Android, not written for HTC — and it is a
  guide for its own app); Dr.Fone guide
  (drfone.wondershare.com/android-transfer/how-to-transfer-
  photos-from-android-to-usb.html — OTG copy, PC drag-and-drop,
  Google Drive, or AirDroid over Wi-Fi; generic Android, and
  most of the ways need a computer or a cloud account);
  AnExplorer guide (anexplorer.io/transfer/phone-to-usb — plug
  in a flash drive via OTG and copy files with a progress bar
  showing speed and time remaining; generic Android, not written
  for HTC — and it is a guide for its own app); Coolmuster
  guide (coolmuster.com/android/transfer-files-from-android-
  to-usb-flash-drive.html — OTG copy, a PC assistant app,
  drag-and-drop through a computer, or a cloud service; generic
  Android, and most of the ways need a computer or a cloud
  account); Photos to USB: OTG Backup (Play listing,
  com.arzdrz.photos_to_usb — a real phone-to-USB app,
  one-tap whole-gallery copy, only new photos next time,
  warns about the FAT32 4 GB limit, free with ads, one-time
  purchase removes ads — generic Android, not an HTC
  guide, and it does not promise oldest-first order,
  detailed progress with Pause and Stop, or size
  verification of every file).
  Wider search: HTC U23 Pro vs HTC Desire 22 Pro specs
  (smartprix.com/mobiles/htc_u23_pro_vs_htc_desire_22_pro-
  cpd1wqtp2ttz_pd1940jof90.php — USB-C v3.0 with USB
  on-the-go for the U23 Pro, USB-C v2.0 with USB on-the-go
  for the Desire 22 Pro; spec tables, not a guide — and the
  confirmation that both current HTC phones have OTG); HTC
  U23 vs HTC Desire 22 Pro specs
  (smartprix.com/mobiles/htc_u23_vs_htc_desire_22_pro-
  cpd1ksw0f1rj_pd1940jof90.php — USB-C v3.0 with USB
  on-the-go for the U23; spec tables, not a guide — and the
  confirmation that the U23 has OTG).
  HTC specifics: OTG is across the current HTC range: spec
  tables list USB-C 3.0 with OTG for the U23 Pro, USB-C 2.0
  with OTG for the Desire 22 Pro, and USB-C 3.0 with OTG for
  the U23; HTC's own support pages for the U12+ and the U12
  life describe viewing files from a USB drive over OTG —
  connect an OTG connector, go to Settings > Storage, and tap
  the name of the drive; the U23 Pro and the Desire 22 Pro
  both have a microSDXC slot, the HTC One has no microSD slot
  at all; HTC has no phone-to-USB backup tool of its own — its
  own tool is HTC Sync Manager, a Windows and Mac desktop app
  that backs pictures, videos, music, contacts, and more up to
  a PC and restores them later, and it is discontinued, support
  ended; the HTC Backup app backs up to an HTC or Google
  account, which is a cloud backup; the other routes are
  Google's (Google Photos, Google backup). HTC's own tools:
  HTC Sync Manager (desktop app, connect with a USB cable,
  Transfer & Backup backs pictures, videos, music, contacts,
  and more up to the PC) backs the phone up to the PC;
  Google Photos (Photos settings > Backup, to Google's cloud,
  15 GB free) backs photos up to Google's cloud; Google backup
  (Settings > System > Backup, Back up to Google Drive)
  restores to a new phone from the cloud — none of them writes
  plain photo files to a USB drive.
- Verdict: how-to article material, not a listing keyword.
  Article published Nov 17, 2026 (WL-68, 37th how-to):
  docs/htc-photos-to-usb.html. Query 36 scored in
  tool/growth/searches.md. Blog index (JSON-LD + post block),
  sitemap.xml, llms.txt, llms-full.txt, feed.xml (37 items)
  updated. PR #102 (article), merge 45feb7d, CI run 44200965044
  (Android PR, success); this row PR #103.

## WL-67 — Back up Lenovo photos to USB article, 36th how-to (DONE)

- Owner: SEO/GEO lane lead. State: DONE.
  New docs/lenovo-photos-to-usb.html ("Back up Lenovo
  photos to a USB drive (no computer)"), following the
  WL-54/WL-55/WL-56/WL-57/WL-58/WL-59/WL-60/WL-61/WL-62/WL-63/
  WL-64/WL-65/WL-66 article template exactly (navy/cyan, TL;DR
  first, no JS, text-only, reuses
  docs/screenshots-01-settings-defaults.png as the lead shot
  — same reuse as the 8th through 35th articles). Facts
  verified from code on fresh main: SAF tree picker
  (MainActivity.kt OpenDocumentTree +
  takePersistableUriPermission); oldest-first via MediaStore
  DATE_TAKEN ascending (PhotoQuery.kt, DATE_TAKEN else
  DATE_MODIFIED fallback); free 5 GB per run
  (ProPrefs.FREE_BACKUP_BYTES_LIMIT); Pro $9.99 one-time =
  auto-backup on connect + unlimited size (strings.xml
  pro_price); no INTERNET permission in AndroidManifest.xml;
  FAT32 files >=4 GiB skipped with reason
  (FilesystemInfo.FAT32_MAX_FILE_BYTES); raw byte-stream copy
  (BackupEngine.copyWithResume — no decode/re-encode) with size
  verification after copy. Search evidence (Nov 17, 2026 live
  web search): there is no Lenovo-specific phone-to-USB
  guide in the top results at all — the Lenovo-specific
  results are store pages for PC backup drives, a PC
  recovery tool, a tablet guide, and Lenovo's own PC backup
  app, and the rest are generic manual guides.
  Lenovo store, WD My Book 6TB USB 3.0 desktop external hard
  drive (lenovo.com/ca/en/p/accessories-and-software/memory-
  and-storage/external-hard-drives/78012364 — a desktop drive
  sold on lenovo.com, with WD Backup software for Windows
  and Mac; a PC backup product, not a phone-to-USB guide);
  Lenovo store, WD My Passport 2TB USB 3.2 Gen 1 external
  hard drive (lenovo.com/ca/en/p/accessories-and-software/
  memory-and-storage/external-hard-drives/78012407 — the
  portable version of the same, WD Backup software for
  Windows and Mac; a PC backup product, not a phone-to-USB
  guide); Wondershare Recoverit, "Download Lenovo OneKey
  Recovery Software" (recoverit.wondershare.com/computer-
  recovery/lenovo-onekey-recovery.html — Lenovo OneKey
  Recovery restores Lenovo and IdeaPad laptops to factory
  settings after a system crash and formats the system,
  erasing all current files; making a recovery USB needs a
  Windows PC and a 16 GB or larger USB drive; a laptop
  recovery tool, not a phone backup at all); Lenovo, "USB
  Flash Drive for Tablets: A Comprehensive Guide"
  (lenovo.com/us/en/knowledgebase/usb-flash-drive-for-
  tablets-a-comprehensive-guide — Lenovo's own guide to
  using a USB flash drive, but written for tablets: expand
  storage, transfer files, back up photos and videos from a
  tablet; a Lenovo guide, but for tablets, not a phone-to-
  USB guide); Lenovo Software Fix (formerly Rescue and Smart
  Assistant) (pcsupport.lenovo.com/bb/en/products/tablets/
  yoga-series/yoga-tab-3-10/za0k/solutions/ht102605-how-to-
  use-rescue-and-smart-assistant-lenovo-moto-smart-assistant-
  smart-device-with-android-system — Lenovo's own support
  page: Software Fix is a Windows PC application for Lenovo
  and Motorola phones and tablets; its Backup & Restore
  backs up pictures, videos, music, and contacts before a
  rescue and restores them after; Lenovo-specific, but a PC
  app — the backup lands on the PC, not on a USB drive as
  plain files — and it is built around rescuing a broken
  phone, not backing up a healthy one); Lenovo Smart
  Assistant download page (gizdev.com/latest-lenovo-smart-
  assistant-for-lenovo-and-motorola — the same tool under its
  older name, Rescue and Smart Assistant, now Software Fix,
  a Windows app that reflashes firmware from Lenovo servers,
  backs up data, transfers to new phones, and manages files
  via USB or Wi-Fi; a PC app again — the backup lives on the
  PC); AnExplorer guide (anexplorer.io/features/usb-otg —
  plug a USB drive in via OTG and AnExplorer gives full
  read/write access, FAT32, exFAT, and NTFS; generic
  Android, not written for Lenovo — and it is a guide for its
  own app); YouTube video (youtube.com/watch?v=ch1evyu5PPM —
  generic OTG + file manager walkthrough: OTG adapter, the
  phone's built-in file manager, select photos in DCIM, Copy
  or Move to the drive); YouTube video
  (youtube.com/watch?v=PYcbV25CczI — generic OTG + file
  manager walkthrough); AnExplorer guide
  (anexplorer.io/transfer/phone-to-usb — plug in a flash
  drive via OTG and copy files with a progress bar showing
  speed and time remaining; generic Android, not written for
  Lenovo — and it is a guide for its own app); ajakteman
  guide (en.ajakteman.com/2026/01/how-to-transfer-photos-
  from-your-phone.html — generic OTG guide: OTG adapter, the
  phone's default file manager, find the photos, Copy or
  Move to the drive; notes some brands require enabling OTG
  in Settings first, by searching Settings for "OTG";
  generic Android, not written for Lenovo);
  umatechnology.org explainer
  (umatechnology.org/how-android-usb-otg-works-with-photo-
  sticks-and-external-drives — connect the drive, open it in
  a file manager such as Files by Google, and copy files;
  Google says Files by Google can access USB storage on
  Android 7.0 and later; generic Android, not written for
  Lenovo); askfilo Q&A (askfilo.com/user-question-answers-
  smart-solutions/how-to-transfer-pictures-from-an-android-
  phone-to-a-3436303837303432 — generic OTG adapter, file
  manager, DCIM, Copy or Move, or a PC/Mac as a middleman;
  generic Android, not written for Lenovo); Dr.Fone guide
  (drfone.wondershare.com/android-transfer/how-to-transfer-
  photos-from-android-to-usb.html — OTG copy, PC
  drag-and-drop, Google Drive, or AirDroid; generic
  Android, most ways need a computer or a cloud account);
  Android Central forum thread
  (forums.androidcentral.com/threads/moving-photos-to-
  usb.973408 — use the OTG adapter to plug in a USB drive,
  and it shows up as external storage in the file manager;
  generic Android, not written for Lenovo); Move Files
  USB/OTG/Internal (Play listing,
  com.fbd.moveapps.sdcard.dp — a generic file-manager app:
  plug in an OTG drive, select hundreds of photos, move
  them to the drive in one go; generic Android, not written
  for Lenovo — and a manual file manager, not an automated
  backup); Photos to USB: OTG Backup (Play listing,
  com.arzdrz.photos_to_usb — a real phone-to-USB app,
  one-tap whole-gallery copy, only new photos next time,
  warns about the FAT32 4 GB limit, free with ads, one-time
  purchase removes ads — generic Android, not a Lenovo
  guide, and it does not promise oldest-first order,
  detailed progress with Pause and Stop, or size
  verification of every file).
  Wider search: Lenovo K13 specs (techspecs.info/lenovo-
  k13 — USB Type-C 2.0, OTG; Android 10 Go edition;
  microSDXC uses shared SIM slot; spec tables, not a guide
  — and the confirmation that the K13 has OTG); Lenovo K13
  Pro specs (smartprix.com/mobiles/oneplus_12_5g_vs_lenovo_
  k13_pro-cpd1y2tx91pl_pd1bgpkm4ok.php — USB-C, USB
  on-the-go; spec tables, not a guide — and the
  confirmation that the K13 Pro has OTG); Lenovo K12 specs
  (smartprix.com/mobiles/lenovo_k12_vs_motorola_moto_g13-
  cpd1t2pcyru0_pd1fdvqw1cc.php — microUSB v2.0, USB
  on-the-go; spec tables, not a guide — and the confirmation
  that even the budget K12 has OTG, over micro-USB); Lenovo
  Legion Phone Duel specs (ubergizmo.com/products/lang/en_us/
  devices/lenovo-legion-phone-duel — USB Type-C, USB 3.1,
  USB OTG; spec tables, not a guide — and the confirmation
  that the Legion Phone Duel has OTG); Lenovo Legion 2 Pro
  specs (gsmarena.com/lenovo_legion_2_pro-10827.php — two
  USB Type-C 2.0 & 3.1, OTG; spec tables, not a guide — and
  the confirmation that the Legion 2 Pro has OTG); Lenovo
  Legion Pro specs (m.gsmarena.com/lenovo_legion_pro-
  10224.php — two USB Type-C, OTG; spec tables, not a guide
  — and the confirmation that the Legion Pro has OTG).
  Lenovo specifics: OTG is across the whole Lenovo phone
  range: spec tables list USB Type-C 2.0 with OTG for the
  K13, USB-C with OTG for the K13 Pro, and two USB-C ports
  with OTG for the Legion Phone Duel and the Legion 2 Pro,
  and even the budget K12 has OTG over micro-USB; on most
  Lenovo phones the drive mounts on plug-in — if nothing
  appears, check Settings > Storage, or search Settings for
  "OTG", since some Android phones need the toggle on first;
  the K13 has a microSDXC slot (shared SIM slot), the
  Legion phones have no card slot; Lenovo has no
  phone-to-USB backup tool of its own — its own tool is
  Software Fix (formerly Rescue and Smart Assistant), a
  Windows PC app that backs pictures, videos, music, and
  contacts up to a PC before a rescue and restores them
  after, and transfers data to a new Lenovo or Motorola
  phone; the other routes are Google's (Google Photos,
  Google backup); Lenovo OneKey Recovery is a laptop tool,
  not a phone backup — it restores Lenovo/IdeaPad laptops to
  factory settings and formats the system; Lenovo's own USB
  flash drive guide is written for tablets; the WD drives on
  lenovo.com ship with WD Backup software for Windows and
  Mac. Lenovo's own tools: Software Fix (PC app, connect
  with a USB cable, Backup & Restore backs pictures, videos,
  music, and contacts up to the PC before a rescue, restores
  after) backs the phone up to the PC; Google Photos (Photos
  settings > Backup, to Google's cloud, 15 GB free) backs
  photos up to Google's cloud; Google backup (Settings >
  System > Backup, Back up to Google Drive) restores to a
  new phone from the cloud; OneKey Recovery restores Lenovo
  and IdeaPad laptops to factory settings and formats the
  system — none of them writes plain photo files to a USB
  drive.
- Verdict: how-to article material, not a listing keyword.
  Article published Nov 17, 2026 (WL-67, 36th how-to):
  docs/lenovo-photos-to-usb.html. Query 35 scored in
  tool/growth/searches.md. Blog index (JSON-LD + post block),
  sitemap.xml, llms.txt, llms-full.txt, feed.xml (36 items)
  updated. PR #100 (article), merge 8f95550, CI run 44191832739
  (Android PR, success); this row PR #101.

## WL-66 — Back up Sharp Aquos photos to USB article, 35th how-to (DONE)

- Owner: SEO/GEO lane lead. State: DONE.
  New docs/sharp-aquos-photos-to-usb.html ("Back up Sharp
  Aquos photos to a USB drive (no computer)"), following the
  WL-54/WL-55/WL-56/WL-57/WL-58/WL-59/WL-60/WL-61/WL-62/WL-63/
  WL-64/WL-65 article template exactly (navy/cyan, TL;DR
  first, no JS, text-only, reuses
  docs/screenshots-01-settings-defaults.png as the lead shot
  — same reuse as the 8th through 34th articles). Facts
  verified from code on fresh main: SAF tree picker
  (MainActivity.kt OpenDocumentTree +
  takePersistableUriPermission); oldest-first via MediaStore
  DATE_TAKEN ascending (PhotoQuery.kt, DATE_TAKEN else
  DATE_MODIFIED fallback); free 5 GB per run
  (ProPrefs.FREE_BACKUP_BYTES_LIMIT); Pro $9.99 one-time =
  auto-backup on connect + unlimited size (strings.xml
  pro_price); no INTERNET permission in AndroidManifest.xml;
  FAT32 files >=4 GiB skipped with reason
  (FilesystemInfo.FAT32_MAX_FILE_BYTES); raw byte-stream copy
  (BackupEngine.copyWithResume — no decode/re-encode) with size
  verification after copy. Search evidence (Nov 17, 2026 live
  web search): there is no Sharp-specific phone-to-USB
  guide in the top results at all — the Sharp-specific
  results are device manuals and generic FAQ pages about PC
  and cloud backup, and the rest are generic manual guides.
  Sharp Aquos Zeta SH-01H user manual
  (manua.ls/sharp/aquos-zeta-sh-01h/manual?p=85 —
  "External Device Connection": connect the handset to a PC
  with the micro USB cable, and use a microSD card as
  external memory for a PC; a manual, not a guide — and it
  needs a PC in between); devicesfaq.com, "Frequently asked
  questions Sharp Aquos Famiredo"
  (devicesfaq.com/en/frequently-asked-questions/sharp-aquos-
  famiredo — "How to backup my Sharp Aquos Famiredo?": the
  most economical method is to connect the phone to a
  computer with the USB cable and copy the files with the
  file browser; for a cloud backup, Google Drive (15 GB
  free) or Google Photos for photos and videos; a generic
  FAQ template, not a phone-to-USB guide); Sharp Aquos Ever
  SH-02J user manual
  (manua.ls/sharp/aquos-ever-sh-02j/manual?p=64 — docomo
  Data Copy backs up phonebook, mail, and media to a
  microSD card or directly to another phone, and Data
  Storage Box backs up call log and music; Sharp-specific,
  but it backs up to a card or another phone — not to a USB
  drive as plain files); devicesfaq.com Aquos model FAQs
  (devicesfaq.com/en/frequently-asked-questions/sharp-aquos-
  xx — the same backup FAQ on the Aquos Xx, Crystal X,
  Zero, R3, and K pages: connect to a computer with the USB
  cable and copy with the file browser, Google Drive for a
  cloud backup, Google Photos for photos and videos; a
  generic template repeated across models, not a
  phone-to-USB guide); AnExplorer guide
  (anexplorer.io/features/usb-otg — plug a USB drive in via
  OTG and AnExplorer gives full read/write access, FAT32,
  exFAT, and NTFS; generic Android, not written for Sharp —
  and it is a guide for its own app); YouTube video
  (youtube.com/watch?v=ch1evyu5PPM — generic OTG + file
  manager walkthrough: OTG adapter, the phone's built-in
  file manager, select photos in DCIM, Copy or Move to the
  drive); YouTube video (youtube.com/watch?v=PYcbV25CczI —
  generic OTG + file manager walkthrough); AnExplorer guide
  (anexplorer.io/transfer/phone-to-usb — plug in a flash
  drive via OTG and copy files with a progress bar showing
  speed and time remaining; generic Android, not written for
  Sharp — and it is a guide for its own app); askfilo Q&A
  (askfilo.com/user-question-answers-smart-solutions/how-to-
  transfer-pictures-from-an-android-phone-to-a-
  3436303837303432 — generic OTG adapter, file manager,
  DCIM, Copy or Move, or a PC/Mac as a middleman; generic
  Android, not written for Sharp); Dr.Fone guide
  (drfone.wondershare.com/android-transfer/how-to-transfer-
  photos-from-android-to-usb.html — OTG copy, PC
  drag-and-drop, Google Drive, or AirDroid; generic
  Android, most ways need a computer or a cloud account);
  Photos to USB: OTG Backup (Play listing,
  com.arzdrz.photos_to_usb — a real phone-to-USB app,
  one-tap whole-gallery copy, only new photos next time,
  warns about the FAT32 4 GB limit, free with ads, one-time
  purchase removes ads — generic Android, not a Sharp
  guide, and it does not promise oldest-first order,
  detailed progress with Pause and Stop, or size
  verification of every file).
  Wider search: Sharp Aquos R10 specs
  (smartprix.com/mobiles/sharp-aquos-r10-ppd19w2x8omg —
  USB-C v3.2, USB on-the-go, Android 15; spec tables, not a
  guide — and the confirmation that the Aquos R10 has OTG);
  Sharp Aquos R9 specs (mobileinto.com/ke/Sharp-Aquos-R9 —
  USB Type-C 2.0, OTG; spec tables, not a guide — and the
  confirmation that the Aquos R9 has OTG); Sharp Aquos
  sense9 specs
  (smartprix.com/mobiles/sharp-aquos-sense-9-ppd17ipy3urx —
  USB-C v3.2, USB on-the-go; spec tables, not a guide —
  and the confirmation that the Aquos sense9 has OTG).
  Sharp specifics: Sharp Aquos phones (R10, R9, and
  sense9) run near-stock Android (the Aquos R10 ships with
  Android 15); OTG is on all three current models: spec
  tables list USB Type-C 3.2 with OTG for the Aquos R10 and
  the Aquos sense9, and USB Type-C 2.0 with OTG for the
  Aquos R9 — no OTG switch on Sharp's near-stock Android,
  so the drive mounts on plug-in; every current Aquos has a
  microSDXC slot; Sharp has no backup tool of its own —
  the routes are Google's (Google Photos, Google backup)
  plus docomo Data Copy on Japan carrier models (backs up
  to a microSD card or another phone; Data Storage Box for
  call log and music); stock file manager is Files by Google
  (Sharp's own FAQ pages point to the Files app for finding
  downloaded files); stock photo backup is Google Photos
  (Sharp's own FAQ pages present Google Photos as the route
  for backing up photos and videos); Sharp phones ship with
  Google apps including the Play Store, so SnapDisk installs
  from the v0.1.2 APK today and from Play once the listing
  is live. Sharp's own tools: Google Photos (Photos
  settings > Backup, to Google's cloud, 15 GB free) backs
  photos up to Google's cloud; Google backup (Settings >
  System > Backup, Back up to Google Drive) restores to a
  new phone from the cloud; docomo Data Copy backs the
  phone up to a microSD card or another phone; Data Storage
  Box backs up call log and music; Sharp's manuals back the
  phone up to a PC via USB cable — none of them writes
  plain photo files to a USB drive.
- Verdict: how-to article material, not a listing keyword.
  Article published Nov 17, 2026 (WL-66, 35th how-to):
  docs/sharp-aquos-photos-to-usb.html. Query 34 scored in
  tool/growth/searches.md. Blog index (JSON-LD + post block),
  sitemap.xml, llms.txt, llms-full.txt, feed.xml (35 items)
  updated. PR #98 (article), merge 0644b1b, CI run 44171900387
  (Android PR, success); this row PR #99.

## WL-65 — Back up Fairphone photos to USB article, 34th how-to (DONE)

- Owner: SEO/GEO lane lead. State: DONE.
  New docs/fairphone-photos-to-usb.html ("Back up Fairphone
  photos to a USB drive (no computer)"), following the
  WL-54/WL-55/WL-56/WL-57/WL-58/WL-59/WL-60/WL-61/WL-62/WL-63
  article template exactly (navy/cyan, TL;DR first, no JS,
  text-only, reuses docs/screenshots-01-settings-defaults.png
  as the lead shot — same reuse as the 8th through 33rd
  articles). Facts verified from code on fresh main: SAF tree
  picker (MainActivity.kt OpenDocumentTree +
  takePersistableUriPermission); oldest-first via MediaStore
  DATE_TAKEN ascending (PhotoQuery.kt, DATE_TAKEN else
  DATE_MODIFIED fallback); free 5 GB per run
  (ProPrefs.FREE_BACKUP_BYTES_LIMIT); Pro $9.99 one-time =
  auto-backup on connect + unlimited size (strings.xml
  pro_price); no INTERNET permission in AndroidManifest.xml;
  FAT32 files >=4 GiB skipped with reason
  (FilesystemInfo.FAT32_MAX_FILE_BYTES); raw byte-stream copy
  (BackupEngine.copyWithResume — no decode/re-encode) with size
  verification after copy. Search evidence (Nov 17, 2026 live
  web search): there is no Fairphone-specific phone-to-USB
  guide in the top results at all — the Fairphone-specific
  result is a forum thread asking how to do it, and the rest
  are generic manual guides. Fairphone community forum,
  "Back up on hard disk"
  (forum.fairphone.com/t/back-up-on-hard-disk/118672 — a
  Fairphone 5 owner asking how to back up to an offline disk
  instead of Google One; the answers are Swift Backup,
  Syncthing, Neo Backup (needs root), FolderSync,
  MyPhoneExplorer (a PC tool), or a USB cable to a PC — a
  discussion, not a guide; one reply's "plug in a USB-C drive
  and do a manual backup in Settings > Backup" is LineageOS,
  not Fairphone OS, corrected in-thread); YouTube video
  (youtube.com/watch?v=PYcbV25CczI — generic OTG + file
  manager walkthrough); FotoSafe guide
  (fotosafe.weidisoft.net/en/guides/back-up-android-photos-
  to-usb/ — connect the drive, select it in FotoSafe, review
  the media and start copying; generic Android, not written
  for Fairphone — and it is a guide for its own app); Dr.Fone
  guide
  (drfone.wondershare.com/android-transfer/how-to-transfer-
  photos-from-android-to-usb.html — OTG copy, PC drag-and-drop,
  Google Drive, or AirDroid; generic Android, most ways need a
  computer or a cloud account); AnExplorer guide
  (anexplorer.io/features/usb-otg — plug a USB drive in via OTG
  and AnExplorer gives full read/write access, FAT32, exFAT,
  and NTFS; generic Android, not written for Fairphone — and it
  is a guide for its own app); softhandtech guide
  (softhandtech.com/can-you-transfer-photos-from-phone-to-
  memory-stick/ — a computer as middleman, direct OTG
  transfer, or a cloud service; generic Android and iPhone,
  not written for Fairphone); Photos to USB: OTG Backup (Play
  listing, com.arzdrz.photos_to_usb — a real phone-to-USB app,
  one-tap whole-gallery copy, only new photos next time, warns
  about the FAT32 4 GB limit, free with ads, one-time purchase
  removes ads — generic Android, not a Fairphone guide, and it
  does not promise oldest-first order, detailed progress with
  Pause and Stop, or size verification of every file).
  Wider search: Fairphone community forum, "Backup your
  phone" guide (forum.fairphone.com/t/backup-your-phone/
  26290/1 — TWRP custom recovery, CWM custom recovery,
  Titanium Backup (needs root), the built-in backup app, or
  Google sync to the cloud; Fairphone-specific, but every
  route is a custom recovery, a rooted backup tool, or a
  cloud sync — none of them puts plain photo files on a
  USB drive); Fairphone community forum, "How to do a full
  backup when rooted?"
  (forum.fairphone.com/t/how-to-do-a-full-backup-when-rooted/
  132065 — the thread's own conclusion is that the only
  official backup way is sending all data to Google, and
  without root there is nothing like a full backup; the
  suggestions are Neo Backup, Swift Backup, and TWRP, which
  the thread notes has not been updated for years;
  Fairphone-specific, but rooted tools and Google's cloud —
  not a phone-to-USB photo backup); /e/OS community, "How
  do I back up my Fairphone 6 with /e/OS?"
  (community.e.foundation/t/how-do-i-back-up-my-fairphone-6-
  with-e-os/77971 — for the de-Googled Fairphone 6, the
  backup is Seedvault (Settings > System > Backup >
  Seedvault), with the backup destination set to the SD
  card; Fairphone-specific, but it backs up apps and data
  to a card, not a folder of plain photo files on a USB
  drive); Fairphone support, "How to back up or restore
  data on your Fairphone"
  (support.fairphone.com/hc/en-us/articles/8951206995601-
  How-to-back-up-or-restore-my-data — Fairphone's own
  support page, and the only backup way it presents is
  Google backup; Fairphone-specific, but it backs the phone
  up to Google's cloud — not to a USB drive); Technobezz's
  Fairphone 5 data transfer guide
  (technobezz.com/how-to-transfer-data-to-fairphone-5 —
  wireless "Copy your data" during setup, a USB cable to
  the old phone, or restore from a Google One backup
  (Settings > System > Backup); Fairphone-specific, but it
  moves data to a new phone or restores it from Google's
  cloud — not a phone-to-USB photo backup); Fairphone 5
  specs (en.wikipedia.org/wiki/Fairphone_5 — USB-C 3.0
  (DisplayPort, USB-OTG); microSDXC (dedicated slot); spec
  tables, not a guide — and the confirmation that the
  Fairphone 5 has OTG; the same page notes the Fairphone 5
  was discontinued in January 2026); Fairphone 4 specs
  (en.wikipedia.org/wiki/Fairphone_4 — USB-C 3.0
  (DisplayPort, USB-OTG); microSD up to 2 TB; spec tables,
  not a guide — and the confirmation that the Fairphone 4
  has OTG); Fairphone 6 specs
  (m.gsmarena.com/compare.php3?idPhone1=13955 — USB
  Type-C 2.0, OTG; microSDXC (dedicated slot); Android 15
  with up to 8 major Android upgrades; spec tables, not a
  guide — and the confirmation that the current Fairphone 6
  has OTG).
  Fairphone specifics: Fairphone phones (4, 5, and 6) run
  Fairphone OS — near-stock Android (the Fairphone 4 now
  runs Android 15; the Fairphone 6 ships with Android 15
  and up to 8 major Android upgrades); OTG is on all three
  current models: spec tables list USB Type-C 3.0 with OTG
  and DisplayPort for the Fairphone 4 and the Fairphone 5,
  and USB Type-C 2.0 with OTG for the Fairphone 6 — no OTG
  switch on Fairphone OS, so the drive mounts on plug-in;
  every current Fairphone has a dedicated microSDXC slot
  (up to 2 TB); Fairphone has no backup tool of its own —
  the routes are Google's (Google Photos, Google backup via
  Google One) plus PC tools (MyPhoneExplorer backs the phone
  up to a PC) and rooted apps (Neo Backup and Titanium
  Backup need a rooted phone; Swift Backup needs root-level
  access via Shizuku for a full backup; FolderSync syncs
  files to a USB drive or anywhere else) and Seedvault on
  the /e/OS Fairphone 6 (Settings > System > Backup >
  Seedvault, destination SD card); stock file manager is
  the Files app (Fairphone owners in the community forum
  copy pictures, videos, and audio "with Files app to the
  SD card"); stock photo backup is Google Photos (Fairphone's
  own support page presents Google backup as the official
  way to back up a Fairphone); Fairphone OS ships with
  Google apps including the Play Store, so SnapDisk installs
  from the v0.1.2 APK today and from Play once the listing
  is live — the /e/OS Fairphone 6 has no Play Store, so the
  APK is the install route there. Fairphone's own tools:
  Google Photos (Photos settings > Backup, to Google's
  cloud, 15 GB free) backs photos up to Google's cloud;
  Google backup (Settings > System > Backup, Back up to
  Google Drive) restores to a new phone from the cloud;
  Seedvault backs apps and data up to a card; MyPhoneExplorer
  backs the phone up to a PC; Swift Backup backs apps and
  data up to a card or a cloud; FolderSync syncs files to a
  USB drive or anywhere else; Neo Backup and Titanium Backup
  need a rooted phone; Google Photos / Google backup back
  photos up to Google's cloud — none of them writes plain
  photo files to a USB drive.
- Verdict: how-to article material, not a listing keyword.
  Article published Nov 17, 2026 (WL-65, 34th how-to):
  docs/fairphone-photos-to-usb.html. Query 33 scored in
  tool/growth/searches.md. Blog index (JSON-LD + post block),
  sitemap.xml, llms.txt, llms-full.txt, feed.xml (34 items)
  updated. PR #96 (article), merge 73d5216, CI run 44149731191
  (Android PR, success); this row PR #97.

## WL-64 — Emulator end-to-end proof: full backup flow green (DONE)

- Owner: Product Lead agent. State: DONE Nov 18, 2026.
  Per Den's Nov 17 rule (product-framework commit 8803ffc):
  the emulator end-to-end pass is the proof; a real device is
  never required and never a gate; never ask him to test on
  his phone. Full backup flow verified on the emulator with
  virtual external storage, every step by evidence, not claims:
  disk detection (StorageReceiver mount broadcast -> offer
  notification) -> offer backup -> copy oldest-first (3 seeded
  photos, byte-verified at the destination) -> progress display
  (notification with progress bar, Pause/Stop actions) -> Pause
  (copy freezes, resume continues byte-exact) -> Stop (finished
  files kept, in-flight .part deleted) -> DONE notification
  ("Backup complete"). Evidence: emulator smoke run
  44219739812, green in 27m20s, JUnit XML in the run's
  smoke-evidence artifact: 18 tests, 0 failures, 0 errors,
  0 skipped — FullFlowInstrumentedTest (mount->offer,
  serviceCopies oldest-first + progress + DONE, pause/resume
  via notification actions, stop via notification action) plus
  NotificationScreenshotTest (offer/progress/done) and the
  engine/billing suites. PR #95 (14 commits), merge 57bc3ab,
  merged 2026-11-18T02:25:54Z; PR CI green.
  Two real findings fixed along the way, both in PR #95: (1) a
  genuine app bug — BackupService.publish() posted the terminal
  DONE/ERROR/LIMIT notification while still foreground, so
  stopForeground() removed it and the user never saw "Backup
  complete" (smoke runs 44199483316, 44203932318, 44210493197);
  fixed by detaching first (stopForeground(false)) and then
  posting via manager.notify(). (2) Two test-harness timing
  lessons, test-only: the DONE title must be asserted promptly
  at DONE time — the harness destroys MainActivity and removes
  both tasks ~15 s after DONE, which cancels the notification,
  so a poll after the ~17 s file verification always saw
  active=[] (run 44210493197's logcat proved the service DID
  post it: its sound played); and the seed DATE_TAKEN converge
  poll is now a warning, not a failure — the MediaStore scanner
  restores DATE_TAKEN from EXIF asynchronously, and with
  taken=0 the effective date (DATE_MODIFIED) still sorts
  sequentially seeded files oldest-first; the enumeration-order
  assert is the real gate.
  "Real-device proof (Pixel 9a + real USB disk)" removed from
  the Blocked (owner) lists in the v0.1.2 and v0.1.1 entries
  below (this row's PR). Honest limit: the emulator has no USB
  mount event, so the mount trigger itself is exercised via a
  synthetic broadcast, not a real OTG plug; the real-disk check
  (NTFS read-only detection) stays open in the Open section,
  route: Firebase Test Lab free tier, never his phone.

## WL-63 — Back up ZTE photos to USB article, 33rd how-to (DONE)

- Owner: SEO/GEO lane lead. State: DONE.
  New docs/zte-photos-to-usb.html ("Back up ZTE photos to a
  USB drive (no computer)"), following the WL-54/WL-55/WL-56/
  WL-57/WL-58/WL-59/WL-60/WL-61/WL-62 article template exactly
  (navy/cyan, TL;DR first, no JS, text-only, reuses
  docs/screenshots-01-settings-defaults.png as the lead shot —
  same reuse as the 8th through 32nd articles). Facts verified
  from code on fresh main: SAF tree picker (MainActivity.kt
  OpenDocumentTree + takePersistableUriPermission); oldest-first
  via MediaStore DATE_TAKEN ascending (PhotoQuery.kt, DATE_TAKEN
  else DATE_MODIFIED fallback); free 5 GB per run
  (ProPrefs.FREE_BACKUP_BYTES_LIMIT); Pro $9.99 one-time =
  auto-backup on connect + unlimited size (strings.xml
  pro_price); no INTERNET permission in AndroidManifest.xml;
  FAT32 files >=4 GiB skipped with reason
  (FilesystemInfo.FAT32_MAX_FILE_BYTES); raw byte-stream copy
  (BackupEngine.copyWithResume — no decode/re-encode) with size
  verification after copy. Search evidence (Nov 17, 2026 live
  web search): there is no ZTE-specific phone-to-USB guide in
  the top results at all — the ZTE-specific results are all
  cloud and PC backup guides. Dr.Fone guide
  (drfone.wondershare.com/android-transfer/how-to-transfer-
  photos-from-android-to-usb.html — OTG copy, PC drag-and-drop,
  Google Drive, or AirDroid; generic Android, most ways need a
  computer or a cloud account); AnExplorer guide
  (anexplorer.io/transfer/phone-to-usb — OTG adapter plus its
  own file manager app, with a progress bar and a FAT32 4 GB
  warning; generic Android, not written for ZTE — and it is a
  guide for its own app); YouTube video
  (youtube.com/watch?v=PYcbV25CczI — generic OTG + file
  manager walkthrough); FoneLab guide
  (fonelab.com/resource/how-to-transfer-photos-from-android-
  phone-to-usb-flash-drive.html — OTG adapter, a PC tool
  (FoneLab HyperTrans), or Samsung Smart Switch; generic
  Android — Smart Switch is Samsung-only — and two of the
  three ways need a computer); softhandtech guide
  (softhandtech.com/can-you-transfer-photos-from-phone-to-
  memory-stick/ — a computer as middleman, direct OTG
  transfer, or a cloud service; generic Android and iPhone,
  not written for ZTE); Photos to USB: OTG Backup (Play
  listing, com.arzdrz.photos_to_usb — a real phone-to-USB app,
  one-tap whole-gallery copy, only new photos next time, warns
  about the FAT32 4 GB limit, free with ads, one-time purchase
  removes ads — generic Android, not a ZTE guide, and it does
  not promise oldest-first order, detailed progress with Pause
  and Stop, or size verification of every file).
  Wider search: mobilespecs.net ZTE Score M backup guide
  (mobilespecs.net/phone/backup/ZTE/ZTE_Score_M.html — Google
  sync (Settings > Google > Sync now, Google Photos for
  photos), then TWRP Recovery, CWM Recovery + ROM Manager, and
  Titanium Backup; ZTE-specific, but every route is a cloud
  sync, a custom recovery, or a rooted backup tool — none of
  them puts plain photo files on a USB drive);
  mobilespecs.net ZTE Axon tablet backup guide
  (mobilespecs.net/phone/backup/ZTE/ZTE_Axon_tablet.html — the
  same pattern: Google sync and Google Photos, then TWRP
  Recovery, CWM Recovery + ROM Manager, Titanium Backup, plus
  SP FlashTool and ADB; ZTE-specific, but cloud sync, recovery
  tools, or PC tools — not a phone-to-USB backup);
  Coolmuster's ZTE backup guide
  (coolmuster.com/android/backup-and-restore-zte.html —
  Coolmuster Android Assistant, Coolmuster Android Backup
  Manager, a Google account, or manual drag-and-drop through
  File Explorer; ZTE-specific, but every route backs the phone
  up to a PC or to Google's cloud — none of them puts plain
  photo files on a USB drive); samsung-messages-backup's ZTE
  backup guide
  (samsung-messages-backup.com/resources/backup-and-restore-
  zte.html — Samsung Backup and Restore, Samsung Messages
  Backup, ZTE Recovery software, or a Google account;
  ZTE-specific in name, but every route is a PC tool or a
  cloud account — none of them puts plain photo files on a USB
  drive); ZTE ZMAX 2 user guide
  (manualshelf.com/manual/zte/z2g111/user-s-guide-english/
  page-107.html — the manual's own pages: File Manager
  "quickly access all of your images, videos, audio clips, and
  other types of files on the phone storage and the microSDHC
  card", with Copy, Move, Share, or Delete on a long press; a
  manual, not a backup guide — but it confirms File Manager is
  the stock file manager on ZTE phones, and that it works by
  manual copy and paste); ZTE Axon 60 specs
  (m.gsmarena.com/zte_axon_60-12988.php — USB Type-C 2.0, OTG;
  Android 13, 256 GB, microSDXC (dedicated slot); spec tables,
  not a guide — and the confirmation that the current Axon 60
  has OTG); ZTE Axon 60 Ultra specs
  (phonebunch.com/phone/zte-axon-60-ultra-5342/ — USB Type-C,
  OTG; Android 14, 512 GB, no card slot; spec tables, not a
  guide — and the confirmation that the current Axon 60 Ultra
  has OTG, but no microSD slot).
  ZTE specifics: ZTE phones (Axon, Blade, and nubia/RedMagic)
  run MyOS — ZTE's Android skin (the Axon 60 runs Android 13
  with MyOS; the Axon 60 Ultra runs Android 14 with MyOS); ZTE
  has no backup tool of its own — the routes are Google's
  (Google Photos, Google backup via Google One) plus PC tools
  (Coolmuster Android Assistant and Coolmuster Android Backup
  Manager back the phone up to a PC; Samsung Backup and
  Restore and Samsung Messages Backup are the other PC tools
  the ZTE guides list; TWRP Recovery and Titanium Backup are
  the other routes the ZTE backup guides list, needing a custom
  recovery or root); stock file manager is File Manager (per
  ZTE's own manuals: it shows phone storage and the microSDHC
  card, with Copy, Move, Share, or Delete); stock photo backup
  is Google Photos (per the ZTE backup guides: "the easiest
  way" not to lose your photos); OTG is genuinely mixed across
  the ZTE lines: spec tables list USB Type-C with OTG for the
  Axon 60, the Axon 60 Ultra, the Axon 30 Ultra, and the Blade
  V10, but the Blade V30, the Blade V8, the Axon 40 Lite, and
  the Blade A73 5G have no OTG — check your own model's specs;
  no OTG switch turned up in ZTE's own manuals, so the drive
  mounts on plug-in on models that support OTG; the microSD
  slot is mixed too (the Axon 60 has a dedicated microSDXC
  slot, the Axon 60 Ultra has none) — check your own model's
  specs; ZTE's current Axon phones list Google as the app
  store. ZTE's own tools: Google Photos (Photos settings >
  Backup, to Google's cloud, 15 GB free) backs photos up to
  Google's cloud; Google backup (Settings > Google > Backup,
  Back up by Google One) restores to a new phone from the
  cloud; Coolmuster Android Assistant and Coolmuster Android
  Backup Manager back the phone up to a PC; Samsung Backup and
  Restore and Samsung Messages Backup back the phone up to a
  PC in one click; TWRP Recovery writes a whole-device backup
  from a custom recovery and Titanium Backup needs a rooted
  phone; Google Photos / Google backup back photos up to
  Google's cloud — none of them writes plain photo files to a
  USB drive.
- Verdict: how-to article material, not a listing keyword.
  Article published Nov 17, 2026 (WL-63, 33rd how-to):
  docs/zte-photos-to-usb.html. Query 32 scored in
  tool/growth/searches.md. Blog index (JSON-LD + post block),
  sitemap.xml, llms.txt, llms-full.txt, feed.xml (33 items)
  updated. PR #93 (article), merge 3218cfb, CI run 44138590332
  (Android PR, success); this row PR #94.

## WL-62 — Back up TCL photos to USB article, 32nd how-to (DONE)

- Owner: SEO/GEO lane lead. State: DONE.
  New docs/tcl-photos-to-usb.html ("Back up TCL photos to a
  USB drive (no computer)"), following the WL-54/WL-55/WL-56/
  WL-57/WL-58/WL-59/WL-60/WL-61 article template exactly
  (navy/cyan, TL;DR first, no JS, text-only, reuses
  docs/screenshots-01-settings-defaults.png as the lead shot —
  same reuse as the 8th through 31st articles). Facts verified
  from code on fresh main: SAF tree picker (MainActivity.kt
  OpenDocumentTree + takePersistableUriPermission); oldest-first
  via MediaStore DATE_TAKEN ascending (PhotoQuery.kt, DATE_TAKEN
  else DATE_MODIFIED fallback); free 5 GB per run
  (ProPrefs.FREE_BACKUP_BYTES_LIMIT); Pro $9.99 one-time =
  auto-backup on connect + unlimited size (strings.xml
  pro_price); no INTERNET permission in AndroidManifest.xml;
  FAT32 files >=4 GiB skipped with reason
  (FilesystemInfo.FAT32_MAX_FILE_BYTES); raw byte-stream copy
  (BackupEngine.copyWithResume — no decode/re-encode) with size
  verification after copy. Search evidence (Nov 17, 2026 live
  web search): there is no TCL-specific phone-to-USB guide in
  the top results at all — the TCL-specific results are all
  cloud and PC backup guides. Dr.Fone guide
  (drfone.wondershare.com/android-transfer/how-to-transfer-
  photos-from-android-to-usb.html — OTG copy, PC drag-and-drop,
  Google Drive, or AirDroid; generic Android, most ways need a
  computer or a cloud account); AnExplorer guide
  (anexplorer.io/transfer/phone-to-usb — OTG adapter plus its
  own file manager app, with a progress bar and a FAT32 4 GB
  warning; generic Android, not written for TCL — and it is a
  guide for its own app); YouTube video
  (youtube.com/watch?v=PYcbV25CczI — generic OTG + file
  manager walkthrough); softhandtech guide
  (softhandtech.com/can-you-transfer-photos-from-phone-to-
  memory-stick/ — a computer as middleman, direct OTG transfer,
  or a cloud service; generic Android and iPhone, not written
  for TCL); thegadgetians guide
  (thegadgetians.com/how-to-transfer-files-from-phone-to-usb-
  without-computer/ — OTG adapter plus the file manager, or a
  Lightning adapter for iPhones; generic Android and iPhone,
  not written for TCL); Photos to USB: OTG Backup (Play listing,
  com.arzdrz.photos_to_usb — a real phone-to-USB app, one-tap
  whole-gallery copy, only new photos next time, warns about the
  FAT32 4 GB limit, free with ads, one-time purchase removes ads
  — generic Android, not a TCL guide, and it does not promise
  oldest-first order, detailed progress with Pause and Stop, or
  size verification of every file).
  Wider search: mobilespecs.net TCL 50 XL backup guide
  (mobilespecs.net/phone/backup/TCL/TCL_50_XL.html — Google
  sync (Settings > Google > Synchronization settings, Google
  Photos for photos), then TWRP Recovery, CWM Recovery + ROM
  Manager, and Titanium Backup; TCL-specific, but every route
  is a cloud sync, a custom recovery, or a rooted backup tool —
  none of them puts plain photo files on a USB drive);
  mobilespecs.net TCL 40 X backup guide
  (mobilespecs.net/phone/backup/TCL/TCL_40_X.html — the same
  pattern: Google sync and Google Photos, then TWRP, CWM, and
  Titanium Backup; TCL-specific, but cloud sync or recovery
  tools — not a phone-to-USB backup); mobilespecs.net TCL 40
  Pro backup guide
  (mobilespecs.net/phone/backup/TCL/TCL_40_Pro.html — the same
  pattern again; TCL-specific, but cloud sync or recovery tools
  — not a phone-to-USB backup); mobilespecs.net TCL 30 V 5G
  backup guide
  (mobilespecs.net/phone/backup/TCL/TCL_30_V_5G.html — the same
  pattern; TCL-specific, but cloud sync or recovery tools — not
  a phone-to-USB backup); TCL 30 5G user manual
  (manualowl.com/m/TCL/30-5G/Manual/689562?page=46 — the
  manual's own pages: Google Photos "stores and backs up your
  photos and videos", and Files "displays all data stored on the
  phone, including both external microSD card and internal
  storage"; a manual, not a backup guide — but it confirms
  Google Photos is the stock photo backup and Files is the stock
  file manager, and the same manual documents OTG and the USB
  connection setting); TCL's own support FAQ
  (tcl.com/eu/en/support/faq/37307 — when the phone connects to
  a PC, pick the mode under Settings > Connections > USB: File
  transfer, Transfer photos (PTP), No data transfer, or Charge
  this device; a support FAQ, not a backup guide — but it
  confirms where TCL puts the USB mode, and that the default is
  charge-only); Coolmuster's laptop guide
  (coolmuster.com/android/transfer-photos-from-android-to-
  laptop.html — a PC tool (Coolmuster Android Assistant), File
  Explorer drag-and-drop, Google Photos, or Google Drive;
  generic Android — TCL appears only in the compatibility list,
  not as a guide of its own — and every way needs a laptop or a
  cloud account); TCL 50 XE NxtPaper specs
  (gsmarena.com.bd/tcl-50-xe-nxtpaper/ — USB Type-C, OTG Yes;
  Android 14, 128 GB, microSDXC; spec tables, not a guide — and
  the confirmation that the current TCL 50 line has OTG); TCL 50
  XL NxtPaper specs (gsmfind.com/tcl-50-xl-nxtpaper.html — USB
  Type-C, OTG; Android 14, 128 GB 6 GB RAM, microSDXC; spec
  tables, not a guide).
  TCL specifics: TCL phones (50, 40, and 30 series) run TCL UI
  — TCL's Android skin (the TCL 40 X runs Android 13 with TCL
  UI; the 50 XL and 50 XE run Android 14); TCL has no backup
  tool of its own — the routes are Google's (Google Photos,
  Google backup via Google One) plus PC tools (Coolmuster
  Android Assistant backs the phone up to a PC; TWRP Recovery
  and Titanium Backup are the other routes the TCL backup guides
  list, needing a custom recovery or root); stock file manager
  is Files (per the TCL 30 5G manual: it shows internal storage
  and the microSD card); stock photo backup is Google Photos
  (per the same manual: Google Photos "stores and backs up your
  photos and videos"); OTG: spec tables list USB Type-C with OTG
  for the 50 XL NxtPaper and the 50 XE NxtPaper, and TCL's own
  user manual documents OTG on the TCL 30 5G; TCL UI has no OTG
  switch to enable — TCL's own documentation describes no on/off
  toggle, so the drive mounts on plug-in (unlike Funtouch OS and
  HyperOS, which have a switch); TCL's USB mode setting
  (Settings > Connections > USB) is for connecting to a
  computer, not for a drive, and its default is charge-only;
  most current TCL models have a microSDXC slot (the 50 XL, 50
  XE, 40 X, and 30 V 5G all do) — check your own model's specs;
  all current TCL phones are USB-C; TCL phones sold in the US
  and Europe ship with Google Play. TCL's own tools: Google
  Photos (Photos settings > Backup, to Google's cloud, 15 GB
  free) backs photos up to Google's cloud; Google backup
  (Settings > Google > Backup, Back up by Google One) restores
  to a new phone from the cloud; Coolmuster Android Assistant
  backs the phone up to a PC; TWRP Recovery writes a
  whole-device backup from a custom recovery and Titanium Backup
  needs a rooted phone; Google Photos / Google backup back
  photos up to Google's cloud — none of them writes plain photo
  files to a USB drive.
- Verdict: how-to article material, not a listing keyword.
  Article published Nov 17, 2026 (WL-62, 32nd how-to):
  docs/tcl-photos-to-usb.html. Query 31 scored in
  tool/growth/searches.md. Blog index (JSON-LD + post block),
  sitemap.xml, llms.txt, llms-full.txt, feed.xml (32 items)
  updated. PR #91 (article), merge 799c36b, CI run 44127326513
  (Android PR, success); this row PR #92.

## WL-61 — Back up iQOO photos to USB article, 31st how-to (DONE)

- Owner: SEO/GEO lane lead. State: DONE.
  New docs/iqoo-photos-to-usb.html ("Back up iQOO photos to a
  USB drive (no computer)"), following the WL-54/WL-55/WL-56/
  WL-57/WL-58/WL-59/WL-60 article template exactly (navy/cyan,
  TL;DR first, no JS, text-only, reuses
  docs/screenshots-01-settings-defaults.png as the lead shot —
  same reuse as the 8th through 30th articles). Facts verified
  from code on fresh main: SAF tree picker (MainActivity.kt
  OpenDocumentTree + takePersistableUriPermission); oldest-first
  via MediaStore DATE_TAKEN ascending (PhotoQuery.kt, DATE_TAKEN
  else DATE_MODIFIED fallback); free 5 GB per run
  (ProPrefs.FREE_BACKUP_BYTES_LIMIT); Pro $9.99 one-time =
  auto-backup on connect + unlimited size (strings.xml
  pro_price); no INTERNET permission in AndroidManifest.xml;
  FAT32 files >=4 GiB skipped with reason
  (FilesystemInfo.FAT32_MAX_FILE_BYTES); raw byte-stream copy
  (BackupEngine.copyWithResume — no decode/re-encode) with size
  verification after copy. Search evidence (Nov 17, 2026 live
  web search): there is no iQOO-specific phone-to-USB guide in
  the top results at all — the iQOO-specific results are all PC
  and cloud backup guides. Coolmuster iQOO photos guide
  (coolmuster.com/android/back-up-iqoo-photos.html — the one
  iQOO-specific photos guide in the top results, and every one
  of its five ways needs a cloud account or a computer: iQOO
  Cloud Backup, Google Photos, vivo Mobile Assistant (a PC
  tool), Coolmuster Android Backup Manager (a PC tool), and
  drag-and-drop to a PC; iQOO-specific, but not phone-to-USB);
  Coolmuster iQOO backup and restore guide
  (coolmuster.com/android/iqoo-backup-restore.html — iQOO Cloud
  Backup, a Google account, a USB cable to a PC, and the
  Coolmuster PC tool; iQOO-specific, and three of the four ways
  need a cloud account or a computer; the USB-cable way copies
  to the computer, not to a drive); Coolmuster iQOO to Mac guide
  (coolmuster.com/android/back-up-iqoo-to-mac.html — a PC tool,
  Android File Transfer to a Mac, vivo Cloud, and EasyShare;
  iQOO-specific, and every way needs a Mac or a cloud account);
  Coolmuster iQOO to PC guide
  (coolmuster.com/android/sync-iqoo-to-pc.html — a PC tool,
  VIVOCloud, a USB cable, Bluetooth, Phone Link, and an SD card;
  iQOO-specific, and every way syncs to a PC or a cloud — none
  of them puts plain photo files on a USB drive); firmwarespro
  (firmwarespro.com/pc/vivo-iqoo-neo-7-se-detail — a
  troubleshooting page for getting one iQOO model to show up on
  a PC over a USB cable; iQOO-specific, but it is a PC-connection
  fix, not a backup guide at all). The rest are generic Android
  guides: Dr.Fone guide
  (drfone.wondershare.com/android-transfer/how-to-transfer-
  photos-from-android-to-usb.html — OTG copy, PC drag-and-drop,
  Google Drive, or AirDroid; generic Android, most ways need a
  computer or a cloud account); Coolmuster generic USB guide
  (coolmuster.com/android/transfer-files-from-android-to-usb-
  flash-drive.html — OTG copy, a PC tool, or a cloud service;
  generic Android, two of the three ways need a computer);
  Coolmuster generic photos guide
  (coolmuster.com/android/transfer-photos-from-android-to-usb-
  flash-drive.html — drag-and-drop through a computer, OTG copy,
  a PC tool, Google Drive, or AirDroid; generic Android, and four
  of the five ways need a computer or a cloud account); YouTube
  video (youtube.com/watch?v=PYcbV25CczI — generic OTG + file
  manager walkthrough); YouTube video
  (youtube.com/watch?v=ch1evyu5PPM — same generic route: check
  OTG, OTG adapter, file manager, Copy or Move, safely eject);
  askfilo guide (askfilo.com — OTG adapter plus the file manager,
  or a PC as a middleman; generic Android, not written for
  iQOO); FoneLab guide (fonelab.com — OTG adapter plus the file
  manager, PC File Explorer drag-and-drop, or the HyperTrans PC
  tool; generic Android, and two of the three ways need a
  computer); epictechtips guide (epictechtips.com — OTG adapter
  plus the file manager, or a PC as a middleman, or a cloud
  service; generic Android, not written for iQOO).
  Wider search: umatechnology iQOO to PC guide
  (umatechnology.org — a USB cable to a PC, File Transfer mode,
  drag-and-drop on the computer, or Wi-Fi apps; iQOO-specific,
  but it connects the phone to a PC — not a phone-to-USB backup);
  manuals.plus OTG tutorial (manuals.plus — confirms Oppo, Vivo,
  iQOO, Realme, and OnePlus phones can only use OTG when the
  function is enabled in Settings, and that the switch turns
  itself off again after 5 to 10 minutes without use; an OTG
  how-to, not a backup guide — but it confirms the iQOO OTG
  switch and its timeout); beinfora OTG guide (beinfora.com — a
  general OTG explainer that names Funtouch OS among the skins
  that enforce automated security timeouts disabling OTG until it
  is manually enabled in system settings; a general guide, not an
  iQOO backup guide — but it confirms the Funtouch OS OTG
  timeout); Photos to USB: OTG Backup (Play listing,
  com.arzdrz.photos_to_usb — a real phone-to-USB app, one-tap
  whole-gallery copy, only new photos next time, warns about the
  FAT32 4 GB limit, free with ads, one-time purchase removes ads
  — generic Android, not an iQOO guide, and it does not promise
  oldest-first order, detailed progress with Pause and Stop, or
  size verification of every file); Google Photos (Play listing,
  com.google.android.apps.photos — 15 GB cloud storage free;
  cloud, not a USB copy); GSMArena iQOO 13 specs
  (m.gsmarena.com/vivo_iqoo_13-ampp-13462.php — USB Type-C 3.2
  with OTG; Android 15 with Funtouch 15 on the international
  model and OriginOS 5 on the China model; spec tables, not a
  guide — and the confirmation that the current iQOO flagship
  runs Funtouch OS outside China with OTG); GSMArena iQOO 12 /
  Neo 10 / Z10 Turbo Pro specs
  (m.gsmarena.com/compare.php3?idPhone1=12691&idPhone2=13462 —
  USB Type-C 2.0 with OTG for the iQOO 12, the Neo 10, and the
  Z10 Turbo Pro; spec tables, not a guide).
  iQOO specifics: iQOO is Vivo's sub-brand and runs Funtouch OS
  outside China (the iQOO 13 runs Android 15 with Funtouch 15 on
  the international model and OriginOS 5 on the China model);
  stock file manager is File Manager (the Funtouch OS file
  manager — a category-based browser: Images, Videos, Audio,
  Documents, Downloads); gallery app is the Albums app (the
  iQOO guides list photos syncing through the Albums app or
  Google Photos, so the gallery is Vivo's own and Google Photos
  is the cloud backup behind it); iQOO's own backup routes are
  iQOO Cloud Backup (powered by vivo's cloud service — gallery
  sync and backup to a vivo account, free storage limited),
  Google's (Google Photos, Google backup via Google One), and PC
  tools (vivo Mobile Assistant, Coolmuster Android Backup Manager
  — both back the phone up to a computer; EasyShare moves data to
  another phone or backs it up to a PC); OTG: spec tables list
  USB Type-C 3.2 with OTG for the iQOO 13 and USB Type-C 2.0 with
  OTG for the iQOO 12, the Neo 10, and the Z10 Turbo Pro;
  whether your model has a microSD slot varies by model — check
  your own model's specs; on Funtouch OS OTG is off by default —
  enable it in Settings > More settings > OTG (search Settings
  for "OTG" if you can't find it), and Vivo's own FAQ says the
  switch turns itself off if it is not used in 5 minutes (same
  switch as the Vivo guide, WL-52, verified there); all current
  iQOO phones are USB-C. iQOO's own tools: iQOO Cloud Backup
  (sign in with a vivo account, backs the phone up to vivo's
  servers) and Google backup (Settings > Google > Backup, Back
  up by Google One) restore to a new phone from the cloud;
  Google Photos (Photos settings > Backup, to Google's cloud,
  15 GB free) backs photos up to Google's cloud; vivo Mobile
  Assistant and Coolmuster Android Backup Manager back the phone
  up to a PC; EasyShare moves data to another phone or backs it
  up to a PC; Google Photos / Google One / iQOO Cloud back
  photos up to their clouds — none of them writes plain photo
  files to a USB drive.
- Verdict: how-to article material, not a listing keyword.
  Article published Nov 17, 2026 (WL-61, 31st how-to):
  docs/iqoo-photos-to-usb.html. Query 30 scored in
  tool/growth/searches.md. Blog index (JSON-LD + post block),
  sitemap.xml, llms.txt, llms-full.txt, feed.xml (31 items)
  updated. PR #89 (article), merge d6e0632, CI run 44115973167
  (Android PR, success); this row PR #90.

## WL-60 — Back up Redmi photos to USB article, 30th how-to (DONE)

- Owner: SEO/GEO lane lead. State: DONE.
  New docs/redmi-photos-to-usb.html ("Back up Redmi photos to a
  USB drive (no computer)"), following the WL-54/WL-55/WL-56/
  WL-57/WL-58/WL-59 article template exactly (navy/cyan, TL;DR
  first, no JS, text-only, reuses
  docs/screenshots-01-settings-defaults.png as the lead shot —
  same reuse as the 8th through 29th articles). Facts verified
  from code on fresh main: SAF tree picker (MainActivity.kt
  OpenDocumentTree + takePersistableUriPermission); oldest-first
  via MediaStore DATE_TAKEN ascending (PhotoQuery.kt, DATE_TAKEN
  else DATE_MODIFIED fallback); free 5 GB per run
  (ProPrefs.FREE_BACKUP_BYTES_LIMIT); Pro $9.99 one-time =
  auto-backup on connect + unlimited size (strings.xml
  pro_price, ProPrefs.PRO_PRICE_LABEL); no INTERNET permission
  in AndroidManifest.xml; FAT32 files >=4 GiB skipped with
  reason (FilesystemInfo.FAT32_MAX_FILE_BYTES); raw byte-stream
  copy (BackupEngine.copyWithResume — no decode/re-encode)
  with size verification after copy. Brand correction: the
  WL-60 brief's candidate list (Realme, Vivo, Honor) was
  already covered — WL-51 Realme (21st how-to, commit
  de132ee), WL-52 Vivo (22nd, commit 8245fa9), WL-53 Honor
  (23rd, commit 81e4052) — so no duplicate article was
  written; Redmi was picked instead. Search evidence (Nov 17,
  2026 live web search): there is no Redmi-specific result in
  the top results at all — Dr.Fone guide
  (drfone.wondershare.com/android-transfer/how-to-transfer-
  photos-from-android-to-usb.html — OTG copy, PC drag-and-drop,
  Google Drive, or AirDroid; generic Android, most ways need a
  computer or a cloud account); AnExplorer guide
  (anexplorer.io/transfer/phone-to-usb — manual
  select-copy-paste, the only one of these guides that shows a
  progress bar with speed and time remaining, still no resume or
  verification); Coolmuster guide
  (coolmuster.com/android/transfer-files-from-android-to-usb-
  flash-drive.html — OTG copy, a PC tool, or a cloud service;
  generic Android, two of the three ways need a computer);
  YouTube video (youtube.com/watch?v=PYcbV25CczI — generic OTG
  + file manager walkthrough); Substack piece
  (curiousbluenoser.substack.com/p/saving-photos-for-
  necessary-updates — a picture-keeper flash drive piece: plug
  the maker's drive in, open its app, tap Start; generic, and
  tied to one maker's drive and app — not a Redmi guide).
  The other two brief candidates were also checked the same
  day: Vivo has no Vivo-specific result in the top results
  either, but its article already exists (WL-52), and Honor
  is the weakest gap of the three — its top two results are
  Honor-specific YouTube guides ("How to Transfer Phones to
  USB Drive on Honor Phone", youtube.com/watch?v=Acqvw1-hNn8,
  and "How to Transfer Files to External USB on Honor Phone",
  youtube.com/watch?v=ySOrhwl2Kuw) — and its article already
  exists (WL-53). iQOO was checked as a fallback: its top
  results include two iQOO-specific Coolmuster guides (a Mac
  backup guide and a photos backup guide, both PC/cloud
  routes, not phone-to-USB), so Redmi — with zero
  Redmi-specific results and the bigger installed base — is
  the cleaner gap. Wider search: Microsoft Q&A
  (learn.microsoft.com/en-us/answers/questions/3934384/
  import-photos-video-from-android-14-g-to-pc-and-fl — a
  generic Android 14 answer: a USB cable to a PC, a flash
  disk direct through an OTG adapter, or a cloud service
  (OneDrive, Google Drive, Google Photos); generic Android,
  not written for Redmi); AnExplorer OTG feature page
  (anexplorer.io/features/usb-otg — a file-manager feature
  page for OTG read/write, not a Redmi backup guide);
  YouTube video (youtube.com/watch?v=ch1evyu5PPM — same
  generic route: check OTG, OTG adapter, file manager, Copy
  or Move, safely eject); askfilo guide (askfilo.com — OTG
  adapter + file manager, or a PC as a middleman; generic
  Android, not written for Redmi); VaultDrive page
  (coticsy.com/vaultdrive.html — a rival phone-to-USB app:
  plug a flash drive or SSD in, pick what to back up, tap
  Start; free up to 200 files or 2 GB per backup, Pro $19.99
  once, no account; generic Android/iPhone, not a Redmi
  guide, and it does not promise oldest-first order, detailed
  progress with Pause and Stop, or size verification of every
  file); AnExplorer Xiaomi device page
  (anexplorer.io/device/phone/xiaomi — Xiaomi's built-in File
  Manager, part of MIUI/HyperOS, shows banner ads while
  browsing your own files; notes phones with SD card slots
  (Redmi Note series, POCO M series) benefit from moving
  media to the card; a file-manager page, not a backup guide
  — but it confirms Redmi phones run MIUI/HyperOS with
  Xiaomi's File Manager built in, and that the Redmi Note
  series has the SD card slot); File Manager by Xiaomi (Play
  listing, com.mi.android.globalFileexplorer — Xiaomi's own
  file manager: quick search, moving, deleting, opening,
  renaming, unzipping, and copy-paste; Xiaomi's preinstalled
  file app on global MIUI and HyperOS phones; the Files app
  the manual method uses); Xiaomi HyperOS backup guide
  (xiaomi-plus.com — the three backup options on a HyperOS
  phone: Google backup, Xiaomi Cloud, and local backup to a
  computer with a USB cable; a backup guide for Xiaomi phones
  running HyperOS — the same system Redmi phones run — but
  none of its three routes writes plain photo files to a USB
  drive); Google Photos help
  (support.google.com/photos/answer/14216276 — default-gallery
  access: Google Photos shares access with your device's
  default gallery app; Xiaomi is one of the brands that works
  with this feature — which confirms Xiaomi phones, Redmi
  included, have their own default gallery app, separate from
  Google Photos, and Google Photos is the cloud backup behind
  it); GSMArena Redmi Note 14 Pro 4G specs
  (m.gsmarena.com/xiaomi_redmi_note_14_pro_4g_(global)-
  ampp-13614.php — USB Type-C 2.0 with OTG, and a microSDXC
  card slot (uses the shared SIM slot); spec tables, not a
  guide); GSMArena Redmi Note 14 Pro 5G specs
  (m.gsmarena.com/xiaomi_redmi_note_14_pro_5g-ampp-13613.php
  — Android 14 with HyperOS, USB Type-C 2.0 with OTG, and no
  card slot; spec tables, not a guide); Photos to USB: OTG
  Backup (Play listing, com.arzdrz.photos_to_usb — a real
  phone-to-USB app, one-tap whole-gallery copy, only new
  photos next time, warns about the FAT32 4 GB limit, free
  with ads, one-time purchase removes ads — generic Android,
  not a Redmi guide, and it does not promise oldest-first
  order, detailed progress with Pause and Stop, or size
  verification of every file); Google Photos (Play listing,
  com.google.android.apps.photos — 15 GB cloud storage free;
  cloud, not a USB copy); umatechnology guide
  (umatechnology.org — copy photos with Files by Google or
  Samsung's My Files, or through a computer as a middleman;
  notes a photo that exists only in the cloud cannot be
  copied to a drive until it is downloaded to the phone
  first; generic Android, not written for Redmi);
  thegadgetians guide (thegadgetians.com — OTG adapter plus
  the phone's file manager for Android, a Lightning adapter
  for iPhone; generic, and it pushes one maker's photo stick;
  not a Redmi guide); pixoneye guide (pixoneye.com — copy
  local originals to compatible USB storage with the phone's
  file manager, or through a trusted computer; generic
  Android and iPhone, not written for Redmi).
  Redmi specifics: Redmi is Xiaomi's sub-brand and runs
  HyperOS (Xiaomi's Android skin — the Note 14 Pro 5G runs
  Android 14 with HyperOS); stock file manager is Mi File
  Manager (File Manager by Xiaomi,
  com.mi.android.globalFileexplorer — Xiaomi's preinstalled
  file app on global MIUI and HyperOS phones; it shows banner
  ads while browsing your own files); gallery app is Xiaomi
  Gallery (the HyperOS Gallery app — Google Photos help lists
  Xiaomi among the brands whose default gallery app
  integrates with Google Photos, so the gallery is Xiaomi's
  own and Google Photos is the cloud backup behind it);
  Redmi's own backup routes are Xiaomi Cloud (gallery sync
  and backup to a Xiaomi account, free storage limited) and
  Google's (Google Photos, Google backup via Google One);
  OTG: spec tables list USB Type-C 2.0 with OTG for the Note
  14 Pro 4G and the Note 14 Pro 5G; the microSD slot varies
  by model — the Note 14 Pro 4G has a microSDXC slot (uses
  the shared SIM slot) while the Note 14 Pro 5G has none —
  check your own model's specs; on many HyperOS phones OTG
  is off by default — enable it in Settings > Additional
  settings > OTG (on some phones under More settings > OTG),
  and the switch turns itself off after about 10 minutes
  without a drive (same switch as the Xiaomi guide, WL-era,
  verified there); all Redmi phones are USB-C. Redmi's own
  tools: Xiaomi Cloud (Settings > Xiaomi account > Xiaomi
  Cloud, gallery sync and backup to Xiaomi's servers, free
  storage limited) and Google backup (Settings > Google >
  Backup, Back up by Google One) restore to a new phone from
  the cloud; Google Photos (Photos settings > Backup, to
  Google's cloud, 15 GB free) backs photos up to Google's
  cloud; the SD-card route copies to a card, not a drive, and
  only some Redmi models have a slot (Note 14 Pro 4G does,
  Note 14 Pro 5G does not); Google Photos / Google One /
  Xiaomi Cloud back photos up to their clouds — none of them
  writes plain photo files to a USB drive.
- Verdict: how-to article material, not a listing keyword.
  Article published Nov 17, 2026 (WL-60, 30th how-to):
  docs/redmi-photos-to-usb.html. Query 29 scored in
  tool/growth/searches.md. Blog index (JSON-LD + post block),
  sitemap.xml, llms.txt, llms-full.txt, feed.xml (30 items)
  updated. PR #87 (article), merge 2b8b714; this row PR #88.

## WL-59 — Back up POCO photos to USB article, 29th how-to (DONE)

- Owner: SEO/GEO lane lead. State: DONE.
  New docs/poco-photos-to-usb.html ("Back up POCO photos to a
  USB drive (no computer)"), following the WL-54/WL-55/WL-56/
  WL-57/WL-58 article template exactly (navy/cyan, TL;DR first,
  no JS, text-only, reuses
  docs/screenshots-01-settings-defaults.png as the lead shot —
  same reuse as the 8th through 28th articles). Facts verified
  from code on fresh main: SAF tree picker (MainActivity.kt
  OpenDocumentTree + takePersistableUriPermission); oldest-first
  via MediaStore DATE_TAKEN ascending (PhotoQuery.kt, DATE_TAKEN
  else DATE_MODIFIED fallback); free 5 GB per run
  (ProPrefs.FREE_BACKUP_BYTES_LIMIT); Pro $9.99 one-time =
  auto-backup on connect + unlimited size (strings.xml
  pro_price, ProPrefs.PRO_PRICE_LABEL); no INTERNET permission
  in AndroidManifest.xml; FAT32 files >=4 GiB skipped with
  reason (FilesystemInfo.FAT32_MAX_FILE_BYTES); raw byte-stream
  copy (BackupEngine.copyWithResume — no decode/re-encode)
  with size verification after copy. Search evidence (Nov 17,
  2026 live web search): there is no POCO-specific result in
  the top results at all — Dr.Fone guide
  (drfone.wondershare.com/android-transfer/how-to-transfer-
  photos-from-android-to-usb.html — OTG copy, PC drag-and-drop,
  Google Drive, or AirDroid; generic Android, most ways need a
  computer or a cloud account); Buffalo Photo Backup (Play
  listing, jp.buffalo.photobackup — a rival phone-to-USB app
  from Buffalo: open the app, connect an SSD, USB flash drive,
  or card reader, backup starts automatically, keeping the
  original data; Android smartphones with USB-C, Android 13 or
  later; generic Android, not a POCO guide, and it does not
  promise oldest-first order, detailed progress with Pause and
  Stop, or size verification of every file); YouTube video
  (youtube.com/watch?v=PYcbV25CczI — generic OTG + file
  manager walkthrough); AnExplorer guide
  (anexplorer.io/transfer/phone-to-usb — manual
  select-copy-paste, the only one of these guides that shows a
  progress bar with speed and time remaining, still no resume or
  verification); Coolmuster guide
  (coolmuster.com/android/transfer-files-from-android-to-usb-
  flash-drive.html — OTG copy, a PC tool, or a cloud service;
  generic Android, two of the three ways need a computer).
  Wider search: VaultDrive page
  (coticsy.com/vaultdrive.html — a rival phone-to-USB app: plug
  a flash drive or SSD in, pick what to back up, tap Start; free
  up to 200 files or 2 GB per backup, Pro $19.99 once, no
  account; generic Android/iPhone, not a POCO guide, and it does
  not promise oldest-first order, detailed progress with Pause
  and Stop, or size verification of every file); AnExplorer OTG
  feature page (anexplorer.io/features/usb-otg — a file-manager
  feature page for OTG read/write, not a POCO backup guide);
  YouTube video (youtube.com/watch?v=ch1evyu5PPM — same generic
  route: check OTG, OTG adapter, file manager, Copy or Move,
  safely eject); askfilo guide (askfilo.com — OTG adapter +
  file manager, or a PC as a middleman; generic Android, not
  written for POCO); AnExplorer Xiaomi device page
  (anexplorer.io/device/phone/xiaomi — Xiaomi's built-in File
  Manager, part of MIUI/HyperOS, shows banner ads while browsing
  your own files; lists POCO models F6 Pro, F6, X6 Pro, X6, M6
  Pro, C65; notes phones with SD card slots (Redmi Note series,
  POCO M series) benefit from moving media to the card; a
  file-manager page, not a backup guide — but it confirms POCO
  phones run MIUI/HyperOS with Xiaomi's File Manager built in,
  and that the M series has the SD card slot); File Manager by
  Xiaomi (Play listing, com.mi.android.globalFileexplorer —
  Xiaomi's own file manager: quick search, moving, deleting,
  opening, renaming, unzipping, and copy-paste; Xiaomi's
  preinstalled file app on global MIUI and HyperOS phones; the
  Files app the manual method uses); Xiaomi HyperOS backup guide
  (xiaomi-plus.com — the three backup options on a HyperOS
  phone: Google backup, Xiaomi Cloud, and local backup to a
  computer with a USB cable; a backup guide for Xiaomi phones
  running HyperOS — the same system POCO phones run — but none
  of its three routes writes plain photo files to a USB drive);
  Google Photos help
  (support.google.com/photos/answer/14216276 — default-gallery
  access: Google Photos shares access with your device's default
  gallery app; Xiaomi is one of the brands that works with this
  feature — which confirms Xiaomi phones, POCO included, have
  their own default gallery app, separate from Google Photos,
  and Google Photos is the cloud backup behind it); phonebunch
  X7 vs M7 vs F7 comparison (cdn.phonebunch.com — spec tables
  list USB Type-C 2.0 with OTG for the X7 and the F7, but plain
  USB Type-C 2.0 with no OTG for the M7; the M7 is the one with
  a microSDXC dedicated slot, while the X7 and F7 have none;
  spec tables, not a guide); GSMArena POCO F9 Ultra specs
  (m.gsmarena.com/xiaomi_poco_f9_ultra-14876.php — Android 16
  with HyperOS 3, USB Type-C 3.2 with OTG, no card slot; spec
  tables, not a guide); POCO M7 specs (meloytechviews.com —
  lists USB Type-C with USB OTG, a microSDXC hybrid SIM slot
  expandable up to 2TB — which contradicts phonebunch, which
  lists the M7's OTG as not supported; a third-party specs site,
  so the M7 OTG claim is reported, not verified); POCO M7 5G
  review (mobilkanal.com — lists USB OTG as supported and a
  memory card slot up to 1TB on the M7 5G — which also
  contradicts phonebunch's Not Supported; a review site, so the
  same caveat applies: the M7 family's OTG support is disputed
  between sources); Photos to USB: OTG Backup (Play listing,
  com.arzdrz.photos_to_usb — a real phone-to-USB app, one-tap
  whole-gallery copy, only new photos next time, warns about
  the FAT32 4 GB limit, free with ads, one-time purchase
  removes ads — generic Android, not a POCO guide, and it does
  not promise oldest-first order, detailed progress with Pause
  and Stop, or size verification of every file); Google Photos
  (Play listing, com.google.android.apps.photos — 15 GB cloud
  storage free; cloud, not a USB copy).
  POCO specifics: POCO is Xiaomi's sub-brand and runs HyperOS
  (Xiaomi's Android skin — the F7 runs Android 15 with HyperOS
  2, the F9 Ultra runs Android 16 with HyperOS 3); stock file
  manager is Mi File Manager (File Manager by Xiaomi,
  com.mi.android.globalFileexplorer — Xiaomi's preinstalled file
  app on global MIUI and HyperOS phones; it shows banner ads
  while browsing your own files); gallery app is Xiaomi Gallery
  (the HyperOS Gallery app — Google Photos help lists Xiaomi
  among the brands whose default gallery app integrates with
  Google Photos, so the gallery is Xiaomi's own and Google
  Photos is the cloud backup behind it); POCO's own backup
  routes are Xiaomi Cloud (gallery sync and backup to a Xiaomi
  account, free storage limited) and Google's (Google Photos,
  Google backup via Google One); OTG: spec tables list USB
  Type-C 2.0 with OTG for the F7, F7 Pro, X7, and X7 Pro, and
  USB Type-C 3.2 with OTG for the F9 Ultra; the budget M7 OTG
  is disputed — phonebunch lists Not Supported, meloytechviews
  and mobilkanal list it as supported — treat as unconfirmed,
  check your own phone; on many HyperOS phones OTG is off by
  default — enable it in Settings > Additional settings > OTG
  (on some phones under More settings > OTG), and the switch
  turns itself off after about 10 minutes without a drive (same
  switch as the Xiaomi guide, WL-era, verified there); all POCO
  phones are USB-C; only the budget M series has a microSD slot
  — the F and X series have none, so a USB drive is the plug-in
  storage for them. POCO's own tools: Xiaomi Cloud (Settings >
  Xiaomi account > Xiaomi Cloud, gallery sync and backup to
  Xiaomi's servers, free storage limited) and Google backup
  (Settings > Google > Backup, Back up by Google One) restore
  to a new phone from the cloud; Google Photos (Photos settings
  > Backup, to Google's cloud, 15 GB free) backs photos up to
  Google's cloud; the SD-card route copies to a card, not a
  drive, and only the M series has a slot; Google Photos /
  Google One / Xiaomi Cloud back photos up to their clouds —
  none of them writes plain photo files to a USB drive.
- Verdict: how-to article material, not a listing keyword.
  Article published Nov 17, 2026 (WL-59, 29th how-to):
  docs/poco-photos-to-usb.html. Query 28 scored in
  tool/growth/searches.md. Blog index (JSON-LD + post block),
  sitemap.xml, llms.txt, llms-full.txt, feed.xml (29 items)
  updated. PR #85 (article), merge afaabc4; this row PR #86.

## WL-58 — Back up Nothing Phone photos to USB article, 28th how-to (DONE)

- Owner: SEO/GEO lane lead. State: DONE.
  New docs/nothing-phone-photos-to-usb.html ("Back up Nothing Phone
  photos to a USB drive (no computer)"), following the WL-54/WL-55/
  WL-56/WL-57 article template exactly (navy/cyan, TL;DR first, no JS,
  text-only, reuses docs/screenshots-01-settings-defaults.png as
  the lead shot — same reuse as the 8th through 27th articles).
  Facts verified from code on fresh main: SAF tree picker
  (MainActivity.kt OpenDocumentTree +
  takePersistableUriPermission); oldest-first via MediaStore
  DATE_TAKEN ascending (PhotoQuery.kt, DATE_TAKEN else
  DATE_MODIFIED fallback); free 5 GB per run
  (ProPrefs.FREE_BACKUP_BYTES_LIMIT); Pro $9.99 one-time =
  auto-backup on connect + unlimited size (strings.xml
  pro_price, ProPrefs.PRO_PRICE_LABEL); no INTERNET permission
  in AndroidManifest.xml; FAT32 files >=4 GiB skipped with
  reason (FilesystemInfo.FAT32_MAX_FILE_BYTES); raw byte-stream
  copy (BackupEngine.copyWithResume — no decode/re-encode)
  with size verification after copy. Search evidence (Nov 17,
  2026 live web search): there is no Nothing-specific result in
  the top results at all — YouTube video
  (youtube.com/watch?v=PYcbV25CczI — generic OTG + file
  manager walkthrough); AnExplorer guide
  (anexplorer.io/transfer/phone-to-usb — manual
  select-copy-paste, the only one of these guides that shows a
  progress bar with speed and time remaining, still no resume or
  verification); Dr.Fone guide
  (drfone.wondershare.com/android-transfer/how-to-transfer-
  photos-from-android-to-usb.html — OTG copy, PC drag-and-drop,
  Google Drive, or AirDroid; generic Android, most ways need a
  computer or a cloud account); pixoneye guide
  (pixoneye.com/how-to-transfer-photos-from-phone-to-usb-stick/
  — copy local originals to USB storage with the phone's file
  manager, or a computer as a middleman; generic Android, not
  written for Nothing); VaultDrive page
  (coticsy.com/vaultdrive.html — a rival phone-to-USB app: plug
  a flash drive or SSD in, pick what to back up, tap Start; free
  up to 200 files or 2 GB per backup, Pro $19.99 once, no
  account; generic Android/iPhone, not a Nothing guide, and it
  does not promise oldest-first order, detailed progress with
  Pause and Stop, or size verification of every file).
  Wider search: Nothing's own support site
  (support.nothing.tech — Product Feature pages document OTG as
  a feature: USB OTG lets the phone read data from a USB drive
  directly; a feature explainer, not a backup guide, but the
  official confirmation that OTG is supported); O2 Phone (1)
  backup guide (o2.co.uk — open Photos, back up pictures and
  videos to Google Drive; a cloud backup guide for one Nothing
  model, not a phone-to-USB guide; confirms Photos is the gallery
  app on the Phone (1)); Google Photos help
  (support.google.com/photos/answer/6193313 — turn Backup on in
  Photos settings, photos upload to your Google account; cloud,
  not a USB copy); Google One help
  (support.google.com/googleone/answer/6193313 — the same Google
  backup route, via Google One; cloud, not a USB copy); Memeburn
  backup guide (memeburn.com/how-to-backup-android-phone/ —
  Google Backup, Google Photos, and a computer; generic Android,
  not written for Nothing, and none of its routes writes plain
  photo files to a USB drive); GSMArena Phone (1) vs Phone (2)
  comparison (gsmarena.com — spec tables list "USB Type-C 2.0,
  OTG" for both the Phone (1) and the Phone (2); spec tables,
  not a guide); GSMArena (2a) vs (3a) vs CMF Phone 1 comparison
  (gsmarena.com — spec tables list "USB Type-C 2.0, OTG" for the
  Phone (2a) and the Phone (3a), but plain "USB Type-C" with no
  OTG for the CMF Phone 1; the CMF Phone 1 is the one with a
  microSDXC card slot; spec tables, not a guide); phonebunch
  (2a) vs CMF Phone 1 vs (3a) comparison (phonebunch.com — USB
  OTG Supported for the Phone (2a) and the Phone (3a), Not
  Supported for the CMF Phone 1; a comparison table, not a
  guide); cashify CMF Phone 1 vs (2a) vs (2) comparison
  (cashify.in — lists OTG Support as Yes for all three,
  including the CMF Phone 1, contradicting GSMArena and
  phonebunch; a third-party comparison site, so the CMF Phone 1
  OTG claim is reported, not verified); Android Authority
  Nothing Gallery leak
  (androidauthority.com/nothing-gallery-app-beta-hands-on-
  3506246/ — Nothing is releasing its own Gallery app with
  Nothing OS 3.0; until then, Nothing phones relied on Google
  Photos for photo management; so the gallery app depends on OS
  version: Google Photos on Nothing OS 2.x, Nothing Gallery on
  Nothing OS 3.0+); Nothing Gallery (Play listing,
  com.nothing.gallery — Nothing's own gallery app for Nothing
  OS 3.0, a clean media viewer; a gallery app, not a backup
  tool); Files by Google (Play listing,
  com.google.android.apps.nbu.files — Google's file manager:
  browse files, see space left on device, SD card and USB drive,
  move files to Drive or an SD card; the Files app the manual
  method uses); Nothing Community thread (nothing.community —
  Nothing Phone 2 owners describe the stock setup as Google's
  apps: Dialler, Contacts, Messages, Gallery, and Files; a user
  thread, not an official statement, but it matches the
  near-stock Nothing OS setup, and it is the basis for naming
  Files by Google as the stock file manager); gadgetsfocus OTG
  fixes (gadgetsfocus.com/phone-not-reading-usb-flash-drive-ssd-
  otg-fixes/ — a general OTG troubleshooting guide; its toggle
  advice is for OnePlus, OPPO, Realme, and Vivo phones, not
  Nothing; useful background, not a Nothing guide); AnExplorer
  OTG troubleshooting (anexplorer.io/solve/cant-access-usb —
  wrong or charge-only adapter, incompatible file system, or
  missing storage permission; useful background, not a Nothing
  guide); Photos to USB: OTG Backup (Play listing,
  com.arzdrz.photos_to_usb) is the one-tap app alternative
  from the wider search — a real phone-to-USB app, one-tap
  whole-gallery copy, only new photos next time, warns about
  the FAT32 4 GB limit, free with ads, one-time purchase
  removes ads — generic Android, not a Nothing guide, and it
  does not promise oldest-first order, detailed progress with
  Pause and Stop, or size verification of every file;
  Google Photos (Play listing, com.google.android.apps.photos
  — 15 GB cloud storage free; cloud, not a USB copy).
  Nothing specifics: Nothing phones run Nothing OS (a near-stock
  Android skin); stock file manager is Files by Google
  (com.google.android.apps.nbu.files — Nothing OS ships Google's
  apps rather than its own file manager, per the Nothing
  Community thread); gallery app depends on OS version — Google
  Photos on Nothing OS 2.x, Nothing Gallery (com.nothing.gallery)
  on Nothing OS 3.0+; no Nothing-owned cloud backup for photos
  exists — the backup routes are Google's (Google Photos, Google
  backup via Google One); OTG: spec tables list USB Type-C 2.0
  with OTG for the Phone (1), Phone (2), Phone (2a), Phone (2a)
  Plus, and Phone (3a), and Nothing's own support site documents
  OTG as a feature; CMF Phone 1 OTG is disputed — GSMArena lists
  plain USB Type-C with no OTG, phonebunch lists Not Supported,
  cashify lists Yes — treat as unconfirmed, check your own phone;
  all Nothing phones are USB-C; only the CMF Phone 1 has a
  microSD slot — the main Phone models have none, so a USB drive
  is the plug-in storage for them. Nothing's own tools: Google
  Photos (Photos settings > Backup, to Google's cloud, 15 GB
  free) and Google backup (Settings > Google > Backup, Back up
  by Google One) restore to a new phone from the cloud; the
  SD-card route copies to a card, not a drive, and only the CMF
  Phone 1 has a slot; Google Photos / Google One back photos up
  to Google's cloud — none of them writes plain photo files to a
  USB drive.
- Verdict: how-to article material, not a listing keyword.
  Article published Nov 17, 2026 (WL-58, 28th how-to):
  docs/nothing-phone-photos-to-usb.html. Query 27 scored in
  tool/growth/searches.md. Blog index (JSON-LD + post block),
  sitemap.xml, llms.txt, llms-full.txt, feed.xml (28 items)
  updated. PR #83 (article), merge 4c2d43c; this row PR #84.

## WL-57 — Back up Tecno photos to USB article, 27th how-to (DONE)

- Owner: SEO/GEO lane lead. State: DONE.
  New docs/tecno-photos-to-usb.html ("Back up Tecno photos to a
  USB drive (no computer)"), following the WL-54/WL-55/WL-56
  article template exactly (navy/cyan, TL;DR first, no JS,
  text-only, reuses docs/screenshots-01-settings-defaults.png as
  the lead shot — same reuse as the 8th through 26th articles).
  Facts verified from code on fresh main: SAF tree picker
  (MainActivity.kt OpenDocumentTree +
  takePersistableUriPermission); oldest-first via MediaStore
  DATE_TAKEN ascending (PhotoQuery.kt, DATE_TAKEN else
  DATE_MODIFIED fallback); free 5 GB per run
  (ProPrefs.FREE_BACKUP_BYTES_LIMIT); Pro $9.99 one-time =
  auto-backup on connect + unlimited size (strings.xml
  pro_price, ProPrefs.PRO_PRICE_LABEL); no INTERNET permission
  in AndroidManifest.xml; FAT32 files >=4 GiB skipped with
  reason (FilesystemInfo.FAT32_MAX_FILE_BYTES); raw byte-stream
  copy (BackupEngine.copyWithResume — no decode/re-encode)
  with size verification after copy. Search evidence (Nov 17,
  2026 live web search): there is no Tecno-specific result in
  the top results at all — YouTube video
  (youtube.com/watch?v=PYcbV25CczI — generic OTG + file
  manager walkthrough); YouTube video
  (youtube.com/watch?v=ch1evyu5PPM — same generic route:
  check OTG, OTG adapter, file manager, Copy or Move, safely
  eject); Dr.Fone guide
  (drfone.wondershare.com/android-transfer/how-to-transfer-
  photos-from-android-to-usb.html — OTG copy, PC drag-and-drop,
  Google Drive, or AirDroid; generic Android, most ways need a
  computer or a cloud account); AnExplorer guide
  (anexplorer.io/transfer/phone-to-usb — manual
  select-copy-paste, the only one of these guides that shows a
  progress bar with speed and time remaining, still no resume or
  verification); askfilo guide (askfilo.com — OTG adapter +
  file manager, or a PC as a middleman; generic Android, not
  written for Tecno); epictechtips guide
  (epictechtips.com/transfer-pictures-from-android-phone-to-
  usb-flash-drive/ — plug the drive in, file manager, Move to
  the drive, or a computer, cloud, or email middleman; generic
  Android, not written for Tecno); FoneLab guide
  (fonelab.com/resource/how-to-transfer-photos-from-android-
  phone-to-usb-flash-drive.html — OTG adapter copy, a PC
  file-explorer copy, or the FoneLab HyperTrans PC tool;
  generic Android, two of the three ways need a computer);
  MobileTrans OTG guide
  (mobiletrans.wondershare.com/android-tips/how-to-transfer-
  files-via-otg.html — OTG cable, select files, Copy, Paste
  to the drive; generic Android, not written for Tecno);
  TechDows OTG explainer
  (techdows.com/usb-otg-everything-you-need-to-know.html —
  which adapter to buy, how to open OTG storage, what to do
  when a drive does not show up; useful background, not a
  Tecno guide); Photos to USB: OTG Backup (Play listing,
  com.arzdrz.photos_to_usb) is the one-tap app alternative
  from the wider search — a real phone-to-USB app, one-tap
  whole-gallery copy, only new photos next time, warns about
  the FAT32 4 GB limit, free with ads, one-time purchase
  removes ads — generic Android, not a Tecno guide, and it
  does not promise oldest-first order, detailed progress with
  Pause and Stop, or size verification of every file;
  Coolmuster Tecno backup guide
  (coolmuster.com/android/tecno-backup-restore.html — the
  Coolmuster desktop tool backing the phone up to a computer
  over USB, built-in Backup & Restore to the phone or an SD
  card, TECNO Cloud to Tecno's cloud, Google Backup to
  Google's cloud; a PC, a card, or a cloud, never a USB
  drive; a third-party tool vendor, not Tecno's own site, so
  the menu names are reported, not official); helpforsmartphone
  Camon 30 backup guide (helpforsmartphone.com — Settings,
  Back up to Google Drive, pick the Google account, sync; a
  cloud backup guide for one Tecno model, not a phone-to-USB
  guide); MobiKin Tecno contacts guide
  (mobikin.com/android-backup/tecno-contact-backup-and-
  restore.html — Tecno-specific, but for contacts, not
  photos: desktop tool, Google sync, SD-card .vcf, or a
  contacts app; a PC, a cloud, or a card);
  umatechnology.org Tecno OTG page
  (umatechnology.org/tecno-phone-otg-support-how-to-check-
  and-connect-usb-devices/ — some Tecno phones support OTG,
  depending on model and regional variant; official spec
  pages list OTG for the POP 7 Pro, CAMON 20 Pro, POVA 7,
  and SPARK Go 2024; a third-party site citing official
  specs, so the model list is reported, not verified here;
  a check-and-setup page, not a backup guide); HT Tech
  Tecno OTG list
  (tech.hindustantimes.com/mobile-finder/tecno-brand/
  usb-otg-external-storage — a filter list of Tecno models
  with USB OTG, not a guide); smartprix Pova 5 Pro spec
  table (smartprix.com — "USB on-the-go" listed for the
  Pova 5 Pro); devicesfaq Pova Neo 3 page
  (devicesfaq.com/en/share-internet/tecno-pova-neo-3 — a
  tethering guide noting the Pova Neo 3 ships with a USB-C
  2.0 OTG cable in the box); go.tuxmat.com Pova 4 guide
  (go.tuxmat.com/total-ask/enable-otg-on-tecno-pova-4-a-
  quick-guide-1767649009 — OTG toggle reported inside the
  connection settings on the Pova 4; a low-quality guide,
  so the toggle location is reported, not verified);
  performancedriversclub.org F1 page
  (performancedriversclub.org/tecno-f1-otg-support/ —
  reports the Tecno F1 supports OTG; a low-quality page);
  Google Android support thread on the Pop 3
  (support.google.com/android/thread/108876882/ — the Pop 3
  lists OTG in its specs, but owners report it not enabled
  out of the box); YouTube OTG-not-connect video
  (youtube.com/watch?v=KFoAL46N-nk — OTG not connecting on
  Tecno and Infinix phones, incl. Spark 20 Pro+ and the
  Zero 30/40 and Camon 30 series; troubleshooting, not a
  backup guide); androidguias OTG explainer
  (en.androidguias.com/USB-OTG-on-Android: Everything you
  can do — how to check compatibility, which adapter to buy;
  useful background, not a Tecno guide); Google Photos
  (Play listing, com.google.android.apps.photos — 15 GB
  cloud storage free; cloud, not a USB copy); Transsion
  File Manager (Play listing, com.transsion.filemanagerx —
  the stock file manager on Tecno phones, by Shanghai
  Transsion Co., Ltd.); PakObserver Google AI Plus story
  (pakobserver.net/tecno-makes-everyday-smartphone-use-
  easier-with-free-2tb-cloud-storage/ — eligible Tecno
  phones launched from December 2025 get three months of
  Google AI Plus with 2TB of cloud storage; a cloud offer,
  not a USB copy). Tecno specifics: Tecno phones run HiOS
  (Tecno's Android skin); stock file manager is File Manager
  (Transsion File Manager, com.transsion.filemanagerx);
  no confirmed stock gallery name was found, so the article
  does not name one; an OTG toggle location is reported in
  the connection settings on the Pova 4, but the source is
  a low-quality guide — treat it as a lead, not a fact;
  if nothing appears, check Settings > Storage; OTG varies
  by model and regional variant (POP 7 Pro, CAMON 20 Pro,
  POVA 7, SPARK Go 2024, Pova 5 Pro listed; Pop 3 lists OTG
  in its specs but owners report it not enabled); modern
  Tecno phones are USB-C, older ones Micro-USB; whether a
  model has a microSD slot varies by model — check the
  model's specs. Tecno's own tools: built-in Backup &
  Restore (Settings > System > Back up & reset, Local backup
  or Phone backup, to internal storage or an SD card);
  TECNO Cloud / HiOS Cloud (Settings > TECNO Cloud, TECNO
  account) backs photos up to Tecno's cloud; Google backup
  (Settings > Google > Backup, Back up by Google One)
  restores to a new phone from the cloud; Coolmuster backs
  the phone up to a PC; the SD-card route copies to a card,
  not a drive; Google Photos / Google One back photos up to
  Google's cloud (15 GB free) — none of them writes plain
  photo files to a USB drive.
- Verdict: how-to article material, not a listing keyword.
  Article published Nov 17, 2026 (WL-57, 27th how-to):
  docs/tecno-photos-to-usb.html. Query 26 scored in
  tool/growth/searches.md. Blog index (JSON-LD + post block),
  sitemap.xml, llms.txt, llms-full.txt, feed.xml (27 items)
  updated. PR #81 (article), merge 6ce3eef; this row PR #82.

## WL-56 — Back up Infinix photos to USB article, 26th how-to (DONE)

- Owner: SEO/GEO lane lead. State: DONE.
  New docs/infinix-photos-to-usb.html ("Back up Infinix photos to a
  USB drive (no computer)"), following the WL-54/WL-55 article template
  exactly (navy/cyan, TL;DR first, no JS, text-only, reuses
  docs/screenshots-01-settings-defaults.png as the lead shot —
  same reuse as the 8th through 25th articles). Facts verified
  from code on fresh main: SAF tree picker (MainActivity.kt
  OpenDocumentTree + takePersistableUriPermission); oldest-first
  via MediaStore DATE_TAKEN ascending (PhotoQuery.kt, DATE_TAKEN
  else DATE_MODIFIED fallback); free 5 GB per run
  (ProPrefs.FREE_BACKUP_BYTES_LIMIT); Pro $9.99 one-time =
  auto-backup on connect + unlimited size; no INTERNET
  permission in AndroidManifest.xml; FAT32 files >=4 GiB skipped
  with reason (FilesystemInfo.FAT32_MAX_FILE_BYTES); raw
  byte-stream copy (BackupEngine.copyWithResume — no
  decode/re-encode) with size verification after copy. Search
  evidence (Nov 17, 2026 live web search): there is no
  Infinix-specific result in the top results at all — YouTube
  video (youtube.com/watch?v=PYcbV25CczI — generic OTG + file
  manager walkthrough); Dr.Fone guide
  (drfone.wondershare.com/android-transfer/how-to-transfer-
  photos-from-android-to-usb.html — OTG copy, PC drag-and-drop,
  Google Drive, or AirDroid; generic Android, most ways need a
  computer or a cloud account); AnExplorer guide
  (anexplorer.io/transfer/phone-to-usb — manual
  select-copy-paste, the only one of these guides that shows a
  progress bar with speed and time remaining, still no resume or
  verification); askfilo guide (askfilo.com — OTG adapter + file
  manager, or a PC as a middleman; generic Android, not written
  for Infinix); TheGadgetians guide
  (thegadgetians.com/how-to-transfer-files-from-phone-to-usb-
  without-computer/ — OTG adapter + file manager, mentions
  ThePhotoStick Omni; generic Android and iPhone, not written
  for Infinix); Infinix Hot 70 retailer page (mascomintl.com —
  a shop listing, not a guide: "USB-C with OTG — read flash
  drives directly", dedicated microSDXC slot; the one
  Infinix-specific OTG confirmation in the top results);
  Pixoneye guide
  (pixoneye.com/how-to-transfer-photos-from-phone-to-usb-stick/
  — copy local originals to a USB drive with a file manager,
  one small batch at a time; notes ThePhotoStick Omni; generic
  Android and iPhone, not written for Infinix); SoftHandTech
  guide
  (softhandtech.com/how-do-you-transfer-pictures-from-your-
  phone-to-a-flash-drive/ — OTG adapter + file manager, or a
  computer, or cloud storage; generic, not written for
  Infinix); FoneLab guide
  (fonelab.com/resource/how-to-transfer-photos-from-android-
  phone-to-usb-flash-drive.html — OTG adapter copy, a PC
  file-explorer copy, or the FoneLab HyperTrans PC tool;
  generic Android, two of the three ways need a computer);
  MobileTrans OTG guide
  (mobiletrans.wondershare.com/android-tips/how-to-transfer-
  files-via-otg.html — OTG cable, select files, Copy, Paste
  to the drive; generic Android, not written for Infinix);
  TechDows OTG explainer
  (techdows.com/usb-otg-everything-you-need-to-know.html —
  which adapter to buy, how to open OTG storage, what to do
  when a drive does not show up; useful background, not an
  Infinix guide); Photos to USB: OTG Backup (Play listing,
  com.arzdrz.photos_to_usb) is the one-tap app alternative from
  the wider search — a real phone-to-USB app, one-tap
  whole-gallery copy, only new photos next time, warns about
  the FAT32 4 GB limit, free with ads, one-time purchase removes
  ads — generic Android, not an Infinix guide, and it does not
  promise oldest-first order, detailed progress with Pause and
  Stop, or size verification of every file; the one
  Infinix-specific backup page found in the wider search,
  infinixfirmware.io
  (infinixfirmware.io/how-to-backup-restore-data-on-infinix-
  smartphones/ — Google Account Backup, XOS backup via
  Settings > System > Backup & Reset with Local Backup or
  XCloud Backup, a manual copy to an SD card, or a copy to a
  laptop over USB; a cloud, a card, or a PC, never a USB drive;
  a third-party fan site, not Infinix's own site, so the XCloud
  menu names are reported, not official); Coolmuster Infinix
  backup guide
  (coolmuster.com/android/infinix-backup-restore.html —
  Google Backup to the cloud, Infinix Cloud to Infinix's
  servers, or the Coolmuster desktop tool backing the phone up
  to a computer over USB; a cloud or a PC, never a USB drive);
  Alibaba external-drive guide
  (alibaba.com/product-insights/how-to-backup-your-entire-
  smartphone-to-an-external-drive-without-cloud-storage.html
  — generic Android OTG + file manager full-phone backup to an
  external drive; not written for Infinix); Trustloud Android
  backup guide (trustloud.com/back-up-android-phone/ —
  Google One + Google Photos, Samsung Cloud or Smart Switch,
  or a USB cable to a computer; cloud or a PC, generic, not
  written for Infinix); Techzei phone backup guide
  (techzei.com/how-to-back-up-your-phone-android-iphone/ —
  Google backup and Google Photos plus a monthly USB copy of
  DCIM to a computer or drive; generic Android and iPhone, not
  written for Infinix); smallusefultips Smart 5 OTG page
  (smallusefultips.com/does-infinix-smart-5-support-otg/ —
  the Infinix Smart 5 (2020, Android 10 Go) does NOT support
  OTG, a hardware limitation no adapter or setting can fix);
  accio.com Zero 30 OTG guide
  (accio.com/business/infinix-zero-30-otg-setting — Zero 30
  5G supports OTG via Type-C; toggle reported at Settings >
  More Connections > OTG, with Infinix switching OTG off after
  10 minutes idle; an AI-generated report, so the toggle
  location is reported, not verified, and firmware may move
  it); docsbot.ai Note 12 G96 OTG page
  (docsbot.ai/prompts/technical/infinix-note-12-g96-otg-
  support — the Note 12 G96 supports OTG); FoneArena GT 50
  Pro unboxing
  (fonearena.com/blog/497019/infinix-gt-50-pro-unboxing-
  first-impressions.html — runs Android 16 with XOS 16,
  USB-C port; OTG support not stated for this model);
  91mobiles XOS 16 hands-on
  (91mobiles.com/reviews/infinix-xos-16-hands/amp/ — XOS 16
  redesigned the first-party Gallery and File Manager apps,
  Gallery gained AI imaging features); XOS stock apps
  (toolify.ai and nairaland.com — stock gallery is AI Gallery
  since XOS 5.5, stock file manager is File Manager);
  HT Tech Infinix OTG list
  (tech.hindustantimes.com/mobile-finder/infinix-brand/
  usb-otg-external-storage — a filter list of Infinix models
  with USB OTG, not a guide); Google Photos (Play listing,
  com.google.android.apps.photos — 15 GB cloud storage free;
  cloud, not a USB copy). Infinix specifics stated honestly:
  Infinix phones run XOS (Infinix's Android skin; XOS 16 is
  current, on Android 16); stock file manager is File Manager
  (XOS File Manager, handles internal + connected external
  storage); stock gallery is AI Gallery (XOS AI Gallery, in the
  box since XOS 5.5); an OTG toggle location is reported at
  Settings > More Connections on the Zero 30, but the source
  is an AI-generated report — treat it as a lead, not a fact;
  if nothing appears, check Settings > Storage; not every
  Infinix phone has OTG (Smart 5 doesn't; Note 12 G96,
  Zero 30 5G, Hot 70 do); modern Infinix phones are USB-C,
  older ones Micro-USB; whether a model has a microSD slot
  varies by model (Hot 70 has a dedicated slot — check the
  model's specs). Infinix's own tools: Google backup
  (Settings > Google > Backup) restores to a new phone from
  the cloud; XCloud (Settings > System > Backup & Reset,
  Local Backup or XCloud Backup, on models that have it)
  backs photos up to Infinix's cloud; Coolmuster backs the
  phone up to a PC; the SD-card route copies to a card, not
  a drive; Google Photos / Google One back photos up to
  Google's cloud (15 GB free) — none of them writes plain
  photo files to a USB drive. Honest contrast stated in the
  TL;DR: Infinix File Manager works but is manual
  select-copy-paste with no progress, no resume, no
  verification; Infinix's own routes work but back photos up
  to the cloud, to a PC, or to a card — not plain photos on a
  USB drive; Google Photos backup is a cloud copy, not a USB
  copy; Photos to USB also does one-tap copy with resume —
  SnapDisk adds oldest-first order, detailed progress with
  Pause and Stop, size verification, no ads, and free 5 GB
  per run. Query 25 scored Nov 17, 2026
  (searches.md): "back up photos from Infinix to USB flash drive
  without computer" — real how-to gap. Volume unknown (Play
  publishes no volume); difficulty low. Linked from blog index
  (JSON-LD + post block), sitemap.xml, llms.txt,
  llms-full.txt (full section + Blog posts list, now 26),
  feed.xml (now 26 items). JSON-LD validated by parse;
  sitemap.xml and feed.xml parse as XML. Title 48 chars, meta
  145 chars. No app code or Play listing files touched;
  applicationId untouched (dev.dzianisv.snapdisk). Work ran from
  a persistent scratch clone (~/workspace/pb-wl56-scratch, not
  the shared checkout, never /tmp). Evidence: PR #79 (commit
  21b5ee4, merge 1cc0892, merged 2026-11-17T10:33:51Z); CI run
  44065615912 (build pass, 1m10s) green; post-merge main runs
  green (Android PR 44065992906, pages 44065991434). Live
  fetch after Pages deploy: article 200, 38062 bytes, title
  "Back up Infinix photos to a USB drive — SnapDisk" present;
  blog index 200, 24147 bytes; feed.xml 200 (26 items);
  sitemap.xml 200 with the new link; llms-full.txt 200,
  295356 bytes, Blog list counts 26 with Infinix present.

## WL-55 — Back up Asus photos to USB article, 25th how-to (DONE)

- Owner: SEO/GEO lane lead. State: DONE.
  New docs/asus-photos-to-usb.html ("Back up Asus photos to a
  USB drive (no computer)"), following the WL-54 article template
  exactly (navy/cyan, TL;DR first, no JS, text-only, reuses
  docs/screenshots-01-settings-defaults.png as the lead shot —
  same reuse as the 8th through 24th articles). Facts verified
  from code on fresh main: SAF tree picker (MainActivity.kt
  OpenDocumentTree + takePersistableUriPermission); oldest-first
  via MediaStore DATE_TAKEN ascending (PhotoQuery.kt, DATE_TAKEN
  else DATE_MODIFIED fallback); free 5 GB per run
  (ProPrefs.FREE_BACKUP_BYTES_LIMIT); Pro $9.99 one-time =
  auto-backup on connect + unlimited size; no INTERNET
  permission in AndroidManifest.xml; FAT32 files >=4 GiB skipped
  with reason (FilesystemInfo.FAT32_MAX_FILE_BYTES); raw
  byte-stream copy (BackupEngine.copyWithResume — no
  decode/re-encode) with size verification after copy. Search
  evidence (Nov 17, 2026 live web search): there is no
  Asus-specific result in the top results at all — Pixoneye
  guide (pixoneye.com/how-to-transfer-photos-from-phone-to-
  usb-stick/ — copy local originals to a USB drive with a file
  manager, one small batch at a time; notes ThePhotoStick Omni;
  generic Android and iPhone, not written for Asus); Photos to
  USB: OTG Backup (Play listing, com.arzdrz.photos_to_usb) is
  the one-tap app alternative from the wider search — a real
  phone-to-USB app, one-tap whole-gallery copy, only new photos
  next time, warns about the FAT32 4 GB limit, free with ads,
  one-time purchase removes ads — generic Android, not an Asus
  guide, and it does not promise oldest-first order, detailed
  progress with Pause and Stop, or size verification of every
  file; TheGadgetians guide
  (thegadgetians.com/how-to-transfer-files-from-phone-to-usb-
  without-computer/ — OTG adapter + file manager, mentions
  ThePhotoStick Omni; generic Android and iPhone, not written
  for Asus); YouTube video
  (youtube.com/watch?v=PYcbV25CczI — generic OTG + file manager
  walkthrough); SoftHandTech guide
  (softhandtech.com/how-do-you-transfer-pictures-from-your-
  phone-to-a-flash-drive/ — OTG adapter + file manager, or a
  computer, or cloud storage; generic, not written for Asus);
  AnExplorer guide (anexplorer.io/transfer/phone-to-usb —
  manual select-copy-paste, the only one of these guides that
  shows a progress bar with speed and time remaining, still no
  resume or verification); Dr.Fone guide
  (drfone.wondershare.com/android-transfer/how-to-transfer-
  photos-from-android-to-usb.html — OTG copy, PC drag-and-drop,
  Google Drive, or AirDroid; generic Android, most ways need a
  computer or a cloud account); askfilo guide (askfilo.com —
  OTG adapter + file manager, or a PC as a middleman; generic
  Android, not written for Asus); FoneLab guide
  (fonelab.com/resource/how-to-transfer-photos-from-android-
  phone-to-usb-flash-drive.html — OTG adapter copy, a PC
  file-explorer copy, or the FoneLab HyperTrans PC tool;
  generic Android, two of the three ways need a computer);
  MobileTrans OTG guide
  (mobiletrans.wondershare.com/android-tips/how-to-transfer-
  files-via-otg.html — OTG cable, select files, Copy, Paste to
  the drive; generic Android, not written for Asus); TechDows
  OTG explainer
  (techdows.com/usb-otg-everything-you-need-to-know.html —
  which adapter to buy, how to open OTG storage, what to do
  when a drive does not show up; useful background, not an
  Asus guide); the one Asus-specific backup page found in the
  wider search, mobilespecs.net
  (mobilespecs.net/phone/backup/ASUS/ASUS_ZenFone_4_ZE554KL.html
  — sync data to Google's cloud, back photos up with Google
  Photos, or use TWRP Recovery, Titanium Backup, or ADB; a PC,
  a custom recovery, or the cloud, never a USB drive);
  devicesfaq Asus Zenfone 9 and Zenfone 7 FAQ pages
  (devicesfaq.com — copy files to a computer over USB, back up
  to Google Drive's cloud, or use Google Photos; a PC or a
  cloud, never a USB drive); manualsnet ZenFone Selfie user
  manual (manualsnet.com/asus/zenfone-selfie-zd551kl — connect
  the phone to a computer with a USB cable, choose File
  Transfer mode, copy the photos on the computer; a PC in
  between, not a phone-to-USB copy); ASUS Phone Clone (Play
  listing, com.futuredial.asusdatatransfer — Asus's own
  migration app, formerly ASUS Data Transfer: moves contacts,
  messages, photos, videos, music, files, and apps from an old
  Android phone to a new Asus phone over Wi-Fi, no cable;
  phone-to-phone, not phone-to-USB, and stock-AOSP Zenfones
  are not supported); Asus's own FAQ
  (asus.com/in/support/faq/1043100/ — Phone Clone is a
  migration tool for a new Asus phone, covering Zenfone 6
  through Zenfone 10 and ROG Phone 3 through ROG Phone 7);
  ZenTalk (zentalk.asus.com — on Zenfone 9 and 10 the default
  gallery is Asus's own Gallery app, and Google Photos cannot
  be set as the default); Zenfone 5 Selfie manual
  (gebruikershandleiding.com — File Manager "allows you to
  easily locate and manage your data on the internal storage
  of your ASUS Phone and its connected external storage
  devices", launched from ASUS > File Manager); Notebookcheck
  Zenfone 7 Pro review — the one Asus-specific OTG
  confirmation from a review (the Zenfone 7 Pro's USB-C port
  "supports USB OTG", ZenUI 7 is "only minimally different to
  standard Android", preinstalled file manager + Asus Data
  Transfer); OTG spec sheets — OTG confirmed on most Zenfones
  but not all: Zenfone 3 Laser "OTG Compatible, Yes"
  (Flipkart), Zenfone 6 lists OTG, Zenfone 4 and Zenfone 3 Max
  reviews confirm OTG — but the original Zenfone 5 "doesn't
  support USB OTG" (91mobiles) and the Zenfone 2 lists "USB
  OTG — No"; Google Photos (Play listing,
  com.google.android.apps.photos — 15 GB cloud storage free;
  cloud, not a USB copy). Asus specifics stated honestly:
  Asus phones run ZenUI (close to stock Android on recent
  models); stock file manager is Asus File Manager (ZenUI File
  Manager, handles internal + connected external storage);
  stock gallery is Asus Gallery (on Zenfone 9/10 Google Photos
  cannot be set as the default); no documented Asus OTG switch
  location was found — the drive mounts on plug-in with a "USB
  storage added" notification; if nothing appears, check
  Settings > Storage; whether a switch exists at all is
  unverified; not every Asus phone has OTG (original Zenfone 5
  and Zenfone 2 don't; Zenfone 3 Laser, 4, 6, 7 Pro do);
  modern Asus phones are USB-C, older ones Micro-USB; whether
  a model has a microSD slot varies by model (check the model's
  specs). Asus's own tools: ASUS Phone Clone (formerly ASUS
  Data Transfer) moves data from an old phone to a new Asus
  phone over Wi-Fi — phone-to-phone, not phone-to-USB; Google
  backup (Settings > System > Backup) restores to a new phone
  from the cloud; Asus WebStorage is Asus's own cloud (5 GB
  free with older Zenfones); Google Photos / Google One back
  photos up to Google's cloud (15 GB free) — none of them
  writes plain photo files to a USB drive. Honest contrast
  stated in the TL;DR: Asus File Manager works but is manual
  select-copy-paste with no progress, no resume, no
  verification; Asus's own routes work but move photos to a
  new Asus phone, to Google's cloud, or to Asus's cloud — not
  plain photos on a USB drive; Google Photos backup is a cloud
  copy, not a USB copy; Photos to USB also does one-tap copy
  with resume — SnapDisk adds oldest-first order, detailed
  progress with Pause and Stop, size verification, no ads, and
  free 5 GB per run. Query 24 scored Nov 17, 2026
  (searches.md): "back up photos from Asus to USB flash drive
  without computer" — real how-to gap. Volume unknown (Play
  publishes no volume); difficulty low. Linked from blog index
  (JSON-LD + post block), sitemap.xml, llms.txt,
  llms-full.txt (full section + Blog posts list, now 25),
  feed.xml (now 25 items). JSON-LD validated by parse;
  sitemap.xml and feed.xml parse as XML. Title 45 chars, meta
  142 chars. No app code or Play listing files touched;
  applicationId untouched (dev.dzianisv.snapdisk). Work ran from
  a persistent scratch clone (~/workspace/pb-wl55-scratch, not
  the shared checkout, never /tmp). Evidence: PR #77 (commit
  c1f05e6, merge 9fb0bf9, merged 2026-11-17T09:35:40Z); CI run
  44056668839 (build pass, 1m12s) green; post-merge main runs
  green (Android PR 44056992617, pages 44056991219). Live
  fetch after Pages deploy: article 200, 34000 bytes, title
  "Back up Asus photos to a USB drive — SnapDisk" present;
  blog index 200, 23208 bytes; feed.xml 200 (25 items);
  sitemap.xml 200 with the new link; llms-full.txt 200,
  275423 bytes, Blog list counts 25 with Asus present.
## WL-54 — Back up Nokia photos to USB article, 24th how-to (DONE)

- Owner: SEO/GEO lane lead. State: DONE.
  New docs/nokia-photos-to-usb.html ("Back up Nokia photos to a
  USB drive (no computer)"), following the WL-53 article template
  exactly (navy/cyan, TL;DR first, no JS, text-only, reuses
  docs/screenshots-01-settings-defaults.png as the lead shot —
  same reuse as the 8th through 23rd articles). Facts verified
  from code on fresh main: SAF tree picker (MainActivity.kt
  OpenDocumentTree + takePersistableUriPermission); oldest-first
  via MediaStore DATE_TAKEN ascending (PhotoQuery.kt, DATE_TAKEN
  else DATE_MODIFIED fallback); free 5 GB per run
  (ProPrefs.FREE_BACKUP_BYTES_LIMIT); Pro $9.99 one-time =
  auto-backup on connect + unlimited size; no INTERNET
  permission in AndroidManifest.xml; FAT32 files >=4 GiB skipped
  with reason (FilesystemInfo.FAT32_MAX_FILE_BYTES); raw
  byte-stream copy (BackupEngine.copyWithResume — no
  decode/re-encode) with size verification after copy. Search
  evidence (Nov 17, 2026 live web search): there is no
  Nokia-specific result in the top results at all — TheGadgetians
  guide (thegadgetians.com/how-to-transfer-files-from-phone-to-
  usb-without-computer/ — OTG adapter + file manager, mentions
  ThePhotoStick Omni; generic Android and iPhone, not written
  for Nokia); SoftHandTech guide
  (softhandtech.com/how-do-you-transfer-pictures-from-your-
  phone-to-a-flash-drive/ — OTG adapter + file manager, or a
  computer, or cloud storage; generic, not written for Nokia);
  SoftHandTech guide
  (softhandtech.com/can-you-transfer-photos-from-phone-to-
  memory-stick/ — computer drag-and-drop, direct OTG copy, or
  cloud services; generic, not written for Nokia); AnExplorer
  guide (anexplorer.io/transfer/phone-to-usb — manual
  select-copy-paste, the only one of the five that shows a
  progress bar with speed and time remaining, still no resume
  or verification); umatechnology.org guide
  (umatechnology.org/how-to-transfer-photos-from-android-to-a-
  usb-flash-drive-without-a-computer/ — Files by Google or
  Samsung My Files, or through a computer; generic Android, not
  written for Nokia); YouTube video
  (youtube.com/watch?v=PYcbV25CczI — generic OTG + file manager
  walkthrough); Dr.Fone guide
  (drfone.wondershare.com/android-transfer/how-to-transfer-
  photos-from-android-to-usb.html — OTG copy, PC drag-and-drop,
  Google Drive, or AirDroid; generic Android, most ways need a
  computer or a cloud account); MobiKin guide
  (mobikin.com/android-backup/transfer-photos-from-android-to-
  flash-drive.html — File Explorer, Assistant PC tool, OTG copy,
  Files by Google, or Google Photos; generic Android, most ways
  need a computer or a cloud account); Seagate OTG
  compatibility list (seagate.com — not a guide, but the one
  Nokia-specific OTG evidence found: Nokia X71, Nokia 5.1 Plus,
  and Nokia 6.1 are on the confirmed-OTG list); the one
  Nokia-specific backup page found in the wider search,
  mull-overthing (mull-overthing.com/how-do-i-backup-my-
  nokia-phone/ — Google One / Android Backup Service to the
  cloud, or Nokia PC Suite to a PC; not a phone-to-USB guide at
  all); MobileTrans Nokia backup guide
  (mobiletrans.wondershare.com/transfer/backup-nokia.html —
  backs the phone up to a PC with a Windows/Mac tool; no USB
  drive, no plain photo files); Nokia PC Suite help
  (nokia-pc-suite.helpmax.net — legacy PC tool, Content Copier
  writes a single .nbu backup file to a PC); HMD's own user
  guides (hmd.com — transfer data to a new phone using your
  Google account, or restore from a Google backup, Settings >
  System > Backup; phone-to-phone or phone-to-cloud, not
  phone-to-USB); Google One (Play listing,
  com.google.android.apps.subscriptions.red — backs photos up
  to Google's cloud, 15 GB free; cloud, not a USB copy);
  Photos to USB: OTG Backup (Play listing,
  com.arzdrz.photos_to_usb) is the one-tap app alternative from
  the wider search — a real phone-to-USB app, one-tap
  whole-gallery copy, only new photos next time, warns about the
  FAT32 4 GB limit, free with ads, one-time purchase removes ads
  — generic Android, not a Nokia guide, and it does not promise
  oldest-first order, detailed progress with Pause and Stop, or
  size verification, so the article says so honestly instead of
  claiming SnapDisk is the only one-tap option. Cloud is Google
  Photos or Google One — cloud, not USB. Nokia specifics stated
  honestly: Nokia phones run near-stock Android (Android One on
  most models); stock file manager is Files by Google; stock
  gallery is Google Photos; no documented Nokia OTG switch
  location was found — the drive mounts on plug-in with a "USB
  storage added" notification; if nothing appears, check
  Settings > Storage; whether a switch exists at all, or
  switches itself off, is unverified; not every Nokia phone has
  OTG (Seagate confirms X71, 5.1 Plus, 6.1); modern Nokia
  phones are USB-C, older ones Micro-USB; whether a model has a
  microSD slot varies by model (check the model's specs).
  Nokia's own tools: no Nokia backup app — HMD's guides point
  to Google backup (Settings > System > Backup, Back up to
  Google Drive), which restores to a new phone from the cloud;
  Google One backs photos up to Google's cloud (15 GB free);
  Nokia PC Suite is a legacy PC tool writing a .nbu file;
  MobileTrans is a PC tool backing the phone up to a computer —
  none of them writes plain photo files to a USB drive. Honest
  contrast stated in the TL;DR: Files by Google works but is
  manual select-copy-paste with no progress, no resume, no
  verification; Nokia's own routes work but write to a new phone,
  to Google's cloud, or to a PC — not plain photos on a USB
  drive; Google Photos / Google One is cloud, not a USB copy;
  Photos to USB also does one-tap copy with resume — SnapDisk
  adds oldest-first order, detailed progress with Pause and Stop,
  size verification, no ads, and free 5 GB per run. Query 23
  scored Nov 17, 2026 (searches.md): "back up photos from Nokia
  to USB flash drive without computer" — real how-to gap. Volume
  unknown (Play publishes no volume); difficulty low. Linked
  from blog index (JSON-LD + post block), sitemap.xml,
  llms.txt, llms-full.txt (full section + Blog posts list, now
  24), feed.xml (now 24 items). JSON-LD validated by parse;
  sitemap.xml and feed.xml parse as XML. Title 46 chars, meta
  143 chars. No app code or Play listing files touched;
  applicationId untouched (dev.dzianisv.snapdisk). Work ran from
  a persistent scratch clone (~/workspace/pb-wl54-scratch, not
  the shared checkout, never /tmp). Evidence: PR #74 (commit
  0a5c352, merge 7361638, merged 2026-11-17T08:33:14Z); CI run
  44047497188 (build pass, 57s) green; post-merge main runs
  green (Android PR 44047724340, pages 44047723953). Live
  fetch after Pages deploy: article 200, 30703 bytes, title
  "Back up Nokia photos to a USB drive — SnapDisk" present;
  blog index 200, 22342 bytes; feed.xml 200 (24 items);
  sitemap.xml 200 with the new link; llms-full.txt 200,
  258543 bytes, Blog list counts 24 with Nokia present.


## WL-53 — Back up Honor photos to USB article, 23rd how-to (DONE)

- Owner: SEO/GEO lane lead. State: DONE.
  New docs/honor-photos-to-usb.html ("Back up Honor photos to a
  USB drive (no computer)"), following the WL-52 article template
  exactly (navy/cyan, TL;DR first, no JS, text-only, reuses
  docs/screenshots-01-settings-defaults.png as the lead shot —
  same reuse as the 8th through 22nd articles). Facts verified
  from code on fresh main: SAF tree picker (MainActivity.kt
  OpenDocumentTree + takePersistableUriPermission); oldest-first
  via MediaStore DATE_TAKEN ascending; free 5 GB per run
  (ProPrefs.FREE_BACKUP_BYTES_LIMIT); Pro $9.99 one-time =
  auto-backup on connect + unlimited size; no INTERNET
  permission in AndroidManifest.xml; FAT32 files >=4 GiB skipped
  with reason (FilesystemInfo.FAT32_MAX_FILE_BYTES); raw
  byte-stream copy (BackupEngine.copyWithResume — no
  decode/re-encode) with size verification after copy. Search
  evidence (Nov 16, 2026 live web search): the only
  Honor-specific results in the top results are videos of the
  manual route — YouTube video (youtube.com/watch?v=Acqvw1-hNn8
  — Honor-specific: OTG adapter, File Manager, select photos,
  copy or move to the USB drive; manual, no automation);
  YouTube video (youtube.com/watch?v=ySOrhwl2Kuw —
  Honor-specific: USB-C OTG adapter, File Manager, select files
  in DCIM, Copy or Move, eject before unplugging; manual again);
  YouTube video (youtube.com/watch?v=voVHMnQEfwI —
  Honor-specific: USB OTG manual copy in Files, or cloud backup
  via Huawei Mobile Cloud, Google Drive, or OneDrive; the USB
  half is manual, the cloud half is not a USB copy); Pixoneye
  guide (pixoneye.com/how-to-transfer-photos-from-phone-to-usb-
  stick/ — copy local originals with a file manager, one small
  batch at a time; notes ThePhotoStick Omni, a dedicated stick
  with its own app; generic Android and iPhone, not written for
  Honor); AnExplorer guide (anexplorer.io/transfer/phone-to-usb;
  manual select-copy-paste, the only one of the six that shows a
  progress bar with speed and time remaining, still no resume or
  verification); YouTube video (youtube.com/watch?v=PYcbV25CczI
  — generic OTG + file manager walkthrough); TheGadgetians guide
  (thegadgetians.com/how-to-transfer-files-from-phone-to-usb-
  without-computer/ — OTG adapter + file manager, mentions
  ThePhotoStick Omni; generic Android and iPhone, not written
  for Honor); Coolmuster Honor backup guide
  (coolmuster.com/android/honor-backup-restore.html — the one
  Honor-specific backup guide found in the wider search, and it
  is not a phone-to-USB guide at all: Honor Suite PC tool,
  Honor Cloud, Google backup); Honor's own support page
  (iknow-dl.service.hihonor.com — Honor's own routes:
  HonorSuite to a PC, Device Clone to another phone, and Backup
  & restore to external storage, a USB drive or a memory card,
  which writes an encrypted backup package, not plain photo
  files); Honor Share (honor.com — moves files between Honor
  phones over Wi-Fi Direct, or to an Honor notebook with PC
  Manager 15.0 and above; phone-to-phone or phone-to-notebook,
  not phone-to-USB); Photos to USB: OTG Backup (Play listing,
  com.arzdrz.photos_to_usb) is the one-tap app alternative from
  the wider search — a real phone-to-USB app, one-tap
  whole-gallery copy, only new photos next time, warns about the
  FAT32 4 GB limit, free with ads, one-time purchase removes ads
  — generic Android, not an Honor guide, and it does not promise
  oldest-first order, detailed progress with Pause and Stop, or
  size verification, so the article says so honestly instead of
  claiming SnapDisk is the only one-tap option. Cloud is Google
  Photos or Honor Cloud — cloud, not USB. Honor specifics stated
  honestly: no documented MagicOS OTG switch location was found
  — the Honor videos show the drive mounting on plug-in with a
  "USB storage added" notification; if nothing appears, search
  Settings for "OTG"; whether a switch exists at all, or
  switches itself off, is unverified; not every Honor phone has
  OTG; stock file manager is Files (category-based: Images,
  Videos, Documents, Audio; integrates with Honor Cloud); stock
  gallery is Gallery; modern Honor phones are USB-C, older ones
  Micro-USB; whether a model has a microSD slot varies by model
  (check the model's specs). Honor's own tools: Backup &
  restore to a USB drive writes an encrypted backup package
  (Settings > System & updates > Backup & restore > Data backup
  > External storage > USB storage; password required,
  restorable only through Honor Backup on an Honor phone) — not
  plain photo files; Honor Share is phone-to-phone transfer or
  to an Honor notebook; Device Clone is phone-to-phone
  migration; Honor Cloud is cloud backup (Settings > Cloud
  Backup, automatic, free tier limited) — none of them writes
  plain photo files to a USB drive. Honest contrast stated in
  the TL;DR: Files works but is manual select-copy-paste with no
  progress, no resume, no verification; Honor's own tools work
  but write an encrypted backup package, move data to another
  phone or a notebook, or back it up to the cloud — not plain
  photos on a USB drive; Google Photos / Honor Cloud is cloud,
  not a USB copy; Photos to USB also does one-tap copy with
  resume — SnapDisk adds oldest-first order, detailed progress
  with Pause/Stop, size verification, no ads, and free 5 GB per
  run. Query 22 scored Nov 16, 2026 (searches.md): "back up
  photos from Honor to USB flash drive without computer" — real
  how-to gap. Volume unknown (Play publishes no volume);
  difficulty low. Linked from blog index (JSON-LD + post block),
  sitemap.xml, llms.txt, llms-full.txt (full section + Blog
  posts list, now 23), feed.xml (now 23 items). JSON-LD
  validated by parse; sitemap.xml and feed.xml parse as XML.
  Title 46 chars, meta 143 chars. No app code or Play listing
  files touched; applicationId untouched
  (dev.dzianisv.snapdisk). Work ran from a persistent scratch
  clone (~/workspace/pb-wl53-scratch, not the shared checkout,
  never /tmp). Evidence: PR #71 (commit 81e4052, merge 9f64e47,
  merged 2026-11-17T07:31:20Z); CI run 44039173134 (build pass,
  1m6s) green; post-merge main runs green (Android PR
  44039349387, pages 44039348781). Live fetch after Pages deploy:
  article 200, 29527 bytes, title "Back up Honor photos to a USB
  drive — SnapDisk" present; blog index 200, 21408 bytes;
  feed.xml 200 (23 items); sitemap.xml 200 with the new link;
  llms-full.txt 200, 244067 bytes, Blog list counts 23 with
  Honor present.

## WL-52 — Back up Vivo photos to USB article, 22nd how-to (DONE)

- Owner: SEO/GEO lane lead. State: DONE.
  New docs/vivo-photos-to-usb.html ("Back up Vivo photos to a
  USB drive (no computer)"), following the WL-47 article template
  exactly (navy/cyan, TL;DR first, no JS, text-only, reuses
  docs/screenshots-01-settings-defaults.png as the lead shot —
  same reuse as the 8th through 21st articles). Facts verified
  from code on fresh main: SAF tree picker (MainActivity.kt
  OpenDocumentTree + takePersistableUriPermission); oldest-first
  via MediaStore DATE_TAKEN ascending; free 5 GB per run
  (ProPrefs.FREE_BACKUP_BYTES_LIMIT); Pro $9.99 one-time =
  auto-backup on connect + unlimited size; no INTERNET
  permission in AndroidManifest.xml; FAT32 files >=4 GiB skipped
  with reason (FilesystemInfo.FAT32_MAX_FILE_BYTES); raw
  byte-stream copy (BackupEngine.copyWithResume — no
  decode/re-encode) with size verification after copy. Search
  evidence (Nov 16, 2026 live web search): no Vivo-specific
  result in the top results at all — FoneLab guide
  (fonelab.com/resource/how-to-transfer-photos-from-android-
  phone-to-usb-flash-drive.html — OTG adapter + file manager,
  PC File Explorer, or HyperTrans PC tool; generic Android, two
  of the three ways need a computer); AnExplorer guide
  (anexplorer.io/transfer/phone-to-usb; manual select-copy-paste,
  the only one of the five that shows a progress bar with speed
  and time remaining, still no resume or verification); YouTube
  video (youtube.com/watch?v=PYcbV25CczI — generic OTG + file
  manager walkthrough); MobiKin guide
  (mobikin.com/android-backup/transfer-files-from-android-to-
  usb-flash-drive.html — generic: PC drag-and-drop, Assistant PC
  tool, OTG copy, Files by Google, AirDroid); VaultDrive page
  (coticsy.com/vaultdrive.html — a rival app's own page, generic
  Android and iPhone, not a Vivo guide: free for up to 200 files
  or 2 GB per backup, Pro $19.99 once; does not promise
  oldest-first order, detailed progress with Pause and Stop, or
  size verification); MobiKin vivo Cloud guide
  (mobikin.com/android-backup/vivo-cloud-backup.html — the one
  Vivo-specific backup guide found in the wider search, and it
  is not a phone-to-USB guide at all: vivo Cloud, Google backup,
  PC tool); vivo's own EasyShare FAQ
  (vivo.com/bt/content/easyshare — EasyShare moves files between
  two phones, or backs the phone up to a PC; phone-to-phone or
  phone-to-PC, not phone-to-USB); Photos to USB: OTG Backup
  (Play listing, com.arzdrz.photos_to_usb) is the one-tap app
  alternative from the wider search — a real phone-to-USB app,
  one-tap whole-gallery copy, only new photos next time, warns
  about the FAT32 4 GB limit, free with ads, one-time purchase
  removes ads — generic Android, not a Vivo guide, and it does
  not promise oldest-first order, detailed progress with Pause
  and Stop, or size verification, so the article says so
  honestly instead of claiming SnapDisk is the only one-tap
  option. Cloud is Google Photos or vivo Cloud — cloud, not USB.
  Vivo specifics stated honestly: Funtouch OS has an "OTG"
  switch (Settings > More settings > OTG — search Settings for
  "OTG"; vivo's own FAQ says the switch turns itself off if it
  is not used in 5 minutes, to save power), and not every Vivo
  phone has OTG; stock file manager is File Manager
  (category-based: Images, Videos, Audio, Documents, Downloads);
  stock gallery is Albums (photo backup there runs through
  Google Photos); modern Vivo phones (X, V, recent Y series) are
  USB-C, older ones Micro-USB; whether a model has a microSD
  slot varies by model (budget Y and T series phones include one
  — not verified for any other model, check the model's specs).
  Vivo's own tools: EasyShare is phone-to-phone transfer (Send
  on one phone, Accept on the other) or backup to a PC via
  EasyShare on the computer (USB cable or Wi-Fi) — needs a
  second phone or a PC; vivo Cloud is cloud backup (5 GB free,
  automatic backup about every 7 days when charging, on Wi-Fi,
  and locked; device backup covers system data, call logs,
  messages, and apps, photos sync through Albums or Google
  Photos) — none of them writes plain photo files to a USB
  drive. Honest contrast stated in the TL;DR: File Manager
  works but is manual select-copy-paste with no progress, no
  resume, no verification; Vivo's own tools work but move data
  to another phone, to a PC, or to the cloud — not plain photos
  on a USB drive; Google Photos / vivo Cloud is cloud, not a USB
  copy; Photos to USB also does one-tap copy with resume —
  SnapDisk adds oldest-first order, detailed progress with
  Pause/Stop, size verification, no ads, and free 5 GB per run.
  Query 21 scored Nov 16, 2026 (searches.md): "back up photos
  from Vivo to USB flash drive without computer" — real how-to
  gap. Volume unknown (Play publishes no volume); difficulty
  low. Linked from blog index (JSON-LD + post block),
  sitemap.xml, llms.txt, llms-full.txt (full section + Blog
  posts list, now 22), feed.xml (now 22 items). JSON-LD
  validated by parse; sitemap.xml and feed.xml parse as XML.
  Title 45 chars, meta 142 chars. No app code or Play listing
  files touched; applicationId untouched
  (dev.dzianisv.snapdisk). Work ran from a persistent scratch
  clone (~/workspace/pb-wl52-scratch, not the shared checkout,
  never /tmp). Evidence: PR #69 (commit 8245fa9, merge b01d86d,
  merged 2026-11-17T06:31:19Z); CI run 44031575908 (build pass,
  1m25s) green; post-merge main runs green (Android PR
  44031831026, pages 44031829397). Live fetch after Pages deploy:
  article 200, 27441 bytes, title "Back up Vivo photos to a USB
  drive — SnapDisk" present; blog index 200, 20530 bytes;
  feed.xml 200 (22 items); sitemap.xml 200 with the new link;
  llms-full.txt 200, 230216 bytes, Blog list counts 22 with
  Vivo present.

## WL-51 — Back up Realme photos to USB article, 21st how-to (DONE)

- Owner: SEO/GEO lane lead. State: DONE.
  New docs/realme-photos-to-usb.html ("Back up Realme photos to a
  USB drive (no computer)"), following the WL-47 article template
  exactly (navy/cyan, TL;DR first, no JS, text-only, reuses
  docs/screenshots-01-settings-defaults.png as the lead shot —
  same reuse as the 8th through 20th articles). Facts verified
  from code on fresh main: SAF tree picker (MainActivity.kt
  OpenDocumentTree + takePersistableUriPermission); oldest-first
  via MediaStore DATE_TAKEN ascending; free 5 GB per run
  (ProPrefs.FREE_BACKUP_BYTES_LIMIT); Pro $9.99 one-time =
  auto-backup on connect + unlimited size; no INTERNET
  permission in AndroidManifest.xml; FAT32 files >=4 GiB skipped
  with reason (FilesystemInfo.FAT32_MAX_FILE_BYTES); raw
  byte-stream copy (BackupEngine.copyWithResume — no
  decode/re-encode) with size verification after copy. Search
  evidence (Nov 16, 2026 live web search): no Realme-specific
  result in the top results at all — AnExplorer guide
  (anexplorer.io/transfer/phone-to-usb; manual select-copy-paste,
  the only one of the six that shows a progress bar with speed
  and time remaining, still no resume or verification); YouTube
  video (youtube.com/watch?v=PYcbV25CczI — generic OTG + file
  manager walkthrough); AskFilo guide (generic: OTG + file
  manager, or a PC as a middleman); Dr.Fone (Google Drive / PC
  drag-and-drop / OTG manual copy / AirDroid); TheGadgetians
  guide (generic phone-to-USB: OTG adapter + file manager);
  Move Files USB/OTG/Internal (Play listing,
  com.fbd.moveapps.sdcard.dp — a generic file-manager app that
  detects an OTG drive and moves files in bulk; manual file
  picking, not an automated backup, and it does not promise
  oldest-first order, progress with Pause and Stop, or
  verification); MobiKin realme backup guide
  (mobikin.com/android-backup/back-up-and-restore-realme.html —
  the one Realme-specific backup guide found in the wider search,
  and it is not a phone-to-USB guide at all: PC tool, Google
  backup, HeyTap Cloud, local backup on the phone); Photos to
  USB: OTG Backup (Play listing, com.arzdrz.photos_to_usb) is
  the one-tap app alternative from the wider search — a real
  phone-to-USB app, one-tap whole-gallery copy with resume, free
  with ads — generic Android, not a Realme guide, and it does not
  promise oldest-first order, detailed progress with Pause and
  Stop, or size verification, so the article says so honestly
  instead of claiming SnapDisk is the only one-tap option. Cloud
  is Google Photos or HeyTap Cloud — cloud, not USB. Realme
  specifics stated honestly: Realme UI has an "OTG connection"
  switch (Settings > Additional settings on most versions, System
  Settings on some — search Settings for "OTG"; some models also
  offer an OTG tile in Quick Settings) that switches off
  automatically after about 10 minutes without a connected
  accessory, and not every Realme phone has OTG; stock file
  manager is File Manager (drive appears under Categories >
  Storage locations); modern Realme phones (GT, Pro, number
  series) are USB-C, older ones Micro-USB; whether a model has a
  microSD slot varies by model — stated in the article as
  unverified, not guessed. Realme's own tools: Clone Phone is
  phone-to-phone migration (new phone shows a QR code, old phone
  scans it, direct connection — needs a second phone), HeyTap
  Cloud is cloud backup (Settings > HeyTap Cloud, 5 GB free,
  auto-backup about every 7 days; Optimize Storage keeps
  originals in the cloud), Local Backup stores a backup file on
  the phone itself — none of them writes plain photo files to a
  USB drive. Honest contrast stated in the TL;DR: File Manager
  works but is manual select-copy-paste with no progress, no
  resume, no verification; Realme's own tools work but move data
  to another phone, to the cloud, or keep it on the same phone —
  not plain photos on a USB drive; Google Photos / HeyTap Cloud
  is cloud, not a USB copy; Photos to USB also does one-tap copy
  with resume — SnapDisk adds oldest-first order, detailed
  progress with Pause/Stop, size verification, no ads, and free
  5 GB per run. Query 20 scored Nov 16, 2026 (searches.md):
  "back up photos from Realme to USB flash drive without
  computer" — real how-to gap. Volume unknown (Play publishes no
  volume); difficulty low. Linked from blog index (JSON-LD + post
  block), sitemap.xml, llms.txt, llms-full.txt (full section +
  Blog posts list, now 21), feed.xml (now 21 items). JSON-LD
  validated by parse; sitemap.xml and feed.xml parse as XML.
  Title 47 chars, meta 144 chars. No app code or Play listing
  files touched; applicationId untouched
  (dev.dzianisv.snapdisk). Work ran from a persistent scratch
  clone (~/workspace/pb-wl51-scratch, not the shared checkout,
  never /tmp). Evidence: PR #67 (commit de132ee, merge 6ac409b,
  merged 2026-11-17T05:31:54Z); CI run 44024585720 (build pass,
  1m11s) green; post-merge main runs green (Android PR
  44024762024, pages 44024761555). Live fetch after Pages deploy:
  article 200, 27776 bytes, title "Back up Realme photos to a USB
  drive — SnapDisk" present; blog index 200, 19665 bytes;
  feed.xml 200 (21 items); sitemap.xml 200 with the new link;
  llms-full.txt 200, 213306 bytes, Blog list counts 21 with
  Realme present.

## WL-50 — Back up Oppo photos to USB article, 20th how-to (DONE)

- Owner: SEO/GEO lane lead. State: DONE.
  New docs/oppo-photos-to-usb.html ("Back up Oppo photos to a USB
  drive (no computer)"), following the WL-47 article template
  exactly (navy/cyan, TL;DR first, no JS, text-only, reuses
  docs/screenshots-01-settings-defaults.png as the lead shot —
  same reuse as the 8th through 19th articles). Facts verified
  from code on fresh main: SAF tree picker (MainActivity.kt
  OpenDocumentTree + takePersistableUriPermission); oldest-first
  via MediaStore DATE_TAKEN ascending; free 5 GB per run
  (ProPrefs.FREE_BACKUP_BYTES_LIMIT); Pro $9.99 one-time =
  auto-backup on connect + unlimited size; no INTERNET
  permission in AndroidManifest.xml; FAT32 files >=4 GiB skipped
  with reason (FilesystemInfo.FAT32_MAX_FILE_BYTES); raw
  byte-stream copy (BackupEngine.copyWithResume — no
  decode/re-encode) with size verification after copy. Search
  evidence (Nov 16, 2026 live web search): the one Oppo-specific
  result is the Coolmuster guide
  (coolmuster.com/android/transfer-files-from-oppo-to-usb-flash-drive.html
  — OTG adapter + File Manager manual copy, USB-C drive direct,
  or the Coolmuster Android Assistant PC tool); FoneLab guide
  (generic: OTG + file manager, PC File Explorer, HyperTrans PC
  tool); Dr.Fone (Google Drive / PC drag-and-drop / OTG manual
  copy / AirDroid); AnExplorer guide (manual select-copy-paste,
  anexplorer.io/transfer/phone-to-usb; the only one of the five
  that shows a progress bar for the manual copy, still no resume
  or verification); MobiKin guide (generic: PC drag-and-drop,
  Assistant PC tool, OTG copy, Files by Google, AirDroid);
  Photos to USB: OTG Backup (Play listing,
  com.arzdrz.photos_to_usb) is the one-tap app alternative from
  the wider search — a real phone-to-USB app, one-tap
  whole-gallery copy with resume, free with ads — generic
  Android, not an Oppo guide, and it does not promise
  oldest-first order, detailed progress with Pause and Stop, or
  size verification, so the article says so honestly instead of
  claiming SnapDisk is the only one-tap option. Cloud is Google
  Photos or HeyTap Cloud — cloud, not USB. Oppo specifics stated
  honestly: ColorOS has an "OTG connection" switch (Settings >
  Additional settings on older ColorOS, System settings on some
  versions, Accessibility & convenience on ColorOS 15 and newer
  — search Settings for "OTG") that switches off automatically
  after about 10 minutes without a connected accessory, and not
  every Oppo phone has OTG; stock file manager is File Manager,
  stock gallery is Photos; modern Oppo phones (Find X, Reno,
  recent A-series) are USB-C, older ones Micro-USB; most recent
  Oppo phones have no microSD slot. Oppo's own tools: Clone
  Phone is phone-to-phone migration over Wi-Fi Direct (needs a
  second phone), HeyTap Cloud is cloud backup (Settings >
  HeyTap Cloud) — neither of them writes plain photo files to a
  USB drive. Honest contrast stated in the TL;DR: File Manager
  works but is manual select-copy-paste with no progress, no
  resume, no verification; Oppo's own tools work but move data
  to another phone or to the cloud — not plain photos on a USB
  drive; Google Photos / HeyTap Cloud is cloud, not a USB copy;
  Photos to USB also does one-tap copy with resume — SnapDisk
  adds oldest-first order, detailed progress with Pause/Stop,
  size verification, no ads, and free 5 GB per run. Query 19
  scored Nov 16, 2026 (searches.md): "back up photos from Oppo
  to USB flash drive without computer" — real how-to gap. Volume
  unknown (Play publishes no volume); difficulty low-medium.
  Linked from blog index (JSON-LD + post block), sitemap.xml,
  llms.txt, llms-full.txt (full section + Blog posts list, now
  20), feed.xml (now 20 items). JSON-LD validated by parse;
  sitemap.xml and feed.xml parse as XML. Title 45 chars, meta
  142 chars. No app code or Play listing files touched;
  applicationId untouched (dev.dzianisv.snapdisk). Work ran from
  a persistent scratch clone (~/workspace/pb-wl50-scratch, not
  the shared checkout, never /tmp). Evidence: PR #65 (commit
  78dc3a0, merge 623bcc9, merged 2026-11-17T04:35:18Z); CI run
  44017899476 (build pass, 1m19s) green; post-merge main runs
  green (Android PR 44018223732, pages 44018223214). Live fetch
  after Pages deploy: article 200, 26135 bytes, title "Back up
  Oppo photos to a USB drive — SnapDisk" present; blog index
  200, 18825 bytes; feed.xml 200 (20 items); sitemap.xml 200
  with the new link; llms-full.txt 200, 196178 bytes, Blog list
  counts 20 with Oppo present.

## WL-49 — Back up LG photos to USB article, 19th how-to (DONE)

- Owner: SEO/GEO lane lead. State: DONE.
  New docs/lg-photos-to-usb.html ("Back up LG photos to a USB
  drive (no computer)"), following the WL-47 article template
  exactly (navy/cyan, TL;DR first, no JS, text-only, reuses
  docs/screenshots-01-settings-defaults.png as the lead shot —
  same reuse as the 8th through 18th articles). Facts verified
  from code on fresh main: SAF tree picker (MainActivity.kt
  OpenDocumentTree + takePersistableUriPermission); oldest-first
  via MediaStore DATE_TAKEN ascending; free 5 GB per run
  (ProPrefs.FREE_BACKUP_BYTES_LIMIT); Pro $9.99 one-time =
  auto-backup on connect + unlimited size; no INTERNET
  permission in AndroidManifest.xml; FAT32 files >=4 GiB skipped
  with reason (FilesystemInfo.FAT32_MAX_FILE_BYTES); raw
  byte-stream copy (BackupEngine.copyWithResume — no
  decode/re-encode) with size verification after copy. Search
  evidence (Nov 16, 2026 live web search): no top result is an
  LG-specific guide — Ubuy photo-stick product listing (a
  dual-connector flash drive sold as compatible with LG,
  Samsung, Google, Motorola — a stick to buy, not a guide;
  its URL was truncated in the search output, so the article
  names it without linking); AnExplorer guide (manual
  select-copy-paste, anexplorer.io/transfer/phone-to-usb; the
  only one showing a progress bar for the manual copy, still no
  resume or verification), YouTube PYcbV25CczI (OTG + file
  manager manual copy, suggests Move), Dr.Fone (Google Drive /
  PC drag-and-drop / OTG manual copy / AirDroid); Photos to USB:
  OTG Backup (Play listing, com.arzdrz.photos_to_usb) is the
  one non-manual result — a real phone-to-USB app, one-tap
  whole-gallery copy with resume, free with ads — generic
  Android, not an LG guide, and it does not promise
  oldest-first order, detailed progress with Pause and Stop, or
  size verification, so the article says so honestly instead of
  claiming SnapDisk is the only one-tap option. Cloud is Google
  Photos — cloud, not USB. LG specifics stated honestly: LG
  left the phone business in April 2021 (wound down July 31,
  2021) — no new LG phones, no new LG tools, and LG Mobile
  Switch is no longer updated (struggles on newer Android
  versions); stock file manager is LG File Manager (Management
  > File Manager), stock gallery is Gallery; no OTG toggle —
  plug the drive in and it mounts; late LG phones (G7, G8, V30
  and later, Velvet, Wing, V60) are USB-C, older ones
  Micro-USB; most LG phones have a microSD slot. LG's own
  tools: LG Mobile Switch is phone-to-phone migration (USB
  cable / Wi-Fi / SD card), LG Bridge is PC software that backs
  the phone up to a computer, LG Backup (Settings, Backup &
  reset) writes a backup to an SD card or the cloud — none of
  them writes plain photo files to a USB drive. Honest contrast
  stated in the TL;DR: LG File Manager works but is manual
  select-copy-paste with no progress, no resume, no
  verification; LG's own tools work but write to another phone,
  a PC, or a backup package — not plain photos on a USB drive;
  Google Photos is cloud, not a USB copy; Photos to USB also
  does one-tap copy with resume — SnapDisk adds oldest-first
  order, detailed progress with Pause/Stop, size verification,
  no ads, and free 5 GB per run. Query 18 scored Nov 16, 2026
  (searches.md): "back up photos from LG to USB flash drive
  without computer" — real how-to gap. Volume unknown (Play
  publishes no volume); difficulty low-medium. Linked from blog
  index (JSON-LD + post block), sitemap.xml, llms.txt,
  llms-full.txt (full section + Blog posts list, now 19),
  feed.xml (now 19 items). JSON-LD validated by parse;
  sitemap.xml and feed.xml parse as XML. Title 43 chars, meta
  140 chars. No app code or Play listing files touched;
  applicationId untouched (dev.dzianisv.snapdisk). NOTE: the
  first fresh clone for this work (/tmp/pb-wl49) was wiped
  mid-run by the VM (tmpfs /tmp emptied between exec calls) —
  the article and all linking edits were rebuilt from scratch
  in a persistent scratch clone
  (~/workspace/pb-wl49-scratch, not the shared checkout) and
  re-verified before the PR; nothing was lost in the rebuild.
  Evidence: PR #63 (commit c7c9d27, merge 4e1407e, merged
  2026-11-17T03:42:52Z); CI run 44011824019 (build pass, 1m3s)
  green; post-merge main runs green (Android PR 44012125216,
  pages 44012124980). Live fetch after Pages deploy: article
  200, 25259 bytes, title "Back up LG photos to a USB drive —
  SnapDisk" present, "November 16, 2026" present; blog index
  200, 17999 bytes; feed.xml 200 (19 items); sitemap.xml 200
  with the new link; llms-full.txt 200, 180503 bytes, Blog list
  counts 19 with LG present.

## WL-48 — Complete the Blog post list in llms-full.txt (DONE)

- Owner: SEO/GEO lane lead. State: DONE.
  The "## Blog" posts list in docs/llms-full.txt still ended at the
  Samsung article (11 posts) while the blog has 18 articles — the
  WL-41–47 articles added their full "## Guides" sections but the
  Blog list was never extended past Samsung. Added the 7 missing
  titles in blog order (Pixel, OnePlus, Xiaomi, Motorola, videos,
  Huawei, Sony Xperia), verified against docs/blog/index.html and
  docs/feed.xml (18 items). One line changed, nothing else touched;
  no app code, no Play listing files, applicationId untouched
  (dev.dzianisv.snapdisk). This work ran from a fresh clone
  (/tmp/pb-wl48), never the shared checkout.
  Evidence: PR #61 (commit 3b7aae4, merge fed9e90, merged
  2026-11-17T02:53:13Z); CI run 44005849540 (build pass, 1m52s)
  green; post-merge main runs green (Android PR 44006220329,
  pages 44006219726). Live fetch after Pages deploy: llms-full.txt
  200, 165731 bytes, Blog posts list counts 18, Pixel and Sony
  Xperia present.

## WL-47 — Back up Sony Xperia photos to USB article, 18th how-to (DONE)

- Owner: SEO/GEO lane lead. State: DONE.
  New docs/sony-xperia-photos-to-usb.html ("Back up Sony Xperia
  photos to a USB drive (no computer)"), following the WL-46
  article template exactly (navy/cyan, TL;DR first, no JS,
  text-only, reuses docs/screenshots-01-settings-defaults.png as
  the lead shot — same reuse as the 8th through 17th articles).
  Facts verified from code on fresh main: SAF tree picker
  (MainActivity.kt OpenDocumentTree + takePersistableUriPermission);
  oldest-first via MediaStore DATE_TAKEN ascending; free 5 GB per
  run (ProPrefs.FREE_BACKUP_BYTES_LIMIT); Pro $9.99 one-time =
  auto-backup on connect + unlimited size; no INTERNET
  permission in AndroidManifest.xml; FAT32 files >=4 GiB skipped
  with reason (FilesystemInfo.FAT32_MAX_FILE_BYTES); raw
  byte-stream copy (BackupEngine.copyWithResume — no
  decode/re-encode) with size verification after copy. Search
  evidence (Nov 16, 2026 live web search): top results are all
  generic manual copy — YouTube PYcbV25CczI (OTG + file manager
  manual copy, suggests Move), AnExplorer guide (manual
  select-copy-paste, anexplorer.io/transfer/phone-to-usb; the
  only one showing a progress bar for the manual copy, still no
  resume or verification), Dr.Fone (Google Drive / PC
  drag-and-drop / OTG manual copy / AirDroid), YouTube
  ch1evyu5PPM (OTG + file manager manual copy, suggests Move),
  askfilo (OTG manual copy or a PC middleman); Photos to USB:
  OTG Backup (Play listing, com.arzdrz.photos_to_usb) is the
  one non-manual result — a real phone-to-USB app, one-tap
  whole-gallery copy with resume, free with ads — generic
  Android, not an Xperia guide, and it does not promise
  oldest-first order, detailed progress with Pause and Stop, or
  size verification, so the article says so honestly instead of
  claiming SnapDisk is the only one-tap option. Cloud is Google
  Photos — cloud, not USB. Sony specifics stated honestly: no
  OTG toggle — near-stock Android mounts the drive on its own;
  stock file manager is Files by Google, stock gallery is
  Google Photos (Sony ships almost no apps of its own on recent
  Xperia phones); microSDXC in the shared SIM slot varies by
  model (up to 1.5 TB on the Xperia 1 VI); Xperia 1 VI USB-C
  is USB 3.2 Gen 1 (5 Gbit/s), Xperia 10 VI is USB 2.0; Sony's
  own tool is Xperia Companion — PC software that backs the
  phone up to a computer as a package, not a phone-to-USB copy
  (older Xperia Backup & Restore could write a backup to a USB
  drive via a host adapter, again a backup package, not plain
  photo files). Honest contrast stated in the TL;DR: Files by
  Google works but is manual select-copy-paste with no progress,
  no resume, no verification; Xperia Companion works but needs
  a PC and writes a backup package, not plain photos; Google
  Photos is cloud, not a USB copy; Photos to USB also does
  one-tap copy with resume — SnapDisk adds oldest-first order,
  detailed progress with Pause/Stop, size verification, no ads,
  and free 5 GB per run. Query 17 scored Nov 16, 2026
  (searches.md): "back up photos from Sony Xperia to USB flash
  drive without computer" — real how-to gap. Volume unknown
  (Play publishes no volume); difficulty low-medium. Linked
  from blog index (JSON-LD + post block), sitemap.xml,
  llms.txt, llms-full.txt (full section), feed.xml (now 18
  items). JSON-LD validated by parse; sitemap.xml and feed.xml
  parse as XML. Title 52 chars, meta 149 chars. No app code or
  Play listing files touched; applicationId untouched
  (dev.dzianisv.snapdisk). Sitemap priority fixed 0.5 → 0.8
  before commit to match the sibling article blocks. This work
  ran from a fresh clone (repo-wl47-sony), never the shared
  checkout.
  Evidence: PR #59 (commit c8034ce, merge 1b7c16f, merged
  2026-11-17T02:38:36Z); CI run 44003965582 (build pass, 1m1s)
  green; post-merge main runs green (Android PR 44004497108,
  pages 44004496408). Live fetch after Pages deploy: article
  200, 25413 bytes, title "Back up Sony Xperia photos to a USB
  drive — SnapDisk" present, "November 16, 2026" present; blog
  index 200, 17256 bytes; feed.xml 200 (18 items); sitemap.xml
  200 with the new link.

## 2026-11-16 — WL-45 / WL-46 + searches.md numbering dedupe (dispatcher)

- Owner: Product Lead (daily loop). State: DONE.
  Two lanes shipped in parallel Nov 16 and collided on labels: the
  videos article (PR #53, merge 4d74d9f) and the Huawei article
  (PR #54, merge 5ecebeb) were both briefed as WL-45 / 16th
  how-to, and searches.md carried two "### 13" sections (the
  videos query duplicating Xiaomi's). Resolved in scoring order,
  no rows deleted: videos stays WL-45 (16th article, query
  13 → 15); Huawei renumbered WL-45 → WL-46 (17th article,
  query 15 → 16). Both articles live and verified (feed 17 items).
  Evidence: PR #57 (commit c35f0cc, merge 337deee, merged
  2026-11-17T02:12:54Z); CI run 44001085487 (build pass, 1m12s)
  green; post-merge main runs green (Android PR 44001312443,
  pages 44001312598).

## WL-46 — Back up Huawei photos to USB article, 17th how-to (DONE)

- Owner: SEO/GEO lane lead. State: DONE.
  New docs/huawei-photos-to-usb.html ("Back up Huawei photos
  to a USB drive (no computer)"), following the WL-44
  article template exactly (navy/cyan, TL;DR first, no JS,
  text-only, reuses docs/screenshots-01-settings-defaults.png as
  the lead shot — same reuse as the 8th, 9th, 10th, 11th, 12th,
  13th, 14th, 15th, and 16th articles). Facts verified from code
  on fresh main: SAF tree picker (MainActivity.kt OpenDocumentTree +
  takePersistableUriPermission); oldest-first via MediaStore
  DATE_TAKEN ascending; free 5 GB per run
  (ProPrefs.FREE_BACKUP_BYTES_LIMIT); Pro $9.99 one-time =
  auto-backup on connect + unlimited size; no INTERNET
  permission in AndroidManifest.xml; FAT32 files >=4 GiB skipped
  with reason (FilesystemInfo.FAT32_MAX_FILE_BYTES); raw
  byte-stream copy (BackupEngine.copyWithResume — no
  decode/re-encode) with size verification after copy. Search
  evidence (Nov 16, 2026 live web search): top results are
  Huawei's own OTG tutorial (manual select-copy-paste in the
  Files app, consumer.huawei.com/ph/community/details/topicId-72169/),
  Huawei Backup (consumer.huawei.com support — Settings, System &
  updates, Backup & restore, Data backup, External storage, USB
  storage — writes an encrypted backup archive, password required
  to restore, restorable only through Huawei Backup on a Huawei
  phone, not plain photo files), AnExplorer guide (manual
  select-copy-paste, anexplorer.io/transfer/phone-to-usb; the
  only one showing a progress bar for the manual copy, still no
  resume or verification), MobiKin (PC drag-and-drop, PC tool,
  OTG manual copy, Files by Google, AirDroid), FoneLab (OTG
  manual copy in the Files app, PC tool HyperTrans, Samsung
  Smart Switch — not a Huawei route). Cloud is Huawei Cloud on
  newer phones (Mate 30 and later ship without Google services —
  no Google Play, no Google Photos preinstalled), Google Photos
  on older ones — cloud, not USB. Huawei specifics stated
  honestly: no OTG toggle — plug the drive in and open Files;
  stock file manager is Files, stock gallery is Gallery;
  expandable storage varies by model — many recent Huawei
  flagships take Huawei's NM (Nano Memory) card (nano-SIM size,
  64/128/256 GB) instead of microSD, older models take microSD,
  some have no card slot; Mate 30 and newer have no Google Play,
  so the v0.1.2 APK from the GitHub release page is the install
  route. Honest contrast stated in the TL;DR: Huawei Backup works
  but writes an encrypted archive, not plain photos; Files works
  but is manual select-copy-paste with no progress, no resume, no
  verification; cloud backup is cloud, not a USB copy; SnapDisk =
  plug in, tap Backup, whole library oldest-first with %, GB
  copied/total, speed, ETA, Pause/Stop, resume on re-plug,
  size-verified, copies never deletes. Query 16 scored Nov 16,
  2026 (searches.md): "back up photos from Huawei to USB flash
  drive without computer" — real how-to gap. Volume unknown
  (Play publishes no volume); difficulty low-medium. Linked from
  blog index (JSON-LD + post block), sitemap.xml, llms.txt,
  llms-full.txt (full section), feed.xml (now 17 items). JSON-LD
  validated by parse; sitemap.xml and feed.xml parse as XML.
  Title 47 chars, meta 144 chars. No app code or Play listing
  files touched; applicationId untouched (dev.dzianisv.snapdisk).
  NUMBERING NOTE: this task was briefed as WL-45 / 16th how-to,
  but a sibling lane shipped the videos article under the same
  WL-45 / 16th labels first (PR #53, merge 4d74d9f, merged
  2026-11-17T01:56:31Z), so this article is the 17th how-to live
  and the Work Log briefly carried two WL-45 rows — resolved by
  the dispatcher Nov 16 (this row renumbered WL-45 → WL-46; see
  the dedupe entry above it). Also: searches.md briefly carried
  two "### 13" sections (the videos query was numbered 13,
  duplicating the Xiaomi row) — resolved in the same fix: videos
  query 13 → 15, Huawei query 15 → 16, in scoring order. This work ran from a
  separate fresh clone (repo-wl45-huawei) because the shared
  checkout was on the sibling's wl-45-videos-to-usb branch with
  uncommitted changes; no sibling files were touched.
  Evidence: PR #54 (commit 9545f8a, merge 5ecebeb, merged
  2026-11-17T02:02:38Z); CI run 43999854157 (build pass, 1m10s)
  green; post-merge main runs green (Android PR 44000056564,
  pages 44000055243). Live fetch after Pages deploy: article 200,
  25184 bytes, title "Back up Huawei photos to a USB drive —
  SnapDisk" present, "November 16, 2026" present; blog index 200,
  16481 bytes; feed.xml 200 (17 items); sitemap.xml 200 with the
  new link.

## WL-45 — Back up videos from Android to USB article, 16th how-to (DONE)

- Owner: SEO/GEO lane lead. State: DONE.
  New docs/videos-to-usb.html ("Back up videos from Android to a
  USB drive (no computer)"), following the WL-44 article template
  exactly (navy/cyan, TL;DR first, no JS, text-only, reuses
  docs/screenshots-01-settings-defaults.png as the lead shot —
  same reuse as the 8th, 9th, 10th, 11th, 12th, 13th, 14th, and
  15th articles). Facts verified from code on fresh main: video
  support in PhotoQuery.kt (MediaStore.Video + VIDEO_EXTENSIONS
  .mp4/.mov/.mkv/.webm/.3gp/.m4v/.avi), oldest-first via MediaStore
  DATE_TAKEN ascending; free 5 GB per run
  (ProPrefs.FREE_BACKUP_BYTES_LIMIT); Pro $9.99 one-time =
  auto-backup on connect + unlimited size; no INTERNET
  permission in AndroidManifest.xml; FAT32 files >=4 GiB skipped
  with reason (FilesystemInfo.FAT32_MAX_FILE_BYTES); raw
  byte-stream copy (BackupEngine.copyWithResume — no
  decode/re-encode) with size verification after copy. Search
  evidence (Nov 16, 2026 live web search): top results are all
  manual copy — YouTube PYcbV25CczI (OTG + file manager manual
  copy, suggests Move), mobilityarena.com (USB-C manual copy via
  Files by Google, with the FAT32 4 GB warning for large videos),
  thetechylife.com (OTG manual copy or a PC), YouTube
  ch1evyu5PPM (OTG + file manager manual copy, suggests Move),
  sekin.in (Files by Google / My Files manual copy, or a PC);
  VaultDrive's own page (coticsy.com/vaultdrive.html) is the
  closest result — a real phone-to-USB app that backs up videos
  original-quality — but a product page, not a videos how-to,
  and its Pro is $19.99 once. Google Photos is cloud, not USB,
  and Storage saver downscales video. Video-specific angle
  stated honestly: a single file over 4 GB cannot live on a
  FAT32 drive, so exFAT is the format note; videos live in
  DCIM/Camera and Movies/Downloads. Honest contrast stated in
  the TL;DR: the file manager works but is manual
  select-copy-paste with no progress, no resume, no
  verification; VaultDrive also backs up videos original-quality
  — SnapDisk is not the only app that does; what it adds is the
  automated whole-library run and half the Pro price. Query 15
  scored Nov 16, 2026 (searches.md): "back up videos from
  Android to USB flash drive without computer" — real how-to
  gap. Volume unknown (Play publishes no volume); difficulty
  low-medium. Linked from blog index (JSON-LD + post block),
  sitemap.xml, llms.txt, llms-full.txt (full section), feed.xml
  (now 16 items). JSON-LD validated by parse; sitemap.xml and
  feed.xml parse as XML. Title 53 chars, meta 150 chars. No app
  code or Play listing files touched; applicationId untouched
  (dev.dzianisv.snapdisk).
  Evidence: PR #53 (commit 9146972, merge 4d74d9f, merged
  2026-11-17T01:55:51Z); CI run 43999049532 (build pass, 1m26s)
  green; post-merge main runs green (Android PR 43999308764,
  pages 43999308531). Live fetch after Pages deploy: article
  200, 22882 bytes, title "Back up videos from Android to a USB
  drive — SnapDisk" present, "November 16, 2026" present;
  blog index, feed.xml (16 items) and sitemap.xml all live with
  the new link.


The only task tracker for this product. One row per task: what, who,
state, evidence, date.

## WL-44 — Back up Motorola photos to USB article, 15th how-to (DONE)

- Owner: SEO/GEO lane lead. State: DONE.
  New docs/motorola-photos-to-usb.html ("Back up Motorola photos
  to a USB drive (no computer)"), following the WL-43
  article template exactly (navy/cyan, TL;DR first, no JS,
  text-only, reuses docs/screenshots-01-settings-defaults.png as
  the lead shot — same reuse as the 8th, 9th, 10th, 11th, 12th,
  13th, and 14th articles). Facts verified from code on fresh
  main: SAF tree picker (MainActivity.kt OpenDocumentTree +
  takePersistableUriPermission); oldest-first via MediaStore
  DATE_TAKEN ascending; free 5 GB per run
  (ProPrefs.FREE_BACKUP_BYTES_LIMIT); Pro $9.99 one-time =
  auto-backup on connect + unlimited size; no INTERNET
  permission in AndroidManifest.xml; FAT32 files >=4 GiB skipped
  with reason (FilesystemInfo.FAT32_MAX_FILE_BYTES); raw
  byte-stream copy (BackupEngine.copyWithResume — no
  decode/re-encode) with size verification after copy. Search
  evidence (Nov 16, 2026 live web search): top results are all
  manual copy — YouTube PYcbV25CczI (OTG + file manager manual
  copy, suggests Move; names the Motorola file manager as
  Files, or Files by Google), AnExplorer guide (manual
  select-copy-paste, anexplorer.io/transfer/phone-to-usb; the
  only one showing a progress bar for the manual copy, still no
  resume or verification), Dr.Fone (Google Drive / PC
  drag-and-drop / OTG manual copy / AirDroid), MobiKin (PC
  drag-and-drop, PC tool, OTG manual copy, Files by Google,
  AirDroid), YouTube ch1evyu5PPM (OTG + file manager manual
  copy, suggests Move), thegadgetians (file manager manual copy;
  mentions ThePhotoStick Omni, a dedicated stick with its own
  app, not an ordinary flash drive). Google Photos is cloud,
  not USB. Motorola specifics stated honestly: no OTG toggle —
  near-stock Android mounts the drive on its own ("USB
  available" notification, Files by Google under Storage
  devices); if the notification says the phone is charging a
  connected device, tap it and switch "Controlled by" to "This
  device"; stock file manager is Files by Google, stock gallery
  is Google Photos; microSD slot varies by model — most Moto G
  phones have one (up to 1 TB on recent models), Motorola Edge
  2024 and 2025 have none. Honest contrast stated in the TL;DR:
  Files by Google works but is manual select-copy-paste with no
  progress, no resume, no verification; Google Photos backup is
  cloud, not a USB copy; SnapDisk = plug in, tap Backup, whole
  library oldest-first with %, GB copied/total, speed, ETA,
  Pause/Stop, resume on re-plug, size-verified, copies never
  deletes. Query 14 scored Nov 16, 2026 (searches.md): "back
  up photos from Motorola to USB flash drive without computer"
  — real how-to gap. Volume unknown (Play publishes no volume);
  difficulty low-medium. Linked from blog index (JSON-LD + post
  block), sitemap.xml, llms.txt, llms-full.txt (full section),
  feed.xml (now 15 items). JSON-LD validated by parse;
  sitemap.xml and feed.xml parse as XML. Title 49 chars, meta
  146 chars. No app code or Play listing files touched;
  applicationId untouched (dev.dzianisv.snapdisk).
  Evidence: PR #51 (commit 1a2cce0, merge 7ea1a76, merged
  2026-11-17T01:34:08Z); CI run 43996397815 (build pass, 58s)
  green. Live fetch after Pages deploy: article 200, 22760
  bytes, title "Back up Motorola photos to a USB drive —
  SnapDisk" present, "November 16, 2026" present; blog index 200, 15109 bytes; feed.xml 200 (15 items); sitemap.xml 200
  with the new link.

## WL-43 — Back up Xiaomi photos to USB article, 14th how-to (DONE)

- Owner: SEO/GEO lane lead. State: DONE.
  New docs/xiaomi-photos-to-usb.html ("Back up Xiaomi photos
  to a USB drive (no computer)"), following the WL-42
  article template exactly (navy/cyan, TL;DR first, no JS,
  text-only, reuses docs/screenshots-01-settings-defaults.png as
  the lead shot — same reuse as the 8th, 9th, 10th, 11th, 12th,
  and 13th articles). Facts verified from code on fresh main: SAF
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
  verification), FoneLab (OTG manual copy in the Files app, plus
  PC tool HyperTrans), Dr.Fone (Google Drive / PC drag-and-drop /
  OTG manual copy / AirDroid), YouTube PYcbV25CczI (OTG + file
  manager manual copy, suggests Move), EpicTechTips (file manager
  manual copy, suggests Move; PC middleman or cloud). Google
  Photos is cloud, not USB. Xiaomi specifics stated honestly:
  OTG off by default on many MIUI/HyperOS phones — Settings,
  Additional settings, OTG (on some phones: Settings, More
  settings, OTG), auto-off after about 10 minutes without a
  drive; Xiaomi's own support note says the switch was removed
  from MIUI 11 onward on some versions while newer model-specific
  pages still document a toggle, so a missing switch means OTG
  works directly, not a broken phone; stock file manager is Mi
  File Manager, stock gallery is Gallery; microSD slot varies by
  model — most Redmi and Redmi Note phones have one, POCO
  F-series and Xiaomi flagships often don't. Honest contrast
  stated in the TL;DR: Mi File Manager works but is manual
  select-copy-paste with no progress, no resume, no
  verification; Google Photos backup is cloud, not a USB copy;
  SnapDisk = enable OTG, plug in, tap Backup, whole library
  oldest-first with %, GB copied/total, speed, ETA, Pause/Stop,
  resume on re-plug, size-verified, copies never deletes. Query
  13 scored Nov 16, 2026 (searches.md): "back up photos from
  Xiaomi to USB flash drive without computer" — real how-to gap.
  Volume unknown (Play publishes no volume); difficulty
  low-medium. Linked from blog index (JSON-LD + post block),
  sitemap.xml, llms.txt, llms-full.txt (full section), feed.xml
  (now 14 items). JSON-LD validated by parse; sitemap.xml and
  feed.xml parse as XML. Title 47 chars, meta 144 chars. No app
  code or Play listing files touched; applicationId untouched
  (dev.dzianisv.snapdisk).
  Evidence: PR #49 (commit 18fc0dd, merge 52874ce, merged
  2026-11-17T00:36:38Z); CI run 43988633465 (build pass, 1m28s)
  green. Live fetch after Pages deploy: article 200, 23267
  bytes, title "Back up Xiaomi photos to a USB drive — SnapDisk"
  present, "November 16, 2026" present; blog index 200, 14415
  bytes; feed.xml 200 (14 items); sitemap.xml 200 with the new
  link.

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
  stays unverified. Paths: re-enter the password carefully, or sign
  in himself in the live browser. Never start a password reset or
  account recovery unless he explicitly asks for one in his own words
  (Nov 17 hard rule). Once in: verify the push, or upload the v0.1.2 AAB
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
  timeouts". Real-device check closed by the Nov 17 emulator-proof
  rule (WL-64): the emulator end-to-end pass is the proof; never
  his phone.

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
  sandbox Pro purchase (needs the Play Console app + product).

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
  sandbox Pro purchase (needs the Play Console app + product).

## Open

- Discovery done: competitor study + name check committed
  (`tool/growth/competitors.md` — discovery lead, Nov 14).
  Keyword scoring DONE Nov 14 (commit 07f7171, CI 43626786426
  green): 7 queries scored in `tool/growth/searches.md`, winners
  added to `tool/play-store/listing.md`. Re-score after launch.
- PRD + TDD + implementation spec + project doc DONE Nov 14
  (Notion, verified by re-fetch — links in the product file).
- Follow-ups: NTFS read-only detection (needs real-disk test —
  route: Firebase Test Lab free tier with a real device + NTFS
  disk, never the owner's phone; not attempted yet, no account
  set up).
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
