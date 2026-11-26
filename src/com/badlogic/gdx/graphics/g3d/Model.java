package com.badlogic.gdx.graphics.g3d;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.NodeAnimation;
import com.badlogic.gdx.graphics.g3d.model.NodeKeyframe;
import com.badlogic.gdx.graphics.g3d.model.NodePart;
import com.badlogic.gdx.graphics.g3d.model.data.ModelAnimation;
import com.badlogic.gdx.graphics.g3d.model.data.ModelData;
import com.badlogic.gdx.graphics.g3d.model.data.ModelMaterial;
import com.badlogic.gdx.graphics.g3d.model.data.ModelMesh;
import com.badlogic.gdx.graphics.g3d.model.data.ModelMeshPart;
import com.badlogic.gdx.graphics.g3d.model.data.ModelNode;
import com.badlogic.gdx.graphics.g3d.model.data.ModelNodeAnimation;
import com.badlogic.gdx.graphics.g3d.model.data.ModelNodeKeyframe;
import com.badlogic.gdx.graphics.g3d.model.data.ModelNodePart;
import com.badlogic.gdx.graphics.g3d.model.data.ModelTexture;
import com.badlogic.gdx.graphics.g3d.utils.TextureDescriptor;
import com.badlogic.gdx.graphics.g3d.utils.TextureProvider;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ObjectMap;

public class Model implements Disposable {
  public final Array<Material> materials = new Array<>();
  
  public final Array<Node> nodes = new Array<>();
  
  public final Array<Animation> animations = new Array<>();
  
  public final Array<Mesh> meshes = new Array<>();
  
  public final Array<MeshPart> meshParts = new Array<>();
  
  protected final Array<Disposable> disposables = new Array<>();
  
  public Model() {}
  
  public Model(ModelData modelData) {
    this(modelData, new TextureProvider.FileTextureProvider());
  }
  
  public Model(ModelData modelData, TextureProvider textureProvider) {
    load(modelData, textureProvider);
  }
  
  protected void load(ModelData modelData, TextureProvider textureProvider) {
    loadMeshes(modelData.meshes);
    loadMaterials(modelData.materials, textureProvider);
    loadNodes(modelData.nodes);
    loadAnimations(modelData.animations);
    calculateTransforms();
  }
  
  protected void loadAnimations(Iterable<ModelAnimation> modelAnimations) {
    for (ModelAnimation anim : modelAnimations) {
      Animation animation = new Animation();
      animation.id = anim.id;
      for (Array.ArrayIterator<ModelNodeAnimation> arrayIterator = anim.nodeAnimations.iterator(); arrayIterator.hasNext(); ) {
        ModelNodeAnimation nanim = arrayIterator.next();
        Node node = getNode(nanim.nodeId);
        if (node == null)
          continue; 
        NodeAnimation nodeAnim = new NodeAnimation();
        nodeAnim.node = node;
        if (nanim.translation != null) {
          nodeAnim.translation = new Array<>();
          nodeAnim.translation.ensureCapacity(nanim.translation.size);
          for (Array.ArrayIterator<ModelNodeKeyframe<Vector3>> arrayIterator1 = nanim.translation.iterator(); arrayIterator1.hasNext(); ) {
            ModelNodeKeyframe<Vector3> kf = arrayIterator1.next();
            if (kf.keytime > animation.duration)
              animation.duration = kf.keytime; 
            nodeAnim.translation.add(new NodeKeyframe<>(kf.keytime, new Vector3((kf.value == null) ? node.translation : (Vector3)kf.value)));
          } 
        } 
        if (nanim.rotation != null) {
          nodeAnim.rotation = new Array<>();
          nodeAnim.rotation.ensureCapacity(nanim.rotation.size);
          for (Array.ArrayIterator<ModelNodeKeyframe<Quaternion>> arrayIterator1 = nanim.rotation.iterator(); arrayIterator1.hasNext(); ) {
            ModelNodeKeyframe<Quaternion> kf = arrayIterator1.next();
            if (kf.keytime > animation.duration)
              animation.duration = kf.keytime; 
            nodeAnim.rotation.add(new NodeKeyframe<>(kf.keytime, new Quaternion((kf.value == null) ? node.rotation : (Quaternion)kf.value)));
          } 
        } 
        if (nanim.scaling != null) {
          nodeAnim.scaling = new Array<>();
          nodeAnim.scaling.ensureCapacity(nanim.scaling.size);
          for (Array.ArrayIterator<ModelNodeKeyframe<Vector3>> arrayIterator1 = nanim.scaling.iterator(); arrayIterator1.hasNext(); ) {
            ModelNodeKeyframe<Vector3> kf = arrayIterator1.next();
            if (kf.keytime > animation.duration)
              animation.duration = kf.keytime; 
            nodeAnim.scaling.add(new NodeKeyframe<>(kf.keytime, new Vector3((kf.value == null) ? node.scale : (Vector3)kf.value)));
          } 
        } 
        if ((nodeAnim.translation != null && nodeAnim.translation.size > 0) || (nodeAnim.rotation != null && nodeAnim.rotation.size > 0) || (nodeAnim.scaling != null && nodeAnim.scaling.size > 0))
          animation.nodeAnimations.add(nodeAnim); 
      } 
      if (animation.nodeAnimations.size > 0)
        this.animations.add(animation); 
    } 
  }
  
