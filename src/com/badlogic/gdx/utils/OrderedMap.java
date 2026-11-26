package com.badlogic.gdx.utils;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class OrderedMap<K, V> extends ObjectMap<K, V> {
  final Array<K> keys;
  
  public OrderedMap() {
    this.keys = new Array<>();
  }
  
  public OrderedMap(int initialCapacity) {
    super(initialCapacity);
    this.keys = new Array<>(initialCapacity);
  }
  
  public OrderedMap(int initialCapacity, float loadFactor) {
    super(initialCapacity, loadFactor);
    this.keys = new Array<>(initialCapacity);
  }
  
  public OrderedMap(OrderedMap<? extends K, ? extends V> map) {
    super(map);
    this.keys = new Array<>(map.keys);
  }
  
  public V put(K key, V value) {
    int i = locateKey(key);
    if (i >= 0) {
      V oldValue = this.valueTable[i];
      this.valueTable[i] = value;
      return oldValue;
    } 
    i = -(i + 1);
    this.keyTable[i] = key;
    this.valueTable[i] = value;
    this.keys.add(key);
    if (++this.size >= this.threshold)
      resize(this.keyTable.length << 1); 
    return null;
  }
  
  public <T extends K> void putAll(OrderedMap<T, ? extends V> map) {
    ensureCapacity(map.size);
    T[] arrayOfT = map.keys.items;
    for (int i = 0, n = map.keys.size; i < n; i++) {
      T t = arrayOfT[i];
      put((K)t, map.get(t));
    } 
  }
  
  public V remove(K key) {
    this.keys.removeValue(key, false);
    return super.remove(key);
  }
  
  public V removeIndex(int index) {
    return super.remove(this.keys.removeIndex(index));
  }
  
  public boolean alter(K before, K after) {
    if (containsKey(after))
      return false; 
    int index = this.keys.indexOf(before, false);
    if (index == -1)
      return false; 
    super.put(after, super.remove(before));
    this.keys.set(index, after);
    return true;
  }
  
  public boolean alterIndex(int index, K after) {
    if (index < 0 || index >= this.size || containsKey(after))
      return false; 
    super.put(after, super.remove(this.keys.get(index)));
    this.keys.set(index, after);
    return true;
  }
  
  public void clear(int maximumCapacity) {
    this.keys.clear();
    super.clear(maximumCapacity);
  }
  
  public void clear() {
    this.keys.clear();
    super.clear();
  }
  
  public Array<K> orderedKeys() {
    return this.keys;
  }
  
  public ObjectMap.Entries<K, V> iterator() {
    return entries();
  }
  
  public ObjectMap.Entries<K, V> entries() {
    if (Collections.allocateIterators)
      return new OrderedMapEntries<>(this); 
    if (this.entries1 == null) {
      this.entries1 = new OrderedMapEntries<>(this);
      this.entries2 = new OrderedMapEntries<>(this);
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
  
  public ObjectMap.Values<V> values() {
    if (Collections.allocateIterators)
      return new OrderedMapValues<>(this); 
    if (this.values1 == null) {
      this.values1 = new OrderedMapValues<>(this);
      this.values2 = new OrderedMapValues<>(this);
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
  
  public ObjectMap.Keys<K> keys() {
    if (Collections.allocateIterators)
      return new OrderedMapKeys<>(this); 
    if (this.keys1 == null) {
      this.keys1 = new OrderedMapKeys<>(this);
      this.keys2 = new OrderedMapKeys<>(this);
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
  
  protected String toString(String separator, boolean braces) {
    if (this.size == 0)
      return braces ? "{}" : ""; 
    java.lang.StringBuilder buffer = new java.lang.StringBuilder(32);
    if (braces)
      buffer.append('{'); 
    Array<K> keys = this.keys;
    for (int i = 0, n = keys.size; i < n; i++) {
      K key = keys.get(i);
      if (i > 0)
        buffer.append(separator); 
      buffer.append((key == this) ? "(this)" : key);
      buffer.append('=');
      V value = get(key);
      buffer.append((value == this) ? "(this)" : value);
    } 
    if (braces)
      buffer.append('}'); 
    return buffer.toString();
  }
  
  public static class OrderedMapEntries<K, V> extends ObjectMap.Entries<K, V> {
    private Array<K> keys;
    
    public OrderedMapEntries(OrderedMap<K, V> map) {
      super(map);
      this.keys = map.keys;
    }
    
    public void reset() {
      this.currentIndex = -1;
      this.nextIndex = 0;
      this.hasNext = (this.map.size > 0);
    }
    
    public ObjectMap.Entry next() {
      if (!this.hasNext)
        throw new NoSuchElementException(); 
      if (!this.valid)
        throw new GdxRuntimeException("#iterator() cannot be used nested."); 
      this.currentIndex = this.nextIndex;
      this.entry.key = this.keys.get(this.nextIndex);
      this.entry.value = this.map.<K>get(this.entry.key);
      this.nextIndex++;
      this.hasNext = (this.nextIndex < this.map.size);
      return this.entry;
    }
    
    public void remove() {
      if (this.currentIndex < 0)
        throw new IllegalStateException("next must be called before remove."); 
      this.map.remove(this.entry.key);
      this.nextIndex--;
      this.currentIndex = -1;
    }
  }
  
  public static class OrderedMapKeys<K> extends ObjectMap.Keys<K> {
    private Array<K> keys;
    
    public OrderedMapKeys(OrderedMap<K, ?> map) {
      super(map);
      this.keys = map.keys;
    }
    
    public void reset() {
      this.currentIndex = -1;
      this.nextIndex = 0;
      this.hasNext = (this.map.size > 0);
    }
    
    public K next() {
      if (!this.hasNext)
        throw new NoSuchElementException(); 
      if (!this.valid)
        throw new GdxRuntimeException("#iterator() cannot be used nested."); 
      K key = this.keys.get(this.nextIndex);
      this.currentIndex = this.nextIndex;
      this.nextIndex++;
      this.hasNext = (this.nextIndex < this.map.size);
      return key;
    }
    
    public void remove() {
      if (this.currentIndex < 0)
        throw new IllegalStateException("next must be called before remove."); 
      ((OrderedMap)this.map).removeIndex(this.currentIndex);
      this.nextIndex = this.currentIndex;
      this.currentIndex = -1;
    }
    
    public Array<K> toArray(Array<K> array) {
      array.addAll(this.keys, this.nextIndex, this.keys.size - this.nextIndex);
      this.nextIndex = this.keys.size;
      this.hasNext = false;
      return array;
    }
    
    public Array<K> toArray() {
      return toArray(new Array<>(true, this.keys.size - this.nextIndex));
    }
  }
  
  public static class OrderedMapValues<V> extends ObjectMap.Values<V> {
    private Array keys;
    
    public OrderedMapValues(OrderedMap<?, V> map) {
      super(map);
      this.keys = map.keys;
    }
    
    public void reset() {
      this.currentIndex = -1;
      this.nextIndex = 0;
      this.hasNext = (this.map.size > 0);
    }
    
    public V next() {
      if (!this.hasNext)
        throw new NoSuchElementException(); 
      if (!this.valid)
        throw new GdxRuntimeException("#iterator() cannot be used nested."); 
      V value = this.map.get(this.keys.get(this.nextIndex));
      this.currentIndex = this.nextIndex;
      this.nextIndex++;
      this.hasNext = (this.nextIndex < this.map.size);
      return value;
    }
    
    public void remove() {
      if (this.currentIndex < 0)
        throw new IllegalStateException("next must be called before remove."); 
      ((OrderedMap)this.map).removeIndex(this.currentIndex);
      this.nextIndex = this.currentIndex;
      this.currentIndex = -1;
    }
    
    public Array<V> toArray(Array<V> array) {
      int n = this.keys.size;
      array.ensureCapacity(n - this.nextIndex);
      Object[] keys = this.keys.items;
      for (int i = this.nextIndex; i < n; i++)
        array.add(this.map.get(keys[i])); 
      this.currentIndex = n - 1;
      this.nextIndex = n;
      this.hasNext = false;
      return array;
    }
    
    public Array<V> toArray() {
      return toArray(new Array<>(true, this.keys.size - this.nextIndex));
    }
  }
}
