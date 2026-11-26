package com.badlogic.gdx.graphics.g2d;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.reflect.ArrayReflection;

public class Animation<T> {
  T[] keyFrames;
  
  private float frameDuration;
  
  private float animationDuration;
  
  private int lastFrameNumber;
  
  private float lastStateTime;
  
  public enum PlayMode {
    NORMAL, REVERSED, LOOP, LOOP_REVERSED, LOOP_PINGPONG, LOOP_RANDOM;
  }
  
  private PlayMode playMode = PlayMode.NORMAL;
  
  public Animation(float frameDuration, Array<? extends T> keyFrames) {
    this.frameDuration = frameDuration;
    Class<?> arrayType = keyFrames.items.getClass().getComponentType();
    T[] frames = (T[])ArrayReflection.newInstance(arrayType, keyFrames.size);
    for (int i = 0, n = keyFrames.size; i < n; i++)
      frames[i] = keyFrames.get(i); 
    setKeyFrames(frames);
  }
  
  public Animation(float frameDuration, Array<? extends T> keyFrames, PlayMode playMode) {
    this(frameDuration, keyFrames);
    setPlayMode(playMode);
  }
  
  public Animation(float frameDuration, T... keyFrames) {
    this.frameDuration = frameDuration;
    setKeyFrames(keyFrames);
  }
  
  public T getKeyFrame(float stateTime, boolean looping) {
    PlayMode oldPlayMode = this.playMode;
    if (looping && (this.playMode == PlayMode.NORMAL || this.playMode == PlayMode.REVERSED)) {
      if (this.playMode == PlayMode.NORMAL) {
        this.playMode = PlayMode.LOOP;
      } else
        this.playMode = PlayMode.LOOP_REVERSED; 
    } else if (!looping && this.playMode != PlayMode.NORMAL && this.playMode != PlayMode.REVERSED) {
      if (this.playMode == PlayMode.LOOP_REVERSED) {
        this.playMode = PlayMode.REVERSED;
      } else
        this.playMode = PlayMode.LOOP; 
    } 
    T frame = getKeyFrame(stateTime);
    this.playMode = oldPlayMode;
    return frame;
  }
  
  public T getKeyFrame(float stateTime) {
    int frameNumber = getKeyFrameIndex(stateTime);
    return this.keyFrames[frameNumber];
  }
  
  public int getKeyFrameIndex(float stateTime) {
    int lastFrameNumber;
    if (this.keyFrames.length == 1)
      return 0; 
    int frameNumber = (int)(stateTime / this.frameDuration);
    switch (this.playMode) {
      case NORMAL:
        frameNumber = Math.min(this.keyFrames.length - 1, frameNumber);
        break;
      case LOOP:
        frameNumber %= this.keyFrames.length;
        break;
      case LOOP_PINGPONG:
        frameNumber %= this.keyFrames.length * 2 - 2;
        if (frameNumber >= this.keyFrames.length)
          frameNumber = this.keyFrames.length - 2 - (frameNumber - this.keyFrames.length); 
        break;
      case LOOP_RANDOM:
        lastFrameNumber = (int)(this.lastStateTime / this.frameDuration);
        if (lastFrameNumber != frameNumber) {
          frameNumber = MathUtils.random(this.keyFrames.length - 1);
        } else
          frameNumber = this.lastFrameNumber; 
        break;
      case REVERSED:
        frameNumber = Math.max(this.keyFrames.length - frameNumber - 1, 0);
        break;
      case LOOP_REVERSED:
        frameNumber %= this.keyFrames.length;
        frameNumber = this.keyFrames.length - frameNumber - 1;
        break;
    } 
    this.lastFrameNumber = frameNumber;
    this.lastStateTime = stateTime;
    return frameNumber;
  }
  
  public T[] getKeyFrames() {
    return this.keyFrames;
  }
  
  protected void setKeyFrames(T... keyFrames) {
    this.keyFrames = keyFrames;
    this.animationDuration = (float)keyFrames.length * this.frameDuration;
  }
  
  public PlayMode getPlayMode() {
    return this.playMode;
  }
  
  public void setPlayMode(PlayMode playMode) {
    this.playMode = playMode;
  }
  
  public boolean isAnimationFinished(float stateTime) {
    int frameNumber = (int)(stateTime / this.frameDuration);
    return (this.keyFrames.length - 1 < frameNumber);
  }
  
  public void setFrameDuration(float frameDuration) {
    this.frameDuration = frameDuration;
    this.animationDuration = (float)this.keyFrames.length * frameDuration;
  }
  
  public float getFrameDuration() {
    return this.frameDuration;
  }
  
  public float getAnimationDuration() {
    return this.animationDuration;
  }
}
