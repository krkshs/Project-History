package org.lwjgl.system;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import javax.annotation.Nullable;
import org.lwjgl.CLongBuffer;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.jni.JNINativeInterface;
import org.lwjgl.system.libc.LibCString;
import sun.misc.Unsafe;

public final class MemoryUtil {
  static final int ARRAY_TLC_SIZE = Configuration.ARRAY_TLC_SIZE.get(8192);
  
  static final ThreadLocal<byte[]> ARRAY_TLC_BYTE = ThreadLocal.withInitial(() -> new byte[ARRAY_TLC_SIZE]);
  
  static final ThreadLocal<char[]> ARRAY_TLC_CHAR = ThreadLocal.withInitial(() -> new char[ARRAY_TLC_SIZE]);
  
  static final ByteOrder NATIVE_ORDER = ByteOrder.nativeOrder();
  
  private static final Charset UTF16 = (NATIVE_ORDER == ByteOrder.LITTLE_ENDIAN) ? StandardCharsets.UTF_16LE : StandardCharsets.UTF_16BE;
  
  static {
    Library.initialize();
    ByteBuffer bb = ByteBuffer.allocateDirect(0).order(NATIVE_ORDER);
    BUFFER_BYTE = (Class)bb.getClass();
    BUFFER_SHORT = (Class)bb.asShortBuffer().getClass();
    BUFFER_CHAR = (Class)bb.asCharBuffer().getClass();
    BUFFER_INT = (Class)bb.asIntBuffer().getClass();
    BUFFER_LONG = (Class)bb.asLongBuffer().getClass();
    BUFFER_FLOAT = (Class)bb.asFloatBuffer().getClass();
    BUFFER_DOUBLE = (Class)bb.asDoubleBuffer().getClass();
  }
  
  static final Unsafe UNSAFE = getUnsafeInstance();
  
  static {
    try {
      MARK = getMarkOffset();
      POSITION = getPositionOffset();
      LIMIT = getLimitOffset();
      CAPACITY = getCapacityOffset();
      ADDRESS = getAddressOffset();
      int oopSize = UNSAFE.arrayIndexScale(Object[].class);
      long offset = Math.max(Math.max(Math.max(MARK, POSITION), LIMIT), CAPACITY) + 4L + (long)(oopSize - 1) & (Integer.toUnsignedLong(oopSize - 1) ^ 0xFFFFFFFFFFFFFFFFL);
      long a = memAddress(bb);
      PARENT_BYTE = getParentOffset(offset, oopSize, bb, bb.duplicate().order(bb.order()));
      PARENT_SHORT = getParentOffset(offset, oopSize, memShortBuffer(a, 0), bb.asShortBuffer());
      PARENT_CHAR = getParentOffset(offset, oopSize, memCharBuffer(a, 0), bb.asCharBuffer());
      PARENT_INT = getParentOffset(offset, oopSize, memIntBuffer(a, 0), bb.asIntBuffer());
      PARENT_LONG = getParentOffset(offset, oopSize, memLongBuffer(a, 0), bb.asLongBuffer());
      PARENT_FLOAT = getParentOffset(offset, oopSize, memFloatBuffer(a, 0), bb.asFloatBuffer());
      PARENT_DOUBLE = getParentOffset(offset, oopSize, memDoubleBuffer(a, 0), bb.asDoubleBuffer());
    } catch (Throwable t) {
      throw new UnsupportedOperationException(t);
    } 
  }
  
  public static final int PAGE_SIZE = UNSAFE.pageSize();
  
  public static final int CACHE_LINE_SIZE = 64;
  
  static final class LazyInit {
    static final MemoryUtil.MemoryAllocator ALLOCATOR_IMPL = MemoryManage.getInstance();
    
    static final MemoryUtil.MemoryAllocator ALLOCATOR = Configuration.DEBUG_MEMORY_ALLOCATOR.get(false) ? new MemoryManage.DebugAllocator(ALLOCATOR_IMPL) : ALLOCATOR_IMPL;
    
    static {
      APIUtil.apiLog("MemoryUtil allocator: " + ALLOCATOR.getClass().getSimpleName());
    }
  }
  
  public static MemoryAllocator getAllocator() {
    return getAllocator(false);
  }
  
  public static MemoryAllocator getAllocator(boolean tracked) {
    return tracked ? LazyInit.ALLOCATOR : LazyInit.ALLOCATOR_IMPL;
  }
  
  public static long nmemAlloc(long size) {
    return LazyInit.ALLOCATOR.malloc(size);
  }
  
  public static long nmemAllocChecked(long size) {
    long address = nmemAlloc((size != 0L) ? size : 1L);
    if (Checks.CHECKS && address == 0L)
      throw new OutOfMemoryError(); 
    return address;
  }
  
  private static long getAllocationSize(int elements, int elementShift) {
    return APIUtil.apiCheckAllocation(elements, Integer.toUnsignedLong(elements) << elementShift, Pointer.BITS64 ? Long.MAX_VALUE : 4294967295L);
  }
  
  public static ByteBuffer memAlloc(int size) {
    return wrap(BUFFER_BYTE, nmemAllocChecked((long)size), size).order(NATIVE_ORDER);
  }
  
  public static ShortBuffer memAllocShort(int size) {
    return wrap(BUFFER_SHORT, nmemAllocChecked(getAllocationSize(size, 1)), size);
  }
  
  public static IntBuffer memAllocInt(int size) {
    return wrap(BUFFER_INT, nmemAllocChecked(getAllocationSize(size, 2)), size);
  }
  
  public static FloatBuffer memAllocFloat(int size) {
    return wrap(BUFFER_FLOAT, nmemAllocChecked(getAllocationSize(size, 2)), size);
  }
  
  public static LongBuffer memAllocLong(int size) {
    return wrap(BUFFER_LONG, nmemAllocChecked(getAllocationSize(size, 3)), size);
  }
  
  public static CLongBuffer memAllocCLong(int size) {
    return Pointer.Default.<CLongBuffer>wrap(CLongBuffer.class, nmemAllocChecked(getAllocationSize(size, Pointer.CLONG_SHIFT)), size);
  }
  
  public static DoubleBuffer memAllocDouble(int size) {
    return wrap(BUFFER_DOUBLE, nmemAllocChecked(getAllocationSize(size, 3)), size);
  }
  
  public static PointerBuffer memAllocPointer(int size) {
    return Pointer.Default.<PointerBuffer>wrap(PointerBuffer.class, nmemAllocChecked(getAllocationSize(size, Pointer.POINTER_SHIFT)), size);
  }
  
  public static void nmemFree(long ptr) {
    LazyInit.ALLOCATOR.free(ptr);
  }
  
  public static void memFree(@Nullable Buffer ptr) {
    if (ptr != null)
      nmemFree(UNSAFE.getLong(ptr, ADDRESS)); 
  }
  
  public static void memFree(@Nullable CustomBuffer ptr) {
    if (ptr != null)
      nmemFree(ptr.address); 
  }
  
  public static long nmemCalloc(long num, long size) {
    return LazyInit.ALLOCATOR.calloc(num, size);
  }
  
  public static long nmemCallocChecked(long num, long size) {
    if (num == 0L || size == 0L) {
      num = 1L;
      size = 1L;
    } 
    long address = nmemCalloc(num, size);
    if (Checks.CHECKS && address == 0L)
      throw new OutOfMemoryError(); 
    return address;
  }
  
  public static ByteBuffer memCalloc(int num, int size) {
    return wrap(BUFFER_BYTE, nmemCallocChecked((long)num, (long)size), num * size).order(NATIVE_ORDER);
  }
  
  public static ByteBuffer memCalloc(int num) {
    return wrap(BUFFER_BYTE, nmemCallocChecked((long)num, 1L), num).order(NATIVE_ORDER);
  }
  
  public static ShortBuffer memCallocShort(int num) {
    return wrap(BUFFER_SHORT, nmemCallocChecked((long)num, 2L), num);
  }
  
  public static IntBuffer memCallocInt(int num) {
    return wrap(BUFFER_INT, nmemCallocChecked((long)num, 4L), num);
  }
  
  public static FloatBuffer memCallocFloat(int num) {
    return wrap(BUFFER_FLOAT, nmemCallocChecked((long)num, 4L), num);
  }
  
