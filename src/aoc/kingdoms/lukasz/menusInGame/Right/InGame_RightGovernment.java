package aoc.kingdoms.lukasz.menusInGame.Right;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.province.ProvinceBorderManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonIdeology2;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Bonuses_Style;
import aoc.kingdoms.lukasz.menu_element.graph.Graph_Vertical;
import aoc.kingdoms.lukasz.menu_element.graph.Graph_Vertical_Data_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonusIdeology;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_FlagCiv_Title;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Image;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG_Clear;
import aoc.kingdoms.lukasz.menu_element.pieChart.PieChart_Data;
import aoc.kingdoms.lukasz.menu_element.pieChart.PieChart_Value;
import aoc.kingdoms.lukasz.menu_element.pieChart.PieChart_WithStatsGovernment;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID_FlagCiv;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID_FlagCiv_Center;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_TitleBlueSort;
import aoc.kingdoms.lukasz.menusInGame.Province.InGame_ProvinceInfo;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_RightGovernment extends Menu {
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static long lTime2 = 0L;
   public static int iSortID = 2;
   public static int iGovID = -1;

   public InGame_RightGovernment() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING;
      int titleHeight = ImageManager.getImage(Images.title2).getHeight();
      int extraX = Images.boxTitleBORDERWIDTH;
      int menuWidth = ImageManager.getImage(Images.title1Red).getWidth();
      int menuX = CFG.GAME_WIDTH - menuWidth;
      int menuY = ImageManager.getImage(Images.topStats).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title1Red).getHeight();
      int buttonYPadding = CFG.PADDING;
      int buttonY = 0;
      int buttonX = paddingLeft + Images.boxTitleBORDERWIDTH;
      int buttonH = CFG.isDesktop() ? CFG.BUTTON_HEIGHT3 : CFG.BUTTON_HEIGHT2;
      int r0W = (int)((menuWidth - Images.boxTitleBORDERWIDTH) * 0.35F);
      int r1W = (int)((menuWidth - Images.boxTitleBORDERWIDTH) * 0.65F);
      int r0W2 = (int)((menuWidth - Images.boxTitleBORDERWIDTH - CFG.PADDING * 3) * 0.35F);
      int r1W2 = (int)((menuWidth - Images.boxTitleBORDERWIDTH - CFG.PADDING * 3) * 0.65F);
      int p0W = (int)((menuWidth - Images.boxTitleBORDERWIDTH) * 0.6F);
      int p1W = (int)((menuWidth - Images.boxTitleBORDERWIDTH) * 0.4F);
      int p0W2 = (int)((menuWidth - Images.boxTitleBORDERWIDTH - CFG.PADDING * 3) * 0.6F);
      int p1W2 = (int)((menuWidth - Images.boxTitleBORDERWIDTH - CFG.PADDING * 3) * 0.4F);
      buttonX = Images.boxTitleBORDERWIDTH;
      int religionH = CFG.BUTTON_HEIGHT;
      int popH = (CFG.BUTTON_HEIGHT - CFG.PADDING) / 2;
      if (iGovID < 0) {
         buttonX = Images.boxTitleBORDERWIDTH + CFG.PADDING;
         List<Integer> tIdeologyID = new ArrayList<>();
         List<Long> tPopulation = new ArrayList<>();

         for (int i = 0; i < Game.ideologiesManager.getIdeologiesSize(); i++) {
            tIdeologyID.add(i);
            tPopulation.add(0L);
         }

         for (int i = 0; i < Game.getProvincesSize(); i++) {
            if (Game.getProvince(i).getCivID() > 0) {
               tPopulation.set(
                  Game.getCiv(Game.getProvince(i).getCivID()).getIdeologyID(),
                  tPopulation.get(Game.getCiv(Game.getProvince(i).getCivID()).getIdeologyID()) + Game.getProvince(i).getPopulationTotal()
               );
            }
         }

         PieChart_Data nPieChartData = new PieChart_Data() {
            @Override
            public float getPieChartValue_ColorR(int i) {
               return Game.ideologiesManager.getIdeology(this.getPieChartValue(i).getDataID()).Color[0];
            }

            @Override
            public float getPieChartValue_ColorG(int i) {
               return Game.ideologiesManager.getIdeology(this.getPieChartValue(i).getDataID()).Color[1];
            }

            @Override
            public float getPieChartValue_ColorB(int i) {
               return Game.ideologiesManager.getIdeology(this.getPieChartValue(i).getDataID()).Color[2];
            }
         };
         long populationTotal = 0L;

         for (int ix = tIdeologyID.size() - 1; ix >= 0; ix--) {
            if ((float)tPopulation.get(ix).longValue() > 0.001F) {
               nPieChartData.addPieChartValues(new PieChart_Value(tIdeologyID.get(ix), (float)tPopulation.get(ix).longValue()));
               populationTotal += tPopulation.get(ix);
            }
         }

         int pieDim = CFG.BUTTON_HEIGHT3 * 2 + CFG.PADDING;
         menuElements.add(
            new PieChart_WithStatsGovernment(buttonX, buttonY + CFG.PADDING, pieDim - CFG.PADDING * 2, pieDim - CFG.PADDING * 2, nPieChartData, null) {
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
                     new MenuElement_HoverElement_Type_TextTitle_BG(
                        Game.lang.get(Game.map.lMaps.get(Game.map.iActiveMapID).mapData.Name) + ": ", Colors.HOVER_LEFT
                     )
                  );
                  nData.add(new MenuElement_HoverElement_Type_TextTitle_BG_Clear(Game.lang.get("Governments"), Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.government, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();

                  for (int ixx = 0; ixx < this.pieChartData.getPieChartValuesSize() && ixx < 10; ixx++) {
                     nData.add(
                        new MenuElement_HoverElement_Type_Button_TextBonusIdeology(
                           Game.ideologiesManager.getIdeology(this.pieChartData.getCivID(ixx)).Name + ": ",
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
         int nY = buttonY;
         int nX = menuElements.get(menuElements.size() - 1).getPosX() + menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING * 2;
         int nW = menuWidth - paddingLeft - nX;
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING * 3;
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses_Style(
               Game.lang.get("Governments") + ": ",
               "" + Game.ideologiesManager.getIdeologiesSize(),
               Images.government,
               nX,
               nY,
               nW,
               CFG.BUTTON_HEIGHT3,
               ImageManager.getImage(Images.battleWidth).getWidth()
            ) {}
         );
         nY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses_Style(
               "",
               "" + CFG.getShortNumber(populationTotal),
               Images.population,
               nX,
               nY,
               nW,
               CFG.BUTTON_HEIGHT3,
               ImageManager.getImage(Images.battleWidth).getWidth()
            ) {
               @Override
               public Color getColorBonus() {
                  return Colors.COLOR_POPULATION;
               }

               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Population") + ": ", CFG.FONT_BOLD));
                  nData.add(new MenuElement_HoverElement_Type_Text(this.sText2, CFG.FONT_BOLD, Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_Image(this.imageID, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements);
               }
            }
         );
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
                  if (InGame_RightGovernment.iSortID == 0) {
                     InGame_RightGovernment.iSortID = 1;
                  } else {
                     InGame_RightGovernment.iSortID = 0;
                  }

                  Game.menuManager.rebuildInGame_RightGovernment();
                  InGame_RightGovernment.lTime = 0L;
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
               r1W,
               CFG.TEXT_HEIGHT + CFG.PADDING * 6,
               CFG.FONT_BOLD_SMALL
            ) {
               @Override
               public void drawLines(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
               }

               @Override
               public void actionElement() {
                  if (InGame_RightGovernment.iSortID == 2) {
                     InGame_RightGovernment.iSortID = 3;
                  } else {
                     InGame_RightGovernment.iSortID = 2;
                  }

                  Game.menuManager.rebuildInGame_RightGovernment();
                  InGame_RightGovernment.lTime = 0L;
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
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;

         while (tIdeologyID.size() > 0) {
            int toAddID = 0;
            if (iSortID == 0) {
               for (int o = 1; o < tIdeologyID.size(); o++) {
                  if (CFG.compareAlphabetic_TwoString(
                     Game.ideologiesManager.getIdeology(tIdeologyID.get(toAddID)).Name, Game.ideologiesManager.getIdeology(tIdeologyID.get(o)).Name
                  )) {
                     toAddID = o;
                  }
               }
            } else if (iSortID == 1) {
               for (int ox = 1; ox < tIdeologyID.size(); ox++) {
                  if (CFG.compareAlphabetic_TwoString(
                     Game.ideologiesManager.getIdeology(tIdeologyID.get(ox)).Name, Game.ideologiesManager.getIdeology(tIdeologyID.get(toAddID)).Name
                  )) {
                     toAddID = ox;
                  }
               }
            } else if (iSortID == 2) {
               for (int oxx = 1; oxx < tIdeologyID.size(); oxx++) {
                  if (tPopulation.get(toAddID) < tPopulation.get(oxx)) {
                     toAddID = oxx;
                  }
               }
            } else if (iSortID == 3) {
               for (int oxxx = 1; oxxx < tIdeologyID.size(); oxxx++) {
                  if (tPopulation.get(toAddID) > tPopulation.get(oxxx)) {
                     toAddID = oxxx;
                  }
               }
            }

            buttonX = Images.boxTitleBORDERWIDTH + CFG.PADDING;
            menuElements.add(
               new ButtonIdeology2(tIdeologyID.get(toAddID), buttonX, buttonY, r0W2, religionH) {
                  @Override
                  public void actionElement() {
                     InGame_RightGovernment.iGovID = this.ideologyID;
                     Game.menuManager.rebuildInGame_RightGovernment();
                     InGame_RightGovernment.lTime = 0L;
                  }

                  @Override
                  public void buildElementHover() {
                     List<MenuElement_HoverElement> nElements = new ArrayList<>();
                     List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                     nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Government") + ": ", CFG.FONT_BOLD));
                     nData.add(
                        new MenuElement_HoverElement_Type_TextTitle_BG_Clear(
                           Game.ideologiesManager.getIdeology(this.ideologyID).Name, CFG.FONT_BOLD, Colors.HOVER_GOLD
                        )
                     );
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     this.menuElementHover = new MenuElement_Hover(nElements, true);
                  }
               }
            );
            buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
            menuElements.add(
               new Text_StaticBG(CFG.getNumberWithSpaces("" + tPopulation.get(toAddID)), CFG.FONT_REGULAR_SMALL, -1, buttonX, buttonY, r1W2, popH) {
                  @Override
                  public void buildElementHover() {
                     List<MenuElement_HoverElement> nElements = new ArrayList<>();
                     List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                     nData.add(
                        new MenuElement_HoverElement_Type_Button_TextBonus(
                           Game.lang.get("Population") + ": ",
                           this.getText(),
                           Images.population,
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
            List<Long> largestCiv = new ArrayList<>();

            for (int ixx = 0; ixx < Game.getCivsSize(); ixx++) {
               if (Game.getCiv(ixx).getIdeologyID() == tIdeologyID.get(toAddID)) {
                  largestCiv.add(Game.getCiv(ixx).getPopulationTotal());
               } else {
                  largestCiv.add(0L);
               }
            }

            int bestCiv = 0;

            for (int ixxx = 1; ixxx < Game.getCivsSize(); ixxx++) {
               if (largestCiv.get(bestCiv) < largestCiv.get(ixxx)) {
                  bestCiv = ixxx;
               }
            }

            if (bestCiv > 0) {
               menuElements.add(
                  new Text_StaticBG_ID_FlagCiv_Center(
                     Game.getCiv(bestCiv).getCivName(), CFG.FONT_REGULAR_SMALL, CFG.PADDING * 2, buttonX, buttonY + CFG.PADDING + popH, r1W2, popH, bestCiv
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
                              if (Game.menuManager.getVisibleInGame_ProvinceInfo()) {
                                 Game.menuManager.rebuildInGame_ProvinceInfo(false);
                              }
                           }
                        }
                     }

                     @Override
                     public void buildElementHover() {
                        List<MenuElement_HoverElement> nElements = new ArrayList<>();
                        List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                        nData.add(
                           new MenuElement_HoverElement_Type_FlagCiv_Title(
                              this.getCurrent(), Game.ideologiesManager.getIdeology(Game.getCiv(this.getCurrent()).getIdeologyID()).Name
                           )
                        );
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        nData.add(
                           new MenuElement_HoverElement_Type_Button_TextBonus(
                              Game.lang.get("Population") + ": ",
                              CFG.getNumberWithSpaces("" + Game.getCiv(this.getCurrent()).getPopulationTotal()),
                              Images.population,
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
            } else {
               menuElements.add(new Text_StaticBG("-", CFG.FONT_REGULAR_SMALL, CFG.PADDING * 2, buttonX, buttonY + CFG.PADDING + popH, r1W2, popH));
            }

            largestCiv.clear();
            buttonY += religionH + CFG.PADDING;
            tIdeologyID.remove(toAddID);
            tPopulation.remove(toAddID);
         }
      } else {
         buttonY = CFG.PADDING;
         buttonX = Images.boxTitleBORDERWIDTH + CFG.PADDING;
         menuElements.add(new ButtonIdeology2(iGovID, buttonX, buttonY, menuWidth - buttonX - CFG.PADDING, religionH) {
            @Override
            public void actionElement() {
               InGame_RightGovernment.iGovID = -1;
               Game.menuManager.rebuildInGame_RightGovernment();
               InGame_RightGovernment.lTime = 0L;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Government"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.government, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements, true);
            }
         });
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         Graph_Vertical graphVertical = new Graph_Vertical(
            Graph_Vertical_Data_Type.GOVERNMENTS_CIVS_RIGHT,
            Game.lang.get("Civilizations"),
            Game.lang.get("Civilizations"),
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            menuWidth / 2,
            true
         ) {};
         menuElements.add(graphVertical);
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
               p0W,
               CFG.TEXT_HEIGHT + CFG.PADDING * 6,
               CFG.FONT_BOLD_SMALL
            ) {
               @Override
               public void drawLines(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
               }

               @Override
               public void actionElement() {
                  if (InGame_RightGovernment.iSortID == 0) {
                     InGame_RightGovernment.iSortID = 1;
                  } else {
                     InGame_RightGovernment.iSortID = 0;
                  }

                  Game.menuManager.rebuildInGame_RightGovernment();
                  InGame_RightGovernment.lTime = 0L;
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
               p1W,
               CFG.TEXT_HEIGHT + CFG.PADDING * 6,
               CFG.FONT_BOLD_SMALL
            ) {
               @Override
               public void drawLines(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
               }

               @Override
               public void actionElement() {
                  if (InGame_RightGovernment.iSortID == 2) {
                     InGame_RightGovernment.iSortID = 3;
                  } else {
                     InGame_RightGovernment.iSortID = 2;
                  }

                  Game.menuManager.rebuildInGame_RightGovernment();
                  InGame_RightGovernment.lTime = 0L;
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
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         List<Integer> tCivsID = new ArrayList<>();
         List<Long> tPop = new ArrayList<>();

         for (int ixxxx = 0; ixxxx < Game.getCivsSize(); ixxxx++) {
            tCivsID.add(ixxxx);
            if (Game.getCiv(ixxxx).getIdeologyID() == iGovID) {
               tPop.add(Game.getCiv(ixxxx).getPopulationTotal());
            } else {
               tPop.add(0L);
            }
         }

         for (int ixxxxx = tCivsID.size() - 1; ixxxxx >= 0; ixxxxx--) {
            if (tPop.get(ixxxxx) <= 0L) {
               tCivsID.remove(ixxxxx);
               tPop.remove(ixxxxx);
            }
         }

         if (tCivsID.size() <= 0) {
            menuElements.add(
               new Text_StaticBG(
                  Game.lang.get("None"),
                  CFG.FONT_REGULAR,
                  -1,
                  Images.boxTitleBORDERWIDTH + CFG.PADDING,
                  buttonY,
                  menuWidth - (Images.boxTitleBORDERWIDTH + CFG.PADDING * 2),
                  CFG.BUTTON_HEIGHT2
               )
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         } else {
            while (tCivsID.size() > 0) {
               int toAddIDx = 0;
               if (iSortID == 0) {
                  for (int oxxxx = 1; oxxxx < tCivsID.size(); oxxxx++) {
                     if (CFG.compareAlphabetic_TwoString(Game.getCiv(tCivsID.get(toAddIDx)).getCivName(), Game.getCiv(tCivsID.get(oxxxx)).getCivName())) {
                        toAddIDx = oxxxx;
                     }
                  }
               } else if (iSortID == 1) {
                  for (int oxxxxx = 1; oxxxxx < tCivsID.size(); oxxxxx++) {
                     if (CFG.compareAlphabetic_TwoString(Game.getCiv(tCivsID.get(oxxxxx)).getCivName(), Game.getCiv(tCivsID.get(toAddIDx)).getCivName())) {
                        toAddIDx = oxxxxx;
                     }
                  }
               } else if (iSortID == 2) {
                  for (int oxxxxxx = 1; oxxxxxx < tCivsID.size(); oxxxxxx++) {
                     if (tPop.get(toAddIDx) < tPop.get(oxxxxxx)) {
                        toAddIDx = oxxxxxx;
                     }
                  }
               } else if (iSortID == 3) {
                  for (int oxxxxxxx = 1; oxxxxxxx < tCivsID.size(); oxxxxxxx++) {
                     if (tPop.get(toAddIDx) > tPop.get(oxxxxxxx)) {
                        toAddIDx = oxxxxxxx;
                     }
                  }
               }

               buttonX = Images.boxTitleBORDERWIDTH + CFG.PADDING;
               menuElements.add(
                  new Text_StaticBG_ID_FlagCiv(
                     Game.getCiv(tCivsID.get(toAddIDx)).getCivName(),
                     CFG.FONT_REGULAR_SMALL,
                     CFG.PADDING * 2,
                     buttonX,
                     buttonY,
                     p0W2,
                     buttonH,
                     tCivsID.get(toAddIDx)
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
                              if (Game.menuManager.getVisibleInGame_ProvinceInfo()) {
                                 Game.menuManager.rebuildInGame_ProvinceInfo(false);
                              }
                           }
                        }
                     }

                     @Override
                     public void buildElementHover() {
                        List<MenuElement_HoverElement> nElements = new ArrayList<>();
                        List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                        nData.add(
                           new MenuElement_HoverElement_Type_FlagCiv_Title(
                              this.getCurrent(), Game.ideologiesManager.getIdeology(Game.getCiv(this.getCurrent()).getIdeologyID()).Name
                           )
                        );
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        nData.add(
                           new MenuElement_HoverElement_Type_Button_TextBonus(
                              Game.lang.get("Population") + ": ",
                              CFG.getNumberWithSpaces("" + Game.getCiv(this.getCurrent()).getPopulationTotal()),
                              Images.population,
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
               buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
               menuElements.add(
                  new Text_StaticBG(CFG.getNumberWithSpaces("" + tPop.get(toAddIDx)), CFG.FONT_REGULAR_SMALL, -1, buttonX, buttonY, p1W2, buttonH) {
                     @Override
                     public void buildElementHover() {
                        List<MenuElement_HoverElement> nElements = new ArrayList<>();
                        List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                        nData.add(
                           new MenuElement_HoverElement_Type_Button_TextBonus(
                              Game.lang.get("Population") + ": ",
                              this.getText(),
                              Images.population,
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
               tCivsID.remove(toAddIDx);
               tPop.remove(toAddIDx);
            }
         }
      }

      buttonY = 0;
      int ixxxxxx = 0;

      for (int iSize = menuElements.size(); ixxxxxx < iSize; ixxxxxx++) {
         if (buttonY < menuElements.get(ixxxxxx).getPosY() + menuElements.get(ixxxxxx).getHeight() + CFG.PADDING) {
            buttonY = menuElements.get(ixxxxxx).getPosY() + menuElements.get(ixxxxxx).getHeight() + CFG.PADDING;
         }
      }

      ixxxxxx = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - (CFG.GAME_HEIGHT - Game.menuManager.getInGame_MapModesPosY() + CFG.PADDING * 3));
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, ixxxxxx)));
      this.initMenu(new MenuTitleIMG(Game.lang.get("Government"), false, false, Images.title600) {
         @Override
         public void draw(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, Status titleStatus) {
            ImageManager.getImage(this.imageID).draw2(oSB, nPosX, nPosY - this.getHeight(), nWidth, this.getHeight());
            this.drawGradient(oSB, nPosX, nPosY, nWidth, titleStatus);
            this.drawText(oSB, nPosX + Images.boxTitleBORDERWIDTH / 2, nPosY, nWidth, titleStatus);
         }

         @Override
         public long getTime() {
            return InGame_RightGovernment.lTime2;
         }
      }, menuX, menuY, menuWidth, ixxxxxx, menuElements, false, true);
      this.scrollExtraPosX = Images.boxTitleBORDERWIDTH;
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      if (lTime + 60L >= CFG.currentTimeMillis) {
         iTranslateX = iTranslateX + CFG.BUTTON_WIDTH - (int)(CFG.BUTTON_WIDTH * ((float)(CFG.currentTimeMillis - lTime) / 60.0F));
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
      oSB.setColor(Colors.COLOR_GRADIENT);
      Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), false, true);
      oSB.setColor(Color.WHITE);
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public void setVisible(boolean visible) {
      super.setVisible(visible);
      lTime = CFG.currentTimeMillis;
      lTime2 = lTime;
   }

   @Override
   public boolean getVisible() {
      return super.getVisible() && Game.mapBG.getHideMenuZoomOut() && !Game.menuManager.getVisibleInGame_CivBonuses();
   }
}
