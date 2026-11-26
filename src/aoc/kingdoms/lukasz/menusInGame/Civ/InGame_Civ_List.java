package aoc.kingdoms.lukasz.menusInGame.Civ;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.ResourcesManager;
import aoc.kingdoms.lukasz.map.civilization.CivilizationRanking;
import aoc.kingdoms.lukasz.map.province.ProvinceBorderManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG_DoubleText;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonResource2;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Bonuses_Style;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Rank_Ranking;
import aoc.kingdoms.lukasz.menu_element.graph.Graph_Vertical;
import aoc.kingdoms.lukasz.menu_element.graph.Graph_Vertical_Data_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonusFlag;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonusResource;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_FlagCiv_Title;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG_Center;
import aoc.kingdoms.lukasz.menu_element.pieChart.PieChart_Data;
import aoc.kingdoms.lukasz.menu_element.pieChart.PieChart_Value;
import aoc.kingdoms.lukasz.menu_element.pieChart.PieChart_WithStatsFlags;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID_FlagCiv_Religion;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_TitleBlueSort;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_CourtOptions2;
import aoc.kingdoms.lukasz.menusInGame.Goods.InGame_GoodsMarket;
import aoc.kingdoms.lukasz.menusInGame.Province.InGame_ProvinceInfo;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_Civ_List extends Menu {
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static long lTime2 = 0L;
   public static List<Integer> civsList = new ArrayList<>();
   public static String civsListTitle = "";
   public static String civsListTitle2 = "";
   public static int iSortID = 0;
   public static int modeID = 0;

   public static final void addCiv(int civID) {
      for (int i = civsList.size() - 1; i >= 0; i--) {
         if (civsList.get(i) == civID) {
            return;
         }
      }

      civsList.add(civID);
   }

   public InGame_Civ_List() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = Images.boxTitleBORDERWIDTH + CFG.PADDING * 2;
      int menuWidth = ImageManager.getImage(Images.insideTop600).getWidth();
      int menuX = InGame_CourtOptions2.getOtherMenuPosX_2();
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title1Red).getHeight();
      int buttonY = CFG.PADDING;
      int buttonH = CFG.BUTTON_HEIGHT3 + CFG.TEXT_HEIGHT + CFG.PADDING * 3;
      int r0W = (int)((menuWidth - Images.boxTitleBORDERWIDTH * 2) * 0.4F);
      int r1W = (int)((menuWidth - Images.boxTitleBORDERWIDTH * 2) * 0.15F);
      InGame_Civ_Compare.civLeft_Rank = -1;
      InGame_Civ_Compare.civRight_Rank = -1;
      buttonY += buttonH + CFG.PADDING;
      int buttonX = paddingLeft + CFG.PADDING;
      PieChart_Data nPieChartData = new PieChart_Data();
      if (modeID == 0) {
         for (int i = civsList.size() - 1; i >= 0; i--) {
            nPieChartData.addPieChartValues(new PieChart_Value(civsList.get(i), (int)Game.getCiv(civsList.get(i)).getPopulationTotal()));
         }
      } else if (modeID == 1) {
         for (int i = civsList.size() - 1; i >= 0; i--) {
            nPieChartData.addPieChartValues(new PieChart_Value(civsList.get(i), (int)Game.getCiv(civsList.get(i)).getEconomyTotal()));
         }
      } else if (modeID == 2) {
         for (int i = civsList.size() - 1; i >= 0; i--) {
            nPieChartData.addPieChartValues(new PieChart_Value(civsList.get(i), Game.getCiv(civsList.get(i)).getNumOfProvinces()));
         }
      } else if (modeID == 3) {
         for (int i = civsList.size() - 1; i >= 0; i--) {
            nPieChartData.addPieChartValues(new PieChart_Value(civsList.get(i), (int)Game.getCiv(civsList.get(i)).fManpowerMax));
         }
      } else if (modeID == 4) {
         for (int i = civsList.size() - 1; i >= 0; i--) {
            nPieChartData.addPieChartValues(new PieChart_Value(civsList.get(i), Game.getCiv(civsList.get(i)).iRegimentsLimit));
         }
      } else if (modeID == 5) {
         for (int i = civsList.size() - 1; i >= 0; i--) {
            nPieChartData.addPieChartValues(new PieChart_Value(civsList.get(i), (int)Game.getCiv(civsList.get(i)).iCivRankScore));
         }
      }

      int pieDim = CFG.BUTTON_HEIGHT3 * 3 + CFG.PADDING * 2;
      menuElements.add(
         new PieChart_WithStatsFlags(buttonX, buttonY + CFG.PADDING, pieDim - CFG.PADDING * 2, pieDim - CFG.PADDING * 2, nPieChartData, null) {
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
               if (InGame_Civ_List.modeID == 0) {
                  nData.add(new MenuElement_HoverElement_Type_TextTitle_BG_Center(Game.lang.get("Population"), Colors.HOVER_GOLD));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();

                  for (int i = 0; i < this.pieChartData.getPieChartValuesSize() && i < 10; i++) {
                     nData.add(
                        new MenuElement_HoverElement_Type_Button_TextBonusFlag(
                           Game.getCiv(this.pieChartData.getCivID(i)).getCivName() + ": ",
                           CFG.getNumberWithSpaces("" + CFG.getPrecision2(this.pieChartData.getPieChartValue(i).getValue(), 1)),
                           this.pieChartData.getCivID(i),
                           CFG.FONT_REGULAR_SMALL,
                           CFG.FONT_BOLD_SMALL,
                           Colors.HOVER_LEFT,
                           Colors.COLOR_POPULATION
                        )
                     );
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                  }
               } else {
                  if (InGame_Civ_List.modeID == 1) {
                     nData.add(new MenuElement_HoverElement_Type_TextTitle_BG_Center(Game.lang.get("Economy"), Colors.HOVER_GOLD));
                  } else if (InGame_Civ_List.modeID == 2) {
                     nData.add(new MenuElement_HoverElement_Type_TextTitle_BG_Center(Game.lang.get("Provinces"), Colors.HOVER_GOLD));
                  } else if (InGame_Civ_List.modeID == 3) {
                     nData.add(new MenuElement_HoverElement_Type_TextTitle_BG_Center(Game.lang.get("MaximumManpower"), Colors.HOVER_GOLD));
                  } else if (InGame_Civ_List.modeID == 4) {
                     nData.add(new MenuElement_HoverElement_Type_TextTitle_BG_Center(Game.lang.get("RegimentsLimit"), Colors.HOVER_GOLD));
                  } else if (InGame_Civ_List.modeID == 5) {
                     nData.add(new MenuElement_HoverElement_Type_TextTitle_BG_Center(Game.lang.get("Prestige"), Colors.HOVER_GOLD));
                  }

                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();

                  for (int i = 0; i < this.pieChartData.getPieChartValuesSize() && i < 10; i++) {
                     nData.add(
                        new MenuElement_HoverElement_Type_Button_TextBonusFlag(
                           Game.getCiv(this.pieChartData.getCivID(i)).getCivName() + ": ",
                           CFG.getNumberWithSpaces("" + CFG.getPrecision2(this.pieChartData.getPieChartValue(i).getValue(), 1)),
                           this.pieChartData.getCivID(i),
                           CFG.FONT_REGULAR_SMALL,
                           CFG.FONT_BOLD_SMALL,
                           Colors.HOVER_LEFT,
                           Colors.HOVER_GOLD
                        )
                     );
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                  }
               }

               this.menuElementHover = new MenuElement_Hover(nElements);
            }

            @Override
            public void actionElement() {
               InGame_Civ_List.modeID++;
               if (InGame_Civ_List.modeID > 5) {
                  InGame_Civ_List.modeID = 0;
               }

               Game.menuManager.rebuildInGame_Civ_List();
               InGame_Civ_List.lTime = 0L;
            }
         }
      );
      int tButtonW = menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING * 2;
      menuElements.add(
         new ButtonStatsRectIMG(
            modeID == 0
               ? Game.lang.get("Population")
               : (
                  modeID == 1
                     ? Game.lang.get("Economy")
                     : (
                        modeID == 2
                           ? Game.lang.get("Provinces")
                           : (modeID == 3 ? Game.lang.get("MaximumManpower") : (modeID == 4 ? Game.lang.get("RegimentsLimit") : Game.lang.get("Prestige")))
                     )
               ),
            modeID == 0
               ? Images.population
               : (
                  modeID == 1
                     ? Game_Calendar.IMG_ECONOMY
                     : (modeID == 2 ? Images.provinces : (modeID == 3 ? Game_Calendar.IMG_MANPOWER : (modeID == 4 ? Images.regimentsLimit : Images.rankGold)))
               ),
            buttonX - CFG.PADDING,
            buttonY - CFG.PADDING - buttonH + CFG.BUTTON_HEIGHT3 + CFG.PADDING,
            tButtonW,
            CFG.TEXT_HEIGHT + CFG.PADDING * 2,
            ImageManager.getImage(Images.battleWidth).getWidth()
         )
      );
      menuElements.add(
         new ButtonStatsRectIMG_Bonuses_Style(
            Game.lang.get("Civilizations") + ": ",
            "" + civsList.size(),
            Images.council,
            buttonX - CFG.PADDING,
            buttonY - CFG.PADDING - buttonH,
            tButtonW,
            CFG.BUTTON_HEIGHT3,
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
      buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING * 2;
      Graph_Vertical.graphCivs.clear();

      for (int a = 0; a < civsList.size(); a++) {
         Graph_Vertical.graphCivs.add(civsList.get(a));
      }

      Graph_Vertical graphVertical = new Graph_Vertical(
         Graph_Vertical_Data_Type.CIVS_LIST_PROVINCES,
         Game.lang.get("Civilizations"),
         Game.lang.get("Civilizations"),
         buttonX - CFG.PADDING * 2,
         buttonY - CFG.PADDING - buttonH,
         menuWidth - paddingLeft - (buttonX - CFG.PADDING * 2),
         pieDim + CFG.PADDING * 3 + buttonH,
         true
      ) {};
      menuElements.add(graphVertical);
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() - buttonH;
      buttonH = CFG.BUTTON_HEIGHT3;
      buttonX = Images.boxTitleBORDERWIDTH;
      menuElements.add(
         new Text_TitleBlueSort(
            iSortID == 0 || iSortID == 1,
            iSortID == 1,
            Game.lang.get("Ranking"),
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
               if (InGame_Civ_List.iSortID == 0) {
                  InGame_Civ_List.iSortID = 1;
               } else {
                  InGame_Civ_List.iSortID = 0;
               }

               Game.menuManager.rebuildInGame_Civ_List();
               InGame_Civ_List.lTime = 0L;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("SortBy") + ": "));
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Ranking"), Colors.HOVER_GOLD));
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
               if (InGame_Civ_List.iSortID == 2) {
                  InGame_Civ_List.iSortID = 3;
               } else {
                  InGame_Civ_List.iSortID = 2;
               }

               Game.menuManager.rebuildInGame_Civ_List();
               InGame_Civ_List.lTime = 0L;
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
            iSortID == 4 || iSortID == 5,
            iSortID == 5,
            Game.lang.get("Population"),
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
               if (InGame_Civ_List.iSortID == 4) {
                  InGame_Civ_List.iSortID = 5;
               } else {
                  InGame_Civ_List.iSortID = 4;
               }

               Game.menuManager.rebuildInGame_Civ_List();
               InGame_Civ_List.lTime = 0L;
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
            iSortID == 6 || iSortID == 7,
            iSortID == 7,
            Game.lang.get("Provinces"),
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
               if (InGame_Civ_List.iSortID == 6) {
                  InGame_Civ_List.iSortID = 7;
               } else {
                  InGame_Civ_List.iSortID = 6;
               }

               Game.menuManager.rebuildInGame_Civ_List();
               InGame_Civ_List.lTime = 0L;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("SortBy") + ": "));
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Provinces"), Colors.HOVER_GOLD));
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
            Game.lang.get("Goods"),
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
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      r0W = (int)((menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 6) * 0.4F);
      r1W = (int)((menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 6) * 0.15F);
      List<Integer> tCivs = new ArrayList<>();
      List<Integer> tPopulation = new ArrayList<>();
      int i = 0;

      for (int iSize = civsList.size(); i < iSize; i++) {
         tCivs.add(civsList.get(i));
         tPopulation.add((int)Game.getCiv(civsList.get(i)).getPopulationTotal());
      }

      while (tCivs.size() > 0) {
         i = 0;
         if (iSortID == 0) {
            for (int o = 1; o < tCivs.size(); o++) {
               if (Game.getCiv(tCivs.get(i)).iCivRankPosition > Game.getCiv(tCivs.get(o)).iCivRankPosition) {
                  i = o;
               }
            }
         } else if (iSortID == 1) {
            for (int ox = 1; ox < tCivs.size(); ox++) {
               if (Game.getCiv(tCivs.get(i)).iCivRankPosition < Game.getCiv(tCivs.get(ox)).iCivRankPosition) {
                  i = ox;
               }
            }
         }

         if (iSortID == 2) {
            for (int oxx = 1; oxx < tCivs.size(); oxx++) {
               if (CFG.compareAlphabetic_TwoString(Game.getCiv(tCivs.get(i)).getCivName(), Game.getCiv(tCivs.get(oxx)).getCivName())) {
                  i = oxx;
               }
            }
         } else if (iSortID == 3) {
            for (int oxxx = 1; oxxx < tCivs.size(); oxxx++) {
               if (CFG.compareAlphabetic_TwoString(Game.getCiv(tCivs.get(oxxx)).getCivName(), Game.getCiv(tCivs.get(i)).getCivName())) {
                  i = oxxx;
               }
            }
         } else if (iSortID == 4) {
            for (int oxxxx = 1; oxxxx < tCivs.size(); oxxxx++) {
               if (tPopulation.get(i) < tPopulation.get(oxxxx)) {
                  i = oxxxx;
               }
            }
         } else if (iSortID == 5) {
            for (int oxxxxx = 1; oxxxxx < tCivs.size(); oxxxxx++) {
               if (tPopulation.get(oxxxxx) < tPopulation.get(i)) {
                  i = oxxxxx;
               }
            }
         } else if (iSortID == 6) {
            for (int oxxxxxx = 1; oxxxxxx < tCivs.size(); oxxxxxx++) {
               if (Game.getCiv(tCivs.get(i)).getNumOfProvinces() < Game.getCiv(tCivs.get(oxxxxxx)).getNumOfProvinces()) {
                  i = oxxxxxx;
               }
            }
         } else if (iSortID == 7) {
            for (int oxxxxxxx = 1; oxxxxxxx < tCivs.size(); oxxxxxxx++) {
               if (Game.getCiv(tCivs.get(i)).getNumOfProvinces() > Game.getCiv(tCivs.get(oxxxxxxx)).getNumOfProvinces()) {
                  i = oxxxxxxx;
               }
            }
         }

         menuElements.add(
            new ButtonStatsRectIMG_Rank_Ranking(
               "" + (Game.getCiv(tCivs.get(i)).iCivRankPosition < 0 ? Game.getCivsSize() : Game.getCiv(tCivs.get(i)).iCivRankPosition),
               CivilizationRanking.getCivilizationRanking_IMG_STAR_CIVID(tCivs.get(i)),
               paddingLeft,
               buttonY,
               r1W,
               buttonH,
               ImageManager.getImage(Images.rankGold).getWidth()
            ) {
               int id = 0;

               @Override
               public void setCurrent(int nCurrent) {
                  this.id = nCurrent;
               }

               @Override
               protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
                  super.drawButtonBG(oSB, iTranslateX, iTranslateY, isActive);
                  if (this.id == InGame_Civ_Compare.civLeft_Rank) {
                     oSB.setColor(Colors.COLOR_NOTIFICATION_OVER_GREEN);
                     Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
                     oSB.setColor(Color.WHITE);
                  }
               }

               @Override
               public void actionElement() {
                  if (InGame_Civ_Compare.civLeft_Rank > 0) {
                     if (InGame_Civ_Compare.civLeft == this.id) {
                        Game.menuManager
                           .addToast_Error(
                              Game.lang.get("Compare")
                                 + ": "
                                 + Game.getCiv(InGame_Civ_Compare.civLeft).getCivName()
                                 + " == "
                                 + Game.getCiv(this.id).getCivName()
                           );
                     } else {
                        InGame_Civ_Compare.civRight_Rank = this.id;
                        InGame_Civ_Compare.civLeft = InGame_Civ_Compare.civLeft_Rank;
                        InGame_Civ_Compare.civRight = InGame_Civ_Compare.civRight_Rank;
                        Game.menuManager.rebuildInGame_Civ_Compare();
                     }
                  } else {
                     InGame_Civ_Compare.civLeft_Rank = this.id;
                  }
               }

               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("CompareCivilizations"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.compare, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  if (InGame_Civ_Compare.civLeft_Rank > 0) {
                     nData.add(
                        new MenuElement_HoverElement_Type_Button_TextBonusFlag(
                           Game.lang.get("Compare") + ": ",
                           Game.getCiv(InGame_Civ_Compare.civLeft_Rank).getCivName(),
                           InGame_Civ_Compare.civLeft_Rank,
                           CFG.FONT_REGULAR,
                           CFG.FONT_BOLD,
                           Colors.HOVER_LEFT,
                           Colors.HOVER_GOLD
                        )
                     );
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     nData.add(
                        new MenuElement_HoverElement_Type_Button_TextBonusFlag(
                           Game.lang.get("Compare") + ": ", "--", 0, CFG.FONT_REGULAR, CFG.FONT_BOLD, Colors.HOVER_LEFT, Colors.HOVER_GOLD
                        )
                     );
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                  } else {
                     nData.add(
                        new MenuElement_HoverElement_Type_Button_TextBonusFlag(
                           Game.lang.get("Compare") + ": ", "--", 0, CFG.FONT_REGULAR, CFG.FONT_BOLD, Colors.HOVER_LEFT, Colors.HOVER_GOLD
                        )
                     );
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     nData.add(
                        new MenuElement_HoverElement_Type_Button_TextBonusFlag(
                           Game.lang.get("Compare") + ": ", "--", 0, CFG.FONT_REGULAR, CFG.FONT_BOLD, Colors.HOVER_LEFT, Colors.HOVER_GOLD
                        )
                     );
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                  }

                  nData.add(new MenuElement_HoverElement_Type_Line());
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        Game.lang.get("CivilizationRank") + ": ",
                        this.getText(),
                        this.imageID,
                        CFG.FONT_REGULAR,
                        CFG.FONT_BOLD,
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
         menuElements.get(menuElements.size() - 1).setCurrent(tCivs.get(i));
         buttonX = paddingLeft + menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         menuElements.add(
            new Text_StaticBG_ID_FlagCiv_Religion(
               "" + Game.getCiv(tCivs.get(i)).getCivName(), CFG.FONT_REGULAR_SMALL, CFG.PADDING * 2, buttonX, buttonY, r0W, buttonH, tCivs.get(i)
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
         buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         menuElements.add(
            new Text_StaticBG_ID("" + CFG.getShortNumber(tPopulation.get(i)), CFG.FONT_REGULAR_SMALL, -1, buttonX, buttonY, r1W, buttonH, tCivs.get(i))
         );
         buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         menuElements.add(
            new Text_StaticBG_ID(
               "" + CFG.getNumberWithSpaces("" + Game.getCiv(tCivs.get(i)).getNumOfProvinces()),
               CFG.FONT_REGULAR_SMALL,
               -1,
               buttonX,
               buttonY,
               r1W,
               buttonH,
               tCivs.get(i)
            )
         );
         buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         menuElements.add(
            new ButtonResource2(ResourcesManager.getLargestGoodsProducedByCiv(tCivs.get(i)), buttonX, buttonY, r1W, buttonH) {
               int id;

               @Override
               public void setCurrent(int nCurrent) {
                  this.id = nCurrent;
               }

               @Override
               public void actionElement() {
                  Game.menuManager.setVisibleInGame_Civ(false);
                  InGame_GoodsMarket.iActiveCivID = this.id;
                  Game.menuManager.rebuildInGame_GoodsMarket();
               }

               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_FlagCiv_Title(this.id, Game.lang.get("ProducedGoods")));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  List<Float> goodsProduced = new ArrayList<>();

                  for (int i = 0; i < ResourcesManager.iResourcesSize; i++) {
                     goodsProduced.add(0.0F);
                  }

                  int bestResourceID = -1;

                  for (int i = 0; i < Game.getCiv(this.id).getNumOfProvinces(); i++) {
                     if (Game.getProvince(Game.getCiv(this.id).getProvinceID(i)).getResourceID() >= 0) {
                        goodsProduced.set(
                           Game.getProvince(Game.getCiv(this.id).getProvinceID(i)).getResourceID(),
                           goodsProduced.get(Game.getProvince(Game.getCiv(this.id).getProvinceID(i)).getResourceID())
                              + ResourcesManager.getProducedGoods(Game.getCiv(this.id).getProvinceID(i))
                        );
                        bestResourceID = Game.getProvince(Game.getCiv(this.id).getProvinceID(i)).getResourceID();
                     }
                  }

                  if (bestResourceID >= 0) {
                     int numAdded = 0;

                     while (numAdded++ < 6) {
                        for (int ix = 0; ix < ResourcesManager.iResourcesSize; ix++) {
                           if (goodsProduced.get(bestResourceID) < goodsProduced.get(ix)) {
                              bestResourceID = ix;
                           }
                        }

                        if (!(goodsProduced.get(bestResourceID) > 0.0F)) {
                           break;
                        }

                        nData.add(
                           new MenuElement_HoverElement_Type_Button_TextBonusResource(
                              ResourcesManager.getResourceName(bestResourceID) + ": ",
                              CFG.getPrecision2(goodsProduced.get(bestResourceID), 10),
                              bestResourceID,
                              CFG.FONT_BOLD_SMALL,
                              CFG.FONT_BOLD_SMALL,
                              Colors.HOVER_LEFT,
                              Colors.HOVER_GOLD
                           )
                        );
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        goodsProduced.set(bestResourceID, 0.0F);
                     }
                  } else {
                     nData.add(
                        new MenuElement_HoverElement_Type_Button_TextBonus(
                           Game.lang.get("None"), "", Images.resourceNone, CFG.FONT_BOLD_SMALL, CFG.FONT_BOLD_SMALL, Colors.HOVER_LEFT, Colors.HOVER_GOLD
                        )
                     );
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                  }

                  goodsProduced.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements);
               }
            }
         );
         menuElements.get(menuElements.size() - 1).setCurrent(tCivs.get(i));
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         tCivs.remove(i);
         tPopulation.remove(i);
      }

      i = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - CFG.PADDING * 3);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, i)));
      this.initMenu(new MenuTitleIMG_DoubleText(civsListTitle, civsListTitle2, false, false, Images.title600) {
         @Override
         public long getTime() {
            return InGame_Civ_List.lTime2;
         }
      }, menuX, menuY, menuWidth, i, menuElements, false, true);
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
         Images.insideTop600,
         Images.insideBot600
      );
      ImageManager.getImage(Images.civInfoOver)
         .draw2(
            oSB,
            this.getPosX() + iTranslateX,
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
