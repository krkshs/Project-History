package aoc.kingdoms.lukasz.menu_element.textStatic;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.button.Button;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TextIcon_Siege extends Button {
   public int imageID;
   public int maxWidth;
   public long lTimeAnimation = 0L;
   public int animationState = 0;
   public final int ANIMATION_T = 1500;
   public float lastValue = -989566.75F;
   public String sText2;
   public int iTextWidth2;
   public String sText3 = "";
   public int iTextWidth3;
   public int iTextHeight3;
   private int iSiegeWidth;
   private int iSiegeHeight;
   private int iFortWidth;
   private int iFortHeight;
   public int iProvinceID;

   public TextIcon_Siege(String sText2, int iProvinceID, int imageID, int nPosX, int nPosY, int nWidth, int nHeight, int maxWidth) {
      this.init(null, CFG.FONT_REGULAR, 0, nPosX, nPosY, nWidth, nHeight, true, true, false, false);
      this.iProvinceID = iProvinceID;
      this.imageID = imageID;
      this.maxWidth = maxWidth;
      this.lTimeAnimation = CFG.currentTimeMillis;
      this.sText2 = sText2;
      Renderer.glyphLayout.setText(Renderer.fontMain.get(this.fontID), sText2);
      this.iTextWidth2 = (int)Renderer.glyphLayout.width;
      this.iSiegeWidth = (int)(ImageManager.getImage(Images.siege).getWidth() * this.getImageScale(Images.siege));
      this.iSiegeHeight = (int)(ImageManager.getImage(Images.siege).getHeight() * this.getImageScale(Images.siege));
      this.iFortWidth = (int)(ImageManager.getImage(Game_Calendar.IMG_FORT_DEFENSE).getWidth() * this.getImageScale(Game_Calendar.IMG_FORT_DEFENSE));
      this.iFortHeight = (int)(ImageManager.getImage(Game_Calendar.IMG_FORT_DEFENSE).getHeight() * this.getImageScale(Game_Calendar.IMG_FORT_DEFENSE));
      this.getTextToDraw();
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      int tX = this.getPosX();
      int tWidth = this.getWidth();
      if (DiplomacyManager.inAnimation) {
         tWidth = (int)(this.getWidth() * DiplomacyManager.fAnimationPerc);
         tX = tX + this.getWidth() / 2 - tWidth / 2;
      }

      oSB.setColor(new Color(Colors.COLOR_GRADIENT_BG_BLUE.r, Colors.COLOR_GRADIENT_BG_BLUE.g, Colors.COLOR_GRADIENT_BG_BLUE.b, 0.25F));
      Renderer.drawBox(oSB, Images.statsRectBG, tX + iTranslateX, this.getPosY() + iTranslateY, tWidth, this.getHeight(), 1.0F);
      oSB.setColor(Color.WHITE);
      oSB.setColor(
         new Color(
            Colors.COLOR_GRADIENT_BG_BLUE.r,
            Colors.COLOR_GRADIENT_BG_BLUE.g,
            Colors.COLOR_GRADIENT_BG_BLUE.b,
            !this.getIsHovered() && !isActive ? 0.25F : 0.65F
         )
      );
      Renderer.drawBox(oSB, Images.statsRectBG, tX + iTranslateX, this.getPosY() + iTranslateY, tWidth, this.getHeight(), 1.0F);
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.2F));
      Images.gradientFull.draw(oSB, tX + iTranslateX, this.getPosY() + iTranslateY, tWidth, this.getHeight());
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.25F));
      Images.gradientXY.draw(oSB, tX + iTranslateX, this.getPosY() + iTranslateY, tWidth, this.getHeight());
      int progressWidth = (int)Math.max(1.0F, tWidth * Game.getProvince(this.iProvinceID).getSiegeProgress());
      oSB.setColor(Color.WHITE);
      if (DiplomacyManager.inAnimation) {
         oSB.setColor(new Color(this.getColorBar().r, this.getColorBar().g, this.getColorBar().b, 0.65F * (1.0F - DiplomacyManager.fAnimationPerc)));
         Images.gradientFull.draw(oSB, tX + iTranslateX, this.getPosY() + iTranslateY, progressWidth, this.getHeight());
         oSB.setColor(new Color(this.getColorBar().r, this.getColorBar().g, this.getColorBar().b, 0.45F + 0.6F * (1.0F - DiplomacyManager.fAnimationPerc)));
      } else {
         oSB.setColor(new Color(this.getColorBar().r, this.getColorBar().g, this.getColorBar().b, 0.45F));
      }

      Images.gradientXY.draw(oSB, tX + iTranslateX, this.getPosY() + iTranslateY, progressWidth, this.getHeight());
      if (this.animationState >= 0) {
         if (this.animationState == 0) {
            float drawPerc = Math.min(1.0F * (float)(CFG.currentTimeMillis - this.lTimeAnimation) / 1500.0F, 1.0F);
            oSB.setColor(new Color(this.getColorBar().r, this.getColorBar().g, this.getColorBar().b, 0.55F * (1.0F - drawPerc)));
            Images.gradientXY.draw(oSB, tX + iTranslateX, this.getPosY() + this.getHeight() / 2 + iTranslateY, progressWidth, this.getHeight() / 2);
            if (this.lTimeAnimation < CFG.currentTimeMillis - 1500L) {
               this.animationState++;
               this.lTimeAnimation = CFG.currentTimeMillis;
            }
         } else {
            float drawPerc = Math.min(1.0F * (float)(CFG.currentTimeMillis - this.lTimeAnimation) / 1500.0F, 1.0F);
            oSB.setColor(new Color(this.getColorBar().r, this.getColorBar().g, this.getColorBar().b, 0.55F * drawPerc));
            Images.gradientXY.draw(oSB, tX + iTranslateX, this.getPosY() + this.getHeight() / 2 + iTranslateY, progressWidth, this.getHeight() / 2);
            if (this.lTimeAnimation < CFG.currentTimeMillis - 1500L) {
               this.animationState = 0;
               this.lTimeAnimation = CFG.currentTimeMillis;
            }
         }

         oSB.setColor(Color.WHITE);
      }

      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.25F));
      Images.gradientXY.draw(oSB, tX + iTranslateX, this.getPosY() + iTranslateY, tWidth, CFG.PADDING * 2, false, true);
      Images.gradientXY.draw(oSB, tX + iTranslateX, this.getPosY() + this.getHeight() - CFG.PADDING * 2 + iTranslateY, tWidth, CFG.PADDING * 2);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.25F));
      Renderer.drawBox(oSB, Images.statsRectBGBorder, tX + iTranslateX, this.getPosY() + iTranslateY, tWidth, this.getHeight(), 1.0F);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F));
      Images.gradientFull.draw(oSB, tX + iTranslateX, this.getPosY() + this.getHeight() - 1 + iTranslateY, tWidth, 1);
      Images.gradientFull.draw(oSB, tX + iTranslateX, this.getPosY() + iTranslateY, tWidth, 1);
      oSB.setColor(Color.WHITE);
   }

   public Color getColorBar() {
      return DiplomacyManager.COLOR_RED;
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      ImageManager.getImage(this.imageID)
         .draw(
            oSB,
            this.getPosX() + this.getWidth() / 2 - this.iTextWidth3 / 2 - CFG.PADDING - this.iSiegeWidth + iTranslateX,
            this.getPosY() + this.getHeight() / 2 - (this.iTextHeight + this.iTextHeight3 + CFG.PADDING) / 2 + CFG.PADDING + this.iTextHeight + iTranslateY,
            this.iSiegeWidth,
            this.iSiegeHeight
         );
      ImageManager.getImage(Game_Calendar.IMG_FORT_DEFENSE)
         .draw(
            oSB,
            this.getPosX() + this.getWidth() / 2 + this.iTextWidth3 / 2 + CFG.PADDING + iTranslateX,
            this.getPosY() + this.getHeight() / 2 - (this.iTextHeight + this.iTextHeight3 + CFG.PADDING) / 2 + CFG.PADDING + this.iTextHeight + iTranslateY,
            this.iFortWidth,
            this.iFortHeight
         );
      Renderer.drawText(
         oSB,
         this.fontID,
         this.sText2,
         this.getPosX() + this.getWidth() / 2 - (this.iTextWidth + this.iTextWidth2) / 2 + iTranslateX,
         this.getPosY() + this.getHeight() / 2 - (this.iTextHeight + this.iTextHeight3 + CFG.PADDING) / 2 + iTranslateY,
         this.getColor(isActive)
      );
      Renderer.drawText(
         oSB,
         this.fontID,
         this.getTextToDraw(),
         this.getPosX() + this.getWidth() / 2 - (this.iTextWidth + this.iTextWidth2) / 2 + this.iTextWidth2 + iTranslateX,
         this.getPosY() + this.getHeight() / 2 - (this.iTextHeight + this.iTextHeight3 + CFG.PADDING) / 2 + iTranslateY,
         Colors.HOVER_GOLD
      );
      Renderer.drawText(
         oSB,
         CFG.FONT_REGULAR_SMALL,
         this.sText3,
         this.getPosX() + this.getWidth() / 2 - this.iTextWidth3 / 2 + iTranslateX,
         this.getPosY() + this.getHeight() / 2 - (this.iTextHeight + this.iTextHeight3 + CFG.PADDING) / 2 + CFG.PADDING + this.iTextHeight + iTranslateY,
         this.getColor(isActive)
      );
   }

   @Override
   public String getTextToDraw() {
      if (this.iProvinceID >= 0 && Game.getProvinceData4(this.iProvinceID).getSiegeProgress() != this.lastValue) {
         this.lastValue = Game.getProvinceData4(this.iProvinceID).getSiegeProgress();
         this.setText("" + CFG.getPrecision2(Game.getProvince(this.iProvinceID).getSiegeProgress() * 100.0F, 1) + "%");
         this.sText3 = ""
            + CFG.getPrecision2(Game.getProvinceData4(this.iProvinceID).getSiegeProgress(), 1)
            + " / "
            + Game.getProvince(this.iProvinceID).getFortDefense();
         Renderer.glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_REGULAR_SMALL), this.sText3);
         this.iTextWidth3 = (int)Renderer.glyphLayout.width;
         this.iTextHeight3 = (int)Renderer.glyphLayout.height;
      }

      return this.sText;
   }

   private final float getImageScale(int iImageID) {
      return (float)CFG.TEXT_HEIGHT_SMALL / ImageManager.getImage(iImageID).getHeight();
   }
}
