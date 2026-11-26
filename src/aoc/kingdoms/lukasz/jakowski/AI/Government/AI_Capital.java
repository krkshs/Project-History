package aoc.kingdoms.lukasz.jakowski.AI.Government;

import aoc.kingdoms.lukasz.jakowski.Game;

public class AI_Capital {
   public static void checkCapital(int civID) {
      if (Game.getCiv(civID).getCapitalProvinceID() < 0 || Game.getProvince(Game.getCiv(civID).getCapitalProvinceID()).getCivID() != civID) {
         Game.getCiv(civID).moveCapital_ToLargestProvince();
      }
   }
}
