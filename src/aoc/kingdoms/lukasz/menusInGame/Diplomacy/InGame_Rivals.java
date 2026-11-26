package aoc.kingdoms.lukasz.menusInGame.Diplomacy;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.RivalsManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonFlag;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame_ImageSparks;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Bonuses;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_FlagCiv_Title;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Image;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG_Clear;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_RulerTitle;
import aoc.kingdoms.lukasz.menusInGame.Civ.InGame_Civ;
import aoc.kingdoms.lukasz.menusInGame.Info.InGame_Info;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_Rivals extends Menu {
   public static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static int rivalCivID;
   public static boolean backToRivalsList = false;

   public InGame_Rivals() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2 + Images.boxTitleBORDERWIDTH;
      int titleHeight = ImageManager.getImage(Images.title500).getHeight();
      int menuWidth = ImageManager.getImage(Images.insideTop500).getWidth();
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title500).getHeight();
      int buttonYPadding = CFG.PADDING * 2;
      int rightW = menuWidth - paddingLeft * 2 - ButtonFlag.getButtonWidth() - CFG.PADDING * 2;
      int rightH = (ButtonFlag.getButtonHeight() - CFG.PADDING) / 2;
      menuElements.add(
         new ButtonFlag(rivalCivID, paddingLeft, buttonYPadding, true) {
            @Override
            public int getFlagCivID() {
               return this.iCivID;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(
                  new MenuElement_HoverElement_Type_FlagCiv_Title(this.iCivID, Game.lang.get("Provinces") + ": " + Game.getCiv(this.iCivID).getNumOfProvinces())
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Population") + ": ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_LEFT));
               nData.add(
                  new MenuElement_HoverElement_Type_Text(
                     CFG.getNumberWithSpaces("" + Game.getCiv(this.iCivID).getPopulationTotal()), CFG.FONT_BOLD_SMALL, Colors.COLOR_POPULATION
                  )
               );
               nData.add(new MenuElement_HoverElement_Type_Image(Images.population, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      menuElements.add(
         new Text_StaticBG_RulerTitle(
            Game.getCiv(rivalCivID).getCivName(), paddingLeft + ButtonFlag.getButtonWidth() + CFG.PADDING * 2, buttonYPadding, rightW, rightH
         ) {
            @Override
            public void actionElement() {
               Game.mapCoords.centerToProvinceID(Game.getCiv(InGame_Rivals.rivalCivID).getCapitalProvinceID());
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(
                  new MenuElement_HoverElement_Type_FlagCiv_Title(
                     InGame_Rivals.rivalCivID, Game.lang.get("Provinces") + ": " + Game.getCiv(InGame_Rivals.rivalCivID).getNumOfProvinces()
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Capital") + ": ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_LEFT));
               nData.add(
                  new MenuElement_HoverElement_Type_Text(
                     Game.getProvince(Game.getCiv(InGame_Rivals.rivalCivID).getCapitalProvinceID()).getProvinceName(), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD
                  )
               );
               nData.add(new MenuElement_HoverElement_Type_Image(Images.capital, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      menuElements.add(
         new ButtonStatsRectIMG_Bonuses(
            Game.lang.get("MonthlyLegacy") + ": ",
            "+" + CFG.getPrecision2(RivalsManager.getLegacy(Game.player.iCivID, rivalCivID), 100),
            Images.legacy,
            paddingLeft + ButtonFlag.getButtonWidth() + CFG.PADDING * 2,
            buttonYPadding + rightH + CFG.PADDING,
            rightW,
            rightH,
            ImageManager.getImage(Images.gold).getWidth()
         ) {
            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     Game.lang.get("MonthlyLegacy") + ": ",
                     "" + this.sText2,
                     Images.legacy,
                     CFG.FONT_REGULAR_SMALL,
                     CFG.FONT_BOLD_SMALL,
                     Colors.HOVER_LEFT,
                     Colors.HOVER_GOLD
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      int buttonY = buttonYPadding + ButtonFlag.getButtonHeight() + buttonYPadding;
      menuElements.add(
         new ButtonStatsRectIMG_Bonuses(
            Game.lang.get("MaximumManpower") + ": ",
            "+" + CFG.getPrecision2((float)RivalsManager.getManpower(Game.player.iCivID, rivalCivID), 1),
            Game_Calendar.IMG_MANPOWER_UP,
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            rightH,
            ImageManager.getImage(Images.gold).getWidth()
         ) {
            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     Game.lang.get("MaximumManpower") + ": ",
                     "" + this.sText2,
                     Game_Calendar.IMG_MANPOWER_UP,
                     CFG.FONT_REGULAR_SMALL,
                     CFG.FONT_BOLD_SMALL,
                     Colors.HOVER_LEFT,
                     Colors.HOVER_GOLD
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(
         new ButtonGame(Game.lang.get("Cancel"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, (menuWidth - paddingLeft * 2 - CFG.PADDING / 2 * 2) / 2, true) {
            @Override
            public void actionElement() {
               Game.menuManager.setVisibleInGame_PopUp(false);
               if (InGame_Rivals.backToRivalsList && Game.getCiv(Game.player.iCivID).diplomacy.rivals.size() < GameValues.rivals.RIVALS_LIMIT) {
                  Game.menuManager.rebuildInGame_RivalsList();
               }
            }
         }
      );
      menuElements.add(
         new ButtonGame_ImageSparks(
            Game.lang.get("Confirm"),
            CFG.FONT_REGULAR,
            -1,
            paddingLeft + CFG.PADDING + (menuWidth - paddingLeft * 2 - CFG.PADDING / 2 * 2) / 2,
            buttonY,
            (menuWidth - paddingLeft * 2 - CFG.PADDING / 2 * 2) / 2,
            true,
            Images.rivals
         ) {
            @Override
            public void actionElement() {
               InGame_Rivals.confirm();
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Rival") + ": ", CFG.FONT_BOLD, Colors.HOVER_GOLD));
               nData.add(
                  new MenuElement_HoverElement_Type_TextTitle_BG_Clear(Game.getCiv(InGame_Rivals.rivalCivID).getCivName(), CFG.FONT_BOLD, Colors.HOVER_GOLD)
               );
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.rivals, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements, true);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      int menuHeight = Math.min(buttonY, CFG.GAME_HEIGHT - titleHeight - menuY);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, buttonY)));
      this.initMenu(
         new MenuTitleIMG(Game.lang.get("Rivalry"), true, false, Images.title500) {
            @Override
            public long getTime() {
               return InGame_Rivals.lTime;
            }
         },
         CFG.GAME_WIDTH / 2 - menuWidth / 2,
         Math.min((int)(CFG.GAME_HEIGHT * 0.2F), CFG.GAME_HEIGHT / 2 - (menuHeight + titleHeight) / 2),
         menuWidth,
         menuHeight,
         menuElements,
         false,
         true
      );
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      if (lTime + 60L >= CFG.currentTimeMillis) {
         iTranslateY = iTranslateY - CFG.BUTTON_HEIGHT * 3 / 5 + (int)(CFG.BUTTON_HEIGHT * 3 / 5 * ((float)(CFG.currentTimeMillis - lTime) / 60.0F));
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
      ImageManager.getImage(Images.civInfoOver)
         .draw2(
            oSB,
            this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.civInfoOver).getWidth() / 2 + iTranslateX,
            this.getPosY() + iTranslateY,
            this.getWidth(),
            Math.min(this.getHeight(), ImageManager.getImage(Images.civInfoOver).getHeight())
         );
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public void setVisible(boolean visible) {
      super.setVisible(visible);
      lTime = CFG.currentTimeMillis;
   }

   public static void confirm() {
      if (Game.getCiv(Game.player.iCivID).diplomacy.addRival(Game.player.iCivID, rivalCivID)) {
         InGame_Info.iCivID = Game.player.iCivID;
         InGame_Info.iCivID2 = rivalCivID;
         Game.menuManager
            .rebuildInGame_Info(Game.lang.get("Rivals"), Game.getCiv(Game.player.iCivID).getCivName() + " - " + Game.getCiv(rivalCivID).getCivName());
         InGame_Info.imgID = Images.infoDiplomacy;
         if (Game.menuManager.getVisibleInGame_Civ()) {
            InGame_Civ.iRebuildToCivID = rivalCivID;
            Game.menuManager.rebuildInGame_Civ(true);
            InGame_Civ.lTime = 0L;
         }
      } else {
         Game.menuManager.addToast_Error(Game.lang.get("Rivals") + ": " + Game.lang.get("Limit") + ": " + GameValues.rivals.RIVALS_LIMIT, Images.rivals);
      }

      Game.menuManager.setVisibleInGame_PopUp(false);
      if (backToRivalsList && Game.getCiv(Game.player.iCivID).diplomacy.rivals.size() < GameValues.rivals.RIVALS_LIMIT) {
         Game.menuManager.rebuildInGame_RivalsList();
      }
   }
}
