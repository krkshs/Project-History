package aoc.kingdoms.lukasz.map.army;

public class ArmyPosition {
   public String key;
   public int provinceID;

   public ArmyPosition() {
   }

   public ArmyPosition(int provinceID, String key) {
      this.key = key;
      this.provinceID = provinceID;
   }
}
