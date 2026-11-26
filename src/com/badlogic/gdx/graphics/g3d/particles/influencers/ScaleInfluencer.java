package com.badlogic.gdx.graphics.g3d.particles.influencers;

import com.badlogic.gdx.graphics.g3d.particles.ParticleChannels;
import com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent;

public class ScaleInfluencer extends SimpleInfluencer {
  public ScaleInfluencer() {
    this.valueChannelDescriptor = ParticleChannels.Scale;
  }
  
  public void activateParticles(int startIndex, int count) {
    if (this.value.isRelative()) {
      int i = startIndex * this.valueChannel.strideSize, a = startIndex * this.interpolationChannel.strideSize, c = i + count * this.valueChannel.strideSize;
      for (; i < c; i += this.valueChannel.strideSize, a += this.interpolationChannel.strideSize) {
        float start = this.value.newLowValue() * this.controller.scale.x;
        float diff = this.value.newHighValue() * this.controller.scale.x;
        this.interpolationChannel.data[a + 0] = start;
        this.interpolationChannel.data[a + 1] = diff;
        this.valueChannel.data[i] = start + diff * this.value.getScale(0.0F);
      } 
    } else {
      int i = startIndex * this.valueChannel.strideSize, a = startIndex * this.interpolationChannel.strideSize, c = i + count * this.valueChannel.strideSize;
      for (; i < c; i += this.valueChannel.strideSize, a += this.interpolationChannel.strideSize) {
        float start = this.value.newLowValue() * this.controller.scale.x;
        float diff = this.value.newHighValue() * this.controller.scale.x - start;
        this.interpolationChannel.data[a + 0] = start;
        this.interpolationChannel.data[a + 1] = diff;
        this.valueChannel.data[i] = start + diff * this.value.getScale(0.0F);
      } 
    } 
  }
  
  public ScaleInfluencer(ScaleInfluencer scaleInfluencer) {
    super(scaleInfluencer);
  }
  
  public ParticleControllerComponent copy() {
    return new ScaleInfluencer(this);
  }
}
