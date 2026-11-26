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

public class XConfigureRequestEvent extends Struct implements NativeResource {
  public static final int SIZEOF;
  
  public static final int ALIGNOF;
  
  public static final int TYPE;
  
  public static final int SERIAL;
  
  public static final int SEND_EVENT;
  
  public static final int DISPLAY;
  
  public static final int PARENT;
  
  public static final int WINDOW;
  
  public static final int X;
  
  public static final int Y;
  
  public static final int WIDTH;
  
  public static final int HEIGHT;
  
  public static final int BORDER_WIDTH;
  
  public static final int ABOVE;
  
  public static final int DETAIL;
  
  public static final int VALUE_MASK;
  
  static {
    Struct.Layout layout = __struct(
        __member(4), 
        __member(CLONG_SIZE), 
        __member(4), 
        __member(POINTER_SIZE), 
        __member(CLONG_SIZE), 
        __member(CLONG_SIZE), 
        __member(4), 
        __member(4), 
        __member(4), 
        __member(4), 
        __member(4), 
        __member(CLONG_SIZE), 
        __member(4), 
        __member(CLONG_SIZE));
    SIZEOF = layout.getSize();
    ALIGNOF = layout.getAlignment();
    TYPE = layout.offsetof(0);
    SERIAL = layout.offsetof(1);
    SEND_EVENT = layout.offsetof(2);
    DISPLAY = layout.offsetof(3);
    PARENT = layout.offsetof(4);
    WINDOW = layout.offsetof(5);
    X = layout.offsetof(6);
    Y = layout.offsetof(7);
    WIDTH = layout.offsetof(8);
    HEIGHT = layout.offsetof(9);
    BORDER_WIDTH = layout.offsetof(10);
    ABOVE = layout.offsetof(11);
    DETAIL = layout.offsetof(12);
    VALUE_MASK = layout.offsetof(13);
  }
  
