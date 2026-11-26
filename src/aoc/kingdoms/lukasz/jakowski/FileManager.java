package aoc.kingdoms.lukasz.jakowski;

import aoc.kingdoms.lukasz.jakowski.Steam.SteamManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class FileManager {
   public static FileManager.LoadInterface loadInterface;
   public static boolean IS_MAC = false;

   public static FileHandle getSaveType(String sFile) {
      return loadInterface.getSaveType(sFile);
   }

   public static void initLoadInterface() {
      if (CFG.isDesktop()) {
         if (IS_MAC) {
            loadInterface = new FileManager.LoadInterface() {
               @Override
               public FileHandle loadFile(String sFile) {
                  for (int i = 0; i < SteamManager.modsFoldersSize; i++) {
                     if (Gdx.files.external(SteamManager.modsFolders.get(i) + sFile).exists()) {
                        return Gdx.files.external(SteamManager.modsFolders.get(i) + sFile);
                     }

                     if (Gdx.files.internal(SteamManager.modsFolders.get(i) + sFile).exists()) {
                        return Gdx.files.internal(SteamManager.modsFolders.get(i) + sFile);
                     }
                  }

                  for (int i = 0; i < SteamManager.itemsInstalledSize; i++) {
                     if (Gdx.files.absolute(SteamManager.itemsInstalled.get(i).getFolder() + "/" + sFile).exists()) {
                        return Gdx.files.absolute(SteamManager.itemsInstalled.get(i).getFolder() + "/" + sFile);
                     }
                  }

                  return Gdx.files.external(sFile).exists() ? Gdx.files.external(sFile) : Gdx.files.internal(sFile);
               }

               @Override
               public FileHandle getSaveType(String sFile) {
                  return Gdx.files.external(sFile);
               }
            };
         } else {
            loadInterface = new FileManager.LoadInterface() {
               @Override
               public FileHandle loadFile(String sFile) {
                  for (int i = 0; i < SteamManager.modsFoldersSize; i++) {
                     if (Gdx.files.internal(SteamManager.modsFolders.get(i) + sFile).exists()) {
                        return Gdx.files.internal(SteamManager.modsFolders.get(i) + sFile);
                     }
                  }

                  for (int ix = 0; ix < SteamManager.itemsInstalledSize; ix++) {
                     if (Gdx.files.absolute(SteamManager.itemsInstalled.get(ix).getFolder() + "/" + sFile).exists()) {
                        return Gdx.files.absolute(SteamManager.itemsInstalled.get(ix).getFolder() + "/" + sFile);
                     }
                  }

                  return Gdx.files.internal(sFile);
               }

               @Override
               public FileHandle getSaveType(String sFile) {
                  return Gdx.files.local(sFile);
               }
            };
         }
      } else {
         loadInterface = new FileManager.LoadInterface() {
            @Override
            public FileHandle loadFile(String sFile) {
               return Gdx.files.local(sFile).exists() ? Gdx.files.local(sFile) : Gdx.files.internal(sFile);
            }

            @Override
            public FileHandle getSaveType(String sFile) {
               return Gdx.files.local(sFile);
            }
         };
      }
   }

   public static FileHandle loadFile(String sFile) {
      return loadInterface.loadFile(sFile);
   }

   public interface LoadInterface {
      FileHandle loadFile(String var1);

      FileHandle getSaveType(String var1);
   }
}
