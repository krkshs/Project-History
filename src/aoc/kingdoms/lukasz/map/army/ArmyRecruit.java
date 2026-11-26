package aoc.kingdoms.lukasz.map.army;

public class ArmyRecruit {
   public int provinceID;
   public int unitID;
   public int armyID;
   public int timeLeft;
   public int cost;
   public String toArmyKey = null;

   public ArmyRecruit() {
   }

   public ArmyRecruit(int iProvinceID, int iUnitID, int iArmyID, String assignToArmyKey) {
      this.provinceID = iProvinceID;
      this.unitID = iUnitID;
      this.armyID = iArmyID;
      this.toArmyKey = assignToArmyKey;
   }
}
