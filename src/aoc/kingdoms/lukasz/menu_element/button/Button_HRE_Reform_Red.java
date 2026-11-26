package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Button_HRE_Reform_Red extends Button_HRE_Reform {
   public Button_HRE_Reform_Red(String sText2, String sDesc, int iPosX, int iPosY, int nWidth, int imageID, int reformID) {
      super(sText2, sDesc, iPosX, iPosY, nWidth, imageID, reformID);
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      if (this.getIsHovered() || isActive) {
         Renderer.drawBoxCorner(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      }

      oSB.setColor(
         new Color(
            Colors.COLOR_NOTIFICATION_BG_RED.r,
            Colors.COLOR_NOTIFICATION_BG_RED.g,
            Colors.COLOR_NOTIFICATION_BG_RED.b,
            !this.getIsHovered() && !isActive ? 0.25F : 0.5F
         )
      );
      Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
      oSB.setColor(
         new Color(
            Colors.COLOR_GRADIENT_OVER_BG_RED.r,
            Colors.COLOR_GRADIENT_OVER_BG_RED.g,
            Colors.COLOR_GRADIENT_OVER_BG_RED.b,
            !this.getIsHovered() && !isActive ? 0.75F : 1.0F
         )
      );
      Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, getButtonHeight(), this.getHeight(), 1.0F);
      oSB.setColor(Color.WHITE);
      oSB.setColor(new Color(Colors.COLOR_NOTIFICATION_OVER_RED.r, Colors.COLOR_NOTIFICATION_OVER_RED.g, Colors.COLOR_NOTIFICATION_OVER_RED.b, 0.3F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      oSB.setColor(new Color(Colors.COLOR_NOTIFICATION_OVER_RED.r, Colors.COLOR_NOTIFICATION_OVER_RED.g, Colors.COLOR_NOTIFICATION_OVER_RED.b, 0.35F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      oSB.setColor(new Color(Colors.COLOR_NOTIFICATION_OVER_RED.r, Colors.COLOR_NOTIFICATION_OVER_RED.g, Colors.COLOR_NOTIFICATION_OVER_RED.b, 0.3F));
      Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.25F));
      Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), CFG.PADDING * 2, false, true);
      Images.gradientXY
         .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - CFG.PADDING * 2 + iTranslateY, this.getWidth(), CFG.PADDING * 2);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.25F));
      Renderer.drawBox(oSB, Images.statsRectBGBorder, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
      oSB.setColor(new Color(Colors.COLOR_NOTIFICATION_BG_RED.r, Colors.COLOR_NOTIFICATION_BG_RED.g, Colors.COLOR_NOTIFICATION_BG_RED.b, 0.25F));
      Renderer.drawBox(
         oSB, Images.statsRectBGBorder, this.getPosX() - 1 + iTranslateX, this.getPosY() - 1 + iTranslateY, this.getWidth() + 2, this.getHeight() + 2, 1.0F
      );
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 1 + iTranslateY, this.getWidth(), 1);
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), 1);
      oSB.setColor(new Color(Colors.COLOR_NOTIFICATION_OVER_RED.r, Colors.COLOR_NOTIFICATION_OVER_RED.g, Colors.COLOR_NOTIFICATION_OVER_RED.b, 0.85F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 2 + iTranslateY, this.getWidth(), 1);
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + 1 + iTranslateY, this.getWidth(), 1);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.55F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 1 + iTranslateY, this.getWidth(), 1);
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), 1);
      oSB.setColor(new Color(Colors.COLOR_NOTIFICATION_OVER_RED.r, Colors.COLOR_NOTIFICATION_OVER_RED.g, Colors.COLOR_NOTIFICATION_OVER_RED.b, 0.9F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 2 + iTranslateY, this.getWidth(), 1);
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + 1 + iTranslateY, this.getWidth(), 1);
      oSB.setColor(
         new Color(
            Colors.COLOR_GRADIENT_OVER_BG_RED.r,
            Colors.COLOR_GRADIENT_OVER_BG_RED.g,
            Colors.COLOR_GRADIENT_OVER_BG_RED.b,
            !this.getIsHovered() && !isActive ? 0.3F : 0.5F
         )
      );
      Renderer.drawBox(
         oSB,
         Images.statsRectBG,
         this.getPosX() + this.getInnerPosX() + CFG.PADDING + iTranslateX,
         this.getPosY() + CFG.PADDING + iTranslateY,
         this.getInnerWidth() - CFG.PADDING * 2,
         (getButtonHeight() - CFG.PADDING * 3) / 2,
         1.0F
      );
      oSB.setColor(Color.WHITE);
      this.drawImage(oSB, iTranslateX, iTranslateY, isActive);
   }
}
