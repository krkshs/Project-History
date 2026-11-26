package com.badlogic.gdx.math;

public abstract class Interpolation {
  public abstract float apply(float paramFloat);
  
  public float apply(float start, float end, float a) {
    return start + (end - start) * apply(a);
  }
  
  public static final Interpolation linear = new Interpolation() {
      public float apply(float a) {
        return a;
      }
    };
  
  public static final Interpolation smooth = new Interpolation() {
      public float apply(float a) {
        return a * a * (3.0F - 2.0F * a);
      }
    };
  
  public static final Interpolation smooth2 = new Interpolation() {
      public float apply(float a) {
        a = a * a * (3.0F - 2.0F * a);
        return a * a * (3.0F - 2.0F * a);
      }
    };
  
  public static final Interpolation smoother = new Interpolation() {
      public float apply(float a) {
        return a * a * a * (a * (a * 6.0F - 15.0F) + 10.0F);
      }
    };
  
  public static final Interpolation fade = smoother;
  
  public static final Pow pow2 = new Pow(2);
  
  public static final PowIn pow2In = new PowIn(2);
  
  public static final PowIn slowFast = pow2In;
  
  public static final PowOut pow2Out = new PowOut(2);
  
  public static final PowOut fastSlow = pow2Out;
  
  public static final Interpolation pow2InInverse = new Interpolation() {
      public float apply(float a) {
        if (a < 1.0E-6F)
          return 0.0F; 
        return (float)Math.sqrt((double)a);
      }
    };
  
  public static final Interpolation pow2OutInverse = new Interpolation() {
      public float apply(float a) {
        if (a < 1.0E-6F)
          return 0.0F; 
        if (a > 1.0F)
          return 1.0F; 
        return 1.0F - (float)Math.sqrt((double)-(a - 1.0F));
      }
    };
  
  public static final Pow pow3 = new Pow(3);
  
  public static final PowIn pow3In = new PowIn(3);
  
  public static final PowOut pow3Out = new PowOut(3);
  
  public static final Interpolation pow3InInverse = new Interpolation() {
      public float apply(float a) {
        return (float)Math.cbrt((double)a);
      }
    };
  
  public static final Interpolation pow3OutInverse = new Interpolation() {
      public float apply(float a) {
        return 1.0F - (float)Math.cbrt((double)-(a - 1.0F));
      }
    };
  
  public static final Pow pow4 = new Pow(4);
  
  public static final PowIn pow4In = new PowIn(4);
  
  public static final PowOut pow4Out = new PowOut(4);
  
  public static final Pow pow5 = new Pow(5);
  
  public static final PowIn pow5In = new PowIn(5);
  
  public static final PowOut pow5Out = new PowOut(5);
  
  public static final Interpolation sine = new Interpolation() {
      public float apply(float a) {
        return (1.0F - MathUtils.cos(a * 3.1415927F)) / 2.0F;
      }
    };
  
  public static final Interpolation sineIn = new Interpolation() {
      public float apply(float a) {
        return 1.0F - MathUtils.cos(a * 1.5707964F);
      }
    };
  
  public static final Interpolation sineOut = new Interpolation() {
      public float apply(float a) {
        return MathUtils.sin(a * 1.5707964F);
      }
    };
  
  public static final Exp exp10 = new Exp(2.0F, 10.0F);
  
  public static final ExpIn exp10In = new ExpIn(2.0F, 10.0F);
  
  public static final ExpOut exp10Out = new ExpOut(2.0F, 10.0F);
  
  public static final Exp exp5 = new Exp(2.0F, 5.0F);
  
  public static final ExpIn exp5In = new ExpIn(2.0F, 5.0F);
  
  public static final ExpOut exp5Out = new ExpOut(2.0F, 5.0F);
  
  public static final Interpolation circle = new Interpolation() {
      public float apply(float a) {
        if (a <= 0.5F) {
          a *= 2.0F;
          return (1.0F - (float)Math.sqrt((double)(1.0F - a * a))) / 2.0F;
        } 
        a--;
        a *= 2.0F;
        return ((float)Math.sqrt((double)(1.0F - a * a)) + 1.0F) / 2.0F;
      }
    };
  
  public static final Interpolation circleIn = new Interpolation() {
      public float apply(float a) {
        return 1.0F - (float)Math.sqrt((double)(1.0F - a * a));
      }
    };
  
  public static final Interpolation circleOut = new Interpolation() {
      public float apply(float a) {
        a--;
        return (float)Math.sqrt((double)(1.0F - a * a));
      }
    };
  
