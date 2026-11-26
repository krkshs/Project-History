package aoc.kingdoms.lukasz.menu_element.button;

public class ButtonGame_Style_NewArmy extends ButtonGame_Style {
   public int iArmyID;
   public int iUnitID;
   public boolean add;

   public ButtonGame_Style_NewArmy(
      int iUnitID, int iArmyID, boolean add, String sText, int fontID, int iTextPositionX, int iPosX, int iPosY, int nWidth, int nHeight, boolean isClickable
   ) {
      super(sText, fontID, iTextPositionX, iPosX, iPosY, nWidth, nHeight, isClickable);
      this.iUnitID = iUnitID;
      this.iArmyID = iArmyID;
      this.add = add;
   }

   @Override
   public int getValue1() {
      return this.iUnitID;
   }

   @Override
   public int getValue2() {
      return this.iArmyID;
   }

   @Override
   public int getCurrent() {
      return this.add ? 1 : 0;
   }
}
