package aoc.kingdoms.lukasz.events.outcome;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.textures.Images;

public class EventOutcome_RelationSet extends EventOutcome {
   public String fromCivTAG;
   public String toCivTag;
   public float value;

   public EventOutcome_RelationSet(String fromCivTAG, String toCivTag, float value) {
      this.fromCivTAG = fromCivTAG;
      this.toCivTag = toCivTag;
      this.value = value;
   }

   @Override
   public void updateCiv(int iCivID, int bonus_duration) {
      try {
         int fromCivID = Game.getCivID(this.fromCivTAG);
         int toCivID = Game.getCivID(this.toCivTag);
         if (fromCivID > 0 && toCivID > 0) {
            Game.getCiv(fromCivID).diplomacy.setRelation(fromCivID, toCivID, this.value);
            Game.getCiv(toCivID).diplomacy.setRelation(toCivID, fromCivID, this.value);
         }
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
      }
   }

   @Override
   public String getStringLeft() {
      return Game.lang.get("Relations") + ":  ";
   }

   @Override
   public String getStringRight() {
      return Game.lang.getCiv(this.fromCivTAG)
         + " - "
         + Game.lang.getCiv(this.toCivTag)
         + " --> "
         + (this.value > 0.0F ? "+" : "")
         + CFG.getPrecision2(this.value, 100);
   }

   @Override
   public int getImage() {
      return Images.relations;
   }
}
