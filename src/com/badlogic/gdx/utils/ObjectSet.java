package com.badlogic.gdx.utils;

import com.badlogic.gdx.math.MathUtils;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ObjectSet<T> implements Iterable<T> {
  public int size;
  
  T[] keyTable;
  
  float loadFactor;
  
  int threshold;
  
  protected int shift;
  
  protected int mask;
  
  private transient ObjectSetIterator iterator1;
  
  private transient ObjectSetIterator iterator2;
  
  public ObjectSet() {
    this(51, 0.8F);
  }
  
  public ObjectSet(int initialCapacity) {
    this(initialCapacity, 0.8F);
  }
  
  public ObjectSet(int initialCapacity, float loadFactor) {
    if (loadFactor <= 0.0F || loadFactor >= 1.0F)
      throw new IllegalArgumentException("loadFactor must be > 0 and < 1: " + loadFactor); 
    this.loadFactor = loadFactor;
    int tableSize = tableSize(initialCapacity, loadFactor);
    this.threshold = (int)((float)tableSize * loadFactor);
    this.mask = tableSize - 1;
    this.shift = Long.numberOfLeadingZeros((long)this.mask);
    this.keyTable = (T[])new Object[tableSize];
  }
  
  public ObjectSet(ObjectSet<? extends T> set) {
    this((int)((float)set.keyTable.length * set.loadFactor), set.loadFactor);
    System.arraycopy(set.keyTable, 0, this.keyTable, 0, set.keyTable.length);
    this.size = set.size;
  }
  
  protected int place(T item) {
    return (int)((long)item.hashCode() * -7046029254386353131L >>> this.shift);
  }
  
  int locateKey(T key) {
    if (key == null)
      throw new IllegalArgumentException("key cannot be null."); 
    T[] keyTable = this.keyTable;
    for (int i = place(key);; i = i + 1 & this.mask) {
      T other = keyTable[i];
      if (other == null)
        return -(i + 1); 
      if (other.equals(key))
        return i; 
    } 
  }
  
  public boolean add(T key) {
    int i = locateKey(key);
    if (i >= 0)
      return false; 
    i = -(i + 1);
    this.keyTable[i] = key;
    if (++this.size >= this.threshold)
      resize(this.keyTable.length << 1); 
    return true;
  }
  
  public void addAll(Array<? extends T> array) {
    addAll(array.items, 0, array.size);
  }
  
  public void addAll(Array<? extends T> array, int offset, int length) {
    if (offset + length > array.size)
      throw new IllegalArgumentException("offset + length must be <= size: " + offset + " + " + length + " <= " + array.size); 
    addAll(array.items, offset, length);
  }
  
  public boolean addAll(T... array) {
    return addAll(array, 0, array.length);
  }
  
  public boolean addAll(T[] array, int offset, int length) {
    ensureCapacity(length);
    int oldSize = this.size;
    for (int i = offset, n = i + length; i < n; i++)
      add(array[i]); 
    return (oldSize != this.size);
  }
  
  public void addAll(ObjectSet<T> set) {
    ensureCapacity(set.size);
    T[] keyTable = set.keyTable;
    for (int i = 0, n = keyTable.length; i < n; i++) {
      T key = keyTable[i];
      if (key != null)
        add(key); 
    } 
  }
  
  private void addResize(T key) {
    T[] keyTable = this.keyTable;
    for (int i = place(key);; i = i + 1 & this.mask) {
      if (keyTable[i] == null) {
        keyTable[i] = key;
        return;
      } 
    } 
  }
  
  public boolean remove(T key) {
    int i = locateKey(key);
    if (i < 0)
      return false; 
    T[] keyTable = this.keyTable;
    int mask = this.mask, next = i + 1 & mask;
    while ((key = keyTable[next]) != null) {
      int placement = place(key);
      if ((next - placement & mask) > (i - placement & mask)) {
        keyTable[i] = key;
        i = next;
      } 
      next = next + 1 & mask;
    } 
    keyTable[i] = null;
    this.size--;
    return true;
  }
  
  public boolean notEmpty() {
    return (this.size > 0);
  }
  
  public boolean isEmpty() {
    return (this.size == 0);
  }
  
  public void shrink(int maximumCapacity) {
    if (maximumCapacity < 0)
      throw new IllegalArgumentException("maximumCapacity must be >= 0: " + maximumCapacity); 
    int tableSize = tableSize(maximumCapacity, this.loadFactor);
    if (this.keyTable.length > tableSize)
      resize(tableSize); 
  }
  
  public void clear(int maximumCapacity) {
    int tableSize = tableSize(maximumCapacity, this.loadFactor);
    if (this.keyTable.length <= tableSize) {
      clear();
      return;
    } 
    this.size = 0;
    resize(tableSize);
  }
  
  public void clear() {
    if (this.size == 0)
      return; 
    this.size = 0;
    Arrays.fill(this.keyTable, null);
  }
  
  public boolean contains(T key) {
    return (locateKey(key) >= 0);
  }
  
  @Null
  public T get(T key) {
    int i = locateKey(key);
    return (i < 0) ? null : this.keyTable[i];
  }
  
  public T first() {
    T[] keyTable = this.keyTable;
    for (int i = 0, n = keyTable.length; i < n; i++) {
      if (keyTable[i] != null)
        return keyTable[i]; 
    } 
    throw new IllegalStateException("ObjectSet is empty.");
  }
  
  public void ensureCapacity(int additionalCapacity) {
    int tableSize = tableSize(this.size + additionalCapacity, this.loadFactor);
    if (this.keyTable.length < tableSize)
      resize(tableSize); 
  }
  
  private void resize(int newSize) {
    int oldCapacity = this.keyTable.length;
    this.threshold = (int)((float)newSize * this.loadFactor);
    this.mask = newSize - 1;
    this.shift = Long.numberOfLeadingZeros((long)this.mask);
    T[] oldKeyTable = this.keyTable;
    this.keyTable = (T[])new Object[newSize];
    if (this.size > 0)
      for (int i = 0; i < oldCapacity; i++) {
        T key = oldKeyTable[i];
        if (key != null)
          addResize(key); 
      }  
  }
  
  public int hashCode() {
    int h = this.size;
    T[] keyTable = this.keyTable;
    for (int i = 0, n = keyTable.length; i < n; i++) {
      T key = keyTable[i];
      if (key != null)
        h += key.hashCode(); 
    } 
    return h;
  }
  
  public boolean equals(Object obj) {
    if (!(obj instanceof ObjectSet))
      return false; 
    ObjectSet other = (ObjectSet)obj;
    if (other.size != this.size)
      return false; 
    T[] keyTable = this.keyTable;
    for (int i = 0, n = keyTable.length; i < n; i++) {
      if (keyTable[i] != null && !other.contains(keyTable[i]))
        return false; 
    } 
    return true;
  }
  
  public String toString() {
    return '{' + toString(", ") + '}';
  }
  
  public String toString(String separator) {
    if (this.size == 0)
      return ""; 
    java.lang.StringBuilder buffer = new java.lang.StringBuilder(32);
    T[] keyTable = this.keyTable;
    int i = keyTable.length;
    while (i-- > 0) {
      T key = keyTable[i];
      if (key == null)
        continue; 
      buffer.append((key == this) ? "(this)" : key);
      break;
    } 
    while (i-- > 0) {
      T key = keyTable[i];
      if (key == null)
        continue; 
      buffer.append(separator);
      buffer.append((key == this) ? "(this)" : key);
    } 
    return buffer.toString();
  }
  
  public ObjectSetIterator<T> iterator() {
    if (Collections.allocateIterators)
      return new ObjectSetIterator<>(this); 
    if (this.iterator1 == null) {
      this.iterator1 = new ObjectSetIterator<>(this);
      this.iterator2 = new ObjectSetIterator<>(this);
    } 
    if (!this.iterator1.valid) {
      this.iterator1.reset();
      this.iterator1.valid = true;
      this.iterator2.valid = false;
      return this.iterator1;
    } 
    this.iterator2.reset();
    this.iterator2.valid = true;
    this.iterator1.valid = false;
    return this.iterator2;
  }
  
  public static <T> ObjectSet<T> with(T... array) {
    ObjectSet<T> set = new ObjectSet<>();
    set.addAll(array);
    return set;
  }
  
  static int tableSize(int capacity, float loadFactor) {
    if (capacity < 0)
      throw new IllegalArgumentException("capacity must be >= 0: " + capacity); 
    int tableSize = MathUtils.nextPowerOfTwo(Math.max(2, (int)Math.ceil((double)((float)capacity / loadFactor))));
    if (tableSize > 1073741824)
      throw new IllegalArgumentException("The required capacity is too large: " + capacity); 
    return tableSize;
  }
  
  public static class ObjectSetIterator<K> implements Iterable<K>, Iterator<K> {
    public boolean hasNext;
    
    final ObjectSet<K> set;
    
    int nextIndex;
    
    int currentIndex;
    
    boolean valid = true;
    
    public ObjectSetIterator(ObjectSet<K> set) {
      this.set = set;
      reset();
    }
    
    public void reset() {
      this.currentIndex = -1;
      this.nextIndex = -1;
      findNextIndex();
    }
    
    private void findNextIndex() {
      T[] arrayOfT = this.set.keyTable;
      for (int n = this.set.keyTable.length; ++this.nextIndex < n;) {
        if (arrayOfT[this.nextIndex] != null) {
          this.hasNext = true;
          return;
        } 
      } 
      this.hasNext = false;
    }
    
    public void remove() {
      int i = this.currentIndex;
      if (i < 0)
        throw new IllegalStateException("next must be called before remove."); 
      T[] arrayOfT = this.set.keyTable;
      int mask = this.set.mask, next = i + 1 & mask;
      T t;
      while ((t = arrayOfT[next]) != null) {
        int placement = this.set.place((K)t);
        if ((next - placement & mask) > (i - placement & mask)) {
          arrayOfT[i] = t;
          i = next;
        } 
        next = next + 1 & mask;
      } 
      arrayOfT[i] = null;
      this.set.size--;
      if (i != this.currentIndex)
        this.nextIndex--; 
      this.currentIndex = -1;
    }
    
    public boolean hasNext() {
      if (!this.valid)
        throw new GdxRuntimeException("#iterator() cannot be used nested."); 
      return this.hasNext;
    }
    
    public K next() {
      if (!this.hasNext)
        throw new NoSuchElementException(); 
      if (!this.valid)
        throw new GdxRuntimeException("#iterator() cannot be used nested."); 
      T t = this.set.keyTable[this.nextIndex];
      this.currentIndex = this.nextIndex;
      findNextIndex();
      return (K)t;
    }
    
    public ObjectSetIterator<K> iterator() {
      return this;
    }
    
    public Array<K> toArray(Array<K> array) {
      while (this.hasNext)
        array.add(next()); 
      return array;
    }
    
    public Array<K> toArray() {
      return toArray(new Array<>(true, this.set.size));
    }
  }
}
