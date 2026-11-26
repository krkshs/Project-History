package aoc.kingdoms.lukasz.events.outcome;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.map.civilization.Civilization;
import aoc.kingdoms.lukasz.textures.Images;

public class EventOutcome_Gold_MonthlyIncome extends EventOutcome {
   public float value;

   public EventOutcome_Gold_MonthlyIncome(float value) {
      this.value = value;
   }

   @Override
   public void updateCiv(int iCivID, int bonus_duration) {
      try {
         Civilization var10000 = Game.getCiv(iCivID);
         var10000.fGold = var10000.fGold + Game.getCiv(iCivID).fTotalIncomePerMonth * this.value;
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   @Override
   public String getStringLeft() {
      return Game.lang.get("Gold") + ": ";
   }

   @Override
   public String getStringRight() {
      return (this.value > 0.0F ? "+" : "") + CFG.getPrecision2(Game.getCiv(Game.player.iCivID).fTotalIncomePerMonth * this.value, 100);
   }

   @Override
   public int getImage() {
      return Images.gold;
   }
}
