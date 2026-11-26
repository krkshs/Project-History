package aoc.kingdoms.lukasz.jakowski.AI.Army;

import java.util.Objects;

public class AI_MoveNoConnectionData {
   public int fromProvince;
   public int toProvince;

   public AI_MoveNoConnectionData(int fromProvince, int toProvince) {
      this.fromProvince = fromProvince;
      this.toProvince = toProvince;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         AI_MoveNoConnectionData that = (AI_MoveNoConnectionData)o;
         return this.fromProvince == that.fromProvince && this.toProvince == that.toProvince;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.fromProvince, this.toProvince);
   }
}
