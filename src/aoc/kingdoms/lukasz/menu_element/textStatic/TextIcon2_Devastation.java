package aoc.kingdoms.lukasz.menu_element.textStatic;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;

public class TextIcon2_Devastation extends TextIcon2 {
   public float lastValue = -997654.3F;
   public int iProvinceID;

   public TextIcon2_Devastation(String sText, int imageID, int nPosX, int nPosY, int nWidth, int nHeight, int iProvinceID) {
      super(sText, imageID, nPosX, nPosY, nWidth, nHeight);
      this.iProvinceID = iProvinceID;
   }

   @Override
   public String getTextToDraw() {
      if (this.lastValue != Game.getProvince(this.iProvinceID).getDevastation()) {
         this.setText("" + CFG.getPrecision2(Game.getProvince(this.iProvinceID).getDevastation() * 100.0F, 10) + "%");
         this.lastValue = Game.getProvince(this.iProvinceID).getDevastation();
      }

      return super.getTextToDraw();
   }

   @Override
   public int getCurrent() {
      return this.iProvinceID;
   }
}
