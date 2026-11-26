package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Null;

public class SplitPane extends WidgetGroup {
  SplitPaneStyle style;
  
  @Null
  private Actor firstWidget;
  
  @Null
  private Actor secondWidget;
  
  boolean vertical;
  
  float splitAmount = 0.5F;
  
  float minAmount;
  
  float maxAmount = 1.0F;
  
  private final Rectangle firstWidgetBounds = new Rectangle();
  
  private final Rectangle secondWidgetBounds = new Rectangle();
  
  final Rectangle handleBounds = new Rectangle();
  
  boolean cursorOverHandle;
  
  private final Rectangle tempScissors = new Rectangle();
  
  Vector2 lastPoint = new Vector2();
  
  Vector2 handlePosition = new Vector2();
  
  public SplitPane(@Null Actor firstWidget, @Null Actor secondWidget, boolean vertical, Skin skin) {
    this(firstWidget, secondWidget, vertical, skin, "default-" + (vertical ? "vertical" : "horizontal"));
  }
  
  public SplitPane(@Null Actor firstWidget, @Null Actor secondWidget, boolean vertical, Skin skin, String styleName) {
    this(firstWidget, secondWidget, vertical, skin.<SplitPaneStyle>get(styleName, SplitPaneStyle.class));
  }
  
  public SplitPane(@Null Actor firstWidget, @Null Actor secondWidget, boolean vertical, SplitPaneStyle style) {
    this.vertical = vertical;
    setStyle(style);
    setFirstWidget(firstWidget);
    setSecondWidget(secondWidget);
    setSize(getPrefWidth(), getPrefHeight());
    initialize();
  }
  
