package org.lwjgl.opengl;

import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class ARBComputeVariableGroupSize {
  public static final int GL_MAX_COMPUTE_VARIABLE_GROUP_INVOCATIONS_ARB = 37700;
  
  public static final int GL_MAX_COMPUTE_FIXED_GROUP_INVOCATIONS_ARB = 37099;
  
  public static final int GL_MAX_COMPUTE_VARIABLE_GROUP_SIZE_ARB = 37701;
  
  public static final int GL_MAX_COMPUTE_FIXED_GROUP_SIZE_ARB = 37311;
  
  static {
    GL.initialize();
  }
  
  protected ARBComputeVariableGroupSize() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(new long[] { caps.glDispatchComputeGroupSizeARB });
  }
  
  public static native void glDispatchComputeGroupSizeARB(@NativeType("GLuint") int paramInt1, @NativeType("GLuint") int paramInt2, @NativeType("GLuint") int paramInt3, @NativeType("GLuint") int paramInt4, @NativeType("GLuint") int paramInt5, @NativeType("GLuint") int paramInt6);
}
