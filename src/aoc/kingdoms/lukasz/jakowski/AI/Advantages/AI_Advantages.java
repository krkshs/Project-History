package aoc.kingdoms.lukasz.jakowski.AI.Advantages;

import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.map.AdvantagesManager;
import java.util.ArrayList;
import java.util.List;

public class AI_Advantages {
   public static final void takeAdvantages(int civID) {
      if (Game.getCiv(civID).getAdvantagePoints() > 0) {
         for (int i = Game.getCiv(civID).getAdvantagePoints() - 1; i >= 0; i--) {
            if (!takeAdvantage(civID)) {
               return;
            }
         }
      }
   }

   public static final boolean takeAdvantage(int civID) {
      List<Integer> possibleToUnlock = new ArrayList<>();
      int totalScore = 0;

      for (int i = 0; i < AdvantagesManager.iAdvantagesSize; i++) {
         if (Game.getCiv(civID).getTechResearched(AdvantagesManager.advantages.get(i).RequiredTechID) && !Game.getCiv(civID).haveAdvantageMaxLvl(i)) {
            possibleToUnlock.add(i);
            totalScore += AdvantagesManager.advantages.get(i).AI;
         }
      }

      if (possibleToUnlock.size() == 0) {
         return false;
      } else {
         if (totalScore <= 0) {
            AdvantagesManager.unlockAdvantage(civID, possibleToUnlock.get(Game.oR.nextInt(possibleToUnlock.size())));
         } else {
            int select = Game.oR.nextInt(totalScore);
            int ix = 0;
            int cScore = 0;

            for (int iSize = possibleToUnlock.size(); ix < iSize; ix++) {
               if (select < cScore + AdvantagesManager.advantages.get(possibleToUnlock.get(ix)).AI) {
                  AdvantagesManager.unlockAdvantage(civID, possibleToUnlock.get(ix));
                  return true;
               }

               cScore += AdvantagesManager.advantages.get(possibleToUnlock.get(ix)).AI;
            }

            AdvantagesManager.unlockAdvantage(civID, possibleToUnlock.get(0));
         }

         return true;
      }
   }

   public static final int takeAdvantage_Player(int civID) {
      List<Integer> possibleToUnlock = new ArrayList<>();
      int totalScore = 0;

      for (int i = 0; i < AdvantagesManager.iAdvantagesSize; i++) {
         if (Game.getCiv(civID).getTechResearched(AdvantagesManager.advantages.get(i).RequiredTechID) && !Game.getCiv(civID).haveAdvantageMaxLvl(i)) {
            possibleToUnlock.add(i);
            totalScore += AdvantagesManager.advantages.get(i).AI;
         }
      }

      if (possibleToUnlock.size() == 0) {
         return -1;
      } else if (totalScore <= 0) {
         AdvantagesManager.unlockAdvantage(civID, possibleToUnlock.get(Game.oR.nextInt(possibleToUnlock.size())));
         return -1;
      } else {
         int select = Game.oR.nextInt(totalScore);
         int ix = 0;
         int cScore = 0;

         for (int iSize = possibleToUnlock.size(); ix < iSize; ix++) {
            if (select < cScore + AdvantagesManager.advantages.get(possibleToUnlock.get(ix)).AI) {
               AdvantagesManager.unlockAdvantage(civID, possibleToUnlock.get(ix));
               return possibleToUnlock.get(ix);
            }

            cScore += AdvantagesManager.advantages.get(possibleToUnlock.get(ix)).AI;
         }

         AdvantagesManager.unlockAdvantage(civID, possibleToUnlock.get(0));
         return possibleToUnlock.get(0);
      }
   }
}
