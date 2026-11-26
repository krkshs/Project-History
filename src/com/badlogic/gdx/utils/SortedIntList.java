package com.badlogic.gdx.utils;

public class SortedIntList<E> implements Iterable<SortedIntList.Node<E>> {
  private NodePool<E> nodePool = new NodePool<>();
  
  private transient Iterator iterator;
  
  int size = 0;
  
  Node<E> first;
  
  @Null
  public E insert(int index, E value) {
    if (this.first != null) {
      Node<E> c = this.first;
      while (c.n != null && c.n.index <= index)
        c = c.n; 
      if (index > c.index) {
        c.n = this.nodePool.obtain(c, c.n, value, index);
        if (c.n.n != null)
          c.n.n.p = c.n; 
        this.size++;
      } else if (index < c.index) {
        Node<E> newFirst = this.nodePool.obtain(null, this.first, value, index);
        this.first.p = newFirst;
        this.first = newFirst;
        this.size++;
      } else
        c.value = value; 
    } else {
      this.first = this.nodePool.obtain(null, null, value, index);
      this.size++;
    } 
    return null;
  }
  
  public E get(int index) {
    E match = null;
    if (this.first != null) {
      Node<E> c = this.first;
      while (c.n != null && c.index < index)
        c = c.n; 
      if (c.index == index)
        match = c.value; 
    } 
    return match;
  }
  
  public void clear() {
    for (; this.first != null; this.first = this.first.n)
      this.nodePool.free(this.first); 
    this.size = 0;
  }
  
  public int size() {
    return this.size;
  }
  
  public boolean notEmpty() {
    return (this.size > 0);
  }
  
  public boolean isEmpty() {
    return (this.size == 0);
  }
  
  public java.util.Iterator<Node<E>> iterator() {
    if (Collections.allocateIterators)
      return new Iterator(); 
    if (this.iterator == null)
      this.iterator = new Iterator(); 
    return this.iterator.reset();
  }
  
  public class Iterator implements java.util.Iterator<Node<E>> {
    private SortedIntList.Node<E> position;
    
    private SortedIntList.Node<E> previousPosition;
    
    public boolean hasNext() {
      return (this.position != null);
    }
    
    public SortedIntList.Node<E> next() {
      this.previousPosition = this.position;
      this.position = this.position.n;
      return this.previousPosition;
    }
    
    public void remove() {
      if (this.previousPosition != null) {
        if (this.previousPosition == SortedIntList.this.first) {
          SortedIntList.this.first = this.position;
        } else {
          this.previousPosition.p.n = this.position;
          if (this.position != null)
            this.position.p = this.previousPosition.p; 
        } 
        SortedIntList.this.size--;
      } 
    }
    
    public Iterator reset() {
      this.position = SortedIntList.this.first;
      this.previousPosition = null;
      return this;
    }
  }
  
  public static class Node<E> {
    protected Node<E> p;
    
    protected Node<E> n;
    
    public E value;
    
    public int index;
  }
  
  static class NodePool<E> extends Pool<Node<E>> {
    protected SortedIntList.Node<E> newObject() {
      return new SortedIntList.Node<>();
    }
    
    public SortedIntList.Node<E> obtain(SortedIntList.Node<E> p, SortedIntList.Node<E> n, E value, int index) {
      SortedIntList.Node<E> newNode = (SortedIntList.Node<E>)obtain();
      newNode.p = p;
      newNode.n = n;
      newNode.value = value;
      newNode.index = index;
      return newNode;
    }
  }
}
