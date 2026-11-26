package aoc.kingdoms.lukasz.units;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Unit {
   private int iHP = 1;
   private float fPosX;
   private float fPosY;
   private int iDirection = 0;
   private int currentFrameID = 0;
   private long ANIMATION_LAST_UPDATE_TIME;
   public Path movePath = null;

   public Unit(int nPosX, int nPosY) {
      this.fPosX = nPosX;
      this.fPosY = nPosY;
      this.iHP = this.getUnitType().getMaxHP();
      this.ANIMATION_LAST_UPDATE_TIME = CFG.currentTimeMillis;
   }

   public UnitType getUnitType() {
      return null;
   }

   public void update() {
      this.updatePath();
      if (CFG.currentTimeMillis > this.ANIMATION_LAST_UPDATE_TIME + this.currentAnimation().ANIMATION_FRAME_TIME) {
         if (++this.currentFrameID >= this.currentAnimation().getNumOfFrames()) {
            this.currentFrameID = 0;
         }

         this.ANIMATION_LAST_UPDATE_TIME = CFG.currentTimeMillis;
      }
   }

   protected final void updatePath() {
      if (this.movePath != null) {
         if (this.movePath.pathPoints.get(0).X > this.getPosX()) {
            if (this.movePath.pathPoints.get(0).Y < this.getPosY()) {
               this.setDirection(5);
               return;
            }

            if (this.movePath.pathPoints.get(0).Y > this.getPosY()) {
               this.setDirection(7);
               return;
            }

            this.setDirection(6);
            return;
         }

         if (this.movePath.pathPoints.get(0).X < this.getPosX()) {
            if (this.movePath.pathPoints.get(0).Y < this.getPosY()) {
               this.setDirection(3);
               return;
            }

            if (this.movePath.pathPoints.get(0).Y > this.getPosY()) {
               this.setDirection(1);
               return;
            }

            this.setDirection(2);
            return;
         }

         if (this.movePath.pathPoints.get(0).Y > this.getPosY()) {
            if (this.movePath.pathPoints.get(0).X > this.getPosX()) {
               this.setDirection(7);
               return;
            }

            if (this.movePath.pathPoints.get(0).X < this.getPosX()) {
               this.setDirection(1);
               return;
            }

            this.setDirection(0);
            return;
         }

         if (this.movePath.pathPoints.get(0).Y < this.getPosY()) {
            if (this.movePath.pathPoints.get(0).X > this.getPosX()) {
               this.setDirection(5);
               return;
            }

            if (this.movePath.pathPoints.get(0).X < this.getPosX()) {
               this.setDirection(3);
               return;
            }

            this.setDirection(4);
            return;
         }
      }

      this.movePath = null;
   }

   public final void drawUnit(SpriteBatch oSB) {
      this.drawHitBoxes(oSB);
      this.drawHP(oSB);
      this.currentAnimation().drawFrame(oSB, this.getPosX(), this.getPosY(), this.iDirection, this.currentFrameID);
   }

   public final void drawHitBoxes(SpriteBatch oSB) {
      this.currentAnimation().drawFrameHitboxes(oSB, this.getPosX(), this.getPosY());
   }

   public final void drawHP(SpriteBatch oSB) {
      oSB.setColor(Color.WHITE);
      Images.pix
         .draw(
            oSB,
            this.getPosX() + Game.mapCoords.getPosX() - this.getHP_Width() / 2,
            this.getPosY() + Game.mapCoords.getPosY() - this.getHP_Height() - CFG.PADDING * 2,
            this.getHP_Width(),
            CFG.PADDING
         );
      oSB.setColor(Color.GREEN);
      Images.pix
         .draw(
            oSB,
            this.getPosX() + Game.mapCoords.getPosX() - this.getHP_Width() / 2,
            this.getPosY() + Game.mapCoords.getPosY() - this.getHP_Height() - CFG.PADDING * 2,
            (int)(this.getHP_Width() * 0.68F),
            CFG.PADDING
         );
      oSB.setColor(Color.WHITE);
   }

   public final int getHP_Width() {
      return this.currentAnimation().hitbox.getWidth();
   }

   public final int getHP_Height() {
      return this.currentAnimation().hitbox.getHeight();
   }

   public Animation_Unit currentAnimation() {
      return this.movePath != null ? this.getUnitType().getAnimation_Walk() : this.getUnitType().getAnimation_Idle(0);
   }

   public final void resetAnimation() {
      this.currentFrameID = 0;
      this.ANIMATION_LAST_UPDATE_TIME = CFG.currentTimeMillis;
   }

   public final int getPosX() {
      return (int)this.fPosX;
   }

   public final int getPosY() {
      return (int)this.fPosY;
   }

   public final void setPosX(int nPosX) {
      this.fPosX = nPosX;
   }

   public final void setPosY(int nPosY) {
      this.fPosY = nPosY;
   }

   public final void movePosX(float nChange) {
      this.fPosX = this.fPosX + nChange * Game.deltaTime;
   }

   public final void movePosY(float nChange) {
      this.fPosY = this.fPosY + nChange * Game.deltaTime;
   }

   public final void setDirection(int iDirection) {
      if (this.iDirection != iDirection) {
         this.iDirection = iDirection;
         this.resetAnimation();
      }
   }

   public final int getDirection() {
      return this.iDirection;
   }

   public final float getSpeed() {
      return this.getUnitType().getUnitSpeed();
   }

   public final int getHP() {
      return this.iHP;
   }

   public final void setHP(int iHP) {
      this.iHP = iHP;
   }

   public int getMaxHP() {
      return this.getUnitType().getMaxHP();
   }
}
