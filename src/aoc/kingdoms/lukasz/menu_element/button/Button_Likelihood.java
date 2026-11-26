package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Button_Likelihood extends Button {
   public float fPerc;
   public long lTime;
   public static final int ANIMATION_TIME = 150;
   public String sText2;
   public int iText2Width = 0;
   public int imageID;
   public int iconWidth;
   public int iconHeight;

   public Button_Likelihood(String sText, String sText2, int iPosX, int iPosY, int nWidth, int nHeight, float perc, int imageID) {
      this.init(sText, CFG.FONT_REGULAR_SMALL, 0, iPosX, iPosY, nWidth, nHeight, true, true, false, false);
      this.fPerc = perc;
      this.sText2 = sText2;
      Renderer.glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_REGULAR_SMALL), sText2);
      this.iText2Width = (int)Renderer.glyphLayout.width;
      this.imageID = imageID;
      float iconScale = this.getImageScale(imageID) * 1.1F;
      this.iconWidth = (int)(ImageManager.getImage(imageID).getWidth() * iconScale);
      this.iconHeight = (int)(ImageManager.getImage(imageID).getHeight() * iconScale);
      this.lTime = CFG.currentTimeMillis;
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      float drawPerc = this.fPerc;
      if (this.lTime + 150L > System.currentTimeMillis()) {
         drawPerc = this.fPerc * (float)(System.currentTimeMillis() - this.lTime) / 150.0F;
      }

      oSB.setColor(new Color(Colors.COLOR_GRADIENT_BG_BLUE.r, Colors.COLOR_GRADIENT_BG_BLUE.g, Colors.COLOR_GRADIENT_BG_BLUE.b, 0.5F));
      Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
      oSB.setColor(Color.WHITE);
      oSB.setColor(
         new Color(
            Colors.COLOR_GRADIENT_BG_BLUE.r,
            Colors.COLOR_GRADIENT_BG_BLUE.g,
            Colors.COLOR_GRADIENT_BG_BLUE.b,
            !this.getIsHovered() && !isActive ? 0.35F : 0.65F
         )
      );
      Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
      oSB.setColor(Color.WHITE);
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.3F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.35F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.3F));
      Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.25F));
      Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), CFG.PADDING * 2, false, true);
      Images.gradientXY
         .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - CFG.PADDING * 2 + iTranslateY, this.getWidth(), CFG.PADDING * 2);
      oSB.setColor(new Color(Colors.HOVER_POSITIVE.r, Colors.HOVER_POSITIVE.g, Colors.HOVER_POSITIVE.b, 0.375F));
      ImageManager.getImage(Images.gradientXYVertical)
         .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, (int)(this.getWidth() * drawPerc), this.getHeight(), true, false);
      oSB.setColor(new Color(Colors.HOVER_NEGATIVE.r, Colors.HOVER_NEGATIVE.g, Colors.HOVER_NEGATIVE.b, 0.375F));
      ImageManager.getImage(Images.gradientXYVertical)
         .draw(
            oSB,
            this.getPosX() + (int)(this.getWidth() * drawPerc) + iTranslateX,
            this.getPosY() + iTranslateY,
            this.getWidth() - (int)(this.getWidth() * drawPerc),
            this.getHeight()
         );
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_BG_BLUE.r, Colors.COLOR_GRADIENT_BG_BLUE.g, Colors.COLOR_GRADIENT_BG_BLUE.b, 1.0F));
      Images.pix.draw(oSB, this.getPosX() + (int)(this.getWidth() * drawPerc) + iTranslateX, this.getPosY() + iTranslateY, 1, this.getHeight());
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.75F));
      ImageManager.getImage(Images.gradientXYVertical)
         .draw(oSB, this.getPosX() + (int)(this.getWidth() * drawPerc) + iTranslateX, this.getPosY() + iTranslateY, 1, this.getHeight() / 2);
      ImageManager.getImage(Images.gradientXYVertical)
         .draw(
            oSB,
            this.getPosX() + (int)(this.getWidth() * drawPerc) + iTranslateX,
            this.getPosY() + this.getHeight() / 2 + iTranslateY,
            1,
            this.getHeight() / 2,
            false,
            true
         );
      oSB.setColor(new Color(Colors.HOVER_GOLD.r, Colors.HOVER_GOLD.g, Colors.HOVER_GOLD.b, 0.5F));
      ImageManager.getImage(Images.gradientVertical)
         .draw(oSB, this.getPosX() + (int)(this.getWidth() / 2.0F) + iTranslateX, this.getPosY() + iTranslateY, 1, this.getHeight() / 2, false, true);
      ImageManager.getImage(Images.gradientVertical)
         .draw(oSB, this.getPosX() + (int)(this.getWidth() / 2.0F) + iTranslateX, this.getPosY() + this.getHeight() / 2 + iTranslateY, 1, this.getHeight() / 2);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F));
      ImageManager.getImage(Images.gradientVertical)
         .draw(oSB, this.getPosX() + (int)(this.getWidth() / 2.0F) - 1 + iTranslateX, this.getPosY() + iTranslateY, 1, this.getHeight() / 2, false, true);
      ImageManager.getImage(Images.gradientVertical)
         .draw(
            oSB, this.getPosX() + (int)(this.getWidth() / 2.0F) - 1 + iTranslateX, this.getPosY() + this.getHeight() / 2 + iTranslateY, 1, this.getHeight() / 2
         );
      ImageManager.getImage(Images.gradientVertical)
         .draw(oSB, this.getPosX() + (int)(this.getWidth() / 2.0F) + 1 + iTranslateX, this.getPosY() + iTranslateY, 1, this.getHeight() / 2, false, true);
      ImageManager.getImage(Images.gradientVertical)
         .draw(
            oSB, this.getPosX() + (int)(this.getWidth() / 2.0F) + 1 + iTranslateX, this.getPosY() + this.getHeight() / 2 + iTranslateY, 1, this.getHeight() / 2
         );
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.25F));
      Renderer.drawBox(oSB, Images.statsRectBGBorder, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 1 + iTranslateY, this.getWidth(), 1);
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), 1);
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.85F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 2 + iTranslateY, this.getWidth(), 1);
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + 1 + iTranslateY, this.getWidth(), 1);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.55F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 1 + iTranslateY, this.getWidth(), 1);
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), 1);
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.9F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 2 + iTranslateY, this.getWidth(), 1);
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + 1 + iTranslateY, this.getWidth(), 1);
      oSB.setColor(Color.WHITE);
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      Renderer.drawTextWithShadow(
         oSB,
         this.fontID,
         this.getTextToDraw(),
         this.getPosX() + this.getWidth() - CFG.PADDING * 2 - this.getTextWidth() - this.iText2Width - this.iconWidth - CFG.PADDING * 2 + iTranslateX,
         this.getPosY() + this.getHeight() / 2 - this.iTextHeight / 2 + iTranslateY,
         this.getColor(isActive)
      );
      Renderer.drawTextWithShadow(
         oSB,
         this.fontID,
         this.sText2,
         this.getPosX() + this.getWidth() - CFG.PADDING * 2 - this.iText2Width - this.iconWidth - CFG.PADDING * 2 + iTranslateX,
         this.getPosY() + this.getHeight() / 2 - this.iTextHeight / 2 + iTranslateY,
         Colors.HOVER_GOLD
      );
      ImageManager.getImage(this.imageID)
         .draw(
            oSB,
            this.getPosX() + this.getWidth() - CFG.PADDING * 2 - this.iconWidth + iTranslateX,
            this.getPosY() + (this.getHeight() - this.iconHeight) / 2 + iTranslateY,
            this.iconWidth,
            this.iconHeight
         );
   }

   private final float getImageScale(int iImageID) {
      return (float)CFG.TEXT_HEIGHT / ImageManager.getImage(iImageID).getHeight();
   }
}
