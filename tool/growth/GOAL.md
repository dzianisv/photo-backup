# SnapDisk — goal

Owner: Dzianis Vashchuk. Goal: publish on Google Play and grow to
**1M downloads**, top photo backup app.

Acceptance: good Google Play reviews (target 4.5+, answer every 1–3★
review in 24–48h), zero open GitHub issues, all Sentry repeats fixed
fundamentally, zero open bug reports.

## Name + payment — DECIDED 2026-11-14 (Product Lead, full delegation)

- App name: **SnapDisk**. Store title: "SnapDisk – Photo Backup to
  USB". ApplicationId: `dev.dzianisv.snapdisk`. Repo stays
  `dzianisv/photo-backup`.
- Payment: free core + one-time Pro unlock **$9.99 USD**
  (`pro_unlock`). Free: manual backup, 5 GB per run, no ads, no
  account, no cloud. Pro: auto-backup on connect + unlimited size.

## Baseline (2026-11-14, day 1, v0.1.0)

- Downloads: unknown (not on Play — Stage 0, pre-launch)
- Rating: unknown (not on Play)
- Open GitHub issues: 0
- Sentry: not installed (DSN blocked on owner)
- PostHog: not installed (project blocked on owner)
- Billing: wired in the app; sandbox purchase NOT verified (needs
  the Play Console app + `pro_unlock` product — owner-gated)

## Maturity stage

Stage 0 — pre-launch. Next: Play project (owner: $25 developer
account — the one remaining gate), internal track, real-device proof
(USB disk on his Pixel 9a).

## First 30 days (from the framework)

- Week 1 — foundation: issues enabled, Sentry in the app, Play
  project created, landing page live, store listing drafted, icon and
  screenshots shipped. Payment wired (sandbox purchase verified) and
  PostHog installed — payment wiring is done, sandbox verification +
  PostHog blocked on the owner gates above.
- Week 2 — publish: internal track, real-phone test, closed track,
  production staged rollout. Baseline measured.
- Week 3 — first users: two or three channels live, five user
  conversations. First paying customer.
- Week 4 — first verdict: scoreboard reviewed, weakest channel killed.
