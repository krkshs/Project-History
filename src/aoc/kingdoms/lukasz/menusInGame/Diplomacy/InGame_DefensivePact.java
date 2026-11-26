package aoc.kingdoms.lukasz.menusInGame.Diplomacy;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.SoundsManager;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.RulersManager;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import aoc.kingdoms.lukasz.map.province.ProvinceDraw;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonFlag_Diplomacy;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame_ImageSparks;
import aoc.kingdoms.lukasz.menu_element.button.ButtonRuler_Diplomacy;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Bonuses;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Diplomacy;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Diplomacy_Flip;
import aoc.kingdoms.lukasz.menu_element.button.Button_Likelihood;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Image;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG_Center;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextBonus;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextIcon_Diplomacy;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_StaticBG;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Static_ID;
import aoc.kingdoms.lukasz.menusInGame.Civ.InGame_Civ;
import aoc.kingdoms.lukasz.textures.Image;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_DefensivePact extends Menu {
   public static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static int iCivID = 0;

   public InGame_DefensivePact(int nCivID) {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING + Images.boxTitleBORDERWIDTH;
      int titleHeight = ImageManager.getImage(Images.title600).getHeight();
      int menuWidth = ImageManager.getImage(Images.insideTop600).getWidth();
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title600).getHeight();
      int buttonYPadding = CFG.PADDING * 2;
      int buttonY = CFG.PADDING;
      iCivID = nCivID;
      int maxWidth = ImageManager.getImage(Images.defensivePactBig).getWidth() + CFG.PADDING * 4;
      int tempTitlePaddingY = CFG.PADDING;
      int tempTitleH = ImageManager.getImage(Images.flagDiplomacyOver).getHeight() + tempTitlePaddingY * 2;
      int tempTextW = menuWidth / 2 - paddingLeft - CFG.PADDING * 2 - maxWidth / 2 - ImageManager.getImage(Images.flagDiplomacyOver).getWidth();
      menuElements.add(
         new ButtonFlag_Diplomacy(
            Game.player.iCivID,
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
         new Text_Static_ID(Game.player.iCivID, Game.getCiv(Game.player.iCivID).getCivName(), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, tempTextW, tempTitleH)
      );
      menuElements.add(new TextIcon_Diplomacy(Images.defensivePactBig, paddingLeft, buttonY, menuWidth - paddingLeft * 2, tempTitleH, maxWidth) {
         @Override
         public Color getColorBar() {
            return DiplomacyManager.COLOR_DEFENSIVE_PACT;
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      int statsX = paddingLeft + ButtonRuler_Diplomacy.getButtonWidth() + CFG.PADDING * 2;
      int statsW = menuWidth / 2 - statsX - CFG.PADDING / 2;
      int statsH = (ButtonRuler_Diplomacy.getButtonHeight() - CFG.PADDING * 2) / 3;
      int maxIconW = ImageManager.getImage(Game_Calendar.IMG_MANPOWER).getWidth() + CFG.PADDING * 2;
      menuElements.add(
         new ButtonStatsRectIMG_Diplomacy(
            Game.lang.get("Opinion") + ": " + CFG.getPrecision2(Game.getCiv(iCivID).diplomacy.getRelation(Game.player.iCivID), 1),
            Images.relations,
            statsX,
            buttonY,
            statsW * 2 + CFG.PADDING / 2 * 2,
            statsH,
            maxIconW,
            0
         ) {
            @Override
            protected Color getColor(boolean isActive) {
               return Colors.getColorTopStats(isActive, this.getIsHovered());
            }

            @Override
            public void buildElementHover() {
               this.menuElementHover = InGame_Civ.getHoverBetweenCivilizations(
                  InGame_DefensivePact.iCivID,
                  Game.player.iCivID,
                  Game.getCiv(Game.player.iCivID).diplomacy.isImprovingRelations(InGame_DefensivePact.iCivID),
                  Game.getCiv(Game.player.iCivID).diplomacy.isDamagingRelations(InGame_DefensivePact.iCivID)
               );
            }
         }
      );
      menuElements.add(
         new ButtonStatsRectIMG_Diplomacy(
            "" + CFG.getShortNumber(Game.getCiv(Game.player.iCivID).iRegimentsLimit),
            Images.regimentsLimit,
            statsX,
            buttonY + CFG.PADDING + statsH,
            statsW,
            statsH,
            maxIconW,
            0
         ) {
            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("RegimentsLimit") + ": "));
               nData.add(new MenuElement_HoverElement_Type_TextTitle("" + Game.getCiv(Game.player.iCivID).iRegimentsLimit, CFG.FONT_BOLD, Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle(Images.regimentsLimit, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      menuElements.add(
         new ButtonStatsRectIMG_Diplomacy(
            "" + Game.getCiv(Game.player.iCivID).getNumOfProvinces(),
            Images.provinces,
            statsX,
            buttonY + (CFG.PADDING + statsH) * 2,
            statsW,
            statsH,
            maxIconW,
            0
         ) {
            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Provinces") + ": "));
               nData.add(new MenuElement_HoverElement_Type_TextTitle(this.getText(), CFG.FONT_BOLD, Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle(Images.provinces, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      menuElements.add(
         new ButtonStatsRectIMG_Diplomacy_Flip(
            "" + CFG.getShortNumber(Game.getCiv(iCivID).iRegimentsLimit),
            Images.regimentsLimit,
            statsX + CFG.PADDING + statsW,
            buttonY + CFG.PADDING + statsH,
            statsW,
            statsH,
            maxIconW,
            0
         ) {
            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("RegimentsLimit") + ": "));
               nData.add(
                  new MenuElement_HoverElement_Type_TextTitle("" + Game.getCiv(InGame_DefensivePact.iCivID).iRegimentsLimit, CFG.FONT_BOLD, Colors.HOVER_GOLD)
               );
               nData.add(new MenuElement_HoverElement_Type_ImageTitle(Images.regimentsLimit, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      menuElements.add(
         new ButtonStatsRectIMG_Diplomacy_Flip(
            "" + Game.getCiv(iCivID).getNumOfProvinces(),
            Images.provinces,
            statsX + CFG.PADDING + statsW,
            buttonY + (CFG.PADDING + statsH) * 2,
            statsW,
            statsH,
            maxIconW,
            0
         ) {
            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Provinces") + ": "));
               nData.add(new MenuElement_HoverElement_Type_TextTitle(this.getText(), CFG.FONT_BOLD, Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle(Images.provinces, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      RulersManager.loadRulerIMG_DiplomacyLeft(Game.player.iCivID);
      RulersManager.loadRulerIMG_DiplomacyRight(iCivID);
      menuElements.add(new ButtonRuler_Diplomacy(Game.player.iCivID, paddingLeft + CFG.PADDING, buttonY));
      menuElements.add(new ButtonRuler_Diplomacy(iCivID, menuWidth - paddingLeft - ButtonRuler_Diplomacy.getButtonWidth() - CFG.PADDING, buttonY) {
         @Override
         public Image getRulerImage() {
            return RulersManager.rulerIMG_DiplomacyRight;
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      int iconWidth = (int)Math.ceil(ImageManager.getImage(Images.gold).getWidth() * 1.5F);
      menuElements.add(
         new TextBonus(
            Game_Calendar.getDate_ByTurnID(Game_Calendar.TURN_ID + GameValues.diplomacy.DIPLOMACY_DEFENSIVE_PACT_EXPIRES),
            "",
            Images.time,
            paddingLeft,
            buttonY,
            (menuWidth - paddingLeft * 2 - CFG.PADDING / 2 * 2) / 2,
            CFG.TEXT_HEIGHT + CFG.PADDING * 5,
            iconWidth
         ) {
            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     Game.lang.get("DefensivePact"), "", Images.defensivePact, CFG.FONT_BOLD_SMALL, CFG.FONT_BOLD_SMALL, Colors.HOVER_LEFT, Colors.HOVER_GOLD
                  )
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     Game.lang.get("Expires") + ": ",
                     Game_Calendar.getDate_ByTurnID(Game_Calendar.TURN_ID + GameValues.diplomacy.DIPLOMACY_DEFENSIVE_PACT_EXPIRES),
                     Images.time,
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
         new TextBonus(
            Game.lang.get("Cost") + ": ",
            "" + CFG.getPrecision2(GameValues.diplomacy.DIPLOMACY_DEFENSIVE_PACT_COST, 100),
            Images.diplomacy,
            paddingLeft + CFG.PADDING + (menuWidth - paddingLeft * 2 - CFG.PADDING / 2 * 2) / 2,
            buttonY,
            (menuWidth - paddingLeft * 2 - CFG.PADDING / 2 * 2) / 2,
            CFG.TEXT_HEIGHT + CFG.PADDING * 5,
            iconWidth
         ) {
            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(
                  new MenuElement_HoverElement_Type_Button_TextBonus(
                     Game.lang.get("DiplomacyPoints") + ": ",
                     "" + CFG.getPrecision2(GameValues.diplomacy.DIPLOMACY_DEFENSIVE_PACT_COST, 100),
                     Images.diplomacy,
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
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      int score = DiplomacyManager.getDefensivePact_Score(Game.player.iCivID, iCivID);
      menuElements.add(
         new Button_Likelihood(
            Game.lang.get("Score") + ": ",
            "" + score,
            paddingLeft,
            buttonY,
            menuWidth - paddingLeft * 2,
            CFG.BUTTON_HEIGHT / 2,
            score > 0 ? 0.5F + Math.min(0.49F, Math.abs(score) / 100.0F) : 0.49F - Math.min(0.48F, Math.abs(score) / 100.0F),
            score > 0 ? Images.v : Images.x
         ) {
            @Override
            public void buildElementHover() {
               this.menuElementHover = InGame_DefensivePact.getHover();
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      if (score < 0) {
         menuElements.add(
            new ButtonStatsRectIMG_Bonuses(
               Game.lang.get("LikelihoodOfSuccess") + ": ",
               "0%",
               Images.x,
               paddingLeft,
               buttonY,
               menuWidth - paddingLeft * 2,
               CFG.BUTTON_HEIGHT3,
               ImageManager.getImage(Images.x).getWidth(),
               CFG.FONT_REGULAR_SMALL,
               CFG.FONT_BOLD_SMALL
            ) {
               @Override
               public Color getColorBonus() {
                  return Colors.getColorNegative(false, this.getIsHovered());
               }

               @Override
               public void buildElementHover() {
                  this.menuElementHover = InGame_DefensivePact.getHover();
               }
            }
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         menuElements.add(
            new Text_StaticBG(
               Game.lang.get("Tip") + ": " + Game.lang.get("ImproveRelations"),
               CFG.FONT_REGULAR,
               -1,
               paddingLeft,
               buttonY,
               menuWidth - paddingLeft * 2,
               CFG.TEXT_HEIGHT + CFG.PADDING * 4
            ) {
               @Override
               protected Color getColor(boolean isActive) {
                  return Colors.HOVER_POSITIVE;
               }

               @Override
               public void buildElementHover() {
                  this.menuElementHover = InGame_DefensivePact.getHover();
               }
            }
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      }

      paddingLeft += CFG.PADDING;
      menuElements.add(
         new ButtonGame(Game.lang.get("Cancel"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, (menuWidth - paddingLeft * 2 - CFG.PADDING / 2 * 2) / 2, true) {
            @Override
            public void actionElement() {
               Game.menuManager.setVisibleInGame_PopUp(false);
            }
         }
      );
      menuElements.add(
         new ButtonGame_ImageSparks(
            Game.lang.get("SendProposal"),
            CFG.FONT_REGULAR,
            -1,
            paddingLeft + CFG.PADDING + (menuWidth - paddingLeft * 2 - CFG.PADDING / 2 * 2) / 2,
            buttonY,
            (menuWidth - paddingLeft * 2 - CFG.PADDING / 2 * 2) / 2,
            true,
            Images.defensivePact
         ) {
            @Override
            public void actionElement() {
               InGame_DefensivePact.confirm();
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("SendProposal"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
               nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.defensivePact, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("DiplomacyPoints") + ": ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_LEFT));
               nData.add(
                  new MenuElement_HoverElement_Type_Text(
                     "-" + CFG.getPrecision2(GameValues.diplomacy.DIPLOMACY_DEFENSIVE_PACT_COST, 100), CFG.FONT_BOLD_SMALL, Colors.HOVER_NEGATIVE
                  )
               );
               nData.add(new MenuElement_HoverElement_Type_Image(Images.diplomacy, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Expires") + ": ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_LEFT));
               nData.add(
                  new MenuElement_HoverElement_Type_Text(
                     "" + Game_Calendar.getDate_ByTurnID(Game_Calendar.TURN_ID + GameValues.diplomacy.DIPLOMACY_DEFENSIVE_PACT_EXPIRES),
                     CFG.FONT_BOLD_SMALL,
                     Colors.HOVER_GOLD
                  )
               );
               nData.add(new MenuElement_HoverElement_Type_Image(Images.time, CFG.PADDING, 0));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }

            @Override
            public int getSFX() {
               return SoundsManager.DIPLOMACY_CLICK;
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING * 2;
      int menuHeight = Math.min(buttonY, CFG.GAME_HEIGHT - titleHeight - menuY);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, buttonY)));
      this.initMenu(
         new MenuTitleIMG(Game.lang.get("FormDefensivePact"), true, false, Images.title600) {
            @Override
            public long getTime() {
               return InGame_DefensivePact.lTime;
            }
         },
         CFG.GAME_WIDTH / 2 - menuWidth / 2,
         Math.min((int)(CFG.GAME_HEIGHT * 0.2F), CFG.GAME_HEIGHT / 2 - (menuHeight + titleHeight) / 2),
         menuWidth,
         menuHeight,
         menuElements,
         false,
         true
      );
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      DiplomacyManager.updateInAnimation();
      if (lTime + 60L >= CFG.currentTimeMillis) {
         iTranslateY = iTranslateY - CFG.BUTTON_HEIGHT * 3 / 5 + (int)(CFG.BUTTON_HEIGHT * 3 / 5 * ((float)(CFG.currentTimeMillis - lTime) / 60.0F));
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

   public static void confirm() {
      if (Game.getCiv(Game.player.iCivID).fDiplomacy < GameValues.diplomacy.DIPLOMACY_DEFENSIVE_PACT_COST) {
         Game.menuManager
            .addToastInsufficient(
               Game.lang.get("Cost") + ", " + Game.lang.get("DiplomacyPoints") + ": ",
               CFG.getPrecision2(GameValues.diplomacy.DIPLOMACY_DEFENSIVE_PACT_COST, 100),
               Images.diplomacy
            );
      } else {
         DiplomacyManager.offerDefensivePact(Game.player.iCivID, iCivID);
         Game.menuManager.setVisibleInGame_PopUp(false);
         ProvinceDraw.addDiplomacyLines(
            Game.getCiv(Game.player.iCivID).getCapitalProvinceID(), Game.getCiv(iCivID).getCapitalProvinceID(), DiplomacyManager.COLOR_DEFENSIVE_PACT
         );
      }
   }

   public static MenuElement_Hover getHover() {
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      int score = DiplomacyManager.getDefensivePact_Score(Game.player.iCivID, iCivID);
      nData.add(new MenuElement_HoverElement_Type_TextTitle_BG_Center(Game.lang.get("DefensivePact"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(
         new MenuElement_HoverElement_Type_Button_TextBonus(
            Game.lang.get("LikelihoodOfSuccess") + ": ",
            score <= 0 ? "0%" : Game.lang.get("High"),
            Images.defensivePact,
            CFG.FONT_REGULAR_SMALL,
            CFG.FONT_BOLD_SMALL,
            Colors.HOVER_LEFT,
            score <= 0 ? Colors.HOVER_NEGATIVE : Colors.HOVER_POSITIVE
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(
         new MenuElement_HoverElement_Type_Button_TextBonus(
            Game.lang.get("Score") + ": ", "" + score, Images.defensivePact, CFG.FONT_REGULAR_SMALL, CFG.FONT_BOLD_SMALL, Colors.HOVER_LEFT, Colors.HOVER_GOLD
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Line());
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("BaseValue") + ": ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_LEFT));
      nData.add(
         new MenuElement_HoverElement_Type_Text(
            "" + CFG.getPrecision2(DiplomacyManager.getDefensivePact_Score_BASE_VALUE(Game.player.iCivID, iCivID), 100), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Distance") + ": ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_LEFT));
      nData.add(
         new MenuElement_HoverElement_Type_Text(
            ""
               + CFG.getPrecision2(
                  GameValues.diplomacy.DEFENSIVE_PACT_SCORE_DISTANCE
                     * Game.getDistance_PercOfMax(Game.getCiv(Game.player.iCivID).getCapitalProvinceID(), Game.getCiv(iCivID).getCapitalProvinceID()),
                  100
               ),
            CFG.FONT_BOLD_SMALL,
            Colors.HOVER_GOLD
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      if (Game.getCiv(Game.player.iCivID).getIdeologyID() != Game.getCiv(iCivID).getIdeologyID()) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Government") + ": ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_LEFT));
         if (Game.ideologiesManager.getIdeology(Game.getCiv(Game.player.iCivID).getIdeologyID()).GOV_GROUP_ID
            != Game.ideologiesManager.getIdeology(Game.getCiv(iCivID).getIdeologyID()).GOV_GROUP_ID) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  CFG.getPrecision2(GameValues.diplomacy.DEFENSIVE_PACT_SCORE_DIFFERENT_GOVERNMENT_GROUP, 100), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD
               )
            );
         } else {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  CFG.getPrecision2(GameValues.diplomacy.DEFENSIVE_PACT_SCORE_DIFFERENT_GOVERNMENT, 100), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD
               )
            );
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Images.government, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (Game.getCiv(Game.player.iCivID).getReligionID() != Game.getCiv(iCivID).getReligionID()) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Religion") + ": ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_LEFT));
         if (Game.religionManager.getReligion(Game.getCiv(Game.player.iCivID).getReligionID()).ReligionGroupID
            != Game.religionManager.getReligion(Game.getCiv(iCivID).getReligionID()).ReligionGroupID) {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  CFG.getPrecision2(GameValues.diplomacy.DEFENSIVE_PACT_SCORE_DIFFERENT_RELIGION_GROUP, 100), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD
               )
            );
         } else {
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  CFG.getPrecision2(GameValues.diplomacy.DEFENSIVE_PACT_SCORE_DIFFERENT_RELIGION, 100), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD
               )
            );
         }

         nData.add(new MenuElement_HoverElement_Type_Image(Images.religion, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (Game.getCiv(Game.player.iCivID).diplomacy.isAtWar()) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("AtWar") + ": ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_LEFT));
         nData.add(
            new MenuElement_HoverElement_Type_Text(
               "" + CFG.getPrecision2(GameValues.diplomacy.DEFENSIVE_PACT_SCORE_AT_WAR, 10), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD
            )
         );
         nData.add(new MenuElement_HoverElement_Type_Image(Images.relations, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Relations") + ": ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_LEFT));
      nData.add(
         new MenuElement_HoverElement_Type_Text(
            "" + CFG.getPrecision2(Game.getCiv(iCivID).diplomacy.getRelation(Game.player.iCivID) * GameValues.diplomacy.DEFENSIVE_PACT_SCORE_PER_RELATION, 10),
            CFG.FONT_BOLD_SMALL,
            Colors.HOVER_GOLD
         )
      );
      nData.add(new MenuElement_HoverElement_Type_Image(Images.relations, CFG.PADDING, 0));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      float tScore = DiplomacyManager.getDefensivePact_Score_SameRivals(Game.player.iCivID, iCivID);
      if (tScore > 0.0F) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Rivals") + ": ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_LEFT));
         nData.add(new MenuElement_HoverElement_Type_Text("" + CFG.getPrecision2(tScore, 10), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD));
         nData.add(new MenuElement_HoverElement_Type_Image(Images.rivals, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      tScore = Game.getCiv(Game.player.iCivID).getAggressiveExpansion();
      if (tScore > 0.0F) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("AggressiveExpansion") + ": ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_LEFT));
         nData.add(
            new MenuElement_HoverElement_Type_Text(
               "" + CFG.getPrecision2(tScore * GameValues.diplomacy.DEFENSIVE_PACT_SCORE_PER_AGGRESSIVE_EXPANSION, 10), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD
            )
         );
         nData.add(new MenuElement_HoverElement_Type_Image(Images.aggressiveExpansion, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (Game.getCiv(Game.player.iCivID).diplomacy.isRival(iCivID)) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Rival") + ": ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_LEFT));
         nData.add(new MenuElement_HoverElement_Type_Text("" + Game.getCiv(iCivID).getCivName(), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD));
         nData.add(new MenuElement_HoverElement_Type_Image(Images.rivals, CFG.PADDING, CFG.PADDING));
         nData.add(
            new MenuElement_HoverElement_Type_Text(
               "" + CFG.getPrecision2(GameValues.diplomacy.DEFENSIVE_PACT_SCORE_RIVALS, 10), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (Game.getCiv(iCivID).diplomacy.isRival(Game.player.iCivID)) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Rival") + ": ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_LEFT));
         nData.add(new MenuElement_HoverElement_Type_Text("" + Game.getCiv(Game.player.iCivID).getCivName(), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD));
         nData.add(new MenuElement_HoverElement_Type_Image(Images.rivals, CFG.PADDING, CFG.PADDING));
         nData.add(
            new MenuElement_HoverElement_Type_Text(
               "" + CFG.getPrecision2(GameValues.diplomacy.DEFENSIVE_PACT_SCORE_RIVALS, 10), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (!Game.getCiv(Game.player.iCivID).diplomacy.defensivePact.isEmpty()) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("DefensivePacts") + ": ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_LEFT));
         nData.add(
            new MenuElement_HoverElement_Type_Text("" + Game.getCiv(Game.player.iCivID).diplomacy.defensivePact.size(), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD)
         );
         nData.add(new MenuElement_HoverElement_Type_Image(Images.defensivePact, CFG.PADDING, CFG.PADDING));
         nData.add(
            new MenuElement_HoverElement_Type_Text(
               ""
                  + CFG.getPrecision2(
                     Game.getCiv(Game.player.iCivID).diplomacy.defensivePact.size() * GameValues.diplomacy.DEFENSIVE_PACT_SCORE_PER_DEFENSIVE_PACT, 10
                  ),
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      if (!Game.getCiv(iCivID).diplomacy.defensivePact.isEmpty()) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("DefensivePacts") + ": ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_LEFT));
         nData.add(new MenuElement_HoverElement_Type_Text("" + Game.getCiv(iCivID).diplomacy.defensivePact.size(), CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD));
         nData.add(new MenuElement_HoverElement_Type_Image(Images.defensivePact, CFG.PADDING, CFG.PADDING));
         nData.add(
            new MenuElement_HoverElement_Type_Text(
               "" + CFG.getPrecision2(Game.getCiv(iCivID).diplomacy.defensivePact.size() * GameValues.diplomacy.DEFENSIVE_PACT_SCORE_PER_DEFENSIVE_PACT, 10),
               CFG.FONT_BOLD_SMALL,
               Colors.HOVER_GOLD
            )
         );
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      return new MenuElement_Hover(nElements);
   }
}
