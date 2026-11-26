package com.badlogic.gdx.utils;

public class Pools {
  private static final ObjectMap<Class, Pool> typePools = new ObjectMap<>();
  
  public static <T> Pool<T> get(Class<T> type, int max) {
    Pool<T> pool = typePools.<Class<T>>get(type);
    if (pool == null) {
      pool = new ReflectionPool<>(type, 4, max);
      typePools.put(type, pool);
    } 
    return pool;
  }
  
  public static <T> Pool<T> get(Class<T> type) {
    return Pools.<T>get(type, 100);
  }
  
  public static <T> void set(Class<T> type, Pool<T> pool) {
    typePools.put(type, pool);
  }
  
  public static <T> T obtain(Class<T> type) {
    return Pools.<T>get(type).obtain();
  }
  
  public static void free(Object object) {
    if (object == null)
      throw new IllegalArgumentException("object cannot be null."); 
    Pool pool = typePools.<Class<?>>get(object.getClass());
    if (pool == null)
      return; 
    pool.free(object);
  }
  
  public static void freeAll(Array objects) {
    freeAll(objects, false);
  }
  
  public static void freeAll(Array objects, boolean samePool) {
    if (objects == null)
      throw new IllegalArgumentException("objects cannot be null."); 
    Pool pool = null;
    for (int i = 0, n = objects.size; i < n; i++) {
      Object object = objects.get(i);
      if (object == null)
        continue; 
      if (pool == null) {
        pool = typePools.<Class<?>>get(object.getClass());
        if (pool == null)
          continue; 
      } 
      pool.free(object);
      if (!samePool)
        pool = null; 
    } 
  }
}
