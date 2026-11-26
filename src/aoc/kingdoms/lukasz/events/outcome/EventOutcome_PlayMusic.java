package aoc.kingdoms.lukasz.events.outcome;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.textures.Images;

public class EventOutcome_PlayMusic extends EventOutcome {
   public String fileName;

   public EventOutcome_PlayMusic(String fileName) {
      this.fileName = fileName;
   }

   @Override
   public void updateCiv(int iCivID, int bonus_duration) {
      try {
         Game.soundsManager.loadNextMusic(this.fileName);
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   @Override
   public String getStringLeft() {
      return Game.lang.get("Audio") + ": ";
   }

   @Override
   public String getStringRight() {
      return this.fileName;
   }

   @Override
   public String getStringRight2(int bonus_duration) {
      return "";
   }

   @Override
   public int getImage() {
      return Images.settings;
   }
}
