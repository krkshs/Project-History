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

public class MOUSEINPUT extends Struct implements NativeResource {
  public static final int SIZEOF;
  
  public static final int ALIGNOF;
  
  public static final int DX;
  
  public static final int DY;
  
  public static final int MOUSEDATA;
  
  public static final int DWFLAGS;
  
  public static final int TIME;
  
  public static final int DWEXTRAINFO;
  
  static {
    Struct.Layout layout = __struct(
        __member(4), 
        __member(4), 
        __member(4), 
        __member(4), 
        __member(4), 
        __member(POINTER_SIZE));
    SIZEOF = layout.getSize();
    ALIGNOF = layout.getAlignment();
    DX = layout.offsetof(0);
    DY = layout.offsetof(1);
    MOUSEDATA = layout.offsetof(2);
    DWFLAGS = layout.offsetof(3);
    TIME = layout.offsetof(4);
    DWEXTRAINFO = layout.offsetof(5);
  }
  
  public MOUSEINPUT(ByteBuffer container) {
    super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
  }
  
  public int sizeof() {
    return SIZEOF;
  }
  
  @NativeType("LONG")
  public int dx() {
    return ndx(address());
  }
  
  @NativeType("LONG")
  public int dy() {
    return ndy(address());
  }
  
  @NativeType("DWORD")
  public int mouseData() {
    return nmouseData(address());
  }
  
  @NativeType("DWORD")
  public int dwFlags() {
    return ndwFlags(address());
  }
  
  @NativeType("DWORD")
  public int time() {
    return ntime(address());
  }
  
  @NativeType("ULONG_PTR")
  public long dwExtraInfo() {
    return ndwExtraInfo(address());
  }
  
  public MOUSEINPUT dx(@NativeType("LONG") int value) {
    ndx(address(), value);
    return this;
  }
  
  public MOUSEINPUT dy(@NativeType("LONG") int value) {
    ndy(address(), value);
    return this;
  }
  
  public MOUSEINPUT mouseData(@NativeType("DWORD") int value) {
    nmouseData(address(), value);
    return this;
  }
  
  public MOUSEINPUT dwFlags(@NativeType("DWORD") int value) {
    ndwFlags(address(), value);
    return this;
  }
  
  public MOUSEINPUT time(@NativeType("DWORD") int value) {
    ntime(address(), value);
    return this;
  }
  
  public MOUSEINPUT dwExtraInfo(@NativeType("ULONG_PTR") long value) {
    ndwExtraInfo(address(), value);
    return this;
  }
  
  public MOUSEINPUT set(int dx, int dy, int mouseData, int dwFlags, int time, long dwExtraInfo) {
    dx(dx);
    dy(dy);
    mouseData(mouseData);
    dwFlags(dwFlags);
    time(time);
    dwExtraInfo(dwExtraInfo);
    return this;
  }
  
  public MOUSEINPUT set(MOUSEINPUT src) {
    MemoryUtil.memCopy(src.address(), address(), (long)SIZEOF);
    return this;
  }
  
  public static MOUSEINPUT malloc() {
    return MOUSEINPUT.<MOUSEINPUT>wrap(MOUSEINPUT.class, MemoryUtil.nmemAllocChecked((long)SIZEOF));
  }
  
  public static MOUSEINPUT calloc() {
    return MOUSEINPUT.<MOUSEINPUT>wrap(MOUSEINPUT.class, MemoryUtil.nmemCallocChecked(1L, (long)SIZEOF));
  }
  
  public static MOUSEINPUT create() {
    ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
    return MOUSEINPUT.<MOUSEINPUT>wrap(MOUSEINPUT.class, MemoryUtil.memAddress(container), container);
  }
  
  public static MOUSEINPUT create(long address) {
    return MOUSEINPUT.<MOUSEINPUT>wrap(MOUSEINPUT.class, address);
  }
  
  @Nullable
  public static MOUSEINPUT createSafe(long address) {
    return (address == 0L) ? null : MOUSEINPUT.<MOUSEINPUT>wrap(MOUSEINPUT.class, address);
  }
  
