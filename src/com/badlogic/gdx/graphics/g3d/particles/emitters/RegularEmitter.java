package com.badlogic.gdx.graphics.g3d.particles.emitters;

import com.badlogic.gdx.graphics.g3d.particles.ParallelArray;
import com.badlogic.gdx.graphics.g3d.particles.ParticleChannels;
import com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent;
import com.badlogic.gdx.graphics.g3d.particles.values.RangedNumericValue;
import com.badlogic.gdx.graphics.g3d.particles.values.ScaledNumericValue;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class RegularEmitter extends Emitter implements Json.Serializable {
  public RangedNumericValue delayValue;
  
  public RangedNumericValue durationValue;
  
  public ScaledNumericValue lifeOffsetValue;
  
  public ScaledNumericValue lifeValue;
  
  public ScaledNumericValue emissionValue;
  
  protected int emission;
  
  protected int emissionDiff;
  
  protected int emissionDelta;
  
  protected int lifeOffset;
  
  protected int lifeOffsetDiff;
  
  protected int life;
  
  protected int lifeDiff;
  
  protected float duration;
  
  protected float delay;
  
  protected float durationTimer;
  
  protected float delayTimer;
  
  private boolean continuous;
  
  private EmissionMode emissionMode;
  
  private ParallelArray.FloatChannel lifeChannel;
  
  public enum EmissionMode {
    Enabled, EnabledUntilCycleEnd, Disabled;
  }
  
  public RegularEmitter() {
    this.delayValue = new RangedNumericValue();
    this.durationValue = new RangedNumericValue();
    this.lifeOffsetValue = new ScaledNumericValue();
    this.lifeValue = new ScaledNumericValue();
    this.emissionValue = new ScaledNumericValue();
    this.durationValue.setActive(true);
    this.emissionValue.setActive(true);
    this.lifeValue.setActive(true);
    this.continuous = true;
    this.emissionMode = EmissionMode.Enabled;
  }
  
  public RegularEmitter(RegularEmitter regularEmitter) {
    this();
    set(regularEmitter);
  }
  
  public void allocateChannels() {
    this.lifeChannel = this.controller.particles.<ParallelArray.FloatChannel>addChannel(ParticleChannels.Life);
  }
  
  public void start() {
    this.delay = this.delayValue.active ? this.delayValue.newLowValue() : 0.0F;
    this.delayTimer = 0.0F;
    this.durationTimer = 0.0F;
    this.duration = this.durationValue.newLowValue();
    this.percent = this.durationTimer / this.duration;
    this.emission = (int)this.emissionValue.newLowValue();
    this.emissionDiff = (int)this.emissionValue.newHighValue();
    if (!this.emissionValue.isRelative())
      this.emissionDiff -= this.emission; 
    this.life = (int)this.lifeValue.newLowValue();
    this.lifeDiff = (int)this.lifeValue.newHighValue();
    if (!this.lifeValue.isRelative())
      this.lifeDiff -= this.life; 
    this.lifeOffset = this.lifeOffsetValue.active ? (int)this.lifeOffsetValue.newLowValue() : 0;
    this.lifeOffsetDiff = (int)this.lifeOffsetValue.newHighValue();
    if (!this.lifeOffsetValue.isRelative())
      this.lifeOffsetDiff -= this.lifeOffset; 
  }
  
  public void init() {
    super.init();
    this.emissionDelta = 0;
    this.durationTimer = this.duration;
  }
  
  public void activateParticles(int startIndex, int count) {
    int currentTotaLife = this.life + (int)((float)this.lifeDiff * this.lifeValue.getScale(this.percent)), currentLife = currentTotaLife;
    int offsetTime = (int)((float)this.lifeOffset + (float)this.lifeOffsetDiff * this.lifeOffsetValue.getScale(this.percent));
    if (offsetTime > 0) {
      if (offsetTime >= currentLife)
        offsetTime = currentLife - 1; 
      currentLife -= offsetTime;
    } 
    float lifePercent = 1.0F - (float)currentLife / (float)currentTotaLife;
    for (int i = startIndex * this.lifeChannel.strideSize, c = i + count * this.lifeChannel.strideSize; i < c; i += this.lifeChannel.strideSize) {
      this.lifeChannel.data[i + 0] = (float)currentLife;
      this.lifeChannel.data[i + 1] = (float)currentTotaLife;
      this.lifeChannel.data[i + 2] = lifePercent;
    } 
  }
  
  public void update() {
    float deltaMillis = this.controller.deltaTime * 1000.0F;
    if (this.delayTimer < this.delay) {
      this.delayTimer += deltaMillis;
    } else {
      boolean emit = (this.emissionMode != EmissionMode.Disabled);
      if (this.durationTimer < this.duration) {
        this.durationTimer += deltaMillis;
        this.percent = this.durationTimer / this.duration;
      } else if (this.continuous && emit && this.emissionMode == EmissionMode.Enabled) {
        this.controller.start();
      } else
        emit = false; 
      if (emit) {
        this.emissionDelta = (int)((float)this.emissionDelta + deltaMillis);
        float emissionTime = (float)this.emission + (float)this.emissionDiff * this.emissionValue.getScale(this.percent);
        if (emissionTime > 0.0F) {
          emissionTime = 1000.0F / emissionTime;
          if ((float)this.emissionDelta >= emissionTime) {
            int emitCount = (int)((float)this.emissionDelta / emissionTime);
            emitCount = Math.min(emitCount, this.maxParticleCount - this.controller.particles.size);
            this.emissionDelta = (int)((float)this.emissionDelta - (float)emitCount * emissionTime);
            this.emissionDelta = (int)((float)this.emissionDelta % emissionTime);
            addParticles(emitCount);
          } 
        } 
        if (this.controller.particles.size < this.minParticleCount)
          addParticles(this.minParticleCount - this.controller.particles.size); 
      } 
    } 
    int activeParticles = this.controller.particles.size;
    for (int i = 0, k = 0; i < this.controller.particles.size; ) {
      this.lifeChannel.data[k + 0] = this.lifeChannel.data[k + 0] - deltaMillis;
      if (this.lifeChannel.data[k + 0] - deltaMillis <= 0.0F) {
        this.controller.particles.removeElement(i);
        continue;
      } 
      this.lifeChannel.data[k + 2] = 1.0F - this.lifeChannel.data[k + 0] / this.lifeChannel.data[k + 1];
      i++;
      k += this.lifeChannel.strideSize;
    } 
    if (this.controller.particles.size < activeParticles)
      this.controller.killParticles(this.controller.particles.size, activeParticles - this.controller.particles.size); 
  }
  
  private void addParticles(int count) {
    count = Math.min(count, this.maxParticleCount - this.controller.particles.size);
    if (count <= 0)
      return; 
    this.controller.activateParticles(this.controller.particles.size, count);
    this.controller.particles.size += count;
  }
  
  public ScaledNumericValue getLife() {
    return this.lifeValue;
  }
  
  public ScaledNumericValue getEmission() {
    return this.emissionValue;
  }
  
  public RangedNumericValue getDuration() {
    return this.durationValue;
  }
  
  public RangedNumericValue getDelay() {
    return this.delayValue;
  }
  
  public ScaledNumericValue getLifeOffset() {
    return this.lifeOffsetValue;
  }
  
  public boolean isContinuous() {
    return this.continuous;
  }
  
  public void setContinuous(boolean continuous) {
    this.continuous = continuous;
  }
  
  public EmissionMode getEmissionMode() {
    return this.emissionMode;
  }
  
  public void setEmissionMode(EmissionMode emissionMode) {
    this.emissionMode = emissionMode;
  }
  
  public boolean isComplete() {
    if (this.delayTimer < this.delay)
      return false; 
    return (this.durationTimer >= this.duration && this.controller.particles.size == 0);
  }
  
  public float getPercentComplete() {
    if (this.delayTimer < this.delay)
      return 0.0F; 
    return Math.min(1.0F, this.durationTimer / this.duration);
  }
  
  public void set(RegularEmitter emitter) {
    set(emitter);
    this.delayValue.load(emitter.delayValue);
    this.durationValue.load(emitter.durationValue);
    this.lifeOffsetValue.load(emitter.lifeOffsetValue);
    this.lifeValue.load(emitter.lifeValue);
    this.emissionValue.load(emitter.emissionValue);
    this.emission = emitter.emission;
    this.emissionDiff = emitter.emissionDiff;
    this.emissionDelta = emitter.emissionDelta;
    this.lifeOffset = emitter.lifeOffset;
    this.lifeOffsetDiff = emitter.lifeOffsetDiff;
    this.life = emitter.life;
    this.lifeDiff = emitter.lifeDiff;
    this.duration = emitter.duration;
    this.delay = emitter.delay;
    this.durationTimer = emitter.durationTimer;
    this.delayTimer = emitter.delayTimer;
    this.continuous = emitter.continuous;
  }
  
  public ParticleControllerComponent copy() {
    return new RegularEmitter(this);
  }
  
  public void write(Json json) {
    super.write(json);
    json.writeValue("continous", Boolean.valueOf(this.continuous));
    json.writeValue("emission", this.emissionValue);
    json.writeValue("delay", this.delayValue);
    json.writeValue("duration", this.durationValue);
    json.writeValue("life", this.lifeValue);
    json.writeValue("lifeOffset", this.lifeOffsetValue);
  }
  
  public void read(Json json, JsonValue jsonData) {
    super.read(json, jsonData);
    this.continuous = (Boolean)json.<boolean>readValue("continous", boolean.class, jsonData);
    this.emissionValue = json.<ScaledNumericValue>readValue("emission", ScaledNumericValue.class, jsonData);
    this.delayValue = json.<RangedNumericValue>readValue("delay", RangedNumericValue.class, jsonData);
    this.durationValue = json.<RangedNumericValue>readValue("duration", RangedNumericValue.class, jsonData);
    this.lifeValue = json.<ScaledNumericValue>readValue("life", ScaledNumericValue.class, jsonData);
    this.lifeOffsetValue = json.<ScaledNumericValue>readValue("lifeOffset", ScaledNumericValue.class, jsonData);
  }
}
