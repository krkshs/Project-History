package aoc.kingdoms.lukasz.menu_element.menuElementHover;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuElement_HoverElement_Type_Button_TextBonus implements MenuElement_HoverElement_Type {
   public String sText;
   public int iTextWidth;
   public int iTextHeight;
   public String sText2;
   public int iTextWidth2;
   public int iTextHeight2;
   public Color oColor;
   public Color oColorBonus;
   public int fontID = 0;
   public int fontID2 = 0;
   public int imageID;
   public int iconWidth;
   public int iconHeight;
   public int maxIconWidth;

   public MenuElement_HoverElement_Type_Button_TextBonus(String sText, String sText2, int imageID, int fontID, Color nColorBonus) {
      this.init(sText, sText2, imageID, fontID, CFG.FONT_BOLD_SMALL, Colors.BUTTON_TEXT_BRIGHT, nColorBonus);
   }

   public MenuElement_HoverElement_Type_Button_TextBonus(String sText, String sText2, int imageID, int fontID, int fontID2, Color nColorBonus) {
      this.init(sText, sText2, imageID, fontID, fontID2, Colors.BUTTON_TEXT_BRIGHT, nColorBonus);
   }

   public MenuElement_HoverElement_Type_Button_TextBonus(String sText, String sText2, int imageID, int fontID, int fontID2, Color nColorLeft, Color nColorBonus) {
      this.init(sText, sText2, imageID, fontID, fontID2, nColorLeft, nColorBonus);
   }

   public final void init(String sText, String sText2, int imageID, int nFontID, int fontID2, Color oColor, Color nColorBonus) {
      this.sText = sText;
      this.sText2 = sText2;
      this.imageID = imageID;
      this.oColor = oColor;
      this.oColorBonus = nColorBonus;
      this.fontID = nFontID;
      this.fontID2 = fontID2;
      Renderer.glyphLayout.setText(Renderer.fontMain.get(this.fontID), sText);
      this.iTextWidth = (int)Renderer.glyphLayout.width;
      this.iTextHeight = (int)Renderer.glyphLayout.height;
      Renderer.glyphLayout.setText(Renderer.fontMain.get(fontID2), sText2);
      this.iTextWidth2 = (int)Renderer.glyphLayout.width;
      this.iTextHeight2 = (int)Renderer.glyphLayout.height;
      this.maxIconWidth = ImageManager.getImage(Images.gold).getWidth();
      float iconScale = this.getImageScale(imageID) * 1.1F;
      this.iconWidth = (int)(ImageManager.getImage(imageID).getWidth() * iconScale);
      this.iconHeight = (int)(ImageManager.getImage(imageID).getHeight() * iconScale);
   }

   @Override
   public void draw(SpriteBatch oSB, int nPosX, int nPosY, float nAlpha, int iMaxWidth) {
      nPosY += this.getPaddingY();
      oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.5F * nAlpha));
      Renderer.drawBox(oSB, Images.statsRectBG, nPosX, nPosY, iMaxWidth, this.getHeight2(), 1.0F * nAlpha);
      oSB.setColor(Color.WHITE);
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_BG_BLUE.r, Colors.COLOR_GRADIENT_BG_BLUE.g, Colors.COLOR_GRADIENT_BG_BLUE.b, 0.35F * nAlpha));
      Renderer.drawBox(oSB, Images.statsRectBG, nPosX, nPosY, CFG.PADDING * 2 + this.maxIconWidth, this.getHeight2(), 1.0F * nAlpha);
      oSB.setColor(Color.WHITE);
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.35F * nAlpha));
      Images.gradientFull.draw(oSB, nPosX, nPosY, CFG.PADDING * 2 + this.maxIconWidth, this.getHeight2());
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.3F * nAlpha));
      Images.gradientXY.draw(oSB, nPosX, nPosY, CFG.PADDING * 2 + this.maxIconWidth, this.getHeight2());
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.55F));
      Images.gradientFull.draw(oSB, nPosX, nPosY + this.getHeight2() - 1, CFG.PADDING * 2 + this.maxIconWidth, 1);
      Images.gradientFull.draw(oSB, nPosX, nPosY, CFG.PADDING * 2 + this.maxIconWidth, 1);
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.9F * nAlpha));
      Images.gradientFull.draw(oSB, nPosX, nPosY + this.getHeight2() - 2, CFG.PADDING * 2 + this.maxIconWidth, 1);
      Images.gradientFull.draw(oSB, nPosX, nPosY + 1, CFG.PADDING * 2 + this.maxIconWidth, 1);
      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, nAlpha));
      ImageManager.getImage(this.imageID)
         .draw(
            oSB,
            nPosX + (this.maxIconWidth + CFG.PADDING * 2) / 2 - this.iconWidth / 2,
            nPosY + (this.getHeight2() - this.iconHeight) / 2,
            this.iconWidth,
            this.iconHeight
         );
      Renderer.drawText(
         oSB,
         this.fontID,
         this.sText,
         nPosX + this.maxIconWidth + CFG.PADDING * 4,
         nPosY + (this.getHeight2() - this.iTextHeight) / 2,
         new Color(this.oColor.r, this.oColor.g, this.oColor.b, nAlpha)
      );
      Renderer.drawText(
         oSB,
         this.fontID2,
         this.sText2,
         nPosX + this.maxIconWidth + this.iTextWidth + CFG.PADDING * 4,
         nPosY + (this.getHeight2() - this.iTextHeight) / 2,
         new Color(this.oColorBonus.r, this.oColorBonus.g, this.oColorBonus.b, nAlpha)
      );
      oSB.setColor(Color.WHITE);
   }

   @Override
   public int getWidth() {
      return this.iTextWidth + this.iTextWidth2 + this.maxIconWidth + CFG.PADDING * 6;
   }

   @Override
   public int getHeight() {
      return CFG.TEXT_HEIGHT + CFG.PADDING * 4;
   }

   public int getPaddingY() {
      return CFG.PADDING;
   }

   public int getHeight2() {
      return CFG.TEXT_HEIGHT + CFG.PADDING * 4;
   }

   private final float getImageScale(int iImageID) {
      return (float)CFG.TEXT_HEIGHT / ImageManager.getImage(iImageID).getHeight();
   }
}
