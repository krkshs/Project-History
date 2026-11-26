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

public class XSelectionClearEvent extends Struct implements NativeResource {
  public static final int SIZEOF;
  
  public static final int ALIGNOF;
  
  public static final int TYPE;
  
  public static final int SERIAL;
  
  public static final int SEND_EVENT;
  
  public static final int DISPLAY;
  
  public static final int WINDOW;
  
  public static final int SELECTION;
  
  public static final int TIME;
  
  static {
    Struct.Layout layout = __struct(
        __member(4), 
        __member(CLONG_SIZE), 
        __member(4), 
        __member(POINTER_SIZE), 
        __member(CLONG_SIZE), 
        __member(CLONG_SIZE), 
        __member(CLONG_SIZE));
    SIZEOF = layout.getSize();
    ALIGNOF = layout.getAlignment();
    TYPE = layout.offsetof(0);
    SERIAL = layout.offsetof(1);
    SEND_EVENT = layout.offsetof(2);
    DISPLAY = layout.offsetof(3);
    WINDOW = layout.offsetof(4);
    SELECTION = layout.offsetof(5);
    TIME = layout.offsetof(6);
  }
  
  public XSelectionClearEvent(ByteBuffer container) {
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
  public long selection() {
    return nselection(address());
  }
  
  @NativeType("Time")
  public long time() {
    return ntime(address());
  }
  
  public XSelectionClearEvent type(int value) {
    ntype(address(), value);
    return this;
  }
  
  public XSelectionClearEvent serial(@NativeType("unsigned long") long value) {
    nserial(address(), value);
    return this;
  }
  
  public XSelectionClearEvent send_event(@NativeType("Bool") boolean value) {
    nsend_event(address(), value ? 1 : 0);
    return this;
  }
  
  public XSelectionClearEvent display(@NativeType("Display *") long value) {
    ndisplay(address(), value);
    return this;
  }
  
  public XSelectionClearEvent window(@NativeType("Window") long value) {
    nwindow(address(), value);
    return this;
  }
  
  public XSelectionClearEvent selection(@NativeType("Atom") long value) {
    nselection(address(), value);
    return this;
  }
  
  public XSelectionClearEvent time(@NativeType("Time") long value) {
    ntime(address(), value);
    return this;
  }
  
  public XSelectionClearEvent set(int type, long serial, boolean send_event, long display, long window, long selection, long time) {
    type(type);
    serial(serial);
    send_event(send_event);
    display(display);
    window(window);
    selection(selection);
    time(time);
    return this;
  }
  
  public XSelectionClearEvent set(XSelectionClearEvent src) {
    MemoryUtil.memCopy(src.address(), address(), (long)SIZEOF);
    return this;
  }
  
  public static XSelectionClearEvent malloc() {
    return XSelectionClearEvent.<XSelectionClearEvent>wrap(XSelectionClearEvent.class, MemoryUtil.nmemAllocChecked((long)SIZEOF));
  }
  
  public static XSelectionClearEvent calloc() {
    return XSelectionClearEvent.<XSelectionClearEvent>wrap(XSelectionClearEvent.class, MemoryUtil.nmemCallocChecked(1L, (long)SIZEOF));
  }
  
  public static XSelectionClearEvent create() {
    ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
    return XSelectionClearEvent.<XSelectionClearEvent>wrap(XSelectionClearEvent.class, MemoryUtil.memAddress(container), container);
  }
  
  public static XSelectionClearEvent create(long address) {
    return XSelectionClearEvent.<XSelectionClearEvent>wrap(XSelectionClearEvent.class, address);
  }
  
  @Nullable
  public static XSelectionClearEvent createSafe(long address) {
    return (address == 0L) ? null : XSelectionClearEvent.<XSelectionClearEvent>wrap(XSelectionClearEvent.class, address);
  }
  
  public static Buffer malloc(int capacity) {
    return XSelectionClearEvent.<Buffer>wrap(Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
  }
  
  public static Buffer calloc(int capacity) {
    return XSelectionClearEvent.<Buffer>wrap(Buffer.class, MemoryUtil.nmemCallocChecked((long)capacity, (long)SIZEOF), capacity);
  }
  
  public static Buffer create(int capacity) {
    ByteBuffer container = __create(capacity, SIZEOF);
    return XSelectionClearEvent.<Buffer>wrap(Buffer.class, MemoryUtil.memAddress(container), capacity, container);
  }
  
  public static Buffer create(long address, int capacity) {
    return XSelectionClearEvent.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  @Nullable
  public static Buffer createSafe(long address, int capacity) {
    return (address == 0L) ? null : XSelectionClearEvent.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  public static XSelectionClearEvent mallocStack() {
    return mallocStack(MemoryStack.stackGet());
  }
  
  public static XSelectionClearEvent callocStack() {
    return callocStack(MemoryStack.stackGet());
  }
  
  public static XSelectionClearEvent mallocStack(MemoryStack stack) {
    return XSelectionClearEvent.<XSelectionClearEvent>wrap(XSelectionClearEvent.class, stack.nmalloc(ALIGNOF, SIZEOF));
  }
  
  public static XSelectionClearEvent callocStack(MemoryStack stack) {
    return XSelectionClearEvent.<XSelectionClearEvent>wrap(XSelectionClearEvent.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
  }
  
  public static Buffer mallocStack(int capacity) {
    return mallocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer callocStack(int capacity) {
    return callocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer mallocStack(int capacity, MemoryStack stack) {
    return XSelectionClearEvent.<Buffer>wrap(Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
  }
  
  public static Buffer callocStack(int capacity, MemoryStack stack) {
    return XSelectionClearEvent.<Buffer>wrap(Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
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
  
  public static long nselection(long struct) {
    return MemoryUtil.memGetCLong(struct + (long)SELECTION);
  }
  
  public static long ntime(long struct) {
    return MemoryUtil.memGetCLong(struct + (long)TIME);
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
  
  public static void nselection(long struct, long value) {
    MemoryUtil.memPutCLong(struct + (long)SELECTION, value);
  }
  
  public static void ntime(long struct, long value) {
    MemoryUtil.memPutCLong(struct + (long)TIME, value);
  }
  
  public static void validate(long struct) {
    Checks.check(MemoryUtil.memGetAddress(struct + (long)DISPLAY));
  }
  
  public static void validate(long array, int count) {
    for (int i = 0; i < count; i++)
      validate(array + Integer.toUnsignedLong(i) * (long)SIZEOF); 
  }
  
  public static class Buffer extends StructBuffer<XSelectionClearEvent, Buffer> implements NativeResource {
    private static final XSelectionClearEvent ELEMENT_FACTORY = XSelectionClearEvent.create(-1L);
    
    public Buffer(ByteBuffer container) {
      super(container, container.remaining() / XSelectionClearEvent.SIZEOF);
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
    
    protected XSelectionClearEvent getElementFactory() {
      return ELEMENT_FACTORY;
    }
    
    public int type() {
      return XSelectionClearEvent.ntype(address());
    }
    
    @NativeType("unsigned long")
    public long serial() {
      return XSelectionClearEvent.nserial(address());
    }
    
    @NativeType("Bool")
    public boolean send_event() {
      return (XSelectionClearEvent.nsend_event(address()) != 0);
    }
    
    @NativeType("Display *")
    public long display() {
      return XSelectionClearEvent.ndisplay(address());
    }
    
    @NativeType("Window")
    public long window() {
      return XSelectionClearEvent.nwindow(address());
    }
    
    @NativeType("Atom")
    public long selection() {
      return XSelectionClearEvent.nselection(address());
    }
    
    @NativeType("Time")
    public long time() {
      return XSelectionClearEvent.ntime(address());
    }
    
    public Buffer type(int value) {
      XSelectionClearEvent.ntype(address(), value);
      return this;
    }
    
    public Buffer serial(@NativeType("unsigned long") long value) {
      XSelectionClearEvent.nserial(address(), value);
      return this;
    }
    
    public Buffer send_event(@NativeType("Bool") boolean value) {
      XSelectionClearEvent.nsend_event(address(), value ? 1 : 0);
      return this;
    }
    
    public Buffer display(@NativeType("Display *") long value) {
      XSelectionClearEvent.ndisplay(address(), value);
      return this;
    }
    
    public Buffer window(@NativeType("Window") long value) {
      XSelectionClearEvent.nwindow(address(), value);
      return this;
    }
    
    public Buffer selection(@NativeType("Atom") long value) {
      XSelectionClearEvent.nselection(address(), value);
      return this;
    }
    
    public Buffer time(@NativeType("Time") long value) {
      XSelectionClearEvent.ntime(address(), value);
      return this;
    }
  }
}
