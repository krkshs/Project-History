package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.SnapshotArray;

public class VerticalGroup extends WidgetGroup {
  private float prefWidth;
  
  private float prefHeight;
  
  private float lastPrefWidth;
  
  private boolean sizeInvalid = true;
  
  private FloatArray columnSizes;
  
  private int align = 2;
  
  private int columnAlign;
  
  private boolean reverse;
  
  private boolean round = true;
  
  private boolean wrap;
  
  private boolean expand;
  
  private float space;
  
  private float wrapSpace;
  
  private float fill;
  
  private float padTop;
  
  private float padLeft;
  
  private float padBottom;
  
  private float padRight;
  
  public VerticalGroup() {
    setTouchable(Touchable.childrenOnly);
  }
  
  public void invalidate() {
    super.invalidate();
    this.sizeInvalid = true;
  }
  
  private void computeSize() {
    this.sizeInvalid = false;
    SnapshotArray<Actor> children = getChildren();
    int n = children.size;
    this.prefWidth = 0.0F;
    if (this.wrap) {
      this.prefHeight = 0.0F;
      if (this.columnSizes == null) {
        this.columnSizes = new FloatArray();
      } else
        this.columnSizes.clear(); 
      FloatArray columnSizes = this.columnSizes;
      float space = this.space, wrapSpace = this.wrapSpace;
      float pad = this.padTop + this.padBottom, groupHeight = getHeight() - pad, x = 0.0F, y = 0.0F, columnWidth = 0.0F;
      int i = 0, incr = 1;
      if (this.reverse) {
        i = n - 1;
        n = -1;
        incr = -1;
      } 
      for (; i != n; i += incr) {
        float width, height;
        Actor child = children.get(i);
        if (child instanceof Layout) {
          Layout layout = (Layout)child;
          width = layout.getPrefWidth();
          height = layout.getPrefHeight();
          if (height > groupHeight)
            height = Math.max(groupHeight, layout.getMinHeight()); 
        } else {
          width = child.getWidth();
          height = child.getHeight();
        } 
        float incrY = height + ((y > 0.0F) ? space : 0.0F);
        if (y + incrY > groupHeight && y > 0.0F) {
          columnSizes.add(y);
          columnSizes.add(columnWidth);
          this.prefHeight = Math.max(this.prefHeight, y + pad);
          if (x > 0.0F)
            x += wrapSpace; 
          x += columnWidth;
          columnWidth = 0.0F;
          y = 0.0F;
          incrY = height;
        } 
        y += incrY;
        columnWidth = Math.max(columnWidth, width);
      } 
      columnSizes.add(y);
      columnSizes.add(columnWidth);
      this.prefHeight = Math.max(this.prefHeight, y + pad);
      if (x > 0.0F)
        x += wrapSpace; 
      this.prefWidth = Math.max(this.prefWidth, x + columnWidth);
    } else {
      this.prefHeight = this.padTop + this.padBottom + this.space * (float)(n - 1);
      for (int i = 0; i < n; i++) {
        Actor child = children.get(i);
        if (child instanceof Layout) {
          Layout layout = (Layout)child;
          this.prefWidth = Math.max(this.prefWidth, layout.getPrefWidth());
          this.prefHeight += layout.getPrefHeight();
        } else {
          this.prefWidth = Math.max(this.prefWidth, child.getWidth());
          this.prefHeight += child.getHeight();
        } 
      } 
    } 
    this.prefWidth += this.padLeft + this.padRight;
    if (this.round) {
      this.prefWidth = (float)Math.round(this.prefWidth);
      this.prefHeight = (float)Math.round(this.prefHeight);
    } 
  }
  
