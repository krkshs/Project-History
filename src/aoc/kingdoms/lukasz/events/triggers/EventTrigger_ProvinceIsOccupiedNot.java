package aoc.kingdoms.lukasz.events.triggers;

import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.textures.Images;

public class EventTrigger_ProvinceIsOccupiedNot extends EventTrigger_Value {
   public int provID;

   public EventTrigger_ProvinceIsOccupiedNot(int provID) {
      this.provID = provID;
   }

   @Override
   public boolean outCondition(int iCivID, int iProvinceID) {
      return this.provID >= 0 ? !Game.getProvince(this.provID).isOccupied() : false;
   }

   @Override
   public String getText() {
      return Game.getProvince(this.provID).getProvinceName() + ", " + Game.lang.get("Occupied") + ": " + Game.lang.get("NOT");
   }

   @Override
   public String getText2() {
      return "";
   }

   @Override
   public String getText3() {
      return "";
   }

   @Override
   public int getImage() {
      return Images.provinces;
   }
}
