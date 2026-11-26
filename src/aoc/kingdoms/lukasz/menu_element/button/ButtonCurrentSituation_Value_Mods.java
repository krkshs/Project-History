package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ButtonCurrentSituation_Value_Mods extends ButtonCurrentSituation_Value {
   public ButtonCurrentSituation_Value_Mods(
      String sText, int imageID, int iPosX, int iPosY, int nWidth, int nHeight, int maxIconWidth, boolean row, int id, boolean checkbox
   ) {
      super(sText, imageID, iPosX, iPosY, nWidth, nHeight, maxIconWidth, row, id);
      this.setCheckboxState(checkbox);
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.5F));
      Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
      if (this.getCheckboxState()) {
         oSB.setColor(
            new Color(
               Colors.HOVER_POSITIVE.r, Colors.HOVER_POSITIVE.g, Colors.HOVER_POSITIVE.b, this.getBoxAlpha(this.getClickable(), this.getIsHovered(), isActive)
            )
         );
         Renderer.drawBox(
            oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.maxIconWidth + CFG.PADDING * 2, this.getHeight(), 1.0F
         );
         oSB.setColor(new Color(Colors.HOVER_POSITIVE.r, Colors.HOVER_POSITIVE.g, Colors.HOVER_POSITIVE.b, 0.75F));
         Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.maxIconWidth + CFG.PADDING * 2, this.getHeight());
      } else {
         oSB.setColor(
            new Color(
               Colors.HOVER_NEGATIVE.r, Colors.HOVER_NEGATIVE.g, Colors.HOVER_NEGATIVE.b, this.getBoxAlpha(this.getClickable(), this.getIsHovered(), isActive)
            )
         );
         Renderer.drawBox(
            oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.maxIconWidth + CFG.PADDING * 2, this.getHeight(), 1.0F
         );
         oSB.setColor(new Color(Colors.HOVER_NEGATIVE.r, Colors.HOVER_NEGATIVE.g, Colors.HOVER_NEGATIVE.b, 0.75F));
         Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.maxIconWidth + CFG.PADDING * 2, this.getHeight());
      }

      oSB.setColor(Color.WHITE);
      if (this.row) {
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.4F));
         Images.line_32_off1.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + 1 - Images.line_32_off1.getHeight() + iTranslateY, this.getWidth(), 1);
         Images.line_32_off1
            .draw(
               oSB,
               this.getPosX() + iTranslateX,
               this.getPosY() + this.getHeight() - 2 - Images.line_32_off1.getHeight() + iTranslateY,
               this.getWidth(),
               1,
               false,
               true
            );
         oSB.setColor(new Color(Colors.COLOR_GRADIENT_TITLE_BLUE.r, Colors.COLOR_GRADIENT_TITLE_BLUE.g, Colors.COLOR_GRADIENT_TITLE_BLUE.b, 0.35F));
         Images.line_32_off1.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - Images.line_32_off1.getHeight() + iTranslateY, this.getWidth(), 1);
         Images.line_32_off1
            .draw(
               oSB,
               this.getPosX() + iTranslateX,
               this.getPosY() + this.getHeight() - 1 - Images.line_32_off1.getHeight() + iTranslateY,
               this.getWidth(),
               1,
               false,
               true
            );
      } else {
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.4F));
         Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), 1);
         Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 1 + iTranslateY, this.getWidth(), 1);
      }

      if (isActive || this.getIsHovered()) {
         oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.325F));
         ImageManager.getImage(Images.gradientHorizontal)
            .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      }

      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.55F));
      ImageManager.getImage(Images.gradientXYVertical).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, CFG.PADDING * 2, this.getHeight());
      ImageManager.getImage(Images.gradientXYVertical)
         .draw(oSB, this.getPosX() + this.maxIconWidth + iTranslateX, this.getPosY() + iTranslateY, CFG.PADDING * 2, this.getHeight(), true, false);
      ImageManager.getImage(Images.gradientXYVertical)
         .draw(oSB, this.getPosX() + this.maxIconWidth + CFG.PADDING * 2 + iTranslateX, this.getPosY() + iTranslateY, CFG.PADDING * 2, this.getHeight());
      oSB.setColor(new Color(Colors.COLOR_BOX_GOLD.r, Colors.COLOR_BOX_GOLD.g, Colors.COLOR_BOX_GOLD.b, 0.5F));
      ImageManager.getImage(Images.gradientVertical)
         .draw(oSB, this.getPosX() + 1 + iTranslateX, this.getPosY() + iTranslateY, 1, this.getHeight() / 2, false, true);
      ImageManager.getImage(Images.gradientVertical)
         .draw(oSB, this.getPosX() + 1 + iTranslateX, this.getPosY() + this.getHeight() / 2 + iTranslateY, 1, this.getHeight() / 2);
      ImageManager.getImage(Images.gradientVertical)
         .draw(oSB, this.getPosX() + this.maxIconWidth + CFG.PADDING * 2 - 2 + iTranslateX, this.getPosY() + iTranslateY, 1, this.getHeight() / 2, false, true);
      ImageManager.getImage(Images.gradientVertical)
         .draw(
            oSB,
            this.getPosX() + this.maxIconWidth + CFG.PADDING * 2 - 2 + iTranslateX,
            this.getPosY() + this.getHeight() / 2 + iTranslateY,
            1,
            this.getHeight() / 2
         );
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.4F));
      ImageManager.getImage(Images.gradientVertical)
         .draw(oSB, this.getPosX() + 2 + iTranslateX, this.getPosY() + iTranslateY, 1, this.getHeight() / 2, false, true);
      ImageManager.getImage(Images.gradientVertical)
         .draw(oSB, this.getPosX() + 2 + iTranslateX, this.getPosY() + this.getHeight() / 2 + iTranslateY, 1, this.getHeight() / 2);
      ImageManager.getImage(Images.gradientVertical)
         .draw(oSB, this.getPosX() + this.maxIconWidth + CFG.PADDING * 2 - 3 + iTranslateX, this.getPosY() + iTranslateY, 1, this.getHeight() / 2, false, true);
      ImageManager.getImage(Images.gradientVertical)
         .draw(
            oSB,
            this.getPosX() + this.maxIconWidth + CFG.PADDING * 2 - 3 + iTranslateX,
            this.getPosY() + this.getHeight() / 2 + iTranslateY,
            1,
            this.getHeight() / 2
         );
      oSB.setColor(Color.WHITE);
   }
}
