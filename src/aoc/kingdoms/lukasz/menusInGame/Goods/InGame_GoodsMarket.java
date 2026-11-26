package aoc.kingdoms.lukasz.menusInGame.Goods;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.ResourcesManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG_FlagCenter;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Active_Click;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Bonuses_Style;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonusResource;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Empty;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Image;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG_Clear;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text_Desc;
import aoc.kingdoms.lukasz.menu_element.pieChart.PieChart_Data;
import aoc.kingdoms.lukasz.menu_element.pieChart.PieChart_Value;
import aoc.kingdoms.lukasz.menu_element.pieChart.PieChart_WithStatsResources;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextIcon_Resource2;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_LargestProducer;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticGoods;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticGoods_ProgressBar;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_TitleBlueSort;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_CourtOptions2;
import aoc.kingdoms.lukasz.menusInGame.Province.InGame_ProvinceInfo;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_GoodsMarket extends Menu {
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static long lTime2 = 0L;
   public static int iActiveCivID;
   public static int iSortID = 4;

   public InGame_GoodsMarket() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = Images.boxTitleBORDERWIDTH + CFG.PADDING;
      int menuWidth = ImageManager.getImage(Images.insideTop500).getWidth();
      int menuX = InGame_CourtOptions2.getOtherMenuPosX_2();
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title500).getHeight();
      int buttonY = CFG.PADDING;
      int maxGoodsH = 0;
      int maxGoodsW = 0;

      for (int i = 0; i < ResourcesManager.iResourcesSize; i++) {
         if (ResourcesManager.resourceImages.get(i).getHeight() > maxGoodsH) {
            maxGoodsH = ResourcesManager.resourceImages.get(i).getHeight();
         }

         if (ResourcesManager.resourceImages.get(i).getWidth() > maxGoodsW) {
            maxGoodsW = ResourcesManager.resourceImages.get(i).getWidth();
         }
      }

      int maxIconW = ImageManager.getImage(Images.goods).getWidth();
      int statW = (menuWidth - paddingLeft * 2 - CFG.PADDING) / 2;
      List<Integer> lResources = new ArrayList<>();
      List<Float> lProduction = new ArrayList<>();
      List<Float> lIncome = new ArrayList<>();
      List<Float> lProductionEfficiency = new ArrayList<>();
      List<Integer> lProvinces = new ArrayList<>();

      for (int i = 0; i < Game.getCiv(iActiveCivID).getNumOfProvinces(); i++) {
         if (Game.getProvince(Game.getCiv(iActiveCivID).getProvinceID(i)).getResourceID() >= 0) {
            boolean tAdded = false;

            for (int k = lResources.size() - 1; k >= 0; k--) {
               if (lResources.get(k) == Game.getProvince(Game.getCiv(iActiveCivID).getProvinceID(i)).getResourceID()) {
                  tAdded = true;
                  lProduction.set(k, lProduction.get(k) + ResourcesManager.getProducedGoods(Game.getCiv(iActiveCivID).getProvinceID(i)));
                  lIncome.set(k, lIncome.get(k) + ResourcesManager.getMonthlyIncome(Game.getCiv(iActiveCivID).getProvinceID(i)));
                  lProductionEfficiency.set(
                     k, lProductionEfficiency.get(k) + ResourcesManager.getProductionEfficiency(Game.getCiv(iActiveCivID).getProvinceID(i))
                  );
                  lProvinces.set(k, lProvinces.get(k) + 1);
                  break;
               }
            }

            if (!tAdded) {
               lResources.add(Game.getProvince(Game.getCiv(iActiveCivID).getProvinceID(i)).getResourceID());
               lProduction.add(ResourcesManager.getProducedGoods(Game.getCiv(iActiveCivID).getProvinceID(i)));
               lIncome.add(ResourcesManager.getMonthlyIncome(Game.getCiv(iActiveCivID).getProvinceID(i)));
               lProductionEfficiency.add(ResourcesManager.getProductionEfficiency(Game.getCiv(iActiveCivID).getProvinceID(i)));
               lProvinces.add(1);
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
         new PieChart_WithStatsResources(paddingLeft, buttonY + CFG.PADDING, pieDim - CFG.PADDING * 2, pieDim - CFG.PADDING * 2, nPieChartData, null) {
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
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.getCiv(InGame_GoodsMarket.iActiveCivID).getCivName() + ": ", Colors.HOVER_LEFT));
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
               Game.menuManager.rebuildInGame_Goods();
               Game.menuManager.setVisibleInGame_Goods(true);
               InGame_Goods.lTime = 0L;
            }
         }
      );
      int nY = buttonY;
      int nX = menuElements.get(menuElements.size() - 1).getPosX() + menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING * 2;
      int nW = menuWidth - paddingLeft - nX;
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING * 3;
      menuElements.add(
         new ButtonStatsRectIMG_Active_Click(
            Game.lang.get("LargestGoodsProducers"), Images.goods, nX, nY, nW, CFG.BUTTON_HEIGHT3, ImageManager.getImage(Images.goods).getWidth(), 0, true
         ) {
            @Override
            public void actionElement() {
               Game.menuManager.rebuildInGame_Goods();
               Game.menuManager.setVisibleInGame_Goods(true);
               InGame_Goods.lTime = 0L;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("LargestGoodsProducers"), Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.goods, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text_Desc(Game.lang.get("Production0"), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Line());
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text_Desc(Game.lang.get("Production1"), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Line());
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text_Desc(Game.lang.get("Production2"), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      nY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new ButtonStatsRectIMG_Bonuses_Style(
            Game.lang.get("UniqueResources") + ": ",
            "" + Game.getCiv(iActiveCivID).iUniqueResources,
            Images.goods,
            nX,
            nY,
            nW,
            CFG.BUTTON_HEIGHT3,
            maxIconW + CFG.PADDING * 2
         ) {
            @Override
            public void actionElement() {
               Game.menuManager.rebuildInGame_Civ();
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("OpenTheDiplomacyView"), Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.diplomacy, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements, true);
            }
         }
      );
      nY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new ButtonStatsRectIMG_Bonuses_Style(
            Game.lang.get("Legacy") + ": ",
            (Game.getLegacyPerUniqueResources(iActiveCivID) > 0.0F ? "+" : "") + CFG.getPrecision2(Game.getLegacyPerUniqueResources(iActiveCivID), 100),
            Images.legacy,
            nX,
            nY,
            nW,
            CFG.BUTTON_HEIGHT3,
            maxIconW + CFG.PADDING * 2
         ) {
            @Override
            public void actionElement() {
               Game.menuManager.rebuildInGame_Civ();
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("OpenTheDiplomacyView"), Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.diplomacy, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Legacy") + ": "));
               nData.add(
                  new MenuElement_HoverElement_Type_TextTitle(
                     "" + CFG.getPrecision2(Game.getLegacyPerUniqueResources(InGame_GoodsMarket.iActiveCivID), 100), Colors.HOVER_GOLD
                  )
               );
               nData.add(new MenuElement_HoverElement_Type_ImageTitle(Images.legacy, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(
                  new MenuElement_HoverElement_Type_Text_Desc(
                     " = "
                        + CFG.getPrecision2(GameValues.legacy.LEGACY_PER_UNIQUE_RESOURCE, 100)
                        + " * "
                        + Game.lang.get("UniqueResources")
                        + " * min("
                        + CFG.getPrecision2(GameValues.legacy.LEGACY_PER_UNIQUE_RESOURCE_PER_TECHS_MAX, 100)
                        + ", "
                        + Game.lang.get("UnlockedTechnologies")
                        + " * "
                        + CFG.getPrecision2(GameValues.legacy.LEGACY_PER_UNIQUE_RESOURCE_PER_TECHS, 100)
                        + ")",
                     CFG.FONT_REGULAR_SMALL,
                     Colors.HOVER_RIGHT3
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData, false));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Line());
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("UniqueResources") + ": ", CFG.FONT_REGULAR_SMALL));
               nData.add(
                  new MenuElement_HoverElement_Type_Text(
                     "" + Game.getCiv(InGame_GoodsMarket.iActiveCivID).iUniqueResources, CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD
                  )
               );
               nData.add(new MenuElement_HoverElement_Type_Image(Images.goods, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("UnlockedTechnologies") + ": ", CFG.FONT_REGULAR_SMALL));
               nData.add(
                  new MenuElement_HoverElement_Type_Text(
                     "" + Game.getCiv(InGame_GoodsMarket.iActiveCivID).getResearchedTechnologies(), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD
                  )
               );
               nData.add(new MenuElement_HoverElement_Type_Image(Game_Calendar.IMG_TECHNOLOGY, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      maxGoodsH += CFG.PADDING * 2;
      maxGoodsW += CFG.PADDING * 5;
      int buttonX = Images.boxTitleBORDERWIDTH;
      int r0W = maxGoodsW + CFG.PADDING * 2;
      int r1W = (menuWidth - Images.boxTitleBORDERWIDTH * 2 - r0W) / 4;
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
               if (InGame_GoodsMarket.iSortID == 0) {
                  InGame_GoodsMarket.iSortID = 1;
               } else {
                  InGame_GoodsMarket.iSortID = 0;
               }

               Game.menuManager.rebuildInGame_GoodsMarket();
               InGame_GoodsMarket.lTime = 0L;
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
               if (InGame_GoodsMarket.iSortID == 2) {
                  InGame_GoodsMarket.iSortID = 3;
               } else {
                  InGame_GoodsMarket.iSortID = 2;
               }

               Game.menuManager.rebuildInGame_GoodsMarket();
               InGame_GoodsMarket.lTime = 0L;
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
            iSortID == 4 || iSortID == 5,
            iSortID == 5,
            Game.lang.get("Production"),
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
               if (InGame_GoodsMarket.iSortID == 4) {
                  InGame_GoodsMarket.iSortID = 5;
               } else {
                  InGame_GoodsMarket.iSortID = 4;
               }

               Game.menuManager.rebuildInGame_GoodsMarket();
               InGame_GoodsMarket.lTime = 0L;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("SortBy") + ": "));
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Production"), Colors.HOVER_GOLD));
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
               if (InGame_GoodsMarket.iSortID == 6) {
                  InGame_GoodsMarket.iSortID = 7;
               } else {
                  InGame_GoodsMarket.iSortID = 6;
               }

               Game.menuManager.rebuildInGame_GoodsMarket();
               InGame_GoodsMarket.lTime = 0L;
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
      buttonX += menuElements.get(menuElements.size() - 1).getWidth();
      menuElements.add(
         new Text_TitleBlueSort(
            iSortID == 8 || iSortID == 9,
            iSortID == 9,
            Game.lang.get("ShareInWorldProduction"),
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
               if (InGame_GoodsMarket.iSortID == 8) {
                  InGame_GoodsMarket.iSortID = 9;
               } else {
                  InGame_GoodsMarket.iSortID = 8;
               }

               Game.menuManager.rebuildInGame_GoodsMarket();
               InGame_GoodsMarket.lTime = 0L;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("SortBy") + ": "));
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("ShareInWorldProduction"), Colors.HOVER_GOLD));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      if (lResources.size() == 0) {
         menuElements.add(
            new Text_StaticBG(Game.lang.get("None"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT2) {
               @Override
               public void actionElement() {
                  if (Game.iActiveProvince >= 0 && Game.getProvince(Game.iActiveProvince).getCivID() > 0) {
                     InGame_Goods.iCivID = Game.getProvince(Game.iActiveProvince).getCivID();
                  } else {
                     InGame_Goods.iCivID = Game.player.iCivID;
                  }

                  InGame_Goods.sortBy = 0;
                  Game.menuManager.rebuildInGame_Goods();
                  Game.menuManager.setVisibleInGame_Goods(true);
               }

               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("LargestGoodsProducers") + ": ", CFG.FONT_BOLD, Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.goods, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements, true);
               }
            }
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      } else {
         List<Integer> production = new ArrayList<>();

         for (int ixx = 0; ixx < Game.getCivsSize(); ixx++) {
            production.add(0);
         }

         List<Float> lMarketShare = new ArrayList<>();
         int ixx = 0;

         for (int iSize = lResources.size(); ixx < iSize; ixx++) {
            lMarketShare.add(lProduction.get(ixx) * 100.0F / ResourcesManager.worldResourcesProduced.get(lResources.get(ixx)).intValue() * 100.0F);
            if (!Game.getCiv(iActiveCivID).getTechResearched(ResourcesManager.lResources.get(ixx).RequiredTechID)) {
               lProductionEfficiency.set(ixx, 0.0F);
            } else {
               lProductionEfficiency.set(ixx, lProductionEfficiency.get(ixx) / lProvinces.get(ixx).intValue());
            }
         }

         ixx = (menuWidth - paddingLeft * 2 - maxGoodsW - CFG.PADDING) / 4;

         while (lResources.size() > 0) {
            int toAddID = 0;
            if (iSortID == 0) {
               for (int o = 1; o < lResources.size(); o++) {
                  if (CFG.compareAlphabetic_TwoString(
                     ResourcesManager.getResourceName(lResources.get(toAddID)), ResourcesManager.getResourceName(lResources.get(o))
                  )) {
                     toAddID = o;
                  }
               }
            } else if (iSortID == 1) {
               for (int ox = 1; ox < lResources.size(); ox++) {
                  if (CFG.compareAlphabetic_TwoString(
                     ResourcesManager.getResourceName(lResources.get(ox)), ResourcesManager.getResourceName(lResources.get(toAddID))
                  )) {
                     toAddID = ox;
                  }
               }
            } else if (iSortID == 2) {
               for (int oxx = 1; oxx < lResources.size(); oxx++) {
                  if (lProvinces.get(oxx) > lProvinces.get(toAddID)) {
                     toAddID = oxx;
                  }
               }
            } else if (iSortID == 3) {
               for (int oxxx = 1; oxxx < lResources.size(); oxxx++) {
                  if (lProvinces.get(oxxx) < lProvinces.get(toAddID)) {
                     toAddID = oxxx;
                  }
               }
            } else if (iSortID == 4) {
               for (int oxxxx = 1; oxxxx < lResources.size(); oxxxx++) {
                  if (lProduction.get(oxxxx) > lProduction.get(toAddID)) {
                     toAddID = oxxxx;
                  }
               }
            } else if (iSortID == 5) {
               for (int oxxxxx = 1; oxxxxx < lResources.size(); oxxxxx++) {
                  if (lProduction.get(oxxxxx) < lProduction.get(toAddID)) {
                     toAddID = oxxxxx;
                  }
               }
            } else if (iSortID == 6) {
               for (int oxxxxxx = 1; oxxxxxx < lResources.size(); oxxxxxx++) {
                  if (lIncome.get(oxxxxxx) > lIncome.get(toAddID)) {
                     toAddID = oxxxxxx;
                  }
               }
            } else if (iSortID == 7) {
               for (int oxxxxxxx = 1; oxxxxxxx < lResources.size(); oxxxxxxx++) {
                  if (lIncome.get(oxxxxxxx) < lIncome.get(toAddID)) {
                     toAddID = oxxxxxxx;
                  }
               }
            } else if (iSortID == 8) {
               for (int oxxxxxxxx = 1; oxxxxxxxx < lResources.size(); oxxxxxxxx++) {
                  if (lMarketShare.get(oxxxxxxxx) > lMarketShare.get(toAddID)) {
                     toAddID = oxxxxxxxx;
                  }
               }
            } else if (iSortID == 9) {
               for (int oxxxxxxxxx = 1; oxxxxxxxxx > lResources.size(); oxxxxxxxxx++) {
                  if (lMarketShare.get(oxxxxxxxxx) < lMarketShare.get(toAddID)) {
                     toAddID = oxxxxxxxxx;
                  }
               }
            }

            buttonX = paddingLeft + maxGoodsW + CFG.PADDING;
            if (lProduction.get(toAddID) > 0.0F) {
               menuElements.add(
                  new Text_StaticGoods(
                     "" + CFG.getNumberWithSpaces("" + lProvinces.get(toAddID)),
                     CFG.FONT_REGULAR_SMALL,
                     -1,
                     buttonX,
                     buttonY,
                     ixx,
                     maxGoodsH,
                     lResources.get(toAddID)
                  ) {
                     @Override
                     public void actionElement() {
                        InGame_GoodsMarket_Resource.iResourceID = this.getCurrent();
                        InGame_GoodsMarket_Resource.iActiveCivID = InGame_GoodsMarket.iActiveCivID;
                        Game.menuManager.rebuildInGame_GoodsMarketResource();
                        InGame_GoodsMarket_Resource.lTime = 0L;
                     }

                     @Override
                     public void buildElementHover() {
                        List<MenuElement_HoverElement> nElements = new ArrayList<>();
                        List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                        nData.add(
                           new MenuElement_HoverElement_Type_Button_TextBonusResource(
                              Game.lang.get("Resource") + ": ",
                              ResourcesManager.getResourceName(this.getCurrent()),
                              this.getCurrent(),
                              CFG.FONT_REGULAR,
                              CFG.FONT_BOLD,
                              Colors.HOVER_LEFT,
                              Colors.HOVER_GOLD
                           )
                        );
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        nData.add(new MenuElement_HoverElement_Type_Line());
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        nData.add(
                           new MenuElement_HoverElement_Type_Button_TextBonus(
                              Game.lang.get("Provinces") + ": ",
                              this.getText(),
                              Images.provinces,
                              CFG.FONT_REGULAR,
                              CFG.FONT_BOLD,
                              Colors.HOVER_LEFT,
                              Colors.HOVER_GOLD
                           )
                        );
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        nData.add(new MenuElement_HoverElement_Type_Empty());
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.getCiv(InGame_GoodsMarket.iActiveCivID).getCivName() + ": "));
                        nData.add(
                           new MenuElement_HoverElement_Type_TextTitle_BG_Clear(
                              ResourcesManager.getResourceName(this.getCurrent()), CFG.FONT_BOLD, Colors.HOVER_GOLD
                           )
                        );
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        this.menuElementHover = new MenuElement_Hover(nElements);
                     }
                  }
               );
               buttonX += menuElements.get(menuElements.size() - 1).getWidth();
               menuElements.add(
                  new Text_StaticGoods(
                     ""
                        + (
                           lProduction.get(toAddID) >= 1000.0F
                              ? CFG.getNumberWithSpaces("" + CFG.getPrecision2(lProduction.get(toAddID), 1))
                              : CFG.getPrecision2(lProduction.get(toAddID), 10)
                        ),
                     CFG.FONT_REGULAR_SMALL,
                     -1,
                     buttonX,
                     buttonY,
                     ixx,
                     maxGoodsH,
                     lResources.get(toAddID)
                  ) {
                     @Override
                     public void actionElement() {
                        InGame_Goods_Provinces.RESOURCE_ID = this.getCurrent();
                        InGame_Goods_Provinces.lTime = 0L;
                        InGame_GoodsMarket.lTime = 0L;
                        Game.menuManager.rebuildInGame_Goods_Provinces();
                     }

                     @Override
                     public void buildElementHover() {
                        List<MenuElement_HoverElement> nElements = new ArrayList<>();
                        List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                        nData.add(
                           new MenuElement_HoverElement_Type_Button_TextBonusResource(
                              Game.lang.get("Resource") + ": ",
                              ResourcesManager.getResourceName(this.getCurrent()),
                              this.getCurrent(),
                              CFG.FONT_REGULAR,
                              CFG.FONT_BOLD,
                              Colors.HOVER_LEFT,
                              Colors.HOVER_GOLD
                           )
                        );
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        nData.add(new MenuElement_HoverElement_Type_Line());
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        int numOfProvinces = 0;

                        for (int i = 0; i < Game.getCiv(InGame_GoodsMarket.iActiveCivID).getNumOfProvinces(); i++) {
                           if (Game.getProvince(Game.getCiv(InGame_GoodsMarket.iActiveCivID).getProvinceID(i)).getResourceID() == this.getCurrent()) {
                              numOfProvinces++;
                           }
                        }

                        nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Provinces") + ": ", CFG.FONT_REGULAR_SMALL));
                        nData.add(new MenuElement_HoverElement_Type_Text("" + numOfProvinces, CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD));
                        nData.add(new MenuElement_HoverElement_Type_Image(Images.provinces, CFG.PADDING, 0));
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        nData.add(new MenuElement_HoverElement_Type_Empty());
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Provinces") + ": "));
                        nData.add(
                           new MenuElement_HoverElement_Type_TextTitle_BG_Clear(
                              ResourcesManager.getResourceName(this.getCurrent()), CFG.FONT_BOLD, Colors.HOVER_GOLD
                           )
                        );
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        this.menuElementHover = new MenuElement_Hover(nElements, true);
                     }
                  }
               );
               buttonX += menuElements.get(menuElements.size() - 1).getWidth();
               menuElements.add(
                  new Text_StaticGoods(
                     "" + CFG.getPrecision2(lIncome.get(toAddID), lIncome.get(toAddID) < 1.0F ? 100 : 10),
                     CFG.FONT_BOLD_SMALL,
                     -1,
                     buttonX,
                     buttonY,
                     ixx,
                     maxGoodsH,
                     lResources.get(toAddID)
                  ) {
                     @Override
                     public void buildElementHover() {
                        List<MenuElement_HoverElement> nElements = new ArrayList<>();
                        List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                        nData.add(
                           new MenuElement_HoverElement_Type_Button_TextBonusResource(
                              Game.lang.get("Resource") + ": ",
                              ResourcesManager.getResourceName(this.getCurrent()),
                              this.getCurrent(),
                              CFG.FONT_REGULAR,
                              CFG.FONT_BOLD,
                              Colors.HOVER_LEFT,
                              Colors.HOVER_GOLD
                           )
                        );
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        nData.add(new MenuElement_HoverElement_Type_Line());
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        int numOfProvinces = 0;

                        for (int i = 0; i < Game.getCiv(InGame_GoodsMarket.iActiveCivID).getNumOfProvinces(); i++) {
                           if (Game.getProvince(Game.getCiv(InGame_GoodsMarket.iActiveCivID).getProvinceID(i)).getResourceID() == this.getCurrent()) {
                              numOfProvinces++;
                           }
                        }

                        nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Provinces") + ": ", CFG.FONT_REGULAR_SMALL));
                        nData.add(new MenuElement_HoverElement_Type_Text("" + numOfProvinces, CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD));
                        nData.add(new MenuElement_HoverElement_Type_Image(Images.provinces, CFG.PADDING, 0));
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        this.menuElementHover = new MenuElement_Hover(nElements);
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
               buttonX += menuElements.get(menuElements.size() - 1).getWidth();
               menuElements.add(
                  new Text_StaticGoods_ProgressBar(
                     "" + CFG.getPrecision2(Math.min(100.0F, lMarketShare.get(toAddID)), lMarketShare.get(toAddID) < 1.0F ? 100 : 10) + "%",
                     CFG.FONT_REGULAR_SMALL,
                     -1,
                     buttonX,
                     buttonY,
                     ixx,
                     maxGoodsH,
                     Math.min(1.0F, lMarketShare.get(toAddID) / 100.0F),
                     lResources.get(toAddID),
                     true
                  ) {
                     @Override
                     public void actionElement() {
                        InGame_Goods_LargestProducers.RESOURCE_ID = this.getCurrent();
                        InGame_Goods_LargestProducers.lTime = 0L;
                        InGame_GoodsMarket.lTime = 0L;
                        Game.menuManager.rebuildInGame_Goods_LargestProducers();
                     }

                     @Override
                     public void drawBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
                     }

                     @Override
                     public void buildElementHover() {
                        this.menuElementHover = Text_StaticBG_LargestProducer.getHoverLargestProducers(this.getCurrent(), false);
                     }
                  }
               );
               buttonX += menuElements.get(menuElements.size() - 1).getWidth();

               for (int ixxx = 0; ixxx < Game.getCivsSize(); ixxx++) {
                  production.set(ixxx, 0);
               }

               for (int ixxx = 0; ixxx < Game.getProvincesSize(); ixxx++) {
                  if (Game.getProvince(ixxx).getResourceID() == lResources.get(toAddID)
                     && !Game.getProvince(ixxx).getSeaProvince()
                     && Game.getProvince(ixxx).getCivID() > 0) {
                     production.set(
                        Game.getProvince(ixxx).getCivID(),
                        production.get(Game.getProvince(ixxx).getCivID()) + (int)(ResourcesManager.getProducedGoods(ixxx) * 100.0F)
                     );
                  }
               }

               int civProducerPosition = 1;

               for (int ixxxx = 0; ixxxx < Game.getCivsSize(); ixxxx++) {
                  if (production.get(iActiveCivID) < production.get(ixxxx)) {
                     civProducerPosition++;
                  }
               }

               menuElements.add(
                  new TextIcon_Resource2(
                     "#" + civProducerPosition, lResources.get(toAddID), paddingLeft, buttonY, maxGoodsW, maxGoodsH, menuWidth - paddingLeft * 2
                  ) {
                     boolean numberOne = false;

                     @Override
                     public void actionElement() {
                        InGame_GoodsMarket_Resource.iResourceID = this.getCurrent();
                        InGame_GoodsMarket_Resource.iActiveCivID = InGame_GoodsMarket.iActiveCivID;
                        Game.menuManager.rebuildInGame_GoodsMarketResource();
                        InGame_GoodsMarket_Resource.lTime = 0L;
                     }

                     @Override
                     public void setCurrent(int nCurrent) {
                        this.numberOne = true;
                     }

                     @Override
                     protected Color getColor(boolean isActive) {
                        return this.numberOne ? Colors.HOVER_GOLD : super.getColor(isActive);
                     }
                  }
               );
               if (civProducerPosition == 1) {
                  menuElements.get(menuElements.size() - 1).setCurrent(1);
               }

               buttonY += maxGoodsH + CFG.PADDING;
            }

            lResources.remove(toAddID);
            lIncome.remove(toAddID);
            lProduction.remove(toAddID);
            lMarketShare.remove(toAddID);
            lProductionEfficiency.remove(toAddID);
            lProvinces.remove(toAddID);
         }

         production.clear();
      }

      int menuHeight = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - CFG.PADDING * 3);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, menuHeight)));
      this.initMenu(new MenuTitleIMG_FlagCenter(Game.lang.get("ProducedGoods"), Game.getCiv(iActiveCivID).getCivName(), false, false, Images.title500) {
         @Override
         public int getFlagCivID() {
            return InGame_GoodsMarket.iActiveCivID;
         }

         @Override
         public long getTime() {
            return InGame_GoodsMarket.lTime2;
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
      ImageManager.getImage(Images.goodsOver)
         .draw2(
            oSB,
            this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.goodsOver).getWidth() / 2 + iTranslateX,
            this.getPosY() + iTranslateY,
            this.getWidth(),
            Math.min(this.getHeight(), ImageManager.getImage(Images.goodsOver).getHeight())
         );
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public void setVisible(boolean visible) {
      super.setVisible(visible);
      lTime = CFG.currentTimeMillis;
      lTime2 = lTime;
   }
}
