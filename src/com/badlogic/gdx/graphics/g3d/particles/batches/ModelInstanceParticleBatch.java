package com.badlogic.gdx.graphics.g3d.particles.batches;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.particles.ResourceData;
import com.badlogic.gdx.graphics.g3d.particles.renderers.ModelInstanceControllerRenderData;
import com.badlogic.gdx.graphics.g3d.particles.renderers.ParticleControllerRenderData;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class ModelInstanceParticleBatch implements ParticleBatch<ModelInstanceControllerRenderData> {
  Array<ModelInstanceControllerRenderData> controllersRenderData = new Array<>(false, 5);
  
  int bufferedParticlesCount;
  
  public void getRenderables(Array<Renderable> renderables, Pool<Renderable> pool) {
    for (Array.ArrayIterator<ModelInstanceControllerRenderData> arrayIterator = this.controllersRenderData.iterator(); arrayIterator.hasNext(); ) {
      ModelInstanceControllerRenderData data = arrayIterator.next();
      for (int i = 0, count = data.controller.particles.size; i < count; i++)
        ((ModelInstance[])data.modelInstanceChannel.data)[i].getRenderables(renderables, pool); 
    } 
  }
  
  public int getBufferedCount() {
    return this.bufferedParticlesCount;
  }
  
  public void begin() {
    this.controllersRenderData.clear();
    this.bufferedParticlesCount = 0;
  }
  
  public void end() {}
  
  public void draw(ModelInstanceControllerRenderData data) {
    this.controllersRenderData.add(data);
    this.bufferedParticlesCount += data.controller.particles.size;
  }
  
  public void save(AssetManager manager, ResourceData assetDependencyData) {}
  
  public void load(AssetManager manager, ResourceData assetDependencyData) {}
}
