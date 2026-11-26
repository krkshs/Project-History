package org.lwjgl;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import org.lwjgl.system.CheckIntrinsics;
import org.lwjgl.system.Checks;
import org.lwjgl.system.CustomBuffer;
import org.lwjgl.system.MemoryUtil;

public class CLongBuffer extends CustomBuffer<CLongBuffer> implements Comparable<CLongBuffer> {
  protected CLongBuffer(long address, @Nullable ByteBuffer container, int mark, int position, int limit, int capacity) {
    super(address, container, mark, position, limit, capacity);
  }
  
  public static CLongBuffer allocateDirect(int capacity) {
    ByteBuffer source = BufferUtils.createByteBuffer(BufferUtils.getAllocationSize(capacity, CLONG_SHIFT));
    return (CLongBuffer)wrap(CLongBuffer.class, MemoryUtil.memAddress(source), capacity, source);
  }
  
  public static CLongBuffer create(long address, int capacity) {
    return (CLongBuffer)wrap(CLongBuffer.class, address, capacity);
  }
  
  public static CLongBuffer create(ByteBuffer source) {
    int capacity = source.remaining() >> CLONG_SHIFT;
    return (CLongBuffer)wrap(CLongBuffer.class, MemoryUtil.memAddress(source), capacity, source);
  }
  
  protected CLongBuffer self() {
    return this;
  }
  
  public int sizeof() {
    return CLONG_SIZE;
  }
  
  public long get() {
    return MemoryUtil.memGetCLong(this.address + Integer.toUnsignedLong(nextGetIndex()) * (long)CLONG_SIZE);
  }
  
  public static long get(ByteBuffer source) {
    if (source.remaining() < CLONG_SIZE)
      throw new BufferUnderflowException(); 
    try {
      return MemoryUtil.memGetCLong(MemoryUtil.memAddress(source));
    } finally {
      source.position(source.position() + CLONG_SIZE);
    } 
  }
  
  public CLongBuffer put(long p) {
    MemoryUtil.memPutCLong(this.address + Integer.toUnsignedLong(nextPutIndex()) * (long)CLONG_SIZE, p);
    return this;
  }
  
  public static void put(ByteBuffer target, long p) {
    if (target.remaining() < CLONG_SIZE)
      throw new BufferOverflowException(); 
    try {
      MemoryUtil.memPutCLong(MemoryUtil.memAddress(target), p);
    } finally {
      target.position(target.position() + CLONG_SIZE);
    } 
  }
  
  public long get(int index) {
    return MemoryUtil.memGetCLong(this.address + Checks.check(index, this.limit) * (long)CLONG_SIZE);
  }
  
  public static long get(ByteBuffer source, int index) {
    CheckIntrinsics.checkFromIndexSize(index, CLONG_SIZE, source.limit());
    return MemoryUtil.memGetCLong(MemoryUtil.memAddress0(source) + (long)index);
  }
  
  public CLongBuffer put(int index, long p) {
    MemoryUtil.memPutCLong(this.address + Checks.check(index, this.limit) * (long)CLONG_SIZE, p);
    return this;
  }
  
  public static void put(ByteBuffer target, int index, long p) {
    CheckIntrinsics.checkFromIndexSize(index, CLONG_SIZE, target.limit());
    MemoryUtil.memPutCLong(MemoryUtil.memAddress0(target) + (long)index, p);
  }
  
  public CLongBuffer get(long[] dst) {
    return get(dst, 0, dst.length);
  }
  
  public CLongBuffer get(long[] dst, int offset, int length) {
    if (CLONG_SIZE == 8) {
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
  
  public CLongBuffer put(long[] src) {
    return put(src, 0, src.length);
  }
  
  public CLongBuffer put(long[] src, int offset, int length) {
    if (CLONG_SIZE == 8) {
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
    if (!(ob instanceof CLongBuffer))
      return false; 
    CLongBuffer that = (CLongBuffer)ob;
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
  
  public int compareTo(CLongBuffer that) {
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
