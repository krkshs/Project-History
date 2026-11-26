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
import aoc.kingdoms.lukasz.menu_element.button.ButtonFlag2;
import aoc.kingdoms.lukasz.menu_element.graph.Graph_Vertical;
import aoc.kingdoms.lukasz.menu_element.graph.Graph_Vertical_Data_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Empty;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextIcon_Resource;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_LargestProducer;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_TitleBlueSort;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_CourtOptions2;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_Goods_LargestProducers extends Menu {
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static long lTime2 = 0L;
   public static int RESOURCE_ID = 0;

   public InGame_Goods_LargestProducers() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = Images.boxTitleBORDERWIDTH + CFG.PADDING * 2;
      int menuWidth = ImageManager.getImage(Images.title928).getWidth();
      int menuX = InGame_CourtOptions2.getOtherMenuPosX_2();
      int menuY = ImageManager.getImage(Images.topStats).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title1Red).getHeight();
      int buttonYPadding = CFG.PADDING * 2;
      int buttonY = CFG.PADDING;
      int buttonX = Images.boxTitleBORDERWIDTH;
      int buttonResW = (int)(CFG.BUTTON_WIDTH * 1.25F);
      int buttonResH = ImageManager.getImage(Images.flagOverDefault).getHeight() + CFG.TEXT_HEIGHT + CFG.PADDING * 3;
      int elementWidth = menuWidth - paddingLeft * 2 - buttonResW - ImageManager.getImage(Images.flagOverDefault).getWidth() - CFG.PADDING * 6;
      int elementWidth2 = menuWidth - paddingLeft * 2 - buttonResW - ImageManager.getImage(Images.flagOverDefault).getWidth();
      InGame_Goods.inGoods = false;
      Graph_Vertical graphVertical = new Graph_Vertical(
         Graph_Vertical_Data_Type.RESOURCE_PRODUCTION,
         Game.lang.get("Civilizations"),
         Game.lang.get("Civilizations"),
         paddingLeft,
         buttonY,
         menuWidth - paddingLeft * 2,
         (int)(CFG.BUTTON_HEIGHT * 3.0F),
         true
      ) {};
      menuElements.add(graphVertical);
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new Text_TitleBlueSort(false, false, Game.lang.get("Name"), -1, buttonX, buttonY, buttonResW + CFG.PADDING * 3, CFG.TEXT_HEIGHT + CFG.PADDING * 6) {
            @Override
            public void drawLines(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            }
         }
      );
      buttonX += menuElements.get(menuElements.size() - 1).getWidth();
      menuElements.add(
         new Text_TitleBlueSort(false, false, Game.lang.get("Provinces"), -1, buttonX, buttonY, (int)(elementWidth2 * 0.25F), CFG.TEXT_HEIGHT + CFG.PADDING * 6) {
            @Override
            public void drawLines(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            }
         }
      );
      buttonX += menuElements.get(menuElements.size() - 1).getWidth();
      menuElements.add(
         new Text_TitleBlueSort(false, false, Game.lang.get("Economy"), -1, buttonX, buttonY, (int)(elementWidth2 * 0.2F), CFG.TEXT_HEIGHT + CFG.PADDING * 6) {
            @Override
            public void drawLines(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Average") + ": " + Game.lang.get("Economy"), Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle(Images.goods, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      buttonX += menuElements.get(menuElements.size() - 1).getWidth();
      menuElements.add(
         new Text_TitleBlueSort(
            false, false, Game.lang.get("ProductionEfficiency"), -1, buttonX, buttonY, (int)(elementWidth2 * 0.2F), CFG.TEXT_HEIGHT + CFG.PADDING * 6
         ) {
            @Override
            public void drawLines(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(
                  new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Average") + ": " + Game.lang.get("ProductionEfficiency"), Colors.HOVER_GOLD)
               );
               nData.add(new MenuElement_HoverElement_Type_ImageTitle(Images.goods, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      buttonX += menuElements.get(menuElements.size() - 1).getWidth();
      menuElements.add(
         new Text_TitleBlueSort(
            true,
            false,
            Game.lang.get("LargestProducer"),
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
      List<Integer> productionCivID = new ArrayList<>();
      List<Integer> production = new ArrayList<>();
      List<Integer> productionProvinces = new ArrayList<>();
      List<Float> productionEconomy = new ArrayList<>();
      List<Float> productionProductionEfficiency = new ArrayList<>();

      for (int i = 0; i < Game.getCivsSize(); i++) {
         production.add(0);
         productionCivID.add(i);
         productionProvinces.add(0);
         productionEconomy.add(0.0F);
         productionProductionEfficiency.add(0.0F);
      }

      for (int i = 0; i < Game.getProvincesSize(); i++) {
         if (Game.getProvince(i).getResourceID() == RESOURCE_ID && !Game.getProvince(i).getSeaProvince() && Game.getProvince(i).getCivID() > 0) {
            production.set(
               Game.getProvince(i).getCivID(), production.get(Game.getProvince(i).getCivID()) + (int)(ResourcesManager.getProducedGoods(i) * 100.0F)
            );
            productionProvinces.set(Game.getProvince(i).getCivID(), productionProvinces.get(Game.getProvince(i).getCivID()) + 1);
            productionEconomy.set(
               Game.getProvince(i).getCivID(), productionEconomy.get(Game.getProvince(i).getCivID()) + Game.getProvince(i).getEconomyWithBonuses()
            );
            productionProductionEfficiency.set(
               Game.getProvince(i).getCivID(), productionProductionEfficiency.get(Game.getProvince(i).getCivID()) + ResourcesManager.getProductionEfficiency(i)
            );
         }
      }

      for (int ix = production.size() - 1; ix >= 0; ix--) {
         if (production.get(ix) == 0) {
            production.remove(ix);
            productionCivID.remove(ix);
            productionProvinces.remove(ix);
            productionEconomy.remove(ix);
            productionProductionEfficiency.remove(ix);
         }
      }

      int nPosition = 1;

      while (production.size() > 0) {
         int toAddID = 0;

         for (int ixx = production.size() - 1; ixx > 0; ixx--) {
            if (production.get(toAddID) < production.get(ixx)) {
               toAddID = ixx;
            }
         }

         menuElements.add(new TextIcon_Resource("#" + nPosition++, RESOURCE_ID, buttonX, buttonY, buttonResW, buttonResH) {
            @Override
            public void actionElement() {
               InGame_Goods_Provinces.RESOURCE_ID = this.getCurrent();
               InGame_Goods_Provinces.lTime = 0L;
               InGame_Goods_LargestProducers.lTime = 0L;
               Game.menuManager.rebuildInGame_Goods_Provinces();
            }
         });
         buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         menuElements.add(
            new Text_StaticBG("" + productionProvinces.get(toAddID), CFG.FONT_REGULAR, -1, buttonX, buttonY, (int)(elementWidth * 0.25F), buttonResH) {
               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Provinces") + ": "));
                  nData.add(new MenuElement_HoverElement_Type_TextTitle(this.getText(), Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle(Images.provinces, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(new MenuElement_HoverElement_Type_Empty());
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("LargestGoodsProducers"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.goods, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements, true);
               }

               @Override
               public void actionElement() {
                  Game.menuManager.rebuildInGame_Goods();
                  Game.menuManager.setVisibleInGame_Goods(true);
                  InGame_Goods.lTime = 0L;
               }
            }
         );
         buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         menuElements.add(
            new Text_StaticBG(
               "" + CFG.getPrecision2(productionEconomy.get(toAddID) / productionProvinces.get(toAddID).intValue(), 10),
               CFG.FONT_REGULAR,
               -1,
               buttonX,
               buttonY,
               (int)(elementWidth * 0.2F),
               buttonResH
            ) {
               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Average") + ": "));
                  nData.add(new MenuElement_HoverElement_Type_TextTitle(this.getText(), Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle(Game_Calendar.IMG_ECONOMY, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(new MenuElement_HoverElement_Type_Empty());
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("LargestGoodsProducers"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.goods, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements, true);
               }

               @Override
               public void actionElement() {
                  Game.menuManager.rebuildInGame_Goods();
                  Game.menuManager.setVisibleInGame_Goods(true);
                  InGame_Goods.lTime = 0L;
               }
            }
         );
         buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         menuElements.add(
            new Text_StaticBG(
               "" + CFG.getPrecision2(productionProductionEfficiency.get(toAddID) * 100.0F / productionProvinces.get(toAddID).intValue(), 10) + "%",
               CFG.FONT_REGULAR,
               -1,
               buttonX,
               buttonY,
               (int)(elementWidth * 0.2F),
               buttonResH
            ) {
               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Average") + ": "));
                  nData.add(new MenuElement_HoverElement_Type_TextTitle(this.getText(), Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle(Images.goods, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(new MenuElement_HoverElement_Type_Empty());
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("LargestGoodsProducers"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.goods, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements, true);
               }

               @Override
               public void actionElement() {
                  Game.menuManager.rebuildInGame_Goods();
                  Game.menuManager.setVisibleInGame_Goods(true);
                  InGame_Goods.lTime = 0L;
               }
            }
         );
         buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         menuElements.add(
            new Text_StaticBG_LargestProducer(
               ""
                  + CFG.getPrecision2(production.get(toAddID).intValue() / 100.0F, 10)
                  + " / "
                  + CFG.getPrecision2(ResourcesManager.worldResourcesProduced.get(RESOURCE_ID).intValue() / 100.0F, 10),
               RESOURCE_ID,
               CFG.FONT_REGULAR,
               -1,
               buttonX,
               buttonY,
               (int)(elementWidth * 0.25F),
               buttonResH
            ) {
               @Override
               public void actionElement() {
                  Game.menuManager.rebuildInGame_Goods();
                  Game.menuManager.setVisibleInGame_Goods(true);
                  InGame_Goods.lTime = 0L;
               }
            }
         );
         buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         menuElements.add(
            new Text_StaticBG_LargestProducer(
               ""
                  + CFG.getPrecision2(
                     (float)Math.min(
                        100.0,
                        Math.floor((float)production.get(toAddID).intValue() / ResourcesManager.worldResourcesProduced.get(RESOURCE_ID).intValue() * 100.0F)
                     ),
                     10
                  )
                  + "%",
               RESOURCE_ID,
               CFG.FONT_BOLD,
               -1,
               buttonX,
               buttonY,
               (int)(elementWidth * 0.1F),
               buttonResH
            ) {
               @Override
               public void actionElement() {
                  Game.menuManager.rebuildInGame_Goods();
                  Game.menuManager.setVisibleInGame_Goods(true);
                  InGame_Goods.lTime = 0L;
               }
            }
         );
         buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         menuElements.add(new ButtonFlag2(productionCivID.get(toAddID), buttonX, buttonY, true) {
            @Override
            public void actionElement() {
               if (Game.getCiv(this.getCurrent()).getCapitalProvinceID() >= 0) {
                  if (!Game.getProvince(Game.getCiv(this.getCurrent()).getCapitalProvinceID()).getDrawProvince()) {
                     Game.mapCoords.centerToProvinceID(Game.getCiv(this.getCurrent()).getCapitalProvinceID());
                  }

                  Game.setActiveProvinceID(Game.getCiv(this.getCurrent()).getCapitalProvinceID());
                  ProvinceBorderManager.action.setProvinceID(Game.iActiveProvince);
                  Game.menuManager.setVisibleInGame_Goods(false);
                  Game.menuManager.rebuildInGame_Civ();
               }
            }
         });
         menuElements.add(
            new Text_StaticBG(
               Game.getCiv(productionCivID.get(toAddID)).getCivName(),
               CFG.FONT_REGULAR_SMALL,
               -1,
               buttonX,
               buttonY + ImageManager.getImage(Images.flagOverDefault).getHeight() + CFG.PADDING,
               ImageManager.getImage(Images.flagOverDefault).getWidth(),
               CFG.TEXT_HEIGHT + CFG.PADDING * 2
            )
         );
         buttonY += buttonResH + buttonYPadding;
         buttonX = paddingLeft;
         production.remove(toAddID);
         productionCivID.remove(toAddID);
         productionProvinces.remove(toAddID);
         productionEconomy.remove(toAddID);
         productionProductionEfficiency.remove(toAddID);
      }

      buttonY = 0;
      int ixxx = 0;

      for (int iSize = menuElements.size(); ixxx < iSize; ixxx++) {
         if (buttonY < menuElements.get(ixxx).getPosY() + menuElements.get(ixxx).getHeight() + CFG.PADDING * 2) {
            buttonY = menuElements.get(ixxx).getPosY() + menuElements.get(ixxx).getHeight() + CFG.PADDING * 2;
         }
      }

      ixxx = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - CFG.PADDING * 3);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, ixxx)));
      this.initMenu(new MenuTitleIMG_DoubleText("", Game.lang.get("Year") + ": " + Game_Calendar.currentYear, false, false, Images.title928) {
         @Override
         public long getTime() {
            return InGame_Goods_LargestProducers.lTime2;
         }
      }, CFG.GAME_WIDTH / 2 - menuWidth / 2, menuY, menuWidth, ixxx, menuElements, false, true);
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
      this.getTitle().setText(Game.lang.get("LargestGoodsProducers") + ": " + ResourcesManager.lResources.get(RESOURCE_ID).Name);
   }

   @Override
   public void setVisible(boolean visible) {
      super.setVisible(visible);
      lTime = CFG.currentTimeMillis;
      lTime2 = lTime;
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
