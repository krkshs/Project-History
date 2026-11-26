package aoc.kingdoms.lukasz.jakowski.AI.Diplomacy;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.map.civilization.Civilization;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import java.util.ArrayList;
import java.util.List;

public class AI_PrepareForWar {
   public static final void prepareForWar() {
      try {
         int i;
         for (i = 1 + Game_Calendar.TURN_ID % GameValues.gameUpdateAI.GAME_UPDATE_AI_PREPARE_FOR_WAR;
            i < Game.player.iCivID;
            i += GameValues.gameUpdateAI.GAME_UPDATE_AI_PREPARE_FOR_WAR
         ) {
            if (Game.getCiv(i).getNumOfProvinces() > 0) {
               prepareForWar(i, 0);
            }
         }

         if (i == Game.player.iCivID) {
            i += GameValues.gameUpdateAI.GAME_UPDATE_AI_PREPARE_FOR_WAR;
         }

         for (; i < Game.getCivsSize(); i += GameValues.gameUpdateAI.GAME_UPDATE_AI_PREPARE_FOR_WAR) {
            if (Game.getCiv(i).getNumOfProvinces() > 0) {
               prepareForWar(i, 0);
            }
         }
      } catch (Exception var1) {
         CFG.exceptionStack(var1);
      }
   }

