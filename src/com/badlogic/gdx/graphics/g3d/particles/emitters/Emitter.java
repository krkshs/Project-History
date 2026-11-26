package com.badlogic.gdx.graphics.g3d.particles.emitters;

import com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public abstract class Emitter extends ParticleControllerComponent implements Json.Serializable {
  public int minParticleCount;
  
  public int maxParticleCount = 4;
  
  public float percent;
  
  public Emitter(Emitter regularEmitter) {
    set(regularEmitter);
  }
  
  public Emitter() {}
  
  public void init() {
    this.controller.particles.size = 0;
  }
  
  public void end() {
    this.controller.particles.size = 0;
  }
  
  public boolean isComplete() {
    return (this.percent >= 1.0F);
  }
  
  public int getMinParticleCount() {
    return this.minParticleCount;
  }
  
  public void setMinParticleCount(int minParticleCount) {
    this.minParticleCount = minParticleCount;
  }
  
  public int getMaxParticleCount() {
    return this.maxParticleCount;
  }
  
  public void setMaxParticleCount(int maxParticleCount) {
    this.maxParticleCount = maxParticleCount;
  }
  
  public void setParticleCount(int aMin, int aMax) {
    setMinParticleCount(aMin);
    setMaxParticleCount(aMax);
  }
  
  public void set(Emitter emitter) {
    this.minParticleCount = emitter.minParticleCount;
    this.maxParticleCount = emitter.maxParticleCount;
  }
  
  public void write(Json json) {
    json.writeValue("minParticleCount", Integer.valueOf(this.minParticleCount));
    json.writeValue("maxParticleCount", Integer.valueOf(this.maxParticleCount));
  }
  
  public void read(Json json, JsonValue jsonData) {
    this.minParticleCount = (Integer)json.<int>readValue("minParticleCount", int.class, jsonData);
    this.maxParticleCount = (Integer)json.<int>readValue("maxParticleCount", int.class, jsonData);
  }
}
