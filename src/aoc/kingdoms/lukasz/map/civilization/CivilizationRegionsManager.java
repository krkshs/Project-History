package aoc.kingdoms.lukasz.map.civilization;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import java.util.ArrayList;
import java.util.List;

public class CivilizationRegionsManager {
   public static List<Boolean> isProvinceAssigned = new ArrayList<>();
   private static CivilizationRegionsManager.Renderer_CivRegionNames oRenderer_CivRegionNames;
   public static boolean updateRegionsInView = true;
   public static float civsRegionsAlpha = 1.0F;
   public static Matrix4 oldTransformMatrix;
   private static List<Integer> lRegions_Civs = new ArrayList<>();
   private static List<List<Integer>> lRegions_Civs_RegionsID = new ArrayList<>();
   public static int NUM_OF_REGIONS_IN_VIEW = 0;
   public static boolean drawHideAnimation = false;
   public static float CIVILIZATION_NAMES_ALPHA = 0.1F;
   public static long CIVILIZATIONS_NAMES_TIME = 0L;
   public static long CIVILIZATIONS_NAMES_TIME_HIDE = 0L;

   public static final void buildCivilizationsRegions() {
      for (int i = 1; i < Game.getCivsSize(); i++) {
         Game.getCiv(i).clearCivRegions_Just();
      }

      isProvinceAssigned.clear();

      for (int i = 0; i < Game.getProvincesSize(); i++) {
         Game.getProvince(i).setCivRegionID(-1);
         isProvinceAssigned.add(false);
      }

      for (int i = 0; i < Game.getProvincesSize(); i++) {
         if (Game.getProvince(i).getCivID() != 0 && !isProvinceAssigned.get(i)) {
            updateIsProvinceAssigned(i, true);
            Game.getCiv(Game.getProvince(i).getCivID()).createCivilizationRegion(i);
         }
      }

      isProvinceAssigned.clear();
      buildCivilizationsRegions_TextOver();
   }

   public static final void buildCivilizationsRegion(int nCivID) {
      try {
         Game.getCiv(nCivID).clearCivRegions_Just();
         isProvinceAssigned.clear();

         for (int i = 0; i < Game.getProvincesSize(); i++) {
            isProvinceAssigned.add(false);
         }

         for (int i = 0; i < Game.getCiv(nCivID).getNumOfProvinces(); i++) {
            Game.getProvince(Game.getCiv(nCivID).getProvinceID(i)).setCivRegionID(-1);
         }

         for (int i = 0; i < Game.getCiv(nCivID).getNumOfProvinces(); i++) {
            if (!isProvinceAssigned.get(Game.getCiv(nCivID).getProvinceID(i))) {
               Game.getCiv(nCivID).createCivilizationRegion(Game.getCiv(nCivID).getProvinceID(i));
               updateIsProvinceAssigned(Game.getCiv(nCivID).getProvinceID(i), true);
            }
         }

         isProvinceAssigned.clear();
         Renderer.addSimpleTaskCivsNames(new Game.SimpleTask("" + nCivID, nCivID) {
            @Override
            public void update() {
               CivilizationRegionsManager.buildCivilizationsRegions_TextOver(this.id);
            }
         });
      } catch (Exception var2) {
      }
   }

   public static void updateIsProvinceAssigned(int nProvinceID, boolean nIsProvinceAssigned) {
      try {
         isProvinceAssigned.set(nProvinceID, nIsProvinceAssigned);
      } catch (Exception var3) {
      }
   }

   public static final void buildCivilizationsRegions_TextOver() {
      for (int i = 1; i < Game.getCivsSize(); i++) {
         buildCivilizationsRegions_TextOver(i);
      }
   }

   public static final void buildCivilizationsRegions_TextOver(int iCivID) {
      for (int j = 0; j < Game.getCiv(iCivID).getCivRegionsSize(); j++) {
         Game.getCiv(iCivID).getCivRegion(j).buildRegionPath_TriedToUse();
         Game.getCiv(iCivID).getCivRegion(j).buildRegionPath();
      }
   }

