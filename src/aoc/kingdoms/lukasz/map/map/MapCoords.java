package aoc.kingdoms.lukasz.map.map;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.map.civilization.CivilizationRegionsManager;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;

public class MapCoords {
   private int iPosX = 0;
   private int iPosY = 0;
   private int iNewPosX = 0;
   private int iNewPosY = 0;
   private boolean secondSideOfMap = false;
   private int iSecondSideOfMap_TranslateX = 0;
   public boolean disableMovingMap = false;
   private int iMinPosY;
   private int iMaxPosY;
   private int iMinPosScaledY;
   private int iMaxPosScaledY;
   private int iMinPosScaledX;
   private boolean drawMapBorder = true;
   private MapCoords.WorldMap worldMap;

   protected final void updateWorldMap() {
      if (Game.map.getMapWorldMap(Game.map.getActiveMapID())) {
         this.worldMap = new MapCoords.WorldMap() {
            @Override
            public void updateSecondSideOfMap() {
               MapCoords.this.secondSideOfMap = -MapCoords.this.iPosX + CFG.GAME_WIDTH / Game.mapScale.getCurrentScale() >= Game.mapBG.getWidth();
               if (MapCoords.this.secondSideOfMap) {
                  MapCoords.this.iSecondSideOfMap_TranslateX = Game.mapBG.getWidth();
               } else {
                  MapCoords.this.iSecondSideOfMap_TranslateX = 0;
               }
            }

            @Override
            public void updateMapPosX() {
               if (Math.abs(MapCoords.this.iNewPosX) > Game.mapBG.getWidth()) {
                  MapCoords.this.iPosX = Game.mapBG.getWidth() + MapCoords.this.iNewPosX;
                  Game.mapTouchManager.setUpdateStartMovePosX(true);
               } else if (MapCoords.this.iNewPosX > 0) {
                  MapCoords.this.iPosX = -Game.mapBG.getWidth() + MapCoords.this.iNewPosX;
                  Game.mapTouchManager.setUpdateStartMovePosX(true);
               } else {
                  MapCoords.this.iPosX = MapCoords.this.iNewPosX;
               }

               MapCoords.this.checkPositionOfMapX();
               this.updateSecondSideOfMap();
            }
         };
      } else {
         this.worldMap = new MapCoords.WorldMap() {
            @Override
            public void updateSecondSideOfMap() {
               MapCoords.this.secondSideOfMap = false;
               MapCoords.this.iSecondSideOfMap_TranslateX = 0;
            }

            @Override
            public void updateMapPosX() {
               if (Math.abs(MapCoords.this.iNewPosX)
                  >= Game.mapBG.getWidth() - CFG.GAME_WIDTH / Game.mapScale.getCurrentScale() + MapCoords.this.iMinPosScaledX) {
                  MapCoords.this.iPosX = (int)(-Game.mapBG.getWidth() - MapCoords.this.iMinPosScaledX + CFG.GAME_WIDTH / Game.mapScale.getCurrentScale());
                  Game.mapTouchManager.setUpdateStartMovePosX(true);
               } else if (MapCoords.this.iNewPosX >= MapCoords.this.iMinPosScaledX) {
                  MapCoords.this.iPosX = MapCoords.this.iMinPosScaledX;
                  Game.mapTouchManager.setUpdateStartMovePosX(true);
               } else {
                  MapCoords.this.iPosX = MapCoords.this.iNewPosX;
               }

               if (MapCoords.this.iPosX >= MapCoords.this.iMinPosScaledX) {
                  MapCoords.this.iPosX = MapCoords.this.iNewPosX = MapCoords.this.iMinPosScaledX;
               }

               MapCoords.this.checkPositionOfMapX();
            }
         };
      }
   }

   public final void updateMapPosition() {
      if (this.iPosX != this.iNewPosX) {
         Game.setUpdateProvincesInView(true);
         CivilizationRegionsManager.updateRegionsInView = true;
         this.worldMap.updateMapPosX();
      }

      if (this.iPosY != this.iNewPosY) {
         Game.setUpdateProvincesInView(true);
         CivilizationRegionsManager.updateRegionsInView = true;
         if (this.iNewPosY > (int)((this.iMinPosY + this.iMinPosScaledY * Game.mapScale.getCurrentScale()) / Game.mapScale.getCurrentScale())) {
            this.iPosY = (int)((this.iMinPosY + this.iMinPosScaledY * Game.mapScale.getCurrentScale()) / Game.mapScale.getCurrentScale());
            Game.mapTouchManager.setUpdateStartMovePosY(true);
         } else if (-this.iNewPosY + CFG.GAME_HEIGHT / Game.mapScale.getCurrentScale()
            > Game.mapBG.getHeight() + (this.iMaxPosY + this.iMaxPosScaledY * Game.mapScale.getCurrentScale()) / Game.mapScale.getCurrentScale()) {
            this.iPosY = -(
               (int)(
                  Game.mapBG.getHeight()
                     - CFG.GAME_HEIGHT / Game.mapScale.getCurrentScale()
                     + (this.iMaxPosY + this.iMaxPosScaledY * Game.mapScale.getCurrentScale()) / Game.mapScale.getCurrentScale()
               )
            );
            Game.mapTouchManager.setUpdateStartMovePosY(true);
         } else {
            this.iPosY = this.iNewPosY;
         }

         this.checkPositionOfMapY();
      }
   }