  public static LongBuffer memCallocLong(int num) {
    return wrap(BUFFER_LONG, nmemCallocChecked((long)num, 8L), num);
  }
  
  public static CLongBuffer memCallocCLong(int num) {
    return Pointer.Default.<CLongBuffer>wrap(CLongBuffer.class, nmemCallocChecked((long)num, (long)Pointer.CLONG_SIZE), num);
  }
  
  public static DoubleBuffer memCallocDouble(int num) {
    return wrap(BUFFER_DOUBLE, nmemCallocChecked((long)num, 8L), num);
  }
  
  public static PointerBuffer memCallocPointer(int num) {
    return Pointer.Default.<PointerBuffer>wrap(PointerBuffer.class, nmemCallocChecked((long)num, (long)Pointer.POINTER_SIZE), num);
  }
  
  public static long nmemRealloc(long ptr, long size) {
    return LazyInit.ALLOCATOR.realloc(ptr, size);
  }
  
  public static long nmemReallocChecked(long ptr, long size) {
    long address = nmemRealloc(ptr, (size != 0L) ? size : 1L);
    if (Checks.CHECKS && address == 0L)
      throw new OutOfMemoryError(); 
    return address;
  }
  
  private static <T extends Buffer> T realloc(@Nullable T old_p, T new_p, int size) {
    if (old_p != null)
      new_p.position(Math.min(old_p.position(), size)); 
    return new_p;
  }
  
  public static ByteBuffer memRealloc(@Nullable ByteBuffer ptr, int size) {
    return MemoryUtil.<ByteBuffer>realloc(ptr, memByteBuffer(nmemReallocChecked((ptr == null) ? 0L : UNSAFE.getLong(ptr, ADDRESS), (long)size), size), size);
  }
  
  public static ShortBuffer memRealloc(@Nullable ShortBuffer ptr, int size) {
    return MemoryUtil.<ShortBuffer>realloc(ptr, memShortBuffer(nmemReallocChecked((ptr == null) ? 0L : UNSAFE.getLong(ptr, ADDRESS), getAllocationSize(size, 1)), size), size);
  }
  
  public static IntBuffer memRealloc(@Nullable IntBuffer ptr, int size) {
    return MemoryUtil.<IntBuffer>realloc(ptr, memIntBuffer(nmemReallocChecked((ptr == null) ? 0L : UNSAFE.getLong(ptr, ADDRESS), getAllocationSize(size, 2)), size), size);
  }
  
  public static LongBuffer memRealloc(@Nullable LongBuffer ptr, int size) {
    return MemoryUtil.<LongBuffer>realloc(ptr, memLongBuffer(nmemReallocChecked((ptr == null) ? 0L : UNSAFE.getLong(ptr, ADDRESS), getAllocationSize(size, 3)), size), size);
  }
  
  public static CLongBuffer memRealloc(@Nullable CLongBuffer ptr, int size) {
    CLongBuffer buffer = memCLongBuffer(nmemReallocChecked((ptr == null) ? 0L : ptr.address, getAllocationSize(size, Pointer.CLONG_SIZE)), size);
    if (ptr != null)
      buffer.position(Math.min(ptr.position(), size)); 
    return buffer;
  }
  
  public static FloatBuffer memRealloc(@Nullable FloatBuffer ptr, int size) {
    return MemoryUtil.<FloatBuffer>realloc(ptr, memFloatBuffer(nmemReallocChecked((ptr == null) ? 0L : UNSAFE.getLong(ptr, ADDRESS), getAllocationSize(size, 2)), size), size);
  }
  
  public static DoubleBuffer memRealloc(@Nullable DoubleBuffer ptr, int size) {
    return MemoryUtil.<DoubleBuffer>realloc(ptr, memDoubleBuffer(nmemReallocChecked((ptr == null) ? 0L : UNSAFE.getLong(ptr, ADDRESS), getAllocationSize(size, 3)), size), size);
  }
  
  public static PointerBuffer memRealloc(@Nullable PointerBuffer ptr, int size) {
    PointerBuffer buffer = memPointerBuffer(nmemReallocChecked((ptr == null) ? 0L : ptr.address, getAllocationSize(size, Pointer.POINTER_SHIFT)), size);
    if (ptr != null)
      buffer.position(Math.min(ptr.position(), size)); 
    return buffer;
  }
  
  public static long nmemAlignedAlloc(long alignment, long size) {
    return LazyInit.ALLOCATOR.aligned_alloc(alignment, size);
  }
  
  public static long nmemAlignedAllocChecked(long alignment, long size) {
    long address = nmemAlignedAlloc(alignment, (size != 0L) ? size : 1L);
    if (Checks.CHECKS && address == 0L)
      throw new OutOfMemoryError(); 
    return address;
  }
  
  public static ByteBuffer memAlignedAlloc(int alignment, int size) {
    return wrap(BUFFER_BYTE, nmemAlignedAllocChecked((long)alignment, (long)size), size).order(NATIVE_ORDER);
  }
  
  public static void nmemAlignedFree(long ptr) {
    LazyInit.ALLOCATOR.aligned_free(ptr);
  }
  
  public static void memAlignedFree(@Nullable ByteBuffer ptr) {
    if (ptr != null)
      nmemAlignedFree(UNSAFE.getLong(ptr, ADDRESS)); 
  }
  
  public static interface MemoryAllocationReport {
    void invoke(long param1Long1, long param1Long2, long param1Long3, @Nullable String param1String, @Nullable StackTraceElement... param1VarArgs);
    
    public enum Aggregate {
      ALL, GROUP_BY_METHOD, GROUP_BY_STACKTRACE;
    }
  }
  
  public static void memReport(MemoryAllocationReport report) {
    MemoryManage.DebugAllocator.report(report);
  }
  
  public static void memReport(MemoryAllocationReport report, MemoryAllocationReport.Aggregate groupByStackTrace, boolean groupByThread) {
    MemoryManage.DebugAllocator.report(report, groupByStackTrace, groupByThread);
  }
  
  public static long memAddress0(Buffer buffer) {
    return UNSAFE.getLong(buffer, ADDRESS);
  }
  
  public static long memAddress(ByteBuffer buffer) {
    return (long)buffer.position() + memAddress0(buffer);
  }
  
  public static long memAddress(ByteBuffer buffer, int position) {
    Objects.requireNonNull(buffer);
    return memAddress0(buffer) + Integer.toUnsignedLong(position);
  }
  
  private static long address(int position, int elementShift, long address) {
    return address + (((long)position & 0xFFFFFFFFL) << elementShift);
  }
  
  public static long memAddress(ShortBuffer buffer) {
    return address(buffer.position(), 1, memAddress0(buffer));
  }
  
  public static long memAddress(ShortBuffer buffer, int position) {
    Objects.requireNonNull(buffer);
    return address(position, 1, memAddress0(buffer));
  }
  
  public static long memAddress(CharBuffer buffer) {
    return address(buffer.position(), 1, memAddress0(buffer));
  }
  
  public static long memAddress(CharBuffer buffer, int position) {
    Objects.requireNonNull(buffer);
    return address(position, 1, memAddress0(buffer));
  }
  
  public static long memAddress(IntBuffer buffer) {
    return address(buffer.position(), 2, memAddress0(buffer));
  }
  
  public static long memAddress(IntBuffer buffer, int position) {
    Objects.requireNonNull(buffer);
    return address(position, 2, memAddress0(buffer));
  }
  
  public static long memAddress(FloatBuffer buffer) {
    return address(buffer.position(), 2, memAddress0(buffer));
  }
  
  public static long memAddress(FloatBuffer buffer, int position) {
    Objects.requireNonNull(buffer);
    return address(position, 2, memAddress0(buffer));
  }
  
  public static long memAddress(LongBuffer buffer) {
    return address(buffer.position(), 3, memAddress0(buffer));
  }
  
  public static long memAddress(LongBuffer buffer, int position) {
    Objects.requireNonNull(buffer);
    return address(position, 3, memAddress0(buffer));
  }
  
  public static long memAddress(DoubleBuffer buffer) {
    return address(buffer.position(), 3, memAddress0(buffer));
  }
  
  public static long memAddress(DoubleBuffer buffer, int position) {
    Objects.requireNonNull(buffer);
    return address(position, 3, memAddress0(buffer));
  }
  
