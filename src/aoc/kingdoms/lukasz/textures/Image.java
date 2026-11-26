package aoc.kingdoms.lukasz.textures;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Image {
   private Texture texture;
   private int iWidth;
   private int iHeight;

   public Image(Texture texture) {
      this.init(texture, TextureFilter.Linear, TextureFilter.Linear, TextureWrap.ClampToEdge, TextureWrap.ClampToEdge);
   }

   public Image(Texture texture, TextureFilter nTextureFilter) {
      this.init(texture, nTextureFilter, nTextureFilter, TextureWrap.ClampToEdge, TextureWrap.ClampToEdge);
   }

   public Image(Texture texture, TextureFilter nTextureFilter, TextureWrap nTextureWrap) {
      this.init(texture, nTextureFilter, nTextureFilter, nTextureWrap, nTextureWrap);
   }

   public Image(Texture texture, TextureFilter minFilter, TextureFilter magFilter) {
      this.init(texture, minFilter, magFilter, TextureWrap.ClampToEdge, TextureWrap.ClampToEdge);
   }

   public Image(Texture texture, TextureFilter minFilter, TextureFilter magFilter, TextureWrap wrapU, TextureWrap wrapV) {
      this.init(texture, minFilter, magFilter, wrapU, wrapV);
   }

   public final void init(Texture texture, TextureFilter minFilter, TextureFilter magFilter, TextureWrap wrapU, TextureWrap wrapV) {
      this.texture = texture;
      this.texture.setFilter(minFilter, magFilter);
      this.texture.setWrap(wrapU, wrapV);
      this.iWidth = texture.getWidth();
      this.iHeight = texture.getHeight();
   }

   public final void draw(SpriteBatch oSB, int nPosX, int nPosY) {
      oSB.draw(this.texture, (float)nPosX, (float)(-(nPosY + this.iHeight)));
   }

   public final void draw(SpriteBatch oSB, int nPosX, int nPosY, boolean flipX, boolean flipY) {
      this.draw_1(oSB, nPosX, nPosY, this.iWidth, this.iHeight, 0, 0, this.iWidth, this.iHeight, flipX, flipY);
   }

   public final void draw(SpriteBatch oSB, int nPosX, int nPosY, float scale) {
      this.draw(
         oSB,
         nPosX,
         nPosY + (int)(this.iHeight * scale - this.iHeight),
         0,
         0,
         this.iWidth,
         this.iHeight,
         scale,
         scale,
         0.0F,
         0,
         0,
         this.iWidth,
         this.iHeight,
         false,
         false
      );
   }

   public final void drawProvince(SpriteBatch oSB, int nPosX, int nPosY, float scale) {
      this.draw(oSB, nPosX, nPosY, 0, 0, this.iWidth, this.iHeight, scale, scale, 0.0F, 0, 0, this.iWidth, this.iHeight, false, false);
   }

   public final void draw(SpriteBatch oSB, int nPosX, int nPosY, float scale, float rotation) {
      this.draw(
         oSB,
         nPosX,
         nPosY + (int)(this.iHeight * scale - this.iHeight),
         0,
         0,
         this.iWidth,
         this.iHeight,
         scale,
         scale,
         rotation,
         0,
         0,
         this.iWidth,
         this.iHeight,
         false,
         false
      );
   }

   public final void draw(SpriteBatch oSB, int nPosX, int nPosY, int nWidth) {
      oSB.draw(this.texture, (float)nPosX, (float)(-nPosY - this.iHeight), (float)nWidth, (float)this.iHeight);
   }

   public final void draw(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, int nHeight) {
      oSB.draw(this.texture, (float)nPosX, (float)(-(nPosY + nHeight)), (float)nWidth, (float)nHeight);
   }

   public final void draw(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, int nHeight, int srcX, int srcY) {
      this.draw2_2(oSB, nPosX, nPosY, nWidth, nHeight, srcX, srcY, this.iWidth, this.iHeight, false, false);
   }

   public final void draw(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, int nHeight, float rotation) {
      this.draw2(oSB, nPosX, nPosY, 0, 0, nWidth, nHeight, 1.0F, 1.0F, rotation, 0, 0, nWidth, nHeight, false, false);
   }

   public final void draw(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, int nHeight, boolean flipX, boolean flipY) {
      this.draw2_2(oSB, nPosX, nPosY, nWidth, nHeight, 0, 0, this.iWidth, this.iHeight, flipX, flipY);
   }

   public final void draw(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, int nHeight, float rotation, int srcX) {
      this.draw2(oSB, nPosX, nPosY, 0, 0, nWidth, nHeight, 1.0F, 1.0F, rotation, srcX, 0, nWidth, nHeight, false, false);
   }

   public final void draw(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, int nHeight, float rotation, int srcX, boolean flipX) {
      this.draw(oSB, nPosX, nPosY, 0, 0, nWidth, -nHeight, 1.0F, 1.0F, rotation, srcX, 0, nWidth, nHeight, flipX, false);
   }

   public final void draw2(SpriteBatch oSB, int nPosX, int nPosY, int nWidth) {
      this.draw_1(oSB, nPosX, nPosY, nWidth, this.iHeight, 0, 0, nWidth, this.iHeight, false, false);
   }

   public final void draw2(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, int nHeight) {
      this.draw2_2(oSB, nPosX, nPosY, nWidth, nHeight, 0, 0, nWidth, nHeight, false, false);
   }

   public final void draw2_Scale(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, int nHeight, float fScale) {
      this.draw2(oSB, nPosX, nPosY, 0, 0, nWidth, nHeight, fScale, fScale, 0.0F, 0, 0, nWidth, nHeight, false, false);
   }

   public final void draw2_Scale(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, int nHeight, boolean flipX, boolean flipY, float fScale) {
      this.draw(oSB, nPosX, nPosY + (int)(nHeight * fScale) - this.iHeight, 0, 0, nWidth, nHeight, fScale, fScale, 0.0F, 0, 0, nWidth, nHeight, flipX, flipY);
   }

   public final void draw2(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, int nHeight, float fRotate) {
      this.draw2(oSB, nPosX, nPosY, 0, 0, nWidth, nHeight, 1.0F, 1.0F, fRotate, 0, 0, nWidth, nHeight, false, false);
   }

   public final void draw2(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, int nHeight, float fRotate, int srcX) {
      this.draw2(oSB, nPosX, nPosY, 0, 0, nWidth, nHeight, 1.0F, 1.0F, fRotate, srcX, 0, nWidth, nHeight, false, false);
   }

   public final void draw2(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, int nHeight, float fRotate, boolean flipX, boolean flipY) {
      this.draw2(oSB, nPosX, nPosY, 0, 0, nWidth, nHeight, 1.0F, 1.0F, fRotate, 0, 0, nWidth, nHeight, flipX, flipY);
   }

   public final void draw2(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, int nHeight, float fRotate, boolean flipX, boolean flipY, int srcX) {
      this.draw2(oSB, nPosX, nPosY, 0, 0, nWidth, nHeight, 1.0F, 1.0F, fRotate, srcX, 0, nWidth, nHeight, flipX, flipY);
   }

   public final void draw2(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, int nHeight, int srcX, int srcY) {
      this.draw2_2(oSB, nPosX, nPosY, nWidth, nHeight, srcX, srcY, nWidth, nHeight, false, false);
   }

   public final void draw2(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, int nHeight, int srcX, int srcY, float fRotate) {
      this.draw2(oSB, nPosX, nPosY, 0, 0, nWidth, nHeight, 1.0F, 1.0F, fRotate, srcX, srcY, nWidth, nHeight, false, false);
   }

   public final void draw2(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, int nHeight, int srcX, int srcY, float fRotate, boolean flipX, boolean flipY) {
      this.draw2(oSB, nPosX, nPosY, 0, 0, nWidth, nHeight, 1.0F, 1.0F, fRotate, srcX, srcY, nWidth, nHeight, flipX, flipY);
   }

   public final void draw2(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, int nHeight, boolean flipX) {
      this.draw2_2(oSB, nPosX, nPosY, nWidth, nHeight, 0, 0, nWidth, nHeight, flipX, false);
   }

   public final void draw2(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, int nHeight, boolean flipX, boolean flipY) {
      this.draw2_2(oSB, nPosX, nPosY, nWidth, nHeight, 0, 0, nWidth, nHeight, flipX, flipY);
   }

   public final void draw2(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, int nHeight, int srcX, int srcY, boolean flipX, boolean flipY) {
      this.draw2_2(oSB, nPosX, nPosY, nWidth, nHeight, srcX, srcY, nWidth, nHeight, flipX, flipY);
   }

   private final void draw(
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
         this.texture,
         nPosX,
         -(nPosY + this.iHeight),
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

   private final void draw_1(
      SpriteBatch oSB, int nPosX, int nPosY, int nWidth, int nHeight, int srcX, int srcY, int srcWidth, int srcHeight, boolean flipX, boolean flipY
   ) {
      oSB.draw(this.texture, nPosX, -(nPosY + this.iHeight), nWidth, nHeight, srcX, srcY, srcWidth, srcHeight, flipX, flipY);
   }

   private final void draw2(
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
         this.texture, nPosX, -(nPosY + nHeight), originX, originY, nWidth, nHeight, scaleX, scaleY, rotation, srcX, srcY, srcWidth, srcHeight, flipX, flipY
      );
   }

   private final void draw2_2(
      SpriteBatch oSB, int nPosX, int nPosY, int nWidth, int nHeight, int srcX, int srcY, int srcWidth, int srcHeight, boolean flipX, boolean flipY
   ) {
      oSB.draw(this.texture, nPosX, -(nPosY + nHeight), nWidth, nHeight, srcX, srcY, srcWidth, srcHeight, flipX, flipY);
   }

   public final Texture getTexture() {
      return this.texture;
   }

   public final int getWidth() {
      return this.iWidth;
   }

   public final int getHeight() {
      return this.iHeight;
   }

   public final void dispose() {
      if (this.texture != null) {
         this.texture.dispose();
      }

      this.texture = null;
   }
}
