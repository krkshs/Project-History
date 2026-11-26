package aoc.kingdoms.lukasz.jakowski;

import aoc.kingdoms.lukasz.map.province.ProvinceBorderManager;
import aoc.kingdoms.lukasz.map.province.ProvinceTouchExtraAction;
import aoc.kingdoms.lukasz.menusInGame.Province.InGame_ProvinceArmy;

public class GameActiveProvince {
   public final void actionUp_setActiveProvinceID(int nPosX, int nPosY, int nPointer, int button) {
      if (!Game.mapScale.getScaleMode()
         && Game.mapTouchManager.actionDownPosX + CFG.PADDING * CFG.DENSITY > nPosX
         && Game.mapTouchManager.actionDownPosX - CFG.PADDING * CFG.DENSITY < nPosX
         && Game.mapTouchManager.actionDownPosY + CFG.PADDING * CFG.DENSITY > nPosY
         && Game.mapTouchManager.actionDownPosY - CFG.PADDING * CFG.DENSITY < nPosY) {
         this.actionUp_setActiveProvinceID2(nPosX, nPosY, nPointer, button);
         if (!CFG.brushTool) {
            Game.mapModes.lMapModes.get(Game.mapModes.iActiveMapModeID).playSFX_ProvinceClick();
         }

         try {
            ProvinceTouchExtraAction.actionUp_SetActiveProvinceID_ExtraAction.extraAction(nPosX, nPosY, nPointer, button);
         } catch (Exception var6) {
            CFG.exceptionStack(var6);
         }
      }
   }

