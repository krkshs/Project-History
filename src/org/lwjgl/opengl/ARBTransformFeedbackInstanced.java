package org.lwjgl.opengl;

import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class ARBTransformFeedbackInstanced {
  static {
    GL.initialize();
  }
  
  protected ARBTransformFeedbackInstanced() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps) {
    return Checks.checkFunctions(new long[] { caps.glDrawTransformFeedbackInstanced, caps.glDrawTransformFeedbackStreamInstanced });
  }
  
  public static void glDrawTransformFeedbackInstanced(@NativeType("GLenum") int mode, @NativeType("GLuint") int id, @NativeType("GLsizei") int primcount) {
    GL42C.glDrawTransformFeedbackInstanced(mode, id, primcount);
  }
  
  public static void glDrawTransformFeedbackStreamInstanced(@NativeType("GLenum") int mode, @NativeType("GLuint") int id, @NativeType("GLuint") int stream, @NativeType("GLsizei") int primcount) {
    GL42C.glDrawTransformFeedbackStreamInstanced(mode, id, stream, primcount);
  }
}
