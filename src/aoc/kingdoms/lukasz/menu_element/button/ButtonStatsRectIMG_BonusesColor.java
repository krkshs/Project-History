package aoc.kingdoms.lukasz.menu_element.button;

import com.badlogic.gdx.graphics.Color;

public class ButtonStatsRectIMG_BonusesColor extends ButtonStatsRectIMG_Bonuses {
   public Color bonusColors;

   public ButtonStatsRectIMG_BonusesColor(
      String sText, String sText2, int imageID, int iPosX, int iPosY, int nWidth, int nHeight, int maxIconWidth, Color nColor
   ) {
      super(sText, sText2, imageID, iPosX, iPosY, nWidth, nHeight, maxIconWidth);
      this.bonusColors = nColor;
   }

   @Override
   public Color getColorBonus() {
      return this.bonusColors;
   }
}
