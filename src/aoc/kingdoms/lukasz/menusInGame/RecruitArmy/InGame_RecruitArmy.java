package aoc.kingdoms.lukasz.menusInGame.RecruitArmy;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Ages;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.SoundsManager;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.jakowski.Renderer.SparksAnimation;
import aoc.kingdoms.lukasz.map.allianceHRE.HREManager;
import aoc.kingdoms.lukasz.map.army.ArmyManager;
import aoc.kingdoms.lukasz.map.army.ArmyRecruit;
import aoc.kingdoms.lukasz.map.battles.BattleManager;
import aoc.kingdoms.lukasz.map.civilization.Civilization;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.MenuManager;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG_DoubleText;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Active_Click;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Active_Click_Sparks;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Bonuses_Style;
import aoc.kingdoms.lukasz.menu_element.button.ButtonUnit3_3;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_BattleRegiment;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_BattleRegimentEmpty;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus_Perc;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Empty;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Image;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageFull;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG_Center;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG_Clear;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text_Desc;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Desc;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Title;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_TitleBlue;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Title_BattleLine;
import aoc.kingdoms.lukasz.menusInGame.InGame_Generals;
import aoc.kingdoms.lukasz.menusInGame.Battle.InGame_Battlefield;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_CourtOptions2;
import aoc.kingdoms.lukasz.menusInGame.Info.InGame_Info;
import aoc.kingdoms.lukasz.menusInGame.Technology.InGame_TechnologyTree;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_RecruitArmy extends Menu {
   public static SparksAnimation sparksAnimation = new SparksAnimation();
   public static Color sparksColor = new Color(1.0F, 1.0F, 1.0F, 0.25F);
   public static int HOVER_POSX = 0;
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static String key = "";

   public InGame_RecruitArmy() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING + Images.boxTitleBORDERWIDTH;
      int titleHeight = ImageManager.getImage(Images.title2).getHeight();
      int menuWidth = ImageManager.getImage(Images.insideTop).getWidth();
      int menuX = InGame_CourtOptions2.getOtherMenuPosX_2();
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title1Red).getHeight();
      int buttonYPadding = CFG.PADDING * 2;
      int buttonY = CFG.PADDING;
      int buttonX = paddingLeft;
      int textTitleH = Math.max(ImageManager.getImage(Images.battleArmy0).getHeight() * 2 + CFG.PADDING * 3, CFG.TEXT_HEIGHT + CFG.PADDING * 6);
      int buttonH = CFG.isDesktop() ? CFG.BUTTON_HEIGHT3 : CFG.BUTTON_HEIGHT2;
      int upgrades = ArmyManager.getRegimentsAvailableToUpgrade(Game.player.iCivID);
      if (upgrades > 0) {
         buttonX = paddingLeft;
         menuElements.add(
            new ButtonStatsRectIMG_Active_Click_Sparks(
               Game.lang.get("UpgradeRegiments"),
               Game_Calendar.IMG_MANPOWER_UP,
               paddingLeft,
               buttonY,
               menuWidth - paddingLeft * 2,
               buttonH,
               ImageManager.getImage(Game_Calendar.IMG_MANPOWER).getWidth(),
               0,
               true
            ) {
               @Override
               public void actionElement() {
                  if (Game.menuManager.getVisibleInGame_Armies()) {
                     Game.menuManager.setVisibleInGame_Armies(false);
                  } else {
                     Game.menuManager.rebuildInGame_Armies(false, false);
                     Game.menuManager.setVisibleInGame_Armies(true);
                     Game.menuManager.setVisibleInGame_RecruitArmy(false);
                  }
               }

               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("UpgradeRegiments"), Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Game_Calendar.IMG_MANPOWER_UP, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  int upgradesx = ArmyManager.getRegimentsAvailableToUpgrade(Game.player.iCivID);
                  nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("AvailableUpgrades") + ": ", CFG.FONT_REGULAR_SMALL));
                  nData.add(new MenuElement_HoverElement_Type_Text(CFG.getNumberWithSpaces("" + upgradesx), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_Image(Images.upgrade, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements);
               }
            }
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      }

      int leftW = (menuWidth - paddingLeft * 2 - CFG.PADDING) * 3 / 5;
      int rightW = (menuWidth - paddingLeft * 2 - CFG.PADDING) * 2 / 5;
      int maxIconWidth = ImageManager.getImage(Game_Calendar.IMG_MANPOWER).getWidth();
      menuElements.add(
         new ButtonStatsRectIMG_Active_Click_Sparks(
            Game.lang.get("CreateNewArmy"), Game_Calendar.IMG_MANPOWER, buttonX + CFG.PADDING + rightW, buttonY, leftW, buttonH, maxIconWidth, 0, true
         ) {
            @Override
            public void actionElement() {
               InGame_RecruitArmy.actionCreateNewArmy();
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("CreateNewArmy"), Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Game_Calendar.IMG_MANPOWER, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text_Desc(Game.lang.get("CreateArmyT"), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT2));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               if (CFG.isDesktop()) {
                  nData.add(new MenuElement_HoverElement_Type_Line());
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Shortcut") + ": ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_LEFT));
                  nData.add(new MenuElement_HoverElement_Type_Text("V", CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_Image(Game_Calendar.IMG_MANPOWER, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
               }

               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      menuElements.add(
         new ButtonStatsRectIMG_Active_Click(
            ""
               + CFG.getShortNumber(Game.getCiv(Game.player.iCivID).getArmySize())
               + " / "
               + CFG.getShortNumber(Game.getCiv(Game.player.iCivID).iRegimentsLimit * Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).REGIMENT_SIZE),
            Game_Calendar.IMG_MANPOWER,
            buttonX,
            buttonY,
            rightW,
            buttonH,
            maxIconWidth,
            0
         ) {
            @Override
            public void actionElement() {
               InGame_RecruitArmy.actionArmies();
            }

            @Override
            protected Color getColor(boolean isActive) {
               return Game.getCiv(Game.player.iCivID).getArmyRegimentSize() + Game.getCiv(Game.player.iCivID).iArmyRecruitSize_Total
                     > Game.getCiv(Game.player.iCivID).iRegimentsLimit
                  ? Colors.COLOR_TEXT_MODIFIER_NEGATIVE
                  : super.getColor(isActive);
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Armies"), Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Game_Calendar.IMG_MANPOWER, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("ArmySize") + ": ", CFG.FONT_REGULAR_SMALL));
               nData.add(
                  new MenuElement_HoverElement_Type_Text(
                     CFG.getNumberWithSpaces("" + Game.getCiv(Game.player.iCivID).getArmySize()), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD
                  )
               );
               nData.add(new MenuElement_HoverElement_Type_Image(Game_Calendar.IMG_MANPOWER, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("RegimentsLimit") + ": ", CFG.FONT_REGULAR_SMALL));
               nData.add(
                  new MenuElement_HoverElement_Type_Text(
                     "" + Game.getCiv(Game.player.iCivID).iRegimentsLimit,
                     CFG.FONT_BOLD_SMALL,
                     Game.getCiv(Game.player.iCivID).getArmyRegimentSize() + Game.getCiv(Game.player.iCivID).iArmyRecruitSize_Total
                           > Game.getCiv(Game.player.iCivID).iRegimentsLimit
                        ? Colors.COLOR_TEXT_MODIFIER_NEGATIVE
                        : Colors.HOVER_GOLD
                  )
               );
               nData.add(new MenuElement_HoverElement_Type_Image(Images.regimentsLimit, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Line());
               nElements.add(new MenuElement_HoverElement(nData, false));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("BaseValue") + ": ", CFG.FONT_REGULAR_SMALL));
               nData.add(
                  new MenuElement_HoverElement_Type_Text(
                     "" + Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).REGIMENTS_LIMIT_BASE_VALUE,
                     CFG.FONT_BOLD_SMALL,
                     Colors.COLOR_TEXT_MODIFIER_POSITIVE
                  )
               );
               nData.add(new MenuElement_HoverElement_Type_Image(Images.regimentsLimit, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData, false));
               nData.clear();
               if (Game.getCiv(Game.player.iCivID).civBonuses.RegimentsLimit > 0) {
                  nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Bonuses") + ": ", CFG.FONT_REGULAR_SMALL));
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        "" + Game.getCiv(Game.player.iCivID).civBonuses.RegimentsLimit, CFG.FONT_BOLD_SMALL, Colors.COLOR_TEXT_MODIFIER_POSITIVE
                     )
                  );
                  nData.add(new MenuElement_HoverElement_Type_Image(Images.regimentsLimit, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData, false));
                  nData.clear();
               }

               if (Game.getCiv(Game.player.iCivID).diplomacy.iVassalsSize > 0) {
                  nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Vassals") + ": ", CFG.FONT_REGULAR_SMALL));
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        "" + GameValues.army.REGIMENTS_LIMIT_PER_VASSAL * Game.getCiv(Game.player.iCivID).diplomacy.iVassalsSize,
                        CFG.FONT_BOLD_SMALL,
                        Colors.COLOR_TEXT_MODIFIER_POSITIVE
                     )
                  );
                  nData.add(new MenuElement_HoverElement_Type_Image(Images.vassal, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData, false));
                  nData.clear();
               }

               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("LocalManpower") + ": ", CFG.FONT_REGULAR_SMALL));
               nData.add(
                  new MenuElement_HoverElement_Type_Text(
                     "" + Game.getCiv(Game.player.iCivID).getRegimentsLimit_Manpower(), CFG.FONT_BOLD_SMALL, Colors.COLOR_TEXT_MODIFIER_POSITIVE
                  )
               );
               nData.add(new MenuElement_HoverElement_Type_Image(Game_Calendar.IMG_MANPOWER, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData, false));
               nData.clear();
               float value = Game.getCiv(Game.player.iCivID).getRegimentsLimit_FromAllianceSpecial();
               if (value > 0.0F) {
                  nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Alliances") + ": ", CFG.FONT_REGULAR_SMALL));
                  nData.add(new MenuElement_HoverElement_Type_Text("" + CFG.getPrecision2(value, 1), CFG.FONT_BOLD_SMALL, Colors.COLOR_TEXT_MODIFIER_POSITIVE));
                  nData.add(new MenuElement_HoverElement_Type_Image(Images.alliance, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData, false));
                  nData.clear();
               }

               if (Game.getCiv(Game.player.iCivID).getPuppetOfCivID() != Game.player.iCivID) {
                  nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Vassal") + ": ", CFG.FONT_REGULAR_SMALL));
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        "" + CFG.getPrecision2(GameValues.army.REGIMENTS_LIMIT_VASSAL * 100.0F, 1) + "%", CFG.FONT_BOLD_SMALL, Colors.HOVER_NEGATIVE
                     )
                  );
                  nData.add(new MenuElement_HoverElement_Type_Image(Images.vassal, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData, false));
                  nData.clear();
               }

               if (Game.getCiv(Game.player.iCivID).getArmyRegimentSize() + Game.getCiv(Game.player.iCivID).iArmyRecruitSize_Total
                  >= Game.getCiv(Game.player.iCivID).iRegimentsLimit) {
                  nData.add(new MenuElement_HoverElement_Type_Line());
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Text_Desc(
                        Game.lang.get("WhenTheMaximumRegimentLimitIsReachedArmyRecruitmentCostIncreasesBy")
                           + ": "
                           + CFG.getPrecision2(GameValues.army.REGIMENTS_LIMIT_RECRUIT_COST_OVER * 100.0F, 10)
                           + "%",
                        CFG.FONT_REGULAR_SMALL,
                        Colors.HOVER_RIGHT
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(new MenuElement_HoverElement_Type_Line());
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
               }

               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("BattleWidth") + ": ", CFG.FONT_REGULAR_SMALL));
               nData.add(new MenuElement_HoverElement_Type_Text("" + BattleManager.getBattleWidth(Game.player.iCivID), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_Image(Images.battleWidth, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Discipline") + ": ", CFG.FONT_REGULAR_SMALL));
               nData.add(
                  new MenuElement_HoverElement_Type_Text(
                     "" + CFG.getPrecision2(Game.getCiv(Game.player.iCivID).civBonuses.Discipline * 100.0F, 10) + "%", CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD
                  )
               );
               nData.add(new MenuElement_HoverElement_Type_Image(Images.discipline, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Line());
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(
                  new MenuElement_HoverElement_Type_Text_Desc(
                     Game.lang.get("ManpowerRecoveryFromADisbandedArmy")
                        + ": "
                        + CFG.getPrecision2(Game.getManpowerRecoveryFromADisbandedArmy(Game.player.iCivID) * 100.0F, 10)
                        + "%",
                     CFG.FONT_REGULAR_SMALL,
                     Colors.HOVER_RIGHT
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               if (CFG.isDesktop()) {
                  nData.add(new MenuElement_HoverElement_Type_Line());
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Shortcut") + ": ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_LEFT));
                  nData.add(new MenuElement_HoverElement_Type_Text("M", CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_Image(Game_Calendar.IMG_MANPOWER, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
               }

               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new ButtonStatsRectIMG_Active_Click(
            Game.lang.get("Mercenaries"), Images.mercenaries, buttonX + CFG.PADDING + rightW, buttonY, leftW, buttonH, maxIconWidth, 0, true
         ) {
            @Override
            public void actionElement() {
               InGame_RecruitArmy.actionMercenaries();
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("RecruitMercenaries"), Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.mercenaries, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               if (CFG.isDesktop()) {
                  nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Shortcut") + ": ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_LEFT));
                  nData.add(new MenuElement_HoverElement_Type_Text("C", CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_Image(Images.mercenaries, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
               }

               this.menuElementHover = new MenuElement_Hover(nElements, !CFG.isDesktop());
            }
         }
      );
      menuElements.add(
         new ButtonStatsRectIMG_Active_Click(
            "" + Game.getCiv(Game.player.iCivID).getTotalGenerals(), Images.general, buttonX, buttonY, rightW, buttonH, maxIconWidth, 0
         ) {
            @Override
            public int getSFX() {
               return SoundsManager.GENERALS_CLICK;
            }

            @Override
            public void actionElement() {
               InGame_Generals.backButton = true;
               InGame_RecruitArmy.actionGenerals();
               InGame_Generals.backButton = true;
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Generals"), Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.general, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Generals") + ": ", CFG.FONT_REGULAR_SMALL));
               nData.add(
                  new MenuElement_HoverElement_Type_Text("" + Game.getCiv(Game.player.iCivID).getTotalGenerals(), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD)
               );
               nData.add(new MenuElement_HoverElement_Type_Image(Images.general, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("UnassignedGenerals") + ": ", CFG.FONT_REGULAR_SMALL));
               nData.add(
                  new MenuElement_HoverElement_Type_Text(
                     "" + Game.getCiv(Game.player.iCivID).getGeneralsNotAssignedSize(), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD
                  )
               );
               nData.add(new MenuElement_HoverElement_Type_Image(Images.general, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Line());
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("MilitaryAcademyForGenerals"), CFG.FONT_BOLD_SMALL));
               nData.add(new MenuElement_HoverElement_Type_Image(Images.general, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Level") + ": ", CFG.FONT_REGULAR_SMALL));
               nData.add(
                  new MenuElement_HoverElement_Type_Text(
                     Game.getCiv(Game.player.iCivID).getMilitaryAcademyForGeneralsLevel()
                        + " / "
                        + Game.getMilitaryAcademyForGenerals_MaxLvl(Game.player.iCivID),
                     CFG.FONT_BOLD_SMALL,
                     Colors.HOVER_GOLD
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               if (CFG.isDesktop()) {
                  nData.add(new MenuElement_HoverElement_Type_Line());
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Shortcut") + ": ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_LEFT));
                  nData.add(new MenuElement_HoverElement_Type_Text("G", CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_Image(Images.general, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
               }

               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      List<MenuElement> tElements = getRecruitMenuElements(buttonY, menuWidth, paddingLeft, CFG.PADDING, textTitleH);
      int i = 0;

      for (int iSize = tElements.size(); i < iSize; i++) {
         menuElements.add(tElements.get(i));
         buttonY = Math.max(buttonY, tElements.get(i).getPosY() + tElements.get(i).getHeight() + CFG.PADDING);
      }

      menuElements.add(
         new Text_TitleBlue(
            Game.lang.get("Army"), CFG.BUTTON_WIDTH / 4, Images.boxTitleBORDERWIDTH, buttonY, menuWidth - Images.boxTitleBORDERWIDTH * 2, textTitleH
         ) {
            @Override
            public void drawLines(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("More"), CFG.FONT_BOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle(Game_Calendar.IMG_MANPOWER, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements) {
                  @Override
                  public int getMinPosX() {
                     return InGame_RecruitArmy.HOVER_POSX;
                  }
               };
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new ButtonStatsRectIMG_Bonuses_Style(
            Game.lang.get("MilitaryAcademy") + ": ",
            "" + Game.getCiv(Game.player.iCivID).getMilitaryAcademyLevel() + " / " + Game.getMilitaryAcademy_MaxLvl(Game.player.iCivID),
            Game_Calendar.IMG_MANPOWER,
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            buttonH,
            ImageManager.getImage(Images.battleWidth).getWidth()
         ) {
            @Override
            public Color getColorBonus() {
               return Colors.HOVER_GOLD;
            }

            @Override
            public void actionElement() {
               if (Game.getCiv(Game.player.iCivID).getMilitaryAcademyLevel() >= Game.getMilitaryAcademy_MaxLvl(Game.player.iCivID)) {
                  Game.menuManager
                     .addToastInsufficient(
                        Game.lang.get("MaximumLevel") + ": ",
                        "" + Game.getCiv(Game.player.iCivID).getMilitaryAcademyLevel() + " / " + Game.getMilitaryAcademy_MaxLvl(Game.player.iCivID),
                        Game_Calendar.IMG_MANPOWER
                     );
               } else if (Game.menuManager.getVisibleInGame_PopUp() && MenuManager.IN_GAME_POP_UP_MENU_ID == 2) {
                  Game.menuManager.setVisibleInGame_PopUp(false);
               } else {
                  Game.menuManager.rebuildInGame_UpgradeMilitaryAcademy();
               }
            }

            @Override
            public void buildElementHover() {
               this.menuElementHover = InGame_RecruitArmy.getHoverMilitaryAcademy();
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new ButtonStatsRectIMG_Bonuses_Style(
            Game.lang.get("BattleWidth") + ": ",
            "" + BattleManager.getBattleWidth(Game.player.iCivID) + " / " + GameValues.battle.BATTLE_MAX_BATTLE_WIDTH,
            Images.battleWidth,
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
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
               nData.add(
                  new MenuElement_HoverElement_Type_TextTitle(
                     "" + BattleManager.getBattleWidth(Game.player.iCivID) + " / " + GameValues.battle.BATTLE_MAX_BATTLE_WIDTH, Colors.HOVER_GOLD
                  )
               );
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
               nData.add(new MenuElement_HoverElement_Type_Empty());
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("ArmyDeployment"), Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.battleWidth, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements, true);
            }

            @Override
            public void actionElement() {
               if (Game.getCiv(Game.player.iCivID).iArmyPositionSize > 0) {
                  try {
                     if (Game.menuManager.getVisibleInGame_PopUp() && MenuManager.IN_GAME_POP_UP_MENU_ID == 51) {
                        Game.menuManager.setVisibleInGame_PopUp(false);
                     } else {
                        InGame_Battlefield.armyDivision = Game.getProvince(Game.getCiv(Game.player.iCivID).getArmyPosition(0))
                           .getArmy(Game.getCiv(Game.player.iCivID).getArmyPositionKey(0));
                        if (InGame_Battlefield.armyDivision != null) {
                           Game.menuManager.rebuildInGame_Battlefield();
                        } else {
                           Game.menuManager.setVisibleInGame_PopUp(false);
                        }
                     }
                  } catch (Exception var2) {
                     CFG.exceptionStack(var2);
                  }
               }
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new ButtonStatsRectIMG_Bonuses_Style(
            Game.lang.get("Discipline") + ": ",
            "" + CFG.getPrecision2(Game.getCiv(Game.player.iCivID).civBonuses.Discipline * 100.0F, 10) + "%",
            Images.discipline,
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
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
               nData.add(
                  new MenuElement_HoverElement_Type_TextTitle_BG(
                     Game.lang.get("Civilizations") + ": " + Game.lang.get("Discipline"), CFG.FONT_BOLD, Colors.HOVER_GOLD
                  )
               );
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.discipline, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Discipline") + ": "));
               nData.add(
                  new MenuElement_HoverElement_Type_TextTitle(
                     "" + CFG.getPrecision2(Game.getCiv(Game.player.iCivID).civBonuses.Discipline * 100.0F, 10) + "%", Colors.HOVER_GOLD
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
            public void actionElement() {
               Game.menuManager.rebuildInGame_Civ_Regiments();
               if (Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_CIV_POPULATION_HOVER) {
                  Game.mapModes.setActiveViewID(Game.mapModes.MODE_DIPLOMACY);
               }
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new ButtonStatsRectIMG_Bonuses_Style(
            Game.lang.get("RegimentsLimit") + ": ",
            CFG.getNumberWithSpaces("" + Game.getCiv(Game.player.iCivID).iRegimentsLimit),
            Images.regimentsLimit,
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
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
               nData.add(
                  new MenuElement_HoverElement_Type_TextTitle_BG(
                     Game.lang.get("Civilizations") + ": " + Game.lang.get("RegimentsLimit"), CFG.FONT_BOLD, Colors.HOVER_GOLD
                  )
               );
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.regimentsLimit, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     Game.lang.get("RegimentsLimit") + ": ",
                     "" + Game.getCiv(Game.player.iCivID).iRegimentsLimit,
                     Images.regimentsLimit,
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
                     Game.lang.get("MaximumManpower") + ": ",
                     "" + CFG.getNumberWithSpaces("" + (int)Game.getCiv(Game.player.iCivID).fManpowerMax),
                     Game_Calendar.IMG_MANPOWER_UP,
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
               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("BaseValue") + ": ", CFG.FONT_REGULAR_SMALL));
               nData.add(
                  new MenuElement_HoverElement_Type_Text(
                     "" + Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).REGIMENTS_LIMIT_BASE_VALUE,
                     CFG.FONT_BOLD_SMALL,
                     Colors.COLOR_TEXT_MODIFIER_POSITIVE
                  )
               );
               nData.add(new MenuElement_HoverElement_Type_Image(Images.regimentsLimit, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               if (Game.getCiv(Game.player.iCivID).civBonuses.RegimentsLimit > 0) {
                  nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Bonuses") + ": ", CFG.FONT_REGULAR_SMALL));
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        "" + Game.getCiv(Game.player.iCivID).civBonuses.RegimentsLimit, CFG.FONT_BOLD_SMALL, Colors.COLOR_TEXT_MODIFIER_POSITIVE
                     )
                  );
                  nData.add(new MenuElement_HoverElement_Type_Image(Images.regimentsLimit, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
               }

               if (Game.getCiv(Game.player.iCivID).diplomacy.iVassalsSize > 0) {
                  nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Vassals") + ": ", CFG.FONT_REGULAR_SMALL));
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        "" + GameValues.army.REGIMENTS_LIMIT_PER_VASSAL * Game.getCiv(Game.player.iCivID).diplomacy.iVassalsSize,
                        CFG.FONT_BOLD_SMALL,
                        Colors.COLOR_TEXT_MODIFIER_POSITIVE
                     )
                  );
                  nData.add(new MenuElement_HoverElement_Type_Image(Images.vassal, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
               }

               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("LocalManpower") + ": ", CFG.FONT_REGULAR_SMALL));
               nData.add(
                  new MenuElement_HoverElement_Type_Text(
                     "" + Game.getCiv(Game.player.iCivID).getRegimentsLimit_Manpower(), CFG.FONT_BOLD_SMALL, Colors.COLOR_TEXT_MODIFIER_POSITIVE
                  )
               );
               nData.add(new MenuElement_HoverElement_Type_Image(Game_Calendar.IMG_MANPOWER, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               float value = Game.getCiv(Game.player.iCivID).getRegimentsLimit_FromAllianceSpecial();
               if (value > 0.0F) {
                  nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Alliances") + ": ", CFG.FONT_REGULAR_SMALL));
                  nData.add(new MenuElement_HoverElement_Type_Text("" + CFG.getPrecision2(value, 1), CFG.FONT_BOLD_SMALL, Colors.COLOR_TEXT_MODIFIER_POSITIVE));
                  nData.add(new MenuElement_HoverElement_Type_Image(Images.alliance, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
               }

               if (Game.getCiv(Game.player.iCivID).getPuppetOfCivID() != Game.player.iCivID) {
                  nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Vassal") + ": ", CFG.FONT_REGULAR_SMALL));
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        "" + CFG.getPrecision2(GameValues.army.REGIMENTS_LIMIT_VASSAL * 100.0F, 1) + "%", CFG.FONT_BOLD_SMALL, Colors.HOVER_NEGATIVE
                     )
                  );
                  nData.add(new MenuElement_HoverElement_Type_Image(Images.vassal, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
               }

               this.menuElementHover = new MenuElement_Hover(nElements);
            }

            @Override
            public void actionElement() {
               Game.menuManager.rebuildInGame_Civ_Regiments();
               if (Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_CIV_POPULATION_HOVER) {
                  Game.mapModes.setActiveViewID(Game.mapModes.MODE_DIPLOMACY);
               }
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new ButtonStatsRectIMG_Bonuses_Style(
            Game.lang.get("Manpower") + ": ",
            CFG.getNumberWithSpaces("" + (int)Game.getCiv(Game.player.iCivID).fManpower)
               + " ["
               + (int)(Math.min(1.0, Game.getCiv(Game.player.iCivID).fManpower / Game.getCiv(Game.player.iCivID).fManpowerMax) * 100.0)
               + "%]",
            Game_Calendar.IMG_MANPOWER,
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
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
               nData.add(
                  new MenuElement_HoverElement_Type_TextTitle_BG(
                     Game.lang.get("Civilizations") + ": " + Game.lang.get("MaximumManpower"), CFG.FONT_BOLD, Colors.HOVER_GOLD
                  )
               );
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Game_Calendar.IMG_MANPOWER, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               float manpowerPerc = (float)Math.min(1.0, Game.getCiv(Game.player.iCivID).fManpower / Game.getCiv(Game.player.iCivID).fManpowerMax);
               int manpowerDays = (int)((1.0F - manpowerPerc) * (GameValues.manpower.MANPOWER_FULL_RECOVERY_MONTHS * 30.0F));
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus_Perc(
                     Game.lang.get("Manpower") + ": ",
                     "" + CFG.getNumberWithSpaces("" + (int)Game.getCiv(Game.player.iCivID).fManpower) + " [" + (int)(manpowerPerc * 100.0F) + "%]",
                     Game_Calendar.IMG_MANPOWER,
                     CFG.FONT_REGULAR,
                     CFG.FONT_BOLD,
                     Colors.HOVER_LEFT,
                     Colors.HOVER_GOLD,
                     manpowerPerc
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     Game.lang.get("MaximumManpower") + ": ",
                     "" + CFG.getNumberWithSpaces("" + (int)Game.getCiv(Game.player.iCivID).fManpowerMax),
                     Game_Calendar.IMG_MANPOWER,
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
               if (manpowerPerc < 0.98F && Game_Calendar.getNumOfDays_ByTurnsPlayed(manpowerDays).length() > 0) {
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        (int)(manpowerPerc * 100.0F) + "% -> 100%: ",
                        Game_Calendar.getNumOfDays_ByTurnsPlayed(manpowerDays),
                        Game_Calendar.IMG_MANPOWER_TIME,
                        CFG.FONT_REGULAR_SMALL,
                        CFG.FONT_BOLD_SMALL,
                        Colors.HOVER_LEFT,
                        Colors.HOVER_GOLD
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
               }

               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     "0% -> 100%: ",
                     ""
                        + Game.lang
                           .get(
                              "MonthsX",
                              (int)(
                                 GameValues.manpower.MANPOWER_FULL_RECOVERY_MONTHS * (1.0F + Game.getCiv(Game.player.iCivID).civBonuses.ManpowerRecoverySpeed)
                              )
                           ),
                     Game_Calendar.IMG_MANPOWER_TIME,
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
               Civilization playerCiv = Game.getCiv(Game.player.iCivID);
               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("BaseValue") + ": ", CFG.FONT_REGULAR_SMALL));
               nData.add(
                  new MenuElement_HoverElement_Type_Text(
                     "" + CFG.getNumberWithSpaces("" + GameValues.manpower.MANPOWER_MAX_BASE), CFG.FONT_BOLD_SMALL, Colors.COLOR_TEXT_MODIFIER_POSITIVE
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData, false));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Provinces") + ": ", CFG.FONT_REGULAR_SMALL));
               nData.add(
                  new MenuElement_HoverElement_Type_Text(
                     "" + CFG.getNumberWithSpaces("" + (int)Math.floor(Math.max(0.0F, playerCiv.getManpowerMax_Provinces_INFO()))),
                     CFG.FONT_BOLD_SMALL,
                     Colors.COLOR_TEXT_MODIFIER_POSITIVE
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData, false));
               nData.clear();
               if (GameValues.civRank.CIV_RANK_MANPOWER_MAX[playerCiv.iCivRankID] != 0) {
                  nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("CivilizationRank") + ": ", CFG.FONT_REGULAR_SMALL));
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        "" + CFG.getNumberWithSpaces("" + CFG.getPrecision2((float)GameValues.civRank.CIV_RANK_MANPOWER_MAX[playerCiv.iCivRankID], 1)),
                        CFG.FONT_BOLD_SMALL,
                        Colors.COLOR_TEXT_MODIFIER_POSITIVE
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData, false));
                  nData.clear();
               }

               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("CivilizationBonuses") + ": ", CFG.FONT_REGULAR_SMALL));
               nData.add(
                  new MenuElement_HoverElement_Type_Text(
                     "" + CFG.getNumberWithSpaces("" + CFG.getPrecision2(playerCiv.civBonuses.MaxManpower, 1)),
                     CFG.FONT_BOLD_SMALL,
                     Colors.COLOR_TEXT_MODIFIER_POSITIVE
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData, false));
               nData.clear();
               float tNum = playerCiv.getManpowerMax_Vassals_INFO();
               if (tNum > 0.0F) {
                  nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get(Game_Ages.getVassals()) + ": ", CFG.FONT_REGULAR_SMALL));
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        "" + CFG.getNumberWithSpaces("" + (int)Math.floor(tNum)), CFG.FONT_BOLD_SMALL, Colors.COLOR_TEXT_MODIFIER_POSITIVE
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData, false));
                  nData.clear();
               }

               if (playerCiv.fManpowerMax_ToLord != 0.0) {
                  nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get(Game_Ages.getLord()) + ": ", CFG.FONT_REGULAR_SMALL));
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        "" + CFG.getPrecision2(playerCiv.fManpowerMax_ToLord * -1.0, 1), CFG.FONT_BOLD_SMALL, Colors.HOVER_NEGATIVE
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData, false));
                  nData.clear();
               }

               for (int i = 0; i < playerCiv.inAllianceSize; i++) {
                  if (Game.alliancesSpecial.get(playerCiv.inAlliance.get(i)).iLeaderCivID == Game.player.iCivID
                     && Game.alliancesSpecial.get(playerCiv.inAlliance.get(i)).typeOfAlliance == 0) {
                     nData.add(
                        new MenuElement_HoverElement_Type_Text(
                           Game.lang.get(Game.alliancesSpecial.get(playerCiv.inAlliance.get(i)).Name_Alliance) + ": ", CFG.FONT_REGULAR_SMALL
                        )
                     );
                     nData.add(
                        new MenuElement_HoverElement_Type_Text(
                           "" + CFG.getNumberWithSpaces("" + CFG.getPrecision2((float)HREManager.getManpower_Emperor(playerCiv.inAlliance.get(i)), 1)),
                           CFG.FONT_BOLD_SMALL,
                           Colors.COLOR_TEXT_MODIFIER_POSITIVE
                        )
                     );
                     nElements.add(new MenuElement_HoverElement(nData, false));
                     nData.clear();
                  }
               }

               if (playerCiv.civBonuses.MaxManpower_Percentage != 0.0F) {
                  nData.add(new MenuElement_HoverElement_Type_Line());
                  nElements.add(new MenuElement_HoverElement(nData, false));
                  nData.clear();
                  nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("MaximumManpower") + ": ", CFG.FONT_REGULAR_SMALL));
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        (playerCiv.civBonuses.MaxManpower_Percentage > 0.0F ? "+" : "")
                           + CFG.getPrecision2(playerCiv.civBonuses.MaxManpower_Percentage * 100.0F, 100)
                           + "%",
                        CFG.FONT_BOLD_SMALL,
                        Colors.COLOR_TEXT_MODIFIER_POSITIVE
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData, false));
                  nData.clear();
               }

               this.menuElementHover = new MenuElement_Hover(nElements);
            }

            @Override
            public void actionElement() {
               Game.menuManager.rebuildInGame_Civ_Regiments();
               if (Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_CIV_POPULATION_HOVER) {
                  Game.mapModes.setActiveViewID(Game.mapModes.MODE_DIPLOMACY);
               }
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new ButtonStatsRectIMG_Bonuses_Style(
            Game.lang.get("MaximumManpower") + ": ",
            CFG.getNumberWithSpaces("" + (int)Game.getCiv(Game.player.iCivID).fManpowerMax),
            Game_Calendar.IMG_MANPOWER,
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
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
               nData.add(
                  new MenuElement_HoverElement_Type_TextTitle_BG(
                     Game.lang.get("Civilizations") + ": " + Game.lang.get("MaximumManpower"), CFG.FONT_BOLD, Colors.HOVER_GOLD
                  )
               );
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Game_Calendar.IMG_MANPOWER, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               float manpowerPerc = (float)Math.min(1.0, Game.getCiv(Game.player.iCivID).fManpower / Game.getCiv(Game.player.iCivID).fManpowerMax);
               int manpowerDays = (int)((1.0F - manpowerPerc) * (GameValues.manpower.MANPOWER_FULL_RECOVERY_MONTHS * 30.0F));
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus_Perc(
                     Game.lang.get("Manpower") + ": ",
                     "" + CFG.getNumberWithSpaces("" + (int)Game.getCiv(Game.player.iCivID).fManpower) + " [" + (int)(manpowerPerc * 100.0F) + "%]",
                     Game_Calendar.IMG_MANPOWER,
                     CFG.FONT_REGULAR,
                     CFG.FONT_BOLD,
                     Colors.HOVER_LEFT,
                     Colors.HOVER_GOLD,
                     manpowerPerc
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     Game.lang.get("MaximumManpower") + ": ",
                     "" + CFG.getNumberWithSpaces("" + (int)Game.getCiv(Game.player.iCivID).fManpowerMax),
                     Game_Calendar.IMG_MANPOWER,
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
               if (manpowerPerc < 0.98F && Game_Calendar.getNumOfDays_ByTurnsPlayed(manpowerDays).length() > 0) {
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        (int)(manpowerPerc * 100.0F) + "% -> 100%: ",
                        Game_Calendar.getNumOfDays_ByTurnsPlayed(manpowerDays),
                        Game_Calendar.IMG_MANPOWER_TIME,
                        CFG.FONT_REGULAR_SMALL,
                        CFG.FONT_BOLD_SMALL,
                        Colors.HOVER_LEFT,
                        Colors.HOVER_GOLD
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
               }

               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     "0% -> 100%: ",
                     ""
                        + Game.lang
                           .get(
                              "MonthsX",
                              (int)(
                                 GameValues.manpower.MANPOWER_FULL_RECOVERY_MONTHS * (1.0F + Game.getCiv(Game.player.iCivID).civBonuses.ManpowerRecoverySpeed)
                              )
                           ),
                     Game_Calendar.IMG_MANPOWER_TIME,
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
               Civilization playerCiv = Game.getCiv(Game.player.iCivID);
               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("BaseValue") + ": ", CFG.FONT_REGULAR_SMALL));
               nData.add(
                  new MenuElement_HoverElement_Type_Text(
                     "" + CFG.getNumberWithSpaces("" + GameValues.manpower.MANPOWER_MAX_BASE), CFG.FONT_BOLD_SMALL, Colors.COLOR_TEXT_MODIFIER_POSITIVE
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Provinces") + ": ", CFG.FONT_REGULAR_SMALL));
               nData.add(
                  new MenuElement_HoverElement_Type_Text(
                     "" + CFG.getNumberWithSpaces("" + (int)Math.floor(Math.max(0.0F, playerCiv.getManpowerMax_Provinces_INFO()))),
                     CFG.FONT_BOLD_SMALL,
                     Colors.COLOR_TEXT_MODIFIER_POSITIVE
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               if (GameValues.civRank.CIV_RANK_MANPOWER_MAX[playerCiv.iCivRankID] != 0) {
                  nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("CivilizationRank") + ": ", CFG.FONT_REGULAR_SMALL));
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        "" + CFG.getNumberWithSpaces("" + CFG.getPrecision2((float)GameValues.civRank.CIV_RANK_MANPOWER_MAX[playerCiv.iCivRankID], 1)),
                        CFG.FONT_BOLD_SMALL,
                        Colors.COLOR_TEXT_MODIFIER_POSITIVE
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
               }

               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("CivilizationBonuses") + ": ", CFG.FONT_REGULAR_SMALL));
               nData.add(
                  new MenuElement_HoverElement_Type_Text(
                     "" + CFG.getNumberWithSpaces("" + CFG.getPrecision2(playerCiv.civBonuses.MaxManpower, 1)),
                     CFG.FONT_BOLD_SMALL,
                     Colors.COLOR_TEXT_MODIFIER_POSITIVE
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               float tNum = playerCiv.getManpowerMax_Vassals_INFO();
               if (tNum > 0.0F) {
                  nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get(Game_Ages.getVassals()) + ": ", CFG.FONT_REGULAR_SMALL));
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        "" + CFG.getNumberWithSpaces("" + (int)Math.floor(tNum)), CFG.FONT_BOLD_SMALL, Colors.COLOR_TEXT_MODIFIER_POSITIVE
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
               }

               if (playerCiv.fManpowerMax_ToLord != 0.0) {
                  nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get(Game_Ages.getLord()) + ": ", CFG.FONT_REGULAR_SMALL));
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        "" + CFG.getPrecision2(playerCiv.fManpowerMax_ToLord * -1.0, 1), CFG.FONT_BOLD_SMALL, Colors.HOVER_NEGATIVE
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
               }

               for (int i = 0; i < playerCiv.inAllianceSize; i++) {
                  if (Game.alliancesSpecial.get(playerCiv.inAlliance.get(i)).iLeaderCivID == Game.player.iCivID
                     && Game.alliancesSpecial.get(playerCiv.inAlliance.get(i)).typeOfAlliance == 0) {
                     nData.add(
                        new MenuElement_HoverElement_Type_Text(
                           Game.lang.get(Game.alliancesSpecial.get(playerCiv.inAlliance.get(i)).Name_Alliance) + ": ", CFG.FONT_REGULAR_SMALL
                        )
                     );
                     nData.add(
                        new MenuElement_HoverElement_Type_Text(
                           "" + CFG.getNumberWithSpaces("" + CFG.getPrecision2((float)HREManager.getManpower_Emperor(playerCiv.inAlliance.get(i)), 1)),
                           CFG.FONT_BOLD_SMALL,
                           Colors.COLOR_TEXT_MODIFIER_POSITIVE
                        )
                     );
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                  }
               }

               if (playerCiv.civBonuses.MaxManpower_Percentage != 0.0F) {
                  nData.add(new MenuElement_HoverElement_Type_Line());
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("MaximumManpower") + ": ", CFG.FONT_REGULAR_SMALL));
                  nData.add(
                     new MenuElement_HoverElement_Type_Text(
                        (playerCiv.civBonuses.MaxManpower_Percentage > 0.0F ? "+" : "")
                           + CFG.getPrecision2(playerCiv.civBonuses.MaxManpower_Percentage * 100.0F, 100)
                           + "%",
                        CFG.FONT_BOLD_SMALL,
                        Colors.COLOR_TEXT_MODIFIER_POSITIVE
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
               }

               this.menuElementHover = new MenuElement_Hover(nElements);
            }

            @Override
            public void actionElement() {
               Game.menuManager.rebuildInGame_Civ_Regiments();
               if (Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_CIV_POPULATION_HOVER) {
                  Game.mapModes.setActiveViewID(Game.mapModes.MODE_DIPLOMACY);
               }
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      menuElements.add(
         new Text_Desc(
            Game.lang.get("Manpower") + ": 0% -> 100%: " + Game.lang.get("MonthsX", (int)GameValues.manpower.MANPOWER_FULL_RECOVERY_MONTHS),
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2
         )
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      if (GameValues.inGame.SHOW_TO_BE_RESEARCHED_UNITS) {
         i = 0;
         menuElements.add(
            new Text_Title(
               Game.lang.get("ToBeResearched"),
               CFG.BUTTON_WIDTH / 4,
               Images.boxTitleBORDERWIDTH,
               buttonY,
               menuWidth - Images.boxTitleBORDERWIDTH * 2,
               textTitleH
            ) {
               @Override
               public void drawLines(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
               }

               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("ToBeResearched"), CFG.FONT_BOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle(Game_Calendar.IMG_MANPOWER, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements) {
                     @Override
                     public int getMinPosX() {
                        return InGame_RecruitArmy.HOVER_POSX;
                     }
                  };
               }
            }
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;

         for (int o = 0; o < 3; o++) {
            for (int ix = 0; ix < ArmyManager.iUnitsTypesSize; ix++) {
               for (int j = 0; j < ArmyManager.lArmySize.get(ix); j++) {
                  if (ArmyManager.lUnitsTypes.get(ix).Line == o && !Game.getCiv(Game.player.iCivID).isUnitResearched(ix, j)) {
                     i++;
                     menuElements.add(new ButtonUnit3_3(ix, j, paddingLeft, buttonY, menuWidth - paddingLeft * 2, true, false) {
                        @Override
                        public void actionElement() {
                           if (Game.menuManager.getVisibleInGame_TechnologyTree()) {
                              Game.menuManager.setVisibleInGame_TechnologyTree(false);
                           } else {
                              Game.menuManager.setVisibleInGame_TechnologyChoose(false);
                              InGame_TechnologyTree.centerToTechID = ArmyManager.lArmy.get(this.getValue1()).get(this.getValue2()).RequiredTechID;
                              Game.menuManager.rebuildInGame_TechnologyTree(false, true);
                           }
                        }
                     });
                     buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
                  }
               }
            }
         }

         if (i == 0) {
            menuElements.add(
               new Text_StaticBG(Game.lang.get("None"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT2)
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }
      }

      i = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - CFG.PADDING * 3);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, i)));
      HOVER_POSX = menuX + menuWidth + CFG.PADDING;
      this.initMenu(
         new MenuTitleIMG_DoubleText("", Game.lang.get("BattleWidth") + ": " + BattleManager.getBattleWidth(Game.player.iCivID), false, false, Images.title1Red) {
            @Override
            public long getTime() {
               return InGame_RecruitArmy.lTime;
            }
         },
         menuX,
         menuY,
         menuWidth,
         i,
         menuElements,
         false,
         true
      );
      this.drawScrollPositionAlways = false;
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
      Renderer.drawMenusBox(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight() + CFG.PADDING, false);
      ImageManager.getImage(Images.recruitArmyOver)
         .draw2(
            oSB,
            this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.recruitArmyOver).getWidth() / 2 + iTranslateX,
            this.getPosY() + iTranslateY,
            this.getWidth(),
            Math.min(this.getHeight(), ImageManager.getImage(Images.recruitArmyOver).getHeight())
         );
      if (Game.getCiv(Game.player.iCivID).diplomacy.isAtWar()) {
         oSB.setColor(sparksColor);
         sparksAnimation.draw2(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), Images.sparkHeight, false, true);
         oSB.setColor(Color.WHITE);
      }

      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public void updateLanguage() {
      super.updateLanguage();
      this.getTitle().setText(Game.lang.get("LandUnits"));
   }

   @Override
   public void setVisible(boolean visible) {
      super.setVisible(visible);
      lTime = CFG.currentTimeMillis;
      if (!visible && Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_RECRUIT_ARMY) {
         Game.mapModes.setActiveViewID(Game.mapModes.MODE_DEFAULT);
      }
   }

   public static MenuElement_Hover getHoverMilitaryAcademy() {
      return getHoverMilitaryAcademy(true, Game.player.iCivID);
   }

   public static MenuElement_Hover getHoverMilitaryAcademy(boolean showUpgrade, int iCivID) {
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      nData.add(new MenuElement_HoverElement_Type_TextTitle_BG_Center(Game.lang.get("MilitaryAcademy"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_ImageFull(Images.armyMilitaryAcademyBig, 0, 0));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Line());
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Level") + ": ", CFG.FONT_REGULAR_SMALL));
      nData.add(
         new MenuElement_HoverElement_Type_Text(
            Game.getCiv(iCivID).getMilitaryAcademyLevel() + " / " + Game.getMilitaryAcademy_MaxLvl(iCivID), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Line());
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(
         new MenuElement_HoverElement_Type_Button_TextBonus(
            Game.lang.get("UnitsAttack") + ": ",
            (Game.getMilitaryAcademy_Attack(iCivID) > 0 ? "+" : "") + CFG.getPrecision2((float)Game.getMilitaryAcademy_Attack(iCivID), 100),
            Images.attack,
            CFG.FONT_REGULAR_SMALL,
            CFG.FONT_BOLD_SMALL,
            Colors.HOVER_LEFT,
            Game.getMilitaryAcademy_Attack(iCivID) > 0 ? Colors.COLOR_TEXT_MODIFIER_POSITIVE : Colors.HOVER_RIGHT
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(
         new MenuElement_HoverElement_Type_Button_TextBonus(
            Game.lang.get("UnitsDefense") + ": ",
            (Game.getMilitaryAcademy_Defense(iCivID) > 0 ? "+" : "") + CFG.getPrecision2((float)Game.getMilitaryAcademy_Defense(iCivID), 100),
            Images.defense,
            CFG.FONT_REGULAR_SMALL,
            CFG.FONT_BOLD_SMALL,
            Colors.HOVER_LEFT,
            Game.getMilitaryAcademy_Defense(iCivID) > 0 ? Colors.COLOR_TEXT_MODIFIER_POSITIVE : Colors.HOVER_RIGHT
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(
         new MenuElement_HoverElement_Type_Button_TextBonus(
            Game.lang.get("RegimentsLimit") + ": ",
            (Game.getMilitaryAcademy_RegimentsLimit(iCivID) > 0 ? "+" : "") + CFG.getPrecision2((float)Game.getMilitaryAcademy_RegimentsLimit(iCivID), 1),
            Images.regimentsLimit,
            CFG.FONT_REGULAR_SMALL,
            CFG.FONT_BOLD_SMALL,
            Colors.HOVER_LEFT,
            Game.getMilitaryAcademy_RegimentsLimit(iCivID) > 0 ? Colors.COLOR_TEXT_MODIFIER_POSITIVE : Colors.HOVER_RIGHT
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(
         new MenuElement_HoverElement_Type_Button_TextBonus(
            Game.lang.get("MaintenanceCost") + ": ",
            "" + CFG.getPrecision2(Game.getMilitaryAcademy_MaintenanceCost(iCivID), 100),
            Images.gold,
            CFG.FONT_REGULAR_SMALL,
            CFG.FONT_BOLD_SMALL,
            Colors.HOVER_LEFT,
            Game.getMilitaryAcademy_MaintenanceCost(iCivID) > 0.0F ? Colors.COLOR_TEXT_MODIFIER_NEGATIVE : Colors.HOVER_RIGHT
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      if (showUpgrade) {
         if (Game.getCiv(iCivID).getMilitaryAcademyLevel() >= Game.getMilitaryAcademy_MaxLvl(iCivID)) {
            nData.add(new MenuElement_HoverElement_Type_Line());
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("MaximumLevel"), CFG.FONT_BOLD_SMALL));
            nData.add(new MenuElement_HoverElement_Type_Image(Game_Calendar.IMG_MANPOWER, CFG.PADDING, 0));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
         } else {
            nData.add(new MenuElement_HoverElement_Type_Empty());
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("UpgradeMilitaryAcademy"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
            nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Game_Calendar.IMG_MANPOWER, CFG.PADDING, 0));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Cost") + ": ", CFG.FONT_REGULAR_SMALL));
            nData.add(
               new MenuElement_HoverElement_Type_Text(CFG.getPrecision2(Game.getMilitaryAcademy_Cost(iCivID), 100), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT)
            );
            nData.add(new MenuElement_HoverElement_Type_Image(Images.gold, CFG.PADDING, 0));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("UnitsAttackPerLevel") + ": ", CFG.FONT_REGULAR_SMALL));
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  "+" + CFG.getPrecision2((float)GameValues.militaryAcademy.MILITARY_ACADEMY_ATTACK_PER_LVL, 100),
                  CFG.FONT_BOLD_SMALL,
                  Colors.COLOR_TEXT_MODIFIER_POSITIVE
               )
            );
            nData.add(new MenuElement_HoverElement_Type_Image(Images.attack, CFG.PADDING, 0));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("UnitsDefensePerLevel") + ": ", CFG.FONT_REGULAR_SMALL));
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  "+" + CFG.getPrecision2((float)GameValues.militaryAcademy.MILITARY_ACADEMY_DEFENSE_PER_LVL, 100),
                  CFG.FONT_BOLD_SMALL,
                  Colors.COLOR_TEXT_MODIFIER_POSITIVE
               )
            );
            nData.add(new MenuElement_HoverElement_Type_Image(Images.defense, CFG.PADDING, 0));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("RegimentsLimitPerLevel") + ": ", CFG.FONT_REGULAR_SMALL));
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  "+" + CFG.getPrecision2((float)GameValues.militaryAcademy.MILITARY_ACADEMY_REGIMENTS_LIMIT_PER_LVL, 1),
                  CFG.FONT_BOLD_SMALL,
                  Colors.COLOR_TEXT_MODIFIER_POSITIVE
               )
            );
            nData.add(new MenuElement_HoverElement_Type_Image(Images.regimentsLimit, CFG.PADDING, 0));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("MaintenanceCost") + ": ", CFG.FONT_REGULAR_SMALL));
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  "+" + CFG.getPrecision2(GameValues.militaryAcademy.MILITARY_ACADEMY_MAINTENANCE_COST_PER_LVL, 100),
                  CFG.FONT_BOLD_SMALL,
                  Colors.COLOR_TEXT_MODIFIER_NEGATIVE
               )
            );
            nData.add(new MenuElement_HoverElement_Type_Image(Images.gold, CFG.PADDING, 0));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
         }
      }

      return new MenuElement_Hover(nElements);
   }

   public static final void upgradeMilitaryAcademy() {
      if (Game.getCiv(Game.player.iCivID).getMilitaryAcademyLevel() >= Game.getMilitaryAcademy_MaxLvl(Game.player.iCivID)) {
         Game.menuManager
            .addToastInsufficient(
               Game.lang.get("MaximumLevel") + ": ",
               "" + Game.getCiv(Game.player.iCivID).getMilitaryAcademyLevel() + " / " + Game.getMilitaryAcademy_MaxLvl(Game.player.iCivID),
               Game_Calendar.IMG_MANPOWER
            );
      } else if (Game.getCiv(Game.player.iCivID).fGold < Game.getMilitaryAcademy_Cost(Game.player.iCivID)) {
         Game.menuManager
            .addToastInsufficient(
               Game.lang.get("InsufficientGold") + ": ", CFG.getPrecision2(Game.getMilitaryAcademy_Cost(Game.player.iCivID), 100), Images.gold
            );
      } else {
         Game.getCiv(Game.player.iCivID).upgradeMilitaryAcademy();
         InGame_Info.iCivID = Game.player.iCivID;
         InGame_Info.iCivID2 = 0;
         Game.menuManager
            .rebuildInGame_Info(
               Game.lang.get("MilitaryAcademy"),
               Game.lang.get("Level")
                  + ": "
                  + Game.getCiv(Game.player.iCivID).getMilitaryAcademyLevel()
                  + " / "
                  + Game.getMilitaryAcademy_MaxLvl(Game.player.iCivID)
            );
         InGame_Info.imgID = Images.infoGeneral;
         Game.getCiv(Game.player.iCivID).updateTotalIncomePerMonth();
      }
   }

   public static MenuElement getTitleFirstLine(int buttonY, int menuWidth, int textTitleH) {
      return new Text_Title_BattleLine(
         Game.lang.get("FirstLine"), CFG.BUTTON_WIDTH / 4, Images.boxTitleBORDERWIDTH, buttonY, menuWidth - Images.boxTitleBORDERWIDTH * 2, textTitleH, 0
      ) {
         @Override
         public void actionElement() {
            if (Game.getCiv(Game.player.iCivID).iArmyPositionSize > 0) {
               try {
                  if (Game.menuManager.getVisibleInGame_PopUp() && MenuManager.IN_GAME_POP_UP_MENU_ID == 51) {
                     Game.menuManager.setVisibleInGame_PopUp(false);
                  } else {
                     InGame_Battlefield.armyDivision = Game.getProvince(Game.getCiv(Game.player.iCivID).getArmyPosition(0))
                        .getArmy(Game.getCiv(Game.player.iCivID).getArmyPositionKey(0));
                     if (InGame_Battlefield.armyDivision != null) {
                        Game.menuManager.rebuildInGame_Battlefield();
                     } else {
                        Game.menuManager.setVisibleInGame_PopUp(false);
                     }
                  }
               } catch (Exception var2) {
                  CFG.exceptionStack(var2);
               }
            }
         }

         @Override
         public void buildElementHover() {
            List<MenuElement_HoverElement> nElements = new ArrayList<>();
            List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
            nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("BattlePosition") + ": ", CFG.FONT_BOLD));
            nData.add(new MenuElement_HoverElement_Type_TextTitle_BG_Clear(Game.lang.get("FirstLine"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy2));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy1));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy1));
            nData.add(new MenuElement_HoverElement_Type_BattleRegiment(Game.player.iCivID, Images.battleArmy0));
            nData.add(new MenuElement_HoverElement_Type_BattleRegiment(Game.player.iCivID, Images.battleArmy0));
            nData.add(new MenuElement_HoverElement_Type_BattleRegiment(Game.player.iCivID, Images.battleArmy0));
            nData.add(new MenuElement_HoverElement_Type_BattleRegiment(Game.player.iCivID, Images.battleArmy0));
            nData.add(new MenuElement_HoverElement_Type_BattleRegiment(Game.player.iCivID, Images.battleArmy0));
            nData.add(new MenuElement_HoverElement_Type_BattleRegiment(Game.player.iCivID, Images.battleArmy0));
            nData.add(new MenuElement_HoverElement_Type_BattleRegiment(Game.player.iCivID, Images.battleArmy0));
            nData.add(new MenuElement_HoverElement_Type_BattleRegiment(Game.player.iCivID, Images.battleArmy0));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy1));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy1));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            nData.add(new MenuElement_HoverElement_Type_Line());
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy1));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy1));
            nData.add(new MenuElement_HoverElement_Type_BattleRegiment(Game.player.iCivID, Images.battleArmy0));
            nData.add(new MenuElement_HoverElement_Type_BattleRegiment(Game.player.iCivID, Images.battleArmy0));
            nData.add(new MenuElement_HoverElement_Type_BattleRegiment(Game.player.iCivID, Images.battleArmy0));
            nData.add(new MenuElement_HoverElement_Type_BattleRegiment(Game.player.iCivID, Images.battleArmy0));
            nData.add(new MenuElement_HoverElement_Type_BattleRegiment(Game.player.iCivID, Images.battleArmy0));
            nData.add(new MenuElement_HoverElement_Type_BattleRegiment(Game.player.iCivID, Images.battleArmy0));
            nData.add(new MenuElement_HoverElement_Type_BattleRegiment(Game.player.iCivID, Images.battleArmy0));
            nData.add(new MenuElement_HoverElement_Type_BattleRegiment(Game.player.iCivID, Images.battleArmy0));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy1));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy1));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy2));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover(nElements) {
               @Override
               public int getMinPosX() {
                  return InGame_RecruitArmy.HOVER_POSX;
               }
            };
         }
      };
   }

   public static MenuElement getTitleFirstLineSide(int buttonY, int menuWidth, int textTitleH) {
      return new Text_Title_BattleLine(
         Game.lang.get("Flank"), CFG.BUTTON_WIDTH / 4, Images.boxTitleBORDERWIDTH, buttonY, menuWidth - Images.boxTitleBORDERWIDTH * 2, textTitleH, 1
      ) {
         @Override
         public void actionElement() {
            if (Game.getCiv(Game.player.iCivID).iArmyPositionSize > 0) {
               try {
                  if (Game.menuManager.getVisibleInGame_PopUp() && MenuManager.IN_GAME_POP_UP_MENU_ID == 51) {
                     Game.menuManager.setVisibleInGame_PopUp(false);
                  } else {
                     InGame_Battlefield.armyDivision = Game.getProvince(Game.getCiv(Game.player.iCivID).getArmyPosition(0))
                        .getArmy(Game.getCiv(Game.player.iCivID).getArmyPositionKey(0));
                     if (InGame_Battlefield.armyDivision != null) {
                        Game.menuManager.rebuildInGame_Battlefield();
                     } else {
                        Game.menuManager.setVisibleInGame_PopUp(false);
                     }
                  }
               } catch (Exception var2) {
                  CFG.exceptionStack(var2);
               }
            }
         }

         @Override
         public void buildElementHover() {
            List<MenuElement_HoverElement> nElements = new ArrayList<>();
            List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
            nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("BattlePosition") + ": ", CFG.FONT_BOLD));
            nData.add(new MenuElement_HoverElement_Type_TextTitle_BG_Clear(Game.lang.get("Flank"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy2));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            nData.add(new MenuElement_HoverElement_Type_BattleRegiment(Game.player.iCivID, Images.battleArmy1));
            nData.add(new MenuElement_HoverElement_Type_BattleRegiment(Game.player.iCivID, Images.battleArmy1));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy0));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy0));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy0));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy0));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy0));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy0));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy0));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy0));
            nData.add(new MenuElement_HoverElement_Type_BattleRegiment(Game.player.iCivID, Images.battleArmy1));
            nData.add(new MenuElement_HoverElement_Type_BattleRegiment(Game.player.iCivID, Images.battleArmy1));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            nData.add(new MenuElement_HoverElement_Type_Line());
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            nData.add(new MenuElement_HoverElement_Type_BattleRegiment(Game.player.iCivID, Images.battleArmy1));
            nData.add(new MenuElement_HoverElement_Type_BattleRegiment(Game.player.iCivID, Images.battleArmy1));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy0));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy0));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy0));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy0));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy0));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy0));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy0));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy0));
            nData.add(new MenuElement_HoverElement_Type_BattleRegiment(Game.player.iCivID, Images.battleArmy1));
            nData.add(new MenuElement_HoverElement_Type_BattleRegiment(Game.player.iCivID, Images.battleArmy1));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy2));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover(nElements) {
               @Override
               public int getMinPosX() {
                  return InGame_RecruitArmy.HOVER_POSX;
               }
            };
         }
      };
   }

   public static MenuElement getTitleSecondLine(int buttonY, int menuWidth, int textTitleH) {
      return new Text_Title_BattleLine(
         Game.lang.get("Support"), CFG.BUTTON_WIDTH / 4, Images.boxTitleBORDERWIDTH, buttonY, menuWidth - Images.boxTitleBORDERWIDTH * 2, textTitleH, 2
      ) {
         @Override
         public void actionElement() {
            if (Game.getCiv(Game.player.iCivID).iArmyPositionSize > 0) {
               try {
                  if (Game.menuManager.getVisibleInGame_PopUp() && MenuManager.IN_GAME_POP_UP_MENU_ID == 51) {
                     Game.menuManager.setVisibleInGame_PopUp(false);
                  } else {
                     InGame_Battlefield.armyDivision = Game.getProvince(Game.getCiv(Game.player.iCivID).getArmyPosition(0))
                        .getArmy(Game.getCiv(Game.player.iCivID).getArmyPositionKey(0));
                     if (InGame_Battlefield.armyDivision != null) {
                        Game.menuManager.rebuildInGame_Battlefield();
                     } else {
                        Game.menuManager.setVisibleInGame_PopUp(false);
                     }
                  }
               } catch (Exception var2) {
                  CFG.exceptionStack(var2);
               }
            }
         }

         @Override
         public void buildElementHover() {
            List<MenuElement_HoverElement> nElements = new ArrayList<>();
            List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
            nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("BattlePosition") + ": ", CFG.FONT_BOLD));
            nData.add(new MenuElement_HoverElement_Type_TextTitle_BG_Clear(Game.lang.get("Support"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            nData.add(new MenuElement_HoverElement_Type_BattleRegiment(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegiment(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegiment(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegiment(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegiment(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegiment(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegiment(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegiment(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegiment(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegiment(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegiment(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegiment(Game.player.iCivID, Images.battleArmy2));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy1));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy1));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy0));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy0));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy0));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy0));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy0));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy0));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy0));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy0));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy1));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy1));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            nData.add(new MenuElement_HoverElement_Type_Line());
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy1));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy1));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy0));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy0));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy0));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy0));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy0));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy0));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy0));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy0));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy1));
            nData.add(new MenuElement_HoverElement_Type_BattleRegimentEmpty(Game.player.iCivID, Images.battleArmy1));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            nData.add(new MenuElement_HoverElement_Type_BattleRegiment(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegiment(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegiment(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegiment(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegiment(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegiment(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegiment(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegiment(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegiment(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegiment(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegiment(Game.player.iCivID, Images.battleArmy2));
            nData.add(new MenuElement_HoverElement_Type_BattleRegiment(Game.player.iCivID, Images.battleArmy2));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover(nElements) {
               @Override
               public int getMinPosX() {
                  return InGame_RecruitArmy.HOVER_POSX;
               }
            };
         }
      };
   }

   public static List<MenuElement> getRecruitMenuElements(int buttonY, int menuWidth, int paddingLeft, int buttonYPadding, int textTitleH) {
      List<MenuElement> menuElements = new ArrayList<>();
      menuElements.add(getTitleFirstLine(buttonY, menuWidth, textTitleH));
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      InGame_RecruitSameType.firstLineUnits = menuElements.size() - 1;
      int tempAdded = 0;

      for (int i = 0; i < ArmyManager.iUnitsTypesSize; i++) {
         for (int j = 0; j < ArmyManager.lArmySize.get(i); j++) {
            if (ArmyManager.lUnitsTypes.get(i).Line == 0 && !ArmyManager.lArmy.get(i).get(j).isSettler && Game.getCiv(Game.player.iCivID).isUnitBest(i, j)) {
               tempAdded++;
               menuElements.add(new ButtonUnit3_3(i, j, paddingLeft, buttonY, menuWidth - paddingLeft * 2, true, true) {
                  @Override
                  public void actionElement() {
                     super.actionElement();
                     if (Game.mapModes.iActiveMapModeID != Game.mapModes.MODE_RECRUIT_ARMY) {
                        Game.gameActiveProvince.resetLastActiveProvince();
                        Game.setActiveProvinceID(-1);
                        Game.menuManager.setVisibleInGame_ProvinceInfo(false);
                        Game.armyRecruit = new ArmyRecruit(0, this.getValue1(), this.getValue2(), InGame_RecruitArmy.key);
                        Game.armyRecruit.cost = ArmyManager.getRecruitmentCost(Game.player.iCivID, -1, this.getValue1(), this.getValue2());
                        Game.mapModes.setActiveViewID(Game.mapModes.MODE_RECRUIT_ARMY);
                     } else if (Game.armyRecruit.unitID == this.getValue1() && Game.armyRecruit.armyID == this.getValue2()) {
                        Game.mapModes.setActiveViewID(Game.mapModes.MODE_DEFAULT);
                     } else {
                        Game.gameActiveProvince.resetLastActiveProvince();
                        Game.setActiveProvinceID(-1);
                        Game.menuManager.setVisibleInGame_ProvinceInfo(false);
                        Game.armyRecruit = new ArmyRecruit(0, this.getValue1(), this.getValue2(), InGame_RecruitArmy.key);
                        Game.armyRecruit.cost = ArmyManager.getRecruitmentCost(Game.player.iCivID, -1, this.getValue1(), this.getValue2());
                     }
                  }
               });
               buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
            }
         }
      }

      if (tempAdded == 0) {
         menuElements.add(new Text_StaticBG(Game.lang.get("None"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT2));
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      }

      tempAdded = 0;
      menuElements.add(getTitleFirstLineSide(buttonY, menuWidth, textTitleH));
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      InGame_RecruitSameType.flankLineElemID = menuElements.size() - 1;

      for (int i = 0; i < ArmyManager.iUnitsTypesSize; i++) {
         for (int jx = 0; jx < ArmyManager.lArmySize.get(i); jx++) {
            if (ArmyManager.lUnitsTypes.get(i).Line == 1 && !ArmyManager.lArmy.get(i).get(jx).isSettler && Game.getCiv(Game.player.iCivID).isUnitBest(i, jx)) {
               tempAdded++;
               menuElements.add(new ButtonUnit3_3(i, jx, paddingLeft, buttonY, menuWidth - paddingLeft * 2, true, true) {
                  @Override
                  public void actionElement() {
                     super.actionElement();
                     if (Game.mapModes.iActiveMapModeID != Game.mapModes.MODE_RECRUIT_ARMY) {
                        Game.gameActiveProvince.resetLastActiveProvince();
                        Game.setActiveProvinceID(-1);
                        Game.menuManager.setVisibleInGame_ProvinceInfo(false);
                        Game.armyRecruit = new ArmyRecruit(0, this.getValue1(), this.getValue2(), InGame_RecruitArmy.key);
                        Game.armyRecruit.cost = ArmyManager.getRecruitmentCost(Game.player.iCivID, -1, this.getValue1(), this.getValue2());
                        Game.mapModes.setActiveViewID(Game.mapModes.MODE_RECRUIT_ARMY);
                     } else if (Game.armyRecruit.unitID == this.getValue1() && Game.armyRecruit.armyID == this.getValue2()) {
                        Game.mapModes.setActiveViewID(Game.mapModes.MODE_DEFAULT);
                     } else {
                        Game.gameActiveProvince.resetLastActiveProvince();
                        Game.setActiveProvinceID(-1);
                        Game.menuManager.setVisibleInGame_ProvinceInfo(false);
                        Game.armyRecruit = new ArmyRecruit(0, this.getValue1(), this.getValue2(), InGame_RecruitArmy.key);
                        Game.armyRecruit.cost = ArmyManager.getRecruitmentCost(Game.player.iCivID, -1, this.getValue1(), this.getValue2());
                     }
                  }
               });
               buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
            }
         }
      }

      if (tempAdded == 0) {
         menuElements.add(new Text_StaticBG(Game.lang.get("None"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT2));
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      }

      tempAdded = 0;
      menuElements.add(getTitleSecondLine(buttonY, menuWidth, textTitleH));
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      InGame_RecruitSameType.supportLineElemID = menuElements.size() - 1;

      for (int i = 0; i < ArmyManager.iUnitsTypesSize; i++) {
         for (int jxx = 0; jxx < ArmyManager.lArmySize.get(i); jxx++) {
            if (ArmyManager.lUnitsTypes.get(i).Line > 1 && !ArmyManager.lArmy.get(i).get(jxx).isSettler && Game.getCiv(Game.player.iCivID).isUnitBest(i, jxx)) {
               tempAdded++;
               menuElements.add(new ButtonUnit3_3(i, jxx, paddingLeft, buttonY, menuWidth - paddingLeft * 2, true, true) {
                  @Override
                  public void actionElement() {
                     super.actionElement();
                     if (Game.mapModes.iActiveMapModeID != Game.mapModes.MODE_RECRUIT_ARMY) {
                        Game.gameActiveProvince.resetLastActiveProvince();
                        Game.setActiveProvinceID(-1);
                        Game.menuManager.setVisibleInGame_ProvinceInfo(false);
                        Game.armyRecruit = new ArmyRecruit(0, this.getValue1(), this.getValue2(), InGame_RecruitArmy.key);
                        Game.armyRecruit.cost = ArmyManager.getRecruitmentCost(Game.player.iCivID, -1, this.getValue1(), this.getValue2());
                        Game.mapModes.setActiveViewID(Game.mapModes.MODE_RECRUIT_ARMY);
                     } else if (Game.armyRecruit.unitID == this.getValue1() && Game.armyRecruit.armyID == this.getValue2()) {
                        Game.mapModes.setActiveViewID(Game.mapModes.MODE_DEFAULT);
                     } else {
                        Game.gameActiveProvince.resetLastActiveProvince();
                        Game.setActiveProvinceID(-1);
                        Game.menuManager.setVisibleInGame_ProvinceInfo(false);
                        Game.armyRecruit = new ArmyRecruit(0, this.getValue1(), this.getValue2(), InGame_RecruitArmy.key);
                        Game.armyRecruit.cost = ArmyManager.getRecruitmentCost(Game.player.iCivID, -1, this.getValue1(), this.getValue2());
                     }
                  }
               });
               buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
            }
         }
      }

      if (tempAdded == 0) {
         menuElements.add(new Text_StaticBG(Game.lang.get("None"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT2));
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      }

      tempAdded = 0;
      menuElements.add(
         new Text_TitleBlue(
            Game.lang.get("Settlers"), CFG.BUTTON_WIDTH / 4, Images.boxTitleBORDERWIDTH, buttonY, menuWidth - Images.boxTitleBORDERWIDTH * 2, textTitleH
         ) {
            @Override
            public void drawLines(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Settlers"), CFG.FONT_BOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle(Images.population, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements) {
                  @Override
                  public int getMinPosX() {
                     return InGame_RecruitArmy.HOVER_POSX;
                  }
               };
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;

      for (int i = 0; i < ArmyManager.iUnitsTypesSize; i++) {
         for (int jxxx = 0; jxxx < ArmyManager.lArmySize.get(i); jxxx++) {
            if (ArmyManager.lArmy.get(i).get(jxxx).isSettler && Game.getCiv(Game.player.iCivID).isUnitBest(i, jxxx)) {
               tempAdded++;
               menuElements.add(new ButtonUnit3_3(i, jxxx, paddingLeft, buttonY, menuWidth - paddingLeft * 2, true, true) {
                  @Override
                  public void actionElement() {
                     super.actionElement();
                     if (Game.mapModes.iActiveMapModeID != Game.mapModes.MODE_RECRUIT_ARMY) {
                        Game.gameActiveProvince.resetLastActiveProvince();
                        Game.setActiveProvinceID(-1);
                        Game.menuManager.setVisibleInGame_ProvinceInfo(false);
                        Game.armyRecruit = new ArmyRecruit(0, this.getValue1(), this.getValue2(), InGame_RecruitArmy.key);
                        Game.armyRecruit.cost = ArmyManager.getRecruitmentCost(Game.player.iCivID, -1, this.getValue1(), this.getValue2());
                        Game.mapModes.setActiveViewID(Game.mapModes.MODE_RECRUIT_ARMY);
                     } else if (Game.armyRecruit.unitID == this.getValue1() && Game.armyRecruit.armyID == this.getValue2()) {
                        Game.mapModes.setActiveViewID(Game.mapModes.MODE_DEFAULT);
                     } else {
                        Game.gameActiveProvince.resetLastActiveProvince();
                        Game.setActiveProvinceID(-1);
                        Game.menuManager.setVisibleInGame_ProvinceInfo(false);
                        Game.armyRecruit = new ArmyRecruit(0, this.getValue1(), this.getValue2(), InGame_RecruitArmy.key);
                        Game.armyRecruit.cost = ArmyManager.getRecruitmentCost(Game.player.iCivID, -1, this.getValue1(), this.getValue2());
                     }
                  }
               });
               buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
            }
         }
      }

      if (tempAdded == 0) {
         menuElements.add(new Text_StaticBG(Game.lang.get("None"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT2));
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      }

      tempAdded = 0;
      if (GameValues.inGame.SHOW_OBSOLETE_UNITS) {
         if (GameValues.army.CAN_RECRUIT_OBSOLETE_UNITS) {
            menuElements.add(
               new Text_TitleBlue(
                  Game.lang.get("Obsolete"), CFG.BUTTON_WIDTH / 4, Images.boxTitleBORDERWIDTH, buttonY, menuWidth - Images.boxTitleBORDERWIDTH * 2, textTitleH
               ) {
                  @Override
                  public void drawLines(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
                  }

                  @Override
                  public void buildElementHover() {
                     List<MenuElement_HoverElement> nElements = new ArrayList<>();
                     List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                     nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Obsolete"), CFG.FONT_BOLD));
                     nData.add(new MenuElement_HoverElement_Type_ImageTitle(Game_Calendar.IMG_MANPOWER, CFG.PADDING, 0));
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     this.menuElementHover = new MenuElement_Hover(nElements) {
                        @Override
                        public int getMinPosX() {
                           return InGame_RecruitArmy.HOVER_POSX;
                        }
                     };
                  }
               }
            );
         } else {
            menuElements.add(
               new Text_Title(
                  Game.lang.get("Obsolete"), CFG.BUTTON_WIDTH / 4, Images.boxTitleBORDERWIDTH, buttonY, menuWidth - Images.boxTitleBORDERWIDTH * 2, textTitleH
               ) {
                  @Override
                  public void drawLines(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
                  }

                  @Override
                  public void buildElementHover() {
                     List<MenuElement_HoverElement> nElements = new ArrayList<>();
                     List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                     nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Obsolete"), CFG.FONT_BOLD));
                     nData.add(new MenuElement_HoverElement_Type_ImageTitle(Game_Calendar.IMG_MANPOWER, CFG.PADDING, 0));
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     this.menuElementHover = new MenuElement_Hover(nElements) {
                        @Override
                        public int getMinPosX() {
                           return InGame_RecruitArmy.HOVER_POSX;
                        }
                     };
                  }
               }
            );
         }

         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;

         for (int o = 0; o < 3; o++) {
            for (int i = 0; i < ArmyManager.iUnitsTypesSize; i++) {
               for (int jxxxx = 0; jxxxx < ArmyManager.lArmySize.get(i); jxxxx++) {
                  if (ArmyManager.lUnitsTypes.get(i).Line == o
                     && Game.getCiv(Game.player.iCivID).isUnitResearched(i, jxxxx)
                     && !Game.getCiv(Game.player.iCivID).isUnitBest(i, jxxxx)) {
                     tempAdded++;
                     menuElements.add(new ButtonUnit3_3(i, jxxxx, paddingLeft, buttonY, menuWidth - paddingLeft * 2, true, true) {
                        @Override
                        public void actionElement() {
                           super.actionElement();
                           if (GameValues.army.CAN_RECRUIT_OBSOLETE_UNITS) {
                              if (Game.mapModes.iActiveMapModeID != Game.mapModes.MODE_RECRUIT_ARMY) {
                                 Game.gameActiveProvince.resetLastActiveProvince();
                                 Game.setActiveProvinceID(-1);
                                 Game.menuManager.setVisibleInGame_ProvinceInfo(false);
                                 Game.armyRecruit = new ArmyRecruit(0, this.getValue1(), this.getValue2(), InGame_RecruitArmy.key);
                                 Game.armyRecruit.cost = ArmyManager.getRecruitmentCost(Game.player.iCivID, -1, this.getValue1(), this.getValue2());
                                 Game.mapModes.setActiveViewID(Game.mapModes.MODE_RECRUIT_ARMY);
                              } else if (Game.armyRecruit.unitID == this.getValue1() && Game.armyRecruit.armyID == this.getValue2()) {
                                 Game.mapModes.setActiveViewID(Game.mapModes.MODE_DEFAULT);
                              } else {
                                 Game.gameActiveProvince.resetLastActiveProvince();
                                 Game.setActiveProvinceID(-1);
                                 Game.menuManager.setVisibleInGame_ProvinceInfo(false);
                                 Game.armyRecruit = new ArmyRecruit(0, this.getValue1(), this.getValue2(), InGame_RecruitArmy.key);
                                 Game.armyRecruit.cost = ArmyManager.getRecruitmentCost(Game.player.iCivID, -1, this.getValue1(), this.getValue2());
                              }
                           }
                        }
                     });
                     buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
                  }
               }
            }
         }

         if (tempAdded == 0) {
            menuElements.add(
               new Text_StaticBG(Game.lang.get("None"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, menuWidth - paddingLeft * 2, CFG.BUTTON_HEIGHT2)
            );
            buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         }

         int var20 = 0;
      }

      return menuElements;
   }

   public static void actionCreateNewArmy() {
      if (Game.mapModes.iActiveMapModeID == Game.mapModes.MODE_RECRUIT_ARMY) {
         Game.mapModes.setActiveViewID(Game.mapModes.MODE_DEFAULT);
      }

      Game.menuManager.rebuildInGame_RecruitArmy_NewArmy();
   }

   public static void actionGenerals() {
      Game.menuManager.setVisibleInGame_RecruitArmy(false);
      InGame_Generals.assignProvinceID = -1;
      InGame_Generals.assignArmyKey = "";
      Game.menuManager.rebuildInGame_Generals();
      Game.menuManager.setVisibleInGame_Generals(true);
      InGame_Generals.backButton = false;
   }

   public static void actionArmies() {
      if (Game.menuManager.getVisibleInGame_Armies()) {
         Game.menuManager.setVisibleInGame_Armies(false);
      } else {
         Game.menuManager.rebuildInGame_Armies(false, false);
         Game.menuManager.setVisibleInGame_Armies(true);
         Game.menuManager.setVisibleInGame_RecruitArmy(false);
      }
   }

   public static void actionMercenaries() {
      if (Game.menuManager.getVisibleInGame_Armies()) {
         Game.menuManager.setVisibleInGame_Armies(false);
      } else {
         Game.menuManager.rebuildInGame_Mercenaries();
         Game.menuManager.setVisibleInGame_Armies(true);
         Game.menuManager.setVisibleInGame_RecruitArmy(false);
      }
   }
}
