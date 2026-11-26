package aoc.kingdoms.lukasz.events.triggers;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;

public class EventTrigger_ProvinceEconomyOver extends EventTrigger_Value {
   public int provID;
   public float value;

   public EventTrigger_ProvinceEconomyOver(int provID, float value) {
      this.provID = provID;
      this.value = value;
   }

   @Override
   public boolean outCondition(int iCivID, int iProvinceID) {
      return this.provID >= 0 ? Game.getProvince(this.provID).getEconomyWithBonuses() > this.value : false;
   }

   @Override
   public String getText() {
      return Game.getProvince(this.provID).getProvinceName() + ", " + Game.lang.get("Economy") + " > ";
   }

   @Override
   public String getText2() {
      return "" + CFG.getPrecision2(this.value, 100);
   }

   @Override
   public String getText3() {
      return " [" + Game.lang.get("Currently") + ": " + CFG.getPrecision2(Game.getProvince(this.provID).getEconomyWithBonuses(), 100) + "]";
   }

   @Override
   public int getImage() {
      return Game_Calendar.IMG_ECONOMY;
   }
}