  private ObjectMap<NodePart, ArrayMap<String, Matrix4>> nodePartBones = new ObjectMap<>();
  
  protected void loadNodes(Iterable<ModelNode> modelNodes) {
    this.nodePartBones.clear();
    for (ModelNode node : modelNodes)
      this.nodes.add(loadNode(node)); 
    for (ObjectMap.Entries<ObjectMap.Entry<NodePart, ArrayMap<String, Matrix4>>> entries = this.nodePartBones.entries().iterator(); entries.hasNext(); ) {
      ObjectMap.Entry<NodePart, ArrayMap<String, Matrix4>> e = entries.next();
      if (((NodePart)e.key).invBoneBindTransforms == null)
        ((NodePart)e.key).invBoneBindTransforms = new ArrayMap<>(Node.class, Matrix4.class); 
      ((NodePart)e.key).invBoneBindTransforms.clear();
      for (ObjectMap.Entry<String, Matrix4> b : (Iterable<ObjectMap.Entry<String, Matrix4>>)((ArrayMap)e.value).entries())
        ((NodePart)e.key).invBoneBindTransforms.put(getNode((String)b.key), new Matrix4((Matrix4)b.value).inv()); 
    } 
  }
  
  protected Node loadNode(ModelNode modelNode) {
    Node node = new Node();
    node.id = modelNode.id;
    if (modelNode.translation != null)
      node.translation.set(modelNode.translation); 
    if (modelNode.rotation != null)
      node.rotation.set(modelNode.rotation); 
    if (modelNode.scale != null)
      node.scale.set(modelNode.scale); 
    if (modelNode.parts != null)
      for (ModelNodePart modelNodePart : modelNode.parts) {
        MeshPart meshPart = null;
        Material meshMaterial = null;
        if (modelNodePart.meshPartId != null)
          for (Array.ArrayIterator<MeshPart> arrayIterator = this.meshParts.iterator(); arrayIterator.hasNext(); ) {
            MeshPart part = arrayIterator.next();
            if (modelNodePart.meshPartId.equals(part.id)) {
              meshPart = part;
              break;
            } 
          }  
        if (modelNodePart.materialId != null)
          for (Array.ArrayIterator<Material> arrayIterator = this.materials.iterator(); arrayIterator.hasNext(); ) {
            Material material = arrayIterator.next();
            if (modelNodePart.materialId.equals(material.id)) {
              meshMaterial = material;
              break;
            } 
          }  
        if (meshPart == null || meshMaterial == null)
          throw new GdxRuntimeException("Invalid node: " + node.id); 
        NodePart nodePart = new NodePart();
        nodePart.meshPart = meshPart;
        nodePart.material = meshMaterial;
        node.parts.add(nodePart);
        if (modelNodePart.bones != null)
          this.nodePartBones.put(nodePart, modelNodePart.bones); 
      }  
    if (modelNode.children != null)
      for (ModelNode child : modelNode.children)
        node.<Node>addChild(loadNode(child));  
    return node;
  }
  
  protected void loadMeshes(Iterable<ModelMesh> meshes) {
    for (ModelMesh mesh : meshes)
      convertMesh(mesh); 
  }
  
  protected void convertMesh(ModelMesh modelMesh) {
    int numIndices = 0;
    for (ModelMeshPart part : modelMesh.parts)
      numIndices += part.indices.length; 
    boolean hasIndices = (numIndices > 0);
    VertexAttributes attributes = new VertexAttributes(modelMesh.attributes);
    int numVertices = modelMesh.vertices.length / (attributes.vertexSize / 4);
    Mesh mesh = new Mesh(true, numVertices, numIndices, attributes);
    this.meshes.add(mesh);
    this.disposables.add(mesh);
    BufferUtils.copy(modelMesh.vertices, mesh.getVerticesBuffer(), modelMesh.vertices.length, 0);
    int offset = 0;
    mesh.getIndicesBuffer().clear();
    for (ModelMeshPart part : modelMesh.parts) {
      MeshPart meshPart = new MeshPart();
      meshPart.id = part.id;
      meshPart.primitiveType = part.primitiveType;
      meshPart.offset = offset;
      meshPart.size = hasIndices ? part.indices.length : numVertices;
      meshPart.mesh = mesh;
      if (hasIndices)
        mesh.getIndicesBuffer().put(part.indices); 
      offset += meshPart.size;
      this.meshParts.add(meshPart);
    } 
    mesh.getIndicesBuffer().position(0);
    for (Array.ArrayIterator<MeshPart> arrayIterator = this.meshParts.iterator(); arrayIterator.hasNext(); ) {
      MeshPart part = arrayIterator.next();
      part.update();
    } 
  }
  
