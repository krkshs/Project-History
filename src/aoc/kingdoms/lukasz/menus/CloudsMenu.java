package aoc.kingdoms.lukasz.menus;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.View;
import aoc.kingdoms.lukasz.menu_element.Empty;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.textures.Image;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class CloudsMenu extends Menu {
   public static View goToView = View.NEW_GAME;
   public int iAnimationTime = 750;
   public long lTime = 0L;
   public List<CloudsMenu.CloudAnimation> lClouds = new ArrayList<>();
   public boolean viewChanged = false;

   public CloudsMenu() {
      List<MenuElement> menuElements = new ArrayList<>();
      menuElements.add(new Empty(0, 0, 1, 1));
      this.lTime = CFG.currentTimeMillis;
      if (Game.cloudsAnimation.imageCloud.size() > 0) {
         for (int i = 0; i < CFG.GAME_WIDTH / 250; i++) {
            int tCloudID = Game.oR.nextInt(Game.cloudsAnimation.imageCloud.size());
            this.lClouds.add(new CloudsMenu.CloudAnimation(tCloudID, Game.oR.nextInt(CFG.GAME_WIDTH), Game.oR.nextInt(CFG.GAME_HEIGHT / 4)));
            this.lClouds.add(new CloudsMenu.CloudAnimation(tCloudID, Game.oR.nextInt(CFG.GAME_WIDTH), CFG.GAME_HEIGHT - Game.oR.nextInt(CFG.GAME_HEIGHT / 4)));
            this.lClouds.add(new CloudsMenu.CloudAnimation(tCloudID, Game.oR.nextInt(CFG.GAME_WIDTH / 4), Game.oR.nextInt(CFG.GAME_HEIGHT)));
            this.lClouds.add(new CloudsMenu.CloudAnimation(tCloudID, CFG.GAME_WIDTH - Game.oR.nextInt(CFG.GAME_WIDTH / 4), Game.oR.nextInt(CFG.GAME_HEIGHT)));
         }

         for (int i = 0; i < CFG.GAME_WIDTH / 600; i++) {
            int tCloudID = Game.oR.nextInt(Game.cloudsAnimation.imageCloud.size());
            this.lClouds.add(new CloudsMenu.CloudAnimation(tCloudID, Game.oR.nextInt(CFG.GAME_WIDTH), Game.oR.nextInt(CFG.GAME_HEIGHT)));
         }
      }

      this.initMenu(null, 0, 0, CFG.GAME_WIDTH, CFG.GAME_HEIGHT, menuElements, true);
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      float fAlpha = Math.min(1.0F, (float)(CFG.currentTimeMillis - this.lTime) / this.iAnimationTime);
      float fPos = fAlpha;
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F * fAlpha));
      ImageManager.getImage(Images.gradientVertical)
         .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), CFG.BUTTON_HEIGHT / 2);
      ImageManager.getImage(Images.gradientVertical)
         .draw(
            oSB,
            this.getPosX() + iTranslateX,
            this.getPosY() + this.getHeight() - CFG.BUTTON_HEIGHT / 2 + iTranslateY,
            this.getWidth(),
            CFG.BUTTON_HEIGHT / 2,
            false,
            true
         );
      oSB.setColor(Color.WHITE);
      fAlpha = 1.0F - Math.min(1.0F, (float)(CFG.currentTimeMillis - this.lTime) / this.iAnimationTime);
      int gradientH = (int)(CFG.GAME_HEIGHT * 0.2F * fAlpha);

      for (int i = this.lClouds.size() - 1; i >= 0; i--) {
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, this.lClouds.get(i).fAlpha * fAlpha));
         if (this.lClouds.get(i).iPosX < CFG.GAME_WIDTH / 2) {
            Image var10000 = Game.cloudsAnimation.imageCloud.get(this.lClouds.get(i).iCloudID);
            float var10003 = CFG.GAME_WIDTH / 10;
            var10000.draw(oSB, this.lClouds.get(i).iPosX - (int)(var10003 * fPos), this.lClouds.get(i).iPosY, 1.0F, this.lClouds.get(i).fRotation);
         } else {
            Image var11 = Game.cloudsAnimation.imageCloud.get(this.lClouds.get(i).iCloudID);
            float var12 = CFG.GAME_WIDTH / 10;
            var11.draw(oSB, this.lClouds.get(i).iPosX + (int)(var12 * fPos), this.lClouds.get(i).iPosY, 1.0F, this.lClouds.get(i).fRotation);
         }
      }

      oSB.setColor(Color.WHITE);
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
      if (CFG.currentTimeMillis - this.lTime > this.iAnimationTime && !this.viewChanged) {
         this.lClouds.clear();
         Game.menuManager.setViewID(goToView);
         this.viewChanged = true;
      }
   }

   public class CloudAnimation {
      int iCloudID;
      int iPosX;
      int iPosY;
      float fAlpha;
      float fRotation;

      public CloudAnimation(int iCloudID, int iPosX, int iPosY) {
         this.iCloudID = iCloudID;
         this.iPosX = iPosX - Game.cloudsAnimation.imageCloud.get(iCloudID).getWidth() / 2;
         this.iPosY = iPosY - Game.cloudsAnimation.imageCloud.get(iCloudID).getHeight() / 2;
         this.fAlpha = 0.1F + Game.oR.nextInt(250) / 1000.0F;
         this.fRotation = Game.oR.nextInt(360);
      }
   }
}
