package aoc.kingdoms.lukasz.map.civilization.save;

public class CivData3 {
   public int a = 0;
   public float w = 0.0F;
   public float e = 0.0F;
   public float t = 0.0F;
   public float c = 0.0F;

   public float getInflation() {
      return this.t;
   }

   public void setInflation(float fInflation) {
      this.t = fInflation;
   }

   public float getCorruption() {
      return this.c;
   }

   public void setCorruption(float fCorruption) {
      this.c = fCorruption;
   }
}
