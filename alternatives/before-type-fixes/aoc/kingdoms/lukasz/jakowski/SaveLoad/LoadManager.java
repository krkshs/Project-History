package aoc.kingdoms.lukasz.jakowski.SaveLoad;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.FileManager;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.map.allianceHRE.Alliance;
import aoc.kingdoms.lukasz.map.army.ArmyDivision;
import aoc.kingdoms.lukasz.map.army.ArmyRegiment;
import aoc.kingdoms.lukasz.map.province.Province;
import aoc.kingdoms.lukasz.map.province.ProvinceConstructedBuilding;
import aoc.kingdoms.lukasz.map.province.ProvinceCut;
import aoc.kingdoms.lukasz.map.province.ProvinceNameData;
import aoc.kingdoms.lukasz.map.province.ProvinceNamesManager;
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
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import java.util.ArrayList;
import java.util.List;

public class LoadManager {
   public static int loadProvincePointsFileID = 0;
   public static int loadProvinceBorderFileID = 0;

   public static final boolean loadProvincePoints() {
      try {
         if (loadProvincePoints(loadProvincePointsFileID++)) {
            return true;
         }

         Game.iProvincesSize = Game.lProvinces.size();
      } catch (Exception var1) {
         CFG.LOG(var1);
      }

      return false;
   }

   private static final boolean loadProvincePoints(int id) {
      try {
         FileHandle fileList = FileManager.loadFile(
            "map/" + Game.map.getFile_ActiveMap_Path() + "data/" + "ProvincePoints/ProvincePoints" + (id > 0 ? "_" + id : "") + ".json"
         );
         String fileContent = fileList.readString();
         Json json = new Json();
         json.setElementType(LoadManager.ConfigJson.class, "Data", LoadManager.Data_ProvincePoints.class);
         LoadManager.ConfigJson data = json.fromJson(LoadManager.ConfigJson.class, fileContent);
         int tempProvinceID = Game.lProvinces.size();

         for (Object e : data.Data) {
            LoadManager.Data_ProvincePoints tempData = (LoadManager.Data_ProvincePoints)e;
            Game.lProvinces.add(new Province(tempProvinceID++, tempData.pX, tempData.pY));
            Game.lProvincesData.add(new ProvinceData());
            Game.lProvincesData2.add(new ProvinceData2());
            Game.lProvincesData3.add(new ProvinceData3());
            Game.lProvincesData4.add(new ProvinceData4());
            Game.lProvincesData5.add(new ProvinceData5());
            Game.lProvincesData6.add(new ProvinceData6());
            Game.lProvincesData7.add(new ProvinceData7());
            Game.lProvincesData8.add(new ProvinceData8());
            Game.lProvincesData9.add(new ProvinceData9());
            Game.lProvincesData10.add(new ProvinceData10());
            Game.lProvincesPopulation.add(new ProvinceData_Population());
            tempData = null;
         }

         data = null;
         FileHandle var10 = null;
         fileContent = null;
         return true;
      } catch (Exception var9) {
         return false;
      }
   }

   public static final void loadProvincePoints_Cut() {
      Game.cutProvinces = new ArrayList<>();

      try {
         int i = 0;

         while (i < 5000 && loadProvincePoints_Cut(i)) {
            i++;
         }
      } catch (GdxRuntimeException var1) {
         CFG.LOG(var1);
      }
   }

   private static final boolean loadProvincePoints_Cut(int id) {
      try {
         FileHandle fileList = FileManager.loadFile(
            "map/" + Game.map.getFile_ActiveMap_Path() + "data/" + "ProvincePoints_Cut/ProvincePoints_Cut" + (id > 0 ? "_" + id : "") + ".json"
         );
         String fileContent = fileList.readString();
         Json json = new Json();
         json.setElementType(LoadManager.ConfigJson.class, "Data", LoadManager.Data_ProvincePoints.class);
         LoadManager.ConfigJson data = json.fromJson(LoadManager.ConfigJson.class, fileContent);

         for (Object e : data.Data) {
            LoadManager.Data_ProvincePoints tempData = (LoadManager.Data_ProvincePoints)e;
            Game.cutProvinces.add(new ProvinceCut(tempData.pX, tempData.pY));
            tempData = null;
         }

         data = null;
         return true;
      } catch (Exception var8) {
         return false;
      }
   }

