package aoc.kingdoms.lukasz.jakowski.GameThreads;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.AI.Diplomacy.AI_Player;
import aoc.kingdoms.lukasz.jakowski.Player.MessageTypes.PMessageGoldenAgeMilitary;
import aoc.kingdoms.lukasz.jakowski.Player.MessageTypes.PMessageGoldenAgeProsperity;
import aoc.kingdoms.lukasz.jakowski.Player.MessageTypes.PMessageGoldenAgeScience;
import aoc.kingdoms.lukasz.jakowski.Player.Notification.Notification;
import aoc.kingdoms.lukasz.jakowski.Steam.SteamAchievementsManager;
import aoc.kingdoms.lukasz.map.RulersManager;
import aoc.kingdoms.lukasz.map.civilization.CivilizationBonuses;
import aoc.kingdoms.lukasz.map.plague.PlagueManager;
import aoc.kingdoms.lukasz.textures.Images;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedDeque;

public class GameThread_Turns extends Thread {
   public boolean running = true;
   public int iLastUpdateTurnID = 0;
   public long timeSleep;
   public int THREAD_TURN_ID = 0;
   public ConcurrentLinkedDeque<Integer> civsUpdateMaxManpower = new ConcurrentLinkedDeque<>();
   public ConcurrentLinkedDeque<Integer> provincesWonderConstruction = new ConcurrentLinkedDeque<>();
   public ConcurrentLinkedDeque<Integer> provincesCoreCreation = new ConcurrentLinkedDeque<>();
   public ConcurrentLinkedDeque<Integer> provincesConvertReligion = new ConcurrentLinkedDeque<>();
   public ConcurrentLinkedDeque<Integer> provincesIncreaseManpower = new ConcurrentLinkedDeque<>();
   public ConcurrentLinkedDeque<Integer> provincesDevelopInfrastructure = new ConcurrentLinkedDeque<>();
   public ConcurrentLinkedDeque<Integer> provincesIncreaseGrowthRate = new ConcurrentLinkedDeque<>();
   public ConcurrentLinkedDeque<Integer> provincesIncreaseTaxEfficiency = new ConcurrentLinkedDeque<>();
   public ConcurrentLinkedDeque<Integer> provincesInvest = new ConcurrentLinkedDeque<>();
   public ConcurrentLinkedDeque<Integer> provincesBuildingsUnderConstruction = new ConcurrentLinkedDeque<>();
   public ConcurrentLinkedDeque<Integer> civsNukes = new ConcurrentLinkedDeque<>();

   public void clearData() {
      this.provincesBuildingsUnderConstruction.clear();
      this.provincesInvest.clear();
      this.provincesIncreaseTaxEfficiency.clear();
      this.provincesIncreaseGrowthRate.clear();
      this.provincesDevelopInfrastructure.clear();
      this.provincesIncreaseManpower.clear();
      this.provincesConvertReligion.clear();
      this.provincesCoreCreation.clear();
      this.provincesWonderConstruction.clear();
      this.civsUpdateMaxManpower.clear();
      this.civsNukes.clear();
      this.iLastUpdateTurnID = 0;
      this.THREAD_TURN_ID = 0;
   }

