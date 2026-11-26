package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.StringBuilder;

public class Label extends Widget {
  private static final Color tempColor = new Color();
  
  private static final GlyphLayout prefSizeLayout = new GlyphLayout();
  
  private LabelStyle style;
  
  private final GlyphLayout layout = new GlyphLayout();
  
  private final Vector2 prefSize = new Vector2();
  
  private final StringBuilder text = new StringBuilder();
  
  private int intValue = Integer.MIN_VALUE;
  
  private BitmapFontCache cache;
  
  private int labelAlign = 8;
  
  private int lineAlign = 8;
  
  private boolean wrap;
  
  private float lastPrefHeight;
  
  private boolean prefSizeInvalid = true;
  
  private float fontScaleX = 1.0F;
  
  private float fontScaleY = 1.0F;
  
  private boolean fontScaleChanged = false;
  
  @Null
  private String ellipsis;
  
  public Label(@Null CharSequence text, Skin skin) {
    this(text, skin.<LabelStyle>get(LabelStyle.class));
  }
  
  public Label(@Null CharSequence text, Skin skin, String styleName) {
    this(text, skin.<LabelStyle>get(styleName, LabelStyle.class));
  }
  
  public Label(@Null CharSequence text, Skin skin, String fontName, Color color) {
    this(text, new LabelStyle(skin.getFont(fontName), color));
  }
  
  public Label(@Null CharSequence text, Skin skin, String fontName, String colorName) {
    this(text, new LabelStyle(skin.getFont(fontName), skin.getColor(colorName)));
  }
  
  public Label(@Null CharSequence text, LabelStyle style) {
    if (text != null)
      this.text.append(text); 
    setStyle(style);
    if (text != null && text.length() > 0)
      setSize(getPrefWidth(), getPrefHeight()); 
  }
  
  public void setStyle(LabelStyle style) {
    if (style == null)
      throw new IllegalArgumentException("style cannot be null."); 
    if (style.font == null)
      throw new IllegalArgumentException("Missing LabelStyle font."); 
    this.style = style;
    this.cache = style.font.newFontCache();
    invalidateHierarchy();
  }
  
  public LabelStyle getStyle() {
    return this.style;
  }
  
  public boolean setText(int value) {
    if (this.intValue == value)
      return false; 
    this.text.clear();
    this.text.append(value);
    this.intValue = value;
    invalidateHierarchy();
    return true;
  }
  
  public void setText(@Null CharSequence newText) {
    if (newText == null) {
      if (this.text.length == 0)
        return; 
      this.text.clear();
    } else if (newText instanceof StringBuilder) {
      if (this.text.equals(newText))
        return; 
      this.text.clear();
      this.text.append((StringBuilder)newText);
    } else {
      if (textEquals(newText))
        return; 
      this.text.clear();
      this.text.append(newText);
    } 
    this.intValue = Integer.MIN_VALUE;
    invalidateHierarchy();
  }
  
  public boolean textEquals(CharSequence other) {
    int length = this.text.length;
    char[] chars = this.text.chars;
    if (length != other.length())
      return false; 
    for (int i = 0; i < length; i++) {
      if (chars[i] != other.charAt(i))
        return false; 
    } 
    return true;
  }
  
  public StringBuilder getText() {
    return this.text;
  }
  
  public void invalidate() {
    super.invalidate();
    this.prefSizeInvalid = true;
  }
  
  private void scaleAndComputePrefSize() {
    BitmapFont font = this.cache.getFont();
    float oldScaleX = font.getScaleX();
    float oldScaleY = font.getScaleY();
    if (this.fontScaleChanged)
      font.getData().setScale(this.fontScaleX, this.fontScaleY); 
    computePrefSize();
    if (this.fontScaleChanged)
      font.getData().setScale(oldScaleX, oldScaleY); 
  }
  
  private void computePrefSize() {
    this.prefSizeInvalid = false;
    GlyphLayout prefSizeLayout = Label.prefSizeLayout;
    if (this.wrap && this.ellipsis == null) {
      float width = getWidth();
      if (this.style.background != null)
        width = Math.max(width, this.style.background.getMinWidth()) - this.style.background.getLeftWidth() - this.style.background.getRightWidth(); 
      prefSizeLayout.setText(this.cache.getFont(), this.text, Color.WHITE, width, 8, true);
    } else
      prefSizeLayout.setText(this.cache.getFont(), this.text); 
    this.prefSize.set(prefSizeLayout.width, prefSizeLayout.height);
  }
  
