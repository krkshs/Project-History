package aoc.kingdoms.lukasz.map.civilization.save;

import aoc.kingdoms.lukasz.jakowski.Game;

public class CivData2 {
   public int g = 0;
   public int l = 0;
   public int m = 0;
   public int d = 0;

   public CivData2() {
   }

   public CivData2(int civID) {
      this.g = (int)(Game.getCiv(civID).fGold * 100.0F);
      this.l = (int)(Game.getCiv(civID).fLegacy * 100.0F);
      this.m = (int)(Game.getCiv(civID).fManpower * 100.0);
      this.d = (int)(Game.getCiv(civID).fDiplomacy * 100.0F);
   }

   public void update(int civID) {
      Game.getCiv(civID).fGold = this.g / 100.0F;
      Game.getCiv(civID).fLegacy = this.l / 100.0F;
      Game.getCiv(civID).fManpower = this.m / 100.0F;
      Game.getCiv(civID).fDiplomacy = this.d / 100.0F;
   }
}
