package aoc.kingdoms.lukasz.menu_element.textStatic;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Text_StaticGoods_ProgressBar extends Text_StaticGoods {
   public float fPerc = 0.0F;
   public static Color progressBarBG = new Color(0.09803922F, 0.09803922F, 0.15686275F, 1.0F);
   public Color progressBar = new Color(0.29411766F, 0.49019608F, 0.64705884F, 1.0F);

   public Text_StaticGoods_ProgressBar(String sText, int fontID, int iTextPositionX, int iPosX, int iPosY, int iWidth, int iHeight, float fPerc, int iCurrent) {
      super(sText, fontID, iTextPositionX, iPosX, iPosY, iWidth, iHeight, iCurrent);
      this.fPerc = fPerc;
      this.iCurrent = iCurrent;
      if (fPerc < 0.25F) {
         this.progressBar = new Color(0.8039216F, 0.19607843F, 0.003921569F, 1.0F);
      } else if (fPerc < 0.55F) {
         this.progressBar = new Color(1.0F, 0.52156866F, 0.20784314F, 1.0F);
      } else if (fPerc < 0.75F) {
         this.progressBar = new Color(1.0F, 0.78431374F, 0.003921569F, 1.0F);
      } else if (fPerc < 0.98F) {
         this.progressBar = new Color(0.09803922F, 0.78431374F, 0.039215688F, 1.0F);
      } else {
         this.progressBar = new Color(0.09019608F, 0.5882353F, 0.023529412F, 1.0F);
      }
   }

   public Text_StaticGoods_ProgressBar(
      String sText, int fontID, int iTextPositionX, int iPosX, int iPosY, int iWidth, int iHeight, float fPerc, int iCurrent, boolean worldShare
   ) {
      super(sText, fontID, iTextPositionX, iPosX, iPosY, iWidth, iHeight, iCurrent);
      this.fPerc = fPerc;
      this.iCurrent = iCurrent;
      if (fPerc < 0.05F) {
         this.progressBar = new Color(0.8039216F, 0.19607843F, 0.003921569F, 1.0F);
      } else if (fPerc < 0.125F) {
         this.progressBar = new Color(1.0F, 0.52156866F, 0.20784314F, 1.0F);
      } else if (fPerc < 0.2F) {
         this.progressBar = new Color(1.0F, 0.78431374F, 0.003921569F, 1.0F);
      } else if (fPerc < 0.3F) {
         this.progressBar = new Color(0.09803922F, 0.78431374F, 0.039215688F, 1.0F);
      } else {
         this.progressBar = new Color(0.09019608F, 0.5882353F, 0.023529412F, 1.0F);
      }
   }

   @Override
   public void drawBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      super.drawBG(oSB, iTranslateX, iTranslateY, isActive);
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
      oSB.setColor(progressBarBG);
      ImageManager.getImage(Images.progressBar)
         .draw(
            oSB,
            this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.progressBar).getWidth() / 2 + iTranslateX,
            this.getPosY()
               + this.getHeight() / 2
               - (this.iTextHeight + CFG.PADDING + ImageManager.getImage(Images.progressBarOver).getHeight()) / 2
               + this.iTextHeight
               + CFG.PADDING
               + (ImageManager.getImage(Images.progressBarOver).getHeight() - ImageManager.getImage(Images.progressBar).getHeight()) / 2
               + iTranslateY
         );
      oSB.setColor(this.progressBar);
      ImageManager.getImage(Images.progressBar)
         .draw2(
            oSB,
            this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.progressBar).getWidth() / 2 + iTranslateX,
            this.getPosY()
               + this.getHeight() / 2
               - (this.iTextHeight + CFG.PADDING + ImageManager.getImage(Images.progressBarOver).getHeight()) / 2
               + this.iTextHeight
               + CFG.PADDING
               + (ImageManager.getImage(Images.progressBarOver).getHeight() - ImageManager.getImage(Images.progressBar).getHeight()) / 2
               + iTranslateY,
            (int)(ImageManager.getImage(Images.progressBar).getWidth() * this.fPerc),
            ImageManager.getImage(Images.progressBar).getHeight()
         );
      oSB.setColor(Color.WHITE);
      ImageManager.getImage(Images.progressBarOver)
         .draw(
            oSB,
            this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.progressBarOver).getWidth() / 2 + iTranslateX,
            this.getPosY()
               + this.getHeight() / 2
               - (this.iTextHeight + CFG.PADDING + ImageManager.getImage(Images.progressBarOver).getHeight()) / 2
               + this.iTextHeight
               + CFG.PADDING
               + iTranslateY
         );
      Renderer.drawTextWithShadow(
         oSB,
         this.fontID,
         this.sText,
         this.getPosX() + this.textPosition.getTextPosition() + iTranslateX,
         this.getPosY() + this.getHeight() / 2 - (this.iTextHeight + CFG.PADDING + ImageManager.getImage(Images.progressBarOver).getHeight()) / 2 + iTranslateY,
         this.getIsHovered() ? this.getColor(isActive) : this.progressBar
      );
   }
}
