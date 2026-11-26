package com.badlogic.gdx.utils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LongMap<V> implements Iterable<LongMap.Entry<V>> {
  public int size;
  
  long[] keyTable;
  
  V[] valueTable;
  
  V zeroValue;
  
  boolean hasZeroValue;
  
  private final float loadFactor;
  
  private int threshold;
  
  protected int shift;
  
  protected int mask;
  
  private transient Entries entries1;
  
  private transient Entries entries2;
  
  private transient Values values1;
  
  private transient Values values2;
  
  private transient Keys keys1;
  
  private transient Keys keys2;
  
  public LongMap() {
    this(51, 0.8F);
  }
  
  public LongMap(int initialCapacity) {
    this(initialCapacity, 0.8F);
  }
  
  public LongMap(int initialCapacity, float loadFactor) {
    if (loadFactor <= 0.0F || loadFactor >= 1.0F)
      throw new IllegalArgumentException("loadFactor must be > 0 and < 1: " + loadFactor); 
    this.loadFactor = loadFactor;
    int tableSize = ObjectSet.tableSize(initialCapacity, loadFactor);
    this.threshold = (int)((float)tableSize * loadFactor);
    this.mask = tableSize - 1;
    this.shift = Long.numberOfLeadingZeros((long)this.mask);
    this.keyTable = new long[tableSize];
    this.valueTable = (V[])new Object[tableSize];
  }
  
  public LongMap(LongMap<? extends V> map) {
    this((int)((float)map.keyTable.length * map.loadFactor), map.loadFactor);
    System.arraycopy(map.keyTable, 0, this.keyTable, 0, map.keyTable.length);
    System.arraycopy(map.valueTable, 0, this.valueTable, 0, map.valueTable.length);
    this.size = map.size;
    this.zeroValue = map.zeroValue;
    this.hasZeroValue = map.hasZeroValue;
  }
  
  protected int place(long item) {
    return (int)((item ^ item >>> 32L) * -7046029254386353131L >>> this.shift);
  }
  
  private int locateKey(long key) {
    long[] keyTable = this.keyTable;
    for (int i = place(key);; i = i + 1 & this.mask) {
      long other = keyTable[i];
      if (other == 0L)
        return -(i + 1); 
      if (other == key)
        return i; 
    } 
  }
  
  @Null
  public V put(long key, @Null V value) {
    if (key == 0L) {
      V oldValue = this.zeroValue;
      this.zeroValue = value;
      if (!this.hasZeroValue) {
        this.hasZeroValue = true;
        this.size++;
      } 
      return oldValue;
    } 
    int i = locateKey(key);
    if (i >= 0) {
      V oldValue = this.valueTable[i];
      this.valueTable[i] = value;
      return oldValue;
    } 
    i = -(i + 1);
    this.keyTable[i] = key;
    this.valueTable[i] = value;
    if (++this.size >= this.threshold)
      resize(this.keyTable.length << 1); 
    return null;
  }
  
  public void putAll(LongMap<? extends V> map) {
    ensureCapacity(map.size);
    if (map.hasZeroValue)
      put(0L, map.zeroValue); 
    long[] keyTable = map.keyTable;
    V[] valueTable = map.valueTable;
    for (int i = 0, n = keyTable.length; i < n; i++) {
      long key = keyTable[i];
      if (key != 0L)
        put(key, valueTable[i]); 
    } 
  }
  
  private void putResize(long key, @Null V value) {
    long[] keyTable = this.keyTable;
    for (int i = place(key);; i = i + 1 & this.mask) {
      if (keyTable[i] == 0L) {
        keyTable[i] = key;
        this.valueTable[i] = value;
        return;
      } 
    } 
  }
  
  @Null
  public V get(long key) {
    if (key == 0L)
      return this.hasZeroValue ? this.zeroValue : null; 
    int i = locateKey(key);
    return (i >= 0) ? this.valueTable[i] : null;
  }
  
  public V get(long key, @Null V defaultValue) {
    if (key == 0L)
      return this.hasZeroValue ? this.zeroValue : defaultValue; 
    int i = locateKey(key);
    return (i >= 0) ? this.valueTable[i] : defaultValue;
  }
  
  @Null
  public V remove(long key) {
    if (key == 0L) {
      if (!this.hasZeroValue)
        return null; 
      this.hasZeroValue = false;
      V v = this.zeroValue;
      this.zeroValue = null;
      this.size--;
      return v;
    } 
    int i = locateKey(key);
    if (i < 0)
      return null; 
    long[] keyTable = this.keyTable;
    V[] valueTable = this.valueTable;
    V oldValue = valueTable[i];
    int mask = this.mask, next = i + 1 & mask;
    while ((key = keyTable[next]) != 0L) {
      int placement = place(key);
      if ((next - placement & mask) > (i - placement & mask)) {
        keyTable[i] = key;
        valueTable[i] = valueTable[next];
        i = next;
      } 
      next = next + 1 & mask;
    } 
    keyTable[i] = 0L;
    valueTable[i] = null;
    this.size--;
    return oldValue;
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
    int tableSize = ObjectSet.tableSize(maximumCapacity, this.loadFactor);
    if (this.keyTable.length > tableSize)
      resize(tableSize); 
  }
  
  public void clear(int maximumCapacity) {
    int tableSize = ObjectSet.tableSize(maximumCapacity, this.loadFactor);
    if (this.keyTable.length <= tableSize) {
      clear();
      return;
    } 
    this.size = 0;
    this.hasZeroValue = false;
    this.zeroValue = null;
    resize(tableSize);
  }
  
  public void clear() {
    if (this.size == 0)
      return; 
    this.size = 0;
    Arrays.fill(this.keyTable, 0L);
    Arrays.fill(this.valueTable, null);
    this.zeroValue = null;
    this.hasZeroValue = false;
  }
  
  public boolean containsValue(@Null Object value, boolean identity) {
    V[] valueTable = this.valueTable;
    if (value == null) {
      if (this.hasZeroValue && this.zeroValue == null)
        return true; 
      long[] keyTable = this.keyTable;
      for (int i = valueTable.length - 1; i >= 0; i--) {
        if (keyTable[i] != 0L && valueTable[i] == null)
          return true; 
      } 
    } else if (identity) {
      if (value == this.zeroValue)
        return true; 
      for (int i = valueTable.length - 1; i >= 0; i--) {
        if (valueTable[i] == value)
          return true; 
      } 
    } else {
      if (this.hasZeroValue && value.equals(this.zeroValue))
        return true; 
      for (int i = valueTable.length - 1; i >= 0; i--) {
        if (value.equals(valueTable[i]))
          return true; 
      } 
    } 
    return false;
  }
  
  public boolean containsKey(long key) {
    if (key == 0L)
      return this.hasZeroValue; 
    return (locateKey(key) >= 0);
  }
  
  public long findKey(@Null Object value, boolean identity, long notFound) {
    V[] valueTable = this.valueTable;
    if (value == null) {
      if (this.hasZeroValue && this.zeroValue == null)
        return 0L; 
      long[] keyTable = this.keyTable;
      for (int i = valueTable.length - 1; i >= 0; i--) {
        if (keyTable[i] != 0L && valueTable[i] == null)
          return keyTable[i]; 
      } 
    } else if (identity) {
      if (value == this.zeroValue)
        return 0L; 
      for (int i = valueTable.length - 1; i >= 0; i--) {
        if (valueTable[i] == value)
          return this.keyTable[i]; 
      } 
    } else {
      if (this.hasZeroValue && value.equals(this.zeroValue))
        return 0L; 
      for (int i = valueTable.length - 1; i >= 0; i--) {
        if (value.equals(valueTable[i]))
          return this.keyTable[i]; 
      } 
    } 
    return notFound;
  }
  
  public void ensureCapacity(int additionalCapacity) {
    int tableSize = ObjectSet.tableSize(this.size + additionalCapacity, this.loadFactor);
    if (this.keyTable.length < tableSize)
      resize(tableSize); 
  }
  
  private void resize(int newSize) {
    int oldCapacity = this.keyTable.length;
    this.threshold = (int)((float)newSize * this.loadFactor);
    this.mask = newSize - 1;
    this.shift = Long.numberOfLeadingZeros((long)this.mask);
    long[] oldKeyTable = this.keyTable;
    V[] oldValueTable = this.valueTable;
    this.keyTable = new long[newSize];
    this.valueTable = (V[])new Object[newSize];
    if (this.size > 0)
      for (int i = 0; i < oldCapacity; i++) {
        long key = oldKeyTable[i];
        if (key != 0L)
          putResize(key, oldValueTable[i]); 
      }  
  }
  
  public int hashCode() {
    int h = this.size;
    if (this.hasZeroValue && this.zeroValue != null)
      h += this.zeroValue.hashCode(); 
    long[] keyTable = this.keyTable;
    V[] valueTable = this.valueTable;
    for (int i = 0, n = keyTable.length; i < n; i++) {
      long key = keyTable[i];
      if (key != 0L) {
        h = (int)((long)h + key * 31L);
        V value = valueTable[i];
        if (value != null)
          h += value.hashCode(); 
      } 
    } 
    return h;
  }
  
  public boolean equals(Object obj) {
    if (obj == this)
      return true; 
    if (!(obj instanceof LongMap))
      return false; 
    LongMap other = (LongMap)obj;
    if (other.size != this.size)
      return false; 
    if (other.hasZeroValue != this.hasZeroValue)
      return false; 
    if (this.hasZeroValue)
      if (other.zeroValue == null) {
        if (this.zeroValue != null)
          return false; 
      } else if (!other.zeroValue.equals(this.zeroValue)) {
        return false;
      }  
    long[] keyTable = this.keyTable;
    V[] valueTable = this.valueTable;
    for (int i = 0, n = keyTable.length; i < n; i++) {
      long key = keyTable[i];
      if (key != 0L) {
        V value = valueTable[i];
        if (value == null) {
          if (other.get(key, (V)ObjectMap.dummy) != null)
            return false; 
        } else if (!value.equals(other.get(key))) {
          return false;
        } 
      } 
    } 
    return true;
  }
  
  public boolean equalsIdentity(@Null Object obj) {
    if (obj == this)
      return true; 
    if (!(obj instanceof LongMap))
      return false; 
    LongMap other = (LongMap)obj;
    if (other.size != this.size)
      return false; 
    if (other.hasZeroValue != this.hasZeroValue)
      return false; 
    if (this.hasZeroValue && this.zeroValue != other.zeroValue)
      return false; 
    long[] keyTable = this.keyTable;
    V[] valueTable = this.valueTable;
    for (int i = 0, n = keyTable.length; i < n; i++) {
      long key = keyTable[i];
      if (key != 0L && valueTable[i] != other.get(key, (V)ObjectMap.dummy))
        return false; 
    } 
    return true;
  }
  
  public String toString() {
    if (this.size == 0)
      return "[]"; 
    java.lang.StringBuilder buffer = new java.lang.StringBuilder(32);
    buffer.append('[');
    long[] keyTable = this.keyTable;
    V[] valueTable = this.valueTable;
    int i = keyTable.length;
    if (this.hasZeroValue) {
      buffer.append("0=");
      buffer.append(this.zeroValue);
    } else
      while (i-- > 0) {
        long key = keyTable[i];
        if (key == 0L)
          continue; 
        buffer.append(key);
        buffer.append('=');
        buffer.append(valueTable[i]);
        break;
      }  
    while (i-- > 0) {
      long key = keyTable[i];
      if (key == 0L)
        continue; 
      buffer.append(", ");
      buffer.append(key);
      buffer.append('=');
      buffer.append(valueTable[i]);
    } 
    buffer.append(']');
    return buffer.toString();
  }
  
  public Iterator<Entry<V>> iterator() {
    return entries();
  }
  
  public Entries<V> entries() {
    if (Collections.allocateIterators)
      return new Entries<>(this); 
    if (this.entries1 == null) {
      this.entries1 = new Entries(this);
      this.entries2 = new Entries(this);
    } 
    if (!this.entries1.valid) {
      this.entries1.reset();
      this.entries1.valid = true;
      this.entries2.valid = false;
      return this.entries1;
    } 
    this.entries2.reset();
    this.entries2.valid = true;
    this.entries1.valid = false;
    return this.entries2;
  }
  
  public Values<V> values() {
    if (Collections.allocateIterators)
      return new Values<>(this); 
    if (this.values1 == null) {
      this.values1 = new Values<>(this);
      this.values2 = new Values<>(this);
    } 
    if (!this.values1.valid) {
      this.values1.reset();
      this.values1.valid = true;
      this.values2.valid = false;
      return this.values1;
    } 
    this.values2.reset();
    this.values2.valid = true;
    this.values1.valid = false;
    return this.values2;
  }
  
  public Keys keys() {
    if (Collections.allocateIterators)
      return new Keys(this); 
    if (this.keys1 == null) {
      this.keys1 = new Keys(this);
      this.keys2 = new Keys(this);
    } 
    if (!this.keys1.valid) {
      this.keys1.reset();
      this.keys1.valid = true;
      this.keys2.valid = false;
      return this.keys1;
    } 
    this.keys2.reset();
    this.keys2.valid = true;
    this.keys1.valid = false;
    return this.keys2;
  }
  
  public static class Entry<V> {
    public long key;
    
    @Null
    public V value;
    
    public String toString() {
      return this.key + "=" + this.value;
    }
  }
  
  private static class MapIterator<V> {
    private static final int INDEX_ILLEGAL = -2;
    
    static final int INDEX_ZERO = -1;
    
    public boolean hasNext;
    
    final LongMap<V> map;
    
    int nextIndex;
    
    int currentIndex;
    
    boolean valid = true;
    
    public MapIterator(LongMap<V> map) {
      this.map = map;
      reset();
    }
    
    public void reset() {
      this.currentIndex = -2;
      this.nextIndex = -1;
      if (this.map.hasZeroValue) {
        this.hasNext = true;
      } else
        findNextIndex(); 
    }
    
    void findNextIndex() {
      long[] keyTable = this.map.keyTable;
      for (int n = keyTable.length; ++this.nextIndex < n;) {
        if (keyTable[this.nextIndex] != 0L) {
          this.hasNext = true;
          return;
        } 
      } 
      this.hasNext = false;
    }
    
    public void remove() {
      int i = this.currentIndex;
      if (i == -1 && this.map.hasZeroValue) {
        this.map.hasZeroValue = false;
        this.map.zeroValue = null;
      } else {
        if (i < 0)
          throw new IllegalStateException("next must be called before remove."); 
        long[] keyTable = this.map.keyTable;
        V[] valueTable = this.map.valueTable;
        int mask = this.map.mask, next = i + 1 & mask;
        long key;
        while ((key = keyTable[next]) != 0L) {
          int placement = this.map.place(key);
          if ((next - placement & mask) > (i - placement & mask)) {
            keyTable[i] = key;
            valueTable[i] = valueTable[next];
            i = next;
          } 
          next = next + 1 & mask;
        } 
        keyTable[i] = 0L;
        valueTable[i] = null;
        if (i != this.currentIndex)
          this.nextIndex--; 
      } 
      this.currentIndex = -2;
      this.map.size--;
    }
  }
  
  public static class Entries<V> extends MapIterator<V> implements Iterable<Entry<V>>, Iterator<Entry<V>> {
    private final LongMap.Entry<V> entry = new LongMap.Entry<>();
    
    public Entries(LongMap map) {
      super(map);
    }
    
    public LongMap.Entry<V> next() {
      if (!this.hasNext)
        throw new NoSuchElementException(); 
      if (!this.valid)
        throw new GdxRuntimeException("#iterator() cannot be used nested."); 
      long[] keyTable = this.map.keyTable;
      if (this.nextIndex == -1) {
        this.entry.key = 0L;
        this.entry.value = this.map.zeroValue;
      } else {
        this.entry.key = keyTable[this.nextIndex];
        this.entry.value = this.map.valueTable[this.nextIndex];
      } 
      this.currentIndex = this.nextIndex;
      findNextIndex();
      return this.entry;
    }
    
    public boolean hasNext() {
      if (!this.valid)
        throw new GdxRuntimeException("#iterator() cannot be used nested."); 
      return this.hasNext;
    }
    
    public Iterator<LongMap.Entry<V>> iterator() {
      return this;
    }
  }
  
  public static class Values<V> extends MapIterator<V> implements Iterable<V>, Iterator<V> {
    public Values(LongMap<V> map) {
      super(map);
    }
    
    public boolean hasNext() {
      if (!this.valid)
        throw new GdxRuntimeException("#iterator() cannot be used nested."); 
      return this.hasNext;
    }
    
    @Null
    public V next() {
      V value;
      if (!this.hasNext)
        throw new NoSuchElementException(); 
      if (!this.valid)
        throw new GdxRuntimeException("#iterator() cannot be used nested."); 
      if (this.nextIndex == -1) {
        value = this.map.zeroValue;
      } else
        value = this.map.valueTable[this.nextIndex]; 
      this.currentIndex = this.nextIndex;
      findNextIndex();
      return value;
    }
    
    public Iterator<V> iterator() {
      return this;
    }
    
    public Array<V> toArray() {
      Array<V> array = new Array(true, this.map.size);
      while (this.hasNext)
        array.add(next()); 
      return array;
    }
  }
  
  public static class Keys extends MapIterator {
    public Keys(LongMap map) {
      super(map);
    }
    
    public long next() {
      if (!this.hasNext)
        throw new NoSuchElementException(); 
      if (!this.valid)
        throw new GdxRuntimeException("#iterator() cannot be used nested."); 
      long key = (this.nextIndex == -1) ? 0L : this.map.keyTable[this.nextIndex];
      this.currentIndex = this.nextIndex;
      findNextIndex();
      return key;
    }
    
    public LongArray toArray() {
      LongArray array = new LongArray(true, this.map.size);
      while (this.hasNext)
        array.add(next()); 
      return array;
    }
    
    public LongArray toArray(LongArray array) {
      while (this.hasNext)
        array.add(next()); 
      return array;
    }
  }
}
