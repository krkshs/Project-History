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

public class XButtonEvent extends Struct implements NativeResource {
  public static final int SIZEOF;
  
  public static final int ALIGNOF;
  
  public static final int TYPE;
  
  public static final int SERIAL;
  
  public static final int SEND_EVENT;
  
  public static final int DISPLAY;
  
  public static final int WINDOW;
  
  public static final int ROOT;
  
  public static final int SUBWINDOW;
  
  public static final int TIME;
  
  public static final int X;
  
  public static final int Y;
  
  public static final int X_ROOT;
  
  public static final int Y_ROOT;
  
  public static final int STATE;
  
  public static final int BUTTON;
  
  public static final int SAME_SCREEN;
  
  static {
    Struct.Layout layout = __struct(new Struct.Member[] { 
          __member(4), 
          __member(CLONG_SIZE), 
          __member(4), 
          __member(POINTER_SIZE), 
          __member(CLONG_SIZE), 
          __member(CLONG_SIZE), 
          __member(CLONG_SIZE), 
          __member(CLONG_SIZE), 
          __member(4), 
          __member(4), 
          __member(4), 
          __member(4), 
          __member(4), 
          __member(4), 
          __member(4) });
    SIZEOF = layout.getSize();
    ALIGNOF = layout.getAlignment();
    TYPE = layout.offsetof(0);
    SERIAL = layout.offsetof(1);
    SEND_EVENT = layout.offsetof(2);
    DISPLAY = layout.offsetof(3);
    WINDOW = layout.offsetof(4);
    ROOT = layout.offsetof(5);
    SUBWINDOW = layout.offsetof(6);
    TIME = layout.offsetof(7);
    X = layout.offsetof(8);
    Y = layout.offsetof(9);
    X_ROOT = layout.offsetof(10);
    Y_ROOT = layout.offsetof(11);
    STATE = layout.offsetof(12);
    BUTTON = layout.offsetof(13);
    SAME_SCREEN = layout.offsetof(14);
  }
  
