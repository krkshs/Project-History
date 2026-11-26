package org.lwjgl.glfw;

import org.lwjgl.system.CallbackI;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.dyncall.DynCallback;

@FunctionalInterface
@NativeType("GLFWerrorfun")
public interface GLFWErrorCallbackI extends CallbackI.V {
  public static final String SIGNATURE = "(ip)v";
  
  default String getSignature() {
    return "(ip)v";
  }
  
  default void callback(long args) {
    invoke(
        DynCallback.dcbArgInt(args), 
        DynCallback.dcbArgPointer(args));
  }
  
  void invoke(int paramInt, @NativeType("char *") long paramLong);
}
