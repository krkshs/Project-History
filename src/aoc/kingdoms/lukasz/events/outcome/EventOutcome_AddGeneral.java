package aoc.kingdoms.lukasz.events.outcome;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.map.civilization.CivilizationGeneralsPool;
import aoc.kingdoms.lukasz.textures.Images;

public class EventOutcome_AddGeneral extends EventOutcome {
   @Override
   public void updateCiv(int iCivID, int bonus_duration) {
      try {
         Game.getCiv(iCivID).addGeneral(CivilizationGeneralsPool.getGeneral_Random(iCivID));
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   @Override
   public String getStringLeft() {
      return Game.lang.get("RecruitGeneral");
   }

   @Override
   public String getStringRight() {
      return "";
   }

   @Override
   public int getImage() {
      return Images.general;
   }
}
