package aoc.kingdoms.lukasz.menusInGame.DiplomacyMessage;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Player.MessageTypes.PMessage;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.RulersManager;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import aoc.kingdoms.lukasz.map.war.War;
import aoc.kingdoms.lukasz.map.war.WarManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG_Message;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonFlag_Diplomacy;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame;
import aoc.kingdoms.lukasz.menu_element.button.ButtonRuler_Diplomacy;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Diplomacy;
import aoc.kingdoms.lukasz.menu_element.button.ButtonStatsRectIMG_Diplomacy_Flip;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextIcon_Diplomacy;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Static_ID;
import aoc.kingdoms.lukasz.menusInGame.Civ.InGame_Civ;
import aoc.kingdoms.lukasz.textures.Image;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_MessageCallToWar extends Menu {
   public static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static int iCivID = 0;
   public static int civRight = 0;
   public static String key;

   public InGame_MessageCallToWar(PMessage message) {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING + Images.boxTitleBORDERWIDTH;
      int titleHeight = ImageManager.getImage(Images.title600).getHeight();
      int menuWidth = ImageManager.getImage(Images.insideTop600).getWidth();
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title600).getHeight();
      int buttonYPadding = CFG.PADDING * 2;
      int buttonY = CFG.PADDING;
      key = message.key;
      if (message != null) {
         iCivID = message.fromCivID;
         civRight = 0;
         War war = WarManager.lWars.get(message.key);
         if (war != null) {
            if (war.isAggressor(iCivID)) {
               civRight = war.lDefenders.get(0).iCivID;
            } else {
               civRight = war.lAggressors.get(0).iCivID;
            }
         }

         int maxWidth = ImageManager.getImage(Images.giftBig).getWidth() + CFG.PADDING * 4;
         int tempTitlePaddingY = CFG.PADDING;
         int tempTitleH = ImageManager.getImage(Images.flagDiplomacyOver).getHeight() + tempTitlePaddingY * 2;
         int tempTextW = menuWidth / 2 - paddingLeft - CFG.PADDING * 2 - maxWidth / 2 - ImageManager.getImage(Images.flagDiplomacyOver).getWidth();
         menuElements.add(
            new ButtonFlag_Diplomacy(
               iCivID,
               menuWidth / 2 - maxWidth / 2 - CFG.PADDING * 2 - ImageManager.getImage(Images.flagDiplomacyOver).getWidth(),
               buttonY + tempTitlePaddingY,
               true
            )
         );
         menuElements.add(new ButtonFlag_Diplomacy(civRight, menuWidth / 2 + maxWidth / 2 + CFG.PADDING * 2, buttonY + tempTitlePaddingY, true));
         menuElements.add(
            new Text_Static_ID(
               civRight,
               Game.getCiv(civRight).getCivName(),
               CFG.FONT_REGULAR,
               -1,
               menuWidth / 2 + maxWidth / 2 + CFG.PADDING * 2 + ImageManager.getImage(Images.flagDiplomacyOver).getWidth(),
               buttonY,
               tempTextW,
               tempTitleH
            )
         );
         menuElements.add(new Text_Static_ID(iCivID, Game.getCiv(iCivID).getCivName(), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, tempTextW, tempTitleH));
         menuElements.add(new TextIcon_Diplomacy(Images.warBig, paddingLeft, buttonY, menuWidth - paddingLeft * 2, tempTitleH, maxWidth) {
            @Override
            public Color getColorBar() {
               return DiplomacyManager.COLOR_WAR;
            }
         });
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         int statsX = paddingLeft + ButtonRuler_Diplomacy.getButtonWidth() + CFG.PADDING * 2;
         int statsW = menuWidth / 2 - statsX - CFG.PADDING / 2;
         int statsH = (ButtonRuler_Diplomacy.getButtonHeight() - CFG.PADDING * 2) / 3;
         int maxIconW = ImageManager.getImage(Game_Calendar.IMG_MANPOWER).getWidth() + CFG.PADDING * 2;
         menuElements.add(
            new ButtonStatsRectIMG_Diplomacy(Game.lang.get("AtWar"), Images.relations, statsX, buttonY, statsW * 2 + CFG.PADDING / 2 * 2, statsH, maxIconW, 0) {
               @Override
               protected Color getColor(boolean isActive) {
                  return Colors.getColorTopStats(isActive, this.getIsHovered());
               }

               @Override
               public void buildElementHover() {
                  this.menuElementHover = InGame_Civ.getHoverBetweenCivilizations(
                     InGame_MessageCallToWar.iCivID,
                     InGame_MessageCallToWar.civRight,
                     Game.getCiv(InGame_MessageCallToWar.civRight).diplomacy.isImprovingRelations(InGame_MessageCallToWar.iCivID),
                     Game.getCiv(InGame_MessageCallToWar.civRight).diplomacy.isDamagingRelations(InGame_MessageCallToWar.iCivID)
                  );
               }
            }
         );
         menuElements.add(
            new ButtonStatsRectIMG_Diplomacy(
               "" + Game.getCiv(iCivID).getNumOfProvinces(), Images.provinces, statsX, buttonY + CFG.PADDING + statsH, statsW, statsH, maxIconW, 0
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
               "" + Game.getCiv(civRight).getNumOfProvinces(),
               Images.provinces,
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
            new ButtonStatsRectIMG_Diplomacy(
               Game_Calendar.getDate_ByTurnID(Game_Calendar.TURN_ID + GameValues.diplomacy.DIPLOMACY_ALLIANCE_EXPIRES),
               Images.time,
               statsX,
               buttonY + (CFG.PADDING + statsH) * 2,
               statsW * 2 + CFG.PADDING,
               statsH,
               maxIconW,
               0
            ) {
               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        Game.lang.get("CallToArms"), "", Images.alliance, CFG.FONT_BOLD_SMALL, CFG.FONT_BOLD_SMALL, Colors.HOVER_LEFT, Colors.HOVER_GOLD
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        Game.lang.get("Expires") + ": ",
                        this.getText(),
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
         RulersManager.loadRulerIMG_DiplomacyLeft(iCivID);
         RulersManager.loadRulerIMG_DiplomacyRight(civRight);
         menuElements.add(new ButtonRuler_Diplomacy(iCivID, paddingLeft + CFG.PADDING, buttonY));
         menuElements.add(new ButtonRuler_Diplomacy(civRight, menuWidth - paddingLeft - ButtonRuler_Diplomacy.getButtonWidth() - CFG.PADDING, buttonY) {
            @Override
            public Image getRulerImage() {
               return RulersManager.rulerIMG_DiplomacyRight;
            }
         });
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         paddingLeft += CFG.PADDING;
         menuElements.add(
            new ButtonGame(Game.lang.get("Refuse"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, (menuWidth - paddingLeft * 2 - CFG.PADDING / 2 * 2) / 2, true) {
               @Override
               public void actionElement() {
                  PMessage tMessage = Game.player.getMessage(InGame_MessageCallToWar.key);
                  if (tMessage != null) {
                     tMessage.onAccept();
                  }

                  Game.menuManager.setVisibleInGame_PopUp(false);
                  Game.player.removeMessage(InGame_MessageCallToWar.key);
               }

               @Override
               public void buildElementHover() {
                  PMessage tMessage = Game.player.getMessage(InGame_MessageCallToWar.key);
                  if (tMessage != null) {
                     List<MenuElement_HoverElement> nElements = new ArrayList<>();
                     List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                     nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Refuse"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
                     nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.war, CFG.PADDING, 0));
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     nData.add(
                        new MenuElement_HoverElement_Type_Button_TextBonus(
                           Game.lang.get("Legacy"),
                           "" + CFG.getPrecision2(GameValues.diplomacy.CALL_TO_ARMS_REFUSE_LEGACY, 100),
                           Images.legacy,
                           CFG.FONT_BOLD_SMALL,
                           CFG.FONT_BOLD_SMALL,
                           Colors.HOVER_LEFT,
                           Colors.HOVER_NEGATIVE
                        )
                     );
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     this.menuElementHover = new MenuElement_Hover(nElements);
                  } else {
                     this.menuElementHover = null;
                  }
               }
            }
         );
         menuElements.add(
            new ButtonGame(
               Game.lang.get("Accept"),
               CFG.FONT_REGULAR,
               -1,
               paddingLeft + CFG.PADDING + (menuWidth - paddingLeft * 2 - CFG.PADDING / 2 * 2) / 2,
               buttonY,
               (menuWidth - paddingLeft * 2 - CFG.PADDING / 2 * 2) / 2,
               true
            ) {
               @Override
               public void actionElement() {
                  InGame_MessageCallToWar.confirm();
               }

               @Override
               public void buildElementHover() {
                  PMessage tMessage = Game.player.getMessage(InGame_MessageCallToWar.key);
                  if (tMessage != null) {
                     List<MenuElement_HoverElement> nElements = new ArrayList<>();
                     List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                     nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("JoinAWar"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
                     nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.war, CFG.PADDING, 0));
                     nElements.add(new MenuElement_HoverElement(nData));
                     nData.clear();
                     this.menuElementHover = new MenuElement_Hover(nElements, true);
                  } else {
                     this.menuElementHover = null;
                  }
               }
            }
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING * 2;
      } else {
         Game.addSimpleTask(new Game.SimpleTask("rebuildInGame_MessagesSavePos") {
            @Override
            public void update() {
               Game.menuManager.rebuildInGame_MessagesSavePos();
            }
         });
      }

      int menuHeight = Math.min(buttonY, CFG.GAME_HEIGHT - titleHeight - menuY);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, buttonY)));
      this.initMenu(
         new MenuTitleIMG_Message(Game.lang.get("CallToArms"), true, false, Images.title600) {
            @Override
            public long getTime() {
               return InGame_MessageCallToWar.lTime;
            }
         },
         CFG.GAME_WIDTH / 2 - menuWidth / 2,
         Math.min((int)(CFG.GAME_HEIGHT * 0.2F), CFG.GAME_HEIGHT / 2 - (menuHeight + titleHeight) / 2),
         menuWidth,
         menuHeight,
         menuElements,
         message != null,
         true
      );
      lTime = CFG.currentTimeMillis;
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
      PMessage tMessage = Game.player.getMessage(key);
      if (tMessage != null) {
         tMessage.onRefuse();
      }

      Game.menuManager.setVisibleInGame_PopUp(false);
      Game.player.removeMessage(key);
   }
}
