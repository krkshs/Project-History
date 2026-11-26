package com.badlogic.gdx.graphics.g3d.particles.influencers;

import com.badlogic.gdx.graphics.g3d.particles.ParallelArray;
import com.badlogic.gdx.graphics.g3d.particles.ParticleChannels;
import com.badlogic.gdx.graphics.g3d.particles.ParticleController;
import com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import java.util.Arrays;

public class DynamicsInfluencer extends Influencer {
  public Array<DynamicsModifier> velocities;
  
  private ParallelArray.FloatChannel accellerationChannel;
  
  private ParallelArray.FloatChannel positionChannel;
  
  private ParallelArray.FloatChannel previousPositionChannel;
  
  private ParallelArray.FloatChannel rotationChannel;
  
  private ParallelArray.FloatChannel angularVelocityChannel;
  
  boolean hasAcceleration;
  
  boolean has2dAngularVelocity;
  
  boolean has3dAngularVelocity;
  
  public DynamicsInfluencer() {
    this.velocities = new Array<>(true, 3, DynamicsModifier.class);
  }
  
  public DynamicsInfluencer(DynamicsModifier... velocities) {
    this.velocities = new Array<>(true, velocities.length, DynamicsModifier.class);
    for (DynamicsModifier value : velocities)
      this.velocities.add((DynamicsModifier)value.copy()); 
  }
  
  public DynamicsInfluencer(DynamicsInfluencer velocityInfluencer) {
    this(velocityInfluencer.velocities.<DynamicsModifier>toArray(DynamicsModifier.class));
  }
  
  public void allocateChannels() {
    for (int k = 0; k < this.velocities.size; k++)
      ((DynamicsModifier[])this.velocities.items)[k].allocateChannels(); 
    this.accellerationChannel = this.controller.particles.<ParallelArray.FloatChannel>getChannel(ParticleChannels.Acceleration);
    this.hasAcceleration = (this.accellerationChannel != null);
    if (this.hasAcceleration) {
      this.positionChannel = this.controller.particles.<ParallelArray.FloatChannel>addChannel(ParticleChannels.Position);
      this.previousPositionChannel = this.controller.particles.<ParallelArray.FloatChannel>addChannel(ParticleChannels.PreviousPosition);
    } 
    this.angularVelocityChannel = this.controller.particles.<ParallelArray.FloatChannel>getChannel(ParticleChannels.AngularVelocity2D);
    this.has2dAngularVelocity = (this.angularVelocityChannel != null);
    if (this.has2dAngularVelocity) {
      this.rotationChannel = this.controller.particles.<ParallelArray.FloatChannel>addChannel(ParticleChannels.Rotation2D);
      this.has3dAngularVelocity = false;
    } else {
      this.angularVelocityChannel = this.controller.particles.<ParallelArray.FloatChannel>getChannel(ParticleChannels.AngularVelocity3D);
      this.has3dAngularVelocity = (this.angularVelocityChannel != null);
      if (this.has3dAngularVelocity)
        this.rotationChannel = this.controller.particles.<ParallelArray.FloatChannel>addChannel(ParticleChannels.Rotation3D); 
    } 
  }
  
  public void set(ParticleController particleController) {
    super.set(particleController);
    for (int k = 0; k < this.velocities.size; k++)
      ((DynamicsModifier[])this.velocities.items)[k].set(particleController); 
  }
  
  public void init() {
    for (int k = 0; k < this.velocities.size; k++)
      ((DynamicsModifier[])this.velocities.items)[k].init(); 
  }
  
  public void activateParticles(int startIndex, int count) {
    if (this.hasAcceleration)
      for (int i = startIndex * this.positionChannel.strideSize, c = i + count * this.positionChannel.strideSize; i < c; i += this.positionChannel.strideSize) {
        this.previousPositionChannel.data[i + 0] = this.positionChannel.data[i + 0];
        this.previousPositionChannel.data[i + 1] = this.positionChannel.data[i + 1];
        this.previousPositionChannel.data[i + 2] = this.positionChannel.data[i + 2];
      }  
    if (this.has2dAngularVelocity) {
      for (int i = startIndex * this.rotationChannel.strideSize, c = i + count * this.rotationChannel.strideSize; i < c; i += this.rotationChannel.strideSize) {
        this.rotationChannel.data[i + 0] = 1.0F;
        this.rotationChannel.data[i + 1] = 0.0F;
      } 
    } else if (this.has3dAngularVelocity) {
      for (int i = startIndex * this.rotationChannel.strideSize, c = i + count * this.rotationChannel.strideSize; i < c; i += this.rotationChannel.strideSize) {
        this.rotationChannel.data[i + 0] = 0.0F;
        this.rotationChannel.data[i + 1] = 0.0F;
        this.rotationChannel.data[i + 2] = 0.0F;
        this.rotationChannel.data[i + 3] = 1.0F;
      } 
    } 
    for (int k = 0; k < this.velocities.size; k++)
      ((DynamicsModifier[])this.velocities.items)[k].activateParticles(startIndex, count); 
  }
  
