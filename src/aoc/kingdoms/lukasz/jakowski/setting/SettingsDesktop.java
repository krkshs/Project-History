package aoc.kingdoms.lukasz.jakowski.setting;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.FileManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class SettingsDesktop {
   public static boolean fullscreen = true;
   public static int iWidth = -1;
   public static int iHeight = -1;
   public static boolean vSync = true;

   public static final void readConfig() {
      try {
         if (Gdx.files.local("settings/Config.txt").exists()) {
            FileHandle file = Gdx.files.local("settings/Config.txt");
            String tempTags = file.readString();
            String[] tSplited = tempTags.replace("\n", "").split(";");

            for (int i = 0; i < tSplited.length; i++) {
               String[] tempR = tSplited[i].split("=");

               try {
                  if (tempR[0].equals("FULLSCREEN")) {
                     fullscreen = Boolean.parseBoolean(tempR[1]);
                  } else if (tempR[0].equals("WIDTH")) {
                     iWidth = Integer.parseInt(tempR[1]);
                  } else if (tempR[0].equals("HEIGHT")) {
                     iHeight = Integer.parseInt(tempR[1]);
                  } else if (tempR[0].equals("VSYNC")) {
                     vSync = Boolean.parseBoolean(tempR[1]);
                  }
               } catch (IndexOutOfBoundsException var6) {
                  iWidth = -1;
                  iHeight = -1;
                  fullscreen = true;
                  break;
               } catch (IllegalArgumentException var7) {
                  iWidth = -1;
                  iHeight = -1;
                  fullscreen = true;
                  break;
               }
            }
         }
      } catch (Exception var8) {
         CFG.exceptionStack(var8);
      }
   }

   public static final void saveConfig() {
      FileHandle fileSave = FileManager.getSaveType("settings/Config.txt");
      fileSave.writeString("FULLSCREEN=" + fullscreen + ";\n", false);
      fileSave.writeString("WIDTH=" + iWidth + ";\n", true);
      fileSave.writeString("HEIGHT=" + iHeight + ";\n", true);
      fileSave.writeString("VSYNC=" + vSync + ";", true);
   }
}
