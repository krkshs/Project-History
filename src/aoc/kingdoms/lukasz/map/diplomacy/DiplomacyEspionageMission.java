package aoc.kingdoms.lukasz.map.diplomacy;

import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;

public class DiplomacyEspionageMission {
   public int iCivID;
   public int iReportTurnID;
   public int iReportExpiresTurnID;

   public DiplomacyEspionageMission() {
   }

   public DiplomacyEspionageMission(int iCivID, int iReportTurnID) {
      this.iCivID = iCivID;
      this.iReportTurnID = iReportTurnID;
      this.iReportExpiresTurnID = iReportTurnID + GameValues.spy.SEND_SPY_REPORT_ACTIVE;
   }

   public boolean isActive() {
      return Game_Calendar.TURN_ID >= this.iReportTurnID;
   }
}
