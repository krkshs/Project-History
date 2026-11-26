package aoc.kingdoms.lukasz.events.outcome;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;

public class EventOutcome_Manpower extends EventOutcome {
   public int value;

   public EventOutcome_Manpower(int value) {
      this.value = value;
   }

   @Override
   public void updateCiv(int iCivID, int bonus_duration) {
      try {
         Game.getCiv(iCivID).setManpower(Game.getCiv(iCivID).fManpower + this.value);
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   @Override
   public String getStringLeft() {
      return Game.lang.get("Manpower") + ": ";
   }

   @Override
   public String getStringRight() {
      return (this.value > 0 ? "+" : "") + CFG.getPrecision2((float)this.value, 1);
   }

   @Override
   public int getImage() {
      return Game_Calendar.IMG_MANPOWER;
   }
}
