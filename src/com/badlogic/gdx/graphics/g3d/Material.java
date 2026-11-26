package com.badlogic.gdx.graphics.g3d;

import com.badlogic.gdx.utils.Array;

public class Material extends Attributes {
  private static int counter = 0;
  
  public String id;
  
  public Material() {
    this("mtl" + ++counter);
  }
  
  public Material(String id) {
    this.id = id;
  }
  
  public Material(Attribute... attributes) {
    this();
    set(attributes);
  }
  
  public Material(String id, Attribute... attributes) {
    this(id);
    set(attributes);
  }
  
  public Material(Array<Attribute> attributes) {
    this();
    set(attributes);
  }
  
  public Material(String id, Array<Attribute> attributes) {
    this(id);
    set(attributes);
  }
  
  public Material(Material copyFrom) {
    this(copyFrom.id, copyFrom);
  }
  
  public Material(String id, Material copyFrom) {
    this(id);
    for (Attribute attr : (Iterable<Attribute>)copyFrom)
      set(attr.copy()); 
  }
  
  public Material copy() {
    return new Material(this);
  }
  
  public int hashCode() {
    return super.hashCode() + 3 * this.id.hashCode();
  }
  
  public boolean equals(Object other) {
    return (other instanceof Material && (other == this || (((Material)other).id.equals(this.id) && super.equals(other))));
  }
}
