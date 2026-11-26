package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import java.util.Arrays;

public class Table extends WidgetGroup {
  public static Color debugTableColor = new Color(0.0F, 0.0F, 1.0F, 1.0F);
  
  public static Color debugCellColor = new Color(1.0F, 0.0F, 0.0F, 1.0F);
  
  public static Color debugActorColor = new Color(0.0F, 1.0F, 0.0F, 1.0F);
  
  static final Pool<Cell> cellPool = new Pool<Cell>() {
      protected Cell newObject() {
        return new Cell();
      }
    };
  
  private static float[] columnWeightedWidth;
  
  private static float[] rowWeightedHeight;
  
  private int columns;
  
  private int rows;
  
  private boolean implicitEndRow;
  
  private final Array<Cell> cells = new Array<>(4);
  
  private final Cell cellDefaults;
  
  private final Array<Cell> columnDefaults = new Array<>(2);
  
  private Cell rowDefaults;
  
  private boolean sizeInvalid = true;
  
  private float[] columnMinWidth;
  
  private float[] rowMinHeight;
  
  private float[] columnPrefWidth;
  
  private float[] rowPrefHeight;
  
  private float tableMinWidth;
  
  private float tableMinHeight;
  
  private float tablePrefWidth;
  
  private float tablePrefHeight;
  
  private float[] columnWidth;
  
  private float[] rowHeight;
  
  private float[] expandWidth;
  
  private float[] expandHeight;
  
  Value padTop = backgroundTop;
  
  Value padLeft = backgroundLeft;
  
  Value padBottom = backgroundBottom;
  
  Value padRight = backgroundRight;
  
  int align = 1;
  
  Debug debug = Debug.none;
  
  Array<DebugRect> debugRects;
  
  @Null
  Drawable background;
  
  private boolean clip;
  
  @Null
  private Skin skin;
  
  boolean round = true;
  
  public Table() {
    this(null);
  }
  
  public Table(@Null Skin skin) {
    this.skin = skin;
    this.cellDefaults = obtainCell();
    setTransform(false);
    setTouchable(Touchable.childrenOnly);
  }
  
  private Cell obtainCell() {
    Cell cell = cellPool.obtain();
    cell.setTable(this);
    return cell;
  }
  
  public void draw(Batch batch, float parentAlpha) {
    validate();
    if (isTransform()) {
      applyTransform(batch, computeTransform());
      drawBackground(batch, parentAlpha, 0.0F, 0.0F);
      if (this.clip) {
        batch.flush();
        float padLeft = this.padLeft.get(this), padBottom = this.padBottom.get(this);
        if (clipBegin(padLeft, padBottom, getWidth() - padLeft - this.padRight.get(this), 
            getHeight() - padBottom - this.padTop.get(this))) {
          drawChildren(batch, parentAlpha);
          batch.flush();
          clipEnd();
        } 
      } else
        drawChildren(batch, parentAlpha); 
      resetTransform(batch);
    } else {
      drawBackground(batch, parentAlpha, getX(), getY());
      super.draw(batch, parentAlpha);
    } 
  }
  
  protected void drawBackground(Batch batch, float parentAlpha, float x, float y) {
    if (this.background == null)
      return; 
    Color color = getColor();
    batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
    this.background.draw(batch, x, y, getWidth(), getHeight());
  }
  
  public void setBackground(String drawableName) {
    if (this.skin == null)
      throw new IllegalStateException("Table must have a skin set to use this method."); 
    setBackground(this.skin.getDrawable(drawableName));
  }
  
  public void setBackground(@Null Drawable background) {
    if (this.background == background)
      return; 
    float padTopOld = getPadTop(), padLeftOld = getPadLeft(), padBottomOld = getPadBottom(), padRightOld = getPadRight();
    this.background = background;
    float padTopNew = getPadTop(), padLeftNew = getPadLeft(), padBottomNew = getPadBottom(), padRightNew = getPadRight();
    if (padTopOld + padBottomOld != padTopNew + padBottomNew || padLeftOld + padRightOld != padLeftNew + padRightNew) {
      invalidateHierarchy();
    } else if (padTopOld != padTopNew || padLeftOld != padLeftNew || padBottomOld != padBottomNew || padRightOld != padRightNew) {
      invalidate();
    } 
  }
  
  public Table background(@Null Drawable background) {
    setBackground(background);
    return this;
  }
  
  public Table background(String drawableName) {
    setBackground(drawableName);
    return this;
  }
  
  @Null
  public Drawable getBackground() {
    return this.background;
  }
  
  @Null
  public Actor hit(float x, float y, boolean touchable) {
    if (this.clip) {
      if (touchable && getTouchable() == Touchable.disabled)
        return null; 
      if (x < 0.0F || x >= getWidth() || y < 0.0F || y >= getHeight())
        return null; 
    } 
    return super.hit(x, y, touchable);
  }
  
  public Table clip() {
    setClip(true);
    return this;
  }
  
  public Table clip(boolean enabled) {
    setClip(enabled);
    return this;
  }
  
  public void setClip(boolean enabled) {
    this.clip = enabled;
    setTransform(enabled);
    invalidate();
  }
  
  public boolean getClip() {
    return this.clip;
  }
  
  public void invalidate() {
    this.sizeInvalid = true;
    super.invalidate();
  }
  
