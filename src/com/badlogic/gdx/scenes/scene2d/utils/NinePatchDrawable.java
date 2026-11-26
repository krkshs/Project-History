package com.badlogic.gdx.scenes.scene2d.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;

public class NinePatchDrawable extends BaseDrawable implements TransformDrawable {
  private NinePatch patch;
  
  public NinePatchDrawable() {}
  
  public NinePatchDrawable(NinePatch patch) {
    setPatch(patch);
  }
  
  public NinePatchDrawable(NinePatchDrawable drawable) {
    super(drawable);
    this.patch = drawable.patch;
  }
  
  public void draw(Batch batch, float x, float y, float width, float height) {
    this.patch.draw(batch, x, y, width, height);
  }
  
  public void draw(Batch batch, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation) {
    this.patch.draw(batch, x, y, originX, originY, width, height, scaleX, scaleY, rotation);
  }
  
  public void setPatch(NinePatch patch) {
    this.patch = patch;
    if (patch != null) {
      setMinWidth(patch.getTotalWidth());
      setMinHeight(patch.getTotalHeight());
      setTopHeight(patch.getPadTop());
      setRightWidth(patch.getPadRight());
      setBottomHeight(patch.getPadBottom());
      setLeftWidth(patch.getPadLeft());
    } 
  }
  
  public NinePatch getPatch() {
    return this.patch;
  }
  
  public NinePatchDrawable tint(Color tint) {
    NinePatchDrawable drawable = new NinePatchDrawable(this);
    drawable.patch = new NinePatch(drawable.getPatch(), tint);
    return drawable;
  }
}
