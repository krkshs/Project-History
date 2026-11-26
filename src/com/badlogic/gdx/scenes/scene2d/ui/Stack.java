package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.SnapshotArray;

public class Stack extends WidgetGroup {
  private float prefWidth;
  
  private float prefHeight;
  
  private float minWidth;
  
  private float minHeight;
  
  private float maxWidth;
  
  private float maxHeight;
  
  private boolean sizeInvalid = true;
  
  public Stack() {
    setTransform(false);
    setWidth(150.0F);
    setHeight(150.0F);
    setTouchable(Touchable.childrenOnly);
  }
  
  public Stack(Actor... actors) {
    this();
    for (Actor actor : actors)
      addActor(actor); 
  }
  
  public void invalidate() {
    super.invalidate();
    this.sizeInvalid = true;
  }
  
  private void computeSize() {
    this.sizeInvalid = false;
    this.prefWidth = 0.0F;
    this.prefHeight = 0.0F;
    this.minWidth = 0.0F;
    this.minHeight = 0.0F;
    this.maxWidth = 0.0F;
    this.maxHeight = 0.0F;
    SnapshotArray<Actor> children = getChildren();
    for (int i = 0, n = children.size; i < n; i++) {
      float childMaxWidth, childMaxHeight;
      Actor child = children.get(i);
      if (child instanceof Layout) {
        Layout layout = (Layout)child;
        this.prefWidth = Math.max(this.prefWidth, layout.getPrefWidth());
        this.prefHeight = Math.max(this.prefHeight, layout.getPrefHeight());
        this.minWidth = Math.max(this.minWidth, layout.getMinWidth());
        this.minHeight = Math.max(this.minHeight, layout.getMinHeight());
        childMaxWidth = layout.getMaxWidth();
        childMaxHeight = layout.getMaxHeight();
      } else {
        this.prefWidth = Math.max(this.prefWidth, child.getWidth());
        this.prefHeight = Math.max(this.prefHeight, child.getHeight());
        this.minWidth = Math.max(this.minWidth, child.getWidth());
        this.minHeight = Math.max(this.minHeight, child.getHeight());
        childMaxWidth = 0.0F;
        childMaxHeight = 0.0F;
      } 
      if (childMaxWidth > 0.0F)
        this.maxWidth = (this.maxWidth == 0.0F) ? childMaxWidth : Math.min(this.maxWidth, childMaxWidth); 
      if (childMaxHeight > 0.0F)
        this.maxHeight = (this.maxHeight == 0.0F) ? childMaxHeight : Math.min(this.maxHeight, childMaxHeight); 
    } 
  }
  
  public void add(Actor actor) {
    addActor(actor);
  }
  
  public void layout() {
    if (this.sizeInvalid)
      computeSize(); 
    float width = getWidth(), height = getHeight();
    Array<Actor> children = getChildren();
    for (int i = 0, n = children.size; i < n; i++) {
      Actor child = children.get(i);
      child.setBounds(0.0F, 0.0F, width, height);
      if (child instanceof Layout)
        ((Layout)child).validate(); 
    } 
  }
  
  public float getPrefWidth() {
    if (this.sizeInvalid)
      computeSize(); 
    return this.prefWidth;
  }
  
  public float getPrefHeight() {
    if (this.sizeInvalid)
      computeSize(); 
    return this.prefHeight;
  }
  
  public float getMinWidth() {
    if (this.sizeInvalid)
      computeSize(); 
    return this.minWidth;
  }
  
  public float getMinHeight() {
    if (this.sizeInvalid)
      computeSize(); 
    return this.minHeight;
  }
  
  public float getMaxWidth() {
    if (this.sizeInvalid)
      computeSize(); 
    return this.maxWidth;
  }
  
  public float getMaxHeight() {
    if (this.sizeInvalid)
      computeSize(); 
    return this.maxHeight;
  }
}