  public <T extends Actor> Cell<T> add(@Null T actor) {
    Cell<T> cell = obtainCell();
    cell.actor = actor;
    if (this.implicitEndRow) {
      this.implicitEndRow = false;
      this.rows--;
      ((Cell)this.cells.peek()).endRow = false;
    } 
    int cellCount = this.cells.size;
    if (cellCount > 0) {
      Cell lastCell = this.cells.peek();
      if (!lastCell.endRow) {
        cell.column = lastCell.column + lastCell.colspan;
        cell.row = lastCell.row;
      } else {
        cell.column = 0;
        cell.row = lastCell.row + 1;
      } 
      if (cell.row > 0) {
        Object[] cells = this.cells.items;
        for (int i = cellCount - 1; i >= 0; i--) {
          Cell other = (Cell)cells[i];
          for (int column = other.column, nn = column + other.colspan; column < nn; column++) {
            if (column == cell.column) {
              cell.cellAboveIndex = i;
              // goto line number 252
            } 
          } 
        } 
      } 
    } else {
      cell.column = 0;
      cell.row = 0;
    } 
    this.cells.add(cell);
    cell.set(this.cellDefaults);
    if (cell.column < this.columnDefaults.size)
      cell.merge(this.columnDefaults.get(cell.column)); 
    cell.merge(this.rowDefaults);
    if (actor != null)
      addActor(actor); 
    return cell;
  }
  
  public Table add(Actor... actors) {
    for (int i = 0, n = actors.length; i < n; i++)
      add(actors[i]); 
    return this;
  }
  
  public Cell<Label> add(@Null CharSequence text) {
    if (this.skin == null)
      throw new IllegalStateException("Table must have a skin set to use this method."); 
    return add(new Label(text, this.skin));
  }
  
  public Cell<Label> add(@Null CharSequence text, String labelStyleName) {
    if (this.skin == null)
      throw new IllegalStateException("Table must have a skin set to use this method."); 
    return add(new Label(text, this.skin.<Label.LabelStyle>get(labelStyleName, Label.LabelStyle.class)));
  }
  
  public Cell<Label> add(@Null CharSequence text, String fontName, @Null Color color) {
    if (this.skin == null)
      throw new IllegalStateException("Table must have a skin set to use this method."); 
    return add(new Label(text, new Label.LabelStyle(this.skin.getFont(fontName), color)));
  }
  
  public Cell<Label> add(@Null CharSequence text, String fontName, String colorName) {
    if (this.skin == null)
      throw new IllegalStateException("Table must have a skin set to use this method."); 
    return add(new Label(text, new Label.LabelStyle(this.skin.getFont(fontName), this.skin.getColor(colorName))));
  }
  
  public Cell add() {
    return add((Actor)null);
  }
  
  public Cell<Stack> stack(@Null Actor... actors) {
    Stack stack = new Stack();
    if (actors != null)
      for (int i = 0, n = actors.length; i < n; i++)
        stack.addActor(actors[i]);  
    return add(stack);
  }
  
  public boolean removeActor(Actor actor) {
    return removeActor(actor, true);
  }
  
  public boolean removeActor(Actor actor, boolean unfocus) {
    if (!super.removeActor(actor, unfocus))
      return false; 
    Cell<Actor> cell = getCell(actor);
    if (cell != null)
      cell.actor = null; 
    return true;
  }
  
  public Actor removeActorAt(int index, boolean unfocus) {
    Actor actor = super.removeActorAt(index, unfocus);
    Cell<Actor> cell = getCell(actor);
    if (cell != null)
      cell.actor = null; 
    return actor;
  }
  
  public void clearChildren(boolean unfocus) {
    Object[] cells = this.cells.items;
    for (int i = this.cells.size - 1; i >= 0; i--) {
      Cell cell = (Cell)cells[i];
      Actor actor = cell.actor;
      if (actor != null)
        actor.remove(); 
    } 
    cellPool.freeAll(this.cells);
    this.cells.clear();
    this.rows = 0;
    this.columns = 0;
    if (this.rowDefaults != null)
      cellPool.free(this.rowDefaults); 
    this.rowDefaults = null;
    this.implicitEndRow = false;
    super.clearChildren(unfocus);
  }
  
  public void reset() {
    clearChildren();
    this.padTop = backgroundTop;
    this.padLeft = backgroundLeft;
    this.padBottom = backgroundBottom;
    this.padRight = backgroundRight;
    this.align = 1;
    debug(Debug.none);
    this.cellDefaults.reset();
    for (int i = 0, n = this.columnDefaults.size; i < n; i++) {
      Cell columnCell = this.columnDefaults.get(i);
      if (columnCell != null)
        cellPool.free(columnCell); 
    } 
    this.columnDefaults.clear();
  }
  
  public Cell row() {
    if (this.cells.size > 0) {
      if (!this.implicitEndRow) {
        if (((Cell)this.cells.peek()).endRow)
          return this.rowDefaults; 
        endRow();
      } 
      invalidate();
    } 
    this.implicitEndRow = false;
    if (this.rowDefaults != null)
      cellPool.free(this.rowDefaults); 
    this.rowDefaults = obtainCell();
    this.rowDefaults.clear();
    return this.rowDefaults;
  }
  
  private void endRow() {
    Object[] cells = this.cells.items;
    int rowColumns = 0;
    for (int i = this.cells.size - 1; i >= 0; i--) {
      Cell cell = (Cell)cells[i];
      if (cell.endRow)
        break; 
      rowColumns += cell.colspan;
    } 
    this.columns = Math.max(this.columns, rowColumns);
    this.rows++;
    ((Cell)this.cells.peek()).endRow = true;
  }
  
