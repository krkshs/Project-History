package aoc.kingdoms.lukasz.map.technology;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.FileManager;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.map.BuildingsManager;
import aoc.kingdoms.lukasz.map.LawsManager;
import aoc.kingdoms.lukasz.map.army.ArmyManager;
import aoc.kingdoms.lukasz.textures.Image;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;
import java.util.ArrayList;
import java.util.List;

public class TechnologyTree {
   public static List<Image> technologyImages = new ArrayList<>();
   public static List<TechnologyTree.Technology> lTechnology = new ArrayList<>();
   public static int iTechnologySize = 0;
   public static List<List<TechnologyTree.Building>> lTechUnlocksBuildings = new ArrayList<>();
   public static List<List<TechnologyTree.LawTech>> lTechUnlocksLaws = new ArrayList<>();
   public static List<List<TechnologyTree.Unit>> lTechUnlocksUnits = new ArrayList<>();
   public static final int PADDING_X = 100;
   public static final int PADDING_Y = 15;
   public static int iTechnologyWidth;
   public static int iTechnologyHeight;
   public static float MapResearchCost = 1.0F;

   public static void buildTechUnlocks() {
      for (int i = 0; i < iTechnologySize; i++) {
         lTechUnlocksBuildings.add(new ArrayList<>());
      }

      for (int i = 0; i < BuildingsManager.buildingsResourceSize; i++) {
         if (BuildingsManager.buildings.get(i).RequiredTechID != null) {
            int j = 0;

            for (int jSize = BuildingsManager.buildings.get(i).RequiredTechID.length; j < jSize; j++) {
               if (BuildingsManager.buildings.get(i).RequiredTechID[j] >= 0) {
                  lTechUnlocksBuildings.get(BuildingsManager.buildings.get(i).RequiredTechID[j]).add(new TechnologyTree.Building(i, j));
               }
            }
         }
      }
   }

   public static void buildTechUnlocks_Laws() {
      for (int i = 0; i < iTechnologySize; i++) {
         lTechUnlocksLaws.add(new ArrayList<>());
      }

      for (int i = 0; i < LawsManager.iLawsSize; i++) {
         for (int j = 0; j < LawsManager.laws.get(i).RequiredTechID.length; j++) {
            if (LawsManager.laws.get(i).RequiredTechID[j] >= 0) {
               lTechUnlocksLaws.get(LawsManager.laws.get(i).RequiredTechID[j]).add(new TechnologyTree.LawTech(i, j));
            }
         }
      }
   }

   public static void buildTechUnlocks_Units() {
      for (int i = 0; i < iTechnologySize; i++) {
         lTechUnlocksUnits.add(new ArrayList<>());
      }

      for (int i = 0; i < ArmyManager.iUnitsTypesSize; i++) {
         for (int j = 0; j < ArmyManager.lArmySize.get(i); j++) {
            if (ArmyManager.lArmy.get(i).get(j).RequiredTechID >= 0) {
               lTechUnlocksUnits.get(ArmyManager.lArmy.get(i).get(j).RequiredTechID).add(new TechnologyTree.Unit(i, j));
            }
         }
      }
   }

   public static float getResearchCost(int iTechID, int iCivID) {
      return Math.max(1.0F, lTechnology.get(iTechID).getResearchCost() * (1.0F + Game.getCiv(iCivID).civBonuses.TechnologyCost / 100.0F));
   }

   public static int getTechBG(int iTechID, int iCivID) {
      if (Game.getCiv(iCivID).getActiveTechResearch() == iTechID) {
         return Images.techBlue;
      } else if (Game.getCiv(iCivID).getTechResearched(iTechID)) {
         return Images.techResearched;
      } else if (lTechnology.get(iTechID).RequiredTech >= 0 && !Game.getCiv(iCivID).getTechResearched(lTechnology.get(iTechID).RequiredTech)) {
         return Images.techGray;
      } else {
         return lTechnology.get(iTechID).RequiredTech2 >= 0 && !Game.getCiv(iCivID).getTechResearched(lTechnology.get(iTechID).RequiredTech2)
            ? Images.techGray
            : Images.techAvailable;
      }
   }

   public static final void loadTechnology() {
      try {
         FileHandle fileList = FileManager.loadFile("game/technologies/Technologies.json");
         String fileContent = fileList.readString();
         Json json = new Json();
         json.setElementType(TechnologyTree.ConfigTechnologyData.class, "Technology", TechnologyTree.Technology.class);
         TechnologyTree.ConfigTechnologyData data = json.fromJson(TechnologyTree.ConfigTechnologyData.class, fileContent);

         for (Object e : data.Technology) {
            lTechnology.add((TechnologyTree.Technology)e);
         }

         data = null;
      } catch (GdxRuntimeException var6) {
         CFG.exceptionStack(var6);
      }

      iTechnologySize = lTechnology.size();
      loadTechnologyImages();
   }

