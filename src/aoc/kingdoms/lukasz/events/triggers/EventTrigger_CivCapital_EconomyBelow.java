package aoc.kingdoms.lukasz.events.triggers;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;

public class EventTrigger_CivCapital_EconomyBelow extends EventTrigger_Value {
   public float value;

   public EventTrigger_CivCapital_EconomyBelow(float value) {
      this.value = value;
   }

   @Override
   public boolean outCondition(int iCivID, int iProvinceID) {
      try {
         if (Game.getCiv(iCivID).getCapitalProvinceID() >= 0 && Game.getProvince(Game.getCiv(iCivID).getCapitalProvinceID()).getCivID() == iCivID) {
            return Game.getProvince(Game.getCiv(iCivID).getCapitalProvinceID()).getEconomyWithBonuses() < this.value;
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }

      return false;
   }

   @Override
   public String getText() {
      return Game.lang.get("Capital") + ", " + Game.lang.get("Economy") + " < ";
   }

   @Override
   public String getText2() {
      return "" + CFG.getPrecision2(this.value, 1);
   }

   @Override
   public String getText3() {
      return " ["
         + Game.lang.get("Currently")
         + ": "
         + CFG.getPrecision2(Game.getProvince(Game.getCiv(Game.player.iCivID).getCapitalProvinceID()).getEconomyWithBonuses(), 1)
         + "]";
   }

   @Override
   public int getImage() {
      return Game_Calendar.IMG_ECONOMY;
   }
}