  public Cell columnDefaults(int column) {
    Cell cell = (this.columnDefaults.size > column) ? this.columnDefaults.get(column) : null;
    if (cell == null) {
      cell = obtainCell();
      cell.clear();
      if (column >= this.columnDefaults.size) {
        for (int i = this.columnDefaults.size; i < column; i++)
          this.columnDefaults.add(null); 
        this.columnDefaults.add(cell);
      } else
        this.columnDefaults.set(column, cell); 
    } 
    return cell;
  }
  
  @Null
  public <T extends Actor> Cell<T> getCell(T actor) {
    if (actor == null)
      throw new IllegalArgumentException("actor cannot be null."); 
    Object[] cells = this.cells.items;
    for (int i = 0, n = this.cells.size; i < n; i++) {
      Cell<T> c = (Cell)cells[i];
      if (c.actor == actor)
        return c; 
    } 
    return null;
  }
  
  public Array<Cell> getCells() {
    return this.cells;
  }
  
  public float getPrefWidth() {
    if (this.sizeInvalid)
      computeSize(); 
    float width = this.tablePrefWidth;
    if (this.background != null)
      return Math.max(width, this.background.getMinWidth()); 
    return width;
  }
  
  public float getPrefHeight() {
    if (this.sizeInvalid)
      computeSize(); 
    float height = this.tablePrefHeight;
    if (this.background != null)
      return Math.max(height, this.background.getMinHeight()); 
    return height;
  }
  
  public float getMinWidth() {
    if (this.sizeInvalid)
      computeSize(); 
    return this.tableMinWidth;
  }
  
  public float getMinHeight() {
    if (this.sizeInvalid)
      computeSize(); 
    return this.tableMinHeight;
  }
  
  public Cell defaults() {
    return this.cellDefaults;
  }
  
  public Table pad(Value pad) {
    if (pad == null)
      throw new IllegalArgumentException("pad cannot be null."); 
    this.padTop = pad;
    this.padLeft = pad;
    this.padBottom = pad;
    this.padRight = pad;
    this.sizeInvalid = true;
    return this;
  }
  
  public Table pad(Value top, Value left, Value bottom, Value right) {
    if (top == null)
      throw new IllegalArgumentException("top cannot be null."); 
    if (left == null)
      throw new IllegalArgumentException("left cannot be null."); 
    if (bottom == null)
      throw new IllegalArgumentException("bottom cannot be null."); 
    if (right == null)
      throw new IllegalArgumentException("right cannot be null."); 
    this.padTop = top;
    this.padLeft = left;
    this.padBottom = bottom;
    this.padRight = right;
    this.sizeInvalid = true;
    return this;
  }
  
  public Table padTop(Value padTop) {
    if (padTop == null)
      throw new IllegalArgumentException("padTop cannot be null."); 
    this.padTop = padTop;
    this.sizeInvalid = true;
    return this;
  }
  
  public Table padLeft(Value padLeft) {
    if (padLeft == null)
      throw new IllegalArgumentException("padLeft cannot be null."); 
    this.padLeft = padLeft;
    this.sizeInvalid = true;
    return this;
  }
  
  public Table padBottom(Value padBottom) {
    if (padBottom == null)
      throw new IllegalArgumentException("padBottom cannot be null."); 
    this.padBottom = padBottom;
    this.sizeInvalid = true;
    return this;
  }
  
  public Table padRight(Value padRight) {
    if (padRight == null)
      throw new IllegalArgumentException("padRight cannot be null."); 
    this.padRight = padRight;
    this.sizeInvalid = true;
    return this;
  }
  
  public Table pad(float pad) {
    pad(Value.Fixed.valueOf(pad));
    return this;
  }
  
  public Table pad(float top, float left, float bottom, float right) {
    this.padTop = Value.Fixed.valueOf(top);
    this.padLeft = Value.Fixed.valueOf(left);
    this.padBottom = Value.Fixed.valueOf(bottom);
    this.padRight = Value.Fixed.valueOf(right);
    this.sizeInvalid = true;
    return this;
  }
  
  public Table padTop(float padTop) {
    this.padTop = Value.Fixed.valueOf(padTop);
    this.sizeInvalid = true;
    return this;
  }
  
  public Table padLeft(float padLeft) {
    this.padLeft = Value.Fixed.valueOf(padLeft);
    this.sizeInvalid = true;
    return this;
  }
  
  public Table padBottom(float padBottom) {
    this.padBottom = Value.Fixed.valueOf(padBottom);
    this.sizeInvalid = true;
    return this;
  }
  
  public Table padRight(float padRight) {
    this.padRight = Value.Fixed.valueOf(padRight);
    this.sizeInvalid = true;
    return this;
  }
  
  public Table align(int align) {
    this.align = align;
    return this;
  }
  
  public Table center() {
    this.align = 1;
    return this;
  }
  
  public Table top() {
    this.align |= 0x2;
    this.align &= 0xFFFFFFFB;
    return this;
  }
  
  public Table left() {
    this.align |= 0x8;
    this.align &= 0xFFFFFFEF;
    return this;
  }
  
  public Table bottom() {
    this.align |= 0x4;
    this.align &= 0xFFFFFFFD;
    return this;
  }
  
  public Table right() {
    this.align |= 0x10;
    this.align &= 0xFFFFFFF7;
    return this;
  }
  
  public void setDebug(boolean enabled) {
    debug(enabled ? Debug.all : Debug.none);
  }
  
