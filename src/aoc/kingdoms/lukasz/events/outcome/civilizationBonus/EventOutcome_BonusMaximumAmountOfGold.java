package aoc.kingdoms.lukasz.events.outcome.civilizationBonus;

import aoc.kingdoms.lukasz.events.outcome.EventOutcome;
import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.map.civilization.CivilizationBonuses;
import aoc.kingdoms.lukasz.textures.Images;

public class EventOutcome_BonusMaximumAmountOfGold extends EventOutcome {
   public float value;

   public EventOutcome_BonusMaximumAmountOfGold(float value) {
      this.value = value;
   }

   @Override
   public void updateCiv(int iCivID, int bonus_duration) {
      try {
         CivilizationBonuses nCivBonus = new CivilizationBonuses();
         nCivBonus.MaximumAmountOfGold = this.value;
         nCivBonus.TempTurnID = Game_Calendar.TURN_ID + bonus_duration * 365;
         Game.getCiv(iCivID).addCivilizationBonus_Temporary(nCivBonus);
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   @Override
   public String getStringLeft() {
      return Game.lang.get("MaximumAmountOfGold") + ": ";
   }

   @Override
   public String getStringRight() {
      return (this.value > 0.0F ? "+" : "") + CFG.getPrecision2(this.value, 1);
   }

   @Override
   public String getStringRight2(int bonus_duration) {
      return Game.lang.get("YearsX", bonus_duration);
   }

   @Override
   public int getImage() {
      return Images.gold;
   }
}
