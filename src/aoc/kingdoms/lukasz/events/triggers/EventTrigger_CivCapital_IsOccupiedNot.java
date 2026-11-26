package aoc.kingdoms.lukasz.events.triggers;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.textures.Images;

public class EventTrigger_CivCapital_IsOccupiedNot extends EventTrigger_Value {
   @Override
   public boolean outCondition(int iCivID, int iProvinceID) {
      try {
         if (Game.getCiv(iCivID).getCapitalProvinceID() >= 0 && Game.getProvince(Game.getCiv(iCivID).getCapitalProvinceID()).getCivID() == iCivID) {
            return !Game.getProvince(Game.getCiv(iCivID).getCapitalProvinceID()).isOccupied();
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }

      return false;
   }

   @Override
   public String getText() {
      return Game.lang.get("Capital") + ", " + Game.lang.get("Occupied") + " " + Game.lang.get("NOT");
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
      return Images.provinces;
   }
}
