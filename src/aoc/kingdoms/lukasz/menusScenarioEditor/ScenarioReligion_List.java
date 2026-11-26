package aoc.kingdoms.lukasz.menusScenarioEditor;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.province.ProvinceDrawDetails;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.View;
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
import aoc.kingdoms.lukasz.menusScenarioEditor.Diplomacy.ScenarioDiplomacy;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class ScenarioReligion_List extends Menu {
   public ScenarioReligion_List() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2;
      int titleHeight = CFG.BUTTON_HEIGHT;
      int menuWidth = CFG.LEFT_MENU_WIDTH * 3 / 5;
      int menuX = CFG.PADDING * 4;
      int menuY = CFG.BUTTON_HEIGHT + CFG.PADDING * 5;
      int buttonYPadding = CFG.PADDING * 2;
      int buttonY = buttonYPadding;
      menuElements.add(new ButtonMain(Game.lang.get("Default"), 1, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, true) {
         int id = 0;

         @Override
         public void buildElementHover() {
            List<MenuElement_HoverElement> nElements = new ArrayList<>();
            List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
            nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Religion") + ": " + this.getText(), Colors.HOVER_GOLD));
            nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.religion, CFG.PADDING, 0));
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
            if (ScenarioDiplomacy.goBackTo == View.IN_GAME) {
               for (int i = 0; i < EditorSelectProvinces.selectedProvinces.size(); i++) {
                  Game.getProvince(EditorSelectProvinces.selectedProvinces.get(i)).setReligion(this.id);
                  ProvinceDrawDetails.updateDrawProvinceDetails_ScenarioReligion(EditorSelectProvinces.selectedProvinces.get(i));
                  Game.getProvince(EditorSelectProvinces.selectedProvinces.get(i)).updateProvinceIncome();
                  Game.gameThread.addCivUpdateTotalIncomePerMonth(Game.getProvince(EditorSelectProvinces.selectedProvinces.get(i)).getCivID());
               }
            } else {
               for (int i = 0; i < EditorSelectProvinces.selectedProvinces.size(); i++) {
                  Game.mapScenarios.editorProvinceReligion.set(EditorSelectProvinces.selectedProvinces.get(i), this.id);
                  ProvinceDrawDetails.updateDrawProvinceDetails_ScenarioReligion(EditorSelectProvinces.selectedProvinces.get(i));
               }
            }

            Game.menuManager.addToastPositive(Game.lang.get("Done"), "", Images.v);
         }
      });
      menuElements.get(menuElements.size() - 1).setCurrent(-1);
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;

      for (int i = 0; i < Game.religionManager.getReligionsSize(); i++) {
         menuElements.add(new ButtonMain(Game.religionManager.getReligion(i).Name, 1, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, true) {
            int id = 0;

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Religion") + ": " + this.getText(), Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.religion, CFG.PADDING, 0));
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
               if (ScenarioDiplomacy.goBackTo == View.IN_GAME) {
                  for (int i = 0; i < EditorSelectProvinces.selectedProvinces.size(); i++) {
                     Game.getProvince(EditorSelectProvinces.selectedProvinces.get(i)).setReligion(this.id);
                     ProvinceDrawDetails.updateDrawProvinceDetails_ScenarioReligion(EditorSelectProvinces.selectedProvinces.get(i));
                     Game.getProvince(EditorSelectProvinces.selectedProvinces.get(i)).updateProvinceIncome();
                     Game.gameThread.addCivUpdateTotalIncomePerMonth(Game.getProvince(EditorSelectProvinces.selectedProvinces.get(i)).getCivID());
                  }
               } else {
                  for (int i = 0; i < EditorSelectProvinces.selectedProvinces.size(); i++) {
                     Game.mapScenarios.editorProvinceReligion.set(EditorSelectProvinces.selectedProvinces.get(i), this.id);
                     ProvinceDrawDetails.updateDrawProvinceDetails_ScenarioReligion(EditorSelectProvinces.selectedProvinces.get(i));
                  }
               }

               Game.menuManager.addToastPositive(Game.lang.get("Done"), "", Images.v);
            }
         });
         menuElements.get(menuElements.size() - 1).setCurrent(i);
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      }

      this.initMenu(
         new MenuTitle(Game.lang.get("Religion"), 1.0F, titleHeight, true, true),
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
