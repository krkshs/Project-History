package org.lwjgl.system.jemalloc;

import javax.annotation.Nullable;
import org.lwjgl.system.Callback;

public abstract class ExtentMerge extends Callback implements ExtentMergeI {
  public static ExtentMerge create(long functionPointer) {
    ExtentMergeI instance = Callback.<ExtentMergeI>get(functionPointer);
    return (instance instanceof ExtentMerge) ? (ExtentMerge)instance : new Container(functionPointer, instance);
  }
  
  @Nullable
  public static ExtentMerge createSafe(long functionPointer) {
    return (functionPointer == 0L) ? null : create(functionPointer);
  }
  
  public static ExtentMerge create(ExtentMergeI instance) {
    return (instance instanceof ExtentMerge) ? (ExtentMerge)instance : new Container(
        
        instance.address(), instance);
  }
  
  protected ExtentMerge() {
    super("(pppppBi)B");
  }
  
  ExtentMerge(long functionPointer) {
    super(functionPointer);
  }
  
  private static final class Container extends ExtentMerge {
    private final ExtentMergeI delegate;
    
    Container(long functionPointer, ExtentMergeI delegate) {
      super(functionPointer);
      this.delegate = delegate;
    }
    
    public boolean invoke(long extent_hooks, long addr_a, long size_a, long addr_b, long size_b, boolean committed, int arena_ind) {
      return this.delegate.invoke(extent_hooks, addr_a, size_a, addr_b, size_b, committed, arena_ind);
    }
  }
}
