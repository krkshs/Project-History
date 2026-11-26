package aoc.kingdoms.lukasz.events.triggers;

import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.textures.Images;

public class EventTrigger_CivHasLargerRegimentsLimitThanCiv extends EventTrigger_Value {
   public String civA;
   public String civB;

   public EventTrigger_CivHasLargerRegimentsLimitThanCiv(String civA, String civB) {
      this.civA = civA;
      this.civB = civB;
   }

   @Override
   public boolean outCondition(int iCivID, int iProvinceID) {
      int idA = Game.getCivID(this.civA);
      int idB = Game.getCivID(this.civB);
      return idA > 0 && idB > 0 ? Game.getCiv(idA).iRegimentsLimit > Game.getCiv(idB).iRegimentsLimit : false;
   }

   @Override
   public String getText() {
      return Game.lang.get("RegimentsLimit") + ": ";
   }

   @Override
   public String getText2() {
      return "" + Game.lang.getCiv(this.civA) + " > " + Game.lang.getCiv(this.civB);
   }

   @Override
   public String getText3() {
      return "";
   }

   @Override
   public int getImage() {
      return Images.regimentsLimit;
   }
}
