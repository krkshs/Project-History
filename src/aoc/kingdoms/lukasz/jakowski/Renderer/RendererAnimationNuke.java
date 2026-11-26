package aoc.kingdoms.lukasz.jakowski.Renderer;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.SoundsManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RendererAnimationNuke {
   public int iProvinceID = 0;
   public int iPosX = 0;
   public int iPosY = 0;
   public long lTime = 0L;
   public int currentIMG = 0;
   public int TURN_ID = 0;
   public boolean remove = false;
   public static long lastTimeNukePlayed = 0L;

   public RendererAnimationNuke(int iProvinceID) {
      this.iProvinceID = iProvinceID;
      if (Game.getProvince(iProvinceID).getCitiesSize() > 0) {
         this.iPosX = Game.getProvince(iProvinceID).getCity(0).getPosX();
         this.iPosY = Game.getProvince(iProvinceID).getCity(0).getPosY();
      } else {
         this.iPosX = Game.getProvince(iProvinceID).iCenterShiftX;
         this.iPosY = Game.getProvince(iProvinceID).iCenterShiftY;
      }

      this.TURN_ID = Game_Calendar.TURN_ID;
   }

   public void draw(SpriteBatch oSB) {
      if (Game_Calendar.TURN_ID - this.TURN_ID > 60) {
         this.remove = true;
      }

      if (Game.getProvince(this.iProvinceID).getDrawProvince() && !this.remove) {
         if (this.lTime == 0L) {
            this.lTime = CFG.currentTimeMillis;
            if (lastTimeNukePlayed + GameValues.atomic.ATOMIC_BOMB_SOUND_EFFECT_LOCK_TIME < CFG.currentTimeMillis) {
               lastTimeNukePlayed = CFG.currentTimeMillis;
               Game.soundsManager.playSound(SoundsManager.SOUND_NUKE, Game.soundsManager.getSoundsVolumeMaster());
            }
         }

         float fProgress = 0.1F + 0.9F * Math.min(1.0F, (float)(CFG.currentTimeMillis - this.lTime) / GameValues.atomic.ATOMIC_BOMB_ANIMATION_TIME);
         int currentW = (int)(ImageManager.getImage(Images.nukeImg.get(0)).getWidth() * Game.mapScale.getCurrentScale());
         int currentH = (int)(ImageManager.getImage(Images.nukeImg.get(0)).getHeight() * Game.mapScale.getCurrentScale());
         int nPosX = (int)((this.iPosX + Game.getProvince(this.iProvinceID).getTranslateProvincePosX()) * Game.mapScale.getCurrentScale());
         int nPosY = (int)((this.iPosY + Game.mapCoords.getPosY()) * Game.mapScale.getCurrentScale());
         float fProgress2 = 1.0F - 0.9F * Math.min(1.0F, (float)this.currentIMG / Images.nukeIMGSize);
         oSB.setColor(new Color(Colors.HOVER_YELLOW.r, Colors.HOVER_YELLOW.g, Colors.HOVER_YELLOW.b, 0.2F * fProgress2));
         Images.gradientXY.draw(oSB, nPosX - currentW, nPosY - currentH, currentW * 2, currentH);
         Images.gradientXY.draw(oSB, nPosX - currentW, nPosY + currentH - currentH, currentW * 2, currentH, false, true);
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 1.0F));
         ImageManager.getImage(Images.nukeImg.get(this.currentIMG)).draw(oSB, nPosX - currentW / 2, nPosY - currentH / 2, currentW, currentH);
         if (fProgress >= 1.0F) {
            this.currentIMG++;
            this.lTime = CFG.currentTimeMillis;
            if (this.currentIMG >= Images.nukeIMGSize) {
               this.currentIMG = 0;
               this.remove = true;
            }
         }

         oSB.setColor(Color.WHITE);
      }
   }
}
