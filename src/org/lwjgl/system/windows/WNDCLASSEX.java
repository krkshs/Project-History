package org.lwjgl.system.windows;

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

public class WNDCLASSEX extends Struct implements NativeResource {
  public static final int SIZEOF;
  
  public static final int ALIGNOF;
  
  public static final int CBSIZE;
  
  public static final int STYLE;
  
  public static final int LPFNWNDPROC;
  
  public static final int CBCLSEXTRA;
  
  public static final int CBWNDEXTRA;
  
  public static final int HINSTANCE;
  
  public static final int HICON;
  
  public static final int HCURSOR;
  
  public static final int HBRBACKGROUND;
  
  public static final int LPSZMENUNAME;
  
  public static final int LPSZCLASSNAME;
  
  public static final int HICONSM;
  
  static {
    Struct.Layout layout = __struct(
        __member(4), 
        __member(4), 
        __member(POINTER_SIZE), 
        __member(4), 
        __member(4), 
        __member(POINTER_SIZE), 
        __member(POINTER_SIZE), 
        __member(POINTER_SIZE), 
        __member(POINTER_SIZE), 
        __member(POINTER_SIZE), 
        __member(POINTER_SIZE), 
        __member(POINTER_SIZE));
    SIZEOF = layout.getSize();
    ALIGNOF = layout.getAlignment();
    CBSIZE = layout.offsetof(0);
    STYLE = layout.offsetof(1);
    LPFNWNDPROC = layout.offsetof(2);
    CBCLSEXTRA = layout.offsetof(3);
    CBWNDEXTRA = layout.offsetof(4);
    HINSTANCE = layout.offsetof(5);
    HICON = layout.offsetof(6);
    HCURSOR = layout.offsetof(7);
    HBRBACKGROUND = layout.offsetof(8);
    LPSZMENUNAME = layout.offsetof(9);
    LPSZCLASSNAME = layout.offsetof(10);
    HICONSM = layout.offsetof(11);
  }
  
  public WNDCLASSEX(ByteBuffer container) {
    super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
  }
  
  public int sizeof() {
    return SIZEOF;
  }
  
  @NativeType("UINT")
  public int cbSize() {
    return ncbSize(address());
  }
  
  @NativeType("UINT")
  public int style() {
    return nstyle(address());
  }
  
  @NativeType("WNDPROC")
  public WindowProc lpfnWndProc() {
    return nlpfnWndProc(address());
  }
  
  public int cbClsExtra() {
    return ncbClsExtra(address());
  }
  
  public int cbWndExtra() {
    return ncbWndExtra(address());
  }
  
  @NativeType("HINSTANCE")
  public long hInstance() {
    return nhInstance(address());
  }
  
  @NativeType("HICON")
  public long hIcon() {
    return nhIcon(address());
  }
  
  @NativeType("HCURSOR")
  public long hCursor() {
    return nhCursor(address());
  }
  
  @NativeType("HBRUSH")
  public long hbrBackground() {
    return nhbrBackground(address());
  }
  
  @Nullable
  @NativeType("LPCTSTR")
  public ByteBuffer lpszMenuName() {
    return nlpszMenuName(address());
  }
  
  @Nullable
  @NativeType("LPCTSTR")
  public String lpszMenuNameString() {
    return nlpszMenuNameString(address());
  }
  
  @NativeType("LPCTSTR")
  public ByteBuffer lpszClassName() {
    return nlpszClassName(address());
  }
  
  @NativeType("LPCTSTR")
  public String lpszClassNameString() {
    return nlpszClassNameString(address());
  }
  
  @NativeType("HICON")
  public long hIconSm() {
    return nhIconSm(address());
  }
  
  public WNDCLASSEX cbSize(@NativeType("UINT") int value) {
    ncbSize(address(), value);
    return this;
  }
  
  public WNDCLASSEX style(@NativeType("UINT") int value) {
    nstyle(address(), value);
    return this;
  }
  
  public WNDCLASSEX lpfnWndProc(@NativeType("WNDPROC") WindowProcI value) {
    nlpfnWndProc(address(), value);
    return this;
  }
  
  public WNDCLASSEX cbClsExtra(int value) {
    ncbClsExtra(address(), value);
    return this;
  }
  
  public WNDCLASSEX cbWndExtra(int value) {
    ncbWndExtra(address(), value);
    return this;
  }
  
  public WNDCLASSEX hInstance(@NativeType("HINSTANCE") long value) {
    nhInstance(address(), value);
    return this;
  }
  
  public WNDCLASSEX hIcon(@NativeType("HICON") long value) {
    nhIcon(address(), value);
    return this;
  }
  
