package aoc.kingdoms.lukasz.events.outcome;

import aoc.kingdoms.lukasz.events.EventsManager;
import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.textures.Images;

public class EventOutcome_RunEvent extends EventOutcome {
   public String eventID;

   public EventOutcome_RunEvent(String eventID) {
      this.eventID = eventID;
   }

   @Override
   public void updateCiv(int iCivID, int bonus_duration) {
      try {
         EventsManager.runEvent.add(this.eventID);
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   @Override
   public String getStringLeft() {
      return Game.lang.get("Event");
   }

   @Override
   public String getStringRight() {
      return "";
   }

   @Override
   public int getImage() {
      return Images.time;
   }
}
