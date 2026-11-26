package aoc.kingdoms.lukasz.jakowski.AI.Army;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Player.MessageTypes.PMessageDemandMilitaryAccess;
import aoc.kingdoms.lukasz.map.SiegeManager;
import aoc.kingdoms.lukasz.map.army.ArmyDivision;
import aoc.kingdoms.lukasz.map.civilization.Civilization;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import aoc.kingdoms.lukasz.map.moveUnits.other.MoveUnits_AI;
import aoc.kingdoms.lukasz.map.province.Province;
import aoc.kingdoms.lukasz.map.province.data.ProvinceData;
import aoc.kingdoms.lukasz.map.war.War;
import aoc.kingdoms.lukasz.map.war.WarCivilization;
import aoc.kingdoms.lukasz.map.war.WarManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AI_MoveAtWar {
   public static List<ArmyDivision> armies = new ArrayList<>();
   public static List<ArmyDivision> armiesInMove = new ArrayList<>();
   public static List<ArmyDivision> armiesSiege = new ArrayList<>();
   public static List<ArmyDivision> armiesLowMoraleManpower = new ArrayList<>();
   public static List<ArmyDivision> armiesToMerge = new ArrayList<>();
   public static List<AI_MoveAtWar.EnemyArmy> enemyArmies = new ArrayList<>();
   public static float warScore;
   public static List<Integer> possibleProvinces = new ArrayList<>();
   public static List<Integer> possibleProvincesDefend = new ArrayList<>();

   public static void moveAtWar(int civID) {
      try {
         armies.clear();
         armiesInMove.clear();
         armiesLowMoraleManpower.clear();
         armiesToMerge.clear();
         armiesSiege.clear();
         enemyArmies.clear();
         possibleProvinces.clear();
         possibleProvincesDefend.clear();
         warScore = 1000.0F;
         Civilization civ = Game.getCiv(civID);
         if (civ.aiUpdateID++ > 14) {
            civ.aiUpdateID = 0;
            AI_Generals.recruitGenerals(civID);
            processArmyPositions(civ);
         } else {
            processArmyPositions_Just(civ);
         }

         boolean buildEnemyArmy = percentageOfCivProvincesThatAreOccupied(civ)
            < Game.aiValuesArmies.AT_WAR_MOVE_ARMY_TO_ENEMY_ARMIES_IF_PERC_OF_PROVINCES_THAT_ARE_OCCUPIED;
         buildEnemyArmy = updateMinWarscore(civ, civID, buildEnemyArmy);
         if ((
               buildEnemyArmy
                  || warScore < Game.aiValuesArmies.AT_WAR_MOVE_ARMY_TO_ENEMY_ARMIES_IF_WARSCORE_BELOW
                  || warScore < Game.aiValuesArmies.AT_WAR_MOVE_ARMY_TO_ENEMY_ARMIES_AT_ANY_COST_IF_WARSCORE_BELOW
            )
            && (!armies.isEmpty() || !armiesInMove.isEmpty() || !armiesSiege.isEmpty())) {
            buildEnemyArmy(civ, civID);
            if (!enemyArmies.isEmpty()) {
               moveToEnemyArmies(civID);
            }
         }

         mergeArmies(civID);
         if (!armiesLowMoraleManpower.isEmpty()) {
            moveArmiesLowMoraleManpower(civID);
         }

         if (!armies.isEmpty()) {
            if (civ.getWarPlayDefensiveUntilTurnID() > Game_Calendar.TURN_ID) {
               determinePossibleProvinces_Defensive(civ, civID);
               if (!possibleProvinces.isEmpty()) {
                  moveArmiesToProvinces(civID);
               }
            } else {
               determinePossibleProvinces(civ, civID);
               if (!possibleProvinces.isEmpty()) {
                  moveArmiesToProvinces(civID);
               }
            }
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public static void moveToEnemyArmies(int civID) {
      moveToEnemyArmies_SortEnemyArmies(civID);
      List<ArmyDivision> civArmies = moveToEnemyArmies_GetOwnArmies();

      for (int i = 0; i < enemyArmies.size(); i++) {
         List<Float> civArmiesDistance = moveToEnemyArmies_MoveDistance(enemyArmies.get(i).provinceID, civArmies);
         List<ArmyDivision> moveArmies = new ArrayList<>();
         int moveArmies_Army = 0;

         while (!civArmies.isEmpty()) {
            int bestID = 0;

            for (int a = civArmiesDistance.size() - 1; a > 0; a--) {
               if (civArmiesDistance.get(bestID) > civArmiesDistance.get(a)) {
                  bestID = a;
               }
            }

            moveArmies.add(civArmies.get(bestID));
            moveArmies_Army += civArmies.get(bestID).iArmy;
            civArmies.remove(bestID);
            civArmiesDistance.remove(bestID);
            if (moveArmies_Army > enemyArmies.get(i).army) {
               for (int ax = 0; ax < moveArmies.size(); ax++) {
                  Game.getCiv(civID).newMove(moveArmies.get(ax).provinceID, enemyArmies.get(i).provinceID, moveArmies.get(ax).key, 0, false);
               }

               moveArmies.clear();
               break;
            }

            if (civArmies.isEmpty()) {
               if (warScore < Game.aiValuesArmies.AT_WAR_MOVE_ARMY_TO_ENEMY_ARMIES_AT_ANY_COST_IF_WARSCORE_BELOW) {
                  for (int ax = 0; ax < moveArmies.size(); ax++) {
                     Game.getCiv(civID).newMove(moveArmies.get(ax).provinceID, enemyArmies.get(i).provinceID, moveArmies.get(ax).key, 0, false);
                  }
               }
               break;
            }
         }
      }
   }

   public static List<Float> moveToEnemyArmies_MoveDistance(int provinceID, List<ArmyDivision> civArmies) {
      List<Float> out = new ArrayList<>();
      int i = 0;

      for (int iSize = civArmies.size(); i < iSize; i++) {
         out.add(Game.getManhattanDistance(provinceID, civArmies.get(i).provinceID));
      }

      return out;
   }

   public static void moveToEnemyArmies_SortEnemyArmies(int civID) {
      for (int i = enemyArmies.size() - 1; i >= 0; i--) {
         enemyArmies.get(i).distance = Game.getManhattanDistance(Game.getCiv(civID).getCapitalProvinceID(), enemyArmies.get(i).provinceID);
      }

      Collections.sort(enemyArmies, new Comparator<AI_MoveAtWar.EnemyArmy>() {
         public int compare(AI_MoveAtWar.EnemyArmy army1, AI_MoveAtWar.EnemyArmy army2) {
            return Float.compare(army1.distance, army2.distance);
         }
      });
   }

   public static List<ArmyDivision> moveToEnemyArmies_GetOwnArmies() {
      List<ArmyDivision> civArmies = new ArrayList<>();

      for (int i = armies.size() - 1; i >= 0; i--) {
         civArmies.add(armies.get(i));
      }

      for (int i = armiesInMove.size() - 1; i >= 0; i--) {
         civArmies.add(armiesInMove.get(i));
      }

      for (int i = armiesSiege.size() - 1; i >= 0; i--) {
         civArmies.add(armiesSiege.get(i));
      }

      return civArmies;
   }

   public static boolean updateMinWarscore(Civilization civ, int civID, boolean buildEnemyArmy) {
      try {
         for (int a = 0; a < civ.diplomacy.iWarsSize; a++) {
            War war = WarManager.lWars.get(civ.diplomacy.lWars.get(a));
            if (war != null) {
               warScore = Math.min(warScore, (float)(-((int)war.warScore) * war.getWarScore_Side(civID)));
               if (!buildEnemyArmy) {
                  if (war.isAggressor(civID)) {
                     if (Game.getCiv(war.lAggressors.get(0).iCivID).iRegiments > Game.getCiv(war.lDefenders.get(0).iCivID).iRegiments) {
                        buildEnemyArmy = true;
                     }
                  } else if (Game.getCiv(war.lDefenders.get(0).iCivID).iRegiments > Game.getCiv(war.lAggressors.get(0).iCivID).iRegiments) {
                     buildEnemyArmy = true;
                  }
               }
            }
         }
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
      }

      return buildEnemyArmy;
   }

   public static void processArmyPositions(Civilization civ) {
      for (int i = 0; i < civ.iArmyPositionSize; i++) {
         ArmyDivision armyDivision = Game.getProvince(civ.getArmyPosition(i)).getArmy(civ.getArmyPositionKey(i));
         if (armyDivision != null && !armyDivision.inBattle) {
            if (Game.getProvinceData4(armyDivision.provinceID).isUnderSiege()) {
               if (SiegeManager.isProvinceUnderSiege(armyDivision.provinceID)) {
                  armiesSiege.add(armyDivision);
               } else {
                  Game.getProvinceData4(armyDivision.provinceID).setIsUnderSiege_Just(false);
               }
            } else if (armyDivision.iArmyRegimentSize < Game.aiValuesArmies.AT_WAR_WAIT_IF_IN_RECRUITMENT_AND_REGIMENTS_BELOW
               && civ.getCivID() != Game.player.iCivID
               && civ.isRecruitArmyInProgress(armyDivision.key)) {
               armiesLowMoraleManpower.add(armyDivision);
            } else if (!armyDivision.inMovement && !armyDivision.inRetreat) {
               if (armyDivision.iArmyRegimentSize > 0) {
                  if (shouldMergeArmy(civ, armyDivision)) {
                     armiesToMerge.add(armyDivision);
                  } else if (armyDivision.fMorale < Game.aiValuesArmies.AT_WAR_DEFEND_IF_ARMY_MORALE_BELOW) {
                     armiesLowMoraleManpower.add(armyDivision);
                  } else if (civ.fManpower > Game.aiValuesArmies.AT_WAR_DEFEND_IF_CIV_MANPOWER_OVER
                     && (float)armyDivision.iArmy / (armyDivision.iArmyRegimentSize * Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).REGIMENT_SIZE)
                        < Game.aiValuesArmies.AT_WAR_DEFEND_IF_ARMY_MANPOWER_BELOW) {
                     armiesLowMoraleManpower.add(armyDivision);
                  } else if (armyDivision.iArmyRegimentSize < Game.aiValuesArmies.AT_WAR_DEFEND_IF_NUM_OF_REGIMENTS_BELOW) {
                     armiesLowMoraleManpower.add(armyDivision);
                  } else {
                     armies.add(armyDivision);
                  }
               }
            } else {
               if (!armyDivision.inRetreat) {
                  armiesInMove.add(armyDivision);
               }

               if (!civ.isInMoveUnits_ArmyKey(armyDivision.key)) {
                  armyDivision.inMovement = false;
                  armyDivision.inRetreat = false;
               }
            }
         }
      }
   }

   public static void processArmyPositions_Just(Civilization civ) {
      for (int i = 0; i < civ.iArmyPositionSize; i++) {
         ArmyDivision armyDivision = Game.getProvince(civ.getArmyPosition(i)).getArmy(civ.getArmyPositionKey(i));
         if (armyDivision != null && !armyDivision.inBattle) {
            if (Game.getProvinceData4(armyDivision.provinceID).isUnderSiege()) {
               if (SiegeManager.isProvinceUnderSiege(armyDivision.provinceID)) {
                  armiesSiege.add(armyDivision);
               } else {
                  Game.getProvinceData4(armyDivision.provinceID).setIsUnderSiege_Just(false);
               }
            } else if (armyDivision.iArmyRegimentSize < Game.aiValuesArmies.AT_WAR_WAIT_IF_IN_RECRUITMENT_AND_REGIMENTS_BELOW
               && civ.getCivID() != Game.player.iCivID
               && civ.isRecruitArmyInProgress(armyDivision.key)) {
               armiesLowMoraleManpower.add(armyDivision);
            } else if (!armyDivision.inMovement && !armyDivision.inRetreat) {
               if (armyDivision.iArmyRegimentSize > 0) {
                  if (shouldMergeArmy(civ, armyDivision)) {
                     if (!civ.aiMerge.isArmyMerging(civ.getCivID(), civ.getArmyPositionKey(i))) {
                        armiesToMerge.add(armyDivision);
                     }
                  } else if (armyDivision.fMorale < Game.aiValuesArmies.AT_WAR_DEFEND_IF_ARMY_MORALE_BELOW) {
                     armiesLowMoraleManpower.add(armyDivision);
                  } else if (civ.fManpower > Game.aiValuesArmies.AT_WAR_DEFEND_IF_CIV_MANPOWER_OVER
                     && (float)armyDivision.iArmy / (armyDivision.iArmyRegimentSize * Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).REGIMENT_SIZE)
                        < Game.aiValuesArmies.AT_WAR_DEFEND_IF_ARMY_MANPOWER_BELOW) {
                     armiesLowMoraleManpower.add(armyDivision);
                  } else if (armyDivision.iArmyRegimentSize < Game.aiValuesArmies.AT_WAR_DEFEND_IF_NUM_OF_REGIMENTS_BELOW) {
                     armiesLowMoraleManpower.add(armyDivision);
                  } else {
                     armies.add(armyDivision);
                  }
               }
            } else if (!armyDivision.inRetreat) {
               armiesInMove.add(armyDivision);
            }
         }
      }
   }

   public static float percentageOfCivProvincesThatAreOccupied(Civilization civ) {
      float out = 0.0F;

      for (int i = 0; i < civ.getNumOfProvinces(); i++) {
         Province province = Game.getProvince(civ.getProvinceID(i));
         if (province.isOccupied()) {
            out++;
         }
      }

      return out / civ.getNumOfProvinces();
   }

   public static void buildEnemyArmy(Civilization civ, int civID) {
      List<Integer> addedCivs = new ArrayList<>();

      try {
         for (int a = 0; a < civ.diplomacy.iWarsSize; a++) {
            War war = WarManager.lWars.get(civ.diplomacy.lWars.get(a));
            if (war != null) {
               List<WarCivilization> warSides = war.isAggressor(civID) ? war.lAggressors : war.lDefenders;
               List<WarCivilization> warSides2 = war.isAggressor(civID) ? war.lDefenders : war.lAggressors;
               if ((
                     Game.getCiv(warSides.get(0).iCivID).iRegiments > Game.getCiv(warSides2.get(0).iCivID).iRegiments
                        || -((int)war.warScore) * war.getWarScore_Side(civID)
                           < Game.aiValuesArmies.AT_WAR_MOVE_ARMY_TO_ENEMY_ARMIES_AT_ANY_COST_IF_WARSCORE_BELOW
                  )
                  && !addedCivs.contains(warSides.get(0).iCivID)) {
                  addedCivs.add(warSides.get(0).iCivID);
                  int j = 0;

                  for (int warSideCivID = warSides.get(0).iCivID; j < Game.getCiv(warSideCivID).getNumOfProvinces(); j++) {
                     Province province = Game.getProvince(Game.getCiv(warSideCivID).getProvinceID(j));
                     if (province.getArmySize() > 0) {
                        for (int k = 0; k < province.getArmySize(); k++) {
                           if (!province.getArmy(k).inRetreat
                              && civID != province.getArmy(k).civID
                              && DiplomacyManager.isAtWar(civID, province.getArmy(k).civID)) {
                              boolean addArmy = true;

                              for (int z = enemyArmies.size() - 1; z >= 0; z--) {
                                 if (enemyArmies.get(z).provinceID == province.getProvinceID()) {
                                    AI_MoveAtWar.EnemyArmy var10000 = enemyArmies.get(z);
                                    var10000.army = var10000.army + province.getArmy(k).iArmy;
                                    addArmy = false;
                                    break;
                                 }
                              }

                              if (addArmy) {
                                 enemyArmies.add(new AI_MoveAtWar.EnemyArmy(province.getProvinceID(), province.getArmy(k).iArmy));
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      } catch (Exception var13) {
         CFG.exceptionStack(var13);
      }

      if (!addedCivs.contains(civID)) {
         for (int i = 0; i < civ.getNumOfProvinces(); i++) {
            Province province = Game.getProvince(civ.getProvinceID(i));
            if (province.getArmySize() > 0) {
               for (int j = 0; j < province.getArmySize(); j++) {
                  if (!province.getArmy(j).inRetreat && civID != province.getArmy(j).civID && DiplomacyManager.isAtWar(civID, province.getArmy(j).civID)) {
                     boolean addArmy = true;

                     for (int zx = enemyArmies.size() - 1; zx >= 0; zx--) {
                        if (enemyArmies.get(zx).provinceID == province.getProvinceID()) {
                           AI_MoveAtWar.EnemyArmy var19 = enemyArmies.get(zx);
                           var19.army = var19.army + province.getArmy(j).iArmy;
                           addArmy = false;
                           break;
                        }
                     }

                     if (addArmy) {
                        enemyArmies.add(new AI_MoveAtWar.EnemyArmy(province.getProvinceID(), province.getArmy(j).iArmy));
                     }
                  }
               }
            }
         }
      }

      addedCivs.clear();
   }

   public static boolean shouldMergeArmy(Civilization civ, ArmyDivision armyDivision) {
      return civ.getArmyRegimentSize() >= Game.aiValuesArmies.AT_WAR_MERGE_ALL_ARMIES_WITH_REGIMENTS_BELOW * 2
         && armyDivision.iArmyRegimentSize < Game.aiValuesArmies.AT_WAR_MERGE_ALL_ARMIES_WITH_REGIMENTS_BELOW;
   }

   public static void mergeArmies(int civID) {
      for (int i = armiesToMerge.size() - 1; i >= 0; i--) {
         mergeArmy(civID, armiesToMerge.get(i).provinceID, armiesToMerge.get(i).key);
         armiesToMerge.remove(i);
      }
   }

   public static void determinePossibleProvinces(Civilization civ, int civID) {
      addOccupiedProvinces(civ);
      addAlliedProvincesFromWars(civ, civID);
   }

   public static void determinePossibleProvinces_Defensive(Civilization civ, int civID) {
      addOccupiedProvinces(civ);
      addAlliedProvincesFromWars_Defensive(civ, civID);
   }

   public static void addOccupiedProvinces(Civilization civ) {
      for (int i = 0; i < civ.getNumOfProvinces(); i++) {
         Province province = Game.getProvince(civ.getProvinceID(i));
         if (province.isOccupied()) {
            possibleProvinces.add(civ.getProvinceID(i));
            if (Game.getProvinceData(civ.getProvinceID(i)).getOccupiedByCivID() < 0) {
               province.aiMoveArmyAtWarScore = Game.aiValuesArmies.AT_WAR_MOVE_PROVINCE_SCORE_OCCUPIED_BY_REBELS;
            } else {
               province.aiMoveArmyAtWarScore = Game.aiValuesArmies.AT_WAR_MOVE_PROVINCE_SCORE_OCCUPIED_BY_ENEMY_CIV;
            }
         } else if (!Game.getProvinceData4(civ.getProvinceID(i)).isUnderSiege()) {
            possibleProvincesDefend.add(civ.getProvinceID(i));
            province.aiMoveArmyAtWarScore = Game.aiValuesArmies.AT_WAR_MOVE_PROVINCE_SCORE_DEFAULT;
         } else {
            boolean byRebels = false;

            for (int a = 0; a < Game.getProvince(civ.getProvinceID(i)).getArmySize(); a++) {
               if (Game.getProvince(civ.getProvinceID(i)).getArmy(a).civID < 0) {
                  byRebels = true;
                  break;
               }
            }

            possibleProvinces.add(civ.getProvinceID(i));
            if (byRebels) {
               province.aiMoveArmyAtWarScore = Game.aiValuesArmies.AT_WAR_MOVE_PROVINCE_SCORE_UNDER_SIEGE_BY_REBELS;
            } else {
               province.aiMoveArmyAtWarScore = Game.aiValuesArmies.AT_WAR_MOVE_PROVINCE_SCORE_UNDER_SIEGE_BY_ENEMY_CIV;
            }
         }
      }
   }

   public static void addAlliedProvincesFromWars(Civilization civ, int civID) {
      for (int i = 0; i < civ.diplomacy.iWarsSize; i++) {
         War war = WarManager.lWars.get(civ.diplomacy.lWars.get(i));
         if (war != null) {
            float warLeaderScore = war.isWarLeader(civID)
               ? Game.aiValuesArmies.AT_WAR_MOVE_PROVINCE_ALLIES_WAR_LEADER
               : Game.aiValuesArmies.AT_WAR_MOVE_PROVINCE_ALLIES_NOT_WAR_LEADER;
            List<WarCivilization> warSides = war.isAggressor(civID) ? war.lAggressors : war.lDefenders;
            List<WarCivilization> warSides2 = war.isAggressor(civID) ? war.lDefenders : war.lAggressors;

            for (WarCivilization side : warSides) {
               if (civID != side.iCivID) {
                  int b = 0;

                  for (int bSize = Game.getCiv(side.iCivID).occupiedProvinces.size(); b < bSize; b++) {
                     int occupiedProvinceID = Game.getCiv(side.iCivID).occupiedProvinces.get(b);
                     ProvinceData provinceData = Game.getProvinceData(occupiedProvinceID);
                     if (provinceData.getOccupiedByCivID() < 0) {
                        possibleProvinces.add(occupiedProvinceID);
                        Game.getProvince(occupiedProvinceID).aiMoveArmyAtWarScore = Game.aiValuesArmies.AT_WAR_MOVE_PROVINCE_ALLIES_OCCUPIED_BY_REBELS;
                     } else if (DiplomacyManager.isAtWar(civID, provinceData.getOccupiedByCivID())) {
                        possibleProvinces.add(occupiedProvinceID);
                        Game.getProvince(occupiedProvinceID).aiMoveArmyAtWarScore = Game.aiValuesArmies.AT_WAR_MOVE_PROVINCE_ALLIES_OCCUPIED_BY_ENEMY_CIV;
                     }
                  }

                  b = 0;

                  for (int bSizex = Game.getCiv(side.iCivID).underSiege.size(); b < bSizex; b++) {
                     int underSiegeProvinceID = Game.getCiv(side.iCivID).underSiege.get(b);

                     for (int c = 0; c < Game.getProvince(underSiegeProvinceID).getArmySize(); c++) {
                        if (DiplomacyManager.isAtWar(civID, Game.getProvince(underSiegeProvinceID).getArmy(c).civID)) {
                           possibleProvinces.add(underSiegeProvinceID);
                           Game.getProvince(underSiegeProvinceID).aiMoveArmyAtWarScore = Game.aiValuesArmies.AT_WAR_MOVE_PROVINCE_ALLIES_UNDER_SIEGE;
                           break;
                        }
                     }
                  }
               }
            }

            int j = 0;

            for (int warSideCivID = warSides2.get(0).iCivID; j < Game.getCiv(warSideCivID).getNumOfProvinces(); j++) {
               int provinceID = Game.getCiv(warSideCivID).getProvinceID(j);
               if (Game.getProvince(provinceID).isOccupied()) {
                  if (Game.getProvinceData4(provinceID).isUnderSiege()) {
                     possibleProvinces.add(provinceID);
                     Game.getProvince(provinceID).aiMoveArmyAtWarScore = Game.aiValuesArmies.AT_WAR_MOVE_PROVINCE_ENEMY_WAR_LEADER_OCCUPIED_UNDER_SIEGE;
                  } else {
                     possibleProvincesDefend.add(provinceID);
                     Game.getProvince(provinceID).aiMoveArmyAtWarScore = Game.aiValuesArmies.AT_WAR_MOVE_PROVINCE_ENEMY_WAR_LEADER_OCCUPIED;
                  }
               } else {
                  possibleProvinces.add(provinceID);
                  Game.getProvince(provinceID).aiMoveArmyAtWarScore = warLeaderScore;
               }
            }

            for (int a = warSides2.size() - 1; a > 0; a--) {
               int warSideCivIDx = warSides2.get(a).iCivID;

               for (int jx = 0; jx < Game.getCiv(warSideCivIDx).getNumOfProvinces(); jx++) {
                  if (!Game.getProvince(Game.getCiv(warSideCivIDx).getProvinceID(jx)).isOccupied()) {
                     possibleProvinces.add(Game.getCiv(warSideCivIDx).getProvinceID(jx));
                     Game.getProvince(Game.getCiv(warSideCivIDx).getProvinceID(jx)).aiMoveArmyAtWarScore = Game.aiValuesArmies.AT_WAR_MOVE_ENEMY_ALLIES_PROVINCES;
                  }
               }
            }
         }
      }
   }

   public static void addAlliedProvincesFromWars_Defensive(Civilization civ, int civID) {
      for (int i = 0; i < civ.diplomacy.iWarsSize; i++) {
         War war = WarManager.lWars.get(civ.diplomacy.lWars.get(i));
         if (war != null) {
            for (WarCivilization side : war.isAggressor(civID) ? war.lAggressors : war.lDefenders) {
               if (civID != side.iCivID) {
                  int b = 0;

                  for (int bSize = Game.getCiv(side.iCivID).occupiedProvinces.size(); b < bSize; b++) {
                     int occupiedProvinceID = Game.getCiv(side.iCivID).occupiedProvinces.get(b);
                     ProvinceData provinceData = Game.getProvinceData(occupiedProvinceID);
                     if (provinceData.getOccupiedByCivID() < 0) {
                        possibleProvinces.add(occupiedProvinceID);
                        Game.getProvince(occupiedProvinceID).aiMoveArmyAtWarScore = Game.aiValuesArmies.AT_WAR_MOVE_PROVINCE_DEFEND_OCCUPIED_BY_REBELS;
                     } else if (DiplomacyManager.isAtWar(civID, provinceData.getOccupiedByCivID())) {
                        possibleProvinces.add(occupiedProvinceID);
                        Game.getProvince(occupiedProvinceID).aiMoveArmyAtWarScore = Game.aiValuesArmies.AT_WAR_MOVE_PROVINCE_DEFEND_OCCUPIED_BY_ENEMY_CIV;
                     }
                  }

                  b = 0;

                  for (int bSizex = Game.getCiv(side.iCivID).underSiege.size(); b < bSizex; b++) {
                     int underSiegeProvinceID = Game.getCiv(side.iCivID).underSiege.get(b);

                     for (int c = 0; c < Game.getProvince(underSiegeProvinceID).getArmySize(); c++) {
                        if (DiplomacyManager.isAtWar(civID, Game.getProvince(underSiegeProvinceID).getArmy(c).civID)) {
                           possibleProvinces.add(underSiegeProvinceID);
                           Game.getProvince(underSiegeProvinceID).aiMoveArmyAtWarScore = Game.aiValuesArmies.AT_WAR_MOVE_PROVINCE_DEFEND_ENEMY_ARMY_IN_PROVINCE;
                           break;
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public static void calculateProvinceScores() {
      for (int provinceID : possibleProvinces) {
         Province province = Game.getProvince(provinceID);
         province.aiMoveArmyAtWarScore = province.aiMoveArmyAtWarScore * province.fProvinceValue;
      }
   }

   public static void moveArmiesToProvinces(int civID) {
      label107:
      for (ArmyDivision army : armies) {
         int toProvinceID = selectBestProvince(civID, selectBestProvince(army.provinceID));
         if (army.provinceID == toProvinceID) {
            SiegeManager.checkForSiege(toProvinceID);
         } else if (Game.getCiv(civID).noConnectionMoveUnits.contains(army.provinceID, toProvinceID)) {
            moveArmiesToProvinces_Defend(civID, army);
         } else if (Game.getCiv(civID).newMove(army.provinceID, toProvinceID, army.key, 0, false)) {
            Game.getProvince(toProvinceID).aiMoveArmyAtWarScore = Game.getProvince(toProvinceID).aiMoveArmyAtWarScore
               * Game.aiValuesArmies.AT_WAR_AFTER_MOVE_SCORE;
         } else {
            MoveUnits_AI moveUnitsAI = new MoveUnits_AI(civID, army.provinceID, toProvinceID);
            if (moveUnitsAI.iRouteSize > 0) {
               List<Integer> accessCivs = new ArrayList<>();

               for (int i = 0; i < moveUnitsAI.iRouteSize; i++) {
                  if (!accessCivs.contains(Game.getProvince(moveUnitsAI.lRoute.get(i)).getCivID())) {
                     accessCivs.add(Game.getProvince(moveUnitsAI.lRoute.get(i)).getCivID());
                  }
               }

               boolean isPlayerInList = false;

               for (int ix = accessCivs.size() - 1; ix >= 0; ix--) {
                  if (accessCivs.get(ix) == 0
                     || accessCivs.get(ix) == civID
                     || DiplomacyManager.isAtWar(civID, accessCivs.get(ix))
                     || DiplomacyManager.isAlly(civID, accessCivs.get(ix))) {
                     accessCivs.remove(ix);
                  } else if (accessCivs.get(ix) == Game.player.iCivID) {
                     accessCivs.remove(ix);
                     isPlayerInList = true;
                  }
               }

               if (isPlayerInList) {
                  Game.player.addMessage(new PMessageDemandMilitaryAccess(civID, Game_Calendar.TURN_ID + GameValues.diplomacy.DIPLOMACY_MESSAGE_DAYS));
                  Civilization var10000 = Game.getCiv(civID);
                  var10000.fDiplomacy = var10000.fDiplomacy
                     - GameValues.diplomacy.DIPLOMACY_DEMAND_MILITARY_ACCESS_COST * Game.aiValuesDiplomacy.AI_DEMAND_MILITARY_ACCESS_COST_MODIFIER;
               }

               for (int ixx = accessCivs.size() - 1; ixx >= 0; ixx--) {
                  if (DiplomacyManager.addMilitaryAccess(civID, accessCivs.get(ixx))) {
                     Civilization var13 = Game.getCiv(civID);
                     var13.fDiplomacy = var13.fDiplomacy
                        - GameValues.diplomacy.DIPLOMACY_DEMAND_MILITARY_ACCESS_COST * Game.aiValuesDiplomacy.AI_DEMAND_MILITARY_ACCESS_COST_MODIFIER;
                  }
               }

               accessCivs.clear();
            }

            Game.getCiv(civID).noConnectionMoveUnits.addProvince(army.provinceID, toProvinceID);
            boolean defend = true;

            for (int a = Math.min(Game.aiValuesArmies.AT_WAR_MOVE_ARMY_CANT_REACH_RANDOM_PROVINCES_LIMIT, possibleProvinces.size()) - 1; a >= 0; a--) {
               toProvinceID = selectBestProvince(civID, selectBestProvince_Random(army.provinceID));
               if (army.provinceID == toProvinceID) {
                  SiegeManager.checkForSiege(toProvinceID);
               } else if (!Game.getCiv(civID).noConnectionMoveUnits.contains(army.provinceID, toProvinceID)
                  && Game.getCiv(civID).newMove(army.provinceID, toProvinceID, army.key, 0, false)) {
                  Game.getProvince(toProvinceID).aiMoveArmyAtWarScore = Game.getProvince(toProvinceID).aiMoveArmyAtWarScore
                     * Game.aiValuesArmies.AT_WAR_AFTER_MOVE_SCORE;
                  defend = false;
                  if (defend) {
                     moveArmiesToProvinces_Defend(civID, army);
                  }
                  continue label107;
               }
            }
            break;
         }
      }
   }

   public static void moveArmiesToProvinces_Defend(int civID, ArmyDivision army) {
      if (!possibleProvincesDefend.isEmpty() && Game.oR.nextInt(100) < Game.aiValuesArmies.AT_WAR_NO_CONNECTIONS_DEFEND_CHANCE) {
         int toProvinceID = selectBestProvince_Defend(army.provinceID);
         if (army.provinceID == toProvinceID) {
            SiegeManager.checkForSiege(toProvinceID);
         }

         if (Game.getCiv(civID).newMove(army.provinceID, toProvinceID, army.key, 0, false)) {
            Game.getProvince(toProvinceID).aiMoveArmyAtWarScore = Game.getProvince(toProvinceID).aiMoveArmyAtWarScore
               * Game.aiValuesArmies.AT_WAR_AFTER_MOVE_DEFEND_SCORE;
         }
      }
   }

   public static int selectBestProvince(int civID, int provinceID) {
      Province province = Game.getProvince(provinceID);
      ProvinceData provinceData = Game.getProvinceData(provinceID);
      if (province.getFortLevel() <= 0 && !Game.getProvinceData4(provinceID).isUnderSiege()) {
         int scoreProvinceID = 0;
         int bestProvinceID = provinceID;
         int bestScore = 0;
         boolean isOccupied = province.isOccupied();
         int currentCivID = provinceData.getOccupiedByCivID();

         for (int i = 0; i < province.getNeighboringProvincesSize(); i++) {
            int neighborID = province.getNeighboringProvinces(i);
            Province neighProvince = Game.getProvince(neighborID);
            ProvinceData neighProvinceData = Game.getProvinceData(neighborID);
            boolean isValidNeighbor = isOccupied
               ? neighProvinceData.getOccupiedByCivID() == currentCivID
               : !neighProvince.isOccupied() && neighProvince.getCivID() == province.getCivID();
            if (isValidNeighbor) {
               scoreProvinceID++;
               int nScore = 0;

               for (int j = 0; j < neighProvince.getNeighboringProvincesSize(); j++) {
                  int neighOfNeighID = neighProvince.getNeighboringProvinces(j);
                  Province neighOfNeighProvince = Game.getProvince(neighOfNeighID);
                  boolean isValidNeighOfNeigh = isOccupied
                     ? Game.getProvinceData(neighOfNeighID).getOccupiedByCivID() == currentCivID
                     : !neighOfNeighProvince.isOccupied() && neighOfNeighProvince.getCivID() == province.getCivID();
                  if (isValidNeighOfNeigh) {
                     nScore++;
                  }
               }

               if (nScore > bestScore) {
                  bestScore = nScore;
                  bestProvinceID = neighborID;
               }
            }
         }

         return scoreProvinceID >= bestScore ? provinceID : bestProvinceID;
      } else {
         return provinceID;
      }
   }

   public static int selectBestProvince(int fromProvinceID) {
      for (int i = possibleProvinces.size() - 1; i >= 0; i--) {
         Game.getProvince(possibleProvinces.get(i)).aiMoveArmyAtWarScore_DistanceFromArmy = Game.getProvince(possibleProvinces.get(i)).aiMoveArmyAtWarScore
            * (1.0F - Game.getManhattanDistance_PercOfMax(fromProvinceID, possibleProvinces.get(i)));
      }

      Game.getProvince(fromProvinceID).aiMoveArmyAtWarScore_DistanceFromArmy = -999999.0F;
      if (Game.oR.nextInt(100) < Game.aiValuesArmies.AT_WAR_MOVE_ARMY_CHOOSE_BEST_PROVINCE_CHANCE) {
         int bestID = 0;

         for (int i = possibleProvinces.size() - 1; i > 0; i--) {
            if (Game.getProvince(possibleProvinces.get(i)).aiMoveArmyAtWarScore_DistanceFromArmy
               > Game.getProvince(possibleProvinces.get(bestID)).aiMoveArmyAtWarScore_DistanceFromArmy) {
               bestID = i;
            }
         }

         return possibleProvinces.get(bestID);
      } else {
         List<Integer> bestToChooseFrom = new ArrayList<>();
         int b = 0;

         for (int bLimit = Math.min(
               possibleProvinces.size(), Game.aiValuesArmies.AT_WAR_MOVE_ARMY_BEST_PROVINCES_LIMIT + Game.getProvince(fromProvinceID).getArmySize()
            );
            b < bLimit;
            b++
         ) {
            int bestID = 0;

            for (int ix = possibleProvinces.size() - 1; ix > 0; ix--) {
               if (Game.getProvince(possibleProvinces.get(ix)).aiMoveArmyAtWarScore_DistanceFromArmy
                  > Game.getProvince(possibleProvinces.get(bestID)).aiMoveArmyAtWarScore_DistanceFromArmy) {
                  bestID = ix;
               }
            }

            if (Game.getProvince(possibleProvinces.get(bestID)).aiMoveArmyAtWarScore_DistanceFromArmy == -999999.0F) {
               break;
            }

            bestToChooseFrom.add(possibleProvinces.get(bestID));
            Game.getProvince(possibleProvinces.get(bestID)).aiMoveArmyAtWarScore_DistanceFromArmy = -999999.0F;
         }

         return bestToChooseFrom.isEmpty() ? fromProvinceID : bestToChooseFrom.get(Game.oR.nextInt(bestToChooseFrom.size()));
      }
   }

   public static int selectBestProvince_Random(int fromProvinceID) {
      return possibleProvinces.get(Game.oR.nextInt(possibleProvinces.size()));
   }

   public static int selectBestProvince_Defend(int fromProvinceID) {
      for (int i = possibleProvincesDefend.size() - 1; i >= 0; i--) {
         Province province = Game.getProvince(possibleProvincesDefend.get(i));
         province.aiMoveArmyAtWarScore_DistanceFromArmy = province.fProvinceValue
            * province.aiMoveArmyAtWarScore
            * (1.0F - Game.getManhattanDistance_PercOfMax(fromProvinceID, possibleProvincesDefend.get(i)));
      }

      Game.getProvince(fromProvinceID).aiMoveArmyAtWarScore_DistanceFromArmy = -999999.0F;
      int bestID = 0;

      for (int i = possibleProvincesDefend.size() - 1; i > 0; i--) {
         if (Game.getProvince(possibleProvincesDefend.get(i)).aiMoveArmyAtWarScore_DistanceFromArmy
            > Game.getProvince(possibleProvincesDefend.get(bestID)).aiMoveArmyAtWarScore_DistanceFromArmy) {
            bestID = i;
         }
      }

      return possibleProvincesDefend.get(bestID);
   }

   public static void moveArmiesLowMoraleManpower(int civID) {
      try {
         for (int i = armiesLowMoraleManpower.size() - 1; i >= 0; i--) {
            if (Game.getProvince(armiesLowMoraleManpower.get(i).provinceID).getCivID() != civID) {
               int rand = Game.oR.nextInt(100);
               if (rand >= Game.aiValuesArmies.AT_WAR_DEFEND_IN_ENEMY_TERRITORY_DO_NOTHING) {
                  if (rand >= Game.aiValuesArmies.AT_WAR_DEFEND_IN_ENEMY_TERRITORY_MOVE_TO_CLOSEST_PROVINCE) {
                     if (rand < Game.aiValuesArmies.AT_WAR_DEFEND_IN_ENEMY_TERRITORY_MOVE_TO_CAPITAL) {
                        Game.getCiv(civID)
                           .newMove(
                              armiesLowMoraleManpower.get(i).provinceID,
                              Game.getCiv(civID).getCapitalProvinceID(),
                              armiesLowMoraleManpower.get(i).key,
                              0,
                              false
                           );
                     } else {
                        Game.getCiv(civID)
                           .newMove(
                              armiesLowMoraleManpower.get(i).provinceID,
                              Game.getCiv(civID).getProvinceID(Game.oR.nextInt(Game.getCiv(civID).getNumOfProvinces())),
                              armiesLowMoraleManpower.get(i).key,
                              0,
                              false
                           );
                     }
                  } else {
                     int bestProvinceID = Game.getCiv(civID).getCapitalProvinceID();
                     float bestDistance = Game.getDistanceFromProvinceToProvince(armiesLowMoraleManpower.get(i).provinceID, bestProvinceID);

                     for (int a = 0; a < Game.getCiv(civID).getNumOfProvinces(); a++) {
                        float tDistance = Game.getDistanceFromProvinceToProvince(armiesLowMoraleManpower.get(i).provinceID, Game.getCiv(civID).getProvinceID(a));
                        if (tDistance < bestDistance) {
                           bestProvinceID = Game.getCiv(civID).getProvinceID(a);
                           bestDistance = tDistance;
                        }
                     }

                     Game.getCiv(civID).newMove(armiesLowMoraleManpower.get(i).provinceID, bestProvinceID, armiesLowMoraleManpower.get(i).key, 0, false);
                  }
               }
            } else {
               int rand = Game.oR.nextInt(100);
               if (rand >= Game.aiValuesArmies.AT_WAR_DEFEND_IN_OWN_TERRITORY_MOVE_TO_NEIGHBORING_PROVINCE) {
                  if (rand < Game.aiValuesArmies.AT_WAR_DEFEND_IN_OWN_TERRITORY_MOVE_TO_CAPITAL) {
                     Game.getCiv(civID)
                        .newMove(
                           armiesLowMoraleManpower.get(i).provinceID, Game.getCiv(civID).getCapitalProvinceID(), armiesLowMoraleManpower.get(i).key, 0, false
                        );
                  }
               } else {
                  Province province = Game.getProvince(armiesLowMoraleManpower.get(i).provinceID);
                  List<Integer> possibleToMove = new ArrayList<>();

                  for (int ax = 0; ax < province.getNeighboringProvincesSize(); ax++) {
                     if (Game.getProvince(province.getNeighboringProvinces(ax)).getCivID() == civID
                        && !Game.getProvince(province.getNeighboringProvinces(ax)).isEnemyArmyInProvince(civID)) {
                        possibleToMove.add(province.getNeighboringProvinces(ax));
                     }
                  }

                  if (!possibleToMove.isEmpty()) {
                     Game.getCiv(civID)
                        .newMove(
                           armiesLowMoraleManpower.get(i).provinceID,
                           possibleToMove.get(Game.oR.nextInt(possibleToMove.size())),
                           armiesLowMoraleManpower.get(i).key,
                           0,
                           false
                        );
                  }

                  possibleToMove.clear();
               }
            }
         }
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }
   }

   public static void mergeArmy(int civID, int provinceID, String armyKey) {
      try {
         if (Game.getCiv(civID).aiMerge.isArmyMerging_Just(civID, armyKey)) {
            return;
         }

         for (int i = 0; i < Game.getProvince(provinceID).getArmySize(); i++) {
            if (Game.getProvince(provinceID).getArmy(i).civID == civID && !Game.getProvince(provinceID).getArmy(i).key.equals(armyKey)) {
               List<String> toMerge = new ArrayList<>();
               toMerge.add(Game.getProvince(provinceID).getArmy(i).key);
               toMerge.add(armyKey);
               Game.getProvince(provinceID).mergeUnits(toMerge);
               return;
            }
         }

         int bestID = -1;
         float bestDistance = 999999.0F;

         for (int ix = 0; ix < Game.getCiv(civID).iArmyPositionSize; ix++) {
            if (Game.getCiv(civID).getArmyPosition(ix) != provinceID) {
               float tDistance = Game.getDistanceFromProvinceToProvince(provinceID, Game.getCiv(civID).getArmyPosition(ix));
               if (tDistance < bestDistance) {
                  ArmyDivision armyDivision = Game.getProvince(Game.getCiv(civID).getArmyPosition(ix)).getArmy(Game.getCiv(civID).getArmyPositionKey(ix));
                  if (armyDivision != null && !armyDivision.inMovement) {
                     bestID = ix;
                     bestDistance = tDistance;
                  }
               }
            }
         }

         if (bestID >= 0) {
            if (Game.getCiv(civID).newMove(provinceID, Game.getCiv(civID).getArmyPosition(bestID), armyKey, 0, false)) {
               Game.getCiv(civID).aiMerge.addMerge(Game.getCiv(civID).getArmyPosition(bestID), Game.getCiv(civID).getArmyPositionKey(bestID));
               Game.getCiv(civID).aiMerge.addMerge(Game.getCiv(civID).getArmyPosition(bestID), armyKey);
            }
         } else {
            Game.getCiv(civID).newMove(provinceID, Game.getCiv(civID).getCapitalProvinceID(), armyKey, 0, false);
         }
      } catch (Exception var8) {
         CFG.exceptionStack(var8);
      }
   }

   public static void mergeArmy_InProvince(int civID, int provinceID, String armyKey) {
      try {
         int largestArmyID = -1;

         for (int i = 0; i < Game.getProvince(provinceID).getArmySize(); i++) {
            if (Game.getProvince(provinceID).getArmy(i).civID == civID && !Game.getProvince(provinceID).getArmy(i).key.equals(armyKey)) {
               if (largestArmyID < 0) {
                  largestArmyID = i;
               } else if (Game.getProvince(provinceID).getArmy(i).iArmyRegimentSize > Game.getProvince(provinceID).getArmy(largestArmyID).iArmyRegimentSize) {
                  largestArmyID = i;
               }
            }
         }

         if (largestArmyID >= 0) {
            List<String> toMerge = new ArrayList<>();
            toMerge.add(Game.getProvince(provinceID).getArmy(largestArmyID).key);
            toMerge.add(armyKey);
            Game.getProvince(provinceID).mergeUnits(toMerge);
         }
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
      }
   }

   public static class EnemyArmy {
      public int provinceID;
      public int army;
      public float distance;

      public EnemyArmy(int provinceID, int army) {
         this.provinceID = provinceID;
         this.army = army;
      }
   }
}
