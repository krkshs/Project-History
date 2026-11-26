package com.badlogic.gdx.graphics.g3d.particles.influencers;

import com.badlogic.gdx.graphics.g3d.particles.ParallelArray;
import com.badlogic.gdx.graphics.g3d.particles.ParticleChannels;
import com.badlogic.gdx.graphics.g3d.particles.ParticleController;
import com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class ParticleControllerFinalizerInfluencer extends Influencer {
  ParallelArray.FloatChannel positionChannel;
  
  ParallelArray.FloatChannel scaleChannel;
  
  ParallelArray.FloatChannel rotationChannel;
  
  ParallelArray.ObjectChannel<ParticleController> controllerChannel;
  
  boolean hasScale;
  
  boolean hasRotation;
  
  public void init() {
    this.controllerChannel = this.controller.particles.<ParallelArray.ObjectChannel<ParticleController>>getChannel(ParticleChannels.ParticleController);
    if (this.controllerChannel == null)
      throw new GdxRuntimeException("ParticleController channel not found, specify an influencer which will allocate it please."); 
    this.scaleChannel = this.controller.particles.<ParallelArray.FloatChannel>getChannel(ParticleChannels.Scale);
    this.rotationChannel = this.controller.particles.<ParallelArray.FloatChannel>getChannel(ParticleChannels.Rotation3D);
    this.hasScale = (this.scaleChannel != null);
    this.hasRotation = (this.rotationChannel != null);
  }
  
  public void allocateChannels() {
    this.positionChannel = this.controller.particles.<ParallelArray.FloatChannel>addChannel(ParticleChannels.Position);
  }
  
  public void update() {
    for (int i = 0, positionOffset = 0, c = this.controller.particles.size; i < c; i++, positionOffset += this.positionChannel.strideSize) {
      ParticleController particleController = ((ParticleController[])this.controllerChannel.data)[i];
      float scale = this.hasScale ? this.scaleChannel.data[i] : 1.0F;
      float qx = 0.0F, qy = 0.0F, qz = 0.0F, qw = 1.0F;
      if (this.hasRotation) {
        int rotationOffset = i * this.rotationChannel.strideSize;
        qx = this.rotationChannel.data[rotationOffset + 0];
        qy = this.rotationChannel.data[rotationOffset + 1];
        qz = this.rotationChannel.data[rotationOffset + 2];
        qw = this.rotationChannel.data[rotationOffset + 3];
      } 
      particleController.setTransform(this.positionChannel.data[positionOffset + 0], this.positionChannel.data[positionOffset + 1], this.positionChannel.data[positionOffset + 2], qx, qy, qz, qw, scale);
      particleController.update();
    } 
  }
  
  public ParticleControllerFinalizerInfluencer copy() {
    return new ParticleControllerFinalizerInfluencer();
  }
}
