package aoc.kingdoms.lukasz.map.civilization;

import java.util.ArrayList;
import java.util.List;

public class CivilizationEventsData_Variables {
   public List<String> v = new ArrayList<>();

   public void addVariable(String sVariable) {
      for (int i = this.v.size() - 1; i >= 0; i--) {
         if (this.v.get(i).equals(sVariable)) {
            return;
         }
      }

      this.v.add(sVariable);
   }

   public boolean hasVariable(String sVariable) {
      for (int i = this.v.size() - 1; i >= 0; i--) {
         if (this.v.get(i).equals(sVariable)) {
            return true;
         }
      }

      return false;
   }
}
