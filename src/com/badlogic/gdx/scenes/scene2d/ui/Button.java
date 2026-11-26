package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Disableable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.Pools;

public class Button extends Table implements Disableable {
  private ButtonStyle style;
  
  boolean isChecked;
  
  boolean isDisabled;
  
  ButtonGroup buttonGroup;
  
  private ClickListener clickListener;
  
  private boolean programmaticChangeEvents = true;
  
  public Button(Skin skin) {
    super(skin);
    initialize();
    setStyle(skin.<ButtonStyle>get(ButtonStyle.class));
    setSize(getPrefWidth(), getPrefHeight());
  }
  
  public Button(Skin skin, String styleName) {
    super(skin);
    initialize();
    setStyle(skin.<ButtonStyle>get(styleName, ButtonStyle.class));
    setSize(getPrefWidth(), getPrefHeight());
  }
  
  public Button(Actor child, Skin skin, String styleName) {
    this(child, skin.<ButtonStyle>get(styleName, ButtonStyle.class));
    setSkin(skin);
  }
  
  public Button(Actor child, ButtonStyle style) {
    initialize();
    add(child);
    setStyle(style);
    setSize(getPrefWidth(), getPrefHeight());
  }
  
  public Button(ButtonStyle style) {
    initialize();
    setStyle(style);
    setSize(getPrefWidth(), getPrefHeight());
  }
  
  public Button() {
    initialize();
  }
  
  private void initialize() {
    setTouchable(Touchable.enabled);
    addListener(this.clickListener = new ClickListener() {
          public void clicked(InputEvent event, float x, float y) {
            if (Button.this.isDisabled())
              return; 
            Button.this.setChecked(!Button.this.isChecked, true);
          }
        });
  }
  
  public Button(@Null Drawable up) {
    this(new ButtonStyle(up, null, null));
  }
  
  public Button(@Null Drawable up, @Null Drawable down) {
    this(new ButtonStyle(up, down, null));
  }
  
  public Button(@Null Drawable up, @Null Drawable down, @Null Drawable checked) {
    this(new ButtonStyle(up, down, checked));
  }
  
  public Button(Actor child, Skin skin) {
    this(child, skin.<ButtonStyle>get(ButtonStyle.class));
  }
  
  public void setChecked(boolean isChecked) {
    setChecked(isChecked, this.programmaticChangeEvents);
  }
  
  void setChecked(boolean isChecked, boolean fireEvent) {
    if (this.isChecked == isChecked)
      return; 
    if (this.buttonGroup != null && !this.buttonGroup.canCheck(this, isChecked))
      return; 
    this.isChecked = isChecked;
    if (fireEvent) {
      ChangeListener.ChangeEvent changeEvent = Pools.<ChangeListener.ChangeEvent>obtain(ChangeListener.ChangeEvent.class);
      if (fire(changeEvent))
        this.isChecked = !isChecked; 
      Pools.free(changeEvent);
    } 
  }
  
  public void toggle() {
    setChecked(!this.isChecked);
  }
  
  public boolean isChecked() {
    return this.isChecked;
  }
  
  public boolean isPressed() {
    return this.clickListener.isVisualPressed();
  }
  
  public boolean isOver() {
    return this.clickListener.isOver();
  }
  
  public ClickListener getClickListener() {
    return this.clickListener;
  }
  
  public boolean isDisabled() {
    return this.isDisabled;
  }
  
  public void setDisabled(boolean isDisabled) {
    this.isDisabled = isDisabled;
  }
  
  public void setProgrammaticChangeEvents(boolean programmaticChangeEvents) {
    this.programmaticChangeEvents = programmaticChangeEvents;
  }
  
  public void setStyle(ButtonStyle style) {
    if (style == null)
      throw new IllegalArgumentException("style cannot be null."); 
    this.style = style;
    setBackground(getBackgroundDrawable());
  }
  
  public ButtonStyle getStyle() {
    return this.style;
  }
  
  @Null
  public ButtonGroup getButtonGroup() {
    return this.buttonGroup;
  }
  
