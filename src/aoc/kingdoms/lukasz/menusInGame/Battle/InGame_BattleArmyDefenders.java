package aoc.kingdoms.lukasz.menusInGame.Battle;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.map.army.ArmyManager;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.button.ButtonArmyBattle;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import java.util.ArrayList;
import java.util.List;

public class InGame_BattleArmyDefenders extends Menu {
   public static int mPosX = 0;
   public static int mPosY = 0;
   public static int mWidth = 0;
   private List<InGame_BattleArmyDefenders.BattleStatsRegiments> tRegiments = new ArrayList<>();

   public void addRegiment(int unitTypeID, int armyID, int numOfUnits, int iCivID) {
      int i = 0;

      for (int iSize = this.tRegiments.size(); i < iSize; i++) {
         if (this.tRegiments.get(i).unitTypeID == unitTypeID && this.tRegiments.get(i).armyID == armyID) {
            this.tRegiments.get(i).numOfUnits += numOfUnits;
            this.tRegiments.get(i).numOfRegiments++;
            return;
         }
      }

      this.tRegiments.add(new InGame_BattleArmyDefenders.BattleStatsRegiments(unitTypeID, armyID, numOfUnits, iCivID));
   }

   public InGame_BattleArmyDefenders() {
      List<MenuElement> menuElements = new ArrayList<>();
      int buttonX = CFG.PADDING;
      int buttonY = 0;
      if (InGame_Battle.battleID >= 0) {
         int i = 0;

         for (int iSize = Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.firstLine.size(); i < iSize; i++) {
            if (Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.firstLine.get(i) != null) {
               this.addRegiment(
                  Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.firstLine.get(i).aR.uID,
                  Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.firstLine.get(i).aR.aID,
                  Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.firstLine.get(i).aR.num,
                  Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.firstLine.get(i).c
               );
            }
         }

         i = 0;

         for (int iSizex = Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.secondLine.size(); i < iSizex; i++) {
            if (Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.secondLine.get(i) != null) {
               this.addRegiment(
                  Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.secondLine.get(i).aR.uID,
                  Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.secondLine.get(i).aR.aID,
                  Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.secondLine.get(i).aR.num,
                  Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.secondLine.get(i).c
               );
            }
         }

         i = 0;

         for (int iSizexx = Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.reserveFirstLine.size(); i < iSizexx; i++) {
            this.addRegiment(
               Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.reserveFirstLine.get(i).aR.uID,
               Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.reserveFirstLine.get(i).aR.aID,
               Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.reserveFirstLine.get(i).aR.num,
               Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.reserveFirstLine.get(i).c
            );
         }

         i = 0;

         for (int iSizexx = Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.reserveSecondLine.size(); i < iSizexx; i++) {
            this.addRegiment(
               Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.reserveSecondLine.get(i).aR.uID,
               Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.reserveSecondLine.get(i).aR.aID,
               Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.reserveSecondLine.get(i).aR.num,
               Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.reserveSecondLine.get(i).c
            );
         }

         i = 0;

         for (int iSizexx = Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.defeated.size(); i < iSizexx; i++) {
            this.addRegiment(
               Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.defeated.get(i).aR.uID,
               Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.defeated.get(i).aR.aID,
               Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.defeated.get(i).aR.num,
               Game.battleManager.getBattle(InGame_Battle.battleID).attackingArmy.defeated.get(i).c
            );
         }

         List<Integer> tSorted = new ArrayList<>();
         List<Integer> tempReg = new ArrayList<>();
         int ix = 0;

         for (int iSizexx = this.tRegiments.size(); ix < iSizexx; ix++) {
            tempReg.add(ix);
         }

         while (tempReg.size() > 0) {
            ix = 0;
            int ixx = 1;

            for (int iSizexx = tempReg.size(); ixx < iSizexx; ixx++) {
               if (this.tRegiments.get(tempReg.get(ix)).unitTypeID > this.tRegiments.get(tempReg.get(ixx)).unitTypeID) {
                  ix = ixx;
               } else if (this.tRegiments.get(tempReg.get(ix)).unitTypeID == this.tRegiments.get(tempReg.get(ixx)).unitTypeID
                  && this.tRegiments.get(tempReg.get(ix)).armyID > this.tRegiments.get(tempReg.get(ixx)).armyID) {
                  ix = ixx;
               }
            }

            tSorted.add(tempReg.get(ix));
            tempReg.remove(ix);
         }

         for (int j = 0; j < 3; j++) {
            int ixx = 0;

            for (int iSizexxx = this.tRegiments.size(); ixx < iSizexxx; ixx++) {
               if (ArmyManager.lUnitsTypes.get(this.tRegiments.get(tSorted.get(ixx)).unitTypeID).Line == j) {
                  menuElements.add(
                     new ButtonArmyBattle(
                        "" + this.tRegiments.get(tSorted.get(ixx)).numOfUnits,
                        this.tRegiments.get(tSorted.get(ixx)).numOfRegiments,
                        this.tRegiments.get(tSorted.get(ixx)).iCivID,
                        buttonX,
                        buttonY,
                        this.tRegiments.get(tSorted.get(ixx)).unitTypeID,
                        this.tRegiments.get(tSorted.get(ixx)).armyID,
                        true
                     )
                  );
                  buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
               }
            }
         }
      }

      this.initMenu(
         null, mPosX, mPosY, mWidth, ImageManager.getImage(Images.unitsFrameBattle).getHeight() + ButtonArmyBattle.getStatsHeight() + 1, menuElements, false
      );
      this.drawScrollPositionAlways = false;
   }

   @Override
   public void onHovered() {
      super.onHovered();
      Game.menuManager.setOrderOfMenu_InGameBattle();
   }

   @Override
   public int getMenuPosY() {
      return super.getMenuPosY() + InGame_Battle.nTranslateY;
   }

   @Override
   public int getPosY() {
      return super.getPosY() + InGame_Battle.nTranslateY;
   }

   @Override
   public int getPosX() {
      return super.getPosX() + InGame_Battle.nTranslateX;
   }

   @Override
   public int getMenuPosX() {
      return super.getMenuPosX() + InGame_Battle.nTranslateX;
   }

   public class BattleStatsRegiments {
      int unitTypeID;
      int armyID;
      int numOfUnits;
      int numOfRegiments;
      int iCivID;

      public BattleStatsRegiments(int unitTypeID, int armyID, int numOfUnits, int iCivID) {
         this.unitTypeID = unitTypeID;
         this.armyID = armyID;
         this.numOfUnits = numOfUnits;
         this.numOfRegiments = 1;
         this.iCivID = iCivID;
      }
   }
}
