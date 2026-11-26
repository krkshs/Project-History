package com.badlogic.gdx.scenes.scene2d.utils;

public interface Layout {
  void layout();
  
  void invalidate();
  
  void invalidateHierarchy();
  
  void validate();
  
  void pack();
  
  void setFillParent(boolean paramBoolean);
  
  void setLayoutEnabled(boolean paramBoolean);
  
  float getMinWidth();
  
  float getMinHeight();
  
  float getPrefWidth();
  
  float getPrefHeight();
  
  float getMaxWidth();
  
  float getMaxHeight();
}
