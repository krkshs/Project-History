package aoc.kingdoms.lukasz.menusMapEditor;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.View;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitle;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonMain;
import aoc.kingdoms.lukasz.menu_element.button.ButtonMainDescripted;
import aoc.kingdoms.lukasz.menus.Dialog;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class EditorMaps extends Menu {
   public EditorMaps() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2;
      int titleHeight = CFG.BUTTON_HEIGHT + CFG.BUTTON_HEIGHT / 2;
      int menuX = CFG.GAME_WIDTH / 10;
      int menuY = CFG.GAME_HEIGHT / 10;
      int buttonYPadding = CFG.PADDING * 2;
      int textPosX = CFG.PADDING * 4 + Game.map.lMaps.get(0).Icon.getWidth();
      menuElements.add(new ButtonMain(null, 1, -1, paddingLeft, CFG.PADDING, CFG.LEFT_MENU_WIDTH - paddingLeft * 2, true) {
         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("Back"));
         }

         @Override
         public void actionElement() {
            Game.menuManager.setViewID(View.EDITOR);
         }
      });
      menuElements.add(new ButtonMain("", 1, -1, paddingLeft, buttonYPadding, CFG.LEFT_MENU_WIDTH - paddingLeft * 2, true) {
         @Override
         public void actionElement() {
            Dialog.GO_TO_LINK = "http://www.ageofhistorygame.com/mapEditorTutorial.html";
            Dialog.setDialogType(Dialog.DialogType.GO_TO_LINK);
         }

         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("Tutorial"));
         }
      });
      int buttonY = buttonYPadding + menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      int i = 0;

      for (int iSize = Game.map.lMaps.size(); i < iSize; i++) {
         menuElements.add(
            new ButtonMainDescripted(
               Game.lang.get("MapType") + ": " + Game.map.lMaps.get(i).mapData.Name + " | " + Game.map.lMaps.get(i).mapData.NumOfProvinces,
               Game.lang.get("Author") + ": " + Game.map.lMaps.get(i).mapData.Author,
               1,
               textPosX,
               paddingLeft,
               buttonY,
               CFG.LEFT_MENU_WIDTH - paddingLeft * 2,
               true,
               i == Game.map.getActiveMapID()
            ) {
               @Override
               public void actionElement() {
                  Game.menuManager.setViewID(View.EDITOR_MAPS_EDIT);
               }

               @Override
               protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
                  super.drawText(oSB, iTranslateX, iTranslateY, isActive);
                  Game.map.lMaps.get(this.getCurrent())
                     .Icon
                     .draw(
                        oSB,
                        iTranslateX + this.getPosX() + CFG.PADDING * 2,
                        iTranslateY + this.getPosY() + this.getHeight() / 2 - Game.map.lMaps.get(this.getCurrent()).Icon.getHeight() / 2
                     );
               }
            }
         );
         menuElements.get(menuElements.size() - 1).setCurrent(i);
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      }

      buttonY += menuElements.get(0).getHeight() + buttonYPadding;
      this.initMenuWithBackButton(
         new MenuTitle("", 1.0F, titleHeight, true, true),
         menuX,
         titleHeight + menuY,
         CFG.LEFT_MENU_WIDTH,
         Math.min(buttonY, CFG.GAME_HEIGHT - titleHeight - menuY - CFG.PADDING * 2),
         menuElements,
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
      this.getTitle().setText(Game.lang.get("MapEditor"));
   }
}
