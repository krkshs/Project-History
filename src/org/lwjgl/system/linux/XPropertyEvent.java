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

public class XPropertyEvent extends Struct implements NativeResource {
  public static final int SIZEOF;
  
  public static final int ALIGNOF;
  
  public static final int TYPE;
  
  public static final int SERIAL;
  
  public static final int SEND_EVENT;
  
  public static final int DISPLAY;
  
  public static final int WINDOW;
  
  public static final int ATOM;
  
  public static final int TIME;
  
  public static final int STATE;
  
  static {
    Struct.Layout layout = __struct(
        __member(4), 
        __member(CLONG_SIZE), 
        __member(4), 
        __member(POINTER_SIZE), 
        __member(CLONG_SIZE), 
        __member(CLONG_SIZE), 
        __member(CLONG_SIZE), 
        __member(4));
    SIZEOF = layout.getSize();
    ALIGNOF = layout.getAlignment();
    TYPE = layout.offsetof(0);
    SERIAL = layout.offsetof(1);
    SEND_EVENT = layout.offsetof(2);
    DISPLAY = layout.offsetof(3);
    WINDOW = layout.offsetof(4);
    ATOM = layout.offsetof(5);
    TIME = layout.offsetof(6);
    STATE = layout.offsetof(7);
  }
  
