package aoc.kingdoms.lukasz.menusInGame.Upgrade;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Bonuses;
import aoc.kingdoms.lukasz.menu_element.button.Button_NuclearReactor;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextBonus;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_RulerTitle;
import aoc.kingdoms.lukasz.menusInGame.AtomicNukes.InGame_Nukes;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_UpgradeNuclearReactor extends Menu {
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;

   public InGame_UpgradeNuclearReactor() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2 + Images.boxTitleBORDERWIDTH;
      int titleHeight = ImageManager.getImage(Images.title500).getHeight();
      int menuWidth = ImageManager.getImage(Images.title500).getWidth();
      int menuX = CFG.BUTTON_WIDTH + Renderer.boxBGExtraY + CFG.PADDING;
      int menuY = ImageManager.getImage(Images.topStats).getHeight() + CFG.PADDING * 2 + ImageManager.getImage(Images.title500).getHeight();
      int buttonY = CFG.PADDING;
      int buttonX = Images.boxTitleBORDERWIDTH;
      menuElements.add(new Button_NuclearReactor(paddingLeft, buttonY));
      buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING * 3;
      int var15 = buttonY + menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      int maxIconW = ImageManager.getImage(Images.gold).getWidth();
      int statW = menuWidth - buttonX - paddingLeft;
      int statH = (Button_NuclearReactor.getButtonHeight() - CFG.PADDING * 2) / 3;
      menuElements.add(
         new Text_StaticBG_RulerTitle(
            Game.lang.get("Level")
               + ": "
               + Game.getCiv(Game.player.iCivID).getNuclearReactorLevel()
               + " / "
               + Game.getNuclearReactor_MaxLvl(Game.player.iCivID),
            buttonX,
            buttonY,
            statW,
            statH
         )
      );
      int var20 = buttonY + menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new ButtonStatsRectIMG_Bonuses(
            Game.lang.get("AtomicBombCost") + ": ",
            "" + CFG.getPrecision2(GameValues.atomic.NUCLEAR_REACTOR_ATOMIC_BOMB_COST_PER_LEVEL * 100.0F, 100) + "%",
            Images.nuke,
            buttonX,
            var20,
            statW,
            statH,
            maxIconW
         )
      );
      var20 += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new ButtonStatsRectIMG_Bonuses(
            Game.lang.get("ProductionEfficiencyPerLevel") + ": ",
            "+" + CFG.getPrecision2(GameValues.atomic.NUCLEAR_REACTOR_PRODUCTION_EFFICIENCY_PER_LVL, 100) + "%",
            Images.goods,
            buttonX,
            var20,
            statW,
            statH,
            maxIconW
         )
      );
      menuElements.add(
         new TextBonus(
            Game.lang.get("Cost") + ": ",
            "" + CFG.getPrecision2(Game.getNuclearReactor_Cost(Game.player.iCivID), 100),
            Images.gold,
            paddingLeft,
            var15,
            menuWidth - paddingLeft * 2,
            CFG.TEXT_HEIGHT + CFG.PADDING * 4,
            ImageManager.getImage(Images.gold).getWidth()
         )
      );
      buttonY = var15 + menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING * 2;
      menuElements.add(
         new ButtonGame(Game.lang.get("Cancel"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, (menuWidth - paddingLeft * 2 - CFG.PADDING) / 2, true) {
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
            paddingLeft + CFG.PADDING + (menuWidth - paddingLeft * 2 - CFG.PADDING) / 2,
            buttonY,
            (menuWidth - paddingLeft * 2 - CFG.PADDING) / 2,
            true
         ) {
            @Override
            public void actionElement() {
               InGame_UpgradeNuclearReactor.confirm();
            }

            @Override
            public void buildElementHover() {
               this.menuElementHover = InGame_Nukes.getHoverNuclearReactor();
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
      this.initMenu(new MenuTitleIMG(Game.lang.get("UpgradeNuclearReactor"), true, false, Images.title500) {
         @Override
         public long getTime() {
            return InGame_UpgradeNuclearReactor.lTime;
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
      InGame_Nukes.upgradeNuclearReactor();
      Game.menuManager.setVisibleInGame_PopUp(false);
   }
}
