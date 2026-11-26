package com.badlogic.gdx.scenes.scene2d.actions;

public class ScaleToAction extends TemporalAction {
  private float startX;
  
  private float startY;
  
  private float endX;
  
  private float endY;
  
  protected void begin() {
    this.startX = this.target.getScaleX();
    this.startY = this.target.getScaleY();
  }
  
  protected void update(float percent) {
    float x, y;
    if (percent == 0.0F) {
      x = this.startX;
      y = this.startY;
    } else if (percent == 1.0F) {
      x = this.endX;
      y = this.endY;
    } else {
      x = this.startX + (this.endX - this.startX) * percent;
      y = this.startY + (this.endY - this.startY) * percent;
    } 
    this.target.setScale(x, y);
  }
  
  public void setScale(float x, float y) {
    this.endX = x;
    this.endY = y;
  }
  
  public void setScale(float scale) {
    this.endX = scale;
    this.endY = scale;
  }
  
  public float getX() {
    return this.endX;
  }
  
  public void setX(float x) {
    this.endX = x;
  }
  
  public float getY() {
    return this.endY;
  }
  
  public void setY(float y) {
    this.endY = y;
  }
}
