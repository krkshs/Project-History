package aoc.kingdoms.lukasz.events.triggers;

import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.textures.Images;

public class EventTrigger_ProvinceCivHasCore extends EventTrigger_Value {
   public int provID;
   public String civA;

   public EventTrigger_ProvinceCivHasCore(int provID, String civA) {
      this.provID = provID;
      this.civA = civA;
   }

   @Override
   public boolean outCondition(int iCivID, int iProvinceID) {
      int idA = Game.getCivID(this.civA);
      return idA > 0 ? Game.getProvince(this.provID).haveACore(idA) : false;
   }

   @Override
   public String getText() {
      int idA = Game.getCivID(this.civA);
      return idA > 0 ? Game.getCiv(idA).getCivName() + ", " + Game.lang.get("HaveACore") + ": " : " -- ";
   }

   @Override
   public String getText2() {
      return Game.getProvince(this.provID).getProvinceName();
   }

   @Override
   public String getText3() {
      int idA = Game.getCivID(this.civA);
      return idA > 0 ? " [" + Game.lang.get("Currently") + ": " + Game.getProvince(this.provID).haveACore(idA) + "]" : " --";
   }

   @Override
   public int getImage() {
      return Images.core;
   }
}
