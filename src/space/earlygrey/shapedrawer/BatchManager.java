package space.earlygrey.shapedrawer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;

class BatchManager {
  protected final Batch batch;
  
  protected TextureRegion r;
  
  protected float floatBits;
  
  protected float[] verts;
  
  protected int vertexCount;
  
  protected float pixelSize = 1.0F, halfPixelSize = 0.5F * this.pixelSize;
  
  protected float offset = 0.001F * this.pixelSize;
  
  protected boolean cacheDraws = false;
  
  protected static final Matrix4 mat4 = new Matrix4();
  
  Drawing drawing = null;
  
  static final int DEFAULT_VERTEX_CACHE_SIZE = 2000;
  
  static final int VERTEX_SIZE = 5;
  
  static final int QUAD_PUSH_SIZE = 20;
  
  BatchManager(Batch batch, TextureRegion region) {
    this.batch = batch;
    this.verts = new float[2000];
    setTextureRegion(region);
    setColor(Color.WHITE);
  }
  
  public void update() {
    update(true);
  }
  
  public void update(boolean updatePixelSize) {
    if (updatePixelSize)
      updatePixelSize(); 
  }
  
  public float updatePixelSize() {
    Matrix4 trans = getBatch().getTransformMatrix(), proj = getBatch().getProjectionMatrix();
    mat4.set(proj).mul(trans);
    float scaleX = mat4.getScaleX();
    float worldWidth = 2.0F / scaleX;
    float newPixelSize = worldWidth / (float)Gdx.graphics.getWidth();
    return setPixelSize(newPixelSize);
  }
  
  public float setPixelSize(float pixelSize) {
    float oldPixelSize = this.pixelSize;
    this.pixelSize = pixelSize;
    this.halfPixelSize = 0.5F * pixelSize;
    this.offset = 0.001F * pixelSize;
    return oldPixelSize;
  }
  
  public TextureRegion setTextureRegion(TextureRegion region) {
    TextureRegion oldRegion = this.r;
    this.r = region;
    setTextureRegionUV();
    return oldRegion;
  }
  
  private void setTextureRegionUV() {
    if (this.r != null) {
      float u = 0.5F * (this.r.getU() + this.r.getU2());
      float v = 0.5F * (this.r.getV() + this.r.getV2());
      for (int i = 0; i < this.verts.length; i += 5) {
        this.verts[i + 3] = u;
        this.verts[i + 4] = v;
      } 
    } 
  }
  
  public float setColor(Color color) {
    return setColor(color.toFloatBits());
  }
  
  public float setColor(float floatBits) {
    float oldColor = getPackedColor();
    this.floatBits = floatBits;
    return oldColor;
  }
  
  public float getPackedColor() {
    return this.floatBits;
  }
  
  boolean isCachingDraws() {
    return this.cacheDraws;
  }
  
  boolean startCaching() {
    boolean wasCaching = isCachingDraws();
    this.cacheDraws = true;
    return wasCaching;
  }
  
  void endCaching() {
    this.cacheDraws = false;
    if (this.vertexCount > 0)
      pushToBatch(); 
  }
  
  public float getPixelSize() {
    return this.pixelSize;
  }
  
  public Batch getBatch() {
    return this.batch;
  }
  
  public TextureRegion getRegion() {
    return this.r;
  }
  
  void startRecording() {
    this.drawing = createDrawing();
  }
  
  Drawing createDrawing() {
    return new Drawing(this);
  }
  
  Drawing stopRecording() {
    pushToBatch();
    this.drawing.finalise();
    Drawing returnVal = this.drawing;
    this.drawing = null;
    return returnVal;
  }
  
  boolean isRecording() {
    return (this.drawing != null);
  }
  
  void pushVertex() {
    this.vertexCount++;
  }
  
  void pushQuad() {
    this.vertexCount += 4;
  }
  
  void pushTriangle() {
    x4(x3());
    y4(y3());
    pushQuad();
  }
  
  void ensureSpaceForTriangle() {
    ensureSpace(4);
  }
  
  void ensureSpaceForQuad() {
    ensureSpace(4);
  }
  
  void ensureSpace(int vertices) {
    if (vertices * 5 > this.verts.length) {
      increaseCacheSize(vertices * 5);
    } else if (verticesRemaining() < vertices) {
      pushToBatch();
    } 
  }
  
  void increaseCacheSize(int minSize) {
    pushToBatch();
    int newSize = this.verts.length;
    while (minSize > newSize)
      newSize *= 2; 
    this.verts = new float[newSize];
    setTextureRegionUV();
  }
  
  int verticesRemaining() {
    return (this.verts.length - 20 * this.vertexCount) / 5;
  }
  
  void pushToBatch() {
    if (this.vertexCount == 0)
      return; 
    if (isRecording()) {
      this.drawing.pushVertices(this.verts, getVerticesArrayIndex());
    } else {
      if (this.r == null)
        throw new IllegalStateException("The texture region is null. Please set a texture region first (e.g. in the constructor or by calling setTextureRegion(TextureRegion region))"); 
      this.batch.draw(this.r.getTexture(), this.verts, 0, getVerticesArrayIndex());
    } 
    this.vertexCount = 0;
  }
  
  int getVerticesArrayIndex() {
    return 5 * this.vertexCount;
  }
  
  protected void x1(float x1) {
    this.verts[getVerticesArrayIndex() + 0] = x1;
  }
  
  protected void y1(float y1) {
    this.verts[getVerticesArrayIndex() + 1] = y1;
  }
  
  protected void x2(float x2) {
    this.verts[getVerticesArrayIndex() + 5] = x2;
  }
  
  protected void y2(float y2) {
    this.verts[getVerticesArrayIndex() + 6] = y2;
  }
  
  protected void x3(float x3) {
    this.verts[getVerticesArrayIndex() + 10] = x3;
  }
  
  protected void y3(float y3) {
    this.verts[getVerticesArrayIndex() + 11] = y3;
  }
  
  protected void x4(float x4) {
    this.verts[getVerticesArrayIndex() + 15] = x4;
  }
  
  protected void y4(float y4) {
    this.verts[getVerticesArrayIndex() + 16] = y4;
  }
  
  protected float x1() {
    return this.verts[getVerticesArrayIndex() + 0];
  }
  
  protected float y1() {
    return this.verts[getVerticesArrayIndex() + 1];
  }
  
  protected float x2() {
    return this.verts[getVerticesArrayIndex() + 5];
  }
  
  protected float y2() {
    return this.verts[getVerticesArrayIndex() + 6];
  }
  
  protected float x3() {
    return this.verts[getVerticesArrayIndex() + 10];
  }
  
  protected float y3() {
    return this.verts[getVerticesArrayIndex() + 11];
  }
  
  protected float x4() {
    return this.verts[getVerticesArrayIndex() + 15];
  }
  
  protected float y4() {
    return this.verts[getVerticesArrayIndex() + 16];
  }
  
  void color1(float c) {
    this.verts[getVerticesArrayIndex() + 2] = c;
  }
  
  void color2(float c) {
    this.verts[getVerticesArrayIndex() + 7] = c;
  }
  
  void color3(float c) {
    this.verts[getVerticesArrayIndex() + 12] = c;
  }
  
  void color4(float c) {
    this.verts[getVerticesArrayIndex() + 17] = c;
  }
}
