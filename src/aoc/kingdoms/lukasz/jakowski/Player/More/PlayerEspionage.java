package aoc.kingdoms.lukasz.jakowski.Player.More;

import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyEspionageMission;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import java.util.ArrayList;
import java.util.List;

public class PlayerEspionage {
   public List<DiplomacyEspionageMission> espionageMissions = new ArrayList<>();
   public int iEspionageMissionsSize = 0;

   public final void addEspionageMission(int iCivID) {
      for (int i = 0; i < this.iEspionageMissionsSize; i++) {
         if (this.espionageMissions.get(i).iCivID == iCivID) {
            return;
         }
      }

      this.espionageMissions.add(new DiplomacyEspionageMission(iCivID, Game_Calendar.TURN_ID + DiplomacyManager.sendSpyTime(Game.player.iCivID, iCivID)));
      this.iEspionageMissionsSize = this.espionageMissions.size();
   }

   public final void removeExpiredEspionageMissions() {
      if (this.iEspionageMissionsSize > 0) {
         for (int i = this.iEspionageMissionsSize - 1; i >= 0; i--) {
            if (this.espionageMissions.get(i).iReportExpiresTurnID <= Game_Calendar.TURN_ID) {
               this.espionageMissions.remove(i);
            }
         }

         this.iEspionageMissionsSize = this.espionageMissions.size();
      }
   }

   public final void removeEspionageMission(int iRemoveCivID) {
      for (int i = 0; i < this.iEspionageMissionsSize; i++) {
         if (this.espionageMissions.get(i).iCivID == iRemoveCivID) {
            this.espionageMissions.remove(i);
            return;
         }
      }
   }

   public final boolean espionageMission_IsAdded(int iCivID) {
      for (int i = 0; i < this.iEspionageMissionsSize; i++) {
         if (this.espionageMissions.get(i).iCivID == iCivID) {
            return true;
         }
      }

      return false;
   }

   public final int espionageMission_ReportEndTurn(int iCivID) {
      for (int i = 0; i < this.iEspionageMissionsSize; i++) {
         if (this.espionageMissions.get(i).iCivID == iCivID) {
            return this.espionageMissions.get(i).iReportExpiresTurnID;
         }
      }

      return 0;
   }

   public final void clearEspionageMissions() {
      this.espionageMissions.clear();
      this.iEspionageMissionsSize = 0;
   }
}
