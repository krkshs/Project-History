package aoc.kingdoms.lukasz.map;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.FileManager;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.textures.Image;
import aoc.kingdoms.lukasz.textures.ImageManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;
import java.util.ArrayList;
import java.util.List;

public class BuildingsManager {
   public static final int GROUP_CAPITAL = 3;
   public static List<BuildingsManager.Buildings> buildings = new ArrayList<>();
   public static List<Integer> buildingSize = new ArrayList<>();
   public static int buildingsSize = 0;
   public static int buildingsResourceStartID = 0;
   public static int buildingsResourceSize = 0;
   public static List<Image> buildingImages = new ArrayList<>();

   public static final void loadBuildings() {
      int id = 0;

      try {
         FileHandle fileList = FileManager.loadFile("game/buildings/Buildings.json");
         String fileContent = fileList.readString();
         Json json = new Json();
         json.setElementType(BuildingsManager.ConfigBuildingsData.class, "Buildings", BuildingsManager.Buildings.class);
         BuildingsManager.ConfigBuildingsData data = json.fromJson(BuildingsManager.ConfigBuildingsData.class, fileContent);

         for (Object e : data.Buildings) {
            BuildingsManager.Buildings dataBuilding = (BuildingsManager.Buildings)e;
            if (dataBuilding.CostGold != null) {
               for (int a = dataBuilding.CostGold.length - 1; a >= 0; a--) {
                  dataBuilding.CostGold[a] = (int)(dataBuilding.CostGold[a] * Game.map.getActiveMap_MapData().mapData.BuildingsCost);
               }
            }

            if (dataBuilding.MaintenanceCost != null) {
               for (int a = dataBuilding.MaintenanceCost.length - 1; a >= 0; a--) {
                  dataBuilding.MaintenanceCost[a] = dataBuilding.MaintenanceCost[a] * Game.map.getActiveMap_MapData().mapData.BuildingsMaintenanceCost;
               }
            }

            if (dataBuilding.ConstructionTime != null) {
               for (int a = dataBuilding.ConstructionTime.length - 1; a >= 0; a--) {
                  dataBuilding.ConstructionTime[a] = (int)(dataBuilding.ConstructionTime[a] * Game.map.getActiveMap_MapData().mapData.BuildingsConstructionTime);
               }
            }

            buildings.add(dataBuilding);
            buildingSize.add(buildings.get(id).Name.length);
            id++;
         }

         data = null;
      } catch (GdxRuntimeException var11) {
         CFG.exceptionStack(var11);
      }

      buildingsSize = buildings.size();
      buildingsResourceStartID = buildingsSize;

      try {
         FileHandle fileList = FileManager.loadFile("game/buildings/BuildingsResources.json");
         String fileContent = fileList.readString();
         Json json = new Json();
         json.setElementType(BuildingsManager.ConfigBuildingsData.class, "Buildings", BuildingsManager.Buildings.class);
         BuildingsManager.ConfigBuildingsData data = json.fromJson(BuildingsManager.ConfigBuildingsData.class, fileContent);

         for (Object e : data.Buildings) {
            buildings.add((BuildingsManager.Buildings)e);
            buildingSize.add(buildings.get(id).Name.length);
            id++;
         }

         data = null;
      } catch (GdxRuntimeException var10) {
         CFG.exceptionStack(var10);
      }

      buildingsResourceSize = buildings.size();

      for (int i = 0; i < buildingsResourceSize; i++) {
         if (buildings.get(i).NameDesc == null) {
            buildings.get(i).NameDesc = new String[buildings.get(i).Name.length];

            for (int j = 0; j < buildings.get(i).Name.length; j++) {
               buildings.get(i).NameDesc[j] = buildings.get(i).Name[j] + "Desc";
            }
         }
      }

      try {
         for (int ix = 0; ix < buildingsResourceSize; ix++) {
            for (int j = 0; j < buildingSize.get(ix); j++) {
               buildings.get(ix).Name[j] = Game.lang.get(buildings.get(ix).Name[j]);
            }
         }
      } catch (Exception var9) {
         CFG.exceptionStack(var9);
      }

      loadBuildingsImages();
   }

   public static final void loadBuildingsImages() {
      try {
         FileHandle tempFileT = FileManager.loadFile("game/buildings/buildingsImages/numOfImages.txt");
         int numOfImages = Integer.parseInt(tempFileT.readString());

         for (int i = 0; i < numOfImages; i++) {
            if (FileManager.loadFile("game/buildings/buildingsImages/" + CFG.getRescouresPath_Short() + i + ".png").exists()) {
               buildingImages.add(
                  new Image(
                     ImageManager.loadTexture_RGB888("game/buildings/buildingsImages/" + CFG.getRescouresPath_Short() + i + ".png"),
                     TextureFilter.Linear,
                     TextureWrap.ClampToEdge
                  )
               );
            } else {
               buildingImages.add(
                  new Image(
                     ImageManager.loadTexture_RGB888("game/buildings/buildingsImages/" + CFG.getRescouresPath_Short_H() + i + ".png"),
                     TextureFilter.Linear,
                     TextureWrap.ClampToEdge
                  )
               );
            }
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public static class Buildings {
      public String[] Name;
      public String[] NameDesc;
      public int[] ImageID;
      public int GroupID;
      public float[] CostGold;
      public float[] MaintenanceCost;
      public int[] ConstructionTime;
      public int[] RequiredTechID;
      public int RequiredGovernmentID = -1;
      public int RequiredReligionID = -1;
      public float[] TaxEfficiency;
      public float[] MonthlyLegacy;
      public int[] DefenseBonus;
      public int[] FortLevel;
      public int[] FortDefense;
      public int[] MaximumManpower;
      public float[] LocalManpower;
      public int[] ConstructionCost;
      public int[] ConstructionTimeBonus;
      public float[] RecruitArmyCostInProvince;
      public float[] LocalGrowthRate;
      public float[] InvestInEconomyCost;
      public float[] IncreaseManpowerCost;
      public float[] IncreaseTaxEfficiencyCost;
      public float[] DevelopInfrastructureCost;
      public float[] IncreaseGrowthRateCost;
      public float[] ProvinceMaintenance;
      public float[] MonthlyIncome;
      public float[] LocalTaxEfficiency;
      public float[] DiseaseDeathRate;
      public float[] CasualtiesNuclearAttacks;
      public float[] Economy;
      public float[] ResearchPoints;
      public float[] ProductionEfficiency;
      public float[] IncomeProduction;
      public int[] MaxInfrastructure;
      public int[] BuildingSlots;
      public float[] ArmyMovementSpeed;
      public int RequiredResource = -1;
      public boolean UniqueCapitalBuilding = false;
      public boolean SeaAccessRequired = false;
      public boolean ShowUpgrades = true;
      public int[] AI;
   }

   public static class ConfigBuildingsData {
      public String Age_of_History;
      public ArrayList Buildings;
   }
}
