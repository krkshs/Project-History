package org.lwjgl.glfw;

import org.lwjgl.system.CallbackI;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.dyncall.DynCallback;

@FunctionalInterface
@NativeType("GLFWkeyfun")
public interface GLFWKeyCallbackI extends CallbackI.V {
  public static final String SIGNATURE = "(piiii)v";
  
  default String getSignature() {
    return "(piiii)v";
  }
  
  default void callback(long args) {
    invoke(
        DynCallback.dcbArgPointer(args), 
        DynCallback.dcbArgInt(args), 
        DynCallback.dcbArgInt(args), 
        DynCallback.dcbArgInt(args), 
        DynCallback.dcbArgInt(args));
  }
  
  void invoke(@NativeType("GLFWwindow *") long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
}
