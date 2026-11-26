package com.badlogic.gdx.math;

import java.util.Random;

public final class MathUtils {
  public static final float nanoToSec = 1.0E-9F;
  
  public static final float FLOAT_ROUNDING_ERROR = 1.0E-6F;
  
  public static final float PI = 3.1415927F;
  
  public static final float PI2 = 6.2831855F;
  
  public static final float HALF_PI = 1.5707964F;
  
  public static final float E = 2.7182817F;
  
  private static final int SIN_BITS = 14;
  
  private static final int SIN_MASK = 16383;
  
  private static final int SIN_COUNT = 16384;
  
  private static final float radFull = 6.2831855F;
  
  private static final float degFull = 360.0F;
  
  private static final float radToIndex = 2607.5945F;
  
  private static final float degToIndex = 45.511112F;
  
  public static final float radiansToDegrees = 57.295776F;
  
  public static final float radDeg = 57.295776F;
  
  public static final float degreesToRadians = 0.017453292F;
  
  public static final float degRad = 0.017453292F;
  
  private static class Sin {
    static final float[] table = new float[16384];
    
    static {
      for (int j = 0; j < 16384; j++)
        table[j] = (float)Math.sin((double)(((float)j + 0.5F) / 16384.0F * 6.2831855F)); 
      for (int i = 0; i < 360; i += 90)
        table[(int)((float)i * 45.511112F) & 0x3FFF] = (float)Math.sin((double)((float)i * 0.017453292F)); 
    }
  }
  
  public static float sin(float radians) {
    return Sin.table[(int)(radians * 2607.5945F) & 0x3FFF];
  }
  
  public static float cos(float radians) {
    return Sin.table[(int)((radians + 1.5707964F) * 2607.5945F) & 0x3FFF];
  }
  
  public static float sinDeg(float degrees) {
    return Sin.table[(int)(degrees * 45.511112F) & 0x3FFF];
  }
  
  public static float cosDeg(float degrees) {
    return Sin.table[(int)((degrees + 90.0F) * 45.511112F) & 0x3FFF];
  }
  
  public static float atan2(float y, float x) {
    if (x == 0.0F) {
      if (y > 0.0F)
        return 1.5707964F; 
      if (y == 0.0F)
        return 0.0F; 
      return -1.5707964F;
    } 
    float z = y / x;
    if (Math.abs(z) < 1.0F) {
      float f = z / (1.0F + 0.28F * z * z);
      if (x < 0.0F)
        return f + ((y < 0.0F) ? -3.1415927F : 3.1415927F); 
      return f;
    } 
    float atan = 1.5707964F - z / (z * z + 0.28F);
    return (y < 0.0F) ? (atan - 3.1415927F) : atan;
  }
  
  public static float acos(float a) {
    float a2 = a * a;
    float a3 = a * a2;
    if (a >= 0.0F)
      return (float)Math.sqrt((double)(1.0F - a)) * (1.5707288F - 0.2121144F * a + 0.074261F * a2 - 0.0187293F * a3); 
    return 3.1415927F - (float)Math.sqrt((double)(1.0F + a)) * (1.5707288F + 0.2121144F * a + 0.074261F * a2 + 0.0187293F * a3);
  }
  
  public static float asin(float a) {
    float a2 = a * a;
    float a3 = a * a2;
    if (a >= 0.0F)
      return 1.5707964F - (float)Math.sqrt((double)(1.0F - a)) * (1.5707288F - 0.2121144F * a + 0.074261F * a2 - 0.0187293F * a3); 
    return -1.5707964F + (float)Math.sqrt((double)(1.0F + a)) * (1.5707288F + 0.2121144F * a + 0.074261F * a2 + 0.0187293F * a3);
  }
  
  public static Random random = new RandomXS128();
  
  private static final int BIG_ENOUGH_INT = 16384;
  
  private static final double BIG_ENOUGH_FLOOR = 16384.0D;
  
  private static final double CEIL = 0.9999999D;
  
  private static final double BIG_ENOUGH_CEIL = 16384.999999999996D;
  
  private static final double BIG_ENOUGH_ROUND = 16384.5D;
  
  public static int random(int range) {
    return random.nextInt(range + 1);
  }
  
  public static int random(int start, int end) {
    return start + random.nextInt(end - start + 1);
  }
  
  public static long random(long range) {
    return random(0L, range);
  }
  
  public static long random(long start, long end) {
    long rand = random.nextLong();
    if (end < start) {
      long t = end;
      end = start;
      start = t;
    } 
    long bound = end - start + 1L;
    long randLow = rand & 0xFFFFFFFFL;
    long boundLow = bound & 0xFFFFFFFFL;
    long randHigh = rand >>> 32L;
    long boundHigh = bound >>> 32L;
    return start + (randHigh * boundLow >>> 32L) + (randLow * boundHigh >>> 32L) + randHigh * boundHigh;
  }
  
