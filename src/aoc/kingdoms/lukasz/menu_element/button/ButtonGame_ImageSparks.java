package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.menu.MenuManager;
import aoc.kingdoms.lukasz.menus.MainMenu;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ButtonGame_ImageSparks extends ButtonGame_Image {
   public ButtonGame_ImageSparks(String sText, int fontID, int iTextPositionX, int iPosX, int iPosY, int nWidth, boolean isClickable, int imageID) {
      super(sText, fontID, iTextPositionX, iPosX, iPosY, nWidth, isClickable, imageID);
   }

   public ButtonGame_ImageSparks(String sText, int fontID, int iTextPositionX, int iPosX, int iPosY, int nWidth, int nHeight, boolean isClickable, int imageID) {
      super(sText, fontID, iTextPositionX, iPosX, iPosY, nWidth, nHeight, isClickable, imageID);
   }

   @Override
   public void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      super.drawButtonBG(oSB, iTranslateX, iTranslateY, isActive);
      oSB.setColor(MainMenu.sparksColors);
      MenuManager.sparksAnimation.draw2(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      oSB.setColor(Color.WHITE);
   }
}
