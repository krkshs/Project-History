package aoc.kingdoms.lukasz.menusMapEditor;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.jakowski.SaveLoad.SaveManager;
import aoc.kingdoms.lukasz.map.province.ProvinceDrawDetails;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.MenuManager;
import aoc.kingdoms.lukasz.menu.View;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Slider;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonMain;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class EditorMapGrowthRate extends Menu {
   public static float currentGrowthRate = 50.0F;
   public static List<EditorMapGrowthRate.UndoGrowthRate> lUndo = new ArrayList<>();

   public EditorMapGrowthRate() {
      List<MenuElement> menuElements = new ArrayList<>();
      menuElements.add(new ButtonMain("-", 1, -1, CFG.PADDING, CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT - CFG.PADDING, CFG.BUTTON_WIDTH, true) {
         @Override
         public void actionElement() {
            EditorMapGrowthRate.this.getMenuElement(1).setCurrent(EditorMapGrowthRate.this.getMenuElement(1).getCurrent() - 1);
         }
      });
      menuElements.add(
         new Slider(
            CFG.BUTTON_WIDTH + CFG.PADDING * 2,
            CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT - CFG.PADDING,
            CFG.GAME_WIDTH - CFG.BUTTON_WIDTH * 2 - CFG.PADDING * 4,
            CFG.BUTTON_HEIGHT,
            0,
            100,
            50
         ) {
            @Override
            public String getDrawText() {
               return this.getCurrent() + "%";
            }

            @Override
            public Color getColorLEFT() {
               return CFG.getGrowthRateColor(this.getCurrent(), 1.0F);
            }

            @Override
            public void actionElement() {
               EditorMapGrowthRate.currentGrowthRate = this.getCurrent();
            }
         }
      );
      menuElements.add(
         new ButtonMain("+", 1, -1, CFG.GAME_WIDTH - CFG.PADDING - CFG.BUTTON_WIDTH, CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT - CFG.PADDING, CFG.BUTTON_WIDTH, true) {
            @Override
            public void actionElement() {
               EditorMapGrowthRate.this.getMenuElement(1).setCurrent(EditorMapGrowthRate.this.getMenuElement(1).getCurrent() + 1);
            }
         }
      );
      menuElements.add(new ButtonMain("", 1, -1, CFG.PADDING, CFG.PADDING, CFG.BUTTON_WIDTH * 2, true) {
         @Override
         public void actionElement() {
            SaveManager.saveProvinceDetails();
            CFG.brushTool = false;
            Renderer.drawArmyInProvince = true;
            Game.menuManager.setViewID(View.EDITOR_MAPS_EDIT);
            Game.menuManager.addToast(Game.lang.get("Saved"));
         }

         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("Save"));
         }
      });
      menuElements.add(new ButtonMain("", 1, -1, CFG.GAME_WIDTH - CFG.BUTTON_WIDTH * 2 - CFG.PADDING, CFG.PADDING, CFG.BUTTON_WIDTH * 2, true, false) {
         @Override
         public void actionElement() {
            CFG.brushTool = !CFG.brushTool;
         }

         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("Brush"));
         }

         @Override
         public boolean getCheckboxState() {
            return CFG.brushTool;
         }
      });
      menuElements.add(new ButtonMain("", 1, -1, CFG.GAME_WIDTH - CFG.BUTTON_WIDTH * 3 - CFG.PADDING * 2, CFG.PADDING, CFG.BUTTON_WIDTH, true) {
         @Override
         public void actionElement() {
            EditorMapGrowthRate.popUndo();
         }

         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("Undo"));
         }

         @Override
         public boolean getClickable() {
            return EditorMapGrowthRate.lUndo.size() > 0;
         }
      });
      menuElements.add(
         new ButtonMain("", 1, -1, CFG.GAME_WIDTH - CFG.BUTTON_WIDTH * 4 - CFG.PADDING * 3, CFG.PADDING, CFG.BUTTON_WIDTH, MenuManager.mapEditorDrawProvinces) {
            @Override
            public void actionElement() {
               MenuManager.mapEditorDrawProvinces = !MenuManager.mapEditorDrawProvinces;
            }

            @Override
            public boolean getCheckboxState() {
               return MenuManager.mapEditorDrawProvinces;
            }

            @Override
            public void updateLanguage() {
               this.setText(Game.lang.get("Map"));
            }
         }
      );
      menuElements.add(new ButtonMain("", 1, -1, CFG.PADDING * 2 + CFG.BUTTON_WIDTH * 2, CFG.PADDING, CFG.BUTTON_WIDTH * 2, true) {
         @Override
         public void actionElement() {
            CFG.brushTool = false;
            Game.menuManager.setViewID(View.EDITOR_MAPS_EDIT);
         }

         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("Back"));
         }
      });
      this.initMenu(null, 0, 0, CFG.GAME_WIDTH, CFG.GAME_HEIGHT, menuElements, true);
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      Renderer.drawBox_EDGE_TOP_LR(
         oSB,
         Images.boxSimple,
         iTranslateX,
         iTranslateY + CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT - CFG.PADDING * 2,
         CFG.GAME_WIDTH,
         CFG.BUTTON_HEIGHT + CFG.PADDING * 2
      );
      Renderer.drawBox_EDGE_LorR(
         oSB, Images.boxSimple, iTranslateX, iTranslateY, CFG.BUTTON_WIDTH * 4 + CFG.PADDING * 3, CFG.BUTTON_HEIGHT + CFG.PADDING * 2, true, true
      );
      Renderer.drawBox_EDGE_LorR(
         oSB,
         Images.boxSimple,
         CFG.GAME_WIDTH - CFG.BUTTON_WIDTH * 4 - CFG.PADDING * 4 + iTranslateX,
         iTranslateY,
         CFG.BUTTON_WIDTH * 4 + CFG.PADDING * 4,
         CFG.BUTTON_HEIGHT + CFG.PADDING * 2,
         false,
         true
      );
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   public static void actionUpdateData(boolean addUndo) {
      if (Game.iActiveProvince >= 0 && !Game.getProvince(Game.iActiveProvince).getSeaProvince()) {
         if (addUndo) {
            addUndo(Game.iActiveProvince);
         }

         Game.getProvince(Game.iActiveProvince)
            .setGrowthRate(currentGrowthRate + (EditorMapGrowthRate_Random.iRandom > 0 ? Game.oR.nextInt(EditorMapGrowthRate_Random.iRandom + 1) : 0));
         ProvinceDrawDetails.updateDrawProvinceDetails_GrowthRate(Game.iActiveProvince);
      }
   }

   public static final void addUndo(int nProvinceID) {
      if (nProvinceID >= 0) {
         if (lUndo.size() > 0) {
            if (lUndo.get(lUndo.size() - 1).iProvinceID != nProvinceID && currentGrowthRate != Game.getProvince(nProvinceID).getGrowthRate()) {
               if (lUndo.size() > 50) {
                  lUndo.remove(0);
                  lUndo.add(new EditorMapGrowthRate.UndoGrowthRate(nProvinceID, Game.getProvince(nProvinceID).getGrowthRate()));
               } else {
                  lUndo.add(new EditorMapGrowthRate.UndoGrowthRate(nProvinceID, Game.getProvince(nProvinceID).getGrowthRate()));
               }
            }
         } else if (currentGrowthRate != Game.getProvince(nProvinceID).getGrowthRate()) {
            lUndo.add(new EditorMapGrowthRate.UndoGrowthRate(nProvinceID, Game.getProvince(nProvinceID).getGrowthRate()));
         }
      }
   }

   public static void popUndo() {
      if (lUndo.size() > 0) {
         Game.gameActiveProvince.setActiveProvinceID(lUndo.get(lUndo.size() - 1).iProvinceID);
         float tempCurrentGrowthRate = currentGrowthRate;
         currentGrowthRate = lUndo.get(lUndo.size() - 1).fGrowthRate;
         actionUpdateData(false);
         currentGrowthRate = tempCurrentGrowthRate;
         if (!Game.getProvince(Game.iActiveProvince).getDrawProvince()) {
            Game.mapCoords.centerToProvinceID(Game.iActiveProvince);
         }

         lUndo.remove(lUndo.size() - 1);
      }
   }

   public static class UndoGrowthRate {
      public int iProvinceID;
      public float fGrowthRate;

      public UndoGrowthRate(int iProvinceID, float fGrowthRate) {
         this.iProvinceID = iProvinceID;
         this.fGrowthRate = fGrowthRate;
      }
   }
}
