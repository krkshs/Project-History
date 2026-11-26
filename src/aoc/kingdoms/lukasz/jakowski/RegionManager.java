package aoc.kingdoms.lukasz.jakowski;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import java.util.ArrayList;
import java.util.List;

public class RegionManager {
   public List<Region> lRegions = new ArrayList<>();
   public int iRegionsSize = 0;

   public final void loadRegions() {
      this.lRegions.clear();
      this.iRegionsSize = 0;
      if (FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "data/" + "ProvinceOptimizationRegions.txt").exists()) {
         FileHandle file = FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "data/" + "ProvinceOptimizationRegions.txt");
         String text = file.readString();
         String[] sAllRegions = text.split("\n");
         List<Boolean> tempAdded = new ArrayList<>();

         for (int i = 0; i < Game.getProvincesSize(); i++) {
            tempAdded.add(false);
         }

         for (int i = 0; i < sAllRegions.length; i++) {
            String[] sRegion = sAllRegions[i].split(";");
            Region newRegion = new Region();

            for (int j = 0; j < sRegion.length; j++) {
               newRegion.addProvince(Integer.parseInt(sRegion[j]));
            }

            for (int j = 0; j < sRegion.length; j++) {
               tempAdded.set(newRegion.getProvince(j), true);
            }

            this.lRegions.add(newRegion);
            this.lRegions.get(i).buildRegionBounds();
         }

         Region tempRegionOfProvincesWithoutIDs = new Region();

         for (int i = 0; i < Game.getProvincesSize(); i++) {
            if (!tempAdded.get(i)) {
               tempRegionOfProvincesWithoutIDs.addProvince(i);
            }
         }

         if (tempRegionOfProvincesWithoutIDs.getProvincesSize2() > 0) {
            this.lRegions.add(tempRegionOfProvincesWithoutIDs);
            this.lRegions.get(this.lRegions.size() - 1).buildRegionBounds();
         }

         this.iRegionsSize = this.lRegions.size();
      } else {
         Region newRegion = new Region();

         for (int ix = 0; ix < Game.getProvincesSize(); ix++) {
            newRegion.addProvince(ix);
         }

         this.lRegions.add(newRegion);
         this.lRegions.get(0).buildRegionBounds();
         this.iRegionsSize = this.lRegions.size();
      }
   }

   public final int getRegionID(int nProvinceID) {
      for (int i = 0; i < this.iRegionsSize; i++) {
         for (int j = 0; j < this.lRegions.get(i).getProvincesSize(); j++) {
            if (this.lRegions.get(i).getProvince(j) == nProvinceID) {
               return i;
            }
         }
      }

      Gdx.app.log("AoC", "REGION ERROR: " + nProvinceID);
      return 0;
   }

   public final void updateRegionsSize() {
      this.iRegionsSize = this.lRegions.size();
   }

   public final void disposeRegions() {
      this.lRegions.clear();
      this.iRegionsSize = 0;
   }
}
