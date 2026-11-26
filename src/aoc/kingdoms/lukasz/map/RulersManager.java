package aoc.kingdoms.lukasz.map;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.FileManager;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.RomanNumber;
import aoc.kingdoms.lukasz.jakowski.Player.Notification.Notification;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_Court;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_CourtOptions;
import aoc.kingdoms.lukasz.menusInGame.Court.InGame_CourtOptions2;
import aoc.kingdoms.lukasz.menusInGame.Info.InGame_Info;
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

public class RulersManager {
   public static Image rulerIMG = null;
   public static Image rulerIMG_DiplomacyLeft = null;
   public static Image rulerIMG_DiplomacyRight = null;
   public static List<List<String>> defaultRandomNames = new ArrayList<>();
   public static List<Integer> NUM_OF_RANDOM_RULERS = new ArrayList<>();
   public static List<Integer> nextRulerIMG = new ArrayList<>();
   public static List<Integer> NUM_OF_RANDOM_RULERS_2 = new ArrayList<>();
   public static List<Integer> nextRulerIMG_2 = new ArrayList<>();
   public static List<String> groups = new ArrayList<>();
   public static int iGroupsSize = 0;

   public static void loadRulerIMG(int iCivID) {
      if (rulerIMG != null) {
         rulerIMG.dispose();
         rulerIMG = null;
      }

      rulerIMG = loadRulerImage(iCivID);
   }

   public static void loadRulerIMG_DiplomacyLeft(int iCivID) {
      if (rulerIMG_DiplomacyLeft != null) {
         rulerIMG_DiplomacyLeft.dispose();
         rulerIMG_DiplomacyLeft = null;
      }

      rulerIMG_DiplomacyLeft = loadRulerImage(iCivID);
   }

   public static void loadRulerIMG_DiplomacyRight(int iCivID) {
      if (rulerIMG_DiplomacyRight != null) {
         rulerIMG_DiplomacyRight.dispose();
         rulerIMG_DiplomacyRight = null;
      }

      rulerIMG_DiplomacyRight = loadRulerImage(iCivID);
   }

