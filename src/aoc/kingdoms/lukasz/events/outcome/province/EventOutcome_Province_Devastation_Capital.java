package aoc.kingdoms.lukasz.events.outcome.province;

import aoc.kingdoms.lukasz.events.outcome.EventOutcome;
import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.textures.Images;

public class EventOutcome_Province_Devastation_Capital extends EventOutcome {
   public float value;

   public EventOutcome_Province_Devastation_Capital(float value) {
      this.value = value;
   }

   @Override
   public void updateCiv(int iCivID, int bonus_duration) {
      try {
         Game.getProvince(Game.getCiv(iCivID).getCapitalProvinceID())
            .setDevastation(Game.getProvince(Game.getCiv(iCivID).getCapitalProvinceID()).getDevastation() + this.value / 100.0F);
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
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
         return Game.getProvince(Game.getCiv(Game.player.iCivID).getCapitalProvinceID()).getProvinceName();
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
