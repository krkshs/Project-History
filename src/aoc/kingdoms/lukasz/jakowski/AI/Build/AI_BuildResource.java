package aoc.kingdoms.lukasz.jakowski.AI.Build;

import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.map.ResourcesManager;

public class AI_BuildResource {
   public static final boolean buildBuilding(int civID, int limitOfBuildings) {
      int i = 0;

      for (int iSize = Math.min(Game.aiValuesBuild.BUILD_RESOURCE_PROVINCES, Game.getCiv(civID).getNumOfProvinces()); i < iSize; i++) {
         int randProvince = Game.getCiv(civID).getProvinceID(Game.oR.nextInt(Game.getCiv(civID).getNumOfProvinces()));
         if (Game.getProvince(randProvince).getResourceID() >= 0
            && ResourcesManager.lResources.get(Game.getProvince(randProvince).getResourceID()).uniqueBuildingID >= 0
            && !Game.getProvince(randProvince)
               .buildingBuilt(ResourcesManager.lResources.get(Game.getProvince(randProvince).getResourceID()).uniqueBuildingID, 0)
            && Game.getCiv(civID).isBuildingResearched(ResourcesManager.lResources.get(Game.getProvince(randProvince).getResourceID()).uniqueBuildingID, 0)
            && Game.getProvince(randProvince)
               .addBuildingConstruction(ResourcesManager.lResources.get(Game.getProvince(randProvince).getResourceID()).uniqueBuildingID, 0)
            && Game.getCiv(civID).fGold < AI_Build.averageCostOfBuilding) {
            return true;
         }
      }

      return false;
   }
}
