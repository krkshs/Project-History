package aoc.kingdoms.lukasz.jakowski.SaveLoad;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.FileManager;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.map.FormableCivManager;
import aoc.kingdoms.lukasz.map.diplomacy.Diplomacy;
import aoc.kingdoms.lukasz.map.map.MapScenarios;
import aoc.kingdoms.lukasz.map.province.ProvinceNamesManager;
import aoc.kingdoms.lukasz.menusScenarioEditor.ScenarioSettings;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public class SaveManager {
   public static final int iNeighboringProvincesPerFile = 2000;

   public static final Json getJson() {
      Json json = new Json();
      json.setTypeName(null);
      json.setUsePrototypes(false);
      json.setIgnoreUnknownFields(true);
      json.setOutputType(OutputType.javascript);
      return json;
   }

   public static final void saveScenarioArmies() {
      ArrayList<SaveManager.ScenarioData_Army> tempDetails = new ArrayList<>();

      for (int i = 0; i < Game.getProvincesSize(); i++) {
         if (Game.getProvince(i).getCivID() > 0 && Game.getProvince(i).getArmySize() > 0) {
            try {
               SaveManager.ScenarioData_Army tArmy = new SaveManager.ScenarioData_Army();
               tArmy.ProvinceID = i;

               for (int j = 0; j < Game.getProvince(i).getArmy(0).iArmyRegimentSize; j++) {
                  tArmy.UnitTypeID.add(Game.getProvince(i).getArmy(0).lArmyRegiment.get(j).uID);
                  tArmy.ArmyID.add(Game.getProvince(i).getArmy(0).lArmyRegiment.get(j).aID);
               }

               if (tArmy.UnitTypeID.size() > 0) {
                  tempDetails.add(tArmy);
               }
            } catch (Exception var4) {
               CFG.exceptionStack(var4);
            }
         }
      }

      Json json = getJson();
      FileHandle file = FileManager.getSaveType("map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + ScenarioSettings.sTag + "/" + "Armies.json");
      file.writeString(json.prettyPrint(tempDetails), false);
   }

   public static final void saveScenarioBuildings() {
      ArrayList<SaveManager.ScenarioData_Buildings> tempDetails = new ArrayList<>();

      for (int i = 0; i < Game.getProvincesSize(); i++) {
         if (Game.getProvince(i).getCivID() > 0 && Game.getProvince(i).iBuildingsSize > 0) {
            try {
               SaveManager.ScenarioData_Buildings tData = new SaveManager.ScenarioData_Buildings();
               tData.pid = i;

               for (int j = 0; j < Game.getProvince(i).iBuildingsSize; j++) {
                  tData.b0.add(Game.getProvince(i).getBuildings(j).getBuilding());
                  tData.b1.add(Game.getProvince(i).getBuildings(j).getBuildingID());
               }

               if (tData.b0.size() > 0) {
                  tempDetails.add(tData);
               }
            } catch (Exception var4) {
               CFG.exceptionStack(var4);
            }
         }
      }

      Json json = getJson();
      FileHandle file = FileManager.getSaveType("map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + ScenarioSettings.sTag + "/" + "Buildings.json");
      file.writeString(json.prettyPrint(tempDetails), false);
   }

   public static final void saveScenarioRelations() {
      ArrayList<SaveManager.ScenarioData_Relations> tData = new ArrayList<>();

      for (int i = 1; i < Game.getCivsSize(); i++) {
         if (Game.getCiv(i).diplomacy.relation.size() > 0) {
            SaveManager.ScenarioData_Relations nData = new SaveManager.ScenarioData_Relations();
            nData.c = i;

            for (Entry<Integer, Float> entry : Game.getCiv(i).diplomacy.relation.entrySet()) {
               nData.w.add(entry.getKey());
               nData.r.add(entry.getValue().intValue());
            }

            tData.add(nData);
         }
      }

      Json json = getJson();
      FileHandle file = FileManager.getSaveType("map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + ScenarioSettings.sTag + "/" + "Relations.json");
      file.writeString(json.toJson(tData), false);
      tData.clear();
      ArrayList<SaveManager.ScenarioData_Relations> var5 = null;
   }

   public static final void saveScenarioAlliances() {
      ArrayList<SaveManager.ScenarioData_Diplomacy> tData = new ArrayList<>();

      for (int i = 1; i < Game.getCivsSize(); i++) {
         if (Game.getCiv(i).diplomacy.alliance.size() > 0) {
            SaveManager.ScenarioData_Diplomacy nData = new SaveManager.ScenarioData_Diplomacy();
            nData.pid = i;

            for (Diplomacy.DiplomacyData dData : Game.getCiv(i).diplomacy.alliance.values()) {
               nData.w0.add(dData.iCivID);
               nData.t0.add(dData.iTurnID);
            }

            tData.add(nData);
         }
      }

      Json json = getJson();
      FileHandle file = FileManager.getSaveType("map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + ScenarioSettings.sTag + "/" + "Alliances.json");
      file.writeString(json.toJson(tData), false);
      tData.clear();
      ArrayList<SaveManager.ScenarioData_Diplomacy> var5 = null;
   }

   public static final void saveScenarioDefensive() {
      ArrayList<SaveManager.ScenarioData_Diplomacy> tData = new ArrayList<>();

      for (int i = 1; i < Game.getCivsSize(); i++) {
         if (Game.getCiv(i).diplomacy.defensivePact.size() > 0) {
            SaveManager.ScenarioData_Diplomacy nData = new SaveManager.ScenarioData_Diplomacy();
            nData.pid = i;

            for (Diplomacy.DiplomacyData dData : Game.getCiv(i).diplomacy.defensivePact.values()) {
               nData.w0.add(dData.iCivID);
               nData.t0.add(dData.iTurnID);
            }

            tData.add(nData);
         }
      }

      Json json = getJson();
      FileHandle file = FileManager.getSaveType("map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + ScenarioSettings.sTag + "/" + "Defensive.json");
      file.writeString(json.toJson(tData), false);
      tData.clear();
      ArrayList<SaveManager.ScenarioData_Diplomacy> var5 = null;
   }

   public static final void saveScenarioAlliancesSpecial() {
      Json json = getJson();
      FileHandle file = FileManager.getSaveType(
         "map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + ScenarioSettings.sTag + "/" + "AlliancesSpecial.json"
      );
      file.writeString(json.toJson(Game.alliancesSpecial), false);
   }

   public static final void saveScenarioTruces() {
      ArrayList<SaveManager.ScenarioData_Diplomacy> tData = new ArrayList<>();

      for (int i = 1; i < Game.getCivsSize(); i++) {
         if (Game.getCiv(i).diplomacy.truce.size() > 0) {
            SaveManager.ScenarioData_Diplomacy nData = new SaveManager.ScenarioData_Diplomacy();
            nData.pid = i;

            for (Diplomacy.DiplomacyData dData : Game.getCiv(i).diplomacy.truce.values()) {
               nData.w0.add(dData.iCivID);
               nData.t0.add(dData.iTurnID);
            }

            tData.add(nData);
         }
      }

      Json json = getJson();
      FileHandle file = FileManager.getSaveType("map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + ScenarioSettings.sTag + "/" + "Truces.json");
      file.writeString(json.toJson(tData), false);
      tData.clear();
      ArrayList<SaveManager.ScenarioData_Diplomacy> var5 = null;
   }

   public static final void saveScenarioNonAggression() {
      ArrayList<SaveManager.ScenarioData_Diplomacy> tData = new ArrayList<>();

      for (int i = 1; i < Game.getCivsSize(); i++) {
         if (Game.getCiv(i).diplomacy.nonAggressionPact.size() > 0) {
            SaveManager.ScenarioData_Diplomacy nData = new SaveManager.ScenarioData_Diplomacy();
            nData.pid = i;

            for (Diplomacy.DiplomacyData dData : Game.getCiv(i).diplomacy.nonAggressionPact.values()) {
               nData.w0.add(dData.iCivID);
               nData.t0.add(dData.iTurnID);
            }

            tData.add(nData);
         }
      }

      Json json = getJson();
      FileHandle file = FileManager.getSaveType("map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + ScenarioSettings.sTag + "/" + "NonAggression.json");
      file.writeString(json.toJson(tData), false);
      tData.clear();
      ArrayList<SaveManager.ScenarioData_Diplomacy> var5 = null;
   }

   public static final void saveScenarioMilitaryAccess() {
      ArrayList<SaveManager.ScenarioData_Diplomacy> tData = new ArrayList<>();

      for (int i = 1; i < Game.getCivsSize(); i++) {
         if (Game.getCiv(i).diplomacy.militaryAccess.size() > 0) {
            SaveManager.ScenarioData_Diplomacy nData = new SaveManager.ScenarioData_Diplomacy();
            nData.pid = i;

            for (Diplomacy.DiplomacyData dData : Game.getCiv(i).diplomacy.militaryAccess.values()) {
               nData.w0.add(dData.iCivID);
               nData.t0.add(dData.iTurnID);
            }

            tData.add(nData);
         }
      }

      Json json = getJson();
      FileHandle file = FileManager.getSaveType("map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + ScenarioSettings.sTag + "/" + "MilitaryAccess.json");
      file.writeString(json.toJson(tData), false);
      tData.clear();
      ArrayList<SaveManager.ScenarioData_Diplomacy> var5 = null;
   }

   public static final void saveScenarioGuarantee() {
      ArrayList<SaveManager.ScenarioData_Diplomacy> tData = new ArrayList<>();

      for (int i = 1; i < Game.getCivsSize(); i++) {
         if (Game.getCiv(i).diplomacy.guarantee.size() > 0) {
            SaveManager.ScenarioData_Diplomacy nData = new SaveManager.ScenarioData_Diplomacy();
            nData.pid = i;

            for (Diplomacy.DiplomacyData dData : Game.getCiv(i).diplomacy.guarantee.values()) {
               nData.w0.add(dData.iCivID);
               nData.t0.add(dData.iTurnID);
            }

            tData.add(nData);
         }
      }

      Json json = getJson();
      FileHandle file = FileManager.getSaveType("map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + ScenarioSettings.sTag + "/" + "Guarantee.json");
      file.writeString(json.toJson(tData), false);
      tData.clear();
      ArrayList<SaveManager.ScenarioData_Diplomacy> var5 = null;
   }

   public static final void saveProvinceDetails() {
      ArrayList<LoadManager.ProvinceDetails> tempDetails = new ArrayList<>();

      for (int i = 0; i < Game.getProvincesSize(); i++) {
         LoadManager.ProvinceDetails provinceDetails = new LoadManager.ProvinceDetails();
         if (Game.getProvince(i).getLevelOfPort() >= -1) {
            if (Game.getProvince(i).getTerrainID() == 0) {
               Game.getProvince(i).setTerrainID(1);
            }

            if (Game.getProvince(i).getContinent() == 0) {
               Game.getProvince(i).setContinent(1);
            }
         }

         provinceDetails.pid = i;
         provinceDetails.co = Game.getProvince(i).getContinent();
         provinceDetails.re = Game.getProvince(i).getGeoRegion();
         provinceDetails.tr = Game.getProvince(i).getTerrainID();
         provinceDetails.lp = Math.min(Game.getProvince(i).getLevelOfPort(), -1);
         provinceDetails.gr = Game.getProvince(i).getGrowthRate();
         provinceDetails.bd = Game.getProvince(i).BaseDevelopment;
         provinceDetails.rs = Game.getProvince(i).getResourceID();
         provinceDetails.sx = Game.getProvince(i).getShiftX() / Game.mapBG.iMapScale;
         provinceDetails.sy = Game.getProvince(i).getShiftY() / Game.mapBG.iMapScale;
         tempDetails.add(provinceDetails);
      }

      Json json = getJson();
      json.setElementType(LoadManager.ConfigJson.class, "Data", LoadManager.Data_ProvincePoints.class);
      FileHandle file = FileManager.getSaveType("map/" + Game.map.getFile_ActiveMap_Path() + "data/" + "ProvinceDetails.json");
      file.writeString(json.toJson(tempDetails), false);
   }

   public static final void saveProvinceNeighboringProvinces() {
      ArrayList<LoadManager.Data_ProvinceBorder> tempDetails = new ArrayList<>();
      int tAdded = 0;
      int fileID = 0;

      for (int i = 0; i < Game.getProvincesSize(); i++) {
         for (int j = 0; j < Game.getProvince(i).getProvinceBordersLandByLandSize(); j++) {
            LoadManager.Data_ProvinceBorder provinceNeighboringProvince = new LoadManager.Data_ProvinceBorder();
            provinceNeighboringProvince.pid = i;
            provinceNeighboringProvince.wp = Game.getProvince(i).getProvinceBordersLandByLand().get(j).withProvinceID;
            provinceNeighboringProvince.px = Game.getProvince(i).getProvinceBordersLandByLand().get(j).lPointsX;
            provinceNeighboringProvince.py = Game.getProvince(i).getProvinceBordersLandByLand().get(j).lPointsY;
            tempDetails.add(provinceNeighboringProvince);
            tAdded++;
         }

         for (int j = 0; j < Game.getProvince(i).getProvinceBordersLandBySeaSize(); j++) {
            LoadManager.Data_ProvinceBorder provinceNeighboringProvince = new LoadManager.Data_ProvinceBorder();
            provinceNeighboringProvince.pid = i;
            provinceNeighboringProvince.wp = Game.getProvince(i).getProvinceBordersLandBySea().get(j).withProvinceID;
            provinceNeighboringProvince.px = Game.getProvince(i).getProvinceBordersLandBySea().get(j).lPointsX;
            provinceNeighboringProvince.py = Game.getProvince(i).getProvinceBordersLandBySea().get(j).lPointsY;
            tempDetails.add(provinceNeighboringProvince);
            tAdded++;
         }

         for (int j = 0; j < Game.getProvince(i).getProvinceBordersSeaBySeaSize(); j++) {
            LoadManager.Data_ProvinceBorder provinceNeighboringProvince = new LoadManager.Data_ProvinceBorder();
            provinceNeighboringProvince.pid = i;
            provinceNeighboringProvince.wp = Game.getProvince(i).getProvinceBordersSeaBySea().get(j).withProvinceID;
            provinceNeighboringProvince.px = Game.getProvince(i).getProvinceBordersSeaBySea().get(j).lPointsX;
            provinceNeighboringProvince.py = Game.getProvince(i).getProvinceBordersSeaBySea().get(j).lPointsY;
            tempDetails.add(provinceNeighboringProvince);
            tAdded++;
         }

         if (tAdded >= 2000) {
            Json json = getJson();
            json.setElementType(LoadManager.ConfigJson.class, "Data", LoadManager.Data_ProvinceBorder.class);
            FileHandle file = FileManager.getSaveType(
               "map/"
                  + Game.map.getFile_ActiveMap_Path()
                  + "data/"
                  + "ProvinceNeighboringProvinces/ProvinceNeighboringProvinces"
                  + (fileID > 0 ? "_" + fileID : "")
                  + ".json"
            );
            file.writeString(json.toJson(tempDetails), false);
            tempDetails.clear();
            tAdded = 0;
            fileID++;
         }
      }

      if (tAdded > 0) {
         Json json = getJson();
         json.setElementType(LoadManager.ConfigJson.class, "Data", LoadManager.Data_ProvinceBorder.class);
         FileHandle file = FileManager.getSaveType(
            "map/"
               + Game.map.getFile_ActiveMap_Path()
               + "data/"
               + "ProvinceNeighboringProvinces/ProvinceNeighboringProvinces"
               + (fileID > 0 ? "_" + fileID : "")
               + ".json"
         );
         file.writeString(json.toJson(tempDetails), false);
         tempDetails.clear();
      }
   }

   public static final void saveScenarioDetails() {
      try {
         ArrayList<MapScenarios.ScenarioCivData> tempDetails = new ArrayList<>();

         for (int i = 1; i < Game.getCivsSize(); i++) {
            MapScenarios.ScenarioCivData tCivID = Game.getCiv(i).scenarioEditorData;
            tCivID.CivTAG = Game.getCiv(i).getCivTag();
            tCivID.CivID = Game.getCiv(i).getCivID();
            tCivID.CPID = Game.getCiv(i).getCapitalProvinceID();
            tCivID.PCID = Game.getCiv(i).getPuppetOfCivID();
            tempDetails.add(tCivID);
         }

         Json json = getJson();
         json.setSerializer(MapScenarios.ScenarioCivData.class, new MapScenarios.ScenarioCivDataSerializer());
         FileHandle file = FileManager.getSaveType("map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + ScenarioSettings.sTag + "/" + "Data.json");
         file.writeString(json.prettyPrint(tempDetails), false);
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public static final void saveScenarioDetailsProvinces() {
      try {
         ArrayList<MapScenarios.ScenarioCivData_Provinces> tempDetails = new ArrayList<>();

         for (int i = 1; i < Game.getCivsSize(); i++) {
            MapScenarios.ScenarioCivData_Provinces tCivID = new MapScenarios.ScenarioCivData_Provinces();
            int[] tempProvinces = new int[Game.getCiv(i).getNumOfProvinces()];

            for (int j = 0; j < Game.getCiv(i).getNumOfProvinces(); j++) {
               tempProvinces[j] = Game.getCiv(i).getProvinceID(j);
            }

            tCivID.Provinces = tempProvinces;
            tempDetails.add(tCivID);
         }

         Json json = getJson();
         FileHandle file = FileManager.getSaveType(
            "map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + ScenarioSettings.sTag + "/" + "DataProvinces.json"
         );
         file.writeString(json.prettyPrint(tempDetails), false);
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
      }
   }

   public static final void saveScenarioCores() {
      try {
         ArrayList<MapScenarios.ScenarioCoresData> tempDetails = new ArrayList<>();

         for (int i = 0; i < Game.getProvincesSize(); i++) {
            if (!Game.getProvince(i).getSeaProvince() && Game.getProvince(i).getWasteland() < 0 && Game.getProvince(i).getCivID() > 0) {
               Game.getProvince(i).updateHaveACore();
               if (!Game.getProvince(i).haveACore || Game.getProvince(i).iCoresSize > 1) {
                  MapScenarios.ScenarioCoresData tCores = new MapScenarios.ScenarioCoresData();
                  tCores.id = i;
                  tCores.Cores = new ArrayList<>();
                  if (Game.getProvince(i).iCoresSize == 0) {
                     tCores.Cores.add(0);
                  } else {
                     for (int j = 0; j < Game.getProvince(i).iCoresSize; j++) {
                        tCores.Cores.add(Game.getProvince(i).getCore(j));
                     }
                  }

                  if (tCores.Cores.size() > 0) {
                     tempDetails.add(tCores);
                  }
               }
            }
         }

         Json json = getJson();
         FileHandle file = FileManager.getSaveType("map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + ScenarioSettings.sTag + "/" + "Cores.json");
         file.writeString(json.prettyPrint(tempDetails), false);
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   public static final void saveScenarioReligion() {
      try {
         ArrayList<MapScenarios.ScenarioReligionData> tempDetails = new ArrayList<>();

         for (int i = 0; i < Game.getProvincesSize(); i++) {
            if (!Game.getProvince(i).getSeaProvince()
               && Game.getProvince(i).getWasteland() < 0
               && Game.getCiv(Game.getProvince(i).getCivID()).getReligionID() != Game.mapScenarios.editorProvinceReligion.get(i)
               && Game.mapScenarios.editorProvinceReligion.get(i) >= 0) {
               MapScenarios.ScenarioReligionData tReligion = new MapScenarios.ScenarioReligionData();
               tReligion.id = i;
               tReligion.rel = Game.mapScenarios.editorProvinceReligion.get(i);
               tempDetails.add(tReligion);
            }
         }

         Json json = getJson();
         FileHandle file = FileManager.getSaveType("map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + ScenarioSettings.sTag + "/" + "Religions.json");
         file.writeString(json.prettyPrint(tempDetails), false);
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public static final void saveProvinceNamesPoints() {
      ArrayList<LoadManager.ProvinceNamesPoints> tempData = new ArrayList<>();

      for (int i = 0; i < Game.getProvincesSize(); i++) {
         LoadManager.ProvinceNamesPoints provincePoints = new LoadManager.ProvinceNamesPoints();
         if (ProvinceNamesManager.provinceNames.get(i) != null) {
            provincePoints.pid = i;
            provincePoints.fX = ProvinceNamesManager.provinceNames.get(i).fX / Game.mapBG.iMapScale;
            provincePoints.fY = ProvinceNamesManager.provinceNames.get(i).fY / Game.mapBG.iMapScale;
            provincePoints.fX2 = ProvinceNamesManager.provinceNames.get(i).fX2 / Game.mapBG.iMapScale;
            provincePoints.fY2 = ProvinceNamesManager.provinceNames.get(i).fY2 / Game.mapBG.iMapScale;
            provincePoints.cx = ProvinceNamesManager.provinceNames.get(i).fCenterX / Game.mapBG.iMapScale;
            provincePoints.cy = ProvinceNamesManager.provinceNames.get(i).fCenterY / Game.mapBG.iMapScale;
         } else {
            provincePoints.pid = i;
            provincePoints.fX = ProvinceNamesManager.NULL_INDICATOR;
            provincePoints.fY = ProvinceNamesManager.NULL_INDICATOR;
            provincePoints.fX2 = ProvinceNamesManager.NULL_INDICATOR;
            provincePoints.fY2 = ProvinceNamesManager.NULL_INDICATOR;
            provincePoints.cx = ProvinceNamesManager.NULL_INDICATOR;
            provincePoints.cy = ProvinceNamesManager.NULL_INDICATOR;
         }

         tempData.add(provincePoints);
      }

      Json json = getJson();
      json.setElementType(LoadManager.ConfigJson.class, "Data", LoadManager.ProvinceNamesPoints.class);
      FileHandle file = FileManager.getSaveType("map/" + Game.map.getFile_ActiveMap_Path() + "data/" + "ProvinceNamePoints.json");
      file.writeString(json.toJson(tempData), false);
   }

   public static final void saveScenariosList() {
      String tList = "";
      if (FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "Scenarios.txt").exists()) {
         FileHandle file2 = FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "Scenarios.txt");
         String tempTags = file2.readString();
         tList = tempTags;
         String[] tSplited = tempTags.split(";");
         boolean add = true;
         int i = 0;

         for (int iSize = tSplited.length; i < iSize; i++) {
            if (tSplited[i].equals(ScenarioSettings.sTag)) {
               add = false;
               break;
            }
         }

         if (add) {
            tList = tempTags + ScenarioSettings.sTag + ";";
         }
      } else {
         tList = tList + ScenarioSettings.sTag + ";";
      }

      FileHandle fileSave = FileManager.getSaveType("map/" + Game.map.getFile_ActiveMap_Path() + "Scenarios.txt");
      fileSave.writeString(tList, false);
   }

   public static final void saveFormableCiv() {
      Json json = getJson();
      json.setElementType(FormableCivManager.ConfigJson.class, "Data", FormableCivManager.FormableCiv.class);
      FileHandle file = FileManager.getSaveType(
         "map/" + Game.map.getFile_ActiveMap_Path() + "formableCivs/" + "data/" + FormableCivManager.activeFormableCiv.FormableCivTag + ".txt"
      );
      file.writeString(json.prettyPrint(FormableCivManager.activeFormableCiv), false);
      String tList = "";
      if (FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "formableCivs/" + "AoH.txt").exists()) {
         FileHandle file2 = FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "formableCivs/" + "AoH.txt");
         String tempTags = file2.readString();
         tList = tempTags;
         String[] tSplited = tempTags.split(";");
         boolean add = true;
         int i = 0;

         for (int iSize = tSplited.length; i < iSize; i++) {
            if (tSplited[i].equals(FormableCivManager.activeFormableCiv.FormableCivTag + ".txt")) {
               add = false;
               break;
            }
         }

         if (add) {
            tList = tempTags + FormableCivManager.activeFormableCiv.FormableCivTag + ".txt;";
         }
      } else {
         tList = tList + FormableCivManager.activeFormableCiv.FormableCivTag + ".txt;";
      }

      FileHandle fileSave = FileManager.getSaveType("map/" + Game.map.getFile_ActiveMap_Path() + "formableCivs/" + "AoH.txt");
      fileSave.writeString(tList, false);
   }

   public static class ScenarioData_Army {
      public int ProvinceID;
      public List<Integer> UnitTypeID = new ArrayList<>();
      public List<Integer> ArmyID = new ArrayList<>();
   }

   public static class ScenarioData_Buildings {
      public int pid;
      public List<Integer> b0 = new ArrayList<>();
      public List<Integer> b1 = new ArrayList<>();
   }

   public static class ScenarioData_Diplomacy {
      public int pid;
      public List<Integer> w0 = new ArrayList<>();
      public List<Integer> t0 = new ArrayList<>();
   }

   public static class ScenarioData_Relations {
      public int c;
      public List<Integer> w = new ArrayList<>();
      public List<Integer> r = new ArrayList<>();
   }
}
