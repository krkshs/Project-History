package aoc.kingdoms.lukasz.jakowski;

import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.SiegeManager;
import aoc.kingdoms.lukasz.map.map.Ship.ShipManager;
import aoc.kingdoms.lukasz.map.province.ProvinceDrawArmy;
import aoc.kingdoms.lukasz.map.province.ProvinceHover;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;

public class Touch {
   public static int buttonTouch = 0;
   public static int mousePosX = 0;
   public static int mousePosY = 0;
   public static boolean selectArmiesMode = false;
   public static final int CAPITAL_FLAG_L = 4;
   public static final int CAPITAL_FLAG_M = 1;

   protected static final void setMousePosXY(int nMousePosX, int nMousePosY) {
      mousePosX = nMousePosX;
      mousePosY = nMousePosY;
   }

   public static final int getMousePosX() {
      return mousePosX;
   }

   public static final int getMousePosY() {
      return mousePosY;
   }

   protected final void actionDown(int nPosX, int nPosY, int nPointer, int button) {
      if (nPointer == 0 && !Game.menuManager.actionDown(nPosX, nPosY)) {
         if (selectArmiesMode) {
            Game.mapTouchManager.iSelectBoxX = nPosX;
            Game.mapTouchManager.iSelectBoxY = nPosY;
            Game.mapTouchManager.iSelectBoxWidth = 1;
            Game.mapTouchManager.iSelectBoxHeight = 1;
            Game.mapTouchManager.selectMode = true;
            selectArmiesMode = false;
         } else {
            Game.mapTouchManager.actionDown(nPosX, nPosY, nPointer, button);
         }
      }
   }

   protected final void actionMove(int nPosX, int nPosY, int nPosX2, int nPosY2) {
      Game.mapTouchManager.actionMove(nPosX, nPosY, nPosX2, nPosY2);
   }

   protected final void actionMove(int nPosX, int nPosY, int nPointer) {
      if (nPointer == 0 && !Game.menuManager.actionMove(nPosX, nPosY)) {
         Game.mapTouchManager.actionMove(nPosX, nPosY, nPointer);
      }
   }

   protected final void actionUp(int nPosX, int nPosY, int nPointer, int button) {
      if (nPointer == 0) {
         if (!Game.menuManager.actionUp(nPosX, nPosY, nPointer, button)) {
            Game.mapTouchManager.actionUp(nPosX, nPosY, nPointer, button);
         }

         resetAllModes();
      } else if (Game.mapScale.getScaleMode()) {
         resetAllModes();
      }

      if (!CFG.isDesktop()) {
         Game.hoverManager.resetHoverActive_Menu();
      }
   }

   protected final void actionMove_Hover(int nPosX, int nPosY) {
      if (Game.menuManager.getFromViewID() < 0) {
         if (Game.hoverManager.actionMove_Hover(nPosX, nPosY, false)) {
         }

         if (Game.hoverManager.hoverActiveMenuElementID >= 0 || Game.hoverManager.hoverActiveSliderMenuID >= 0 || Game.hoverManager.hoverActiveMenuTitleID >= 0
            )
          {
            Game.hoveredArmy.iProvinceID = -1;
            Game.hoveredBattle.iProvinceID = -1;
            Game.hoveredSiegeID = -1;
            Game.hoveredShipID = -1;
            Game.provinceHover_Informations = null;
            Game.iHoveredProvinceID = -1;
         } else if (!Renderer.drawArmyInProvince) {
            Game.updateHoveredProvince_HoverInMapMode(nPosX, nPosY);
         } else if (!(Game.mapScale.getCurrentScale() >= Game.DRAW_ARMY_MIN_SCALE)) {
            Game.updateHoveredProvince_Hover(nPosX, nPosY);
         } else if (Game.menuManager.getInGame()) {
            if (this.updateHoveredArmy(nPosX, nPosY)) {
               Game.iHoveredProvinceID = -1;
               Game.hoveredBattle.iProvinceID = -1;
               Game.hoveredSiegeID = -1;
               Game.hoveredShipID = -1;
            } else if (this.updateHoveredBattle(nPosX, nPosY)) {
               Game.iHoveredProvinceID = -1;
               Game.hoveredArmy.iProvinceID = -1;
               Game.hoveredSiegeID = -1;
               Game.hoveredShipID = -1;
            } else if (this.updateHoveredSiege(nPosX, nPosY)) {
               Game.iHoveredProvinceID = -1;
               Game.hoveredArmy.iProvinceID = -1;
               Game.hoveredBattle.iProvinceID = -1;
               Game.hoveredShipID = -1;
            } else if (this.updateHoveredCapitalProvince_Flag(nPosX, nPosY)) {
               Game.iHoveredProvinceID = -1;
               Game.hoveredBattle.iProvinceID = -1;
               Game.hoveredSiegeID = -1;
               Game.hoveredShipID = -1;
            } else if (this.updateHoveredShip(nPosX, nPosY)) {
               Game.iHoveredProvinceID = -1;
               Game.hoveredArmy.iProvinceID = -1;
               Game.hoveredBattle.iProvinceID = -1;
               Game.hoveredSiegeID = -1;
            } else {
               Game.updateHoveredProvince_Hover(nPosX, nPosY);
            }
         } else if ((Game.menuManager.getInNewGame() || Game.menuManager.getInGameLost()) && this.updateHoveredCapitalProvince_Flag(nPosX, nPosY)) {
            Game.iHoveredProvinceID = -1;
            Game.hoveredBattle.iProvinceID = -1;
            Game.hoveredSiegeID = -1;
            Game.hoveredShipID = -1;
         } else {
            Game.updateHoveredProvince_Hover(nPosX, nPosY);
         }

         Game.hoverManager.updateHoveredMenuElement_Hover(nPosX, nPosY);
      }
   }

