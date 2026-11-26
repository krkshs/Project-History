package aoc.kingdoms.lukasz.jakowski.Missions;

import aoc.kingdoms.lukasz.events.EventsManager;
import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.FileManager;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.SoundsManager;
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

public class MissionTree {
   public static final int MISSION_TYPE_ID = 999;
   public static final int MISSION_TYPE_ID_CIV = 1000;
   public static List<Mission> lMissions = new ArrayList<>();
   public static int iMissionsSize = 0;
   public static List<Image> missionImages = new ArrayList<>();
   public static List<String> missionImagesNames = new ArrayList<>();
   public static List<Image> missionImagesCivs = new ArrayList<>();
   public static List<String> missionImagesNamesCivs = new ArrayList<>();
   public static int iMissionWidth;
   public static int iMissionHeight;

   public static final void loadMissions() {
      try {
         FileHandle fileList = FileManager.loadFile("game/missions/Missions.json");
         String fileContent = fileList.readString();
         Json json = new Json();
         json.setElementType(MissionTree.ConfigMissionsData.class, "Mission", Mission.class);
         MissionTree.ConfigMissionsData data = json.fromJson(MissionTree.ConfigMissionsData.class, fileContent);

         for (Object e : data.Mission) {
            try {
               Mission mission = (Mission)e;
               mission.Name = Game.lang.get(mission.Name);
               FileHandle tempFileEvent = FileManager.loadFile("game/missions/missionsEvents/" + mission.MissionEvent);
               mission.event = EventsManager.loadEvent(999, tempFileEvent.readString().split("\\r?\\n"));
               lMissions.add(mission);
            } catch (Exception var8) {
               CFG.exceptionStack(var8);
            }
         }

         data = null;
      } catch (GdxRuntimeException var9) {
         CFG.exceptionStack(var9);
      }

      iMissionsSize = lMissions.size();
      loadMissionsImages();
   }

   public static final void loadMissions_Civs() {
      disposeMissionImagesCivs();
      List<Integer> loadCivsImages = new ArrayList<>();

      try {
         for (int i = 1; i < Game.getCivsSize(); i++) {
            if (FileManager.loadFile(
                     "map/"
                        + Game.map.getFile_ActiveMap_Path()
                        + "scenarios/"
                        + EventsManager.loadScenarioEventsTag
                        + "/"
                        + "missions/"
                        + Game.getCiv(i).getCivTag()
                        + ".json"
                  )
                  .exists()
               || FileManager.loadFile(
                     "map/"
                        + Game.map.getFile_ActiveMap_Path()
                        + "scenarios/"
                        + EventsManager.loadScenarioEventsTag
                        + "/"
                        + "missions/"
                        + Game.getCiv(i).realTag
                        + ".json"
                  )
                  .exists()) {
               try {
                  Game.getCiv(i).lMissions.clear();
                  Game.getCiv(i).iMissionsSize = 0;
                  FileHandle fileList;
                  if (FileManager.loadFile(
                        "map/"
                           + Game.map.getFile_ActiveMap_Path()
                           + "scenarios/"
                           + EventsManager.loadScenarioEventsTag
                           + "/"
                           + "missions/"
                           + Game.getCiv(i).getCivTag()
                           + ".json"
                     )
                     .exists()) {
                     fileList = FileManager.loadFile(
                        "map/"
                           + Game.map.getFile_ActiveMap_Path()
                           + "scenarios/"
                           + EventsManager.loadScenarioEventsTag
                           + "/"
                           + "missions/"
                           + Game.getCiv(i).getCivTag()
                           + ".json"
                     );
                  } else {
                     fileList = FileManager.loadFile(
                        "map/"
                           + Game.map.getFile_ActiveMap_Path()
                           + "scenarios/"
                           + EventsManager.loadScenarioEventsTag
                           + "/"
                           + "missions/"
                           + Game.getCiv(i).realTag
                           + ".json"
                     );
                  }

                  String fileContent = fileList.readString();
                  Json json = new Json();
                  json.setElementType(MissionTree.ConfigMissionsData.class, "Mission", Mission.class);
                  MissionTree.ConfigMissionsData data = json.fromJson(MissionTree.ConfigMissionsData.class, fileContent);

                  for (Object e : data.Mission) {
                     try {
                        Mission mission = (Mission)e;
                        mission.Name = Game.lang.get(mission.Name);
                        FileHandle tempFileEvent;
                        if (FileManager.loadFile(
                              "map/"
                                 + Game.map.getFile_ActiveMap_Path()
                                 + "scenarios/"
                                 + EventsManager.loadScenarioEventsTag
                                 + "/"
                                 + "missions/"
                                 + "missionsEvents/"
                                 + mission.MissionEvent
                           )
                           .exists()) {
                           tempFileEvent = FileManager.loadFile(
                              "map/"
                                 + Game.map.getFile_ActiveMap_Path()
                                 + "scenarios/"
                                 + EventsManager.loadScenarioEventsTag
                                 + "/"
                                 + "missions/"
                                 + "missionsEvents/"
                                 + mission.MissionEvent
                           );
                        } else {
                           tempFileEvent = FileManager.loadFile("game/missions/missionsEvents/" + mission.MissionEvent);
                        }

                        mission.event = EventsManager.loadEvent(999, tempFileEvent.readString().split("\\r?\\n"));
                        Game.getCiv(i).lMissions.add(mission);
                     } catch (Exception var10) {
                        CFG.exceptionStack(var10);
                     }
                  }

                  data = null;
               } catch (GdxRuntimeException var11) {
                  CFG.exceptionStack(var11);
               }

               loadCivsImages.add(i);
               Game.getCiv(i).iMissionsSize = Game.getCiv(i).lMissions.size();
            }
         }
      } catch (Exception var12) {
         CFG.exceptionStack(var12);
      }

      for (int ix = loadCivsImages.size() - 1; ix >= 0; ix--) {
         loadMissionsImagesCiv(loadCivsImages.get(ix));
      }

      loadCivsImages.clear();
   }

