package com.badlogic.gdx.graphics.g2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.Array;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;

public class ParticleEmitter {
  private static final int UPDATE_SCALE = 1;
  
  private static final int UPDATE_ANGLE = 2;
  
  private static final int UPDATE_ROTATION = 4;
  
  private static final int UPDATE_VELOCITY = 8;
  
  private static final int UPDATE_WIND = 16;
  
  private static final int UPDATE_GRAVITY = 32;
  
  private static final int UPDATE_TINT = 64;
  
  private static final int UPDATE_SPRITE = 128;
  
  private RangedNumericValue delayValue = new RangedNumericValue();
  
  private IndependentScaledNumericValue lifeOffsetValue = new IndependentScaledNumericValue();
  
  private RangedNumericValue durationValue = new RangedNumericValue();
  
  private IndependentScaledNumericValue lifeValue = new IndependentScaledNumericValue();
  
  private ScaledNumericValue emissionValue = new ScaledNumericValue();
  
  private ScaledNumericValue xScaleValue = new ScaledNumericValue();
  
  private ScaledNumericValue yScaleValue = new ScaledNumericValue();
  
  private ScaledNumericValue rotationValue = new ScaledNumericValue();
  
  private ScaledNumericValue velocityValue = new ScaledNumericValue();
  
  private ScaledNumericValue angleValue = new ScaledNumericValue();
  
  private ScaledNumericValue windValue = new ScaledNumericValue();
  
  private ScaledNumericValue gravityValue = new ScaledNumericValue();
  
  private ScaledNumericValue transparencyValue = new ScaledNumericValue();
  
  private GradientColorValue tintValue = new GradientColorValue();
  
  private RangedNumericValue xOffsetValue = new ScaledNumericValue();
  
  private RangedNumericValue yOffsetValue = new ScaledNumericValue();
  
  private ScaledNumericValue spawnWidthValue = new ScaledNumericValue();
  
  private ScaledNumericValue spawnHeightValue = new ScaledNumericValue();
  
  private SpawnShapeValue spawnShapeValue = new SpawnShapeValue();
  
  private RangedNumericValue[] xSizeValues;
  
  private RangedNumericValue[] ySizeValues;
  
  private RangedNumericValue[] motionValues;
  
  private float accumulator;
  
  private Array<Sprite> sprites;
  
  private SpriteMode spriteMode = SpriteMode.single;
  
  private Particle[] particles;
  
  private int minParticleCount;
  
  private int maxParticleCount = 4;
  
  private float x;
  
  private float y;
  
  private String name;
  
  private Array<String> imagePaths;
  
  private int activeCount;
  
  private boolean[] active;
  
  private boolean firstUpdate;
  
  private boolean flipX;
  
  private boolean flipY;
  
  private int updateFlags;
  
  private boolean allowCompletion;
  
  private BoundingBox bounds;
  
  private int emission;
  
  private int emissionDiff;
  
  private int emissionDelta;
  
  private int lifeOffset;
  
  private int lifeOffsetDiff;
  
  private int life;
  
  private int lifeDiff;
  
  private float spawnWidth;
  
  private float spawnWidthDiff;
  
  private float spawnHeight;
  
  private float spawnHeightDiff;
  
  public float duration = 1.0F;
  
  public float durationTimer;
  
  private float delay;
  
  private float delayTimer;
  
  private boolean attached;
  
  private boolean continuous;
  
  private boolean aligned;
  
  private boolean behind;
  
  private boolean additive = true;
  
  private boolean premultipliedAlpha = false;
  
  boolean cleansUpBlendFunction = true;
  
  public ParticleEmitter() {
    initialize();
  }
  
  public ParticleEmitter(BufferedReader reader) throws IOException {
    initialize();
    load(reader);
  }
  
  public ParticleEmitter(ParticleEmitter emitter) {
    this.sprites = new Array<>(emitter.sprites);
    this.name = emitter.name;
    this.imagePaths = new Array<>(emitter.imagePaths);
    setMaxParticleCount(emitter.maxParticleCount);
    this.minParticleCount = emitter.minParticleCount;
    this.delayValue.load(emitter.delayValue);
    this.durationValue.load(emitter.durationValue);
    this.emissionValue.load(emitter.emissionValue);
    this.lifeValue.load(emitter.lifeValue);
    this.lifeOffsetValue.load(emitter.lifeOffsetValue);
    this.xScaleValue.load(emitter.xScaleValue);
    this.yScaleValue.load(emitter.yScaleValue);
    this.rotationValue.load(emitter.rotationValue);
    this.velocityValue.load(emitter.velocityValue);
    this.angleValue.load(emitter.angleValue);
    this.windValue.load(emitter.windValue);
    this.gravityValue.load(emitter.gravityValue);
    this.transparencyValue.load(emitter.transparencyValue);
    this.tintValue.load(emitter.tintValue);
    this.xOffsetValue.load(emitter.xOffsetValue);
    this.yOffsetValue.load(emitter.yOffsetValue);
    this.spawnWidthValue.load(emitter.spawnWidthValue);
    this.spawnHeightValue.load(emitter.spawnHeightValue);
    this.spawnShapeValue.load(emitter.spawnShapeValue);
    this.attached = emitter.attached;
    this.continuous = emitter.continuous;
    this.aligned = emitter.aligned;
    this.behind = emitter.behind;
    this.additive = emitter.additive;
    this.premultipliedAlpha = emitter.premultipliedAlpha;
    this.cleansUpBlendFunction = emitter.cleansUpBlendFunction;
    this.spriteMode = emitter.spriteMode;
    setPosition(emitter.getX(), emitter.getY());
  }
  
  private void initialize() {
    this.sprites = new Array<>();
    this.imagePaths = new Array<>();
    this.durationValue.setAlwaysActive(true);
    this.emissionValue.setAlwaysActive(true);
    this.lifeValue.setAlwaysActive(true);
    this.xScaleValue.setAlwaysActive(true);
    this.transparencyValue.setAlwaysActive(true);
    this.spawnShapeValue.setAlwaysActive(true);
    this.spawnWidthValue.setAlwaysActive(true);
    this.spawnHeightValue.setAlwaysActive(true);
  }
  
  public void setMaxParticleCount(int maxParticleCount) {
    this.maxParticleCount = maxParticleCount;
    this.active = new boolean[maxParticleCount];
    this.activeCount = 0;
    this.particles = new Particle[maxParticleCount];
  }
  
  public void addParticle() {
    int activeCount = this.activeCount;
    if (activeCount == this.maxParticleCount)
      return; 
    boolean[] active = this.active;
    for (int i = 0, n = active.length; i < n; i++) {
      if (!active[i]) {
        activateParticle(i);
        active[i] = true;
        this.activeCount = activeCount + 1;
        break;
      } 
    } 
  }
  
  public void addParticles(int count) {
    count = Math.min(count, this.maxParticleCount - this.activeCount);
    if (count == 0)
      return; 
    boolean[] active = this.active;
    int index = 0, n = active.length;
    for (int i = 0; i < count; ) {
      while (index < n) {
        if (!active[index]) {
          activateParticle(index);
          active[index++] = true;
        } else
          index++; 
      } 
      break;
    } 
    this.activeCount += count;
  }
  
