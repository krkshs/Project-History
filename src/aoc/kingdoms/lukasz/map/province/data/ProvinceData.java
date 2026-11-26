package aoc.kingdoms.lukasz.map.province.data;

import aoc.kingdoms.lukasz.jakowski.Game;

public class ProvinceData {
   public int c = 0;
   public int o = 0;
   public int w = -1;

   public int getCivID() {
      return this.c;
   }

   public void setCivID(int iCivID) {
      this.c = iCivID;
   }

   public int getOccupiedByCivID() {
      return this.o;
   }

   public void setOccupiedByCivID(int provinceID, int iOccupiedByCivID) {
      this.o = iOccupiedByCivID;
      if (iOccupiedByCivID != 0) {
         Game.getCiv(Game.getProvince(provinceID).getCivID()).addOccupiedProvince(provinceID);
      } else {
         Game.getCiv(Game.getProvince(provinceID).getCivID()).removeOccupiedProvince(provinceID);
      }
   }

   public int getWastelandLevel() {
      return this.w;
   }

   public void setWastelandLevel(int wa) {
      this.w = wa;
   }
}
