package aoc.kingdoms.lukasz.map.map;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;

public class MapScroll {
   protected static final float SCROLL_SLOW = 0.97F;
   private boolean scrollingTheMap = false;
   private int iScrollPosX;
   private int iScrollPosY;
   private int iScrollPosX2 = -1;
   private int iScrollPosY2 = -1;
   private float fScrollNewPosX;
   private float fScrollNewPosY;
   private long moveMapTime = 0L;
   private boolean moveMapDirection = false;
   private int iStepID = 0;
   private int iScrollEvent_PosX;
   private int iScrollEvent_PosY;
   private boolean scrollEvent = false;
   private MapScroll.ReverseDirection reverseDirectionX = null;
   private MapScroll.ReverseDirection reverseDirectionY = null;
   private static final int MAX_SCROLLING_SPEED = 500;

   public MapScroll() {
      this.buildReverseDirectionX();
      this.buildReverseDirectionY();
   }

   protected final void buildReverseDirectionX() {
      if (CFG.reverseDirectionX) {
         this.reverseDirectionX = new MapScroll.ReverseDirection() {
            @Override
            public int getNewPos(int nPosX) {
               return Game.mapCoords.getNewPosX() + nPosX;
            }
         };
      } else {
         this.reverseDirectionX = new MapScroll.ReverseDirection() {
            @Override
            public int getNewPos(int nPosX) {
               return Game.mapCoords.getNewPosX() - nPosX;
            }
         };
      }
   }

   protected final void buildReverseDirectionY() {
      if (CFG.reverseDirectionY) {
         this.reverseDirectionY = new MapScroll.ReverseDirection() {
            @Override
            public int getNewPos(int nPosY) {
               return Game.mapCoords.getNewPosY() + nPosY;
            }
         };
      } else {
         this.reverseDirectionY = new MapScroll.ReverseDirection() {
            @Override
            public int getNewPos(int nPosY) {
               return Game.mapCoords.getNewPosY() - nPosY;
            }
         };
      }
   }

   protected final void update() {
      if (this.scrollEvent) {
         if (this.iStepID < 14) {
            Game.mapCoords.setNewPosX(Game.mapCoords.getPosX() - (int)changeAnimationPos(this.iStepID, this.iScrollEvent_PosX));
            Game.mapCoords.setNewPosY(Game.mapCoords.getPosY() - (int)changeAnimationPos(this.iStepID++, this.iScrollEvent_PosY));
            if (this.iStepID == 14) {
               this.moveMapTime = CFG.currentTimeMillis;
               this.scrollEvent = false;
            }
         }
      } else if (this.scrollingTheMap && !Game.mapCoords.disableMovingMap) {
         if (!(Math.abs(this.fScrollNewPosX) > 1.0F) && !(Math.abs(this.fScrollNewPosY) > 1.0F)) {
            this.stopScrollingTheMap();
         } else {
            if (Math.abs(this.fScrollNewPosX) > 1.0F) {
               Game.mapCoords.setNewPosX(this.reverseDirectionX.getNewPos((int)this.fScrollNewPosX));
               this.fScrollNewPosX *= 0.97F;
            }

            if (Math.abs(this.fScrollNewPosY) > 1.0F) {
               Game.mapCoords.setNewPosY(this.reverseDirectionY.getNewPos((int)this.fScrollNewPosY));
               this.fScrollNewPosY *= 0.97F;
            }
         }
      }
   }

   protected static final float changeAnimationPos(int animationStepID, int nWidth) {
      switch (animationStepID) {
         case 0:
         case 1:
         case 12:
         case 13:
            return nWidth * 2.5F / 100.0F;
         case 2:
         case 3:
         case 10:
         case 11:
            return nWidth * 5.0F / 100.0F;
         case 4:
         case 5:
         case 8:
         case 9:
            return nWidth * 10.0F / 100.0F;
         case 6:
         case 7:
            return nWidth * 15.0F / 100.0F;
         default:
            return 0.0F;
      }
   }

