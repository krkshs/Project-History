package aoc.kingdoms.lukasz.menusMapEditor;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.FileManager;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Region;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.province.ProvinceDrawDetails;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.View;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Slider;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonMain;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class EditorMapOptimizationRegions extends Menu {
   public static int activeRegion = 0;
   public static List<EditorMapOptimizationRegions.UndoOptimizationRegions> lUndo = new ArrayList<>();
   public static List<Color> lColors = new ArrayList<>();

   public EditorMapOptimizationRegions() {
      List<MenuElement> menuElements = new ArrayList<>();
      menuElements.add(new ButtonMain("-", 1, -1, CFG.PADDING, CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT - CFG.PADDING, CFG.BUTTON_WIDTH, true) {
         @Override
         public void actionElement() {
            EditorMapOptimizationRegions.this.getMenuElement(1).setCurrent(EditorMapOptimizationRegions.this.getMenuElement(1).getCurrent() - 1);
            EditorMapOptimizationRegions.this.getMenuElement(1).setMax(Game.regions.iRegionsSize);
            EditorMapOptimizationRegions.activeRegion = EditorMapOptimizationRegions.this.getMenuElement(1).getCurrent();
            EditorMapOptimizationRegions.this.centerToActiveRegionID();
         }
      });
      menuElements.add(
         new Slider(
            "SET TO REGION ID: ",
            CFG.BUTTON_WIDTH + CFG.PADDING * 2,
            CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT - CFG.PADDING,
            CFG.GAME_WIDTH - CFG.BUTTON_WIDTH * 2 - CFG.PADDING * 4,
            CFG.BUTTON_HEIGHT,
            0,
            Game.regions.iRegionsSize,
            0
         ) {
            @Override
            public void actionElement() {
               EditorMapOptimizationRegions.activeRegion = this.getCurrent();
            }
         }
      );
      menuElements.add(
         new ButtonMain("+", 1, -1, CFG.GAME_WIDTH - CFG.PADDING - CFG.BUTTON_WIDTH, CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT - CFG.PADDING, CFG.BUTTON_WIDTH, true) {
            @Override
            public void actionElement() {
               EditorMapOptimizationRegions.this.getMenuElement(1).setCurrent(EditorMapOptimizationRegions.this.getMenuElement(1).getCurrent() + 1);
               EditorMapOptimizationRegions.this.getMenuElement(1).setMax(Game.regions.iRegionsSize);
               EditorMapOptimizationRegions.activeRegion = EditorMapOptimizationRegions.this.getMenuElement(1).getCurrent();
               EditorMapOptimizationRegions.this.centerToActiveRegionID();
            }
         }
      );
      menuElements.add(new ButtonMain("", 1, -1, CFG.PADDING, CFG.PADDING, CFG.BUTTON_WIDTH * 2, true) {
         @Override
         public void actionElement() {
            CFG.brushTool = false;
            EditorMapOptimizationRegions.saveRegions();
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
            EditorMapOptimizationRegions.popUndo();
         }

         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("Undo"));
         }

         @Override
         public boolean getClickable() {
            return EditorMapOptimizationRegions.lUndo.size() > 0;
         }
      });
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
         CFG.GAME_WIDTH - CFG.BUTTON_WIDTH * 3 - CFG.PADDING * 3 + iTranslateX,
         iTranslateY,
         CFG.BUTTON_WIDTH * 3 + CFG.PADDING * 3,
         CFG.BUTTON_HEIGHT + CFG.PADDING * 2,
         false,
         true
      );
      this.drawEditorText(oSB, iTranslateX, iTranslateY);
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   public final void drawEditorText(SpriteBatch oSB, int iTranslateX, int iTranslateY) {
      String sText = "SET TO REGION ID: "
         + activeRegion
         + (
            Game.iActiveProvince >= 0
               ? "\n\nACTIVE PROVINCE REGION ID: "
                  + Game.getRegionID(Game.iActiveProvince)
                  + "\nNUMBER OF PROVINCES: "
                  + Game.regions.lRegions.get(Game.getRegionID(Game.iActiveProvince)).getProvincesSize()
                  + "\nWIDTH: "
                  + (
                     Game.regions.lRegions.get(Game.getRegionID(Game.iActiveProvince)).getMaxX()
                        - Game.regions.lRegions.get(Game.getRegionID(Game.iActiveProvince)).getMinX()
                  )
                  + " ["
                  + (int)(
                     (
                           Game.regions.lRegions.get(Game.getRegionID(Game.iActiveProvince)).getMaxX()
                              - Game.regions.lRegions.get(Game.getRegionID(Game.iActiveProvince)).getMinX()
                        )
                        * 100.0F
                        / Game.mapBG.getWidth()
                  )
                  + "%]\nHEIGHT:"
                  + (
                     Game.regions.lRegions.get(Game.getRegionID(Game.iActiveProvince)).getMaxY()
                        - Game.regions.lRegions.get(Game.getRegionID(Game.iActiveProvince)).getMinY()
                  )
                  + " ["
                  + (int)(
                     (
                           Game.regions.lRegions.get(Game.getRegionID(Game.iActiveProvince)).getMaxY()
                              - Game.regions.lRegions.get(Game.getRegionID(Game.iActiveProvince)).getMinY()
                        )
                        * 100.0F
                        / Game.mapBG.getHeight()
                  )
                  + "%]"
               : ""
         );
      Renderer.glyphLayout.setText(Renderer.fontMain.get(0), sText);
      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.6F));
      Renderer.drawBoxCorner(
         oSB,
         iTranslateX + CFG.PADDING * 4,
         iTranslateY + CFG.PADDING * 6 + CFG.BUTTON_HEIGHT,
         (int)Renderer.glyphLayout.width,
         (int)Renderer.glyphLayout.height
      );
      oSB.setColor(Color.WHITE);
      Renderer.drawText(oSB, sText, iTranslateX + CFG.PADDING * 4, iTranslateY + CFG.PADDING * 6 + CFG.BUTTON_HEIGHT, Colors.COLOR_TEXT_TITLE);
   }

   public static void actionUpdateData(boolean addUndo) {
      if (Game.iActiveProvince >= 0) {
         if (addUndo) {
            addUndo(Game.iActiveProvince);
         }

         for (int i = 0; i < Game.regions.lRegions.size(); i++) {
            for (int j = 0; j < Game.regions.lRegions.get(i).getProvincesSize(); j++) {
               if (Game.regions.lRegions.get(i).getProvince(j) == Game.iActiveProvince) {
                  Game.regions.lRegions.get(i).removeProvince(j);
                  Game.regions.lRegions.get(i).buildRegionBounds();
               }
            }
         }

         if (activeRegion >= Game.regions.lRegions.size()) {
            lColors.add(CFG.getRandomColor());
            Game.regions.lRegions.add(new Region());
            Game.regions.lRegions.get(Game.regions.lRegions.size() - 1).addProvince(Game.iActiveProvince);
            Game.regions.lRegions.get(Game.regions.lRegions.size() - 1).buildRegionBounds();
            Game.regions.updateRegionsSize();
         } else {
            Game.regions.lRegions.get(activeRegion).addProvince(Game.iActiveProvince);
            Game.regions.lRegions.get(activeRegion).buildRegionBounds();
         }

         ProvinceDrawDetails.updateDrawProvinceDetails_OptimizationRegions(Game.iActiveProvince);
      }
   }

   public static final void addUndo(int nProvinceID) {
      if (nProvinceID >= 0) {
         if (lUndo.size() > 0) {
            if (lUndo.get(lUndo.size() - 1).iProvinceID != nProvinceID && activeRegion != Game.getRegionID(nProvinceID)) {
               if (lUndo.size() > 50) {
                  lUndo.remove(0);
                  lUndo.add(new EditorMapOptimizationRegions.UndoOptimizationRegions(nProvinceID, Game.getRegionID(nProvinceID)));
               } else {
                  lUndo.add(new EditorMapOptimizationRegions.UndoOptimizationRegions(nProvinceID, Game.getRegionID(nProvinceID)));
               }
            }
         } else if (activeRegion != Game.getRegionID(nProvinceID)) {
            lUndo.add(new EditorMapOptimizationRegions.UndoOptimizationRegions(nProvinceID, Game.getRegionID(nProvinceID)));
         }
      }
   }

   public static void popUndo() {
      if (lUndo.size() > 0) {
         Game.gameActiveProvince.setActiveProvinceID(lUndo.get(lUndo.size() - 1).iProvinceID);
         int tempRegionID = activeRegion;
         activeRegion = lUndo.get(lUndo.size() - 1).iRegionID;
         actionUpdateData(false);
         activeRegion = tempRegionID;
         if (!Game.getProvince(Game.iActiveProvince).getDrawProvince()) {
            Game.mapCoords.centerToProvinceID(Game.iActiveProvince);
         }

         lUndo.remove(lUndo.size() - 1);
      }
   }

   private final void centerToActiveRegionID() {
      for (int i = 0; i < Game.getProvincesSize(); i++) {
         if (Game.regions.getRegionID(i) == activeRegion) {
            Game.mapCoords.centerToProvinceID(i);
            Game.iActiveProvince = i;
            return;
         }
      }

      Game.menuManager.addToast("0 PROVINCES");
   }

   protected static final void saveRegions() {
      FileHandle fileSave = FileManager.getSaveType("map/" + Game.map.getFile_ActiveMap_Path() + "data/" + "ProvinceOptimizationRegions.txt");
      String sLine = "";

      for (int i = 0; i < Game.regions.lRegions.size(); i++) {
         if (Game.regions.lRegions.get(i).getProvincesSize() == 0) {
            Game.regions.lRegions.remove(i--);
            Game.regions.iRegionsSize = Game.regions.lRegions.size();
         }
      }

      for (int ix = 0; ix < Game.regions.lRegions.size(); ix++) {
         for (int j = 0; j < Game.regions.lRegions.get(ix).getProvincesSize(); j++) {
            sLine = sLine + Game.regions.lRegions.get(ix).getProvince(j) + ";";
         }

         if (ix != Game.regions.lRegions.size() - 1) {
            sLine = sLine + "\n";
         }
      }

      fileSave.writeString(sLine, false);
   }

   public static class UndoOptimizationRegions {
      public int iProvinceID;
      public int iRegionID;

      public UndoOptimizationRegions(int iProvinceID, int iRegionID) {
         this.iProvinceID = iProvinceID;
         this.iRegionID = iRegionID;
      }
   }
}
