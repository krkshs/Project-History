package aoc.kingdoms.lukasz.jakowski.AI.Army;

import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.map.province.Province;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class AI_MoveNoConnection {
   public Map<AI_MoveNoConnectionData, Integer> noConnectionMap = new HashMap<>();

   public final void addProvince(int fromProvince, int toProvince) {
      this.noConnectionMap.put(new AI_MoveNoConnectionData(fromProvince, toProvince), Game_Calendar.TURN_ID + Game.aiValuesArmies.AT_WAR_NO_CONNECTION_DAYS);
   }

   public void update() {
      Iterator<Entry<AI_MoveNoConnectionData, Integer>> iterator = this.noConnectionMap.entrySet().iterator();

      while (iterator.hasNext()) {
         if (iterator.next().getValue() < Game_Calendar.TURN_ID) {
            iterator.remove();
         }
      }
   }

   public boolean contains(int fromProvince, int toProvince) {
      AI_MoveNoConnectionData keyCheck = new AI_MoveNoConnectionData(fromProvince, toProvince);
      if (this.noConnectionMap.containsKey(keyCheck)) {
         int turnID = this.noConnectionMap.get(keyCheck);
         if (turnID < Game_Calendar.TURN_ID) {
            this.noConnectionMap.remove(keyCheck);
            return false;
         } else {
            return true;
         }
      } else {
         if (Game.getProvince(fromProvince).getCivID() != Game.getProvince(toProvince).getCivID()) {
            Iterator<Entry<AI_MoveNoConnectionData, Integer>> iterator = this.noConnectionMap.entrySet().iterator();

            while (iterator.hasNext()) {
               Entry<AI_MoveNoConnectionData, Integer> entry = iterator.next();
               AI_MoveNoConnectionData key = entry.getKey();
               if (entry.getValue() < Game_Calendar.TURN_ID) {
                  iterator.remove();
               } else if (this.isTheSameRegion(Game.getProvince(key.fromProvince), fromProvince)
                  && this.isTheSameRegion(Game.getProvince(key.toProvince), toProvince)) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public boolean isTheSameRegion(Province provinceA, int provinceB) {
      return provinceA.getCivRegionID() == Game.getProvince(provinceB).getCivRegionID() && provinceA.getCivRegionID() >= 0;
   }
}
