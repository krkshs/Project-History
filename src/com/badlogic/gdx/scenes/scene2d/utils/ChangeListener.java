package com.badlogic.gdx.scenes.scene2d.utils;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;

public abstract class ChangeListener implements EventListener {
  public boolean handle(Event event) {
    if (!(event instanceof ChangeEvent))
      return false; 
    changed((ChangeEvent)event, event.getTarget());
    return false;
  }
  
  public abstract void changed(ChangeEvent paramChangeEvent, Actor paramActor);
  
  public static class ChangeEvent extends Event {}
}