   private final void actionUp_setActiveProvinceID2(int nPosX, int nPosY, int nPointer, int button) {
      nPosX = (int)(nPosX / Game.mapScale.getCurrentScale());
      nPosY = (int)(nPosY / Game.mapScale.getCurrentScale());
      if (Game.regroupArmyMode) {
         int i = 0;

         for (int tPosX = 0; i < Game.getProvincesSize(); i++) {
            tPosX = (int)Math.abs(Game.checkPosOfClickX(Game.mapCoords.getPosX() - nPosX));
            if (Game.getProvince(i).getMinX() <= tPosX
               && Game.getProvince(i).getMaxX() >= tPosX
               && Game.getProvince(i).getMinY() <= -Game.mapCoords.getPosY() + nPosY
               && Game.getProvince(i).getMaxY() >= -Game.mapCoords.getPosY() + nPosY
               && Game.pathContains(i, tPosX, -Game.mapCoords.getPosY() + nPosY)) {
               Game.regroupArmy_AddProvince(i);
               return;
            }
         }

         if (Math.abs(Game.checkPosOfClickX(Game.mapCoords.getPosX() - nPosX))
               + -Game.MAX_BELOW_ZERO_POINT_X * Game.mapBG.iMapScale / Game.mapScale.getCurrentScale()
            > Game.mapBG.getWidth()) {
            i = 0;

            for (int tPosXx = 0; i < Game.getProvincesSize(); i++) {
               if (Game.getProvince(i).getBelowZero()) {
                  tPosXx = (int)Math.abs(Game.checkPosOfClickX(Game.mapCoords.getPosX() - nPosX));
                  if (Game.getProvince(i).getMinX() <= tPosXx - Game.mapBG.getWidth()
                     && Game.getProvince(i).getMaxX() >= tPosXx - Game.mapBG.getWidth()
                     && Game.getProvince(i).getMinY() <= -Game.mapCoords.getPosY() + nPosY
                     && Game.getProvince(i).getMaxY() >= -Game.mapCoords.getPosY() + nPosY
                     && Game.pathContains(i, tPosXx - Game.mapBG.getWidth(), -Game.mapCoords.getPosY() + nPosY)) {
                     Game.regroupArmy_AddProvince(i);
                     return;
                  }
               }
            }
         }
      } else if (Game.invasionArmyMode) {
         int i = 0;

         for (int tPosXxx = 0; i < Game.getProvincesSize(); i++) {
            tPosXxx = (int)Math.abs(Game.checkPosOfClickX(Game.mapCoords.getPosX() - nPosX));
            if (Game.getProvince(i).getMinX() <= tPosXxx
               && Game.getProvince(i).getMaxX() >= tPosXxx
               && Game.getProvince(i).getMinY() <= -Game.mapCoords.getPosY() + nPosY
               && Game.getProvince(i).getMaxY() >= -Game.mapCoords.getPosY() + nPosY
               && Game.pathContains(i, tPosXxx, -Game.mapCoords.getPosY() + nPosY)) {
               Game.invasionArmy_AddProvince(i);
               return;
            }
         }

         if (Math.abs(Game.checkPosOfClickX(Game.mapCoords.getPosX() - nPosX))
               + -Game.MAX_BELOW_ZERO_POINT_X * Game.mapBG.iMapScale / Game.mapScale.getCurrentScale()
            > Game.mapBG.getWidth()) {
            i = 0;

            for (int tPosXxxx = 0; i < Game.getProvincesSize(); i++) {
               if (Game.getProvince(i).getBelowZero()) {
                  tPosXxxx = (int)Math.abs(Game.checkPosOfClickX(Game.mapCoords.getPosX() - nPosX));
                  if (Game.getProvince(i).getMinX() <= tPosXxxx - Game.mapBG.getWidth()
                     && Game.getProvince(i).getMaxX() >= tPosXxxx - Game.mapBG.getWidth()
                     && Game.getProvince(i).getMinY() <= -Game.mapCoords.getPosY() + nPosY
                     && Game.getProvince(i).getMaxY() >= -Game.mapCoords.getPosY() + nPosY
                     && Game.pathContains(i, tPosXxxx - Game.mapBG.getWidth(), -Game.mapCoords.getPosY() + nPosY)) {
                     Game.invasionArmy_AddProvince(i);
                     return;
                  }
               }
            }
         }
      } else {
         if (Game.chooseProvinceMode) {
            Game.chooseProvinceMode = false;
            InGame_ProvinceArmy.chooseProvinceExtraY = 0;
            int i = 0;

            for (int tPosXxxxx = 0; i < Game.getProvincesSize(); i++) {
               tPosXxxxx = (int)Math.abs(Game.checkPosOfClickX(Game.mapCoords.getPosX() - nPosX));
               if (Game.getProvince(i).getMinX() <= tPosXxxxx
                  && Game.getProvince(i).getMaxX() >= tPosXxxxx
                  && Game.getProvince(i).getMinY() <= -Game.mapCoords.getPosY() + nPosY
                  && Game.getProvince(i).getMaxY() >= -Game.mapCoords.getPosY() + nPosY
                  && Game.pathContains(i, tPosXxxxx, -Game.mapCoords.getPosY() + nPosY)) {
                  this.moveActiveArmiesToProvinceID(i);
                  return;
               }
            }

            if (Math.abs(Game.checkPosOfClickX(Game.mapCoords.getPosX() - nPosX))
                  + -Game.MAX_BELOW_ZERO_POINT_X * Game.mapBG.iMapScale / Game.mapScale.getCurrentScale()
               > Game.mapBG.getWidth()) {
               i = 0;

               for (int tPosXxxxxx = 0; i < Game.getProvincesSize(); i++) {
                  if (Game.getProvince(i).getBelowZero()) {
                     tPosXxxxxx = (int)Math.abs(Game.checkPosOfClickX(Game.mapCoords.getPosX() - nPosX));
                     if (Game.getProvince(i).getMinX() <= tPosXxxxxx - Game.mapBG.getWidth()
                        && Game.getProvince(i).getMaxX() >= tPosXxxxxx - Game.mapBG.getWidth()
                        && Game.getProvince(i).getMinY() <= -Game.mapCoords.getPosY() + nPosY
                        && Game.getProvince(i).getMaxY() >= -Game.mapCoords.getPosY() + nPosY
                        && Game.pathContains(i, tPosXxxxxx - Game.mapBG.getWidth(), -Game.mapCoords.getPosY() + nPosY)) {
                        this.moveActiveArmiesToProvinceID(i);
                        return;
                     }
                  }
               }
            }

            return;
         }

         int i = 0;

         for (int tPosXxxxxxx = 0; i < Game.getProvincesSize(); i++) {
            tPosXxxxxxx = (int)Math.abs(Game.checkPosOfClickX(Game.mapCoords.getPosX() - nPosX));
            if (Game.getProvince(i).getMinX() <= tPosXxxxxxx
               && Game.getProvince(i).getMaxX() >= tPosXxxxxxx
               && Game.getProvince(i).getMinY() <= -Game.mapCoords.getPosY() + nPosY
               && Game.getProvince(i).getMaxY() >= -Game.mapCoords.getPosY() + nPosY
               && Game.pathContains(i, tPosXxxxxxx, -Game.mapCoords.getPosY() + nPosY)) {
               if (i != Game.iActiveProvince) {
                  this.resetLastActiveProvince();
                  this.setActiveProvinceID(i);
               } else {
                  Game.iOldActiveProvinceID = Game.iActiveProvince;
               }

               return;
            }
         }

         if (Math.abs(Game.checkPosOfClickX(Game.mapCoords.getPosX() - nPosX))
               + -Game.MAX_BELOW_ZERO_POINT_X * Game.mapBG.iMapScale / Game.mapScale.getCurrentScale()
            > Game.mapBG.getWidth()) {
            i = 0;

            for (int tPosXxxxxxxx = 0; i < Game.getProvincesSize(); i++) {
               if (Game.getProvince(i).getBelowZero()) {
                  tPosXxxxxxxx = (int)Math.abs(Game.checkPosOfClickX(Game.mapCoords.getPosX() - nPosX));
                  if (Game.getProvince(i).getMinX() <= tPosXxxxxxxx - Game.mapBG.getWidth()
                     && Game.getProvince(i).getMaxX() >= tPosXxxxxxxx - Game.mapBG.getWidth()
                     && Game.getProvince(i).getMinY() <= -Game.mapCoords.getPosY() + nPosY
                     && Game.getProvince(i).getMaxY() >= -Game.mapCoords.getPosY() + nPosY
                     && Game.pathContains(i, tPosXxxxxxxx - Game.mapBG.getWidth(), -Game.mapCoords.getPosY() + nPosY)) {
                     if (i != Game.iActiveProvince) {
                        this.resetLastActiveProvince();
                        this.setActiveProvinceID(i);
                     } else {
                        Game.iOldActiveProvinceID = Game.iActiveProvince;
                     }

                     return;
                  }
               }
            }
         }
      }
   }

