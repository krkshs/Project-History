package aoc.kingdoms.lukasz.events.outcome;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.CharactersManager;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.map.advisors.AdvisorManager;
import aoc.kingdoms.lukasz.textures.Images;

public class EventOutcome_AddAdvisor_Character extends EventOutcome {
   public String sName;
   public int advisorTypeID;

   public EventOutcome_AddAdvisor_Character(String sName, int advisorTypeID) {
      this.sName = sName;
      this.advisorTypeID = advisorTypeID;
   }

   @Override
   public void updateCiv(int iCivID, int bonus_duration) {
      try {
         CharactersManager.loadAdvisor(iCivID, this.sName, this.advisorTypeID);
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   @Override
   public String getStringLeft() {
      return Game.lang.get("HireAdvisor") + ": ";
   }

   @Override
   public String getStringRight() {
      return "" + this.sName + " - " + AdvisorManager.getAdvisorGroupName(this.advisorTypeID);
   }

   @Override
   public int getImage() {
      return Images.council;
   }
}
