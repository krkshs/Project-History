package aoc.kingdoms.lukasz.map;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.map.civilization.Civilization;
import aoc.kingdoms.lukasz.map.province.Province;
import aoc.kingdoms.lukasz.textures.Images;

public class ColonizationManager {
   public static boolean establishSettlement(int iCivID, int iProvinceID, int numOfSettlers) {
      try {
         if (Game.getProvince(iProvinceID).getSeaProvince()) {
            return false;
         } else if (iCivID == Game.player.iCivID
            && !GameValues.colonization.ALLOW_COLONIZATION_BY_SPENDING_GOLD
            && (
               !GameValues.colonization.ALLOW_COLONIZATION_BY_SPENDING_GOLD_PLAYER_TRIBAL
                  || !Game.ideologiesManager.getIdeology(Game.getCiv(Game.player.iCivID).getIdeologyID()).TRIBAL
            )
            && (
               !GameValues.colonization.AI_TRIBAL_CAN_COLONIZE_WITHOUT_LAWS
                  || Game_Calendar.TURN_ID < GameValues.colonization.AI_TRIBAL_CAN_COLONIZE_WITHOUT_LAWS_MIN_TURN_ID
            )
            && !Game.getCiv(iCivID).canColonize) {
            Game.menuManager.addToast_Error(Game.lang.get("Law") + ": " + Game.lang.get("NoColonization"), Images.law);
            Game.menuManager.addToast_Error(Game.lang.get("NoColonization.d"), Images.provinces);
            return false;
         } else {
            Province province = Game.getProvince(iProvinceID);
            if (province.getCivID() != 0) {
               return false;
            } else {
               numOfSettlers = Math.max(numOfSettlers, getNumberOfSettlers(iCivID, iProvinceID));
               if (numOfSettlers <= 0) {
                  return false;
               } else {
                  numOfSettlers = Math.min(
                     numOfSettlers, Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).REGIMENT_SIZE * GameValues.colonization.COLONIZATION_MAX_SETTLERS
                  );
                  province.setCivID(iCivID);
                  province.setReligion(Game.getCiv(iCivID).getReligionID());
                  province.addCore(iCivID);
                  province.clearPopulationData();
                  province.setPopulationOfCivID(iCivID, numOfSettlers);
                  Game.getProvinceData9(iProvinceID).setColonizationStartedTurnID(Game_Calendar.TURN_ID);
                  Game.getProvinceData9(iProvinceID)
                     .setColonizationGrowthRateExtra((int)(province.getGrowthRate() * GameValues.colonization.COLONIZATION_GROWTH_RATE_EXTRA));
                  Game.getCiv(iCivID).addColonizationProvince(iProvinceID);

                  for (int i = province.getArmySize() - 1; i >= 0; i--) {
                     if (province.getArmy(i).civID == iCivID) {
                        province.getArmy(i).removeAllSettlers();
                        if (province.getArmy(i).iArmyRegimentSize == 0) {
                           if (province.getArmy(i).armyGeneral != null) {
                              Game.getCiv(province.getArmy(i).civID).addGeneral(province.getArmy(i).armyGeneral);
                           }

                           province.removeArmy(i);
                        }
                     }
                  }

                  return true;
               }
            }
         }
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
         return false;
      }
   }

   public static final int getNumberOfSettlers(int iCivID, int iProvinceID) {
      int numOfSettlers = 0;
      Province province = Game.getProvince(iProvinceID);

      for (int i = province.getArmySize() - 1; i >= 0; i--) {
         if (province.getArmy(i).civID == iCivID) {
            numOfSettlers += province.getArmy(i).getNumberOfSettlers();
         }
      }

      return numOfSettlers;
   }

   public static final float getSettlementEstablishmentProgress(int iProvinceID) {
      return Math.min(
         1.0F, (float)(Game_Calendar.TURN_ID - Game.getProvinceData9(iProvinceID).getColonizationStartedTurnID()) / GameValues.colonization.COLONIZATION_TIME
      );
   }

   public static boolean establishSettlement_Gold(int iCivID, int iProvinceID) {
      Province province = Game.getProvince(iProvinceID);
      if (province.getCivID() != 0) {
         return false;
      } else {
         Civilization var10000 = Game.getCiv(iCivID);
         var10000.fGold = var10000.fGold - GameValues.colonization.ALLOW_COLONIZATION_BY_SPENDING_GOLD_COST;
         province.setCivID(iCivID);
         province.setReligion(Game.getCiv(iCivID).getReligionID());
         province.addCore(iCivID);
         province.clearPopulationData();
         province.setPopulationOfCivID(
            iCivID,
            GameValues.colonization.ALLOW_COLONIZATION_BY_SPENDING_GOLD_SETTLERS
               + Game.oR.nextInt(GameValues.colonization.ALLOW_COLONIZATION_BY_SPENDING_GOLD_SETTLERS_RANDOM)
         );
         Game.getProvinceData9(iProvinceID).setColonizationStartedTurnID(Game_Calendar.TURN_ID);
         Game.getProvinceData9(iProvinceID)
            .setColonizationGrowthRateExtra((int)(province.getGrowthRate() * GameValues.colonization.COLONIZATION_GROWTH_RATE_EXTRA));
         Game.getCiv(iCivID).addColonizationProvince(iProvinceID);
         return true;
      }
   }
}