   public static final void prepareForWar(int civID, int extraWarChance) {
      try {
         Civilization civ = Game.getCiv(civID);
         if (civ.fGold < Game.aiValuesDiplomacy.AI_PREPARE_FOR_WAR_ONLY_IF_GOLD_OVER) {
            civ.aiCivDiplomacy.clearPrepareForWar();
            return;
         }

         if (civ.civStability_LostFrom100 > Game.aiValuesDiplomacy.AI_PREPARE_FOR_WAR_ONLY_IF_CIV_STABILITY_IS_BELOW_100) {
            return;
         }

         if (civ.getAggressiveExpansion() > Game.aiValuesDiplomacy.AI_PREPARE_FOR_WAR_ONLY_IF_AE_IS_BELOW) {
            return;
         }

         if (civ.fManpower / civ.fManpowerMax < Game.aiValuesDiplomacy.AI_PREPARE_FOR_WAR_ONLY_IF_MANPOWER_PERC_OVER) {
            return;
         }

         if (civ.getPuppetOfCivID() != civID && !Game.getCiv(civ.getPuppetOfCivID()).diplomacy.getVassal_CanDeclareWar(civID)) {
            return;
         }

         if (civ.diplomacy.isAtWar()) {
            return;
         }

         if (!civ.aiCivDiplomacy.w.isEmpty()) {
            return;
         }

         if (Game.oR.nextInt(Game.aiValuesDiplomacy.AI_PREPARE_FOR_WAR_RANDOM_MAX)
            > Game.aiValuesDiplomacy.AI_PREPARE_FOR_WAR_CHANCE
               + Game.getCiv(civID).getExtraAggressiveness()
               + Game.ideologiesManager.getIdeology(civ.getIdeologyID()).AI_EXTRA_AGGRESSIVENESS
               + GameValues.civRank.CIV_RANK_AI_EXTRA_AGGRESSIVENESS[Game.getCiv(civID).iCivRankID]
               + Game.aiAggressivnes
               + extraWarChance) {
            return;
         }

         List<Integer> possibleCivs = new ArrayList<>();
         if (Game.aiValuesDiplomacy.AI_PREPARE_FOR_WAR_PRIORITIZE_TRIBAL
            && Game.oR.nextInt(100) < Game.aiValuesDiplomacy.AI_PREPARE_FOR_WAR_PRIORITIZE_NEIGBORS_TRIBAL_CHANCE
            && !Game.ideologiesManager.getIdeology(civ.getIdeologyID()).TRIBAL
            && !Game.religionManager.getReligion(civ.getReligionID()).Tribal) {
            for (int i = 0; i < civ.civNeighbors.civsSize; i++) {
               if ((
                     Game.ideologiesManager.getIdeology(Game.getCiv(civ.civNeighbors.civs.get(i).civID).getIdeologyID()).TRIBAL
                        || Game.religionManager.getReligion(Game.getCiv(civ.civNeighbors.civs.get(i).civID).getReligionID()).Tribal
                  )
                  && Game.getCiv(civ.civNeighbors.civs.get(i).civID).getNumOfProvinces() > 0
                  && !isCivAlly(civID, civ.civNeighbors.civs.get(i).civID)
                  && civ.getResearchedTechnologies() > Game.getCiv(civ.civNeighbors.civs.get(i).civID).getResearchedTechnologies()
                  && civ.getNumOfProvinces() > Game.getCiv(civ.civNeighbors.civs.get(i).civID).getNumOfProvinces()) {
                  possibleCivs.add(civ.civNeighbors.civs.get(i).civID);
               }
            }
         }

         if (possibleCivs.isEmpty()
            && civ.iCivRankPosition < Game.aiValuesDiplomacy.AI_PREPARE_FOR_WAR_CONQUER_TRIBAL_TOP_RANK_CIVS
            && civ.haveAccessSea
            && Game.oR.nextInt(100) < Game.aiValuesDiplomacy.AI_PREPARE_FOR_WAR_CONQUER_TRIBAL_TOP_RANK_CIVS_CHANCE) {
            List<AI_PrepareForWar.CivDistance> distance = new ArrayList<>();

            for (int ix = 1; ix < civID; ix++) {
               if (Game.getCiv(ix).getNumOfProvinces() > 0
                  && (
                     Game.ideologiesManager.getIdeology(Game.getCiv(ix).getIdeologyID()).TRIBAL
                        || Game.religionManager.getReligion(Game.getCiv(ix).getReligionID()).Tribal
                  )) {
                  distance.add(
                     new AI_PrepareForWar.CivDistance(
                        ix,
                        Game.getManhattanDistance_PercOfMax(civ.getCapitalProvinceID(), Game.getCiv(ix).getCapitalProvinceID())
                           * (
                              Game.continents.lContinents.get(Game.getProvince(Game.getCiv(ix).getCapitalProvinceID()).getContinent()).prioritizeColonization
                                 ? Game.aiValuesDiplomacy.AI_PREPARE_FOR_WAR_CONQUER_TRIBAL_PRIORITIZE_CONTINENT_MODIFIER
                                 : 1.0F
                           )
                     )
                  );
               }
            }

            for (int ixx = civID + 1; ixx < Game.getCivsSize(); ixx++) {
               if (Game.getCiv(ixx).getNumOfProvinces() > 0
                  && (
                     Game.ideologiesManager.getIdeology(Game.getCiv(ixx).getIdeologyID()).TRIBAL
                        || Game.religionManager.getReligion(Game.getCiv(ixx).getReligionID()).Tribal
                  )) {
                  distance.add(
                     new AI_PrepareForWar.CivDistance(
                        ixx,
                        Game.getManhattanDistance_PercOfMax(civ.getCapitalProvinceID(), Game.getCiv(ixx).getCapitalProvinceID())
                           * (
                              Game.continents.lContinents.get(Game.getProvince(Game.getCiv(ixx).getCapitalProvinceID()).getContinent()).prioritizeColonization
                                 ? Game.aiValuesDiplomacy.AI_PREPARE_FOR_WAR_CONQUER_TRIBAL_PRIORITIZE_CONTINENT_MODIFIER
                                 : 1.0F
                           )
                     )
                  );
               }
            }

            for (int ixxx = 0; !distance.isEmpty() && ixxx < Game.aiValuesDiplomacy.AI_PREPARE_FOR_WAR_CONQUER_TRIBAL_LIMIT; ixxx++) {
               int bestID = 0;
               int j = 1;

               for (int jSize = distance.size(); j < jSize; j++) {
                  if (distance.get(bestID).distance > distance.get(j).distance) {
                     bestID = j;
                  }
               }

               if (!isCivAlly(civID, distance.get(bestID).civID)) {
                  possibleCivs.add(distance.get(bestID).civID);
               } else {
                  ixxx--;
               }

               distance.remove(bestID);
            }
         }

         if (possibleCivs.isEmpty()) {
            boolean conquerVassal = false;
            if (civ.diplomacy.iVassalsSize > 0
               && civ.diplomacy.iVassalsSize > Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).AI_CONQUER_OWN_VASSALS_IF_OVER
               && Game.oR.nextInt(100) < Game.aiValuesDiplomacy.AI_CONQUER_OWN_VASSALS_CHANCE) {
               for (int ixxx = 0; ixxx < civ.civNeighbors.civsSize; ixxx++) {
                  if (Game.getCiv(civ.civNeighbors.civs.get(ixxx).civID).getPuppetOfCivID() == civID
                     && !isCivAlly(civID, civ.civNeighbors.civs.get(ixxx).civID)) {
                     possibleCivs.add(civ.civNeighbors.civs.get(ixxx).civID);
                     conquerVassal = true;
                  }
               }
            }

            if (!conquerVassal && civ.civNeighbors.civsSize > 0) {
               for (int ixxxx = 0; ixxxx < civ.civNeighbors.civsSize; ixxxx++) {
                  if (Game.getCiv(civ.civNeighbors.civs.get(ixxxx).civID).getPuppetOfCivID() == civID) {
                     if (Game.oR.nextInt(100) < Game.aiValuesDiplomacy.AI_PREPARE_FOR_WAR_AGAINST_OWN_VASSAL_CHANCE
                        && !isCivAlly(civID, civ.civNeighbors.civs.get(ixxxx).civID)) {
                        possibleCivs.add(civ.civNeighbors.civs.get(ixxxx).civID);
                     } else {
                        for (int j = 0; j < Game.getCiv(civ.civNeighbors.civs.get(ixxxx).civID).civNeighbors.civsSize; j++) {
                           if (Game.getCiv(civ.civNeighbors.civs.get(ixxxx).civID).civNeighbors.civs.get(j).civID != civID
                              && !isCivAlly(civID, Game.getCiv(civ.civNeighbors.civs.get(ixxxx).civID).civNeighbors.civs.get(j).civID)
                              && !possibleCivs.contains(Game.getCiv(civ.civNeighbors.civs.get(ixxxx).civID).civNeighbors.civs.get(j).civID)) {
                              possibleCivs.add(Game.getCiv(civ.civNeighbors.civs.get(ixxxx).civID).civNeighbors.civs.get(j).civID);
                           }
                        }
                     }
                  } else if (!isCivAlly(civID, civ.civNeighbors.civs.get(ixxxx).civID) && !possibleCivs.contains(civ.civNeighbors.civs.get(ixxxx).civID)) {
                     possibleCivs.add(civ.civNeighbors.civs.get(ixxxx).civID);
                  }
               }
            }
         }

         if (possibleCivs.isEmpty() && Game.oR.nextInt(100) < Game.aiValuesDiplomacy.AI_PREPARE_FOR_WAR_CLOSEST_CIV_CHANCE) {
            List<AI_PrepareForWar.CivDistance> distance = new ArrayList<>();

            for (int ixxxxx = 1; ixxxxx < civID; ixxxxx++) {
               if (Game.getCiv(ixxxxx).getNumOfProvinces() > 0) {
                  distance.add(
                     new AI_PrepareForWar.CivDistance(
                        ixxxxx, Game.getManhattanDistance_PercOfMax(civ.getCapitalProvinceID(), Game.getCiv(ixxxxx).getCapitalProvinceID())
                     )
                  );
               }
            }

            for (int ixxxxxx = civID + 1; ixxxxxx < Game.getCivsSize(); ixxxxxx++) {
               if (Game.getCiv(ixxxxxx).getNumOfProvinces() > 0) {
                  distance.add(
                     new AI_PrepareForWar.CivDistance(
                        ixxxxxx, Game.getManhattanDistance_PercOfMax(civ.getCapitalProvinceID(), Game.getCiv(ixxxxxx).getCapitalProvinceID())
                     )
                  );
               }
            }

            for (int ixxxxxxx = 0; !distance.isEmpty() && ixxxxxxx < Game.aiValuesDiplomacy.AI_PREPARE_FOR_WAR_CLOSEST_CIV_CIVS_LIMIT; ixxxxxxx++) {
               int bestID = 0;
               int jx = 1;

               for (int jSizex = distance.size(); jx < jSizex; jx++) {
                  if (distance.get(bestID).distance > distance.get(jx).distance) {
                     bestID = jx;
                  }
               }

               if (!isCivAlly(civID, distance.get(bestID).civID)) {
                  possibleCivs.add(distance.get(bestID).civID);
               } else {
                  ixxxxxxx--;
               }

               distance.remove(bestID);
            }
         }

         if (!possibleCivs.isEmpty()) {
            for (int a = 0; a < Game.aiValuesDiplomacy.AI_PREPARE_FOR_WAR_CHECK_LIMIT && !possibleCivs.isEmpty() && !civ.diplomacy.isAtWar(); a++) {
               int bestID = 0;
               int rand = Game.oR.nextInt(Game.aiValuesDiplomacy.AI_PREPARE_FOR_WAR_CHOOSE_WEAKEST_RANDOM_NUMBER);
               if (rand >= Game.aiValuesDiplomacy.AI_PREPARE_FOR_WAR_CHOOSE_WEAKEST_CLOSEST_CIV_ALL_PROVINCES_CHANCE) {
                  if (rand < Game.aiValuesDiplomacy.AI_PREPARE_FOR_WAR_CHOOSE_WEAKEST_CLOSEST_CIV_CAPITAL_CHANCE) {
                     float distance = Game.getManhattanDistance_PercOfMax(
                        civ.getCapitalProvinceID(), Game.getCiv(possibleCivs.get(bestID)).getCapitalProvinceID()
                     );

                     for (int c = 1; c < possibleCivs.size(); c++) {
                        if (distance > Game.getManhattanDistance_PercOfMax(civ.getCapitalProvinceID(), Game.getCiv(possibleCivs.get(c)).getCapitalProvinceID())
                           )
                         {
                           distance = Game.getManhattanDistance_PercOfMax(civ.getCapitalProvinceID(), Game.getCiv(possibleCivs.get(c)).getCapitalProvinceID());
                           bestID = c;
                        }
                     }
                  } else if (rand < Game.aiValuesDiplomacy.AI_PREPARE_FOR_WAR_CHOOSE_WEAKEST_CIV_PROVINCES_CHANCE) {
                     for (int cx = 1; cx < possibleCivs.size(); cx++) {
                        if (Game.getCiv(possibleCivs.get(bestID)).getNumOfProvinces()
                              + Game.getCiv(Game.getCiv(possibleCivs.get(bestID)).getPuppetOfCivID()).getNumOfProvinces()
                           > Game.getCiv(Game.getCiv(possibleCivs.get(cx)).getPuppetOfCivID()).getNumOfProvinces()
                              + Game.getCiv(possibleCivs.get(cx)).getNumOfProvinces()) {
                           bestID = cx;
                        }
                     }
                  } else if (rand < Game.aiValuesDiplomacy.AI_PREPARE_FOR_WAR_CHOOSE_WEAKEST_CIV_MANPOWER_MAX_CHANCE) {
                     for (int cxx = 1; cxx < possibleCivs.size(); cxx++) {
                        if (Game.getCiv(possibleCivs.get(bestID)).fManpowerMax
                              + Game.getCiv(Game.getCiv(possibleCivs.get(bestID)).getPuppetOfCivID()).fManpowerMax
                           > Game.getCiv(Game.getCiv(possibleCivs.get(cxx)).getPuppetOfCivID()).fManpowerMax + Game.getCiv(possibleCivs.get(cxx)).fManpowerMax) {
                           bestID = cxx;
                        }
                     }
                  } else if (rand < Game.aiValuesDiplomacy.AI_PREPARE_FOR_WAR_CHOOSE_WEAKEST_CIV_REGIMENTS_CHANCE) {
                     for (int cxxx = 1; cxxx < possibleCivs.size(); cxxx++) {
                        if (Game.getCiv(possibleCivs.get(bestID)).iRegiments + Game.getCiv(Game.getCiv(possibleCivs.get(bestID)).getPuppetOfCivID()).iRegiments
                           > Game.getCiv(Game.getCiv(possibleCivs.get(cxxx)).getPuppetOfCivID()).iRegiments + Game.getCiv(possibleCivs.get(cxxx)).iRegiments) {
                           bestID = cxxx;
                        }
                     }
                  } else {
                     bestID = Game.oR.nextInt(possibleCivs.size());
                  }
               } else {
                  List<Float> distance = new ArrayList<>();
                  int capitalProvinceID = Game.getCiv(civID).getCapitalProvinceID();

                  for (int cxxxx = 0; cxxxx < possibleCivs.size(); cxxxx++) {
                     float provincesDistance = 0.0F;
                     Civilization civDistance = Game.getCiv(possibleCivs.get(cxxxx));

                     for (int jx = 0; jx < civDistance.getNumOfProvinces(); jx++) {
                        provincesDistance += Game.getManhattanDistance(capitalProvinceID, civDistance.getProvinceID(jx));
                     }

                     distance.add(provincesDistance / civDistance.getNumOfProvinces());
                  }

                  for (int cxxxx = 1; cxxxx < possibleCivs.size(); cxxxx++) {
                     if (distance.get(bestID) > distance.get(cxxxx)) {
                        bestID = cxxxx;
                     }
                  }
               }

               int onCivID = possibleCivs.get(bestID);
               if (Game.getCiv(civID).diplomacy.haveNonAggressionPact(onCivID)) {
                  civ.diplomacy.addDamageRelations(civID, onCivID);
                  possibleCivs.remove(bestID);
               } else if (!AI_DeclareWar.checkRegiments_ForWar(civID, onCivID)) {
                  possibleCivs.remove(bestID);
               } else {
                  if (civ.diplomacy.getRelation(onCivID) <= GameValues.war.RELATIONS_TO_DECLARE_WAR
                     && Game.oR.nextInt(100) < Game.aiValuesDiplomacy.AI_PREPARE_FOR_WAR_DECLARE_WAR_IMMEDIATELY_IF_POSSIBLE_CHANCE
                     && AI_DeclareWar.declareWar(civID, onCivID)) {
                     civ.updateMilitaryLevel(Math.max(GameValues.war.AI_AT_WAR_MIN_MILITARY_LEVEL, Game.getCiv(civID).getMilitaryLevel()));
                     possibleCivs.clear();
                     return;
                  }

                  if (civ.diplomacy.getRelation(onCivID)
                     > GameValues.war.RELATIONS_TO_DECLARE_WAR * Game.aiValuesDiplomacy.AI_PREPARE_FOR_WAR_DECREASE_RELATIONS_MODIFIER) {
                     civ.diplomacy.addDamageRelations(civID, onCivID);
                  }

                  if ((
                        civ.diplomacy.getRelation(onCivID) > Game.aiValuesDiplomacy.AI_PREPARE_FOR_WAR_SEND_INSULT_IF_RELATIONS_OVER
                           || Game.oR.nextInt(100) < Game.aiValuesDiplomacy.AI_PREPARE_FOR_WAR_SEND_INSULT_IF_RELATIONS_OVER_RANDOM_CHANCE
                     )
                     && Game.oR.nextInt(100) < Game.aiValuesDiplomacy.AI_PREPARE_FOR_WAR_SEND_INSULT_CHANCE) {
                     DiplomacyManager.sendInsult(civID, onCivID);
                  }

                  if (civ.aiCivDiplomacy.addPrepareForWar(onCivID)) {
                     Game.gameThread.addAI_SimpleTask(new Game.SimpleTask("update_ReorganizeArmiesAtPeace" + civID, civID) {
                        @Override
                        public void update() {
                           Game.aiManager.update_ReorganizeArmiesAtPeace(this.id);
                        }
                     });
                  }

                  civ.updateMilitaryLevel(Math.max(GameValues.war.AI_AT_WAR_MIN_MILITARY_LEVEL, Game.getCiv(civID).getMilitaryLevel()));
                  if (civ.aiCivDiplomacy.w.size() >= Game.aiValuesDiplomacy.AI_PREPARE_FOR_WAR_CIVS_LIMIT) {
                     break;
                  }
               }
            }
         }

         possibleCivs.clear();
      } catch (Exception var13) {
         CFG.exceptionStack(var13);
      }
   }

   public static boolean isCivAlly(int civID, int civB) {
      return DiplomacyManager.isAlly_AllianceCheck(civID, civB)
         || Game.getCiv(civID).diplomacy.haveNonAggressionPact(civB)
         || Game.getCiv(civB).diplomacy.haveNonAggressionPact(civID)
         || Game.getCiv(civID).diplomacy.haveDefensivePact(civB)
         || Game.getCiv(civB).diplomacy.haveDefensivePact(civID);
   }

   public static List<Integer> getClosestCivs(int civID, int limit) {
      List<AI_PrepareForWar.CivDistance> civs = new ArrayList<>();

      for (int i = 1; i < Game.getCivsSize(); i++) {
         if (Game.getCiv(i).getNumOfProvinces() > 0 && !DiplomacyManager.isAlly(civID, i)) {
            civs.add(
               new AI_PrepareForWar.CivDistance(i, Game.getManhattanDistance(Game.getCiv(civID).getCapitalProvinceID(), Game.getCiv(i).getCapitalProvinceID()))
            );
         }
      }

      List<Integer> out = new ArrayList<>();

      while (!civs.isEmpty() && out.size() < limit) {
         int bestID = 0;

         for (int ix = civs.size() - 1; ix > 0; ix--) {
            if (civs.get(bestID).distance > civs.get(ix).distance) {
               bestID = ix;
            }
         }

         out.add(civs.get(bestID).civID);
         civs.remove(bestID);
      }

      return out;
   }

   public static void buildScoreManpower(int civID, int civB) {
      if (Game.getCiv(civB).getNumOfProvinces() > 0) {
         Game.getCiv(civB).aiScore = (float)(Game.getCiv(civID).fManpowerMax / Game.getCiv(civB).fManpowerMax);
      } else {
         Game.getCiv(civB).aiScore = 999876.0F;
      }
   }

   public static boolean differentGovernment(int civA, int civB) {
      return Game.getCiv(civA).getIdeologyID() != Game.getCiv(civB).getIdeologyID();
   }

   public static boolean differentReligion(int civA, int civB) {
      return Game.getCiv(civA).getReligionID() != Game.getCiv(civB).getReligionID();
   }

   public static class CivDistance {
      public int civID;
      public float distance;

      public CivDistance(int civID, float distance) {
         this.civID = civID;
         this.distance = distance;
      }
   }
}
