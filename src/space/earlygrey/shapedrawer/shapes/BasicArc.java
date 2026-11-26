package space.earlygrey.shapedrawer.shapes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import space.earlygrey.shapedrawer.JoinType;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class BasicArc extends BasicShape<BasicArc> implements Shape.Arc {
  Vector2 centre;
  
  float radius;
  
  float startAngle;
  
  float radians;
  
  int sides;
  
  protected BasicArc(ShapeDrawer drawer) {
    super(drawer);
    this.centre = new Vector2();
  }
  
  void reset(boolean filled) {
    super.reset(filled);
    this.centre.setZero();
    this.radians = 3.1415927F;
    this.startAngle = 0.0F;
    this.radius = 1.0F;
    this.sides = 6;
  }
  
  public BasicArc color(Color color) {
    this.color = color.toFloatBits();
    return this;
  }
  
  public BasicArc joinType(JoinType joinType) {
    this.joinType = joinType;
    return this;
  }
  
  public BasicArc lineWidth(float width) {
    setLineWidth(width);
    return this;
  }
  
  public BasicArc centre(float x, float y) {
    this.centre.set(x, y);
    return this;
  }
  
  public BasicArc centre(Vector2 centre) {
    this.centre.set(centre);
    return this;
  }
  
  public BasicArc radius(float radius) {
    this.radius = radius;
    return this;
  }
  
  public BasicArc startAngle(float startAngle) {
    this.startAngle = startAngle;
    return this;
  }
  
  public BasicArc radians(float radians) {
    this.radians = radians;
    return this;
  }
  
  public BasicArc sides(int sides) {
    this.sides = sides;
    return this;
  }
  
  public void draw() {
    if (this.filled) {
      draw(() -> this.drawer.sector(this.centre.x, this.centre.y, this.radius, this.startAngle, this.radians, this.sides));
    } else
      draw(() -> this.drawer.arc(this.centre.x, this.centre.y, this.radius, this.startAngle, this.radians, this.lineWidth.getWidth(0, 0.0F), this.joinType, this.sides)); 
  }
}
