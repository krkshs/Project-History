package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ButtonGame extends Button {
   public static long lTimeAnimation = 0L;
   public static int animationState = 0;
   public static final int ANIMATION_T = 1000;
   public int iCurrent = 0;
   private static final Color colorLine = new Color(0.5176471F, 0.43529412F, 0.25882354F, 0.55F);

   public ButtonGame(String sText, int iPosX, int iPosY, int nWidth, int nHeight) {
      this.init(sText, CFG.FONT_REGULAR, -1, iPosX, iPosY, nWidth, CFG.BUTTON_HEIGHT2, true, true, false, false);
   }

   public ButtonGame(String sText, int fontID, int iTextPositionX, int iPosX, int iPosY, int nWidth, boolean isClickable) {
      this.init(sText, fontID, iTextPositionX, iPosX, iPosY, nWidth, CFG.BUTTON_HEIGHT2, isClickable, true, false, false);
   }

   public ButtonGame(String sText, int fontID, int iTextPositionX, int iPosX, int iPosY, int nWidth, int nHeight, boolean isClickable) {
      this.init(sText, fontID, iTextPositionX, iPosX, iPosY, nWidth, nHeight, isClickable, true, false, false);
   }

   public ButtonGame(String sText, int fontID, int iTextPositionX, int iPosX, int iPosY, int nWidth, boolean isClickable, boolean checkBox) {
      this.init(sText, fontID, iTextPositionX, iPosX, iPosY, nWidth, CFG.BUTTON_HEIGHT2, isClickable, true, true, checkBox);
   }

   @Override
   public void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      if ((isActive || this.getIsHovered()) && this.getClickable()) {
         Renderer.drawBox(oSB, this.getButtonBG_Active(), this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
         oSB.setColor(Colors.COLOR_GRADIENT_HOVER);
      } else {
         Renderer.drawBox(oSB, this.getButtonBG(), this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
         oSB.setColor(Colors.COLOR_GRADIENT);
      }

      Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + 2 + iTranslateY, this.getWidth(), this.getHeight() - 4);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.35F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 3 + iTranslateY, this.getWidth(), 1);
      oSB.setColor(Color.WHITE);
      if (this.getClickable() && this.getIsHovered() && animationState >= 0) {
         if (animationState == 0) {
            float drawPerc = Math.min(1.0F * (float)(CFG.currentTimeMillis - lTimeAnimation) / 1000.0F, 1.0F);
            oSB.setColor(getColorLine());
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
            oSB.setColor(getColorLine());
            Images.line_32_off1
               .draw(
                  oSB,
                  this.getPosX() + CFG.PADDING + (int)((this.getWidth() - CFG.PADDING * 2) * drawPerc) + iTranslateX,
                  this.getPosY() + 1 + iTranslateY,
                  this.getWidth() - CFG.PADDING * 2 - (int)((this.getWidth() - CFG.PADDING * 2) * drawPerc),
                  1
               );
            Images.line_32_off1
               .draw(
                  oSB,
                  this.getPosX() + CFG.PADDING + (int)((this.getWidth() - CFG.PADDING * 2) * drawPerc) + iTranslateX,
                  this.getPosY() + this.getHeight() - 2 + iTranslateY,
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

   public int getButtonBG() {
      return Images.buttonGame;
   }

   public int getButtonBG_Active() {
      return Images.buttonGameH;
   }

   public static final Color getColorLine() {
      return colorLine;
   }

   @Override
   public void setIsHovered(boolean isHovered) {
      super.setIsHovered(isHovered);
      lTimeAnimation = CFG.currentTimeMillis;
      animationState = 0;
   }

   @Override
   public void setCurrent(int nCurrent) {
      this.iCurrent = nCurrent;
   }

   @Override
   public int getCurrent() {
      return this.iCurrent;
   }
}
