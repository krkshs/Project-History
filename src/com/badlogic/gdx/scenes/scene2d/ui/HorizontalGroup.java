package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.SnapshotArray;

public class HorizontalGroup extends WidgetGroup {
  private float prefWidth;
  
  private float prefHeight;
  
  private float lastPrefHeight;
  
  private boolean sizeInvalid = true;
  
  private FloatArray rowSizes;
  
  private int align = 8;
  
  private int rowAlign;
  
  private boolean reverse;
  
  private boolean round = true;
  
  private boolean wrap;
  
  private boolean wrapReverse;
  
  private boolean expand;
  
  private float space;
  
  private float wrapSpace;
  
  private float fill;
  
  private float padTop;
  
  private float padLeft;
  
  private float padBottom;
  
  private float padRight;
  
  public HorizontalGroup() {
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
    this.prefHeight = 0.0F;
    if (this.wrap) {
      this.prefWidth = 0.0F;
      if (this.rowSizes == null) {
        this.rowSizes = new FloatArray();
      } else
        this.rowSizes.clear(); 
      FloatArray rowSizes = this.rowSizes;
      float space = this.space, wrapSpace = this.wrapSpace;
      float pad = this.padLeft + this.padRight, groupWidth = getWidth() - pad, x = 0.0F, y = 0.0F, rowHeight = 0.0F;
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
          if (width > groupWidth)
            width = Math.max(groupWidth, layout.getMinWidth()); 
          height = layout.getPrefHeight();
        } else {
          width = child.getWidth();
          height = child.getHeight();
        } 
        float incrX = width + ((x > 0.0F) ? space : 0.0F);
        if (x + incrX > groupWidth && x > 0.0F) {
          rowSizes.add(x);
          rowSizes.add(rowHeight);
          this.prefWidth = Math.max(this.prefWidth, x + pad);
          if (y > 0.0F)
            y += wrapSpace; 
          y += rowHeight;
          rowHeight = 0.0F;
          x = 0.0F;
          incrX = width;
        } 
        x += incrX;
        rowHeight = Math.max(rowHeight, height);
      } 
      rowSizes.add(x);
      rowSizes.add(rowHeight);
      this.prefWidth = Math.max(this.prefWidth, x + pad);
      if (y > 0.0F)
        y += wrapSpace; 
      this.prefHeight = Math.max(this.prefHeight, y + rowHeight);
    } else {
      this.prefWidth = this.padLeft + this.padRight + this.space * (float)(n - 1);
      for (int i = 0; i < n; i++) {
        Actor child = children.get(i);
        if (child instanceof Layout) {
          Layout layout = (Layout)child;
          this.prefWidth += layout.getPrefWidth();
          this.prefHeight = Math.max(this.prefHeight, layout.getPrefHeight());
        } else {
          this.prefWidth += child.getWidth();
          this.prefHeight = Math.max(this.prefHeight, child.getHeight());
        } 
      } 
    } 
    this.prefHeight += this.padTop + this.padBottom;
    if (this.round) {
      this.prefWidth = (float)Math.round(this.prefWidth);
      this.prefHeight = (float)Math.round(this.prefHeight);
    } 
  }
  
  public void layout() {
    float startY;
    if (this.sizeInvalid)
      computeSize(); 
    if (this.wrap) {
      layoutWrapped();
      return;
    } 
    boolean round = this.round;
    int align = this.align;
    float space = this.space, padBottom = this.padBottom, fill = this.fill;
    float rowHeight = (this.expand ? getHeight() : this.prefHeight) - this.padTop - padBottom, x = this.padLeft;
    if ((align & 0x10) != 0) {
      x += getWidth() - this.prefWidth;
    } else if ((align & 0x8) == 0) {
      x += (getWidth() - this.prefWidth) / 2.0F;
    } 
    if ((align & 0x4) != 0) {
      startY = padBottom;
    } else if ((align & 0x2) != 0) {
      startY = getHeight() - this.padTop - rowHeight;
    } else
      startY = padBottom + (getHeight() - padBottom - this.padTop - rowHeight) / 2.0F; 
    align = this.rowAlign;
    SnapshotArray<Actor> children = getChildren();
    int i = 0, n = children.size, incr = 1;
    if (this.reverse) {
      i = n - 1;
      n = -1;
      incr = -1;
    } 
    for (int r = 0; i != n; i += incr) {
      float width, f1;
      Actor child = children.get(i);
      Layout layout = null;
      if (child instanceof Layout) {
        layout = (Layout)child;
        width = layout.getPrefWidth();
        f1 = layout.getPrefHeight();
      } else {
        width = child.getWidth();
        f1 = child.getHeight();
      } 
      if (fill > 0.0F)
        f1 = rowHeight * fill; 
      if (layout != null) {
        f1 = Math.max(f1, layout.getMinHeight());
        float maxHeight = layout.getMaxHeight();
        if (maxHeight > 0.0F && f1 > maxHeight)
          f1 = maxHeight; 
      } 
      float y = startY;
      if ((align & 0x2) != 0) {
        y += rowHeight - f1;
      } else if ((align & 0x4) == 0) {
        y += (rowHeight - f1) / 2.0F;
      } 
      if (round) {
        child.setBounds((float)Math.round(x), (float)Math.round(y), (float)Math.round(width), (float)Math.round(f1));
      } else
        child.setBounds(x, y, width, f1); 
      x += width + space;
      if (layout != null)
        layout.validate(); 
    } 
  }
  
  private void layoutWrapped() {
    float prefHeight = getPrefHeight();
    if (prefHeight != this.lastPrefHeight) {
      this.lastPrefHeight = prefHeight;
      invalidateHierarchy();
    } 
    int align = this.align;
    boolean round = this.round;
    float space = this.space, fill = this.fill, wrapSpace = this.wrapSpace;
    float maxWidth = this.prefWidth - this.padLeft - this.padRight;
    float rowY = prefHeight - this.padTop, groupWidth = getWidth(), xStart = this.padLeft, x = 0.0F, rowHeight = 0.0F, rowDir = -1.0F;
    if ((align & 0x2) != 0) {
      rowY += getHeight() - prefHeight;
    } else if ((align & 0x4) == 0) {
      rowY += (getHeight() - prefHeight) / 2.0F;
    } 
    if (this.wrapReverse) {
      rowY -= prefHeight + this.rowSizes.get(1);
      rowDir = 1.0F;
    } 
    if ((align & 0x10) != 0) {
      xStart += groupWidth - this.prefWidth;
    } else if ((align & 0x8) == 0) {
      xStart += (groupWidth - this.prefWidth) / 2.0F;
    } 
    groupWidth -= this.padRight;
    align = this.rowAlign;
    FloatArray rowSizes = this.rowSizes;
    SnapshotArray<Actor> children = getChildren();
    int i = 0, n = children.size, incr = 1;
    if (this.reverse) {
      i = n - 1;
      n = -1;
      incr = -1;
    } 
    for (int r = 0; i != n; i += incr) {
      float width, f1;
      Actor child = children.get(i);
      Layout layout = null;
      if (child instanceof Layout) {
        layout = (Layout)child;
        width = layout.getPrefWidth();
        if (width > groupWidth)
          width = Math.max(groupWidth, layout.getMinWidth()); 
        f1 = layout.getPrefHeight();
      } else {
        width = child.getWidth();
        f1 = child.getHeight();
      } 
      if (x + width > groupWidth || r == 0) {
        r = Math.min(r, rowSizes.size - 2);
        x = xStart;
        if ((align & 0x10) != 0) {
          x += maxWidth - rowSizes.get(r);
        } else if ((align & 0x8) == 0) {
          x += (maxWidth - rowSizes.get(r)) / 2.0F;
        } 
        rowHeight = rowSizes.get(r + 1);
        if (r > 0)
          rowY += wrapSpace * rowDir; 
        rowY += rowHeight * rowDir;
        r += 2;
      } 
      if (fill > 0.0F)
        f1 = rowHeight * fill; 
      if (layout != null) {
        f1 = Math.max(f1, layout.getMinHeight());
        float maxHeight = layout.getMaxHeight();
        if (maxHeight > 0.0F && f1 > maxHeight)
          f1 = maxHeight; 
      } 
      float y = rowY;
      if ((align & 0x2) != 0) {
        y += rowHeight - f1;
      } else if ((align & 0x4) == 0) {
        y += (rowHeight - f1) / 2.0F;
      } 
      if (round) {
        child.setBounds((float)Math.round(x), (float)Math.round(y), (float)Math.round(width), (float)Math.round(f1));
      } else
        child.setBounds(x, y, width, f1); 
      x += width + space;
      if (layout != null)
        layout.validate(); 
    } 
  }
  
  public float getPrefWidth() {
    if (this.wrap)
      return 0.0F; 
    if (this.sizeInvalid)
      computeSize(); 
    return this.prefWidth;
  }
  
  public float getPrefHeight() {
    if (this.sizeInvalid)
      computeSize(); 
    return this.prefHeight;
  }
  
  public void setRound(boolean round) {
    this.round = round;
  }
  
  public HorizontalGroup reverse() {
    this.reverse = true;
    return this;
  }
  
  public HorizontalGroup reverse(boolean reverse) {
    this.reverse = reverse;
    return this;
  }
  
  public boolean getReverse() {
    return this.reverse;
  }
  
  public HorizontalGroup wrapReverse() {
    this.wrapReverse = true;
    return this;
  }
  
  public HorizontalGroup wrapReverse(boolean wrapReverse) {
    this.wrapReverse = wrapReverse;
    return this;
  }
  
  public boolean getWrapReverse() {
    return this.wrapReverse;
  }
  
  public HorizontalGroup space(float space) {
    this.space = space;
    return this;
  }
  
  public float getSpace() {
    return this.space;
  }
  
  public HorizontalGroup wrapSpace(float wrapSpace) {
    this.wrapSpace = wrapSpace;
    return this;
  }
  
  public float getWrapSpace() {
    return this.wrapSpace;
  }
  
  public HorizontalGroup pad(float pad) {
    this.padTop = pad;
    this.padLeft = pad;
    this.padBottom = pad;
    this.padRight = pad;
    return this;
  }
  
  public HorizontalGroup pad(float top, float left, float bottom, float right) {
    this.padTop = top;
    this.padLeft = left;
    this.padBottom = bottom;
    this.padRight = right;
    return this;
  }
  
  public HorizontalGroup padTop(float padTop) {
    this.padTop = padTop;
    return this;
  }
  
  public HorizontalGroup padLeft(float padLeft) {
    this.padLeft = padLeft;
    return this;
  }
  
  public HorizontalGroup padBottom(float padBottom) {
    this.padBottom = padBottom;
    return this;
  }
  
  public HorizontalGroup padRight(float padRight) {
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
  
  public HorizontalGroup align(int align) {
    this.align = align;
    return this;
  }
  
  public HorizontalGroup center() {
    this.align = 1;
    return this;
  }
  
  public HorizontalGroup top() {
    this.align |= 0x2;
    this.align &= 0xFFFFFFFB;
    return this;
  }
  
  public HorizontalGroup left() {
    this.align |= 0x8;
    this.align &= 0xFFFFFFEF;
    return this;
  }
  
  public HorizontalGroup bottom() {
    this.align |= 0x4;
    this.align &= 0xFFFFFFFD;
    return this;
  }
  
  public HorizontalGroup right() {
    this.align |= 0x10;
    this.align &= 0xFFFFFFF7;
    return this;
  }
  
  public int getAlign() {
    return this.align;
  }
  
  public HorizontalGroup fill() {
    this.fill = 1.0F;
    return this;
  }
  
  public HorizontalGroup fill(float fill) {
    this.fill = fill;
    return this;
  }
  
  public float getFill() {
    return this.fill;
  }
  
  public HorizontalGroup expand() {
    this.expand = true;
    return this;
  }
  
  public HorizontalGroup expand(boolean expand) {
    this.expand = expand;
    return this;
  }
  
  public boolean getExpand() {
    return this.expand;
  }
  
  public HorizontalGroup grow() {
    this.expand = true;
    this.fill = 1.0F;
    return this;
  }
  
  public HorizontalGroup wrap() {
    this.wrap = true;
    return this;
  }
  
  public HorizontalGroup wrap(boolean wrap) {
    this.wrap = wrap;
    return this;
  }
  
  public boolean getWrap() {
    return this.wrap;
  }
  
  public HorizontalGroup rowAlign(int rowAlign) {
    this.rowAlign = rowAlign;
    return this;
  }
  
  public HorizontalGroup rowCenter() {
    this.rowAlign = 1;
    return this;
  }
  
  public HorizontalGroup rowTop() {
    this.rowAlign |= 0x2;
    this.rowAlign &= 0xFFFFFFFB;
    return this;
  }
  
  public HorizontalGroup rowLeft() {
    this.rowAlign |= 0x8;
    this.rowAlign &= 0xFFFFFFEF;
    return this;
  }
  
  public HorizontalGroup rowBottom() {
    this.rowAlign |= 0x4;
    this.rowAlign &= 0xFFFFFFFD;
    return this;
  }
  
  public HorizontalGroup rowRight() {
    this.rowAlign |= 0x10;
    this.rowAlign &= 0xFFFFFFF7;
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
