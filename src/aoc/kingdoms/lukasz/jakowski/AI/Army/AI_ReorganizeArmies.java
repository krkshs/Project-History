package aoc.kingdoms.lukasz.jakowski.AI.Army;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.map.army.ArmyDivision;
import aoc.kingdoms.lukasz.map.army.ArmyManager;
import aoc.kingdoms.lukasz.map.army.ArmyPosition;
import aoc.kingdoms.lukasz.map.battles.BattleManager;
import aoc.kingdoms.lukasz.map.civilization.Civilization;
import aoc.kingdoms.lukasz.map.province.Province;
import java.util.ArrayList;
import java.util.List;

public class AI_ReorganizeArmies {
   public static void reorganizeArmies_AtPeace(int civID) {
      try {
         Civilization civ = Game.getCiv(civID);
         civ.aiMerge.aiMergeTasks.clear();
         List<ArmyPosition> armiesInOwnProvinces = new ArrayList<>();
         List<ArmyPosition> armiesInAnotherTerritory = new ArrayList<>();

         for (int i = 0; i < civ.iArmyPositionSize; i++) {
            ArmyDivision armyDivision = Game.getProvince(civ.getArmyPosition(i)).getArmy(civ.getArmyPositionKey(i));
            if (armyDivision != null && armyDivision.civID == civID && !armyDivision.inBattle && !armyDivision.inRetreat) {
               if (Game.getProvince(civ.getArmyPosition(i)).getCivID() == civID) {
                  armiesInOwnProvinces.add(new ArmyPosition(civ.getArmyPosition(i), armyDivision.key));
               } else {
                  armiesInAnotherTerritory.add(new ArmyPosition(civ.getArmyPosition(i), armyDivision.key));
               }

               if (armyDivision.inMovement && !civ.isInMoveUnits_ArmyKey(armyDivision.key)) {
                  armyDivision.inMovement = false;
                  armyDivision.inRetreat = false;
               }
            }
         }

         if (civ.aiCivDiplomacy.w.isEmpty()) {
            buildProvincesScore(civID);
            Province var10000 = Game.getProvince(civ.getCapitalProvinceID());
            var10000.aiArmyScore = var10000.aiArmyScore + Game.aiValuesArmies.SCORE_REORGANIZE_ARMY_CAPITAL;
         } else {
            buildProvincesScore_PrepareForWar(civID);
            Province var71 = Game.getProvince(civ.getCapitalProvinceID());
            var71.aiArmyScore = var71.aiArmyScore + Game.aiValuesArmies.SCORE_REORGANIZE_ARMY_PREPARE_FOR_WAR_CAPITAL;
         }

         int battleWidth = BattleManager.getBattleWidth(civID);
         AI_Army_Composition armyFirst = new AI_Army_Composition(civID, (int)Math.max(4.0F, battleWidth * 2 * civ.aiCiv.army_First));
         AI_Army_Composition armySecond = new AI_Army_Composition(civID, (int)Math.max(4.0F, battleWidth * 2 * civ.aiCiv.army_Second));
         AI_Army_Composition armyThird = new AI_Army_Composition(civID, (int)Math.max(4.0F, battleWidth * 2 * civ.aiCiv.army_Third));
         AI_Army_Composition armyFourth = new AI_Army_Composition(civID, (int)Math.max(4.0F, battleWidth * 2 * civ.aiCiv.army_Fourth));
         List<Integer> civProvinces = new ArrayList<>(civ.getProvinces());
         List<Integer> sortedProvinces = new ArrayList<>();
         int limitOfProvinces = (int)(Math.ceil((float)civ.getArmyRegimentSize() / armyFirst.numOfRegiments) + 1.0);

         while (civProvinces.size() > 0 && limitOfProvinces-- > 0) {
            int bestID = 0;

            for (int ix = civProvinces.size() - 1; ix > 0; ix--) {
               if (Game.getProvince(civProvinces.get(ix)).aiArmyScore > Game.getProvince(civProvinces.get(bestID)).aiArmyScore) {
                  bestID = ix;
               }
            }

            sortedProvinces.add(civProvinces.get(bestID));
            civProvinces.remove(bestID);
         }

         civProvinces.clear();
         int sortedID = 0;
         int limit = 100;

         while (!sortedProvinces.isEmpty() && (!armiesInOwnProvinces.isEmpty() || !armiesInAnotherTerritory.isEmpty()) && limit-- > 0) {
            int provinceID = sortedProvinces.get(sortedID);
            String armyKey = null;
            AI_Army_Composition armyCompositionToCreate;
            switch (provinceID % 4) {
               case 0:
                  armyCompositionToCreate = new AI_Army_Composition(armyFirst);
                  break;
               case 1:
                  armyCompositionToCreate = new AI_Army_Composition(armySecond);
                  break;
               case 2:
                  armyCompositionToCreate = new AI_Army_Composition(armyThird);
                  break;
               default:
                  armyCompositionToCreate = new AI_Army_Composition(armyFourth);
            }

            for (int ixx = armiesInOwnProvinces.size() - 1; ixx >= 0; ixx--) {
               if (armiesInOwnProvinces.get(ixx).provinceID == provinceID) {
                  ArmyDivision armyDivision = Game.getProvince(provinceID).getArmy(armiesInOwnProvinces.get(ixx).key);
                  if (armyDivision != null) {
                     if (armyKey == null) {
                        armyKey = armyDivision.key;
                        Game.getCiv(civID).aiMerge.addMerge(armyDivision.provinceID, armyDivision.key);
                     }

                     AI_Army_Composition currentArmyComposition = armyDivision.getArmyComposition();
                     if (armyKey.equals(armyDivision.key)) {
                        int tempNum = Math.min(armyCompositionToCreate.numFirstLine, currentArmyComposition.numFirstLine);
                        armyCompositionToCreate.numFirstLine -= tempNum;
                        currentArmyComposition.numFirstLine -= tempNum;
                        tempNum = Math.min(armyCompositionToCreate.numFlank, currentArmyComposition.numFlank);
                        armyCompositionToCreate.numFlank -= tempNum;
                        currentArmyComposition.numFlank -= tempNum;
                        tempNum = Math.min(armyCompositionToCreate.numSupport, currentArmyComposition.numSupport);
                        armyCompositionToCreate.numSupport -= tempNum;
                        currentArmyComposition.numSupport -= tempNum;
                        tempNum = Math.min(armyCompositionToCreate.numSiege, currentArmyComposition.numSiege);
                        armyCompositionToCreate.numSiege -= tempNum;
                        currentArmyComposition.numSiege -= tempNum;
                        armyCompositionToCreate.updateNumOfRegiments();
                        currentArmyComposition.updateNumOfRegiments();
                        AI_Army_Composition armyCompositionSplit = new AI_Army_Composition();
                        if (currentArmyComposition.numFirstLine > Game.aiValuesArmies.REORGANIZE_MAX_NUMBER_OF_REGIMENTS_OVER_MAX) {
                           tempNum = currentArmyComposition.numFirstLine - Game.aiValuesArmies.REORGANIZE_MAX_NUMBER_OF_REGIMENTS_OVER_MAX;
                           currentArmyComposition.numFirstLine -= tempNum;
                           armyCompositionSplit.numFirstLine = tempNum;
                        }

                        if (currentArmyComposition.numFlank > Game.aiValuesArmies.REORGANIZE_MAX_NUMBER_OF_REGIMENTS_OVER_MAX) {
                           tempNum = currentArmyComposition.numFlank - Game.aiValuesArmies.REORGANIZE_MAX_NUMBER_OF_REGIMENTS_OVER_MAX;
                           currentArmyComposition.numFlank -= tempNum;
                           armyCompositionSplit.numFlank = tempNum;
                        }

                        if (currentArmyComposition.numSupport > Game.aiValuesArmies.REORGANIZE_MAX_NUMBER_OF_REGIMENTS_OVER_MAX) {
                           tempNum = currentArmyComposition.numSupport - Game.aiValuesArmies.REORGANIZE_MAX_NUMBER_OF_REGIMENTS_OVER_MAX;
                           currentArmyComposition.numSupport -= tempNum;
                           armyCompositionSplit.numSupport = tempNum;
                        }

                        if (currentArmyComposition.numSiege > Game.aiValuesArmies.REORGANIZE_MAX_NUMBER_OF_REGIMENTS_OVER_MAX) {
                           tempNum = currentArmyComposition.numSiege - Game.aiValuesArmies.REORGANIZE_MAX_NUMBER_OF_REGIMENTS_OVER_MAX;
                           currentArmyComposition.numSiege -= tempNum;
                           armyCompositionSplit.numSiege = tempNum;
                        }

                        currentArmyComposition.updateNumOfRegiments();
                        armyCompositionSplit.updateNumOfRegiments();
                        if (armyCompositionSplit.numOfRegiments > 0) {
                           int splitProvinceID = armyDivision.provinceID;
                           String splitKey = AI_SplitArmy.splitArmy(armyCompositionSplit, armyDivision.provinceID, armyDivision.key);
                           if (splitKey != null) {
                              armiesInOwnProvinces.add(new ArmyPosition(splitProvinceID, splitKey));
                           }

                           Game.getProvince(provinceID).updateArmyPosY();
                        }

                        armiesInOwnProvinces.remove(ixx);
                     } else {
                        ArmyDivision armyDivision_ToCreate = Game.getProvince(provinceID).getArmy(armyKey);
                        boolean updateArmies = false;
                        if (armyDivision_ToCreate != null) {
                           if (armyCompositionToCreate.numFirstLine > 0 && currentArmyComposition.numFirstLine > 0) {
                              for (int a = armyDivision.iArmyRegimentSize - 1; a >= 0; a--) {
                                 if (ArmyManager.lUnitsTypes.get(armyDivision.lArmyRegiment.get(a).uID).Line == 0) {
                                    armyDivision_ToCreate.addRegiment(armyDivision.lArmyRegiment.get(a));
                                    armyDivision.removeRegiment(a);
                                    armyCompositionToCreate.numFirstLine--;
                                    updateArmies = true;
                                    if (armyCompositionToCreate.numFirstLine <= 0) {
                                       break;
                                    }
                                 }
                              }
                           }

                           if (armyCompositionToCreate.numFlank > 0 && currentArmyComposition.numFlank > 0) {
                              for (int ax = armyDivision.iArmyRegimentSize - 1; ax >= 0; ax--) {
                                 if (ArmyManager.lUnitsTypes.get(armyDivision.lArmyRegiment.get(ax).uID).Line == 1) {
                                    armyDivision_ToCreate.addRegiment(armyDivision.lArmyRegiment.get(ax));
                                    armyDivision.removeRegiment(ax);
                                    armyCompositionToCreate.numFlank--;
                                    updateArmies = true;
                                    if (armyCompositionToCreate.numFlank <= 0) {
                                       break;
                                    }
                                 }
                              }
                           }

                           if (armyCompositionToCreate.numSupport > 0 && currentArmyComposition.numSupport > 0) {
                              for (int axx = armyDivision.iArmyRegimentSize - 1; axx >= 0; axx--) {
                                 if (ArmyManager.lUnitsTypes.get(armyDivision.lArmyRegiment.get(axx).uID).Line == 2
                                    && !ArmyManager.lArmy.get(armyDivision.lArmyRegiment.get(axx).uID).get(armyDivision.lArmyRegiment.get(axx).aID).SiegeUnit
                                    && !ArmyManager.lArmy.get(armyDivision.lArmyRegiment.get(axx).uID).get(armyDivision.lArmyRegiment.get(axx).aID).isSettler) {
                                    armyDivision_ToCreate.addRegiment(armyDivision.lArmyRegiment.get(axx));
                                    armyDivision.removeRegiment(axx);
                                    armyCompositionToCreate.numSupport--;
                                    updateArmies = true;
                                    if (armyCompositionToCreate.numSupport <= 0) {
                                       break;
                                    }
                                 }
                              }
                           }

                           if (armyCompositionToCreate.numSiege > 0 && currentArmyComposition.numSiege > 0) {
                              for (int axxx = armyDivision.iArmyRegimentSize - 1; axxx >= 0; axxx--) {
                                 if (ArmyManager.lUnitsTypes.get(armyDivision.lArmyRegiment.get(axxx).uID).Line == 2
                                    && ArmyManager.lArmy.get(armyDivision.lArmyRegiment.get(axxx).uID).get(armyDivision.lArmyRegiment.get(axxx).aID).SiegeUnit
                                    && !ArmyManager.lArmy.get(armyDivision.lArmyRegiment.get(axxx).uID).get(armyDivision.lArmyRegiment.get(axxx).aID).isSettler
                                    )
                                  {
                                    armyDivision_ToCreate.addRegiment(armyDivision.lArmyRegiment.get(axxx));
                                    armyDivision.removeRegiment(axxx);
                                    armyCompositionToCreate.numSiege--;
                                    updateArmies = true;
                                    if (armyCompositionToCreate.numSiege <= 0) {
                                       break;
                                    }
                                 }
                              }
                           }

                           if (updateArmies) {
                              int armyID = Game.getProvince(provinceID).getArmyKeyID(armyDivision.key);
                              int armyID_ToCreate = Game.getProvince(provinceID).getArmyKeyID(armyDivision_ToCreate.key);
                              if (armyID >= 0 && armyID_ToCreate >= 0) {
                                 if (armyDivision.iArmyRegimentSize <= 0) {
                                    Game.getProvince(provinceID).updateRegiment(armyID_ToCreate, armyDivision_ToCreate.lArmyRegiment);
                                    Game.getProvince(provinceID).removeArmy(armyDivision.key);
                                    armiesInOwnProvinces.remove(ixx);
                                 } else {
                                    Game.getProvince(provinceID).updateRegiment(armyID, armyDivision.lArmyRegiment);
                                    Game.getProvince(provinceID).updateRegiment(armyID_ToCreate, armyDivision_ToCreate.lArmyRegiment);
                                 }

                                 Game.getProvince(provinceID).updateArmyPosY();
                              }
                           }
                        }
                     }
                  }

                  if (armyCompositionToCreate.numOfRegiments <= 0) {
                     break;
                  }
               }
            }

            if (armyCompositionToCreate.numOfRegiments > 0) {
               for (int ixxx = armiesInAnotherTerritory.size() - 1; ixxx >= 0; ixxx--) {
                  ArmyDivision armyDivisionx = Game.getProvince(armiesInAnotherTerritory.get(ixxx).provinceID).getArmy(armiesInAnotherTerritory.get(ixxx).key);
                  if (armyDivisionx != null) {
                     AI_Army_Composition currentArmyComposition = armyDivisionx.getArmyComposition();
                     AI_Army_Composition armyCompositionSplitx = new AI_Army_Composition();
                     if (armyCompositionToCreate.numFirstLine > 0 && currentArmyComposition.numFirstLine > 0) {
                        int tempNumx = Math.min(armyCompositionToCreate.numFirstLine, currentArmyComposition.numFirstLine);
                        currentArmyComposition.numFirstLine -= tempNumx;
                        armyCompositionToCreate.numFirstLine -= tempNumx;
                        armyCompositionSplitx.numFirstLine = tempNumx;
                     }

                     if (armyCompositionToCreate.numFlank > 0 && currentArmyComposition.numFlank > 0) {
                        int tempNumx = Math.min(armyCompositionToCreate.numFlank, currentArmyComposition.numFlank);
                        currentArmyComposition.numFlank -= tempNumx;
                        armyCompositionToCreate.numFlank -= tempNumx;
                        armyCompositionSplitx.numFlank = tempNumx;
                     }

                     if (armyCompositionToCreate.numSupport > 0 && currentArmyComposition.numSupport > 0) {
                        int tempNumx = Math.min(armyCompositionToCreate.numSupport, currentArmyComposition.numSupport);
                        currentArmyComposition.numSupport -= tempNumx;
                        armyCompositionToCreate.numSupport -= tempNumx;
                        armyCompositionSplitx.numSupport = tempNumx;
                     }

                     if (armyCompositionToCreate.numSiege > 0 && currentArmyComposition.numSiege > 0) {
                        int tempNumx = Math.min(armyCompositionToCreate.numSiege, currentArmyComposition.numSiege);
                        currentArmyComposition.numSiege -= tempNumx;
                        armyCompositionToCreate.numSiege -= tempNumx;
                        armyCompositionSplitx.numSiege = tempNumx;
                     }

                     currentArmyComposition.updateNumOfRegiments();
                     armyCompositionSplitx.updateNumOfRegiments();
                     armyCompositionToCreate.updateNumOfRegiments();
                     if (armyCompositionSplitx.numOfRegiments > 0) {
                        if (currentArmyComposition.numOfRegiments == 0) {
                           if (civ.newMove(armiesInAnotherTerritory.get(ixxx).provinceID, provinceID, armiesInAnotherTerritory.get(ixxx).key, 0, false)) {
                              if (armyKey == null) {
                                 armyKey = armiesInAnotherTerritory.get(ixxx).key;
                                 civ.aiMerge.addMerge(provinceID, armyKey);
                              } else {
                                 civ.aiMerge.addMerge(provinceID, armiesInAnotherTerritory.get(ixxx).key);
                              }
                           } else {
                              ArmyDivision extraRegroup = Game.getProvince(armiesInAnotherTerritory.get(ixxx).provinceID)
                                 .getArmy(armiesInAnotherTerritory.get(ixxx).key);
                              if (extraRegroup != null) {
                                 extraRegroup.inBattle = false;
                                 extraRegroup.inRetreat = false;
                                 Game.getProvince(armiesInAnotherTerritory.get(ixxx).provinceID).removeArmy(armiesInAnotherTerritory.get(ixxx).key);
                                 Game.getProvince(provinceID).addArmy(extraRegroup);
                                 if (armyKey == null) {
                                    armyKey = armiesInAnotherTerritory.get(ixxx).key;
                                    civ.aiMerge.addMerge(provinceID, armyKey);
                                 } else {
                                    civ.aiMerge.addMerge(provinceID, armiesInAnotherTerritory.get(ixxx).key);
                                    civ.aiMerge.checkMerge(provinceID, armyKey);
                                 }
                              }
                           }

                           armiesInAnotherTerritory.remove(ixxx);
                        } else {
                           int splitProvinceID = armyDivisionx.provinceID;
                           String splitKey = AI_SplitArmy.splitArmy(armyCompositionSplitx, armyDivisionx.provinceID, armyDivisionx.key);
                           Game.getProvince(splitProvinceID).updateArmyPosY();
                           if (splitKey != null) {
                              if (civ.newMove(splitProvinceID, provinceID, splitKey, 0, false)) {
                                 if (armyKey == null) {
                                    armyKey = splitKey;
                                    civ.aiMerge.addMerge(provinceID, splitKey);
                                 } else {
                                    civ.aiMerge.addMerge(provinceID, splitKey);
                                 }
                              } else {
                                 ArmyDivision extraRegroup = Game.getProvince(armiesInAnotherTerritory.get(ixxx).provinceID)
                                    .getArmy(armiesInAnotherTerritory.get(ixxx).key);
                                 if (extraRegroup != null) {
                                    extraRegroup.inBattle = false;
                                    extraRegroup.inRetreat = false;
                                    Game.getProvince(armiesInAnotherTerritory.get(ixxx).provinceID).removeArmy(armiesInAnotherTerritory.get(ixxx).key);
                                    Game.getProvince(provinceID).addArmy(extraRegroup);
                                    if (armyKey == null) {
                                       armyKey = armiesInAnotherTerritory.get(ixxx).key;
                                       civ.aiMerge.addMerge(provinceID, armyKey);
                                    } else {
                                       civ.aiMerge.addMerge(provinceID, armiesInAnotherTerritory.get(ixxx).key);
                                       civ.aiMerge.checkMerge(provinceID, armyKey);
                                    }
                                 }
                              }
                           }

                           if (Game.getProvince(armiesInAnotherTerritory.get(ixxx).provinceID).getArmy(armiesInAnotherTerritory.get(ixxx).key) == null) {
                              armiesInAnotherTerritory.remove(ixxx);
                           }
                        }
                     }
                  }

                  if (armyCompositionToCreate.numOfRegiments <= 0) {
                     break;
                  }
               }
            }

            if (armyCompositionToCreate.numOfRegiments > 0) {
               for (int ixxx = armiesInOwnProvinces.size() - 1; ixxx >= 0; ixxx--) {
                  ArmyDivision armyDivisionxx = Game.getProvince(armiesInOwnProvinces.get(ixxx).provinceID).getArmy(armiesInOwnProvinces.get(ixxx).key);
                  if (armyDivisionxx != null) {
                     AI_Army_Composition currentArmyCompositionx = armyDivisionxx.getArmyComposition();
                     AI_Army_Composition armyCompositionSplitxx = new AI_Army_Composition();
                     if (armyCompositionToCreate.numFirstLine > 0 && currentArmyCompositionx.numFirstLine > 0) {
                        int tempNumx = Math.min(armyCompositionToCreate.numFirstLine, currentArmyCompositionx.numFirstLine);
                        currentArmyCompositionx.numFirstLine -= tempNumx;
                        armyCompositionToCreate.numFirstLine -= tempNumx;
                        armyCompositionSplitxx.numFirstLine = tempNumx;
                     }

                     if (armyCompositionToCreate.numFlank > 0 && currentArmyCompositionx.numFlank > 0) {
                        int tempNumx = Math.min(armyCompositionToCreate.numFlank, currentArmyCompositionx.numFlank);
                        currentArmyCompositionx.numFlank -= tempNumx;
                        armyCompositionToCreate.numFlank -= tempNumx;
                        armyCompositionSplitxx.numFlank = tempNumx;
                     }

                     if (armyCompositionToCreate.numSupport > 0 && currentArmyCompositionx.numSupport > 0) {
                        int tempNumx = Math.min(armyCompositionToCreate.numSupport, currentArmyCompositionx.numSupport);
                        currentArmyCompositionx.numSupport -= tempNumx;
                        armyCompositionToCreate.numSupport -= tempNumx;
                        armyCompositionSplitxx.numSupport = tempNumx;
                     }

                     if (armyCompositionToCreate.numSiege > 0 && currentArmyCompositionx.numSiege > 0) {
                        int tempNumx = Math.min(armyCompositionToCreate.numSiege, currentArmyCompositionx.numSiege);
                        currentArmyCompositionx.numSiege -= tempNumx;
                        armyCompositionToCreate.numSiege -= tempNumx;
                        armyCompositionSplitxx.numSiege = tempNumx;
                     }

                     currentArmyCompositionx.updateNumOfRegiments();
                     armyCompositionSplitxx.updateNumOfRegiments();
                     armyCompositionToCreate.updateNumOfRegiments();
                     if (armyCompositionSplitxx.numOfRegiments > 0) {
                        if (currentArmyCompositionx.numOfRegiments == 0) {
                           if (civ.newMove(armiesInOwnProvinces.get(ixxx).provinceID, provinceID, armiesInOwnProvinces.get(ixxx).key, 0, false)) {
                              if (armyKey == null) {
                                 armyKey = armiesInOwnProvinces.get(ixxx).key;
                                 civ.aiMerge.addMerge(provinceID, armyKey);
                              } else {
                                 civ.aiMerge.addMerge(provinceID, armiesInOwnProvinces.get(ixxx).key);
                              }
                           } else {
                              ArmyDivision extraRegroup = Game.getProvince(armiesInOwnProvinces.get(ixxx).provinceID)
                                 .getArmy(armiesInOwnProvinces.get(ixxx).key);
                              if (extraRegroup != null) {
                                 extraRegroup.inBattle = false;
                                 extraRegroup.inRetreat = false;
                                 Game.getProvince(armiesInOwnProvinces.get(ixxx).provinceID).removeArmy(armiesInOwnProvinces.get(ixxx).key);
                                 Game.getProvince(provinceID).addArmy(extraRegroup);
                                 if (armyKey == null) {
                                    armyKey = armiesInOwnProvinces.get(ixxx).key;
                                    civ.aiMerge.addMerge(provinceID, armyKey);
                                 } else {
                                    civ.aiMerge.addMerge(provinceID, armiesInOwnProvinces.get(ixxx).key);
                                    civ.aiMerge.checkMerge(provinceID, armyKey);
                                 }
                              }
                           }

                           armiesInOwnProvinces.remove(ixxx);
                        } else {
                           int splitProvinceIDx = armyDivisionxx.provinceID;
                           String splitKeyx = AI_SplitArmy.splitArmy(armyCompositionSplitxx, armyDivisionxx.provinceID, armyDivisionxx.key);
                           if (splitKeyx != null) {
                              if (civ.newMove(splitProvinceIDx, provinceID, splitKeyx, 0, false)) {
                                 if (armyKey == null) {
                                    armyKey = splitKeyx;
                                    civ.aiMerge.addMerge(provinceID, splitKeyx);
                                 } else {
                                    civ.aiMerge.addMerge(provinceID, splitKeyx);
                                 }
                              } else {
                                 ArmyDivision extraRegroup = Game.getProvince(armiesInOwnProvinces.get(ixxx).provinceID)
                                    .getArmy(armiesInOwnProvinces.get(ixxx).key);
                                 if (extraRegroup != null) {
                                    extraRegroup.inBattle = false;
                                    extraRegroup.inRetreat = false;
                                    Game.getProvince(armiesInOwnProvinces.get(ixxx).provinceID).removeArmy(armiesInOwnProvinces.get(ixxx).key);
                                    Game.getProvince(provinceID).addArmy(extraRegroup);
                                    if (armyKey == null) {
                                       armyKey = armiesInOwnProvinces.get(ixxx).key;
                                       civ.aiMerge.addMerge(provinceID, armyKey);
                                    } else {
                                       civ.aiMerge.addMerge(provinceID, armiesInOwnProvinces.get(ixxx).key);
                                       civ.aiMerge.checkMerge(provinceID, armyKey);
                                    }
                                 }
                              }
                           }

                           if (Game.getProvince(armiesInOwnProvinces.get(ixxx).provinceID).getArmy(armiesInOwnProvinces.get(ixxx).key) == null) {
                              armiesInOwnProvinces.remove(ixxx);
                           }
                        }
                     }
                  }

                  if (armyCompositionToCreate.numOfRegiments <= 0) {
                     break;
                  }
               }
            }

            if (++sortedID >= sortedProvinces.size()) {
               sortedID = 0;
            }
         }

         try {
            for (int ixxx = civ.iArmyPositionSize - 1; ixxx >= 0; ixxx--) {
               ArmyDivision armyDivisionxxx = Game.getProvince(civ.getArmyPosition(ixxx)).getArmy(civ.getArmyPositionKey(ixxx));
               if (armyDivisionxxx != null
                  && !armyDivisionxxx.inMovement
                  && !armyDivisionxxx.inBattle
                  && !armyDivisionxxx.inRetreat
                  && armyDivisionxxx.iArmyRegimentSize < Game.aiValuesArmies.REORGANIZE_MERGE_ALL_IN_PROVINCE_WITH_REGIMENTS_BELOW) {
                  AI_MoveAtWar.mergeArmy_InProvince(civID, armyDivisionxxx.provinceID, armyDivisionxxx.key);
               }
            }
         } catch (Exception var25) {
            CFG.exceptionStack(var25);
         }

         armiesInOwnProvinces.clear();
         armiesInAnotherTerritory.clear();
         sortedProvinces.clear();
         civProvinces.clear();
      } catch (Exception var26) {
         CFG.exceptionStack(var26);
      }
   }

