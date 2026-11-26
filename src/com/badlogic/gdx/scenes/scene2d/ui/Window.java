package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Null;

public class Window extends Table {
  private static final Vector2 tmpPosition = new Vector2();
  
  private static final Vector2 tmpSize = new Vector2();
  
  private static final int MOVE = 32;
  
  private WindowStyle style;
  
  boolean isMovable = true;
  
  boolean isModal;
  
  boolean isResizable;
  
  int resizeBorder = 8;
  
  boolean keepWithinStage = true;
  
  Label titleLabel;
  
  Table titleTable;
  
  boolean drawTitleTable;
  
  protected int edge;
  
  protected boolean dragging;
  
  public Window(String title, Skin skin) {
    this(title, skin.<WindowStyle>get(WindowStyle.class));
    setSkin(skin);
  }
  
  public Window(String title, Skin skin, String styleName) {
    this(title, skin.<WindowStyle>get(styleName, WindowStyle.class));
    setSkin(skin);
  }
  
  public Window(String title, WindowStyle style) {
    if (title == null)
      throw new IllegalArgumentException("title cannot be null."); 
    setTouchable(Touchable.enabled);
    setClip(true);
    this.titleLabel = new Label(title, new Label.LabelStyle(style.titleFont, style.titleFontColor));
    this.titleLabel.setEllipsis(true);
    this.titleTable = new Table() {
        public void draw(Batch batch, float parentAlpha) {
          if (Window.this.drawTitleTable)
            super.draw(batch, parentAlpha); 
        }
      };
    this.titleTable.<Label>add(this.titleLabel).expandX().fillX().minWidth(0.0F);
    addActor(this.titleTable);
    setStyle(style);
    setWidth(150.0F);
    setHeight(150.0F);
    addCaptureListener(new InputListener() {
          public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            Window.this.toFront();
            return false;
          }
        });
    addListener(new InputListener() {
          float startX;
          
          float startY;
          
          float lastX;
          
          float lastY;
          
          private void updateEdge(float x, float y) {
            float border = (float)Window.this.resizeBorder / 2.0F;
            float width = Window.this.getWidth(), height = Window.this.getHeight();
            float padTop = Window.this.getPadTop(), padLeft = Window.this.getPadLeft(), padBottom = Window.this.getPadBottom(), padRight = Window.this.getPadRight();
            float left = padLeft, right = width - padRight, bottom = padBottom;
            Window.this.edge = 0;
            if (Window.this.isResizable && x >= left - border && x <= right + border && y >= bottom - border) {
              if (x < left + border)
                Window.this.edge |= 0x8; 
              if (x > right - border)
                Window.this.edge |= 0x10; 
              if (y < bottom + border)
                Window.this.edge |= 0x4; 
              if (Window.this.edge != 0)
                border += 25.0F; 
              if (x < left + border)
                Window.this.edge |= 0x8; 
              if (x > right - border)
                Window.this.edge |= 0x10; 
              if (y < bottom + border)
                Window.this.edge |= 0x4; 
            } 
            if (Window.this.isMovable && Window.this.edge == 0 && y <= height && y >= height - padTop && x >= left && x <= right)
              Window.this.edge = 32; 
          }
          
          public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            if (button == 0) {
              updateEdge(x, y);
              Window.this.dragging = (Window.this.edge != 0);
              this.startX = x;
              this.startY = y;
              this.lastX = x - Window.this.getWidth();
              this.lastY = y - Window.this.getHeight();
            } 
            return (Window.this.edge != 0 || Window.this.isModal);
          }
          
          public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            Window.this.dragging = false;
          }
          
