package aoc.kingdoms.lukasz.jakowski;

import java.util.ArrayList;
import java.util.List;

public class AddNewCivData {
   public String sCivTag = "";
   public List<Integer> lProvinces = new ArrayList<>();
   public int technologyID = 0;

   public AddNewCivData() {
   }

   public AddNewCivData(String sCivTag) {
      this.sCivTag = sCivTag;
   }

   public void addProvince(int provinceID) {
      for (int i = 0; i < this.lProvinces.size(); i++) {
         if (this.lProvinces.get(i) == provinceID) {
            return;
         }
      }

      this.lProvinces.add(provinceID);
   }

   public void removeProvince(int provinceID) {
      if (this.lProvinces.size() > 1) {
         for (int i = 0; i < this.lProvinces.size(); i++) {
            if (this.lProvinces.get(i) == provinceID) {
               this.lProvinces.remove(i);
               return;
            }
         }
      }
   }
}
