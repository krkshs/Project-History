package aoc.kingdoms.lukasz.menusInGame.Right;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.province.ProvinceBorderManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG_DoubleText;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Religion;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRect_Active;
import aoc.kingdoms.lukasz.menu_element.graph.Graph_Vertical;
import aoc.kingdoms.lukasz.menu_element.graph.Graph_Vertical_Data_Type;
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
import aoc.kingdoms.lukasz.menusInGame.Graph.InGame_GraphPopulation;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_RightInfrastructure extends Menu {
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static long lTime2 = 0L;
   public static int iModeID = 0;
   public static int iSortID = 2;
   public static float totalInfrastructure = 0.0F;

   public InGame_RightInfrastructure() {
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
      totalInfrastructure = 0.0F;

      for (int i = 0; i < Game.getProvincesSize(); i++) {
         if (Game.getProvince(i).getCivID() > 0) {
            totalInfrastructure = totalInfrastructure + Game.getProvince(i).getInfrastructure();
         }
      }

      menuElements.add(
         new ButtonStatsRect_Active(
            Game.lang.get("Civilizations"), buttonX, buttonYPadding, c0W, buttonH, iModeID == 0 ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL
         ) {
            @Override
            public void actionElement() {
               if (InGame_RightInfrastructure.iModeID != 0) {
                  InGame_RightInfrastructure.iModeID = 0;
                  Game.menuManager.rebuildInGame_RightInfrastructure();
                  InGame_RightInfrastructure.lTime = 0L;
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
               return InGame_RightInfrastructure.iModeID == 0 ? Colors.HOVER_GOLD : super.getColor(isActive);
            }
         }
      );
      menuElements.add(
         new ButtonStatsRect_Active(
            Game.lang.get("Provinces"), buttonX + CFG.PADDING + c0W, buttonYPadding, c0W, buttonH, iModeID == 1 ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL
         ) {
            @Override
            public void actionElement() {
               if (InGame_RightInfrastructure.iModeID != 1) {
                  InGame_RightInfrastructure.iModeID = 1;
                  Game.menuManager.rebuildInGame_RightInfrastructure();
                  InGame_RightInfrastructure.lTime = 0L;
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
               return InGame_RightInfrastructure.iModeID == 1 ? Colors.HOVER_GOLD : super.getColor(isActive);
            }
         }
      );
      int buttonY = buttonYPadding + menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new ButtonStatsRect_Active(Game.lang.get("Continents"), buttonX, buttonY, c0W, buttonH, iModeID == 2 ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL) {
            @Override
            public void actionElement() {
               if (InGame_RightInfrastructure.iModeID != 2) {
                  InGame_RightInfrastructure.iModeID = 2;
                  Game.menuManager.rebuildInGame_RightInfrastructure();
                  InGame_RightInfrastructure.lTime = 0L;
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
               return InGame_RightInfrastructure.iModeID == 2 ? Colors.HOVER_GOLD : super.getColor(isActive);
            }
         }
      );
      menuElements.add(
         new ButtonStatsRect_Active(
            Game.lang.get("Religion"), buttonX + CFG.PADDING + c0W, buttonY, c0W, buttonH, iModeID == 3 ? CFG.FONT_BOLD_SMALL : CFG.FONT_REGULAR_SMALL
         ) {
            @Override
            public void actionElement() {
               if (InGame_RightInfrastructure.iModeID != 3) {
                  InGame_RightInfrastructure.iModeID = 3;
                  Game.menuManager.rebuildInGame_RightInfrastructure();
                  InGame_RightInfrastructure.lTime = 0L;
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
               return InGame_RightInfrastructure.iModeID == 3 ? Colors.HOVER_GOLD : super.getColor(isActive);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      if (iModeID == 0 && totalInfrastructure > 0.0F) {
         Graph_Vertical graphVertical = new Graph_Vertical(
            Graph_Vertical_Data_Type.CIVS_INFRASTRUCTURE,
            Game.lang.get("Civilizations"),
            Game.lang.get("Infrastructure"),
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            menuWidth / 2,
            true
         ) {
            @Override
            public void actionElement() {
               InGame_GraphPopulation.activeModeID = 3;
               Game.menuManager.rebuildInGame_GraphPopulation();
            }
         };
         graphVertical.allowStatisticsMode = false;
         menuElements.add(graphVertical);
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      }

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
               if (InGame_RightInfrastructure.iSortID == 0) {
                  InGame_RightInfrastructure.iSortID = 1;
               } else {
                  InGame_RightInfrastructure.iSortID = 0;
               }

               Game.menuManager.rebuildInGame_RightInfrastructure();
               InGame_RightInfrastructure.lTime = 0L;
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
            iModeID == 1 ? Game.lang.get("Economy") : Game.lang.get("Infrastructure"),
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
               if (InGame_RightInfrastructure.iSortID == 2) {
                  InGame_RightInfrastructure.iSortID = 3;
               } else {
                  InGame_RightInfrastructure.iSortID = 2;
               }

               Game.menuManager.rebuildInGame_RightInfrastructure();
               InGame_RightInfrastructure.lTime = 0L;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("SortBy") + ": "));
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Infrastructure"), Colors.HOVER_GOLD));
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
            iModeID == 1 ? Game.lang.get("Infrastructure") : Game.lang.get("World"),
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
               if (InGame_RightInfrastructure.iSortID == 4) {
                  InGame_RightInfrastructure.iSortID = 5;
               } else {
                  InGame_RightInfrastructure.iSortID = 4;
               }

               Game.menuManager.rebuildInGame_RightInfrastructure();
               InGame_RightInfrastructure.lTime = 0L;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("SortBy") + ": "));
               nData.add(
                  new MenuElement_HoverElement_Type_TextTitle(
                     InGame_RightInfrastructure.iModeID == 1 ? Game.lang.get("Infrastructure") : Game.lang.get("World"), Colors.HOVER_GOLD
                  )
               );
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
            List<Integer> tInfrastructure = new ArrayList<>();
            List<Float> tPercOfWorld = new ArrayList<>();

            for (int ix = 1; ix < Game.getCivsSize(); ix++) {
               if (Game.getCiv(ix).getNumOfProvinces() > 0) {
                  tCivs.add(ix);
                  tInfrastructure.add(Game.getCiv(ix).getInfrastructure());
                  tPercOfWorld.add(Game.getCiv(ix).getInfrastructure() / totalInfrastructure * 100.0F);
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
                     if (tInfrastructure.get(toAddID) < tInfrastructure.get(oxx)) {
                        toAddID = oxx;
                     }
                  }
               } else if (iSortID == 3) {
                  for (int oxxx = 1; oxxx < tCivs.size(); oxxx++) {
                     if (tInfrastructure.get(toAddID) > tInfrastructure.get(oxxx)) {
                        toAddID = oxxx;
                     }
                  }
               } else if (iSortID == 4) {
                  for (int oxxxx = 1; oxxxx < tCivs.size(); oxxxx++) {
                     if (tPercOfWorld.get(toAddID) < tPercOfWorld.get(oxxxx)) {
                        toAddID = oxxxx;
                     }
                  }
               } else if (iSortID == 5) {
                  for (int oxxxxx = 1; oxxxxx < tCivs.size(); oxxxxx++) {
                     if (tPercOfWorld.get(toAddID) > tPercOfWorld.get(oxxxxx)) {
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
                              Game.lang.get("Infrastructure") + ": ",
                              CFG.getPrecision2((float)Game.getCiv(this.getCurrent()).getInfrastructure(), 10),
                              Images.infrastructure,
                              CFG.FONT_REGULAR_SMALL,
                              CFG.FONT_BOLD_SMALL,
                              Colors.HOVER_LEFT,
                              Colors.HOVER_GOLD
                           )
                        );
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        float fEco = 0.0F;

                        for (int i = 0; i < Game.getCiv(this.getCurrent()).getNumOfProvinces(); i++) {
                           fEco += Game.getProvince(Game.getCiv(this.getCurrent()).getProvinceID(i)).getInfrastructure();
                        }

                        nData.add(
                           new MenuElement_HoverElement_Type_Button_TextBonus(
                              Game.lang.get("Average") + ": ",
                              CFG.getPrecision2(fEco / Game.getCiv(this.getCurrent()).getNumOfProvinces(), 100),
                              Images.infrastructure,
                              CFG.FONT_REGULAR_SMALL,
                              CFG.FONT_BOLD_SMALL,
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
                              Game.lang.get("World") + ": ",
                              ""
                                 + CFG.getPrecision2(
                                    Game.getCiv(this.getCurrent()).getInfrastructure() / InGame_RightInfrastructure.totalInfrastructure * 100.0F, 100
                                 )
                                 + "%",
                              Images.infrastructure,
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
                     CFG.getPrecision2((float)tInfrastructure.get(toAddID).intValue(), 10), CFG.FONT_REGULAR_SMALL, -1, buttonX, buttonY, r1W2, buttonH
                  )
               );
               buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
               menuElements.add(
                  new Text_StaticBG(CFG.getPrecision2(tPercOfWorld.get(toAddID), 100) + "%", CFG.FONT_REGULAR_SMALL, -1, buttonX, buttonY, r2W2, buttonH)
               );
               buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
               buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
               tCivs.remove(toAddID);
               tInfrastructure.remove(toAddID);
               tPercOfWorld.remove(toAddID);
            }
         } else if (iModeID == 1) {
            List<Integer> tProvinces = new ArrayList<>();
            List<Float> tEconomy = new ArrayList<>();
            List<Integer> tInfrastructure = new ArrayList<>();

            for (int ixx = 0; ixx < Game.getProvincesSize(); ixx++) {
               if (Game.getProvince(ixx).getCivID() > 0) {
                  tProvinces.add(ixx);
                  tEconomy.add(Game.getProvince(ixx).getEconomyWithBonuses());
                  tInfrastructure.add(Game.getProvince(ixx).getInfrastructure());
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
                     if (tEconomy.get(toAddID) < tEconomy.get(oxxxxxxxx)) {
                        toAddID = oxxxxxxxx;
                     }
                  }
               } else if (iSortID == 3) {
                  for (int oxxxxxxxxx = 1; oxxxxxxxxx < tProvinces.size(); oxxxxxxxxx++) {
                     if (tEconomy.get(toAddID) > tEconomy.get(oxxxxxxxxx)) {
                        toAddID = oxxxxxxxxx;
                     }
                  }
               } else if (iSortID == 4) {
                  for (int oxxxxxxxxxx = 1; oxxxxxxxxxx < tProvinces.size(); oxxxxxxxxxx++) {
                     if (tInfrastructure.get(toAddID) < tInfrastructure.get(oxxxxxxxxxx)) {
                        toAddID = oxxxxxxxxxx;
                     }
                  }
               } else if (iSortID == 5) {
                  for (int oxxxxxxxxxxx = 1; oxxxxxxxxxxx < tProvinces.size(); oxxxxxxxxxxx++) {
                     if (tInfrastructure.get(toAddID) > tInfrastructure.get(oxxxxxxxxxxx)) {
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
                              Game.lang.get("Infrastructure") + ": ",
                              CFG.getNumberWithSpaces("" + Game.getProvince(this.getCurrent()).getInfrastructure()),
                              Images.infrastructure,
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
                              Game.lang.get("Economy") + ": ",
                              CFG.getPrecision2(Game.getProvince(this.getCurrent()).getEconomyWithBonuses(), 10),
                              Game_Calendar.IMG_ECONOMY,
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
               menuElements.add(new Text_StaticBG(CFG.getPrecision2(tEconomy.get(toAddID), 10), CFG.FONT_REGULAR_SMALL, -1, buttonX, buttonY, r1W2, buttonH));
               buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
               menuElements.add(
                  new Text_StaticBG(
                     CFG.getPrecision2((float)tInfrastructure.get(toAddID).intValue(), 1)
                        + " / "
                        + Game.getProvince(tProvinces.get(toAddID)).iInfrastructureMax,
                     CFG.FONT_REGULAR_SMALL,
                     -1,
                     buttonX,
                     buttonY,
                     r2W2,
                     buttonH
                  )
               );
               buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
               buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
               tProvinces.remove(toAddID);
               tEconomy.remove(toAddID);
               tInfrastructure.remove(toAddID);
            }
         } else if (iModeID == 2) {
            List<Integer> tContinents = new ArrayList<>();
            List<Integer> tInfrastructure = new ArrayList<>();
            List<Float> tPerc = new ArrayList<>();

            for (int ixxx = 0; ixxx < Game.continents.iContinentsSize; ixxx++) {
               tContinents.add(ixxx);
               tInfrastructure.add(0);
               tPerc.add(0.0F);
            }

            for (int ixxx = 0; ixxx < Game.getProvincesSize(); ixxx++) {
               if (Game.getProvince(ixxx).getCivID() > 0) {
                  tInfrastructure.set(
                     Game.getProvince(ixxx).getContinent(),
                     tInfrastructure.get(Game.getProvince(ixxx).getContinent()) + Game.getProvince(ixxx).getInfrastructure()
                  );
               }
            }

            tContinents.remove(0);
            tInfrastructure.remove(0);
            tPerc.remove(0);

            for (int oxxxxxxxxxxxx = 0; oxxxxxxxxxxxx < tContinents.size(); oxxxxxxxxxxxx++) {
               tPerc.set(oxxxxxxxxxxxx, tInfrastructure.get(oxxxxxxxxxxxx).intValue() / totalInfrastructure * 100.0F);
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
                     if (tInfrastructure.get(toAddID) < tInfrastructure.get(oxxxxxxxxxxxxxx)) {
                        toAddID = oxxxxxxxxxxxxxx;
                     }
                  }
               } else if (iSortID == 3) {
                  for (int oxxxxxxxxxxxxxxx = 1; oxxxxxxxxxxxxxxx < tContinents.size(); oxxxxxxxxxxxxxxx++) {
                     if (tInfrastructure.get(toAddID) > tInfrastructure.get(oxxxxxxxxxxxxxxx)) {
                        toAddID = oxxxxxxxxxxxxxxx;
                     }
                  }
               } else if (iSortID == 4) {
                  for (int oxxxxxxxxxxxxxxxx = 1; oxxxxxxxxxxxxxxxx < tContinents.size(); oxxxxxxxxxxxxxxxx++) {
                     if (tPerc.get(toAddID) < tPerc.get(oxxxxxxxxxxxxxxxx)) {
                        toAddID = oxxxxxxxxxxxxxxxx;
                     }
                  }
               } else if (iSortID == 5) {
                  for (int oxxxxxxxxxxxxxxxxx = 1; oxxxxxxxxxxxxxxxxx < tContinents.size(); oxxxxxxxxxxxxxxxxx++) {
                     if (tPerc.get(toAddID) > tPerc.get(oxxxxxxxxxxxxxxxxx)) {
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
                        float tPop = 0.0F;

                        for (int i = 0; i < Game.getProvincesSize(); i++) {
                           if (Game.getProvince(i).getCivID() > 0 && Game.getProvince(i).getContinent() == this.getCurrent()) {
                              tPop += Game.getProvince(i).getInfrastructure();
                           }
                        }

                        nData.add(
                           new MenuElement_HoverElement_Type_Button_TextBonus(
                              Game.lang.get("Infrastructure") + ": ",
                              CFG.getNumberWithSpaces("" + CFG.getPrecision2(tPop, 1)),
                              Images.infrastructure,
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
                     CFG.getNumberWithSpaces("" + CFG.getPrecision2((float)tInfrastructure.get(toAddID).intValue(), 1)),
                     CFG.FONT_REGULAR_SMALL,
                     -1,
                     buttonX,
                     buttonY,
                     r1W2,
                     buttonH
                  )
               );
               buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
               menuElements.add(new Text_StaticBG(CFG.getPrecision2(tPerc.get(toAddID), 10) + "%", CFG.FONT_REGULAR_SMALL, -1, buttonX, buttonY, r2W2, buttonH));
               buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
               buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
               tContinents.remove(toAddID);
               tInfrastructure.remove(toAddID);
               tPerc.remove(toAddID);
            }
         } else if (iModeID == 3) {
            List<Integer> tReligion = new ArrayList<>();
            List<Integer> tInfrastructure = new ArrayList<>();
            List<Float> tPerc = new ArrayList<>();

            for (int ixxxx = 0; ixxxx < Game.religionManager.getReligionsSize(); ixxxx++) {
               tReligion.add(ixxxx);
               tInfrastructure.add(0);
               tPerc.add(0.0F);
            }

            for (int ixxxx = 0; ixxxx < Game.getProvincesSize(); ixxxx++) {
               if (Game.getProvince(ixxxx).getCivID() > 0) {
                  tInfrastructure.set(
                     Game.getProvince(ixxxx).getReligion(),
                     tInfrastructure.get(Game.getProvince(ixxxx).getReligion()) + Game.getProvince(ixxxx).getInfrastructure()
                  );
               }
            }

            for (int oxxxxxxxxxxxxxxxxxx = 0; oxxxxxxxxxxxxxxxxxx < tReligion.size(); oxxxxxxxxxxxxxxxxxx++) {
               tPerc.set(oxxxxxxxxxxxxxxxxxx, tInfrastructure.get(oxxxxxxxxxxxxxxxxxx).intValue() / totalInfrastructure * 100.0F);
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
                     if (tInfrastructure.get(toAddID) < tInfrastructure.get(oxxxxxxxxxxxxxxxxxxxx)) {
                        toAddID = oxxxxxxxxxxxxxxxxxxxx;
                     }
                  }
               } else if (iSortID == 3) {
                  for (int oxxxxxxxxxxxxxxxxxxxxx = 1; oxxxxxxxxxxxxxxxxxxxxx < tReligion.size(); oxxxxxxxxxxxxxxxxxxxxx++) {
                     if (tInfrastructure.get(toAddID) > tInfrastructure.get(oxxxxxxxxxxxxxxxxxxxxx)) {
                        toAddID = oxxxxxxxxxxxxxxxxxxxxx;
                     }
                  }
               } else if (iSortID == 4) {
                  for (int oxxxxxxxxxxxxxxxxxxxxxx = 1; oxxxxxxxxxxxxxxxxxxxxxx < tReligion.size(); oxxxxxxxxxxxxxxxxxxxxxx++) {
                     if (tPerc.get(toAddID) < tPerc.get(oxxxxxxxxxxxxxxxxxxxxxx)) {
                        toAddID = oxxxxxxxxxxxxxxxxxxxxxx;
                     }
                  }
               } else if (iSortID == 5) {
                  for (int oxxxxxxxxxxxxxxxxxxxxxxx = 1; oxxxxxxxxxxxxxxxxxxxxxxx < tReligion.size(); oxxxxxxxxxxxxxxxxxxxxxxx++) {
                     if (tPerc.get(toAddID) > tPerc.get(oxxxxxxxxxxxxxxxxxxxxxxx)) {
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
                        float tPop = 0.0F;

                        for (int i = 0; i < Game.getProvincesSize(); i++) {
                           if (Game.getProvince(i).getCivID() > 0 && Game.getProvince(i).getReligion() == this.imageID) {
                              tPop += Game.getProvince(i).getInfrastructure();
                           }
                        }

                        nData.add(
                           new MenuElement_HoverElement_Type_Button_TextBonus(
                              Game.lang.get("Infrastructure") + ": ",
                              CFG.getNumberWithSpaces("" + CFG.getPrecision2(tPop, 1)),
                              Images.infrastructure,
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
                     CFG.getNumberWithSpaces("" + CFG.getPrecision2((float)tInfrastructure.get(toAddID).intValue(), 1)),
                     CFG.FONT_REGULAR_SMALL,
                     -1,
                     buttonX,
                     buttonY,
                     r1W2,
                     buttonH
                  )
               );
               buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
               menuElements.add(new Text_StaticBG(CFG.getPrecision2(tPerc.get(toAddID), 10) + "%", CFG.FONT_REGULAR_SMALL, -1, buttonX, buttonY, r2W2, buttonH));
               buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
               buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
               tReligion.remove(toAddID);
               tInfrastructure.remove(toAddID);
               tPerc.remove(toAddID);
            }
         }
      } catch (Exception var25) {
         CFG.exceptionStack(var25);
      }

      buttonY = 0;
      int ixxxxx = 0;

      for (int iSize = menuElements.size(); ixxxxx < iSize; ixxxxx++) {
         if (buttonY < menuElements.get(ixxxxx).getPosY() + menuElements.get(ixxxxx).getHeight() + CFG.PADDING) {
            buttonY = menuElements.get(ixxxxx).getPosY() + menuElements.get(ixxxxx).getHeight() + CFG.PADDING;
         }
      }

      ixxxxx = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - (CFG.GAME_HEIGHT - Game.menuManager.getInGame_MapModesPosY() + CFG.PADDING * 3));
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, ixxxxx)));
      this.initMenu(
         new MenuTitleIMG_DoubleText(
            Game.lang.get("Infrastructure"),
            Game.lang.get("Total") + ": " + CFG.getNumberWithSpaces("" + CFG.getPrecision2(totalInfrastructure, 1)),
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
               return InGame_RightInfrastructure.lTime2;
            }
         },
         menuX,
         menuY,
         menuWidth,
         ixxxxx,
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
