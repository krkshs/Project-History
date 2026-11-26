package space.earlygrey.shapedrawer.shapes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.ShortArray;
import space.earlygrey.shapedrawer.JoinType;
import space.earlygrey.shapedrawer.ShapeDrawer;
import space.earlygrey.shapedrawer.ShapeUtils;

public class BasicPolyline extends BasicShape<BasicPolyline> implements Shape.PolyLine<BasicPolyline> {
  FloatArray vertices;
  
  float offsetX;
  
  float offsetY;
  
  float scaleX;
  
  float scaleY;
  
  boolean open;
  
  EarClippingTriangulator triangulator = new EarClippingTriangulator();
  
  BasicPolyline(ShapeDrawer drawer) {
    super(drawer);
    this.vertices = new FloatArray();
  }
  
  void reset(boolean filled) {
    super.reset(filled);
    this.vertices.clear();
    this.open = false;
    this.offsetX = 0.0F;
    this.offsetY = 0.0F;
    this.scaleX = 1.0F;
    this.scaleY = 1.0F;
  }
  
  public BasicPolyline setOpen(boolean open) {
    this.open = open;
    return this;
  }
  
  public BasicPolyline color(Color color) {
    this.color = color.toFloatBits();
    return this;
  }
  
  public BasicPolyline joinType(JoinType joinType) {
    this.joinType = joinType;
    return this;
  }
  
  public BasicPolyline lineWidth(float width) {
    setLineWidth(width);
    return this;
  }
  
  public BasicPolyline lineWidth(ShapeUtils.LineWidthFunction width) {
    this.lineWidth = width;
    return this;
  }
  
  public <T extends Vector2> BasicPolyline vertices(Iterable<T> points) {
    this.vertices.clear();
    points.forEach(p -> this.vertices.add(p.x, p.y));
    return this;
  }
  
  public BasicPolyline vertices(FloatArray points) {
    this.vertices.clear();
    this.vertices.addAll(points);
    return this;
  }
  
  public BasicPolyline vertices(float[] points) {
    this.vertices.clear();
    this.vertices.addAll(points, 0, points.length);
    return this;
  }
  
  public BasicPolyline addVertex(float x, float y) {
    this.vertices.add(x, y);
    return this;
  }
  
  public BasicPolyline addVertex(Vector2 vertex) {
    return addVertex(vertex.x, vertex.y);
  }
  
  public BasicPolyline offset(float x, float y) {
    this.offsetX = x;
    this.offsetY = y;
    return this;
  }
  
  public BasicPolyline scale(float x, float y) {
    this.scaleX = x;
    this.scaleY = y;
    return this;
  }
  
  public void draw() {
    if (this.filled) {
      ShortArray triangles = this.triangulator.computeTriangles(this.vertices.items, 0, this.vertices.size);
      draw(() -> this.drawer.filledPolygon(this.vertices.items, triangles.items, triangles.size, this.offsetX, this.offsetY, this.scaleX, this.scaleY));
    } else
      draw(() -> this.drawer.path(this.vertices.items, 0, this.vertices.size, this.lineWidth, this.joinType, this.open, this.offsetX, this.offsetY, this.scaleX, this.scaleY)); 
  }
}
