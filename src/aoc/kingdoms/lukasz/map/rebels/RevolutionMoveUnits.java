package aoc.kingdoms.lukasz.map.rebels;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.SaveLoad.SaveGameManager;
import aoc.kingdoms.lukasz.map.SiegeManager;
import aoc.kingdoms.lukasz.map.moveUnits.MoveUnits;
import java.util.ArrayList;
import java.util.List;

public class RevolutionMoveUnits {
   public List<MoveUnits> lMoveUnits = new ArrayList<>();
   public int iMoveUnitsSize;

   public void clearData() {
      this.lMoveUnits.clear();
      this.iMoveUnitsSize = 0;
   }

   public final void removeAllRebelsArmiesMovingToCiv(int civID) {
      for (int i = this.lMoveUnits.size() - 1; i >= 0; i--) {
         if (Game.getProvince(this.lMoveUnits.get(i).getToProvinceLastID()).getCivID() == civID) {
            Game.getProvince(this.lMoveUnits.get(i).getFromProvinceID()).removeArmy(this.lMoveUnits.get(i).key);
            Game.revolutionManager.removeArmyPosition(this.lMoveUnits.get(i).getFromProvinceID(), this.lMoveUnits.get(i).key);
            this.lMoveUnits.remove(i);
            this.iMoveUnitsSize = this.lMoveUnits.size();
         }
      }
   }

