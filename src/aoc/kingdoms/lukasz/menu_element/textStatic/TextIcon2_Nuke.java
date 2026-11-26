package aoc.kingdoms.lukasz.menu_element.textStatic;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.menu_element.button.ButtonWonderProvince;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TextIcon2_Nuke extends TextIcon2 {
   protected static long lTimeAnimation = 0L;
   protected static int animationState = 0;
   protected static final int ANIMATION_T = 1000;

   public TextIcon2_Nuke(String sText, int imageID, int nPosX, int nPosY, int nWidth, int nHeight) {
      super(sText, imageID, nPosX, nPosY, nWidth, nHeight);
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      super.drawText(oSB, iTranslateX, iTranslateY, isActive);
      if (animationState >= 0) {
         if (animationState == 0) {
            float drawPerc = Math.min(1.0F * (float)(CFG.currentTimeMillis - lTimeAnimation) / 1000.0F, 1.0F);
            oSB.setColor(ButtonWonderProvince.getColorLine());
            Images.line_32_off1
               .draw(
                  oSB, this.getPosX() + CFG.PADDING + iTranslateX, this.getPosY() + 1 + iTranslateY, (int)((this.getWidth() - CFG.PADDING * 2) * drawPerc), 1
               );
            Images.line_32_off1
               .draw(
                  oSB,
                  this.getPosX() + CFG.PADDING + iTranslateX,
                  this.getPosY() + this.getHeight() - 2 + iTranslateY,
                  (int)((this.getWidth() - CFG.PADDING * 2) * drawPerc),
                  1
               );
            if (lTimeAnimation < CFG.currentTimeMillis - 1000L) {
               animationState++;
               lTimeAnimation = CFG.currentTimeMillis;
            }
         } else {
            float drawPerc = Math.min(1.0F * (float)(CFG.currentTimeMillis - lTimeAnimation) / 1000.0F, 1.0F);
            oSB.setColor(ButtonWonderProvince.getColorLine());
            Images.line_32_off1
               .draw(
                  oSB,
                  this.getPosX() + CFG.PADDING + (int)((this.getWidth() - CFG.PADDING * 2) * drawPerc) + iTranslateX,
                  this.getPosY() + iTranslateY,
                  this.getWidth() - CFG.PADDING * 2 - (int)((this.getWidth() - CFG.PADDING * 2) * drawPerc),
                  1
               );
            Images.line_32_off1
               .draw(
                  oSB,
                  this.getPosX() + CFG.PADDING + (int)((this.getWidth() - CFG.PADDING * 2) * drawPerc) + iTranslateX,
                  this.getPosY() + this.getHeight() - 1 + iTranslateY,
                  this.getWidth() - CFG.PADDING * 2 - (int)((this.getWidth() - CFG.PADDING * 2) * drawPerc),
                  1
               );
            if (lTimeAnimation < CFG.currentTimeMillis - 1000L) {
               animationState = 0;
               lTimeAnimation = CFG.currentTimeMillis;
            }
         }

         oSB.setColor(Color.WHITE);
      }
   }
}
