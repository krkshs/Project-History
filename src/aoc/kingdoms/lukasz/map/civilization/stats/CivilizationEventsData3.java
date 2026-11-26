package aoc.kingdoms.lukasz.map.civilization.stats;

public class CivilizationEventsData3 {
   public int a = 0;
   public int p = 0;
   public int w = 0;

   public int getRecruitedAdvisors() {
      return this.a;
   }

   public void addRecruitedAdvisors(int recruitedAdvisors) {
      this.a += recruitedAdvisors;
   }

   public int getConqueredProvinces() {
      return this.p;
   }

   public void addConqueredProvinces(int conquered_provinces) {
      this.p += conquered_provinces;
   }

   public int getNumOfWars() {
      return this.w;
   }

   public void addNumOfWars(int wars) {
      this.w += wars;
   }
}
