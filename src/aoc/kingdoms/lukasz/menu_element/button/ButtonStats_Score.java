package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.jakowski.Renderer.SparksAnimation;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ButtonStats_Score extends Button {
   public static SparksAnimation sparksAnimation = new SparksAnimation();
   public float fPerc = 0.5F;
   public String sText2;
   public int iText2Width = 0;
   public int iconWidth;
   public int iconHeight;
   public float fLastValue = -9.781892E7F;

   public ButtonStats_Score(String sText, int iPosX, int iPosY, int nWidth, int nHeight) {
      this.init(sText, CFG.FONT_REGULAR, 0, iPosX, iPosY, nWidth, nHeight, true, true, false, false);
      float iconScale = this.getImageScale(Images.victoryPoints) * 1.25F;
      this.iconWidth = (int)(ImageManager.getImage(Images.victoryPoints).getWidth() * iconScale);
      this.iconHeight = (int)(ImageManager.getImage(Images.victoryPoints).getHeight() * iconScale);
      this.updateScoreText();
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
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
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.15F));
      ImageManager.getImage(Images.gradientXYVertical)
         .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, (int)(this.getWidth() * this.fPerc), this.getHeight(), true, false);
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.15F));
      ImageManager.getImage(Images.gradientXYVertical)
         .draw(
            oSB,
            this.getPosX() + (int)(this.getWidth() * this.fPerc) + iTranslateX,
            this.getPosY() + iTranslateY,
            this.getWidth() - (int)(this.getWidth() * this.fPerc),
            this.getHeight()
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
      oSB.setColor(DiplomacyManager.COLOR_WAR);
      sparksAnimation.draw2(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      oSB.setColor(Color.WHITE);
   }

   public static final float getBoxAlpha(boolean clickable, boolean isHovered, boolean isActive) {
      return clickable ? (isActive ? 0.85F : (isHovered ? 0.7F : 0.5F)) : 0.2F;
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      Renderer.drawTextWithShadow(
         oSB,
         this.fontID,
         this.getTextToDraw(),
         this.getPosX() + this.getWidth() / 2 - (this.getTextWidth() + this.iText2Width + CFG.PADDING + this.iconWidth) / 2 + iTranslateX,
         this.getPosY() + this.getHeight() / 2 - this.iTextHeight / 2 + iTranslateY,
         this.getColor(isActive)
      );
      Renderer.drawTextWithShadow(
         oSB,
         CFG.FONT_BOLD,
         this.sText2,
         this.getPosX() + this.getWidth() / 2 - (this.getTextWidth() + this.iText2Width + CFG.PADDING + this.iconWidth) / 2 + this.getTextWidth() + iTranslateX,
         this.getPosY() + this.getHeight() / 2 - this.iTextHeight / 2 + iTranslateY,
         Colors.HOVER_GOLD
      );
      ImageManager.getImage(Images.victoryPoints)
         .draw(
            oSB,
            this.getPosX()
               + this.getWidth() / 2
               + this.iText2Width
               + CFG.PADDING * 2
               - (this.getTextWidth() + this.iText2Width + CFG.PADDING + this.iconWidth) / 2
               + this.getTextWidth()
               + iTranslateX,
            this.getPosY() + this.getHeight() / 2 - this.iconHeight / 2 + iTranslateY,
            this.iconWidth,
            this.iconHeight
         );
      this.updateScoreText();
   }

   @Override
   protected Color getColor(boolean isActive) {
      return Colors.getColorButtonHover(isActive, this.getIsHovered());
   }

   public final void updateScoreText() {
      if (this.fLastValue != Game.player.peaceTreaty.fScore) {
         this.fLastValue = Game.player.peaceTreaty.fScore;
         this.sText2 = CFG.getPrecision2(this.fLastValue, 10);
         Renderer.glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_BOLD), this.sText2);
         this.iText2Width = (int)Renderer.glyphLayout.width;
      }
   }

   public final float getImageScale(int iImageID) {
      return (float)CFG.TEXT_HEIGHT / ImageManager.getImage(iImageID).getHeight();
   }
}
