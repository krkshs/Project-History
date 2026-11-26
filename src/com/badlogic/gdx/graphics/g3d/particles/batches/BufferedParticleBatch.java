package com.badlogic.gdx.graphics.g3d.particles.batches;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSorter;
import com.badlogic.gdx.graphics.g3d.particles.renderers.ParticleControllerRenderData;
import com.badlogic.gdx.utils.Array;

public abstract class BufferedParticleBatch<T extends ParticleControllerRenderData> implements ParticleBatch<T> {
  protected Array<T> renderData;
  
  protected int bufferedParticlesCount;
  
  protected int currentCapacity = 0;
  
  protected ParticleSorter sorter;
  
  protected Camera camera;
  
  protected BufferedParticleBatch(Class<T> type) {
    this.sorter = new ParticleSorter.Distance();
    this.renderData = new Array<>(false, 10, type);
  }
  
  public void begin() {
    this.renderData.clear();
    this.bufferedParticlesCount = 0;
  }
  
  public void draw(T data) {
    if (data.controller.particles.size > 0) {
      this.renderData.add(data);
      this.bufferedParticlesCount += data.controller.particles.size;
    } 
  }
  
  public void end() {
    if (this.bufferedParticlesCount > 0) {
      ensureCapacity(this.bufferedParticlesCount);
      flush(this.sorter.<T>sort(this.renderData));
    } 
  }
  
  public void ensureCapacity(int capacity) {
    if (this.currentCapacity >= capacity)
      return; 
    this.sorter.ensureCapacity(capacity);
    allocParticlesData(capacity);
    this.currentCapacity = capacity;
  }
  
  public void resetCapacity() {
    this.currentCapacity = this.bufferedParticlesCount = 0;
  }
  
  protected abstract void allocParticlesData(int paramInt);
  
  public void setCamera(Camera camera) {
    this.camera = camera;
    this.sorter.setCamera(camera);
  }
  
  public ParticleSorter getSorter() {
    return this.sorter;
  }
  
  public void setSorter(ParticleSorter sorter) {
    this.sorter = sorter;
    sorter.setCamera(this.camera);
    sorter.ensureCapacity(this.currentCapacity);
  }
  
  protected abstract void flush(int[] paramArrayOfint);
  
  public int getBufferedCount() {
    return this.bufferedParticlesCount;
  }
}
