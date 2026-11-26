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

public class KEYBDINPUT extends Struct implements NativeResource {
  public static final int SIZEOF;
  
  public static final int ALIGNOF;
  
  public static final int WVK;
  
  public static final int WSCAN;
  
  public static final int DWFLAGS;
  
  public static final int TIME;
  
  public static final int DWEXTRAINFO;
  
  static {
    Struct.Layout layout = __struct(new Struct.Member[] { __member(2), 
          __member(2), 
          __member(4), 
          __member(4), 
          __member(POINTER_SIZE) });
    SIZEOF = layout.getSize();
    ALIGNOF = layout.getAlignment();
    WVK = layout.offsetof(0);
    WSCAN = layout.offsetof(1);
    DWFLAGS = layout.offsetof(2);
    TIME = layout.offsetof(3);
    DWEXTRAINFO = layout.offsetof(4);
  }
  
  public KEYBDINPUT(ByteBuffer container) {
    super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
  }
  
  public int sizeof() {
    return SIZEOF;
  }
  
  @NativeType("WORD")
  public short wVk() {
    return nwVk(address());
  }
  
  @NativeType("WORD")
  public short wScan() {
    return nwScan(address());
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
  
  public KEYBDINPUT wVk(@NativeType("WORD") short value) {
    nwVk(address(), value);
    return this;
  }
  
  public KEYBDINPUT wScan(@NativeType("WORD") short value) {
    nwScan(address(), value);
    return this;
  }
  
  public KEYBDINPUT dwFlags(@NativeType("DWORD") int value) {
    ndwFlags(address(), value);
    return this;
  }
  
  public KEYBDINPUT time(@NativeType("DWORD") int value) {
    ntime(address(), value);
    return this;
  }
  
  public KEYBDINPUT dwExtraInfo(@NativeType("ULONG_PTR") long value) {
    ndwExtraInfo(address(), value);
    return this;
  }
  
  public KEYBDINPUT set(short wVk, short wScan, int dwFlags, int time, long dwExtraInfo) {
    wVk(wVk);
    wScan(wScan);
    dwFlags(dwFlags);
    time(time);
    dwExtraInfo(dwExtraInfo);
    return this;
  }
  
  public KEYBDINPUT set(KEYBDINPUT src) {
    MemoryUtil.memCopy(src.address(), address(), (long)SIZEOF);
    return this;
  }
  
  public static KEYBDINPUT malloc() {
    return KEYBDINPUT.<KEYBDINPUT>wrap(KEYBDINPUT.class, MemoryUtil.nmemAllocChecked((long)SIZEOF));
  }
  
  public static KEYBDINPUT calloc() {
    return KEYBDINPUT.<KEYBDINPUT>wrap(KEYBDINPUT.class, MemoryUtil.nmemCallocChecked(1L, (long)SIZEOF));
  }
  
  public static KEYBDINPUT create() {
    ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
    return KEYBDINPUT.<KEYBDINPUT>wrap(KEYBDINPUT.class, MemoryUtil.memAddress(container), container);
  }
  
  public static KEYBDINPUT create(long address) {
    return KEYBDINPUT.<KEYBDINPUT>wrap(KEYBDINPUT.class, address);
  }
  
  @Nullable
  public static KEYBDINPUT createSafe(long address) {
    return (address == 0L) ? null : KEYBDINPUT.<KEYBDINPUT>wrap(KEYBDINPUT.class, address);
  }
  
  public static Buffer malloc(int capacity) {
    return KEYBDINPUT.<Buffer>wrap(Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
  }
  
  public static Buffer calloc(int capacity) {
    return KEYBDINPUT.<Buffer>wrap(Buffer.class, MemoryUtil.nmemCallocChecked((long)capacity, (long)SIZEOF), capacity);
  }
  
  public static Buffer create(int capacity) {
    ByteBuffer container = __create(capacity, SIZEOF);
    return KEYBDINPUT.<Buffer>wrap(Buffer.class, MemoryUtil.memAddress(container), capacity, container);
  }
  
  public static Buffer create(long address, int capacity) {
    return KEYBDINPUT.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  @Nullable
  public static Buffer createSafe(long address, int capacity) {
    return (address == 0L) ? null : KEYBDINPUT.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  public static KEYBDINPUT mallocStack() {
    return mallocStack(MemoryStack.stackGet());
  }
  
  public static KEYBDINPUT callocStack() {
    return callocStack(MemoryStack.stackGet());
  }
  
  public static KEYBDINPUT mallocStack(MemoryStack stack) {
    return KEYBDINPUT.<KEYBDINPUT>wrap(KEYBDINPUT.class, stack.nmalloc(ALIGNOF, SIZEOF));
  }
  
  public static KEYBDINPUT callocStack(MemoryStack stack) {
    return KEYBDINPUT.<KEYBDINPUT>wrap(KEYBDINPUT.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
  }
  
  public static Buffer mallocStack(int capacity) {
    return mallocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer callocStack(int capacity) {
    return callocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer mallocStack(int capacity, MemoryStack stack) {
    return KEYBDINPUT.<Buffer>wrap(Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
  }
  
  public static Buffer callocStack(int capacity, MemoryStack stack) {
    return KEYBDINPUT.<Buffer>wrap(Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
  }
  
  public static short nwVk(long struct) {
    return UNSAFE.getShort(null, struct + (long)WVK);
  }
  
  public static short nwScan(long struct) {
    return UNSAFE.getShort(null, struct + (long)WSCAN);
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
  
  public static void nwVk(long struct, short value) {
    UNSAFE.putShort(null, struct + (long)WVK, value);
  }
  
  public static void nwScan(long struct, short value) {
    UNSAFE.putShort(null, struct + (long)WSCAN, value);
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
  
  public static class Buffer extends StructBuffer<KEYBDINPUT, Buffer> implements NativeResource {
    private static final KEYBDINPUT ELEMENT_FACTORY = KEYBDINPUT.create(-1L);
    
    public Buffer(ByteBuffer container) {
      super(container, container.remaining() / KEYBDINPUT.SIZEOF);
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
    
    protected KEYBDINPUT getElementFactory() {
      return ELEMENT_FACTORY;
    }
    
    @NativeType("WORD")
    public short wVk() {
      return KEYBDINPUT.nwVk(address());
    }
    
    @NativeType("WORD")
    public short wScan() {
      return KEYBDINPUT.nwScan(address());
    }
    
    @NativeType("DWORD")
    public int dwFlags() {
      return KEYBDINPUT.ndwFlags(address());
    }
    
    @NativeType("DWORD")
    public int time() {
      return KEYBDINPUT.ntime(address());
    }
    
    @NativeType("ULONG_PTR")
    public long dwExtraInfo() {
      return KEYBDINPUT.ndwExtraInfo(address());
    }
    
    public Buffer wVk(@NativeType("WORD") short value) {
      KEYBDINPUT.nwVk(address(), value);
      return this;
    }
    
    public Buffer wScan(@NativeType("WORD") short value) {
      KEYBDINPUT.nwScan(address(), value);
      return this;
    }
    
    public Buffer dwFlags(@NativeType("DWORD") int value) {
      KEYBDINPUT.ndwFlags(address(), value);
      return this;
    }
    
    public Buffer time(@NativeType("DWORD") int value) {
      KEYBDINPUT.ntime(address(), value);
      return this;
    }
    
    public Buffer dwExtraInfo(@NativeType("ULONG_PTR") long value) {
      KEYBDINPUT.ndwExtraInfo(address(), value);
      return this;
    }
  }
}
