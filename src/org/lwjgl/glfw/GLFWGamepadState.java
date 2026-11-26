package org.lwjgl.glfw;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
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

@NativeType("struct GLFWgamepadstate")
public class GLFWGamepadState extends Struct implements NativeResource {
  public static final int SIZEOF;
  
  public static final int ALIGNOF;
  
  public static final int BUTTONS;
  
  public static final int AXES;
  
  static {
    Struct.Layout layout = __struct(new Struct.Member[] { __array(1, 15), 
          __array(4, 6) });
    SIZEOF = layout.getSize();
    ALIGNOF = layout.getAlignment();
    BUTTONS = layout.offsetof(0);
    AXES = layout.offsetof(1);
  }
  
  public GLFWGamepadState(ByteBuffer container) {
    super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
  }
  
  public int sizeof() {
    return SIZEOF;
  }
  
  @NativeType("unsigned char[15]")
  public ByteBuffer buttons() {
    return nbuttons(address());
  }
  
  @NativeType("unsigned char")
  public byte buttons(int index) {
    return nbuttons(address(), index);
  }
  
  @NativeType("float[6]")
  public FloatBuffer axes() {
    return naxes(address());
  }
  
  public float axes(int index) {
    return naxes(address(), index);
  }
  
  public GLFWGamepadState buttons(@NativeType("unsigned char[15]") ByteBuffer value) {
    nbuttons(address(), value);
    return this;
  }
  
  public GLFWGamepadState buttons(int index, @NativeType("unsigned char") byte value) {
    nbuttons(address(), index, value);
    return this;
  }
  
  public GLFWGamepadState axes(@NativeType("float[6]") FloatBuffer value) {
    naxes(address(), value);
    return this;
  }
  
  public GLFWGamepadState axes(int index, float value) {
    naxes(address(), index, value);
    return this;
  }
  
  public GLFWGamepadState set(ByteBuffer buttons, FloatBuffer axes) {
    buttons(buttons);
    axes(axes);
    return this;
  }
  
  public GLFWGamepadState set(GLFWGamepadState src) {
    MemoryUtil.memCopy(src.address(), address(), (long)SIZEOF);
    return this;
  }
  
  public static GLFWGamepadState malloc() {
    return GLFWGamepadState.<GLFWGamepadState>wrap(GLFWGamepadState.class, MemoryUtil.nmemAllocChecked((long)SIZEOF));
  }
  
  public static GLFWGamepadState calloc() {
    return GLFWGamepadState.<GLFWGamepadState>wrap(GLFWGamepadState.class, MemoryUtil.nmemCallocChecked(1L, (long)SIZEOF));
  }
  
  public static GLFWGamepadState create() {
    ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
    return GLFWGamepadState.<GLFWGamepadState>wrap(GLFWGamepadState.class, MemoryUtil.memAddress(container), container);
  }
  
  public static GLFWGamepadState create(long address) {
    return GLFWGamepadState.<GLFWGamepadState>wrap(GLFWGamepadState.class, address);
  }
  
  @Nullable
  public static GLFWGamepadState createSafe(long address) {
    return (address == 0L) ? null : GLFWGamepadState.<GLFWGamepadState>wrap(GLFWGamepadState.class, address);
  }
  