  public void layout() {
    float startX;
    if (this.sizeInvalid)
      computeSize(); 
    if (this.wrap) {
      layoutWrapped();
      return;
    } 
    boolean round = this.round;
    int align = this.align;
    float space = this.space, padLeft = this.padLeft, fill = this.fill;
    float columnWidth = (this.expand ? getWidth() : this.prefWidth) - padLeft - this.padRight, y = this.prefHeight - this.padTop + space;
    if ((align & 0x2) != 0) {
      y += getHeight() - this.prefHeight;
    } else if ((align & 0x4) == 0) {
      y += (getHeight() - this.prefHeight) / 2.0F;
    } 
    if ((align & 0x8) != 0) {
      startX = padLeft;
    } else if ((align & 0x10) != 0) {
      startX = getWidth() - this.padRight - columnWidth;
    } else
      startX = padLeft + (getWidth() - padLeft - this.padRight - columnWidth) / 2.0F; 
    align = this.columnAlign;
    SnapshotArray<Actor> children = getChildren();
    int i = 0, n = children.size, incr = 1;
    if (this.reverse) {
      i = n - 1;
      n = -1;
      incr = -1;
    } 
    for (int r = 0; i != n; i += incr) {
      float f1, height;
      Actor child = children.get(i);
      Layout layout = null;
      if (child instanceof Layout) {
        layout = (Layout)child;
        f1 = layout.getPrefWidth();
        height = layout.getPrefHeight();
      } else {
        f1 = child.getWidth();
        height = child.getHeight();
      } 
      if (fill > 0.0F)
        f1 = columnWidth * fill; 
      if (layout != null) {
        f1 = Math.max(f1, layout.getMinWidth());
        float maxWidth = layout.getMaxWidth();
        if (maxWidth > 0.0F && f1 > maxWidth)
          f1 = maxWidth; 
      } 
      float x = startX;
      if ((align & 0x10) != 0) {
        x += columnWidth - f1;
      } else if ((align & 0x8) == 0) {
        x += (columnWidth - f1) / 2.0F;
      } 
      y -= height + space;
      if (round) {
        child.setBounds((float)Math.round(x), (float)Math.round(y), (float)Math.round(f1), (float)Math.round(height));
      } else
        child.setBounds(x, y, f1, height); 
      if (layout != null)
        layout.validate(); 
    } 
  }
  
  private void layoutWrapped() {
    float prefWidth = getPrefWidth();
    if (prefWidth != this.lastPrefWidth) {
      this.lastPrefWidth = prefWidth;
      invalidateHierarchy();
    } 
    int align = this.align;
    boolean round = this.round;
    float space = this.space, padLeft = this.padLeft, fill = this.fill, wrapSpace = this.wrapSpace;
    float maxHeight = this.prefHeight - this.padTop - this.padBottom;
    float columnX = padLeft, groupHeight = getHeight();
    float yStart = this.prefHeight - this.padTop + space, y = 0.0F, columnWidth = 0.0F;
    if ((align & 0x10) != 0) {
      columnX += getWidth() - prefWidth;
    } else if ((align & 0x8) == 0) {
      columnX += (getWidth() - prefWidth) / 2.0F;
    } 
    if ((align & 0x2) != 0) {
      yStart += groupHeight - this.prefHeight;
    } else if ((align & 0x4) == 0) {
      yStart += (groupHeight - this.prefHeight) / 2.0F;
    } 
    groupHeight -= this.padTop;
    align = this.columnAlign;
    FloatArray columnSizes = this.columnSizes;
    SnapshotArray<Actor> children = getChildren();
    int i = 0, n = children.size, incr = 1;
    if (this.reverse) {
      i = n - 1;
      n = -1;
      incr = -1;
    } 
    for (int r = 0; i != n; i += incr) {
      float f1, height;
      Actor child = children.get(i);
      Layout layout = null;
      if (child instanceof Layout) {
        layout = (Layout)child;
        f1 = layout.getPrefWidth();
        height = layout.getPrefHeight();
        if (height > groupHeight)
          height = Math.max(groupHeight, layout.getMinHeight()); 
      } else {
        f1 = child.getWidth();
        height = child.getHeight();
      } 
      if (y - height - space < this.padBottom || r == 0) {
        r = Math.min(r, columnSizes.size - 2);
        y = yStart;
        if ((align & 0x4) != 0) {
          y -= maxHeight - columnSizes.get(r);
        } else if ((align & 0x2) == 0) {
          y -= (maxHeight - columnSizes.get(r)) / 2.0F;
        } 
        if (r > 0) {
          columnX += wrapSpace;
          columnX += columnWidth;
        } 
        columnWidth = columnSizes.get(r + 1);
        r += 2;
      } 
      if (fill > 0.0F)
        f1 = columnWidth * fill; 
      if (layout != null) {
        f1 = Math.max(f1, layout.getMinWidth());
        float maxWidth = layout.getMaxWidth();
        if (maxWidth > 0.0F && f1 > maxWidth)
          f1 = maxWidth; 
      } 
      float x = columnX;
      if ((align & 0x10) != 0) {
        x += columnWidth - f1;
      } else if ((align & 0x8) == 0) {
        x += (columnWidth - f1) / 2.0F;
      } 
      y -= height + space;
      if (round) {
        child.setBounds((float)Math.round(x), (float)Math.round(y), (float)Math.round(f1), (float)Math.round(height));
      } else
        child.setBounds(x, y, f1, height); 
      if (layout != null)
        layout.validate(); 
    } 
  }
  
  public float getPrefWidth() {
    if (this.sizeInvalid)
      computeSize(); 
    return this.prefWidth;
  }
  
  public float getPrefHeight() {
    if (this.wrap)
      return 0.0F; 
    if (this.sizeInvalid)
      computeSize(); 
    return this.prefHeight;
  }
  
  public void setRound(boolean round) {
    this.round = round;
  }
  