  public Table debug() {
    super.debug();
    return this;
  }
  
  public Table debugAll() {
    super.debugAll();
    return this;
  }
  
  public Table debugTable() {
    super.setDebug(true);
    if (this.debug != Debug.table) {
      this.debug = Debug.table;
      invalidate();
    } 
    return this;
  }
  
  public Table debugCell() {
    super.setDebug(true);
    if (this.debug != Debug.cell) {
      this.debug = Debug.cell;
      invalidate();
    } 
    return this;
  }
  
  public Table debugActor() {
    super.setDebug(true);
    if (this.debug != Debug.actor) {
      this.debug = Debug.actor;
      invalidate();
    } 
    return this;
  }
  
  public Table debug(Debug debug) {
    super.setDebug((debug != Debug.none));
    if (this.debug != debug) {
      this.debug = debug;
      if (debug == Debug.none) {
        clearDebugRects();
      } else
        invalidate(); 
    } 
    return this;
  }
  
  public Debug getTableDebug() {
    return this.debug;
  }
  
  public Value getPadTopValue() {
    return this.padTop;
  }
  
  public float getPadTop() {
    return this.padTop.get(this);
  }
  
  public Value getPadLeftValue() {
    return this.padLeft;
  }
  
  public float getPadLeft() {
    return this.padLeft.get(this);
  }
  
  public Value getPadBottomValue() {
    return this.padBottom;
  }
  
  public float getPadBottom() {
    return this.padBottom.get(this);
  }
  
  public Value getPadRightValue() {
    return this.padRight;
  }
  
  public float getPadRight() {
    return this.padRight.get(this);
  }
  
  public float getPadX() {
    return this.padLeft.get(this) + this.padRight.get(this);
  }
  
  public float getPadY() {
    return this.padTop.get(this) + this.padBottom.get(this);
  }
  
  public int getAlign() {
    return this.align;
  }
  
  public int getRow(float y) {
    int n = this.cells.size;
    if (n == 0)
      return -1; 
    y += getPadTop();
    Object[] cells = this.cells.items;
    for (int i = 0, row = 0; i < n; ) {
      Cell c = (Cell)cells[i++];
      if (c.actorY + c.computedPadTop < y)
        return row; 
      if (c.endRow)
        row++; 
    } 
    return -1;
  }
  
  public void setSkin(@Null Skin skin) {
    this.skin = skin;
  }
  
  public void setRound(boolean round) {
    this.round = round;
  }
  
  public int getRows() {
    return this.rows;
  }
  
  public int getColumns() {
    return this.columns;
  }
  
  public float getRowHeight(int rowIndex) {
    if (this.rowHeight == null)
      return 0.0F; 
    return this.rowHeight[rowIndex];
  }
  
  public float getRowMinHeight(int rowIndex) {
    if (this.sizeInvalid)
      computeSize(); 
    return this.rowMinHeight[rowIndex];
  }
  
  public float getRowPrefHeight(int rowIndex) {
    if (this.sizeInvalid)
      computeSize(); 
    return this.rowPrefHeight[rowIndex];
  }
  
  public float getColumnWidth(int columnIndex) {
    if (this.columnWidth == null)
      return 0.0F; 
    return this.columnWidth[columnIndex];
  }
  
  public float getColumnMinWidth(int columnIndex) {
    if (this.sizeInvalid)
      computeSize(); 
    return this.columnMinWidth[columnIndex];
  }
  
  public float getColumnPrefWidth(int columnIndex) {
    if (this.sizeInvalid)
      computeSize(); 
    return this.columnPrefWidth[columnIndex];
  }
  
  private float[] ensureSize(float[] array, int size) {
    if (array == null || array.length < size)
      return new float[size]; 
    Arrays.fill(array, 0, size, 0.0F);
    return array;
  }
  
