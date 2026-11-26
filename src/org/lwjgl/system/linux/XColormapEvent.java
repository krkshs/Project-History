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

public class XColormapEvent extends Struct implements NativeResource {
  public static final int SIZEOF;
  
  public static final int ALIGNOF;
  
  public static final int TYPE;
  
  public static final int SERIAL;
  
  public static final int SEND_EVENT;
  
  public static final int DISPLAY;
  
  public static final int WINDOW;
  
  public static final int COLORMAP;
  
  public static final int NEW;
  
  public static final int STATE;
  
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
    WINDOW = layout.offsetof(4);
    COLORMAP = layout.offsetof(5);
    NEW = layout.offsetof(6);
    STATE = layout.offsetof(7);
  }
  
  public XColormapEvent(ByteBuffer container) {
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
  
  @NativeType("Colormap")
  public long colormap() {
    return ncolormap(address());
  }
  
  public int new$() {
    return nnew$(address());
  }
  
  public int state() {
    return nstate(address());
  }
  
  public XColormapEvent type(int value) {
    ntype(address(), value);
    return this;
  }
  
  public XColormapEvent serial(@NativeType("unsigned long") long value) {
    nserial(address(), value);
    return this;
  }
  
  public XColormapEvent send_event(@NativeType("Bool") boolean value) {
    nsend_event(address(), value ? 1 : 0);
    return this;
  }
  
  public XColormapEvent display(@NativeType("Display *") long value) {
    ndisplay(address(), value);
    return this;
  }
  
  public XColormapEvent window(@NativeType("Window") long value) {
    nwindow(address(), value);
    return this;
  }
  
  public XColormapEvent colormap(@NativeType("Colormap") long value) {
    ncolormap(address(), value);
    return this;
  }
  
  public XColormapEvent new$(int value) {
    nnew$(address(), value);
    return this;
  }
  
  public XColormapEvent state(int value) {
    nstate(address(), value);
    return this;
  }
  
  public XColormapEvent set(int type, long serial, boolean send_event, long display, long window, long colormap, int new$, int state) {
    type(type);
    serial(serial);
    send_event(send_event);
    display(display);
    window(window);
    colormap(colormap);
    new$(new$);
    state(state);
    return this;
  }
  
  public XColormapEvent set(XColormapEvent src) {
    MemoryUtil.memCopy(src.address(), address(), (long)SIZEOF);
    return this;
  }
  
  public static XColormapEvent malloc() {
    return XColormapEvent.<XColormapEvent>wrap(XColormapEvent.class, MemoryUtil.nmemAllocChecked((long)SIZEOF));
  }
  
  public static XColormapEvent calloc() {
    return XColormapEvent.<XColormapEvent>wrap(XColormapEvent.class, MemoryUtil.nmemCallocChecked(1L, (long)SIZEOF));
  }
  
  public static XColormapEvent create() {
    ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
    return XColormapEvent.<XColormapEvent>wrap(XColormapEvent.class, MemoryUtil.memAddress(container), container);
  }
  
  public static XColormapEvent create(long address) {
    return XColormapEvent.<XColormapEvent>wrap(XColormapEvent.class, address);
  }
  
  @Nullable
  public static XColormapEvent createSafe(long address) {
    return (address == 0L) ? null : XColormapEvent.<XColormapEvent>wrap(XColormapEvent.class, address);
  }
  
  public static Buffer malloc(int capacity) {
    return XColormapEvent.<Buffer>wrap(Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
  }
  
  public static Buffer calloc(int capacity) {
    return XColormapEvent.<Buffer>wrap(Buffer.class, MemoryUtil.nmemCallocChecked((long)capacity, (long)SIZEOF), capacity);
  }
  
  public static Buffer create(int capacity) {
    ByteBuffer container = __create(capacity, SIZEOF);
    return XColormapEvent.<Buffer>wrap(Buffer.class, MemoryUtil.memAddress(container), capacity, container);
  }
  
  public static Buffer create(long address, int capacity) {
    return XColormapEvent.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  @Nullable
  public static Buffer createSafe(long address, int capacity) {
    return (address == 0L) ? null : XColormapEvent.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  public static XColormapEvent mallocStack() {
    return mallocStack(MemoryStack.stackGet());
  }
  
  public static XColormapEvent callocStack() {
    return callocStack(MemoryStack.stackGet());
  }
  
  public static XColormapEvent mallocStack(MemoryStack stack) {
    return XColormapEvent.<XColormapEvent>wrap(XColormapEvent.class, stack.nmalloc(ALIGNOF, SIZEOF));
  }
  
  public static XColormapEvent callocStack(MemoryStack stack) {
    return XColormapEvent.<XColormapEvent>wrap(XColormapEvent.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
  }
  
  public static Buffer mallocStack(int capacity) {
    return mallocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer callocStack(int capacity) {
    return callocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer mallocStack(int capacity, MemoryStack stack) {
    return XColormapEvent.<Buffer>wrap(Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
  }
  
  public static Buffer callocStack(int capacity, MemoryStack stack) {
    return XColormapEvent.<Buffer>wrap(Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
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
  
  public static long ncolormap(long struct) {
    return MemoryUtil.memGetCLong(struct + (long)COLORMAP);
  }
  
  public static int nnew$(long struct) {
    return UNSAFE.getInt(null, struct + (long)NEW);
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
  
  public static void ncolormap(long struct, long value) {
    MemoryUtil.memPutCLong(struct + (long)COLORMAP, value);
  }
  
  public static void nnew$(long struct, int value) {
    UNSAFE.putInt(null, struct + (long)NEW, value);
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
  
  public static class Buffer extends StructBuffer<XColormapEvent, Buffer> implements NativeResource {
    private static final XColormapEvent ELEMENT_FACTORY = XColormapEvent.create(-1L);
    
    public Buffer(ByteBuffer container) {
      super(container, container.remaining() / XColormapEvent.SIZEOF);
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
    
    protected XColormapEvent getElementFactory() {
      return ELEMENT_FACTORY;
    }
    
    public int type() {
      return XColormapEvent.ntype(address());
    }
    
    @NativeType("unsigned long")
    public long serial() {
      return XColormapEvent.nserial(address());
    }
    
    @NativeType("Bool")
    public boolean send_event() {
      return (XColormapEvent.nsend_event(address()) != 0);
    }
    
    @NativeType("Display *")
    public long display() {
      return XColormapEvent.ndisplay(address());
    }
    
    @NativeType("Window")
    public long window() {
      return XColormapEvent.nwindow(address());
    }
    
    @NativeType("Colormap")
    public long colormap() {
      return XColormapEvent.ncolormap(address());
    }
    
    public int new$() {
      return XColormapEvent.nnew$(address());
    }
    
    public int state() {
      return XColormapEvent.nstate(address());
    }
    
    public Buffer type(int value) {
      XColormapEvent.ntype(address(), value);
      return this;
    }
    
    public Buffer serial(@NativeType("unsigned long") long value) {
      XColormapEvent.nserial(address(), value);
      return this;
    }
    
    public Buffer send_event(@NativeType("Bool") boolean value) {
      XColormapEvent.nsend_event(address(), value ? 1 : 0);
      return this;
    }
    
    public Buffer display(@NativeType("Display *") long value) {
      XColormapEvent.ndisplay(address(), value);
      return this;
    }
    
    public Buffer window(@NativeType("Window") long value) {
      XColormapEvent.nwindow(address(), value);
      return this;
    }
    
    public Buffer colormap(@NativeType("Colormap") long value) {
      XColormapEvent.ncolormap(address(), value);
      return this;
    }
    
    public Buffer new$(int value) {
      XColormapEvent.nnew$(address(), value);
      return this;
    }
    
    public Buffer state(int value) {
      XColormapEvent.nstate(address(), value);
      return this;
    }
  }
}
