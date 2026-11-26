package com.badlogic.gdx.graphics.g3d.particles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.particles.emitters.Emitter;
import com.badlogic.gdx.graphics.g3d.particles.influencers.Influencer;
import com.badlogic.gdx.graphics.g3d.particles.renderers.ParticleControllerRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.reflect.ClassReflection;

public class ParticleController implements Json.Serializable, ResourceData.Configurable {
  protected static final float DEFAULT_TIME_STEP = 0.016666668F;
  
  public String name;
  
  public Emitter emitter;
  
  public Array<Influencer> influencers;
  
  public ParticleControllerRenderer<?, ?> renderer;
  
  public ParallelArray particles;
  
  public ParticleChannels particleChannels;
  
  public Matrix4 transform;
  
  public Vector3 scale;
  
  protected BoundingBox boundingBox;
  
  public float deltaTime;
  
  public float deltaTimeSqr;
  
  public ParticleController() {
    this.transform = new Matrix4();
    this.scale = new Vector3(1.0F, 1.0F, 1.0F);
    this.influencers = new Array<>(true, 3, Influencer.class);
    setTimeStep(0.016666668F);
  }
  
  public ParticleController(String name, Emitter emitter, ParticleControllerRenderer<?, ?> renderer, Influencer... influencers) {
    this();
    this.name = name;
    this.emitter = emitter;
    this.renderer = renderer;
    this.particleChannels = new ParticleChannels();
    this.influencers = new Array<>(influencers);
  }
  
  private void setTimeStep(float timeStep) {
    this.deltaTime = timeStep;
    this.deltaTimeSqr = this.deltaTime * this.deltaTime;
  }
  
  public void setTransform(Matrix4 transform) {
    this.transform.set(transform);
    transform.getScale(this.scale);
  }
  
  public void setTransform(float x, float y, float z, float qx, float qy, float qz, float qw, float scale) {
    this.transform.set(x, y, z, qx, qy, qz, qw, scale, scale, scale);
    this.scale.set(scale, scale, scale);
  }
  
  public void rotate(Quaternion rotation) {
    this.transform.rotate(rotation);
  }
  
  public void rotate(Vector3 axis, float angle) {
    this.transform.rotate(axis, angle);
  }
  
  public void translate(Vector3 translation) {
    this.transform.translate(translation);
  }
  
  public void setTranslation(Vector3 translation) {
    this.transform.setTranslation(translation);
  }
  
  public void scale(float scaleX, float scaleY, float scaleZ) {
    this.transform.scale(scaleX, scaleY, scaleZ);
    this.transform.getScale(this.scale);
  }
  
  public void scale(Vector3 scale) {
    scale(scale.x, scale.y, scale.z);
  }
  
  public void mul(Matrix4 transform) {
    this.transform.mul(transform);
    this.transform.getScale(this.scale);
  }
  
  public void getTransform(Matrix4 transform) {
    transform.set(this.transform);
  }
  
  public boolean isComplete() {
    return this.emitter.isComplete();
  }
  
  public void init() {
    bind();
    if (this.particles != null) {
      end();
      this.particleChannels.resetIds();
    } 
    allocateChannels(this.emitter.maxParticleCount);
    this.emitter.init();
    for (Array.ArrayIterator<Influencer> arrayIterator = this.influencers.iterator(); arrayIterator.hasNext(); ) {
      Influencer influencer = arrayIterator.next();
      influencer.init();
    } 
    this.renderer.init();
  }
  
  protected void allocateChannels(int maxParticleCount) {
    this.particles = new ParallelArray(maxParticleCount);
    this.emitter.allocateChannels();
    for (Array.ArrayIterator<Influencer> arrayIterator = this.influencers.iterator(); arrayIterator.hasNext(); ) {
      Influencer influencer = arrayIterator.next();
      influencer.allocateChannels();
    } 
    this.renderer.allocateChannels();
  }
  
  protected void bind() {
    this.emitter.set(this);
    for (Array.ArrayIterator<Influencer> arrayIterator = this.influencers.iterator(); arrayIterator.hasNext(); ) {
      Influencer influencer = arrayIterator.next();
      influencer.set(this);
    } 
    this.renderer.set(this);
  }
  
  public void start() {
    this.emitter.start();
    for (Array.ArrayIterator<Influencer> arrayIterator = this.influencers.iterator(); arrayIterator.hasNext(); ) {
      Influencer influencer = arrayIterator.next();
      influencer.start();
    } 
  }
  
  public void reset() {
    end();
    start();
  }
  
  public void end() {
    for (Array.ArrayIterator<Influencer> arrayIterator = this.influencers.iterator(); arrayIterator.hasNext(); ) {
      Influencer influencer = arrayIterator.next();
      influencer.end();
    } 
    this.emitter.end();
  }
  
  public void activateParticles(int startIndex, int count) {
    this.emitter.activateParticles(startIndex, count);
    for (Array.ArrayIterator<Influencer> arrayIterator = this.influencers.iterator(); arrayIterator.hasNext(); ) {
      Influencer influencer = arrayIterator.next();
      influencer.activateParticles(startIndex, count);
    } 
  }
  
