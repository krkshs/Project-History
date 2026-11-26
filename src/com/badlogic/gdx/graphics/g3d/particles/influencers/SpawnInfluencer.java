package com.badlogic.gdx.graphics.g3d.particles.influencers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.particles.ParallelArray;
import com.badlogic.gdx.graphics.g3d.particles.ParticleChannels;
import com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent;
import com.badlogic.gdx.graphics.g3d.particles.ResourceData;
import com.badlogic.gdx.graphics.g3d.particles.values.PointSpawnShapeValue;
import com.badlogic.gdx.graphics.g3d.particles.values.SpawnShapeValue;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class SpawnInfluencer extends Influencer {
  public SpawnShapeValue spawnShapeValue;
  
  ParallelArray.FloatChannel positionChannel;
  
  ParallelArray.FloatChannel rotationChannel;
  
  public SpawnInfluencer() {
    this.spawnShapeValue = new PointSpawnShapeValue();
  }
  
  public SpawnInfluencer(SpawnShapeValue spawnShapeValue) {
    this.spawnShapeValue = spawnShapeValue;
  }
  
  public SpawnInfluencer(SpawnInfluencer source) {
    this.spawnShapeValue = source.spawnShapeValue.copy();
  }
  
  public void init() {
    this.spawnShapeValue.init();
  }
  
  public void allocateChannels() {
    this.positionChannel = this.controller.particles.<ParallelArray.FloatChannel>addChannel(ParticleChannels.Position);
    this.rotationChannel = this.controller.particles.<ParallelArray.FloatChannel>addChannel(ParticleChannels.Rotation3D);
  }
  
  public void start() {
    this.spawnShapeValue.start();
  }
  
  public void activateParticles(int startIndex, int count) {
    for (int j = startIndex * this.positionChannel.strideSize, k = j + count * this.positionChannel.strideSize; j < k; j += this.positionChannel.strideSize) {
      this.spawnShapeValue.spawn(TMP_V1, this.controller.emitter.percent);
      TMP_V1.mul(this.controller.transform);
      this.positionChannel.data[j + 0] = TMP_V1.x;
      this.positionChannel.data[j + 1] = TMP_V1.y;
      this.positionChannel.data[j + 2] = TMP_V1.z;
    } 
    for (int i = startIndex * this.rotationChannel.strideSize, c = i + count * this.rotationChannel.strideSize; i < c; i += this.rotationChannel.strideSize) {
      this.controller.transform.getRotation(TMP_Q, true);
      this.rotationChannel.data[i + 0] = TMP_Q.x;
      this.rotationChannel.data[i + 1] = TMP_Q.y;
      this.rotationChannel.data[i + 2] = TMP_Q.z;
      this.rotationChannel.data[i + 3] = TMP_Q.w;
    } 
  }
  
  public SpawnInfluencer copy() {
    return new SpawnInfluencer(this);
  }
  
  public void write(Json json) {
    json.writeValue("spawnShape", this.spawnShapeValue, SpawnShapeValue.class);
  }
  
  public void read(Json json, JsonValue jsonData) {
    this.spawnShapeValue = json.<SpawnShapeValue>readValue("spawnShape", SpawnShapeValue.class, jsonData);
  }
  
  public void save(AssetManager manager, ResourceData data) {
    this.spawnShapeValue.save(manager, data);
  }
  
  public void load(AssetManager manager, ResourceData data) {
    this.spawnShapeValue.load(manager, data);
  }
}