  private void initialize() {
    addListener(new InputListener() {
          int draggingPointer = -1;
          
          public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            if (this.draggingPointer != -1)
              return false; 
            if (pointer == 0 && button != 0)
              return false; 
            if (SplitPane.this.handleBounds.contains(x, y)) {
              this.draggingPointer = pointer;
              SplitPane.this.lastPoint.set(x, y);
              SplitPane.this.handlePosition.set(SplitPane.this.handleBounds.x, SplitPane.this.handleBounds.y);
              return true;
            } 
            return false;
          }
          
          public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            if (pointer == this.draggingPointer)
              this.draggingPointer = -1; 
          }
          
          public void touchDragged(InputEvent event, float x, float y, int pointer) {
            if (pointer != this.draggingPointer)
              return; 
            Drawable handle = SplitPane.this.style.handle;
            if (!SplitPane.this.vertical) {
              float delta = x - SplitPane.this.lastPoint.x;
              float availWidth = SplitPane.this.getWidth() - handle.getMinWidth();
              float dragX = SplitPane.this.handlePosition.x + delta;
              SplitPane.this.handlePosition.x = dragX;
              dragX = Math.max(0.0F, dragX);
              dragX = Math.min(availWidth, dragX);
              SplitPane.this.splitAmount = dragX / availWidth;
              SplitPane.this.lastPoint.set(x, y);
            } else {
              float delta = y - SplitPane.this.lastPoint.y;
              float availHeight = SplitPane.this.getHeight() - handle.getMinHeight();
              float dragY = SplitPane.this.handlePosition.y + delta;
              SplitPane.this.handlePosition.y = dragY;
              dragY = Math.max(0.0F, dragY);
              dragY = Math.min(availHeight, dragY);
              SplitPane.this.splitAmount = 1.0F - dragY / availHeight;
              SplitPane.this.lastPoint.set(x, y);
            } 
            SplitPane.this.invalidate();
          }
          
          public boolean mouseMoved(InputEvent event, float x, float y) {
            SplitPane.this.cursorOverHandle = SplitPane.this.handleBounds.contains(x, y);
            return false;
          }
        });
  }
  
  public void setStyle(SplitPaneStyle style) {
    this.style = style;
    invalidateHierarchy();
  }
  
  public SplitPaneStyle getStyle() {
    return this.style;
  }
  
  public void layout() {
    clampSplitAmount();
    if (!this.vertical) {
      calculateHorizBoundsAndPositions();
    } else
      calculateVertBoundsAndPositions(); 
    Actor firstWidget = this.firstWidget;
    if (firstWidget != null) {
      Rectangle firstWidgetBounds = this.firstWidgetBounds;
      firstWidget.setBounds(firstWidgetBounds.x, firstWidgetBounds.y, firstWidgetBounds.width, firstWidgetBounds.height);
      if (firstWidget instanceof Layout)
        ((Layout)firstWidget).validate(); 
    } 
    Actor secondWidget = this.secondWidget;
    if (secondWidget != null) {
      Rectangle secondWidgetBounds = this.secondWidgetBounds;
      secondWidget.setBounds(secondWidgetBounds.x, secondWidgetBounds.y, secondWidgetBounds.width, secondWidgetBounds.height);
      if (secondWidget instanceof Layout)
        ((Layout)secondWidget).validate(); 
    } 
  }
  
  public float getPrefWidth() {
    float first = (this.firstWidget == null) ? 0.0F : ((this.firstWidget instanceof Layout) ? ((Layout)this.firstWidget)
      .getPrefWidth() : this.firstWidget.getWidth());
    float second = (this.secondWidget == null) ? 0.0F : ((this.secondWidget instanceof Layout) ? ((Layout)this.secondWidget)
      .getPrefWidth() : this.secondWidget.getWidth());
    if (this.vertical)
      return Math.max(first, second); 
    return first + this.style.handle.getMinWidth() + second;
  }
  
  public float getPrefHeight() {
    float first = (this.firstWidget == null) ? 0.0F : ((this.firstWidget instanceof Layout) ? ((Layout)this.firstWidget)
      .getPrefHeight() : this.firstWidget.getHeight());
    float second = (this.secondWidget == null) ? 0.0F : ((this.secondWidget instanceof Layout) ? ((Layout)this.secondWidget)
      .getPrefHeight() : this.secondWidget.getHeight());
    if (!this.vertical)
      return Math.max(first, second); 
    return first + this.style.handle.getMinHeight() + second;
  }
  
  public float getMinWidth() {
    float first = (this.firstWidget instanceof Layout) ? ((Layout)this.firstWidget).getMinWidth() : 0.0F;
    float second = (this.secondWidget instanceof Layout) ? ((Layout)this.secondWidget).getMinWidth() : 0.0F;
    if (this.vertical)
      return Math.max(first, second); 
    return first + this.style.handle.getMinWidth() + second;
  }
  
  public float getMinHeight() {
    float first = (this.firstWidget instanceof Layout) ? ((Layout)this.firstWidget).getMinHeight() : 0.0F;
    float second = (this.secondWidget instanceof Layout) ? ((Layout)this.secondWidget).getMinHeight() : 0.0F;
    if (!this.vertical)
      return Math.max(first, second); 
    return first + this.style.handle.getMinHeight() + second;
  }
  
  public void setVertical(boolean vertical) {
    if (this.vertical == vertical)
      return; 
    this.vertical = vertical;
    invalidateHierarchy();
  }
  
  public boolean isVertical() {
    return this.vertical;
  }
  
  private void calculateHorizBoundsAndPositions() {
    Drawable handle = this.style.handle;
    float height = getHeight();
    float availWidth = getWidth() - handle.getMinWidth();
    float leftAreaWidth = (float)(int)(availWidth * this.splitAmount);
    float rightAreaWidth = availWidth - leftAreaWidth;
    float handleWidth = handle.getMinWidth();
    this.firstWidgetBounds.set(0.0F, 0.0F, leftAreaWidth, height);
    this.secondWidgetBounds.set(leftAreaWidth + handleWidth, 0.0F, rightAreaWidth, height);
    this.handleBounds.set(leftAreaWidth, 0.0F, handleWidth, height);
  }
  
  private void calculateVertBoundsAndPositions() {
    Drawable handle = this.style.handle;
    float width = getWidth();
    float height = getHeight();
    float availHeight = height - handle.getMinHeight();
    float topAreaHeight = (float)(int)(availHeight * this.splitAmount);
    float bottomAreaHeight = availHeight - topAreaHeight;
    float handleHeight = handle.getMinHeight();
    this.firstWidgetBounds.set(0.0F, height - topAreaHeight, width, topAreaHeight);
    this.secondWidgetBounds.set(0.0F, 0.0F, width, bottomAreaHeight);
    this.handleBounds.set(0.0F, bottomAreaHeight, width, handleHeight);
  }
  
  public void draw(Batch batch, float parentAlpha) {
    Stage stage = getStage();
    if (stage == null)
      return; 
    validate();
    Color color = getColor();
    float alpha = color.a * parentAlpha;
    applyTransform(batch, computeTransform());
    if (this.firstWidget != null && this.firstWidget.isVisible()) {
      batch.flush();
      stage.calculateScissors(this.firstWidgetBounds, this.tempScissors);
      if (ScissorStack.pushScissors(this.tempScissors)) {
        this.firstWidget.draw(batch, alpha);
        batch.flush();
        ScissorStack.popScissors();
      } 
    } 
    if (this.secondWidget != null && this.secondWidget.isVisible()) {
      batch.flush();
      stage.calculateScissors(this.secondWidgetBounds, this.tempScissors);
      if (ScissorStack.pushScissors(this.tempScissors)) {
        this.secondWidget.draw(batch, alpha);
        batch.flush();
        ScissorStack.popScissors();
      } 
    } 
    batch.setColor(color.r, color.g, color.b, alpha);
    this.style.handle.draw(batch, this.handleBounds.x, this.handleBounds.y, this.handleBounds.width, this.handleBounds.height);
    resetTransform(batch);
  }
  
  public void setSplitAmount(float splitAmount) {
    this.splitAmount = splitAmount;
    invalidate();
  }
  
  public float getSplitAmount() {
    return this.splitAmount;
  }
  
  protected void clampSplitAmount() {
    float effectiveMinAmount = this.minAmount, effectiveMaxAmount = this.maxAmount;
    if (this.vertical) {
      float availableHeight = getHeight() - this.style.handle.getMinHeight();
      if (this.firstWidget instanceof Layout)
        effectiveMinAmount = Math.max(effectiveMinAmount, 
            Math.min(((Layout)this.firstWidget).getMinHeight() / availableHeight, 1.0F)); 
      if (this.secondWidget instanceof Layout)
        effectiveMaxAmount = Math.min(effectiveMaxAmount, 1.0F - 
            Math.min(((Layout)this.secondWidget).getMinHeight() / availableHeight, 1.0F)); 
    } else {
      float availableWidth = getWidth() - this.style.handle.getMinWidth();
      if (this.firstWidget instanceof Layout)
        effectiveMinAmount = Math.max(effectiveMinAmount, Math.min(((Layout)this.firstWidget).getMinWidth() / availableWidth, 1.0F)); 
      if (this.secondWidget instanceof Layout)
        effectiveMaxAmount = Math.min(effectiveMaxAmount, 1.0F - 
            Math.min(((Layout)this.secondWidget).getMinWidth() / availableWidth, 1.0F)); 
    } 
    if (effectiveMinAmount > effectiveMaxAmount) {
      this.splitAmount = 0.5F * (effectiveMinAmount + effectiveMaxAmount);
    } else
      this.splitAmount = Math.max(Math.min(this.splitAmount, effectiveMaxAmount), effectiveMinAmount); 
  }
  
  public float getMinSplitAmount() {
    return this.minAmount;
  }
  
  public void setMinSplitAmount(float minAmount) {
    if (minAmount < 0.0F || minAmount > 1.0F)
      throw new GdxRuntimeException("minAmount has to be >= 0 and <= 1"); 
    this.minAmount = minAmount;
  }
  
  public float getMaxSplitAmount() {
    return this.maxAmount;
  }
  
  public void setMaxSplitAmount(float maxAmount) {
    if (maxAmount < 0.0F || maxAmount > 1.0F)
      throw new GdxRuntimeException("maxAmount has to be >= 0 and <= 1"); 
    this.maxAmount = maxAmount;
  }
  
  public void setFirstWidget(@Null Actor widget) {
    if (this.firstWidget != null)
      super.removeActor(this.firstWidget); 
    this.firstWidget = widget;
    if (widget != null)
      super.addActor(widget); 
    invalidate();
  }
  
  public void setSecondWidget(@Null Actor widget) {
    if (this.secondWidget != null)
      super.removeActor(this.secondWidget); 
    this.secondWidget = widget;
    if (widget != null)
      super.addActor(widget); 
    invalidate();
  }
  
  public void addActor(Actor actor) {
    throw new UnsupportedOperationException("Use SplitPane#setWidget.");
  }
  
  public void addActorAt(int index, Actor actor) {
    throw new UnsupportedOperationException("Use SplitPane#setWidget.");
  }
  
  public void addActorBefore(Actor actorBefore, Actor actor) {
    throw new UnsupportedOperationException("Use SplitPane#setWidget.");
  }
  
  public boolean removeActor(Actor actor) {
    if (actor == null)
      throw new IllegalArgumentException("actor cannot be null."); 
    if (actor == this.firstWidget) {
      setFirstWidget(null);
      return true;
    } 
    if (actor == this.secondWidget) {
      setSecondWidget(null);
      return true;
    } 
    return true;
  }
  
  public boolean removeActor(Actor actor, boolean unfocus) {
    if (actor == null)
      throw new IllegalArgumentException("actor cannot be null."); 
    if (actor == this.firstWidget) {
      super.removeActor(actor, unfocus);
      this.firstWidget = null;
      invalidate();
      return true;
    } 
    if (actor == this.secondWidget) {
      super.removeActor(actor, unfocus);
      this.secondWidget = null;
      invalidate();
      return true;
    } 
    return false;
  }
  
  public Actor removeActorAt(int index, boolean unfocus) {
    Actor actor = super.removeActorAt(index, unfocus);
    if (actor == this.firstWidget) {
      super.removeActor(actor, unfocus);
      this.firstWidget = null;
      invalidate();
    } else if (actor == this.secondWidget) {
      super.removeActor(actor, unfocus);
      this.secondWidget = null;
      invalidate();
    } 
    return actor;
  }
  
  public boolean isCursorOverHandle() {
    return this.cursorOverHandle;
  }
  
  public static class SplitPaneStyle {
    public Drawable handle;
    
    public SplitPaneStyle() {}
    
    public SplitPaneStyle(Drawable handle) {
      this.handle = handle;
    }
    
    public SplitPaneStyle(SplitPaneStyle style) {
      this.handle = style.handle;
    }
  }
}
