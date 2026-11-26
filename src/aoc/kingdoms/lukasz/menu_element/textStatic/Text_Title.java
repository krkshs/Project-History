package aoc.kingdoms.lukasz.menu_element.textStatic;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.GlyphLayout_Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.MenuElement_Type;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Text_Title extends Text_Static {
   public Text_Title(String sText, int iTextPositionX, int iPosX, int iPosY, int iWidth, int iHeight) {
      this.typeOfElement = MenuElement_Type.TEXT;
      this.fontID = CFG.FONT_BOLD;
      this.iTextPositionX = iTextPositionX;
      this.setPosX(iPosX);
      this.setPosY(iPosY);
      this.setWidth(iWidth);
      this.setHeight(iHeight);
      this.setText(sText);
      this.updateTextPosition();
      int tWMax = 0;

      while (this.iTextWidth > this.getWidth() - (iTextPositionX > 0 ? iTextPositionX : 0) && this.getText().length() > 5) {
         if (++tWMax >= 100) {
            break;
         }

         this.setText(this.getText().substring(0, Math.max(1, this.getText().length() - 3)) + "..");
      }
   }

   public Text_Title(String sText, int iTextPositionX, int iPosX, int iPosY, int iWidth, int iHeight, int fontID) {
      this.typeOfElement = MenuElement_Type.TEXT;
      this.fontID = fontID;
      this.iTextPositionX = iTextPositionX;
      this.setPosX(iPosX);
      this.setPosY(iPosY);
      this.setWidth(iWidth);
      this.setHeight(iHeight);
      this.setText(sText);
      this.updateTextPosition();
      int tWMax = 0;

      while (this.iTextWidth > this.getWidth() - (iTextPositionX > 0 ? iTextPositionX : 0) && this.getText().length() > 5) {
         if (++tWMax >= 100) {
            break;
         }

         this.setText(this.getText().substring(0, Math.max(1, this.getText().length() - 3)) + "..");
      }
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_BG.r, Colors.COLOR_GRADIENT_BG.g, Colors.COLOR_GRADIENT_BG.b, 0.9F));
      Images.pix.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER.r, Colors.COLOR_GRADIENT_OVER.g, Colors.COLOR_GRADIENT_OVER.b, 0.5F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER.r, Colors.COLOR_GRADIENT_OVER.g, Colors.COLOR_GRADIENT_OVER.b, 0.75F));
      Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F));
      ImageManager.getImage(Images.gradientVertical).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), CFG.PADDING * 2);
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER.r, Colors.COLOR_GRADIENT_OVER.g, Colors.COLOR_GRADIENT_OVER.b, 1.0F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + 1 + iTranslateY, this.getWidth(), 1);
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 2 + iTranslateY, this.getWidth(), 1);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 1.0F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), 1);
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 1 + iTranslateY, this.getWidth(), 1);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.75F));
      ImageManager.getImage(Images.gradientVertical)
         .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() + iTranslateY, this.getWidth(), CFG.PADDING);
      this.drawLines(oSB, iTranslateX, iTranslateY, isActive, scrollableY);
      oSB.setColor(Color.WHITE);
      Renderer.drawTextWithShadow(
         oSB,
         this.fontID,
         this.sText,
         this.getPosX() + this.getWidth() / 2 - this.getTextWidth() / 2 + iTranslateX,
         this.getPosY() + (this.getHeight() - this.getTextHeight()) / 2 + iTranslateY,
         this.getColor(isActive)
      );
   }

   public void drawLines(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
      oSB.setColor(new Color(Colors.HOVER_GOLD.r, Colors.HOVER_GOLD.g, Colors.HOVER_GOLD.b, 0.45F));
      ImageManager.getImage(Images.gradientHorizontal)
         .draw(
            oSB,
            this.getPosX() + iTranslateX,
            this.getPosY() + this.getHeight() / 2 + iTranslateY,
            this.getWidth() / 2 - this.getTextWidth() / 2 - CFG.PADDING * 2,
            1,
            true,
            false
         );
      ImageManager.getImage(Images.gradientHorizontal)
         .draw(
            oSB,
            this.getPosX() + this.getWidth() / 2 + this.getTextWidth() / 2 + CFG.PADDING * 2 + iTranslateX,
            this.getPosY() + this.getHeight() / 2 + iTranslateY,
            this.getWidth() / 2 - this.getTextWidth() / 2 - CFG.PADDING * 2,
            1
         );
   }

   @Override
   protected Color getColor(boolean isActive) {
      return Colors.getColorTopStats2(isActive, this.getIsHovered());
   }

   @Override
   public void setText(String sText) {
      this.sText = sText;

      try {
         GlyphLayout_Game glyphLayout = new GlyphLayout_Game();
         glyphLayout.setText(Renderer.fontMain.get(this.fontID), sText);
         this.iTextWidth = (int)glyphLayout.width;
         this.iTextHeight = (int)glyphLayout.height;
         if (this.getHeight() < this.iTextHeight) {
            this.setHeight(this.iTextHeight);
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }
}
