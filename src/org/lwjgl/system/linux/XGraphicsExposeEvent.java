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

public class XGraphicsExposeEvent extends Struct implements NativeResource {
  public static final int SIZEOF;
  
  public static final int ALIGNOF;
  
  public static final int TYPE;
  
  public static final int SERIAL;
  
  public static final int SEND_EVENT;
  
  public static final int DISPLAY;
  
  public static final int DRAWABLE;
  
  public static final int X;
  
  public static final int Y;
  
  public static final int WIDTH;
  
  public static final int HEIGHT;
  
  public static final int COUNT;
  
  public static final int MAJOR_CODE;
  
  public static final int MINOR_CODE;
  
  static {
    Struct.Layout layout = __struct(
        __member(4), 
        __member(CLONG_SIZE), 
        __member(4), 
        __member(POINTER_SIZE), 
        __member(CLONG_SIZE), 
        __member(4), 
        __member(4), 
        __member(4), 
        __member(4), 
        __member(4), 
        __member(4), 
        __member(4));
    SIZEOF = layout.getSize();
    ALIGNOF = layout.getAlignment();
    TYPE = layout.offsetof(0);
    SERIAL = layout.offsetof(1);
    SEND_EVENT = layout.offsetof(2);
    DISPLAY = layout.offsetof(3);
    DRAWABLE = layout.offsetof(4);
    X = layout.offsetof(5);
    Y = layout.offsetof(6);
    WIDTH = layout.offsetof(7);
    HEIGHT = layout.offsetof(8);
    COUNT = layout.offsetof(9);
    MAJOR_CODE = layout.offsetof(10);
    MINOR_CODE = layout.offsetof(11);
  }
  
  public XGraphicsExposeEvent(ByteBuffer container) {
    super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
  }
  
  public int sizeof() {
    return SIZEOF;
  }
  
  public int type() {
    return ntype(address());
  }
  
  @NativeType("unsigned long")
  public long serial() {
    return nserial(address());
  }
  
  @NativeType("Bool")
  public boolean send_event() {
    return (nsend_event(address()) != 0);
  }
  
  @NativeType("Display *")
  public long display() {
    return ndisplay(address());
  }
  
  @NativeType("Drawable")
  public long drawable() {
    return ndrawable(address());
  }
  
  public int x() {
    return nx(address());
  }
  
  public int y() {
    return ny(address());
  }
  
  public int width() {
    return nwidth(address());
  }
  
  public int height() {
    return nheight(address());
  }
  
  public int count() {
    return ncount(address());
  }
  
  public int major_code() {
    return nmajor_code(address());
  }
  
  public int minor_code() {
    return nminor_code(address());
  }
  
  public XGraphicsExposeEvent type(int value) {
    ntype(address(), value);
    return this;
  }
  
  public XGraphicsExposeEvent serial(@NativeType("unsigned long") long value) {
    nserial(address(), value);
    return this;
  }
  
  public XGraphicsExposeEvent send_event(@NativeType("Bool") boolean value) {
    nsend_event(address(), value ? 1 : 0);
    return this;
  }
  
  public XGraphicsExposeEvent display(@NativeType("Display *") long value) {
    ndisplay(address(), value);
    return this;
  }
  
  public XGraphicsExposeEvent drawable(@NativeType("Drawable") long value) {
    ndrawable(address(), value);
    return this;
  }
  
  public XGraphicsExposeEvent x(int value) {
    nx(address(), value);
    return this;
  }
  
  public XGraphicsExposeEvent y(int value) {
    ny(address(), value);
    return this;
  }
  
  public XGraphicsExposeEvent width(int value) {
    nwidth(address(), value);
    return this;
  }
  
  public XGraphicsExposeEvent height(int value) {
    nheight(address(), value);
    return this;
  }
  
  public XGraphicsExposeEvent count(int value) {
    ncount(address(), value);
    return this;
  }
  
  public XGraphicsExposeEvent major_code(int value) {
    nmajor_code(address(), value);
    return this;
  }
  
  public XGraphicsExposeEvent minor_code(int value) {
    nminor_code(address(), value);
    return this;
  }
  
  public XGraphicsExposeEvent set(int type, long serial, boolean send_event, long display, long drawable, int x, int y, int width, int height, int count, int major_code, int minor_code) {
    type(type);
    serial(serial);
    send_event(send_event);
    display(display);
    drawable(drawable);
    x(x);
    y(y);
    width(width);
    height(height);
    count(count);
    major_code(major_code);
    minor_code(minor_code);
    return this;
  }
  
  public XGraphicsExposeEvent set(XGraphicsExposeEvent src) {
    MemoryUtil.memCopy(src.address(), address(), (long)SIZEOF);
    return this;
  }
  
  public static XGraphicsExposeEvent malloc() {
    return XGraphicsExposeEvent.<XGraphicsExposeEvent>wrap(XGraphicsExposeEvent.class, MemoryUtil.nmemAllocChecked((long)SIZEOF));
  }
  
