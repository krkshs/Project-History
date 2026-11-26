package aoc.kingdoms.lukasz.events.outcome;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.CharactersManager;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.textures.Images;

public class EventOutcome_AddGeneral_Character extends EventOutcome {
   public String sGeneral;

   public EventOutcome_AddGeneral_Character(String sGeneral) {
      this.sGeneral = sGeneral;
   }

   @Override
   public void updateCiv(int iCivID, int bonus_duration) {
      try {
         CharactersManager.loadGeneral(iCivID, this.sGeneral, -99, -99);
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   @Override
   public String getStringLeft() {
      return Game.lang.get("RecruitGeneral") + ": ";
   }

   @Override
   public String getStringRight() {
      return "" + this.sGeneral;
   }

   @Override
   public int getImage() {
      return Images.general;
   }
}
