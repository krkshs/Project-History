package space.earlygrey.shapedrawer.shapes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import space.earlygrey.shapedrawer.JoinType;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class BasicLine extends BasicShape<BasicLine> implements Shape.Line {
  private Vector2 from;
  
  private Vector2 to;
  
  private float startColor;
  
  private float endColor;
  
  boolean snap;
  
  protected BasicLine(ShapeDrawer drawer) {
    super(drawer);
    this.from = new Vector2();
    this.to = new Vector2();
  }
  
  void reset(boolean filled) {
    super.reset(filled);
    this.from.setZero();
    this.to.setZero();
    this.startColor = this.drawer.getPackedColor();
    this.endColor = this.drawer.getPackedColor();
    this.snap = this.drawer.isDefaultSnap();
  }
  
  public BasicLine from(float x, float y) {
    this.from.set(x, y);
    return this;
  }
  
  public BasicLine to(float x, float y) {
    this.to.set(x, y);
    return this;
  }
  
  public BasicLine from(Vector2 from) {
    this.from.set(from);
    return this;
  }
  
  public BasicLine to(Vector2 to) {
    this.to.set(to);
    return this;
  }
  
  public BasicLine joinType(JoinType joinType) {
    this.joinType = joinType;
    return this;
  }
  
  public BasicLine lineWidth(float width) {
    setLineWidth(width);
    return this;
  }
  
  public BasicLine color(Color color) {
    startColor(color);
    endColor(color);
    return this;
  }
  
  public BasicLine startColor(Color startColor) {
    this.startColor = startColor.toFloatBits();
    return this;
  }
  
  public BasicLine endColor(Color endColor) {
    this.endColor = endColor.toFloatBits();
    return this;
  }
  
  public BasicLine snap(boolean snap) {
    this.snap = snap;
    return this;
  }
  
  public void draw() {
    this.drawer.line(this.from.x, this.from.y, this.to.x, this.to.y, this.lineWidth.getWidth(0, 0.0F), this.snap, this.startColor, this.endColor);
  }
}
