package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ButtonStats_WarScore2 extends Button {
   public float fPerc = 0.35F;
   private int iAggressor;
   private int iDefender;

   public ButtonStats_WarScore2(String sText, int iPosX, int iPosY, int nWidth, int nHeight, int iAggressor, int iDefender) {
      this.init(sText, CFG.FONT_REGULAR_SMALL, 0, iPosX, iPosY, nWidth, nHeight, true, true, false, false);
      this.iAggressor = iAggressor;
      this.iDefender = iDefender;
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_BG_BLUE.r, Colors.COLOR_GRADIENT_BG_BLUE.g, Colors.COLOR_GRADIENT_BG_BLUE.b, 0.3F));
      Images.pix.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      oSB.setColor(Game.getCiv(this.iAggressor).getColor(0.45F));
      Images.pix.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, (int)(this.getWidth() * this.fPerc), this.getHeight(), false, true);
      ImageManager.getImage(Images.gradientVertical)
         .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, (int)(this.getWidth() * this.fPerc), CFG.PADDING, false, false);
      ImageManager.getImage(Images.gradientVertical)
         .draw(
            oSB,
            this.getPosX() + iTranslateX,
            this.getPosY() + this.getHeight() - CFG.PADDING + iTranslateY,
            (int)(this.getWidth() * this.fPerc),
            CFG.PADDING,
            false,
            true
         );
      ImageManager.getImage(Images.gradientHorizontal)
         .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, (int)(this.getWidth() * this.fPerc), this.getHeight(), true, true);
      oSB.setColor(Game.getCiv(this.iDefender).getColor(0.45F));
      Images.pix
         .draw(
            oSB,
            this.getPosX() + (int)(this.getWidth() * this.fPerc) + iTranslateX,
            this.getPosY() + iTranslateY,
            this.getWidth() - (int)(this.getWidth() * this.fPerc),
            this.getHeight(),
            false,
            true
         );
      ImageManager.getImage(Images.gradientVertical)
         .draw(
            oSB,
            this.getPosX() + (int)(this.getWidth() * this.fPerc) + iTranslateX,
            this.getPosY() + iTranslateY,
            this.getWidth() - (int)(this.getWidth() * this.fPerc),
            CFG.PADDING,
            false,
            false
         );
      ImageManager.getImage(Images.gradientVertical)
         .draw(
            oSB,
            this.getPosX() + (int)(this.getWidth() * this.fPerc) + iTranslateX,
            this.getPosY() + this.getHeight() - CFG.PADDING + iTranslateY,
            this.getWidth() - (int)(this.getWidth() * this.fPerc),
            CFG.PADDING,
            false,
            true
         );
      ImageManager.getImage(Images.gradientHorizontal)
         .draw(
            oSB,
            this.getPosX() + (int)(this.getWidth() * this.fPerc) + iTranslateX,
            this.getPosY() + iTranslateY,
            this.getWidth() - (int)(this.getWidth() * this.fPerc),
            this.getHeight(),
            false,
            true
         );
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.785F));
      Images.pix.draw(oSB, this.getPosX() + (int)(this.getWidth() * this.fPerc) + iTranslateX, this.getPosY() + iTranslateY, 1, this.getHeight());
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.95F));
      Images.pix.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getWidth() - 1 + iTranslateY, this.getWidth(), 1);
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
         this.getPosX() + this.getWidth() / 2 - this.getTextWidth() / 2 + iTranslateX,
         this.getPosY() + this.getHeight() / 2 - this.iTextHeight / 2 + iTranslateY,
         this.getColor(isActive)
      );
   }

   @Override
   protected Color getColor(boolean isActive) {
      return Colors.getColorButtonHover(isActive, this.getIsHovered());
   }
}
