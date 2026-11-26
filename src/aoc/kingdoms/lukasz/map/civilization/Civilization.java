package aoc.kingdoms.lukasz.map.civilization;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.FileManager;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.GlyphLayout_Game;
import aoc.kingdoms.lukasz.jakowski.AI.AI_Civ;
import aoc.kingdoms.lukasz.jakowski.AI.AI_CivDiplomacy;
import aoc.kingdoms.lukasz.jakowski.AI.AI_Civ_CreateNewArmy;
import aoc.kingdoms.lukasz.jakowski.AI.Army.AI_Merge;
import aoc.kingdoms.lukasz.jakowski.AI.Army.AI_MoveNoConnection;
import aoc.kingdoms.lukasz.jakowski.AI.Technology.AI_UnlockedTechnology;
import aoc.kingdoms.lukasz.jakowski.Missions.Mission;
import aoc.kingdoms.lukasz.jakowski.Player.Notification.Notification;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.jakowski.SaveLoad.SaveGameManager;
import aoc.kingdoms.lukasz.map.AdvantagesManager;
import aoc.kingdoms.lukasz.map.BuildingsManager;
import aoc.kingdoms.lukasz.map.ColonizationManager;
import aoc.kingdoms.lukasz.map.LawsManager;
import aoc.kingdoms.lukasz.map.LegacyManager;
import aoc.kingdoms.lukasz.map.Loan;
import aoc.kingdoms.lukasz.map.ResourcesManager;
import aoc.kingdoms.lukasz.map.Ruler;
import aoc.kingdoms.lukasz.map.RulersManager;
import aoc.kingdoms.lukasz.map.SiegeManager;
import aoc.kingdoms.lukasz.map.advisors.Advisor;
import aoc.kingdoms.lukasz.map.allianceHRE.HREManager;
import aoc.kingdoms.lukasz.map.army.ArmyDivision;
import aoc.kingdoms.lukasz.map.army.ArmyGeneral;
import aoc.kingdoms.lukasz.map.army.ArmyManager;
import aoc.kingdoms.lukasz.map.army.ArmyPosition;
import aoc.kingdoms.lukasz.map.army.ArmyRecruit;
import aoc.kingdoms.lukasz.map.army.ArmyRegiment;
import aoc.kingdoms.lukasz.map.civilization.data.Civilization_ArmiesWithoutGenerals;
import aoc.kingdoms.lukasz.map.civilization.data.Civilization_ConvertReligion;
import aoc.kingdoms.lukasz.map.civilization.data.Civilization_Cores;
import aoc.kingdoms.lukasz.map.civilization.save.CivData;
import aoc.kingdoms.lukasz.map.civilization.save.CivData3;
import aoc.kingdoms.lukasz.map.civilization.save.CivData4;
import aoc.kingdoms.lukasz.map.civilization.stats.CivilizationEventsData;
import aoc.kingdoms.lukasz.map.civilization.stats.CivilizationEventsData2;
import aoc.kingdoms.lukasz.map.civilization.stats.CivilizationEventsData3;
import aoc.kingdoms.lukasz.map.diplomacy.Diplomacy;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import aoc.kingdoms.lukasz.map.diplomacy.Vassal;
import aoc.kingdoms.lukasz.map.map.MapScenarios;
import aoc.kingdoms.lukasz.map.moveUnits.MoveUnits;
import aoc.kingdoms.lukasz.map.moveUnits.MoveUnits_Player;
import aoc.kingdoms.lukasz.map.province.Province;
import aoc.kingdoms.lukasz.map.province.ProvinceInvest;
import aoc.kingdoms.lukasz.map.technology.TechnologyResearch;
import aoc.kingdoms.lukasz.map.technology.TechnologyTree;
import aoc.kingdoms.lukasz.menusInGame.InGame_Generals;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_Court;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_CourtOptions;
import aoc.kingdoms.lukasz.menusInGame.Info.InGame_Info;
import aoc.kingdoms.lukasz.menusInGame.Technology.InGame_TechnologyChoose;
import aoc.kingdoms.lukasz.menusInGame.Technology.InGame_TechnologyTree;
import aoc.kingdoms.lukasz.textures.Image;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Civilization {
   public int iCivID;
   public CivData civData = new CivData();
   public CivData3 civData3 = new CivData3();
   public CivData4 civData4 = new CivData4();
   public String sCivName;
   public String realTag;
   public Ruler ruler;
   public CivilizationEventsData eventsData = new CivilizationEventsData();
   public CivilizationEventsData2 eventsData2 = new CivilizationEventsData2();
   public CivilizationEventsData3 eventsData3 = new CivilizationEventsData3();
   public CivilizationEventsData_Variables eventsDataVariables = new CivilizationEventsData_Variables();
   public CivilizationGoldenAge goldenAge = new CivilizationGoldenAge();
   public int eventProvinceID = -1;
   public float fGold = 0.0F;
   public float fLegacy = 0.0F;
   public double fManpower = 0.0;
   public float fDiplomacy = 0.0F;
   public Diplomacy diplomacy = new Diplomacy();
   private int iR;
   private int iG;
   private int iB;
   private float fR;
   private float fG;
   private float fB;
   public int iCivRankID = 0;
   public int iCivRankPosition = 999;
   public float iCivRankScore = 0.0F;
   public float iCivRankScoreArmy = 0.0F;
   public int iIdeologyID = 0;
   public int iGroupID = 0;
   public int iRegiments = 0;
   public int iRegimentsLimit = 0;
   public int iUniqueResources = 0;
   public boolean haveAccessSea = false;
   public boolean canAccessSea = false;
   public boolean canColonize = false;
   public boolean canBuildNuke = false;
   public List<ProvinceInvest> nukesDaysLeft = new ArrayList<>();
   public int iNukesSize = 0;
   private List<CivilizationLegacy> legacies = new ArrayList<>();
   private int iLegaciesSize = 0;
   public List<MoveUnits> lMoveUnits = Collections.synchronizedList(new ArrayList<>());
   private int iMoveUnitsSize;
   public ArrayList<ArrayList<ArmyRecruit>> lArmyRecruit = new ArrayList<>();
   public int iArmyRecruitSize;
   public int iArmyRecruitSize_Total;
   public ConcurrentHashMap<String, Integer> lCreateNewArmy = new ConcurrentHashMap<>();
   private List<ArmyGeneral> lGeneralsNotAssigned = Collections.synchronizedList(new ArrayList<>());
   public int iGeneralsSize = 0;
   public boolean isPlayerAlly = false;
   private int iNumOfProvinces;
   public float civStability_LostFrom100 = 0.0F;
   private List<Integer> lProvinces = new ArrayList<>();
   public Advisor advisorAdministration = new Advisor();
   public Advisor advisorEconomy = new Advisor();
   public Advisor advisorTechnology = new Advisor();
   public Advisor advisorMilitary = new Advisor();
   public int iAlternativeTechResearch = -1;
   public List<Mission> lMissions = new ArrayList<>();
   public int iMissionsSize = 0;
   public CivilizationsNeighbors civNeighbors = new CivilizationsNeighbors();
   public AI_Civ aiCiv = new AI_Civ();
   public AI_CivDiplomacy aiCivDiplomacy = new AI_CivDiplomacy();
   public AI_Civ_CreateNewArmy aiCivCreateNewArmy = new AI_Civ_CreateNewArmy();
   public AI_Merge aiMerge = new AI_Merge();
   public int aiUpdateID = 0;
   public float aiScore;
   public AI_MoveNoConnection noConnectionMoveUnits = new AI_MoveNoConnection();
   public List<String> inBattles = new ArrayList<>();
   public List<Integer> underSiege = new ArrayList<>();
   public int underSiegeSize = 0;
   public List<Integer> occupiedProvinces = new ArrayList<>();
   public int occupiedProvincesSize = 0;
   public Civilization_ConvertReligion convertReligion = new Civilization_ConvertReligion();
   public Civilization_Cores civilizationCores = new Civilization_Cores();
   public Civilization_ArmiesWithoutGenerals armiesWithoutGenerals = new Civilization_ArmiesWithoutGenerals();
   public float fTotalIncomePerMonth = 0.0F;
   public float fTotalExpensesPerMonth = 0.0F;
   public float fArmyMaintenance = 0.0F;
   public float fIncomeLord = 0.0F;
   public float fExpenseVassal = 0.0F;
   public float fProsperity_AverageEconomy = 0.0F;
   public float fLoansCost = 0.0F;
   public List<Loan> loans = new ArrayList<>();
   public int iLoansSize = 0;
   public float fLegacyPerMonth = 0.0F;
   public double fManpowerMax = 0.0;
   public double fManpowerPerMonth = 0.0;
   public double fManpowerMax_ToLord = 0.0;
   public float fResearchPerMonth = 0.0F;
   public float fDiplomacyMax = 0.0F;
   private float fDiplomacyPerMonth = 0.0F;
   public List<Integer> inAlliance = new ArrayList<>();
   public int inAllianceSize = 0;
   public CivilizationBonuses civBonuses = new CivilizationBonuses();
   public List<CivilizationBonuses> civBonusesTemporary = new ArrayList<>();
   public int iCivBonusesTemporarySize = 0;
   public List<Integer> colonizationProvince = new ArrayList<>();
   public int iColonizationProvinceSize = 0;
   public List<Boolean> lTechResearched = new ArrayList<>();
   public List<TechnologyResearch> lResearching = new ArrayList<>();
   public List<CivilizationLegacy> lAdvantages = new ArrayList<>();
   public int iAdvantagesSize = 0;
   private List<TechnologyTree.Building> unlockedBuildings = new ArrayList<>();
   private List<TechnologyTree.Unit> unlockedUnits = new ArrayList<>();
   public List<TechnologyTree.Unit> unitsBest = new ArrayList<>();
   public List<TechnologyTree.Unit> unitsBest_FirstLine = new ArrayList<>();
   public List<TechnologyTree.Unit> unitsBest_Flank = new ArrayList<>();
   public List<TechnologyTree.Unit> unitsBest_Support = new ArrayList<>();
   public List<TechnologyTree.Unit> unitsBest_Siege = new ArrayList<>();
   public int unitsBestSize = 0;
   public int unitsBest_FirstLineSize = 0;
   public int unitsBest_FlankSize = 0;
   public int unitsBest_SupportSize = 0;
   public int unitsBest_SiegeSize = 0;
   public List<Integer> laws = new ArrayList<>();
   public List<Integer> goodsProduced = new ArrayList<>();
   public int largestProducerNum = 0;
   public List<String> sTagsCanForm = new ArrayList<>();
   public List<ArmyPosition> armyPosition = Collections.synchronizedList(new ArrayList<>());
   public int iArmyPositionSize = 0;
   public int iArmyImgID = 0;
   public int iCapitalNameWidth = 0;
   public int iCapitalNameHeight = 0;
   public MapScenarios.ScenarioCivData scenarioEditorData = new MapScenarios.ScenarioCivData();
   private Image civFlag = null;
   public List<String> lCivNameChars;
   public int iCivNameLength = 0;
   public String sCivName_UpperCase;
   public int iCivNameWidth;
   public int iCivNameHeight;
   private List<CivilizationRegion> lCivRegions = new ArrayList<>();
   private int iCivRegionsSize;
   private boolean updateRegions = false;
   public float fTotalProvincesValue = 1.0F;
   public boolean warView_ParticipatesInWar = false;
   public boolean warView_IsAggressor = false;
   public boolean isFlagNearest = false;
   public Color civColor = new Color(1.0F, 1.0F, 1.0F, 1.0F);
   public Color civColorMap = new Color(1.0F, 1.0F, 1.0F, 1.0F);
   public Color civColorFog = new Color(1.0F, 1.0F, 1.0F, 1.0F);

   public float getBalance() {
      return this.fTotalIncomePerMonth + this.civBonuses.MonthlyIncome - this.fTotalExpensesPerMonth;
   }

   public Civilization(int iCivID, String sCivTag, int iPuppetOfCivID, int iR, int iG, int iB, int iCapitalProvinceID, int iReligionID, int iGroupID) {
      this.civData.t = sCivTag;
      this.realTag = Game.ideologiesManager.getRealTag(sCivTag);
      this.setCivName(Game.lang.getCiv(sCivTag));
      this.iCivID = iCivID;
      this.civData.p = iPuppetOfCivID;
      this.civData.c = iCapitalProvinceID;
      this.setR(iR);
      this.setG(iG);
      this.setB(iB);
      this.civData.r = iReligionID;
      this.iGroupID = iGroupID;
      this.iArmyImgID = Images.army;
      this.isPlayerAlly = false;
      this.loadFlag();
      this.updateCivilizationTAG();
      this.buildLaws();
   }

   public Civilization(
      int iCivID, String sCivTag, int iPuppetOfCivID, int iR, int iG, int iB, int iCapitalProvinceID, int iReligionID, int iGroupID, boolean loadScenario
   ) {
      this.civData.t = sCivTag;
      this.realTag = Game.ideologiesManager.getRealTag(sCivTag);
      this.iCivID = iCivID;
      this.civData.p = iPuppetOfCivID;
      this.civData.c = iCapitalProvinceID;
      this.setR(iR);
      this.setG(iG);
      this.setB(iB);
      this.civData.r = iReligionID;
      this.iGroupID = iGroupID;
      this.iArmyImgID = Images.army;
      this.isPlayerAlly = false;
   }

   public void loadScenario_A() {
      this.setCivName(Game.lang.getCiv(this.getCivTag()));
      this.updateCivilizationTAG();
      this.buildLaws();
   }

   public void loadScenario_B() {
      this.loadFlag();
   }

   public final void addProvince_Just(int nProvinceID) {
      for (int i = 0; i < this.iNumOfProvinces; i++) {
         if (this.lProvinces.get(i) == nProvinceID) {
            return;
         }
      }

      this.lProvinces.add(nProvinceID);
      this.updateNumOfProvinces();
   }

   public final void addProvince_LoadScenario(int nProvinceID) {
      for (int i = 0; i < this.iNumOfProvinces; i++) {
         if (this.lProvinces.get(i) == nProvinceID) {
            return;
         }
      }

      this.lProvinces.add(nProvinceID);
      this.iNumOfProvinces = this.lProvinces.size();
   }

   public final void addProvince(int nProvinceID) {
      for (int i = 0; i < this.iNumOfProvinces; i++) {
         if (this.lProvinces.get(i) == nProvinceID) {
            return;
         }
      }

      this.lProvinces.add(nProvinceID);
      this.updateNumOfProvinces();
      Game.getProvince(nProvinceID).setCivRegionID(-1);
      this.convertReligion.checkProvince(nProvinceID, this.getCivID());
      this.civilizationCores.checkProvince_Full(nProvinceID, this.getCivID());
   }

   public final void removeProvince(int nProvinceID) {
      for (int i = 0; i < this.iNumOfProvinces; i++) {
         if (this.lProvinces.get(i) == nProvinceID) {
            this.lProvinces.remove(i);
            this.updateNumOfProvinces();
            break;
         }
      }

      Game.getProvince(nProvinceID).setCivRegionID(-1);
      this.convertReligion.removeProvince(nProvinceID);
      this.civilizationCores.removeProvince(nProvinceID);
   }

   public final void updateNumOfProvinces() {
      this.iNumOfProvinces = this.lProvinces.size();
      if (this.iCivRankID != CivilizationRanking.getCivilizationRank(this.iNumOfProvinces)) {
         this.iCivRankID = CivilizationRanking.getCivilizationRank(this.iNumOfProvinces);
         Game.gameThreadTurns.addCivUpdateMaxManpower(this.getCivID());
         Game.gameThread.addCivUpdateLegacyPerMonth(this.getCivID());
         Game.gameThread.addCivUpdateResearchPerMonth(this.getCivID());
         Game.gameThread.addCivUpdateTotalIncomePerMonth(this.iCivID);
      }
   }

   public final void addArmyPosition(int nProvinceID, String key) {
      try {
         for (int i = this.iArmyPositionSize - 1; i >= 0; i--) {
            if (this.armyPosition.get(i).provinceID == nProvinceID && this.armyPosition.get(i).key.equals(key)) {
               return;
            }
         }

         this.armyPosition.add(new ArmyPosition(nProvinceID, key));
         this.iArmyPositionSize = this.armyPosition.size();
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   public final void removeArmyPosition(int nProvinceID, String key) {
      try {
         for (int i = 0; i < this.iArmyPositionSize; i++) {
            if (this.armyPosition.get(i).provinceID == nProvinceID && this.armyPosition.get(i).key.equals(key)) {
               this.armyPosition.remove(i);
               this.iArmyPositionSize = this.armyPosition.size();
               return;
            }
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   public void updateArmyPosition(String key, int nProvinceID) {
      try {
         for (int i = 0; i < this.iArmyPositionSize; i++) {
            if (this.armyPosition.get(i).key.equals(key)) {
               this.armyPosition.get(i).provinceID = nProvinceID;
               return;
            }
         }

         this.armyPosition.add(new ArmyPosition(nProvinceID, key));
         this.iArmyPositionSize = this.armyPosition.size();
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   public int getArmyPosition(int i) {
      try {
         return this.armyPosition.get(i).provinceID;
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
         return -1;
      }
   }

   public String getArmyPositionKey(int i) {
      try {
         return this.armyPosition.get(i).key;
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
         return "";
      }
   }

   public final void clearArmyPosition() {
      this.armyPosition.clear();
      this.iArmyPositionSize = 0;
   }

   public boolean armyExists(String key) {
      try {
         for (int i = 0; i < this.iArmyPositionSize; i++) {
            if (this.armyPosition.get(i).key.equals(key)) {
               return true;
            }
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }

      return false;
   }

   public final boolean loadFlag() {
      if (FileManager.loadFile("gfx/flagsXH/" + this.getCivTag() + ".png").exists()) {
         this.civFlag = new Image(new Texture(FileManager.loadFile("gfx/flagsXH/" + this.getCivTag() + ".png"), Format.RGB888, false), TextureFilter.Linear);
      } else if (FileManager.loadFile("gfx/flagsXH/" + this.realTag + ".png").exists()) {
         this.civFlag = new Image(new Texture(FileManager.loadFile("gfx/flagsXH/" + this.realTag + ".png"), Format.RGB888, false), TextureFilter.Linear);
      } else if (FileManager.loadFile("gfx/flagsH/" + this.getCivTag() + ".png").exists()) {
         this.civFlag = new Image(new Texture(FileManager.loadFile("gfx/flagsH/" + this.getCivTag() + ".png"), Format.RGB888, false), TextureFilter.Linear);
      } else if (FileManager.loadFile("gfx/flagsH/" + this.realTag + ".png").exists()) {
         this.civFlag = new Image(new Texture(FileManager.loadFile("gfx/flagsH/" + this.realTag + ".png"), Format.RGB888, false), TextureFilter.Linear);
      } else if (FileManager.loadFile("gfx/flags/" + this.getCivTag() + ".png").exists()) {
         this.civFlag = new Image(new Texture(FileManager.loadFile("gfx/flags/" + this.getCivTag() + ".png"), Format.RGB888, false), TextureFilter.Nearest);
         this.isFlagNearest = true;
      } else if (FileManager.loadFile("gfx/flags/" + this.realTag + ".png").exists()) {
         this.civFlag = new Image(new Texture(FileManager.loadFile("gfx/flags/" + this.realTag + ".png"), Format.RGB888, false), TextureFilter.Nearest);
         this.isFlagNearest = true;
      } else if (FileManager.loadFile("mods/GameCivs/gfx/flagsH/" + this.getCivTag() + ".png").exists()) {
         this.civFlag = new Image(
            new Texture(FileManager.loadFile("mods/GameCivs/gfx/flagsH/" + this.getCivTag() + ".png"), Format.RGB888, false), TextureFilter.Linear
         );
      } else if (FileManager.loadFile("mods/GameCivs/gfx/flagsH/" + this.realTag + ".png").exists()) {
         this.civFlag = new Image(
            new Texture(FileManager.loadFile("mods/GameCivs/gfx/flagsH/" + this.realTag + ".png"), Format.RGB888, false), TextureFilter.Linear
         );
      } else {
         this.civFlag = new Image(new Texture(FileManager.loadFile("gfx/flagsXH/ran.png"), Format.RGB888, false), TextureFilter.Nearest);
         this.isFlagNearest = true;
      }

      return true;
   }

   public final Image getFlag() {
      return this.civFlag == null ? ImageManager.getImage(Images.randomCivilizationFlag) : this.civFlag;
   }

   public final void disposeFlag() {
      if (this.civFlag != null) {
         this.civFlag.getTexture().dispose();
         this.civFlag = null;
      }
   }

   public final void setFlag(Image nFlag) {
      try {
         this.civFlag.dispose();
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }

      this.civFlag = nFlag;
   }

   public final void createCivilizationRegion(int nProvinceID) {
      this.lCivRegions.add(new CivilizationRegion(nProvinceID, this.iCivRegionsSize));
      this.iCivRegionsSize = this.lCivRegions.size();

      try {
         for (int i = 0; i < this.getNumOfProvinces(); i++) {
            Game.getProvince(this.getProvinceID(i)).wasCivRegion = false;
         }
      } catch (Exception var3) {
      }

      Game.getProvince(nProvinceID).wasCivRegion = true;
      this.buildCivilizationRegion(nProvinceID, this.iCivRegionsSize - 1);
   }

   private final void buildCivilizationRegion(int nProvinceID, int nCivRegionID) {
      for (int i = 0; i < Game.getProvince(nProvinceID).getNeighboringProvincesSize(); i++) {
         int neighborProvinceID = Game.getProvince(nProvinceID).getNeighboringProvinces(i);
         Province neighProvince = Game.getProvince(neighborProvinceID);
         if (neighProvince.getCivID() == this.iCivID && !neighProvince.wasCivRegion && neighProvince.getCivRegionID() < 0) {
            neighProvince.wasCivRegion = true;
            this.lCivRegions.get(nCivRegionID).addProvince(neighborProvinceID);
            neighProvince.setCivRegionID(nCivRegionID);
            CivilizationRegionsManager.updateIsProvinceAssigned(neighborProvinceID, true);
            this.buildCivilizationRegion(neighborProvinceID, nCivRegionID);
         }
      }
   }

   protected final boolean civRegionsContainsProvince(int nProvinceID) {
      for (int i = 0; i < this.iCivRegionsSize; i++) {
         if (this.lCivRegions.get(i).containsProvince(nProvinceID)) {
            return true;
         }
      }

      return false;
   }

   public final void clearCivRegions() {
      for (int i = 0; i < this.getNumOfProvinces(); i++) {
         Game.getProvince(this.getProvinceID(i)).setCivRegionID(-1);
      }

      this.lCivRegions.clear();
      this.iCivRegionsSize = 0;
   }

   public final void clearCivRegions_Just() {
      this.lCivRegions.clear();
      this.iCivRegionsSize = 0;
   }

   public final CivilizationRegion getCivRegion(int i) {
      return this.lCivRegions.get(i);
   }

   public final int getCivRegionsSize() {
      return this.iCivRegionsSize;
   }

   public final void setCivName(String sCivName) {
      if (sCivName.length() <= 0) {
         sCivName = "AB";
      }

      this.sCivName = sCivName;
      GlyphLayout_Game glyphLayout = new GlyphLayout_Game();
      glyphLayout.setText(Renderer.fontMain.get(0), sCivName);
      this.iCivNameWidth = (int)glyphLayout.width;
      this.iCivNameHeight = (int)glyphLayout.height;
      this.lCivNameChars = new ArrayList<>();
      this.sCivName_UpperCase = sCivName.toUpperCase();

      for (int i = 0; i < this.sCivName.length(); i++) {
         this.lCivNameChars.add("" + this.sCivName_UpperCase.charAt(i));
      }

      this.iCivNameLength = this.lCivNameChars.size();
      glyphLayout.setText(Renderer.fontMain.get(4), this.sCivName);
      this.iCapitalNameWidth = (int)glyphLayout.width;
      this.iCapitalNameHeight = (int)glyphLayout.height;
   }

   public final int getCivNameWidth() {
      return this.iCivNameWidth;
   }

   public final int getCivNameHeight() {
      return this.iCivNameHeight;
   }

   public final String getCivNameCharacter(int id) {
      return this.lCivNameChars.get(id);
   }

   public final int getCivNameLength() {
      return this.iCivNameLength;
   }

   public final boolean getUpdateRegions() {
      return this.updateRegions;
   }

   public final void setUpdateRegions(boolean updateRegions) {
      this.updateRegions = updateRegions;
   }

   public final int getIdeologyID() {
      return this.iIdeologyID;
   }

   public final void setIdeologyID(int iIdeologyID) {
      this.iIdeologyID = iIdeologyID;
   }

   public final int getReligionID() {
      return this.civData.r;
   }

   public final void setReligionID(int iReligionID) {
      this.civData.r = iReligionID;
   }

   public final void setReligionID_UpdateBonuses(int nReligionID) {
      Game.religionManager.updateCivBonuses(this.iCivID, this.civData.r, -1, true);
      this.civData.r = nReligionID;
      Game.religionManager.updateCivBonuses(this.iCivID, this.civData.r, 1, false);
   }

   public final void setR(int nR) {
      this.iR = nR;
      this.fR = nR / 255.0F;
      this.civColor.r = this.fR;
      this.civColorMap.r = this.fR;
      this.civColorFog.r = Math.max(0.0F, this.fR - GameValues.fog.PROVINCE_FOG_COLOR_DIFFERENCE);
   }

   public final void setG(int nG) {
      this.iG = nG;
      this.fG = nG / 255.0F;
      this.civColor.g = this.fG;
      this.civColorMap.g = this.fG;
      this.civColorFog.g = Math.max(0.0F, this.fG - GameValues.fog.PROVINCE_FOG_COLOR_DIFFERENCE);
   }

   public final void setB(int nB) {
      this.iB = nB;
      this.fB = nB / 255.0F;
      this.civColor.b = this.fB;
      this.civColorMap.b = this.fB;
      this.civColorFog.b = Math.max(0.0F, this.fB - GameValues.fog.PROVINCE_FOG_COLOR_DIFFERENCE);
      this.updateVassalsColors();
   }

   public final void updateColorMap() {
      if (this.iCivID == this.getPuppetOfCivID()) {
         this.civColorMap = this.civColor;
      } else {
         this.civColorMap = CFG.getColorMixed_2(this.civColor, Game.getCiv(this.getPuppetOfCivID()).civColor);
      }

      this.civColorFog.r = Math.max(0.0F, this.civColorMap.r - GameValues.fog.PROVINCE_FOG_COLOR_DIFFERENCE);
      this.civColorFog.g = Math.max(0.0F, this.civColorMap.g - GameValues.fog.PROVINCE_FOG_COLOR_DIFFERENCE);
      this.civColorFog.b = Math.max(0.0F, this.civColorMap.b - GameValues.fog.PROVINCE_FOG_COLOR_DIFFERENCE);
   }

   public final void updateVassalsColors() {
      for (int i = 0; i < this.diplomacy.iVassalsSize; i++) {
         Game.getCiv(this.diplomacy.lVassals.get(i).c).updateColorMap();
      }
   }

   public final int getR_Int() {
      return this.iR;
   }

   public final int getG_Int() {
      return this.iG;
   }

   public final int getB_Int() {
      return this.iB;
   }

   public final float getR() {
      return this.fR;
   }

   public final float getG() {
      return this.fG;
   }

   public final float getB() {
      return this.fB;
   }

   public final Color getColor(float fAlpha) {
      return this.getRGB(fAlpha);
   }

   public final Color getRGB(float fAlpha) {
      return new Color(this.getR(), this.getG(), this.getB(), fAlpha);
   }

   public final String getCivName() {
      return this.sCivName;
   }

   public final int getNumOfProvinces() {
      return this.iNumOfProvinces;
   }

   public final int getProvinceID(int i) {
      try {
         return this.lProvinces.get(i);
      } catch (Exception var3) {
         return -1;
      }
   }

   public final List<Integer> getProvinces() {
      return this.lProvinces;
   }

   public final int getCapitalProvinceID() {
      return this.civData.c;
   }

   public final void moveCapital_ToLargestProvince() {
      if (this.getNumOfProvinces() > 0) {
         int tBestID = 0;

         for (int i = this.getNumOfProvinces() - 1; i > 0; i--) {
            if (Game.getProvince(this.getProvinceID(tBestID)).getPopulationTotal() < Game.getProvince(this.getProvinceID(i)).getPopulationTotal()) {
               tBestID = i;
            }
         }

         this.setCapitalProvinceID(this.getProvinceID(tBestID));
      }
   }

   public final boolean moveCapital(int toProvinceID) {
      if (toProvinceID >= 0
         && toProvinceID < Game.getProvincesSize()
         && toProvinceID != this.getCapitalProvinceID()
         && !Game.getProvince(toProvinceID).isOccupied()
         && this.fGold >= GameValues.capital.MOVE_CAPITAL_COST) {
         this.fGold = this.fGold - GameValues.capital.MOVE_CAPITAL_COST;
         this.setCapitalProvinceID(toProvinceID);
         return true;
      } else {
         return false;
      }
   }

   public final void setCapitalProvinceID(int nCapitalProvinceID) {
      if (this.civData.c >= 0 && Game.getProvince(this.civData.c).getCivID() == this.iCivID) {
         Game.getProvince(this.civData.c).setIsCapital(false);
         Game.getProvince(this.civData.c).updateBuildingLimit();
         Game.getProvince(this.civData.c).updateInfrastructureMax();
      }

      this.civData.c = nCapitalProvinceID;
      if (Game.getProvince(this.civData.c).getCivID() == this.iCivID) {
         Game.getProvince(this.civData.c).setIsCapital(true);
         Game.getProvince(this.civData.c).updateBuildingLimit();
         Game.getProvince(this.civData.c).updateInfrastructureMax();
         Game.getProvince(this.civData.c).setDrawCities(true);
         Game.buildDistanceToCapital();
      }
   }

   public final void setCivTag(String sCivTag) {
      this.civData.t = sCivTag;
      this.realTag = Game.ideologiesManager.getRealTag(sCivTag);
      this.setCivName(Game.lang.getCiv(sCivTag));
   }

   public final void updateCivilizationTAG(String nCivTag, int iR, int iG, int iB) {
      this.setCivTag(nCivTag);
      this.setR(iR);
      this.setG(iG);
      this.setB(iB);
      this.updateCivilizationTAG();
      this.loadFlag();
      this.updateColorMap();
   }

   public final void updateCivilizationTAG() {
      this.setIdeologyID(Game.ideologiesManager.getIdeologyID(this.getCivTag()));
   }

   public final String getCivTag() {
      return this.civData.t;
   }

   public int getCivID() {
      return this.iCivID;
   }

   public final void setCivID(int iCivID) {
      this.iCivID = iCivID;
      this.civData.p = iCivID;
   }

   public final void setCivID_Just(int iCivID) {
      this.iCivID = iCivID;
   }

   public int getPuppetOfCivID() {
      return this.civData.p;
   }

   public final void setPuppetOfCivID(int nPuppetOfCivID) {
      if (nPuppetOfCivID != this.iCivID || nPuppetOfCivID != this.getPuppetOfCivID()) {
         Game.getCiv(this.getPuppetOfCivID()).removeVassal(this.iCivID);
      }

      this.civData.p = nPuppetOfCivID;
      if (this.getPuppetOfCivID() != this.iCivID) {
         Game.getCiv(this.getPuppetOfCivID()).addVassal(this.iCivID);
      }

      this.updateColorMap();
   }

   public final void addVassal(int nCivID) {
      try {
         for (int i = 0; i < this.diplomacy.lVassals.size(); i++) {
            if (this.diplomacy.lVassals.get(i).c == nCivID) {
               return;
            }
         }

         this.diplomacy.lVassals.add(new Vassal(nCivID));
         this.diplomacy.iVassalsSize = this.diplomacy.lVassals.size();
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public final void removeVassal(int nCivID) {
      try {
         for (int i = 0; i < this.diplomacy.lVassals.size(); i++) {
            if (this.diplomacy.lVassals.get(i).c == nCivID) {
               this.diplomacy.lVassals.remove(i);
               this.diplomacy.iVassalsSize = this.diplomacy.lVassals.size();
               return;
            }
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public final void updateVassalsIncome() {
      try {
         this.fIncomeLord = 0.0F;

         for (int i = 0; i < this.diplomacy.lVassals.size(); i++) {
            this.fIncomeLord = this.fIncomeLord + Game.getCiv(this.diplomacy.lVassals.get(i).c).fExpenseVassal;
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   public final void updateMoveInBattle(String key, boolean inBattle) {
      try {
         for (int i = 0; i < this.iMoveUnitsSize; i++) {
            if (this.lMoveUnits.get(i).key.equals(key)) {
               this.lMoveUnits.get(i).inBattle = inBattle;
               return;
            }
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   public final boolean newMove(int fromProvinceID, int toProvinceID, String key, int extraArmyY, boolean retreat) {
      try {
         if (fromProvinceID == toProvinceID) {
            return false;
         }

         int armyID = Game.getProvince(fromProvinceID).getArmyKeyID(key);
         if (armyID >= 0) {
            if (Game.getProvince(fromProvinceID).getArmy(armyID).inBattle && Game.battleManager.isArmyInBattle(fromProvinceID, key)) {
               return false;
            }

            MoveUnits nMoveUnits;
            if (this.getCivID() == Game.player.iCivID) {
               nMoveUnits = new MoveUnits_Player(this.getCivID(), fromProvinceID, toProvinceID, key, extraArmyY, retreat, false);
               if (nMoveUnits.iRouteSize > 2
                  && nMoveUnits.iRouteSize < GameValues.army.MOVE_UNITS_TRY_MOVING_LAND_IF_PATH_IS_BELOW_PLAYER
                  && nMoveUnits.haveSeaProvince()) {
                  MoveUnits nMoveUnitsLand = new MoveUnits_Player(this.getCivID(), fromProvinceID, toProvinceID, key, extraArmyY, retreat, true);
                  if (nMoveUnitsLand.iRouteSize > 1 && nMoveUnitsLand.getWidthTotal() < nMoveUnits.getWidthTotal()) {
                     nMoveUnits = nMoveUnitsLand;
                     nMoveUnitsLand = null;
                  }
               }
            } else {
               nMoveUnits = new MoveUnits(this.getCivID(), fromProvinceID, toProvinceID, key, extraArmyY, retreat, false);
               if (nMoveUnits.iRouteSize > 2
                  && nMoveUnits.iRouteSize < GameValues.army.MOVE_UNITS_TRY_MOVING_LAND_IF_PATH_IS_BELOW
                  && nMoveUnits.haveSeaProvince()) {
                  MoveUnits nMoveUnitsLand = new MoveUnits(this.getCivID(), fromProvinceID, toProvinceID, key, extraArmyY, retreat, true);
                  if (nMoveUnitsLand.iRouteSize > 1 && nMoveUnitsLand.getWidthTotal() < nMoveUnits.getWidthTotal()) {
                     nMoveUnits = nMoveUnitsLand;
                     nMoveUnitsLand = null;
                  }
               }
            }

            if (nMoveUnits.iRouteSize <= 1) {
               return false;
            }

            for (int i = 0; i < this.iMoveUnitsSize; i++) {
               if (this.lMoveUnits.get(i).key.equals(key)) {
                  if (this.lMoveUnits.get(i).inRetreat) {
                     return false;
                  }

                  if (this.lMoveUnits.get(i).getToProvinceID() == nMoveUnits.getToProvinceID()) {
                     nMoveUnits.doneMovementProgressWidth = this.lMoveUnits.get(i).doneMovementProgressWidth;
                     nMoveUnits.movementProgressOverWidth = this.lMoveUnits.get(i).movementProgressOverWidth;
                     nMoveUnits.currentMovementProgressWidth = this.lMoveUnits.get(i).currentMovementProgressWidth;
                     nMoveUnits.lCurrentMovingTime = this.lMoveUnits.get(i).lCurrentMovingTime;
                     nMoveUnits.fCurrentMovingPercentage = this.lMoveUnits.get(i).fCurrentMovingPercentage;
                     this.removeMove(i);
                  } else {
                     if (this.lMoveUnits.get(i).getProgressPerc() >= GameValues.army.MOVE_UNITS_LOCKED_MOVE) {
                        nMoveUnits = new MoveUnits(
                           this.getCivID(),
                           this.lMoveUnits.get(i).getToProvinceID(),
                           toProvinceID,
                           key,
                           this.lMoveUnits.get(i).extraArmyY,
                           this.lMoveUnits.get(i).getFromProvinceID()
                        );
                        if (nMoveUnits.iRouteSize > 1) {
                           nMoveUnits.doneMovementProgressWidth = this.lMoveUnits.get(i).doneMovementProgressWidth;
                           nMoveUnits.movementProgressOverWidth = this.lMoveUnits.get(i).movementProgressOverWidth;
                           nMoveUnits.currentMovementProgressWidth = this.lMoveUnits.get(i).currentMovementProgressWidth;
                           nMoveUnits.lCurrentMovingTime = this.lMoveUnits.get(i).lCurrentMovingTime;
                           nMoveUnits.fCurrentMovingPercentage = this.lMoveUnits.get(i).fCurrentMovingPercentage;
                           this.lMoveUnits.set(i, nMoveUnits);
                           Game.getProvince(fromProvinceID).getArmy(armyID).inRetreat = retreat;
                           return true;
                        }

                        return false;
                     }

                     this.removeMove(i);
                  }
                  break;
               }
            }

            this.lMoveUnits.add(nMoveUnits);
            this.iMoveUnitsSize = this.lMoveUnits.size();
            Game.getProvince(fromProvinceID).getArmy(armyID).inRetreat = retreat;
            Game.getProvince(fromProvinceID).getArmy(armyID).setInMovement(true);
            Game.getProvince(fromProvinceID).updateArmyPosY();
            Game.getProvince(fromProvinceID).updateIsUnderSiege();
            return true;
         }
      } catch (Exception var9) {
         CFG.exceptionStack(var9);
      }

      return false;
   }

   public final void removeMove(int i) {
      try {
         int tID = Game.getProvince(this.lMoveUnits.get(i).getFromProvinceID()).getArmyKeyID(this.lMoveUnits.get(i).key);
         if (tID >= 0) {
            Game.getProvince(this.lMoveUnits.get(i).getFromProvinceID()).getArmy(tID).setInMovement(false);
            Game.getProvince(this.lMoveUnits.get(i).getFromProvinceID()).getArmy(tID).inRetreat = false;
            Game.getProvince(this.lMoveUnits.get(i).getFromProvinceID()).getArmy(tID).iShiftX_Scaled = 0;
            Game.getProvince(this.lMoveUnits.get(i).getFromProvinceID()).getArmy(tID).iShiftY_Scaled = 0;
         } else {
            Game.ArmyPos armyPos = Game.findArmy_FullCheck(this.getCivID(), this.lMoveUnits.get(i).key);
            if (armyPos != null) {
               Game.getProvince(armyPos.iProvinceID).getArmy(armyPos.iID).setInMovement(false);
               Game.getProvince(armyPos.iProvinceID).getArmy(armyPos.iID).inRetreat = false;
               Game.getProvince(armyPos.iProvinceID).getArmy(armyPos.iID).iShiftX_Scaled = 0;
               Game.getProvince(armyPos.iProvinceID).getArmy(armyPos.iID).iShiftY_Scaled = 0;
            }
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }

      this.lMoveUnits.remove(i);
      this.iMoveUnitsSize = this.lMoveUnits.size();
   }

   public final boolean cancelMove(String key) {
      for (int i = 0; i < this.iMoveUnitsSize; i++) {
         if (this.lMoveUnits.get(i).key.equals(key)) {
            if (this.lMoveUnits.get(i).inRetreat) {
               return false;
            }

            if (this.lMoveUnits.get(i).getProgressPerc() >= GameValues.army.MOVE_UNITS_LOCKED_MOVE) {
               MoveUnits nMoveUnits = new MoveUnits(
                  this.getCivID(),
                  this.lMoveUnits.get(i).getToProvinceID(),
                  this.lMoveUnits.get(i).getFromProvinceID(),
                  key,
                  this.lMoveUnits.get(i).extraArmyY,
                  this.lMoveUnits.get(i).getFromProvinceID()
               );
               nMoveUnits.doneMovementProgressWidth = this.lMoveUnits.get(i).doneMovementProgressWidth;
               nMoveUnits.movementProgressOverWidth = this.lMoveUnits.get(i).movementProgressOverWidth;
               nMoveUnits.currentMovementProgressWidth = this.lMoveUnits.get(i).currentMovementProgressWidth;
               nMoveUnits.lCurrentMovingTime = this.lMoveUnits.get(i).lCurrentMovingTime;
               nMoveUnits.fCurrentMovingPercentage = this.lMoveUnits.get(i).fCurrentMovingPercentage;
               this.lMoveUnits.set(i, nMoveUnits);
            } else {
               int tID = Game.getProvince(this.lMoveUnits.get(i).getFromProvinceID()).getArmyKeyID(this.lMoveUnits.get(i).key);
               if (tID >= 0) {
                  Game.getProvince(this.lMoveUnits.get(i).getFromProvinceID()).getArmy(tID).setInMovement(false);
                  Game.getProvince(this.lMoveUnits.get(i).getFromProvinceID()).getArmy(tID).iShiftX_Scaled = 0;
                  Game.getProvince(this.lMoveUnits.get(i).getFromProvinceID()).getArmy(tID).iShiftY_Scaled = 0;
               }

               SiegeManager.checkForSiege(this.lMoveUnits.get(i).getFromProvinceID());
               Game.getProvince(this.lMoveUnits.get(i).getFromProvinceID()).updateArmyPosY();
               this.removeMove(i);
            }

            return true;
         }
      }

      return false;
   }

   public final boolean removeMove(String key) {
      for (int i = 0; i < this.iMoveUnitsSize; i++) {
         if (this.lMoveUnits.get(i).key.equals(key)) {
            int tID = Game.getProvince(this.lMoveUnits.get(i).getFromProvinceID()).getArmyKeyID(this.lMoveUnits.get(i).key);
            if (tID >= 0) {
               Game.getProvince(this.lMoveUnits.get(i).getFromProvinceID()).getArmy(tID).setInMovement(false);
               Game.getProvince(this.lMoveUnits.get(i).getFromProvinceID()).getArmy(tID).iShiftX_Scaled = 0;
               Game.getProvince(this.lMoveUnits.get(i).getFromProvinceID()).getArmy(tID).iShiftY_Scaled = 0;
            }

            this.removeMove(i);
            return true;
         }
      }

      return false;
   }

   public final void clearMoveUnits() {
      this.lMoveUnits.clear();
      this.iMoveUnitsSize = this.lMoveUnits.size();
   }

   public final MoveUnits getMoveUnits(int i) {
      return this.lMoveUnits.get(i);
   }

   public final void updateMoveUnits_Load(SaveGameManager.Save_Civ_MoveUnits civMoveUnits, boolean nInRetreat, boolean nInBattle) {
      for (int i = this.iMoveUnitsSize - 1; i >= 0; i--) {
         if (this.lMoveUnits.get(i).key.equals(civMoveUnits.k)) {
            this.lMoveUnits.get(i).currentMovementProgressWidth = civMoveUnits.m;
            this.lMoveUnits.get(i).inRetreat = nInRetreat;
            this.lMoveUnits.get(i).inBattle = nInBattle;
            return;
         }
      }
   }

   public final boolean isArmyMovingToProvince(String armyKey, int toProvinceID) {
      for (int i = 0; i < this.iMoveUnitsSize; i++) {
         if (this.lMoveUnits.get(i).getToProvinceLastID() == toProvinceID && this.lMoveUnits.get(i).key.equals(armyKey)) {
            return true;
         }
      }

      return false;
   }

   public final int getMoveUnitsSize() {
      return this.iMoveUnitsSize;
   }

   public final boolean isArmyMovingToProvince_MoveUnits(int provinceID) {
      for (int i = 0; i < this.iMoveUnitsSize; i++) {
         if (this.lMoveUnits.get(i).getToProvinceLastID() == provinceID) {
            return true;
         }
      }

      return false;
   }

   public final boolean isInMoveUnits_ArmyKey(String key) {
      for (int i = 0; i < this.iMoveUnitsSize; i++) {
         if (this.lMoveUnits.get(i).key.equals(key)) {
            return true;
         }
      }

      return false;
   }

   public final int armyMovingToProvince_MoveUnits(int provinceID) {
      int out = 0;

      for (int i = 0; i < this.iMoveUnitsSize; i++) {
         if (this.lMoveUnits.get(i).getToProvinceLastID() == provinceID) {
            ArmyDivision armyDivision = Game.getProvince(this.lMoveUnits.get(i).getFromProvinceID()).getArmy(this.lMoveUnits.get(i).key);
            if (armyDivision != null) {
               out += armyDivision.iArmyRegimentSize;
            }
         }
      }

      return out;
   }

   public final boolean recruitArmy(ArmyRecruit nArmyRecruit) {
      try {
         if (this.fGold >= ArmyManager.getRecruitmentCost(this.getCivID(), nArmyRecruit.provinceID, nArmyRecruit.unitID, nArmyRecruit.armyID)) {
            if (this.fManpower < Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).REGIMENT_SIZE) {
               return false;
            }

            int nID = -1;
            nArmyRecruit.cost = ArmyManager.getRecruitmentCost(this.getCivID(), nArmyRecruit.provinceID, nArmyRecruit.unitID, nArmyRecruit.armyID);
            nArmyRecruit.timeLeft = ArmyManager.getRecruitmentTime(this.getCivID(), nArmyRecruit.provinceID, nArmyRecruit.unitID, nArmyRecruit.armyID);
            this.fGold = this.fGold - nArmyRecruit.cost;
            this.setManpower(this.fManpower - Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).REGIMENT_SIZE);

            for (int i = 0; i < this.iArmyRecruitSize; i++) {
               if (this.lArmyRecruit.get(i).get(0).provinceID == nArmyRecruit.provinceID) {
                  nID = i;
                  break;
               }
            }

            if (nID >= 0) {
               this.lArmyRecruit.get(nID).add(nArmyRecruit);
            } else {
               ArrayList<ArmyRecruit> nListArmyRecruit = new ArrayList<>();
               nListArmyRecruit.add(nArmyRecruit);
               this.lArmyRecruit.add(nListArmyRecruit);
            }

            this.iArmyRecruitSize = this.lArmyRecruit.size();
            this.iArmyRecruitSize_Total = 0;

            for (int ix = 0; ix < this.iArmyRecruitSize; ix++) {
               this.iArmyRecruitSize_Total = this.iArmyRecruitSize_Total + this.lArmyRecruit.get(ix).size();
            }

            if (this.getCivID() == Game.player.iCivID) {
               Game.menuManager.addRebuildInGame_RightQueue();
            }

            return true;
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }

      return false;
   }

   public final void addRecruitArmy_Load(ArmyRecruit nArmyRecruit) {
      int nID = -1;

      for (int i = 0; i < this.iArmyRecruitSize; i++) {
         if (this.lArmyRecruit.get(i).get(0).provinceID == nArmyRecruit.provinceID) {
            nID = i;
            break;
         }
      }

      if (nID >= 0) {
         this.lArmyRecruit.get(nID).add(nArmyRecruit);
      } else {
         ArrayList<ArmyRecruit> nListArmyRecruit = new ArrayList<>();
         nListArmyRecruit.add(nArmyRecruit);
         this.lArmyRecruit.add(nListArmyRecruit);
      }

      this.iArmyRecruitSize = this.lArmyRecruit.size();
   }

   public final void addRecruitArmy_LoadUpdateSize() {
      this.iArmyRecruitSize_Total = 0;

      for (int i = 0; i < this.iArmyRecruitSize; i++) {
         this.iArmyRecruitSize_Total = this.iArmyRecruitSize_Total + this.lArmyRecruit.get(i).size();
      }
   }

   public final boolean cancelRecruitArmy(ArmyRecruit nArmyRecruit) {
      return this.cancelRecruitArmy(nArmyRecruit.provinceID);
   }

   public final boolean cancelRecruitArmy(int nProvinceID) {
      try {
         for (int i = this.iArmyRecruitSize - 1; i >= 0; i--) {
            if (this.lArmyRecruit.get(i).get(0).provinceID == nProvinceID) {
               int tRemoveID = this.lArmyRecruit.get(i).size() - 1;
               this.fGold = this.fGold + this.lArmyRecruit.get(i).get(tRemoveID).cost;
               this.setManpower(this.fManpower + Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).REGIMENT_SIZE);
               this.lArmyRecruit.get(i).remove(tRemoveID);
               if (this.lArmyRecruit.get(i).size() == 0) {
                  this.lArmyRecruit.remove(i);
                  this.iArmyRecruitSize = this.lArmyRecruit.size();
               }

               this.iArmyRecruitSize_Total = 0;

               for (int o = 0; o < this.iArmyRecruitSize; o++) {
                  this.iArmyRecruitSize_Total = this.iArmyRecruitSize_Total + this.lArmyRecruit.get(o).size();
               }

               if (this.getCivID() == Game.player.iCivID) {
                  Game.menuManager.addRebuildInGame_RightQueue();
               }

               return true;
            }
         }
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
      }

      return false;
   }

   public final void cancelRecruitArmy_All() {
      try {
         for (int i = this.iArmyRecruitSize - 1; i >= 0; i--) {
            for (int j = this.lArmyRecruit.get(i).size() - 1; j >= 0; j--) {
               this.fGold = this.fGold + this.lArmyRecruit.get(i).get(j).cost;
               this.setManpower(this.fManpower + Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).REGIMENT_SIZE);
               this.lArmyRecruit.get(i).remove(j);
               if (this.lArmyRecruit.get(i).size() == 0) {
                  this.lArmyRecruit.remove(i);
                  this.iArmyRecruitSize = this.lArmyRecruit.size();
               }

               this.iArmyRecruitSize_Total = 0;

               for (int o = 0; o < this.iArmyRecruitSize; o++) {
                  this.iArmyRecruitSize_Total = this.iArmyRecruitSize_Total + this.lArmyRecruit.get(o).size();
               }
            }
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   public final void updateRecruitArmy(int i, int j) {
      ArmyRecruit armyRecruit = this.lArmyRecruit.get(i).get(j);
      armyRecruit.timeLeft--;
      if (armyRecruit.timeLeft <= 0) {
         if (armyRecruit.toArmyKey != null) {
            try {
               Game.ArmyPos nArmyPos = Game.findArmy_FullCheck(this.getCivID(), armyRecruit.toArmyKey);
               if (nArmyPos != null) {
                  if (nArmyPos.iProvinceID >= 0 && nArmyPos.iID >= 0 && Game.getProvince(nArmyPos.iProvinceID).getArmy(nArmyPos.iID).civID == this.getCivID()) {
                     Game.getProvince(nArmyPos.iProvinceID).getArmy(nArmyPos.iID).addRegiment(new ArmyRegiment(armyRecruit.unitID, armyRecruit.armyID));
                     this.lArmyRecruit.get(i).remove(j);
                     if (this.lArmyRecruit.get(i).size() == 0) {
                        this.lArmyRecruit.remove(i);
                        this.iArmyRecruitSize = this.lArmyRecruit.size();
                     }

                     this.iArmyRecruitSize_Total = 0;

                     for (int o = 0; o < this.iArmyRecruitSize; o++) {
                        this.iArmyRecruitSize_Total = this.iArmyRecruitSize_Total + this.lArmyRecruit.get(o).size();
                     }

                     Game.gameThread.addCivUpdateArmyMaintenance(this.getCivID());
                     if (this.getCivID() == Game.player.iCivID) {
                        Game.player.playerStats.recruitedRegiments++;
                        Game.stats.civStats.rr++;
                        Game.menuManager.addRebuildInGame_RightQueue();
                     }

                     return;
                  }
               } else if (this.lCreateNewArmy.containsKey(armyRecruit.toArmyKey)) {
                  int nProvinceID = this.lCreateNewArmy.get(armyRecruit.toArmyKey);
                  if (nProvinceID >= 0) {
                     if (this.getNumOfProvinces() > 0) {
                        List<ArmyRegiment> nArmyRegiment = new ArrayList<>();
                        nArmyRegiment.add(new ArmyRegiment(armyRecruit.unitID, armyRecruit.armyID));
                        if (Game.getProvince(nProvinceID).getCivID() != this.getCivID()
                           && Game.getCiv(Game.getProvince(nProvinceID).getCivID()).getPuppetOfCivID() != this.getCivID()) {
                           if (this.getCapitalProvinceID() >= 0 && Game.getProvince(this.getCapitalProvinceID()).getCivID() == this.getCivID()) {
                              nProvinceID = this.getCapitalProvinceID();
                           } else {
                              nProvinceID = this.getProvinceID(0);
                           }
                        }

                        ArmyDivision nArmyDivision = new ArmyDivision(this.getCivID(), nProvinceID, nArmyRegiment);
                        nArmyDivision.key = armyRecruit.toArmyKey;
                        Game.getProvince(nProvinceID).addArmy(nArmyDivision);
                     }

                     this.lCreateNewArmy.remove(armyRecruit.toArmyKey);
                     this.lArmyRecruit.get(i).remove(j);
                     if (this.lArmyRecruit.get(i).size() == 0) {
                        this.lArmyRecruit.remove(i);
                        this.iArmyRecruitSize = this.lArmyRecruit.size();
                     }

                     this.iArmyRecruitSize_Total = 0;

                     for (int o = 0; o < this.iArmyRecruitSize; o++) {
                        this.iArmyRecruitSize_Total = this.iArmyRecruitSize_Total + this.lArmyRecruit.get(o).size();
                     }

                     Game.gameThread.addCivUpdateArmyMaintenance(this.getCivID());
                     if (this.getCivID() == Game.player.iCivID) {
                        Game.player.playerStats.recruitedRegiments++;
                        Game.stats.civStats.rr++;
                        Game.menuManager.addRebuildInGame_RightQueue();
                     }

                     return;
                  }
               } else {
                  int nProvinceID = armyRecruit.provinceID;
                  if (nProvinceID >= 0) {
                     if (this.getNumOfProvinces() > 0) {
                        List<ArmyRegiment> nArmyRegiment = new ArrayList<>();
                        nArmyRegiment.add(new ArmyRegiment(armyRecruit.unitID, armyRecruit.armyID));
                        if (Game.getProvince(nProvinceID).getCivID() != this.getCivID()
                           && Game.getCiv(Game.getProvince(nProvinceID).getCivID()).getPuppetOfCivID() != this.getCivID()) {
                           if (this.getCapitalProvinceID() >= 0 && Game.getProvince(this.getCapitalProvinceID()).getCivID() == this.getCivID()) {
                              nProvinceID = this.getCapitalProvinceID();
                           } else {
                              nProvinceID = this.getProvinceID(0);
                           }
                        }

                        ArmyDivision nArmyDivision = new ArmyDivision(this.getCivID(), nProvinceID, nArmyRegiment);
                        nArmyDivision.key = armyRecruit.toArmyKey;
                        Game.getProvince(nProvinceID).addArmy(nArmyDivision);
                     }

                     this.lArmyRecruit.get(i).remove(j);
                     if (this.lArmyRecruit.get(i).size() == 0) {
                        this.lArmyRecruit.remove(i);
                        this.iArmyRecruitSize = this.lArmyRecruit.size();
                     }

                     this.iArmyRecruitSize_Total = 0;

                     for (int o = 0; o < this.iArmyRecruitSize; o++) {
                        this.iArmyRecruitSize_Total = this.iArmyRecruitSize_Total + this.lArmyRecruit.get(o).size();
                     }

                     Game.gameThread.addCivUpdateArmyMaintenance(this.getCivID());
                     if (this.getCivID() == Game.player.iCivID) {
                        Game.player.playerStats.recruitedRegiments++;
                        Game.stats.civStats.rr++;
                        Game.menuManager.addRebuildInGame_RightQueue();
                     }

                     return;
                  }
               }
            } catch (Exception var8) {
               CFG.exceptionStack(var8);
            }
         }

         for (int o = 0; o < Game.getProvince(armyRecruit.provinceID).getArmySize(); o++) {
            if (Game.getProvince(armyRecruit.provinceID).getArmy(o).civID == this.getCivID()) {
               Game.getProvince(armyRecruit.provinceID).getArmy(o).addRegiment(new ArmyRegiment(armyRecruit.unitID, armyRecruit.armyID));
               this.lArmyRecruit.get(i).remove(j);
               if (this.lArmyRecruit.get(i).size() == 0) {
                  this.lArmyRecruit.remove(i);
                  this.iArmyRecruitSize = this.lArmyRecruit.size();
               }

               this.iArmyRecruitSize_Total = 0;

               for (int a = 0; a < this.iArmyRecruitSize; a++) {
                  this.iArmyRecruitSize_Total = this.iArmyRecruitSize_Total + this.lArmyRecruit.get(a).size();
               }

               Game.gameThread.addCivUpdateArmyMaintenance(this.getCivID());
               if (this.getCivID() == Game.player.iCivID) {
                  Game.player.playerStats.recruitedRegiments++;
                  Game.stats.civStats.rr++;
                  Game.menuManager.addRebuildInGame_RightQueue();
               }

               return;
            }
         }

         if (this.getNumOfProvinces() > 0) {
            int recruitInProvinceID = armyRecruit.provinceID;
            if (Game.getProvince(recruitInProvinceID).getCivID() != this.getCivID()
                  && Game.getCiv(Game.getProvince(recruitInProvinceID).getCivID()).getPuppetOfCivID() != this.getCivID()
               || Game.getProvince(recruitInProvinceID).isOccupied()) {
               if (this.getCapitalProvinceID() >= 0
                  && Game.getProvince(this.getCapitalProvinceID()).getCivID() == this.getCivID()
                  && !Game.getProvince(this.getCapitalProvinceID()).isOccupied()) {
                  recruitInProvinceID = this.getCapitalProvinceID();
               } else {
                  recruitInProvinceID = -1;

                  for (int p = 0; p < this.getNumOfProvinces(); p++) {
                     if (Game.getProvince(this.getProvinceID(p)).getCivID() == this.getCivID() && !Game.getProvince(this.getProvinceID(p)).isOccupied()) {
                        recruitInProvinceID = this.getProvinceID(p);
                        break;
                     }
                  }
               }
            }

            if (recruitInProvinceID >= 0) {
               List<ArmyRegiment> nArmyRegiment = new ArrayList<>();
               nArmyRegiment.add(new ArmyRegiment(armyRecruit.unitID, armyRecruit.armyID));
               ArmyDivision nArmyDivision = new ArmyDivision(this.getCivID(), recruitInProvinceID, nArmyRegiment);
               Game.getProvince(recruitInProvinceID).addArmy(nArmyDivision);
            }
         }

         this.lArmyRecruit.get(i).remove(j);
         if (this.lArmyRecruit.get(i).size() == 0) {
            this.lArmyRecruit.remove(i);
            this.iArmyRecruitSize = this.lArmyRecruit.size();
         }

         this.iArmyRecruitSize_Total = 0;

         for (int ox = 0; ox < this.iArmyRecruitSize; ox++) {
            this.iArmyRecruitSize_Total = this.iArmyRecruitSize_Total + this.lArmyRecruit.get(ox).size();
         }

         Game.gameThread.addCivUpdateArmyMaintenance(this.getCivID());
         if (this.getCivID() == Game.player.iCivID) {
            Game.player.playerStats.recruitedRegiments++;
            Game.stats.civStats.rr++;
            Game.menuManager.addRebuildInGame_RightQueue();
         }
      }
   }

   public int getRecruitArmyInProvinceID(int nProvinceID) {
      try {
         for (int i = 0; i < this.iArmyRecruitSize; i++) {
            if (this.lArmyRecruit.get(i).get(0).provinceID == nProvinceID) {
               return i;
            }
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }

      return -1;
   }

   public int getRecruitArmyInProgress(String key) {
      int out = 0;

      for (int i = 0; i < this.iArmyRecruitSize; i++) {
         if (this.lArmyRecruit.get(i).get(0).toArmyKey != null && this.lArmyRecruit.get(i).get(0).toArmyKey.equals(key)) {
            out += this.lArmyRecruit.get(i).size();
         }
      }

      return out;
   }

   public boolean isRecruitArmyInProgress(String key) {
      for (int i = 0; i < this.iArmyRecruitSize; i++) {
         if (this.lArmyRecruit.get(i).get(0).toArmyKey != null && this.lArmyRecruit.get(i).get(0).toArmyKey.equals(key)) {
            return true;
         }
      }

      return false;
   }

   public int getArmyRecruitSize() {
      return this.iArmyRecruitSize;
   }

   public int getLegaciesSize() {
      return this.iLegaciesSize;
   }

   public int getLegacyLevel(int legacyID) {
      for (int i = 0; i < this.iLegaciesSize; i++) {
         if (this.legacies.get(i).id == legacyID) {
            return this.legacies.get(i).lvl;
         }
      }

      return -1;
   }

   public final boolean addLegacy(int legacyID, int levelID) {
      for (int i = 0; i < this.iLegaciesSize; i++) {
         if (this.legacies.get(i).id == legacyID) {
            this.legacies.get(i).lvl = Math.max(this.legacies.get(i).lvl, levelID);
            return false;
         }
      }

      this.legacies.add(new CivilizationLegacy(legacyID, levelID));
      this.iLegaciesSize = this.legacies.size();
      return true;
   }

   public final void addLegacy_Load(int legacyID, int levelID) {
      this.legacies.add(new CivilizationLegacy(legacyID, levelID));
      this.iLegaciesSize = this.legacies.size();

      try {
         for (int a = levelID; a >= 0; a--) {
            LegacyManager.updateCivBonuses(legacyID, a, this.getCivID(), true);
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   public final boolean unlockLegacy(int legacyID) {
      for (int i = 0; i < this.iLegaciesSize; i++) {
         if (this.legacies.get(i).id == legacyID) {
            if (this.legacies.get(i).lvl + 1 < LegacyManager.legacies.get(legacyID).CostLegacy.length) {
               if (this.fLegacy < LegacyManager.legacies.get(legacyID).CostLegacy[this.legacies.get(i).lvl + 1]) {
                  return false;
               }

               this.fLegacy = this.fLegacy - LegacyManager.legacies.get(legacyID).CostLegacy[this.legacies.get(i).lvl + 1];
               this.legacies.get(i).lvl = Math.min(this.legacies.get(i).lvl + 1, LegacyManager.legacies.get(legacyID).CostLegacy.length - 1);
               LegacyManager.updateCivBonuses(legacyID, this.legacies.get(i).lvl, this.getCivID());
               return true;
            }

            return false;
         }
      }

      if (this.fLegacy < LegacyManager.legacies.get(legacyID).CostLegacy[0]) {
         return false;
      } else {
         this.fLegacy = this.fLegacy - LegacyManager.legacies.get(legacyID).CostLegacy[0];
         this.legacies.add(new CivilizationLegacy(legacyID, 0));
         this.iLegaciesSize = this.legacies.size();
         LegacyManager.updateCivBonuses(legacyID, 0, this.getCivID());
         return true;
      }
   }

   public CivilizationLegacy getCivLegacy(int i) {
      try {
         return this.legacies.get(i);
      } catch (Exception var3) {
         return new CivilizationLegacy(0, 0);
      }
   }

   public int getLegaciesUnlocked() {
      int out = 0;

      for (int i = 0; i < this.iLegaciesSize; i++) {
         out += this.legacies.get(i).lvl + 1;
      }

      return out;
   }

   public int getLegaciesUnlocked(int groupID) {
      int out = 0;

      for (int i = 0; i < this.iLegaciesSize; i++) {
         if (LegacyManager.legacies.get(this.legacies.get(i).id).GroupID == groupID) {
            out += this.legacies.get(i).lvl + 1;
         }
      }

      return out;
   }

   public ArmyGeneral getGeneralNotAssigned(int i) {
      return this.lGeneralsNotAssigned.get(i);
   }

   public final void addGeneral(ArmyGeneral armyGeneral) {
      this.lGeneralsNotAssigned.add(armyGeneral);
      this.iGeneralsSize = this.lGeneralsNotAssigned.size();
   }

   public final boolean assignGeneralToArmy(int nProvinceID, int nArmyID) {
      return this.iGeneralsSize > 0
         ? this.assignGeneralToArmy(nProvinceID, nArmyID, this.lGeneralsNotAssigned.get(Game.oR.nextInt(this.lGeneralsNotAssigned.size())).key)
         : false;
   }

   public final boolean assignGeneralToArmy(int nProvinceID, int nArmyID, String generalKey) {
      for (int i = this.iGeneralsSize - 1; i >= 0; i--) {
         if (this.lGeneralsNotAssigned.get(i).key.equals(generalKey)) {
            this.assignGeneralToArmy(nProvinceID, nArmyID, i);
            return true;
         }
      }

      return false;
   }

   public final void assignGeneralToArmy(int nProvinceID, int nArmyID, int generalID) {
      try {
         if (Game.getProvince(nProvinceID).getArmy(nArmyID).armyGeneral != null) {
            this.addGeneral(Game.getProvince(nProvinceID).getArmy(nArmyID).armyGeneral);
         }

         Game.getProvince(nProvinceID).getArmy(nArmyID).setArmyGeneral(this.lGeneralsNotAssigned.get(generalID));
         this.lGeneralsNotAssigned.remove(generalID);
         this.iGeneralsSize = this.lGeneralsNotAssigned.size();
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
      }
   }

   public final void update_ChanceOfGeneral_NotAssigned() {
      try {
         for (int i = this.iGeneralsSize - 1; i >= 0; i--) {
            if (this.lGeneralsNotAssigned.get(i).d % 12 + 1 == Game_Calendar.currentMonth
               && RulersManager.characterDies(this.iCivID, this.lGeneralsNotAssigned.get(i).y)) {
               if (this.iCivID == Game.player.iCivID) {
                  Game.player
                     .addNotification(
                        new Notification(
                           Notification.Notification_Type.GENERAL_DIED,
                           Game.lang.get("GeneralDied") + ": " + this.lGeneralsNotAssigned.get(i).n,
                           Images.general,
                           Game_Calendar.TURN_ID,
                           Notification.Notification_BG.RED,
                           Game.player.iCivID
                        )
                     );
                  Game.addSimpleTask(new Game.SimpleTask("rebuildGenerals") {
                     @Override
                     public void update() {
                        if (Game.menuManager.getVisibleInGame_Generals()) {
                           Game.menuManager.rebuildInGame_Generals();
                           Game.menuManager.setVisibleInGame_Generals(true);
                           InGame_Generals.lTime = 0L;
                        }
                     }
                  });
               }

               this.lGeneralsNotAssigned.remove(i);
               this.iGeneralsSize = this.lGeneralsNotAssigned.size();
            }
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   public final void buildTechTree_UnlockTechnologies(int iTechID) {
      iTechID = Math.max(0, Math.min(TechnologyTree.iTechnologySize, iTechID));
      this.lTechResearched.set(iTechID, true);
      this.buildUnlockTechnologies_TechQueue1(iTechID);
      this.buildUnlockTechnologies_TechQueue2(iTechID);
   }

   private final void buildUnlockTechnologies_TechQueue1(int iTechID) {
      if (TechnologyTree.lTechnology.get(iTechID).RequiredTech >= 0 && !this.getTechResearched(TechnologyTree.lTechnology.get(iTechID).RequiredTech)) {
         this.lTechResearched.set(TechnologyTree.lTechnology.get(iTechID).RequiredTech, true);
         this.buildUnlockTechnologies_TechQueue1(TechnologyTree.lTechnology.get(iTechID).RequiredTech);
         this.buildUnlockTechnologies_TechQueue2(TechnologyTree.lTechnology.get(iTechID).RequiredTech);
      }
   }

   private final void buildUnlockTechnologies_TechQueue2(int iTechID) {
      if (TechnologyTree.lTechnology.get(iTechID).RequiredTech2 >= 0
         && !Game.getCiv(this.iCivID).getTechResearched(TechnologyTree.lTechnology.get(iTechID).RequiredTech2)) {
         this.lTechResearched.set(TechnologyTree.lTechnology.get(iTechID).RequiredTech2, true);
         this.buildUnlockTechnologies_TechQueue1(TechnologyTree.lTechnology.get(iTechID).RequiredTech2);
         this.buildUnlockTechnologies_TechQueue2(TechnologyTree.lTechnology.get(iTechID).RequiredTech2);
      }
   }

   public final void setTechnologyResearched(int iTechID, boolean researched) {
      this.lTechResearched.set(iTechID, researched);
      if (TechnologyTree.lTechnology.get(iTechID).UnlocksNukes) {
         this.canBuildNuke = true;
      }

      if (TechnologyTree.lTechnology.get(iTechID).UnlocksAccessToTheSea) {
         this.canAccessSea = true;
      }

      if (TechnologyTree.lTechnology.get(iTechID).UnlocksColonization) {
         this.canColonize = true;
      }
   }

   public void initTechTree() {
      this.lTechResearched.clear();

      for (int i = 0; i < TechnologyTree.iTechnologySize; i++) {
         this.lTechResearched.add(false);
      }
   }

   public void buildTechTree() {
      this.lTechResearched.clear();
      this.unlockedUnits.clear();
      this.unlockedBuildings.clear();

      for (int i = 0; i < TechnologyTree.iTechnologySize; i++) {
         this.lTechResearched.add(false);
      }

      if (this.scenarioEditorData.TechnologyID != MapScenarios.DEFAULT_VALUE) {
         if (this.scenarioEditorData.TechnologyID >= 0) {
            this.buildTechTree_UnlockTechnologies(this.scenarioEditorData.TechnologyID);
         } else if (Game.mapScenarios.details.get(Game.scenarioID).CivDefault_Technology >= 0) {
            this.buildTechTree_UnlockTechnologies(Game.mapScenarios.details.get(Game.scenarioID).CivDefault_Technology);
         }
      } else if (Game.mapScenarios.details.get(Game.scenarioID).CivDefault_Technology >= 0) {
         this.buildTechTree_UnlockTechnologies(Game.mapScenarios.details.get(Game.scenarioID).CivDefault_Technology);
      }

      for (int i = 0; i < BuildingsManager.buildingsSize; i++) {
         for (int j = 0; j < BuildingsManager.buildingSize.get(i); j++) {
            if (BuildingsManager.buildings.get(i).RequiredTechID[j] < 0) {
               this.unlockedBuildings.add(new TechnologyTree.Building(i, j));
            }
         }
      }

      for (int i = 0; i < ArmyManager.iUnitsTypesSize; i++) {
         for (int jx = 0; jx < ArmyManager.lArmySize.get(i); jx++) {
            if (ArmyManager.lArmy.get(i).get(jx).RequiredTechID < 0) {
               this.unlockedUnits.add(new TechnologyTree.Unit(i, jx));
            }
         }
      }

      for (int i = 0; i < TechnologyTree.iTechnologySize; i++) {
         if (this.lTechResearched.get(i)) {
            this.addTechnology(i, true);
         }
      }

      this.updateBestUnits();
      this.initMaxLaws();
   }

   public void buildTechTree_Load() {
      this.unlockedUnits.clear();
      this.unlockedBuildings.clear();

      for (int i = 0; i < BuildingsManager.buildingsSize; i++) {
         for (int j = 0; j < BuildingsManager.buildingSize.get(i); j++) {
            if (BuildingsManager.buildings.get(i).RequiredTechID[j] < 0) {
               this.unlockedBuildings.add(new TechnologyTree.Building(i, j));
            }
         }
      }

      for (int i = 0; i < ArmyManager.iUnitsTypesSize; i++) {
         for (int jx = 0; jx < ArmyManager.lArmySize.get(i); jx++) {
            if (ArmyManager.lArmy.get(i).get(jx).RequiredTechID < 0) {
               this.unlockedUnits.add(new TechnologyTree.Unit(i, jx));
            }
         }
      }

      for (int t = 0; t < TechnologyTree.iTechnologySize; t++) {
         if (this.getTechResearched(t)) {
            for (int i = 0; i < TechnologyTree.lTechUnlocksBuildings.get(t).size(); i++) {
               this.unlockedBuildings.add(TechnologyTree.lTechUnlocksBuildings.get(t).get(i));
            }

            for (int i = 0; i < TechnologyTree.lTechUnlocksUnits.get(t).size(); i++) {
               this.unlockedUnits.add(TechnologyTree.lTechUnlocksUnits.get(t).get(i));
            }
         }
      }

      for (int i = 0; i < TechnologyTree.iTechnologySize; i++) {
         if (this.lTechResearched.get(i)) {
            this.updateTechnologyBonuses(i);
         }
      }

      this.updateBestUnits();
   }

   public final int getActiveTechResearch() {
      return this.civData4.e;
   }

   public final void setActiveTechResearch(int iTechID) {
      this.civData4.e = iTechID;
      if (iTechID >= 0) {
         for (int i = this.lResearching.size() - 1; i >= 0; i--) {
            if (this.lResearching.get(i).iTechID == iTechID) {
               return;
            }
         }

         this.lResearching.add(new TechnologyResearch(iTechID));
      }
   }

   public final void addResearchProgress(float nProgress) {
      this.addResearchProgress(this.getActiveTechResearch(), nProgress);
   }

   public final void addResearchProgress(int iTechID, float nProgress) {
      if (iTechID >= 0) {
         for (int i = this.lResearching.size() - 1; i >= 0; i--) {
            if (this.lResearching.get(i).iTechID == iTechID) {
               this.lResearching.get(i).fProgress = Math.max(0.0F, this.lResearching.get(i).fProgress + nProgress);
               if (this.lResearching.get(i).fProgress >= TechnologyTree.getResearchCost(this.lResearching.get(i).iTechID, this.getCivID())) {
                  this.addTechnology(iTechID, false);
               }

               return;
            }
         }

         this.lResearching.add(new TechnologyResearch(iTechID));
         this.lResearching.get(this.lResearching.size() - 1).fProgress += nProgress;
      }
   }

   public void addTechnology(int iTechID, boolean loadScenario) {
      if (!this.lTechResearched.get(iTechID) || TechnologyTree.lTechnology.get(iTechID).Repeatable || loadScenario) {
         this.lTechResearched.set(iTechID, true);
         if (!loadScenario) {
            this.setAdvantagePoints(this.getAdvantagePoints() + 1);
         }

         for (int i = 0; i < TechnologyTree.lTechUnlocksBuildings.get(iTechID).size(); i++) {
            this.unlockedBuildings.add(TechnologyTree.lTechUnlocksBuildings.get(iTechID).get(i));
         }

         boolean updateBestUnits = false;

         for (int i = 0; i < TechnologyTree.lTechUnlocksUnits.get(iTechID).size(); i++) {
            this.unlockedUnits.add(TechnologyTree.lTechUnlocksUnits.get(iTechID).get(i));
            updateBestUnits = true;
         }

         if (!loadScenario && updateBestUnits) {
            this.updateBestUnits();
         }

         for (int i = this.lResearching.size() - 1; i >= 0; i--) {
            if (this.lResearching.get(i).iTechID == iTechID) {
               this.lResearching.remove(i);
               break;
            }
         }

         if (!TechnologyTree.lTechnology.get(iTechID).Repeatable) {
            this.civData4.e = -1;
         }

         this.iAlternativeTechResearch = -1;
         this.updateTechnologyBonuses(iTechID);
         if (!loadScenario) {
            if (TechnologyTree.lTechnology.get(iTechID).Legacy != 0) {
               this.addLegacy(TechnologyTree.lTechnology.get(iTechID).Legacy);
            }

            if (TechnologyTree.lTechnology.get(iTechID).Gold != 0) {
               this.fGold = this.fGold + TechnologyTree.lTechnology.get(iTechID).Gold;
            }

            Game.gameThread.addCivUpdateLegacyPerMonth(this.getCivID());
            if (this.getCivID() == Game.player.iCivID) {
               if (Game.menuManager.getVisibleInGame_TechnologyChoose() && InGame_TechnologyChoose.IN_TECHNOLOGY_CHOOSE) {
                  Game.addSimpleTask(new Game.SimpleTask("rebuildTechTree") {
                     @Override
                     public void update() {
                        Game.menuManager.rebuildInGame_TechnologyChoose(true, false);
                        InGame_TechnologyChoose.lTime = 0L;
                     }
                  });
               }

               if (Game.menuManager.getVisibleInGame_TechnologyTree()) {
                  Game.addSimpleTask(new Game.SimpleTask("rebuildTechTree") {
                     @Override
                     public void update() {
                        Game.menuManager.rebuildInGame_TechnologyTree(true, false);
                        InGame_TechnologyTree.lTime = 0L;
                     }
                  });
               }

               if (Game.menuManager.getVisibleInGame_Court()
                  && InGame_CourtOptions.iActiveID == InGame_CourtOptions.buildID
                  && Game.mapModes.MODE_BUILDING != Game.mapModes.iActiveMapModeID) {
                  Game.addSimpleTask(new Game.SimpleTask("rebuildBuildings2SavePos") {
                     @Override
                     public void update() {
                        Game.menuManager.rebuildInGame_Buildings2SavePos();
                        Game.menuManager.setVisibleInGame_Court(true);
                        InGame_Court.lTime = 0L;
                     }
                  });
               }

               InGame_Info.iCivID = Game.player.iCivID;
               InGame_Info.iCivID2 = 0;
               Game.menuManager.rebuildInGame_Info(TechnologyTree.lTechnology.get(iTechID).Name, Game.lang.get("ResearchCompleted"));
               if (Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).OVER_IMAGE_ID > 2) {
                  InGame_Info.imgID = Images.infoTechnology2;
               } else {
                  InGame_Info.imgID = Images.infoTechnology;
               }

               if (Game.player.playerData.techQueue.lTechQueue.size() > 0) {
                  this.setActiveTechResearch(Game.player.playerData.techQueue.getTechQueue());
               }

               Game.player.currSituation.updateCurrentSituation();
            }
         }

         if (!loadScenario && this.getCivID() != Game.player.iCivID) {
            AI_UnlockedTechnology.unlockedTechnology(this.getCivID(), iTechID);
         }
      }
   }

   public void updateTechnologyBonuses(int iTechID) {
      if (TechnologyTree.lTechnology.get(iTechID).BattleWidth != 0) {
         this.civBonuses.BattleWidth = this.civBonuses.BattleWidth + TechnologyTree.lTechnology.get(iTechID).BattleWidth;
      }

      if (TechnologyTree.lTechnology.get(iTechID).UnitsAttack != 0) {
         this.civBonuses.UnitsAttack = this.civBonuses.UnitsAttack + TechnologyTree.lTechnology.get(iTechID).UnitsAttack;
      }

      if (TechnologyTree.lTechnology.get(iTechID).UnitsDefense != 0) {
         this.civBonuses.UnitsDefense = this.civBonuses.UnitsDefense + TechnologyTree.lTechnology.get(iTechID).UnitsDefense;
      }

      if (TechnologyTree.lTechnology.get(iTechID).GeneralAttack != 0) {
         this.civBonuses.GeneralAttack = this.civBonuses.GeneralAttack + TechnologyTree.lTechnology.get(iTechID).GeneralAttack;
      }

      if (TechnologyTree.lTechnology.get(iTechID).GeneralDefense != 0) {
         this.civBonuses.GeneralDefense = this.civBonuses.GeneralDefense + TechnologyTree.lTechnology.get(iTechID).GeneralDefense;
      }

      if (TechnologyTree.lTechnology.get(iTechID).MaxMorale != 0) {
         this.civBonuses.MaxMorale = this.civBonuses.MaxMorale + TechnologyTree.lTechnology.get(iTechID).MaxMorale / 100.0F;
      }

      if (TechnologyTree.lTechnology.get(iTechID).Discipline != 0) {
         this.civBonuses.Discipline = this.civBonuses.Discipline + TechnologyTree.lTechnology.get(iTechID).Discipline / 100.0F;
      }

      if (TechnologyTree.lTechnology.get(iTechID).MaximumLevelOfCapitalCity != 0) {
         this.civBonuses.MaximumLevelOfCapitalCity = this.civBonuses.MaximumLevelOfCapitalCity
            + TechnologyTree.lTechnology.get(iTechID).MaximumLevelOfCapitalCity;
      }

      if (TechnologyTree.lTechnology.get(iTechID).MaximumLevelOfTheMilitaryAcademy != 0) {
         this.civBonuses.MaximumLevelOfTheMilitaryAcademy = this.civBonuses.MaximumLevelOfTheMilitaryAcademy
            + TechnologyTree.lTechnology.get(iTechID).MaximumLevelOfTheMilitaryAcademy;
      }

      if (TechnologyTree.lTechnology.get(iTechID).MaximumLevelOfTheMilitaryAcademyForGenerals != 0) {
         this.civBonuses.MaximumLevelOfTheMilitaryAcademyForGenerals = this.civBonuses.MaximumLevelOfTheMilitaryAcademyForGenerals
            + TechnologyTree.lTechnology.get(iTechID).MaximumLevelOfTheMilitaryAcademyForGenerals;
      }

      if (TechnologyTree.lTechnology.get(iTechID).UnlocksNukes) {
         this.canBuildNuke = true;
      }

      if (TechnologyTree.lTechnology.get(iTechID).UnlocksAccessToTheSea) {
         this.canAccessSea = true;
      }

      if (TechnologyTree.lTechnology.get(iTechID).UnlocksColonization) {
         this.canColonize = true;
      }
   }

   public boolean getAvailableToResearch(int iTechID) {
      if (this.getTechResearched(iTechID) && !TechnologyTree.lTechnology.get(iTechID).Repeatable) {
         return false;
      } else {
         return TechnologyTree.lTechnology.get(iTechID).RequiredTech >= 0
               && !Game.getCiv(this.iCivID).getTechResearched(TechnologyTree.lTechnology.get(iTechID).RequiredTech)
            ? false
            : TechnologyTree.lTechnology.get(iTechID).RequiredTech2 < 0
               || Game.getCiv(this.iCivID).getTechResearched(TechnologyTree.lTechnology.get(iTechID).RequiredTech2);
      }
   }

   public boolean getTechResearched(int iTechID) {
      try {
         return iTechID < 0 ? true : this.lTechResearched.get(iTechID);
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
         return true;
      }
   }

   public float getResearchProgress(int iTechID) {
      for (int i = this.lResearching.size() - 1; i >= 0; i--) {
         if (this.lResearching.get(i).iTechID == iTechID) {
            return this.lResearching.get(i).fProgress / TechnologyTree.getResearchCost(this.lResearching.get(i).iTechID, this.getCivID());
         }
      }

      return 0.0F;
   }

   public final void updateBestUnits() {
      this.unitsBest.clear();
      this.unitsBest_FirstLine.clear();
      this.unitsBest_Flank.clear();
      this.unitsBest_Support.clear();
      this.unitsBestSize = 0;
      this.unitsBest_FirstLineSize = 0;
      this.unitsBest_FlankSize = 0;
      this.unitsBest_SupportSize = 0;
      this.unitsBest_SiegeSize = 0;
      int i = 0;

      for (int jSize = this.unlockedUnits.size(); i < ArmyManager.iUnitsTypesSize; i++) {
         int bestID = -1;

         for (int j = 0; j < jSize; j++) {
            if (this.unlockedUnits.get(j).unitID == i) {
               if (bestID < 0) {
                  bestID = j;
               } else if (ArmyManager.lArmy.get(this.unlockedUnits.get(bestID).unitID).get(this.unlockedUnits.get(bestID).armyID).UnitLevel
                  < ArmyManager.lArmy.get(this.unlockedUnits.get(j).unitID).get(this.unlockedUnits.get(j).armyID).UnitLevel) {
                  bestID = j;
               }
            }
         }

         if (bestID >= 0) {
            this.unitsBest.add(this.unlockedUnits.get(bestID));
         }
      }

      this.unitsBestSize = this.unitsBest.size();

      for (int ix = this.unitsBestSize - 1; ix >= 0; ix--) {
         if (!ArmyManager.lArmy.get(this.unitsBest.get(ix).unitID).get(this.unitsBest.get(ix).armyID).isSettler) {
            if (ArmyManager.lUnitsTypes.get(this.unitsBest.get(ix).unitID).Line == 0) {
               this.unitsBest_FirstLine.add(this.unitsBest.get(ix));
            } else if (ArmyManager.lUnitsTypes.get(this.unitsBest.get(ix).unitID).Line == 1) {
               this.unitsBest_Flank.add(this.unitsBest.get(ix));
            } else if (ArmyManager.lArmy.get(this.unitsBest.get(ix).unitID).get(this.unitsBest.get(ix).armyID).SiegeUnit) {
               this.unitsBest_Siege.add(this.unitsBest.get(ix));
            } else {
               this.unitsBest_Support.add(this.unitsBest.get(ix));
            }
         }
      }

      this.unitsBest_FirstLineSize = this.unitsBest_FirstLine.size();
      this.unitsBest_FlankSize = this.unitsBest_Flank.size();
      this.unitsBest_SupportSize = this.unitsBest_Support.size();
      this.unitsBest_SiegeSize = this.unitsBest_Siege.size();
   }

   public int getResearchedTechnologies() {
      int out = 0;

      for (int i = this.lTechResearched.size() - 1; i >= 0; i--) {
         if (this.lTechResearched.get(i)) {
            out++;
         }
      }

      return out;
   }

   public int getInfrastructure() {
      int out = 0;

      for (int i = this.getNumOfProvinces() - 1; i >= 0; i--) {
         out += Game.getProvince(this.getProvinceID(i)).getInfrastructure();
      }

      return out;
   }

   public int getConstructedBuildings() {
      int out = 0;

      for (int i = this.getNumOfProvinces() - 1; i >= 0; i--) {
         out += Game.getProvince(this.getProvinceID(i)).iBuildingsSize;
      }

      return out;
   }

   public boolean isBuildingResearched(int building, int buildingID) {
      for (int i = this.unlockedBuildings.size() - 1; i >= 0; i--) {
         if (this.unlockedBuildings.get(i).building == building && this.unlockedBuildings.get(i).buildingID == buildingID) {
            return true;
         }
      }

      return false;
   }

   public boolean isUnitResearched(int unitID, int armyID) {
      for (int i = this.unlockedUnits.size() - 1; i >= 0; i--) {
         if (this.unlockedUnits.get(i).unitID == unitID && this.unlockedUnits.get(i).armyID == armyID) {
            return true;
         }
      }

      return false;
   }

   public boolean isUnitBest(int unitID, int armyID) {
      for (int i = this.unitsBest.size() - 1; i >= 0; i--) {
         if (this.unitsBest.get(i).unitID == unitID && this.unitsBest.get(i).armyID == armyID) {
            return true;
         }
      }

      return false;
   }

   public final int getUnitsBest_AttackDefense() {
      int out = 0;

      try {
         for (int i = this.unitsBest.size() - 1; i >= 0; i--) {
            if (ArmyManager.lArmy.get(this.unitsBest.get(i).unitID).get(this.unitsBest.get(i).armyID).getAttack()
                  + ArmyManager.lArmy.get(this.unitsBest.get(i).unitID).get(this.unitsBest.get(i).armyID).getDefense()
               > out) {
               out = ArmyManager.lArmy.get(this.unitsBest.get(i).unitID).get(this.unitsBest.get(i).armyID).getAttack()
                  + ArmyManager.lArmy.get(this.unitsBest.get(i).unitID).get(this.unitsBest.get(i).armyID).getDefense();
            }
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }

      return out;
   }

   public final void updateResearchPerMonth() {
      this.fResearchPerMonth = 0.0F;

      for (int i = 0; i < this.getNumOfProvinces(); i++) {
         this.fResearchPerMonth = this.fResearchPerMonth + Game.getProvince(this.getProvinceID(i)).provBonuses.ResearchPoints;
      }

      this.fResearchPerMonth = this.fResearchPerMonth
         + (GameValues.research.BASE_RESEARCH + this.civBonuses.ResearchPoints + GameValues.civRank.CIV_RANK_MONTHLY_RESEARCH[this.iCivRankID]);
      this.fResearchPerMonth = this.fResearchPerMonth
         * Math.max(
            0.0F,
            1.0F
               + this.civBonuses.Research / 100.0F
               + GameValues.budget.TAXATION_LEVEL_RESEARCH[this.getTaxationLevel()]
               + GameValues.budget.RESEARCH_LEVEL_RESEARCH[this.getResearchLevel()]
         );
      this.fResearchPerMonth = Math.min(TechnologyTree.getMaxResearch(this.getCivID()), this.fResearchPerMonth);
   }

   public final float getResearchFromBuildings() {
      float fOut = 0.0F;

      for (int i = 0; i < this.getNumOfProvinces(); i++) {
         fOut += Game.getProvince(this.getProvinceID(i)).provBonuses.ResearchPoints;
      }

      return fOut;
   }

   public final void updateLegacyPerMonth() {
      this.fLegacyPerMonth = this.civBonuses.MonthlyLegacy;

      for (int i = 0; i < this.getNumOfProvinces(); i++) {
         this.fLegacyPerMonth = this.fLegacyPerMonth + Game.getProvince(this.getProvinceID(i)).provBonuses.MonthlyLegacy;
      }

      this.fLegacyPerMonth = this.fLegacyPerMonth
         + (
            GameValues.civRank.CIV_RANK_MONTHLY_LEGACY[this.iCivRankID]
               + Game.getLegacyPerUniqueResources(this.getCivID())
               + this.diplomacy.getRivalsLegacy(this.getCivID())
         );
      this.fLegacyPerMonth = this.fLegacyPerMonth * (1.0F + this.civBonuses.MonthlyLegacy_Percentage);
      this.fLegacyPerMonth = this.fLegacyPerMonth * (1.0F + GameValues.budget.TAXATION_LEVEL_LEGACY[this.getTaxationLevel()]);
   }

   public final void addLegacy(float fValue) {
      this.fLegacy = Math.max(GameValues.legacy.MIN_LEGACY_POINTS, Math.min(GameValues.legacy.MAX_LEGACY_POINTS, this.fLegacy + fValue));
   }

   public final void updateManpowerPerMonth() {
      this.fManpowerMax_ToLord = 0.0;
      this.fManpowerMax = this.civBonuses.MaxManpower;

      try {
         for (int i = 0; i < this.getNumOfProvinces(); i++) {
            this.fManpowerMax = this.fManpowerMax + Game.getProvince(this.getProvinceID(i)).provBonuses.MaximumManpower;
            this.fManpowerMax = this.fManpowerMax + Game.getManpowerMaxFromProvinceManpowerLvl(this.getProvinceID(i));
         }
      } catch (Exception var2) {
      }

      this.fManpowerMax = this.fManpowerMax + GameValues.civRank.CIV_RANK_MANPOWER_MAX[this.iCivRankID];
      if (this.getPuppetOfCivID() != this.getCivID()) {
         this.fManpowerMax_ToLord = this.fManpowerMax
            * GameValues.vassal.VASSAL_MANPOWER_TO_LORD[Game.getCiv(this.getPuppetOfCivID()).diplomacy.getVassal_ManpowerLevel(this.getCivID())];
         this.fManpowerMax = Math.max(0.0, this.fManpowerMax - this.fManpowerMax_ToLord);
      }

      this.fManpowerMax = this.fManpowerMax + GameValues.manpower.MANPOWER_MAX_BASE;
      this.fManpowerMax = this.fManpowerMax * (1.0F + this.civBonuses.MaxManpower_Percentage);

      for (int i = 0; i < this.diplomacy.lVassals.size(); i++) {
         this.fManpowerMax = this.fManpowerMax + Game.getCiv(this.diplomacy.lVassals.get(i).c).fManpowerMax_ToLord;
      }

      this.fManpowerMax = this.fManpowerMax + this.diplomacy.getRivalsManpower(this.getCivID());

      for (int i = 0; i < this.inAllianceSize; i++) {
         if (Game.alliancesSpecial.get(this.inAlliance.get(i)).iLeaderCivID == this.getCivID()
            && Game.alliancesSpecial.get(this.inAlliance.get(i)).typeOfAlliance == 0) {
            this.fManpowerMax = this.fManpowerMax + HREManager.getManpower_Emperor(this.inAlliance.get(i));
         }
      }

      this.fManpowerPerMonth = Game.getManpowerPerMonth(this.getCivID(), this.fManpowerMax)
         * (1.0F + GameValues.budget.MILITARY_LEVEL_MANPOWER[this.getMilitaryLevel()]);
   }

   public final float getManpowerMax_Provinces_INFO() {
      float out = 0.0F;

      for (int i = 0; i < this.getNumOfProvinces(); i++) {
         out += Game.getProvince(this.getProvinceID(i)).provBonuses.MaximumManpower;
         out += Game.getManpowerMaxFromProvinceManpowerLvl(this.getProvinceID(i));
      }

      return out * (1.0F + this.civBonuses.MaxManpower_Percentage);
   }

   public final float getManpowerMax_Vassals_INFO() {
      float out = 0.0F;

      for (int i = 0; i < this.diplomacy.lVassals.size(); i++) {
         out = (float)(out + Game.getCiv(this.diplomacy.lVassals.get(i).c).fManpowerMax_ToLord);
      }

      return out;
   }

   public final void updateDiplomacyPerMonth() {
      this.fDiplomacyPerMonth = GameValues.diplomacy.DIPLOMACY_POINTS_PER_MONTH_BASE_VALUE * (1.0F + this.civBonuses.DiplomacyPoints)
         + CivilizationRanking.getDiplomacyPoints_RankStar(this.getCivID());
      this.fDiplomacyMax = GameValues.diplomacy.DIPLOMACY_POINTS_MAX + this.getNumOfProvinces() * GameValues.diplomacy.DIPLOMACY_POINTS_MAX_PER_PROVINCE;
   }

   public final float getDiplomacyPerMonth() {
      return this.fDiplomacyPerMonth
         - GameValues.diplomacy.DIPLOMACY_IMPROVE_RELATIONS_COST_PER_MONTH * this.diplomacy.iImprovingRelationsSize
         - GameValues.diplomacy.DIPLOMACY_DAMAGE_RELATIONS_COST_PER_MONTH * this.diplomacy.iDamagingRelationsSize;
   }

   public final void setDiplomacyPerMonth(float fDiplomacyPerMonth) {
      this.fDiplomacyPerMonth = fDiplomacyPerMonth;
   }

   public final void updateIncome() {
      if (this.getPuppetOfCivID() == this.getCivID()) {
         this.fTotalIncomePerMonth = GameValues.civRank.CIV_BASE_INCOME[this.iCivRankID];
      } else {
         this.fTotalIncomePerMonth = GameValues.civRank.CIV_BASE_INCOME_VASSAL[this.iCivRankID];
      }

      this.fTotalExpensesPerMonth = 0.0F;

      for (int i = 0; i < this.getNumOfProvinces(); i++) {
         this.fTotalIncomePerMonth = this.fTotalIncomePerMonth + Game.getProvince(this.getProvinceID(i)).fProvinceIncome;
         this.fTotalExpensesPerMonth = this.fTotalExpensesPerMonth + Game.getProvince(this.getProvinceID(i)).fProvinceMaintenance;
      }

      for (int i = 0; i < this.inAllianceSize; i++) {
         if (Game.alliancesSpecial.get(this.inAlliance.get(i)).iLeaderCivID == this.getCivID()
            && Game.alliancesSpecial.get(this.inAlliance.get(i)).typeOfAlliance == 0) {
            this.fTotalIncomePerMonth = this.fTotalIncomePerMonth + HREManager.getIncome_Emperor(this.inAlliance.get(i));
         }
      }

      this.fTotalExpensesPerMonth = this.fTotalExpensesPerMonth + this.fArmyMaintenance;
      this.fTotalExpensesPerMonth = this.fTotalExpensesPerMonth + Game.getResearchCost(this.getCivID());
      if (this.getPuppetOfCivID() != this.getCivID()) {
         this.fExpenseVassal = Game.getIncomeFromVassal(this.getPuppetOfCivID(), this.getCivID());
         this.fTotalExpensesPerMonth = this.fTotalExpensesPerMonth + this.fExpenseVassal;
      } else {
         this.fExpenseVassal = 0.0F;
      }
   }

   public final void updateTotalIncomePerMonth() {
      this.updateIncome();
      this.updateVassalsIncome();
      this.fTotalIncomePerMonth = this.fTotalIncomePerMonth + (this.fIncomeLord - this.fLoansCost + this.civBonuses.MaintenanceCost);
   }

   public final void updateArmyMaintenance() {
      this.fArmyMaintenance = 0.0F;

      for (int i = this.iArmyPositionSize - 1; i >= 0; i--) {
         Province province = Game.getProvince(this.getArmyPosition(i));

         for (int j = province.getArmySize() - 1; j >= 0; j--) {
            ArmyDivision army = Game.getProvince(this.getArmyPosition(i)).getArmy(j);
            if (army.civID == this.getCivID() && this.getArmyPositionKey(i).equals(army.key)) {
               this.fArmyMaintenance = this.fArmyMaintenance + army.fMaintenanceCost;
            }
         }
      }

      this.fArmyMaintenance = this.fArmyMaintenance
         * Math.max(0.05F, 1.0F + this.civBonuses.ArmyMaintenance / 100.0F + GameValues.budget.MILITARY_LEVEL_ARMY_MAINTENANCE[this.getMilitaryLevel()])
         * (1.0F + Math.max(0.0F, (float)this.iRegiments / this.iRegimentsLimit - 1.0F) * GameValues.army.ARMY_MAINTENANCE_OVER_REGIMENTS_LIMIT_MODIFIER);
   }

   public final float getAdditionalExpenses() {
      return this.civBonuses.MaintenanceCost + this.fExpenseVassal;
   }

   public float getIncomeTaxation() {
      float out = 0.0F;

      for (int i = 0; i < this.getNumOfProvinces(); i++) {
         out += Game.getProvince(this.getProvinceID(i)).fProvinceIncomeTaxation;
      }

      return out;
   }

   public float getIncomeEconomy() {
      float out = 0.0F;

      for (int i = 0; i < this.getNumOfProvinces(); i++) {
         out += Game.getProvince(this.getProvinceID(i)).fProvinceIncomeEconomy;
      }

      return out;
   }

   public float getIncomeProduction() {
      float out = 0.0F;

      for (int i = 0; i < this.getNumOfProvinces(); i++) {
         out += Game.getProvince(this.getProvinceID(i)).fProvinceIncomeProduction;
      }

      return out;
   }

   public float getIncomeBuildings() {
      float out = 0.0F;

      for (int i = 0; i < this.getNumOfProvinces(); i++) {
         out += Game.getProvince(this.getProvinceID(i)).provBonuses.MonthlyIncome;
      }

      return out;
   }

   public float getProvinceMaintenance() {
      float out = 0.0F;

      for (int i = 0; i < this.getNumOfProvinces(); i++) {
         out += Game.getProvince(this.getProvinceID(i)).fProvinceMaintenance;
      }

      return out;
   }

   public float getBuildingsMaintenance() {
      float out = 0.0F;

      for (int i = 0; i < this.getNumOfProvinces(); i++) {
         out += Game.getProvince(this.getProvinceID(i)).getProvinceBuildingsMaintenance();
      }

      return out;
   }

   public final boolean takeLoan() {
      if (this.iLoansSize >= Game.getLoanMaxNumber(this.getCivID())) {
         return false;
      } else {
         float fValue = Game.getLoanValue(this.getCivID()) + Game.getLoanInterestValue(this.getCivID());
         this.loans.add(new Loan(fValue));
         this.iLoansSize = this.loans.size();
         this.fGold += fValue;
         this.fLoansCost = this.fLoansCost + this.loans.get(this.iLoansSize - 1).fInterestPerMonth;
         this.fTotalExpensesPerMonth = this.fTotalExpensesPerMonth + this.loans.get(this.iLoansSize - 1).fInterestPerMonth;
         this.setInflation(this.civData3.getInflation() + GameValues.loan.LOAN_INFLATION);
         Game.gameThread.addCivUpdateLoans(this.getCivID());
         Game.gameThread.addCivUpdateTotalIncomePerMonth(this.getCivID());
         return true;
      }
   }

   public final void addLoan_Load(Loan nLoan) {
      this.loans.add(nLoan);
      this.iLoansSize = this.loans.size();
      this.fLoansCost = this.fLoansCost + nLoan.fInterestPerMonth;
      this.fTotalExpensesPerMonth = this.fTotalExpensesPerMonth + nLoan.fInterestPerMonth;
   }

   public final void updateLoans() {
      if (this.iLoansSize > 0) {
         for (int i = this.iLoansSize - 1; i >= 0; i--) {
            if (Game_Calendar.TURN_ID >= this.loans.get(i).iExpires_TurnID) {
               this.fLoansCost = this.fLoansCost - this.loans.get(i).fInterestPerMonth;
               this.fTotalExpensesPerMonth = this.fTotalExpensesPerMonth - this.loans.get(i).fInterestPerMonth;
               this.loans.remove(i);
               this.iLoansSize = this.loans.size();
               if (this.iLoansSize == 0) {
                  this.fLoansCost = 0.0F;
                  Game.gameThread.removeCivUpdateLoans(this.getCivID());
                  Game.gameThread.addCivUpdateTotalIncomePerMonth(this.getCivID());
               }
            }
         }
      }
   }

   public final boolean repayLoan(int i) {
      try {
         if (i < this.iLoansSize) {
            float toRepay = this.loans.get(i).fLoanValue - this.loans.get(i).getLoanValueLeft();
            if (this.fGold >= toRepay) {
               this.fGold -= toRepay;
               this.fLoansCost = this.fLoansCost - this.loans.get(i).fInterestPerMonth;
               this.loans.remove(i);
               this.iLoansSize = this.loans.size();
               if (this.iLoansSize == 0) {
                  this.fLoansCost = 0.0F;
                  Game.gameThread.removeCivUpdateLoans(this.getCivID());
                  Game.gameThread.addCivUpdateTotalIncomePerMonth(this.getCivID());
               }

               return true;
            }
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }

      return false;
   }

   public final void updateProvincesIncomeAndExpenses() {
      for (int i = 0; i < this.getNumOfProvinces(); i++) {
         Game.getProvince(this.getProvinceID(i)).updateProvinceIncome();
      }

      this.updateResearchPerMonth();
      this.updateLegacyPerMonth();
      this.updateTotalIncomePerMonth();
   }

   public final void updateTaxationLevel(int nTaxationLevel) {
      if (this.getTaxationLevel() != nTaxationLevel) {
         this.setTaxationLevel(nTaxationLevel);

         for (int i = 0; i < this.getNumOfProvinces(); i++) {
            Game.getProvince(this.getProvinceID(i)).updateProvinceIncome();
         }

         this.updateResearchPerMonth();
         this.updateLegacyPerMonth();
         this.updateTotalIncomePerMonth();
      }
   }

   public final void updateResearchLevel(int nResearchLevel) {
      if (this.getResearchLevel() != nResearchLevel) {
         this.setResearchLevel(nResearchLevel);
         this.updateResearchPerMonth();
         this.updateTotalIncomePerMonth();
      }
   }

   public final void updateMilitaryLevel(int nMilitaryLevel) {
      if (this.getMilitaryLevel() != nMilitaryLevel) {
         this.setMilitaryLevel(nMilitaryLevel);
         this.updateManpowerPerMonth();
         this.updateArmyMaintenance();
         this.updateTotalIncomePerMonth();
      }
   }

   public final boolean reduceInflation() {
      if (this.civData3.getInflation() > 0.0F && this.fLegacy > 0.0F) {
         if (this.fLegacy >= Game.getInflationReduceCost_Legacy(this.getCivID())) {
            this.fLegacy = this.fLegacy - Game.getInflationReduceCost_Legacy(this.getCivID());
            this.civData3.setInflation(0.0F);
         } else {
            float maxDecrease = this.fLegacy / GameValues.inflation.INFLATION_REDUCE_COST_LEGACY_PER_INFLATION;
            this.civData3.setInflation(Math.max(0.0F, this.civData3.getInflation() - maxDecrease / 100.0F));
            this.fLegacy = this.fLegacy - maxDecrease * GameValues.inflation.INFLATION_REDUCE_COST_LEGACY_PER_INFLATION;
         }

         return true;
      } else {
         return false;
      }
   }

   public final float getInflation() {
      return this.civData3.getInflation() + this.civBonuses.Inflation;
   }

   public final float getInflation_Just() {
      return this.civData3.getInflation();
   }

   public final void setInflation(float fInflation) {
      this.civData3.setInflation(Math.max(0.0F, Math.min(fInflation, GameValues.inflation.INFLATION_MAX)));
   }

   public final float getCorruption() {
      return this.civData3.getCorruption() + this.civBonuses.Corruption;
   }

   public final void setCorruption(float fCorruption) {
      this.civData3.setCorruption(Math.max(GameValues.supremeCourt.CORRUPTION_MIN, Math.min(fCorruption, GameValues.supremeCourt.CORRUPTION_MAX)));
   }

   public final void addGold(float nGold) {
      if (this.fGold < Game.getMaxAmountOfGold(this.getCivID())) {
         this.fGold = Math.min((float)Game.getMaxAmountOfGold(this.getCivID()), this.fGold + nGold);
      } else if (nGold < 0.0F) {
         this.fGold += nGold;
      }
   }

   public final boolean upgradeMilitaryAcademyForGenerals() {
      if (this.fGold >= Game.getMilitaryAcademyForGenerals_Cost(this.getCivID())
         && this.getMilitaryAcademyForGeneralsLevel() < Game.getMilitaryAcademyForGenerals_MaxLvl(this.getCivID())) {
         this.fGold = this.fGold - Game.getMilitaryAcademyForGenerals_Cost(this.getCivID());
         this.civBonuses.GeneralAttack = this.civBonuses.GeneralAttack - Game.getMilitaryAcademyForGenerals_GeneralAttack(this.getCivID());
         this.civBonuses.GeneralDefense = this.civBonuses.GeneralDefense - Game.getMilitaryAcademyForGenerals_GeneralDefense(this.getCivID());
         this.civBonuses.MaintenanceCost = this.civBonuses.MaintenanceCost - Game.getMilitaryAcademyForGenerals_MaintenanceCost(this.getCivID());
         this.setMilitaryAcademyForGeneralsLevel(this.getMilitaryAcademyForGeneralsLevel() + 1);
         this.civBonuses.GeneralAttack = this.civBonuses.GeneralAttack + Game.getMilitaryAcademyForGenerals_GeneralAttack(this.getCivID());
         this.civBonuses.GeneralDefense = this.civBonuses.GeneralDefense + Game.getMilitaryAcademyForGenerals_GeneralDefense(this.getCivID());
         this.civBonuses.MaintenanceCost = this.civBonuses.MaintenanceCost + Game.getMilitaryAcademyForGenerals_MaintenanceCost(this.getCivID());
         Game.gameThread.addCivUpdateTotalIncomePerMonth(this.getCivID());
         return true;
      } else {
         return false;
      }
   }

   public final void buildMilitaryAcademyForGenerals_Bonuses() {
      if (this.getMilitaryAcademyForGeneralsLevel() > 0) {
         this.civBonuses.GeneralAttack = this.civBonuses.GeneralAttack + Game.getMilitaryAcademyForGenerals_GeneralAttack(this.getCivID());
         this.civBonuses.GeneralDefense = this.civBonuses.GeneralDefense + Game.getMilitaryAcademyForGenerals_GeneralDefense(this.getCivID());
         this.civBonuses.MaintenanceCost = this.civBonuses.MaintenanceCost + Game.getMilitaryAcademyForGenerals_MaintenanceCost(this.getCivID());
      }
   }

   public final boolean upgradeMilitaryAcademy() {
      if (this.fGold >= Game.getMilitaryAcademy_Cost(this.getCivID()) && this.getMilitaryAcademyLevel() < Game.getMilitaryAcademy_MaxLvl(this.getCivID())) {
         this.fGold = this.fGold - Game.getMilitaryAcademy_Cost(this.getCivID());
         this.civBonuses.UnitsAttack = this.civBonuses.UnitsAttack - Game.getMilitaryAcademy_Attack(this.getCivID());
         this.civBonuses.UnitsDefense = this.civBonuses.UnitsDefense - Game.getMilitaryAcademy_Defense(this.getCivID());
         this.civBonuses.MaintenanceCost = this.civBonuses.MaintenanceCost - Game.getMilitaryAcademy_MaintenanceCost(this.getCivID());
         this.civBonuses.RegimentsLimit = this.civBonuses.RegimentsLimit - Game.getMilitaryAcademy_RegimentsLimit(this.getCivID());
         this.setMilitaryAcademyLevel(this.getMilitaryAcademyLevel() + 1);
         this.civBonuses.UnitsAttack = this.civBonuses.UnitsAttack + Game.getMilitaryAcademy_Attack(this.getCivID());
         this.civBonuses.UnitsDefense = this.civBonuses.UnitsDefense + Game.getMilitaryAcademy_Defense(this.getCivID());
         this.civBonuses.MaintenanceCost = this.civBonuses.MaintenanceCost + Game.getMilitaryAcademy_MaintenanceCost(this.getCivID());
         this.civBonuses.RegimentsLimit = this.civBonuses.RegimentsLimit + Game.getMilitaryAcademy_RegimentsLimit(this.getCivID());
         Game.gameThread.addCivUpdateTotalIncomePerMonth(this.getCivID());
         this.updateRegimentsLimit();
         return true;
      } else {
         return false;
      }
   }

   public final void buildMilitaryAcademy_Bonuses() {
      if (this.getMilitaryAcademyLevel() > 0) {
         this.civBonuses.UnitsAttack = this.civBonuses.UnitsAttack + Game.getMilitaryAcademy_Attack(this.getCivID());
         this.civBonuses.UnitsDefense = this.civBonuses.UnitsDefense + Game.getMilitaryAcademy_Defense(this.getCivID());
         this.civBonuses.MaintenanceCost = this.civBonuses.MaintenanceCost + Game.getMilitaryAcademy_MaintenanceCost(this.getCivID());
         this.civBonuses.RegimentsLimit = this.civBonuses.RegimentsLimit + Game.getMilitaryAcademy_RegimentsLimit(this.getCivID());
      }
   }

   public final boolean upgradeNuclearReactor() {
      if (this.fGold >= Game.getNuclearReactor_Cost(this.getCivID()) && this.getNuclearReactorLevel() < Game.getNuclearReactor_MaxLvl(this.getCivID())) {
         this.fGold = this.fGold - Game.getNuclearReactor_Cost(this.getCivID());
         this.civBonuses.ProductionEfficiency = this.civBonuses.ProductionEfficiency - Game.getNuclearReactor_ProductionEfficiency(this.getCivID());
         this.setNuclearReactorLevel(this.getNuclearReactorLevel() + 1);
         this.civBonuses.ProductionEfficiency = this.civBonuses.ProductionEfficiency + Game.getNuclearReactor_ProductionEfficiency(this.getCivID());

         for (int i = 0; i < this.getNumOfProvinces(); i++) {
            Game.getProvince(this.getProvinceID(i)).updateProvinceIncome();
         }

         Game.gameThread.addCivUpdateTotalIncomePerMonth(this.getCivID());
         return true;
      } else {
         return false;
      }
   }

   public final void buildNuclearReactor_Bonuses() {
      if (this.getNuclearReactorLevel() > 0) {
         this.civBonuses.ProductionEfficiency = this.civBonuses.ProductionEfficiency + Game.getNuclearReactor_ProductionEfficiency(this.getCivID());
      }
   }

   public final boolean upgradeCapitalCity() {
      if (this.fGold >= Game.getCapital_Cost(this.getCivID()) && this.getCapitalLevel() < Game.getCapital_MaxLvl(this.getCivID())) {
         this.fGold = this.fGold - Game.getCapital_Cost(this.getCivID());
         this.civBonuses.MonthlyIncome = this.civBonuses.MonthlyIncome - Game.getCapital_Income(this.getCivID());
         this.civBonuses.ProvinceMaintenance = this.civBonuses.ProvinceMaintenance - Game.getCapital_ProvincesMaintenance(this.getCivID()) * 100.0F;
         this.setCapitalLevel(this.getCapitalLevel() + 1);
         this.civBonuses.MonthlyIncome = this.civBonuses.MonthlyIncome + Game.getCapital_Income(this.getCivID());
         this.civBonuses.ProvinceMaintenance = this.civBonuses.ProvinceMaintenance + Game.getCapital_ProvincesMaintenance(this.getCivID()) * 100.0F;
         Game.gameThread.addCivUpdateTotalIncomePerMonth(this.getCivID());
         return true;
      } else {
         return false;
      }
   }

   public final void buildCapitalCity_Bonuses() {
      if (this.getCapitalLevel() > 0) {
         this.civBonuses.MonthlyIncome = this.civBonuses.MonthlyIncome + Game.getCapital_Income(this.getCivID());
         this.civBonuses.ProvinceMaintenance = this.civBonuses.ProvinceMaintenance + Game.getCapital_ProvincesMaintenance(this.getCivID()) * 100.0F;
      }
   }

   public final void buildSupremeCourt_Bonuses() {
      this.civData3
         .setCorruption(this.civData3.getCorruption() + GameValues.supremeCourt.SUPREME_COURT_CORRUPTION_REDUCTION_PER_LVL * this.getSupremeCourtLevel());
   }

   public final boolean upgradeSupremeCourt() {
      if (this.fGold >= Game.getSupremeCourt_Cost(this.getCivID()) && this.getSupremeCourtLevel() < Game.getSupremeCourt_MaxLvl(this.getCivID())) {
         this.fGold = this.fGold - Game.getSupremeCourt_Cost(this.getCivID());
         this.setSupremeCourtLevel(this.getSupremeCourtLevel() + 1);
         this.civData3.setCorruption(this.civData3.getCorruption() + GameValues.supremeCourt.SUPREME_COURT_CORRUPTION_REDUCTION_PER_LVL);

         for (int i = 0; i < this.getNumOfProvinces(); i++) {
            Game.getProvince(this.getProvinceID(i)).updateProvinceIncome();
         }

         Game.gameThread.addCivUpdateTotalIncomePerMonth(this.getCivID());
         return true;
      } else {
         return false;
      }
   }

   public final void updateRegimentsLimit() {
      this.iRegimentsLimit = (int)(
         (
               Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).REGIMENTS_LIMIT_BASE_VALUE
                     + this.civBonuses.RegimentsLimit
                     + GameValues.army.REGIMENTS_LIMIT_PER_VASSAL * this.diplomacy.iVassalsSize
                  + this.getRegimentsLimit_FromAllianceSpecial()
                  + this.getRegimentsLimit_Manpower()
            )
            * (this.getCivID() == this.getPuppetOfCivID() ? 1.0F : GameValues.army.REGIMENTS_LIMIT_VASSAL)
      );
   }

   public final int getRegimentsLimit_Manpower() {
      double sum = 0.0;

      for (int i = this.getNumOfProvinces() - 1; i >= 0; i--) {
         sum += Game.getProvince(this.getProvinceID(i)).getManpower();
      }

      return (int)(sum * GameValues.army.REGIMENTS_LIMIT_MANPOWER_LEVEL);
   }

   public final float getRegimentsLimit_FromAllianceSpecial() {
      float sum = 0.0F;

      for (int i = 0; i < this.inAllianceSize; i++) {
         if (Game.alliancesSpecial.get(this.inAlliance.get(i)).iLeaderCivID == this.getCivID()
            && Game.alliancesSpecial.get(this.inAlliance.get(i)).typeOfAlliance == 0) {
            sum += GameValues.hre.HRE_EMPEROR_REGIMENTS;
         }
      }

      return sum;
   }

   public final void updateMorale_Reinforce() {
      float maxMorale = this.getMaxMorale();
      float moraleRecovery = this.getMoraleRecoveryPerMonth();
      int maxReinforce = this.getMaxReinforce();
      float maxRegimentSize = Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).REGIMENT_SIZE;
      float reinforceCost = 0.0F;

      for (int i = 0; i < this.iArmyPositionSize; i++) {
         Province province = Game.getProvince(this.getArmyPosition(i));

         for (int j = 0; j < province.getArmySize(); j++) {
            ArmyDivision army = province.getArmy(j);
            if (!Game.getProvince(army.provinceID).getSeaProvince()
               && !army.inBattle
               && !army.inRetreat
               && army.civID == this.getCivID()
               && army.key.equals(this.getArmyPositionKey(i))) {
               army.updateMorale_Regiments(maxMorale, moraleRecovery);
               if (!(this.fManpower > GameValues.army.REINFORCE_MIN_MANPOWER)) {
                  i = this.iArmyPositionSize;
                  break;
               }

               reinforceCost += army.updateReinforce(maxReinforce, maxRegimentSize);
               maxReinforce = (int)Math.max((double)maxReinforce, this.fManpower);
            }
         }
      }

      this.fGold -= reinforceCost;
      if (this.getCivID() == Game.player.iCivID && reinforceCost >= 0.01F) {
         Game.stats.civStats.rf += (int)(reinforceCost * 100.0F);
         Game.player
            .addNotification_Reinforce(
               new Notification(
                  Notification.Notification_Type.REINFORCE_ARMY_COST,
                  Game.lang.get("ReinforceCost") + ": " + CFG.getPrecision2(reinforceCost, reinforceCost > 99.9F ? 1 : (reinforceCost < 0.1F ? 100 : 10)),
                  Images.gold,
                  Game_Calendar.TURN_ID,
                  Notification.Notification_BG.NEUTRAL_BG,
                  (int)(reinforceCost * 100.0F)
               )
            );
      }
   }

   public final float getMaxMorale() {
      return (GameValues.army.MORALE_BASE_VALUE + this.civBonuses.MaxMorale) * GameValues.budget.MILITARY_LEVEL_MORALE[this.getMilitaryLevel()];
   }

   public final float getMoraleRecoveryPerMonth() {
      return GameValues.army.MORALE_RECOVERY_PER_MONTH
         * (1.0F + this.civBonuses.ArmyMoraleRecovery + this.civStability_LostFrom100 * GameValues.civStability.CS_ARMY_MORALE_RECOVERY_PER_POINT);
   }

   public final int getMaxReinforce() {
      return this.fManpower < GameValues.army.REINFORCE_MIN_MANPOWER
         ? 0
         : (int)Math.min(
            this.fManpower,
            (double)(
               Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).REGIMENT_SIZE
                  * (
                     (GameValues.army.REINFORCE_PER_MONTH + this.civBonuses.ReinforcementSpeed)
                        * GameValues.budget.MILITARY_LEVEL_REINFORCE_SPEED[this.getMilitaryLevel()]
                  )
            )
         );
   }

   public final long getPopulationTotal() {
      long out = 0L;

      for (int i = this.getNumOfProvinces() - 1; i >= 0; i--) {
         out += Game.getProvince(this.getProvinceID(i)).getPopulationTotal();
      }

      return out;
   }

   public final float getAverageGrowthRate() {
      float out = 0.0F;

      for (int i = this.getNumOfProvinces() - 1; i >= 0; i--) {
         out += Game.getProvince(this.getProvinceID(i)).getGrowthRateWithBonuses();
      }

      return out / this.getNumOfProvinces();
   }

   public final float getEconomyTotal() {
      float out = 0.0F;

      for (int i = this.getNumOfProvinces() - 1; i >= 0; i--) {
         out += Game.getProvince(this.getProvinceID(i)).getEconomyWithBonuses();
      }

      return out;
   }

   public final float getEconomyTotal_CivRank() {
      float out = 0.0F;

      for (int i = this.getNumOfProvinces() - 1; i >= 0; i--) {
         out += Game.getProvince(this.getProvinceID(i)).getEconomyWithBonuses()
            * (
               1.0F
                  + (Game.getProvince(i).haveACore ? 0.0F : GameValues.civRank.CIV_SCORE_NON_CORE)
                  + (Game.getProvince(this.getProvinceID(i)).getReligion() == this.getReligionID() ? 0.0F : GameValues.civRank.CIV_SCORE_DIFFERENT_RELIGION)
            );
      }

      return out;
   }

   public final float getTaxEfficiencyTotal_CivRank() {
      float out = 0.0F;

      for (int i = this.getNumOfProvinces() - 1; i >= 0; i--) {
         out += Game.getProvince(this.getProvinceID(i)).getTaxEfficiencyWithBonuses()
            * (
               1.0F
                  + (Game.getProvince(i).haveACore ? 0.0F : GameValues.civRank.CIV_SCORE_NON_CORE)
                  + (Game.getProvince(this.getProvinceID(i)).getReligion() == this.getReligionID() ? 0.0F : GameValues.civRank.CIV_SCORE_DIFFERENT_RELIGION)
            );
      }

      return out;
   }

   public final float getTaxEfficiencyTotal() {
      float out = 0.0F;

      for (int i = this.getNumOfProvinces() - 1; i >= 0; i--) {
         out += Game.getProvince(this.getProvinceID(i)).getTaxEfficiencyWithBonuses();
      }

      return out;
   }

   public final float getTotalProvinceIncome() {
      float out = 0.0F;

      for (int i = this.getNumOfProvinces() - 1; i >= 0; i--) {
         out += Game.getProvince(this.getProvinceID(i)).getProvinceIncome();
      }

      return out;
   }

   public final float getTotalProvinceIncomeTax() {
      float out = 0.0F;

      for (int i = this.getNumOfProvinces() - 1; i >= 0; i--) {
         out += Game.getProvince(this.getProvinceID(i)).fProvinceIncomeTaxation;
      }

      return out;
   }

   public final float getAverageTaxEfficiency() {
      float out = 0.0F;

      for (int i = this.getNumOfProvinces() - 1; i >= 0; i--) {
         out += Game.getProvince(this.getProvinceID(i)).getTaxEfficiencyWithBonuses();
      }

      return out / this.getNumOfProvinces();
   }

   public final float getTotalIncome() {
      float out = 0.0F;

      for (int i = this.getNumOfProvinces() - 1; i >= 0; i--) {
         out += Game.getProvince(this.getProvinceID(i)).getProvinceIncome() - Game.getProvince(this.getProvinceID(i)).fProvinceMaintenance;
      }

      return out;
   }

   public final float getTotalLoot() {
      float out = 0.0F;

      for (int i = this.getNumOfProvinces() - 1; i >= 0; i--) {
         out += Game.getLootValue(this.getProvinceID(i));
      }

      return out;
   }

   public final float getTotalIncomeFromEconomy() {
      float out = 0.0F;

      for (int i = this.getNumOfProvinces() - 1; i >= 0; i--) {
         out += Game.getIncomeFromEconomy(this.getProvinceID(i));
      }

      return out;
   }

   public final float getTotalIncomeFromProduction() {
      float out = 0.0F;

      for (int i = this.getNumOfProvinces() - 1; i >= 0; i--) {
         out += ResourcesManager.getMonthlyIncome(this.getProvinceID(i));
      }

      return out;
   }

   public final int getTotalGenerals() {
      int outGenerals = 0;

      for (int i = 0; i < this.iArmyPositionSize; i++) {
         for (int j = 0; j < Game.getProvince(this.getArmyPosition(i)).getArmySize(); j++) {
            if (this.getCivID() == Game.getProvince(this.getArmyPosition(i)).getArmy(j).civID
               && this.getArmyPositionKey(i).equals(Game.getProvince(this.getArmyPosition(i)).getArmy(j).key)
               && Game.getProvince(this.getArmyPosition(i)).getArmy(j).armyGeneral != null) {
               outGenerals++;
            }
         }
      }

      return this.lGeneralsNotAssigned.size() + outGenerals;
   }

   public final int getGeneralsNotAssignedSize() {
      return this.lGeneralsNotAssigned.size();
   }

   public final int getArmySize() {
      int out = 0;

      for (int i = 0; i < this.iArmyPositionSize; i++) {
         for (int j = 0; j < Game.getProvince(this.getArmyPosition(i)).getArmySize(); j++) {
            if (this.getCivID() == Game.getProvince(this.getArmyPosition(i)).getArmy(j).civID
               && this.getArmyPositionKey(i).equals(Game.getProvince(this.getArmyPosition(i)).getArmy(j).key)) {
               out += Game.getProvince(this.getArmyPosition(i)).getArmy(j).iArmy;
            }
         }
      }

      return out;
   }

   public final int getArmyRegimentSize() {
      return this.iRegiments;
   }

   public final int updateArmyRegimentSize() {
      this.iRegiments = 0;

      for (int i = 0; i < this.iArmyPositionSize; i++) {
         Province province = Game.getProvince(this.getArmyPosition(i));

         for (int j = 0; j < province.getArmySize(); j++) {
            ArmyDivision army = province.getArmy(j);
            if (this.getCivID() == army.civID && this.getArmyPositionKey(i).equals(army.key)) {
               this.iRegiments = this.iRegiments + army.iArmyRegimentSize;
            }
         }
      }

      return this.iRegiments;
   }

   public final void updateBuildingLimit() {
      for (int o = 0; o < this.getNumOfProvinces(); o++) {
         Game.getProvince(this.getProvinceID(o)).updateBuildingLimit();
      }
   }

   public final void updateInfrastructureMax() {
      for (int o = 0; o < this.getNumOfProvinces(); o++) {
         Game.getProvince(this.getProvinceID(o)).updateInfrastructureMax();
      }
   }

   public boolean addNukeProduction() {
      if (this.getNuclearReactorLevel() < GameValues.atomic.NUCLEAR_REACTOR_LVL_TO_CONSTRUCT_NUKE) {
         return false;
      } else if (this.fGold < Game.getAtomicBombCost(this.getCivID())) {
         return false;
      } else {
         Civilization var10000 = Game.getCiv(this.getCivID());
         var10000.fGold = var10000.fGold - Game.getAtomicBombCost(this.getCivID());
         int investTime = Game.getAtomicBombProductionTime(this.getCivID());
         this.nukesDaysLeft.add(new ProvinceInvest(investTime, investTime));
         this.iNukesSize = this.nukesDaysLeft.size();
         Game.gameThreadTurns.addCivsNukes(this.getCivID());
         return true;
      }
   }

   public void addNukeProduction_Load(int daysLeft, int investTime) {
      this.nukesDaysLeft.add(new ProvinceInvest(daysLeft, investTime));
      this.iNukesSize = this.nukesDaysLeft.size();
      Game.gameThreadTurns.addCivsNukes(this.getCivID());
   }

   public void updateNukeProduction(int turns) {
      if (this.iNukesSize > 0) {
         this.nukesDaysLeft.get(0).daysLeft -= turns;
         if (this.nukesDaysLeft.get(0).daysLeft <= 0) {
            this.nukesDaysLeft.remove(0);
            this.iNukesSize = this.nukesDaysLeft.size();
            this.setNukes(this.getNukes() + 1);
            if (this.getCivID() == Game.player.iCivID) {
               InGame_Info.iCivID = Game.player.iCivID;
               InGame_Info.iCivID2 = 0;
               Game.menuManager.rebuildInGame_Info(Game.lang.get("AnAtomicBombHasBeenBuilt"), Game.lang.get("AtomicBombs") + ": " + this.getNukes());
               InGame_Info.imgID = Images.infoAtomic;
            }

            if (this.iNukesSize == 0) {
               Game.gameThreadTurns.removeCivsNukes(this.getCivID());
            }
         }
      }
   }

   public final void setManpower(double nManpower) {
      this.fManpower = Math.max(0.0, Math.min(Math.max(this.fManpowerMax, this.fManpower), nManpower));
   }

   public final boolean haveAdvantage(int iAdvantageID, int iLevel) {
      for (int i = 0; i < this.iAdvantagesSize; i++) {
         if (this.lAdvantages.get(i).id == iAdvantageID) {
            return this.lAdvantages.get(i).lvl >= iLevel;
         }
      }

      return false;
   }

   public final boolean haveAdvantageMaxLvl(int iAdvantageID) {
      for (int i = 0; i < this.iAdvantagesSize; i++) {
         if (this.lAdvantages.get(i).id == iAdvantageID) {
            return this.lAdvantages.get(i).lvl >= AdvantagesManager.advantages.get(iAdvantageID).ImageID.length - 1;
         }
      }

      return false;
   }

   public final int getAdvantageLvl(int iAdvantageID) {
      for (int i = 0; i < this.iAdvantagesSize; i++) {
         if (this.lAdvantages.get(i).id == iAdvantageID) {
            return this.lAdvantages.get(i).lvl;
         }
      }

      return -1;
   }

   public final boolean canUnlockAdvantage(int iAdvantageID, int iLevel) {
      if (iLevel == 0) {
         return true;
      } else {
         for (int i = 0; i < this.iAdvantagesSize; i++) {
            if (this.lAdvantages.get(i).id == iAdvantageID) {
               return this.lAdvantages.get(i).lvl + 1 >= iLevel;
            }
         }

         return false;
      }
   }

   public final void addAdvantage(int iAdvantageID, int iLevel) {
      if (!this.haveAdvantage(iAdvantageID, iLevel)) {
         if (iLevel == 0) {
            this.lAdvantages.add(new CivilizationLegacy(iAdvantageID, iLevel));
            this.iAdvantagesSize = this.lAdvantages.size();
         } else {
            for (int i = 0; i < this.iAdvantagesSize; i++) {
               if (this.lAdvantages.get(i).id == iAdvantageID && this.lAdvantages.get(i).lvl + 1 >= iLevel) {
                  this.lAdvantages.get(i).lvl = iLevel;
               }
            }
         }
      }
   }

   public final void updateTotalProvincesValue() {
      this.fTotalProvincesValue = 0.0F;

      for (int i = this.getNumOfProvinces() - 1; i >= 0; i--) {
         this.fTotalProvincesValue = this.fTotalProvincesValue + Game.getProvince(this.getProvinceID(i)).fProvinceValue;
      }
   }

   public final void addCivilizationBonus_Temporary(CivilizationBonuses nBonus) {
      try {
         this.updateCivilizationBonuses_Temporary(nBonus, 1.0F);
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }

      this.civBonusesTemporary.add(nBonus);
      this.iCivBonusesTemporarySize = this.civBonusesTemporary.size();
   }

   public final void updateCivilizationBonuses_Temporary() {
      try {
         for (int i = this.iCivBonusesTemporarySize - 1; i >= 0; i--) {
            if (this.civBonusesTemporary.get(i).TempTurnID <= Game_Calendar.TURN_ID) {
               try {
                  this.updateCivilizationBonuses_Temporary(this.civBonusesTemporary.get(i), -1.0F);
               } catch (Exception var3) {
                  CFG.exceptionStack(var3);
               }

               this.civBonusesTemporary.remove(i);
               this.iCivBonusesTemporarySize = this.civBonusesTemporary.size();
            }
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   public final void updateCivilizationBonuses_Temporary(CivilizationBonuses nBonus, float mod) {
      if (nBonus.MonthlyIncome != 0.0F) {
         this.civBonuses.MonthlyIncome = this.civBonuses.MonthlyIncome + nBonus.MonthlyIncome * mod;
      }

      if (nBonus.TaxEfficiency != 0.0F) {
         this.civBonuses.TaxEfficiency = this.civBonuses.TaxEfficiency + nBonus.TaxEfficiency * mod;
      }

      if (nBonus.IncomeProduction != 0.0F) {
         this.civBonuses.IncomeProduction = this.civBonuses.IncomeProduction + nBonus.IncomeProduction * mod;
         Game.gameThread.addCivUpdateTotalIncomePerMonth(this.getCivID());
      }

      if (nBonus.IncomeTaxation != 0.0F) {
         this.civBonuses.IncomeTaxation = this.civBonuses.IncomeTaxation + nBonus.IncomeTaxation * mod;
         Game.gameThread.addCivUpdateTotalIncomePerMonth(this.getCivID());
      }

      if (nBonus.IncomeEconomy != 0.0F) {
         this.civBonuses.IncomeEconomy = this.civBonuses.IncomeEconomy + nBonus.IncomeEconomy * mod;
         Game.gameThread.addCivUpdateTotalIncomePerMonth(this.getCivID());
      }

      if (nBonus.ProductionEfficiency != 0.0F) {
         this.civBonuses.ProductionEfficiency = this.civBonuses.ProductionEfficiency + nBonus.ProductionEfficiency * mod;
      }

      if (nBonus.MaintenanceCost != 0.0F) {
         this.civBonuses.MaintenanceCost = this.civBonuses.MaintenanceCost + nBonus.MaintenanceCost * mod;
         Game.gameThread.addCivUpdateTotalIncomePerMonth(this.getCivID());
      }

      if (nBonus.BuildingsMaintenanceCost != 0.0F) {
         this.civBonuses.BuildingsMaintenanceCost = this.civBonuses.BuildingsMaintenanceCost + nBonus.BuildingsMaintenanceCost * mod;
         Game.gameThread.addCivUpdateTotalIncomePerMonth(this.getCivID());
      }

      if (nBonus.ProvinceMaintenance != 0.0F) {
         this.civBonuses.ProvinceMaintenance = this.civBonuses.ProvinceMaintenance + nBonus.ProvinceMaintenance * mod;
         Game.gameThread.addCivUpdateTotalIncomePerMonth(this.getCivID());
      }

      if (nBonus.GrowthRate != 0.0F) {
         this.civBonuses.GrowthRate = this.civBonuses.GrowthRate + nBonus.GrowthRate * mod;
         Game.getCiv(this.getCivID()).updateResearchPerMonth();
         Game.gameThread.addCivUpdateTotalIncomePerMonth(this.getCivID());
      }

      if (nBonus.MonthlyLegacy != 0.0F) {
         this.civBonuses.MonthlyLegacy = this.civBonuses.MonthlyLegacy + nBonus.MonthlyLegacy * mod;
         Game.gameThread.addCivUpdateLegacyPerMonth(this.getCivID());
      }

      if (nBonus.MonthlyLegacy_Percentage != 0.0F) {
         this.civBonuses.MonthlyLegacy_Percentage = this.civBonuses.MonthlyLegacy_Percentage + nBonus.MonthlyLegacy_Percentage * mod;
         Game.gameThread.addCivUpdateLegacyPerMonth(this.getCivID());
      }

      if (nBonus.MaxManpower != 0.0F) {
         this.civBonuses.MaxManpower = this.civBonuses.MaxManpower + nBonus.MaxManpower * mod;
         Game.gameThreadTurns.addCivUpdateMaxManpower(this.getCivID());
      }

      if (nBonus.MaxManpower_Percentage != 0.0F) {
         this.civBonuses.MaxManpower_Percentage = this.civBonuses.MaxManpower_Percentage + nBonus.MaxManpower_Percentage * mod;
         Game.gameThreadTurns.addCivUpdateMaxManpower(this.getCivID());
      }

      if (nBonus.ManpowerRecoverySpeed != 0.0F) {
         this.civBonuses.ManpowerRecoverySpeed = this.civBonuses.ManpowerRecoverySpeed + nBonus.ManpowerRecoverySpeed * mod;
      }

      if (nBonus.ReinforcementSpeed != 0.0F) {
         this.civBonuses.ReinforcementSpeed = this.civBonuses.ReinforcementSpeed + nBonus.ReinforcementSpeed * mod;
      }

      if (nBonus.ArmyMoraleRecovery != 0.0F) {
         this.civBonuses.ArmyMoraleRecovery = this.civBonuses.ArmyMoraleRecovery + nBonus.ArmyMoraleRecovery * mod;
      }

      if (nBonus.WarScoreCost != 0.0F) {
         this.civBonuses.WarScoreCost = this.civBonuses.WarScoreCost + nBonus.WarScoreCost * mod;
      }

      if (nBonus.ArmyMaintenance != 0.0F) {
         this.civBonuses.ArmyMaintenance = this.civBonuses.ArmyMaintenance + nBonus.ArmyMaintenance * mod;
         Game.gameThread.addCivUpdateArmyMaintenance(this.getCivID());
      }

      if (nBonus.RecruitmentTime != 0.0F) {
         this.civBonuses.RecruitmentTime = this.civBonuses.RecruitmentTime + nBonus.RecruitmentTime * mod;
      }

      if (nBonus.RecruitArmyCost != 0.0F) {
         this.civBonuses.RecruitArmyCost = this.civBonuses.RecruitArmyCost + nBonus.RecruitArmyCost * mod;
      }

      if (nBonus.RecruitArmyFirstLineCost != 0.0F) {
         this.civBonuses.RecruitArmyFirstLineCost = this.civBonuses.RecruitArmyFirstLineCost + nBonus.RecruitArmyFirstLineCost * mod;
      }

      if (nBonus.RecruitArmySecondLineCost != 0.0F) {
         this.civBonuses.RecruitArmySecondLineCost = this.civBonuses.RecruitArmySecondLineCost + nBonus.RecruitArmySecondLineCost * mod;
      }

      if (nBonus.ResearchPoints != 0.0F) {
         this.civBonuses.ResearchPoints = this.civBonuses.ResearchPoints + nBonus.ResearchPoints * mod;
         Game.gameThread.addCivUpdateResearchPerMonth(this.getCivID());
         Game.gameThread.addCivUpdateTotalIncomePerMonth(this.getCivID());
      }

      if (nBonus.Research != 0.0F) {
         this.civBonuses.Research = this.civBonuses.Research + nBonus.Research * mod;
         Game.gameThread.addCivUpdateResearchPerMonth(this.getCivID());
         Game.gameThread.addCivUpdateTotalIncomePerMonth(this.getCivID());
      }

      if (nBonus.TechnologyCost != 0.0F) {
         this.civBonuses.TechnologyCost = this.civBonuses.TechnologyCost + nBonus.TechnologyCost * mod;
      }

      if (nBonus.ConstructionCost != 0.0F) {
         this.civBonuses.ConstructionCost = this.civBonuses.ConstructionCost + nBonus.ConstructionCost * mod;
      }

      if (nBonus.AdministrationBuildingsCost != 0.0F) {
         this.civBonuses.AdministrationBuildingsCost = this.civBonuses.AdministrationBuildingsCost + nBonus.AdministrationBuildingsCost * mod;
      }

      if (nBonus.EconomyBuildingsCost != 0.0F) {
         this.civBonuses.EconomyBuildingsCost = this.civBonuses.EconomyBuildingsCost + nBonus.EconomyBuildingsCost * mod;
      }

      if (nBonus.MilitaryBuildingsCost != 0.0F) {
         this.civBonuses.MilitaryBuildingsCost = this.civBonuses.MilitaryBuildingsCost + nBonus.MilitaryBuildingsCost * mod;
      }

      if (nBonus.WonderConstructionCost != 0.0F) {
         this.civBonuses.WonderConstructionCost = this.civBonuses.WonderConstructionCost + nBonus.WonderConstructionCost * mod;
      }

      if (nBonus.ConstructionTime != 0.0F) {
         this.civBonuses.ConstructionTime = this.civBonuses.ConstructionTime + nBonus.ConstructionTime * mod;
      }

      if (nBonus.InvestInEconomyCost != 0.0F) {
         this.civBonuses.InvestInEconomyCost = this.civBonuses.InvestInEconomyCost + nBonus.InvestInEconomyCost * mod;
      }

      if (nBonus.IncreaseTaxEfficiencyCost != 0.0F) {
         this.civBonuses.IncreaseTaxEfficiencyCost = this.civBonuses.IncreaseTaxEfficiencyCost + nBonus.IncreaseTaxEfficiencyCost * mod;
      }

      if (nBonus.IncreaseGrowthRateCost != 0.0F) {
         this.civBonuses.IncreaseGrowthRateCost = this.civBonuses.IncreaseGrowthRateCost + nBonus.IncreaseGrowthRateCost * mod;
      }

      if (nBonus.DevelopInfrastructureCost != 0.0F) {
         this.civBonuses.DevelopInfrastructureCost = this.civBonuses.DevelopInfrastructureCost + nBonus.DevelopInfrastructureCost * mod;
      }

      if (nBonus.IncreaseManpowerCost != 0.0F) {
         this.civBonuses.IncreaseManpowerCost = this.civBonuses.IncreaseManpowerCost + nBonus.IncreaseManpowerCost * mod;
      }

      if (nBonus.GeneralAttack != 0) {
         this.civBonuses.GeneralAttack = (int)(this.civBonuses.GeneralAttack + nBonus.GeneralAttack * mod);
      }

      if (nBonus.GeneralDefense != 0) {
         this.civBonuses.GeneralDefense = (int)(this.civBonuses.GeneralDefense + nBonus.GeneralDefense * mod);
      }

      if (nBonus.UnitsAttack != 0) {
         this.civBonuses.UnitsAttack = (int)(this.civBonuses.UnitsAttack + nBonus.UnitsAttack * mod);
      }

      if (nBonus.UnitsDefense != 0) {
         this.civBonuses.UnitsDefense = (int)(this.civBonuses.UnitsDefense + nBonus.UnitsDefense * mod);
      }

      if (nBonus.AdvisorCost != 0.0F) {
         this.civBonuses.AdvisorCost = this.civBonuses.AdvisorCost + nBonus.AdvisorCost * mod;
      }

      if (nBonus.GeneralCost != 0.0F) {
         this.civBonuses.GeneralCost = this.civBonuses.GeneralCost + nBonus.GeneralCost * mod;
      }

      if (nBonus.MaxMorale != 0.0F) {
         this.civBonuses.MaxMorale = this.civBonuses.MaxMorale + nBonus.MaxMorale * mod;
      }

      if (nBonus.DiseaseDeathRate != 0.0F) {
         this.civBonuses.DiseaseDeathRate = this.civBonuses.DiseaseDeathRate + nBonus.DiseaseDeathRate * mod;
      }

      if (nBonus.AllCharactersLifeExpectancy != 0) {
         this.civBonuses.AllCharactersLifeExpectancy = (int)(this.civBonuses.AllCharactersLifeExpectancy + nBonus.AllCharactersLifeExpectancy * mod);
      }

      if (nBonus.BattleWidth != 0) {
         this.civBonuses.BattleWidth = (int)(this.civBonuses.BattleWidth + nBonus.BattleWidth * mod);
      }

      if (nBonus.Discipline != 0.0F) {
         this.civBonuses.Discipline = this.civBonuses.Discipline + nBonus.Discipline * mod;
      }

      if (nBonus.ManpowerRecoveryFromADisbandedArmy != 0.0F) {
         this.civBonuses.ManpowerRecoveryFromADisbandedArmy = this.civBonuses.ManpowerRecoveryFromADisbandedArmy
            + nBonus.ManpowerRecoveryFromADisbandedArmy * mod;
      }

      if (nBonus.MaxInfrastructure != 0) {
         this.civBonuses.MaxInfrastructure = (int)(this.civBonuses.MaxInfrastructure + nBonus.MaxInfrastructure * mod);

         for (int o = 0; o < Game.getCiv(this.getCivID()).getNumOfProvinces(); o++) {
            Game.getProvince(Game.getCiv(this.getCivID()).getProvinceID(o)).updateInfrastructureMax();
         }
      }

      if (nBonus.ArmyMovementSpeed != 0.0F) {
         this.civBonuses.ArmyMovementSpeed = this.civBonuses.ArmyMovementSpeed + nBonus.ArmyMovementSpeed * mod;
      }

      if (nBonus.SiegeEffectiveness != 0.0F) {
         this.civBonuses.SiegeEffectiveness = this.civBonuses.SiegeEffectiveness + nBonus.SiegeEffectiveness * mod;
      }

      if (nBonus.ImproveRelationsModifier != 0.0F) {
         this.civBonuses.ImproveRelationsModifier = this.civBonuses.ImproveRelationsModifier + nBonus.ImproveRelationsModifier * mod;
      }

      if (nBonus.IncomeFromVassals != 0.0F) {
         this.civBonuses.IncomeFromVassals = this.civBonuses.IncomeFromVassals + nBonus.IncomeFromVassals * mod;
         Game.gameThread.addCivUpdateTotalIncomePerMonth(this.iCivID);
      }

      if (nBonus.LoanInterest != 0.0F) {
         this.civBonuses.LoanInterest = this.civBonuses.LoanInterest + nBonus.LoanInterest * mod;
      }

      if (nBonus.DiplomacyPoints != 0.0F) {
         this.civBonuses.DiplomacyPoints = this.civBonuses.DiplomacyPoints + nBonus.DiplomacyPoints * mod;
         Game.getCiv(this.getCivID()).updateDiplomacyPerMonth();
      }

      if (nBonus.AggressiveExpansion != 0.0F) {
         this.civBonuses.AggressiveExpansion = this.civBonuses.AggressiveExpansion + nBonus.AggressiveExpansion * mod;
      }

      if (nBonus.RevolutionaryRisk != 0.0F) {
         this.civBonuses.RevolutionaryRisk = this.civBonuses.RevolutionaryRisk + nBonus.RevolutionaryRisk * mod;
      }

      if (nBonus.Devastation != 0.0F) {
         this.civBonuses.Devastation = this.civBonuses.Devastation + nBonus.Devastation * mod;
      }

      if (nBonus.CoreCost != 0.0F) {
         this.civBonuses.CoreCost = this.civBonuses.CoreCost + nBonus.CoreCost * mod;
      }

      if (nBonus.ReligionCost != 0.0F) {
         this.civBonuses.ReligionCost = this.civBonuses.ReligionCost + nBonus.ReligionCost * mod;
      }

      if (nBonus.AdvisorMaxLevel != 0) {
         this.civBonuses.AdvisorMaxLevel = (int)(this.civBonuses.AdvisorMaxLevel + nBonus.AdvisorMaxLevel * mod);
      }

      if (nBonus.MaximumAmountOfGold != 0.0F) {
         this.civBonuses.MaximumAmountOfGold = this.civBonuses.MaximumAmountOfGold + nBonus.MaximumAmountOfGold * mod;
      }

      if (nBonus.MaximumAmountOfGold_Percentage != 0.0F) {
         this.civBonuses.MaximumAmountOfGold_Percentage = this.civBonuses.MaximumAmountOfGold_Percentage + nBonus.MaximumAmountOfGold_Percentage * mod;
      }

      if (nBonus.Corruption != 0.0F) {
         this.civBonuses.Corruption = this.civBonuses.Corruption + nBonus.Corruption * mod;
         Game.gameThread.addCivUpdateTotalIncomePerMonth(this.iCivID);
      }

      if (nBonus.Inflation != 0.0F) {
         this.civBonuses.Inflation = this.civBonuses.Inflation + nBonus.Inflation * mod;
         Game.gameThread.addCivUpdateTotalIncomePerMonth(this.iCivID);
      }

      if (nBonus.RegimentsLimit != 0) {
         this.civBonuses.RegimentsLimit = (int)(this.civBonuses.RegimentsLimit + nBonus.RegimentsLimit * mod);
         this.updateRegimentsLimit();
      }

      Game.getCiv(this.getCivID()).updateProvincesIncomeAndExpenses();
   }

   public final void addGoodsProduced(int iResourceID, int value) {
      try {
         this.goodsProduced.set(iResourceID, this.goodsProduced.get(iResourceID) + value);
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
         this.initGoodsProduced();
      }
   }

   public final int getGoodsProduced(int iResourceID) {
      try {
         return this.goodsProduced.get(iResourceID);
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
         this.initGoodsProduced();
         return 0;
      }
   }

   public final void initGoodsProduced() {
      this.goodsProduced.clear();

      for (int i = 0; i < ResourcesManager.iResourcesSize; i++) {
         this.goodsProduced.add(0);
      }
   }

   public final void resetGoodsProduced() {
      try {
         for (int i = 0; i < ResourcesManager.iResourcesSize; i++) {
            this.goodsProduced.set(i, 0);
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   public final void buildLaws() {
      this.laws.clear();

      for (int i = 0; i < LawsManager.iLawsSize; i++) {
         this.laws.add(0);
      }
   }

   public final void adoptReform(int lawID, int lawID2) {
      try {
         this.laws.set(lawID, lawID2);
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
         this.buildLaws();
      }
   }

   public final void initMaxLaws() {
      try {
         for (int i = 0; i < LawsManager.iLawsSize; i++) {
            for (int j = LawsManager.laws.get(i).RequiredTechID.length - 1; j > 0; j--) {
               if (this.getTechResearched(LawsManager.laws.get(i).RequiredTechID[j])
                  && (
                     LawsManager.laws.get(i).RequiredGovernmentID == null
                        || LawsManager.laws.get(i).RequiredGovernmentID[j] < 0
                        || LawsManager.laws.get(i).RequiredGovernmentID[j] == this.getIdeologyID()
                  )) {
                  LawsManager.updateCivBonuses(i, this.laws.get(i), this.iCivID, -1.0F);
                  this.laws.set(i, j);
                  LawsManager.updateCivBonuses(i, j, this.iCivID, 1.0F);
                  break;
               }
            }
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public final void addColonizationProvince(int iProvinceID) {
      try {
         for (int i = this.iColonizationProvinceSize - 1; i >= 0; i--) {
            if (this.colonizationProvince.get(i) == iProvinceID) {
               return;
            }
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }

      this.colonizationProvince.add(iProvinceID);
      this.iColonizationProvinceSize = this.colonizationProvince.size();
   }

   public final void removeColonizationProvince(int iProvinceID) {
      try {
         for (int i = this.iColonizationProvinceSize - 1; i >= 0; i--) {
            if (this.colonizationProvince.get(i) == iProvinceID) {
               this.colonizationProvince.remove(i);
               this.iColonizationProvinceSize = this.colonizationProvince.size();
               return;
            }
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public final void buildColonizationProvince() {
      this.colonizationProvince.clear();

      try {
         for (int i = 0; i < this.getNumOfProvinces(); i++) {
            if (Game.getProvinceData9(this.getProvinceID(i)).getColonizationStartedTurnID() >= 0) {
               if (ColonizationManager.getSettlementEstablishmentProgress(this.getProvinceID(i)) >= 0.99F) {
                  Game.getProvince(this.getProvinceID(i)).resetColonizationData();
               } else if (!this.colonizationProvince.contains(this.getProvinceID(i))) {
                  this.colonizationProvince.add(this.getProvinceID(i));
               }
            }
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }

      this.iColonizationProvinceSize = this.colonizationProvince.size();
   }

   public final void clearColonizationProvince() {
      this.colonizationProvince.clear();
      this.iColonizationProvinceSize = 0;
   }

   public final void updateColonizationProvince() {
      try {
         for (int i = this.iColonizationProvinceSize - 1; i >= 0; i--) {
            if (ColonizationManager.getSettlementEstablishmentProgress(this.colonizationProvince.get(i)) >= 0.99F) {
               if (this.getCivID() == Game.player.iCivID) {
                  Game.player
                     .addNotification(
                        new Notification(
                           Notification.Notification_Type.SETTLEMENT_ESTABLISHED,
                           Game.lang.get("SettlementEstablished") + ": " + Game.getProvince(this.colonizationProvince.get(i)).getProvinceName(),
                           Images.population,
                           Game_Calendar.TURN_ID,
                           Notification.Notification_BG.GREEN,
                           this.colonizationProvince.get(i)
                        )
                     );
               }

               this.expandColony(this.colonizationProvince.get(i));
               Game.getProvince(this.colonizationProvince.get(i)).resetColonizationData();
               this.colonizationProvince.remove(i);
               this.iColonizationProvinceSize = this.colonizationProvince.size();
            }
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   public final void expandColony(int provinceID) {
      if (Game.oR.nextInt(100) < GameValues.colonization.AUTO_EXPAND_CHANCE) {
         List<Integer> possibleExpand = new ArrayList<>();

         for (int i = 0; i < Game.getProvince(provinceID).getNeighboringProvincesSize(); i++) {
            if (Game.getProvince(Game.getProvince(provinceID).getNeighboringProvinces(i)).getWasteland() < 0
               && Game.getProvince(Game.getProvince(provinceID).getNeighboringProvinces(i)).getCivID() == 0
               && !Game.getProvince(Game.getProvince(provinceID).getNeighboringProvinces(i)).getSeaProvince()) {
               possibleExpand.add(Game.getProvince(provinceID).getNeighboringProvinces(i));
            }
         }

         if (possibleExpand.size() > 0) {
            List<Integer> possibleScore = new ArrayList<>();
            int bestID = 0;

            for (int ix = 0; ix < possibleExpand.size(); ix++) {
               int score = 0;
               boolean isLastNeutral = true;

               for (int j = 0; j < Game.getProvince(possibleExpand.get(ix)).getNeighboringProvincesSize(); j++) {
                  if (Game.getProvince(Game.getProvince(possibleExpand.get(ix)).getNeighboringProvinces(j)).getCivID()
                     == Game.getProvince(provinceID).getCivID()) {
                     score += 10;
                  } else if (Game.getProvince(Game.getProvince(possibleExpand.get(ix)).getNeighboringProvinces(j)).getCivID() == 0) {
                     isLastNeutral = false;
                  }
               }

               if (isLastNeutral) {
                  score += 500;
               }

               possibleScore.add(score);
            }

            for (int ix = 1; ix < possibleExpand.size(); ix++) {
               if (possibleScore.get(bestID) < possibleScore.get(ix)) {
                  bestID = ix;
               }
            }

            if (ColonizationManager.establishSettlement(
                  Game.getProvince(provinceID).getCivID(), possibleExpand.get(bestID), GameValues.colonization.AUTO_EXPAND_POPULATION
               )
               && Game.getProvince(provinceID).getCivID() == Game.player.iCivID) {
               InGame_Info.iCivID = Game.player.iCivID;
               InGame_Info.iCivID2 = 0;
               Game.menuManager
                  .rebuildInGame_Info(
                     Game.lang.get("SettlementEstablished") + ": " + Game.getProvince(possibleExpand.get(bestID)).getProvinceName(),
                     Game.lang.get("Population") + ": " + CFG.getNumberWithSpaces("" + Game.getProvince(possibleExpand.get(bestID)).getPopulationTotal())
                  );
               InGame_Info.imgID = Images.infoCrown;
            }

            possibleScore.clear();
         }

         possibleExpand.clear();
      }
   }

   public final void clearTagsCanForm() {
      this.sTagsCanForm.clear();
   }

   public final void addTagsCanForm(String nTag) {
      for (int i = 0; i < this.sTagsCanForm.size(); i++) {
         if (this.sTagsCanForm.get(i).equals(nTag)) {
            return;
         }
      }

      this.sTagsCanForm.add(nTag);
   }

   public final void removeTagsCanForm(int i) {
      this.sTagsCanForm.remove(i);
   }

   public final void removeTagsCanForm(String nTag) {
      for (int i = 0; i < this.sTagsCanForm.size(); i++) {
         if (this.sTagsCanForm.get(i).equals(nTag)) {
            this.sTagsCanForm.remove(i);
            return;
         }
      }
   }

   public float getLegacyPerMonth() {
      return this.fLegacyPerMonth * (1.0F + this.getWarWeariness() * GameValues.warWeariness.WW_LEGACY_PER_POINT);
   }

   public final void setBattleTacticsID(int nBattleTactics) {
      this.civData4.b = Math.max(0, Math.min(GameValues.battleTactics.BATTLE_TACTICS.length - 1, nBattleTactics));
   }

   public final void updateArmyImgID() {
      if (this.getCivID() == Game.player.iCivID) {
         this.updateArmyImgID_Player();
         this.isPlayerAlly = true;
      } else if (DiplomacyManager.isAtWar(Game.player.iCivID, this.getCivID())) {
         this.iArmyImgID = Images.armyEnemy;
         this.isPlayerAlly = false;
      } else if (DiplomacyManager.isAlly(Game.player.iCivID, this.getCivID())) {
         this.iArmyImgID = Images.armyAlly;
         this.isPlayerAlly = true;
      } else {
         this.iArmyImgID = Images.army;
         this.isPlayerAlly = false;
      }
   }

   public final void updateArmyImgID_Player() {
      switch (Game.player.playerData.armyImgID) {
         case 0:
            this.iArmyImgID = Images.armyPlayer0;
            break;
         case 1:
            this.iArmyImgID = Images.armyPlayer1;
            break;
         case 2:
            this.iArmyImgID = Images.armyPlayer2;
            break;
         case 3:
            this.iArmyImgID = Images.armyPlayer3;
            break;
         case 4:
            this.iArmyImgID = Images.armyPlayer4;
            break;
         case 5:
            this.iArmyImgID = Images.armyPlayer5;
            break;
         case 6:
            this.iArmyImgID = Images.armyPlayer6;
            break;
         case 7:
            this.iArmyImgID = Images.armyPlayer7;
            break;
         case 8:
            this.iArmyImgID = Images.armyPlayer8;
            break;
         case 9:
            this.iArmyImgID = Images.armyPlayer9;
            break;
         case 10:
            this.iArmyImgID = Images.armyPlayer10;
            break;
         case 11:
            this.iArmyImgID = Images.armyPlayer11;
            break;
         case 12:
            this.iArmyImgID = Images.armyPlayer12;
            break;
         case 13:
            this.iArmyImgID = Images.armyPlayer13;
            break;
         case 14:
            this.iArmyImgID = Images.armyPlayer14;
            break;
         default:
            this.iArmyImgID = Images.armyPlayer0;
      }
   }

   public final void updateProsperity_AverageEconomy() {
      long popTotal = this.getPopulationTotal();
      float out = 0.0F;

      for (int i = this.getNumOfProvinces() - 1; i >= 0; i--) {
         out += Game.getProvince(this.getProvinceID(i)).getEconomyWithBonuses()
            * (Game.getProvince(this.getProvinceID(i)).getPopulationTotal() / (float)popTotal);
      }

      this.fProsperity_AverageEconomy = out;
   }

   public final void addInAllianceSpecial(int nAllianceID) {
      if (!this.inAlliance.contains(nAllianceID)) {
         this.inAlliance.add(nAllianceID);
         this.inAllianceSize = this.inAlliance.size();
      }
   }

   public final boolean areInAllianceSpecial(int civID) {
      for (int i = 0; i < Game.getCiv(civID).inAllianceSize; i++) {
         if (this.inAlliance.contains(Game.getCiv(civID).inAlliance.get(i))) {
            return true;
         }
      }

      return false;
   }

   public final void removeInAllianceSpecial(int nAllianceID) {
      for (int i = 0; i < this.inAllianceSize; i++) {
         if (this.inAlliance.get(i) == nAllianceID) {
            this.inAlliance.remove(i);
            this.inAllianceSize = this.inAlliance.size();
            return;
         }
      }
   }

   public final void addAggressiveExpansion(float fValue) {
      this.setAggressiveExpansion(
         Math.min(
            GameValues.aggressiveExpansion.AE_MAX_VALUE,
            this.getAggressiveExpansion() + fValue * Math.max(0.0F, 1.0F + this.civBonuses.AggressiveExpansion / 100.0F)
         )
      );
   }

   public final void decayAggressiveExpansion() {
      this.setAggressiveExpansion(Math.max(0.0F, this.getAggressiveExpansion() - GameValues.aggressiveExpansion.AE_DECAY_PER_TICK));
   }

   public final void updateWarWeariness(float fValue) {
      this.setWarWeariness(Math.max(0.0F, Math.min(GameValues.warWeariness.WAR_WEARINESS_MAX, this.getWarWeariness() + fValue)));
   }

   public final boolean reduceWarWeariness() {
      if (this.fGold < GameValues.warWeariness.WW_REDUCE_COST_GOLD) {
         return false;
      } else if (this.fLegacy < GameValues.warWeariness.WW_REDUCE_COST_LEGACY) {
         return false;
      } else if (this.getWarWeariness() <= 0.005) {
         return false;
      } else {
         this.updateWarWeariness(GameValues.warWeariness.WW_REDUCE);
         this.addGold(GameValues.warWeariness.WW_REDUCE_COST_GOLD);
         this.addLegacy(GameValues.warWeariness.WW_REDUCE_COST_LEGACY);
         return true;
      }
   }

   public final void removeAllArmies() {
      for (int i = this.iArmyPositionSize - 1; i >= 0; i--) {
         Game.getProvince(this.armyPosition.get(i).provinceID).removeArmyCivID(this.getCivID());
      }

      this.armyPosition.clear();
      this.iArmyPositionSize = 0;
   }

   public final void updateCivStability() {
      if (this.getNumOfProvinces() > 0) {
         float score = 0.0F;
         int numOfProvinces = 0;

         for (int i = 0; i < this.getNumOfProvinces(); i++) {
            if (!Game.getProvince(this.getProvinceID(i)).haveACore) {
               score += GameValues.civStability.CS_SCORE_NON_CORE;
               numOfProvinces++;
            }

            if (Game.getProvince(this.getProvinceID(i)).getReligion() != this.getReligionID()) {
               score += GameValues.civStability.CS_SCORE_DIFFERENT_RELIGION;
               if (Game.getProvince(this.getProvinceID(i)).haveACore) {
                  numOfProvinces++;
               }
            }
         }

         this.civStability_LostFrom100 = Math.min(
            GameValues.civStability.CS_MAX_VALUE,
            Math.min(GameValues.civStability.CS_SCORE_MAX_PER_PROVINCE * numOfProvinces, score / this.getNumOfProvinces())
         );
      } else {
         this.civStability_LostFrom100 = 0.0F;
      }
   }

   public final int civStability_NumOfProvinces() {
      int score = 0;

      for (int i = 0; i < this.getNumOfProvinces(); i++) {
         if (!Game.getProvince(this.getProvinceID(i)).haveACore) {
            score++;
         } else if (Game.getProvince(this.getProvinceID(i)).getReligion() != this.getReligionID()) {
            score++;
         }
      }

      return score;
   }

   public void addInBattles(String key) {
      if (!this.inBattles.contains(key)) {
         this.inBattles.add(key);
         if (this.iCivID == Game.player.iCivID) {
            Game.addSimpleTask(new Game.SimpleTask("rebuildInGame_Right") {
               @Override
               public void update() {
                  Game.menuManager.rebuildInGame_Right();
               }
            });
         }
      }
   }

   public void removeInBattles(String key) {
      this.inBattles.remove(key);
   }

   public int getAdvantagePoints() {
      return this.civData3.a;
   }

   public void setAdvantagePoints(int iAdvantagePoints) {
      this.civData3.a = iAdvantagePoints;
   }

   public float getWarWeariness() {
      return this.civData3.w;
   }

   public void setWarWeariness(float warWeariness) {
      this.civData3.w = warWeariness;
   }

   public float getAggressiveExpansion() {
      return this.civData3.e;
   }

   public void setAggressiveExpansion(float aggressiveExpansion) {
      this.civData3.e = aggressiveExpansion;
   }

   public int getMilitaryAcademyForGeneralsLevel() {
      return this.civData4.g;
   }

   public void setMilitaryAcademyForGeneralsLevel(int iMilitaryAcademyForGeneralsLevel) {
      this.civData4.g = iMilitaryAcademyForGeneralsLevel;
   }

   public int getMilitaryAcademyLevel() {
      return this.civData4.m;
   }

   public void setMilitaryAcademyLevel(int iMilitaryAcademyLevel) {
      this.civData4.m = iMilitaryAcademyLevel;
   }

   public int getSupremeCourtLevel() {
      return this.civData4.s;
   }

   public void setSupremeCourtLevel(int iSupremeCourtLevel) {
      this.civData4.s = iSupremeCourtLevel;
   }

   public int getNuclearReactorLevel() {
      return this.civData4.n;
   }

   public void setNuclearReactorLevel(int iNuclearReactorLevel) {
      this.civData4.n = iNuclearReactorLevel;
   }

   public int getCapitalLevel() {
      return this.civData4.c;
   }

   public void setCapitalLevel(int iCapitalLevel) {
      this.civData4.c = iCapitalLevel;
   }

   public int getWarPlayDefensiveUntilTurnID() {
      return this.civData4.d;
   }

   public void setWarPlayDefensiveUntilTurnID(int untilTurnID) {
      this.civData4.d = untilTurnID;
   }

   public int getExtraAggressiveness() {
      return this.civData4.v;
   }

   public void setExtraAggressiveness(int extraAggressiveness) {
      this.civData4.v = extraAggressiveness;
   }

   public int getBattleTacticsID() {
      return this.civData4.b;
   }

   public int getNukes() {
      return this.civData4.u;
   }

   public void setNukes(int iNukes) {
      this.civData4.u = iNukes;
   }

   public int getTaxationLevel() {
      return this.civData4.t;
   }

   public void setTaxationLevel(int iTaxationLevel) {
      this.civData4.t = iTaxationLevel;
   }

   public int getResearchLevel() {
      return this.civData4.r;
   }

   public void setResearchLevel(int iResearchLevel) {
      this.civData4.r = iResearchLevel;
   }

   public int getMilitaryLevel() {
      return this.civData4.y;
   }

   public void setMilitaryLevel(int iMilitaryLevel) {
      this.civData4.y = iMilitaryLevel;
   }

   public int getAlternativeTechResearch() {
      return this.iAlternativeTechResearch;
   }

   public void setAlternativeTechResearch(int iAlternativeTechResearch) {
      this.iAlternativeTechResearch = iAlternativeTechResearch;
   }

   public final void updateProvinceBorder() {
      for (int i = 0; i < this.getNumOfProvinces(); i++) {
         Game.updateProvinceBorder(this.getProvinceID(i));
      }
   }

   public List<TechnologyTree.Unit> getUnlockedUnitsFirstLine() {
      List<TechnologyTree.Unit> out = new ArrayList<>();

      for (int i = this.unlockedUnits.size() - 1; i >= 0; i--) {
         if (ArmyManager.lUnitsTypes.get(this.unlockedUnits.get(i).unitID).Line == 0) {
            out.add(this.unlockedUnits.get(i));
         }
      }

      return out;
   }

   public List<TechnologyTree.Unit> getUnlockedUnitsFlank() {
      List<TechnologyTree.Unit> out = new ArrayList<>();

      for (int i = this.unlockedUnits.size() - 1; i >= 0; i--) {
         if (ArmyManager.lUnitsTypes.get(this.unlockedUnits.get(i).unitID).Line == 1) {
            out.add(this.unlockedUnits.get(i));
         }
      }

      return out;
   }

   public List<TechnologyTree.Unit> getUnlockedUnitsSupport() {
      List<TechnologyTree.Unit> out = new ArrayList<>();

      for (int i = this.unlockedUnits.size() - 1; i >= 0; i--) {
         if (ArmyManager.lUnitsTypes.get(this.unlockedUnits.get(i).unitID).Line == 2
            && !ArmyManager.lArmy.get(this.unlockedUnits.get(i).unitID).get(this.unlockedUnits.get(i).armyID).isSettler
            && !ArmyManager.lArmy.get(this.unlockedUnits.get(i).unitID).get(this.unlockedUnits.get(i).armyID).SiegeUnit) {
            out.add(this.unlockedUnits.get(i));
         }
      }

      return out;
   }

   public final void addUnderSiege(int provinceID) {
      if (!this.underSiege.contains(provinceID)) {
         this.underSiege.add(provinceID);
         this.underSiegeSize = this.underSiege.size();
      }
   }

   public final void removeUnderSiege(int provinceID) {
      for (int i = this.underSiege.size() - 1; i >= 0; i--) {
         if (this.underSiege.get(i) == provinceID) {
            this.underSiege.remove(i);
            this.underSiegeSize = this.underSiege.size();
            return;
         }
      }
   }

   public final void buildUnderSiege() {
      this.underSiege.clear();

      for (int i = 0; i < this.getNumOfProvinces(); i++) {
         if (Game.getProvinceData4(this.getProvinceID(i)).isUnderSiege()) {
            this.underSiege.add(this.getProvinceID(i));
         }
      }

      this.underSiegeSize = this.underSiege.size();
   }

   public final void addOccupiedProvince(int provinceID) {
      if (!this.occupiedProvinces.contains(provinceID)) {
         this.occupiedProvinces.add(provinceID);
         this.occupiedProvincesSize = this.occupiedProvinces.size();
      }
   }

   public final void removeOccupiedProvince(int provinceID) {
      for (int i = this.occupiedProvinces.size() - 1; i >= 0; i--) {
         if (this.occupiedProvinces.get(i) == provinceID) {
            this.occupiedProvinces.remove(i);
            this.occupiedProvincesSize = this.occupiedProvinces.size();
            return;
         }
      }
   }

   public final void buildOccupiedProvinces() {
      this.occupiedProvinces.clear();

      for (int i = 0; i < this.getNumOfProvinces(); i++) {
         if (Game.getProvinceData(this.getProvinceID(i)).getOccupiedByCivID() != 0) {
            this.occupiedProvinces.add(this.getProvinceID(i));
         }
      }

      this.occupiedProvincesSize = this.occupiedProvinces.size();
   }
}
