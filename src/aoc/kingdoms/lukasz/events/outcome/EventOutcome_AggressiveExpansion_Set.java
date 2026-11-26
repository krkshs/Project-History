package aoc.kingdoms.lukasz.events.outcome;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.textures.Images;

public class EventOutcome_AggressiveExpansion_Set extends EventOutcome {
   public float value;

   public EventOutcome_AggressiveExpansion_Set(float value) {
      this.value = value;
   }

   @Override
   public void updateCiv(int iCivID, int bonus_duration) {
      try {
         Game.getCiv(iCivID).setAggressiveExpansion(this.value);
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   @Override
   public String getStringLeft() {
      return Game.lang.get("AggressiveExpansion") + ": ";
   }

   @Override
   public String getStringRight() {
      return "" + CFG.getPrecision2(this.value, 1);
   }

   @Override
   public int getImage() {
      return Images.aggressiveExpansion;
   }
}