  public static long memAddress(Buffer buffer) {
    int elementShift;
    if (buffer instanceof ByteBuffer) {
      elementShift = 0;
    } else if (buffer instanceof ShortBuffer || buffer instanceof CharBuffer) {
      elementShift = 1;
    } else if (buffer instanceof IntBuffer || buffer instanceof FloatBuffer) {
      elementShift = 2;
    } else
      elementShift = 3; 
    return address(buffer.position(), elementShift, memAddress0(buffer));
  }
  
  public static long memAddress(CustomBuffer<?> buffer) {
    return buffer.address();
  }
  
  public static long memAddress(CustomBuffer<?> buffer, int position) {
    return buffer.address(position);
  }
  
  public static long memAddressSafe(@Nullable ByteBuffer buffer) {
    return (buffer == null) ? 0L : (memAddress0(buffer) + (long)buffer.position());
  }
  
  public static long memAddressSafe(@Nullable ShortBuffer buffer) {
    return (buffer == null) ? 0L : address(buffer.position(), 1, memAddress0(buffer));
  }
  
  public static long memAddressSafe(@Nullable CharBuffer buffer) {
    return (buffer == null) ? 0L : address(buffer.position(), 1, memAddress0(buffer));
  }
  
  public static long memAddressSafe(@Nullable IntBuffer buffer) {
    return (buffer == null) ? 0L : address(buffer.position(), 2, memAddress0(buffer));
  }
  
  public static long memAddressSafe(@Nullable FloatBuffer buffer) {
    return (buffer == null) ? 0L : address(buffer.position(), 2, memAddress0(buffer));
  }
  
  public static long memAddressSafe(@Nullable LongBuffer buffer) {
    return (buffer == null) ? 0L : address(buffer.position(), 3, memAddress0(buffer));
  }
  
  public static long memAddressSafe(@Nullable DoubleBuffer buffer) {
    return (buffer == null) ? 0L : address(buffer.position(), 3, memAddress0(buffer));
  }
  
  public static long memAddressSafe(@Nullable Pointer pointer) {
    return (pointer == null) ? 0L : pointer.address();
  }
  
  public static ByteBuffer memByteBuffer(long address, int capacity) {
    if (Checks.CHECKS)
      Checks.check(address); 
    return wrap(BUFFER_BYTE, address, capacity).order(NATIVE_ORDER);
  }
  
  @Nullable
  public static ByteBuffer memByteBufferSafe(long address, int capacity) {
    return (address == 0L) ? null : wrap(BUFFER_BYTE, address, capacity).order(NATIVE_ORDER);
  }
  
  public static ByteBuffer memByteBuffer(ShortBuffer buffer) {
    if (Checks.CHECKS && 1073741823 < buffer.remaining())
      throw new IllegalArgumentException("The source buffer range is too wide"); 
    return wrap(BUFFER_BYTE, memAddress(buffer), buffer.remaining() << 1).order(NATIVE_ORDER);
  }
  
  public static ByteBuffer memByteBuffer(CharBuffer buffer) {
    if (Checks.CHECKS && 1073741823 < buffer.remaining())
      throw new IllegalArgumentException("The source buffer range is too wide"); 
    return wrap(BUFFER_BYTE, memAddress(buffer), buffer.remaining() << 1).order(NATIVE_ORDER);
  }
  
  public static ByteBuffer memByteBuffer(IntBuffer buffer) {
    if (Checks.CHECKS && 536870911 < buffer.remaining())
      throw new IllegalArgumentException("The source buffer range is too wide"); 
    return wrap(BUFFER_BYTE, memAddress(buffer), buffer.remaining() << 2).order(NATIVE_ORDER);
  }
  
  public static ByteBuffer memByteBuffer(LongBuffer buffer) {
    if (Checks.CHECKS && 268435455 < buffer.remaining())
      throw new IllegalArgumentException("The source buffer range is too wide"); 
    return wrap(BUFFER_BYTE, memAddress(buffer), buffer.remaining() << 3).order(NATIVE_ORDER);
  }
  
  public static ByteBuffer memByteBuffer(FloatBuffer buffer) {
    if (Checks.CHECKS && 536870911 < buffer.remaining())
      throw new IllegalArgumentException("The source buffer range is too wide"); 
    return wrap(BUFFER_BYTE, memAddress(buffer), buffer.remaining() << 2).order(NATIVE_ORDER);
  }
  
  public static ByteBuffer memByteBuffer(DoubleBuffer buffer) {
    if (Checks.CHECKS && 268435455 < buffer.remaining())
      throw new IllegalArgumentException("The source buffer range is too wide"); 
    return wrap(BUFFER_BYTE, memAddress(buffer), buffer.remaining() << 3).order(NATIVE_ORDER);
  }
  
  public static ByteBuffer memByteBuffer(CustomBuffer<?> buffer) {
    if (Checks.CHECKS && Integer.MAX_VALUE / buffer.sizeof() < buffer.remaining())
      throw new IllegalArgumentException("The source buffer range is too wide"); 
    return wrap(BUFFER_BYTE, memAddress(buffer), buffer.remaining() * buffer.sizeof()).order(NATIVE_ORDER);
  }
  
  public static ShortBuffer memShortBuffer(long address, int capacity) {
    if (Checks.CHECKS)
      Checks.check(address); 
    return wrap(BUFFER_SHORT, address, capacity);
  }
  
  @Nullable
  public static ShortBuffer memShortBufferSafe(long address, int capacity) {
    return (address == 0L) ? null : wrap(BUFFER_SHORT, address, capacity);
  }
  
  public static CharBuffer memCharBuffer(long address, int capacity) {
    if (Checks.CHECKS)
      Checks.check(address); 
    return wrap(BUFFER_CHAR, address, capacity);
  }
  
  @Nullable
  public static CharBuffer memCharBufferSafe(long address, int capacity) {
    return (address == 0L) ? null : wrap(BUFFER_CHAR, address, capacity);
  }
  
  public static IntBuffer memIntBuffer(long address, int capacity) {
    if (Checks.CHECKS)
      Checks.check(address); 
    return wrap(BUFFER_INT, address, capacity);
  }
  
  @Nullable
  public static IntBuffer memIntBufferSafe(long address, int capacity) {
    return (address == 0L) ? null : wrap(BUFFER_INT, address, capacity);
  }
  
  public static LongBuffer memLongBuffer(long address, int capacity) {
    if (Checks.CHECKS)
      Checks.check(address); 
    return wrap(BUFFER_LONG, address, capacity);
  }
  
  @Nullable
  public static LongBuffer memLongBufferSafe(long address, int capacity) {
    return (address == 0L) ? null : wrap(BUFFER_LONG, address, capacity);
  }
  
  public static CLongBuffer memCLongBuffer(long address, int capacity) {
    if (Checks.CHECKS)
      Checks.check(address); 
    return Pointer.Default.<CLongBuffer>wrap(CLongBuffer.class, address, capacity);
  }
  
  @Nullable
  public static CLongBuffer memCLongBufferSafe(long address, int capacity) {
    return (address == 0L) ? null : Pointer.Default.<CLongBuffer>wrap(CLongBuffer.class, address, capacity);
  }
  
  public static FloatBuffer memFloatBuffer(long address, int capacity) {
    if (Checks.CHECKS)
      Checks.check(address); 
    return wrap(BUFFER_FLOAT, address, capacity);
  }
  
  @Nullable
  public static FloatBuffer memFloatBufferSafe(long address, int capacity) {
    return (address == 0L) ? null : wrap(BUFFER_FLOAT, address, capacity);
  }
  
  public static DoubleBuffer memDoubleBuffer(long address, int capacity) {
    if (Checks.CHECKS)
      Checks.check(address); 
    return wrap(BUFFER_DOUBLE, address, capacity);
  }
  
  @Nullable
  public static DoubleBuffer memDoubleBufferSafe(long address, int capacity) {
    return (address == 0L) ? null : wrap(BUFFER_DOUBLE, address, capacity);
  }
  
  public static PointerBuffer memPointerBuffer(long address, int capacity) {
    if (Checks.CHECKS)
      Checks.check(address); 
    return Pointer.Default.<PointerBuffer>wrap(PointerBuffer.class, address, capacity);
  }
  
  @Nullable
  public static PointerBuffer memPointerBufferSafe(long address, int capacity) {
    return (address == 0L) ? null : Pointer.Default.<PointerBuffer>wrap(PointerBuffer.class, address, capacity);
  }
  
