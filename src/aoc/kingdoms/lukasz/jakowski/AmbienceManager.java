package aoc.kingdoms.lukasz.jakowski;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import java.util.ArrayList;
import java.util.List;

public class AmbienceManager {
   public List<Music> lSounds;
   public long VOLUME_TIME = 2000L;
   public long VOLUME_TIME_DOWN = 1500L;
   public static List<AmbienceManager.Ambience> ambiences = new ArrayList<>();
   public static int lastPosX;
   public static int lastPosY;
   public static int lastProvinceID;
   public static int activeNew = -1;
   public static int activeOld = -1;

   public AmbienceManager() {
      this.lSounds = new ArrayList<>();
   }

   public boolean loadAmbience() {
      return CFG.isDesktop() || GameValues.value.MOBILE_LOAD_AMBIENCE;
   }

   public final void loadSounds() {
      if (this.loadAmbience()) {
         FileHandle tempFileT = FileManager.loadFile("audio/ambience/AoH.txt");
         String tempT = tempFileT.readString();
         String[] tList = tempT.split(";");

         for (int i = 0; i < tList.length; i++) {
            ambiences.add(new AmbienceManager.Ambience(this.addSoundSFX(tList[i] + "." + getFileExtension())));
         }
      }
   }

   public final void update() {
      if (this.loadAmbience()) {
         try {
            if (Game.mapScale.getCurrentScale() > GameValues.inGame.AMBIENCE_SCALE
               && (lastPosX != Game.mapCoords.getPosX() || lastPosY != Game.mapCoords.getPosY())) {
               lastPosX = Game.mapCoords.getPosX();
               lastPosY = Game.mapCoords.getPosY();
               if (lastProvinceID < 0
                  || !Game.setProvinceID_IsMouseOverAProvinceID(
                     (int)(-Game.mapCoords.getPosX() + CFG.GAME_WIDTH / 2 / Game.mapScale.getCurrentScale()),
                     (int)(-Game.mapCoords.getPosY() + CFG.GAME_HEIGHT / 2 / Game.mapScale.getCurrentScale()),
                     lastProvinceID
                  )) {
                  int nProvinceID = Game.setProvinceID_Point(
                     (int)(-Game.mapCoords.getPosX() + CFG.GAME_WIDTH / 2 / Game.mapScale.getCurrentScale()),
                     (int)(-Game.mapCoords.getPosY() + CFG.GAME_HEIGHT / 2 / Game.mapScale.getCurrentScale())
                  );
                  if (nProvinceID != lastProvinceID && nProvinceID >= 0) {
                     if (activeNew != Game.terrainManager.terrains.get(Game.getProvince(nProvinceID).getTerrainID()).Ambience) {
                        if (activeOld < 0 && activeNew >= 0) {
                           activeOld = activeNew;
                        } else if (activeNew >= 0) {
                           ambiences.get(activeNew).isPlaying = false;
                           ambiences.get(activeNew).isPlaying = false;
                           ambiences.get(activeNew).updateVolumeUP = false;
                           this.lSounds.get(ambiences.get(activeNew).id).pause();
                        }

                        activeNew = Game.terrainManager.terrains.get(Game.getProvince(nProvinceID).getTerrainID()).Ambience;
                     }

                     lastProvinceID = nProvinceID;
                     if (activeNew == activeOld) {
                        activeOld = -1;
                     }
                  }
               }
            }
         } catch (Exception var4) {
            CFG.exceptionStack(var4);
         }

         try {
            if (activeOld >= 0 && ambiences.get(activeOld).isPlaying) {
               if (ambiences.get(activeOld).updateTime) {
                  ambiences.get(activeOld).updateTime = false;
                  ambiences.get(activeOld).updateVolumeUP = true;
                  ambiences.get(activeOld).lTime = CFG.currentTimeMillis;
               }

               this.lSounds
                  .get(ambiences.get(activeOld).id)
                  .setVolume(
                     SoundsManager.ambienceVolume
                        * SoundsManager.masterVolume
                        * Math.max(0.0F, 1.0F - (float)(CFG.currentTimeMillis - ambiences.get(activeOld).lTime) / (float)this.VOLUME_TIME_DOWN)
                  );
               if (CFG.currentTimeMillis - ambiences.get(activeOld).lTime > this.VOLUME_TIME) {
                  ambiences.get(activeOld).isPlaying = false;
                  ambiences.get(activeOld).isPlaying = false;
                  ambiences.get(activeOld).updateVolumeUP = false;
                  this.lSounds.get(ambiences.get(activeOld).id).pause();
                  activeOld = -1;
               }
            }
         } catch (Exception var3) {
            CFG.exceptionStack(var3);
         }

         try {
            if (activeNew >= 0) {
               if (Game.mapScale.getCurrentScale() > GameValues.inGame.AMBIENCE_SCALE) {
                  if (!ambiences.get(activeNew).startedPlaying) {
                     ambiences.get(activeNew).startedPlaying = true;
                     ambiences.get(activeNew).updateVolumeUP = true;
                     this.lSounds.get(ambiences.get(activeNew).id).setLooping(true);
                     this.lSounds.get(ambiences.get(activeNew).id).setVolume(0.0F);
                     this.lSounds.get(ambiences.get(activeNew).id).play();
                     ambiences.get(activeNew).lTime = CFG.currentTimeMillis;
                  } else if (!ambiences.get(activeNew).isPlaying) {
                     ambiences.get(activeNew).isPlaying = true;
                     ambiences.get(activeNew).updateVolumeUP = true;
                     this.lSounds.get(ambiences.get(activeNew).id).play();
                     this.lSounds.get(ambiences.get(activeNew).id).setVolume(0.0F);
                     ambiences.get(activeNew).lTime = CFG.currentTimeMillis;
                  } else if (ambiences.get(activeNew).updateVolumeUP) {
                     if (CFG.currentTimeMillis - ambiences.get(activeNew).lTime > this.VOLUME_TIME) {
                        ambiences.get(activeNew).updateVolumeUP = false;
                     }

                     this.lSounds
                        .get(ambiences.get(activeNew).id)
                        .setVolume(
                           SoundsManager.ambienceVolume
                              * SoundsManager.masterVolume
                              * Math.min(1.0F, (float)(CFG.currentTimeMillis - ambiences.get(activeNew).lTime) / (float)this.VOLUME_TIME)
                        );
                     ambiences.get(activeNew).updateTime = true;
                  }
               } else if (ambiences.get(activeNew).isPlaying) {
                  if (ambiences.get(activeNew).updateTime) {
                     ambiences.get(activeNew).updateTime = false;
                     ambiences.get(activeNew).updateVolumeUP = true;
                     ambiences.get(activeNew).lTime = CFG.currentTimeMillis;
                  }

                  this.lSounds
                     .get(ambiences.get(activeNew).id)
                     .setVolume(
                        SoundsManager.ambienceVolume
                           * SoundsManager.masterVolume
                           * Math.max(0.0F, 1.0F - (float)(CFG.currentTimeMillis - ambiences.get(activeNew).lTime) / (float)this.VOLUME_TIME_DOWN)
                     );
                  if (CFG.currentTimeMillis - ambiences.get(activeNew).lTime > this.VOLUME_TIME) {
                     ambiences.get(activeNew).isPlaying = false;
                     ambiences.get(activeNew).isPlaying = false;
                     ambiences.get(activeNew).updateVolumeUP = false;
                     this.lSounds.get(ambiences.get(activeNew).id).pause();
                     activeNew = -1;
                  }
               }
            }
         } catch (Exception var2) {
            CFG.exceptionStack(var2);
         }
      }
   }

   public final int addSoundSFX(String fileName) {
      try {
         this.lSounds.add(Gdx.audio.newMusic(FileManager.loadFile("audio/ambience/" + fileName)));
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }

      return this.lSounds.size() - 1;
   }

   public static final String getFileExtension() {
      return CFG.isiOS ? "mp3" : "ogg";
   }

   public class Ambience {
      public int id = 0;
      public long idSoundPlaying = 0L;
      public boolean startedPlaying = false;
      public boolean isPlaying = false;
      public boolean updateVolumeUP = true;
      public boolean updateTime = false;
      public long lTime = 0L;

      public Ambience(int id) {
         this.id = id;
      }

      public final void update() {
      }
   }
}
