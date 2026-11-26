package aoc.kingdoms.lukasz.map.plague;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.FileManager;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.textures.Image;
import aoc.kingdoms.lukasz.textures.ImageManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;
import java.util.ArrayList;
import java.util.List;

public class PlagueManager {
   public static List<Plague> activePlagues = new ArrayList<>();
   public static List<PlagueManager.Data_Disease> lPlagues = new ArrayList<>();
   public static int iPlaguesSize = 0;
   public static List<Image> plagueImages = new ArrayList<>();
   public static List<Image> plagueImagesBig = new ArrayList<>();

   public static final void runPlagues() {
      try {
         for (int i = activePlagues.size() - 1; i >= 0; i--) {
            try {
               activePlagues.get(i).runDisease();
            } catch (Exception var3) {
               CFG.exceptionStack(var3);
            }
         }

         for (int i = activePlagues.size() - 1; i >= 0; i--) {
            try {
               if (--activePlagues.get(i).iDurationTurnsLeft < 1 && activePlagues.get(i).lProvinces_Active.size() == 0) {
                  for (int k = i + 1; k < activePlagues.size(); k++) {
                     for (int o = 0; o < activePlagues.get(k).lProvinces_Active.size(); o++) {
                        if (Game.getProvince(activePlagues.get(k).lProvinces_Active.get(o)).provincePlague != null
                           && Game.getProvince(activePlagues.get(k).lProvinces_Active.get(o)).provincePlague.id == activePlagues.get(k).getPlagueID_InGame()) {
                           Game.getProvince(activePlagues.get(k).lProvinces_Active.get(o)).provincePlague.id--;
                        }
                     }

                     activePlagues.get(k).setPlagueID_InGame(activePlagues.get(k).getPlagueID_InGame() - 1);
                  }

                  activePlagues.remove(i);
               }
            } catch (Exception var4) {
               CFG.exceptionStack(var4);
            }
         }

         for (int i = activePlagues.size() - 1; i >= 0; i--) {
            activePlagues.get(i).spreadDisease();
         }
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
      }
   }

   public static final void startDisease() {
      int tRandScore = Game.oR.nextInt(GameValues.plagues.DISEASE_OUTBREAK_RANDOM);
      if (tRandScore < GameValues.plagues.DISEASE_OUTBREAK_RANDOM * Game.gameAges.getAge_DiseaseChance(Game_Calendar.CURRENT_AGEID)) {
         List<Integer> tempIDsToSpawn = new ArrayList<>();
         int tScoreTotal = 0;

         for (int i = 0; i < iPlaguesSize; i++) {
            if (Game_Calendar.currentYear >= lPlagues.get(i).BeginningYear && Game_Calendar.currentYear <= lPlagues.get(i).EndYear) {
               tempIDsToSpawn.add(i);
               tScoreTotal = (int)(tScoreTotal + lPlagues.get(i).OUTBREAK_CHANCE * GameValues.plagues.DISEASE_OUTBREAK_MODIFY);
            }
         }

         if (tempIDsToSpawn.size() > 0) {
            int spawnID = 0;
            if (tScoreTotal > 0) {
               int ix = tempIDsToSpawn.size() - 1;

               for (int tCurrentScore = 0; ix >= 0; ix--) {
                  tCurrentScore += (int)(lPlagues.get(tempIDsToSpawn.get(ix)).OUTBREAK_CHANCE * GameValues.plagues.DISEASE_OUTBREAK_MODIFY);
                  tRandScore = Game.oR.nextInt(tScoreTotal);
                  if (tCurrentScore > tRandScore) {
                     spawnID = ix;
                     break;
                  }
               }
            } else {
               spawnID = Game.oR.nextInt(tempIDsToSpawn.size());
            }

            startDisease(tempIDsToSpawn.get(spawnID));
         }
      }
   }

