package aoc.kingdoms.lukasz.menusMapEditor;

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
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class EditorMapProvinceConnectionsList extends Menu {
   public EditorMapProvinceConnectionsList() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2;
      int titleHeight = CFG.BUTTON_HEIGHT;
      int menuWidth = CFG.LEFT_MENU_WIDTH / 2;
      int menuX = CFG.GAME_WIDTH - menuWidth - CFG.PADDING * 4;
      int menuY = CFG.PADDING * 4;
      int buttonYPadding = CFG.PADDING * 2;
      int buttonY = buttonYPadding;
      if (EditorMapProvinceConnections.activeProvinceID >= 0) {
         for (int i = 0; i < Game.getProvince(EditorMapProvinceConnections.activeProvinceID).getNeighboringProvincesSize(); i++) {
            menuElements.add(
               new ButtonMain(
                  "" + Game.getProvince(EditorMapProvinceConnections.activeProvinceID).getNeighboringProvinces(i),
                  1,
                  -1,
                  paddingLeft,
                  buttonY,
                  menuWidth - paddingLeft * 2,
                  true
               ) {
                  @Override
                  public void actionElement() {
                     Game.mapCoords.centerToProvinceID(this.getCurrent());
                  }

                  @Override
                  public void buildElementHover() {
                     List<MenuElement_HoverElement> nElements = new ArrayList<>();
                     List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                     nData.add(new MenuElement_HoverElement_Type_Text(Game.getProvince(this.getCurrent()).getProvinceName(), CFG.FONT_BOLD_SMALL));
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     nData.add(new MenuElement_HoverElement_Type_Line());
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Province") + ": ", CFG.FONT_BOLD_SMALL));
                     nData.add(
                        new MenuElement_HoverElement_Type_Text(
                           "" + Game.getProvince(this.getCurrent()).getProvinceID(), CFG.FONT_REGULAR_SMALL, Colors.BUTTON_TEXT_ACTIVE
                        )
                     );
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Terrain") + ": ", CFG.FONT_BOLD_SMALL));
                     nData.add(
                        new MenuElement_HoverElement_Type_Text(
                           "" + Game.terrainManager.terrains.get(Game.getProvince(this.getCurrent()).getTerrainID()).Name,
                           CFG.FONT_REGULAR_SMALL,
                           Colors.BUTTON_TEXT_ACTIVE
                        )
                     );
                     nElements.add(new MenuElement_HoverElement(nData));
                     this.menuElementHover = new MenuElement_Hover(nElements);
                  }
               }
            );
            menuElements.get(menuElements.size() - 1).setCurrent(Game.getProvince(EditorMapProvinceConnections.activeProvinceID).getNeighboringProvinces(i));
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
         }

         for (int i = 0; i < Game.getProvince(EditorMapProvinceConnections.activeProvinceID).getNeighboringSeaProvincesSize(); i++) {
            menuElements.add(
               new ButtonMain(
                  "" + Game.getProvince(EditorMapProvinceConnections.activeProvinceID).getNeighboringSeaProvinces(i),
                  1,
                  -1,
                  paddingLeft,
                  buttonY,
                  menuWidth - paddingLeft * 2,
                  true
               ) {
                  @Override
                  public void actionElement() {
                     Game.mapCoords.centerToProvinceID(this.getCurrent());
                  }

                  @Override
                  public void buildElementHover() {
                     List<MenuElement_HoverElement> nElements = new ArrayList<>();
                     List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                     nData.add(new MenuElement_HoverElement_Type_Text(Game.getProvince(this.getCurrent()).getProvinceName(), CFG.FONT_BOLD_SMALL));
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     nData.add(new MenuElement_HoverElement_Type_Line());
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Province") + ": ", CFG.FONT_BOLD_SMALL));
                     nData.add(
                        new MenuElement_HoverElement_Type_Text(
                           "" + Game.getProvince(this.getCurrent()).getProvinceID(), CFG.FONT_REGULAR_SMALL, Colors.BUTTON_TEXT_ACTIVE
                        )
                     );
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Terrain") + ": ", CFG.FONT_BOLD_SMALL));
                     nData.add(
                        new MenuElement_HoverElement_Type_Text(
                           "" + Game.terrainManager.terrains.get(Game.getProvince(this.getCurrent()).getTerrainID()).Name,
                           CFG.FONT_REGULAR_SMALL,
                           Colors.BUTTON_TEXT_ACTIVE
                        )
                     );
                     nElements.add(new MenuElement_HoverElement(nData));
                     this.menuElementHover = new MenuElement_Hover(nElements);
                  }
               }
            );
            menuElements.get(menuElements.size() - 1).setCurrent(Game.getProvince(EditorMapProvinceConnections.activeProvinceID).getNeighboringSeaProvinces(i));
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
         }
      }

      this.initMenu(
         new MenuTitle(Game.lang.get("Province") + ": " + EditorMapProvinceConnections.activeProvinceID, 1.0F, titleHeight, true, true),
         menuX,
         titleHeight + menuY,
         menuWidth,
         Math.min(buttonY, CFG.GAME_HEIGHT - titleHeight - menuY * 2),
         menuElements,
         EditorMapProvinceConnections.activeProvinceID >= 0,
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
      this.getTitle().setText(Game.lang.get("Province") + ": " + EditorMapProvinceConnections.activeProvinceID);
   }
}
