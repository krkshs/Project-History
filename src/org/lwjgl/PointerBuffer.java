package org.lwjgl;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import javax.annotation.Nullable;
import org.lwjgl.system.CheckIntrinsics;
import org.lwjgl.system.Checks;
import org.lwjgl.system.CustomBuffer;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.Pointer;

public class PointerBuffer extends CustomBuffer<PointerBuffer> implements Comparable<PointerBuffer> {
  protected PointerBuffer(long address, @Nullable ByteBuffer container, int mark, int position, int limit, int capacity) {
    super(address, container, mark, position, limit, capacity);
  }
  
  public static PointerBuffer allocateDirect(int capacity) {
    ByteBuffer source = BufferUtils.createByteBuffer(BufferUtils.getAllocationSize(capacity, POINTER_SHIFT));
    return (PointerBuffer)wrap(PointerBuffer.class, MemoryUtil.memAddress(source), capacity, source);
  }
  
  public static PointerBuffer create(long address, int capacity) {
    return (PointerBuffer)wrap(PointerBuffer.class, address, capacity);
  }
  
  public static PointerBuffer create(ByteBuffer source) {
    int capacity = source.remaining() >> POINTER_SHIFT;
    return (PointerBuffer)wrap(PointerBuffer.class, MemoryUtil.memAddress(source), capacity, source);
  }
  
  protected PointerBuffer self() {
    return this;
  }
  
  public int sizeof() {
    return POINTER_SIZE;
  }
  
  public long get() {
    return MemoryUtil.memGetAddress(this.address + Integer.toUnsignedLong(nextGetIndex()) * (long)POINTER_SIZE);
  }
  
  public static long get(ByteBuffer source) {
    if (source.remaining() < POINTER_SIZE)
      throw new BufferUnderflowException(); 
    try {
      return MemoryUtil.memGetAddress(MemoryUtil.memAddress(source));
    } finally {
      source.position(source.position() + POINTER_SIZE);
    } 
  }
  
  public PointerBuffer put(long p) {
    MemoryUtil.memPutAddress(this.address + Integer.toUnsignedLong(nextPutIndex()) * (long)POINTER_SIZE, p);
    return this;
  }
  
  public static void put(ByteBuffer target, long p) {
    if (target.remaining() < POINTER_SIZE)
      throw new BufferOverflowException(); 
    try {
      MemoryUtil.memPutAddress(MemoryUtil.memAddress(target), p);
    } finally {
      target.position(target.position() + POINTER_SIZE);
    } 
  }
  
  public long get(int index) {
    return MemoryUtil.memGetAddress(this.address + Checks.check(index, this.limit) * (long)POINTER_SIZE);
  }
  
  public static long get(ByteBuffer source, int index) {
    CheckIntrinsics.checkFromIndexSize(index, POINTER_SIZE, source.limit());
    return MemoryUtil.memGetAddress(MemoryUtil.memAddress0(source) + (long)index);
  }
  
  public PointerBuffer put(int index, long p) {
    MemoryUtil.memPutAddress(this.address + Checks.check(index, this.limit) * (long)POINTER_SIZE, p);
    return this;
  }
  
  public static void put(ByteBuffer target, int index, long p) {
    CheckIntrinsics.checkFromIndexSize(index, POINTER_SIZE, target.limit());
    MemoryUtil.memPutAddress(MemoryUtil.memAddress0(target) + (long)index, p);
  }
  
  public PointerBuffer put(Pointer pointer) {
    put(pointer.address());
    return this;
  }
  
  public PointerBuffer put(int index, Pointer pointer) {
    put(index, pointer.address());
    return this;
  }
  
  public PointerBuffer put(ByteBuffer buffer) {
    put(MemoryUtil.memAddress(buffer));
    return this;
  }
  
  public PointerBuffer put(ShortBuffer buffer) {
    put(MemoryUtil.memAddress(buffer));
    return this;
  }
  
  public PointerBuffer put(IntBuffer buffer) {
    put(MemoryUtil.memAddress(buffer));
    return this;
  }
  
  public PointerBuffer put(LongBuffer buffer) {
    put(MemoryUtil.memAddress(buffer));
    return this;
  }
  
  public PointerBuffer put(FloatBuffer buffer) {
    put(MemoryUtil.memAddress(buffer));
    return this;
  }
  
  public PointerBuffer put(DoubleBuffer buffer) {
    put(MemoryUtil.memAddress(buffer));
    return this;
  }
  
  public PointerBuffer putAddressOf(CustomBuffer<?> buffer) {
    put(MemoryUtil.memAddress(buffer));
    return this;
  }
  
  public PointerBuffer put(int index, ByteBuffer buffer) {
    put(index, MemoryUtil.memAddress(buffer));
    return this;
  }
  
  public PointerBuffer put(int index, ShortBuffer buffer) {
    put(index, MemoryUtil.memAddress(buffer));
    return this;
  }
  
  public PointerBuffer put(int index, IntBuffer buffer) {
    put(index, MemoryUtil.memAddress(buffer));
    return this;
  }
  
  public PointerBuffer put(int index, LongBuffer buffer) {
    put(index, MemoryUtil.memAddress(buffer));
    return this;
  }
  
  public PointerBuffer put(int index, FloatBuffer buffer) {
    put(index, MemoryUtil.memAddress(buffer));
    return this;
  }
  
