package aoc.kingdoms.lukasz.events.outcome;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.textures.Images;

public class EventOutcome_SetCiv extends EventOutcome {
   public String value;

   public EventOutcome_SetCiv(String value) {
      this.value = value;
   }

   @Override
   public void updateCiv(int iCivID, int bonus_duration) {
      try {
         Game.getCiv(iCivID).setCivTag(this.value);
         Game.addSimpleTask(new Game.SimpleTask(this.value, iCivID) {
            @Override
            public void update() {
               Game.LoadCivilizationData civData = Game.loadCivilization(this.taskKey);
               Game.getCiv(this.id).updateCivilizationTAG(this.taskKey, civData.iR, civData.iG, civData.iB);
            }
         });
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   @Override
   public String getStringLeft() {
      return Game.lang.get("FormCivilization") + ": ";
   }

   @Override
   public String getStringRight() {
      return Game.lang.getCiv(this.value);
   }

   @Override
   public int getImage() {
      return Images.government;
   }
}
