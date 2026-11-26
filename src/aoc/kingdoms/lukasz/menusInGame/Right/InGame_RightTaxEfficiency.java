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
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Religion;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRect_Active;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_FlagCiv_Title;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ReligionTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID_Flag;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID_FlagCiv;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_TitleBlueSort;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_RightTaxEfficiency extends Menu {
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static long lTime2 = 0L;
   public static int iModeID = 0;
   public static int iSortID = 2;

   public InGame_RightTaxEfficiency() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING;
      int titleHeight = ImageManager.getImage(Images.title2).getHeight();
      int extraX = Images.boxTitleBORDERWIDTH;
      int menuWidth = ImageManager.getImage(Images.title1Red).getWidth();
      int menuX = CFG.GAME_WIDTH - menuWidth;
      int menuY = ImageManager.getImage(Images.topStats).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title1Red).getHeight();
      int buttonYPadding = CFG.PADDING;
      int buttonX = paddingLeft + Images.boxTitleBORDERWIDTH;
      int buttonH = CFG.isDesktop() ? CFG.BUTTON_HEIGHT3 : CFG.BUTTON_HEIGHT2;
      int c0W = (int)((menuWidth - paddingLeft * 2 - CFG.PADDING - Images.boxTitleBORDERWIDTH) / 2.0F);
      menuElements.add(
         new ButtonStatsRect_Active(
            Game.lang.get("Civilizations"), buttonX, buttonYPadding, c0W, buttonH, iModeID == 0 ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL
         ) {
            @Override
            public void actionElement() {
               if (InGame_RightTaxEfficiency.iModeID != 0) {
                  InGame_RightTaxEfficiency.iModeID = 0;
                  Game.menuManager.rebuildInGame_RightTaxEfficiency();
                  InGame_RightTaxEfficiency.lTime = 0L;
               }
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Civilizations"), Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.council, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements, true);
            }

            @Override
            protected Color getColor(boolean isActive) {
               return InGame_RightTaxEfficiency.iModeID == 0 ? Colors.HOVER_GOLD : super.getColor(isActive);
            }
         }
      );
      menuElements.add(
         new ButtonStatsRect_Active(
            Game.lang.get("Provinces"), buttonX + CFG.PADDING + c0W, buttonYPadding, c0W, buttonH, iModeID == 1 ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL
         ) {
            @Override
            public void actionElement() {
               if (InGame_RightTaxEfficiency.iModeID != 1) {
                  InGame_RightTaxEfficiency.iModeID = 1;
                  Game.menuManager.rebuildInGame_RightTaxEfficiency();
                  InGame_RightTaxEfficiency.lTime = 0L;
               }
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Provinces"), Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.provinces, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements, true);
            }

            @Override
            protected Color getColor(boolean isActive) {
               return InGame_RightTaxEfficiency.iModeID == 1 ? Colors.HOVER_GOLD : super.getColor(isActive);
            }
         }
      );
      int buttonY = buttonYPadding + menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new ButtonStatsRect_Active(Game.lang.get("Continents"), buttonX, buttonY, c0W, buttonH, iModeID == 2 ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL) {
            @Override
            public void actionElement() {
               if (InGame_RightTaxEfficiency.iModeID != 2) {
                  InGame_RightTaxEfficiency.iModeID = 2;
                  Game.menuManager.rebuildInGame_RightTaxEfficiency();
                  InGame_RightTaxEfficiency.lTime = 0L;
               }
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Continents"), Colors.HOVER_GOLD));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements, true);
            }

            @Override
            protected Color getColor(boolean isActive) {
               return InGame_RightTaxEfficiency.iModeID == 2 ? Colors.HOVER_GOLD : super.getColor(isActive);
            }
         }
      );
      menuElements.add(
         new ButtonStatsRect_Active(
            Game.lang.get("Religion"), buttonX + CFG.PADDING + c0W, buttonY, c0W, buttonH, iModeID == 3 ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL
         ) {
            @Override
            public void actionElement() {
               if (InGame_RightTaxEfficiency.iModeID != 3) {
                  InGame_RightTaxEfficiency.iModeID = 3;
                  Game.menuManager.rebuildInGame_RightTaxEfficiency();
                  InGame_RightTaxEfficiency.lTime = 0L;
               }
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Religion"), Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.religion, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements, true);
            }

            @Override
            protected Color getColor(boolean isActive) {
               return InGame_RightTaxEfficiency.iModeID == 3 ? Colors.HOVER_GOLD : super.getColor(isActive);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
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
               if (InGame_RightTaxEfficiency.iSortID == 0) {
                  InGame_RightTaxEfficiency.iSortID = 1;
               } else {
                  InGame_RightTaxEfficiency.iSortID = 0;
               }

               Game.menuManager.rebuildInGame_RightTaxEfficiency();
               InGame_RightTaxEfficiency.lTime = 0L;
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
            Game.lang.get("TaxEfficiency"),
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
               if (InGame_RightTaxEfficiency.iSortID == 2) {
                  InGame_RightTaxEfficiency.iSortID = 3;
               } else {
                  InGame_RightTaxEfficiency.iSortID = 2;
               }

               Game.menuManager.rebuildInGame_RightTaxEfficiency();
               InGame_RightTaxEfficiency.lTime = 0L;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("SortBy") + ": "));
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("TaxEfficiency"), Colors.HOVER_GOLD));
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
            Game.lang.get("IncomeTaxation"),
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
               if (InGame_RightTaxEfficiency.iSortID == 4) {
                  InGame_RightTaxEfficiency.iSortID = 5;
               } else {
                  InGame_RightTaxEfficiency.iSortID = 4;
               }

               Game.menuManager.rebuildInGame_RightTaxEfficiency();
               InGame_RightTaxEfficiency.lTime = 0L;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("SortBy") + ": "));
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("IncomeTaxation"), Colors.HOVER_GOLD));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;

      try {
         if (iModeID == 0) {
            List<Integer> tCivs = new ArrayList<>();
            List<Float> tTax = new ArrayList<>();
            List<Float> tIncomeTaxation = new ArrayList<>();

            for (int i = 1; i < Game.getCivsSize(); i++) {
               if (Game.getCiv(i).getNumOfProvinces() > 0) {
                  tCivs.add(i);
                  tTax.add(Game.getCiv(i).getAverageTaxEfficiency());
                  tIncomeTaxation.add(Game.getCiv(i).getTotalProvinceIncomeTax());
               }
            }

            while (tCivs.size() > 0) {
               int toAddID = 0;
               if (iSortID == 0) {
                  for (int o = 1; o < tCivs.size(); o++) {
                     if (CFG.compareAlphabetic_TwoString(Game.getCiv(tCivs.get(toAddID)).getCivName(), Game.getCiv(tCivs.get(o)).getCivName())) {
                        toAddID = o;
                     }
                  }
               } else if (iSortID == 1) {
                  for (int ox = 1; ox < tCivs.size(); ox++) {
                     if (CFG.compareAlphabetic_TwoString(Game.getCiv(tCivs.get(ox)).getCivName(), Game.getCiv(tCivs.get(toAddID)).getCivName())) {
                        toAddID = ox;
                     }
                  }
               } else if (iSortID == 2) {
                  for (int oxx = 1; oxx < tCivs.size(); oxx++) {
                     if (tTax.get(toAddID) < tTax.get(oxx)) {
                        toAddID = oxx;
                     }
                  }
               } else if (iSortID == 3) {
                  for (int oxxx = 1; oxxx < tCivs.size(); oxxx++) {
                     if (tTax.get(toAddID) > tTax.get(oxxx)) {
                        toAddID = oxxx;
                     }
                  }
               } else if (iSortID == 4) {
                  for (int oxxxx = 1; oxxxx < tCivs.size(); oxxxx++) {
                     if (tIncomeTaxation.get(toAddID) < tIncomeTaxation.get(oxxxx)) {
                        toAddID = oxxxx;
                     }
                  }
               } else if (iSortID == 5) {
                  for (int oxxxxx = 1; oxxxxx < tCivs.size(); oxxxxx++) {
                     if (tIncomeTaxation.get(toAddID) > tIncomeTaxation.get(oxxxxx)) {
                        toAddID = oxxxxx;
                     }
                  }
               }

               buttonX = Images.boxTitleBORDERWIDTH + CFG.PADDING;
               menuElements.add(
                  new Text_StaticBG_ID_FlagCiv(
                     Game.getCiv(tCivs.get(toAddID)).getCivName(), CFG.FONT_REGULAR_SMALL, CFG.PADDING * 2, buttonX, buttonY, r0W2, buttonH, tCivs.get(toAddID)
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
                              this.getCurrent(),
                              Game.lang.get("Provinces") + ": " + CFG.getNumberWithSpaces("" + Game.getCiv(this.getCurrent()).getNumOfProvinces())
                           )
                        );
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        nData.add(
                           new MenuElement_HoverElement_Type_Button_TextBonus(
                              Game.lang.get("Average") + ", " + Game.lang.get("TaxEfficiency") + ": ",
                              CFG.getPrecision2(Game.getCiv(this.getCurrent()).getAverageTaxEfficiency(), 100) + "%",
                              Images.tax,
                              CFG.FONT_REGULAR_SMALL,
                              CFG.FONT_BOLD_SMALL,
                              Colors.HOVER_LEFT,
                              Colors.HOVER_GOLD
                           )
                        );
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        nData.add(
                           new MenuElement_HoverElement_Type_Button_TextBonus(
                              Game.lang.get("IncomeTaxation") + ": ",
                              CFG.getPrecision2(Game.getCiv(this.getCurrent()).getTotalProvinceIncomeTax(), 10),
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
               buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
               menuElements.add(new Text_StaticBG(CFG.getPrecision2(tTax.get(toAddID), 10) + "%", CFG.FONT_REGULAR_SMALL, -1, buttonX, buttonY, r1W2, buttonH));
               buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
               menuElements.add(
                  new Text_StaticBG(CFG.getPrecision2(tIncomeTaxation.get(toAddID), 100), CFG.FONT_REGULAR_SMALL, -1, buttonX, buttonY, r2W2, buttonH)
               );
               buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
               buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
               tCivs.remove(toAddID);
               tTax.remove(toAddID);
               tIncomeTaxation.remove(toAddID);
            }
         } else if (iModeID == 1) {
            List<Integer> tProvinces = new ArrayList<>();
            List<Float> tTax = new ArrayList<>();
            List<Float> tIncomeTaxation = new ArrayList<>();

            for (int ix = 0; ix < Game.getProvincesSize(); ix++) {
               if (Game.getProvince(ix).getCivID() > 0) {
                  tProvinces.add(ix);
                  tTax.add(Game.getProvince(ix).getTaxEfficiencyWithBonuses());
                  tIncomeTaxation.add(Game.getProvince(ix).fProvinceIncomeTaxation);
               }
            }

            int maxProvinces = 250;

            while (tProvinces.size() > 0 && maxProvinces-- > 0) {
               int toAddID = 0;
               if (iSortID == 0) {
                  for (int oxxxxxx = 1; oxxxxxx < tProvinces.size(); oxxxxxx++) {
                     if (CFG.compareAlphabetic_TwoString(
                        Game.getProvince(tProvinces.get(toAddID)).getProvinceName(), Game.getProvince(tProvinces.get(oxxxxxx)).getProvinceName()
                     )) {
                        toAddID = oxxxxxx;
                     }
                  }
               } else if (iSortID == 1) {
                  for (int oxxxxxxx = 1; oxxxxxxx < tProvinces.size(); oxxxxxxx++) {
                     if (CFG.compareAlphabetic_TwoString(
                        Game.getProvince(tProvinces.get(oxxxxxxx)).getProvinceName(), Game.getProvince(tProvinces.get(toAddID)).getProvinceName()
                     )) {
                        toAddID = oxxxxxxx;
                     }
                  }
               } else if (iSortID == 2) {
                  for (int oxxxxxxxx = 1; oxxxxxxxx < tProvinces.size(); oxxxxxxxx++) {
                     if (tTax.get(toAddID) < tTax.get(oxxxxxxxx)) {
                        toAddID = oxxxxxxxx;
                     }
                  }
               } else if (iSortID == 3) {
                  for (int oxxxxxxxxx = 1; oxxxxxxxxx < tProvinces.size(); oxxxxxxxxx++) {
                     if (tTax.get(toAddID) > tTax.get(oxxxxxxxxx)) {
                        toAddID = oxxxxxxxxx;
                     }
                  }
               } else if (iSortID == 4) {
                  for (int oxxxxxxxxxx = 1; oxxxxxxxxxx < tProvinces.size(); oxxxxxxxxxx++) {
                     if (tIncomeTaxation.get(toAddID) < tIncomeTaxation.get(oxxxxxxxxxx)) {
                        toAddID = oxxxxxxxxxx;
                     }
                  }
               } else if (iSortID == 5) {
                  for (int oxxxxxxxxxxx = 1; oxxxxxxxxxxx < tProvinces.size(); oxxxxxxxxxxx++) {
                     if (tIncomeTaxation.get(toAddID) > tIncomeTaxation.get(oxxxxxxxxxxx)) {
                        toAddID = oxxxxxxxxxxx;
                     }
                  }
               }

               buttonX = Images.boxTitleBORDERWIDTH + CFG.PADDING;
               menuElements.add(
                  new Text_StaticBG_ID_Flag(
                     Game.getProvince(tProvinces.get(toAddID)).getProvinceName(),
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
                        List<MenuElement_HoverElement> nElements = new ArrayList<>();
                        List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                        nData.add(
                           new MenuElement_HoverElement_Type_FlagCiv_Title(
                              Game.getProvince(this.getCurrent()).getCivID(), Game.getProvince(this.getCurrent()).getProvinceName()
                           )
                        );
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        nData.add(
                           new MenuElement_HoverElement_Type_Button_TextBonus(
                              Game.lang.get("TaxEfficiency") + ": ",
                              CFG.getPrecision2(Game.getProvince(this.getCurrent()).getTaxEfficiency(), 10) + "%",
                              Images.tax,
                              CFG.FONT_REGULAR_SMALL,
                              CFG.FONT_BOLD_SMALL,
                              Colors.HOVER_LEFT,
                              Colors.HOVER_GOLD
                           )
                        );
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        nData.add(
                           new MenuElement_HoverElement_Type_Button_TextBonus(
                              Game.lang.get("IncomeTaxation") + ": ",
                              CFG.getPrecision2(Game.getProvince(this.getCurrent()).fProvinceIncomeTaxation, 100),
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
               buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
               menuElements.add(new Text_StaticBG(CFG.getPrecision2(tTax.get(toAddID), 10) + "%", CFG.FONT_REGULAR_SMALL, -1, buttonX, buttonY, r1W2, buttonH));
               buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
               menuElements.add(
                  new Text_StaticBG(CFG.getPrecision2(tIncomeTaxation.get(toAddID), 100), CFG.FONT_REGULAR_SMALL, -1, buttonX, buttonY, r2W2, buttonH)
               );
               buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
               buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
               tProvinces.remove(toAddID);
               tTax.remove(toAddID);
               tIncomeTaxation.remove(toAddID);
            }
         } else if (iModeID == 2) {
            List<Integer> tContinents = new ArrayList<>();
            List<Float> tTax = new ArrayList<>();
            List<Float> tIncomeTaxation = new ArrayList<>();
            List<Integer> tProvinces = new ArrayList<>();

            for (int ixx = 0; ixx < Game.continents.iContinentsSize; ixx++) {
               tContinents.add(ixx);
               tTax.add(0.0F);
               tIncomeTaxation.add(0.0F);
               tProvinces.add(0);
            }

            for (int ixx = 0; ixx < Game.getProvincesSize(); ixx++) {
               if (Game.getProvince(ixx).getCivID() > 0) {
                  tTax.set(
                     Game.getProvince(ixx).getContinent(), tTax.get(Game.getProvince(ixx).getContinent()) + Game.getProvince(ixx).getTaxEfficiencyWithBonuses()
                  );
                  tIncomeTaxation.set(
                     Game.getProvince(ixx).getContinent(),
                     tIncomeTaxation.get(Game.getProvince(ixx).getContinent()) + Game.getProvince(ixx).fProvinceIncomeTaxation
                  );
                  tProvinces.set(Game.getProvince(ixx).getContinent(), tProvinces.get(Game.getProvince(ixx).getContinent()) + 1);
               }
            }

            tContinents.remove(0);
            tTax.remove(0);
            tIncomeTaxation.remove(0);
            tProvinces.remove(0);

            for (int ixxx = 0; ixxx < tTax.size(); ixxx++) {
               tTax.set(ixxx, tTax.get(ixxx) / tProvinces.get(ixxx).intValue());
            }

            while (tContinents.size() > 0) {
               int toAddID = 0;
               if (iSortID == 0) {
                  for (int oxxxxxxxxxxxx = 1; oxxxxxxxxxxxx < tContinents.size(); oxxxxxxxxxxxx++) {
                     if (CFG.compareAlphabetic_TwoString(
                        Game.continents.lContinents.get(tContinents.get(toAddID)).sName, Game.continents.lContinents.get(tContinents.get(oxxxxxxxxxxxx)).sName
                     )) {
                        toAddID = oxxxxxxxxxxxx;
                     }
                  }
               } else if (iSortID == 1) {
                  for (int oxxxxxxxxxxxxx = 1; oxxxxxxxxxxxxx < tContinents.size(); oxxxxxxxxxxxxx++) {
                     if (CFG.compareAlphabetic_TwoString(
                        Game.continents.lContinents.get(tContinents.get(oxxxxxxxxxxxxx)).sName, Game.continents.lContinents.get(tContinents.get(toAddID)).sName
                     )) {
                        toAddID = oxxxxxxxxxxxxx;
                     }
                  }
               } else if (iSortID == 2) {
                  for (int oxxxxxxxxxxxxxx = 1; oxxxxxxxxxxxxxx < tContinents.size(); oxxxxxxxxxxxxxx++) {
                     if (tTax.get(toAddID) < tTax.get(oxxxxxxxxxxxxxx)) {
                        toAddID = oxxxxxxxxxxxxxx;
                     }
                  }
               } else if (iSortID == 3) {
                  for (int oxxxxxxxxxxxxxxx = 1; oxxxxxxxxxxxxxxx < tContinents.size(); oxxxxxxxxxxxxxxx++) {
                     if (tTax.get(toAddID) > tTax.get(oxxxxxxxxxxxxxxx)) {
                        toAddID = oxxxxxxxxxxxxxxx;
                     }
                  }
               } else if (iSortID == 4) {
                  for (int oxxxxxxxxxxxxxxxx = 1; oxxxxxxxxxxxxxxxx < tContinents.size(); oxxxxxxxxxxxxxxxx++) {
                     if (tIncomeTaxation.get(toAddID) < tIncomeTaxation.get(oxxxxxxxxxxxxxxxx)) {
                        toAddID = oxxxxxxxxxxxxxxxx;
                     }
                  }
               } else if (iSortID == 5) {
                  for (int oxxxxxxxxxxxxxxxxx = 1; oxxxxxxxxxxxxxxxxx < tContinents.size(); oxxxxxxxxxxxxxxxxx++) {
                     if (tIncomeTaxation.get(toAddID) > tIncomeTaxation.get(oxxxxxxxxxxxxxxxxx)) {
                        toAddID = oxxxxxxxxxxxxxxxxx;
                     }
                  }
               }

               buttonX = Images.boxTitleBORDERWIDTH + CFG.PADDING;
               menuElements.add(
                  new Text_StaticBG_ID(
                     Game.continents.lContinents.get(tContinents.get(toAddID)).sName,
                     CFG.FONT_REGULAR_SMALL,
                     CFG.PADDING * 2,
                     buttonX,
                     buttonY,
                     r0W2,
                     buttonH,
                     tContinents.get(toAddID)
                  ) {
                     @Override
                     public void buildElementHover() {
                        List<MenuElement_HoverElement> nElements = new ArrayList<>();
                        List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                        nData.add(
                           new MenuElement_HoverElement_Type_TextTitle(
                              Game.continents.lContinents.get(this.getCurrent()).sName, CFG.FONT_BOLD, Colors.HOVER_GOLD
                           )
                        );
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        nData.add(new MenuElement_HoverElement_Type_Line());
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        float tTax = 0.0F;
                        float tIncome = 0.0F;
                        int tNum = 0;

                        for (int i = 0; i < Game.getProvincesSize(); i++) {
                           if (Game.getProvince(i).getCivID() > 0 && Game.getProvince(i).getContinent() == this.getCurrent()) {
                              tTax += Game.getProvince(i).getTaxEfficiencyWithBonuses();
                              tIncome += Game.getProvince(i).fProvinceIncomeTaxation;
                              tNum++;
                           }
                        }

                        nData.add(
                           new MenuElement_HoverElement_Type_Button_TextBonus(
                              Game.lang.get("Average") + ", " + Game.lang.get("TaxEfficiency") + ": ",
                              CFG.getPrecision2(tTax / tNum, 100) + "%",
                              Images.tax,
                              CFG.FONT_REGULAR_SMALL,
                              CFG.FONT_BOLD_SMALL,
                              Colors.HOVER_LEFT,
                              Colors.HOVER_GOLD
                           )
                        );
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        nData.add(
                           new MenuElement_HoverElement_Type_Button_TextBonus(
                              Game.lang.get("IncomeTaxation") + ": ",
                              CFG.getPrecision2(tIncome, 100),
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
               buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
               menuElements.add(new Text_StaticBG(CFG.getPrecision2(tTax.get(toAddID), 100) + "%", CFG.FONT_REGULAR_SMALL, -1, buttonX, buttonY, r1W2, buttonH));
               buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
               menuElements.add(
                  new Text_StaticBG(CFG.getPrecision2(tIncomeTaxation.get(toAddID), 100), CFG.FONT_REGULAR_SMALL, -1, buttonX, buttonY, r2W2, buttonH)
               );
               buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
               buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
               tContinents.remove(toAddID);
               tTax.remove(toAddID);
               tIncomeTaxation.remove(toAddID);
               tProvinces.remove(toAddID);
            }
         } else if (iModeID == 3) {
            List<Integer> tReligion = new ArrayList<>();
            List<Float> tTax = new ArrayList<>();
            List<Float> tIncomeTaxation = new ArrayList<>();
            List<Integer> tProvinces = new ArrayList<>();

            for (int ixxx = 0; ixxx < Game.religionManager.getReligionsSize(); ixxx++) {
               tReligion.add(ixxx);
               tTax.add(0.0F);
               tIncomeTaxation.add(0.0F);
               tProvinces.add(0);
            }

            for (int ixxx = 0; ixxx < Game.getProvincesSize(); ixxx++) {
               if (Game.getProvince(ixxx).getCivID() > 0) {
                  tTax.set(
                     Game.getProvince(ixxx).getReligion(),
                     tTax.get(Game.getProvince(ixxx).getReligion()) + Game.getProvince(ixxx).getTaxEfficiencyWithBonuses()
                  );
                  tIncomeTaxation.set(
                     Game.getProvince(ixxx).getReligion(),
                     tIncomeTaxation.get(Game.getProvince(ixxx).getReligion()) + Game.getProvince(ixxx).fProvinceIncomeTaxation
                  );
                  tProvinces.set(Game.getProvince(ixxx).getReligion(), tProvinces.get(Game.getProvince(ixxx).getReligion()) + 1);
               }
            }

            for (int ixxxx = 0; ixxxx < tTax.size(); ixxxx++) {
               tTax.set(ixxxx, tTax.get(ixxxx) / tProvinces.get(ixxxx).intValue());
            }

            while (tReligion.size() > 0) {
               int toAddID = 0;
               if (iSortID == 0) {
                  for (int oxxxxxxxxxxxxxxxxxx = 1; oxxxxxxxxxxxxxxxxxx < tReligion.size(); oxxxxxxxxxxxxxxxxxx++) {
                     if (CFG.compareAlphabetic_TwoString(
                        Game.religionManager.getReligion(tReligion.get(toAddID)).Name,
                        Game.religionManager.getReligion(tReligion.get(oxxxxxxxxxxxxxxxxxx)).Name
                     )) {
                        toAddID = oxxxxxxxxxxxxxxxxxx;
                     }
                  }
               } else if (iSortID == 1) {
                  for (int oxxxxxxxxxxxxxxxxxxx = 1; oxxxxxxxxxxxxxxxxxxx < tReligion.size(); oxxxxxxxxxxxxxxxxxxx++) {
                     if (CFG.compareAlphabetic_TwoString(
                        Game.religionManager.getReligion(tReligion.get(oxxxxxxxxxxxxxxxxxxx)).Name,
                        Game.religionManager.getReligion(tReligion.get(toAddID)).Name
                     )) {
                        toAddID = oxxxxxxxxxxxxxxxxxxx;
                     }
                  }
               } else if (iSortID == 2) {
                  for (int oxxxxxxxxxxxxxxxxxxxx = 1; oxxxxxxxxxxxxxxxxxxxx < tReligion.size(); oxxxxxxxxxxxxxxxxxxxx++) {
                     if (tTax.get(toAddID) < tTax.get(oxxxxxxxxxxxxxxxxxxxx)) {
                        toAddID = oxxxxxxxxxxxxxxxxxxxx;
                     }
                  }
               } else if (iSortID == 3) {
                  for (int oxxxxxxxxxxxxxxxxxxxxx = 1; oxxxxxxxxxxxxxxxxxxxxx < tReligion.size(); oxxxxxxxxxxxxxxxxxxxxx++) {
                     if (tTax.get(toAddID) > tTax.get(oxxxxxxxxxxxxxxxxxxxxx)) {
                        toAddID = oxxxxxxxxxxxxxxxxxxxxx;
                     }
                  }
               } else if (iSortID == 4) {
                  for (int oxxxxxxxxxxxxxxxxxxxxxx = 1; oxxxxxxxxxxxxxxxxxxxxxx < tReligion.size(); oxxxxxxxxxxxxxxxxxxxxxx++) {
                     if (tIncomeTaxation.get(toAddID) < tIncomeTaxation.get(oxxxxxxxxxxxxxxxxxxxxxx)) {
                        toAddID = oxxxxxxxxxxxxxxxxxxxxxx;
                     }
                  }
               } else if (iSortID == 5) {
                  for (int oxxxxxxxxxxxxxxxxxxxxxxx = 1; oxxxxxxxxxxxxxxxxxxxxxxx < tReligion.size(); oxxxxxxxxxxxxxxxxxxxxxxx++) {
                     if (tIncomeTaxation.get(toAddID) > tIncomeTaxation.get(oxxxxxxxxxxxxxxxxxxxxxxx)) {
                        toAddID = oxxxxxxxxxxxxxxxxxxxxxxx;
                     }
                  }
               }

               buttonX = Images.boxTitleBORDERWIDTH + CFG.PADDING;
               menuElements.add(
                  new ButtonStatsRectIMG_Religion(
                     Game.religionManager.getReligion(tReligion.get(toAddID)).Name,
                     tReligion.get(toAddID),
                     buttonX,
                     buttonY,
                     r0W2,
                     buttonH,
                     ImageManager.getImage(Images.religion).getWidth(),
                     0
                  ) {
                     @Override
                     public void buildElementHover() {
                        List<MenuElement_HoverElement> nElements = new ArrayList<>();
                        List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                        nData.add(
                           new MenuElement_HoverElement_Type_TextTitle(Game.religionManager.getReligion(this.imageID).Name, CFG.FONT_BOLD, Colors.HOVER_GOLD)
                        );
                        nData.add(new MenuElement_HoverElement_Type_ReligionTitle(this.imageID, CFG.PADDING, 0));
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        nData.add(new MenuElement_HoverElement_Type_Line());
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        float tTax = 0.0F;
                        float tIncome = 0.0F;
                        int tNum = 0;

                        for (int i = 0; i < Game.getProvincesSize(); i++) {
                           if (Game.getProvince(i).getCivID() > 0 && Game.getProvince(i).getReligion() == this.imageID) {
                              tTax += Game.getProvince(i).getTaxEfficiencyWithBonuses();
                              tIncome += Game.getProvince(i).fProvinceIncomeTaxation;
                              tNum++;
                           }
                        }

                        nData.add(
                           new MenuElement_HoverElement_Type_Button_TextBonus(
                              Game.lang.get("Average") + ", " + Game.lang.get("TaxEfficiency") + ": ",
                              CFG.getPrecision2(tTax / tNum, 100) + "%",
                              Images.tax,
                              CFG.FONT_REGULAR_SMALL,
                              CFG.FONT_BOLD_SMALL,
                              Colors.HOVER_LEFT,
                              Colors.HOVER_GOLD
                           )
                        );
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        nData.add(
                           new MenuElement_HoverElement_Type_Button_TextBonus(
                              Game.lang.get("IncomeTaxation") + ": ",
                              CFG.getPrecision2(tIncome, 100),
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
               buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
               menuElements.add(new Text_StaticBG(CFG.getPrecision2(tTax.get(toAddID), 100) + "%", CFG.FONT_REGULAR_SMALL, -1, buttonX, buttonY, r1W2, buttonH));
               buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
               menuElements.add(
                  new Text_StaticBG(CFG.getPrecision2(tIncomeTaxation.get(toAddID), 100), CFG.FONT_REGULAR_SMALL, -1, buttonX, buttonY, r2W2, buttonH)
               );
               buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
               buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
               tReligion.remove(toAddID);
               tTax.remove(toAddID);
               tIncomeTaxation.remove(toAddID);
               tProvinces.remove(toAddID);
            }
         }
      } catch (Exception var25) {
         CFG.exceptionStack(var25);
      }

      buttonY = 0;
      int ixxxx = 0;

      for (int iSize = menuElements.size(); ixxxx < iSize; ixxxx++) {
         if (buttonY < menuElements.get(ixxxx).getPosY() + menuElements.get(ixxxx).getHeight() + CFG.PADDING) {
            buttonY = menuElements.get(ixxxx).getPosY() + menuElements.get(ixxxx).getHeight() + CFG.PADDING;
         }
      }

      ixxxx = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - (CFG.GAME_HEIGHT - Game.menuManager.getInGame_MapModesPosY() + CFG.PADDING * 3));
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, ixxxx)));
      this.initMenu(new MenuTitleIMG(Game.lang.get("TaxEfficiency"), false, false, Images.title600) {
         @Override
         public void draw(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, Status titleStatus) {
            ImageManager.getImage(this.imageID).draw2(oSB, nPosX, nPosY - this.getHeight(), nWidth, this.getHeight());
            this.drawGradient(oSB, nPosX, nPosY, nWidth, titleStatus);
            this.drawText(oSB, nPosX + Images.boxTitleBORDERWIDTH / 2, nPosY, nWidth, titleStatus);
         }

         @Override
         public long getTime() {
            return InGame_RightTaxEfficiency.lTime2;
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
      return super.getVisible() && Game.mapBG.getHideMenuZoomOut() && !Game.menuManager.getVisibleInGame_CivBonuses();
   }
}
