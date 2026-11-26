package space.earlygrey.shapedrawer;

import com.badlogic.gdx.math.MathUtils;

public class DefaultSideEstimator implements SideEstimator {
  protected int minimumSides;
  
  protected int maximumSides;
  
  protected float sideMultiplier;
  
  public DefaultSideEstimator() {
    this(20, 4000, 1.0F);
  }
  
  public DefaultSideEstimator(int minimumSides, int maximumSides, float sideMultiplier) {
    this.minimumSides = minimumSides;
    this.maximumSides = maximumSides;
    this.sideMultiplier = sideMultiplier;
  }
  
  public int estimateSidesRequired(float pixelSize, float radiusX, float radiusY) {
    float circumference = (float)(6.2831854820251465D * Math.sqrt((double)((radiusX * radiusX + radiusY * radiusY) / 2.0F)));
    int sides = (int)(circumference / (16.0F * pixelSize));
    float a = Math.min(radiusX, radiusY), b = Math.max(radiusX, radiusY);
    float eccentricity = (float)Math.sqrt((double)(1.0F - a * a / (b * b)));
    sides = (int)((float)sides + (float)sides * eccentricity * this.sideMultiplier / 16.0F);
    return MathUtils.clamp(sides, this.minimumSides, this.maximumSides);
  }
  
  public int getMinimumSides() {
    return this.minimumSides;
  }
  
  public void setMinimumSides(int minimumSides) {
    this.minimumSides = minimumSides;
  }
  
  public int getMaximumSides() {
    return this.maximumSides;
  }
  
  public void setMaximumSides(int maximumSides) {
    this.maximumSides = maximumSides;
  }
  
  public float getSideMultiplier() {
    return this.sideMultiplier;
  }
  
  public void setSideMultiplier(float sideMultiplier) {
    this.sideMultiplier = sideMultiplier;
  }
}
