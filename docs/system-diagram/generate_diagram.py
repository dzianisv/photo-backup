#!/usr/bin/env python3
"""Generate the Photo Backup system diagram (layered, one row per layer).

Source of truth: ~/workspace/products/photo-backup.md + owner wireframes v1
(~/workspace/photo-backup/docs/wireframes-v1.png), 2026-11-14.
Hand-built SVG (stdlib only) -> rendered to PNG with headless Chromium at 2x.

Layout (top to bottom, ONE horizontal row per layer):
  1. User / triggers   2. UI   3. App services   4. Engine   5. Storage (cylinders)
  + "Watch out" flags box + legend/footer.
Columns: A x=120-620, B x=800-1300, C x=1480-1980 (centres 370/1050/1730).
Canvas: 2100 x 1200 (~2:1). Body text >= 19px at 1x, titles 21-34px.
"""
import os

W, H = 2100, 1200
OUT_DIR = os.path.dirname(os.path.abspath(__file__))
SVG_PATH = os.path.join(OUT_DIR, "photo-backup-system-diagram.svg")

FONT = "DejaVu Sans, Liberation Sans, sans-serif"

# Colours by role (consistent across versions)
C_TRIG = "#475569"   # slate  - user / triggers
C_UI = "#0F766E"     # teal    - UI
C_SVC = "#B45309"    # amber   - app services
C_ENG = "#6D28D9"    # violet  - engine
C_STO = "#1D4ED8"    # blue    - storage
C_LABEL = "#475569"
EDGE = "#334155"

# rows: (label, top, height)
ROWS = {
    1: ("1 · USER / TRIGGERS", 160, 118),
    2: ("2 · UI", 328, 118),
    3: ("3 · APP SERVICES", 496, 118),
    4: ("4 · ENGINE", 664, 118),
    5: ("5 · STORAGE", 832, 118),
}
COLS = {"A": (120, 620), "B": (800, 1300), "C": (1480, 1980)}


def esc(s):
    return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")


def node(x, y, w, h, fill, title, lines, title_size=21, body_size=19):
    """Rounded box with centred title + body lines."""
    cx = x + w / 2
    n = len(lines)
    # vertical centring: title + lines block
    block_h = 30 + n * 26
    ty = y + (h - block_h) / 2 + 24
    s = [f'<rect x="{x}" y="{y}" width="{w}" height="{h}" rx="16" '
         f'fill="{fill}" stroke="#0F172A" stroke-width="2"/>']
    s.append(f'<text x="{cx}" y="{ty:.0f}" font-family="{FONT}" font-size="{title_size}" '
             f'font-weight="bold" fill="#FFFFFF" text-anchor="middle">{esc(title)}</text>')
    for i, ln in enumerate(lines):
        s.append(f'<text x="{cx}" y="{ty + 28 + i * 26:.0f}" font-family="{FONT}" '
                 f'font-size="{body_size}" fill="#FFFFFF" text-anchor="middle">{esc(ln)}</text>')
    return "\n".join(s)


def cylinder(x, y, w, h, fill, title, lines):
    """Storage cylinder: body rect + top ellipse."""
    cx = x + w / 2
    ry = 14
    s = [f'<path d="M {x} {y + ry} L {x} {y + h - ry} '
         f'A {w / 2:.0f} {ry} 0 0 0 {x + w} {y + h - ry} L {x + w} {y + ry}" '
         f'fill="{fill}" stroke="#0F172A" stroke-width="2"/>',
         f'<ellipse cx="{cx}" cy="{y + ry}" rx="{w / 2}" ry="{ry}" '
         f'fill="#FFFFFF" fill-opacity="0.25" stroke="#0F172A" stroke-width="2"/>']
    block_h = 30 + len(lines) * 26
    ty = y + ry + (h - ry - block_h) / 2 + 24
    s.append(f'<text x="{cx}" y="{ty:.0f}" font-family="{FONT}" font-size="21" '
             f'font-weight="bold" fill="#FFFFFF" text-anchor="middle">{esc(title)}</text>')
    for i, ln in enumerate(lines):
        s.append(f'<text x="{cx}" y="{ty + 28 + i * 26:.0f}" font-family="{FONT}" '
                 f'font-size="19" fill="#FFFFFF" text-anchor="middle">{esc(ln)}</text>')
    return "\n".join(s)


