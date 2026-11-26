package aoc.kingdoms.lukasz.events.triggers;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.textures.Images;

public class EventTrigger_OpinionOver extends EventTrigger_Value {
   public String civA;
   public String civB;
   public float value;

   public EventTrigger_OpinionOver(String civA, String civB, float value) {
      this.civA = civA;
      this.civB = civB;
      this.value = value;
   }

   @Override
   public boolean outCondition(int iCivID, int iProvinceID) {
      int idA = Game.getCivID(this.civA);
      int idB = Game.getCivID(this.civB);
      return idA > 0 && idB > 0 ? Game.getCiv(idA).diplomacy.getRelation(idB) > this.value : false;
   }

   @Override
   public String getText() {
      return Game.lang.get("Opinion") + ": ";
   }

   @Override
   public String getText2() {
      return Game.lang.getCiv(this.civA) + " - " + Game.lang.getCiv(this.civB) + " > " + this.value;
   }

   @Override
   public String getText3() {
      int idA = Game.getCivID(this.civA);
      int idB = Game.getCivID(this.civB);
      return idA > 0 && idB > 0 ? " [" + Game.lang.get("Currently") + ": " + CFG.getPrecision2(Game.getCiv(idA).diplomacy.getRelation(idB), 1) + "]" : "";
   }

   @Override
   public int getImage() {
      return Images.relations;
   }
}
