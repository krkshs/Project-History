package space.earlygrey.shapedrawer.shapes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import space.earlygrey.shapedrawer.JoinType;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class BasicEllipse extends BasicShape implements Shape.OutlinedEllipse {
  Vector2 centre;
  
  float radiusX;
  
  float radiusY;
  
  float rotation;
  
  protected BasicEllipse(ShapeDrawer drawer) {
    super(drawer);
    this.centre = new Vector2();
  }
  
  void reset(boolean filled) {
    super.reset(filled);
    this.centre.setZero();
    this.radiusX = 1.0F;
    this.radiusY = 1.0F;
    this.rotation = 0.0F;
  }
  
  public BasicEllipse color(Color color) {
    this.color = color.toFloatBits();
    return this;
  }
  
  public BasicEllipse centre(float x, float y) {
    this.centre.set(x, y);
    return this;
  }
  
  public BasicEllipse centre(Vector2 centre) {
    this.centre.set(centre);
    return this;
  }
  
  public BasicEllipse radiusX(float radiusX) {
    this.radiusX = radiusX;
    return this;
  }
  
  public BasicEllipse radiusY(float radiusY) {
    this.radiusY = radiusY;
    return this;
  }
  
  public BasicEllipse rotation(float rotation) {
    this.rotation = rotation;
    return this;
  }
  
  public BasicEllipse rotate(float rotation) {
    this.rotation += rotation;
    return this;
  }
  
  public BasicEllipse joinType(JoinType joinType) {
    this.joinType = joinType;
    return this;
  }
  
  public BasicEllipse lineWidth(float width) {
    setLineWidth(width);
    return this;
  }
  
  public void draw() {
    float c = this.drawer.setColor(this.color);
    if (this.filled) {
      this.drawer.filledEllipse(this.centre.x, this.centre.y, this.radiusX, this.radiusY, this.rotation);
    } else
      this.drawer.ellipse(this.centre.x, this.centre.y, this.radiusX, this.radiusY, this.rotation, this.lineWidth.getWidth(0, 0.0F), this.joinType); 
    this.drawer.setColor(c);
  }
}
