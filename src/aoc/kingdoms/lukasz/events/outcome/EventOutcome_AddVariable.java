package aoc.kingdoms.lukasz.events.outcome;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;

public class EventOutcome_AddVariable extends EventOutcome {
   public String value;

   public EventOutcome_AddVariable(String value) {
      this.value = value;
   }

   @Override
   public void updateCiv(int iCivID, int bonus_duration) {
      try {
         Game.getCiv(iCivID).eventsDataVariables.addVariable(this.value);
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }
}
