package aoc.kingdoms.lukasz.jakowski;

import aoc.kingdoms.lukasz.jakowski.AI.AI_Manager;
import aoc.kingdoms.lukasz.jakowski.AI.Values.AI_Values;
import aoc.kingdoms.lukasz.jakowski.AI.Values.AI_ValuesArmies;
import aoc.kingdoms.lukasz.jakowski.AI.Values.AI_ValuesBuild;
import aoc.kingdoms.lukasz.jakowski.AI.Values.AI_ValuesConvert;
import aoc.kingdoms.lukasz.jakowski.AI.Values.AI_ValuesCores;
import aoc.kingdoms.lukasz.jakowski.AI.Values.AI_ValuesDiplomacy;
import aoc.kingdoms.lukasz.jakowski.FlagsEditor.FlagManager;
import aoc.kingdoms.lukasz.jakowski.GameThreads.GameThread;
import aoc.kingdoms.lukasz.jakowski.GameThreads.GameThread_Events;
import aoc.kingdoms.lukasz.jakowski.GameThreads.GameThread_Turns;
import aoc.kingdoms.lukasz.jakowski.GameThreads.GameThread_Update;
import aoc.kingdoms.lukasz.jakowski.Player.Player;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.jakowski.Renderer.RendererGame;
import aoc.kingdoms.lukasz.jakowski.SaveLoad.LoadManager;
import aoc.kingdoms.lukasz.jakowski.SaveLoad.SaveManager;
import aoc.kingdoms.lukasz.jakowski.Stats.StatsManager;
import aoc.kingdoms.lukasz.jakowski.Steam.SteamAchievementsManager;
import aoc.kingdoms.lukasz.jakowski.setting.SettingsManager;
import aoc.kingdoms.lukasz.jakowski.zOther.Point_XY;
import aoc.kingdoms.lukasz.map.BuildingsManager;
import aoc.kingdoms.lukasz.map.GeneralManager;
import aoc.kingdoms.lukasz.map.IdeologiesManager;
import aoc.kingdoms.lukasz.map.ReligionManager;
import aoc.kingdoms.lukasz.map.WondersManager;
import aoc.kingdoms.lukasz.map.advisors.AdvisorManager;
import aoc.kingdoms.lukasz.map.allianceHRE.Alliance;
import aoc.kingdoms.lukasz.map.army.ArmyDivision_Shadow;
import aoc.kingdoms.lukasz.map.army.ArmyRecruit;
import aoc.kingdoms.lukasz.map.battles.BattleManager;
import aoc.kingdoms.lukasz.map.civilization.Civilization;
import aoc.kingdoms.lukasz.map.civilization.CivilizationBonuses;
import aoc.kingdoms.lukasz.map.civilization.CivilizationRegionsManager;
import aoc.kingdoms.lukasz.map.clouds.CloudsManager;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import aoc.kingdoms.lukasz.map.map.Continents;
import aoc.kingdoms.lukasz.map.map.GeographicalRegions;
import aoc.kingdoms.lukasz.map.map.Map;
import aoc.kingdoms.lukasz.map.map.MapBG;
import aoc.kingdoms.lukasz.map.map.MapCities;
import aoc.kingdoms.lukasz.map.map.MapCoords;
import aoc.kingdoms.lukasz.map.map.MapEdgeMove;
import aoc.kingdoms.lukasz.map.map.MapModeManager;
import aoc.kingdoms.lukasz.map.map.MapOver;
import aoc.kingdoms.lukasz.map.map.MapScale;
import aoc.kingdoms.lukasz.map.map.MapScenarios;
import aoc.kingdoms.lukasz.map.map.MapScroll;
import aoc.kingdoms.lukasz.map.map.MapTouchManager;
import aoc.kingdoms.lukasz.map.moveUnits.other.MoveUnits_RegroupLine;
import aoc.kingdoms.lukasz.map.province.Province;
import aoc.kingdoms.lukasz.map.province.ProvinceBorderManager;
import aoc.kingdoms.lukasz.map.province.ProvinceCut;
import aoc.kingdoms.lukasz.map.province.ProvinceHover;
import aoc.kingdoms.lukasz.map.province.animation.ProvinceAnimation;
import aoc.kingdoms.lukasz.map.province.animation.ProvinceAnimationHover;
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
import aoc.kingdoms.lukasz.map.rebels.RevolutionMoveUnits;
import aoc.kingdoms.lukasz.map.terrain.TerrainManager;
import aoc.kingdoms.lukasz.menu.HoverManager;
import aoc.kingdoms.lukasz.menu.MenuManager;
import aoc.kingdoms.lukasz.menu.View;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menus.Init_SelectLanguage;
import aoc.kingdoms.lukasz.menusInGame.InGame;
import aoc.kingdoms.lukasz.menusInGame.DrawOver.InGameDrawOver;
import aoc.kingdoms.lukasz.menusInGame.DrawOver.InGameDrawOver_InvasionArmy;
import aoc.kingdoms.lukasz.menusInGame.DrawOver.InGameDrawOver_RegroupArmy;
import aoc.kingdoms.lukasz.menusInGame.Info.InGame_Info;
import aoc.kingdoms.lukasz.menusInGame.Province.InGame_ProvinceArmy;
import aoc.kingdoms.lukasz.textures.Image;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Cursor.SystemCursor;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Game {
   public static int versionWidth = 0;
   public static Random oR = new Random();
   public static AI_Manager aiManager = new AI_Manager();
   public static AI_Values aiValues = new AI_Values();
   public static AI_ValuesBuild aiValuesBuild = new AI_ValuesBuild();
   public static AI_ValuesConvert aiValuesConvert = new AI_ValuesConvert();
   public static AI_ValuesCores aiValuesCores = new AI_ValuesCores();
   public static AI_ValuesArmies aiValuesArmies = new AI_ValuesArmies();
   public static AI_ValuesDiplomacy aiValuesDiplomacy = new AI_ValuesDiplomacy();
   public static Map map;
   public static MapBG mapBG;
   public static MapCoords mapCoords = new MapCoords();
   public static MapScale mapScale = new MapScale();
   public static MapEdgeMove mapEdgeMove = new MapEdgeMove();
   public static MapScroll mapScroll = new MapScroll();
   public static CloudsManager cloudsAnimation = new CloudsManager();
   public static MapCities mapCities = new MapCities();
   public static MapOver mapOver = new MapOver();
   public static MapOver mapOverSea = new MapOver();
   public static MapModeManager mapModes = new MapModeManager();
   public static MapTouchManager mapTouchManager = new MapTouchManager();
   public static TerrainManager terrainManager = new TerrainManager();
   public static Continents continents = new Continents();
   public static GeographicalRegions geographicalRegions = new GeographicalRegions();
   public static GeneralManager generalManager = new GeneralManager();
   public static AdvisorManager advisorManager = new AdvisorManager();
   public static MenuManager menuManager;
   public static HoverManager hoverManager;
   public static SoundsManager soundsManager;
   public static AmbienceManager ambienceManager;
   public static Keyboard keyboard = new Keyboard();
   public static AnimationManager animationManager;
   public static GameThread gameThread = new GameThread();
   public static GameThread_Update gameThreadUpdate = new GameThread_Update();
   public static GameThread_Turns gameThreadTurns = new GameThread_Turns();
   public static GameThread_Events gameThreadEvents = new GameThread_Events();
   public static IdeologiesManager ideologiesManager = new IdeologiesManager();
   public static ReligionManager religionManager = new ReligionManager();
   public static Game_Ages gameAges = new Game_Ages();
   public static FlagManager flagManager = new FlagManager();
   public static UnionManager_Flags unionManagerFlags = new UnionManager_Flags();
   public static StatsManager stats = new StatsManager();
   public static boolean FOG_OF_WAR = false;
   public static boolean SPECTATOR_MODE = false;
   public static boolean SANDBOX = false;
   public static boolean SCENARIO_EVENTS = true;
   public static boolean ENABLE_CALL_VASSALS = false;
   public static int HOURS_PER_TURN;
   public static boolean AGE_OF_CHAOS_MODE = false;
   public static int AGE_OF_CHAOS_YEARS = 20;
   public static int AGE_OF_CHAOS_CIVS = 4;
   public static List<Integer> AGE_OF_CHAOS_CIVS_LIST = new ArrayList<>();
   public static int scenarioID = 0;
   public static boolean reloadScenario = false;
   public static MapScenarios mapScenarios = new MapScenarios();
   public static boolean highTextureSettings = true;
   public static int aiAggressivnes = 0;
   public static int difficultyID = 0;
   public static float DRAW_CITIES_MIN_SCALE = 1.3F;
   public static float DRAW_CIV_NAMES_START_DRAWING_MAP_SCALE = 0.6F;
   public static float DRAW_INNER_BORDERS = 0.6F;
   public static float DRAW_OCCUPIED_PROVINCES_MIN_SCALE = 0.15F;
   public static float DRAW_ARMY_MIN_SCALE = 0.5F;
   public static float DRAW_OCCUPIED_SCALE = 2.0F;
   public static Game.FlagData flagData;
   public static List<Province> lProvinces = new ArrayList<>();
   public static int iProvincesSize = 0;
   public static List<ProvinceData> lProvincesData = new ArrayList<>();
   public static List<ProvinceData2> lProvincesData2 = new ArrayList<>();
   public static List<ProvinceData3> lProvincesData3 = new ArrayList<>();
   public static List<ProvinceData4> lProvincesData4 = new ArrayList<>();
   public static List<ProvinceData5> lProvincesData5 = new ArrayList<>();
   public static List<ProvinceData6> lProvincesData6 = new ArrayList<>();
   public static List<ProvinceData7> lProvincesData7 = new ArrayList<>();
   public static List<ProvinceData8> lProvincesData8 = new ArrayList<>();
   public static List<ProvinceData9> lProvincesData9 = new ArrayList<>();
   public static List<ProvinceData10> lProvincesData10 = new ArrayList<>();
   public static List<ProvinceData_Population> lProvincesPopulation = new ArrayList<>();
   public static List<Alliance> alliancesSpecial = new ArrayList<>();
   public static int alliancesSpecialSize = 0;
   public static List<Image> alliancesSpecial_Flag = new ArrayList<>();
   public static ArmyRecruit armyRecruit = new ArmyRecruit(0, 0, 0, null);
   public static GameActiveProvince gameActiveProvince = new GameActiveProvince();
   public static ProvinceAnimation activeProvince_Animation_Data = new ProvinceAnimation();
   public static ProvinceAnimation selectedProvinces_Animation_Data = new ProvinceAnimation();
   public static ProvinceAnimationHover animationHover = new ProvinceAnimationHover();
   public static int iActiveProvince = -1;
   public static int iHoveredProvinceID = -1;
   public static MenuElement_Hover provinceHover_Informations;
   public static int iOldActiveProvinceID = -1;
   public static int iOldHoveredProvinceID = 0;
   public static Game.HoveredArmy hoveredArmy = new Game.HoveredArmy();
   public static List<Game.HoveredArmy> activeArmy = new ArrayList<>();
   public static int activeArmySize = 0;
   public static int iHoveredCapitalProvinceID = -1;
   public static boolean invasionArmyMode = false;
   public static List<Integer> invasionArmyProvinces = new ArrayList<>();
   public static int invasionArmyProvincesSize = 0;
   public static boolean regroupArmyMode = false;
   public static List<Integer> regroupArmyProvinces = new ArrayList<>();
   public static List<ArmyDivision_Shadow> regroupArmyShadows = new ArrayList<>();
   public static int iRegroupArmyProvincesSize = 0;
   public static MoveUnits_RegroupLine regroupArmyLine = null;
   public static int hoveredShipID = -1;
   public static Game.HoveredBattle hoveredBattle = new Game.HoveredBattle();
   public static int hoveredSiegeID = -1;
   public static boolean chooseProvinceMode = false;
   public static int MAX_BELOW_ZERO_POINT_X = 0;
   public static List<ProvinceCut> cutProvinces = null;
   public static RegionManager regions = new RegionManager();
   public static boolean updateProvincesInView = true;
   public static List<Integer> lProvincesInView = new ArrayList<>();
   public static List<Integer> lSeaProvincesInView = new ArrayList<>();
   public static List<Integer> lWastelandProvincesInView = new ArrayList<>();
   public static List<Integer> lExtraProvincesInView = new ArrayList<>();
   public static int NUM_OF_PROVINCES_IN_VIEW = 0;
   public static int NUM_OF_EXTRA_PROVINCES_IN_VIEW = 0;
   public static int NUM_OF_SEA_PROVINCES_IN_VIEW = 0;
   public static int NUM_OF_WASTELAND_PROVINCES_IN_VIEW = 0;
   public static List<Civilization> lCivs = new ArrayList<>();
   public static int iCivsSize = 0;
   public static BattleManager battleManager;
   public static RevolutionManager revolutionManager = new RevolutionManager();
   public static RevolutionMoveUnits revolutionMoveUnits = new RevolutionMoveUnits();
   public static Player player = new Player();
   public static SettingsManager settingsManager;
   public static LanguageManager lang;
   public static float deltaTime = Gdx.graphics.getDeltaTime();
   private static ConcurrentLinkedDeque<Game.SimpleTask> simpleTasks = new ConcurrentLinkedDeque<>();
   public static List<Cursor> lCursors = new ArrayList<>();
   public static int activeCursorID = -1;
   public static int maxWastelandLvl = 99;
   public static int hoveredProvinceBG_LoadedID = -1;
   public static Image hoveredProvinceBG = null;
   private static final float HIGHLIGHTED_PROVINCES_ANIMATION_TIME = 750.0F;
   private static final float HIGHLIGHTED_PROVINCES_ANIMATION_TIME_BACK = 350.0F;
   public static float fDashedLine_Percentage_HighlitedProvinceBorder = 0.0F;
   public static long lDashedLineTime_Percentage_HighlitedProvinceBorder;
   public static boolean highlightedProvinceBorder_BackAnimation = false;
   public static boolean highlightedProvinceBorder_Update = false;
   public static int inViewY_CordsY = 0;
   public static int inViewY_CordsY_Height = 0;
   public static int inViewX_CordsX = 0;
   public static int inViewX_CordsX_Width = 0;
   public static float iMaxDistance = 1.0F;
   public static float iMaxDistanceManhattan = 1.0F;
   public static Game.MapDistance mapDistance = new Game.MapDistance() {
      @Override
      public float getManhattanDistance(int provA, int provB) {
         return 0.0F;
      }

      @Override
      public float getDistanceFromProvinceToProvince(int provA, int provB) {
         return 0.0F;
      }
   };

   public static void loadAiValues() {
      aiValuesBuild.BUILD_SCORE_CAPITAL_TOTAL = 0;

      for (int i = aiValuesBuild.BUILD_SCORE_CAPITAL.length - 1; i >= 0; i--) {
         aiValuesBuild.BUILD_SCORE_CAPITAL_TOTAL = aiValuesBuild.BUILD_SCORE_CAPITAL_TOTAL + aiValuesBuild.BUILD_SCORE_CAPITAL[i];
      }
   }

   public static final void updateDaultScenarioID() {
      for (int i = 0; i < mapScenarios.SCENARIOS_SIZE; i++) {
         if (mapScenarios.lScenarios_TagsList.get(i).equals(map.lMaps.get(map.getActiveMapID()).mapData.DefaultScenario)) {
            scenarioID = i;
            mapScenarios.sActiveScenarioTag = mapScenarios.lScenarios_TagsList.get(i);
            break;
         }
      }
   }

   public static boolean highTextueSettings() {
      return highTextureSettings;
   }

   public static void loadLowSettings() {
      if (CFG.isDesktop()) {
         try {
            if (FileManager.loadFile("settings/LoadTexturesHigh.txt").exists()) {
               FileHandle tempFileT2 = FileManager.loadFile("settings/LoadTexturesHigh.txt");
               highTextureSettings = Boolean.parseBoolean(tempFileT2.readString());
            }
         } catch (Exception var1) {
            CFG.exceptionStack(var1);
         }
      } else {
         highTextureSettings = true;
      }
   }

   public static final void saveTextueSettings() {
      FileHandle fileSave;
      if (FileManager.IS_MAC) {
         fileSave = Gdx.files.external("settings/LoadTexturesHigh.txt");
      } else {
         fileSave = Gdx.files.local("settings/LoadTexturesHigh.txt");
      }

      fileSave.writeString("" + highTextureSettings, false);
   }

   public static final void initDifficulty() {
      if (!SPECTATOR_MODE && Game_Calendar.TURN_ID == 1) {
         int turnID = Game_Calendar.TURN_ID + GameValues.difficulty.BONUS_DURATION;
         CivilizationBonuses bonusDifficulty = new CivilizationBonuses(turnID);
         bonusDifficulty.IncomeProduction = GameValues.difficulty.INCOME_PRODUCTION[difficultyID];
         bonusDifficulty.MonthlyIncome = GameValues.difficulty.MONTHLY_INCOME[difficultyID];
         bonusDifficulty.MonthlyLegacy_Percentage = GameValues.difficulty.LEGACY[difficultyID];
         bonusDifficulty.MaxManpower_Percentage = GameValues.difficulty.MANPOWER[difficultyID];
         bonusDifficulty.RecruitArmyCost = GameValues.difficulty.RECRUIT_ARMY_COST[difficultyID];
         bonusDifficulty.RecruitmentTime = GameValues.difficulty.RECRUIT_ARMY_TIME[difficultyID];
         bonusDifficulty.RegimentsLimit = GameValues.difficulty.REGIMENTS_LIMIT[difficultyID];
         bonusDifficulty.ConstructionCost = GameValues.difficulty.CONSTRUCTION_COST[difficultyID];
         bonusDifficulty.CoreCost = GameValues.difficulty.CORE_COST[difficultyID];
         bonusDifficulty.ReligionCost = GameValues.difficulty.RELIGION_COST[difficultyID];
         getCiv(player.iCivID).addCivilizationBonus_Temporary(bonusDifficulty);
      }
   }

   public static final void clearAllianceSpecial() {
      try {
         alliancesSpecial.clear();
         alliancesSpecialSize = 0;
      } catch (Exception var1) {
         CFG.exceptionStack(var1);
      }

      try {
         for (int i = alliancesSpecial_Flag.size() - 1; i >= 0; i--) {
            alliancesSpecial_Flag.get(i).dispose();
            alliancesSpecial_Flag.set(i, null);
         }

         alliancesSpecial_Flag.clear();
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   public static int getAllianceSpecialNumOfCivs(int allianceID) {
      int out = 0;
      if (getCiv(alliancesSpecial.get(allianceID).iLeaderCivID).getNumOfProvinces() > 0
         && !alliancesSpecial.get(allianceID).firstTier.contains(alliancesSpecial.get(allianceID).iLeaderCivID)
         && !alliancesSpecial.get(allianceID).secondTier.contains(alliancesSpecial.get(allianceID).iLeaderCivID)) {
         out++;
      }

      try {
         for (int i = alliancesSpecial.get(allianceID).firstTier.size() - 1; i >= 0; i--) {
            if (getCiv(alliancesSpecial.get(allianceID).firstTier.get(i)).getNumOfProvinces() > 0) {
               out++;
            }
         }

         for (int ix = alliancesSpecial.get(allianceID).secondTier.size() - 1; ix >= 0; ix--) {
            if (getCiv(alliancesSpecial.get(allianceID).secondTier.get(ix)).getNumOfProvinces() > 0) {
               out++;
            }
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }

      return out;
   }

   public static void addAllianceSpecial(Alliance nAlliance) {
      for (int i = 0; i < alliancesSpecial.size(); i++) {
         if (alliancesSpecial.get(i).Name_Alliance.equals(nAlliance.Name_Alliance)) {
            alliancesSpecial.set(i, nAlliance);
            return;
         }
      }

      alliancesSpecial.add(nAlliance);
   }

   public static void removeAllianceSpecial(String tag) {
      for (int i = 0; i < alliancesSpecial.size(); i++) {
         if (alliancesSpecial.get(i).Name_Alliance.equals(tag)) {
            alliancesSpecial.remove(i);
            return;
         }
      }
   }

   public static void removeAllianceSpecial(int id) {
      try {
         alliancesSpecial.remove(id);
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   public static void loadAlliancesSpecial_Images() {
      for (int i = 0; i < alliancesSpecial.size(); i++) {
         if (FileManager.loadFile("gfx/flagsXH/" + alliancesSpecial.get(i).FlagTag + ".png").exists()) {
            alliancesSpecial_Flag.add(
               new Image(new Texture(FileManager.loadFile("gfx/flagsXH/" + alliancesSpecial.get(i).FlagTag + ".png")), TextureFilter.Linear)
            );
         } else if (FileManager.loadFile("gfx/flagsH/" + alliancesSpecial.get(i).FlagTag + ".png").exists()) {
            alliancesSpecial_Flag.add(
               new Image(new Texture(FileManager.loadFile("gfx/flagsH/" + alliancesSpecial.get(i).FlagTag + ".png")), TextureFilter.Linear)
            );
         } else if (FileManager.loadFile("gfx/flags/" + alliancesSpecial.get(i).FlagTag + ".png").exists()) {
            alliancesSpecial_Flag.add(
               new Image(new Texture(FileManager.loadFile("gfx/flags/" + alliancesSpecial.get(i).FlagTag + ".png")), TextureFilter.Nearest)
            );
         } else {
            alliancesSpecial_Flag.add(new Image(new Texture(FileManager.loadFile("gfx/flagsXH/ran.png")), TextureFilter.Nearest));
         }
      }
   }

   public static void setActiveProvinceID(int nActiveProvince) {
      iActiveProvince = nActiveProvince;
      if (iHoveredProvinceID == iActiveProvince) {
         if (MapCities.lTIME_HOVERED_CITIES > CFG.currentTimeMillis - 2000L) {
            MapCities.lTIME_ACTIVE_CITIES = CFG.currentTimeMillis
               - (long)(550.0F * ((float)(CFG.currentTimeMillis - MapCities.lTIME_HOVERED_CITIES) / 2000.0F));
         } else {
            MapCities.lTIME_ACTIVE_CITIES = 0L;
         }
      } else {
         MapCities.lTIME_ACTIVE_CITIES = CFG.currentTimeMillis;
      }
   }

   public static void setHoveredProvinceID(int nHoveredProvinceID) {
      if (iOldHoveredProvinceID != iHoveredProvinceID && nHoveredProvinceID >= 0) {
         iOldHoveredProvinceID = iHoveredProvinceID;
         if (mapScale.getCurrentScale() >= DRAW_INNER_BORDERS) {
            soundsManager.playHover();
         }
      }

      iHoveredProvinceID = nHoveredProvinceID;
      MapCities.lTIME_HOVERED_CITIES = CFG.currentTimeMillis;
   }

   public static final float getInvestEconomyGrowth(int iProvinceID) {
      return GameValues.economy.INVEST_ECONOMY_GROWTH;
   }

   public static float getInvestCost(int nProvinceID) {
      Province province = getProvince(nProvinceID);

      try {
         if (province.iProvinceInvestSize > 0) {
            return Math.max(
               1.0F,
               (
                     GameValues.economy.INVEST_COST
                        + (
                              province.getEconomyWithBonuses()
                                 + getInvestEconomyGrowth(nProvinceID)
                                    * province.provinceInvestDaysLeft.get(0).daysLeft
                                    / province.provinceInvestDaysLeft.get(0).investTime
                                 + getInvestEconomyGrowth(nProvinceID) * (province.iProvinceInvestSize - 1)
                           )
                           * GameValues.economy.INVEST_COST_PER_ECONOMY
                  )
                  * (
                     1.0F
                        + province.provBonuses.InvestInEconomyCost
                        + province.getInfrastructure() * GameValues.infrastructure.INFRASTRUCTURE_INVEST_COST_PER_LVL
                        + getCiv(province.getCivID()).getInflation()
                        + getCiv(province.getCivID()).civBonuses.InvestInEconomyCost
                        + GameValues.civRank.CIV_RANK_INVEST_ECONOMY_COST[getCiv(province.getCivID()).iCivRankID]
                  )
            );
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }

      return Math.max(
         1.0F,
         (GameValues.economy.INVEST_COST + province.getEconomyWithBonuses() * GameValues.economy.INVEST_COST_PER_ECONOMY)
            * (
               1.0F
                  + province.provBonuses.InvestInEconomyCost
                  + province.getInfrastructure() * GameValues.infrastructure.INFRASTRUCTURE_INVEST_COST_PER_LVL
                  + getCiv(province.getCivID()).getInflation()
                  + getCiv(province.getCivID()).civBonuses.InvestInEconomyCost
                  + GameValues.civRank.CIV_RANK_INVEST_ECONOMY_COST[getCiv(province.getCivID()).iCivRankID]
            )
      );
   }

   public static float getInvestCost_Legacy(int nProvinceID) {
      Province province = getProvince(nProvinceID);

      try {
         if (province.iProvinceInvestSize > 0) {
            return Math.max(
               1.0F,
               (
                     GameValues.economy.INVEST_COST_LEGACY
                        + (
                              province.getEconomyWithBonuses()
                                 + getInvestEconomyGrowth(nProvinceID)
                                    * province.provinceInvestDaysLeft.get(0).daysLeft
                                    / province.provinceInvestDaysLeft.get(0).investTime
                                 + getInvestEconomyGrowth(nProvinceID) * (province.iProvinceInvestSize - 1)
                           )
                           * GameValues.economy.INVEST_COST_LEGACY_PER_ECONOMY
                  )
                  * (
                     1.0F
                        + province.provBonuses.InvestInEconomyCost
                        + province.getInfrastructure() * GameValues.infrastructure.INFRASTRUCTURE_INVEST_COST_PER_LVL
                        + getCiv(province.getCivID()).getInflation()
                        + getCiv(province.getCivID()).civBonuses.InvestInEconomyCost
                        + GameValues.civRank.CIV_RANK_INVEST_ECONOMY_COST[getCiv(province.getCivID()).iCivRankID]
                  )
            );
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }

      return Math.max(
         1.0F,
         (GameValues.economy.INVEST_COST_LEGACY + province.getEconomyWithBonuses() * GameValues.economy.INVEST_COST_LEGACY_PER_ECONOMY)
            * (
               1.0F
                  + province.provBonuses.InvestInEconomyCost
                  + province.getInfrastructure() * GameValues.infrastructure.INFRASTRUCTURE_INVEST_COST_PER_LVL
                  + getCiv(province.getCivID()).getInflation()
                  + getCiv(province.getCivID()).civBonuses.InvestInEconomyCost
                  + GameValues.civRank.CIV_RANK_INVEST_ECONOMY_COST[getCiv(province.getCivID()).iCivRankID]
            )
      );
   }

   public static int getInvestTime(int nCivID) {
      return GameValues.economy.INVEST_IN_PROVINCE_DAYS;
   }

   public static float getMaxEconomy(int nProvinceID) {
      return getProvince(nProvinceID).getGrowthRateWithBonuses() * GameValues.economy.MAX_ECONOMY_GROWTH_RATE;
   }

   public static boolean canInvestInEconomy(int nProvinceID) {
      return getProvince(nProvinceID).getEconomyWithBonuses() + getProvince(nProvinceID).iProvinceInvestSize * getInvestEconomyGrowth(nProvinceID)
         > getMaxEconomy(nProvinceID);
   }

   public static final float getExploitEconomy_Lose(int iProvinceID) {
      return Math.min(
         GameValues.economy.EXPLOIT_ECONOMY,
         getProvince(iProvinceID).getEconomy() > GameValues.economy.EXPLOIT_ECONOMY_MIN_ECONOMY
            ? getProvince(iProvinceID).getEconomy() - GameValues.economy.EXPLOIT_ECONOMY_MIN_ECONOMY
            : 0.0F
      );
   }

   public static final float getExploitEconomy_Gain(int iProvinceID) {
      return getInvestCost(iProvinceID)
         * getExploitEconomy_Lose(iProvinceID)
         / GameValues.economy.INVEST_ECONOMY_GROWTH
         * GameValues.economy.EXPLOIT_ECONOMY_GAIN_PER_INVEST_COST;
   }

   public static final boolean exploitEconomy(int iProvinceID) {
      if (getProvince(iProvinceID).getEconomy() > GameValues.economy.EXPLOIT_ECONOMY_MIN_ECONOMY) {
         getCiv(getProvince(iProvinceID).getCivID()).addGold(getExploitEconomy_Gain(iProvinceID));
         getProvince(iProvinceID).setEconomy(getProvince(iProvinceID).getEconomy() - getExploitEconomy_Lose(iProvinceID));
         getProvince(iProvinceID).updateAfterEconomyChange();
         getCiv(getProvince(iProvinceID).getCivID()).updateTotalIncomePerMonth();
         return true;
      } else {
         return false;
      }
   }

   public static float getLootValue(int iProvinceID) {
      return getProvince(iProvinceID).getProvinceIncome() * GameValues.siege.LOOT_PROVINCE_PER_PROVINCE_INCOME * getProvince(iProvinceID).getLoot();
   }

   public static final float getDevelopInfrastructureCost(int nProvinceID) {
      Province province = getProvince(nProvinceID);
      return Math.max(
         1.0F,
         (
               GameValues.infrastructure.DEVELOP_INFRASTRUCTURE_COST_GOLD
                  + (province.getInfrastructure() + province.iProvinceDevelopInfrastructureSize)
                     * GameValues.infrastructure.DEVELOP_INFRASTRUCTURE_COST_GOLD_PER_INFRASTRUCTURE
            )
            * (1.0F + province.provBonuses.DevelopInfrastructureCost + getCiv(province.getCivID()).civBonuses.DevelopInfrastructureCost)
      );
   }

   public static final float getDevelopInfrastructureCostLegacy(int nProvinceID) {
      Province province = getProvince(nProvinceID);
      return Math.max(
         1.0F,
         (
               GameValues.infrastructure.DEVELOP_INFRASTRUCTURE_COST_LEGACY
                  + (province.getInfrastructure() + province.iProvinceDevelopInfrastructureSize)
                     * GameValues.infrastructure.DEVELOP_INFRASTRUCTURE_COST_LEGACY_PER_INFRASTRUCTURE
            )
            * (1.0F + province.provBonuses.DevelopInfrastructureCost + getCiv(province.getCivID()).civBonuses.DevelopInfrastructureCost)
      );
   }

   public static final int getDevelopInfrastructureTime(int nProvinceID) {
      Province province = getProvince(nProvinceID);
      return Math.max(
         1,
         GameValues.infrastructure.DEVELOP_INFRASTRUCTURE_TIME
            + (province.getInfrastructure() + province.iProvinceDevelopInfrastructureSize)
               * GameValues.infrastructure.DEVELOP_INFRASTRUCTURE_TIME_PER_INFRASTRUCTURE
      );
   }

   public static float getProvinceMaintenanceEconomy(int iCivID, int iProvinceID) {
      return GameValues.province.PROVINCE_MAINTENANCE_PER_ECONOMY * getProvince(iProvinceID).getEconomyWithBonuses();
   }

   public static float getProvinceMaintenanceTax(int iCivID, int iProvinceID) {
      return GameValues.province.PROVINCE_MAINTENANCE_PER_TAX * getProvince(iProvinceID).getEconomyWithBonuses();
   }

   public static float getProvinceMaintenanceManpower(int iCivID, int iProvinceID) {
      return GameValues.province.PROVINCE_MAINTENANCE_PER_MANPOWER * getProvince(iProvinceID).getEconomyWithBonuses();
   }

   public static float getDevastationOccupiedProvince(int iCivID) {
      return GameValues.siege.DEVASTATION_LOOTED * (1.0F + getCiv(iCivID).civBonuses.Devastation);
   }

   public static float getIncomeFromVassal(int iCivID, int iVassalCivID) {
      return getIncomeFromVassal(iCivID, iVassalCivID, getCiv(iCivID).diplomacy.getVassal_TributeLevel(iVassalCivID));
   }

   public static float getIncomeFromVassal(int iCivID, int iVassalCivID, int iLevel) {
      return getCiv(iVassalCivID).fTotalIncomePerMonth * GameValues.vassal.VASSAL_INCOME_TO_LORD[iLevel] * (1.0F + getCiv(iCivID).civBonuses.IncomeFromVassals);
   }

   public static double getManpowerFromVassal_INFO(int iCivID, int iVassalCivID, int iLevel) {
      return (getCiv(iVassalCivID).fManpowerMax + getCiv(iVassalCivID).fManpowerMax_ToLord - GameValues.manpower.MANPOWER_MAX_BASE)
         * GameValues.vassal.VASSAL_MANPOWER_TO_LORD[iLevel];
   }

   public static float getIncomeFromEconomy(int iProvinceID) {
      Province province = getProvince(iProvinceID);
      Civilization civ = getCiv(province.getCivID());
      return province.getEconomyWithBonuses()
         * GameValues.economy.INCOME_ECONOMY_PER_ECONOMY
         * Math.max(
            0.01F,
            1.0F
               + GameValues.budget.TAXATION_LEVEL_INCOME_ECONOMY[civ.getTaxationLevel()]
               + civ.fProsperity_AverageEconomy / 100.0F * GameValues.prosperity.PROSPERITY_INCOME
               + civ.civBonuses.IncomeEconomy
               - province.getDevastation()
               - civ.getCorruption()
               + (province.getReligion() == civ.getReligionID() ? 0.0F : GameValues.religion.BASE_INCOME_DIFFERENT_RELIGION)
               + (province.haveACore ? 0.0F : GameValues.core.BASE_INCOME_NON_CORE)
         );
   }

   public static float getIncomeFromEconomy_Invest(int iProvinceID) {
      Province province = getProvince(iProvinceID);
      Civilization civ = getCiv(province.getCivID());
      return getInvestEconomyGrowth(iProvinceID)
         * GameValues.economy.INCOME_ECONOMY_PER_ECONOMY
         * Math.max(
            0.01F,
            1.0F
               + GameValues.budget.TAXATION_LEVEL_INCOME_ECONOMY[civ.getTaxationLevel()]
               + civ.fProsperity_AverageEconomy / 100.0F * GameValues.prosperity.PROSPERITY_INCOME
               + civ.civBonuses.IncomeEconomy
               - province.getDevastation()
               - civ.getCorruption()
               + (province.getReligion() == civ.getReligionID() ? 0.0F : GameValues.religion.BASE_INCOME_DIFFERENT_RELIGION)
               + (province.haveACore ? 0.0F : GameValues.core.BASE_INCOME_NON_CORE)
         );
   }

   public static int getBuildingConstructionCost(int iCivID, int iProvinceID, int building, int buildingID) {
      Province province = iProvinceID >= 0 ? getProvince(iProvinceID) : null;
      return Math.max(
         1,
         (int)Math.ceil(
            (
                  BuildingsManager.buildings.get(building).CostGold[buildingID]
                     + (
                        BuildingsManager.buildings.get(building).ResearchPoints != null
                           ? GameValues.research.RESEARCH_BUILDING_COST_PER_RESEARCH_POINT * getCiv(iCivID).fResearchPerMonth
                           : 0.0F
                     )
               )
               * (
                  iProvinceID >= 0
                     ? terrainManager.terrains.get(province.getTerrainID()).BuildCost
                        + getCiv(iCivID).getInflation()
                        + GameValues.infrastructure.INFRASTRUCTURE_CONSTRUCTION_COST_PER_LVL * province.getInfrastructure()
                        + province.provBonuses.ConstructionCost
                        + getCiv(iCivID).civBonuses.ConstructionCost
                        + getBuildingGroupCost(iCivID, building)
                     : 1.0F
               )
         )
      );
   }

   public static int getBuildingConstructionTime(int iCivID, int iProvinceID, int building, int buildingID) {
      Province province = iProvinceID >= 0 ? getProvince(iProvinceID) : null;
      return Math.max(
         1,
         (int)Math.ceil(
            BuildingsManager.buildings.get(building).ConstructionTime[buildingID]
               * (
                  1.0F
                     + (
                        iProvinceID >= 0
                           ? province.getInfrastructure() * GameValues.infrastructure.INFRASTRUCTURE_CONSTRUCTION_TIME_PER_LVL
                              + province.provBonuses.ConstructionTimeBonus
                           : 0.0F
                     )
                     + getCiv(iCivID).civBonuses.ConstructionTime
               )
         )
      );
   }

   public static float getBuildingGroupCost(int iCivID, int building) {
      switch (BuildingsManager.buildings.get(building).GroupID) {
         case 0:
            return getCiv(iCivID).civBonuses.AdministrationBuildingsCost;
         case 1:
            return getCiv(iCivID).civBonuses.MilitaryBuildingsCost;
         case 2:
            return getCiv(iCivID).civBonuses.EconomyBuildingsCost;
         default:
            return 0.0F;
      }
   }

   public static float getCoreCreationCost(int iProvinceID) {
      Province province = getProvince(iProvinceID);
      Civilization civ = getCiv(province.getCivID());
      return Math.max(
         1.0F,
         (
               GameValues.core.CORE_CREATION_COST_DEFAULT
                  + province.getEconomyWithBonuses() * GameValues.core.CORE_CREATION_COST_PER_ECONOMY
                  + province.getInfrastructure() * GameValues.infrastructure.INFRASTRUCTURE_CORE_COST_PER_LVL
            )
            * (1.0F + civ.getInflation() + civ.getWarWeariness() * GameValues.warWeariness.WW_CORE_COST_PER_POINT + civ.civBonuses.CoreCost / 100.0F)
      );
   }

   public static int getCoreCreationTime(int iProvinceID) {
      return Math.max(
         1,
         (int)(
            GameValues.core.CORE_CREATION_TIME_DEFAULT
               + getProvince(iProvinceID).getTaxEfficiencyWithBonuses() * GameValues.core.CORE_CREATION_TIME_PER_TAX_EFFICIENCY
         )
      );
   }

   public static float getProvinceTaxEfficiency(int iProvinceID) {
      return Math.max(0.1F, getProvince(iProvinceID).getTaxEfficiencyWithBonuses())
         * (1.0F + GameValues.budget.TAXATION_LEVEL_EFFICIENCY[getCiv(getProvince(iProvinceID).getCivID()).getTaxationLevel()]);
   }

   public static float getIncomePopulationTaxation(int iProvinceID) {
      return getIncomePopulationTaxation(iProvinceID, getProvinceTaxEfficiency(iProvinceID));
   }

   public static float getIncomePopulationTaxation(int iProvinceID, float taxEfficiency) {
      Province province = getProvince(iProvinceID);
      Civilization civ = getCiv(province.getCivID());
      return GameValues.tax.BASE_INCOME_POPULATION_INCOME
         + Math.min(province.getPopulationTotal(), GameValues.tax.TAX_EFFICIENCY_MAX_POPULATION)
            / GameValues.tax.TAX_EFFICIENCY_POPULATION_DIVIDE
            * (taxEfficiency / 100.0F)
            * Math.max(
               0.01F,
               1.0F
                  + civ.civBonuses.IncomeTaxation
                  - province.getDevastation()
                  - civ.getCorruption()
                  + (province.getReligion() == civ.getReligionID() ? 0.0F : GameValues.religion.BASE_INCOME_DIFFERENT_RELIGION)
                  + (province.haveACore ? 0.0F : GameValues.core.BASE_INCOME_NON_CORE)
            );
   }

   public static float getIncomePopulationTaxation_Invest(int iProvinceID) {
      return getIncomePopulationTaxation(iProvinceID, getProvinceTaxEfficiency(iProvinceID) + GameValues.tax.INCREASE_TAX_EFFICIENCY_GROWTH)
         - getIncomePopulationTaxation(iProvinceID, getProvinceTaxEfficiency(iProvinceID));
   }

   public static final int getManpowerMaxFromProvinceManpowerLvl(int iProvinceID) {
      Province province = getProvince(iProvinceID);
      return (int)(
         province.getManpower()
            * GameValues.manpower.MANPOWER_MAX_PER_PROVINCE_MANPOWER_LVL
            * (Math.min(province.getGrowthRateWithBonuses(), (float)GameValues.manpower.MANPOWER_MAX_PER_PROVINCE_MAX_GROWTH_RATE) / 100.0F)
            * (
               1.0F
                  + province.provBonuses.LocalManpower
                  + (province.getReligion() != getCiv(province.getCivID()).getReligionID() ? GameValues.manpower.MANPOWER_MAX_DIFFERENT_RELIGION : 0.0F)
                  + (province.haveACore(province.getCivID()) ? 0.0F : GameValues.manpower.MANPOWER_MAX_NON_CORE)
            )
      );
   }

   public static final float getManpowerFromProvincePerMonth_Info(int iProvinceID) {
      return getManpowerMaxFromProvinceManpowerLvl(iProvinceID) / GameValues.manpower.MANPOWER_FULL_RECOVERY_MONTHS;
   }

   public static int getIncreseManpower_MaximumManpowerInfo(int iProvinceID) {
      Province province = getProvince(iProvinceID);
      return (int)(
         GameValues.manpower.MANPOWER_MAX_PER_PROVINCE_MANPOWER_LVL
            * GameValues.manpower.INCREASE_MANPOWER_GROWTH
            * (Math.min(province.getGrowthRateWithBonuses(), (float)GameValues.manpower.MANPOWER_MAX_PER_PROVINCE_MAX_GROWTH_RATE) / 100.0F)
            * (
               1.0F
                  + province.provBonuses.LocalManpower
                  + (province.getReligion() != getCiv(province.getCivID()).getReligionID() ? GameValues.manpower.MANPOWER_MAX_DIFFERENT_RELIGION : 0.0F)
                  + (province.haveACore(province.getCivID()) ? 0.0F : GameValues.manpower.MANPOWER_MAX_NON_CORE)
            )
      );
   }

   public static float getIncreseManpower_ManpowerPerMonthInfo(int nProvinceID) {
      return getIncreseManpower_MaximumManpowerInfo(nProvinceID) / GameValues.manpower.MANPOWER_FULL_RECOVERY_MONTHS;
   }

   public static final double getManpowerPerMonth(int iCivID, double iManpowerMax) {
      return iManpowerMax / GameValues.manpower.MANPOWER_FULL_RECOVERY_MONTHS * (1.0F + getCiv(iCivID).civBonuses.ManpowerRecoverySpeed);
   }

   public static final float getManpowerRecoveryFromADisbandedArmy(int iCivID) {
      return Math.min(1.0F, GameValues.manpower.MANPOWER_RECOVERY_FROM_DISBANDED_ARMY_DEFAULT + getCiv(iCivID).civBonuses.ManpowerRecoveryFromADisbandedArmy);
   }

   public static final int getMilitaryAcademyForGenerals_MaxLvl(int iCivID) {
      return GameValues.militaryAcademy.MILITARY_ACADEMY_FOR_GENERALS_MAX_LVL + getCiv(iCivID).civBonuses.MaximumLevelOfTheMilitaryAcademyForGenerals;
   }

   public static final int getMilitaryAcademyForGenerals_GeneralAttack(int iCivID) {
      return GameValues.militaryAcademy.MILITARY_ACADEMY_FOR_GENERALS_ATTACK_PER_LVL * getCiv(iCivID).getMilitaryAcademyForGeneralsLevel();
   }

   public static final int getMilitaryAcademyForGenerals_GeneralDefense(int iCivID) {
      return GameValues.militaryAcademy.MILITARY_ACADEMY_FOR_GENERALS_DEFENSE_PER_LVL * getCiv(iCivID).getMilitaryAcademyForGeneralsLevel();
   }

   public static final float getMilitaryAcademyForGenerals_Cost(int iCivID) {
      return GameValues.militaryAcademy.MILITARY_ACADEMY_FOR_GENERALS_COST
         + GameValues.militaryAcademy.MILITARY_ACADEMY_FOR_GENERALS_COST_PER_LVL * getCiv(iCivID).getMilitaryAcademyForGeneralsLevel();
   }

   public static final float getMilitaryAcademyForGenerals_MaintenanceCost(int iCivID) {
      return GameValues.militaryAcademy.MILITARY_ACADEMY_FOR_GENERALS_MAINTENANCE_COST_PER_LVL * getCiv(iCivID).getMilitaryAcademyForGeneralsLevel();
   }

   public static final int getMilitaryAcademy_MaxLvl(int iCivID) {
      return GameValues.militaryAcademy.MILITARY_ACADEMY_MAX_LVL + getCiv(iCivID).civBonuses.MaximumLevelOfTheMilitaryAcademy;
   }

   public static final int getMilitaryAcademy_Attack(int iCivID) {
      return GameValues.militaryAcademy.MILITARY_ACADEMY_ATTACK_PER_LVL * getCiv(iCivID).getMilitaryAcademyLevel();
   }

   public static final int getMilitaryAcademy_Defense(int iCivID) {
      return GameValues.militaryAcademy.MILITARY_ACADEMY_DEFENSE_PER_LVL * getCiv(iCivID).getMilitaryAcademyLevel();
   }

   public static final int getMilitaryAcademy_RegimentsLimit(int iCivID) {
      return GameValues.militaryAcademy.MILITARY_ACADEMY_REGIMENTS_LIMIT_PER_LVL * getCiv(iCivID).getMilitaryAcademyLevel();
   }

   public static final float getMilitaryAcademy_Cost(int iCivID) {
      return GameValues.militaryAcademy.MILITARY_ACADEMY_COST
         + GameValues.militaryAcademy.MILITARY_ACADEMY_COST_PER_LVL * getCiv(iCivID).getMilitaryAcademyLevel();
   }

   public static final float getMilitaryAcademy_MaintenanceCost(int iCivID) {
      return GameValues.militaryAcademy.MILITARY_ACADEMY_MAINTENANCE_COST_PER_LVL * getCiv(iCivID).getMilitaryAcademyLevel();
   }

   public static final int getSupremeCourt_MaxLvl(int iCivID) {
      return GameValues.supremeCourt.SUPREME_COURT_MAX_LVL + getCiv(iCivID).civBonuses.MaximumLevelOfTheSupremeCourt;
   }

   public static final float getSupremeCourt_Corruption(int iCivID) {
      return GameValues.supremeCourt.SUPREME_COURT_CORRUPTION_REDUCTION_PER_LVL * getCiv(iCivID).getSupremeCourtLevel();
   }

   public static final float getSupremeCourt_Cost(int iCivID) {
      return GameValues.supremeCourt.SUPREME_COURT_COST + GameValues.supremeCourt.SUPREME_COURT_COST_PER_LVL * getCiv(iCivID).getSupremeCourtLevel();
   }

   public static final int getCapital_MaxLvl(int iCivID) {
      return GameValues.capital.CAPITAL_MAX_LVL + getCiv(iCivID).civBonuses.MaximumLevelOfCapitalCity;
   }

   public static final float getCapital_Income(int iCivID) {
      return GameValues.capital.CAPITAL_INCOME_PER_LVL * getCiv(iCivID).getCapitalLevel();
   }

   public static final float getCapital_ProvincesMaintenance(int iCivID) {
      return GameValues.capital.CAPITAL_PROVINCES_MAINTENANCE_COST_PER_LVL * getCiv(iCivID).getCapitalLevel();
   }

   public static final float getCapital_MaxResearch(int iCivID) {
      return GameValues.capital.CAPITAL_MAX_RESEARCH_PER_LVL * getCiv(iCivID).getCapitalLevel();
   }

   public static final float getCapital_Cost(int iCivID) {
      return GameValues.capital.CAPITAL_COST + GameValues.capital.CAPITAL_COST_PER_LVL * getCiv(iCivID).getCapitalLevel();
   }

   public static final float getAtomicBombCasualties(int iProvinceID) {
      return GameValues.atomic.ATOMIC_BOMB_POPULATION_CASUALTIES * Math.max(0.01F, 1.0F + getProvince(iProvinceID).provBonuses.CasualtiesNuclearAttacks);
   }

   public static final int dropAtomicBomb(int iCivID, int iProvinceID) {
      if (getCiv(iCivID).getNukes() > 0 && DiplomacyManager.isAtWar(iCivID, getProvince(iProvinceID).getCivID())) {
         getCiv(iCivID).setNukes(getCiv(iCivID).getNukes() - 1);
         int iCasualties = getProvince(iProvinceID).atomicBombDropped();
         if (iCivID == player.iCivID || getProvince(iProvinceID).getCivID() == player.iCivID) {
            InGame_Info.iCivID = iCivID;
            InGame_Info.iCivID2 = getProvince(iProvinceID).getCivID();
            menuManager.rebuildInGame_Info(
               lang.get("AtomicBombing") + ": " + getProvince(iProvinceID).getProvinceName(),
               lang.get("Casualties") + ": " + CFG.getNumberWithSpaces("" + CFG.getPrecision2((float)iCasualties, 1))
            );
            InGame_Info.imgID = Images.infoAtomic;
            if (iCivID == player.iCivID) {
               SteamAchievementsManager.unlockAchievement(SteamAchievementsManager.DROP_NUKE);
            }
         }

         RendererGame.addNuke(iProvinceID);
      }

      return -1;
   }

   public static final int getNuclearReactor_MaxLvl(int iCivID) {
      return GameValues.atomic.NUCLEAR_REACTOR_MAX_LVL + getCiv(iCivID).civBonuses.MaximumLevelOfTheSupremeCourt;
   }

   public static final float getNuclearReactor_ProductionEfficiency(int iCivID) {
      return GameValues.atomic.NUCLEAR_REACTOR_PRODUCTION_EFFICIENCY_PER_LVL * getCiv(iCivID).getNuclearReactorLevel();
   }

   public static final float getNuclearReactor_AtomicBombCost(int iCivID) {
      return GameValues.atomic.NUCLEAR_REACTOR_ATOMIC_BOMB_COST_PER_LEVEL * getCiv(iCivID).getNuclearReactorLevel();
   }

   public static final float getNuclearReactor_Cost(int iCivID) {
      return GameValues.atomic.NUCLEAR_REACTOR_COST + GameValues.atomic.NUCLEAR_REACTOR_COST_PER_LVL * getCiv(iCivID).getNuclearReactorLevel();
   }

   public static final float getAtomicBombCost(int iCivID) {
      return Math.max(
         1.0F,
         (GameValues.atomic.ATOMIC_BOMB_COST + GameValues.atomic.ATOMIC_BOMB_COST_PER_ATOMIC_BOMB * (getCiv(iCivID).getNukes() + getCiv(iCivID).iNukesSize))
            * (1.0F + getNuclearReactor_AtomicBombCost(iCivID))
      );
   }

   public static final int getAtomicBombProductionTime(int iCivID) {
      return (int)GameValues.atomic.ATOMIC_BOMB_PRODUCTION_TIME;
   }

   public static float getIncreaseManpowerCost(int nProvinceID) {
      return Math.max(
         1.0F,
         (GameValues.manpower.INCREASE_MANPOWER_COST + getProvince(nProvinceID).getManpower() * GameValues.manpower.INCREASE_MANPOWER_COST_PER_LEVEL)
            * (
               1.0F
                  + getProvince(nProvinceID).provBonuses.IncreaseManpowerCost
                  + getCiv(getProvince(nProvinceID).getCivID()).civBonuses.IncreaseManpowerCost / 100.0F
            )
      );
   }

   public static float getIncreaseManpowerCostLegacy(int nProvinceID) {
      return Math.max(
         0.05F,
         (
               GameValues.manpower.INCREASE_MANPOWER_COST_LEGACY
                  + getProvince(nProvinceID).getManpower() * GameValues.manpower.INCREASE_MANPOWER_COST_LEGACY_PER_LEVEL
            )
            * (
               1.0F
                  + getProvince(nProvinceID).provBonuses.IncreaseManpowerCost
                  + getCiv(getProvince(nProvinceID).getCivID()).civBonuses.IncreaseManpowerCost / 100.0F
            )
      );
   }

   public static int getIncreseManpowerTime(int nCivID) {
      return GameValues.manpower.INCREASE_MANPOWER_IN_PROVINCE_DAYS;
   }

   public static float getLegacyPerUniqueResources(int iCivID) {
      return GameValues.legacy.LEGACY_PER_UNIQUE_RESOURCE
         * getCiv(iCivID).iUniqueResources
         * Math.min(
            GameValues.legacy.LEGACY_PER_UNIQUE_RESOURCE_PER_TECHS_MAX,
            getCiv(iCivID).getResearchedTechnologies() * GameValues.legacy.LEGACY_PER_UNIQUE_RESOURCE_PER_TECHS
         );
   }

   public static float getIncreaseTaxEfficiencyCost(int nProvinceID) {
      return Math.max(
         1.0F,
         (
               GameValues.tax.INCREASE_TAX_EFFICIENCY_COST
                  + getProvince(nProvinceID).getTaxEfficiencyWithBonuses() * GameValues.tax.INCREASE_TAX_EFFICIENCY_COST_PER_TAX_EFFICIENCY
            )
            * (
               1.0F
                  + getProvince(nProvinceID).provBonuses.IncreaseTaxEfficiencyCost
                  + getCiv(getProvince(nProvinceID).getCivID()).getInflation()
                  + getCiv(getProvince(nProvinceID).getCivID()).civBonuses.IncreaseTaxEfficiencyCost
            )
      );
   }

   public static float getIncreaseTaxEfficiencyCostLegacy(int nProvinceID) {
      return Math.max(
         1.0F,
         (
               GameValues.tax.INCREASE_TAX_EFFICIENCY_COST_LEGACY
                  + getProvince(nProvinceID).getTaxEfficiencyWithBonuses() * GameValues.tax.INCREASE_TAX_EFFICIENCY_COST_LEGACY_PER_TAX
            )
            * (
               1.0F
                  + getProvince(nProvinceID).provBonuses.IncreaseTaxEfficiencyCost
                  + getCiv(getProvince(nProvinceID).getCivID()).getInflation()
                  + getCiv(getProvince(nProvinceID).getCivID()).civBonuses.IncreaseTaxEfficiencyCost
            )
      );
   }

   public static int getIncreasTaxEfficiencyTime(int nCivID) {
      return GameValues.tax.INCREASE_TAX_EFFICIENCY_IN_PROVINCE_DAYS;
   }

   public static float getIncreaseGrowthRateCost(int nProvinceID) {
      return Math.max(
         1,
         (int)(
            (
                  GameValues.growthRate.INCREASE_GROWTH_RATE_COST
                     + (
                           getProvince(nProvinceID).getGrowthRateWithBonuses()
                              + getProvince(nProvinceID).iProvinceIncreaseGrowthRateSize * GameValues.growthRate.INCREASE_GROWTH_RATE_GROWTH
                        )
                        * GameValues.growthRate.INCREASE_GROWTH_RATE_COST_PER_GROWTH_RATE
               )
               * (
                  terrainManager.terrains.get(getProvince(nProvinceID).getTerrainID()).IncreaseGrowthRateCost
                     + getProvince(nProvinceID).provBonuses.IncreaseGrowthRateCost
                     + getCiv(getProvince(nProvinceID).getCivID()).civBonuses.IncreaseGrowthRateCost
               )
         )
      );
   }

   public static int getIncreaseGrowthRateTime(int nCivID) {
      return GameValues.growthRate.INCREASE_GROWTH_RATE_IN_PROVINCE_DAYS;
   }

   public static final void checkHoveredArmy_Fog() {
      if (hoveredArmy.iProvinceID >= 0 && !getProvince(hoveredArmy.iProvinceID).getFogDrawArmy()) {
         hoveredArmy.iProvinceID = -1;
      }
   }

   public static void setInvasionArmyMode() {
      setInvasionArmyMode(!invasionArmyMode);
   }

   public static void setInvasionArmyMode(boolean nInvasionArmyMode) {
      if (invasionArmyMode != nInvasionArmyMode) {
         invasionArmyMode = nInvasionArmyMode;
         invasionArmy_Clear();
         CFG.brushTool = false;
         menuManager.rebuildInGame_ProvinceArmy_InvasionArmy();
         if (invasionArmyMode) {
            InGame.drawOver = new InGameDrawOver_InvasionArmy();
         } else {
            InGame.drawOver = new InGameDrawOver();
         }
      }
   }

   public static void invasionArmy_AddProvince(int nProvinceID) {
      try {
         if (invasionArmyProvinces.contains(nProvinceID)) {
            return;
         }

         if (getProvince(nProvinceID).isOccupied()) {
            if (getCiv(getProvince(nProvinceID).getCivID()).getPuppetOfCivID() != player.iCivID) {
               return;
            }
         } else if (!DiplomacyManager.isAtWar(player.iCivID, getProvince(nProvinceID).getCivID())) {
            return;
         }

         invasionArmyProvinces.add(nProvinceID);
         invasionArmyProvincesSize = invasionArmyProvinces.size();
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   public static void invasionArmy_UndoProvince() {
      try {
         if (invasionArmyProvincesSize > 0) {
            invasionArmyProvinces.remove(invasionArmyProvincesSize - 1);
            invasionArmyProvincesSize = invasionArmyProvinces.size();
         }
      } catch (Exception var1) {
         CFG.exceptionStack(var1);
      }
   }

   public static void invasionArmy_RemoveProvince(int nProvinceID) {
      try {
         for (int i = 0; i < invasionArmyProvincesSize; i++) {
            if (invasionArmyProvinces.get(i) == nProvinceID) {
               invasionArmyProvinces.remove(i);
               invasionArmyProvincesSize = invasionArmyProvinces.size();
               return;
            }
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   public static void invasionArmy_Clear() {
      invasionArmyProvinces.clear();
      invasionArmyProvincesSize = 0;
   }

   public static void setRegroupArmyMode() {
      setRegroupArmyMode(!regroupArmyMode);
   }

   public static void setRegroupArmyMode(boolean nRegroupArmyMode) {
      if (regroupArmyMode != nRegroupArmyMode) {
         regroupArmyMode = nRegroupArmyMode;
         regroupArmy_Clear();
         CFG.brushTool = false;
         menuManager.rebuildInGame_ProvinceArmy_RegroupArmy();
         if (regroupArmyMode) {
            InGame.drawOver = new InGameDrawOver_RegroupArmy();
         } else {
            InGame.drawOver = new InGameDrawOver();
            regroupArmyLine = null;
         }
      }
   }

   public static void regroupArmy_AddProvince(int nProvinceID) {
      try {
         for (int i = 0; i < iRegroupArmyProvincesSize; i++) {
            if (regroupArmyProvinces.get(i) == nProvinceID) {
               return;
            }
         }

         regroupArmyProvinces.add(nProvinceID);
         iRegroupArmyProvincesSize = regroupArmyProvinces.size();
         regroupArmy_BuildArmyShadow();
         regroupArmyLine = new MoveUnits_RegroupLine(regroupArmyProvinces);
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   public static void regroupArmy_UndoProvince() {
      try {
         if (iRegroupArmyProvincesSize > 0) {
            regroupArmyProvinces.remove(iRegroupArmyProvincesSize - 1);
            iRegroupArmyProvincesSize = regroupArmyProvinces.size();
            regroupArmy_BuildArmyShadow();
            regroupArmyLine = new MoveUnits_RegroupLine(regroupArmyProvinces);
         }
      } catch (Exception var1) {
         CFG.exceptionStack(var1);
      }
   }

   public static void regroupArmy_RemoveProvince(int nProvinceID) {
      try {
         for (int i = 0; i < iRegroupArmyProvincesSize; i++) {
            if (regroupArmyProvinces.get(i) == nProvinceID) {
               regroupArmyProvinces.remove(i);
               iRegroupArmyProvincesSize = regroupArmyProvinces.size();
               regroupArmy_BuildArmyShadow();
               regroupArmyLine = new MoveUnits_RegroupLine(regroupArmyProvinces);
               return;
            }
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   public static void regroupArmy_BuildArmyShadow() {
      try {
         regroupArmyShadows.clear();
         List<Integer> numOfArmies = new ArrayList<>();

         for (int i = 0; i < iRegroupArmyProvincesSize; i++) {
            numOfArmies.add(0);
         }

         if (iRegroupArmyProvincesSize > 0) {
            for (int i = 0; i < activeArmySize; i++) {
               Game.ArmyPos nArmyPos = findArmy_FullCheck(activeArmy.get(i).iCivID, activeArmy.get(i).key);
               if (nArmyPos != null) {
                  regroupArmyShadows.add(
                     new ArmyDivision_Shadow(
                        getProvince(nArmyPos.iProvinceID).getArmy(nArmyPos.iID).sArmy,
                        regroupArmyProvinces.get(i % iRegroupArmyProvincesSize),
                        numOfArmies.get(i % iRegroupArmyProvincesSize)
                     )
                  );
                  numOfArmies.set(i % iRegroupArmyProvincesSize, numOfArmies.get(i % iRegroupArmyProvincesSize) + 1);
               }
            }
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public static void regroupArmy_Move() {
      try {
         if (iRegroupArmyProvincesSize > 0) {
            for (int i = 0; i < activeArmySize; i++) {
               Game.ArmyPos nArmyPos = findArmy_FullCheck(activeArmy.get(i).iCivID, activeArmy.get(i).key);
               if (nArmyPos != null) {
                  int extraY = 0;

                  try {
                     for (int j = 0; j < i; j++) {
                        if (activeArmy.get(i).iProvinceID == activeArmy.get(j).iProvinceID) {
                           extraY++;
                        }
                     }
                  } catch (Exception var4) {
                     CFG.exceptionStack(var4);
                  }

                  player.playerData.invasion.removeInvasion(activeArmy.get(i).key);
                  getCiv(activeArmy.get(i).iCivID)
                     .newMove(nArmyPos.iProvinceID, regroupArmyProvinces.get(i % iRegroupArmyProvincesSize), activeArmy.get(i).key, extraY, false);
               }
            }
         }

         setRegroupArmyMode(false);
         soundsManager.playMove();
         if (menuManager.getVisibleInGame_ProvinceArmy()) {
            menuManager.rebuildInGame_ProvinceArmy(true, true);
         }
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
      }
   }

   public static void regroupArmy_Clear() {
      regroupArmyProvinces.clear();
      regroupArmyShadows.clear();
      iRegroupArmyProvincesSize = 0;
      regroupArmyLine = null;
   }

   public static final void addActiveArmy(Game.HoveredArmy nHA) {
      for (int i = 0; i < activeArmySize; i++) {
         if (activeArmy.get(i).key.equals(nHA.key)) {
            return;
         }
      }

      activeArmy.add(nHA);
      activeArmySize = activeArmy.size();
   }

   public static final boolean isAlreadyActiveArmy(String key) {
      for (int i = 0; i < activeArmySize; i++) {
         if (activeArmy.get(i).key.equals(key)) {
            return true;
         }
      }

      return false;
   }

   public static final void setActiveArmy(int nProvinceID, String sKey) {
      clearActiveArmy();

      try {
         int tID = getProvince(nProvinceID).getArmyKeyID(sKey);
         if (tID >= 0) {
            Game.HoveredArmy nHA = new Game.HoveredArmy();
            nHA.key = getProvince(nProvinceID).getArmy(tID).key;
            nHA.iCivID = getProvince(nProvinceID).getArmy(tID).civID;
            nHA.iProvinceID = nProvinceID;
            nHA.iArmyID = tID;
            activeArmy.add(nHA);
            activeArmySize = activeArmy.size();
         }
      } catch (IndexOutOfBoundsException var4) {
      }
   }

   public static final void updateActiveArmy_MoveUnits(String key, int oldProvinceID, int newProvinceID) {
      try {
         if (hoveredArmy.iProvinceID == oldProvinceID) {
            hoveredArmy.iProvinceID = newProvinceID;
            hoveredArmy.iArmyID = getProvince(hoveredArmy.iProvinceID).getArmyKeyID(hoveredArmy.key);
            if (hoveredArmy.iArmyID < 0) {
               hoveredArmy.iProvinceID = -1;
            }
         }

         for (int i = 0; i < activeArmySize; i++) {
            if (activeArmy.get(i).iProvinceID == oldProvinceID && activeArmy.get(i).key.equals(key)) {
               activeArmy.get(i).iArmyID = getProvince(newProvinceID).getArmyKeyID(activeArmy.get(i).key);
               if (activeArmy.get(i).iArmyID >= 0) {
                  activeArmy.get(i).iProvinceID = newProvinceID;
                  activeArmy.get(i).iArmyID = newProvinceID;
                  if (menuManager.getVisibleInGame_ProvinceArmy() && InGame_ProvinceArmy.sActiveKEY.equals(key)) {
                     addSimpleTask(new Game.SimpleTask("rebuildProvinceArmy") {
                        @Override
                        public void update() {
                           Game.menuManager.rebuildInGame_ProvinceArmy(true, false);
                        }
                     });
                  }
               } else {
                  activeArmy.remove(i);
                  activeArmySize = activeArmy.size();
                  if (activeArmySize == 0) {
                     menuManager.setVisibleInGame_ProvinceArmy(false);
                  } else if (menuManager.getVisibleInGame_ProvinceArmy() && InGame_ProvinceArmy.sActiveKEY.equals(key)) {
                     addSimpleTask(new Game.SimpleTask("rebuildProvinceArmy") {
                        @Override
                        public void update() {
                           Game.menuManager.rebuildInGame_ProvinceArmy(true, false);
                        }
                     });
                  }
               }

               return;
            }
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   public static final void clearActiveArmy() {
      activeArmy.clear();
      activeArmySize = 0;
      setRegroupArmyMode(false);
   }

   public static final void removeActiveArmy(int id) {
      activeArmy.remove(id);
      activeArmySize = activeArmy.size();
      if (activeArmySize == 0) {
         setRegroupArmyMode(false);
      }
   }

   public static final void removeActiveArmy(String key) {
      for (int i = 0; i < activeArmySize; i++) {
         if (activeArmy.get(i).key.equals(key)) {
            activeArmy.remove(i);
            activeArmySize = activeArmy.size();
            return;
         }
      }
   }

   public static final boolean checkActiveArmy_Fog() {
      boolean outUpdate = false;

      try {
         for (int i = 0; i < activeArmySize; i++) {
            if (!getProvince(activeArmy.get(i).iProvinceID).getFogDrawArmy()) {
               activeArmy.remove(i);
               activeArmySize = activeArmy.size();
               outUpdate = true;
               i--;
            }
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }

      return outUpdate;
   }

   public static final Civilization getNeutralCivilization() {
      return new Civilization(0, "neu", 0, 251, 251, 221, -1, 0, 0);
   }

   protected static final void loadSettings() {
      settingsManager = new SettingsManager();
      loadSettingsDefault();

      try {
         if (FileManager.loadFile("settings/Settings.txt").exists()) {
            FileHandle fileList = FileManager.loadFile("settings/Settings.txt");
            Json json = new Json();
            settingsManager = json.fromJson(SettingsManager.class, fileList);
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }

      Map.updateDrawProvincesFlags();
   }

   public static void saveSettings() {
      Json json = SaveManager.getJson();
      json.setElementType(LoadManager.ConfigJson.class, "Data", SettingsManager.class);
      FileHandle file;
      if (FileManager.IS_MAC) {
         file = Gdx.files.external("settings/Settings.txt");
      } else {
         file = Gdx.files.local("settings/Settings.txt");
      }

      file.writeString(json.prettyPrint(settingsManager), false);
   }

   public static final void loadSettingsDefault() {
      if (!CFG.isDesktop()) {
         settingsManager.DOUBLE_BORDER = false;
         settingsManager.SETTINGS_PROVINCE_BORDER = 2;
         settingsManager.SETTINGS_PROVINCE_FLAGS = 1;
         settingsManager.SETTINGS_PROVINCE_NAMES = 2;
         settingsManager.CLOUDS = false;
         settingsManager.SHIPS_ON_MAP = 30;
         settingsManager.SETTINGS_CIV_NAMES = 1;
         settingsManager.BORDER_EXTRA_WIDTH = 0.5F;
         settingsManager.FONT_BORDER_SIZE = 84;
      }
   }

   protected static final void loadLanguage() {
      Init_SelectLanguage.loadLanguages();
      lang = new LanguageManager(settingsManager.LANGUAGE_TAG);
   }

   public static final void updateGame() {
      deltaTime = Math.min(2.0F, Gdx.graphics.getDeltaTime() / 0.016667F);
      ProvinceBorderManager.update();
      ProvinceBorderManager.action.update();
      if (activeArmySize > 0 || hoveredArmy.iProvinceID >= 0) {
         animationHover.update();
      }

      if (iActiveProvince >= 0 && getProvince(iActiveProvince).getDrawProvince()) {
         activeProvince_Animation_Data.update();
      }

      selectedProvinces_Animation_Data.update();
   }

   public static final void updateSimpleTask() {
      try {
         for (int i = simpleTasks.size() - 1; i >= 0; i--) {
            try {
               simpleTasks.remove().update();
            } catch (Exception var2) {
               CFG.exceptionStack(var2);
            }
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public static final void addSimpleTask(Game.SimpleTask nSimpleTask) {
      if (!simpleTasks.contains(nSimpleTask)) {
         simpleTasks.add(nSimpleTask);
      }
   }

   public static final void addSimpleTask_First(Game.SimpleTask nSimpleTask) {
      if (!simpleTasks.contains(nSimpleTask)) {
         simpleTasks.addFirst(nSimpleTask);
      }
   }

   public static final void loadCursor(boolean resetToDefault) {
      if (settingsManager.loadCursor) {
         lCursors.add(Gdx.graphics.newCursor(new Pixmap(FileManager.loadFile("gfx/cursor/standard.png")), 0, 0));
         lCursors.add(Gdx.graphics.newCursor(new Pixmap(FileManager.loadFile("gfx/cursor/drag.png")), 0, 0));
         lCursors.add(Gdx.graphics.newCursor(new Pixmap(FileManager.loadFile("gfx/cursor/build.png")), 0, 0));
         lCursors.add(Gdx.graphics.newCursor(new Pixmap(FileManager.loadFile("gfx/cursor/recruit.png")), 0, 0));
         lCursors.add(Gdx.graphics.newCursor(new Pixmap(FileManager.loadFile("gfx/cursor/recruit2.png")), 0, 0));
         lCursors.add(Gdx.graphics.newCursor(new Pixmap(FileManager.loadFile("gfx/cursor/x.png")), 0, 0));
         lCursors.add(Gdx.graphics.newCursor(new Pixmap(FileManager.loadFile("gfx/cursor/plus.png")), 0, 0));
         lCursors.add(Gdx.graphics.newCursor(new Pixmap(FileManager.loadFile("gfx/cursor/economy.png")), 0, 0));
         lCursors.add(Gdx.graphics.newCursor(new Pixmap(FileManager.loadFile("gfx/cursor/tax.png")), 0, 0));
         lCursors.add(Gdx.graphics.newCursor(new Pixmap(FileManager.loadFile("gfx/cursor/core.png")), 0, 0));
         lCursors.add(Gdx.graphics.newCursor(new Pixmap(FileManager.loadFile("gfx/cursor/religion.png")), 0, 0));
         lCursors.add(Gdx.graphics.newCursor(new Pixmap(FileManager.loadFile("gfx/cursor/populationGrowth.png")), 0, 0));
         lCursors.add(Gdx.graphics.newCursor(new Pixmap(FileManager.loadFile("gfx/cursor/infrastructure.png")), 0, 0));
         lCursors.add(Gdx.graphics.newCursor(new Pixmap(FileManager.loadFile("gfx/cursor/recruit3.png")), 0, 0));
         lCursors.add(Gdx.graphics.newCursor(new Pixmap(FileManager.loadFile("gfx/cursor/economy2.png")), 0, 0));
         lCursors.add(Gdx.graphics.newCursor(new Pixmap(FileManager.loadFile("gfx/cursor/nuke.png")), 0, 0));
         Gdx.graphics.setCursor(lCursors.get(0));
      } else if (resetToDefault) {
         Gdx.graphics.setSystemCursor(SystemCursor.Arrow);
      }
   }

   public static final void setCursorDefault() {
      if (settingsManager.loadCursor && activeCursorID != 0) {
         Gdx.graphics.setCursor(lCursors.get(0));
         activeCursorID = 0;
      }
   }

   public static final void setCursorDrag() {
      if (settingsManager.loadCursor && activeCursorID != 1) {
         Gdx.graphics.setCursor(lCursors.get(1));
         activeCursorID = 1;
      }
   }

   public static final void setCursorBuild() {
      if (settingsManager.loadCursor && activeCursorID != 2) {
         Gdx.graphics.setCursor(lCursors.get(2));
         activeCursorID = 2;
      }
   }

   public static final void setCursorRecruit() {
      if (settingsManager.loadCursor && activeCursorID != 3) {
         if (gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).OVER_IMAGE_ID > 2) {
            Gdx.graphics.setCursor(lCursors.get(13));
            activeCursorID = 13;
         } else if (gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).OVER_IMAGE_ID > 1) {
            Gdx.graphics.setCursor(lCursors.get(4));
            activeCursorID = 4;
         } else {
            Gdx.graphics.setCursor(lCursors.get(3));
            activeCursorID = 3;
         }
      }
   }

   public static final void setCursorX() {
      if (settingsManager.loadCursor && activeCursorID != 5) {
         Gdx.graphics.setCursor(lCursors.get(5));
         activeCursorID = 5;
      }
   }

   public static final void setCursorPlus() {
      if (settingsManager.loadCursor && activeCursorID != 6) {
         Gdx.graphics.setCursor(lCursors.get(6));
         activeCursorID = 6;
      }
   }

   public static final void setCursorEconomy() {
      if (settingsManager.loadCursor && activeCursorID != 7) {
         if (gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).OVER_IMAGE_ID > 2) {
            Gdx.graphics.setCursor(lCursors.get(14));
            activeCursorID = 14;
         } else {
            Gdx.graphics.setCursor(lCursors.get(7));
            activeCursorID = 7;
         }
      }
   }

   public static final void setCursorTax() {
      if (settingsManager.loadCursor && activeCursorID != 8) {
         Gdx.graphics.setCursor(lCursors.get(8));
         activeCursorID = 8;
      }
   }

   public static final void setCursorCore() {
      if (settingsManager.loadCursor && activeCursorID != 9) {
         Gdx.graphics.setCursor(lCursors.get(9));
         activeCursorID = 9;
      }
   }

   public static final void setCursorReligion() {
      if (settingsManager.loadCursor && activeCursorID != 10) {
         Gdx.graphics.setCursor(lCursors.get(10));
         activeCursorID = 10;
      }
   }

   public static final void setCursorPopulationGrowth() {
      if (settingsManager.loadCursor && activeCursorID != 11) {
         Gdx.graphics.setCursor(lCursors.get(11));
         activeCursorID = 11;
      }
   }

   public static final void setCursorInfrastructure() {
      if (settingsManager.loadCursor && activeCursorID != 12) {
         Gdx.graphics.setCursor(lCursors.get(12));
         activeCursorID = 12;
      }
   }

   public static final void setCursorNuke() {
      if (settingsManager.loadCursor && activeCursorID != 15) {
         Gdx.graphics.setCursor(lCursors.get(15));
         activeCursorID = 15;
      }
   }

   public static final Game.LoadCivilizationData loadCivilization(String nTag) {
      try {
         Json json = new Json();
         Game.LoadCivilizationData outCivData;
         if (FileManager.loadFile("game/civilizations/" + nTag + ".json").exists()) {
            FileHandle file = FileManager.loadFile("game/civilizations/" + nTag + ".json");
            outCivData = json.fromJson(Game.LoadCivilizationData.class, file);
         } else if (FileManager.loadFile("game/civilizations/" + ideologiesManager.getRealTag(nTag) + ".json").exists()) {
            FileHandle file = FileManager.loadFile("game/civilizations/" + ideologiesManager.getRealTag(nTag) + ".json");
            outCivData = json.fromJson(Game.LoadCivilizationData.class, file);
         } else if (FileManager.loadFile("mods/GameCivs/game/civilizations/" + nTag + ".json").exists()) {
            FileHandle file = FileManager.loadFile("mods/GameCivs/game/civilizations/" + nTag + ".json");
            outCivData = json.fromJson(Game.LoadCivilizationData.class, file);
         } else {
            if (!FileManager.loadFile("mods/GameCivs/game/civilizations/" + ideologiesManager.getRealTag(nTag) + ".json").exists()) {
               return new Game.LoadCivilizationData();
            }

            FileHandle file = FileManager.loadFile("mods/GameCivs/game/civilizations/" + ideologiesManager.getRealTag(nTag) + ".json");
            outCivData = json.fromJson(Game.LoadCivilizationData.class, file);
         }

         return outCivData;
      } catch (GdxRuntimeException var4) {
         CFG.exceptionStack(var4);
         return new Game.LoadCivilizationData();
      }
   }

   public static final void initProvinceData() {
      for (int i = 0; i < getProvincesSize(); i++) {
         getProvince(i).updateBuildingLimit();
         getProvince(i).updateInfrastructureMax();
      }

      for (int i = 0; i < getProvincesSize(); i++) {
         getProvince(i).updateProvinceValue();
         getProvince(i).updateCityScale();
      }
   }

   public static final void removeCivilization(int nRemoveCivID) {
      if (nRemoveCivID != 0) {
         getCiv(nRemoveCivID).getFlag().getTexture().dispose();
         if (getCiv(nRemoveCivID).getCapitalProvinceID() >= 0) {
            getProvince(getCiv(nRemoveCivID).getCapitalProvinceID()).setIsCapital(false);
         }

         for (int i = 0; i < getProvincesSize(); i++) {
            getProvince(i).clearArmiesCivID(nRemoveCivID);
         }

         lCivs.remove(nRemoveCivID);
         iCivsSize = lCivs.size();

         for (int i = 1; i < iCivsSize; i++) {
            getCiv(i).setCivID_Just(i);
            if (getCiv(i).getPuppetOfCivID() > nRemoveCivID) {
               getCiv(i).setPuppetOfCivID(getCiv(i).getPuppetOfCivID() - 1);
            } else if (getCiv(i).getPuppetOfCivID() == nRemoveCivID) {
               getCiv(i).setPuppetOfCivID(getCiv(i).getCivID());
            }
         }

         for (int ix = 0; ix < iProvincesSize; ix++) {
            if (getProvince(ix).getCivID() > nRemoveCivID) {
               getProvince(ix).setCivID_Just(getProvince(ix).getCivID() - 1);
            } else if (getProvince(ix).getCivID() == nRemoveCivID) {
               getProvince(ix).setCivID_Just(0);
               updateProvinceBorder(ix);
            }

            for (int j = getProvince(ix).getArmySize() - 1; j >= 0; j--) {
               if (getProvince(ix).getArmy(j).civID > nRemoveCivID) {
                  getProvince(ix).getArmy(j).civID--;
               } else if (getProvince(ix).getArmy(j).civID == nRemoveCivID) {
                  getProvince(ix).removeArmy(j);
               }
            }

            getProvince(ix).updateCores_AfterRemoveCiv(nRemoveCivID);
         }

         try {
            for (int ix = 0; ix < alliancesSpecialSize; ix++) {
               alliancesSpecial.get(ix).updateAfterRemoveOfCiv(nRemoveCivID);
            }
         } catch (Exception var3) {
            CFG.exceptionStack(var3);
         }

         DiplomacyManager.updateAfterRemoveCiv(nRemoveCivID);
         updateProvincesInView = true;
         CivilizationRegionsManager.updateRegionsInView = true;
      }
   }

   public static final boolean addCivilization(
      String nCivTag, int nProvinceID, boolean rebuildCores, boolean addCore, boolean isInGame, boolean updateOwnerOfProvince, boolean loadFlagLater
   ) {
      for (int i = 0; i < getCivsSize(); i++) {
         if (getCiv(i).getCivTag().equals(nCivTag)) {
            return false;
         }
      }

      Game.LoadCivilizationData civData = loadCivilization(nCivTag);
      if (loadFlagLater) {
         lCivs.add(
            new Civilization(getCivsSize(), nCivTag, iCivsSize, civData.iR, civData.iG, civData.iB, nProvinceID, civData.ReligionID, civData.GroupID, true)
         );
         lCivs.get(lCivs.size() - 1).loadScenario_A();
         addSimpleTask(new Game.SimpleTask("LOAD_FLAG" + lCivs.size() + CFG.extraRandomTag(), lCivs.size() - 1) {
            @Override
            public void update() {
               Game.getCiv(this.id).loadFlag();
            }
         });
      } else {
         lCivs.add(new Civilization(getCivsSize(), nCivTag, iCivsSize, civData.iR, civData.iG, civData.iB, nProvinceID, civData.ReligionID, civData.GroupID));
      }

      iCivsSize = lCivs.size();
      lCivs.get(iCivsSize - 1).initTechTree();
      lCivs.get(iCivsSize - 1).initGoodsProduced();
      if (updateOwnerOfProvince) {
         int tCivID = getProvince(nProvinceID).getCivID();
         getCiv(iCivsSize - 1).addProvince(nProvinceID);
         getProvince(nProvinceID).setCivID_RemoveOldAddNewToCiv(iCivsSize - 1);
         getProvince(nProvinceID).setIsCapital(true);
         getProvince(nProvinceID).setDrawCities(true);
         if (tCivID > 0) {
            getCiv(tCivID).removeProvince(nProvinceID);
            addSimpleTask(new Game.SimpleTask("buildCivilizationsRegion" + tCivID, tCivID) {
               @Override
               public void update() {
                  CivilizationRegionsManager.buildCivilizationsRegion(this.id);
               }
            });
         }
      }

      return true;
   }

   public static final void updateProvinceBorder(int i) {
      for (int j = 0; j < getProvince(i).getNeighboringProvincesSize(); j++) {
         if (i < getProvince(i).getNeighboringProvinces(j)) {
            getProvince(i)
               .getProvinceBordersLandByLand(getProvince(i).getNeighboringProvinces(j))
               .setIsCivilizationBorder(getProvince(i).getCivID() != getProvince(getProvince(i).getNeighboringProvinces(j)).getCivID(), i);
         } else {
            getProvince(getProvince(i).getNeighboringProvinces(j))
               .getProvinceBordersLandByLand(i)
               .setIsCivilizationBorder(
                  getProvince(i).getCivID() != getProvince(getProvince(i).getNeighboringProvinces(j)).getCivID(), getProvince(i).getNeighboringProvinces(j)
               );
         }
      }
   }

   public static final void buildProvinceIsCapital() {
      for (int i = 1; i < getCivsSize(); i++) {
         if (getCiv(i).getCapitalProvinceID() >= 0 && getProvince(getCiv(i).getCapitalProvinceID()).getCivID() == i) {
            getProvince(getCiv(i).getCapitalProvinceID()).setIsCapital(true);
         }
      }
   }

   public static final void disposeCivilizations() {
      if (lCivs != null) {
         clearActiveArmy();

         for (int i = 0; i < getCivsSize(); i++) {
            try {
               getCiv(i).disposeFlag();
            } catch (Exception var2) {
               CFG.exceptionStack(var2);
            }
         }

         for (int i = 0; i < getProvincesSize(); i++) {
            getProvince(i).clearData();
         }

         lProvincesData.clear();
         lProvincesData2.clear();
         lProvincesData3.clear();
         lProvincesData4.clear();
         lProvincesData5.clear();
         lProvincesData6.clear();
         lProvincesData7.clear();
         lProvincesData8.clear();
         lProvincesData9.clear();
         lProvincesData10.clear();
         lProvincesPopulation.clear();

         for (int i = 0; i < getProvincesSize(); i++) {
            lProvincesData.add(new ProvinceData());
            lProvincesData2.add(new ProvinceData2());
            lProvincesData3.add(new ProvinceData3());
            lProvincesData4.add(new ProvinceData4());
            lProvincesData5.add(new ProvinceData5());
            lProvincesData6.add(new ProvinceData6());
            lProvincesData7.add(new ProvinceData7());
            lProvincesData8.add(new ProvinceData8());
            lProvincesData9.add(new ProvinceData9());
            lProvincesData10.add(new ProvinceData10());
            lProvincesPopulation.add(new ProvinceData_Population());
         }

         lCivs.clear();
         iCivsSize = 0;
         WondersManager.initProvinceWonders();
      }
   }

   public static final void disposeCutProvinces() {
      cutProvinces.clear();
      cutProvinces = null;
   }

   public static final void loadProvincesBG() {
      for (int i = 0; i < iProvincesSize; i++) {
         getProvince(i).loadProvinceBG();
      }

      disposeCutProvinces();
   }

   public static final void loadProvinceBG(int nProvinceID) {
      getProvince(nProvinceID).loadProvinceBG();
   }

   public static final void buildWastelandLevels() {
      try {
         List<Integer> tWasteland = new ArrayList<>();

         for (int i = 0; i < getProvincesSize(); i++) {
            if (getProvince(i).getWasteland() >= 0 && !getProvince(i).getSeaProvince()) {
               tWasteland.add(i);
               getProvince(i).setWasteland(0);
            }
         }

         buildWastelandLevels(tWasteland, 0, tWasteland.size());
         tWasteland.clear();
         tWasteland = null;
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   public static final void buildWastelandLevels(List<Integer> tWasteland, int nLevel, int nWastelandSize) {
      try {
         boolean rec = false;

         for (int i = 0; i < nWastelandSize; i++) {
            if (getProvince(tWasteland.get(i)).getWasteland() == nLevel && getProvince(tWasteland.get(i)).getNeighboringSeaProvincesSize() <= 0) {
               boolean incLevel = true;

               for (int j = 0; j < getProvince(tWasteland.get(i)).getNeighboringProvincesSize(); j++) {
                  if (getProvince(getProvince(tWasteland.get(i)).getNeighboringProvinces(j)).getWasteland() < nLevel) {
                     incLevel = false;
                     break;
                  }
               }

               if (incLevel) {
                  getProvince(tWasteland.get(i)).setWasteland(nLevel + 1);
                  rec = true;
               } else {
                  tWasteland.remove(i);
                  i--;
                  nWastelandSize = tWasteland.size();
               }
            } else {
               tWasteland.remove(i);
               i--;
               nWastelandSize = tWasteland.size();
            }
         }

         if (rec && nLevel < maxWastelandLvl) {
            buildWastelandLevels(tWasteland, nLevel + 1, nWastelandSize);
         }
      } catch (StackOverflowError var7) {
         CFG.exceptionStack(var7);
      }
   }

   public static final boolean checkClosedSea(int nProvinceID) {
      for (int i = 0; i < getProvince(nProvinceID).getNeighboringSeaProvincesSize(); i++) {
         if (getProvince(getProvince(nProvinceID).getNeighboringSeaProvinces(i)).getLevelOfPort() == -2) {
            return true;
         }
      }

      return false;
   }

   public static final void updateProvinceBG_ActiveHoveredProvince() {
      try {
         if (!CFG.isDesktop()) {
            return;
         }

         if (!GameValues.value.LOAD_SEA_PROVINCES
            && GameValues.value.LOAD_HOVERED_SEA_PROVINCE
            && iHoveredProvinceID >= 0
            && getProvince(iHoveredProvinceID).getSeaProvince()
            && hoveredProvinceBG_LoadedID != iHoveredProvinceID) {
            if (hoveredProvinceBG != null) {
               hoveredProvinceBG.dispose();
               hoveredProvinceBG = null;
               hoveredProvinceBG_LoadedID = -1;
            }

            if (FileManager.loadFile(
                  "map/"
                     + map.getFile_ActiveMap_Path()
                     + "data/"
                     + "scales/"
                     + (getProvince(iHoveredProvinceID).getLevelOfPort() == -4 ? 1 : (int)(mapBG.iMapScale / mapBG.iMapExtraScale))
                     + "/"
                     + iHoveredProvinceID
               )
               .exists()) {
               Pixmap pixmap = PixmapIO.readCIM(
                  FileManager.loadFile(
                     "map/"
                        + map.getFile_ActiveMap_Path()
                        + "data/"
                        + "scales/"
                        + (getProvince(iHoveredProvinceID).getLevelOfPort() == -4 ? 1 : (int)(mapBG.iMapScale / mapBG.iMapExtraScale))
                        + "/"
                        + iHoveredProvinceID
                  )
               );
               Pixmap convertedPixmap = new Pixmap(pixmap.getWidth(), pixmap.getHeight(), Format.Alpha);
               convertedPixmap.drawPixmap(pixmap, 0, 0);
               hoveredProvinceBG = new Image(new Texture(convertedPixmap), TextureFilter.Nearest, TextureWrap.ClampToEdge);
               pixmap.dispose();
               Pixmap var3 = null;
               convertedPixmap.dispose();
               Pixmap var4 = null;
               hoveredProvinceBG_LoadedID = iHoveredProvinceID;
            }
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   public static final void drawActiveProvince(SpriteBatch oSB) {
      updateProvinceBG_ActiveHoveredProvince();
      oSB.setShader(Renderer.shaderDefaultProvince);

      try {
         if (mapScale.getCurrentScale() >= DRAW_INNER_BORDERS) {
            if (iActiveProvince >= 0 && getProvince(iActiveProvince).getDrawProvince()) {
               try {
                  if (chooseProvinceMode) {
                     oSB.setColor(
                        new Color(
                           CFG.getColorStep(255, 55, activeProvince_Animation_Data.getColorStepID(), 30),
                           CFG.getColorStep(255, 55, activeProvince_Animation_Data.getColorStepID(), 30),
                           CFG.getColorStep(255, 55, activeProvince_Animation_Data.getColorStepID(), 30),
                           (
                                 getProvince(iActiveProvince).getSeaProvince()
                                    ? (activeProvince_Animation_Data.getAlpha() + 35.0F) / 255.0F / 3.0F
                                    : (activeProvince_Animation_Data.getAlpha() + 35.0F) / 255.0F * (fDashedLine_Percentage_HighlitedProvinceBorder / 100.0F)
                              )
                              / (mapScale.getCurrentScale() > 1.0F ? mapScale.getCurrentScale() : 1.0F)
                        )
                     );
                     getProvince(iActiveProvince).drawProvince_ActiveProvince(oSB);
                  } else {
                     if (getProvince(iActiveProvince).getSeaProvince()) {
                        oSB.setColor(new Color(1.0F, 1.0F, 1.0F, activeProvince_Animation_Data.getAlpha() / 2.0F / 255.0F));
                     } else {
                        oSB.setColor(
                           new Color(
                              0.96F,
                              0.96F,
                              0.94F,
                              activeProvince_Animation_Data.getAlpha() / 255.0F / (mapScale.getCurrentScale() > 1.0F ? mapScale.getCurrentScale() : 1.0F)
                           )
                        );
                     }

                     getProvince(iActiveProvince).drawProvince_ActiveProvince(oSB);
                  }
               } catch (Exception var3) {
               }
            }

            try {
               if (iHoveredProvinceID >= 0 && getProvince(iHoveredProvinceID).getDrawProvince()) {
                  if (!GameValues.value.LOAD_SEA_PROVINCES && getProvince(iHoveredProvinceID).getSeaProvince()) {
                     oSB.setColor(
                        new Color(
                           1.0F,
                           1.0F,
                           1.0F,
                           (getProvince(iHoveredProvinceID).getSeaProvince() ? 0.02F : 0.05F)
                              / (mapScale.getCurrentScale() > 1.0F ? mapScale.getCurrentScale() : 1.0F)
                        )
                     );
                     getProvince(iHoveredProvinceID).drawProvince_ActiveProvince(oSB, hoveredProvinceBG);
                  } else if (iActiveProvince != iHoveredProvinceID) {
                     try {
                        oSB.setColor(
                           new Color(
                              1.0F,
                              1.0F,
                              1.0F,
                              (getProvince(iHoveredProvinceID).getSeaProvince() ? 0.02F : 0.05F)
                                 / (mapScale.getCurrentScale() > 1.0F ? mapScale.getCurrentScale() : 1.0F)
                           )
                        );
                        getProvince(iHoveredProvinceID).drawProvince_ActiveProvince(oSB);
                     } catch (Exception var2) {
                     }
                  }
               }
            } catch (Exception var4) {
               CFG.exceptionStack(var4);
            }

            oSB.setColor(Color.WHITE);
         }
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
      }

      oSB.setShader(Renderer.shaderDefault);
   }

   protected static final void updateHoveredProvince_HoverInMapMode(int nPosX, int nPosY) {
      if (menuManager.dialogMenu.getVisible()) {
         provinceHover_Informations = null;
      } else if (hoverManager.hoverActiveMenuTitleID < 0) {
         try {
            if (iHoveredProvinceID >= 0) {
               if (setProvinceID_IsMouseOverAProvinceID(
                  (int)(nPosX / mapScale.getCurrentScale()), (int)(nPosY / mapScale.getCurrentScale()), iHoveredProvinceID
               )) {
                  return;
               }

               setHoveredProvinceID(setProvinceID_HoverAProvince((int)(nPosX / mapScale.getCurrentScale()), (int)(nPosY / mapScale.getCurrentScale())));
               if (iHoveredProvinceID < 0) {
                  provinceHover_Informations = null;
               }
            } else {
               setHoveredProvinceID(setProvinceID_HoverAProvince((int)(nPosX / mapScale.getCurrentScale()), (int)(nPosY / mapScale.getCurrentScale())));
               if (iHoveredProvinceID >= 0) {
                  MenuElement_Hover.resetAnimation();
               } else {
                  provinceHover_Informations = null;
               }
            }

            provinceHover_Informations = getProvince(iHoveredProvinceID).drawDetails.buildHover(iHoveredProvinceID);
         } catch (NullPointerException var3) {
            provinceHover_Informations = null;
            iHoveredProvinceID = -1;
         } catch (IndexOutOfBoundsException var4) {
            provinceHover_Informations = null;
            iHoveredProvinceID = -1;
         }
      }
   }

   protected static final void updateHoveredProvince_Hover(int nPosX, int nPosY) {
      try {
         if (menuManager.dialogMenu.getVisible()) {
            provinceHover_Informations = null;
            return;
         }

         if (hoverManager.hoverActiveMenuTitleID >= 0) {
            return;
         }

         try {
            if (iHoveredProvinceID >= 0) {
               if (!getProvince(iHoveredProvinceID).getSeaProvince()
                  && setProvinceID_IsMouseOverAProvinceID(
                     (int)(nPosX / mapScale.getCurrentScale()), (int)(nPosY / mapScale.getCurrentScale()), iHoveredProvinceID
                  )) {
                  return;
               }

               setHoveredProvinceID(setProvinceID_HoverAProvince((int)(nPosX / mapScale.getCurrentScale()), (int)(nPosY / mapScale.getCurrentScale())));
               if (iHoveredProvinceID < 0) {
                  provinceHover_Informations = null;
               }
            } else {
               setHoveredProvinceID(setProvinceID_HoverAProvince((int)(nPosX / mapScale.getCurrentScale()), (int)(nPosY / mapScale.getCurrentScale())));
               if (iHoveredProvinceID >= 0) {
                  MenuElement_Hover.resetAnimation();
               } else {
                  provinceHover_Informations = null;
               }
            }

            ProvinceHover.provinceHoverBuild.build();
         } catch (Exception var3) {
            provinceHover_Informations = null;
            iHoveredProvinceID = -1;
            CFG.exceptionStack(var3);
         }
      } catch (Exception var4) {
      }
   }

   public static final void updateHighlitghtProvinceBorder(SpriteBatch oSB) {
      if (highlightedProvinceBorder_Update) {
         if (highlightedProvinceBorder_BackAnimation) {
            fDashedLine_Percentage_HighlitedProvinceBorder = fDashedLine_Percentage_HighlitedProvinceBorder
               - (float)(CFG.currentTimeMillis - lDashedLineTime_Percentage_HighlitedProvinceBorder) / 350.0F * 100.0F;
            if (fDashedLine_Percentage_HighlitedProvinceBorder <= 0.0F) {
               highlightedProvinceBorder_Update = false;
               return;
            }

            lDashedLineTime_Percentage_HighlitedProvinceBorder = CFG.currentTimeMillis;
         } else {
            fDashedLine_Percentage_HighlitedProvinceBorder = fDashedLine_Percentage_HighlitedProvinceBorder
               + (float)(CFG.currentTimeMillis - lDashedLineTime_Percentage_HighlitedProvinceBorder) / 750.0F * 95.0F;
            if (fDashedLine_Percentage_HighlitedProvinceBorder > 100.0F) {
               fDashedLine_Percentage_HighlitedProvinceBorder = 100.0F;
               highlightedProvinceBorder_Update = false;
               return;
            }

            lDashedLineTime_Percentage_HighlitedProvinceBorder = CFG.currentTimeMillis;
         }
      }
   }

   public static final int setProvinceID_HoverAProvince(int nPosX, int nPosY) {
      int i = 0;

      for (int tPosX = 0; i < iProvincesSize; i++) {
         tPosX = (int)Math.abs(checkPosOfClickX(mapCoords.getPosX() - nPosX));
         if (getProvince(i).getMinX() <= tPosX
            && getProvince(i).getMaxX() >= tPosX
            && getProvince(i).getMinY() <= -mapCoords.getPosY() + nPosY
            && getProvince(i).getMaxY() >= -mapCoords.getPosY() + nPosY
            && pathContains(i, tPosX, -mapCoords.getPosY() + nPosY)) {
            return i;
         }
      }

      if (Math.abs(checkPosOfClickX(mapCoords.getPosX() - nPosX)) + -MAX_BELOW_ZERO_POINT_X * mapBG.iMapScale / mapScale.getCurrentScale() > mapBG.getWidth()) {
         i = 0;

         for (int tPosXx = 0; i < iProvincesSize; i++) {
            if (getProvince(i).getBelowZero()) {
               tPosXx = (int)Math.abs(checkPosOfClickX(mapCoords.getPosX() - nPosX));
               if (getProvince(i).getMinX() <= tPosXx - mapBG.getWidth()
                  && getProvince(i).getMaxX() >= tPosXx - mapBG.getWidth()
                  && getProvince(i).getMinY() <= -mapCoords.getPosY() + nPosY
                  && getProvince(i).getMaxY() >= -mapCoords.getPosY() + nPosY
                  && pathContains(i, tPosXx - mapBG.getWidth(), -mapCoords.getPosY() + nPosY)) {
                  return i;
               }
            }
         }
      }

      return -1;
   }

   protected static final boolean setProvinceID_IsMouseOverAProvinceID(int nPosX, int nPosY, int nProvinceID) {
      int tPosX = (int)Math.abs(checkPosOfClickX(mapCoords.getPosX() - nPosX));
      if (getProvince(nProvinceID).getMinX() <= tPosX
         && getProvince(nProvinceID).getMaxX() >= tPosX
         && getProvince(nProvinceID).getMinY() <= -mapCoords.getPosY() + nPosY
         && getProvince(nProvinceID).getMaxY() >= -mapCoords.getPosY() + nPosY
         && pathContains(nProvinceID, tPosX, -mapCoords.getPosY() + nPosY)) {
         return true;
      } else {
         if (Math.abs(checkPosOfClickX(mapCoords.getPosX() - nPosX)) + -MAX_BELOW_ZERO_POINT_X * mapBG.iMapScale / mapScale.getCurrentScale()
               > mapBG.getWidth()
            && getProvince(nProvinceID).getBelowZero()) {
            tPosX = (int)Math.abs(checkPosOfClickX(mapCoords.getPosX() - nPosX));
            if (getProvince(nProvinceID).getMinX() <= tPosX - mapBG.getWidth()
               && getProvince(nProvinceID).getMaxX() >= tPosX - mapBG.getWidth()
               && getProvince(nProvinceID).getMinY() <= -mapCoords.getPosY() + nPosY
               && getProvince(nProvinceID).getMaxY() >= -mapCoords.getPosY() + nPosY
               && pathContains(nProvinceID, tPosX - mapBG.getWidth(), -mapCoords.getPosY() + nPosY)) {
               return true;
            }
         }

         return false;
      }
   }

   public static final int setProvinceID_Point(int nPosX, int nPosY) {
      for (int i = 0; i < iProvincesSize; i++) {
         if (getProvince(i).getMinX() <= nPosX
            && getProvince(i).getMaxX() >= nPosX
            && getProvince(i).getMinY() <= nPosY
            && getProvince(i).getMaxY() >= nPosY
            && pathContains(i, nPosX, nPosY)) {
            return i;
         }
      }

      if (Math.abs(checkPosOfClickX(nPosX)) + -MAX_BELOW_ZERO_POINT_X * mapBG.iMapScale / mapScale.getCurrentScale() > mapBG.getWidth()) {
         int ix = 0;

         for (int tPosX = 0; ix < iProvincesSize; ix++) {
            if (getProvince(ix).getBelowZero()) {
               tPosX = (int)Math.abs(checkPosOfClickX(nPosX));
               if (getProvince(ix).getMinX() <= tPosX - mapBG.getWidth()
                  && getProvince(ix).getMaxX() >= tPosX - mapBG.getWidth()
                  && getProvince(ix).getMinY() <= nPosY
                  && getProvince(ix).getMaxY() >= nPosY
                  && pathContains(ix, tPosX - mapBG.getWidth(), nPosY)) {
                  return ix;
               }
            }
         }
      }

      return -1;
   }

   public static final void updateProvincesInView() {
      if (updateProvincesInView) {
         updateProvincesInView = false;

         for (int i = 0; i < NUM_OF_PROVINCES_IN_VIEW; i++) {
            getProvince(lProvincesInView.get(i)).setDrawProvince(false);
         }

         for (int i = 0; i < NUM_OF_SEA_PROVINCES_IN_VIEW; i++) {
            getProvince(lSeaProvincesInView.get(i)).setDrawProvince(false);
         }

         for (int i = 0; i < NUM_OF_WASTELAND_PROVINCES_IN_VIEW; i++) {
            getProvince(lWastelandProvincesInView.get(i)).setDrawProvince(false);
         }

         for (int i = 0; i < NUM_OF_EXTRA_PROVINCES_IN_VIEW; i++) {
            getProvince(lExtraProvincesInView.get(i)).setDrawProvince(false);
         }

         NUM_OF_PROVINCES_IN_VIEW = 0;
         NUM_OF_SEA_PROVINCES_IN_VIEW = 0;
         NUM_OF_WASTELAND_PROVINCES_IN_VIEW = 0;
         NUM_OF_EXTRA_PROVINCES_IN_VIEW = 0;
         lProvincesInView.clear();
         lSeaProvincesInView.clear();
         lWastelandProvincesInView.clear();
         lExtraProvincesInView.clear();

         for (int i = 0; i < regions.iRegionsSize; i++) {
            Region region = regions.lRegions.get(i);
            if (inViewY(region.getMinY(), region.getMaxY())) {
               if (inViewX(region.getMinX(), region.getMaxX()) || inViewX2(region.getMinX(), region.getMaxX())) {
                  updateDrawRegionProvinces(i);
               } else if (region.getBelowZero() && inViewXBelowZero(region.getMinX(), region.getMaxX())) {
                  updateDrawRegionProvinces(i);
               }
            }
         }

         NUM_OF_PROVINCES_IN_VIEW = lProvincesInView.size();
         NUM_OF_SEA_PROVINCES_IN_VIEW = lSeaProvincesInView.size();
         NUM_OF_WASTELAND_PROVINCES_IN_VIEW = lWastelandProvincesInView.size();
         NUM_OF_EXTRA_PROVINCES_IN_VIEW = lExtraProvincesInView.size();
      }
   }

   private static final void updateDrawRegionProvinces(int iID) {
      Region region = regions.lRegions.get(iID);
      if (inViewY_WholeRegion(region.getMinY(), region.getMaxY())) {
         if (inViewX_WholeRegion(region.getMinX(), region.getMaxX())) {
            for (int i = 0; i < region.getProvincesSize(); i++) {
               getProvince(region.getProvince(i)).setTranslateProvincePosX(mapCoords.getSecondSideOfMap_MoveX() + mapCoords.getPosX());
               updateProvinceInViewLists(region.getProvince(i));
            }

            return;
         }

         if (inViewX_WholeRegion2(region.getMinX(), region.getMaxX())) {
            for (int i = 0; i < region.getProvincesSize(); i++) {
               getProvince(region.getProvince(i)).setTranslateProvincePosX(mapCoords.getPosX());
               updateProvinceInViewLists(region.getProvince(i));
            }

            return;
         }
      }

      for (int i = 0; i < region.getProvincesSize(); i++) {
         updateDrawProvince2(region.getProvince(i));
      }
   }

   protected static final void updateDrawProvince2(int nProvinceID) {
      if (inViewY(nProvinceID)) {
         if (inViewX(nProvinceID)) {
            getProvince(nProvinceID).setTranslateProvincePosX(mapCoords.getSecondSideOfMap_MoveX() + mapCoords.getPosX());
            updateProvinceInViewLists(nProvinceID);
            return;
         }

         if (inViewX2(nProvinceID)) {
            getProvince(nProvinceID).setTranslateProvincePosX(mapCoords.getPosX());
            updateProvinceInViewLists(nProvinceID);
            return;
         }

         if (getProvince(nProvinceID).getBelowZero() && inViewXBelowZero(nProvinceID)) {
            getProvince(nProvinceID).setTranslateProvincePosX(mapBG.getWidth() + mapCoords.getPosX());
            updateProvinceInViewLists(nProvinceID);
            return;
         }
      }

      getProvince(nProvinceID).setDrawProvince(false);
   }

   protected static final void updateProvinceInViewLists(int nProvinceID) {
      getProvince(nProvinceID).setDrawProvince(true);
      if (getProvince(nProvinceID).getWasteland() >= 0) {
         lWastelandProvincesInView.add(nProvinceID);
      } else if (getProvince(nProvinceID).getSeaProvince()) {
         lSeaProvincesInView.add(nProvinceID);
      } else {
         lProvincesInView.add(nProvinceID);
      }
   }

   public static final void updateDrawProvince_CheckExtra(int nProvinceID) {
      if (mapCoords.getSecondSideOfMap() && getProvince(nProvinceID).getMinX() < inViewX_CordsX && getProvince(nProvinceID).getMaxX() > inViewX_CordsX) {
         lExtraProvincesInView.add(nProvinceID);
      }
   }

   public final void clearProvincesInView() {
      for (int i = 0; i < getProvincesSize(); i++) {
         getProvince(i).setDrawProvince(false);
      }

      NUM_OF_PROVINCES_IN_VIEW = 0;
      NUM_OF_SEA_PROVINCES_IN_VIEW = 0;
      NUM_OF_WASTELAND_PROVINCES_IN_VIEW = 0;
      NUM_OF_EXTRA_PROVINCES_IN_VIEW = 0;
      lProvincesInView.clear();
      lSeaProvincesInView.clear();
      lWastelandProvincesInView.clear();
      lExtraProvincesInView.clear();
   }

   public static final int getProvinceInViewID(int iID) {
      try {
         return lProvincesInView.get(iID);
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
         return 0;
      }
   }

   public static final int getExtraProvinceInViewID(int iID) {
      try {
         return lExtraProvincesInView.get(iID);
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
         return 0;
      }
   }

   public static final int getSeaProvinceInViewID(int iID) {
      try {
         return lSeaProvincesInView.get(iID);
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
         return 0;
      }
   }

   public static final int getWastelandProvinceInViewID(int iID) {
      try {
         return lWastelandProvincesInView.get(iID);
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
         return 0;
      }
   }

   public static final void setUpdateProvincesInView(boolean nUpdateProvincesInView) {
      updateProvincesInView = nUpdateProvincesInView;
   }

   public static final void updateInView_CordsXY() {
      inViewY_CordsY = -mapCoords.getPosY();
      inViewY_CordsY_Height = (int)(-mapCoords.getPosY() + CFG.GAME_HEIGHT / mapScale.getCurrentScale());
      inViewX_CordsX = -mapCoords.getPosX();
      inViewX_CordsX_Width = (int)(-mapCoords.getPosX() + CFG.GAME_WIDTH / mapScale.getCurrentScale());
   }

   protected static final boolean inViewY(int nProvinceID) {
      return inViewY_CordsY_Height >= getProvince(nProvinceID).getMinY() && inViewY_CordsY <= getProvince(nProvinceID).getMaxY();
   }

   protected static final boolean inViewX(int nProvinceID) {
      return inViewX_CordsX_Width >= getProvince(nProvinceID).getMinX() + mapCoords.getSecondSideOfMap_MoveX()
         && inViewX_CordsX <= getProvince(nProvinceID).getMaxX() + mapCoords.getSecondSideOfMap_MoveX();
   }

   protected static final boolean inViewX2(int nProvinceID) {
      return inViewX_CordsX_Width >= getProvince(nProvinceID).getMinX() && inViewX_CordsX <= getProvince(nProvinceID).getMaxX();
   }

   protected static final boolean inViewXBelowZero(int nProvinceID) {
      return inViewX_CordsX_Width >= getProvince(nProvinceID).getMinX() + mapBG.getWidth()
         && inViewX_CordsX <= getProvince(nProvinceID).getMaxX() + mapBG.getWidth();
   }

   public static final boolean inViewY(int nMinY, int nMaxY) {
      return inViewY_CordsY_Height >= nMinY && inViewY_CordsY <= nMaxY;
   }

   public static final boolean inViewX(int nMinX, int nMaxX) {
      return inViewX_CordsX_Width >= nMinX + mapCoords.getSecondSideOfMap_MoveX() && inViewX_CordsX <= nMaxX + mapCoords.getSecondSideOfMap_MoveX();
   }

   public static final boolean inViewX2(int nMinX, int nMaxX) {
      return inViewX_CordsX_Width >= nMinX && inViewX_CordsX <= nMaxX;
   }

   protected static final boolean inViewXBelowZero(int nMinX, int nMaxX) {
      return inViewX_CordsX_Width >= nMinX + mapBG.getWidth() && inViewX_CordsX <= nMaxX + mapBG.getWidth();
   }

   protected static final boolean inViewY_WholeRegion(int nMinY, int nMaxY) {
      return nMinY >= inViewY_CordsY && nMaxY <= inViewY_CordsY_Height;
   }

   protected static final boolean inViewX_WholeRegion(int nMinX, int nMaxX) {
      return nMinX + mapCoords.getSecondSideOfMap_MoveX() >= inViewX_CordsX && nMaxX + mapCoords.getSecondSideOfMap_MoveX() <= inViewX_CordsX_Width;
   }

   protected static final boolean inViewX_WholeRegion2(int nMinX, int nMaxX) {
      return nMinX >= inViewX_CordsX && nMaxX <= inViewX_CordsX_Width;
   }

   protected static final String getFogOfWarName(int i) {
      switch (i) {
         case 0:
            return lang.get("Off");
         case 2:
            return lang.get("Discovery");
         default:
            return lang.get("Classic");
      }
   }

   public static final boolean pathContains(int nProvinceID, int nPosX, int nPosY) {
      boolean output = false;
      int i = 0;
      int iSize = getProvince(nProvinceID).getPointsSize();

      for (int j = iSize - 1; i < iSize; j = i++) {
         if (getProvince(nProvinceID).getPointsY(i) > nPosY != getProvince(nProvinceID).getPointsY(j) > nPosY
            && nPosX
               < (getProvince(nProvinceID).getPointsX(j) - getProvince(nProvinceID).getPointsX(i))
                     * (nPosY - getProvince(nProvinceID).getPointsY(i))
                     / (getProvince(nProvinceID).getPointsY(j) - getProvince(nProvinceID).getPointsY(i))
                  + getProvince(nProvinceID).getPointsX(i)) {
            output = !output;
         }
      }

      return output;
   }

   public static final boolean pathContains_Cut(int nProvinceID, int nPosX, int nPosY) {
      boolean output = false;
      if (nPosX >= cutProvinces.get(nProvinceID).iMinX * mapBG.iMapScale
         && nPosX <= cutProvinces.get(nProvinceID).iMaxX * mapBG.iMapScale
         && nPosY >= cutProvinces.get(nProvinceID).iMinY * mapBG.iMapScale
         && nPosY <= cutProvinces.get(nProvinceID).iMaxY * mapBG.iMapScale) {
         int i = 0;
         int iSize = cutProvinces.get(nProvinceID).getPointsSize();

         for (int j = iSize - 1; i < iSize; j = i++) {
            if (cutProvinces.get(nProvinceID).getPointsY(i) > nPosY != cutProvinces.get(nProvinceID).getPointsY(j) > nPosY
               && nPosX
                  < (cutProvinces.get(nProvinceID).getPointsX(j) - cutProvinces.get(nProvinceID).getPointsX(i))
                        * (nPosY - cutProvinces.get(nProvinceID).getPointsY(i))
                        / (cutProvinces.get(nProvinceID).getPointsY(j) - cutProvinces.get(nProvinceID).getPointsY(i))
                     + cutProvinces.get(nProvinceID).getPointsX(i)) {
               output = !output;
            }
         }
      }

      return output;
   }

   public static final float checkPosOfClickX(float nPosX) {
      if (-nPosX > mapBG.getWidth()) {
         while (-nPosX > mapBG.getWidth()) {
            nPosX += mapBG.getWidth();
         }
      } else if (nPosX > 0.0F) {
         while (nPosX > 0.0F) {
            nPosX -= mapBG.getWidth();
         }
      }

      return nPosX;
   }

   public static final void centerToRandomMapPosition() {
      mapScroll.stopScrollingTheMap();
      mapScale.setCurrentScale(MapScale.STANDARD_SCALE);
      Point_XY tempPointToCenterTheMap = CFG.getRandomPointToCenterTheMap();
      mapCoords.setNewPosX(-(tempPointToCenterTheMap.getPosX() * mapBG.iMapScale - CFG.GAME_WIDTH / 2));
      mapCoords.setNewPosY(-(tempPointToCenterTheMap.getPosY() * mapBG.iMapScale - CFG.GAME_HEIGHT / 2));
   }

   public static final void updateCivilizationIdeology(int nCivID, String nCivTag) {
      try {
         for (int i = 0; i < getCivsSize(); i++) {
            if (getCiv(i).getCivTag().equals(nCivTag)) {
               return;
            }
         }

         Game.LoadCivilizationData civData = loadCivilization(nCivTag);
         getCiv(nCivID).updateCivilizationTAG(nCivTag, civData.iR, civData.iG, civData.iB);
         Renderer.addSimpleTaskCivsNames(new Game.SimpleTask("" + nCivID, nCivID) {
            @Override
            public void update() {
               CivilizationRegionsManager.buildCivilizationsRegions_TextOver(this.id);
            }
         });
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public static final void updateCivilizationIdeology_InGame(int nCivID, String nCivTag) {
      try {
         Game.LoadCivilizationData civData = loadCivilization(nCivTag);
         getCiv(nCivID).updateCivilizationTAG(nCivTag, civData.iR, civData.iG, civData.iB);
         Renderer.addSimpleTaskCivsNames(new Game.SimpleTask("" + nCivID, nCivID) {
            @Override
            public void update() {
               CivilizationRegionsManager.buildCivilizationsRegions_TextOver(this.id);
            }
         });
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public static final Province getProvince(int ID) {
      return lProvinces.get(ID);
   }

   public static final ProvinceData getProvinceData(int ID) {
      return lProvincesData.get(ID);
   }

   public static final ProvinceData2 getProvinceData2(int ID) {
      return lProvincesData2.get(ID);
   }

   public static final ProvinceData3 getProvinceData3(int ID) {
      return lProvincesData3.get(ID);
   }

   public static final ProvinceData4 getProvinceData4(int ID) {
      return lProvincesData4.get(ID);
   }

   public static final ProvinceData5 getProvinceData5(int ID) {
      return lProvincesData5.get(ID);
   }

   public static final ProvinceData6 getProvinceData6(int ID) {
      return lProvincesData6.get(ID);
   }

   public static final ProvinceData7 getProvinceData7(int ID) {
      return lProvincesData7.get(ID);
   }

   public static final ProvinceData8 getProvinceData8(int ID) {
      return lProvincesData8.get(ID);
   }

   public static final ProvinceData9 getProvinceData9(int ID) {
      return lProvincesData9.get(ID);
   }

   public static final ProvinceData10 getProvinceData10(int ID) {
      return lProvincesData10.get(ID);
   }

   public static final ProvinceData_Population getProvincePopulation(int ID) {
      return lProvincesPopulation.get(ID);
   }

   public static final int getProvincesSize() {
      return iProvincesSize;
   }

   public static final Civilization getCiv(int i) {
      try {
         return lCivs.get(i);
      } catch (Exception var2) {
         return lCivs.get(0);
      }
   }

   public static final int getCivID(String tag) {
      try {
         for (int i = 1; i < getCivsSize(); i++) {
            if (getCiv(i).getCivTag().equals(tag)) {
               return i;
            }
         }

         for (int ix = 1; ix < getCivsSize(); ix++) {
            if (getCiv(ix).realTag.equals(tag)) {
               return ix;
            }
         }
      } catch (Exception var2) {
      }

      return -1;
   }

   public static final int getCivsSize() {
      return iCivsSize;
   }

   public final ProvinceAnimation getProvinceAnimation_Active_Data() {
      return activeProvince_Animation_Data;
   }

   public static final int getRegionID(int nProvinceID) {
      for (int i = 0; i < regions.iRegionsSize; i++) {
         for (int j = 0; j < regions.lRegions.get(i).getProvincesSize(); j++) {
            if (regions.lRegions.get(i).getProvince(j) == nProvinceID) {
               return i;
            }
         }
      }

      return 0;
   }

   public static final int countContinentProvinces(int nContinentID) {
      int out = 0;

      for (int i = 0; i < getProvincesSize(); i++) {
         if (getProvince(i).getContinent() == nContinentID) {
            out++;
         }
      }

      return out;
   }

   public static int countPlayableProvince() {
      int out = 0;

      for (int i = 0; i < getProvincesSize(); i++) {
         if (!getProvince(i).getSeaProvince() && getProvince(i).getWasteland() < 0) {
            out++;
         }
      }

      return out;
   }

   public static float getLoanValue(int iCivID) {
      float out = GameValues.loan.LOAN_VALUE_MIN;

      for (int i = 0; i < getCiv(iCivID).getNumOfProvinces(); i++) {
         out += getProvince(getCiv(iCivID).getProvinceID(i)).getEconomyWithBonuses();
      }

      return out * GameValues.loan.LOAN_VALUE_BY_ECONOMY;
   }

   public static float getLoanInterestValue(int iCivID) {
      return getLoanValue(iCivID) * (getLoanInterest(iCivID) / 100.0F) * getLoanExpires_Years();
   }

   public static float getLoanInterest(int iCivID) {
      return GameValues.loan.LOAN_DEFAULT_INTEREST
         * (1.0F + (getCiv(iCivID).civBonuses.LoanInterest + GameValues.civRank.CIV_RANK_LOAN_INTEREST[getCiv(iCivID).iCivRankID]) / 100.0F);
   }

   public static int getLoanExpires_Years() {
      return GameValues.loan.LOAN_YEARS;
   }

   public static int getLoanExpires() {
      return GameValues.loan.LOAN_DAYS * getLoanExpires_Years();
   }

   public static float getLoanMonthlyExpenses(float fValue) {
      return fValue / (12 * getLoanExpires_Years());
   }

   public static int getLoanMaxNumber(int iCivID) {
      return GameValues.loan.MAX_NUMBER_OF_LOANS + getCiv(iCivID).civBonuses.MaxNumberOfLoans;
   }

   public static float getInflationReduceCost_Legacy(int iCivID) {
      return getCiv(iCivID).getInflation() * 100.0F * GameValues.inflation.INFLATION_REDUCE_COST_LEGACY_PER_INFLATION;
   }

   public static int getMaxAmountOfGold(int iCivID) {
      Civilization civ = getCiv(iCivID);
      return (int)(
         (
               GameValues.budget.MAX_AMOUNT_OF_GOLD_MIN
                  + (int)civ.civBonuses.MaximumAmountOfGold
                  + (int)(civ.fTotalIncomePerMonth * GameValues.budget.MAX_AMOUNT_OF_GOLD_PER_INCOME)
            )
            * (1.0F + civ.civBonuses.MaximumAmountOfGold_Percentage)
      );
   }

   public static float getResearchCost(int iCivID) {
      return getCiv(iCivID).fResearchPerMonth
         * GameValues.research.RESEARCH_MAINTENANCE_COST
         * (1.0F + GameValues.budget.RESEARCH_LEVEL_COST[getCiv(iCivID).getResearchLevel()]);
   }

   public static float getResearchCost(int iCivID, float value) {
      return value * GameValues.research.RESEARCH_MAINTENANCE_COST * (1.0F + GameValues.budget.RESEARCH_LEVEL_COST[getCiv(iCivID).getResearchLevel()]);
   }

   public static final int getCivilizationsInGame() {
      int out = 0;

      for (int i = 1; i < getCivsSize(); i++) {
         if (getCiv(i).getNumOfProvinces() > 0) {
            out++;
         }
      }

      return out;
   }

   public static Game.ArmyPos findArmy(int iCivID, String key) {
      try {
         if (key != null) {
            Civilization civ = getCiv(iCivID);

            for (int i = civ.getNumOfProvinces() - 1; i >= 0; i--) {
               for (int j = getProvince(civ.getProvinceID(i)).getArmySize() - 1; j >= 0; j--) {
                  if (getProvince(civ.getProvinceID(i)).getArmy(j).civID == iCivID && getProvince(civ.getProvinceID(i)).getArmy(j).key.equals(key)) {
                     return new Game.ArmyPos(civ.getProvinceID(i), j);
                  }
               }
            }
         }
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
      }

      return null;
   }

   public static Game.ArmyPos findArmy_IncludeVassals(int iCivID, String key) {
      try {
         if (key != null) {
            Civilization civ = getCiv(iCivID);

            for (int i = civ.getNumOfProvinces() - 1; i >= 0; i--) {
               for (int j = getProvince(civ.getProvinceID(i)).getArmySize() - 1; j >= 0; j--) {
                  if (getProvince(civ.getProvinceID(i)).getArmy(j).civID == iCivID && getProvince(civ.getProvinceID(i)).getArmy(j).key.equals(key)) {
                     return new Game.ArmyPos(civ.getProvinceID(i), j);
                  }
               }
            }

            for (int a = 0; a < civ.diplomacy.iVassalsSize; a++) {
               for (int i = getCiv(civ.diplomacy.lVassals.get(a).c).getNumOfProvinces() - 1; i >= 0; i--) {
                  for (int jx = getProvince(getCiv(civ.diplomacy.lVassals.get(a).c).getProvinceID(i)).getArmySize() - 1; jx >= 0; jx--) {
                     if (getProvince(getCiv(civ.diplomacy.lVassals.get(a).c).getProvinceID(i)).getArmy(jx).civID == iCivID
                        && getProvince(getCiv(civ.diplomacy.lVassals.get(a).c).getProvinceID(i)).getArmy(jx).key.equals(key)) {
                        return new Game.ArmyPos(getCiv(civ.diplomacy.lVassals.get(a).c).getProvinceID(i), jx);
                     }
                  }
               }
            }
         }
      } catch (Exception var6) {
         CFG.exceptionStack(var6);
      }

      return null;
   }

   public static Game.ArmyPos findArmy_FullCheck(int iCivID, String key) {
      try {
         if (key != null) {
            Civilization civ = getCiv(iCivID);

            for (int i = civ.iArmyPositionSize - 1; i >= 0; i--) {
               for (int j = getProvince(civ.getArmyPosition(i)).getArmySize() - 1; j >= 0; j--) {
                  if (getProvince(civ.getArmyPosition(i)).getArmy(j).civID == iCivID && getProvince(civ.getArmyPosition(i)).getArmy(j).key.equals(key)) {
                     return new Game.ArmyPos(civ.getArmyPosition(i), j);
                  }
               }
            }

            for (int i = civ.getNumOfProvinces() - 1; i >= 0; i--) {
               for (int jx = getProvince(civ.getProvinceID(i)).getArmySize() - 1; jx >= 0; jx--) {
                  if (getProvince(civ.getProvinceID(i)).getArmy(jx).civID == iCivID && getProvince(civ.getProvinceID(i)).getArmy(jx).key.equals(key)) {
                     return new Game.ArmyPos(civ.getProvinceID(i), jx);
                  }
               }
            }

            for (int i = 0; i < getProvincesSize(); i++) {
               for (int jxx = getProvince(i).getArmySize() - 1; jxx >= 0; jxx--) {
                  if (getProvince(i).getArmy(jxx).civID == iCivID && getProvince(i).getArmy(jxx).key.equals(key)) {
                     return new Game.ArmyPos(i, jxx);
                  }
               }
            }
         }
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
      }

      return null;
   }

   public static final void buildProsperity_AverageEconomy() {
      for (int i = 1; i < getCivsSize(); i++) {
         getCiv(i).updateProsperity_AverageEconomy();
      }
   }

   public static String getProsperityLevel(int iCivID) {
      for (int i = 0; i < GameValues.prosperity.PROSPERITY_LEVELS_VALUE.length; i++) {
         if (getCiv(iCivID).fProsperity_AverageEconomy < GameValues.prosperity.PROSPERITY_LEVELS_VALUE[i]) {
            return lang.get(GameValues.prosperity.PROSPERITY_LEVELS[i]);
         }
      }

      return lang.get(GameValues.prosperity.PROSPERITY_LEVELS[GameValues.prosperity.PROSPERITY_LEVELS.length - 1]);
   }

   public static final void updateMapDistance() {
      if (map.isWorldMap(map.getActiveMapID())) {
         mapDistance = new Game.MapDistance() {
            @Override
            public float getManhattanDistance(int provA, int provB) {
               Province provinceA = Game.getProvince(provA);
               Province provinceB = Game.getProvince(provB);
               int xDifference = Math.abs(provinceA.getCenterX_Real() - provinceB.getCenterX_Real());
               int yDifference = Math.abs(provinceA.getCenterY_Real() - provinceB.getCenterY_Real());
               return Math.min(
                  xDifference + yDifference,
                  Math.abs(provinceA.getCenterX_Real() + Game.mapBG.getWidth_Real() - provinceB.getCenterX_Real())
                     + Math.abs(provinceA.getCenterY_Real() - provinceB.getCenterY_Real())
               );
            }

            @Override
            public float getDistanceFromProvinceToProvince(int provA, int provB) {
               Province provinceA = Game.getProvince(provA);
               Province provinceB = Game.getProvince(provB);

               try {
                  return Math.min(
                     Math.min(
                        (float)Math.sqrt(
                           Math.pow(provinceB.getCenterX_Real() + Game.mapBG.getWidth_Real() - provinceA.getCenterX_Real(), 2.0)
                              + Math.pow(provinceB.getCenterY_Real() - provinceA.getCenterY_Real(), 2.0)
                        ),
                        (float)Math.sqrt(
                           Math.pow(provinceB.getCenterX_Real() - (provinceA.getCenterX_Real() + Game.mapBG.getWidth_Real()), 2.0)
                              + Math.pow(provinceB.getCenterY_Real() - provinceA.getCenterY_Real(), 2.0)
                        )
                     ),
                     (float)Math.sqrt(
                        Math.pow(provinceB.getCenterX_Real() - provinceA.getCenterX_Real(), 2.0)
                           + Math.pow(provinceB.getCenterY_Real() - provinceA.getCenterY_Real(), 2.0)
                     )
                  );
               } catch (Exception var6) {
                  CFG.exceptionStack(var6);
                  return Game.iMaxDistance;
               }
            }
         };
      } else {
         mapDistance = new Game.MapDistance() {
            @Override
            public float getManhattanDistance(int provA, int provB) {
               Province provinceA = Game.getProvince(provA);
               Province provinceB = Game.getProvince(provB);
               int xDifference = Math.abs(provinceA.getCenterX_Real() - provinceB.getCenterX_Real());
               int yDifference = Math.abs(provinceA.getCenterY_Real() - provinceB.getCenterY_Real());
               return xDifference + yDifference;
            }

            @Override
            public float getDistanceFromProvinceToProvince(int provA, int provB) {
               Province provinceA = Game.getProvince(provA);
               Province provinceB = Game.getProvince(provB);

               try {
                  return (float)Math.sqrt(
                     Math.pow(provinceB.getCenterX_Real() - provinceA.getCenterX_Real(), 2.0)
                        + Math.pow(provinceB.getCenterY_Real() - provinceA.getCenterY_Real(), 2.0)
                  );
               } catch (Exception var6) {
                  CFG.exceptionStack(var6);
                  return Game.iMaxDistance;
               }
            }
         };
      }
   }

   public static float getManhattanDistance(int provA, int provB) {
      return mapDistance.getManhattanDistance(provA, provB);
   }

   public static final float getManhattanDistance_PercOfMax(int provA, int provB) {
      return getManhattanDistance(provA, provB) / iMaxDistanceManhattan;
   }

   public static final float getDistanceFromProvinceToProvince(int provA, int provB) {
      Province provinceA = getProvince(provA);
      Province provinceB = getProvince(provB);
      return mapDistance.getDistanceFromProvinceToProvince(provA, provB);
   }

   public static final float getDistanceFromProvinceToProvince_Simpler(int provA, int provB) {
      Province provinceA = getProvince(provA);
      Province provinceB = getProvince(provB);

      try {
         return (float)Math.sqrt(
            Math.pow(provinceB.getCenterX_Real() - provinceA.getCenterX_Real(), 2.0) + Math.pow(provinceB.getCenterY_Real() - provinceA.getCenterY_Real(), 2.0)
         );
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
         return iMaxDistance;
      }
   }

   public static final float getDistance_PercOfMax(int provA, int provB) {
      return getDistanceFromProvinceToProvince(provA, provB) / iMaxDistance;
   }

   public static final float getDistance_PercOfMax_Simpler(int provA, int provB) {
      return getDistanceFromProvinceToProvince_Simpler(provA, provB) / iMaxDistance;
   }

   public static final float getDistanceFromAToB_Km(int provA, int provB) {
      return getDistanceFromProvinceToProvince(provA, provB) * map.lMaps.get(map.getActiveMapID()).mapData.DistanceKm;
   }

   public static final void buildDistanceToCapital() {
      for (int i = 0; i < getProvincesSize(); i++) {
         getProvince(i).buildDistanceToCapital();
      }
   }

   public static List<Game.VassalsToRelease> getVassalsToRelease(int civID) {
      List<Game.VassalsToRelease> out = new ArrayList<>();

      for (int i = 0; i < getCiv(civID).getNumOfProvinces(); i++) {
         for (int j = 0; j < getProvince(getCiv(civID).getProvinceID(i)).iCoresSize; j++) {
            if (getCiv(getProvince(getCiv(civID).getProvinceID(i)).getCore(j)).getNumOfProvinces() == 0) {
               boolean found = false;

               for (int k = out.size() - 1; k >= 0; k--) {
                  if (out.get(k).iCivID == getProvince(getCiv(civID).getProvinceID(i)).getCore(j)) {
                     out.get(k).iNumOfProvinces++;
                     found = true;
                     break;
                  }
               }

               if (!found) {
                  out.add(new Game.VassalsToRelease(getProvince(getCiv(civID).getProvinceID(i)).getCore(j), 1));
               }
            }
         }
      }

      return out;
   }

   public static List<Integer> getVassalsToRelease_Provinces(int civID, int releaseCivID) {
      List<Integer> out = new ArrayList<>();

      for (int i = 0; i < getCiv(civID).getNumOfProvinces(); i++) {
         for (int j = 0; j < getProvince(getCiv(civID).getProvinceID(i)).iCoresSize; j++) {
            if (getProvince(getCiv(civID).getProvinceID(i)).getCore(j) == releaseCivID
               && getCiv(getProvince(getCiv(civID).getProvinceID(i)).getCore(j)).getNumOfProvinces() == 0) {
               out.add(getCiv(civID).getProvinceID(i));
               break;
            }
         }
      }

      return out;
   }

   public static List<String> loadSuggestedCivs(int provinceID) {
      List<String> out = new ArrayList<>();
      if (FileManager.loadFile("map/" + map.getFile_ActiveMap_Path() + "suggestedCivilizations/" + provinceID + ".txt").exists()) {
         FileHandle file = FileManager.loadFile("map/" + map.getFile_ActiveMap_Path() + "suggestedCivilizations/" + provinceID + ".txt");
         String sOwners = file.readString();
         String[] sRes = sOwners.split(";");

         for (int a = 0; a < sRes.length; a++) {
            out.add(sRes[a]);
         }
      }

      return out;
   }

   public static void buildSuggestedCivs_ToSmallerMap_OnlyToConvert() {
      for (int i = 0; i < getProvincesSize(); i++) {
         if (getProvince(i).getCitiesSize() > 0
            && FileManager.loadFile("map/" + map.getFile_ActiveMap_Path() + "suggestedCivilizations/" + i + ".txt").exists()) {
            FileHandle file = FileManager.loadFile("map/" + map.getFile_ActiveMap_Path() + "suggestedCivilizations/" + i + ".txt");
            String sOwners = file.readString();
            String[] sRes = sOwners.split(";");

            for (int a = 0; a < sRes.length; a++) {
               FileHandle fileWrite = Gdx.files.local("map/" + map.getFile_ActiveMap_Path() + "SUGGESTED_TEST.txt");
               fileWrite.writeString(
                  getProvince(i).getCity(0).getPosX() / mapBG.iMapScale + ";" + getProvince(i).getCity(0).getPosY() / mapBG.iMapScale + ";" + sRes[a] + ";",
                  true
               );
            }
         }
      }
   }

   public static void generateSuggestedCivs_ToSmallerMap_OnlyToConvert() {
      FileHandle file = FileManager.loadFile("map/" + map.getFile_ActiveMap_Path() + "SUGGESTED_TEST.txt");
      String sOwners = file.readString();
      String[] sRes = sOwners.split(";");

      for (int a = 0; a < sRes.length; a += 3) {
         int tx = Integer.parseInt(sRes[a]) * mapBG.iMapScale;
         int ty = Integer.parseInt(sRes[a + 1]) * mapBG.iMapScale;

         for (int j = 0; j < getProvincesSize(); j++) {
            if (getProvince(j).getMinX() <= tx
               && getProvince(j).getMaxX() >= tx
               && getProvince(j).getMinY() <= ty
               && getProvince(j).getMaxY() >= ty
               && pathContains(j, tx, ty)) {
               if (!FileManager.loadFile("map/" + map.getFile_ActiveMap_Path() + "suggestedCivilizations/" + j + ".txt").exists()) {
                  FileHandle fileWrite = Gdx.files.local("map/" + map.getFile_ActiveMap_Path() + "suggestedCivilizations/" + j + ".txt");
                  fileWrite.writeString(sRes[a + 2] + ";", false);
                  break;
               }

               FileHandle file2 = FileManager.loadFile("map/" + map.getFile_ActiveMap_Path() + "suggestedCivilizations/" + j + ".txt");
               String sOwners2 = file2.readString();
               String[] sRes3 = sOwners2.split(";");
               boolean add = true;

               for (int az = 0; az < sRes3.length; az++) {
                  if (sRes3[az].equals(sRes[a + 2])) {
                     add = false;
                  }
               }

               if (add) {
                  FileHandle fileWrite = Gdx.files.local("map/" + map.getFile_ActiveMap_Path() + "suggestedCivilizations/" + j + ".txt");
                  fileWrite.writeString(sRes[a + 2] + ";", true);
               }
               break;
            }
         }
      }
   }

   public static void updateDrawArmy() {
      for (int i = 0; i < getProvincesSize(); i++) {
         getProvince(i).updateDrawArmy();
      }
   }

   public static final void dispose() {
      map.dispose();
      mapBG.dispose();
   }

   public static void runAgeOfChaos() {
      try {
         if (!AGE_OF_CHAOS_MODE) {
            return;
         }

         if ((Game_Calendar.TURN_ID + 1) % (AGE_OF_CHAOS_YEARS * 365) == 0) {
            if (SPECTATOR_MODE) {
               return;
            }

            AGE_OF_CHAOS_CIVS_LIST.clear();
            List<Integer> civs = new ArrayList<>();
            List<Integer> score = new ArrayList<>();
            double k = 4.0;
            int currentPlayerCapital = Math.max(
               0, getCiv(player.iCivID).getCapitalProvinceID() >= 0 ? getCiv(player.iCivID).getCapitalProvinceID() : getCiv(player.iCivID).getProvinceID(0)
            );

            for (int i = 1; i < getCivsSize(); i++) {
               if (i != player.iCivID && getCiv(i).getNumOfProvinces() > 0 && !ideologiesManager.getIdeology(getCiv(i).getIdeologyID()).REVOLUTIONISTS) {
                  civs.add(i);
                  score.add((int)(10.0 + 1000.0 * Math.exp(-k * getDistance_PercOfMax(currentPlayerCapital, Math.max(0, getCiv(i).getCapitalProvinceID())))));
               }
            }

            if (!civs.isEmpty()) {
               for (int a = 0; a < AGE_OF_CHAOS_CIVS && !civs.isEmpty(); a++) {
                  int totalScore = 0;

                  for (int ix = score.size() - 1; ix >= 0; ix--) {
                     totalScore += score.get(ix);
                  }

                  int randomScore = oR.nextInt(totalScore);
                  int ix = 0;
                  int iSize = score.size();

                  for (int currentScore = 0; ix < iSize; ix++) {
                     currentScore += score.get(ix);
                     if (currentScore >= randomScore) {
                        AGE_OF_CHAOS_CIVS_LIST.add(civs.get(ix));
                        civs.remove(ix);
                        score.remove(ix);
                        break;
                     }
                  }
               }

               if (!AGE_OF_CHAOS_CIVS_LIST.isEmpty()) {
                  menuManager.setViewIDWithoutAnimation(View.AGE_OF_CHAOS);
               }
            }
         }
      } catch (Exception var11) {
         CFG.exceptionStack(var11);
      }
   }

   public static class ArmyPos {
      public int iProvinceID;
      public int iID;

      public ArmyPos(int iProvinceID, int iID) {
         this.iProvinceID = iProvinceID;
         this.iID = iID;
      }
   }

   public static class FlagData {
      public int NumOfFlags;
   }

   public static class HoveredArmy {
      public String key;
      public int iCivID;
      public int iProvinceID = -1;
      public int iArmyID;
   }

   public static class HoveredBattle {
      public String key;
      public int iProvinceID = -1;
   }

   public static class LoadCivilizationData {
      public String Tag;
      public String Name = null;
      public int iR;
      public int iG;
      public int iB;
      public int ReligionID;
      public int GroupID = 0;
      public String Wiki;
   }

   public interface MapDistance {
      float getManhattanDistance(int var1, int var2);

      float getDistanceFromProvinceToProvince(int var1, int var2);
   }

   public static class SimpleTask {
      public String taskKey;
      public int id;
      public int id2;

      public SimpleTask(String taskKey) {
         this.taskKey = taskKey;
      }

      public SimpleTask(String taskKey, int id) {
         this.taskKey = taskKey;
         this.id = id;
      }

      public SimpleTask(String taskKey, int id, int id2) {
         this.taskKey = taskKey;
         this.id = id;
         this.id2 = id2;
      }

      public void update() {
      }

      @Override
      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (!(o instanceof Game.SimpleTask)) {
            return false;
         } else {
            Game.SimpleTask that = (Game.SimpleTask)o;
            return Objects.equals(this.taskKey, that.taskKey);
         }
      }

      @Override
      public int hashCode() {
         return 0;
      }
   }

   public static class VassalsToRelease {
      public int iCivID;
      public int iNumOfProvinces = 0;

      public VassalsToRelease(int iCivID, int iNumOfProvinces) {
         this.iCivID = iCivID;
         this.iNumOfProvinces = iNumOfProvinces;
      }
   }
}
