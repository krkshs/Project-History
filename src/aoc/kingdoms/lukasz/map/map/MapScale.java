package aoc.kingdoms.lukasz.map.map;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.FileManager;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Touch;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

public class MapScale {
   public static float MINSCALE = 0.01F;
   public static final float MAXSCALE = 20.0F;
   public static float STANDARD_SCALE = 1.0F;
   private float currentScale = 1.0F;
   private float newScale = -1.0F;
   private boolean enableScaling = false;
   public boolean scaleMode;
   private static final short REQUIRED_TIME_TO_RESET_SCALE = 175;
   private float fScaleBeforeReset = 1.5F;
   private float changeCurrentScaleByX;
   private boolean scaleChangeByTouch = true;
   protected static int animation_TIME_TO_END = 100;
   protected long animation_TIME_STARTED = 0L;
   protected float animation_StartingScale = 1.0F;
   private float fScaleAnimation_PercX = 1.0F;
   private float fScaleAnimation_PercY = 1.0F;
   private int iStartScalePosY;
   private int iStartScalePosY2;
   private int iStartScalePosX;
   private int iStartScalePosX2;
   public int definedScale = 1;
   private int definedScaleBeforeReset = 1;
   public int definedScalesLength = 1;
   public static MapScale.DefinedScales defScales = new MapScale.DefinedScales();
   public float startScale = -1.0F;
   public boolean scaleByYAxis = true;
   private int iStartScaleMapPosX = -1;
   private int iStartScaleMapPosY = -1;

   protected final void updateAnimationScale_CenterToXY(int nPosX, int nPosY) {
      this.fScaleAnimation_PercX = (float)nPosX / CFG.GAME_WIDTH;
      this.fScaleAnimation_PercY = (float)nPosY / CFG.GAME_HEIGHT;
   }

   public final void update() {
      if (this.changeCurrentScaleByX != 0.0F) {
         this.updateScale();
      }
   }

   private final void updateScale() {
      float oldCurrentScale = this.currentScale;
      this.setCurrentScale(
         this.animation_StartingScale
            + this.changeCurrentScaleByX
               * (float)Math.min(CFG.currentTimeMillis - this.animation_TIME_STARTED, (long)animation_TIME_TO_END)
               / animation_TIME_TO_END
      );
      if (CFG.currentTimeMillis - this.animation_TIME_STARTED > animation_TIME_TO_END) {
         if (this.fScaleBeforeReset != STANDARD_SCALE && this.scaleChangeByTouch || this.currentScale > 0.9925F && this.currentScale < 1.0075F) {
            this.setCurrentScale(STANDARD_SCALE);
         }

         this.resetScaleAnimation();
      }

      Game.mapCoords
         .setNewPosX((int)(Game.mapCoords.getPosX() - (CFG.GAME_WIDTH / oldCurrentScale - CFG.GAME_WIDTH / this.currentScale) * this.fScaleAnimation_PercX));
      Game.mapCoords
         .setNewPosY((int)(Game.mapCoords.getPosY() - (CFG.GAME_HEIGHT / oldCurrentScale - CFG.GAME_HEIGHT / this.currentScale) * this.fScaleAnimation_PercY));
   }

   private final void resetScaleAnimation() {
      this.changeCurrentScaleByX = 0.0F;
      this.animation_TIME_STARTED = 0L;
   }

   public final void initDefinedScales() {
      try {
         Json json = new Json();
         FileHandle file = FileManager.loadFile(
            "map/"
               + Game.map.getFile_ActiveMap_Path()
               + "/"
               + "data/"
               + "scales/"
               + (Game.highTextureSettings ? "DefinedScales.json" : "DefinedScales_Low.json")
         );
         defScales = json.fromJson(MapScale.DefinedScales.class, file);
         this.definedScale = defScales.definedScale_Default;
         this.definedScaleBeforeReset = defScales.definedScale_Default;
         this.definedScalesLength = defScales.definedScale_Default;
         int i = 0;

         for (int iSize = defScales.definedScales.length; i < iSize; i++) {
            if (defScales.definedScales[i] < MINSCALE) {
               defScales.definedScales[i] = MINSCALE;
            }
         }

         if (defScales.definedScales[defScales.definedScales.length - 1] > MINSCALE) {
            defScales.definedScales[defScales.definedScales.length - 1] = MINSCALE;
         }

         this.definedScalesLength = defScales.definedScales.length;
      } catch (Exception var5) {
         CFG.LOG("Error loading: DefinedScales");
         CFG.exceptionStack(var5);
      }
   }

