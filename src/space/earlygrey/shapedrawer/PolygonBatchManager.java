package space.earlygrey.shapedrawer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

class PolygonBatchManager extends BatchManager {
  protected short[] triangles;
  
  protected int triangleCount = 0;
  
  PolygonBatchManager(PolygonBatch batch, TextureRegion region) {
    super(batch, region);
    int trianglesLength = (int)Math.ceil((double)(this.verts.length / 5 * 3));
    this.triangles = new short[trianglesLength];
  }
  
  public PolygonBatch getBatch() {
    return (PolygonBatch)super.getBatch();
  }
  
  Drawing createDrawing() {
    return new Drawing.PolygonDrawing(this);
  }
  
  void pushQuad() {
    pushTriangleIndices((short)this.vertexCount, (short)(this.vertexCount + 1), (short)(this.vertexCount + 2));
    pushTriangleIndices((short)this.vertexCount, (short)(this.vertexCount + 2), (short)(this.vertexCount + 3));
    super.pushQuad();
  }
  
  void pushTriangle() {
    pushTriangleIndices((short)this.vertexCount, (short)(this.vertexCount + 1), (short)(this.vertexCount + 2));
    this.vertexCount += 3;
  }
  
  void pushTriangleIndices(short t1, short t2, short t3) {
    int t = getTrianglesArrayOffset();
    this.triangles[t] = t1;
    this.triangles[t + 1] = t2;
    this.triangles[t + 2] = t3;
    this.triangleCount++;
  }
  
  protected void pushVertexData(float[] vertices, short[] triangles, int trianglesArrayCount, float color) {
    pushVertexData(vertices, triangles, trianglesArrayCount, color, 0.0F, 0.0F, 1.0F, 1.0F);
  }
  
  protected void pushVertexData(float[] vertices, short[] triangles, int trianglesArrayCount, float color, float offsetX, float offsetY, float scaleX, float scaleY) {
    int v = getVerticesArrayIndex();
    int t = getTrianglesArrayOffset();
    for (int i = 0, n = trianglesArrayCount; i < n; i++)
      this.triangles[t + i] = (short)(this.vertexCount + triangles[i]); 
    this.triangleCount += trianglesArrayCount / 3;
    for (int j = 0; j < vertices.length; j += 2) {
      float x = scaleX * vertices[j] + offsetX, y = scaleY * vertices[j + 1] + offsetY;
      this.verts[v + 0] = x;
      this.verts[v + 1] = y;
      this.verts[v + 2] = color;
      v += 5;
    } 
    this.vertexCount += vertices.length / 2;
  }
  
  void ensureSpaceForTriangle() {
    ensureSpace(3);
  }
  
  void pushToBatch() {
    if (this.vertexCount == 0)
      return; 
    if (isRecording()) {
      this.drawing.pushVertices(this.verts, getVerticesArrayIndex());
    } else
      getBatch().draw(this.r.getTexture(), this.verts, 0, getVerticesArrayIndex(), this.triangles, 0, getTrianglesArrayOffset()); 
    this.vertexCount = 0;
    this.triangleCount = 0;
  }
  
  void increaseCacheSize(int minSize) {
    super.increaseCacheSize(minSize);
    int trianglesLength = (int)Math.ceil((double)(this.verts.length / 5 * 3));
    this.triangles = new short[trianglesLength];
  }
  
  int getTrianglesArrayOffset() {
    return 3 * this.triangleCount;
  }
}
