package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.menusInGame.InGame_Wonder;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ButtonWonder extends Button {
   public ButtonWonder(int iPosX, int iPosY) {
      this.init("", CFG.FONT_REGULAR, 0, iPosX, iPosY, InGame_Wonder.wonderIMG.getWidth(), InGame_Wonder.wonderIMG.getHeight(), true, true, false, false);
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      try {
         InGame_Wonder.wonderIMG.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY);
      } catch (Exception var6) {
      }
   }
}
