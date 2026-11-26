package aoc.kingdoms.lukasz.events.triggers;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;

public class EventTrigger_CivMaxManpowerBelow extends EventTrigger_Value {
   public int value;

   public EventTrigger_CivMaxManpowerBelow(int value) {
      this.value = value;
   }

   @Override
   public boolean outCondition(int iCivID, int iProvinceID) {
      return Game.getCiv(iCivID).fManpowerMax > this.value;
   }

   @Override
   public String getText() {
      return Game.lang.get("MaximumManpower") + " < ";
   }

   @Override
   public String getText2() {
      return "" + CFG.getPrecision2((float)this.value, 100);
   }

   @Override
   public String getText3() {
      return " [" + Game.lang.get("Currently") + ": " + CFG.getPrecision2(Game.getCiv(Game.player.iCivID).fManpowerMax, 1) + "]";
   }

   @Override
   public int getImage() {
      return Game_Calendar.IMG_MANPOWER;
   }
}
