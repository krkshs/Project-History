package aoc.kingdoms.lukasz.map.province.data;

public class ProvinceData7 {
   private int n;
   private float g = 0.0F;

   public int getReligionID() {
      return this.n;
   }

   public void setReligionID(int rel) {
      this.n = rel;
   }

   public float getIncreasedGrowthRate() {
      return this.g;
   }

   public void setIncreasedGrowthRate(float ig) {
      this.g = ig;
   }
}
