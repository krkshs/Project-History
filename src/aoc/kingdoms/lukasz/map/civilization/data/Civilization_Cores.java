package aoc.kingdoms.lukasz.map.civilization.data;

import aoc.kingdoms.lukasz.jakowski.Game;
import java.util.ArrayList;
import java.util.List;

public class Civilization_Cores {
   public List<Integer> provinces = new ArrayList<>();
   public int provincesSize = 0;

   public final void buildProvincesNonCore(int civID) {
      this.provinces.clear();
      this.provincesSize = 0;

      for (int i = 0; i < Game.getCiv(civID).getNumOfProvinces(); i++) {
         if (!Game.getProvince(Game.getCiv(civID).getProvinceID(i)).haveACore && Game.getProvince(Game.getCiv(civID).getProvinceID(i)).coreCreation == null) {
            this.provinces.add(Game.getCiv(civID).getProvinceID(i));
         }
      }

      this.provincesSize = this.provinces.size();
   }

   public final void addProvince(int provinceID) {
      if (!this.provinces.contains(provinceID)) {
         this.provinces.add(provinceID);
         this.provincesSize = this.provinces.size();
      }
   }

   public final void removeProvince(int provinceID) {
      for (int i = 0; i < this.provincesSize; i++) {
         if (this.provinces.get(i) == provinceID) {
            this.provinces.remove(i);
            this.provincesSize = this.provinces.size();
            return;
         }
      }
   }

   public final void checkProvince(int provinceID, int civID) {
      if (Game.getProvince(provinceID).getCivID() != civID) {
         this.removeProvince(provinceID);
      } else {
         if (!Game.getProvince(provinceID).haveACore) {
            if (this.provinces.contains(provinceID)) {
               return;
            }

            this.provinces.add(provinceID);
            this.provincesSize = this.provinces.size();
         } else {
            this.removeProvince(provinceID);
         }
      }
   }

   public final void checkProvince_Full(int provinceID, int civID) {
      if (Game.getProvince(provinceID).getCivID() != civID) {
         this.removeProvince(provinceID);
      } else {
         if (!Game.getProvince(provinceID).haveACore(civID)) {
            if (this.provinces.contains(provinceID)) {
               return;
            }

            this.provinces.add(provinceID);
            this.provincesSize = this.provinces.size();
         } else {
            this.removeProvince(provinceID);
         }
      }
   }
}