   public static final void drawCivNames(SpriteBatch oSB) {
      if (Game.mapScale.getCurrentScale() >= Game.DRAW_CIV_NAMES_START_DRAWING_MAP_SCALE) {
         if (drawHideAnimation) {
            if (updateRegionsInView) {
               oRenderer_CivRegionNames.update();
               updateRegionsInView = false;
            }

            drawCivNames_Begin(oSB);
            drawCivRegions_Names_UpdateTimeHide();
            oRenderer_CivRegionNames.draw(oSB);
            drawCivNames_End(oSB);
         } else {
            CIVILIZATIONS_NAMES_TIME = 0L;
         }
      } else {
         if (updateRegionsInView) {
            oRenderer_CivRegionNames.update();
            updateRegionsInView = false;
         }

         drawCivNames_Begin(oSB);
         drawCivRegions_Names_UpdateTime();
         oRenderer_CivRegionNames.draw(oSB);
         drawCivNames_End(oSB);
         drawHideAnimation = true;
         CIVILIZATIONS_NAMES_TIME_HIDE = 0L;
      }

      oSB.setColor(Color.WHITE);
   }

   public static final synchronized void drawCivNames_Begin(SpriteBatch oSB) {
      civsRegionsAlpha = CIVILIZATION_NAMES_ALPHA * (1.0F + 0.25F * (1.0F - Game.mapScale.getCurrentScale()));
      oldTransformMatrix = oSB.getTransformMatrix().cpy();
      Renderer.fontBorder.get(0).setColor(new Color(1.0F, 1.0F, 1.0F, civsRegionsAlpha));
   }

   public static final void drawCivNames_End(SpriteBatch oSB) {
      oSB.setTransformMatrix(oldTransformMatrix);
   }

   public static final void updateRenderer_CivNames() {
      if (Game.menuManager.getInInitGameMenu()
         || Game.menuManager.getInInitGame_Menus()
         || Game.menuManager.getInMainMenu()
         || Game.menuManager.getInLoadGamesList()
         || Game.menuManager.getInScenarios_NewGame()
         || Game.menuManager.getInGameLegacies()
         || Game.menuManager.getInLoadScenario()) {
         oRenderer_CivRegionNames = new CivilizationRegionsManager.Renderer_CivRegionNames() {
            @Override
            public void draw(SpriteBatch oSB) {
            }

            @Override
            public void update() {
            }
         };
      } else if (Game.settingsManager.SETTINGS_CIV_NAMES == 2) {
         oRenderer_CivRegionNames = new CivilizationRegionsManager.Renderer_CivRegionNames() {
            @Override
            public void draw(SpriteBatch oSB) {
               CivilizationRegionsManager.drawCivRegions_Names2(oSB);
            }

            @Override
            public void update() {
               CivilizationRegionsManager.updateRegionsInView2();
            }
         };
      } else if (Game.settingsManager.SETTINGS_CIV_NAMES == 1) {
         oRenderer_CivRegionNames = new CivilizationRegionsManager.Renderer_CivRegionNames() {
            @Override
            public void draw(SpriteBatch oSB) {
               CivilizationRegionsManager.drawCivRegions_Names2_Low(oSB);
            }

            @Override
            public void update() {
               CivilizationRegionsManager.updateRegionsInView2();
            }
         };
      } else {
         oRenderer_CivRegionNames = new CivilizationRegionsManager.Renderer_CivRegionNames() {
            @Override
            public void draw(SpriteBatch oSB) {
            }

            @Override
            public void update() {
            }
         };
      }
   }

