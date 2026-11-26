package aoc.kingdoms.lukasz.menusInGame.Right;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
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
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonusResource;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID_Flag;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_TitleBlueSort;
import aoc.kingdoms.lukasz.menusInGame.Province.InGame_ProvinceInfo;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_RightGoodsProvinces extends Menu {
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static long lTime2 = 0L;
   public static int iSortID = 2;

   public InGame_RightGoodsProvinces() {
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
      int r0W = (int)((menuWidth - Images.boxTitleBORDERWIDTH) * 0.5F);
      int r1W = (int)((menuWidth - Images.boxTitleBORDERWIDTH) * 0.25F);
      int r2W = (int)((menuWidth - Images.boxTitleBORDERWIDTH) * 0.25F);
      int r0W2 = (int)((menuWidth - Images.boxTitleBORDERWIDTH - CFG.PADDING * 4) * 0.5F);
      int r1W2 = (int)((menuWidth - Images.boxTitleBORDERWIDTH - CFG.PADDING * 4) * 0.25F);
      int r2W2 = (int)((menuWidth - Images.boxTitleBORDERWIDTH - CFG.PADDING * 4) * 0.25F);
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
               if (InGame_RightGoodsProvinces.iSortID == 0) {
                  InGame_RightGoodsProvinces.iSortID = 1;
               } else {
                  InGame_RightGoodsProvinces.iSortID = 0;
               }

               Game.menuManager.rebuildInGame_RightGoodsProvinces();
               InGame_RightGoodsProvinces.lTime = 0L;
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
               if (InGame_RightGoodsProvinces.iSortID == 2) {
                  InGame_RightGoodsProvinces.iSortID = 3;
               } else {
                  InGame_RightGoodsProvinces.iSortID = 2;
               }

               Game.menuManager.rebuildInGame_RightGoodsProvinces();
               InGame_RightGoodsProvinces.lTime = 0L;
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
            iSortID == 4 || iSortID == 5,
            iSortID == 5,
            Game.lang.get("Income"),
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
               if (InGame_RightGoodsProvinces.iSortID == 4) {
                  InGame_RightGoodsProvinces.iSortID = 5;
               } else {
                  InGame_RightGoodsProvinces.iSortID = 4;
               }

               Game.menuManager.rebuildInGame_RightGoodsProvinces();
               InGame_RightGoodsProvinces.lTime = 0L;
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
      int tNumOfProvinces = 0;

      try {
         List<Integer> tProvinces = new ArrayList<>();
         List<Float> tProduction = new ArrayList<>();
         List<Float> tIncome = new ArrayList<>();

         for (int i = 0; i < Game.getProvincesSize(); i++) {
            if (Game.getProvince(i).getResourceID() == InGame_RightGoods.iActiveResID) {
               tProvinces.add(i);
               tProduction.add(ResourcesManager.getProducedGoods(i));
               tIncome.add(ResourcesManager.getMonthlyIncome(i));
               tNumOfProvinces++;
            }
         }

         int tID = 1;

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
                  if (tProduction.get(toAddID) < tProduction.get(oxx)) {
                     toAddID = oxx;
                  }
               }
            } else if (iSortID == 3) {
               for (int oxxx = 1; oxxx < tProvinces.size(); oxxx++) {
                  if (tProduction.get(toAddID) > tProduction.get(oxxx)) {
                     toAddID = oxxx;
                  }
               }
            } else if (iSortID == 4) {
               for (int oxxxx = 1; oxxxx < tProvinces.size(); oxxxx++) {
                  if (tIncome.get(toAddID) < tIncome.get(oxxxx)) {
                     toAddID = oxxxx;
                  }
               }
            } else if (iSortID == 5) {
               for (int oxxxxx = 1; oxxxxx < tProvinces.size(); oxxxxx++) {
                  if (tIncome.get(toAddID) > tIncome.get(oxxxxx)) {
                     toAddID = oxxxxx;
                  }
               }
            }

            buttonX = Images.boxTitleBORDERWIDTH + CFG.PADDING;
            menuElements.add(
               new Text_StaticBG_ID_Flag(
                  "" + tID++ + ". " + Game.getProvince(tProvinces.get(toAddID)).getProvinceName(),
                  CFG.FONT_REGULAR_SMALL,
                  CFG.PADDING * 2,
                  buttonX,
                  buttonY,
                  r0W2,
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
                        if (Game.menuManager.getVisibleInGame_ProvinceInfo()) {
                           Game.menuManager.rebuildInGame_ProvinceInfo(false);
                        }
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
               new Text_StaticBG(
                  "" + CFG.getPrecision2(tProduction.get(toAddID), tProduction.get(toAddID) < 1.0F ? 100 : 10),
                  CFG.FONT_REGULAR_SMALL,
                  -1,
                  buttonX,
                  buttonY,
                  r1W2,
                  buttonH
               ) {
                  @Override
                  public void actionElement() {
                     Game.menuManager.setVisibleInGame_ProvinceInfo(false);
                     Game.gameActiveProvince.resetLastActiveProvince();
                     Game.setActiveProvinceID(-1);
                     InGame_RightGoods.iActiveResID = -1;
                     Game.menuManager.rebuildInGame_RightGoods();
                  }

                  @Override
                  public void buildElementHover() {
                     List<MenuElement_HoverElement> nElements = new ArrayList<>();
                     List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                     nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Resources"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
                     nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.goods, CFG.PADDING, 0));
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     nData.add(
                        new MenuElement_HoverElement_Type_Button_TextBonusResource(
                           Game.lang.get("Production") + ": ",
                           this.getText(),
                           InGame_RightGoods.iActiveResID,
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
                  "" + CFG.getPrecision2(tIncome.get(toAddID), tIncome.get(toAddID) < 1.0F ? 100 : 10),
                  CFG.FONT_BOLD_SMALL,
                  -1,
                  buttonX,
                  buttonY,
                  r2W2,
                  buttonH
               ) {
                  @Override
                  protected Color getColor(boolean isActive) {
                     if (isActive) {
                        return Colors.COLOR_TEXT_MODIFIER_POSITIVE_ACTIVE;
                     } else {
                        return this.getIsHovered() ? Colors.COLOR_TEXT_MODIFIER_POSITIVE_HOVER : Colors.COLOR_TEXT_MODIFIER_POSITIVE;
                     }
                  }

                  @Override
                  public void actionElement() {
                     Game.menuManager.setVisibleInGame_ProvinceInfo(false);
                     Game.gameActiveProvince.resetLastActiveProvince();
                     Game.setActiveProvinceID(-1);
                     InGame_RightGoods.iActiveResID = -1;
                     Game.menuManager.rebuildInGame_RightGoods();
                  }

                  @Override
                  public void buildElementHover() {
                     List<MenuElement_HoverElement> nElements = new ArrayList<>();
                     List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                     nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Resources"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
                     nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.goods, CFG.PADDING, 0));
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     nData.add(
                        new MenuElement_HoverElement_Type_Button_TextBonus(
                           Game.lang.get("IncomeProduction") + ": ",
                           this.getText(),
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
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
            tProduction.remove(toAddID);
            tProvinces.remove(toAddID);
            tIncome.remove(toAddID);
         }
      } catch (Exception var25) {
         CFG.exceptionStack(var25);
      }

      buttonY = 0;
      int ix = 0;

      for (int iSize = menuElements.size(); ix < iSize; ix++) {
         if (buttonY < menuElements.get(ix).getPosY() + menuElements.get(ix).getHeight() + CFG.PADDING) {
            buttonY = menuElements.get(ix).getPosY() + menuElements.get(ix).getHeight() + CFG.PADDING;
         }
      }

      ix = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - (CFG.GAME_HEIGHT - Game.menuManager.getInGame_MapModesPosY() + CFG.PADDING * 3));
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, ix)));
      this.initMenu(
         new MenuTitleIMG_DoubleText(
            ResourcesManager.getResourceName(InGame_RightGoods.iActiveResID),
            Game.lang.get("Provinces") + ": " + tNumOfProvinces,
            false,
            false,
            Images.title600
         ) {
            @Override
            public void draw(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, Status titleStatus) {
               ImageManager.getImage(this.imageID).draw2(oSB, nPosX, nPosY - this.getHeight(), nWidth, this.getHeight());
               this.drawGradient(oSB, nPosX, nPosY, nWidth, titleStatus);
               this.drawText(oSB, nPosX + Images.boxTitleBORDERWIDTH / 2, nPosY, nWidth, titleStatus);
            }

            @Override
            public long getTime() {
               return InGame_RightGoodsProvinces.lTime2;
            }
         },
         menuX,
         menuY,
         menuWidth,
         ix,
         menuElements,
         false,
         true
      );
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