  public void update(float delta) {
    this.accumulator += delta * 1000.0F;
    if (this.accumulator < 1.0F)
      return; 
    int deltaMillis = (int)this.accumulator;
    this.accumulator -= (float)deltaMillis;
    if (this.delayTimer < this.delay) {
      this.delayTimer += (float)deltaMillis;
    } else {
      boolean done = false;
      if (this.firstUpdate) {
        this.firstUpdate = false;
        addParticle();
      } 
      if (this.durationTimer < this.duration) {
        this.durationTimer += (float)deltaMillis;
      } else if (!this.continuous || this.allowCompletion) {
        done = true;
      } else
        restart(); 
      if (!done) {
        this.emissionDelta += deltaMillis;
        float emissionTime = (float)this.emission + (float)this.emissionDiff * this.emissionValue.getScale(this.durationTimer / this.duration);
        if (emissionTime > 0.0F) {
          emissionTime = 1000.0F / emissionTime;
          if ((float)this.emissionDelta >= emissionTime) {
            int emitCount = (int)((float)this.emissionDelta / emissionTime);
            emitCount = Math.min(emitCount, this.maxParticleCount - this.activeCount);
            this.emissionDelta = (int)((float)this.emissionDelta - (float)emitCount * emissionTime);
            this.emissionDelta = (int)((float)this.emissionDelta % emissionTime);
            addParticles(emitCount);
          } 
        } 
        if (this.activeCount < this.minParticleCount)
          addParticles(this.minParticleCount - this.activeCount); 
      } 
    } 
    boolean[] active = this.active;
    int activeCount = this.activeCount;
    Particle[] particles = this.particles;
    for (int i = 0, n = active.length; i < n; i++) {
      if (active[i] && !updateParticle(particles[i], delta, deltaMillis)) {
        active[i] = false;
        activeCount--;
      } 
    } 
    this.activeCount = activeCount;
  }
  
  public void draw(Batch batch) {
    if (this.premultipliedAlpha) {
      batch.setBlendFunction(1, 771);
    } else if (this.additive) {
      batch.setBlendFunction(770, 1);
    } else
      batch.setBlendFunction(770, 771); 
    Particle[] particles = this.particles;
    boolean[] active = this.active;
    for (int i = 0, n = active.length; i < n; i++) {
      if (active[i])
        particles[i].draw(batch); 
    } 
    if (this.cleansUpBlendFunction && (this.additive || this.premultipliedAlpha))
      batch.setBlendFunction(770, 771); 
  }
  
  public void draw(Batch batch, float delta) {
    this.accumulator += delta * 1000.0F;
    if (this.accumulator < 1.0F) {
      draw(batch);
      return;
    } 
    int deltaMillis = (int)this.accumulator;
    this.accumulator -= (float)deltaMillis;
    if (this.premultipliedAlpha) {
      batch.setBlendFunction(1, 771);
    } else if (this.additive) {
      batch.setBlendFunction(770, 1);
    } else
      batch.setBlendFunction(770, 771); 
    Particle[] particles = this.particles;
    boolean[] active = this.active;
    int activeCount = this.activeCount;
    for (int i = 0, n = active.length; i < n; i++) {
      if (active[i]) {
        Particle particle = particles[i];
        if (updateParticle(particle, delta, deltaMillis)) {
          particle.draw(batch);
        } else {
          active[i] = false;
          activeCount--;
        } 
      } 
    } 
    this.activeCount = activeCount;
    if (this.cleansUpBlendFunction && (this.additive || this.premultipliedAlpha))
      batch.setBlendFunction(770, 771); 
    if (this.delayTimer < this.delay) {
      this.delayTimer += (float)deltaMillis;
      return;
    } 
    if (this.firstUpdate) {
      this.firstUpdate = false;
      addParticle();
    } 
    if (this.durationTimer < this.duration) {
      this.durationTimer += (float)deltaMillis;
    } else {
      if (!this.continuous || this.allowCompletion)
        return; 
      restart();
    } 
    this.emissionDelta += deltaMillis;
    float emissionTime = (float)this.emission + (float)this.emissionDiff * this.emissionValue.getScale(this.durationTimer / this.duration);
    if (emissionTime > 0.0F) {
      emissionTime = 1000.0F / emissionTime;
      if ((float)this.emissionDelta >= emissionTime) {
        int emitCount = (int)((float)this.emissionDelta / emissionTime);
        emitCount = Math.min(emitCount, this.maxParticleCount - activeCount);
        this.emissionDelta = (int)((float)this.emissionDelta - (float)emitCount * emissionTime);
        this.emissionDelta = (int)((float)this.emissionDelta % emissionTime);
        addParticles(emitCount);
      } 
    } 
    if (activeCount < this.minParticleCount)
      addParticles(this.minParticleCount - activeCount); 
  }
  
  public void start() {
    this.firstUpdate = true;
    this.allowCompletion = false;
    restart();
  }
  
  public void reset() {
    this.emissionDelta = 0;
    this.durationTimer = this.duration;
    boolean[] active = this.active;
    for (int i = 0, n = active.length; i < n; i++)
      active[i] = false; 
    this.activeCount = 0;
    start();
  }
  
  private void restart() {
    this.delay = this.delayValue.active ? this.delayValue.newLowValue() : 0.0F;
    this.delayTimer = 0.0F;
    this.durationTimer -= this.duration;
    this.duration = this.durationValue.newLowValue();
    this.emission = (int)this.emissionValue.newLowValue();
    this.emissionDiff = (int)this.emissionValue.newHighValue();
    if (!this.emissionValue.isRelative())
      this.emissionDiff -= this.emission; 
    if (!this.lifeValue.independent)
      generateLifeValues(); 
    if (!this.lifeOffsetValue.independent)
      generateLifeOffsetValues(); 
    this.spawnWidth = this.spawnWidthValue.newLowValue();
    this.spawnWidthDiff = this.spawnWidthValue.newHighValue();
    if (!this.spawnWidthValue.isRelative())
      this.spawnWidthDiff -= this.spawnWidth; 
    this.spawnHeight = this.spawnHeightValue.newLowValue();
    this.spawnHeightDiff = this.spawnHeightValue.newHighValue();
    if (!this.spawnHeightValue.isRelative())
      this.spawnHeightDiff -= this.spawnHeight; 
    this.updateFlags = 0;
    if (this.angleValue.active && this.angleValue.timeline.length > 1)
      this.updateFlags |= 0x2; 
    if (this.velocityValue.active)
      this.updateFlags |= 0x8; 
    if (this.xScaleValue.timeline.length > 1)
      this.updateFlags |= 0x1; 
    if (this.yScaleValue.active && this.yScaleValue.timeline.length > 1)
      this.updateFlags |= 0x1; 
    if (this.rotationValue.active && this.rotationValue.timeline.length > 1)
      this.updateFlags |= 0x4; 
    if (this.windValue.active)
      this.updateFlags |= 0x10; 
    if (this.gravityValue.active)
      this.updateFlags |= 0x20; 
    if (this.tintValue.timeline.length > 1)
      this.updateFlags |= 0x40; 
    if (this.spriteMode == SpriteMode.animated)
      this.updateFlags |= 0x80; 
  }
  
  protected Particle newParticle(Sprite sprite) {
    return new Particle(sprite);
  }
  