   private float getDefinedScale() {
      try {
         return defScales.definedScales[this.definedScale];
      } catch (IndexOutOfBoundsException var2) {
         return 1.0F;
      }
   }

   public final void scrollScale(int changeScaleByX) {
      if (!Game.menuManager.getInMainMenu()
         && !Game.menuManager.getInScenarios_NewGame()
         && !Game.menuManager.getInLoadGamesList()
         && !Game.menuManager.getInGameLegacies()) {
         if (!Game.mapTouchManager.selectMode) {
            this.definedScale += changeScaleByX;
            if (this.definedScale < 0) {
               this.definedScale = 0;
            } else if (this.definedScale >= defScales.definedScales.length) {
               this.definedScale = defScales.definedScales.length - 1;
            }

            float newMapScale = this.getDefinedScale();
            if (newMapScale >= 0.995F && newMapScale <= 1.005F) {
               newMapScale = STANDARD_SCALE;
            }

            if (newMapScale != this.currentScale && newMapScale >= MINSCALE - 0.05F) {
               this.resetScaleAnimation();
               this.scaleChangeByTouch = false;
               animation_TIME_TO_END = 125;
               this.animation_StartingScale = this.currentScale;
               this.changeCurrentScaleByX = newMapScale - this.currentScale;
               this.fScaleBeforeReset = newMapScale;
               this.animation_TIME_STARTED = CFG.currentTimeMillis;
               this.updateAnimationScale_CenterToXY(Touch.getMousePosX(), Touch.getMousePosY());
            }
         }
      }
   }

   public float scrollScaleChange(boolean zoomIN) {
      float zoom = zoomIN ? -1.0F : 1.0F;
      if (zoomIN) {
         if (this.getCurrentScale() >= 0.75F) {
            return 0.05F * zoom;
         } else {
            return this.getCurrentScale() >= 0.5F ? 0.02F * zoom : 0.01F * zoom;
         }
      } else {
         return 0.1F * zoom;
      }
   }

   protected final void resetScaleOfMap(long nActionDownTime) {
      if ((Game.settingsManager.ENABLE_DOUBLE_CLICK_TO_RESET_MAP_SCALE || CFG.isAndroid())
         && nActionDownTime > 0L
         && this.changeCurrentScaleByX == 0.0F
         && CFG.currentTimeMillis < Game.mapTouchManager.getActionDownTime() + 175L
         && !Game.mapCoords.getDisableMovingMap()) {
         this.resetScaleAnimation();
         this.scaleChangeByTouch = true;
         this.animation_StartingScale = this.currentScale;
         if (this.currentScale != STANDARD_SCALE) {
            this.fScaleBeforeReset = this.currentScale;
            this.changeCurrentScaleByX = STANDARD_SCALE - this.currentScale;
            this.definedScale = this.definedScaleBeforeReset;
         } else {
            this.changeCurrentScaleByX = this.fScaleBeforeReset - this.currentScale;
            this.fScaleBeforeReset = STANDARD_SCALE;
            this.definedScaleBeforeReset = this.definedScale;
            this.definedScale = defScales.definedScale_Default;
         }

         this.animation_TIME_STARTED = CFG.currentTimeMillis;
         this.updateAnimationScale_CenterToXY(Touch.getMousePosX(), Touch.getMousePosY());
         animation_TIME_TO_END = 100;
         Game.mapTouchManager.updateStartMovePosX = true;
         Game.mapTouchManager.updateStartMovePosY = true;
         Game.mapScroll.resetScrollInfo();
      }

      Game.mapTouchManager.setActionDownTime(nActionDownTime);
   }

   public final void startScaleTheMap(int nX, int nX2, int nY, int nY2) {
      this.scaleMode = true;
      if (Math.max(nX, nX2) - Math.min(nX, nX2) > Math.max(nY, nY2) - Math.min(nY, nY2)) {
         this.scaleByYAxis = false;
         this.iStartScalePosY = nX;
         this.iStartScalePosY2 = nX2;
      } else {
         this.scaleByYAxis = true;
         this.iStartScalePosY = nY;
         this.iStartScalePosY2 = nY2;
      }
   }

   public final void scaleTheMap(int nX, int nX2, int nY, int nY2) {
      if (this.scaleByYAxis) {
         this.scaleTheMap(nY, nY2, Math.abs((nX + nX2) / 2.0F), Math.abs((nY + nY2) / 2.0F));
      } else {
         this.scaleTheMap(nX, nX2, Math.abs((nX + nX2) / 2.0F), Math.abs((nY + nY2) / 2.0F));
      }
   }

