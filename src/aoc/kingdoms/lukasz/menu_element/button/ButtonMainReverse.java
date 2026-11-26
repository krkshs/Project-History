package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;

public class ButtonMainReverse extends ButtonMain {
   public ButtonMainReverse(String sText, int fontID, int iTextPositionX, int iPosX, int iPosY, int nWidth, boolean isClickable) {
      super(sText, fontID, iTextPositionX, iPosX, iPosY, nWidth, isClickable);
   }

   @Override
   public int getButtonBG() {
      return Images.buttonMenuH;
   }

   @Override
   public int getButtonBG_Active() {
      return Images.buttonMenu;
   }

   @Override
   protected Color getColor(boolean isActive) {
      if (isActive) {
         return Colors.BUTTON_TEXT;
      } else if (this.getIsHovered()) {
         return Colors.BUTTON_TEXT_HOVERED;
      } else {
         return this.getClickable() ? Colors.BUTTON_TEXT_ACTIVE : Colors.BUTTON_TEXT_DISABLED;
      }
   }
}
