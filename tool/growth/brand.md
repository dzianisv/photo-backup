# SnapDisk — Brand guide (WL-200)

Created 2026-11-21. Compiled ONLY from verified shipped assets in this
repo — every fact below traces to a file read this run. Nothing here
is invented: no download counts, no ratings, no review counts, no
hexes from memory. Where the shipped assets disagree with each other,
the disagreement is recorded, not smoothed over.

Path note: growth-design expects `growth/brand.md`; this repo's growth
folder is `tool/growth/`, so this file lives at
`tool/growth/brand.md`. Same role, repo path.

## Identity

- **App name:** SnapDisk (decided 2026-11-14, Product Lead under owner
  delegation — see `tool/growth/DECISIONS.md`, Decision 1).
- **Store title:** "SnapDisk – Photo Backup to USB" (30 chars —
  `tool/play-store/listing.md`).
- **ApplicationId:** `dev.dzianisv.snapdisk` — set before the first
  release; it must never change (`app/build.gradle.kts:11`,
  DECISIONS.md Decision 1). Display name in the app is `SnapDisk`
  (`app/src/main/res/values/strings.xml`, `app_name`).
- **Repo:** dzianisv/photo-backup. Current release: v0.1.2
  (versionCode 3). Stage 0 — not on Google Play.
- **Short description (57 chars, listing.md):** "Back up photos &
  videos to a USB disk. One tap, no cloud."
- **Tagline (brand line, 7 words — on the feature graphic):**
  "Photo backup to USB. One tap."
- **Tagline variants in the wild (drift, see below):** the landing
  page H1 reads "Back up photos to USB. One tap. No cloud." and its
  tagline line reads "Plug in a USB disk. Tap once. Done."
  (`docs/index.html`). The feature-graphic line is the canonical
  brand tagline; the landing variants predate this file and are
  flagged for reconciliation, not silently adopted.

## The mark

One idea: a white download arrow into a tray, on navy. No text in
the mark.

- Source: `app/src/main/res/drawable/ic_launcher_foreground.xml`
  (vector, white `#FFFFFF` path — arrow shaft + head + tray) and the
  same proportions redrawn in
  `tool/play-store/generate_creatives.py` (`arrow()`).
- **App icon:** `tool/play-store/icon-512.png`, 512x512, RGB, no
  transparency, no rounded corners (Play masks it). Verified this
  run: 512x512, corner pixel (15, 23, 42), white arrow at centre.
  Adaptive icon: background `@color/ic_launcher_background`
  `#0F172A`, foreground the vector above
  (`app/src/main/res/mipmap-anydpi-v26/ic_launcher.xml`).
- **Feature graphic:** `tool/play-store/feature-graphic-1024x500.png`,
  1024x500, RGB. Navy field, white arrow at left, "SnapDisk" in
  white DejaVu Bold 110, tagline "Photo backup to USB. One tap."
  in muted grey DejaVu 36.
- **Landing copy:** the same icon file serves the landing page
  (`docs/icon-512.png`, byte-identical to `tool/play-store/icon-512.png`,
  md5 `91f311265ab5921717b07f3e6ddc62a9`).
- Gap closed (WL-201, 2026-11-21): the standalone SVG and the
  192/128/48/16 px PNG set now exist. `tool/play-store/icon.svg`
  (630 B) is the launcher vector path data copied verbatim
  (`ic_launcher_foreground.xml`, viewport 108x108) on a navy
  `#0F172A` field — it renders the same mark as `icon-512.png`
  at 512 (same white arrow-into-tray on navy, same 300 px mark
  width, navy corner (15, 23, 42)) but is NOT pixel-identical to
  it: 9.4% of pixels differ, because `generate_creatives.py`
  `arrow()` redraws the mark with a wider, shorter head than the
  vector (head tip at 53% of height in the PNG vs 64% in the
  vector; tray 13 px lower). The SVG follows the canonical
  vector; the PNG redraw's proportions are the divergence, not
  the SVG — so the "same proportions redrawn" line above is
  imprecise and stands corrected by this measurement.
  `tool/play-store/icons/icon-{192,128,48,16}.png` are LANCZOS
  downscales of `icon-512.png` (RGB, navy corners; the 192
  white bbox is within 1 px of the scaled source). Honest
  limit: at 16 px the arrow/tray detail blurs into one white
  glyph on navy (6 pure-white pixels) — readable as the mark,
  not crisp; a hand-tuned 16 px redraw is follow-up work, not
  a blocker for the Play listing.

