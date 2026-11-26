package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.utils.Null;

public abstract class Value {
  public float get() {
    return get(null);
  }
  
  public static final Fixed zero = new Fixed(0.0F);
  
  public static class Fixed extends Value {
    static final Fixed[] cache = new Fixed[111];
    
    private final float value;
    
    public Fixed(float value) {
      this.value = value;
    }
    
    public float get(@Null Actor context) {
      return this.value;
    }
    
    public String toString() {
      return Float.toString(this.value);
    }
    
    public static Fixed valueOf(float value) {
      if (value == 0.0F)
        return zero; 
      if (value >= -10.0F && value <= 100.0F && value == (float)(int)value) {
        Fixed fixed = cache[(int)value + 10];
        if (fixed == null)
          cache[(int)value + 10] = fixed = new Fixed(value); 
        return fixed;
      } 
      return new Fixed(value);
    }
  }
  
  public static Value minWidth = new Value() {
      public float get(@Null Actor context) {
        if (context instanceof Layout)
          return ((Layout)context).getMinWidth(); 
        return (context == null) ? 0.0F : context.getWidth();
      }
    };
  
  public static Value minHeight = new Value() {
      public float get(@Null Actor context) {
        if (context instanceof Layout)
          return ((Layout)context).getMinHeight(); 
        return (context == null) ? 0.0F : context.getHeight();
      }
    };
  
  public static Value prefWidth = new Value() {
      public float get(@Null Actor context) {
        if (context instanceof Layout)
          return ((Layout)context).getPrefWidth(); 
        return (context == null) ? 0.0F : context.getWidth();
      }
    };
  
  public static Value prefHeight = new Value() {
      public float get(@Null Actor context) {
        if (context instanceof Layout)
          return ((Layout)context).getPrefHeight(); 
        return (context == null) ? 0.0F : context.getHeight();
      }
    };
  
  public static Value maxWidth = new Value() {
      public float get(@Null Actor context) {
        if (context instanceof Layout)
          return ((Layout)context).getMaxWidth(); 
        return (context == null) ? 0.0F : context.getWidth();
      }
    };
  
  public static Value maxHeight = new Value() {
      public float get(@Null Actor context) {
        if (context instanceof Layout)
          return ((Layout)context).getMaxHeight(); 
        return (context == null) ? 0.0F : context.getHeight();
      }
    };
  
  public abstract float get(@Null Actor paramActor);
  
  public static Value percentWidth(final float percent) {
    return new Value() {
        public float get(@Null Actor actor) {
          return actor.getWidth() * percent;
        }
      };
  }
  
  public static Value percentHeight(final float percent) {
    return new Value() {
        public float get(@Null Actor actor) {
          return actor.getHeight() * percent;
        }
      };
  }
  
  public static Value percentWidth(final float percent, final Actor actor) {
    if (actor == null)
      throw new IllegalArgumentException("actor cannot be null."); 
    return new Value() {
        public float get(@Null Actor context) {
          return actor.getWidth() * percent;
        }
      };
  }
  
  public static Value percentHeight(final float percent, final Actor actor) {
    if (actor == null)
      throw new IllegalArgumentException("actor cannot be null."); 
    return new Value() {
        public float get(@Null Actor context) {
          return actor.getHeight() * percent;
        }
      };
  }
}