   private static final void updateRegionsInView2() {
      lRegions_Civs.clear();
      lRegions_Civs_RegionsID.clear();

      try {
         int[] tempCivs = new int[Game.getCivsSize()];

         for (int i = 0; i < Game.NUM_OF_PROVINCES_IN_VIEW; i++) {
            processProvince(Game.getProvinceInViewID(i), tempCivs);
         }

         for (int i = 0; i < Game.NUM_OF_EXTRA_PROVINCES_IN_VIEW; i++) {
            processProvince(Game.getExtraProvinceInViewID(i), tempCivs);
         }

         NUM_OF_REGIONS_IN_VIEW = lRegions_Civs.size();
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   private static void processProvince(int provinceID, int[] tempCivs) {
      if (Game.getProvince(provinceID).getCivID() > 0) {
         int civID = Game.getProvince(provinceID).getCivID();
         int civIndex = tempCivs[civID];
         if (civIndex > 0) {
            if (!lRegions_Civs_RegionsID.get(civIndex - 1).contains(Game.getProvince(provinceID).getCivRegionID())) {
               lRegions_Civs_RegionsID.get(civIndex - 1).add(Game.getProvince(provinceID).getCivRegionID());
            }
         } else {
            lRegions_Civs.add(civID);
            civIndex = lRegions_Civs.size();
            tempCivs[civID] = civIndex;
            lRegions_Civs_RegionsID.add(new ArrayList<>());
            if (Game.getProvince(provinceID).getCivRegionID() >= 0) {
               lRegions_Civs_RegionsID.get(civIndex - 1).add(Game.getProvince(provinceID).getCivRegionID());
            }
         }
      }
   }

   public static final void drawCivRegions_Names_UpdateTime() {
      if (CIVILIZATIONS_NAMES_TIME == 0L) {
         CIVILIZATIONS_NAMES_TIME = CFG.currentTimeMillis;
         CIVILIZATION_NAMES_ALPHA = 0.0F;
      } else if (CIVILIZATION_NAMES_ALPHA < Game.settingsManager.CIV_NAMES_TEXT_ALPHA) {
         CIVILIZATION_NAMES_ALPHA = Game.settingsManager.CIV_NAMES_TEXT_ALPHA
            * ((float)(CFG.currentTimeMillis - CIVILIZATIONS_NAMES_TIME) / Game.settingsManager.CIVILIZATIONS_NAMES_INTERVAL);
         if (CIVILIZATION_NAMES_ALPHA > Game.settingsManager.CIV_NAMES_TEXT_ALPHA) {
            CIVILIZATION_NAMES_ALPHA = Game.settingsManager.CIV_NAMES_TEXT_ALPHA;
         }
      }
   }

   public static final void drawCivRegions_Names_UpdateTimeHide() {
      if (CIVILIZATIONS_NAMES_TIME_HIDE == 0L) {
         CIVILIZATIONS_NAMES_TIME_HIDE = CFG.currentTimeMillis;
         CIVILIZATION_NAMES_ALPHA = Game.settingsManager.CIV_NAMES_TEXT_ALPHA;
      } else {
         CIVILIZATION_NAMES_ALPHA = Game.settingsManager.CIV_NAMES_TEXT_ALPHA
            - Game.settingsManager.CIV_NAMES_TEXT_ALPHA
               * ((float)(CFG.currentTimeMillis - CIVILIZATIONS_NAMES_TIME_HIDE) / (Game.settingsManager.CIVILIZATIONS_NAMES_INTERVAL / 2.0F));
         if (CIVILIZATION_NAMES_ALPHA <= 0.0F) {
            CIVILIZATION_NAMES_ALPHA = 0.0F;
            drawHideAnimation = false;
         }
      }
   }

   protected static final void drawCivRegions_Names2(SpriteBatch oSB) {
      try {
         for (int i = 0; i < lRegions_Civs_RegionsID.size(); i++) {
            try {
               for (int j = lRegions_Civs_RegionsID.get(i).size() - 1; j >= 0; j--) {
                  try {
                     Game.getCiv(lRegions_Civs.get(i)).getCivRegion(lRegions_Civs_RegionsID.get(i).get(j)).drawCivRegion(oSB);
                  } catch (IndexOutOfBoundsException var4) {
                     updateRegionsInView2();
                     break;
                  } catch (Exception var5) {
                     CFG.exceptionStack(var5);
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

   protected static final void drawCivRegions_Names2_Low(SpriteBatch oSB) {
      try {
         for (int i = 0; i < NUM_OF_REGIONS_IN_VIEW; i++) {
            try {
               for (int j = lRegions_Civs_RegionsID.get(i).size() - 1; j >= 0; j--) {
                  try {
                     Game.getCiv(lRegions_Civs.get(i)).getCivRegion(lRegions_Civs_RegionsID.get(i).get(j)).drawCivRegion_Low(oSB);
                  } catch (IndexOutOfBoundsException var4) {
                     updateRegionsInView2();
                     break;
                  } catch (Exception var5) {
                     CFG.exceptionStack(var5);
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

   public interface Renderer_CivRegionNames {
      void draw(SpriteBatch var1);

      void update();
   }
}