  public PointerBuffer put(int index, DoubleBuffer buffer) {
    put(index, MemoryUtil.memAddress(buffer));
    return this;
  }
  
  public PointerBuffer putAddressOf(int index, CustomBuffer<?> buffer) {
    put(index, MemoryUtil.memAddress(buffer));
    return this;
  }
  
  public ByteBuffer getByteBuffer(int size) {
    return MemoryUtil.memByteBuffer(get(), size);
  }
  
  public ShortBuffer getShortBuffer(int size) {
    return MemoryUtil.memShortBuffer(get(), size);
  }
  
  public IntBuffer getIntBuffer(int size) {
    return MemoryUtil.memIntBuffer(get(), size);
  }
  
  public LongBuffer getLongBuffer(int size) {
    return MemoryUtil.memLongBuffer(get(), size);
  }
  
  public FloatBuffer getFloatBuffer(int size) {
    return MemoryUtil.memFloatBuffer(get(), size);
  }
  
  public DoubleBuffer getDoubleBuffer(int size) {
    return MemoryUtil.memDoubleBuffer(get(), size);
  }
  
  public PointerBuffer getPointerBuffer(int size) {
    return MemoryUtil.memPointerBuffer(get(), size);
  }
  
  public String getStringASCII() {
    return MemoryUtil.memASCII(get());
  }
  
  public String getStringUTF8() {
    return MemoryUtil.memUTF8(get());
  }
  
  public String getStringUTF16() {
    return MemoryUtil.memUTF16(get());
  }
  
  public ByteBuffer getByteBuffer(int index, int size) {
    return MemoryUtil.memByteBuffer(get(index), size);
  }
  
  public ShortBuffer getShortBuffer(int index, int size) {
    return MemoryUtil.memShortBuffer(get(index), size);
  }
  
  public IntBuffer getIntBuffer(int index, int size) {
    return MemoryUtil.memIntBuffer(get(index), size);
  }
  
  public LongBuffer getLongBuffer(int index, int size) {
    return MemoryUtil.memLongBuffer(get(index), size);
  }
  
  public FloatBuffer getFloatBuffer(int index, int size) {
    return MemoryUtil.memFloatBuffer(get(index), size);
  }
  
  public DoubleBuffer getDoubleBuffer(int index, int size) {
    return MemoryUtil.memDoubleBuffer(get(index), size);
  }
  
  public PointerBuffer getPointerBuffer(int index, int size) {
    return MemoryUtil.memPointerBuffer(get(index), size);
  }
  
  public String getStringASCII(int index) {
    return MemoryUtil.memASCII(get(index));
  }
  
  public String getStringUTF8(int index) {
    return MemoryUtil.memUTF8(get(index));
  }
  
  public String getStringUTF16(int index) {
    return MemoryUtil.memUTF16(get(index));
  }
  
  public PointerBuffer get(long[] dst) {
    return get(dst, 0, dst.length);
  }
  
  public PointerBuffer get(long[] dst, int offset, int length) {
    if (BITS64) {
      MemoryUtil.memLongBuffer(address(), remaining()).get(dst, offset, length);
      position(position() + length);
    } else
      get32(dst, offset, length); 
    return this;
  }
  
  private void get32(long[] dst, int offset, int length) {
    CheckIntrinsics.checkFromIndexSize(offset, length, dst.length);
    if (remaining() < length)
      throw new BufferUnderflowException(); 
    for (int i = offset, end = offset + length; i < end; i++)
      dst[i] = get(); 
  }
  
  public PointerBuffer put(long[] src) {
    return put(src, 0, src.length);
  }
  
  public PointerBuffer put(long[] src, int offset, int length) {
    if (BITS64) {
      MemoryUtil.memLongBuffer(address(), remaining()).put(src, offset, length);
      position(position() + length);
    } else
      put32(src, offset, length); 
    return this;
  }
  
  private void put32(long[] src, int offset, int length) {
    CheckIntrinsics.checkFromIndexSize(offset, length, src.length);
    if (remaining() < length)
      throw new BufferOverflowException(); 
    int end = offset + length;
    for (int i = offset; i < end; i++)
      put(src[i]); 
  }
  
  public int hashCode() {
    int h = 1;
    int p = position();
    for (int i = limit() - 1; i >= p; i--)
      h = 31 * h + (int)get(i); 
    return h;
  }
  
  public boolean equals(Object ob) {
    if (!(ob instanceof PointerBuffer))
      return false; 
    PointerBuffer that = (PointerBuffer)ob;
    if (remaining() != that.remaining())
      return false; 
    int p = position();
    for (int i = limit() - 1, j = that.limit() - 1; i >= p; i--, j--) {
      long v1 = get(i);
      long v2 = that.get(j);
      if (v1 != v2)
        return false; 
    } 
    return true;
  }
  
  public int compareTo(PointerBuffer that) {
    int n = position() + Math.min(remaining(), that.remaining());
    for (int i = position(), j = that.position(); i < n; ) {
      long v1 = get(i);
      long v2 = that.get(j);
      if (v1 == v2) {
        i++;
        j++;
        continue;
      } 
      if (v1 < v2)
        return -1; 
      return 1;
    } 
    return remaining() - that.remaining();
  }
}