   public static Image loadRulerImage(int iCivID) {
      try {
         if (Game.getCiv(iCivID).ruler == null) {
            loadRuler(iCivID, Game.getCiv(iCivID).getCivTag(), false);
         }

         if (Game.getCiv(iCivID).ruler.isRandom) {
            if (Game.getCiv(iCivID).ruler.kingImage) {
               return FileManager.loadFile(
                        "game/rulersRandom/rulersImages/"
                           + CFG.getRescouresPath_Short()
                           + Game.getCiv(iCivID).iGroupID
                           + "/"
                           + Game.getCiv(iCivID).ruler.ImageID
                           + ".png"
                     )
                     .exists()
                  ? new Image(
                     ImageManager.loadTexture_RGB888(
                        "game/rulersRandom/rulersImages/"
                           + CFG.getRescouresPath_Short()
                           + Game.getCiv(iCivID).iGroupID
                           + "/"
                           + Game.getCiv(iCivID).ruler.ImageID
                           + ".png"
                     ),
                     TextureFilter.Linear,
                     TextureWrap.ClampToEdge
                  )
                  : new Image(
                     ImageManager.loadTexture_RGB888(
                        "game/rulersRandom/rulersImages/"
                           + CFG.getRescouresPath_Short_H()
                           + Game.getCiv(iCivID).iGroupID
                           + "/"
                           + Game.getCiv(iCivID).ruler.ImageID
                           + ".png"
                     ),
                     TextureFilter.Linear,
                     TextureWrap.ClampToEdge
                  );
            } else {
               return FileManager.loadFile(
                        "game/rulersRandom/rulersImages2/"
                           + CFG.getRescouresPath_Short()
                           + Game.getCiv(iCivID).iGroupID
                           + "/"
                           + Game.getCiv(iCivID).ruler.ImageID
                           + ".png"
                     )
                     .exists()
                  ? new Image(
                     ImageManager.loadTexture_RGB888(
                        "game/rulersRandom/rulersImages2/"
                           + CFG.getRescouresPath_Short()
                           + Game.getCiv(iCivID).iGroupID
                           + "/"
                           + Game.getCiv(iCivID).ruler.ImageID
                           + ".png"
                     ),
                     TextureFilter.Linear,
                     TextureWrap.ClampToEdge
                  )
                  : new Image(
                     ImageManager.loadTexture_RGB888(
                        "game/rulersRandom/rulersImages2/"
                           + CFG.getRescouresPath_Short_H()
                           + Game.getCiv(iCivID).iGroupID
                           + "/"
                           + Game.getCiv(iCivID).ruler.ImageID
                           + ".png"
                     ),
                     TextureFilter.Linear,
                     TextureWrap.ClampToEdge
                  );
            }
         } else if (FileManager.loadFile("game/rulers/rulersImages/" + CFG.getRescouresPath_Short() + Game.getCiv(iCivID).ruler.ImageID + ".png").exists()) {
            return new Image(
               ImageManager.loadTexture_RGB888("game/rulers/rulersImages/" + CFG.getRescouresPath_Short() + Game.getCiv(iCivID).ruler.ImageID + ".png"),
               TextureFilter.Linear,
               TextureWrap.ClampToEdge
            );
         } else if (FileManager.loadFile("game/rulers/rulersImages/" + CFG.getRescouresPath_Short_H() + Game.getCiv(iCivID).ruler.ImageID + ".png").exists()) {
            return new Image(
               ImageManager.loadTexture_RGB888("game/rulers/rulersImages/" + CFG.getRescouresPath_Short_H() + Game.getCiv(iCivID).ruler.ImageID + ".png"),
               TextureFilter.Linear,
               TextureWrap.ClampToEdge
            );
         } else {
            try {
               if (!Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).ENABLE_NON_KINGS_IMG
                  && !Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).FORCE_NON_KINGS_IMG) {
                  return FileManager.loadFile(
                           "game/rulersRandom/rulersImages/"
                              + CFG.getRescouresPath_Short()
                              + Game.getCiv(iCivID).iGroupID
                              + "/"
                              + Math.abs(Game.getCiv(iCivID).ruler.BornYear) % NUM_OF_RANDOM_RULERS.get(Game.getCiv(iCivID).iGroupID)
                              + ".png"
                        )
                        .exists()
                     ? new Image(
                        ImageManager.loadTexture_RGB888(
                           "game/rulersRandom/rulersImages/"
                              + CFG.getRescouresPath_Short()
                              + Game.getCiv(iCivID).iGroupID
                              + "/"
                              + Math.abs(Game.getCiv(iCivID).ruler.BornYear) % NUM_OF_RANDOM_RULERS.get(Game.getCiv(iCivID).iGroupID)
                              + ".png"
                        ),
                        TextureFilter.Linear,
                        TextureWrap.ClampToEdge
                     )
                     : new Image(
                        ImageManager.loadTexture_RGB888(
                           "game/rulersRandom/rulersImages/"
                              + CFG.getRescouresPath_Short_H()
                              + Game.getCiv(iCivID).iGroupID
                              + "/"
                              + Math.abs(Game.getCiv(iCivID).ruler.BornYear) % NUM_OF_RANDOM_RULERS.get(Game.getCiv(iCivID).iGroupID)
                              + ".png"
                        ),
                        TextureFilter.Linear,
                        TextureWrap.ClampToEdge
                     );
               } else {
                  return FileManager.loadFile(
                           "game/rulersRandom/rulersImages2/"
                              + CFG.getRescouresPath_Short()
                              + Game.getCiv(iCivID).iGroupID
                              + "/"
                              + Math.abs(Game.getCiv(iCivID).ruler.BornYear) % NUM_OF_RANDOM_RULERS_2.get(Game.getCiv(iCivID).iGroupID)
                              + ".png"
                        )
                        .exists()
                     ? new Image(
                        ImageManager.loadTexture_RGB888(
                           "game/rulersRandom/rulersImages2/"
                              + CFG.getRescouresPath_Short()
                              + Game.getCiv(iCivID).iGroupID
                              + "/"
                              + Math.abs(Game.getCiv(iCivID).ruler.BornYear) % NUM_OF_RANDOM_RULERS_2.get(Game.getCiv(iCivID).iGroupID)
                              + ".png"
                        ),
                        TextureFilter.Linear,
                        TextureWrap.ClampToEdge
                     )
                     : new Image(
                        ImageManager.loadTexture_RGB888(
                           "game/rulersRandom/rulersImages2/"
                              + CFG.getRescouresPath_Short_H()
                              + Game.getCiv(iCivID).iGroupID
                              + "/"
                              + Math.abs(Game.getCiv(iCivID).ruler.BornYear) % NUM_OF_RANDOM_RULERS_2.get(Game.getCiv(iCivID).iGroupID)
                              + ".png"
                        ),
                        TextureFilter.Linear,
                        TextureWrap.ClampToEdge
                     );
               }
            } catch (Exception var2) {
               CFG.exceptionStack(var2);
               return new Image(
                  ImageManager.loadTexture_RGB888("game/rulersRandom/rulersImages/" + CFG.getRescouresPath_Short() + "0/0.png"),
                  TextureFilter.Linear,
                  TextureWrap.ClampToEdge
               );
            }
         }
      } catch (Exception var3) {
         return new Image(
            ImageManager.loadTexture_RGB888("game/rulersRandom/rulersImages/" + CFG.getRescouresPath_Short() + "0/0.png"),
            TextureFilter.Linear,
            TextureWrap.ClampToEdge
         );
      }
   }

   public static final void initRandomRulers() {
      int numOfGroups = 0;

      try {
         FileHandle tempFileT = FileManager.loadFile("game/groups/civsGroups.txt");
         String[] tText = tempFileT.readString().split(";");
         numOfGroups = tText.length;

         for (int i = 0; i < numOfGroups; i++) {
            groups.add(tText[i]);
         }

         iGroupsSize = groups.size();
      } catch (GdxRuntimeException var9) {
         CFG.exceptionStack(var9);
      }

      for (int i = 0; i < iGroupsSize; i++) {
         FileHandle tempFileT = FileManager.loadFile("game/rulersRandom/" + groups.get(i) + ".txt");
         String[] tNames = tempFileT.readString().split(";");
         List<String> tLNames = new ArrayList<>();
         int j = 0;

         for (int jSize = tNames.length; j < jSize; j++) {
            tLNames.add(tNames[j]);
         }

         defaultRandomNames.add(tLNames);
      }

      try {
         for (int i = 0; i < iGroupsSize; i++) {
            FileHandle tempFileT2 = FileManager.loadFile("game/rulersRandom/rulersImages/" + CFG.getRescouresPath_Short() + i + "/numOfImages.txt");
            NUM_OF_RANDOM_RULERS.add(Integer.parseInt(tempFileT2.readString()));
         }

         int i = 0;

         for (int iSize = NUM_OF_RANDOM_RULERS.size(); i < iSize; i++) {
            nextRulerIMG.add(Game.oR.nextInt(NUM_OF_RANDOM_RULERS.get(i)));
         }
      } catch (GdxRuntimeException var8) {
         CFG.exceptionStack(var8);
      }

      try {
         for (int i = 0; i < iGroupsSize; i++) {
            FileHandle tempFileT2 = FileManager.loadFile("game/rulersRandom/rulersImages2/" + CFG.getRescouresPath_Short() + i + "/numOfImages.txt");
            NUM_OF_RANDOM_RULERS_2.add(Integer.parseInt(tempFileT2.readString()));
         }

         int i = 0;

         for (int iSize = NUM_OF_RANDOM_RULERS_2.size(); i < iSize; i++) {
            nextRulerIMG_2.add(Game.oR.nextInt(NUM_OF_RANDOM_RULERS_2.get(i)));
         }
      } catch (GdxRuntimeException var7) {
         CFG.exceptionStack(var7);
      }
   }

   public static final void loadRulers(int startID, int endID) {
      for (int i = startID; i < endID; i++) {
         try {
            loadRuler(i, Game.getCiv(i).getCivTag(), false);
         } catch (Exception var4) {
            CFG.exceptionStack(var4);
         }
      }
   }

   public static final boolean characterDies(int iCivID, int iBornYear) {
      int chanceID = Math.max(
         0,
         Math.min(
            10,
            (
                  Game_Calendar.currentYear
                     - iBornYear
                     - Game.getCiv(iCivID).civBonuses.AllCharactersLifeExpectancy
                     - Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).CHARACTERS_EXTRA_LIFE_EXPECTANCY
               )
               / 10
               / Game.gameAges.getAge_TurnDays(Game_Calendar.CURRENT_AGEID)
         )
      );
      return Game.oR.nextInt(10000)
         < GameValues.advisors.CHANCE_OF_DEATH[chanceID]
            + (GameValues.advisors.CHANCE_OF_DEATH[chanceID + 1] - GameValues.advisors.CHANCE_OF_DEATH[chanceID])
               * ((Game_Calendar.currentYear - iBornYear) % 10 / 10.0F);
   }

   public static final void update_ChanceOfDeathOfRuler(int iCivID) {
      try {
         if (characterDies(iCivID, Game.getCiv(iCivID).ruler.BornYear)) {
            deathOfRuler(iCivID);
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }

   public static final void deathOfRuler(int iCivID) {
      if (iCivID == Game.player.iCivID) {
         Game.player
            .addNotification(
               new Notification(
                  Notification.Notification_Type.LEADER_DIED,
                  Game.lang.get("DeathOfALeader") + ": " + Game.getCiv(iCivID).ruler.Name,
                  Images.council,
                  Game_Calendar.TURN_ID,
                  Notification.Notification_BG.RED,
                  Game.player.iCivID
               )
            );
         InGame_Info.iCivID = Game.player.iCivID;
         InGame_Info.iCivID2 = 0;
         Game.menuManager.rebuildInGame_Info(Game.lang.get("DeathOfALeader"), Game.getCiv(iCivID).ruler.Name);
         InGame_Info.imgID = Images.infoCrown;
         Game.addSimpleTask(new Game.SimpleTask("rebuildCourt") {
            @Override
            public void update() {
               if (Game.menuManager.getVisibleInGame_Court() && InGame_CourtOptions.iActiveID == InGame_CourtOptions2.idCourt) {
                  Game.menuManager.rebuildInGame_CourtSavePos();
                  Game.menuManager.setVisibleInGame_Court(true);
                  InGame_Court.lTime = 0L;
               }
            }
         });
      }

      for (int i = 0; i < Game.getCiv(iCivID).inAllianceSize; i++) {
         if (Game.alliancesSpecial.get(Game.getCiv(iCivID).inAlliance.get(i)).iLeaderCivID == iCivID
            && Game.alliancesSpecial.get(Game.getCiv(iCivID).inAlliance.get(i)).typeOfAlliance == 0) {
            Game.alliancesSpecial.get(Game.getCiv(iCivID).inAlliance.get(i)).elections();
         }
      }

      Game.addSimpleTask(new Game.SimpleTask("deathOfRuler" + iCivID, iCivID) {
         @Override
         public void update() {
            Game.getCiv(this.id).ruler.updateCivBonuses(this.id, -1);
            RulersManager.loadRuler(this.id, Game.getCiv(this.id).getCivTag(), true);
         }
      });
   }

   public static final void loadRuler(int iCivID, String sCivTAG, boolean random) {
      try {
         try {
            if (!random) {
               FileHandle fileList = null;
               if (FileManager.loadFile("game/rulers/" + sCivTAG + ".json").exists()) {
                  fileList = FileManager.loadFile("game/rulers/" + sCivTAG + ".json");
               } else if (FileManager.loadFile("game/rulers/" + Game.getCiv(iCivID).realTag + ".json").exists()) {
                  fileList = FileManager.loadFile("game/rulers/" + Game.getCiv(iCivID).realTag + ".json");
               } else if (FileManager.loadFile("game/rulers/link/" + Game.getCiv(iCivID).realTag + ".txt").exists()) {
                  FileHandle tempFileT = FileManager.loadFile("game/rulers/link/" + Game.getCiv(iCivID).realTag + ".txt");
                  String tempFileName = tempFileT.readString();
                  if (FileManager.loadFile("game/rulers/" + tempFileName + ".json").exists()) {
                     fileList = FileManager.loadFile("game/rulers/" + tempFileName + ".json");
                  }
               }

               if (fileList != null) {
                  String fileContent = fileList.readString();
                  Json json = new Json();
                  json.setElementType(RulersManager.ConfigRulersData.class, "Rulers", RulersManager.Rulers.class);
                  RulersManager.ConfigRulersData data = json.fromJson(RulersManager.ConfigRulersData.class, fileContent);
                  List<RulersManager.Rulers> tempRulers = new ArrayList<>();
                  int tRulersSize = 0;

                  for (Object e : data.Rulers) {
                     tempRulers.add((RulersManager.Rulers)e);
                     tRulersSize++;
                  }

                  if (!tempRulers.isEmpty()) {
                     int bestID = 0;
                     if (tempRulers.get(bestID).ReignYear < Game_Calendar.currentYear) {
                        for (int i = tRulersSize - 1; i > 0; i--) {
                           if (tempRulers.get(i).ReignYear <= Game_Calendar.currentYear) {
                              bestID = i;
                              break;
                           }
                        }
                     }

                     if (tempRulers.get(bestID).ReignYear <= Game_Calendar.currentYear
                        && tempRulers.get(bestID).BornYear > Game_Calendar.currentYear - 96
                        && tempRulers.get(bestID).BornYear < Game_Calendar.currentYear) {
                        Game.getCiv(iCivID).ruler = new Ruler(
                           iCivID,
                           tempRulers.get(bestID).Name,
                           "" + tempRulers.get(bestID).ImageID,
                           tempRulers.get(bestID).BornDay,
                           tempRulers.get(bestID).BornMonth,
                           tempRulers.get(bestID).BornYear,
                           tempRulers.get(bestID).ReignYear,
                           false,
                           false
                        );
                        tempRulers.clear();
                        tempRulers = null;
                        return;
                     }

                     tempRulers.clear();
                     tempRulers = null;
                  }
               }
            }
         } catch (Exception var11) {
            CFG.LOG("ERROR, CIV TAG: " + Game.getCiv(iCivID).getCivTag());
            CFG.exceptionStack(var11);
         }

         int nMonth = Game.oR.nextInt(12);
         if ((
               Game.ideologiesManager.getIdeology(Game.getCiv(iCivID).getIdeologyID()).KingsImages
                  || !Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).ENABLE_NON_KINGS_IMG
            )
            && !Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).FORCE_NON_KINGS_IMG) {
            Game.getCiv(iCivID).ruler = new Ruler(
               iCivID,
               getRulerRandomName(iCivID, sCivTAG),
               "" + nextRulerIMG.get(Game.getCiv(iCivID).iGroupID),
               Game.oR.nextInt(Game_Calendar.getNumOfDaysInMonth(nMonth)),
               nMonth,
               Game_Calendar.currentYear - GameValues.court.RULER_YEARS_OLD_MIN - Game.oR.nextInt(GameValues.court.RULER_YEARS_OLD_RANDOM),
               Game_Calendar.currentYear,
               true,
               true
            );
            int nextIMG = nextRulerIMG.get(Game.getCiv(iCivID).iGroupID) + 1;
            if (nextIMG >= NUM_OF_RANDOM_RULERS.get(Game.getCiv(iCivID).iGroupID)) {
               nextIMG = 0;
            }

            nextRulerIMG.set(Game.getCiv(iCivID).iGroupID, nextIMG);
         } else {
            Game.getCiv(iCivID).ruler = new Ruler(
               iCivID,
               getRulerRandomName(iCivID, sCivTAG),
               "" + nextRulerIMG_2.get(Game.getCiv(iCivID).iGroupID),
               Game.oR.nextInt(Game_Calendar.getNumOfDaysInMonth(nMonth)),
               nMonth,
               Game_Calendar.currentYear - GameValues.court.RULER_YEARS_OLD_MIN - Game.oR.nextInt(GameValues.court.RULER_YEARS_OLD_RANDOM),
               Game_Calendar.currentYear,
               true,
               false
            );
            int nextIMG = nextRulerIMG_2.get(Game.getCiv(iCivID).iGroupID) + 1;
            if (nextIMG >= NUM_OF_RANDOM_RULERS_2.get(Game.getCiv(iCivID).iGroupID)) {
               nextIMG = 0;
            }

            nextRulerIMG_2.set(Game.getCiv(iCivID).iGroupID, nextIMG);
         }
      } catch (Exception var12) {
         CFG.LOG("ERROR, CIV TAG: " + Game.getCiv(iCivID).getCivTag());
         CFG.exceptionStack(var12);
      }
   }

   public static String getRulerRandomName(int iCivID, String sCivTAG) {
      sCivTAG = Game.getCiv(iCivID).realTag;
      if (Game.ideologiesManager.getIdeology(Game.getCiv(iCivID).getIdeologyID()).RulerRoman) {
         if (FileManager.loadFile("game/rulersRandom/" + sCivTAG + ".txt").exists()) {
            FileHandle fileList = FileManager.loadFile("game/rulersRandom/" + sCivTAG + ".txt");
            String[] tSplit = fileList.readString().split(";");
            if (tSplit.length > 0) {
               return tSplit[Game.oR.nextInt(tSplit.length)]
                  + " "
                  + RomanNumber.getRoman(1 + Game.oR.nextInt(Math.max(1, GameValues.court.RULER_ROMAN_NUMBER_MAX_RANDOM)));
            }
         } else if (FileManager.loadFile("game/rulersRandom/link/" + sCivTAG + ".txt").exists()) {
            FileHandle fileList = FileManager.loadFile("game/rulersRandom/link/" + sCivTAG + ".txt");
            sCivTAG = fileList.readString();
            if (FileManager.loadFile("game/rulersRandom/" + sCivTAG + ".txt").exists()) {
               FileHandle fileList2 = FileManager.loadFile("game/rulersRandom/" + sCivTAG + ".txt");
               String[] tSplit = fileList2.readString().split(";");
               if (tSplit.length > 0) {
                  return tSplit[Game.oR.nextInt(tSplit.length)]
                     + " "
                     + RomanNumber.getRoman(1 + Game.oR.nextInt(Math.max(1, GameValues.court.RULER_ROMAN_NUMBER_MAX_RANDOM)));
               }
            }
         }

         return Game.generalManager.getGeneralRandomName(iCivID) + " " + RomanNumber.getRoman(1 + Game.oR.nextInt(9));
      } else {
         return Game.generalManager.getGeneralRandomName(iCivID) + " " + Game.generalManager.getGeneralRandomSurname(iCivID);
      }
   }

   public static class ConfigRulersData {
      public String Age_of_History;
      public ArrayList Rulers;
   }

   public static class Rulers {
      public String Name;
      public String ImageID;
      public int BornDay;
      public int BornMonth;
      public int BornYear;
      public int ReignYear;
   }
}
