package aoc.kingdoms.lukasz.events.outcome.province;

import aoc.kingdoms.lukasz.events.outcome.EventOutcome;
import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.textures.Images;

public class EventOutcome_Province_Devastation_ID extends EventOutcome {
   public int provID;
   public float value;

   public EventOutcome_Province_Devastation_ID(int provID, float value) {
      this.provID = provID;
      this.value = value;
   }

   @Override
   public void updateProvince(int iProvinceID) {
      try {
         Game.getProvince(this.provID).setDevastation(Game.getProvince(this.provID).getDevastation() + this.value / 100.0F);
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   @Override
   public String getStringLeft() {
      return Game.lang.get("Devastation") + ": ";
   }

   @Override
   public String getStringRight() {
      return (this.value > 0.0F ? "+" : "") + CFG.getPrecision2(this.value, 100) + "%";
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
      return Images.devastation;
   }
}