   public final void moveActiveArmiesToProvinceID(int toProvinceID) {
      int i = 0;

      for (int extraArmyY = 0; i < Game.activeArmySize; i++) {
         if (toProvinceID == Game.activeArmy.get(i).iProvinceID) {
            Game.player.playerData.invasion.removeInvasion(Game.activeArmy.get(i).key);
            Game.getCiv(Game.activeArmy.get(i).iCivID).cancelMove(Game.activeArmy.get(i).key);
            if (Game.menuManager.getVisibleInGame_ProvinceArmy()) {
               Game.menuManager.rebuildInGame_ProvinceArmy(true, true);
            }
         } else {
            extraArmyY = 0;

            for (int j = 0; j < i; j++) {
               if (Game.activeArmy.get(j).iProvinceID == Game.activeArmy.get(i).iProvinceID) {
                  extraArmyY++;
               }
            }

            Game.player.playerData.invasion.removeInvasion(Game.activeArmy.get(i).key);
            if (!GameValues.move.PLAYER_CAN_MOVE_ALL_ARMIES && (!Game.SPECTATOR_MODE || !Game.SANDBOX)) {
               if (Game.activeArmy.get(i).iCivID == Game.player.iCivID
                  || GameValues.move.PLAYER_CAN_MOVE_VASSALS_ARMIES && Game.getCiv(Game.activeArmy.get(i).iCivID).getPuppetOfCivID() == Game.player.iCivID) {
                  Game.getCiv(Game.activeArmy.get(i).iCivID)
                     .newMove(Game.activeArmy.get(i).iProvinceID, toProvinceID, Game.activeArmy.get(i).key, extraArmyY, false);
               }
            } else if (Game.activeArmy.get(i).iCivID < 0) {
               Game.revolutionMoveUnits.newMove(Game.activeArmy.get(i).iProvinceID, toProvinceID, Game.activeArmy.get(i).key, extraArmyY, false);
            } else {
               Game.getCiv(Game.activeArmy.get(i).iCivID)
                  .newMove(Game.activeArmy.get(i).iProvinceID, toProvinceID, Game.activeArmy.get(i).key, extraArmyY, false);
            }

            Game.soundsManager.playMove();
            if (Game.menuManager.getVisibleInGame_ProvinceArmy()) {
               Game.menuManager.rebuildInGame_ProvinceArmy(true, true);
            }
         }
      }
   }

