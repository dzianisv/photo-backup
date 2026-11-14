# SnapDisk — Decision Log

Every closed decision: decision, date, decider, reason in one line.

## 2026-11-14

- DECISION 1 — NAME (Product Lead, 2026-11-14): App name is
  **SnapDisk**. Store title: "SnapDisk – Photo Backup to USB".
  Reasoning: discovery lead verified on Play that "Photo Backup"
  collides with Buffalo Inc.'s live app of exactly that name
  (jp.buffalo.photobackup, 4.5★, updated Dec 2025); SnapDisk had no
  exact-name app on Play (nearest Snapdish, a food camera).
  ApplicationId: use **dev.dzianisv.snapdisk** — set it now, before
  the first release; it must never change after the first release
  ships. Display name + store title + README/landing copy say
  SnapDisk; repo name stays dzianisv/photo-backup (do not rename the
  repo mid-build). Decider: Product Lead (delegated by owner).
- DECISION 2 — PAYMENT (Product Lead, 2026-11-14): Free core +
  one-time Pro unlock **$9.99 USD**. Free: manual backup of photos +
  videos, oldest-first, no ads, no account, no cloud — the free edge
  stays whole. Pro (one-time purchase, no subscription): auto-backup
  when a disk is connected + unlimited backup size. Price reasoning
  from verified Play evidence: direct USB photo-backup competitor
  VaultDrive charges $19.99 once for Pro Lifetime; comparable
  utility one-time unlocks are €4.99 (Portafogli Pro, Play listing);
  $9.99 sits between the two and undercuts the direct competitor
  while staying a serious price. Wire Google Play Billing
  (billingclient) for the Pro SKU in the MVP and sandbox-verify a
  purchase end to end in the emulator before calling the build done.
  Decider: Product Lead (delegated by owner).
- Package `dev.dzianisv.snapdisk` — Product Lead, 2026-11-14
  (Decision 1); never changes once the first release ships.
- Working name "Photo Backup" retired — Product Lead, 2026-11-14
  (Decision 1).
- PostHog NOT installed in v0.1.0 — no project key exists; never
  hard-code one. Blocked on the owner's PostHog project.
- No capability claim in the UI without a test proving it —
  "USB-OTG ready" stays unclaimed until a real-disk test on the
  owner's Pixel 9a.
- Errors show the FULL error with Copy, never one truncated line —
  Convert264 WL-17 lesson, adopted as a build rule.
- CI mirrors Convert264's three-workflow pattern (PR / Release /
  Emulator Smoke), adapted: no share-intent audit (no share intents),
  foreground-service + storage-receiver audit instead.
- Free backup size cap is 5 GB per run — Product Lead, 2026-11-14.
  Decision 2 promises Pro "unlimited backup size", so free must have
  a number; 5 GB covers a typical phone's DCIM while heavy libraries
  hit Pro. Recorded here because the cap itself was not in the
  delegated decision text.
- Min SDK 24 (Android 7.0) — Product Lead, 2026-11-14. The spec
  targeted API 26; SAF + ForegroundService allow 24, and a lower
  floor beats Buffalo's Android 13+ requirement by more.
- "Photos" = images + videos — PRD A5, implemented in v0.1.0.
- Partial files are `<name>.part` next to the destination (PRD A3):
  pause keeps the `.part`, stop deletes it (TDD S1), a complete
  `.part` left by a crash is renamed, never re-copied. This replaces
  the earlier draft's resume-in-place (same filename) design.
- Skip-and-continue (TDD C5/E7) + size-verify retry-once (TDD C2)
  implemented in v0.1.0; FAT32 >4 GB pre-check reads /proc/mounts.
- Volume-UUID refusal on resume to a different disk (TDD X3)
  implemented in v0.1.0; when the UUID cannot be resolved the check
  is skipped (never blocks a backup on an unknown volume).
- NTFS read-only detection (spec §1, TDD D7) NOT implemented in
  v0.1.0 — needs a real-disk test to verify the detection; tracked
  as follow-up, not claimed.