   public static final void buildProvincesScore(int civID) {
      Civilization civ = Game.getCiv(civID);

      for (int i = 0; i < civ.getNumOfProvinces(); i++) {
         Province province = Game.getProvince(civ.getProvinceID(i));
         province.aiArmyScore = province.getGrowthRateWithBonuses() * Game.aiValuesArmies.SCORE_REORGANIZE_ARMY_GROWTH_RATE
            + province.getEconomyWithBonuses() * Game.aiValuesArmies.SCORE_REORGANIZE_ARMY_ECONOMY
            + province.fProvinceIncome * Game.aiValuesArmies.SCORE_REORGANIZE_ARMY_PROVINCE_INCOME
            + province.aiDistanceToCapital * Game.aiValuesArmies.SCORE_REORGANIZE_ARMY_DISTANCE;
      }
   }

   public static final void buildProvincesScore_PrepareForWar(int civID) {
      Civilization civ = Game.getCiv(civID);

      for (int i = 0; i < civ.getNumOfProvinces(); i++) {
         Province province = Game.getProvince(civ.getProvinceID(i));
         province.aiArmyScore = (borderWithPreparingForWar(civ, province) ? Game.aiValuesArmies.SCORE_REORGANIZE_ARMY_PREPARE_FOR_WAR_BORDERS_WITH_CIV : 1.0F)
            * (
               province.fProvinceIncome * Game.aiValuesArmies.SCORE_REORGANIZE_ARMY_PREPARE_FOR_WAR_PROVINCE_INCOME
                  + province.aiDistanceToCapital * Game.aiValuesArmies.SCORE_REORGANIZE_ARMY_PREPARE_FOR_WAR_DISTANCE
            );
      }
   }

   public static boolean borderWithPreparingForWar(Civilization civ, Province province) {
      for (int j = 0; j < civ.aiCivDiplomacy.w.size(); j++) {
         for (int i = 0; i < province.getNeighboringProvincesSize(); i++) {
            if (province.getCivID() == civ.aiCivDiplomacy.w.get(j).c) {
               return true;
            }
         }
      }

      return false;
   }
}
