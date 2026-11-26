package aoc.kingdoms.lukasz.events.triggers;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.map.ResourcesManager;
import aoc.kingdoms.lukasz.textures.Images;

public class EventTrigger_LargestProducer_ProductionOver extends EventTrigger_Value {
   public int resourceID;
   public int value;

   public EventTrigger_LargestProducer_ProductionOver(int resourceID, int value) {
      this.resourceID = resourceID;
      this.value = value;
   }

   @Override
   public boolean outCondition(int iCivID, int iProvinceID) {
      try {
         return ResourcesManager.worldResources_LargestProducer_Amount.get(this.resourceID) > this.value * 100;
      } catch (Exception var4) {
         return false;
      }
   }

   @Override
   public String getText() {
      return ResourcesManager.getResourceName(this.resourceID) + ": " + Game.lang.get("LargestProducer") + ", " + Game.lang.get("Production") + " > ";
   }

   @Override
   public String getText2() {
      return "" + CFG.getPrecision2((float)this.value, 100);
   }

   @Override
   public String getText3() {
      return " ["
         + Game.lang.get("Currently")
         + ": "
         + CFG.getPrecision2((float)ResourcesManager.worldResources_LargestProducer_Amount.get(this.resourceID).intValue(), 100)
         + "]";
   }

   @Override
   public int getImage() {
      return Images.goods;
   }
}
