package space.earlygrey.shapedrawer.scene2d;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import space.earlygrey.shapedrawer.ShapeDrawer;

public abstract class ShapeDrawerDrawable extends BaseDrawable {
  public transient ShapeDrawer shapeDrawer;
  
  public ShapeDrawerDrawable() {}
  
  public ShapeDrawerDrawable(ShapeDrawer shapeDrawer) {
    this.shapeDrawer = shapeDrawer;
  }
  
  public void draw(Batch batch, float x, float y, float width, float height) {
    if (this.shapeDrawer == null)
      throw new NullPointerException("shapeDrawer must be defined before the Drawable can be drawn."); 
    if (!batch.equals(this.shapeDrawer.getBatch()))
      throw new IllegalArgumentException("Argument \"batch\" does not match \"shapeDrawer.batch\""); 
    drawShapes(this.shapeDrawer, x, y, width, height);
  }
  
  public abstract void drawShapes(ShapeDrawer paramShapeDrawer, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);
  
  public ShapeDrawer getShapeDrawer() {
    return this.shapeDrawer;
  }
  
  public void setShapeDrawer(ShapeDrawer shapeDrawer) {
    this.shapeDrawer = shapeDrawer;
  }
}
