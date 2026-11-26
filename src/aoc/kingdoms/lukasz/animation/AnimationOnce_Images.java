package aoc.kingdoms.lukasz.animation;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AnimationOnce_Images {
   private float fPosX;
   private float fPosY;
   private int currentFrameID = 0;
   private long ANIMATION_LAST_UPDATE_TIME;
   private boolean animationFinished = false;

   public AnimationOnce_Images(int nPosX, int nPosY) {
      this.fPosX = nPosX;
      this.fPosY = nPosY;
      this.ANIMATION_LAST_UPDATE_TIME = CFG.currentTimeMillis;
   }

   public AnimationData_Images getAnimationData_Images() {
      return Game.animationManager.animationMove;
   }

   public void update() {
      if (CFG.currentTimeMillis > this.ANIMATION_LAST_UPDATE_TIME + this.getAnimationData_Images().ANIMATION_FRAME_TIME) {
         if (++this.currentFrameID >= this.getAnimationData_Images().getNumOfFrames()) {
            switch (this.getAnimationData_Images().animationType) {
               case LOOP:
                  this.currentFrameID = 0;
                  break;
               case ONCE:
                  this.currentFrameID = 0;
                  this.animationFinished = true;
            }
         }

         this.ANIMATION_LAST_UPDATE_TIME = CFG.currentTimeMillis;
      }
   }

   public final void draw(SpriteBatch oSB) {
      this.getAnimationData_Images().drawFrame(oSB, this.getPosX(), this.getPosY(), this.currentFrameID);
   }

   public int getPosX() {
      return (int)this.fPosX;
   }

   public void setPosX(float fPosX) {
      this.fPosX = fPosX;
   }

   public int getPosY() {
      return (int)this.fPosY;
   }

   public void setPosY(float fPosY) {
      this.fPosY = fPosY;
   }

   public boolean getAnimationFinished() {
      return this.animationFinished;
   }
}
