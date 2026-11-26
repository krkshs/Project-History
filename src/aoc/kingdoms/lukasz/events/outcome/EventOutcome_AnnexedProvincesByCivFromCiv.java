package aoc.kingdoms.lukasz.events.outcome;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.map.army.ArmyDivision;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import aoc.kingdoms.lukasz.menusInGame.Info.InGame_Info;
import aoc.kingdoms.lukasz.textures.Images;
import java.util.List;

public class EventOutcome_AnnexedProvincesByCivFromCiv extends EventOutcome {
   public String sFromCivTAG;
   public String byCivTAG;
   public List<Integer> lProvinces;

   public EventOutcome_AnnexedProvincesByCivFromCiv(String byCivTAG, String sFromCivTAG, List<Integer> nProvinces) {
      this.byCivTAG = byCivTAG;
      this.sFromCivTAG = sFromCivTAG;
      this.lProvinces = nProvinces;

      for (int i = this.lProvinces.size() - 1; i >= 0; i--) {
         if (this.lProvinces.get(i) < 0 || this.lProvinces.get(i) >= Game.getProvincesSize()) {
            this.lProvinces.remove(i);
         }
      }
   }

   @Override
   public void updateCiv(int iCivID, int bonus_duration) {
      try {
         int annexByCivID = Game.getCivID(this.byCivTAG);
         int annexFromCivID = Game.getCivID(this.sFromCivTAG);
         if (annexByCivID > 0 && annexFromCivID > 0) {
            for (int i = this.lProvinces.size() - 1; i >= 0; i--) {
               if (Game.getProvince(this.lProvinces.get(i)).getCivID() == annexFromCivID) {
                  Game.getProvince(this.lProvinces.get(i)).setCivID(annexByCivID);
                  Game.getProvince(this.lProvinces.get(i)).addCore(annexByCivID);
               }
            }

            for (int ix = this.lProvinces.size() - 1; ix >= 0; ix--) {
               if (Game.getProvince(this.lProvinces.get(ix)).getCivID() == annexByCivID) {
                  for (int j = Game.getProvince(this.lProvinces.get(ix)).getArmySize() - 1; j >= 0; j--) {
                     if (!DiplomacyManager.isAlly(annexByCivID, Game.getProvince(this.lProvinces.get(ix)).getArmy(j).civID)) {
                        ArmyDivision tArmy = Game.getProvince(this.lProvinces.get(ix)).getArmy(j);
                        Game.getProvince(this.lProvinces.get(ix)).removeArmy(j);
                        if (tArmy.civID > 0
                           && Game.getCiv(tArmy.civID).getCapitalProvinceID() >= 0
                           && Game.getProvince(Game.getCiv(tArmy.civID).getCapitalProvinceID()).getCivID() == tArmy.civID) {
                           Game.getProvince(Game.getCiv(tArmy.civID).getCapitalProvinceID()).addArmy(tArmy);
                        }
                     }
                  }
               }
            }

            Game.gameThread.addCivUpdateTotalIncomePerMonth(annexByCivID);
            Game.gameThread.addCivUpdateTotalIncomePerMonth(annexFromCivID);
            if (annexByCivID == Game.player.iCivID) {
               InGame_Info.iCivID = annexByCivID;
               InGame_Info.iCivID2 = annexFromCivID;
               Game.menuManager
                  .rebuildInGame_Info(
                     Game.lang.get("AcceptedOurUltimatum"), Game.getCiv(annexByCivID).getCivName() + " - " + Game.getCiv(annexFromCivID).getCivName()
                  );
               InGame_Info.imgID = Images.infoDiplomacy;
            }
         }
      } catch (Exception var8) {
         CFG.exceptionStack(var8);
      }
   }

   @Override
   public String getStringLeft() {
      return Game.lang.getCiv(this.byCivTAG) + ", " + Game.lang.get("Annexation") + ": ";
   }

   @Override
   public String getStringRight() {
      String out = "";
      int annexCivID = Game.getCivID(this.sFromCivTAG);

      for (int i = 0; i < this.lProvinces.size(); i++) {
         if (this.lProvinces.get(i) >= 0
            && this.lProvinces.get(i) < Game.getProvincesSize()
            && Game.getProvince(this.lProvinces.get(i)).getCivID() == annexCivID) {
            out = out + Game.getProvince(this.lProvinces.get(i)).getProvinceName() + (i == this.lProvinces.size() - 1 ? "" : ", ");
         }
      }

      return out.length() > 0 ? out : Game.lang.get("None");
   }

   @Override
   public int getImage() {
      return Images.provinces;
   }
}
