package aoc.kingdoms.lukasz.events.outcome;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.menusInGame.InGame_Sandbox;
import aoc.kingdoms.lukasz.textures.Images;

public class EventOutcome_PlayerChangeCiv extends EventOutcome {
   public String value;

   public EventOutcome_PlayerChangeCiv(String value) {
      this.value = value;
   }

   @Override
   public void updateCiv(int iCivID, int bonus_duration) {
      try {
         int tID = Game.getCivID(this.value);
         if (tID > 0 && iCivID != tID && Game.getCiv(tID).getNumOfProvinces() > 0) {
            InGame_Sandbox.setPlayerCiv(tID);
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   @Override
   public String getStringLeft() {
      return Game.lang.get("Player") + ", " + Game.lang.get("Civilization") + ": ";
   }

   @Override
   public String getStringRight() {
      int tID = Game.getCivID(this.value);
      return tID > 0 ? Game.getCiv(tID).getCivName() : " -- ";
   }

   @Override
   public int getImage() {
      return Images.play;
   }
}
