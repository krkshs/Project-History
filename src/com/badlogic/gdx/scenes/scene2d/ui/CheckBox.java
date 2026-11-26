package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.Scaling;

public class CheckBox extends TextButton {
  private Image image;
  
  private Cell imageCell;
  
  private CheckBoxStyle style;
  
  public CheckBox(@Null String text, Skin skin) {
    this(text, skin.<CheckBoxStyle>get(CheckBoxStyle.class));
  }
  
  public CheckBox(@Null String text, Skin skin, String styleName) {
    this(text, skin.<CheckBoxStyle>get(styleName, CheckBoxStyle.class));
  }
  
  public CheckBox(@Null String text, CheckBoxStyle style) {
    super(text, style);
    clearChildren();
    Label label = getLabel();
    this.imageCell = add(this.image = new Image(style.checkboxOff, Scaling.none));
    add(label);
    label.setAlignment(8);
    setSize(getPrefWidth(), getPrefHeight());
  }
  
  public void setStyle(Button.ButtonStyle style) {
    if (!(style instanceof CheckBoxStyle))
      throw new IllegalArgumentException("style must be a CheckBoxStyle."); 
    this.style = (CheckBoxStyle)style;
    super.setStyle(style);
  }
  
  public CheckBoxStyle getStyle() {
    return this.style;
  }
  
  public void draw(Batch batch, float parentAlpha) {
    Drawable checkbox = null;
    if (isDisabled())
      if (this.isChecked && this.style.checkboxOnDisabled != null) {
        checkbox = this.style.checkboxOnDisabled;
      } else
        checkbox = this.style.checkboxOffDisabled;  
    if (checkbox == null) {
      boolean over = (isOver() && !isDisabled());
      if (this.isChecked && this.style.checkboxOn != null) {
        checkbox = (over && this.style.checkboxOnOver != null) ? this.style.checkboxOnOver : this.style.checkboxOn;
      } else if (over && this.style.checkboxOver != null) {
        checkbox = this.style.checkboxOver;
      } else
        checkbox = this.style.checkboxOff; 
    } 
    this.image.setDrawable(checkbox);
    super.draw(batch, parentAlpha);
  }
  
  public Image getImage() {
    return this.image;
  }
  
  public Cell getImageCell() {
    return this.imageCell;
  }
  
  public static class CheckBoxStyle extends TextButton.TextButtonStyle {
    public Drawable checkboxOn;
    
    public Drawable checkboxOff;
    
    @Null
    public Drawable checkboxOnOver;
    
    @Null
    public Drawable checkboxOver;
    
    @Null
    public Drawable checkboxOnDisabled;
    
    @Null
    public Drawable checkboxOffDisabled;
    
    public CheckBoxStyle() {}
    
    public CheckBoxStyle(Drawable checkboxOff, Drawable checkboxOn, BitmapFont font, @Null Color fontColor) {
      this.checkboxOff = checkboxOff;
      this.checkboxOn = checkboxOn;
      this.font = font;
      this.fontColor = fontColor;
    }
    
    public CheckBoxStyle(CheckBoxStyle style) {
      super(style);
      this.checkboxOff = style.checkboxOff;
      this.checkboxOn = style.checkboxOn;
      this.checkboxOnOver = style.checkboxOnOver;
      this.checkboxOver = style.checkboxOver;
      this.checkboxOnDisabled = style.checkboxOnDisabled;
      this.checkboxOffDisabled = style.checkboxOffDisabled;
    }
  }
}
