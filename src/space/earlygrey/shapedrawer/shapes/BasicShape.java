package space.earlygrey.shapedrawer.shapes;

import com.badlogic.gdx.graphics.Color;
import space.earlygrey.shapedrawer.JoinType;
import space.earlygrey.shapedrawer.ShapeDrawer;
import space.earlygrey.shapedrawer.ShapeUtils;

public abstract class BasicShape<T extends BasicShape> {
  final ShapeDrawer drawer;
  
  final ShapeUtils.ConstantLineWidth CONSTANT_LINE_WIDTH = new ShapeUtils.ConstantLineWidth();
  
  ShapeUtils.LineWidthFunction lineWidth;
  
  JoinType joinType;
  
  float color;
  
  boolean filled;
  
  BasicShape(ShapeDrawer drawer) {
    this.drawer = drawer;
  }
  
  void reset(boolean filled) {
    this.filled = filled;
    this.lineWidth = this.CONSTANT_LINE_WIDTH.width(this.drawer.getDefaultLineWidth());
    this.joinType = JoinType.POINTY;
    this.color = this.drawer.getPackedColor();
  }
  
  void draw(Runnable drawCall) {
    float c = this.drawer.setColor(this.color);
    drawCall.run();
    this.drawer.setColor(c);
  }
  
  void setLineWidth(float width) {
    this.lineWidth = this.CONSTANT_LINE_WIDTH.width(width);
  }
  
  abstract T joinType(JoinType paramJoinType);
  
  abstract T lineWidth(float paramFloat);
  
  abstract T color(Color paramColor);
}
