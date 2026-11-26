package aoc.kingdoms.lukasz.menusInGame.Goods;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.ResourcesManager;
import aoc.kingdoms.lukasz.map.province.ProvinceBorderManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG_DoubleText;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID_Flag;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_TitleBlueSort;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_CourtOptions2;
import aoc.kingdoms.lukasz.menusInGame.Province.InGame_ProvinceInfo;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_Goods_Provinces extends Menu {
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static int RESOURCE_ID = 0;

   public InGame_Goods_Provinces() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = Images.boxTitleBORDERWIDTH + CFG.PADDING * 2;
      int menuWidth = ImageManager.getImage(Images.title928).getWidth();
      int menuX = InGame_CourtOptions2.getOtherMenuPosX_2();
      int menuY = ImageManager.getImage(Images.topStats).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title1Red).getHeight();
      int buttonYPadding = CFG.PADDING * 2;
      int buttonY = 0;
      int buttonX = Images.boxTitleBORDERWIDTH;
      int buttonResH = ImageManager.getImage(Images.flagOverDefault).getHeight() + CFG.TEXT_HEIGHT + CFG.PADDING * 3;
      int elementWidth = menuWidth - paddingLeft * 2 - CFG.PADDING * 6;
      int elementWidth2 = menuWidth - paddingLeft * 2;
      InGame_Goods.inGoods = false;
      menuElements.add(
         new Text_TitleBlueSort(false, false, Game.lang.get("Name"), -1, buttonX, buttonY, (int)(elementWidth2 * 0.3F), CFG.TEXT_HEIGHT + CFG.PADDING * 6) {
            @Override
            public void drawLines(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            }
         }
      );
      buttonX += menuElements.get(menuElements.size() - 1).getWidth();
      menuElements.add(
         new Text_TitleBlueSort(false, false, Game.lang.get("Economy"), -1, buttonX, buttonY, (int)(elementWidth2 * 0.175F), CFG.TEXT_HEIGHT + CFG.PADDING * 6) {
            @Override
            public void drawLines(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            }
         }
      );
      buttonX += menuElements.get(menuElements.size() - 1).getWidth();
      menuElements.add(
         new Text_TitleBlueSort(
            false, false, Game.lang.get("ProductionEfficiency"), -1, buttonX, buttonY, (int)(elementWidth2 * 0.175F), CFG.TEXT_HEIGHT + CFG.PADDING * 6
         ) {
            @Override
            public void drawLines(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            }
         }
      );
      buttonX += menuElements.get(menuElements.size() - 1).getWidth();
      menuElements.add(
         new Text_TitleBlueSort(
            true, false, Game.lang.get("Production"), -1, buttonX, buttonY, (int)(elementWidth2 * 0.175F), CFG.TEXT_HEIGHT + CFG.PADDING * 6
         ) {
            @Override
            public void drawLines(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            }
         }
      );
      buttonX += menuElements.get(menuElements.size() - 1).getWidth();
      menuElements.add(
         new Text_TitleBlueSort(
            false,
            false,
            Game.lang.get("MonthlyIncome"),
            -1,
            buttonX,
            buttonY,
            menuWidth - buttonX - Images.boxTitleBORDERWIDTH,
            CFG.TEXT_HEIGHT + CFG.PADDING * 6
         ) {
            @Override
            public void drawLines(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      buttonX = paddingLeft;
      int buttonH = CFG.isDesktop() ? CFG.BUTTON_HEIGHT3 : CFG.BUTTON_HEIGHT2;
      List<Integer> provinceID = new ArrayList<>();
      List<Float> production = new ArrayList<>();

      for (int i = 0; i < Game.getProvincesSize(); i++) {
         if (Game.getProvince(i).getResourceID() == RESOURCE_ID) {
            provinceID.add(i);
            production.add(ResourcesManager.getProducedGoods(i));
         }
      }

      int tID = 1;

      while (production.size() > 0) {
         int toAddID = 0;

         for (int ix = production.size() - 1; ix > 0; ix--) {
            if (production.get(toAddID) < production.get(ix)) {
               toAddID = ix;
            }
         }

         menuElements.add(
            new Text_StaticBG_ID_Flag(
               "" + tID++ + ". " + Game.getProvince(provinceID.get(toAddID)).getProvinceName(),
               CFG.FONT_REGULAR_SMALL,
               CFG.PADDING * 2,
               buttonX,
               buttonY,
               (int)(elementWidth * 0.3F),
               buttonH,
               provinceID.get(toAddID)
            ) {
               @Override
               public void actionElement() {
                  if (Game.iActiveProvince == this.getCurrent()) {
                     Game.menuManager.setVisibleInGame_GoodsMarket(false);
                     Game.menuManager.setVisibleInGame_Goods(false);
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
         buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         menuElements.add(
            new Text_StaticBG_ID(
               CFG.getPrecision2(Game.getProvince(provinceID.get(toAddID)).getEconomyWithBonuses(), 100),
               CFG.FONT_REGULAR_SMALL,
               -1,
               buttonX,
               buttonY,
               (int)(elementWidth * 0.175F),
               buttonH,
               provinceID.get(toAddID)
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
                  nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("LargestGoodsProducers"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
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
               "" + CFG.getPrecision2(ResourcesManager.getProductionEfficiency(provinceID.get(toAddID)) * 100.0F, 10) + "%",
               CFG.FONT_REGULAR_SMALL,
               -1,
               buttonX,
               buttonY,
               (int)(elementWidth * 0.175F),
               buttonH,
               provinceID.get(toAddID)
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
                  nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("LargestGoodsProducers"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
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
               "" + CFG.getPrecision2(production.get(toAddID), 10),
               CFG.FONT_REGULAR_SMALL,
               -1,
               buttonX,
               buttonY,
               (int)(elementWidth * 0.175F),
               buttonH,
               provinceID.get(toAddID)
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
                  nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("LargestGoodsProducers"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.goods, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements, true);
               }
            }
         );
         buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         float fGold = Game.getProvince(provinceID.get(toAddID)).fProvinceIncomeProduction;
         menuElements.add(
            new Text_StaticBG_ID(
               "" + CFG.getPrecision2(fGold, 100), CFG.FONT_BOLD_SMALL, -1, buttonX, buttonY, (int)(elementWidth * 0.175F), buttonH, provinceID.get(toAddID)
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
                  nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("LargestGoodsProducers"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.goods, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements, true);
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
         buttonX = paddingLeft;
         production.remove(toAddID);
         provinceID.remove(toAddID);
      }

      buttonY = 0;
      int ixx = 0;

      for (int iSize = menuElements.size(); ixx < iSize; ixx++) {
         if (buttonY < menuElements.get(ixx).getPosY() + menuElements.get(ixx).getHeight() + CFG.PADDING * 2) {
            buttonY = menuElements.get(ixx).getPosY() + menuElements.get(ixx).getHeight() + CFG.PADDING * 2;
         }
      }

      ixx = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - CFG.PADDING * 3);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, ixx)));
      this.initMenu(new MenuTitleIMG_DoubleText("", Game.lang.get("Year") + ": " + Game_Calendar.currentYear, false, false, Images.title928) {
         @Override
         public long getTime() {
            return InGame_Goods_Provinces.lTime;
         }
      }, CFG.GAME_WIDTH / 2 - menuWidth / 2, menuY, menuWidth, ixx, menuElements, false, true);
      this.drawScrollPositionAlways = false;
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      float fA = 1.0F;
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_BG.r, Colors.COLOR_GRADIENT_BG.g, Colors.COLOR_GRADIENT_BG.b, 0.2F * fA));
      Images.pix.draw2(oSB, iTranslateX, iTranslateY, CFG.GAME_WIDTH, CFG.GAME_HEIGHT);
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_BG.r, Colors.COLOR_GRADIENT_BG.g, Colors.COLOR_GRADIENT_BG.b, 0.65F * fA));
      Images.gradientFull.draw(oSB, iTranslateX, iTranslateY, CFG.GAME_WIDTH, CFG.GAME_HEIGHT);
      oSB.setColor(Color.WHITE);
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
         Images.insideTop928,
         Images.insideBot928
      );
      oSB.setColor(Colors.COLOR_GRADIENT);
      Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), false, true);
      oSB.setColor(Color.WHITE);
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public void updateLanguage() {
      super.updateLanguage();
      this.getTitle().setText(Game.lang.get("Provinces") + ": " + ResourcesManager.lResources.get(RESOURCE_ID).Name);
   }

   @Override
   public void setVisible(boolean visible) {
      super.setVisible(visible);
      lTime = CFG.currentTimeMillis;
   }

   @Override
   public void onHovered() {
      super.onHovered();
      Game.menuManager.setOrderOfMenu_InGameGoods();
   }

   @Override
   public void actionCloseMenu() {
      super.actionCloseMenu();
      Game.menuManager.setVisibleInGame_Goods(false);
   }
}
