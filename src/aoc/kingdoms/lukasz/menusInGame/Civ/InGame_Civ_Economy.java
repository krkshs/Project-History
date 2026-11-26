package aoc.kingdoms.lukasz.menusInGame.Civ;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.ResourcesManager;
import aoc.kingdoms.lukasz.map.province.ProvinceBorderManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG_FlagCenter;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Active_Click;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Bonuses_Style;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Resource;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonusResource;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG_Clear;
import aoc.kingdoms.lukasz.menu_element.pieChart.PieChart_Data;
import aoc.kingdoms.lukasz.menu_element.pieChart.PieChart_Value;
import aoc.kingdoms.lukasz.menu_element.pieChart.PieChart_WithStatsResources;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_TitleBlueSort;
import aoc.kingdoms.lukasz.menusInGame.InGame_Ranking;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_CourtOptions2;
import aoc.kingdoms.lukasz.menusInGame.Province.InGame_ProvinceInfo;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_Civ_Economy extends Menu {
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static long lTime2 = 0L;
   public static int iSortID = 2;
   public static boolean goBackToRank = false;

   public InGame_Civ_Economy() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = Images.boxTitleBORDERWIDTH + CFG.PADDING * 2;
      int menuWidth = ImageManager.getImage(Images.insideTop500).getWidth();
      int menuX = InGame_CourtOptions2.getOtherMenuPosX_2();
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title1Red).getHeight();
      int buttonYPadding = CFG.PADDING * 2;
      int buttonX = CFG.PADDING + Images.boxTitleBORDERWIDTH;
      int buttonH = CFG.BUTTON_HEIGHT3;
      if (goBackToRank) {
         if (InGame_Civ.iActiveCivID <= 0) {
            InGame_Civ.iActiveCivID = Game.player.iCivID;
         }
      } else if (Game.iActiveProvince >= 0 && Game.getProvince(Game.iActiveProvince).getCivID() > 0) {
         InGame_Civ.iActiveCivID = Game.getProvince(Game.iActiveProvince).getCivID();
      } else if (InGame_Civ.iActiveCivID <= 0) {
         InGame_Civ.iActiveCivID = Game.player.iCivID;
      }

      int r0W = (int)((menuWidth - Images.boxTitleBORDERWIDTH * 2) * 0.2F);
      int r1W = (int)((menuWidth - Images.boxTitleBORDERWIDTH * 2) * 0.2F);
      int c0W = (int)((menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 4) / 3.0F);
      List<Integer> lResources = new ArrayList<>();
      List<Float> lProduction = new ArrayList<>();

      for (int i = 0; i < Game.getCiv(InGame_Civ.iActiveCivID).getNumOfProvinces(); i++) {
         if (Game.getProvince(Game.getCiv(InGame_Civ.iActiveCivID).getProvinceID(i)).getResourceID() >= 0) {
            boolean tAdded = false;

            for (int k = lResources.size() - 1; k >= 0; k--) {
               if (lResources.get(k) == Game.getProvince(Game.getCiv(InGame_Civ.iActiveCivID).getProvinceID(i)).getResourceID()) {
                  tAdded = true;
                  lProduction.set(k, lProduction.get(k) + ResourcesManager.getProducedGoods(Game.getCiv(InGame_Civ.iActiveCivID).getProvinceID(i)));
                  break;
               }
            }

            if (!tAdded) {
               lResources.add(Game.getProvince(Game.getCiv(InGame_Civ.iActiveCivID).getProvinceID(i)).getResourceID());
               lProduction.add(ResourcesManager.getProducedGoods(Game.getCiv(InGame_Civ.iActiveCivID).getProvinceID(i)));
            }
         }
      }

      PieChart_Data nPieChartData = new PieChart_Data() {
         @Override
         public float getPieChartValue_ColorR(int i) {
            return ResourcesManager.lResources.get(this.getPieChartValue(i).getDataID()).Color[0];
         }

         @Override
         public float getPieChartValue_ColorG(int i) {
            return ResourcesManager.lResources.get(this.getPieChartValue(i).getDataID()).Color[1];
         }

         @Override
         public float getPieChartValue_ColorB(int i) {
            return ResourcesManager.lResources.get(this.getPieChartValue(i).getDataID()).Color[2];
         }
      };

      for (int ix = lResources.size() - 1; ix >= 0; ix--) {
         if (lProduction.get(ix) > 0.001F) {
            nPieChartData.addPieChartValues(new PieChart_Value(lResources.get(ix), lProduction.get(ix)));
         }
      }

      int pieDim = CFG.BUTTON_HEIGHT3 * 3 + CFG.PADDING * 2;
      menuElements.add(
         new PieChart_WithStatsResources(buttonX, buttonYPadding + CFG.PADDING, pieDim - CFG.PADDING * 2, pieDim - CFG.PADDING * 2, nPieChartData, null) {
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
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.getCiv(InGame_Civ.iActiveCivID).getCivName() + ": ", Colors.HOVER_LEFT));
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG_Clear(Game.lang.get("ProducedGoods"), Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.goods, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();

               for (int ixx = 0; ixx < this.pieChartData.getPieChartValuesSize() && ixx < 10; ixx++) {
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonusResource(
                        ResourcesManager.getResourceName(this.pieChartData.getCivID(ixx)) + ": ",
                        CFG.getPrecision2(this.pieChartData.getPieChartValue(ixx).getPercentage(), 10) + "%",
                        this.pieChartData.getCivID(ixx),
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
      int nX = menuElements.get(menuElements.size() - 1).getPosX() + menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING * 2;
      int nW = menuWidth - paddingLeft - nX;
      int buttonY = buttonYPadding + menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING * 3;
      float fGold = Game.getCiv(InGame_Civ.iActiveCivID).getEconomyTotal();
      menuElements.add(
         new ButtonStatsRectIMG_Bonuses_Style(
            Game.lang.get("Economy") + ": ",
            "" + CFG.getPrecision2(fGold, fGold < 10.0F ? 100 : (fGold < 100.0F ? 10 : 1)),
            Game_Calendar.IMG_ECONOMY,
            nX,
            buttonYPadding,
            nW,
            CFG.BUTTON_HEIGHT3,
            ImageManager.getImage(Game_Calendar.IMG_ECONOMY).getWidth() + CFG.PADDING * 2
         ) {
            @Override
            public void actionElement() {
               if (InGame_Civ_Economy.goBackToRank) {
                  InGame_Ranking.sSearch = "";
                  Game.menuManager.rebuildInGame_CurrentSituation_Ranking();
               } else {
                  Game.menuManager.rebuildInGame_Civ();
               }
            }

            @Override
            public Color getColorBonus() {
               return Colors.COLOR_TEXT_ECONOMY;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               if (InGame_Civ_Economy.goBackToRank) {
                  nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Ranking"), Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.rank, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
               } else {
                  nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("OpenTheDiplomacyView"), Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.diplomacy, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
               }

               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     Game.lang.get("Provinces") + ": ",
                     CFG.getNumberWithSpaces("" + Game.getCiv(InGame_Civ.iActiveCivID).getNumOfProvinces()),
                     Images.provinces,
                     CFG.FONT_REGULAR_SMALL,
                     CFG.FONT_BOLD_SMALL,
                     Colors.HOVER_LEFT,
                     Colors.HOVER_GOLD
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     Game.lang.get("Economy") + ": ",
                     CFG.getPrecision2(Game.getCiv(InGame_Civ.iActiveCivID).getEconomyTotal(), 100),
                     Game_Calendar.IMG_ECONOMY,
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
      int var39 = buttonYPadding + menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      fGold = Game.getCiv(InGame_Civ.iActiveCivID).getTotalIncomeFromEconomy();
      menuElements.add(
         new ButtonStatsRectIMG_Active_Click(
            "" + CFG.getPrecision2(fGold, fGold < 10.0F ? 100 : (fGold < 100.0F ? 10 : 1)),
            Images.gold,
            nX,
            var39,
            nW,
            buttonH,
            ImageManager.getImage(Images.gold).getWidth(),
            0
         ) {
            @Override
            public void actionElement() {
               if (InGame_Civ_Economy.goBackToRank) {
                  InGame_Ranking.sSearch = "";
                  Game.menuManager.rebuildInGame_CurrentSituation_Ranking();
               } else {
                  Game.menuManager.rebuildInGame_Civ();
               }
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               if (InGame_Civ_Economy.goBackToRank) {
                  nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Ranking"), Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.rank, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
               } else {
                  nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("OpenTheDiplomacyView"), Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.diplomacy, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
               }

               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     Game.lang.get("Provinces") + ": ",
                     CFG.getNumberWithSpaces("" + Game.getCiv(InGame_Civ.iActiveCivID).getNumOfProvinces()),
                     Images.provinces,
                     CFG.FONT_REGULAR_SMALL,
                     CFG.FONT_BOLD_SMALL,
                     Colors.HOVER_LEFT,
                     Colors.HOVER_GOLD
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     Game.lang.get("MonthlyIncomeEconomy") + ": ",
                     CFG.getPrecision2(Game.getCiv(InGame_Civ.iActiveCivID).getTotalIncomeFromEconomy(), 100),
                     Images.gold,
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
      var39 += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      fGold = Game.getCiv(InGame_Civ.iActiveCivID).getTotalIncomeFromProduction();
      menuElements.add(
         new ButtonStatsRectIMG_Active_Click(
            "" + CFG.getPrecision2(fGold, fGold < 10.0F ? 100 : (fGold < 100.0F ? 10 : 1)),
            Images.goods,
            nX,
            var39,
            nW,
            buttonH,
            ImageManager.getImage(Images.goods).getWidth(),
            0
         ) {
            @Override
            public void actionElement() {
               if (InGame_Civ_Economy.goBackToRank) {
                  InGame_Ranking.sSearch = "";
                  Game.menuManager.rebuildInGame_CurrentSituation_Ranking();
               } else {
                  Game.menuManager.rebuildInGame_Civ();
               }
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               if (InGame_Civ_Economy.goBackToRank) {
                  nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Ranking"), Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.rank, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
               } else {
                  nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("OpenTheDiplomacyView"), Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.diplomacy, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
               }

               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     Game.lang.get("IncomeProduction") + ": ",
                     CFG.getPrecision2(Game.getCiv(InGame_Civ.iActiveCivID).getTotalIncomeFromProduction(), 100),
                     Images.gold,
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
               if (InGame_Civ_Economy.iSortID == 0) {
                  InGame_Civ_Economy.iSortID = 1;
               } else {
                  InGame_Civ_Economy.iSortID = 0;
               }

               Game.menuManager.rebuildInGame_Civ_Economy();
               InGame_Civ_Economy.lTime = 0L;
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
            Game.lang.get("Economy"),
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
               if (InGame_Civ_Economy.iSortID == 2) {
                  InGame_Civ_Economy.iSortID = 3;
               } else {
                  InGame_Civ_Economy.iSortID = 2;
               }

               Game.menuManager.rebuildInGame_Civ_Economy();
               InGame_Civ_Economy.lTime = 0L;
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
            iSortID == 4 || iSortID == 5,
            iSortID == 5,
            Game.lang.get("Income"),
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
               if (InGame_Civ_Economy.iSortID == 4) {
                  InGame_Civ_Economy.iSortID = 5;
               } else {
                  InGame_Civ_Economy.iSortID = 4;
               }

               Game.menuManager.rebuildInGame_Civ_Economy();
               InGame_Civ_Economy.lTime = 0L;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("SortBy") + ": "));
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("MonthlyIncomeEconomy"), Colors.HOVER_GOLD));
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
               if (InGame_Civ_Economy.iSortID == 6) {
                  InGame_Civ_Economy.iSortID = 7;
               } else {
                  InGame_Civ_Economy.iSortID = 6;
               }

               Game.menuManager.rebuildInGame_Civ_Economy();
               InGame_Civ_Economy.lTime = 0L;
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
      menuElements.add(
         new Text_TitleBlueSort(
            iSortID == 8 || iSortID == 9,
            iSortID == 9,
            Game.lang.get("IncomeProduction"),
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
               if (InGame_Civ_Economy.iSortID == 8) {
                  InGame_Civ_Economy.iSortID = 9;
               } else {
                  InGame_Civ_Economy.iSortID = 8;
               }

               Game.menuManager.rebuildInGame_Civ_Economy();
               InGame_Civ_Economy.lTime = 0L;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("SortBy") + ": "));
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("IncomeProduction"), Colors.HOVER_GOLD));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      r0W = (int)((menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 6) * 0.2F);
      r1W = (int)((menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 6) * 0.2F);
      List<Integer> tProvinces = new ArrayList<>();

      for (int ixx = 0; ixx < Game.getCiv(InGame_Civ.iActiveCivID).getNumOfProvinces(); ixx++) {
         if (!Game.getProvince(Game.getCiv(InGame_Civ.iActiveCivID).getProvinceID(ixx)).isOccupied()) {
            tProvinces.add(Game.getCiv(InGame_Civ.iActiveCivID).getProvinceID(ixx));
         }
      }

      while (tProvinces.size() > 0) {
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
               if (Game.getProvince(tProvinces.get(toAddID)).getEconomyWithBonuses() < Game.getProvince(tProvinces.get(oxx)).getEconomyWithBonuses()) {
                  toAddID = oxx;
               }
            }
         } else if (iSortID == 3) {
            for (int oxxx = 1; oxxx < tProvinces.size(); oxxx++) {
               if (Game.getProvince(tProvinces.get(toAddID)).getEconomyWithBonuses() > Game.getProvince(tProvinces.get(oxxx)).getEconomyWithBonuses()) {
                  toAddID = oxxx;
               }
            }
         } else if (iSortID == 4) {
            for (int oxxxx = 1; oxxxx < tProvinces.size(); oxxxx++) {
               if (Game.getIncomeFromEconomy(tProvinces.get(toAddID)) < Game.getIncomeFromEconomy(tProvinces.get(oxxxx))) {
                  toAddID = oxxxx;
               }
            }
         } else if (iSortID == 5) {
            for (int oxxxxx = 1; oxxxxx < tProvinces.size(); oxxxxx++) {
               if (Game.getIncomeFromEconomy(tProvinces.get(toAddID)) > Game.getIncomeFromEconomy(tProvinces.get(oxxxxx))) {
                  toAddID = oxxxxx;
               }
            }
         } else if (iSortID == 6) {
            for (int oxxxxxx = 1; oxxxxxx < tProvinces.size(); oxxxxxx++) {
               if (CFG.compareAlphabetic_TwoString(
                  ResourcesManager.getResourceName(Game.getProvince(tProvinces.get(toAddID)).getResourceID()),
                  ResourcesManager.getResourceName(Game.getProvince(tProvinces.get(oxxxxxx)).getResourceID())
               )) {
                  toAddID = oxxxxxx;
               }
            }
         } else if (iSortID == 7) {
            for (int oxxxxxxx = 1; oxxxxxxx < tProvinces.size(); oxxxxxxx++) {
               if (CFG.compareAlphabetic_TwoString(
                  ResourcesManager.getResourceName(Game.getProvince(tProvinces.get(oxxxxxxx)).getResourceID()),
                  ResourcesManager.getResourceName(Game.getProvince(tProvinces.get(toAddID)).getResourceID())
               )) {
                  toAddID = oxxxxxxx;
               }
            }
         } else if (iSortID == 8) {
            for (int oxxxxxxxx = 1; oxxxxxxxx < tProvinces.size(); oxxxxxxxx++) {
               if (ResourcesManager.getMonthlyIncome(tProvinces.get(toAddID)) < ResourcesManager.getMonthlyIncome(tProvinces.get(oxxxxxxxx))) {
                  toAddID = oxxxxxxxx;
               }
            }
         } else if (iSortID == 9) {
            for (int oxxxxxxxxx = 1; oxxxxxxxxx < tProvinces.size(); oxxxxxxxxx++) {
               if (ResourcesManager.getMonthlyIncome(tProvinces.get(toAddID)) > ResourcesManager.getMonthlyIncome(tProvinces.get(oxxxxxxxxx))) {
                  toAddID = oxxxxxxxxx;
               }
            }
         }

         menuElements.add(
            new Text_StaticBG_ID(
               Game.getProvince(tProvinces.get(toAddID)).getProvinceName(),
               CFG.FONT_REGULAR_SMALL,
               CFG.PADDING * 2,
               paddingLeft,
               buttonY,
               r0W,
               buttonH,
               tProvinces.get(toAddID)
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
            }
         );
         buttonX = paddingLeft + menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         fGold = Game.getProvince(tProvinces.get(toAddID)).getEconomyWithBonuses();
         menuElements.add(
            new Text_StaticBG_ID(
               "" + CFG.getPrecision2(fGold, fGold < 10.0F ? 100 : (fGold < 100.0F ? 10 : 1)),
               CFG.FONT_REGULAR_SMALL,
               -1,
               buttonX,
               buttonY,
               r0W,
               buttonH,
               tProvinces.get(toAddID)
            ) {
               @Override
               public void buildElementHover() {
                  this.menuElementHover = InGame_ProvinceInfo.getHoverEconomy(this.getCurrent(), false, false);
               }
            }
         );
         buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         fGold = Game.getIncomeFromEconomy(tProvinces.get(toAddID));
         menuElements.add(
            new Text_StaticBG_ID(
               "" + CFG.getPrecision2(fGold, fGold < 10.0F ? 100 : (fGold < 100.0F ? 10 : 1)),
               CFG.FONT_BOLD_SMALL,
               -1,
               buttonX,
               buttonY,
               r1W,
               buttonH,
               tProvinces.get(toAddID)
            ) {
               @Override
               public void buildElementHover() {
                  this.menuElementHover = InGame_ProvinceInfo.getHoverEconomy(this.getCurrent(), false, false);
               }

               @Override
               protected Color getColor(boolean isActive) {
                  if (isActive) {
                     return Colors.COLOR_TEXT_MODIFIER_POSITIVE_ACTIVE;
                  } else {
                     return this.getIsHovered() ? Colors.COLOR_TEXT_MODIFIER_POSITIVE_HOVER : Colors.COLOR_TEXT_MODIFIER_POSITIVE;
                  }
               }
            }
         );
         buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         if (Game.getProvince(tProvinces.get(toAddID)).getResourceID() >= 0) {
            menuElements.add(
               new ButtonStatsRectIMG_Resource(
                  ResourcesManager.getResourceName(Game.getProvince(tProvinces.get(toAddID)).getResourceID()),
                  Game.getProvince(tProvinces.get(toAddID)).getResourceID(),
                  buttonX,
                  buttonY,
                  r0W,
                  buttonH,
                  ImageManager.getImage(Images.gold).getWidth(),
                  tProvinces.get(toAddID)
               ) {
                  @Override
                  public void buildElementHover() {
                     this.menuElementHover = InGame_ProvinceInfo.getHoverResource(this.getCurrent(), this.getValue1(), false);
                  }
               }
            );
         } else {
            menuElements.add(new Text_StaticBG(Game.lang.get("None"), CFG.FONT_REGULAR_SMALL, -1, buttonX, buttonY, r0W, buttonH));
         }

         buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         fGold = ResourcesManager.getMonthlyIncome(tProvinces.get(toAddID));
         menuElements.add(
            new Text_StaticBG_ID(
               "" + CFG.getPrecision2(fGold, fGold < 10.0F ? 100 : (fGold < 100.0F ? 10 : 1)),
               CFG.FONT_BOLD_SMALL,
               -1,
               buttonX,
               buttonY,
               r1W,
               buttonH,
               tProvinces.get(toAddID)
            ) {
               @Override
               public void buildElementHover() {
                  this.menuElementHover = InGame_ProvinceInfo.getHoverResource(this.getCurrent(), Game.getProvince(this.getCurrent()).getResourceID(), false);
               }

               @Override
               protected Color getColor(boolean isActive) {
                  if (isActive) {
                     return Colors.COLOR_TEXT_MODIFIER_POSITIVE_ACTIVE;
                  } else {
                     return this.getIsHovered() ? Colors.COLOR_TEXT_MODIFIER_POSITIVE_HOVER : Colors.COLOR_TEXT_MODIFIER_POSITIVE;
                  }
               }
            }
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         tProvinces.remove(toAddID);
      }

      int menuHeight = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - CFG.PADDING * 3);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, menuHeight)));
      this.initMenu(new MenuTitleIMG_FlagCenter(Game.getCiv(InGame_Civ.iActiveCivID).getCivName(), Game.lang.get("Economy"), false, false, Images.title500) {
         @Override
         public int getFlagCivID() {
            return InGame_Civ.iActiveCivID;
         }

         @Override
         public long getTime() {
            return InGame_Civ_Economy.lTime2;
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