   public static final boolean loadProvinceBorder() {
      try {
         int id = loadProvinceBorderFileID++;
         FileHandle fileList = FileManager.loadFile(
            "map/"
               + Game.map.getFile_ActiveMap_Path()
               + "data/"
               + "ProvinceNeighboringProvinces/ProvinceNeighboringProvinces"
               + (id > 0 ? "_" + id : "")
               + ".json"
         );
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);

         for (JsonValue jValue : tempArrayData) {
            LoadManager.Data_ProvinceBorder tempData = json.readValue(LoadManager.Data_ProvinceBorder.class, jValue);
            Game.getProvince(tempData.pid).addProvinceBorder(tempData.wp, tempData.px, tempData.py);
            tempData = null;
         }

         tempArrayData.clear();
         ArrayList<JsonValue> var10 = null;
         fileList = null;
         return true;
      } catch (Exception var7) {
      } catch (GdxRuntimeException var8) {
         CFG.LOG(var8);
      }

      return false;
   }

   public static final void loadScenarioArmies() {
      try {
         FileHandle fileList = FileManager.loadFile(
            "map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + Game.mapScenarios.lScenarios_TagsList.get(Game.scenarioID) + "/" + "Armies.json"
         );
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);

         for (JsonValue jValue : tempArrayData) {
            SaveManager.ScenarioData_Army tempData = json.readValue(SaveManager.ScenarioData_Army.class, jValue);
            if (tempData.ProvinceID >= 0 && tempData.ProvinceID < Game.getProvincesSize() && Game.getProvince(tempData.ProvinceID).getCivID() > 0) {
               List<ArmyRegiment> nArmyRegiment = new ArrayList<>();
               int i = 0;

               for (int iSize = tempData.UnitTypeID.size(); i < iSize; i++) {
                  nArmyRegiment.add(new ArmyRegiment(tempData.UnitTypeID.get(i), tempData.ArmyID.get(i)));
               }

               if (nArmyRegiment.size() > 0) {
                  Game.getProvince(tempData.ProvinceID)
                     .addArmy(new ArmyDivision(Game.getProvince(tempData.ProvinceID).getCivID(), tempData.ProvinceID, nArmyRegiment));
               }
            }

            tempData = null;
         }

         tempArrayData.clear();
         Object var10 = null;
      } catch (Exception var9) {
         CFG.exceptionStack(var9);
      }
   }

   public static final void loadScenarioBuildings() {
      try {
         FileHandle fileList = FileManager.loadFile(
            "map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + Game.mapScenarios.lScenarios_TagsList.get(Game.scenarioID) + "/" + "Buildings.json"
         );
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);

         for (JsonValue jValue : tempArrayData) {
            SaveManager.ScenarioData_Buildings tempData = json.readValue(SaveManager.ScenarioData_Buildings.class, jValue);
            if (tempData.pid >= 0 && tempData.pid < Game.getProvincesSize() && Game.getProvince(tempData.pid).getCivID() > 0) {
               int i = 0;

               for (int iSize = tempData.b0.size(); i < iSize; i++) {
                  Game.getProvince(tempData.pid).addNewBuilding_LoadScenario(new ProvinceConstructedBuilding(tempData.b0.get(i), tempData.b1.get(i)));
               }
            }
         }

         tempArrayData.clear();
         Object var9 = null;
      } catch (Exception var8) {
         CFG.exceptionStack(var8);
      }
   }

   public static final void loadScenarioRelations() {
      try {
         FileHandle fileList = FileManager.loadFile(
            "map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + Game.mapScenarios.lScenarios_TagsList.get(Game.scenarioID) + "/" + "Relations.json"
         );
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);

         for (JsonValue jValue : tempArrayData) {
            SaveManager.ScenarioData_Relations tempData = json.readValue(SaveManager.ScenarioData_Relations.class, jValue);
            if (tempData.c > 0 && tempData.c < Game.getCivsSize()) {
               for (int i = tempData.w.size() - 1; i >= 0; i--) {
                  Game.getCiv(tempData.c).diplomacy.setRelation(tempData.c, tempData.w.get(i), tempData.r.get(i).intValue());
               }
            }

            tempData = null;
         }

         tempArrayData.clear();
         Object var8 = null;
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }
   }

   public static final void loadScenarioAlliances() {
      try {
         FileHandle fileList = FileManager.loadFile(
            "map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + Game.mapScenarios.lScenarios_TagsList.get(Game.scenarioID) + "/" + "Alliances.json"
         );
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

   public static final void loadScenarioDefensive() {
      try {
         FileHandle fileList = FileManager.loadFile(
            "map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + Game.mapScenarios.lScenarios_TagsList.get(Game.scenarioID) + "/" + "Defensive.json"
         );
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

   public static final void loadScenarioTruces() {
      try {
         FileHandle fileList = FileManager.loadFile(
            "map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + Game.mapScenarios.lScenarios_TagsList.get(Game.scenarioID) + "/" + "Truces.json"
         );
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

   public static final void loadScenarioAlliancesSpecial() {
      try {
         FileHandle fileList = FileManager.loadFile(
            "map/"
               + Game.map.getFile_ActiveMap_Path()
               + "scenarios/"
               + Game.mapScenarios.lScenarios_TagsList.get(Game.scenarioID)
               + "/"
               + "AlliancesSpecial.json"
         );
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);
         if (tempArrayData != null && tempArrayData.size() > 0) {
            for (JsonValue jValue : tempArrayData) {
               Alliance tempData = json.readValue(Alliance.class, jValue);
               Game.alliancesSpecial.add(tempData);
            }
         }

         Game.alliancesSpecialSize = Game.alliancesSpecial.size();
         tempArrayData.clear();
         Object var7 = null;
      } catch (Exception var6) {
         CFG.exceptionStack(var6);
      }
   }

   public static final void loadScenarioNonAggression() {
      try {
         FileHandle fileList = FileManager.loadFile(
            "map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + Game.mapScenarios.lScenarios_TagsList.get(Game.scenarioID) + "/" + "NonAggression.json"
         );
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

   public static final void loadScenarioMilitaryAccess() {
      try {
         FileHandle fileList = FileManager.loadFile(
            "map/"
               + Game.map.getFile_ActiveMap_Path()
               + "scenarios/"
               + Game.mapScenarios.lScenarios_TagsList.get(Game.scenarioID)
               + "/"
               + "MilitaryAccess.json"
         );
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

   public static final void loadScenarioGuarantee() {
      try {
         FileHandle fileList = FileManager.loadFile(
            "map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + Game.mapScenarios.lScenarios_TagsList.get(Game.scenarioID) + "/" + "Guarantee.json"
         );
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

   public static final void loadProvinceMapData() {
      loadProvinceDetails();
      buildLevelOfPort();
   }

   public static final void loadProvinceDetails() {
      try {
         for (int i = 0; i < Game.getProvincesSize(); i++) {
            Game.getProvince(i).setShiftX(0);
            Game.getProvince(i).setShiftY(0);
         }

         if (FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "data/" + "ProvinceDetails.json").exists()) {
            FileHandle fileList = FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "data/" + "ProvinceDetails.json");
            Json json = new Json();
            ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);

            for (JsonValue jValue : tempArrayData) {
               LoadManager.ProvinceDetails tempData = json.readValue(LoadManager.ProvinceDetails.class, jValue);
               if (tempData.pid < Game.getProvincesSize()) {
                  Game.getProvince(tempData.pid).setContinent(tempData.co);
                  Game.getProvince(tempData.pid).setGeoRegion(tempData.re);
                  Game.getProvince(tempData.pid).setTerrainID(tempData.tr);
                  Game.getProvince(tempData.pid).setLevelOfPort(tempData.lp);
                  Game.getProvince(tempData.pid).setResourceID(tempData.rs);
                  Game.getProvince(tempData.pid).BaseDevelopment = tempData.bd;
                  Game.getProvince(tempData.pid).setGrowthRate(tempData.gr);
                  Game.getProvince(tempData.pid).setShiftX(tempData.sx * Game.mapBG.iMapScale);
                  Game.getProvince(tempData.pid).setShiftY(tempData.sy * Game.mapBG.iMapScale);
                  Game.getProvince(tempData.pid).updateSeaProvince();
                  if (Game.getProvince(tempData.pid).getSeaProvince()) {
                     Game.getProvince(tempData.pid).setTerrainID(0);
                  } else if (Game.getProvince(tempData.pid).getTerrainID() == 0) {
                     Game.getProvince(tempData.pid).setTerrainID(1);
                  }

                  Object var9 = null;
               }
            }

            tempArrayData.clear();
            Object var8 = null;
         }
      } catch (GdxRuntimeException var6) {
         CFG.LOG(var6);
      }
   }

   public static final void buildNeighboringProvinces() {
      for (int i = 0; i < Game.getProvincesSize(); i++) {
         for (int j = 0; j < Game.getProvince(i).getProvinceBordersLandByLandSize(); j++) {
            update(i, Game.getProvince(i).getProvinceBordersLandByLand().get(j).getWithProvinceID());
         }

         for (int j = 0; j < Game.getProvince(i).getProvinceBordersLandBySeaSize(); j++) {
            update(i, Game.getProvince(i).getProvinceBordersLandBySea().get(j).getWithProvinceID());
         }

         for (int j = 0; j < Game.getProvince(i).getProvinceBordersSeaBySeaSize(); j++) {
            update(i, Game.getProvince(i).getProvinceBordersSeaBySea().get(j).getWithProvinceID());
         }
      }
   }

   public static final void update(int province1, int province2) {
      if (Game.getProvince(province1).getSeaProvince()) {
         Game.getProvince(province1).addNeighboringProvince(province2);
      } else if (Game.getProvince(province2).getSeaProvince()) {
         Game.getProvince(province1).addNeighboringSeaProvince(province2);
         Game.getProvince(province1).setLevelOfPort(1);
      } else {
         Game.getProvince(province1).addNeighboringProvince(province2);
      }

      if (Game.getProvince(province2).getSeaProvince()) {
         Game.getProvince(province2).addNeighboringProvince(province1);
      } else if (Game.getProvince(province1).getSeaProvince()) {
         Game.getProvince(province2).addNeighboringSeaProvince(province1);
         Game.getProvince(province2).setLevelOfPort(1);
      } else {
         Game.getProvince(province2).addNeighboringProvince(province1);
      }
   }

   public static final void buildLevelOfPort() {
      for (int i = 0; i < Game.getProvincesSize(); i++) {
         if (Game.getProvince(i).getNeighboringSeaProvincesSize() > 0) {
            Game.getProvince(i).setLevelOfPort(0);
         }
      }
   }

   public static final void loadProvinceNamesPoints() {
      try {
         if (FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "data/" + "ProvinceNamePoints.json").exists()) {
            FileHandle fileList = FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "data/" + "ProvinceNamePoints.json");
            Json json = new Json();
            ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);

            for (JsonValue jValue : tempArrayData) {
               LoadManager.ProvinceNamesPoints tempData = json.readValue(LoadManager.ProvinceNamesPoints.class, jValue);
               ProvinceNameData nData;
               if (tempData.fX == ProvinceNamesManager.NULL_INDICATOR) {
                  nData = null;
               } else {
                  nData = new ProvinceNameData();
                  nData.fX = tempData.fX * Game.mapBG.iMapScale;
                  nData.fX2 = tempData.fX2 * Game.mapBG.iMapScale;
                  nData.fY = tempData.fY * Game.mapBG.iMapScale;
                  nData.fY2 = tempData.fY2 * Game.mapBG.iMapScale;
                  nData.fCenterX = tempData.cx * Game.mapBG.iMapScale;
                  nData.fCenterY = tempData.cy * Game.mapBG.iMapScale;
               }

               ProvinceNamesManager.provinceNames.add(nData);
               Object var9 = null;
            }

            tempArrayData.clear();
            Object var8 = null;
         }
      } catch (GdxRuntimeException var7) {
         CFG.LOG(var7);
      }
   }

   public static class ConfigJson {
      public String Age_of_History;
      public ArrayList Data;
   }

   public static class ConfigJson2 {
      public ArrayList Data;
   }

   public static class Data_ProvinceBorder {
      public int pid;
      public int wp;
      public List<Integer> px;
      public List<Integer> py;
   }

   public static class Data_ProvincePoints {
      List<Short> pX;
      List<Short> pY;
   }

   protected static class ProvinceDetails {
      int pid;
      int co;
      int re;
      int tr;
      int lp;
      int rs = -1;
      float gr;
      float bd;
      int sx;
      int sy;
   }

   protected static class ProvinceNamesPoints {
      int pid;
      float fX;
      float fY;
      float fX2;
      float fY2;
      float cx;
      float cy;
   }

   protected static class ScenarioData {
      String sCivTAG;
      int iCapitalProvinceID;
      int[] lProvinces;
   }
}
