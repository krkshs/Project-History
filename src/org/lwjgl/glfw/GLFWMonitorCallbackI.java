package org.lwjgl.glfw;

import org.lwjgl.system.CallbackI;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.dyncall.DynCallback;

@FunctionalInterface
@NativeType("GLFWmonitorfun")
public interface GLFWMonitorCallbackI extends CallbackI.V {
  public static final String SIGNATURE = "(pi)v";
  
  default String getSignature() {
    return "(pi)v";
  }
  
  default void callback(long args) {
    invoke(
        DynCallback.dcbArgPointer(args), 
        DynCallback.dcbArgInt(args));
  }
  
  void invoke(@NativeType("GLFWmonitor *") long paramLong, int paramInt);
}