   public static final void loadMissionsImages() {
      for (int i = 0; i < iMissionsSize; i++) {
         boolean addImage = true;

         for (int j = missionImagesNames.size() - 1; j >= 0; j--) {
            if (missionImagesNames.get(j).equals(lMissions.get(i).ImageName)) {
               lMissions.get(i).ImageID = j;
               addImage = false;
               break;
            }
         }

         if (addImage) {
            if (FileManager.loadFile("game/missions/missionsImages/" + CFG.getRescouresPath_Short() + lMissions.get(i).ImageName).exists()) {
               missionImages.add(
                  new Image(
                     ImageManager.loadTexture("game/missions/missionsImages/" + CFG.getRescouresPath_Short() + lMissions.get(i).ImageName),
                     TextureFilter.Linear,
                     TextureWrap.ClampToEdge
                  )
               );
            } else {
               missionImages.add(
                  new Image(
                     ImageManager.loadTexture("game/missions/missionsImages/" + CFG.getRescouresPath_Short_H() + lMissions.get(i).ImageName),
                     TextureFilter.Linear,
                     TextureWrap.ClampToEdge
                  )
               );
            }

            missionImagesNames.add(lMissions.get(i).ImageName);
            lMissions.get(i).ImageID = missionImages.size() - 1;
         }
      }

      iMissionWidth = ImageManager.getImage(Images.missionMask).getWidth();
      iMissionHeight = ImageManager.getImage(Images.missionMask).getHeight();
   }