  public static XGraphicsExposeEvent calloc() {
    return XGraphicsExposeEvent.<XGraphicsExposeEvent>wrap(XGraphicsExposeEvent.class, MemoryUtil.nmemCallocChecked(1L, (long)SIZEOF));
  }
  
  public static XGraphicsExposeEvent create() {
    ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
    return XGraphicsExposeEvent.<XGraphicsExposeEvent>wrap(XGraphicsExposeEvent.class, MemoryUtil.memAddress(container), container);
  }
  
  public static XGraphicsExposeEvent create(long address) {
    return XGraphicsExposeEvent.<XGraphicsExposeEvent>wrap(XGraphicsExposeEvent.class, address);
  }
  
  @Nullable
  public static XGraphicsExposeEvent createSafe(long address) {
    return (address == 0L) ? null : XGraphicsExposeEvent.<XGraphicsExposeEvent>wrap(XGraphicsExposeEvent.class, address);
  }
  
  public static Buffer malloc(int capacity) {
    return XGraphicsExposeEvent.<Buffer>wrap(Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
  }
  
  public static Buffer calloc(int capacity) {
    return XGraphicsExposeEvent.<Buffer>wrap(Buffer.class, MemoryUtil.nmemCallocChecked((long)capacity, (long)SIZEOF), capacity);
  }
  
  public static Buffer create(int capacity) {
    ByteBuffer container = __create(capacity, SIZEOF);
    return XGraphicsExposeEvent.<Buffer>wrap(Buffer.class, MemoryUtil.memAddress(container), capacity, container);
  }
  
  public static Buffer create(long address, int capacity) {
    return XGraphicsExposeEvent.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  @Nullable
  public static Buffer createSafe(long address, int capacity) {
    return (address == 0L) ? null : XGraphicsExposeEvent.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  public static XGraphicsExposeEvent mallocStack() {
    return mallocStack(MemoryStack.stackGet());
  }
  
  public static XGraphicsExposeEvent callocStack() {
    return callocStack(MemoryStack.stackGet());
  }
  
  public static XGraphicsExposeEvent mallocStack(MemoryStack stack) {
    return XGraphicsExposeEvent.<XGraphicsExposeEvent>wrap(XGraphicsExposeEvent.class, stack.nmalloc(ALIGNOF, SIZEOF));
  }
  
  public static XGraphicsExposeEvent callocStack(MemoryStack stack) {
    return XGraphicsExposeEvent.<XGraphicsExposeEvent>wrap(XGraphicsExposeEvent.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
  }
  
  public static Buffer mallocStack(int capacity) {
    return mallocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer callocStack(int capacity) {
    return callocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer mallocStack(int capacity, MemoryStack stack) {
    return XGraphicsExposeEvent.<Buffer>wrap(Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
  }
  
  public static Buffer callocStack(int capacity, MemoryStack stack) {
    return XGraphicsExposeEvent.<Buffer>wrap(Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
  }
  
  public static int ntype(long struct) {
    return UNSAFE.getInt(null, struct + (long)TYPE);
  }
  
  public static long nserial(long struct) {
    return MemoryUtil.memGetCLong(struct + (long)SERIAL);
  }
  
  public static int nsend_event(long struct) {
    return UNSAFE.getInt(null, struct + (long)SEND_EVENT);
  }
  
  public static long ndisplay(long struct) {
    return MemoryUtil.memGetAddress(struct + (long)DISPLAY);
  }
  
  public static long ndrawable(long struct) {
    return MemoryUtil.memGetCLong(struct + (long)DRAWABLE);
  }
  
  public static int nx(long struct) {
    return UNSAFE.getInt(null, struct + (long)X);
  }
  
  public static int ny(long struct) {
    return UNSAFE.getInt(null, struct + (long)Y);
  }
  
  public static int nwidth(long struct) {
    return UNSAFE.getInt(null, struct + (long)WIDTH);
  }
  
  public static int nheight(long struct) {
    return UNSAFE.getInt(null, struct + (long)HEIGHT);
  }
  
  public static int ncount(long struct) {
    return UNSAFE.getInt(null, struct + (long)COUNT);
  }
  
  public static int nmajor_code(long struct) {
    return UNSAFE.getInt(null, struct + (long)MAJOR_CODE);
  }
  
  public static int nminor_code(long struct) {
    return UNSAFE.getInt(null, struct + (long)MINOR_CODE);
  }
  
  public static void ntype(long struct, int value) {
    UNSAFE.putInt(null, struct + (long)TYPE, value);
  }
  
  public static void nserial(long struct, long value) {
    MemoryUtil.memPutCLong(struct + (long)SERIAL, value);
  }
  
  public static void nsend_event(long struct, int value) {
    UNSAFE.putInt(null, struct + (long)SEND_EVENT, value);
  }
  
  public static void ndisplay(long struct, long value) {
    MemoryUtil.memPutAddress(struct + (long)DISPLAY, Checks.check(value));
  }
  
  public static void ndrawable(long struct, long value) {
    MemoryUtil.memPutCLong(struct + (long)DRAWABLE, value);
  }
  
  public static void nx(long struct, int value) {
    UNSAFE.putInt(null, struct + (long)X, value);
  }
  
  public static void ny(long struct, int value) {
    UNSAFE.putInt(null, struct + (long)Y, value);
  }
  
  public static void nwidth(long struct, int value) {
    UNSAFE.putInt(null, struct + (long)WIDTH, value);
  }
  
  public static void nheight(long struct, int value) {
    UNSAFE.putInt(null, struct + (long)HEIGHT, value);
  }
  
  public static void ncount(long struct, int value) {
    UNSAFE.putInt(null, struct + (long)COUNT, value);
  }
  
  public static void nmajor_code(long struct, int value) {
    UNSAFE.putInt(null, struct + (long)MAJOR_CODE, value);
  }
  
  public static void nminor_code(long struct, int value) {
    UNSAFE.putInt(null, struct + (long)MINOR_CODE, value);
  }
  
  public static void validate(long struct) {
    Checks.check(MemoryUtil.memGetAddress(struct + (long)DISPLAY));
  }
  
  public static void validate(long array, int count) {
    for (int i = 0; i < count; i++)
      validate(array + Integer.toUnsignedLong(i) * (long)SIZEOF); 
  }
  
  public static class Buffer extends StructBuffer<XGraphicsExposeEvent, Buffer> implements NativeResource {
    private static final XGraphicsExposeEvent ELEMENT_FACTORY = XGraphicsExposeEvent.create(-1L);
    
    public Buffer(ByteBuffer container) {
      super(container, container.remaining() / XGraphicsExposeEvent.SIZEOF);
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
    
    protected XGraphicsExposeEvent getElementFactory() {
      return ELEMENT_FACTORY;
    }
    
    public int type() {
      return XGraphicsExposeEvent.ntype(address());
    }
    
    @NativeType("unsigned long")
    public long serial() {
      return XGraphicsExposeEvent.nserial(address());
    }
    
    @NativeType("Bool")
    public boolean send_event() {
      return (XGraphicsExposeEvent.nsend_event(address()) != 0);
    }
    
    @NativeType("Display *")
    public long display() {
      return XGraphicsExposeEvent.ndisplay(address());
    }
    
    @NativeType("Drawable")
    public long drawable() {
      return XGraphicsExposeEvent.ndrawable(address());
    }
    
    public int x() {
      return XGraphicsExposeEvent.nx(address());
    }
    
    public int y() {
      return XGraphicsExposeEvent.ny(address());
    }
    
    public int width() {
      return XGraphicsExposeEvent.nwidth(address());
    }
    
    public int height() {
      return XGraphicsExposeEvent.nheight(address());
    }
    
    public int count() {
      return XGraphicsExposeEvent.ncount(address());
    }
    
    public int major_code() {
      return XGraphicsExposeEvent.nmajor_code(address());
    }
    
    public int minor_code() {
      return XGraphicsExposeEvent.nminor_code(address());
    }
    
    public Buffer type(int value) {
      XGraphicsExposeEvent.ntype(address(), value);
      return this;
    }
    
    public Buffer serial(@NativeType("unsigned long") long value) {
      XGraphicsExposeEvent.nserial(address(), value);
      return this;
    }
    
    public Buffer send_event(@NativeType("Bool") boolean value) {
      XGraphicsExposeEvent.nsend_event(address(), value ? 1 : 0);
      return this;
    }
    
    public Buffer display(@NativeType("Display *") long value) {
      XGraphicsExposeEvent.ndisplay(address(), value);
      return this;
    }
    
    public Buffer drawable(@NativeType("Drawable") long value) {
      XGraphicsExposeEvent.ndrawable(address(), value);
      return this;
    }
    
    public Buffer x(int value) {
      XGraphicsExposeEvent.nx(address(), value);
      return this;
    }
    
    public Buffer y(int value) {
      XGraphicsExposeEvent.ny(address(), value);
      return this;
    }
    
    public Buffer width(int value) {
      XGraphicsExposeEvent.nwidth(address(), value);
      return this;
    }
    
    public Buffer height(int value) {
      XGraphicsExposeEvent.nheight(address(), value);
      return this;
    }
    
    public Buffer count(int value) {
      XGraphicsExposeEvent.ncount(address(), value);
      return this;
    }
    
    public Buffer major_code(int value) {
      XGraphicsExposeEvent.nmajor_code(address(), value);
      return this;
    }
    
    public Buffer minor_code(int value) {
      XGraphicsExposeEvent.nminor_code(address(), value);
      return this;
    }
  }
}
