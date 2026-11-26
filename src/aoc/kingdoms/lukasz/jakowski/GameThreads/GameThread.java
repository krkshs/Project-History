package aoc.kingdoms.lukasz.jakowski.GameThreads;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.AI.Army.AI_BattleEnd;
import aoc.kingdoms.lukasz.jakowski.AI.Laws.AI_Laws;
import aoc.kingdoms.lukasz.jakowski.AI.Technology.AI_SelectTechnology;
import aoc.kingdoms.lukasz.jakowski.SaveLoad.SaveGameManager;
import aoc.kingdoms.lukasz.map.CoalitionManager;
import aoc.kingdoms.lukasz.map.ResourcesManager;
import aoc.kingdoms.lukasz.map.SiegeManager;
import aoc.kingdoms.lukasz.map.army.ArmyDivision;
import aoc.kingdoms.lukasz.map.army.ArmyManager;
import aoc.kingdoms.lukasz.map.civilization.Civilization;
import aoc.kingdoms.lukasz.map.civilization.CivilizationRanking;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import aoc.kingdoms.lukasz.map.diplomacy.LibertyDesireManager;
import aoc.kingdoms.lukasz.map.moveUnits.MoveUnits;
import aoc.kingdoms.lukasz.map.technology.TechnologyTree;
import aoc.kingdoms.lukasz.map.war.WarManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.View;
import aoc.kingdoms.lukasz.menu_element.Toast;
import aoc.kingdoms.lukasz.menu_element.button.ButtonTopDate;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

public class GameThread extends Thread {
   public boolean running = true;
   public ConcurrentLinkedDeque<Integer> civsUpdateLegacyPerMonth = new ConcurrentLinkedDeque<>();
   public ConcurrentLinkedDeque<Integer> civsUpdateResearchPerMonth = new ConcurrentLinkedDeque<>();
   public ConcurrentLinkedDeque<Integer> civsUpdateLoans = new ConcurrentLinkedDeque<>();
   public ConcurrentLinkedDeque<Integer> civsUpdateArmyMaintenance = new ConcurrentLinkedDeque<>();
   public ConcurrentLinkedDeque<Integer> civsUpdateTotalIncomePerMonth = new ConcurrentLinkedDeque<>();
   public ConcurrentLinkedDeque<Game.SimpleTask> aiTask = new ConcurrentLinkedDeque<>();
   public boolean play = false;
   public int playSpeed = 3;
   public int playMaxSpeed = 5;
   public int playSpeedTIME = 100;
   public static long calculationsTime = 0L;

