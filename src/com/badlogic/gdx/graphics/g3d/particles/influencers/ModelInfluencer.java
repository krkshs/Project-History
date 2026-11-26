package com.badlogic.gdx.graphics.g3d.particles.influencers;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.particles.ParallelArray;
import com.badlogic.gdx.graphics.g3d.particles.ParticleChannels;
import com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent;
import com.badlogic.gdx.graphics.g3d.particles.ResourceData;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public abstract class ModelInfluencer extends Influencer {
  public Array<Model> models;
  
  ParallelArray.ObjectChannel<ModelInstance> modelChannel;
  
  public static class Single extends ModelInfluencer {
    public Single() {}
    
    public Single(Single influencer) {
      super(influencer);
    }
    
    public Single(Model... models) {
      super(models);
    }
    
    public void init() {
      Model first = this.models.first();
      for (int i = 0, c = this.controller.emitter.maxParticleCount; i < c; i++)
        ((ModelInstance[])this.modelChannel.data)[i] = new ModelInstance(first); 
    }
    
    public Single copy() {
      return new Single(this);
    }
  }
  
  public static class Random extends ModelInfluencer {
    ModelInstancePool pool;
    
    private class ModelInstancePool extends Pool<ModelInstance> {
      public ModelInstance newObject() {
        return new ModelInstance(Random.this.models.random());
      }
    }
    
    public Random() {
      this.pool = new ModelInstancePool();
    }
    
    public Random(Random influencer) {
      super(influencer);
      this.pool = new ModelInstancePool();
    }
    
    public Random(Model... models) {
      super(models);
      this.pool = new ModelInstancePool();
    }
    
    public void init() {
      this.pool.clear();
    }
    
    public void activateParticles(int startIndex, int count) {
      for (int i = startIndex, c = startIndex + count; i < c; i++)
        ((ModelInstance[])this.modelChannel.data)[i] = this.pool.obtain(); 
    }
    
    public void killParticles(int startIndex, int count) {
      for (int i = startIndex, c = startIndex + count; i < c; i++) {
        this.pool.free(((ModelInstance[])this.modelChannel.data)[i]);
        ((ModelInstance[])this.modelChannel.data)[i] = null;
      } 
    }
    
    public Random copy() {
      return new Random(this);
    }
  }
  
  public ModelInfluencer() {
    this.models = new Array<>(true, 1, Model.class);
  }
  
  public ModelInfluencer(Model... models) {
    this.models = new Array<>(models);
  }
  
  public ModelInfluencer(ModelInfluencer influencer) {
    this(influencer.models.<Model>toArray(Model.class));
  }
  
  public void allocateChannels() {
    this.modelChannel = this.controller.particles.<ParallelArray.ObjectChannel<ModelInstance>>addChannel(ParticleChannels.ModelInstance);
  }
  
  public void save(AssetManager manager, ResourceData resources) {
    ResourceData.SaveData data = resources.createSaveData();
    for (Array.ArrayIterator<Model> arrayIterator = this.models.iterator(); arrayIterator.hasNext(); ) {
      Model model = arrayIterator.next();
      data.<Model>saveAsset(manager.<Model>getAssetFileName(model), Model.class);
    } 
  }
  
  public void load(AssetManager manager, ResourceData resources) {
    ResourceData.SaveData data = resources.getSaveData();
    AssetDescriptor<Model> descriptor;
    while ((descriptor = data.loadAsset()) != null) {
      Model model = manager.<Model>get(descriptor);
      if (model == null)
        throw new RuntimeException("Model is null"); 
      this.models.add(model);
    } 
  }
}
