package org.lwjgl.system.macosx;

import org.lwjgl.system.CallbackI;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.dyncall.DynCallback;

@FunctionalInterface
@NativeType("EnumerationMutationHandler")
public interface EnumerationMutationHandlerI extends CallbackI.V {
  public static final String SIGNATURE = "(p)v";
  
  default String getSignature() {
    return "(p)v";
  }
  
  default void callback(long args) {
    invoke(
        DynCallback.dcbArgPointer(args));
  }
  
  void invoke(@NativeType("id") long paramLong);
}
