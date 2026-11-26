package org.lwjgl.opengl;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.Set;
import javax.annotation.Nullable;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class ARBRobustness {
  public static final int GL_GUILTY_CONTEXT_RESET_ARB = 33363;
  
  public static final int GL_INNOCENT_CONTEXT_RESET_ARB = 33364;
  
  public static final int GL_UNKNOWN_CONTEXT_RESET_ARB = 33365;
  
  public static final int GL_RESET_NOTIFICATION_STRATEGY_ARB = 33366;
  
  public static final int GL_LOSE_CONTEXT_ON_RESET_ARB = 33362;
  
  public static final int GL_NO_RESET_NOTIFICATION_ARB = 33377;
  
  public static final int GL_CONTEXT_FLAG_ROBUST_ACCESS_BIT_ARB = 4;
  
  static {
    GL.initialize();
  }
  
  protected ARBRobustness() {
    throw new UnsupportedOperationException();
  }
  
  static boolean isAvailable(GLCapabilities caps, Set<String> ext) {
    return Checks.checkFunctions(new long[] { 
          caps.glGetGraphicsResetStatusARB, (caps.glGetMapdv != 0L) ? caps.glGetnMapdvARB : -1L, (caps.glGetMapfv != 0L) ? caps.glGetnMapfvARB : -1L, (caps.glGetMapiv != 0L) ? caps.glGetnMapivARB : -1L, (caps.glGetPixelMapfv != 0L) ? caps.glGetnPixelMapfvARB : -1L, (caps.glGetPixelMapuiv != 0L) ? caps.glGetnPixelMapuivARB : -1L, (caps.glGetPixelMapusv != 0L) ? caps.glGetnPixelMapusvARB : -1L, (caps.glGetPolygonStipple != 0L) ? caps.glGetnPolygonStippleARB : -1L, caps.glGetnTexImageARB, caps.glReadnPixelsARB, 
          (



          
          ext.contains("GL_ARB_imaging") && caps.glGetColorTable != 0L) ? caps.glGetnColorTableARB : -1L, (
          ext.contains("GL_ARB_imaging") && caps.glGetConvolutionFilter != 0L) ? caps.glGetnConvolutionFilterARB : -1L, (
          ext.contains("GL_ARB_imaging") && caps.glGetSeparableFilter != 0L) ? caps.glGetnSeparableFilterARB : -1L, (
          ext.contains("GL_ARB_imaging") && caps.glGetHistogram != 0L) ? caps.glGetnHistogramARB : -1L, (
          ext.contains("GL_ARB_imaging") && caps.glGetMinmax != 0L) ? caps.glGetnMinmaxARB : -1L, 
          ext.contains("OpenGL13") ? caps.glGetnCompressedTexImageARB : -1L, ext.contains("OpenGL20") ? caps.glGetnUniformfvARB : -1L, 
          ext.contains("OpenGL20") ? caps.glGetnUniformivARB : -1L, ext.contains("OpenGL30") ? caps.glGetnUniformuivARB : -1L, 
          ext.contains("OpenGL40") ? caps.glGetnUniformdvARB : -1L });
  }
  
  public static void glGetnMapdvARB(@NativeType("GLenum") int target, @NativeType("GLenum") int query, @NativeType("GLdouble *") DoubleBuffer data) {
    nglGetnMapdvARB(target, query, data.remaining(), MemoryUtil.memAddress(data));
  }
  
  @NativeType("void")
  public static double glGetnMapdARB(@NativeType("GLenum") int target, @NativeType("GLenum") int query) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      DoubleBuffer data = stack.callocDouble(1);
      nglGetnMapdvARB(target, query, 1, MemoryUtil.memAddress(data));
      return data.get(0);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glGetnMapfvARB(@NativeType("GLenum") int target, @NativeType("GLenum") int query, @NativeType("GLfloat *") FloatBuffer data) {
    nglGetnMapfvARB(target, query, data.remaining(), MemoryUtil.memAddress(data));
  }
  
  @NativeType("void")
  public static float glGetnMapfARB(@NativeType("GLenum") int target, @NativeType("GLenum") int query) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      FloatBuffer data = stack.callocFloat(1);
      nglGetnMapfvARB(target, query, 1, MemoryUtil.memAddress(data));
      return data.get(0);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glGetnMapivARB(@NativeType("GLenum") int target, @NativeType("GLenum") int query, @NativeType("GLint *") IntBuffer data) {
    nglGetnMapivARB(target, query, data.remaining(), MemoryUtil.memAddress(data));
  }
  
  @NativeType("void")
  public static int glGetnMapiARB(@NativeType("GLenum") int target, @NativeType("GLenum") int query) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      IntBuffer data = stack.callocInt(1);
      nglGetnMapivARB(target, query, 1, MemoryUtil.memAddress(data));
      return data.get(0);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glGetnPixelMapfvARB(@NativeType("GLenum") int map, @NativeType("GLfloat *") FloatBuffer data) {
    nglGetnPixelMapfvARB(map, data.remaining(), MemoryUtil.memAddress(data));
  }
  
  public static void glGetnPixelMapuivARB(@NativeType("GLenum") int map, @NativeType("GLuint *") IntBuffer data) {
    nglGetnPixelMapuivARB(map, data.remaining(), MemoryUtil.memAddress(data));
  }
  
  public static void glGetnPixelMapusvARB(@NativeType("GLenum") int map, @NativeType("GLushort *") ShortBuffer data) {
    nglGetnPixelMapusvARB(map, data.remaining(), MemoryUtil.memAddress(data));
  }
  
  public static void glGetnPolygonStippleARB(@NativeType("GLsizei") int bufSize, @NativeType("GLubyte *") long pattern) {
    nglGetnPolygonStippleARB(bufSize, pattern);
  }
  
  public static void glGetnPolygonStippleARB(@NativeType("GLubyte *") ByteBuffer pattern) {
    nglGetnPolygonStippleARB(pattern.remaining(), MemoryUtil.memAddress(pattern));
  }
  
  public static void glGetnTexImageARB(@NativeType("GLenum") int tex, @NativeType("GLint") int level, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("GLsizei") int bufSize, @NativeType("void *") long img) {
    nglGetnTexImageARB(tex, level, format, type, bufSize, img);
  }
  
  public static void glGetnTexImageARB(@NativeType("GLenum") int tex, @NativeType("GLint") int level, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void *") ByteBuffer img) {
    nglGetnTexImageARB(tex, level, format, type, img.remaining(), MemoryUtil.memAddress(img));
  }
  
  public static void glGetnTexImageARB(@NativeType("GLenum") int tex, @NativeType("GLint") int level, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void *") ShortBuffer img) {
    nglGetnTexImageARB(tex, level, format, type, img.remaining() << 1, MemoryUtil.memAddress(img));
  }
  
  public static void glGetnTexImageARB(@NativeType("GLenum") int tex, @NativeType("GLint") int level, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void *") IntBuffer img) {
    nglGetnTexImageARB(tex, level, format, type, img.remaining() << 2, MemoryUtil.memAddress(img));
  }
  
  public static void glGetnTexImageARB(@NativeType("GLenum") int tex, @NativeType("GLint") int level, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void *") FloatBuffer img) {
    nglGetnTexImageARB(tex, level, format, type, img.remaining() << 2, MemoryUtil.memAddress(img));
  }
  
  public static void glGetnTexImageARB(@NativeType("GLenum") int tex, @NativeType("GLint") int level, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void *") DoubleBuffer img) {
    nglGetnTexImageARB(tex, level, format, type, img.remaining() << 3, MemoryUtil.memAddress(img));
  }
  
  public static void glReadnPixelsARB(@NativeType("GLint") int x, @NativeType("GLint") int y, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("GLsizei") int bufSize, @NativeType("void *") long data) {
    nglReadnPixelsARB(x, y, width, height, format, type, bufSize, data);
  }
  
  public static void glReadnPixelsARB(@NativeType("GLint") int x, @NativeType("GLint") int y, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void *") ByteBuffer data) {
    nglReadnPixelsARB(x, y, width, height, format, type, data.remaining(), MemoryUtil.memAddress(data));
  }
  
  public static void glReadnPixelsARB(@NativeType("GLint") int x, @NativeType("GLint") int y, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void *") ShortBuffer data) {
    nglReadnPixelsARB(x, y, width, height, format, type, data.remaining() << 1, MemoryUtil.memAddress(data));
  }
  
  public static void glReadnPixelsARB(@NativeType("GLint") int x, @NativeType("GLint") int y, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void *") IntBuffer data) {
    nglReadnPixelsARB(x, y, width, height, format, type, data.remaining() << 2, MemoryUtil.memAddress(data));
  }
  
  public static void glReadnPixelsARB(@NativeType("GLint") int x, @NativeType("GLint") int y, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void *") FloatBuffer data) {
    nglReadnPixelsARB(x, y, width, height, format, type, data.remaining() << 2, MemoryUtil.memAddress(data));
  }
  
  public static void glGetnColorTableARB(@NativeType("GLenum") int target, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("GLsizei") int bufSize, @NativeType("void *") long table) {
    nglGetnColorTableARB(target, format, type, bufSize, table);
  }
  
  public static void glGetnColorTableARB(@NativeType("GLenum") int target, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void *") ByteBuffer table) {
    nglGetnColorTableARB(target, format, type, table.remaining(), MemoryUtil.memAddress(table));
  }
  
  public static void glGetnColorTableARB(@NativeType("GLenum") int target, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void *") ShortBuffer table) {
    nglGetnColorTableARB(target, format, type, table.remaining() << 1, MemoryUtil.memAddress(table));
  }
  
  public static void glGetnColorTableARB(@NativeType("GLenum") int target, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void *") IntBuffer table) {
    nglGetnColorTableARB(target, format, type, table.remaining() << 2, MemoryUtil.memAddress(table));
  }
  
  public static void glGetnColorTableARB(@NativeType("GLenum") int target, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void *") FloatBuffer table) {
    nglGetnColorTableARB(target, format, type, table.remaining() << 2, MemoryUtil.memAddress(table));
  }
  
  public static void glGetnConvolutionFilterARB(@NativeType("GLenum") int target, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("GLsizei") int bufSize, @NativeType("void *") long image) {
    nglGetnConvolutionFilterARB(target, format, type, bufSize, image);
  }
  
  public static void glGetnConvolutionFilterARB(@NativeType("GLenum") int target, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void *") ByteBuffer image) {
    nglGetnConvolutionFilterARB(target, format, type, image.remaining(), MemoryUtil.memAddress(image));
  }
  
  public static void glGetnSeparableFilterARB(@NativeType("GLenum") int target, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("GLsizei") int rowBufSize, @NativeType("void *") long row, @NativeType("GLsizei") int columnBufSize, @NativeType("void *") long column, @Nullable @NativeType("void *") ByteBuffer span) {
    nglGetnSeparableFilterARB(target, format, type, rowBufSize, row, columnBufSize, column, MemoryUtil.memAddressSafe(span));
  }
  
  public static void glGetnSeparableFilterARB(@NativeType("GLenum") int target, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void *") ByteBuffer row, @NativeType("void *") ByteBuffer column, @Nullable @NativeType("void *") ByteBuffer span) {
    nglGetnSeparableFilterARB(target, format, type, row.remaining(), MemoryUtil.memAddress(row), column.remaining(), MemoryUtil.memAddress(column), MemoryUtil.memAddressSafe(span));
  }
  
  public static void glGetnHistogramARB(@NativeType("GLenum") int target, @NativeType("GLboolean") boolean reset, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("GLsizei") int bufSize, @NativeType("void *") long values) {
    nglGetnHistogramARB(target, reset, format, type, bufSize, values);
  }
  
  public static void glGetnHistogramARB(@NativeType("GLenum") int target, @NativeType("GLboolean") boolean reset, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void *") ByteBuffer values) {
    nglGetnHistogramARB(target, reset, format, type, values.remaining(), MemoryUtil.memAddress(values));
  }
  
  public static void glGetnMinmaxARB(@NativeType("GLenum") int target, @NativeType("GLboolean") boolean reset, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("GLsizei") int bufSize, @NativeType("void *") long values) {
    nglGetnMinmaxARB(target, reset, format, type, bufSize, values);
  }
  
  public static void glGetnMinmaxARB(@NativeType("GLenum") int target, @NativeType("GLboolean") boolean reset, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void *") ByteBuffer values) {
    nglGetnMinmaxARB(target, reset, format, type, values.remaining(), MemoryUtil.memAddress(values));
  }
  
  public static void glGetnCompressedTexImageARB(@NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("GLsizei") int bufSize, @NativeType("void *") long img) {
    nglGetnCompressedTexImageARB(target, level, bufSize, img);
  }
  
  public static void glGetnCompressedTexImageARB(@NativeType("GLenum") int target, @NativeType("GLint") int level, @NativeType("void *") ByteBuffer img) {
    if (Checks.CHECKS && 
      Checks.DEBUG)
      Checks.check((Buffer)img, GL11.glGetTexLevelParameteri(target, level, 34464)); 
    nglGetnCompressedTexImageARB(target, level, img.remaining(), MemoryUtil.memAddress(img));
  }
  
  public static void glGetnUniformfvARB(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLfloat *") FloatBuffer params) {
    nglGetnUniformfvARB(program, location, params.remaining(), MemoryUtil.memAddress(params));
  }
  
  @NativeType("void")
  public static float glGetnUniformfARB(@NativeType("GLuint") int program, @NativeType("GLint") int location) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      FloatBuffer params = stack.callocFloat(1);
      nglGetnUniformfvARB(program, location, 1, MemoryUtil.memAddress(params));
      return params.get(0);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glGetnUniformivARB(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLint *") IntBuffer params) {
    nglGetnUniformivARB(program, location, params.remaining(), MemoryUtil.memAddress(params));
  }
  
  @NativeType("void")
  public static int glGetnUniformiARB(@NativeType("GLuint") int program, @NativeType("GLint") int location) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      IntBuffer params = stack.callocInt(1);
      nglGetnUniformivARB(program, location, 1, MemoryUtil.memAddress(params));
      return params.get(0);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glGetnUniformuivARB(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLuint *") IntBuffer params) {
    nglGetnUniformuivARB(program, location, params.remaining(), MemoryUtil.memAddress(params));
  }
  
  @NativeType("void")
  public static int glGetnUniformuiARB(@NativeType("GLuint") int program, @NativeType("GLint") int location) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      IntBuffer params = stack.callocInt(1);
      nglGetnUniformuivARB(program, location, 1, MemoryUtil.memAddress(params));
      return params.get(0);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glGetnUniformdvARB(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLdouble *") DoubleBuffer params) {
    nglGetnUniformdvARB(program, location, params.remaining(), MemoryUtil.memAddress(params));
  }
  
  @NativeType("void")
  public static double glGetnUniformdARB(@NativeType("GLuint") int program, @NativeType("GLint") int location) {
    MemoryStack stack = MemoryStack.stackGet();
    int stackPointer = stack.getPointer();
    try {
      DoubleBuffer params = stack.callocDouble(1);
      nglGetnUniformdvARB(program, location, 1, MemoryUtil.memAddress(params));
      return params.get(0);
    } finally {
      stack.setPointer(stackPointer);
    } 
  }
  
  public static void glGetnMapdvARB(@NativeType("GLenum") int target, @NativeType("GLenum") int query, @NativeType("GLdouble *") double[] data) {
    long __functionAddress = (GL.getICD()).glGetnMapdvARB;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(target, query, data.length, data, __functionAddress);
  }
  
  public static void glGetnMapfvARB(@NativeType("GLenum") int target, @NativeType("GLenum") int query, @NativeType("GLfloat *") float[] data) {
    long __functionAddress = (GL.getICD()).glGetnMapfvARB;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(target, query, data.length, data, __functionAddress);
  }
  
  public static void glGetnMapivARB(@NativeType("GLenum") int target, @NativeType("GLenum") int query, @NativeType("GLint *") int[] data) {
    long __functionAddress = (GL.getICD()).glGetnMapivARB;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(target, query, data.length, data, __functionAddress);
  }
  
  public static void glGetnPixelMapfvARB(@NativeType("GLenum") int map, @NativeType("GLfloat *") float[] data) {
    long __functionAddress = (GL.getICD()).glGetnPixelMapfvARB;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(map, data.length, data, __functionAddress);
  }
  
  public static void glGetnPixelMapuivARB(@NativeType("GLenum") int map, @NativeType("GLuint *") int[] data) {
    long __functionAddress = (GL.getICD()).glGetnPixelMapuivARB;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(map, data.length, data, __functionAddress);
  }
  
  public static void glGetnPixelMapusvARB(@NativeType("GLenum") int map, @NativeType("GLushort *") short[] data) {
    long __functionAddress = (GL.getICD()).glGetnPixelMapusvARB;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(map, data.length, data, __functionAddress);
  }
  
  public static void glGetnTexImageARB(@NativeType("GLenum") int tex, @NativeType("GLint") int level, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void *") short[] img) {
    long __functionAddress = (GL.getICD()).glGetnTexImageARB;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(tex, level, format, type, img.length << 1, img, __functionAddress);
  }
  
  public static void glGetnTexImageARB(@NativeType("GLenum") int tex, @NativeType("GLint") int level, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void *") int[] img) {
    long __functionAddress = (GL.getICD()).glGetnTexImageARB;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(tex, level, format, type, img.length << 2, img, __functionAddress);
  }
  
  public static void glGetnTexImageARB(@NativeType("GLenum") int tex, @NativeType("GLint") int level, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void *") float[] img) {
    long __functionAddress = (GL.getICD()).glGetnTexImageARB;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(tex, level, format, type, img.length << 2, img, __functionAddress);
  }
  
  public static void glGetnTexImageARB(@NativeType("GLenum") int tex, @NativeType("GLint") int level, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void *") double[] img) {
    long __functionAddress = (GL.getICD()).glGetnTexImageARB;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(tex, level, format, type, img.length << 3, img, __functionAddress);
  }
  
  public static void glReadnPixelsARB(@NativeType("GLint") int x, @NativeType("GLint") int y, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void *") short[] data) {
    long __functionAddress = (GL.getICD()).glReadnPixelsARB;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(x, y, width, height, format, type, data.length << 1, data, __functionAddress);
  }
  
  public static void glReadnPixelsARB(@NativeType("GLint") int x, @NativeType("GLint") int y, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void *") int[] data) {
    long __functionAddress = (GL.getICD()).glReadnPixelsARB;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(x, y, width, height, format, type, data.length << 2, data, __functionAddress);
  }
  
  public static void glReadnPixelsARB(@NativeType("GLint") int x, @NativeType("GLint") int y, @NativeType("GLsizei") int width, @NativeType("GLsizei") int height, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void *") float[] data) {
    long __functionAddress = (GL.getICD()).glReadnPixelsARB;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(x, y, width, height, format, type, data.length << 2, data, __functionAddress);
  }
  
  public static void glGetnColorTableARB(@NativeType("GLenum") int target, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void *") short[] table) {
    long __functionAddress = (GL.getICD()).glGetnColorTableARB;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(target, format, type, table.length << 1, table, __functionAddress);
  }
  
  public static void glGetnColorTableARB(@NativeType("GLenum") int target, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void *") int[] table) {
    long __functionAddress = (GL.getICD()).glGetnColorTableARB;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(target, format, type, table.length << 2, table, __functionAddress);
  }
  
  public static void glGetnColorTableARB(@NativeType("GLenum") int target, @NativeType("GLenum") int format, @NativeType("GLenum") int type, @NativeType("void *") float[] table) {
    long __functionAddress = (GL.getICD()).glGetnColorTableARB;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(target, format, type, table.length << 2, table, __functionAddress);
  }
  
  public static void glGetnUniformfvARB(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLfloat *") float[] params) {
    long __functionAddress = (GL.getICD()).glGetnUniformfvARB;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(program, location, params.length, params, __functionAddress);
  }
  
  public static void glGetnUniformivARB(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLint *") int[] params) {
    long __functionAddress = (GL.getICD()).glGetnUniformivARB;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(program, location, params.length, params, __functionAddress);
  }
  
  public static void glGetnUniformuivARB(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLuint *") int[] params) {
    long __functionAddress = (GL.getICD()).glGetnUniformuivARB;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(program, location, params.length, params, __functionAddress);
  }
  
  public static void glGetnUniformdvARB(@NativeType("GLuint") int program, @NativeType("GLint") int location, @NativeType("GLdouble *") double[] params) {
    long __functionAddress = (GL.getICD()).glGetnUniformdvARB;
    if (Checks.CHECKS)
      Checks.check(__functionAddress); 
    JNI.callPV(program, location, params.length, params, __functionAddress);
  }
  
  @NativeType("GLenum")
  public static native int glGetGraphicsResetStatusARB();
  
  public static native void nglGetnMapdvARB(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void nglGetnMapfvARB(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void nglGetnMapivARB(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void nglGetnPixelMapfvARB(int paramInt1, int paramInt2, long paramLong);
  
  public static native void nglGetnPixelMapuivARB(int paramInt1, int paramInt2, long paramLong);
  
  public static native void nglGetnPixelMapusvARB(int paramInt1, int paramInt2, long paramLong);
  
  public static native void nglGetnPolygonStippleARB(int paramInt, long paramLong);
  
  public static native void nglGetnTexImageARB(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, long paramLong);
  
  public static native void nglReadnPixelsARB(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, long paramLong);
  
  public static native void nglGetnColorTableARB(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong);
  
  public static native void nglGetnConvolutionFilterARB(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong);
  
  public static native void nglGetnSeparableFilterARB(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong1, int paramInt5, long paramLong2, long paramLong3);
  
  public static native void nglGetnHistogramARB(int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, long paramLong);
  
  public static native void nglGetnMinmaxARB(int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, long paramLong);
  
  public static native void nglGetnCompressedTexImageARB(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void nglGetnUniformfvARB(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void nglGetnUniformivARB(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void nglGetnUniformuivARB(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static native void nglGetnUniformdvARB(int paramInt1, int paramInt2, int paramInt3, long paramLong);
}
