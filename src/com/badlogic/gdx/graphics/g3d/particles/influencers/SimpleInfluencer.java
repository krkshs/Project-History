package com.badlogic.gdx.graphics.g3d.particles.influencers;

import com.badlogic.gdx.graphics.g3d.particles.ParallelArray;
import com.badlogic.gdx.graphics.g3d.particles.ParticleChannels;
import com.badlogic.gdx.graphics.g3d.particles.values.ScaledNumericValue;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public abstract class SimpleInfluencer extends Influencer {
  public ScaledNumericValue value;
  
  ParallelArray.FloatChannel valueChannel;
  
  ParallelArray.FloatChannel interpolationChannel;
  
  ParallelArray.FloatChannel lifeChannel;
  
  ParallelArray.ChannelDescriptor valueChannelDescriptor;
  
  public SimpleInfluencer() {
    this.value = new ScaledNumericValue();
    this.value.setHigh(1.0F);
  }
  
  public SimpleInfluencer(SimpleInfluencer billboardScaleinfluencer) {
    this();
    set(billboardScaleinfluencer);
  }
  
  private void set(SimpleInfluencer scaleInfluencer) {
    this.value.load(scaleInfluencer.value);
    this.valueChannelDescriptor = scaleInfluencer.valueChannelDescriptor;
  }
  
  public void allocateChannels() {
    this.valueChannel = this.controller.particles.<ParallelArray.FloatChannel>addChannel(this.valueChannelDescriptor);
    ParticleChannels.Interpolation.id = this.controller.particleChannels.newId();
    this.interpolationChannel = this.controller.particles.<ParallelArray.FloatChannel>addChannel(ParticleChannels.Interpolation);
    this.lifeChannel = this.controller.particles.<ParallelArray.FloatChannel>addChannel(ParticleChannels.Life);
  }
  
  public void activateParticles(int startIndex, int count) {
    if (!this.value.isRelative()) {
      int i = startIndex * this.valueChannel.strideSize, a = startIndex * this.interpolationChannel.strideSize, c = i + count * this.valueChannel.strideSize;
      for (; i < c; i += this.valueChannel.strideSize, a += this.interpolationChannel.strideSize) {
        float start = this.value.newLowValue();
        float diff = this.value.newHighValue() - start;
        this.interpolationChannel.data[a + 0] = start;
        this.interpolationChannel.data[a + 1] = diff;
        this.valueChannel.data[i] = start + diff * this.value.getScale(0.0F);
      } 
    } else {
      int i = startIndex * this.valueChannel.strideSize, a = startIndex * this.interpolationChannel.strideSize, c = i + count * this.valueChannel.strideSize;
      for (; i < c; i += this.valueChannel.strideSize, a += this.interpolationChannel.strideSize) {
        float start = this.value.newLowValue();
        float diff = this.value.newHighValue();
        this.interpolationChannel.data[a + 0] = start;
        this.interpolationChannel.data[a + 1] = diff;
        this.valueChannel.data[i] = start + diff * this.value.getScale(0.0F);
      } 
    } 
  }
  
  public void update() {
    for (int i = 0, a = 0, l = 2, c = i + this.controller.particles.size * this.valueChannel.strideSize; i < c; i += this.valueChannel.strideSize, a += this.interpolationChannel.strideSize, l += this.lifeChannel.strideSize)
      this.valueChannel.data[i] = this.interpolationChannel.data[a + 0] + this.interpolationChannel.data[a + 1] * 
        this.value.getScale(this.lifeChannel.data[l]); 
  }
  
  public void write(Json json) {
    json.writeValue("value", this.value);
  }
  
  public void read(Json json, JsonValue jsonData) {
    this.value = json.<ScaledNumericValue>readValue("value", ScaledNumericValue.class, jsonData);
  }
}
