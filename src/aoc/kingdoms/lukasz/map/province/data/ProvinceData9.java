package aoc.kingdoms.lukasz.map.province.data;

public class ProvinceData9 {
   public int s = -1;
   public int e = 0;

   public int getColonizationGrowthRateExtra() {
      return this.e;
   }

   public void setColonizationGrowthRateExtra(int colonizationGrowthRateExtra) {
      this.e = colonizationGrowthRateExtra;
   }

   public void setColonizationStartedTurnID(int colonizationStartedTurnID) {
      this.s = colonizationStartedTurnID;
   }

   public int getColonizationStartedTurnID() {
      return this.s;
   }
}