  protected Particle[] getParticles() {
    return this.particles;
  }
  
  private void activateParticle(int index) {
    float width, height, radiusX, radiusY, scaleY, radius2;
    Sprite sprite = null;
    switch (this.spriteMode) {
      case single:
      case animated:
        sprite = this.sprites.first();
        break;
      case random:
        sprite = this.sprites.random();
        break;
    } 
    Particle particle = this.particles[index];
    if (particle == null) {
      this.particles[index] = particle = newParticle(sprite);
      particle.flip(this.flipX, this.flipY);
    } else
      particle.set(sprite); 
    float percent = this.durationTimer / this.duration;
    int updateFlags = this.updateFlags;
    if (this.lifeValue.independent)
      generateLifeValues(); 
    if (this.lifeOffsetValue.independent)
      generateLifeOffsetValues(); 
    particle.currentLife = particle.life = this.life + (int)((float)this.lifeDiff * this.lifeValue.getScale(percent));
    if (this.velocityValue.active) {
      particle.velocity = this.velocityValue.newLowValue();
      particle.velocityDiff = this.velocityValue.newHighValue();
      if (!this.velocityValue.isRelative())
        particle.velocityDiff -= particle.velocity; 
    } 
    particle.angle = this.angleValue.newLowValue();
    particle.angleDiff = this.angleValue.newHighValue();
    if (!this.angleValue.isRelative())
      particle.angleDiff -= particle.angle; 
    float angle = 0.0F;
    if ((updateFlags & 0x2) == 0) {
      angle = particle.angle + particle.angleDiff * this.angleValue.getScale(0.0F);
      particle.angle = angle;
      particle.angleCos = MathUtils.cosDeg(angle);
      particle.angleSin = MathUtils.sinDeg(angle);
    } 
    float spriteWidth = sprite.getWidth();
    float spriteHeight = sprite.getHeight();
    particle.xScale = this.xScaleValue.newLowValue() / spriteWidth;
    particle.xScaleDiff = this.xScaleValue.newHighValue() / spriteWidth;
    if (!this.xScaleValue.isRelative())
      particle.xScaleDiff -= particle.xScale; 
    if (this.yScaleValue.active) {
      particle.yScale = this.yScaleValue.newLowValue() / spriteHeight;
      particle.yScaleDiff = this.yScaleValue.newHighValue() / spriteHeight;
      if (!this.yScaleValue.isRelative())
        particle.yScaleDiff -= particle.yScale; 
      particle.setScale(particle.xScale + particle.xScaleDiff * this.xScaleValue.getScale(0.0F), particle.yScale + particle.yScaleDiff * 
          this.yScaleValue.getScale(0.0F));
    } else
      particle.setScale(particle.xScale + particle.xScaleDiff * this.xScaleValue.getScale(0.0F)); 
    if (this.rotationValue.active) {
      particle.rotation = this.rotationValue.newLowValue();
      particle.rotationDiff = this.rotationValue.newHighValue();
      if (!this.rotationValue.isRelative())
        particle.rotationDiff -= particle.rotation; 
      float rotation = particle.rotation + particle.rotationDiff * this.rotationValue.getScale(0.0F);
      if (this.aligned)
        rotation += angle; 
      particle.setRotation(rotation);
    } 
    if (this.windValue.active) {
      particle.wind = this.windValue.newLowValue();
      particle.windDiff = this.windValue.newHighValue();
      if (!this.windValue.isRelative())
        particle.windDiff -= particle.wind; 
    } 
    if (this.gravityValue.active) {
      particle.gravity = this.gravityValue.newLowValue();
      particle.gravityDiff = this.gravityValue.newHighValue();
      if (!this.gravityValue.isRelative())
        particle.gravityDiff -= particle.gravity; 
    } 
    float[] color = particle.tint;
    if (color == null)
      particle.tint = color = new float[3]; 
    float[] temp = this.tintValue.getColor(0.0F);
    color[0] = temp[0];
    color[1] = temp[1];
    color[2] = temp[2];
    particle.transparency = this.transparencyValue.newLowValue();
    particle.transparencyDiff = this.transparencyValue.newHighValue() - particle.transparency;
    float x = this.x;
    if (this.xOffsetValue.active)
      x += this.xOffsetValue.newLowValue(); 
    float y = this.y;
    if (this.yOffsetValue.active)
      y += this.yOffsetValue.newLowValue(); 
    switch (this.spawnShapeValue.shape) {
      case square:
        width = this.spawnWidth + this.spawnWidthDiff * this.spawnWidthValue.getScale(percent);
        height = this.spawnHeight + this.spawnHeightDiff * this.spawnHeightValue.getScale(percent);
        x += MathUtils.random(width) - width / 2.0F;
        y += MathUtils.random(height) - height / 2.0F;
        break;
      case ellipse:
        width = this.spawnWidth + this.spawnWidthDiff * this.spawnWidthValue.getScale(percent);
        height = this.spawnHeight + this.spawnHeightDiff * this.spawnHeightValue.getScale(percent);
        radiusX = width / 2.0F;
        radiusY = height / 2.0F;
        if (radiusX == 0.0F || radiusY == 0.0F)
          break; 
        scaleY = radiusX / radiusY;
        if (this.spawnShapeValue.edges) {
          float spawnAngle;
          switch (this.spawnShapeValue.side) {
            case top:
              spawnAngle = -MathUtils.random(179.0F);
              break;
            case bottom:
              spawnAngle = MathUtils.random(179.0F);
              break;
            default:
              spawnAngle = MathUtils.random(360.0F);
              break;
          } 
          float cosDeg = MathUtils.cosDeg(spawnAngle);
          float sinDeg = MathUtils.sinDeg(spawnAngle);
          x += cosDeg * radiusX;
          y += sinDeg * radiusX / scaleY;
          if ((updateFlags & 0x2) == 0) {
            particle.angle = spawnAngle;
            particle.angleCos = cosDeg;
            particle.angleSin = sinDeg;
          } 
          break;
        } 
        radius2 = radiusX * radiusX;
        while (true) {
          float px = MathUtils.random(width) - radiusX;
          float py = MathUtils.random(width) - radiusX;
          if (px * px + py * py <= radius2) {
            x += px;
            y += py / scaleY;
            break;
          } 
        } 
        break;
      case line:
        width = this.spawnWidth + this.spawnWidthDiff * this.spawnWidthValue.getScale(percent);
        height = this.spawnHeight + this.spawnHeightDiff * this.spawnHeightValue.getScale(percent);
        if (width != 0.0F) {
          float lineX = width * MathUtils.random();
          x += lineX;
          y += lineX * height / width;
        } else
          y += height * MathUtils.random(); 
        break;
    } 
    particle.setBounds(x - spriteWidth / 2.0F, y - spriteHeight / 2.0F, spriteWidth, spriteHeight);
    int offsetTime = (int)((float)this.lifeOffset + (float)this.lifeOffsetDiff * this.lifeOffsetValue.getScale(percent));
    if (offsetTime > 0) {
      if (offsetTime >= particle.currentLife)
        offsetTime = particle.currentLife - 1; 
      updateParticle(particle, (float)offsetTime / 1000.0F, offsetTime);
    } 
  }
  
