package aoc.kingdoms.lukasz.map.province;

public class ProvincePopulation {
   private int c;
   private int n;

   public ProvincePopulation() {
   }

   public ProvincePopulation(int iCivID, int iPopulation) {
      this.c = iCivID;
      this.n = iPopulation;
   }

   public final int getCivID() {
      return this.c;
   }

   public final int getPopulation() {
      return this.n;
   }

   public final void setPopulation(int iPopulation) {
      this.n = iPopulation;
   }
}
