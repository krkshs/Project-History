package aoc.kingdoms.lukasz.menusInGame.Upgrade;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Bonuses;
import aoc.kingdoms.lukasz.menu_element.button.Button_Capital;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextBonus;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_RulerTitle;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_Court;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_CourtOptions;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_Court_Government;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_UpgradeCapital extends Menu {
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;

   public InGame_UpgradeCapital() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2 + Images.boxTitleBORDERWIDTH;
      int titleHeight = ImageManager.getImage(Images.title500).getHeight();
      int menuWidth = ImageManager.getImage(Images.title500).getWidth();
      int menuX = CFG.BUTTON_WIDTH + Renderer.boxBGExtraY + CFG.PADDING;
      int menuY = ImageManager.getImage(Images.topStats).getHeight() + CFG.PADDING * 2 + ImageManager.getImage(Images.title500).getHeight();
      int buttonY = CFG.PADDING;
      int buttonX = Images.boxTitleBORDERWIDTH;
      menuElements.add(new Button_Capital(paddingLeft, buttonY));
      buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING * 3;
      int var15 = buttonY + menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      int maxIconW = ImageManager.getImage(Images.gold).getWidth();
      int statW = menuWidth - buttonX - paddingLeft;
      int statH = (Button_Capital.getButtonHeight() - CFG.PADDING * 2) / 3;
      menuElements.add(
         new Text_StaticBG_RulerTitle(
            Game.lang.get("Level") + ": " + Game.getCiv(Game.player.iCivID).getCapitalLevel() + " / " + Game.getCapital_MaxLvl(Game.player.iCivID),
            buttonX,
            buttonY,
            statW,
            statH
         )
      );
      int var21 = buttonY + menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new ButtonStatsRectIMG_Bonuses(
            Game.lang.get("MonthlyIncomePerLevel") + ": ",
            "+" + CFG.getPrecision2(GameValues.capital.CAPITAL_INCOME_PER_LVL, 100),
            Images.gold,
            buttonX,
            var21,
            statW,
            statH,
            maxIconW
         )
      );
      var21 += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new ButtonStatsRectIMG_Bonuses(
            Game.lang.get("MaximumResearch") + ": ",
            "+" + CFG.getPrecision2(GameValues.capital.CAPITAL_MAX_RESEARCH_PER_LVL, 100),
            Game_Calendar.IMG_TECHNOLOGY,
            buttonX,
            var21,
            statW,
            statH,
            maxIconW
         )
      );
      var21 += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new ButtonStatsRectIMG_Bonuses(
            Game.lang.get("ProvincesMaintenance") + ": ",
            "" + CFG.getPrecision2(GameValues.capital.CAPITAL_PROVINCES_MAINTENANCE_COST_PER_LVL * 100.0F, 100) + "%",
            Images.provinces,
            paddingLeft,
            var21,
            menuWidth - paddingLeft * 2,
            statH,
            maxIconW
         )
      );
      buttonY = var15 + menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new TextBonus(
            Game.lang.get("Cost") + ": ",
            "" + CFG.getPrecision2(Game.getCapital_Cost(Game.player.iCivID), 100),
            Images.gold,
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            CFG.TEXT_HEIGHT + CFG.PADDING * 4,
            ImageManager.getImage(Images.gold).getWidth()
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING * 2;
      menuElements.add(
         new ButtonGame(
            Game.lang.get("Cancel"),
            CFG.FONT_REGULAR,
            -1,
            paddingLeft,
            buttonY,
            (menuWidth - paddingLeft * 2 - CFG.PADDING - CFG.PADDING - CFG.BUTTON_HEIGHT2) / 2,
            true
         ) {
            @Override
            public void actionElement() {
               Game.menuManager.setVisibleInGame_PopUp(false);
            }
         }
      );
      menuElements.add(
         new ButtonGame(
            Game.lang.get("Confirm"),
            CFG.FONT_REGULAR,
            -1,
            paddingLeft + CFG.PADDING + (menuWidth - paddingLeft * 2 - CFG.PADDING - CFG.PADDING - CFG.BUTTON_HEIGHT2) / 2,
            buttonY,
            (menuWidth - paddingLeft * 2 - CFG.PADDING - CFG.BUTTON_HEIGHT2) / 2,
            true
         ) {
            @Override
            public void actionElement() {
               InGame_UpgradeCapital.confirm();
            }

            @Override
            public void buildElementHover() {
               this.menuElementHover = InGame_Court_Government.getHoverCapitalCity();
            }
         }
      );
      menuElements.add(
         new ButtonGame(
            "+",
            CFG.FONT_REGULAR,
            -1,
            paddingLeft + CFG.PADDING * 2 + (menuWidth - paddingLeft * 2 - CFG.PADDING - CFG.PADDING - CFG.BUTTON_HEIGHT2),
            buttonY,
            CFG.BUTTON_HEIGHT2,
            true
         ) {
            @Override
            public void actionElement() {
               InGame_Court_Government.upgradeCapital();
               if (Game.getCiv(Game.player.iCivID).getCapitalLevel() >= Game.getCapital_MaxLvl(Game.player.iCivID)) {
                  Game.menuManager.setVisibleInGame_PopUp(false);
               } else {
                  Game.menuManager.rebuildInGame_UpgradeCapital();
               }

               if (Game.menuManager.getVisibleInGame_Court() && InGame_CourtOptions.iActiveID == InGame_CourtOptions.iGovernmentID) {
                  Game.menuManager.rebuildInGame_Government_SavePos();
                  Game.menuManager.setVisibleInGame_Court(true);
                  InGame_Court.lTime = 0L;
               }
            }

            @Override
            public void buildElementHover() {
               this.menuElementHover = InGame_Court_Government.getHoverCapitalCity();
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      buttonY = 0;
      int i = 0;

      for (int iSize = menuElements.size(); i < iSize; i++) {
         if (buttonY < menuElements.get(i).getPosY() + menuElements.get(i).getHeight() + CFG.PADDING * 2) {
            buttonY = menuElements.get(i).getPosY() + menuElements.get(i).getHeight() + CFG.PADDING * 2;
         }
      }

      i = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - CFG.PADDING * 2);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, i)));
      this.initMenu(new MenuTitleIMG(Game.lang.get("UpgradeCapitalCity"), true, false, Images.title500) {
         @Override
         public long getTime() {
            return InGame_UpgradeCapital.lTime;
         }
      }, CFG.GAME_WIDTH / 2 - menuWidth / 2, CFG.GAME_HEIGHT / 4, menuWidth, i, menuElements, false, true);
      this.drawScrollPositionAlways = false;
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      if (lTime + 60L >= CFG.currentTimeMillis) {
         iTranslateY = iTranslateY - CFG.BUTTON_HEIGHT + (int)(CFG.BUTTON_HEIGHT * ((float)(CFG.currentTimeMillis - lTime) / 60.0F));
      }

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
         Images.insideTop500,
         Images.insideBot500
      );
      oSB.setColor(Colors.COLOR_GRADIENT);
      Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), false, true);
      oSB.setColor(Color.WHITE);
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public void setVisible(boolean visible) {
      super.setVisible(visible);
      lTime = CFG.currentTimeMillis;
   }

   public static void confirm() {
      InGame_Court_Government.upgradeCapital();
      Game.menuManager.setVisibleInGame_PopUp(false);
      if (Game.menuManager.getVisibleInGame_Court() && InGame_CourtOptions.iActiveID == InGame_CourtOptions.iGovernmentID) {
         Game.menuManager.rebuildInGame_Government_SavePos();
         Game.menuManager.setVisibleInGame_Court(true);
         InGame_Court.lTime = 0L;
      }
   }
}