  public static Buffer malloc(int capacity) {
    return MOUSEINPUT.<Buffer>wrap(Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
  }
  
  public static Buffer calloc(int capacity) {
    return MOUSEINPUT.<Buffer>wrap(Buffer.class, MemoryUtil.nmemCallocChecked((long)capacity, (long)SIZEOF), capacity);
  }
  
  public static Buffer create(int capacity) {
    ByteBuffer container = __create(capacity, SIZEOF);
    return MOUSEINPUT.<Buffer>wrap(Buffer.class, MemoryUtil.memAddress(container), capacity, container);
  }
  
  public static Buffer create(long address, int capacity) {
    return MOUSEINPUT.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  @Nullable
  public static Buffer createSafe(long address, int capacity) {
    return (address == 0L) ? null : MOUSEINPUT.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  public static MOUSEINPUT mallocStack() {
    return mallocStack(MemoryStack.stackGet());
  }
  
  public static MOUSEINPUT callocStack() {
    return callocStack(MemoryStack.stackGet());
  }
  
  public static MOUSEINPUT mallocStack(MemoryStack stack) {
    return MOUSEINPUT.<MOUSEINPUT>wrap(MOUSEINPUT.class, stack.nmalloc(ALIGNOF, SIZEOF));
  }
  
  public static MOUSEINPUT callocStack(MemoryStack stack) {
    return MOUSEINPUT.<MOUSEINPUT>wrap(MOUSEINPUT.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
  }
  
  public static Buffer mallocStack(int capacity) {
    return mallocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer callocStack(int capacity) {
    return callocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer mallocStack(int capacity, MemoryStack stack) {
    return MOUSEINPUT.<Buffer>wrap(Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
  }
  
  public static Buffer callocStack(int capacity, MemoryStack stack) {
    return MOUSEINPUT.<Buffer>wrap(Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
  }
  
  public static int ndx(long struct) {
    return UNSAFE.getInt(null, struct + (long)DX);
  }
  
  public static int ndy(long struct) {
    return UNSAFE.getInt(null, struct + (long)DY);
  }
  
  public static int nmouseData(long struct) {
    return UNSAFE.getInt(null, struct + (long)MOUSEDATA);
  }
  
  public static int ndwFlags(long struct) {
    return UNSAFE.getInt(null, struct + (long)DWFLAGS);
  }
  
  public static int ntime(long struct) {
    return UNSAFE.getInt(null, struct + (long)TIME);
  }
  
  public static long ndwExtraInfo(long struct) {
    return MemoryUtil.memGetAddress(struct + (long)DWEXTRAINFO);
  }
  
  public static void ndx(long struct, int value) {
    UNSAFE.putInt(null, struct + (long)DX, value);
  }
  
  public static void ndy(long struct, int value) {
    UNSAFE.putInt(null, struct + (long)DY, value);
  }
  
  public static void nmouseData(long struct, int value) {
    UNSAFE.putInt(null, struct + (long)MOUSEDATA, value);
  }
  
  public static void ndwFlags(long struct, int value) {
    UNSAFE.putInt(null, struct + (long)DWFLAGS, value);
  }
  
  public static void ntime(long struct, int value) {
    UNSAFE.putInt(null, struct + (long)TIME, value);
  }
  
  public static void ndwExtraInfo(long struct, long value) {
    MemoryUtil.memPutAddress(struct + (long)DWEXTRAINFO, value);
  }
  
  public static class Buffer extends StructBuffer<MOUSEINPUT, Buffer> implements NativeResource {
    private static final MOUSEINPUT ELEMENT_FACTORY = MOUSEINPUT.create(-1L);
    
    public Buffer(ByteBuffer container) {
      super(container, container.remaining() / MOUSEINPUT.SIZEOF);
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
    
    protected MOUSEINPUT getElementFactory() {
      return ELEMENT_FACTORY;
    }
    
    @NativeType("LONG")
    public int dx() {
      return MOUSEINPUT.ndx(address());
    }
    
    @NativeType("LONG")
    public int dy() {
      return MOUSEINPUT.ndy(address());
    }
    
    @NativeType("DWORD")
    public int mouseData() {
      return MOUSEINPUT.nmouseData(address());
    }
    
    @NativeType("DWORD")
    public int dwFlags() {
      return MOUSEINPUT.ndwFlags(address());
    }
    
    @NativeType("DWORD")
    public int time() {
      return MOUSEINPUT.ntime(address());
    }
    
    @NativeType("ULONG_PTR")
    public long dwExtraInfo() {
      return MOUSEINPUT.ndwExtraInfo(address());
    }
    
    public Buffer dx(@NativeType("LONG") int value) {
      MOUSEINPUT.ndx(address(), value);
      return this;
    }
    
    public Buffer dy(@NativeType("LONG") int value) {
      MOUSEINPUT.ndy(address(), value);
      return this;
    }
    
    public Buffer mouseData(@NativeType("DWORD") int value) {
      MOUSEINPUT.nmouseData(address(), value);
      return this;
    }
    
    public Buffer dwFlags(@NativeType("DWORD") int value) {
      MOUSEINPUT.ndwFlags(address(), value);
      return this;
    }
    
    public Buffer time(@NativeType("DWORD") int value) {
      MOUSEINPUT.ntime(address(), value);
      return this;
    }
    
    public Buffer dwExtraInfo(@NativeType("ULONG_PTR") long value) {
      MOUSEINPUT.ndwExtraInfo(address(), value);
      return this;
    }
  }
}
