package org.lwjgl.glfw;

import org.lwjgl.system.CallbackI;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.dyncall.DynCallback;

@FunctionalInterface
@NativeType("GLFWjoystickfun")
public interface GLFWJoystickCallbackI extends CallbackI.V {
  public static final String SIGNATURE = "(ii)v";
  
  default String getSignature() {
    return "(ii)v";
  }
  
  default void callback(long args) {
    invoke(
        DynCallback.dcbArgInt(args), 
        DynCallback.dcbArgInt(args));
  }
  
  void invoke(int paramInt1, int paramInt2);
}
