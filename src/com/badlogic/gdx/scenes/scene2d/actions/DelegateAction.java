package com.badlogic.gdx.scenes.scene2d.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Pool;

public abstract class DelegateAction extends Action {
  protected Action action;
  
  public void setAction(Action action) {
    this.action = action;
  }
  
  public Action getAction() {
    return this.action;
  }
  
  protected abstract boolean delegate(float paramFloat);
  
  public final boolean act(float delta) {
    Pool pool = getPool();
    setPool(null);
    try {
      return delegate(delta);
    } finally {
      setPool(pool);
    } 
  }
  
  public void restart() {
    if (this.action != null)
      this.action.restart(); 
  }
  
  public void reset() {
    super.reset();
    this.action = null;
  }
  
  public void setActor(Actor actor) {
    if (this.action != null)
      this.action.setActor(actor); 
    super.setActor(actor);
  }
  
  public void setTarget(Actor target) {
    if (this.action != null)
      this.action.setTarget(target); 
    super.setTarget(target);
  }
  
  public String toString() {
    return super.toString() + ((this.action == null) ? "" : ("(" + this.action + ")"));
  }
}
