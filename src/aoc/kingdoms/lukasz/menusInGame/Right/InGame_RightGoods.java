package aoc.kingdoms.lukasz.menusInGame.Right;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.ResourcesManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Active_Click;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Bonuses_Style;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Resource2;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonusResource;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG_Clear;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text_Desc;
import aoc.kingdoms.lukasz.menu_element.pieChart.PieChart_Data;
import aoc.kingdoms.lukasz.menu_element.pieChart.PieChart_Value;
import aoc.kingdoms.lukasz.menu_element.pieChart.PieChart_WithStatsResources;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_TitleBlueSort;
import aoc.kingdoms.lukasz.menusInGame.Goods.InGame_Goods;
import aoc.kingdoms.lukasz.menusInGame.Province.InGame_ProvinceInfo;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_RightGoods extends Menu {
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static long lTime2 = 0L;
   public static int iActiveResID = -1;
   public static int iSortID = 0;

   public InGame_RightGoods() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING;
      int titleHeight = ImageManager.getImage(Images.title2).getHeight();
      int extraX = Images.boxTitleBORDERWIDTH;
      int menuWidth = ImageManager.getImage(Images.title1Red).getWidth();
      int menuX = CFG.GAME_WIDTH - menuWidth;
      int menuY = ImageManager.getImage(Images.topStats).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title1Red).getHeight();
      int buttonYPadding = CFG.PADDING;
      int buttonY = CFG.PADDING;
      int buttonX = paddingLeft + Images.boxTitleBORDERWIDTH;
      int buttonH = CFG.isDesktop() ? CFG.BUTTON_HEIGHT3 : CFG.BUTTON_HEIGHT2;
      int r0W = (int)((menuWidth - Images.boxTitleBORDERWIDTH) * 0.5F);
      int r1W = (int)((menuWidth - Images.boxTitleBORDERWIDTH) * 0.25F);
      int r2W = (int)((menuWidth - Images.boxTitleBORDERWIDTH) * 0.25F);
      int r0W2 = (int)((menuWidth - Images.boxTitleBORDERWIDTH - CFG.PADDING * 4) * 0.5F);
      int r1W2 = (int)((menuWidth - Images.boxTitleBORDERWIDTH - CFG.PADDING * 4) * 0.25F);
      int r2W2 = (int)((menuWidth - Images.boxTitleBORDERWIDTH - CFG.PADDING * 4) * 0.25F);
      List<Integer> tResources = new ArrayList<>();
      List<Integer> tProvinces = new ArrayList<>();
      int tMaxWidthIcon = 0;
      int i = 0;

      for (int iSize = ResourcesManager.resourceImages.size(); i < iSize; i++) {
         if (tMaxWidthIcon < ResourcesManager.resourceImages.get(i).getWidth()) {
            tMaxWidthIcon = ResourcesManager.resourceImages.get(i).getWidth();
         }
      }

      for (int ix = 0; ix < ResourcesManager.iResourcesSize; ix++) {
         tResources.add(ix);
         tProvinces.add(0);
      }

      for (int ix = 0; ix < Game.getProvincesSize(); ix++) {
         if (Game.getProvince(ix).getWasteland() < 0 && Game.getProvince(ix).getResourceID() >= 0) {
            tProvinces.set(Game.getProvince(ix).getResourceID(), tProvinces.get(Game.getProvince(ix).getResourceID()) + 1);
         }
      }

      for (int ixx = tResources.size() - 1; ixx >= 0; ixx--) {
         if (ResourcesManager.lResources.get(tResources.get(ixx)).RequiredTechID >= 0) {
            boolean tRemove = true;

            for (int a = 1; a < Game.getCivsSize(); a++) {
               if (Game.getCiv(a).getTechResearched(ResourcesManager.lResources.get(tResources.get(ixx)).RequiredTechID)) {
                  tRemove = false;
                  break;
               }
            }

            if (tRemove) {
               tResources.remove(ixx);
               tProvinces.remove(ixx);
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

      for (int ixxx = tResources.size() - 1; ixxx >= 0; ixxx--) {
         if (tResources.get(ixxx).intValue() > 0.001F) {
            nPieChartData.addPieChartValues(new PieChart_Value(tResources.get(ixxx), tProvinces.get(ixxx).intValue()));
         }
      }

      int pieDim = CFG.BUTTON_HEIGHT3 * 2 + CFG.PADDING;
      menuElements.add(
         new PieChart_WithStatsResources(buttonX, buttonY + CFG.PADDING, pieDim - CFG.PADDING * 2, pieDim - CFG.PADDING * 2, nPieChartData, null) {
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
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Resources") + ": ", Colors.HOVER_LEFT));
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG_Clear(Game.lang.get("Provinces") + " %", Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.provinces, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();

               for (int ixxxx = 0; ixxxx < this.pieChartData.getPieChartValuesSize() && ixxxx < 10; ixxxx++) {
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonusResource(
                        ResourcesManager.getResourceName(this.pieChartData.getCivID(ixxxx)) + ": ",
                        CFG.getPrecision2(this.pieChartData.getPieChartValue(ixxxx).getPercentage(), 10) + "%",
                        this.pieChartData.getCivID(ixxxx),
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
            Game.lang.get("Resources") + ": ",
            "" + ResourcesManager.iResourcesSize,
            Images.goods,
            nX,
            nY,
            nW,
            CFG.BUTTON_HEIGHT3,
            ImageManager.getImage(Images.battleWidth).getWidth()
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
               if (InGame_RightGoods.iSortID == 0) {
                  InGame_RightGoods.iSortID = 1;
               } else {
                  InGame_RightGoods.iSortID = 0;
               }

               Game.menuManager.rebuildInGame_RightGoods();
               InGame_RightGoods.lTime = 0L;
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
               if (InGame_RightGoods.iSortID == 2) {
                  InGame_RightGoods.iSortID = 3;
               } else {
                  InGame_RightGoods.iSortID = 2;
               }

               Game.menuManager.rebuildInGame_RightGoods();
               InGame_RightGoods.lTime = 0L;
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
            Game.lang.get("Price"),
            -1,
            buttonX,
            buttonY,
            r2W,
            CFG.TEXT_HEIGHT + CFG.PADDING * 6,
            CFG.FONT_BOLD_SMALL
         ) {
            @Override
            public void drawLines(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            }

            @Override
            public void actionElement() {
               if (InGame_RightGoods.iSortID == 4) {
                  InGame_RightGoods.iSortID = 5;
               } else {
                  InGame_RightGoods.iSortID = 4;
               }

               Game.menuManager.rebuildInGame_RightGoods();
               InGame_RightGoods.lTime = 0L;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("SortBy") + ": "));
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Price"), Colors.HOVER_GOLD));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;

      try {
         while (tResources.size() > 0) {
            int toAddID = 0;
            if (iSortID == 0) {
               for (int o = 1; o < tResources.size(); o++) {
                  if (CFG.compareAlphabetic_TwoString(
                     ResourcesManager.getResourceName(tResources.get(toAddID)), ResourcesManager.getResourceName(tResources.get(o))
                  )) {
                     toAddID = o;
                  }
               }
            } else if (iSortID == 1) {
               for (int ox = 1; ox < tResources.size(); ox++) {
                  if (CFG.compareAlphabetic_TwoString(
                     ResourcesManager.getResourceName(tResources.get(ox)), ResourcesManager.getResourceName(tResources.get(toAddID))
                  )) {
                     toAddID = ox;
                  }
               }
            } else if (iSortID == 2) {
               for (int oxx = 1; oxx < tResources.size(); oxx++) {
                  if (tProvinces.get(toAddID) < tProvinces.get(oxx)) {
                     toAddID = oxx;
                  }
               }
            } else if (iSortID == 3) {
               for (int oxxx = 1; oxxx < tResources.size(); oxxx++) {
                  if (tProvinces.get(toAddID) > tProvinces.get(oxxx)) {
                     toAddID = oxxx;
                  }
               }
            } else if (iSortID == 4) {
               for (int oxxxx = 1; oxxxx < tResources.size(); oxxxx++) {
                  if (ResourcesManager.getPrice(tResources.get(toAddID)) < ResourcesManager.getPrice(tResources.get(oxxxx))) {
                     toAddID = oxxxx;
                  }
               }
            } else if (iSortID == 5) {
               for (int oxxxxx = 1; oxxxxx < tResources.size(); oxxxxx++) {
                  if (ResourcesManager.getPrice(tResources.get(toAddID)) > ResourcesManager.getPrice(tResources.get(oxxxxx))) {
                     toAddID = oxxxxx;
                  }
               }
            }

            buttonX = Images.boxTitleBORDERWIDTH + CFG.PADDING;
            menuElements.add(
               new ButtonStatsRectIMG_Resource2(
                  ResourcesManager.getResourceName(tResources.get(toAddID)),
                  tResources.get(toAddID),
                  buttonX,
                  buttonY,
                  r0W2,
                  buttonH,
                  tMaxWidthIcon,
                  tResources.get(toAddID)
               ) {
                  @Override
                  public void actionElement() {
                     InGame_RightGoods.iActiveResID = this.getCurrent();
                     Game.menuManager.rebuildInGame_RightGoodsProvinces();
                  }

                  @Override
                  public void buildElementHover() {
                     List<MenuElement_HoverElement> nElements = new ArrayList<>();
                     List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                     nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Provinces") + ": ", CFG.FONT_BOLD));
                     nData.add(
                        new MenuElement_HoverElement_Type_TextTitle_BG_Clear(
                           ResourcesManager.lResources.get(this.getCurrent()).Name, CFG.FONT_BOLD, Colors.HOVER_GOLD
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
               new Text_StaticBG("" + CFG.getNumberWithSpaces("" + tProvinces.get(toAddID)), CFG.FONT_REGULAR_SMALL, -1, buttonX, buttonY, r1W2, buttonH) {
                  @Override
                  public void buildElementHover() {
                     List<MenuElement_HoverElement> nElements = new ArrayList<>();
                     List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                     nData.add(
                        new MenuElement_HoverElement_Type_Button_TextBonus(
                           Game.lang.get("Provinces") + ": ",
                           this.getText(),
                           Images.provinces,
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
               new Text_StaticBG(
                  CFG.getPrecision2(ResourcesManager.getPrice(tResources.get(toAddID)), 100), CFG.FONT_BOLD_SMALL, -1, buttonX, buttonY, r2W2, buttonH
               ) {
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
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
            tResources.remove(toAddID);
            tProvinces.remove(toAddID);
         }
      } catch (Exception var28) {
         CFG.exceptionStack(var28);
      }

      buttonY = 0;
      int ixxxx = 0;

      for (int iSizex = menuElements.size(); ixxxx < iSizex; ixxxx++) {
         if (buttonY < menuElements.get(ixxxx).getPosY() + menuElements.get(ixxxx).getHeight() + CFG.PADDING) {
            buttonY = menuElements.get(ixxxx).getPosY() + menuElements.get(ixxxx).getHeight() + CFG.PADDING;
         }
      }

      ixxxx = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - (CFG.GAME_HEIGHT - Game.menuManager.getInGame_MapModesPosY() + CFG.PADDING * 3));
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, ixxxx)));
      this.initMenu(new MenuTitleIMG(Game.lang.get("Resources"), false, false, Images.title600) {
         @Override
         public void draw(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, Status titleStatus) {
            ImageManager.getImage(this.imageID).draw2(oSB, nPosX, nPosY - this.getHeight(), nWidth, this.getHeight());
            this.drawGradient(oSB, nPosX, nPosY, nWidth, titleStatus);
            this.drawText(oSB, nPosX + Images.boxTitleBORDERWIDTH / 2, nPosY, nWidth, titleStatus);
         }

         @Override
         public long getTime() {
            return InGame_RightGoods.lTime2;
         }
      }, menuX, menuY, menuWidth, ixxxx, menuElements, false, true);
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
      return super.getVisible() && !Game.menuManager.getVisibleInGame_CivBonuses();
   }
}
