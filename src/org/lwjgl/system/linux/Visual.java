package org.lwjgl.system.linux;

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

public class Visual extends Struct implements NativeResource {
  public static final int SIZEOF;
  
  public static final int ALIGNOF;
  
  public static final int EXT_DATA;
  
  public static final int VISUALID;
  
  public static final int CLASS;
  
  public static final int RED_MASK;
  
  public static final int GREEN_MASK;
  
  public static final int BLUE_MASK;
  
  public static final int BITS_PER_RGB;
  
  public static final int MAP_ENTRIES;
  
  static {
    Struct.Layout layout = __struct(
        __member(POINTER_SIZE), 
        __member(CLONG_SIZE), 
        __member(4), 
        __member(CLONG_SIZE), 
        __member(CLONG_SIZE), 
        __member(CLONG_SIZE), 
        __member(4), 
        __member(4));
    SIZEOF = layout.getSize();
    ALIGNOF = layout.getAlignment();
    EXT_DATA = layout.offsetof(0);
    VISUALID = layout.offsetof(1);
    CLASS = layout.offsetof(2);
    RED_MASK = layout.offsetof(3);
    GREEN_MASK = layout.offsetof(4);
    BLUE_MASK = layout.offsetof(5);
    BITS_PER_RGB = layout.offsetof(6);
    MAP_ENTRIES = layout.offsetof(7);
  }
  
  public Visual(ByteBuffer container) {
    super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
  }
  
  public int sizeof() {
    return SIZEOF;
  }
  
  @NativeType("void *")
  public long ext_data() {
    return next_data(address());
  }
  
  @NativeType("VisualID")
  public long visualid() {
    return nvisualid(address());
  }
  
  public int class$() {
    return nclass$(address());
  }
  
  @NativeType("unsigned long")
  public long red_mask() {
    return nred_mask(address());
  }
  
  @NativeType("unsigned long")
  public long green_mask() {
    return ngreen_mask(address());
  }
  
  @NativeType("unsigned long")
  public long blue_mask() {
    return nblue_mask(address());
  }
  
  public int bits_per_rgb() {
    return nbits_per_rgb(address());
  }
  
  public int map_entries() {
    return nmap_entries(address());
  }
  
  public Visual ext_data(@NativeType("void *") long value) {
    next_data(address(), value);
    return this;
  }
  
  public Visual visualid(@NativeType("VisualID") long value) {
    nvisualid(address(), value);
    return this;
  }
  
  public Visual class$(int value) {
    nclass$(address(), value);
    return this;
  }
  
  public Visual red_mask(@NativeType("unsigned long") long value) {
    nred_mask(address(), value);
    return this;
  }
  
  public Visual green_mask(@NativeType("unsigned long") long value) {
    ngreen_mask(address(), value);
    return this;
  }
  
  public Visual blue_mask(@NativeType("unsigned long") long value) {
    nblue_mask(address(), value);
    return this;
  }
  
  public Visual bits_per_rgb(int value) {
    nbits_per_rgb(address(), value);
    return this;
  }
  
  public Visual map_entries(int value) {
    nmap_entries(address(), value);
    return this;
  }
  
  public Visual set(long ext_data, long visualid, int class$, long red_mask, long green_mask, long blue_mask, int bits_per_rgb, int map_entries) {
    ext_data(ext_data);
    visualid(visualid);
    class$(class$);
    red_mask(red_mask);
    green_mask(green_mask);
    blue_mask(blue_mask);
    bits_per_rgb(bits_per_rgb);
    map_entries(map_entries);
    return this;
  }
  
  public Visual set(Visual src) {
    MemoryUtil.memCopy(src.address(), address(), (long)SIZEOF);
    return this;
  }
  
  public static Visual malloc() {
    return Visual.<Visual>wrap(Visual.class, MemoryUtil.nmemAllocChecked((long)SIZEOF));
  }
  
  public static Visual calloc() {
    return Visual.<Visual>wrap(Visual.class, MemoryUtil.nmemCallocChecked(1L, (long)SIZEOF));
  }
  
  public static Visual create() {
    ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
    return Visual.<Visual>wrap(Visual.class, MemoryUtil.memAddress(container), container);
  }
  
  public static Visual create(long address) {
    return Visual.<Visual>wrap(Visual.class, address);
  }
  
  @Nullable
  public static Visual createSafe(long address) {
    return (address == 0L) ? null : Visual.<Visual>wrap(Visual.class, address);
  }
  