  protected void loadMaterials(Iterable<ModelMaterial> modelMaterials, TextureProvider textureProvider) {
    for (ModelMaterial mtl : modelMaterials)
      this.materials.add(convertMaterial(mtl, textureProvider)); 
  }
  
  protected Material convertMaterial(ModelMaterial mtl, TextureProvider textureProvider) {
    Material result = new Material();
    result.id = mtl.id;
    if (mtl.ambient != null)
      result.set(new ColorAttribute(ColorAttribute.Ambient, mtl.ambient)); 
    if (mtl.diffuse != null)
      result.set(new ColorAttribute(ColorAttribute.Diffuse, mtl.diffuse)); 
    if (mtl.specular != null)
      result.set(new ColorAttribute(ColorAttribute.Specular, mtl.specular)); 
    if (mtl.emissive != null)
      result.set(new ColorAttribute(ColorAttribute.Emissive, mtl.emissive)); 
    if (mtl.reflection != null)
      result.set(new ColorAttribute(ColorAttribute.Reflection, mtl.reflection)); 
    if (mtl.shininess > 0.0F)
      result.set(new FloatAttribute(FloatAttribute.Shininess, mtl.shininess)); 
    if (mtl.opacity != 1.0F)
      result.set(new BlendingAttribute(770, 771, mtl.opacity)); 
    ObjectMap<String, Texture> textures = new ObjectMap<>();
    if (mtl.textures != null)
      for (Array.ArrayIterator<ModelTexture> arrayIterator = mtl.textures.iterator(); arrayIterator.hasNext(); ) {
        Texture texture;
        ModelTexture tex = arrayIterator.next();
        if (textures.containsKey(tex.fileName)) {
          texture = textures.<String>get(tex.fileName);
        } else {
          texture = textureProvider.load(tex.fileName);
          textures.put(tex.fileName, texture);
          this.disposables.add(texture);
        } 
        TextureDescriptor<Texture> descriptor = new TextureDescriptor<>(texture);
        descriptor.minFilter = texture.getMinFilter();
        descriptor.magFilter = texture.getMagFilter();
        descriptor.uWrap = texture.getUWrap();
        descriptor.vWrap = texture.getVWrap();
        float offsetU = (tex.uvTranslation == null) ? 0.0F : tex.uvTranslation.x;
        float offsetV = (tex.uvTranslation == null) ? 0.0F : tex.uvTranslation.y;
        float scaleU = (tex.uvScaling == null) ? 1.0F : tex.uvScaling.x;
        float scaleV = (tex.uvScaling == null) ? 1.0F : tex.uvScaling.y;
        switch (tex.usage) {
          case 2:
            result.set(new TextureAttribute(TextureAttribute.Diffuse, descriptor, offsetU, offsetV, scaleU, scaleV));
          case 5:
            result.set(new TextureAttribute(TextureAttribute.Specular, descriptor, offsetU, offsetV, scaleU, scaleV));
          case 8:
            result.set(new TextureAttribute(TextureAttribute.Bump, descriptor, offsetU, offsetV, scaleU, scaleV));
          case 7:
            result.set(new TextureAttribute(TextureAttribute.Normal, descriptor, offsetU, offsetV, scaleU, scaleV));
          case 4:
            result.set(new TextureAttribute(TextureAttribute.Ambient, descriptor, offsetU, offsetV, scaleU, scaleV));
          case 3:
            result.set(new TextureAttribute(TextureAttribute.Emissive, descriptor, offsetU, offsetV, scaleU, scaleV));
          case 10:
            result.set(new TextureAttribute(TextureAttribute.Reflection, descriptor, offsetU, offsetV, scaleU, scaleV));
        } 
      }  
    return result;
  }
  
  public void manageDisposable(Disposable disposable) {
    if (!this.disposables.contains(disposable, true))
      this.disposables.add(disposable); 
  }
  
  public Iterable<Disposable> getManagedDisposables() {
    return this.disposables;
  }
  
  public void dispose() {
    for (Array.ArrayIterator<Disposable> arrayIterator = this.disposables.iterator(); arrayIterator.hasNext(); ) {
      Disposable disposable = arrayIterator.next();
      disposable.dispose();
    } 
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
    return getAnimation(id, true);
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
