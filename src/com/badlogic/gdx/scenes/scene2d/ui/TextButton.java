package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Null;

public class TextButton extends Button {
  private Label label;
  
  private TextButtonStyle style;
  
  public TextButton(@Null String text, Skin skin) {
    this(text, skin.<TextButtonStyle>get(TextButtonStyle.class));
    setSkin(skin);
  }
  
  public TextButton(@Null String text, Skin skin, String styleName) {
    this(text, skin.<TextButtonStyle>get(styleName, TextButtonStyle.class));
    setSkin(skin);
  }
  
  public TextButton(@Null String text, TextButtonStyle style) {
    setStyle(style);
    this.label = new Label(text, new Label.LabelStyle(style.font, style.fontColor));
    this.label.setAlignment(1);
    add(this.label).expand().fill();
    setSize(getPrefWidth(), getPrefHeight());
  }
  
  public void setStyle(Button.ButtonStyle style) {
    if (style == null)
      throw new NullPointerException("style cannot be null"); 
    if (!(style instanceof TextButtonStyle))
      throw new IllegalArgumentException("style must be a TextButtonStyle."); 
    this.style = (TextButtonStyle)style;
    super.setStyle(style);
    if (this.label != null) {
      TextButtonStyle textButtonStyle = (TextButtonStyle)style;
      Label.LabelStyle labelStyle = this.label.getStyle();
      labelStyle.font = textButtonStyle.font;
      labelStyle.fontColor = textButtonStyle.fontColor;
      this.label.setStyle(labelStyle);
    } 
  }
  
  public TextButtonStyle getStyle() {
    return this.style;
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
    (this.label.getStyle()).fontColor = getFontColor();
    super.draw(batch, parentAlpha);
  }
  
  public void setLabel(Label label) {
    if (label == null)
      throw new IllegalArgumentException("label cannot be null."); 
    getLabelCell().<Label>setActor(label);
    this.label = label;
  }
  
  public Label getLabel() {
    return this.label;
  }
  
  public Cell<Label> getLabelCell() {
    return getCell(this.label);
  }
  
  public void setText(@Null String text) {
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
    return ((className.indexOf('$') != -1) ? "TextButton " : "") + className + ": " + this.label.getText();
  }
  
  public static class TextButtonStyle extends Button.ButtonStyle {
    public BitmapFont font;
    
    @Null
    public Color fontColor;
    
    @Null
    public Color downFontColor;
    
    @Null
    public Color overFontColor;
    
    @Null
    public Color focusedFontColor;
    
    @Null
    public Color disabledFontColor;
    
    @Null
    public Color checkedFontColor;
    
    @Null
    public Color checkedDownFontColor;
    
    @Null
    public Color checkedOverFontColor;
    
    @Null
    public Color checkedFocusedFontColor;
    
    public TextButtonStyle() {}
    
    public TextButtonStyle(@Null Drawable up, @Null Drawable down, @Null Drawable checked, @Null BitmapFont font) {
      super(up, down, checked);
      this.font = font;
    }
    
    public TextButtonStyle(TextButtonStyle style) {
      super(style);
      this.font = style.font;
      if (style.fontColor != null)
        this.fontColor = new Color(style.fontColor); 
      if (style.downFontColor != null)
        this.downFontColor = new Color(style.downFontColor); 
      if (style.overFontColor != null)
        this.overFontColor = new Color(style.overFontColor); 
      if (style.focusedFontColor != null)
        this.focusedFontColor = new Color(style.focusedFontColor); 
      if (style.disabledFontColor != null)
        this.disabledFontColor = new Color(style.disabledFontColor); 
      if (style.checkedFontColor != null)
        this.checkedFontColor = new Color(style.checkedFontColor); 
      if (style.checkedDownFontColor != null)
        this.checkedDownFontColor = new Color(style.checkedDownFontColor); 
      if (style.checkedOverFontColor != null)
        this.checkedOverFontColor = new Color(style.checkedOverFontColor); 
      if (style.checkedFocusedFontColor != null)
        this.checkedFocusedFontColor = new Color(style.checkedFocusedFontColor); 
    }
  }
}
