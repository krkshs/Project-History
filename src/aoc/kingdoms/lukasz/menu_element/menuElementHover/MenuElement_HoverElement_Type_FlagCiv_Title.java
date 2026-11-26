package aoc.kingdoms.lukasz.menu_element.menuElementHover;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuElement_HoverElement_Type_FlagCiv_Title implements MenuElement_HoverElement_Type {
   private static final int fontID = CFG.FONT_BOLD;
   private static final int fontID2 = CFG.FONT_REGULAR_SMALL;
   private int iCivID;
   private int iTextWidthMAX = 0;
   private String sText2;
   private String civName;

   public MenuElement_HoverElement_Type_FlagCiv_Title(int iCivID, String nText2) {
      this.iCivID = iCivID;
      this.sText2 = nText2;
      Renderer.glyphLayout.setText(Renderer.fontMain.get(fontID), Game.getCiv(iCivID).getCivName());
      this.iTextWidthMAX = (int)Renderer.glyphLayout.width;
      Renderer.glyphLayout.setText(Renderer.fontMain.get(fontID2), this.sText2);
      this.iTextWidthMAX = Math.max(this.iTextWidthMAX, (int)Renderer.glyphLayout.width);
      if (iCivID < 0) {
         this.civName = Game.lang.get("Rebels");
      } else {
         this.civName = Game.getCiv(iCivID).getCivName();
      }
   }

   @Override
   public void draw(SpriteBatch oSB, int nPosX, int nPosY, float nAlpha, int iMaxWidth) {
      oSB.setColor(Game.getCiv(this.iCivID).getRGB(0.65F * nAlpha));
      Images.gradientXY
         .draw(
            oSB,
            nPosX - MenuElement_Hover.getDrawExtraXPos(),
            nPosY - CFG.PADDING,
            iMaxWidth + MenuElement_Hover.getDrawExtraXPos() * 2,
            this.getHeight() + CFG.PADDING
         );
      oSB.setColor(new Color(Colors.HOVER_LINE1.r, Colors.HOVER_LINE1.g, Colors.HOVER_LINE1.b, Colors.HOVER_LINE1.a * nAlpha));
      Images.pix.draw(oSB, nPosX - MenuElement_Hover.getDrawExtraXPos(), nPosY + this.getHeight() - 1, iMaxWidth + MenuElement_Hover.getDrawExtraXPos() * 2, 1);
      oSB.setColor(new Color(Colors.HOVER_LINE2.r, Colors.HOVER_LINE2.g, Colors.HOVER_LINE2.b, Colors.HOVER_LINE2.a * nAlpha));
      Images.pix.draw(oSB, nPosX - MenuElement_Hover.getDrawExtraXPos(), nPosY + this.getHeight(), iMaxWidth + MenuElement_Hover.getDrawExtraXPos() * 2, 1);
      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, nAlpha));
      oSB.setShader(Renderer.shaderAlpha);
      if (this.iCivID < 0) {
         ImageManager.getImage(Images.rebelsFlag).getTexture().bind(1);
      } else {
         Game.getCiv(this.iCivID).getFlag().getTexture().bind(1);
      }

      Gdx.gl.glActiveTexture(33984);
      ImageManager.getImage(Images.flagMaskDefault)
         .draw(
            oSB,
            nPosX + (ImageManager.getImage(Images.flagOverDefault).getWidth() - ImageManager.getImage(Images.flagMaskDefault).getWidth()) / 2,
            nPosY + (ImageManager.getImage(Images.flagOverDefault).getHeight() - ImageManager.getImage(Images.flagMaskDefault).getHeight()) / 2,
            ImageManager.getImage(Images.flagMaskDefault).getWidth(),
            ImageManager.getImage(Images.flagMaskDefault).getHeight()
         );
      oSB.flush();
      oSB.setShader(Renderer.shaderDefault);
      ImageManager.getImage(Images.flagOverDefault).draw(oSB, nPosX, nPosY);
      Renderer.drawText(
         oSB,
         fontID,
         this.civName,
         nPosX + CFG.PADDING + ImageManager.getImage(Images.flagOverDefault).getWidth(),
         nPosY + ImageManager.getImage(Images.flagOverDefault).getHeight() / 2 - (int)Math.floor(CFG.PADDING / 2.0F) - CFG.TEXT_HEIGHT,
         new Color(Colors.HOVER_TITLE.r, Colors.HOVER_TITLE.g, Colors.HOVER_TITLE.b, Colors.HOVER_TITLE.a * nAlpha)
      );
      Renderer.drawText(
         oSB,
         fontID2,
         this.sText2,
         nPosX + CFG.PADDING + ImageManager.getImage(Images.flagOverDefault).getWidth(),
         nPosY + ImageManager.getImage(Images.flagOverDefault).getHeight() / 2 + (int)Math.ceil(CFG.PADDING / 2.0F),
         new Color(Colors.HOVER_TITLE_2.r, Colors.HOVER_TITLE_2.g, Colors.HOVER_TITLE_2.b, Colors.HOVER_TITLE_2.a * nAlpha)
      );
   }

   @Override
   public int getWidth() {
      return CFG.PADDING + ImageManager.getImage(Images.flagOverDefault).getWidth() + this.iTextWidthMAX + CFG.PADDING * 2;
   }

   @Override
   public int getHeight() {
      return ImageManager.getImage(Images.flagOverDefault).getHeight() + CFG.PADDING + CFG.PADDING / 2;
   }
}
