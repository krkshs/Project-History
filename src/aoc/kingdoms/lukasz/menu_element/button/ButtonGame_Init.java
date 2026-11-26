package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ButtonGame_Init extends ButtonGame {
   public static long INIT_TIME = 0L;
   public static final float INIT_ANIMATION_TIME = 750.0F;

   public ButtonGame_Init(String sText, int iPosX, int iPosY, int nWidth, int nHeight) {
      super(sText, iPosX, iPosY, nWidth, nHeight);
      INIT_TIME = CFG.currentTimeMillis;
   }

   public ButtonGame_Init(String sText, int fontID, int iTextPositionX, int iPosX, int iPosY, int nWidth, boolean isClickable) {
      super(sText, fontID, iTextPositionX, iPosX, iPosY, nWidth, isClickable);
      INIT_TIME = CFG.currentTimeMillis;
   }

   public ButtonGame_Init(String sText, int fontID, int iTextPositionX, int iPosX, int iPosY, int nWidth, int nHeight, boolean isClickable) {
      super(sText, fontID, iTextPositionX, iPosX, iPosY, nWidth, nHeight, isClickable);
      INIT_TIME = CFG.currentTimeMillis;
   }

   public ButtonGame_Init(String sText, int fontID, int iTextPositionX, int iPosX, int iPosY, int nWidth, boolean isClickable, boolean checkBox) {
      super(sText, fontID, iTextPositionX, iPosX, iPosY, nWidth, isClickable, checkBox);
      INIT_TIME = CFG.currentTimeMillis;
   }

   @Override
   public void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      super.drawButtonBG(oSB, iTranslateX, iTranslateY, isActive);
      float fAlpha = 1.0F - (float)(CFG.currentTimeMillis - INIT_TIME) / 750.0F;
      if (fAlpha > 0.0F) {
         oSB.setColor(new Color(Colors.COLOR_GRADIENT.r, Colors.COLOR_GRADIENT.g, Colors.COLOR_GRADIENT.b, fAlpha));
         Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + 2 + iTranslateY, this.getWidth(), this.getHeight() - 4, false, true);
         Images.gradientFull
            .draw(
               oSB,
               this.getPosX() + (int)(this.getWidth() * (1.0F - fAlpha)) / 2 + iTranslateX,
               this.getPosY() + 2 + iTranslateY,
               (int)(this.getWidth() * fAlpha),
               this.getHeight() - 4
            );
         oSB.setColor(Color.WHITE);
      }
   }
}
