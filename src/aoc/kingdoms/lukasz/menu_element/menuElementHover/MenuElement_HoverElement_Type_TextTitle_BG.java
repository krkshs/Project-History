package aoc.kingdoms.lukasz.menu_element.menuElementHover;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuElement_HoverElement_Type_TextTitle_BG implements MenuElement_HoverElement_Type {
   private String sText;
   private int iTextWidth;
   private int iTextHeight;
   private Color oColor;
   private int fontID = 0;
   protected static long lTimeAnimation = 0L;
   protected static int animationState = 0;
   protected static final int ANIMATION_T = 2000;

   public MenuElement_HoverElement_Type_TextTitle_BG(String sText) {
      this.init(sText, 0, Colors.HOVER_LEFT);
   }

   public MenuElement_HoverElement_Type_TextTitle_BG(String sText, int fontID) {
      this.init(sText, fontID, Colors.HOVER_LEFT);
   }

   public MenuElement_HoverElement_Type_TextTitle_BG(String sText, Color nColor) {
      this.init(sText, 0, nColor);
   }

   public MenuElement_HoverElement_Type_TextTitle_BG(String sText, int fontID, Color nColor) {
      this.init(sText, fontID, nColor);
   }

   public final void init(String sText, int nFontID, Color oColor) {
      this.sText = sText;
      this.oColor = oColor;
      this.fontID = nFontID;
      Renderer.glyphLayout.setText(Renderer.fontMain.get(this.fontID), sText);
      this.iTextWidth = (int)Renderer.glyphLayout.width;
      this.iTextHeight = (int)Renderer.glyphLayout.height;
   }

   @Override
   public void draw(SpriteBatch oSB, int nPosX, int nPosY, float nAlpha, int iMaxWidth) {
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_BG_BLUE.r, Colors.COLOR_GRADIENT_BG_BLUE.g, Colors.COLOR_GRADIENT_BG_BLUE.b, 0.9F * nAlpha));
      Images.pix
         .draw(
            oSB, nPosX - MenuElement_Hover.getDrawExtraXPos(), nPosY, iMaxWidth + MenuElement_Hover.getDrawExtraXPos() * 2, this.getHeight2() + CFG.PADDING * 2
         );
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.75F * nAlpha));
      Images.gradientXY
         .draw(
            oSB, nPosX - MenuElement_Hover.getDrawExtraXPos(), nPosY, iMaxWidth + MenuElement_Hover.getDrawExtraXPos() * 2, this.getHeight2() + CFG.PADDING * 2
         );
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 1.0F * nAlpha));
      Images.gradientXY
         .draw(
            oSB, nPosX - MenuElement_Hover.getDrawExtraXPos(), nPosY, iMaxWidth + MenuElement_Hover.getDrawExtraXPos() * 2, this.getHeight2() + CFG.PADDING * 2
         );
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.2F * nAlpha));
      ImageManager.getImage(Images.gradientVertical)
         .draw(oSB, nPosX - MenuElement_Hover.getDrawExtraXPos(), nPosY, iMaxWidth + MenuElement_Hover.getDrawExtraXPos() * 2, CFG.PADDING * 2);
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 1.0F * nAlpha));
      Images.gradientFull.draw(oSB, nPosX - MenuElement_Hover.getDrawExtraXPos(), nPosY + 1, iMaxWidth + MenuElement_Hover.getDrawExtraXPos() * 2, 1);
      Images.gradientFull
         .draw(
            oSB,
            nPosX - MenuElement_Hover.getDrawExtraXPos(),
            nPosY + this.getHeight2() + CFG.PADDING * 2 - 2,
            iMaxWidth + MenuElement_Hover.getDrawExtraXPos() * 2,
            1
         );
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 1.0F * nAlpha));
      Images.gradientFull.draw(oSB, nPosX - MenuElement_Hover.getDrawExtraXPos(), nPosY, iMaxWidth + MenuElement_Hover.getDrawExtraXPos() * 2, 1);
      Images.gradientFull
         .draw(
            oSB,
            nPosX - MenuElement_Hover.getDrawExtraXPos(),
            nPosY + this.getHeight2() + CFG.PADDING * 2 - 1,
            iMaxWidth + MenuElement_Hover.getDrawExtraXPos() * 2,
            1
         );
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.15F * nAlpha));
      ImageManager.getImage(Images.gradientVertical)
         .draw(
            oSB,
            nPosX - MenuElement_Hover.getDrawExtraXPos(),
            nPosY + this.getHeight2() + CFG.PADDING * 2,
            iMaxWidth + MenuElement_Hover.getDrawExtraXPos() * 2,
            CFG.PADDING
         );
      Images.gradientXY
         .draw(
            oSB,
            nPosX - MenuElement_Hover.getDrawExtraXPos(),
            nPosY + this.getHeight2() + CFG.PADDING * 2,
            iMaxWidth + MenuElement_Hover.getDrawExtraXPos() * 2,
            CFG.PADDING,
            false,
            true
         );
      ImageManager.getImage(Images.gradientVertical)
         .draw(
            oSB,
            nPosX - MenuElement_Hover.getDrawExtraXPos(),
            nPosY - CFG.PADDING,
            iMaxWidth + MenuElement_Hover.getDrawExtraXPos() * 2,
            CFG.PADDING,
            false,
            true
         );
      Images.gradientXY
         .draw(oSB, nPosX - MenuElement_Hover.getDrawExtraXPos(), nPosY - CFG.PADDING, iMaxWidth + MenuElement_Hover.getDrawExtraXPos() * 2, CFG.PADDING);
      if (animationState >= 0) {
         if (animationState == 0) {
            float drawPerc = Math.min(1.0F * (float)(CFG.currentTimeMillis - lTimeAnimation) / 2000.0F, 1.0F);
            oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.075F * nAlpha));
            Images.line_32_off1
               .draw(
                  oSB,
                  nPosX - MenuElement_Hover.getDrawExtraXPos(),
                  nPosY + this.getHeight() - 2,
                  (int)((iMaxWidth + MenuElement_Hover.getDrawExtraXPos() * 2) * drawPerc),
                  1
               );
            if (lTimeAnimation < CFG.currentTimeMillis - 2000L) {
               animationState++;
               lTimeAnimation = CFG.currentTimeMillis;
            }
         } else {
            float drawPerc = Math.min(1.0F * (float)(CFG.currentTimeMillis - lTimeAnimation) / 2000.0F, 1.0F);
            oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.075F * nAlpha));
            Images.line_32_off1
               .draw(
                  oSB,
                  nPosX - MenuElement_Hover.getDrawExtraXPos() + (int)((iMaxWidth + MenuElement_Hover.getDrawExtraXPos() * 2) * drawPerc),
                  nPosY + this.getHeight() - 2,
                  iMaxWidth + MenuElement_Hover.getDrawExtraXPos() * 2 - (int)((iMaxWidth + MenuElement_Hover.getDrawExtraXPos() * 2) * drawPerc),
                  1
               );
            if (lTimeAnimation < CFG.currentTimeMillis - 2000L) {
               animationState = 0;
               lTimeAnimation = CFG.currentTimeMillis;
            }
         }

         oSB.setColor(Color.WHITE);
      }

      oSB.setColor(Color.WHITE);
      Renderer.drawText(
         oSB,
         this.fontID,
         this.sText,
         nPosX,
         nPosY + this.getHeight() / 2 - this.iTextHeight / 2,
         new Color(this.oColor.r, this.oColor.g, this.oColor.b, nAlpha)
      );
   }

   @Override
   public int getWidth() {
      return this.iTextWidth;
   }

   @Override
   public int getHeight() {
      return CFG.TEXT_HEIGHT + CFG.PADDING * 5;
   }

   public int getHeight2() {
      return CFG.TEXT_HEIGHT + CFG.PADDING * 3;
   }
}
