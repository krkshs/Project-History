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

public class XKeyEvent extends Struct implements NativeResource {
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
  
  public static final int KEYCODE;
  
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
    KEYCODE = layout.offsetof(13);
    SAME_SCREEN = layout.offsetof(14);
  }
  
  public XKeyEvent(ByteBuffer container) {
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
  public int keycode() {
    return nkeycode(address());
  }
  
  @NativeType("Bool")
  public boolean same_screen() {
    return (nsame_screen(address()) != 0);
  }
  
  public XKeyEvent type(int value) {
    ntype(address(), value);
    return this;
  }
  
  public XKeyEvent serial(@NativeType("unsigned long") long value) {
    nserial(address(), value);
    return this;
  }
  
  public XKeyEvent send_event(@NativeType("Bool") boolean value) {
    nsend_event(address(), value ? 1 : 0);
    return this;
  }
  
  public XKeyEvent display(@NativeType("Display *") long value) {
    ndisplay(address(), value);
    return this;
  }
  
  public XKeyEvent window(@NativeType("Window") long value) {
    nwindow(address(), value);
    return this;
  }
  
  public XKeyEvent root(@NativeType("Window") long value) {
    nroot(address(), value);
    return this;
  }
  
  public XKeyEvent subwindow(@NativeType("Window") long value) {
    nsubwindow(address(), value);
    return this;
  }
  
  public XKeyEvent time(@NativeType("Time") long value) {
    ntime(address(), value);
    return this;
  }
  
  public XKeyEvent x(int value) {
    nx(address(), value);
    return this;
  }
  
  public XKeyEvent y(int value) {
    ny(address(), value);
    return this;
  }
  
  public XKeyEvent x_root(int value) {
    nx_root(address(), value);
    return this;
  }
  
  public XKeyEvent y_root(int value) {
    ny_root(address(), value);
    return this;
  }
  
  public XKeyEvent state(@NativeType("unsigned int") int value) {
    nstate(address(), value);
    return this;
  }
  
  public XKeyEvent keycode(@NativeType("unsigned int") int value) {
    nkeycode(address(), value);
    return this;
  }
  
  public XKeyEvent same_screen(@NativeType("Bool") boolean value) {
    nsame_screen(address(), value ? 1 : 0);
    return this;
  }
  
  public XKeyEvent set(int type, long serial, boolean send_event, long display, long window, long root, long subwindow, long time, int x, int y, int x_root, int y_root, int state, int keycode, boolean same_screen) {
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
    keycode(keycode);
    same_screen(same_screen);
    return this;
  }
  
  public XKeyEvent set(XKeyEvent src) {
    MemoryUtil.memCopy(src.address(), address(), (long)SIZEOF);
    return this;
  }
  
  public static XKeyEvent malloc() {
    return XKeyEvent.<XKeyEvent>wrap(XKeyEvent.class, MemoryUtil.nmemAllocChecked((long)SIZEOF));
  }
  
  public static XKeyEvent calloc() {
    return XKeyEvent.<XKeyEvent>wrap(XKeyEvent.class, MemoryUtil.nmemCallocChecked(1L, (long)SIZEOF));
  }
  
  public static XKeyEvent create() {
    ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
    return XKeyEvent.<XKeyEvent>wrap(XKeyEvent.class, MemoryUtil.memAddress(container), container);
  }
  
  public static XKeyEvent create(long address) {
    return XKeyEvent.<XKeyEvent>wrap(XKeyEvent.class, address);
  }
  
  @Nullable
  public static XKeyEvent createSafe(long address) {
    return (address == 0L) ? null : XKeyEvent.<XKeyEvent>wrap(XKeyEvent.class, address);
  }
  
  public static Buffer malloc(int capacity) {
    return XKeyEvent.<Buffer>wrap(Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
  }
  
  public static Buffer calloc(int capacity) {
    return XKeyEvent.<Buffer>wrap(Buffer.class, MemoryUtil.nmemCallocChecked((long)capacity, (long)SIZEOF), capacity);
  }
  
  public static Buffer create(int capacity) {
    ByteBuffer container = __create(capacity, SIZEOF);
    return XKeyEvent.<Buffer>wrap(Buffer.class, MemoryUtil.memAddress(container), capacity, container);
  }
  
  public static Buffer create(long address, int capacity) {
    return XKeyEvent.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  @Nullable
  public static Buffer createSafe(long address, int capacity) {
    return (address == 0L) ? null : XKeyEvent.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  public static XKeyEvent mallocStack() {
    return mallocStack(MemoryStack.stackGet());
  }
  
  public static XKeyEvent callocStack() {
    return callocStack(MemoryStack.stackGet());
  }
  
  public static XKeyEvent mallocStack(MemoryStack stack) {
    return XKeyEvent.<XKeyEvent>wrap(XKeyEvent.class, stack.nmalloc(ALIGNOF, SIZEOF));
  }
  
  public static XKeyEvent callocStack(MemoryStack stack) {
    return XKeyEvent.<XKeyEvent>wrap(XKeyEvent.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
  }
  
  public static Buffer mallocStack(int capacity) {
    return mallocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer callocStack(int capacity) {
    return callocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer mallocStack(int capacity, MemoryStack stack) {
    return XKeyEvent.<Buffer>wrap(Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
  }
  
  public static Buffer callocStack(int capacity, MemoryStack stack) {
    return XKeyEvent.<Buffer>wrap(Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
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
  
  public static int nkeycode(long struct) {
    return UNSAFE.getInt(null, struct + (long)KEYCODE);
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
  
  public static void nkeycode(long struct, int value) {
    UNSAFE.putInt(null, struct + (long)KEYCODE, value);
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
  
  public static class Buffer extends StructBuffer<XKeyEvent, Buffer> implements NativeResource {
    private static final XKeyEvent ELEMENT_FACTORY = XKeyEvent.create(-1L);
    
    public Buffer(ByteBuffer container) {
      super(container, container.remaining() / XKeyEvent.SIZEOF);
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
    
    protected XKeyEvent getElementFactory() {
      return ELEMENT_FACTORY;
    }
    
    public int type() {
      return XKeyEvent.ntype(address());
    }
    
    @NativeType("unsigned long")
    public long serial() {
      return XKeyEvent.nserial(address());
    }
    
    @NativeType("Bool")
    public boolean send_event() {
      return (XKeyEvent.nsend_event(address()) != 0);
    }
    
    @NativeType("Display *")
    public long display() {
      return XKeyEvent.ndisplay(address());
    }
    
    @NativeType("Window")
    public long window() {
      return XKeyEvent.nwindow(address());
    }
    
    @NativeType("Window")
    public long root() {
      return XKeyEvent.nroot(address());
    }
    
    @NativeType("Window")
    public long subwindow() {
      return XKeyEvent.nsubwindow(address());
    }
    
    @NativeType("Time")
    public long time() {
      return XKeyEvent.ntime(address());
    }
    
    public int x() {
      return XKeyEvent.nx(address());
    }
    
    public int y() {
      return XKeyEvent.ny(address());
    }
    
    public int x_root() {
      return XKeyEvent.nx_root(address());
    }
    
    public int y_root() {
      return XKeyEvent.ny_root(address());
    }
    
    @NativeType("unsigned int")
    public int state() {
      return XKeyEvent.nstate(address());
    }
    
    @NativeType("unsigned int")
    public int keycode() {
      return XKeyEvent.nkeycode(address());
    }
    
    @NativeType("Bool")
    public boolean same_screen() {
      return (XKeyEvent.nsame_screen(address()) != 0);
    }
    
    public Buffer type(int value) {
      XKeyEvent.ntype(address(), value);
      return this;
    }
    
    public Buffer serial(@NativeType("unsigned long") long value) {
      XKeyEvent.nserial(address(), value);
      return this;
    }
    
    public Buffer send_event(@NativeType("Bool") boolean value) {
      XKeyEvent.nsend_event(address(), value ? 1 : 0);
      return this;
    }
    
    public Buffer display(@NativeType("Display *") long value) {
      XKeyEvent.ndisplay(address(), value);
      return this;
    }
    
    public Buffer window(@NativeType("Window") long value) {
      XKeyEvent.nwindow(address(), value);
      return this;
    }
    
    public Buffer root(@NativeType("Window") long value) {
      XKeyEvent.nroot(address(), value);
      return this;
    }
    
    public Buffer subwindow(@NativeType("Window") long value) {
      XKeyEvent.nsubwindow(address(), value);
      return this;
    }
    
    public Buffer time(@NativeType("Time") long value) {
      XKeyEvent.ntime(address(), value);
      return this;
    }
    
    public Buffer x(int value) {
      XKeyEvent.nx(address(), value);
      return this;
    }
    
    public Buffer y(int value) {
      XKeyEvent.ny(address(), value);
      return this;
    }
    
    public Buffer x_root(int value) {
      XKeyEvent.nx_root(address(), value);
      return this;
    }
    
    public Buffer y_root(int value) {
      XKeyEvent.ny_root(address(), value);
      return this;
    }
    
    public Buffer state(@NativeType("unsigned int") int value) {
      XKeyEvent.nstate(address(), value);
      return this;
    }
    
    public Buffer keycode(@NativeType("unsigned int") int value) {
      XKeyEvent.nkeycode(address(), value);
      return this;
    }
    
    public Buffer same_screen(@NativeType("Bool") boolean value) {
      XKeyEvent.nsame_screen(address(), value ? 1 : 0);
      return this;
    }
  }
}
