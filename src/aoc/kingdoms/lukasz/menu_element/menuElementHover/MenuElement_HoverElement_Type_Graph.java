package aoc.kingdoms.lukasz.menu_element.menuElementHover;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.graph.Graph;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuElement_HoverElement_Type_Graph implements MenuElement_HoverElement_Type {
   public Graph graph;
   public int iGraphWidth = 0;

   public MenuElement_HoverElement_Type_Graph(String sText, Graph.GraphType graphType, boolean split100) {
      this.iGraphWidth = CFG.BUTTON_WIDTH * 3;
      this.graph = new Graph("", sText, 0, 0, this.iGraphWidth, CFG.BUTTON_WIDTH + CFG.BUTTON_WIDTH / 2, true, 1, graphType, split100);
   }

   @Override
   public void draw(SpriteBatch oSB, int nPosX, int nPosY, float nAlpha, int iMaxWidth) {
      if (this.iGraphWidth != iMaxWidth) {
         this.iGraphWidth = iMaxWidth;
         this.graph = new Graph(
            "", this.graph.sTextY, 0, 0, this.iGraphWidth, CFG.BUTTON_WIDTH + CFG.BUTTON_WIDTH / 2, true, 1, this.graph.graphType, this.graph.split100
         );
      }

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
      oSB.setColor(Color.WHITE);
      this.graph.draw(oSB, nPosX, nPosY + CFG.PADDING * 2, false, false);
   }

   @Override
   public int getWidth() {
      return this.graph.getWidth();
   }

   @Override
   public int getHeight() {
      return this.graph.getHeight() + CFG.PADDING;
   }

   public int getHeight2() {
      return this.graph.getHeight() + CFG.PADDING;
   }
}