## Palette — exact hexes, verified from the generator scripts

Hexes below are from `tool/play-store/generate_creatives.py` and
`tool/play-store/generate_cards.py` (RGB tuples read this run),
cross-checked against the pixels of the shipped PNGs and against
`docs/index.html`.

| Colour | Hex | RGB | Where it is used |
|---|---|---|---|
| Navy | `#0F172A` | (15, 23, 42) | Primary background everywhere: icon, feature graphic, card frames, landing body, launcher background (`#0F172A` in `app/src/main/res/values/ic_launcher_background.xml`) |
| White | `#FFFFFF` | (255, 255, 255) | The mark, headlines, body text |
| Cyan | `#22D3EE` | (34, 211, 238) | The one accent: one emphasis word per card headline, the thin cyan rule under each headline, landing links and buttons |
| Muted grey | `#94A3B8` | (148, 163, 184) | Secondary text: feature-graphic tagline, landing tagline |
| Navy 2 | `#1E293B` | (30, 41, 59) | Landing-page card/step surfaces only (`docs/index.html` `--navy2`) |

The brand's two core colours are navy + cyan (growth-design's
two-colour rule). White is the mark/text colour, muted grey is
secondary text, navy 2 is a landing surface tint.

**Purple drift, recorded honestly:** the landing page also uses
purple `#8B5CF6` (`docs/index.html` `--purple`), in a cyan→purple
gradient on the H1 span and the buttons. Purple appears NOWHERE in
the icon, the feature graphic, or any of the five cards — those are
navy + cyan + white only. The gradient is a landing-page accent,
not a brand colour. Do not put purple on a store card. Reconciling
the landing gradient with the navy/cyan brand is follow-up work.

## Typography

- **Store creatives (icon, feature graphic, cards):** DejaVu Sans
  Bold for headlines, DejaVu Sans for regular text
  (`/usr/share/fonts/truetype/dejavu/DejaVuSans-Bold.ttf` and
  `DejaVuSans.ttf` in both generator scripts). Card headlines are
  88 px; feature-graphic title 110 px, tagline 36 px.
- **Landing page:** system stack —
  `-apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica,
  Arial, sans-serif` (`docs/index.html:101`). No DejaVu on the
  landing page; the landing's type is the browser default stack.
- **In-app UI:** standard Material 3 (system typeface). The app's
  in-app typeface is not separately specified anywhere in the repo
  and is not claimed here.

## Voice / tone (from `tool/play-store/listing.md`)

Plain, direct, honest. Short sentences. Proof by numbers, not
adjectives: "Proof it copied — counts, not promises", "Honest
progress", "trust the counts". The promise is always the same
shape: plug in, tap once, walk away; no account, no cloud, no
upload — everything stays on your devices. Errors show the full
error with Copy, never one truncated line. Free is stated with its
limit (manual backup, 5 GB per backup run); Pro is stated with its
price and its two features (auto-backup when a disk is connected +
unlimited size, $9.99 one-time, no subscription). Nothing is
claimed that a test has not proven.

## Play Store screenshot cards (WL-199)

