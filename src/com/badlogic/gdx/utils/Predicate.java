package com.badlogic.gdx.utils;

import java.util.Iterator;

public interface Predicate<T> {
  boolean evaluate(T paramT);
  
  public static class PredicateIterator<T> implements Iterator<T> {
    public Iterator<T> iterator;
    
    public Predicate<T> predicate;
    
    public boolean end = false;
    
    public boolean peeked = false;
    
    public T next = null;
    
    public PredicateIterator(Iterable<T> iterable, Predicate<T> predicate) {
      this(iterable.iterator(), predicate);
    }
    
    public PredicateIterator(Iterator<T> iterator, Predicate<T> predicate) {
      set(iterator, predicate);
    }
    
    public void set(Iterable<T> iterable, Predicate<T> predicate) {
      set(iterable.iterator(), predicate);
    }
    
    public void set(Iterator<T> iterator, Predicate<T> predicate) {
      this.iterator = iterator;
      this.predicate = predicate;
      this.end = this.peeked = false;
      this.next = null;
    }
    
    public boolean hasNext() {
      if (this.end)
        return false; 
      if (this.next != null)
        return true; 
      this.peeked = true;
      while (this.iterator.hasNext()) {
        T n = this.iterator.next();
        if (this.predicate.evaluate(n)) {
          this.next = n;
          return true;
        } 
      } 
      this.end = true;
      return false;
    }
    
    public T next() {
      if (this.next == null && !hasNext())
        return null; 
      T result = this.next;
      this.next = null;
      this.peeked = false;
      return result;
    }
    
    public void remove() {
      if (this.peeked)
        throw new GdxRuntimeException("Cannot remove between a call to hasNext() and next()."); 
      this.iterator.remove();
    }
  }
  
  public static class PredicateIterable<T> implements Iterable<T> {
    public Iterable<T> iterable;
    
    public Predicate<T> predicate;
    
    public Predicate.PredicateIterator<T> iterator = null;
    
    public PredicateIterable(Iterable<T> iterable, Predicate<T> predicate) {
      set(iterable, predicate);
    }
    
    public void set(Iterable<T> iterable, Predicate<T> predicate) {
      this.iterable = iterable;
      this.predicate = predicate;
    }
    
    public Iterator<T> iterator() {
      if (Collections.allocateIterators)
        return new Predicate.PredicateIterator<>(this.iterable.iterator(), this.predicate); 
      if (this.iterator == null) {
        this.iterator = new Predicate.PredicateIterator<>(this.iterable.iterator(), this.predicate);
      } else
        this.iterator.set(this.iterable.iterator(), this.predicate); 
      return this.iterator;
    }
  }
}
