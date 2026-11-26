package com.badlogic.gdx.utils;

import java.util.Comparator;

public class DelayedRemovalArray<T> extends Array<T> {
  private int iterating;
  
  private IntArray remove = new IntArray(0);
  
  private int clear;
  
  public DelayedRemovalArray() {}
  
  public DelayedRemovalArray(Array array) {
    super(array);
  }
  
  public DelayedRemovalArray(boolean ordered, int capacity, Class arrayType) {
    super(ordered, capacity, arrayType);
  }
  
  public DelayedRemovalArray(boolean ordered, int capacity) {
    super(ordered, capacity);
  }
  
  public DelayedRemovalArray(boolean ordered, T[] array, int startIndex, int count) {
    super(ordered, array, startIndex, count);
  }
  
  public DelayedRemovalArray(Class arrayType) {
    super(arrayType);
  }
  
  public DelayedRemovalArray(int capacity) {
    super(capacity);
  }
  
  public DelayedRemovalArray(T[] array) {
    super(array);
  }
  
  public void begin() {
    this.iterating++;
  }
  
  public void end() {
    if (this.iterating == 0)
      throw new IllegalStateException("begin must be called before end."); 
    this.iterating--;
    if (this.iterating == 0) {
      if (this.clear > 0 && this.clear == this.size) {
        this.remove.clear();
        clear();
      } else {
        for (int j = 0, n = this.remove.size; j < n; j++) {
          int index = this.remove.pop();
          if (index >= this.clear)
            removeIndex(index); 
        } 
        for (int i = this.clear - 1; i >= 0; i--)
          removeIndex(i); 
      } 
      this.clear = 0;
    } 
  }
  
  private void remove(int index) {
    if (index < this.clear)
      return; 
    for (int i = 0, n = this.remove.size; i < n; i++) {
      int removeIndex = this.remove.get(i);
      if (index == removeIndex)
        return; 
      if (index < removeIndex) {
        this.remove.insert(i, index);
        return;
      } 
    } 
    this.remove.add(index);
  }
  
  public boolean removeValue(T value, boolean identity) {
    if (this.iterating > 0) {
      int index = indexOf(value, identity);
      if (index == -1)
        return false; 
      remove(index);
      return true;
    } 
    return super.removeValue(value, identity);
  }
  
  public T removeIndex(int index) {
    if (this.iterating > 0) {
      remove(index);
      return get(index);
    } 
    return super.removeIndex(index);
  }
  
  public void removeRange(int start, int end) {
    if (this.iterating > 0) {
      for (int i = end; i >= start; i--)
        remove(i); 
    } else
      super.removeRange(start, end); 
  }
  
  public void clear() {
    if (this.iterating > 0) {
      this.clear = this.size;
      return;
    } 
    super.clear();
  }
  
  public void set(int index, T value) {
    if (this.iterating > 0)
      throw new IllegalStateException("Invalid between begin/end."); 
    super.set(index, value);
  }
  
  public void insert(int index, T value) {
    if (this.iterating > 0)
      throw new IllegalStateException("Invalid between begin/end."); 
    super.insert(index, value);
  }
  
  public void insertRange(int index, int count) {
    if (this.iterating > 0)
      throw new IllegalStateException("Invalid between begin/end."); 
    super.insertRange(index, count);
  }
  
  public void swap(int first, int second) {
    if (this.iterating > 0)
      throw new IllegalStateException("Invalid between begin/end."); 
    super.swap(first, second);
  }
  
  public T pop() {
    if (this.iterating > 0)
      throw new IllegalStateException("Invalid between begin/end."); 
    return super.pop();
  }
  
  public void sort() {
    if (this.iterating > 0)
      throw new IllegalStateException("Invalid between begin/end."); 
    super.sort();
  }
  
  public void sort(Comparator<? super T> comparator) {
    if (this.iterating > 0)
      throw new IllegalStateException("Invalid between begin/end."); 
    super.sort(comparator);
  }
  
  public void reverse() {
    if (this.iterating > 0)
      throw new IllegalStateException("Invalid between begin/end."); 
    super.reverse();
  }
  
  public void shuffle() {
    if (this.iterating > 0)
      throw new IllegalStateException("Invalid between begin/end."); 
    super.shuffle();
  }
  
  public void truncate(int newSize) {
    if (this.iterating > 0)
      throw new IllegalStateException("Invalid between begin/end."); 
    super.truncate(newSize);
  }
  
  public T[] setSize(int newSize) {
    if (this.iterating > 0)
      throw new IllegalStateException("Invalid between begin/end."); 
    return super.setSize(newSize);
  }
  
  public static <T> DelayedRemovalArray<T> with(T... array) {
    return new DelayedRemovalArray<>(array);
  }
}
