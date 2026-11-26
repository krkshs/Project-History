package org.lwjgl.system.windows;

import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.CustomBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeResource;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.Struct;
import org.lwjgl.system.StructBuffer;

public class POINT extends Struct implements NativeResource {
  public static final int SIZEOF;
  
  public static final int ALIGNOF;
  
  public static final int X;
  
  public static final int Y;
  
  static {
    Struct.Layout layout = __struct(
        __member(4), 
        __member(4));
    SIZEOF = layout.getSize();
    ALIGNOF = layout.getAlignment();
    X = layout.offsetof(0);
    Y = layout.offsetof(1);
  }
  
  public POINT(ByteBuffer container) {
    super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
  }
  
  public int sizeof() {
    return SIZEOF;
  }
  
  @NativeType("LONG")
  public int x() {
    return nx(address());
  }
  
  @NativeType("LONG")
  public int y() {
    return ny(address());
  }
  
  public POINT x(@NativeType("LONG") int value) {
    nx(address(), value);
    return this;
  }
  
  public POINT y(@NativeType("LONG") int value) {
    ny(address(), value);
    return this;
  }
  
  public POINT set(int x, int y) {
    x(x);
    y(y);
    return this;
  }
  
  public POINT set(POINT src) {
    MemoryUtil.memCopy(src.address(), address(), (long)SIZEOF);
    return this;
  }
  
  public static POINT malloc() {
    return POINT.<POINT>wrap(POINT.class, MemoryUtil.nmemAllocChecked((long)SIZEOF));
  }
  
  public static POINT calloc() {
    return POINT.<POINT>wrap(POINT.class, MemoryUtil.nmemCallocChecked(1L, (long)SIZEOF));
  }
  
  public static POINT create() {
    ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
    return POINT.<POINT>wrap(POINT.class, MemoryUtil.memAddress(container), container);
  }
  
  public static POINT create(long address) {
    return POINT.<POINT>wrap(POINT.class, address);
  }
  
  @Nullable
  public static POINT createSafe(long address) {
    return (address == 0L) ? null : POINT.<POINT>wrap(POINT.class, address);
  }
  
  public static Buffer malloc(int capacity) {
    return POINT.<Buffer>wrap(Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
  }
  
  public static Buffer calloc(int capacity) {
    return POINT.<Buffer>wrap(Buffer.class, MemoryUtil.nmemCallocChecked((long)capacity, (long)SIZEOF), capacity);
  }
  
  public static Buffer create(int capacity) {
    ByteBuffer container = __create(capacity, SIZEOF);
    return POINT.<Buffer>wrap(Buffer.class, MemoryUtil.memAddress(container), capacity, container);
  }
  
  public static Buffer create(long address, int capacity) {
    return POINT.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  @Nullable
  public static Buffer createSafe(long address, int capacity) {
    return (address == 0L) ? null : POINT.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  public static POINT mallocStack() {
    return mallocStack(MemoryStack.stackGet());
  }
  
  public static POINT callocStack() {
    return callocStack(MemoryStack.stackGet());
  }
  
  public static POINT mallocStack(MemoryStack stack) {
    return POINT.<POINT>wrap(POINT.class, stack.nmalloc(ALIGNOF, SIZEOF));
  }
  
  public static POINT callocStack(MemoryStack stack) {
    return POINT.<POINT>wrap(POINT.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
  }
  
  public static Buffer mallocStack(int capacity) {
    return mallocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer callocStack(int capacity) {
    return callocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer mallocStack(int capacity, MemoryStack stack) {
    return POINT.<Buffer>wrap(Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
  }
  
  public static Buffer callocStack(int capacity, MemoryStack stack) {
    return POINT.<Buffer>wrap(Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
  }
  
  public static int nx(long struct) {
    return UNSAFE.getInt(null, struct + (long)X);
  }
  
  public static int ny(long struct) {
    return UNSAFE.getInt(null, struct + (long)Y);
  }
  
  public static void nx(long struct, int value) {
    UNSAFE.putInt(null, struct + (long)X, value);
  }
  
  public static void ny(long struct, int value) {
    UNSAFE.putInt(null, struct + (long)Y, value);
  }
  
  public static class Buffer extends StructBuffer<POINT, Buffer> implements NativeResource {
    private static final POINT ELEMENT_FACTORY = POINT.create(-1L);
    
    public Buffer(ByteBuffer container) {
      super(container, container.remaining() / POINT.SIZEOF);
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
    
    protected POINT getElementFactory() {
      return ELEMENT_FACTORY;
    }
    
    @NativeType("LONG")
    public int x() {
      return POINT.nx(address());
    }
    
    @NativeType("LONG")
    public int y() {
      return POINT.ny(address());
    }
    
    public Buffer x(@NativeType("LONG") int value) {
      POINT.nx(address(), value);
      return this;
    }
    
    public Buffer y(@NativeType("LONG") int value) {
      POINT.ny(address(), value);
      return this;
    }
  }
}