  public static ByteBuffer memDuplicate(ByteBuffer buffer) {
    ByteBuffer target;
    try {
      target = (ByteBuffer)UNSAFE.allocateInstance(BUFFER_BYTE);
    } catch (InstantiationException e) {
      throw new UnsupportedOperationException(e);
    } 
    UNSAFE.putLong(target, ADDRESS, UNSAFE.getLong(buffer, ADDRESS));
    UNSAFE.putInt(target, MARK, UNSAFE.getInt(buffer, MARK));
    UNSAFE.putInt(target, POSITION, UNSAFE.getInt(buffer, POSITION));
    UNSAFE.putInt(target, LIMIT, UNSAFE.getInt(buffer, LIMIT));
    UNSAFE.putInt(target, CAPACITY, UNSAFE.getInt(buffer, CAPACITY));
    Object attachment = UNSAFE.getObject(buffer, PARENT_BYTE);
    UNSAFE.putObject(target, PARENT_BYTE, (attachment == null) ? buffer : attachment);
    return target.order(buffer.order());
  }
  
  public static ShortBuffer memDuplicate(ShortBuffer buffer) {
    return duplicate(BUFFER_SHORT, buffer, PARENT_SHORT);
  }
  
  public static CharBuffer memDuplicate(CharBuffer buffer) {
    return duplicate(BUFFER_CHAR, buffer, PARENT_CHAR);
  }
  
  public static IntBuffer memDuplicate(IntBuffer buffer) {
    return duplicate(BUFFER_INT, buffer, PARENT_INT);
  }
  
  public static LongBuffer memDuplicate(LongBuffer buffer) {
    return duplicate(BUFFER_LONG, buffer, PARENT_LONG);
  }
  
  public static FloatBuffer memDuplicate(FloatBuffer buffer) {
    return duplicate(BUFFER_FLOAT, buffer, PARENT_FLOAT);
  }
  
  public static DoubleBuffer memDuplicate(DoubleBuffer buffer) {
    return duplicate(BUFFER_DOUBLE, buffer, PARENT_DOUBLE);
  }
  
  public static ByteBuffer memSlice(ByteBuffer buffer) {
    return slice(buffer, memAddress0(buffer) + (long)buffer.position(), buffer.remaining());
  }
  
  public static ShortBuffer memSlice(ShortBuffer buffer) {
    return slice(BUFFER_SHORT, buffer, address(buffer.position(), 1, memAddress0(buffer)), buffer.remaining(), PARENT_SHORT);
  }
  
  public static CharBuffer memSlice(CharBuffer buffer) {
    return slice(BUFFER_CHAR, buffer, address(buffer.position(), 1, memAddress0(buffer)), buffer.remaining(), PARENT_CHAR);
  }
  
  public static IntBuffer memSlice(IntBuffer buffer) {
    return slice(BUFFER_INT, buffer, address(buffer.position(), 2, memAddress0(buffer)), buffer.remaining(), PARENT_INT);
  }
  
  public static LongBuffer memSlice(LongBuffer buffer) {
    return slice(BUFFER_LONG, buffer, address(buffer.position(), 3, memAddress0(buffer)), buffer.remaining(), PARENT_LONG);
  }
  
  public static FloatBuffer memSlice(FloatBuffer buffer) {
    return slice(BUFFER_FLOAT, buffer, address(buffer.position(), 2, memAddress0(buffer)), buffer.remaining(), PARENT_FLOAT);
  }
  
  public static DoubleBuffer memSlice(DoubleBuffer buffer) {
    return slice(BUFFER_DOUBLE, buffer, address(buffer.position(), 3, memAddress0(buffer)), buffer.remaining(), PARENT_DOUBLE);
  }
  
  public static ByteBuffer memSlice(ByteBuffer buffer, int offset, int capacity) {
    int position = buffer.position() + offset;
    if (offset < 0 || buffer.limit() < position)
      throw new IllegalArgumentException(); 
    if (capacity < 0 || buffer.capacity() - position < capacity)
      throw new IllegalArgumentException(); 
    return slice(buffer, memAddress0(buffer) + (long)position, capacity);
  }
  
  public static ShortBuffer memSlice(ShortBuffer buffer, int offset, int capacity) {
    int position = buffer.position() + offset;
    if (offset < 0 || buffer.limit() < position)
      throw new IllegalArgumentException(); 
    if (capacity < 0 || buffer.capacity() - position < capacity)
      throw new IllegalArgumentException(); 
    return slice(BUFFER_SHORT, buffer, address(position, 1, memAddress0(buffer)), capacity, PARENT_SHORT);
  }
  
  public static CharBuffer memSlice(CharBuffer buffer, int offset, int capacity) {
    int position = buffer.position() + offset;
    if (offset < 0 || buffer.limit() < position)
      throw new IllegalArgumentException(); 
    if (capacity < 0 || buffer.capacity() - position < capacity)
      throw new IllegalArgumentException(); 
    return slice(BUFFER_CHAR, buffer, address(position, 1, memAddress0(buffer)), capacity, PARENT_CHAR);
  }
  
  public static IntBuffer memSlice(IntBuffer buffer, int offset, int capacity) {
    int position = buffer.position() + offset;
    if (offset < 0 || buffer.limit() < position)
      throw new IllegalArgumentException(); 
    if (capacity < 0 || buffer.capacity() - position < capacity)
      throw new IllegalArgumentException(); 
    return slice(BUFFER_INT, buffer, address(position, 2, memAddress0(buffer)), capacity, PARENT_INT);
  }
  
  public static LongBuffer memSlice(LongBuffer buffer, int offset, int capacity) {
    int position = buffer.position() + offset;
    if (offset < 0 || buffer.limit() < position)
      throw new IllegalArgumentException(); 
    if (capacity < 0 || buffer.capacity() - position < capacity)
      throw new IllegalArgumentException(); 
    return slice(BUFFER_LONG, buffer, address(position, 3, memAddress0(buffer)), capacity, PARENT_LONG);
  }
  
  public static FloatBuffer memSlice(FloatBuffer buffer, int offset, int capacity) {
    int position = buffer.position() + offset;
    if (offset < 0 || buffer.limit() < position)
      throw new IllegalArgumentException(); 
    if (capacity < 0 || buffer.capacity() - position < capacity)
      throw new IllegalArgumentException(); 
    return slice(BUFFER_FLOAT, buffer, address(position, 2, memAddress0(buffer)), capacity, PARENT_FLOAT);
  }
  
  public static DoubleBuffer memSlice(DoubleBuffer buffer, int offset, int capacity) {
    int position = buffer.position() + offset;
    if (offset < 0 || buffer.limit() < position)
      throw new IllegalArgumentException(); 
    if (capacity < 0 || buffer.capacity() - position < capacity)
      throw new IllegalArgumentException(); 
    return slice(BUFFER_DOUBLE, buffer, address(position, 3, memAddress0(buffer)), capacity, PARENT_DOUBLE);
  }
  
  public static <T extends CustomBuffer<T>> T memSlice(T buffer, int offset, int capacity) {
    return buffer.slice(offset, capacity);
  }
  
  public static void memSet(ByteBuffer ptr, int value) {
    memSet(memAddress(ptr), value, (long)ptr.remaining());
  }
  
  public static void memSet(ShortBuffer ptr, int value) {
    memSet(memAddress(ptr), value, APIUtil.apiGetBytes(ptr.remaining(), 1));
  }
  
  public static void memSet(CharBuffer ptr, int value) {
    memSet(memAddress(ptr), value, APIUtil.apiGetBytes(ptr.remaining(), 1));
  }
  
  public static void memSet(IntBuffer ptr, int value) {
    memSet(memAddress(ptr), value, APIUtil.apiGetBytes(ptr.remaining(), 2));
  }
  
  public static void memSet(LongBuffer ptr, int value) {
    memSet(memAddress(ptr), value, APIUtil.apiGetBytes(ptr.remaining(), 3));
  }
  
  public static void memSet(FloatBuffer ptr, int value) {
    memSet(memAddress(ptr), value, APIUtil.apiGetBytes(ptr.remaining(), 2));
  }
  
  public static void memSet(DoubleBuffer ptr, int value) {
    memSet(memAddress(ptr), value, APIUtil.apiGetBytes(ptr.remaining(), 3));
  }
  
