package aoc.kingdoms.lukasz.menusMapEditor;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.jakowski.SaveLoad.SaveManager;
import aoc.kingdoms.lukasz.map.province.ProvinceDrawDetails;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.MenuManager;
import aoc.kingdoms.lukasz.menu.View;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonMain;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class EditorMapTerrainType extends Menu {
   protected static int currentTerrainTypeID = 1;
   protected static List<EditorMapTerrainType.UndoTerrain> lUndo = new ArrayList<>();

   public EditorMapTerrainType() {
      List<MenuElement> menuElements = new ArrayList<>();
      menuElements.add(new ButtonMain(null, 1, -1, CFG.PADDING, CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT - CFG.PADDING, CFG.BUTTON_WIDTH * 2, true) {
         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("Save"));
         }

         @Override
         public void actionElement() {
            SaveManager.saveProvinceDetails();
            Renderer.drawArmyInProvince = true;
            Game.menuManager.setViewID(View.EDITOR_MAPS_EDIT);
            Game.menuManager.addToast(Game.lang.get("Saved"));
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
            EditorMapTerrainType.popUndo();
         }

         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("Undo"));
         }

         @Override
         public boolean getClickable() {
            return EditorMapTerrainType.lUndo.size() > 0;
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
      menuElements.add(
         new ButtonMain(null, 1, -1, CFG.PADDING * 2 + CFG.BUTTON_WIDTH * 2, CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT - CFG.PADDING, CFG.BUTTON_WIDTH * 2, true) {
            @Override
            public void updateLanguage() {
               this.setText(Game.lang.get("Back"));
            }

            @Override
            public void actionElement() {
               Game.menuManager.setViewID(View.EDITOR_MAPS_EDIT);
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
         CFG.BUTTON_WIDTH * 4 + CFG.PADDING * 3,
         CFG.BUTTON_HEIGHT + CFG.PADDING * 2
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
      this.drawEditorText(oSB, iTranslateX, iTranslateY);
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   public final void drawEditorText(SpriteBatch oSB, int iTranslateX, int iTranslateY) {
      String sText = Game.lang.get("Terrain") + ": " + Game.terrainManager.terrains.get(currentTerrainTypeID).Name;
      Renderer.glyphLayout.setText(Renderer.fontMain.get(0), sText);
      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.6F));
      Renderer.drawBoxCorner(
         oSB, iTranslateX + CFG.PADDING * 4, iTranslateY + CFG.PADDING * 4, (int)Renderer.glyphLayout.width, (int)Renderer.glyphLayout.height
      );
      oSB.setColor(Color.WHITE);
      Renderer.drawText(oSB, sText, iTranslateX + CFG.PADDING * 4, iTranslateY + CFG.PADDING * 4, Colors.COLOR_TEXT_TITLE);
   }

   public static void actionUpdateData(boolean addUndo) {
      if (Game.iActiveProvince >= 0 && !Game.getProvince(Game.iActiveProvince).getSeaProvince()) {
         if (addUndo) {
            addUndo(Game.iActiveProvince);
         }

         Game.getProvince(Game.iActiveProvince).setTerrainID(currentTerrainTypeID);
         ProvinceDrawDetails.updateDrawProvinceDetails_Terrain(Game.iActiveProvince);
      }
   }

   private static final void addUndo(int nProvinceID) {
      if (nProvinceID >= 0) {
         if (lUndo.size() > 0) {
            if (lUndo.get(lUndo.size() - 1).iProvinceID != nProvinceID && currentTerrainTypeID != Game.getProvince(nProvinceID).getTerrainID()) {
               if (lUndo.size() > 50) {
                  lUndo.remove(0);
               }

               lUndo.add(new EditorMapTerrainType.UndoTerrain(nProvinceID, Game.getProvince(nProvinceID).getTerrainID()));
            }
         } else if (currentTerrainTypeID != Game.getProvince(nProvinceID).getTerrainID()) {
            lUndo.add(new EditorMapTerrainType.UndoTerrain(nProvinceID, Game.getProvince(nProvinceID).getTerrainID()));
         }
      }
   }

   protected static void popUndo() {
      if (lUndo.size() > 0) {
         Game.iActiveProvince = lUndo.get(lUndo.size() - 1).iProvinceID;
         currentTerrainTypeID = lUndo.get(lUndo.size() - 1).iTerrainID;
         actionUpdateData(false);
         if (!Game.getProvince(Game.iActiveProvince).getDrawProvince()) {
            Game.mapCoords.centerToProvinceID(Game.iActiveProvince);
         }

         lUndo.remove(lUndo.size() - 1);
      }
   }

   public static class UndoTerrain {
      protected int iProvinceID;
      protected int iTerrainID;

      public UndoTerrain(int iProvinceID, int iTerrainID) {
         this.iProvinceID = iProvinceID;
         this.iTerrainID = iTerrainID;
      }
   }
}
