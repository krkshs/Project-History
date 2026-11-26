package aoc.kingdoms.lukasz.events.outcome;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.textures.Images;

public class EventOutcome_MilitaryAcademy extends EventOutcome {
   public int value;

   public EventOutcome_MilitaryAcademy(int value) {
      this.value = value;
   }

   @Override
   public void updateCiv(int iCivID, int bonus_duration) {
      try {
         Game.getCiv(iCivID).setMilitaryAcademyLevel(Math.max(0, Game.getCiv(iCivID).getMilitaryAcademyLevel() + this.value));
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   @Override
   public String getStringLeft() {
      return Game.lang.get("MilitaryAcademy") + ": ";
   }

   @Override
   public String getStringRight() {
      return (this.value > 0 ? "+" : "") + CFG.getPrecision2((float)this.value, 1);
   }

   @Override
   public int getImage() {
      return Images.armyMilitaryAcademy;
   }
}
