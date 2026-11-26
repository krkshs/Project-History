package aoc.kingdoms.lukasz.map.civilization;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.map.province.Province;
import java.util.ArrayList;
import java.util.List;

public class CivilizationsNeighbors {
   public List<CivilizationsNeighbors.CivNeighbor> civs = new ArrayList<>();
   public int civsSize = 0;

   public final void buildNeighbors(int civID) {
      this.civs.clear();
      this.civsSize = 0;
      Game.getCiv(civID).haveAccessSea = false;

      try {
         for (int i = 0; i < Game.getCiv(civID).getNumOfProvinces(); i++) {
            int provinceID = Game.getCiv(civID).getProvinceID(i);
            if (provinceID >= 0) {
               this.addLandNeighbors(Game.getProvince(provinceID), civID);
               this.addSeaNeighbors(Game.getProvince(provinceID), civID);
            }
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }

      this.civsSize = this.civs.size();
   }

   private void addLandNeighbors(Province province, int civID) {
      int numNeighbors = province.getNeighboringProvincesSize();

      for (int j = 0; j < numNeighbors; j++) {
         int neighborCivID = Game.getProvince(province.getNeighboringProvinces(j)).getCivID();
         if (neighborCivID > 0 && neighborCivID != civID) {
            this.addCiv(neighborCivID, true);
         }
      }
   }

   private void addSeaNeighbors(Province province, int civID) {
      int numSeaNeighbors = province.getNeighboringSeaProvincesSize();

      for (int j = 0; j < numSeaNeighbors; j++) {
         int seaNeighborID = province.getNeighboringSeaProvinces(j);
         Province seaProvince = Game.getProvince(seaNeighborID);
         if (seaProvince.getLevelOfPort() == -2) {
            Game.getCiv(civID).haveAccessSea = true;
         }

         int numLandNeighbors = seaProvince.getNeighboringProvincesSize();

         for (int k = 0; k < numLandNeighbors; k++) {
            int landNeighborID = seaProvince.getNeighboringProvinces(k);
            int landNeighborCivID = Game.getProvince(landNeighborID).getCivID();
            if (landNeighborCivID > 0 && landNeighborCivID != civID && !Game.getProvince(landNeighborID).getSeaProvince()) {
               this.addCiv(landNeighborCivID, false);
            }
         }
      }
   }

   public boolean isNeighbor(int civID) {
      return this.civs.contains(civID);
   }

   public void addCiv(int civID, boolean byLand) {
      for (int i = this.civs.size() - 1; i >= 0; i--) {
         if (this.civs.get(i).civID == civID) {
            this.civs.get(i).byLand = this.civs.get(i).byLand || byLand;
            return;
         }
      }

      this.civs.add(new CivilizationsNeighbors.CivNeighbor(civID, byLand));
   }

   public void logCivNeighbors(int civID) {
      CFG.LOG("CivNeighbors: " + Game.getCiv(civID).getCivName() + " -> " + this.civsSize);

      for (int i = 0; i < this.civsSize; i++) {
         CFG.LOG("Neighbor: " + Game.getCiv(this.civs.get(i).civID).getCivName());
      }
   }

   public class CivNeighbor {
      public int civID;
      public boolean byLand;

      public CivNeighbor() {
      }

      public CivNeighbor(int civID, boolean byLand) {
         this.civID = civID;
         this.byLand = byLand;
      }
   }
}
