package com.badlogic.gdx.graphics.g3d.utils;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.NodeAnimation;
import com.badlogic.gdx.graphics.g3d.model.NodeKeyframe;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Pool;

public class BaseAnimationController {
  public static final class Transform implements Pool.Poolable {
    public final Vector3 translation = new Vector3();
    
    public final Quaternion rotation = new Quaternion();
    
    public final Vector3 scale = new Vector3(1.0F, 1.0F, 1.0F);
    
    public Transform idt() {
      this.translation.set(0.0F, 0.0F, 0.0F);
      this.rotation.idt();
      this.scale.set(1.0F, 1.0F, 1.0F);
      return this;
    }
    
    public Transform set(Vector3 t, Quaternion r, Vector3 s) {
      this.translation.set(t);
      this.rotation.set(r);
      this.scale.set(s);
      return this;
    }
    
    public Transform set(Transform other) {
      return set(other.translation, other.rotation, other.scale);
    }
    
    public Transform lerp(Transform target, float alpha) {
      return lerp(target.translation, target.rotation, target.scale, alpha);
    }
    
    public Transform lerp(Vector3 targetT, Quaternion targetR, Vector3 targetS, float alpha) {
      this.translation.lerp(targetT, alpha);
      this.rotation.slerp(targetR, alpha);
      this.scale.lerp(targetS, alpha);
      return this;
    }
    
    public Matrix4 toMatrix4(Matrix4 out) {
      return out.set(this.translation, this.rotation, this.scale);
    }
    
    public void reset() {
      idt();
    }
    
    public String toString() {
      return this.translation.toString() + " - " + this.rotation.toString() + " - " + this.scale.toString();
    }
  }
  
  private final Pool<Transform> transformPool = new Pool<Transform>() {
      protected BaseAnimationController.Transform newObject() {
        return new BaseAnimationController.Transform();
      }
    };
  
  private static final ObjectMap<Node, Transform> transforms = new ObjectMap<>();
  
  private boolean applying = false;
  
  public final ModelInstance target;
  
  public BaseAnimationController(ModelInstance target) {
    this.target = target;
  }
  
  protected void begin() {
    if (this.applying)
      throw new GdxRuntimeException("You must call end() after each call to being()"); 
    this.applying = true;
  }
  
  protected void apply(Animation animation, float time, float weight) {
    if (!this.applying)
      throw new GdxRuntimeException("You must call begin() before adding an animation"); 
    applyAnimation(transforms, this.transformPool, weight, animation, time);
  }
  
  protected void end() {
    if (!this.applying)
      throw new GdxRuntimeException("You must call begin() first"); 
    for (ObjectMap.Entries<ObjectMap.Entry<Node, Transform>> entries = transforms.entries().iterator(); entries.hasNext(); ) {
      ObjectMap.Entry<Node, Transform> entry = entries.next();
      ((Transform)entry.value).toMatrix4(((Node)entry.key).localTransform);
      this.transformPool.free((Transform)entry.value);
    } 
    transforms.clear();
    this.target.calculateTransforms();
    this.applying = false;
  }
  
  protected void applyAnimation(Animation animation, float time) {
    if (this.applying)
      throw new GdxRuntimeException("Call end() first"); 
    applyAnimation(null, null, 1.0F, animation, time);
    this.target.calculateTransforms();
  }
  
  protected void applyAnimations(Animation anim1, float time1, Animation anim2, float time2, float weight) {
    if (anim2 == null || weight == 0.0F) {
      applyAnimation(anim1, time1);
    } else if (anim1 == null || weight == 1.0F) {
      applyAnimation(anim2, time2);
    } else {
      if (this.applying)
        throw new GdxRuntimeException("Call end() first"); 
      begin();
      apply(anim1, time1, 1.0F);
      apply(anim2, time2, weight);
      end();
    } 
  }
  
  private static final Transform tmpT = new Transform();
  
  static final <T> int getFirstKeyframeIndexAtTime(Array<NodeKeyframe<T>> arr, float time) {
    int lastIndex = arr.size - 1;
    if (lastIndex <= 0 || time < ((NodeKeyframe)arr.get(0)).keytime || time > ((NodeKeyframe)arr.get(lastIndex)).keytime)
      return 0; 
    int minIndex = 0;
    int maxIndex = lastIndex;
    while (minIndex < maxIndex) {
      int i = (minIndex + maxIndex) / 2;
      if (time > ((NodeKeyframe)arr.get(i + 1)).keytime) {
        minIndex = i + 1;
        continue;
      } 
      if (time < ((NodeKeyframe)arr.get(i)).keytime) {
        maxIndex = i - 1;
        continue;
      } 
      return i;
    } 
    return minIndex;
  }
  
  private static final Vector3 getTranslationAtTime(NodeAnimation nodeAnim, float time, Vector3 out) {
    if (nodeAnim.translation == null)
      return out.set(nodeAnim.node.translation); 
    if (nodeAnim.translation.size == 1)
      return out.set((Vector3)((NodeKeyframe)nodeAnim.translation.get(0)).value); 
    int index = BaseAnimationController.<Vector3>getFirstKeyframeIndexAtTime(nodeAnim.translation, time);
    NodeKeyframe firstKeyframe = nodeAnim.translation.get(index);
    out.set((Vector3)firstKeyframe.value);
    if (++index < nodeAnim.translation.size) {
      NodeKeyframe<Vector3> secondKeyframe = nodeAnim.translation.get(index);
      float t = (time - firstKeyframe.keytime) / (secondKeyframe.keytime - firstKeyframe.keytime);
      out.lerp((Vector3)secondKeyframe.value, t);
    } 
    return out;
  }
  
