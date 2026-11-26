package aoc.kingdoms.lukasz.events.outcome;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.menusInGame.Info.InGame_Info;
import aoc.kingdoms.lukasz.textures.Images;

public class EventOutcome_AnnexedByCivilization extends EventOutcome {
   public String annexedByCivTag = "";

   public EventOutcome_AnnexedByCivilization(String annexCivTag) {
      this.annexedByCivTag = annexCivTag;
   }

   @Override
   public void updateCiv(int iCivID, int bonus_duration) {
      int annexCivID = Game.getCivID(this.annexedByCivTag);
      if (annexCivID != iCivID && annexCivID > 0 && iCivID > 0) {
         for (int i = Game.getCiv(iCivID).getNumOfProvinces() - 1; i >= 0; i--) {
            try {
               int pID = Game.getCiv(iCivID).getProvinceID(i);
               Game.getProvince(pID).setCivID(annexCivID);
               Game.getProvince(pID).removeArmyCivID(iCivID);
               Game.getProvince(pID).addCore(annexCivID);
            } catch (Exception var7) {
               CFG.exceptionStack(var7);
            }
         }

         Game.gameThread.addCivUpdateTotalIncomePerMonth(annexCivID);
         Game.gameThread.addCivUpdateTotalIncomePerMonth(iCivID);
         if (annexCivID == Game.player.iCivID) {
            InGame_Info.iCivID = annexCivID;
            InGame_Info.iCivID2 = iCivID;
            Game.menuManager
               .rebuildInGame_Info(Game.lang.get("AcceptedOurUltimatum"), Game.getCiv(annexCivID).getCivName() + " - " + Game.getCiv(iCivID).getCivName());
            InGame_Info.imgID = Images.infoDiplomacy;
         }
      }
   }

   @Override
   public String getStringLeft() {
      return Game.lang.getCiv(this.annexedByCivTag) + ", " + Game.lang.get("Annexation") + ": ";
   }

   @Override
   public String getStringRight() {
      return Game.getCiv(Game.player.iCivID).getCivName();
   }

   @Override
   public int getImage() {
      return Images.provinces;
   }
}
