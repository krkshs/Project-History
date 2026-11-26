package com.badlogic.gdx;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.SnapshotArray;

public class InputMultiplexer implements InputProcessor {
  private SnapshotArray<InputProcessor> processors = new SnapshotArray<>(4);
  
  public InputMultiplexer() {}
  
  public InputMultiplexer(InputProcessor... processors) {
    this.processors.addAll(processors);
  }
  
  public void addProcessor(int index, InputProcessor processor) {
    if (processor == null)
      throw new NullPointerException("processor cannot be null"); 
    this.processors.insert(index, processor);
  }
  
  public void removeProcessor(int index) {
    this.processors.removeIndex(index);
  }
  
  public void addProcessor(InputProcessor processor) {
    if (processor == null)
      throw new NullPointerException("processor cannot be null"); 
    this.processors.add(processor);
  }
  
  public void removeProcessor(InputProcessor processor) {
    this.processors.removeValue(processor, true);
  }
  
  public int size() {
    return this.processors.size;
  }
  
  public void clear() {
    this.processors.clear();
  }
  
  public void setProcessors(InputProcessor... processors) {
    this.processors.clear();
    this.processors.addAll(processors);
  }
  
  public void setProcessors(Array<InputProcessor> processors) {
    this.processors.clear();
    this.processors.addAll(processors);
  }
  
  public SnapshotArray<InputProcessor> getProcessors() {
    return this.processors;
  }
  
  public boolean keyDown(int keycode) {
    Object[] items = this.processors.begin();
    try {
      for (int i = 0, n = this.processors.size; i < n; i++) {
        if (((InputProcessor)items[i]).keyDown(keycode))
          return true; 
      } 
    } finally {
      this.processors.end();
    } 
    return false;
  }
  
  public boolean keyUp(int keycode) {
    Object[] items = this.processors.begin();
    try {
      for (int i = 0, n = this.processors.size; i < n; i++) {
        if (((InputProcessor)items[i]).keyUp(keycode))
          return true; 
      } 
    } finally {
      this.processors.end();
    } 
    return false;
  }
  
  public boolean keyTyped(char character) {
    Object[] items = this.processors.begin();
    try {
      for (int i = 0, n = this.processors.size; i < n; i++) {
        if (((InputProcessor)items[i]).keyTyped(character))
          return true; 
      } 
    } finally {
      this.processors.end();
    } 
    return false;
  }
  
  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    Object[] items = this.processors.begin();
    try {
      for (int i = 0, n = this.processors.size; i < n; i++) {
        if (((InputProcessor)items[i]).touchDown(screenX, screenY, pointer, button))
          return true; 
      } 
    } finally {
      this.processors.end();
    } 
    return false;
  }
  
  public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    Object[] items = this.processors.begin();
    try {
      for (int i = 0, n = this.processors.size; i < n; i++) {
        if (((InputProcessor)items[i]).touchUp(screenX, screenY, pointer, button))
          return true; 
      } 
    } finally {
      this.processors.end();
    } 
    return false;
  }
  
  public boolean touchDragged(int screenX, int screenY, int pointer) {
    Object[] items = this.processors.begin();
    try {
      for (int i = 0, n = this.processors.size; i < n; i++) {
        if (((InputProcessor)items[i]).touchDragged(screenX, screenY, pointer))
          return true; 
      } 
    } finally {
      this.processors.end();
    } 
    return false;
  }
  
  public boolean mouseMoved(int screenX, int screenY) {
    Object[] items = this.processors.begin();
    try {
      for (int i = 0, n = this.processors.size; i < n; i++) {
        if (((InputProcessor)items[i]).mouseMoved(screenX, screenY))
          return true; 
      } 
    } finally {
      this.processors.end();
    } 
    return false;
  }
  
  public boolean scrolled(float amountX, float amountY) {
    Object[] items = this.processors.begin();
    try {
      for (int i = 0, n = this.processors.size; i < n; i++) {
        if (((InputProcessor)items[i]).scrolled(amountX, amountY))
          return true; 
      } 
    } finally {
      this.processors.end();
    } 
    return false;
  }
}
