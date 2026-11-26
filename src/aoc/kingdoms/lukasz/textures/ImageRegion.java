package aoc.kingdoms.lukasz.textures;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ImageRegion {
   private TextureRegion textureRegion = new TextureRegion();
   private int iWidth;
   private int iHeight;
   private int iRegionWidth = 1;
   private int iRegionHeight = 1;

   protected ImageRegion(Texture texture, int regionWidth, int regionHeight) {
      this.init(texture, TextureFilter.Linear, TextureFilter.Linear, TextureWrap.ClampToEdge, TextureWrap.ClampToEdge, regionWidth, regionHeight);
   }

   protected ImageRegion(Texture texture, TextureFilter nTextureFilter, int regionWidth, int regionHeight) {
      this.init(texture, nTextureFilter, nTextureFilter, TextureWrap.ClampToEdge, TextureWrap.ClampToEdge, regionWidth, regionHeight);
   }

   protected ImageRegion(Texture texture, TextureFilter nTextureFilter, int regionHeight) {
      this.init(texture, nTextureFilter, nTextureFilter, TextureWrap.ClampToEdge, TextureWrap.ClampToEdge, regionHeight);
   }

   protected ImageRegion(Texture texture, TextureFilter nTextureFilter, TextureWrap nTextureWrap, int regionWidth, int regionHeight) {
      this.init(texture, nTextureFilter, nTextureFilter, nTextureWrap, nTextureWrap, regionWidth, regionHeight);
   }

   protected ImageRegion(Texture texture, TextureFilter minFilter, TextureFilter magFilter, int regionWidth, int regionHeight) {
      this.init(texture, minFilter, magFilter, TextureWrap.ClampToEdge, TextureWrap.ClampToEdge, regionWidth, regionHeight);
   }

   protected ImageRegion(
      Texture texture, TextureFilter minFilter, TextureFilter magFilter, TextureWrap wrapU, TextureWrap wrapV, int regionWidth, int regionHeight
   ) {
      this.init(texture, minFilter, magFilter, wrapU, wrapV, regionWidth, regionHeight);
   }

   public final void init(Texture texture, TextureFilter minFilter, TextureFilter magFilter, TextureWrap wrapU, TextureWrap wrapV, int regionHeight) {
      this.textureRegion.setTexture(texture);
      this.textureRegion.getTexture().setFilter(minFilter, magFilter);
      this.textureRegion.getTexture().setWrap(wrapU, wrapV);
      this.iRegionWidth = this.textureRegion.getTexture().getWidth();
      this.iRegionHeight = regionHeight;
      this.iWidth = texture.getWidth();
      this.iHeight = texture.getHeight();
   }

   public final void init(
      Texture texture, TextureFilter minFilter, TextureFilter magFilter, TextureWrap wrapU, TextureWrap wrapV, int regionWidth, int regionHeight
   ) {
      this.textureRegion.setTexture(texture);
      this.textureRegion.getTexture().setFilter(minFilter, magFilter);
      this.textureRegion.getTexture().setWrap(wrapU, wrapV);
      this.iRegionWidth = regionWidth;
      this.iRegionHeight = regionHeight;
      this.iWidth = texture.getWidth();
      this.iHeight = texture.getHeight();
   }

   public final void draw(SpriteBatch oSB) {
      this.draw(oSB, 0, 0, 0, 0, this.iWidth, this.iHeight, 1.0F, 1.0F, 0.0F, 0, 0, this.iWidth, this.iHeight, false, false);
   }

   public final void draw(SpriteBatch oSB, int nPosX, int nPosY) {
      this.draw(oSB, nPosX, nPosY, 0, 0, this.iWidth, this.iHeight, 1.0F, 1.0F, 0.0F, 0, 0, this.iWidth, this.iHeight, false, false);
   }

   public final void draw(SpriteBatch oSB, int nPosX, int nPosY, boolean flipX) {
      this.draw(oSB, nPosX, nPosY, 0, 0, this.iWidth, this.iHeight, 1.0F, 1.0F, 0.0F, 0, 0, this.iWidth, this.iHeight, flipX, false);
   }

   public final void draw(SpriteBatch oSB, int nPosX, int nPosY, boolean flipX, boolean flipY) {
      this.draw(oSB, nPosX, nPosY, 0, 0, this.iWidth, this.iHeight, 1.0F, 1.0F, 0.0F, 0, 0, this.iWidth, this.iHeight, flipX, flipY);
   }

   public final void draw2(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, int nHeight) {
      this.draw(oSB, nPosX, nPosY + (nHeight - this.iHeight), 0, 0, nWidth, nHeight, 1.0F, 1.0F, 0.0F, 0, 0, nWidth, nHeight, false, false);
   }

   public final void draw(
      SpriteBatch oSB,
      int nPosX,
      int nPosY,
      int originX,
      int originY,
      int nWidth,
      int nHeight,
      float scaleX,
      float scaleY,
      float rotation,
      int srcX,
      int srcY,
      int srcWidth,
      int srcHeight,
      boolean flipX,
      boolean flipY
   ) {
      oSB.draw(
         this.textureRegion.getTexture(),
         nPosX,
         -nPosY - this.iHeight,
         originX,
         originY,
         nWidth,
         nHeight,
         scaleX,
         scaleY,
         rotation,
         srcX,
         srcY,
         srcWidth,
         srcHeight,
         flipX,
         flipY
      );
   }

   public final void drawRegion(SpriteBatch oSB) {
      this.drawRegion(oSB, 0, 0);
   }

   public final void drawRegion(SpriteBatch oSB, int nPosX, int nPosY) {
      this.drawRegion(oSB, nPosX, nPosY, this.iRegionWidth, this.iRegionHeight);
   }

   public final void drawRegion(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, int nHeight) {
      oSB.draw(this.textureRegion, (float)nPosX, (float)(-nPosY - nHeight), (float)nWidth, (float)nHeight);
   }

   public final TextureRegion getTextureRegion() {
      return this.textureRegion;
   }

   public final Texture getTexture() {
      return this.textureRegion.getTexture();
   }

   public final int getWidth() {
      return this.iWidth;
   }

   public final int getHeight() {
      return this.iHeight;
   }

   public final int getRegionWidth() {
      return this.iRegionWidth;
   }

   public final int getRegionHeight() {
      return this.iRegionHeight;
   }

   public final void dispose() {
      if (this.textureRegion.getTexture() != null) {
         this.textureRegion.getTexture().dispose();
      }

      this.textureRegion = null;
   }
}