   public static final void loadMissionsImagesCiv(int civID) {
      for (int i = 0; i < Game.getCiv(civID).iMissionsSize; i++) {
         boolean addImage = true;

         for (int j = missionImagesNamesCivs.size() - 1; j >= 0; j--) {
            if (missionImagesNamesCivs.get(j).equals(Game.getCiv(civID).lMissions.get(i).ImageName)) {
               Game.getCiv(civID).lMissions.get(i).ImageID = j;
               addImage = false;
               break;
            }
         }

         if (addImage) {
            if (FileManager.loadFile(
                  "map/"
                     + Game.map.getFile_ActiveMap_Path()
                     + "scenarios/"
                     + EventsManager.loadScenarioEventsTag
                     + "/"
                     + "missions/"
                     + "missionsImages/"
                     + CFG.getRescouresPath_Short()
                     + Game.getCiv(civID).lMissions.get(i).ImageName
               )
               .exists()) {
               missionImagesCivs.add(
                  new Image(
                     ImageManager.loadTexture(
                        "map/"
                           + Game.map.getFile_ActiveMap_Path()
                           + "scenarios/"
                           + EventsManager.loadScenarioEventsTag
                           + "/"
                           + "missions/"
                           + "missionsImages/"
                           + CFG.getRescouresPath_Short()
                           + Game.getCiv(civID).lMissions.get(i).ImageName
                     ),
                     TextureFilter.Linear,
                     TextureWrap.ClampToEdge
                  )
               );
            } else if (FileManager.loadFile(
                  "map/"
                     + Game.map.getFile_ActiveMap_Path()
                     + "scenarios/"
                     + EventsManager.loadScenarioEventsTag
                     + "/"
                     + "missions/"
                     + "missionsImages/"
                     + CFG.getRescouresPath_Short_H()
                     + Game.getCiv(civID).lMissions.get(i).ImageName
               )
               .exists()) {
               missionImagesCivs.add(
                  new Image(
                     ImageManager.loadTexture(
                        "map/"
                           + Game.map.getFile_ActiveMap_Path()
                           + "scenarios/"
                           + EventsManager.loadScenarioEventsTag
                           + "/"
                           + "missions/"
                           + "missionsImages/"
                           + CFG.getRescouresPath_Short_H()
                           + Game.getCiv(civID).lMissions.get(i).ImageName
                     ),
                     TextureFilter.Linear,
                     TextureWrap.ClampToEdge
                  )
               );
            } else if (FileManager.loadFile("game/missions/missionsImages/" + CFG.getRescouresPath_Short() + Game.getCiv(civID).lMissions.get(i).ImageName)
               .exists()) {
               missionImagesCivs.add(
                  new Image(
                     ImageManager.loadTexture("game/missions/missionsImages/" + CFG.getRescouresPath_Short() + Game.getCiv(civID).lMissions.get(i).ImageName),
                     TextureFilter.Linear,
                     TextureWrap.ClampToEdge
                  )
               );
            } else {
               missionImagesCivs.add(
                  new Image(
                     ImageManager.loadTexture("game/missions/missionsImages/" + CFG.getRescouresPath_Short_H() + Game.getCiv(civID).lMissions.get(i).ImageName),
                     TextureFilter.Linear,
                     TextureWrap.ClampToEdge
                  )
               );
            }

            missionImagesNamesCivs.add(Game.getCiv(civID).lMissions.get(i).ImageName);
            Game.getCiv(civID).lMissions.get(i).ImageID = missionImagesCivs.size() - 1;
         }
      }
   }

   public static void disposeMissionImagesCivs() {
      for (int i = missionImagesCivs.size() - 1; i >= 0; i--) {
         missionImagesCivs.get(i).getTexture().dispose();
      }

      missionImagesNamesCivs.clear();
      missionImagesCivs.clear();
   }

   public static final boolean haveUnlockedMission(int civID, int missionID) {
      return Game.getCiv(civID).eventsDataVariables.hasVariable(lMissions.get(missionID).event.id);
   }

   public static final boolean haveUnlockedMission_Civ(int civID, int missionID) {
      return Game.getCiv(civID).eventsDataVariables.hasVariable(Game.getCiv(civID).lMissions.get(missionID).event.id);
   }

   public static final boolean canRunMission_PreviousMissions(int civID, int missionID) {
      return lMissions.get(missionID).RequiredMission >= 0
            && !Game.getCiv(civID).eventsDataVariables.hasVariable(lMissions.get(lMissions.get(missionID).RequiredMission).event.id)
         ? false
         : lMissions.get(missionID).RequiredMission2 < 0
            || Game.getCiv(civID).eventsDataVariables.hasVariable(lMissions.get(lMissions.get(missionID).RequiredMission2).event.id);
   }

   public static final boolean canRunMission_PreviousMissions_Civ(int civID, int missionID) {
      return Game.getCiv(civID).lMissions.get(missionID).RequiredMission >= 0
            && !Game.getCiv(civID)
               .eventsDataVariables
               .hasVariable(Game.getCiv(civID).lMissions.get(Game.getCiv(civID).lMissions.get(missionID).RequiredMission).event.id)
         ? false
         : Game.getCiv(civID).lMissions.get(missionID).RequiredMission2 < 0
            || Game.getCiv(civID)
               .eventsDataVariables
               .hasVariable(Game.getCiv(civID).lMissions.get(Game.getCiv(civID).lMissions.get(missionID).RequiredMission2).event.id);
   }

