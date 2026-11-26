package aoc.kingdoms.lukasz.menusScenarioEditor;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Keyboard;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_Write;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class Scenario_Calendar extends Menu {
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static String sYear = "";

   public Scenario_Calendar(boolean visible) {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2 + Images.boxTitleBORDERWIDTH;
      int titleHeight = ImageManager.getImage(Images.title2).getHeight();
      int menuWidth = ImageManager.getImage(Images.insideTop).getWidth();
      int menuX = CFG.PADDING * 2;
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title1Red).getHeight();
      int buttonY = CFG.PADDING * 2;
      int buttonX = Images.boxTitleBORDERWIDTH + CFG.PADDING * 2;
      int buttonH = CFG.TEXT_HEIGHT + CFG.PADDING * 4;
      sYear = "" + Game_Calendar.currentYear;
      menuElements.add(new ButtonGame(Game.lang.get("Close"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, true) {
         @Override
         public void actionElement() {
            Game.menuManager.setVisibleScenario_Calendar(false);
            Game.keyboard.hideKeyboard();
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING * 2;
      menuElements.add(
         new Text_StaticBG_Write(
            Game.lang.get("Year") + ": ", CFG.FONT_REGULAR_SMALL, CFG.PADDING * 2, paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT2
         ) {
            @Override
            public String getTextToDraw() {
               return Scenario_Calendar.sYear + Keyboard.getKeyboardVerticalLine();
            }

            @Override
            public void actionElement() {
               Game.keyboard.showKeyboard(Keyboard.KeyboardActionType.SCENARIO_YEAR, Scenario_Calendar.sYear);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING * 2;
      menuElements.add(new Text_StaticBG("-", CFG.FONT_REGULAR, -1, buttonX, buttonY, CFG.BUTTON_HEIGHT3, CFG.BUTTON_HEIGHT3) {
         @Override
         public void actionElement() {
            Game_Calendar.currentMonth--;
            if (Game_Calendar.currentMonth < 0) {
               Game_Calendar.currentMonth = 11;
            }

            Game.menuManager.rebuildScenario_Calendar();
         }
      });
      buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
      menuElements.add(
         new Text_StaticBG(
            "" + Game_Calendar.getMonthName(Game_Calendar.currentMonth),
            CFG.FONT_REGULAR,
            -1,
            buttonX,
            buttonY,
            menuWidth - paddingLeft * 2 - CFG.PADDING * 2 - CFG.BUTTON_HEIGHT3 * 2,
            CFG.BUTTON_HEIGHT3
         )
      );
      buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
      menuElements.add(new Text_StaticBG("+", CFG.FONT_REGULAR, -1, buttonX, buttonY, CFG.BUTTON_HEIGHT3, CFG.BUTTON_HEIGHT3) {
         @Override
         public void actionElement() {
            Game_Calendar.currentMonth++;
            if (Game_Calendar.currentMonth > 11) {
               Game_Calendar.currentMonth = 0;
            }

            Game.menuManager.rebuildScenario_Calendar();
         }
      });
      buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING * 2;
      int tDayW = (menuWidth - paddingLeft * 2 - CFG.PADDING * 6) / 7;
      int i = 0;

      for (int tX = paddingLeft; i < Game_Calendar.getNumOfDaysInMonth(Game_Calendar.currentMonth); i++) {
         menuElements.add(new Text_StaticBG_ID("" + (i + 1), CFG.FONT_REGULAR_SMALL, -1, tX, buttonY, tDayW, CFG.BUTTON_HEIGHT3, i + 1) {
            @Override
            public void actionElement() {
               Game_Calendar.currentDay = this.getCurrent();
            }
         });
         tX += tDayW + CFG.PADDING;
         if ((i + 1) % 7 == 0) {
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
            tX = paddingLeft;
         }
      }

      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING * 2;
      buttonY = 0;
      i = 0;

      for (int iSize = menuElements.size(); i < iSize; i++) {
         if (buttonY < menuElements.get(i).getPosY() + menuElements.get(i).getHeight() + CFG.PADDING * 2) {
            buttonY = menuElements.get(i).getPosY() + menuElements.get(i).getHeight() + CFG.PADDING * 2;
         }
      }

      buttonY = Math.max(buttonY, CFG.BUTTON_HEIGHT + CFG.BUTTON_HEIGHT * 4);
      i = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - CFG.PADDING * 3);
      menuElements.add(new Empty(0, 0, menuWidth, i));
      this.initMenu(new MenuTitleIMG(Game.lang.get("DateAndAgeOfScenario"), false, false, Images.title1Red) {
         @Override
         public long getTime() {
            return Scenario_Calendar.lTime;
         }
      }, CFG.GAME_WIDTH - CFG.GAME_WIDTH / 10 - menuWidth, menuY, menuWidth, i, menuElements, visible, true);
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      if (lTime + 60L >= CFG.currentTimeMillis) {
         iTranslateX = iTranslateX - CFG.BUTTON_WIDTH + (int)(CFG.BUTTON_WIDTH * 4 / 5 * ((float)(CFG.currentTimeMillis - lTime) / 60.0F));
      }

      Renderer.drawBoxCorner(
         oSB,
         this.getPosX() + iTranslateX,
         this.getPosY() - this.getTitle().getHeight() + iTranslateY,
         this.getWidth(),
         this.getHeight() + this.getTitle().getHeight() + CFG.PADDING
      );
      Renderer.drawMenusBox(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight() + CFG.PADDING, false);
      ImageManager.getImage(Images.newGameOver)
         .draw2(
            oSB,
            this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.newGameOver).getWidth() / 2 + iTranslateX,
            this.getPosY() + iTranslateY,
            this.getWidth(),
            Math.min(this.getHeight(), ImageManager.getImage(Images.newGameOver).getHeight())
         );
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public void setVisible(boolean visible) {
      super.setVisible(visible);
      lTime = CFG.currentTimeMillis;
   }

   @Override
   public void actionCloseMenu() {
      super.actionCloseMenu();
   }
}
