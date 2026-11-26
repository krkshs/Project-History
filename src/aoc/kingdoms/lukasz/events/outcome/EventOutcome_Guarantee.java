package aoc.kingdoms.lukasz.events.outcome;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import aoc.kingdoms.lukasz.textures.Images;

public class EventOutcome_Guarantee extends EventOutcome {
   public String fromCivTAG;
   public String toCivTag;

   public EventOutcome_Guarantee(String fromCivTAG, String toCivTag) {
      this.fromCivTAG = fromCivTAG;
      this.toCivTag = toCivTag;
   }

   @Override
   public void updateCiv(int iCivID, int bonus_duration) {
      try {
         int fromCivID = Game.getCivID(this.fromCivTAG);
         int toCivID = Game.getCivID(this.toCivTag);
         if (fromCivID > 0 && toCivID > 0 && !DiplomacyManager.isAtWar(fromCivID, toCivID)) {
            DiplomacyManager.addGuarantee(fromCivID, toCivID);
         }
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
      }
   }

   @Override
   public String getStringLeft() {
      return Game.lang.get("GuaranteeIndependence") + ": ";
   }

   @Override
   public String getStringRight() {
      return Game.lang.getCiv(this.fromCivTAG) + " -> " + Game.lang.getCiv(this.toCivTag);
   }

   @Override
   public int getImage() {
      return Images.guaranteeIndependence;
   }
}
