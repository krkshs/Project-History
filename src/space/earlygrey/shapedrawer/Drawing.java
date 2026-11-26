package space.earlygrey.shapedrawer;

import com.badlogic.gdx.utils.Array;

public class Drawing {
  BatchManager batchManager;
  
  Array<float[]> vertexBatches;
  
  private float[] tmpVertices;
  
  private float offsetX;
  
  private float offsetY;
  
  private float scaleX = 1.0F, scaleY = 1.0F;
  
  Drawing(BatchManager batchManager) {
    this.batchManager = batchManager;
    this.vertexBatches = new Array<>();
  }
  
  public void draw() {
    draw(this.offsetX, this.offsetY);
  }
  
  public void draw(float x, float y) {
    draw(x, y, this.scaleX, this.scaleY);
  }
  
  public void draw(float x, float y, float scaleX, float scaleY) {
    if (this.batchManager.r == null)
      throw new IllegalStateException("The texture region is null. Please set a texture region first (e.g. in the constructor or by calling setTextureRegion(TextureRegion region))"); 
    for (int i = 0; i < this.vertexBatches.size; i++) {
      float[] vertices = this.vertexBatches.get(i);
      getBatchManager().getBatch().draw(this.batchManager.r.getTexture(), applyTransformation(vertices, x, y, scaleX, scaleY), 0, vertices.length);
    } 
  }
  
  void pushVertices(float[] vertices, int count) {
    float[] copy = new float[count];
    System.arraycopy(vertices, 0, copy, 0, count);
    this.vertexBatches.add(copy);
  }
  
  void finalise() {
    this.vertexBatches.setSize(this.vertexBatches.size);
    int max = 0;
    for (int i = 0; i < this.vertexBatches.size; i++) {
      float[] vertices = this.vertexBatches.get(i);
      if (vertices.length > max)
        max = vertices.length; 
    } 
    this.tmpVertices = new float[max];
  }
  
  float[] applyTransformation(float[] vertices, float x, float y, float scaleX, float scaleY) {
    if (!needsTransforming(x, y, scaleX, scaleY))
      return vertices; 
    for (int i = 0; i < vertices.length; i += 5) {
      this.tmpVertices[i] = x + scaleX * vertices[i];
      this.tmpVertices[i + 1] = y + scaleY * vertices[i + 1];
      this.tmpVertices[i + 2] = vertices[i + 2];
      this.tmpVertices[i + 3] = vertices[i + 3];
      this.tmpVertices[i + 4] = vertices[i + 4];
    } 
    return this.tmpVertices;
  }
  
  boolean needsTransforming(float x, float y, float scaleX, float scaleY) {
    return (x != 0.0F || y != 0.0F || scaleX != 1.0F || scaleY != 1.0F);
  }
  
  BatchManager getBatchManager() {
    return this.batchManager;
  }
  
  public float getOffsetX() {
    return this.offsetX;
  }
  
  public void setOffset(float offsetX, float offsetY) {
    this.offsetX = offsetX;
    this.offsetY = offsetY;
  }
  
  public float getOffsetY() {
    return this.offsetY;
  }
  
  public float getScaleX() {
    return this.scaleX;
  }
  
  public void setScaleX(float scaleX, float scaleY) {
    this.scaleX = scaleX;
    this.scaleY = scaleY;
  }
  
  public float getScaleY() {
    return this.scaleY;
  }
  
  static class PolygonDrawing extends Drawing {
    Array<short[]> triangleBatches;
    
    PolygonDrawing(PolygonBatchManager batchManager) {
      super(batchManager);
      this.triangleBatches = new Array<>();
    }
    
    public void draw(float x, float y, float scaleX, float scaleY) {
      if (this.batchManager.r == null)
        throw new IllegalStateException("The texture region is null. Please set a texture region first (e.g. in the constructor or by calling setTextureRegion(TextureRegion region))"); 
      for (int i = 0; i < this.vertexBatches.size; i++) {
        float[] vertices = this.vertexBatches.get(i);
        short[] triangles = this.triangleBatches.get(i);
        getBatchManager().getBatch().draw(this.batchManager.r.getTexture(), applyTransformation(vertices, x, y, scaleX, scaleY), 0, vertices.length, triangles, 0, triangles.length);
      } 
    }
    
    PolygonBatchManager getBatchManager() {
      return (PolygonBatchManager)super.getBatchManager();
    }
    
    void pushVertices(float[] vertices, int count) {
      super.pushVertices(vertices, count);
      short[] triangles = new short[getBatchManager().getTrianglesArrayOffset()];
      System.arraycopy((getBatchManager()).triangles, 0, triangles, 0, triangles.length);
      this.triangleBatches.add(triangles);
    }
    
    void finalise() {
      super.finalise();
      this.triangleBatches.setSize(this.triangleBatches.size);
    }
  }
}
