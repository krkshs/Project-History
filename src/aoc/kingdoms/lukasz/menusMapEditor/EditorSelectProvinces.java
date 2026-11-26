package aoc.kingdoms.lukasz.menusMapEditor;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.FileManager;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.MenuManager;
import aoc.kingdoms.lukasz.menu.View;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonMain;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class EditorSelectProvinces extends Menu {
   public static List<Integer> selectedProvinces = new ArrayList<>();

   public EditorSelectProvinces() {
      List<MenuElement> menuElements = new ArrayList<>();
      menuElements.add(new ButtonMain(null, 1, -1, CFG.PADDING, CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT - CFG.PADDING, CFG.BUTTON_WIDTH * 2, true) {
         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("Save"));
         }

         @Override
         public void actionElement() {
            CFG.brushTool = false;
            Game.menuManager.setViewID(View.EDITOR);
            Game.menuManager.addToast(Game.lang.get("Saved"));
            if (EditorSelectProvinces.selectedProvinces.size() > 0) {
               FileHandle fileSave = FileManager.getSaveType("selectedProvinces.txt");
               fileSave.writeString("", false);

               for (int i = 0; i < EditorSelectProvinces.selectedProvinces.size(); i++) {
                  fileSave.writeString(EditorSelectProvinces.selectedProvinces.get(i) + ";", true);
               }
            }
         }

         @Override
         public void buildElementHover() {
            List<MenuElement_HoverElement> nElements = new ArrayList<>();
            List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
            nData.add(new MenuElement_HoverElement_Type_TextTitle("List of selected provinces will be saved in this file: ", CFG.FONT_REGULAR));
            nData.add(new MenuElement_HoverElement_Type_TextTitle("selectedProvinces.txt", CFG.FONT_BOLD, Colors.HOVER_GOLD));
            nData.add(new MenuElement_HoverElement_Type_ImageTitle(Images.provinces, CFG.PADDING, 0));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover(nElements);
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
         new ButtonMain(null, 1, -1, CFG.PADDING * 2 + CFG.BUTTON_WIDTH * 2, CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT - CFG.PADDING, CFG.BUTTON_WIDTH * 2, true) {
            @Override
            public void updateLanguage() {
               this.setText(Game.lang.get("Back"));
            }

            @Override
            public void actionElement() {
               CFG.brushTool = false;
               Game.menuManager.setViewID(View.EDITOR);
            }
         }
      );
      menuElements.add(
         new ButtonMain("", 1, -1, CFG.GAME_WIDTH - CFG.BUTTON_WIDTH * 3 - CFG.PADDING * 2, CFG.PADDING, CFG.BUTTON_WIDTH, CFG.selectMode, false) {
            @Override
            public void actionElement() {
               CFG.selectMode = !CFG.selectMode;
            }

            @Override
            public boolean getCheckboxState() {
               return CFG.selectMode;
            }

            @Override
            public void updateLanguage() {
               this.setText(Game.lang.get("Select"));
            }
         }
      );
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
   }

   public static final void addSelectedProvince(int id) {
      if (!selectedProvinces.contains(id)) {
         selectedProvinces.add(id);
      }
   }

   public static final void removeSelectedProvince(int id) {
      for (int i = 0; i < selectedProvinces.size(); i++) {
         if (selectedProvinces.get(i) == id) {
            selectedProvinces.remove(i);
         }
      }
   }
}
