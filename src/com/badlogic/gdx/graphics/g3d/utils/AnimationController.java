package com.badlogic.gdx.graphics.g3d.utils;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Pool;

public class AnimationController extends BaseAnimationController {
  public static class AnimationDesc {
    public AnimationController.AnimationListener listener;
    
    public Animation animation;
    
    public float speed;
    
    public float time;
    
    public float offset;
    
    public float duration;
    
    public int loopCount;
    
    protected float update(float delta) {
      if (this.loopCount != 0 && this.animation != null) {
        int loops;
        float diff = this.speed * delta;
        if (!MathUtils.isZero(this.duration)) {
          this.time += diff;
          if (this.speed < 0.0F) {
            float invTime = this.duration - this.time;
            loops = (int)Math.abs(invTime / this.duration);
            invTime = Math.abs(invTime % this.duration);
            this.time = this.duration - invTime;
          } else {
            loops = (int)Math.abs(this.time / this.duration);
            this.time = Math.abs(this.time % this.duration);
          } 
        } else
          loops = 1; 
        for (int i = 0; i < loops; i++) {
          if (this.loopCount > 0)
            this.loopCount--; 
          if (this.loopCount != 0 && this.listener != null)
            this.listener.onLoop(this); 
          if (this.loopCount == 0) {
            float result = (float)(loops - 1 - i) * this.duration + ((diff < 0.0F) ? (this.duration - this.time) : this.time);
            this.time = (diff < 0.0F) ? 0.0F : this.duration;
            if (this.listener != null)
              this.listener.onEnd(this); 
            return result;
          } 
        } 
        return -1.0F;
      } 
      return delta;
    }
  }
  
  protected final Pool<AnimationDesc> animationPool = new Pool<AnimationDesc>() {
      protected AnimationController.AnimationDesc newObject() {
        return new AnimationController.AnimationDesc();
      }
    };
  
  public AnimationDesc current;
  
  public AnimationDesc queued;
  
  public float queuedTransitionTime;
  
  public AnimationDesc previous;
  
  public float transitionCurrentTime;
  
  public float transitionTargetTime;
  
  public boolean inAction;
  
  public boolean paused;
  
  public boolean allowSameAnimation;
  
  public static interface AnimationListener {
    void onEnd(AnimationController.AnimationDesc param1AnimationDesc);
    
    void onLoop(AnimationController.AnimationDesc param1AnimationDesc);
  }
  
  private boolean justChangedAnimation = false;
  
  public AnimationController(ModelInstance target) {
    super(target);
  }
  
  private AnimationDesc obtain(Animation anim, float offset, float duration, int loopCount, float speed, AnimationListener listener) {
    if (anim == null)
      return null; 
    AnimationDesc result = this.animationPool.obtain();
    result.animation = anim;
    result.listener = listener;
    result.loopCount = loopCount;
    result.speed = speed;
    result.offset = offset;
    result.duration = (duration < 0.0F) ? (anim.duration - offset) : duration;
    result.time = (speed < 0.0F) ? result.duration : 0.0F;
    return result;
  }
  
  private AnimationDesc obtain(String id, float offset, float duration, int loopCount, float speed, AnimationListener listener) {
    if (id == null)
      return null; 
    Animation anim = this.target.getAnimation(id);
    if (anim == null)
      throw new GdxRuntimeException("Unknown animation: " + id); 
    return obtain(anim, offset, duration, loopCount, speed, listener);
  }
  
  private AnimationDesc obtain(AnimationDesc anim) {
    return obtain(anim.animation, anim.offset, anim.duration, anim.loopCount, anim.speed, anim.listener);
  }
  
  public void update(float delta) {
    if (this.paused)
      return; 
    if (this.previous != null && (this.transitionCurrentTime += delta) >= this.transitionTargetTime) {
      removeAnimation(this.previous.animation);
      this.justChangedAnimation = true;
      this.animationPool.free(this.previous);
      this.previous = null;
    } 
    if (this.justChangedAnimation) {
      this.target.calculateTransforms();
      this.justChangedAnimation = false;
    } 
    if (this.current == null || this.current.loopCount == 0 || this.current.animation == null)
      return; 
    float remain = this.current.update(delta);
    if (remain >= 0.0F && this.queued != null) {
      this.inAction = false;
      animate(this.queued, this.queuedTransitionTime);
      this.queued = null;
      if (remain > 0.0F)
        update(remain); 
      return;
    } 
    if (this.previous != null) {
      applyAnimations(this.previous.animation, this.previous.offset + this.previous.time, this.current.animation, this.current.offset + this.current.time, this.transitionCurrentTime / this.transitionTargetTime);
    } else
      applyAnimation(this.current.animation, this.current.offset + this.current.time); 
  }
  
  public AnimationDesc setAnimation(String id) {
    return setAnimation(id, 1, 1.0F, null);
  }
  
  public AnimationDesc setAnimation(String id, int loopCount) {
    return setAnimation(id, loopCount, 1.0F, null);
  }
  
  public AnimationDesc setAnimation(String id, AnimationListener listener) {
    return setAnimation(id, 1, 1.0F, listener);
  }
  
  public AnimationDesc setAnimation(String id, int loopCount, AnimationListener listener) {
    return setAnimation(id, loopCount, 1.0F, listener);
  }
  
