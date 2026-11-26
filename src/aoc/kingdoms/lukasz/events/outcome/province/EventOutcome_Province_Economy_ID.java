package aoc.kingdoms.lukasz.events.outcome.province;

import aoc.kingdoms.lukasz.events.outcome.EventOutcome;
import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;

public class EventOutcome_Province_Economy_ID extends EventOutcome {
   public int provID;
   public float value;

   public EventOutcome_Province_Economy_ID(int provID, float value) {
      this.provID = provID;
      this.value = value;
   }

   @Override
   public void updateProvince(int iProvinceID) {
      try {
         Game.getProvince(this.provID).setEconomy(Game.getProvince(this.provID).getEconomy() + this.value);
         Game.getProvince(this.provID).updateAfterEconomyChange();
         Game.getCiv(Game.getProvince(this.provID).getCivID()).updateTotalIncomePerMonth();
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   @Override
   public String getStringLeft() {
      return Game.lang.get("Economy") + ": ";
   }

   @Override
   public String getStringRight() {
      return (this.value > 0.0F ? "+" : "") + CFG.getPrecision2(this.value, 100);
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
      return Game_Calendar.IMG_ECONOMY;
   }
}
