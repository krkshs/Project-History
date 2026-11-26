package aoc.kingdoms.lukasz.jakowski.SaveLoad;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.FileManager;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.map.FormableCivManager;
import aoc.kingdoms.lukasz.map.WondersManager;
import aoc.kingdoms.lukasz.menu.View;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import java.util.ArrayList;
import java.util.List;

public class ExportData {
   public static final void Save_Save_ProvinceData_3M() {
      ArrayList<ExportData.Save_ProvinceData_3M> tSaveDetails = new ArrayList<>();

      for (int i = 0; i < Game.getProvincesSize(); i++) {
         if (!Game.getProvince(i).getSeaProvince() && Game.getProvince(i).getCitiesSize() > 0) {
            ExportData.Save_ProvinceData_3M tData = new ExportData.Save_ProvinceData_3M();
            tData.iX = Game.getProvince(i).getCity(0).getPosX() / Game.mapBG.iMapScale;
            tData.iY = Game.getProvince(i).getCity(0).getPosY() / Game.mapBG.iMapScale;
            tData.terrainID = Game.getProvince(i).getTerrainID();
            tData.resourceID = Game.getProvince(i).getResourceID();
            tData.baseDevelopment = Game.getProvince(i).BaseDevelopment;
            tSaveDetails.add(tData);
         }
      }

      Json json = SaveManager.getJson();
      FileHandle file = Gdx.files.local("P3M.json");
      file.writeString(json.toJson(tSaveDetails), false);
      tSaveDetails.clear();
      ArrayList<ExportData.Save_ProvinceData_3M> var3 = null;
   }

   public static final void loadSave_ProvinceData_3M() {
      try {
         FileHandle fileList = FileManager.loadFile("P3M.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);

         for (JsonValue jValue : tempArrayData) {
            ExportData.Save_ProvinceData_3M tempData = json.readValue(ExportData.Save_ProvinceData_3M.class, jValue);
            int provID = Game.setProvinceID_Point(tempData.iX * Game.mapBG.iMapScale, tempData.iY * Game.mapBG.iMapScale);
            if (provID >= 0 && !Game.getProvince(provID).getSeaProvince()) {
               if (Game.getProvince(provID).getResourceID() >= 0) {
                  if (Game.oR.nextInt(100) < 25) {
                     Game.getProvince(provID).setTerrainID(tempData.terrainID);
                     Game.getProvince(provID).setResourceID(tempData.resourceID);
                     Game.getProvince(provID).BaseDevelopment = tempData.baseDevelopment;
                  }
               } else {
                  Game.getProvince(provID).setTerrainID(tempData.terrainID);
                  Game.getProvince(provID).setResourceID(tempData.resourceID);
                  Game.getProvince(provID).BaseDevelopment = tempData.baseDevelopment;
               }
            }

            Object var9 = null;
         }

         tempArrayData.clear();
         Object var8 = null;
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }
   }

   public static final void Save_Save_ProvinceData_Images() {
      ArrayList<ExportData.Save_ProvinceData_Images> tSaveDetails = new ArrayList<>();

      for (int i = 0; i < Game.getProvincesSize(); i++) {
         if (!Game.getProvince(i).getSeaProvince()
            && Game.getProvince(i).getCitiesSize() > 0
            && FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "provinces/" + i + ".png").exists()) {
            ExportData.Save_ProvinceData_Images tData = new ExportData.Save_ProvinceData_Images();
            tData.iX = Game.getProvince(i).getCity(0).getPosX() / Game.mapBG.iMapScale;
            tData.iY = Game.getProvince(i).getCity(0).getPosY() / Game.mapBG.iMapScale;
            tData.imgID = i;
            tSaveDetails.add(tData);
         }
      }

      Json json = SaveManager.getJson();
      FileHandle file = Gdx.files.local("P_IMG.json");
      file.writeString(json.toJson(tSaveDetails), false);
      tSaveDetails.clear();
      ArrayList<ExportData.Save_ProvinceData_Images> var3 = null;
   }