   public final boolean updateHoveredShip(int nPosX, int nPosY) {
      if (Game.hoveredShipID >= 0) {
         if (isMouseOverShip(nPosX, nPosY, Game.hoveredShipID)) {
            return true;
         }

         Game.hoveredShipID = -1;
      }

      for (int i = 0; i < ShipManager.shipLinesSize; i++) {
         if (isMouseOverShip(nPosX, nPosY, i)) {
            Game.hoveredShipID = i;
            ProvinceHover.updateShipHovered();
            return true;
         }
      }

      return false;
   }

   public final boolean updateHoveredCapitalProvince_Flag(int nPosX, int nPosY) {
      if (Game.iHoveredCapitalProvinceID >= 0) {
         if (isMouseOverCapitalProvince_Flag(nPosX, nPosY, Game.iHoveredCapitalProvinceID)) {
            return true;
         }

         Game.iHoveredCapitalProvinceID = -1;
      }

      for (int i = 0; i < Game.NUM_OF_PROVINCES_IN_VIEW; i++) {
         if (Game.getProvince(Game.getProvinceInViewID(i)).isCapital
            && Game.getProvince(Game.getProvinceInViewID(i)).getCitiesSize() > 0
            && isMouseOverCapitalProvince_Flag(nPosX, nPosY, Game.getProvinceInViewID(i))) {
            Game.iHoveredCapitalProvinceID = Game.getProvinceInViewID(i);
            ProvinceHover.updateProvinceHoverCapitalFlag();
            return true;
         }
      }

      for (int ix = 0; ix < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; ix++) {
         if (Game.getProvince(Game.getExtraProvinceInViewID(ix)).isCapital
            && Game.getProvince(Game.getExtraProvinceInViewID(ix)).getCitiesSize() > 0
            && isMouseOverCapitalProvince_Flag(nPosX, nPosY, Game.getExtraProvinceInViewID(ix))) {
            Game.iHoveredCapitalProvinceID = Game.getExtraProvinceInViewID(ix);
            ProvinceHover.updateProvinceHoverCapitalFlag();
            return true;
         }
      }

      return false;
   }

   public final boolean updateHoveredBattle(int nPosX, int nPosY) {
      try {
         if (Game.hoveredBattle.iProvinceID >= 0) {
            for (int i = 0; i < Game.battleManager.getBattleSize(); i++) {
               if (Game.battleManager.getBattle(i).provinceID == Game.hoveredBattle.iProvinceID
                  && Game.battleManager.getBattle(i).key.equals(Game.hoveredBattle.key)) {
                  if (isMouseOverBattle(nPosX, nPosY, i)) {
                     return true;
                  }

                  Game.hoveredBattle.iProvinceID = -1;
                  break;
               }
            }
         }

         for (int ix = 0; ix < Game.battleManager.getBattleSize(); ix++) {
            if (isMouseOverBattle(nPosX, nPosY, ix)) {
               Game.hoveredBattle.iProvinceID = Game.battleManager.getBattle(ix).provinceID;
               Game.hoveredBattle.key = Game.battleManager.getBattle(ix).key;
               ProvinceHover.updateProvinceHoverBattle();
               Game.animationHover.resetAnimationData();
               return true;
            }
         }
      } catch (Exception var4) {
      }

      return false;
   }

