package space.earlygrey.shapedrawer.shapes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import space.earlygrey.shapedrawer.JoinType;
import space.earlygrey.shapedrawer.ShapeDrawer;

class BasicRectangle extends BasicShape<BasicRectangle> implements Shape.OutlinedRectangle {
  float x;
  
  float y;
  
  float width;
  
  float height;
  
  float rotation;
  
  BasicRectangle(ShapeDrawer drawer) {
    super(drawer);
  }
  
  void reset(boolean filled) {
    super.reset(filled);
    this.x = 0.0F;
    this.y = 0.0F;
    this.width = 1.0F;
    this.height = 1.0F;
    this.rotation = 0.0F;
  }
  
  public BasicRectangle color(Color color) {
    this.color = color.toFloatBits();
    return this;
  }
  
  public BasicRectangle joinType(JoinType joinType) {
    this.joinType = joinType;
    return this;
  }
  
  public BasicRectangle lineWidth(float width) {
    setLineWidth(width);
    return this;
  }
  
  public Shape.OutlinedRectangle position(float x, float y) {
    this.x = x;
    this.y = y;
    return this;
  }
  
  public Shape.OutlinedRectangle position(Vector2 position) {
    return position(position.x, position.y);
  }
  
  public Shape.OutlinedRectangle size(float width, float height) {
    this.width = width;
    this.height = height;
    return this;
  }
  
  public Shape.OutlinedRectangle rotation(float rotation) {
    this.rotation = rotation;
    return this;
  }
  
  public Shape.OutlinedRectangle rotate(float rotation) {
    this.rotation += rotation;
    return this;
  }
  
  public void draw() {
    if (this.filled) {
      draw(() -> this.drawer.filledRectangle(this.x, this.y, this.width, this.height, this.rotation));
    } else
      draw(() -> this.drawer.rectangle(this.x, this.y, this.width, this.height, this.lineWidth.getWidth(0, 0.0F), this.rotation, this.joinType)); 
  }
}
