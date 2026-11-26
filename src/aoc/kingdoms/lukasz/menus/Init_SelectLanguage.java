package aoc.kingdoms.lukasz.menus;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.FileManager;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.LanguageManager;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.View;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitle;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame2;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class Init_SelectLanguage extends Menu {
   public static List<String> languages = new ArrayList<>();
   public static List<String> languagesFiles = new ArrayList<>();
   public static View goBackToMenu = View.INIT_GAME_MENU;

   public Init_SelectLanguage() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2;
      int titleHeight = CFG.BUTTON_HEIGHT + CFG.BUTTON_HEIGHT / 2;
      int menuX = CFG.GAME_WIDTH / 10;
      int menuY = CFG.GAME_HEIGHT / 10;
      int buttonYPadding = CFG.PADDING * 2;
      int buttonY = buttonYPadding;

      for (int i = 0; i < languages.size(); i++) {
         menuElements.add(new ButtonGame2(languages.get(i), 0, -1, paddingLeft, buttonY, CFG.LEFT_MENU_WIDTH - paddingLeft * 2, true) {
            int id = 0;

            @Override
            public void actionElement() {
               Game.settingsManager.LANGUAGE_TAG = Init_SelectLanguage.languagesFiles.get(this.id);
               if (Init_SelectLanguage.goBackToMenu != View.INIT_GAME_MENU) {
                  Game.menuManager.addToastGold(Game.lang.get("GameNeedsToBeRestartedToApplyTheChanges"), Images.settings);
               } else {
                  Game.lang = new LanguageManager(Init_SelectLanguage.languagesFiles.get(this.id));
               }

               Game.saveSettings();
               Game.menuManager.setViewIDWithoutAnimation(Init_SelectLanguage.goBackToMenu);
            }

            @Override
            public void setCurrent(int nCurrent) {
               this.id = nCurrent;
            }
         });
         menuElements.get(menuElements.size() - 1).setCurrent(i);
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      }

      this.initMenu(
         new MenuTitle(Game.lang.get("SelectLanguage"), 1.0F, titleHeight, false, false),
         menuX,
         titleHeight + menuY,
         CFG.LEFT_MENU_WIDTH,
         Math.min(buttonY, CFG.GAME_HEIGHT - titleHeight - menuY * 2),
         menuElements,
         true,
         false
      );
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      oSB.setColor(new Color(0.047058824F, 0.047058824F, 0.047058824F, 1.0F));
      Images.pix.draw(oSB, iTranslateX, iTranslateY, CFG.GAME_WIDTH, CFG.GAME_HEIGHT);
      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.2F));
      InitGame.background
         .draw(
            oSB,
            iTranslateX + (CFG.GAME_WIDTH - InitGame.backgroundWidth) / 2,
            iTranslateY + (CFG.GAME_HEIGHT - InitGame.backgroundHeight) / 2,
            InitGame.backgroundWidth,
            InitGame.backgroundHeight
         );
      oSB.setColor(Color.WHITE);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.6F));
      ImageManager.getImage(Images.gradientVertical).draw(oSB, iTranslateX, iTranslateY, CFG.GAME_WIDTH, CFG.PADDING * 3);
      ImageManager.getImage(Images.gradientVertical)
         .draw(oSB, iTranslateX, CFG.GAME_HEIGHT - CFG.PADDING * 3 + iTranslateY, CFG.GAME_WIDTH, CFG.PADDING * 3, false, true);
      oSB.setColor(Color.WHITE);
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

   public static void loadLanguages() {
      try {
         FileHandle tempFileT = FileManager.loadFile("game/languages/Languages.txt");
         String tempT = tempFileT.readString();
         String[] tList = tempT.split(";");

         for (int i = 0; i < tList.length; i += 2) {
            languages.add(tList[i]);
            languagesFiles.add(tList[i + 1]);
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }
}
