package aoc.kingdoms.lukasz.jakowski.AI.Advisors;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.map.advisors.AdvisorManager;
import aoc.kingdoms.lukasz.map.civilization.Civilization;
import aoc.kingdoms.lukasz.map.civilization.CivilizationAdvisorsPool;
import java.util.ArrayList;
import java.util.List;

public class AI_ManageAdvisors {
   public static void updateAdvisors() {
      try {
         int i;
         for (i = 1 + Game_Calendar.TURN_ID % GameValues.gameUpdateAI.GAME_UPDATE_AI_RECRUIT_ADVISORS;
            i < Game.player.iCivID;
            i += GameValues.gameUpdateAI.GAME_UPDATE_AI_RECRUIT_ADVISORS
         ) {
            if (Game.getCiv(i).getNumOfProvinces() > 0) {
               updateAdvisors(i);
            }
         }

         if (i == Game.player.iCivID) {
            i += GameValues.gameUpdateAI.GAME_UPDATE_AI_RECRUIT_ADVISORS;
         }

         for (; i < Game.getCivsSize(); i += GameValues.gameUpdateAI.GAME_UPDATE_AI_RECRUIT_ADVISORS) {
            if (Game.getCiv(i).getNumOfProvinces() > 0) {
               updateAdvisors(i);
            }
         }
      } catch (Exception var1) {
         CFG.exceptionStack(var1);
      }
   }

   public static void updateAdvisors(int civID) {
      Civilization civ = Game.getCiv(civID);
      if (civ.fGold > GameValues.advisors.RECRUIT_ADVISOR_GOLD_COST && civ.fLegacy > GameValues.advisors.RECRUIT_ADVISOR_LEGACY_COST) {
         List<Integer> toRecruit = new ArrayList<>();
         if (civ.advisorAdministration.sName == null) {
            toRecruit.add(0);
         }

         if (civ.advisorEconomy.sName == null) {
            toRecruit.add(1);
         }

         if (civ.advisorTechnology.sName == null) {
            toRecruit.add(2);
         }

         if (civ.advisorMilitary.sName == null) {
            toRecruit.add(3);
         }

         while (toRecruit.size() > 0) {
            int rand = Game.oR.nextInt(toRecruit.size());
            if (!recruitAdvisor(civID, toRecruit.get(rand))) {
               break;
            }

            toRecruit.remove(rand);
         }

         toRecruit.clear();
         if (civ.fGold > GameValues.advisors.ADVISOR_PROMOTE_COST_PER_LEVEL * 2.0F) {
            if (civ.advisorAdministration.iLevel < AdvisorManager.getAdvisorMaxLevel(civID)) {
               toRecruit.add(0);
            }

            if (civ.advisorEconomy.iLevel < AdvisorManager.getAdvisorMaxLevel(civID)) {
               toRecruit.add(1);
            }

            if (civ.advisorTechnology.iLevel < AdvisorManager.getAdvisorMaxLevel(civID)) {
               toRecruit.add(2);
            }

            if (civ.advisorMilitary.iLevel < AdvisorManager.getAdvisorMaxLevel(civID)) {
               toRecruit.add(3);
            }

            while (toRecruit.size() > 0) {
               int rand = Game.oR.nextInt(toRecruit.size());
               if (!AdvisorManager.promoteAdvisor(civID, toRecruit.get(rand), false)) {
                  break;
               }

               switch (toRecruit.get(rand)) {
                  case 0:
                     if (civ.advisorAdministration.iLevel >= AdvisorManager.getAdvisorMaxLevel(civID)) {
                        toRecruit.remove(rand);
                     }
                     break;
                  case 1:
                     if (civ.advisorEconomy.iLevel >= AdvisorManager.getAdvisorMaxLevel(civID)) {
                        toRecruit.remove(rand);
                     }
                     break;
                  case 2:
                     if (civ.advisorTechnology.iLevel >= AdvisorManager.getAdvisorMaxLevel(civID)) {
                        toRecruit.remove(rand);
                     }
                     break;
                  case 3:
                     if (civ.advisorMilitary.iLevel >= AdvisorManager.getAdvisorMaxLevel(civID)) {
                        toRecruit.remove(rand);
                     }
               }
            }
         }

         toRecruit.clear();
      }
   }

   public static boolean recruitAdvisor(int iCivID, int iAdvisorType) {
      if (Game.getCiv(iCivID).fGold < AdvisorManager.getRecruitGoldCost(iCivID)) {
         return false;
      } else if (Game.getCiv(iCivID).fLegacy < AdvisorManager.getRecruitCostLegacy(iCivID)) {
         return false;
      } else {
         Civilization var10000 = Game.getCiv(iCivID);
         var10000.fGold = var10000.fGold - AdvisorManager.getRecruitGoldCost(iCivID);
         var10000 = Game.getCiv(iCivID);
         var10000.fLegacy = var10000.fLegacy - AdvisorManager.getRecruitCostLegacy(iCivID);
         Game.getCiv(iCivID).eventsData3.addRecruitedAdvisors(1);
         if (iCivID == Game.player.iCivID) {
            Game.stats.civStats.ra++;
         }

         switch (iAdvisorType) {
            case 0:
               Game.getCiv(iCivID).advisorAdministration = CivilizationAdvisorsPool.generateAdvisor_Random(iCivID, iAdvisorType);
               AdvisorManager.updateCivBonuses(Game.getCiv(iCivID).advisorAdministration, iCivID, 1);
               break;
            case 1:
               Game.getCiv(iCivID).advisorEconomy = CivilizationAdvisorsPool.generateAdvisor_Random(iCivID, iAdvisorType);
               AdvisorManager.updateCivBonuses(Game.getCiv(iCivID).advisorEconomy, iCivID, 1);
               break;
            case 2:
               Game.getCiv(iCivID).advisorTechnology = CivilizationAdvisorsPool.generateAdvisor_Random(iCivID, iAdvisorType);
               AdvisorManager.updateCivBonuses(Game.getCiv(iCivID).advisorTechnology, iCivID, 1);
               break;
            default:
               Game.getCiv(iCivID).advisorMilitary = CivilizationAdvisorsPool.generateAdvisor_Random(iCivID, iAdvisorType);
               AdvisorManager.updateCivBonuses(Game.getCiv(iCivID).advisorMilitary, iCivID, 1);
         }

         return true;
      }
   }
}
