package aoc.kingdoms.lukasz.map.army;

public class ArmyPositionSize {
   public String key;
   public int provinceID;
   public int armySize;

   public ArmyPositionSize(int provinceID, String key, int armySize) {
      this.key = key;
      this.provinceID = provinceID;
      this.armySize = armySize;
   }
}
