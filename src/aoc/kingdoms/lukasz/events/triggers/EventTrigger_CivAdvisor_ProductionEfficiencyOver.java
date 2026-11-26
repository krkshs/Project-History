package aoc.kingdoms.lukasz.events.triggers;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.map.advisors.AdvisorManager;
import aoc.kingdoms.lukasz.textures.Images;

public class EventTrigger_CivAdvisor_ProductionEfficiencyOver extends EventTrigger_Value {
   public float value;
   public int advisorType;

   public EventTrigger_CivAdvisor_ProductionEfficiencyOver(int advisorType, float value) {
      this.advisorType = advisorType;
      this.value = value;
   }

   @Override
   public boolean outCondition(int iCivID, int iProvinceID) {
      return AdvisorManager.getAdvisor(iCivID, this.advisorType).sName == null
         ? false
         : AdvisorManager.getAdvisor(iCivID, this.advisorType).ProductionEfficiency > this.value;
   }

   @Override
   public String getText() {
      return AdvisorManager.getAdvisorGroupName(this.advisorType) + ", " + Game.lang.get("ProductionEfficiency") + " > ";
   }

   @Override
   public String getText2() {
      return "" + CFG.getPrecision2(this.value, 100);
   }

   @Override
   public String getText3() {
      return AdvisorManager.getAdvisor(Game.player.iCivID, this.advisorType).sName == null
         ? " [" + Game.lang.get("Currently") + ": " + Game.lang.get("NoAdvisor") + "]"
         : " ["
            + Game.lang.get("Currently")
            + ": "
            + CFG.getPrecision2(AdvisorManager.getAdvisor(Game.player.iCivID, this.advisorType).ProductionEfficiency, 100)
            + "%]";
   }

   @Override
   public int getImage() {
      return Images.goods;
   }
}