   public final void updateAI_SimpleTask() {
      try {
         for (int i = this.aiTask.size() - 1; i >= 0; i--) {
            try {
               this.aiTask.remove().update();
            } catch (Exception var3) {
               CFG.exceptionStack(var3);
            }
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   public final void addAI_SimpleTask(Game.SimpleTask nSimpleTask) {
      if (!this.aiTask.contains(nSimpleTask)) {
         this.aiTask.add(nSimpleTask);
      }
   }

   public void clearData() {
      this.civsUpdateLegacyPerMonth.clear();
      this.civsUpdateResearchPerMonth.clear();
      this.civsUpdateLoans.clear();
      this.civsUpdateArmyMaintenance.clear();
      this.civsUpdateTotalIncomePerMonth.clear();
      this.aiTask.clear();
   }

   public final int getPlaySpeed() {
      switch (this.playSpeed) {
         case 1:
            this.playSpeedTIME = GameValues.gameSpeed.GAME_SPEED_1;
            break;
         case 2:
            this.playSpeedTIME = GameValues.gameSpeed.GAME_SPEED_2;
            break;
         case 3:
            this.playSpeedTIME = GameValues.gameSpeed.GAME_SPEED_3;
            break;
         case 4:
            this.playSpeedTIME = GameValues.gameSpeed.GAME_SPEED_4;
            break;
         default:
            if (Game.SPECTATOR_MODE) {
               this.playSpeedTIME = GameValues.gameSpeed.GAME_SPEED_SPECTATOR_MODE;
            } else {
               this.playSpeedTIME = GameValues.gameSpeed.GAME_SPEED_5;
            }
      }

      return this.playSpeedTIME;
   }

   public final void updateNewMonth() {
      if (Game_Calendar.TURN_ID % 31 == 0) {
         try {
            WarManager.updateWars_TickingWarScore();
         } catch (Exception var2) {
            CFG.exceptionStack(var2);
         }

         Game.gameThreadUpdate.addSimpleTask(new Game.SimpleTask("buildCivilizationRanking") {
            @Override
            public void update() {
               try {
                  CivilizationRanking.buildCivilizationRanking();
               } catch (Exception var2) {
                  CFG.exceptionStack(var2);
               }
            }
         });
         Game.player.playerStats2.updateData();
         Game.player.playerStats3.updateData();
      }
   }

   public final void updateNewYear() {
      if (Game_Calendar.TURN_ID % 365 == 0) {
         Game.gameThreadUpdate.addSimpleTask(new Game.SimpleTask("buildProsperity_AverageEconomy") {
            @Override
            public void update() {
               Game.buildProsperity_AverageEconomy();
            }
         });
         Game.gameThreadUpdate.addSimpleTask(new Game.SimpleTask("updateWorldResourcesProduced_NewYear") {
            @Override
            public void update() {
               try {
                  ResourcesManager.updateWorldResourcesProduced_NewYear();
                  ResourcesManager.updateUniqueCivsGoods();
               } catch (Exception var2) {
                  CFG.exceptionStack(var2);
               }
            }
         });
      }
   }

   public void checkGameOver() {
      try {
         if (!Game.SPECTATOR_MODE && Game.getCiv(Game.player.iCivID).getNumOfProvinces() <= 0) {
            Game.menuManager.setViewIDWithoutAnimation(View.GAME_LOST);
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   public void updateMinimap() {
      if (Game_Calendar.TURN_ID % GameValues.gameUpdate.GAME_UPDATE_MINIMAP == 0) {
         Game.mapBG.requestToDisposeMinimap = true;
      }
   }

   public final void autoSaveData() {
      if (Game_Calendar.TURN_ID % GameValues.value.AUTO_SAVE_STATS_DAYS == 2) {
         Game.addSimpleTask(new Game.SimpleTask("saveStats") {
            @Override
            public void update() {
               Game.stats.saveStats();
            }
         });
      }
   }

   @Override
   public void run() {
      while (this.running) {
         try {
            calculationsTime = System.currentTimeMillis();
            if (Game.menuManager.getInGame() && !Game.menuManager.getVisibleInGame_Escape()) {
               try {
                  if (this.play && Game.mapModes.iActiveMapModeID != Game.mapModes.MODE_PEACE_VIEW) {
                     Game_Calendar.HOUR = Game_Calendar.HOUR + Game.HOURS_PER_TURN;
                     this.updateMoveUnits();
                     this.updateMoveUnits_Rebels();
                     this.updateArmyMorale_Reinforce();
                     this.updateRecruitArmy();
                     this.updateSieges();
                     this.updateBattles();
                     this.checkGameOver();
                     if (Game_Calendar.HOUR >= 24) {
                        Game_Calendar.HOUR %= 24;
                        Game_Calendar.TURN_ID++;
                        Game.stats.civStats.tr++;
                        this.autoSaveData();
                        Game_Calendar.nextDays(Game.gameAges.getAge_TurnDays(Game_Calendar.CURRENT_AGEID));
                        this.updateMinimap();
                        this.updateNewMonth();
                        this.updateNewYear();
                        this.updateAutoAssimilation();
                        this.updatePopulation();
                        ResourcesManager.updatePriceChanges();
                        this.updateLoans();
                        this.updateResearchPerMonth();
                        this.updateLegacyPerMonth();
                        this.updateArmyMaintenance();
                        this.updateTotalIncomePerMonth();
                        this.updateGold_Manpower_Legacy_Diplomacy();
                        this.updateResearch();
                        this.updateAI_SimpleTask();
                        CoalitionManager.updateCreateCoalition();
                        Game.aiManager.updateAll();
                        Game.revolutionManager.update();
                        this.updateAggressiveExpansion();
                        this.updateWarWeariness();
                        this.updateWars_Peace();
                        this.updateWars_WhitePeace();
                        LibertyDesireManager.updateLibertyDesire();
                        this.updateCivilizationBonuses();
                        this.updateCivs_Laws();
                        updateRelations();
                        this.returnOccupiedProvinces_NotAtWar();
                        SaveGameManager.autosave();
                        Game.runAgeOfChaos();
                     }
                  }
               } catch (Exception var3) {
                  CFG.exceptionStack(var3);
               }
            }

            try {
               Thread.sleep(Math.max((long)GameValues.gameSpeed.MIN_THREAD_SLEEP, this.getPlaySpeed() - (System.currentTimeMillis() - calculationsTime)));
            } catch (InterruptedException var2) {
            }
         } catch (Exception var4) {
         }
      }
   }

   public final void updateRecruitArmy() {
      try {
         for (int i = 1; i < Game.getCivsSize(); i++) {
            for (int j = Game.getCiv(i).getArmyRecruitSize() - 1; j >= 0; j--) {
               Game.getCiv(i).updateRecruitArmy(j, 0);
            }
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   private final void updateMoveUnits() {
      try {
         for (int i = 1; i < Game.getCivsSize(); i++) {
            try {
               for (int j = 0; j < Game.getCiv(i).getMoveUnitsSize(); j++) {
                  try {
                     MoveUnits moveUnits = Game.getCiv(i).getMoveUnits(j);
                     if (!moveUnits.inBattle) {
                        moveUnits.fCurrentMovingPercentage = 0.0F;
                        moveUnits.doneMovementProgressWidth = moveUnits.currentMovementProgressWidth;
                        moveUnits.currentMovementProgressWidth = moveUnits.currentMovementProgressWidth
                           + moveUnits.fSpeed
                              * (
                                 Game.terrainManager.terrains.get(Game.getProvince(moveUnits.getToProvinceID()).getTerrainID()).MovementSpeed
                                    + Game.getProvince(moveUnits.getFromProvinceID()).provBonuses.ArmyMovementSpeed
                                    + Game.getProvince(moveUnits.getFromProvinceID()).getInfrastructure()
                                       * GameValues.infrastructure.INFRASTRUCTURE_ARMY_MOVEMENT_PER_LVL
                              );
                        if (moveUnits.currentMovementProgressWidth > moveUnits.iWidth.get(0).intValue()) {
                           moveUnits.movementProgressOverWidth = moveUnits.currentMovementProgressWidth - moveUnits.iWidth.get(0).intValue();
                           moveUnits.currentMovementProgressWidth = moveUnits.iWidth.get(0).intValue();
                        }

                        moveUnits.updateLittleAnimationMovingArmy();
                        if (moveUnits.currentMovementProgressWidth >= moveUnits.iWidth.get(0).intValue()) {
                           ArmyDivision armyDivision = Game.getProvince(moveUnits.getFromProvinceID()).getArmy(moveUnits.key);
                           if (armyDivision != null) {
                              armyDivision.iShiftX_Scaled = 0;
                              armyDivision.iShiftY_Scaled = 0;
                              armyDivision.provinceID = moveUnits.getToProvinceID();
                              armyDivision.setInMovement(moveUnits.iRouteSize > 2);
                              Game.getProvince(moveUnits.getFromProvinceID()).removeArmy(moveUnits.key);
                              Game.getProvince(moveUnits.getToProvinceID()).addArmy(armyDivision);
                              Game.updateActiveArmy_MoveUnits(moveUnits.key, moveUnits.getFromProvinceID(), moveUnits.getToProvinceID());
                              if (Game.getCiv(i).isPlayerAlly) {
                                 Game.player.fog.updateFogOfWar_All(moveUnits.getFromProvinceID());
                                 Game.player.fog.updateFogOfWar_All(moveUnits.getToProvinceID());
                              }

                              if (moveUnits.iRouteSize <= 2) {
                                 armyDivision.setInMovement(false);
                                 int inProvinceID = moveUnits.getToProvinceID();
                                 if (i == Game.player.iCivID) {
                                    Game.getCiv(i).removeMove(j);
                                    j--;

                                    try {
                                       if (Game.getProvince(armyDivision.provinceID).isOccupied()) {
                                          Game.getProvince(armyDivision.provinceID).invasionMoveArmies();
                                       }
                                    } catch (Exception var8) {
                                       CFG.exceptionStack(var8);
                                    }
                                 } else {
                                    Game.getCiv(i).aiMerge.checkMerge(moveUnits.getToProvinceID(), moveUnits.key);
                                    Game.getCiv(i).removeMove(j);
                                    j--;
                                 }

                                 SiegeManager.checkForSiege(inProvinceID);
                              } else {
                                 moveUnits.updateToNextProvince(i);
                                 moveUnits.doneMovementProgressWidth = 0.0F;
                                 moveUnits.currentMovementProgressWidth = Math.max(
                                    0.0F,
                                    moveUnits.fSpeed
                                          * (
                                             Game.terrainManager.terrains.get(Game.getProvince(moveUnits.getToProvinceID()).getTerrainID()).MovementSpeed
                                                + Game.getProvince(moveUnits.getFromProvinceID()).provBonuses.ArmyMovementSpeed
                                                + Game.getProvince(moveUnits.getFromProvinceID()).getInfrastructure()
                                                   * GameValues.infrastructure.INFRASTRUCTURE_ARMY_MOVEMENT_PER_LVL
                                          )
                                       + moveUnits.movementProgressOverWidth
                                 );
                                 moveUnits.movementProgressOverWidth = 0.0F;

                                 try {
                                    if (moveUnits.currentMovementProgressWidth > moveUnits.iWidth.get(0).intValue()) {
                                       moveUnits.movementProgressOverWidth = Math.max(
                                          0.0F, moveUnits.currentMovementProgressWidth - moveUnits.iWidth.get(0).intValue()
                                       );
                                       moveUnits.currentMovementProgressWidth = moveUnits.iWidth.get(0).intValue();
                                    }
                                 } catch (Exception var7) {
                                    CFG.exceptionStack(var7);
                                 }
                              }
                           } else {
                              Game.getCiv(i).aiMerge.checkMerge(moveUnits.getToProvinceID(), moveUnits.key);
                              Game.getCiv(i).removeMove(j);
                              j--;
                           }
                        }
                     }
                  } catch (Exception var9) {
                     CFG.exceptionStack(var9);
                  }
               }
            } catch (Exception var10) {
               CFG.exceptionStack(var10);
            }
         }
      } catch (Exception var11) {
         CFG.exceptionStack(var11);
      }
   }

   private final void updateMoveUnits_Rebels() {
      try {
         for (int j = 0; j < Game.revolutionMoveUnits.iMoveUnitsSize; j++) {
            try {
               MoveUnits moveUnits = Game.revolutionMoveUnits.lMoveUnits.get(j);
               if (!moveUnits.inBattle) {
                  moveUnits.fCurrentMovingPercentage = 0.0F;
                  moveUnits.doneMovementProgressWidth = moveUnits.currentMovementProgressWidth;
                  moveUnits.currentMovementProgressWidth = moveUnits.currentMovementProgressWidth
                     + moveUnits.fSpeed
                        * (
                           Game.terrainManager.terrains.get(Game.getProvince(moveUnits.getToProvinceID()).getTerrainID()).MovementSpeed
                              + Game.getProvince(moveUnits.getFromProvinceID()).provBonuses.ArmyMovementSpeed
                              + Game.getProvince(moveUnits.getFromProvinceID()).getInfrastructure()
                                 * GameValues.infrastructure.INFRASTRUCTURE_ARMY_MOVEMENT_PER_LVL
                        );
                  if (moveUnits.currentMovementProgressWidth > moveUnits.iWidth.get(0).intValue()) {
                     moveUnits.movementProgressOverWidth = moveUnits.currentMovementProgressWidth - moveUnits.iWidth.get(0).intValue();
                     moveUnits.currentMovementProgressWidth = moveUnits.iWidth.get(0).intValue();
                  }

                  moveUnits.updateLittleAnimationMovingArmy();
                  if (moveUnits.currentMovementProgressWidth >= moveUnits.iWidth.get(0).intValue()) {
                     ArmyDivision tArmyDivision = Game.getProvince(moveUnits.getFromProvinceID()).getArmy(moveUnits.key);
                     if (tArmyDivision != null) {
                        tArmyDivision.iShiftX_Scaled = 0;
                        tArmyDivision.iShiftY_Scaled = 0;
                        tArmyDivision.provinceID = moveUnits.getToProvinceID();
                        tArmyDivision.setInMovement(moveUnits.iRouteSize > 2);
                        Game.getProvince(moveUnits.getFromProvinceID()).removeArmy_MoveUnits(moveUnits.key);
                        Game.getProvince(moveUnits.getToProvinceID()).addArmy_MoveUnits(tArmyDivision);
                        Game.revolutionManager.updateArmyPosition(moveUnits.key, moveUnits.getToProvinceID());
                        Game.updateActiveArmy_MoveUnits(moveUnits.key, moveUnits.getFromProvinceID(), moveUnits.getToProvinceID());
                        if (moveUnits.iRouteSize <= 2) {
                           tArmyDivision.setInMovement(false);
                           tArmyDivision.inRetreat = false;
                           SiegeManager.checkForSiege(moveUnits.getToProvinceID());
                           Game.revolutionMoveUnits.removeMove(j);
                           j--;
                        } else {
                           moveUnits.updateToNextProvince(tArmyDivision.civID);
                           moveUnits.doneMovementProgressWidth = 0.0F;
                           moveUnits.currentMovementProgressWidth = Math.max(
                              0.0F,
                              moveUnits.fSpeed
                                    * (
                                       Game.terrainManager.terrains.get(Game.getProvince(moveUnits.getToProvinceID()).getTerrainID()).MovementSpeed
                                          + Game.getProvince(moveUnits.getFromProvinceID()).provBonuses.ArmyMovementSpeed
                                          + Game.getProvince(moveUnits.getFromProvinceID()).getInfrastructure()
                                             * GameValues.infrastructure.INFRASTRUCTURE_ARMY_MOVEMENT_PER_LVL
                                    )
                                 + moveUnits.movementProgressOverWidth
                           );
                           moveUnits.movementProgressOverWidth = 0.0F;

                           try {
                              if (moveUnits.currentMovementProgressWidth > moveUnits.iWidth.get(0).intValue()) {
                                 moveUnits.movementProgressOverWidth = Math.max(
                                    0.0F, moveUnits.currentMovementProgressWidth - moveUnits.iWidth.get(0).intValue()
                                 );
                                 moveUnits.currentMovementProgressWidth = moveUnits.iWidth.get(0).intValue();
                              }
                           } catch (Exception var5) {
                              CFG.exceptionStack(var5);
                           }
                        }
                     } else {
                        Game.revolutionMoveUnits.removeMove(j);
                        j--;
                     }
                  }
               }
            } catch (Exception var6) {
               CFG.exceptionStack(var6);
            }
         }
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }
   }

   public final void updateSpeed(int speed) {
      Game.gameThread.playSpeed = Math.max(1, Math.min(Game.gameThread.playMaxSpeed, speed));
      ButtonTopDate.ANIMATION_TIME = CFG.currentTimeMillis;
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Speed") + ": ", CFG.FONT_BOLD_SMALL));
      nData.add(
         new MenuElement_HoverElement_Type_Text(
            "" + Game.gameThread.playSpeed + "/" + Game.gameThread.playMaxSpeed, CFG.FONT_REGULAR_SMALL, Colors.HOVER_IMPORTANT
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      Game.menuManager.addToast(new Toast(nElements, 0, 1000, CFG.GAME_WIDTH, ImageManager.getImage(Images.topStats).getHeight() + CFG.PADDING * 2));
   }

   public final void updateSpeedMinus() {
      Game.gameThread.playSpeed--;
      if (Game.gameThread.playSpeed < 1) {
         Game.gameThread.playSpeed = 1;
      }

      ButtonTopDate.ANIMATION_TIME = CFG.currentTimeMillis;
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Speed") + ": ", CFG.FONT_BOLD_SMALL));
      nData.add(
         new MenuElement_HoverElement_Type_Text(
            "" + Game.gameThread.playSpeed + "/" + Game.gameThread.playMaxSpeed, CFG.FONT_REGULAR_SMALL, Colors.HOVER_IMPORTANT
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      Game.menuManager.addToast(new Toast(nElements, 0, 1000, CFG.GAME_WIDTH, ImageManager.getImage(Images.topStats).getHeight() + CFG.PADDING * 2));
   }

   public final void updateSpeedPlus() {
      Game.gameThread.playSpeed++;
      if (Game.gameThread.playSpeed > Game.gameThread.playMaxSpeed) {
         Game.gameThread.playSpeed = Game.gameThread.playMaxSpeed;
      }

      ButtonTopDate.ANIMATION_TIME = CFG.currentTimeMillis;
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Speed") + ": ", CFG.FONT_BOLD_SMALL));
      nData.add(
         new MenuElement_HoverElement_Type_Text(
            "" + Game.gameThread.playSpeed + "/" + Game.gameThread.playMaxSpeed, CFG.FONT_REGULAR_SMALL, Colors.HOVER_IMPORTANT
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      Game.menuManager.addToast(new Toast(nElements, 0, 1000, CFG.GAME_WIDTH, ImageManager.getImage(Images.topStats).getHeight() + CFG.PADDING * 2));
   }

   public final void updateSieges() {
      try {
         SiegeManager.updateSieges();
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   public final void updateBattles() {
      for (int i = Game.battleManager.getBattleSize() - 1; i >= 0; i--) {
         try {
            Game.battleManager.getBattle(i).updateBattle();
            if (Game.battleManager.getBattle(i).endOfBattle()) {
               Game.battleManager.getBattle(i).updateBattle_Summary(true);

               try {
                  Game.getProvince(Game.battleManager.getBattle(i).provinceID).updateIsUnderSiege();
                  SiegeManager.checkForSiege(Game.battleManager.getBattle(i).provinceID);
               } catch (Exception var3) {
                  CFG.exceptionStack(var3);
               }

               int inProvinceID = Game.battleManager.getBattle(i).provinceID;
               Game.battleManager.removeBattle(i);
               AI_BattleEnd.battleEnded(inProvinceID);
            } else if (Game.battleManager.getBattle(i).endOfBattle_NoAttacks()) {
               Game.battleManager.getBattle(i).updateBattle_Summary(true);

               try {
                  Game.getProvince(Game.battleManager.getBattle(i).provinceID).updateIsUnderSiege();
                  SiegeManager.checkForSiege(Game.battleManager.getBattle(i).provinceID);
               } catch (Exception var4) {
                  CFG.exceptionStack(var4);
               }

               Game.battleManager.removeBattle(i);
            }
         } catch (Exception var5) {
            CFG.exceptionStack(var5);
         }
      }
   }

   public final void updateWars_WhitePeace() {
      try {
         if (Game_Calendar.TURN_ID % GameValues.war.GAME_UPDATE_WAR_AUTO_WHITE_PEACE == 0) {
            WarManager.updateWars_WhitePeace();
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   public final void updateWars_Peace() {
      try {
         if (Game_Calendar.TURN_ID % GameValues.war.GAME_UPDATE_WAR_AI_PEACE == 0) {
            WarManager.updateWars_Peace();
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   public final void addCivUpdateTotalIncomePerMonth(int iCivID) {
      try {
         if (this.civsUpdateTotalIncomePerMonth.contains(iCivID)) {
            return;
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }

      if (iCivID >= 1) {
         try {
            this.civsUpdateTotalIncomePerMonth.add(iCivID);
         } catch (Exception var3) {
            CFG.exceptionStack(var3);
         }
      }
   }

   public final void updateTotalIncomePerMonth() {
      try {
         for (int i = this.civsUpdateTotalIncomePerMonth.size() - 1; i >= 0; i--) {
            int nCivID = this.civsUpdateTotalIncomePerMonth.remove();
            Game.getCiv(nCivID).updateProvincesIncomeAndExpenses();
            Game.getCiv(nCivID).updateTotalIncomePerMonth();
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public final void addCivUpdateArmyMaintenance(int iCivID) {
      try {
         if (this.civsUpdateArmyMaintenance.contains(iCivID)) {
            return;
         }

         this.civsUpdateArmyMaintenance.add(iCivID);
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public final void updateArmyMaintenance() {
      try {
         for (int i = this.civsUpdateArmyMaintenance.size() - 1; i >= 0; i--) {
            int nCivID = this.civsUpdateArmyMaintenance.remove();
            Game.getCiv(nCivID).updateArmyMaintenance();
            Game.getCiv(nCivID).updateArmyRegimentSize();
            this.addCivUpdateTotalIncomePerMonth(nCivID);
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public final void addCivUpdateLoans(int iCivID) {
      try {
         if (this.civsUpdateLoans.contains(iCivID)) {
            return;
         }

         this.civsUpdateLoans.add(iCivID);
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public final void removeCivUpdateLoans(int iCivID) {
      try {
         this.civsUpdateLoans.remove(iCivID);
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public final void updateLoans() {
      try {
         for (Integer civID : this.civsUpdateLoans) {
            Game.getCiv(civID).updateLoans();
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public final void updateGold() {
      try {
         for (int i = 1; i < Game.getCivsSize(); i++) {
            try {
               if (Game.getCiv(i).getNumOfProvinces() > 0) {
                  Game.getCiv(i)
                     .addGold(
                        (Game.getCiv(i).fTotalIncomePerMonth + Game.getCiv(i).civBonuses.MonthlyIncome - Game.getCiv(i).fTotalExpensesPerMonth)
                           / Game_Calendar.UPDATE_NUM_OF_DAYS
                     );
               }
            } catch (Exception var3) {
               CFG.exceptionStack(var3);
            }
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   public final void updateGold_2() {
      try {
         if (Game.getCiv(Game.player.iCivID).getNumOfProvinces() > 0) {
            Game.getCiv(Game.player.iCivID)
               .addGold(
                  (
                        Game.getCiv(Game.player.iCivID).fTotalIncomePerMonth
                           + Game.getCiv(Game.player.iCivID).civBonuses.MonthlyIncome
                           - Game.getCiv(Game.player.iCivID).fTotalExpensesPerMonth
                     )
                     / Game_Calendar.UPDATE_NUM_OF_DAYS
               );
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }

      try {
         int i;
         for (i = 1 + Game_Calendar.TURN_ID % Game_Calendar.UPDATE_NUM_OF_DAYS_INT; i < Game.player.iCivID; i += Game_Calendar.UPDATE_NUM_OF_DAYS_INT) {
            if (Game.getCiv(i).getNumOfProvinces() > 0) {
               Game.getCiv(i).addGold(Game.getCiv(i).fTotalIncomePerMonth + Game.getCiv(i).civBonuses.MonthlyIncome - Game.getCiv(i).fTotalExpensesPerMonth);
            }
         }

         if (i == Game.player.iCivID) {
            i += Game_Calendar.UPDATE_NUM_OF_DAYS_INT;
         }

         for (; i < Game.getCivsSize(); i += Game_Calendar.UPDATE_NUM_OF_DAYS_INT) {
            if (Game.getCiv(i).getNumOfProvinces() > 0) {
               Game.getCiv(i).addGold(Game.getCiv(i).fTotalIncomePerMonth + Game.getCiv(i).civBonuses.MonthlyIncome - Game.getCiv(i).fTotalExpensesPerMonth);
            }
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public final void updateGold_Manpower_Legacy_Diplomacy() {
      try {
         Civilization civPlayer = Game.getCiv(Game.player.iCivID);
         if (civPlayer.getNumOfProvinces() > 0) {
            civPlayer.addGold(
               (civPlayer.fTotalIncomePerMonth + civPlayer.civBonuses.MonthlyIncome - civPlayer.fTotalExpensesPerMonth) / Game_Calendar.UPDATE_NUM_OF_DAYS
            );
            civPlayer.setManpower(civPlayer.fManpower + civPlayer.fManpowerPerMonth / Game_Calendar.UPDATE_NUM_OF_DAYS);
            civPlayer.addLegacy(civPlayer.getLegacyPerMonth() / Game_Calendar.UPDATE_NUM_OF_DAYS);
            civPlayer.fDiplomacy = Math.max(
               (float)GameValues.diplomacy.DIPLOMACY_POINTS_MIN,
               Math.min(civPlayer.fDiplomacyMax, civPlayer.fDiplomacy + civPlayer.getDiplomacyPerMonth() / Game_Calendar.UPDATE_NUM_OF_DAYS)
            );
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }

      try {
         int i;
         for (i = 1 + Game_Calendar.TURN_ID % Game_Calendar.UPDATE_NUM_OF_DAYS_INT; i < Game.player.iCivID; i += Game_Calendar.UPDATE_NUM_OF_DAYS_INT) {
            Civilization civ = Game.getCiv(i);
            if (civ.getNumOfProvinces() > 0) {
               civ.addGold(civ.fTotalIncomePerMonth + civ.civBonuses.MonthlyIncome - civ.fTotalExpensesPerMonth);
               civ.setManpower(civ.fManpower + civ.fManpowerPerMonth);
               civ.addLegacy(civ.getLegacyPerMonth());
               civ.fDiplomacy = Math.max(
                  (float)GameValues.diplomacy.DIPLOMACY_POINTS_MIN, Math.min(civ.fDiplomacyMax, civ.fDiplomacy + civ.getDiplomacyPerMonth())
               );
            }
         }

         if (i == Game.player.iCivID) {
            i += Game_Calendar.UPDATE_NUM_OF_DAYS_INT;
         }

         for (; i < Game.getCivsSize(); i += Game_Calendar.UPDATE_NUM_OF_DAYS_INT) {
            Civilization civ = Game.getCiv(i);
            if (civ.getNumOfProvinces() > 0) {
               civ.addGold(civ.fTotalIncomePerMonth + civ.civBonuses.MonthlyIncome - civ.fTotalExpensesPerMonth);
               civ.setManpower(civ.fManpower + civ.fManpowerPerMonth);
               civ.addLegacy(civ.getLegacyPerMonth());
               civ.fDiplomacy = Math.max(
                  (float)GameValues.diplomacy.DIPLOMACY_POINTS_MIN, Math.min(civ.fDiplomacyMax, civ.fDiplomacy + civ.getDiplomacyPerMonth())
               );
            }
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   public final void updateCivs_Advantages() {
   }

   public final void updateCivs_Laws() {
      try {
         int i;
         for (i = 1 + Game_Calendar.TURN_ID % GameValues.gameUpdateAI.GAME_UPDATE_AI_LAWS;
            i < Game.player.iCivID;
            i += GameValues.gameUpdateAI.GAME_UPDATE_AI_LAWS
         ) {
            if (Game.getCiv(i).getNumOfProvinces() > 0) {
               AI_Laws.adoptNewLaws(i);
            }
         }

         if (i == Game.player.iCivID) {
            i += GameValues.gameUpdateAI.GAME_UPDATE_AI_LAWS;
         }

         for (; i < Game.getCivsSize(); i += GameValues.gameUpdateAI.GAME_UPDATE_AI_LAWS) {
            if (Game.getCiv(i).getNumOfProvinces() > 0) {
               AI_Laws.adoptNewLaws(i);
            }
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   public static void updateRelations() {
      try {
         for (int i = Game_Calendar.TURN_ID % GameValues.gameUpdate.GAME_UPDATE_RELATIONS_TO_NEUTRAL + 1;
            i < Game.getCivsSize();
            i += GameValues.gameUpdate.GAME_UPDATE_RELATIONS_TO_NEUTRAL
         ) {
            if (Game.getCiv(i).getNumOfProvinces() > 0) {
               updateRelations(i);
            }
         }
      } catch (Exception var1) {
         CFG.exceptionStack(var1);
      }
   }

   public static void updateRelations(int civID) {
      Game.getCiv(civID).diplomacy.updateRelations_ToNeutral(civID);
   }

   public final void updateCivs_UpgradeUnits() {
      try {
         int i;
         for (i = 1 + Game_Calendar.TURN_ID % GameValues.gameUpdateAI.GAME_UPDATE_AI_UPGRADE_UNITS;
            i < Game.player.iCivID;
            i += GameValues.gameUpdateAI.GAME_UPDATE_AI_UPGRADE_UNITS
         ) {
            if (Game.getCiv(i).getNumOfProvinces() > 0) {
               ArmyManager.upgradeAllArmies(i);
            }
         }

         if (i == Game.player.iCivID) {
            i += GameValues.gameUpdateAI.GAME_UPDATE_AI_UPGRADE_UNITS;
         }

         for (; i < Game.getCivsSize(); i += GameValues.gameUpdateAI.GAME_UPDATE_AI_UPGRADE_UNITS) {
            if (Game.getCiv(i).getNumOfProvinces() > 0) {
               ArmyManager.upgradeAllArmies(i);
            }
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   public final void addCivUpdateResearchPerMonth(int iCivID) {
      try {
         if (this.civsUpdateResearchPerMonth.contains(iCivID)) {
            return;
         }

         this.civsUpdateResearchPerMonth.add(iCivID);
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public final void updateResearchPerMonth() {
      try {
         for (int i = this.civsUpdateResearchPerMonth.size() - 1; i >= 0; i--) {
            Game.getCiv(this.civsUpdateResearchPerMonth.remove()).updateResearchPerMonth();
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   public final void updateResearch() {
      try {
         for (int i = 1; i < Game.getCivsSize(); i++) {
            if (Game.getCiv(i).getNumOfProvinces() > 0) {
               if (Game.getCiv(i).getActiveTechResearch() >= 0) {
                  Game.getCiv(i)
                     .addResearchProgress(Game.getCiv(i).getActiveTechResearch(), Game.getCiv(i).fResearchPerMonth / Game_Calendar.UPDATE_NUM_OF_DAYS);
               } else {
                  AI_SelectTechnology.selectTechnology(i);
                  if (Game.getCiv(i).getActiveTechResearch() >= 0) {
                     Game.getCiv(i)
                        .addResearchProgress(Game.getCiv(i).getActiveTechResearch(), Game.getCiv(i).fResearchPerMonth / Game_Calendar.UPDATE_NUM_OF_DAYS);
                  } else {
                     try {
                        if (Game.getCiv(i).getAlternativeTechResearch() >= 0) {
                           Game.getCiv(i)
                              .addResearchProgress(
                                 Game.getCiv(i).getAlternativeTechResearch(), Game.getCiv(i).fResearchPerMonth / Game_Calendar.UPDATE_NUM_OF_DAYS
                              );
                        } else {
                           for (int j = 0; j < TechnologyTree.iTechnologySize; j++) {
                              if (Game.getCiv(i).getAvailableToResearch(j)) {
                                 Game.getCiv(i).setAlternativeTechResearch(j);
                                 Game.getCiv(i)
                                    .addResearchProgress(
                                       Game.getCiv(i).getAlternativeTechResearch(), Game.getCiv(i).fResearchPerMonth / Game_Calendar.UPDATE_NUM_OF_DAYS
                                    );
                                 break;
                              }
                           }
                        }
                     } catch (Exception var3) {
                        CFG.exceptionStack(var3);
                     }
                  }
               }
            }
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   public final void addCivUpdateLegacyPerMonth(int iCivID) {
      try {
         if (this.civsUpdateLegacyPerMonth.contains(iCivID)) {
            return;
         }

         this.civsUpdateLegacyPerMonth.add(iCivID);
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public final void updateLegacyPerMonth() {
      try {
         for (int i = this.civsUpdateLegacyPerMonth.size() - 1; i >= 0; i--) {
            Game.getCiv(this.civsUpdateLegacyPerMonth.remove()).updateLegacyPerMonth();
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   public final void updateLegacy() {
      try {
         for (int i = 1; i < Game.getCivsSize(); i++) {
            if (Game.getCiv(i).getNumOfProvinces() > 0) {
               Game.getCiv(i).addLegacy(Game.getCiv(i).getLegacyPerMonth() / Game_Calendar.UPDATE_NUM_OF_DAYS);
            }
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   public final void updateLegacy_2() {
      try {
         if (Game.getCiv(Game.player.iCivID).getNumOfProvinces() > 0) {
            Game.getCiv(Game.player.iCivID).addLegacy(Game.getCiv(Game.player.iCivID).getLegacyPerMonth() / Game_Calendar.UPDATE_NUM_OF_DAYS);
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }

      try {
         int i;
         for (i = 1 + Game_Calendar.TURN_ID % Game_Calendar.UPDATE_NUM_OF_DAYS_INT; i < Game.player.iCivID; i += Game_Calendar.UPDATE_NUM_OF_DAYS_INT) {
            if (Game.getCiv(i).getNumOfProvinces() > 0) {
               Game.getCiv(i).addLegacy(Game.getCiv(i).getLegacyPerMonth());
            }
         }

         if (i == Game.player.iCivID) {
            i += Game_Calendar.UPDATE_NUM_OF_DAYS_INT;
         }

         for (; i < Game.getCivsSize(); i += Game_Calendar.UPDATE_NUM_OF_DAYS_INT) {
            if (Game.getCiv(i).getNumOfProvinces() > 0) {
               Game.getCiv(i).addLegacy(Game.getCiv(i).getLegacyPerMonth());
            }
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public final void updateManpower() {
      try {
         for (int i = 1; i < Game.getCivsSize(); i++) {
            if (Game.getCiv(i).getNumOfProvinces() > 0) {
               Game.getCiv(i).setManpower(Game.getCiv(i).fManpower + Game.getCiv(i).fManpowerPerMonth / Game_Calendar.UPDATE_NUM_OF_DAYS);
            }
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   public final void updateManpower_2() {
      try {
         if (Game.getCiv(Game.player.iCivID).getNumOfProvinces() > 0) {
            Game.getCiv(Game.player.iCivID)
               .setManpower(Game.getCiv(Game.player.iCivID).fManpower + Game.getCiv(Game.player.iCivID).fManpowerPerMonth / Game_Calendar.UPDATE_NUM_OF_DAYS);
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }

      try {
         int i;
         for (i = 1 + Game_Calendar.TURN_ID % Game_Calendar.UPDATE_NUM_OF_DAYS_INT; i < Game.player.iCivID; i += Game_Calendar.UPDATE_NUM_OF_DAYS_INT) {
            if (Game.getCiv(i).getNumOfProvinces() > 0) {
               Game.getCiv(i).setManpower(Game.getCiv(i).fManpower + Game.getCiv(i).fManpowerPerMonth);
            }
         }

         if (i == Game.player.iCivID) {
            i += Game_Calendar.UPDATE_NUM_OF_DAYS_INT;
         }

         for (; i < Game.getCivsSize(); i += Game_Calendar.UPDATE_NUM_OF_DAYS_INT) {
            if (Game.getCiv(i).getNumOfProvinces() > 0) {
               Game.getCiv(i).setManpower(Game.getCiv(i).fManpower + Game.getCiv(i).fManpowerPerMonth);
            }
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public final void updateDiplomacyPoints() {
      try {
         for (int i = 1; i < Game.getCivsSize(); i++) {
            if (Game.getCiv(i).getNumOfProvinces() > 0) {
               Game.getCiv(i).fDiplomacy = Math.max(
                  (float)GameValues.diplomacy.DIPLOMACY_POINTS_MIN,
                  Math.min(Game.getCiv(i).fDiplomacyMax, Game.getCiv(i).fDiplomacy + Game.getCiv(i).getDiplomacyPerMonth() / Game_Calendar.UPDATE_NUM_OF_DAYS)
               );
            }
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   public final void updateDiplomacyPoints_2() {
      try {
         if (Game.getCiv(Game.player.iCivID).getNumOfProvinces() > 0) {
            Game.getCiv(Game.player.iCivID).fDiplomacy = Math.max(
               (float)GameValues.diplomacy.DIPLOMACY_POINTS_MIN,
               Math.min(
                  Game.getCiv(Game.player.iCivID).fDiplomacyMax,
                  Game.getCiv(Game.player.iCivID).fDiplomacy + Game.getCiv(Game.player.iCivID).getDiplomacyPerMonth() / Game_Calendar.UPDATE_NUM_OF_DAYS
               )
            );
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }

      try {
         int i;
         for (i = 1 + Game_Calendar.TURN_ID % Game_Calendar.UPDATE_NUM_OF_DAYS_INT; i < Game.player.iCivID; i += Game_Calendar.UPDATE_NUM_OF_DAYS_INT) {
            if (Game.getCiv(i).getNumOfProvinces() > 0) {
               Game.getCiv(i).fDiplomacy = Math.max(
                  (float)GameValues.diplomacy.DIPLOMACY_POINTS_MIN,
                  Math.min(Game.getCiv(i).fDiplomacyMax, Game.getCiv(i).fDiplomacy + Game.getCiv(i).getDiplomacyPerMonth())
               );
            }
         }

         if (i == Game.player.iCivID) {
            i += Game_Calendar.UPDATE_NUM_OF_DAYS_INT;
         }

         for (; i < Game.getCivsSize(); i += Game_Calendar.UPDATE_NUM_OF_DAYS_INT) {
            if (Game.getCiv(i).getNumOfProvinces() > 0) {
               Game.getCiv(i).fDiplomacy = Math.max(
                  (float)GameValues.diplomacy.DIPLOMACY_POINTS_MIN,
                  Math.min(Game.getCiv(i).fDiplomacyMax, Game.getCiv(i).fDiplomacy + Game.getCiv(i).getDiplomacyPerMonth())
               );
            }
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public final void updateAggressiveExpansion() {
      try {
         for (int i = Game_Calendar.TURN_ID % GameValues.aggressiveExpansion.GAME_UPDATE_AE_DECAY_TURNS;
            i < Game.getCivsSize();
            i += GameValues.aggressiveExpansion.GAME_UPDATE_AE_DECAY_TURNS
         ) {
            if (Game.getCiv(i).getAggressiveExpansion() > 0.0F) {
               Game.getCiv(i).decayAggressiveExpansion();
            }
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   public final void updateWarWeariness() {
      try {
         for (int i = Game_Calendar.TURN_ID % GameValues.warWeariness.GAME_UPDATE_WAR_WEARINESS_TICK_TURNS;
            i < Game.getCivsSize();
            i += GameValues.warWeariness.GAME_UPDATE_WAR_WEARINESS_TICK_TURNS
         ) {
            if (Game.getCiv(i).getNumOfProvinces() > 0) {
               if (!Game.getCiv(i).diplomacy.isAtWar()) {
                  Game.getCiv(i).updateWarWeariness(GameValues.warWeariness.WAR_WEARINESS_PER_TICK_AT_PEACE);
               } else {
                  boolean isAttacker = false;

                  for (int a = Game.getCiv(i).diplomacy.iWarsSize - 1; a >= 0; a--) {
                     if (WarManager.lWars.containsKey(Game.getCiv(i).diplomacy.lWars.get(a))
                        && WarManager.lWars.get(Game.getCiv(i).diplomacy.lWars.get(a)).isAggressor(i)) {
                        isAttacker = true;
                        break;
                     }
                  }

                  if (isAttacker) {
                     Game.getCiv(i).updateWarWeariness(GameValues.warWeariness.WAR_WEARINESS_PER_TICK_AT_WAR);
                  } else {
                     Game.getCiv(i).updateWarWeariness(GameValues.warWeariness.WAR_WEARINESS_PER_TICK_AT_WAR_DEFENDER);
                  }
               }
            }
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   public final void updateAutoAssimilation() {
      try {
         for (int i = Game_Calendar.TURN_ID % GameValues.province.AUTO_ASSIMILATION_UPDATE_STEPS;
            i < Game.getProvincesSize();
            i += GameValues.province.AUTO_ASSIMILATION_UPDATE_STEPS
         ) {
            if (Game.getProvince(i).getCivID() > 0) {
               Game.getProvince(i).autoAssimilate();
            }
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   public final void updatePopulation() {
      try {
         for (int i = Game_Calendar.TURN_ID % GameValues.gameUpdate.GAME_UPDATE_POPULATION_STEPS;
            i < Game.getProvincesSize();
            i += GameValues.gameUpdate.GAME_UPDATE_POPULATION_STEPS
         ) {
            if (Game.getProvince(i).getCivID() > 0) {
               Game.getProvince(i).updatePopulationGrowth();
            }
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   public final void updateArmyMorale_Reinforce() {
      try {
         for (int i = Game_Calendar.TURN_ID % GameValues.gameUpdate.GAME_UPDATE_ARMIES_MORALE_REINFORCE_STEPS + 1;
            i < Game.getCivsSize();
            i += GameValues.gameUpdate.GAME_UPDATE_ARMIES_MORALE_REINFORCE_STEPS
         ) {
            try {
               if (Game.getCiv(i).getNumOfProvinces() > 0) {
                  Game.getCiv(i).updateMorale_Reinforce();
               }
            } catch (Exception var3) {
               CFG.exceptionStack(var3);
            }
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   public final void updateCivilizationBonuses() {
      try {
         for (int i = Game_Calendar.TURN_ID % GameValues.gameUpdate.GAME_UPDATE_CIV_BONUSES + 1;
            i < Game.getCivsSize();
            i += GameValues.gameUpdate.GAME_UPDATE_CIV_BONUSES
         ) {
            Game.getCiv(i).updateCivilizationBonuses_Temporary();
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   public final void returnOccupiedProvinces_NotAtWar() {
      if (Game_Calendar.TURN_ID % GameValues.gameUpdate.GAME_UPDATE_RETURN_OCCUPIED_PROVINCES_NOT_AT_WAR == 0) {
         for (int i = 0; i < Game.getProvincesSize(); i++) {
            if (Game.getProvince(i).isOccupied()
               && Game.getProvinceData(i).getOccupiedByCivID() >= 0
               && !DiplomacyManager.isAtWar(Game.getProvince(i).getCivID(), Game.getProvinceData(i).getOccupiedByCivID())) {
               Game.getProvince(i).retakeOccupiedProvince_Peace();
            }
         }
      }
   }
}