   public static final void buildTechnologiesNames() {
      for (int i = 0; i < iTechnologySize; i++) {
         if (lTechnology.get(i).MaintainTechnologyName) {
            lTechnology.get(i).Name = Game.lang.get(lTechnology.get(i).Name);
         } else if (lTechUnlocksLaws.get(i).size() > 0) {
            lTechnology.get(i).Name = Game.lang.get(LawsManager.laws.get(lTechUnlocksLaws.get(i).get(0).law).Law[lTechUnlocksLaws.get(i).get(0).lawID]);
         } else if (lTechUnlocksUnits.get(i).size() > 0) {
            lTechnology.get(i).Name = ArmyManager.lArmy.get(lTechUnlocksUnits.get(i).get(0).unitID).get(lTechUnlocksUnits.get(i).get(0).armyID).Name;
         } else if (lTechUnlocksBuildings.get(i).size() > 0) {
            lTechnology.get(i).Name = BuildingsManager.buildings.get(lTechUnlocksBuildings.get(i).get(0).building).Name[lTechUnlocksBuildings.get(i).get(0).buildingID];
         } else {
            lTechnology.get(i).Name = Game.lang.get(lTechnology.get(i).Name);
         }
      }
   }

   public static final void loadTechnologyImages() {
      FileHandle tempFileT = FileManager.loadFile("game/technologies/technologiesImages/numOfImages.txt");
      int numOfImages = Integer.parseInt(tempFileT.readString());

      for (int i = 0; i < numOfImages; i++) {
         technologyImages.add(
            new Image(
               ImageManager.loadTexture("game/technologies/technologiesImages/" + CFG.getRescouresPath_Short() + i + ".png"),
               TextureFilter.Linear,
               TextureWrap.ClampToEdge
            )
         );
      }

      iTechnologyWidth = ImageManager.getImage(Images.techBlue).getWidth();
      iTechnologyHeight = ImageManager.getImage(Images.techBlue).getHeight();
   }

   public static final float getMaxResearch(int civID) {
      return GameValues.research.MAX_RESEARCH_BASE
         + (Game.getCiv(civID).getCapitalProvinceID() >= 0 ? Game.getProvince(Game.getCiv(civID).getCapitalProvinceID()).getGrowthRateWithBonuses() : 1.0F)
            * GameValues.research.MAX_RESEARCH_PER_GROWTH_RATE_IN_CAPITAL
         + Game.getCiv(civID).getCapitalLevel() * GameValues.capital.CAPITAL_MAX_RESEARCH_PER_LVL;
   }

   public static class Building {
      public int building;
      public int buildingID;

      public Building(int building, int buildingID) {
         this.building = building;
         this.buildingID = buildingID;
      }
   }

   public static class ConfigTechnologyData {
      public String Age_of_History;
      public ArrayList Technology;
   }

   public static class LawTech {
      public int law;
      public int lawID;

      public LawTech(int law, int lawID) {
         this.law = law;
         this.lawID = lawID;
      }
   }

   public static class Technology {
      public int ID;
      public String Name;
      public int ImageID;
      public boolean MaintainTechnologyName = false;
      public int TreeColumn;
      public int TreeRow;
      public int RequiredTech;
      public int RequiredTech2;
      public int ResearchCost;
      public boolean UnlocksNukes = false;
      public boolean UnlocksAccessToTheSea = false;
      public boolean UnlocksColonization = false;
      public int BattleWidth = 0;
      public int UnitsAttack = 0;
      public int UnitsDefense = 0;
      public int GeneralAttack = 0;
      public int GeneralDefense = 0;
      public int MaxMorale = 0;
      public int Discipline = 0;
      public int Legacy = 0;
      public int Gold = 0;
      public int MaximumLevelOfCapitalCity = 0;
      public int MaximumLevelOfTheMilitaryAcademy = 0;
      public int MaximumLevelOfTheMilitaryAcademyForGenerals = 0;
      public boolean Repeatable;
      public int AI;

      public int getResearchCost() {
         return (int)(this.ResearchCost * TechnologyTree.MapResearchCost);
      }
   }

   public static class Unit {
      public int unitID;
      public int armyID;

      public Unit(int unitID, int armyID) {
         this.unitID = unitID;
         this.armyID = armyID;
      }
   }
}
