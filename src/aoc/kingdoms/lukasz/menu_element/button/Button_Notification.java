package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Button_Notification extends Button {
   public int ANIMATION_TIME = 350;
   public String sText2;
   public int iTextWidth2 = 0;
   public int imageID;
   public int iconWidth;
   public int iconHeight;
   public int notificationID;
   public long lTime;
   float fAlpha = 1.0F;

   public Button_Notification(String sText, String sText2, int nPosX, int nPosY, int nWidth, int nHeight, int imageID, int notificationID, long lTime) {
      super(sText, CFG.FONT_REGULAR_SMALL, -1, nPosX, nPosY, nWidth, nHeight, true);
      this.imageID = imageID;
      this.notificationID = notificationID;
      this.lTime = lTime;
      this.sText2 = sText2;
      Renderer.glyphLayout.setText(Renderer.fontMain.get(this.fontID), sText2);
      this.iTextWidth2 = (int)Renderer.glyphLayout.width;
      this.updateIcon();
      this.updateTextWidth();
   }

   public void updateTextWidth() {
      int tWMax = 0;

      while (this.iTextWidth > this.getWidth() - this.iTextWidth2 - CFG.PADDING * 7 - this.iconWidth && this.getText().length() > 5) {
         if (++tWMax >= 100) {
            break;
         }

         this.setText(this.getText().substring(0, Math.max(1, this.getText().length() - 3)) + ".");
      }
   }

   public void updateIcon() {
      float iconScale = this.getImageScale(this.imageID) * 1.2F;
      this.iconWidth = (int)(ImageManager.getImage(this.imageID).getWidth() * iconScale);
      this.iconHeight = (int)(ImageManager.getImage(this.imageID).getHeight() * iconScale);
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
      if (this.lTime + this.ANIMATION_TIME >= CFG.currentTimeMillis) {
         this.fAlpha = Math.min(1.0F, (float)(CFG.currentTimeMillis - this.lTime) / this.ANIMATION_TIME);
         iTranslateX = (int)(iTranslateX + (CFG.BUTTON_WIDTH - CFG.BUTTON_WIDTH * this.fAlpha));
      }

      super.draw(oSB, iTranslateX, iTranslateY, isActive, scrollableY);
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      oSB.setColor(
         new Color(
            Colors.COLOR_NOTIFICATION_BG.r,
            Colors.COLOR_NOTIFICATION_BG.g,
            Colors.COLOR_NOTIFICATION_BG.b,
            (!this.getIsHovered() && !isActive ? 0.925F : 0.975F) * this.fAlpha
         )
      );
      Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
      oSB.setColor(Color.WHITE);
      oSB.setColor(new Color(Colors.COLOR_NOTIFICATION_OVER.r, Colors.COLOR_NOTIFICATION_OVER.g, Colors.COLOR_NOTIFICATION_OVER.b, 0.3F * this.fAlpha));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      oSB.setColor(new Color(Colors.COLOR_NOTIFICATION_OVER.r, Colors.COLOR_NOTIFICATION_OVER.g, Colors.COLOR_NOTIFICATION_OVER.b, 0.35F * this.fAlpha));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      oSB.setColor(new Color(Colors.COLOR_NOTIFICATION_OVER.r, Colors.COLOR_NOTIFICATION_OVER.g, Colors.COLOR_NOTIFICATION_OVER.b, 0.3F * this.fAlpha));
      Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.25F * this.fAlpha));
      Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), CFG.PADDING * 2, false, true);
      Images.gradientXY
         .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - CFG.PADDING * 2 + iTranslateY, this.getWidth(), CFG.PADDING * 2);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.25F * this.fAlpha));
      Renderer.drawBox(oSB, Images.statsRectBGBorder, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
      oSB.setColor(new Color(Colors.COLOR_NOTIFICATION_BG.r, Colors.COLOR_NOTIFICATION_BG.g, Colors.COLOR_NOTIFICATION_BG.b, 0.25F));
      Renderer.drawBox(
         oSB, Images.statsRectBGBorder, this.getPosX() - 1 + iTranslateX, this.getPosY() - 1 + iTranslateY, this.getWidth() + 2, this.getHeight() + 2, 1.0F
      );
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F * this.fAlpha));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 1 + iTranslateY, this.getWidth(), 1);
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), 1);
      oSB.setColor(new Color(Colors.COLOR_NOTIFICATION_OVER.r, Colors.COLOR_NOTIFICATION_OVER.g, Colors.COLOR_NOTIFICATION_OVER.b, 0.85F * this.fAlpha));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 2 + iTranslateY, this.getWidth(), 1);
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + 1 + iTranslateY, this.getWidth(), 1);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.55F * this.fAlpha));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 1 + iTranslateY, this.getWidth(), 1);
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), 1);
      oSB.setColor(new Color(Colors.COLOR_NOTIFICATION_OVER.r, Colors.COLOR_NOTIFICATION_OVER.g, Colors.COLOR_NOTIFICATION_OVER.b, 0.9F * this.fAlpha));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 2 + iTranslateY, this.getWidth(), 1);
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + 1 + iTranslateY, this.getWidth(), 1);
      oSB.setColor(Color.WHITE);
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      this.drawIcon(oSB, iTranslateX, iTranslateY, isActive);
      Renderer.drawText(
         oSB,
         this.fontID,
         this.getTextToDraw(),
         this.getPosX() + this.iconWidth + CFG.PADDING * 4 + iTranslateX,
         this.getPosY() + this.getHeight() / 2 - this.getTextHeight() / 2 + iTranslateY,
         this.getColor2(this.getColor(isActive))
      );
      Renderer.drawText(
         oSB,
         this.fontID,
         this.sText2,
         this.getPosX() + this.getWidth() - CFG.PADDING * 2 - this.iTextWidth2 + iTranslateX,
         this.getPosY() + this.getHeight() / 2 - this.getTextHeight() / 2 + iTranslateY,
         this.getColor2(this.getColor(isActive))
      );
   }

   protected void drawIcon(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, this.fAlpha));
      ImageManager.getImage(this.imageID)
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
   protected Color getColor(boolean isActive) {
      return Colors.getColorButton(isActive, this.getIsHovered());
   }

   protected Color getColor2(Color outColor) {
      return new Color(outColor.r, outColor.g, outColor.b, this.fAlpha);
   }

   public float getImageScale(int iImageID) {
      return (float)CFG.TEXT_HEIGHT / ImageManager.getImage(iImageID).getHeight();
   }

   @Override
   public void actionElement() {
      try {
         Game.player.lNotifications.get(this.notificationID).onAction();
         Game.player.removeNotification(this.notificationID);
      } catch (Exception var2) {
      }
   }

   @Override
   public void actionElementPPM() {
      Game.player.removeNotification(this.notificationID);
   }

   @Override
   public void buildElementHover() {
      try {
         this.menuElementHover = Game.player.lNotifications.get(this.notificationID).buildMenuElementHover();
      } catch (Exception var2) {
      }
   }
}
