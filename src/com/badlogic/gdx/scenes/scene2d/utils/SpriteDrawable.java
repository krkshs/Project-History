package com.badlogic.gdx.scenes.scene2d.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class SpriteDrawable extends BaseDrawable implements TransformDrawable {
  private Sprite sprite;
  
  public SpriteDrawable() {}
  
  public SpriteDrawable(Sprite sprite) {
    setSprite(sprite);
  }
  
  public SpriteDrawable(SpriteDrawable drawable) {
    super(drawable);
    setSprite(drawable.sprite);
  }
  
  public void draw(Batch batch, float x, float y, float width, float height) {
    Color spriteColor = this.sprite.getColor();
    float oldColor = spriteColor.toFloatBits();
    this.sprite.setColor(spriteColor.mul(batch.getColor()));
    this.sprite.setRotation(0.0F);
    this.sprite.setScale(1.0F, 1.0F);
    this.sprite.setBounds(x, y, width, height);
    this.sprite.draw(batch);
    this.sprite.setPackedColor(oldColor);
  }
  
  public void draw(Batch batch, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation) {
    Color spriteColor = this.sprite.getColor();
    float oldColor = spriteColor.toFloatBits();
    this.sprite.setColor(spriteColor.mul(batch.getColor()));
    this.sprite.setOrigin(originX, originY);
    this.sprite.setRotation(rotation);
    this.sprite.setScale(scaleX, scaleY);
    this.sprite.setBounds(x, y, width, height);
    this.sprite.draw(batch);
    this.sprite.setPackedColor(oldColor);
  }
  
  public void setSprite(Sprite sprite) {
    this.sprite = sprite;
    setMinWidth(sprite.getWidth());
    setMinHeight(sprite.getHeight());
  }
  
  public Sprite getSprite() {
    return this.sprite;
  }
  
  public SpriteDrawable tint(Color tint) {
    Sprite newSprite;
    if (this.sprite instanceof TextureAtlas.AtlasSprite) {
      newSprite = new TextureAtlas.AtlasSprite((TextureAtlas.AtlasSprite)this.sprite);
    } else
      newSprite = new Sprite(this.sprite); 
    newSprite.setColor(tint);
    newSprite.setSize(getMinWidth(), getMinHeight());
    SpriteDrawable drawable = new SpriteDrawable(newSprite);
    drawable.setLeftWidth(getLeftWidth());
    drawable.setRightWidth(getRightWidth());
    drawable.setTopHeight(getTopHeight());
    drawable.setBottomHeight(getBottomHeight());
    return drawable;
  }
}
