package space.earlygrey.shapedrawer.shapes;

import space.earlygrey.shapedrawer.ShapeDrawer;

abstract class Utensil {
  ShapeDrawer drawer;
  
  final BasicPolyline POLYLINE;
  
  final BasicRegularPolygon REGULAR_POLYGON;
  
  final BasicCircle CIRCLE;
  
  final BasicEllipse ELLIPSE;
  
  final BasicRectangle RECTANGLE;
  
  final BasicArc ARC;
  
  final BasicTriangle TRIANGLE;
  
  Utensil(ShapeDrawer drawer) {
    this.drawer = drawer;
    this.POLYLINE = new BasicPolyline(drawer);
    this.REGULAR_POLYGON = new BasicRegularPolygon(drawer);
    this.CIRCLE = new BasicCircle(drawer);
    this.ELLIPSE = new BasicEllipse(drawer);
    this.RECTANGLE = new BasicRectangle(drawer);
    this.ARC = new BasicArc(drawer);
    this.TRIANGLE = new BasicTriangle(drawer);
  }
  
  abstract boolean filled();
  
  public Shape.FilledPolygon<?> polygon() {
    this.POLYLINE.reset(filled());
    return this.POLYLINE;
  }
  
  public Shape.FilledRegularPolygon<?> regularPolygon() {
    this.REGULAR_POLYGON.reset(filled());
    return this.REGULAR_POLYGON;
  }
  
  public Shape.FilledCircle<?> circle() {
    this.CIRCLE.reset(filled());
    return this.CIRCLE;
  }
  
  public Shape.FilledEllipse<?> ellipse() {
    this.ELLIPSE.reset(filled());
    return this.ELLIPSE;
  }
  
  public Shape.FilledRectangle<?> rectangle() {
    this.RECTANGLE.reset(filled());
    return this.RECTANGLE;
  }
  
  public Shape.FilledTriangle<?> triangle() {
    this.TRIANGLE.reset(filled());
    return this.TRIANGLE;
  }
}