   public final int mouseOverProvinceID(int nPosX, int nPosY) {
      nPosX = (int)(nPosX / Game.mapScale.getCurrentScale());
      nPosY = (int)(nPosY / Game.mapScale.getCurrentScale());
      int i = 0;

      for (int tPosX = 0; i < Game.getProvincesSize(); i++) {
         tPosX = (int)Math.abs(Game.checkPosOfClickX(Game.mapCoords.getPosX() - nPosX));
         if (Game.getProvince(i).getMinX() <= tPosX
            && Game.getProvince(i).getMaxX() >= tPosX
            && Game.getProvince(i).getMinY() <= -Game.mapCoords.getPosY() + nPosY
            && Game.getProvince(i).getMaxY() >= -Game.mapCoords.getPosY() + nPosY
            && Game.pathContains(i, tPosX, -Game.mapCoords.getPosY() + nPosY)) {
            return i;
         }
      }

      if (Math.abs(Game.checkPosOfClickX(Game.mapCoords.getPosX() - nPosX))
            + -Game.MAX_BELOW_ZERO_POINT_X * Game.mapBG.iMapScale / Game.mapScale.getCurrentScale()
         > Game.mapBG.getWidth()) {
         i = 0;

         for (int tPosXx = 0; i < Game.getProvincesSize(); i++) {
            if (Game.getProvince(i).getBelowZero()) {
               tPosXx = (int)Math.abs(Game.checkPosOfClickX(Game.mapCoords.getPosX() - nPosX));
               if (Game.getProvince(i).getMinX() <= tPosXx - Game.mapBG.getWidth()
                  && Game.getProvince(i).getMaxX() >= tPosXx - Game.mapBG.getWidth()
                  && Game.getProvince(i).getMinY() <= -Game.mapCoords.getPosY() + nPosY
                  && Game.getProvince(i).getMaxY() >= -Game.mapCoords.getPosY() + nPosY
                  && Game.pathContains(i, tPosXx - Game.mapBG.getWidth(), -Game.mapCoords.getPosY() + nPosY)) {
                  return i;
               }
            }
         }
      }

      return -1;
   }

