package aoc.kingdoms.lukasz.map.province;

public class ProvinceConstructionBuilding {
   private int building;
   private int buildingID;
   private int iConstructionTime;
   public int iConstructionTimeLeft;

   public ProvinceConstructionBuilding() {
   }

   public ProvinceConstructionBuilding(int building, int buildingID, int iConstructionTime, int iConstructionTimeLeft) {
      this.building = building;
      this.buildingID = buildingID;
      this.iConstructionTime = iConstructionTime;
      this.iConstructionTimeLeft = iConstructionTimeLeft;
   }

   public int getBuilding() {
      return this.building;
   }

   public void setBuilding(int building) {
      this.building = building;
   }

   public int getBuildingID() {
      return this.buildingID;
   }

   public void setBuildingID(int buildingID) {
      this.buildingID = buildingID;
   }

   public int getConstructionTime() {
      return this.iConstructionTime;
   }

   public void setConstructionTime(int iConstructionTime) {
      this.iConstructionTime = iConstructionTime;
   }

   public int getConstructionTimeLeft() {
      return this.iConstructionTimeLeft;
   }

   public void setConstructionTimeLeft(int iConstructionTimeLeft) {
      this.iConstructionTimeLeft = iConstructionTimeLeft;
   }
}
