package aoc.kingdoms.lukasz.events.triggers;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.textures.Images;

public class EventTrigger_CivInflationBelow extends EventTrigger_Value {
   public float value;

   public EventTrigger_CivInflationBelow(float value) {
      this.value = value;
   }

   @Override
   public boolean outCondition(int iCivID, int iProvinceID) {
      return Game.getCiv(iCivID).getInflation() > this.value / 100.0F;
   }

   @Override
   public String getText() {
      return Game.lang.get("Inflation") + " > ";
   }

   @Override
   public String getText2() {
      return "" + CFG.getPrecision2(this.value, 100) + "%";
   }

   @Override
   public String getText3() {
      return " [" + Game.lang.get("Currently") + ": " + CFG.getPrecision2(Game.getCiv(Game.player.iCivID).getInflation() * 100.0F, 100) + "%]";
   }

   @Override
   public int getImage() {
      return Images.inflation;
   }
}
