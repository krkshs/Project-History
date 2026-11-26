package aoc.kingdoms.lukasz.map.map;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;

public class MapEdgeMove {
   public int MAP_MOVE_KEYBOARD = 0;
   public boolean MAP_MOVE_LEFT = false;
   public boolean MAP_MOVE_RIGHT = false;
   public boolean MAP_MOVE_TOP = false;
   public boolean MAP_MOVE_BOT = false;
   private static int PADDING_EDGE_MOVE = 10;
   private static int MAX_MOVE_SPEED = 45;
   public static final int DEFAULT_SCROLL_MAP = 30;
   public float iScroll_MAP = 30.0F;
   public float fScroll_XorY_Perc = 0.0F;
   public long lScrollTime_MAP = 0L;
   private long lScrollResetTime = 0L;
   private static int SCROLL_RESET_TIME = 350;
   public static final int DEFAULT_SCROLL = PADDING_EDGE_MOVE;
   private int iScroll = DEFAULT_SCROLL;
   private long lScrollTime = 0L;
   private int iMousePosX = 100;
   private int iMousePosY = 100;
   private int extraTime = 125;
   private final int NEXT_SCROLL_TIME = 10;

   public final void MouseMoved_EdgeMove(int screenX, int screenY) {
      if (CFG.isDesktop && this.MAP_MOVE_KEYBOARD == 0) {
         this.iMousePosX = screenX;
         this.iMousePosY = screenY;
         if (Game.settingsManager.ENABLE_EDGE_SCROLL) {
            if (screenX < PADDING_EDGE_MOVE) {
               if (!this.MAP_MOVE_LEFT) {
                  this.MAP_MOVE_LEFT = true;
                  this.MAP_MOVE_RIGHT = false;
                  this.resetScrollMap();
                  Game.mapScroll.stopScrollingTheMap();
                  this.MAP_MOVE_KEYBOARD = 0;
               }
            } else {
               this.MAP_MOVE_LEFT = false;
            }

            if (screenX > CFG.GAME_WIDTH - PADDING_EDGE_MOVE) {
               if (!this.MAP_MOVE_RIGHT) {
                  this.MAP_MOVE_RIGHT = true;
                  this.MAP_MOVE_LEFT = false;
                  this.resetScrollMap();
                  Game.mapScroll.stopScrollingTheMap();
                  this.MAP_MOVE_KEYBOARD = 0;
               }
            } else {
               this.MAP_MOVE_RIGHT = false;
            }

            if (screenY < PADDING_EDGE_MOVE) {
               if (!this.MAP_MOVE_TOP) {
                  this.MAP_MOVE_TOP = true;
                  this.MAP_MOVE_BOT = false;
                  this.resetScrollMap();
                  Game.mapScroll.stopScrollingTheMap();
                  this.MAP_MOVE_KEYBOARD = 0;
               }
            } else {
               this.MAP_MOVE_TOP = false;
            }

            if (screenY > CFG.GAME_HEIGHT - PADDING_EDGE_MOVE) {
               if (!this.MAP_MOVE_BOT) {
                  this.MAP_MOVE_BOT = true;
                  this.MAP_MOVE_TOP = false;
                  this.resetScrollMap();
                  Game.mapScroll.stopScrollingTheMap();
                  this.MAP_MOVE_KEYBOARD = 0;
               }
            } else {
               this.MAP_MOVE_BOT = false;
            }
         }
      }
   }

   private final void resetScrollMap() {
      if (this.lScrollResetTime + SCROLL_RESET_TIME < CFG.currentTimeMillis) {
         this.lScrollTime_MAP = CFG.currentTimeMillis + this.extraTime;
         this.iScroll_MAP = DEFAULT_SCROLL;
      }
   }

