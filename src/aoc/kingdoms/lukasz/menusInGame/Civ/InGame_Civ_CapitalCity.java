package aoc.kingdoms.lukasz.menusInGame.Civ;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.civilization.CivilizationRanking;
import aoc.kingdoms.lukasz.map.province.ProvinceBorderManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Bonuses_Style;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonusFlag;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG_Center;
import aoc.kingdoms.lukasz.menu_element.pieChart.PieChart_Data;
import aoc.kingdoms.lukasz.menu_element.pieChart.PieChart_Value;
import aoc.kingdoms.lukasz.menu_element.pieChart.PieChart_WithStatsFlags;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID_FlagCiv;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_TitleBlueSort;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_CourtOptions2;
import aoc.kingdoms.lukasz.menusInGame.Province.InGame_ProvinceInfo;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_Civ_CapitalCity extends Menu {
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static long lTime2 = 0L;
   public static int iSortID = 4;

   public InGame_Civ_CapitalCity() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = Images.boxTitleBORDERWIDTH + CFG.PADDING * 2;
      int menuWidth = ImageManager.getImage(Images.insideTop500).getWidth();
      int menuX = InGame_CourtOptions2.getOtherMenuPosX_2();
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title1Red).getHeight();
      int buttonYPadding = CFG.PADDING * 2;
      int buttonX = CFG.PADDING + Images.boxTitleBORDERWIDTH;
      int buttonH = CFG.BUTTON_HEIGHT3;
      if (Game.iActiveProvince >= 0 && Game.getProvince(Game.iActiveProvince).getCivID() > 0) {
         InGame_Civ.iActiveCivID = Game.getProvince(Game.iActiveProvince).getCivID();
      } else if (InGame_Civ.iActiveCivID <= 0) {
         InGame_Civ.iActiveCivID = Game.player.iCivID;
      }

      int r0W = (int)((menuWidth - Images.boxTitleBORDERWIDTH * 2) * 0.4F);
      int r1W = (int)((menuWidth - Images.boxTitleBORDERWIDTH * 2) * 0.3F);
      int maxLevel = 0;
      buttonX = paddingLeft + CFG.PADDING;
      List<Integer> tCivs = new ArrayList<>();
      List<Long> tCivsPopulation = new ArrayList<>();
      PieChart_Data nPieChartData = new PieChart_Data();

      for (int i = 0; i < Game.getCivsSize(); i++) {
         if (Game.getCiv(i).getNumOfProvinces() > 0) {
            tCivs.add(i);
            tCivsPopulation.add((long)Game.getProvince(Game.getCiv(i).getCapitalProvinceID()).getPopulationTotal());
            nPieChartData.addPieChartValues(new PieChart_Value(i, Game.getProvince(Game.getCiv(i).getCapitalProvinceID()).getPopulationTotal()));
            if (Game.getCiv(i).getCapitalLevel() > maxLevel) {
               maxLevel = Game.getCiv(i).getCapitalLevel();
            }
         }
      }

      int pieDim = buttonH * 2 + CFG.PADDING;
      menuElements.add(
         new PieChart_WithStatsFlags(buttonX, buttonYPadding + CFG.PADDING, pieDim - CFG.PADDING * 2, pieDim - CFG.PADDING * 2, nPieChartData, null) {
            @Override
            protected void drawPieChart(
               SpriteBatch oSB,
               int iTranslateX,
               int iTranslateY,
               boolean isActive,
               boolean scrollableY,
               int nPosX,
               int nPosY,
               int nWidth,
               int nHeight,
               int nWidth_LEFT
            ) {
               oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.5F));
               Renderer.drawBox(
                  oSB,
                  Images.statsRectBG,
                  this.getPosX() - CFG.PADDING + iTranslateX,
                  this.getPosY() - CFG.PADDING + iTranslateY,
                  this.getWidth() + CFG.PADDING * 2,
                  this.getHeight() + CFG.PADDING * 2,
                  1.0F
               );
               oSB.setColor(Color.WHITE);
               super.drawPieChart(oSB, iTranslateX, iTranslateY, isActive, scrollableY, nPosX, nPosY, nWidth, nHeight, nWidth_LEFT);
            }

            @Override
            protected float animationPerc() {
               return Math.min(1.0F, (float)(CFG.currentTimeMillis - InGame_ProvinceInfo.lTime) / 150.0F);
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(
                  new MenuElement_HoverElement_Type_TextTitle_BG_Center(Game.lang.get("Capital") + ": " + Game.lang.get("Population"), Colors.HOVER_GOLD)
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();

               for (int ix = 0; ix < this.pieChartData.getPieChartValuesSize() && ix < 10; ix++) {
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonusFlag(
                        Game.getCiv(this.pieChartData.getCivID(ix)).getCivName() + ": ",
                        "" + CFG.getPrecision2(this.pieChartData.getPieChartValue(ix).getPercentage(), 5) + "%",
                        this.pieChartData.getCivID(ix),
                        CFG.FONT_REGULAR_SMALL,
                        CFG.FONT_BOLD_SMALL,
                        Colors.HOVER_LEFT,
                        Colors.HOVER_GOLD
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
               }

               this.menuElementHover = new MenuElement_Hover(nElements);
            }

            @Override
            public void actionElement() {
            }
         }
      );
      buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING * 2;
      int c0W = menuWidth - paddingLeft - buttonX;
      menuElements.add(
         new ButtonStatsRectIMG_Bonuses_Style(
            Game.lang.get("Civilizations") + ": ",
            "" + tCivs.size(),
            Images.council,
            buttonX,
            buttonYPadding,
            c0W,
            buttonH,
            ImageManager.getImage(Images.battleWidth).getWidth()
         ) {
            @Override
            public void actionElement() {
               Game.menuManager.rebuildInGame_Civ();
            }

            @Override
            public Color getColorBonus() {
               return Colors.HOVER_GOLD;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("OpenTheDiplomacyView"), Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.diplomacy, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     Game.lang.get("Civilizations") + ": ",
                     this.sText2,
                     Images.council,
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
      int buttonY = buttonYPadding + menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new ButtonStatsRectIMG_Bonuses_Style(
            Game.lang.get("Max") + ": ", "" + maxLevel, Images.capital, buttonX, buttonY, c0W, buttonH, ImageManager.getImage(Images.battleWidth).getWidth()
         ) {
            @Override
            public void actionElement() {
               Game.menuManager.rebuildInGame_Civ();
            }

            @Override
            public Color getColorBonus() {
               return Colors.HOVER_GOLD;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("OpenTheDiplomacyView"), Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.diplomacy, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     Game.lang.get("MaximumLevelOfCapitalCity") + ": ",
                     this.sText2,
                     Images.capital,
                     CFG.FONT_REGULAR_SMALL,
                     CFG.FONT_BOLD_SMALL,
                     Colors.HOVER_LEFT,
                     Colors.COLOR_POPULATION
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      buttonX = Images.boxTitleBORDERWIDTH;
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
               if (InGame_Civ_CapitalCity.iSortID == 0) {
                  InGame_Civ_CapitalCity.iSortID = 1;
               } else {
                  InGame_Civ_CapitalCity.iSortID = 0;
               }

               Game.menuManager.rebuildInGame_Civ_CapitalCity();
               InGame_Civ_CapitalCity.lTime = 0L;
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
            Game.lang.get("Capital") + ": " + Game.lang.get("Population"),
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
               if (InGame_Civ_CapitalCity.iSortID == 2) {
                  InGame_Civ_CapitalCity.iSortID = 3;
               } else {
                  InGame_Civ_CapitalCity.iSortID = 2;
               }

               Game.menuManager.rebuildInGame_Civ_CapitalCity();
               InGame_Civ_CapitalCity.lTime = 0L;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("SortBy") + ": "));
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Capital") + ": " + Game.lang.get("Population"), Colors.HOVER_GOLD));
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
            Game.lang.get("CapitalCity") + ": " + Game.lang.get("Level"),
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
               if (InGame_Civ_CapitalCity.iSortID == 4) {
                  InGame_Civ_CapitalCity.iSortID = 5;
               } else {
                  InGame_Civ_CapitalCity.iSortID = 4;
               }

               Game.menuManager.rebuildInGame_Civ_CapitalCity();
               InGame_Civ_CapitalCity.lTime = 0L;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("SortBy") + ": "));
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("CapitalCity") + ": " + Game.lang.get("Level"), Colors.HOVER_GOLD));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      r0W = (int)((menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 5) * 0.4F);
      r1W = (int)((menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 5) * 0.3F);

      while (!tCivs.isEmpty()) {
         int toAddID = 0;
         if (iSortID == 0) {
            for (int o = 1; o < tCivs.size(); o++) {
               if (CFG.compareAlphabetic_TwoString(Game.getCiv(tCivs.get(toAddID)).getCivName(), Game.getCiv(tCivs.get(o)).getCivName())) {
                  toAddID = o;
               }
            }
         } else if (iSortID == 1) {
            for (int ox = 1; ox < tCivs.size(); ox++) {
               if (CFG.compareAlphabetic_TwoString(Game.getCiv(tCivs.get(ox)).getCivName(), Game.getCiv(tCivs.get(toAddID)).getCivName())) {
                  toAddID = ox;
               }
            }
         } else if (iSortID == 2) {
            for (int oxx = 1; oxx < tCivs.size(); oxx++) {
               if (tCivsPopulation.get(toAddID) < tCivsPopulation.get(oxx)) {
                  toAddID = oxx;
               }
            }
         } else if (iSortID == 3) {
            for (int oxxx = 1; oxxx < tCivs.size(); oxxx++) {
               if (tCivsPopulation.get(toAddID) > tCivsPopulation.get(oxxx)) {
                  toAddID = oxxx;
               }
            }
         } else if (iSortID == 4) {
            for (int oxxxx = 1; oxxxx < tCivs.size(); oxxxx++) {
               if (Game.getCiv(tCivs.get(toAddID)).getCapitalLevel() < Game.getCiv(tCivs.get(oxxxx)).getCapitalLevel()) {
                  toAddID = oxxxx;
               }
            }
         } else if (iSortID == 5) {
            for (int oxxxxx = 1; oxxxxx < tCivs.size(); oxxxxx++) {
               if (Game.getCiv(tCivs.get(toAddID)).getCapitalLevel() > Game.getCiv(tCivs.get(oxxxxx)).getCapitalLevel()) {
                  toAddID = oxxxxx;
               }
            }
         }

         menuElements.add(
            new Text_StaticBG_ID_FlagCiv(
               Game.getCiv(tCivs.get(toAddID)).getCivName(), CFG.FONT_REGULAR_SMALL, CFG.PADDING * 2, paddingLeft, buttonY, r0W, buttonH, tCivs.get(toAddID)
            ) {
               @Override
               public void actionElement() {
                  if (Game.getCiv(this.getCurrent()).getCapitalProvinceID() >= 0
                     && Game.getProvince(Game.getCiv(this.getCurrent()).getCapitalProvinceID()).getCivID() == this.getCurrent()) {
                     if (Game.iActiveProvince >= 0 && Game.getProvince(Game.iActiveProvince).getCivID() == this.getCurrent()) {
                        Game.menuManager.rebuildInGame_Civ();
                     } else {
                        Game.mapCoords.centerToProvinceID(Game.getCiv(this.getCurrent()).getCapitalProvinceID());
                        Game.setActiveProvinceID(Game.getCiv(this.getCurrent()).getCapitalProvinceID());
                        ProvinceBorderManager.action.setProvinceID(Game.iActiveProvince);
                     }
                  }
               }

               @Override
               public void buildElementHover() {
                  this.menuElementHover = CivilizationRanking.getHover_CivilizationRanking(this.getCurrent(), false, false);
               }
            }
         );
         buttonX = paddingLeft + menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         menuElements.add(
            new Text_StaticBG_ID(
               "" + CFG.getNumberWithSpaces("" + tCivsPopulation.get(toAddID)), CFG.FONT_REGULAR_SMALL, -1, buttonX, buttonY, r1W, buttonH, tCivs.get(toAddID)
            ) {
               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonusFlag(
                        Game.getCiv(this.id).getCivName(), "", this.id, CFG.FONT_BOLD_SMALL, CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD, Colors.HOVER_GOLD
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        Game.lang.get("Capital") + ", " + Game.lang.get("Population") + ": ",
                        this.getText(),
                        Images.population,
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
         buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         menuElements.add(
            new Text_StaticBG_ID(
               "" + Game.getCiv(tCivs.get(toAddID)).getCapitalLevel() + " / " + Game.getCapital_MaxLvl(tCivs.get(toAddID)),
               CFG.FONT_REGULAR_SMALL,
               -1,
               buttonX,
               buttonY,
               r1W,
               buttonH,
               tCivs.get(toAddID)
            ) {
               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonusFlag(
                        Game.getCiv(this.id).getCivName(), "", this.id, CFG.FONT_BOLD_SMALL, CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD, Colors.HOVER_GOLD
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        Game.lang.get("CapitalCity") + ": ",
                        Game.lang.get("Level") + ": " + this.getText(),
                        Images.capital,
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
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         tCivs.remove(toAddID);
         tCivsPopulation.remove(toAddID);
      }

      int menuHeight = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - CFG.PADDING * 3);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, menuHeight)));
      this.initMenu(new MenuTitleIMG(Game.lang.get("CapitalCity"), false, false, Images.title500) {
         @Override
         public long getTime() {
            return InGame_Civ_CapitalCity.lTime2;
         }
      }, menuX, menuY, menuWidth, menuHeight, menuElements, false, true);
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      if (lTime + 60L >= CFG.currentTimeMillis) {
         iTranslateX = iTranslateX - CFG.BUTTON_WIDTH + (int)(CFG.BUTTON_WIDTH * ((float)(CFG.currentTimeMillis - lTime) / 60.0F));
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
      lTime2 = lTime;
   }

   @Override
   public void actionCloseMenu() {
      super.actionCloseMenu();
      InGame_Civ.actionOnClose();
   }
}
