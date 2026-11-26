package com.badlogic.gdx.graphics.g3d.particles.values;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.particles.ResourceData;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public abstract class SpawnShapeValue extends ParticleValue implements ResourceData.Configurable, Json.Serializable {
  public RangedNumericValue xOffsetValue = new RangedNumericValue();
  
  public RangedNumericValue yOffsetValue = new RangedNumericValue();
  
  public RangedNumericValue zOffsetValue = new RangedNumericValue();
  
  public SpawnShapeValue() {}
  
  public SpawnShapeValue(SpawnShapeValue spawnShapeValue) {
    this();
  }
  
  public abstract void spawnAux(Vector3 paramVector3, float paramFloat);
  
  public final Vector3 spawn(Vector3 vector, float percent) {
    spawnAux(vector, percent);
    if (this.xOffsetValue.active)
      vector.x += this.xOffsetValue.newLowValue(); 
    if (this.yOffsetValue.active)
      vector.y += this.yOffsetValue.newLowValue(); 
    if (this.zOffsetValue.active)
      vector.z += this.zOffsetValue.newLowValue(); 
    return vector;
  }
  
  public void init() {}
  
  public void start() {}
  
  public void load(ParticleValue value) {
    super.load(value);
    SpawnShapeValue shape = (SpawnShapeValue)value;
    this.xOffsetValue.load(shape.xOffsetValue);
    this.yOffsetValue.load(shape.yOffsetValue);
    this.zOffsetValue.load(shape.zOffsetValue);
  }
  
  public abstract SpawnShapeValue copy();
  
  public void write(Json json) {
    super.write(json);
    json.writeValue("xOffsetValue", this.xOffsetValue);
    json.writeValue("yOffsetValue", this.yOffsetValue);
    json.writeValue("zOffsetValue", this.zOffsetValue);
  }
  
  public void read(Json json, JsonValue jsonData) {
    super.read(json, jsonData);
    this.xOffsetValue = json.<RangedNumericValue>readValue("xOffsetValue", RangedNumericValue.class, jsonData);
    this.yOffsetValue = json.<RangedNumericValue>readValue("yOffsetValue", RangedNumericValue.class, jsonData);
    this.zOffsetValue = json.<RangedNumericValue>readValue("zOffsetValue", RangedNumericValue.class, jsonData);
  }
  
  public void save(AssetManager manager, ResourceData data) {}
  
  public void load(AssetManager manager, ResourceData data) {}
}