  private void computeSize() {
    this.sizeInvalid = false;
    Object[] cells = this.cells.items;
    int cellCount = this.cells.size;
    if (cellCount > 0 && !((Cell)cells[cellCount - 1]).endRow) {
      endRow();
      this.implicitEndRow = true;
    } 
    int columns = this.columns, rows = this.rows;
    float[] columnMinWidth = this.columnMinWidth = ensureSize(this.columnMinWidth, columns);
    float[] rowMinHeight = this.rowMinHeight = ensureSize(this.rowMinHeight, rows);
    float[] columnPrefWidth = this.columnPrefWidth = ensureSize(this.columnPrefWidth, columns);
    float[] rowPrefHeight = this.rowPrefHeight = ensureSize(this.rowPrefHeight, rows);
    float[] columnWidth = this.columnWidth = ensureSize(this.columnWidth, columns);
    float[] rowHeight = this.rowHeight = ensureSize(this.rowHeight, rows);
    float[] expandWidth = this.expandWidth = ensureSize(this.expandWidth, columns);
    float[] expandHeight = this.expandHeight = ensureSize(this.expandHeight, rows);
    float spaceRightLast = 0.0F;
    for (int i = 0; i < cellCount; i++) {
      Cell c = (Cell)cells[i];
      int column = c.column, row = c.row, colspan = c.colspan;
      Actor a = c.actor;
      if (c.expandY != 0 && expandHeight[row] == 0.0F)
        expandHeight[row] = (float)c.expandY; 
      if (colspan == 1 && c.expandX != 0 && expandWidth[column] == 0.0F)
        expandWidth[column] = (float)c.expandX; 
      c.computedPadLeft = c.padLeft.get(a) + ((column == 0) ? 0.0F : Math.max(0.0F, c.spaceLeft.get(a) - spaceRightLast));
      c.computedPadTop = c.padTop.get(a);
      if (c.cellAboveIndex != -1) {
        Cell above = (Cell)cells[c.cellAboveIndex];
        c.computedPadTop += Math.max(0.0F, c.spaceTop.get(a) - above.spaceBottom.get(a));
      } 
      float spaceRight = c.spaceRight.get(a);
      c.computedPadRight = c.padRight.get(a) + ((column + colspan == columns) ? 0.0F : spaceRight);
      c.computedPadBottom = c.padBottom.get(a) + ((row == rows - 1) ? 0.0F : c.spaceBottom.get(a));
      spaceRightLast = spaceRight;
      float prefWidth = c.prefWidth.get(a), prefHeight = c.prefHeight.get(a);
      float minWidth = c.minWidth.get(a), minHeight = c.minHeight.get(a);
      float maxWidth = c.maxWidth.get(a), maxHeight = c.maxHeight.get(a);
      if (prefWidth < minWidth)
        prefWidth = minWidth; 
      if (prefHeight < minHeight)
        prefHeight = minHeight; 
      if (maxWidth > 0.0F && prefWidth > maxWidth)
        prefWidth = maxWidth; 
      if (maxHeight > 0.0F && prefHeight > maxHeight)
        prefHeight = maxHeight; 
      if (this.round) {
        minWidth = (float)Math.ceil((double)minWidth);
        minHeight = (float)Math.ceil((double)minHeight);
        prefWidth = (float)Math.ceil((double)prefWidth);
        prefHeight = (float)Math.ceil((double)prefHeight);
      } 
      if (colspan == 1) {
        float f = c.computedPadLeft + c.computedPadRight;
        columnPrefWidth[column] = Math.max(columnPrefWidth[column], prefWidth + f);
        columnMinWidth[column] = Math.max(columnMinWidth[column], minWidth + f);
      } 
      float f1 = c.computedPadTop + c.computedPadBottom;
      rowPrefHeight[row] = Math.max(rowPrefHeight[row], prefHeight + f1);
      rowMinHeight[row] = Math.max(rowMinHeight[row], minHeight + f1);
    } 
    float uniformMinWidth = 0.0F, uniformMinHeight = 0.0F;
    float uniformPrefWidth = 0.0F, uniformPrefHeight = 0.0F;
    int j;
    label131: for (j = 0; j < cellCount; j++) {
      Cell c = (Cell)cells[j];
      int column = c.column;
      int expandX = c.expandX;
      if (expandX != 0) {
        int nn = column + c.colspan;
        for (int n = column; n < nn; n++) {
          if (expandWidth[n] != 0.0F)
            continue label131; 
        } 
        for (int ii = column; ii < nn; ii++)
          expandWidth[ii] = (float)expandX; 
      } 
      if (c.uniformX == Boolean.TRUE && c.colspan == 1) {
        float f = c.computedPadLeft + c.computedPadRight;
        uniformMinWidth = Math.max(uniformMinWidth, columnMinWidth[column] - f);
        uniformPrefWidth = Math.max(uniformPrefWidth, columnPrefWidth[column] - f);
      } 
      if (c.uniformY == Boolean.TRUE) {
        float f = c.computedPadTop + c.computedPadBottom;
        uniformMinHeight = Math.max(uniformMinHeight, rowMinHeight[c.row] - f);
        uniformPrefHeight = Math.max(uniformPrefHeight, rowPrefHeight[c.row] - f);
      } 
    } 
    if (uniformPrefWidth > 0.0F || uniformPrefHeight > 0.0F)
      for (int n = 0; n < cellCount; n++) {
        Cell c = (Cell)cells[n];
        if (uniformPrefWidth > 0.0F && c.uniformX == Boolean.TRUE && c.colspan == 1) {
          float f = c.computedPadLeft + c.computedPadRight;
          columnMinWidth[c.column] = uniformMinWidth + f;
          columnPrefWidth[c.column] = uniformPrefWidth + f;
        } 
        if (uniformPrefHeight > 0.0F && c.uniformY == Boolean.TRUE) {
          float f = c.computedPadTop + c.computedPadBottom;
          rowMinHeight[c.row] = uniformMinHeight + f;
          rowPrefHeight[c.row] = uniformPrefHeight + f;
        } 
      }  
    for (j = 0; j < cellCount; j++) {
      Cell c = (Cell)cells[j];
      int colspan = c.colspan;
      if (colspan != 1) {
        int column = c.column;
        Actor a = c.actor;
        float minWidth = c.minWidth.get(a), prefWidth = c.prefWidth.get(a), maxWidth = c.maxWidth.get(a);
        if (prefWidth < minWidth)
          prefWidth = minWidth; 
        if (maxWidth > 0.0F && prefWidth > maxWidth)
          prefWidth = maxWidth; 
        if (this.round) {
          minWidth = (float)Math.ceil((double)minWidth);
          prefWidth = (float)Math.ceil((double)prefWidth);
        } 
        float spannedMinWidth = -(c.computedPadLeft + c.computedPadRight), spannedPrefWidth = spannedMinWidth;
        float totalExpandWidth = 0.0F;
        for (int ii = column, nn = ii + colspan; ii < nn; ii++) {
          spannedMinWidth += columnMinWidth[ii];
          spannedPrefWidth += columnPrefWidth[ii];
          totalExpandWidth += expandWidth[ii];
        } 
        float extraMinWidth = Math.max(0.0F, minWidth - spannedMinWidth);
        float extraPrefWidth = Math.max(0.0F, prefWidth - spannedPrefWidth);
        for (int n = column, i1 = n + colspan; n < i1; n++) {
          float ratio = (totalExpandWidth == 0.0F) ? (1.0F / (float)colspan) : (expandWidth[n] / totalExpandWidth);
          columnMinWidth[n] = columnMinWidth[n] + extraMinWidth * ratio;
          columnPrefWidth[n] = columnPrefWidth[n] + extraPrefWidth * ratio;
        } 
      } 
    } 
    float hpadding = this.padLeft.get(this) + this.padRight.get(this);
    float vpadding = this.padTop.get(this) + this.padBottom.get(this);
    this.tableMinWidth = hpadding;
    this.tablePrefWidth = hpadding;
    for (int m = 0; m < columns; m++) {
      this.tableMinWidth += columnMinWidth[m];
      this.tablePrefWidth += columnPrefWidth[m];
    } 
    this.tableMinHeight = vpadding;
    this.tablePrefHeight = vpadding;
    for (int k = 0; k < rows; k++) {
      this.tableMinHeight += rowMinHeight[k];
      this.tablePrefHeight += Math.max(rowMinHeight[k], rowPrefHeight[k]);
    } 
    this.tablePrefWidth = Math.max(this.tableMinWidth, this.tablePrefWidth);
    this.tablePrefHeight = Math.max(this.tableMinHeight, this.tablePrefHeight);
  }
  
