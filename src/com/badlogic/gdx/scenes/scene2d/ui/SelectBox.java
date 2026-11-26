package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ArraySelection;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Disableable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

public class SelectBox<T> extends Widget implements Disableable {
  static final Vector2 temp = new Vector2();
  
  SelectBoxStyle style;
  
  final Array<T> items = new Array<>();
  
  SelectBoxList<T> selectBoxList;
  
  private float prefWidth;
  
  private float prefHeight;
  
  private ClickListener clickListener;
  
  boolean disabled;
  
  private int alignment = 8;
  
  boolean selectedPrefWidth;
  
  final ArraySelection<T> selection = new ArraySelection(this.items) {
      public boolean fireChangeEvent() {
        if (SelectBox.this.selectedPrefWidth)
          SelectBox.this.invalidateHierarchy(); 
        return super.fireChangeEvent();
      }
    };
  
  public SelectBox(Skin skin) {
    this(skin.<SelectBoxStyle>get(SelectBoxStyle.class));
  }
  
  public SelectBox(Skin skin, String styleName) {
    this(skin.<SelectBoxStyle>get(styleName, SelectBoxStyle.class));
  }
  
  public SelectBox(SelectBoxStyle style) {
    setStyle(style);
    setSize(getPrefWidth(), getPrefHeight());
    this.selection.setActor(this);
    this.selection.setRequired(true);
    this.selectBoxList = new SelectBoxList<>(this);
    addListener(this.clickListener = new ClickListener() {
          public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            if (pointer == 0 && button != 0)
              return false; 
            if (SelectBox.this.isDisabled())
              return false; 
            if (SelectBox.this.selectBoxList.hasParent()) {
              SelectBox.this.hideList();
            } else
              SelectBox.this.showList(); 
            return true;
          }
        });
  }
  
  public void setMaxListCount(int maxListCount) {
    this.selectBoxList.maxListCount = maxListCount;
  }
  
  public int getMaxListCount() {
    return this.selectBoxList.maxListCount;
  }
  
  protected void setStage(Stage stage) {
    if (stage == null)
      this.selectBoxList.hide(); 
    super.setStage(stage);
  }
  
  public void setStyle(SelectBoxStyle style) {
    if (style == null)
      throw new IllegalArgumentException("style cannot be null."); 
    this.style = style;
    if (this.selectBoxList != null) {
      this.selectBoxList.setStyle(style.scrollStyle);
      this.selectBoxList.list.setStyle(style.listStyle);
    } 
    invalidateHierarchy();
  }
  
  public SelectBoxStyle getStyle() {
    return this.style;
  }
  
  public void setItems(T... newItems) {
    if (newItems == null)
      throw new IllegalArgumentException("newItems cannot be null."); 
    float oldPrefWidth = getPrefWidth();
    this.items.clear();
    this.items.addAll(newItems);
    this.selection.validate();
    this.selectBoxList.list.setItems(this.items);
    invalidate();
    if (oldPrefWidth != getPrefWidth())
      invalidateHierarchy(); 
  }
  
  public void setItems(Array<T> newItems) {
    if (newItems == null)
      throw new IllegalArgumentException("newItems cannot be null."); 
    float oldPrefWidth = getPrefWidth();
    if (newItems != this.items) {
      this.items.clear();
      this.items.addAll(newItems);
    } 
    this.selection.validate();
    this.selectBoxList.list.setItems(this.items);
    invalidate();
    if (oldPrefWidth != getPrefWidth())
      invalidateHierarchy(); 
  }
  
  public void clearItems() {
    if (this.items.size == 0)
      return; 
    this.items.clear();
    this.selection.clear();
    invalidateHierarchy();
  }
  
  public Array<T> getItems() {
    return this.items;
  }
  
  public void layout() {
    Drawable bg = this.style.background;
    BitmapFont font = this.style.font;
    if (bg != null) {
      this.prefHeight = Math.max(bg.getTopHeight() + bg.getBottomHeight() + font.getCapHeight() - font.getDescent() * 2.0F, 
          bg.getMinHeight());
    } else
      this.prefHeight = font.getCapHeight() - font.getDescent() * 2.0F; 
    Pool<GlyphLayout> layoutPool = Pools.<GlyphLayout>get(GlyphLayout.class);
    GlyphLayout layout = layoutPool.obtain();
    if (this.selectedPrefWidth) {
      this.prefWidth = 0.0F;
      if (bg != null)
        this.prefWidth = bg.getLeftWidth() + bg.getRightWidth(); 
      T selected = getSelected();
      if (selected != null) {
        layout.setText(font, toString(selected));
        this.prefWidth += layout.width;
      } 
    } else {
      float maxItemWidth = 0.0F;
      for (int i = 0; i < this.items.size; i++) {
        layout.setText(font, toString(this.items.get(i)));
        maxItemWidth = Math.max(layout.width, maxItemWidth);
      } 
      this.prefWidth = maxItemWidth;
      if (bg != null)
        this.prefWidth = Math.max(this.prefWidth + bg.getLeftWidth() + bg.getRightWidth(), bg.getMinWidth()); 
      List.ListStyle listStyle = this.style.listStyle;
      ScrollPane.ScrollPaneStyle scrollStyle = this.style.scrollStyle;
      float listWidth = maxItemWidth + listStyle.selection.getLeftWidth() + listStyle.selection.getRightWidth();
      bg = scrollStyle.background;
      if (bg != null)
        listWidth = Math.max(listWidth + bg.getLeftWidth() + bg.getRightWidth(), bg.getMinWidth()); 
      if (this.selectBoxList == null || !this.selectBoxList.disableY)
        listWidth += Math.max((this.style.scrollStyle.vScroll != null) ? this.style.scrollStyle.vScroll.getMinWidth() : 0.0F, (this.style.scrollStyle.vScrollKnob != null) ? 
            this.style.scrollStyle.vScrollKnob.getMinWidth() : 0.0F); 
      this.prefWidth = Math.max(this.prefWidth, listWidth);
    } 
    layoutPool.free(layout);
  }
  
  @Null
  protected Drawable getBackgroundDrawable() {
    if (isDisabled() && this.style.backgroundDisabled != null)
      return this.style.backgroundDisabled; 
    if (this.selectBoxList.hasParent() && this.style.backgroundOpen != null)
      return this.style.backgroundOpen; 
    if (isOver() && this.style.backgroundOver != null)
      return this.style.backgroundOver; 
    return this.style.background;
  }
  
  @Null
  protected Color getFontColor() {
    if (isDisabled() && this.style.disabledFontColor != null)
      return this.style.disabledFontColor; 
    if (this.style.overFontColor != null && (isOver() || this.selectBoxList.hasParent()))
      return this.style.overFontColor; 
    return this.style.fontColor;
  }
  
  public void draw(Batch batch, float parentAlpha) {
    validate();
    Drawable background = getBackgroundDrawable();
    Color fontColor = getFontColor();
    BitmapFont font = this.style.font;
    Color color = getColor();
    float x = getX(), y = getY();
    float width = getWidth(), height = getHeight();
    batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
    if (background != null)
      background.draw(batch, x, y, width, height); 
    T selected = this.selection.first();
    if (selected != null) {
      if (background != null) {
        width -= background.getLeftWidth() + background.getRightWidth();
        height -= background.getBottomHeight() + background.getTopHeight();
        x += background.getLeftWidth();
        y += (float)(int)(height / 2.0F + background.getBottomHeight() + (font.getData()).capHeight / 2.0F);
      } else
        y += (float)(int)(height / 2.0F + (font.getData()).capHeight / 2.0F); 
      font.setColor(fontColor.r, fontColor.g, fontColor.b, fontColor.a * parentAlpha);
      drawItem(batch, font, selected, x, y, width);
    } 
  }
  
  protected GlyphLayout drawItem(Batch batch, BitmapFont font, T item, float x, float y, float width) {
    String string = toString(item);
    return font.draw(batch, string, x, y, 0, string.length(), width, this.alignment, false, "...");
  }
  
  public void setAlignment(int alignment) {
    this.alignment = alignment;
  }
  
  public ArraySelection<T> getSelection() {
    return this.selection;
  }
  
  @Null
  public T getSelected() {
    return this.selection.first();
  }
  
  public void setSelected(@Null T item) {
    if (this.items.contains(item, false)) {
      this.selection.set(item);
    } else if (this.items.size > 0) {
      this.selection.set(this.items.first());
    } else
      this.selection.clear(); 
  }
  
  public int getSelectedIndex() {
    ObjectSet<T> selected = this.selection.items();
    return (selected.size == 0) ? -1 : this.items.indexOf(selected.first(), false);
  }
  
  public void setSelectedIndex(int index) {
    this.selection.set(this.items.get(index));
  }
  
  public void setSelectedPrefWidth(boolean selectedPrefWidth) {
    this.selectedPrefWidth = selectedPrefWidth;
  }
  
  public float getMaxSelectedPrefWidth() {
    Pool<GlyphLayout> layoutPool = Pools.<GlyphLayout>get(GlyphLayout.class);
    GlyphLayout layout = layoutPool.obtain();
    float width = 0.0F;
    for (int i = 0; i < this.items.size; i++) {
      layout.setText(this.style.font, toString(this.items.get(i)));
      width = Math.max(layout.width, width);
    } 
    Drawable bg = this.style.background;
    if (bg != null)
      width = Math.max(width + bg.getLeftWidth() + bg.getRightWidth(), bg.getMinWidth()); 
    return width;
  }
  
  public void setDisabled(boolean disabled) {
    if (disabled && !this.disabled)
      hideList(); 
    this.disabled = disabled;
  }
  
  public boolean isDisabled() {
    return this.disabled;
  }
  
  public float getPrefWidth() {
    validate();
    return this.prefWidth;
  }
  
  public float getPrefHeight() {
    validate();
    return this.prefHeight;
  }
  
  protected String toString(T item) {
    return item.toString();
  }
  
  public void showList() {
    if (this.items.size == 0)
      return; 
    if (getStage() != null)
      this.selectBoxList.show(getStage()); 
  }
  
  public void hideList() {
    this.selectBoxList.hide();
  }
  
  public List<T> getList() {
    return this.selectBoxList.list;
  }
  
  public void setScrollingDisabled(boolean y) {
    this.selectBoxList.setScrollingDisabled(true, y);
    invalidateHierarchy();
  }
  
  public ScrollPane getScrollPane() {
    return this.selectBoxList;
  }
  
  public boolean isOver() {
    return this.clickListener.isOver();
  }
  
  public ClickListener getClickListener() {
    return this.clickListener;
  }
  
  protected void onShow(Actor selectBoxList, boolean below) {
    (selectBoxList.getColor()).a = 0.0F;
    selectBoxList.addAction(Actions.fadeIn(0.3F, Interpolation.fade));
  }
  
  protected void onHide(Actor selectBoxList) {
    (selectBoxList.getColor()).a = 1.0F;
    selectBoxList.addAction(Actions.sequence(Actions.fadeOut(0.15F, Interpolation.fade), Actions.removeActor()));
  }
  
  static class SelectBoxList<T> extends ScrollPane {
    private final SelectBox<T> selectBox;
    
    int maxListCount;
    
    private final Vector2 stagePosition = new Vector2();
    
    final List<T> list;
    
    private InputListener hideListener;
    
    private Actor previousScrollFocus;
    
    public SelectBoxList(final SelectBox<T> selectBox) {
      super(null, selectBox.style.scrollStyle);
      this.selectBox = selectBox;
      setOverscroll(false, false);
      setFadeScrollBars(false);
      setScrollingDisabled(true, false);
      this.list = new List<T>(selectBox.style.listStyle) {
          public String toString(T obj) {
            return selectBox.toString(obj);
          }
        };
      this.list.setTouchable(Touchable.disabled);
      this.list.setTypeToSelect(true);
      setActor(this.list);
      this.list.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
              T selected = SelectBoxList.this.list.getSelected();
              if (selected != null)
                selectBox.selection.items().clear(); 
              selectBox.selection.choose(selected);
              SelectBoxList.this.hide();
            }
            
            public boolean mouseMoved(InputEvent event, float x, float y) {
              int index = SelectBoxList.this.list.getItemIndexAt(y);
              if (index != -1)
                SelectBoxList.this.list.setSelectedIndex(index); 
              return true;
            }
          });
      addListener(new InputListener() {
            public void exit(InputEvent event, float x, float y, int pointer, @Null Actor toActor) {
              if (toActor == null || !SelectBoxList.this.isAscendantOf(toActor))
                SelectBoxList.this.list.selection.set(selectBox.getSelected()); 
            }
          });
      this.hideListener = new InputListener() {
          public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            Actor target = event.getTarget();
            if (SelectBoxList.this.isAscendantOf(target))
              return false; 
            SelectBoxList.this.list.selection.set(selectBox.getSelected());
            SelectBoxList.this.hide();
            return false;
          }
          
          public boolean keyDown(InputEvent event, int keycode) {
            switch (keycode) {
              case 66:
              case 160:
                selectBox.selection.choose(SelectBoxList.this.list.getSelected());
              case 111:
                SelectBoxList.this.hide();
                event.stop();
                return true;
            } 
            return false;
          }
        };
    }
    
    public void show(Stage stage) {
      if (this.list.isTouchable())
        return; 
      stage.addActor(this);
      stage.addCaptureListener(this.hideListener);
      stage.addListener(this.list.getKeyListener());
      this.selectBox.localToStageCoordinates(this.stagePosition.set(0.0F, 0.0F));
      float itemHeight = this.list.getItemHeight();
      float height = itemHeight * (float)((this.maxListCount <= 0) ? this.selectBox.items.size : Math.min(this.maxListCount, this.selectBox.items.size));
      Drawable scrollPaneBackground = (getStyle()).background;
      if (scrollPaneBackground != null)
        height += scrollPaneBackground.getTopHeight() + scrollPaneBackground.getBottomHeight(); 
      Drawable listBackground = (this.list.getStyle()).background;
      if (listBackground != null)
        height += listBackground.getTopHeight() + listBackground.getBottomHeight(); 
      float heightBelow = this.stagePosition.y;
      float heightAbove = stage.getHeight() - heightBelow - this.selectBox.getHeight();
      boolean below = true;
      if (height > heightBelow)
        if (heightAbove > heightBelow) {
          below = false;
          height = Math.min(height, heightAbove);
        } else
          height = heightBelow;  
      if (below) {
        setY(this.stagePosition.y - height);
      } else
        setY(this.stagePosition.y + this.selectBox.getHeight()); 
      setX(this.stagePosition.x);
      setHeight(height);
      validate();
      float width = Math.max(getPrefWidth(), this.selectBox.getWidth());
      if (getPrefHeight() > height && !this.disableY)
        width += getScrollBarWidth(); 
      setWidth(width);
      validate();
      scrollTo(0.0F, this.list.getHeight() - (float)this.selectBox.getSelectedIndex() * itemHeight - itemHeight / 2.0F, 0.0F, 0.0F, true, true);
      updateVisualScroll();
      this.previousScrollFocus = null;
      Actor actor = stage.getScrollFocus();
      if (actor != null && !actor.isDescendantOf(this))
        this.previousScrollFocus = actor; 
      stage.setScrollFocus(this);
      this.list.selection.set(this.selectBox.getSelected());
      this.list.setTouchable(Touchable.enabled);
      clearActions();
      this.selectBox.onShow(this, below);
    }
    
    public void hide() {
      if (!this.list.isTouchable() || !hasParent())
        return; 
      this.list.setTouchable(Touchable.disabled);
      Stage stage = getStage();
      if (stage != null) {
        stage.removeCaptureListener(this.hideListener);
        stage.removeListener(this.list.getKeyListener());
        if (this.previousScrollFocus != null && this.previousScrollFocus.getStage() == null)
          this.previousScrollFocus = null; 
        Actor actor = stage.getScrollFocus();
        if (actor == null || isAscendantOf(actor))
          stage.setScrollFocus(this.previousScrollFocus); 
      } 
      clearActions();
      this.selectBox.onHide(this);
    }
    
    public void draw(Batch batch, float parentAlpha) {
      this.selectBox.localToStageCoordinates(SelectBox.temp.set(0.0F, 0.0F));
      if (!SelectBox.temp.equals(this.stagePosition))
        hide(); 
      super.draw(batch, parentAlpha);
    }
    
    public void act(float delta) {
      super.act(delta);
      toFront();
    }
    
    protected void setStage(Stage stage) {
      Stage oldStage = getStage();
      if (oldStage != null) {
        oldStage.removeCaptureListener(this.hideListener);
        oldStage.removeListener(this.list.getKeyListener());
      } 
      super.setStage(stage);
    }
  }
  
  public static class SelectBoxStyle {
    public BitmapFont font;
    
    public Color fontColor = new Color(1.0F, 1.0F, 1.0F, 1.0F);
    
    @Null
    public Color overFontColor;
    
    @Null
    public Color disabledFontColor;
    
    @Null
    public Drawable background;
    
    public ScrollPane.ScrollPaneStyle scrollStyle;
    
    public List.ListStyle listStyle;
    
    @Null
    public Drawable backgroundOver;
    
    @Null
    public Drawable backgroundOpen;
    
    @Null
    public Drawable backgroundDisabled;
    
    public SelectBoxStyle() {}
    
    public SelectBoxStyle(BitmapFont font, Color fontColor, @Null Drawable background, ScrollPane.ScrollPaneStyle scrollStyle, List.ListStyle listStyle) {
      this.font = font;
      this.fontColor.set(fontColor);
      this.background = background;
      this.scrollStyle = scrollStyle;
      this.listStyle = listStyle;
    }
    
    public SelectBoxStyle(SelectBoxStyle style) {
      this.font = style.font;
      this.fontColor.set(style.fontColor);
      if (style.overFontColor != null)
        this.overFontColor = new Color(style.overFontColor); 
      if (style.disabledFontColor != null)
        this.disabledFontColor = new Color(style.disabledFontColor); 
      this.background = style.background;
      this.scrollStyle = new ScrollPane.ScrollPaneStyle(style.scrollStyle);
      this.listStyle = new List.ListStyle(style.listStyle);
      this.backgroundOver = style.backgroundOver;
      this.backgroundOpen = style.backgroundOpen;
      this.backgroundDisabled = style.backgroundDisabled;
    }
  }
}
