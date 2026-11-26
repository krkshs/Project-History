package aoc.kingdoms.lukasz.menusInGame.Right;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.WondersManager;
import aoc.kingdoms.lukasz.map.province.ProvinceBorderManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG_DoubleText;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonWonderProvince;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID_Flag;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_TitleBlueSort;
import aoc.kingdoms.lukasz.menusInGame.InGame_Wonder;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_RightWonders extends Menu {
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static long lTime2 = 0L;
   public static int iSortID = 0;
   public static int iReligionID = -1;

   public InGame_RightWonders() {
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
      int r0W = ImageManager.getImage(Images.wonderFrame).getWidth() + CFG.PADDING * 2;
      int r1W = menuWidth - Images.boxTitleBORDERWIDTH - r0W;
      int r1W2 = menuWidth - Images.boxTitleBORDERWIDTH - CFG.PADDING * 5 - ImageManager.getImage(Images.wonderFrame).getWidth();
      buttonX = Images.boxTitleBORDERWIDTH;
      int popH = (ImageManager.getImage(Images.wonderFrame).getHeight() + CFG.PADDING * 4 - CFG.PADDING) / 2;
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
               if (InGame_RightWonders.iSortID == 0) {
                  InGame_RightWonders.iSortID = 1;
               } else {
                  InGame_RightWonders.iSortID = 0;
               }

               Game.menuManager.rebuildInGame_RightWonders();
               InGame_RightWonders.lTime = 0L;
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
            Game.lang.get("Province"),
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
               if (InGame_RightWonders.iSortID == 2) {
                  InGame_RightWonders.iSortID = 3;
               } else {
                  InGame_RightWonders.iSortID = 2;
               }

               Game.menuManager.rebuildInGame_RightWonders();
               InGame_RightWonders.lTime = 0L;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("SortBy") + ": "));
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Province"), Colors.HOVER_GOLD));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      List<Integer> tWonders = new ArrayList<>();

      for (int i = 0; i < WondersManager.wondersSize; i++) {
         if (WondersManager.wonders.get(i).ProvinceID >= 0) {
            tWonders.add(i);
         }
      }

      if (tWonders.size() <= 0) {
         menuElements.add(new Text_StaticBG(Game.lang.get("None"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT2));
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      } else {
         while (tWonders.size() > 0) {
            int toAddID = 0;
            if (iSortID == 0) {
               for (int o = 1; o < tWonders.size(); o++) {
                  if (CFG.compareAlphabetic_TwoString(WondersManager.wonders.get(tWonders.get(toAddID)).Name, WondersManager.wonders.get(tWonders.get(o)).Name)
                     )
                   {
                     toAddID = o;
                  }
               }
            } else if (iSortID == 1) {
               for (int ox = 1; ox < tWonders.size(); ox++) {
                  if (CFG.compareAlphabetic_TwoString(WondersManager.wonders.get(tWonders.get(ox)).Name, WondersManager.wonders.get(tWonders.get(toAddID)).Name)
                     )
                   {
                     toAddID = ox;
                  }
               }
            } else if (iSortID == 2) {
               for (int oxx = 1; oxx < tWonders.size(); oxx++) {
                  if (CFG.compareAlphabetic_TwoString(
                     Game.getProvince(WondersManager.wonders.get(tWonders.get(toAddID)).ProvinceID).getProvinceName(),
                     Game.getProvince(WondersManager.wonders.get(tWonders.get(oxx)).ProvinceID).getProvinceName()
                  )) {
                     toAddID = oxx;
                  }
               }
            } else if (iSortID == 3) {
               for (int oxxx = 1; oxxx < tWonders.size(); oxxx++) {
                  if (CFG.compareAlphabetic_TwoString(
                     Game.getProvince(WondersManager.wonders.get(tWonders.get(oxxx)).ProvinceID).getProvinceName(),
                     Game.getProvince(WondersManager.wonders.get(tWonders.get(toAddID)).ProvinceID).getProvinceName()
                  )) {
                     toAddID = oxxx;
                  }
               }
            }

            buttonX = Images.boxTitleBORDERWIDTH + CFG.PADDING * 2;
            menuElements.add(
               new ButtonWonderProvince(buttonX, buttonY + CFG.PADDING * 2, tWonders.get(toAddID), WondersManager.wonders.get(tWonders.get(toAddID)).ProvinceID) {
                  @Override
                  public void buildElementHover() {
                     this.menuElementHover = getHoverWonder(this.iProvinceID, this.wonderID, false);
                  }

                  @Override
                  public void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
                     oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.5F));
                     Renderer.drawBox(
                        oSB,
                        Images.statsRectBG,
                        this.getPosX() - CFG.PADDING + iTranslateX,
                        this.getPosY() - CFG.PADDING * 2 + iTranslateY,
                        this.getWidth() + CFG.PADDING * 2,
                        this.getHeight() + CFG.PADDING * 4,
                        1.0F
                     );
                     oSB.setColor(Color.WHITE);
                     super.drawButtonBG(oSB, iTranslateX, iTranslateY, isActive);
                  }

                  @Override
                  public void actionElement() {
                     Game.mapCoords.centerToProvinceID(this.getCurrent());
                     Game.setActiveProvinceID(this.getCurrent());
                     ProvinceBorderManager.action.setProvinceID(Game.iActiveProvince);
                     if (Game.menuManager.getVisibleInGame_Wonder() && this.getCurrent() >= 0 && Game.getProvince(this.getCurrent()).wonderID >= 0) {
                        InGame_Wonder.iProvinceID = this.getCurrent();
                        Game.menuManager.rebuildInGame_Wonder();
                        InGame_Wonder.lTime = 0L;
                     }
                  }
               }
            );
            buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING * 2;
            int var10006 = CFG.PADDING * 2;
            menuElements.add(
               new Text_StaticBG_ID(
                  WondersManager.wonders.get(tWonders.get(toAddID)).Name,
                  CFG.FONT_BOLD_SMALL,
                  var10006,
                  buttonX,
                  buttonY,
                  r1W2,
                  popH,
                  WondersManager.wonders.get(tWonders.get(toAddID)).ProvinceID
               ) {
                  @Override
                  protected Color getColor(boolean isActive) {
                     return Colors.getColorTopStats3(isActive, this.getIsHovered());
                  }

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

                     if (Game.menuManager.getVisibleInGame_Wonder() && this.getCurrent() >= 0 && Game.getProvince(this.getCurrent()).wonderID >= 0) {
                        InGame_Wonder.iProvinceID = this.getCurrent();
                        Game.menuManager.rebuildInGame_Wonder();
                        InGame_Wonder.lTime = 0L;
                     }
                  }
               }
            );
            menuElements.add(
               new Text_StaticBG_ID_Flag(
                  Game.getProvince(WondersManager.wonders.get(tWonders.get(toAddID)).ProvinceID).getProvinceName(),
                  CFG.FONT_REGULAR_SMALL,
                  CFG.PADDING * 2,
                  buttonX,
                  buttonY + CFG.PADDING + popH,
                  r1W2,
                  popH,
                  WondersManager.wonders.get(tWonders.get(toAddID)).ProvinceID
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

                     if (Game.menuManager.getVisibleInGame_Wonder() && this.getCurrent() >= 0 && Game.getProvince(this.getCurrent()).wonderID >= 0) {
                        InGame_Wonder.iProvinceID = this.getCurrent();
                        Game.menuManager.rebuildInGame_Wonder();
                        InGame_Wonder.lTime = 0L;
                     }
                  }
               }
            );
            buttonY += ImageManager.getImage(Images.wonderFrame).getHeight() + CFG.PADDING * 4 + CFG.PADDING;
            tWonders.remove(toAddID);
         }
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
         new MenuTitleIMG_DoubleText(Game.lang.get("Wonders"), Game.lang.get("Total") + ": " + WondersManager.wondersSize, false, false, Images.title600) {
            @Override
            public void draw(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, Status titleStatus) {
               ImageManager.getImage(this.imageID).draw2(oSB, nPosX, nPosY - this.getHeight(), nWidth, this.getHeight());
               this.drawGradient(oSB, nPosX, nPosY, nWidth, titleStatus);
               this.drawText(oSB, nPosX + Images.boxTitleBORDERWIDTH / 2, nPosY, nWidth, titleStatus);
            }

            @Override
            public long getTime() {
               return InGame_RightWonders.lTime2;
            }
         }, menuX, menuY, menuWidth, ix, menuElements, false, true
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
