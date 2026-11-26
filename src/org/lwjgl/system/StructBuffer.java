package org.lwjgl.system;

import java.nio.ByteBuffer;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;

public abstract class StructBuffer<T extends Struct, SELF extends StructBuffer<T, SELF>> extends CustomBuffer<SELF> implements Iterable<T> {
  protected StructBuffer(ByteBuffer container, int remaining) {
    super(MemoryUtil.memAddress(container), container, -1, 0, remaining, remaining);
  }
  
  protected StructBuffer(long address, @Nullable ByteBuffer container, int mark, int position, int limit, int capacity) {
    super(address, container, mark, position, limit, capacity);
  }
  
  public int sizeof() {
    return getElementFactory().sizeof();
  }
  
  public T get() {
    return getElementFactory().wrap(this.address, nextGetIndex(), this.container);
  }
  
  public SELF get(T value) {
    int sizeof = getElementFactory().sizeof();
    MemoryUtil.memCopy(this.address + Integer.toUnsignedLong(nextGetIndex()) * (long)sizeof, value.address(), (long)sizeof);
    return self();
  }
  
  public SELF put(T value) {
    int sizeof = getElementFactory().sizeof();
    MemoryUtil.memCopy(value.address(), this.address + Integer.toUnsignedLong(nextPutIndex()) * (long)sizeof, (long)sizeof);
    return self();
  }
  
  public T get(int index) {
    return getElementFactory().wrap(this.address, check(index, this.limit), this.container);
  }
  
  public SELF get(int index, T value) {
    int sizeof = getElementFactory().sizeof();
    MemoryUtil.memCopy(this.address + Checks.check(index, this.limit) * (long)sizeof, value.address(), (long)sizeof);
    return self();
  }
  
  public SELF put(int index, T value) {
    int sizeof = getElementFactory().sizeof();
    MemoryUtil.memCopy(value.address(), this.address + Checks.check(index, this.limit) * (long)sizeof, (long)sizeof);
    return self();
  }
  
  public SELF apply(Consumer<T> consumer) {
    consumer.accept(get());
    return self();
  }
  
  public SELF apply(int index, Consumer<T> consumer) {
    consumer.accept(get(index));
    return self();
  }
  
  public Iterator<T> iterator() {
    return new StructIterator(this.address, this.container, getElementFactory(), this.position, this.limit);
  }
  
  private static class StructIterator<T extends Struct, SELF extends StructBuffer<T, SELF>> implements Iterator<T> {
    private long address;
    
    @Nullable
    private ByteBuffer container;
    
    private T factory;
    
    private int index;
    
    private int fence;
    
    StructIterator(long address, @Nullable ByteBuffer container, T factory, int position, int limit) {
      this.address = address;
      this.container = container;
      this.factory = factory;
      this.index = position;
      this.fence = limit;
    }
    
    public boolean hasNext() {
      return (this.index < this.fence);
    }
    
    public T next() {
      if (Checks.CHECKS && this.fence <= this.index)
        throw new NoSuchElementException(); 
      return this.factory.wrap(this.address, this.index++, this.container);
    }
    
    public void forEachRemaining(Consumer<? super T> action) {
      Objects.requireNonNull(action);
      int i = this.index;
      try {
        for (; i < this.fence; i++)
          action.accept(this.factory.wrap(this.address, i, this.container)); 
      } finally {
        this.index = i;
      } 
    }
  }
  
  public void forEach(Consumer<? super T> action) {
    Objects.requireNonNull(action);
    T factory = getElementFactory();
    for (int i = this.position, fence = this.limit; i < fence; i++)
      action.accept(factory.wrap(this.address, i, this.container)); 
  }
  
  public Spliterator<T> spliterator() {
    return new StructSpliterator(this.address, this.container, getElementFactory(), this.position, this.limit);
  }
  
  private static class StructSpliterator<T extends Struct, SELF extends StructBuffer<T, SELF>> implements Spliterator<T> {
    private long address;
    
    @Nullable
    private ByteBuffer container;
    
    private T factory;
    
    private int index;
    
    private int fence;
    
    StructSpliterator(long address, @Nullable ByteBuffer container, T factory, int position, int limit) {
      this.address = address;
      this.container = container;
      this.factory = factory;
      this.index = position;
      this.fence = limit;
    }
    
    public boolean tryAdvance(Consumer<? super T> action) {
      Objects.requireNonNull(action);
      if (this.index < this.fence) {
        action.accept(this.factory.wrap(this.address, this.index++, this.container));
        return true;
      } 
      return false;
    }
    
    @Nullable
    public Spliterator<T> trySplit() {
      int lo = this.index;
      int mid = lo + this.fence >>> 1;
      return (lo < mid) ? new StructSpliterator(this.address, this.container, this.factory, lo, this.index = mid) : null;
    }
    
    public long estimateSize() {
      return (long)(this.fence - this.index);
    }
    
    public int characteristics() {
      return 17744;
    }
    
    public void forEachRemaining(Consumer<? super T> action) {
      Objects.requireNonNull(action);
      int i = this.index;
      try {
        for (; i < this.fence; i++)
          action.accept(this.factory.wrap(this.address, i, this.container)); 
      } finally {
        this.index = i;
      } 
    }
    
    public Comparator<? super T> getComparator() {
      throw new IllegalStateException();
    }
  }
  
  public Stream<T> stream() {
    return StreamSupport.stream(spliterator(), false);
  }
  
  public Stream<T> parallelStream() {
    return StreamSupport.stream(spliterator(), true);
  }
  
  protected abstract T getElementFactory();
  
  private static int check(int index, int length) {
    if (Checks.CHECKS && (index < 0 || length <= index))
      throw new IndexOutOfBoundsException(); 
    return index;
  }
}
