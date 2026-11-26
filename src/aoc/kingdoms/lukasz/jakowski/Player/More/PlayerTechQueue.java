package aoc.kingdoms.lukasz.jakowski.Player.More;

import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.map.technology.TechnologyTree;
import java.util.ArrayList;
import java.util.List;

public class PlayerTechQueue {
   public List<Integer> lTechQueue = new ArrayList<>();

   public final void buildTechQueue(int iTechID) {
      this.lTechQueue.clear();
      this.lTechQueue.add(iTechID);
      this.buildTechQueue1(iTechID);
      this.buildTechQueue2(iTechID);
      List<Integer> nQueue = new ArrayList<>();
      List<Integer> nAvailable = new ArrayList<>();

      for (int i = this.lTechQueue.size() - 1; i >= 0; i--) {
         if (Game.getCiv(Game.player.iCivID).getAvailableToResearch(this.lTechQueue.get(i))) {
            nAvailable.add(this.lTechQueue.get(i));
            this.lTechQueue.remove(i);
         }
      }

      while (nAvailable.size() > 0) {
         int bestID = 0;

         for (int ix = nAvailable.size() - 1; ix > 0; ix--) {
            if (TechnologyTree.lTechnology.get(nAvailable.get(ix)).getResearchCost() < TechnologyTree.lTechnology.get(nAvailable.get(bestID)).getResearchCost()
               || TechnologyTree.lTechnology.get(nAvailable.get(ix)).TreeColumn < TechnologyTree.lTechnology.get(nAvailable.get(bestID)).TreeColumn) {
               bestID = ix;
            }
         }

         nQueue.add(nAvailable.get(bestID));
         nAvailable.remove(bestID);
      }

      while (this.lTechQueue.size() > 0) {
         int bestID = 0;

         for (int ixx = this.lTechQueue.size() - 1; ixx > 0; ixx--) {
            if (TechnologyTree.lTechnology.get(this.lTechQueue.get(ixx)).TreeColumn < TechnologyTree.lTechnology.get(this.lTechQueue.get(bestID)).TreeColumn) {
               bestID = ixx;
            }
         }

         nQueue.add(this.lTechQueue.get(bestID));
         this.lTechQueue.remove(bestID);
      }

      this.lTechQueue = nQueue;
   }

   private final void buildTechQueue1(int iTechID) {
      if (TechnologyTree.lTechnology.get(iTechID).RequiredTech >= 0
         && !Game.getCiv(Game.player.iCivID).getTechResearched(TechnologyTree.lTechnology.get(iTechID).RequiredTech)) {
         this.addTechQueue(TechnologyTree.lTechnology.get(iTechID).RequiredTech);
         this.buildTechQueue1(TechnologyTree.lTechnology.get(iTechID).RequiredTech);
         this.buildTechQueue2(TechnologyTree.lTechnology.get(iTechID).RequiredTech);
      }
   }

   private final void buildTechQueue2(int iTechID) {
      if (TechnologyTree.lTechnology.get(iTechID).RequiredTech2 >= 0
         && !Game.getCiv(Game.player.iCivID).getTechResearched(TechnologyTree.lTechnology.get(iTechID).RequiredTech2)) {
         this.addTechQueue(TechnologyTree.lTechnology.get(iTechID).RequiredTech2);
         this.buildTechQueue1(TechnologyTree.lTechnology.get(iTechID).RequiredTech2);
         this.buildTechQueue2(TechnologyTree.lTechnology.get(iTechID).RequiredTech2);
      }
   }

   private final boolean addTechQueue(int iTechID) {
      if (iTechID < 0) {
         return false;
      } else {
         for (int i = this.lTechQueue.size() - 1; i >= 0; i--) {
            if (this.lTechQueue.get(i) == iTechID) {
               return false;
            }
         }

         this.lTechQueue.add(iTechID);
         return true;
      }
   }

   public final int getTechQueue() {
      List<Integer> possibleToResearch = new ArrayList<>();
      int bestID = 0;

      for (int i = this.lTechQueue.size() - 1; i >= 0; i--) {
         if (Game.getCiv(Game.player.iCivID).getAvailableToResearch(this.lTechQueue.get(i))
            && !Game.getCiv(Game.player.iCivID).getTechResearched(this.lTechQueue.get(i))) {
            possibleToResearch.add(this.lTechQueue.get(i));
         }
      }

      if (possibleToResearch.size() <= 0) {
         return -1;
      } else {
         for (int ix = possibleToResearch.size() - 1; ix >= 1; ix--) {
            if (TechnologyTree.lTechnology.get(possibleToResearch.get(ix)).getResearchCost()
                  < TechnologyTree.lTechnology.get(possibleToResearch.get(bestID)).getResearchCost()
               || TechnologyTree.lTechnology.get(possibleToResearch.get(ix)).TreeColumn
                  < TechnologyTree.lTechnology.get(possibleToResearch.get(bestID)).TreeColumn) {
               bestID = ix;
            }
         }

         for (int ixx = this.lTechQueue.size() - 1; ixx >= 0; ixx--) {
            if (this.lTechQueue.get(ixx) == possibleToResearch.get(bestID)) {
               this.lTechQueue.remove(ixx);
               break;
            }
         }

         return possibleToResearch.get(bestID);
      }
   }

   public int getTechIsInQueue(int iTechID) {
      for (int i = this.lTechQueue.size() - 1; i >= 0; i--) {
         if (this.lTechQueue.get(i) == iTechID) {
            return i;
         }
      }

      return -1;
   }

   public void clearTechQueue() {
      this.lTechQueue.clear();
   }
}
