package com.badlogic.gdx.graphics.g3d.particles.influencers;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.particles.ParallelArray;
import com.badlogic.gdx.graphics.g3d.particles.ParticleChannels;
import com.badlogic.gdx.graphics.g3d.particles.ParticleController;
import com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.graphics.g3d.particles.ResourceData;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.Pool;
import java.util.Iterator;

public abstract class ParticleControllerInfluencer extends Influencer {
  public Array<ParticleController> templates;
  
  ParallelArray.ObjectChannel<ParticleController> particleControllerChannel;
  
  public static class Single extends ParticleControllerInfluencer {
    public Single(ParticleController... templates) {
      super(templates);
    }
    
    public Single() {}
    
    public Single(Single particleControllerSingle) {
      super(particleControllerSingle);
    }
    
    public void init() {
      ParticleController first = this.templates.first();
      for (int i = 0, c = this.controller.particles.capacity; i < c; i++) {
        ParticleController copy = first.copy();
        copy.init();
        ((ParticleController[])this.particleControllerChannel.data)[i] = copy;
      } 
    }
    
    public void activateParticles(int startIndex, int count) {
      for (int i = startIndex, c = startIndex + count; i < c; i++)
        ((ParticleController[])this.particleControllerChannel.data)[i].start(); 
    }
    
    public void killParticles(int startIndex, int count) {
      for (int i = startIndex, c = startIndex + count; i < c; i++)
        ((ParticleController[])this.particleControllerChannel.data)[i].end(); 
    }
    
    public Single copy() {
      return new Single(this);
    }
  }
  
  public static class Random extends ParticleControllerInfluencer {
    ParticleControllerPool pool;
    
    private class ParticleControllerPool extends Pool<ParticleController> {
      public ParticleController newObject() {
        ParticleController controller = Random.this.templates.random().copy();
        controller.init();
        return controller;
      }
      
      public void clear() {
        for (int i = 0, free = Random.this.pool.getFree(); i < free; i++)
          Random.this.pool.obtain().dispose(); 
        super.clear();
      }
    }
    
    public Random() {
      this.pool = new ParticleControllerPool();
    }
    
    public Random(ParticleController... templates) {
      super(templates);
      this.pool = new ParticleControllerPool();
    }
    
    public Random(Random particleControllerRandom) {
      super(particleControllerRandom);
      this.pool = new ParticleControllerPool();
    }
    
    public void init() {
      this.pool.clear();
      for (int i = 0; i < this.controller.emitter.maxParticleCount; i++)
        this.pool.free(this.pool.newObject()); 
    }
    
    public void dispose() {
      this.pool.clear();
      super.dispose();
    }
    
    public void activateParticles(int startIndex, int count) {
      for (int i = startIndex, c = startIndex + count; i < c; i++) {
        ParticleController controller = this.pool.obtain();
        controller.start();
        ((ParticleController[])this.particleControllerChannel.data)[i] = controller;
      } 
    }
    
    public void killParticles(int startIndex, int count) {
      for (int i = startIndex, c = startIndex + count; i < c; i++) {
        ParticleController controller = ((ParticleController[])this.particleControllerChannel.data)[i];
        controller.end();
        this.pool.free(controller);
        ((ParticleController[])this.particleControllerChannel.data)[i] = null;
      } 
    }
    
    public Random copy() {
      return new Random(this);
    }
  }
  
  public ParticleControllerInfluencer() {
    this.templates = new Array<>(true, 1, ParticleController.class);
  }
  
  public ParticleControllerInfluencer(ParticleController... templates) {
    this.templates = new Array<>(templates);
  }
  
  public ParticleControllerInfluencer(ParticleControllerInfluencer influencer) {
    this((ParticleController[])influencer.templates.items);
  }
  
  public void allocateChannels() {
    this.particleControllerChannel = this.controller.particles.<ParallelArray.ObjectChannel<ParticleController>>addChannel(ParticleChannels.ParticleController);
  }
  
  public void end() {
    for (int i = 0; i < this.controller.particles.size; i++)
      ((ParticleController[])this.particleControllerChannel.data)[i].end(); 
  }
  
  public void dispose() {
    if (this.controller != null)
      for (int i = 0; i < this.controller.particles.size; i++) {
        ParticleController controller = ((ParticleController[])this.particleControllerChannel.data)[i];
        if (controller != null) {
          controller.dispose();
          ((ParticleController[])this.particleControllerChannel.data)[i] = null;
        } 
      }  
  }
  
  public void save(AssetManager manager, ResourceData resources) {
    ResourceData.SaveData data = resources.createSaveData();
    Array<ParticleEffect> effects = manager.<ParticleEffect>getAll(ParticleEffect.class, new Array<>());
    Array<ParticleController> controllers = new Array<>(this.templates);
    Array<IntArray> effectsIndices = new Array<>();
    for (int i = 0; i < effects.size && controllers.size > 0; i++) {
      ParticleEffect effect = effects.get(i);
      Array<ParticleController> effectControllers = effect.getControllers();
      Iterator<ParticleController> iterator = controllers.iterator();
      IntArray indices = null;
      while (iterator.hasNext()) {
        ParticleController controller = iterator.next();
        int index = -1;
        if ((index = effectControllers.indexOf(controller, true)) > -1) {
          if (indices == null)
            indices = new IntArray(); 
          iterator.remove();
          indices.add(index);
        } 
      } 
      if (indices != null) {
        data.<ParticleEffect>saveAsset(manager.<ParticleEffect>getAssetFileName(effect), ParticleEffect.class);
        effectsIndices.add(indices);
      } 
    } 
    data.save("indices", effectsIndices);
  }
  
  public void load(AssetManager manager, ResourceData resources) {
    ResourceData.SaveData data = resources.getSaveData();
    Array<IntArray> effectsIndices = data.<Array<IntArray>>load("indices");
    Iterator<IntArray> iterator = effectsIndices.iterator();
    AssetDescriptor<ParticleEffect> descriptor;
    while ((descriptor = data.loadAsset()) != null) {
      ParticleEffect effect = manager.<ParticleEffect>get(descriptor);
      if (effect == null)
        throw new RuntimeException("Template is null"); 
      Array<ParticleController> effectControllers = effect.getControllers();
      IntArray effectIndices = iterator.next();
      for (int i = 0, n = effectIndices.size; i < n; i++)
        this.templates.add(effectControllers.get(effectIndices.get(i))); 
    } 
  }
}