  public static final Elastic elastic = new Elastic(2.0F, 10.0F, 7, 1.0F);
  
  public static final ElasticIn elasticIn = new ElasticIn(2.0F, 10.0F, 6, 1.0F);
  
  public static final ElasticOut elasticOut = new ElasticOut(2.0F, 10.0F, 7, 1.0F);
  
  public static final Swing swing = new Swing(1.5F);
  
  public static final SwingIn swingIn = new SwingIn(2.0F);
  
  public static final SwingOut swingOut = new SwingOut(2.0F);
  
  public static final Bounce bounce = new Bounce(4);
  
  public static final BounceIn bounceIn = new BounceIn(4);
  
  public static final BounceOut bounceOut = new BounceOut(4);
  
  public static class Pow extends Interpolation {
    final int power;
    
    public Pow(int power) {
      this.power = power;
    }
    
    public float apply(float a) {
      if (a <= 0.5F)
        return (float)Math.pow((double)(a * 2.0F), (double)this.power) / 2.0F; 
      return (float)Math.pow((double)((a - 1.0F) * 2.0F), (double)this.power) / (float)((this.power % 2 == 0) ? -2 : 2) + 1.0F;
    }
  }
  
  public static class PowIn extends Pow {
    public PowIn(int power) {
      super(power);
    }
    
    public float apply(float a) {
      return (float)Math.pow((double)a, (double)this.power);
    }
  }
  
  public static class PowOut extends Pow {
    public PowOut(int power) {
      super(power);
    }
    
    public float apply(float a) {
      return (float)Math.pow((double)(a - 1.0F), (double)this.power) * (float)((this.power % 2 == 0) ? -1 : true) + 1.0F;
    }
  }
  
  public static class Exp extends Interpolation {
    final float value;
    
    final float power;
    
    final float min;
    
    final float scale;
    
    public Exp(float value, float power) {
      this.value = value;
      this.power = power;
      this.min = (float)Math.pow((double)value, (double)-power);
      this.scale = 1.0F / (1.0F - this.min);
    }
    
    public float apply(float a) {
      if (a <= 0.5F)
        return ((float)Math.pow((double)this.value, (double)(this.power * (a * 2.0F - 1.0F))) - this.min) * this.scale / 2.0F; 
      return (2.0F - ((float)Math.pow((double)this.value, (double)(-this.power * (a * 2.0F - 1.0F))) - this.min) * this.scale) / 2.0F;
    }
  }
  
  public static class ExpIn extends Exp {
    public ExpIn(float value, float power) {
      super(value, power);
    }
    
    public float apply(float a) {
      return ((float)Math.pow((double)this.value, (double)(this.power * (a - 1.0F))) - this.min) * this.scale;
    }
  }
  
  public static class ExpOut extends Exp {
    public ExpOut(float value, float power) {
      super(value, power);
    }
    
    public float apply(float a) {
      return 1.0F - ((float)Math.pow((double)this.value, (double)(-this.power * a)) - this.min) * this.scale;
    }
  }
  
  public static class Elastic extends Interpolation {
    final float value;
    
    final float power;
    
    final float scale;
    
    final float bounces;
    
    public Elastic(float value, float power, int bounces, float scale) {
      this.value = value;
      this.power = power;
      this.scale = scale;
      this.bounces = (float)bounces * 3.1415927F * (float)((bounces % 2 == 0) ? true : -1);
    }
    
    public float apply(float a) {
      if (a <= 0.5F) {
        a *= 2.0F;
        return (float)Math.pow((double)this.value, (double)(this.power * (a - 1.0F))) * MathUtils.sin(a * this.bounces) * this.scale / 2.0F;
      } 
      a = 1.0F - a;
      a *= 2.0F;
      return 1.0F - (float)Math.pow((double)this.value, (double)(this.power * (a - 1.0F))) * MathUtils.sin(a * this.bounces) * this.scale / 2.0F;
    }
  }
  
  public static class ElasticIn extends Elastic {
    public ElasticIn(float value, float power, int bounces, float scale) {
      super(value, power, bounces, scale);
    }
    
    public float apply(float a) {
      if ((double)a >= 0.99D)
        return 1.0F; 
      return (float)Math.pow((double)this.value, (double)(this.power * (a - 1.0F))) * MathUtils.sin(a * this.bounces) * this.scale;
    }
  }
  
  public static class ElasticOut extends Elastic {
    public ElasticOut(float value, float power, int bounces, float scale) {
      super(value, power, bounces, scale);
    }
    
