package aoc.kingdoms.lukasz.events.outcome.province;

import aoc.kingdoms.lukasz.events.outcome.EventOutcome;
import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;

public class EventOutcome_Province_Manpower extends EventOutcome {
   public float value;

   public EventOutcome_Province_Manpower(float value) {
      this.value = value;
   }

   @Override
   public void updateProvince(int iProvinceID) {
      try {
         Game.getProvince(iProvinceID).setManpower(Game.getProvince(iProvinceID).getManpower() + this.value);
         Game.gameThreadTurns.addCivUpdateMaxManpower(Game.getProvince(iProvinceID).getCivID());
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   @Override
   public String getStringLeft() {
      return Game.lang.get("LocalManpower") + ": ";
   }

   @Override
   public String getStringRight() {
      return (this.value > 0.0F ? "+" : "") + CFG.getPrecision2(this.value, 100);
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
      return Game_Calendar.IMG_MANPOWER;
   }
}
