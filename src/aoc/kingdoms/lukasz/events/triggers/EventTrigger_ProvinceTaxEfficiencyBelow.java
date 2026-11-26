package aoc.kingdoms.lukasz.events.triggers;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.textures.Images;

public class EventTrigger_ProvinceTaxEfficiencyBelow extends EventTrigger_Value {
   public int provID;
   public float value;

   public EventTrigger_ProvinceTaxEfficiencyBelow(int provID, float value) {
      this.provID = provID;
      this.value = value;
   }

   @Override
   public boolean outCondition(int iCivID, int iProvinceID) {
      return this.provID >= 0 ? Game.getProvince(this.provID).getTaxEfficiencyWithBonuses() < this.value : false;
   }

   @Override
   public String getText() {
      return Game.getProvince(this.provID).getProvinceName() + ", " + Game.lang.get("TaxEfficiency") + " < ";
   }

   @Override
   public String getText2() {
      return "" + CFG.getPrecision2(this.value, 100);
   }

   @Override
   public String getText3() {
      return " [" + Game.lang.get("Currently") + ": " + CFG.getPrecision2(Game.getProvince(this.provID).getTaxEfficiencyWithBonuses(), 100) + "%]";
   }

   @Override
   public int getImage() {
      return Images.tax;
   }
}
