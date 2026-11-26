package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.Scaling;

public class ImageTextButton extends Button {
  private final Image image;
  
  private Label label;
  
  private ImageTextButtonStyle style;
  
  public ImageTextButton(@Null String text, Skin skin) {
    this(text, skin.<ImageTextButtonStyle>get(ImageTextButtonStyle.class));
    setSkin(skin);
  }
  
  public ImageTextButton(@Null String text, Skin skin, String styleName) {
    this(text, skin.<ImageTextButtonStyle>get(styleName, ImageTextButtonStyle.class));
    setSkin(skin);
  }
  
  public ImageTextButton(@Null String text, ImageTextButtonStyle style) {
    super(style);
    this.style = style;
    defaults().space(3.0F);
    this.image = new Image();
    this.image.setScaling(Scaling.fit);
    this.label = new Label(text, new Label.LabelStyle(style.font, style.fontColor));
    this.label.setAlignment(1);
    add(this.image);
    add(this.label);
    setStyle(style);
    setSize(getPrefWidth(), getPrefHeight());
  }
  
  public void setStyle(Button.ButtonStyle style) {
    if (!(style instanceof ImageTextButtonStyle))
      throw new IllegalArgumentException("style must be a ImageTextButtonStyle."); 
    this.style = (ImageTextButtonStyle)style;
    super.setStyle(style);
    if (this.image != null)
      updateImage(); 
    if (this.label != null) {
      ImageTextButtonStyle textButtonStyle = (ImageTextButtonStyle)style;
      Label.LabelStyle labelStyle = this.label.getStyle();
      labelStyle.font = textButtonStyle.font;
      labelStyle.fontColor = getFontColor();
      this.label.setStyle(labelStyle);
    } 
  }
  
  public ImageTextButtonStyle getStyle() {
    return this.style;
  }
  
  @Null
  protected Drawable getImageDrawable() {
    if (isDisabled() && this.style.imageDisabled != null)
      return this.style.imageDisabled; 
    if (isPressed()) {
      if (isChecked() && this.style.imageCheckedDown != null)
        return this.style.imageCheckedDown; 
      if (this.style.imageDown != null)
        return this.style.imageDown; 
    } 
    if (isOver())
      if (isChecked()) {
        if (this.style.imageCheckedOver != null)
          return this.style.imageCheckedOver; 
      } else if (this.style.imageOver != null) {
        return this.style.imageOver;
      }  
    if (isChecked()) {
      if (this.style.imageChecked != null)
        return this.style.imageChecked; 
      if (isOver() && this.style.imageOver != null)
        return this.style.imageOver; 
    } 
    return this.style.imageUp;
  }
  
  protected void updateImage() {
    this.image.setDrawable(getImageDrawable());
  }
  
  @Null
  protected Color getFontColor() {
    if (isDisabled() && this.style.disabledFontColor != null)
      return this.style.disabledFontColor; 
    if (isPressed()) {
      if (isChecked() && this.style.checkedDownFontColor != null)
        return this.style.checkedDownFontColor; 
      if (this.style.downFontColor != null)
        return this.style.downFontColor; 
    } 
    if (isOver())
      if (isChecked()) {
        if (this.style.checkedOverFontColor != null)
          return this.style.checkedOverFontColor; 
      } else if (this.style.overFontColor != null) {
        return this.style.overFontColor;
      }  
    boolean focused = hasKeyboardFocus();
    if (isChecked()) {
      if (focused && this.style.checkedFocusedFontColor != null)
        return this.style.checkedFocusedFontColor; 
      if (this.style.checkedFontColor != null)
        return this.style.checkedFontColor; 
      if (isOver() && this.style.overFontColor != null)
        return this.style.overFontColor; 
    } 
    if (focused && this.style.focusedFontColor != null)
      return this.style.focusedFontColor; 
    return this.style.fontColor;
  }
  
  public void draw(Batch batch, float parentAlpha) {
    updateImage();
    (this.label.getStyle()).fontColor = getFontColor();
    super.draw(batch, parentAlpha);
  }
  
  public Image getImage() {
    return this.image;
  }
  
  public Cell getImageCell() {
    return getCell(this.image);
  }
  
  public void setLabel(Label label) {
    getLabelCell().setActor(label);
    this.label = label;
  }
  
  public Label getLabel() {
    return this.label;
  }
  
  public Cell getLabelCell() {
    return getCell(this.label);
  }
  
  public void setText(CharSequence text) {
    this.label.setText(text);
  }
  
  public CharSequence getText() {
    return this.label.getText();
  }
  
  public String toString() {
    String name = getName();
    if (name != null)
      return name; 
    String className = getClass().getName();
    int dotIndex = className.lastIndexOf('.');
    if (dotIndex != -1)
      className = className.substring(dotIndex + 1); 
    return ((className.indexOf('$') != -1) ? "ImageTextButton " : "") + className + ": " + this.image.getDrawable() + " " + 
      this.label.getText();
  }
  
  public static class ImageTextButtonStyle extends TextButton.TextButtonStyle {
    @Null
    public Drawable imageUp;
    
    @Null
    public Drawable imageDown;
    
    @Null
    public Drawable imageOver;
    
    @Null
    public Drawable imageDisabled;
    
    @Null
    public Drawable imageChecked;
    
    @Null
    public Drawable imageCheckedDown;
    
    @Null
    public Drawable imageCheckedOver;
    
    public ImageTextButtonStyle() {}
    
    public ImageTextButtonStyle(@Null Drawable up, @Null Drawable down, @Null Drawable checked, BitmapFont font) {
      super(up, down, checked, font);
    }
    
    public ImageTextButtonStyle(ImageTextButtonStyle style) {
      super(style);
      this.imageUp = style.imageUp;
      this.imageDown = style.imageDown;
      this.imageOver = style.imageOver;
      this.imageDisabled = style.imageDisabled;
      this.imageChecked = style.imageChecked;
      this.imageCheckedDown = style.imageCheckedDown;
      this.imageCheckedOver = style.imageCheckedOver;
    }
    
    public ImageTextButtonStyle(TextButton.TextButtonStyle style) {
      super(style);
    }
  }
}
