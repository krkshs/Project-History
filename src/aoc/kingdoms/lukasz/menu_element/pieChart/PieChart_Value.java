package aoc.kingdoms.lukasz.menu_element.pieChart;

public class PieChart_Value {
   private int iDataID;
   private double fValue = 1.0;
   private float fPercentage;

   public PieChart_Value(int iDataID, float fValue) {
      this.iDataID = iDataID;
      this.fValue = fValue;
   }

   public final int getDataID() {
      return this.iDataID;
   }

   public final double getValue() {
      return this.fValue;
   }

   public final float getPercentage() {
      return this.fPercentage;
   }

   public final void setPercentage(float fPercentage) {
      this.fPercentage = fPercentage;
   }
}
