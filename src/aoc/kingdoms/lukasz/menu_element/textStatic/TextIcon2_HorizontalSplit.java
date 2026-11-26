package aoc.kingdoms.lukasz.menu_element.textStatic;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.button.Button;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class TextIcon2_HorizontalSplit extends Button {
   public List<String> sLines = new ArrayList<>();
   public int iLineSize = 0;
   public int imageID;
   public int iconWidth;
   public int iconHeight;
   public int maxIconWidth;
   public int iY = 0;
   public int maxWidth = 0;

   public TextIcon2_HorizontalSplit(String sText, int imageID, int iPosX, int iPosY, int nWidth, int nHeight, int maxIconWidth, int maxWidth) {
      this.init(sText, CFG.FONT_REGULAR_SMALL, 0, iPosX, iPosY, nWidth, nHeight, true, true, false, false);
      this.imageID = imageID;
      this.maxIconWidth = maxIconWidth;
      this.maxWidth = maxWidth;
      this.iconWidth = ImageManager.getImage(imageID).getWidth();
      this.iconHeight = ImageManager.getImage(imageID).getHeight();
      String[] words = sText.split(" ");
      int textPosX = 0;
      int maxW = nWidth - this.getIconBGWidth() - CFG.PADDING * 2;
      String currentLine = "";
      int i = 0;

      for (int iSize = words.length; i < iSize; i++) {
         Renderer.glyphLayout.setText(Renderer.fontMain.get(this.fontID), words[i] + " ");
         this.iTextWidth = (int)Renderer.glyphLayout.width;
         textPosX += this.iTextWidth;
         if (textPosX < maxW) {
            currentLine = currentLine + words[i] + " ";
         } else {
            this.sLines.add(currentLine);
            currentLine = words[i] + " ";
            textPosX = this.iTextWidth;
         }
      }

      if (currentLine.length() > 0) {
         this.sLines.add(currentLine);
      }

      if (this.sLines.size() > 0 && this.sLines.get(0).length() > 0) {
         Renderer.glyphLayout.setText(Renderer.fontMain.get(this.fontID), this.sLines.get(0));
         this.iTextHeight = (int)Renderer.glyphLayout.height;
      }

      this.iLineSize = Math.min(3, this.sLines.size());
      this.setHeight(Math.max(this.getHeight(), CFG.PADDING * 3 + this.iTextHeight * this.iLineSize + CFG.PADDING * 2 * (this.iLineSize - 1)));
      this.iY = this.getHeight() / 2 - (this.iTextHeight * this.iLineSize + CFG.PADDING * 2 * (this.iLineSize - 1)) / 2;
   }

   public int getIconBGWidth() {
      return this.maxIconWidth + CFG.PADDING * 2;
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      oSB.setColor(
         new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, !this.getIsHovered() && !isActive ? 0.5F : 0.6F)
      );
      Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.maxWidth, this.getHeight(), 1.0F);
      oSB.setColor(Color.WHITE);
      oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.3F));
      Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getIconBGWidth(), this.getHeight(), 1.0F);
      oSB.setColor(Color.WHITE);
      oSB.setColor(getColor_gradientXY());
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 1 + iTranslateY, this.maxWidth, 1);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 2 + iTranslateY, this.maxWidth, 1);
      oSB.setColor(Color.WHITE);
   }

   public static Color getColor_gradientXY() {
      return new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.85F);
   }

   public static final float getBoxAlpha(boolean clickable, boolean isHovered, boolean isActive) {
      return clickable ? (isActive ? 0.85F : (isHovered ? 0.7F : 0.5F)) : 0.2F;
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      ImageManager.getImage(this.imageID)
         .draw(
            oSB,
            this.getPosX() + CFG.PADDING + this.maxIconWidth / 2 - this.iconWidth / 2 + iTranslateX,
            this.getPosY() + (this.getHeight() - this.iconHeight) / 2 + iTranslateY,
            this.iconWidth,
            this.iconHeight
         );

      for (int i = 0; i < this.iLineSize; i++) {
         Renderer.drawTextWithShadow(
            oSB,
            this.fontID,
            this.sLines.get(i),
            this.getPosX() + this.getIconBGWidth() + CFG.PADDING * 2 + iTranslateX,
            this.getPosY() + this.iY + (this.iTextHeight + CFG.PADDING * 2) * i + iTranslateY,
            this.getColor(isActive)
         );
      }
   }

   private final float getImageScale(int iImageID) {
      return (float)CFG.TEXT_HEIGHT / ImageManager.getImage(iImageID).getHeight();
   }

   @Override
   protected Color getColor(boolean isActive) {
      return Colors.getColorButtonHover(isActive, this.getIsHovered());
   }

   @Override
   public void buildElementHover() {
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      String tText = "";

      for (int i = 0; i < this.sLines.size(); i++) {
         tText = tText + this.sLines.get(i);
      }

      nData.add(
         new MenuElement_HoverElement_Type_Button_TextBonus(
            tText, "", this.imageID, CFG.FONT_BOLD_SMALL, CFG.FONT_BOLD_SMALL, Colors.HOVER_LEFT, Colors.HOVER_GOLD
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      this.menuElementHover = new MenuElement_Hover(nElements);
   }
}
