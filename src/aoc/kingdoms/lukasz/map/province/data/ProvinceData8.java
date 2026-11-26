package aoc.kingdoms.lukasz.map.province.data;

public class ProvinceData8 {
   public boolean w = false;
   public float r = 0.0F;

   public boolean isWonderBuilt() {
      return this.w;
   }

   public void setWonderBuilt(boolean wo) {
      this.w = wo;
   }

   public float getRevolutionaryRisk() {
      return this.r;
   }

   public void setRevolutionaryRisk(float rr) {
      this.r = rr;
   }
}
