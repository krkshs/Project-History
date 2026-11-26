package com.badlogic.gdx.graphics.g3d;

import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.NodeAnimation;
import com.badlogic.gdx.graphics.g3d.model.NodeKeyframe;
import com.badlogic.gdx.graphics.g3d.model.NodePart;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.Pool;

public class ModelInstance implements RenderableProvider {
  public static boolean defaultShareKeyframes = true;
  
  public final Array<Material> materials = new Array<>();
  
  public final Array<Node> nodes = new Array<>();
  
  public final Array<Animation> animations = new Array<>();
  
  public final Model model;
  
  public Matrix4 transform;
  
  public Object userData;
  
  public ModelInstance(Model model) {
    this(model, (String[])null);
  }
  
  public ModelInstance(Model model, String nodeId, boolean mergeTransform) {
    this(model, null, nodeId, false, false, mergeTransform);
  }
  
  public ModelInstance(Model model, Matrix4 transform, String nodeId, boolean mergeTransform) {
    this(model, transform, nodeId, false, false, mergeTransform);
  }
  
  public ModelInstance(Model model, String nodeId, boolean parentTransform, boolean mergeTransform) {
    this(model, null, nodeId, true, parentTransform, mergeTransform);
  }
  
  public ModelInstance(Model model, Matrix4 transform, String nodeId, boolean parentTransform, boolean mergeTransform) {
    this(model, transform, nodeId, true, parentTransform, mergeTransform);
  }
  
  public ModelInstance(Model model, String nodeId, boolean recursive, boolean parentTransform, boolean mergeTransform) {
    this(model, null, nodeId, recursive, parentTransform, mergeTransform);
  }
  
  public ModelInstance(Model model, Matrix4 transform, String nodeId, boolean recursive, boolean parentTransform, boolean mergeTransform) {
    this(model, transform, nodeId, recursive, parentTransform, mergeTransform, defaultShareKeyframes);
  }
  
  public ModelInstance(Model model, Matrix4 transform, String nodeId, boolean recursive, boolean parentTransform, boolean mergeTransform, boolean shareKeyframes) {
    this.model = model;
    this.transform = (transform == null) ? new Matrix4() : transform;
    Node node = model.getNode(nodeId, recursive);
    Node copy;
    this.nodes.add(copy = node.copy());
    if (mergeTransform) {
      this.transform.mul(parentTransform ? node.globalTransform : node.localTransform);
      copy.translation.set(0.0F, 0.0F, 0.0F);
      copy.rotation.idt();
      copy.scale.set(1.0F, 1.0F, 1.0F);
    } else if (parentTransform && copy.hasParent()) {
      this.transform.mul((node.getParent()).globalTransform);
    } 
    invalidate();
    copyAnimations(model.animations, shareKeyframes);
    calculateTransforms();
  }
  
  public ModelInstance(Model model, String... rootNodeIds) {
    this(model, null, rootNodeIds);
  }
  
  public ModelInstance(Model model, Matrix4 transform, String... rootNodeIds) {
    this.model = model;
    this.transform = (transform == null) ? new Matrix4() : transform;
    if (rootNodeIds == null) {
      copyNodes(model.nodes);
    } else
      copyNodes(model.nodes, rootNodeIds); 
    copyAnimations(model.animations, defaultShareKeyframes);
    calculateTransforms();
  }
  
  public ModelInstance(Model model, Array<String> rootNodeIds) {
    this(model, null, rootNodeIds);
  }
  
  public ModelInstance(Model model, Matrix4 transform, Array<String> rootNodeIds) {
    this(model, transform, rootNodeIds, defaultShareKeyframes);
  }
  
  public ModelInstance(Model model, Matrix4 transform, Array<String> rootNodeIds, boolean shareKeyframes) {
    this.model = model;
    this.transform = (transform == null) ? new Matrix4() : transform;
    copyNodes(model.nodes, rootNodeIds);
    copyAnimations(model.animations, shareKeyframes);
    calculateTransforms();
  }
  
  public ModelInstance(Model model, Vector3 position) {
    this(model);
    this.transform.setToTranslation(position);
  }
  
  public ModelInstance(Model model, float x, float y, float z) {
    this(model);
    this.transform.setToTranslation(x, y, z);
  }
  
  public ModelInstance(Model model, Matrix4 transform) {
    this(model, transform, (String[])null);
  }
  
  public ModelInstance(ModelInstance copyFrom) {
    this(copyFrom, copyFrom.transform.cpy());
  }
  
  public ModelInstance(ModelInstance copyFrom, Matrix4 transform) {
    this(copyFrom, transform, defaultShareKeyframes);
  }
  