   @Override
   public void run() {
      while (this.running) {
         try {
            try {
               this.timeSleep = System.currentTimeMillis();
               if (Game.menuManager.getInGame() && Game_Calendar.TURN_ID != this.iLastUpdateTurnID) {
                  int turns = Math.max(1, Game_Calendar.TURN_ID - this.iLastUpdateTurnID);
                  this.THREAD_TURN_ID++;
                  this.updateBuildingsUnderConstruction(turns);
                  this.updateProvinceConvertReligion(turns);
                  this.updateProvinceCoreCreation(turns);
                  this.updateProvinceInvestEconomy();
                  this.updateProvinceIncreaseManpower();
                  this.updateProvinceIncreaseTaxEfficiency();
                  this.updateProvinceIncreaseGrowthRate();
                  this.updateProvinceWonderConstruction();
                  this.updateProvinceDevelopInfrastructure();
                  this.updateMaxManpower();
                  this.updateUnrest();
                  this.updateDevastation();
                  this.updateProvinceValues();
                  this.updateColonization();
                  this.updateRelations();
                  this.updateDiplomacy();
                  AI_Player.update();
                  if (Game_Calendar.TURN_ID > GameValues.gameUpdate.GAME_UPDATE_DEATH_RULER_MIN_TURN_ID) {
                     this.updateDeathOfAdvisors_Administrative();
                     this.updateDeathOfAdvisors_Economic();
                     this.updateDeathOfAdvisors_Innovation();
                     this.updateDeathOfAdvisors_Military();
                     this.updateDeathOfAGenerals_NotAssigned();
                     this.updateDeathOfAGenerals_Assigned();
                     this.updateDeathOfRulers();
                  }

                  this.updateRegimentsLimit();
                  this.updateGoldenAge();
                  PlagueManager.runPlagues();
                  PlagueManager.startDisease();
                  this.updateCivsNukes(turns);
                  this.iLastUpdateTurnID = Game_Calendar.TURN_ID;
               }
            } catch (Exception var3) {
               CFG.exceptionStack(var3);
            }

            try {
               Thread.sleep(Math.max(1L, 15L - (System.currentTimeMillis() - this.timeSleep)));
            } catch (InterruptedException var2) {
            }
         } catch (Exception var4) {
            CFG.exceptionStack(var4);
         }
      }
   }

