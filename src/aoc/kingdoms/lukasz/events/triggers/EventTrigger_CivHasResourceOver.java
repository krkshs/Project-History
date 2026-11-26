package aoc.kingdoms.lukasz.events.triggers;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.map.ResourcesManager;
import aoc.kingdoms.lukasz.textures.Images;

public class EventTrigger_CivHasResourceOver extends EventTrigger_Value {
   public int value;
   public float value_over;

   public EventTrigger_CivHasResourceOver(int value, float value_over) {
      this.value = value;
      this.value_over = value_over;
   }

   @Override
   public boolean outCondition(int iCivID, int iProvinceID) {
      try {
         return Game.getCiv(iCivID).getGoodsProduced(this.value) > this.value_over * 100.0F;
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
         return false;
      }
   }

   @Override
   public String getText() {
      return Game.lang.get("HasTheResource") + ": ";
   }

   @Override
   public String getText2() {
      return this.value >= 0 && this.value < ResourcesManager.iResourcesSize
         ? ResourcesManager.getResourceName(this.value) + ", " + Game.lang.get("Production") + " > " + CFG.getPrecision2(this.value_over, 100)
         : Game.lang.get("None");
   }

   @Override
   public String getText3() {
      float tCurrent = 0.0F;

      for (int i = 0; i < Game.getCiv(Game.player.iCivID).getNumOfProvinces(); i++) {
         if (Game.getProvince(Game.getCiv(Game.player.iCivID).getProvinceID(i)).getResourceID() == this.value) {
            tCurrent += ResourcesManager.getProducedGoods(Game.getCiv(Game.player.iCivID).getProvinceID(i));
         }
      }

      return " [" + Game.lang.get("Currently") + ": " + CFG.getPrecision2(tCurrent, 100) + "]";
   }

   @Override
   public int getImage() {
      return Images.goods;
   }
}
