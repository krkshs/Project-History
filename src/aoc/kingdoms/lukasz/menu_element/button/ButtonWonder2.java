package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.map.WondersManager;
import aoc.kingdoms.lukasz.map.province.ProvinceDraw;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ButtonWonder2 extends Button {
   public int wonderID;
   public int iProvinceID;

   public ButtonWonder2(int iPosX, int iPosY, int wonderID, int iProvinceID) {
      this.wonderID = wonderID;
      this.iProvinceID = iProvinceID;
      this.init(null, CFG.FONT_REGULAR_SMALL, this.iTextPositionX, iPosX, iPosY, getButtonWidth(), getButtonHeight(), true, true, false, false);
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      WondersManager.wonderImages
         .get(WondersManager.wonders.get(this.wonderID).ImageID)
         .draw(
            oSB,
            this.getPosX() + iTranslateX,
            this.getPosY() + iTranslateY,
            ImageManager.getImage(Images.wonderFrame).getWidth(),
            ImageManager.getImage(Images.wonderFrame).getHeight()
         );
      ImageManager.getImage(Images.wonderFrame).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY);
      oSB.setColor(Color.WHITE);
      if (Game.getProvince(this.iProvinceID).wonderConstruction != null) {
         float fUnderConstruction = (float)Game.getProvince(this.iProvinceID).wonderConstruction.daysLeft
            / Game.getProvince(this.iProvinceID).wonderConstruction.investTime;
         if (fUnderConstruction < 0.0F) {
            fUnderConstruction = 0.0F;
         }

         oSB.setColor(new Color(ProvinceDraw.progressBarBG));
         ImageManager.getImage(Images.progressBarFrameMask)
            .draw(
               oSB,
               this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.progressBarFrameMask).getWidth() / 2 + iTranslateX,
               this.getPosY()
                  - CFG.PADDING
                  + this.getHeight()
                  - ImageManager.getImage(Images.progressBarFrame).getHeight() / 2
                  - ImageManager.getImage(Images.progressBarFrameMask).getHeight() / 2
                  + iTranslateY
            );
         oSB.setColor(new Color(ProvinceDraw.progressBar));
         ImageManager.getImage(Images.progressBarFrameMask)
            .draw(
               oSB,
               this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.progressBarFrameMask).getWidth() / 2 + iTranslateX,
               this.getPosY()
                  - CFG.PADDING
                  + this.getHeight()
                  - ImageManager.getImage(Images.progressBarFrame).getHeight() / 2
                  - ImageManager.getImage(Images.progressBarFrameMask).getHeight() / 2
                  + iTranslateY,
               (int)(ImageManager.getImage(Images.progressBarFrameMask).getWidth() * (1.0F - fUnderConstruction)),
               ImageManager.getImage(Images.progressBarFrameMask).getHeight()
            );
         oSB.setColor(Color.WHITE);
         ImageManager.getImage(Images.progressBarFrame)
            .draw(
               oSB,
               this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.progressBarFrame).getWidth() / 2 + iTranslateX,
               this.getPosY() - CFG.PADDING + this.getHeight() - ImageManager.getImage(Images.progressBarFrame).getHeight() + iTranslateY
            );
      }
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
   }

   public static int getButtonHeight() {
      return ImageManager.getImage(Images.wonderFrame).getHeight();
   }

   public static int getButtonWidth() {
      return ImageManager.getImage(Images.wonderFrame).getWidth();
   }

   @Override
   public void buildElementHover() {
      this.menuElementHover = ButtonWonderProvince.getHoverWonder(this.iProvinceID, this.wonderID, false);
   }
}
