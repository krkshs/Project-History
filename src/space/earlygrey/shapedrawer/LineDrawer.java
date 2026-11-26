package space.earlygrey.shapedrawer;

import com.badlogic.gdx.math.Vector2;

class LineDrawer extends DrawerTemplate<BatchManager> {
  private final Vector2 l = new Vector2(), startOffset = new Vector2(), endOffset = new Vector2();
  
  LineDrawer(BatchManager batchManager, AbstractShapeDrawer drawer) {
    super(batchManager, drawer);
  }
  
  void line(float x1, float y1, float x2, float y2, float lineWidth, boolean snap, float c1, float c2) {
    pushLine(x1, y1, x2, y2, lineWidth, snap, c1, c2);
    this.batchManager.pushToBatch();
  }
  
  void line(float x1, float y1, float x2, float y2, float startLineWidth, float endLineWidth, boolean snap) {
    pushLine(x1, y1, x2, y2, startLineWidth, endLineWidth, snap, this.batchManager.floatBits, this.batchManager.floatBits);
    this.batchManager.pushToBatch();
  }
  
  void pushLine(float x1, float y1, float x2, float y2, float lineWidth, boolean snap) {
    pushLine(x1, y1, x2, y2, lineWidth, snap, this.batchManager.floatBits, this.batchManager.floatBits);
  }
  
  void pushLine(float x1, float y1, float x2, float y2, float startLineWidth, float endLineWidth, boolean snap) {
    pushLine(x1, y1, x2, y2, startLineWidth, endLineWidth, snap, this.batchManager.floatBits, this.batchManager.floatBits);
  }
  
  void pushLine(float x1, float y1, float x2, float y2, float lineWidth, boolean snap, float c1, float c2) {
    pushLine(x1, y1, x2, y2, lineWidth, lineWidth, snap, c1, c2);
  }
  
  void pushLine(float x1, float y1, float x2, float y2, float startLineWidth, float endLineWidth, boolean snap, float c1, float c2) {
    this.batchManager.ensureSpaceForQuad();
    this.l.set(x2 - x1, y2 - y1);
    if (snap) {
      float offset = this.batchManager.offset;
      float pixelSize = this.batchManager.pixelSize, halfPixelSize = this.batchManager.halfPixelSize;
      x1 = ShapeUtils.snap(x1, pixelSize, halfPixelSize) - Math.signum(this.l.x) * offset;
      y1 = ShapeUtils.snap(y1, pixelSize, halfPixelSize) - Math.signum(this.l.y) * offset;
      x2 = ShapeUtils.snap(x2, pixelSize, halfPixelSize) + Math.signum(this.l.x) * offset;
      y2 = ShapeUtils.snap(y2, pixelSize, halfPixelSize) + Math.signum(this.l.y) * offset;
    } 
    if (ShapeUtils.epsilonEquals(x1, x2)) {
      this.startOffset.set(startLineWidth / 2.0F, 0.0F);
      this.endOffset.set(endLineWidth / 2.0F, 0.0F);
    } else if (ShapeUtils.epsilonEquals(y1, y2)) {
      this.startOffset.set(0.0F, startLineWidth / 2.0F);
      this.endOffset.set(0.0F, endLineWidth / 2.0F);
    } else {
      this.startOffset.set(this.l).setLength(startLineWidth / 2.0F);
      this.startOffset.set(-this.startOffset.y, this.startOffset.x);
      this.endOffset.set(this.l).setLength(endLineWidth / 2.0F);
      this.endOffset.set(-this.endOffset.y, this.endOffset.x);
    } 
    x1(x1 + this.startOffset.x);
    y1(y1 + this.startOffset.y);
    x2(x1 - this.startOffset.x);
    y2(y1 - this.startOffset.y);
    x3(x2 - this.endOffset.x);
    y3(y2 - this.endOffset.y);
    x4(x2 + this.endOffset.x);
    y4(y2 + this.endOffset.y);
    color1(c1);
    color2(c1);
    color3(c2);
    color4(c2);
    this.batchManager.pushQuad();
    if (!this.batchManager.isCachingDraws())
      this.batchManager.pushToBatch(); 
  }
}
