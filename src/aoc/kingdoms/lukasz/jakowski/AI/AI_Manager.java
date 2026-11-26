package aoc.kingdoms.lukasz.jakowski.AI;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.AI.Advisors.AI_ManageAdvisors;
import aoc.kingdoms.lukasz.jakowski.AI.Army.AI_DisbandArmies;
import aoc.kingdoms.lukasz.jakowski.AI.Army.AI_Generals;
import aoc.kingdoms.lukasz.jakowski.AI.Army.AI_MoveAtPeace;
import aoc.kingdoms.lukasz.jakowski.AI.Army.AI_MoveAtWar;
import aoc.kingdoms.lukasz.jakowski.AI.Army.AI_MoveNoConnectionManager;
import aoc.kingdoms.lukasz.jakowski.AI.Army.AI_RecruitArmy;
import aoc.kingdoms.lukasz.jakowski.AI.Army.AI_ReorganizeArmies;
import aoc.kingdoms.lukasz.jakowski.AI.Build.AI_Build;
import aoc.kingdoms.lukasz.jakowski.AI.Build.AI_BuildResearch;
import aoc.kingdoms.lukasz.jakowski.AI.Colonization.AI_Colonization;
import aoc.kingdoms.lukasz.jakowski.AI.Colonization.AI_Colonization_NeighCiv;
import aoc.kingdoms.lukasz.jakowski.AI.Diplomacy.AI_Alliances;
import aoc.kingdoms.lukasz.jakowski.AI.Diplomacy.AI_DeclareWar;
import aoc.kingdoms.lukasz.jakowski.AI.Diplomacy.AI_Pacts;
import aoc.kingdoms.lukasz.jakowski.AI.Diplomacy.AI_PrepareForWar;
import aoc.kingdoms.lukasz.jakowski.AI.Diplomacy.AI_RelationsDamage;
import aoc.kingdoms.lukasz.jakowski.AI.Diplomacy.AI_RelationsImprove;
import aoc.kingdoms.lukasz.jakowski.AI.Diplomacy.AI_Rivals;
import aoc.kingdoms.lukasz.jakowski.AI.Diplomacy.AI_VassalLiberty;
import aoc.kingdoms.lukasz.jakowski.AI.Government.AI_Government;
import aoc.kingdoms.lukasz.jakowski.AI.Invest.AI_DevelopInfrastructure;
import aoc.kingdoms.lukasz.jakowski.AI.Invest.AI_GrowthRate;
import aoc.kingdoms.lukasz.jakowski.AI.Invest.AI_IncreaseManpower;
import aoc.kingdoms.lukasz.jakowski.AI.Invest.AI_IncreaseTaxEfficiency;
import aoc.kingdoms.lukasz.jakowski.AI.Invest.AI_InvestInEconomy;
import aoc.kingdoms.lukasz.jakowski.AI.Legacies.AI_Legacy;
import aoc.kingdoms.lukasz.jakowski.AI.Nukes.AI_BuildNukes;
import aoc.kingdoms.lukasz.jakowski.AI.Nukes.AI_Nuke;
import aoc.kingdoms.lukasz.jakowski.AI.Wonders.AI_Wonders;
import aoc.kingdoms.lukasz.map.civilization.Civilization;
import aoc.kingdoms.lukasz.map.technology.TechnologyTree;

public class AI_Manager {
   public static final float SCORE_NOT_POSSIBLE = -999999.0F;
   public static AI_Budget aiBudget = new AI_Budget();

