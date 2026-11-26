package aoc.kingdoms.lukasz.map;

import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.map.army.ArmyDivision;
import aoc.kingdoms.lukasz.map.army.ArmyManager;
import aoc.kingdoms.lukasz.map.army.ArmyRegiment;
import aoc.kingdoms.lukasz.map.battles.BattleManager;
import aoc.kingdoms.lukasz.map.civilization.Civilization;
import java.util.ArrayList;
import java.util.List;

public class MercenariesManager {
   public static List<MercenariesManager.MercenaryArmy> getMercenaryArmies(int civID) {
      List<MercenariesManager.MercenaryArmy> out = new ArrayList<>();
      int width = BattleManager.getBattleWidth(civID);
      if (width > 3) {
         for (int a = 0; a < GameValues.army.MERCENARIES_LIMIT_TO_CHOOSE_FROM; a++) {
            List<MercenariesManager.BestArmy> bestUnits = new ArrayList<>();
            MercenariesManager.MercenaryArmy mercenaryArmy = new MercenariesManager.MercenaryArmy();
            int limit = (int)Math.max(2.0F, width * ((a + 1.0F) / GameValues.army.MERCENARIES_LIMIT_TO_CHOOSE_FROM));
            int random = 0;
            List<MercenariesManager.MercenaryArmy> tempMercenary = new ArrayList<>();

            for (int i = 0; i < Game.getCiv(civID).unitsBest.size(); i++) {
               if ((
                     ArmyManager.lUnitsTypes.get(Game.getCiv(civID).unitsBest.get(i).unitID).Line == 0
                        || ArmyManager.lUnitsTypes.get(Game.getCiv(civID).unitsBest.get(i).unitID).Line == 1
                  )
                  && !ArmyManager.lArmy.get(Game.getCiv(civID).unitsBest.get(i).unitID).get(Game.getCiv(civID).unitsBest.get(i).armyID).isSettler) {
                  bestUnits.add(new MercenariesManager.BestArmy(Game.getCiv(civID).unitsBest.get(i).unitID, Game.getCiv(civID).unitsBest.get(i).armyID));
               }
            }

            if (bestUnits.size() > 0) {
               for (int ix = 0; ix < bestUnits.size(); ix++) {
                  tempMercenary.add(new MercenariesManager.MercenaryArmy());
               }

               for (int ix = 0; ix < limit; ix++) {
                  random = Game.oR.nextInt(bestUnits.size());
                  tempMercenary.get(random).addArmy(bestUnits.get(random).iUnitID, bestUnits.get(random).iArmyID);
               }

               for (int ix = 0; ix < tempMercenary.size(); ix++) {
                  for (int j = 0; j < tempMercenary.get(ix).iUnitID.size(); j++) {
                     mercenaryArmy.addArmy(tempMercenary.get(ix).iUnitID.get(j), tempMercenary.get(ix).iArmyID.get(j));
                  }
               }
            }

            tempMercenary.clear();
            bestUnits.clear();

            for (int ix = 0; ix < Game.getCiv(civID).unitsBest.size(); ix++) {
               if (ArmyManager.lUnitsTypes.get(Game.getCiv(civID).unitsBest.get(ix).unitID).Line == 2
                  && !ArmyManager.lArmy.get(Game.getCiv(civID).unitsBest.get(ix).unitID).get(Game.getCiv(civID).unitsBest.get(ix).armyID).isSettler) {
                  bestUnits.add(new MercenariesManager.BestArmy(Game.getCiv(civID).unitsBest.get(ix).unitID, Game.getCiv(civID).unitsBest.get(ix).armyID));
               }
            }

            if (bestUnits.size() > 0) {
               for (int ixx = 0; ixx < bestUnits.size(); ixx++) {
                  tempMercenary.add(new MercenariesManager.MercenaryArmy());
               }

               for (int ixx = 0; ixx < limit; ixx++) {
                  random = Game.oR.nextInt(bestUnits.size());
                  tempMercenary.get(random).addArmy(bestUnits.get(random).iUnitID, bestUnits.get(random).iArmyID);
               }

               for (int ixx = 0; ixx < tempMercenary.size(); ixx++) {
                  for (int j = 0; j < tempMercenary.get(ixx).iUnitID.size(); j++) {
                     mercenaryArmy.addArmy(tempMercenary.get(ixx).iUnitID.get(j), tempMercenary.get(ixx).iArmyID.get(j));
                  }
               }
            }

            tempMercenary.clear();
            bestUnits.clear();
            if (mercenaryArmy.iUnitID.size() > 0) {
               mercenaryArmy.buildCost(civID);
               out.add(mercenaryArmy);
            }
         }
      }

      return out;
   }

   public static final boolean recruitMercenaries(int civID, int provinceID, MercenariesManager.MercenaryArmy mercenaryArmy) {
      if (Game.getCiv(civID).fGold < mercenaryArmy.iCost) {
         return false;
      } else {
         List<ArmyRegiment> nArmyRegiment = new ArrayList<>();
         int i = 0;

         for (int iSize = mercenaryArmy.iUnitID.size(); i < iSize; i++) {
            nArmyRegiment.add(new ArmyRegiment(mercenaryArmy.iUnitID.get(i), mercenaryArmy.iArmyID.get(i)));
         }

         ArmyDivision nArmyDivision = new ArmyDivision(civID, provinceID, nArmyRegiment);
         Game.getProvince(provinceID).addArmy(nArmyDivision);
         Civilization var10000 = Game.getCiv(civID);
         var10000.fGold = var10000.fGold - mercenaryArmy.iCost;
         Game.gameThread.addCivUpdateArmyMaintenance(civID);
         return true;
      }
   }

   public static class BestArmy {
      public int iUnitID;
      public int iArmyID;

      public BestArmy(int iUnitID, int iArmyID) {
         this.iUnitID = iUnitID;
         this.iArmyID = iArmyID;
      }
   }

   public static class MercenaryArmy {
      public List<Integer> iUnitID = new ArrayList<>();
      public List<Integer> iArmyID = new ArrayList<>();
      public int iCost;

      public void addArmy(int iUnitID, int iArmyID) {
         this.iUnitID.add(iUnitID);
         this.iArmyID.add(iArmyID);
      }

      public void buildCost(int civID) {
         for (int i = this.iUnitID.size() - 1; i >= 0; i--) {
            this.iCost = this.iCost
               + ArmyManager.getRecruitmentCost(civID, -1, this.iUnitID.get(i), this.iArmyID.get(i), ArmyManager.getRecruitmentCost_Regiments(civID) + i);
         }

         this.iCost = (int)(this.iCost * GameValues.army.MERCENARIES_COST_EXTRA_PER_REGIMENT);
      }
   }
}
