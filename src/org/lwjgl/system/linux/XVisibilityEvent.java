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

public class XVisibilityEvent extends Struct implements NativeResource {
  public static final int SIZEOF;
  
  public static final int ALIGNOF;
  
  public static final int TYPE;
  
  public static final int SERIAL;
  
  public static final int SEND_EVENT;
  
  public static final int DISPLAY;
  
  public static final int WINDOW;
  
  public static final int STATE;
  
  static {
    Struct.Layout layout = __struct(new Struct.Member[] { __member(4), 
          __member(CLONG_SIZE), 
          __member(4), 
          __member(POINTER_SIZE), 
          __member(CLONG_SIZE), 
          __member(4) });
    SIZEOF = layout.getSize();
    ALIGNOF = layout.getAlignment();
    TYPE = layout.offsetof(0);
    SERIAL = layout.offsetof(1);
    SEND_EVENT = layout.offsetof(2);
    DISPLAY = layout.offsetof(3);
    WINDOW = layout.offsetof(4);
    STATE = layout.offsetof(5);
  }
  
  public XVisibilityEvent(ByteBuffer container) {
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
  
  public int state() {
    return nstate(address());
  }
  
  public XVisibilityEvent type(int value) {
    ntype(address(), value);
    return this;
  }
  
  public XVisibilityEvent serial(@NativeType("unsigned long") long value) {
    nserial(address(), value);
    return this;
  }
  
  public XVisibilityEvent send_event(@NativeType("Bool") boolean value) {
    nsend_event(address(), value ? 1 : 0);
    return this;
  }
  
  public XVisibilityEvent display(@NativeType("Display *") long value) {
    ndisplay(address(), value);
    return this;
  }
  
  public XVisibilityEvent window(@NativeType("Window") long value) {
    nwindow(address(), value);
    return this;
  }
  
  public XVisibilityEvent state(int value) {
    nstate(address(), value);
    return this;
  }
  
  public XVisibilityEvent set(int type, long serial, boolean send_event, long display, long window, int state) {
    type(type);
    serial(serial);
    send_event(send_event);
    display(display);
    window(window);
    state(state);
    return this;
  }
  
  public XVisibilityEvent set(XVisibilityEvent src) {
    MemoryUtil.memCopy(src.address(), address(), (long)SIZEOF);
    return this;
  }
  
  public static XVisibilityEvent malloc() {
    return XVisibilityEvent.<XVisibilityEvent>wrap(XVisibilityEvent.class, MemoryUtil.nmemAllocChecked((long)SIZEOF));
  }
  
  public static XVisibilityEvent calloc() {
    return XVisibilityEvent.<XVisibilityEvent>wrap(XVisibilityEvent.class, MemoryUtil.nmemCallocChecked(1L, (long)SIZEOF));
  }
  
  public static XVisibilityEvent create() {
    ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
    return XVisibilityEvent.<XVisibilityEvent>wrap(XVisibilityEvent.class, MemoryUtil.memAddress(container), container);
  }
  
  public static XVisibilityEvent create(long address) {
    return XVisibilityEvent.<XVisibilityEvent>wrap(XVisibilityEvent.class, address);
  }
  
  @Nullable
  public static XVisibilityEvent createSafe(long address) {
    return (address == 0L) ? null : XVisibilityEvent.<XVisibilityEvent>wrap(XVisibilityEvent.class, address);
  }
  
  public static Buffer malloc(int capacity) {
    return XVisibilityEvent.<Buffer>wrap(Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
  }
  
  public static Buffer calloc(int capacity) {
    return XVisibilityEvent.<Buffer>wrap(Buffer.class, MemoryUtil.nmemCallocChecked((long)capacity, (long)SIZEOF), capacity);
  }
  
  public static Buffer create(int capacity) {
    ByteBuffer container = __create(capacity, SIZEOF);
    return XVisibilityEvent.<Buffer>wrap(Buffer.class, MemoryUtil.memAddress(container), capacity, container);
  }
  
  public static Buffer create(long address, int capacity) {
    return XVisibilityEvent.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  @Nullable
  public static Buffer createSafe(long address, int capacity) {
    return (address == 0L) ? null : XVisibilityEvent.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  public static XVisibilityEvent mallocStack() {
    return mallocStack(MemoryStack.stackGet());
  }
  
  public static XVisibilityEvent callocStack() {
    return callocStack(MemoryStack.stackGet());
  }
  
  public static XVisibilityEvent mallocStack(MemoryStack stack) {
    return XVisibilityEvent.<XVisibilityEvent>wrap(XVisibilityEvent.class, stack.nmalloc(ALIGNOF, SIZEOF));
  }
  
  public static XVisibilityEvent callocStack(MemoryStack stack) {
    return XVisibilityEvent.<XVisibilityEvent>wrap(XVisibilityEvent.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
  }
  
  public static Buffer mallocStack(int capacity) {
    return mallocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer callocStack(int capacity) {
    return callocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer mallocStack(int capacity, MemoryStack stack) {
    return XVisibilityEvent.<Buffer>wrap(Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
  }
  
  public static Buffer callocStack(int capacity, MemoryStack stack) {
    return XVisibilityEvent.<Buffer>wrap(Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
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
  
  public static int nstate(long struct) {
    return UNSAFE.getInt(null, struct + (long)STATE);
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
  
  public static void nstate(long struct, int value) {
    UNSAFE.putInt(null, struct + (long)STATE, value);
  }
  
  public static void validate(long struct) {
    Checks.check(MemoryUtil.memGetAddress(struct + (long)DISPLAY));
  }
  
  public static void validate(long array, int count) {
    for (int i = 0; i < count; i++)
      validate(array + Integer.toUnsignedLong(i) * (long)SIZEOF); 
  }
  
  public static class Buffer extends StructBuffer<XVisibilityEvent, Buffer> implements NativeResource {
    private static final XVisibilityEvent ELEMENT_FACTORY = XVisibilityEvent.create(-1L);
    
    public Buffer(ByteBuffer container) {
      super(container, container.remaining() / XVisibilityEvent.SIZEOF);
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
    
    protected XVisibilityEvent getElementFactory() {
      return ELEMENT_FACTORY;
    }
    
    public int type() {
      return XVisibilityEvent.ntype(address());
    }
    
    @NativeType("unsigned long")
    public long serial() {
      return XVisibilityEvent.nserial(address());
    }
    
    @NativeType("Bool")
    public boolean send_event() {
      return (XVisibilityEvent.nsend_event(address()) != 0);
    }
    
    @NativeType("Display *")
    public long display() {
      return XVisibilityEvent.ndisplay(address());
    }
    
    @NativeType("Window")
    public long window() {
      return XVisibilityEvent.nwindow(address());
    }
    
    public int state() {
      return XVisibilityEvent.nstate(address());
    }
    
    public Buffer type(int value) {
      XVisibilityEvent.ntype(address(), value);
      return this;
    }
    
    public Buffer serial(@NativeType("unsigned long") long value) {
      XVisibilityEvent.nserial(address(), value);
      return this;
    }
    
    public Buffer send_event(@NativeType("Bool") boolean value) {
      XVisibilityEvent.nsend_event(address(), value ? 1 : 0);
      return this;
    }
    
    public Buffer display(@NativeType("Display *") long value) {
      XVisibilityEvent.ndisplay(address(), value);
      return this;
    }
    
    public Buffer window(@NativeType("Window") long value) {
      XVisibilityEvent.nwindow(address(), value);
      return this;
    }
    
    public Buffer state(int value) {
      XVisibilityEvent.nstate(address(), value);
      return this;
    }
  }
}
