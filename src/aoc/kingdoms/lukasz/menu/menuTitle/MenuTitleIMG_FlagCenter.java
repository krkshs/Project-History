package aoc.kingdoms.lukasz.menu.menuTitle;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GlyphLayout_Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuTitleIMG_FlagCenter extends MenuTitleIMG {
   public String sText2;
   public int flagWidth;
   public int flagHeight;
   public int iText2Width = 0;

   public MenuTitleIMG_FlagCenter(String sText, String sText2, boolean moveable, boolean resizable, int imageID) {
      super(sText, moveable, resizable, imageID);
      this.fontID = CFG.FONT_BOLD;
      this.sText2 = sText2;
      this.flagHeight = this.getHeight() - Images.boxTitleBORDERWIDTH;
      float tScale = (float)this.flagHeight / CFG.CIV_FLAG_HEIGHT;
      this.flagWidth = (int)(CFG.CIV_FLAG_WIDTH * tScale);
      GlyphLayout_Game glyphLayout = new GlyphLayout_Game();
      glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_REGULAR_SMALL), sText2);
      this.iText2Width = (int)glyphLayout.width;
   }

   @Override
   public void draw(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, Status titleStatus) {
      super.draw(oSB, nPosX, nPosY, nWidth, titleStatus);

      try {
         oSB.setShader(Renderer.shaderAlpha);
         Game.getCiv(this.getFlagCivID()).getFlag().getTexture().bind(1);
         Gdx.gl.glActiveTexture(33984);
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 1.0F));
         ImageManager.getImage(Images.gradientHorizontal)
            .draw(oSB, nPosX + Images.boxTitleBORDERWIDTH, nPosY - this.getHeight() + Images.boxTitleBORDERWIDTH, this.flagWidth, this.flagHeight);
         oSB.setColor(Color.WHITE);
         oSB.flush();
         oSB.setShader(Renderer.shaderDefault);
         oSB.setShader(Renderer.shaderAlpha);
         ImageManager.getImage(Images.titleFlagOver).getTexture().bind(1);
         Gdx.gl.glActiveTexture(33984);
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.75F));
         ImageManager.getImage(Images.gradientHorizontal)
            .draw(oSB, nPosX, nPosY - this.getHeight() + Images.boxTitleBORDERWIDTH, this.flagWidth, this.flagHeight);
         oSB.setColor(Color.WHITE);
         oSB.flush();
         oSB.setShader(Renderer.shaderDefault);
      } catch (Exception var7) {
      }
   }

   @Override
   public void drawText(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, Status titleStatus) {
      Renderer.drawText(
         oSB,
         this.fontID,
         this.getText(),
         nPosX + nWidth / 2 - this.getTextWidth() / 2,
         1 + nPosY - this.getHeight() + (this.getHeight() / 2 - this.getTextHeight()),
         this.getColorText(titleStatus)
      );
      Renderer.drawText(
         oSB,
         CFG.FONT_REGULAR_SMALL,
         this.sText2,
         nPosX + nWidth / 2 - this.iText2Width / 2,
         1 + nPosY - this.getHeight() + this.getHeight() / 2 + CFG.PADDING,
         MenuTitle.colorHovered
      );
   }

   public int getFlagCivID() {
      return 0;
   }
}
