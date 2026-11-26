package aoc.kingdoms.lukasz.map.civilization.stats;

public class CivilizationEventsData2 {
   public int b = 0;
   public int a = 0;
   public int e = 0;
   public int m = 0;
   public int c = 0;

   public int getBuildingsConstructed() {
      return this.b;
   }

   public void addBuildingsConstructed(int buildings_constructed) {
      this.b += buildings_constructed;
   }

   public int getAdministrativeBuildingsConstructed() {
      return this.a;
   }

   public void addAdministrativeBuildingsConstructed(int administrative_buildings_constructed) {
      this.a += administrative_buildings_constructed;
   }

   public int getEconomyBuildingsConstructed() {
      return this.e;
   }

   public void addEconomyBuildingsConstructed(int economy_buildings_constructed) {
      this.e += economy_buildings_constructed;
   }

   public int getMilitaryBuildingsConstructed() {
      return this.m;
   }

   public void addMilitaryBuildingsConstructed(int military_buildings_constructed) {
      this.m += military_buildings_constructed;
   }

   public int getCapitalBuildingsConstructed() {
      return this.c;
   }

   public void addCapitalBuildingsConstructed(int capital_buildings_constructed) {
      this.c += capital_buildings_constructed;
   }
}