   public final void addProvinceBuildingsUnderConstruction(int iProvinceID) {
      try {
         if (this.provincesBuildingsUnderConstruction.contains(iProvinceID)) {
            return;
         }

         this.provincesBuildingsUnderConstruction.add(iProvinceID);
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public final void removeProvinceBuildingsUnderConstruction(int iProvinceID) {
      try {
         this.provincesBuildingsUnderConstruction.remove(iProvinceID);
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public final void updateBuildingsUnderConstruction(int turns) {
      try {
         for (Integer provinceID : this.provincesBuildingsUnderConstruction) {
            try {
               if (Game.getProvince(provinceID).iBuildingsConstructionSize > 0) {
                  Game.getProvince(provinceID).updateBuildingsUnderConstrucion(turns);
               } else {
                  this.provincesBuildingsUnderConstruction.remove(provinceID);
               }
            } catch (Exception var5) {
               CFG.exceptionStack(var5);
               this.provincesBuildingsUnderConstruction.remove(provinceID);
            }
         }
      } catch (Exception var6) {
         CFG.exceptionStack(var6);
      }
   }

   public final void addProvinceInvest(int iProvinceID) {
      try {
         if (this.provincesInvest.contains(iProvinceID)) {
            return;
         }

         this.provincesInvest.add(iProvinceID);
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public final void removeProvinceInvest(int iProvinceID) {
      try {
         this.provincesInvest.remove(iProvinceID);
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public final void updateProvinceInvestEconomy() {
      try {
         for (Integer provinceID : this.provincesInvest) {
            Game.getProvince(provinceID).updateInvestEconomy();
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public final void addProvinceIncreaseTaxEfficiency(int iProvinceID) {
      try {
         if (this.provincesIncreaseTaxEfficiency.contains(iProvinceID)) {
            return;
         }

         this.provincesIncreaseTaxEfficiency.add(iProvinceID);
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public final void removeProvinceIncreaseTaxEfficiency(int iProvinceID) {
      try {
         this.provincesIncreaseTaxEfficiency.remove(iProvinceID);
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public final void updateProvinceIncreaseTaxEfficiency() {
      try {
         for (Integer provinceID : this.provincesIncreaseTaxEfficiency) {
            Game.getProvince(provinceID).updateIncreaseTaxEfficiency();
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public final void addProvinceIncreaseGrowthRate(int iProvinceID) {
      try {
         if (this.provincesIncreaseGrowthRate.contains(iProvinceID)) {
            return;
         }

         this.provincesIncreaseGrowthRate.add(iProvinceID);
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public final void removeProvinceIncreaseGrowthRate(int iProvinceID) {
      try {
         this.provincesIncreaseGrowthRate.remove(iProvinceID);
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public final void updateProvinceIncreaseGrowthRate() {
      try {
         for (Integer provinceID : this.provincesIncreaseGrowthRate) {
            Game.getProvince(provinceID).updateIncreaseGrowthRate();
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public final void addProvinceDevelopInfrastructure(int iProvinceID) {
      try {
         if (this.provincesDevelopInfrastructure.contains(iProvinceID)) {
            return;
         }

         this.provincesDevelopInfrastructure.add(iProvinceID);
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public final void removeProvinceDevelopInfrastructure(int iProvinceID) {
      try {
         this.provincesDevelopInfrastructure.remove(iProvinceID);
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public final void updateProvinceDevelopInfrastructure() {
      try {
         Iterator<Integer> it = this.provincesDevelopInfrastructure.iterator();

         while (it.hasNext()) {
            Game.getProvince(it.next()).updateDevelopInfrastructure();
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   public final void addProvinceIncreaseManpower(int iProvinceID) {
      try {
         if (this.provincesIncreaseManpower.contains(iProvinceID)) {
            return;
         }

         this.provincesIncreaseManpower.add(iProvinceID);
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public final void removeProvinceIncreaseManpower(int iProvinceID) {
      try {
         this.provincesIncreaseManpower.remove(iProvinceID);
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public final void updateProvinceIncreaseManpower() {
      try {
         Iterator<Integer> it = this.provincesIncreaseManpower.iterator();

         while (it.hasNext()) {
            Game.getProvince(it.next()).updateIncreaseManpower();
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   public final void addProvinceConvertReligion(int iProvinceID) {
      try {
         if (this.provincesConvertReligion.contains(iProvinceID)) {
            return;
         }

         this.provincesConvertReligion.add(iProvinceID);
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public final void removeProvinceConvertReligion(int iProvinceID) {
      try {
         this.provincesConvertReligion.remove(iProvinceID);
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public final void updateProvinceConvertReligion(int turns) {
      try {
         Iterator<Integer> it = this.provincesConvertReligion.iterator();

         while (it.hasNext()) {
            Game.getProvince(it.next()).updateReligionConversion(turns);
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public final void addProvinceCoreCreation(int iProvinceID) {
      try {
         if (this.provincesCoreCreation.contains(iProvinceID)) {
            return;
         }

         this.provincesCoreCreation.add(iProvinceID);
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public final void removeProvinceCoreCreation(int iProvinceID) {
      try {
         this.provincesCoreCreation.remove(iProvinceID);
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public final void updateProvinceCoreCreation(int turns) {
      try {
         Iterator<Integer> it = this.provincesCoreCreation.iterator();

         while (it.hasNext()) {
            Game.getProvince(it.next()).updateCoreCreation(turns);
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public final void addProvinceWonderConstruction(int iProvinceID) {
      try {
         if (this.provincesWonderConstruction.contains(iProvinceID)) {
            return;
         }

         this.provincesWonderConstruction.add(iProvinceID);
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public final void removeProvinceWonderConstruction(int iProvinceID) {
      try {
         this.provincesWonderConstruction.remove(iProvinceID);
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public final void updateProvinceWonderConstruction() {
      try {
         Iterator<Integer> it = this.provincesWonderConstruction.iterator();

         while (it.hasNext()) {
            Game.getProvince(it.next()).updateWonderConstruction();
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   public final void updateDeathOfAdvisors_Administrative() {
      try {
         for (int i = this.THREAD_TURN_ID % GameValues.gameUpdate.GAME_UPDATE_DEATH_ADVISOR_EVERY_X_DAYS;
            i < Game.getCivsSize();
            i += GameValues.gameUpdate.GAME_UPDATE_DEATH_ADVISOR_EVERY_X_DAYS
         ) {
            try {
               if (Game.getCiv(i).getNumOfProvinces() > 0) {
                  Game.advisorManager.update_ChanceOfDeathAdvisor_Administrative(i);
               }
            } catch (Exception var3) {
               CFG.exceptionStack(var3);
            }
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   public final void updateDeathOfAdvisors_Economic() {
      try {
         for (int i = (this.THREAD_TURN_ID + 90) % GameValues.gameUpdate.GAME_UPDATE_DEATH_ADVISOR_EVERY_X_DAYS;
            i < Game.getCivsSize();
            i += GameValues.gameUpdate.GAME_UPDATE_DEATH_ADVISOR_EVERY_X_DAYS
         ) {
            try {
               if (Game.getCiv(i).getNumOfProvinces() > 0) {
                  Game.advisorManager.update_ChanceOfDeathAdvisor_Economic(i);
               }
            } catch (Exception var3) {
               CFG.exceptionStack(var3);
            }
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   public final void updateDeathOfAdvisors_Innovation() {
      try {
         for (int i = (this.THREAD_TURN_ID + 180) % GameValues.gameUpdate.GAME_UPDATE_DEATH_ADVISOR_EVERY_X_DAYS;
            i < Game.getCivsSize();
            i += GameValues.gameUpdate.GAME_UPDATE_DEATH_ADVISOR_EVERY_X_DAYS
         ) {
            try {
               if (Game.getCiv(i).getNumOfProvinces() > 0) {
                  Game.advisorManager.update_ChanceOfDeathAdvisor_Innovation(i);
               }
            } catch (Exception var3) {
               CFG.exceptionStack(var3);
            }
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   public final void updateDeathOfAdvisors_Military() {
      try {
         for (int i = (this.THREAD_TURN_ID + 270) % GameValues.gameUpdate.GAME_UPDATE_DEATH_ADVISOR_EVERY_X_DAYS;
            i < Game.getCivsSize();
            i += GameValues.gameUpdate.GAME_UPDATE_DEATH_ADVISOR_EVERY_X_DAYS
         ) {
            try {
               if (Game.getCiv(i).getNumOfProvinces() > 0) {
                  Game.advisorManager.update_ChanceOfDeathAdvisor_Military(i);
               }
            } catch (Exception var3) {
               CFG.exceptionStack(var3);
            }
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   public final void updateDeathOfAGenerals_NotAssigned() {
      try {
         for (int i = 1 + this.THREAD_TURN_ID % GameValues.gameUpdate.GAME_UPDATE_DEATH_GENERAL_EVERY_X_DAYS_NOT_ASSIGNED;
            i < Game.getCivsSize();
            i += GameValues.gameUpdate.GAME_UPDATE_DEATH_GENERAL_EVERY_X_DAYS_NOT_ASSIGNED
         ) {
            try {
               if (Game.getCiv(i).getNumOfProvinces() > 0) {
                  Game.getCiv(i).update_ChanceOfGeneral_NotAssigned();
               }
            } catch (Exception var3) {
               CFG.exceptionStack(var3);
            }
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   public final void updateDeathOfAGenerals_Assigned() {
      try {
         for (int i = this.THREAD_TURN_ID % GameValues.gameUpdate.GAME_UPDATE_DEATH_GENERAL_EVERY_X_DAYS;
            i < Game.getProvincesSize();
            i += GameValues.gameUpdate.GAME_UPDATE_DEATH_GENERAL_EVERY_X_DAYS
         ) {
            try {
               for (int j = Game.getProvince(i).getArmySize() - 1; j >= 0; j--) {
                  if (Game.getProvince(i).getArmy(j).armyGeneral != null
                     && RulersManager.characterDies(Game.getProvince(i).getArmy(j).civID, Game.getProvince(i).getArmy(j).armyGeneral.y)) {
                     if (Game.getProvince(i).getArmy(j).civID == Game.player.iCivID) {
                        Game.player
                           .addNotification(
                              new Notification(
                                 Notification.Notification_Type.GENERAL_DIED,
                                 Game.lang.get("GeneralDied") + ": " + Game.getProvince(i).getArmy(j).armyGeneral.n,
                                 Images.general,
                                 this.THREAD_TURN_ID,
                                 Notification.Notification_BG.RED,
                                 Game.player.iCivID
                              )
                           );
                     }

                     Game.getProvince(i).getArmy(j).setArmyGeneral(null);
                  }
               }
            } catch (Exception var3) {
               CFG.exceptionStack(var3);
            }
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   public final void updateDeathOfRulers() {
      try {
         for (int i = this.THREAD_TURN_ID % GameValues.gameUpdate.GAME_UPDATE_DEATH_RULER_EVERY_X_DAYS;
            i < Game.getCivsSize();
            i += GameValues.gameUpdate.GAME_UPDATE_DEATH_RULER_EVERY_X_DAYS
         ) {
            try {
               if (Game.getCiv(i).getNumOfProvinces() > 0) {
                  RulersManager.update_ChanceOfDeathOfRuler(i);
               }
            } catch (Exception var3) {
               CFG.exceptionStack(var3);
            }
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   public final void updateDevastation() {
      try {
         for (int i = this.THREAD_TURN_ID % GameValues.gameUpdate.GAME_UPDATE_DEVASTATION_STEPS;
            i < Game.getProvincesSize();
            i += GameValues.gameUpdate.GAME_UPDATE_DEVASTATION_STEPS
         ) {
            if (!Game.getProvince(i).getSeaProvince()) {
               Game.getProvince(i).updateDevastation();
               Game.getProvince(i).updateLoot();
            }
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   public final void updateProvinceValues() {
      try {
         for (int i = this.THREAD_TURN_ID % 365; i < Game.getProvincesSize(); i += 365) {
            if (!Game.getProvince(i).getSeaProvince()) {
               Game.getProvince(i).updateProvinceValue();
            }
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   public final void updateUnrest() {
      try {
         for (int i = this.THREAD_TURN_ID % GameValues.gameUpdate.GAME_UPDATE_UNREST_STEPS;
            i < Game.getProvincesSize();
            i += GameValues.gameUpdate.GAME_UPDATE_UNREST_STEPS
         ) {
            if (Game.getProvince(i).getCivID() > 0) {
               Game.getProvince(i).updateRevolutionaryRisk();
            }
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   public final void addCivsNukes(int iCivID) {
      try {
         if (this.civsNukes.contains(iCivID)) {
            return;
         }

         this.civsNukes.add(iCivID);
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public final void removeCivsNukes(int iCivID) {
      try {
         this.civsNukes.remove(iCivID);
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public final void updateCivsNukes(int turns) {
      try {
         Iterator<Integer> it = this.civsNukes.iterator();

         while (it.hasNext()) {
            Game.getCiv(it.next()).updateNukeProduction(turns);
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public final void updateDiplomacy() {
      try {
         int i;
         for (i = 1 + Game_Calendar.TURN_ID % GameValues.gameUpdate.GAME_UPDATE_DIPLOMACY_EXPIRED;
            i < Game.player.iCivID;
            i += GameValues.gameUpdate.GAME_UPDATE_DIPLOMACY_EXPIRED
         ) {
            if (Game.getCiv(i).getNumOfProvinces() > 0) {
               this.updateDiplomacy(i);
            }
         }

         if (i == Game.player.iCivID) {
            i += GameValues.gameUpdate.GAME_UPDATE_DIPLOMACY_EXPIRED;
         }

         for (; i < Game.getCivsSize(); i += GameValues.gameUpdate.GAME_UPDATE_DIPLOMACY_EXPIRED) {
            if (Game.getCiv(i).getNumOfProvinces() > 0) {
               this.updateDiplomacy(i);
            }
         }

         if (Game.player.iCivID > 0) {
            this.updateDiplomacy(Game.player.iCivID);
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   public final void updateDiplomacy(int civID) {
      Game.getCiv(civID).diplomacy.updateTruces(civID);
      Game.getCiv(civID).diplomacy.updateDefensivePact(civID);
      Game.getCiv(civID).diplomacy.updateNonAggressionPact(civID);
      Game.getCiv(civID).diplomacy.updateGuarantee(civID);
      Game.getCiv(civID).diplomacy.updateAlliance(civID);
      Game.getCiv(civID).diplomacy.updateMilitaryAccess(civID);
      Game.getCiv(civID).diplomacy.updateRivals(civID);
   }

   public final void updateRelations() {
      try {
         for (int i = 1 + Game_Calendar.TURN_ID % GameValues.gameUpdate.GAME_UPDATE_DIPLOMACY_EXPIRED;
            i < Game.getCivsSize();
            i += GameValues.gameUpdate.GAME_UPDATE_DIPLOMACY_IMPROVE_DAMAGE_RELATIONS
         ) {
            Game.getCiv(i).diplomacy.updateImproveRelations(i);
            Game.getCiv(i).diplomacy.updateDamageRelations(i);
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   public final void updateColonization() {
      try {
         for (int i = this.THREAD_TURN_ID % GameValues.gameUpdate.GAME_UPDATE_POPULATION_STEPS;
            i < Game.getCivsSize();
            i += GameValues.gameUpdate.GAME_UPDATE_POPULATION_STEPS
         ) {
            if (Game.getCiv(i).getNumOfProvinces() > 0) {
               Game.getCiv(i).updateColonizationProvince();
            }
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   public final void addCivUpdateMaxManpower(int iCivID) {
      try {
         if (this.civsUpdateMaxManpower.contains(iCivID)) {
            return;
         }

         this.civsUpdateMaxManpower.add(iCivID);
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public final void updateMaxManpower() {
      try {
         for (int i = this.civsUpdateMaxManpower.size() - 1; i >= 0; i--) {
            int nCivID = this.civsUpdateMaxManpower.remove();
            Game.getCiv(nCivID).updateManpowerPerMonth();
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public final void updateRegimentsLimit() {
      try {
         for (int i = this.THREAD_TURN_ID % GameValues.gameUpdate.GAME_UPDATE_REGIMENTS_LIMIT_STEPS;
            i < Game.getCivsSize();
            i += GameValues.gameUpdate.GAME_UPDATE_REGIMENTS_LIMIT_STEPS
         ) {
            try {
               if (Game.getCiv(i).getNumOfProvinces() > 0) {
                  Game.getCiv(i).updateRegimentsLimit();
               }
            } catch (Exception var3) {
               CFG.exceptionStack(var3);
            }
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   public final void updateGoldenAge() {
      try {
         for (int i = this.THREAD_TURN_ID % GameValues.gameUpdate.GAME_UPDATE_GOLDEN_AGE;
            i < Game.getCivsSize();
            i += GameValues.gameUpdate.GAME_UPDATE_GOLDEN_AGE
         ) {
            if (Game.getCiv(i).getNumOfProvinces() > 0) {
               this.updateGoldenAge_Prosperity(i);
               this.updateGoldenAge_Military(i);
               this.updateGoldenAge_Science(i);
            }
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   public final void updateGoldenAge_Prosperity(int i) {
      try {
         if (Game.getCiv(i).goldenAge.getGoldenAgeProsperity() < GameValues.iGoldenAge_ProsperitySize
            && Game.getCiv(i).eventsData.getInvestedInEconomy() / 100.0F
               >= GameValues.goldenAge.GA_PROSPERITY_INVESTED_IN_ECONOMY[Game.getCiv(i).goldenAge.getGoldenAgeProsperity()]
            && Game.getCiv(i).eventsData2.getEconomyBuildingsConstructed()
               >= GameValues.goldenAge.GA_PROSPERITY_ECONOMY_BUILDINGS[Game.getCiv(i).goldenAge.getGoldenAgeProsperity()]) {
            this.unlockGoldenAge_Prosperity(i);
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public final void unlockGoldenAge_Prosperity(int i) {
      CivilizationBonuses nCivBonus = new CivilizationBonuses();
      nCivBonus.MonthlyIncome = GameValues.goldenAge.GA_PROSPERITY_MONTHLY_INCOME[Game.getCiv(i).goldenAge.getGoldenAgeProsperity()];
      nCivBonus.IncomeProduction = GameValues.goldenAge.GA_PROSPERITY_INCOME_PRODUCTION[Game.getCiv(i).goldenAge.getGoldenAgeProsperity()];
      nCivBonus.TempTurnID = Game_Calendar.TURN_ID + GameValues.goldenAge.GA_PROSPERITY_DURATION_DAYS[Game.getCiv(i).goldenAge.getGoldenAgeProsperity()];
      Game.getCiv(i).addCivilizationBonus_Temporary(nCivBonus);
      Game.getCiv(i).goldenAge.setGoldenAgeProsperity(Game.getCiv(i).goldenAge.getGoldenAgeProsperity() + 1);
      if (i == Game.player.iCivID) {
         Game.player.addMessage(new PMessageGoldenAgeProsperity(Game.player.iCivID, Game_Calendar.TURN_ID + GameValues.diplomacy.DIPLOMACY_MESSAGE_DAYS));
         SteamAchievementsManager.unlockAchievement(SteamAchievementsManager.GOLDEN_PROSPERITY);
      }
   }

   public final void updateGoldenAge_Military(int i) {
      try {
         if (Game.getCiv(i).goldenAge.getGoldenAgeMilitary() < GameValues.iGoldenAge_MilitarySize
            && Game.getCiv(i).eventsData.getIncreasedManpower() / 100.0F
               >= GameValues.goldenAge.GA_MILITARY_INCREASED_MANPOWER[Game.getCiv(i).goldenAge.getGoldenAgeMilitary()]
            && Game.getCiv(i).eventsData2.getMilitaryBuildingsConstructed()
               >= GameValues.goldenAge.GA_MILITARY_MILITARY_BUILDINGS[Game.getCiv(i).goldenAge.getGoldenAgeMilitary()]) {
            this.unlockGoldenAge_Military(i);
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public final void unlockGoldenAge_Military(int i) {
      CivilizationBonuses nCivBonus = new CivilizationBonuses();
      nCivBonus.UnitsAttack = GameValues.goldenAge.GA_MILITARY_UNITS_ATTACK[Game.getCiv(i).goldenAge.getGoldenAgeMilitary()];
      nCivBonus.UnitsDefense = GameValues.goldenAge.GA_MILITARY_UNITS_DEFENSE[Game.getCiv(i).goldenAge.getGoldenAgeMilitary()];
      nCivBonus.MaxManpower_Percentage = GameValues.goldenAge.GA_MILITARY_MAX_MANPOWER_PERC[Game.getCiv(i).goldenAge.getGoldenAgeMilitary()];
      nCivBonus.TempTurnID = Game_Calendar.TURN_ID + GameValues.goldenAge.GA_MILITARY_DURATION_DAYS[Game.getCiv(i).goldenAge.getGoldenAgeMilitary()];
      Game.getCiv(i).addCivilizationBonus_Temporary(nCivBonus);
      Game.getCiv(i).goldenAge.setGoldenAgeMilitary(Game.getCiv(i).goldenAge.getGoldenAgeMilitary() + 1);
      if (i == Game.player.iCivID) {
         Game.player.addMessage(new PMessageGoldenAgeMilitary(Game.player.iCivID, Game_Calendar.TURN_ID + GameValues.diplomacy.DIPLOMACY_MESSAGE_DAYS));
         SteamAchievementsManager.unlockAchievement(SteamAchievementsManager.GOLDEN_MILITARY);
      }
   }

   public final void updateGoldenAge_Science(int i) {
      try {
         if (Game.getCiv(i).goldenAge.getGoldenAgeScience() < GameValues.iGoldenAge_ScienceSize
            && Game.getCiv(i).eventsData.getDevelopedInfrastructure()
               >= GameValues.goldenAge.GA_SCIENCE_DEVELOPED_INFRASTRUCTURE[Game.getCiv(i).goldenAge.getGoldenAgeScience()]
            && Game.getCiv(i).eventsData2.getAdministrativeBuildingsConstructed()
               >= GameValues.goldenAge.GA_SCIENCE_ADMINISTRATIVE_BUILDINGS[Game.getCiv(i).goldenAge.getGoldenAgeScience()]) {
            this.unlockGoldenAge_Science(i);
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public final void unlockGoldenAge_Science(int i) {
      CivilizationBonuses nCivBonus = new CivilizationBonuses();
      nCivBonus.ResearchPoints = GameValues.goldenAge.GA_SCIENCE_RESEARCH[Game.getCiv(i).goldenAge.getGoldenAgeScience()];
      nCivBonus.MonthlyLegacy = GameValues.goldenAge.GA_SCIENCE_LEGACY[Game.getCiv(i).goldenAge.getGoldenAgeScience()];
      nCivBonus.TempTurnID = Game_Calendar.TURN_ID + GameValues.goldenAge.GA_SCIENCE_DURATION_DAYS[Game.getCiv(i).goldenAge.getGoldenAgeScience()];
      Game.getCiv(i).addCivilizationBonus_Temporary(nCivBonus);
      Game.getCiv(i).goldenAge.setGoldenAgeScience(Game.getCiv(i).goldenAge.getGoldenAgeScience() + 1);
      if (i == Game.player.iCivID) {
         Game.player.addMessage(new PMessageGoldenAgeScience(Game.player.iCivID, Game_Calendar.TURN_ID + GameValues.diplomacy.DIPLOMACY_MESSAGE_DAYS));
         SteamAchievementsManager.unlockAchievement(SteamAchievementsManager.GOLDEN_SCIENCE);
      }
   }
}
