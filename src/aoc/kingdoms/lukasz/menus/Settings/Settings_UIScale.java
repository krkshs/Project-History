package aoc.kingdoms.lukasz.menus.Settings;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.View;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame2;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Title_v2_TextLR;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class Settings_UIScale extends Menu {
   public Settings_UIScale() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2 + Images.boxTitleBORDERWIDTH;
      int titleHeight = ImageManager.getImage(Images.title600).getHeight();
      int menuWidth = ImageManager.getImage(Images.title600).getWidth();
      int menuX = CFG.BUTTON_WIDTH + Renderer.boxBGExtraY + CFG.PADDING;
      int menuY = ImageManager.getImage(Images.topStats).getHeight() + CFG.PADDING * 2 + ImageManager.getImage(Images.title600).getHeight();
      int buttonYPadding = CFG.PADDING;
      int buttonY = buttonYPadding * 2;
      int buttonX = paddingLeft;
      menuElements.add(new ButtonGame2(Game.lang.get("Back"), 1, -1, paddingLeft, buttonY, CFG.LEFT_MENU_WIDTH2 - paddingLeft * 2, true, CFG.BUTTON_HEIGHT2) {
         @Override
         public void actionElement() {
            Renderer.drawArmyInProvince = true;
            Game.menuManager.setViewID(View.SETTINGS);
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding * 2;
      menuElements.add(
         new Text_Title_v2_TextLR(
            Game.lang.get("UIScale"),
            CFG.BUTTON_WIDTH / 4,
            Images.boxTitleBORDERWIDTH,
            buttonY,
            menuWidth - Images.boxTitleBORDERWIDTH * 2,
            CFG.TEXT_HEIGHT + CFG.PADDING * 4,
            ""
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(new ButtonGame2("0", 1, -1, paddingLeft, buttonY, CFG.LEFT_MENU_WIDTH2 - paddingLeft * 2, true, 68, !CFG.XHDPI && !CFG.XXHDPI) {
         @Override
         public void actionElement() {
            Game.settingsManager.UI_SCALE = 0;
            Game.saveSettings();
            Game.menuManager.setViewID(View.SETTINGS);
            Game.menuManager.addToastGold(Game.lang.get("GameNeedsToBeRestartedToApplyTheChanges"), Images.settings);
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(new ButtonGame2("1", 1, -1, paddingLeft, buttonY, CFG.LEFT_MENU_WIDTH2 - paddingLeft * 2, true, 90, CFG.XHDPI && !CFG.XXHDPI) {
         @Override
         public void actionElement() {
            Game.settingsManager.UI_SCALE = 1;
            Game.saveSettings();
            Game.menuManager.setViewID(View.SETTINGS);
            Game.menuManager.addToastGold(Game.lang.get("GameNeedsToBeRestartedToApplyTheChanges"), Images.settings);
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(new ButtonGame2("2", 1, -1, paddingLeft, buttonY, CFG.LEFT_MENU_WIDTH2 - paddingLeft * 2, true, 110, CFG.XXHDPI) {
         @Override
         public void actionElement() {
            Game.settingsManager.UI_SCALE = 2;
            Game.saveSettings();
            Game.menuManager.setViewID(View.SETTINGS);
            Game.menuManager.addToastGold(Game.lang.get("GameNeedsToBeRestartedToApplyTheChanges"), Images.settings);
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      int tMenuHeight = Math.min(buttonY, CFG.GAME_HEIGHT - menuY * 2 - CFG.PADDING * 2);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, tMenuHeight)));
      this.initMenu(
         new MenuTitleIMG(Game.lang.get("UIScale"), false, false, Images.title600),
         CFG.GAME_WIDTH / 10,
         CFG.GAME_HEIGHT / 8,
         menuWidth,
         tMenuHeight,
         menuElements,
         true,
         false
      );
      this.drawScrollPositionAlways = false;
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
      Renderer.drawMenusBox(
         oSB,
         this.getPosX() + iTranslateX,
         this.getPosY() + iTranslateY,
         this.getWidth(),
         this.getHeight() + CFG.PADDING,
         false,
         Images.insideTop600,
         Images.insideBot600
      );
      ImageManager.getImage(Images.rulerOver)
         .draw2(
            oSB,
            this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.rulerOver).getWidth() / 2 + iTranslateX,
            this.getPosY() + iTranslateY,
            this.getWidth(),
            Math.min(this.getHeight(), ImageManager.getImage(Images.rulerOver).getHeight())
         );
      oSB.setColor(Color.WHITE);
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }
}
