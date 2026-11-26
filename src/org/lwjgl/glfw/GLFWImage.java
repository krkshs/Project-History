package org.lwjgl.glfw;

import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.Checks;
import org.lwjgl.system.CustomBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeResource;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.Struct;
import org.lwjgl.system.StructBuffer;

@NativeType("struct GLFWimage")
public class GLFWImage extends Struct implements NativeResource {
  public static final int SIZEOF;
  
  public static final int ALIGNOF;
  
  public static final int WIDTH;
  
  public static final int HEIGHT;
  
  public static final int PIXELS;
  
  static {
    Struct.Layout layout = __struct(
        __member(4), 
        __member(4), 
        __member(POINTER_SIZE));
    SIZEOF = layout.getSize();
    ALIGNOF = layout.getAlignment();
    WIDTH = layout.offsetof(0);
    HEIGHT = layout.offsetof(1);
    PIXELS = layout.offsetof(2);
  }
  
  public GLFWImage(ByteBuffer container) {
    super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
  }
  
  public int sizeof() {
    return SIZEOF;
  }
  
  public int width() {
    return nwidth(address());
  }
  
  public int height() {
    return nheight(address());
  }
  
  @NativeType("unsigned char *")
  public ByteBuffer pixels(int capacity) {
    return npixels(address(), capacity);
  }
  
  public GLFWImage width(int value) {
    nwidth(address(), value);
    return this;
  }
  
  public GLFWImage height(int value) {
    nheight(address(), value);
    return this;
  }
  
  public GLFWImage pixels(@NativeType("unsigned char *") ByteBuffer value) {
    npixels(address(), value);
    return this;
  }
  
  public GLFWImage set(int width, int height, ByteBuffer pixels) {
    width(width);
    height(height);
    pixels(pixels);
    return this;
  }
  
  public GLFWImage set(GLFWImage src) {
    MemoryUtil.memCopy(src.address(), address(), (long)SIZEOF);
    return this;
  }
  
  public static GLFWImage malloc() {
    return GLFWImage.<GLFWImage>wrap(GLFWImage.class, MemoryUtil.nmemAllocChecked((long)SIZEOF));
  }
  
  public static GLFWImage calloc() {
    return GLFWImage.<GLFWImage>wrap(GLFWImage.class, MemoryUtil.nmemCallocChecked(1L, (long)SIZEOF));
  }
  
  public static GLFWImage create() {
    ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
    return GLFWImage.<GLFWImage>wrap(GLFWImage.class, MemoryUtil.memAddress(container), container);
  }
  
  public static GLFWImage create(long address) {
    return GLFWImage.<GLFWImage>wrap(GLFWImage.class, address);
  }
  
  @Nullable
  public static GLFWImage createSafe(long address) {
    return (address == 0L) ? null : GLFWImage.<GLFWImage>wrap(GLFWImage.class, address);
  }
  
  public static Buffer malloc(int capacity) {
    return GLFWImage.<Buffer>wrap(Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
  }
  
  public static Buffer calloc(int capacity) {
    return GLFWImage.<Buffer>wrap(Buffer.class, MemoryUtil.nmemCallocChecked((long)capacity, (long)SIZEOF), capacity);
  }
  
  public static Buffer create(int capacity) {
    ByteBuffer container = __create(capacity, SIZEOF);
    return GLFWImage.<Buffer>wrap(Buffer.class, MemoryUtil.memAddress(container), capacity, container);
  }
  
  public static Buffer create(long address, int capacity) {
    return GLFWImage.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  @Nullable
  public static Buffer createSafe(long address, int capacity) {
    return (address == 0L) ? null : GLFWImage.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  public static GLFWImage mallocStack() {
    return mallocStack(MemoryStack.stackGet());
  }
  
  public static GLFWImage callocStack() {
    return callocStack(MemoryStack.stackGet());
  }
  
  public static GLFWImage mallocStack(MemoryStack stack) {
    return GLFWImage.<GLFWImage>wrap(GLFWImage.class, stack.nmalloc(ALIGNOF, SIZEOF));
  }
  
  public static GLFWImage callocStack(MemoryStack stack) {
    return GLFWImage.<GLFWImage>wrap(GLFWImage.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
  }
  
  public static Buffer mallocStack(int capacity) {
    return mallocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer callocStack(int capacity) {
    return callocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer mallocStack(int capacity, MemoryStack stack) {
    return GLFWImage.<Buffer>wrap(Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
  }
  
  public static Buffer callocStack(int capacity, MemoryStack stack) {
    return GLFWImage.<Buffer>wrap(Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
  }
  
  public static int nwidth(long struct) {
    return UNSAFE.getInt(null, struct + (long)WIDTH);
  }
  
  public static int nheight(long struct) {
    return UNSAFE.getInt(null, struct + (long)HEIGHT);
  }
  
  public static ByteBuffer npixels(long struct, int capacity) {
    return MemoryUtil.memByteBuffer(MemoryUtil.memGetAddress(struct + (long)PIXELS), capacity);
  }
  
  public static void nwidth(long struct, int value) {
    UNSAFE.putInt(null, struct + (long)WIDTH, value);
  }
  
  public static void nheight(long struct, int value) {
    UNSAFE.putInt(null, struct + (long)HEIGHT, value);
  }
  
  public static void npixels(long struct, ByteBuffer value) {
    MemoryUtil.memPutAddress(struct + (long)PIXELS, MemoryUtil.memAddress(value));
  }
  
  public static void validate(long struct) {
    Checks.check(MemoryUtil.memGetAddress(struct + (long)PIXELS));
  }
  
  public static void validate(long array, int count) {
    for (int i = 0; i < count; i++)
      validate(array + Integer.toUnsignedLong(i) * (long)SIZEOF); 
  }
  
  public static class Buffer extends StructBuffer<GLFWImage, Buffer> implements NativeResource {
    private static final GLFWImage ELEMENT_FACTORY = GLFWImage.create(-1L);
    
    public Buffer(ByteBuffer container) {
      super(container, container.remaining() / GLFWImage.SIZEOF);
    }
    
    public Buffer(long address, int cap) {
      super(address, null, -1, 0, cap, cap);
    }
    
    Buffer(long address, @Nullable ByteBuffer container, int mark, int pos, int lim, int cap) {
      super(address, container, mark, pos, lim, cap);
    }
    
    protected Buffer self() {
      return this;
    }
    
    protected GLFWImage getElementFactory() {
      return ELEMENT_FACTORY;
    }
    
    public int width() {
      return GLFWImage.nwidth(address());
    }
    
    public int height() {
      return GLFWImage.nheight(address());
    }
    
    @NativeType("unsigned char *")
    public ByteBuffer pixels(int capacity) {
      return GLFWImage.npixels(address(), capacity);
    }
    
    public Buffer width(int value) {
      GLFWImage.nwidth(address(), value);
      return this;
    }
    
    public Buffer height(int value) {
      GLFWImage.nheight(address(), value);
      return this;
    }
    
    public Buffer pixels(@NativeType("unsigned char *") ByteBuffer value) {
      GLFWImage.npixels(address(), value);
      return this;
    }
  }
}
