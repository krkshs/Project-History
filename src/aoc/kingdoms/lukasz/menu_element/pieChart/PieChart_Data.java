package aoc.kingdoms.lukasz.menu_element.pieChart;

import aoc.kingdoms.lukasz.jakowski.Game;
import java.util.ArrayList;
import java.util.List;

public class PieChart_Data {
   private List<PieChart_Value> pieChartValues = new ArrayList<>();
   private int pieChartValuesSize = 0;

   public float getPieChartValue_ColorR(int i) {
      return Game.getCiv(this.getPieChartValue(i).getDataID()).getR();
   }

   public float getPieChartValue_ColorG(int i) {
      return Game.getCiv(this.getPieChartValue(i).getDataID()).getG();
   }

   public float getPieChartValue_ColorB(int i) {
      return Game.getCiv(this.getPieChartValue(i).getDataID()).getB();
   }

   public int getCivID(int i) {
      return this.getPieChartValue(i).getDataID();
   }

   public final void sortAndBuild_PieChartValues() {
      List<PieChart_Value> tempPieChartValues = new ArrayList<>();
      float countedValues = 0.0F;

      for (int i = 0; i < this.getPieChartValuesSize(); i++) {
         tempPieChartValues.add(this.getPieChartValue(i));
         countedValues = (float)(countedValues + this.getPieChartValue(i).getValue());
      }

      this.pieChartValues.clear();

      while (tempPieChartValues.size() > 0) {
         int nMinID = 0;

         for (int i = 1; i < tempPieChartValues.size(); i++) {
            if (tempPieChartValues.get(nMinID).getValue() < tempPieChartValues.get(i).getValue()) {
               nMinID = i;
            }
         }

         this.pieChartValues.add(tempPieChartValues.get(nMinID));
         tempPieChartValues.remove(nMinID);
      }

      for (int ix = 0; ix < this.getPieChartValuesSize(); ix++) {
         this.getPieChartValue(ix).setPercentage((float)(this.getPieChartValue(ix).getValue() * 100.0 / countedValues));
      }
   }

   public final void addPieChartValues(PieChart_Value nPieChartValue) {
      this.pieChartValues.add(nPieChartValue);
      this.pieChartValuesSize = this.pieChartValues.size();
   }

   public final PieChart_Value getPieChartValue(int i) {
      return this.pieChartValues.get(i);
   }

   public final int getPieChartValuesSize() {
      return this.pieChartValuesSize;
   }
}
