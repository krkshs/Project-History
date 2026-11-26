package space.earlygrey.shapedrawer;

import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.utils.ShortArray;

abstract class FilledPolygonDrawer<T extends BatchManager> extends DrawerTemplate<T> {
  static final EarClippingTriangulator triangulator = new EarClippingTriangulator();
  
  FilledPolygonDrawer(T batchManager, AbstractShapeDrawer drawer) {
    super(batchManager, drawer);
  }
  
  abstract void polygon(float paramFloat1, float paramFloat2, int paramInt, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9);
  
  abstract void polygon(float[] paramArrayOffloat, short[] paramArrayOfshort, int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);
  
  void polygon(float[] vertices, short[] triangles, int trianglesCount) {
    polygon(vertices, triangles, trianglesCount, 0.0F, 0.0F, 1.0F, 1.0F);
  }
  
  void polygon(float[] vertices) {
    polygon(vertices, 0, vertices.length);
  }
  
  void polygon(float[] vertices, int offset, int count) {
    ShortArray triangles = triangulator.computeTriangles(vertices, offset, count);
    polygon(vertices, triangles);
  }
  
  void polygon(float[] vertices, ShortArray triangles) {
    polygon(vertices, triangles.items, triangles.size);
  }
  
  void polygon(float[] vertices, short[] triangles) {
    polygon(vertices, triangles, triangles.length);
  }
  
  void polygon(float[] vertices, short[] triangles, float offsetX, float offsetY) {
    polygon(vertices, triangles, triangles.length, offsetX, offsetY, 1.0F, 1.0F);
  }
  
  void rectangle(float x, float y, float width, float height, float rotation) {
    float c = this.batchManager.floatBits;
    rectangle(x, y, width, height, rotation, c, c, c, c);
  }
  
  void rectangle(float x, float y, float width, float height, float rotation, float c1, float c2, float c3, float c4) {
    boolean caching = this.batchManager.isCachingDraws();
    this.batchManager.ensureSpaceForQuad();
    float cos = (float)Math.cos((double)rotation), sin = (float)Math.sin((double)rotation);
    float halfWidth = 0.5F * width, halfHeight = 0.5F * height;
    float centreX = x + halfWidth, centreY = y + halfHeight;
    x1(halfWidth * cos - halfHeight * sin + centreX);
    y1(halfWidth * sin + halfHeight * cos + centreY);
    x2(-halfWidth * cos - halfHeight * sin + centreX);
    y2(-halfWidth * sin + halfHeight * cos + centreY);
    x3(-halfWidth * cos - -halfHeight * sin + centreX);
    y3(-halfWidth * sin + -halfHeight * cos + centreY);
    x4(halfWidth * cos - -halfHeight * sin + centreX);
    y4(halfWidth * sin + -halfHeight * cos + centreY);
    color(c1, c2, c3, c4);
    this.batchManager.pushQuad();
    if (!caching)
      this.batchManager.pushToBatch(); 
  }
  
  public void triangle(float x1, float y1, float x2, float y2, float x3, float y3, float color1, float color2, float color3) {
    boolean caching = this.batchManager.isCachingDraws();
    vert1(x1, y1);
    vert2(x2, y2);
    vert3(x3, y3);
    color(color1, color2, color3);
    this.batchManager.pushTriangle();
    if (!caching)
      this.batchManager.pushToBatch(); 
  }
  
  static class BatchFilledPolygonDrawer extends FilledPolygonDrawer<BatchManager> {
    BatchFilledPolygonDrawer(BatchManager batchManager, AbstractShapeDrawer drawer) {
      super(batchManager, drawer);
    }
    
    void polygon(float centreX, float centreY, int sides, float radiusX, float radiusY, float rotation, float startAngle, float radians, float innerColor, float outerColor) {
      if (radians == 0.0F)
        return; 
      radians = Math.min(radians, 6.2831855F);
      boolean wasCaching = this.batchManager.startCaching();
      float angleInterval = 6.2831855F / (float)sides;
      float endAngle = startAngle + radians;
      float cos = (float)Math.cos((double)angleInterval), sin = (float)Math.sin((double)angleInterval);
      float cosRot = (float)Math.cos((double)rotation), sinRot = (float)Math.sin((double)rotation);
      int start = (int)Math.ceil((double)((float)sides * startAngle / 6.2831855F));
      int end = (int)Math.floor((double)((float)sides * endAngle / 6.2831855F)) + 1;
      if (ShapeUtils.epsilonEquals((float)start * angleInterval, startAngle))
        start++; 
      B.set(1.0F, 0.0F).rotateRad(startAngle).scl(radiusX, radiusY);
      int n = end - start + 1;
      if (n < 2) {
        this.batchManager.ensureSpaceForTriangle();
        A.set(1.0F, 0.0F).rotateRad(startAngle).scl(radiusX, radiusY);
        B.set(1.0F, 0.0F).rotateRad(endAngle).scl(radiusX, radiusY);
        vert1(centreX, centreY);
        x2(A.x * cosRot - A.y * sinRot + centreX);
        y2(A.x * sinRot + A.y * cosRot + centreY);
        x3(B.x * cosRot - B.y * sinRot + centreX);
        y3(B.x * sinRot + B.y * cosRot + centreY);
        color(innerColor, outerColor, outerColor);
        this.batchManager.pushTriangle();
      } else {
        dir.set(1.0F, 0.0F).rotateRad(Math.min((float)start * angleInterval, endAngle));
        C.set(dir).scl(radiusX, radiusY);
      } 
      for (int i = 0; i < n - 1; i++) {
        A.set(B);
        B.set(C);
        if (i < n - 2) {
          dir.set(dir.x * cos - dir.y * sin, dir.x * sin + dir.y * cos);
          C.set(dir).scl(radiusX, radiusY);
        } else
          C.set(1.0F, 0.0F).rotateRad(endAngle).scl(radiusX, radiusY); 
        if (i % 2 == 0) {
          this.batchManager.ensureSpaceForQuad();
          vert1(centreX, centreY);
          x2(A.x * cosRot - A.y * sinRot + centreX);
          y2(A.x * sinRot + A.y * cosRot + centreY);
          x3(B.x * cosRot - B.y * sinRot + centreX);
          y3(B.x * sinRot + B.y * cosRot + centreY);
          x4(C.x * cosRot - C.y * sinRot + centreX);
          y4(C.x * sinRot + C.y * cosRot + centreY);
          color(innerColor, outerColor, outerColor, outerColor);
          this.batchManager.pushQuad();
        } else if (i == n - 2) {
          this.batchManager.ensureSpaceForTriangle();
          C.set(1.0F, 0.0F).rotateRad(endAngle).scl(radiusX, radiusY);
          vert1(centreX, centreY);
          x2(B.x * cosRot - B.y * sinRot + centreX);
          y2(B.x * sinRot + B.y * cosRot + centreY);
          x3(C.x * cosRot - C.y * sinRot + centreX);
          y3(C.x * sinRot + C.y * cosRot + centreY);
          color(innerColor, outerColor, outerColor);
          this.batchManager.pushTriangle();
        } 
      } 
      if (!wasCaching)
        this.batchManager.endCaching(); 
    }
    