  public static <T extends CustomBuffer<T>> void memSet(T ptr, int value) {
    memSet(memAddress(ptr), value, Integer.toUnsignedLong(ptr.remaining()) * (long)ptr.sizeof());
  }
  
  public static <T extends Struct> void memSet(T ptr, int value) {
    memSet(ptr.address, value, (long)ptr.sizeof());
  }
  
  public static void memCopy(ByteBuffer src, ByteBuffer dst) {
    if (Checks.CHECKS)
      Checks.check((Buffer)dst, src.remaining()); 
    MultiReleaseMemCopy.copy(memAddress(src), memAddress(dst), (long)src.remaining());
  }
  
  public static void memCopy(ShortBuffer src, ShortBuffer dst) {
    if (Checks.CHECKS)
      Checks.check((Buffer)dst, src.remaining()); 
    MultiReleaseMemCopy.copy(memAddress(src), memAddress(dst), APIUtil.apiGetBytes(src.remaining(), 1));
  }
  
  public static void memCopy(CharBuffer src, CharBuffer dst) {
    if (Checks.CHECKS)
      Checks.check((Buffer)dst, src.remaining()); 
    MultiReleaseMemCopy.copy(memAddress(src), memAddress(dst), APIUtil.apiGetBytes(src.remaining(), 1));
  }
  
  public static void memCopy(IntBuffer src, IntBuffer dst) {
    if (Checks.CHECKS)
      Checks.check((Buffer)dst, src.remaining()); 
    MultiReleaseMemCopy.copy(memAddress(src), memAddress(dst), APIUtil.apiGetBytes(src.remaining(), 2));
  }
  
  public static void memCopy(LongBuffer src, LongBuffer dst) {
    if (Checks.CHECKS)
      Checks.check((Buffer)dst, src.remaining()); 
    MultiReleaseMemCopy.copy(memAddress(src), memAddress(dst), APIUtil.apiGetBytes(src.remaining(), 3));
  }
  
  public static void memCopy(FloatBuffer src, FloatBuffer dst) {
    if (Checks.CHECKS)
      Checks.check((Buffer)dst, src.remaining()); 
    MultiReleaseMemCopy.copy(memAddress(src), memAddress(dst), APIUtil.apiGetBytes(src.remaining(), 2));
  }
  
  public static void memCopy(DoubleBuffer src, DoubleBuffer dst) {
    if (Checks.CHECKS)
      Checks.check((Buffer)dst, src.remaining()); 
    MultiReleaseMemCopy.copy(memAddress(src), memAddress(dst), APIUtil.apiGetBytes(src.remaining(), 3));
  }
  
  public static <T extends CustomBuffer<T>> void memCopy(T src, T dst) {
    if (Checks.CHECKS)
      Checks.check(dst, src.remaining()); 
    MultiReleaseMemCopy.copy(memAddress(src), memAddress(dst), Integer.toUnsignedLong(src.remaining()) * (long)src.sizeof());
  }
  
  public static <T extends Struct> void memCopy(T src, T dst) {
    MultiReleaseMemCopy.copy(src.address, dst.address, (long)src.sizeof());
  }
  
  private static final NativeShift SHIFT = (NATIVE_ORDER == ByteOrder.BIG_ENDIAN) ? new NativeShift() {
      public long left(long value, int bytes) {
        return value << bytes << 3;
      }
      
      public long right(long value, int bytes) {
        return value >>> bytes << 3;
      }
    } : new NativeShift() {
      public long left(long value, int bytes) {
        return value >>> bytes << 3;
      }
      
      public long right(long value, int bytes) {
        return value << bytes << 3;
      }
    };
  
  private static final long FILL_PATTERN = Long.divideUnsigned(-1L, 255L);
  
  public static final long NULL = 0L;
  
  static final Class<? extends ByteBuffer> BUFFER_BYTE;
  
  static final Class<? extends ShortBuffer> BUFFER_SHORT;
  
  static final Class<? extends CharBuffer> BUFFER_CHAR;
  
  static final Class<? extends IntBuffer> BUFFER_INT;
  
  static final Class<? extends LongBuffer> BUFFER_LONG;
  
  static final Class<? extends FloatBuffer> BUFFER_FLOAT;
  
  static final Class<? extends DoubleBuffer> BUFFER_DOUBLE;
  
  private static final long MARK;
  
  private static final long POSITION;
  
  private static final long LIMIT;
  
  private static final long CAPACITY;
  
  private static final long ADDRESS;
  
  private static final long PARENT_BYTE;
  
  private static final long PARENT_SHORT;
  
  private static final long PARENT_CHAR;
  
  private static final long PARENT_INT;
  
  private static final long PARENT_LONG;
  
  private static final long PARENT_FLOAT;
  
  private static final long PARENT_DOUBLE;
  
  private static final int MAGIC_CAPACITY = 219540062;
  
  private static final int MAGIC_POSITION = 16435934;
  
  public static void memSet(long ptr, int value, long bytes) {
    if (Checks.DEBUG && (ptr == 0L || bytes < 0L))
      throw new IllegalArgumentException(); 
    if (256L <= bytes) {
      LibCString.nmemset(ptr, value, bytes);
      return;
    } 
    long fill = (long)(value & 0xFF) * FILL_PATTERN;
    int i = 0;
    int length = (int)bytes & 0xFF;
    if (length != 0) {
      int misalignment = (int)ptr & 0x7;
      if (misalignment != 0) {
        long aligned = ptr - (long)misalignment;
        UNSAFE.putLong(null, aligned, merge(
              UNSAFE.getLong(null, aligned), fill, 
              
              SHIFT.right(SHIFT.left(-1L, Math.max(0, 8 - length)), misalignment)));
        i += 8 - misalignment;
      } 
    } 
    for (; i <= length - 8; i += 8)
      UNSAFE.putLong(null, ptr + (long)i, fill); 
    int tail = length - i;
    if (0 < tail)
      UNSAFE.putLong(null, ptr + (long)i, merge(fill, 
            
            UNSAFE.getLong(null, ptr + (long)i), 
            SHIFT.right(-1L, tail))); 
  }
  
  static long merge(long a, long b, long mask) {
    return a ^ (a ^ b) & mask;
  }
  
  public static void memCopy(long src, long dst, long bytes) {
    if (Checks.DEBUG && (src == 0L || dst == 0L || bytes < 0L))
      throw new IllegalArgumentException(); 
    MultiReleaseMemCopy.copy(src, dst, bytes);
  }
  
  static void memCopyAligned(long src, long dst, int bytes) {
    int i = 0;
    for (; i <= bytes - 8; i += 8)
      UNSAFE.putLong(null, dst + (long)i, UNSAFE.getLong(null, src + (long)i)); 
    if (i < bytes)
      UNSAFE.putLong(null, dst + (long)i, merge(
            UNSAFE.getLong(null, src + (long)i), 
            UNSAFE.getLong(null, dst + (long)i), 
            SHIFT.right(-1L, bytes - i))); 
  }
  
  public static boolean memGetBoolean(long ptr) {
    return (UNSAFE.getByte(null, ptr) != 0);
  }
  
  public static byte memGetByte(long ptr) {
    return UNSAFE.getByte(null, ptr);
  }
  
  public static short memGetShort(long ptr) {
    return UNSAFE.getShort(null, ptr);
  }
  
  public static int memGetInt(long ptr) {
    return UNSAFE.getInt(null, ptr);
  }
  
  public static long memGetLong(long ptr) {
    return UNSAFE.getLong(null, ptr);
  }
  
  public static float memGetFloat(long ptr) {
    return UNSAFE.getFloat(null, ptr);
  }
  
  public static double memGetDouble(long ptr) {
    return UNSAFE.getDouble(null, ptr);
  }
  
  public static long memGetCLong(long ptr) {
    return (Pointer.CLONG_SIZE == 8) ? 
      UNSAFE.getLong(null, ptr) : 
      (long)UNSAFE.getInt(null, ptr);
  }
  
  public static long memGetAddress(long ptr) {
    return Pointer.BITS64 ? 
      UNSAFE.getLong(null, ptr) : (
      (long)UNSAFE.getInt(null, ptr) & 0xFFFFFFFFL);
  }
  
  public static void memPutByte(long ptr, byte value) {
    UNSAFE.putByte(null, ptr, value);
  }
  