   public static final boolean canRunMission(int civID, int missionID) {
      if (lMissions.get(missionID).RequiredMission >= 0
         && !Game.getCiv(civID).eventsDataVariables.hasVariable(lMissions.get(lMissions.get(missionID).RequiredMission).event.id)) {
         return false;
      } else if (lMissions.get(missionID).RequiredMission2 >= 0
         && !Game.getCiv(civID).eventsDataVariables.hasVariable(lMissions.get(lMissions.get(missionID).RequiredMission2).event.id)) {
         return false;
      } else {
         return Game.getCiv(civID).eventsDataVariables.hasVariable(lMissions.get(missionID).event.id)
            ? false
            : lMissions.get(missionID).event.runTriggers(civID);
      }
   }

   public static final boolean canRunMission_Civ(int civID, int missionID) {
      if (Game.getCiv(civID).lMissions.get(missionID).RequiredMission >= 0
         && !Game.getCiv(civID)
            .eventsDataVariables
            .hasVariable(Game.getCiv(civID).lMissions.get(Game.getCiv(civID).lMissions.get(missionID).RequiredMission).event.id)) {
         return false;
      } else if (Game.getCiv(civID).lMissions.get(missionID).RequiredMission2 >= 0
         && !Game.getCiv(civID)
            .eventsDataVariables
            .hasVariable(Game.getCiv(civID).lMissions.get(Game.getCiv(civID).lMissions.get(missionID).RequiredMission2).event.id)) {
         return false;
      } else {
         return Game.getCiv(civID).eventsDataVariables.hasVariable(Game.getCiv(civID).lMissions.get(missionID).event.id)
            ? false
            : Game.getCiv(civID).lMissions.get(missionID).event.runTriggers(civID);
      }
   }

   public static final void runMission(int civID, int missionID) {
      Game.player.addActiveEvent(999, missionID, 0);
      Game.addSimpleTask(new Game.SimpleTask("" + lMissions.get(missionID).event.id + 999, missionID) {
         @Override
         public void update() {
            Game.menuManager.rebuildInGame_Right();
            Game.soundsManager.playSound(SoundsManager.EVENT_INFO);
            Game.menuManager.rebuildInGame_Event(MissionTree.lMissions.get(this.id).event, 999, this.id);
            Game.soundsManager.playSound(SoundsManager.EVENT);
         }
      });
   }

   public static final void runMission_Civ(int civID, int missionID) {
      Game.player.addActiveEvent(1000, missionID, 0);
      Game.addSimpleTask(new Game.SimpleTask("" + Game.getCiv(Game.player.iCivID).lMissions.get(missionID).event.id + 1000, missionID) {
         @Override
         public void update() {
            Game.menuManager.rebuildInGame_Right();
            Game.soundsManager.playSound(SoundsManager.EVENT_INFO);
            Game.menuManager.rebuildInGame_Event(Game.getCiv(Game.player.iCivID).lMissions.get(this.id).event, 1000, this.id);
            Game.soundsManager.playSound(SoundsManager.EVENT);
         }
      });
   }

   public static final void takeMissionDecision(int iCivID, int missionID, int optionID) {
      try {
         Game.getCiv(iCivID).eventsDataVariables.addVariable(lMissions.get(missionID).event.id);
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
      }

      try {
         lMissions.get(missionID).event.options.get(optionID).executeOutcome(iCivID);
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   public static final void takeMissionDecision_Civ(int iCivID, int missionID, int optionID) {
      try {
         Game.getCiv(iCivID).eventsDataVariables.addVariable(Game.getCiv(iCivID).lMissions.get(missionID).event.id);
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
      }

      try {
         Game.getCiv(iCivID).lMissions.get(missionID).event.options.get(optionID).executeOutcome(iCivID);
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   public static class ConfigMissionsData {
      public String Age_of_History;
      public ArrayList Mission;
   }
}
