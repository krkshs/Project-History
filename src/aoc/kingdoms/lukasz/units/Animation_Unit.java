package aoc.kingdoms.lukasz.units;

import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.ImageRegion;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Animation_Unit {
   private ImageRegion animation;
   private int iNumOfFrames = 0;
   public int ANIMATION_FRAME_TIME = 48;
   protected Hitbox hitbox;

   protected Animation_Unit(String sFilePath, int regionWidth, int regionHeight, Hitbox hitbox, int FRAME_TIME) {
      this.animation = ImageManager.loadImageRegion(sFilePath, regionWidth, regionHeight);
      this.iNumOfFrames = this.animation.getHeight() / this.animation.getRegionHeight();
      this.hitbox = hitbox;
      this.ANIMATION_FRAME_TIME = FRAME_TIME;
   }

   public final void drawFrame(SpriteBatch oSB, int nPosX, int nPosY, int nDirection, int currentFrameID) {
      this.animation
         .getTextureRegion()
         .setRegion(
            nDirection * this.animation.getRegionWidth(),
            currentFrameID * this.animation.getRegionHeight(),
            this.animation.getRegionWidth(),
            this.animation.getRegionHeight()
         );
      this.animation
         .drawRegion(
            oSB, nPosX + Game.mapCoords.getPosX() - this.animation.getRegionWidth() / 2, nPosY + Game.mapCoords.getPosY() - this.animation.getRegionHeight()
         );
   }

   public final void drawFrameHitboxes(SpriteBatch oSB, int nPosX, int nPosY) {
      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.4F));
      Renderer.drawRect(
         oSB,
         nPosX + Game.mapCoords.getPosX() - this.animation.getRegionWidth() / 2 + this.hitbox.getPosX(),
         nPosY + Game.mapCoords.getPosY() - this.animation.getRegionHeight() + this.hitbox.getPosY(),
         this.hitbox.getWidth(),
         this.hitbox.getHeight()
      );
      oSB.setColor(Color.WHITE);
   }

   protected int getNumOfFrames() {
      return this.iNumOfFrames;
   }
}