  public static boolean randomBoolean() {
    return random.nextBoolean();
  }
  
  public static boolean randomBoolean(float chance) {
    return (random() < chance);
  }
  
  public static float random() {
    return random.nextFloat();
  }
  
  public static float random(float range) {
    return random.nextFloat() * range;
  }
  
  public static float random(float start, float end) {
    return start + random.nextFloat() * (end - start);
  }
  
  public static int randomSign() {
    return 0x1 | random.nextInt() >> 31;
  }
  
  public static float randomTriangular() {
    return random.nextFloat() - random.nextFloat();
  }
  
  public static float randomTriangular(float max) {
    return (random.nextFloat() - random.nextFloat()) * max;
  }
  
  public static float randomTriangular(float min, float max) {
    return randomTriangular(min, max, (min + max) * 0.5F);
  }
  
  public static float randomTriangular(float min, float max, float mode) {
    float u = random.nextFloat();
    float d = max - min;
    if (u <= (mode - min) / d)
      return min + (float)Math.sqrt((double)(u * d * (mode - min))); 
    return max - (float)Math.sqrt((double)((1.0F - u) * d * (max - mode)));
  }
  
  public static int nextPowerOfTwo(int value) {
    if (value == 0)
      return 1; 
    value--;
    value |= value >> 1;
    value |= value >> 2;
    value |= value >> 4;
    value |= value >> 8;
    value |= value >> 16;
    return value + 1;
  }
  
  public static boolean isPowerOfTwo(int value) {
    return (value != 0 && (value & value - 1) == 0);
  }
  
  public static short clamp(short value, short min, short max) {
    if (value < min)
      return min; 
    if (value > max)
      return max; 
    return value;
  }
  
  public static int clamp(int value, int min, int max) {
    if (value < min)
      return min; 
    if (value > max)
      return max; 
    return value;
  }
  
  public static long clamp(long value, long min, long max) {
    if (value < min)
      return min; 
    if (value > max)
      return max; 
    return value;
  }
  
  public static float clamp(float value, float min, float max) {
    if (value < min)
      return min; 
    if (value > max)
      return max; 
    return value;
  }
  
  public static double clamp(double value, double min, double max) {
    if (value < min)
      return min; 
    if (value > max)
      return max; 
    return value;
  }
  
  public static float lerp(float fromValue, float toValue, float progress) {
    return fromValue + (toValue - fromValue) * progress;
  }
  
  public static float norm(float rangeStart, float rangeEnd, float value) {
    return (value - rangeStart) / (rangeEnd - rangeStart);
  }
  
  public static float map(float inRangeStart, float inRangeEnd, float outRangeStart, float outRangeEnd, float value) {
    return outRangeStart + (value - inRangeStart) * (outRangeEnd - outRangeStart) / (inRangeEnd - inRangeStart);
  }
  
  public static float lerpAngle(float fromRadians, float toRadians, float progress) {
    float delta = (toRadians - fromRadians + 6.2831855F + 3.1415927F) % 6.2831855F - 3.1415927F;
    return (fromRadians + delta * progress + 6.2831855F) % 6.2831855F;
  }
  
  public static float lerpAngleDeg(float fromDegrees, float toDegrees, float progress) {
    float delta = (toDegrees - fromDegrees + 360.0F + 180.0F) % 360.0F - 180.0F;
    return (fromDegrees + delta * progress + 360.0F) % 360.0F;
  }
  
  public static int floor(float value) {
    return (int)((double)value + 16384.0D) - 16384;
  }
  
  public static int floorPositive(float value) {
    return (int)value;
  }
  
  public static int ceil(float value) {
    return 16384 - (int)(16384.0D - (double)value);
  }
  
  public static int ceilPositive(float value) {
    return (int)((double)value + 0.9999999D);
  }
  
  public static int round(float value) {
    return (int)((double)value + 16384.5D) - 16384;
  }
  
  public static int roundPositive(float value) {
    return (int)(value + 0.5F);
  }
  
  public static boolean isZero(float value) {
    return (Math.abs(value) <= 1.0E-6F);
  }
  
  public static boolean isZero(float value, float tolerance) {
    return (Math.abs(value) <= tolerance);
  }
  
  public static boolean isEqual(float a, float b) {
    return (Math.abs(a - b) <= 1.0E-6F);
  }
  
  public static boolean isEqual(float a, float b, float tolerance) {
    return (Math.abs(a - b) <= tolerance);
  }
  
  public static float log(float a, float value) {
    return (float)(Math.log((double)value) / Math.log((double)a));
  }
  
  public static float log2(float value) {
    return log(2.0F, value);
  }
}