  public XButtonEvent(ByteBuffer container) {
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
  
  @NativeType("Window")
  public long root() {
    return nroot(address());
  }
  
  @NativeType("Window")
  public long subwindow() {
    return nsubwindow(address());
  }
  
  @NativeType("Time")
  public long time() {
    return ntime(address());
  }
  
  public int x() {
    return nx(address());
  }
  
  public int y() {
    return ny(address());
  }
  
  public int x_root() {
    return nx_root(address());
  }
  
  public int y_root() {
    return ny_root(address());
  }
  
  @NativeType("unsigned int")
  public int state() {
    return nstate(address());
  }
  
  @NativeType("unsigned int")
  public int button() {
    return nbutton(address());
  }
  
  @NativeType("Bool")
  public boolean same_screen() {
    return (nsame_screen(address()) != 0);
  }
  
  public XButtonEvent type(int value) {
    ntype(address(), value);
    return this;
  }
  
  public XButtonEvent serial(@NativeType("unsigned long") long value) {
    nserial(address(), value);
    return this;
  }
  
  public XButtonEvent send_event(@NativeType("Bool") boolean value) {
    nsend_event(address(), value ? 1 : 0);
    return this;
  }
  
  public XButtonEvent display(@NativeType("Display *") long value) {
    ndisplay(address(), value);
    return this;
  }
  
  public XButtonEvent window(@NativeType("Window") long value) {
    nwindow(address(), value);
    return this;
  }
  
  public XButtonEvent root(@NativeType("Window") long value) {
    nroot(address(), value);
    return this;
  }
  
  public XButtonEvent subwindow(@NativeType("Window") long value) {
    nsubwindow(address(), value);
    return this;
  }
  
  public XButtonEvent time(@NativeType("Time") long value) {
    ntime(address(), value);
    return this;
  }
  
  public XButtonEvent x(int value) {
    nx(address(), value);
    return this;
  }
  
  public XButtonEvent y(int value) {
    ny(address(), value);
    return this;
  }
  
  public XButtonEvent x_root(int value) {
    nx_root(address(), value);
    return this;
  }
  
  public XButtonEvent y_root(int value) {
    ny_root(address(), value);
    return this;
  }
  
  public XButtonEvent state(@NativeType("unsigned int") int value) {
    nstate(address(), value);
    return this;
  }
  
  public XButtonEvent button(@NativeType("unsigned int") int value) {
    nbutton(address(), value);
    return this;
  }
  
  public XButtonEvent same_screen(@NativeType("Bool") boolean value) {
    nsame_screen(address(), value ? 1 : 0);
    return this;
  }
  
  public XButtonEvent set(int type, long serial, boolean send_event, long display, long window, long root, long subwindow, long time, int x, int y, int x_root, int y_root, int state, int button, boolean same_screen) {
    type(type);
    serial(serial);
    send_event(send_event);
    display(display);
    window(window);
    root(root);
    subwindow(subwindow);
    time(time);
    x(x);
    y(y);
    x_root(x_root);
    y_root(y_root);
    state(state);
    button(button);
    same_screen(same_screen);
    return this;
  }
  
  public XButtonEvent set(XButtonEvent src) {
    MemoryUtil.memCopy(src.address(), address(), (long)SIZEOF);
    return this;
  }
  
  public static XButtonEvent malloc() {
    return XButtonEvent.<XButtonEvent>wrap(XButtonEvent.class, MemoryUtil.nmemAllocChecked((long)SIZEOF));
  }
  
  public static XButtonEvent calloc() {
    return XButtonEvent.<XButtonEvent>wrap(XButtonEvent.class, MemoryUtil.nmemCallocChecked(1L, (long)SIZEOF));
  }
  
  public static XButtonEvent create() {
    ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
    return XButtonEvent.<XButtonEvent>wrap(XButtonEvent.class, MemoryUtil.memAddress(container), container);
  }
  
  public static XButtonEvent create(long address) {
    return XButtonEvent.<XButtonEvent>wrap(XButtonEvent.class, address);
  }
  
  @Nullable
  public static XButtonEvent createSafe(long address) {
    return (address == 0L) ? null : XButtonEvent.<XButtonEvent>wrap(XButtonEvent.class, address);
  }
  
  public static Buffer malloc(int capacity) {
    return XButtonEvent.<Buffer>wrap(Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
  }
  
  public static Buffer calloc(int capacity) {
    return XButtonEvent.<Buffer>wrap(Buffer.class, MemoryUtil.nmemCallocChecked((long)capacity, (long)SIZEOF), capacity);
  }
  
  public static Buffer create(int capacity) {
    ByteBuffer container = __create(capacity, SIZEOF);
    return XButtonEvent.<Buffer>wrap(Buffer.class, MemoryUtil.memAddress(container), capacity, container);
  }
  
  public static Buffer create(long address, int capacity) {
    return XButtonEvent.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  @Nullable
  public static Buffer createSafe(long address, int capacity) {
    return (address == 0L) ? null : XButtonEvent.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  public static XButtonEvent mallocStack() {
    return mallocStack(MemoryStack.stackGet());
  }
  
  public static XButtonEvent callocStack() {
    return callocStack(MemoryStack.stackGet());
  }
  
  public static XButtonEvent mallocStack(MemoryStack stack) {
    return XButtonEvent.<XButtonEvent>wrap(XButtonEvent.class, stack.nmalloc(ALIGNOF, SIZEOF));
  }
  
  public static XButtonEvent callocStack(MemoryStack stack) {
    return XButtonEvent.<XButtonEvent>wrap(XButtonEvent.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
  }
  
  public static Buffer mallocStack(int capacity) {
    return mallocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer callocStack(int capacity) {
    return callocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer mallocStack(int capacity, MemoryStack stack) {
    return XButtonEvent.<Buffer>wrap(Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
  }
  
  public static Buffer callocStack(int capacity, MemoryStack stack) {
    return XButtonEvent.<Buffer>wrap(Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
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
  
  public static long nroot(long struct) {
    return MemoryUtil.memGetCLong(struct + (long)ROOT);
  }
  
  public static long nsubwindow(long struct) {
    return MemoryUtil.memGetCLong(struct + (long)SUBWINDOW);
  }
  
  public static long ntime(long struct) {
    return MemoryUtil.memGetCLong(struct + (long)TIME);
  }
  
  public static int nx(long struct) {
    return UNSAFE.getInt(null, struct + (long)X);
  }
  
  public static int ny(long struct) {
    return UNSAFE.getInt(null, struct + (long)Y);
  }
  
  public static int nx_root(long struct) {
    return UNSAFE.getInt(null, struct + (long)X_ROOT);
  }
  
  public static int ny_root(long struct) {
    return UNSAFE.getInt(null, struct + (long)Y_ROOT);
  }
  
  public static int nstate(long struct) {
    return UNSAFE.getInt(null, struct + (long)STATE);
  }
  
  public static int nbutton(long struct) {
    return UNSAFE.getInt(null, struct + (long)BUTTON);
  }
  
  public static int nsame_screen(long struct) {
    return UNSAFE.getInt(null, struct + (long)SAME_SCREEN);
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
  
  public static void nroot(long struct, long value) {
    MemoryUtil.memPutCLong(struct + (long)ROOT, value);
  }
  
  public static void nsubwindow(long struct, long value) {
    MemoryUtil.memPutCLong(struct + (long)SUBWINDOW, value);
  }
  
  public static void ntime(long struct, long value) {
    MemoryUtil.memPutCLong(struct + (long)TIME, value);
  }
  
  public static void nx(long struct, int value) {
    UNSAFE.putInt(null, struct + (long)X, value);
  }
  
  public static void ny(long struct, int value) {
    UNSAFE.putInt(null, struct + (long)Y, value);
  }
  
  public static void nx_root(long struct, int value) {
    UNSAFE.putInt(null, struct + (long)X_ROOT, value);
  }
  
  public static void ny_root(long struct, int value) {
    UNSAFE.putInt(null, struct + (long)Y_ROOT, value);
  }
  
  public static void nstate(long struct, int value) {
    UNSAFE.putInt(null, struct + (long)STATE, value);
  }
  
  public static void nbutton(long struct, int value) {
    UNSAFE.putInt(null, struct + (long)BUTTON, value);
  }
  
  public static void nsame_screen(long struct, int value) {
    UNSAFE.putInt(null, struct + (long)SAME_SCREEN, value);
  }
  
  public static void validate(long struct) {
    Checks.check(MemoryUtil.memGetAddress(struct + (long)DISPLAY));
  }
  
  public static void validate(long array, int count) {
    for (int i = 0; i < count; i++)
      validate(array + Integer.toUnsignedLong(i) * (long)SIZEOF); 
  }
  
  public static class Buffer extends StructBuffer<XButtonEvent, Buffer> implements NativeResource {
    private static final XButtonEvent ELEMENT_FACTORY = XButtonEvent.create(-1L);
    
    public Buffer(ByteBuffer container) {
      super(container, container.remaining() / XButtonEvent.SIZEOF);
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
    
    protected XButtonEvent getElementFactory() {
      return ELEMENT_FACTORY;
    }
    
    public int type() {
      return XButtonEvent.ntype(address());
    }
    
    @NativeType("unsigned long")
    public long serial() {
      return XButtonEvent.nserial(address());
    }
    
    @NativeType("Bool")
    public boolean send_event() {
      return (XButtonEvent.nsend_event(address()) != 0);
    }
    
    @NativeType("Display *")
    public long display() {
      return XButtonEvent.ndisplay(address());
    }
    
    @NativeType("Window")
    public long window() {
      return XButtonEvent.nwindow(address());
    }
    
    @NativeType("Window")
    public long root() {
      return XButtonEvent.nroot(address());
    }
    
    @NativeType("Window")
    public long subwindow() {
      return XButtonEvent.nsubwindow(address());
    }
    
    @NativeType("Time")
    public long time() {
      return XButtonEvent.ntime(address());
    }
    
    public int x() {
      return XButtonEvent.nx(address());
    }
    
    public int y() {
      return XButtonEvent.ny(address());
    }
    
    public int x_root() {
      return XButtonEvent.nx_root(address());
    }
    
    public int y_root() {
      return XButtonEvent.ny_root(address());
    }
    
    @NativeType("unsigned int")
    public int state() {
      return XButtonEvent.nstate(address());
    }
    
    @NativeType("unsigned int")
    public int button() {
      return XButtonEvent.nbutton(address());
    }
    
    @NativeType("Bool")
    public boolean same_screen() {
      return (XButtonEvent.nsame_screen(address()) != 0);
    }
    
    public Buffer type(int value) {
      XButtonEvent.ntype(address(), value);
      return this;
    }
    
    public Buffer serial(@NativeType("unsigned long") long value) {
      XButtonEvent.nserial(address(), value);
      return this;
    }
    
    public Buffer send_event(@NativeType("Bool") boolean value) {
      XButtonEvent.nsend_event(address(), value ? 1 : 0);
      return this;
    }
    
    public Buffer display(@NativeType("Display *") long value) {
      XButtonEvent.ndisplay(address(), value);
      return this;
    }
    
    public Buffer window(@NativeType("Window") long value) {
      XButtonEvent.nwindow(address(), value);
      return this;
    }
    
    public Buffer root(@NativeType("Window") long value) {
      XButtonEvent.nroot(address(), value);
      return this;
    }
    
    public Buffer subwindow(@NativeType("Window") long value) {
      XButtonEvent.nsubwindow(address(), value);
      return this;
    }
    
    public Buffer time(@NativeType("Time") long value) {
      XButtonEvent.ntime(address(), value);
      return this;
    }
    
    public Buffer x(int value) {
      XButtonEvent.nx(address(), value);
      return this;
    }
    
    public Buffer y(int value) {
      XButtonEvent.ny(address(), value);
      return this;
    }
    
    public Buffer x_root(int value) {
      XButtonEvent.nx_root(address(), value);
      return this;
    }
    
    public Buffer y_root(int value) {
      XButtonEvent.ny_root(address(), value);
      return this;
    }
    
    public Buffer state(@NativeType("unsigned int") int value) {
      XButtonEvent.nstate(address(), value);
      return this;
    }
    
    public Buffer button(@NativeType("unsigned int") int value) {
      XButtonEvent.nbutton(address(), value);
      return this;
    }
    
    public Buffer same_screen(@NativeType("Bool") boolean value) {
      XButtonEvent.nsame_screen(address(), value ? 1 : 0);
      return this;
    }
  }
}
