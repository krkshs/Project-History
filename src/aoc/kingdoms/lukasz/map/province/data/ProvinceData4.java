package aoc.kingdoms.lukasz.map.province.data;

import aoc.kingdoms.lukasz.jakowski.Game;

public class ProvinceData4 {
   public boolean u = false;
   public float s = 0.0F;

   public boolean isUnderSiege() {
      return this.u;
   }

   public void setIsUnderSiege(int provinceID, boolean isUnderSiege) {
      this.u = isUnderSiege;
      if (isUnderSiege) {
         Game.getCiv(Game.getProvince(provinceID).getCivID()).addUnderSiege(provinceID);
      } else {
         Game.getCiv(Game.getProvince(provinceID).getCivID()).removeUnderSiege(provinceID);
      }
   }

   public void setIsUnderSiege_Just(boolean isUnderSiege) {
      this.u = isUnderSiege;
   }

   public float getSiegeProgress() {
      return this.s;
   }

   public void setSiegeProgress(float siegeProgress) {
      this.s = siegeProgress;
   }
}
