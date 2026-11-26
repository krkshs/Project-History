package com.badlogic.gdx.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class OrthographicCamera extends Camera {
  public float zoom = 1.0F;
  
  public OrthographicCamera() {
    this.near = 0.0F;
  }
  
  public OrthographicCamera(float viewportWidth, float viewportHeight) {
    this.viewportWidth = viewportWidth;
    this.viewportHeight = viewportHeight;
    this.near = 0.0F;
    update();
  }
  
  private final Vector3 tmp = new Vector3();
  
  public void update() {
    update(true);
  }
  
  public void update(boolean updateFrustum) {
    this.projection.setToOrtho(this.zoom * -this.viewportWidth / 2.0F, this.zoom * this.viewportWidth / 2.0F, this.zoom * -(this.viewportHeight / 2.0F), this.zoom * this.viewportHeight / 2.0F, this.near, this.far);
    this.view.setToLookAt(this.position, this.tmp.set(this.position).add(this.direction), this.up);
    this.combined.set(this.projection);
    Matrix4.mul(this.combined.val, this.view.val);
    if (updateFrustum) {
      this.invProjectionView.set(this.combined);
      Matrix4.inv(this.invProjectionView.val);
      this.frustum.update(this.invProjectionView);
    } 
  }
  
  public void setToOrtho(boolean yDown) {
    setToOrtho(yDown, (float)Gdx.graphics.getWidth(), (float)Gdx.graphics.getHeight());
  }
  
  public void setToOrtho(boolean yDown, float viewportWidth, float viewportHeight) {
    if (yDown) {
      this.up.set(0.0F, -1.0F, 0.0F);
      this.direction.set(0.0F, 0.0F, 1.0F);
    } else {
      this.up.set(0.0F, 1.0F, 0.0F);
      this.direction.set(0.0F, 0.0F, -1.0F);
    } 
    this.position.set(this.zoom * viewportWidth / 2.0F, this.zoom * viewportHeight / 2.0F, 0.0F);
    this.viewportWidth = viewportWidth;
    this.viewportHeight = viewportHeight;
    update();
  }
  
  public void rotate(float angle) {
    rotate(this.direction, angle);
  }
  
  public void translate(float x, float y) {
    translate(x, y, 0.0F);
  }
  
  public void translate(Vector2 vec) {
    translate(vec.x, vec.y, 0.0F);
  }
}
