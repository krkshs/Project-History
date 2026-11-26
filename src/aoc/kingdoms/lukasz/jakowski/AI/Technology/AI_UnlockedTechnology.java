package aoc.kingdoms.lukasz.jakowski.AI.Technology;

import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.AI.Advantages.AI_Advantages;
import aoc.kingdoms.lukasz.jakowski.AI.Laws.AI_Laws;
import aoc.kingdoms.lukasz.map.army.ArmyManager;

public class AI_UnlockedTechnology {
   public static final void unlockedTechnology(int civID, int techID) {
      AI_Advantages.takeAdvantages(civID);
      if (Game.getCiv(civID).getNumOfProvinces() > 0) {
         AI_Laws.adoptNewLaws(civID);
         ArmyManager.upgradeAllArmies(civID);
         AI_SelectTechnology.selectTechnology(civID);
      }
   }
}