   public final void scaleTheMap(int nY, int nY2, float fCenterX, float fCenterY) {
      if (this.startScale < 0.0F) {
         this.iStartScaleMapPosX = Game.mapCoords.getPosX();
         this.iStartScaleMapPosY = Game.mapCoords.getPosY();
         this.startScale = this.currentScale;
      }

      if (this.iStartScalePosY != nY) {
         this.setNewCurrentScaleByTouch(
            this.currentScale + (nY < nY2 ? this.iStartScalePosY - nY : nY - this.iStartScalePosY) / 150.0F / CFG.GUI_SCALE, fCenterX, fCenterY
         );
         this.iStartScalePosY = nY;
         Game.mapTouchManager.updateStartMovePosX = true;
         Game.mapTouchManager.updateStartMovePosY = true;
         this.resetScaleAnimation();
      }

      if (this.iStartScalePosY2 != nY2) {
         this.setNewCurrentScaleByTouch(
            this.currentScale + (nY > nY2 ? this.iStartScalePosY2 - nY2 : nY2 - this.iStartScalePosY2) / 150.0F / CFG.GUI_SCALE, fCenterX, fCenterY
         );
         this.iStartScalePosY2 = nY2;
         Game.mapTouchManager.updateStartMovePosX = true;
         Game.mapTouchManager.updateStartMovePosY = true;
         this.resetScaleAnimation();
      }
   }

   public final void resetStartScalePosition() {
      this.iStartScalePosY = this.iStartScalePosY2 = -1;
   }

   public final void resetScaleInfo() {
      this.resetStartScalePosition();
      this.scaleMode = false;
      this.startScale = -1.0F;
   }

   public final void setNewCurrentScaleByTouch(float nCurrentScale, float fCenterX, float fCenterY) {
      if (nCurrentScale > 20.0F) {
         this.newScale = 20.0F;
      } else if (nCurrentScale < MINSCALE) {
         this.newScale = MINSCALE;
      } else {
         this.newScale = nCurrentScale;
      }

      this.scaleChangeByTouch = true;
      if (this.newScale > 0.0F) {
         if (this.currentScale != this.newScale) {
            this.fScaleAnimation_PercX = fCenterX / CFG.GAME_WIDTH;
            this.fScaleAnimation_PercY = fCenterY / CFG.GAME_HEIGHT;
            if (this.startScale < this.currentScale) {
               Game.mapCoords
                  .setNewPosX(this.iStartScaleMapPosX - (int)((CFG.GAME_WIDTH / this.startScale - CFG.GAME_WIDTH / this.newScale) * this.fScaleAnimation_PercX));
               Game.mapCoords
                  .setNewPosY(
                     this.iStartScaleMapPosY - (int)((CFG.GAME_HEIGHT / this.startScale - CFG.GAME_HEIGHT / this.newScale) * this.fScaleAnimation_PercY)
                  );
            } else {
               Game.mapCoords.setNewPosX(this.iStartScaleMapPosX - (int)((CFG.GAME_WIDTH / this.startScale - CFG.GAME_WIDTH / this.newScale) / 2.0F));
               Game.mapCoords.setNewPosY(this.iStartScaleMapPosY - (int)((CFG.GAME_HEIGHT / this.startScale - CFG.GAME_HEIGHT / this.newScale) / 2.0F));
            }

            this.currentScale = this.newScale;
            this.newScale = 0.0F;
         }

         Game.mapCoords.checkPositionOfMapY();
         Game.mapCoords.checkPositionOfMapX();
         Game.mapCoords.updateSecondSideOfMap();
      }
   }

   public float getCurrentScale() {
      return this.currentScale;
   }

   public void setCurrentScale(float currentScale) {
      if (20.0F < currentScale) {
         currentScale = 20.0F;
      } else if (MINSCALE > currentScale) {
         currentScale = MINSCALE;
      }

      this.currentScale = currentScale;
      Renderer.updateBackgroundColor = true;
   }

   public boolean getEnableScaling() {
      return this.enableScaling;
   }

   public void setEnableScaling(boolean enableScaling) {
      this.enableScaling = enableScaling;
   }

   public boolean getScaleMode() {
      return this.scaleMode;
   }

   public final int getStartScalePosY() {
      return this.iStartScalePosY;
   }

   public static class DefinedScales {
      public int definedScale_Default;
      public float[] definedScales;
   }
}
