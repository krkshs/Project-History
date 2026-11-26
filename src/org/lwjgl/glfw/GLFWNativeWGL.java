package org.lwjgl.glfw;

import org.lwjgl.system.APIUtil;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.NativeType;

public class GLFWNativeWGL {
  protected GLFWNativeWGL() {
    throw new UnsupportedOperationException();
  }
  
  public static final class Functions {
    public static final long GetWGLContext = APIUtil.apiGetFunctionAddress(GLFW.getLibrary(), "glfwGetWGLContext");
  }
  
  @NativeType("HGLRC")
  public static long glfwGetWGLContext(@NativeType("GLFWwindow *") long window) {
    long __functionAddress = Functions.GetWGLContext;
    if (Checks.CHECKS)
      Checks.check(window); 
    return JNI.invokePP(window, __functionAddress);
  }
}