   public static final void loadSave_ProvinceData_Images() {
      try {
         FileHandle fileList = FileManager.loadFile("P_IMG.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);

         for (JsonValue jValue : tempArrayData) {
            ExportData.Save_ProvinceData_Images tempData = json.readValue(ExportData.Save_ProvinceData_Images.class, jValue);
            int provID = Game.setProvinceID_Point(tempData.iX * Game.mapBG.iMapScale, tempData.iY * Game.mapBG.iMapScale);
            if (provID >= 0 && !Game.getProvince(provID).getSeaProvince()) {
               CFG.LOG("" + tempData.imgID + ".png -----> " + provID);
            }

            tempData = null;
         }

         tempArrayData.clear();
         Object var8 = null;
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }
   }

   public static final void Save_Save_ProvinceData_Wonders() {
      ArrayList<ExportData.Save_ProvinceData_Images> tSaveDetails = new ArrayList<>();

      for (int i = 0; i < WondersManager.wondersSize; i++) {
         ExportData.Save_ProvinceData_Images tData = new ExportData.Save_ProvinceData_Images();
         tData.iX = Game.getProvince(WondersManager.wonders.get(i).ProvinceID).getCity(0).getPosX() / Game.mapBG.iMapScale;
         tData.iY = Game.getProvince(WondersManager.wonders.get(i).ProvinceID).getCity(0).getPosY() / Game.mapBG.iMapScale;
         tData.imgID = WondersManager.wonders.get(i).ProvinceID;
         tSaveDetails.add(tData);
      }

      Json json = SaveManager.getJson();
      FileHandle file = Gdx.files.local("P_WOND.json");
      file.writeString(json.toJson(tSaveDetails), false);
      tSaveDetails.clear();
      ArrayList<ExportData.Save_ProvinceData_Images> var3 = null;
   }

   public static final void loadSave_ProvinceData_Wonders() {
      try {
         FileHandle fileList = FileManager.loadFile("P_WOND.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);

         for (JsonValue jValue : tempArrayData) {
            ExportData.Save_ProvinceData_Images tempData = json.readValue(ExportData.Save_ProvinceData_Images.class, jValue);
            int provID = Game.setProvinceID_Point(tempData.iX * Game.mapBG.iMapScale, tempData.iY * Game.mapBG.iMapScale);
            if (provID >= 0 && !Game.getProvince(provID).getSeaProvince()) {
               CFG.LOG("Old prov: " + tempData.imgID + " -----> New: " + provID);
            }

            tempData = null;
         }

         tempArrayData.clear();
         Object var8 = null;
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }
   }

   public static final void Save_Save_ProvinceData_ScenarioCivs() {
      ArrayList<ExportData.Save_ProvinceData_ScenarioCivs> tSaveDetails = new ArrayList<>();

      for (int i = 1; i < Game.getCivsSize(); i++) {
         if (Game.getCiv(i).getNumOfProvinces() > 0 && Game.getProvince(Game.getCiv(i).getCapitalProvinceID()).getCitiesSize() > 0) {
            ExportData.Save_ProvinceData_ScenarioCivs tData = new ExportData.Save_ProvinceData_ScenarioCivs();
            tData.iX = Game.getProvince(Game.getCiv(i).getCapitalProvinceID()).getCity(0).getPosX() / Game.mapBG.iMapScale;
            tData.iY = Game.getProvince(Game.getCiv(i).getCapitalProvinceID()).getCity(0).getPosY() / Game.mapBG.iMapScale;
            tData.sTag = Game.getCiv(i).getCivTag();
            tData.numOfProvinces = Game.getCiv(i).getNumOfProvinces();
            tSaveDetails.add(tData);
         }
      }

      Json json = SaveManager.getJson();
      FileHandle file = Gdx.files.local("P_SCEN.json");
      file.writeString(json.toJson(tSaveDetails), false);
      tSaveDetails.clear();
      ArrayList<ExportData.Save_ProvinceData_ScenarioCivs> var3 = null;
   }

   public static final void Save_Save_ProvinceData_ScenarioCivsAssign() {
      ArrayList<ExportData.Save_ProvinceData_ScenarioCivs> tSaveDetails = new ArrayList<>();

      for (int i = 0; i < Game.getProvincesSize(); i++) {
         if (Game.getProvince(i).getCivID() > 0 && Game.getProvince(i).getCitiesSize() > 0) {
            ExportData.Save_ProvinceData_ScenarioCivs tData = new ExportData.Save_ProvinceData_ScenarioCivs();
            tData.iX = Game.getProvince(i).getCity(0).getPosX() / Game.mapBG.iMapScale;
            tData.iY = Game.getProvince(i).getCity(0).getPosY() / Game.mapBG.iMapScale;
            tData.sTag = Game.getCiv(Game.getProvince(i).getCivID()).getCivTag();
            tData.numOfProvinces = Game.getCiv(Game.getProvince(i).getCivID()).getNumOfProvinces();
            tSaveDetails.add(tData);
         }
      }

      Json json = SaveManager.getJson();
      FileHandle file = Gdx.files.local("P_SCEN_ASSI.json");
      file.writeString(json.toJson(tSaveDetails), false);
      tSaveDetails.clear();
      ArrayList<ExportData.Save_ProvinceData_ScenarioCivs> var3 = null;
   }

   public static final void loadSave_ProvinceData_ScenarioCivs() {
      try {
         FileHandle fileList = FileManager.loadFile("P_SCEN.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);
         Game.mapScenarios.createScenario();
         Game.mapBG.requestToDisposeMinimap = true;
         CFG.iCreateScenario_AssignProvinces_Civ = 0;
         Game.mapScenarios.loadReligions_JustBuild(true);
         Game.mapScenarios.scenarioEditor_isCampaign = false;
         Game.menuManager.setViewID(View.SCENARIO_WASTELAND_CONTINENTS);

         for (JsonValue jValue : tempArrayData) {
            ExportData.Save_ProvinceData_ScenarioCivs tempData = json.readValue(ExportData.Save_ProvinceData_ScenarioCivs.class, jValue);
            int provID = Game.setProvinceID_Point(tempData.iX * Game.mapBG.iMapScale, tempData.iY * Game.mapBG.iMapScale);
            if (Game.getProvince(provID).getCivID() > 0) {
               CFG.LOG("NOT ADDED: " + Game.lang.getCiv(tempData.sTag));
            } else {
               Game.addCivilization(tempData.sTag, provID, true, true, false, true, false);
            }

            tempData = null;
         }

         Game.iCivsSize = Game.lCivs.size();
         tempArrayData.clear();
         Object var8 = null;
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }
   }

   public static final void loadSave_ProvinceData_ScenarioCivs_Assign() {
      try {
         FileHandle fileList = FileManager.loadFile("P_SCEN_ASSI.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);

         for (JsonValue jValue : tempArrayData) {
            ExportData.Save_ProvinceData_ScenarioCivs tempData = json.readValue(ExportData.Save_ProvinceData_ScenarioCivs.class, jValue);
            int provID = Game.setProvinceID_Point(tempData.iX * Game.mapBG.iMapScale, tempData.iY * Game.mapBG.iMapScale);
            if (provID >= 0 && Game.getProvince(provID).getCivID() <= 0) {
               int civID = Game.getCivID(tempData.sTag);
               if (civID > 0) {
                  Game.getProvince(provID).setCivID_RemoveOldAddNewToCiv(civID);
               }
            }

            tempData = null;
         }

         tempArrayData.clear();
         Object var9 = null;
      } catch (Exception var8) {
         CFG.exceptionStack(var8);
      }
   }

   public static void exportFormableCivs() {
      List<ExportData.FormableCiv_Export> exportData = new ArrayList<>();
      if (FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "formableCivs/" + "AoH.txt").exists()) {
         FileHandle tempFileT = FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "formableCivs/" + "AoH.txt");
         String tempT = tempFileT.readString();
         String[] tagsSPLITED = tempT.split(";");
         int i = 0;

         for (int iSize = tagsSPLITED.length; i < iSize; i++) {
            try {
               FormableCivManager.loadActiveFormableCivilization(tagsSPLITED[i]);
               if (FormableCivManager.activeFormableCiv.CapitalProvinceID >= 0) {
                  ExportData.FormableCiv_Export data = new ExportData.FormableCiv_Export();
                  data.FormableCivTag = FormableCivManager.activeFormableCiv.FormableCivTag;
                  int j = 0;

                  for (int jSize = FormableCivManager.activeFormableCiv.ClaimantsTag.size(); j < jSize; j++) {
                     data.ClaimantsTag.add(FormableCivManager.activeFormableCiv.ClaimantsTag.get(j));
                  }

                  j = 0;

                  for (int jSize = FormableCivManager.activeFormableCiv.Provinces.size(); j < jSize; j++) {
                     if (Game.getProvince(FormableCivManager.activeFormableCiv.Provinces.get(j)).getCitiesSize() > 0) {
                        data.ProvincesX
                           .add(Game.getProvince(FormableCivManager.activeFormableCiv.Provinces.get(j)).getCity(0).getPosX() / Game.mapBG.iMapScale);
                        data.ProvincesY
                           .add(Game.getProvince(FormableCivManager.activeFormableCiv.Provinces.get(j)).getCity(0).getPosY() / Game.mapBG.iMapScale);
                     }
                  }

                  data.CapitalProvinceID_X = Game.getProvince(FormableCivManager.activeFormableCiv.CapitalProvinceID).getCity(0).getPosX()
                     / Game.mapBG.iMapScale;
                  data.CapitalProvinceID_Y = Game.getProvince(FormableCivManager.activeFormableCiv.CapitalProvinceID).getCity(0).getPosY()
                     / Game.mapBG.iMapScale;
                  if (data.ProvincesX.size() > 0) {
                     exportData.add(data);
                  }
               }
            } catch (Exception var9) {
               CFG.exceptionStack(var9);
            }
         }
      }

      Json json = SaveManager.getJson();
      FileHandle file = Gdx.files.local("P_FORMABLE.json");
      file.writeString(json.toJson(exportData), false);
   }

   public static final void loadExported_Formables() {
      try {
         FileHandle fileList = FileManager.loadFile("P_FORMABLE.json");
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);

         for (JsonValue jValue : tempArrayData) {
            ExportData.FormableCiv_Export tempData = json.readValue(ExportData.FormableCiv_Export.class, jValue);
            FormableCivManager.activeFormableCiv = new FormableCivManager.FormableCiv();
            FormableCivManager.activeFormableCiv.FormableCivTag = tempData.FormableCivTag;

            for (int a = 0; a < tempData.ClaimantsTag.size(); a++) {
               FormableCivManager.activeFormableCiv.ClaimantsTag.add(tempData.ClaimantsTag.get(a));
            }

            int provID = Game.setProvinceID_Point(tempData.CapitalProvinceID_X * Game.mapBG.iMapScale, tempData.CapitalProvinceID_Y * Game.mapBG.iMapScale);
            FormableCivManager.activeFormableCiv.CapitalProvinceID = provID;

            for (int a = 0; a < tempData.ProvincesX.size(); a++) {
               provID = Game.setProvinceID_Point(tempData.ProvincesX.get(a) * Game.mapBG.iMapScale, tempData.ProvincesY.get(a) * Game.mapBG.iMapScale);
               if (provID >= 0 && !Game.getProvince(provID).getSeaProvince()) {
                  FormableCivManager.activeFormableCiv.addProvince(provID);
               }
            }

            if (FormableCivManager.activeFormableCiv.CapitalProvinceID >= 0
               && !Game.getProvince(FormableCivManager.activeFormableCiv.CapitalProvinceID).getSeaProvince()
               && FormableCivManager.activeFormableCiv.Provinces.size() > 0) {
               SaveManager.saveFormableCiv();
            }

            Object var10 = null;
         }

         tempArrayData.clear();
         Object var9 = null;
      } catch (Exception var8) {
         CFG.exceptionStack(var8);
      }
   }

   public static class FormableCiv_Export {
      public String FormableCivTag = null;
      public List<String> ClaimantsTag = new ArrayList<>();
      public List<Integer> ProvincesX = new ArrayList<>();
      public List<Integer> ProvincesY = new ArrayList<>();
      public int CapitalProvinceID_X = 0;
      public int CapitalProvinceID_Y = 0;
   }

   public static class Save_ProvinceData_3M {
      public int iX;
      public int iY;
      public int terrainID;
      public int resourceID;
      public float baseDevelopment;
   }

   public static class Save_ProvinceData_Images {
      public int iX;
      public int iY;
      public int imgID;
   }

   public static class Save_ProvinceData_ScenarioCivs {
      public int iX;
      public int iY;
      public String sTag;
      public int numOfProvinces;
   }
}
