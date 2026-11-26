package org.lwjgl.system.jemalloc;

import org.lwjgl.system.CallbackI;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.dyncall.DynCallback;

@FunctionalInterface
@NativeType("extent_dalloc_t")
public interface ExtentDallocI extends CallbackI.Z {
  public static final String SIGNATURE = "(pppBi)B";
  
  default String getSignature() {
    return "(pppBi)B";
  }
  
  default boolean callback(long args) {
    return invoke(
        DynCallback.dcbArgPointer(args), 
        DynCallback.dcbArgPointer(args), 
        DynCallback.dcbArgPointer(args), 
        DynCallback.dcbArgBool(args), 
        DynCallback.dcbArgInt(args));
  }
  
  @NativeType("bool")
  boolean invoke(@NativeType("extent_hooks_t *") long paramLong1, @NativeType("void *") long paramLong2, @NativeType("size_t") long paramLong3, @NativeType("bool") boolean paramBoolean, @NativeType("unsigned int") int paramInt);
}
