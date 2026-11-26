package com.badlogic.gdx.graphics.g3d.particles.renderers;

import com.badlogic.gdx.graphics.g3d.particles.ParallelArray;
import com.badlogic.gdx.graphics.g3d.particles.ParticleChannels;
import com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent;
import com.badlogic.gdx.graphics.g3d.particles.batches.BillboardParticleBatch;
import com.badlogic.gdx.graphics.g3d.particles.batches.ParticleBatch;

public class BillboardRenderer extends ParticleControllerRenderer<BillboardControllerRenderData, BillboardParticleBatch> {
  public BillboardRenderer() {
    super(new BillboardControllerRenderData());
  }
  
  public BillboardRenderer(BillboardParticleBatch batch) {
    this();
    setBatch(batch);
  }
  
  public void allocateChannels() {
    this.renderData.positionChannel = this.controller.particles.<ParallelArray.FloatChannel>addChannel(ParticleChannels.Position);
    this.renderData.regionChannel = this.controller.particles.<ParallelArray.FloatChannel>addChannel(ParticleChannels.TextureRegion, ParticleChannels.TextureRegionInitializer.get());
    this.renderData.colorChannel = this.controller.particles.<ParallelArray.FloatChannel>addChannel(ParticleChannels.Color, ParticleChannels.ColorInitializer.get());
    this.renderData.scaleChannel = this.controller.particles.<ParallelArray.FloatChannel>addChannel(ParticleChannels.Scale, ParticleChannels.ScaleInitializer.get());
    this.renderData.rotationChannel = this.controller.particles.<ParallelArray.FloatChannel>addChannel(ParticleChannels.Rotation2D, ParticleChannels.Rotation2dInitializer.get());
  }
  
  public ParticleControllerComponent copy() {
    return new BillboardRenderer(this.batch);
  }
  
  public boolean isCompatible(ParticleBatch<?> batch) {
    return batch instanceof BillboardParticleBatch;
  }
}
