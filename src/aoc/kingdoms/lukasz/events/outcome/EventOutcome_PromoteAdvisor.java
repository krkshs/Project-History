package aoc.kingdoms.lukasz.events.outcome;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.map.advisors.AdvisorManager;
import aoc.kingdoms.lukasz.textures.Images;

public class EventOutcome_PromoteAdvisor extends EventOutcome {
   public int value;

   public EventOutcome_PromoteAdvisor(int value) {
      this.value = value;
   }

   @Override
   public void updateCiv(int iCivID, int bonus_duration) {
      try {
         AdvisorManager.promoteAdvisor(iCivID, this.value, true);
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   @Override
   public String getStringLeft() {
      return Game.lang.get("PromoteAdvisor") + ": ";
   }

   @Override
   public String getStringRight() {
      return AdvisorManager.getAdvisorGroupName(this.value);
   }

   @Override
   public int getImage() {
      return Images.skill;
   }
}
