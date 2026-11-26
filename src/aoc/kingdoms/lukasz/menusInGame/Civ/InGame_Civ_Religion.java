package aoc.kingdoms.lukasz.menusInGame.Civ;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.province.ProvinceBorderManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG_FlagCenter;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonReligion2;
import aoc.kingdoms.lukasz.menu_element.graph.Graph_Vertical;
import aoc.kingdoms.lukasz.menu_element.graph.Graph_Vertical_Data_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_FlagCiv_Title;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID_FlagCiv;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_TitleBlueSort;
import aoc.kingdoms.lukasz.menusInGame.InGame_MapModes;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_CourtOptions2;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_Civ_Religion extends Menu {
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static long lTime2 = 0L;
   public static int iSortID = 2;
   public static int iReligionID = -1;

   public InGame_Civ_Religion() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = Images.boxTitleBORDERWIDTH + CFG.PADDING * 2;
      int menuWidth = ImageManager.getImage(Images.insideTop500).getWidth();
      int menuX = InGame_CourtOptions2.getOtherMenuPosX_2();
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title1Red).getHeight();
      int buttonYPadding = CFG.PADDING * 2;
      int buttonX = CFG.PADDING + Images.boxTitleBORDERWIDTH;
      int buttonH = CFG.isDesktop() ? CFG.BUTTON_HEIGHT3 : CFG.BUTTON_HEIGHT2;
      int p0W = (int)((menuWidth - Images.boxTitleBORDERWIDTH * 2) * 0.6F);
      int p1W = (int)((menuWidth - Images.boxTitleBORDERWIDTH * 2) * 0.4F);
      int p0W2 = (int)((menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 3) * 0.6F);
      int p1W2 = (int)((menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 3) * 0.4F);
      int religionH = CFG.BUTTON_HEIGHT;
      int buttonY = CFG.PADDING;
      buttonX = Images.boxTitleBORDERWIDTH + CFG.PADDING;
      menuElements.add(new ButtonReligion2(iReligionID, buttonX, buttonY, menuWidth - buttonX * 2, religionH) {
         @Override
         public void actionElement() {
            Game.menuManager.rebuildInGame_Civ();
         }

         @Override
         public void actionElementPPM() {
            InGame_MapModes.actionReligion();
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
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      Graph_Vertical graphVertical = new Graph_Vertical(
         Graph_Vertical_Data_Type.RELIGION_CIVS,
         Game.lang.get("Civilizations"),
         Game.lang.get("Civilizations"),
         paddingLeft,
         buttonY,
         menuWidth - paddingLeft * 2,
         CFG.BUTTON_HEIGHT * 3,
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
               if (InGame_Civ_Religion.iSortID == 0) {
                  InGame_Civ_Religion.iSortID = 1;
               } else {
                  InGame_Civ_Religion.iSortID = 0;
               }

               Game.menuManager.rebuildInGame_Civ_Religion();
               InGame_Civ_Religion.lTime = 0L;
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
               if (InGame_Civ_Religion.iSortID == 2) {
                  InGame_Civ_Religion.iSortID = 3;
               } else {
                  InGame_Civ_Religion.iSortID = 2;
               }

               Game.menuManager.rebuildInGame_Civ_Religion();
               InGame_Civ_Religion.lTime = 0L;
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

      for (int i = 0; i < Game.getCivsSize(); i++) {
         tCivsID.add(i);
         tPop.add(0L);
      }

      for (int i = 0; i < Game.getProvincesSize(); i++) {
         if (Game.getProvince(i).getCivID() > 0 && Game.getProvince(i).getReligion() == iReligionID) {
            tPop.set(Game.getProvince(i).getCivID(), tPop.get(Game.getProvince(i).getCivID()) + Game.getProvince(i).getPopulationTotal());
         }
      }

      for (int ix = tCivsID.size() - 1; ix >= 0; ix--) {
         if (tPop.get(ix) <= 0L) {
            tCivsID.remove(ix);
            tPop.remove(ix);
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
            int toAddID = 0;
            if (iSortID == 0) {
               for (int o = 1; o < tCivsID.size(); o++) {
                  if (CFG.compareAlphabetic_TwoString(Game.getCiv(tCivsID.get(toAddID)).getCivName(), Game.getCiv(tCivsID.get(o)).getCivName())) {
                     toAddID = o;
                  }
               }
            } else if (iSortID == 1) {
               for (int ox = 1; ox < tCivsID.size(); ox++) {
                  if (CFG.compareAlphabetic_TwoString(Game.getCiv(tCivsID.get(ox)).getCivName(), Game.getCiv(tCivsID.get(toAddID)).getCivName())) {
                     toAddID = ox;
                  }
               }
            } else if (iSortID == 2) {
               for (int oxx = 1; oxx < tCivsID.size(); oxx++) {
                  if (tPop.get(toAddID) < tPop.get(oxx)) {
                     toAddID = oxx;
                  }
               }
            } else if (iSortID == 3) {
               for (int oxxx = 1; oxxx < tCivsID.size(); oxxx++) {
                  if (tPop.get(toAddID) > tPop.get(oxxx)) {
                     toAddID = oxxx;
                  }
               }
            }

            buttonX = Images.boxTitleBORDERWIDTH + CFG.PADDING;
            menuElements.add(
               new Text_StaticBG_ID_FlagCiv(
                  Game.getCiv(tCivsID.get(toAddID)).getCivName(),
                  CFG.FONT_REGULAR_SMALL,
                  CFG.PADDING * 2,
                  buttonX,
                  buttonY,
                  p0W2,
                  buttonH,
                  tCivsID.get(toAddID)
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
               new Text_StaticBG(CFG.getNumberWithSpaces("" + tPop.get(toAddID)), CFG.FONT_REGULAR_SMALL, -1, buttonX, buttonY, p1W2, buttonH) {
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
            tCivsID.remove(toAddID);
            tPop.remove(toAddID);
         }
      }

      buttonY = 0;
      int ixx = 0;

      for (int iSize = menuElements.size(); ixx < iSize; ixx++) {
         if (buttonY < menuElements.get(ixx).getPosY() + menuElements.get(ixx).getHeight() + CFG.PADDING) {
            buttonY = menuElements.get(ixx).getPosY() + menuElements.get(ixx).getHeight() + CFG.PADDING;
         }
      }

      ixx = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - CFG.PADDING * 3);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, ixx)));
      this.initMenu(new MenuTitleIMG_FlagCenter(Game.getCiv(InGame_Civ.iActiveCivID).getCivName(), Game.lang.get("Religion"), false, false, Images.title500) {
         @Override
         public int getFlagCivID() {
            return InGame_Civ.iActiveCivID;
         }

         @Override
         public long getTime() {
            return InGame_Civ_Religion.lTime2;
         }
      }, menuX, menuY, menuWidth, ixx, menuElements, false, true);
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
      ImageManager.getImage(Images.civInfoOver)
         .draw2(
            oSB,
            this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.civInfoOver).getWidth() / 2 + iTranslateX,
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