  public static void memPutShort(long ptr, short value) {
    UNSAFE.putShort(null, ptr, value);
  }
  
  public static void memPutInt(long ptr, int value) {
    UNSAFE.putInt(null, ptr, value);
  }
  
  public static void memPutLong(long ptr, long value) {
    UNSAFE.putLong(null, ptr, value);
  }
  
  public static void memPutFloat(long ptr, float value) {
    UNSAFE.putFloat(null, ptr, value);
  }
  
  public static void memPutDouble(long ptr, double value) {
    UNSAFE.putDouble(null, ptr, value);
  }
  
  public static void memPutCLong(long ptr, long value) {
    if (Pointer.CLONG_SIZE == 8) {
      UNSAFE.putLong(null, ptr, value);
    } else
      UNSAFE.putInt(null, ptr, (int)value); 
  }
  
  public static void memPutAddress(long ptr, long value) {
    if (Pointer.BITS64) {
      UNSAFE.putLong(null, ptr, value);
    } else
      UNSAFE.putInt(null, ptr, (int)value); 
  }
  
  @Deprecated
  public static long memNewGlobalRef(Object obj) {
    return JNINativeInterface.NewGlobalRef(obj);
  }
  
  @Deprecated
  public static void memDeleteGlobalRef(long globalRef) {
    JNINativeInterface.DeleteGlobalRef(globalRef);
  }
  
  @Deprecated
  public static long memNewWeakGlobalRef(Object obj) {
    return JNINativeInterface.NewWeakGlobalRef(obj);
  }
  
  @Deprecated
  public static void memDeleteWeakGlobalRef(long globalRef) {
    JNINativeInterface.DeleteWeakGlobalRef(globalRef);
  }
  
  public static ByteBuffer memASCII(CharSequence text) {
    return memASCII(text, true);
  }
  
  @Nullable
  public static ByteBuffer memASCIISafe(@Nullable CharSequence text) {
    return (text == null) ? null : memASCII(text, true);
  }
  
  public static ByteBuffer memASCII(CharSequence text, boolean nullTerminated) {
    int length = memLengthASCII(text, nullTerminated);
    long target = nmemAlloc((long)length);
    encodeASCII(text, nullTerminated, target);
    return wrap(BUFFER_BYTE, target, length).order(NATIVE_ORDER);
  }
  
  @Nullable
  public static ByteBuffer memASCIISafe(@Nullable CharSequence text, boolean nullTerminated) {
    return (text == null) ? null : memASCII(text, nullTerminated);
  }
  
  public static int memASCII(CharSequence text, boolean nullTerminated, ByteBuffer target) {
    return encodeASCII(text, nullTerminated, memAddress(target));
  }
  
  public static int memASCII(CharSequence text, boolean nullTerminated, ByteBuffer target, int offset) {
    return encodeASCII(text, nullTerminated, memAddress(target, offset));
  }
  
  static int encodeASCII(CharSequence text, boolean nullTerminated, long target) {
    int len = text.length();
    for (int p = 0; p < len; p++)
      UNSAFE.putByte(target + (long)p, (byte)text.charAt(p)); 
    if (nullTerminated)
      UNSAFE.putByte(target + (long)len++, (byte)0); 
    return len;
  }
  
  public static int memLengthASCII(CharSequence value, boolean nullTerminated) {
    return value.length() + (nullTerminated ? 1 : 0);
  }
  
  public static ByteBuffer memUTF8(CharSequence text) {
    return memUTF8(text, true);
  }
  
  @Nullable
  public static ByteBuffer memUTF8Safe(@Nullable CharSequence text) {
    return (text == null) ? null : memUTF8(text, true);
  }
  
  public static ByteBuffer memUTF8(CharSequence text, boolean nullTerminated) {
    int length = memLengthUTF8(text, nullTerminated);
    long target = nmemAlloc((long)length);
    encodeUTF8(text, nullTerminated, target);
    return wrap(BUFFER_BYTE, target, length).order(NATIVE_ORDER);
  }
  
  @Nullable
  public static ByteBuffer memUTF8Safe(@Nullable CharSequence text, boolean nullTerminated) {
    return (text == null) ? null : memUTF8(text, nullTerminated);
  }
  
  public static int memUTF8(CharSequence text, boolean nullTerminated, ByteBuffer target) {
    return encodeUTF8(text, nullTerminated, memAddress(target));
  }
  
  public static int memUTF8(CharSequence text, boolean nullTerminated, ByteBuffer target, int offset) {
    return encodeUTF8(text, nullTerminated, memAddress(target, offset));
  }
  
  static int encodeUTF8(CharSequence text, boolean nullTerminated, long target) {
    int i = 0, len = text.length(), p = 0;
    char c;
    while (i < len && (c = text.charAt(i)) < '\u0080') {
      UNSAFE.putByte(target + (long)p++, (byte)c);
      i++;
    } 
    while (i < len) {
      c = text.charAt(i++);
      if (c < '\u0080') {
        UNSAFE.putByte(target + (long)p++, (byte)c);
        continue;
      } 
      int cp = c;
      if (c < 'ࠀ') {
        UNSAFE.putByte(target + (long)p++, (byte)(0xC0 | cp >> 6));
      } else {
        if (!Character.isHighSurrogate(c)) {
          UNSAFE.putByte(target + (long)p++, (byte)(0xE0 | cp >> 12));
        } else {
          cp = Character.toCodePoint(c, text.charAt(i++));
          UNSAFE.putByte(target + (long)p++, (byte)(0xF0 | cp >> 18));
          UNSAFE.putByte(target + (long)p++, (byte)(0x80 | cp >> 12 & 0x3F));
        } 
        UNSAFE.putByte(target + (long)p++, (byte)(0x80 | cp >> 6 & 0x3F));
      } 
      UNSAFE.putByte(target + (long)p++, (byte)(0x80 | cp & 0x3F));
    } 
    if (nullTerminated)
      UNSAFE.putByte(target + (long)p++, (byte)0); 
    return p;
  }
  
  public static int memLengthUTF8(CharSequence value, boolean nullTerminated) {
    int len = value.length(), bytes = len;
    int i;
    for (i = 0; i < len && 
      '\u0080' > value.charAt(i); i++);
    for (; i < len; i++) {
      char c = value.charAt(i);
      if ('ࠀ' <= c) {
        bytes += encodeUTF8LengthSlow(value, i, len);
        break;
      } 
      bytes += 127 - c >>> 31;
    } 
    return bytes + (nullTerminated ? 1 : 0);
  }
  
  private static int encodeUTF8LengthSlow(CharSequence value, int offset, int len) {
    int bytes = 0;
    for (int i = offset; i < len; i++) {
      char c = value.charAt(i);
      if (c < 'ࠀ') {
        bytes += 127 - c >>> 31;
      } else if (c < '?' || '?' < c) {
        bytes += 2;
      } else {
        bytes += 2;
        i++;
      } 
    } 
    return bytes;
  }
  
  public static ByteBuffer memUTF16(CharSequence text) {
    return memUTF16(text, true);
  }
  
  @Nullable
  public static ByteBuffer memUTF16Safe(@Nullable CharSequence text) {
    return (text == null) ? null : memUTF16(text, true);
  }
  
  public static ByteBuffer memUTF16(CharSequence text, boolean nullTerminated) {
    int length = memLengthUTF16(text, nullTerminated);
    long target = nmemAlloc((long)length);
    encodeUTF16(text, nullTerminated, target);
    return wrap(BUFFER_BYTE, target, length).order(NATIVE_ORDER);
  }
  
  @Nullable
  public static ByteBuffer memUTF16Safe(@Nullable CharSequence text, boolean nullTerminated) {
    return (text == null) ? null : memUTF16(text, nullTerminated);
  }
  
  public static int memUTF16(CharSequence text, boolean nullTerminated, ByteBuffer target) {
    return encodeUTF16(text, nullTerminated, memAddress(target));
  }
  
  public static int memUTF16(CharSequence text, boolean nullTerminated, ByteBuffer target, int offset) {
    return encodeUTF16(text, nullTerminated, memAddress(target, offset));
  }
  
