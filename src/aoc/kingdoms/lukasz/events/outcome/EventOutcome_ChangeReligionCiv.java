package aoc.kingdoms.lukasz.events.outcome;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.menusInGame.Info.InGame_Info;
import aoc.kingdoms.lukasz.textures.Images;

public class EventOutcome_ChangeReligionCiv extends EventOutcome {
   public int value;
   public String civA;

   public EventOutcome_ChangeReligionCiv(int value, String civA) {
      this.value = value;
      this.civA = civA;
   }

   @Override
   public void updateCiv(int iCivID, int bonus_duration) {
      try {
         if (this.value >= 0 && this.value < Game.religionManager.getReligionsSize()) {
            int idA = Game.getCivID(this.civA);
            if (idA > 0) {
               Game.getCiv(idA).setReligionID_UpdateBonuses(this.value);
               Game.getCiv(idA).updateProvincesIncomeAndExpenses();
               Game.gameThread.addCivUpdateTotalIncomePerMonth(idA);
               if (idA == Game.player.iCivID) {
                  InGame_Info.iCivID = Game.player.iCivID;
                  InGame_Info.iCivID2 = 0;
                  Game.menuManager.rebuildInGame_Info(Game.lang.get("Religion"), Game.religionManager.getReligion(this.value).Name);
                  InGame_Info.imgID = Images.infoCrown;
               }
            }
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   @Override
   public String getStringLeft() {
      int idA = Game.getCivID(this.civA);
      return idA > 0 ? Game.getCiv(idA).getCivName() + ", " + Game.lang.get("ConvertReligion") + ": " : " -- ";
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
