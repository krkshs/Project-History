package aoc.kingdoms.lukasz.map.civilization;

public class CivilizationGoldenAge {
   private int p = 0;
   private int m = 0;
   private int s = 0;

   public int getGoldenAgeProsperity() {
      return this.p;
   }

   public void setGoldenAgeProsperity(int golden_age_prosperity) {
      this.p = golden_age_prosperity;
   }

   public int getGoldenAgeMilitary() {
      return this.m;
   }

   public void setGoldenAgeMilitary(int golden_age_military) {
      this.m = golden_age_military;
   }

   public int getGoldenAgeScience() {
      return this.s;
   }

   public void setGoldenAgeScience(int golden_age_science) {
      this.s = golden_age_science;
   }
}
