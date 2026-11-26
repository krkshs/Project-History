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

public class XFocusChangeEvent extends Struct implements NativeResource {
  public static final int SIZEOF;
  
  public static final int ALIGNOF;
  
  public static final int TYPE;
  
  public static final int SERIAL;
  
  public static final int SEND_EVENT;
  
  public static final int DISPLAY;
  
  public static final int WINDOW;
  
  public static final int MODE;
  
  public static final int DETAIL;
  
  static {
    Struct.Layout layout = __struct(
        __member(4), 
        __member(CLONG_SIZE), 
        __member(4), 
        __member(POINTER_SIZE), 
        __member(CLONG_SIZE), 
        __member(4), 
        __member(4));
    SIZEOF = layout.getSize();
    ALIGNOF = layout.getAlignment();
    TYPE = layout.offsetof(0);
    SERIAL = layout.offsetof(1);
    SEND_EVENT = layout.offsetof(2);
    DISPLAY = layout.offsetof(3);
    WINDOW = layout.offsetof(4);
    MODE = layout.offsetof(5);
    DETAIL = layout.offsetof(6);
  }
  
  public XFocusChangeEvent(ByteBuffer container) {
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
  public long window() {
    return nwindow(address());
  }
  
  public int mode() {
    return nmode(address());
  }
  
  public int detail() {
    return ndetail(address());
  }
  
  public XFocusChangeEvent type(int value) {
    ntype(address(), value);
    return this;
  }
  
  public XFocusChangeEvent serial(@NativeType("unsigned long") long value) {
    nserial(address(), value);
    return this;
  }
  
  public XFocusChangeEvent send_event(@NativeType("Bool") boolean value) {
    nsend_event(address(), value ? 1 : 0);
    return this;
  }
  
  public XFocusChangeEvent display(@NativeType("Display *") long value) {
    ndisplay(address(), value);
    return this;
  }
  
  public XFocusChangeEvent window(@NativeType("Window") long value) {
    nwindow(address(), value);
    return this;
  }
  
  public XFocusChangeEvent mode(int value) {
    nmode(address(), value);
    return this;
  }
  
  public XFocusChangeEvent detail(int value) {
    ndetail(address(), value);
    return this;
  }
  
  public XFocusChangeEvent set(int type, long serial, boolean send_event, long display, long window, int mode, int detail) {
    type(type);
    serial(serial);
    send_event(send_event);
    display(display);
    window(window);
    mode(mode);
    detail(detail);
    return this;
  }
  
  public XFocusChangeEvent set(XFocusChangeEvent src) {
    MemoryUtil.memCopy(src.address(), address(), (long)SIZEOF);
    return this;
  }
  
  public static XFocusChangeEvent malloc() {
    return XFocusChangeEvent.<XFocusChangeEvent>wrap(XFocusChangeEvent.class, MemoryUtil.nmemAllocChecked((long)SIZEOF));
  }
  
  public static XFocusChangeEvent calloc() {
    return XFocusChangeEvent.<XFocusChangeEvent>wrap(XFocusChangeEvent.class, MemoryUtil.nmemCallocChecked(1L, (long)SIZEOF));
  }
  
  public static XFocusChangeEvent create() {
    ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
    return XFocusChangeEvent.<XFocusChangeEvent>wrap(XFocusChangeEvent.class, MemoryUtil.memAddress(container), container);
  }
  
  public static XFocusChangeEvent create(long address) {
    return XFocusChangeEvent.<XFocusChangeEvent>wrap(XFocusChangeEvent.class, address);
  }
  
  @Nullable
  public static XFocusChangeEvent createSafe(long address) {
    return (address == 0L) ? null : XFocusChangeEvent.<XFocusChangeEvent>wrap(XFocusChangeEvent.class, address);
  }
  
  public static Buffer malloc(int capacity) {
    return XFocusChangeEvent.<Buffer>wrap(Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
  }
  
  public static Buffer calloc(int capacity) {
    return XFocusChangeEvent.<Buffer>wrap(Buffer.class, MemoryUtil.nmemCallocChecked((long)capacity, (long)SIZEOF), capacity);
  }
  
  public static Buffer create(int capacity) {
    ByteBuffer container = __create(capacity, SIZEOF);
    return XFocusChangeEvent.<Buffer>wrap(Buffer.class, MemoryUtil.memAddress(container), capacity, container);
  }
  
  public static Buffer create(long address, int capacity) {
    return XFocusChangeEvent.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  @Nullable
  public static Buffer createSafe(long address, int capacity) {
    return (address == 0L) ? null : XFocusChangeEvent.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  public static XFocusChangeEvent mallocStack() {
    return mallocStack(MemoryStack.stackGet());
  }
  
  public static XFocusChangeEvent callocStack() {
    return callocStack(MemoryStack.stackGet());
  }
  
  public static XFocusChangeEvent mallocStack(MemoryStack stack) {
    return XFocusChangeEvent.<XFocusChangeEvent>wrap(XFocusChangeEvent.class, stack.nmalloc(ALIGNOF, SIZEOF));
  }
  
  public static XFocusChangeEvent callocStack(MemoryStack stack) {
    return XFocusChangeEvent.<XFocusChangeEvent>wrap(XFocusChangeEvent.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
  }
  
  public static Buffer mallocStack(int capacity) {
    return mallocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer callocStack(int capacity) {
    return callocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer mallocStack(int capacity, MemoryStack stack) {
    return XFocusChangeEvent.<Buffer>wrap(Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
  }
  
  public static Buffer callocStack(int capacity, MemoryStack stack) {
    return XFocusChangeEvent.<Buffer>wrap(Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
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
  
  public static long nwindow(long struct) {
    return MemoryUtil.memGetCLong(struct + (long)WINDOW);
  }
  
  public static int nmode(long struct) {
    return UNSAFE.getInt(null, struct + (long)MODE);
  }
  
  public static int ndetail(long struct) {
    return UNSAFE.getInt(null, struct + (long)DETAIL);
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
  
  public static void nwindow(long struct, long value) {
    MemoryUtil.memPutCLong(struct + (long)WINDOW, value);
  }
  
  public static void nmode(long struct, int value) {
    UNSAFE.putInt(null, struct + (long)MODE, value);
  }
  
  public static void ndetail(long struct, int value) {
    UNSAFE.putInt(null, struct + (long)DETAIL, value);
  }
  
  public static void validate(long struct) {
    Checks.check(MemoryUtil.memGetAddress(struct + (long)DISPLAY));
  }
  
  public static void validate(long array, int count) {
    for (int i = 0; i < count; i++)
      validate(array + Integer.toUnsignedLong(i) * (long)SIZEOF); 
  }
  
  public static class Buffer extends StructBuffer<XFocusChangeEvent, Buffer> implements NativeResource {
    private static final XFocusChangeEvent ELEMENT_FACTORY = XFocusChangeEvent.create(-1L);
    
    public Buffer(ByteBuffer container) {
      super(container, container.remaining() / XFocusChangeEvent.SIZEOF);
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
    
    protected XFocusChangeEvent getElementFactory() {
      return ELEMENT_FACTORY;
    }
    
    public int type() {
      return XFocusChangeEvent.ntype(address());
    }
    
    @NativeType("unsigned long")
    public long serial() {
      return XFocusChangeEvent.nserial(address());
    }
    
    @NativeType("Bool")
    public boolean send_event() {
      return (XFocusChangeEvent.nsend_event(address()) != 0);
    }
    
    @NativeType("Display *")
    public long display() {
      return XFocusChangeEvent.ndisplay(address());
    }
    
    @NativeType("Window")
    public long window() {
      return XFocusChangeEvent.nwindow(address());
    }
    
    public int mode() {
      return XFocusChangeEvent.nmode(address());
    }
    
    public int detail() {
      return XFocusChangeEvent.ndetail(address());
    }
    
    public Buffer type(int value) {
      XFocusChangeEvent.ntype(address(), value);
      return this;
    }
    
    public Buffer serial(@NativeType("unsigned long") long value) {
      XFocusChangeEvent.nserial(address(), value);
      return this;
    }
    
    public Buffer send_event(@NativeType("Bool") boolean value) {
      XFocusChangeEvent.nsend_event(address(), value ? 1 : 0);
      return this;
    }
    
    public Buffer display(@NativeType("Display *") long value) {
      XFocusChangeEvent.ndisplay(address(), value);
      return this;
    }
    
    public Buffer window(@NativeType("Window") long value) {
      XFocusChangeEvent.nwindow(address(), value);
      return this;
    }
    
    public Buffer mode(int value) {
      XFocusChangeEvent.nmode(address(), value);
      return this;
    }
    
    public Buffer detail(int value) {
      XFocusChangeEvent.ndetail(address(), value);
      return this;
    }
  }
}
