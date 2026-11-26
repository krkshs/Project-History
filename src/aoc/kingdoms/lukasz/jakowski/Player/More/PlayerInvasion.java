package aoc.kingdoms.lukasz.jakowski.Player.More;

import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.map.army.ArmyInvasion;
import aoc.kingdoms.lukasz.menusInGame.Province.InGame_ProvinceArmy;
import java.util.ArrayList;
import java.util.List;

public class PlayerInvasion {
   public List<ArmyInvasion> invasions = new ArrayList<>();
   public int invasionsSize = 0;

   public final boolean addInvasion(int iCivID) {
      if (Game.invasionArmyProvincesSize == 0) {
         return false;
      } else if (InGame_ProvinceArmy.sActiveKEY == null) {
         return false;
      } else if (Game.findArmy_FullCheck(iCivID, InGame_ProvinceArmy.sActiveKEY) == null) {
         return false;
      } else {
         for (int i = 0; i < this.invasionsSize; i++) {
            if (this.invasions.get(i).armyKey.equals(InGame_ProvinceArmy.sActiveKEY)) {
               this.invasions.get(i).lProvinces = Game.invasionArmyProvinces;
               return false;
            }
         }

         this.invasions.add(new ArmyInvasion(iCivID, InGame_ProvinceArmy.sActiveKEY, Game.invasionArmyProvinces));
         this.invasionsSize = this.invasions.size();
         return true;
      }
   }

   public final boolean removeInvasion(String armyKey) {
      for (int i = 0; i < this.invasionsSize; i++) {
         if (this.invasions.get(i).armyKey.equals(armyKey)) {
            this.invasions.remove(i);
            this.invasionsSize = this.invasions.size();
            return true;
         }
      }

      return false;
   }

   public final boolean haveInvasionPlan(String armyKey) {
      for (int i = 0; i < this.invasionsSize; i++) {
         if (this.invasions.get(i).armyKey.equals(armyKey)) {
            return true;
         }
      }

      return false;
   }

   public final int getInvasionPlan_NumOfProvinces(String armyKey) {
      for (int i = 0; i < this.invasionsSize; i++) {
         if (this.invasions.get(i).armyKey.equals(armyKey)) {
            return this.invasions.get(i).lProvinces.size();
         }
      }

      return 0;
   }

   public final boolean moveInvasion(String armyKey) {
      for (int i = 0; i < this.invasionsSize; i++) {
         if (this.invasions.get(i).armyKey.equals(armyKey)) {
            if (!this.invasions.get(i).invasionMoveArmy(Game.player.iCivID)) {
               this.invasions.remove(i);
               this.invasionsSize = this.invasions.size();
            }

            return true;
         }
      }

      return false;
   }

   public final void clearInvasions() {
      this.invasions.clear();
      this.invasionsSize = 0;
   }
}