Five cards in `tool/play-store/cards/`, 1080x2400 each (the source
screenshots' native device size), navy `#0F172A` frame, DejaVu Bold
headline (white, one emphasis word cyan `#22D3EE`), thin cyan rule,
source screenshot below at 0.91 scale with rounded corners. Built
by `tool/play-store/generate_cards.py` — re-run it to regenerate.
Critic verdict: **PASS, 2026-11-21** (recorded in
`tool/play-store/cards/README.md`).

### The 5 captions, in listing order (verbatim)

| Slot | Card | Source screenshot | Caption |
|---|---|---|---|
| 1 | `01-card-02-offer.png` | `02-offer.png` | "Plug in a disk, tap Backup" |
| 2 | `02-card-03-progress.png` | `03-progress.png` | "Real progress, Pause and Stop" |
| 3 | `03-card-04-done.png` | `04-done.png` | "Proof it copied — counts, not promises" |
| 4 | `04-card-01-settings.png` | `01-settings-defaults.png` | "Pick your folders once, then one tap" |
| 5 | `05-card-05-pro.png` | `05-pro.png` | "Pro: auto-backup, unlimited size" |

### The first-3 rule (growth-aso)

Slot 1 is the biggest promise — plug in a disk, one tap, backup
starts (the core loop from `listing.md`: "Plug in a USB disk.
SnapDisk offers a backup in a notification — tap Backup and walk
away"). Slot 2 is the differentiator — live measured progress
with Pause/Stop, the thing no competitor shows on its listing.
Slot 3 is the next strongest feature — proof with real counts
("Backed up 3 photos (585.9 KB)"). Slot 4 is the one-time setup;
slot 5 is the Pro closer. The first three appear in search; most
users never scroll past them.

## Screenshot rules

- **Real screenshots only, never mockups.** Every screenshot in
  `tool/play-store/screenshots/` comes from a green emulator smoke
  run, never a design comp (`tool/play-store/screenshots/README.md`).
- Sources: `01-settings-defaults.png` from smoke run 43624496788
  (green, 2026-11-14); `02-offer.png`, `03-progress.png`,
  `04-done.png`, `05-pro.png` from smoke run 43644126583 (green,
  14/14 tests, 2026-11-14). Evidence artifacts:
  `tool/smoke-screenshots/<run-id>/screenshots/` in each run's
  `smoke-evidence` artifact.
- **One idea per card, one emphasis word per headline.** No tiny
  text: headlines are 88 px and were read at 160 px thumbnail
  width in WL-199. No numbers invented: the captions contain no
  numbers; every number visible on a card is already visible in
  its source screenshot (card 2: the live copy's "7%, 872.0 KB/12.0
  MB, 168.6 KB/s, ETA 1m"; card 3: the real "3 photos (585.9 KB)";
  card 5: the "$9.99 once" printed in the app's own Pro UI).
- **One uniform crop** for all five shots: y 0–1900 of the
  1080x2400 source — drops only the empty space below the content
  and the Android nav bar. Never crop to hide a real UI element.

## Payment (decided, not invented)

Free core + one-time Pro unlock **$9.99 USD** (DECISIONS.md,
Decision 2, 2026-11-14). Free: manual backup of photos + videos,
oldest-first, 5 GB per backup run, no ads, no account, no cloud.
Pro (`pro_unlock`, one-time, no subscription): auto-backup when a
disk is connected + unlimited backup size. Price reasoning from
verified Play evidence: direct USB photo-backup competitor
VaultDrive charges $19.99 once for Pro Lifetime; comparable
utility one-time unlocks are €4.99 (Portafogli Pro, Play listing);
$9.99 sits between the two and undercuts the direct competitor.
Sandbox purchase NOT yet verified — needs the Play Console app +
the product to exist (owner-gated).

## Honest limits

- **Card 5 is NOT a live-billing proof.** Its source `05-pro.png`
  was rendered with injected billing state (`proAvailable=true`);
  the emulator has no Play Store, so the live billing flow could
  not produce it. The card shows the app's real Pro UI and the
  $9.99 price printed in that UI. Replace it with a shot through
  the live billing flow once the Play listing + `pro_unlock`
  product exist (owner-gated).
- **Shade shots keep the emulator "Silent" system block.** Cards
  1–3 keep the Android System silent notifications below the
  SnapDisk notification ("Serial console enabled", "AT Translated
  Set 2 keyboard configured") — emulator noise, not the app.
  Cropping it out would have left a short, wide shot floating in
  a tall navy void. Interim until Pixel 9a + real-disk shots exist
  (owner-gated: real-device proof on his Pixel 9a).
- **Screen-off continuation under battery saver is UNVERIFIED**
  and is claimed nowhere — not on any card, not in this file, not
  in the listing.
- **No download counts, ratings, or review counts anywhere.**
  None exist — Stage 0, not on Play. Nothing on any card, in the
  listing, or in this file states or implies one.
- **No promo video yet** (30–60 s of the core loop, after the
  first real-disk test — `listing.md`). No Chrome Web Store tiles
  and no Product Hunt cards exist yet. This brand file covers the
  Play set + landing page only; the other stores' cards are
  follow-up work and must use this same mark, palette, tagline,
  and style when they are built.
- **Style adjective (lead's pick, from the shipped copy):**
  "direct". Short sentences, no decoration, proof by numbers.
  Every card, caption, and video frame follows it.
- **Drift to reconcile (not hidden):** the landing page's
  cyan→purple gradient (`#8B5CF6`, H1 span + buttons) and its two
  tagline variants ("Back up photos to USB. One tap. No cloud.",
  "Plug in a USB disk. Tap once. Done.") predate this file. The
  store cards are navy + cyan only and carry the canonical
  tagline "Photo backup to USB. One tap." Reconciliation is
  follow-up work; until then the cards are the brand reference.

## Critic verdict: PASS — 2026-11-21

Run per growth-design against the shipped assets (this file IS the
brand record; the check below is the critic applied to the brand
as recorded, with the WL-199 card verdict carried forward).

- **Check 1 — KPI alignment: PASS.** The KPI is 1M Play downloads
  (Stage 0, pre-launch). The brand carries the one-tap promise
  ("Photo backup to USB. One tap.") that makes a user install;
  the cards' slot 1 is that promise, slots 2–3 show the core loop
  and the proof that closes the install decision. Nothing in the
  brand serves a different KPI.
- **Check 2 — product description alignment: PASS.** Every element
  in this file traces to a shipped asset read this run: name,
  title, applicationId, price, palette, type, captions, and
  screenshot sources all match `listing.md`, `DECISIONS.md`, the
  generator scripts, the PNGs, and `docs/index.html`. No feature
  is shown that does not exist; no unproven capability is claimed.
  The honest limits above (card 5 injected billing state,
  emulator Silent block, screen-off unverified, no counts) are
  stated in the file itself.
- **Check 3 — brand / style alignment: PASS, with the drift noted.**
  One mark (white arrow on navy), two core colours (navy `#0F172A`
  + cyan `#22D3EE`), one tagline, one style ("direct") — identical
  across the icon, feature graphic, all five cards, and the
  listing copy. The two drifts (landing purple gradient, landing
  tagline variants) are recorded in this file rather than hidden,
  and the cards — the shipped store assets — are the reference
  until they are reconciled. The WL-199 card critic verdict
  (PASS, 2026-11-21) stands; this file closes the "No brand.md
  exists" gap that verdict flagged.

## Sources (all read this run, 2026-11-21)

- `tool/play-store/listing.md` — name, title, descriptions, price,
  in-app product, creatives list.
- `tool/play-store/generate_creatives.py` — navy (15, 23, 42),
  white, muted (148, 163, 184), DejaVu Bold/Regular, icon +
  feature-graphic layout.
- `tool/play-store/generate_cards.py` — navy, white, cyan
  (34, 211, 238), DejaVu Bold 88 px, card layout, captions,
  first-3 order, crops.
- `tool/play-store/icon-512.png` + `feature-graphic-1024x500.png`
  — read back as pixels (corner (15, 23, 42); 512x512 / 1024x500).
- `tool/play-store/cards/README.md` — the 5 captions verbatim,
  first-3 rule, numbers, crops, caveats, critic PASS 2026-11-21.
- `tool/play-store/screenshots/README.md` — real screenshot
  sources, smoke runs, how each shot was produced, the injected
  billing state of `05-pro.png`.
- `docs/index.html` — landing palette (`--navy:#0f172a`,
  `--navy2:#1e293b`, `--cyan:#22d3ee`, `--purple:#8b5cf6`,
  `--grey:#94a3b8`), system font stack, H1/tagline variants,
  "Not yet — the Play listing is being prepared".
- `tool/growth/DECISIONS.md` — Decision 1 (name, title,
  applicationId) and Decision 2 (payment $9.99, reasoning).
- `app/build.gradle.kts` — applicationId `dev.dzianisv.snapdisk`,
  versionName 0.1.2, versionCode 3.
- `app/src/main/res/values/strings.xml` — display name SnapDisk.
- `app/src/main/res/drawable/ic_launcher_foreground.xml` +
  `app/src/main/res/mipmap-anydpi-v26/ic_launcher.xml` +
  `app/src/main/res/values/ic_launcher_background.xml` — the mark
  and the adaptive-icon background `#0F172A`.
