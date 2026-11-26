package aoc.kingdoms.lukasz.map.map;

import aoc.kingdoms.lukasz.jakowski.AA_KeyManager;
import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.SoundsManager;
import aoc.kingdoms.lukasz.jakowski.Touch;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.SiegeManager;
import aoc.kingdoms.lukasz.map.province.ProvinceBorderManager;
import aoc.kingdoms.lukasz.map.province.ProvinceDraw;
import aoc.kingdoms.lukasz.map.province.ProvinceDrawArmy;
import aoc.kingdoms.lukasz.map.province.ProvinceTouchExtraAction;
import aoc.kingdoms.lukasz.menusInGame.InGame_CivBonuses;
import aoc.kingdoms.lukasz.menusInGame.Battle.InGame_Battle;
import aoc.kingdoms.lukasz.menusInGame.Civ.InGame_Civ;
import aoc.kingdoms.lukasz.menusInGame.Province.InGame_ProvinceInfo;
import aoc.kingdoms.lukasz.menusInGame.Province.InGame_ProvinceSiege;
import aoc.kingdoms.lukasz.menusMapEditor.EditorMapContinents;
import aoc.kingdoms.lukasz.menusScenarioEditor.ScenarioAssign;
import aoc.kingdoms.lukasz.menusScenarioEditor.Wasteland.ScenarioWasteland;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MapTouchManager {
   public int iStartMovePosX;
   public int iStartMovePosY;
   protected static boolean reverseDirectionX = true;
   protected static boolean reverseDirectionY = true;
   public int actionDownPosX = 0;
   public int actionDownPosY = 0;
   public boolean updateStartMovePosX;
   public boolean updateStartMovePosY;
   private long lActionDownTime = 0L;
   public boolean disableMovingMap = false;
   public boolean enableSelectMode = true;
   public boolean selectMode = false;
   public int iSelectBoxX;
   public int iSelectBoxY;
   public int iSelectBoxWidth;
   public int iSelectBoxHeight;
   public MapTouchManager.ReverseDirection mapMoveDirectionX;
   public MapTouchManager.ReverseDirection mapMoveDirectionY;
   public MapTouchManager.ReverseDirection2 mapMoveDirectionX2;
   public MapTouchManager.ReverseDirection2 mapMoveDirectionY2;

   public MapTouchManager() {
      this.buildReversePos();
   }

   public final void drawSelectMode(SpriteBatch oSB) {
      if (this.selectMode) {
         try {
            int nX = this.iSelectBoxX;
            if (this.iSelectBoxWidth == 0) {
               this.iSelectBoxWidth = 1;
            } else if (this.iSelectBoxWidth < 0) {
               nX += this.iSelectBoxWidth;
            }

            int nY = this.iSelectBoxY;
            if (this.iSelectBoxHeight == 0) {
               this.iSelectBoxHeight = 1;
            } else if (this.iSelectBoxHeight < 0) {
               nY += this.iSelectBoxHeight;
            }

            oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.075F));
            Images.pix.draw(oSB, nX, nY, Math.abs(this.iSelectBoxWidth), Math.abs(this.iSelectBoxHeight));
            oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.275F));
            Renderer.drawBox2(oSB, nX, nY, Math.abs(this.iSelectBoxWidth), Math.abs(this.iSelectBoxHeight), 1.0F);
            oSB.setColor(Color.WHITE);
         } catch (Exception var4) {
            CFG.exceptionStack(var4);
         }
      }
   }

   public final void actionDown(int nPosX, int nPosY, int nPointer, int button) {
      try {
         this.setActionDownPosXY(nPosX, nPosY);
         if (this.enableSelectMode && button == Game.settingsManager.MAP_SELECT_ARMY_BUTTON) {
            this.iSelectBoxX = nPosX;
            this.iSelectBoxY = nPosY;
            this.iSelectBoxWidth = 1;
            this.iSelectBoxHeight = 1;
            this.selectMode = true;
            Game.setCursorDrag();
            return;
         }

         this.selectMode = false;
         if (Game.mapScroll.getScrollingTheMap()) {
            Game.mapScroll.stopScrollingTheMap();
         }

         try {
            ProvinceTouchExtraAction.actionDown_ExtraAction.extraAction(nPosX, nPosY, nPointer, button);
         } catch (Exception var6) {
            CFG.exceptionStack(var6);
         }
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }
   }

   public final void actionMove(int nPosX, int nPosY, int nPointer) {
      try {
         if (CFG.brushTool && Touch.buttonTouch == 0) {
            this.actionDownPosX = nPosX;
            this.actionDownPosY = nPosY;
            Game.gameActiveProvince.actionUp_setActiveProvinceID(nPosX, nPosY, nPointer, 0);
            return;
         }

         if (this.selectMode) {
            this.iSelectBoxWidth = nPosX - this.iSelectBoxX;
            this.iSelectBoxHeight = nPosY - this.iSelectBoxY;
            return;
         }

         this.actionMoveMap(nPosX, nPosY, nPointer);
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
      }
   }

   protected final void actionMoveMap(int nPosX, int nPosY, int nPointer) {
      try {
         if (!Game.mapTouchManager.disableMovingMap) {
            if (this.updateStartMovePosX || this.updateStartMovePosY) {
               this.updateStartMovePosXY(nPosX, nPosY);
               this.updateStartMovePosX = false;
               this.updateStartMovePosY = false;
            }

            Game.mapCoords.setNewPosX(this.mapMoveDirectionX2.getNewPos(this.iStartMovePosX, (int)(nPosX / Game.mapScale.getCurrentScale())));
            Game.mapCoords.setNewPosY(this.mapMoveDirectionY2.getNewPos(this.iStartMovePosY, (int)(nPosY / Game.mapScale.getCurrentScale())));
         } else {
            try {
               ProvinceTouchExtraAction.actionMove_ExtraAction.extraAction(nPosX, nPosY, nPointer, 0);
            } catch (Exception var5) {
               CFG.exceptionStack(var5);
            }
         }
      } catch (Exception var6) {
         CFG.exceptionStack(var6);
      }
   }

   public final void actionMove(int nPosX, int nPosY, int nPosX2, int nPosY2) {
      try {
         if (!Game.mapCoords.disableMovingMap && Game.mapScale.getEnableScaling()) {
            if (Game.mapScale.getStartScalePosY() <= 0) {
               Game.mapScale.startScaleTheMap(nPosX, nPosX2, nPosY, nPosY2);
            } else {
               Game.mapScale.scaleTheMap(nPosX, nPosX2, nPosY, nPosY2);
            }
         }
      } catch (Exception var6) {
         CFG.exceptionStack(var6);
      }
   }

   public final void actionUp(int nPosX, int nPosY, int nPointer, int button) {
      try {
         if (Game.mapScale.getEnableScaling() && (button == Game.settingsManager.MAP_MOVE || button == 1)) {
            Game.mapScale.resetScaleOfMap(CFG.currentTimeMillis);
         }

         if (this.selectMode && (button == Game.settingsManager.MAP_SELECT_ARMY_BUTTON || !CFG.isDesktop())) {
            this.selectMode = false;
            Game.setCursorDefault();
            this.actionUpSelectMode(nPosX, nPosY);
            return;
         }

         if (Game.menuManager.getInGame() && !Game.chooseProvinceMode && Math.abs(nPosX - this.actionDownPosX) < CFG.PADDING / 2) {
            if (button == 1) {
               if (Game.activeArmySize > 0) {
                  Game.gameActiveProvince.setProvinceID_PPM(nPosX, nPosY);
                  return;
               }

               if (Game.mapModes.iActiveMapModeID != Game.mapModes.MODE_RECRUIT_ARMY
                  && Game.mapModes.iActiveMapModeID != Game.mapModes.MODE_BUILDING
                  && Game.mapModes.iActiveMapModeID != Game.mapModes.MODE_NEW_ARMY_CHOOSE_PROVINCE
                  && Game.mapModes.iActiveMapModeID != Game.mapModes.MODE_NUKE_CHOOSE_PROVINCE
                  && Game.mapModes.iActiveMapModeID != Game.mapModes.MODE_SELL_PROVINCES
                  && Game.mapModes.iActiveMapModeID != Game.mapModes.MODE_COLONIZE_CHOOSE_PROVINCE
                  && Game.mapModes.iActiveMapModeID != Game.mapModes.MODE_WARS
                  && Game.mapModes.iActiveMapModeID != Game.mapModes.MODE_MERCENARIES_CHOOSE_PROVINCE
                  && Game.mapModes.iActiveMapModeID != Game.mapModes.MODE_PEACE_VIEW) {
                  if (Game.iHoveredProvinceID >= 0) {
                     Game.setActiveProvinceID(Game.iHoveredProvinceID);
                     ProvinceBorderManager.action.setProvinceID(Game.iActiveProvince);
                  }

                  if (Game.menuManager.getVisibleInGame_Civ()) {
                     if (Game.iActiveProvince >= 0 && Game.getProvince(Game.iActiveProvince).getCivID() == InGame_Civ.iActiveCivID) {
                        Game.menuManager.setVisibleInGame_Civ(false);
                        Game.gameActiveProvince.resetLastActiveProvince();
                        Game.setActiveProvinceID(-1);
                     } else {
                        Game.menuManager.rebuildInGame_Civ();
                        InGame_Civ.lTime = 0L;
                        Game.soundsManager.playSound(Game.soundsManager.getDiplomacy());
                        if (Game.menuManager.getVisibleInGame_CivBonuses()
                           && InGame_Civ.iActiveCivID != 0
                           && InGame_CivBonuses.iCivID != InGame_Civ.iActiveCivID) {
                           InGame_CivBonuses.iCivID = InGame_Civ.iActiveCivID;
                           Game.menuManager.rebuildInGame_CivBonuses();
                           Game.menuManager.setVisibleInGame_CivBonuses(true);
                           InGame_CivBonuses.lTime = 0L;
                        }
                     }
                  } else {
                     Game.menuManager.rebuildInGame_Civ();
                     Game.soundsManager.playSound(Game.soundsManager.getDiplomacy());
                     if (Game.menuManager.getVisibleInGame_CivBonuses() && InGame_Civ.iActiveCivID != 0 && InGame_CivBonuses.iCivID != InGame_Civ.iActiveCivID) {
                        InGame_CivBonuses.iCivID = InGame_Civ.iActiveCivID;
                        Game.menuManager.rebuildInGame_CivBonuses();
                        Game.menuManager.setVisibleInGame_CivBonuses(true);
                        InGame_CivBonuses.lTime = 0L;
                     }
                  }

                  return;
               }
            } else if (Game.mapModes.iActiveMapModeID != Game.mapModes.MODE_RECRUIT_ARMY
               && Game.mapModes.iActiveMapModeID != Game.mapModes.MODE_NEW_ARMY_CHOOSE_PROVINCE
               && Game.mapModes.iActiveMapModeID != Game.mapModes.MODE_NUKE_CHOOSE_PROVINCE
               && Game.mapModes.iActiveMapModeID != Game.mapModes.MODE_SELL_PROVINCES
               && Game.mapModes.iActiveMapModeID != Game.mapModes.MODE_COLONIZE_CHOOSE_PROVINCE
               && Game.mapModes.iActiveMapModeID != Game.mapModes.MODE_MERCENARIES_CHOOSE_PROVINCE
               && Game.mapModes.iActiveMapModeID != Game.mapModes.MODE_INVEST_IN_ECONOMY
               && Game.mapModes.iActiveMapModeID != Game.mapModes.MODE_DEVELOP_INFRASTRUCTURE
               && Game.mapModes.iActiveMapModeID != Game.mapModes.MODE_INCREASE_TAX_EFFICIENCY
               && Game.mapModes.iActiveMapModeID != Game.mapModes.MODE_INCREASE_MANPOWER
               && Game.mapModes.iActiveMapModeID != Game.mapModes.MODE_MOVE_CAPITAL
               && Game.mapModes.iActiveMapModeID != Game.mapModes.MODE_INCREASE_GROWTH_RATE
               && Game.mapModes.iActiveMapModeID != Game.mapModes.MODE_BUILDING
               && Game.mapModes.iActiveMapModeID != Game.mapModes.MODE_CONVERT_RELIGION
               && Game.mapModes.iActiveMapModeID != Game.mapModes.MODE_CORE
               && Game.mapModes.iActiveMapModeID != Game.mapModes.MODE_PEACE_VIEW
               && Game.mapScale.getCurrentScale() >= Game.DRAW_ARMY_MIN_SCALE
               && Renderer.drawArmyInProvince) {
               if (CFG.isDesktop()) {
                  if (Game.hoveredBattle.iProvinceID >= 0) {
                     int tBattleID = Game.battleManager.getBattleID(Game.hoveredBattle.iProvinceID, Game.hoveredBattle.key);
                     if (Touch.isMouseOverBattle(nPosX, nPosY, tBattleID)) {
                        InGame_Battle.iProvinceID = Game.hoveredBattle.iProvinceID;
                        InGame_Battle.key = Game.hoveredBattle.key;
                        if (Game.menuManager.getVisibleInGame_Siege()) {
                           Game.menuManager.setVisibleInGame_Siege(false);
                        }

                        if (Game.menuManager.getVisibleInGame_War()) {
                           Game.menuManager.setVisibleInGame_War(false);
                        }

                        Game.menuManager.showInGame_Battle_HideMenus();
                        Game.menuManager.rebuildInGame_Battle();
                        Game.gameActiveProvince.resetLastActiveProvince();
                        Game.setActiveProvinceID(-1);
                        Game.clearActiveArmy();
                        return;
                     }
                  } else if (Game.hoveredSiegeID >= 0) {
                     if (Touch.isMouseOverSiege(nPosX, nPosY, Game.hoveredSiegeID)) {
                        if (Game.menuManager.getVisibleInGame_Siege() && InGame_ProvinceSiege.iProvinceID == Game.hoveredSiegeID) {
                           Game.menuManager.setVisibleInGame_Siege(false);
                        } else {
                           InGame_ProvinceSiege.iProvinceID = Game.hoveredSiegeID;
                           if (Game.menuManager.getVisibleInGame_Battle()) {
                              Game.menuManager.setVisibleInGame_Battle(false);
                           }

                           if (Game.menuManager.getVisibleInGame_War()) {
                              Game.menuManager.setVisibleInGame_War(false);
                           }

                           Game.menuManager.showInGame_Battle_HideMenus();
                           Game.menuManager.rebuildInGame_Siege();
                           Game.soundsManager.playSound(SoundsManager.SIEGE);
                           Game.gameActiveProvince.resetLastActiveProvince();
                           Game.setActiveProvinceID(-1);
                           Game.clearActiveArmy();
                        }

                        return;
                     }
                  } else if (Game.hoveredArmy.iProvinceID >= 0) {
                     if (Touch.isMouseOverArmy(nPosX, nPosY, Game.hoveredArmy.iProvinceID, Game.hoveredArmy.iArmyID)) {
                        boolean selectAllArmiesOfCivInProvince = false;
                        if (Game.activeArmy.size() == 1
                           && Game.getProvince(Game.hoveredArmy.iProvinceID).getArmySize() > 1
                           && Game.activeArmy.get(0).key.equals(Game.getProvince(Game.hoveredArmy.iProvinceID).getArmy(Game.hoveredArmy.iArmyID).key)) {
                           selectAllArmiesOfCivInProvince = true;
                        }

                        if (AA_KeyManager.CTRL_HOLD) {
                           Game.HoveredArmy nHA = new Game.HoveredArmy();
                           nHA.key = Game.getProvince(Game.hoveredArmy.iProvinceID).getArmy(Game.hoveredArmy.iArmyID).key;
                           nHA.iCivID = Game.getProvince(Game.hoveredArmy.iProvinceID).getArmy(Game.hoveredArmy.iArmyID).civID;
                           nHA.iProvinceID = Game.hoveredArmy.iProvinceID;
                           nHA.iArmyID = Game.hoveredArmy.iArmyID;
                           if (Game.isAlreadyActiveArmy(nHA.key)) {
                              Game.removeActiveArmy(nHA.key);
                           } else {
                              Game.addActiveArmy(nHA);
                           }

                           ProvinceTouchExtraAction.actionUp_SetActiveArmy();
                        } else {
                           Game.clearActiveArmy();
                           if (selectAllArmiesOfCivInProvince) {
                              for (int i = 0; i < Game.getProvince(Game.hoveredArmy.iProvinceID).getArmySize(); i++) {
                                 if (Game.getProvince(Game.hoveredArmy.iProvinceID).getArmy(i).civID
                                    == Game.getProvince(Game.hoveredArmy.iProvinceID).getArmy(Game.hoveredArmy.iArmyID).civID) {
                                    Game.HoveredArmy nHA = new Game.HoveredArmy();
                                    nHA.key = Game.getProvince(Game.hoveredArmy.iProvinceID).getArmy(i).key;
                                    nHA.iCivID = Game.getProvince(Game.hoveredArmy.iProvinceID).getArmy(i).civID;
                                    nHA.iProvinceID = Game.hoveredArmy.iProvinceID;
                                    nHA.iArmyID = i;
                                    Game.addActiveArmy(nHA);
                                    ProvinceTouchExtraAction.actionUp_SetActiveArmy();
                                 }
                              }
                           } else {
                              Game.HoveredArmy nHA = new Game.HoveredArmy();
                              nHA.key = Game.getProvince(Game.hoveredArmy.iProvinceID).getArmy(Game.hoveredArmy.iArmyID).key;
                              nHA.iCivID = Game.getProvince(Game.hoveredArmy.iProvinceID).getArmy(Game.hoveredArmy.iArmyID).civID;
                              nHA.iProvinceID = Game.hoveredArmy.iProvinceID;
                              nHA.iArmyID = Game.hoveredArmy.iArmyID;
                              Game.addActiveArmy(nHA);
                              ProvinceTouchExtraAction.actionUp_SetActiveArmy();
                           }
                        }

                        Game.gameActiveProvince.resetLastActiveProvince();
                        Game.setActiveProvinceID(-1);
                        Game.animationHover.resetAnimationData();
                        return;
                     }
                  } else if (Game.iHoveredCapitalProvinceID >= 0 && Touch.isMouseOverCapitalProvince_Flag(nPosX, nPosY, Game.iHoveredCapitalProvinceID)) {
                     Game.gameActiveProvince.resetLastActiveProvince();
                     Game.setActiveProvinceID(Game.iHoveredCapitalProvinceID);
                     ProvinceBorderManager.action.setProvinceID(Game.iActiveProvince);
                     Game.animationHover.resetAnimationData();
                     if (Game.menuManager.getVisibleInGame_Civ()) {
                        if (InGame_Civ.iActiveCivID != Game.getProvince(Game.iHoveredCapitalProvinceID).getCivID()) {
                           Game.menuManager.rebuildInGame_Civ();
                           InGame_Civ.lTime = 0L;
                           if (Game.menuManager.getVisibleInGame_CivBonuses()
                              && Game.getProvince(Game.iHoveredCapitalProvinceID).getCivID() != 0
                              && InGame_CivBonuses.iCivID != Game.getProvince(Game.iHoveredCapitalProvinceID).getCivID()) {
                              InGame_CivBonuses.iCivID = Game.getProvince(Game.iHoveredCapitalProvinceID).getCivID();
                              Game.menuManager.rebuildInGame_CivBonuses();
                              Game.menuManager.setVisibleInGame_CivBonuses(true);
                              InGame_CivBonuses.lTime = 0L;
                           }

                           Game.soundsManager.playSound(Game.soundsManager.getDiplomacy());
                        } else {
                           Game.menuManager.setVisibleInGame_Civ(false);
                           Game.soundsManager.playSound(SoundsManager.SOUND_CLICK_TOP);
                        }
                     } else {
                        Game.menuManager.rebuildInGame_Civ();
                        if (Game.menuManager.getVisibleInGame_CivBonuses()
                           && Game.getProvince(Game.iHoveredCapitalProvinceID).getCivID() != 0
                           && InGame_CivBonuses.iCivID != Game.getProvince(Game.iHoveredCapitalProvinceID).getCivID()) {
                           InGame_CivBonuses.iCivID = Game.getProvince(Game.iHoveredCapitalProvinceID).getCivID();
                           Game.menuManager.rebuildInGame_CivBonuses();
                           Game.menuManager.setVisibleInGame_CivBonuses(true);
                           InGame_CivBonuses.lTime = 0L;
                        }

                        Game.soundsManager.playSound(Game.soundsManager.getDiplomacy());
                     }

                     Game.gameActiveProvince.resetLastActiveProvince();
                     Game.setActiveProvinceID(-1);
                     return;
                  }
               } else {
                  for (int ix = 0; ix < Game.battleManager.getBattleSize(); ix++) {
                     if (Touch.isMouseOverBattle(nPosX, nPosY, ix)) {
                        InGame_Battle.iProvinceID = Game.battleManager.getBattle(ix).provinceID;
                        InGame_Battle.key = Game.battleManager.getBattle(ix).key;
                        if (Game.menuManager.getVisibleInGame_Siege()) {
                           Game.menuManager.setVisibleInGame_Siege(false);
                        }

                        if (Game.menuManager.getVisibleInGame_War()) {
                           Game.menuManager.setVisibleInGame_War(false);
                        }

                        Game.menuManager.showInGame_Battle_HideMenus();
                        Game.menuManager.rebuildInGame_Battle();
                        Game.gameActiveProvince.resetLastActiveProvince();
                        Game.setActiveProvinceID(-1);
                        Game.clearActiveArmy();
                        return;
                     }
                  }

                  for (int ixx = 0; ixx < SiegeManager.iProvincesSize; ixx++) {
                     if (Touch.isMouseOverSiege(nPosX, nPosY, SiegeManager.lProvinces.get(ixx))) {
                        if (Game.menuManager.getVisibleInGame_Siege() && InGame_ProvinceSiege.iProvinceID == SiegeManager.lProvinces.get(ixx)) {
                           Game.menuManager.setVisibleInGame_Siege(false);
                        } else {
                           InGame_ProvinceSiege.iProvinceID = SiegeManager.lProvinces.get(ixx);
                           if (Game.menuManager.getVisibleInGame_Battle()) {
                              Game.menuManager.setVisibleInGame_Battle(false);
                           }

                           if (Game.menuManager.getVisibleInGame_War()) {
                              Game.menuManager.setVisibleInGame_War(false);
                           }

                           Game.menuManager.showInGame_Battle_HideMenus();
                           Game.menuManager.rebuildInGame_Siege();
                           Game.soundsManager.playSound(SoundsManager.SIEGE);
                           Game.gameActiveProvince.resetLastActiveProvince();
                           Game.setActiveProvinceID(-1);
                           Game.clearActiveArmy();
                        }

                        return;
                     }
                  }

                  for (int ixxx = 0; ixxx < Game.NUM_OF_PROVINCES_IN_VIEW; ixxx++) {
                     if (Game.getProvince(Game.getProvinceInViewID(ixxx)).getFogDrawArmy()) {
                        for (int j = 0; j < Game.getProvince(Game.getProvinceInViewID(ixxx)).getArmySize(); j++) {
                           if (!Game.getProvince(Game.getProvinceInViewID(ixxx)).getArmy(j).inBattle
                              && Touch.isMouseOverArmy(nPosX, nPosY, Game.getProvinceInViewID(ixxx), j)) {
                              Game.clearActiveArmy();
                              Game.HoveredArmy nHA = new Game.HoveredArmy();
                              nHA.key = Game.getProvince(Game.getProvinceInViewID(ixxx)).getArmy(j).key;
                              nHA.iCivID = Game.getProvince(Game.getProvinceInViewID(ixxx)).getArmy(j).civID;
                              nHA.iProvinceID = Game.getProvinceInViewID(ixxx);
                              nHA.iArmyID = j;
                              Game.addActiveArmy(nHA);
                              ProvinceTouchExtraAction.actionUp_SetActiveArmy();
                              return;
                           }
                        }
                     }
                  }

                  for (int ixxxx = 0; ixxxx < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; ixxxx++) {
                     if (Game.getProvince(Game.getExtraProvinceInViewID(ixxxx)).getFogDrawArmy()) {
                        for (int jx = 0; jx < Game.getProvince(Game.getExtraProvinceInViewID(ixxxx)).getArmySize(); jx++) {
                           if (!Game.getProvince(Game.getExtraProvinceInViewID(ixxxx)).getArmy(jx).inBattle
                              && Touch.isMouseOverArmy(nPosX, nPosY, Game.getExtraProvinceInViewID(ixxxx), jx)) {
                              Game.clearActiveArmy();
                              Game.HoveredArmy nHA = new Game.HoveredArmy();
                              nHA.key = Game.getProvince(Game.getExtraProvinceInViewID(ixxxx)).getArmy(jx).key;
                              nHA.iCivID = Game.getProvince(Game.getExtraProvinceInViewID(ixxxx)).getArmy(jx).civID;
                              nHA.iProvinceID = Game.getExtraProvinceInViewID(ixxxx);
                              nHA.iArmyID = jx;
                              Game.addActiveArmy(nHA);
                              ProvinceTouchExtraAction.actionUp_SetActiveArmy();
                              return;
                           }
                        }
                     }
                  }

                  for (int ixxxxx = 0; ixxxxx < Game.NUM_OF_SEA_PROVINCES_IN_VIEW; ixxxxx++) {
                     if (Game.getProvince(Game.getSeaProvinceInViewID(ixxxxx)).getFogDrawArmy()) {
                        for (int jxx = 0; jxx < Game.getProvince(Game.getSeaProvinceInViewID(ixxxxx)).getArmySize(); jxx++) {
                           if (!Game.getProvince(Game.getSeaProvinceInViewID(ixxxxx)).getArmy(jxx).inBattle
                              && Touch.isMouseOverArmy(nPosX, nPosY, Game.getSeaProvinceInViewID(ixxxxx), jxx)) {
                              Game.clearActiveArmy();
                              Game.HoveredArmy nHA = new Game.HoveredArmy();
                              nHA.key = Game.getProvince(Game.getSeaProvinceInViewID(ixxxxx)).getArmy(jxx).key;
                              nHA.iCivID = Game.getProvince(Game.getSeaProvinceInViewID(ixxxxx)).getArmy(jxx).civID;
                              nHA.iProvinceID = Game.getSeaProvinceInViewID(ixxxxx);
                              nHA.iArmyID = jxx;
                              Game.addActiveArmy(nHA);
                              ProvinceTouchExtraAction.actionUp_SetActiveArmy();
                              return;
                           }
                        }
                     }
                  }

                  for (int ixxxxxx = 0; ixxxxxx < Game.NUM_OF_WASTELAND_PROVINCES_IN_VIEW; ixxxxxx++) {
                     if (Game.getProvince(Game.getWastelandProvinceInViewID(ixxxxxx)).getFogDrawArmy()) {
                        for (int jxxx = 0; jxxx < Game.getProvince(Game.getWastelandProvinceInViewID(ixxxxxx)).getArmySize(); jxxx++) {
                           if (!Game.getProvince(Game.getWastelandProvinceInViewID(ixxxxxx)).getArmy(jxxx).inBattle
                              && Touch.isMouseOverArmy(nPosX, nPosY, Game.getWastelandProvinceInViewID(ixxxxxx), jxxx)) {
                              Game.clearActiveArmy();
                              Game.HoveredArmy nHA = new Game.HoveredArmy();
                              nHA.key = Game.getProvince(Game.getWastelandProvinceInViewID(ixxxxxx)).getArmy(jxxx).key;
                              nHA.iCivID = Game.getProvince(Game.getWastelandProvinceInViewID(ixxxxxx)).getArmy(jxxx).civID;
                              nHA.iProvinceID = Game.getWastelandProvinceInViewID(ixxxxxx);
                              nHA.iArmyID = jxxx;
                              Game.addActiveArmy(nHA);
                              ProvinceTouchExtraAction.actionUp_SetActiveArmy();
                              return;
                           }
                        }
                     }
                  }

                  for (int ixxxxxxx = 0; ixxxxxxx < Game.NUM_OF_PROVINCES_IN_VIEW; ixxxxxxx++) {
                     if (Game.getProvince(Game.getProvinceInViewID(ixxxxxxx)).isCapital
                        && Game.getProvince(Game.getProvinceInViewID(ixxxxxxx)).getCitiesSize() > 0
                        && Touch.isMouseOverCapitalProvince_Flag(nPosX, nPosY, Game.getProvinceInViewID(ixxxxxxx))) {
                        Game.gameActiveProvince.resetLastActiveProvince();
                        Game.iHoveredCapitalProvinceID = Game.getProvinceInViewID(ixxxxxxx);
                        Game.setActiveProvinceID(Game.iHoveredCapitalProvinceID);
                        ProvinceBorderManager.action.setProvinceID(Game.iActiveProvince);
                        Game.animationHover.resetAnimationData();
                        if (Game.menuManager.getVisibleInGame_Civ()) {
                           if (InGame_Civ.iActiveCivID != Game.getProvince(Game.iHoveredCapitalProvinceID).getCivID()) {
                              Game.menuManager.rebuildInGame_Civ();
                              InGame_Civ.lTime = 0L;
                              if (Game.menuManager.getVisibleInGame_CivBonuses()
                                 && Game.getProvince(Game.iHoveredCapitalProvinceID).getCivID() != 0
                                 && InGame_CivBonuses.iCivID != Game.getProvince(Game.iHoveredCapitalProvinceID).getCivID()) {
                                 InGame_CivBonuses.iCivID = Game.getProvince(Game.iHoveredCapitalProvinceID).getCivID();
                                 Game.menuManager.rebuildInGame_CivBonuses();
                                 Game.menuManager.setVisibleInGame_CivBonuses(true);
                                 InGame_CivBonuses.lTime = 0L;
                              }

                              Game.soundsManager.playSound(Game.soundsManager.getDiplomacy());
                           } else {
                              Game.menuManager.setVisibleInGame_Civ(false);
                              Game.soundsManager.playSound(SoundsManager.SOUND_CLICK_TOP);
                           }
                        } else {
                           Game.menuManager.rebuildInGame_Civ();
                           if (Game.menuManager.getVisibleInGame_CivBonuses()
                              && Game.getProvince(Game.iHoveredCapitalProvinceID).getCivID() != 0
                              && InGame_CivBonuses.iCivID != Game.getProvince(Game.iHoveredCapitalProvinceID).getCivID()) {
                              InGame_CivBonuses.iCivID = Game.getProvince(Game.iHoveredCapitalProvinceID).getCivID();
                              Game.menuManager.rebuildInGame_CivBonuses();
                              Game.menuManager.setVisibleInGame_CivBonuses(true);
                              InGame_CivBonuses.lTime = 0L;
                           }

                           Game.soundsManager.playSound(Game.soundsManager.getDiplomacy());
                        }

                        Game.gameActiveProvince.resetLastActiveProvince();
                        Game.setActiveProvinceID(-1);
                        return;
                     }
                  }

                  for (int ixxxxxxxx = 0; ixxxxxxxx < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; ixxxxxxxx++) {
                     if (Game.getProvince(Game.getExtraProvinceInViewID(ixxxxxxxx)).isCapital
                        && Game.getProvince(Game.getExtraProvinceInViewID(ixxxxxxxx)).getCitiesSize() > 0
                        && Touch.isMouseOverCapitalProvince_Flag(nPosX, nPosY, Game.getExtraProvinceInViewID(ixxxxxxxx))) {
                        Game.gameActiveProvince.resetLastActiveProvince();
                        Game.iHoveredCapitalProvinceID = Game.getExtraProvinceInViewID(ixxxxxxxx);
                        Game.setActiveProvinceID(Game.iHoveredCapitalProvinceID);
                        ProvinceBorderManager.action.setProvinceID(Game.iActiveProvince);
                        Game.animationHover.resetAnimationData();
                        if (Game.menuManager.getVisibleInGame_Civ()) {
                           if (InGame_Civ.iActiveCivID != Game.getProvince(Game.iHoveredCapitalProvinceID).getCivID()) {
                              Game.menuManager.rebuildInGame_Civ();
                              InGame_Civ.lTime = 0L;
                              if (Game.menuManager.getVisibleInGame_CivBonuses()
                                 && Game.getProvince(Game.iHoveredCapitalProvinceID).getCivID() != 0
                                 && InGame_CivBonuses.iCivID != Game.getProvince(Game.iHoveredCapitalProvinceID).getCivID()) {
                                 InGame_CivBonuses.iCivID = Game.getProvince(Game.iHoveredCapitalProvinceID).getCivID();
                                 Game.menuManager.rebuildInGame_CivBonuses();
                                 Game.menuManager.setVisibleInGame_CivBonuses(true);
                                 InGame_CivBonuses.lTime = 0L;
                              }

                              Game.soundsManager.playSound(Game.soundsManager.getDiplomacy());
                           } else {
                              Game.menuManager.setVisibleInGame_Civ(false);
                              Game.soundsManager.playSound(SoundsManager.SOUND_CLICK_TOP);
                           }
                        } else {
                           Game.menuManager.rebuildInGame_Civ();
                           if (Game.menuManager.getVisibleInGame_CivBonuses()
                              && Game.getProvince(Game.iHoveredCapitalProvinceID).getCivID() != 0
                              && InGame_CivBonuses.iCivID != Game.getProvince(Game.iHoveredCapitalProvinceID).getCivID()) {
                              InGame_CivBonuses.iCivID = Game.getProvince(Game.iHoveredCapitalProvinceID).getCivID();
                              Game.menuManager.rebuildInGame_CivBonuses();
                              Game.menuManager.setVisibleInGame_CivBonuses(true);
                              InGame_CivBonuses.lTime = 0L;
                           }

                           Game.soundsManager.playSound(Game.soundsManager.getDiplomacy());
                        }

                        Game.gameActiveProvince.resetLastActiveProvince();
                        Game.setActiveProvinceID(-1);
                        return;
                     }
                  }
               }
            }
         }

         if (button == Game.settingsManager.MAP_MOVE || button == 1 && this.enableRightClick()) {
            if (!Game.menuManager.getInGame()) {
               Game.gameActiveProvince.actionUp_setActiveProvinceID(nPosX, nPosY, nPointer, button);
            } else if (!(Game.mapScale.getCurrentScale() >= Game.DRAW_CIV_NAMES_START_DRAWING_MAP_SCALE)
               && Game.iHoveredProvinceID >= 0
               && (Game.iHoveredProvinceID < 0 || Game.getProvince(Game.iHoveredProvinceID).getCivID() != 0)) {
               if (Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_DEFAULT) {
                  if (Math.abs(nPosX - this.actionDownPosX) < CFG.PADDING / 2) {
                     if (Game.regroupArmyMode || Game.chooseProvinceMode) {
                        Game.gameActiveProvince.actionUp_setActiveProvinceID(nPosX, nPosY, nPointer, button);
                     } else if (Game.invasionArmyMode) {
                        Game.gameActiveProvince.actionUp_setActiveProvinceID(nPosX, nPosY, nPointer, button);
                     } else {
                        Game.setActiveProvinceID(Game.iHoveredProvinceID);
                        ProvinceBorderManager.action.setProvinceID(Game.iActiveProvince);
                        if (Game.menuManager.getVisibleInGame_Civ()) {
                           boolean tEnabledByScaleOut = InGame_Civ.enabledByScaleOut;
                           Game.menuManager.rebuildInGame_Civ();
                           InGame_Civ.lTime = 0L;
                           Game.soundsManager.playSound(Game.soundsManager.getDiplomacy());
                           InGame_Civ.enabledByScaleOut = tEnabledByScaleOut;
                        } else {
                           Game.menuManager.rebuildInGame_Civ();
                           InGame_Civ.enabledByScaleOut = true;
                           Game.soundsManager.playSound(Game.soundsManager.getDiplomacy());
                        }

                        if (Game.menuManager.getVisibleInGame_CivBonuses()
                           && Game.getProvince(Game.iActiveProvince).getCivID() != 0
                           && InGame_CivBonuses.iCivID != Game.getProvince(Game.iActiveProvince).getCivID()) {
                           InGame_CivBonuses.iCivID = Game.getProvince(Game.iActiveProvince).getCivID();
                           Game.menuManager.rebuildInGame_CivBonuses();
                           Game.menuManager.setVisibleInGame_CivBonuses(true);
                           InGame_CivBonuses.lTime = 0L;
                        }
                     }
                  }
               } else {
                  Game.gameActiveProvince.actionUp_setActiveProvinceID(nPosX, nPosY, nPointer, button);
               }
            } else {
               Game.gameActiveProvince.actionUp_setActiveProvinceID(nPosX, nPosY, nPointer, button);
            }
         }

         if (!Game.mapScale.getScaleMode() && !Game.mapCoords.disableMovingMap) {
            Game.mapScroll.startScrollingTheMap();
         }

         try {
            ProvinceTouchExtraAction.actionUp_ExtraAction.extraAction(nPosX, nPosY, nPointer, button);
         } catch (Exception var8) {
            CFG.exceptionStack(var8);
         }
      } catch (Exception var9) {
         CFG.exceptionStack(var9);
      }
   }

   public boolean enableRightClick() {
      return Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_RECRUIT_ARMY || Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_BUILDING;
   }

   public final void actionUpSelectMode(int nMaxX, int nMaxY) {
      try {
         if (this.iSelectBoxX == nMaxX || this.iSelectBoxY == nMaxY) {
            return;
         }

         if (this.iSelectBoxX > nMaxX) {
            int tX = this.iSelectBoxX;
            this.iSelectBoxX = nMaxX;
            nMaxX = tX;
         }

         if (this.iSelectBoxY > nMaxY) {
            int tY = this.iSelectBoxY;
            this.iSelectBoxY = nMaxY;
            nMaxY = tY;
         }

         if (Game.menuManager.getInGame()) {
            if (Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_INVEST_IN_ECONOMY) {
               for (int i = 0; i < Game.NUM_OF_PROVINCES_IN_VIEW; i++) {
                  if (Game.getProvince(Game.getProvinceInViewID(i)).getCivID() == Game.player.iCivID
                     && actionUpSelectModeIsInBox_Center(Game.getProvinceInViewID(i), this.iSelectBoxX, this.iSelectBoxY, nMaxX, nMaxY)) {
                     if (Game.canInvestInEconomy(Game.getProvinceInViewID(i))) {
                        Game.menuManager.addToastGold(Game.lang.get("IncreasePopulationGrowthRate"), Images.populationUp);
                        Game.menuManager
                           .addToastInsufficient(
                              Game.lang.get("MaximumEconomy") + ": ",
                              CFG.getPrecision2(Game.getProvince(Game.getProvinceInViewID(i)).getEconomyWithBonuses(), 10)
                                 + " / "
                                 + CFG.getPrecision2(Game.getMaxEconomy(Game.getProvinceInViewID(i)), 10),
                              Game_Calendar.IMG_ECONOMY
                           );
                     } else if (Game.getCiv(Game.player.iCivID).fLegacy < Game.getInvestCost_Legacy(Game.getProvinceInViewID(i))) {
                        Game.menuManager
                           .addToastInsufficient(
                              Game.lang.get("InsufficientLegacy") + ": ",
                              CFG.getPrecision2(Game.getInvestCost_Legacy(Game.getProvinceInViewID(i)), 100),
                              Images.legacy
                           );
                     } else if (!Game.getProvince(Game.getProvinceInViewID(i)).addInvestInProvince()) {
                        Game.menuManager
                           .addToastInsufficient(
                              Game.lang.get("InsufficientGold") + ": ", CFG.getPrecision2(Game.getInvestCost(Game.getProvinceInViewID(i)), 100), Images.gold
                           );
                     } else {
                        ProvinceDraw.addProvinceDot_Economy(Game.getProvinceInViewID(i));
                     }
                  }
               }

               for (int ix = 0; ix < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; ix++) {
                  if (Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCivID() == Game.player.iCivID
                     && actionUpSelectModeIsInBox_Center(Game.getExtraProvinceInViewID(ix), this.iSelectBoxX, this.iSelectBoxY, nMaxX, nMaxY)) {
                     if (Game.canInvestInEconomy(Game.getExtraProvinceInViewID(ix))) {
                        Game.menuManager.addToastGold(Game.lang.get("IncreasePopulationGrowthRate"), Images.populationUp);
                        Game.menuManager
                           .addToastInsufficient(
                              Game.lang.get("MaximumEconomy") + ": ",
                              CFG.getPrecision2(Game.getProvince(Game.getExtraProvinceInViewID(ix)).getEconomyWithBonuses(), 10)
                                 + " / "
                                 + CFG.getPrecision2(Game.getMaxEconomy(Game.getExtraProvinceInViewID(ix)), 10),
                              Game_Calendar.IMG_ECONOMY
                           );
                     } else if (Game.getCiv(Game.player.iCivID).fLegacy < Game.getInvestCost_Legacy(Game.getExtraProvinceInViewID(ix))) {
                        Game.menuManager
                           .addToastInsufficient(
                              Game.lang.get("InsufficientLegacy") + ": ",
                              CFG.getPrecision2(Game.getInvestCost_Legacy(Game.getExtraProvinceInViewID(ix)), 100),
                              Images.legacy
                           );
                     } else if (!Game.getProvince(Game.getExtraProvinceInViewID(ix)).addInvestInProvince()) {
                        Game.menuManager
                           .addToastInsufficient(
                              Game.lang.get("InsufficientGold") + ": ",
                              CFG.getPrecision2(Game.getInvestCost(Game.getExtraProvinceInViewID(ix)), 100),
                              Images.gold
                           );
                     } else {
                        ProvinceDraw.addProvinceDot_Economy(Game.getExtraProvinceInViewID(ix));
                     }
                  }
               }
            } else if (Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_PEACE_VIEW) {
               for (int ixx = 0; ixx < Game.NUM_OF_PROVINCES_IN_VIEW; ixx++) {
                  if (actionUpSelectModeIsInBox_Center(Game.getProvinceInViewID(ixx), this.iSelectBoxX, this.iSelectBoxY, nMaxX, nMaxY)) {
                     ProvinceTouchExtraAction.actionPeaceView(Game.getProvinceInViewID(ixx), true);
                  }
               }

               for (int ixxx = 0; ixxx < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; ixxx++) {
                  if (actionUpSelectModeIsInBox_Center(Game.getExtraProvinceInViewID(ixxx), this.iSelectBoxX, this.iSelectBoxY, nMaxX, nMaxY)) {
                     ProvinceTouchExtraAction.actionPeaceView(Game.getExtraProvinceInViewID(ixxx), true);
                  }
               }
            } else if (Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_RELEASE_VASSAL) {
               for (int ixxxx = 0; ixxxx < Game.NUM_OF_PROVINCES_IN_VIEW; ixxxx++) {
                  if (actionUpSelectModeIsInBox_Center(Game.getProvinceInViewID(ixxxx), this.iSelectBoxX, this.iSelectBoxY, nMaxX, nMaxY)) {
                     ProvinceTouchExtraAction.actionReleaseVassal(Game.getProvinceInViewID(ixxxx), true);
                  }
               }

               for (int ixxxxx = 0; ixxxxx < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; ixxxxx++) {
                  if (actionUpSelectModeIsInBox_Center(Game.getExtraProvinceInViewID(ixxxxx), this.iSelectBoxX, this.iSelectBoxY, nMaxX, nMaxY)) {
                     ProvinceTouchExtraAction.actionReleaseVassal(Game.getExtraProvinceInViewID(ixxxxx), true);
                  }
               }

               Game.menuManager.rebuildInGame_ReleaseAVassal_SavePos();
            } else if (Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_BUILDING) {
               for (int ixxxxxx = 0; ixxxxxx < Game.NUM_OF_PROVINCES_IN_VIEW; ixxxxxx++) {
                  if (Game.getProvince(Game.getProvinceInViewID(ixxxxxx)).getCivID() == Game.player.iCivID
                     && actionUpSelectModeIsInBox_Center(Game.getProvinceInViewID(ixxxxxx), this.iSelectBoxX, this.iSelectBoxY, nMaxX, nMaxY)) {
                     ProvinceTouchExtraAction.actionBuilding(Game.getProvinceInViewID(ixxxxxx));
                  }
               }

               for (int ixxxxxxx = 0; ixxxxxxx < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; ixxxxxxx++) {
                  if (Game.getProvince(Game.getExtraProvinceInViewID(ixxxxxxx)).getCivID() == Game.player.iCivID
                     && actionUpSelectModeIsInBox_Center(Game.getExtraProvinceInViewID(ixxxxxxx), this.iSelectBoxX, this.iSelectBoxY, nMaxX, nMaxY)) {
                     ProvinceTouchExtraAction.actionBuilding(Game.getExtraProvinceInViewID(ixxxxxxx));
                  }
               }
            } else if (Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_INCREASE_MANPOWER) {
               for (int ixxxxxxxx = 0; ixxxxxxxx < Game.NUM_OF_PROVINCES_IN_VIEW; ixxxxxxxx++) {
                  if (Game.getProvince(Game.getProvinceInViewID(ixxxxxxxx)).getCivID() == Game.player.iCivID
                     && actionUpSelectModeIsInBox_Center(Game.getProvinceInViewID(ixxxxxxxx), this.iSelectBoxX, this.iSelectBoxY, nMaxX, nMaxY)) {
                     if (Game.getCiv(Game.player.iCivID).fLegacy < Game.getIncreaseManpowerCostLegacy(Game.getProvinceInViewID(ixxxxxxxx))) {
                        Game.menuManager
                           .addToastInsufficient(
                              Game.lang.get("InsufficientLegacy") + ": ",
                              CFG.getPrecision2(Game.getIncreaseManpowerCostLegacy(Game.getProvinceInViewID(ixxxxxxxx)), 100),
                              Images.legacy
                           );
                     } else if (!Game.getProvince(Game.getProvinceInViewID(ixxxxxxxx)).addIncreaseManpowerInProvince()) {
                        Game.menuManager
                           .addToastInsufficient(
                              Game.lang.get("InsufficientGold") + ": ",
                              CFG.getPrecision2(Game.getIncreaseManpowerCost(Game.getProvinceInViewID(ixxxxxxxx)), 100),
                              Images.gold
                           );
                     } else {
                        ProvinceDraw.addProvinceDot_Manpower(Game.getProvinceInViewID(ixxxxxxxx));
                     }
                  }
               }

               for (int ixxxxxxxxx = 0; ixxxxxxxxx < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; ixxxxxxxxx++) {
                  if (Game.getProvince(Game.getExtraProvinceInViewID(ixxxxxxxxx)).getCivID() == Game.player.iCivID
                     && actionUpSelectModeIsInBox_Center(Game.getExtraProvinceInViewID(ixxxxxxxxx), this.iSelectBoxX, this.iSelectBoxY, nMaxX, nMaxY)) {
                     if (Game.getCiv(Game.player.iCivID).fLegacy < Game.getIncreaseManpowerCostLegacy(Game.getExtraProvinceInViewID(ixxxxxxxxx))) {
                        Game.menuManager
                           .addToastInsufficient(
                              Game.lang.get("InsufficientLegacy") + ": ",
                              CFG.getPrecision2(Game.getIncreaseManpowerCostLegacy(Game.getExtraProvinceInViewID(ixxxxxxxxx)), 100),
                              Images.legacy
                           );
                     } else if (!Game.getProvince(Game.getExtraProvinceInViewID(ixxxxxxxxx)).addIncreaseManpowerInProvince()) {
                        Game.menuManager
                           .addToastInsufficient(
                              Game.lang.get("InsufficientGold") + ": ",
                              CFG.getPrecision2(Game.getIncreaseManpowerCost(Game.getExtraProvinceInViewID(ixxxxxxxxx)), 100),
                              Images.gold
                           );
                     } else {
                        ProvinceDraw.addProvinceDot_Manpower(Game.getExtraProvinceInViewID(ixxxxxxxxx));
                     }
                  }
               }
            } else if (Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_INCREASE_GROWTH_RATE) {
               for (int ixxxxxxxxxx = 0; ixxxxxxxxxx < Game.NUM_OF_PROVINCES_IN_VIEW; ixxxxxxxxxx++) {
                  if (Game.getProvince(Game.getProvinceInViewID(ixxxxxxxxxx)).getCivID() == Game.player.iCivID
                     && actionUpSelectModeIsInBox_Center(Game.getProvinceInViewID(ixxxxxxxxxx), this.iSelectBoxX, this.iSelectBoxY, nMaxX, nMaxY)) {
                     if (!Game.getProvince(Game.getProvinceInViewID(ixxxxxxxxxx)).addIncreaseGrowthRateInProvince()) {
                        Game.menuManager
                           .addToastInsufficient(
                              Game.lang.get("InsufficientGold") + ": ",
                              CFG.getPrecision2(Game.getIncreaseGrowthRateCost(Game.getProvinceInViewID(ixxxxxxxxxx)), 100),
                              Images.gold
                           );
                     } else {
                        ProvinceDraw.addProvinceDot_GrowthRate(Game.getProvinceInViewID(ixxxxxxxxxx));
                     }
                  }
               }

               for (int ixxxxxxxxxxx = 0; ixxxxxxxxxxx < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; ixxxxxxxxxxx++) {
                  if (Game.getProvince(Game.getExtraProvinceInViewID(ixxxxxxxxxxx)).getCivID() == Game.player.iCivID
                     && actionUpSelectModeIsInBox_Center(Game.getExtraProvinceInViewID(ixxxxxxxxxxx), this.iSelectBoxX, this.iSelectBoxY, nMaxX, nMaxY)) {
                     if (!Game.getProvince(Game.getExtraProvinceInViewID(ixxxxxxxxxxx)).addIncreaseGrowthRateInProvince()) {
                        Game.menuManager
                           .addToastInsufficient(
                              Game.lang.get("InsufficientGold") + ": ",
                              CFG.getPrecision2(Game.getIncreaseGrowthRateCost(Game.getExtraProvinceInViewID(ixxxxxxxxxxx)), 100),
                              Images.gold
                           );
                     } else {
                        ProvinceDraw.addProvinceDot_GrowthRate(Game.getExtraProvinceInViewID(ixxxxxxxxxxx));
                     }
                  }
               }
            } else if (Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_DEVELOP_INFRASTRUCTURE) {
               for (int ixxxxxxxxxxxx = 0; ixxxxxxxxxxxx < Game.NUM_OF_PROVINCES_IN_VIEW; ixxxxxxxxxxxx++) {
                  if (Game.getProvince(Game.getProvinceInViewID(ixxxxxxxxxxxx)).getCivID() == Game.player.iCivID
                     && actionUpSelectModeIsInBox_Center(Game.getProvinceInViewID(ixxxxxxxxxxxx), this.iSelectBoxX, this.iSelectBoxY, nMaxX, nMaxY)
                     && InGame_ProvinceInfo.addDevelopInfrastructureCost(Game.getProvinceInViewID(ixxxxxxxxxxxx))) {
                     ProvinceDraw.addProvinceDot_Infrastructure(Game.getProvinceInViewID(ixxxxxxxxxxxx));
                  }
               }

               for (int ixxxxxxxxxxxxx = 0; ixxxxxxxxxxxxx < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; ixxxxxxxxxxxxx++) {
                  if (Game.getProvince(Game.getExtraProvinceInViewID(ixxxxxxxxxxxxx)).getCivID() == Game.player.iCivID
                     && actionUpSelectModeIsInBox_Center(Game.getExtraProvinceInViewID(ixxxxxxxxxxxxx), this.iSelectBoxX, this.iSelectBoxY, nMaxX, nMaxY)
                     && InGame_ProvinceInfo.addDevelopInfrastructureCost(Game.getExtraProvinceInViewID(ixxxxxxxxxxxxx))) {
                     ProvinceDraw.addProvinceDot_Infrastructure(Game.getExtraProvinceInViewID(ixxxxxxxxxxxxx));
                  }
               }
            } else if (Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_INCREASE_TAX_EFFICIENCY) {
               for (int ixxxxxxxxxxxxxx = 0; ixxxxxxxxxxxxxx < Game.NUM_OF_PROVINCES_IN_VIEW; ixxxxxxxxxxxxxx++) {
                  if (Game.getProvince(Game.getProvinceInViewID(ixxxxxxxxxxxxxx)).getCivID() == Game.player.iCivID
                     && actionUpSelectModeIsInBox_Center(Game.getProvinceInViewID(ixxxxxxxxxxxxxx), this.iSelectBoxX, this.iSelectBoxY, nMaxX, nMaxY)) {
                     if (Game.getCiv(Game.player.iCivID).fLegacy < Game.getIncreaseTaxEfficiencyCostLegacy(Game.getProvinceInViewID(ixxxxxxxxxxxxxx))) {
                        Game.menuManager
                           .addToastInsufficient(
                              Game.lang.get("InsufficientLegacy") + ": ",
                              CFG.getPrecision2(Game.getIncreaseTaxEfficiencyCostLegacy(Game.getProvinceInViewID(ixxxxxxxxxxxxxx)), 100),
                              Images.legacy
                           );
                     } else if (!Game.getProvince(Game.getProvinceInViewID(ixxxxxxxxxxxxxx)).addIncreaseTaxEfficiencyInProvince()) {
                        Game.menuManager
                           .addToastInsufficient(
                              Game.lang.get("InsufficientGold") + ": ",
                              CFG.getPrecision2(Game.getIncreaseTaxEfficiencyCost(Game.getProvinceInViewID(ixxxxxxxxxxxxxx)), 100),
                              Images.gold
                           );
                     } else {
                        ProvinceDraw.addProvinceDot_TaxEfficiency(Game.getProvinceInViewID(ixxxxxxxxxxxxxx));
                     }
                  }
               }

               for (int ixxxxxxxxxxxxxxx = 0; ixxxxxxxxxxxxxxx < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; ixxxxxxxxxxxxxxx++) {
                  if (Game.getProvince(Game.getExtraProvinceInViewID(ixxxxxxxxxxxxxxx)).getCivID() == Game.player.iCivID
                     && actionUpSelectModeIsInBox_Center(Game.getExtraProvinceInViewID(ixxxxxxxxxxxxxxx), this.iSelectBoxX, this.iSelectBoxY, nMaxX, nMaxY)) {
                     if (Game.getCiv(Game.player.iCivID).fLegacy < Game.getIncreaseTaxEfficiencyCostLegacy(Game.getExtraProvinceInViewID(ixxxxxxxxxxxxxxx))) {
                        Game.menuManager
                           .addToastInsufficient(
                              Game.lang.get("InsufficientLegacy") + ": ",
                              CFG.getPrecision2(Game.getIncreaseTaxEfficiencyCostLegacy(Game.getExtraProvinceInViewID(ixxxxxxxxxxxxxxx)), 100),
                              Images.legacy
                           );
                     } else if (!Game.getProvince(Game.getExtraProvinceInViewID(ixxxxxxxxxxxxxxx)).addIncreaseTaxEfficiencyInProvince()) {
                        Game.menuManager
                           .addToastInsufficient(
                              Game.lang.get("InsufficientGold") + ": ",
                              CFG.getPrecision2(Game.getIncreaseTaxEfficiencyCost(Game.getExtraProvinceInViewID(ixxxxxxxxxxxxxxx)), 100),
                              Images.gold
                           );
                     } else {
                        ProvinceDraw.addProvinceDot_TaxEfficiency(Game.getExtraProvinceInViewID(ixxxxxxxxxxxxxxx));
                     }
                  }
               }
            } else if (Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_CONVERT_RELIGION) {
               for (int ixxxxxxxxxxxxxxxx = 0; ixxxxxxxxxxxxxxxx < Game.NUM_OF_PROVINCES_IN_VIEW; ixxxxxxxxxxxxxxxx++) {
                  if (Game.getProvince(Game.getProvinceInViewID(ixxxxxxxxxxxxxxxx)).getCivID() == Game.player.iCivID
                     && actionUpSelectModeIsInBox_Center(Game.getProvinceInViewID(ixxxxxxxxxxxxxxxx), this.iSelectBoxX, this.iSelectBoxY, nMaxX, nMaxY)
                     && Game.getProvince(Game.getProvinceInViewID(ixxxxxxxxxxxxxxxx)).getReligion()
                        != Game.getCiv(Game.getProvince(Game.getProvinceInViewID(ixxxxxxxxxxxxxxxx)).getCivID()).getReligionID()
                     && !Game.getProvince(Game.getProvinceInViewID(ixxxxxxxxxxxxxxxx)).addReligionConversion()) {
                     Game.menuManager
                        .addToastInsufficient(
                           Game.lang.get("InsufficientGold") + ": ",
                           CFG.getPrecision2((float)Game.religionManager.getReligionConversionCost(Game.getProvinceInViewID(ixxxxxxxxxxxxxxxx)), 100),
                           Images.gold
                        );
                  }
               }

               for (int ixxxxxxxxxxxxxxxxx = 0; ixxxxxxxxxxxxxxxxx < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; ixxxxxxxxxxxxxxxxx++) {
                  if (Game.getProvince(Game.getExtraProvinceInViewID(ixxxxxxxxxxxxxxxxx)).getCivID() == Game.player.iCivID
                     && actionUpSelectModeIsInBox_Center(Game.getExtraProvinceInViewID(ixxxxxxxxxxxxxxxxx), this.iSelectBoxX, this.iSelectBoxY, nMaxX, nMaxY)
                     && Game.getProvince(Game.getExtraProvinceInViewID(ixxxxxxxxxxxxxxxxx)).getReligion()
                        != Game.getCiv(Game.getProvince(Game.getExtraProvinceInViewID(ixxxxxxxxxxxxxxxxx)).getCivID()).getReligionID()
                     && !Game.getProvince(Game.getExtraProvinceInViewID(ixxxxxxxxxxxxxxxxx)).addReligionConversion()) {
                     Game.menuManager
                        .addToastInsufficient(
                           Game.lang.get("InsufficientGold") + ": ",
                           CFG.getPrecision2((float)Game.religionManager.getReligionConversionCost(Game.getExtraProvinceInViewID(ixxxxxxxxxxxxxxxxx)), 100),
                           Images.gold
                        );
                  }
               }
            } else if (Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_CORE) {
               for (int ixxxxxxxxxxxxxxxxxx = 0; ixxxxxxxxxxxxxxxxxx < Game.NUM_OF_PROVINCES_IN_VIEW; ixxxxxxxxxxxxxxxxxx++) {
                  if (Game.getProvince(Game.getProvinceInViewID(ixxxxxxxxxxxxxxxxxx)).getCivID() == Game.player.iCivID
                     && actionUpSelectModeIsInBox_Center(Game.getProvinceInViewID(ixxxxxxxxxxxxxxxxxx), this.iSelectBoxX, this.iSelectBoxY, nMaxX, nMaxY)
                     && !Game.getProvince(Game.getProvinceInViewID(ixxxxxxxxxxxxxxxxxx)).haveACore(Game.player.iCivID)
                     && Game.getProvince(Game.getProvinceInViewID(ixxxxxxxxxxxxxxxxxx)).coreCreation == null
                     && !Game.getProvince(Game.getProvinceInViewID(ixxxxxxxxxxxxxxxxxx)).addCoreCreation()) {
                     Game.menuManager
                        .addToastInsufficient(
                           Game.lang.get("InsufficientGold") + ": ",
                           CFG.getPrecision2(Game.getCoreCreationCost(Game.getProvinceInViewID(ixxxxxxxxxxxxxxxxxx)), 100),
                           Images.gold
                        );
                  }
               }

               for (int ixxxxxxxxxxxxxxxxxxx = 0; ixxxxxxxxxxxxxxxxxxx < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; ixxxxxxxxxxxxxxxxxxx++) {
                  if (Game.getProvince(Game.getExtraProvinceInViewID(ixxxxxxxxxxxxxxxxxxx)).getCivID() == Game.player.iCivID
                     && actionUpSelectModeIsInBox_Center(Game.getExtraProvinceInViewID(ixxxxxxxxxxxxxxxxxxx), this.iSelectBoxX, this.iSelectBoxY, nMaxX, nMaxY)
                     && !Game.getProvince(Game.getExtraProvinceInViewID(ixxxxxxxxxxxxxxxxxxx)).haveACore(Game.player.iCivID)
                     && Game.getProvince(Game.getExtraProvinceInViewID(ixxxxxxxxxxxxxxxxxxx)).coreCreation == null
                     && !Game.getProvince(Game.getExtraProvinceInViewID(ixxxxxxxxxxxxxxxxxxx)).addCoreCreation()) {
                     Game.menuManager
                        .addToastInsufficient(
                           Game.lang.get("InsufficientGold") + ": ",
                           CFG.getPrecision2(Game.getCoreCreationCost(Game.getExtraProvinceInViewID(ixxxxxxxxxxxxxxxxxxx)), 100),
                           Images.gold
                        );
                  }
               }
            } else if (Game.invasionArmyMode) {
               if (AA_KeyManager.CTRL_HOLD || AA_KeyManager.SHIFT_HOLD) {
                  for (int ixxxxxxxxxxxxxxxxxxxx = 0; ixxxxxxxxxxxxxxxxxxxx < Game.NUM_OF_PROVINCES_IN_VIEW; ixxxxxxxxxxxxxxxxxxxx++) {
                     if (actionUpSelectModeIsInBox_Center(Game.getProvinceInViewID(ixxxxxxxxxxxxxxxxxxxx), this.iSelectBoxX, this.iSelectBoxY, nMaxX, nMaxY)) {
                        Game.invasionArmy_RemoveProvince(Game.getProvinceInViewID(ixxxxxxxxxxxxxxxxxxxx));
                     }
                  }

                  for (int ixxxxxxxxxxxxxxxxxxxxx = 0; ixxxxxxxxxxxxxxxxxxxxx < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; ixxxxxxxxxxxxxxxxxxxxx++) {
                     if (actionUpSelectModeIsInBox_Center(
                        Game.getExtraProvinceInViewID(ixxxxxxxxxxxxxxxxxxxxx), this.iSelectBoxX, this.iSelectBoxY, nMaxX, nMaxY
                     )) {
                        Game.invasionArmy_RemoveProvince(Game.getExtraProvinceInViewID(ixxxxxxxxxxxxxxxxxxxxx));
                     }
                  }
               } else {
                  for (int ixxxxxxxxxxxxxxxxxxxxxx = 0; ixxxxxxxxxxxxxxxxxxxxxx < Game.NUM_OF_PROVINCES_IN_VIEW; ixxxxxxxxxxxxxxxxxxxxxx++) {
                     if (actionUpSelectModeIsInBox_Center(Game.getProvinceInViewID(ixxxxxxxxxxxxxxxxxxxxxx), this.iSelectBoxX, this.iSelectBoxY, nMaxX, nMaxY)) {
                        Game.invasionArmy_AddProvince(Game.getProvinceInViewID(ixxxxxxxxxxxxxxxxxxxxxx));
                     }
                  }

                  for (int ixxxxxxxxxxxxxxxxxxxxxxx = 0; ixxxxxxxxxxxxxxxxxxxxxxx < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; ixxxxxxxxxxxxxxxxxxxxxxx++) {
                     if (actionUpSelectModeIsInBox_Center(
                        Game.getExtraProvinceInViewID(ixxxxxxxxxxxxxxxxxxxxxxx), this.iSelectBoxX, this.iSelectBoxY, nMaxX, nMaxY
                     )) {
                        Game.invasionArmy_AddProvince(Game.getExtraProvinceInViewID(ixxxxxxxxxxxxxxxxxxxxxxx));
                     }
                  }
               }
            } else {
               if (nMaxX - this.iSelectBoxX > CFG.BUTTON_HEIGHT2) {
                  Game.menuManager.hideInGameMenus();
               }

               if (!AA_KeyManager.SHIFT_HOLD) {
                  Game.clearActiveArmy();
               }

               for (int ixxxxxxxxxxxxxxxxxxxxxxxx = 0; ixxxxxxxxxxxxxxxxxxxxxxxx < Game.NUM_OF_PROVINCES_IN_VIEW; ixxxxxxxxxxxxxxxxxxxxxxxx++) {
                  if (Game.getProvince(Game.getProvinceInViewID(ixxxxxxxxxxxxxxxxxxxxxxxx)).getFogDrawArmy()) {
                     for (int j = 0; j < Game.getProvince(Game.getProvinceInViewID(ixxxxxxxxxxxxxxxxxxxxxxxx)).getArmySize(); j++) {
                        if (Game.getProvince(Game.getProvinceInViewID(ixxxxxxxxxxxxxxxxxxxxxxxx)).getArmy(j).civID == Game.player.iCivID
                           && actionUpSelectModeIsInBox(
                              Game.getProvinceInViewID(ixxxxxxxxxxxxxxxxxxxxxxxx), j, this.iSelectBoxX, this.iSelectBoxY, nMaxX, nMaxY
                           )) {
                           Game.HoveredArmy nHA = new Game.HoveredArmy();
                           nHA.key = Game.getProvince(Game.getProvinceInViewID(ixxxxxxxxxxxxxxxxxxxxxxxx)).getArmy(j).key;
                           nHA.iCivID = Game.getProvince(Game.getProvinceInViewID(ixxxxxxxxxxxxxxxxxxxxxxxx)).getArmy(j).civID;
                           nHA.iProvinceID = Game.getProvinceInViewID(ixxxxxxxxxxxxxxxxxxxxxxxx);
                           nHA.iArmyID = j;
                           Game.addActiveArmy(nHA);
                           Game.animationHover.resetAnimationData();
                        }
                     }
                  }
               }

               for (int ixxxxxxxxxxxxxxxxxxxxxxxxx = 0; ixxxxxxxxxxxxxxxxxxxxxxxxx < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; ixxxxxxxxxxxxxxxxxxxxxxxxx++) {
                  if (Game.getProvince(Game.getExtraProvinceInViewID(ixxxxxxxxxxxxxxxxxxxxxxxxx)).getFogDrawArmy()) {
                     for (int jx = 0; jx < Game.getProvince(Game.getExtraProvinceInViewID(ixxxxxxxxxxxxxxxxxxxxxxxxx)).getArmySize(); jx++) {
                        if (Game.getProvince(Game.getExtraProvinceInViewID(ixxxxxxxxxxxxxxxxxxxxxxxxx)).getArmy(jx).civID == Game.player.iCivID
                           && actionUpSelectModeIsInBox(
                              Game.getExtraProvinceInViewID(ixxxxxxxxxxxxxxxxxxxxxxxxx), jx, this.iSelectBoxX, this.iSelectBoxY, nMaxX, nMaxY
                           )) {
                           Game.HoveredArmy nHA = new Game.HoveredArmy();
                           nHA.key = Game.getProvince(Game.getExtraProvinceInViewID(ixxxxxxxxxxxxxxxxxxxxxxxxx)).getArmy(jx).key;
                           nHA.iCivID = Game.getProvince(Game.getExtraProvinceInViewID(ixxxxxxxxxxxxxxxxxxxxxxxxx)).getArmy(jx).civID;
                           nHA.iProvinceID = Game.getExtraProvinceInViewID(ixxxxxxxxxxxxxxxxxxxxxxxxx);
                           nHA.iArmyID = jx;
                           Game.addActiveArmy(nHA);
                           Game.animationHover.resetAnimationData();
                        }
                     }
                  }
               }

               for (int ixxxxxxxxxxxxxxxxxxxxxxxxxx = 0; ixxxxxxxxxxxxxxxxxxxxxxxxxx < Game.NUM_OF_SEA_PROVINCES_IN_VIEW; ixxxxxxxxxxxxxxxxxxxxxxxxxx++) {
                  if (Game.getProvince(Game.getSeaProvinceInViewID(ixxxxxxxxxxxxxxxxxxxxxxxxxx)).getFogDrawArmy()) {
                     for (int jxx = 0; jxx < Game.getProvince(Game.getSeaProvinceInViewID(ixxxxxxxxxxxxxxxxxxxxxxxxxx)).getArmySize(); jxx++) {
                        if (Game.getProvince(Game.getSeaProvinceInViewID(ixxxxxxxxxxxxxxxxxxxxxxxxxx)).getArmy(jxx).civID == Game.player.iCivID
                           && actionUpSelectModeIsInBox(
                              Game.getSeaProvinceInViewID(ixxxxxxxxxxxxxxxxxxxxxxxxxx), jxx, this.iSelectBoxX, this.iSelectBoxY, nMaxX, nMaxY
                           )) {
                           Game.HoveredArmy nHA = new Game.HoveredArmy();
                           nHA.key = Game.getProvince(Game.getSeaProvinceInViewID(ixxxxxxxxxxxxxxxxxxxxxxxxxx)).getArmy(jxx).key;
                           nHA.iCivID = Game.getProvince(Game.getSeaProvinceInViewID(ixxxxxxxxxxxxxxxxxxxxxxxxxx)).getArmy(jxx).civID;
                           nHA.iProvinceID = Game.getSeaProvinceInViewID(ixxxxxxxxxxxxxxxxxxxxxxxxxx);
                           nHA.iArmyID = jxx;
                           Game.addActiveArmy(nHA);
                           Game.animationHover.resetAnimationData();
                        }
                     }
                  }
               }

               for (int ixxxxxxxxxxxxxxxxxxxxxxxxxxx = 0;
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxx < Game.NUM_OF_WASTELAND_PROVINCES_IN_VIEW;
                  ixxxxxxxxxxxxxxxxxxxxxxxxxxx++
               ) {
                  if (Game.getProvince(Game.getWastelandProvinceInViewID(ixxxxxxxxxxxxxxxxxxxxxxxxxxx)).getFogDrawArmy()) {
                     for (int jxxx = 0; jxxx < Game.getProvince(Game.getWastelandProvinceInViewID(ixxxxxxxxxxxxxxxxxxxxxxxxxxx)).getArmySize(); jxxx++) {
                        if (Game.getProvince(Game.getWastelandProvinceInViewID(ixxxxxxxxxxxxxxxxxxxxxxxxxxx)).getArmy(jxxx).civID == Game.player.iCivID
                           && actionUpSelectModeIsInBox(
                              Game.getWastelandProvinceInViewID(ixxxxxxxxxxxxxxxxxxxxxxxxxxx), jxxx, this.iSelectBoxX, this.iSelectBoxY, nMaxX, nMaxY
                           )) {
                           Game.HoveredArmy nHA = new Game.HoveredArmy();
                           nHA.key = Game.getProvince(Game.getWastelandProvinceInViewID(ixxxxxxxxxxxxxxxxxxxxxxxxxxx)).getArmy(jxxx).key;
                           nHA.iCivID = Game.getProvince(Game.getWastelandProvinceInViewID(ixxxxxxxxxxxxxxxxxxxxxxxxxxx)).getArmy(jxxx).civID;
                           nHA.iProvinceID = Game.getWastelandProvinceInViewID(ixxxxxxxxxxxxxxxxxxxxxxxxxxx);
                           nHA.iArmyID = jxxx;
                           Game.addActiveArmy(nHA);
                           Game.animationHover.resetAnimationData();
                        }
                     }
                  }
               }

               ProvinceTouchExtraAction.actionUp_SetActiveArmy();
               if (Game.activeArmySize > 0) {
                  Game.gameActiveProvince.resetLastActiveProvince();
                  Game.setActiveProvinceID(-1);
               }
            }
         } else if (Game.menuManager.getInScenarioAssign()) {
            int tActive = Game.iActiveProvince;

            for (int ixxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0; ixxxxxxxxxxxxxxxxxxxxxxxxxxxx < Game.NUM_OF_PROVINCES_IN_VIEW; ixxxxxxxxxxxxxxxxxxxxxxxxxxxx++) {
               if (actionUpSelectModeIsInBox_Center(Game.getProvinceInViewID(ixxxxxxxxxxxxxxxxxxxxxxxxxxxx), this.iSelectBoxX, this.iSelectBoxY, nMaxX, nMaxY)) {
                  Game.iActiveProvince = Game.getProvinceInViewID(ixxxxxxxxxxxxxxxxxxxxxxxxxxxx);
                  ScenarioAssign.actionUpdateData(true);
               }
            }

            for (int ixxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0; ixxxxxxxxxxxxxxxxxxxxxxxxxxxxx < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; ixxxxxxxxxxxxxxxxxxxxxxxxxxxxx++) {
               if (actionUpSelectModeIsInBox_Center(
                  Game.getExtraProvinceInViewID(ixxxxxxxxxxxxxxxxxxxxxxxxxxxxx), this.iSelectBoxX, this.iSelectBoxY, nMaxX, nMaxY
               )) {
                  Game.iActiveProvince = Game.getExtraProvinceInViewID(ixxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
                  ScenarioAssign.actionUpdateData(true);
               }
            }

            Game.iActiveProvince = tActive;
         } else if (Game.menuManager.getInScenarioWasteland()) {
            int tActive = Game.iActiveProvince;

            for (int ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0; ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < Game.NUM_OF_PROVINCES_IN_VIEW; ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxx++) {
               if (actionUpSelectModeIsInBox_Center(Game.getProvinceInViewID(ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxx), this.iSelectBoxX, this.iSelectBoxY, nMaxX, nMaxY)
                  )
                {
                  Game.iActiveProvince = Game.getProvinceInViewID(ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
                  ScenarioWasteland.actionUpdateData(true);
               }
            }

            for (int ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0;
               ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW;
               ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx++
            ) {
               if (actionUpSelectModeIsInBox_Center(
                  Game.getExtraProvinceInViewID(ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx), this.iSelectBoxX, this.iSelectBoxY, nMaxX, nMaxY
               )) {
                  Game.iActiveProvince = Game.getExtraProvinceInViewID(ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
                  ScenarioWasteland.actionUpdateData(true);
               }
            }

            Game.iActiveProvince = tActive;
         } else if (Game.menuManager.getInMapEditorContinents()) {
            int tActive = Game.iActiveProvince;

            for (int ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0;
               ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < Game.NUM_OF_PROVINCES_IN_VIEW;
               ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx++
            ) {
               if (actionUpSelectModeIsInBox_Center(
                  Game.getProvinceInViewID(ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx), this.iSelectBoxX, this.iSelectBoxY, nMaxX, nMaxY
               )) {
                  Game.getProvince(Game.getProvinceInViewID(ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)).setContinent(EditorMapContinents.currentContinentID);
               }
            }

            for (int ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0;
               ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW;
               ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx++
            ) {
               if (actionUpSelectModeIsInBox_Center(
                  Game.getExtraProvinceInViewID(ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx), this.iSelectBoxX, this.iSelectBoxY, nMaxX, nMaxY
               )) {
                  Game.getProvince(Game.getExtraProvinceInViewID(ixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)).setContinent(EditorMapContinents.currentContinentID);
               }
            }

            Game.iActiveProvince = tActive;
         }
      } catch (Exception var6) {
         CFG.exceptionStack(var6);
      }
   }

   public static final boolean actionUpSelectModeIsInBox(int nProvinceID, int nArmyID, int nMinX, int nMinY, int nMaxX, int nMaxY) {
      try {
         int nX = ProvinceDrawArmy.getArmyPosX(nProvinceID, nArmyID);
         int nY = ProvinceDrawArmy.getArmyPosY(nProvinceID, nArmyID);
         int nWidth = ProvinceDrawArmy.getArmyWidth(Game.getProvince(nProvinceID).getArmy(nArmyID).iArmyWidth);
         int nHeight = ProvinceDrawArmy.getArmyHeight();
         if ((nX < nMaxX && nX > nMinX || nX + nWidth < nMaxX && nX + nWidth > nMinX)
            && (nY > nMinY && nY < nMaxY || nY + nHeight > nMinY && nY + nHeight < nMaxY)) {
            return true;
         }
      } catch (Exception var10) {
         CFG.exceptionStack(var10);
      }

      return false;
   }

   public static final boolean actionUpSelectModeIsInBox_Center(int nProvinceID, int nMinX, int nMinY, int nMaxX, int nMaxY) {
      try {
         int nX = ProvinceDrawArmy.getDetailsPosX_2(nProvinceID);
         int nY = ProvinceDrawArmy.getDetailsPosY_2(nProvinceID);
         int nWidth = 1;
         int nHeight = 1;
         if ((nX < nMaxX && nX > nMinX || nX + nWidth < nMaxX && nX + nWidth > nMinX)
            && (nY > nMinY && nY < nMaxY || nY + nHeight > nMinY && nY + nHeight < nMaxY)) {
            return true;
         }
      } catch (Exception var9) {
         CFG.exceptionStack(var9);
      }

      return false;
   }

   public final void Scroll(int amount) {
      Game.mapScale.scrollScale(amount);
   }

   public final void setActionDownPosXY(int actionDownPosX, int actionDownPosY) {
      try {
         this.updateStartMovePosXY(actionDownPosX, actionDownPosY);
         this.actionDownPosX = actionDownPosX;
         this.actionDownPosY = actionDownPosY;
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   protected final void updateStartMovePosXY(int nPosX, int nPosY) {
      this.iStartMovePosX = this.mapMoveDirectionX.getStartMovePos((int)(nPosX / Game.mapScale.getCurrentScale()));
      this.iStartMovePosY = this.mapMoveDirectionY.getStartMovePos((int)(nPosY / Game.mapScale.getCurrentScale()));
   }

   public final void buildReversePos() {
      this.buildReversePosX();
      this.buildReversePosY();
      this.buildReversePosX2();
      this.buildReversePosY2();
   }

   protected final void buildReversePosX() {
      if (reverseDirectionX) {
         this.mapMoveDirectionX = new MapTouchManager.ReverseDirection() {
            @Override
            public int getStartMovePos(int nPos) {
               return Game.mapCoords.getPosX() - nPos;
            }
         };
      } else {
         this.mapMoveDirectionX = new MapTouchManager.ReverseDirection() {
            @Override
            public int getStartMovePos(int nPos) {
               return Game.mapCoords.getPosX() + nPos;
            }
         };
      }
   }

   protected final void buildReversePosY() {
      if (reverseDirectionY) {
         this.mapMoveDirectionY = new MapTouchManager.ReverseDirection() {
            @Override
            public int getStartMovePos(int nPos) {
               return Game.mapCoords.getPosY() - nPos;
            }
         };
      } else {
         this.mapMoveDirectionY = new MapTouchManager.ReverseDirection() {
            @Override
            public int getStartMovePos(int nPos) {
               return Game.mapCoords.getPosY() + nPos;
            }
         };
      }
   }

   protected final void buildReversePosX2() {
      if (reverseDirectionX) {
         this.mapMoveDirectionX2 = new MapTouchManager.ReverseDirection2() {
            @Override
            public int getNewPos(int iStartMovePos, int nPos) {
               return iStartMovePos + nPos;
            }
         };
      } else {
         this.mapMoveDirectionX2 = new MapTouchManager.ReverseDirection2() {
            @Override
            public int getNewPos(int iStartMovePos, int nPos) {
               return iStartMovePos - nPos;
            }
         };
      }
   }

   protected final void buildReversePosY2() {
      if (reverseDirectionY) {
         this.mapMoveDirectionY2 = new MapTouchManager.ReverseDirection2() {
            @Override
            public int getNewPos(int iStartMovePos, int nPos) {
               return iStartMovePos + nPos;
            }
         };
      } else {
         this.mapMoveDirectionY2 = new MapTouchManager.ReverseDirection2() {
            @Override
            public int getNewPos(int iStartMovePos, int nPos) {
               return iStartMovePos - nPos;
            }
         };
      }
   }

   public final long getActionDownTime() {
      return this.lActionDownTime;
   }

   public final void setActionDownTime(long lActionDownTime) {
      this.lActionDownTime = lActionDownTime;
   }

   public final void setUpdateStartMovePosX(boolean updateStartMovePosX) {
      this.updateStartMovePosX = updateStartMovePosX;
   }

   public final void setUpdateStartMovePosY(boolean updateStartMovePosY) {
      this.updateStartMovePosY = updateStartMovePosY;
   }

   public interface ReverseDirection {
      int getStartMovePos(int var1);
   }

   public interface ReverseDirection2 {
      int getNewPos(int var1, int var2);
   }
}
