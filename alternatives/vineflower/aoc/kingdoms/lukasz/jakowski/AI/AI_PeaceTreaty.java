package aoc.kingdoms.lukasz.jakowski.AI;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Player.MessageTypes.PMessageTruce;
import aoc.kingdoms.lukasz.map.PeaceTreaty;
import aoc.kingdoms.lukasz.map.civilization.Civilization;
import aoc.kingdoms.lukasz.map.province.Province;
import aoc.kingdoms.lukasz.map.war.War;
import aoc.kingdoms.lukasz.map.war.WarManager;
import java.util.ArrayList;
import java.util.List;

public class AI_PeaceTreaty {
   public static void peaceTreaty(String warKey, int civID, int lostID) {
      if (civID != Game.player.iCivID) {
         PeaceTreaty peaceTreaty = new PeaceTreaty(civID, warKey, false);
         boolean conquerVassal = false;
         boolean isCoalition = false;

         try {
            War war = WarManager.lWars.get(warKey);
            conquerVassal = war.conquerVassal;
            isCoalition = war.isCoalition;
         } catch (Exception var15) {
            CFG.exceptionStack(var15);
         }

         try {
            List<Integer> provinceToTake = new ArrayList<>();
            Civilization civ = Game.getCiv(civID);
            Civilization civLost = Game.getCiv(lostID);

            for (int i = 0; i < civLost.getNumOfProvinces(); i++) {
               provinceToTake.add(civLost.getProvinceID(i));
               buildProvinceScore(civID, civLost.getProvinceID(i), lostID);
            }

            int[] order;
            order = Game.oR
                     .nextInt(
                        Game.ideologiesManager.getIdeology(civ.getIdeologyID()).AI_PEACE_ORDER_CHANCE
                           + Game.ideologiesManager.getIdeology(civ.getIdeologyID()).AI_PEACE_ORDER_CHANCE2
                     )
                  < Game.ideologiesManager.getIdeology(civ.getIdeologyID()).AI_PEACE_ORDER_CHANCE
               ? Game.ideologiesManager.getIdeology(civ.getIdeologyID()).AI_PEACE_ORDER
               : Game.ideologiesManager.getIdeology(civ.getIdeologyID()).AI_PEACE_ORDER2;
            label108:
            if (isCoalition) {
               order = GameValues.peace.AI_PEACE_DEMAND_COALITION;
            } else if (conquerVassal) {
               order = GameValues.peace.AI_PEACE_DEMAND_ANNEX_VASSAL_PEACE_ORDER;
            } else if (lostID == Game.player.iCivID
               && GameValues.peace.AI_PEACE_DEMAND_VASSALIZE_PLAYER
               && !Game.player.playerData.wasVassalized
               && civLost.getPuppetOfCivID() != civID
               && peaceTreaty.fScore >= peaceTreaty.getScore_DemandVassalization()) {
               Game.player.playerData.wasVassalized = true;
               order = GameValues.peace.AI_PEACE_DEMAND_VASSALIZE_PLAYER_PEACE_ORDER;
            }

            for (int a = 0; a < order.length; a++) {
               switch (order[a]) {
                  case 0:
                     while (peaceTreaty.fScore > 0.0F && provinceToTake.size() > 0) {
                        int bestID = 0;

                        for (int ix = provinceToTake.size() - 1; ix >= 0; ix--) {
                           if (Game.getProvince(provinceToTake.get(bestID)).aiPeaceScore < Game.getProvince(provinceToTake.get(ix)).aiPeaceScore) {
                              bestID = ix;
                           }
                        }

                        if (!peaceTreaty.addProvince(provinceToTake.get(bestID))) {
                           break;
                        }

                        Game.getProvince(provinceToTake.get(bestID)).aiPeaceCivID = civID;

                        for (int j = 0; j < Game.getProvince(provinceToTake.get(bestID)).getNeighboringProvincesSize(); j++) {
                           buildProvinceScore(civID, Game.getProvince(provinceToTake.get(bestID)).getNeighboringProvinces(j), lostID);
                        }

                        provinceToTake.remove(bestID);
                     }
                     break;
                  case 1:
                     if (peaceTreaty.fScore >= peaceTreaty.getScore_DemandVassalization()) {
                        peaceTreaty.demandVassalization();
                     }
                     break;
                  case 2:
                     if (civLost.diplomacy.iVassalsSize <= 0) {
                        break;
                     }

                     try {
                        List<Integer> possibleVassals = new ArrayList<>();

                        for (int i = 0; i < civLost.diplomacy.iVassalsSize; i++) {
                           possibleVassals.add(civLost.diplomacy.lVassals.get(i).c);
                        }

                        while (possibleVassals.size() > 0) {
                           int bestID = 0;
                           float bestDistance = Game.getDistanceFromProvinceToProvince(
                              civ.getCapitalProvinceID(), Game.getCiv(possibleVassals.get(bestID)).getCapitalProvinceID()
                           );

                           for (int i = possibleVassals.size() - 1; i > 0; i--) {
                              if (bestDistance
                                 > Game.getDistanceFromProvinceToProvince(
                                    civ.getCapitalProvinceID(), Game.getCiv(possibleVassals.get(i)).getCapitalProvinceID()
                                 )) {
                                 bestDistance = Game.getDistanceFromProvinceToProvince(
                                    civ.getCapitalProvinceID(), Game.getCiv(possibleVassals.get(i)).getCapitalProvinceID()
                                 );
                                 bestID = i;
                              }
                           }

                           peaceTreaty.demandSubjectTransfer(possibleVassals.get(bestID));
                           possibleVassals.remove(bestID);
                        }

                        possibleVassals.clear();
                     } catch (Exception var16) {
                        CFG.exceptionStack(var16);
                     }
                     break;
                  case 3:
                     if (civ.getReligionID() != civLost.getReligionID() && peaceTreaty.fScore >= peaceTreaty.getScore_DemandReligionConversion()) {
                        peaceTreaty.demandReligionConversion();
                     }
                     break;
                  case 4:
                     if (civ.getIdeologyID() != civLost.getIdeologyID() && peaceTreaty.fScore >= peaceTreaty.getScore_DemandGovernmentChange()) {
                        peaceTreaty.demandGovernmentChange();
                     }
                     break;
                  case 5:
                     int i = 0;

                     while (i < GameValues.peace.PEACE_GOLD_MAX && peaceTreaty.demandGold()) {
                        i++;
                     }
                     break label108;
                  case 6:
                     List<Integer> liberateCivs = PeaceTreaty.getCivsPossibleToLiberate(lostID);

                     for (int i = 0; i < liberateCivs.size(); i++) {
                        peaceTreaty.demandLiberateCiv(lostID, liberateCivs.get(i));
                     }
                     break label108;
                  case 7:
                     if (peaceTreaty.fScore >= peaceTreaty.getScore_DemandWarReparations()) {
                        peaceTreaty.demandWarReparations();
                     }
                     break;
                  case 8:
                     if (peaceTreaty.fScore >= peaceTreaty.getScore_DemandHumiliate()) {
                        peaceTreaty.demandHumiliate();
                     }
                     break;
                  case 9:
                     peaceTreaty.demandReturnProvinces = true;
               }
            }

            order = null;
         } catch (Exception var17) {
            CFG.exceptionStack(var17);
         }

         peaceTreaty.enforceDemands(false);
         if (lostID == Game.player.iCivID) {
            Game.player.addMessage(new PMessageTruce(civID, Game_Calendar.TURN_ID + GameValues.diplomacy.DIPLOMACY_MESSAGE_DAYS, peaceTreaty));
         }
      }
   }

