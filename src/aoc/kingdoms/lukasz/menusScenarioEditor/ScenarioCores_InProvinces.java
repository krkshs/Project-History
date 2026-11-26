package aoc.kingdoms.lukasz.menusScenarioEditor;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.province.ProvinceDrawDetails;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitle;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonMain;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menusMapEditor.EditorSelectProvinces;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class ScenarioCores_InProvinces extends Menu {
   public ScenarioCores_InProvinces() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2;
      int titleHeight = CFG.BUTTON_HEIGHT;
      int menuWidth = CFG.LEFT_MENU_WIDTH * 3 / 5;
      int menuX = CFG.PADDING * 4;
      int menuY = CFG.BUTTON_HEIGHT + CFG.PADDING * 5;
      int buttonYPadding = CFG.PADDING * 2;
      int buttonY = buttonYPadding;
      List<Integer> tCores = new ArrayList<>();

      for (int i = 0; i < EditorSelectProvinces.selectedProvinces.size(); i++) {
         for (int j = 0; j < Game.getProvince(EditorSelectProvinces.selectedProvinces.get(i)).iCoresSize; j++) {
            if (!tCores.contains(Game.getProvince(EditorSelectProvinces.selectedProvinces.get(i)).getCore(j))) {
               tCores.add(Game.getProvince(EditorSelectProvinces.selectedProvinces.get(i)).getCore(j));
            }
         }
      }

      for (int i = 0; i < tCores.size(); i++) {
         menuElements.add(new ButtonMain(Game.getCiv(tCores.get(i)).getCivName(), 1, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, true) {
            int id = 0;

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Remove") + ": " + Game.lang.get("Core"), Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.x, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements, true);
            }

            @Override
            public void setCurrent(int nCurrent) {
               this.id = nCurrent;
            }

            @Override
            public void actionElement() {
               for (int ix = 0; ix < EditorSelectProvinces.selectedProvinces.size(); ix++) {
                  Game.getProvince(EditorSelectProvinces.selectedProvinces.get(ix)).removeCore_ScenarioEditor(this.id);
                  ProvinceDrawDetails.updateDrawProvinceDetails_ScenarioCores(EditorSelectProvinces.selectedProvinces.get(ix));
               }

               Game.menuManager.addToastPositive(Game.lang.get("Removed"), "", Images.v);
               Game.menuManager.rebuildScenarioCoresList_InProvince();
            }
         });
         menuElements.get(menuElements.size() - 1).setCurrent(tCores.get(i));
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      }

      this.initMenu(
         new MenuTitle(Game.lang.get("Cores"), 1.0F, titleHeight, true, true),
         menuX,
         titleHeight + menuY,
         menuWidth,
         Math.min(buttonY, CFG.GAME_HEIGHT - titleHeight - menuY * 2),
         menuElements,
         menuElements.size() > 0,
         false
      );
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      Renderer.drawBoxCorner(
         oSB,
         this.getPosX() + iTranslateX,
         this.getPosY() - this.getTitle().getHeight() + iTranslateY,
         this.getWidth(),
         this.getHeight() + this.getTitle().getHeight() + CFG.PADDING
      );
      Renderer.drawEditorMenuBG(oSB, this.getPosX(), this.getPosY(), this.getWidth(), this.getHeight() + CFG.PADDING, iTranslateX, iTranslateY);
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }
}
