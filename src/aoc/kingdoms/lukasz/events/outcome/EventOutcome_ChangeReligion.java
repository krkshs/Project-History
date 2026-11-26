package aoc.kingdoms.lukasz.events.outcome;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.menusInGame.Info.InGame_Info;
import aoc.kingdoms.lukasz.textures.Images;

public class EventOutcome_ChangeReligion extends EventOutcome {
   public int value;

   public EventOutcome_ChangeReligion(int value) {
      this.value = value;
   }

   @Override
   public void updateCiv(int iCivID, int bonus_duration) {
      try {
         if (this.value >= 0 && this.value < Game.religionManager.getReligionsSize()) {
            Game.getCiv(iCivID).setReligionID_UpdateBonuses(this.value);
            Game.getCiv(iCivID).updateProvincesIncomeAndExpenses();
            Game.gameThread.addCivUpdateTotalIncomePerMonth(iCivID);
            if (iCivID == Game.player.iCivID) {
               InGame_Info.iCivID = Game.player.iCivID;
               InGame_Info.iCivID2 = 0;
               Game.menuManager.rebuildInGame_Info(Game.lang.get("Religion"), Game.religionManager.getReligion(this.value).Name);
               InGame_Info.imgID = Images.infoCrown;
            }
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   @Override
   public String getStringLeft() {
      return Game.lang.get("ConvertReligion") + ": ";
   }

   @Override
   public String getStringRight() {
      return this.value >= 0 && this.value < Game.religionManager.getReligionsSize()
         ? Game.religionManager.getReligion(this.value).Name
         : Game.lang.get("None");
   }

   @Override
   public int getImage() {
      return Images.religion;
   }
}
