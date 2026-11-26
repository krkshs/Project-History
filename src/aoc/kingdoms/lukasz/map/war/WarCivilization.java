package aoc.kingdoms.lukasz.map.war;

public class WarCivilization {
   public int iCivID = 0;
   public int iCasualties = 0;

   public WarCivilization() {
   }

   public WarCivilization(int nCivID) {
      this.iCivID = nCivID;
   }

   public WarCivilization(int iCivID, int iCasualties) {
      this.iCivID = iCivID;
      this.iCasualties = iCasualties;
   }
}
