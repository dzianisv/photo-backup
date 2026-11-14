#!/usr/bin/env python3
"""Generate SnapDisk Play Store creatives from the launcher glyph.

Icon (512x512) + feature graphic (1024x500), navy #0F172A background
(same as the launcher), white download-arrow glyph — the same arrow
as ic_launcher_foreground.xml, drawn from the same proportions.
Regenerate any time with: python3 tool/play-store/generate_creatives.py
"""
import os
from PIL import Image, ImageDraw, ImageFont

OUT = os.path.dirname(os.path.abspath(__file__))
NAVY = (15, 23, 42)
WHITE = (255, 255, 255)
MUTED = (148, 163, 184)

BOLD = "/usr/share/fonts/truetype/dejavu/DejaVuSans-Bold.ttf"
REG = "/usr/share/fonts/truetype/dejavu/DejaVuSans.ttf"


def arrow(draw, cx, top, w, h):
    """Download arrow into a tray, in a box (cx-w/2, top, cx+w/2, top+h)."""
    # Arrow shaft + head (top 60% of the box), tray (bottom 15%).
    shaft_w = w * 0.22
    head_h = h * 0.34
    tray_h = h * 0.10
    tray_y = top + h - tray_h
    # Shaft
    draw.rectangle([cx - shaft_w / 2, top, cx + shaft_w / 2, top + h * 0.42], fill=WHITE)
    # Head (triangle)
    draw.polygon([
        (cx - w / 2, top + h * 0.22),
        (cx + w / 2, top + h * 0.22),
        (cx, top + h * 0.22 + head_h),
    ], fill=WHITE)
    # Tray
    draw.rectangle([cx - w / 2, tray_y, cx + w / 2, top + h], fill=WHITE)


def icon():
    img = Image.new("RGB", (512, 512), NAVY)
    draw = ImageDraw.Draw(img)
    arrow(draw, 256, 110, 300, 292)
    img.save(os.path.join(OUT, "icon-512.png"))


def feature_graphic():
    img = Image.new("RGB", (1024, 500), NAVY)
    draw = ImageDraw.Draw(img)
    arrow(draw, 200, 100, 220, 230)
    title = ImageFont.truetype(BOLD, 110)
    draw.text((360, 130), "SnapDisk", font=title, fill=WHITE)
    tag = ImageFont.truetype(REG, 36)
    draw.text((362, 290), "Photo backup to USB. One tap.", font=tag, fill=MUTED)
    img.save(os.path.join(OUT, "feature-graphic-1024x500.png"))


if __name__ == "__main__":
    icon()
    feature_graphic()
    print("wrote icon-512.png + feature-graphic-1024x500.png")
