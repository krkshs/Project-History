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

public class MONITORINFOEX extends Struct implements NativeResource {
  public static final int SIZEOF;
  
  public static final int ALIGNOF;
  
  public static final int CBSIZE;
  
  public static final int RCMONITOR;
  
  public static final int RCWORK;
  
  public static final int DWFLAGS;
  
  public static final int SZDEVICE;
  
  static {
    Struct.Layout layout = __struct(
        __member(4), 
        __member(RECT.SIZEOF, RECT.ALIGNOF), 
        __member(RECT.SIZEOF, RECT.ALIGNOF), 
        __member(4), 
        __array(2, 32));
    SIZEOF = layout.getSize();
    ALIGNOF = layout.getAlignment();
    CBSIZE = layout.offsetof(0);
    RCMONITOR = layout.offsetof(1);
    RCWORK = layout.offsetof(2);
    DWFLAGS = layout.offsetof(3);
    SZDEVICE = layout.offsetof(4);
  }
  
  public MONITORINFOEX(ByteBuffer container) {
    super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
  }
  
  public int sizeof() {
    return SIZEOF;
  }
  
  @NativeType("DWORD")
  public int cbSize() {
    return ncbSize(address());
  }
  
  public RECT rcMonitor() {
    return nrcMonitor(address());
  }
  
  public RECT rcWork() {
    return nrcWork(address());
  }
  
  @NativeType("DWORD")
  public int dwFlags() {
    return ndwFlags(address());
  }
  
  @NativeType("TCHAR[32]")
  public ByteBuffer szDevice() {
    return nszDevice(address());
  }
  
  @NativeType("TCHAR[32]")
  public String szDeviceString() {
    return nszDeviceString(address());
  }
  
  public MONITORINFOEX cbSize(@NativeType("DWORD") int value) {
    ncbSize(address(), value);
    return this;
  }
  
  public MONITORINFOEX set(MONITORINFOEX src) {
    MemoryUtil.memCopy(src.address(), address(), (long)SIZEOF);
    return this;
  }
  
  public static MONITORINFOEX malloc() {
    return MONITORINFOEX.<MONITORINFOEX>wrap(MONITORINFOEX.class, MemoryUtil.nmemAllocChecked((long)SIZEOF));
  }
  
  public static MONITORINFOEX calloc() {
    return MONITORINFOEX.<MONITORINFOEX>wrap(MONITORINFOEX.class, MemoryUtil.nmemCallocChecked(1L, (long)SIZEOF));
  }
  
  public static MONITORINFOEX create() {
    ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
    return MONITORINFOEX.<MONITORINFOEX>wrap(MONITORINFOEX.class, MemoryUtil.memAddress(container), container);
  }
  
  public static MONITORINFOEX create(long address) {
    return MONITORINFOEX.<MONITORINFOEX>wrap(MONITORINFOEX.class, address);
  }
  
  @Nullable
  public static MONITORINFOEX createSafe(long address) {
    return (address == 0L) ? null : MONITORINFOEX.<MONITORINFOEX>wrap(MONITORINFOEX.class, address);
  }
  
  public static Buffer malloc(int capacity) {
    return MONITORINFOEX.<Buffer>wrap(Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
  }
  
  public static Buffer calloc(int capacity) {
    return MONITORINFOEX.<Buffer>wrap(Buffer.class, MemoryUtil.nmemCallocChecked((long)capacity, (long)SIZEOF), capacity);
  }
  
  public static Buffer create(int capacity) {
    ByteBuffer container = __create(capacity, SIZEOF);
    return MONITORINFOEX.<Buffer>wrap(Buffer.class, MemoryUtil.memAddress(container), capacity, container);
  }
  
  public static Buffer create(long address, int capacity) {
    return MONITORINFOEX.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  @Nullable
  public static Buffer createSafe(long address, int capacity) {
    return (address == 0L) ? null : MONITORINFOEX.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  public static MONITORINFOEX mallocStack() {
    return mallocStack(MemoryStack.stackGet());
  }
  
  public static MONITORINFOEX callocStack() {
    return callocStack(MemoryStack.stackGet());
  }
  
  public static MONITORINFOEX mallocStack(MemoryStack stack) {
    return MONITORINFOEX.<MONITORINFOEX>wrap(MONITORINFOEX.class, stack.nmalloc(ALIGNOF, SIZEOF));
  }
  
  public static MONITORINFOEX callocStack(MemoryStack stack) {
    return MONITORINFOEX.<MONITORINFOEX>wrap(MONITORINFOEX.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
  }
  
  public static Buffer mallocStack(int capacity) {
    return mallocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer callocStack(int capacity) {
    return callocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer mallocStack(int capacity, MemoryStack stack) {
    return MONITORINFOEX.<Buffer>wrap(Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
  }
  
  public static Buffer callocStack(int capacity, MemoryStack stack) {
    return MONITORINFOEX.<Buffer>wrap(Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
  }
  
  public static int ncbSize(long struct) {
    return UNSAFE.getInt(null, struct + (long)CBSIZE);
  }
  
  public static RECT nrcMonitor(long struct) {
    return RECT.create(struct + (long)RCMONITOR);
  }
  
  public static RECT nrcWork(long struct) {
    return RECT.create(struct + (long)RCWORK);
  }
  
  public static int ndwFlags(long struct) {
    return UNSAFE.getInt(null, struct + (long)DWFLAGS);
  }
  
  public static ByteBuffer nszDevice(long struct) {
    return MemoryUtil.memByteBuffer(struct + (long)SZDEVICE, 64);
  }
  
  public static String nszDeviceString(long struct) {
    return MemoryUtil.memUTF16(struct + (long)SZDEVICE);
  }
  
  public static void ncbSize(long struct, int value) {
    UNSAFE.putInt(null, struct + (long)CBSIZE, value);
  }
  
  public static class Buffer extends StructBuffer<MONITORINFOEX, Buffer> implements NativeResource {
    private static final MONITORINFOEX ELEMENT_FACTORY = MONITORINFOEX.create(-1L);
    
    public Buffer(ByteBuffer container) {
      super(container, container.remaining() / MONITORINFOEX.SIZEOF);
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
    
    protected MONITORINFOEX getElementFactory() {
      return ELEMENT_FACTORY;
    }
    
    @NativeType("DWORD")
    public int cbSize() {
      return MONITORINFOEX.ncbSize(address());
    }
    
    public RECT rcMonitor() {
      return MONITORINFOEX.nrcMonitor(address());
    }
    
    public RECT rcWork() {
      return MONITORINFOEX.nrcWork(address());
    }
    
    @NativeType("DWORD")
    public int dwFlags() {
      return MONITORINFOEX.ndwFlags(address());
    }
    
    @NativeType("TCHAR[32]")
    public ByteBuffer szDevice() {
      return MONITORINFOEX.nszDevice(address());
    }
    
    @NativeType("TCHAR[32]")
    public String szDeviceString() {
      return MONITORINFOEX.nszDeviceString(address());
    }
    
    public Buffer cbSize(@NativeType("DWORD") int value) {
      MONITORINFOEX.ncbSize(address(), value);
      return this;
    }
  }
}