  static int encodeUTF16(CharSequence text, boolean nullTerminated, long target) {
    int len = text.length();
    for (int i = 0; i < len; i++)
      UNSAFE.putShort(target + Integer.toUnsignedLong(i) * 2L, (short)text.charAt(i)); 
    if (nullTerminated)
      UNSAFE.putShort(target + Integer.toUnsignedLong(len++) * 2L, (short)0); 
    return 2 * len;
  }
  
  public static int memLengthUTF16(CharSequence value, boolean nullTerminated) {
    return value.length() + (nullTerminated ? 1 : 0) << 1;
  }
  
  private static int memLengthNT1(long address, int maxLength) {
    if (Checks.CHECKS)
      Checks.check(address); 
    return Pointer.BITS64 ? 
      strlen64NT1(address, maxLength) : 
      strlen32NT1(address, maxLength);
  }
  
  private static int strlen64NT1(long address, int maxLength) {
    int i = 0;
    if (8 <= maxLength) {
      int misalignment = (int)address & 0x7;
      if (misalignment != 0)
        for (int len = 8 - misalignment; i < len; i++) {
          if (UNSAFE.getByte(null, address + (long)i) == 0)
            return i; 
        }  
      while (i <= maxLength - 8 && 
        !MathUtil.mathHasZeroByte(UNSAFE.getLong(null, address + (long)i)))
        i += 8; 
    } 
    for (; i < maxLength && 
      UNSAFE.getByte(null, address + (long)i) != 0; i++);
    return i;
  }
  
  private static int strlen32NT1(long address, int maxLength) {
    int i = 0;
    if (4 <= maxLength) {
      int misalignment = (int)address & 0x3;
      if (misalignment != 0)
        for (int len = 4 - misalignment; i < len; i++) {
          if (UNSAFE.getByte(null, address + (long)i) == 0)
            return i; 
        }  
      while (i <= maxLength - 4 && 
        !MathUtil.mathHasZeroByte(UNSAFE.getInt(null, address + (long)i)))
        i += 4; 
    } 
    for (; i < maxLength && 
      UNSAFE.getByte(null, address + (long)i) != 0; i++);
    return i;
  }
  
  public static int memLengthNT1(ByteBuffer buffer) {
    return memLengthNT1(memAddress(buffer), buffer.remaining());
  }
  
  private static int memLengthNT2(long address, int maxLength) {
    if (Checks.CHECKS)
      Checks.check(address); 
    return Pointer.BITS64 ? 
      strlen64NT2(address, maxLength) : 
      strlen32NT2(address, maxLength);
  }
  
  private static int strlen64NT2(long address, int maxLength) {
    int i = 0;
    if (8 <= maxLength) {
      int misalignment = (int)address & 0x7;
      if (misalignment != 0)
        for (int len = 8 - misalignment; i < len; i += 2) {
          if (UNSAFE.getShort(null, address + (long)i) == 0)
            return i; 
        }  
      while (i <= maxLength - 8 && 
        !MathUtil.mathHasZeroShort(UNSAFE.getLong(null, address + (long)i)))
        i += 8; 
    } 
    for (; i < maxLength && 
      UNSAFE.getShort(null, address + (long)i) != 0; i += 2);
    return i;
  }
  
  private static int strlen32NT2(long address, int maxLength) {
    int i = 0;
    if (4 <= maxLength) {
      int misalignment = (int)address & 0x3;
      if (misalignment != 0)
        for (int len = 4 - misalignment; i < len; i += 2) {
          if (UNSAFE.getShort(null, address + (long)i) == 0)
            return i; 
        }  
      while (i <= maxLength - 4 && 
        !MathUtil.mathHasZeroShort(UNSAFE.getInt(null, address + (long)i)))
        i += 4; 
    } 
    for (; i < maxLength && 
      UNSAFE.getShort(null, address + (long)i) != 0; i += 2);
    return i;
  }
  
  public static int memLengthNT2(ByteBuffer buffer) {
    return memLengthNT2(memAddress(buffer), buffer.remaining());
  }
  
  public static ByteBuffer memByteBufferNT1(long address) {
    return memByteBuffer(address, memLengthNT1(address, Integer.MAX_VALUE));
  }
  
  public static ByteBuffer memByteBufferNT1(long address, int maxLength) {
    return memByteBuffer(address, memLengthNT1(address, maxLength));
  }
  
  @Nullable
  public static ByteBuffer memByteBufferNT1Safe(long address) {
    return (address == 0L) ? null : memByteBuffer(address, memLengthNT1(address, Integer.MAX_VALUE));
  }
  
  @Nullable
  public static ByteBuffer memByteBufferNT1Safe(long address, int maxLength) {
    return (address == 0L) ? null : memByteBuffer(address, memLengthNT1(address, maxLength));
  }
  
  public static ByteBuffer memByteBufferNT2(long address) {
    return memByteBufferNT2(address, 2147483646);
  }
  
  public static ByteBuffer memByteBufferNT2(long address, int maxLength) {
    if (Checks.DEBUG && (
      maxLength & 0x1) != 0)
      throw new IllegalArgumentException("The maximum length must be an even number."); 
    return memByteBuffer(address, memLengthNT2(address, maxLength));
  }
  
  @Nullable
  public static ByteBuffer memByteBufferNT2Safe(long address) {
    return (address == 0L) ? null : memByteBufferNT2(address, 2147483646);
  }
  
  @Nullable
  public static ByteBuffer memByteBufferNT2Safe(long address, int maxLength) {
    return (address == 0L) ? null : memByteBufferNT2(address, maxLength);
  }
  
  public static String memASCII(long address) {
    return memASCII(address, memLengthNT1(address, Integer.MAX_VALUE));
  }
  
  public static String memASCII(long address, int length) {
    if (length <= 0)
      return ""; 
    byte[] ascii = (length <= ARRAY_TLC_SIZE) ? ARRAY_TLC_BYTE.get() : new byte[length];
    memByteBuffer(address, length).get(ascii, 0, length);
    return new String(ascii, 0, 0, length);
  }
  
  public static String memASCII(ByteBuffer buffer) {
    return memASCII(memAddress(buffer), buffer.remaining());
  }
  
  @Nullable
  public static String memASCIISafe(long address) {
    return (address == 0L) ? null : memASCII(address, memLengthNT1(address, Integer.MAX_VALUE));
  }
  
  @Nullable
  public static String memASCIISafe(long address, int length) {
    return (address == 0L) ? null : memASCII(address, length);
  }
  
  @Nullable
  public static String memASCIISafe(@Nullable ByteBuffer buffer) {
    return (buffer == null) ? null : memASCII(memAddress(buffer), buffer.remaining());
  }
  
  public static String memASCII(ByteBuffer buffer, int length) {
    return memASCII(memAddress(buffer), length);
  }
  
  public static String memASCII(ByteBuffer buffer, int length, int offset) {
    return memASCII(memAddress(buffer, offset), length);
  }
  
  public static String memUTF8(long address) {
    return MultiReleaseTextDecoding.decodeUTF8(address, memLengthNT1(address, Integer.MAX_VALUE));
  }
  
  public static String memUTF8(long address, int length) {
    return MultiReleaseTextDecoding.decodeUTF8(address, length);
  }
  
  public static String memUTF8(ByteBuffer buffer) {
    return MultiReleaseTextDecoding.decodeUTF8(memAddress(buffer), buffer.remaining());
  }
  
  @Nullable
  public static String memUTF8Safe(long address) {
    return (address == 0L) ? null : MultiReleaseTextDecoding.decodeUTF8(address, memLengthNT1(address, Integer.MAX_VALUE));
  }
  
  @Nullable
  public static String memUTF8Safe(long address, int length) {
    return (address == 0L) ? null : MultiReleaseTextDecoding.decodeUTF8(address, length);
  }
  
  @Nullable
  public static String memUTF8Safe(@Nullable ByteBuffer buffer) {
    return (buffer == null) ? null : MultiReleaseTextDecoding.decodeUTF8(memAddress(buffer), buffer.remaining());
  }
  
  public static String memUTF8(ByteBuffer buffer, int length) {
    return MultiReleaseTextDecoding.decodeUTF8(memAddress(buffer), length);
  }
  
  public static String memUTF8(ByteBuffer buffer, int length, int offset) {
    return MultiReleaseTextDecoding.decodeUTF8(memAddress(buffer, offset), length);
  }
  
  public static String memUTF16(long address) {
    return memUTF16(address, memLengthNT2(address, 2147483646) >> 1);
  }
  
