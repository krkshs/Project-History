package aoc.kingdoms.lukasz.jakowski.AI.Legacies;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.map.LegacyManager;
import java.util.ArrayList;
import java.util.List;

public class AI_Legacy {
   public static void updateCiv_Legacies() {
      try {
         int i;
         for (i = 1 + Game_Calendar.TURN_ID % GameValues.gameUpdateAI.GAME_UPDATE_AI_UNLOCK_LEGACY;
            i < Game.player.iCivID;
            i += GameValues.gameUpdateAI.GAME_UPDATE_AI_UNLOCK_LEGACY
         ) {
            if (Game.getCiv(i).getNumOfProvinces() > 0) {
               unlockLegacy(i);
            }
         }

         if (i == Game.player.iCivID) {
            i += GameValues.gameUpdateAI.GAME_UPDATE_AI_UNLOCK_LEGACY;
         }

         for (; i < Game.getCivsSize(); i += GameValues.gameUpdateAI.GAME_UPDATE_AI_UNLOCK_LEGACY) {
            if (Game.getCiv(i).getNumOfProvinces() > 0) {
               unlockLegacy(i);
            }
         }
      } catch (Exception var1) {
         CFG.exceptionStack(var1);
      }
   }

   public static void unlockLegacy(int civID) {
      try {
         if (Game.getCiv(civID).fLegacy > LegacyManager.minLegacyCost) {
            List<Integer> toUnlock = new ArrayList<>();
            List<Integer> toUnlockLevel = new ArrayList<>();
            int score = 0;

            for (int i = 0; i < LegacyManager.iLegaciesSize; i++) {
               int level = Game.getCiv(civID).getLegacyLevel(i);
               if (level < 0) {
                  if (LegacyManager.legacies.get(i).CostLegacy[0] < Game.getCiv(civID).fLegacy) {
                     toUnlock.add(i);
                     score += LegacyManager.legacies.get(i).AI[0];
                     toUnlockLevel.add(0);
                  }
               } else if (level + 1 < LegacyManager.legacies.get(i).CostLegacy.length
                  && LegacyManager.legacies.get(i).CostLegacy[level + 1] < Game.getCiv(civID).fLegacy) {
                  toUnlock.add(i);
                  score += LegacyManager.legacies.get(i).AI[level + 1];
                  toUnlockLevel.add(level + 1);
               }
            }

            if (toUnlock.size() > 0) {
               int rand = 0;
               int bestID = 0;

               for (int a = 0; a < Game.aiValues.UNLOCK_LEGACIES; a++) {
                  if (score > 0 && toUnlock.size() > 0) {
                     rand = Game.oR.nextInt(score);
                     bestID = 0;
                     int ix = 0;
                     int iSize = toUnlock.size();

                     for (int currScore = 0; ix < iSize; ix++) {
                        currScore += LegacyManager.legacies.get(toUnlock.get(ix)).AI[toUnlockLevel.get(ix)];
                        if (rand < currScore) {
                           bestID = ix;
                           break;
                        }
                     }

                     Game.getCiv(civID).unlockLegacy(toUnlock.get(bestID));
                     score -= toUnlockLevel.get(bestID);
                     toUnlock.remove(bestID);
                     toUnlockLevel.remove(bestID);
                  }
               }
            }

            toUnlock.clear();
            toUnlockLevel.clear();
         }
      } catch (Exception var10) {
         CFG.exceptionStack(var10);
      }
   }
}
