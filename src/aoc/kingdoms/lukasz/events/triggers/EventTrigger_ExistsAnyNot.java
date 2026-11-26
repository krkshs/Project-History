package aoc.kingdoms.lukasz.events.triggers;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;

public class EventTrigger_ExistsAnyNot extends EventTrigger_Value {
   public String value;

   public EventTrigger_ExistsAnyNot(String value) {
      this.value = value;
   }

   @Override
   public boolean outCondition(int iCivID, int iProvinceID) {
      try {
         for (int i = 1; i < Game.getCivsSize(); i++) {
            if (Game.getCiv(i).getNumOfProvinces() > 0 && Game.getCiv(i).getCivTag().indexOf(this.value) >= 0 && Game.getCiv(i).realTag.equals(this.value)) {
               return false;
            }
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }

      return true;
   }

   @Override
   public String getText() {
      return Game.lang.get("CivilizationExist") + ", " + Game.lang.get("NOT") + ": ";
   }

   @Override
   public String getText2() {
      return "" + Game.lang.getCiv(Game.ideologiesManager.getRealTag(this.value));
   }

   @Override
   public String getText3() {
      return "";
   }
}