  public void update() {
    if (this.hasAcceleration)
      Arrays.fill(this.accellerationChannel.data, 0, this.controller.particles.size * this.accellerationChannel.strideSize, 0.0F); 
    if (this.has2dAngularVelocity || this.has3dAngularVelocity)
      Arrays.fill(this.angularVelocityChannel.data, 0, this.controller.particles.size * this.angularVelocityChannel.strideSize, 0.0F); 
    for (int k = 0; k < this.velocities.size; k++)
      ((DynamicsModifier[])this.velocities.items)[k].update(); 
    if (this.hasAcceleration)
      for (int i = 0, offset = 0; i < this.controller.particles.size; i++, offset += this.positionChannel.strideSize) {
        float x = this.positionChannel.data[offset + 0], y = this.positionChannel.data[offset + 1];
        float z = this.positionChannel.data[offset + 2];
        this.positionChannel.data[offset + 0] = 2.0F * x - this.previousPositionChannel.data[offset + 0] + this.accellerationChannel.data[offset + 0] * this.controller.deltaTimeSqr;
        this.positionChannel.data[offset + 1] = 2.0F * y - this.previousPositionChannel.data[offset + 1] + this.accellerationChannel.data[offset + 1] * this.controller.deltaTimeSqr;
        this.positionChannel.data[offset + 2] = 2.0F * z - this.previousPositionChannel.data[offset + 2] + this.accellerationChannel.data[offset + 2] * this.controller.deltaTimeSqr;
        this.previousPositionChannel.data[offset + 0] = x;
        this.previousPositionChannel.data[offset + 1] = y;
        this.previousPositionChannel.data[offset + 2] = z;
      }  
    if (this.has2dAngularVelocity) {
      for (int i = 0, offset = 0; i < this.controller.particles.size; i++, offset += this.rotationChannel.strideSize) {
        float rotation = this.angularVelocityChannel.data[i] * this.controller.deltaTime;
        if (rotation != 0.0F) {
          float cosBeta = MathUtils.cosDeg(rotation), sinBeta = MathUtils.sinDeg(rotation);
          float currentCosine = this.rotationChannel.data[offset + 0];
          float currentSine = this.rotationChannel.data[offset + 1];
          float newCosine = currentCosine * cosBeta - currentSine * sinBeta, newSine = currentSine * cosBeta + currentCosine * sinBeta;
          this.rotationChannel.data[offset + 0] = newCosine;
          this.rotationChannel.data[offset + 1] = newSine;
        } 
      } 
    } else if (this.has3dAngularVelocity) {
      for (int i = 0, offset = 0, angularOffset = 0; i < this.controller.particles.size; i++, offset += this.rotationChannel.strideSize, angularOffset += this.angularVelocityChannel.strideSize) {
        float wx = this.angularVelocityChannel.data[angularOffset + 0], wy = this.angularVelocityChannel.data[angularOffset + 1];
        float wz = this.angularVelocityChannel.data[angularOffset + 2], qx = this.rotationChannel.data[offset + 0];
        float qy = this.rotationChannel.data[offset + 1], qz = this.rotationChannel.data[offset + 2];
        float qw = this.rotationChannel.data[offset + 3];
        TMP_Q.set(wx, wy, wz, 0.0F).mul(qx, qy, qz, qw).mul(0.5F * this.controller.deltaTime).add(qx, qy, qz, qw).nor();
        this.rotationChannel.data[offset + 0] = TMP_Q.x;
        this.rotationChannel.data[offset + 1] = TMP_Q.y;
        this.rotationChannel.data[offset + 2] = TMP_Q.z;
        this.rotationChannel.data[offset + 3] = TMP_Q.w;
      } 
    } 
  }
  
  public DynamicsInfluencer copy() {
    return new DynamicsInfluencer(this);
  }
  
  public void write(Json json) {
    json.writeValue("velocities", this.velocities, Array.class, DynamicsModifier.class);
  }
  
  public void read(Json json, JsonValue jsonData) {
    this.velocities.addAll((Array<? extends DynamicsModifier>)json.<Array>readValue("velocities", Array.class, DynamicsModifier.class, jsonData));
  }
}
