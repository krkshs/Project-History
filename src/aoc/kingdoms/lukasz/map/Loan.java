package aoc.kingdoms.lukasz.map;

import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.SaveLoad.SaveGameManager;

public class Loan {
   public float fLoanValue;
   public float fInterestPerMonth;
   public int iExpires_TurnID;

   public Loan(float fLoanValue) {
      this.fLoanValue = fLoanValue;
      this.iExpires_TurnID = Game_Calendar.TURN_ID + Game.getLoanExpires();
      this.fInterestPerMonth = Game.getLoanMonthlyExpenses(fLoanValue);
   }

   public Loan(SaveGameManager.Save_Civ_Loan nLoan) {
      this.fLoanValue = nLoan.l;
      this.iExpires_TurnID = nLoan.e;
      this.fInterestPerMonth = nLoan.n;
   }

   public float getLoanValueLeft() {
      return this.fLoanValue * (1.0F - Math.min(1.0F, (float)(this.iExpires_TurnID - Game_Calendar.TURN_ID) / Game.getLoanExpires()));
   }
}
