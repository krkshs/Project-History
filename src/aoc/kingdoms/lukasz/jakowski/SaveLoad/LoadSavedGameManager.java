package aoc.kingdoms.lukasz.jakowski.SaveLoad;

import aoc.kingdoms.lukasz.events.EventsManager;
import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.FileManager;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.AI.AI_Budget;
import aoc.kingdoms.lukasz.jakowski.AI.AI_CivDiplomacy;
import aoc.kingdoms.lukasz.jakowski.AI.AI_Manager;
import aoc.kingdoms.lukasz.jakowski.GameThreads.GameThread_Events;
import aoc.kingdoms.lukasz.jakowski.Player.More.PlayerData;
import aoc.kingdoms.lukasz.jakowski.Player.More.PlayerStats;
import aoc.kingdoms.lukasz.jakowski.Player.More.PlayerStats2;
import aoc.kingdoms.lukasz.jakowski.Player.More.PlayerStats3;
import aoc.kingdoms.lukasz.jakowski.Player.More.PlayerStats4;
import aoc.kingdoms.lukasz.map.AdvantagesManager;
import aoc.kingdoms.lukasz.map.LawsManager;
import aoc.kingdoms.lukasz.map.Loan;
import aoc.kingdoms.lukasz.map.Ruler;
import aoc.kingdoms.lukasz.map.WondersManager;
import aoc.kingdoms.lukasz.map.advisors.Advisor;
import aoc.kingdoms.lukasz.map.advisors.AdvisorManager;
import aoc.kingdoms.lukasz.map.allianceHRE.Alliance;
import aoc.kingdoms.lukasz.map.army.ArmyDivision;
import aoc.kingdoms.lukasz.map.army.ArmyGeneral;
import aoc.kingdoms.lukasz.map.battles.Battle;
import aoc.kingdoms.lukasz.map.civilization.Civilization;
import aoc.kingdoms.lukasz.map.civilization.CivilizationBonuses;
import aoc.kingdoms.lukasz.map.civilization.CivilizationEventsData_Variables;
import aoc.kingdoms.lukasz.map.civilization.CivilizationGoldenAge;
import aoc.kingdoms.lukasz.map.civilization.CivilizationLegacy;
import aoc.kingdoms.lukasz.map.civilization.save.CivData;
import aoc.kingdoms.lukasz.map.civilization.save.CivData2;
import aoc.kingdoms.lukasz.map.civilization.save.CivData3;
import aoc.kingdoms.lukasz.map.civilization.save.CivData4;
import aoc.kingdoms.lukasz.map.civilization.stats.CivilizationEventsData;
import aoc.kingdoms.lukasz.map.civilization.stats.CivilizationEventsData2;
import aoc.kingdoms.lukasz.map.civilization.stats.CivilizationEventsData3;
import aoc.kingdoms.lukasz.map.diplomacy.Vassal;
import aoc.kingdoms.lukasz.map.plague.Plague;
import aoc.kingdoms.lukasz.map.plague.PlagueManager;
import aoc.kingdoms.lukasz.map.province.ProvinceConstructedBuilding;
import aoc.kingdoms.lukasz.map.province.ProvinceConstructionBuilding;
import aoc.kingdoms.lukasz.map.province.data.ProvinceData;
import aoc.kingdoms.lukasz.map.province.data.ProvinceData10;
import aoc.kingdoms.lukasz.map.province.data.ProvinceData2;
import aoc.kingdoms.lukasz.map.province.data.ProvinceData3;
import aoc.kingdoms.lukasz.map.province.data.ProvinceData4;
import aoc.kingdoms.lukasz.map.province.data.ProvinceData5;
import aoc.kingdoms.lukasz.map.province.data.ProvinceData6;
import aoc.kingdoms.lukasz.map.province.data.ProvinceData7;
import aoc.kingdoms.lukasz.map.province.data.ProvinceData8;
import aoc.kingdoms.lukasz.map.province.data.ProvinceData9;
import aoc.kingdoms.lukasz.map.province.data.ProvinceData_Population;
import aoc.kingdoms.lukasz.map.rebels.RevolutionManager;
import aoc.kingdoms.lukasz.map.technology.TechnologyResearch;
import aoc.kingdoms.lukasz.map.war.War;
import aoc.kingdoms.lukasz.map.war.WarManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import java.util.ArrayList;

public class LoadSavedGameManager {
   public static String key = "";
   public static String playerKey = "";