  private boolean updateParticle(Particle particle, float delta, int deltaMillis) {
    float[] color;
    int life = particle.currentLife - deltaMillis;
    if (life <= 0)
      return false; 
    particle.currentLife = life;
    float percent = 1.0F - (float)particle.currentLife / (float)particle.life;
    int updateFlags = this.updateFlags;
    if ((updateFlags & 0x1) != 0)
      if (this.yScaleValue.active) {
        particle.setScale(particle.xScale + particle.xScaleDiff * this.xScaleValue.getScale(percent), particle.yScale + particle.yScaleDiff * 
            this.yScaleValue.getScale(percent));
      } else
        particle.setScale(particle.xScale + particle.xScaleDiff * this.xScaleValue.getScale(percent));  
    if ((updateFlags & 0x8) != 0) {
      float velocityX, velocityY;
      float velocity = (particle.velocity + particle.velocityDiff * this.velocityValue.getScale(percent)) * delta;
      if ((updateFlags & 0x2) != 0) {
        float angle = particle.angle + particle.angleDiff * this.angleValue.getScale(percent);
        velocityX = velocity * MathUtils.cosDeg(angle);
        velocityY = velocity * MathUtils.sinDeg(angle);
        if ((updateFlags & 0x4) != 0) {
          float rotation = particle.rotation + particle.rotationDiff * this.rotationValue.getScale(percent);
          if (this.aligned)
            rotation += angle; 
          particle.setRotation(rotation);
        } 
      } else {
        velocityX = velocity * particle.angleCos;
        velocityY = velocity * particle.angleSin;
        if (this.aligned || (updateFlags & 0x4) != 0) {
          float rotation = particle.rotation + particle.rotationDiff * this.rotationValue.getScale(percent);
          if (this.aligned)
            rotation += particle.angle; 
          particle.setRotation(rotation);
        } 
      } 
      if ((updateFlags & 0x10) != 0)
        velocityX += (particle.wind + particle.windDiff * this.windValue.getScale(percent)) * delta; 
      if ((updateFlags & 0x20) != 0)
        velocityY += (particle.gravity + particle.gravityDiff * this.gravityValue.getScale(percent)) * delta; 
      particle.translate(velocityX, velocityY);
    } else if ((updateFlags & 0x4) != 0) {
      particle.setRotation(particle.rotation + particle.rotationDiff * this.rotationValue.getScale(percent));
    } 
    if ((updateFlags & 0x40) != 0) {
      color = this.tintValue.getColor(percent);
    } else
      color = particle.tint; 
    if (this.premultipliedAlpha) {
      float alphaMultiplier = this.additive ? 0.0F : 1.0F;
      float a = particle.transparency + particle.transparencyDiff * this.transparencyValue.getScale(percent);
      particle.setColor(color[0] * a, color[1] * a, color[2] * a, a * alphaMultiplier);
    } else
      particle.setColor(color[0], color[1], color[2], particle.transparency + particle.transparencyDiff * 
          this.transparencyValue.getScale(percent)); 
    if ((updateFlags & 0x80) != 0) {
      int frame = Math.min((int)(percent * (float)this.sprites.size), this.sprites.size - 1);
      if (particle.frame != frame) {
        Sprite sprite = this.sprites.get(frame);
        float prevSpriteWidth = particle.getWidth();
        float prevSpriteHeight = particle.getHeight();
        particle.setRegion(sprite);
        particle.setSize(sprite.getWidth(), sprite.getHeight());
        particle.setOrigin(sprite.getOriginX(), sprite.getOriginY());
        particle.translate((prevSpriteWidth - sprite.getWidth()) / 2.0F, (prevSpriteHeight - sprite.getHeight()) / 2.0F);
        particle.frame = frame;
      } 
    } 
    return true;
  }
  
  private void generateLifeValues() {
    this.life = (int)this.lifeValue.newLowValue();
    this.lifeDiff = (int)this.lifeValue.newHighValue();
    if (!this.lifeValue.isRelative())
      this.lifeDiff -= this.life; 
  }
  
  private void generateLifeOffsetValues() {
    this.lifeOffset = this.lifeOffsetValue.active ? (int)this.lifeOffsetValue.newLowValue() : 0;
    this.lifeOffsetDiff = (int)this.lifeOffsetValue.newHighValue();
    if (!this.lifeOffsetValue.isRelative())
      this.lifeOffsetDiff -= this.lifeOffset; 
  }
  
  public void setPosition(float x, float y) {
    if (this.attached) {
      float xAmount = x - this.x;
      float yAmount = y - this.y;
      boolean[] active = this.active;
      for (int i = 0, n = active.length; i < n; i++) {
        if (active[i])
          this.particles[i].translate(xAmount, yAmount); 
      } 
    } 
    this.x = x;
    this.y = y;
  }
  
  public void setSprites(Array<Sprite> sprites) {
    this.sprites = sprites;
    if (sprites.size == 0)
      return; 
    for (int i = 0, n = this.particles.length; i < n; i++) {
      float percent;
      Particle particle = this.particles[i];
      if (particle == null)
        break; 
      Sprite sprite = null;
      switch (this.spriteMode) {
        case single:
          sprite = sprites.first();
          break;
        case random:
          sprite = sprites.random();
          break;
        case animated:
          percent = 1.0F - (float)particle.currentLife / (float)particle.life;
          particle.frame = Math.min((int)(percent * (float)sprites.size), sprites.size - 1);
          sprite = sprites.get(particle.frame);
          break;
      } 
      particle.setRegion(sprite);
      particle.setOrigin(sprite.getOriginX(), sprite.getOriginY());
    } 
  }
  
  public void setSpriteMode(SpriteMode spriteMode) {
    this.spriteMode = spriteMode;
  }
  
  public void preAllocateParticles() {
    if (this.sprites.isEmpty())
      throw new IllegalStateException("ParticleEmitter.setSprites() must have been called before preAllocateParticles()"); 
    for (int index = 0; index < this.particles.length; index++) {
      Particle particle = this.particles[index];
      if (particle == null) {
        this.particles[index] = particle = newParticle(this.sprites.first());
        particle.flip(this.flipX, this.flipY);
      } 
    } 
  }
  
  public void allowCompletion() {
    this.allowCompletion = true;
    this.durationTimer = this.duration;
  }
  
  public Array<Sprite> getSprites() {
    return this.sprites;
  }
  
  public SpriteMode getSpriteMode() {
    return this.spriteMode;
  }
  
