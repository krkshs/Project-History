package aoc.kingdoms.lukasz.map.army;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.SaveLoad.SaveGameManager;

public class ArmyRegiment {
   public String key;
   public int uID = 0;
   public int aID = 0;
   public int num = 0;
   public float mo = 1.0F;

   public ArmyRegiment() {
   }

   public ArmyRegiment(int nUnitTypeID, int nArmyID) {
      this.uID = nUnitTypeID;
      this.aID = nArmyID;
      this.num = Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).REGIMENT_SIZE;
      this.key = CFG.extraRandomTag();
   }

   public ArmyRegiment(SaveGameManager.Save_Provinces_ArmyRegiment armyRegiment) {
      this.key = armyRegiment.k;
      this.uID = armyRegiment.u;
      this.aID = armyRegiment.a;
      this.num = armyRegiment.n;
      this.mo = armyRegiment.m;
   }
}
