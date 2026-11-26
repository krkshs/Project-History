package aoc.kingdoms.lukasz.menusInGame.Court;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.battles.BattleManager;
import aoc.kingdoms.lukasz.map.civilization.CivilizationRanking;
import aoc.kingdoms.lukasz.map.technology.TechnologyTree;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonArmyBattle;
import aoc.kingdoms.lukasz.menu_element.button.ButtonArmyGeneral2_Armies;
import aoc.kingdoms.lukasz.menu_element.button.ButtonArmyNoGeneral2_Armies;
import aoc.kingdoms.lukasz.menu_element.button.ButtonFlag;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsBudget_Balance_Spy;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsBudget_Right2;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Bonuses;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Bonuses_Style;
import aoc.kingdoms.lukasz.menu_element.button.ButtonTechnology;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_FlagCiv_Title;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_FlagTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Image;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text_Desc;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextArmies;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextIcon2;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_RulerTitle;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Title_v2Center;
import aoc.kingdoms.lukasz.menusInGame.InGame_Armies;
import aoc.kingdoms.lukasz.menusInGame.Civ.InGame_Civ;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_Court_Espionage extends Menu {
   public int iCivID = 1;
   public int iReportEndTurnID;

   public InGame_Court_Espionage(final int iCivID, int iReportEndTurnID) {
      List<MenuElement> menuElements = new ArrayList<>();
      this.iCivID = iCivID;
      this.iReportEndTurnID = iReportEndTurnID;
      int paddingLeft = Images.boxTitleBORDERWIDTH + CFG.PADDING * 2;
      int menuWidth = ImageManager.getImage(Images.insideTop500).getWidth();
      int menuX = InGame_CourtOptions2.getOtherMenuPosX();
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING;
      int buttonYPadding = CFG.PADDING * 2;
      int buttonY = CFG.PADDING * 2;
      int rightW = menuWidth - paddingLeft * 2 - ButtonFlag.getButtonWidth() - CFG.PADDING * 2;
      int rightH = (ButtonFlag.getButtonHeight() - CFG.PADDING) / 2;
      menuElements.add(
         new ButtonFlag(iCivID, paddingLeft, buttonY, true) {
            @Override
            public int getFlagCivID() {
               return this.iCivID;
            }

            @Override
            public void actionElement() {
               if (Game.menuManager.getVisibleInGame_Civ() && InGame_Civ.iActiveCivID == this.getCurrent()) {
                  Game.menuManager.setVisibleInGame_Civ(false);
               } else {
                  boolean resetTime = Game.menuManager.getVisibleInGame_Civ();
                  InGame_Civ.iRebuildToCivID = this.iCivID;
                  Game.menuManager.rebuildInGame_Civ();
                  if (resetTime) {
                     InGame_Civ.lTime = 0L;
                  }
               }
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(
                  new MenuElement_HoverElement_Type_FlagCiv_Title(
                     this.iCivID, CivilizationRanking.getCivilizationRank_Name(Game.getCiv(this.iCivID).iCivRankID)
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("OpenTheDiplomacyView"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.diplomacy, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements, true);
            }
         }
      );
      menuElements.add(
         new Text_StaticBG_RulerTitle(Game.getCiv(iCivID).getCivName(), paddingLeft + ButtonFlag.getButtonWidth() + CFG.PADDING * 2, buttonY, rightW, rightH) {
            @Override
            public void actionElement() {
               Game.mapCoords.centerToProvinceID(Game.getCiv(iCivID).getCapitalProvinceID());
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("CenterToCivilization"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.capital, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               if (Game.getCiv(iCivID).getCapitalProvinceID() >= 0) {
                  nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Capital") + ": ", CFG.FONT_BOLD, Colors.HOVER_LEFT));
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        Game.getProvince(Game.getCiv(iCivID).getCapitalProvinceID()).getProvinceName(), CFG.FONT_BOLD, Colors.HOVER_GOLD
                     )
                  );
                  nData.add(new MenuElement_HoverElement_Type_Image(Images.capital, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
               }

               this.menuElementHover = new MenuElement_Hover(nElements, Game.getCiv(iCivID).getCapitalProvinceID() < 0);
            }
         }
      );
      menuElements.add(
         new ButtonStatsRectIMG_Bonuses(
            Game.lang.get("Expires") + ": ",
            "" + Game_Calendar.getDate_ByTurnID(iReportEndTurnID),
            Images.time,
            paddingLeft + ButtonFlag.getButtonWidth() + CFG.PADDING * 2,
            buttonY + rightH + CFG.PADDING,
            rightW,
            rightH,
            ImageManager.getImage(Images.gold).getWidth()
         )
      );
      buttonY += ButtonFlag.getButtonHeight() + CFG.PADDING;
      paddingLeft = Images.boxTitleBORDERWIDTH + CFG.PADDING;
      int tIconMaxW = ImageManager.getImage(Images.gold).getWidth() + CFG.PADDING * 4 + CFG.PADDING / 2;
      int tButtonH = CFG.TEXT_HEIGHT + CFG.PADDING * 4;
      Renderer.glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_BOLD), "+999 999");
      int tButtonRightW = (int)Renderer.glyphLayout.width + CFG.PADDING * 4 + CFG.PADDING / 2;
      int tButtonW = menuWidth - paddingLeft * 2 - CFG.PADDING - tButtonRightW;
      int iRow = 0;
      menuElements.add(
         new Text_Title_v2Center(
            Game.lang.get("Budget"), -1, Images.boxTitleBORDERWIDTH, buttonY, menuWidth - Images.boxTitleBORDERWIDTH * 2, CFG.TEXT_HEIGHT + CFG.PADDING * 6
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new ButtonStatsRectIMG_Bonuses_Style(Game.lang.get("TotalIncome"), "", Images.goldPositive, paddingLeft, buttonY, tButtonW, tButtonH, tIconMaxW) {
            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               float fVal = Game.getCiv(iCivID).fTotalIncomePerMonth + Game.getCiv(iCivID).civBonuses.MonthlyIncome;
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     Game.lang.get("TotalIncome") + ": ",
                     "" + (fVal > 0.0F ? "+" : "") + CFG.getPrecision2(fVal, 100),
                     Images.gold,
                     CFG.FONT_REGULAR,
                     CFG.FONT_BOLD,
                     Colors.HOVER_LEFT,
                     fVal == 0.0F
                        ? Colors.COLOR_TEXT_MODIFIER_NEUTRAL
                        : (fVal > 0.0F ? Colors.COLOR_TEXT_MODIFIER_POSITIVE : Colors.COLOR_TEXT_MODIFIER_NEGATIVE)
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }

            @Override
            protected void drawButtonBG_Animation(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
            }
         }
      );
      menuElements.add(
         new ButtonStatsBudget_Right2(
            "+" + CFG.getPrecision2(Game.getCiv(iCivID).fTotalIncomePerMonth + Game.getCiv(iCivID).civBonuses.MonthlyIncome, 100),
            paddingLeft + tButtonW + CFG.PADDING,
            buttonY,
            tButtonRightW,
            tButtonH
         ) {
            @Override
            public String getTextToDraw() {
               if (this.lastValue != Game.getCiv(iCivID).fTotalIncomePerMonth + Game.getCiv(iCivID).civBonuses.MonthlyIncome) {
                  float fVal = Game.getCiv(iCivID).fTotalIncomePerMonth + Game.getCiv(iCivID).civBonuses.MonthlyIncome;
                  this.setText((fVal > 0.0F ? "+" : "") + CFG.getPrecision2(fVal, 100));
                  this.lastValue = fVal;
                  if (fVal == 0.0F) {
                     this.nColor = Colors.COLOR_TEXT_MODIFIER_NEUTRAL;
                     this.nColorH = Colors.COLOR_TEXT_MODIFIER_NEUTRAL_HOVER;
                     this.nColorA = Colors.COLOR_TEXT_MODIFIER_NEUTRAL_ACTIVE;
                  } else if (fVal > 0.0F) {
                     this.nColor = Colors.COLOR_TEXT_MODIFIER_POSITIVE;
                     this.nColorH = Colors.COLOR_TEXT_MODIFIER_POSITIVE_HOVER;
                     this.nColorA = Colors.COLOR_TEXT_MODIFIER_POSITIVE_ACTIVE;
                  } else {
                     this.nColor = Colors.COLOR_TEXT_MODIFIER_NEGATIVE;
                     this.nColorH = Colors.COLOR_TEXT_MODIFIER_NEGATIVE_HOVER;
                     this.nColorA = Colors.COLOR_TEXT_MODIFIER_NEGATIVE_ACTIVE;
                  }
               }

               return super.getTextToDraw();
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      float fGold = Game.getCiv(iCivID).fTotalExpensesPerMonth;
      menuElements.add(
         new ButtonStatsRectIMG_Bonuses_Style(Game.lang.get("TotalExpenses"), "", Images.goldNegative, paddingLeft, buttonY, tButtonW, tButtonH, tIconMaxW) {
            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               float fVal = Game.getCiv(iCivID).fTotalExpensesPerMonth;
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     Game.lang.get("TotalExpenses") + ": ",
                     "" + (fVal > 0.0F ? "-" : "") + CFG.getPrecision2(fVal, 100),
                     Images.goldNegative,
                     CFG.FONT_REGULAR,
                     CFG.FONT_BOLD,
                     Colors.HOVER_LEFT,
                     fVal == 0.0F
                        ? Colors.COLOR_TEXT_MODIFIER_NEUTRAL
                        : (fVal > 0.0F ? Colors.COLOR_TEXT_MODIFIER_NEGATIVE : Colors.COLOR_TEXT_MODIFIER_POSITIVE)
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }

            @Override
            protected void drawButtonBG_Animation(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
            }
         }
      );
      menuElements.add(
         new ButtonStatsBudget_Right2(
            (fGold >= 0.0F ? "+" : "-") + CFG.getPrecision2(Game.getCiv(iCivID).fTotalExpensesPerMonth, 100),
            paddingLeft + tButtonW + CFG.PADDING,
            buttonY,
            tButtonRightW,
            tButtonH
         ) {
            @Override
            public String getTextToDraw() {
               if (this.lastValue != Game.getCiv(iCivID).fTotalExpensesPerMonth) {
                  float fVal = Game.getCiv(iCivID).fTotalExpensesPerMonth;
                  this.setText((fVal > 0.0F ? "-" : "") + CFG.getPrecision2(fVal, 100));
                  this.lastValue = fVal;
                  if (fVal == 0.0F) {
                     this.nColor = Colors.COLOR_TEXT_MODIFIER_NEUTRAL;
                     this.nColorH = Colors.COLOR_TEXT_MODIFIER_NEUTRAL_HOVER;
                     this.nColorA = Colors.COLOR_TEXT_MODIFIER_NEUTRAL_ACTIVE;
                  } else if (fVal > 0.0F) {
                     this.nColor = Colors.COLOR_TEXT_MODIFIER_NEGATIVE;
                     this.nColorH = Colors.COLOR_TEXT_MODIFIER_NEGATIVE_HOVER;
                     this.nColorA = Colors.COLOR_TEXT_MODIFIER_NEGATIVE_ACTIVE;
                  } else {
                     this.nColor = Colors.COLOR_TEXT_MODIFIER_POSITIVE;
                     this.nColorH = Colors.COLOR_TEXT_MODIFIER_POSITIVE_HOVER;
                     this.nColorA = Colors.COLOR_TEXT_MODIFIER_POSITIVE_ACTIVE;
                  }
               }

               return super.getTextToDraw();
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      fGold = Game.getCiv(iCivID).fTotalIncomePerMonth + Game.getCiv(iCivID).civBonuses.MonthlyIncome - Game.getCiv(iCivID).fTotalExpensesPerMonth;
      menuElements.add(
         new ButtonStatsRectIMG_Bonuses_Style(Game.lang.get("Balance"), "", Images.gold, paddingLeft, buttonY, tButtonW, tButtonH, tIconMaxW) {
            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               float fVal = Game.getCiv(iCivID).fTotalIncomePerMonth
                  + Game.getCiv(iCivID).civBonuses.MonthlyIncome
                  - Game.getCiv(iCivID).fTotalExpensesPerMonth;
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     Game.lang.get("TotalIncome") + ": ",
                     "" + (fVal > 0.0F ? "+" : "") + CFG.getPrecision2(fVal, 100),
                     Images.gold,
                     CFG.FONT_REGULAR,
                     CFG.FONT_BOLD,
                     Colors.HOVER_LEFT,
                     fVal == 0.0F
                        ? Colors.COLOR_TEXT_MODIFIER_NEUTRAL
                        : (fVal > 0.0F ? Colors.COLOR_TEXT_MODIFIER_POSITIVE : Colors.COLOR_TEXT_MODIFIER_NEGATIVE)
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }

            @Override
            protected void drawButtonBG_Animation(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
            }
         }
      );
      menuElements.add(
         new ButtonStatsBudget_Right2(
            (fGold >= 0.0F ? "+" : "") + CFG.getPrecision2(fGold, 100), paddingLeft + tButtonW + CFG.PADDING, buttonY, tButtonRightW, tButtonH
         ) {
            @Override
            public String getTextToDraw() {
               if (this.lastValue
                  != Game.getCiv(iCivID).fTotalIncomePerMonth + Game.getCiv(iCivID).civBonuses.MonthlyIncome - Game.getCiv(iCivID).fTotalExpensesPerMonth) {
                  float fVal = Game.getCiv(iCivID).fTotalIncomePerMonth
                     + Game.getCiv(iCivID).civBonuses.MonthlyIncome
                     - Game.getCiv(iCivID).fTotalExpensesPerMonth;
                  this.setText2((fVal > 0.0F ? "+" : "") + CFG.getPrecision2(fVal, 100));
                  this.lastValue = fVal;
                  if (fVal == 0.0F) {
                     this.nColor = Colors.COLOR_TEXT_MODIFIER_NEUTRAL;
                     this.nColorH = Colors.COLOR_TEXT_MODIFIER_NEUTRAL_HOVER;
                     this.nColorA = Colors.COLOR_TEXT_MODIFIER_NEUTRAL_ACTIVE;
                  } else if (fVal > 0.0F) {
                     this.nColor = Colors.COLOR_TEXT_MODIFIER_POSITIVE;
                     this.nColorH = Colors.COLOR_TEXT_MODIFIER_POSITIVE_HOVER;
                     this.nColorA = Colors.COLOR_TEXT_MODIFIER_POSITIVE_ACTIVE;
                  } else {
                     this.nColor = Colors.COLOR_TEXT_MODIFIER_NEGATIVE;
                     this.nColorH = Colors.COLOR_TEXT_MODIFIER_NEGATIVE_HOVER;
                     this.nColorA = Colors.COLOR_TEXT_MODIFIER_NEGATIVE_ACTIVE;
                  }
               }

               return super.getTextToDraw();
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      fGold = Game.getCiv(iCivID).fGold;
      menuElements.add(
         new ButtonStatsBudget_Balance_Spy(
            Game.lang.get("Treasury") + ": ",
            (fGold > 0.0F ? "" : "") + CFG.getPrecision2(fGold, 100),
            Images.gold,
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            CFG.TEXT_HEIGHT + CFG.PADDING * 10
         ) {
            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_FlagCiv_Title(iCivID, CivilizationRanking.getCivilizationRank_Name(Game.getCiv(iCivID).iCivRankID)));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               float fVal = Game.getCiv(iCivID).fGold;
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     Game.lang.get("Treasury") + ": ",
                     "" + (fVal > 0.0F ? "" : "") + CFG.getPrecision2(fVal, 100),
                     Images.gold,
                     CFG.FONT_REGULAR,
                     CFG.FONT_BOLD,
                     Colors.HOVER_LEFT,
                     fVal == 0.0F
                        ? Colors.COLOR_TEXT_MODIFIER_NEUTRAL
                        : (fVal > 0.0F ? Colors.COLOR_TEXT_MODIFIER_POSITIVE : Colors.COLOR_TEXT_MODIFIER_NEGATIVE)
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }

            @Override
            public String getText2() {
               if (this.lastValue != Game.getCiv(iCivID).fGold) {
                  float fGoldx = Game.getCiv(iCivID).fGold;
                  this.setText2((fGoldx > 0.0F ? "+" : "") + CFG.getPrecision2(fGoldx, 100));
                  this.lastValue = fGoldx;
                  if (fGoldx == 0.0F) {
                     this.nColor = Colors.COLOR_TEXT_MODIFIER_NEUTRAL;
                     this.nColorH = Colors.COLOR_TEXT_MODIFIER_NEUTRAL_HOVER;
                     this.nColorA = Colors.COLOR_TEXT_MODIFIER_NEUTRAL_ACTIVE;
                  } else if (fGoldx > 0.0F) {
                     this.nColor = Colors.COLOR_TEXT_MODIFIER_POSITIVE;
                     this.nColorH = Colors.COLOR_TEXT_MODIFIER_POSITIVE_HOVER;
                     this.nColorA = Colors.COLOR_TEXT_MODIFIER_POSITIVE_ACTIVE;
                  } else {
                     this.nColor = Colors.COLOR_TEXT_MODIFIER_NEGATIVE;
                     this.nColorH = Colors.COLOR_TEXT_MODIFIER_NEGATIVE_HOVER;
                     this.nColorA = Colors.COLOR_TEXT_MODIFIER_NEGATIVE_ACTIVE;
                  }
               }

               return this.sText2;
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new Text_Title_v2Center(
            Game.lang.get("Armies"), -1, Images.boxTitleBORDERWIDTH, buttonY, menuWidth - Images.boxTitleBORDERWIDTH * 2, CFG.TEXT_HEIGHT + CFG.PADDING * 6
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new ButtonStatsRectIMG_Bonuses_Style(
            Game.lang.get("Manpower") + ": ",
            "" + CFG.getNumberWithSpaces("" + CFG.getPrecision2(Game.getCiv(iCivID).fManpower, 1)),
            Game_Calendar.IMG_MANPOWER,
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            CFG.BUTTON_HEIGHT3,
            ImageManager.getImage(Images.gold).getWidth()
         ) {
            @Override
            public Color getColorBonus() {
               return Colors.HOVER_GOLD;
            }

            @Override
            protected void drawButtonBG_Animation(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new ButtonStatsRectIMG_Bonuses_Style(
            Game.lang.get("MaximumManpower") + ": ",
            "" + CFG.getNumberWithSpaces("" + CFG.getPrecision2(Game.getCiv(iCivID).fManpowerMax, 1)),
            Game_Calendar.IMG_MANPOWER,
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            CFG.BUTTON_HEIGHT3,
            ImageManager.getImage(Images.gold).getWidth()
         ) {
            @Override
            public Color getColorBonus() {
               return Colors.HOVER_GOLD;
            }

            @Override
            protected void drawButtonBG_Animation(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new ButtonStatsRectIMG_Bonuses_Style(
            Game.lang.get("ManpowerPerMonth") + ": ",
            "+" + CFG.getPrecision2(Game.getCiv(iCivID).fManpowerPerMonth, 10),
            Game_Calendar.IMG_MANPOWER,
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            CFG.BUTTON_HEIGHT3,
            ImageManager.getImage(Images.gold).getWidth()
         ) {
            @Override
            public Color getColorBonus() {
               return Colors.COLOR_TEXT_MODIFIER_POSITIVE;
            }

            @Override
            protected void drawButtonBG_Animation(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      int extraX = 0;
      menuElements.add(
         new ButtonStatsRectIMG_Bonuses_Style(
            Game.lang.get("BattleWidth") + ": ",
            "" + BattleManager.getBattleWidth(iCivID),
            Images.battleWidth,
            paddingLeft,
            buttonY,
            (menuWidth - paddingLeft * 2 - CFG.PADDING) / 2,
            CFG.BUTTON_HEIGHT3,
            ImageManager.getImage(Images.battleWidth).getWidth()
         ) {
            @Override
            public Color getColorBonus() {
               return Colors.HOVER_GOLD;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("BattleWidth") + ": "));
               nData.add(new MenuElement_HoverElement_Type_TextTitle("" + BattleManager.getBattleWidth(iCivID), Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle(Images.battleWidth, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Line());
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(
                  new MenuElement_HoverElement_Type_Text_Desc(
                     Game.lang.get("BattleWidthRefersToTheNumberOfUnitsThatCanBeDeployedInAFormationOnTheBattlefield"),
                     CFG.FONT_REGULAR_SMALL,
                     Colors.HOVER_RIGHT
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }

            @Override
            protected void drawButtonBG_Animation(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
            }
         }
      );
      menuElements.add(
         new ButtonStatsRectIMG_Bonuses_Style(
            Game.lang.get("Discipline") + ": ",
            "" + CFG.getPrecision2(Game.getCiv(iCivID).civBonuses.Discipline * 100.0F, 10) + "%",
            Images.discipline,
            paddingLeft + CFG.PADDING + (menuWidth - paddingLeft * 2 - CFG.PADDING) / 2,
            buttonY,
            (menuWidth - paddingLeft * 2 - CFG.PADDING) / 2,
            CFG.BUTTON_HEIGHT3,
            ImageManager.getImage(Images.battleWidth).getWidth()
         ) {
            @Override
            public Color getColorBonus() {
               return Colors.HOVER_GOLD;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Discipline") + ": "));
               nData.add(
                  new MenuElement_HoverElement_Type_TextTitle(
                     "" + CFG.getPrecision2(Game.getCiv(iCivID).civBonuses.Discipline * 100.0F, 10) + "%", Colors.HOVER_GOLD
                  )
               );
               nData.add(new MenuElement_HoverElement_Type_ImageTitle(Images.discipline, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Line());
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(
                  new MenuElement_HoverElement_Type_Text_Desc(
                     Game.lang.get("DisciplineSignifiesOrganizationAndCoordinationOfTheArmyInBattleIncreasingUnitsAttack"),
                     CFG.FONT_REGULAR_SMALL,
                     Colors.HOVER_RIGHT
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }

            @Override
            protected void drawButtonBG_Animation(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new ButtonStatsRectIMG_Bonuses_Style(
            Game.lang.get("NumberOfRegiments") + ": ",
            "" + CFG.getNumberWithSpaces("" + CFG.getPrecision2((float)Game.getCiv(iCivID).getArmyRegimentSize(), 1)),
            Game_Calendar.IMG_MANPOWER,
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            CFG.BUTTON_HEIGHT3,
            ImageManager.getImage(Images.gold).getWidth()
         ) {
            @Override
            public Color getColorBonus() {
               return Colors.HOVER_GOLD;
            }

            @Override
            protected void drawButtonBG_Animation(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      if (Game.getCiv(iCivID).iArmyPositionSize == 0) {
         menuElements.add(
            new Text_StaticBG(
               Game.lang.get("None") + ".",
               CFG.FONT_REGULAR_SMALL,
               -1,
               paddingLeft + extraX,
               buttonY,
               menuWidth - paddingLeft * 2,
               ImageManager.getImage(Images.generalFrameBattle).getHeight() + CFG.TEXT_HEIGHT + CFG.PADDING * 2
            )
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      } else {
         int buttonX = paddingLeft;
         List<InGame_Armies.SortedArmies> toSort = new ArrayList<>();
         List<InGame_Armies.SortedArmies> sortedArmies = new ArrayList<>();

         for (int i = 0; i < Game.getCiv(iCivID).iArmyPositionSize; i++) {
            int nProvinceID = Game.getCiv(iCivID).getArmyPosition(i);
            if (nProvinceID >= 0) {
               for (int j = Game.getProvince(nProvinceID).getArmySize() - 1; j >= 0; j--) {
                  if (Game.getProvince(nProvinceID).getArmy(j).civID == iCivID
                     && Game.getCiv(iCivID).getArmyPositionKey(i).equals(Game.getProvince(nProvinceID).getArmy(j).key)) {
                     toSort.add(new InGame_Armies.SortedArmies(nProvinceID, j, InGame_Armies.getSortKey(Game.getProvince(nProvinceID).getArmy(j).key)));
                  }
               }
            }
         }

         while (toSort.size() > 0) {
            int addID = 0;
            int ix = 1;

            for (int iSize = toSort.size(); ix < iSize; ix++) {
               if (toSort.get(addID).sortKey > toSort.get(ix).sortKey) {
                  addID = ix;
               }
            }

            sortedArmies.add(toSort.get(addID));
            toSort.remove(addID);
         }

         int pinH = (ButtonArmyBattle.getButtonHeight() - CFG.PADDING) / 2;
         int ix = 0;

         for (int iSizex = sortedArmies.size(); ix < iSizex; ix++) {
            int tTitleY = buttonY;
            buttonY += CFG.TEXT_HEIGHT + CFG.PADDING * 6;
            buttonX += CFG.PADDING;
            if (Game.getProvince(sortedArmies.get(ix).iProvinceID).getArmy(sortedArmies.get(ix).iArmyID).armyGeneral == null) {
               menuElements.add(
                  new ButtonArmyNoGeneral2_Armies(
                     Game.lang.get("NoGeneral"),
                     iCivID,
                     buttonX + extraX,
                     buttonY,
                     Game.getProvince(sortedArmies.get(ix).iProvinceID).getArmy(sortedArmies.get(ix).iArmyID).key,
                     Game.getProvince(sortedArmies.get(ix).iProvinceID).getArmy(sortedArmies.get(ix).iArmyID).civID,
                     sortedArmies.get(ix).iProvinceID
                  ) {
                     @Override
                     public void actionElement() {
                     }

                     @Override
                     public void buildElementHover() {
                        List<MenuElement_HoverElement> nElements = new ArrayList<>();
                        List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                        nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("NoGeneral"), CFG.FONT_BOLD));
                        nData.add(new MenuElement_HoverElement_Type_FlagTitle(this.iCivID, CFG.PADDING, 0));
                        nElements.add(new MenuElement_HoverElement(nData));
                        nData.clear();
                        this.menuElementHover = new MenuElement_Hover(nElements);
                     }
                  }
               );
            } else {
               menuElements.add(
                  new ButtonArmyGeneral2_Armies(
                     Game.getProvince(sortedArmies.get(ix).iProvinceID).getArmy(sortedArmies.get(ix).iArmyID).armyGeneral.n,
                     Game.getProvince(sortedArmies.get(ix).iProvinceID).getArmy(sortedArmies.get(ix).iArmyID).civID,
                     Game.getProvince(sortedArmies.get(ix).iProvinceID).getArmy(sortedArmies.get(ix).iArmyID).armyGeneral.getAttack(),
                     Game.getProvince(sortedArmies.get(ix).iProvinceID).getArmy(sortedArmies.get(ix).iArmyID).armyGeneral.getDefense(),
                     buttonX + extraX,
                     buttonY,
                     Game.getProvince(sortedArmies.get(ix).iProvinceID).getArmy(sortedArmies.get(ix).iArmyID).armyGeneral.g,
                     Game.getProvince(sortedArmies.get(ix).iProvinceID).getArmy(sortedArmies.get(ix).iArmyID).armyGeneral.d,
                     Game.getProvince(sortedArmies.get(ix).iProvinceID).getArmy(sortedArmies.get(ix).iArmyID).armyGeneral.m,
                     Game.getProvince(sortedArmies.get(ix).iProvinceID).getArmy(sortedArmies.get(ix).iArmyID).armyGeneral.y,
                     Game.getProvince(sortedArmies.get(ix).iProvinceID).getArmy(sortedArmies.get(ix).iArmyID).key,
                     Game.getProvince(sortedArmies.get(ix).iProvinceID).getArmy(sortedArmies.get(ix).iArmyID).civID,
                     sortedArmies.get(ix).iProvinceID,
                     Game.getProvince(sortedArmies.get(ix).iProvinceID).getArmy(sortedArmies.get(ix).iArmyID).armyGeneral.sI,
                     Game.getProvince(sortedArmies.get(ix).iProvinceID).getArmy(sortedArmies.get(ix).iArmyID).armyGeneral.getCombatExperience()
                  ) {
                     @Override
                     public void actionElement() {
                     }
                  }
               );
            }

            buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;

            for (int k = 0; k < Game.getProvince(sortedArmies.get(ix).iProvinceID).getArmy(sortedArmies.get(ix).iArmyID).iArmyRegimentSize; k++) {
               int tUnits = Game.getProvince(sortedArmies.get(ix).iProvinceID).getArmy(sortedArmies.get(ix).iArmyID).lArmyRegiment.get(k).num;
               int numOfRegiments = 1;

               for (int o = k + 1;
                  o < Game.getProvince(sortedArmies.get(ix).iProvinceID).getArmy(sortedArmies.get(ix).iArmyID).iArmyRegimentSize
                     && Game.getProvince(sortedArmies.get(ix).iProvinceID).getArmy(sortedArmies.get(ix).iArmyID).lArmyRegiment.get(k).uID
                        == Game.getProvince(sortedArmies.get(ix).iProvinceID).getArmy(sortedArmies.get(ix).iArmyID).lArmyRegiment.get(o).uID
                     && Game.getProvince(sortedArmies.get(ix).iProvinceID).getArmy(sortedArmies.get(ix).iArmyID).lArmyRegiment.get(k).aID
                        == Game.getProvince(sortedArmies.get(ix).iProvinceID).getArmy(sortedArmies.get(ix).iArmyID).lArmyRegiment.get(o).aID;
                  o++
               ) {
                  k++;
                  numOfRegiments++;
                  tUnits += Game.getProvince(sortedArmies.get(ix).iProvinceID).getArmy(sortedArmies.get(ix).iArmyID).lArmyRegiment.get(k).num;
               }

               menuElements.add(
                  new ButtonArmyBattle(
                     "" + tUnits,
                     numOfRegiments,
                     Game.getProvince(sortedArmies.get(ix).iProvinceID).getArmy(sortedArmies.get(ix).iArmyID).civID,
                     buttonX + extraX,
                     buttonY,
                     Game.getProvince(sortedArmies.get(ix).iProvinceID).getArmy(sortedArmies.get(ix).iArmyID).lArmyRegiment.get(k).uID,
                     Game.getProvince(sortedArmies.get(ix).iProvinceID).getArmy(sortedArmies.get(ix).iArmyID).lArmyRegiment.get(k).aID,
                     false
                  ) {
                     @Override
                     public void actionElement() {
                     }
                  }
               );
               buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
            }

            buttonY = menuElements.get(menuElements.size() - 1).getPosY()
               + menuElements.get(menuElements.size() - 1).getHeight()
               + buttonYPadding
               + CFG.PADDING;
            menuElements.add(
               new TextArmies(
                  Game.lang.get("Army"),
                  CFG.getNumberWithSpaces("" + Game.getProvince(sortedArmies.get(ix).iProvinceID).getArmy(sortedArmies.get(ix).iArmyID).iArmy),
                  paddingLeft + extraX,
                  tTitleY,
                  Math.max(buttonX, menuWidth - paddingLeft * 2),
                  CFG.TEXT_HEIGHT + CFG.PADDING * 5,
                  Game.getProvince(sortedArmies.get(ix).iProvinceID).getArmy(sortedArmies.get(ix).iArmyID).key,
                  Game.getProvince(sortedArmies.get(ix).iProvinceID).getArmy(sortedArmies.get(ix).iArmyID).civID,
                  sortedArmies.get(ix).iProvinceID,
                  menuWidth - paddingLeft * 2
               ) {
                  @Override
                  public void actionElement() {
                  }

                  @Override
                  public void buildElementHover() {
                     this.menuElementHover = null;
                  }
               }
            );
            buttonX = paddingLeft;
         }
      }

      menuElements.add(
         new Text_Title_v2Center(
            Game.lang.get("Technology"), -1, Images.boxTitleBORDERWIDTH, buttonY, menuWidth - Images.boxTitleBORDERWIDTH * 2, CFG.TEXT_HEIGHT + CFG.PADDING * 6
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new ButtonStatsRectIMG_Bonuses_Style(
            Game.lang.get("UnlockedTechnologies") + ": ",
            "" + CFG.getNumberWithSpaces("" + Game.getCiv(iCivID).getResearchedTechnologies()),
            Game_Calendar.IMG_TECHNOLOGY,
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            CFG.BUTTON_HEIGHT3,
            ImageManager.getImage(Images.gold).getWidth()
         ) {
            @Override
            public Color getColorBonus() {
               return Colors.HOVER_GOLD;
            }

            @Override
            protected void drawButtonBG_Animation(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new ButtonStatsRectIMG_Bonuses_Style(
            Game.lang.get("ResearchPerMonth") + ": ",
            "" + CFG.getPrecision2(Game.getCiv(iCivID).fResearchPerMonth, 100),
            Game_Calendar.IMG_TECHNOLOGY,
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            CFG.BUTTON_HEIGHT3,
            ImageManager.getImage(Images.gold).getWidth()
         ) {
            @Override
            public Color getColorBonus() {
               return Colors.HOVER_GOLD;
            }

            @Override
            protected void drawButtonBG_Animation(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      int techID = -1;
      if (Game.getCiv(iCivID).getActiveTechResearch() >= 0) {
         techID = Game.getCiv(iCivID).getActiveTechResearch();
      } else if (Game.getCiv(iCivID).getAlternativeTechResearch() >= 0) {
         techID = Game.getCiv(iCivID).getAlternativeTechResearch();
      }

      if (techID >= 0) {
         int statW = menuWidth - paddingLeft * 2 - CFG.PADDING * 3 - ImageManager.getImage(Images.techGray).getWidth();
         menuElements.add(
            new TextIcon2(
               "" + CFG.getPrecision2(TechnologyTree.getResearchCost(techID, iCivID), 10),
               Game_Calendar.IMG_TECHNOLOGY,
               paddingLeft,
               buttonY,
               statW,
               ImageManager.getImage(Images.techGray).getHeight()
            ) {
               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("ResearchCost") + ": ", CFG.FONT_BOLD));
                  nData.add(new MenuElement_HoverElement_Type_TextTitle(this.getText(), CFG.FONT_BOLD, Colors.COLOR_TEXT_RESEARCH));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle(Game_Calendar.IMG_TECHNOLOGY, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements);
               }

               @Override
               public int getTextH() {
                  return CFG.TEXT_HEIGHT + CFG.PADDING * 4;
               }
            }
         );
         int var34 = paddingLeft + menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         menuElements.add(new ButtonTechnology(Images.techBlue, techID, var34, buttonY, false, -1, iCivID) {
            @Override
            public void actionElement() {
            }
         });
      }

      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new Text_Title_v2Center(
            Game.lang.get("More"), -1, Images.boxTitleBORDERWIDTH, buttonY, menuWidth - Images.boxTitleBORDERWIDTH * 2, CFG.TEXT_HEIGHT + CFG.PADDING * 6
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      if (Game.getCiv(iCivID).getNukes() > 0 || Game.getCiv(iCivID).canBuildNuke) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses_Style(
               Game.lang.get("AtomicBombs") + ": ",
               "" + CFG.getNumberWithSpaces("" + CFG.getPrecision2((float)Game.getCiv(iCivID).getNukes(), 1)),
               Images.nuke,
               paddingLeft,
               buttonY,
               menuWidth - paddingLeft * 2,
               CFG.BUTTON_HEIGHT3,
               ImageManager.getImage(Images.gold).getWidth()
            ) {
               @Override
               public Color getColorBonus() {
                  return Colors.HOVER_GOLD;
               }

               @Override
               protected void drawButtonBG_Animation(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
               }
            }
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      }

      menuElements.add(
         new ButtonStatsRectIMG_Bonuses_Style(
            Game.lang.get("LegacyPoints") + ": ",
            "" + CFG.getNumberWithSpaces("" + CFG.getPrecision2(Game.getCiv(iCivID).fLegacy, 1)),
            Images.legacy,
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            CFG.BUTTON_HEIGHT3,
            ImageManager.getImage(Images.gold).getWidth()
         ) {
            @Override
            public Color getColorBonus() {
               return Colors.HOVER_GOLD;
            }

            @Override
            protected void drawButtonBG_Animation(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new ButtonStatsRectIMG_Bonuses_Style(
            Game.lang.get("Loans") + ": ",
            "" + Game.getCiv(iCivID).iLoansSize + " / " + Game.getLoanMaxNumber(iCivID),
            Images.loan,
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            CFG.BUTTON_HEIGHT3,
            ImageManager.getImage(Images.gold).getWidth()
         ) {
            @Override
            public Color getColorBonus() {
               return Colors.HOVER_GOLD;
            }

            @Override
            protected void drawButtonBG_Animation(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuY += InGame_CourtOptions.menuH;
      int menuHeight = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - CFG.PADDING * 3);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, menuHeight)));
      this.initMenu(null, menuX, menuY, menuWidth, menuHeight, menuElements, false, false);
      this.drawScrollPositionAlways = false;
      Game.menuManager.setInGame_CivOptions_Title(Game.lang.get("EspionageReports"));
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
      if (Game_Calendar.TURN_ID >= this.iReportEndTurnID) {
         InGame_Court.iActiveCivID = Game.player.iCivID;
         Game.menuManager.rebuildInGame_Court();
         Game.menuManager.setVisibleInGame_Court(true);
      }
   }

   @Override
   public void setVisible(boolean visible) {
      super.setVisible(visible);
      InGame_Court.lTime = CFG.currentTimeMillis;
      InGame_Court.lTime2 = CFG.currentTimeMillis;
   }

   @Override
   public void onHovered() {
      super.onHovered();
      Game.menuManager.setOrderOfMenu_InGameCourt();
   }
}
