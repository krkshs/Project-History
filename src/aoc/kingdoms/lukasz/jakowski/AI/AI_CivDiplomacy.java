package aoc.kingdoms.lukasz.jakowski.AI;

import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.AI.Diplomacy.AI_PrepareForWar_Data;
import java.util.ArrayList;
import java.util.List;

public class AI_CivDiplomacy {
   public List<AI_PrepareForWar_Data> w = new ArrayList<>();
   public List<AI_PrepareForWar_Data> p = new ArrayList<>();

   public boolean addPrepareForWar(int onCivID) {
      for (int i = this.w.size() - 1; i >= 0; i--) {
         if (this.w.get(i).c == onCivID) {
            return false;
         }
      }

      this.w
         .add(
            new AI_PrepareForWar_Data(
               onCivID,
               Game_Calendar.TURN_ID
                  + Game.aiValuesDiplomacy.AI_PREPARE_FOR_WAR_TURNS_MIN
                  + Game.oR.nextInt(Game.aiValuesDiplomacy.AI_PREPARE_FOR_WAR_TURNS_RANDOM)
            )
         );
      return true;
   }

   public boolean isPreparingForWarWithCivID(int onCivID) {
      for (int i = this.w.size() - 1; i >= 0; i--) {
         if (this.w.get(i).c == onCivID) {
            return true;
         }
      }

      return false;
   }

   public void clearPrepareForWar() {
      this.w.clear();
   }

   public boolean addPrepareForAlliance(int onCivID) {
      for (int i = this.p.size() - 1; i >= 0; i--) {
         if (this.p.get(i).c == onCivID) {
            return false;
         }
      }

      this.p.add(new AI_PrepareForWar_Data(onCivID, Game_Calendar.TURN_ID + Game.aiValuesDiplomacy.AI_FIND_ALLY_EXPIRE));
      return true;
   }

   public void removePreparingForAllianceWithCivID(int onCivID) {
      for (int i = this.p.size() - 1; i >= 0; i--) {
         if (this.p.get(i).c == onCivID) {
            this.p.remove(i);
            return;
         }
      }
   }

   public boolean isPreparingForAllianceWithCivID(int onCivID) {
      for (int i = this.p.size() - 1; i >= 0; i--) {
         if (this.p.get(i).c == onCivID) {
            return true;
         }
      }

      return false;
   }

   public void clearPrepareForAlliance() {
      this.p.clear();
   }
}
