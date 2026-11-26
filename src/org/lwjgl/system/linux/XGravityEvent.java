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

public class XGravityEvent extends Struct implements NativeResource {
  public static final int SIZEOF;
  
  public static final int ALIGNOF;
  
  public static final int TYPE;
  
  public static final int SERIAL;
  
  public static final int SEND_EVENT;
  
  public static final int DISPLAY;
  
  public static final int EVENT;
  
  public static final int WINDOW;
  
  public static final int X;
  
  public static final int Y;
  
  static {
    Struct.Layout layout = __struct(
        __member(4), 
        __member(CLONG_SIZE), 
        __member(4), 
        __member(POINTER_SIZE), 
        __member(CLONG_SIZE), 
        __member(CLONG_SIZE), 
        __member(4), 
        __member(4));
    SIZEOF = layout.getSize();
    ALIGNOF = layout.getAlignment();
    TYPE = layout.offsetof(0);
    SERIAL = layout.offsetof(1);
    SEND_EVENT = layout.offsetof(2);
    DISPLAY = layout.offsetof(3);
    EVENT = layout.offsetof(4);
    WINDOW = layout.offsetof(5);
    X = layout.offsetof(6);
    Y = layout.offsetof(7);
  }
  
  public XGravityEvent(ByteBuffer container) {
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
  
  public int x() {
    return nx(address());
  }
  
  public int y() {
    return ny(address());
  }
  
  public XGravityEvent type(int value) {
    ntype(address(), value);
    return this;
  }
  
  public XGravityEvent serial(@NativeType("unsigned long") long value) {
    nserial(address(), value);
    return this;
  }
  
  public XGravityEvent send_event(@NativeType("Bool") boolean value) {
    nsend_event(address(), value ? 1 : 0);
    return this;
  }
  
  public XGravityEvent display(@NativeType("Display *") long value) {
    ndisplay(address(), value);
    return this;
  }
  
  public XGravityEvent event(@NativeType("Window") long value) {
    nevent(address(), value);
    return this;
  }
  
  public XGravityEvent window(@NativeType("Window") long value) {
    nwindow(address(), value);
    return this;
  }
  
  public XGravityEvent x(int value) {
    nx(address(), value);
    return this;
  }
  
  public XGravityEvent y(int value) {
    ny(address(), value);
    return this;
  }
  
  public XGravityEvent set(int type, long serial, boolean send_event, long display, long event, long window, int x, int y) {
    type(type);
    serial(serial);
    send_event(send_event);
    display(display);
    event(event);
    window(window);
    x(x);
    y(y);
    return this;
  }
  
  public XGravityEvent set(XGravityEvent src) {
    MemoryUtil.memCopy(src.address(), address(), (long)SIZEOF);
    return this;
  }
  
  public static XGravityEvent malloc() {
    return XGravityEvent.<XGravityEvent>wrap(XGravityEvent.class, MemoryUtil.nmemAllocChecked((long)SIZEOF));
  }
  
  public static XGravityEvent calloc() {
    return XGravityEvent.<XGravityEvent>wrap(XGravityEvent.class, MemoryUtil.nmemCallocChecked(1L, (long)SIZEOF));
  }
  
  public static XGravityEvent create() {
    ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
    return XGravityEvent.<XGravityEvent>wrap(XGravityEvent.class, MemoryUtil.memAddress(container), container);
  }
  
  public static XGravityEvent create(long address) {
    return XGravityEvent.<XGravityEvent>wrap(XGravityEvent.class, address);
  }
  
  @Nullable
  public static XGravityEvent createSafe(long address) {
    return (address == 0L) ? null : XGravityEvent.<XGravityEvent>wrap(XGravityEvent.class, address);
  }
  
  public static Buffer malloc(int capacity) {
    return XGravityEvent.<Buffer>wrap(Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
  }
  
  public static Buffer calloc(int capacity) {
    return XGravityEvent.<Buffer>wrap(Buffer.class, MemoryUtil.nmemCallocChecked((long)capacity, (long)SIZEOF), capacity);
  }
  
  public static Buffer create(int capacity) {
    ByteBuffer container = __create(capacity, SIZEOF);
    return XGravityEvent.<Buffer>wrap(Buffer.class, MemoryUtil.memAddress(container), capacity, container);
  }
  
  public static Buffer create(long address, int capacity) {
    return XGravityEvent.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  @Nullable
  public static Buffer createSafe(long address, int capacity) {
    return (address == 0L) ? null : XGravityEvent.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  public static XGravityEvent mallocStack() {
    return mallocStack(MemoryStack.stackGet());
  }
  
  public static XGravityEvent callocStack() {
    return callocStack(MemoryStack.stackGet());
  }
  
  public static XGravityEvent mallocStack(MemoryStack stack) {
    return XGravityEvent.<XGravityEvent>wrap(XGravityEvent.class, stack.nmalloc(ALIGNOF, SIZEOF));
  }
  
  public static XGravityEvent callocStack(MemoryStack stack) {
    return XGravityEvent.<XGravityEvent>wrap(XGravityEvent.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
  }
  
  public static Buffer mallocStack(int capacity) {
    return mallocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer callocStack(int capacity) {
    return callocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer mallocStack(int capacity, MemoryStack stack) {
    return XGravityEvent.<Buffer>wrap(Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
  }
  
  public static Buffer callocStack(int capacity, MemoryStack stack) {
    return XGravityEvent.<Buffer>wrap(Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
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
  
  public static int nx(long struct) {
    return UNSAFE.getInt(null, struct + (long)X);
  }
  
  public static int ny(long struct) {
    return UNSAFE.getInt(null, struct + (long)Y);
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
  
  public static void nx(long struct, int value) {
    UNSAFE.putInt(null, struct + (long)X, value);
  }
  
  public static void ny(long struct, int value) {
    UNSAFE.putInt(null, struct + (long)Y, value);
  }
  
  public static void validate(long struct) {
    Checks.check(MemoryUtil.memGetAddress(struct + (long)DISPLAY));
  }
  
  public static void validate(long array, int count) {
    for (int i = 0; i < count; i++)
      validate(array + Integer.toUnsignedLong(i) * (long)SIZEOF); 
  }
  
  public static class Buffer extends StructBuffer<XGravityEvent, Buffer> implements NativeResource {
    private static final XGravityEvent ELEMENT_FACTORY = XGravityEvent.create(-1L);
    
    public Buffer(ByteBuffer container) {
      super(container, container.remaining() / XGravityEvent.SIZEOF);
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
    
    protected XGravityEvent getElementFactory() {
      return ELEMENT_FACTORY;
    }
    
    public int type() {
      return XGravityEvent.ntype(address());
    }
    
    @NativeType("unsigned long")
    public long serial() {
      return XGravityEvent.nserial(address());
    }
    
    @NativeType("Bool")
    public boolean send_event() {
      return (XGravityEvent.nsend_event(address()) != 0);
    }
    
    @NativeType("Display *")
    public long display() {
      return XGravityEvent.ndisplay(address());
    }
    
    @NativeType("Window")
    public long event() {
      return XGravityEvent.nevent(address());
    }
    
    @NativeType("Window")
    public long window() {
      return XGravityEvent.nwindow(address());
    }
    
    public int x() {
      return XGravityEvent.nx(address());
    }
    
    public int y() {
      return XGravityEvent.ny(address());
    }
    
    public Buffer type(int value) {
      XGravityEvent.ntype(address(), value);
      return this;
    }
    
    public Buffer serial(@NativeType("unsigned long") long value) {
      XGravityEvent.nserial(address(), value);
      return this;
    }
    
    public Buffer send_event(@NativeType("Bool") boolean value) {
      XGravityEvent.nsend_event(address(), value ? 1 : 0);
      return this;
    }
    
    public Buffer display(@NativeType("Display *") long value) {
      XGravityEvent.ndisplay(address(), value);
      return this;
    }
    
    public Buffer event(@NativeType("Window") long value) {
      XGravityEvent.nevent(address(), value);
      return this;
    }
    
    public Buffer window(@NativeType("Window") long value) {
      XGravityEvent.nwindow(address(), value);
      return this;
    }
    
    public Buffer x(int value) {
      XGravityEvent.nx(address(), value);
      return this;
    }
    
    public Buffer y(int value) {
      XGravityEvent.ny(address(), value);
      return this;
    }
  }
}
