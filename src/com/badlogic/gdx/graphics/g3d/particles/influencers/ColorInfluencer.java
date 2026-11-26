package com.badlogic.gdx.graphics.g3d.particles.influencers;

import com.badlogic.gdx.graphics.g3d.particles.ParallelArray;
import com.badlogic.gdx.graphics.g3d.particles.ParticleChannels;
import com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent;
import com.badlogic.gdx.graphics.g3d.particles.values.GradientColorValue;
import com.badlogic.gdx.graphics.g3d.particles.values.ScaledNumericValue;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public abstract class ColorInfluencer extends Influencer {
  ParallelArray.FloatChannel colorChannel;
  
  public static class Random extends ColorInfluencer {
    ParallelArray.FloatChannel colorChannel;
    
    public void allocateChannels() {
      this.colorChannel = this.controller.particles.<ParallelArray.FloatChannel>addChannel(ParticleChannels.Color);
    }
    
    public void activateParticles(int startIndex, int count) {
      for (int i = startIndex * this.colorChannel.strideSize, c = i + count * this.colorChannel.strideSize; i < c; i += this.colorChannel.strideSize) {
        this.colorChannel.data[i + 0] = MathUtils.random();
        this.colorChannel.data[i + 1] = MathUtils.random();
        this.colorChannel.data[i + 2] = MathUtils.random();
        this.colorChannel.data[i + 3] = MathUtils.random();
      } 
    }
    
    public Random copy() {
      return new Random();
    }
  }
  
  public static class Single extends ColorInfluencer {
    ParallelArray.FloatChannel alphaInterpolationChannel;
    
    ParallelArray.FloatChannel lifeChannel;
    
    public ScaledNumericValue alphaValue;
    
    public GradientColorValue colorValue;
    
    public Single() {
      this.colorValue = new GradientColorValue();
      this.alphaValue = new ScaledNumericValue();
      this.alphaValue.setHigh(1.0F);
    }
    
    public Single(Single billboardColorInfluencer) {
      this();
      set(billboardColorInfluencer);
    }
    
    public void set(Single colorInfluencer) {
      this.colorValue.load(colorInfluencer.colorValue);
      this.alphaValue.load(colorInfluencer.alphaValue);
    }
    
    public void allocateChannels() {
      super.allocateChannels();
      ParticleChannels.Interpolation.id = this.controller.particleChannels.newId();
      this.alphaInterpolationChannel = this.controller.particles.<ParallelArray.FloatChannel>addChannel(ParticleChannels.Interpolation);
      this.lifeChannel = this.controller.particles.<ParallelArray.FloatChannel>addChannel(ParticleChannels.Life);
    }
    
    public void activateParticles(int startIndex, int count) {
      int i = startIndex * this.colorChannel.strideSize, a = startIndex * this.alphaInterpolationChannel.strideSize, l = startIndex * this.lifeChannel.strideSize + 2;
      for (int c = i + count * this.colorChannel.strideSize; i < c; i += this.colorChannel.strideSize, a += this.alphaInterpolationChannel.strideSize, l += this.lifeChannel.strideSize) {
        float alphaStart = this.alphaValue.newLowValue();
        float alphaDiff = this.alphaValue.newHighValue() - alphaStart;
        this.colorValue.getColor(0.0F, this.colorChannel.data, i);
        this.colorChannel.data[i + 3] = alphaStart + alphaDiff * 
          this.alphaValue.getScale(this.lifeChannel.data[l]);
        this.alphaInterpolationChannel.data[a + 0] = alphaStart;
        this.alphaInterpolationChannel.data[a + 1] = alphaDiff;
      } 
    }
    
    public void update() {
      int i = 0, a = 0, l = 2, c = i + this.controller.particles.size * this.colorChannel.strideSize;
      for (; i < c; i += this.colorChannel.strideSize, a += this.alphaInterpolationChannel.strideSize, l += this.lifeChannel.strideSize) {
        float lifePercent = this.lifeChannel.data[l];
        this.colorValue.getColor(lifePercent, this.colorChannel.data, i);
        this.colorChannel.data[i + 3] = this.alphaInterpolationChannel.data[a + 0] + this.alphaInterpolationChannel.data[a + 1] * 
          
          this.alphaValue.getScale(lifePercent);
      } 
    }
    
    public Single copy() {
      return new Single(this);
    }
    
    public void write(Json json) {
      json.writeValue("alpha", this.alphaValue);
      json.writeValue("color", this.colorValue);
    }
    
    public void read(Json json, JsonValue jsonData) {
      this.alphaValue = json.<ScaledNumericValue>readValue("alpha", ScaledNumericValue.class, jsonData);
      this.colorValue = json.<GradientColorValue>readValue("color", GradientColorValue.class, jsonData);
    }
  }
  
  public void allocateChannels() {
    this.colorChannel = this.controller.particles.<ParallelArray.FloatChannel>addChannel(ParticleChannels.Color);
  }
}
