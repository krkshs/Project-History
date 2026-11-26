package aoc.kingdoms.lukasz.events.outcome;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.textures.Images;

public class EventOutcome_AI_Aggression extends EventOutcome {
   public int value;

   public EventOutcome_AI_Aggression(int value) {
      this.value = value;
   }

   @Override
   public void updateCiv(int iCivID, int bonus_duration) {
      try {
         Game.getCiv(iCivID).setExtraAggressiveness(Game.getCiv(iCivID).getExtraAggressiveness() + this.value);
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   @Override
   public String getStringLeft() {
      return Game.lang.get("AI");
   }

   @Override
   public String getStringRight() {
      return "";
   }

   @Override
   public int getImage() {
      return Images.ai;
   }
}
