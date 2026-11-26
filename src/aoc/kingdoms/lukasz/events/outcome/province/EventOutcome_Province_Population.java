package aoc.kingdoms.lukasz.events.outcome.province;

import aoc.kingdoms.lukasz.events.outcome.EventOutcome;
import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.textures.Images;

public class EventOutcome_Province_Population extends EventOutcome {
   public int value;

   public EventOutcome_Province_Population(int value) {
      this.value = value;
   }

   @Override
   public void updateProvince(int iProvinceID) {
      try {
         Game.getProvince(iProvinceID)
            .setPopulationOfCivID(
               Game.getProvince(iProvinceID).getCivID(),
               Game.getProvince(iProvinceID).getPopulationOfCivID(Game.getProvince(iProvinceID).getCivID()) + this.value
            );
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   @Override
   public String getStringLeft() {
      return Game.lang.get("Population") + ": ";
   }

   @Override
   public String getStringRight() {
      return (this.value > 0 ? "+" : "") + CFG.getPrecision2((float)this.value, 100);
   }

   @Override
   public String getStringRight2(int bonus_duration) {
      try {
         return Game.getProvince(Game.getCiv(Game.player.iCivID).eventProvinceID).getProvinceName();
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
         return null;
      }
   }

   @Override
   public int getImage() {
      return Images.population;
   }
}
