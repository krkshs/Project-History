package space.earlygrey.shapedrawer.shapes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import space.earlygrey.shapedrawer.JoinType;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class BasicCircle implements Shape.OutlinedCircle {
  private BasicEllipse ellipse;
  
  protected BasicCircle(ShapeDrawer drawer) {
    this.ellipse = new BasicEllipse(drawer);
  }
  
  void reset(boolean filled) {
    this.ellipse.reset(filled);
  }
  
  public BasicCircle color(Color color) {
    this.ellipse.color(color);
    return this;
  }
  
  public BasicCircle centre(float x, float y) {
    this.ellipse.centre(x, y);
    return this;
  }
  
  public BasicCircle centre(Vector2 centre) {
    this.ellipse.centre(centre);
    return this;
  }
  
  public BasicCircle radius(float radius) {
    this.ellipse.radiusX(radius);
    this.ellipse.radiusY(radius);
    return this;
  }
  
  public BasicCircle joinType(JoinType joinType) {
    this.ellipse.joinType(joinType);
    return this;
  }
  
  public BasicCircle lineWidth(float width) {
    this.ellipse.setLineWidth(width);
    return this;
  }
  
  public void draw() {
    this.ellipse.draw();
  }
}
