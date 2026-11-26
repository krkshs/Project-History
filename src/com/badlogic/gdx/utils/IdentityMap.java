package com.badlogic.gdx.utils;

public class IdentityMap<K, V> extends ObjectMap<K, V> {
  public IdentityMap() {}
  
  public IdentityMap(int initialCapacity) {
    super(initialCapacity);
  }
  
  public IdentityMap(int initialCapacity, float loadFactor) {
    super(initialCapacity, loadFactor);
  }
  
  public IdentityMap(IdentityMap<K, V> map) {
    super(map);
  }
  
  protected int place(K item) {
    return (int)((long)System.identityHashCode(item) * -7046029254386353131L >>> this.shift);
  }
  
  int locateKey(K key) {
    if (key == null)
      throw new IllegalArgumentException("key cannot be null."); 
    K[] keyTable = this.keyTable;
    for (int i = place(key);; i = i + 1 & this.mask) {
      K other = keyTable[i];
      if (other == null)
        return -(i + 1); 
      if (other == key)
        return i; 
    } 
  }
  
  public int hashCode() {
    int h = this.size;
    K[] keyTable = this.keyTable;
    V[] valueTable = this.valueTable;
    for (int i = 0, n = keyTable.length; i < n; i++) {
      K key = keyTable[i];
      if (key != null) {
        h += System.identityHashCode(key);
        V value = valueTable[i];
        if (value != null)
          h += value.hashCode(); 
      } 
    } 
    return h;
  }
}
