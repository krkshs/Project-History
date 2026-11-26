package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ButtonReorganize extends Button {
   public String sNumOfUnits;
   public int iNumOfUnitsWidth = 0;
   public int iconWidth;
   public int iconHeight;

   public ButtonReorganize(String sText, String sNumOfUnits, int nX, int nY, int nWidth) {
      this.init(sText, this.fontID, -1, nX, nY, nWidth, 1, true, true, false, false);
      this.sNumOfUnits = sNumOfUnits;
      Renderer.glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_REGULAR_SMALL), sNumOfUnits);
      this.iNumOfUnitsWidth = (int)Renderer.glyphLayout.width;
      float iconScale = this.getImageScale(Game_Calendar.IMG_MANPOWER);
      this.iconWidth = (int)(ImageManager.getImage(Game_Calendar.IMG_MANPOWER).getWidth() * iconScale);
      this.iconHeight = (int)(ImageManager.getImage(Game_Calendar.IMG_MANPOWER).getHeight() * iconScale);
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), false, true);
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), 1);
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 1 + iTranslateY, this.getWidth(), 1);
      oSB.setColor(Color.WHITE);
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      Renderer.drawTextWithShadow(
         oSB,
         this.fontID,
         this.getTextToDraw(),
         this.getPosX() + this.textPosition.getTextPosition() + iTranslateX,
         this.getPosY() + CFG.PADDING * 2 + iTranslateY,
         this.getColor(isActive)
      );
      Renderer.drawTextWithShadow(
         oSB,
         CFG.FONT_REGULAR_SMALL,
         this.sNumOfUnits,
         this.getPosX() + this.getWidth() / 2 - (this.iNumOfUnitsWidth + CFG.PADDING + this.iconWidth) / 2 + CFG.PADDING + this.iconWidth + iTranslateX,
         this.getPosY() + CFG.TEXT_HEIGHT + CFG.TEXT_HEIGHT / 2 - CFG.TEXT_HEIGHT_SMALL / 2 + CFG.PADDING * 4 + iTranslateY,
         Colors.HOVER_RIGHT
      );
      ImageManager.getImage(Game_Calendar.IMG_MANPOWER)
         .draw(
            oSB,
            this.getPosX() + this.getWidth() / 2 - (this.iNumOfUnitsWidth + CFG.PADDING + this.iconWidth) / 2 + iTranslateX,
            this.getPosY() + CFG.TEXT_HEIGHT + CFG.TEXT_HEIGHT / 2 - this.iconHeight / 2 + CFG.PADDING * 4 + iTranslateY,
            this.iconWidth,
            this.iconHeight
         );
   }

   @Override
   public int getHeight() {
      return CFG.TEXT_HEIGHT * 2 + CFG.PADDING * 6;
   }

   @Override
   protected Color getColor(boolean isActive) {
      return Colors.getColorTopStats(isActive, this.getIsHovered());
   }

   private final float getImageScale(int iImageID) {
      return (float)CFG.TEXT_HEIGHT_SMALL / ImageManager.getImage(iImageID).getHeight();
   }
}
