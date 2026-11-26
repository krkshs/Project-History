package org.lwjgl.glfw;

import org.lwjgl.system.CallbackI;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.dyncall.DynCallback;

@FunctionalInterface
@NativeType("GLFWdropfun")
public interface GLFWDropCallbackI extends CallbackI.V {
  public static final String SIGNATURE = "(pip)v";
  
  default String getSignature() {
    return "(pip)v";
  }
  
  default void callback(long args) {
    invoke(
        DynCallback.dcbArgPointer(args), 
        DynCallback.dcbArgInt(args), 
        DynCallback.dcbArgPointer(args));
  }
  
  void invoke(@NativeType("GLFWwindow *") long paramLong1, int paramInt, @NativeType("char const **") long paramLong2);
}
