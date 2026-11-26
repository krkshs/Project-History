package aoc.kingdoms.lukasz.menu_element.button;

public class ButtonCurrentSituation_Value extends ButtonCurrentSituation {
   public int id;

   public ButtonCurrentSituation_Value(String sText, int imageID, int iPosX, int iPosY, int nWidth, int nHeight, int maxIconWidth, boolean row, int id) {
      super(sText, imageID, iPosX, iPosY, nWidth, nHeight, maxIconWidth, row);
      this.id = id;
   }

   @Override
   public int getCurrent() {
      return this.id;
   }
}