   public final void updateMoveInBattle(String key, boolean inBattle) {
      try {
         for (int i = 0; i < this.iMoveUnitsSize; i++) {
            if (this.lMoveUnits.get(i).key.equals(key)) {
               this.lMoveUnits.get(i).inBattle = inBattle;
               return;
            }
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   public final boolean newMove(int fromProvinceID, int toProvinceID, String key, int extraArmyY, boolean retreat) {
      try {
         if (fromProvinceID == toProvinceID) {
            return false;
         }

         SiegeManager.checkForSiege(fromProvinceID);
         if (Game.getProvinceData4(fromProvinceID).isUnderSiege()) {
            int armyID = Game.getProvince(fromProvinceID).getArmyKeyID(key);
            if (armyID >= 0) {
               Game.getProvince(fromProvinceID).getArmy(armyID).inMovement = false;
               this.removeMove(Game.getProvince(fromProvinceID).getArmy(armyID).key);
            }

            return true;
         }

         int armyID = Game.getProvince(fromProvinceID).getArmyKeyID(key);
         if (armyID >= 0) {
            if (Game.getProvince(fromProvinceID).getArmy(armyID).inBattle && Game.battleManager.isArmyInBattle(fromProvinceID, key)) {
               return false;
            }

            int civID = Game.getProvince(fromProvinceID).getArmy(armyID).civID;
            MoveUnits nMoveUnits = new MoveUnits(civID, fromProvinceID, toProvinceID, key, extraArmyY, retreat, false);
            if (nMoveUnits.iRouteSize > 2 && nMoveUnits.haveSeaProvince()) {
               MoveUnits nMoveUnitsLand = new MoveUnits(civID, fromProvinceID, toProvinceID, key, extraArmyY, retreat, true);
               if (nMoveUnitsLand.iRouteSize > 1 && nMoveUnitsLand.getWidthTotal() < nMoveUnits.getWidthTotal()) {
                  nMoveUnits = nMoveUnitsLand;
                  nMoveUnitsLand = null;
               }
            }

            if (nMoveUnits.iRouteSize <= 1) {
               return false;
            }

            for (int i = 0; i < this.iMoveUnitsSize; i++) {
               if (this.lMoveUnits.get(i).key.equals(key)) {
                  if (this.lMoveUnits.get(i).inRetreat) {
                     return false;
                  }

                  if (this.lMoveUnits.get(i).getToProvinceID() == nMoveUnits.getToProvinceID()) {
                     nMoveUnits.doneMovementProgressWidth = this.lMoveUnits.get(i).doneMovementProgressWidth;
                     nMoveUnits.movementProgressOverWidth = this.lMoveUnits.get(i).movementProgressOverWidth;
                     nMoveUnits.currentMovementProgressWidth = this.lMoveUnits.get(i).currentMovementProgressWidth;
                     nMoveUnits.lCurrentMovingTime = this.lMoveUnits.get(i).lCurrentMovingTime;
                     nMoveUnits.fCurrentMovingPercentage = this.lMoveUnits.get(i).fCurrentMovingPercentage;
                     this.lMoveUnits.remove(i);
                     this.iMoveUnitsSize = this.lMoveUnits.size();
                  } else {
                     if (this.lMoveUnits.get(i).getProgressPerc() >= GameValues.army.MOVE_UNITS_LOCKED_MOVE) {
                        nMoveUnits = new MoveUnits(
                           civID,
                           this.lMoveUnits.get(i).getToProvinceID(),
                           toProvinceID,
                           key,
                           this.lMoveUnits.get(i).extraArmyY,
                           this.lMoveUnits.get(i).getFromProvinceID()
                        );
                        if (nMoveUnits.iRouteSize > 1) {
                           nMoveUnits.doneMovementProgressWidth = this.lMoveUnits.get(i).doneMovementProgressWidth;
                           nMoveUnits.movementProgressOverWidth = this.lMoveUnits.get(i).movementProgressOverWidth;
                           nMoveUnits.currentMovementProgressWidth = this.lMoveUnits.get(i).currentMovementProgressWidth;
                           nMoveUnits.lCurrentMovingTime = this.lMoveUnits.get(i).lCurrentMovingTime;
                           nMoveUnits.fCurrentMovingPercentage = this.lMoveUnits.get(i).fCurrentMovingPercentage;
                           this.lMoveUnits.set(i, nMoveUnits);
                           Game.getProvince(fromProvinceID).getArmy(armyID).inRetreat = retreat;
                           return true;
                        }

                        return false;
                     }

                     this.lMoveUnits.remove(i);
                     this.iMoveUnitsSize = this.lMoveUnits.size();
                  }
                  break;
               }
            }

            this.lMoveUnits.add(nMoveUnits);
            this.iMoveUnitsSize = this.lMoveUnits.size();
            Game.getProvince(fromProvinceID).getArmy(armyID).inRetreat = retreat;
            Game.getProvince(fromProvinceID).getArmy(armyID).setInMovement(true);
            Game.getProvince(fromProvinceID).updateArmyPosY();
            Game.getProvince(fromProvinceID).updateIsUnderSiege();
            return true;
         }
      } catch (Exception var10) {
         CFG.exceptionStack(var10);
      }

      return false;
   }

   public final void updateMoveUnits_Load(SaveGameManager.Save_Civ_MoveUnits civMoveUnits, boolean nInRetreat, boolean nInBattle) {
      for (int i = this.iMoveUnitsSize - 1; i >= 0; i--) {
         if (this.lMoveUnits.get(i).key.equals(civMoveUnits.k)) {
            this.lMoveUnits.get(i).currentMovementProgressWidth = civMoveUnits.m;
            this.lMoveUnits.get(i).inRetreat = nInRetreat;
            this.lMoveUnits.get(i).inBattle = nInBattle;
            return;
         }
      }
   }

   public final boolean removeMove(String key) {
      for (int i = 0; i < this.iMoveUnitsSize; i++) {
         if (this.lMoveUnits.get(i).key.equals(key)) {
            int tID = Game.getProvince(this.lMoveUnits.get(i).getFromProvinceID()).getArmyKeyID(this.lMoveUnits.get(i).key);
            if (tID >= 0) {
               Game.getProvince(this.lMoveUnits.get(i).getFromProvinceID()).getArmy(tID).setInMovement(false);
               Game.getProvince(this.lMoveUnits.get(i).getFromProvinceID()).getArmy(tID).iShiftX_Scaled = 0;
               Game.getProvince(this.lMoveUnits.get(i).getFromProvinceID()).getArmy(tID).iShiftY_Scaled = 0;
            }

            this.lMoveUnits.remove(i);
            this.iMoveUnitsSize = this.lMoveUnits.size();
            return true;
         }
      }

      return false;
   }

   public final void removeMove(int i) {
      int tID = Game.getProvince(this.lMoveUnits.get(i).getFromProvinceID()).getArmyKeyID(this.lMoveUnits.get(i).key);
      if (tID >= 0) {
         Game.getProvince(this.lMoveUnits.get(i).getFromProvinceID()).getArmy(tID).setInMovement(false);
         Game.getProvince(this.lMoveUnits.get(i).getFromProvinceID()).getArmy(tID).inRetreat = false;
         Game.getProvince(this.lMoveUnits.get(i).getFromProvinceID()).getArmy(tID).iShiftX_Scaled = 0;
         Game.getProvince(this.lMoveUnits.get(i).getFromProvinceID()).getArmy(tID).iShiftY_Scaled = 0;
      }

      this.lMoveUnits.remove(i);
      this.iMoveUnitsSize = this.lMoveUnits.size();
   }

   public boolean isArmyAlreadyMoving(int fromProvinceID, int toProvinceID) {
      for (int i = 0; i < this.iMoveUnitsSize; i++) {
         if (this.lMoveUnits.get(i).getFromProvinceID() == fromProvinceID && this.lMoveUnits.get(i).getToProvinceLastID() == toProvinceID) {
            return true;
         }
      }

      return false;
   }
}
