package aoc.kingdoms.lukasz.jakowski.AI.Army;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.map.army.ArmyDivision;
import aoc.kingdoms.lukasz.map.army.ArmyManager;
import aoc.kingdoms.lukasz.map.army.ArmyRegiment;
import aoc.kingdoms.lukasz.map.civilization.Civilization;
import java.util.ArrayList;
import java.util.List;

public class AI_DisbandArmies {
   public static void updateDisbandArmies() {
      int i;
      for (i = 1 + Game_Calendar.TURN_ID % GameValues.gameUpdateAI.GAME_UPDATE_AI_DISBAND_ARMIES;
         i < Game.player.iCivID;
         i += GameValues.gameUpdateAI.GAME_UPDATE_AI_DISBAND_ARMIES
      ) {
         if (Game.getCiv(i).getNumOfProvinces() > 0) {
            try {
               updateDisbandArmies(i);
            } catch (Exception var3) {
               CFG.exceptionStack(var3);
            }
         }
      }

      if (i == Game.player.iCivID) {
         i += GameValues.gameUpdateAI.GAME_UPDATE_AI_DISBAND_ARMIES;
      }

      for (; i < Game.getCivsSize(); i += GameValues.gameUpdateAI.GAME_UPDATE_AI_DISBAND_ARMIES) {
         if (Game.getCiv(i).getNumOfProvinces() > 0) {
            try {
               updateDisbandArmies(i);
            } catch (Exception var2) {
               CFG.exceptionStack(var2);
            }
         }
      }
   }

   public static void updateDisbandArmies(int civID) {
      Civilization civ = Game.getCiv(civID);
      if (!civ.diplomacy.isAtWar() && civ.aiCivDiplomacy.w.isEmpty()) {
         if (civ.fTotalIncomePerMonth * Game.aiValuesArmies.MILITARY_SPENDINGS_PERC_OF_MAX_INCOME_DISBAND < civ.fArmyMaintenance) {
            float maintenanceCostToDisband = civ.fArmyMaintenance
               - civ.fTotalIncomePerMonth * Game.aiValuesArmies.MILITARY_SPENDINGS_PERC_OF_MAX_INCOME_DISBAND;
            civ.aiCivCreateNewArmy.cNA.clear();

            try {
               civ.cancelRecruitArmy_All();
            } catch (Exception var10) {
               CFG.exceptionStack(var10);
            }

            for (int i = civ.iArmyPositionSize - 1; i >= 0; i--) {
               ArmyDivision armyDivision = Game.getProvince(civ.getArmyPosition(i)).getArmy(civ.getArmyPositionKey(i));
               if (armyDivision != null) {
                  List<ArmyRegiment> possibleToDisband = new ArrayList<>();
                  possibleToDisband.addAll(armyDivision.lArmyRegiment);
                  List<ArmyRegiment> toDisband = new ArrayList<>();

                  while (!possibleToDisband.isEmpty()) {
                     int disbandID = Game.oR.nextInt(possibleToDisband.size());
                     toDisband.add(possibleToDisband.get(disbandID));
                     maintenanceCostToDisband -= ArmyManager.lArmy.get(possibleToDisband.get(disbandID).uID).get(possibleToDisband.get(disbandID).aID).MaintenanceCost;
                     possibleToDisband.remove(disbandID);
                     if (maintenanceCostToDisband < 0.0F) {
                        break;
                     }
                  }

                  Game.getProvince(civ.getArmyPosition(i)).disbandRegiment(armyDivision.key, toDisband);
                  possibleToDisband.clear();
                  if (maintenanceCostToDisband < 0.0F) {
                     break;
                  }
               }
            }
         }

         if (civ.getBalance() < Game.aiValuesArmies.DISBAND_ARMIES_IF_BALANCE_BELOW) {
            float balance = Game.aiValuesArmies.DISBAND_ARMIES_IF_BALANCE_BELOW;
            if (civ.getMilitaryLevel() != 1) {
               civ.updateMilitaryLevel(1);
            }

            if (civ.getBalance() >= balance) {
               return;
            }

            civ.aiCivCreateNewArmy.cNA.clear();

            try {
               civ.cancelRecruitArmy_All();
            } catch (Exception var9) {
               CFG.exceptionStack(var9);
            }

            float currentBalance = civ.getBalance();

            for (int ix = civ.iArmyPositionSize - 1; ix >= 0; ix--) {
               ArmyDivision armyDivision = Game.getProvince(civ.getArmyPosition(ix)).getArmy(civ.getArmyPositionKey(ix));
               if (armyDivision != null) {
                  List<ArmyRegiment> possibleToDisband = new ArrayList<>();
                  possibleToDisband.addAll(armyDivision.lArmyRegiment);
                  List<ArmyRegiment> toDisband = new ArrayList<>();

                  while (!possibleToDisband.isEmpty()) {
                     int disbandID = Game.oR.nextInt(possibleToDisband.size());
                     toDisband.add(possibleToDisband.get(disbandID));
                     currentBalance += ArmyManager.lArmy.get(possibleToDisband.get(disbandID).uID).get(possibleToDisband.get(disbandID).aID).MaintenanceCost;
                     possibleToDisband.remove(disbandID);
                     if (currentBalance > balance) {
                        Game.getProvince(civ.getArmyPosition(ix)).disbandRegiment(armyDivision.key, toDisband);
                        possibleToDisband.clear();
                        return;
                     }
                  }

                  Game.getProvince(civ.getArmyPosition(ix)).disbandRegiment(armyDivision.key, toDisband);
                  possibleToDisband.clear();
                  if (currentBalance > balance) {
                     return;
                  }
               }
            }
         }
      }
   }
}
