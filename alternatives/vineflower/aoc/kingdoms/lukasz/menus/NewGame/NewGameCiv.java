package aoc.kingdoms.lukasz.menus.NewGame;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.FileManager;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Ages;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.RulersManager;
import aoc.kingdoms.lukasz.map.civilization.CivilizationRanking;
import aoc.kingdoms.lukasz.map.diplomacy.Diplomacy;
import aoc.kingdoms.lukasz.map.province.ProvinceBorderManager;
import aoc.kingdoms.lukasz.menu.ColorPicker;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonFlag;
import aoc.kingdoms.lukasz.menu_element.button.ButtonFlag_Animation;
import aoc.kingdoms.lukasz.menu_element.button.ButtonFlag_Diplomacy;
import aoc.kingdoms.lukasz.menu_element.button.ButtonIdeology;
import aoc.kingdoms.lukasz.menu_element.button.ButtonIdeology2;
import aoc.kingdoms.lukasz.menu_element.button.ButtonReligion2;
import aoc.kingdoms.lukasz.menu_element.button.ButtonRuler;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG;
import aoc.kingdoms.lukasz.menu_element.button.Button_Advantage3;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_FlagTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextIcon2;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextIcon2_HorizontalSplit;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Desc_SimpleNewGame;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Title_v2Center;
import aoc.kingdoms.lukasz.menusInGame.InGame_CivilizationAdvantages;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class NewGameCiv extends Menu {
   public static int iActiveCivID = 0;
   public int flagX;
   public int flagY;
   public int flagW;
   public int flagH;
   public static boolean expandCivDesc = false;

   public NewGameCiv() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = Images.boxTitleBORDERWIDTH + CFG.PADDING * 2;
      int titleHeight = ImageManager.getImage(Images.title1Red).getHeight();
      int menuWidth = ImageManager.getImage(Images.insideTop).getWidth();
      int menuX = CFG.GAME_WIDTH - menuWidth - CFG.PADDING * 2;
      int menuY = CFG.GAME_HEIGHT / 10;
      int buttonYPadding = CFG.PADDING * 2;
      iActiveCivID = Game.player.iCivID;
      RulersManager.loadRulerIMG(iActiveCivID);
      menuElements.add(new ButtonRuler(iActiveCivID, menuWidth - ButtonRuler.getButtonWidth() - paddingLeft, buttonYPadding));
      this.flagX = paddingLeft;
      this.flagY = buttonYPadding;
      this.flagW = menuWidth - paddingLeft * 2 - CFG.PADDING - ButtonRuler.getButtonWidth();
      int buttonY = buttonYPadding + CFG.PADDING * 3;
      menuElements.add(
         new ButtonFlag_Animation((menuWidth - ButtonRuler.getButtonWidth() - Images.boxTitleBORDERWIDTH) / 2 - ButtonFlag.getButtonWidth() / 2, buttonY, true) {
            @Override
            public int getFlagCivID() {
               return NewGameCiv.iActiveCivID;
            }

            @Override
            public void actionElement() {
               if (Game.menuManager.getColorPicker().getVisible()) {
                  Game.menuManager.getColorPicker().hideColorPicker();
               } else {
                  ColorPicker.ACTIVE_CIV_ID = NewGameCiv.iActiveCivID;
                  Game.menuManager
                     .getColorPicker()
                     .setActiveRGBColor(
                        Game.getCiv(NewGameCiv.iActiveCivID).getR(), Game.getCiv(NewGameCiv.iActiveCivID).getG(), Game.getCiv(NewGameCiv.iActiveCivID).getB()
                     );
                  Game.menuManager.getColorPicker().setVisible(true, ColorPicker.PickerAction.CIV_COLOR_NEWGAME);
                  Game.menuManager.getColorPicker().setPosX(CFG.BUTTON_WIDTH);
                  Game.menuManager.getColorPicker().setPosY(CFG.GAME_HEIGHT / 10);
               }
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("ShowHideColorPicker"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_FlagTitle(Game.player.iCivID, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }

            @Override
            protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
               oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.5F));
               Renderer.drawBox(
                  oSB,
                  Images.statsRectBG,
                  NewGameCiv.this.flagX + iTranslateX,
                  NewGameCiv.this.flagY + iTranslateY,
                  NewGameCiv.this.flagW,
                  NewGameCiv.this.flagH,
                  1.0F
               );
               oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.5F));
               Images.gradientXY
                  .draw(oSB, NewGameCiv.this.flagX + iTranslateX, NewGameCiv.this.flagY + iTranslateY, NewGameCiv.this.flagW, NewGameCiv.this.flagH);
               oSB.setColor(Color.WHITE);
               oSB.setColor(new Color(Colors.COLOR_GRADIENT.r, Colors.COLOR_GRADIENT.g, Colors.COLOR_GRADIENT.b, 0.55F));
               Images.gradientXY
                  .draw(
                     oSB,
                     NewGameCiv.this.flagX + iTranslateX,
                     NewGameCiv.this.flagY + iTranslateY,
                     NewGameCiv.this.flagW,
                     NewGameCiv.this.flagH / 2,
                     false,
                     true
                  );
               Images.gradientXY
                  .draw(
                     oSB,
                     NewGameCiv.this.flagX + iTranslateX,
                     NewGameCiv.this.flagY + NewGameCiv.this.flagH / 2 + iTranslateY,
                     NewGameCiv.this.flagW,
                     NewGameCiv.this.flagH / 2,
                     false,
                     false
                  );
               Images.gradientFull
                  .draw(oSB, NewGameCiv.this.flagX + iTranslateX, NewGameCiv.this.flagY + NewGameCiv.this.flagH - 2 + iTranslateY, NewGameCiv.this.flagW, 1);
               Images.gradientFull.draw(oSB, NewGameCiv.this.flagX + iTranslateX, NewGameCiv.this.flagY + 1 + iTranslateY, NewGameCiv.this.flagW, 1);
               oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.2F));
               Images.gradientFull.draw(oSB, NewGameCiv.this.flagX + iTranslateX, NewGameCiv.this.flagY + iTranslateY, NewGameCiv.this.flagW, 1);
               Images.gradientFull
                  .draw(oSB, NewGameCiv.this.flagX + iTranslateX, NewGameCiv.this.flagY + NewGameCiv.this.flagH - 1 + iTranslateY, NewGameCiv.this.flagW, 1);
               oSB.setColor(Color.WHITE);
               if (this.getIsHovered()) {
                  oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 1.0F));
                  Renderer.drawBox(
                     oSB,
                     Images.statsRectBGBorder,
                     NewGameCiv.this.flagX + iTranslateX,
                     NewGameCiv.this.flagY + iTranslateY,
                     NewGameCiv.this.flagW,
                     NewGameCiv.this.flagH,
                     1.0F
                  );
                  oSB.setColor(Color.WHITE);
               }

               super.drawButtonBG(oSB, iTranslateX, iTranslateY, isActive);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING * 3;
      this.flagH = buttonY - this.flagY - CFG.PADDING;
      ButtonIdeology.maxHeight = Math.max(Game.ideologiesManager.maxHeight, Game.religionManager.maxHeight);
      int tWidth = (menuWidth - ButtonRuler.getButtonWidth() - paddingLeft * 2) / 2 - CFG.PADDING;
      menuElements.add(
         new ButtonIdeology2(Game.getCiv(iActiveCivID).getIdeologyID(), paddingLeft, buttonY, tWidth, ButtonRuler.getButtonHeight() - buttonY + CFG.PADDING * 2)
      );
      menuElements.add(
         new ButtonReligion2(
            Game.getCiv(iActiveCivID).getReligionID(),
            paddingLeft + CFG.PADDING + tWidth,
            buttonY,
            tWidth,
            ButtonRuler.getButtonHeight() - buttonY + CFG.PADDING * 2
         )
      );
      buttonY = menuElements.get(0).getPosY() + menuElements.get(0).getHeight() + CFG.PADDING;
      tWidth = (menuWidth - paddingLeft * 2 - CFG.PADDING) / 2;
      menuElements.add(
         new ButtonStatsRectIMG(
            "" + Game.getCiv(iActiveCivID).iCivRankPosition,
            CivilizationRanking.getCivilizationRanking_IMG_STAR_CIVID(iActiveCivID),
            paddingLeft,
            buttonY,
            tWidth,
            CFG.TEXT_HEIGHT + CFG.PADDING * 5,
            ImageManager.getImage(Images.vassal).getWidth() + CFG.PADDING * 2
         ) {
            @Override
            public void buildElementHover() {
               this.menuElementHover = CivilizationRanking.getHover_CivilizationRanking(NewGameCiv.iActiveCivID, false, false);
            }
         }
      );
      int buttonX = paddingLeft + menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
      menuElements.add(
         new ButtonStatsRectIMG(
            Game.getCiv(iActiveCivID).getCapitalProvinceID() >= 0 ? Game.getProvince(Game.getCiv(iActiveCivID).getCapitalProvinceID()).getProvinceName() : "",
            Images.capital,
            buttonX,
            buttonY,
            tWidth,
            CFG.TEXT_HEIGHT + CFG.PADDING * 5,
            ImageManager.getImage(Images.vassal).getWidth() + CFG.PADDING * 2
         ) {
            @Override
            public void buildElementHover() {
               if (Game.getCiv(NewGameCiv.iActiveCivID).getCapitalProvinceID() >= 0) {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Capital") + ": ", CFG.FONT_BOLD));
                  nData.add(
                     new MenuElement_HoverElement_Type_TextTitle(
                        Game.getProvince(Game.getCiv(NewGameCiv.iActiveCivID).getCapitalProvinceID()).getProvinceName(), CFG.FONT_BOLD, Colors.HOVER_GOLD
                     )
                  );
                  nData.add(new MenuElement_HoverElement_Type_FlagTitle(NewGameCiv.iActiveCivID, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(new MenuElement_HoverElement_Type_Line());
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        Game.lang.get("Population") + ": ",
                        "" + CFG.getNumberWithSpaces("" + Game.getProvince(Game.getCiv(NewGameCiv.iActiveCivID).getCapitalProvinceID()).getPopulationTotal()),
                        Images.population,
                        CFG.FONT_REGULAR,
                        CFG.FONT_BOLD,
                        Colors.HOVER_LEFT,
                        Colors.COLOR_POPULATION
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements);
               } else {
                  this.menuElementHover = null;
               }
            }

            @Override
            public void actionElement() {
               if (Game.getCiv(NewGameCiv.iActiveCivID).getCapitalProvinceID() >= 0) {
                  Game.mapCoords.centerToProvinceID(Game.getCiv(NewGameCiv.iActiveCivID).getCapitalProvinceID());
                  Game.setActiveProvinceID(Game.getCiv(NewGameCiv.iActiveCivID).getCapitalProvinceID());
                  ProvinceBorderManager.action.setProvinceID(Game.iActiveProvince);
               }
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      tWidth = (menuWidth - paddingLeft * 2 - CFG.PADDING * 3) / 4;
      int statsH = ImageManager.getImage(Images.provinces).getHeight() + CFG.TEXT_HEIGHT + CFG.PADDING * 6;
      menuElements.add(
         new TextIcon2("" + Game.getCiv(iActiveCivID).getNumOfProvinces(), Images.provinces, paddingLeft, buttonY, tWidth, statsH) {
            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     Game.lang.get("Provinces") + ": ",
                     "" + CFG.getNumberWithSpaces("" + Game.getCiv(NewGameCiv.iActiveCivID).getNumOfProvinces()),
                     Images.provinces,
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
      buttonX = paddingLeft + menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
      menuElements.add(
         new TextIcon2(CFG.getShortNumber(Game.getCiv(iActiveCivID).getPopulationTotal()), Images.population, buttonX, buttonY, tWidth, statsH) {
            @Override
            protected Color getColor(boolean isActive) {
               if (isActive) {
                  return Colors.COLOR_POPULATION_ACTIVE;
               } else if (this.getIsHovered()) {
                  return Colors.COLOR_POPULATION_HOVER;
               } else {
                  return this.getClickable() ? Colors.COLOR_POPULATION : Colors.BUTTON_TEXT_DISABLED;
               }
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     Game.lang.get("Population") + ": ",
                     "" + CFG.getNumberWithSpaces("" + Game.getCiv(NewGameCiv.iActiveCivID).getPopulationTotal()),
                     Images.population,
                     CFG.FONT_REGULAR,
                     CFG.FONT_BOLD,
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
         new TextIcon2(CFG.getShortNumber((int)Game.getCiv(iActiveCivID).getEconomyTotal()), Game_Calendar.IMG_ECONOMY, buttonX, buttonY, tWidth, statsH) {
            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     Game.lang.get("Economy") + ": ",
                     "" + CFG.getNumberWithSpaces("" + CFG.getPrecision2(Game.getCiv(NewGameCiv.iActiveCivID).getEconomyTotal(), 1)),
                     Game_Calendar.IMG_ECONOMY,
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
      buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
      menuElements.add(
         new TextIcon2("" + Game.getCiv(iActiveCivID).getResearchedTechnologies(), Game_Calendar.IMG_TECHNOLOGY, buttonX, buttonY, tWidth, statsH) {
            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     Game.lang.get("UnlockedTechnologies") + ": ",
                     "" + CFG.getNumberWithSpaces("" + Game.getCiv(NewGameCiv.iActiveCivID).getResearchedTechnologies()),
                     Game_Calendar.IMG_TECHNOLOGY,
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
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      String description = "";
      if (FileManager.loadFile(
            "map/"
               + Game.map.getFile_ActiveMap_Path()
               + "scenarios/"
               + Game.mapScenarios.lScenarios_TagsList.get(Game.scenarioID)
               + "/"
               + "descriptions/"
               + Game.getCiv(iActiveCivID).getCivTag()
               + ".txt"
         )
         .exists()) {
         FileHandle tempFileT = FileManager.loadFile(
            "map/"
               + Game.map.getFile_ActiveMap_Path()
               + "scenarios/"
               + Game.mapScenarios.lScenarios_TagsList.get(Game.scenarioID)
               + "/"
               + "descriptions/"
               + Game.getCiv(iActiveCivID).getCivTag()
               + ".txt"
         );
         description = tempFileT.readString();
      } else if (FileManager.loadFile(
            "map/"
               + Game.map.getFile_ActiveMap_Path()
               + "scenarios/"
               + Game.mapScenarios.lScenarios_TagsList.get(Game.scenarioID)
               + "/"
               + "descriptions/"
               + Game.getCiv(iActiveCivID).realTag
               + ".txt"
         )
         .exists()) {
         FileHandle tempFileT = FileManager.loadFile(
            "map/"
               + Game.map.getFile_ActiveMap_Path()
               + "scenarios/"
               + Game.mapScenarios.lScenarios_TagsList.get(Game.scenarioID)
               + "/"
               + "descriptions/"
               + Game.getCiv(iActiveCivID).realTag
               + ".txt"
         );
         description = tempFileT.readString();
      }

      if (description.length() > 0) {
         menuElements.add(new Text_Desc_SimpleNewGame(description, paddingLeft, buttonY, menuWidth - paddingLeft * 2) {
            @Override
            public void actionElement() {
               NewGameCiv.expandCivDesc = !NewGameCiv.expandCivDesc;
               Game.menuManager.rebuildNewGameCiv();
            }
         });
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      }

      if (Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).NEW_GAME_ADVANTAGES) {
         List<MenuElement> toSort = InGame_CivilizationAdvantages.getAdvantagesSmall(iActiveCivID);
         if (toSort.size() > 0) {
            int toSortX = menuWidth / 2 - (Button_Advantage3.getButtonWidth() * toSort.size() + CFG.PADDING * (toSort.size() - 1)) / 2;

            while (toSort.size() > 0) {
               int bestID = 0;
               int i = 1;

               for (int iSize = toSort.size(); i < iSize; i++) {
                  if (CFG.compareAlphabetic_TwoString(toSort.get(bestID).getTextToDraw(), toSort.get(i).getTextToDraw())) {
                     bestID = i;
                  }
               }

               toSort.get(bestID).setPosX(toSortX);
               toSort.get(bestID).setPosY(buttonY);
               menuElements.add(toSort.get(bestID));
               toSortX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
               toSort.remove(bestID);
            }

            buttonY += Button_Advantage3.getButtonHeight() + CFG.PADDING;
         }
      }

      menuElements.add(
         new Text_Title_v2Center(
            Game.lang.get("Diplomacy"), -1, Images.boxTitleBORDERWIDTH, buttonY, menuWidth - Images.boxTitleBORDERWIDTH * 2, CFG.TEXT_HEIGHT + CFG.PADDING * 6
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight();
      int tempElementsBefore = menuElements.size();
      int leftW = menuWidth - paddingLeft * 2 - ButtonFlag_Diplomacy.getButtonWidth() * 3 - CFG.PADDING * 2;
      int lineH = ImageManager.getImage(Images.flagDiplomacyOver).getHeight() + CFG.PADDING * 2;
      int maxIconW = ImageManager.getImage(Images.relations).getWidth() + CFG.PADDING * 2;
      int linesAdded = 0;
      paddingLeft = Images.boxTitleBORDERWIDTH;
      if (Game.getCiv(iActiveCivID).getPuppetOfCivID() != Game.getCiv(iActiveCivID).getCivID()) {
         buttonX = paddingLeft + leftW + CFG.PADDING;
         menuElements.add(
            new ButtonFlag_Diplomacy(Game.getCiv(iActiveCivID).getPuppetOfCivID(), paddingLeft + leftW + CFG.PADDING, buttonY + CFG.PADDING, true, true, false)
         );
         menuElements.add(
            new TextIcon2_HorizontalSplit(
               Game.lang.get(Game_Ages.getLord()), Images.vassal, paddingLeft, buttonY, leftW, lineH, maxIconW, menuWidth - paddingLeft * 2
            )
         );
         buttonY += lineH;
      }

      if (Game.getCiv(iActiveCivID).diplomacy.iVassalsSize > 0) {
         buttonX = paddingLeft + leftW + CFG.PADDING;

         for (int i = 0; i < Game.getCiv(iActiveCivID).diplomacy.iVassalsSize; i++) {
            if (buttonX + ButtonFlag_Diplomacy.getButtonWidth() > menuWidth) {
               buttonX = paddingLeft + leftW + CFG.PADDING;
               buttonY += lineH;
               linesAdded++;
            }

            int var10006 = buttonY + CFG.PADDING;
            menuElements.add(new ButtonFlag_Diplomacy(Game.getCiv(iActiveCivID).diplomacy.lVassals.get(i).c, buttonX, var10006, true, true, true) {
               @Override
               public void actionElement() {
               }
            });
            buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         }

         menuElements.add(
            new TextIcon2_HorizontalSplit(
               Game.lang.get(Game_Ages.getVassals()),
               Images.vassal,
               paddingLeft,
               buttonY - lineH * linesAdded,
               leftW,
               lineH * (linesAdded + 1),
               maxIconW,
               menuWidth - paddingLeft * 2
            )
         );
         linesAdded = 0;
         buttonY += lineH;
      }

      try {
         if (Game.getCiv(iActiveCivID).diplomacy.alliance.size() > 0) {
            buttonX = paddingLeft + leftW + CFG.PADDING;

            for (Diplomacy.DiplomacyData nData : Game.getCiv(iActiveCivID).diplomacy.alliance.values()) {
               if (buttonX + ButtonFlag_Diplomacy.getButtonWidth() > menuWidth) {
                  buttonX = paddingLeft + leftW + CFG.PADDING;
                  buttonY += lineH;
                  linesAdded++;
               }

               menuElements.add(new ButtonFlag_Diplomacy(nData.iCivID, buttonX, buttonY + CFG.PADDING, true) {
                  @Override
                  public void actionElement() {
                  }
               });
               buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
            }

            menuElements.add(
               new TextIcon2_HorizontalSplit(
                  Game.lang.get("Alliance"),
                  Images.alliance,
                  paddingLeft,
                  buttonY - lineH * linesAdded,
                  leftW,
                  lineH * (linesAdded + 1),
                  maxIconW,
                  menuWidth - paddingLeft * 2
               )
            );
            linesAdded = 0;
            buttonY += Math.max(lineH, menuElements.get(menuElements.size() - 1).getHeight());
         }
      } catch (Exception var30) {
         CFG.exceptionStack(var30);
      }

      try {
         if (Game.getCiv(iActiveCivID).diplomacy.truce.size() > 0) {
            buttonX = paddingLeft + leftW + CFG.PADDING;

            for (Diplomacy.DiplomacyData nData : Game.getCiv(iActiveCivID).diplomacy.truce.values()) {
               if (buttonX + ButtonFlag_Diplomacy.getButtonWidth() > menuWidth) {
                  buttonX = paddingLeft + leftW + CFG.PADDING;
                  buttonY += lineH;
                  linesAdded++;
               }

               menuElements.add(new ButtonFlag_Diplomacy(nData.iCivID, buttonX, buttonY + CFG.PADDING, true) {
                  @Override
                  public void actionElement() {
                  }
               });
               buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
            }

            menuElements.add(
               new TextIcon2_HorizontalSplit(
                  Game.lang.get("Truce"),
                  Images.truce,
                  paddingLeft,
                  buttonY - lineH * linesAdded,
                  leftW,
                  lineH * (linesAdded + 1),
                  maxIconW,
                  menuWidth - paddingLeft * 2
               )
            );
            linesAdded = 0;
            buttonY += Math.max(lineH, menuElements.get(menuElements.size() - 1).getHeight());
         }
      } catch (Exception var29) {
         CFG.exceptionStack(var29);
      }

      try {
         if (Game.getCiv(iActiveCivID).diplomacy.defensivePact.size() > 0) {
            buttonX = paddingLeft + leftW + CFG.PADDING;

            for (Diplomacy.DiplomacyData nData : Game.getCiv(iActiveCivID).diplomacy.defensivePact.values()) {
               if (buttonX + ButtonFlag_Diplomacy.getButtonWidth() > menuWidth) {
                  buttonX = paddingLeft + leftW + CFG.PADDING;
                  buttonY += lineH;
                  linesAdded++;
               }

               menuElements.add(new ButtonFlag_Diplomacy(nData.iCivID, buttonX, buttonY + CFG.PADDING, true) {
                  @Override
                  public void actionElement() {
                  }
               });
               buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
            }

            menuElements.add(
               new TextIcon2_HorizontalSplit(
                  Game.lang.get("DefensivePact"),
                  Images.defensivePact,
                  paddingLeft,
                  buttonY - lineH * linesAdded,
                  leftW,
                  lineH * (linesAdded + 1),
                  maxIconW,
                  menuWidth - paddingLeft * 2
               )
            );
            linesAdded = 0;
            buttonY += Math.max(lineH, menuElements.get(menuElements.size() - 1).getHeight());
         }
      } catch (Exception var28) {
         CFG.exceptionStack(var28);
      }

      try {
         if (Game.getCiv(iActiveCivID).diplomacy.guarantee.size() > 0) {
            buttonX = paddingLeft + leftW + CFG.PADDING;

            for (Diplomacy.DiplomacyData nData : Game.getCiv(iActiveCivID).diplomacy.guarantee.values()) {
               if (buttonX + ButtonFlag_Diplomacy.getButtonWidth() > menuWidth) {
                  buttonX = paddingLeft + leftW + CFG.PADDING;
                  buttonY += lineH;
                  linesAdded++;
               }

               menuElements.add(new ButtonFlag_Diplomacy(nData.iCivID, buttonX, buttonY + CFG.PADDING, true) {
                  @Override
                  public void actionElement() {
                  }
               });
               buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
            }

            menuElements.add(
               new TextIcon2_HorizontalSplit(
                  Game.lang.get("GuaranteeIndependence"),
                  Images.guaranteeIndependence,
                  paddingLeft,
                  buttonY - lineH * linesAdded,
                  leftW,
                  lineH * (linesAdded + 1),
                  maxIconW,
                  menuWidth - paddingLeft * 2
               )
            );
            linesAdded = 0;
            buttonY += Math.max(lineH, menuElements.get(menuElements.size() - 1).getHeight());
         }
      } catch (Exception var27) {
         CFG.exceptionStack(var27);
      }

      try {
         if (Game.getCiv(iActiveCivID).diplomacy.guaranteeByCivID.size() > 0) {
            buttonX = paddingLeft + leftW + CFG.PADDING;

            for (Diplomacy.DiplomacyData nData : Game.getCiv(iActiveCivID).diplomacy.guaranteeByCivID.values()) {
               if (buttonX + ButtonFlag_Diplomacy.getButtonWidth() > menuWidth) {
                  buttonX = paddingLeft + leftW + CFG.PADDING;
                  buttonY += lineH;
                  linesAdded++;
               }

               menuElements.add(new ButtonFlag_Diplomacy(nData.iCivID, buttonX, buttonY + CFG.PADDING, true) {
                  @Override
                  public void actionElement() {
                  }
               });
               buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
            }

            menuElements.add(
               new TextIcon2_HorizontalSplit(
                  Game.lang.get("GuaranteeTheirIndependence"),
                  Images.guaranteeIndependence,
                  paddingLeft,
                  buttonY - lineH * linesAdded,
                  leftW,
                  lineH * (linesAdded + 1),
                  maxIconW,
                  menuWidth - paddingLeft * 2
               )
            );
            linesAdded = 0;
            buttonY += Math.max(lineH, menuElements.get(menuElements.size() - 1).getHeight());
         }
      } catch (Exception var26) {
         CFG.exceptionStack(var26);
      }

      try {
         if (Game.getCiv(iActiveCivID).diplomacy.nonAggressionPact.size() > 0) {
            buttonX = paddingLeft + leftW + CFG.PADDING;

            for (Diplomacy.DiplomacyData nData : Game.getCiv(iActiveCivID).diplomacy.nonAggressionPact.values()) {
               if (buttonX + ButtonFlag_Diplomacy.getButtonWidth() > menuWidth) {
                  buttonX = paddingLeft + leftW + CFG.PADDING;
                  buttonY += lineH;
                  linesAdded++;
               }

               menuElements.add(new ButtonFlag_Diplomacy(nData.iCivID, buttonX, buttonY + CFG.PADDING, true) {
                  @Override
                  public void actionElement() {
                  }
               });
               buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
            }

            menuElements.add(
               new TextIcon2_HorizontalSplit(
                  Game.lang.get("NonAggressionPact").replace("-", " "),
                  Images.nonAggression,
                  paddingLeft,
                  buttonY - lineH * linesAdded,
                  leftW,
                  lineH * (linesAdded + 1),
                  maxIconW,
                  menuWidth - paddingLeft * 2
               )
            );
            linesAdded = 0;
            buttonY += Math.max(lineH, menuElements.get(menuElements.size() - 1).getHeight());
         }
      } catch (Exception var25) {
         CFG.exceptionStack(var25);
      }

      try {
         List<Integer> friendly = new ArrayList<>();

         for (int i = 1; i < Game.getCivsSize(); i++) {
            if (Game.getCiv(i).diplomacy.getRelation(iActiveCivID) >= GameValues.diplomacy.DIPLOMACY_RELATIONS_FRIENDLY) {
               friendly.add(i);
            }
         }

         if (friendly.size() > 0) {
            buttonX = paddingLeft + leftW + CFG.PADDING;

            for (int ix = 0; ix < GameValues.diplomacy.DIPLOMACY_FRIENDLY_LIMIT_IN_DIPLOMACY_VIEW && ix < friendly.size(); ix++) {
               int tBestID = 0;

               for (int j = friendly.size() - 1; j > 0; j--) {
                  if (Game.getCiv(friendly.get(tBestID)).diplomacy.getRelation(iActiveCivID) < Game.getCiv(friendly.get(j)).diplomacy.getRelation(iActiveCivID)
                     )
                   {
                     tBestID = j;
                  }
               }

               if (buttonX + ButtonFlag_Diplomacy.getButtonWidth() > menuWidth) {
                  buttonX = paddingLeft + leftW + CFG.PADDING;
                  buttonY += lineH;
                  linesAdded++;
               }

               menuElements.add(new ButtonFlag_Diplomacy(friendly.get(tBestID), buttonX, buttonY + CFG.PADDING, true) {
                  @Override
                  public void actionElement() {
                  }
               });
               buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
               friendly.remove(tBestID);
            }

            menuElements.add(
               new TextIcon2_HorizontalSplit(
                  Game.lang.get("FriendlyCivilizations"),
                  Images.heart,
                  paddingLeft,
                  buttonY - lineH * linesAdded,
                  leftW,
                  lineH * (linesAdded + 1),
                  maxIconW,
                  menuWidth - paddingLeft * 2
               )
            );
            linesAdded = 0;
            buttonY += Math.max(lineH, menuElements.get(menuElements.size() - 1).getHeight());
         }
      } catch (Exception var24) {
         CFG.exceptionStack(var24);
      }

      try {
         if (Game.getCiv(iActiveCivID).diplomacy.militaryAccess.size() > 0) {
            buttonX = paddingLeft + leftW + CFG.PADDING;

            for (Diplomacy.DiplomacyData nData : Game.getCiv(iActiveCivID).diplomacy.militaryAccess.values()) {
               if (buttonX + ButtonFlag_Diplomacy.getButtonWidth() > menuWidth) {
                  buttonX = paddingLeft + leftW + CFG.PADDING;
                  buttonY += lineH;
                  linesAdded++;
               }

               menuElements.add(new ButtonFlag_Diplomacy(nData.iCivID, buttonX, buttonY + CFG.PADDING, true) {
                  @Override
                  public void actionElement() {
                  }
               });
               buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
            }

            menuElements.add(
               new TextIcon2_HorizontalSplit(
                  Game.lang.get("HaveMilitaryAccess"),
                  Images.militaryAccess,
                  paddingLeft,
                  buttonY - lineH * linesAdded,
                  leftW,
                  lineH * (linesAdded + 1),
                  maxIconW,
                  menuWidth - paddingLeft * 2
               )
            );
            linesAdded = 0;
            buttonY += Math.max(lineH, menuElements.get(menuElements.size() - 1).getHeight());
         }
      } catch (Exception var23) {
         CFG.exceptionStack(var23);
      }

      try {
         List<Integer> tMilitaryAccessGives = new ArrayList<>();

         for (int ix = 1; ix < Game.getCivsSize(); ix++) {
            if (Game.getCiv(ix).getNumOfProvinces() > 0 && Game.getCiv(ix).diplomacy.militaryAccess.containsKey(iActiveCivID)) {
               tMilitaryAccessGives.add(ix);
            }
         }

         if (tMilitaryAccessGives.size() > 0) {
            buttonX = paddingLeft + leftW + CFG.PADDING;

            for (int ixx = 0; ixx < tMilitaryAccessGives.size(); ixx++) {
               if (buttonX + ButtonFlag_Diplomacy.getButtonWidth() > menuWidth) {
                  buttonX = paddingLeft + leftW + CFG.PADDING;
                  buttonY += lineH;
                  linesAdded++;
               }

               menuElements.add(new ButtonFlag_Diplomacy(tMilitaryAccessGives.get(ixx), buttonX, buttonY + CFG.PADDING, true) {
                  @Override
                  public void actionElement() {
                  }
               });
               buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
            }

            menuElements.add(
               new TextIcon2_HorizontalSplit(
                  Game.lang.get("GivesMilitaryAccess"),
                  Images.militaryAccess2,
                  paddingLeft,
                  buttonY - lineH * linesAdded,
                  leftW,
                  lineH * (linesAdded + 1),
                  maxIconW,
                  menuWidth - paddingLeft * 2
               )
            );
            int var61 = false;
            buttonY += Math.max(lineH, menuElements.get(menuElements.size() - 1).getHeight());
         }
      } catch (Exception var22) {
         CFG.exceptionStack(var22);
      }

      if (tempElementsBefore == menuElements.size()) {
         buttonY += CFG.PADDING;
         menuElements.add(new Text_StaticBG("---", CFG.FONT_REGULAR, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT2));
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      }

      int menuHeight = Math.min(
         CFG.GAME_HEIGHT - ImageManager.getImage(Images.buttonPlay).getHeight() - CFG.PADDING * 3 - menuY - titleHeight,
         Math.min(buttonY, CFG.GAME_HEIGHT - menuY - CFG.PADDING * 3)
      );
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, menuHeight)));
      this.initMenu(new MenuTitleIMG("", true, false, Images.title1Red), menuX, titleHeight + menuY, menuWidth, menuHeight, menuElements, true, false);
      if (Game.menuManager.getColorPicker().getVisible()) {
         ColorPicker.ACTIVE_CIV_ID = iActiveCivID;
         Game.menuManager
            .getColorPicker()
            .setActiveRGBColor(Game.getCiv(iActiveCivID).getR(), Game.getCiv(iActiveCivID).getG(), Game.getCiv(iActiveCivID).getB());
      }
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      if (NewGame.lTime + 500L >= CFG.currentTimeMillis) {
         iTranslateX = iTranslateX + this.getWidth() - (int)(this.getWidth() * ((float)(CFG.currentTimeMillis - NewGame.lTime) / 500.0F));
      }

      Renderer.drawBoxCorner(
         oSB,
         this.getPosX() + iTranslateX,
         this.getPosY() - this.getTitle().getHeight() + iTranslateY,
         this.getWidth(),
         this.getHeight() + this.getTitle().getHeight() + CFG.PADDING
      );
      Renderer.drawMenusBox(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight() + CFG.PADDING, false);
      ImageManager.getImage(Images.newGameOver)
         .draw2(
            oSB,
            this.getPosX() + iTranslateX,
            this.getPosY() + iTranslateY,
            this.getWidth(),
            Math.min(this.getHeight(), ImageManager.getImage(Images.newGameOver).getHeight())
         );
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public void updateLanguage() {
      super.updateLanguage();
      this.getTitle().setText(Game.getCiv(iActiveCivID).getCivName());
   }
}