   private static final void startDisease(int nID) {
      try {
         int nOutbreakProvinces = lPlagues.get(nID).OUTBREAK_PROVINCES;
         if (lPlagues.get(nID).OUTBREAK_PROVINCES_EXTRA > 0) {
            nOutbreakProvinces += Game.oR.nextInt(lPlagues.get(nID).OUTBREAK_PROVINCES_EXTRA);
         }

         List<Integer> lPossibleProvinces = new ArrayList<>();

         for (int i = 0; i < Game.getProvincesSize(); i++) {
            if (Game.getProvince(i).getWasteland() < 0
               && !Game.getProvince(i).getSeaProvince()
               && Game.getProvince(i).getCivID() > 0
               && Game.getProvince(i).provincePlague == null
               && Game_Calendar.TURN_ID - Game.getProvinceData10(i).t > GameValues.plagues.PLAGUE_PAUSE_FOR_X_DAYS) {
               lPossibleProvinces.add(i);
            }
         }

         if (lPossibleProvinces.size() > 0) {
            List<Integer> lSpreadPropositions = new ArrayList<>();
            int nToCheck = 8 + (int)(10.0F * Math.min(lPlagues.get(nID).DEATH_RATE_MIN, 1.0F));

            while (lPossibleProvinces.size() > 0 && nToCheck-- > 0) {
               int tRandID = Game.oR.nextInt(lPossibleProvinces.size());
               lSpreadPropositions.add(lPossibleProvinces.get(tRandID));
               lPossibleProvinces.remove(tRandID);
            }

            lPossibleProvinces.clear();
            if (lSpreadPropositions.size() > 0) {
               List<Float> lSpreadPropositions_Score = new ArrayList<>();
               int tMaxPopulation = 0;
               float tMaxEconomy = 0.0F;
               float tMaxInfrastructure = 0.0F;
               float tMaxDevastation = 0.0F;

               for (int ix = lSpreadPropositions.size() - 1; ix >= 0; ix--) {
                  if (Game.getProvince(lSpreadPropositions.get(ix)).getPopulationTotal() > tMaxPopulation) {
                     tMaxPopulation = Game.getProvince(lSpreadPropositions.get(ix)).getPopulationTotal();
                  }

                  if (Game.getProvince(lSpreadPropositions.get(ix)).getEconomyWithBonuses() > tMaxEconomy) {
                     tMaxEconomy = Game.getProvince(lSpreadPropositions.get(ix)).getEconomyWithBonuses();
                  }

                  if (Game.getProvince(lSpreadPropositions.get(ix)).getInfrastructure() > tMaxInfrastructure) {
                     tMaxInfrastructure = Game.getProvince(lSpreadPropositions.get(ix)).getInfrastructure();
                  }

                  if (Game.getProvince(lSpreadPropositions.get(ix)).getDevastation() > tMaxDevastation) {
                     tMaxDevastation = Game.getProvince(lSpreadPropositions.get(ix)).getDevastation();
                  }
               }

               for (int ix = lSpreadPropositions.size() - 1; ix >= 0; ix--) {
                  lSpreadPropositions_Score.add(
                     lPlagues.get(nID).OUTBREAK_SCORE_POPULATION * Game.getProvince(lSpreadPropositions.get(ix)).getPopulationTotal() / tMaxPopulation
                        + lPlagues.get(nID).OUTBREAK_SCORE_ECONOMY * Game.getProvince(lSpreadPropositions.get(ix)).getEconomyWithBonuses() / tMaxEconomy
                        + (
                           lPlagues.get(nID).OUTBREAK_SCORE_INFRASTRUCTURE
                              - lPlagues.get(nID).OUTBREAK_SCORE_INFRASTRUCTURE
                                 * Game.getProvince(lSpreadPropositions.get(ix)).getInfrastructure()
                                 / tMaxInfrastructure
                        )
                        + lPlagues.get(nID).OUTBREAK_SCORE_DEVASTATION * Game.getProvince(lSpreadPropositions.get(ix)).getDevastation() / tMaxDevastation
                  );
               }

               int tBestID = 0;

               for (int ix = lSpreadPropositions_Score.size() - 1; ix > 0; ix--) {
                  if (lSpreadPropositions_Score.get(tBestID) < lSpreadPropositions_Score.get(ix)) {
                     tBestID = ix;
                  }
               }

               int nPlagueID_InGame = activePlagues.size();
               activePlagues.add(
                  new Plague(
                     lSpreadPropositions.get(tBestID),
                     lPlagues.get(nID).Name,
                     lPlagues.get(nID).R / 255.0F,
                     lPlagues.get(nID).G / 255.0F,
                     lPlagues.get(nID).B / 255.0F,
                     nPlagueID_InGame,
                     lPlagues.get(nID).DEATH_RATE_MIN + Game.oR.nextInt((int)(lPlagues.get(nID).DEATH_RATE_EXTRA * 100000.0F + 1.0F)) / 100000.0F,
                     lPlagues.get(nID).DURATION_TURNS_MIN
                        + (lPlagues.get(nID).DURATION_TURNS_EXTRA > 0 ? Game.oR.nextInt(lPlagues.get(nID).DURATION_TURNS_EXTRA) : 0),
                     lPlagues.get(nID).EXPANSION_MODIFIER + Game.oR.nextInt((int)(lPlagues.get(nID).EXPANSION_MODIFIER_EXTRA * 100000.0F + 1.0F)) / 100000.0F,
                     lPlagues.get(nID).ImageID,
                     lPlagues.get(nID).DEVASTATION
                  )
               );
               lSpreadPropositions.clear();
               lSpreadPropositions_Score.clear();
               if (--nOutbreakProvinces > 0) {
                  activePlagues.get(nPlagueID_InGame).spreadDisease(nOutbreakProvinces);
               }
            }
         }
      } catch (Exception var12) {
         CFG.exceptionStack(var12);
      }
   }

