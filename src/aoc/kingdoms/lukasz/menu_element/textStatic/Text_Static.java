package aoc.kingdoms.lukasz.menu_element.textStatic;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.GlyphLayout_Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.MenuElement_Type;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Text_Static extends MenuElement {
   protected String sText = null;
   protected int iTextWidth = -1;
   protected int iTextHeight = -1;
   protected int iTextPositionX;
   protected Text_Static.TextPosition textPosition;

   protected Text_Static() {
   }

   public Text_Static(String sText, int iPosX, int iPosY) {
      this.typeOfElement = MenuElement_Type.TEXT;
      this.setPosX(iPosX);
      this.setPosY(iPosY);
      this.setHeight(CFG.TEXT_HEIGHT);
      this.setText(sText);
      this.textPosition = new Text_Static.TextPosition() {
         @Override
         public int getTextPosition() {
            return 0;
         }
      };
   }

   public Text_Static(String sText, int iPosX, int iPosY, int fontID) {
      this.typeOfElement = MenuElement_Type.TEXT;
      this.fontID = fontID;
      this.setPosX(iPosX);
      this.setPosY(iPosY);
      this.setHeight(CFG.TEXT_HEIGHT);
      this.setText(sText);
      this.textPosition = new Text_Static.TextPosition() {
         @Override
         public int getTextPosition() {
            return 0;
         }
      };
   }

   public Text_Static(String sText, int iTextPositionX, int iPosX, int iPosY, int iHeight) {
      this.typeOfElement = MenuElement_Type.TEXT;
      this.iTextPositionX = iTextPositionX;
      this.setPosX(iPosX);
      this.setPosY(iPosY);
      this.setHeight(iHeight);
      this.setText(sText);
      this.updateTextPosition();
   }

   public Text_Static(String sText, int iTextPositionX, int iPosX, int iPosY, int iWidth, int iHeight) {
      this.typeOfElement = MenuElement_Type.TEXT;
      this.iTextPositionX = iTextPositionX;
      this.setPosX(iPosX);
      this.setPosY(iPosY);
      this.setWidth(iWidth);
      this.setHeight(iHeight);
      this.setText(sText);
      this.updateTextPosition();
   }

   public Text_Static(String sText, int fontID, int iTextPositionX, int iPosX, int iPosY, int iWidth, int iHeight) {
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

   public Text_Static(String sText, int fontID, int iTextPositionX, int iPosX, int iPosY, int iWidth, int iHeight, int nCurrent) {
      this.typeOfElement = MenuElement_Type.TEXT;
      this.fontID = fontID;
      this.iCurrent = nCurrent;
      this.iTextPositionX = iTextPositionX;
      this.setPosX(iPosX);
      this.setPosY(iPosY);
      this.setWidth(iWidth);
      this.setHeight(iHeight);
      this.setText(sText);
      this.updateTextPosition();
   }

   protected void updateTextPosition() {
      if (this.iTextPositionX < 0) {
         this.textPosition = new Text_Static.TextPosition() {
            @Override
            public int getTextPosition() {
               return Text_Static.this.getWidth() / 2 - Text_Static.this.iTextWidth / 2;
            }
         };
      } else {
         this.textPosition = new Text_Static.TextPosition() {
            @Override
            public int getTextPosition() {
               return Text_Static.this.iTextPositionX;
            }
         };
      }
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
      Renderer.drawTextWithShadow(
         oSB,
         this.fontID,
         this.sText,
         this.getPosX() + this.textPosition.getTextPosition() + iTranslateX,
         this.getPosY() + (this.getHeight() - CFG.TEXT_HEIGHT) / 2 + iTranslateY,
         this.getColor(isActive)
      );
   }

   protected Color getColor(boolean isActive) {
      return isActive
         ? new Color(0.56F, 0.56F, 0.56F, 1.0F)
         : (
            this.getClickable()
               ? (this.getIsHovered() ? new Color(0.68F, 0.68F, 0.68F, 1.0F) : new Color(0.82F, 0.82F, 0.82F, 1.0F))
               : new Color(0.78F, 0.78F, 0.78F, 0.7F)
         );
   }

   @Override
   public final String getText() {
      return this.sText;
   }

   @Override
   public void setText(String sText) {
      this.sText = sText;

      try {
         GlyphLayout_Game glyphLayout = new GlyphLayout_Game();
         glyphLayout.setText(Renderer.fontMain.get(this.fontID), sText);
         this.iTextWidth = (int)glyphLayout.width;
         this.iTextHeight = (int)glyphLayout.height;
         if (super.getWidth() < this.iTextWidth) {
            this.setWidth(this.iTextWidth);
         }

         if (this.getHeight() < this.iTextHeight) {
            this.setHeight(this.iTextHeight);
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   @Override
   public int getTextWidth() {
      return this.iTextWidth;
   }

   @Override
   public int getTextHeight() {
      return this.iTextHeight;
   }

   @Override
   public int getCurrent() {
      return this.iCurrent;
   }

   interface TextPosition {
      int getTextPosition();
   }
}