  public void layout() {
    float[] columnWeightedWidth, rowWeightedHeight;
    if (this.sizeInvalid)
      computeSize(); 
    float layoutWidth = getWidth(), layoutHeight = getHeight();
    int columns = this.columns, rows = this.rows;
    float[] columnWidth = this.columnWidth, rowHeight = this.rowHeight;
    float padLeft = this.padLeft.get(this), hpadding = padLeft + this.padRight.get(this);
    float padTop = this.padTop.get(this), vpadding = padTop + this.padBottom.get(this);
    float totalGrowWidth = this.tablePrefWidth - this.tableMinWidth;
    if (totalGrowWidth == 0.0F) {
      columnWeightedWidth = this.columnMinWidth;
    } else {
      float extraWidth = Math.min(totalGrowWidth, Math.max(0.0F, layoutWidth - this.tableMinWidth));
      columnWeightedWidth = Table.columnWeightedWidth = ensureSize(Table.columnWeightedWidth, columns);
      float[] columnMinWidth = this.columnMinWidth, columnPrefWidth = this.columnPrefWidth;
      for (int i5 = 0; i5 < columns; i5++) {
        float growWidth = columnPrefWidth[i5] - columnMinWidth[i5];
        float growRatio = growWidth / totalGrowWidth;
        columnWeightedWidth[i5] = columnMinWidth[i5] + extraWidth * growRatio;
      } 
    } 
    float totalGrowHeight = this.tablePrefHeight - this.tableMinHeight;
    if (totalGrowHeight == 0.0F) {
      rowWeightedHeight = this.rowMinHeight;
    } else {
      rowWeightedHeight = Table.rowWeightedHeight = ensureSize(Table.rowWeightedHeight, rows);
      float extraHeight = Math.min(totalGrowHeight, Math.max(0.0F, layoutHeight - this.tableMinHeight));
      float[] rowMinHeight = this.rowMinHeight, rowPrefHeight = this.rowPrefHeight;
      for (int i5 = 0; i5 < rows; i5++) {
        float growHeight = rowPrefHeight[i5] - rowMinHeight[i5];
        float growRatio = growHeight / totalGrowHeight;
        rowWeightedHeight[i5] = rowMinHeight[i5] + extraHeight * growRatio;
      } 
    } 
    Object[] cells = this.cells.items;
    int cellCount = this.cells.size;
    for (int i = 0; i < cellCount; i++) {
      Cell c = (Cell)cells[i];
      int column = c.column, row = c.row;
      Actor a = c.actor;
      float spannedWeightedWidth = 0.0F;
      int colspan = c.colspan;
      for (int ii = column, nn = ii + colspan; ii < nn; ii++)
        spannedWeightedWidth += columnWeightedWidth[ii]; 
      float weightedHeight = rowWeightedHeight[row];
      float prefWidth = c.prefWidth.get(a), prefHeight = c.prefHeight.get(a);
      float minWidth = c.minWidth.get(a), minHeight = c.minHeight.get(a);
      float maxWidth = c.maxWidth.get(a), maxHeight = c.maxHeight.get(a);
      if (prefWidth < minWidth)
        prefWidth = minWidth; 
      if (prefHeight < minHeight)
        prefHeight = minHeight; 
      if (maxWidth > 0.0F && prefWidth > maxWidth)
        prefWidth = maxWidth; 
      if (maxHeight > 0.0F && prefHeight > maxHeight)
        prefHeight = maxHeight; 
      c.actorWidth = Math.min(spannedWeightedWidth - c.computedPadLeft - c.computedPadRight, prefWidth);
      c.actorHeight = Math.min(weightedHeight - c.computedPadTop - c.computedPadBottom, prefHeight);
      if (colspan == 1)
        columnWidth[column] = Math.max(columnWidth[column], spannedWeightedWidth); 
      rowHeight[row] = Math.max(rowHeight[row], weightedHeight);
    } 
    float[] expandWidth = this.expandWidth, expandHeight = this.expandHeight;
    float totalExpand = 0.0F;
    for (int m = 0; m < columns; m++)
      totalExpand += expandWidth[m]; 
    if (totalExpand > 0.0F) {
      float extra = layoutWidth - hpadding;
      for (int i5 = 0; i5 < columns; i5++)
        extra -= columnWidth[i5]; 
      if (extra > 0.0F) {
        float used = 0.0F;
        int lastIndex = 0;
        for (int i6 = 0; i6 < columns; i6++) {
          if (expandWidth[i6] != 0.0F) {
            float amount = extra * expandWidth[i6] / totalExpand;
            columnWidth[i6] = columnWidth[i6] + amount;
            used += amount;
            lastIndex = i6;
          } 
        } 
        columnWidth[lastIndex] = columnWidth[lastIndex] + extra - used;
      } 
    } 
    totalExpand = 0.0F;
    for (int k = 0; k < rows; k++)
      totalExpand += expandHeight[k]; 
    if (totalExpand > 0.0F) {
      float extra = layoutHeight - vpadding;
      for (int i5 = 0; i5 < rows; i5++)
        extra -= rowHeight[i5]; 
      if (extra > 0.0F) {
        float used = 0.0F;
        int lastIndex = 0;
        for (int i6 = 0; i6 < rows; i6++) {
          if (expandHeight[i6] != 0.0F) {
            float amount = extra * expandHeight[i6] / totalExpand;
            rowHeight[i6] = rowHeight[i6] + amount;
            used += amount;
            lastIndex = i6;
          } 
        } 
        rowHeight[lastIndex] = rowHeight[lastIndex] + extra - used;
      } 
    } 
    for (int j = 0; j < cellCount; j++) {
      Cell c = (Cell)cells[j];
      int colspan = c.colspan;
      if (colspan != 1) {
        float extraWidth = 0.0F;
        for (int column = c.column, nn = column + colspan; column < nn; column++)
          extraWidth += columnWeightedWidth[column] - columnWidth[column]; 
        extraWidth -= Math.max(0.0F, c.computedPadLeft + c.computedPadRight);
        extraWidth /= (float)colspan;
        if (extraWidth > 0.0F)
          for (int i5 = c.column, i6 = i5 + colspan; i5 < i6; i5++)
            columnWidth[i5] = columnWidth[i5] + extraWidth;  
      } 
    } 
    float tableWidth = hpadding, tableHeight = vpadding;
    for (int i2 = 0; i2 < columns; i2++)
      tableWidth += columnWidth[i2]; 
    for (int i1 = 0; i1 < rows; i1++)
      tableHeight += rowHeight[i1]; 
    int align = this.align;
    float x = padLeft;
    if ((align & 0x10) != 0) {
      x += layoutWidth - tableWidth;
    } else if ((align & 0x8) == 0) {
      x += (layoutWidth - tableWidth) / 2.0F;
    } 
    float y = padTop;
    if ((align & 0x4) != 0) {
      y += layoutHeight - tableHeight;
    } else if ((align & 0x2) == 0) {
      y += (layoutHeight - tableHeight) / 2.0F;
    } 
    float currentX = x, currentY = y;
    for (int i3 = 0; i3 < cellCount; i3++) {
      Cell c = (Cell)cells[i3];
      float spannedCellWidth = 0.0F;
      for (int column = c.column, nn = column + c.colspan; column < nn; column++)
        spannedCellWidth += columnWidth[column]; 
      spannedCellWidth -= c.computedPadLeft + c.computedPadRight;
      currentX += c.computedPadLeft;
      float fillX = c.fillX, fillY = c.fillY;
      if (fillX > 0.0F) {
        c.actorWidth = Math.max(spannedCellWidth * fillX, c.minWidth.get(c.actor));
        float maxWidth = c.maxWidth.get(c.actor);
        if (maxWidth > 0.0F)
          c.actorWidth = Math.min(c.actorWidth, maxWidth); 
      } 
      if (fillY > 0.0F) {
        c.actorHeight = Math.max(rowHeight[c.row] * fillY - c.computedPadTop - c.computedPadBottom, c.minHeight.get(c.actor));
        float maxHeight = c.maxHeight.get(c.actor);
        if (maxHeight > 0.0F)
          c.actorHeight = Math.min(c.actorHeight, maxHeight); 
      } 
      align = c.align;
      if ((align & 0x8) != 0) {
        c.actorX = currentX;
      } else if ((align & 0x10) != 0) {
        c.actorX = currentX + spannedCellWidth - c.actorWidth;
      } else
        c.actorX = currentX + (spannedCellWidth - c.actorWidth) / 2.0F; 
      if ((align & 0x2) != 0) {
        c.actorY = c.computedPadTop;
      } else if ((align & 0x4) != 0) {
        c.actorY = rowHeight[c.row] - c.actorHeight - c.computedPadBottom;
      } else
        c.actorY = (rowHeight[c.row] - c.actorHeight + c.computedPadTop - c.computedPadBottom) / 2.0F; 
      c.actorY = layoutHeight - currentY - c.actorY - c.actorHeight;
      if (this.round) {
        c.actorWidth = (float)Math.ceil((double)c.actorWidth);
        c.actorHeight = (float)Math.ceil((double)c.actorHeight);
        c.actorX = (float)Math.floor((double)c.actorX);
        c.actorY = (float)Math.floor((double)c.actorY);
      } 
      if (c.actor != null)
        c.actor.setBounds(c.actorX, c.actorY, c.actorWidth, c.actorHeight); 
      if (c.endRow) {
        currentX = x;
        currentY += rowHeight[c.row];
      } else
        currentX += spannedCellWidth + c.computedPadRight; 
    } 
    Array<Actor> childrenArray = getChildren();
    Actor[] children = (Actor[])childrenArray.items;
    for (int i4 = 0, n = childrenArray.size; i4 < n; i4++) {
      Object child = children[i4];
      if (child instanceof Layout)
        ((Layout)child).validate(); 
    } 
    if (this.debug != Debug.none)
      addDebugRects(x, y, tableWidth - hpadding, tableHeight - vpadding); 
  }
  
