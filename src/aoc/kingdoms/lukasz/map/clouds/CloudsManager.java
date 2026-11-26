package aoc.kingdoms.lukasz.map.clouds;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.FileManager;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.textures.Image;
import aoc.kingdoms.lukasz.textures.ImageManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Json;
import java.util.ArrayList;
import java.util.List;

public class CloudsManager {
   public List<Image> imageCloud = new ArrayList<>();
   public List<Integer> imageCloudMaxDimension = new ArrayList<>();
   private List<Cloud> lClouds = new ArrayList<>();
   private int iCloudsSize = 0;
   private long lTimeDimOutAlpha = 0L;
   private long lTimeDimInAlpha = 0L;
   private float fDimAlpha = 1.0F;
   public CloudsManager.CloudsSettings cloudsSettings = new CloudsManager.CloudsSettings();
   public static int updateInViewID = 0;
   public CloudsManager.CloudsInterface cloudsInterface = new CloudsManager.CloudsInterface() {
      @Override
      public void drawCloudsInterface(SpriteBatch oSB) {
      }
   };

   public CloudsManager() {
      this.readSettings();
   }

   public boolean loadClouds() {
      return CFG.isDesktop() || GameValues.value.MOBILE_LOAD_CLOUDS;
   }

   public final void loadCloudsImages() {
      if (this.loadClouds()) {
         for (int i = 0; i < this.cloudsSettings.numOfCloudImages; i++) {
            this.imageCloud.add(ImageManager.loadImage("gfx/clouds/" + i + ".png"));
         }

         for (int i = 0; i < this.cloudsSettings.numOfCloudImages; i++) {
            this.imageCloudMaxDimension.add(Math.max(this.imageCloud.get(i).getWidth(), this.imageCloud.get(i).getHeight()));
         }

         for (int i = 0; i < 25; i++) {
            this.addCloud();
         }
      }
   }

   public final void readSettings() {
      Json json = new Json();
      FileHandle file = FileManager.loadFile("gfx/clouds/" + (Game.highTextureSettings ? "Config.json" : "ConfigLow.json"));
      this.cloudsSettings = json.fromJson(CloudsManager.CloudsSettings.class, file);
      this.cloudsSettings.moveSpeedY = Math.max(0.1F, this.cloudsSettings.moveSpeedY);
      this.cloudsSettings.randomAlpha = Math.min(1.0F, this.cloudsSettings.randomAlpha);
      if (!this.loadClouds()) {
         this.cloudsSettings.numOfCloudImages = 0;
      }
   }

   public final void addCloud() {
      this.addCloud(Game.oR.nextInt(Game.mapBG.getWidth()), Game.oR.nextInt(Game.mapBG.getHeight()));
   }

   public final void addCloud(int nPosX, int nPosY) {
      if (this.iCloudsSize < this.cloudsSettings.maxNumOfCloudsInTheGame && this.cloudsSettings.numOfCloudImages > 0) {
         int tempID = Game.oR.nextInt(this.imageCloud.size());
         this.lClouds
            .add(
               new Cloud(
                  tempID,
                  nPosX,
                  nPosY,
                  (10 + Game.oR.nextInt(20) + Game.oR.nextInt(25) + Game.oR.nextInt(25) + Game.oR.nextInt(46)) / 100.0F,
                  Game.oR.nextInt(360)
               )
            );
         this.iCloudsSize = this.lClouds.size();
      }
   }

   public final void updateClouds() {
      for (int i = this.lClouds.size() - 1; i >= 0; i--) {
         if (this.lClouds.get(i).removeCloud()) {
            this.lClouds.remove(i);
            this.iCloudsSize = this.lClouds.size();
         }
      }

      this.randomSpawnCloud();

      for (int ix = 0; ix < this.iCloudsSize; ix++) {
         this.lClouds.get(ix).update();
      }

      updateInViewID = (updateInViewID + 1) % 8;

      for (int ix = updateInViewID; ix < this.iCloudsSize; ix += 8) {
         this.lClouds.get(ix).updateIsInView();
      }
   }

   public final void randomSpawnCloud() {
      if (Game.oR.nextFloat() < this.cloudsSettings.spawnCloudChance) {
         this.addCloud();
      }
   }

