package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TransformDrawable;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.Scaling;

public class Image extends Widget {
  private Scaling scaling;
  
  private int align = 1;
  
  private float imageX;
  
  private float imageY;
  
  private float imageWidth;
  
  private float imageHeight;
  
  private Drawable drawable;
  
  public Image() {
    this((Drawable)null);
  }
  
  public Image(@Null NinePatch patch) {
    this(new NinePatchDrawable(patch), Scaling.stretch, 1);
  }
  
  public Image(@Null TextureRegion region) {
    this(new TextureRegionDrawable(region), Scaling.stretch, 1);
  }
  
  public Image(Texture texture) {
    this(new TextureRegionDrawable(new TextureRegion(texture)));
  }
  
  public Image(Skin skin, String drawableName) {
    this(skin.getDrawable(drawableName), Scaling.stretch, 1);
  }
  
  public Image(@Null Drawable drawable) {
    this(drawable, Scaling.stretch, 1);
  }
  
  public Image(@Null Drawable drawable, Scaling scaling) {
    this(drawable, scaling, 1);
  }
  
  public Image(@Null Drawable drawable, Scaling scaling, int align) {
    setDrawable(drawable);
    this.scaling = scaling;
    this.align = align;
    setSize(getPrefWidth(), getPrefHeight());
  }
  
  public void layout() {
    if (this.drawable == null)
      return; 
    float regionWidth = this.drawable.getMinWidth();
    float regionHeight = this.drawable.getMinHeight();
    float width = getWidth();
    float height = getHeight();
    Vector2 size = this.scaling.apply(regionWidth, regionHeight, width, height);
    this.imageWidth = size.x;
    this.imageHeight = size.y;
    if ((this.align & 0x8) != 0) {
      this.imageX = 0.0F;
    } else if ((this.align & 0x10) != 0) {
      this.imageX = (float)(int)(width - this.imageWidth);
    } else
      this.imageX = (float)(int)(width / 2.0F - this.imageWidth / 2.0F); 
    if ((this.align & 0x2) != 0) {
      this.imageY = (float)(int)(height - this.imageHeight);
    } else if ((this.align & 0x4) != 0) {
      this.imageY = 0.0F;
    } else
      this.imageY = (float)(int)(height / 2.0F - this.imageHeight / 2.0F); 
  }
  
  public void draw(Batch batch, float parentAlpha) {
    validate();
    Color color = getColor();
    batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
    float x = getX();
    float y = getY();
    float scaleX = getScaleX();
    float scaleY = getScaleY();
    if (this.drawable instanceof TransformDrawable) {
      float rotation = getRotation();
      if (scaleX != 1.0F || scaleY != 1.0F || rotation != 0.0F) {
        ((TransformDrawable)this.drawable).draw(batch, x + this.imageX, y + this.imageY, getOriginX() - this.imageX, getOriginY() - this.imageY, this.imageWidth, this.imageHeight, scaleX, scaleY, rotation);
        return;
      } 
    } 
    if (this.drawable != null)
      this.drawable.draw(batch, x + this.imageX, y + this.imageY, this.imageWidth * scaleX, this.imageHeight * scaleY); 
  }
  
  public void setDrawable(Skin skin, String drawableName) {
    setDrawable(skin.getDrawable(drawableName));
  }
  
  public void setDrawable(@Null Drawable drawable) {
    if (this.drawable == drawable)
      return; 
    if (drawable != null) {
      if (getPrefWidth() != drawable.getMinWidth() || getPrefHeight() != drawable.getMinHeight())
        invalidateHierarchy(); 
    } else
      invalidateHierarchy(); 
    this.drawable = drawable;
  }
  
  @Null
  public Drawable getDrawable() {
    return this.drawable;
  }
  
  public void setScaling(Scaling scaling) {
    if (scaling == null)
      throw new IllegalArgumentException("scaling cannot be null."); 
    this.scaling = scaling;
    invalidate();
  }
  
  public void setAlign(int align) {
    this.align = align;
    invalidate();
  }
  
  public int getAlign() {
    return this.align;
  }
  
  public float getMinWidth() {
    return 0.0F;
  }
  
  public float getMinHeight() {
    return 0.0F;
  }
  
  public float getPrefWidth() {
    if (this.drawable != null)
      return this.drawable.getMinWidth(); 
    return 0.0F;
  }
  
  public float getPrefHeight() {
    if (this.drawable != null)
      return this.drawable.getMinHeight(); 
    return 0.0F;
  }
  
  public float getImageX() {
    return this.imageX;
  }
  
  public float getImageY() {
    return this.imageY;
  }
  
  public float getImageWidth() {
    return this.imageWidth;
  }
  
  public float getImageHeight() {
    return this.imageHeight;
  }
  
  public String toString() {
    String name = getName();
    if (name != null)
      return name; 
    String className = getClass().getName();
    int dotIndex = className.lastIndexOf('.');
    if (dotIndex != -1)
      className = className.substring(dotIndex + 1); 
    return ((className.indexOf('$') != -1) ? "Image " : "") + className + ": " + this.drawable;
  }
}
