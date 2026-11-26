package aoc.kingdoms.lukasz.menusInGame.AllianceSpecial;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.RulersManager;
import aoc.kingdoms.lukasz.map.allianceHRE.HREManager;
import aoc.kingdoms.lukasz.map.civilization.CivilizationRanking;
import aoc.kingdoms.lukasz.map.province.ProvinceBorderManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.MenuManager;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonFlagRect;
import aoc.kingdoms.lukasz.menu_element.button.ButtonFlag_AllianceSpecial;
import aoc.kingdoms.lukasz.menu_element.button.ButtonFlag_Diplomacy;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame_Image;
import aoc.kingdoms.lukasz.menu_element.button.ButtonRuler_Diplomacy;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Bonuses;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Rank;
import aoc.kingdoms.lukasz.menu_element.button.Button_HRE_Reform;
import aoc.kingdoms.lukasz.menu_element.button.Button_HRE_Reform_Green;
import aoc.kingdoms.lukasz.menu_element.button.Button_HRE_Reform_Red;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_FlagCiv_Title;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_FlagCiv_Title_AllianceSpecial;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text_Desc;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextIcon2;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextIcon2_Horizontal;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID_FlagCiv;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID_FlagCiv_SpecialEmperor;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID_FlagCiv_SpecialEmpty;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID_Special;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_TitleSpecial_ID;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Title_v2_TextLR;
import aoc.kingdoms.lukasz.menusInGame.InGame_CivBonuses;
import aoc.kingdoms.lukasz.menusInGame.Civ.InGame_Civ;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_CourtOptions2;
import aoc.kingdoms.lukasz.menusInGame.Info.InGame_Info;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_AllianceSpecial extends Menu {
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static long lTime2 = 0L;
   public static int allianceID;
   public static boolean showFirstTier = true;
   public static boolean showSecondTier = false;
   public boolean leaveAllianceConfirm = false;

   public InGame_AllianceSpecial(int nAllianceID) {
      allianceID = nAllianceID;
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = Images.boxTitleBORDERWIDTH + CFG.PADDING * 2;
      int paddingLeft2 = Images.boxTitleBORDERWIDTH + CFG.PADDING;
      int titleHeight = ImageManager.getImage(Images.title500).getHeight();
      int menuWidth = ImageManager.getImage(Images.title500).getWidth();
      int menuX = InGame_CourtOptions2.getOtherMenuPosX_2();
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title1Red).getHeight();
      int buttonY = CFG.PADDING;
      int topHeight = ButtonRuler_Diplomacy.getButtonHeight() + CFG.PADDING * 2;
      RulersManager.loadRulerIMG_DiplomacyLeft(Game.alliancesSpecial.get(allianceID).iLeaderCivID);
      buttonY += CFG.PADDING;
      int topTitleH = CFG.BUTTON_HEIGHT3 - CFG.PADDING * 2;
      menuElements.add(
         new ButtonRuler_Diplomacy(
            Game.alliancesSpecial.get(allianceID).iLeaderCivID, menuWidth - ButtonRuler_Diplomacy.getButtonWidth() - paddingLeft, buttonY
         ) {
            @Override
            public void actionElement() {
               Game.menuManager.setVisibleInGame_PopUp(false);
               super.actionElement();
            }
         }
      );
      menuElements.add(
         new Text_StaticBG_ID_FlagCiv_SpecialEmperor(
            Game.lang.get(Game.alliancesSpecial.get(allianceID).Name_Leader) + ": ",
            Game.getCiv(Game.alliancesSpecial.get(allianceID).iLeaderCivID).getCivName(),
            CFG.FONT_REGULAR,
            CFG.PADDING * 2,
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2 - CFG.PADDING - ButtonRuler_Diplomacy.getButtonWidth(),
            topTitleH,
            Game.alliancesSpecial.get(allianceID).iLeaderCivID
         ) {
            @Override
            public void actionElement() {
               if (Game.getCiv(Game.alliancesSpecial.get(InGame_AllianceSpecial.allianceID).iLeaderCivID).getCapitalProvinceID() >= 0) {
                  if (Game.iActiveProvince == Game.getCiv(Game.alliancesSpecial.get(InGame_AllianceSpecial.allianceID).iLeaderCivID).getCapitalProvinceID()) {
                     Game.menuManager.hideCourtCiv();
                     Game.menuManager.setVisibleInGame_Civ(false);
                     Game.setActiveProvinceID(Game.getCiv(Game.alliancesSpecial.get(InGame_AllianceSpecial.allianceID).iLeaderCivID).getCapitalProvinceID());
                     Game.menuManager.rebuildInGame_ProvinceInfo(true);
                     ProvinceBorderManager.action.setProvinceID(Game.iActiveProvince);
                     Game.menuManager.setVisibleInGame_PopUp(false);
                  } else {
                     Game.mapCoords
                        .centerToProvinceID(Game.getCiv(Game.alliancesSpecial.get(InGame_AllianceSpecial.allianceID).iLeaderCivID).getCapitalProvinceID());
                     Game.setActiveProvinceID(Game.getCiv(Game.alliancesSpecial.get(InGame_AllianceSpecial.allianceID).iLeaderCivID).getCapitalProvinceID());
                     ProvinceBorderManager.action.setProvinceID(Game.iActiveProvince);
                  }
               }
            }
         }
      );
      int statsY = buttonY + topTitleH + CFG.PADDING;
      int statsY2 = statsY;
      int innerWidth = (menuWidth - paddingLeft * 2 - CFG.PADDING - ButtonRuler_Diplomacy.getButtonWidth() - CFG.PADDING) / 2;
      menuElements.add(
         new TextIcon2_Horizontal(
            Game.getCiv(Game.alliancesSpecial.get(allianceID).iLeaderCivID).getCapitalProvinceID() >= 0
               ? Game.getProvince(Game.getCiv(Game.alliancesSpecial.get(allianceID).iLeaderCivID).getCapitalProvinceID()).getProvinceName()
               : "",
            Images.capital,
            paddingLeft,
            statsY,
            innerWidth,
            topTitleH,
            ImageManager.getImage(Images.capital).getWidth() + CFG.PADDING * 2
         ) {
            @Override
            public void buildElementHover() {
               if (Game.getCiv(Game.alliancesSpecial.get(InGame_AllianceSpecial.allianceID).iLeaderCivID).getCapitalProvinceID() >= 0) {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(
                     new MenuElement_HoverElement_Type_FlagCiv_Title(
                        Game.alliancesSpecial.get(InGame_AllianceSpecial.allianceID).iLeaderCivID,
                        Game.lang.get("Capital")
                           + ": "
                           + Game.getProvince(Game.getCiv(Game.alliancesSpecial.get(InGame_AllianceSpecial.allianceID).iLeaderCivID).getCapitalProvinceID())
                              .getProvinceName()
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        Game.lang.get("Population") + ": ",
                        CFG.getNumberWithSpaces(
                           ""
                              + Game.getProvince(Game.getCiv(Game.alliancesSpecial.get(InGame_AllianceSpecial.allianceID).iLeaderCivID).getCapitalProvinceID())
                                 .getPopulationTotal()
                        ),
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
               } else {
                  this.menuElementHover = null;
               }
            }

            @Override
            public void actionElement() {
               if (Game.getCiv(Game.alliancesSpecial.get(InGame_AllianceSpecial.allianceID).iLeaderCivID).getCapitalProvinceID() >= 0) {
                  if (Game.iActiveProvince == Game.getCiv(Game.alliancesSpecial.get(InGame_AllianceSpecial.allianceID).iLeaderCivID).getCapitalProvinceID()) {
                     Game.menuManager.hideCourtCiv();
                     Game.menuManager.setVisibleInGame_Civ(false);
                     Game.setActiveProvinceID(Game.getCiv(Game.alliancesSpecial.get(InGame_AllianceSpecial.allianceID).iLeaderCivID).getCapitalProvinceID());
                     Game.menuManager.rebuildInGame_ProvinceInfo(true);
                     ProvinceBorderManager.action.setProvinceID(Game.iActiveProvince);
                     Game.menuManager.setVisibleInGame_PopUp(false);
                  } else {
                     Game.mapCoords
                        .centerToProvinceID(Game.getCiv(Game.alliancesSpecial.get(InGame_AllianceSpecial.allianceID).iLeaderCivID).getCapitalProvinceID());
                     Game.setActiveProvinceID(Game.getCiv(Game.alliancesSpecial.get(InGame_AllianceSpecial.allianceID).iLeaderCivID).getCapitalProvinceID());
                     ProvinceBorderManager.action.setProvinceID(Game.iActiveProvince);
                  }
               }
            }
         }
      );
      statsY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new ButtonFlag_AllianceSpecial(
            allianceID,
            paddingLeft + innerWidth / 2 - ButtonFlag_AllianceSpecial.getButtonWidth() / 2,
            statsY + (ButtonRuler_Diplomacy.getButtonHeight() - topTitleH * 2 - CFG.PADDING * 2) / 2 - ButtonFlag_AllianceSpecial.getButtonHeight() / 2
         ) {
            @Override
            public void actionElement() {
            }
         }
      );
      int innerWidth2 = (innerWidth - CFG.PADDING) / 2;
      int statH2 = (ButtonRuler_Diplomacy.getButtonHeight() - topTitleH - CFG.PADDING * 2) / 2;
      menuElements.add(
         new TextIcon2(
            "" + CFG.getNumberWithSpaces("" + Game.alliancesSpecial.get(allianceID).getNumOfCivilizations()),
            this.getCivilizationsIMG(),
            paddingLeft + innerWidth + CFG.PADDING,
            statsY2,
            innerWidth2,
            statH2
         ) {
            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(
                  new MenuElement_HoverElement_Type_FlagCiv_Title_AllianceSpecial(
                     InGame_AllianceSpecial.allianceID,
                     Game.lang.get("Civilizations") + ": " + Game.alliancesSpecial.get(InGame_AllianceSpecial.allianceID).getNumOfCivilizations()
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     Game.lang.get("Civilizations") + ": ",
                     CFG.getNumberWithSpaces("" + Game.alliancesSpecial.get(InGame_AllianceSpecial.allianceID).getNumOfCivilizations()),
                     InGame_AllianceSpecial.this.getCivilizationsIMG(),
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
      menuElements.add(
         new TextIcon2(
            "" + CFG.getNumberWithSpaces("" + Game.alliancesSpecial.get(allianceID).getProvinces()),
            Images.provinces,
            paddingLeft + innerWidth + CFG.PADDING * 2 + innerWidth2,
            statsY2,
            innerWidth2,
            statH2
         ) {
            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(
                  new MenuElement_HoverElement_Type_FlagCiv_Title_AllianceSpecial(
                     InGame_AllianceSpecial.allianceID,
                     Game.lang.get("Civilizations") + ": " + Game.alliancesSpecial.get(InGame_AllianceSpecial.allianceID).getNumOfCivilizations()
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     Game.lang.get("Provinces") + ": ",
                     CFG.getNumberWithSpaces("" + Game.alliancesSpecial.get(InGame_AllianceSpecial.allianceID).getProvinces()),
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
      statsY2 += statH2 + CFG.PADDING;
      menuElements.add(
         new TextIcon2(
            "" + CFG.getShortNumber(Game.alliancesSpecial.get(allianceID).getPopulation()),
            Images.population,
            paddingLeft + innerWidth + CFG.PADDING,
            statsY2,
            innerWidth2,
            statH2
         ) {
            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(
                  new MenuElement_HoverElement_Type_FlagCiv_Title_AllianceSpecial(
                     InGame_AllianceSpecial.allianceID,
                     Game.lang.get("Civilizations") + ": " + Game.alliancesSpecial.get(InGame_AllianceSpecial.allianceID).getNumOfCivilizations()
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     Game.lang.get("Population") + ": ",
                     CFG.getNumberWithSpaces("" + Game.alliancesSpecial.get(InGame_AllianceSpecial.allianceID).getPopulation()),
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

            @Override
            protected Color getColor(boolean isActive) {
               return Colors.getColorPopulation(isActive, this.getIsHovered());
            }
         }
      );
      menuElements.add(
         new TextIcon2(
            "" + CFG.getShortNumber(Game.alliancesSpecial.get(allianceID).getEconomy()),
            Game_Calendar.IMG_ECONOMY,
            paddingLeft + innerWidth + CFG.PADDING * 2 + innerWidth2,
            statsY2,
            innerWidth2,
            statH2
         ) {
            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(
                  new MenuElement_HoverElement_Type_FlagCiv_Title_AllianceSpecial(
                     InGame_AllianceSpecial.allianceID,
                     Game.lang.get("Civilizations") + ": " + Game.alliancesSpecial.get(InGame_AllianceSpecial.allianceID).getNumOfCivilizations()
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     Game.lang.get("Economy") + ": ",
                     CFG.getNumberWithSpaces("" + Game.alliancesSpecial.get(InGame_AllianceSpecial.allianceID).getEconomy()),
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
      buttonY -= CFG.PADDING;
      menuElements.add(new Text_StaticBG_ID_FlagCiv_SpecialEmpty(paddingLeft2, buttonY, menuWidth - paddingLeft2 * 2, topHeight));
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      if (Game.alliancesSpecial.get(allianceID).typeOfAlliance == 0) {
         int maxIconW = ImageManager.getImage(Images.gold).getWidth();
         int statW = (menuWidth - paddingLeft * 2 - CFG.PADDING * 2) / 3;
         int statH = CFG.TEXT_HEIGHT + CFG.PADDING * 4;
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               "", "+" + CFG.getPrecision2(HREManager.getIncome_Emperor(allianceID), 100), Images.gold, paddingLeft, buttonY, statW, statH, maxIconW
            ) {
               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(
                     new MenuElement_HoverElement_Type_FlagCiv_Title(
                        Game.alliancesSpecial.get(InGame_AllianceSpecial.allianceID).iLeaderCivID,
                        Game.lang.get(Game.alliancesSpecial.get(InGame_AllianceSpecial.allianceID).Name_Leader)
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        Game.lang.get("MonthlyIncome") + ": ",
                        "+" + CFG.getPrecision2(HREManager.getIncome_Emperor(InGame_AllianceSpecial.allianceID), 100),
                        Images.gold,
                        CFG.FONT_REGULAR_SMALL,
                        CFG.FONT_BOLD_SMALL,
                        Colors.HOVER_LEFT,
                        Colors.HOVER_POSITIVE
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements);
               }
            }
         );
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               "",
               "+" + CFG.getPrecision2((float)HREManager.getManpower_Emperor(allianceID), 1),
               Game_Calendar.IMG_MANPOWER,
               paddingLeft + statW + CFG.PADDING,
               buttonY,
               statW,
               statH,
               maxIconW
            ) {
               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(
                     new MenuElement_HoverElement_Type_FlagCiv_Title(
                        Game.alliancesSpecial.get(InGame_AllianceSpecial.allianceID).iLeaderCivID,
                        Game.lang.get(Game.alliancesSpecial.get(InGame_AllianceSpecial.allianceID).Name_Leader)
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        Game.lang.get("MaximumManpower") + ": ",
                        "+" + CFG.getPrecision2((float)HREManager.getManpower_Emperor(InGame_AllianceSpecial.allianceID), 1),
                        Game_Calendar.IMG_MANPOWER,
                        CFG.FONT_REGULAR_SMALL,
                        CFG.FONT_BOLD_SMALL,
                        Colors.HOVER_LEFT,
                        Colors.HOVER_POSITIVE
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements);
               }
            }
         );
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               "",
               "+" + CFG.getPrecision2(GameValues.hre.HRE_EMPEROR_REGIMENTS, 1),
               Images.regimentsLimit,
               paddingLeft + statW * 2 + CFG.PADDING * 2,
               buttonY,
               statW,
               statH,
               maxIconW
            ) {
               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(
                     new MenuElement_HoverElement_Type_FlagCiv_Title(
                        Game.alliancesSpecial.get(InGame_AllianceSpecial.allianceID).iLeaderCivID,
                        Game.lang.get(Game.alliancesSpecial.get(InGame_AllianceSpecial.allianceID).Name_Leader)
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        Game.lang.get("RegimentsLimit") + ": ",
                        "+" + CFG.getPrecision2(GameValues.hre.HRE_EMPEROR_REGIMENTS, 1),
                        Images.regimentsLimit,
                        CFG.FONT_REGULAR_SMALL,
                        CFG.FONT_BOLD_SMALL,
                        Colors.HOVER_LEFT,
                        Colors.HOVER_POSITIVE
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements);
               }
            }
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      }

      if (Game.alliancesSpecial.get(allianceID).typeOfAlliance == 1) {
         menuElements.add(
            new TextIcon2_Horizontal(
               Game.lang.get("DefensiveAlliance"),
               Images.defensivePact,
               paddingLeft,
               buttonY,
               menuWidth - paddingLeft * 2,
               topTitleH,
               ImageManager.getImage(Images.defensivePact).getWidth() + CFG.PADDING * 2
            ) {
               @Override
               public void actionElement() {
               }
            }
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      }

      int buttonYBefore = buttonY;
      buttonY += CFG.PADDING;
      menuElements.add(
         new Text_StaticBG_TitleSpecial_ID(
            Game.lang.get(Game.alliancesSpecial.get(allianceID).Name_FirstTier),
            CFG.FONT_REGULAR,
            -1,
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            topTitleH,
            allianceID
         ) {
            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     this.getText() + ": ",
                     CFG.getNumberWithSpaces("" + Game.alliancesSpecial.get(InGame_AllianceSpecial.allianceID).getNumOfCivilizations_FirstTier()),
                     InGame_AllianceSpecial.this.getCivilizationsIMG(),
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
            public void actionElement() {
               InGame_AllianceSpecial.showFirstTier = !InGame_AllianceSpecial.showFirstTier;
               Game.menuManager.rebuildInGame_AllianceSpecial_SavePos(InGame_AllianceSpecial.allianceID);
               InGame_AllianceSpecial.lTime = 0L;
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      int buttonX = paddingLeft;
      if (showFirstTier) {
         for (int i = 0; i < Game.alliancesSpecial.get(allianceID).firstTier.size(); i++) {
            if (buttonX + ButtonFlag_Diplomacy.getButtonWidth() > menuWidth - paddingLeft) {
               buttonY += ButtonFlag_Diplomacy.getButtonHeight() + CFG.PADDING;
               buttonX = paddingLeft;
            }

            if (Game.getCiv(Game.alliancesSpecial.get(allianceID).firstTier.get(i)).getNumOfProvinces() > 0) {
               menuElements.add(
                  new ButtonFlag_Diplomacy(Game.alliancesSpecial.get(allianceID).firstTier.get(i), buttonX, buttonY, true) {
                     @Override
                     public void actionElement() {
                        if (this.iCivID >= 0 && this.iCivID != InGame_Civ.iActiveCivID) {
                           Game.menuManager.setVisibleInGame_PopUp(false);
                           InGame_Civ.iRebuildToCivID = this.iCivID;
                           Game.menuManager.rebuildInGame_Civ();
                           InGame_Civ.lTime = 0L;
                           if (Game.menuManager.getVisibleInGame_CivBonuses()
                              && InGame_Civ.iActiveCivID != 0
                              && InGame_CivBonuses.iCivID != InGame_Civ.iActiveCivID) {
                              InGame_CivBonuses.iCivID = InGame_Civ.iActiveCivID;
                              Game.menuManager.rebuildInGame_CivBonuses();
                              Game.menuManager.setVisibleInGame_CivBonuses(true);
                              InGame_CivBonuses.lTime = 0L;
                           }
                        } else if (Game.getCiv(this.iCivID).getCapitalProvinceID() >= 0
                           && Game.getProvince(Game.getCiv(this.iCivID).getCapitalProvinceID()).getCivID() == this.iCivID) {
                           Game.mapCoords.centerToProvinceID(Game.getCiv(this.iCivID).getCapitalProvinceID());
                           Game.setActiveProvinceID(Game.getCiv(this.iCivID).getCapitalProvinceID());
                           ProvinceBorderManager.action.setProvinceID(Game.getCiv(this.iCivID).getCapitalProvinceID());
                           Game.menuManager.setVisibleInGame_PopUp(false);
                        }
                     }
                  }
               );
               buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
            }
         }

         for (int i = menuElements.size() - 1; i >= 0; i--) {
            buttonY = Math.max(buttonY, menuElements.get(i).getPosY() + menuElements.get(i).getHeight() + CFG.PADDING);
         }
      }

      menuElements.add(new Text_StaticBG_ID_FlagCiv_SpecialEmpty(paddingLeft2, buttonYBefore, menuWidth - paddingLeft2 * 2, buttonY - buttonYBefore));
      buttonY += CFG.PADDING;
      buttonY += CFG.PADDING;
      menuElements.add(
         new Text_StaticBG_TitleSpecial_ID(
            Game.lang.get(Game.alliancesSpecial.get(allianceID).Name_Rest),
            CFG.FONT_REGULAR,
            -1,
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            topTitleH,
            allianceID
         ) {
            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     this.getText() + ": ",
                     CFG.getNumberWithSpaces("" + Game.alliancesSpecial.get(InGame_AllianceSpecial.allianceID).getNumOfCivilizations_SecondTier()),
                     InGame_AllianceSpecial.this.getCivilizationsIMG(),
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
            public void actionElement() {
               InGame_AllianceSpecial.showSecondTier = !InGame_AllianceSpecial.showSecondTier;
               Game.menuManager.rebuildInGame_AllianceSpecial_SavePos(InGame_AllianceSpecial.allianceID);
               InGame_AllianceSpecial.lTime = 0L;
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      buttonX = paddingLeft;
      if (showSecondTier) {
         for (int i = 0; i < Game.alliancesSpecial.get(allianceID).secondTier.size(); i++) {
            if (buttonX + ButtonFlagRect.getButtonWidth() > menuWidth - paddingLeft) {
               buttonY += ButtonFlagRect.getButtonHeight() + CFG.PADDING / 2;
               buttonX = paddingLeft;
            }

            if (Game.getCiv(Game.alliancesSpecial.get(allianceID).secondTier.get(i)).getNumOfProvinces() > 0) {
               menuElements.add(
                  new ButtonFlagRect(Game.alliancesSpecial.get(allianceID).secondTier.get(i), buttonX, buttonY, true) {
                     @Override
                     public void actionElement() {
                        if (this.iCivID >= 0 && this.iCivID != InGame_Civ.iActiveCivID) {
                           Game.menuManager.setVisibleInGame_PopUp(false);
                           InGame_Civ.iRebuildToCivID = this.iCivID;
                           Game.menuManager.rebuildInGame_Civ();
                           InGame_Civ.lTime = 0L;
                           if (Game.menuManager.getVisibleInGame_CivBonuses()
                              && InGame_Civ.iActiveCivID != 0
                              && InGame_CivBonuses.iCivID != InGame_Civ.iActiveCivID) {
                              InGame_CivBonuses.iCivID = InGame_Civ.iActiveCivID;
                              Game.menuManager.rebuildInGame_CivBonuses();
                              Game.menuManager.setVisibleInGame_CivBonuses(true);
                              InGame_CivBonuses.lTime = 0L;
                           }
                        } else if (Game.getCiv(this.iCivID).getCapitalProvinceID() >= 0
                           && Game.getProvince(Game.getCiv(this.iCivID).getCapitalProvinceID()).getCivID() == this.iCivID) {
                           Game.mapCoords.centerToProvinceID(Game.getCiv(this.iCivID).getCapitalProvinceID());
                           Game.setActiveProvinceID(Game.getCiv(this.iCivID).getCapitalProvinceID());
                           ProvinceBorderManager.action.setProvinceID(Game.getCiv(this.iCivID).getCapitalProvinceID());
                           Game.menuManager.setVisibleInGame_PopUp(false);
                        }
                     }
                  }
               );
               buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
            }
         }

         for (int i = menuElements.size() - 1; i >= 0; i--) {
            buttonY = Math.max(buttonY, menuElements.get(i).getPosY() + menuElements.get(i).getHeight() + CFG.PADDING);
         }
      }

      menuElements.add(new Text_StaticBG_ID_FlagCiv_SpecialEmpty(paddingLeft2, buttonY, menuWidth - paddingLeft2 * 2, buttonY - buttonY));
      buttonY += CFG.PADDING;
      buttonX = paddingLeft;
      if (Game.alliancesSpecial.get(allianceID).isInAlliance(Game.player.iCivID)) {
         menuElements.add(
            new ButtonGame_Image(
               Game.lang.get("LeaveAlliance"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT4, true, Images.x
            ) {
               @Override
               public void actionElement() {
                  if (InGame_AllianceSpecial.this.leaveAllianceConfirm) {
                     Game.alliancesSpecial.get(InGame_AllianceSpecial.allianceID).removeCiv(Game.player.iCivID);
                     Game.getCiv(Game.player.iCivID).removeInAllianceSpecial(InGame_AllianceSpecial.allianceID);
                     InGame_Info.iCivID = Game.player.iCivID;
                     InGame_Info.iCivID2 = Game.player.iCivID;
                     Game.menuManager
                        .rebuildInGame_Info(
                           Game.lang.get(Game.alliancesSpecial.get(InGame_AllianceSpecial.allianceID).Name_Alliance), Game.lang.get("LeaveAlliance")
                        );
                     InGame_Info.imgID = Images.infoDiplomacy;
                     InGame_AllianceSpecial.this.setVisible(false);
                  } else {
                     InGame_AllianceSpecial.this.leaveAllianceConfirm = true;
                     this.setText(Game.lang.get("Confirm") + ": " + Game.lang.get("LeaveAlliance"));
                  }
               }

               @Override
               public boolean getIsHovered() {
                  return super.getIsHovered() || InGame_AllianceSpecial.this.leaveAllianceConfirm;
               }

               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(
                     new MenuElement_HoverElement_Type_TextTitle_BG(
                        Game.lang.get("LeaveAlliance") + ": " + Game.lang.get(Game.alliancesSpecial.get(InGame_AllianceSpecial.allianceID).Name_Alliance),
                        CFG.FONT_BOLD,
                        Colors.HOVER_GOLD
                     )
                  );
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.x, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements, true);
               }
            }
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      }

      if (Game.alliancesSpecial.get(allianceID).typeOfAlliance == 0) {
         menuElements.add(
            new Text_Title_v2_TextLR(
               Game.lang.get("NextElections"),
               CFG.BUTTON_WIDTH / 4,
               Images.boxTitleBORDERWIDTH,
               buttonY,
               menuWidth - Images.boxTitleBORDERWIDTH * 2,
               CFG.TEXT_HEIGHT + CFG.PADDING * 4,
               ""
            ) {
               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(
                     new MenuElement_HoverElement_Type_TextTitle(
                        Game.lang.get(Game.alliancesSpecial.get(InGame_AllianceSpecial.allianceID).Name_Alliance), CFG.FONT_BOLD, Colors.HOVER_GOLD
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(new MenuElement_HoverElement_Type_Line());
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Text_Desc(
                        Game.lang.get("CivilizationWithTheHighestRankWillWinNextElection"), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements);
               }
            }
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         List<Integer> tCivs = new ArrayList<>();
         tCivs.add(Game.alliancesSpecial.get(allianceID).iLeaderCivID);

         for (int i = Game.alliancesSpecial.get(allianceID).firstTier.size() - 1; i >= 0; i--) {
            if (!tCivs.contains(Game.alliancesSpecial.get(allianceID).firstTier.get(i))
               && Game.getCiv(Game.alliancesSpecial.get(allianceID).firstTier.get(i)).getNumOfProvinces() > 0
               && Game.getCiv(Game.alliancesSpecial.get(allianceID).firstTier.get(i)).iCivRankPosition >= 0) {
               tCivs.add(Game.alliancesSpecial.get(allianceID).firstTier.get(i));
            }
         }

         for (int ix = Game.alliancesSpecial.get(allianceID).secondTier.size() - 1; ix >= 0; ix--) {
            if (!tCivs.contains(Game.alliancesSpecial.get(allianceID).secondTier.get(ix))
               && Game.getCiv(Game.alliancesSpecial.get(allianceID).secondTier.get(ix)).getNumOfProvinces() > 0
               && Game.getCiv(Game.alliancesSpecial.get(allianceID).secondTier.get(ix)).iCivRankPosition >= 0) {
               tCivs.add(Game.alliancesSpecial.get(allianceID).secondTier.get(ix));
            }
         }

         int buttonH = CFG.isDesktop() ? CFG.BUTTON_HEIGHT3 : CFG.BUTTON_HEIGHT2;
         int r0W0 = (int)((menuWidth - paddingLeft * 2 - CFG.PADDING * 2) * 0.2F);
         int r0W = (int)((menuWidth - paddingLeft * 2 - CFG.PADDING * 2) * 0.6F);
         int r1W = (int)((menuWidth - paddingLeft * 2 - CFG.PADDING * 2) * 0.2F);

         for (int o = 0; o < GameValues.hre.HRE_VIEW_ELECTIONS_TOP_CIVS && o < tCivs.size(); o++) {
            int toAddID = 0;

            for (int ixx = tCivs.size() - 1; ixx > 0; ixx--) {
               if (Game.getCiv(tCivs.get(ixx)).iCivRankPosition < Game.getCiv(tCivs.get(toAddID)).iCivRankPosition) {
                  toAddID = ixx;
               }
            }

            menuElements.add(
               new ButtonStatsRectIMG_Rank(
                  "" + Game.getCiv(tCivs.get(toAddID)).iCivRankPosition,
                  CivilizationRanking.getCivilizationRanking_IMG_STAR_CIVID(tCivs.get(toAddID)),
                  buttonX,
                  buttonY,
                  r0W0,
                  buttonH,
                  ImageManager.getImage(Images.rankGold).getWidth()
               ) {}
            );
            buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
            menuElements.add(
               new Text_StaticBG_ID_FlagCiv(
                  "" + Game.getCiv(tCivs.get(toAddID)).getCivName(),
                  CFG.FONT_REGULAR_SMALL,
                  CFG.PADDING * 2,
                  buttonX,
                  buttonY,
                  r0W,
                  buttonH,
                  tCivs.get(toAddID)
               ) {
                  @Override
                  public void actionElement() {
                     if (Game.getCiv(this.getCurrent()).getCapitalProvinceID() >= 0
                        && Game.getProvince(Game.getCiv(this.getCurrent()).getCapitalProvinceID()).getCivID() == this.getCurrent()) {
                        if (Game.iActiveProvince >= 0 && Game.getProvince(Game.iActiveProvince).getCivID() == this.getCurrent()) {
                           Game.menuManager.setVisibleInGame_PopUp(false);
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
               }
            );
            buttonX = paddingLeft;
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
            tCivs.remove(toAddID);
         }

         tCivs.clear();
         menuElements.add(
            new Text_Title_v2_TextLR(
               Game.lang.get("Reforms"),
               CFG.BUTTON_WIDTH / 4,
               Images.boxTitleBORDERWIDTH,
               buttonY,
               menuWidth - Images.boxTitleBORDERWIDTH * 2,
               CFG.TEXT_HEIGHT + CFG.PADDING * 4,
               Game.alliancesSpecial.get(allianceID).iReformsPassed + " / " + HREManager.numOfReforms
            )
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;

         for (int ixxx = 0; ixxx < HREManager.numOfReforms; ixxx++) {
            if (ixxx == Game.alliancesSpecial.get(allianceID).iReformsPassed) {
               menuElements.add(
                  new Button_HRE_Reform(
                     Game.lang.get("Reform" + ixxx),
                     Game.lang.get("Reform" + ixxx + ".d"),
                     paddingLeft + CFG.PADDING,
                     buttonY,
                     menuWidth - paddingLeft * 2 - CFG.PADDING * 2,
                     ixxx,
                     ixxx
                  ) {
                     @Override
                     public void actionElement() {
                        if (Game.menuManager.getVisibleInGame_PopUp()
                           && MenuManager.IN_GAME_POP_UP_MENU_ID == 36
                           && InGame_AllianceSpecialReformHRE.allianceID == InGame_AllianceSpecial.allianceID
                           && InGame_AllianceSpecialReformHRE.reformID == this.getCurrent()) {
                           Game.menuManager.setVisibleInGame_PopUp(false);
                        } else {
                           Game.menuManager.rebuildInGame_AllianceSpecialReform(InGame_AllianceSpecial.allianceID, this.getCurrent());
                        }
                     }

                     @Override
                     public void buildElementHover() {
                        this.menuElementHover = HREManager.getHoverReform(InGame_AllianceSpecial.allianceID, this.reformID);
                     }
                  }
               );
            } else if (ixxx < Game.alliancesSpecial.get(allianceID).iReformsPassed) {
               menuElements.add(
                  new Button_HRE_Reform_Green(
                     Game.lang.get("Reform" + ixxx),
                     Game.lang.get("Reform" + ixxx + ".d"),
                     paddingLeft + CFG.PADDING,
                     buttonY,
                     menuWidth - paddingLeft * 2 - CFG.PADDING * 2,
                     ixxx,
                     ixxx
                  ) {
                     @Override
                     public void actionElement() {
                     }

                     @Override
                     public void buildElementHover() {
                        this.menuElementHover = HREManager.getHoverReform(InGame_AllianceSpecial.allianceID, this.reformID);
                     }
                  }
               );
            } else {
               menuElements.add(
                  new Button_HRE_Reform_Red(
                     Game.lang.get("Reform" + ixxx),
                     Game.lang.get("Reform" + ixxx + ".d"),
                     paddingLeft + CFG.PADDING,
                     buttonY,
                     menuWidth - paddingLeft * 2 - CFG.PADDING * 2,
                     ixxx,
                     ixxx
                  ) {
                     @Override
                     public void actionElement() {
                        if (Game.menuManager.getVisibleInGame_PopUp()
                           && MenuManager.IN_GAME_POP_UP_MENU_ID == 36
                           && InGame_AllianceSpecialReformHRE.allianceID == InGame_AllianceSpecial.allianceID
                           && InGame_AllianceSpecialReformHRE.reformID == this.getCurrent()) {
                           Game.menuManager.setVisibleInGame_PopUp(false);
                        } else {
                           Game.menuManager.rebuildInGame_AllianceSpecialReform(InGame_AllianceSpecial.allianceID, this.getCurrent());
                        }
                     }

                     @Override
                     public void buildElementHover() {
                        this.menuElementHover = HREManager.getHoverReform(InGame_AllianceSpecial.allianceID, this.reformID);
                     }
                  }
               );
            }

            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }
      }

      int menuHeight = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - CFG.PADDING * 3);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, menuHeight)));
      this.initMenu(new MenuTitleIMG(Game.lang.get(Game.alliancesSpecial.get(allianceID).Name_Alliance), true, false, Images.title500) {
         @Override
         public long getTime() {
            return InGame_AllianceSpecial.lTime2;
         }
      }, menuX, menuY, menuWidth, menuHeight, menuElements, true, true);
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
      lTime = CFG.currentTimeMillis;
      lTime2 = lTime;
   }

   public int getCivilizationsIMG() {
      return Game.alliancesSpecial.get(allianceID).typeOfAlliance == 0 ? Images.hre : Images.council;
   }
}
