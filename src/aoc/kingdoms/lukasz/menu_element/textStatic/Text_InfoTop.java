package aoc.kingdoms.lukasz.menu_element.textStatic;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu_element.MenuElement_Type;
import aoc.kingdoms.lukasz.menusInGame.Info.InGame_Info;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Text_InfoTop extends Text_Static {
   public Color colorText = new Color(0.17254902F, 0.09019608F, 0.05490196F, 1.0F);
   public Color colorTextHover = new Color(0.11764706F, 0.039215688F, 0.023529412F, 1.0F);
   public Color colorTextActive = new Color(0.08235294F, 0.023529412F, 0.007843138F, 1.0F);

   public Text_InfoTop(String sText, int iTextPositionX, int iPosX, int iPosY, int iWidth, int iHeight) {
      this.typeOfElement = MenuElement_Type.TEXT;
      this.fontID = CFG.FONT_BOLD;
      this.iTextPositionX = iTextPositionX;
      this.setPosX(iPosX);
      this.setPosY(iPosY);
      this.setWidth(iWidth);
      this.setHeight(iHeight);
      this.setText(sText);
      this.updateTextPosition();
   }

   public Text_InfoTop(String sText, int fontID, int iTextPositionX, int iPosX, int iPosY, int iWidth, int iHeight) {
      this.typeOfElement = MenuElement_Type.TEXT;
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
      Renderer.drawText(
         oSB,
         this.fontID,
         this.sText,
         this.getPosX() + this.getWidth() / 2 - this.getTextWidth() / 2 + iTranslateX,
         this.getPosY() + (this.getHeight() - this.iTextHeight) / 2 + iTranslateY,
         this.getColor(isActive)
      );
   }

   @Override
   protected Color getColor(boolean isActive) {
      if (isActive) {
         return new Color(this.colorTextActive.r, this.colorTextActive.g, this.colorTextActive.b, 1.0F * InGame_Info.fAnimationPerc);
      } else {
         return this.getIsHovered()
            ? new Color(this.colorTextHover.r, this.colorTextHover.g, this.colorTextHover.b, 1.0F * InGame_Info.fAnimationPerc)
            : new Color(this.colorText.r, this.colorText.g, this.colorText.b, 1.0F * InGame_Info.fAnimationPerc);
      }
   }
}
