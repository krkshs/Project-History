package org.lwjgl.glfw;

import org.lwjgl.system.CallbackI;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.dyncall.DynCallback;

@FunctionalInterface
@NativeType("GLFWcursorposfun")
public interface GLFWCursorPosCallbackI extends CallbackI.V {
  public static final String SIGNATURE = "(pdd)v";
  
  default String getSignature() {
    return "(pdd)v";
  }
  
  default void callback(long args) {
    invoke(
        DynCallback.dcbArgPointer(args), 
        DynCallback.dcbArgDouble(args), 
        DynCallback.dcbArgDouble(args));
  }
  
  void invoke(@NativeType("GLFWwindow *") long paramLong, double paramDouble1, double paramDouble2);
}
