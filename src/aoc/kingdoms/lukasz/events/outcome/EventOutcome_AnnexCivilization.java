package aoc.kingdoms.lukasz.events.outcome;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.textures.Images;

public class EventOutcome_AnnexCivilization extends EventOutcome {
   public String annexCivTag = "";

   public EventOutcome_AnnexCivilization(String annexCivTag) {
      this.annexCivTag = annexCivTag;
   }

   @Override
   public void updateCiv(int iCivID, int bonus_duration) {
      int annexCivID = Game.getCivID(this.annexCivTag);
      if (annexCivID != iCivID && annexCivID > 0) {
         for (int i = Game.getCiv(annexCivID).getNumOfProvinces() - 1; i >= 0; i--) {
            try {
               int pID = Game.getCiv(annexCivID).getProvinceID(i);
               Game.getProvince(pID).setCivID(iCivID);
               Game.getProvince(pID).removeArmyCivID(annexCivID);
               Game.getProvince(pID).addCore(iCivID);
            } catch (Exception var7) {
               CFG.exceptionStack(var7);
            }
         }
      }
   }

   @Override
   public String getStringLeft() {
      return Game.lang.get("Annexation") + ": ";
   }

   @Override
   public String getStringRight() {
      int annexCivID = Game.getCivID(this.annexCivTag);
      return annexCivID > 0 && Game.getCiv(annexCivID).getNumOfProvinces() > 0 ? Game.getCiv(annexCivID).getCivName() : Game.lang.get("None");
   }

   @Override
   public int getImage() {
      return Images.provinces;
   }
}
