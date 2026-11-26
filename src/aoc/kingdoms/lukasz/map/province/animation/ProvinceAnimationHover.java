package aoc.kingdoms.lukasz.map.province.animation;

import aoc.kingdoms.lukasz.jakowski.CFG;

public class ProvinceAnimationHover {
   public final int START_ALPHA = 255;
   public final int TIME_UPDATE = 40;
   public long lTime = 0L;
   public float fAlpha = 255.0F;
   public int iColorStepID;
   public int iStepID = 0;
   public boolean backAnimation = false;

   public final void update() {
      if (this.lTime < CFG.currentTimeMillis - 40L) {
         this.iStepID++;
         if (this.backAnimation) {
            this.fAlpha += 6.5F;
            this.iColorStepID--;
         } else {
            this.fAlpha -= 6.5F;
            this.iColorStepID++;
         }

         this.lTime = CFG.currentTimeMillis;
         if (this.iStepID == 12) {
            if (this.backAnimation) {
               this.fAlpha = 255.0F;
            }

            this.iStepID = 0;
            this.backAnimation = !this.backAnimation;
         }
      }
   }

   public final void resetAnimationData() {
      this.lTime = 0L;
      this.fAlpha = 255.0F;
      this.iStepID = 0;
      this.backAnimation = false;
      this.iColorStepID = 0;
   }

   public final float getAlpha() {
      return this.fAlpha;
   }

   public final boolean getBackAnimation() {
      return this.backAnimation;
   }

   public final int getStepID() {
      return this.iStepID;
   }

   public final int getColorStepID() {
      return this.iColorStepID;
   }
}
