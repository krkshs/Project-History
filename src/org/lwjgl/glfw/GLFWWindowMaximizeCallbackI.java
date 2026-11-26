package org.lwjgl.glfw;

import org.lwjgl.system.CallbackI;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.dyncall.DynCallback;

@FunctionalInterface
@NativeType("GLFWwindowmaximizefun")
public interface GLFWWindowMaximizeCallbackI extends CallbackI.V {
  public static final String SIGNATURE = "(pi)v";
  
  default String getSignature() {
    return "(pi)v";
  }
  
  default void callback(long args) {
    invoke(
        DynCallback.dcbArgPointer(args), 
        (DynCallback.dcbArgInt(args) != 0));
  }
  
  void invoke(@NativeType("GLFWwindow *") long paramLong, @NativeType("int") boolean paramBoolean);
}
