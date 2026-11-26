package aoc.kingdoms.lukasz.menusMapEditor;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.jakowski.SaveLoad.SaveManager;
import aoc.kingdoms.lukasz.map.FormableCivManager;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.View;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonMain;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class EditorMap_FormableCiv extends Menu {
   public static boolean drawMap = true;

   public EditorMap_FormableCiv() {
      List<MenuElement> menuElements = new ArrayList<>();
      menuElements.add(new ButtonMain(null, 1, -1, CFG.PADDING, CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT - CFG.PADDING, CFG.BUTTON_WIDTH * 2, true) {
         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("Save"));
         }

         @Override
         public void actionElement() {
            if (FormableCivManager.activeFormableCiv.FormableCivTag == null) {
               Game.menuManager.addToast_Error(Game.lang.get("SelectCivilization") + ": " + Game.lang.get("FormableCivilization"));
            } else if (FormableCivManager.activeFormableCiv.ClaimantsTag.size() == 0) {
               Game.menuManager.addToast_Error(Game.lang.get("SelectCivilization") + ": " + Game.lang.get("Claimants"));
            } else if (FormableCivManager.activeFormableCiv.Provinces.size() == 0) {
               Game.menuManager.addToast_Error(Game.lang.get("NumberOfProvinces") + ": " + FormableCivManager.activeFormableCiv.Provinces.size());
            } else if (FormableCivManager.activeFormableCiv.CapitalProvinceID < 0) {
               Game.menuManager.addToast_Error(Game.lang.get("SelectCapital"));
            } else {
               CFG.brushTool = false;
               SaveManager.saveFormableCiv();
               Renderer.drawArmyInProvince = true;
               Game.menuManager.setViewID(View.EDITOR_MAPS_EDIT_FORMABLE_CIVS);
               Game.menuManager.addToast(Game.lang.get("Saved"));
            }
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
      menuElements.add(
         new ButtonMain("", 1, -1, CFG.GAME_WIDTH - CFG.BUTTON_WIDTH * 3 - CFG.PADDING * 2, CFG.PADDING, CFG.BUTTON_WIDTH, true, CFG.selectMode) {
            @Override
            public void actionElement() {
               CFG.selectMode = !CFG.selectMode;
            }

            @Override
            public void updateLanguage() {
               this.setText(Game.lang.get("Select"));
            }

            @Override
            public boolean getCheckboxState() {
               return CFG.selectMode;
            }
         }
      );
      menuElements.add(new ButtonMain("", 1, -1, CFG.GAME_WIDTH - CFG.BUTTON_WIDTH * 5 - CFG.PADDING * 3, CFG.PADDING, CFG.BUTTON_WIDTH * 2, true, drawMap) {
         @Override
         public void actionElement() {
            EditorMap_FormableCiv.drawMap = !EditorMap_FormableCiv.drawMap;
         }

         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("FillTheMap"));
         }

         @Override
         public boolean getCheckboxState() {
            return EditorMap_FormableCiv.drawMap;
         }
      });
      menuElements.add(
         new ButtonMain(null, 1, -1, CFG.PADDING * 2 + CFG.BUTTON_WIDTH * 2, CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT - CFG.PADDING, CFG.BUTTON_WIDTH, true) {
            @Override
            public void updateLanguage() {
               this.setText(Game.lang.get("Back"));
            }

            @Override
            public void actionElement() {
               CFG.brushTool = false;
               Renderer.drawArmyInProvince = true;
               Game.menuManager.setViewID(View.EDITOR_MAPS_EDIT_FORMABLE_CIVS);
            }
         }
      );
      this.initMenu(null, 0, 0, CFG.GAME_WIDTH, CFG.GAME_HEIGHT, menuElements, true);
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      Renderer.drawBox_EDGE_TOP_LR(
         oSB,
         Images.boxSimple,
         iTranslateX,
         iTranslateY + CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT - CFG.PADDING * 2,
         CFG.BUTTON_WIDTH * 3 + CFG.PADDING * 3,
         CFG.BUTTON_HEIGHT + CFG.PADDING * 2
      );
      Renderer.drawBox_EDGE_LorR(
         oSB,
         Images.boxSimple,
         CFG.GAME_WIDTH - CFG.BUTTON_WIDTH * 5 - CFG.PADDING * 5 + iTranslateX,
         iTranslateY,
         CFG.BUTTON_WIDTH * 5 + CFG.PADDING * 5,
         CFG.BUTTON_HEIGHT + CFG.PADDING * 2,
         false,
         true
      );
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }
}
