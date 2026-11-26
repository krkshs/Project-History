package org.lwjgl.system.jemalloc;

import javax.annotation.Nullable;
import org.lwjgl.system.Callback;

public abstract class ExtentAlloc extends Callback implements ExtentAllocI {
  public static ExtentAlloc create(long functionPointer) {
    ExtentAllocI instance = Callback.<ExtentAllocI>get(functionPointer);
    return (instance instanceof ExtentAlloc) ? (ExtentAlloc)instance : new Container(functionPointer, instance);
  }
  
  @Nullable
  public static ExtentAlloc createSafe(long functionPointer) {
    return (functionPointer == 0L) ? null : create(functionPointer);
  }
  
  public static ExtentAlloc create(ExtentAllocI instance) {
    return (instance instanceof ExtentAlloc) ? (ExtentAlloc)instance : new Container(
        
        instance.address(), instance);
  }
  
  protected ExtentAlloc() {
    super("(ppppppi)p");
  }
  
  ExtentAlloc(long functionPointer) {
    super(functionPointer);
  }
  
  private static final class Container extends ExtentAlloc {
    private final ExtentAllocI delegate;
    
    Container(long functionPointer, ExtentAllocI delegate) {
      super(functionPointer);
      this.delegate = delegate;
    }
    
    public long invoke(long extent_hooks, long new_addr, long size, long alignment, long zero, long commit, int arena_ind) {
      return this.delegate.invoke(extent_hooks, new_addr, size, alignment, zero, commit, arena_ind);
    }
  }
}
