package aoc.kingdoms.lukasz.menusInGame.Battle;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.army.ArmyManager;
import aoc.kingdoms.lukasz.map.battles.BattleRegiment;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitleTechTree;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonBattleRegiment;
import aoc.kingdoms.lukasz.menu_element.button.ButtonBattleRegimentDefeated;
import aoc.kingdoms.lukasz.menu_element.button.ButtonBattleRegimentEmpty;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_Battle_Full extends Menu {
   protected static final int ANIMATION_TIME = 60;
   public static long lTime = 0L;
   public static String key = "";
   public static int iProvinceID = 0;
   public static int battleID = -1;
   public static int TURN_ID = 0;
   public static String sDay = "";
   public static int iDayWidth;

   public InGame_Battle_Full() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2;
      int paddingTop = CFG.PADDING * 4;
      int titleHeight = ImageManager.getImage(Images.titleTechTree).getHeight();
      int menuWidth = CFG.GAME_WIDTH;
      int menuX = CFG.BUTTON_WIDTH + Renderer.boxBGExtraY + CFG.PADDING;
      int menuY = ImageManager.getImage(Images.topStats).getHeight() + CFG.PADDING * 2 + ImageManager.getImage(Images.title928).getHeight();
      int buttonY = paddingTop;
      int buttonX = menuWidth / 2
         - (
               GameValues.battle.BATTLE_MAX_BATTLE_WIDTH * ImageManager.getImage(Images.battleArmy0).getWidth()
                  + 1 * (GameValues.battle.BATTLE_MAX_BATTLE_WIDTH + 1)
            )
            / 2
         + 1;

      for (int i = 0; i < Game.battleManager.getBattle(battleID).attackingArmy.secondLine.size(); i++) {
         if (Game.battleManager.getBattle(battleID).attackingArmy.secondLine.get(i) != null) {
            menuElements.add(
               new ButtonBattleRegiment(
                  Game.battleManager.getBattle(battleID).attackingArmy.secondLine.get(i).c,
                  this.getImageRegimentID(ArmyManager.lUnitsTypes.get(Game.battleManager.getBattle(battleID).attackingArmy.secondLine.get(i).aR.uID).Line),
                  buttonX,
                  buttonY,
                  i,
                  0,
                  ArmyManager.lArmy
                        .get(Game.battleManager.getBattle(battleID).attackingArmy.secondLine.get(i).aR.uID)
                        .get(Game.battleManager.getBattle(battleID).attackingArmy.secondLine.get(i).aR.aID)
                     .AttackRange,
                  true,
                  false
               ) {
                  @Override
                  public BattleRegiment getBattleRegiment(int battleID, int id) {
                     return Game.battleManager.getBattle(battleID).attackingArmy.secondLine.get(id);
                  }
               }
            );
         } else {
            menuElements.add(new ButtonBattleRegimentEmpty(Images.battleArmy2, buttonX, buttonY) {});
         }

         buttonX += ImageManager.getImage(Images.battleArmy0).getWidth() + 1;
      }

      buttonX = menuWidth / 2
         - (
               GameValues.battle.BATTLE_MAX_BATTLE_WIDTH * ImageManager.getImage(Images.battleArmy0).getWidth()
                  + 1 * (GameValues.battle.BATTLE_MAX_BATTLE_WIDTH + 1)
            )
            / 2
         + 1;
      buttonY += ImageManager.getImage(Images.battleArmy0).getHeight() + 2;

      for (int i = 0; i < Game.battleManager.getBattle(battleID).attackingArmy.firstLine.size(); i++) {
         if (Game.battleManager.getBattle(battleID).attackingArmy.firstLine.get(i) != null) {
            if (Game.battleManager.getBattle(battleID).attackingArmy.firstLine.get(i).aR.num > 0) {
               menuElements.add(
                  new ButtonBattleRegiment(
                     Game.battleManager.getBattle(battleID).attackingArmy.firstLine.get(i).c,
                     this.getImageRegimentID(ArmyManager.lUnitsTypes.get(Game.battleManager.getBattle(battleID).attackingArmy.firstLine.get(i).aR.uID).Line),
                     buttonX,
                     buttonY,
                     i,
                     0,
                     ArmyManager.lArmy
                           .get(Game.battleManager.getBattle(battleID).attackingArmy.firstLine.get(i).aR.uID)
                           .get(Game.battleManager.getBattle(battleID).attackingArmy.firstLine.get(i).aR.aID)
                        .AttackRange,
                     false,
                     false
                  ) {}
               );
            } else {
               menuElements.add(
                  new ButtonBattleRegimentDefeated(
                     Game.battleManager.getBattle(battleID).attackingArmy.firstLine.get(i).c,
                     this.getImageRegimentID(ArmyManager.lUnitsTypes.get(Game.battleManager.getBattle(battleID).attackingArmy.firstLine.get(i).aR.uID).Line),
                     buttonX,
                     buttonY,
                     i,
                     0
                  ) {}
               );
            }
         } else {
            menuElements.add(new ButtonBattleRegimentEmpty(Images.battleArmy0, buttonX, buttonY));
         }

         buttonX += ImageManager.getImage(Images.battleArmy0).getWidth() + 1;
      }

      buttonX = menuWidth / 2
         - (
               GameValues.battle.BATTLE_MAX_BATTLE_WIDTH * ImageManager.getImage(Images.battleArmy0).getWidth()
                  + 1 * (GameValues.battle.BATTLE_MAX_BATTLE_WIDTH + 1)
            )
            / 2
         + 1;
      buttonY += getMiddleHeight() + ImageManager.getImage(Images.battleArmy0).getHeight() + 1;

      for (int i = 0; i < Game.battleManager.getBattle(battleID).defendingArmy.firstLine.size(); i++) {
         if (Game.battleManager.getBattle(battleID).defendingArmy.firstLine.get(i) != null) {
            if (Game.battleManager.getBattle(battleID).defendingArmy.firstLine.get(i).aR.num > 0) {
               menuElements.add(
                  new ButtonBattleRegiment(
                     Game.battleManager.getBattle(battleID).defendingArmy.firstLine.get(i).c,
                     this.getImageRegimentID(ArmyManager.lUnitsTypes.get(Game.battleManager.getBattle(battleID).defendingArmy.firstLine.get(i).aR.uID).Line),
                     buttonX,
                     buttonY,
                     i,
                     0,
                     ArmyManager.lArmy
                           .get(Game.battleManager.getBattle(battleID).defendingArmy.firstLine.get(i).aR.uID)
                           .get(Game.battleManager.getBattle(battleID).defendingArmy.firstLine.get(i).aR.aID)
                        .AttackRange,
                     false,
                     true
                  ) {
                     @Override
                     public BattleRegiment getBattleRegiment(int battleID, int id) {
                        return Game.battleManager.getBattle(battleID).defendingArmy.firstLine.get(id);
                     }
                  }
               );
            } else {
               menuElements.add(
                  new ButtonBattleRegimentDefeated(
                     Game.battleManager.getBattle(battleID).defendingArmy.firstLine.get(i).c,
                     this.getImageRegimentID(ArmyManager.lUnitsTypes.get(Game.battleManager.getBattle(battleID).defendingArmy.firstLine.get(i).aR.uID).Line),
                     buttonX,
                     buttonY,
                     i,
                     0
                  ) {
                     @Override
                     public BattleRegiment getBattleRegiment(int battleID, int id) {
                        return Game.battleManager.getBattle(battleID).defendingArmy.firstLine.get(id);
                     }
                  }
               );
            }
         } else {
            menuElements.add(new ButtonBattleRegimentEmpty(Images.battleArmy0, buttonX, buttonY));
         }

         buttonX += ImageManager.getImage(Images.battleArmy0).getWidth() + 1;
      }

      buttonX = menuWidth / 2
         - (
               GameValues.battle.BATTLE_MAX_BATTLE_WIDTH * ImageManager.getImage(Images.battleArmy0).getWidth()
                  + 1 * (GameValues.battle.BATTLE_MAX_BATTLE_WIDTH + 1)
            )
            / 2
         + 1;
      buttonY += ImageManager.getImage(Images.battleArmy0).getHeight() + 2;

      for (int i = 0; i < Game.battleManager.getBattle(battleID).defendingArmy.secondLine.size(); i++) {
         if (Game.battleManager.getBattle(battleID).defendingArmy.secondLine.get(i) != null) {
            menuElements.add(
               new ButtonBattleRegiment(
                  Game.battleManager.getBattle(battleID).defendingArmy.secondLine.get(i).c,
                  this.getImageRegimentID(ArmyManager.lUnitsTypes.get(Game.battleManager.getBattle(battleID).defendingArmy.secondLine.get(i).aR.uID).Line),
                  buttonX,
                  buttonY,
                  i,
                  0,
                  ArmyManager.lArmy
                        .get(Game.battleManager.getBattle(battleID).defendingArmy.secondLine.get(i).aR.uID)
                        .get(Game.battleManager.getBattle(battleID).defendingArmy.secondLine.get(i).aR.aID)
                     .AttackRange,
                  true,
                  true
               ) {
                  @Override
                  public BattleRegiment getBattleRegiment(int battleID, int id) {
                     return Game.battleManager.getBattle(battleID).defendingArmy.secondLine.get(id);
                  }
               }
            );
         } else {
            menuElements.add(new ButtonBattleRegimentEmpty(Images.battleArmy2, buttonX, buttonY) {});
         }

         buttonX += ImageManager.getImage(Images.battleArmy0).getWidth() + 1;
      }

      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING * 3;
      buttonY = 0;
      int i = 0;

      for (int iSize = menuElements.size(); i < iSize; i++) {
         if (buttonY < menuElements.get(i).getPosY() + menuElements.get(i).getHeight() + CFG.PADDING) {
            buttonY = menuElements.get(i).getPosY() + menuElements.get(i).getHeight() + CFG.PADDING;
         }
      }

      i = Math.min(buttonY, CFG.GAME_HEIGHT - menuY - CFG.PADDING * 2);
      int tMaxX = 0;
      int ix = 0;

      for (int iSizex = menuElements.size(); ix < iSizex; ix++) {
         if (menuElements.get(ix).getPosX() + menuElements.get(ix).getWidth() > tMaxX) {
            tMaxX = menuElements.get(ix).getPosX() + menuElements.get(ix).getWidth();
         }
      }

      tMaxX += 100;
      menuElements.add(new Empty(0, 0, tMaxX, Math.max(buttonY, i)));
      this.initMenu(new MenuTitleTechTree(Game.lang.get("Battle"), titleHeight, false, false) {
         @Override
         public long getTime() {
            return InGame_Battle_Full.lTime;
         }
      }, 0, CFG.GAME_HEIGHT / 2 - i / 2, menuWidth, i, menuElements, false, true);
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
      ImageManager.getImage(Images.insideTechTree)
         .draw2(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight() + CFG.PADDING, false, true);
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public void setVisible(boolean visible) {
      super.setVisible(visible);
      lTime = CFG.currentTimeMillis;
   }

   @Override
   public void onHovered() {
      super.onHovered();
      Game.menuManager.setOrderOfMenu_BattleFull();
   }

   @Override
   public void actionCloseMenu() {
      super.actionCloseMenu();
      Game.menuManager.setVisibleInGame_BattleFull(false);
   }

   public int getImageRegimentID(int iLine) {
      switch (iLine) {
         case 1:
            return Images.battleArmy1;
         case 2:
            return Images.battleArmy2;
         default:
            return Images.battleArmy0;
      }
   }

   public static int getMiddleHeight() {
      return ImageManager.getImage(Images.battleArmy0).getHeight();
   }
}