          public void touchDragged(InputEvent event, float x, float y, int pointer) {
            if (!Window.this.dragging)
              return; 
            float width = Window.this.getWidth(), height = Window.this.getHeight();
            float windowX = Window.this.getX(), windowY = Window.this.getY();
            float minWidth = Window.this.getMinWidth(), maxWidth = Window.this.getMaxWidth();
            float minHeight = Window.this.getMinHeight(), maxHeight = Window.this.getMaxHeight();
            Stage stage = Window.this.getStage();
            boolean clampPosition = (Window.this.keepWithinStage && stage != null && Window.this.getParent() == stage.getRoot());
            if ((Window.this.edge & 0x20) != 0) {
              float amountX = x - this.startX, amountY = y - this.startY;
              windowX += amountX;
              windowY += amountY;
            } 
            if ((Window.this.edge & 0x8) != 0) {
              float amountX = x - this.startX;
              if (width - amountX < minWidth)
                amountX = -(minWidth - width); 
              if (clampPosition && windowX + amountX < 0.0F)
                amountX = -windowX; 
              width -= amountX;
              windowX += amountX;
            } 
            if ((Window.this.edge & 0x4) != 0) {
              float amountY = y - this.startY;
              if (height - amountY < minHeight)
                amountY = -(minHeight - height); 
              if (clampPosition && windowY + amountY < 0.0F)
                amountY = -windowY; 
              height -= amountY;
              windowY += amountY;
            } 
            if ((Window.this.edge & 0x10) != 0) {
              float amountX = x - this.lastX - width;
              if (width + amountX < minWidth)
                amountX = minWidth - width; 
              if (clampPosition && windowX + width + amountX > stage.getWidth())
                amountX = stage.getWidth() - windowX - width; 
              width += amountX;
            } 
            if ((Window.this.edge & 0x2) != 0) {
              float amountY = y - this.lastY - height;
              if (height + amountY < minHeight)
                amountY = minHeight - height; 
              if (clampPosition && windowY + height + amountY > stage.getHeight())
                amountY = stage.getHeight() - windowY - height; 
              height += amountY;
            } 
            Window.this.setBounds((float)Math.round(windowX), (float)Math.round(windowY), (float)Math.round(width), (float)Math.round(height));
          }
          
          public boolean mouseMoved(InputEvent event, float x, float y) {
            updateEdge(x, y);
            return Window.this.isModal;
          }
          
          public boolean scrolled(InputEvent event, float x, float y, int amount) {
            return Window.this.isModal;
          }
          
          public boolean keyDown(InputEvent event, int keycode) {
            return Window.this.isModal;
          }
          
          public boolean keyUp(InputEvent event, int keycode) {
            return Window.this.isModal;
          }
          
