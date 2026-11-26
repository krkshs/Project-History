package space.earlygrey.shapedrawer.shapes;

import space.earlygrey.shapedrawer.ShapeDrawer;

public class Brush extends Utensil {
  public Brush(ShapeDrawer drawer) {
    super(drawer);
  }
  
  boolean filled() {
    return true;
  }
  
  public Shape.Sector sector() {
    this.ARC.reset(filled());
    return this.ARC;
  }
}
