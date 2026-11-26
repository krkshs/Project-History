package com.badlogic.gdx.graphics.g3d.model;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class Node {
  public String id;
  
  public boolean inheritTransform = true;
  
  public boolean isAnimated;
  
  public final Vector3 translation = new Vector3();
  
  public final Quaternion rotation = new Quaternion(0.0F, 0.0F, 0.0F, 1.0F);
  
  public final Vector3 scale = new Vector3(1.0F, 1.0F, 1.0F);
  
  public final Matrix4 localTransform = new Matrix4();
  
  public final Matrix4 globalTransform = new Matrix4();
  
  public Array<NodePart> parts = new Array<>(2);
  
  protected Node parent;
  
  private final Array<Node> children = new Array<>(2);
  
  public Matrix4 calculateLocalTransform() {
    if (!this.isAnimated)
      this.localTransform.set(this.translation, this.rotation, this.scale); 
    return this.localTransform;
  }
  
  public Matrix4 calculateWorldTransform() {
    if (this.inheritTransform && this.parent != null) {
      this.globalTransform.set(this.parent.globalTransform).mul(this.localTransform);
    } else
      this.globalTransform.set(this.localTransform); 
    return this.globalTransform;
  }
  
  public void calculateTransforms(boolean recursive) {
    calculateLocalTransform();
    calculateWorldTransform();
    if (recursive)
      for (Array.ArrayIterator<Node> arrayIterator = this.children.iterator(); arrayIterator.hasNext(); ) {
        Node child = arrayIterator.next();
        child.calculateTransforms(true);
      }  
  }
  
  public void calculateBoneTransforms(boolean recursive) {
    for (Array.ArrayIterator<NodePart> arrayIterator = this.parts.iterator(); arrayIterator.hasNext(); ) {
      NodePart part = arrayIterator.next();
      if (part.invBoneBindTransforms == null || part.bones == null || part.invBoneBindTransforms.size != part.bones.length)
        continue; 
      int n = part.invBoneBindTransforms.size;
      for (int i = 0; i < n; i++)
        part.bones[i].set((((Node[])part.invBoneBindTransforms.keys)[i]).globalTransform).mul(((Matrix4[])part.invBoneBindTransforms.values)[i]); 
    } 
    if (recursive)
      for (Array.ArrayIterator<NodePart> arrayIterator1 = (Array.ArrayIterator)this.children.iterator(); arrayIterator1.hasNext(); ) {
        Node child = (Node)arrayIterator1.next();
        child.calculateBoneTransforms(true);
      }  
  }
  
  public BoundingBox calculateBoundingBox(BoundingBox out) {
    out.inf();
    return extendBoundingBox(out);
  }
  
  public BoundingBox calculateBoundingBox(BoundingBox out, boolean transform) {
    out.inf();
    return extendBoundingBox(out, transform);
  }
  
  public BoundingBox extendBoundingBox(BoundingBox out) {
    return extendBoundingBox(out, true);
  }
  
  public BoundingBox extendBoundingBox(BoundingBox out, boolean transform) {
    int partCount = this.parts.size;
    for (int i = 0; i < partCount; i++) {
      NodePart part = this.parts.get(i);
      if (part.enabled) {
        MeshPart meshPart = part.meshPart;
        if (transform) {
          meshPart.mesh.extendBoundingBox(out, meshPart.offset, meshPart.size, this.globalTransform);
        } else
          meshPart.mesh.extendBoundingBox(out, meshPart.offset, meshPart.size); 
      } 
    } 
    int childCount = this.children.size;
    for (int j = 0; j < childCount; j++)
      this.children.get(j).extendBoundingBox(out); 
    return out;
  }
  
  public <T extends Node> void attachTo(T parent) {
    parent.addChild(this);
  }
  
  public void detach() {
    if (this.parent != null) {
      this.parent.removeChild(this);
      this.parent = null;
    } 
  }
  
  public boolean hasChildren() {
    return (this.children != null && this.children.size > 0);
  }
  
  public int getChildCount() {
    return this.children.size;
  }
  
  public Node getChild(int index) {
    return this.children.get(index);
  }
  
  public Node getChild(String id, boolean recursive, boolean ignoreCase) {
    return getNode(this.children, id, recursive, ignoreCase);
  }
  
  public <T extends Node> int addChild(T child) {
    return insertChild(-1, child);
  }
  
  public <T extends Node> int addChildren(Iterable<T> nodes) {
    return insertChildren(-1, nodes);
  }
  
  public <T extends Node> int insertChild(int index, T child) {
    Node p;
    for (p = this; p != null; p = p.getParent()) {
      if (p == child)
        throw new GdxRuntimeException("Cannot add a parent as a child"); 
    } 
    p = child.getParent();
    if (p != null && !p.<T>removeChild(child))
      throw new GdxRuntimeException("Could not remove child from its current parent"); 
    if (index < 0 || index >= this.children.size) {
      index = this.children.size;
      this.children.add(child);
    } else
      this.children.insert(index, child); 
    child.parent = this;
    return index;
  }
  
  public <T extends Node> int insertChildren(int index, Iterable<T> nodes) {
    if (index < 0 || index > this.children.size)
      index = this.children.size; 
    int i = index;
    for (T child : (Iterable<T>)nodes)
      insertChild(i++, child); 
    return index;
  }
  
  public <T extends Node> boolean removeChild(T child) {
    if (!this.children.removeValue(child, true))
      return false; 
    child.parent = null;
    return true;
  }
  
  public Iterable<Node> getChildren() {
    return this.children;
  }
  
  public Node getParent() {
    return this.parent;
  }
  
  public boolean hasParent() {
    return (this.parent != null);
  }
  
  public Node copy() {
    return new Node().set(this);
  }
  
  protected Node set(Node other) {
    detach();
    this.id = other.id;
    this.isAnimated = other.isAnimated;
    this.inheritTransform = other.inheritTransform;
    this.translation.set(other.translation);
    this.rotation.set(other.rotation);
    this.scale.set(other.scale);
    this.localTransform.set(other.localTransform);
    this.globalTransform.set(other.globalTransform);
    this.parts.clear();
    for (Array.ArrayIterator<NodePart> arrayIterator = other.parts.iterator(); arrayIterator.hasNext(); ) {
      NodePart nodePart = arrayIterator.next();
      this.parts.add(nodePart.copy());
    } 
    this.children.clear();
    for (Node child : other.getChildren())
      addChild(child.copy()); 
    return this;
  }
  
  public static Node getNode(Array<Node> nodes, String id, boolean recursive, boolean ignoreCase) {
    int n = nodes.size;
    if (ignoreCase) {
      for (int i = 0; i < n; i++) {
        Node node;
        if ((node = nodes.get(i)).id.equalsIgnoreCase(id))
          return node; 
      } 
    } else
      for (int i = 0; i < n; i++) {
        Node node;
        if ((node = nodes.get(i)).id.equals(id))
          return node; 
      }  
    if (recursive)
      for (int i = 0; i < n; i++) {
        Node node;
        if ((node = getNode(((Node)nodes.get(i)).children, id, true, ignoreCase)) != null)
          return node; 
      }  
    return null;
  }
}
