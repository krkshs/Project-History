package com.badlogic.gdx.scenes.scene2d;

import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.Pool;

public abstract class Action implements Pool.Poolable {
  protected Actor actor;
  
  protected Actor target;
  
  @Null
  private Pool pool;
  
  public abstract boolean act(float paramFloat);
  
  public void restart() {}
  
  public void setActor(Actor actor) {
    this.actor = actor;
    if (this.target == null)
      setTarget(actor); 
    if (actor == null && 
      this.pool != null) {
      this.pool.free(this);
      this.pool = null;
    } 
  }
  
  public Actor getActor() {
    return this.actor;
  }
  
  public void setTarget(Actor target) {
    this.target = target;
  }
  
  public Actor getTarget() {
    return this.target;
  }
  
  public void reset() {
    this.actor = null;
    this.target = null;
    this.pool = null;
    restart();
  }
  
  @Null
  public Pool getPool() {
    return this.pool;
  }
  
  public void setPool(@Null Pool pool) {
    this.pool = pool;
  }
  
  public String toString() {
    String name = getClass().getName();
    int dotIndex = name.lastIndexOf('.');
    if (dotIndex != -1)
      name = name.substring(dotIndex + 1); 
    if (name.endsWith("Action"))
      name = name.substring(0, name.length() - 6); 
    return name;
  }
}