  private static final Quaternion getRotationAtTime(NodeAnimation nodeAnim, float time, Quaternion out) {
    if (nodeAnim.rotation == null)
      return out.set(nodeAnim.node.rotation); 
    if (nodeAnim.rotation.size == 1)
      return out.set((Quaternion)((NodeKeyframe)nodeAnim.rotation.get(0)).value); 
    int index = BaseAnimationController.<Quaternion>getFirstKeyframeIndexAtTime(nodeAnim.rotation, time);
    NodeKeyframe firstKeyframe = nodeAnim.rotation.get(index);
    out.set((Quaternion)firstKeyframe.value);
    if (++index < nodeAnim.rotation.size) {
      NodeKeyframe<Quaternion> secondKeyframe = nodeAnim.rotation.get(index);
      float t = (time - firstKeyframe.keytime) / (secondKeyframe.keytime - firstKeyframe.keytime);
      out.slerp((Quaternion)secondKeyframe.value, t);
    } 
    return out;
  }
  
  private static final Vector3 getScalingAtTime(NodeAnimation nodeAnim, float time, Vector3 out) {
    if (nodeAnim.scaling == null)
      return out.set(nodeAnim.node.scale); 
    if (nodeAnim.scaling.size == 1)
      return out.set((Vector3)((NodeKeyframe)nodeAnim.scaling.get(0)).value); 
    int index = BaseAnimationController.<Vector3>getFirstKeyframeIndexAtTime(nodeAnim.scaling, time);
    NodeKeyframe firstKeyframe = nodeAnim.scaling.get(index);
    out.set((Vector3)firstKeyframe.value);
    if (++index < nodeAnim.scaling.size) {
      NodeKeyframe<Vector3> secondKeyframe = nodeAnim.scaling.get(index);
      float t = (time - firstKeyframe.keytime) / (secondKeyframe.keytime - firstKeyframe.keytime);
      out.lerp((Vector3)secondKeyframe.value, t);
    } 
    return out;
  }
  
  private static final Transform getNodeAnimationTransform(NodeAnimation nodeAnim, float time) {
    Transform transform = tmpT;
    getTranslationAtTime(nodeAnim, time, transform.translation);
    getRotationAtTime(nodeAnim, time, transform.rotation);
    getScalingAtTime(nodeAnim, time, transform.scale);
    return transform;
  }
  
  private static final void applyNodeAnimationDirectly(NodeAnimation nodeAnim, float time) {
    Node node = nodeAnim.node;
    node.isAnimated = true;
    Transform transform = getNodeAnimationTransform(nodeAnim, time);
    transform.toMatrix4(node.localTransform);
  }
  
  private static final void applyNodeAnimationBlending(NodeAnimation nodeAnim, ObjectMap<Node, Transform> out, Pool<Transform> pool, float alpha, float time) {
    Node node = nodeAnim.node;
    node.isAnimated = true;
    Transform transform = getNodeAnimationTransform(nodeAnim, time);
    Transform t = out.get(node, null);
    if (t != null) {
      if (alpha > 0.999999F) {
        t.set(transform);
      } else
        t.lerp(transform, alpha); 
    } else if (alpha > 0.999999F) {
      out.put(node, pool.obtain().set(transform));
    } else
      out.put(node, pool.obtain().set(node.translation, node.rotation, node.scale).lerp(transform, alpha)); 
  }
  
  protected static void applyAnimation(ObjectMap<Node, Transform> out, Pool<Transform> pool, float alpha, Animation animation, float time) {
    if (out == null) {
      for (Array.ArrayIterator<NodeAnimation> arrayIterator = animation.nodeAnimations.iterator(); arrayIterator.hasNext(); ) {
        NodeAnimation nodeAnim = arrayIterator.next();
        applyNodeAnimationDirectly(nodeAnim, time);
      } 
    } else {
      for (ObjectMap.Keys<Node> keys = out.keys().iterator(); keys.hasNext(); ) {
        Node node = keys.next();
        node.isAnimated = false;
      } 
      for (Array.ArrayIterator<NodeAnimation> arrayIterator = animation.nodeAnimations.iterator(); arrayIterator.hasNext(); ) {
        NodeAnimation nodeAnim = arrayIterator.next();
        applyNodeAnimationBlending(nodeAnim, out, pool, alpha, time);
      } 
      for (ObjectMap.Entries<ObjectMap.Entry<Node, Transform>> entries = out.entries().iterator(); entries.hasNext(); ) {
        ObjectMap.Entry<Node, Transform> e = entries.next();
        if (!((Node)e.key).isAnimated) {
          ((Node)e.key).isAnimated = true;
          ((Transform)e.value).lerp(((Node)e.key).translation, ((Node)e.key).rotation, ((Node)e.key).scale, alpha);
        } 
      } 
    } 
  }
  
  protected void removeAnimation(Animation animation) {
    for (Array.ArrayIterator<NodeAnimation> arrayIterator = animation.nodeAnimations.iterator(); arrayIterator.hasNext(); ) {
      NodeAnimation nodeAnim = arrayIterator.next();
      nodeAnim.node.isAnimated = false;
    } 
  }
}
