package aoc.kingdoms.lukasz.jakowski.AI;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import java.util.ArrayList;
import java.util.List;

public class AI_Budget {
   public List<AI_BudgetDefault> tx = new ArrayList<>();
   public List<AI_BudgetDefault> a = new ArrayList<>();

   public final void updateBudget() {
      try {
         if (Game_Calendar.TURN_ID % GameValues.gameUpdateAI.GAME_UPDATE_AI_BUDGET_DEFAULT == 0) {
            for (int i = this.tx.size() - 1; i >= 0; i--) {
               if (Game_Calendar.TURN_ID > this.tx.get(i).t) {
                  Game.getCiv(this.tx.get(i).c).updateTaxationLevel(1);
                  this.tx.remove(i);
               }
            }

            for (int ix = this.a.size() - 1; ix >= 0; ix--) {
               if (Game_Calendar.TURN_ID > this.a.get(ix).t) {
                  if (!Game.getCiv(this.a.get(ix).c).diplomacy.isAtWar()) {
                     Game.getCiv(this.a.get(ix).c).updateMilitaryLevel(Math.max(1, Game.getCiv(this.a.get(ix).c).getMilitaryLevel()));
                  }

                  this.a.remove(ix);
               }
            }
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }

      try {
         int ixx;
         for (ixx = 1 + Game_Calendar.TURN_ID % GameValues.gameUpdateAI.GAME_UPDATE_AI_BUDGET;
            ixx < Game.player.iCivID;
            ixx += GameValues.gameUpdateAI.GAME_UPDATE_AI_BUDGET
         ) {
            if (Game.getCiv(ixx).getNumOfProvinces() > 0) {
               this.updateBudget(ixx);
            }
         }

         if (ixx == Game.player.iCivID) {
            ixx += GameValues.gameUpdateAI.GAME_UPDATE_AI_BUDGET;
         }

         for (; ixx < Game.getCivsSize(); ixx += GameValues.gameUpdateAI.GAME_UPDATE_AI_BUDGET) {
            if (Game.getCiv(ixx).getNumOfProvinces() > 0) {
               this.updateBudget(ixx);
            }
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   public final void updateBudget(int civID) {
      if (Game.oR.nextInt(100) < Game.aiValuesArmies.CHANGE_BATTLE_TACTICS_CHANCE) {
         Game.getCiv(civID).setBattleTacticsID(Game.oR.nextInt(GameValues.battleTactics.BATTLE_TACTICS.length));
      }

      if (Game.getProvince(Game.getCiv(civID).getCapitalProvinceID()).getRevulutionaryRisk() > Game.aiValues.BUDGET_DECREASE_TAXATION_IF_UNREST_IN_CAPITAL_OVER
         )
       {
         Game.getCiv(civID).updateTaxationLevel(0);
         this.tx
            .add(
               new AI_BudgetDefault(
                  civID,
                  Game_Calendar.TURN_ID
                     + Game.aiValues.BUDGET_DECREASE_TAXATION_TURNS_MIN
                     + Game.oR.nextInt(Game.aiValues.BUDGET_DECREASE_TAXATION_TURNS_RANDOM)
               )
            );
      } else if (Game.oR.nextInt(100) < Game.aiValues.BUDGET_INCREASE_TAXATION_CHANCE
         && Game.getCiv(civID).civStability_LostFrom100 <= Game.aiValues.BUDGET_INCREASE_TAXATION_IF_STABILITY_BELOW
         && Game.getProvince(Game.getCiv(civID).getCapitalProvinceID()).getRevulutionaryRisk()
            < Game.aiValues.BUDGET_INCREASE_TAXATION_IF_UNREST_IN_CAPITAL_BELOW) {
         Game.getCiv(civID).updateTaxationLevel(2);
         this.tx
            .add(
               new AI_BudgetDefault(
                  civID,
                  Game_Calendar.TURN_ID
                     + Game.aiValues.BUDGET_INCREASE_TAXATION_TURNS_MIN
                     + Game.oR.nextInt(Game.aiValues.BUDGET_INCREASE_TAXATION_TURNS_RANDOM)
               )
            );
      }

      if (!Game.getCiv(civID).diplomacy.isAtWar()
         && Game.getCiv(civID).aiCivDiplomacy.w.isEmpty()
         && Game.oR.nextInt(100) < Game.aiValues.BUDGET_DECREASE_MILITARY_CHANCE) {
         Game.getCiv(civID).updateMilitaryLevel(0);
         this.a
            .add(
               new AI_BudgetDefault(
                  civID,
                  Game_Calendar.TURN_ID
                     + Game.aiValues.BUDGET_DECREASE_MILITARY_TURNS_MIN
                     + Game.oR.nextInt(Game.aiValues.BUDGET_DECREASE_MILITARY_TURNS_RANDOM)
               )
            );
      }
   }

   public final void updateMilitaryLevel_War(int civID, int civB, int regimentsA, int regimentsB) {
      if (civID != Game.player.iCivID) {
         if ((float)Game.getCiv(civB).getArmyRegimentSize() / Game.getCiv(civID).getArmyRegimentSize()
               > GameValues.war.AI_MAX_MILITARY_LEVEL_IF_REGIMENTS_RATIO_OVER
            || (float)regimentsB / regimentsA > GameValues.war.AI_MAX_MILITARY_LEVEL_IF_REGIMENTS_RATIO_OVER) {
            Game.getCiv(civID).updateMilitaryLevel(GameValues.war.AI_AT_WAR_MAX_MILITARY_LEVEL);
         }
      }
   }
}
