package aoc.kingdoms.lukasz.events.triggers;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;

public class EventTrigger_CivManpower_PercOfMax_Below extends EventTrigger_Value {
   public float value;

   public EventTrigger_CivManpower_PercOfMax_Below(float value) {
      this.value = value;
   }

   @Override
   public boolean outCondition(int iCivID, int iProvinceID) {
      return Game.getCiv(iCivID).fManpower / Game.getCiv(iCivID).fManpowerMax * 100.0 < this.value;
   }

   @Override
   public String getText() {
      return Game.lang.get("Manpower") + " < ";
   }

   @Override
   public String getText2() {
      return "" + CFG.getPrecision2(this.value, 100) + "%";
   }

   @Override
   public String getText3() {
      return " ["
         + Game.lang.get("Currently")
         + ": "
         + CFG.getPrecision2(Game.getCiv(Game.player.iCivID).fManpower / Game.getCiv(Game.player.iCivID).fManpowerMax * 100.0, 10)
         + "]";
   }

   @Override
   public int getImage() {
      return Game_Calendar.IMG_MANPOWER;
   }
}