   public static void buildProvinceScore(int civID, int provinceID, int lostID) {
      Province province = Game.getProvince(provinceID);
      province.aiPeaceScore = 0;
      int neighboringPerc = 0;
      float neighboringPerc_Total = 0.0F;

      for (int i = 0; i < province.getNeighboringProvincesSize(); i++) {
         if (Game.getProvince(province.getNeighboringProvinces(i)).getWasteland() < 0) {
            if (Game.getProvince(province.getNeighboringProvinces(i)).aiPeaceCivID == civID
               || Game.getProvince(province.getNeighboringProvinces(i)).aiPeaceCivID == Game.getProvince(province.getNeighboringProvinces(i)).getCivID()) {
               neighboringPerc_Total++;
            }

            if (Game.getProvince(province.getNeighboringProvinces(i)).aiPeaceCivID == lostID) {
               province.aiPeaceScore = (int)(province.aiPeaceScore + GameValues.peace.AI_PEACE_DEMAND_PROVINCE_NEIGHBORING_LOSER_SCORE);
            }

            if (Game.getProvince(province.getNeighboringProvinces(i)).aiPeaceCivID == civID) {
               province.aiPeaceScore = (int)(province.aiPeaceScore + GameValues.peace.AI_PEACE_DEMAND_PROVINCE_NEIGHBORING_WINNER_SCORE);
               neighboringPerc++;
            }
         }
      }

      if (neighboringPerc_Total > 0.0F) {
         province.aiPeaceScore = (int)(
            province.aiPeaceScore + GameValues.peace.AI_PEACE_DEMAND_PROVINCE_NEIGHBORING_WINNER_PERC * (neighboringPerc / neighboringPerc_Total)
         );
      }

      if (province.accessToMainSea) {
         province.aiPeaceScore = (int)(province.aiPeaceScore + GameValues.peace.AI_PEACE_DEMAND_PROVINCE_NEIGHBORING_SEA);
         boolean extraSeaPoints = false;

         for (int ix = 0; ix < province.getNeighboringProvincesSize(); ix++) {
            if (Game.getProvince(province.getNeighboringProvinces(ix)).getWasteland() < 0
               && Game.getProvince(province.getNeighboringProvinces(ix)).aiPeaceCivID != province.getCivID()) {
               extraSeaPoints = true;
               break;
            }
         }

         if (extraSeaPoints) {
            province.aiPeaceScore = (int)(province.aiPeaceScore + GameValues.peace.AI_PEACE_DEMAND_PROVINCE_NEIGHBORING_SEA_BEGIN);
         }
      }

      province.aiPeaceScore = province.aiPeaceScore
         + (int)(
            GameValues.peace.AI_PEACE_DEMAND_PROVINCE_DISTANCE
               * Math.max(0.0F, 1.0F - Game.getDistance_PercOfMax(Game.getCiv(civID).getCapitalProvinceID(), provinceID))
         );
   }