def edge_label(x, y, text, anchor="middle"):
    return (f'<text x="{x}" y="{y}" font-family="{FONT}" font-size="16" '
            f'fill="{EDGE}" text-anchor="{anchor}">{esc(text)}</text>')


parts = []
parts.append(f'<svg xmlns="http://www.w3.org/2000/svg" width="{W}" height="{H}" '
             f'viewBox="0 0 {W} {H}">')
parts.append('<defs><marker id="arr" viewBox="0 0 10 10" refX="9" refY="5" '
             'markerWidth="9" markerHeight="9" orient="auto-start-reverse">'
             f'<path d="M 0 0 L 10 5 L 0 10 z" fill="{EDGE}"/></marker></defs>')
parts.append(f'<rect x="0" y="0" width="{W}" height="{H}" fill="#FFFFFF"/>')

# ---- title / subtitle ----
parts.append(f'<text x="1050" y="54" font-family="{FONT}" font-size="34" font-weight="bold" '
             f'fill="#0F172A" text-anchor="middle">Photo Backup — System Diagram</text>')
parts.append(f'<text x="1050" y="94" font-family="{FONT}" font-size="20" fill="{C_LABEL}" '
             f'text-anchor="middle">dev.dzianisv.photobackup · Kotlin + Jetpack Compose · '
             f'on-device, no cloud, no account · 2026-11-14</text>')

# ---- row labels ----
for _, (label, top, _) in ROWS.items():
    parts.append(f'<text x="120" y="{top - 12}" font-family="{FONT}" font-size="20" '
                 f'font-weight="bold" fill="{C_LABEL}">{esc(label)}</text>')

# ---- edges (drawn first, under nodes) ----
E = []
# E1 user opens app -> settings (A col, vertical)
E.append('<line x1="370" y1="278" x2="370" y2="328" stroke="#334155" stroke-width="2.5" marker-end="url(#arr)"/>')
# E2 notification tap -> notification (C col, vertical)
E.append('<line x1="1730" y1="278" x2="1730" y2="328" stroke="#334155" stroke-width="2.5" marker-end="url(#arr)"/>')
# E3 USB disk connected -> storage detection (B col, vertical)
E.append('<line x1="1050" y1="278" x2="1050" y2="496" stroke="#334155" stroke-width="2.5" marker-end="url(#arr)"/>')
# E4 storage detection -> notification, up then right (label: shows offer)
E.append('<path d="M 1150 496 V 470 H 1600 V 446" fill="none" stroke="#334155" '
         'stroke-width="2.5" marker-end="url(#arr)"/>')
E.append(edge_label(1375, 460, "shows offer"))
# E5 notification -> foreground service (C col, vertical)
E.append('<line x1="1730" y1="446" x2="1730" y2="496" stroke="#334155" stroke-width="2.5" marker-end="url(#arr)"/>')
# E6 settings -> app prefs via left gutter (reads / writes)
E.append('<path d="M 120 387 H 70 V 891 H 112" fill="none" stroke="#334155" '
         'stroke-width="2.5" marker-end="url(#arr)"/>')
E.append('<text x="54" y="640" font-family="DejaVu Sans, Liberation Sans, sans-serif" '
         'font-size="16" fill="#334155" text-anchor="middle" '
         'transform="rotate(-90 54 640)">reads / writes</text>')
# E7 foreground service -> copy engine (elbow down-left in R3/R4 gap)
E.append('<path d="M 1730 614 V 639 H 1050 V 656" fill="none" stroke="#334155" '
         'stroke-width="2.5" marker-end="url(#arr)"/>')
E.append(edge_label(1390, 630, "starts copy"))
# E8 mediaStore scanner -> copy engine (horizontal, row 4)
E.append('<line x1="1480" y1="723" x2="1308" y2="723" stroke="#334155" stroke-width="2.5" marker-end="url(#arr)"/>')
E.append(edge_label(1394, 712, "photo list"))
# E9 copy engine -> error capture (horizontal, row 4)
E.append('<line x1="800" y1="723" x2="628" y2="723" stroke="#334155" stroke-width="2.5" marker-end="url(#arr)"/>')
E.append(edge_label(714, 712, "on error"))
# E10 mediaStore scanner -> internal DCIM (C col, vertical)
E.append('<line x1="1730" y1="782" x2="1730" y2="832" stroke="#334155" stroke-width="2.5" marker-end="url(#arr)"/>')
E.append(edge_label(1744, 814, "reads", anchor="start"))
# E11 copy engine -> external disk (B col, vertical)
E.append('<line x1="1050" y1="782" x2="1050" y2="832" stroke="#334155" stroke-width="2.5" marker-end="url(#arr)"/>')
E.append(edge_label(1064, 814, "writes", anchor="start"))
parts.extend(E)

