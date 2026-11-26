package aoc.kingdoms.lukasz.utilities;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FPS {
   public long lTimeFPS;
   public int iFPS_Counter = 0;
   public int iNumOfFPS = 60;
   public static final int MIN_NUM_OF_FPS = 22;
   public static boolean drawFPS = false;

   public void countFPS() {
      this.iFPS_Counter++;
      if (CFG.currentTimeMillis > this.lTimeFPS + 1000L) {
         this.lTimeFPS = CFG.currentTimeMillis;
         this.iNumOfFPS = this.iFPS_Counter;
         this.iFPS_Counter = 0;
      }
   }

   public final void drawFPS(SpriteBatch oSB) {
      if (drawFPS || Game.menuManager.getInSettingsMenu()) {
         Renderer.drawText(oSB, "" + this.iNumOfFPS, CFG.PADDING, CFG.PADDING, new Color(1.0F, 1.0F, 1.0F, 0.3F));
      }
   }
}
