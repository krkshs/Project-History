package aoc.kingdoms.lukasz.jakowski.AI.Technology;

import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.map.technology.TechnologyTree;
import java.util.ArrayList;
import java.util.List;

public class AI_SelectTechnology {
   public static void selectTechnology(int civID) {
      List<Integer> possibleResearch = new ArrayList<>();

      for (int i = 0; i < TechnologyTree.iTechnologySize; i++) {
         if (Game.getCiv(civID).getAvailableToResearch(i)) {
            possibleResearch.add(i);
         }
      }

      if (possibleResearch.size() != 0) {
         if (possibleResearch.size() == 1) {
            Game.getCiv(civID).setActiveTechResearch(possibleResearch.get(0));
            Game.getCiv(civID).iAlternativeTechResearch = -1;
         } else {
            int totalScore = 0;

            for (int ix = possibleResearch.size() - 1; ix >= 0; ix--) {
               totalScore += TechnologyTree.lTechnology.get(possibleResearch.get(ix)).AI;
            }

            if (totalScore <= 0) {
               Game.getCiv(civID).setActiveTechResearch(possibleResearch.get(0));
               Game.getCiv(civID).iAlternativeTechResearch = -1;
            } else {
               int select = Game.oR.nextInt(totalScore);
               int ix = 0;

               for (int cScore = 0; ix < possibleResearch.size(); ix++) {
                  if (select < cScore + TechnologyTree.lTechnology.get(possibleResearch.get(ix)).AI) {
                     Game.getCiv(civID).setActiveTechResearch(possibleResearch.get(ix));
                     Game.getCiv(civID).iAlternativeTechResearch = -1;
                     return;
                  }

                  cScore += TechnologyTree.lTechnology.get(possibleResearch.get(ix)).AI;
               }

               Game.getCiv(civID).setActiveTechResearch(possibleResearch.get(0));
               Game.getCiv(civID).iAlternativeTechResearch = -1;
            }
         }
      }
   }
}
