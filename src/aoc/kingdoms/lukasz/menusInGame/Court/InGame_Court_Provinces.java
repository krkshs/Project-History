package aoc.kingdoms.lukasz.menusInGame.Court;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Keyboard;
import aoc.kingdoms.lukasz.jakowski.SoundsManager;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.ResourcesManager;
import aoc.kingdoms.lukasz.map.province.ProvinceBorderManager;
import aoc.kingdoms.lukasz.menu.ClickAnimation;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.HoverManager;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.MenuManager;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonCurrentSituation;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextIcon2_Economy;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextIcon2_GrowthRate;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextIcon2_Infrastructure;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextIcon2_Manpower;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextIcon2_ProvinceIncome;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextIcon2_Tax;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextIcon_Resource_Small;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID_FlagCiv_Special;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID_FlagCiv_SpecialEmpty;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_Write;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_TitleBlueSort;
import aoc.kingdoms.lukasz.menusInGame.Province.InGame_ProvinceInfo;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_Court_Provinces extends Menu {
   public static int iSortID = 2;
   public static String sSearch = "";

   public InGame_Court_Provinces() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = Images.boxTitleBORDERWIDTH + CFG.PADDING * 2;
      int paddingLeft2 = Images.boxTitleBORDERWIDTH + CFG.PADDING;
      int menuWidth = ImageManager.getImage(Images.insideTop500).getWidth();
      int menuX = InGame_CourtOptions2.getOtherMenuPosX();
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING;
      int buttonYPadding = CFG.PADDING * 2;
      int buttonY = CFG.PADDING;
      int buttonH = CFG.isDesktop() ? CFG.BUTTON_HEIGHT3 : CFG.BUTTON_HEIGHT2;
      int statsRightW = (menuWidth - paddingLeft * 2 - CFG.PADDING * 6) / 7;
      int statsRightH = CFG.BUTTON_HEIGHT;
      int emptyBGH = buttonH + CFG.PADDING + statsRightH;
      int r0W = (int)((menuWidth - Images.boxTitleBORDERWIDTH * 2) / 4.0F);
      int r1W = (int)((menuWidth - Images.boxTitleBORDERWIDTH * 2) / 5.0F);
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
               InGame_CourtOptions.iActiveID = InGame_CourtOptions2.idCourt;
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
         new Text_StaticBG_Write(
            Game.lang.get("Search") + ": ", CFG.FONT_REGULAR_SMALL, CFG.PADDING * 2, paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT2
         ) {
            @Override
            public String getTextToDraw() {
               return InGame_Court_Provinces.sSearch + Keyboard.getKeyboardVerticalLine();
            }

            @Override
            public void actionElement() {
               Game.keyboard.showKeyboard(Keyboard.KeyboardActionType.SEARCH_COURT_PROVINCES, InGame_Court_Provinces.sSearch);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new Text_TitleBlueSort(
            iSortID == 0 || iSortID == 1,
            iSortID == 1,
            Game.lang.get("Name"),
            -1,
            buttonX,
            buttonY,
            r0W,
            CFG.TEXT_HEIGHT + CFG.PADDING * 6,
            CFG.FONT_BOLD_SMALL
         ) {
            @Override
            public void drawLines(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            }

            @Override
            public void actionElement() {
               if (InGame_Court_Provinces.iSortID == 0) {
                  InGame_Court_Provinces.iSortID = 1;
               } else {
                  InGame_Court_Provinces.iSortID = 0;
               }

               Game.menuManager.rebuildInGame_CourtProvinces();
               Game.menuManager.setVisibleInGame_Court(true);
               InGame_Court.lTime = 0L;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("SortBy") + ": "));
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Name"), Colors.HOVER_GOLD));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      buttonX += menuElements.get(menuElements.size() - 1).getWidth();
      menuElements.add(
         new Text_TitleBlueSort(
            iSortID == 2 || iSortID == 3,
            iSortID == 3,
            Game.lang.get("Population"),
            -1,
            buttonX,
            buttonY,
            r0W,
            CFG.TEXT_HEIGHT + CFG.PADDING * 6,
            CFG.FONT_BOLD_SMALL
         ) {
            @Override
            public void drawLines(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            }

            @Override
            public void actionElement() {
               if (InGame_Court_Provinces.iSortID == 2) {
                  InGame_Court_Provinces.iSortID = 3;
               } else {
                  InGame_Court_Provinces.iSortID = 2;
               }

               Game.menuManager.rebuildInGame_CourtProvinces();
               Game.menuManager.setVisibleInGame_Court(true);
               InGame_Court.lTime = 0L;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("SortBy") + ": "));
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Population"), Colors.HOVER_GOLD));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      buttonX += menuElements.get(menuElements.size() - 1).getWidth();
      menuElements.add(
         new Text_TitleBlueSort(
            iSortID == 4 || iSortID == 5,
            iSortID == 5,
            Game.lang.get("Income"),
            -1,
            buttonX,
            buttonY,
            r0W,
            CFG.TEXT_HEIGHT + CFG.PADDING * 6,
            CFG.FONT_BOLD_SMALL
         ) {
            @Override
            public void drawLines(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            }

            @Override
            public void actionElement() {
               if (InGame_Court_Provinces.iSortID == 4) {
                  InGame_Court_Provinces.iSortID = 5;
               } else {
                  InGame_Court_Provinces.iSortID = 4;
               }

               Game.menuManager.rebuildInGame_CourtProvinces();
               Game.menuManager.setVisibleInGame_Court(true);
               InGame_Court.lTime = 0L;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("SortBy") + ": "));
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Income"), Colors.HOVER_GOLD));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      buttonX += menuElements.get(menuElements.size() - 1).getWidth();
      menuElements.add(
         new Text_TitleBlueSort(
            iSortID == 6 || iSortID == 7,
            iSortID == 7,
            Game.lang.get("TaxEfficiency"),
            -1,
            buttonX,
            buttonY,
            r0W,
            CFG.TEXT_HEIGHT + CFG.PADDING * 6,
            CFG.FONT_BOLD_SMALL
         ) {
            @Override
            public void drawLines(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            }

            @Override
            public void actionElement() {
               if (InGame_Court_Provinces.iSortID == 6) {
                  InGame_Court_Provinces.iSortID = 7;
               } else {
                  InGame_Court_Provinces.iSortID = 6;
               }

               Game.menuManager.rebuildInGame_CourtProvinces();
               Game.menuManager.setVisibleInGame_Court(true);
               InGame_Court.lTime = 0L;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("SortBy") + ": "));
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("TaxEfficiency"), Colors.HOVER_GOLD));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      buttonX = Images.boxTitleBORDERWIDTH;
      buttonY += menuElements.get(menuElements.size() - 1).getHeight();
      menuElements.add(
         new Text_TitleBlueSort(
            iSortID == 8 || iSortID == 9,
            iSortID == 9,
            Game.lang.get("GrowthRate"),
            -1,
            buttonX,
            buttonY,
            r1W,
            CFG.TEXT_HEIGHT + CFG.PADDING * 6,
            CFG.FONT_BOLD_SMALL
         ) {
            @Override
            public void drawLines(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            }

            @Override
            public void actionElement() {
               if (InGame_Court_Provinces.iSortID == 8) {
                  InGame_Court_Provinces.iSortID = 9;
               } else {
                  InGame_Court_Provinces.iSortID = 8;
               }

               Game.menuManager.rebuildInGame_CourtProvinces();
               Game.menuManager.setVisibleInGame_Court(true);
               InGame_Court.lTime = 0L;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("SortBy") + ": "));
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("GrowthRate"), Colors.HOVER_GOLD));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      buttonX += menuElements.get(menuElements.size() - 1).getWidth();
      menuElements.add(
         new Text_TitleBlueSort(
            iSortID == 10 || iSortID == 11,
            iSortID == 11,
            Game.lang.get("Manpower"),
            -1,
            buttonX,
            buttonY,
            r1W,
            CFG.TEXT_HEIGHT + CFG.PADDING * 6,
            CFG.FONT_BOLD_SMALL
         ) {
            @Override
            public void drawLines(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            }

            @Override
            public void actionElement() {
               if (InGame_Court_Provinces.iSortID == 10) {
                  InGame_Court_Provinces.iSortID = 11;
               } else {
                  InGame_Court_Provinces.iSortID = 10;
               }

               Game.menuManager.rebuildInGame_CourtProvinces();
               Game.menuManager.setVisibleInGame_Court(true);
               InGame_Court.lTime = 0L;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("SortBy") + ": "));
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Manpower"), Colors.HOVER_GOLD));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      buttonX += menuElements.get(menuElements.size() - 1).getWidth();
      menuElements.add(
         new Text_TitleBlueSort(
            iSortID == 12 || iSortID == 13,
            iSortID == 13,
            Game.lang.get("Infrastructure"),
            -1,
            buttonX,
            buttonY,
            r1W,
            CFG.TEXT_HEIGHT + CFG.PADDING * 6,
            CFG.FONT_BOLD_SMALL
         ) {
            @Override
            public void drawLines(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            }

            @Override
            public void actionElement() {
               if (InGame_Court_Provinces.iSortID == 12) {
                  InGame_Court_Provinces.iSortID = 13;
               } else {
                  InGame_Court_Provinces.iSortID = 12;
               }

               Game.menuManager.rebuildInGame_CourtProvinces();
               Game.menuManager.setVisibleInGame_Court(true);
               InGame_Court.lTime = 0L;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("SortBy") + ": "));
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Infrastructure"), Colors.HOVER_GOLD));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      buttonX += menuElements.get(menuElements.size() - 1).getWidth();
      menuElements.add(
         new Text_TitleBlueSort(
            iSortID == 14 || iSortID == 15,
            iSortID == 15,
            Game.lang.get("Economy"),
            -1,
            buttonX,
            buttonY,
            r1W,
            CFG.TEXT_HEIGHT + CFG.PADDING * 6,
            CFG.FONT_BOLD_SMALL
         ) {
            @Override
            public void drawLines(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            }

            @Override
            public void actionElement() {
               if (InGame_Court_Provinces.iSortID == 14) {
                  InGame_Court_Provinces.iSortID = 15;
               } else {
                  InGame_Court_Provinces.iSortID = 14;
               }

               Game.menuManager.rebuildInGame_CourtProvinces();
               Game.menuManager.setVisibleInGame_Court(true);
               InGame_Court.lTime = 0L;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("SortBy") + ": "));
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Economy"), Colors.HOVER_GOLD));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      buttonX += menuElements.get(menuElements.size() - 1).getWidth();
      menuElements.add(
         new Text_TitleBlueSort(
            iSortID == 16 || iSortID == 17,
            iSortID == 17,
            Game.lang.get("Resource"),
            -1,
            buttonX,
            buttonY,
            r1W,
            CFG.TEXT_HEIGHT + CFG.PADDING * 6,
            CFG.FONT_BOLD_SMALL
         ) {
            @Override
            public void drawLines(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            }

            @Override
            public void actionElement() {
               if (InGame_Court_Provinces.iSortID == 16) {
                  InGame_Court_Provinces.iSortID = 17;
               } else {
                  InGame_Court_Provinces.iSortID = 16;
               }

               Game.menuManager.rebuildInGame_CourtProvinces();
               Game.menuManager.setVisibleInGame_Court(true);
               InGame_Court.lTime = 0L;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("SortBy") + ": "));
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Resource"), Colors.HOVER_GOLD));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      buttonX += menuElements.get(menuElements.size() - 1).getWidth();
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      List<Integer> tProvinces = new ArrayList<>();
      if (sSearch.length() > 0) {
         String tSearch = sSearch.toLowerCase();

         for (int i = 0; i < Game.getCiv(Game.player.iCivID).getNumOfProvinces(); i++) {
            if (Game.getProvince(Game.getCiv(Game.player.iCivID).getProvinceID(i)).getProvinceName().toLowerCase().indexOf(tSearch) >= 0) {
               tProvinces.add(Game.getCiv(Game.player.iCivID).getProvinceID(i));
            }
         }
      } else {
         for (int ix = 0; ix < Game.getCiv(Game.player.iCivID).getNumOfProvinces(); ix++) {
            tProvinces.add(Game.getCiv(Game.player.iCivID).getProvinceID(ix));
         }
      }

      if (tProvinces.size() > 0) {
         int numOfAdded = 0;

         while (tProvinces.size() > 0 && numOfAdded++ < GameValues.inGame.COURT_PROVINCES_LIMIT) {
            int toAddID = 0;
            if (iSortID == 0) {
               for (int o = 1; o < tProvinces.size(); o++) {
                  if (CFG.compareAlphabetic_TwoString(
                     Game.getProvince(tProvinces.get(toAddID)).getProvinceName(), Game.getProvince(tProvinces.get(o)).getProvinceName()
                  )) {
                     toAddID = o;
                  }
               }
            } else if (iSortID == 1) {
               for (int ox = 1; ox < tProvinces.size(); ox++) {
                  if (CFG.compareAlphabetic_TwoString(
                     Game.getProvince(tProvinces.get(ox)).getProvinceName(), Game.getProvince(tProvinces.get(toAddID)).getProvinceName()
                  )) {
                     toAddID = ox;
                  }
               }
            } else if (iSortID == 2) {
               for (int oxx = 1; oxx < tProvinces.size(); oxx++) {
                  if (Game.getProvince(tProvinces.get(oxx)).getPopulationTotal() > Game.getProvince(tProvinces.get(toAddID)).getPopulationTotal()) {
                     toAddID = oxx;
                  }
               }
            } else if (iSortID == 3) {
               for (int oxxx = 1; oxxx < tProvinces.size(); oxxx++) {
                  if (Game.getProvince(tProvinces.get(oxxx)).getPopulationTotal() < Game.getProvince(tProvinces.get(toAddID)).getPopulationTotal()) {
                     toAddID = oxxx;
                  }
               }
            } else if (iSortID == 4) {
               for (int oxxxx = 1; oxxxx < tProvinces.size(); oxxxx++) {
                  if (Game.getProvince(tProvinces.get(oxxxx)).getProvinceIncome() - Game.getProvince(tProvinces.get(oxxxx)).fProvinceMaintenance
                     > Game.getProvince(tProvinces.get(toAddID)).getProvinceIncome() - Game.getProvince(tProvinces.get(toAddID)).fProvinceMaintenance) {
                     toAddID = oxxxx;
                  }
               }
            } else if (iSortID == 5) {
               for (int oxxxxx = 1; oxxxxx < tProvinces.size(); oxxxxx++) {
                  if (Game.getProvince(tProvinces.get(oxxxxx)).getProvinceIncome() - Game.getProvince(tProvinces.get(oxxxxx)).fProvinceMaintenance
                     < Game.getProvince(tProvinces.get(toAddID)).getProvinceIncome() - Game.getProvince(tProvinces.get(toAddID)).fProvinceMaintenance) {
                     toAddID = oxxxxx;
                  }
               }
            } else if (iSortID == 6) {
               for (int oxxxxxx = 1; oxxxxxx < tProvinces.size(); oxxxxxx++) {
                  if (Game.getProvince(tProvinces.get(oxxxxxx)).getTaxEfficiencyWithBonuses()
                     > Game.getProvince(tProvinces.get(toAddID)).getTaxEfficiencyWithBonuses()) {
                     toAddID = oxxxxxx;
                  }
               }
            } else if (iSortID == 7) {
               for (int oxxxxxxx = 1; oxxxxxxx < tProvinces.size(); oxxxxxxx++) {
                  if (Game.getProvince(tProvinces.get(oxxxxxxx)).getTaxEfficiencyWithBonuses()
                     < Game.getProvince(tProvinces.get(toAddID)).getTaxEfficiencyWithBonuses()) {
                     toAddID = oxxxxxxx;
                  }
               }
            } else if (iSortID == 8) {
               for (int oxxxxxxxx = 1; oxxxxxxxx < tProvinces.size(); oxxxxxxxx++) {
                  if (Game.getProvince(tProvinces.get(oxxxxxxxx)).getGrowthRateWithBonuses()
                     > Game.getProvince(tProvinces.get(toAddID)).getGrowthRateWithBonuses()) {
                     toAddID = oxxxxxxxx;
                  }
               }
            } else if (iSortID == 9) {
               for (int oxxxxxxxxx = 1; oxxxxxxxxx < tProvinces.size(); oxxxxxxxxx++) {
                  if (Game.getProvince(tProvinces.get(oxxxxxxxxx)).getGrowthRateWithBonuses()
                     < Game.getProvince(tProvinces.get(toAddID)).getGrowthRateWithBonuses()) {
                     toAddID = oxxxxxxxxx;
                  }
               }
            } else if (iSortID == 10) {
               for (int oxxxxxxxxxx = 1; oxxxxxxxxxx < tProvinces.size(); oxxxxxxxxxx++) {
                  if (Game.getProvince(tProvinces.get(oxxxxxxxxxx)).getManpower() > Game.getProvince(tProvinces.get(toAddID)).getManpower()) {
                     toAddID = oxxxxxxxxxx;
                  }
               }
            } else if (iSortID == 11) {
               for (int oxxxxxxxxxxx = 1; oxxxxxxxxxxx < tProvinces.size(); oxxxxxxxxxxx++) {
                  if (Game.getProvince(tProvinces.get(oxxxxxxxxxxx)).getManpower() < Game.getProvince(tProvinces.get(toAddID)).getManpower()) {
                     toAddID = oxxxxxxxxxxx;
                  }
               }
            } else if (iSortID == 12) {
               for (int oxxxxxxxxxxxx = 1; oxxxxxxxxxxxx < tProvinces.size(); oxxxxxxxxxxxx++) {
                  if (Game.getProvince(tProvinces.get(oxxxxxxxxxxxx)).getInfrastructure() > Game.getProvince(tProvinces.get(toAddID)).getInfrastructure()) {
                     toAddID = oxxxxxxxxxxxx;
                  }
               }
            } else if (iSortID == 13) {
               for (int oxxxxxxxxxxxxx = 1; oxxxxxxxxxxxxx < tProvinces.size(); oxxxxxxxxxxxxx++) {
                  if (Game.getProvince(tProvinces.get(oxxxxxxxxxxxxx)).getInfrastructure() < Game.getProvince(tProvinces.get(toAddID)).getInfrastructure()) {
                     toAddID = oxxxxxxxxxxxxx;
                  }
               }
            } else if (iSortID == 14) {
               for (int oxxxxxxxxxxxxxx = 1; oxxxxxxxxxxxxxx < tProvinces.size(); oxxxxxxxxxxxxxx++) {
                  if (Game.getProvince(tProvinces.get(oxxxxxxxxxxxxxx)).getEconomyWithBonuses()
                     > Game.getProvince(tProvinces.get(toAddID)).getEconomyWithBonuses()) {
                     toAddID = oxxxxxxxxxxxxxx;
                  }
               }
            } else if (iSortID == 15) {
               for (int oxxxxxxxxxxxxxxx = 1; oxxxxxxxxxxxxxxx < tProvinces.size(); oxxxxxxxxxxxxxxx++) {
                  if (Game.getProvince(tProvinces.get(oxxxxxxxxxxxxxxx)).getEconomyWithBonuses()
                     < Game.getProvince(tProvinces.get(toAddID)).getEconomyWithBonuses()) {
                     toAddID = oxxxxxxxxxxxxxxx;
                  }
               }
            } else if (iSortID == 16) {
               for (int oxxxxxxxxxxxxxxxx = 1; oxxxxxxxxxxxxxxxx < tProvinces.size(); oxxxxxxxxxxxxxxxx++) {
                  if (CFG.compareAlphabetic_TwoString(
                     ResourcesManager.getResourceName(Game.getProvince(tProvinces.get(toAddID)).getResourceID()),
                     ResourcesManager.getResourceName(Game.getProvince(tProvinces.get(oxxxxxxxxxxxxxxxx)).getResourceID())
                  )) {
                     toAddID = oxxxxxxxxxxxxxxxx;
                  }
               }
            } else if (iSortID == 17) {
               for (int oxxxxxxxxxxxxxxxxx = 1; oxxxxxxxxxxxxxxxxx < tProvinces.size(); oxxxxxxxxxxxxxxxxx++) {
                  if (CFG.compareAlphabetic_TwoString(
                     ResourcesManager.getResourceName(Game.getProvince(tProvinces.get(oxxxxxxxxxxxxxxxxx)).getResourceID()),
                     ResourcesManager.getResourceName(Game.getProvince(tProvinces.get(toAddID)).getResourceID())
                  )) {
                     toAddID = oxxxxxxxxxxxxxxxxx;
                  }
               }
            }

            int nProvinceID = tProvinces.get(toAddID);
            menuElements.add(
               new Text_StaticBG_ID_FlagCiv_Special(
                  Game.getProvince(nProvinceID).getProvinceName(),
                  CFG.FONT_REGULAR,
                  CFG.PADDING * 2,
                  paddingLeft,
                  buttonY,
                  menuWidth - paddingLeft * 2,
                  buttonH,
                  nProvinceID,
                  Images.population,
                  CFG.getNumberWithSpaces("" + Game.getProvince(nProvinceID).getPopulationTotal())
               ) {
                  @Override
                  public void actionElement() {
                     if (Game.iActiveProvince == this.getCurrent()) {
                        Game.menuManager.hideCourtCiv();
                        Game.menuManager.setVisibleInGame_Civ(false);
                        Game.setActiveProvinceID(this.getCurrent());
                        Game.menuManager.rebuildInGame_ProvinceInfo(true);
                        ProvinceBorderManager.action.setProvinceID(Game.iActiveProvince);
                     } else {
                        Game.mapCoords.centerToProvinceID(this.getCurrent());
                        Game.setActiveProvinceID(this.getCurrent());
                        ProvinceBorderManager.action.setProvinceID(Game.iActiveProvince);
                     }
                  }

                  @Override
                  public void buildElementHover() {
                     this.menuElementHover = InGame_ProvinceInfo.getHoverProvince(this.id);
                  }

                  @Override
                  public Color getColor2(boolean isActive) {
                     return Colors.getColorPopulation(isActive, this.getIsHovered());
                  }
               }
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight();
            menuElements.add(
               new TextIcon2_ProvinceIncome(
                  nProvinceID,
                  "" + CFG.getPrecision2(Game.getProvince(nProvinceID).getProvinceIncome() - Game.getProvince(nProvinceID).fProvinceMaintenance, 100),
                  Images.gold,
                  paddingLeft,
                  buttonY,
                  statsRightW,
                  statsRightH
               ) {
                  @Override
                  public void buildElementHover() {
                     this.menuElementHover = InGame_ProvinceInfo.getHoverProvinceIncome(this.iProvinceID, false);
                  }

                  @Override
                  public void actionElementPPM() {
                     if (CFG.isDesktop() && GameValues.mapModes.PROVINCE_INFO_HOVER_PROVINCE_INCOME) {
                        this.menuElementHover = null;
                     }
                  }

                  @Override
                  public void updateHovered() {
                     super.updateHovered();
                     if (CFG.isDesktop()
                        && GameValues.mapModes.PROVINCE_INFO_HOVER_PROVINCE_INCOME
                        && HoverManager.hoverTime + GameValues.mapModes.HOVER_TIME <= CFG.currentTimeMillis
                        && this.getIsHovered()
                        && Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_DEFAULT) {
                        Game.mapModes.setActiveViewID(Game.mapModes.MODE_PROVINCE_PROVINCE_INCOME_HOVER);
                     }
                  }

                  @Override
                  public void setIsHovered(boolean isHovered) {
                     super.setIsHovered(isHovered);
                     if (CFG.isDesktop()
                        && GameValues.mapModes.PROVINCE_INFO_HOVER_PROVINCE_INCOME
                        && !isHovered
                        && Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_PROVINCE_PROVINCE_INCOME_HOVER) {
                        Game.mapModes.setActiveViewID(Game.mapModes.MODE_DEFAULT);
                     }
                  }
               }
            );
            buttonX = paddingLeft + menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
            menuElements.add(
               new TextIcon2_Tax(
                  nProvinceID,
                  "" + CFG.getPrecision2(Game.getProvince(nProvinceID).getTaxEfficiencyWithBonuses(), 10) + "%",
                  Images.tax,
                  buttonX,
                  buttonY,
                  statsRightW,
                  statsRightH
               ) {
                  @Override
                  public int getSFX() {
                     return Game.soundsManager.getCoin();
                  }

                  @Override
                  public void buildElementHover() {
                     this.menuElementHover = InGame_ProvinceInfo.getHoverTaxEfficiency(this.iProvinceID, false);
                  }

                  @Override
                  public void actionElement() {
                     if (InGame_ProvinceInfo.actionTax(this.iProvinceID)) {
                        MenuManager.addClickAnimation(
                           new ClickAnimation(
                              this.getPosX() + this.getWidth() / 2 + InGame_Court_Provinces.this.getMenuPosX(),
                              this.getPosY() + this.getHeight() / 2 + InGame_Court_Provinces.this.getMenuPosY(),
                              this.getWidth(),
                              this.getHeight()
                           ) {
                              @Override
                              public Color getColor() {
                                 return Colors.HOVER_YELLOW;
                              }
                           }
                        );
                     }
                  }

                  @Override
                  public void actionElementPPM() {
                     if (CFG.isDesktop() && GameValues.mapModes.PROVINCE_INFO_HOVER_TAX_EFFICIENCY) {
                        this.menuElementHover = null;
                     }
                  }

                  @Override
                  public void updateHovered() {
                     super.updateHovered();
                     if (CFG.isDesktop()
                        && GameValues.mapModes.PROVINCE_INFO_HOVER_TAX_EFFICIENCY
                        && HoverManager.hoverTime + GameValues.mapModes.HOVER_TIME <= CFG.currentTimeMillis
                        && this.getIsHovered()
                        && Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_DEFAULT) {
                        Game.mapModes.setActiveViewID(Game.mapModes.MODE_PROVINCE_TAX_EFFICIENCY_HOVER);
                     }
                  }

                  @Override
                  public void setIsHovered(boolean isHovered) {
                     super.setIsHovered(isHovered);
                     if (CFG.isDesktop()
                        && GameValues.mapModes.PROVINCE_INFO_HOVER_TAX_EFFICIENCY
                        && !isHovered
                        && Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_PROVINCE_TAX_EFFICIENCY_HOVER) {
                        Game.mapModes.setActiveViewID(Game.mapModes.MODE_DEFAULT);
                     }
                  }
               }
            );
            buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
            menuElements.add(
               new TextIcon2_GrowthRate(
                  nProvinceID,
                  "" + CFG.getPrecision2(Game.getProvince(nProvinceID).getGrowthRateWithBonuses(), 10) + "%",
                  Images.populationGrowth,
                  buttonX,
                  buttonY,
                  statsRightW,
                  statsRightH
               ) {
                  @Override
                  public int getSFX() {
                     return SoundsManager.getGrowthRate();
                  }

                  @Override
                  public void actionElement() {
                     if (InGame_ProvinceInfo.actionGrowthRate(this.iProvinceID)) {
                        MenuManager.addClickAnimation(
                           new ClickAnimation(
                              this.getPosX() + this.getWidth() / 2 + InGame_Court_Provinces.this.getMenuPosX(),
                              this.getPosY() + this.getHeight() / 2 + InGame_Court_Provinces.this.getMenuPosY(),
                              this.getWidth(),
                              this.getHeight()
                           ) {
                              @Override
                              public Color getColor() {
                                 return Colors.COLOR_POPULATION;
                              }
                           }
                        );
                     }
                  }

                  @Override
                  public void buildElementHover() {
                     this.menuElementHover = InGame_ProvinceInfo.getHoverPopulationGrowth(this.iProvinceID, false, true);
                  }

                  @Override
                  public void actionElementPPM() {
                     if (CFG.isDesktop() && GameValues.mapModes.PROVINCE_INFO_HOVER_GROWTH_RATE) {
                        this.menuElementHover = null;
                     }
                  }

                  @Override
                  public void updateHovered() {
                     super.updateHovered();
                     if (CFG.isDesktop()
                        && GameValues.mapModes.PROVINCE_INFO_HOVER_GROWTH_RATE
                        && HoverManager.hoverTime + GameValues.mapModes.HOVER_TIME <= CFG.currentTimeMillis
                        && this.getIsHovered()
                        && Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_DEFAULT) {
                        Game.mapModes.setActiveViewID(Game.mapModes.MODE_PROVINCE_GROWTH_RATE_HOVER);
                     }
                  }

                  @Override
                  public void setIsHovered(boolean isHovered) {
                     super.setIsHovered(isHovered);
                     if (CFG.isDesktop()
                        && GameValues.mapModes.PROVINCE_INFO_HOVER_GROWTH_RATE
                        && !isHovered
                        && Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_PROVINCE_GROWTH_RATE_HOVER) {
                        Game.mapModes.setActiveViewID(Game.mapModes.MODE_DEFAULT);
                     }
                  }
               }
            );
            buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
            menuElements.add(
               new TextIcon2_Manpower(nProvinceID, "1", Game_Calendar.IMG_MANPOWER, buttonX, buttonY, statsRightW, statsRightH) {
                  @Override
                  public int getSFX() {
                     return Game.soundsManager.getClickIncreaseManpower();
                  }

                  @Override
                  public void actionElement() {
                     if (InGame_ProvinceInfo.actionManpower(this.iProvinceID)) {
                        MenuManager.addClickAnimation(
                           new ClickAnimation(
                              this.getPosX() + this.getWidth() / 2 + InGame_Court_Provinces.this.getMenuPosX(),
                              this.getPosY() + this.getHeight() / 2 + InGame_Court_Provinces.this.getMenuPosY(),
                              this.getWidth(),
                              this.getHeight()
                           )
                        );
                     }
                  }

                  @Override
                  public int getImageID() {
                     return Game_Calendar.IMG_MANPOWER;
                  }

                  @Override
                  public void buildElementHover() {
                     this.menuElementHover = InGame_ProvinceInfo.getHoverManpower(this.iProvinceID, false, true);
                  }

                  @Override
                  public void actionElementPPM() {
                     if (CFG.isDesktop() && GameValues.mapModes.PROVINCE_INFO_HOVER_MANPOWER) {
                        this.menuElementHover = null;
                     }
                  }

                  @Override
                  public void updateHovered() {
                     super.updateHovered();
                     if (CFG.isDesktop()
                        && GameValues.mapModes.PROVINCE_INFO_HOVER_MANPOWER
                        && HoverManager.hoverTime + GameValues.mapModes.HOVER_TIME <= CFG.currentTimeMillis
                        && this.getIsHovered()
                        && Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_DEFAULT) {
                        Game.mapModes.setActiveViewID(Game.mapModes.MODE_PROVINCE_MANPOWER_HOVER);
                     }
                  }

                  @Override
                  public void setIsHovered(boolean isHovered) {
                     super.setIsHovered(isHovered);
                     if (CFG.isDesktop()
                        && GameValues.mapModes.PROVINCE_INFO_HOVER_MANPOWER
                        && !isHovered
                        && Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_PROVINCE_MANPOWER_HOVER) {
                        Game.mapModes.setActiveViewID(Game.mapModes.MODE_DEFAULT);
                     }
                  }
               }
            );
            buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
            menuElements.add(
               new TextIcon2_Infrastructure(nProvinceID, "", Images.infrastructure, buttonX, buttonY, statsRightW, statsRightH) {
                  @Override
                  public int getSFX() {
                     return Game.soundsManager.getInfrastructure();
                  }

                  @Override
                  public void actionElement() {
                     if (InGame_ProvinceInfo.actionInfrastructure(this.iProvinceID)) {
                        MenuManager.addClickAnimation(
                           new ClickAnimation(
                              this.getPosX() + this.getWidth() / 2 + InGame_Court_Provinces.this.getMenuPosX(),
                              this.getPosY() + this.getHeight() / 2 + InGame_Court_Provinces.this.getMenuPosY(),
                              this.getWidth(),
                              this.getHeight()
                           ) {
                              @Override
                              public Color getColor() {
                                 return Colors.HOVER_LEFT;
                              }
                           }
                        );
                     }
                  }

                  @Override
                  public void buildElementHover() {
                     this.menuElementHover = InGame_ProvinceInfo.getHoverInfrastructure(this.iProvinceID, false);
                  }

                  @Override
                  public void actionElementPPM() {
                     if (CFG.isDesktop() && GameValues.mapModes.PROVINCE_INFO_HOVER_INFRASTRUCTURE) {
                        this.menuElementHover = null;
                     }
                  }

                  @Override
                  public void updateHovered() {
                     super.updateHovered();
                     if (CFG.isDesktop()
                        && GameValues.mapModes.PROVINCE_INFO_HOVER_INFRASTRUCTURE
                        && HoverManager.hoverTime + GameValues.mapModes.HOVER_TIME <= CFG.currentTimeMillis
                        && this.getIsHovered()
                        && Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_DEFAULT) {
                        Game.mapModes.setActiveViewID(Game.mapModes.MODE_PROVINCE_INFRASTRUCTURE_HOVER);
                     }
                  }

                  @Override
                  public void setIsHovered(boolean isHovered) {
                     super.setIsHovered(isHovered);
                     if (CFG.isDesktop()
                        && GameValues.mapModes.PROVINCE_INFO_HOVER_INFRASTRUCTURE
                        && !isHovered
                        && Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_PROVINCE_INFRASTRUCTURE_HOVER) {
                        Game.mapModes.setActiveViewID(Game.mapModes.MODE_DEFAULT);
                     }
                  }
               }
            );
            buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
            menuElements.add(
               new TextIcon2_Economy(
                  nProvinceID,
                  "" + CFG.getPrecision2(Game.getProvince(nProvinceID).getEconomyWithBonuses(), 10),
                  Game_Calendar.IMG_ECONOMY,
                  buttonX,
                  buttonY,
                  statsRightW,
                  statsRightH
               ) {
                  @Override
                  public int getSFX() {
                     return Game.soundsManager.getEconomy();
                  }

                  @Override
                  public void buildElementHover() {
                     this.menuElementHover = InGame_ProvinceInfo.getHoverEconomy(this.iProvinceID, false);
                  }

                  @Override
                  public void actionElement() {
                     if (InGame_ProvinceInfo.actionEconomy(this.iProvinceID)) {
                        MenuManager.addClickAnimation(
                           new ClickAnimation(
                              this.getPosX() + this.getWidth() / 2 + InGame_Court_Provinces.this.getMenuPosX(),
                              this.getPosY() + this.getHeight() / 2 + InGame_Court_Provinces.this.getMenuPosY(),
                              this.getWidth(),
                              this.getHeight()
                           ) {
                              @Override
                              public Color getColor() {
                                 return Colors.COLOR_TEXT_ECONOMY;
                              }
                           }
                        );
                     }
                  }

                  @Override
                  public void actionElementPPM() {
                     if (CFG.isDesktop() && GameValues.mapModes.PROVINCE_INFO_HOVER_ECONOMY) {
                        this.menuElementHover = null;
                     }
                  }

                  @Override
                  public void updateHovered() {
                     super.updateHovered();
                     if (CFG.isDesktop()
                        && GameValues.mapModes.PROVINCE_INFO_HOVER_ECONOMY
                        && HoverManager.hoverTime + GameValues.mapModes.HOVER_TIME <= CFG.currentTimeMillis
                        && this.getIsHovered()
                        && Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_DEFAULT) {
                        Game.mapModes.setActiveViewID(Game.mapModes.MODE_PROVINCE_ECONOMY_HOVER);
                     }
                  }

                  @Override
                  public void setIsHovered(boolean isHovered) {
                     super.setIsHovered(isHovered);
                     if (CFG.isDesktop()
                        && GameValues.mapModes.PROVINCE_INFO_HOVER_ECONOMY
                        && !isHovered
                        && Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_PROVINCE_ECONOMY_HOVER) {
                        Game.mapModes.setActiveViewID(Game.mapModes.MODE_DEFAULT);
                     }
                  }
               }
            );
            buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
            menuElements.add(
               new TextIcon_Resource_Small(
                  nProvinceID,
                  ResourcesManager.getResourceName(Game.getProvince(nProvinceID).getResourceID()),
                  Game.getProvince(nProvinceID).getResourceID(),
                  buttonX,
                  buttonY,
                  statsRightW,
                  statsRightH
               ) {
                  @Override
                  public void buildElementHover() {
                     this.menuElementHover = InGame_ProvinceInfo.getHoverResource(this.iProvinceID, this.resourceID, false);
                  }
               }
            );
            buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
            menuElements.add(new Text_StaticBG_ID_FlagCiv_SpecialEmpty(paddingLeft2, buttonY - emptyBGH, menuWidth - paddingLeft2 * 2, emptyBGH));
            buttonY += CFG.PADDING;
            tProvinces.remove(toAddID);
         }
      } else {
         menuElements.add(new Text_StaticBG(Game.lang.get("None"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT2));
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      }

      menuY += InGame_CourtOptions.menuH;
      int menuHeight = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - CFG.PADDING * 3);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, menuHeight)));
      this.initMenu(null, menuX, menuY, menuWidth, menuHeight, menuElements, false, false);
      this.drawScrollPositionAlways = false;
      Game.menuManager.setInGame_CivOptions_Title(Game.lang.get("Provinces") + ": " + Game.getCiv(Game.player.iCivID).getNumOfProvinces());
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      if (InGame_Court.lTime + 60L >= CFG.currentTimeMillis) {
         iTranslateX = iTranslateX - CFG.BUTTON_WIDTH + (int)(CFG.BUTTON_WIDTH * ((float)(CFG.currentTimeMillis - InGame_Court.lTime) / 60.0F));
      }

      Renderer.drawBoxCorner(
         oSB,
         this.getPosX() + iTranslateX,
         this.getPosY() - InGame_CourtOptions.menuH + iTranslateY,
         this.getWidth(),
         this.getHeight() + InGame_CourtOptions.menuH + CFG.PADDING
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
      ImageManager.getImage(Images.rulerOver)
         .draw2(
            oSB,
            this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.rulerOver).getWidth() / 2 + iTranslateX,
            this.getPosY() + iTranslateY,
            this.getWidth(),
            Math.min(this.getHeight(), ImageManager.getImage(Images.rulerOver).getHeight())
         );
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public void setVisible(boolean visible) {
      super.setVisible(visible);
      InGame_Court.lTime = CFG.currentTimeMillis;
      InGame_Court.lTime2 = CFG.currentTimeMillis;
      if (!visible) {
         Game.keyboard.hideKeyboard();
      }
   }

   @Override
   public void onHovered() {
      super.onHovered();
      Game.menuManager.setOrderOfMenu_InGameCourt();
   }

   @Override
   public void actionCloseMenu() {
      super.actionCloseMenu();
      Game.keyboard.hideKeyboard();
   }
}
