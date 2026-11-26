package aoc.kingdoms.lukasz.events.triggers;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.map.ResourcesManager;
import aoc.kingdoms.lukasz.textures.Images;

public class EventTrigger_IsLargestProducer extends EventTrigger_Value {
   public int resourceID;

   public EventTrigger_IsLargestProducer(int resourceID) {
      this.resourceID = resourceID;
   }

   @Override
   public boolean outCondition(int iCivID, int iProvinceID) {
      try {
         return ResourcesManager.worldResources_LargestProducer.get(this.resourceID) == iCivID;
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
         return false;
      }
   }

   @Override
   public String getText() {
      return ResourcesManager.getResourceName(this.resourceID) + ": " + Game.lang.get("LargestProducer");
   }

   @Override
   public String getText2() {
      return "";
   }

   @Override
   public String getText3() {
      return " [" + Game.lang.get("Currently") + ": " + Game.getCiv(ResourcesManager.worldResources_LargestProducer.get(this.resourceID)).getCivName() + "]";
   }

   @Override
   public int getImage() {
      return Images.goods;
   }
}
