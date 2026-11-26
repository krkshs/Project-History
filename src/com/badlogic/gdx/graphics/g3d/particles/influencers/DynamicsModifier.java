package com.badlogic.gdx.graphics.g3d.particles.influencers;

import com.badlogic.gdx.graphics.g3d.particles.ParallelArray;
import com.badlogic.gdx.graphics.g3d.particles.ParticleChannels;
import com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent;
import com.badlogic.gdx.graphics.g3d.particles.values.ScaledNumericValue;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public abstract class DynamicsModifier extends Influencer {
  protected static final Vector3 TMP_V1 = new Vector3();
  
  protected static final Vector3 TMP_V2 = new Vector3();
  
  protected static final Vector3 TMP_V3 = new Vector3();
  
  protected static final Quaternion TMP_Q = new Quaternion();
  
  public static class FaceDirection extends DynamicsModifier {
    ParallelArray.FloatChannel rotationChannel;
    
    ParallelArray.FloatChannel accellerationChannel;
    
    public FaceDirection() {}
    
    public FaceDirection(FaceDirection rotation) {
      super(rotation);
    }
    
    public void allocateChannels() {
      this.rotationChannel = this.controller.particles.<ParallelArray.FloatChannel>addChannel(ParticleChannels.Rotation3D);
      this.accellerationChannel = this.controller.particles.<ParallelArray.FloatChannel>addChannel(ParticleChannels.Acceleration);
    }
    
    public void update() {
      for (int i = 0, accelOffset = 0, c = i + this.controller.particles.size * this.rotationChannel.strideSize; i < c; i += this.rotationChannel.strideSize, accelOffset += this.accellerationChannel.strideSize) {
        Vector3 axisZ = TMP_V1.set(this.accellerationChannel.data[accelOffset + 0], this.accellerationChannel.data[accelOffset + 1], this.accellerationChannel.data[accelOffset + 2])
          
          .nor();
        Vector3 axisY = TMP_V2.set(TMP_V1)
          .crs(Vector3.Y).nor().crs(TMP_V1).nor(), axisX = TMP_V3.set(axisY).crs(axisZ).nor();
        TMP_Q.setFromAxes(false, axisX.x, axisY.x, axisZ.x, axisX.y, axisY.y, axisZ.y, axisX.z, axisY.z, axisZ.z);
        this.rotationChannel.data[i + 0] = TMP_Q.x;
        this.rotationChannel.data[i + 1] = TMP_Q.y;
        this.rotationChannel.data[i + 2] = TMP_Q.z;
        this.rotationChannel.data[i + 3] = TMP_Q.w;
      } 
    }
    
    public ParticleControllerComponent copy() {
      return new FaceDirection(this);
    }
  }
  
  public static abstract class Strength extends DynamicsModifier {
    protected ParallelArray.FloatChannel strengthChannel;
    
    public ScaledNumericValue strengthValue;
    
    public Strength() {
      this.strengthValue = new ScaledNumericValue();
    }
    
    public Strength(Strength rotation) {
      super(rotation);
      this.strengthValue = new ScaledNumericValue();
      this.strengthValue.load(rotation.strengthValue);
    }
    
    public void allocateChannels() {
      super.allocateChannels();
      ParticleChannels.Interpolation.id = this.controller.particleChannels.newId();
      this.strengthChannel = this.controller.particles.<ParallelArray.FloatChannel>addChannel(ParticleChannels.Interpolation);
    }
    
    public void activateParticles(int startIndex, int count) {
      for (int i = startIndex * this.strengthChannel.strideSize, c = i + count * this.strengthChannel.strideSize; i < c; i += this.strengthChannel.strideSize) {
        float start = this.strengthValue.newLowValue();
        float diff = this.strengthValue.newHighValue();
        if (!this.strengthValue.isRelative())
          diff -= start; 
        this.strengthChannel.data[i + 0] = start;
        this.strengthChannel.data[i + 1] = diff;
      } 
    }
    
    public void write(Json json) {
      super.write(json);
      json.writeValue("strengthValue", this.strengthValue);
    }
    
    public void read(Json json, JsonValue jsonData) {
      super.read(json, jsonData);
      this.strengthValue = json.<ScaledNumericValue>readValue("strengthValue", ScaledNumericValue.class, jsonData);
    }
  }
  
  public static abstract class Angular extends Strength {
    protected ParallelArray.FloatChannel angularChannel;
    
    public ScaledNumericValue thetaValue;
    
    public ScaledNumericValue phiValue;
    
    public Angular() {
      this.thetaValue = new ScaledNumericValue();
      this.phiValue = new ScaledNumericValue();
    }
    
    public Angular(Angular value) {
      super(value);
      this.thetaValue = new ScaledNumericValue();
      this.phiValue = new ScaledNumericValue();
      this.thetaValue.load(value.thetaValue);
      this.phiValue.load(value.phiValue);
    }
    
    public void allocateChannels() {
      super.allocateChannels();
      ParticleChannels.Interpolation4.id = this.controller.particleChannels.newId();
      this.angularChannel = this.controller.particles.<ParallelArray.FloatChannel>addChannel(ParticleChannels.Interpolation4);
    }
    
    public void activateParticles(int startIndex, int count) {
      super.activateParticles(startIndex, count);
      for (int i = startIndex * this.angularChannel.strideSize, c = i + count * this.angularChannel.strideSize; i < c; i += this.angularChannel.strideSize) {
        float start = this.thetaValue.newLowValue();
        float diff = this.thetaValue.newHighValue();
        if (!this.thetaValue.isRelative())
          diff -= start; 
        this.angularChannel.data[i + 0] = start;
        this.angularChannel.data[i + 1] = diff;
        start = this.phiValue.newLowValue();
        diff = this.phiValue.newHighValue();
        if (!this.phiValue.isRelative())
          diff -= start; 
        this.angularChannel.data[i + 2] = start;
        this.angularChannel.data[i + 3] = diff;
      } 
    }
    
    public void write(Json json) {
      super.write(json);
      json.writeValue("thetaValue", this.thetaValue);
      json.writeValue("phiValue", this.phiValue);
    }
    
    public void read(Json json, JsonValue jsonData) {
      super.read(json, jsonData);
      this.thetaValue = json.<ScaledNumericValue>readValue("thetaValue", ScaledNumericValue.class, jsonData);
      this.phiValue = json.<ScaledNumericValue>readValue("phiValue", ScaledNumericValue.class, jsonData);
    }
  }
  
  public static class Rotational2D extends Strength {
    ParallelArray.FloatChannel rotationalVelocity2dChannel;
    
    public Rotational2D() {}
    
    public Rotational2D(Rotational2D rotation) {
      super(rotation);
    }
    
    public void allocateChannels() {
      super.allocateChannels();
      this.rotationalVelocity2dChannel = this.controller.particles.<ParallelArray.FloatChannel>addChannel(ParticleChannels.AngularVelocity2D);
    }
    
    public void update() {
      int i = 0, l = 2, s = 0, c = i + this.controller.particles.size * this.rotationalVelocity2dChannel.strideSize;
      for (; i < c; s += this.strengthChannel.strideSize, i += this.rotationalVelocity2dChannel.strideSize, l += this.lifeChannel.strideSize)
        this.rotationalVelocity2dChannel.data[i] = this.rotationalVelocity2dChannel.data[i] + this.strengthChannel.data[s + 0] + this.strengthChannel.data[s + 1] * 
          
          this.strengthValue.getScale(this.lifeChannel.data[l]); 
    }
    
    public Rotational2D copy() {
      return new Rotational2D(this);
    }
  }
  
  public static class Rotational3D extends Angular {
    ParallelArray.FloatChannel rotationChannel;
    
    ParallelArray.FloatChannel rotationalForceChannel;
    
    public Rotational3D() {}
    
    public Rotational3D(Rotational3D rotation) {
      super(rotation);
    }
    
    public void allocateChannels() {
      super.allocateChannels();
      this.rotationChannel = this.controller.particles.<ParallelArray.FloatChannel>addChannel(ParticleChannels.Rotation3D);
      this.rotationalForceChannel = this.controller.particles.<ParallelArray.FloatChannel>addChannel(ParticleChannels.AngularVelocity3D);
    }
    
    public void update() {
      int i = 0, l = 2, s = 0, a = 0, c = this.controller.particles.size * this.rotationalForceChannel.strideSize;
      for (; i < c; s += this.strengthChannel.strideSize, i += this.rotationalForceChannel.strideSize, a += this.angularChannel.strideSize, l += this.lifeChannel.strideSize) {
        float lifePercent = this.lifeChannel.data[l];
        float strength = this.strengthChannel.data[s + 0] + this.strengthChannel.data[s + 1] * 
          
          this.strengthValue.getScale(lifePercent);
        float phi = this.angularChannel.data[a + 2] + this.angularChannel.data[a + 3] * 
          
          this.phiValue.getScale(lifePercent);
        float theta = this.angularChannel.data[a + 0] + this.angularChannel.data[a + 1] * 
          
          this.thetaValue.getScale(lifePercent);
        float cosTheta = MathUtils.cosDeg(theta), sinTheta = MathUtils.sinDeg(theta), cosPhi = MathUtils.cosDeg(phi);
        float sinPhi = MathUtils.sinDeg(phi);
        TMP_V3.set(cosTheta * sinPhi, cosPhi, sinTheta * sinPhi);
        TMP_V3.scl(strength * 0.017453292F);
        this.rotationalForceChannel.data[i + 0] = this.rotationalForceChannel.data[i + 0] + TMP_V3.x;
        this.rotationalForceChannel.data[i + 1] = this.rotationalForceChannel.data[i + 1] + TMP_V3.y;
        this.rotationalForceChannel.data[i + 2] = this.rotationalForceChannel.data[i + 2] + TMP_V3.z;
      } 
    }
    
    public Rotational3D copy() {
      return new Rotational3D(this);
    }
  }
  
  public static class CentripetalAcceleration extends Strength {
    ParallelArray.FloatChannel accelerationChannel;
    
    ParallelArray.FloatChannel positionChannel;
    
    public CentripetalAcceleration() {}
    
    public CentripetalAcceleration(CentripetalAcceleration rotation) {
      super(rotation);
    }
    
    public void allocateChannels() {
      super.allocateChannels();
      this.accelerationChannel = this.controller.particles.<ParallelArray.FloatChannel>addChannel(ParticleChannels.Acceleration);
      this.positionChannel = this.controller.particles.<ParallelArray.FloatChannel>addChannel(ParticleChannels.Position);
    }
    
    public void update() {
      float cx = 0.0F, cy = 0.0F, cz = 0.0F;
      if (!this.isGlobal) {
        float[] val = this.controller.transform.val;
        cx = val[12];
        cy = val[13];
        cz = val[14];
      } 
      int lifeOffset = 2, strengthOffset = 0, positionOffset = 0, forceOffset = 0;
      for (int i = 0, c = this.controller.particles.size; i < c; i++, positionOffset += this.positionChannel.strideSize, strengthOffset += this.strengthChannel.strideSize, forceOffset += this.accelerationChannel.strideSize, lifeOffset += this.lifeChannel.strideSize) {
        float strength = this.strengthChannel.data[strengthOffset + 0] + this.strengthChannel.data[strengthOffset + 1] * 
          
          this.strengthValue.getScale(this.lifeChannel.data[lifeOffset]);
        TMP_V3.set(this.positionChannel.data[positionOffset + 0] - cx, this.positionChannel.data[positionOffset + 1] - cy, this.positionChannel.data[positionOffset + 2] - cz)
          
          .nor().scl(strength);
        this.accelerationChannel.data[forceOffset + 0] = this.accelerationChannel.data[forceOffset + 0] + TMP_V3.x;
        this.accelerationChannel.data[forceOffset + 1] = this.accelerationChannel.data[forceOffset + 1] + TMP_V3.y;
        this.accelerationChannel.data[forceOffset + 2] = this.accelerationChannel.data[forceOffset + 2] + TMP_V3.z;
      } 
    }
    
    public CentripetalAcceleration copy() {
      return new CentripetalAcceleration(this);
    }
  }
  
  public static class PolarAcceleration extends Angular {
    ParallelArray.FloatChannel directionalVelocityChannel;
    
    public PolarAcceleration() {}
    
    public PolarAcceleration(PolarAcceleration rotation) {
      super(rotation);
    }
    
    public void allocateChannels() {
      super.allocateChannels();
      this.directionalVelocityChannel = this.controller.particles.<ParallelArray.FloatChannel>addChannel(ParticleChannels.Acceleration);
    }
    
    public void update() {
      int i = 0, l = 2, s = 0, a = 0, c = i + this.controller.particles.size * this.directionalVelocityChannel.strideSize;
      for (; i < c; s += this.strengthChannel.strideSize, i += this.directionalVelocityChannel.strideSize, a += this.angularChannel.strideSize, l += this.lifeChannel.strideSize) {
        float lifePercent = this.lifeChannel.data[l];
        float strength = this.strengthChannel.data[s + 0] + this.strengthChannel.data[s + 1] * 
          
          this.strengthValue.getScale(lifePercent);
        float phi = this.angularChannel.data[a + 2] + this.angularChannel.data[a + 3] * 
          
          this.phiValue.getScale(lifePercent);
        float theta = this.angularChannel.data[a + 0] + this.angularChannel.data[a + 1] * 
          
          this.thetaValue.getScale(lifePercent);
        float cosTheta = MathUtils.cosDeg(theta), sinTheta = MathUtils.sinDeg(theta), cosPhi = MathUtils.cosDeg(phi);
        float sinPhi = MathUtils.sinDeg(phi);
        TMP_V3.set(cosTheta * sinPhi, cosPhi, sinTheta * sinPhi).nor().scl(strength);
        if (!this.isGlobal) {
          this.controller.transform.getRotation(TMP_Q, true);
          TMP_V3.mul(TMP_Q);
        } 
        this.directionalVelocityChannel.data[i + 0] = this.directionalVelocityChannel.data[i + 0] + TMP_V3.x;
        this.directionalVelocityChannel.data[i + 1] = this.directionalVelocityChannel.data[i + 1] + TMP_V3.y;
        this.directionalVelocityChannel.data[i + 2] = this.directionalVelocityChannel.data[i + 2] + TMP_V3.z;
      } 
    }
    
    public PolarAcceleration copy() {
      return new PolarAcceleration(this);
    }
  }
  
  public static class TangentialAcceleration extends Angular {
    ParallelArray.FloatChannel directionalVelocityChannel;
    
    ParallelArray.FloatChannel positionChannel;
    
    public TangentialAcceleration() {}
    
    public TangentialAcceleration(TangentialAcceleration rotation) {
      super(rotation);
    }
    
    public void allocateChannels() {
      super.allocateChannels();
      this.directionalVelocityChannel = this.controller.particles.<ParallelArray.FloatChannel>addChannel(ParticleChannels.Acceleration);
      this.positionChannel = this.controller.particles.<ParallelArray.FloatChannel>addChannel(ParticleChannels.Position);
    }
    
    public void update() {
      int i = 0, l = 2, s = 0, a = 0, positionOffset = 0, c = i + this.controller.particles.size * this.directionalVelocityChannel.strideSize;
      for (; i < c; s += this.strengthChannel.strideSize, i += this.directionalVelocityChannel.strideSize, a += this.angularChannel.strideSize, l += this.lifeChannel.strideSize, positionOffset += this.positionChannel.strideSize) {
        float lifePercent = this.lifeChannel.data[l];
        float strength = this.strengthChannel.data[s + 0] + this.strengthChannel.data[s + 1] * 
          
          this.strengthValue.getScale(lifePercent);
        float phi = this.angularChannel.data[a + 2] + this.angularChannel.data[a + 3] * 
          
          this.phiValue.getScale(lifePercent);
        float theta = this.angularChannel.data[a + 0] + this.angularChannel.data[a + 1] * 
          
          this.thetaValue.getScale(lifePercent);
        float cosTheta = MathUtils.cosDeg(theta), sinTheta = MathUtils.sinDeg(theta), cosPhi = MathUtils.cosDeg(phi);
        float sinPhi = MathUtils.sinDeg(phi);
        TMP_V3.set(cosTheta * sinPhi, cosPhi, sinTheta * sinPhi);
        TMP_V1.set(this.positionChannel.data[positionOffset + 0], this.positionChannel.data[positionOffset + 1], this.positionChannel.data[positionOffset + 2]);
        if (!this.isGlobal) {
          this.controller.transform.getTranslation(TMP_V2);
          TMP_V1.sub(TMP_V2);
          this.controller.transform.getRotation(TMP_Q, true);
          TMP_V3.mul(TMP_Q);
        } 
        TMP_V3.crs(TMP_V1).nor().scl(strength);
        this.directionalVelocityChannel.data[i + 0] = this.directionalVelocityChannel.data[i + 0] + TMP_V3.x;
        this.directionalVelocityChannel.data[i + 1] = this.directionalVelocityChannel.data[i + 1] + TMP_V3.y;
        this.directionalVelocityChannel.data[i + 2] = this.directionalVelocityChannel.data[i + 2] + TMP_V3.z;
      } 
    }
    
    public TangentialAcceleration copy() {
      return new TangentialAcceleration(this);
    }
  }
  
  public static class BrownianAcceleration extends Strength {
    ParallelArray.FloatChannel accelerationChannel;
    
    public BrownianAcceleration() {}
    
    public BrownianAcceleration(BrownianAcceleration rotation) {
      super(rotation);
    }
    
    public void allocateChannels() {
      super.allocateChannels();
      this.accelerationChannel = this.controller.particles.<ParallelArray.FloatChannel>addChannel(ParticleChannels.Acceleration);
    }
    
    public void update() {
      int lifeOffset = 2, strengthOffset = 0, forceOffset = 0;
      for (int i = 0, c = this.controller.particles.size; i < c; i++, strengthOffset += this.strengthChannel.strideSize, forceOffset += this.accelerationChannel.strideSize, lifeOffset += this.lifeChannel.strideSize) {
        float strength = this.strengthChannel.data[strengthOffset + 0] + this.strengthChannel.data[strengthOffset + 1] * 
          
          this.strengthValue.getScale(this.lifeChannel.data[lifeOffset]);
        TMP_V3.set(MathUtils.random(-1.0F, 1.0F), MathUtils.random(-1.0F, 1.0F), MathUtils.random(-1.0F, 1.0F)).nor().scl(strength);
        this.accelerationChannel.data[forceOffset + 0] = this.accelerationChannel.data[forceOffset + 0] + TMP_V3.x;
        this.accelerationChannel.data[forceOffset + 1] = this.accelerationChannel.data[forceOffset + 1] + TMP_V3.y;
        this.accelerationChannel.data[forceOffset + 2] = this.accelerationChannel.data[forceOffset + 2] + TMP_V3.z;
      } 
    }
    
    public BrownianAcceleration copy() {
      return new BrownianAcceleration(this);
    }
  }
  
  public boolean isGlobal = false;
  
  protected ParallelArray.FloatChannel lifeChannel;
  
  public DynamicsModifier() {}
  
  public DynamicsModifier(DynamicsModifier modifier) {
    this.isGlobal = modifier.isGlobal;
  }
  
  public void allocateChannels() {
    this.lifeChannel = this.controller.particles.<ParallelArray.FloatChannel>addChannel(ParticleChannels.Life);
  }
  
  public void write(Json json) {
    super.write(json);
    json.writeValue("isGlobal", Boolean.valueOf(this.isGlobal));
  }
  
  public void read(Json json, JsonValue jsonData) {
    super.read(json, jsonData);
    this.isGlobal = (Boolean)json.<boolean>readValue("isGlobal", boolean.class, jsonData);
  }
}
