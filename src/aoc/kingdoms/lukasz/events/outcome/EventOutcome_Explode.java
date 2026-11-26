package aoc.kingdoms.lukasz.events.outcome;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.map.civilization.Civilization;
import aoc.kingdoms.lukasz.map.technology.TechnologyTree;
import aoc.kingdoms.lukasz.textures.Images;
import java.util.ArrayList;
import java.util.List;

public class EventOutcome_Explode extends EventOutcome {
   public String civTAG;

   public EventOutcome_Explode(String civTAG) {
      this.civTAG = civTAG;
   }

   @Override
   public void updateCiv(int iCivID, int bonus_duration) {
      try {
         int civID = Game.getCivID(this.civTAG);
         if (civID > 0) {
            Civilization civ = Game.getCiv(civID);
            if (civ.getNumOfProvinces() > 1) {
               List<EventOutcome_Explode.ExplodeProvince> explode = new ArrayList<>();

               for (int i = 0; i < civ.getNumOfProvinces(); i++) {
                  if (civ.getProvinceID(i) != civ.getCapitalProvinceID()) {
                     int bestID = -1;

                     for (int j = 0; j < Game.getProvince(civ.getProvinceID(i)).iCoresSize; j++) {
                        if (Game.getProvince(civ.getProvinceID(i)).getCore(j) != civID
                           && Game.getCiv(Game.getProvince(civ.getProvinceID(i)).getCore(j)).getNumOfProvinces() == 0) {
                           bestID = Math.max(bestID, Game.getProvince(civ.getProvinceID(i)).getCore(j));
                        }
                     }

                     if (bestID > 0) {
                        explode.add(new EventOutcome_Explode.ExplodeProvince(bestID, civ.getProvinceID(i)));
                     }
                  }
               }

               for (int ix = explode.size() - 1; ix >= 0; ix--) {
                  Game.getProvince(explode.get(ix).provinceID).setCivID(explode.get(ix).civID);
               }

               List<Integer> civs = new ArrayList<>();

               for (int ix = explode.size() - 1; ix >= 0; ix--) {
                  if (!civs.contains(explode.get(ix).civID)) {
                     civs.add(explode.get(ix).civID);
                  }
               }

               for (int ixx = 0; ixx < civs.size(); ixx++) {
                  if (Game.getCiv(civs.get(ixx)).getPuppetOfCivID() != civs.get(ixx)) {
                     Game.getCiv(civs.get(ixx)).setPuppetOfCivID(civs.get(ixx));
                  }

                  if (Game.getProvince(Game.getCiv(civs.get(ixx)).getCapitalProvinceID()).getCivID() == civs.get(ixx)) {
                     Game.getCiv(civs.get(ixx)).setCapitalProvinceID(Game.getCiv(civs.get(ixx)).getCapitalProvinceID());
                  } else if (Game.getCiv(civs.get(ixx)).getCapitalProvinceID() < 0
                     || Game.getProvince(Game.getCiv(civs.get(ixx)).getCapitalProvinceID()).getCivID() != civs.get(ixx)) {
                     Game.getCiv(civs.get(ixx)).moveCapital_ToLargestProvince();
                  }

                  Game.getCiv(civs.get(ixx)).fGold = Math.max(Game.getCiv(civs.get(ixx)).fGold, (float)GameValues.events.EVENT_EXPLODE_MIN_GOLD);
                  Game.getCiv(civs.get(ixx)).fLegacy = Math.max(Game.getCiv(civs.get(ixx)).fLegacy, (float)GameValues.events.EVENT_EXPLODE_MIN_LEGACY);
                  Game.getCiv(civs.get(ixx)).fManpower = Math.max(Game.getCiv(civs.get(ixx)).fManpower, (double)GameValues.events.EVENT_EXPLODE_MIN_MANPOWER);
                  if (civ.getResearchedTechnologies() > Game.getCiv(civs.get(ixx)).getResearchedTechnologies()) {
                     for (int a = 0; a < TechnologyTree.iTechnologySize; a++) {
                        if (civ.getTechResearched(a)) {
                           Game.getCiv(civs.get(ixx)).addTechnology(a, false);
                        }
                     }
                  }
               }
            }
         }
      } catch (Exception var9) {
         CFG.exceptionStack(var9);
      }
   }

   @Override
   public String getStringLeft() {
      return Game.lang.get("RevolutionaryMovements");
   }

   @Override
   public String getStringRight() {
      return "";
   }

   @Override
   public int getImage() {
      return Images.revolutionRisk;
   }

   public class ExplodeProvince {
      public int civID;
      public int provinceID;

      public ExplodeProvince(int civID, int provinceID) {
         this.civID = civID;
         this.provinceID = provinceID;
      }
   }
}