  public static Buffer malloc(int capacity) {
    return Visual.<Buffer>wrap(Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
  }
  
  public static Buffer calloc(int capacity) {
    return Visual.<Buffer>wrap(Buffer.class, MemoryUtil.nmemCallocChecked((long)capacity, (long)SIZEOF), capacity);
  }
  
  public static Buffer create(int capacity) {
    ByteBuffer container = __create(capacity, SIZEOF);
    return Visual.<Buffer>wrap(Buffer.class, MemoryUtil.memAddress(container), capacity, container);
  }
  
  public static Buffer create(long address, int capacity) {
    return Visual.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  @Nullable
  public static Buffer createSafe(long address, int capacity) {
    return (address == 0L) ? null : Visual.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  public static Visual mallocStack() {
    return mallocStack(MemoryStack.stackGet());
  }
  
  public static Visual callocStack() {
    return callocStack(MemoryStack.stackGet());
  }
  
  public static Visual mallocStack(MemoryStack stack) {
    return Visual.<Visual>wrap(Visual.class, stack.nmalloc(ALIGNOF, SIZEOF));
  }
  
  public static Visual callocStack(MemoryStack stack) {
    return Visual.<Visual>wrap(Visual.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
  }
  
  public static Buffer mallocStack(int capacity) {
    return mallocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer callocStack(int capacity) {
    return callocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer mallocStack(int capacity, MemoryStack stack) {
    return Visual.<Buffer>wrap(Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
  }
  
  public static Buffer callocStack(int capacity, MemoryStack stack) {
    return Visual.<Buffer>wrap(Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
  }
  
  public static long next_data(long struct) {
    return MemoryUtil.memGetAddress(struct + (long)EXT_DATA);
  }
  
  public static long nvisualid(long struct) {
    return MemoryUtil.memGetCLong(struct + (long)VISUALID);
  }
  
  public static int nclass$(long struct) {
    return UNSAFE.getInt(null, struct + (long)CLASS);
  }
  
  public static long nred_mask(long struct) {
    return MemoryUtil.memGetCLong(struct + (long)RED_MASK);
  }
  
  public static long ngreen_mask(long struct) {
    return MemoryUtil.memGetCLong(struct + (long)GREEN_MASK);
  }
  
  public static long nblue_mask(long struct) {
    return MemoryUtil.memGetCLong(struct + (long)BLUE_MASK);
  }
  
  public static int nbits_per_rgb(long struct) {
    return UNSAFE.getInt(null, struct + (long)BITS_PER_RGB);
  }
  
  public static int nmap_entries(long struct) {
    return UNSAFE.getInt(null, struct + (long)MAP_ENTRIES);
  }
  
  public static void next_data(long struct, long value) {
    MemoryUtil.memPutAddress(struct + (long)EXT_DATA, value);
  }
  
  public static void nvisualid(long struct, long value) {
    MemoryUtil.memPutCLong(struct + (long)VISUALID, value);
  }
  
  public static void nclass$(long struct, int value) {
    UNSAFE.putInt(null, struct + (long)CLASS, value);
  }
  
  public static void nred_mask(long struct, long value) {
    MemoryUtil.memPutCLong(struct + (long)RED_MASK, value);
  }
  
  public static void ngreen_mask(long struct, long value) {
    MemoryUtil.memPutCLong(struct + (long)GREEN_MASK, value);
  }
  
  public static void nblue_mask(long struct, long value) {
    MemoryUtil.memPutCLong(struct + (long)BLUE_MASK, value);
  }
  
  public static void nbits_per_rgb(long struct, int value) {
    UNSAFE.putInt(null, struct + (long)BITS_PER_RGB, value);
  }
  
  public static void nmap_entries(long struct, int value) {
    UNSAFE.putInt(null, struct + (long)MAP_ENTRIES, value);
  }
  
  public static class Buffer extends StructBuffer<Visual, Buffer> implements NativeResource {
    private static final Visual ELEMENT_FACTORY = Visual.create(-1L);
    
    public Buffer(ByteBuffer container) {
      super(container, container.remaining() / Visual.SIZEOF);
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
    
    protected Visual getElementFactory() {
      return ELEMENT_FACTORY;
    }
    
    @NativeType("void *")
    public long ext_data() {
      return Visual.next_data(address());
    }
    
    @NativeType("VisualID")
    public long visualid() {
      return Visual.nvisualid(address());
    }
    
    public int class$() {
      return Visual.nclass$(address());
    }
    
    @NativeType("unsigned long")
    public long red_mask() {
      return Visual.nred_mask(address());
    }
    
    @NativeType("unsigned long")
    public long green_mask() {
      return Visual.ngreen_mask(address());
    }
    
    @NativeType("unsigned long")
    public long blue_mask() {
      return Visual.nblue_mask(address());
    }
    
    public int bits_per_rgb() {
      return Visual.nbits_per_rgb(address());
    }
    
    public int map_entries() {
      return Visual.nmap_entries(address());
    }
    
    public Buffer ext_data(@NativeType("void *") long value) {
      Visual.next_data(address(), value);
      return this;
    }
    
    public Buffer visualid(@NativeType("VisualID") long value) {
      Visual.nvisualid(address(), value);
      return this;
    }
    
    public Buffer class$(int value) {
      Visual.nclass$(address(), value);
      return this;
    }
    
    public Buffer red_mask(@NativeType("unsigned long") long value) {
      Visual.nred_mask(address(), value);
      return this;
    }
    
    public Buffer green_mask(@NativeType("unsigned long") long value) {
      Visual.ngreen_mask(address(), value);
      return this;
    }
    
    public Buffer blue_mask(@NativeType("unsigned long") long value) {
      Visual.nblue_mask(address(), value);
      return this;
    }
    
    public Buffer bits_per_rgb(int value) {
      Visual.nbits_per_rgb(address(), value);
      return this;
    }
    
    public Buffer map_entries(int value) {
      Visual.nmap_entries(address(), value);
      return this;
    }
  }
}
