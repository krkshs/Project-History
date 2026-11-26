package aoc.kingdoms.lukasz.events.triggers;

import java.util.ArrayList;
import java.util.List;

public class EventTrigger {
   public List<EventTrigger_Value> triggersAnd = new ArrayList<>();
   public List<EventTrigger_Value> triggersAndNot = new ArrayList<>();
   public List<EventTrigger_Value> triggersOr = new ArrayList<>();
   public List<EventTrigger_Value> triggersOrNot = new ArrayList<>();

   public void addTrigger(EventTrigger_Value trigger, int typeID) {
      switch (typeID) {
         case 0:
            this.triggersAnd.add(trigger);
            break;
         case 1:
            this.triggersAndNot.add(trigger);
            break;
         case 2:
            this.triggersOr.add(trigger);
            break;
         case 3:
            this.triggersOrNot.add(trigger);
      }
   }

   public final boolean runTriggers(int iCivID, int iProvinceID) {
      for (int i = this.triggersOr.size() - 1; i >= 0; i--) {
         if (this.triggersOr.get(i).outCondition(iCivID, iProvinceID)) {
            return true;
         }
      }

      for (int ix = this.triggersOrNot.size() - 1; ix >= 0; ix--) {
         if (!this.triggersOrNot.get(ix).outCondition(iCivID, iProvinceID)) {
            return true;
         }
      }

      for (int ixx = this.triggersAnd.size() - 1; ixx >= 0; ixx--) {
         if (!this.triggersAnd.get(ixx).outCondition(iCivID, iProvinceID)) {
            return false;
         }
      }

      for (int ixxx = this.triggersAndNot.size() - 1; ixxx >= 0; ixxx--) {
         if (this.triggersAndNot.get(ixxx).outCondition(iCivID, iProvinceID)) {
            return false;
         }
      }

      return this.triggersAnd.size() > 0 || this.triggersAndNot.size() > 0;
   }
}
