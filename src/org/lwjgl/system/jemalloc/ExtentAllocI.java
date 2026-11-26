package org.lwjgl.system.jemalloc;

import org.lwjgl.system.CallbackI;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.dyncall.DynCallback;

@FunctionalInterface
@NativeType("extent_alloc_t")
public interface ExtentAllocI extends CallbackI.P {
  public static final String SIGNATURE = "(ppppppi)p";
  
  default String getSignature() {
    return "(ppppppi)p";
  }
  
  default long callback(long args) {
    return invoke(
        DynCallback.dcbArgPointer(args), 
        DynCallback.dcbArgPointer(args), 
        DynCallback.dcbArgPointer(args), 
        DynCallback.dcbArgPointer(args), 
        DynCallback.dcbArgPointer(args), 
        DynCallback.dcbArgPointer(args), 
        DynCallback.dcbArgInt(args));
  }
  
  @NativeType("void *")
  long invoke(@NativeType("extent_hooks_t *") long paramLong1, @NativeType("void *") long paramLong2, @NativeType("size_t") long paramLong3, @NativeType("size_t") long paramLong4, @NativeType("bool *") long paramLong5, @NativeType("bool *") long paramLong6, @NativeType("unsigned int") int paramInt);
}
