package aoc.kingdoms.lukasz.events.triggers;

import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.textures.Images;

public class EventTrigger_ProvinceControlledByCivNot extends EventTrigger_Value {
   public int provID;
   public String civA;

   public EventTrigger_ProvinceControlledByCivNot(int provID, String civA) {
      this.provID = provID;
      this.civA = civA;
   }

   @Override
   public boolean outCondition(int iCivID, int iProvinceID) {
      int idA = Game.getCivID(this.civA);
      return idA > 0 ? Game.getProvince(this.provID).getCivID() != idA : false;
   }

   @Override
   public String getText() {
      return Game.getProvince(this.provID).getProvinceName() + ", " + Game.lang.get("ControlledBy") + ", " + Game.lang.get("NOT") + ": ";
   }

   @Override
   public String getText2() {
      int idA = Game.getCivID(this.civA);
      return idA > 0 ? Game.getCiv(idA).getCivName() : " -- ";
   }

   @Override
   public String getText3() {
      return " [" + Game.lang.get("Currently") + ": " + Game.getCiv(Game.getProvince(this.provID).getCivID()).getCivName() + "]";
   }

   @Override
   public int getImage() {
      return Images.provinces;
   }
}
