package space.earlygrey.shapedrawer;

import com.badlogic.gdx.math.Vector2;

public class ShapeUtils {
  public static final float EPSILON = 0.001F;
  
  public static final float PI = 3.1415927F;
  
  public static final float PI2 = 6.2831855F;
  
  public static final float PI_2 = 1.5707964F;
  
  public static final float PI_4 = 0.7853982F;
  
  public static final float SQRT2 = (float)Math.sqrt(2.0D);
  
  public static final float SQRT3 = (float)Math.sqrt(3.0D);
  
  public static class ConstantLineWidth implements LineWidthFunction {
    float width;
    
    public float getWidth(int i, float t) {
      return this.width;
    }
    
    public ConstantLineWidth width(float width) {
      this.width = width;
      return this;
    }
  }
  
  public static float snap(float a, float pixelSize, float halfPixelSize) {
    return (float)Math.round(a / pixelSize) * pixelSize + halfPixelSize;
  }
  
  public static boolean epsilonEquals(float a, float b) {
    return (Math.abs(a - b) < 0.001F);
  }
  
  public static float normaliseAngleToPositive(float angle) {
    angle %= 6.2831855F;
    angle = (angle + 6.2831855F) % 6.2831855F;
    return angle;
  }
  
  public static float floor(float x, float interval) {
    return (float)(Math.floor((double)(x / interval)) * (double)interval);
  }
  
  public static float ceil(float x, float interval) {
    return (float)(Math.ceil((double)(x / interval)) * (double)interval);
  }
  
  public static float angleRad(Vector2 v, Vector2 reference) {
    return (float)Math.atan2((double)(reference.x * v.y - reference.y * v.x), (double)(v.x * reference.x + v.y * reference.y));
  }
  
  static float pathLength(float[] path) {
    if (path.length < 4)
      return 0.0F; 
    float l = 0.0F;
    for (int i = 0; i < path.length - 4; i += 2)
      l += Vector2.dst(path[i], path[i + 1], path[i + 2], path[i + 3]); 
    return l;
  }
  
  public static interface LineWidthFunction {
    float getWidth(int param1Int, float param1Float);
  }
}
