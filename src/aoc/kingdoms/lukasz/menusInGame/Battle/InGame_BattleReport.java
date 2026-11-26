package aoc.kingdoms.lukasz.menusInGame.Battle;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.RulersManager;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonFlag_Diplomacy;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame_Style;
import aoc.kingdoms.lukasz.menu_element.button.ButtonRuler_Diplomacy;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Diplomacy;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Diplomacy_Flip;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextIcon_Diplomacy;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID_Special;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG_ID_SpecialWarscore;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Static_ID;
import aoc.kingdoms.lukasz.textures.Image;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_BattleReport extends Menu {
   public static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static Color colorBar = Color.WHITE;

   public InGame_BattleReport(int reportID) {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING + Images.boxTitleBORDERWIDTH;
      int titleHeight = ImageManager.getImage(Images.title600).getHeight();
      int menuWidth = ImageManager.getImage(Images.insideTop600).getWidth();
      int menuX = CFG.PADDING * 2;
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title1Red).getHeight();
      int buttonYPadding = CFG.PADDING * 2;
      int buttonY = CFG.PADDING;
      int buttonX = paddingLeft;

      try {
         int iCivIDLeft = Game.player.lBattleReports.get(reportID).civReportLeft.iCivID;
         int iCivID = Game.player.lBattleReports.get(reportID).civReportRight.iCivID;
         int maxWidth = ImageManager.getImage(Images.battleBig).getWidth() + CFG.PADDING * 4;
         int tempTitlePaddingY = CFG.PADDING;
         int tempTitleH = ImageManager.getImage(Images.flagDiplomacyOver).getHeight() + tempTitlePaddingY * 2;
         int tempTextW = menuWidth / 2 - paddingLeft - CFG.PADDING * 2 - maxWidth / 2 - ImageManager.getImage(Images.flagDiplomacyOver).getWidth();
         if (Game.player.lBattleReports.get(reportID).playerWon) {
            colorBar = DiplomacyManager.COLOR_GREEN;
         } else {
            colorBar = DiplomacyManager.COLOR_RED;
         }

         menuElements.add(
            new ButtonFlag_Diplomacy(
               iCivIDLeft,
               menuWidth / 2 - maxWidth / 2 - CFG.PADDING * 2 - ImageManager.getImage(Images.flagDiplomacyOver).getWidth(),
               buttonY + tempTitlePaddingY,
               true
            )
         );
         menuElements.add(new ButtonFlag_Diplomacy(iCivID, menuWidth / 2 + maxWidth / 2 + CFG.PADDING * 2, buttonY + tempTitlePaddingY, true));
         menuElements.add(
            new Text_Static_ID(
               iCivID,
               Game.getCiv(iCivID).getCivName(),
               CFG.FONT_REGULAR,
               -1,
               menuWidth / 2 + maxWidth / 2 + CFG.PADDING * 2 + ImageManager.getImage(Images.flagDiplomacyOver).getWidth(),
               buttonY,
               tempTextW,
               tempTitleH
            )
         );
         menuElements.add(
            new Text_Static_ID(iCivIDLeft, Game.getCiv(iCivIDLeft).getCivName(), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, tempTextW, tempTitleH)
         );
         menuElements.add(new TextIcon_Diplomacy(Images.battleBig, paddingLeft, buttonY, menuWidth - paddingLeft * 2, tempTitleH, maxWidth) {
            @Override
            public Color getColorBar() {
               return InGame_BattleReport.colorBar;
            }
         });
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         int statsX = paddingLeft + ButtonRuler_Diplomacy.getButtonWidth() + CFG.PADDING;
         int statsW = menuWidth / 2 - statsX - CFG.PADDING / 2;
         int statsH = (ButtonRuler_Diplomacy.getButtonHeight() - CFG.PADDING * 3) / 4;
         int maxIconW = ImageManager.getImage(Game_Calendar.IMG_MANPOWER).getWidth() + CFG.PADDING * 2;
         menuElements.add(
            new Text_StaticBG_ID_SpecialWarscore(
               "" + Game.lang.get("WarScore") + ": ",
               CFG.getPrecision2(Game.player.lBattleReports.get(reportID).fWarScore, 100) + "%",
               CFG.FONT_REGULAR,
               -1,
               statsX,
               buttonY,
               statsW * 2 + CFG.PADDING,
               statsH,
               0
            )
         );
         menuElements.add(
            new ButtonStatsRectIMG_Diplomacy(
               "" + CFG.getShortNumber(Game.player.lBattleReports.get(reportID).civReportLeft.iSoldiers),
               Game_Calendar.IMG_MANPOWER,
               statsX,
               buttonY + CFG.PADDING + statsH,
               statsW,
               statsH,
               maxIconW,
               Game.player.lBattleReports.get(reportID).civReportLeft.iSoldiers
            ) {
               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Soldiers") + ": ", CFG.FONT_BOLD));
                  nData.add(new MenuElement_HoverElement_Type_TextTitle(CFG.getNumberWithSpaces("" + this.id), CFG.FONT_BOLD, Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle(Game_Calendar.IMG_MANPOWER, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements);
               }
            }
         );
         menuElements.add(
            new ButtonStatsRectIMG_Diplomacy(
               "" + CFG.getShortNumber(Game.player.lBattleReports.get(reportID).civReportLeft.iCasualties),
               Images.skull,
               statsX,
               buttonY + (CFG.PADDING + statsH) * 2,
               statsW,
               statsH,
               maxIconW,
               Game.player.lBattleReports.get(reportID).civReportLeft.iCasualties
            ) {
               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Casualties") + ": ", CFG.FONT_BOLD));
                  nData.add(
                     new MenuElement_HoverElement_Type_TextTitle(CFG.getNumberWithSpaces("" + this.id), CFG.FONT_BOLD, Colors.COLOR_TEXT_MODIFIER_NEGATIVE)
                  );
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle(Images.skull, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements);
               }
            }
         );
         menuElements.add(
            new ButtonStatsRectIMG_Diplomacy(
               "" + CFG.getShortNumber(Game.player.lBattleReports.get(reportID).civReportLeft.iRetreated),
               Images.retreat,
               statsX,
               buttonY + (CFG.PADDING + statsH) * 3,
               statsW,
               statsH,
               maxIconW,
               Game.player.lBattleReports.get(reportID).civReportLeft.iRetreated
            ) {
               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Retreated") + ": ", CFG.FONT_BOLD));
                  nData.add(new MenuElement_HoverElement_Type_TextTitle(CFG.getNumberWithSpaces("" + this.id), CFG.FONT_BOLD, Colors.HOVER_RIGHT));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle(Images.retreat, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements);
               }
            }
         );
         menuElements.add(
            new ButtonStatsRectIMG_Diplomacy_Flip(
               "" + CFG.getShortNumber(Game.player.lBattleReports.get(reportID).civReportRight.iSoldiers),
               Game_Calendar.IMG_MANPOWER,
               statsX + CFG.PADDING + statsW,
               buttonY + CFG.PADDING + statsH,
               statsW,
               statsH,
               maxIconW,
               Game.player.lBattleReports.get(reportID).civReportRight.iSoldiers
            ) {
               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Soldiers") + ": ", CFG.FONT_BOLD));
                  nData.add(new MenuElement_HoverElement_Type_TextTitle(CFG.getNumberWithSpaces("" + this.id), CFG.FONT_BOLD, Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle(Game_Calendar.IMG_MANPOWER, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements);
               }
            }
         );
         menuElements.add(
            new ButtonStatsRectIMG_Diplomacy_Flip(
               "" + CFG.getShortNumber(Game.player.lBattleReports.get(reportID).civReportRight.iCasualties),
               Images.skull,
               statsX + CFG.PADDING + statsW,
               buttonY + (CFG.PADDING + statsH) * 2,
               statsW,
               statsH,
               maxIconW,
               Game.player.lBattleReports.get(reportID).civReportRight.iCasualties
            ) {
               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Casualties") + ": ", CFG.FONT_BOLD));
                  nData.add(
                     new MenuElement_HoverElement_Type_TextTitle(CFG.getNumberWithSpaces("" + this.id), CFG.FONT_BOLD, Colors.COLOR_TEXT_MODIFIER_NEGATIVE)
                  );
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle(Images.skull, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements);
               }
            }
         );
         menuElements.add(
            new ButtonStatsRectIMG_Diplomacy_Flip(
               "" + CFG.getShortNumber(Game.player.lBattleReports.get(reportID).civReportRight.iRetreated),
               Images.retreat,
               statsX + CFG.PADDING + statsW,
               buttonY + (CFG.PADDING + statsH) * 3,
               statsW,
               statsH,
               maxIconW,
               Game.player.lBattleReports.get(reportID).civReportRight.iRetreated
            ) {
               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Retreated") + ": ", CFG.FONT_BOLD));
                  nData.add(new MenuElement_HoverElement_Type_TextTitle(CFG.getNumberWithSpaces("" + this.id), CFG.FONT_BOLD, Colors.HOVER_RIGHT));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle(Images.retreat, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements);
               }
            }
         );
         RulersManager.loadRulerIMG_DiplomacyLeft(iCivIDLeft);
         RulersManager.loadRulerIMG_DiplomacyRight(iCivID);
         menuElements.add(new ButtonRuler_Diplomacy(iCivIDLeft, buttonX, buttonY));
         menuElements.add(new ButtonRuler_Diplomacy(iCivID, menuWidth - paddingLeft - ButtonRuler_Diplomacy.getButtonWidth(), buttonY) {
            @Override
            public Image getRulerImage() {
               return RulersManager.rulerIMG_DiplomacyRight;
            }
         });
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         paddingLeft += CFG.PADDING;
         menuElements.add(
            new Text_StaticBG_ID_Special(
               Game.lang.get("Victorious"),
               CFG.FONT_BOLD,
               -1,
               Game.player.lBattleReports.get(reportID).leftSideWon
                  ? paddingLeft
                  : paddingLeft + CFG.PADDING + (menuWidth - paddingLeft * 2 - CFG.PADDING / 2 * 2) / 2,
               buttonY,
               (menuWidth - paddingLeft * 2 - CFG.PADDING / 2 * 2) / 2,
               CFG.BUTTON_HEIGHT2,
               0
            ) {
               @Override
               protected Color getColor(boolean isActive) {
                  return Colors.getColorPositive(isActive, this.getIsHovered());
               }
            }
         );
         menuElements.add(
            new ButtonGame_Style(
               Game.lang.get("Close"),
               CFG.FONT_REGULAR,
               -1,
               Game.player.lBattleReports.get(reportID).leftSideWon
                  ? paddingLeft + CFG.PADDING + (menuWidth - paddingLeft * 2 - CFG.PADDING / 2 * 2) / 2
                  : paddingLeft,
               buttonY,
               (menuWidth - paddingLeft * 2 - CFG.PADDING / 2 * 2) / 2,
               true
            ) {
               @Override
               public void actionElement() {
                  Game.menuManager.setVisibleInGame_PopUp(false);
               }
            }
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING * 2;
         int menuHeight = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - CFG.PADDING * 3);
         menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, buttonY)));
         this.initMenu(
            new MenuTitleIMG(
               Game.lang.get("BattleOf", Game.getProvince(Game.player.lBattleReports.get(reportID).iProvinceID).getProvinceName()),
               true,
               false,
               Images.title600
            ) {
               @Override
               public long getTime() {
                  return InGame_BattleReport.lTime;
               }
            },
            CFG.GAME_WIDTH / 2 - menuWidth / 2,
            menuY,
            menuWidth,
            menuHeight,
            menuElements,
            false,
            true
         );
      } catch (Exception var22) {
         CFG.exceptionStack(var22);
      }
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      DiplomacyManager.updateInAnimation();
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
      ImageManager.getImage(Images.civInfoOver)
         .draw2(
            oSB,
            this.getPosX() + iTranslateX,
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
      DiplomacyManager.updateAnimationTime();
   }
}