  public void layout() {
    float textWidth, textHeight;
    BitmapFont font = this.cache.getFont();
    float oldScaleX = font.getScaleX();
    float oldScaleY = font.getScaleY();
    if (this.fontScaleChanged)
      font.getData().setScale(this.fontScaleX, this.fontScaleY); 
    boolean wrap = (this.wrap && this.ellipsis == null);
    if (wrap) {
      float prefHeight = getPrefHeight();
      if (prefHeight != this.lastPrefHeight) {
        this.lastPrefHeight = prefHeight;
        invalidateHierarchy();
      } 
    } 
    float width = getWidth(), height = getHeight();
    Drawable background = this.style.background;
    float x = 0.0F, y = 0.0F;
    if (background != null) {
      x = background.getLeftWidth();
      y = background.getBottomHeight();
      width -= background.getLeftWidth() + background.getRightWidth();
      height -= background.getBottomHeight() + background.getTopHeight();
    } 
    GlyphLayout layout = this.layout;
    if (wrap || this.text.indexOf("\n") != -1) {
      layout.setText(font, this.text, 0, this.text.length, Color.WHITE, width, this.lineAlign, wrap, this.ellipsis);
      textWidth = layout.width;
      textHeight = layout.height;
      if ((this.labelAlign & 0x8) == 0)
        if ((this.labelAlign & 0x10) != 0) {
          x += width - textWidth;
        } else
          x += (width - textWidth) / 2.0F;  
    } else {
      textWidth = width;
      textHeight = (font.getData()).capHeight;
    } 
    if ((this.labelAlign & 0x2) != 0) {
      y += this.cache.getFont().isFlipped() ? 0.0F : (height - textHeight);
      y += this.style.font.getDescent();
    } else if ((this.labelAlign & 0x4) != 0) {
      y += this.cache.getFont().isFlipped() ? (height - textHeight) : 0.0F;
      y -= this.style.font.getDescent();
    } else
      y += (height - textHeight) / 2.0F; 
    if (!this.cache.getFont().isFlipped())
      y += textHeight; 
    layout.setText(font, this.text, 0, this.text.length, Color.WHITE, textWidth, this.lineAlign, wrap, this.ellipsis);
    this.cache.setText(layout, x, y);
    if (this.fontScaleChanged)
      font.getData().setScale(oldScaleX, oldScaleY); 
  }
  
  public void draw(Batch batch, float parentAlpha) {
    validate();
    Color color = tempColor.set(getColor());
    color.a *= parentAlpha;
    if (this.style.background != null) {
      batch.setColor(color.r, color.g, color.b, color.a);
      this.style.background.draw(batch, getX(), getY(), getWidth(), getHeight());
    } 
    if (this.style.fontColor != null)
      color.mul(this.style.fontColor); 
    this.cache.tint(color);
    this.cache.setPosition(getX(), getY());
    this.cache.draw(batch);
  }
  
  public float getPrefWidth() {
    if (this.wrap)
      return 0.0F; 
    if (this.prefSizeInvalid)
      scaleAndComputePrefSize(); 
    float width = this.prefSize.x;
    Drawable background = this.style.background;
    if (background != null)
      width = Math.max(width + background.getLeftWidth() + background.getRightWidth(), background.getMinWidth()); 
    return width;
  }
  
  public float getPrefHeight() {
    if (this.prefSizeInvalid)
      scaleAndComputePrefSize(); 
    float descentScaleCorrection = 1.0F;
    if (this.fontScaleChanged)
      descentScaleCorrection = this.fontScaleY / this.style.font.getScaleY(); 
    float height = this.prefSize.y - this.style.font.getDescent() * descentScaleCorrection * 2.0F;
    Drawable background = this.style.background;
    if (background != null)
      height = Math.max(height + background.getTopHeight() + background.getBottomHeight(), background.getMinHeight()); 
    return height;
  }
  
  public GlyphLayout getGlyphLayout() {
    return this.layout;
  }
  
  public void setWrap(boolean wrap) {
    this.wrap = wrap;
    invalidateHierarchy();
  }
  
  public boolean getWrap() {
    return this.wrap;
  }
  
  public int getLabelAlign() {
    return this.labelAlign;
  }
  
  public int getLineAlign() {
    return this.lineAlign;
  }
  
  public void setAlignment(int alignment) {
    setAlignment(alignment, alignment);
  }
  
  public void setAlignment(int labelAlign, int lineAlign) {
    this.labelAlign = labelAlign;
    if ((lineAlign & 0x8) != 0) {
      this.lineAlign = 8;
    } else if ((lineAlign & 0x10) != 0) {
      this.lineAlign = 16;
    } else
      this.lineAlign = 1; 
    invalidate();
  }
  
  public void setFontScale(float fontScale) {
    setFontScale(fontScale, fontScale);
  }
  
  public void setFontScale(float fontScaleX, float fontScaleY) {
    this.fontScaleChanged = true;
    this.fontScaleX = fontScaleX;
    this.fontScaleY = fontScaleY;
    invalidateHierarchy();
  }
  
  public float getFontScaleX() {
    return this.fontScaleX;
  }
  
  public void setFontScaleX(float fontScaleX) {
    setFontScale(fontScaleX, this.fontScaleY);
  }
  
  public float getFontScaleY() {
    return this.fontScaleY;
  }
  
  public void setFontScaleY(float fontScaleY) {
    setFontScale(this.fontScaleX, fontScaleY);
  }
  
  public void setEllipsis(@Null String ellipsis) {
    this.ellipsis = ellipsis;
  }
  
  public void setEllipsis(boolean ellipsis) {
    if (ellipsis) {
      this.ellipsis = "...";
    } else
      this.ellipsis = null; 
  }
  
  protected BitmapFontCache getBitmapFontCache() {
    return this.cache;
  }
  
  public String toString() {
    String name = getName();
    if (name != null)
      return name; 
    String className = getClass().getName();
    int dotIndex = className.lastIndexOf('.');
    if (dotIndex != -1)
      className = className.substring(dotIndex + 1); 
    return ((className.indexOf('$') != -1) ? "Label " : "") + className + ": " + this.text;
  }
  
  public static class LabelStyle {
    public BitmapFont font;
    
    @Null
    public Color fontColor;
    
    @Null
    public Drawable background;
    
    public LabelStyle() {}
    
    public LabelStyle(BitmapFont font, @Null Color fontColor) {
      this.font = font;
      this.fontColor = fontColor;
    }
    
    public LabelStyle(LabelStyle style) {
      this.font = style.font;
      if (style.fontColor != null)
        this.fontColor = new Color(style.fontColor); 
      this.background = style.background;
    }
  }
}
