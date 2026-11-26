package aoc.kingdoms.lukasz.menusInGame.Court.World;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Keyboard;
import aoc.kingdoms.lukasz.jakowski.SoundsManager;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.province.ProvinceBorderManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonCurrentSituation;
import aoc.kingdoms.lukasz.menu_element.button.ButtonIdeology2;
import aoc.kingdoms.lukasz.menu_element.button.ButtonReligion2;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_FlagCiv_Title;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextIcon2;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID_FlagCiv_SpecialCiv;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID_FlagCiv_SpecialEmpty;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_Write;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_TitleBlueSort;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_Court;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_CourtOptions;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_CourtOptions2;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_Court_WorldCivs extends Menu {
   public static String sSearch = "";
   public static int iSortID = 2;

   public InGame_Court_WorldCivs() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = Images.boxTitleBORDERWIDTH + CFG.PADDING * 2;
      int paddingLeft2 = Images.boxTitleBORDERWIDTH + CFG.PADDING;
      int menuWidth = ImageManager.getImage(Images.insideTop500).getWidth();
      int menuX = CFG.PADDING * 2 + InGame_CourtOptions2.getMenuWidth();
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING;
      int buttonYPadding = CFG.PADDING * 2;
      int buttonX = paddingLeft;
      int buttonY = CFG.PADDING;
      menuElements.add(
         new ButtonCurrentSituation(
            Game.lang.get(GameValues.court.COUNCIL_NAME),
            Images.council,
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            CFG.BUTTON_HEIGHT4,
            ImageManager.getImage(Images.council).getWidth() + CFG.PADDING * 4,
            true
         ) {
            @Override
            public void actionElement() {
               InGame_Court.iActiveCivID = Game.player.iCivID;
               Game.menuManager.rebuildInGame_Court();
               Game.menuManager.setVisibleInGame_Court(true);
               InGame_Court.lTime = 0L;
               Game.setRegroupArmyMode(false);
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get(GameValues.court.COUNCIL_NAME), Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.council, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements, true);
            }

            @Override
            public int getSFX() {
               return SoundsManager.getClickSound_CivOptions();
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new Text_StaticBG_Write(
            Game.lang.get("Search") + ": ", CFG.FONT_REGULAR_SMALL, CFG.PADDING * 2, paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT2
         ) {
            @Override
            public String getTextToDraw() {
               return InGame_Court_WorldCivs.sSearch + Keyboard.getKeyboardVerticalLine();
            }

            @Override
            public void actionElement() {
               Game.keyboard.showKeyboard(Keyboard.KeyboardActionType.SEARCH_COURT_ALL_CIVS, InGame_Court_WorldCivs.sSearch);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      int buttonH = CFG.isDesktop() ? CFG.BUTTON_HEIGHT3 : CFG.BUTTON_HEIGHT2;
      int statsRightW = (menuWidth - paddingLeft * 2 - CFG.PADDING * 6) / 7;
      int statsRightH = CFG.BUTTON_HEIGHT;
      int emptyBGH = buttonH + CFG.PADDING + statsRightH;
      int r0W = (int)((menuWidth - Images.boxTitleBORDERWIDTH * 2) / 5.0F);
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
               if (InGame_Court_WorldCivs.iSortID == 0) {
                  InGame_Court_WorldCivs.iSortID = 1;
               } else {
                  InGame_Court_WorldCivs.iSortID = 0;
               }

               Game.menuManager.rebuildInGame_CourtSearchCivs();
               Game.menuManager.setVisibleInGame_Court(true);
               InGame_Court.lTime = 0L;
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
            r0W,
            CFG.TEXT_HEIGHT + CFG.PADDING * 6,
            CFG.FONT_BOLD_SMALL
         ) {
            @Override
            public void drawLines(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            }

            @Override
            public void actionElement() {
               if (InGame_Court_WorldCivs.iSortID == 2) {
                  InGame_Court_WorldCivs.iSortID = 3;
               } else {
                  InGame_Court_WorldCivs.iSortID = 2;
               }

               Game.menuManager.rebuildInGame_CourtSearchCivs();
               Game.menuManager.setVisibleInGame_Court(true);
               InGame_Court.lTime = 0L;
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
      buttonX += menuElements.get(menuElements.size() - 1).getWidth();
      menuElements.add(
         new Text_TitleBlueSort(
            iSortID == 4 || iSortID == 5,
            iSortID == 5,
            Game.lang.get("Provinces"),
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
               if (InGame_Court_WorldCivs.iSortID == 4) {
                  InGame_Court_WorldCivs.iSortID = 5;
               } else {
                  InGame_Court_WorldCivs.iSortID = 4;
               }

               Game.menuManager.rebuildInGame_CourtSearchCivs();
               Game.menuManager.setVisibleInGame_Court(true);
               InGame_Court.lTime = 0L;
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
            iSortID == 6 || iSortID == 7,
            iSortID == 7,
            Game.lang.get("Economy"),
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
               if (InGame_Court_WorldCivs.iSortID == 6) {
                  InGame_Court_WorldCivs.iSortID = 7;
               } else {
                  InGame_Court_WorldCivs.iSortID = 6;
               }

               Game.menuManager.rebuildInGame_CourtSearchCivs();
               Game.menuManager.setVisibleInGame_Court(true);
               InGame_Court.lTime = 0L;
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
            Game.lang.get("Technologies"),
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
               if (InGame_Court_WorldCivs.iSortID == 8) {
                  InGame_Court_WorldCivs.iSortID = 9;
               } else {
                  InGame_Court_WorldCivs.iSortID = 8;
               }

               Game.menuManager.rebuildInGame_CourtSearchCivs();
               Game.menuManager.setVisibleInGame_Court(true);
               InGame_Court.lTime = 0L;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("SortBy") + ": "));
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Technologies"), Colors.HOVER_GOLD));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      List<Integer> tCivs = new ArrayList<>();
      List<Long> tPop = new ArrayList<>();
      List<Float> tEco = new ArrayList<>();
      List<Integer> tTech = new ArrayList<>();
      if (sSearch.length() > 0) {
         int num = -1;

         try {
            num = Integer.parseInt(sSearch);
         } catch (Exception var23) {
         }

         if (num >= 0 && num < Game.getCivsSize()) {
            tCivs.add(num);
            tPop.add(Game.getCiv(num).getPopulationTotal());
            tEco.add(Game.getCiv(num).getEconomyTotal());
            tTech.add(Game.getCiv(num).getResearchedTechnologies());
         } else {
            String tSearch = sSearch.toLowerCase();

            for (int i = 0; i < Game.getProvincesSize(); i++) {
               if (Game.getCiv(i).getCivName().toLowerCase().indexOf(tSearch) >= 0) {
                  tCivs.add(i);
                  tPop.add(Game.getCiv(i).getPopulationTotal());
                  tEco.add(Game.getCiv(i).getEconomyTotal());
                  tTech.add(Game.getCiv(i).getResearchedTechnologies());
               }
            }
         }
      } else {
         for (int ix = 1; ix < Game.getCivsSize(); ix++) {
            tCivs.add(ix);
            tPop.add(Game.getCiv(ix).getPopulationTotal());
            tEco.add(Game.getCiv(ix).getEconomyTotal());
            tTech.add(Game.getCiv(ix).getResearchedTechnologies());
         }
      }

      if (tCivs.size() > 0) {
         int numOfAdded = 0;
         int limit = GameValues.inGame.COURT_PROVINCES_LIMIT * 2;

         while (tCivs.size() > 0 && numOfAdded++ < limit) {
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
                  if (tPop.get(oxx) > tPop.get(toAddID)) {
                     toAddID = oxx;
                  }
               }
            } else if (iSortID == 3) {
               for (int oxxx = 1; oxxx < tCivs.size(); oxxx++) {
                  if (tPop.get(oxxx) < tPop.get(toAddID)) {
                     toAddID = oxxx;
                  }
               }
            } else if (iSortID == 4) {
               for (int oxxxx = 1; oxxxx < tCivs.size(); oxxxx++) {
                  if (Game.getCiv(tCivs.get(oxxxx)).getNumOfProvinces() > Game.getCiv(tCivs.get(toAddID)).getNumOfProvinces()) {
                     toAddID = oxxxx;
                  }
               }
            } else if (iSortID == 5) {
               for (int oxxxxx = 1; oxxxxx < tCivs.size(); oxxxxx++) {
                  if (Game.getCiv(tCivs.get(oxxxxx)).getNumOfProvinces() < Game.getCiv(tCivs.get(toAddID)).getNumOfProvinces()) {
                     toAddID = oxxxxx;
                  }
               }
            } else if (iSortID == 6) {
               for (int oxxxxxx = 1; oxxxxxx < tCivs.size(); oxxxxxx++) {
                  if (tEco.get(oxxxxxx) > tEco.get(toAddID)) {
                     toAddID = oxxxxxx;
                  }
               }
            } else if (iSortID == 7) {
               for (int oxxxxxxx = 1; oxxxxxxx < tCivs.size(); oxxxxxxx++) {
                  if (tEco.get(oxxxxxxx) < tEco.get(toAddID)) {
                     toAddID = oxxxxxxx;
                  }
               }
            } else if (iSortID == 8) {
               for (int oxxxxxxxx = 1; oxxxxxxxx < tCivs.size(); oxxxxxxxx++) {
                  if (tTech.get(oxxxxxxxx) > tTech.get(toAddID)) {
                     toAddID = oxxxxxxxx;
                  }
               }
            } else if (iSortID == 9) {
               for (int oxxxxxxxxx = 1; oxxxxxxxxx < tCivs.size(); oxxxxxxxxx++) {
                  if (tTech.get(oxxxxxxxxx) < tTech.get(toAddID)) {
                     toAddID = oxxxxxxxxx;
                  }
               }
            }

            int nCivID = tCivs.get(toAddID);
            menuElements.add(
               new Text_StaticBG_ID_FlagCiv_SpecialCiv(
                  Game.getCiv(nCivID).getCivName(),
                  CFG.FONT_REGULAR,
                  CFG.PADDING * 2,
                  paddingLeft,
                  buttonY,
                  menuWidth - paddingLeft * 2,
                  buttonH,
                  nCivID,
                  Images.population,
                  CFG.getNumberWithSpaces("" + tPop.get(toAddID))
               ) {
                  @Override
                  public void actionElement() {
                     if (Game.getCiv(this.getCurrent()).getCapitalProvinceID() >= 0
                        && Game.getProvince(Game.getCiv(this.getCurrent()).getCapitalProvinceID()).getCivID() == this.getCurrent()) {
                        if (Game.iActiveProvince >= 0 && Game.getProvince(Game.iActiveProvince).getCivID() == this.getCurrent()) {
                           Game.menuManager.hideCourtCiv();
                           Game.menuManager.setVisibleInGame_Civ(false);
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
                     List<MenuElement_HoverElement> nElements = new ArrayList<>();
                     List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                     nData.add(
                        new MenuElement_HoverElement_Type_FlagCiv_Title(
                           this.id, Game.getProvince(Game.getCiv(this.id).getCapitalProvinceID()).getProvinceName()
                        )
                     );
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     nData.add(
                        new MenuElement_HoverElement_Type_Button_TextBonus(
                           Game.lang.get("Population") + ": ",
                           CFG.getNumberWithSpaces("" + Game.getCiv(this.id).getPopulationTotal()),
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
                           Game.lang.get("Economy") + ": ",
                           CFG.getPrecision2(Game.getCiv(this.id).getEconomyTotal(), 10),
                           Game_Calendar.IMG_ECONOMY,
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
                           Game.lang.get("Civilization") + " ID: ",
                           "" + this.id,
                           Images.world,
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

                  @Override
                  public Color getColor2(boolean isActive) {
                     return Colors.getColorPopulation(isActive, this.getIsHovered());
                  }
               }
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight();
            menuElements.add(new ButtonIdeology2(Game.getCiv(nCivID).getIdeologyID(), paddingLeft, buttonY, statsRightW * 2 + CFG.PADDING, statsRightH) {});
            buttonX = paddingLeft + menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
            menuElements.add(new ButtonReligion2(Game.getCiv(nCivID).getReligionID(), buttonX, buttonY, statsRightW * 2 + CFG.PADDING, statsRightH) {});
            buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
            menuElements.add(
               new TextIcon2(
                  "" + CFG.getNumberWithSpaces("" + Game.getCiv(nCivID).getNumOfProvinces()), Images.provinces, buttonX, buttonY, statsRightW, statsRightH
               ) {
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
               new TextIcon2("" + CFG.getPrecision2(tEco.get(toAddID), 1), Game_Calendar.IMG_ECONOMY, buttonX, buttonY, statsRightW, statsRightH) {
                  @Override
                  public void buildElementHover() {
                     List<MenuElement_HoverElement> nElements = new ArrayList<>();
                     List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                     nData.add(
                        new MenuElement_HoverElement_Type_Button_TextBonus(
                           Game.lang.get("Economy") + ": ",
                           this.getText(),
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
            menuElements.add(
               new TextIcon2("" + tTech.get(toAddID), Game_Calendar.IMG_TECHNOLOGY, buttonX, buttonY, statsRightW, statsRightH) {
                  @Override
                  public void buildElementHover() {
                     List<MenuElement_HoverElement> nElements = new ArrayList<>();
                     List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                     nData.add(
                        new MenuElement_HoverElement_Type_Button_TextBonus(
                           Game.lang.get("UnlockedTechnologies") + ": ",
                           this.getText(),
                           Game_Calendar.IMG_TECHNOLOGY,
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
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
            menuElements.add(new Text_StaticBG_ID_FlagCiv_SpecialEmpty(paddingLeft2, buttonY - emptyBGH, menuWidth - paddingLeft2 * 2, emptyBGH));
            buttonY += CFG.PADDING;
            tCivs.remove(toAddID);
            tPop.remove(toAddID);
            tEco.remove(toAddID);
            tTech.remove(toAddID);
         }
      } else {
         menuElements.add(new Text_StaticBG(Game.lang.get("None"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT2));
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      }

      menuY += InGame_CourtOptions.menuH;
      int menuHeight = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - CFG.PADDING * 3);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, menuHeight)));
      this.initMenu(null, menuX, menuY, menuWidth, menuHeight, menuElements, false, false);
      this.drawScrollPositionAlways = false;
      Game.menuManager.setInGame_CivOptions_Title(Game.lang.get("Civilizations"));
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      if (InGame_Court.lTime + 60L >= CFG.currentTimeMillis) {
         iTranslateX = iTranslateX - CFG.BUTTON_WIDTH + (int)(CFG.BUTTON_WIDTH * ((float)(CFG.currentTimeMillis - InGame_Court.lTime) / 60.0F));
      }

      Renderer.drawBoxCorner(
         oSB,
         this.getPosX() + iTranslateX,
         this.getPosY() - InGame_CourtOptions.menuH + iTranslateY,
         this.getWidth(),
         this.getHeight() + InGame_CourtOptions.menuH + CFG.PADDING
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
      ImageManager.getImage(Images.rulerOver)
         .draw2(
            oSB,
            this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.rulerOver).getWidth() / 2 + iTranslateX,
            this.getPosY() + iTranslateY,
            this.getWidth(),
            Math.min(this.getHeight(), ImageManager.getImage(Images.rulerOver).getHeight())
         );
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public void setVisible(boolean visible) {
      super.setVisible(visible);
      InGame_Court.lTime = CFG.currentTimeMillis;
      InGame_Court.lTime2 = CFG.currentTimeMillis;
      if (!visible) {
         Game.keyboard.hideKeyboard();
      }
   }

   @Override
   public void onHovered() {
      super.onHovered();
      Game.menuManager.setOrderOfMenu_InGameCourt();
   }

   @Override
   public void actionCloseMenu() {
      super.actionCloseMenu();
      Game.keyboard.hideKeyboard();
   }
}
