package aoc.kingdoms.lukasz.menu_element.textStatic;

import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.MenuElement_Type;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Text_StaticGoods extends Text_Static {
   public Text_StaticGoods(String sText, int fontID, int iTextPositionX, int iPosX, int iPosY, int iWidth, int iHeight, int iCurrent) {
      this.typeOfElement = MenuElement_Type.TEXT;
      this.iCurrent = iCurrent;
      this.fontID = fontID;
      this.iTextPositionX = iTextPositionX;
      this.setPosX(iPosX);
      this.setPosY(iPosY);
      this.setWidth(iWidth);
      this.setHeight(iHeight);
      this.setText(sText);
      this.updateTextPosition();
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
      this.drawBG(oSB, iTranslateX, iTranslateY, isActive);
      Renderer.drawText(
         oSB,
         this.fontID,
         this.sText,
         this.getPosX() + this.textPosition.getTextPosition() + iTranslateX,
         this.getPosY() + (this.getHeight() - this.iTextHeight) / 2 + iTranslateY,
         this.getColor(isActive)
      );
   }

   public void drawBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.25F));
      ImageManager.getImage(Images.gradientVertical)
         .draw(oSB, this.getPosX() - 1 + this.getWidth() + iTranslateX, this.getPosY() + 1 + iTranslateY, 1, this.getHeight() / 2, false, true);
      ImageManager.getImage(Images.gradientVertical)
         .draw(oSB, this.getPosX() - 1 + this.getWidth() + iTranslateX, this.getPosY() - 1 + this.getHeight() / 2 + iTranslateY, 1, this.getHeight() / 2);
      ImageManager.getImage(Images.gradientVertical)
         .draw(oSB, this.getPosX() + 1 + this.getWidth() + iTranslateX, this.getPosY() + 1 + iTranslateY, 1, this.getHeight() / 2, false, true);
      ImageManager.getImage(Images.gradientVertical)
         .draw(oSB, this.getPosX() + 1 + this.getWidth() + iTranslateX, this.getPosY() - 1 + this.getHeight() / 2 + iTranslateY, 1, this.getHeight() / 2);
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE3.r, Colors.COLOR_GRADIENT_OVER_BLUE3.g, Colors.COLOR_GRADIENT_OVER_BLUE3.b, 0.9F));
      ImageManager.getImage(Images.gradientVertical)
         .draw(oSB, this.getPosX() + this.getWidth() + iTranslateX, this.getPosY() + 1 + iTranslateY, 1, this.getHeight() / 2, false, true);
      ImageManager.getImage(Images.gradientVertical)
         .draw(oSB, this.getPosX() + this.getWidth() + iTranslateX, this.getPosY() - 1 + this.getHeight() / 2 + iTranslateY, 1, this.getHeight() / 2);
      oSB.setColor(Color.WHITE);
   }

   @Override
   protected Color getColor(boolean isActive) {
      return Colors.getColorButtonHover(isActive, this.getIsHovered());
   }
}
