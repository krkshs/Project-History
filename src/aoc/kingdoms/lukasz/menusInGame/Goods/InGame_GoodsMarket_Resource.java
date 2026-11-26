package aoc.kingdoms.lukasz.menusInGame.Goods;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.ResourcesManager;
import aoc.kingdoms.lukasz.map.province.ProvinceBorderManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG_FlagCenter;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsBudget_Right2;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Empty;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_TitleBlueSort;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_CourtOptions2;
import aoc.kingdoms.lukasz.menusInGame.Province.InGame_ProvinceInfo;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_GoodsMarket_Resource extends Menu {
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static long lTime2 = 0L;
   public static int iActiveCivID;
   public static int iResourceID;
   public static int iSortID = 0;

   public InGame_GoodsMarket_Resource() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = Images.boxTitleBORDERWIDTH + CFG.PADDING;
      int menuWidth = ImageManager.getImage(Images.insideTop500).getWidth();
      int menuX = InGame_CourtOptions2.getOtherMenuPosX_2();
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title500).getHeight();
      int buttonYPadding = CFG.PADDING;
      int buttonY = 0;
      int buttonX = Images.boxTitleBORDERWIDTH;
      int r0W = (int)((menuWidth - Images.boxTitleBORDERWIDTH * 2) * 0.4F);
      int r1W = (int)((menuWidth - Images.boxTitleBORDERWIDTH * 2) * 0.2F);
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
               if (InGame_GoodsMarket_Resource.iSortID == 0) {
                  InGame_GoodsMarket_Resource.iSortID = 1;
               } else {
                  InGame_GoodsMarket_Resource.iSortID = 0;
               }

               Game.menuManager.rebuildInGame_GoodsMarketResource();
               InGame_GoodsMarket_Resource.lTime = 0L;
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
            Game.lang.get("ProductionEfficiency"),
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
               if (InGame_GoodsMarket_Resource.iSortID == 2) {
                  InGame_GoodsMarket_Resource.iSortID = 3;
               } else {
                  InGame_GoodsMarket_Resource.iSortID = 2;
               }

               Game.menuManager.rebuildInGame_GoodsMarketResource();
               InGame_GoodsMarket_Resource.lTime = 0L;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("SortBy") + ": "));
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("ProductionEfficiency"), Colors.HOVER_GOLD));
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
               if (InGame_GoodsMarket_Resource.iSortID == 4) {
                  InGame_GoodsMarket_Resource.iSortID = 5;
               } else {
                  InGame_GoodsMarket_Resource.iSortID = 4;
               }

               Game.menuManager.rebuildInGame_GoodsMarketResource();
               InGame_GoodsMarket_Resource.lTime = 0L;
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
               if (InGame_GoodsMarket_Resource.iSortID == 6) {
                  InGame_GoodsMarket_Resource.iSortID = 7;
               } else {
                  InGame_GoodsMarket_Resource.iSortID = 6;
               }

               Game.menuManager.rebuildInGame_GoodsMarketResource();
               InGame_GoodsMarket_Resource.lTime = 0L;
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
      int buttonH = CFG.isDesktop() ? CFG.BUTTON_HEIGHT3 : CFG.BUTTON_HEIGHT2;
      r0W = (int)((menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 5) * 0.4F);
      r1W = (int)((menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 5) * 0.2F);
      List<Integer> tProvinces = new ArrayList<>();

      for (int i = 0; i < Game.getCiv(iActiveCivID).getNumOfProvinces(); i++) {
         if (Game.getProvince(Game.getCiv(iActiveCivID).getProvinceID(i)).getResourceID() == iResourceID) {
            tProvinces.add(Game.getCiv(iActiveCivID).getProvinceID(i));
         }
      }

      int tID = 1;
      if (tProvinces.size() <= 0) {
         menuElements.add(
            new Text_StaticBG(Game.lang.get("None"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT2) {
               @Override
               public void actionElement() {
                  Game.menuManager.rebuildInGame_GoodsMarket();
                  InGame_GoodsMarket.lTime = 0L;
               }
            }
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      } else {
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
                  if (ResourcesManager.getProductionEfficiency(tProvinces.get(oxx)) > ResourcesManager.getProductionEfficiency(tProvinces.get(toAddID))) {
                     toAddID = oxx;
                  }
               }
            } else if (iSortID == 3) {
               for (int oxxx = 1; oxxx < tProvinces.size(); oxxx++) {
                  if (ResourcesManager.getProductionEfficiency(tProvinces.get(oxxx)) < ResourcesManager.getProductionEfficiency(tProvinces.get(toAddID))) {
                     toAddID = oxxx;
                  }
               }
            } else if (iSortID == 4) {
               for (int oxxxx = 1; oxxxx < tProvinces.size(); oxxxx++) {
                  if (ResourcesManager.getProducedGoods(tProvinces.get(oxxxx)) > ResourcesManager.getProducedGoods(tProvinces.get(toAddID))) {
                     toAddID = oxxxx;
                  }
               }
            } else if (iSortID == 5) {
               for (int oxxxxx = 1; oxxxxx < tProvinces.size(); oxxxxx++) {
                  if (ResourcesManager.getProducedGoods(tProvinces.get(oxxxxx)) < ResourcesManager.getProducedGoods(tProvinces.get(toAddID))) {
                     toAddID = oxxxxx;
                  }
               }
            } else if (iSortID == 6) {
               for (int oxxxxxx = 1; oxxxxxx < tProvinces.size(); oxxxxxx++) {
                  if (ResourcesManager.getMonthlyIncome(tProvinces.get(oxxxxxx)) > ResourcesManager.getMonthlyIncome(tProvinces.get(toAddID))) {
                     toAddID = oxxxxxx;
                  }
               }
            } else if (iSortID == 7) {
               for (int oxxxxxxx = 1; oxxxxxxx < tProvinces.size(); oxxxxxxx++) {
                  if (ResourcesManager.getMonthlyIncome(tProvinces.get(oxxxxxxx)) < ResourcesManager.getMonthlyIncome(tProvinces.get(toAddID))) {
                     toAddID = oxxxxxxx;
                  }
               }
            }

            menuElements.add(
               new Text_StaticBG_ID(
                  "" + tID++ + ". " + Game.getProvince(tProvinces.get(toAddID)).getProvinceName(),
                  Game.getProvince(tProvinces.get(toAddID)).isCapital ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL,
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
                        Game.menuManager.setVisibleInGame_GoodsMarket(false);
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
            menuElements.add(
               new Text_StaticBG_ID(
                  "" + CFG.getPrecision2(ResourcesManager.getProductionEfficiency(tProvinces.get(toAddID)) * 100.0F, 10) + "%",
                  CFG.FONT_REGULAR_SMALL,
                  -1,
                  buttonX,
                  buttonY,
                  r1W,
                  buttonH,
                  tProvinces.get(toAddID)
               ) {
                  @Override
                  public void actionElement() {
                     Game.menuManager.rebuildInGame_GoodsMarket();
                     InGame_GoodsMarket.lTime = 0L;
                  }

                  @Override
                  public void buildElementHover() {
                     List<MenuElement_HoverElement> nElements = new ArrayList<>();
                     List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                     nData.add(
                        new MenuElement_HoverElement_Type_Button_TextBonus(
                           Game.lang.get("ProductionEfficiency") + ": ",
                           this.getText(),
                           Images.goods,
                           CFG.FONT_REGULAR_SMALL,
                           CFG.FONT_BOLD_SMALL,
                           Colors.HOVER_LEFT,
                           Colors.HOVER_GOLD
                        )
                     );
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     nData.add(new MenuElement_HoverElement_Type_Empty());
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Back"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
                     nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.goods, CFG.PADDING, 0));
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     this.menuElementHover = new MenuElement_Hover(nElements, true);
                  }
               }
            );
            buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
            menuElements.add(
               new Text_StaticBG_ID(
                  "" + CFG.getPrecision2(ResourcesManager.getProducedGoods(tProvinces.get(toAddID)), 10),
                  CFG.FONT_REGULAR_SMALL,
                  -1,
                  buttonX,
                  buttonY,
                  r1W,
                  buttonH,
                  tProvinces.get(toAddID)
               ) {
                  @Override
                  public void actionElement() {
                     Game.menuManager.rebuildInGame_GoodsMarket();
                     InGame_GoodsMarket.lTime = 0L;
                  }

                  @Override
                  public void buildElementHover() {
                     List<MenuElement_HoverElement> nElements = new ArrayList<>();
                     List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                     nData.add(
                        new MenuElement_HoverElement_Type_Button_TextBonus(
                           Game.lang.get("ProducedGoods") + ": ",
                           this.getText(),
                           Images.goods,
                           CFG.FONT_REGULAR_SMALL,
                           CFG.FONT_BOLD_SMALL,
                           Colors.HOVER_LEFT,
                           Colors.HOVER_GOLD
                        )
                     );
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     nData.add(new MenuElement_HoverElement_Type_Empty());
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Back"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
                     nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.goods, CFG.PADDING, 0));
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     this.menuElementHover = new MenuElement_Hover(nElements, true);
                  }
               }
            );
            buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
            float fGold = ResourcesManager.getMonthlyIncome(tProvinces.get(toAddID));
            menuElements.add(
               new ButtonStatsBudget_Right2((fGold > 0.0F ? "+" : "") + CFG.getPrecision2(fGold, 100), buttonX, buttonY, r1W, buttonH, tProvinces.get(toAddID)) {
                  @Override
                  public void actionElement() {
                     Game.menuManager.rebuildInGame_GoodsMarket();
                     InGame_GoodsMarket.lTime = 0L;
                  }

                  @Override
                  public void buildElementHover() {
                     List<MenuElement_HoverElement> nElements = new ArrayList<>();
                     List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                     nData.add(
                        new MenuElement_HoverElement_Type_Button_TextBonus(
                           Game.lang.get("IncomeProduction") + ": ",
                           this.getText(),
                           Images.gold,
                           CFG.FONT_REGULAR_SMALL,
                           CFG.FONT_BOLD_SMALL,
                           Colors.HOVER_LEFT,
                           Colors.COLOR_TEXT_MODIFIER_POSITIVE
                        )
                     );
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     nData.add(new MenuElement_HoverElement_Type_Empty());
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Back"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
                     nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.goods, CFG.PADDING, 0));
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     this.menuElementHover = new MenuElement_Hover(nElements, true);
                  }
               }
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
            tProvinces.remove(toAddID);
         }
      }

      int menuHeight = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - CFG.PADDING * 3);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, menuHeight)));
      this.initMenu(
         new MenuTitleIMG_FlagCenter(ResourcesManager.getResourceName(iResourceID), Game.getCiv(iActiveCivID).getCivName(), false, false, Images.title500) {
            @Override
            public int getFlagCivID() {
               return InGame_GoodsMarket_Resource.iActiveCivID;
            }

            @Override
            public long getTime() {
               return InGame_GoodsMarket_Resource.lTime2;
            }
         }, menuX, menuY, menuWidth, menuHeight, menuElements, false, true
      );
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
