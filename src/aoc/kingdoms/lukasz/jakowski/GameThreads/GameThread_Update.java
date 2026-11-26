package aoc.kingdoms.lukasz.jakowski.GameThreads;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import java.util.concurrent.ConcurrentLinkedDeque;

public class GameThread_Update extends Thread {
   public boolean running = true;
   private ConcurrentLinkedDeque<Game.SimpleTask> simpleTasks = new ConcurrentLinkedDeque<>();
   public int iLastUpdateTurnID = -1;
   public long timeSleep;

   public final void updateSimpleTask() {
      try {
         for (int i = this.simpleTasks.size() - 1; i >= 0; i--) {
            try {
               this.simpleTasks.remove().update();
            } catch (Exception var3) {
               CFG.exceptionStack(var3);
            }
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   public final void addSimpleTask(Game.SimpleTask nSimpleTask) {
      if (!this.simpleTasks.contains(nSimpleTask)) {
         this.simpleTasks.add(nSimpleTask);
      }
   }

   public final void addSimpleTask_First(Game.SimpleTask nSimpleTask) {
      if (!this.simpleTasks.contains(nSimpleTask)) {
         this.simpleTasks.addFirst(nSimpleTask);
      }
   }

   public void clearData() {
      this.simpleTasks.clear();
      this.iLastUpdateTurnID = 0;
   }

   @Override
   public void run() {
      while (this.running) {
         try {
            try {
               this.timeSleep = System.currentTimeMillis();
               Game.updateGame();
               this.updateSimpleTask();
               if (Game.menuManager.getInGame() && Game_Calendar.TURN_ID != this.iLastUpdateTurnID) {
                  if (Game_Calendar.TURN_ID % GameValues.gameUpdate.GAME_UPDATE_CURRENT_SITUATION_EVERY_X_DAYS == 0) {
                     Game.player.currSituation.updateCurrentSituation();
                  }

                  Game.player.updateNotifications();
                  Game.player.updateMessages();
                  this.iLastUpdateTurnID = Game_Calendar.TURN_ID;
               }
            } catch (Exception var3) {
               CFG.exceptionStack(var3);
            }

            try {
               Thread.sleep(Math.max(1L, 15L - (System.currentTimeMillis() - this.timeSleep)));
            } catch (InterruptedException var2) {
            }
         } catch (Exception var4) {
            CFG.exceptionStack(var4);
         }
      }
   }
}