    public float apply(float a) {
      if (a == 0.0F)
        return 0.0F; 
      a = 1.0F - a;
      return 1.0F - (float)Math.pow((double)this.value, (double)(this.power * (a - 1.0F))) * MathUtils.sin(a * this.bounces) * this.scale;
    }
  }
  
  public static class Bounce extends BounceOut {
    public Bounce(float[] widths, float[] heights) {
      super(widths, heights);
    }
    
    public Bounce(int bounces) {
      super(bounces);
    }
    
    private float out(float a) {
      float test = a + this.widths[0] / 2.0F;
      if (test < this.widths[0])
        return test / (this.widths[0] / 2.0F) - 1.0F; 
      return super.apply(a);
    }
    
    public float apply(float a) {
      if (a <= 0.5F)
        return (1.0F - out(1.0F - a * 2.0F)) / 2.0F; 
      return out(a * 2.0F - 1.0F) / 2.0F + 0.5F;
    }
  }
  
  public static class BounceOut extends Interpolation {
    final float[] widths;
    
    final float[] heights;
    
    public BounceOut(float[] widths, float[] heights) {
      if (widths.length != heights.length)
        throw new IllegalArgumentException("Must be the same number of widths and heights."); 
      this.widths = widths;
      this.heights = heights;
    }
    
    public BounceOut(int bounces) {
      if (bounces < 2 || bounces > 5)
        throw new IllegalArgumentException("bounces cannot be < 2 or > 5: " + bounces); 
      this.widths = new float[bounces];
      this.heights = new float[bounces];
      this.heights[0] = 1.0F;
      switch (bounces) {
        case 2:
          this.widths[0] = 0.6F;
          this.widths[1] = 0.4F;
          this.heights[1] = 0.33F;
          break;
        case 3:
          this.widths[0] = 0.4F;
          this.widths[1] = 0.4F;
          this.widths[2] = 0.2F;
          this.heights[1] = 0.33F;
          this.heights[2] = 0.1F;
          break;
        case 4:
          this.widths[0] = 0.34F;
          this.widths[1] = 0.34F;
          this.widths[2] = 0.2F;
          this.widths[3] = 0.15F;
          this.heights[1] = 0.26F;
          this.heights[2] = 0.11F;
          this.heights[3] = 0.03F;
          break;
        case 5:
          this.widths[0] = 0.3F;
          this.widths[1] = 0.3F;
          this.widths[2] = 0.2F;
          this.widths[3] = 0.1F;
          this.widths[4] = 0.1F;
          this.heights[1] = 0.45F;
          this.heights[2] = 0.3F;
          this.heights[3] = 0.15F;
          this.heights[4] = 0.06F;
          break;
      } 
      this.widths[0] = this.widths[0] * 2.0F;
    }
    
    public float apply(float a) {
      if (a == 1.0F)
        return 1.0F; 
      a += this.widths[0] / 2.0F;
      float width = 0.0F, height = 0.0F;
      for (int i = 0, n = this.widths.length; i < n; i++) {
        width = this.widths[i];
        if (a <= width) {
          height = this.heights[i];
          break;
        } 
        a -= width;
      } 
      a /= width;
      float z = 4.0F / width * height * a;
      return 1.0F - (z - z * a) * width;
    }
  }
  
  public static class BounceIn extends BounceOut {
    public BounceIn(float[] widths, float[] heights) {
      super(widths, heights);
    }
    
    public BounceIn(int bounces) {
      super(bounces);
    }
    
    public float apply(float a) {
      return 1.0F - super.apply(1.0F - a);
    }
  }
  
  public static class Swing extends Interpolation {
    private final float scale;
    
    public Swing(float scale) {
      this.scale = scale * 2.0F;
    }
    
    public float apply(float a) {
      if (a <= 0.5F) {
        a *= 2.0F;
        return a * a * ((this.scale + 1.0F) * a - this.scale) / 2.0F;
      } 
      a--;
      a *= 2.0F;
      return a * a * ((this.scale + 1.0F) * a + this.scale) / 2.0F + 1.0F;
    }
  }
  
  public static class SwingOut extends Interpolation {
    private final float scale;
    
    public SwingOut(float scale) {
      this.scale = scale;
    }
    
    public float apply(float a) {
      a--;
      return a * a * ((this.scale + 1.0F) * a + this.scale) + 1.0F;
    }
  }
  
  public static class SwingIn extends Interpolation {
    private final float scale;
    
    public SwingIn(float scale) {
      this.scale = scale;
    }
    
    public float apply(float a) {
      return a * a * ((this.scale + 1.0F) * a - this.scale);
    }
  }
}
