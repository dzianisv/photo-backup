#!/usr/bin/env python3
"""Generate the SnapDisk Play Store feature graphic (1024x500), navy
#0F172A background (same as the launcher), white download-arrow glyph.

The arrow is the launcher mark, not a redraw: arrow() maps the verbatim
path coordinates from app/src/main/res/drawable/ic_launcher_foreground.xml
(viewport 108x108) into the caller's box, so its internal proportions
(head height, shaft width, tray position) are the vector's own.

The app icon (icon-512.png) is NOT generated here. It is rendered from
tool/play-store/icon.svg (the same vector, standalone SVG) with cairosvg:
  python3 -c "import cairosvg; cairosvg.svg2png(url='tool/play-store/icon.svg', write_to='tool/play-store/icon-512.png', output_width=512, output_height=512)"
That split is deliberate (WL-202): PIL draws un-antialiased polygons,
cairosvg antialiases, so a PIL icon would re-diverge from the SVG by
thousands of pixels even with identical geometry. Do not re-add an
icon() writer to this script.
Regenerate the feature graphic any time with:
python3 tool/play-store/generate_creatives.py
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
    """Download arrow into a tray, in a box (cx-w/2, top, cx+w/2, top+h).

    Geometry is the launcher vector verbatim
    (ic_launcher_foreground.xml, viewport 108x108): the arrow is one
    polygon (shaft + head), the tray a separate rect, mapped from the
    mark's own bounding box (x 19..83, y 26..84) into the caller's box.
    A box with the vector's aspect (w/h = 64/58) reproduces the vector
    exactly; any other box keeps the vector's internal proportions and
    stretches only the overall aspect, as the old code did.
    """
    # Arrow polygon (shaft + head), tray rect, mark bbox — vector coords.
    ARROW = [(73, 42), (53, 42), (53, 26), (39, 26), (39, 42), (19, 42), (46, 69)]
    TRAY = (25, 76, 83, 84)  # x0, y0, x1, y1
    MX0, MY0, MX1, MY1 = 19, 26, 83, 84
    def X(vx):
        return cx - w / 2 + (vx - MX0) / (MX1 - MX0) * w
    def Y(vy):
        return top + (vy - MY0) / (MY1 - MY0) * h
    draw.polygon([(X(x), Y(y)) for x, y in ARROW], fill=WHITE)
    draw.rectangle([X(TRAY[0]), Y(TRAY[1]), X(TRAY[2]), Y(TRAY[3])], fill=WHITE)


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
    feature_graphic()
    print("wrote feature-graphic-1024x500.png (icon-512.png comes from icon.svg, see header)")
