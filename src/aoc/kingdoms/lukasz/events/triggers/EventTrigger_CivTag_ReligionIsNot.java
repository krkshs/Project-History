package aoc.kingdoms.lukasz.events.triggers;

import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.textures.Images;

public class EventTrigger_CivTag_ReligionIsNot extends EventTrigger_Value {
   public String civA;
   public int value;

   public EventTrigger_CivTag_ReligionIsNot(String civA, int value) {
      this.civA = civA;
      this.value = value;
   }

   @Override
   public boolean outCondition(int iCivID, int iProvinceID) {
      int idA = Game.getCivID(this.civA);
      return idA > 0 ? Game.getCiv(idA).getReligionID() != this.value : false;
   }

   @Override
   public String getText() {
      int idA = Game.getCivID(this.civA);
      return idA > 0
         ? Game.getCiv(idA).getCivName() + ", " + Game.lang.get("Religion") + ", " + Game.lang.get("NOT") + ": "
         : Game.lang.get("Religion") + " != ";
   }

   @Override
   public String getText2() {
      return "" + Game.religionManager.getReligion(this.value).Name;
   }

   @Override
   public String getText3() {
      return "";
   }

   @Override
   public int getImage() {
      return Images.religion;
   }
}
