package aoc.kingdoms.lukasz.events.triggers;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;

public class EventTrigger_CivCapital_IsUnderSiege extends EventTrigger_Value {
   @Override
   public boolean outCondition(int iCivID, int iProvinceID) {
      try {
         if (Game.getCiv(iCivID).getCapitalProvinceID() >= 0 && Game.getProvince(Game.getCiv(iCivID).getCapitalProvinceID()).getCivID() == iCivID) {
            return Game.getProvinceData4(Game.getCiv(iCivID).getCapitalProvinceID()).isUnderSiege();
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }

      return false;
   }

   @Override
   public String getText() {
      return Game.lang.get("Capital") + ", " + Game.lang.get("UnderSiege");
   }

   @Override
   public String getText2() {
      return "";
   }

   @Override
   public String getText3() {
      return "";
   }

   @Override
   public int getImage() {
      return Game_Calendar.IMG_FORT_DEFENSE;
   }
}
