package aoc.kingdoms.lukasz.events.outcome.province;

import aoc.kingdoms.lukasz.events.outcome.EventOutcome;
import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.textures.Images;

public class EventOutcome_Province_Infrastructure_ID extends EventOutcome {
   public int provID;
   public int value;

   public EventOutcome_Province_Infrastructure_ID(int provID, int value) {
      this.provID = provID;
      this.value = value;
   }

   @Override
   public void updateProvince(int iProvinceID) {
      try {
         Game.getProvince(this.provID).setInfrastructure(Game.getProvince(this.provID).getInfrastructure() + this.value);
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   @Override
   public String getStringLeft() {
      return Game.lang.get("Infrastructure") + ": ";
   }

   @Override
   public String getStringRight() {
      return (this.value > 0 ? "+" : "") + CFG.getPrecision2((float)this.value, 100);
   }

   @Override
   public String getStringRight2(int bonus_duration) {
      try {
         return Game.getProvince(this.provID).getProvinceName();
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
         return null;
      }
   }

   @Override
   public int getImage() {
      return Images.infrastructure;
   }
}