  public WNDCLASSEX hCursor(@NativeType("HCURSOR") long value) {
    nhCursor(address(), value);
    return this;
  }
  
  public WNDCLASSEX hbrBackground(@NativeType("HBRUSH") long value) {
    nhbrBackground(address(), value);
    return this;
  }
  
  public WNDCLASSEX lpszMenuName(@Nullable @NativeType("LPCTSTR") ByteBuffer value) {
    nlpszMenuName(address(), value);
    return this;
  }
  
  public WNDCLASSEX lpszClassName(@NativeType("LPCTSTR") ByteBuffer value) {
    nlpszClassName(address(), value);
    return this;
  }
  
  public WNDCLASSEX hIconSm(@NativeType("HICON") long value) {
    nhIconSm(address(), value);
    return this;
  }
  
  public WNDCLASSEX set(int cbSize, int style, WindowProcI lpfnWndProc, int cbClsExtra, int cbWndExtra, long hInstance, long hIcon, long hCursor, long hbrBackground, @Nullable ByteBuffer lpszMenuName, ByteBuffer lpszClassName, long hIconSm) {
    cbSize(cbSize);
    style(style);
    lpfnWndProc(lpfnWndProc);
    cbClsExtra(cbClsExtra);
    cbWndExtra(cbWndExtra);
    hInstance(hInstance);
    hIcon(hIcon);
    hCursor(hCursor);
    hbrBackground(hbrBackground);
    lpszMenuName(lpszMenuName);
    lpszClassName(lpszClassName);
    hIconSm(hIconSm);
    return this;
  }
  
  public WNDCLASSEX set(WNDCLASSEX src) {
    MemoryUtil.memCopy(src.address(), address(), (long)SIZEOF);
    return this;
  }
  
  public static WNDCLASSEX malloc() {
    return WNDCLASSEX.<WNDCLASSEX>wrap(WNDCLASSEX.class, MemoryUtil.nmemAllocChecked((long)SIZEOF));
  }
  
  public static WNDCLASSEX calloc() {
    return WNDCLASSEX.<WNDCLASSEX>wrap(WNDCLASSEX.class, MemoryUtil.nmemCallocChecked(1L, (long)SIZEOF));
  }
  
  public static WNDCLASSEX create() {
    ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
    return WNDCLASSEX.<WNDCLASSEX>wrap(WNDCLASSEX.class, MemoryUtil.memAddress(container), container);
  }
  
  public static WNDCLASSEX create(long address) {
    return WNDCLASSEX.<WNDCLASSEX>wrap(WNDCLASSEX.class, address);
  }
  
  @Nullable
  public static WNDCLASSEX createSafe(long address) {
    return (address == 0L) ? null : WNDCLASSEX.<WNDCLASSEX>wrap(WNDCLASSEX.class, address);
  }
  