  public AnimationDesc setAnimation(String id, int loopCount, float speed, AnimationListener listener) {
    return setAnimation(id, 0.0F, -1.0F, loopCount, speed, listener);
  }
  
  public AnimationDesc setAnimation(String id, float offset, float duration, int loopCount, float speed, AnimationListener listener) {
    return setAnimation(obtain(id, offset, duration, loopCount, speed, listener));
  }
  
  protected AnimationDesc setAnimation(Animation anim, float offset, float duration, int loopCount, float speed, AnimationListener listener) {
    return setAnimation(obtain(anim, offset, duration, loopCount, speed, listener));
  }
  
  protected AnimationDesc setAnimation(AnimationDesc anim) {
    if (this.current == null) {
      this.current = anim;
    } else {
      if (!this.allowSameAnimation && anim != null && this.current.animation == anim.animation) {
        anim.time = this.current.time;
      } else
        removeAnimation(this.current.animation); 
      this.animationPool.free(this.current);
      this.current = anim;
    } 
    this.justChangedAnimation = true;
    return anim;
  }
  
  public AnimationDesc animate(String id, float transitionTime) {
    return animate(id, 1, 1.0F, null, transitionTime);
  }
  
  public AnimationDesc animate(String id, AnimationListener listener, float transitionTime) {
    return animate(id, 1, 1.0F, listener, transitionTime);
  }
  
  public AnimationDesc animate(String id, int loopCount, AnimationListener listener, float transitionTime) {
    return animate(id, loopCount, 1.0F, listener, transitionTime);
  }
  
  public AnimationDesc animate(String id, int loopCount, float speed, AnimationListener listener, float transitionTime) {
    return animate(id, 0.0F, -1.0F, loopCount, speed, listener, transitionTime);
  }
  
  public AnimationDesc animate(String id, float offset, float duration, int loopCount, float speed, AnimationListener listener, float transitionTime) {
    return animate(obtain(id, offset, duration, loopCount, speed, listener), transitionTime);
  }
  
  protected AnimationDesc animate(Animation anim, float offset, float duration, int loopCount, float speed, AnimationListener listener, float transitionTime) {
    return animate(obtain(anim, offset, duration, loopCount, speed, listener), transitionTime);
  }
  
  protected AnimationDesc animate(AnimationDesc anim, float transitionTime) {
    if (this.current == null || this.current.loopCount == 0) {
      this.current = anim;
    } else if (this.inAction) {
      queue(anim, transitionTime);
    } else if (!this.allowSameAnimation && anim != null && this.current.animation == anim.animation) {
      anim.time = this.current.time;
      this.animationPool.free(this.current);
      this.current = anim;
    } else {
      if (this.previous != null) {
        removeAnimation(this.previous.animation);
        this.animationPool.free(this.previous);
      } 
      this.previous = this.current;
      this.current = anim;
      this.transitionCurrentTime = 0.0F;
      this.transitionTargetTime = transitionTime;
    } 
    return anim;
  }
  
  public AnimationDesc queue(String id, int loopCount, float speed, AnimationListener listener, float transitionTime) {
    return queue(id, 0.0F, -1.0F, loopCount, speed, listener, transitionTime);
  }
  
  public AnimationDesc queue(String id, float offset, float duration, int loopCount, float speed, AnimationListener listener, float transitionTime) {
    return queue(obtain(id, offset, duration, loopCount, speed, listener), transitionTime);
  }
  
  protected AnimationDesc queue(Animation anim, float offset, float duration, int loopCount, float speed, AnimationListener listener, float transitionTime) {
    return queue(obtain(anim, offset, duration, loopCount, speed, listener), transitionTime);
  }
  
  protected AnimationDesc queue(AnimationDesc anim, float transitionTime) {
    if (this.current == null || this.current.loopCount == 0) {
      animate(anim, transitionTime);
    } else {
      if (this.queued != null)
        this.animationPool.free(this.queued); 
      this.queued = anim;
      this.queuedTransitionTime = transitionTime;
      if (this.current.loopCount < 0)
        this.current.loopCount = 1; 
    } 
    return anim;
  }
  
  public AnimationDesc action(String id, int loopCount, float speed, AnimationListener listener, float transitionTime) {
    return action(id, 0.0F, -1.0F, loopCount, speed, listener, transitionTime);
  }
  
  public AnimationDesc action(String id, float offset, float duration, int loopCount, float speed, AnimationListener listener, float transitionTime) {
    return action(obtain(id, offset, duration, loopCount, speed, listener), transitionTime);
  }
  
  protected AnimationDesc action(Animation anim, float offset, float duration, int loopCount, float speed, AnimationListener listener, float transitionTime) {
    return action(obtain(anim, offset, duration, loopCount, speed, listener), transitionTime);
  }
  
  protected AnimationDesc action(AnimationDesc anim, float transitionTime) {
    if (anim.loopCount < 0)
      throw new GdxRuntimeException("An action cannot be continuous"); 
    if (this.current == null || this.current.loopCount == 0) {
      animate(anim, transitionTime);
    } else {
      AnimationDesc toQueue = this.inAction ? null : obtain(this.current);
      this.inAction = false;
      animate(anim, transitionTime);
      this.inAction = true;
      if (toQueue != null)
        queue(toQueue, transitionTime); 
    } 
    return anim;
  }
}
