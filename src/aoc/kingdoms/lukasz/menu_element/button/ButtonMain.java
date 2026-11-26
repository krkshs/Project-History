package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ButtonMain extends Button {
   protected static long lTimeAnimation = 0L;
   protected static int animationState = 0;
   protected static final int ANIMATION_T = 750;
   private static final Color colorLine = new Color(0.5176471F, 0.43529412F, 0.25882354F, 0.55F);

   public ButtonMain(String sText, int fontID, int iTextPositionX, int iPosX, int iPosY, int nWidth, boolean isClickable) {
      this.init(sText, fontID, iTextPositionX, iPosX, iPosY, nWidth, CFG.BUTTON_HEIGHT, isClickable, true, false, false);
   }

   public ButtonMain(String sText, int fontID, int iTextPositionX, int iPosX, int iPosY, int nWidth, boolean isClickable, boolean checkBox) {
      this.init(sText, fontID, iTextPositionX, iPosX, iPosY, nWidth, CFG.BUTTON_HEIGHT, isClickable, true, true, checkBox);
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      if ((isActive || this.getIsHovered()) && this.getClickable()) {
         oSB.setColor(COLOR_BUTTON_MENU_HOVER_BG);
         Renderer.drawBox(oSB, this.getButtonBG_Active(), this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.15F));
         oSB.setColor(Color.WHITE);
         if (animationState >= 0) {
            if (animationState == 0) {
               float drawPerc = Math.min(1.0F * (float)(CFG.currentTimeMillis - lTimeAnimation) / 750.0F, 1.0F);
               oSB.setColor(getColorLine());
               Images.line_32_off1
                  .draw(
                     oSB,
                     this.getPosX() + CFG.PADDING + iTranslateX,
                     this.getPosY() + 1 + iTranslateY,
                     (int)((this.getWidth() - CFG.PADDING * 2) * drawPerc),
                     1
                  );
               Images.line_32_off1
                  .draw(
                     oSB,
                     this.getPosX() + CFG.PADDING + iTranslateX,
                     this.getPosY() + this.getHeight() - 2 + iTranslateY,
                     (int)((this.getWidth() - CFG.PADDING * 2) * drawPerc),
                     1
                  );
               if (lTimeAnimation < CFG.currentTimeMillis - 750L) {
                  animationState++;
                  lTimeAnimation = CFG.currentTimeMillis;
               }
            } else {
               float drawPerc = Math.min(1.0F * (float)(CFG.currentTimeMillis - lTimeAnimation) / 750.0F, 1.0F);
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
               if (lTimeAnimation < CFG.currentTimeMillis - 750L) {
                  animationState = 0;
                  lTimeAnimation = CFG.currentTimeMillis;
               }
            }

            oSB.setColor(Color.WHITE);
         }
      } else {
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.9F));
         Renderer.drawBox(oSB, this.getButtonBG(), this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
         oSB.setColor(Color.WHITE);
      }

      oSB.setColor(Color.WHITE);
   }

   public int getButtonBG() {
      return Images.buttonMenu;
   }

   public int getButtonBG_Active() {
      return Images.buttonMenuH;
   }

   @Override
   public void setIsHovered(boolean isHovered) {
      super.setIsHovered(isHovered);
      lTimeAnimation = CFG.currentTimeMillis;
      animationState = 0;
   }

   protected static final Color getColorLine() {
      return colorLine;
   }
}