  private void addDebugRects(float currentX, float currentY, float width, float height) {
    clearDebugRects();
    if (this.debug == Debug.table || this.debug == Debug.all) {
      addDebugRect(0.0F, 0.0F, getWidth(), getHeight(), debugTableColor);
      addDebugRect(currentX, getHeight() - currentY, width, -height, debugTableColor);
    } 
    float x = currentX;
    for (int i = 0, n = this.cells.size; i < n; i++) {
      Cell c = this.cells.get(i);
      if (this.debug == Debug.actor || this.debug == Debug.all)
        addDebugRect(c.actorX, c.actorY, c.actorWidth, c.actorHeight, debugActorColor); 
      float spannedCellWidth = 0.0F;
      for (int column = c.column, nn = column + c.colspan; column < nn; column++)
        spannedCellWidth += this.columnWidth[column]; 
      spannedCellWidth -= c.computedPadLeft + c.computedPadRight;
      currentX += c.computedPadLeft;
      if (this.debug == Debug.cell || this.debug == Debug.all) {
        float h = this.rowHeight[c.row] - c.computedPadTop - c.computedPadBottom;
        float y = currentY + c.computedPadTop;
        addDebugRect(currentX, getHeight() - y, spannedCellWidth, -h, debugCellColor);
      } 
      if (c.endRow) {
        currentX = x;
        currentY += this.rowHeight[c.row];
      } else
        currentX += spannedCellWidth + c.computedPadRight; 
    } 
  }
  
