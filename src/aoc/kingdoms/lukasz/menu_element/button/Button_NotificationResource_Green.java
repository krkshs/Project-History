package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.map.ResourcesManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Button_NotificationResource_Green extends Button_Notification_Green {
   public Button_NotificationResource_Green(
      String sText, String sText2, int nPosX, int nPosY, int nWidth, int nHeight, int imageID, int notificationID, long lTime
   ) {
      super(sText, sText2, nPosX, nPosY, nWidth, nHeight, imageID, notificationID, lTime);
   }

   @Override
   protected void drawIcon(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, this.fAlpha));
      ResourcesManager.resourceImages
         .get(this.imageID)
         .draw(
            oSB,
            this.getPosX() + CFG.PADDING * 2 + iTranslateX,
            this.getPosY() + this.getHeight() / 2 - this.iconHeight / 2 + iTranslateY,
            this.iconWidth,
            this.iconHeight
         );
      oSB.setColor(Color.WHITE);
   }

   @Override
   public void updateIcon() {
      float iconScale = this.getImageScale(this.imageID) * 1.2F;
      this.iconWidth = (int)(ResourcesManager.resourceImages.get(this.imageID).getWidth() * iconScale);
      this.iconHeight = (int)(ResourcesManager.resourceImages.get(this.imageID).getHeight() * iconScale);
   }

   @Override
   public float getImageScale(int iImageID) {
      return (float)CFG.TEXT_HEIGHT / ResourcesManager.resourceImages.get(iImageID).getHeight();
   }
}
