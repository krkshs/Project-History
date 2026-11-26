package aoc.kingdoms.lukasz.map.battles;

import aoc.kingdoms.lukasz.map.army.ArmyRegiment;

public class BattleRegiment {
   public ArmyRegiment aR;
   public int c;
   public String aK;
   public int rn;
   public int ca;
   public int re;
   public int cL = 0;
   public int rL = 0;
   public int d = 0;
   public byte f = 0;
   public byte l = 0;

   public BattleRegiment() {
   }

   public BattleRegiment(int iCivID, ArmyRegiment armyRegiment, String sArmyDivisionKey) {
      this.c = iCivID;
      this.aR = armyRegiment;
      this.aK = sArmyDivisionKey;
   }
}
