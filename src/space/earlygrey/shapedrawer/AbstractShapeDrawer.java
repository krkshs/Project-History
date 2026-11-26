package space.earlygrey.shapedrawer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;

public abstract class AbstractShapeDrawer {
  final BatchManager batchManager;
  
  float defaultLineWidth = 1.0F;
  
  boolean defaultSnap = false;
  
  protected static final Matrix4 mat4 = new Matrix4();
  
  protected static final float[] trianglePathPoints = new float[6];
  
  protected final LineDrawer lineDrawer;
  
  protected final PathDrawer pathDrawer;
  
  protected final PolygonDrawer polygonDrawer;
  
  protected final FilledPolygonDrawer filledPolygonDrawer;
  
  private SideEstimator sideEstimator;
  
  AbstractShapeDrawer(Batch batch, TextureRegion region, SideEstimator sideEstimator) {
    if (batch instanceof PolygonBatch) {
      PolygonBatchManager manager = new PolygonBatchManager((PolygonBatch)batch, region);
      this.filledPolygonDrawer = new FilledPolygonDrawer.PolygonBatchFilledPolygonDrawer(manager, this);
      this.batchManager = manager;
    } else {
      this.batchManager = new BatchManager(batch, region);
      this.filledPolygonDrawer = new FilledPolygonDrawer.BatchFilledPolygonDrawer(this.batchManager, this);
    } 
    this.lineDrawer = new LineDrawer(this.batchManager, this);
    this.pathDrawer = new PathDrawer(this.batchManager, this);
    this.polygonDrawer = new PolygonDrawer(this.batchManager, this);
    this.sideEstimator = sideEstimator;
  }
  
  public void startRecording() {
    this.batchManager.startRecording();
  }
  
  public Drawing stopRecording() {
    return this.batchManager.stopRecording();
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
  
  protected boolean isJoinNecessary(float lineWidth) {
    return (lineWidth > 3.0F * getPixelSize());
  }
  
  protected boolean isJoinNecessary() {
    return isJoinNecessary(getDefaultLineWidth());
  }
  
  protected int estimateSidesRequired(float radiusX, float radiusY) {
    return this.sideEstimator.estimateSidesRequired(getPixelSize(), radiusX, radiusY);
  }
  
  public final SideEstimator getSideEstimator() {
    return this.sideEstimator;
  }
  
  public SideEstimator setSideEstimator(SideEstimator sideEstimator) {
    SideEstimator oldSideEstimator = this.sideEstimator;
    this.sideEstimator = sideEstimator;
    return oldSideEstimator;
  }
  
  public float setPixelSize(float pixelSize) {
    return this.batchManager.setPixelSize(pixelSize);
  }
  
  public float getPixelSize() {
    return this.batchManager.getPixelSize();
  }
  
  public Batch getBatch() {
    return this.batchManager.getBatch();
  }
  
  public TextureRegion getRegion() {
    return this.batchManager.getRegion();
  }
  
  public float getDefaultLineWidth() {
    return this.defaultLineWidth;
  }
  
  public float setDefaultLineWidth(float defaultLineWidth) {
    float oldWidth = this.defaultLineWidth;
    this.defaultLineWidth = defaultLineWidth;
    return oldWidth;
  }
  
  public boolean isDefaultSnap() {
    return this.defaultSnap;
  }
  
  public boolean setDefaultSnap(boolean defaultSnap) {
    boolean oldSnap = this.defaultSnap;
    this.defaultSnap = defaultSnap;
    return oldSnap;
  }
  
  public TextureRegion setTextureRegion(TextureRegion region) {
    return this.batchManager.setTextureRegion(region);
  }
  
  public float setColor(Color color) {
    return setColor(color.toFloatBits());
  }
  
  public float setColor(float floatBits) {
    return this.batchManager.setColor(floatBits);
  }
  
  public float getPackedColor() {
    return this.batchManager.getPackedColor();
  }
  
  public float setColor(float r, float g, float b, float a) {
    return setColor(Color.toFloatBits(r, g, b, a));
  }
}