# ---- nodes ----
# Row 1: triggers
parts.append(node(120, 160, 500, 118, C_TRIG, "User opens app", ["opens the Settings screen"]))
parts.append(node(800, 160, 500, 118, C_TRIG, "USB disk connected", ["OTG / USB storage attached"]))
parts.append(node(1480, 160, 500, 118, C_TRIG, "Notification tap · Backup", ["user taps the Backup action"]))
# Row 2: UI
parts.append(node(120, 328, 500, 118, C_UI, "Settings screen",
                  ["From /sdcard/DCIM · To /disk/DCIM", "folder pickers via SAF document-tree"]))
parts.append(node(1480, 328, 500, 118, C_UI, "Notification",
                  ["offer · progress % · GB · MB/s · ETA", "Backup / Cancel · Pause / Stop"]))
# Row 3: app services
parts.append(node(120, 496, 500, 118, C_SVC, "WorkManager",
                  ["retry / scheduling", "re-runs a failed backup"]))
parts.append(node(800, 496, 500, 118, C_SVC, "Storage detection",
                  ["StorageManager · USB", "ACTION_MEDIA_MOUNTED receiver"]))
parts.append(node(1480, 496, 500, 118, C_SVC, "ForegroundService",
                  ["backup service", "runs copy · posts progress"]))
# Row 4: engine
parts.append(node(120, 664, 500, 118, C_ENG, "Error capture",
                  ["full detail, never truncated", "surfaced in UI + logs"]))
parts.append(node(800, 664, 500, 118, C_ENG, "Copy engine",
                  ["copy · pause · stop · resume", "skip-if-exists · verify size"]))
parts.append(node(1480, 664, 500, 118, C_ENG, "MediaStore scanner",
                  ["DATE_TAKEN ascending", "oldest photos first"]))
# Row 5: storage (cylinders)
parts.append(cylinder(120, 832, 500, 118, C_STO, "App prefs",
                      ["From/To URIs", "last backup state"]))
parts.append(cylinder(800, 832, 500, 118, C_STO, "External disk",
                      ["/disk/DCIM", "backup destination"]))
parts.append(cylinder(1480, 832, 500, 118, C_STO, "Internal DCIM",
                      ["/sdcard/DCIM", "source photos"]))

# ---- "Watch out" flags box ----
parts.append('<rect x="120" y="988" width="1860" height="130" rx="16" fill="#FEF3C7" '
             'stroke="#DC2626" stroke-width="3"/>')
parts.append(f'<text x="150" y="1024" font-family="{FONT}" font-size="21" font-weight="bold" '
             f'fill="#991B1B">⚠ WATCH OUT</text>')
watch = [
    "Play Console account · upload keystore · Sentry DSN · PostHog — all owner-gated",
    "Real-disk OTG test on Pixel 9a unproven — “USB-OTG ready” stays unclaimed",
    "Name + payment model undecided — owner’s call",
]
for i, ln in enumerate(watch):
    parts.append(f'<text x="150" y="{1056 + i * 26}" font-family="{FONT}" font-size="19" '
                 f'fill="#451A03">{esc(ln)}</text>')

# ---- legend / footer ----
parts.append(f'<text x="1050" y="1154" font-family="{FONT}" font-size="18" fill="{C_LABEL}" '
             f'text-anchor="middle">Rounded box = process / screen · Cylinder = stored data · '
             f'Arrow = calls / uses / reads / writes</text>')
parts.append(f'<text x="1050" y="1184" font-family="{FONT}" font-size="18" fill="{C_LABEL}" '
             f'text-anchor="middle">Slate = triggers · Teal = UI · Amber = app services · '
             f'Violet = engine · Blue = storage — Source: owner wireframes v1 + '
             f'products/photo-backup.md · Repo: dzianisv/photo-backup (to create)</text>')

parts.append('</svg>')

with open(SVG_PATH, "w") as f:
    f.write("\n".join(parts))
print(f"wrote {SVG_PATH} ({W}x{H})")
