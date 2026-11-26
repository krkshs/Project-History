package aoc.kingdoms.lukasz.menu_element.menuElementHover;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuElement_HoverElement_Type_TextTitle_BG_Center implements MenuElement_HoverElement_Type {
   private String sText;
   private int iTextWidth;
   private Color oColor;
   private int fontID = 0;

   public MenuElement_HoverElement_Type_TextTitle_BG_Center(String sText) {
      this.init(sText, 0, Colors.HOVER_LEFT);
   }

   public MenuElement_HoverElement_Type_TextTitle_BG_Center(String sText, int fontID) {
      this.init(sText, fontID, Colors.HOVER_LEFT);
   }

   public MenuElement_HoverElement_Type_TextTitle_BG_Center(String sText, Color nColor) {
      this.init(sText, 0, nColor);
   }

   public MenuElement_HoverElement_Type_TextTitle_BG_Center(String sText, int fontID, Color nColor) {
      this.init(sText, fontID, nColor);
   }

   public final void init(String sText, int nFontID, Color oColor) {
      this.sText = sText;
      this.oColor = oColor;
      this.fontID = nFontID;
      Renderer.glyphLayout.setText(Renderer.fontMain.get(this.fontID), sText);
      this.iTextWidth = (int)Renderer.glyphLayout.width;
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
         .draw(
            oSB,
            nPosX - MenuElement_Hover.getDrawExtraXPos(),
            nPosY - CFG.PADDING,
            iMaxWidth + MenuElement_Hover.getDrawExtraXPos() * 2,
            CFG.PADDING,
            false,
            true
         );
      oSB.setColor(Color.WHITE);
      Renderer.drawText(
         oSB,
         this.fontID,
         this.sText,
         nPosX + iMaxWidth / 2 - this.iTextWidth / 2,
         nPosY + CFG.PADDING * 2 + CFG.PADDING / 2,
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