   protected final void startScrollingTheMap() {
      if (!CFG.brushTool && (this.iScrollPosX2 >= 0 || this.iScrollPosY2 >= 0)) {
         if (Math.abs(this.iScrollPosX - this.iScrollPosX2) > (CFG.isDesktop() ? CFG.PADDING * 2.0F : 4.0F) * CFG.DENSITY) {
            this.fScrollNewPosX = (this.iScrollPosX - this.iScrollPosX2) * 1.2F * (CFG.reverseDirectionX ? 1 : -1);
            this.scrollingTheMap = true;
         }

         if (Math.abs(this.iScrollPosY - this.iScrollPosY2) > (CFG.isDesktop() ? CFG.PADDING * 2.0F : 4.0F) * CFG.DENSITY) {
            this.fScrollNewPosY = (this.iScrollPosY - this.iScrollPosY2) * 1.2F * (CFG.reverseDirectionY ? 1 : -1);
            this.scrollingTheMap = true;
         }

         if (Game.mapScale.getCurrentScale() > 1.0F) {
            this.fScrollNewPosX = Math.max(-500.0F, Math.min(500.0F, this.fScrollNewPosX) / Game.mapScale.getCurrentScale());
            this.fScrollNewPosY = Math.max(-500.0F, Math.min(500.0F, this.fScrollNewPosY) / Game.mapScale.getCurrentScale());
         } else {
            this.fScrollNewPosX = Math.max(-500.0F, Math.min(500.0F, this.fScrollNewPosX));
            this.fScrollNewPosY = Math.max(-500.0F, Math.min(500.0F, this.fScrollNewPosY));
         }
      }

      if (this.iScrollPosX != this.iScrollPosX2) {
         this.updateMoveMapDirection(this.iScrollPosX > this.iScrollPosX2);
      }

      this.resetScrollInfo();
   }

   public final void stopScrollingTheMap() {
      this.scrollingTheMap = false;
      this.scrollEvent = false;
      this.resetScrollInfo();
   }

   protected final void updateMoveMapDirection(boolean moveMapDirection) {
      this.moveMapDirection = moveMapDirection;
      this.moveMapTime = 0L;
   }

   public final void resetScrollInfo() {
      this.iScrollPosX = this.iScrollPosY = this.iScrollPosX2 = this.iScrollPosY2 = -1;
   }

   protected final void setScrollEvent(int nProvinceID) {
      this.setScrollEvent_Pos(
         (int)(Game.mapCoords.getPosX() + Game.getProvince(nProvinceID).getCenterX() - CFG.GAME_WIDTH / Game.mapScale.getCurrentScale() / 2.0F),
         (int)(Game.mapCoords.getPosY() + Game.getProvince(nProvinceID).getCenterY() - CFG.GAME_HEIGHT / Game.mapScale.getCurrentScale() / 2.0F)
      );
   }

   protected final void setScrollEvent_ToPosition(int nPosX, int nPosY) {
      this.setScrollEvent_Pos(
         (int)(Game.mapCoords.getPosX() + nPosX - CFG.GAME_WIDTH / Game.mapScale.getCurrentScale() / 2.0F),
         (int)(Game.mapCoords.getPosY() + nPosY - CFG.GAME_HEIGHT / Game.mapScale.getCurrentScale() / 2.0F)
      );
   }

   private final void setScrollEvent_Pos(int nPosX, int nPosY) {
      if (!this.scrollEvent) {
         this.scrollEvent = true;
         this.iStepID = 0;
         this.iScrollEvent_PosX = nPosX;
         this.iScrollEvent_PosY = nPosY;
         this.moveMapTime = CFG.currentTimeMillis + 208L;
      }
   }

   public final void setScrollPos(int nPosX, int nPosY) {
      this.iScrollPosX2 = this.iScrollPosX;
      this.iScrollPosY2 = this.iScrollPosY;
      this.iScrollPosX = nPosX;
      this.iScrollPosY = nPosY;
   }

   protected boolean getScrollingTheMap() {
      return this.scrollingTheMap;
   }

   private interface ReverseDirection {
      int getNewPos(int var1);
   }
}