  public ModelInstance(ModelInstance copyFrom, Matrix4 transform, boolean shareKeyframes) {
    this.model = copyFrom.model;
    this.transform = (transform == null) ? new Matrix4() : transform;
    copyNodes(copyFrom.nodes);
    copyAnimations(copyFrom.animations, shareKeyframes);
    calculateTransforms();
  }
  
  public ModelInstance copy() {
    return new ModelInstance(this);
  }
  
  private void copyNodes(Array<Node> nodes) {
    for (int i = 0, n = nodes.size; i < n; i++) {
      Node node = nodes.get(i);
      this.nodes.add(node.copy());
    } 
    invalidate();
  }
  
  private void copyNodes(Array<Node> nodes, String... nodeIds) {
    for (int i = 0, n = nodes.size; i < n; i++) {
      Node node = nodes.get(i);
      for (String nodeId : nodeIds) {
        if (nodeId.equals(node.id)) {
          this.nodes.add(node.copy());
          break;
        } 
      } 
    } 
    invalidate();
  }
  
  private void copyNodes(Array<Node> nodes, Array<String> nodeIds) {
    for (int i = 0, n = nodes.size; i < n; i++) {
      Node node = nodes.get(i);
      for (Array.ArrayIterator<String> arrayIterator = nodeIds.iterator(); arrayIterator.hasNext(); ) {
        String nodeId = arrayIterator.next();
        if (nodeId.equals(node.id)) {
          this.nodes.add(node.copy());
          break;
        } 
      } 
    } 
    invalidate();
  }
  
  private void invalidate(Node node) {
    for (int j = 0, k = node.parts.size; j < k; j++) {
      NodePart part = node.parts.get(j);
      ArrayMap<Node, Matrix4> bindPose = part.invBoneBindTransforms;
      if (bindPose != null)
        for (int m = 0; m < bindPose.size; m++)
          ((Node[])bindPose.keys)[m] = getNode((((Node[])bindPose.keys)[m]).id);  
      if (!this.materials.contains(part.material, true)) {
        int midx = this.materials.indexOf(part.material, false);
        if (midx < 0) {
          this.materials.add(part.material = part.material.copy());
        } else
          part.material = this.materials.get(midx); 
      } 
    } 
    for (int i = 0, n = node.getChildCount(); i < n; i++)
      invalidate(node.getChild(i)); 
  }
  
  private void invalidate() {
    for (int i = 0, n = this.nodes.size; i < n; i++)
      invalidate(this.nodes.get(i)); 
  }
  
  public void copyAnimations(Iterable<Animation> source) {
    for (Animation anim : source)
      copyAnimation(anim, defaultShareKeyframes); 
  }
  
  public void copyAnimations(Iterable<Animation> source, boolean shareKeyframes) {
    for (Animation anim : source)
      copyAnimation(anim, shareKeyframes); 
  }
  
  public void copyAnimation(Animation sourceAnim) {
    copyAnimation(sourceAnim, defaultShareKeyframes);
  }
  
  public void copyAnimation(Animation sourceAnim, boolean shareKeyframes) {
    Animation animation = new Animation();
    animation.id = sourceAnim.id;
    animation.duration = sourceAnim.duration;
    for (Array.ArrayIterator<NodeAnimation> arrayIterator = sourceAnim.nodeAnimations.iterator(); arrayIterator.hasNext(); ) {
      NodeAnimation nanim = arrayIterator.next();
      Node node = getNode(nanim.node.id);
      if (node == null)
        continue; 
      NodeAnimation nodeAnim = new NodeAnimation();
      nodeAnim.node = node;
      if (shareKeyframes) {
        nodeAnim.translation = nanim.translation;
        nodeAnim.rotation = nanim.rotation;
        nodeAnim.scaling = nanim.scaling;
      } else {
        if (nanim.translation != null) {
          nodeAnim.translation = new Array<>();
          for (Array.ArrayIterator<NodeKeyframe<Vector3>> arrayIterator1 = nanim.translation.iterator(); arrayIterator1.hasNext(); ) {
            NodeKeyframe<Vector3> kf = arrayIterator1.next();
            nodeAnim.translation.add(new NodeKeyframe<>(kf.keytime, (Vector3)kf.value));
          } 
        } 
        if (nanim.rotation != null) {
          nodeAnim.rotation = new Array<>();
          for (Array.ArrayIterator<NodeKeyframe<Quaternion>> arrayIterator1 = nanim.rotation.iterator(); arrayIterator1.hasNext(); ) {
            NodeKeyframe<Quaternion> kf = arrayIterator1.next();
            nodeAnim.rotation.add(new NodeKeyframe<>(kf.keytime, (Quaternion)kf.value));
          } 
        } 
        if (nanim.scaling != null) {
          nodeAnim.scaling = new Array<>();
          for (Array.ArrayIterator<NodeKeyframe<Vector3>> arrayIterator1 = nanim.scaling.iterator(); arrayIterator1.hasNext(); ) {
            NodeKeyframe<Vector3> kf = arrayIterator1.next();
            nodeAnim.scaling.add(new NodeKeyframe<>(kf.keytime, (Vector3)kf.value));
          } 
        } 
      } 
      if (nodeAnim.translation != null || nodeAnim.rotation != null || nodeAnim.scaling != null)
        animation.nodeAnimations.add(nodeAnim); 
    } 
    if (animation.nodeAnimations.size > 0)
      this.animations.add(animation); 
  }
  
