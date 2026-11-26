package org.lwjgl.system.linux;

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

public class XVisualInfo extends Struct implements NativeResource {
  public static final int SIZEOF;
  
  public static final int ALIGNOF;
  
  public static final int VISUAL;
  
  public static final int VISUALID;
  
  public static final int SCREEN;
  
  public static final int DEPTH;
  
  public static final int CLASS;
  
  public static final int RED_MASK;
  
  public static final int GREEN_MASK;
  
  public static final int BLUE_MASK;
  
  public static final int COLORMAP_SIZE;
  
  public static final int BITS_PER_RGB;
  
  static {
    Struct.Layout layout = __struct(new Struct.Member[] { __member(POINTER_SIZE), 
          __member(CLONG_SIZE), 
          __member(4), 
          __member(4), 
          __member(4), 
          __member(CLONG_SIZE), 
          __member(CLONG_SIZE), 
          __member(CLONG_SIZE), 
          __member(4), 
          __member(4) });
    SIZEOF = layout.getSize();
    ALIGNOF = layout.getAlignment();
    VISUAL = layout.offsetof(0);
    VISUALID = layout.offsetof(1);
    SCREEN = layout.offsetof(2);
    DEPTH = layout.offsetof(3);
    CLASS = layout.offsetof(4);
    RED_MASK = layout.offsetof(5);
    GREEN_MASK = layout.offsetof(6);
    BLUE_MASK = layout.offsetof(7);
    COLORMAP_SIZE = layout.offsetof(8);
    BITS_PER_RGB = layout.offsetof(9);
  }
  
  public XVisualInfo(ByteBuffer container) {
    super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
  }
  
  public int sizeof() {
    return SIZEOF;
  }
  
  @NativeType("Visual *")
  public Visual visual() {
    return nvisual(address());
  }
  
  @NativeType("VisualID")
  public long visualid() {
    return nvisualid(address());
  }
  
  public int screen() {
    return nscreen(address());
  }
  
