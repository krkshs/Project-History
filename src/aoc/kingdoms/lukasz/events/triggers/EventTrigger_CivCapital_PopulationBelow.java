package aoc.kingdoms.lukasz.events.triggers;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.textures.Images;

public class EventTrigger_CivCapital_PopulationBelow extends EventTrigger_Value {
   public int value;

   public EventTrigger_CivCapital_PopulationBelow(int value) {
      this.value = value;
   }

   @Override
   public boolean outCondition(int iCivID, int iProvinceID) {
      try {
         if (Game.getCiv(iCivID).getCapitalProvinceID() >= 0 && Game.getProvince(Game.getCiv(iCivID).getCapitalProvinceID()).getCivID() == iCivID) {
            return Game.getProvince(Game.getCiv(iCivID).getCapitalProvinceID()).getPopulationTotal() < this.value;
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }

      return false;
   }

   @Override
   public String getText() {
      return Game.lang.get("Capital") + ", " + Game.lang.get("Population") + " < ";
   }

   @Override
   public String getText2() {
      return "" + CFG.getPrecision2((float)this.value, 1);
   }

   @Override
   public String getText3() {
      return " ["
         + Game.lang.get("Currently")
         + ": "
         + CFG.getPrecision2((float)Game.getProvince(Game.getCiv(Game.player.iCivID).getCapitalProvinceID()).getPopulationTotal(), 1)
         + "]";
   }

   @Override
   public int getImage() {
      return Images.population;
   }
}
