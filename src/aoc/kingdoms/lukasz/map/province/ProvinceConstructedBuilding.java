package aoc.kingdoms.lukasz.map.province;

public class ProvinceConstructedBuilding {
   private int b0;
   private int b1;

   public ProvinceConstructedBuilding(int building, int buildingID) {
      this.b0 = building;
      this.b1 = buildingID;
   }

   public int getBuilding() {
      return this.b0;
   }

   public void setBuilding(int b0) {
      this.b0 = b0;
   }

   public int getBuildingID() {
      return this.b1;
   }

   public void setBuildingID(int b1) {
      this.b1 = b1;
   }
}
