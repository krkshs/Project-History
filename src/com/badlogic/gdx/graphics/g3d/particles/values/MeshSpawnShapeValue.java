package com.badlogic.gdx.graphics.g3d.particles.values;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.particles.ResourceData;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.GdxRuntimeException;

public abstract class MeshSpawnShapeValue extends SpawnShapeValue {
  protected Mesh mesh;
  
  protected Model model;
  
  public static class Triangle {
    float x1;
    
    float y1;
    
    float z1;
    
    float x2;
    
    float y2;
    
    float z2;
    
    float x3;
    
    float y3;
    
    float z3;
    
    public Triangle(float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3) {
      this.x1 = x1;
      this.y1 = y1;
      this.z1 = z1;
      this.x2 = x2;
      this.y2 = y2;
      this.z2 = z2;
      this.x3 = x3;
      this.y3 = y3;
      this.z3 = z3;
    }
    
    public static Vector3 pick(float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, Vector3 vector) {
      float a = MathUtils.random(), b = MathUtils.random();
      return vector.set(x1 + a * (x2 - x1) + b * (x3 - x1), y1 + a * (y2 - y1) + b * (y3 - y1), z1 + a * (z2 - z1) + b * (z3 - z1));
    }
    
    public Vector3 pick(Vector3 vector) {
      float a = MathUtils.random(), b = MathUtils.random();
      return vector.set(this.x1 + a * (this.x2 - this.x1) + b * (this.x3 - this.x1), this.y1 + a * (this.y2 - this.y1) + b * (this.y3 - this.y1), this.z1 + a * (this.z2 - this.z1) + b * (this.z3 - this.z1));
    }
  }
  
  public MeshSpawnShapeValue(MeshSpawnShapeValue value) {
    super(value);
  }
  
  public MeshSpawnShapeValue() {}
  
  public void load(ParticleValue value) {
    super.load(value);
    MeshSpawnShapeValue spawnShapeValue = (MeshSpawnShapeValue)value;
    setMesh(spawnShapeValue.mesh, spawnShapeValue.model);
  }
  
  public void setMesh(Mesh mesh, Model model) {
    if (mesh.getVertexAttribute(1) == null)
      throw new GdxRuntimeException("Mesh vertices must have Usage.Position"); 
    this.model = model;
    this.mesh = mesh;
  }
  
  public void setMesh(Mesh mesh) {
    setMesh(mesh, null);
  }
  
  public void save(AssetManager manager, ResourceData data) {
    if (this.model != null) {
      ResourceData.SaveData saveData = data.createSaveData();
      saveData.<Model>saveAsset(manager.<Model>getAssetFileName(this.model), Model.class);
      saveData.save("index", Integer.valueOf(this.model.meshes.indexOf(this.mesh, true)));
    } 
  }
  
  public void load(AssetManager manager, ResourceData data) {
    ResourceData.SaveData saveData = data.getSaveData();
    AssetDescriptor<Model> descriptor = saveData.loadAsset();
    if (descriptor != null) {
      Model model = manager.<Model>get(descriptor);
      setMesh(model.meshes.get(saveData.<Integer>load("index")), model);
    } 
  }
}
