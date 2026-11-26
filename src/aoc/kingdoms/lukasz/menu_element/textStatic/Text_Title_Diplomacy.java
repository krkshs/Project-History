package aoc.kingdoms.lukasz.menu_element.textStatic;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.GlyphLayout_Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.MenuElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class Text_Title_Diplomacy extends Text_Static {
   protected static long lTimeAnimation = 0L;
   protected static int animationState = 0;
   protected static final int ANIMATION_T = 2000;
   public boolean isActiveButton;

   public Text_Title_Diplomacy(String sText, int iPosX, int iPosY, int iWidth, int iHeight, boolean isActiveButton) {
      this.typeOfElement = MenuElement_Type.TEXT;
      this.fontID = CFG.FONT_BOLD;
      this.isActiveButton = isActiveButton;
      this.iTextPositionX = -1;
      this.setPosX(iPosX);
      this.setPosY(iPosY);
      this.setWidth(iWidth);
      this.setHeight(iHeight);
      this.setText(sText);
      int tWMax = 0;

      while (this.iTextWidth > this.getWidth() && this.getText().length() > 5) {
         if (++tWMax >= 100) {
            break;
         }

         this.setText(this.getText().substring(0, Math.max(1, this.getText().length() - 3)) + "..");
      }

      this.updateTextPosition();
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_BG_BLUE.r, Colors.COLOR_GRADIENT_BG_BLUE.g, Colors.COLOR_GRADIENT_BG_BLUE.b, 0.9F));
      Images.pix.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      if (this.isActiveButton) {
         oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.55F));
         Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
         oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.95F));
         Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      } else {
         oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.4F));
         Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      }

      if (this.getIsHovered()) {
         oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE3.r, Colors.COLOR_GRADIENT_OVER_BLUE3.g, Colors.COLOR_GRADIENT_OVER_BLUE3.b, 0.75F));
         Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      }

      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.2F));
      ImageManager.getImage(Images.gradientVertical).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), CFG.PADDING * 2);
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 1.0F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + 1 + iTranslateY, this.getWidth(), 1);
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 2 + iTranslateY, this.getWidth(), 1);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 1.0F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), 1);
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 1 + iTranslateY, this.getWidth(), 1);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.75F));
      ImageManager.getImage(Images.gradientVertical)
         .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() + iTranslateY, this.getWidth(), CFG.PADDING);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.1F));
      ImageManager.getImage(Images.gradientVertical)
         .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - CFG.PADDING + iTranslateY, this.getWidth(), CFG.PADDING, false, true);
      if (animationState >= 0) {
         if (animationState == 0) {
            float drawPerc = Math.min(1.0F * (float)(CFG.currentTimeMillis - lTimeAnimation) / 2000.0F, 1.0F);
            oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.125F));
            Images.line_32_off1
               .draw(
                  oSB,
                  this.getPosX() + iTranslateX - MenuElement_Hover.getDrawExtraXPos(),
                  this.getPosY() + iTranslateY + this.getHeight() - 2,
                  (int)((this.getWidth() + MenuElement_Hover.getDrawExtraXPos() * 2) * drawPerc),
                  1
               );
            if (lTimeAnimation < CFG.currentTimeMillis - 2000L) {
               animationState++;
               lTimeAnimation = CFG.currentTimeMillis;
            }
         } else {
            float drawPerc = Math.min(1.0F * (float)(CFG.currentTimeMillis - lTimeAnimation) / 2000.0F, 1.0F);
            oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.125F));
            Images.line_32_off1
               .draw(
                  oSB,
                  this.getPosX()
                     + iTranslateX
                     - MenuElement_Hover.getDrawExtraXPos()
                     + (int)((this.getWidth() + MenuElement_Hover.getDrawExtraXPos() * 2) * drawPerc),
                  this.getPosY() + iTranslateY + this.getHeight() - 2,
                  this.getWidth() + MenuElement_Hover.getDrawExtraXPos() * 2 - (int)((this.getWidth() + MenuElement_Hover.getDrawExtraXPos() * 2) * drawPerc),
                  1
               );
            if (lTimeAnimation < CFG.currentTimeMillis - 2000L) {
               animationState = 0;
               lTimeAnimation = CFG.currentTimeMillis;
            }
         }

         oSB.setColor(Color.WHITE);
      }

      oSB.setColor(Color.WHITE);
      Renderer.drawTextWithShadow(
         oSB,
         this.fontID,
         this.sText,
         this.getPosX() + this.textPosition.getTextPosition() + iTranslateX,
         this.getPosY() + (this.getHeight() - this.getTextHeight()) / 2 + iTranslateY,
         this.getColor(isActive)
      );
   }

   @Override
   protected Color getColor(boolean isActive) {
      return this.isActiveButton ? Colors.getColorTopStats2(isActive, this.getIsHovered()) : Colors.getColorTopStats5(isActive, this.getIsHovered());
   }

   @Override
   public void buildElementHover() {
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(this.getText(), CFG.FONT_BOLD, Colors.HOVER_GOLD));
      nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.diplomacy, CFG.PADDING, 0));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      this.menuElementHover = new MenuElement_Hover(nElements, true);
   }

   @Override
   public void setText(String sText) {
      this.sText = sText;

      try {
         GlyphLayout_Game glyphLayout = new GlyphLayout_Game();
         glyphLayout.setText(Renderer.fontMain.get(this.fontID), sText);
         this.iTextWidth = (int)glyphLayout.width;
         this.iTextHeight = (int)glyphLayout.height;
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }
}
