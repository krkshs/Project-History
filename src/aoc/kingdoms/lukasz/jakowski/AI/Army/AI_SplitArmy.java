package aoc.kingdoms.lukasz.jakowski.AI.Army;

import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.map.army.ArmyDivision;
import aoc.kingdoms.lukasz.map.army.ArmyManager;
import java.util.ArrayList;

public class AI_SplitArmy {
   public static String splitArmy(AI_Army_Composition newAmyComp, int provinceID, String armyKey) {
      ArmyDivision armyDivision = Game.getProvince(provinceID).getArmy(armyKey);
      if (armyDivision != null) {
         ArmyDivision newArmy = new ArmyDivision(armyDivision.civID, provinceID, new ArrayList<>());
         if (newAmyComp.numFirstLine > 0) {
            for (int i = armyDivision.iArmyRegimentSize - 1; i >= 0; i--) {
               if (ArmyManager.lUnitsTypes.get(armyDivision.lArmyRegiment.get(i).uID).Line == 0) {
                  newArmy.addRegiment(armyDivision.lArmyRegiment.get(i));
                  armyDivision.removeRegiment(i);
                  newAmyComp.numFirstLine--;
                  if (newAmyComp.numFirstLine <= 0) {
                     break;
                  }
               }
            }
         }

         if (newAmyComp.numFlank > 0) {
            for (int ix = armyDivision.iArmyRegimentSize - 1; ix >= 0; ix--) {
               if (ArmyManager.lUnitsTypes.get(armyDivision.lArmyRegiment.get(ix).uID).Line == 1) {
                  newArmy.addRegiment(armyDivision.lArmyRegiment.get(ix));
                  armyDivision.removeRegiment(ix);
                  newAmyComp.numFlank--;
                  if (newAmyComp.numFlank <= 0) {
                     break;
                  }
               }
            }
         }

         if (newAmyComp.numSupport > 0) {
            for (int ixx = armyDivision.iArmyRegimentSize - 1; ixx >= 0; ixx--) {
               if (ArmyManager.lUnitsTypes.get(armyDivision.lArmyRegiment.get(ixx).uID).Line == 2
                  && !ArmyManager.lArmy.get(armyDivision.lArmyRegiment.get(ixx).uID).get(armyDivision.lArmyRegiment.get(ixx).aID).SiegeUnit
                  && !ArmyManager.lArmy.get(armyDivision.lArmyRegiment.get(ixx).uID).get(armyDivision.lArmyRegiment.get(ixx).aID).isSettler) {
                  newArmy.addRegiment(armyDivision.lArmyRegiment.get(ixx));
                  armyDivision.removeRegiment(ixx);
                  newAmyComp.numSupport--;
                  if (newAmyComp.numSupport <= 0) {
                     break;
                  }
               }
            }
         }

         if (newAmyComp.numSiege > 0) {
            for (int ixxx = armyDivision.iArmyRegimentSize - 1; ixxx >= 0; ixxx--) {
               if (ArmyManager.lUnitsTypes.get(armyDivision.lArmyRegiment.get(ixxx).uID).Line == 2
                  && ArmyManager.lArmy.get(armyDivision.lArmyRegiment.get(ixxx).uID).get(armyDivision.lArmyRegiment.get(ixxx).aID).SiegeUnit
                  && !ArmyManager.lArmy.get(armyDivision.lArmyRegiment.get(ixxx).uID).get(armyDivision.lArmyRegiment.get(ixxx).aID).isSettler) {
                  newArmy.addRegiment(armyDivision.lArmyRegiment.get(ixxx));
                  armyDivision.removeRegiment(ixxx);
                  newAmyComp.numSiege--;
                  if (newAmyComp.numSiege <= 0) {
                     break;
                  }
               }
            }
         }

         if (newArmy.iArmyRegimentSize > 0) {
            int armyID = Game.getProvince(provinceID).getArmyKeyID(armyKey);
            if (armyID >= 0) {
               Game.getProvince(provinceID).updateRegiment(armyID, armyDivision.lArmyRegiment);
               return Game.getProvince(provinceID).addArmy(new ArmyDivision(armyDivision.civID, provinceID, newArmy.lArmyRegiment));
            }
         }
      }

      return null;
   }
}
