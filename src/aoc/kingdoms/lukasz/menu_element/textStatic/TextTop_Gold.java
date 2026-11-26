package aoc.kingdoms.lukasz.menu_element.textStatic;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;

public class TextTop_Gold extends TextTop {
   public float lastValuePerMonth = -997654.3F;

   public TextTop_Gold(int imageID, String sText, String sText2, int iPosX, int iPosY) {
      super(imageID, sText, sText2, iPosX, iPosY);
   }

   @Override
   public String getTextToDraw() {
      if (this.lastValue != Game.getCiv(Game.player.iCivID).fGold) {
         if (Game.getCiv(Game.player.iCivID).fGold >= 100000.0F) {
            this.setText("" + CFG.getShortNumber((int)Math.floor(Game.getCiv(Game.player.iCivID).fGold)));
         } else {
            this.setText("" + (int)Math.floor(Game.getCiv(Game.player.iCivID).fGold));
         }

         this.lastValue = Game.getCiv(Game.player.iCivID).fGold;
      }

      if (this.lastValuePerMonth
         != Game.getCiv(Game.player.iCivID).fTotalIncomePerMonth
            + Game.getCiv(Game.player.iCivID).civBonuses.MonthlyIncome
            - Game.getCiv(Game.player.iCivID).fTotalExpensesPerMonth) {
         float fGold = Game.getCiv(Game.player.iCivID).fTotalIncomePerMonth
            + Game.getCiv(Game.player.iCivID).civBonuses.MonthlyIncome
            - Game.getCiv(Game.player.iCivID).fTotalExpensesPerMonth;
         if (Game.getCiv(Game.player.iCivID).fGold >= Game.getMaxAmountOfGold(Game.player.iCivID)) {
            this.setText2("---");
         } else {
            this.setText2((fGold > 0.0F ? "+" : "") + CFG.getPrecision2(fGold, Math.abs(fGold) < 10.0F ? 100 : (Math.abs(fGold) < 100.0F ? 10 : 1)));
         }

         this.lastValuePerMonth = fGold;
      }

      return super.getTextToDraw();
   }

   @Override
   public boolean getIsActiveButton() {
      return Game.menuManager.getVisibleInGame_Budget();
   }
}