   public static final boolean isMouseOverBattle(int nPosX, int nPosY, int nBattleID) {
      try {
         return nPosX >= Game.battleManager.getBattle(nBattleID).posX
            && nPosX <= Game.battleManager.getBattle(nBattleID).posX + Game.battleManager.getBattle(nBattleID).battleWidth
            && nPosY >= Game.battleManager.getBattle(nBattleID).posY
            && nPosY <= Game.battleManager.getBattle(nBattleID).posY + ImageManager.getImage(Images.army).getHeight();
      } catch (Exception var4) {
         return false;
      }
   }

   public static final boolean isMouseOverSiege(int nPosX, int nPosY, int nProvinceID) {
      try {
         return nPosX >= SiegeManager.getArmyPosX(nProvinceID) - SiegeManager.getWidth() / 2
            && nPosX <= SiegeManager.getArmyPosX(nProvinceID) + SiegeManager.getWidth() / 2
            && nPosY >= SiegeManager.getArmyPosY(nProvinceID) - SiegeManager.getHeight()
            && nPosY <= SiegeManager.getArmyPosY(nProvinceID);
      } catch (Exception var4) {
         return false;
      }
   }

   public final boolean updateHoveredSiege(int nPosX, int nPosY) {
      try {
         if (Game.hoveredSiegeID >= 0) {
            if (Game.getProvinceData4(Game.hoveredSiegeID).isUnderSiege() && isMouseOverSiege(nPosX, nPosY, Game.hoveredSiegeID)) {
               return true;
            }

            Game.hoveredSiegeID = -1;
         }

         for (int i = 0; i < SiegeManager.iProvincesSize; i++) {
            if (isMouseOverSiege(nPosX, nPosY, SiegeManager.lProvinces.get(i))) {
               Game.hoveredSiegeID = SiegeManager.lProvinces.get(i);
               ProvinceHover.updateProvinceHoverSiege();
               Game.animationHover.resetAnimationData();
               return true;
            }
         }
      } catch (Exception var4) {
      }

      return false;
   }

