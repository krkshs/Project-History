package aoc.kingdoms.lukasz.menu_element.textStatic;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.button.Button;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TextIcon_Diplomacy extends Button {
   public int imageID;
   public int maxWidth;
   public long lTimeAnimation = 0L;
   public int animationState = 0;
   public final int ANIMATION_T = 1500;

   public TextIcon_Diplomacy(int imageID, int nPosX, int nPosY, int nWidth, int nHeight, int maxWidth) {
      this.init(null, CFG.FONT_REGULAR_SMALL, 0, nPosX, nPosY, nWidth, nHeight, true, true, false, false);
      this.imageID = imageID;
      this.maxWidth = maxWidth;
      this.lTimeAnimation = CFG.currentTimeMillis;
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      int tX = this.getPosX();
      int tWidth = this.getWidth();
      if (DiplomacyManager.inAnimation) {
         tWidth = (int)(this.getWidth() * DiplomacyManager.fAnimationPerc);
         tX = tX + this.getWidth() / 2 - tWidth / 2;
      }

      oSB.setColor(new Color(Colors.COLOR_GRADIENT_BG_BLUE.r, Colors.COLOR_GRADIENT_BG_BLUE.g, Colors.COLOR_GRADIENT_BG_BLUE.b, 0.5F));
      Renderer.drawBox(oSB, Images.statsRectBG, tX + iTranslateX, this.getPosY() + iTranslateY, tWidth, this.getHeight(), 1.0F);
      oSB.setColor(Color.WHITE);
      oSB.setColor(
         new Color(
            Colors.COLOR_GRADIENT_BG_BLUE.r,
            Colors.COLOR_GRADIENT_BG_BLUE.g,
            Colors.COLOR_GRADIENT_BG_BLUE.b,
            !this.getIsHovered() && !isActive ? 0.35F : 0.65F
         )
      );
      Renderer.drawBox(oSB, Images.statsRectBG, tX + iTranslateX, this.getPosY() + iTranslateY, tWidth, this.getHeight(), 1.0F);
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.3F));
      Images.gradientFull.draw(oSB, tX + iTranslateX, this.getPosY() + iTranslateY, tWidth, this.getHeight());
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.35F));
      Images.gradientFull.draw(oSB, tX + iTranslateX, this.getPosY() + iTranslateY, tWidth, this.getHeight());
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.3F));
      Images.gradientXY.draw(oSB, tX + iTranslateX, this.getPosY() + iTranslateY, tWidth, this.getHeight());
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.3F));
      Images.gradientXY.draw(oSB, tX + iTranslateX, this.getPosY() + iTranslateY, tWidth, this.getHeight());
      if (DiplomacyManager.inAnimation) {
         oSB.setColor(new Color(this.getColorBar().r, this.getColorBar().g, this.getColorBar().b, 0.65F * (1.0F - DiplomacyManager.fAnimationPerc)));
         Images.gradientFull.draw(oSB, tX + iTranslateX, this.getPosY() + iTranslateY, tWidth, this.getHeight());
         oSB.setColor(new Color(this.getColorBar().r, this.getColorBar().g, this.getColorBar().b, 0.45F + 0.6F * (1.0F - DiplomacyManager.fAnimationPerc)));
      } else {
         oSB.setColor(new Color(this.getColorBar().r, this.getColorBar().g, this.getColorBar().b, 0.45F));
      }

      Images.gradientXY.draw(oSB, tX + iTranslateX, this.getPosY() + iTranslateY, tWidth, this.getHeight());
      if (this.animationState >= 0) {
         if (this.animationState == 0) {
            float drawPerc = Math.min(1.0F * (float)(CFG.currentTimeMillis - this.lTimeAnimation) / 1500.0F, 1.0F);
            oSB.setColor(new Color(this.getColorBar().r, this.getColorBar().g, this.getColorBar().b, 0.55F * (1.0F - drawPerc)));
            Images.gradientXY.draw(oSB, tX + iTranslateX, this.getPosY() + this.getHeight() / 2 + iTranslateY, tWidth, this.getHeight() / 2);
            if (this.lTimeAnimation < CFG.currentTimeMillis - 1500L) {
               this.animationState++;
               this.lTimeAnimation = CFG.currentTimeMillis;
            }
         } else {
            float drawPerc = Math.min(1.0F * (float)(CFG.currentTimeMillis - this.lTimeAnimation) / 1500.0F, 1.0F);
            oSB.setColor(new Color(this.getColorBar().r, this.getColorBar().g, this.getColorBar().b, 0.55F * drawPerc));
            Images.gradientXY.draw(oSB, tX + iTranslateX, this.getPosY() + this.getHeight() / 2 + iTranslateY, tWidth, this.getHeight() / 2);
            if (this.lTimeAnimation < CFG.currentTimeMillis - 1500L) {
               this.animationState = 0;
               this.lTimeAnimation = CFG.currentTimeMillis;
            }
         }

         oSB.setColor(Color.WHITE);
      }

      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.25F));
      Images.gradientXY.draw(oSB, tX + iTranslateX, this.getPosY() + iTranslateY, tWidth, CFG.PADDING * 2, false, true);
      Images.gradientXY.draw(oSB, tX + iTranslateX, this.getPosY() + this.getHeight() - CFG.PADDING * 2 + iTranslateY, tWidth, CFG.PADDING * 2);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.25F));
      Renderer.drawBox(oSB, Images.statsRectBGBorder, tX + iTranslateX, this.getPosY() + iTranslateY, tWidth, this.getHeight(), 1.0F);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F));
      Images.gradientFull.draw(oSB, tX + iTranslateX, this.getPosY() + this.getHeight() - 1 + iTranslateY, tWidth, 1);
      Images.gradientFull.draw(oSB, tX + iTranslateX, this.getPosY() + iTranslateY, tWidth, 1);
      oSB.setColor(Color.WHITE);
   }

   public Color getColorBar() {
      return DiplomacyManager.COLOR_RED;
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      ImageManager.getImage(this.imageID)
         .draw(
            oSB,
            this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(this.imageID).getWidth() / 2 + iTranslateX,
            this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(this.imageID).getHeight() / 2 + iTranslateY
         );
   }

   @Override
   public boolean canBeHovered() {
      return false;
   }
}