  public static Buffer malloc(int capacity) {
    return WNDCLASSEX.<Buffer>wrap(Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
  }
  
  public static Buffer calloc(int capacity) {
    return WNDCLASSEX.<Buffer>wrap(Buffer.class, MemoryUtil.nmemCallocChecked((long)capacity, (long)SIZEOF), capacity);
  }
  
  public static Buffer create(int capacity) {
    ByteBuffer container = __create(capacity, SIZEOF);
    return WNDCLASSEX.<Buffer>wrap(Buffer.class, MemoryUtil.memAddress(container), capacity, container);
  }
  
  public static Buffer create(long address, int capacity) {
    return WNDCLASSEX.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  @Nullable
  public static Buffer createSafe(long address, int capacity) {
    return (address == 0L) ? null : WNDCLASSEX.<Buffer>wrap(Buffer.class, address, capacity);
  }
  
  public static WNDCLASSEX mallocStack() {
    return mallocStack(MemoryStack.stackGet());
  }
  
  public static WNDCLASSEX callocStack() {
    return callocStack(MemoryStack.stackGet());
  }
  
  public static WNDCLASSEX mallocStack(MemoryStack stack) {
    return WNDCLASSEX.<WNDCLASSEX>wrap(WNDCLASSEX.class, stack.nmalloc(ALIGNOF, SIZEOF));
  }
  
  public static WNDCLASSEX callocStack(MemoryStack stack) {
    return WNDCLASSEX.<WNDCLASSEX>wrap(WNDCLASSEX.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
  }
  
  public static Buffer mallocStack(int capacity) {
    return mallocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer callocStack(int capacity) {
    return callocStack(capacity, MemoryStack.stackGet());
  }
  
  public static Buffer mallocStack(int capacity, MemoryStack stack) {
    return WNDCLASSEX.<Buffer>wrap(Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
  }
  
  public static Buffer callocStack(int capacity, MemoryStack stack) {
    return WNDCLASSEX.<Buffer>wrap(Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
  }
  
  public static int ncbSize(long struct) {
    return UNSAFE.getInt(null, struct + (long)CBSIZE);
  }
  
  public static int nstyle(long struct) {
    return UNSAFE.getInt(null, struct + (long)STYLE);
  }
  
  public static WindowProc nlpfnWndProc(long struct) {
    return WindowProc.create(MemoryUtil.memGetAddress(struct + (long)LPFNWNDPROC));
  }
  
  public static int ncbClsExtra(long struct) {
    return UNSAFE.getInt(null, struct + (long)CBCLSEXTRA);
  }
  
  public static int ncbWndExtra(long struct) {
    return UNSAFE.getInt(null, struct + (long)CBWNDEXTRA);
  }
  
  public static long nhInstance(long struct) {
    return MemoryUtil.memGetAddress(struct + (long)HINSTANCE);
  }
  
  public static long nhIcon(long struct) {
    return MemoryUtil.memGetAddress(struct + (long)HICON);
  }
  
  public static long nhCursor(long struct) {
    return MemoryUtil.memGetAddress(struct + (long)HCURSOR);
  }
  
  public static long nhbrBackground(long struct) {
    return MemoryUtil.memGetAddress(struct + (long)HBRBACKGROUND);
  }
  
  @Nullable
  public static ByteBuffer nlpszMenuName(long struct) {
    return MemoryUtil.memByteBufferNT2Safe(MemoryUtil.memGetAddress(struct + (long)LPSZMENUNAME));
  }
  
  @Nullable
  public static String nlpszMenuNameString(long struct) {
    return MemoryUtil.memUTF16Safe(MemoryUtil.memGetAddress(struct + (long)LPSZMENUNAME));
  }
  
  public static ByteBuffer nlpszClassName(long struct) {
    return MemoryUtil.memByteBufferNT2(MemoryUtil.memGetAddress(struct + (long)LPSZCLASSNAME));
  }
  
  public static String nlpszClassNameString(long struct) {
    return MemoryUtil.memUTF16(MemoryUtil.memGetAddress(struct + (long)LPSZCLASSNAME));
  }
  
  public static long nhIconSm(long struct) {
    return MemoryUtil.memGetAddress(struct + (long)HICONSM);
  }
  
  public static void ncbSize(long struct, int value) {
    UNSAFE.putInt(null, struct + (long)CBSIZE, value);
  }
  
  public static void nstyle(long struct, int value) {
    UNSAFE.putInt(null, struct + (long)STYLE, value);
  }
  
  public static void nlpfnWndProc(long struct, WindowProcI value) {
    MemoryUtil.memPutAddress(struct + (long)LPFNWNDPROC, value.address());
  }
  
  public static void ncbClsExtra(long struct, int value) {
    UNSAFE.putInt(null, struct + (long)CBCLSEXTRA, value);
  }
  
  public static void ncbWndExtra(long struct, int value) {
    UNSAFE.putInt(null, struct + (long)CBWNDEXTRA, value);
  }
  
  public static void nhInstance(long struct, long value) {
    MemoryUtil.memPutAddress(struct + (long)HINSTANCE, Checks.check(value));
  }
  
  public static void nhIcon(long struct, long value) {
    MemoryUtil.memPutAddress(struct + (long)HICON, value);
  }
  
  public static void nhCursor(long struct, long value) {
    MemoryUtil.memPutAddress(struct + (long)HCURSOR, value);
  }
  
  public static void nhbrBackground(long struct, long value) {
    MemoryUtil.memPutAddress(struct + (long)HBRBACKGROUND, value);
  }
  
  public static void nlpszMenuName(long struct, @Nullable ByteBuffer value) {
    if (Checks.CHECKS)
      Checks.checkNT2Safe(value); 
    MemoryUtil.memPutAddress(struct + (long)LPSZMENUNAME, MemoryUtil.memAddressSafe(value));
  }
  
  public static void nlpszClassName(long struct, ByteBuffer value) {
    if (Checks.CHECKS)
      Checks.checkNT2(value); 
    MemoryUtil.memPutAddress(struct + (long)LPSZCLASSNAME, MemoryUtil.memAddress(value));
  }
  
  public static void nhIconSm(long struct, long value) {
    MemoryUtil.memPutAddress(struct + (long)HICONSM, value);
  }
  
  public static void validate(long struct) {
    Checks.check(MemoryUtil.memGetAddress(struct + (long)LPFNWNDPROC));
    Checks.check(MemoryUtil.memGetAddress(struct + (long)HINSTANCE));
    Checks.check(MemoryUtil.memGetAddress(struct + (long)LPSZCLASSNAME));
  }
  
  public static void validate(long array, int count) {
    for (int i = 0; i < count; i++)
      validate(array + Integer.toUnsignedLong(i) * (long)SIZEOF); 
  }
  
  public static class Buffer extends StructBuffer<WNDCLASSEX, Buffer> implements NativeResource {
    private static final WNDCLASSEX ELEMENT_FACTORY = WNDCLASSEX.create(-1L);
    
    public Buffer(ByteBuffer container) {
      super(container, container.remaining() / WNDCLASSEX.SIZEOF);
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
    
    protected WNDCLASSEX getElementFactory() {
      return ELEMENT_FACTORY;
    }
    
    @NativeType("UINT")
    public int cbSize() {
      return WNDCLASSEX.ncbSize(address());
    }
    
    @NativeType("UINT")
    public int style() {
      return WNDCLASSEX.nstyle(address());
    }
    
    @NativeType("WNDPROC")
    public WindowProc lpfnWndProc() {
      return WNDCLASSEX.nlpfnWndProc(address());
    }
    
    public int cbClsExtra() {
      return WNDCLASSEX.ncbClsExtra(address());
    }
    
    public int cbWndExtra() {
      return WNDCLASSEX.ncbWndExtra(address());
    }
    
    @NativeType("HINSTANCE")
    public long hInstance() {
      return WNDCLASSEX.nhInstance(address());
    }
    
    @NativeType("HICON")
    public long hIcon() {
      return WNDCLASSEX.nhIcon(address());
    }
    
    @NativeType("HCURSOR")
    public long hCursor() {
      return WNDCLASSEX.nhCursor(address());
    }
    
    @NativeType("HBRUSH")
    public long hbrBackground() {
      return WNDCLASSEX.nhbrBackground(address());
    }
    
    @Nullable
    @NativeType("LPCTSTR")
    public ByteBuffer lpszMenuName() {
      return WNDCLASSEX.nlpszMenuName(address());
    }
    
    @Nullable
    @NativeType("LPCTSTR")
    public String lpszMenuNameString() {
      return WNDCLASSEX.nlpszMenuNameString(address());
    }
    
    @NativeType("LPCTSTR")
    public ByteBuffer lpszClassName() {
      return WNDCLASSEX.nlpszClassName(address());
    }
    
    @NativeType("LPCTSTR")
    public String lpszClassNameString() {
      return WNDCLASSEX.nlpszClassNameString(address());
    }
    
    @NativeType("HICON")
    public long hIconSm() {
      return WNDCLASSEX.nhIconSm(address());
    }
    
    public Buffer cbSize(@NativeType("UINT") int value) {
      WNDCLASSEX.ncbSize(address(), value);
      return this;
    }
    
    public Buffer style(@NativeType("UINT") int value) {
      WNDCLASSEX.nstyle(address(), value);
      return this;
    }
    
    public Buffer lpfnWndProc(@NativeType("WNDPROC") WindowProcI value) {
      WNDCLASSEX.nlpfnWndProc(address(), value);
      return this;
    }
    
    public Buffer cbClsExtra(int value) {
      WNDCLASSEX.ncbClsExtra(address(), value);
      return this;
    }
    
    public Buffer cbWndExtra(int value) {
      WNDCLASSEX.ncbWndExtra(address(), value);
      return this;
    }
    
    public Buffer hInstance(@NativeType("HINSTANCE") long value) {
      WNDCLASSEX.nhInstance(address(), value);
      return this;
    }
    
    public Buffer hIcon(@NativeType("HICON") long value) {
      WNDCLASSEX.nhIcon(address(), value);
      return this;
    }
    
    public Buffer hCursor(@NativeType("HCURSOR") long value) {
      WNDCLASSEX.nhCursor(address(), value);
      return this;
    }
    
    public Buffer hbrBackground(@NativeType("HBRUSH") long value) {
      WNDCLASSEX.nhbrBackground(address(), value);
      return this;
    }
    
    public Buffer lpszMenuName(@Nullable @NativeType("LPCTSTR") ByteBuffer value) {
      WNDCLASSEX.nlpszMenuName(address(), value);
      return this;
    }
    
    public Buffer lpszClassName(@NativeType("LPCTSTR") ByteBuffer value) {
      WNDCLASSEX.nlpszClassName(address(), value);
      return this;
    }
    
    public Buffer hIconSm(@NativeType("HICON") long value) {
      WNDCLASSEX.nhIconSm(address(), value);
      return this;
    }
  }
}