   public final void setActiveProvinceID(int nActiveProvinceID) {
      try {
         Game.iOldActiveProvinceID = Game.iActiveProvince;
         Game.setActiveProvinceID(nActiveProvinceID);
         Game.activeProvince_Animation_Data.resetAnimationData();
         Game.clearActiveArmy();
         if (nActiveProvinceID >= 0 && Game.getProvince(nActiveProvinceID).getSeaProvince()) {
            for (int i = 0; i < Game.getProvince(nActiveProvinceID).getArmySize(); i++) {
               if (Game.getProvince(nActiveProvinceID).getArmy(i).civID == Game.player.iCivID) {
                  Game.HoveredArmy nHA = new Game.HoveredArmy();
                  nHA.iProvinceID = nActiveProvinceID;
                  nHA.key = Game.getProvince(nActiveProvinceID).getArmy(i).key;
                  nHA.iArmyID = i;
                  nHA.iCivID = Game.getProvince(nActiveProvinceID).getArmy(i).civID;
                  Game.addActiveArmy(nHA);
               }
            }

            if (Game.activeArmySize > 0) {
               Game.setActiveProvinceID(-1);
               Game.menuManager.rebuildInGame_ProvinceArmy();
               return;
            }
         }

         ProvinceBorderManager.action.setProvinceID(Game.iActiveProvince);
      } catch (NullPointerException var4) {
         CFG.exceptionStack(var4);
      }
   }

   public final void resetLastActiveProvince() {
      ProvinceBorderManager.action.resetProvinceID();
   }

   public final void setProvinceID_PPM(int nPosX, int nPosY) {
      Game.animationManager.clickAnimation(nPosX, nPosY);
      int nNewChosenProvinceID = Game.setProvinceID_HoverAProvince(
         (int)(nPosX / Game.mapScale.getCurrentScale()), (int)(nPosY / Game.mapScale.getCurrentScale())
      );
      if (nNewChosenProvinceID >= 0) {
         if (Game.regroupArmyMode) {
            Game.regroupArmy_RemoveProvince(nNewChosenProvinceID);
         } else if (Game.invasionArmyMode) {
            Game.invasionArmy_RemoveProvince(nNewChosenProvinceID);
         } else {
            int i = 0;

            for (int extraArmyY = 0; i < Game.activeArmySize; i++) {
               if (nNewChosenProvinceID == Game.activeArmy.get(i).iProvinceID) {
                  Game.player.playerData.invasion.removeInvasion(Game.activeArmy.get(i).key);
                  Game.getCiv(Game.activeArmy.get(i).iCivID).cancelMove(Game.activeArmy.get(i).key);
                  if (Game.menuManager.getVisibleInGame_ProvinceArmy()) {
                     Game.menuManager.rebuildInGame_ProvinceArmy(true, true);
                  }
               } else {
                  extraArmyY = 0;

                  for (int j = 0; j < i; j++) {
                     if (Game.activeArmy.get(j).iProvinceID == Game.activeArmy.get(i).iProvinceID) {
                        extraArmyY++;
                     }
                  }

                  Game.player.playerData.invasion.removeInvasion(Game.activeArmy.get(i).key);
                  if (!GameValues.move.PLAYER_CAN_MOVE_ALL_ARMIES && (!Game.SPECTATOR_MODE || !Game.SANDBOX)) {
                     if (Game.activeArmy.get(i).iCivID == Game.player.iCivID
                        || GameValues.move.PLAYER_CAN_MOVE_VASSALS_ARMIES
                           && Game.getCiv(Game.activeArmy.get(i).iCivID).getPuppetOfCivID() == Game.player.iCivID) {
                        Game.getCiv(Game.activeArmy.get(i).iCivID)
                           .newMove(Game.activeArmy.get(i).iProvinceID, nNewChosenProvinceID, Game.activeArmy.get(i).key, extraArmyY, false);
                     }
                  } else if (Game.activeArmy.get(i).iCivID < 0) {
                     Game.revolutionMoveUnits.newMove(Game.activeArmy.get(i).iProvinceID, nNewChosenProvinceID, Game.activeArmy.get(i).key, extraArmyY, false);
                  } else {
                     Game.getCiv(Game.activeArmy.get(i).iCivID)
                        .newMove(Game.activeArmy.get(i).iProvinceID, nNewChosenProvinceID, Game.activeArmy.get(i).key, extraArmyY, false);
                  }

                  Game.soundsManager.playMove();
                  if (Game.menuManager.getVisibleInGame_ProvinceArmy()) {
                     Game.menuManager.rebuildInGame_ProvinceArmy(true, true);
                  }
               }
            }
         }
      }
   }
}
