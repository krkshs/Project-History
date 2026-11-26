package aoc.kingdoms.lukasz.map;

import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import java.util.ArrayList;
import java.util.List;

public class RivalsManager {
   public static float getLegacy(int civID, int rivalID) {
      return GameValues.rivals.RIVAL_LEGACY_MIN;
   }

   public static int getManpower(int civID, int rivalID) {
      return (int)(
         GameValues.rivals.RIVAL_MANPOWER_MIN
            * Math.max(0.05F, 1.0F - Game.getDistance_PercOfMax(Game.getCiv(civID).getCapitalProvinceID(), Game.getCiv(rivalID).getCapitalProvinceID()))
      );
   }

   public static final List<Integer> buildRivals(int civID, int rivalsLimit) {
      List<Integer> rivals = new ArrayList<>();
      List<Float> distance = new ArrayList<>();

      for (int i = 1; i < civID; i++) {
         if (!Game.getCiv(civID).diplomacy.isRival(i)) {
            float tDistance = buildRivals_IsInDistance_Perc(civID, i);
            if (tDistance >= 0.0F) {
               rivals.add(i);
               distance.add(tDistance);
            }
         }
      }

      for (int ix = civID + 1; ix < Game.getCivsSize(); ix++) {
         if (!Game.getCiv(civID).diplomacy.isRival(ix)) {
            float tDistance = buildRivals_IsInDistance_Perc(civID, ix);
            if (tDistance >= 0.0F) {
               rivals.add(ix);
               distance.add(tDistance);
            }
         }
      }

      if (rivals.size() <= rivalsLimit) {
         return rivals;
      } else {
         List<Integer> out = new ArrayList<>();
         int ixx = 0;

         for (int iSize = distance.size(); ixx < iSize; ixx++) {
            float tDistance = Math.abs(Game.getCiv(civID).iCivRankScore - Game.getCiv(rivals.get(ixx)).iCivRankScore);
            distance.set(
               ixx,
               tDistance * GameValues.rivals.RIVALS_SCORE_MIN
                  + tDistance
                     * GameValues.rivals.RIVALS_SCORE_DISTANCE
                     * (distance.get(ixx) / Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).RIVALS_DISTANCE)
            );
         }

         while (out.size() <= rivalsLimit) {
            ixx = 0;
            int ixxx = 1;

            for (int iSize = rivals.size(); ixxx < iSize; ixxx++) {
               if (distance.get(ixxx) < distance.get(ixx)) {
                  ixx = ixxx;
               }
            }

            out.add(rivals.get(ixx));
            rivals.remove(ixx);
            distance.remove(ixx);
         }

         return out;
      }
   }

   public static boolean buildRivals_IsInDistance(int civID, int rivalID) {
      return Game.getCiv(rivalID).getNumOfProvinces() <= 0
         ? false
         : !(
            Game.getDistance_PercOfMax(Game.getCiv(civID).getCapitalProvinceID(), Game.getCiv(rivalID).getCapitalProvinceID())
               > Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).RIVALS_DISTANCE
         );
   }

   public static float buildRivals_IsInDistance_Perc(int civID, int rivalID) {
      if (Game.getCiv(rivalID).getNumOfProvinces() <= 0) {
         return -1.0F;
      } else {
         float distancePerc = Game.getDistance_PercOfMax(Game.getCiv(civID).getCapitalProvinceID(), Game.getCiv(rivalID).getCapitalProvinceID());
         return distancePerc > Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).RIVALS_DISTANCE ? -1.0F : distancePerc;
      }
   }

   public static List<Integer> getRivaledBy(int civID) {
      List<Integer> out = new ArrayList<>();

      for (int i = 1; i < civID; i++) {
         if (Game.getCiv(i).getNumOfProvinces() > 0 && Game.getCiv(i).diplomacy.isRival(civID)) {
            out.add(i);
         }
      }

      for (int ix = civID + 1; ix < Game.getCivsSize(); ix++) {
         if (Game.getCiv(ix).getNumOfProvinces() > 0 && Game.getCiv(ix).diplomacy.isRival(civID)) {
            out.add(ix);
         }
      }

      return out;
   }
}