   public final void updateAll() {
      try {
         AI_MoveNoConnectionManager.updateNoConnections();
         this.updateMoveArmies();
         AI_DisbandArmies.updateDisbandArmies();
         this.updateRecruitArmy();
         this.updateReorganizeArmiesAtPeace();
         AI_Government.updateGovernment();
         this.updateCoresReligion();
         this.updateBuildInvest();
         AI_ManageAdvisors.updateAdvisors();
         this.updateRecruitGenerals();
         AI_BuildNukes.updateBuildNukes();
         AI_VassalLiberty.update();
         AI_DeclareWar.declareWar();
         AI_PrepareForWar.prepareForWar();
         AI_Alliances.update();
         AI_RelationsDamage.updateRelations_Damage();
         AI_RelationsImprove.updateRelations_Improve();
         AI_Legacy.updateCiv_Legacies();
         AI_Rivals.updateAI_Rivals();
         AI_Pacts.update();
         AI_Wonders.buildWonders();
         AI_Colonization.updateColonize();
         AI_Colonization_NeighCiv.updateColonize();
         aiBudget.updateBudget();
         AI_Missions.updateMissions();

         try {
            if (Game.player.allowAIMove
               && Game.getCiv(Game.player.iCivID).diplomacy.isAtWar()
               && Game_Calendar.TURN_ID % GameValues.gameUpdateAI.GAME_UPDATE_AI_MOVE_ARMIES_PLAYER == 0) {
               AI_MoveAtWar.moveAtWar(Game.player.iCivID);
            }
         } catch (Exception var2) {
            CFG.exceptionStack(var2);
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public final void update() {
   }

   public final void updateBuildInvest() {
      int i;
      for (i = 1 + Game_Calendar.TURN_ID % GameValues.gameUpdateAI.GAME_UPDATE_AI_BUILD_INVEST;
         i < Game.player.iCivID;
         i += GameValues.gameUpdateAI.GAME_UPDATE_AI_BUILD_INVEST
      ) {
         if (Game.getCiv(i).getNumOfProvinces() > 0) {
            try {
               this.update_BuildInvest(i);
            } catch (Exception var4) {
               CFG.exceptionStack(var4);
            }
         }
      }

      if (i == Game.player.iCivID) {
         i += GameValues.gameUpdateAI.GAME_UPDATE_AI_BUILD_INVEST;
      }

      for (; i < Game.getCivsSize(); i += GameValues.gameUpdateAI.GAME_UPDATE_AI_BUILD_INVEST) {
         if (Game.getCiv(i).getNumOfProvinces() > 0) {
            try {
               this.update_BuildInvest(i);
            } catch (Exception var3) {
               CFG.exceptionStack(var3);
            }
         }
      }
   }

   public final void updateCoresReligion() {
      int i;
      for (i = 1 + Game_Calendar.TURN_ID % GameValues.gameUpdateAI.GAME_UPDATE_AI_CORES_RELIGION;
         i < Game.player.iCivID;
         i += GameValues.gameUpdateAI.GAME_UPDATE_AI_CORES_RELIGION
      ) {
         if (Game.getCiv(i).getNumOfProvinces() > 0) {
            try {
               this.update_CoresReligion(i);
            } catch (Exception var4) {
               CFG.exceptionStack(var4);
            }
         }
      }

      if (i == Game.player.iCivID) {
         i += GameValues.gameUpdateAI.GAME_UPDATE_AI_CORES_RELIGION;
      }

      for (; i < Game.getCivsSize(); i += GameValues.gameUpdateAI.GAME_UPDATE_AI_CORES_RELIGION) {
         if (Game.getCiv(i).getNumOfProvinces() > 0) {
            try {
               this.update_CoresReligion(i);
            } catch (Exception var3) {
               CFG.exceptionStack(var3);
            }
         }
      }
   }

   public final void updateRecruitArmy() {
      int i;
      for (i = 1 + Game_Calendar.TURN_ID % GameValues.gameUpdateAI.GAME_UPDATE_AI_RECRUIT_ARMY_AT_PEACE;
         i < Game.player.iCivID;
         i += GameValues.gameUpdateAI.GAME_UPDATE_AI_RECRUIT_ARMY_AT_PEACE
      ) {
         if (Game.getCiv(i).getNumOfProvinces() > 0) {
            try {
               this.update_RecruitArmy(i);
            } catch (Exception var4) {
               CFG.exceptionStack(var4);
            }
         }
      }

      if (i == Game.player.iCivID) {
         i += GameValues.gameUpdateAI.GAME_UPDATE_AI_RECRUIT_ARMY_AT_PEACE;
      }

      for (; i < Game.getCivsSize(); i += GameValues.gameUpdateAI.GAME_UPDATE_AI_RECRUIT_ARMY_AT_PEACE) {
         if (Game.getCiv(i).getNumOfProvinces() > 0) {
            try {
               this.update_RecruitArmy(i);
            } catch (Exception var3) {
               CFG.exceptionStack(var3);
            }
         }
      }
   }

   public final void updateRecruitGenerals() {
      int i;
      for (i = 1 + Game_Calendar.TURN_ID % GameValues.gameUpdateAI.GAME_UPDATE_AI_RECRUIT_GENERALS;
         i < Game.player.iCivID;
         i += GameValues.gameUpdateAI.GAME_UPDATE_AI_RECRUIT_GENERALS
      ) {
         if (Game.getCiv(i).getNumOfProvinces() > 0) {
            try {
               this.update_RecruitGenerals(i);
            } catch (Exception var4) {
               CFG.exceptionStack(var4);
            }
         }
      }

      if (i == Game.player.iCivID) {
         i += GameValues.gameUpdateAI.GAME_UPDATE_AI_RECRUIT_GENERALS;
      }

      for (; i < Game.getCivsSize(); i += GameValues.gameUpdateAI.GAME_UPDATE_AI_RECRUIT_GENERALS) {
         if (Game.getCiv(i).getNumOfProvinces() > 0) {
            try {
               this.update_RecruitGenerals(i);
            } catch (Exception var3) {
               CFG.exceptionStack(var3);
            }
         }
      }
   }

   public final void updateReorganizeArmiesAtPeace() {
      int i = 1 + Game_Calendar.TURN_ID % GameValues.gameUpdateAI.GAME_UPDATE_AI_REORGANIZE_ARMIES_AT_PEACE;
      if (i == 1) {
         Game.player.playerData.reorganizeArmyStep++;
         if (Game.player.playerData.reorganizeArmyStep > 10) {
            Game.player.playerData.reorganizeArmyStep = 0;
         }
      }

      for (; i < Game.player.iCivID; i += GameValues.gameUpdateAI.GAME_UPDATE_AI_REORGANIZE_ARMIES_AT_PEACE) {
         if (Game.getCiv(i).getNumOfProvinces() > 0) {
            try {
               this.update_ReorganizeArmiesAtPeace_Regiments(i);
            } catch (Exception var5) {
               CFG.exceptionStack(var5);
            }
         } else {
            try {
               if (Game.getCiv(i).iArmyPositionSize > 0) {
                  Game.getCiv(i).removeAllArmies();
               }
            } catch (Exception var6) {
               CFG.exceptionStack(var6);
            }
         }
      }

      if (i == Game.player.iCivID) {
         i += GameValues.gameUpdateAI.GAME_UPDATE_AI_REORGANIZE_ARMIES_AT_PEACE;
      }

      for (; i < Game.getCivsSize(); i += GameValues.gameUpdateAI.GAME_UPDATE_AI_REORGANIZE_ARMIES_AT_PEACE) {
         if (Game.getCiv(i).getNumOfProvinces() > 0) {
            try {
               this.update_ReorganizeArmiesAtPeace_Regiments(i);
            } catch (Exception var3) {
               CFG.exceptionStack(var3);
            }
         } else {
            try {
               if (Game.getCiv(i).iArmyPositionSize > 0) {
                  Game.getCiv(i).removeAllArmies();
               }
            } catch (Exception var4) {
               CFG.exceptionStack(var4);
            }
         }
      }
   }

   public final void updateMoveArmies() {
      int i;
      for (i = 1 + Game_Calendar.TURN_ID % GameValues.gameUpdateAI.GAME_UPDATE_AI_MOVE_ARMIES;
         i < Game.player.iCivID;
         i += GameValues.gameUpdateAI.GAME_UPDATE_AI_MOVE_ARMIES
      ) {
         if (Game.getCiv(i).getNumOfProvinces() > 0) {
            try {
               this.update_MoveUnits(i);
            } catch (Exception var4) {
               CFG.exceptionStack(var4);
            }
         }
      }

      if (i == Game.player.iCivID) {
         i += GameValues.gameUpdateAI.GAME_UPDATE_AI_MOVE_ARMIES;
      }

      for (; i < Game.getCivsSize(); i += GameValues.gameUpdateAI.GAME_UPDATE_AI_MOVE_ARMIES) {
         if (Game.getCiv(i).getNumOfProvinces() > 0) {
            try {
               this.update_MoveUnits(i);
            } catch (Exception var3) {
               CFG.exceptionStack(var3);
            }
         }
      }
   }

   public final void update_BuildInvest(int civID) {
      if (Game.getCiv(civID).fGold >= GameValues.gameUpdateAI.GAME_UPDATE_AI_BUILD_INVEST_IF_GOLD_OVER) {
         if (!Game.aiValuesBuild.BUILD_INVEST_AT_WAR && Game.getCiv(civID).diplomacy.isAtWar()) {
            return;
         }

         int rand = Game.oR.nextInt(100);
         if (Game.aiValuesBuild.BUILD_PRIORITIZE_RESEARCH
            && Game.getCiv(civID).fResearchPerMonth < TechnologyTree.getMaxResearch(civID) * Game.aiValuesBuild.BUILD_PRIORITIZE_RESEARCH_PERC) {
            AI_BuildResearch.buildResearchBuilding(
               civID,
               Game.aiValuesBuild.BUILD_PRIORITIZE_RESEARCH_BUILD_LIMIT
                  + (
                     Game.aiValuesBuild.BUILD_PRIORITIZE_RESEARCH_BUILD_LIMIT_RANDOM > 0
                        ? Game.oR.nextInt(Game.aiValuesBuild.BUILD_PRIORITIZE_RESEARCH_BUILD_LIMIT_RANDOM)
                        : 0
                  )
            );
         }

         if (Game.difficultyID > GameValues.difficulty.NORMAL_ID
            && Game.getCiv(civID).fTotalIncomePerMonth > Game.aiValuesBuild.UPGRADE_CAPITAL_BUILDINGS_IF_INCOME_OVER
            && Game.getCiv(civID).fGold > 0.0F) {
            for (int a = 0; a < Game.aiValuesBuild.UPGRADE_CAPITAL_BUILDINGS_LIMIT; a++) {
               switch (Game.oR.nextInt(3)) {
                  case 0:
                     if (Game.getCiv(civID).getMilitaryAcademyLevel() < Game.getMilitaryAcademy_MaxLvl(civID) && Game.getCiv(civID).fGold > 0.0F) {
                        Civilization var7 = Game.getCiv(civID);
                        var7.fGold = var7.fGold + Game.getMilitaryAcademy_Cost(civID);
                        Game.getCiv(civID).upgradeMilitaryAcademy();
                        var7 = Game.getCiv(civID);
                        var7.fGold = var7.fGold - Game.getMilitaryAcademy_Cost(civID);
                     }
                     break;
                  case 1:
                     if (Game.getCiv(civID).getMilitaryAcademyForGeneralsLevel() < Game.getMilitaryAcademyForGenerals_MaxLvl(civID)
                        && Game.getCiv(civID).fGold > 0.0F) {
                        Civilization var5 = Game.getCiv(civID);
                        var5.fGold = var5.fGold + Game.getMilitaryAcademyForGenerals_Cost(civID);
                        Game.getCiv(civID).upgradeMilitaryAcademyForGenerals();
                        var5 = Game.getCiv(civID);
                        var5.fGold = var5.fGold - Game.getMilitaryAcademyForGenerals_Cost(civID);
                     }
                     break;
                  default:
                     if (Game.getCiv(civID).getCapitalLevel() < Game.getCapital_MaxLvl(civID) && Game.getCiv(civID).fGold > 0.0F) {
                        Civilization var10000 = Game.getCiv(civID);
                        var10000.fGold = var10000.fGold + Game.getCapital_Cost(civID);
                        Game.getCiv(civID).upgradeCapitalCity();
                        var10000 = Game.getCiv(civID);
                        var10000.fGold = var10000.fGold - Game.getCapital_Cost(civID);
                     }
               }
            }
         }

         if (Game.getCiv(civID).fTotalIncomePerMonth < Game.aiValuesBuild.BUILD_PRIORITIZE_ECONOMY_IF_INCOME_BELOW) {
            AI_InvestInEconomy.investInEconomy(civID, Game.aiValuesBuild.INVEST_IN_ECONOMY_MIN_LEFT_GOLD);
         }

         if (rand < Game.ideologiesManager.getIdeology(Game.getCiv(civID).getIdeologyID()).AI_BUILD_INVEST_IN_ECONOMY) {
            AI_InvestInEconomy.investInEconomy(civID, Game.aiValuesBuild.INVEST_IN_ECONOMY_MIN_LEFT_GOLD);
         } else if (rand < Game.ideologiesManager.getIdeology(Game.getCiv(civID).getIdeologyID()).AI_BUILD_INCREASE_TAX_EFFICIENCY) {
            AI_IncreaseTaxEfficiency.increaseTaxEfficiency(civID, Game.aiValuesBuild.INCREASE_TAX_EFFICIENCY_MIN_LEFT_GOLD);
         } else if (rand < Game.ideologiesManager.getIdeology(Game.getCiv(civID).getIdeologyID()).AI_BUILD_INCREASE_MANPOWER) {
            AI_IncreaseManpower.increaseManpower(civID, Game.aiValuesBuild.INCREASE_MANPOWER_MIN_LEFT_GOLD);
         } else if (rand < Game.ideologiesManager.getIdeology(Game.getCiv(civID).getIdeologyID()).AI_BUILD_INCREASE_GROWTH_RATE) {
            AI_GrowthRate.increaseGrowthRate(civID, Game.aiValuesBuild.INCREASE_GROWTH_RATE_MIN_LEFT_GOLD);
         } else if (rand < Game.ideologiesManager.getIdeology(Game.getCiv(civID).getIdeologyID()).AI_BUILD_DEVELOP_INFRASTRUCTURE) {
            AI_DevelopInfrastructure.developInfrastructure(civID, Game.aiValuesBuild.DEVELOP_INFRASTRUCTURE_MIN_LEFT_GOLD);
         } else if (rand < Game.ideologiesManager.getIdeology(Game.getCiv(civID).getIdeologyID()).AI_BUILD_BUILDING) {
            AI_Build.build(civID);
         }
      }
   }

   public final void update_RecruitArmy(int civID) {
      if (Game.getCiv(civID).fGold >= GameValues.gameUpdateAI.GAME_UPDATE_AI_RECRUIT_ARMY_IF_GOLD_OVER) {
         Game.getCiv(civID).aiCivCreateNewArmy.runCreateNewArmy_Task(civID);
         AI_RecruitArmy.recruitArmy(civID);
      } else {
         Game.getCiv(civID).aiCivCreateNewArmy.runCreateNewArmy_Expired(civID);
      }
   }

   public final void update_RecruitGenerals(int civID) {
      if (Game.getCiv(civID).fGold >= GameValues.gameUpdateAI.GAME_UPDATE_AI_RECRUIT_GENERALS_IF_GOLD_OVER) {
         AI_Generals.recruitGenerals(civID);
      }
   }

   public final void update_CoresReligion(int civID) {
      if (Game.getCiv(civID).fGold >= GameValues.gameUpdateAI.GAME_UPDATE_AI_CORES_RELIGION_IF_GOLD_OVER) {
         if (Game.oR.nextInt(100) < 50) {
            AI_ConvertReligion.convertReligion(civID);
            AI_Cores.createCore(civID);
         } else {
            AI_Cores.createCore(civID);
            AI_ConvertReligion.convertReligion(civID);
         }
      }
   }

   public final void update_MoveUnits(int civID) {
      if (Game.getCiv(civID).diplomacy.isAtWar()) {
         AI_MoveAtWar.moveAtWar(civID);
         AI_Nuke.useNukes(civID);
         this.update_RecruitArmy(civID);
         this.update_RecruitGenerals(civID);
      } else {
         AI_MoveAtPeace.moveAtPeace(civID);
      }
   }

   public final void update_ReorganizeArmiesAtPeace(int civID) {
      if (!Game.getCiv(civID).diplomacy.isAtWar()) {
         AI_ReorganizeArmies.reorganizeArmies_AtPeace(civID);
      }
   }

   public final void update_ReorganizeArmiesAtPeace_Regiments(int civID) {
      if (!Game.getCiv(civID).diplomacy.isAtWar()) {
         if (Game.getCiv(civID).iRegiments > 2500) {
            if (Game.player.playerData.reorganizeArmyStep == 0) {
               AI_ReorganizeArmies.reorganizeArmies_AtPeace(civID);
            }
         } else if (Game.getCiv(civID).iRegiments > 1000) {
            if (Game.player.playerData.reorganizeArmyStep % 5 == 0) {
               AI_ReorganizeArmies.reorganizeArmies_AtPeace(civID);
            }
         } else if (Game.getCiv(civID).iRegiments > 500) {
            if (Game.player.playerData.reorganizeArmyStep % 2 == 0) {
               AI_ReorganizeArmies.reorganizeArmies_AtPeace(civID);
            }
         } else {
            AI_ReorganizeArmies.reorganizeArmies_AtPeace(civID);
         }
      }
   }

   public final int getRandomProvince(int iCivID) {
      return Game.getCiv(iCivID).getProvinceID(Game.oR.nextInt(Game.getCiv(iCivID).getNumOfProvinces()));
   }
}
