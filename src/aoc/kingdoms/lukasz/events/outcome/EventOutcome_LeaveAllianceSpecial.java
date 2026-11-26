package aoc.kingdoms.lukasz.events.outcome;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.textures.Images;

public class EventOutcome_LeaveAllianceSpecial extends EventOutcome {
   public int value;

   public EventOutcome_LeaveAllianceSpecial(int value) {
      this.value = value;
   }

   @Override
   public void updateCiv(int iCivID, int bonus_duration) {
      try {
         if (this.value < Game.alliancesSpecialSize) {
            Game.alliancesSpecial.get(this.value).removeCiv(iCivID);
            Game.getCiv(iCivID).removeInAllianceSpecial(this.value);
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   @Override
   public String getStringLeft() {
      return Game.lang.get("LeaveAlliance") + ": ";
   }

   @Override
   public String getStringRight() {
      return this.value < Game.alliancesSpecialSize ? Game.lang.get(Game.alliancesSpecial.get(this.value).Name_Alliance) : "--";
   }

   @Override
   public int getImage() {
      return Images.alliance;
   }
}
