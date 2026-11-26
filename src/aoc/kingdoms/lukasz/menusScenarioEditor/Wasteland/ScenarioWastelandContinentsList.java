package aoc.kingdoms.lukasz.menusScenarioEditor.Wasteland;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitle;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonMain;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class ScenarioWastelandContinentsList extends Menu {
   public ScenarioWastelandContinentsList() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2;
      int titleHeight = CFG.BUTTON_HEIGHT;
      int menuWidth = CFG.LEFT_MENU_WIDTH * 3 / 5;
      int menuX = CFG.GAME_WIDTH - menuWidth - CFG.PADDING * 4;
      int menuY = CFG.BUTTON_HEIGHT + CFG.PADDING * 5;
      int buttonYPadding = CFG.PADDING * 2;
      int buttonY = buttonYPadding;
      int i = 1;

      for (int iSize = Game.continents.lContinents.size(); i < iSize; i++) {
         menuElements.add(
            new ButtonMain("" + Game.continents.lContinents.get(i).sName, 1, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, true, true) {
               @Override
               public void actionElement() {
                  int numOfWastelandProvinces = 0;
                  int numOfNormalProvinces = 0;
                  int chosenContinent = this.getCurrent();

                  for (int ix = 0; ix < Game.getProvincesSize(); ix++) {
                     if (Game.getProvince(ix).getContinent() == chosenContinent) {
                        if (Game.getProvince(ix).getWasteland() >= 0) {
                           numOfWastelandProvinces++;
                        } else {
                           numOfNormalProvinces++;
                        }
                     }
                  }

                  this.setCheckboxState(numOfWastelandProvinces > numOfNormalProvinces);

                  for (int ix = 0; ix < Game.getProvincesSize(); ix++) {
                     if (Game.getProvince(ix).getContinent() == chosenContinent) {
                        Game.getProvince(ix).setWasteland(numOfWastelandProvinces < numOfNormalProvinces ? 0 : -1);
                     }
                  }

                  Game.buildWastelandLevels();
                  Game.updateProvincesInView = true;
                  Game.menuManager
                     .addToast(this.getText() + ": " + (numOfWastelandProvinces < numOfNormalProvinces ? Game.lang.get("Disabled") : Game.lang.get("Enabled")));
               }

               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.continents.lContinents.get(this.getCurrent()).sName, Colors.HOVER_GOLD));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(new MenuElement_HoverElement_Type_Line());
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("NumberOfProvinces") + ": ", CFG.FONT_REGULAR_SMALL));
                  nData.add(
                     new MenuElement_HoverElement_Type_Text("" + Game.countContinentProvinces(this.getCurrent()), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT)
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements);
               }
            }
         );
         menuElements.get(menuElements.size() - 1).setCurrent(i);
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      }

      this.initMenu(
         new MenuTitle(Game.lang.get("Continents"), 1.0F, titleHeight, true, true),
         menuX,
         titleHeight + menuY,
         menuWidth,
         Math.min(buttonY, CFG.GAME_HEIGHT - titleHeight - menuY * 2),
         menuElements,
         true,
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

   @Override
   public void updateLanguage() {
      super.updateLanguage();
      this.getTitle().setText(Game.lang.get("Continents"));
   }
}
