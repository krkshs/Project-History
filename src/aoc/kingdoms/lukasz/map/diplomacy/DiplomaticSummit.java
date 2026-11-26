package aoc.kingdoms.lukasz.map.diplomacy;

import java.util.ArrayList;
import java.util.List;

public class DiplomaticSummit {
   public int civHostID;
   public List<Integer> invitedCivs = new ArrayList<>();
   public int endTurnID = 0;

   public boolean isInvited(int nCivID) {
      for (int i = this.invitedCivs.size() - 1; i >= 0; i--) {
         if (this.invitedCivs.get(i) == nCivID) {
            return true;
         }
      }

      return false;
   }
}
