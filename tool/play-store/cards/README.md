# SnapDisk — Play Store ASO screenshot cards (WL-199)

Captioned Play phone cards cut from the 5 real screenshots in
`../screenshots/` (smoke-test runs, never mockups). All cards
1080x2400 (the source screenshots' native device size), navy
#0F172A frame, DejaVu Bold headline (white, one emphasis word
cyan #22D3EE — the landing-page navy/cyan accent), thin cyan
rule, screenshot below at 0.91 scale with rounded corners.
Built by `../generate_cards.py` — re-run it to regenerate:
`python3 tool/play-store/generate_cards.py`.

## The cards (in listing order)

| Slot | Card | Source screenshot | Caption (verbatim from `../screenshots/README.md`) |
|---|---|---|---|
| 1 | `01-card-02-offer.png` | `02-offer.png` | "Plug in a disk, tap Backup" |
| 2 | `02-card-03-progress.png` | `03-progress.png` | "Real progress, Pause and Stop" |
| 3 | `03-card-04-done.png` | `04-done.png` | "Proof it copied — counts, not promises" |
| 4 | `04-card-01-settings.png` | `01-settings-defaults.png` | "Pick your folders once, then one tap" |
| 5 | `05-card-05-pro.png` | `05-pro.png` | "Pro: auto-backup, unlimited size" |

Order follows the growth-aso first-3 rule. Slot 1 is the biggest
promise — plug in a disk, one tap, backup starts (the core loop
from `listing.md`: "Plug in a USB disk. SnapDisk offers a backup
in a notification — tap Backup and walk away"). Slot 2 is the
differentiator — live measured progress with Pause/Stop, the thing
no competitor shows on its listing. Slot 3 is the next strongest
feature — proof with real counts ("Backed up 3 photos (585.9
KB)"). Slot 4 is the one-time setup; slot 5 is the Pro closer.

## Numbers

No number on any card was invented. The captions contain no
numbers. Every number visible on a card is a number already
visible in its source screenshot: card 2 shows the live copy's
measured "7%, 872.0 KB/12.0 MB, 168.6 KB/s, ETA 1m"; card 3
shows the real "3 photos (585.9 KB)"; card 5 shows the "$9.99
once" printed in the app's own Pro UI. No download counts, no
ratings, no review counts anywhere.

## Crops

One uniform crop for all five shots: y 0–1900 of the 1080x2400
source — drops only the empty space below the content and the
Android nav bar. The notification-shade shots (cards 1–3) keep
their "Silent" Android System block below the SnapDisk
notification: that block is emulator noise, not the app (see
`../screenshots/README.md`), and cropping it out would have left
a short, wide shot floating in a tall navy void. The shade shots
are interim until Pixel 9a + real-disk screenshots exist.

## Caveats

- **Card 5 (Pro) is not a live-billing proof.** Its source
  `05-pro.png` was rendered with injected billing state
  (`proAvailable=true`); the emulator has no Play Store, so the
  live billing flow could not produce it. The card shows the
  app's real Pro UI and the $9.99 price printed in that UI. It
  must be replaced with a shot through the live billing flow
  once the Play listing + `pro_unlock` product exist
  (owner-gated).
- **Screen-off continuation under battery saver is UNVERIFIED**
  and is claimed nowhere on these cards.
- **No brand.md exists in this repo** (growth-design expects
  `growth/brand.md`). Brand alignment below was judged against
  the shipped assets instead: `icon-512.png` and
  `feature-graphic-1024x500.png` (navy #0F172A, white) and the
  landing page's navy/cyan palette. Creating `growth/brand.md`
  is follow-up work, not part of WL-199.

## Critic verdict: PASS — 2026-11-21

Run per growth-design before shipping; all five cards were
viewed at full size and at 160 px thumbnail width.

- **Check 1 — KPI alignment: PASS.** The KPI is 1M Play
  downloads (Stage 0, pre-launch). Slot 1 carries the one-tap
  promise that makes a user install; slots 2–3 show the core
  loop and the proof that closes the install decision. Nothing
  on the cards serves a different KPI.
- **Check 2 — product description alignment: PASS.** Every
  screenshot is a real shot from a green smoke run; every
  caption is verbatim from `screenshots/README.md`; every
  feature shown exists in the app and in `listing.md`
  (offer notification, live progress + Pause/Stop, done
  counts, folder pickers, Pro $9.99 one-time). No feature is
  shown that does not exist; no unproven capability is
  claimed. The two honest limits are stated above (card 5
  injected billing state; screen-off continuation unverified
  and unclaimed).
- **Check 3 — brand / style alignment: PASS.** Navy #0F172A
  frame matches the icon and feature graphic exactly; same
  DejaVu type family as `generate_creatives.py`; one idea per
  card, one emphasis word per headline, no tiny text (headlines
  88 px, readable at 160 px thumbnail width). The cyan accent
  comes from the landing page's navy/cyan palette — see the
  brand.md note above.

## Build note (method deviation, recorded honestly)

growth-design / app-store-screenshots prescribe the Next.js
screenshot editor. These cards were built with the PIL script
`../generate_cards.py` instead: this subagent has no live
browser to run the editor in, and the task prescribed the brand
frame consistent with `generate_creatives.py` — the repo's
existing PIL creatives generator. Same pattern (deterministic
script, source in the repo, regenerable), same navy, same type
family. If a later run re-cuts the cards in the editor, the
captions, order, crops and critic verdict above are the spec
to match.