  public VerticalGroup reverse() {
    this.reverse = true;
    return this;
  }
  
  public VerticalGroup reverse(boolean reverse) {
    this.reverse = reverse;
    return this;
  }
  
  public boolean getReverse() {
    return this.reverse;
  }
  
  public VerticalGroup space(float space) {
    this.space = space;
    return this;
  }
  
  public float getSpace() {
    return this.space;
  }
  
  public VerticalGroup wrapSpace(float wrapSpace) {
    this.wrapSpace = wrapSpace;
    return this;
  }
  
  public float getWrapSpace() {
    return this.wrapSpace;
  }
  
  public VerticalGroup pad(float pad) {
    this.padTop = pad;
    this.padLeft = pad;
    this.padBottom = pad;
    this.padRight = pad;
    return this;
  }
  
  public VerticalGroup pad(float top, float left, float bottom, float right) {
    this.padTop = top;
    this.padLeft = left;
    this.padBottom = bottom;
    this.padRight = right;
    return this;
  }
  
  public VerticalGroup padTop(float padTop) {
    this.padTop = padTop;
    return this;
  }
  
  public VerticalGroup padLeft(float padLeft) {
    this.padLeft = padLeft;
    return this;
  }
  
  public VerticalGroup padBottom(float padBottom) {
    this.padBottom = padBottom;
    return this;
  }
  
  public VerticalGroup padRight(float padRight) {
    this.padRight = padRight;
    return this;
  }
  
  public float getPadTop() {
    return this.padTop;
  }
  
  public float getPadLeft() {
    return this.padLeft;
  }
  
  public float getPadBottom() {
    return this.padBottom;
  }
  
  public float getPadRight() {
    return this.padRight;
  }
  
  public VerticalGroup align(int align) {
    this.align = align;
    return this;
  }
  
  public VerticalGroup center() {
    this.align = 1;
    return this;
  }
  
  public VerticalGroup top() {
    this.align |= 0x2;
    this.align &= 0xFFFFFFFB;
    return this;
  }
  
  public VerticalGroup left() {
    this.align |= 0x8;
    this.align &= 0xFFFFFFEF;
    return this;
  }
  
  public VerticalGroup bottom() {
    this.align |= 0x4;
    this.align &= 0xFFFFFFFD;
    return this;
  }
  
  public VerticalGroup right() {
    this.align |= 0x10;
    this.align &= 0xFFFFFFF7;
    return this;
  }
  
  public int getAlign() {
    return this.align;
  }
  
  public VerticalGroup fill() {
    this.fill = 1.0F;
    return this;
  }
  
  public VerticalGroup fill(float fill) {
    this.fill = fill;
    return this;
  }
  
  public float getFill() {
    return this.fill;
  }
  
  public VerticalGroup expand() {
    this.expand = true;
    return this;
  }
  
  public VerticalGroup expand(boolean expand) {
    this.expand = expand;
    return this;
  }
  
  public boolean getExpand() {
    return this.expand;
  }
  
  public VerticalGroup grow() {
    this.expand = true;
    this.fill = 1.0F;
    return this;
  }
  
  public VerticalGroup wrap() {
    this.wrap = true;
    return this;
  }
  
  public VerticalGroup wrap(boolean wrap) {
    this.wrap = wrap;
    return this;
  }
  
  public boolean getWrap() {
    return this.wrap;
  }
  
  public VerticalGroup columnAlign(int columnAlign) {
    this.columnAlign = columnAlign;
    return this;
  }
  
  public VerticalGroup columnCenter() {
    this.columnAlign = 1;
    return this;
  }
  
  public VerticalGroup columnTop() {
    this.columnAlign |= 0x2;
    this.columnAlign &= 0xFFFFFFFB;
    return this;
  }
  
  public VerticalGroup columnLeft() {
    this.columnAlign |= 0x8;
    this.columnAlign &= 0xFFFFFFEF;
    return this;
  }
  
  public VerticalGroup columnBottom() {
    this.columnAlign |= 0x4;
    this.columnAlign &= 0xFFFFFFFD;
    return this;
  }
  
  public VerticalGroup columnRight() {
    this.columnAlign |= 0x10;
    this.columnAlign &= 0xFFFFFFF7;
    return this;
  }
  
  protected void drawDebugBounds(ShapeRenderer shapes) {
    super.drawDebugBounds(shapes);
    if (!getDebug())
      return; 
    shapes.set(ShapeRenderer.ShapeType.Line);
    if (getStage() != null)
      shapes.setColor(getStage().getDebugColor()); 
    shapes.rect(getX() + this.padLeft, getY() + this.padBottom, getOriginX(), getOriginY(), getWidth() - this.padLeft - this.padRight, 
        getHeight() - this.padBottom - this.padTop, getScaleX(), getScaleY(), getRotation());
  }
}
