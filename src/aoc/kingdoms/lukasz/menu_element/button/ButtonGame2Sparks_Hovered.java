package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.menu.MenuManager;
import aoc.kingdoms.lukasz.menus.MainMenu;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ButtonGame2Sparks_Hovered extends ButtonGame2 {
   public ButtonGame2Sparks_Hovered(String sText, int fontID, int iTextPositionX, int iPosX, int iPosY, int nWidth, boolean isClickable) {
      super(sText, fontID, iTextPositionX, iPosX, iPosY, nWidth, isClickable);
   }

   public ButtonGame2Sparks_Hovered(String sText, int fontID, int iTextPositionX, int iPosX, int iPosY, int nWidth, boolean isClickable, int nHeight) {
      super(sText, fontID, iTextPositionX, iPosX, iPosY, nWidth, isClickable, nHeight);
   }

   public ButtonGame2Sparks_Hovered(String sText, int fontID, int iTextPositionX, int iPosX, int iPosY, int nWidth, boolean isClickable, boolean checkBox) {
      super(sText, fontID, iTextPositionX, iPosX, iPosY, nWidth, isClickable, checkBox);
   }

   @Override
   public void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      super.drawButtonBG(oSB, iTranslateX, iTranslateY, isActive);
      if (this.getIsHovered() || isActive) {
         oSB.setColor(MainMenu.sparksColors);
         MenuManager.sparksAnimationHover.draw2(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
         oSB.setColor(Color.WHITE);
      }
   }
}
