package org.lwjgl.system.jemalloc;

import org.lwjgl.system.MemoryUtil;

public class JEmallocAllocator implements MemoryUtil.MemoryAllocator {
  static {
    JEmalloc.getLibrary();
  }
  
  public long getMalloc() {
    return JEmalloc.Functions.malloc;
  }
  
  public long getCalloc() {
    return JEmalloc.Functions.calloc;
  }
  
  public long getRealloc() {
    return JEmalloc.Functions.realloc;
  }
  
  public long getFree() {
    return JEmalloc.Functions.free;
  }
  
  public long getAlignedAlloc() {
    return JEmalloc.Functions.aligned_alloc;
  }
  
  public long getAlignedFree() {
    return JEmalloc.Functions.free;
  }
  
  public long malloc(long size) {
    return JEmalloc.nje_malloc(size);
  }
  
  public long calloc(long num, long size) {
    return JEmalloc.nje_calloc(num, size);
  }
  
  public long realloc(long ptr, long size) {
    return JEmalloc.nje_realloc(ptr, size);
  }
  
  public void free(long ptr) {
    JEmalloc.nje_free(ptr);
  }
  
  public long aligned_alloc(long alignment, long size) {
    return JEmalloc.nje_aligned_alloc(alignment, size);
  }
  
  public void aligned_free(long ptr) {
    JEmalloc.nje_free(ptr);
  }
}
