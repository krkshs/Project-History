package space.earlygrey.shapedrawer.shapes;

import space.earlygrey.shapedrawer.ShapeDrawer;

public class Pen extends Utensil {
  final BasicLine LINE;
  
  public Pen(ShapeDrawer drawer) {
    super(drawer);
    this.LINE = new BasicLine(drawer);
  }
  
  boolean filled() {
    return false;
  }
  
  public Shape.Line<?> line() {
    this.LINE.reset(false);
    return this.LINE;
  }
  
  public Shape.PolyLine<?> polyLine() {
    this.POLYLINE.reset(false);
    this.POLYLINE.setOpen(true);
    return this.POLYLINE;
  }
  
  public Shape.OutlinedPolygon<?> polygon() {
    this.POLYLINE.reset(filled());
    return this.POLYLINE;
  }
  
  public Shape.OutlinedRegularPolygon<?> regularPolygon() {
    this.REGULAR_POLYGON.reset(false);
    return this.REGULAR_POLYGON;
  }
  
  public Shape.OutlinedCircle<?> circle() {
    this.CIRCLE.reset(false);
    return this.CIRCLE;
  }
  
  public Shape.OutlinedEllipse<?> ellipse() {
    this.ELLIPSE.reset(false);
    return this.ELLIPSE;
  }
  
  public Shape.OutlinedRectangle<?> rectangle() {
    this.RECTANGLE.reset(false);
    return this.RECTANGLE;
  }
  
  public Shape.Arc<?> arc() {
    this.ARC.reset(false);
    return this.ARC;
  }
  
  public Shape.OutlinedTriangle<?> triangle() {
    this.TRIANGLE.reset(false);
    return this.TRIANGLE;
  }
}
