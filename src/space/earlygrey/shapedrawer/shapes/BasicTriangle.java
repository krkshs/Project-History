package space.earlygrey.shapedrawer.shapes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import space.earlygrey.shapedrawer.JoinType;
import space.earlygrey.shapedrawer.ShapeDrawer;

class BasicTriangle extends BasicShape<BasicTriangle> implements Shape.OutlinedTriangle {
  Vector2 a;
  
  Vector2 b;
  
  Vector2 c;
  
  BasicTriangle(ShapeDrawer drawer) {
    super(drawer);
    this.a = new Vector2();
    this.b = new Vector2();
    this.c = new Vector2();
  }
  
  void reset(boolean filled) {
    super.reset(filled);
    this.a.set(0.0F, 0.0F);
    this.b.set(0.5F, 1.0F);
    this.c.set(1.0F, 0.0F);
  }
  
  public BasicTriangle color(Color color) {
    this.color = color.toFloatBits();
    return this;
  }
  
  public BasicTriangle joinType(JoinType joinType) {
    this.joinType = joinType;
    return this;
  }
  
  public BasicTriangle lineWidth(float width) {
    setLineWidth(width);
    return this;
  }
  
  public Shape.OutlinedTriangle a(float x, float y) {
    this.a.set(x, y);
    return this;
  }
  
  public Shape.OutlinedTriangle b(float x, float y) {
    this.b.set(x, y);
    return this;
  }
  
  public Shape.OutlinedTriangle c(float x, float y) {
    this.c.set(x, y);
    return this;
  }
  
  public Shape.OutlinedTriangle a(Vector2 a) {
    this.a.set(a);
    return this;
  }
  
  public Shape.OutlinedTriangle b(Vector2 b) {
    this.b.set(b);
    return this;
  }
  
  public Shape.OutlinedTriangle c(Vector2 c) {
    this.c.set(c);
    return this;
  }
  
  public void draw() {
    if (this.filled) {
      draw(() -> this.drawer.filledTriangle(this.a, this.b, this.c));
    } else
      draw(() -> this.drawer.triangle(this.a, this.b, this.c, this.lineWidth.getWidth(0, 0.0F), this.joinType, this.color)); 
  }
}