  @Null
  protected Drawable getBackgroundDrawable() {
    if (isDisabled() && this.style.disabled != null)
      return this.style.disabled; 
    if (isPressed()) {
      if (isChecked() && this.style.checkedDown != null)
        return this.style.checkedDown; 
      if (this.style.down != null)
        return this.style.down; 
    } 
    if (isOver())
      if (isChecked()) {
        if (this.style.checkedOver != null)
          return this.style.checkedOver; 
      } else if (this.style.over != null) {
        return this.style.over;
      }  
    boolean focused = hasKeyboardFocus();
    if (isChecked()) {
      if (focused && this.style.checkedFocused != null)
        return this.style.checkedFocused; 
      if (this.style.checked != null)
        return this.style.checked; 
      if (isOver() && this.style.over != null)
        return this.style.over; 
    } 
    if (focused && this.style.focused != null)
      return this.style.focused; 
    return this.style.up;
  }
  
  public void draw(Batch batch, float parentAlpha) {
    validate();
    setBackground(getBackgroundDrawable());
    float offsetX = 0.0F, offsetY = 0.0F;
    if (isPressed() && !isDisabled()) {
      offsetX = this.style.pressedOffsetX;
      offsetY = this.style.pressedOffsetY;
    } else if (isChecked() && !isDisabled()) {
      offsetX = this.style.checkedOffsetX;
      offsetY = this.style.checkedOffsetY;
    } else {
      offsetX = this.style.unpressedOffsetX;
      offsetY = this.style.unpressedOffsetY;
    } 
    boolean offset = (offsetX != 0.0F || offsetY != 0.0F);
    Array<Actor> children = getChildren();
    if (offset)
      for (int i = 0; i < children.size; i++)
        children.get(i).moveBy(offsetX, offsetY);  
    super.draw(batch, parentAlpha);
    if (offset)
      for (int i = 0; i < children.size; i++)
        children.get(i).moveBy(-offsetX, -offsetY);  
    Stage stage = getStage();
    if (stage != null && stage.getActionsRequestRendering() && isPressed() != this.clickListener.isPressed())
      Gdx.graphics.requestRendering(); 
  }
  
  public float getPrefWidth() {
    float width = super.getPrefWidth();
    if (this.style.up != null)
      width = Math.max(width, this.style.up.getMinWidth()); 
    if (this.style.down != null)
      width = Math.max(width, this.style.down.getMinWidth()); 
    if (this.style.checked != null)
      width = Math.max(width, this.style.checked.getMinWidth()); 
    return width;
  }
  
  public float getPrefHeight() {
    float height = super.getPrefHeight();
    if (this.style.up != null)
      height = Math.max(height, this.style.up.getMinHeight()); 
    if (this.style.down != null)
      height = Math.max(height, this.style.down.getMinHeight()); 
    if (this.style.checked != null)
      height = Math.max(height, this.style.checked.getMinHeight()); 
    return height;
  }
  
  public float getMinWidth() {
    return getPrefWidth();
  }
  
  public float getMinHeight() {
    return getPrefHeight();
  }
  
  public static class ButtonStyle {
    @Null
    public Drawable up;
    
    @Null
    public Drawable down;
    
    @Null
    public Drawable over;
    
    @Null
    public Drawable focused;
    
    @Null
    public Drawable disabled;
    
    @Null
    public Drawable checked;
    
    @Null
    public Drawable checkedOver;
    
    @Null
    public Drawable checkedDown;
    
    @Null
    public Drawable checkedFocused;
    
    public float pressedOffsetX;
    
    public float pressedOffsetY;
    
    public float unpressedOffsetX;
    
    public float unpressedOffsetY;
    
    public float checkedOffsetX;
    
    public float checkedOffsetY;
    
    public ButtonStyle() {}
    
    public ButtonStyle(@Null Drawable up, @Null Drawable down, @Null Drawable checked) {
      this.up = up;
      this.down = down;
      this.checked = checked;
    }
    
    public ButtonStyle(ButtonStyle style) {
      this.up = style.up;
      this.down = style.down;
      this.over = style.over;
      this.focused = style.focused;
      this.disabled = style.disabled;
      this.checked = style.checked;
      this.checkedOver = style.checkedOver;
      this.checkedDown = style.checkedDown;
      this.checkedFocused = style.checkedFocused;
      this.pressedOffsetX = style.pressedOffsetX;
      this.pressedOffsetY = style.pressedOffsetY;
      this.unpressedOffsetX = style.unpressedOffsetX;
      this.unpressedOffsetY = style.unpressedOffsetY;
      this.checkedOffsetX = style.checkedOffsetX;
      this.checkedOffsetY = style.checkedOffsetY;
    }
  }
}
