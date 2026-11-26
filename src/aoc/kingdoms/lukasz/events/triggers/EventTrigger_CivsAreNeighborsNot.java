package aoc.kingdoms.lukasz.events.triggers;

import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.textures.Images;

public class EventTrigger_CivsAreNeighborsNot extends EventTrigger_Value {
   public String civA;
   public String civB;

   public EventTrigger_CivsAreNeighborsNot(String civA, String civB) {
      this.civA = civA;
      this.civB = civB;
   }

   @Override
   public boolean outCondition(int iCivID, int iProvinceID) {
      int idA = Game.getCivID(this.civA);
      int idB = Game.getCivID(this.civB);
      if (idA > 0 && idB > 0) {
         for (int i = 0; i < Game.getCiv(idA).civNeighbors.civsSize; i++) {
            if (Game.getCiv(idA).civNeighbors.civs.get(i).civID == idB && Game.getCiv(idA).civNeighbors.civs.get(i).byLand) {
               return false;
            }
         }
      }

      return false;
   }

   @Override
   public String getText() {
      return Game.lang.get("NOT") + ", " + Game.lang.get("Neighbors") + ": ";
   }

   @Override
   public String getText2() {
      return "" + Game.lang.getCiv(this.civA) + " - " + Game.lang.getCiv(this.civB);
   }

   @Override
   public String getText3() {
      return "";
   }

   @Override
   public int getImage() {
      return Images.frontLine;
   }
}