   protected final void checkPositionOfMapX() {
      if (-this.iNewPosX > Game.mapBG.getWidth()) {
         this.iPosX = this.iPosX % Game.mapBG.getWidth();
         this.iNewPosX = this.iPosX;
      } else if (this.iPosX > 0) {
         this.iPosX = this.iPosX % Game.mapBG.getWidth();
         this.iNewPosX = this.iPosX;
      }
   }

   protected final void checkPositionOfMapY() {
      if (-this.iPosY > Game.mapBG.getHeight() + Game.mapBG.mapBorder.OutsideTheMapMaxY_Below) {
         this.iPosY = this.iPosY % (Game.mapBG.getHeight() + Game.mapBG.mapBorder.OutsideTheMapMaxY_Below);
         this.iNewPosY = this.iPosY;
      } else if (this.iPosY > (this.iMinPosY + this.iMinPosScaledY * Game.mapScale.getCurrentScale()) / Game.mapScale.getCurrentScale()) {
         this.iPosY = (int)((this.iMinPosY + this.iMinPosScaledY * Game.mapScale.getCurrentScale()) / Game.mapScale.getCurrentScale());
         this.iNewPosY = this.iPosY;
      }
   }

   public final void updateMinMaxPosY() {
      this.iMinPosY = 0;
      this.iMaxPosY = 0;
      if (this.drawMapBorder) {
         this.iMinPosScaledY = ImageManager.getImage(Images.map_border).getHeight()
            - Game.mapBG.mapBorder.ShadowY
            + Game.mapBG.mapBorder.OutsideTheMapMaxY_Over;
         this.iMaxPosScaledY = ImageManager.getImage(Images.map_border).getHeight()
            - Game.mapBG.mapBorder.ShadowY
            + Game.mapBG.mapBorder.OutsideTheMapMaxY_Below;
         if (!Game.map.getMapWorldMap(Game.map.getActiveMapID())) {
            this.iMinPosScaledX = ImageManager.getImage(Images.map_border).getHeight() - Game.mapBG.mapBorder.ShadowY;
         } else {
            this.iMinPosScaledX = 0;
         }
      } else {
         this.iMinPosScaledY = 0;
         this.iMaxPosScaledY = 0;
         this.iMinPosScaledX = 0;
      }
   }

   public final void updateSecondSideOfMap() {
      this.worldMap.updateSecondSideOfMap();
   }

   public final void centerToProvinceID(int i) {
      try {
         Game.mapScroll.stopScrollingTheMap();
         Game.mapScroll.setScrollEvent(i);
      } catch (Exception var3) {
      }
   }

   public final void centerToMinimapClick(int nX, int nY) {
      float tempScaleX = Game.mapBG.iMinimapScaled_Width / Game.mapBG.iMinimapWidth;
      float tempScaleY = Game.mapBG.iMinimapScaled_Height / Game.mapBG.iMinimapHeight;
      Game.mapScroll.stopScrollingTheMap();
      Game.mapScroll
         .setScrollEvent_ToPosition(Game.mapBG.iMinimapScaled_PosX + (int)(nX * tempScaleX), Game.mapBG.iMinimapScaled_PosY + (int)(nY * tempScaleY));
   }

   public final int getMinimapPosX(int nX) {
      float tempScaleX = Game.mapBG.iMinimapScaled_Width / Game.mapBG.iMinimapWidth;
      return Game.mapBG.iMinimapScaled_PosX + (int)(nX * tempScaleX);
   }

   public final int getMinimapPosY(int nY) {
      float tempScaleY = Game.mapBG.iMinimapScaled_Height / Game.mapBG.iMinimapHeight;
      return Game.mapBG.iMinimapScaled_PosY + (int)(nY * tempScaleY);
   }

   protected final int getNewPosY() {
      return this.iNewPosY;
   }

   public final void setNewPosY(int iNewPosY) {
      this.iNewPosY = iNewPosY;
   }

   protected final int getNewPosX() {
      return this.iNewPosX;
   }

   public final void setNewPosX(int iNewPosX) {
      this.iNewPosX = iNewPosX;
   }

   public int getPosY() {
      return this.iPosY;
   }

   public void setPosY(int posY) {
      this.iPosY = posY;
   }

   public int getPosX() {
      return this.iPosX;
   }

   public void setPosX(int posX) {
      this.iPosX = posX;
   }

   public boolean getDisableMovingMap() {
      return this.disableMovingMap;
   }

   public void setDisableMovingMap(boolean disableMovingMap) {
      this.disableMovingMap = disableMovingMap;
   }

   public final boolean getSecondSideOfMap() {
      return this.secondSideOfMap;
   }

   public final int getSecondSideOfMap_MoveX() {
      return this.iSecondSideOfMap_TranslateX;
   }

   private interface WorldMap {
      void updateSecondSideOfMap();

      void updateMapPosX();
   }
}
