package aoc.kingdoms.lukasz.events.outcome.civilizationBonus;

import aoc.kingdoms.lukasz.events.outcome.EventOutcome;
import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.map.civilization.CivilizationBonuses;
import aoc.kingdoms.lukasz.textures.Images;

public class EventOutcome_BonusBuildingsMaintenanceCost extends EventOutcome {
   public float value;

   public EventOutcome_BonusBuildingsMaintenanceCost(float value) {
      this.value = value;
   }

   @Override
   public void updateCiv(int iCivID, int bonus_duration) {
      try {
         CivilizationBonuses nCivBonus = new CivilizationBonuses();
         nCivBonus.BuildingsMaintenanceCost = this.value / 100.0F;
         nCivBonus.TempTurnID = Game_Calendar.TURN_ID + bonus_duration * 365;
         Game.getCiv(iCivID).addCivilizationBonus_Temporary(nCivBonus);
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   @Override
   public String getStringLeft() {
      return Game.lang.get("BuildingsMaintenanceCost") + ": ";
   }

   @Override
   public String getStringRight() {
      return (this.value > 0.0F ? "+" : "") + CFG.getPrecision2(this.value, 100) + "%";
   }

   @Override
   public String getStringRight2(int bonus_duration) {
      return Game.lang.get("YearsX", bonus_duration) + ", " + Game.lang.get("CivilizationBonus");
   }

   @Override
   public int getImage() {
      return Images.buildings;
   }
}
