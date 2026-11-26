package aoc.kingdoms.lukasz.map.province;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GlyphLayout_Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.jakowski.zOther.Point_XY;
import aoc.kingdoms.lukasz.map.army.ArmyDivision;
import aoc.kingdoms.lukasz.map.army.ArmyManager;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class ProvinceDrawArmy {
   public static final Color COLOR_ARMY = new Color(0.9019608F, 0.88235295F, 0.84313726F, 1.0F);
   public static final Color COLOR_ARMY2 = new Color(1.0F, 0.9607843F, 0.92156863F, 1.0F);
   public static final Color COLOR_ARMY_OLD = new Color(0.8627451F, 0.78431374F, 0.27450982F, 1.0F);
   public static int ARMY_HEIGHT = 1;
   public static final int HOVER_PADDING = 4;
   public static final int FLAG_PADDING_Y = 2;
   public static final int FLAG_PADDING_X = 3;
   public static final int FLAG_PADDING_X_RIGHT = 2;
   public static final int FLAG_PADDING_X_SUM = 5;
   public static final int ARMY_PADDING_X = 5;
   public static final float DRAW_ARMY_DEFAULT_ALPHA = 1.0F;
   public static boolean drawHideAnimation = false;
   public static float DRAW_ARMY_ALPHA = 0.1F;
   public static long DRAW_ARMY_TIME = 0L;
   public static long DRAW_ARMY_TIME_HIDE = 0L;
   public static float DRAW_ARMY_MIN_SCALE_ANIMATION = 0.3F;
   public static boolean drawCitiesHideAnimation = false;
   public static float DRAW_CITIES_ALPHA = 0.1F;
   public static long DRAW_CITIES_TIME = 0L;
   public static long DRAW_CITIES_TIME_HIDE = 0L;
   public static boolean drawProvinceNamesHideAnimation = false;
   public static float DRAW_PROVINCE_NAMES_ALPHA = 0.1F;
   public static long DRAW_PROVINCE_NAMES_TIME = 0L;
   public static long DRAW_PROVINCE_NAMES_TIME_HIDE = 0L;
   public static boolean drawOccupiedHideAnimation = false;
   public static float DRAW_OCCUPIED_ALPHA = 0.1F;
   public static long DRAW_OCCUPIED_TIME = 0L;
   public static long DRAW_OCCUPIED_TIME_HIDE = 0L;
   public static Color moraleBG = new Color(0.0627451F, 0.0627451F, 0.101960786F, 1.0F);
   public static Color moraleGreen = new Color(0.11764706F, 0.50980395F, 0.101960786F, 1.0F);
   public static Color drawProvinceArmyColorAlpha = Color.WHITE;
   public static Color drawProvinceArmyTextColor = Color.WHITE;
   public static int armyFlagPosX;
   public static int armyFlagPosY;
   public static int armyFlagPosWidth;
   public static int armyFlagPosHeight;

   public static final void updateArmyImgID() {
      for (int i = 0; i < Game.getCivsSize(); i++) {
         Game.getCiv(i).updateArmyImgID();
      }
   }

   public static final void updateDrawArmyAlpha() {
      if (Game.mapScale.getCurrentScale() >= Game.DRAW_ARMY_MIN_SCALE) {
         updateTime();
         drawHideAnimation = true;
         DRAW_ARMY_TIME_HIDE = 0L;
      } else if (drawHideAnimation) {
         updateTimeHide();
      } else {
         DRAW_ARMY_TIME = 0L;
      }

      if (Game.mapScale.getCurrentScale() >= Game.DRAW_CITIES_MIN_SCALE) {
         updateCitiesTime();
         drawCitiesHideAnimation = true;
         DRAW_CITIES_TIME_HIDE = 0L;
      } else if (drawCitiesHideAnimation) {
         updateCitiesTimeHide();
      } else {
         DRAW_CITIES_TIME = 0L;
      }

      if (Game.mapScale.getCurrentScale() >= Game.DRAW_CIV_NAMES_START_DRAWING_MAP_SCALE) {
         updateProvinceNamesTime();
         drawProvinceNamesHideAnimation = true;
         DRAW_PROVINCE_NAMES_TIME_HIDE = 0L;
      } else if (drawProvinceNamesHideAnimation) {
         updateProvinceNamesTimeHide();
      } else {
         DRAW_PROVINCE_NAMES_TIME = 0L;
      }

      if (Game.mapScale.getCurrentScale() >= Game.DRAW_OCCUPIED_PROVINCES_MIN_SCALE) {
         updateOccupiedTime();
         drawOccupiedHideAnimation = true;
         DRAW_OCCUPIED_TIME_HIDE = 0L;
      } else if (drawOccupiedHideAnimation) {
         updateOccupiedTimeHide();
      } else {
         DRAW_OCCUPIED_TIME = 0L;
      }
   }

   public static final void updateTime() {
      if (DRAW_ARMY_TIME == 0L) {
         DRAW_ARMY_TIME = CFG.currentTimeMillis;
         DRAW_ARMY_ALPHA = 0.0F;
      } else if (DRAW_ARMY_ALPHA < 1.0F) {
         DRAW_ARMY_ALPHA = 1.0F * ((float)(CFG.currentTimeMillis - DRAW_ARMY_TIME) / Game.settingsManager.CIVILIZATIONS_NAMES_INTERVAL);
         if (DRAW_ARMY_ALPHA > 1.0F) {
            DRAW_ARMY_ALPHA = 1.0F;
         }
      }
   }

   public static final void updateCitiesTime() {
      if (DRAW_CITIES_TIME == 0L) {
         DRAW_CITIES_TIME = CFG.currentTimeMillis;
         DRAW_CITIES_ALPHA = 0.0F;
      } else if (DRAW_CITIES_ALPHA < 1.0F) {
         DRAW_CITIES_ALPHA = 1.0F * ((float)(CFG.currentTimeMillis - DRAW_CITIES_TIME) / Game.settingsManager.CIVILIZATIONS_NAMES_INTERVAL);
         if (DRAW_CITIES_ALPHA > 1.0F) {
            DRAW_CITIES_ALPHA = 1.0F;
         }
      }
   }

   public static final void updateTimeHide() {
      if (DRAW_ARMY_TIME_HIDE == 0L) {
         DRAW_ARMY_TIME_HIDE = CFG.currentTimeMillis;
         DRAW_ARMY_ALPHA = 1.0F;
      } else {
         DRAW_ARMY_ALPHA = 1.0F - 1.0F * ((float)(CFG.currentTimeMillis - DRAW_ARMY_TIME_HIDE) / (Game.settingsManager.CIVILIZATIONS_NAMES_INTERVAL / 2.0F));
         if (DRAW_ARMY_ALPHA <= 0.0F) {
            DRAW_ARMY_ALPHA = 0.0F;
            drawHideAnimation = false;
         }
      }
   }

   public static final void updateCitiesTimeHide() {
      if (DRAW_CITIES_TIME_HIDE == 0L) {
         DRAW_CITIES_TIME_HIDE = CFG.currentTimeMillis;
         DRAW_CITIES_ALPHA = 1.0F;
      } else {
         DRAW_CITIES_ALPHA = 1.0F
            - 1.0F * ((float)(CFG.currentTimeMillis - DRAW_CITIES_TIME_HIDE) / (Game.settingsManager.CIVILIZATIONS_NAMES_INTERVAL / 2.0F));
         if (DRAW_CITIES_ALPHA <= 0.0F) {
            DRAW_CITIES_ALPHA = 0.0F;
            drawCitiesHideAnimation = false;
         }
      }
   }

   public static final void updateProvinceNamesTimeHide() {
      if (DRAW_PROVINCE_NAMES_TIME_HIDE == 0L) {
         DRAW_PROVINCE_NAMES_TIME_HIDE = CFG.currentTimeMillis;
         DRAW_PROVINCE_NAMES_ALPHA = 1.0F;
      } else {
         DRAW_PROVINCE_NAMES_ALPHA = 1.0F
            - 1.0F * ((float)(CFG.currentTimeMillis - DRAW_PROVINCE_NAMES_TIME_HIDE) / (Game.settingsManager.CIVILIZATIONS_NAMES_INTERVAL / 2.0F));
         if (DRAW_PROVINCE_NAMES_ALPHA <= 0.0F) {
            DRAW_PROVINCE_NAMES_ALPHA = 0.0F;
            drawProvinceNamesHideAnimation = false;
         }
      }
   }

   public static final void updateProvinceNamesTime() {
      if (DRAW_PROVINCE_NAMES_TIME == 0L) {
         DRAW_PROVINCE_NAMES_TIME = CFG.currentTimeMillis;
         DRAW_PROVINCE_NAMES_ALPHA = 0.0F;
      } else if (DRAW_PROVINCE_NAMES_ALPHA < 1.0F) {
         DRAW_PROVINCE_NAMES_ALPHA = 1.0F * ((float)(CFG.currentTimeMillis - DRAW_PROVINCE_NAMES_TIME) / Game.settingsManager.CIVILIZATIONS_NAMES_INTERVAL);
         if (DRAW_PROVINCE_NAMES_ALPHA > 1.0F) {
            DRAW_PROVINCE_NAMES_ALPHA = 1.0F;
         }
      }
   }

   public static final void updateOccupiedTime() {
      if (DRAW_OCCUPIED_TIME == 0L) {
         DRAW_OCCUPIED_TIME = CFG.currentTimeMillis;
         DRAW_OCCUPIED_ALPHA = 0.0F;
      } else if (DRAW_OCCUPIED_ALPHA < 1.0F) {
         DRAW_OCCUPIED_ALPHA = 1.0F * ((float)(CFG.currentTimeMillis - DRAW_OCCUPIED_TIME) / Game.settingsManager.CIVILIZATIONS_NAMES_INTERVAL);
         if (DRAW_OCCUPIED_ALPHA > 1.0F) {
            DRAW_OCCUPIED_ALPHA = 1.0F;
         }
      }
   }

   public static final void updateOccupiedTimeHide() {
      if (DRAW_OCCUPIED_TIME_HIDE == 0L) {
         DRAW_OCCUPIED_TIME_HIDE = CFG.currentTimeMillis;
         DRAW_OCCUPIED_ALPHA = 1.0F;
      } else {
         DRAW_OCCUPIED_ALPHA = 1.0F
            - 1.0F * ((float)(CFG.currentTimeMillis - DRAW_OCCUPIED_TIME_HIDE) / (Game.settingsManager.CIVILIZATIONS_NAMES_INTERVAL / 2.0F));
         if (DRAW_OCCUPIED_ALPHA <= 0.0F) {
            DRAW_OCCUPIED_ALPHA = 0.0F;
            drawOccupiedHideAnimation = false;
         }
      }
   }

   public static final void drawProvincesArmy(SpriteBatch oSB) {
      if (Game.mapScale.getCurrentScale() >= Game.DRAW_ARMY_MIN_SCALE) {
         drawProvincesArmy_Just(oSB, DRAW_ARMY_ALPHA);
      } else if (Game.mapTouchManager.selectMode
         && Game.mapModes.iActiveMapModeID != Game.mapModes.MODE_PEACE_VIEW
         && Game.mapModes.iActiveMapModeID != Game.mapModes.MODE_INVEST_IN_ECONOMY
         && Game.mapModes.iActiveMapModeID != Game.mapModes.MODE_INCREASE_TAX_EFFICIENCY
         && Game.mapModes.iActiveMapModeID != Game.mapModes.MODE_DEVELOP_INFRASTRUCTURE
         && Game.mapModes.iActiveMapModeID != Game.mapModes.MODE_INCREASE_GROWTH_RATE
         && Game.mapModes.iActiveMapModeID != Game.mapModes.MODE_INCREASE_MANPOWER
         && Game.mapModes.iActiveMapModeID != Game.mapModes.MODE_CONVERT_RELIGION
         && Game.mapModes.iActiveMapModeID != Game.mapModes.MODE_CORE
         && Game.mapModes.iActiveMapModeID != Game.mapModes.MODE_BUILDING) {
         drawProvincesArmy_Just(oSB, 1.0F);
      } else if (drawHideAnimation && Game.mapScale.getCurrentScale() >= DRAW_ARMY_MIN_SCALE_ANIMATION) {
         drawProvincesArmy_Just(oSB, DRAW_ARMY_ALPHA);
      }
   }

   private static final void drawProvincesArmy_Just(SpriteBatch oSB, float fAlpha) {
      try {
         try {
            for (int i = 0; i < Game.activeArmySize; i++) {
               if (Game.getProvince(Game.activeArmy.get(i).iProvinceID).getDrawProvince()) {
                  Game.activeArmy.get(i).iArmyID = Game.getProvince(Game.activeArmy.get(i).iProvinceID).getArmyKeyID(Game.activeArmy.get(i).key);
                  if (Game.activeArmy.get(i).iArmyID < 0) {
                     Game.removeActiveArmy(i);
                  } else if (!Game.getProvince(Game.activeArmy.get(i).iProvinceID).getArmy(Game.activeArmy.get(i).iArmyID).inBattle) {
                     drawProvinceArmyActive(oSB, Game.activeArmy.get(i).iProvinceID, Game.activeArmy.get(i).iArmyID, fAlpha);
                  }
               }
            }
         } catch (Exception var6) {
         }

         try {
            if (Game.hoveredArmy.iProvinceID >= 0) {
               Game.checkHoveredArmy_Fog();
               if (Game.getProvince(Game.hoveredArmy.iProvinceID).getDrawProvince()) {
                  Game.hoveredArmy.iArmyID = Game.getProvince(Game.hoveredArmy.iProvinceID).getArmyKeyID(Game.hoveredArmy.key);
                  if (!Game.getProvince(Game.hoveredArmy.iProvinceID).getArmy(Game.hoveredArmy.iArmyID).inBattle
                     && Game.hoveredArmy.iProvinceID >= 0
                     && Game.hoveredArmy.iArmyID >= 0) {
                     drawProvinceArmyHover(oSB, Game.hoveredArmy.iProvinceID, Game.hoveredArmy.iArmyID, fAlpha);
                  }
               }
            }
         } catch (Exception var3) {
         }

         drawProvinceArmyColorAlpha = new Color(1.0F, 1.0F, 1.0F, fAlpha);
         drawProvinceArmyTextColor = new Color(COLOR_ARMY.r, COLOR_ARMY.g, COLOR_ARMY.b, fAlpha);
         oSB.setColor(drawProvinceArmyColorAlpha);

         try {
            for (int ix = 0; ix < Game.NUM_OF_WASTELAND_PROVINCES_IN_VIEW; ix++) {
               Game.getProvince(Game.getWastelandProvinceInViewID(ix)).drawArmy(oSB, fAlpha);
            }

            for (int ix = 0; ix < Game.NUM_OF_SEA_PROVINCES_IN_VIEW; ix++) {
               Game.getProvince(Game.getSeaProvinceInViewID(ix)).drawArmy(oSB, fAlpha);
            }

            for (int ix = 0; ix < Game.NUM_OF_PROVINCES_IN_VIEW; ix++) {
               Game.getProvince(Game.getProvinceInViewID(ix)).drawArmy(oSB, fAlpha);
            }

            for (int ix = 0; ix < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; ix++) {
               Game.getProvince(Game.getExtraProvinceInViewID(ix)).drawArmy(oSB, fAlpha);
            }
         } catch (Exception var5) {
            CFG.exceptionStack(var5);
         }

         oSB.setColor(Color.WHITE);

         try {
            if (Game.regroupArmyShadows.size() > 0) {
               for (int ix = 0; ix < Game.regroupArmyShadows.size(); ix++) {
                  drawProvinceArmyWithFlag_Shadow(
                     oSB,
                     Game.regroupArmyShadows.get(ix).iProvinceID,
                     Color.WHITE,
                     0.5F,
                     Game.regroupArmyShadows.get(ix).sArmy,
                     Game.regroupArmyShadows.get(ix).iArmyWidth,
                     Game.regroupArmyShadows.get(ix).extraY
                  );
               }
            }
         } catch (Exception var4) {
            CFG.exceptionStack(var4);
         }
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }

      oSB.setColor(Color.WHITE);
   }

   public static final void drawMapModeDetails(SpriteBatch oSB) {
      if (Game.mapScale.getCurrentScale() >= Game.DRAW_ARMY_MIN_SCALE) {
         for (int i = 0; i < Game.NUM_OF_SEA_PROVINCES_IN_VIEW; i++) {
            Game.getProvince(Game.getSeaProvinceInViewID(i)).drawDetails(oSB);
         }

         for (int i = 0; i < Game.NUM_OF_SEA_PROVINCES_IN_VIEW; i++) {
            Game.getProvince(Game.getSeaProvinceInViewID(i)).drawDetailsSea(oSB);
         }

         for (int i = 0; i < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; i++) {
            Game.getProvince(Game.getExtraProvinceInViewID(i)).drawDetails(oSB);
         }

         for (int i = 0; i < Game.NUM_OF_PROVINCES_IN_VIEW; i++) {
            Game.getProvince(Game.getProvinceInViewID(i)).drawDetails(oSB);
         }
      }
   }

   public static final ProvinceDrawArmy.ProvinceDrawArmyINT updateDrawArmy(int nProvinceID) {
      return Game.getProvince(nProvinceID).getFogDrawArmy() ? new ProvinceDrawArmy.ProvinceDrawArmyINT() {
         @Override
         public void drawArmy(SpriteBatch oSB, int nProvinceIDx, int nArmyID) {
            if (!Game.getProvince(nProvinceIDx).getArmy(nArmyID).inBattle) {
               ProvinceDrawArmy.drawProvinceArmyWithFlag(oSB, nProvinceIDx, nArmyID);
            }
         }
      } : new ProvinceDrawArmy.ProvinceDrawArmyINT() {
         @Override
         public void drawArmy(SpriteBatch oSB, int nProvinceID, int nArmyID) {
         }
      };
   }

   public static final int getDetailsPosX(int nProvinceID) {
      return (int)((Game.getProvince(nProvinceID).iCenterShiftX + Game.getProvince(nProvinceID).getTranslateProvincePosX()) * Game.mapScale.getCurrentScale())
         + Game.getProvince(nProvinceID).drawDetails.iShiftX;
   }

   public static final int getDetailsPosX_2(int nProvinceID) {
      return (int)((Game.getProvince(nProvinceID).iCenterShiftX + Game.getProvince(nProvinceID).getTranslateProvincePosX()) * Game.mapScale.getCurrentScale());
   }

   public static final int getDetailsPosY(int nProvinceID) {
      return (int)((Game.getProvince(nProvinceID).iCenterShiftY + Game.mapCoords.getPosY()) * Game.mapScale.getCurrentScale())
         + Game.getProvince(nProvinceID).drawDetails.iShiftY;
   }

   public static final int getDetailsPosY_2(int nProvinceID) {
      return (int)((Game.getProvince(nProvinceID).iCenterShiftY + Game.mapCoords.getPosY()) * Game.mapScale.getCurrentScale());
   }

   public static final int getArmyPosX(int nProvinceID, int nArmyID) {
      return (int)(
            (
                  Game.getProvince(nProvinceID).iCenterShiftX
                     + Game.getProvince(nProvinceID).getTranslateProvincePosX()
                     + Game.getProvince(nProvinceID).getArmy(nArmyID).iShiftX_Scaled
               )
               * Game.mapScale.getCurrentScale()
         )
         + Game.getProvince(nProvinceID).getArmy(nArmyID).iShiftX;
   }

   public static final int getArmyPosY(int nProvinceID, int nArmyID) {
      return (int)(
            (Game.getProvince(nProvinceID).iCenterShiftY + Game.mapCoords.getPosY() + Game.getProvince(nProvinceID).getArmy(nArmyID).iShiftY_Scaled)
               * Game.mapScale.getCurrentScale()
         )
         + Game.getProvince(nProvinceID).getArmy(nArmyID).iShiftY;
   }

   public static final int getArmyPosX_Shadow(int nProvinceID) {
      return (int)((Game.getProvince(nProvinceID).iCenterShiftX + Game.getProvince(nProvinceID).getTranslateProvincePosX()) * Game.mapScale.getCurrentScale());
   }

   public static final int getArmyPosY_Shadow(int nProvinceID) {
      return (int)((Game.getProvince(nProvinceID).iCenterShiftY + Game.mapCoords.getPosY()) * Game.mapScale.getCurrentScale());
   }

   public static final int getArmyWidth(int nArmyWidth) {
      return Images.armyLeft3_Width + nArmyWidth + 10;
   }

   public static final int getArmyHeight() {
      return ImageManager.getImage(Images.armyLeft).getHeight();
   }

   public static final void drawProvinceArmyWithFlag(SpriteBatch oSB, int nProvinceID, int nArmyID) {
      int nPosX = getArmyPosX(nProvinceID, nArmyID);
      int nPosY = getArmyPosY(nProvinceID, nArmyID);
      Province province = Game.getProvince(nProvinceID);
      ArmyDivision armyDivision = province.getArmy(nArmyID);
      drawProvinceArmyFlag_2(oSB, nPosX, nPosY, armyDivision.civID);
      Images.armyLeft3.get(armyDivision.fMoraleDraw).draw(oSB, nPosX, nPosY);
      nPosX += Images.armyLeft3_Width;
      drawProvinceArmy(oSB, nPosX, nPosY, armyDivision.iArmyWidth + 10, Game.getCiv(armyDivision.civID).iArmyImgID, armyDivision.inRetreat);
      Renderer.drawText(
         oSB,
         CFG.FONT_ARMY,
         armyDivision.sArmy,
         nPosX + armyDivision.iArmyExtraPosX + 5,
         nPosY + (int)(ImageManager.getImage(Images.armyLeft).getHeight() / 2.0F) - (int)(ARMY_HEIGHT / 2.0F),
         drawProvinceArmyTextColor
      );
   }

   private static final void drawProvinceArmy(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, int ImageID, boolean inRetreat) {
      ImageManager.getImage(ImageID).draw2(oSB, nPosX, nPosY, nWidth, ImageManager.getImage(ImageID).getHeight(), true, false);
      if (inRetreat) {
         ImageManager.getImage(Images.armyRetreat)
            .draw(
               oSB, nPosX + nWidth, nPosY + ImageManager.getImage(Images.armyLeft).getHeight() / 2 - ImageManager.getImage(Images.armyRetreat).getHeight() / 2
            );
      }
   }

   private static final void drawProvinceArmyFlag(SpriteBatch oSB, int nPosX, int nPosY, int nCivID, int ImageID) {
      ImageManager.getImage(ImageID)
         .draw2(oSB, nPosX, nPosY, ImageManager.getImage(Images.armyFlag).getWidth() + 5, ImageManager.getImage(ImageID).getHeight());
      if (nCivID < 0) {
         ImageManager.getImage(Images.rebelsFlag)
            .draw(oSB, nPosX + 3, nPosY + 2, ImageManager.getImage(Images.armyFlag).getWidth(), ImageManager.getImage(Images.armyFlag).getHeight());
      } else {
         Game.getCiv(nCivID)
            .getFlag()
            .draw(oSB, nPosX + 3, nPosY + 2, ImageManager.getImage(Images.armyFlag).getWidth(), ImageManager.getImage(Images.armyFlag).getHeight());
      }

      ImageManager.getImage(Images.armyFlag).draw(oSB, nPosX + 3, nPosY + 2);
   }

   private static final void drawProvinceArmyFlag_2(SpriteBatch oSB, int nPosX, int nPosY, int nCivID) {
      if (nCivID < 0) {
         ImageManager.getImage(Images.rebelsFlag).draw(oSB, nPosX + armyFlagPosX, nPosY + armyFlagPosY, armyFlagPosWidth, armyFlagPosHeight);
      } else {
         Game.getCiv(nCivID).getFlag().draw(oSB, nPosX + armyFlagPosX, nPosY + armyFlagPosY, armyFlagPosWidth, armyFlagPosHeight);
      }
   }

   public static final void drawProvinceArmy_Units(SpriteBatch oSB, int nProvinceID, int nArmyID, Color armyColor, float fAlpha) {
      try {
         int nPosX = getArmyPosX(nProvinceID, nArmyID);
         int nPosY = getArmyPosY(nProvinceID, nArmyID);
         Province province = Game.getProvince(nProvinceID);
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, fAlpha));
         int uID = province.getArmy(nArmyID).lArmyRegiment.get(0).uID;
         int aID = province.getArmy(nArmyID).lArmyRegiment.get(0).aID;
         int numOfUnits = province.getArmy(nArmyID).lArmyRegiment.get(0).num;

         for (int i = 1; i < province.getArmy(nArmyID).iArmyRegimentSize; i++) {
            if (uID == province.getArmy(nArmyID).lArmyRegiment.get(i).uID && aID == province.getArmy(nArmyID).lArmyRegiment.get(i).aID) {
               numOfUnits += province.getArmy(nArmyID).lArmyRegiment.get(i).num;
            } else {
               try {
                  Renderer.clipView_Start(
                     oSB, nPosX, nPosY, ImageManager.getImage(Images.unitsFrameBattle).getWidth(), -ImageManager.getImage(Images.unitsFrameBattle).getHeight()
                  );
                  ArmyManager.armyImages
                     .get(ArmyManager.lArmy.get(uID).get(aID).ImageID)
                     .draw(oSB, nPosX, nPosY, ImageManager.getImage(Images.unitsFrameBattle).getHeight());
                  Renderer.clipView_End(oSB);
               } catch (Exception var13) {
                  CFG.exceptionStack(var13);
               }

               ImageManager.getImage(Images.unitsFrameBattle).draw(oSB, nPosX, nPosY);
               nPosX += ImageManager.getImage(Images.unitsFrameBattle).getWidth() + CFG.PADDING / 2;
               uID = province.getArmy(nArmyID).lArmyRegiment.get(i).uID;
               aID = province.getArmy(nArmyID).lArmyRegiment.get(i).aID;
               numOfUnits = province.getArmy(nArmyID).lArmyRegiment.get(i).num;
            }
         }

         oSB.setColor(Color.WHITE);
      } catch (Exception var14) {
      }
   }

   public static final void drawProvinceArmyWithFlag_Shadow(
      SpriteBatch oSB, int nProvinceID, Color armyColor, float fAlpha, String sArmy, int iArmyWidth, int extraY
   ) {
      try {
         int nPosX = getArmyPosX_Shadow(nProvinceID)
            - (ImageManager.getImage(Images.armyMorale).getWidth() + ImageManager.getImage(Images.armyFlag).getWidth() + 5 + iArmyWidth + 10) / 2;
         int nPosY = getArmyPosY_Shadow(nProvinceID) + extraY * (getArmyHeight() + 2);
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, fAlpha));
         drawProvinceArmyFlag(oSB, nPosX, nPosY, Game.player.iCivID, Images.armyLeft);
         oSB.setColor(new Color(moraleBG.r, moraleBG.g, moraleBG.b, fAlpha));
         Images.pix
            .draw2(
               oSB,
               nPosX + ImageManager.getImage(Images.armyFlag).getWidth() + 5,
               nPosY,
               ImageManager.getImage(Images.armyMorale).getWidth(),
               ImageManager.getImage(Images.armyMorale).getHeight()
            );
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, fAlpha));
         drawProvinceArmy(
            oSB,
            nPosX + ImageManager.getImage(Images.armyMorale).getWidth() + ImageManager.getImage(Images.armyFlag).getWidth() + 5,
            nPosY,
            iArmyWidth + 10,
            Images.army,
            false
         );
         Renderer.drawText(
            oSB,
            CFG.FONT_ARMY,
            sArmy,
            nPosX + ImageManager.getImage(Images.armyMorale).getWidth() + ImageManager.getImage(Images.armyFlag).getWidth() + 5 + 5,
            nPosY + (int)(ImageManager.getImage(Images.armyLeft).getHeight() / 2.0F) - (int)(ARMY_HEIGHT / 2.0F),
            new Color(armyColor.r, armyColor.g, armyColor.b, armyColor.a * fAlpha)
         );
         oSB.setColor(Color.WHITE);
      } catch (Exception var9) {
         CFG.exceptionStack(var9);
      }
   }

   public static final void drawProvinceArmyHover(SpriteBatch oSB, int nProvinceID, int nArmyID, float fAlpha) {
      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, Game.animationHover.getAlpha() / 255.0F * fAlpha));
      drawProvinceArmyHover(
         oSB, getArmyPosX(nProvinceID, nArmyID), getArmyPosY(nProvinceID, nArmyID), getArmyWidth(Game.getProvince(nProvinceID).getArmy(nArmyID).iArmyWidth)
      );
      oSB.setColor(Color.WHITE);
   }

   public static final void drawProvinceArmyHover_WithUnitsImages(SpriteBatch oSB, int nProvinceID, int nArmyID, float fAlpha) {
      int nPosX = getArmyPosX(nProvinceID, nArmyID);
      int nPosY = getArmyPosY(nProvinceID, nArmyID);
      List<Point_XY> tIDs = new ArrayList<>();

      for (int i = 0; i < Game.getProvince(nProvinceID).getArmy(nArmyID).iArmyRegimentSize; i++) {
         boolean add = true;

         for (int j = 0; j < tIDs.size(); j++) {
            if (tIDs.get(j).getPosX() == Game.getProvince(nProvinceID).getArmy(nArmyID).lArmyRegiment.get(i).uID
               && tIDs.get(j).getPosY() == Game.getProvince(nProvinceID).getArmy(nArmyID).lArmyRegiment.get(i).aID) {
               add = false;
               break;
            }
         }

         if (add) {
            tIDs.add(
               new Point_XY(
                  Game.getProvince(nProvinceID).getArmy(nArmyID).lArmyRegiment.get(i).uID,
                  Game.getProvince(nProvinceID).getArmy(nArmyID).lArmyRegiment.get(i).aID
               )
            );
         }
      }

      int nWidth = getArmyWidth(Game.getProvince(nProvinceID).getArmy(nArmyID).iArmyWidth);
      int extraWidth = ImageManager.getImage(Images.armyUnitsFrame).getWidth() * tIDs.size();
      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, Game.animationHover.getAlpha() / 255.0F * fAlpha));
      drawProvinceArmyHover_Units(oSB, nPosX, nPosY, nWidth + extraWidth);
      oSB.setColor(Color.WHITE);
      drawProvinceArmy(
         oSB,
         nPosX - 5 + nWidth,
         nPosY,
         ImageManager.getImage(Images.armyUnitsFrame).getWidth(),
         Images.armyAlly,
         Game.getProvince(nProvinceID).getArmy(nArmyID).inRetreat
      );

      for (int jx = 0; jx < tIDs.size(); jx++) {
         Renderer.clipView_Start(
            oSB,
            nPosX + nWidth + ImageManager.getImage(Images.armyUnitsFrame).getWidth() * jx,
            CFG.GAME_HEIGHT - nPosY,
            ImageManager.getImage(Images.armyUnitsFrame).getWidth(),
            -ImageManager.getImage(Images.armyUnitsFrame).getHeight()
         );
         int unitImgWidth = (int)(
            ArmyManager.armyImages.get(ArmyManager.lArmy.get(tIDs.get(jx).getPosX()).get(tIDs.get(jx).getPosY()).ImageID).getWidth()
               * (
                  (float)ImageManager.getImage(Images.armyUnitsFrame).getHeight()
                     / ArmyManager.armyImages.get(ArmyManager.lArmy.get(tIDs.get(jx).getPosX()).get(tIDs.get(jx).getPosY()).ImageID).getHeight()
               )
         );
         ArmyManager.armyImages
            .get(ArmyManager.lArmy.get(tIDs.get(jx).getPosX()).get(tIDs.get(jx).getPosY()).ImageID)
            .draw(
               oSB,
               nPosX
                  + ImageManager.getImage(Images.armyUnitsFrame).getWidth() / 2
                  - unitImgWidth / 2
                  + nWidth
                  + ImageManager.getImage(Images.armyUnitsFrame).getWidth() * jx,
               nPosY,
               unitImgWidth,
               ImageManager.getImage(Images.armyUnitsFrame).getHeight()
            );
         Renderer.clipView_End(oSB);
         ImageManager.getImage(Images.armyUnitsFrame).draw(oSB, nPosX + nWidth + ImageManager.getImage(Images.armyUnitsFrame).getWidth() * jx, nPosY);
      }
   }

   public static final void drawProvinceArmyActive(SpriteBatch oSB, int nProvinceID, int nArmyID, float fAlpha) {
      try {
         if (nArmyID >= 0 && nArmyID < Game.getProvince(nProvinceID).getArmySize()) {
            oSB.setColor(new Color(1.0F, 1.0F, 1.0F, Game.animationHover.getAlpha() / 255.0F * fAlpha));
            drawProvinceArmyActive(
               oSB,
               getArmyPosX(nProvinceID, nArmyID),
               getArmyPosY(nProvinceID, nArmyID),
               getArmyWidth(Game.getProvince(nProvinceID).getArmy(nArmyID).iArmyWidth)
            );
            oSB.setColor(Color.WHITE);
         }
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
      }
   }

   public static final void drawProvinceArmyHover(SpriteBatch oSB, int nPosX, int nPosY, int nWidth) {
      ImageManager.getImage(Images.armyHover)
         .draw2(oSB, nPosX - 4, nPosY - 4, nWidth + 8 - ImageManager.getImage(Images.armyHover).getWidth(), ImageManager.getImage(Images.armyHover).getHeight());
      ImageManager.getImage(Images.armyHover)
         .draw2(
            oSB,
            nPosX + nWidth + 4 - ImageManager.getImage(Images.armyHover).getWidth(),
            nPosY - 4,
            ImageManager.getImage(Images.armyHover).getWidth(),
            ImageManager.getImage(Images.armyHover).getHeight(),
            true,
            false
         );
   }

   public static final void drawProvinceArmyHover_Units(SpriteBatch oSB, int nPosX, int nPosY, int nWidth) {
      ImageManager.getImage(Images.armyHover)
         .draw2(
            oSB,
            nPosX - 4,
            nPosY - 4,
            nWidth + 8 - ImageManager.getImage(Images.armyHoverRight).getWidth(),
            ImageManager.getImage(Images.armyHover).getHeight()
         );
      ImageManager.getImage(Images.armyHoverRight)
         .draw2(
            oSB,
            nPosX + nWidth + 4 - ImageManager.getImage(Images.armyHoverRight).getWidth(),
            nPosY - 4,
            ImageManager.getImage(Images.armyHoverRight).getWidth(),
            ImageManager.getImage(Images.armyHoverRight).getHeight()
         );
   }

   private static final void drawProvinceArmyActive(SpriteBatch oSB, int nPosX, int nPosY, int nWidth) {
      ImageManager.getImage(Images.armyActive)
         .draw2(
            oSB, nPosX - 4, nPosY - 4, nWidth + 8 - ImageManager.getImage(Images.armyActive).getWidth(), ImageManager.getImage(Images.armyActive).getHeight()
         );
      ImageManager.getImage(Images.armyActive)
         .draw2(
            oSB,
            nPosX + nWidth + 4 - ImageManager.getImage(Images.armyActive).getWidth(),
            nPosY - 4,
            ImageManager.getImage(Images.armyActive).getWidth(),
            ImageManager.getImage(Images.armyActive).getHeight(),
            true,
            false
         );
   }

   public static final void updateArmyHeight() {
      GlyphLayout_Game glyphLayout = new GlyphLayout_Game();
      glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_ARMY), "0123456789");
      ARMY_HEIGHT = (int)glyphLayout.height;
   }

   public interface ProvinceDrawArmyINT {
      void drawArmy(SpriteBatch var1, int var2, int var3);
   }

   public static class ProvinceDrawDetailsINT {
      public String sText = "";
      public int iTextWidth = 0;
      public int iShiftX = 0;
      public int iShiftY = 0;
      public int iWidth = 0;
      public int iHeight = 0;

      public void drawDetails(SpriteBatch oSB, int nProvinceID) {
      }

      public void drawDetailsSea(SpriteBatch oSB, int nProvinceID) {
      }

      public MenuElement_Hover buildHover(int nProvinceID) {
         return null;
      }
   }
}