   public final void updateMoveMap() {
      try {
         if (this.MAP_MOVE_KEYBOARD > 0) {
            if (this.MAP_MOVE_LEFT) {
               this.updateScroll_MapX();
               Game.mapCoords.setNewPosX(Game.mapCoords.getPosX() + (int)this.iScroll_MAP);
            } else if (this.MAP_MOVE_RIGHT) {
               this.updateScroll_MapX();
               Game.mapCoords.setNewPosX(Game.mapCoords.getPosX() - (int)this.iScroll_MAP);
            }

            if (this.MAP_MOVE_TOP) {
               this.updateScroll_MapY();
               Game.mapCoords.setNewPosY(Game.mapCoords.getPosY() + (int)this.iScroll_MAP);
            } else if (this.MAP_MOVE_BOT) {
               this.updateScroll_MapY();
               Game.mapCoords.setNewPosY(Game.mapCoords.getPosY() - (int)this.iScroll_MAP);
            }
         } else if (this.MAP_MOVE_LEFT) {
            this.updateScroll_MapX();
            Game.mapCoords.setNewPosX(Game.mapCoords.getPosX() + (int)this.iScroll_MAP);
            Game.mapCoords.setNewPosY((int)(Game.mapCoords.getPosY() - (int)this.iScroll_MAP * this.fScroll_XorY_Perc));
         } else if (this.MAP_MOVE_RIGHT) {
            this.updateScroll_MapX();
            Game.mapCoords.setNewPosX(Game.mapCoords.getPosX() - (int)this.iScroll_MAP);
            Game.mapCoords.setNewPosY((int)(Game.mapCoords.getPosY() - (int)this.iScroll_MAP * this.fScroll_XorY_Perc));
         } else if (this.MAP_MOVE_TOP) {
            this.updateScroll_MapY();
            Game.mapCoords.setNewPosY(Game.mapCoords.getPosY() + (int)this.iScroll_MAP);
            Game.mapCoords.setNewPosX((int)(Game.mapCoords.getPosX() - (int)this.iScroll_MAP * this.fScroll_XorY_Perc));
         } else if (this.MAP_MOVE_BOT) {
            this.updateScroll_MapY();
            Game.mapCoords.setNewPosY(Game.mapCoords.getPosY() - (int)this.iScroll_MAP);
            Game.mapCoords.setNewPosX((int)(Game.mapCoords.getPosX() - (int)this.iScroll_MAP * this.fScroll_XorY_Perc));
         }
      } catch (IndexOutOfBoundsException var2) {
         CFG.LOG(var2);
      } catch (NullPointerException var3) {
         CFG.LOG(var3);
      } catch (ArithmeticException var4) {
         CFG.LOG(var4);
      }
   }

   private final void updateScroll_MapX() {
      if (this.iMousePosY > CFG.GAME_HEIGHT / 2) {
         this.fScroll_XorY_Perc = (this.iMousePosY - CFG.GAME_HEIGHT / 2.0F) / (CFG.GAME_HEIGHT / 2.0F);
      } else {
         this.fScroll_XorY_Perc = -(1.0F - this.iMousePosY / (CFG.GAME_HEIGHT / 2.0F));
      }

      this.lScrollResetTime = CFG.currentTimeMillis;
      if (this.lScrollTime_MAP + 10L < CFG.currentTimeMillis) {
         this.lScrollTime_MAP = CFG.currentTimeMillis;
         if (Game.mapScale.getCurrentScale() < 1.0F) {
            this.iScroll_MAP = this.iScroll_MAP + this.iScroll_MAP * (0.13F + (1.0F - Game.mapScale.getCurrentScale()));
         } else {
            this.iScroll_MAP = this.iScroll_MAP + this.iScroll_MAP * (0.13F / Game.mapScale.getCurrentScale());
         }

         if (this.iScroll_MAP > this.getMaxMoveSpeed()) {
            this.iScroll_MAP = this.getMaxMoveSpeed();
         }
      }
   }

   private final void updateScroll_MapY() {
      if (this.iMousePosX > CFG.GAME_WIDTH / 2) {
         this.fScroll_XorY_Perc = (this.iMousePosX - CFG.GAME_WIDTH / 2.0F) / (CFG.GAME_WIDTH / 2.0F);
      } else {
         this.fScroll_XorY_Perc = -(1.0F - this.iMousePosX / (CFG.GAME_WIDTH / 2.0F));
      }

      this.lScrollResetTime = CFG.currentTimeMillis;
      if (this.lScrollTime_MAP + 10L < CFG.currentTimeMillis) {
         this.lScrollTime_MAP = CFG.currentTimeMillis;
         this.iScroll_MAP = this.iScroll_MAP
            + this.iScroll_MAP * (0.2F + (Game.mapScale.getCurrentScale() < 1.0F ? 1.0F - Game.mapScale.getCurrentScale() : 0.0F));
         if (this.iScroll_MAP > this.getMaxMoveSpeed()) {
            this.iScroll_MAP = this.getMaxMoveSpeed();
         }
      }
   }

   private final int getMaxMoveSpeed() {
      return Game.mapScale.getCurrentScale() < 1.0F
         ? (int)(MAX_MOVE_SPEED * (1.0F + (1.0F - Game.mapScale.getCurrentScale()) * 2.0F))
         : (int)(MAX_MOVE_SPEED * (1.0F - 0.75F * (Game.mapScale.getCurrentScale() / MapScale.defScales.definedScales[0])));
   }
}
