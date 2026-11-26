package aoc.kingdoms.lukasz.menusInGame;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleIMG;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonCurrentSituation_Value;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.textStatic.TextIcon2_Value;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_Court;
import aoc.kingdoms.lukasz.menusInGame.Info.InGame_Info;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_BattleTactics extends Menu {
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;

   public InGame_BattleTactics() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2 + Images.boxTitleBORDERWIDTH;
      int titleHeight = ImageManager.getImage(Images.title500).getHeight();
      int menuWidth = ImageManager.getImage(Images.title500).getWidth();
      int menuX = CFG.BUTTON_WIDTH + Renderer.boxBGExtraY + CFG.PADDING;
      int menuY = CFG.GAME_HEIGHT / 4;
      int buttonY = CFG.PADDING;
      int buttonX = Images.boxTitleBORDERWIDTH;

      for (int i = 0; i < GameValues.battleTactics.BATTLE_TACTICS.length; i++) {
         menuElements.add(
            new ButtonCurrentSituation_Value(
               Game.lang.get(GameValues.battleTactics.BATTLE_TACTICS[i]),
               Images.battle,
               paddingLeft,
               buttonY,
               menuWidth - paddingLeft * 2 - CFG.BUTTON_HEIGHT2 * 2 - CFG.PADDING * 2,
               CFG.BUTTON_HEIGHT,
               ImageManager.getImage(Images.gold).getWidth() + CFG.PADDING * 4,
               true,
               i
            ) {
               @Override
               public void actionElement() {
                  if (Game.getCiv(Game.player.iCivID).getBattleTacticsID() != this.getCurrent()) {
                     Game.getCiv(Game.player.iCivID).setBattleTacticsID(this.getCurrent());
                     if (Game.menuManager.getVisibleInGame_Court()) {
                        Game.menuManager.rebuildInGame_CourtSavePos();
                        Game.menuManager.setVisibleInGame_Court(true);
                        InGame_Court.lTime = 0L;
                     }

                     InGame_Info.iCivID = Game.player.iCivID;
                     InGame_Info.iCivID2 = Game.player.iCivID;
                     Game.menuManager
                        .rebuildInGame_Info(
                           Game.lang.get("BattleTactics"),
                           Game.lang.get(GameValues.battleTactics.BATTLE_TACTICS[Game.getCiv(Game.player.iCivID).getBattleTacticsID()])
                        );
                     InGame_Info.imgID = Images.infoCrown;
                  }

                  Game.menuManager.setVisibleInGame_PopUp(false);
               }

               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(this.getText(), Colors.HOVER_GOLD));
                  nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.battle, CFG.PADDING, 0));
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        Game.lang.get("UnitsAttack") + ": ",
                        (GameValues.battleTactics.BATTLE_TACTICS_ATTACK[this.getCurrent()] > 0 ? "+" : "")
                           + GameValues.battleTactics.BATTLE_TACTICS_ATTACK[this.getCurrent()],
                        Images.attack,
                        CFG.FONT_REGULAR_SMALL,
                        CFG.FONT_BOLD_SMALL,
                        Colors.HOVER_LEFT,
                        GameValues.battleTactics.BATTLE_TACTICS_ATTACK[this.getCurrent()] == 0
                           ? Colors.HOVER_NEUTRAL
                           : (
                              GameValues.battleTactics.BATTLE_TACTICS_ATTACK[this.getCurrent()] > 0
                                 ? Colors.COLOR_TEXT_MODIFIER_POSITIVE
                                 : Colors.COLOR_TEXT_MODIFIER_NEGATIVE
                           )
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        Game.lang.get("UnitsDefense") + ": ",
                        (GameValues.battleTactics.BATTLE_TACTICS_DEFENSE[this.getCurrent()] > 0 ? "+" : "")
                           + GameValues.battleTactics.BATTLE_TACTICS_DEFENSE[this.getCurrent()],
                        Images.defense,
                        CFG.FONT_REGULAR_SMALL,
                        CFG.FONT_BOLD_SMALL,
                        Colors.HOVER_LEFT,
                        GameValues.battleTactics.BATTLE_TACTICS_DEFENSE[this.getCurrent()] == 0
                           ? Colors.HOVER_NEUTRAL
                           : (
                              GameValues.battleTactics.BATTLE_TACTICS_DEFENSE[this.getCurrent()] > 0
                                 ? Colors.COLOR_TEXT_MODIFIER_POSITIVE
                                 : Colors.COLOR_TEXT_MODIFIER_NEGATIVE
                           )
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements);
               }

               @Override
               protected Color getColor(boolean isActive) {
                  return this.getCurrent() == Game.getCiv(Game.player.iCivID).getBattleTacticsID() ? Colors.HOVER_GOLD : super.getColor(isActive);
               }
            }
         );
         buttonX = paddingLeft + menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         menuElements.add(
            new TextIcon2_Value(
               (GameValues.battleTactics.BATTLE_TACTICS_ATTACK[i] > 0 ? "+" : "") + GameValues.battleTactics.BATTLE_TACTICS_ATTACK[i],
               Images.attack,
               buttonX,
               buttonY,
               CFG.BUTTON_HEIGHT2,
               CFG.BUTTON_HEIGHT,
               i
            ) {
               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        Game.lang.get("UnitsAttack") + ": ",
                        (GameValues.battleTactics.BATTLE_TACTICS_ATTACK[this.getCurrent()] > 0 ? "+" : "")
                           + GameValues.battleTactics.BATTLE_TACTICS_ATTACK[this.getCurrent()],
                        Images.attack,
                        CFG.FONT_REGULAR_SMALL,
                        CFG.FONT_BOLD_SMALL,
                        Colors.HOVER_LEFT,
                        GameValues.battleTactics.BATTLE_TACTICS_ATTACK[this.getCurrent()] == 0
                           ? Colors.HOVER_NEUTRAL
                           : (
                              GameValues.battleTactics.BATTLE_TACTICS_ATTACK[this.getCurrent()] > 0
                                 ? Colors.COLOR_TEXT_MODIFIER_POSITIVE
                                 : Colors.COLOR_TEXT_MODIFIER_NEGATIVE
                           )
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements);
               }

               @Override
               protected Color getColor(boolean isActive) {
                  if (this.getIsHovered()) {
                     return super.getColor(isActive);
                  } else {
                     return GameValues.battleTactics.BATTLE_TACTICS_ATTACK[this.getCurrent()] == 0
                        ? Colors.HOVER_NEUTRAL
                        : (
                           GameValues.battleTactics.BATTLE_TACTICS_ATTACK[this.getCurrent()] > 0
                              ? Colors.COLOR_TEXT_MODIFIER_POSITIVE
                              : Colors.COLOR_TEXT_MODIFIER_NEGATIVE
                        );
                  }
               }
            }
         );
         buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         menuElements.add(
            new TextIcon2_Value(
               (GameValues.battleTactics.BATTLE_TACTICS_DEFENSE[i] > 0 ? "+" : "") + GameValues.battleTactics.BATTLE_TACTICS_DEFENSE[i],
               Images.defense,
               buttonX,
               buttonY,
               CFG.BUTTON_HEIGHT2,
               CFG.BUTTON_HEIGHT,
               i
            ) {
               @Override
               public void buildElementHover() {
                  List<MenuElement_HoverElement> nElements = new ArrayList<>();
                  List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
                  nData.add(
                     new MenuElement_HoverElement_Type_Button_TextBonus(
                        Game.lang.get("UnitsDefense") + ": ",
                        (GameValues.battleTactics.BATTLE_TACTICS_DEFENSE[this.getCurrent()] > 0 ? "+" : "")
                           + GameValues.battleTactics.BATTLE_TACTICS_DEFENSE[this.getCurrent()],
                        Images.defense,
                        CFG.FONT_REGULAR_SMALL,
                        CFG.FONT_BOLD_SMALL,
                        Colors.HOVER_LEFT,
                        GameValues.battleTactics.BATTLE_TACTICS_DEFENSE[this.getCurrent()] == 0
                           ? Colors.HOVER_NEUTRAL
                           : (
                              GameValues.battleTactics.BATTLE_TACTICS_DEFENSE[this.getCurrent()] > 0
                                 ? Colors.COLOR_TEXT_MODIFIER_POSITIVE
                                 : Colors.COLOR_TEXT_MODIFIER_NEGATIVE
                           )
                     )
                  );
                  nElements.add(new MenuElement_HoverElement(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover(nElements);
               }

               @Override
               protected Color getColor(boolean isActive) {
                  if (this.getIsHovered()) {
                     return super.getColor(isActive);
                  } else {
                     return GameValues.battleTactics.BATTLE_TACTICS_DEFENSE[this.getCurrent()] == 0
                        ? Colors.HOVER_NEUTRAL
                        : (
                           GameValues.battleTactics.BATTLE_TACTICS_DEFENSE[this.getCurrent()] > 0
                              ? Colors.COLOR_TEXT_MODIFIER_POSITIVE
                              : Colors.COLOR_TEXT_MODIFIER_NEGATIVE
                        );
                  }
               }
            }
         );
         buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
      }

      int tMenuHeight = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - CFG.PADDING * 2);
      menuElements.add(new Empty(0, 0, menuWidth, Math.max(buttonY, tMenuHeight)));
      this.initMenu(new MenuTitleIMG(Game.lang.get("BattleTactics"), true, false, Images.title500) {
         @Override
         public long getTime() {
            return InGame_BattleTactics.lTime;
         }
      }, CFG.GAME_WIDTH / 2 - menuWidth / 2, CFG.GAME_HEIGHT / 4, menuWidth, tMenuHeight, menuElements, false, true);
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
      ImageManager.getImage(Images.civInfoOver)
         .draw2(
            oSB,
            this.getPosX() + iTranslateX,
            this.getPosY() + iTranslateY,
            this.getWidth(),
            Math.min(this.getHeight(), ImageManager.getImage(Images.civInfoOver).getHeight())
         );
      oSB.setColor(Color.WHITE);
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public void setVisible(boolean visible) {
      super.setVisible(visible);
      lTime = CFG.currentTimeMillis;
   }
}
