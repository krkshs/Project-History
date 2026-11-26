package org.lwjgl.glfw;

import org.lwjgl.system.CallbackI;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.dyncall.DynCallback;

@FunctionalInterface
@NativeType("GLFWwindowcontentscalefun")
public interface GLFWWindowContentScaleCallbackI extends CallbackI.V {
  public static final String SIGNATURE = "(pff)v";
  
  default String getSignature() {
    return "(pff)v";
  }
  
  default void callback(long args) {
    invoke(
        DynCallback.dcbArgPointer(args), 
        DynCallback.dcbArgFloat(args), 
        DynCallback.dcbArgFloat(args));
  }
  
  void invoke(@NativeType("GLFWwindow *") long paramLong, float paramFloat1, float paramFloat2);
}
