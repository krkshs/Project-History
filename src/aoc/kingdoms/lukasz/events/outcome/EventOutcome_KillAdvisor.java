package aoc.kingdoms.lukasz.events.outcome;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.map.advisors.AdvisorManager;
import aoc.kingdoms.lukasz.textures.Images;

public class EventOutcome_KillAdvisor extends EventOutcome {
   public int value;

   public EventOutcome_KillAdvisor(int value) {
      this.value = value;
   }

   @Override
   public void updateCiv(int iCivID, int bonus_duration) {
      try {
         Game.advisorManager.advisorDied(iCivID, this.value);
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   @Override
   public String getStringLeft() {
      return Game.lang.get("AdvisorWillDie") + ": ";
   }

   @Override
   public String getStringRight() {
      return AdvisorManager.getAdvisorGroupName(this.value);
   }

   @Override
   public int getImage() {
      return Images.council;
   }
}
