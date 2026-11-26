package aoc.kingdoms.lukasz.menu_element.button;

public class ButtonGame_Value extends ButtonGame {
   public int id;

   public ButtonGame_Value(String sText, int fontID, int iTextPositionX, int iPosX, int iPosY, int nWidth, boolean isClickable, int id) {
      super(sText, fontID, iTextPositionX, iPosX, iPosY, nWidth, isClickable);
      this.id = id;
   }

   @Override
   public int getCurrent() {
      return this.id;
   }
}
