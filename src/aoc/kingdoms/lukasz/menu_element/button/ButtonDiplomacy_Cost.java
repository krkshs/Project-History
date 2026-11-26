package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.GlyphLayout_Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.textures.ImageManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ButtonDiplomacy_Cost extends ButtonDiplomacy {
   public String sValue;
   public int iTextBonusW;
   public int iTextBonusH;

   public ButtonDiplomacy_Cost(String sText, int imageID, int iPosX, int iPosY, int iWidth, int iHeight, String sValue) {
      super(sText, imageID, iPosX, iPosY, iWidth, iHeight);
      this.sValue = sValue;
      GlyphLayout_Game glyphLayout = new GlyphLayout_Game();
      glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_REGULAR_SMALL), sValue);
      this.iTextBonusW = (int)glyphLayout.width;
      this.iTextBonusH = (int)glyphLayout.height;
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      if (this.getClickable()) {
         ImageManager.getImage(this.imageID)
            .draw(
               oSB,
               this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(this.imageID).getWidth() / 2 + iTranslateX,
               this.getPosY() + this.iconY + iTranslateY
            );
      } else {
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.33F));
         ImageManager.getImage(this.imageID)
            .draw(
               oSB,
               this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(this.imageID).getWidth() / 2 + iTranslateX,
               this.getPosY() + this.iconY + iTranslateY
            );
         oSB.setColor(Color.WHITE);
      }

      Renderer.drawTextWithShadowRotated(
         oSB,
         CFG.FONT_REGULAR_SMALL,
         this.sValue,
         this.getPosX() + this.getWidth() - CFG.PADDING * 2 - this.iTextBonusH + iTranslateX,
         this.getPosY() + this.iconH - CFG.PADDING + iTranslateY,
         Colors.HOVER_RIGHT3,
         90.0F
      );

      for (int i = 0; i < this.iLineSize; i++) {
         Renderer.drawText(
            oSB,
            this.fontID,
            this.sLines.get(i),
            this.getPosX() + this.getWidth() / 2 - this.iLinesWidth.get(i) / 2 + iTranslateX,
            this.getPosY() + this.iY_Text + (this.iTextHeight + CFG.PADDING * 2) * i + iTranslateY,
            this.getColor(isActive)
         );
      }
   }

   public final float getImageScale(int iImageID) {
      return (float)CFG.TEXT_HEIGHT / ImageManager.getImage(iImageID).getHeight();
   }
}
