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

public class HARDWAREINPUT extends Struct implements NativeResource {
  public static final int SIZEOF;
  
  public static final int ALIGNOF;
  
  public static final int UMSG;
  
  public static final int WPARAML;
  
  public static final int WPARAMH;
  
  static {
    Struct.Layout layout = __struct(
        __member(4), 
        __member(2), 
        __member(2));
    SIZEOF = layout.getSize();
    ALIGNOF = layout.getAlignment();
    UMSG = layout.offsetof(0);
    WPARAML = layout.offsetof(1);
    WPARAMH = layout.offsetof(2);
  }
  
  public HARDWAREINPUT(ByteBuffer container) {
    super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
  }
  
  public int sizeof() {
    return SIZEOF;
  }
  
  @NativeType("DWORD")
  public int uMsg() {
    return nuMsg(address());
  }
  
  @NativeType("WORD")
  public short wParamL() {
    return nwParamL(address());
  }
  
  @NativeType("WORD")
  public short wParamH() {
    return nwParamH(address());
  }
  
  public HARDWAREINPUT uMsg(@NativeType("DWORD") int value) {
    nuMsg(address(), value);
    return this;
  }
  
  public HARDWAREINPUT wParamL(@NativeType("WORD") short value) {
    nwParamL(address(), value);
    return this;
  }
  
  public HARDWAREINPUT wParamH(@NativeType("WORD") short value) {
    nwParamH(address(), value);
    return this;
  }
  
  public HARDWAREINPUT set(int uMsg, short wParamL, short wParamH) {
    uMsg(uMsg);
    wParamL(wParamL);
    wParamH(wParamH);
    return this;
  }
  
  public HARDWAREINPUT set(HARDWAREINPUT src) {
    MemoryUtil.memCopy(src.address(), address(), (long)SIZEOF);
    return this;
  }
  
  public static HARDWAREINPUT malloc() {
    return HARDWAREINPUT.<HARDWAREINPUT>wrap(HARDWAREINPUT.class, MemoryUtil.nmemAllocChecked((long)SIZEOF));
  }
  
  public static HARDWAREINPUT calloc() {
    return HARDWAREINPUT.<HARDWAREINPUT>wrap(HARDWAREINPUT.class, MemoryUtil.nmemCallocChecked(1L, (long)SIZEOF));
  }
  
  public static HARDWAREINPUT create() {
    ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
    return HARDWAREINPUT.<HARDWAREINPUT>wrap(HARDWAREINPUT.class, MemoryUtil.memAddress(container), container);
  }
  
  public static HARDWAREINPUT create(long address) {
    return HARDWAREINPUT.<HARDWAREINPUT>wrap(HARDWAREINPUT.class, address);
  }
  
  @Nullable
  public static HARDWAREINPUT createSafe(long address) {
    return (address == 0L) ? null : HARDWAREINPUT.<HARDWAREINPUT>wrap(HARDWAREINPUT.class, address);
  }
  
  public static Buffer malloc(int capacity) {
    return HARDWAREINPUT.<Buffer>wrap(Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
  }
  
  public static Buffer calloc(int capacity) {
    return HARDWAREINPUT.<Buffer>wrap(Buffer.class, MemoryUtil.nmemCallocChecked((long)capacity, (long)SIZEOF), capacity);
  }
  
  public static Buffer create(int capacity) {
    ByteBuffer container = __create(capacity, SIZEOF);
    return HARDWAREINPUT.<Buffer>wrap(Buffer.class, MemoryUtil.memAddress(container), capacity, container);
  }
  
  public static Buffer create(long address, int capacity) {
    return HARDWAREINPUT.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  @Nullable
  public static Buffer createSafe(long address, int capacity) {
    return (address == 0L) ? null : HARDWAREINPUT.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  public static HARDWAREINPUT mallocStack() {
    return mallocStack(MemoryStack.stackGet());
  }
  
  public static HARDWAREINPUT callocStack() {
    return callocStack(MemoryStack.stackGet());
  }
  
  public static HARDWAREINPUT mallocStack(MemoryStack stack) {
    return HARDWAREINPUT.<HARDWAREINPUT>wrap(HARDWAREINPUT.class, stack.nmalloc(ALIGNOF, SIZEOF));
  }
  
  public static HARDWAREINPUT callocStack(MemoryStack stack) {
    return HARDWAREINPUT.<HARDWAREINPUT>wrap(HARDWAREINPUT.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
  }
  
  public static Buffer mallocStack(int capacity) {
    return mallocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer callocStack(int capacity) {
    return callocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer mallocStack(int capacity, MemoryStack stack) {
    return HARDWAREINPUT.<Buffer>wrap(Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
  }
  
  public static Buffer callocStack(int capacity, MemoryStack stack) {
    return HARDWAREINPUT.<Buffer>wrap(Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
  }
  
  public static int nuMsg(long struct) {
    return UNSAFE.getInt(null, struct + (long)UMSG);
  }
  
  public static short nwParamL(long struct) {
    return UNSAFE.getShort(null, struct + (long)WPARAML);
  }
  
  public static short nwParamH(long struct) {
    return UNSAFE.getShort(null, struct + (long)WPARAMH);
  }
  
  public static void nuMsg(long struct, int value) {
    UNSAFE.putInt(null, struct + (long)UMSG, value);
  }
  
  public static void nwParamL(long struct, short value) {
    UNSAFE.putShort(null, struct + (long)WPARAML, value);
  }
  
  public static void nwParamH(long struct, short value) {
    UNSAFE.putShort(null, struct + (long)WPARAMH, value);
  }
  
  public static class Buffer extends StructBuffer<HARDWAREINPUT, Buffer> implements NativeResource {
    private static final HARDWAREINPUT ELEMENT_FACTORY = HARDWAREINPUT.create(-1L);
    
    public Buffer(ByteBuffer container) {
      super(container, container.remaining() / HARDWAREINPUT.SIZEOF);
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
    
    protected HARDWAREINPUT getElementFactory() {
      return ELEMENT_FACTORY;
    }
    
    @NativeType("DWORD")
    public int uMsg() {
      return HARDWAREINPUT.nuMsg(address());
    }
    
    @NativeType("WORD")
    public short wParamL() {
      return HARDWAREINPUT.nwParamL(address());
    }
    
    @NativeType("WORD")
    public short wParamH() {
      return HARDWAREINPUT.nwParamH(address());
    }
    
    public Buffer uMsg(@NativeType("DWORD") int value) {
      HARDWAREINPUT.nuMsg(address(), value);
      return this;
    }
    
    public Buffer wParamL(@NativeType("WORD") short value) {
      HARDWAREINPUT.nwParamL(address(), value);
      return this;
    }
    
    public Buffer wParamH(@NativeType("WORD") short value) {
      HARDWAREINPUT.nwParamH(address(), value);
      return this;
    }
  }
}