  private void clearDebugRects() {
    if (this.debugRects == null)
      this.debugRects = new Array<>(); 
    DebugRect.pool.freeAll(this.debugRects);
    this.debugRects.clear();
  }
  
  private void addDebugRect(float x, float y, float w, float h, Color color) {
    DebugRect rect = DebugRect.pool.obtain();
    rect.color = color;
    rect.set(x, y, w, h);
    this.debugRects.add(rect);
  }
  
  public void drawDebug(ShapeRenderer shapes) {
    if (isTransform()) {
      applyTransform(shapes, computeTransform());
      drawDebugRects(shapes);
      if (this.clip) {
        shapes.flush();
        float x = 0.0F, y = 0.0F, width = getWidth(), height = getHeight();
        if (this.background != null) {
          x = this.padLeft.get(this);
          y = this.padBottom.get(this);
          width -= x + this.padRight.get(this);
          height -= y + this.padTop.get(this);
        } 
        if (clipBegin(x, y, width, height)) {
          drawDebugChildren(shapes);
          clipEnd();
        } 
      } else
        drawDebugChildren(shapes); 
      resetTransform(shapes);
    } else {
      drawDebugRects(shapes);
      super.drawDebug(shapes);
    } 
  }
  
  protected void drawDebugBounds(ShapeRenderer shapes) {}
  
  private void drawDebugRects(ShapeRenderer shapes) {
    if (this.debugRects == null || !getDebug())
      return; 
    shapes.set(ShapeRenderer.ShapeType.Line);
    if (getStage() != null)
      shapes.setColor(getStage().getDebugColor()); 
    float x = 0.0F, y = 0.0F;
    if (!isTransform()) {
      x = getX();
      y = getY();
    } 
    for (int i = 0, n = this.debugRects.size; i < n; i++) {
      DebugRect debugRect = this.debugRects.get(i);
      shapes.setColor(debugRect.color);
      shapes.rect(x + debugRect.x, y + debugRect.y, debugRect.width, debugRect.height);
    } 
  }
  
  @Null
  public Skin getSkin() {
    return this.skin;
  }
  
  public static class DebugRect extends Rectangle {
    static Pool<DebugRect> pool = Pools.<DebugRect>get(DebugRect.class);
    
    Color color;
  }
  
  public enum Debug {
    none, all, table, cell, actor;
  }
  
  public static Value backgroundTop = new Value() {
      public float get(@Null Actor context) {
        Drawable background = ((Table)context).background;
        return (background == null) ? 0.0F : background.getTopHeight();
      }
    };
  
  public static Value backgroundLeft = new Value() {
      public float get(@Null Actor context) {
        Drawable background = ((Table)context).background;
        return (background == null) ? 0.0F : background.getLeftWidth();
      }
    };
  
  public static Value backgroundBottom = new Value() {
      public float get(@Null Actor context) {
        Drawable background = ((Table)context).background;
        return (background == null) ? 0.0F : background.getBottomHeight();
      }
    };
  
  public static Value backgroundRight = new Value() {
      public float get(@Null Actor context) {
        Drawable background = ((Table)context).background;
        return (background == null) ? 0.0F : background.getRightWidth();
      }
    };
}
