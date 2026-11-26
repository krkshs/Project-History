package aoc.kingdoms.lukasz.menusInGame;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.SoundsManager;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG_FlagCenter;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonCurrentSituation;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRect_ArmyPlayer;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Title_v2_TextLR;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_Court;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_CourtOptions2;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_ArmyCustomize extends Menu {
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;

   public InGame_ArmyCustomize() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2 + Images.boxTitleBORDERWIDTH;
      int titleHeight = ImageManager.getImage(Images.title500).getHeight();
      int menuWidth = ImageManager.getImage(Images.title500).getWidth();
      int menuX = InGame_CourtOptions2.getOtherMenuPosX_2();
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title1Red).getHeight();
      int buttonY = CFG.PADDING;
      int buttonX = Images.boxTitleBORDERWIDTH;
      menuElements.add(
         new ButtonCurrentSituation(
            Game.lang.get(GameValues.court.COUNCIL_NAME),
            Images.council,
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            CFG.BUTTON_HEIGHT4,
            ImageManager.getImage(Images.council).getWidth() + CFG.PADDING * 4,
            true
         ) {
            @Override
            public void actionElement() {
               InGame_Court.iActiveCivID = Game.player.iCivID;
               Game.menuManager.rebuildInGame_Court();
               Game.menuManager.setVisibleInGame_Court(true);
               InGame_Court.lTime = 0L;
               Game.setRegroupArmyMode(false);
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get(GameValues.court.COUNCIL_NAME), Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.council, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements, true);
            }

            @Override
            public int getSFX() {
               return SoundsManager.getClickSound_CivOptions();
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new Text_Title_v2_TextLR(
            Game.lang.get("Colors"),
            CFG.BUTTON_WIDTH / 4,
            Images.boxTitleBORDERWIDTH,
            buttonY,
            menuWidth - Images.boxTitleBORDERWIDTH * 2,
            CFG.TEXT_HEIGHT + CFG.PADDING * 4,
            ""
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      int buttonH = CFG.BUTTON_HEIGHT3;
      int buttonW = (menuWidth - paddingLeft * 2 - CFG.PADDING) / 2;
      menuElements.add(new ButtonStatsRect_ArmyPlayer("1", 0, Images.armyPlayer0, paddingLeft, buttonY, buttonW, buttonH));
      menuElements.add(new ButtonStatsRect_ArmyPlayer("2", 1, Images.armyPlayer1, paddingLeft + CFG.PADDING + buttonW, buttonY, buttonW, buttonH));
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(new ButtonStatsRect_ArmyPlayer("3", 2, Images.armyPlayer2, paddingLeft, buttonY, buttonW, buttonH));
      menuElements.add(new ButtonStatsRect_ArmyPlayer("4", 3, Images.armyPlayer3, paddingLeft + CFG.PADDING + buttonW, buttonY, buttonW, buttonH));
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(new ButtonStatsRect_ArmyPlayer("5", 4, Images.armyPlayer4, paddingLeft, buttonY, buttonW, buttonH));
      menuElements.add(new ButtonStatsRect_ArmyPlayer("6", 5, Images.armyPlayer5, paddingLeft + CFG.PADDING + buttonW, buttonY, buttonW, buttonH));
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(new ButtonStatsRect_ArmyPlayer("7", 6, Images.armyPlayer6, paddingLeft, buttonY, buttonW, buttonH));
      menuElements.add(new ButtonStatsRect_ArmyPlayer("8", 7, Images.armyPlayer7, paddingLeft + CFG.PADDING + buttonW, buttonY, buttonW, buttonH));
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(new ButtonStatsRect_ArmyPlayer("9", 8, Images.armyPlayer8, paddingLeft, buttonY, buttonW, buttonH));
      menuElements.add(new ButtonStatsRect_ArmyPlayer("10", 9, Images.armyPlayer9, paddingLeft + CFG.PADDING + buttonW, buttonY, buttonW, buttonH));
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(new ButtonStatsRect_ArmyPlayer("11", 10, Images.armyPlayer10, paddingLeft, buttonY, buttonW, buttonH));
      menuElements.add(new ButtonStatsRect_ArmyPlayer("12", 11, Images.armyPlayer11, paddingLeft + CFG.PADDING + buttonW, buttonY, buttonW, buttonH));
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(new ButtonStatsRect_ArmyPlayer("13", 12, Images.armyPlayer12, paddingLeft, buttonY, buttonW, buttonH));
      menuElements.add(new ButtonStatsRect_ArmyPlayer("14", 13, Images.armyPlayer13, paddingLeft + CFG.PADDING + buttonW, buttonY, buttonW, buttonH));
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(new ButtonStatsRect_ArmyPlayer("15", 14, Images.armyPlayer14, paddingLeft, buttonY, buttonW, buttonH));
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      buttonY = 0;
      int i = 0;

      for (int iSize = menuElements.size(); i < iSize; i++) {
         if (buttonY < menuElements.get(i).getPosY() + menuElements.get(i).getHeight() + CFG.PADDING * 2) {
            buttonY = menuElements.get(i).getPosY() + menuElements.get(i).getHeight() + CFG.PADDING * 2;
         }
      }

      i = Math.min(buttonY, CFG.GAME_HEIGHT - menuY * 2 - CFG.PADDING * 2);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, i)));
      this.initMenu(new MenuTitleIMG_FlagCenter(Game.lang.get("Armies"), Game.getCiv(Game.player.iCivID).getCivName(), true, false, Images.title500) {
         @Override
         public int getFlagCivID() {
            return Game.player.iCivID;
         }

         @Override
         public long getTime() {
            return InGame_ArmyCustomize.lTime;
         }
      }, menuX, menuY, menuWidth, i, menuElements, false, true);
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
}
