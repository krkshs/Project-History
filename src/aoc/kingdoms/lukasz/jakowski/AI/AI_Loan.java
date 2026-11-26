package aoc.kingdoms.lukasz.jakowski.AI;

import aoc.kingdoms.lukasz.jakowski.Game;

public class AI_Loan {
   public static boolean canTakeLoan(int civID) {
      return Game.getCiv(civID).iLoansSize < Game.aiValues.LOANS_LIMIT;
   }

   public static boolean takeLoan_Chance(int civID, int chance) {
      return Game.oR.nextInt(100) < chance ? takeLoan(civID) : false;
   }

   public static boolean takeLoan(int civID) {
      if (Game.getCiv(civID).iLoansSize >= Game.getLoanMaxNumber(civID)) {
         return false;
      } else if (Game.getCiv(civID).fGold > Game.aiValues.TAKE_LOAN_ONLY_IF_TREASURY_BELOW) {
         return false;
      } else {
         Game.getCiv(civID).takeLoan();
         Game.getCiv(civID).reduceInflation();
         return true;
      }
   }
}
