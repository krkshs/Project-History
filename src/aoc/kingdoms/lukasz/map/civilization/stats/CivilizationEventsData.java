package aoc.kingdoms.lukasz.map.civilization.stats;

public class CivilizationEventsData {
   public int e = 0;
   public int g = 0;
   public int t = 0;
   public int m = 0;
   public int d = 0;

   public int getInvestedInEconomy() {
      return this.e;
   }

   public void setInvestedInEconomy(int invested_in_economy) {
      this.e = invested_in_economy;
   }

   public void addInvestedInEconomy(int invested_in_economy) {
      this.e += invested_in_economy;
   }

   public int getIncreasedGrowthRate() {
      return this.g;
   }

   public void setIncreasedGrowthRate(int increased_growth_rate) {
      this.g = increased_growth_rate;
   }

   public void addIncreasedGrowthRate(int increased_growth_rate) {
      this.g += increased_growth_rate;
   }

   public int getIncreasedTaxEfficiency() {
      return this.t;
   }

   public void setIncreasedTaxEfficiency(int increased_tax_efficiency) {
      this.t = increased_tax_efficiency;
   }

   public void addIncreasedTaxEfficiency(int increased_tax_efficiency) {
      this.t += increased_tax_efficiency;
   }

   public int getDevelopedInfrastructure() {
      return this.d;
   }

   public void setDevelopedInfrastructure(int developed_infrastructure) {
      this.d = developed_infrastructure;
   }

   public void addDevelopedInfrastructure(int developed_infrastructure) {
      this.d += developed_infrastructure;
   }

   public int getIncreasedManpower() {
      return this.m;
   }

   public void setIncreasedManpower(int increased_manpower) {
      this.m = increased_manpower;
   }

   public void addIncreasedManpower(int increased_manpower) {
      this.m += increased_manpower;
   }
}
