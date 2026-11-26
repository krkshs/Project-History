package aoc.kingdoms.lukasz.menusInGame.DiplomacyMessage;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Player.MessageTypes.PMessage;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.RulersManager;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
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
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextIcon_Diplomacy;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Desc;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Static_ID;
import aoc.kingdoms.lukasz.menusInGame.Civ.InGame_Civ;
import aoc.kingdoms.lukasz.menusInGame.Diplomacy.InGame_DeclareWar;
import aoc.kingdoms.lukasz.textures.Image;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_MessageInsult extends Menu {
   public static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static int iCivID = 0;
   public static String key;

   public InGame_MessageInsult(String nKey) {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING + Images.boxTitleBORDERWIDTH;
      int titleHeight = ImageManager.getImage(Images.title600).getHeight();
      int menuWidth = ImageManager.getImage(Images.insideTop600).getWidth();
      int menuY = ImageManager.getImage(Images.flagBG).getHeight() + Renderer.boxBGExtraY + CFG.PADDING + ImageManager.getImage(Images.title600).getHeight();
      int buttonYPadding = CFG.PADDING * 2;
      int buttonY = CFG.PADDING;
      key = nKey;
      PMessage pMessage = Game.player.getMessage(key);
      if (pMessage != null) {
         iCivID = pMessage.fromCivID;
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
         menuElements.add(new ButtonFlag_Diplomacy(Game.player.iCivID, menuWidth / 2 + maxWidth / 2 + CFG.PADDING * 2, buttonY + tempTitlePaddingY, true));
         menuElements.add(
            new Text_Static_ID(
               Game.player.iCivID,
               Game.getCiv(Game.player.iCivID).getCivName(),
               CFG.FONT_REGULAR,
               -1,
               menuWidth / 2 + maxWidth / 2 + CFG.PADDING * 2 + ImageManager.getImage(Images.flagDiplomacyOver).getWidth(),
               buttonY,
               tempTextW,
               tempTitleH
            )
         );
         menuElements.add(new Text_Static_ID(iCivID, Game.getCiv(iCivID).getCivName(), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, tempTextW, tempTitleH));
         menuElements.add(new TextIcon_Diplomacy(Images.insultBig, paddingLeft, buttonY, menuWidth - paddingLeft * 2, tempTitleH, maxWidth) {
            @Override
            public Color getColorBar() {
               return DiplomacyManager.COLOR_INSULT;
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
                     InGame_MessageInsult.iCivID,
                     Game.player.iCivID,
                     Game.getCiv(Game.player.iCivID).diplomacy.isImprovingRelations(InGame_MessageInsult.iCivID),
                     Game.getCiv(Game.player.iCivID).diplomacy.isDamagingRelations(InGame_MessageInsult.iCivID)
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
               "" + Game.getCiv(Game.player.iCivID).getNumOfProvinces(),
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
               Game_Calendar.getDate_ByTurnID(pMessage.expiresTurnID),
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
                  nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Expires") + ": "));
                  nData.add(new MenuElement_HoverElement_Type_TextTitle(this.getText(), CFG.FONT_BOLD, Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle(Images.time, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements);
               }
            }
         );
         RulersManager.loadRulerIMG_DiplomacyLeft(iCivID);
         RulersManager.loadRulerIMG_DiplomacyRight(Game.player.iCivID);
         menuElements.add(new ButtonRuler_Diplomacy(iCivID, paddingLeft + CFG.PADDING, buttonY));
         menuElements.add(
            new ButtonRuler_Diplomacy(Game.player.iCivID, menuWidth - paddingLeft - ButtonRuler_Diplomacy.getButtonWidth() - CFG.PADDING, buttonY) {
               @Override
               public Image getRulerImage() {
                  return RulersManager.rulerIMG_DiplomacyRight;
               }
            }
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
         menuElements.add(new Text_Desc(DiplomacyManager.getInsult(), paddingLeft, buttonY, menuWidth - paddingLeft * 2) {
            @Override
            protected Color getColor(boolean isActive) {
               return Colors.getColorButtonHover2(isActive, this.getIsHovered());
            }
         });
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING * 2;
         paddingLeft += CFG.PADDING;
         menuElements.add(
            new ButtonGame(
               Game.lang.get("WithdrawTheDiplomat"), CFG.FONT_REGULAR, -1, paddingLeft, buttonY, (menuWidth - paddingLeft * 2 - CFG.PADDING / 2 * 2) / 2, true
            ) {
               @Override
               public void actionElement() {
                  InGame_MessageInsult.confirm();
               }
            }
         );
         menuElements.add(
            new ButtonGame(
               Game.lang.get("DeclareWar"),
               CFG.FONT_REGULAR,
               -1,
               paddingLeft + CFG.PADDING + (menuWidth - paddingLeft * 2 - CFG.PADDING / 2 * 2) / 2,
               buttonY,
               (menuWidth - paddingLeft * 2 - CFG.PADDING / 2 * 2) / 2,
               true
            ) {
               @Override
               public void actionElement() {
                  PMessage tMessage = Game.player.getMessage(InGame_MessageInsult.key);
                  if (tMessage != null) {
                     tMessage.onAccept();
                     InGame_DeclareWar.actionDeclareWar(Game.player.iCivID, tMessage.fromCivID, true);
                  }

                  Game.menuManager.setVisibleInGame_PopUp(false);
                  Game.player.removeMessage(InGame_MessageInsult.key);
               }

               @Override
               public void buildElementHover() {
                  PMessage tMessage = Game.player.getMessage(InGame_MessageInsult.key);
                  if (tMessage != null) {
                     List<MenuElement_HoverElement> nElements = new ArrayList<>();
                     List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                     nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("DeclareWar"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
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
         new MenuTitleIMG_Message(Game.lang.get("AnInsultFrom") + ": " + Game.getCiv(iCivID).getCivName(), true, false, Images.title600) {
            @Override
            public long getTime() {
               return InGame_MessageInsult.lTime;
            }
         },
         CFG.GAME_WIDTH / 2 - menuWidth / 2,
         Math.min((int)(CFG.GAME_HEIGHT * 0.2F), CFG.GAME_HEIGHT / 2 - (menuHeight + titleHeight) / 2),
         menuWidth,
         menuHeight,
         menuElements,
         pMessage != null,
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
