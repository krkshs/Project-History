package aoc.kingdoms.lukasz.menusScenarioEditor.Wasteland;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitle;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.Toast;
import aoc.kingdoms.lukasz.menu_element.button.ButtonMain;
import aoc.kingdoms.lukasz.menus.Dialog;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class ScenarioWastelandOptions extends Menu {
   public ScenarioWastelandOptions() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2;
      int titleHeight = CFG.BUTTON_HEIGHT;
      int menuWidth = CFG.LEFT_MENU_WIDTH * 3 / 5;
      int menuX = CFG.GAME_WIDTH - menuWidth - CFG.PADDING * 4;
      int menuY = CFG.BUTTON_HEIGHT + CFG.PADDING * 5;
      int buttonYPadding = CFG.PADDING * 2;
      int buttonY = buttonYPadding;
      menuElements.add(new ButtonMain("", 1, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, true, true) {
         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("Brush"));
         }

         @Override
         public void actionElement() {
            CFG.brushTool = !CFG.brushTool;
         }

         @Override
         public boolean getCheckboxState() {
            return CFG.brushTool;
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(new ButtonMain("", 1, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, true, true) {
         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("Wasteland"));
         }

         @Override
         public void actionElement() {
            ScenarioWasteland.bSetWasteland_AvailableProvinces = !ScenarioWasteland.bSetWasteland_AvailableProvinces;
         }

         @Override
         public boolean getCheckboxState() {
            return ScenarioWasteland.bSetWasteland_AvailableProvinces;
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(new ButtonMain("", 1, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, true) {
         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("Undo"));
         }

         @Override
         public boolean getClickable() {
            return ScenarioWasteland.lUndo.size() > 0;
         }

         @Override
         public void actionElement() {
            ScenarioWasteland.popUndo();
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(new ButtonMain("", 1, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, true, true) {
         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("ColonizationofWasteland"));
         }

         @Override
         public void actionElement() {
            Game_Calendar.ENABLE_COLONIZATION_OF_WASTELAND = !Game_Calendar.ENABLE_COLONIZATION_OF_WASTELAND;
            if (Game_Calendar.ENABLE_COLONIZATION_OF_WASTELAND) {
               Game.menuManager.addToast(new Toast(Game.lang.get("Colonization") + " - " + Game.lang.get("Enabled"), Colors.HOVER_POSITIVE));
            } else {
               Game.menuManager.addToast(new Toast(Game.lang.get("Colonization") + " - " + Game.lang.get("Disabled"), Colors.HOVER_NEGATIVE));
            }
         }

         @Override
         public boolean getCheckboxState() {
            return Game_Calendar.ENABLE_COLONIZATION_OF_WASTELAND;
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(new ButtonMain("", 1, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, true) {
         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("Reverse"));
         }

         @Override
         public void actionElement() {
            Dialog.setDialogType(Dialog.DialogType.REVERSE_WASTELAND);
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      this.initMenu(
         new MenuTitle(Game.lang.get("Editor"), 1.0F, titleHeight, true, true),
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
      this.getTitle().setText(Game.lang.get("Editor"));
   }
}
