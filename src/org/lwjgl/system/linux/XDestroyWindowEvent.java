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

public class XDestroyWindowEvent extends Struct implements NativeResource {
  public static final int SIZEOF;
  
  public static final int ALIGNOF;
  
  public static final int TYPE;
  
  public static final int SERIAL;
  
  public static final int SEND_EVENT;
  
  public static final int DISPLAY;
  
  public static final int EVENT;
  
  public static final int WINDOW;
  
  static {
    Struct.Layout layout = __struct(
        __member(4), 
        __member(CLONG_SIZE), 
        __member(4), 
        __member(POINTER_SIZE), 
        __member(CLONG_SIZE), 
        __member(CLONG_SIZE));
    SIZEOF = layout.getSize();
    ALIGNOF = layout.getAlignment();
    TYPE = layout.offsetof(0);
    SERIAL = layout.offsetof(1);
    SEND_EVENT = layout.offsetof(2);
    DISPLAY = layout.offsetof(3);
    EVENT = layout.offsetof(4);
    WINDOW = layout.offsetof(5);
  }
  
  public XDestroyWindowEvent(ByteBuffer container) {
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
  public long event() {
    return nevent(address());
  }
  
  @NativeType("Window")
  public long window() {
    return nwindow(address());
  }
  
  public XDestroyWindowEvent type(int value) {
    ntype(address(), value);
    return this;
  }
  
  public XDestroyWindowEvent serial(@NativeType("unsigned long") long value) {
    nserial(address(), value);
    return this;
  }
  
  public XDestroyWindowEvent send_event(@NativeType("Bool") boolean value) {
    nsend_event(address(), value ? 1 : 0);
    return this;
  }
  
  public XDestroyWindowEvent display(@NativeType("Display *") long value) {
    ndisplay(address(), value);
    return this;
  }
  
  public XDestroyWindowEvent event(@NativeType("Window") long value) {
    nevent(address(), value);
    return this;
  }
  
  public XDestroyWindowEvent window(@NativeType("Window") long value) {
    nwindow(address(), value);
    return this;
  }
  
  public XDestroyWindowEvent set(int type, long serial, boolean send_event, long display, long event, long window) {
    type(type);
    serial(serial);
    send_event(send_event);
    display(display);
    event(event);
    window(window);
    return this;
  }
  
  public XDestroyWindowEvent set(XDestroyWindowEvent src) {
    MemoryUtil.memCopy(src.address(), address(), (long)SIZEOF);
    return this;
  }
  
  public static XDestroyWindowEvent malloc() {
    return XDestroyWindowEvent.<XDestroyWindowEvent>wrap(XDestroyWindowEvent.class, MemoryUtil.nmemAllocChecked((long)SIZEOF));
  }
  
  public static XDestroyWindowEvent calloc() {
    return XDestroyWindowEvent.<XDestroyWindowEvent>wrap(XDestroyWindowEvent.class, MemoryUtil.nmemCallocChecked(1L, (long)SIZEOF));
  }
  
  public static XDestroyWindowEvent create() {
    ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
    return XDestroyWindowEvent.<XDestroyWindowEvent>wrap(XDestroyWindowEvent.class, MemoryUtil.memAddress(container), container);
  }
  
  public static XDestroyWindowEvent create(long address) {
    return XDestroyWindowEvent.<XDestroyWindowEvent>wrap(XDestroyWindowEvent.class, address);
  }
  
  @Nullable
  public static XDestroyWindowEvent createSafe(long address) {
    return (address == 0L) ? null : XDestroyWindowEvent.<XDestroyWindowEvent>wrap(XDestroyWindowEvent.class, address);
  }
  
  public static Buffer malloc(int capacity) {
    return XDestroyWindowEvent.<Buffer>wrap(Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
  }
  
  public static Buffer calloc(int capacity) {
    return XDestroyWindowEvent.<Buffer>wrap(Buffer.class, MemoryUtil.nmemCallocChecked((long)capacity, (long)SIZEOF), capacity);
  }
  
  public static Buffer create(int capacity) {
    ByteBuffer container = __create(capacity, SIZEOF);
    return XDestroyWindowEvent.<Buffer>wrap(Buffer.class, MemoryUtil.memAddress(container), capacity, container);
  }
  
  public static Buffer create(long address, int capacity) {
    return XDestroyWindowEvent.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  @Nullable
  public static Buffer createSafe(long address, int capacity) {
    return (address == 0L) ? null : XDestroyWindowEvent.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  public static XDestroyWindowEvent mallocStack() {
    return mallocStack(MemoryStack.stackGet());
  }
  
  public static XDestroyWindowEvent callocStack() {
    return callocStack(MemoryStack.stackGet());
  }
  
  public static XDestroyWindowEvent mallocStack(MemoryStack stack) {
    return XDestroyWindowEvent.<XDestroyWindowEvent>wrap(XDestroyWindowEvent.class, stack.nmalloc(ALIGNOF, SIZEOF));
  }
  
  public static XDestroyWindowEvent callocStack(MemoryStack stack) {
    return XDestroyWindowEvent.<XDestroyWindowEvent>wrap(XDestroyWindowEvent.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
  }
  
  public static Buffer mallocStack(int capacity) {
    return mallocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer callocStack(int capacity) {
    return callocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer mallocStack(int capacity, MemoryStack stack) {
    return XDestroyWindowEvent.<Buffer>wrap(Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
  }
  
  public static Buffer callocStack(int capacity, MemoryStack stack) {
    return XDestroyWindowEvent.<Buffer>wrap(Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
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
  
  public static long nevent(long struct) {
    return MemoryUtil.memGetCLong(struct + (long)EVENT);
  }
  
  public static long nwindow(long struct) {
    return MemoryUtil.memGetCLong(struct + (long)WINDOW);
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
  
  public static void nevent(long struct, long value) {
    MemoryUtil.memPutCLong(struct + (long)EVENT, value);
  }
  
  public static void nwindow(long struct, long value) {
    MemoryUtil.memPutCLong(struct + (long)WINDOW, value);
  }
  
  public static void validate(long struct) {
    Checks.check(MemoryUtil.memGetAddress(struct + (long)DISPLAY));
  }
  
  public static void validate(long array, int count) {
    for (int i = 0; i < count; i++)
      validate(array + Integer.toUnsignedLong(i) * (long)SIZEOF); 
  }
  
  public static class Buffer extends StructBuffer<XDestroyWindowEvent, Buffer> implements NativeResource {
    private static final XDestroyWindowEvent ELEMENT_FACTORY = XDestroyWindowEvent.create(-1L);
    
    public Buffer(ByteBuffer container) {
      super(container, container.remaining() / XDestroyWindowEvent.SIZEOF);
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
    
    protected XDestroyWindowEvent getElementFactory() {
      return ELEMENT_FACTORY;
    }
    
    public int type() {
      return XDestroyWindowEvent.ntype(address());
    }
    
    @NativeType("unsigned long")
    public long serial() {
      return XDestroyWindowEvent.nserial(address());
    }
    
    @NativeType("Bool")
    public boolean send_event() {
      return (XDestroyWindowEvent.nsend_event(address()) != 0);
    }
    
    @NativeType("Display *")
    public long display() {
      return XDestroyWindowEvent.ndisplay(address());
    }
    
    @NativeType("Window")
    public long event() {
      return XDestroyWindowEvent.nevent(address());
    }
    
    @NativeType("Window")
    public long window() {
      return XDestroyWindowEvent.nwindow(address());
    }
    
    public Buffer type(int value) {
      XDestroyWindowEvent.ntype(address(), value);
      return this;
    }
    
    public Buffer serial(@NativeType("unsigned long") long value) {
      XDestroyWindowEvent.nserial(address(), value);
      return this;
    }
    
    public Buffer send_event(@NativeType("Bool") boolean value) {
      XDestroyWindowEvent.nsend_event(address(), value ? 1 : 0);
      return this;
    }
    
    public Buffer display(@NativeType("Display *") long value) {
      XDestroyWindowEvent.ndisplay(address(), value);
      return this;
    }
    
    public Buffer event(@NativeType("Window") long value) {
      XDestroyWindowEvent.nevent(address(), value);
      return this;
    }
    
    public Buffer window(@NativeType("Window") long value) {
      XDestroyWindowEvent.nwindow(address(), value);
      return this;
    }
  }
}