   public final boolean updateHoveredArmy(int nPosX, int nPosY) {
      try {
         if (Game.hoveredArmy.iProvinceID >= 0) {
            if (Game.getProvince(Game.hoveredArmy.iProvinceID).getFogDrawArmy()) {
               if (Game.hoveredArmy.iArmyID < Game.getProvince(Game.hoveredArmy.iProvinceID).getArmySize()) {
                  if (!Game.getProvince(Game.hoveredArmy.iProvinceID).getArmy(Game.hoveredArmy.iArmyID).inBattle
                     && isMouseOverArmy(nPosX, nPosY, Game.hoveredArmy.iProvinceID, Game.hoveredArmy.iArmyID)) {
                     return true;
                  }

                  Game.hoveredArmy.iProvinceID = -1;
                  Game.hoveredArmy.iArmyID = 0;
               } else {
                  Game.hoveredArmy.iProvinceID = -1;
                  Game.hoveredArmy.iArmyID = 0;
               }
            } else {
               Game.hoveredArmy.iProvinceID = -1;
               Game.hoveredArmy.iArmyID = 0;
            }
         }

         for (int i = 0; i < Game.NUM_OF_PROVINCES_IN_VIEW; i++) {
            if (Game.getProvince(Game.getProvinceInViewID(i)).getFogDrawArmy()) {
               for (int j = 0; j < Game.getProvince(Game.getProvinceInViewID(i)).getArmySize(); j++) {
                  if (!Game.getProvince(Game.getProvinceInViewID(i)).getArmy(j).inBattle && isMouseOverArmy(nPosX, nPosY, Game.getProvinceInViewID(i), j)) {
                     Game.hoveredArmy.iProvinceID = Game.getProvinceInViewID(i);
                     Game.hoveredArmy.iArmyID = j;
                     Game.hoveredArmy.key = Game.getProvince(Game.getProvinceInViewID(i)).getArmy(j).key;
                     Game.hoveredArmy.iCivID = Game.getProvince(Game.getProvinceInViewID(i)).getArmy(j).civID;
                     ProvinceHover.updateProvinceHoverArmy();
                     Game.animationHover.resetAnimationData();
                     return true;
                  }
               }
            }
         }

         for (int ix = 0; ix < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; ix++) {
            if (Game.getProvince(Game.getExtraProvinceInViewID(ix)).getFogDrawArmy()) {
               for (int jx = 0; jx < Game.getProvince(Game.getExtraProvinceInViewID(ix)).getArmySize(); jx++) {
                  if (!Game.getProvince(Game.getExtraProvinceInViewID(ix)).getArmy(jx).inBattle
                     && isMouseOverArmy(nPosX, nPosY, Game.getExtraProvinceInViewID(ix), jx)) {
                     Game.hoveredArmy.iProvinceID = Game.getExtraProvinceInViewID(ix);
                     Game.hoveredArmy.iArmyID = jx;
                     Game.hoveredArmy.key = Game.getProvince(Game.getExtraProvinceInViewID(ix)).getArmy(jx).key;
                     Game.hoveredArmy.iCivID = Game.getProvince(Game.getExtraProvinceInViewID(ix)).getArmy(jx).civID;
                     ProvinceHover.updateProvinceHoverArmy();
                     Game.animationHover.resetAnimationData();
                     return true;
                  }
               }
            }
         }

         for (int ixx = 0; ixx < Game.NUM_OF_SEA_PROVINCES_IN_VIEW; ixx++) {
            if (Game.getProvince(Game.getSeaProvinceInViewID(ixx)).getFogDrawArmy()) {
               for (int jxx = 0; jxx < Game.getProvince(Game.getSeaProvinceInViewID(ixx)).getArmySize(); jxx++) {
                  if (!Game.getProvince(Game.getSeaProvinceInViewID(ixx)).getArmy(jxx).inBattle
                     && isMouseOverArmy(nPosX, nPosY, Game.getSeaProvinceInViewID(ixx), jxx)) {
                     Game.hoveredArmy.iProvinceID = Game.getSeaProvinceInViewID(ixx);
                     Game.hoveredArmy.iArmyID = jxx;
                     Game.hoveredArmy.key = Game.getProvince(Game.getSeaProvinceInViewID(ixx)).getArmy(jxx).key;
                     Game.hoveredArmy.iCivID = Game.getProvince(Game.getSeaProvinceInViewID(ixx)).getArmy(jxx).civID;
                     ProvinceHover.updateProvinceHoverArmy();
                     Game.animationHover.resetAnimationData();
                     return true;
                  }
               }
            }
         }

         for (int ixxx = 0; ixxx < Game.NUM_OF_WASTELAND_PROVINCES_IN_VIEW; ixxx++) {
            if (Game.getProvince(Game.getWastelandProvinceInViewID(ixxx)).getFogDrawArmy()) {
               for (int jxxx = 0; jxxx < Game.getProvince(Game.getWastelandProvinceInViewID(ixxx)).getArmySize(); jxxx++) {
                  if (!Game.getProvince(Game.getWastelandProvinceInViewID(ixxx)).getArmy(jxxx).inBattle
                     && isMouseOverArmy(nPosX, nPosY, Game.getWastelandProvinceInViewID(ixxx), jxxx)) {
                     Game.hoveredArmy.iProvinceID = Game.getWastelandProvinceInViewID(ixxx);
                     Game.hoveredArmy.iArmyID = jxxx;
                     Game.hoveredArmy.key = Game.getProvince(Game.getWastelandProvinceInViewID(ixxx)).getArmy(jxxx).key;
                     Game.hoveredArmy.iCivID = Game.getProvince(Game.getWastelandProvinceInViewID(ixxx)).getArmy(jxxx).civID;
                     ProvinceHover.updateProvinceHoverArmy();
                     Game.animationHover.resetAnimationData();
                     return true;
                  }
               }
            }
         }
      } catch (Exception var5) {
      }

      return false;
   }

   public static final boolean isMouseOverArmy(int nPosX, int nPosY, int nProvinceID, int nArmyID) {
      try {
         int nX = ProvinceDrawArmy.getArmyPosX(nProvinceID, nArmyID);
         int nY = ProvinceDrawArmy.getArmyPosY(nProvinceID, nArmyID);
         int nWidth = ProvinceDrawArmy.getArmyWidth(Game.getProvince(nProvinceID).getArmy(nArmyID).iArmyWidth);
         int nHeight = ProvinceDrawArmy.getArmyHeight();
         return nPosX >= nX && nPosX <= nX + nWidth && nPosY >= nY && nPosY <= nY + nHeight;
      } catch (Exception var8) {
         return false;
      }
   }