  public XPropertyEvent(ByteBuffer container) {
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
  
  @NativeType("Atom")
  public long atom() {
    return natom(address());
  }
  
  @NativeType("Time")
  public long time() {
    return ntime(address());
  }
  
  public int state() {
    return nstate(address());
  }
  
  public XPropertyEvent type(int value) {
    ntype(address(), value);
    return this;
  }
  
  public XPropertyEvent serial(@NativeType("unsigned long") long value) {
    nserial(address(), value);
    return this;
  }
  
  public XPropertyEvent send_event(@NativeType("Bool") boolean value) {
    nsend_event(address(), value ? 1 : 0);
    return this;
  }
  
  public XPropertyEvent display(@NativeType("Display *") long value) {
    ndisplay(address(), value);
    return this;
  }
  
  public XPropertyEvent window(@NativeType("Window") long value) {
    nwindow(address(), value);
    return this;
  }
  
  public XPropertyEvent atom(@NativeType("Atom") long value) {
    natom(address(), value);
    return this;
  }
  
  public XPropertyEvent time(@NativeType("Time") long value) {
    ntime(address(), value);
    return this;
  }
  
  public XPropertyEvent state(int value) {
    nstate(address(), value);
    return this;
  }
  
  public XPropertyEvent set(int type, long serial, boolean send_event, long display, long window, long atom, long time, int state) {
    type(type);
    serial(serial);
    send_event(send_event);
    display(display);
    window(window);
    atom(atom);
    time(time);
    state(state);
    return this;
  }
  
  public XPropertyEvent set(XPropertyEvent src) {
    MemoryUtil.memCopy(src.address(), address(), (long)SIZEOF);
    return this;
  }
  
  public static XPropertyEvent malloc() {
    return XPropertyEvent.<XPropertyEvent>wrap(XPropertyEvent.class, MemoryUtil.nmemAllocChecked((long)SIZEOF));
  }
  
  public static XPropertyEvent calloc() {
    return XPropertyEvent.<XPropertyEvent>wrap(XPropertyEvent.class, MemoryUtil.nmemCallocChecked(1L, (long)SIZEOF));
  }
  
  public static XPropertyEvent create() {
    ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
    return XPropertyEvent.<XPropertyEvent>wrap(XPropertyEvent.class, MemoryUtil.memAddress(container), container);
  }
  
  public static XPropertyEvent create(long address) {
    return XPropertyEvent.<XPropertyEvent>wrap(XPropertyEvent.class, address);
  }
  
  @Nullable
  public static XPropertyEvent createSafe(long address) {
    return (address == 0L) ? null : XPropertyEvent.<XPropertyEvent>wrap(XPropertyEvent.class, address);
  }
  
  public static Buffer malloc(int capacity) {
    return XPropertyEvent.<Buffer>wrap(Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
  }
  
  public static Buffer calloc(int capacity) {
    return XPropertyEvent.<Buffer>wrap(Buffer.class, MemoryUtil.nmemCallocChecked((long)capacity, (long)SIZEOF), capacity);
  }
  
  public static Buffer create(int capacity) {
    ByteBuffer container = __create(capacity, SIZEOF);
    return XPropertyEvent.<Buffer>wrap(Buffer.class, MemoryUtil.memAddress(container), capacity, container);
  }
  
  public static Buffer create(long address, int capacity) {
    return XPropertyEvent.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  @Nullable
  public static Buffer createSafe(long address, int capacity) {
    return (address == 0L) ? null : XPropertyEvent.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  public static XPropertyEvent mallocStack() {
    return mallocStack(MemoryStack.stackGet());
  }
  
  public static XPropertyEvent callocStack() {
    return callocStack(MemoryStack.stackGet());
  }
  
  public static XPropertyEvent mallocStack(MemoryStack stack) {
    return XPropertyEvent.<XPropertyEvent>wrap(XPropertyEvent.class, stack.nmalloc(ALIGNOF, SIZEOF));
  }
  
  public static XPropertyEvent callocStack(MemoryStack stack) {
    return XPropertyEvent.<XPropertyEvent>wrap(XPropertyEvent.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
  }
  
  public static Buffer mallocStack(int capacity) {
    return mallocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer callocStack(int capacity) {
    return callocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer mallocStack(int capacity, MemoryStack stack) {
    return XPropertyEvent.<Buffer>wrap(Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
  }
  
  public static Buffer callocStack(int capacity, MemoryStack stack) {
    return XPropertyEvent.<Buffer>wrap(Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
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
  
  public static long natom(long struct) {
    return MemoryUtil.memGetCLong(struct + (long)ATOM);
  }
  
  public static long ntime(long struct) {
    return MemoryUtil.memGetCLong(struct + (long)TIME);
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
  
  public static void natom(long struct, long value) {
    MemoryUtil.memPutCLong(struct + (long)ATOM, value);
  }
  
  public static void ntime(long struct, long value) {
    MemoryUtil.memPutCLong(struct + (long)TIME, value);
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
  
  public static class Buffer extends StructBuffer<XPropertyEvent, Buffer> implements NativeResource {
    private static final XPropertyEvent ELEMENT_FACTORY = XPropertyEvent.create(-1L);
    
    public Buffer(ByteBuffer container) {
      super(container, container.remaining() / XPropertyEvent.SIZEOF);
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
    
    protected XPropertyEvent getElementFactory() {
      return ELEMENT_FACTORY;
    }
    
    public int type() {
      return XPropertyEvent.ntype(address());
    }
    
    @NativeType("unsigned long")
    public long serial() {
      return XPropertyEvent.nserial(address());
    }
    
    @NativeType("Bool")
    public boolean send_event() {
      return (XPropertyEvent.nsend_event(address()) != 0);
    }
    
    @NativeType("Display *")
    public long display() {
      return XPropertyEvent.ndisplay(address());
    }
    
    @NativeType("Window")
    public long window() {
      return XPropertyEvent.nwindow(address());
    }
    
    @NativeType("Atom")
    public long atom() {
      return XPropertyEvent.natom(address());
    }
    
    @NativeType("Time")
    public long time() {
      return XPropertyEvent.ntime(address());
    }
    
    public int state() {
      return XPropertyEvent.nstate(address());
    }
    
    public Buffer type(int value) {
      XPropertyEvent.ntype(address(), value);
      return this;
    }
    
    public Buffer serial(@NativeType("unsigned long") long value) {
      XPropertyEvent.nserial(address(), value);
      return this;
    }
    
    public Buffer send_event(@NativeType("Bool") boolean value) {
      XPropertyEvent.nsend_event(address(), value ? 1 : 0);
      return this;
    }
    
    public Buffer display(@NativeType("Display *") long value) {
      XPropertyEvent.ndisplay(address(), value);
      return this;
    }
    
    public Buffer window(@NativeType("Window") long value) {
      XPropertyEvent.nwindow(address(), value);
      return this;
    }
    
    public Buffer atom(@NativeType("Atom") long value) {
      XPropertyEvent.natom(address(), value);
      return this;
    }
    
    public Buffer time(@NativeType("Time") long value) {
      XPropertyEvent.ntime(address(), value);
      return this;
    }
    
    public Buffer state(int value) {
      XPropertyEvent.nstate(address(), value);
      return this;
    }
  }
}
