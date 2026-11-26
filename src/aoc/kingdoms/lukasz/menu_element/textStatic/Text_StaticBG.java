package aoc.kingdoms.lukasz.menu_element.textStatic;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.MenuElement_Type;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Text_StaticBG extends Text_Static {
   public Text_StaticBG(String sText, int iTextPositionX, int iPosX, int iPosY, int iWidth, int iHeight) {
      this.typeOfElement = MenuElement_Type.TEXT;
      this.iTextPositionX = iTextPositionX;
      this.setPosX(iPosX);
      this.setPosY(iPosY);
      this.setWidth(iWidth);
      this.setHeight(iHeight);
      this.setText(sText);
      if (super.getWidth() < this.iTextWidth) {
         this.setWidth(this.iTextWidth);
      }

      this.updateTextPosition();
   }

   public Text_StaticBG(String sText, int fontID, int iTextPositionX, int iPosX, int iPosY, int iWidth, int iHeight) {
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

      while (this.iTextWidth >= this.getWidth() - (iTextPositionX > 0 ? iTextPositionX : 0) && this.getText().length() > 5) {
         if (++tWMax >= 100) {
            break;
         }

         this.setText(this.getText().substring(0, Math.max(1, this.getText().length() - 3)) + "..");
      }
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
      oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.5F));
      Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
      oSB.setColor(Color.WHITE);
      Renderer.drawTextWithShadow(
         oSB,
         this.fontID,
         this.sText,
         this.getPosX() + this.textPosition.getTextPosition() + iTranslateX,
         this.getPosY() + (this.getHeight() - CFG.TEXT_HEIGHT) / 2 + iTranslateY,
         this.getColor(isActive)
      );
   }

   @Override
   protected Color getColor(boolean isActive) {
      if (isActive) {
         return Colors.BUTTON_TEXT_ACTIVE;
      } else if (this.getIsHovered()) {
         return Colors.BUTTON_TEXT_HOVERED;
      } else {
         return this.getClickable() ? Colors.BUTTON_TEXT : Colors.BUTTON_TEXT_DISABLED;
      }
   }

   @Override
   public void setText(String sText) {
      this.sText = sText;

      try {
         Renderer.glyphLayout.setText(Renderer.fontMain.get(this.fontID), sText);
         this.iTextWidth = (int)Renderer.glyphLayout.width;
         this.iTextHeight = (int)Renderer.glyphLayout.height;
         if (this.getHeight() < this.iTextHeight) {
            this.setHeight(this.iTextHeight);
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }
}
