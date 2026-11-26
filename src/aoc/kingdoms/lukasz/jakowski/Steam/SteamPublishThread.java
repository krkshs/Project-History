package aoc.kingdoms.lukasz.jakowski.Steam;

public class SteamPublishThread extends Thread {
   public static String key;

   @Override
   public void run() {
      SteamManager.createItem(key);
   }
}