  public XConfigureRequestEvent(ByteBuffer container) {
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
  
  @NativeType("Window")
  public long parent() {
    return nparent(address());
  }
  
  @NativeType("Window")
  public long window() {
    return nwindow(address());
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
  
  public int border_width() {
    return nborder_width(address());
  }
  
  @NativeType("Window")
  public long above() {
    return nabove(address());
  }
  
  public int detail() {
    return ndetail(address());
  }
  
  @NativeType("unsigned long")
  public long value_mask() {
    return nvalue_mask(address());
  }
  
  public XConfigureRequestEvent type(int value) {
    ntype(address(), value);
    return this;
  }
  
  public XConfigureRequestEvent serial(@NativeType("unsigned long") long value) {
    nserial(address(), value);
    return this;
  }
  
  public XConfigureRequestEvent send_event(@NativeType("Bool") boolean value) {
    nsend_event(address(), value ? 1 : 0);
    return this;
  }
  
  public XConfigureRequestEvent display(@NativeType("Display *") long value) {
    ndisplay(address(), value);
    return this;
  }
  
  public XConfigureRequestEvent parent(@NativeType("Window") long value) {
    nparent(address(), value);
    return this;
  }
  
  public XConfigureRequestEvent window(@NativeType("Window") long value) {
    nwindow(address(), value);
    return this;
  }
  
  public XConfigureRequestEvent x(int value) {
    nx(address(), value);
    return this;
  }
  
  public XConfigureRequestEvent y(int value) {
    ny(address(), value);
    return this;
  }
  
  public XConfigureRequestEvent width(int value) {
    nwidth(address(), value);
    return this;
  }
  
  public XConfigureRequestEvent height(int value) {
    nheight(address(), value);
    return this;
  }
  
  public XConfigureRequestEvent border_width(int value) {
    nborder_width(address(), value);
    return this;
  }
  
  public XConfigureRequestEvent above(@NativeType("Window") long value) {
    nabove(address(), value);
    return this;
  }
  
  public XConfigureRequestEvent detail(int value) {
    ndetail(address(), value);
    return this;
  }
  
  public XConfigureRequestEvent value_mask(@NativeType("unsigned long") long value) {
    nvalue_mask(address(), value);
    return this;
  }
  
  public XConfigureRequestEvent set(int type, long serial, boolean send_event, long display, long parent, long window, int x, int y, int width, int height, int border_width, long above, int detail, long value_mask) {
    type(type);
    serial(serial);
    send_event(send_event);
    display(display);
    parent(parent);
    window(window);
    x(x);
    y(y);
    width(width);
    height(height);
    border_width(border_width);
    above(above);
    detail(detail);
    value_mask(value_mask);
    return this;
  }
  
  public XConfigureRequestEvent set(XConfigureRequestEvent src) {
    MemoryUtil.memCopy(src.address(), address(), (long)SIZEOF);
    return this;
  }
  
  public static XConfigureRequestEvent malloc() {
    return XConfigureRequestEvent.<XConfigureRequestEvent>wrap(XConfigureRequestEvent.class, MemoryUtil.nmemAllocChecked((long)SIZEOF));
  }
  
  public static XConfigureRequestEvent calloc() {
    return XConfigureRequestEvent.<XConfigureRequestEvent>wrap(XConfigureRequestEvent.class, MemoryUtil.nmemCallocChecked(1L, (long)SIZEOF));
  }
  
  public static XConfigureRequestEvent create() {
    ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
    return XConfigureRequestEvent.<XConfigureRequestEvent>wrap(XConfigureRequestEvent.class, MemoryUtil.memAddress(container), container);
  }
  
  public static XConfigureRequestEvent create(long address) {
    return XConfigureRequestEvent.<XConfigureRequestEvent>wrap(XConfigureRequestEvent.class, address);
  }
  
  @Nullable
  public static XConfigureRequestEvent createSafe(long address) {
    return (address == 0L) ? null : XConfigureRequestEvent.<XConfigureRequestEvent>wrap(XConfigureRequestEvent.class, address);
  }
  
  public static Buffer malloc(int capacity) {
    return XConfigureRequestEvent.<Buffer>wrap(Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
  }
  
  public static Buffer calloc(int capacity) {
    return XConfigureRequestEvent.<Buffer>wrap(Buffer.class, MemoryUtil.nmemCallocChecked((long)capacity, (long)SIZEOF), capacity);
  }
  
  public static Buffer create(int capacity) {
    ByteBuffer container = __create(capacity, SIZEOF);
    return XConfigureRequestEvent.<Buffer>wrap(Buffer.class, MemoryUtil.memAddress(container), capacity, container);
  }
  
  public static Buffer create(long address, int capacity) {
    return XConfigureRequestEvent.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  @Nullable
  public static Buffer createSafe(long address, int capacity) {
    return (address == 0L) ? null : XConfigureRequestEvent.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  public static XConfigureRequestEvent mallocStack() {
    return mallocStack(MemoryStack.stackGet());
  }
  
  public static XConfigureRequestEvent callocStack() {
    return callocStack(MemoryStack.stackGet());
  }
  
  public static XConfigureRequestEvent mallocStack(MemoryStack stack) {
    return XConfigureRequestEvent.<XConfigureRequestEvent>wrap(XConfigureRequestEvent.class, stack.nmalloc(ALIGNOF, SIZEOF));
  }
  
  public static XConfigureRequestEvent callocStack(MemoryStack stack) {
    return XConfigureRequestEvent.<XConfigureRequestEvent>wrap(XConfigureRequestEvent.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
  }
  
  public static Buffer mallocStack(int capacity) {
    return mallocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer callocStack(int capacity) {
    return callocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer mallocStack(int capacity, MemoryStack stack) {
    return XConfigureRequestEvent.<Buffer>wrap(Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
  }
  
  public static Buffer callocStack(int capacity, MemoryStack stack) {
    return XConfigureRequestEvent.<Buffer>wrap(Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
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
  
  public static long nparent(long struct) {
    return MemoryUtil.memGetCLong(struct + (long)PARENT);
  }
  
  public static long nwindow(long struct) {
    return MemoryUtil.memGetCLong(struct + (long)WINDOW);
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
  
  public static int nborder_width(long struct) {
    return UNSAFE.getInt(null, struct + (long)BORDER_WIDTH);
  }
  
  public static long nabove(long struct) {
    return MemoryUtil.memGetCLong(struct + (long)ABOVE);
  }
  
  public static int ndetail(long struct) {
    return UNSAFE.getInt(null, struct + (long)DETAIL);
  }
  
  public static long nvalue_mask(long struct) {
    return MemoryUtil.memGetCLong(struct + (long)VALUE_MASK);
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
  
  public static void nparent(long struct, long value) {
    MemoryUtil.memPutCLong(struct + (long)PARENT, value);
  }
  
  public static void nwindow(long struct, long value) {
    MemoryUtil.memPutCLong(struct + (long)WINDOW, value);
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
  
  public static void nborder_width(long struct, int value) {
    UNSAFE.putInt(null, struct + (long)BORDER_WIDTH, value);
  }
  
  public static void nabove(long struct, long value) {
    MemoryUtil.memPutCLong(struct + (long)ABOVE, value);
  }
  
  public static void ndetail(long struct, int value) {
    UNSAFE.putInt(null, struct + (long)DETAIL, value);
  }
  
  public static void nvalue_mask(long struct, long value) {
    MemoryUtil.memPutCLong(struct + (long)VALUE_MASK, value);
  }
  
  public static void validate(long struct) {
    Checks.check(MemoryUtil.memGetAddress(struct + (long)DISPLAY));
  }
  
  public static void validate(long array, int count) {
    for (int i = 0; i < count; i++)
      validate(array + Integer.toUnsignedLong(i) * (long)SIZEOF); 
  }
  
  public static class Buffer extends StructBuffer<XConfigureRequestEvent, Buffer> implements NativeResource {
    private static final XConfigureRequestEvent ELEMENT_FACTORY = XConfigureRequestEvent.create(-1L);
    
    public Buffer(ByteBuffer container) {
      super(container, container.remaining() / XConfigureRequestEvent.SIZEOF);
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
    
    protected XConfigureRequestEvent getElementFactory() {
      return ELEMENT_FACTORY;
    }
    
    public int type() {
      return XConfigureRequestEvent.ntype(address());
    }
    
    @NativeType("unsigned long")
    public long serial() {
      return XConfigureRequestEvent.nserial(address());
    }
    
    @NativeType("Bool")
    public boolean send_event() {
      return (XConfigureRequestEvent.nsend_event(address()) != 0);
    }
    
    @NativeType("Display *")
    public long display() {
      return XConfigureRequestEvent.ndisplay(address());
    }
    
    @NativeType("Window")
    public long parent() {
      return XConfigureRequestEvent.nparent(address());
    }
    
    @NativeType("Window")
    public long window() {
      return XConfigureRequestEvent.nwindow(address());
    }
    
    public int x() {
      return XConfigureRequestEvent.nx(address());
    }
    
    public int y() {
      return XConfigureRequestEvent.ny(address());
    }
    
    public int width() {
      return XConfigureRequestEvent.nwidth(address());
    }
    
    public int height() {
      return XConfigureRequestEvent.nheight(address());
    }
    
    public int border_width() {
      return XConfigureRequestEvent.nborder_width(address());
    }
    
    @NativeType("Window")
    public long above() {
      return XConfigureRequestEvent.nabove(address());
    }
    
    public int detail() {
      return XConfigureRequestEvent.ndetail(address());
    }
    
    @NativeType("unsigned long")
    public long value_mask() {
      return XConfigureRequestEvent.nvalue_mask(address());
    }
    
    public Buffer type(int value) {
      XConfigureRequestEvent.ntype(address(), value);
      return this;
    }
    
    public Buffer serial(@NativeType("unsigned long") long value) {
      XConfigureRequestEvent.nserial(address(), value);
      return this;
    }
    
    public Buffer send_event(@NativeType("Bool") boolean value) {
      XConfigureRequestEvent.nsend_event(address(), value ? 1 : 0);
      return this;
    }
    
    public Buffer display(@NativeType("Display *") long value) {
      XConfigureRequestEvent.ndisplay(address(), value);
      return this;
    }
    
    public Buffer parent(@NativeType("Window") long value) {
      XConfigureRequestEvent.nparent(address(), value);
      return this;
    }
    
    public Buffer window(@NativeType("Window") long value) {
      XConfigureRequestEvent.nwindow(address(), value);
      return this;
    }
    
    public Buffer x(int value) {
      XConfigureRequestEvent.nx(address(), value);
      return this;
    }
    
    public Buffer y(int value) {
      XConfigureRequestEvent.ny(address(), value);
      return this;
    }
    
    public Buffer width(int value) {
      XConfigureRequestEvent.nwidth(address(), value);
      return this;
    }
    
    public Buffer height(int value) {
      XConfigureRequestEvent.nheight(address(), value);
      return this;
    }
    
    public Buffer border_width(int value) {
      XConfigureRequestEvent.nborder_width(address(), value);
      return this;
    }
    
    public Buffer above(@NativeType("Window") long value) {
      XConfigureRequestEvent.nabove(address(), value);
      return this;
    }
    
    public Buffer detail(int value) {
      XConfigureRequestEvent.ndetail(address(), value);
      return this;
    }
    
    public Buffer value_mask(@NativeType("unsigned long") long value) {
      XConfigureRequestEvent.nvalue_mask(address(), value);
      return this;
    }
  }
}
