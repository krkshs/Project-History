package org.lwjgl.system.jemalloc;

import org.lwjgl.system.CallbackI;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.dyncall.DynCallback;

@FunctionalInterface
@NativeType("void (*) (void *, char const *)")
public interface MallocMessageCallbackI extends CallbackI.V {
  public static final String SIGNATURE = "(pp)v";
  
  default String getSignature() {
    return "(pp)v";
  }
  
  default void callback(long args) {
    invoke(
        DynCallback.dcbArgPointer(args), 
        DynCallback.dcbArgPointer(args));
  }
  
  void invoke(@NativeType("void *") long paramLong1, @NativeType("char const *") long paramLong2);
}
