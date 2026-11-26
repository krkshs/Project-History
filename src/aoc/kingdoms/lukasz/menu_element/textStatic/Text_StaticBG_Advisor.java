package aoc.kingdoms.lukasz.menu_element.textStatic;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.MenuElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class Text_StaticBG_Advisor extends Text_Static {
   public Text_StaticBG_Advisor(String sText, int iPosX, int iPosY, int iWidth, int iHeight) {
      this.typeOfElement = MenuElement_Type.TEXT;
      this.fontID = CFG.FONT_REGULAR_SMALL;
      this.iTextPositionX = CFG.PADDING * 2;
      this.setPosX(iPosX);
      this.setPosY(iPosY);
      this.setWidth(iWidth);
      this.setHeight(iHeight);
      this.setText(sText);
      this.updateTextPosition();
      int tWMax = 0;

      while (this.iTextWidth > this.getWidth() - CFG.PADDING * 4 && this.getText().length() > 4) {
         if (++tWMax >= 100) {
            break;
         }

         this.setText(this.getText().substring(0, Math.max(1, this.getText().length() - 3)) + ".");
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
         this.getTextToDraw(),
         this.getPosX() + CFG.PADDING * 2 + iTranslateX,
         this.getPosY() + this.getHeight() / 2 - this.getTextHeight() / 2 + iTranslateY,
         this.getColor(isActive)
      );
   }

   @Override
   public String getTextToDraw() {
      return this.sText;
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

   @Override
   public void buildElementHover() {
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      nData.add(new MenuElement_HoverElement_Type_TextTitle(this.getText(), CFG.FONT_BOLD, Colors.HOVER_GOLD));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      this.menuElementHover = new MenuElement_Hover(nElements);
   }

   @Override
   protected Color getColor(boolean isActive) {
      return Colors.getColorButtonHover(isActive, this.getIsHovered());
   }

   protected Color getColor2(boolean isActive) {
      return Colors.getColorButtonHover(isActive, this.getIsHovered());
   }
}
