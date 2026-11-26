package org.lwjgl.system.jemalloc;

import javax.annotation.Nullable;
import org.lwjgl.system.Callback;

public abstract class ExtentPurge extends Callback implements ExtentPurgeI {
  public static ExtentPurge create(long functionPointer) {
    ExtentPurgeI instance = Callback.<ExtentPurgeI>get(functionPointer);
    return (instance instanceof ExtentPurge) ? (ExtentPurge)instance : new Container(functionPointer, instance);
  }
  
  @Nullable
  public static ExtentPurge createSafe(long functionPointer) {
    return (functionPointer == 0L) ? null : create(functionPointer);
  }
  
  public static ExtentPurge create(ExtentPurgeI instance) {
    return (instance instanceof ExtentPurge) ? (ExtentPurge)instance : new Container(
        
        instance.address(), instance);
  }
  
  protected ExtentPurge() {
    super("(pppppi)B");
  }
  
  ExtentPurge(long functionPointer) {
    super(functionPointer);
  }
  
  private static final class Container extends ExtentPurge {
    private final ExtentPurgeI delegate;
    
    Container(long functionPointer, ExtentPurgeI delegate) {
      super(functionPointer);
      this.delegate = delegate;
    }
    
    public boolean invoke(long extent_hooks, long addr, long size, long offset, long length, int arena_ind) {
      return this.delegate.invoke(extent_hooks, addr, size, offset, length, arena_ind);
    }
  }
}