   public static final boolean isMouseOverCapitalProvince_Flag(int nPosX, int nPosY, int nProvinceID) {
      try {
         if (!Game.menuManager.getInGame() || Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_DIPLOMACY) {
            int nX = Game.getProvince(nProvinceID).getCity(0).getDrawPosX(nProvinceID, Game.mapScale.getCurrentScale())
               - Game.getProvince(nProvinceID).getCity(0).getDrawWidth() / 2;
            int nY = Game.getProvince(nProvinceID).getCity(0).getDrawPosY(nProvinceID, Game.mapScale.getCurrentScale());
            int nWidth = ImageManager.getImage(Images.capitalLeft).getWidth();
            int nHeight = ImageManager.getImage(Images.capitalLeft).getHeight();
            return nPosX >= nX && nPosX <= nX + nWidth && nPosY >= nY && nPosY <= nY + nHeight;
         } else if (Game.getCiv(Game.getProvince(nProvinceID).getCivID()).iCivRankID == 4) {
            int nX = Game.getProvince(nProvinceID).getCity(0).getDrawPosX(nProvinceID, Game.mapScale.getCurrentScale())
               - ImageManager.getImage(Images.flagCapitalOver_l).getWidth() / 2;
            int nY = Game.getProvince(nProvinceID).getCity(0).getDrawPosY(nProvinceID, Game.mapScale.getCurrentScale());
            int nWidth = ImageManager.getImage(Images.flagCapitalOver_l).getWidth();
            int nHeight = ImageManager.getImage(Images.flagCapitalOver_l).getHeight();
            return nPosX >= nX && nPosX <= nX + nWidth && nPosY >= nY && nPosY <= nY + nHeight;
         } else if (Game.getCiv(Game.getProvince(nProvinceID).getCivID()).iCivRankID > 1) {
            int nX = Game.getProvince(nProvinceID).getCity(0).getDrawPosX(nProvinceID, Game.mapScale.getCurrentScale())
               - ImageManager.getImage(Images.flagCapitalOver).getWidth() / 2;
            int nY = Game.getProvince(nProvinceID).getCity(0).getDrawPosY(nProvinceID, Game.mapScale.getCurrentScale());
            int nWidth = ImageManager.getImage(Images.flagCapitalOver).getWidth();
            int nHeight = ImageManager.getImage(Images.flagCapitalOver).getHeight();
            return nPosX >= nX && nPosX <= nX + nWidth && nPosY >= nY && nPosY <= nY + nHeight;
         } else {
            int nX = Game.getProvince(nProvinceID).getCity(0).getDrawPosX(nProvinceID, Game.mapScale.getCurrentScale())
               - ImageManager.getImage(Images.flagCapitalOver_s).getWidth() / 2;
            int nY = Game.getProvince(nProvinceID).getCity(0).getDrawPosY(nProvinceID, Game.mapScale.getCurrentScale());
            int nWidth = ImageManager.getImage(Images.flagCapitalOver_s).getWidth();
            int nHeight = ImageManager.getImage(Images.flagCapitalOver_s).getHeight();
            return nPosX >= nX && nPosX <= nX + nWidth && nPosY >= nY && nPosY <= nY + nHeight;
         }
      } catch (Exception var7) {
         return false;
      }
   }

   public static final boolean isMouseOverShip(int nPosX, int nPosY, int shipID) {
      try {
         int nX = (int)((ShipManager.ships.get(shipID).posX + Game.mapCoords.getPosX()) * Game.mapScale.getCurrentScale())
            - ShipManager.shipImg.get(0).get(0).getWidth() / 2;
         int nY = (int)((ShipManager.ships.get(shipID).posY + Game.mapCoords.getPosY()) * Game.mapScale.getCurrentScale())
            - ShipManager.shipImg.get(0).get(0).getHeight() / 2;
         int nWidth = ShipManager.shipImg.get(0).get(0).getWidth();
         int nHeight = ShipManager.shipImg.get(0).get(0).getHeight();
         return nPosX >= nX && nPosX <= nX + nWidth && nPosY >= nY && nPosY <= nY + nHeight;
      } catch (Exception var7) {
         return false;
      }
   }

   public static final void resetAllModes() {
      Game.menuManager.setActiveSliderMenuID(-1);
      Game.menuManager.setActiveMenuElementID(-1);
      Game.menuManager.setCloseMenuMode(false);
      Game.menuManager.setMenu_MoveByTitleMode(false);
      Game.menuManager.setMenu_ResizeMode(false);
      Game.menuManager.setMenu_MoveInnerElements(false);
      Game.menuManager.setPieChartMode(false);
      Game.menuManager.setTextScrollableMode(false);
      Game.menuManager.setColorPickerMode(false);
      Game.menuManager.graphVertical_ScrollMode_X = false;
      Game.menuManager.graphVertical_ScrollMode_Y = false;
      Game.mapScale.resetScaleInfo();
      Game.mapScroll.resetScrollInfo();
      Game.mapTouchManager.selectMode = false;
   }
}
