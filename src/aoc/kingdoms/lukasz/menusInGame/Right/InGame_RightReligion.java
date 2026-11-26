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
import aoc.kingdoms.lukasz.menu_element.button.ButtonReligion2;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Religion;
import aoc.kingdoms.lukasz.menu_element.graph.Graph_Vertical;
import aoc.kingdoms.lukasz.menu_element.graph.Graph_Vertical_Data_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonusReligion;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_FlagCiv_Title;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG_Clear;
import aoc.kingdoms.lukasz.menu_element.pieChart.PieChart_Data;
import aoc.kingdoms.lukasz.menu_element.pieChart.PieChart_Value;
import aoc.kingdoms.lukasz.menu_element.pieChart.PieChart_WithStatsReligion;
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

public class InGame_RightReligion extends Menu {
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static long lTime2 = 0L;
   public static int iSortID = 2;
   public static int iReligionID = -1;

   public InGame_RightReligion() {
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
      if (iReligionID < 0) {
         buttonX = Images.boxTitleBORDERWIDTH + CFG.PADDING;
         List<Integer> tReligionID = new ArrayList<>();
         List<Long> tPopulation = new ArrayList<>();

         for (int i = 0; i < Game.religionManager.getReligionsSize(); i++) {
            tReligionID.add(i);
            tPopulation.add(0L);
         }

         for (int i = 0; i < Game.getProvincesSize(); i++) {
            if (Game.getProvince(i).getCivID() > 0) {
               tPopulation.set(Game.getProvince(i).getReligion(), tPopulation.get(Game.getProvince(i).getReligion()) + Game.getProvince(i).getPopulationTotal());
            }
         }

         PieChart_Data nPieChartData = new PieChart_Data() {
            @Override
            public float getPieChartValue_ColorR(int i) {
               return Game.religionManager.getReligion(this.getPieChartValue(i).getDataID()).Color[0];
            }

            @Override
            public float getPieChartValue_ColorG(int i) {
               return Game.religionManager.getReligion(this.getPieChartValue(i).getDataID()).Color[1];
            }

            @Override
            public float getPieChartValue_ColorB(int i) {
               return Game.religionManager.getReligion(this.getPieChartValue(i).getDataID()).Color[2];
            }
         };

         for (int ix = tReligionID.size() - 1; ix >= 0; ix--) {
            if ((float)tPopulation.get(ix).longValue() > 0.001F) {
               nPieChartData.addPieChartValues(new PieChart_Value(tReligionID.get(ix), (float)tPopulation.get(ix).longValue()));
            }
         }

         int pieDim = CFG.BUTTON_HEIGHT3 * 3 + CFG.PADDING * 2;
         menuElements.add(
            new PieChart_WithStatsReligion(buttonX, buttonY + CFG.PADDING, pieDim - CFG.PADDING * 2, pieDim - CFG.PADDING * 2, nPieChartData, null) {
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
                  nData.add(new MenuElement_HoverElement_Type_TextTitle_BG_Clear(Game.lang.get("Religion"), Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.religion, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();

                  for (int ixx = 0; ixx < this.pieChartData.getPieChartValuesSize() && ixx < 10; ixx++) {
                     nData.add(
                        new MenuElement_HoverElement_Type_Button_TextBonusReligion(
                           Game.religionManager.getReligion(this.pieChartData.getCivID(ixx)).Name + ": ",
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

         try {
            List<Integer> rightList = new ArrayList<>(tReligionID);
            List<Long> rightListPop = new ArrayList<>(tPopulation);

            for (int ixx = 0; !rightList.isEmpty() && ixx < 3; ixx++) {
               int bestID = 0;

               for (int o = 1; o < rightList.size(); o++) {
                  if (rightListPop.get(bestID) < rightListPop.get(o)) {
                     bestID = o;
                  }
               }

               menuElements.add(
                  new ButtonStatsRectIMG_Religion(
                     "" + Game.religionManager.getReligion(rightList.get(bestID)).Name,
                     rightList.get(bestID),
                     nX,
                     nY,
                     nW,
                     buttonH,
                     ImageManager.getImage(Images.religion).getWidth(),
                     0
                  ) {
                     @Override
                     public void actionElement() {
                        InGame_RightReligion.iReligionID = this.imageID;
                        Game.menuManager.rebuildInGame_RightReligion();
                        InGame_RightReligion.lTime = 0L;
                     }

                     @Override
                     public void buildElementHover() {
                        List<MenuElement_HoverElement> nElements = new ArrayList<>();
                        List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                        nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Religion") + ": ", CFG.FONT_BOLD));
                        nData.add(
                           new MenuElement_HoverElement_Type_TextTitle_BG_Clear(
                              Game.religionManager.getReligion(this.imageID).Name, CFG.FONT_BOLD, Colors.HOVER_GOLD
                           )
                        );
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        this.menuElementHover = new MenuElement_Hover(nElements, true);
                     }
                  }
               );
               nY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
               rightList.remove(bestID);
               rightListPop.remove(bestID);
            }
         } catch (Exception var34) {
            CFG.exceptionStack(var34);
         }

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
                  if (InGame_RightReligion.iSortID == 0) {
                     InGame_RightReligion.iSortID = 1;
                  } else {
                     InGame_RightReligion.iSortID = 0;
                  }

                  Game.menuManager.rebuildInGame_RightReligion();
                  InGame_RightReligion.lTime = 0L;
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
                  if (InGame_RightReligion.iSortID == 2) {
                     InGame_RightReligion.iSortID = 3;
                  } else {
                     InGame_RightReligion.iSortID = 2;
                  }

                  Game.menuManager.rebuildInGame_RightReligion();
                  InGame_RightReligion.lTime = 0L;
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

         while (tReligionID.size() > 0) {
            int toAddID = 0;
            if (iSortID == 0) {
               for (int ox = 1; ox < tReligionID.size(); ox++) {
                  if (CFG.compareAlphabetic_TwoString(
                     Game.religionManager.getReligion(tReligionID.get(toAddID)).Name, Game.religionManager.getReligion(tReligionID.get(ox)).Name
                  )) {
                     toAddID = ox;
                  }
               }
            } else if (iSortID == 1) {
               for (int oxx = 1; oxx < tReligionID.size(); oxx++) {
                  if (CFG.compareAlphabetic_TwoString(
                     Game.religionManager.getReligion(tReligionID.get(oxx)).Name, Game.religionManager.getReligion(tReligionID.get(toAddID)).Name
                  )) {
                     toAddID = oxx;
                  }
               }
            } else if (iSortID == 2) {
               for (int oxxx = 1; oxxx < tReligionID.size(); oxxx++) {
                  if (tPopulation.get(toAddID) < tPopulation.get(oxxx)) {
                     toAddID = oxxx;
                  }
               }
            } else if (iSortID == 3) {
               for (int oxxxx = 1; oxxxx < tReligionID.size(); oxxxx++) {
                  if (tPopulation.get(toAddID) > tPopulation.get(oxxxx)) {
                     toAddID = oxxxx;
                  }
               }
            }

            buttonX = Images.boxTitleBORDERWIDTH + CFG.PADDING;
            menuElements.add(
               new ButtonReligion2(tReligionID.get(toAddID), buttonX, buttonY, r0W2, religionH) {
                  @Override
                  public void actionElement() {
                     InGame_RightReligion.iReligionID = this.getCurrent();
                     Game.menuManager.rebuildInGame_RightReligion();
                     InGame_RightReligion.lTime = 0L;
                  }

                  @Override
                  public void buildElementHover() {
                     List<MenuElement_HoverElement> nElements = new ArrayList<>();
                     List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                     nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Religion") + ": ", CFG.FONT_BOLD));
                     nData.add(
                        new MenuElement_HoverElement_Type_TextTitle_BG_Clear(
                           Game.religionManager.getReligion(this.religionID).Name, CFG.FONT_BOLD, Colors.HOVER_GOLD
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
            List<Integer> largestCiv = new ArrayList<>();

            for (int ixx = 0; ixx < Game.getCivsSize(); ixx++) {
               largestCiv.add(0);
            }

            for (int ixx = 0; ixx < Game.getProvincesSize(); ixx++) {
               if (Game.getProvince(ixx).getCivID() > 0 && Game.getProvince(ixx).getReligion() == tReligionID.get(toAddID)) {
                  largestCiv.set(
                     Game.getProvince(ixx).getCivID(), largestCiv.get(Game.getProvince(ixx).getCivID()) + Game.getProvince(ixx).getPopulationTotal()
                  );
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
                              this.getCurrent(), Game.religionManager.getReligion(Game.getCiv(this.getCurrent()).getReligionID()).Name
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

            buttonY += religionH + CFG.PADDING;
            tReligionID.remove(toAddID);
            tPopulation.remove(toAddID);
         }
      } else {
         buttonY = CFG.PADDING;
         buttonX = Images.boxTitleBORDERWIDTH + CFG.PADDING;
         menuElements.add(new ButtonReligion2(iReligionID, buttonX, buttonY, menuWidth - buttonX - CFG.PADDING, religionH) {
            @Override
            public void actionElement() {
               InGame_RightReligion.iReligionID = -1;
               Game.menuManager.rebuildInGame_RightReligion();
               InGame_RightReligion.lTime = 0L;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Religion"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.religion, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements, true);
            }
         });
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         Graph_Vertical graphVertical = new Graph_Vertical(
            Graph_Vertical_Data_Type.RELIGION_CIVS_RIGHT,
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
                  if (InGame_RightReligion.iSortID == 0) {
                     InGame_RightReligion.iSortID = 1;
                  } else {
                     InGame_RightReligion.iSortID = 0;
                  }

                  Game.menuManager.rebuildInGame_RightReligion();
                  InGame_RightReligion.lTime = 0L;
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
                  if (InGame_RightReligion.iSortID == 2) {
                     InGame_RightReligion.iSortID = 3;
                  } else {
                     InGame_RightReligion.iSortID = 2;
                  }

                  Game.menuManager.rebuildInGame_RightReligion();
                  InGame_RightReligion.lTime = 0L;
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
         List<Integer> tPop = new ArrayList<>();

         for (int ixxxx = 0; ixxxx < Game.getCivsSize(); ixxxx++) {
            tCivsID.add(ixxxx);
            tPop.add(0);
         }

         for (int ixxxx = 0; ixxxx < Game.getProvincesSize(); ixxxx++) {
            if (Game.getProvince(ixxxx).getCivID() > 0 && Game.getProvince(ixxxx).getReligion() == iReligionID) {
               tPop.set(Game.getProvince(ixxxx).getCivID(), tPop.get(Game.getProvince(ixxxx).getCivID()) + Game.getProvince(ixxxx).getPopulationTotal());
            }
         }

         for (int ixxxxx = tCivsID.size() - 1; ixxxxx >= 0; ixxxxx--) {
            if (tPop.get(ixxxxx) <= 0) {
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
                  for (int oxxxxx = 1; oxxxxx < tCivsID.size(); oxxxxx++) {
                     if (CFG.compareAlphabetic_TwoString(Game.getCiv(tCivsID.get(toAddIDx)).getCivName(), Game.getCiv(tCivsID.get(oxxxxx)).getCivName())) {
                        toAddIDx = oxxxxx;
                     }
                  }
               } else if (iSortID == 1) {
                  for (int oxxxxxx = 1; oxxxxxx < tCivsID.size(); oxxxxxx++) {
                     if (CFG.compareAlphabetic_TwoString(Game.getCiv(tCivsID.get(oxxxxxx)).getCivName(), Game.getCiv(tCivsID.get(toAddIDx)).getCivName())) {
                        toAddIDx = oxxxxxx;
                     }
                  }
               } else if (iSortID == 2) {
                  for (int oxxxxxxx = 1; oxxxxxxx < tCivsID.size(); oxxxxxxx++) {
                     if (tPop.get(toAddIDx) < tPop.get(oxxxxxxx)) {
                        toAddIDx = oxxxxxxx;
                     }
                  }
               } else if (iSortID == 3) {
                  for (int oxxxxxxxx = 1; oxxxxxxxx < tCivsID.size(); oxxxxxxxx++) {
                     if (tPop.get(toAddIDx) > tPop.get(oxxxxxxxx)) {
                        toAddIDx = oxxxxxxxx;
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
                              this.getCurrent(), Game.religionManager.getReligion(Game.getCiv(this.getCurrent()).getReligionID()).Name
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
      this.initMenu(new MenuTitleIMG(Game.lang.get("Religion"), false, false, Images.title600) {
         @Override
         public void draw(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, Status titleStatus) {
            ImageManager.getImage(this.imageID).draw2(oSB, nPosX, nPosY - this.getHeight(), nWidth, this.getHeight());
            this.drawGradient(oSB, nPosX, nPosY, nWidth, titleStatus);
            this.drawText(oSB, nPosX + Images.boxTitleBORDERWIDTH / 2, nPosY, nWidth, titleStatus);
         }

         @Override
         public long getTime() {
            return InGame_RightReligion.lTime2;
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