  public void getRenderables(Array<Renderable> renderables, Pool<Renderable> pool) {
    for (Array.ArrayIterator<Node> arrayIterator = this.nodes.iterator(); arrayIterator.hasNext(); ) {
      Node node = arrayIterator.next();
      getRenderables(node, renderables, pool);
    } 
  }
  
  public Renderable getRenderable(Renderable out) {
    return getRenderable(out, this.nodes.get(0));
  }
  
  public Renderable getRenderable(Renderable out, Node node) {
    return getRenderable(out, node, node.parts.get(0));
  }
  
  public Renderable getRenderable(Renderable out, Node node, NodePart nodePart) {
    nodePart.setRenderable(out);
    if (nodePart.bones == null && this.transform != null) {
      out.worldTransform.set(this.transform).mul(node.globalTransform);
    } else if (this.transform != null) {
      out.worldTransform.set(this.transform);
    } else
      out.worldTransform.idt(); 
    out.userData = this.userData;
    return out;
  }
  
  protected void getRenderables(Node node, Array<Renderable> renderables, Pool<Renderable> pool) {
    if (node.parts.size > 0)
      for (Array.ArrayIterator<NodePart> arrayIterator = node.parts.iterator(); arrayIterator.hasNext(); ) {
        NodePart nodePart = arrayIterator.next();
        if (nodePart.enabled)
          renderables.add(getRenderable(pool.obtain(), node, nodePart)); 
      }  
    for (Node child : node.getChildren())
      getRenderables(child, renderables, pool); 
  }
  
  public void calculateTransforms() {
    int n = this.nodes.size;
    for (int j = 0; j < n; j++)
      this.nodes.get(j).calculateTransforms(true); 
    for (int i = 0; i < n; i++)
      this.nodes.get(i).calculateBoneTransforms(true); 
  }
  
  public BoundingBox calculateBoundingBox(BoundingBox out) {
    out.inf();
    return extendBoundingBox(out);
  }
  
  public BoundingBox extendBoundingBox(BoundingBox out) {
    int n = this.nodes.size;
    for (int i = 0; i < n; i++)
      this.nodes.get(i).extendBoundingBox(out); 
    return out;
  }
  
  public Animation getAnimation(String id) {
    return getAnimation(id, false);
  }
  
  public Animation getAnimation(String id, boolean ignoreCase) {
    int n = this.animations.size;
    if (ignoreCase) {
      for (int i = 0; i < n; i++) {
        Animation animation;
        if ((animation = this.animations.get(i)).id.equalsIgnoreCase(id))
          return animation; 
      } 
    } else
      for (int i = 0; i < n; i++) {
        Animation animation;
        if ((animation = this.animations.get(i)).id.equals(id))
          return animation; 
      }  
    return null;
  }
  
  public Material getMaterial(String id) {
    return getMaterial(id, true);
  }
  
  public Material getMaterial(String id, boolean ignoreCase) {
    int n = this.materials.size;
    if (ignoreCase) {
      for (int i = 0; i < n; i++) {
        Material material;
        if ((material = this.materials.get(i)).id.equalsIgnoreCase(id))
          return material; 
      } 
    } else
      for (int i = 0; i < n; i++) {
        Material material;
        if ((material = this.materials.get(i)).id.equals(id))
          return material; 
      }  
    return null;
  }
  
  public Node getNode(String id) {
    return getNode(id, true);
  }
  
  public Node getNode(String id, boolean recursive) {
    return getNode(id, recursive, false);
  }
  
  public Node getNode(String id, boolean recursive, boolean ignoreCase) {
    return Node.getNode(this.nodes, id, recursive, ignoreCase);
  }
}
