package aoc.kingdoms.lukasz.events.outcome;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.textures.Images;

public class EventOutcome_SetCiv2 extends EventOutcome {
   public String fromCivTAG;
   public String toCivTag;

   public EventOutcome_SetCiv2(String fromCivTAG, String toCivTag) {
      this.fromCivTAG = fromCivTAG;
      this.toCivTag = toCivTag;
   }

   @Override
   public void updateCiv(int iCivID, int bonus_duration) {
      try {
         int fromCivID = Game.getCivID(this.fromCivTAG);
         if (fromCivID > 0) {
            Game.getCiv(fromCivID).setCivTag(this.toCivTag);
            Game.addSimpleTask(new Game.SimpleTask(this.toCivTag, fromCivID) {
               @Override
               public void update() {
                  Game.LoadCivilizationData civData = Game.loadCivilization(this.taskKey);
                  Game.getCiv(this.id).updateCivilizationTAG(this.taskKey, civData.iR, civData.iG, civData.iB);
               }
            });
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   @Override
   public String getStringLeft() {
      return Game.lang.getCiv(this.fromCivTAG) + " -> ";
   }

   @Override
   public String getStringRight() {
      return Game.lang.getCiv(this.toCivTag);
   }

   @Override
   public int getImage() {
      return Images.government;
   }
}