          public boolean keyTyped(InputEvent event, char character) {
            return Window.this.isModal;
          }
        });
  }
  
  public void setStyle(WindowStyle style) {
    if (style == null)
      throw new IllegalArgumentException("style cannot be null."); 
    this.style = style;
    setBackground(style.background);
    this.titleLabel.setStyle(new Label.LabelStyle(style.titleFont, style.titleFontColor));
    invalidateHierarchy();
  }
  
  public WindowStyle getStyle() {
    return this.style;
  }
  
  public void keepWithinStage() {
    if (!this.keepWithinStage)
      return; 
    Stage stage = getStage();
    if (stage == null)
      return; 
    Camera camera = stage.getCamera();
    if (camera instanceof OrthographicCamera) {
      OrthographicCamera orthographicCamera = (OrthographicCamera)camera;
      float parentWidth = stage.getWidth();
      float parentHeight = stage.getHeight();
      if (getX(16) - camera.position.x > parentWidth / 2.0F / orthographicCamera.zoom)
        setPosition(camera.position.x + parentWidth / 2.0F / orthographicCamera.zoom, getY(16), 16); 
      if (getX(8) - camera.position.x < -parentWidth / 2.0F / orthographicCamera.zoom)
        setPosition(camera.position.x - parentWidth / 2.0F / orthographicCamera.zoom, getY(8), 8); 
      if (getY(2) - camera.position.y > parentHeight / 2.0F / orthographicCamera.zoom)
        setPosition(getX(2), camera.position.y + parentHeight / 2.0F / orthographicCamera.zoom, 2); 
      if (getY(4) - camera.position.y < -parentHeight / 2.0F / orthographicCamera.zoom)
        setPosition(getX(4), camera.position.y - parentHeight / 2.0F / orthographicCamera.zoom, 4); 
    } else if (getParent() == stage.getRoot()) {
      float parentWidth = stage.getWidth();
      float parentHeight = stage.getHeight();
      if (getX() < 0.0F)
        setX(0.0F); 
      if (getRight() > parentWidth)
        setX(parentWidth - getWidth()); 
      if (getY() < 0.0F)
        setY(0.0F); 
      if (getTop() > parentHeight)
        setY(parentHeight - getHeight()); 
    } 
  }
  
  public void draw(Batch batch, float parentAlpha) {
    Stage stage = getStage();
    if (stage != null) {
      if (stage.getKeyboardFocus() == null)
        stage.setKeyboardFocus(this); 
      keepWithinStage();
      if (this.style.stageBackground != null) {
        stageToLocalCoordinates(tmpPosition.set(0.0F, 0.0F));
        stageToLocalCoordinates(tmpSize.set(stage.getWidth(), stage.getHeight()));
        drawStageBackground(batch, parentAlpha, getX() + tmpPosition.x, getY() + tmpPosition.y, getX() + tmpSize.x, 
            getY() + tmpSize.y);
      } 
    } 
    super.draw(batch, parentAlpha);
  }
  
  protected void drawStageBackground(Batch batch, float parentAlpha, float x, float y, float width, float height) {
    Color color = getColor();
    batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
    this.style.stageBackground.draw(batch, x, y, width, height);
  }
  
  protected void drawBackground(Batch batch, float parentAlpha, float x, float y) {
    super.drawBackground(batch, parentAlpha, x, y);
    (this.titleTable.getColor()).a = (getColor()).a;
    float padTop = getPadTop(), padLeft = getPadLeft();
    this.titleTable.setSize(getWidth() - padLeft - getPadRight(), padTop);
    this.titleTable.setPosition(padLeft, getHeight() - padTop);
    this.drawTitleTable = true;
    this.titleTable.draw(batch, parentAlpha);
    this.drawTitleTable = false;
  }
  
  @Null
  public Actor hit(float x, float y, boolean touchable) {
    if (!isVisible())
      return null; 
    Actor hit = super.hit(x, y, touchable);
    if (hit == null && this.isModal && (!touchable || getTouchable() == Touchable.enabled))
      return this; 
    float height = getHeight();
    if (hit == null || hit == this)
      return hit; 
    if (y <= height && y >= height - getPadTop() && x >= 0.0F && x <= getWidth()) {
      Actor current = hit;
      while (current.getParent() != this)
        current = current.getParent(); 
      if (getCell(current) != null)
        return this; 
    } 
    return hit;
  }
  
  public boolean isMovable() {
    return this.isMovable;
  }
  
  public void setMovable(boolean isMovable) {
    this.isMovable = isMovable;
  }
  
  public boolean isModal() {
    return this.isModal;
  }
  
  public void setModal(boolean isModal) {
    this.isModal = isModal;
  }
  
  public void setKeepWithinStage(boolean keepWithinStage) {
    this.keepWithinStage = keepWithinStage;
  }
  
  public boolean isResizable() {
    return this.isResizable;
  }
  
  public void setResizable(boolean isResizable) {
    this.isResizable = isResizable;
  }
  
  public void setResizeBorder(int resizeBorder) {
    this.resizeBorder = resizeBorder;
  }
  
  public boolean isDragging() {
    return this.dragging;
  }
  
  public float getPrefWidth() {
    return Math.max(super.getPrefWidth(), this.titleTable.getPrefWidth() + getPadLeft() + getPadRight());
  }
  
  public Table getTitleTable() {
    return this.titleTable;
  }
  
  public Label getTitleLabel() {
    return this.titleLabel;
  }
  
  public static class WindowStyle {
    @Null
    public Drawable background;
    
    public BitmapFont titleFont;
    
    @Null
    public Color titleFontColor = new Color(1.0F, 1.0F, 1.0F, 1.0F);
    
    @Null
    public Drawable stageBackground;
    
    public WindowStyle() {}
    
    public WindowStyle(BitmapFont titleFont, Color titleFontColor, @Null Drawable background) {
      this.titleFont = titleFont;
      this.titleFontColor.set(titleFontColor);
      this.background = background;
    }
    
    public WindowStyle(WindowStyle style) {
      this.background = style.background;
      this.titleFont = style.titleFont;
      if (style.titleFontColor != null)
        this.titleFontColor = new Color(style.titleFontColor); 
      this.background = style.background;
    }
  }
}
