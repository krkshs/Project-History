package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ButtonGame2 extends Button {
   protected static long lTimeAnimation = 0L;
   protected static int animationState = 0;
   protected static final int ANIMATION_T = 1000;
   public int iCurrent = 0;
   public static Color colorGradient = new Color(0.09803922F, 0.15686275F, 0.23529412F, 0.4F);
   public static Color colorGradientHover = new Color(0.19607843F, 0.13725491F, 0.11764706F, 0.75F);
   private static final Color colorLine = new Color(0.5176471F, 0.43529412F, 0.25882354F, 0.55F);

   public ButtonGame2(String sText, int fontID, int iTextPositionX, int iPosX, int iPosY, int nWidth, boolean isClickable) {
      this.init(sText, fontID, iTextPositionX, iPosX, iPosY, nWidth, CFG.BUTTON_HEIGHT, isClickable, true, false, false);
   }

   public ButtonGame2(String sText, int fontID, int iTextPositionX, int iPosX, int iPosY, int nWidth, boolean isClickable, int nHeight) {
      this.init(sText, fontID, iTextPositionX, iPosX, iPosY, nWidth, nHeight, isClickable, true, false, false);
   }

   public ButtonGame2(String sText, int fontID, int iTextPositionX, int iPosX, int iPosY, int nWidth, boolean isClickable, int nHeight, boolean checkBox) {
      this.init(sText, fontID, iTextPositionX, iPosX, iPosY, nWidth, nHeight, isClickable, true, true, checkBox);
   }

   public ButtonGame2(String sText, int fontID, int iTextPositionX, int iPosX, int iPosY, int nWidth, boolean isClickable, boolean checkBox) {
      this.init(sText, fontID, iTextPositionX, iPosX, iPosY, nWidth, CFG.BUTTON_HEIGHT, isClickable, true, true, checkBox);
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      if ((isActive || this.getIsHovered()) && this.getClickable()) {
         Renderer.drawBox(oSB, this.getButtonBG_Active(), this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
         oSB.setColor(colorGradientHover);
      } else {
         Renderer.drawBox(oSB, this.getButtonBG(), this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
         oSB.setColor(colorGradient);
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

   protected static final Color getColorLine() {
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
