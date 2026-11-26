package space.earlygrey.shapedrawer;

import com.badlogic.gdx.math.Vector2;

class PolygonDrawer extends DrawerTemplate<BatchManager> {
  static final Vector2 centre = new Vector2(), radius = new Vector2();
  
  PolygonDrawer(BatchManager batchManager, AbstractShapeDrawer drawer) {
    super(batchManager, drawer);
  }
  
  void polygon(float centreX, float centreY, int sides, float radiusX, float radiusY, float rotation, float lineWidth, JoinType joinType, float startAngle, float radians) {
    if (radians == 0.0F)
      return; 
    radians = Math.min(radians, 6.2831855F);
    float halfLineWidth = 0.5F * lineWidth;
    centre.set(centreX, centreY);
    radius.set(radiusX, radiusY);
    boolean wasCaching = this.batchManager.startCaching();
    if (joinType == JoinType.NONE) {
      drawPolygonNoJoin(centre, sides, lineWidth, rotation, radius, startAngle, radians);
    } else
      drawPolygonWithJoin(centre, sides, halfLineWidth, rotation, radius, startAngle, radians, (joinType == JoinType.SMOOTH)); 
    if (!wasCaching)
      this.batchManager.endCaching(); 
  }
  
  void drawPolygonNoJoin(Vector2 centre, int sides, float lineWidth, float rotation, Vector2 radius, float startAngle, float radians) {
    float angleInterval = 6.2831855F / (float)sides;
    float endAngle = startAngle + radians;
    float cos = (float)Math.cos((double)angleInterval), sin = (float)Math.sin((double)angleInterval);
    float cosRot = (float)Math.cos((double)rotation), sinRot = (float)Math.sin((double)rotation);
    int start = (int)Math.ceil((double)((float)sides * startAngle / 6.2831855F));
    int end = (int)Math.floor((double)((float)sides * endAngle / 6.2831855F)) + 1;
    dir.set(1.0F, 0.0F).rotateRad(Math.min((float)start * angleInterval, endAngle));
    A.set(1.0F, 0.0F).rotateRad(startAngle).scl(radius);
    B.set(dir).scl(radius);
    for (int i = start; i <= end; i++) {
      float x1 = A.x * cosRot - A.y * sinRot + centre.x, y1 = A.x * sinRot + A.y * cosRot + centre.y;
      float x2 = B.x * cosRot - B.y * sinRot + centre.x, y2 = B.x * sinRot + B.y * cosRot + centre.y;
      this.drawer.lineDrawer.pushLine(x1, y1, x2, y2, lineWidth, false);
      if (i < end - 1) {
        A.set(B);
        dir.set(dir.x * cos - dir.y * sin, dir.x * sin + dir.y * cos);
        B.set(dir).scl(radius);
      } else if (i == end - 1) {
        A.set(B);
        B.set(1.0F, 0.0F).rotateRad(endAngle).scl(radius);
      } 
    } 
  }
  
  void drawPolygonWithJoin(Vector2 centre, int sides, float halfLineWidth, float rotation, Vector2 radius, float startAngle, float radians, boolean smooth) {
    int start, end;
    float c = this.batchManager.floatBits;
    boolean full = ShapeUtils.epsilonEquals(radians, 6.2831855F);
    float angleInterval = 6.2831855F / (float)sides;
    float endAngle = startAngle + radians;
    float cos = (float)Math.cos((double)angleInterval), sin = (float)Math.sin((double)angleInterval);
    float cosRot = (float)Math.cos((double)rotation), sinRot = (float)Math.sin((double)rotation);
    if (full) {
      start = 1;
      end = sides;
      dir.set(1.0F, 0.0F).rotateRad((float)start * angleInterval);
      A.set(1.0F, 0.0F).rotateRad((float)(start - 2) * angleInterval).scl(radius);
      C.set(dir).scl(radius);
      B.set(1.0F, 0.0F).rotateRad((float)(start - 1) * angleInterval).scl(radius);
    } else {
      start = (int)Math.ceil((double)((float)sides * startAngle / 6.2831855F));
      if (ShapeUtils.epsilonEquals((float)start * angleInterval, startAngle))
        start++; 
      end = (int)Math.floor((double)((float)sides * endAngle / 6.2831855F)) + 1;
      end = Math.min(end, start + sides);
      dir.set(1.0F, 0.0F).rotateRad(Math.min((float)start * angleInterval, endAngle));
      A.set(1.0F, 0.0F).rotateRad((float)(start - 1) * angleInterval).scl(radius);
      B.set(1.0F, 0.0F).rotateRad(startAngle).scl(radius);
      C.set(dir).scl(radius);
    } 
    for (int i = start; i <= end; i++) {
      this.batchManager.ensureSpaceForQuad();
      if (!full && i == start) {
        Joiner.prepareRadialEndpoint(B, D, E, halfLineWidth);
      } else if (smooth) {
        Joiner.prepareSmoothJoin(A, B, C, D, E, halfLineWidth, true);
      } else
        Joiner.preparePointyJoin(A, B, C, D, E, halfLineWidth); 
      vert1(E.x * cosRot - E.y * sinRot + centre.x, E.x * sinRot + E.y * cosRot + centre.y);
      vert2(D.x * cosRot - D.y * sinRot + centre.x, D.x * sinRot + D.y * cosRot + centre.y);
      if (full || i < end) {
        A.set(B);
        B.set(C);
        dir.set(dir.x * cos - dir.y * sin, dir.x * sin + dir.y * cos);
        C.set(dir).scl(radius);
      } else
        B.set(1.0F, 0.0F).rotateRad(endAngle).scl(radius); 
      if (full || i < end) {
        if (smooth) {
          Joiner.prepareSmoothJoin(A, B, C, D, E, halfLineWidth, false);
        } else
          Joiner.preparePointyJoin(A, B, C, D, E, halfLineWidth); 
      } else
        Joiner.prepareRadialEndpoint(B, D, E, halfLineWidth); 
      vert3(D.x * cosRot - D.y * sinRot + centre.x, D.x * sinRot + D.y * cosRot + centre.y);
      vert4(E.x * cosRot - E.y * sinRot + centre.x, E.x * sinRot + E.y * cosRot + centre.y);
      color(c, c, c, c);
      this.batchManager.pushQuad();
      if (smooth && (full || i < end))
        drawSmoothJoinFill(A, B, C, D, E, centre, cosRot, sinRot, halfLineWidth); 
    } 
  }
}
