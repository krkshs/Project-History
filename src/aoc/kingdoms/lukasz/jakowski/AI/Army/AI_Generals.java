package aoc.kingdoms.lukasz.jakowski.AI.Army;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.AI.AI_Loan;
import aoc.kingdoms.lukasz.map.GeneralManager;
import aoc.kingdoms.lukasz.map.civilization.Civilization;
import java.util.ArrayList;
import java.util.List;

public class AI_Generals {
   public static void recruitGenerals(int civID) {
      try {
         if (civID != Game.player.iCivID) {
            Civilization civ = Game.getCiv(civID);
            if (civ.armiesWithoutGenerals.keysSize > 0) {
               try {
                  if (Game.getCiv(civID).getGeneralsNotAssignedSize() <= 0
                     && Game.getCiv(civID).diplomacy.isAtWar()
                     && civ.fGold < GameValues.generals.RECRUIT_GENERAL_GOLD_COST
                     && civ.fLegacy >= GameValues.generals.RECRUIT_GENERAL_LEGACY_COST) {
                     AI_Loan.takeLoan(civID);
                  }

                  if (civ.fGold >= GameValues.generals.RECRUIT_GENERAL_GOLD_COST && civ.fLegacy >= GameValues.generals.RECRUIT_GENERAL_LEGACY_COST) {
                     List<Game.ArmyPos> armyPosList = new ArrayList<>();
                     int i = 0;

                     for (int limit = 0; i < civ.armiesWithoutGenerals.keysSize && limit++ < 25; i++) {
                        Game.ArmyPos armyPos = Game.findArmy_FullCheck(civID, civ.armiesWithoutGenerals.keys.get(i));
                        if (armyPos == null) {
                           civ.armiesWithoutGenerals.removeArmyKey(civ.armiesWithoutGenerals.keys.get(i));
                           i--;
                        } else if (Game.getProvince(armyPos.iProvinceID).getArmy(armyPos.iID).armyGeneral == null) {
                           armyPosList.add(armyPos);
                        } else {
                           civ.armiesWithoutGenerals.removeArmyKey(civ.armiesWithoutGenerals.keys.get(i));
                           i--;
                        }
                     }

                     i = 0;

                     while (
                        civ.fGold >= GameValues.generals.RECRUIT_GENERAL_GOLD_COST
                           && !civ.armiesWithoutGenerals.keys.isEmpty()
                           && civ.fLegacy >= GameValues.generals.RECRUIT_GENERAL_LEGACY_COST
                           && armyPosList.size() > 0
                           && i++ < 50
                     ) {
                        int bestID = 0;

                        for (int ix = 1; ix < armyPosList.size(); ix++) {
                           if (Game.getProvince(armyPosList.get(bestID).iProvinceID).getArmy(armyPosList.get(bestID).iID).iArmyRegimentSize
                              < Game.getProvince(armyPosList.get(ix).iProvinceID).getArmy(armyPosList.get(ix).iID).iArmyRegimentSize) {
                              bestID = ix;
                           }
                        }

                        if (Game.getCiv(civID).getGeneralsNotAssignedSize() <= 0
                           || !Game.getCiv(civID).assignGeneralToArmy(armyPosList.get(bestID).iProvinceID, armyPosList.get(bestID).iID)) {
                           if (!GeneralManager.recruitGeneral_AI(civID, armyPosList.get(bestID).iProvinceID, armyPosList.get(bestID).iID)) {
                              return;
                           }

                           armyPosList.remove(bestID);
                        }
                     }
                  }
               } catch (Exception var6) {
                  CFG.exceptionStack(var6);
               }
            }
         }
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }
   }
}
