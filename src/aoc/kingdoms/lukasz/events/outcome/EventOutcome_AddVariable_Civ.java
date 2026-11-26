package aoc.kingdoms.lukasz.events.outcome;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;

public class EventOutcome_AddVariable_Civ extends EventOutcome {
   public String addToCiv;
   public String value;

   public EventOutcome_AddVariable_Civ(String addToCiv, String value) {
      this.addToCiv = addToCiv;
      this.value = value;
   }

   @Override
   public void updateCiv(int iCivID, int bonus_duration) {
      try {
         int toCivID = Game.getCivID(this.addToCiv);
         if (toCivID > 0) {
            Game.getCiv(toCivID).eventsDataVariables.addVariable(this.value);
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }
}
