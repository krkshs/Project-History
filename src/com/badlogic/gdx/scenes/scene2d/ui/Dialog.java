package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.ObjectMap;

public class Dialog extends Window {
  Table contentTable;
  
  Table buttonTable;
  
  @Null
  private Skin skin;
  
  ObjectMap<Actor, Object> values = new ObjectMap<>();
  
  boolean cancelHide;
  
  Actor previousKeyboardFocus;
  
  Actor previousScrollFocus;
  
  FocusListener focusListener;
  
  protected InputListener ignoreTouchDown = new InputListener() {
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        event.cancel();
        return false;
      }
    };
  
  public Dialog(String title, Skin skin) {
    super(title, skin.<Window.WindowStyle>get(Window.WindowStyle.class));
    setSkin(skin);
    this.skin = skin;
    initialize();
  }
  
  public Dialog(String title, Skin skin, String windowStyleName) {
    super(title, skin.<Window.WindowStyle>get(windowStyleName, Window.WindowStyle.class));
    setSkin(skin);
    this.skin = skin;
    initialize();
  }
  
  public Dialog(String title, Window.WindowStyle windowStyle) {
    super(title, windowStyle);
    initialize();
  }
  
  private void initialize() {
    setModal(true);
    defaults().space(6.0F);
    add(this.contentTable = new Table(this.skin)).expand().fill();
    row();
    add(this.buttonTable = new Table(this.skin)).fillX();
    this.contentTable.defaults().space(6.0F);
    this.buttonTable.defaults().space(6.0F);
    this.buttonTable.addListener(new ChangeListener() {
          public void changed(ChangeListener.ChangeEvent event, Actor actor) {
            if (!Dialog.this.values.containsKey(actor))
              return; 
            while (actor.getParent() != Dialog.this.buttonTable)
              actor = actor.getParent(); 
            Dialog.this.result(Dialog.this.values.<Actor>get(actor));
            if (!Dialog.this.cancelHide)
              Dialog.this.hide(); 
            Dialog.this.cancelHide = false;
          }
        });
    this.focusListener = new FocusListener() {
        public void keyboardFocusChanged(FocusListener.FocusEvent event, Actor actor, boolean focused) {
          if (!focused)
            focusChanged(event); 
        }
        
        public void scrollFocusChanged(FocusListener.FocusEvent event, Actor actor, boolean focused) {
          if (!focused)
            focusChanged(event); 
        }
        
        private void focusChanged(FocusListener.FocusEvent event) {
          Stage stage = Dialog.this.getStage();
          if (Dialog.this.isModal && stage != null && (stage.getRoot().getChildren()).size > 0 && 
            stage.getRoot().getChildren().peek() == Dialog.this) {
            Actor newFocusedActor = event.getRelatedActor();
            if (newFocusedActor != null && !newFocusedActor.isDescendantOf(Dialog.this) && 
              !newFocusedActor.equals(Dialog.this.previousKeyboardFocus) && !newFocusedActor.equals(Dialog.this.previousScrollFocus))
              event.cancel(); 
          } 
        }
      };
  }
  
  protected void setStage(Stage stage) {
    if (stage == null) {
      addListener(this.focusListener);
    } else
      removeListener(this.focusListener); 
    super.setStage(stage);
  }
  
  public Table getContentTable() {
    return this.contentTable;
  }
  
  public Table getButtonTable() {
    return this.buttonTable;
  }
  
  public Dialog text(@Null String text) {
    if (this.skin == null)
      throw new IllegalStateException("This method may only be used if the dialog was constructed with a Skin."); 
    return text(text, this.skin.<Label.LabelStyle>get(Label.LabelStyle.class));
  }
  
  public Dialog text(@Null String text, Label.LabelStyle labelStyle) {
    return text(new Label(text, labelStyle));
  }
  
  public Dialog text(Label label) {
    this.contentTable.<Label>add(label);
    return this;
  }
  
  public Dialog button(@Null String text) {
    return button(text, null);
  }
  
  public Dialog button(@Null String text, @Null Object object) {
    if (this.skin == null)
      throw new IllegalStateException("This method may only be used if the dialog was constructed with a Skin."); 
    return button(text, object, this.skin.<TextButton.TextButtonStyle>get(TextButton.TextButtonStyle.class));
  }
  
  public Dialog button(@Null String text, @Null Object object, TextButton.TextButtonStyle buttonStyle) {
    return button(new TextButton(text, buttonStyle), object);
  }
  
  public Dialog button(Button button) {
    return button(button, null);
  }
  
  public Dialog button(Button button, @Null Object object) {
    this.buttonTable.<Button>add(button);
    setObject(button, object);
    return this;
  }
  
  public Dialog show(Stage stage, @Null Action action) {
    clearActions();
    removeCaptureListener(this.ignoreTouchDown);
    this.previousKeyboardFocus = null;
    Actor actor = stage.getKeyboardFocus();
    if (actor != null && !actor.isDescendantOf(this))
      this.previousKeyboardFocus = actor; 
    this.previousScrollFocus = null;
    actor = stage.getScrollFocus();
    if (actor != null && !actor.isDescendantOf(this))
      this.previousScrollFocus = actor; 
    stage.addActor(this);
    pack();
    stage.cancelTouchFocus();
    stage.setKeyboardFocus(this);
    stage.setScrollFocus(this);
    if (action != null)
      addAction(action); 
    return this;
  }
  
  public Dialog show(Stage stage) {
    show(stage, Actions.sequence(Actions.alpha(0.0F), Actions.fadeIn(0.4F, Interpolation.fade)));
    setPosition((float)Math.round((stage.getWidth() - getWidth()) / 2.0F), (float)Math.round((stage.getHeight() - getHeight()) / 2.0F));
    return this;
  }
  
  public void hide(@Null Action action) {
    Stage stage = getStage();
    if (stage != null) {
      removeListener(this.focusListener);
      if (this.previousKeyboardFocus != null && this.previousKeyboardFocus.getStage() == null)
        this.previousKeyboardFocus = null; 
      Actor actor = stage.getKeyboardFocus();
      if (actor == null || actor.isDescendantOf(this))
        stage.setKeyboardFocus(this.previousKeyboardFocus); 
      if (this.previousScrollFocus != null && this.previousScrollFocus.getStage() == null)
        this.previousScrollFocus = null; 
      actor = stage.getScrollFocus();
      if (actor == null || actor.isDescendantOf(this))
        stage.setScrollFocus(this.previousScrollFocus); 
    } 
    if (action != null) {
      addCaptureListener(this.ignoreTouchDown);
      addAction(Actions.sequence(action, Actions.removeListener(this.ignoreTouchDown, true), Actions.removeActor()));
    } else
      remove(); 
  }
  
  public void hide() {
    hide(Actions.fadeOut(0.4F, Interpolation.fade));
  }
  
  public void setObject(Actor actor, @Null Object object) {
    this.values.put(actor, object);
  }
  
  public Dialog key(final int keycode, @Null final Object object) {
    addListener(new InputListener() {
          public boolean keyDown(InputEvent event, int keycode2) {
            if (keycode == keycode2)
              Gdx.app.postRunnable(new Runnable() {
                    public void run() {
                      Dialog.this.result(object);
                      if (!Dialog.this.cancelHide)
                        Dialog.this.hide(); 
                      Dialog.this.cancelHide = false;
                    }
                  }); 
            return false;
          }
        });
    return this;
  }
  
  protected void result(@Null Object object) {}
  
  public void cancel() {
    this.cancelHide = true;
  }
}
