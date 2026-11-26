package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.utils.SnapshotArray;

public class WidgetGroup extends Group implements Layout {
  private boolean needsLayout = true;
  
  private boolean fillParent;
  
  private boolean layoutEnabled = true;
  
  public WidgetGroup() {}
  
  public WidgetGroup(Actor... actors) {
    for (Actor actor : actors)
      addActor(actor); 
  }
  
  public float getMinWidth() {
    return getPrefWidth();
  }
  
  public float getMinHeight() {
    return getPrefHeight();
  }
  
  public float getPrefWidth() {
    return 0.0F;
  }
  
  public float getPrefHeight() {
    return 0.0F;
  }
  
  public float getMaxWidth() {
    return 0.0F;
  }
  
  public float getMaxHeight() {
    return 0.0F;
  }
  
  public void setLayoutEnabled(boolean enabled) {
    this.layoutEnabled = enabled;
    setLayoutEnabled(this, enabled);
  }
  
  private void setLayoutEnabled(Group parent, boolean enabled) {
    SnapshotArray<Actor> children = parent.getChildren();
    for (int i = 0, n = children.size; i < n; i++) {
      Actor actor = children.get(i);
      if (actor instanceof Layout) {
        ((Layout)actor).setLayoutEnabled(enabled);
      } else if (actor instanceof Group) {
        setLayoutEnabled((Group)actor, enabled);
      } 
    } 
  }
  
  public void validate() {
    if (!this.layoutEnabled)
      return; 
    Group parent = getParent();
    if (this.fillParent && parent != null) {
      float parentWidth, parentHeight;
      Stage stage = getStage();
      if (stage != null && parent == stage.getRoot()) {
        parentWidth = stage.getWidth();
        parentHeight = stage.getHeight();
      } else {
        parentWidth = parent.getWidth();
        parentHeight = parent.getHeight();
      } 
      if (getWidth() != parentWidth || getHeight() != parentHeight) {
        setWidth(parentWidth);
        setHeight(parentHeight);
        invalidate();
      } 
    } 
    if (!this.needsLayout)
      return; 
    this.needsLayout = false;
    layout();
    if (this.needsLayout) {
      if (parent instanceof WidgetGroup)
        return; 
      for (int i = 0; i < 5; i++) {
        this.needsLayout = false;
        layout();
        if (!this.needsLayout)
          break; 
      } 
    } 
  }
  
  public boolean needsLayout() {
    return this.needsLayout;
  }
  
  public void invalidate() {
    this.needsLayout = true;
  }
  
  public void invalidateHierarchy() {
    invalidate();
    Group parent = getParent();
    if (parent instanceof Layout)
      ((Layout)parent).invalidateHierarchy(); 
  }
  
  protected void childrenChanged() {
    invalidateHierarchy();
  }
  
  protected void sizeChanged() {
    invalidate();
  }
  
  public void pack() {
    setSize(getPrefWidth(), getPrefHeight());
    validate();
    setSize(getPrefWidth(), getPrefHeight());
    validate();
  }
  
  public void setFillParent(boolean fillParent) {
    this.fillParent = fillParent;
  }
  
  public void layout() {}
  
  public void draw(Batch batch, float parentAlpha) {
    validate();
    super.draw(batch, parentAlpha);
  }
}
