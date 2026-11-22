#!/usr/bin/env python3
"""Generate SnapDisk Play Store ASO screenshot cards (WL-199).

Captioned 1080x2400 cards cut from the 5 real screenshots in
tool/play-store/screenshots/ — no mockups, no invented numbers.
Every number visible on a card is a number already visible in its
source screenshot (the captions themselves contain no numbers).

Layout: navy #0F172A frame (same as icon-512.png /
feature-graphic-1024x500.png, built by generate_creatives.py),
caption headline in DejaVu Bold (white, one emphasis word cyan
#22D3EE — the landing-page navy/cyan accent), thin cyan rule,
then the source screenshot below, cropped only to drop dead
space (see CROPS) and scaled down, never up, with rounded corners.

Card size 1080x2400 = the source screenshots' native device size
(growth-design allows 1080x1920 or the device's native size).
Cropping the full 2400-tall shot into a 1920 card would cut real
UI, so the card keeps the native height.

Order (growth-aso first-3 rule: slot 1 biggest promise, slot 2
core interaction / differentiator, slot 3 next strongest feature):
  1. 02-offer    — the one-tap promise: plug in, tap Backup.
  2. 03-progress — the differentiator: live measured progress
                    with Pause/Stop, no competitor shows this.
  3. 04-done     — the proof: real counts, not a spinner.
  4. 01-settings — setup once, then one tap (supporting slot).
  5. 05-pro      — closer: Pro auto-backup + unlimited size.
Captions are verbatim from tool/play-store/screenshots/README.md.

CROPS (one uniform crop for all five shots, verified by viewing
the cards after generation):
  - every shot: y 0-1900 — drops only the empty space below the
    content and the Android nav bar. All content is kept,
    including the notification-shade shots' "Silent" Android
    System block below the SnapDisk notification: that block is
    emulator noise, not the app (see screenshots/README.md), and
    cropping it out would have left a short, wide shot floating
    in a tall navy void. Uniform 1080x1900 crops keep every card
    the same shape and the notification text at 0.91 scale. The
    shade shots are interim until Pixel 9a + real-disk shots
    exist; the cards README says so.

05-pro caveat: its source was rendered with injected billing state
(proAvailable=true; see screenshots/README.md) — the card shows
the app's real Pro UI and the $9.99 price that is printed in that
UI, but it is NOT a live-billing proof. Replace with a shot
through the live billing flow once the Play listing + pro_unlock
product exist (owner-gated).

Regenerate any time with: python3 tool/play-store/generate_cards.py
Outputs: tool/play-store/cards/01-card-02-offer.png … 05-card-05-pro.png
"""
import os

from PIL import Image, ImageDraw, ImageFont

HERE = os.path.dirname(os.path.abspath(__file__))
SHOTS = os.path.join(HERE, "screenshots")
OUT = os.path.join(HERE, "cards")

NAVY = (15, 23, 42)
WHITE = (255, 255, 255)
CYAN = (34, 211, 238)  # #22D3EE, landing navy/cyan accent

BOLD = "/usr/share/fonts/truetype/dejavu/DejaVuSans-Bold.ttf"
FONT_SIZE = 88
LINE_HEIGHT = 106
MARGIN_X = 48
CARD_W, CARD_H = 1080, 2400
SHOT_TOP = 600          # screenshot area top
SHOT_BOTTOM = 2330      # screenshot area bottom
RADIUS = 40

# slot, source shot, caption lines (verbatim caption, line-broken),
# emphasis word (one per card, cyan), crop box (x0, y0, x1, y1).
CARDS = [
    ("01-card-02-offer", "02-offer.png",
     ["Plug in a disk,", "tap Backup"], "Backup", (0, 0, 1080, 1900)),
    ("02-card-03-progress", "03-progress.png",
     ["Real progress,", "Pause and Stop"], "progress", (0, 0, 1080, 1900)),
    ("03-card-04-done", "04-done.png",
     ["Proof it copied —", "counts, not", "promises"], "counts,", (0, 0, 1080, 1900)),
    ("04-card-01-settings", "01-settings-defaults.png",
     ["Pick your folders", "once, then one tap"], "tap", (0, 0, 1080, 1900)),
    ("05-card-05-pro", "05-pro.png",
     ["Pro: auto-backup,", "unlimited size"], "unlimited", (0, 0, 1080, 1900)),
]


def draw_headline(draw, lines, emphasis, font):
    """White headline, one emphasis word in cyan. Returns bottom y."""
    y = 110
    for line in lines:
        x = MARGIN_X
        for word in line.split(" "):
            fill = CYAN if word == emphasis else WHITE
            draw.text((x, y), word, font=font, fill=fill)
            x += font.getlength(word + " ")
        y += LINE_HEIGHT
    return y


def rounded(shot, radius):
    mask = Image.new("L", shot.size, 0)
    md = ImageDraw.Draw(mask)
    md.rounded_rectangle([0, 0, shot.size[0], shot.size[1]], radius=radius, fill=255)
    out = Image.new("RGBA", shot.size, (0, 0, 0, 0))
    out.paste(shot, (0, 0), mask)
    return out


def build(name, src, lines, emphasis, crop):
    font = ImageFont.truetype(BOLD, FONT_SIZE)
    # Every headline line must fit the card width.
    for line in lines:
        w = font.getlength(line)
        assert w <= CARD_W - 2 * MARGIN_X, f"{name}: line too wide ({w:.0f}px): {line!r}"
    card = Image.new("RGB", (CARD_W, CARD_H), NAVY)
    draw = ImageDraw.Draw(card)
    bottom = draw_headline(draw, lines, emphasis, font)
    # Thin cyan rule under the headline.
    draw.rectangle([MARGIN_X, bottom + 36, MARGIN_X + 120, bottom + 44], fill=CYAN)
    shot = Image.open(os.path.join(SHOTS, src)).convert("RGB").crop(crop)
    max_w = CARD_W - 2 * MARGIN_X
    max_h = SHOT_BOTTOM - SHOT_TOP
    scale = min(max_w / shot.width, max_h / shot.height)
    assert scale <= 1.0, f"{name}: crop would upscale ({scale:.2f})"
    nw, nh = int(shot.width * scale), int(shot.height * scale)
    shot = shot.resize((nw, nh), Image.LANCZOS)
    shot = rounded(shot, RADIUS)
    x = (CARD_W - nw) // 2
    y = SHOT_TOP + (max_h - nh) // 2
    card.paste(shot, (x, y), shot)
    path = os.path.join(OUT, name + ".png")
    card.save(path)
    print(f"wrote {path} {card.size} (shot {nw}x{nh} from {src} crop {crop})")
    return path


def main():
    os.makedirs(OUT, exist_ok=True)
    for name, src, lines, emphasis, crop in CARDS:
        build(name, src, lines, emphasis, crop)


if __name__ == "__main__":
    main()