  public String getName() {
    return this.name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public ScaledNumericValue getLife() {
    return this.lifeValue;
  }
  
  public ScaledNumericValue getXScale() {
    return this.xScaleValue;
  }
  
  public ScaledNumericValue getYScale() {
    return this.yScaleValue;
  }
  
  public ScaledNumericValue getRotation() {
    return this.rotationValue;
  }
  
  public GradientColorValue getTint() {
    return this.tintValue;
  }
  
  public ScaledNumericValue getVelocity() {
    return this.velocityValue;
  }
  
  public ScaledNumericValue getWind() {
    return this.windValue;
  }
  
  public ScaledNumericValue getGravity() {
    return this.gravityValue;
  }
  
  public ScaledNumericValue getAngle() {
    return this.angleValue;
  }
  
  public ScaledNumericValue getEmission() {
    return this.emissionValue;
  }
  
  public ScaledNumericValue getTransparency() {
    return this.transparencyValue;
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
  
  public RangedNumericValue getXOffsetValue() {
    return this.xOffsetValue;
  }
  
  public RangedNumericValue getYOffsetValue() {
    return this.yOffsetValue;
  }
  
  public ScaledNumericValue getSpawnWidth() {
    return this.spawnWidthValue;
  }
  
  public ScaledNumericValue getSpawnHeight() {
    return this.spawnHeightValue;
  }
  
  public SpawnShapeValue getSpawnShape() {
    return this.spawnShapeValue;
  }
  
  public boolean isAttached() {
    return this.attached;
  }
  
  public void setAttached(boolean attached) {
    this.attached = attached;
  }
  
  public boolean isContinuous() {
    return this.continuous;
  }
  
  public void setContinuous(boolean continuous) {
    this.continuous = continuous;
  }
  
  public boolean isAligned() {
    return this.aligned;
  }
  
  public void setAligned(boolean aligned) {
    this.aligned = aligned;
  }
  
  public boolean isAdditive() {
    return this.additive;
  }
  
  public void setAdditive(boolean additive) {
    this.additive = additive;
  }
  
  public boolean cleansUpBlendFunction() {
    return this.cleansUpBlendFunction;
  }
  
  public void setCleansUpBlendFunction(boolean cleansUpBlendFunction) {
    this.cleansUpBlendFunction = cleansUpBlendFunction;
  }
  
  public boolean isBehind() {
    return this.behind;
  }
  
  public void setBehind(boolean behind) {
    this.behind = behind;
  }
  
  public boolean isPremultipliedAlpha() {
    return this.premultipliedAlpha;
  }
  
  public void setPremultipliedAlpha(boolean premultipliedAlpha) {
    this.premultipliedAlpha = premultipliedAlpha;
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
  
  public boolean isComplete() {
    if (this.continuous && !this.allowCompletion)
      return false; 
    if (this.delayTimer < this.delay)
      return false; 
    return (this.durationTimer >= this.duration && this.activeCount == 0);
  }
  
  public float getPercentComplete() {
    if (this.delayTimer < this.delay)
      return 0.0F; 
    return Math.min(1.0F, this.durationTimer / this.duration);
  }
  
  public float getX() {
    return this.x;
  }
  
  public float getY() {
    return this.y;
  }
  
  public int getActiveCount() {
    return this.activeCount;
  }
  
  public Array<String> getImagePaths() {
    return this.imagePaths;
  }
  
  public void setImagePaths(Array<String> imagePaths) {
    this.imagePaths = imagePaths;
  }
  
  public void setFlip(boolean flipX, boolean flipY) {
    this.flipX = flipX;
    this.flipY = flipY;
    if (this.particles == null)
      return; 
    for (int i = 0, n = this.particles.length; i < n; i++) {
      Particle particle = this.particles[i];
      if (particle != null)
        particle.flip(flipX, flipY); 
    } 
  }
  
  public void flipY() {
    this.angleValue.setHigh(-this.angleValue.getHighMin(), -this.angleValue.getHighMax());
    this.angleValue.setLow(-this.angleValue.getLowMin(), -this.angleValue.getLowMax());
    this.gravityValue.setHigh(-this.gravityValue.getHighMin(), -this.gravityValue.getHighMax());
    this.gravityValue.setLow(-this.gravityValue.getLowMin(), -this.gravityValue.getLowMax());
    this.windValue.setHigh(-this.windValue.getHighMin(), -this.windValue.getHighMax());
    this.windValue.setLow(-this.windValue.getLowMin(), -this.windValue.getLowMax());
    this.rotationValue.setHigh(-this.rotationValue.getHighMin(), -this.rotationValue.getHighMax());
    this.rotationValue.setLow(-this.rotationValue.getLowMin(), -this.rotationValue.getLowMax());
    this.yOffsetValue.setLow(-this.yOffsetValue.getLowMin(), -this.yOffsetValue.getLowMax());
  }
  
  public BoundingBox getBoundingBox() {
    if (this.bounds == null)
      this.bounds = new BoundingBox(); 
    Particle[] particles = this.particles;
    boolean[] active = this.active;
    BoundingBox bounds = this.bounds;
    bounds.inf();
    for (int i = 0, n = active.length; i < n; i++) {
      if (active[i]) {
        Rectangle r = particles[i].getBoundingRectangle();
        bounds.ext(r.x, r.y, 0.0F);
        bounds.ext(r.x + r.width, r.y + r.height, 0.0F);
      } 
    } 
    return bounds;
  }
  
  protected RangedNumericValue[] getXSizeValues() {
    if (this.xSizeValues == null) {
      this.xSizeValues = new RangedNumericValue[3];
      this.xSizeValues[0] = this.xScaleValue;
      this.xSizeValues[1] = this.spawnWidthValue;
      this.xSizeValues[2] = this.xOffsetValue;
    } 
    return this.xSizeValues;
  }
  
  protected RangedNumericValue[] getYSizeValues() {
    if (this.ySizeValues == null) {
      this.ySizeValues = new RangedNumericValue[3];
      this.ySizeValues[0] = this.yScaleValue;
      this.ySizeValues[1] = this.spawnHeightValue;
      this.ySizeValues[2] = this.yOffsetValue;
    } 
    return this.ySizeValues;
  }
  
  protected RangedNumericValue[] getMotionValues() {
    if (this.motionValues == null) {
      this.motionValues = new RangedNumericValue[3];
      this.motionValues[0] = this.velocityValue;
      this.motionValues[1] = this.windValue;
      this.motionValues[2] = this.gravityValue;
    } 
    return this.motionValues;
  }
  
  public void scaleSize(float scale) {
    if (scale == 1.0F)
      return; 
    scaleSize(scale, scale);
  }
  
  public void scaleSize(float scaleX, float scaleY) {
    if (scaleX == 1.0F && scaleY == 1.0F)
      return; 
    for (RangedNumericValue value : getXSizeValues())
      value.scale(scaleX); 
    for (RangedNumericValue value : getYSizeValues())
      value.scale(scaleY); 
  }
  
  public void scaleMotion(float scale) {
    if (scale == 1.0F)
      return; 
    for (RangedNumericValue value : getMotionValues())
      value.scale(scale); 
  }
  
  public void matchSize(ParticleEmitter template) {
    matchXSize(template);
    matchYSize(template);
  }
  
  public void matchXSize(ParticleEmitter template) {
    RangedNumericValue[] values = getXSizeValues();
    RangedNumericValue[] templateValues = template.getXSizeValues();
    for (int i = 0; i < values.length; i++)
      values[i].set(templateValues[i]); 
  }
  
  public void matchYSize(ParticleEmitter template) {
    RangedNumericValue[] values = getYSizeValues();
    RangedNumericValue[] templateValues = template.getYSizeValues();
    for (int i = 0; i < values.length; i++)
      values[i].set(templateValues[i]); 
  }
  
  public void matchMotion(ParticleEmitter template) {
    RangedNumericValue[] values = getMotionValues();
    RangedNumericValue[] templateValues = template.getMotionValues();
    for (int i = 0; i < values.length; i++)
      values[i].set(templateValues[i]); 
  }
  
  public void save(Writer output) throws IOException {
    output.write(this.name + "\n");
    output.write("- Delay -\n");
    this.delayValue.save(output);
    output.write("- Duration - \n");
    this.durationValue.save(output);
    output.write("- Count - \n");
    output.write("min: " + this.minParticleCount + "\n");
    output.write("max: " + this.maxParticleCount + "\n");
    output.write("- Emission - \n");
    this.emissionValue.save(output);
    output.write("- Life - \n");
    this.lifeValue.save(output);
    output.write("- Life Offset - \n");
    this.lifeOffsetValue.save(output);
    output.write("- X Offset - \n");
    this.xOffsetValue.save(output);
    output.write("- Y Offset - \n");
    this.yOffsetValue.save(output);
    output.write("- Spawn Shape - \n");
    this.spawnShapeValue.save(output);
    output.write("- Spawn Width - \n");
    this.spawnWidthValue.save(output);
    output.write("- Spawn Height - \n");
    this.spawnHeightValue.save(output);
    output.write("- X Scale - \n");
    this.xScaleValue.save(output);
    output.write("- Y Scale - \n");
    this.yScaleValue.save(output);
    output.write("- Velocity - \n");
    this.velocityValue.save(output);
    output.write("- Angle - \n");
    this.angleValue.save(output);
    output.write("- Rotation - \n");
    this.rotationValue.save(output);
    output.write("- Wind - \n");
    this.windValue.save(output);
    output.write("- Gravity - \n");
    this.gravityValue.save(output);
    output.write("- Tint - \n");
    this.tintValue.save(output);
    output.write("- Transparency - \n");
    this.transparencyValue.save(output);
    output.write("- Options - \n");
    output.write("attached: " + this.attached + "\n");
    output.write("continuous: " + this.continuous + "\n");
    output.write("aligned: " + this.aligned + "\n");
    output.write("additive: " + this.additive + "\n");
    output.write("behind: " + this.behind + "\n");
    output.write("premultipliedAlpha: " + this.premultipliedAlpha + "\n");
    output.write("spriteMode: " + this.spriteMode.toString() + "\n");
    output.write("- Image Paths -\n");
    for (Array.ArrayIterator<String> arrayIterator = this.imagePaths.iterator(); arrayIterator.hasNext(); ) {
      String imagePath = arrayIterator.next();
      output.write(imagePath + "\n");
    } 
    output.write("\n");
  }
  
  public void load(BufferedReader reader) throws IOException {
    try {
      this.name = readString(reader, "name");
      reader.readLine();
      this.delayValue.load(reader);
      reader.readLine();
      this.durationValue.load(reader);
      reader.readLine();
      setMinParticleCount(readInt(reader, "minParticleCount"));
      setMaxParticleCount(readInt(reader, "maxParticleCount"));
      reader.readLine();
      this.emissionValue.load(reader);
      reader.readLine();
      this.lifeValue.load(reader);
      reader.readLine();
      this.lifeOffsetValue.load(reader);
      reader.readLine();
      this.xOffsetValue.load(reader);
      reader.readLine();
      this.yOffsetValue.load(reader);
      reader.readLine();
      this.spawnShapeValue.load(reader);
      reader.readLine();
      this.spawnWidthValue.load(reader);
      reader.readLine();
      this.spawnHeightValue.load(reader);
      String line = reader.readLine();
      if (line.trim().equals("- Scale -")) {
        this.xScaleValue.load(reader);
        this.yScaleValue.setActive(false);
      } else {
        this.xScaleValue.load(reader);
        reader.readLine();
        this.yScaleValue.load(reader);
      } 
      reader.readLine();
      this.velocityValue.load(reader);
      reader.readLine();
      this.angleValue.load(reader);
      reader.readLine();
      this.rotationValue.load(reader);
      reader.readLine();
      this.windValue.load(reader);
      reader.readLine();
      this.gravityValue.load(reader);
      reader.readLine();
      this.tintValue.load(reader);
      reader.readLine();
      this.transparencyValue.load(reader);
      reader.readLine();
      this.attached = readBoolean(reader, "attached");
      this.continuous = readBoolean(reader, "continuous");
      this.aligned = readBoolean(reader, "aligned");
      this.additive = readBoolean(reader, "additive");
      this.behind = readBoolean(reader, "behind");
      line = reader.readLine();
      if (line.startsWith("premultipliedAlpha")) {
        this.premultipliedAlpha = readBoolean(line);
        line = reader.readLine();
      } 
      if (line.startsWith("spriteMode")) {
        this.spriteMode = SpriteMode.valueOf(readString(line));
        line = reader.readLine();
      } 
      Array<String> imagePaths = new Array<>();
      while ((line = reader.readLine()) != null && !line.isEmpty())
        imagePaths.add(line); 
      setImagePaths(imagePaths);
    } catch (RuntimeException ex) {
      if (this.name == null)
        throw ex; 
      throw new RuntimeException("Error parsing emitter: " + this.name, ex);
    } 
  }
  
  static String readString(String line) throws IOException {
    return line.substring(line.indexOf(":") + 1).trim();
  }
  
  static String readString(BufferedReader reader, String name) throws IOException {
    String line = reader.readLine();
    if (line == null)
      throw new IOException("Missing value: " + name); 
    return readString(line);
  }
  
  static boolean readBoolean(String line) throws IOException {
    return Boolean.parseBoolean(readString(line));
  }
  
  static boolean readBoolean(BufferedReader reader, String name) throws IOException {
    return Boolean.parseBoolean(readString(reader, name));
  }
  
  static int readInt(BufferedReader reader, String name) throws IOException {
    return Integer.parseInt(readString(reader, name));
  }
  
  static float readFloat(BufferedReader reader, String name) throws IOException {
    return Float.parseFloat(readString(reader, name));
  }
  
  public static class Particle extends Sprite {
    protected int life;
    
    protected int currentLife;
    
    protected float xScale;
    
    protected float xScaleDiff;
    
    protected float yScale;
    
    protected float yScaleDiff;
    
    protected float rotation;
    
    protected float rotationDiff;
    
    protected float velocity;
    
    protected float velocityDiff;
    
    protected float angle;
    
    protected float angleDiff;
    
    protected float angleCos;
    
    protected float angleSin;
    
    protected float transparency;
    
    protected float transparencyDiff;
    
    protected float wind;
    
    protected float windDiff;
    
    protected float gravity;
    
    protected float gravityDiff;
    
    protected float[] tint;
    
    protected int frame;
    
    public Particle(Sprite sprite) {
      super(sprite);
    }
  }
  
  public static class ParticleValue {
    boolean active;
    
    boolean alwaysActive;
    
    public void setAlwaysActive(boolean alwaysActive) {
      this.alwaysActive = alwaysActive;
    }
    
    public boolean isAlwaysActive() {
      return this.alwaysActive;
    }
    
    public boolean isActive() {
      return (this.alwaysActive || this.active);
    }
    
    public void setActive(boolean active) {
      this.active = active;
    }
    
    public void save(Writer output) throws IOException {
      if (!this.alwaysActive) {
        output.write("active: " + this.active + "\n");
      } else
        this.active = true; 
    }
    
    public void load(BufferedReader reader) throws IOException {
      if (!this.alwaysActive) {
        this.active = ParticleEmitter.readBoolean(reader, "active");
      } else
        this.active = true; 
    }
    
    public void load(ParticleValue value) {
      this.active = value.active;
      this.alwaysActive = value.alwaysActive;
    }
  }
  
  public static class NumericValue extends ParticleValue {
    private float value;
    
    public float getValue() {
      return this.value;
    }
    
    public void setValue(float value) {
      this.value = value;
    }
    
    public void save(Writer output) throws IOException {
      super.save(output);
      if (!this.active)
        return; 
      output.write("value: " + this.value + "\n");
    }
    
    public void load(BufferedReader reader) throws IOException {
      super.load(reader);
      if (!this.active)
        return; 
      this.value = ParticleEmitter.readFloat(reader, "value");
    }
    
    public void load(NumericValue value) {
      load(value);
      this.value = value.value;
    }
  }
  
  public static class RangedNumericValue extends ParticleValue {
    private float lowMin;
    
    private float lowMax;
    
    public float newLowValue() {
      return this.lowMin + (this.lowMax - this.lowMin) * MathUtils.random();
    }
    
    public void setLow(float value) {
      this.lowMin = value;
      this.lowMax = value;
    }
    
    public void setLow(float min, float max) {
      this.lowMin = min;
      this.lowMax = max;
    }
    
    public float getLowMin() {
      return this.lowMin;
    }
    
    public void setLowMin(float lowMin) {
      this.lowMin = lowMin;
    }
    
    public float getLowMax() {
      return this.lowMax;
    }
    
    public void setLowMax(float lowMax) {
      this.lowMax = lowMax;
    }
    
    public void scale(float scale) {
      this.lowMin *= scale;
      this.lowMax *= scale;
    }
    
    public void set(RangedNumericValue value) {
      this.lowMin = value.lowMin;
      this.lowMax = value.lowMax;
    }
    
    public void save(Writer output) throws IOException {
      super.save(output);
      if (!this.active)
        return; 
      output.write("lowMin: " + this.lowMin + "\n");
      output.write("lowMax: " + this.lowMax + "\n");
    }
    
    public void load(BufferedReader reader) throws IOException {
      super.load(reader);
      if (!this.active)
        return; 
      this.lowMin = ParticleEmitter.readFloat(reader, "lowMin");
      this.lowMax = ParticleEmitter.readFloat(reader, "lowMax");
    }
    
    public void load(RangedNumericValue value) {
      load(value);
      this.lowMax = value.lowMax;
      this.lowMin = value.lowMin;
    }
  }
  
  public static class ScaledNumericValue extends RangedNumericValue {
    private float[] scaling = new float[] { 1.0F };
    
    float[] timeline = new float[] { 0.0F };
    
    private float highMin;
    
    private float highMax;
    
    private boolean relative;
    
    public float newHighValue() {
      return this.highMin + (this.highMax - this.highMin) * MathUtils.random();
    }
    
    public void setHigh(float value) {
      this.highMin = value;
      this.highMax = value;
    }
    
    public void setHigh(float min, float max) {
      this.highMin = min;
      this.highMax = max;
    }
    
    public float getHighMin() {
      return this.highMin;
    }
    
    public void setHighMin(float highMin) {
      this.highMin = highMin;
    }
    
    public float getHighMax() {
      return this.highMax;
    }
    
    public void setHighMax(float highMax) {
      this.highMax = highMax;
    }
    
    public void scale(float scale) {
      super.scale(scale);
      this.highMin *= scale;
      this.highMax *= scale;
    }
    
    public void set(ParticleEmitter.RangedNumericValue value) {
      if (value instanceof ScaledNumericValue) {
        set((ScaledNumericValue)value);
      } else
        super.set(value); 
    }
    
    public void set(ScaledNumericValue value) {
      super.set(value);
      this.highMin = value.highMin;
      this.highMax = value.highMax;
      if (this.scaling.length != value.scaling.length) {
        this.scaling = Arrays.copyOf(value.scaling, value.scaling.length);
      } else
        System.arraycopy(value.scaling, 0, this.scaling, 0, this.scaling.length); 
      if (this.timeline.length != value.timeline.length) {
        this.timeline = Arrays.copyOf(value.timeline, value.timeline.length);
      } else
        System.arraycopy(value.timeline, 0, this.timeline, 0, this.timeline.length); 
      this.relative = value.relative;
    }
    
    public float[] getScaling() {
      return this.scaling;
    }
    
    public void setScaling(float[] values) {
      this.scaling = values;
    }
    
    public float[] getTimeline() {
      return this.timeline;
    }
    
    public void setTimeline(float[] timeline) {
      this.timeline = timeline;
    }
    
    public boolean isRelative() {
      return this.relative;
    }
    
    public void setRelative(boolean relative) {
      this.relative = relative;
    }
    
    public float getScale(float percent) {
      int endIndex = -1;
      float[] timeline = this.timeline;
      int n = timeline.length;
      for (int i = 1; i < n; i++) {
        float t = timeline[i];
        if (t > percent) {
          endIndex = i;
          break;
        } 
      } 
      if (endIndex == -1)
        return this.scaling[n - 1]; 
      float[] scaling = this.scaling;
      int startIndex = endIndex - 1;
      float startValue = scaling[startIndex];
      float startTime = timeline[startIndex];
      return startValue + (scaling[endIndex] - startValue) * (percent - startTime) / (timeline[endIndex] - startTime);
    }
    
    public void save(Writer output) throws IOException {
      super.save(output);
      if (!this.active)
        return; 
      output.write("highMin: " + this.highMin + "\n");
      output.write("highMax: " + this.highMax + "\n");
      output.write("relative: " + this.relative + "\n");
      output.write("scalingCount: " + this.scaling.length + "\n");
      for (int j = 0; j < this.scaling.length; j++)
        output.write("scaling" + j + ": " + this.scaling[j] + "\n"); 
      output.write("timelineCount: " + this.timeline.length + "\n");
      for (int i = 0; i < this.timeline.length; i++)
        output.write("timeline" + i + ": " + this.timeline[i] + "\n"); 
    }
    
    public void load(BufferedReader reader) throws IOException {
      super.load(reader);
      if (!this.active)
        return; 
      this.highMin = ParticleEmitter.readFloat(reader, "highMin");
      this.highMax = ParticleEmitter.readFloat(reader, "highMax");
      this.relative = ParticleEmitter.readBoolean(reader, "relative");
      this.scaling = new float[ParticleEmitter.readInt(reader, "scalingCount")];
      for (int j = 0; j < this.scaling.length; j++)
        this.scaling[j] = ParticleEmitter.readFloat(reader, "scaling" + j); 
      this.timeline = new float[ParticleEmitter.readInt(reader, "timelineCount")];
      for (int i = 0; i < this.timeline.length; i++)
        this.timeline[i] = ParticleEmitter.readFloat(reader, "timeline" + i); 
    }
    
    public void load(ScaledNumericValue value) {
      load((ParticleEmitter.RangedNumericValue)value);
      this.highMax = value.highMax;
      this.highMin = value.highMin;
      this.scaling = new float[value.scaling.length];
      System.arraycopy(value.scaling, 0, this.scaling, 0, this.scaling.length);
      this.timeline = new float[value.timeline.length];
      System.arraycopy(value.timeline, 0, this.timeline, 0, this.timeline.length);
      this.relative = value.relative;
    }
  }
  
  public static class IndependentScaledNumericValue extends ScaledNumericValue {
    boolean independent;
    
    public boolean isIndependent() {
      return this.independent;
    }
    
    public void setIndependent(boolean independent) {
      this.independent = independent;
    }
    
    public void set(ParticleEmitter.RangedNumericValue value) {
      if (value instanceof IndependentScaledNumericValue) {
        set((IndependentScaledNumericValue)value);
      } else
        super.set(value); 
    }
    
    public void set(ParticleEmitter.ScaledNumericValue value) {
      if (value instanceof IndependentScaledNumericValue) {
        set((IndependentScaledNumericValue)value);
      } else
        super.set(value); 
    }
    
    public void set(IndependentScaledNumericValue value) {
      super.set((ParticleEmitter.ScaledNumericValue)value);
      this.independent = value.independent;
    }
    
    public void save(Writer output) throws IOException {
      super.save(output);
      output.write("independent: " + this.independent + "\n");
    }
    
    public void load(BufferedReader reader) throws IOException {
      super.load(reader);
      if (reader.markSupported())
        reader.mark(100); 
      String line = reader.readLine();
      if (line == null)
        throw new IOException("Missing value: independent"); 
      if (line.contains("independent")) {
        this.independent = Boolean.parseBoolean(ParticleEmitter.readString(line));
      } else if (reader.markSupported()) {
        reader.reset();
      } else {
        String errorMessage = "The loaded particle effect descriptor file uses an old invalid format. Please download the latest version of the Particle Editor tool and recreate the file by loading and saving it again.";
        Gdx.app.error("ParticleEmitter", errorMessage);
        throw new IOException(errorMessage);
      } 
    }
    
    public void load(IndependentScaledNumericValue value) {
      load((ParticleEmitter.ScaledNumericValue)value);
      this.independent = value.independent;
    }
  }
  
  public static class GradientColorValue extends ParticleValue {
    private static float[] temp = new float[4];
    
    private float[] colors = new float[] { 1.0F, 1.0F, 1.0F };
    
    float[] timeline = new float[] { 0.0F };
    
    public GradientColorValue() {
      this.alwaysActive = true;
    }
    
    public float[] getTimeline() {
      return this.timeline;
    }
    
    public void setTimeline(float[] timeline) {
      this.timeline = timeline;
    }
    
    public float[] getColors() {
      return this.colors;
    }
    
    public void setColors(float[] colors) {
      this.colors = colors;
    }
    
    public float[] getColor(float percent) {
      int startIndex = 0, endIndex = -1;
      float[] timeline = this.timeline;
      int n = timeline.length;
      for (int i = 1; i < n; i++) {
        float t = timeline[i];
        if (t > percent) {
          endIndex = i;
          break;
        } 
        startIndex = i;
      } 
      float startTime = timeline[startIndex];
      startIndex *= 3;
      float r1 = this.colors[startIndex];
      float g1 = this.colors[startIndex + 1];
      float b1 = this.colors[startIndex + 2];
      if (endIndex == -1) {
        temp[0] = r1;
        temp[1] = g1;
        temp[2] = b1;
        return temp;
      } 
      float factor = (percent - startTime) / (timeline[endIndex] - startTime);
      endIndex *= 3;
      temp[0] = r1 + (this.colors[endIndex] - r1) * factor;
      temp[1] = g1 + (this.colors[endIndex + 1] - g1) * factor;
      temp[2] = b1 + (this.colors[endIndex + 2] - b1) * factor;
      return temp;
    }
    
    public void save(Writer output) throws IOException {
      super.save(output);
      if (!this.active)
        return; 
      output.write("colorsCount: " + this.colors.length + "\n");
      for (int j = 0; j < this.colors.length; j++)
        output.write("colors" + j + ": " + this.colors[j] + "\n"); 
      output.write("timelineCount: " + this.timeline.length + "\n");
      for (int i = 0; i < this.timeline.length; i++)
        output.write("timeline" + i + ": " + this.timeline[i] + "\n"); 
    }
    
    public void load(BufferedReader reader) throws IOException {
      super.load(reader);
      if (!this.active)
        return; 
      this.colors = new float[ParticleEmitter.readInt(reader, "colorsCount")];
      for (int j = 0; j < this.colors.length; j++)
        this.colors[j] = ParticleEmitter.readFloat(reader, "colors" + j); 
      this.timeline = new float[ParticleEmitter.readInt(reader, "timelineCount")];
      for (int i = 0; i < this.timeline.length; i++)
        this.timeline[i] = ParticleEmitter.readFloat(reader, "timeline" + i); 
    }
    
    public void load(GradientColorValue value) {
      load(value);
      this.colors = new float[value.colors.length];
      System.arraycopy(value.colors, 0, this.colors, 0, this.colors.length);
      this.timeline = new float[value.timeline.length];
      System.arraycopy(value.timeline, 0, this.timeline, 0, this.timeline.length);
    }
  }
  
  public static class SpawnShapeValue extends ParticleValue {
    ParticleEmitter.SpawnShape shape = ParticleEmitter.SpawnShape.point;
    
    boolean edges;
    
    ParticleEmitter.SpawnEllipseSide side = ParticleEmitter.SpawnEllipseSide.both;
    
    public ParticleEmitter.SpawnShape getShape() {
      return this.shape;
    }
    
    public void setShape(ParticleEmitter.SpawnShape shape) {
      this.shape = shape;
    }
    
    public boolean isEdges() {
      return this.edges;
    }
    
    public void setEdges(boolean edges) {
      this.edges = edges;
    }
    
    public ParticleEmitter.SpawnEllipseSide getSide() {
      return this.side;
    }
    
    public void setSide(ParticleEmitter.SpawnEllipseSide side) {
      this.side = side;
    }
    
    public void save(Writer output) throws IOException {
      super.save(output);
      if (!this.active)
        return; 
      output.write("shape: " + this.shape + "\n");
      if (this.shape == ParticleEmitter.SpawnShape.ellipse) {
        output.write("edges: " + this.edges + "\n");
        output.write("side: " + this.side + "\n");
      } 
    }
    
    public void load(BufferedReader reader) throws IOException {
      super.load(reader);
      if (!this.active)
        return; 
      this.shape = ParticleEmitter.SpawnShape.valueOf(ParticleEmitter.readString(reader, "shape"));
      if (this.shape == ParticleEmitter.SpawnShape.ellipse) {
        this.edges = ParticleEmitter.readBoolean(reader, "edges");
        this.side = ParticleEmitter.SpawnEllipseSide.valueOf(ParticleEmitter.readString(reader, "side"));
      } 
    }
    
    public void load(SpawnShapeValue value) {
      load(value);
      this.shape = value.shape;
      this.edges = value.edges;
      this.side = value.side;
    }
  }
  
  public enum SpawnShape {
    point, line, square, ellipse;
  }
  
  public enum SpawnEllipseSide {
    both, top, bottom;
  }
  
  public enum SpriteMode {
    single, random, animated;
  }
}
