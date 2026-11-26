package aoc.kingdoms.lukasz.menu_element.menuElementHover;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuElement_HoverElement_Type_Button_TextBonus_Perc extends MenuElement_HoverElement_Type_Button_TextBonus {
   public float perc;

   public MenuElement_HoverElement_Type_Button_TextBonus_Perc(String sText, String sText2, int imageID, int fontID, Color nColorBonus, float perc) {
      super(sText, sText2, imageID, fontID, nColorBonus);
      this.perc = Math.max(0.05F, Math.min(1.0F, perc));
   }

   public MenuElement_HoverElement_Type_Button_TextBonus_Perc(String sText, String sText2, int imageID, int fontID, int fontID2, Color nColorBonus, float perc) {
      super(sText, sText2, imageID, fontID, fontID2, nColorBonus);
      this.perc = Math.max(0.05F, Math.min(1.0F, perc));
   }

   public MenuElement_HoverElement_Type_Button_TextBonus_Perc(
      String sText, String sText2, int imageID, int fontID, int fontID2, Color nColorLeft, Color nColorBonus, float perc
   ) {
      super(sText, sText2, imageID, fontID, fontID2, nColorLeft, nColorBonus);
      this.perc = Math.max(0.05F, Math.min(1.0F, perc));
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
      oSB.setColor(new Color(Colors.HOVER_POSITIVE.r, Colors.HOVER_POSITIVE.g, Colors.HOVER_POSITIVE.b, 0.25F));
      Renderer.drawBoxProgress(oSB, Images.statsRectBG, nPosX, nPosY, (int)(iMaxWidth * this.perc), this.getHeight2(), (int)(iMaxWidth * this.perc));
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
}