  public static Buffer malloc(int capacity) {
    return GLFWGamepadState.<Buffer>wrap(Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
  }
  
  public static Buffer calloc(int capacity) {
    return GLFWGamepadState.<Buffer>wrap(Buffer.class, MemoryUtil.nmemCallocChecked((long)capacity, (long)SIZEOF), capacity);
  }
  
  public static Buffer create(int capacity) {
    ByteBuffer container = __create(capacity, SIZEOF);
    return GLFWGamepadState.<Buffer>wrap(Buffer.class, MemoryUtil.memAddress(container), capacity, container);
  }
  
  public static Buffer create(long address, int capacity) {
    return GLFWGamepadState.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  @Nullable
  public static Buffer createSafe(long address, int capacity) {
    return (address == 0L) ? null : GLFWGamepadState.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  public static GLFWGamepadState mallocStack() {
    return mallocStack(MemoryStack.stackGet());
  }
  
  public static GLFWGamepadState callocStack() {
    return callocStack(MemoryStack.stackGet());
  }
  
  public static GLFWGamepadState mallocStack(MemoryStack stack) {
    return GLFWGamepadState.<GLFWGamepadState>wrap(GLFWGamepadState.class, stack.nmalloc(ALIGNOF, SIZEOF));
  }
  
  public static GLFWGamepadState callocStack(MemoryStack stack) {
    return GLFWGamepadState.<GLFWGamepadState>wrap(GLFWGamepadState.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
  }
  
  public static Buffer mallocStack(int capacity) {
    return mallocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer callocStack(int capacity) {
    return callocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer mallocStack(int capacity, MemoryStack stack) {
    return GLFWGamepadState.<Buffer>wrap(Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
  }
  
  public static Buffer callocStack(int capacity, MemoryStack stack) {
    return GLFWGamepadState.<Buffer>wrap(Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
  }
  
  public static ByteBuffer nbuttons(long struct) {
    return MemoryUtil.memByteBuffer(struct + (long)BUTTONS, 15);
  }
  
  public static byte nbuttons(long struct, int index) {
    return UNSAFE.getByte(null, struct + (long)BUTTONS + Checks.check(index, 15) * 1L);
  }
  
  public static FloatBuffer naxes(long struct) {
    return MemoryUtil.memFloatBuffer(struct + (long)AXES, 6);
  }
  
  public static float naxes(long struct, int index) {
    return UNSAFE.getFloat(null, struct + (long)AXES + Checks.check(index, 6) * 4L);
  }
  
  public static void nbuttons(long struct, ByteBuffer value) {
    if (Checks.CHECKS)
      Checks.checkGT(value, 15); 
    MemoryUtil.memCopy(MemoryUtil.memAddress(value), struct + (long)BUTTONS, (long)(value.remaining() * 1));
  }
  
  public static void nbuttons(long struct, int index, byte value) {
    UNSAFE.putByte(null, struct + (long)BUTTONS + Checks.check(index, 15) * 1L, value);
  }
  
  public static void naxes(long struct, FloatBuffer value) {
    if (Checks.CHECKS)
      Checks.checkGT(value, 6); 
    MemoryUtil.memCopy(MemoryUtil.memAddress(value), struct + (long)AXES, (long)(value.remaining() * 4));
  }
  
  public static void naxes(long struct, int index, float value) {
    UNSAFE.putFloat(null, struct + (long)AXES + Checks.check(index, 6) * 4L, value);
  }
  
  public static class Buffer extends StructBuffer<GLFWGamepadState, Buffer> implements NativeResource {
    private static final GLFWGamepadState ELEMENT_FACTORY = GLFWGamepadState.create(-1L);
    
    public Buffer(ByteBuffer container) {
      super(container, container.remaining() / GLFWGamepadState.SIZEOF);
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
    
    protected GLFWGamepadState getElementFactory() {
      return ELEMENT_FACTORY;
    }
    
    @NativeType("unsigned char[15]")
    public ByteBuffer buttons() {
      return GLFWGamepadState.nbuttons(address());
    }
    
    @NativeType("unsigned char")
    public byte buttons(int index) {
      return GLFWGamepadState.nbuttons(address(), index);
    }
    
    @NativeType("float[6]")
    public FloatBuffer axes() {
      return GLFWGamepadState.naxes(address());
    }
    
    public float axes(int index) {
      return GLFWGamepadState.naxes(address(), index);
    }
    
    public Buffer buttons(@NativeType("unsigned char[15]") ByteBuffer value) {
      GLFWGamepadState.nbuttons(address(), value);
      return this;
    }
    
    public Buffer buttons(int index, @NativeType("unsigned char") byte value) {
      GLFWGamepadState.nbuttons(address(), index, value);
      return this;
    }
    
    public Buffer axes(@NativeType("float[6]") FloatBuffer value) {
      GLFWGamepadState.naxes(address(), value);
      return this;
    }
    
    public Buffer axes(int index, float value) {
      GLFWGamepadState.naxes(address(), index, value);
      return this;
    }
  }
}
