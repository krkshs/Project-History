package com.badlogic.gdx.graphics.g3d.loader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.model.data.ModelData;
import com.badlogic.gdx.graphics.g3d.model.data.ModelMaterial;
import com.badlogic.gdx.graphics.g3d.model.data.ModelMesh;
import com.badlogic.gdx.graphics.g3d.model.data.ModelMeshPart;
import com.badlogic.gdx.graphics.g3d.model.data.ModelNode;
import com.badlogic.gdx.graphics.g3d.model.data.ModelNodePart;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ObjLoader extends ModelLoader<ObjLoader.ObjLoaderParameters> {
  public static boolean logWarning = false;
  
  public static class ObjLoaderParameters extends ModelLoader.ModelParameters {
    public boolean flipV;
    
    public ObjLoaderParameters() {}
    
    public ObjLoaderParameters(boolean flipV) {
      this.flipV = flipV;
    }
  }
  
  final FloatArray verts = new FloatArray(300);
  
  final FloatArray norms = new FloatArray(300);
  
  final FloatArray uvs = new FloatArray(200);
  
  final Array<Group> groups = new Array<>(10);
  
  public ObjLoader() {
    this(null);
  }
  
  public ObjLoader(FileHandleResolver resolver) {
    super(resolver);
  }
  
  public Model loadModel(FileHandle fileHandle, boolean flipV) {
    return loadModel(fileHandle, new ObjLoaderParameters(flipV));
  }
  
  public ModelData loadModelData(FileHandle file, ObjLoaderParameters parameters) {
    return loadModelData(file, (parameters != null && parameters.flipV));
  }
  
  protected ModelData loadModelData(FileHandle file, boolean flipV) {
    if (logWarning)
      Gdx.app.error("ObjLoader", "Wavefront (OBJ) is not fully supported, consult the documentation for more information"); 
    MtlLoader mtl = new MtlLoader();
    Group activeGroup = new Group("default");
    this.groups.add(activeGroup);
    BufferedReader reader = new BufferedReader(new InputStreamReader(file.read()), 4096);
    int id = 0;
    try {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] tokens = line.split("\\s+");
        if (tokens.length < 1)
          break; 
        if (tokens[0].length() == 0)
          continue; 
        char firstChar;
        if ((firstChar = tokens[0].toLowerCase().charAt(0)) == '#')
          continue; 
        if (firstChar == 'v') {
          if (tokens[0].length() == 1) {
            this.verts.add(Float.parseFloat(tokens[1]));
            this.verts.add(Float.parseFloat(tokens[2]));
            this.verts.add(Float.parseFloat(tokens[3]));
            continue;
          } 
          if (tokens[0].charAt(1) == 'n') {
            this.norms.add(Float.parseFloat(tokens[1]));
            this.norms.add(Float.parseFloat(tokens[2]));
            this.norms.add(Float.parseFloat(tokens[3]));
            continue;
          } 
          if (tokens[0].charAt(1) == 't') {
            this.uvs.add(Float.parseFloat(tokens[1]));
            this.uvs.add(flipV ? (1.0F - Float.parseFloat(tokens[2])) : Float.parseFloat(tokens[2]));
          } 
          continue;
        } 
        if (firstChar == 'f') {
          Array<Integer> faces = activeGroup.faces;
          for (int j = 1; j < tokens.length - 2; j--) {
            String[] parts = tokens[1].split("/");
            faces.add(getIndex(parts[0], this.verts.size));
            if (parts.length > 2) {
              if (j == 1)
                activeGroup.hasNorms = true; 
              faces.add(getIndex(parts[2], this.norms.size));
            } 
            if (parts.length > 1 && parts[1].length() > 0) {
              if (j == 1)
                activeGroup.hasUVs = true; 
              faces.add(getIndex(parts[1], this.uvs.size));
            } 
            parts = tokens[++j].split("/");
            faces.add(getIndex(parts[0], this.verts.size));
            if (parts.length > 2)
              faces.add(getIndex(parts[2], this.norms.size)); 
            if (parts.length > 1 && parts[1].length() > 0)
              faces.add(getIndex(parts[1], this.uvs.size)); 
            parts = tokens[++j].split("/");
            faces.add(getIndex(parts[0], this.verts.size));
            if (parts.length > 2)
              faces.add(getIndex(parts[2], this.norms.size)); 
            if (parts.length > 1 && parts[1].length() > 0)
              faces.add(getIndex(parts[1], this.uvs.size)); 
            activeGroup.numFaces++;
          } 
          continue;
        } 
        if (firstChar == 'o' || firstChar == 'g') {
          if (tokens.length > 1) {
            activeGroup = setActiveGroup(tokens[1]);
            continue;
          } 
          activeGroup = setActiveGroup("default");
          continue;
        } 
        if (tokens[0].equals("mtllib")) {
          mtl.load(file.parent().child(tokens[1]));
          continue;
        } 
        if (tokens[0].equals("usemtl")) {
          if (tokens.length == 1) {
            activeGroup.materialName = "default";
            continue;
          } 
          activeGroup.materialName = tokens[1].replace('.', '_');
        } 
      } 
      reader.close();
    } catch (IOException e) {
      return null;
    } 
    for (int i = 0; i < this.groups.size; i++) {
      if (((Group)this.groups.get(i)).numFaces < 1) {
        this.groups.removeIndex(i);
        i--;
      } 
    } 
    if (this.groups.size < 1)
      return null; 
    int numGroups = this.groups.size;
    ModelData data = new ModelData();
    for (int g = 0; g < numGroups; g++) {
      Group group = this.groups.get(g);
      Array<Integer> faces = group.faces;
      int numElements = faces.size;
      int numFaces = group.numFaces;
      boolean hasNorms = group.hasNorms;
      boolean hasUVs = group.hasUVs;
      float[] finalVerts = new float[numFaces * 3 * (3 + (hasNorms ? 3 : 0) + (hasUVs ? 2 : 0))];
      for (int j = 0, vi = 0; j < numElements; ) {
        int vertIndex = faces.get(j++) * 3;
        finalVerts[vi++] = this.verts.get(vertIndex++);
        finalVerts[vi++] = this.verts.get(vertIndex++);
        finalVerts[vi++] = this.verts.get(vertIndex);
        if (hasNorms) {
          int normIndex = faces.get(j++) * 3;
          finalVerts[vi++] = this.norms.get(normIndex++);
          finalVerts[vi++] = this.norms.get(normIndex++);
          finalVerts[vi++] = this.norms.get(normIndex);
        } 
        if (hasUVs) {
          int uvIndex = faces.get(j++) * 2;
          finalVerts[vi++] = this.uvs.get(uvIndex++);
          finalVerts[vi++] = this.uvs.get(uvIndex);
        } 
      } 
      int numIndices = (numFaces * 3 >= 32767) ? 0 : (numFaces * 3);
      short[] finalIndices = new short[numIndices];
      if (numIndices > 0)
        for (int k = 0; k < numIndices; k++)
          finalIndices[k] = (short)k;  
      Array<VertexAttribute> attributes = new Array<>();
      attributes.add(new VertexAttribute(1, 3, "a_position"));
      if (hasNorms)
        attributes.add(new VertexAttribute(8, 3, "a_normal")); 
      if (hasUVs)
        attributes.add(new VertexAttribute(16, 2, "a_texCoord0")); 
      String stringId = Integer.toString(++id);
      String nodeId = "default".equals(group.name) ? ("node" + stringId) : group.name;
      String meshId = "default".equals(group.name) ? ("mesh" + stringId) : group.name;
      String partId = "default".equals(group.name) ? ("part" + stringId) : group.name;
      ModelNode node = new ModelNode();
      node.id = nodeId;
      node.meshId = meshId;
      node.scale = new Vector3(1.0F, 1.0F, 1.0F);
      node.translation = new Vector3();
      node.rotation = new Quaternion();
      ModelNodePart pm = new ModelNodePart();
      pm.meshPartId = partId;
      pm.materialId = group.materialName;
      node.parts = new ModelNodePart[] { pm };
      ModelMeshPart part = new ModelMeshPart();
      part.id = partId;
      part.indices = finalIndices;
      part.primitiveType = 4;
      ModelMesh mesh = new ModelMesh();
      mesh.id = meshId;
      mesh.attributes = attributes.<VertexAttribute>toArray(VertexAttribute.class);
      mesh.vertices = finalVerts;
      mesh.parts = new ModelMeshPart[] { part };
      data.nodes.add(node);
      data.meshes.add(mesh);
      ModelMaterial mm = mtl.getMaterial(group.materialName);
      data.materials.add(mm);
    } 
    if (this.verts.size > 0)
      this.verts.clear(); 
    if (this.norms.size > 0)
      this.norms.clear(); 
    if (this.uvs.size > 0)
      this.uvs.clear(); 
    if (this.groups.size > 0)
      this.groups.clear(); 
    return data;
  }
  
  private Group setActiveGroup(String name) {
    for (Array.ArrayIterator<Group> arrayIterator = this.groups.iterator(); arrayIterator.hasNext(); ) {
      Group group1 = arrayIterator.next();
      if (group1.name.equals(name))
        return group1; 
    } 
    Group group = new Group(name);
    this.groups.add(group);
    return group;
  }
  
  private int getIndex(String index, int size) {
    if (index == null || index.length() == 0)
      return 0; 
    int idx = Integer.parseInt(index);
    if (idx < 0)
      return size + idx; 
    return idx - 1;
  }
  
  private class Group {
    final String name;
    
    String materialName;
    
    Array<Integer> faces;
    
    int numFaces;
    
    boolean hasNorms;
    
    boolean hasUVs;
    
    Material mat;
    
    Group(String name) {
      this.name = name;
      this.faces = new Array<>(200);
      this.numFaces = 0;
      this.mat = new Material("");
      this.materialName = "default";
    }
  }
}
