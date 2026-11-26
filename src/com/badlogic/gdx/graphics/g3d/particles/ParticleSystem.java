package com.badlogic.gdx.graphics.g3d.particles;

import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.RenderableProvider;
import com.badlogic.gdx.graphics.g3d.particles.batches.ParticleBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public final class ParticleSystem implements RenderableProvider {
  private static ParticleSystem instance;
  
  private Array<ParticleBatch<?>> batches;
  
  private Array<ParticleEffect> effects;
  
  @Deprecated
  public static ParticleSystem get() {
    if (instance == null)
      instance = new ParticleSystem(); 
    return instance;
  }
  
  public ParticleSystem() {
    this.batches = new Array<>();
    this.effects = new Array<>();
  }
  
  public void add(ParticleBatch<?> batch) {
    this.batches.add(batch);
  }
  
  public void add(ParticleEffect effect) {
    this.effects.add(effect);
  }
  
  public void remove(ParticleEffect effect) {
    this.effects.removeValue(effect, true);
  }
  
  public void removeAll() {
    this.effects.clear();
  }
  
  public void update() {
    for (Array.ArrayIterator<ParticleEffect> arrayIterator = this.effects.iterator(); arrayIterator.hasNext(); ) {
      ParticleEffect effect = arrayIterator.next();
      effect.update();
    } 
  }
  
  public void updateAndDraw() {
    for (Array.ArrayIterator<ParticleEffect> arrayIterator = this.effects.iterator(); arrayIterator.hasNext(); ) {
      ParticleEffect effect = arrayIterator.next();
      effect.update();
      effect.draw();
    } 
  }
  
  public void update(float deltaTime) {
    for (Array.ArrayIterator<ParticleEffect> arrayIterator = this.effects.iterator(); arrayIterator.hasNext(); ) {
      ParticleEffect effect = arrayIterator.next();
      effect.update(deltaTime);
    } 
  }
  
  public void updateAndDraw(float deltaTime) {
    for (Array.ArrayIterator<ParticleEffect> arrayIterator = this.effects.iterator(); arrayIterator.hasNext(); ) {
      ParticleEffect effect = arrayIterator.next();
      effect.update(deltaTime);
      effect.draw();
    } 
  }
  
  public void begin() {
    for (Array.ArrayIterator<ParticleBatch<?>> arrayIterator = this.batches.iterator(); arrayIterator.hasNext(); ) {
      ParticleBatch<?> batch = arrayIterator.next();
      batch.begin();
    } 
  }
  
  public void draw() {
    for (Array.ArrayIterator<ParticleEffect> arrayIterator = this.effects.iterator(); arrayIterator.hasNext(); ) {
      ParticleEffect effect = arrayIterator.next();
      effect.draw();
    } 
  }
  
  public void end() {
    for (Array.ArrayIterator<ParticleBatch<?>> arrayIterator = this.batches.iterator(); arrayIterator.hasNext(); ) {
      ParticleBatch<?> batch = arrayIterator.next();
      batch.end();
    } 
  }
  
  public void getRenderables(Array<Renderable> renderables, Pool<Renderable> pool) {
    for (Array.ArrayIterator<ParticleBatch<?>> arrayIterator = this.batches.iterator(); arrayIterator.hasNext(); ) {
      ParticleBatch<?> batch = arrayIterator.next();
      batch.getRenderables(renderables, pool);
    } 
  }
  
  public Array<ParticleBatch<?>> getBatches() {
    return this.batches;
  }
}