    void polygon(float[] vertices, short[] triangles, int trianglesCount, float x, float y, float scaleX, float scaleY) {
      float c = this.batchManager.floatBits;
      for (int i = 0; i < trianglesCount; i += 3) {
        this.batchManager.ensureSpaceForTriangle();
        vert1(scaleX * vertices[2 * triangles[i]] + x, scaleY * vertices[2 * triangles[i] + 1] + y);
        vert2(scaleX * vertices[2 * triangles[i + 1]] + x, scaleY * vertices[2 * triangles[i + 1] + 1] + y);
        vert3(scaleX * vertices[2 * triangles[i + 2]] + x, scaleY * vertices[2 * triangles[i + 2] + 1] + y);
        color(c, c, c);
        this.batchManager.pushTriangle();
      } 
      this.batchManager.pushToBatch();
    }
  }
  
  static class PolygonBatchFilledPolygonDrawer extends FilledPolygonDrawer<PolygonBatchManager> {
    PolygonBatchFilledPolygonDrawer(PolygonBatchManager batchManager, AbstractShapeDrawer drawer) {
      super(batchManager, drawer);
    }
    
    void polygon(float centreX, float centreY, int sides, float radiusX, float radiusY, float rotation, float startAngle, float radians, float innerColor, float outerColor) {
      if (radians == 0.0F)
        return; 
      radians = Math.min(radians, 6.2831855F);
      boolean wasCaching = this.batchManager.startCaching();
      float angleInterval = 6.2831855F / (float)sides;
      float endAngle = startAngle + radians;
      float cos = (float)Math.cos((double)angleInterval), sin = (float)Math.sin((double)angleInterval);
      float cosRot = (float)Math.cos((double)rotation), sinRot = (float)Math.sin((double)rotation);
      int start = (int)Math.ceil((double)((float)sides * startAngle / 6.2831855F));
      int end = (int)Math.floor((double)((float)sides * endAngle / 6.2831855F)) + 1;
      if (ShapeUtils.epsilonEquals((float)start * angleInterval, startAngle))
        start++; 
      int n = end - start + 1;
      this.batchManager.ensureSpace(n + 2);
      int vertexOffset = this.batchManager.getVerticesArrayIndex();
      vert1(centreX, centreY);
      color1(innerColor);
      this.batchManager.pushVertex();
      A.set(1.0F, 0.0F).rotateRad(startAngle).scl(radiusX, radiusY);
      x1(A.x * cosRot - A.y * sinRot + centreX);
      y1(A.x * sinRot + A.y * cosRot + centreY);
      color1(outerColor);
      this.batchManager.pushVertex();
      this.batchManager.pushTriangleIndices((short)vertexOffset, (short)(vertexOffset + 1), (short)(vertexOffset + 2));
      dir.set(1.0F, 0.0F).rotateRad(Math.min((float)start * angleInterval, endAngle));
      A.set(dir).scl(radiusX, radiusY);
      for (int i = 0; i < n - 1; i++) {
        x1(A.x * cosRot - A.y * sinRot + centreX);
        y1(A.x * sinRot + A.y * cosRot + centreY);
        color1(outerColor);
        this.batchManager.pushVertex();
        dir.set(dir.x * cos - dir.y * sin, dir.x * sin + dir.y * cos);
        A.set(dir).scl(radiusX, radiusY);
        this.batchManager.pushTriangleIndices((short)vertexOffset, (short)(vertexOffset + i + 2), (short)(vertexOffset + i + 3));
      } 
      A.set(1.0F, 0.0F).rotateRad(endAngle).scl(radiusX, radiusY);
      x1(A.x * cosRot - A.y * sinRot + centreX);
      y1(A.x * sinRot + A.y * cosRot + centreY);
      color1(outerColor);
      this.batchManager.pushVertex();
      if (!wasCaching)
        this.batchManager.endCaching(); 
    }
    
    void polygon(float[] vertices, short[] triangles, int trianglesCount, float offsetX, float offsetY, float scaleX, float scaleY) {
      int n = vertices.length / 2;
      this.batchManager.ensureSpace(n);
      this.batchManager.pushVertexData(vertices, triangles, trianglesCount, this.batchManager.floatBits, offsetX, offsetY, scaleX, scaleY);
      this.batchManager.pushToBatch();
    }
  }
}
