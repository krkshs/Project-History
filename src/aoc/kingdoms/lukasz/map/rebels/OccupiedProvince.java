package aoc.kingdoms.lukasz.map.rebels;

public class OccupiedProvince {
   public int p;
   public int sinceTurnID;

   public OccupiedProvince() {
   }

   public OccupiedProvince(int provinceID, int sinceTurnID) {
      this.p = provinceID;
      this.sinceTurnID = sinceTurnID;
   }
}
