package space.earlygrey.shapedrawer.scene2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import space.earlygrey.shapedrawer.GraphDrawer;
import space.earlygrey.shapedrawer.JoinType;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class GraphDrawerDrawable extends ShapeDrawerDrawable {
  private transient GraphDrawer graphDrawer;
  
  private Interpolation interpolation;
  
  private float color = new Color(Color.WHITE).toFloatBits();
  
  private float lineWidth = 1.0F;
  
  private JoinType joinType = JoinType.SMOOTH;
  
  private int samples = 50;
  
  private float plotBegin = 0.0F;
  
  private float plotEnd = 1.0F;
  
  private float domainBegin = 0.0F;
  
  private float domainEnd = 1.0F;
  
  private boolean rescale = true;
  
  public GraphDrawerDrawable(GraphDrawer graphDrawer) {
    super(graphDrawer.getShapeDrawer());
    this.graphDrawer = graphDrawer;
  }
  
  public void drawShapes(ShapeDrawer shapeDrawer, float x, float y, float width, float height) {
    float previousColor = shapeDrawer.getPackedColor();
    shapeDrawer.setColor(this.color);
    float previousLineWidth = shapeDrawer.getDefaultLineWidth();
    shapeDrawer.setDefaultLineWidth(this.lineWidth);
    this.graphDrawer.draw(this.interpolation, x, y, width, height, this.joinType, this.samples, this.plotBegin, this.plotEnd, this.domainBegin, this.domainEnd, this.rescale);
    shapeDrawer.setColor(previousColor);
    shapeDrawer.setDefaultLineWidth(previousLineWidth);
  }
  
  public GraphDrawer getGraphDrawer() {
    return this.graphDrawer;
  }
  
  public void setGraphDrawer(GraphDrawer graphDrawer) {
    this.graphDrawer = graphDrawer;
  }
  
  public Interpolation getInterpolation() {
    return this.interpolation;
  }
  
  public void setInterpolation(Interpolation interpolation) {
    this.interpolation = interpolation;
  }
  
  public float getPackedColor() {
    return this.color;
  }
  
  public void setColor(float color) {
    this.color = color;
  }
  
  public void setColor(Color color) {
    this.color = color.toFloatBits();
  }
  
  public float getLineWidth() {
    return this.lineWidth;
  }
  
  public void setLineWidth(float lineWidth) {
    this.lineWidth = lineWidth;
  }
  
  public JoinType getJoinType() {
    return this.joinType;
  }
  
  public void setJoinType(JoinType joinType) {
    this.joinType = joinType;
  }
  
  public int getSamples() {
    return this.samples;
  }
  
  public void setSamples(int samples) {
    this.samples = samples;
  }
  
  public float getPlotBegin() {
    return this.plotBegin;
  }
  
  public void setPlotBegin(float plotBegin) {
    this.plotBegin = plotBegin;
  }
  
  public float getPlotEnd() {
    return this.plotEnd;
  }
  
  public void setPlotEnd(float plotEnd) {
    this.plotEnd = plotEnd;
  }
  
  public float getDomainBegin() {
    return this.domainBegin;
  }
  
  public void setDomainBegin(float domainBegin) {
    this.domainBegin = domainBegin;
  }
  
  public float getDomainEnd() {
    return this.domainEnd;
  }
  
  public void setDomainEnd(float domainEnd) {
    this.domainEnd = domainEnd;
  }
  
  public boolean isRescale() {
    return this.rescale;
  }
  
  public void setRescale(boolean rescale) {
    this.rescale = rescale;
  }
}