   public static int buildProvinceScore_Player(List<Integer> aiPeaceCivID, int civID, int provinceID) {
      int out = 0;
      int neighboringPerc = 0;
      float neighboringPerc_Total = 0.0F;

      for (int i = 0; i < Game.getProvince(provinceID).getNeighboringProvincesSize(); i++) {
         if (Game.getProvince(Game.getProvince(provinceID).getNeighboringProvinces(i)).getWasteland() < 0) {
            if (aiPeaceCivID.get(Game.getProvince(provinceID).getNeighboringProvinces(i)) == civID
               || aiPeaceCivID.get(Game.getProvince(provinceID).getNeighboringProvinces(i))
                  == Game.getProvince(Game.getProvince(provinceID).getNeighboringProvinces(i)).getCivID()) {
               neighboringPerc_Total++;
            }

            if (aiPeaceCivID.get(Game.getProvince(provinceID).getNeighboringProvinces(i)) == civID) {
               out = (int)(out + GameValues.peace.AI_PEACE_DEMAND_PROVINCE_NEIGHBORING_WINNER_SCORE);
               neighboringPerc++;
            }
         }
      }

      if (neighboringPerc_Total > 0.0F) {
         out = (int)(out + GameValues.peace.AI_PEACE_DEMAND_PROVINCE_NEIGHBORING_WINNER_PERC * (neighboringPerc / neighboringPerc_Total));
      }

      if (Game.getProvince(provinceID).accessToMainSea) {
         out = (int)(out + GameValues.peace.AI_PEACE_DEMAND_PROVINCE_NEIGHBORING_SEA);
      }

      return out
         + (int)(
            GameValues.peace.AI_PEACE_DEMAND_PROVINCE_DISTANCE
               * Math.max(0.0F, 1.0F - Game.getDistance_PercOfMax(Game.getCiv(civID).getCapitalProvinceID(), provinceID))
         );
   }
}
