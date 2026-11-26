package com.badlogic.gdx.math;

public interface Path<T> {
  T derivativeAt(T paramT, float paramFloat);
  
  T valueAt(T paramT, float paramFloat);
  
  float approximate(T paramT);
  
  float locate(T paramT);
  
  float approxLength(int paramInt);
}