  public int depth() {
    return ndepth(address());
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
  
  public int colormap_size() {
    return ncolormap_size(address());
  }
  
  public int bits_per_rgb() {
    return nbits_per_rgb(address());
  }
  
  public XVisualInfo visual(@NativeType("Visual *") Visual value) {
    nvisual(address(), value);
    return this;
  }
  
  public XVisualInfo visualid(@NativeType("VisualID") long value) {
    nvisualid(address(), value);
    return this;
  }
  
  public XVisualInfo screen(int value) {
    nscreen(address(), value);
    return this;
  }
  
  public XVisualInfo depth(int value) {
    ndepth(address(), value);
    return this;
  }
  
  public XVisualInfo class$(int value) {
    nclass$(address(), value);
    return this;
  }
  
  public XVisualInfo red_mask(@NativeType("unsigned long") long value) {
    nred_mask(address(), value);
    return this;
  }
  
  public XVisualInfo green_mask(@NativeType("unsigned long") long value) {
    ngreen_mask(address(), value);
    return this;
  }
  
  public XVisualInfo blue_mask(@NativeType("unsigned long") long value) {
    nblue_mask(address(), value);
    return this;
  }
  
  public XVisualInfo colormap_size(int value) {
    ncolormap_size(address(), value);
    return this;
  }
  
  public XVisualInfo bits_per_rgb(int value) {
    nbits_per_rgb(address(), value);
    return this;
  }
  
  public XVisualInfo set(Visual visual, long visualid, int screen, int depth, int class$, long red_mask, long green_mask, long blue_mask, int colormap_size, int bits_per_rgb) {
    visual(visual);
    visualid(visualid);
    screen(screen);
    depth(depth);
    class$(class$);
    red_mask(red_mask);
    green_mask(green_mask);
    blue_mask(blue_mask);
    colormap_size(colormap_size);
    bits_per_rgb(bits_per_rgb);
    return this;
  }
  
  public XVisualInfo set(XVisualInfo src) {
    MemoryUtil.memCopy(src.address(), address(), (long)SIZEOF);
    return this;
  }
  
  public static XVisualInfo malloc() {
    return XVisualInfo.<XVisualInfo>wrap(XVisualInfo.class, MemoryUtil.nmemAllocChecked((long)SIZEOF));
  }
  
  public static XVisualInfo calloc() {
    return XVisualInfo.<XVisualInfo>wrap(XVisualInfo.class, MemoryUtil.nmemCallocChecked(1L, (long)SIZEOF));
  }
  
  public static XVisualInfo create() {
    ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
    return XVisualInfo.<XVisualInfo>wrap(XVisualInfo.class, MemoryUtil.memAddress(container), container);
  }
  
  public static XVisualInfo create(long address) {
    return XVisualInfo.<XVisualInfo>wrap(XVisualInfo.class, address);
  }
  
  @Nullable
  public static XVisualInfo createSafe(long address) {
    return (address == 0L) ? null : XVisualInfo.<XVisualInfo>wrap(XVisualInfo.class, address);
  }
  
  public static Buffer malloc(int capacity) {
    return XVisualInfo.<Buffer>wrap(Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
  }
  
  public static Buffer calloc(int capacity) {
    return XVisualInfo.<Buffer>wrap(Buffer.class, MemoryUtil.nmemCallocChecked((long)capacity, (long)SIZEOF), capacity);
  }
  
  public static Buffer create(int capacity) {
    ByteBuffer container = __create(capacity, SIZEOF);
    return XVisualInfo.<Buffer>wrap(Buffer.class, MemoryUtil.memAddress(container), capacity, container);
  }
  
  public static Buffer create(long address, int capacity) {
    return XVisualInfo.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  @Nullable
  public static Buffer createSafe(long address, int capacity) {
    return (address == 0L) ? null : XVisualInfo.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  public static XVisualInfo mallocStack() {
    return mallocStack(MemoryStack.stackGet());
  }
  
  public static XVisualInfo callocStack() {
    return callocStack(MemoryStack.stackGet());
  }
  
  public static XVisualInfo mallocStack(MemoryStack stack) {
    return XVisualInfo.<XVisualInfo>wrap(XVisualInfo.class, stack.nmalloc(ALIGNOF, SIZEOF));
  }
  
  public static XVisualInfo callocStack(MemoryStack stack) {
    return XVisualInfo.<XVisualInfo>wrap(XVisualInfo.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
  }
  
  public static Buffer mallocStack(int capacity) {
    return mallocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer callocStack(int capacity) {
    return callocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer mallocStack(int capacity, MemoryStack stack) {
    return XVisualInfo.<Buffer>wrap(Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
  }
  
  public static Buffer callocStack(int capacity, MemoryStack stack) {
    return XVisualInfo.<Buffer>wrap(Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
  }
  
  public static Visual nvisual(long struct) {
    return Visual.create(MemoryUtil.memGetAddress(struct + (long)VISUAL));
  }
  
  public static long nvisualid(long struct) {
    return MemoryUtil.memGetCLong(struct + (long)VISUALID);
  }
  
  public static int nscreen(long struct) {
    return UNSAFE.getInt(null, struct + (long)SCREEN);
  }
  
  public static int ndepth(long struct) {
    return UNSAFE.getInt(null, struct + (long)DEPTH);
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
  
  public static int ncolormap_size(long struct) {
    return UNSAFE.getInt(null, struct + (long)COLORMAP_SIZE);
  }
  
  public static int nbits_per_rgb(long struct) {
    return UNSAFE.getInt(null, struct + (long)BITS_PER_RGB);
  }
  
  public static void nvisual(long struct, Visual value) {
    MemoryUtil.memPutAddress(struct + (long)VISUAL, value.address());
  }
  
  public static void nvisualid(long struct, long value) {
    MemoryUtil.memPutCLong(struct + (long)VISUALID, value);
  }
  
  public static void nscreen(long struct, int value) {
    UNSAFE.putInt(null, struct + (long)SCREEN, value);
  }
  
  public static void ndepth(long struct, int value) {
    UNSAFE.putInt(null, struct + (long)DEPTH, value);
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
  
  public static void ncolormap_size(long struct, int value) {
    UNSAFE.putInt(null, struct + (long)COLORMAP_SIZE, value);
  }
  
  public static void nbits_per_rgb(long struct, int value) {
    UNSAFE.putInt(null, struct + (long)BITS_PER_RGB, value);
  }
  
  public static void validate(long struct) {
    Checks.check(MemoryUtil.memGetAddress(struct + (long)VISUAL));
  }
  
  public static void validate(long array, int count) {
    for (int i = 0; i < count; i++)
      validate(array + Integer.toUnsignedLong(i) * (long)SIZEOF); 
  }
  
  public static class Buffer extends StructBuffer<XVisualInfo, Buffer> implements NativeResource {
    private static final XVisualInfo ELEMENT_FACTORY = XVisualInfo.create(-1L);
    
    public Buffer(ByteBuffer container) {
      super(container, container.remaining() / XVisualInfo.SIZEOF);
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
    
    protected XVisualInfo getElementFactory() {
      return ELEMENT_FACTORY;
    }
    
    @NativeType("Visual *")
    public Visual visual() {
      return XVisualInfo.nvisual(address());
    }
    
    @NativeType("VisualID")
    public long visualid() {
      return XVisualInfo.nvisualid(address());
    }
    
    public int screen() {
      return XVisualInfo.nscreen(address());
    }
    
    public int depth() {
      return XVisualInfo.ndepth(address());
    }
    
    public int class$() {
      return XVisualInfo.nclass$(address());
    }
    
    @NativeType("unsigned long")
    public long red_mask() {
      return XVisualInfo.nred_mask(address());
    }
    
    @NativeType("unsigned long")
    public long green_mask() {
      return XVisualInfo.ngreen_mask(address());
    }
    
    @NativeType("unsigned long")
    public long blue_mask() {
      return XVisualInfo.nblue_mask(address());
    }
    
    public int colormap_size() {
      return XVisualInfo.ncolormap_size(address());
    }
    
    public int bits_per_rgb() {
      return XVisualInfo.nbits_per_rgb(address());
    }
    
    public Buffer visual(@NativeType("Visual *") Visual value) {
      XVisualInfo.nvisual(address(), value);
      return this;
    }
    
    public Buffer visualid(@NativeType("VisualID") long value) {
      XVisualInfo.nvisualid(address(), value);
      return this;
    }
    
    public Buffer screen(int value) {
      XVisualInfo.nscreen(address(), value);
      return this;
    }
    
    public Buffer depth(int value) {
      XVisualInfo.ndepth(address(), value);
      return this;
    }
    
    public Buffer class$(int value) {
      XVisualInfo.nclass$(address(), value);
      return this;
    }
    
    public Buffer red_mask(@NativeType("unsigned long") long value) {
      XVisualInfo.nred_mask(address(), value);
      return this;
    }
    
    public Buffer green_mask(@NativeType("unsigned long") long value) {
      XVisualInfo.ngreen_mask(address(), value);
      return this;
    }
    
    public Buffer blue_mask(@NativeType("unsigned long") long value) {
      XVisualInfo.nblue_mask(address(), value);
      return this;
    }
    
    public Buffer colormap_size(int value) {
      XVisualInfo.ncolormap_size(address(), value);
      return this;
    }
    
    public Buffer bits_per_rgb(int value) {
      XVisualInfo.nbits_per_rgb(address(), value);
      return this;
    }
  }
}
