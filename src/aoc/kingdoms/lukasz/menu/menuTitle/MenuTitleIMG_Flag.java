package aoc.kingdoms.lukasz.menu.menuTitle;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuTitleIMG_Flag extends MenuTitleIMG {
   public String sText2;
   public int flagWidth;
   public int flagHeight;

   public MenuTitleIMG_Flag(String sText, String sText2, boolean moveable, boolean resizable, int imageID) {
      super(sText2, moveable, resizable, imageID);
      this.fontID = CFG.FONT_BOLD;
      this.sText2 = sText;
      this.flagHeight = this.getHeight() - Images.boxTitleBORDERWIDTH;
      float tScale = (float)this.flagHeight / CFG.CIV_FLAG_HEIGHT;
      this.flagWidth = (int)(CFG.CIV_FLAG_WIDTH * tScale);
   }

   @Override
   public void draw(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, Status titleStatus) {
      super.draw(oSB, nPosX, nPosY, nWidth, titleStatus);

      try {
         oSB.setShader(Renderer.shaderAlpha);
         if (this.getFlagCivID() < 0) {
            ImageManager.getImage(Images.rebelsFlag).getTexture().bind(1);
         } else {
            Game.getCiv(this.getFlagCivID()).getFlag().getTexture().bind(1);
         }

         Gdx.gl.glActiveTexture(33984);
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 1.0F));
         ImageManager.getImage(Images.gradientHorizontal)
            .draw(oSB, nPosX, nPosY - this.getHeight() + Images.boxTitleBORDERWIDTH, this.flagWidth, this.flagHeight);
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
         this.getText2(),
         nPosX + this.flagWidth + CFG.PADDING,
         1 + nPosY - this.getHeight() + (this.getHeight() / 2 - this.getTextHeight()),
         this.getColorText(titleStatus)
      );
      Renderer.drawText(
         oSB,
         CFG.FONT_REGULAR_SMALL,
         this.getText(),
         nPosX + this.flagWidth + CFG.PADDING,
         1 + nPosY - this.getHeight() + this.getHeight() / 2 + CFG.PADDING,
         MenuTitle.colorHovered
      );
   }

   public int getFlagCivID() {
      return 0;
   }

   public String getText2() {
      return this.sText2;
   }
}
