package aoc.kingdoms.lukasz.map.province.data;

import aoc.kingdoms.lukasz.map.province.ProvincePopulation;
import java.util.ArrayList;
import java.util.List;

public class ProvinceData_Population {
   private List<ProvincePopulation> p = new ArrayList<>();

   public List<ProvincePopulation> getPopulation() {
      return this.p;
   }

   public ProvincePopulation getPopulation(int i) {
      return this.p.get(i);
   }
}
