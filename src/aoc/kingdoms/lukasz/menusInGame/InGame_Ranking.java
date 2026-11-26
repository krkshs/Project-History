package aoc.kingdoms.lukasz.menusInGame;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Keyboard;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.civilization.CivilizationRanking;
import aoc.kingdoms.lukasz.map.province.ProvinceBorderManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Bonuses_Style;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Rank_Ranking;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonusFlag;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Image;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_EconomyRanking;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID_FlagCiv_Religion;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID_Special;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_Write;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_TitleBlueSort;
import aoc.kingdoms.lukasz.menusInGame.Civ.InGame_Civ;
import aoc.kingdoms.lukasz.menusInGame.Civ.InGame_Civ_Compare;
import aoc.kingdoms.lukasz.menusInGame.Civ.InGame_Civ_Economy;
import aoc.kingdoms.lukasz.menusInGame.Civ.InGame_Civ_Population;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_CourtOptions2;
import aoc.kingdoms.lukasz.menusInGame.Graph.InGame_GraphPopulation;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_Ranking extends Menu {
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static long lTime2 = 0L;
   public static int iSortID = 0;
   public static String sSearch = "";

   public InGame_Ranking() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2 + Images.boxTitleBORDERWIDTH;
      int titleHeight = ImageManager.getImage(Images.title600).getHeight();
      int menuWidth = ImageManager.getImage(Images.title600).getWidth();
      int menuX = InGame_CourtOptions2.getOtherMenuPosX_2();
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title1Red).getHeight();
      int buttonY = CFG.PADDING;
      int buttonX = Images.boxTitleBORDERWIDTH;
      int r0W0 = (int)((menuWidth - Images.boxTitleBORDERWIDTH * 2) * 0.15F);
      int r0W = (int)((menuWidth - Images.boxTitleBORDERWIDTH * 2) * 0.4F);
      int r1W = (int)((menuWidth - Images.boxTitleBORDERWIDTH * 2) * 0.15F);
      int c0W = (int)((menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 4) / 3.0F);
      int buttonH = CFG.BUTTON_HEIGHT3;
      int searchButtonW = (menuWidth - paddingLeft * 2 - CFG.PADDING) / 2;
      menuElements.add(
         new Text_StaticBG_Write(Game.lang.get("Search") + ": ", CFG.FONT_REGULAR_SMALL, CFG.PADDING * 2, paddingLeft, buttonY, searchButtonW, buttonH) {
            @Override
            public String getTextToDraw() {
               return InGame_Ranking.sSearch + Keyboard.getKeyboardVerticalLine();
            }

            @Override
            public void actionElement() {
               Game.keyboard.showKeyboard(Keyboard.KeyboardActionType.SEARCH_RANKING, InGame_Ranking.sSearch);
            }
         }
      );
      int civsInGame = 0;

      for (int i = 0; i < Game.getCivsSize(); i++) {
         if (Game.getCiv(i).getNumOfProvinces() > 0) {
            civsInGame++;
         }
      }

      menuElements.add(
         new ButtonStatsRectIMG_Bonuses_Style(
            Game.lang.get("Civilizations") + ": ",
            "" + civsInGame,
            Images.council,
            paddingLeft + searchButtonW + CFG.PADDING,
            buttonY,
            searchButtonW,
            buttonH,
            ImageManager.getImage(Images.battleWidth).getWidth()
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      buttonX = Images.boxTitleBORDERWIDTH;
      menuElements.add(
         new Text_TitleBlueSort(
            iSortID == 0 || iSortID == 1,
            iSortID == 1,
            Game.lang.get("Ranking"),
            -1,
            buttonX,
            buttonY,
            r0W0,
            CFG.TEXT_HEIGHT + CFG.PADDING * 6,
            CFG.FONT_BOLD_SMALL
         ) {
            @Override
            public void drawLines(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            }

            @Override
            public void actionElement() {
               if (InGame_Ranking.iSortID == 0) {
                  InGame_Ranking.iSortID = 1;
               } else {
                  InGame_Ranking.iSortID = 0;
               }

               Game.menuManager.rebuildInGame_CurrentSituation_Ranking();
               InGame_Ranking.lTime = 0L;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("SortBy") + ": "));
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Ranking"), Colors.HOVER_GOLD));
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
               if (InGame_Ranking.iSortID == 2) {
                  InGame_Ranking.iSortID = 3;
               } else {
                  InGame_Ranking.iSortID = 2;
               }

               Game.menuManager.rebuildInGame_CurrentSituation_Ranking();
               InGame_Ranking.lTime = 0L;
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
            iSortID == 4 || iSortID == 5,
            iSortID == 5,
            Game.lang.get("Prestige"),
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
               if (InGame_Ranking.iSortID == 4) {
                  InGame_Ranking.iSortID = 5;
               } else {
                  InGame_Ranking.iSortID = 4;
               }

               Game.menuManager.rebuildInGame_CurrentSituation_Ranking();
               InGame_Ranking.lTime = 0L;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("SortBy") + ": "));
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Prestige"), Colors.HOVER_GOLD));
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
            Game.lang.get("Economy"),
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
               if (InGame_Ranking.iSortID == 6) {
                  InGame_Ranking.iSortID = 7;
               } else {
                  InGame_Ranking.iSortID = 6;
               }

               Game.menuManager.rebuildInGame_CurrentSituation_Ranking();
               InGame_Ranking.lTime = 0L;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("SortBy") + ": "));
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Economy"), Colors.HOVER_GOLD));
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
            Game.lang.get("Population"),
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
               if (InGame_Ranking.iSortID == 8) {
                  InGame_Ranking.iSortID = 9;
               } else {
                  InGame_Ranking.iSortID = 8;
               }

               Game.menuManager.rebuildInGame_CurrentSituation_Ranking();
               InGame_Ranking.lTime = 0L;
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
      r0W0 = (int)((menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 6) * 0.15F);
      r0W = (int)((menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 6) * 0.4F);
      r1W = (int)((menuWidth - Images.boxTitleBORDERWIDTH * 2 - CFG.PADDING * 6) * 0.15F);
      buttonH = CFG.isDesktop() ? CFG.BUTTON_HEIGHT3 : CFG.BUTTON_HEIGHT2;
      List<Integer> tCivs = new ArrayList<>();
      List<Float> tEconomy = new ArrayList<>();
      List<Long> tPopulationTotal = new ArrayList<>();
      if (sSearch.length() > 0) {
         String tempSearch = sSearch.toLowerCase();

         for (int ix = 1; ix < Game.getCivsSize(); ix++) {
            if (Game.getCiv(ix).getNumOfProvinces() > 0
               && Game.getCiv(ix).iCivRankPosition >= 0
               && Game.getCiv(ix).getCivName().toLowerCase().indexOf(tempSearch) != -1) {
               tCivs.add(ix);
               tEconomy.add(Game.getCiv(ix).getEconomyTotal());
               tPopulationTotal.add(Game.getCiv(ix).getPopulationTotal());
            }
         }
      } else {
         for (int ixx = 1; ixx < Game.getCivsSize(); ixx++) {
            if (Game.getCiv(ixx).getNumOfProvinces() > 0 && Game.getCiv(ixx).iCivRankPosition >= 0) {
               tCivs.add(ixx);
               tEconomy.add(Game.getCiv(ixx).getEconomyTotal());
               tPopulationTotal.add(Game.getCiv(ixx).getPopulationTotal());
            }
         }
      }

      while (tCivs.size() > 0) {
         int toAddID = 0;
         if (iSortID == 0) {
            for (int o = 1; o < tCivs.size(); o++) {
               if (Game.getCiv(tCivs.get(toAddID)).iCivRankPosition > Game.getCiv(tCivs.get(o)).iCivRankPosition) {
                  toAddID = o;
               }
            }
         } else if (iSortID == 1) {
            for (int ox = 1; ox < tCivs.size(); ox++) {
               if (Game.getCiv(tCivs.get(toAddID)).iCivRankPosition < Game.getCiv(tCivs.get(ox)).iCivRankPosition) {
                  toAddID = ox;
               }
            }
         } else if (iSortID == 2) {
            for (int oxx = 1; oxx < tCivs.size(); oxx++) {
               if (CFG.compareAlphabetic_TwoString(Game.getCiv(tCivs.get(toAddID)).getCivName(), Game.getCiv(tCivs.get(oxx)).getCivName())) {
                  toAddID = oxx;
               }
            }
         } else if (iSortID == 3) {
            for (int oxxx = 1; oxxx < tCivs.size(); oxxx++) {
               if (CFG.compareAlphabetic_TwoString(Game.getCiv(tCivs.get(oxxx)).getCivName(), Game.getCiv(tCivs.get(toAddID)).getCivName())) {
                  toAddID = oxxx;
               }
            }
         } else if (iSortID == 4) {
            for (int oxxxx = 1; oxxxx < tCivs.size(); oxxxx++) {
               if (Game.getCiv(tCivs.get(toAddID)).iCivRankScore < Game.getCiv(tCivs.get(oxxxx)).iCivRankScore) {
                  toAddID = oxxxx;
               }
            }
         } else if (iSortID == 5) {
            for (int oxxxxx = 1; oxxxxx < tCivs.size(); oxxxxx++) {
               if (Game.getCiv(tCivs.get(toAddID)).iCivRankScore > Game.getCiv(tCivs.get(oxxxxx)).iCivRankScore) {
                  toAddID = oxxxxx;
               }
            }
         } else if (iSortID == 6) {
            for (int oxxxxxx = 1; oxxxxxx < tCivs.size(); oxxxxxx++) {
               if (tEconomy.get(toAddID) < tEconomy.get(oxxxxxx)) {
                  toAddID = oxxxxxx;
               }
            }
         } else if (iSortID == 7) {
            for (int oxxxxxxx = 1; oxxxxxxx < tCivs.size(); oxxxxxxx++) {
               if (tEconomy.get(toAddID) > tEconomy.get(oxxxxxxx)) {
                  toAddID = oxxxxxxx;
               }
            }
         } else if (iSortID == 8) {
            for (int oxxxxxxxx = 1; oxxxxxxxx < tCivs.size(); oxxxxxxxx++) {
               if (tPopulationTotal.get(toAddID) < tPopulationTotal.get(oxxxxxxxx)) {
                  toAddID = oxxxxxxxx;
               }
            }
         } else if (iSortID == 9) {
            for (int oxxxxxxxxx = 1; oxxxxxxxxx < tCivs.size(); oxxxxxxxxx++) {
               if (tPopulationTotal.get(toAddID) > tPopulationTotal.get(oxxxxxxxxx)) {
                  toAddID = oxxxxxxxxx;
               }
            }
         }

         menuElements.add(
            new ButtonStatsRectIMG_Rank_Ranking(
               "" + Game.getCiv(tCivs.get(toAddID)).iCivRankPosition,
               CivilizationRanking.getCivilizationRanking_IMG_STAR_CIVID(tCivs.get(toAddID)),
               paddingLeft,
               buttonY,
               r0W0,
               buttonH,
               ImageManager.getImage(Images.rankGold).getWidth()
            ) {
               int id = 0;

               @Override
               public void setCurrent(int nCurrent) {
                  this.id = nCurrent;
               }

               @Override
               protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
                  super.drawButtonBG(oSB, iTranslateX, iTranslateY, isActive);
                  if (this.id == InGame_Civ_Compare.civLeft_Rank) {
                     oSB.setColor(Colors.COLOR_NOTIFICATION_OVER_GREEN);
                     Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
                     oSB.setColor(Color.WHITE);
                  }
               }

               @Override
               public void actionElement() {
                  if (InGame_Civ_Compare.civLeft_Rank > 0) {
                     if (InGame_Civ_Compare.civLeft == this.id) {
                        Game.menuManager
                           .addToast_Error(
                              Game.lang.get("Compare")
                                 + ": "
                                 + Game.getCiv(InGame_Civ_Compare.civLeft).getCivName()
                                 + " == "
                                 + Game.getCiv(this.id).getCivName()
                           );
                     } else {
                        InGame_Civ_Compare.civRight_Rank = this.id;
                        InGame_Civ_Compare.civLeft = InGame_Civ_Compare.civLeft_Rank;
                        InGame_Civ_Compare.civRight = InGame_Civ_Compare.civRight_Rank;
                        Game.menuManager.rebuildInGame_Civ_Compare();
                     }
                  } else {
                     InGame_Civ_Compare.civLeft_Rank = this.id;
                  }
               }

               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("CompareCivilizations"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.compare, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  if (InGame_Civ_Compare.civLeft_Rank > 0) {
                     nData.add(
                        new MenuElement_HoverElement_Type_Button_TextBonusFlag(
                           Game.lang.get("Compare") + ": ",
                           Game.getCiv(InGame_Civ_Compare.civLeft_Rank).getCivName(),
                           InGame_Civ_Compare.civLeft_Rank,
                           CFG.FONT_REGULAR,
                           CFG.FONT_BOLD,
                           Colors.HOVER_LEFT,
                           Colors.HOVER_GOLD
                        )
                     );
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     nData.add(
                        new MenuElement_HoverElement_Type_Button_TextBonusFlag(
                           Game.lang.get("Compare") + ": ", "--", 0, CFG.FONT_REGULAR, CFG.FONT_BOLD, Colors.HOVER_LEFT, Colors.HOVER_GOLD
                        )
                     );
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                  } else {
                     nData.add(
                        new MenuElement_HoverElement_Type_Button_TextBonusFlag(
                           Game.lang.get("Compare") + ": ", "--", 0, CFG.FONT_REGULAR, CFG.FONT_BOLD, Colors.HOVER_LEFT, Colors.HOVER_GOLD
                        )
                     );
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     nData.add(
                        new MenuElement_HoverElement_Type_Button_TextBonusFlag(
                           Game.lang.get("Compare") + ": ", "--", 0, CFG.FONT_REGULAR, CFG.FONT_BOLD, Colors.HOVER_LEFT, Colors.HOVER_GOLD
                        )
                     );
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                  }

                  nData.add(new MenuElement_HoverElement_Type_Line());
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        Game.lang.get("CivilizationRank") + ": ",
                        this.getText(),
                        this.imageID,
                        CFG.FONT_REGULAR,
                        CFG.FONT_BOLD,
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
         menuElements.get(menuElements.size() - 1).setCurrent(tCivs.get(toAddID));
         buttonX = paddingLeft + menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         menuElements.add(
            new Text_StaticBG_ID_FlagCiv_Religion(
               "" + Game.getCiv(tCivs.get(toAddID)).getCivName(), CFG.FONT_REGULAR_SMALL, CFG.PADDING * 2, buttonX, buttonY, r0W, buttonH, tCivs.get(toAddID)
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
                     }
                  }
               }

               @Override
               public void buildElementHover() {
                  this.menuElementHover = CivilizationRanking.getHover_CivilizationRanking(this.getCurrent(), false, false);
               }
            }
         );
         buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         menuElements.add(
            new Text_StaticBG_ID_Special(
               "" + CFG.getPrecision2(Game.getCiv(tCivs.get(toAddID)).iCivRankScore, 1),
               CFG.FONT_REGULAR_SMALL,
               -1,
               buttonX,
               buttonY,
               r1W,
               buttonH,
               tCivs.get(toAddID)
            ) {
               @Override
               public void buildElementHover() {
                  this.menuElementHover = CivilizationRanking.getHover_CivilizationRanking(this.getCurrent(), false, false);
               }

               @Override
               public void actionElement() {
                  InGame_GraphPopulation.activeModeID = 6;
                  Game.menuManager.rebuildInGame_GraphPopulation();
               }
            }
         );
         buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         menuElements.add(
            new Text_StaticBG_EconomyRanking(
               "" + CFG.getNumberWithSpaces("" + CFG.getPrecision2(tEconomy.get(toAddID), 1)),
               CFG.FONT_REGULAR_SMALL,
               -1,
               buttonX,
               buttonY,
               r1W,
               buttonH,
               tCivs.get(toAddID)
            ) {
               @Override
               public void actionElement() {
                  InGame_Civ.iActiveCivID = this.id;
                  InGame_Civ_Economy.goBackToRank = true;
                  Game.menuManager.rebuildInGame_Civ_Economy();
               }

               @Override
               public void actionElementPPM() {
                  InGame_GraphPopulation.activeModeID = 1;
                  Game.menuManager.rebuildInGame_GraphPopulation();
               }
            }
         );
         buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         menuElements.add(
            new Text_StaticBG_ID(
               "" + CFG.getShortNumber(tPopulationTotal.get(toAddID)), CFG.FONT_REGULAR_SMALL, -1, buttonX, buttonY, r1W, buttonH, tCivs.get(toAddID)
            ) {
               @Override
               public void actionElement() {
                  InGame_Civ.iActiveCivID = this.id;
                  InGame_Civ_Population.goBackToRank = true;
                  Game.menuManager.rebuildInGame_Civ_Population();
               }

               @Override
               public void actionElementPPM() {
                  InGame_GraphPopulation.activeModeID = 0;
                  Game.menuManager.rebuildInGame_GraphPopulation();
               }

               @Override
               protected Color getColor(boolean isActive) {
                  return Colors.COLOR_POPULATION;
               }

               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(
                     new MenuElement_HoverElement_Type_TextTitle_BG(
                        Game.getCiv(this.id).getCivName() + ": " + Game.lang.get("Population"), CFG.FONT_BOLD, Colors.HOVER_GOLD
                     )
                  );
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.population, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        Game.lang.get("Population") + ": ",
                        "" + CFG.getNumberWithSpaces("" + Game.getCiv(this.id).getPopulationTotal()),
                        Images.population,
                        CFG.FONT_REGULAR_SMALL,
                        CFG.FONT_BOLD_SMALL,
                        Colors.HOVER_LEFT,
                        Colors.COLOR_POPULATION
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        Game.lang.get("Ranking") + ": ",
                        "#" + InGame_Civ.getRankPopulation(this.id) + " / " + InGame_Civ.getRankCivsWithProvinces(this.id),
                        CivilizationRanking.getCivilizationRanking_IMG_STAR_CIVID(this.id),
                        CFG.FONT_REGULAR_SMALL,
                        CFG.FONT_BOLD_SMALL,
                        Colors.HOVER_LEFT,
                        Colors.HOVER_RIGHT
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(new MenuElement_HoverElement_Type_Line());
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(Game.lang.get("GrowthRate") + ", " + Game.lang.get("Average") + ": ", CFG.FONT_REGULAR_SMALL)
                  );
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        "" + CFG.getPrecision2(Game.getCiv(this.id).getAverageGrowthRate(), 100) + "%", CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD
                     )
                  );
                  nData.add(new MenuElement_HoverElement_Type_Image(Images.populationGrowth, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements);
               }
            }
         );
         buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         tCivs.remove(toAddID);
         tEconomy.remove(toAddID);
         tPopulationTotal.remove(toAddID);
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
      this.initMenu(new MenuTitleIMG(Game.lang.get("CivilizationRanking"), false, false, Images.title600) {
         @Override
         public long getTime() {
            return InGame_Ranking.lTime2;
         }
      }, menuX, menuY, menuWidth, ixxx, menuElements, false, true);
      this.drawScrollPositionAlways = false;
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      if (lTime + 60L >= CFG.currentTimeMillis) {
         iTranslateY = iTranslateY - CFG.BUTTON_HEIGHT + (int)(CFG.BUTTON_HEIGHT * ((float)(CFG.currentTimeMillis - lTime) / 60.0F));
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