  public void killParticles(int startIndex, int count) {
    this.emitter.killParticles(startIndex, count);
    for (Array.ArrayIterator<Influencer> arrayIterator = this.influencers.iterator(); arrayIterator.hasNext(); ) {
      Influencer influencer = arrayIterator.next();
      influencer.killParticles(startIndex, count);
    } 
  }
  
  public void update() {
    update(Gdx.graphics.getDeltaTime());
  }
  
  public void update(float deltaTime) {
    setTimeStep(deltaTime);
    this.emitter.update();
    for (Array.ArrayIterator<Influencer> arrayIterator = this.influencers.iterator(); arrayIterator.hasNext(); ) {
      Influencer influencer = arrayIterator.next();
      influencer.update();
    } 
  }
  
  public void draw() {
    if (this.particles.size > 0)
      this.renderer.update(); 
  }
  
  public ParticleController copy() {
    Emitter emitter = (Emitter)this.emitter.copy();
    Influencer[] influencers = new Influencer[this.influencers.size];
    int i = 0;
    for (Array.ArrayIterator<Influencer> arrayIterator = this.influencers.iterator(); arrayIterator.hasNext(); ) {
      Influencer influencer = arrayIterator.next();
      influencers[i++] = (Influencer)influencer.copy();
    } 
    return new ParticleController(new String(this.name), emitter, (ParticleControllerRenderer<?, ?>)this.renderer.copy(), influencers);
  }
  
  public void dispose() {
    this.emitter.dispose();
    for (Array.ArrayIterator<Influencer> arrayIterator = this.influencers.iterator(); arrayIterator.hasNext(); ) {
      Influencer influencer = arrayIterator.next();
      influencer.dispose();
    } 
  }
  
  public BoundingBox getBoundingBox() {
    if (this.boundingBox == null)
      this.boundingBox = new BoundingBox(); 
    calculateBoundingBox();
    return this.boundingBox;
  }
  
  protected void calculateBoundingBox() {
    this.boundingBox.clr();
    ParallelArray.FloatChannel positionChannel = this.particles.<ParallelArray.FloatChannel>getChannel(ParticleChannels.Position);
    for (int pos = 0, c = positionChannel.strideSize * this.particles.size; pos < c; pos += positionChannel.strideSize)
      this.boundingBox.ext(positionChannel.data[pos + 0], positionChannel.data[pos + 1], positionChannel.data[pos + 2]); 
  }
  
  private <K extends Influencer> int findIndex(Class<K> type) {
    for (int i = 0; i < this.influencers.size; i++) {
      Influencer influencer = this.influencers.get(i);
      if (ClassReflection.isAssignableFrom(type, influencer.getClass()))
        return i; 
    } 
    return -1;
  }
  
  public <K extends Influencer> K findInfluencer(Class<K> influencerClass) {
    int index = findIndex(influencerClass);
    return (index > -1) ? (K)this.influencers.get(index) : null;
  }
  
  public <K extends Influencer> void removeInfluencer(Class<K> type) {
    int index = findIndex(type);
    if (index > -1)
      this.influencers.removeIndex(index); 
  }
  
  public <K extends Influencer> boolean replaceInfluencer(Class<K> type, K newInfluencer) {
    int index = findIndex(type);
    if (index > -1) {
      this.influencers.insert(index, newInfluencer);
      this.influencers.removeIndex(index + 1);
      return true;
    } 
    return false;
  }
  
  public void write(Json json) {
    json.writeValue("name", this.name);
    json.writeValue("emitter", this.emitter, Emitter.class);
    json.writeValue("influencers", this.influencers, Array.class, Influencer.class);
    json.writeValue("renderer", this.renderer, ParticleControllerRenderer.class);
  }
  
  public void read(Json json, JsonValue jsonMap) {
    this.name = json.<String>readValue("name", String.class, jsonMap);
    this.emitter = json.<Emitter>readValue("emitter", Emitter.class, jsonMap);
    this.influencers.addAll((Array<? extends Influencer>)json.<Array>readValue("influencers", Array.class, Influencer.class, jsonMap));
    this.renderer = json.<ParticleControllerRenderer>readValue("renderer", ParticleControllerRenderer.class, jsonMap);
  }
  
  public void save(AssetManager manager, ResourceData data) {
    this.emitter.save(manager, data);
    for (Array.ArrayIterator<Influencer> arrayIterator = this.influencers.iterator(); arrayIterator.hasNext(); ) {
      Influencer influencer = arrayIterator.next();
      influencer.save(manager, data);
    } 
    this.renderer.save(manager, data);
  }
  
  public void load(AssetManager manager, ResourceData data) {
    this.emitter.load(manager, data);
    for (Array.ArrayIterator<Influencer> arrayIterator = this.influencers.iterator(); arrayIterator.hasNext(); ) {
      Influencer influencer = arrayIterator.next();
      influencer.load(manager, data);
    } 
    this.renderer.load(manager, data);
  }
}
