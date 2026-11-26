package aoc.kingdoms.lukasz.menu_element.menuElementHover;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuElement_HoverElement_Type_Title implements MenuElement_HoverElement_Type {
   private static final int fontID = CFG.FONT_BOLD;
   private static final int fontID2 = CFG.FONT_REGULAR_SMALL;
   private int imageID;
   private String sText;
   private String sText2;
   private String sText3;
   private String sText4;
   private int iTextWidthMAX = 0;
   public int iTextWidth = 0;
   public int iTextWidth2 = 0;
   public int iTextWidth3 = 0;
   public int iTextWidth4 = 0;
   public int iTextHeight3 = 0;
   public int maxHeight = 0;
   public int iTextY = 0;
   public Color textColor1;
   public Color textColor;

   public MenuElement_HoverElement_Type_Title(int imageID, String nText, String nText2, Color textColor1, String nText3, String nText4, Color textColor) {
      this.imageID = imageID;
      this.sText = nText;
      this.sText2 = nText2;
      this.sText3 = nText3;
      this.sText4 = nText4;
      this.textColor = textColor;
      this.textColor1 = textColor1;
      Renderer.glyphLayout.setText(Renderer.fontMain.get(fontID), this.sText);
      this.iTextWidth = (int)Renderer.glyphLayout.width;
      Renderer.glyphLayout.setText(Renderer.fontMain.get(fontID), this.sText2);
      this.iTextWidth2 = (int)Renderer.glyphLayout.width;
      Renderer.glyphLayout.setText(Renderer.fontMain.get(fontID2), this.sText3);
      this.iTextWidth3 = (int)Renderer.glyphLayout.width;
      this.iTextHeight3 = (int)Renderer.glyphLayout.height;
      Renderer.glyphLayout.setText(Renderer.fontMain.get(fontID2), this.sText4);
      this.iTextWidth4 = (int)Renderer.glyphLayout.width;
      this.maxHeight = Math.max(CFG.TEXT_HEIGHT * 2 + CFG.PADDING, ImageManager.getImage(imageID).getHeight());
      this.iTextWidthMAX = Math.max(this.iTextWidth + this.iTextWidth2, this.iTextWidth3 + this.iTextWidth4);
      this.iTextY = this.getHeight2() / 2 - (CFG.TEXT_HEIGHT * 2 + CFG.PADDING * 2) / 2;
   }

   @Override
   public void draw(SpriteBatch oSB, int nPosX, int nPosY, float nAlpha, int iMaxWidth) {
      nPosY -= CFG.PADDING * 2;
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_BG_BLUE.r, Colors.COLOR_GRADIENT_BG_BLUE.g, Colors.COLOR_GRADIENT_BG_BLUE.b, 1.0F * nAlpha));
      Renderer.drawBox(
         oSB,
         Images.statsRectBG,
         nPosX - MenuElement_Hover.getDrawExtraXPos(),
         nPosY,
         iMaxWidth + MenuElement_Hover.getDrawExtraXPos() * 2,
         this.getHeight2(),
         1.0F
      );
      oSB.setColor(Color.WHITE);
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_BG_BLUE.r, Colors.COLOR_GRADIENT_BG_BLUE.g, Colors.COLOR_GRADIENT_BG_BLUE.b, 0.35F * nAlpha));
      Renderer.drawBox(
         oSB,
         Images.statsRectBG,
         nPosX - MenuElement_Hover.getDrawExtraXPos(),
         nPosY,
         iMaxWidth + MenuElement_Hover.getDrawExtraXPos() * 2,
         this.getHeight2(),
         1.0F
      );
      oSB.setColor(Color.WHITE);
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.3F * nAlpha));
      Images.gradientFull
         .draw(oSB, nPosX - MenuElement_Hover.getDrawExtraXPos(), nPosY, iMaxWidth + MenuElement_Hover.getDrawExtraXPos() * 2, this.getHeight2());
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.35F * nAlpha));
      Images.gradientFull
         .draw(oSB, nPosX - MenuElement_Hover.getDrawExtraXPos(), nPosY, iMaxWidth + MenuElement_Hover.getDrawExtraXPos() * 2, this.getHeight2());
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.7F * nAlpha));
      ImageManager.getImage(Images.gradientXYVertical)
         .draw(
            oSB,
            nPosX - MenuElement_Hover.getDrawExtraXPos(),
            nPosY + CFG.PADDING,
            iMaxWidth + MenuElement_Hover.getDrawExtraXPos() * 2,
            this.getHeight2() - CFG.PADDING * 2
         );
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.45F * nAlpha));
      Images.gradientXY
         .draw(oSB, nPosX - MenuElement_Hover.getDrawExtraXPos(), nPosY, iMaxWidth + MenuElement_Hover.getDrawExtraXPos() * 2, CFG.PADDING * 2, false, true);
      Images.gradientXY
         .draw(
            oSB,
            nPosX - MenuElement_Hover.getDrawExtraXPos(),
            nPosY + this.getHeight2() - CFG.PADDING * 2,
            iMaxWidth + MenuElement_Hover.getDrawExtraXPos() * 2,
            CFG.PADDING * 2
         );
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.85F * nAlpha));
      Renderer.drawBox(
         oSB,
         Images.statsRectBGBorder,
         nPosX - MenuElement_Hover.getDrawExtraXPos(),
         nPosY,
         iMaxWidth + MenuElement_Hover.getDrawExtraXPos() * 2,
         this.getHeight2(),
         1.0F
      );
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F * nAlpha));
      Images.gradientFull
         .draw(oSB, nPosX - MenuElement_Hover.getDrawExtraXPos(), nPosY + this.getHeight2() - 1, iMaxWidth + MenuElement_Hover.getDrawExtraXPos() * 2, 1);
      Images.gradientFull.draw(oSB, nPosX - MenuElement_Hover.getDrawExtraXPos(), nPosY, iMaxWidth + MenuElement_Hover.getDrawExtraXPos() * 2, 1);
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.85F));
      Images.gradientFull
         .draw(oSB, nPosX - MenuElement_Hover.getDrawExtraXPos(), nPosY + this.getHeight2() - 2, iMaxWidth + MenuElement_Hover.getDrawExtraXPos() * 2, 1);
      Images.gradientFull.draw(oSB, nPosX - MenuElement_Hover.getDrawExtraXPos(), nPosY + 1, iMaxWidth + MenuElement_Hover.getDrawExtraXPos() * 2, 1);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.55F * nAlpha));
      Images.gradientFull
         .draw(oSB, nPosX - MenuElement_Hover.getDrawExtraXPos(), nPosY + this.getHeight2() - 1, iMaxWidth + MenuElement_Hover.getDrawExtraXPos() * 2, 1);
      Images.gradientFull.draw(oSB, nPosX - MenuElement_Hover.getDrawExtraXPos(), nPosY, iMaxWidth + MenuElement_Hover.getDrawExtraXPos() * 2, 1);
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.9F));
      Images.gradientFull
         .draw(oSB, nPosX - MenuElement_Hover.getDrawExtraXPos(), nPosY + this.getHeight2() - 2, iMaxWidth + MenuElement_Hover.getDrawExtraXPos() * 2, 1);
      Images.gradientFull.draw(oSB, nPosX - MenuElement_Hover.getDrawExtraXPos(), nPosY + 1, iMaxWidth + MenuElement_Hover.getDrawExtraXPos() * 2, 1);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.45F * nAlpha));
      Images.gradientXY
         .draw(
            oSB,
            nPosX - MenuElement_Hover.getDrawExtraXPos(),
            nPosY + this.getHeight2(),
            iMaxWidth + MenuElement_Hover.getDrawExtraXPos() * 2,
            CFG.PADDING,
            false,
            true
         );
      iMaxWidth = ImageManager.getImage(this.imageID).getWidth() + CFG.PADDING * 2;
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_BG_BLUE.r, Colors.COLOR_GRADIENT_BG_BLUE.g, Colors.COLOR_GRADIENT_BG_BLUE.b, 1.0F * nAlpha));
      Renderer.drawBox(
         oSB,
         Images.statsRectBG,
         nPosX - MenuElement_Hover.getDrawExtraXPos(),
         nPosY,
         iMaxWidth + MenuElement_Hover.getDrawExtraXPos() * 2,
         this.getHeight2(),
         1.0F
      );
      oSB.setColor(Color.WHITE);
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_BG_BLUE.r, Colors.COLOR_GRADIENT_BG_BLUE.g, Colors.COLOR_GRADIENT_BG_BLUE.b, 0.35F * nAlpha));
      Renderer.drawBox(
         oSB,
         Images.statsRectBG,
         nPosX - MenuElement_Hover.getDrawExtraXPos(),
         nPosY,
         iMaxWidth + MenuElement_Hover.getDrawExtraXPos() * 2,
         this.getHeight2(),
         1.0F
      );
      oSB.setColor(Color.WHITE);
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.3F * nAlpha));
      Images.gradientFull
         .draw(oSB, nPosX - MenuElement_Hover.getDrawExtraXPos(), nPosY, iMaxWidth + MenuElement_Hover.getDrawExtraXPos() * 2, this.getHeight2());
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.35F * nAlpha));
      Images.gradientFull
         .draw(oSB, nPosX - MenuElement_Hover.getDrawExtraXPos(), nPosY, iMaxWidth + MenuElement_Hover.getDrawExtraXPos() * 2, this.getHeight2());
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.7F * nAlpha));
      ImageManager.getImage(Images.gradientXYVertical)
         .draw(
            oSB,
            nPosX - MenuElement_Hover.getDrawExtraXPos(),
            nPosY + CFG.PADDING,
            iMaxWidth + MenuElement_Hover.getDrawExtraXPos() * 2,
            this.getHeight2() - CFG.PADDING * 2
         );
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.45F * nAlpha));
      Images.gradientXY
         .draw(oSB, nPosX - MenuElement_Hover.getDrawExtraXPos(), nPosY, iMaxWidth + MenuElement_Hover.getDrawExtraXPos() * 2, CFG.PADDING * 2, false, true);
      Images.gradientXY
         .draw(
            oSB,
            nPosX - MenuElement_Hover.getDrawExtraXPos(),
            nPosY + this.getHeight2() - CFG.PADDING * 2,
            iMaxWidth + MenuElement_Hover.getDrawExtraXPos() * 2,
            CFG.PADDING * 2
         );
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.85F * nAlpha));
      Renderer.drawBox(
         oSB,
         Images.statsRectBGBorder,
         nPosX - MenuElement_Hover.getDrawExtraXPos(),
         nPosY,
         iMaxWidth + MenuElement_Hover.getDrawExtraXPos() * 2,
         this.getHeight2(),
         1.0F
      );
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F * nAlpha));
      Images.gradientFull
         .draw(oSB, nPosX - MenuElement_Hover.getDrawExtraXPos(), nPosY + this.getHeight2() - 1, iMaxWidth + MenuElement_Hover.getDrawExtraXPos() * 2, 1);
      Images.gradientFull.draw(oSB, nPosX - MenuElement_Hover.getDrawExtraXPos(), nPosY, iMaxWidth + MenuElement_Hover.getDrawExtraXPos() * 2, 1);
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.85F));
      Images.gradientFull
         .draw(oSB, nPosX - MenuElement_Hover.getDrawExtraXPos(), nPosY + this.getHeight2() - 2, iMaxWidth + MenuElement_Hover.getDrawExtraXPos() * 2, 1);
      Images.gradientFull.draw(oSB, nPosX - MenuElement_Hover.getDrawExtraXPos(), nPosY + 1, iMaxWidth + MenuElement_Hover.getDrawExtraXPos() * 2, 1);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.55F * nAlpha));
      Images.gradientFull
         .draw(oSB, nPosX - MenuElement_Hover.getDrawExtraXPos(), nPosY + this.getHeight2() - 1, iMaxWidth + MenuElement_Hover.getDrawExtraXPos() * 2, 1);
      Images.gradientFull.draw(oSB, nPosX - MenuElement_Hover.getDrawExtraXPos(), nPosY, iMaxWidth + MenuElement_Hover.getDrawExtraXPos() * 2, 1);
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.9F));
      Images.gradientFull
         .draw(oSB, nPosX - MenuElement_Hover.getDrawExtraXPos(), nPosY + this.getHeight2() - 2, iMaxWidth + MenuElement_Hover.getDrawExtraXPos() * 2, 1);
      Images.gradientFull.draw(oSB, nPosX - MenuElement_Hover.getDrawExtraXPos(), nPosY + 1, iMaxWidth + MenuElement_Hover.getDrawExtraXPos() * 2, 1);
      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, nAlpha));
      ImageManager.getImage(this.imageID).draw(oSB, nPosX + CFG.PADDING, nPosY + this.getHeight2() / 2 - ImageManager.getImage(this.imageID).getHeight() / 2);
      Renderer.drawText(
         oSB,
         fontID,
         this.sText,
         nPosX + CFG.PADDING * 7 + ImageManager.getImage(this.imageID).getWidth(),
         nPosY + this.iTextY,
         new Color(Colors.COLOR_TOP_STATS.r, Colors.COLOR_TOP_STATS.g, Colors.COLOR_TOP_STATS.b, nAlpha)
      );
      Renderer.drawText(
         oSB,
         fontID,
         this.sText2,
         nPosX + CFG.PADDING * 7 + this.iTextWidth + ImageManager.getImage(this.imageID).getWidth(),
         nPosY + this.iTextY,
         new Color(this.textColor1.r, this.textColor1.g, this.textColor1.b, nAlpha)
      );
      Renderer.drawText(
         oSB,
         fontID2,
         this.sText3,
         nPosX + CFG.PADDING * 7 + ImageManager.getImage(this.imageID).getWidth(),
         nPosY + this.iTextY + CFG.TEXT_HEIGHT + CFG.PADDING * 2 + CFG.TEXT_HEIGHT / 2 - this.iTextHeight3 / 2,
         new Color(Colors.COLOR_TOP_STATS2.r, Colors.COLOR_TOP_STATS2.g, Colors.COLOR_TOP_STATS2.b, nAlpha)
      );
      Renderer.drawText(
         oSB,
         fontID2,
         this.sText4,
         nPosX + CFG.PADDING * 7 + this.iTextWidth3 + ImageManager.getImage(this.imageID).getWidth(),
         nPosY + this.iTextY + CFG.TEXT_HEIGHT + CFG.PADDING * 2 + CFG.TEXT_HEIGHT / 2 - this.iTextHeight3 / 2,
         new Color(this.textColor.r, this.textColor.g, this.textColor.b, nAlpha)
      );
   }

   @Override
   public int getWidth() {
      return ImageManager.getImage(this.imageID).getWidth() + this.iTextWidthMAX + CFG.PADDING * 8;
   }

   @Override
   public int getHeight() {
      return this.maxHeight + CFG.PADDING * 5;
   }

   public int getHeight2() {
      return this.maxHeight + CFG.PADDING * 7;
   }
}
