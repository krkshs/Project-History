package aoc.kingdoms.lukasz.menu_element.textStatic;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.MenuElement_Type;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TextTop2 extends MenuElement {
   public String sText = null;
   public int iTextWidth = -1;
   public int iTextHeight = -1;
   public int iTextPosX;
   public int imageID;

   public TextTop2(int imageID, String sText, int iPosX, int iPosY) {
      this.typeOfElement = MenuElement_Type.TEXT;
      this.fontID = CFG.FONT_REGULAR_SMALL;
      this.imageID = imageID;
      this.setPosX(iPosX);
      this.setPosY(iPosY);
      this.setHeight(CFG.TEXT_HEIGHT * 2 + CFG.PADDING * 3);
      this.setText(sText);
      this.iTextPosX = TextTop.EXTRA_WIDTH_BOX_PADDING + ImageManager.getImage(imageID).getWidth() + CFG.PADDING * 2;
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
      oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.5F));
      Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
      oSB.setColor(Color.WHITE);
      ImageManager.getImage(this.imageID)
         .draw(
            oSB,
            this.getPosX() + TextTop.EXTRA_WIDTH_BOX_PADDING + iTranslateX,
            this.getPosY() + (this.getHeight() - ImageManager.getImage(this.imageID).getHeight()) / 2 + iTranslateY
         );
      Renderer.drawTextWithShadow(
         oSB,
         this.fontID,
         this.sText,
         this.getPosX() + this.iTextPosX + iTranslateX,
         this.getPosY() + this.getHeight() / 2 - this.getTextHeight() / 2 + iTranslateY,
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

   protected Color getColor2(boolean isActive) {
      return this.getColor(isActive);
   }

   @Override
   public final String getText() {
      return this.sText;
   }

   @Override
   public void setText(String sText) {
      this.sText = sText;

      try {
         Renderer.glyphLayout.setText(Renderer.fontMain.get(this.fontID), sText);
         this.iTextWidth = (int)Renderer.glyphLayout.width;
         this.iTextHeight = (int)Renderer.glyphLayout.height;
         if (super.getWidth() < this.iTextWidth + this.extraWidth()) {
            this.setWidth(this.iTextWidth + this.extraWidth());
         }
      } catch (NullPointerException var3) {
         CFG.exceptionStack(var3);
      } catch (IndexOutOfBoundsException var4) {
         CFG.exceptionStack(var4);
      } catch (IllegalArgumentException var5) {
         CFG.exceptionStack(var5);
      }
   }

   public final void setWidthOfButton() {
      this.setWidth(this.extraWidth());
   }

   public final int extraWidth() {
      return ImageManager.getImage(this.imageID).getWidth() + CFG.PADDING * 2 + TextTop.EXTRA_WIDTH_BOX_PADDING * 2;
   }

   @Override
   public int getTextWidth() {
      return this.iTextWidth;
   }

   @Override
   public int getTextHeight() {
      return this.iTextHeight;
   }
}
