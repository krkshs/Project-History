package org.lwjgl.system.jemalloc;

import org.lwjgl.system.CallbackI;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.dyncall.DynCallback;

@FunctionalInterface
@NativeType("extent_decommit_t")
public interface ExtentDecommitI extends CallbackI.Z {
  public static final String SIGNATURE = "(pppppi)B";
  
  default String getSignature() {
    return "(pppppi)B";
  }
  
  default boolean callback(long args) {
    return invoke(
        DynCallback.dcbArgPointer(args), 
        DynCallback.dcbArgPointer(args), 
        DynCallback.dcbArgPointer(args), 
        DynCallback.dcbArgPointer(args), 
        DynCallback.dcbArgPointer(args), 
        DynCallback.dcbArgInt(args));
  }
  
  @NativeType("bool")
  boolean invoke(@NativeType("extent_hooks_t *") long paramLong1, @NativeType("void *") long paramLong2, @NativeType("size_t") long paramLong3, @NativeType("size_t") long paramLong4, @NativeType("size_t") long paramLong5, @NativeType("unsigned int") int paramInt);
}