  public static String memUTF16(long address, int length) {
    if (length <= 0)
      return ""; 
    if (Checks.DEBUG) {
      int len = length << 1;
      byte[] bytes = (len <= ARRAY_TLC_SIZE) ? ARRAY_TLC_BYTE.get() : new byte[len];
      memByteBuffer(address, len).get(bytes, 0, len);
      return new String(bytes, 0, len, UTF16);
    } 
    char[] chars = (length <= ARRAY_TLC_SIZE) ? ARRAY_TLC_CHAR.get() : new char[length];
    memCharBuffer(address, length).get(chars, 0, length);
    return new String(chars, 0, length);
  }
  
  public static String memUTF16(ByteBuffer buffer) {
    return memUTF16(memAddress(buffer), buffer.remaining() >> 1);
  }
  
  @Nullable
  public static String memUTF16Safe(long address) {
    return (address == 0L) ? null : memUTF16(address, memLengthNT2(address, 2147483646) >> 1);
  }
  
  @Nullable
  public static String memUTF16Safe(long address, int length) {
    return (address == 0L) ? null : memUTF16(address, length);
  }
  
  @Nullable
  public static String memUTF16Safe(@Nullable ByteBuffer buffer) {
    return (buffer == null) ? null : memUTF16(memAddress(buffer), buffer.remaining() >> 1);
  }
  
  public static String memUTF16(ByteBuffer buffer, int length) {
    return memUTF16(memAddress(buffer), length);
  }
  
  public static String memUTF16(ByteBuffer buffer, int length, int offset) {
    return memUTF16(memAddress(buffer, offset), length);
  }
  
  private static Unsafe getUnsafeInstance() {
    Field[] fields = Unsafe.class.getDeclaredFields();
    for (Field field : fields) {
      if (field.getType().equals(Unsafe.class)) {
        int modifiers = field.getModifiers();
        if (Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers))
          try {
            field.setAccessible(true);
            return (Unsafe)field.get(null);
          } catch (Exception e) {
            break;
          }  
      } 
    } 
    throw new UnsupportedOperationException("LWJGL requires sun.misc.Unsafe to be available.");
  }
  
  private static long getAddressOffset() {
    long MAGIC_ADDRESS = -2401053090268712947L;
    if (Pointer.BITS32)
      MAGIC_ADDRESS &= 0xFFFFFFFFL; 
    ByteBuffer bb = Objects.<ByteBuffer>requireNonNull(JNINativeInterface.NewDirectByteBuffer(MAGIC_ADDRESS, 0L));
    long offset = 8L;
    while (true) {
      if (UNSAFE.getLong(bb, offset) == MAGIC_ADDRESS)
        return offset; 
      offset += 8L;
    } 
  }
  
  private static long getIntFieldOffset(ByteBuffer bb, int magicValue) {
    long offset = 4L;
    while (true) {
      if (UNSAFE.getInt(bb, offset) == magicValue)
        return offset; 
      offset += 4L;
    } 
  }
  
  private static long getMarkOffset() {
    ByteBuffer bb = Objects.<ByteBuffer>requireNonNull(JNINativeInterface.NewDirectByteBuffer(1L, 0L));
    return getIntFieldOffset(bb, -1);
  }
  
  private static long getPositionOffset() {
    ByteBuffer bb = Objects.<ByteBuffer>requireNonNull(JNINativeInterface.NewDirectByteBuffer(-1L, 219540062L));
    bb.position(16435934);
    return getIntFieldOffset(bb, 16435934);
  }
  
  private static long getLimitOffset() {
    ByteBuffer bb = Objects.<ByteBuffer>requireNonNull(JNINativeInterface.NewDirectByteBuffer(-1L, 219540062L));
    bb.limit(16435934);
    return getIntFieldOffset(bb, 16435934);
  }
  
  private static long getCapacityOffset() {
    ByteBuffer bb = Objects.<ByteBuffer>requireNonNull(JNINativeInterface.NewDirectByteBuffer(-1L, 219540062L));
    bb.limit(0);
    return getIntFieldOffset(bb, 219540062);
  }
  
  private static <T extends Buffer> long getParentOffset(long offset, int oopSize, T buffer, T bufferWithAttachment) {
    switch (oopSize) {
      case 4:
        while (true) {
          if (UNSAFE.getInt(buffer, offset) != UNSAFE.getInt(bufferWithAttachment, offset))
            return offset; 
          offset += (long)oopSize;
        } 
      case 8:
        while (true) {
          if (UNSAFE.getLong(buffer, offset) != UNSAFE.getLong(bufferWithAttachment, offset))
            return offset; 
          offset += (long)oopSize;
        } 
    } 
    throw new IllegalStateException();
  }
  
  static <T extends Buffer> T wrap(Class<? extends T> clazz, long address, int capacity) {
    T buffer;
    try {
      buffer = (T)UNSAFE.allocateInstance(clazz);
    } catch (InstantiationException e) {
      throw new UnsupportedOperationException(e);
    } 
    UNSAFE.putLong(buffer, ADDRESS, address);
    UNSAFE.putInt(buffer, MARK, -1);
    UNSAFE.putInt(buffer, LIMIT, capacity);
    UNSAFE.putInt(buffer, CAPACITY, capacity);
    return buffer;
  }
  
  static ByteBuffer slice(ByteBuffer source, long address, int capacity) {
    ByteBuffer target;
    try {
      target = (ByteBuffer)UNSAFE.allocateInstance(BUFFER_BYTE);
    } catch (InstantiationException e) {
      throw new UnsupportedOperationException(e);
    } 
    UNSAFE.putLong(target, ADDRESS, address);
    UNSAFE.putInt(target, MARK, -1);
    UNSAFE.putInt(target, LIMIT, capacity);
    UNSAFE.putInt(target, CAPACITY, capacity);
    Object attachment = UNSAFE.getObject(source, PARENT_BYTE);
    UNSAFE.putObject(target, PARENT_BYTE, (attachment == null) ? source : attachment);
    return target.order(source.order());
  }
  
  static <T extends Buffer> T slice(Class<? extends T> clazz, T source, long address, int capacity, long attachmentOffset) {
    T target;
    try {
      target = (T)UNSAFE.allocateInstance(clazz);
    } catch (InstantiationException e) {
      throw new UnsupportedOperationException(e);
    } 
    UNSAFE.putLong(target, ADDRESS, address);
    UNSAFE.putInt(target, MARK, -1);
    UNSAFE.putInt(target, LIMIT, capacity);
    UNSAFE.putInt(target, CAPACITY, capacity);
    UNSAFE.putObject(target, attachmentOffset, UNSAFE.getObject(source, attachmentOffset));
    return target;
  }
  
  static <T extends Buffer> T duplicate(Class<? extends T> clazz, T source, long attachmentOffset) {
    T target;
    try {
      target = (T)UNSAFE.allocateInstance(clazz);
    } catch (InstantiationException e) {
      throw new UnsupportedOperationException(e);
    } 
    UNSAFE.putLong(target, ADDRESS, UNSAFE.getLong(source, ADDRESS));
    UNSAFE.putInt(target, MARK, UNSAFE.getInt(source, MARK));
    UNSAFE.putInt(target, POSITION, UNSAFE.getInt(source, POSITION));
    UNSAFE.putInt(target, LIMIT, UNSAFE.getInt(source, LIMIT));
    UNSAFE.putInt(target, CAPACITY, UNSAFE.getInt(source, CAPACITY));
    UNSAFE.putObject(target, attachmentOffset, UNSAFE.getObject(source, attachmentOffset));
    return target;
  }
  
  public static native <T> T memGlobalRefToObject(long paramLong);
  
  private static interface NativeShift {
    long left(long param1Long, int param1Int);
    
    long right(long param1Long, int param1Int);
  }
  
  public static interface MemoryAllocator {
    long getMalloc();
    
    long getCalloc();
    
    long getRealloc();
    
    long getFree();
    
    long getAlignedAlloc();
    
    long getAlignedFree();
    
    long malloc(long param1Long);
    
    long calloc(long param1Long1, long param1Long2);
    
    long realloc(long param1Long1, long param1Long2);
    
    void free(long param1Long);
    
    long aligned_alloc(long param1Long1, long param1Long2);
    
    void aligned_free(long param1Long);
  }
}
