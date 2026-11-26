package aoc.kingdoms.lukasz.events.triggers;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.map.battles.BattleManager;
import aoc.kingdoms.lukasz.textures.Images;

public class EventTrigger_CivBattleWidthBelow extends EventTrigger_Value {
   public int value;

   public EventTrigger_CivBattleWidthBelow(int value) {
      this.value = value;
   }

   @Override
   public boolean outCondition(int iCivID, int iProvinceID) {
      return BattleManager.getBattleWidth(iCivID) < this.value;
   }

   @Override
   public String getText() {
      return Game.lang.get("BattleWidth") + " < ";
   }

   @Override
   public String getText2() {
      return "" + CFG.getPrecision2((float)this.value, 100);
   }

   @Override
   public String getText3() {
      return " [" + Game.lang.get("Currently") + ": " + CFG.getPrecision2((float)BattleManager.getBattleWidth(Game.player.iCivID), 1) + "]";
   }

   @Override
   public int getImage() {
      return Images.battleWidth;
   }
}