   public static final void loadDiseases() {
      try {
         FileHandle fileList = FileManager.loadFile("game/diseases/Diseases.json");
         String fileContent = fileList.readString();
         Json json = new Json();
         json.setElementType(PlagueManager.ConfigDiseaseData.class, "Disease", PlagueManager.Data_Disease.class);
         PlagueManager.ConfigDiseaseData data = json.fromJson(PlagueManager.ConfigDiseaseData.class, fileContent);

         for (Object e : data.Disease) {
            lPlagues.add((PlagueManager.Data_Disease)e);
         }
      } catch (GdxRuntimeException var6) {
         CFG.exceptionStack(var6);
      }

      iPlaguesSize = lPlagues.size();
      loadPlagueImages();
   }

   public static final void loadPlagueImages() {
      FileHandle tempFileT = FileManager.loadFile("game/diseases/images/numOfImages.txt");
      int numOfImages = Integer.parseInt(tempFileT.readString());

      for (int i = 0; i < numOfImages; i++) {
         if (FileManager.loadFile("game/diseases/images/" + CFG.getRescouresPath_Short() + i + ".png").exists()) {
            plagueImages.add(
               new Image(
                  ImageManager.loadTexture("game/diseases/images/" + CFG.getRescouresPath_Short() + i + ".png"), TextureFilter.Linear, TextureWrap.ClampToEdge
               )
            );
            plagueImagesBig.add(
               new Image(
                  ImageManager.loadTexture("game/diseases/images/" + CFG.getRescouresPath_Short() + i + "b.png"), TextureFilter.Linear, TextureWrap.ClampToEdge
               )
            );
         } else {
            plagueImages.add(
               new Image(
                  ImageManager.loadTexture("game/diseases/images/" + CFG.getRescouresPath_Short_H() + i + ".png"),
                  TextureFilter.Linear,
                  TextureWrap.ClampToEdge
               )
            );
            plagueImagesBig.add(
               new Image(
                  ImageManager.loadTexture("game/diseases/images/" + CFG.getRescouresPath_Short_H() + i + "b.png"),
                  TextureFilter.Linear,
                  TextureWrap.ClampToEdge
               )
            );
         }
      }
   }

   public static class ConfigDiseaseData {
      public String Age_of_History;
      public ArrayList Disease;
   }

   public static class Data_Disease {
      public String Name;
      public int BeginningYear;
      public int EndYear;
      public int ImageID;
      public float OUTBREAK_CHANCE;
      public int OUTBREAK_PROVINCES;
      public int OUTBREAK_PROVINCES_EXTRA;
      public int DURATION_TURNS_MIN;
      public int DURATION_TURNS_EXTRA;
      public float EXPANSION_MODIFIER;
      public float EXPANSION_MODIFIER_EXTRA;
      public float OUTBREAK_SCORE_POPULATION;
      public float OUTBREAK_SCORE_ECONOMY;
      public float OUTBREAK_SCORE_INFRASTRUCTURE;
      public float OUTBREAK_SCORE_DEVASTATION;
      public float DEATH_RATE_MIN;
      public float DEATH_RATE_EXTRA;
      public float DEVASTATION;
      public float R;
      public float G;
      public float B;
   }
}