   public static final SaveGameManager.SaveDetails loadSave_Details(String nKey) {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + nKey + "/" + "Details.json");
         Json json = new Json();
         return json.fromJson(SaveGameManager.SaveDetails.class, fileList);
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
         return null;
      }
   }

   public static final void loadSave_Details() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "Details.json");
         Json json = new Json();
         SaveGameManager.SaveDetails tempDetailsData = json.fromJson(SaveGameManager.SaveDetails.class, fileList);
         playerKey = tempDetailsData.sCivTag;
         Game_Calendar.TURN_ID = tempDetailsData.iTurnID;
         SaveGameManager.AUTO_SAVE_LAST_TURN_ID = tempDetailsData.iTurnID;
         Game_Calendar.currentDay = tempDetailsData.iDay;
         Game_Calendar.currentMonth = tempDetailsData.iMonth;
         Game_Calendar.currentYear = tempDetailsData.iYear;
         Game.mapScenarios.sActiveScenarioTag = tempDetailsData.scenarioTAG;
         EventsManager.loadScenarioEventsTag = tempDetailsData.scenarioTAG;
         Game.difficultyID = tempDetailsData.DIFFICULTY;
         Game.FOG_OF_WAR = tempDetailsData.FOG_OF_WAR;
         Game.SPECTATOR_MODE = tempDetailsData.SPECTATOR_MODE;
         Game.AGE_OF_CHAOS_MODE = tempDetailsData.AGE_OF_CHAOS_MODE;
         Game.AGE_OF_CHAOS_YEARS = tempDetailsData.AGE_OF_CHAOS_YEARS;
         Game.SANDBOX = tempDetailsData.SANDBOX;
         Game.ENABLE_CALL_VASSALS = tempDetailsData.ENABLE_CALL_VASSALS;
         Game.SCENARIO_EVENTS = tempDetailsData.SCENARIO_EVENTS;
         Game.HOURS_PER_TURN = tempDetailsData.HOURS_PER_TURN;
         Game.aiAggressivnes = tempDetailsData.AI_AGGRESSIVENESS;
         Game.gameThreadTurns.THREAD_TURN_ID = Game_Calendar.TURN_ID;
         Game.gameThreadTurns.iLastUpdateTurnID = Game_Calendar.TURN_ID;
         Game_Calendar.updateManpowerImg();
         Game_Calendar.updateAge(false);
         GameThread_Events.THREAD_TURN_ID = Game_Calendar.TURN_ID;
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public static final void loadSave_UpdatePlayersCivID() {
      for (int i = 1; i < Game.getCivsSize(); i++) {
         if (Game.getCiv(i).getCivTag().equals(playerKey)) {
            Game.player.iCivID = i;
            break;
         }
      }
   }

   public static final void loadSave_InitCivsData() {
      for (int i = 0; i < Game.getCivsSize(); i++) {
         Game.getCiv(i).initTechTree();
         Game.getCiv(i).initGoodsProduced();
      }
   }

   public static final void loadSave_BuildTechTree() {
      for (int i = 0; i < Game.getCivsSize(); i++) {
         Game.getCiv(i).buildTechTree_Load();
      }
   }

   public static final void loadSave_UpdateDiplomacyPerMonth() {
      for (int i = 0; i < Game.getCivsSize(); i++) {
         Game.getCiv(i).updateDiplomacyPerMonth();
      }
   }

   public static final void loadSave_InitCivsData_CoresReligionGenerals() {
      for (int i = 0; i < Game.getCivsSize(); i++) {
         Game.getCiv(i).convertReligion.buildProvincesConvertReligion(i);
         Game.getCiv(i).civilizationCores.buildProvincesNonCore(i);
         Game.getCiv(i).armiesWithoutGenerals.buildArmiesWithoutGenerals(i);
      }
   }

   public static final void loadSave_Population() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "Provinces_Population.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);
         int id = 0;

         for (JsonValue jValue : tempArrayData) {
            ProvinceData_Population tempData = json.readValue(ProvinceData_Population.class, jValue);
            Game.lProvincesPopulation.set(id++, tempData);
         }

         tempArrayData.clear();
         Object var8 = null;
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }
   }

   public static final void buildProvincePopulationData() {
      try {
         for (int i = 0; i < Game.getProvincesSize(); i++) {
            Game.getProvince(i).buildPopulation_LoadGame();
         }
      } catch (Exception var1) {
         CFG.exceptionStack(var1);
      }
   }

   public static final void buildProvinceCores() {
      try {
         for (int i = 0; i < Game.getProvincesSize(); i++) {
            Game.getProvince(i).updateCoresSize();
            Game.getProvince(i).updateHaveACore();
         }
      } catch (Exception var1) {
         CFG.exceptionStack(var1);
      }
   }

   public static final void buildProvinceData() {
      try {
         for (int i = 0; i < Game.getProvincesSize(); i++) {
            Game.getProvince(i).updateBuildingLimit();
            Game.getProvince(i).updateInfrastructureMax();
         }
      } catch (Exception var1) {
         CFG.exceptionStack(var1);
      }
   }

   public static final boolean loadSave_Vassals() {
      try {
         if (!FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "Vassals.json").exists()) {
            return false;
         } else {
            FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "Vassals.json");
            Json json = new Json();
            ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);

            for (JsonValue jValue : tempArrayData) {
               Vassal tempData = json.readValue(Vassal.class, jValue);
               Game.getCiv(Game.getCiv(tempData.c).getPuppetOfCivID())
                  .diplomacy
                  .setVassal_LoadData(tempData.c, tempData.tL, tempData.mL, tempData.cW, tempData.lD);
            }

            tempArrayData.clear();
            ArrayList<JsonValue> var7 = null;
            return true;
         }
      } catch (Exception var6) {
         CFG.exceptionStack(var6);
         return false;
      }
   }

   public static final boolean loadSave_ProvincesBuildings(int id) {
      try {
         if (!FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "Provinces_Buildings_" + id + ".json").exists()) {
            return false;
         } else {
            FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "Provinces_Buildings_" + id + ".json");
            Json json = new Json();
            ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);

            for (JsonValue jValue : tempArrayData) {
               SaveManager.ScenarioData_Buildings tempData = json.readValue(SaveManager.ScenarioData_Buildings.class, jValue);
               if (tempData.pid >= 0 && tempData.pid < Game.getProvincesSize()) {
                  int i = 0;

                  for (int iSize = tempData.b0.size(); i < iSize; i++) {
                     Game.getProvince(tempData.pid).addNewBuilding_LoadScenario(new ProvinceConstructedBuilding(tempData.b0.get(i), tempData.b1.get(i)));
                  }
               }
            }

            tempArrayData.clear();
            ArrayList<JsonValue> var10 = null;
            return true;
         }
      } catch (Exception var9) {
         CFG.exceptionStack(var9);
         return false;
      }
   }

   public static final void loadSave_ProvinceConstructionBuilding() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "Provinces_BuildingsConstruction.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);

         for (JsonValue jValue : tempArrayData) {
            SaveGameManager.ScenarioData_ProvinceConstructionBuilding tempData = json.readValue(
               SaveGameManager.ScenarioData_ProvinceConstructionBuilding.class, jValue
            );
            if (tempData.p >= 0 && tempData.p < Game.getProvincesSize()) {
               int i = 0;

               for (int iSize = tempData.b0.size(); i < iSize; i++) {
                  Game.getProvince(tempData.p)
                     .addBuildingConstruction_Load(
                        new ProvinceConstructionBuilding(tempData.b0.get(i), tempData.b1.get(i), tempData.ct.get(i), tempData.ctL.get(i))
                     );
               }
            }
         }

         tempArrayData.clear();
         Object var9 = null;
      } catch (Exception var8) {
         CFG.exceptionStack(var8);
      }
   }

   public static final void loadSave_ProvinceInvestDaysLeft() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "Provinces_InvestEconomy.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);

         for (JsonValue jValue : tempArrayData) {
            SaveGameManager.Save_ProvinceInvest tempData = json.readValue(SaveGameManager.Save_ProvinceInvest.class, jValue);
            if (tempData.p >= 0 && tempData.p < Game.getProvincesSize()) {
               int i = 0;

               for (int iSize = tempData.d.size(); i < iSize; i++) {
                  Game.getProvince(tempData.p).addInvestInProvince_Load(tempData.d.get(i), tempData.n.get(i));
               }
            }
         }

         tempArrayData.clear();
         Object var9 = null;
      } catch (Exception var8) {
         CFG.exceptionStack(var8);
      }
   }

   public static final void loadSave_ProvinceInvestTax() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "Provinces_InvestTax.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);

         for (JsonValue jValue : tempArrayData) {
            SaveGameManager.Save_ProvinceInvest tempData = json.readValue(SaveGameManager.Save_ProvinceInvest.class, jValue);
            if (tempData.p >= 0 && tempData.p < Game.getProvincesSize()) {
               int i = 0;

               for (int iSize = tempData.d.size(); i < iSize; i++) {
                  Game.getProvince(tempData.p).addIncreaseTaxEfficiencyInProvince_Load(tempData.d.get(i), tempData.n.get(i));
               }
            }
         }

         tempArrayData.clear();
         Object var9 = null;
      } catch (Exception var8) {
         CFG.exceptionStack(var8);
      }
   }

   public static final void loadSave_ProvinceInvestManpower() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "Provinces_InvestManpower.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);

         for (JsonValue jValue : tempArrayData) {
            SaveGameManager.Save_ProvinceInvest tempData = json.readValue(SaveGameManager.Save_ProvinceInvest.class, jValue);
            if (tempData.p >= 0 && tempData.p < Game.getProvincesSize()) {
               int i = 0;

               for (int iSize = tempData.d.size(); i < iSize; i++) {
                  Game.getProvince(tempData.p).addIncreaseManpowerInProvince_Load(tempData.d.get(i), tempData.n.get(i));
               }
            }
         }

         tempArrayData.clear();
         Object var9 = null;
      } catch (Exception var8) {
         CFG.exceptionStack(var8);
      }
   }

   public static final void loadSave_ProvinceInvestGrowthRate() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "Provinces_InvestGrowth.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);

         for (JsonValue jValue : tempArrayData) {
            SaveGameManager.Save_ProvinceInvest tempData = json.readValue(SaveGameManager.Save_ProvinceInvest.class, jValue);
            if (tempData.p >= 0 && tempData.p < Game.getProvincesSize()) {
               int i = 0;

               for (int iSize = tempData.d.size(); i < iSize; i++) {
                  Game.getProvince(tempData.p).addIncreaseGrowthRateInProvince_Load(tempData.d.get(i), tempData.n.get(i));
               }
            }
         }

         tempArrayData.clear();
         Object var9 = null;
      } catch (Exception var8) {
         CFG.exceptionStack(var8);
      }
   }

   public static final void loadSave_ProvinceInvestInfrastructure() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "Provinces_InvestInfrastructure.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);

         for (JsonValue jValue : tempArrayData) {
            SaveGameManager.Save_ProvinceInvest tempData = json.readValue(SaveGameManager.Save_ProvinceInvest.class, jValue);
            if (tempData.p >= 0 && tempData.p < Game.getProvincesSize()) {
               int i = 0;

               for (int iSize = tempData.d.size(); i < iSize; i++) {
                  Game.getProvince(tempData.p).addDevelopInfrastructure_Load(tempData.d.get(i), tempData.n.get(i));
               }
            }
         }

         tempArrayData.clear();
         Object var9 = null;
      } catch (Exception var8) {
         CFG.exceptionStack(var8);
      }
   }

   public static final void loadSave_ProvinceCoreCreation() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "Provinces_CoreCreation.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);

         for (JsonValue jValue : tempArrayData) {
            SaveGameManager.Save_ProvinceInvest_Single tempData = json.readValue(SaveGameManager.Save_ProvinceInvest_Single.class, jValue);
            if (tempData.p >= 0 && tempData.p < Game.getProvincesSize()) {
               Game.getProvince(tempData.p).addCoreCreation_Load(tempData.d, tempData.n);
            }
         }

         tempArrayData.clear();
         Object var7 = null;
      } catch (Exception var6) {
         CFG.exceptionStack(var6);
      }
   }

   public static final void loadSave_ProvinceReligionConversion() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "Provinces_Conversion.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);

         for (JsonValue jValue : tempArrayData) {
            SaveGameManager.Save_ProvinceInvest_Single tempData = json.readValue(SaveGameManager.Save_ProvinceInvest_Single.class, jValue);
            if (tempData.p >= 0 && tempData.p < Game.getProvincesSize()) {
               Game.getProvince(tempData.p).addReligionConversion_Load(tempData.d, tempData.n);
            }
         }

         tempArrayData.clear();
         Object var7 = null;
      } catch (Exception var6) {
         CFG.exceptionStack(var6);
      }
   }

   public static final void loadSave_ProvinceWonderConstruction() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "Provinces_WonderConstruction.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);

         for (JsonValue jValue : tempArrayData) {
            SaveGameManager.Save_ProvinceInvest_Single tempData = json.readValue(SaveGameManager.Save_ProvinceInvest_Single.class, jValue);
            if (tempData.p >= 0 && tempData.p < Game.getProvincesSize()) {
               Game.getProvince(tempData.p).addWonderConstruction_Load(tempData.d, tempData.n);
            }
         }

         tempArrayData.clear();
         Object var7 = null;
      } catch (Exception var6) {
         CFG.exceptionStack(var6);
      }
   }

   public static final void loadSave_CivsNukesProduction() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "NukesProduction.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);

         for (JsonValue jValue : tempArrayData) {
            SaveGameManager.Save_ProvinceInvest tempData = json.readValue(SaveGameManager.Save_ProvinceInvest.class, jValue);
            if (tempData.p > 0 && tempData.p < Game.getCivsSize()) {
               int i = 0;

               for (int iSize = tempData.d.size(); i < iSize; i++) {
                  Game.getCiv(tempData.p).addNukeProduction_Load(tempData.d.get(i), tempData.n.get(i));
               }
            }
         }

         tempArrayData.clear();
         Object var9 = null;
      } catch (Exception var8) {
         CFG.exceptionStack(var8);
      }
   }

   public static final void loadSave_Civs() {
      Game.lCivs.add(Game.getNeutralCivilization());
      Game.lCivs.get(0).iCivID = 0;

      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "Civs.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);
         int tCivID = 1;

         for (JsonValue jValue : tempArrayData) {
            CivData tempData = json.readValue(CivData.class, jValue);
            Game.LoadCivilizationData civData = Game.loadCivilization(tempData.t);
            Game.lCivs.add(new Civilization(tCivID, tempData.t, tempData.p, civData.iR, civData.iG, civData.iB, tempData.c, tempData.r, civData.GroupID, true));
            tCivID++;
            tempData = null;
         }

         tempArrayData.clear();
         Object var9 = null;
      } catch (Exception var8) {
         CFG.exceptionStack(var8);
      }

      Game.iCivsSize = Game.lCivs.size();
   }

   public static final void loadSave_Civs2() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "Civs2.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);
         int tCivID = 1;

         for (JsonValue jValue : tempArrayData) {
            CivData2 tempData = json.readValue(CivData2.class, jValue);
            tempData.update(tCivID);
            tCivID++;
            Object var9 = null;
         }

         tempArrayData.clear();
         Object var8 = null;
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }
   }

   public static final void loadSave_Civs3() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "Civs3.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);
         int tCivID = 1;

         for (JsonValue jValue : tempArrayData) {
            CivData3 tempData = json.readValue(CivData3.class, jValue);
            Game.getCiv(tCivID).civData3 = tempData;
            tCivID++;
            tempData = null;
         }

         tempArrayData.clear();
         Object var8 = null;
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }
   }

   public static final void loadSave_Civs4() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "Civs4.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);
         int tCivID = 1;

         for (JsonValue jValue : tempArrayData) {
            CivData4 tempData = json.readValue(CivData4.class, jValue);
            Game.getCiv(tCivID).civData4 = tempData;
            tCivID++;
            tempData = null;
         }

         tempArrayData.clear();
         Object var8 = null;
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }
   }

   public static final void loadSave_CivsUnlockedTechnologies() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "UnlockedTechnologies.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);
         int tCivID = 1;

         for (JsonValue jValue : tempArrayData) {
            SaveGameManager.Save_Civ_UnlockedTechnologies tempData = json.readValue(SaveGameManager.Save_Civ_UnlockedTechnologies.class, jValue);

            for (int i = 0; i < tempData.a; i++) {
               Game.getCiv(tCivID).setTechnologyResearched(i, true);
            }

            int i = 0;

            for (int iSize = tempData.u.size(); i < iSize; i++) {
               Game.getCiv(tCivID).setTechnologyResearched(tempData.u.get(i), true);
            }

            tCivID++;
            tempData = null;
         }

         tempArrayData.clear();
         Object var10 = null;
      } catch (Exception var9) {
         CFG.exceptionStack(var9);
      }
   }

   public static final void loadSave_CivsResearchProgress() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "ResearchProgress.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);
         int tCivID = 1;

         for (JsonValue jValue : tempArrayData) {
            SaveGameManager.Save_Civ_ResearchProgress tempData = json.readValue(SaveGameManager.Save_Civ_ResearchProgress.class, jValue);

            for (int i = 0; i < tempData.t.size(); i++) {
               Game.getCiv(tCivID).lResearching.add(new TechnologyResearch(tempData.t.get(i), tempData.p.get(i).intValue() / 1000.0F));
            }

            tCivID++;
            tempData = null;
         }

         tempArrayData.clear();
         Object var9 = null;
      } catch (Exception var8) {
         CFG.exceptionStack(var8);
      }
   }

   public static final void loadSave_CivsLaws() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "Laws.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);
         int tCivID = 1;

         for (JsonValue jValue : tempArrayData) {
            SaveGameManager.Save_Civ_Laws tempData = json.readValue(SaveGameManager.Save_Civ_Laws.class, jValue);
            int i = 0;

            for (int iSize = tempData.l.size(); i < iSize; i++) {
               Game.getCiv(tCivID).laws.set(i, tempData.l.get(i));
               LawsManager.updateCivBonuses(i, tempData.l.get(i), tCivID, 1.0F);
            }

            tCivID++;
            tempData = null;
         }

         tempArrayData.clear();
         Object var10 = null;
      } catch (Exception var9) {
         CFG.exceptionStack(var9);
      }
   }

   public static final void loadSave_CivsRecruitArmyCreate() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "RecruitArmyCreate.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);

         for (JsonValue jValue : tempArrayData) {
            SaveGameManager.Save_Civ_RecruitArmyCreate tempData = json.readValue(SaveGameManager.Save_Civ_RecruitArmyCreate.class, jValue);
            int i = 0;

            for (int iSize = tempData.k.size(); i < iSize; i++) {
               Game.getCiv(tempData.c).lCreateNewArmy.put(tempData.k.get(i), tempData.p.get(i));
            }

            tempData = null;
         }

         tempArrayData.clear();
         Object var9 = null;
      } catch (Exception var8) {
         CFG.exceptionStack(var8);
      }
   }

   public static final void loadSave_CivsRecruitArmy() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "RecruitArmy.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);

         for (JsonValue jValue : tempArrayData) {
            SaveGameManager.Save_Civ_RecruitArmy tempData = json.readValue(SaveGameManager.Save_Civ_RecruitArmy.class, jValue);
            int i = 0;

            for (int iSize = tempData.a.size(); i < iSize; i++) {
               Game.getCiv(tempData.c).addRecruitArmy_Load(tempData.a.get(i));
            }

            Game.getCiv(tempData.c).addRecruitArmy_LoadUpdateSize();
            tempData = null;
         }

         tempArrayData.clear();
         Object var9 = null;
      } catch (Exception var8) {
         CFG.exceptionStack(var8);
      }
   }

   public static final boolean loadSave_ProvincesArmy_MoreFiles(int id) {
      try {
         if (!FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "Provinces_Armies_" + id + ".json").exists()) {
            return false;
         } else {
            FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "Provinces_Armies_" + id + ".json");
            Json json = new Json();
            ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);

            for (JsonValue jValue : tempArrayData) {
               SaveGameManager.Save_Provinces_ArmyDivision tempData = json.readValue(SaveGameManager.Save_Provinces_ArmyDivision.class, jValue);
               Game.getProvince(tempData.p).addArmy_Load(new ArmyDivision(tempData));
               tempData = null;
            }

            tempArrayData.clear();
            ArrayList<JsonValue> var8 = null;
            return true;
         }
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
         return false;
      }
   }

   public static final void loadSave_MapPlagues() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "Plagues.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);
         PlagueManager.activePlagues.clear();

         for (JsonValue jValue : tempArrayData) {
            Plague tempData = json.readValue(Plague.class, jValue);
            PlagueManager.activePlagues.add(tempData);
            tempData = null;
         }

         tempArrayData.clear();
         Object var7 = null;
      } catch (Exception var6) {
         CFG.exceptionStack(var6);
      }
   }

   public static final void loadSave_ProvincesPlagues() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "Provinces_Plagues.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);

         for (JsonValue jValue : tempArrayData) {
            SaveGameManager.Save_Province_Plague tempData = json.readValue(SaveGameManager.Save_Province_Plague.class, jValue);
            Game.getProvince(tempData.p).provincePlague = tempData.l;
            Object var8 = null;
         }

         tempArrayData.clear();
         Object var7 = null;
      } catch (Exception var6) {
         CFG.exceptionStack(var6);
      }
   }

   public static final void loadSave_CivsUnlockedAdvantages() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "Advantages.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);
         int tCivID = 1;

         for (JsonValue jValue : tempArrayData) {
            SaveGameManager.Save_Civ_UnlockedAdvantages tempData = json.readValue(SaveGameManager.Save_Civ_UnlockedAdvantages.class, jValue);
            int i = 0;

            for (int iSize = tempData.a.size(); i < iSize; i++) {
               Game.getCiv(tCivID).lAdvantages.add(new CivilizationLegacy(tempData.a.get(i), tempData.l.get(i)));

               try {
                  for (int a = tempData.l.get(i); a >= 0; a--) {
                     AdvantagesManager.updateCivBonuses(tempData.a.get(i), a, tCivID, true);
                  }
               } catch (Exception var10) {
                  CFG.exceptionStack(var10);
               }
            }

            Game.getCiv(tCivID).iAdvantagesSize = Game.getCiv(tCivID).lAdvantages.size();
            tCivID++;
            tempData = null;
         }

         tempArrayData.clear();
         Object var12 = null;
      } catch (Exception var11) {
         CFG.exceptionStack(var11);
      }
   }

   public static final void loadSave_CivsRulers() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "Rulers.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);
         int tCivID = 1;

         for (JsonValue jValue : tempArrayData) {
            SaveGameManager.Save_Civ_Ruler tempData = json.readValue(SaveGameManager.Save_Civ_Ruler.class, jValue);
            Game.getCiv(tCivID).ruler = new Ruler(tempData);
            tCivID++;
            tempData = null;
         }

         tempArrayData.clear();
         Object var8 = null;
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }
   }

   public static final void loadSave_CivsRulers_Bonuses() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "RulersBonuses.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);
         int tCivID = 1;

         for (JsonValue jValue : tempArrayData) {
            CivilizationBonuses tempData = json.readValue(CivilizationBonuses.class, jValue);
            if (Game.getCiv(tCivID).ruler != null) {
               Game.getCiv(tCivID).ruler.initRulerBonuses_Load(tCivID, tempData);
            }

            tCivID++;
            tempData = null;
         }

         tempArrayData.clear();
         Object var8 = null;
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }
   }

   public static final void loadSave_CivsAdvisorsAdmBonuses() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "AdvisorAdministrationBonuses.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);
         int tCivID = 1;

         for (JsonValue jValue : tempArrayData) {
            Advisor tempData = json.readValue(Advisor.class, jValue);
            if (tempData != null) {
               Game.getCiv(tCivID).advisorAdministration = tempData;
            }

            tCivID++;
            tempData = null;
         }

         tempArrayData.clear();
         Object var8 = null;
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }
   }

   public static final void loadSave_CivsAdvisorsAdm() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "AdvisorAdministration.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);
         int tCivID = 1;

         for (JsonValue jValue : tempArrayData) {
            SaveGameManager.Save_Civ_Advisor tempData = json.readValue(SaveGameManager.Save_Civ_Advisor.class, jValue);
            if (tempData != null) {
               Game.getCiv(tCivID).advisorAdministration.sName = tempData.n;
               Game.getCiv(tCivID).advisorAdministration.iYearOfBirth = tempData.y;
               Game.getCiv(tCivID).advisorAdministration.iMonthOfBirth = tempData.m;
               Game.getCiv(tCivID).advisorAdministration.iDayOfBirth = tempData.d;
               Game.getCiv(tCivID).advisorAdministration.sIMG = tempData.g;
               Game.getCiv(tCivID).advisorAdministration.imageID = tempData.e;
               Game.getCiv(tCivID).advisorAdministration.iLevel = tempData.l;

               try {
                  AdvisorManager.updateCivBonuses(Game.getCiv(tCivID).advisorAdministration, tCivID, 1);
               } catch (Exception var8) {
                  CFG.exceptionStack(var8);
               }
            }

            tCivID++;
            Object var11 = null;
         }

         tempArrayData.clear();
         Object var10 = null;
      } catch (Exception var9) {
         CFG.exceptionStack(var9);
      }
   }

   public static final void loadSave_CivsAdvisorsEconomyBonuses() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "AdvisorEconomyBonuses.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);
         int tCivID = 1;

         for (JsonValue jValue : tempArrayData) {
            Advisor tempData = json.readValue(Advisor.class, jValue);
            if (tempData != null) {
               Game.getCiv(tCivID).advisorEconomy = tempData;
            }

            tCivID++;
            tempData = null;
         }

         tempArrayData.clear();
         Object var8 = null;
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }
   }

   public static final void loadSave_CivsAdvisorsEconomy() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "AdvisorEconomy.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);
         int tCivID = 1;

         for (JsonValue jValue : tempArrayData) {
            SaveGameManager.Save_Civ_Advisor tempData = json.readValue(SaveGameManager.Save_Civ_Advisor.class, jValue);
            if (tempData != null) {
               Game.getCiv(tCivID).advisorEconomy.sName = tempData.n;
               Game.getCiv(tCivID).advisorEconomy.iYearOfBirth = tempData.y;
               Game.getCiv(tCivID).advisorEconomy.iMonthOfBirth = tempData.m;
               Game.getCiv(tCivID).advisorEconomy.iDayOfBirth = tempData.d;
               Game.getCiv(tCivID).advisorEconomy.sIMG = tempData.g;
               Game.getCiv(tCivID).advisorEconomy.imageID = tempData.e;
               Game.getCiv(tCivID).advisorEconomy.iLevel = tempData.l;

               try {
                  AdvisorManager.updateCivBonuses(Game.getCiv(tCivID).advisorEconomy, tCivID, 1);
               } catch (Exception var8) {
                  CFG.exceptionStack(var8);
               }
            }

            tCivID++;
            Object var11 = null;
         }

         tempArrayData.clear();
         Object var10 = null;
      } catch (Exception var9) {
         CFG.exceptionStack(var9);
      }
   }

   public static final void loadSave_CivsAdvisorsInnovationBonuses() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "AdvisorInnovationBonuses.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);
         int tCivID = 1;

         for (JsonValue jValue : tempArrayData) {
            Advisor tempData = json.readValue(Advisor.class, jValue);
            if (tempData != null) {
               Game.getCiv(tCivID).advisorTechnology = tempData;
            }

            tCivID++;
            tempData = null;
         }

         tempArrayData.clear();
         Object var8 = null;
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }
   }

   public static final void loadSave_CivsAdvisorsInnovation() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "AdvisorInnovation.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);
         int tCivID = 1;

         for (JsonValue jValue : tempArrayData) {
            SaveGameManager.Save_Civ_Advisor tempData = json.readValue(SaveGameManager.Save_Civ_Advisor.class, jValue);
            if (tempData != null) {
               Game.getCiv(tCivID).advisorTechnology.sName = tempData.n;
               Game.getCiv(tCivID).advisorTechnology.iYearOfBirth = tempData.y;
               Game.getCiv(tCivID).advisorTechnology.iMonthOfBirth = tempData.m;
               Game.getCiv(tCivID).advisorTechnology.iDayOfBirth = tempData.d;
               Game.getCiv(tCivID).advisorTechnology.sIMG = tempData.g;
               Game.getCiv(tCivID).advisorTechnology.imageID = tempData.e;
               Game.getCiv(tCivID).advisorTechnology.iLevel = tempData.l;

               try {
                  AdvisorManager.updateCivBonuses(Game.getCiv(tCivID).advisorTechnology, tCivID, 1);
               } catch (Exception var8) {
                  CFG.exceptionStack(var8);
               }
            }

            tCivID++;
            Object var11 = null;
         }

         tempArrayData.clear();
         Object var10 = null;
      } catch (Exception var9) {
         CFG.exceptionStack(var9);
      }
   }

   public static final void loadSave_CivsAdvisorsMilitaryBonuses() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "AdvisorMilitaryBonuses.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);
         int tCivID = 1;

         for (JsonValue jValue : tempArrayData) {
            Advisor tempData = json.readValue(Advisor.class, jValue);
            if (tempData != null) {
               Game.getCiv(tCivID).advisorMilitary = tempData;
            }

            tCivID++;
            tempData = null;
         }

         tempArrayData.clear();
         Object var8 = null;
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }
   }

   public static final void loadSave_CivsAdvisorsMilitary() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "AdvisorMilitary.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);
         int tCivID = 1;

         for (JsonValue jValue : tempArrayData) {
            SaveGameManager.Save_Civ_Advisor tempData = json.readValue(SaveGameManager.Save_Civ_Advisor.class, jValue);
            if (tempData != null) {
               Game.getCiv(tCivID).advisorMilitary.sName = tempData.n;
               Game.getCiv(tCivID).advisorMilitary.iYearOfBirth = tempData.y;
               Game.getCiv(tCivID).advisorMilitary.iMonthOfBirth = tempData.m;
               Game.getCiv(tCivID).advisorMilitary.iDayOfBirth = tempData.d;
               Game.getCiv(tCivID).advisorMilitary.sIMG = tempData.g;
               Game.getCiv(tCivID).advisorMilitary.imageID = tempData.e;
               Game.getCiv(tCivID).advisorMilitary.iLevel = tempData.l;

               try {
                  AdvisorManager.updateCivBonuses(Game.getCiv(tCivID).advisorMilitary, tCivID, 1);
               } catch (Exception var8) {
                  CFG.exceptionStack(var8);
               }
            }

            tCivID++;
            Object var11 = null;
         }

         tempArrayData.clear();
         Object var10 = null;
      } catch (Exception var9) {
         CFG.exceptionStack(var9);
      }
   }

   public static final void loadSave_CivsGeneralsNotAssigned() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "GeneralsNotAssigned.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);

         for (JsonValue jValue : tempArrayData) {
            ArmyGeneral tempData = json.readValue(ArmyGeneral.class, jValue);
            if (tempData != null) {
               Game.getCiv(tempData.c).addGeneral(tempData);
            }

            tempData = null;
         }

         tempArrayData.clear();
         Object var7 = null;
      } catch (Exception var6) {
         CFG.exceptionStack(var6);
      }
   }

   public static final void loadSave_CivsEventsData() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "EventsData.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);
         int tCivID = 1;

         for (JsonValue jValue : tempArrayData) {
            CivilizationEventsData tempData = json.readValue(CivilizationEventsData.class, jValue);
            Game.getCiv(tCivID).eventsData = tempData;
            tCivID++;
            tempData = null;
         }

         tempArrayData.clear();
         Object var8 = null;
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }
   }

   public static final void loadSave_CivsEventsData2() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "EventsData2.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);
         int tCivID = 1;

         for (JsonValue jValue : tempArrayData) {
            CivilizationEventsData2 tempData = json.readValue(CivilizationEventsData2.class, jValue);
            Game.getCiv(tCivID).eventsData2 = tempData;
            tCivID++;
            tempData = null;
         }

         tempArrayData.clear();
         Object var8 = null;
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }
   }

   public static final void loadSave_CivsEventsData3() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "EventsData3.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);
         int tCivID = 1;

         for (JsonValue jValue : tempArrayData) {
            CivilizationEventsData3 tempData = json.readValue(CivilizationEventsData3.class, jValue);
            Game.getCiv(tCivID).eventsData3 = tempData;
            tCivID++;
            tempData = null;
         }

         tempArrayData.clear();
         Object var8 = null;
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }
   }

   public static final void loadSave_CivsTemporaryBonuses() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "BonusesTemp.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);
         int tCivID = 1;

         for (JsonValue jValue : tempArrayData) {
            SaveGameManager.Save_Civ_TemporaryBonuses tempData = json.readValue(SaveGameManager.Save_Civ_TemporaryBonuses.class, jValue);
            if (tempData != null) {
               for (int i = 0; i < tempData.b.size(); i++) {
                  Game.getCiv(tCivID).addCivilizationBonus_Temporary(tempData.b.get(i));
               }
            }

            tCivID++;
            tempData = null;
         }

         tempArrayData.clear();
         Object var9 = null;
      } catch (Exception var8) {
         CFG.exceptionStack(var8);
      }
   }

   public static final void loadSave_CivsEventsVariables() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "EventsVariables.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);
         int tCivID = 1;

         for (JsonValue jValue : tempArrayData) {
            CivilizationEventsData_Variables tempData = json.readValue(CivilizationEventsData_Variables.class, jValue);
            if (tempData != null) {
               Game.getCiv(tCivID).eventsDataVariables = tempData;
            }

            tCivID++;
            tempData = null;
         }

         tempArrayData.clear();
         Object var8 = null;
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }
   }

   public static final void loadSave_CivsEventsVariables2() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "EventsVariables2.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);
         int tCivID = (int)Math.floor(Game.getCivsSize() / 2.0F);

         for (JsonValue jValue : tempArrayData) {
            CivilizationEventsData_Variables tempData = json.readValue(CivilizationEventsData_Variables.class, jValue);
            if (tempData != null) {
               Game.getCiv(tCivID).eventsDataVariables = tempData;
            }

            tCivID++;
            tempData = null;
         }

         tempArrayData.clear();
         Object var8 = null;
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }
   }

   public static final void loadSave_CivsGoldenAges() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "GoldenAge.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);
         int tCivID = 1;

         for (JsonValue jValue : tempArrayData) {
            CivilizationGoldenAge tempData = json.readValue(CivilizationGoldenAge.class, jValue);
            if (tempData != null) {
               Game.getCiv(tCivID).goldenAge = tempData;
            }

            tCivID++;
            tempData = null;
         }

         tempArrayData.clear();
         Object var8 = null;
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }
   }

   public static final void loadSave_CivsLoans() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "Loans.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);

         for (JsonValue jValue : tempArrayData) {
            SaveGameManager.Save_Civ_Loan tempData = json.readValue(SaveGameManager.Save_Civ_Loan.class, jValue);
            Game.getCiv(tempData.c).addLoan_Load(new Loan(tempData));
            tempData = null;
         }

         tempArrayData.clear();
         Object var7 = null;
      } catch (Exception var6) {
         CFG.exceptionStack(var6);
      }
   }

   public static final void loadSave_CivsLegacies() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "Legacies.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);
         int tCivID = 1;

         for (JsonValue jValue : tempArrayData) {
            SaveGameManager.Save_Civ_Legacies tempData = json.readValue(SaveGameManager.Save_Civ_Legacies.class, jValue);
            if (tempData.b.size() > 0) {
               for (int i = 0; i < tempData.b.size(); i++) {
                  Game.getCiv(tCivID).addLegacy_Load(tempData.b.get(i).id, tempData.b.get(i).lvl);
               }
            }

            tCivID++;
            tempData = null;
         }

         tempArrayData.clear();
         Object var9 = null;
      } catch (Exception var8) {
         CFG.exceptionStack(var8);
      }
   }

   public static final void loadSave_CivsMoveUnits() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "MoveUnits.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);

         for (JsonValue jValue : tempArrayData) {
            SaveGameManager.Save_Civ_MoveUnits tempData = json.readValue(SaveGameManager.Save_Civ_MoveUnits.class, jValue);
            ArmyDivision armyDivision = Game.getProvince(tempData.f).getArmy(tempData.k);
            if (armyDivision != null && Game.getCiv(tempData.c).newMove(tempData.f, tempData.t, tempData.k, 0, armyDivision.inRetreat)) {
               Game.getCiv(tempData.c).updateMoveUnits_Load(tempData, armyDivision.inRetreat, armyDivision.inBattle);
            }

            tempData = null;
         }

         tempArrayData.clear();
         Object var8 = null;
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }
   }

   public static final void loadSave_RebelsMoveUnits() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "RebelsMoveUnits.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);

         for (JsonValue jValue : tempArrayData) {
            SaveGameManager.Save_Civ_MoveUnits tempData = json.readValue(SaveGameManager.Save_Civ_MoveUnits.class, jValue);
            ArmyDivision armyDivision = Game.getProvince(tempData.f).getArmy(tempData.k);
            if (armyDivision != null && Game.revolutionMoveUnits.newMove(tempData.f, tempData.t, tempData.k, 0, false)) {
               Game.revolutionMoveUnits.updateMoveUnits_Load(tempData, false, armyDivision.inBattle);
            }

            tempData = null;
         }

         tempArrayData.clear();
         Object var8 = null;
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }
   }

   public static final void loadSave_Rebels() {
      try {
         Json json = new Json();
         Game.revolutionManager = json.fromJson(
            RevolutionManager.class, FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "Rebels.json")
         );
      } catch (Exception var1) {
         CFG.exceptionStack(var1);
      }
   }

   public static final void loadSave_MapBattles() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "Battles.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);

         for (JsonValue jValue : tempArrayData) {
            Battle tempData = json.readValue(Battle.class, jValue);
            tempData.attackingArmy.updateLoaded_Load();
            tempData.defendingArmy.updateLoaded_Load();
            Game.battleManager.lBattle.add(tempData);
            tempData = null;
         }

         tempArrayData.clear();
         Object var7 = null;
      } catch (Exception var6) {
         CFG.exceptionStack(var6);
      }

      Game.battleManager.iBattleSize = Game.battleManager.lBattle.size();
   }

   public static final void loadSave_MapBattles_Update() {
      for (int i = 0; i < Game.battleManager.iBattleSize; i++) {
         Game.battleManager.lBattle.get(i).attackingArmy.updateInBattle_Load(Game.battleManager.lBattle.get(i).provinceID);
         Game.battleManager.lBattle.get(i).defendingArmy.updateInBattle_Load(Game.battleManager.lBattle.get(i).provinceID);
      }
   }

   public static final void loadSaveRelations() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "Relations.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);

         for (JsonValue jValue : tempArrayData) {
            SaveManager.ScenarioData_Relations tempData = json.readValue(SaveManager.ScenarioData_Relations.class, jValue);
            if (tempData.c > 0 && tempData.c < Game.getCivsSize()) {
               for (int i = tempData.w.size() - 1; i >= 0; i--) {
                  Game.getCiv(tempData.c).diplomacy.setRelation_Load(tempData.w.get(i), tempData.r.get(i).intValue());
               }
            }
         }

         tempArrayData.clear();
         Object var8 = null;
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }
   }

   public static final void loadSaveRelations2() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "Relations2.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);

         for (JsonValue jValue : tempArrayData) {
            SaveManager.ScenarioData_Relations tempData = json.readValue(SaveManager.ScenarioData_Relations.class, jValue);
            if (tempData.c > 0 && tempData.c < Game.getCivsSize()) {
               for (int i = tempData.w.size() - 1; i >= 0; i--) {
                  Game.getCiv(tempData.c).diplomacy.setRelation_Load(tempData.w.get(i), tempData.r.get(i).intValue());
               }
            }
         }

         tempArrayData.clear();
         Object var8 = null;
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }
   }

   public static final void loadSaveAlliances() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "Alliances.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);

         for (JsonValue jValue : tempArrayData) {
            SaveManager.ScenarioData_Diplomacy tempData = json.readValue(SaveManager.ScenarioData_Diplomacy.class, jValue);
            if (tempData.pid > 0 && tempData.pid < Game.getCivsSize()) {
               for (int i = tempData.w0.size() - 1; i >= 0; i--) {
                  Game.getCiv(tempData.pid).diplomacy.addAlliance(tempData.w0.get(i), tempData.t0.get(i));
                  Game.getCiv(tempData.w0.get(i)).diplomacy.addAlliance(tempData.pid, tempData.t0.get(i));
               }
            }
         }

         tempArrayData.clear();
         Object var8 = null;
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }
   }

   public static final void loadSaveRivals() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "Rivals.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);

         for (JsonValue jValue : tempArrayData) {
            SaveManager.ScenarioData_Diplomacy tempData = json.readValue(SaveManager.ScenarioData_Diplomacy.class, jValue);
            if (tempData.pid > 0 && tempData.pid < Game.getCivsSize()) {
               int i = 0;

               for (int iSize = tempData.w0.size(); i < iSize; i++) {
                  Game.getCiv(tempData.pid).diplomacy.addRival_load(tempData.w0.get(i), tempData.t0.get(i));
               }
            }
         }

         tempArrayData.clear();
         Object var9 = null;
      } catch (Exception var8) {
         CFG.exceptionStack(var8);
      }
   }

   public static final void loadSaveRelationsImprove() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "RelationsImprove.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);

         for (JsonValue jValue : tempArrayData) {
            SaveManager.ScenarioData_Diplomacy tempData = json.readValue(SaveManager.ScenarioData_Diplomacy.class, jValue);
            if (tempData.pid > 0 && tempData.pid < Game.getCivsSize()) {
               int i = 0;

               for (int iSize = tempData.w0.size(); i < iSize; i++) {
                  Game.getCiv(tempData.pid).diplomacy.addImproveRelations_Load(tempData.w0.get(i), tempData.t0.get(i));
               }

               Game.getCiv(tempData.pid).diplomacy.iImprovingRelationsSize = Game.getCiv(tempData.pid).diplomacy.improvingRelations.size();
            }
         }

         tempArrayData.clear();
         Object var9 = null;
      } catch (Exception var8) {
         CFG.exceptionStack(var8);
      }
   }

   public static final void loadSaveRelationsDamage() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "RelationsDamage.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);

         for (JsonValue jValue : tempArrayData) {
            SaveManager.ScenarioData_Diplomacy tempData = json.readValue(SaveManager.ScenarioData_Diplomacy.class, jValue);
            if (tempData.pid > 0 && tempData.pid < Game.getCivsSize()) {
               int i = 0;

               for (int iSize = tempData.w0.size(); i < iSize; i++) {
                  Game.getCiv(tempData.pid).diplomacy.addDamageRelations_Load(tempData.w0.get(i), tempData.t0.get(i));
               }

               Game.getCiv(tempData.pid).diplomacy.iDamagingRelationsSize = Game.getCiv(tempData.pid).diplomacy.damagingRelations.size();
            }
         }

         tempArrayData.clear();
         Object var9 = null;
      } catch (Exception var8) {
         CFG.exceptionStack(var8);
      }
   }

   public static final void loadSaveDefensive() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "Defensive.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);

         for (JsonValue jValue : tempArrayData) {
            SaveManager.ScenarioData_Diplomacy tempData = json.readValue(SaveManager.ScenarioData_Diplomacy.class, jValue);
            if (tempData.pid > 0 && tempData.pid < Game.getCivsSize()) {
               for (int i = tempData.w0.size() - 1; i >= 0; i--) {
                  Game.getCiv(tempData.pid).diplomacy.addDefensivePact(tempData.w0.get(i), tempData.t0.get(i));
                  Game.getCiv(tempData.w0.get(i)).diplomacy.addDefensivePact(tempData.pid, tempData.t0.get(i));
               }
            }
         }

         tempArrayData.clear();
         Object var8 = null;
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }
   }

   public static final void loadSaveTruce() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "Trcues.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);

         for (JsonValue jValue : tempArrayData) {
            SaveManager.ScenarioData_Diplomacy tempData = json.readValue(SaveManager.ScenarioData_Diplomacy.class, jValue);
            if (tempData.pid > 0 && tempData.pid < Game.getCivsSize()) {
               for (int i = tempData.w0.size() - 1; i >= 0; i--) {
                  Game.getCiv(tempData.pid).diplomacy.addTruce(tempData.w0.get(i), tempData.t0.get(i));
                  Game.getCiv(tempData.w0.get(i)).diplomacy.addTruce(tempData.pid, tempData.t0.get(i));
               }
            }
         }

         tempArrayData.clear();
         Object var8 = null;
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }
   }

   public static final void loadSaveNonAggression() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "NonAggression.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);

         for (JsonValue jValue : tempArrayData) {
            SaveManager.ScenarioData_Diplomacy tempData = json.readValue(SaveManager.ScenarioData_Diplomacy.class, jValue);
            if (tempData.pid > 0 && tempData.pid < Game.getCivsSize()) {
               for (int i = tempData.w0.size() - 1; i >= 0; i--) {
                  Game.getCiv(tempData.pid).diplomacy.addNonAggressionPact(tempData.w0.get(i), tempData.t0.get(i));
                  Game.getCiv(tempData.w0.get(i)).diplomacy.addNonAggressionPact(tempData.pid, tempData.t0.get(i));
               }
            }
         }

         tempArrayData.clear();
         Object var8 = null;
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }
   }

   public static final void loadSaveMilitaryAccess() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "MilitaryAccess.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);

         for (JsonValue jValue : tempArrayData) {
            SaveManager.ScenarioData_Diplomacy tempData = json.readValue(SaveManager.ScenarioData_Diplomacy.class, jValue);
            if (tempData.pid > 0 && tempData.pid < Game.getCivsSize()) {
               for (int i = tempData.w0.size() - 1; i >= 0; i--) {
                  Game.getCiv(tempData.pid).diplomacy.addMilitaryAccess(tempData.w0.get(i), tempData.t0.get(i));
               }
            }
         }

         tempArrayData.clear();
         Object var8 = null;
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }
   }

   public static final void loadSaveGuarantee() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "Guarantee.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);

         for (JsonValue jValue : tempArrayData) {
            SaveManager.ScenarioData_Diplomacy tempData = json.readValue(SaveManager.ScenarioData_Diplomacy.class, jValue);
            if (tempData.pid > 0 && tempData.pid < Game.getCivsSize()) {
               for (int i = tempData.w0.size() - 1; i >= 0; i--) {
                  Game.getCiv(tempData.pid).diplomacy.addGuarantee(tempData.w0.get(i), tempData.t0.get(i));
                  Game.getCiv(tempData.w0.get(i)).diplomacy.addGuaranteeByCivID(tempData.pid, tempData.t0.get(i));
               }
            }
         }

         tempArrayData.clear();
         Object var8 = null;
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }
   }

   public static final void loadSave_AllianceSpecial() {
      try {
         Game.alliancesSpecial.clear();
         Game.alliancesSpecialSize = 0;
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "AllianceSpecial.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);

         for (JsonValue jValue : tempArrayData) {
            Alliance tempData = json.readValue(Alliance.class, jValue);
            Game.alliancesSpecial.add(tempData);
            tempData = null;
         }

         tempArrayData.clear();
         Object var7 = null;
      } catch (Exception var6) {
         CFG.exceptionStack(var6);
      }

      Game.alliancesSpecialSize = Game.alliancesSpecial.size();
   }

   public static final void loadSave_AI_Merge() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "AI_Merge.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);

         for (JsonValue jValue : tempArrayData) {
            SaveGameManager.Save_Civ_AI_Merge tempData = json.readValue(SaveGameManager.Save_Civ_AI_Merge.class, jValue);
            Game.getCiv(tempData.c).aiMerge = tempData.a;
            Object var8 = null;
         }

         tempArrayData.clear();
         Object var7 = null;
      } catch (Exception var6) {
         CFG.exceptionStack(var6);
      }
   }

   public static final void loadSave_AI_CreateNewArmy() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "AI_CreateNewArmy.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);

         for (JsonValue jValue : tempArrayData) {
            SaveGameManager.Save_Civ_AI_CreateNewArmy tempData = json.readValue(SaveGameManager.Save_Civ_AI_CreateNewArmy.class, jValue);
            Game.getCiv(tempData.c).aiCivCreateNewArmy = tempData.a;
            Object var8 = null;
         }

         tempArrayData.clear();
         Object var7 = null;
      } catch (Exception var6) {
         CFG.exceptionStack(var6);
      }
   }

   public static final void loadSave_AI_Diplomacy() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "AI_Diplomacy.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);
         int civID = 1;

         for (JsonValue jValue : tempArrayData) {
            AI_CivDiplomacy tempData = json.readValue(AI_CivDiplomacy.class, jValue);
            Game.getCiv(civID++).aiCivDiplomacy = tempData;
            tempData = null;
         }

         tempArrayData.clear();
         Object var8 = null;
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }
   }

   public static final void loadSave_AI_Budget() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "AI_Budget.json");
         Json json = new Json();
         AI_Budget tempData = json.fromJson(AI_Budget.class, fileList);
         AI_Manager.aiBudget = tempData;
         tempData = null;
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public static void loadBuildColonization() {
      for (int i = 1; i < Game.getCivsSize(); i++) {
         Game.getCiv(i).buildColonizationProvince();
      }

      for (int i = 0; i < Game.getProvincesSize(); i++) {
         if (Game.getProvince(i).getWonderBuilt()) {
            WondersManager.updateCivBonuses(Game.getProvince(i).getCivID(), Game.getProvince(i).wonderID, 1);
         }
      }
   }

   public static void loadBuild_ProvincesUnderSiege() {
      for (int i = 1; i < Game.getCivsSize(); i++) {
         Game.getCiv(i).buildUnderSiege();
      }
   }

   public static void loadBuild_ProvincesOccupied() {
      for (int i = 1; i < Game.getCivsSize(); i++) {
         Game.getCiv(i).buildOccupiedProvinces();
      }
   }

   public static final void loadSaveWars() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "Wars.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);
         WarManager.lWars.clear();

         for (JsonValue jValue : tempArrayData) {
            War tempData = json.readValue(War.class, jValue);
            WarManager.lWars.put(tempData.key, tempData);
            tempData.loadSave_AddInWar();
         }

         tempArrayData.clear();
         Object var7 = null;
      } catch (Exception var6) {
         CFG.exceptionStack(var6);
      }

      WarManager.iWarsSize = WarManager.lWars.size();
   }

   public static final void loadSaveWars_BuildData() {
      try {
         WarManager.buildWars_Load();
      } catch (Exception var1) {
         CFG.exceptionStack(var1);
      }
   }

   public static final void loadPlayer_Data() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "PlayerData.json");
         Json json = new Json();
         PlayerData tempData = json.fromJson(PlayerData.class, fileList);
         Game.player.playerData = tempData;
         tempData = null;
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }

      Game.player.iPinnedArmiesSize = Game.player.playerData.pinnedArmies.size();
      Game.player.iPinnedProvincesSize = Game.player.playerData.pinnedProvinces.size();
      Game.player.iActiveEventsSize = Game.player.playerData.activeEvents.size();
   }

   public static final void loadPlayer_Stats() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "PlayerStats.json");
         Json json = new Json();
         PlayerStats tempData = json.fromJson(PlayerStats.class, fileList);
         Game.player.playerStats = tempData;
         tempData = null;
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public static final void loadPlayer_Stats2() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "PlayerStats2.json");
         Json json = new Json();
         PlayerStats2 tempData = json.fromJson(PlayerStats2.class, fileList);
         Game.player.playerStats2 = tempData;
         tempData = null;
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public static final void loadPlayer_Stats3() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "PlayerStats3.json");
         Json json = new Json();
         PlayerStats3 tempData = json.fromJson(PlayerStats3.class, fileList);
         Game.player.playerStats3 = tempData;
         tempData = null;
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }

      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "PlayerStats4.json");
         Json json = new Json();
         PlayerStats4 tempData = json.fromJson(PlayerStats4.class, fileList);
         Game.player.playerStats4 = tempData;
         Game.player.playerStats4.flagsGenerated = false;
         tempData = null;
      } catch (Exception var3) {
      }
   }

   public static final void loadSave_ProvinceData() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "Provinces_Data.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);
         int tID = 0;

         for (JsonValue jValue : tempArrayData) {
            ProvinceData tempData = json.readValue(ProvinceData.class, jValue);
            Game.lProvincesData.set(tID, tempData);
            tID++;
         }

         tempArrayData.clear();
         Object var8 = null;
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }
   }

   public static final void loadSave_ProvinceData2() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "Provinces_Data2.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);
         int tID = 0;

         for (JsonValue jValue : tempArrayData) {
            ProvinceData2 tempData = json.readValue(ProvinceData2.class, jValue);
            Game.lProvincesData2.set(tID, tempData);
            tID++;
         }

         tempArrayData.clear();
         Object var8 = null;
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }
   }

   public static final void loadSave_ProvinceData3() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "Provinces_Data3.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);
         int tID = 0;

         for (JsonValue jValue : tempArrayData) {
            ProvinceData3 tempData = json.readValue(ProvinceData3.class, jValue);
            Game.lProvincesData3.set(tID, tempData);
            tID++;
         }

         tempArrayData.clear();
         Object var8 = null;
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }
   }

   public static final void loadSave_ProvinceData4() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "Provinces_Data4.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);
         int tID = 0;

         for (JsonValue jValue : tempArrayData) {
            ProvinceData4 tempData = json.readValue(ProvinceData4.class, jValue);
            Game.lProvincesData4.set(tID, tempData);
            tID++;
         }

         tempArrayData.clear();
         Object var8 = null;
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }
   }

   public static final void loadSave_ProvinceData5() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "Provinces_Data5.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);
         int tID = 0;

         for (JsonValue jValue : tempArrayData) {
            ProvinceData5 tempData = json.readValue(ProvinceData5.class, jValue);
            Game.lProvincesData5.set(tID, tempData);
            tID++;
         }

         tempArrayData.clear();
         Object var8 = null;
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }
   }

   public static final void loadSave_ProvinceData6() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "Provinces_Data6.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);
         int tID = 0;

         for (JsonValue jValue : tempArrayData) {
            ProvinceData6 tempData = json.readValue(ProvinceData6.class, jValue);
            Game.lProvincesData6.set(tID, tempData);
            tID++;
         }

         tempArrayData.clear();
         Object var8 = null;
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }
   }

   public static final void loadSave_ProvinceData7() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "Provinces_Data7.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);
         int tID = 0;

         for (JsonValue jValue : tempArrayData) {
            ProvinceData7 tempData = json.readValue(ProvinceData7.class, jValue);
            Game.lProvincesData7.set(tID, tempData);
            tID++;
         }

         tempArrayData.clear();
         Object var8 = null;
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }
   }

   public static final void loadSave_ProvinceData8() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "Provinces_Data8.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);
         int tID = 0;

         for (JsonValue jValue : tempArrayData) {
            ProvinceData8 tempData = json.readValue(ProvinceData8.class, jValue);
            Game.lProvincesData8.set(tID, tempData);
            tID++;
         }

         tempArrayData.clear();
         Object var8 = null;
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }
   }

   public static final void loadSave_ProvinceData9() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "Provinces_Data9.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);
         int tID = 0;

         for (JsonValue jValue : tempArrayData) {
            ProvinceData9 tempData = json.readValue(ProvinceData9.class, jValue);
            Game.lProvincesData9.set(tID, tempData);
            tID++;
         }

         tempArrayData.clear();
         Object var8 = null;
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }
   }

   public static final void loadSave_ProvinceData10() {
      try {
         FileHandle fileList = FileManager.loadFile("saves/" + Game.map.getFile_ActiveMap_Path() + key + "/" + "Provinces_Data10.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);
         int tID = 0;

         for (JsonValue jValue : tempArrayData) {
            ProvinceData10 tempData = json.readValue(ProvinceData10.class, jValue);
            Game.lProvincesData10.set(tID, tempData);
            tID++;
         }

         tempArrayData.clear();
         Object var8 = null;
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }
   }
}