   public final void updateCloudsInterface() {
      if (!Game.settingsManager.CLOUDS) {
         this.cloudsInterface = new CloudsManager.CloudsInterface() {
            @Override
            public void drawCloudsInterface(SpriteBatch oSB) {
            }
         };
         this.lClouds.clear();
         this.iCloudsSize = 0;
      } else {
         this.cloudsInterface = new CloudsManager.CloudsInterface() {
            @Override
            public void drawCloudsInterface(SpriteBatch oSB) {
               CloudsManager.this.drawClouds(oSB);
            }
         };
      }
   }

   public final void drawClouds(SpriteBatch oSB) {
      if (Game.mapScale.getCurrentScale() > this.cloudsSettings.drawCloudsMinScale && Game.mapScale.getCurrentScale() < this.cloudsSettings.drawCloudsMaxScale) {
         this.lTimeDimOutAlpha = CFG.currentTimeMillis;
         if (this.fDimAlpha < 1.0F) {
            this.fDimAlpha = Math.max(
               1.0F * ((float)(CFG.currentTimeMillis - this.lTimeDimInAlpha) / Game.cloudsAnimation.cloudsSettings.spawnAnimationTime), 0.0F
            );
            this.fDimAlpha = Math.min(this.fDimAlpha, 1.0F);
         }

         this.drawClouds(oSB, this.fDimAlpha);
      } else {
         this.fDimAlpha = Math.max(
            1.0F - 1.0F * ((float)(CFG.currentTimeMillis - this.lTimeDimOutAlpha) / Game.cloudsAnimation.cloudsSettings.spawnAnimationTime), 0.0F
         );
         this.lTimeDimInAlpha = CFG.currentTimeMillis - (long)(Game.cloudsAnimation.cloudsSettings.spawnAnimationTime * this.fDimAlpha);
         if (this.fDimAlpha > 0.01F) {
            this.drawClouds(oSB, this.fDimAlpha);
         }
      }

      oSB.setColor(Color.WHITE);
   }

   public final void drawClouds(SpriteBatch oSB, float modAlpha) {
      this.updateClouds();

      for (int i = 0; i < this.iCloudsSize; i++) {
         if (this.lClouds.get(i).isInView) {
            oSB.setColor(new Color(0.0F, 0.0F, 0.0F, this.lClouds.get(i).fAlpha * modAlpha));
            this.imageCloud
               .get(this.lClouds.get(i).iCloudImageID)
               .draw(
                  oSB,
                  (int)this.lClouds.get(i).iPosX + this.lClouds.get(i).iPosX_SecondSideOfMap + Game.mapCoords.getPosX() + this.lClouds.get(i).iShadowX,
                  (int)this.lClouds.get(i).iPosY + Game.mapCoords.getPosY() + this.lClouds.get(i).iShadowY,
                  this.lClouds.get(i).fScale * 0.75F,
                  (float)this.lClouds.get(i).iRotate
               );
         }
      }

      if (Game.mapScale.getCurrentScale() < 1.0F) {
         modAlpha *= 1.0F + (1.0F - Game.mapScale.getCurrentScale()) / 2.0F;
      }

      for (int ix = 0; ix < this.iCloudsSize; ix++) {
         if (this.lClouds.get(ix).isInView) {
            oSB.setColor(new Color(1.0F, 1.0F, 1.0F, this.lClouds.get(ix).fAlpha * modAlpha));
            this.imageCloud
               .get(this.lClouds.get(ix).iCloudImageID)
               .draw(
                  oSB,
                  (int)this.lClouds.get(ix).iPosX + this.lClouds.get(ix).iPosX_SecondSideOfMap + Game.mapCoords.getPosX(),
                  (int)this.lClouds.get(ix).iPosY + Game.mapCoords.getPosY(),
                  this.lClouds.get(ix).fScale,
                  (float)this.lClouds.get(ix).iRotate
               );
         }
      }

      oSB.setColor(Color.WHITE);
   }

   public interface CloudsInterface {
      void drawCloudsInterface(SpriteBatch var1);
   }

   public static class CloudsSettings {
      public int numOfCloudImages;
      public int maxNumOfCloudsInTheGame;
      public float spawnCloudChance;
      public int spawnAnimationTime;
      public float moveSpeedX;
      public float moveSpeedY;
      public int shadowX;
      public int shadowY;
      public float minAlpha;
      public float randomAlpha;
      public float drawCloudsMinScale;
      public float drawCloudsMaxScale;
   }
}
