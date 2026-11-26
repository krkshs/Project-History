package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;

public class ButtonStatsRect_Active_Value extends ButtonStatsRect_Active {
   public int id;

   public ButtonStatsRect_Active_Value(String sText, int iPosX, int iPosY, int nWidth, int nHeight, int id) {
      super(sText, CFG.PADDING * 2, iPosX, iPosY, nWidth, nHeight, CFG.FONT_REGULAR_SMALL);
      this.id = id;
   }

   public ButtonStatsRect_Active_Value(String sText, int iPosX, int iPosY, int nWidth, int nHeight, int id, int iTextPos) {
      super(sText, iTextPos, iPosX, iPosY, nWidth, nHeight, CFG.FONT_REGULAR_SMALL);
      this.id = id;
   }

   @Override
   public int getCurrent() {
      return this.id;
   }
}
