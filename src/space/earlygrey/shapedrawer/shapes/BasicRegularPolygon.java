package space.earlygrey.shapedrawer.shapes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import space.earlygrey.shapedrawer.JoinType;
import space.earlygrey.shapedrawer.ShapeDrawer;

class BasicRegularPolygon extends BasicShape<BasicRegularPolygon> implements Shape.OutlinedRegularPolygon {
  Vector2 centre;
  
  float scaleX;
  
  float scaleY;
  
  float rotation;
  
  int sides;
  
  protected BasicRegularPolygon(ShapeDrawer drawer) {
    super(drawer);
    this.centre = new Vector2();
  }
  
  void reset(boolean filled) {
    super.reset(filled);
    this.centre.setZero();
    this.scaleX = 1.0F;
    this.scaleY = 1.0F;
    this.rotation = 0.0F;
    this.sides = 6;
  }
  
  public BasicRegularPolygon joinType(JoinType joinType) {
    this.joinType = joinType;
    return this;
  }
  
  public BasicRegularPolygon lineWidth(float width) {
    setLineWidth(width);
    return this;
  }
  
  public BasicRegularPolygon color(Color color) {
    this.color = color.toFloatBits();
    return this;
  }
  
  public BasicRegularPolygon centre(Vector2 centre) {
    this.centre.set(centre);
    return this;
  }
  
  public BasicRegularPolygon radiusX(float radiusX) {
    this.scaleX = radiusX;
    return this;
  }
  
  public BasicRegularPolygon radiusY(float radiusY) {
    this.scaleY = radiusY;
    return this;
  }
  
  public BasicRegularPolygon radius(float radius) {
    this.scaleX = radius;
    this.scaleY = radius;
    return this;
  }
  
  public BasicRegularPolygon rotation(float rotation) {
    this.rotation = rotation;
    return this;
  }
  
  public BasicRegularPolygon rotate(float rotation) {
    this.rotation += rotation;
    return this;
  }
  
  public BasicRegularPolygon sides(int sides) {
    this.sides = sides;
    return this;
  }
  
  public void draw() {
    float c = this.drawer.setColor(this.color);
    if (this.filled) {
      this.drawer.filledPolygon(this.centre.x, this.centre.y, this.sides, this.scaleX, this.scaleY, this.rotation);
    } else
      this.drawer.polygon(this.centre.x, this.centre.y, this.sides, this.scaleX, this.scaleY, this.rotation, this.lineWidth.getWidth(0, 0.0F), this.joinType); 
    this.drawer.setColor(c);
  }
}
