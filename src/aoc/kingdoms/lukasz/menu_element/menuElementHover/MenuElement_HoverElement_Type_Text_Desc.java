package aoc.kingdoms.lukasz.menu_element.menuElementHover;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.GlyphLayout_Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class MenuElement_HoverElement_Type_Text_Desc implements MenuElement_HoverElement_Type {
   public List<String> sLines = new ArrayList<>();
   public int iLineSize = 0;
   public int iTextWidth = 0;
   public int iTextHeight = 0;
   private Color oColor;
   private int fontID = 0;

   public MenuElement_HoverElement_Type_Text_Desc(String sText) {
      this.init(sText, CFG.FONT_BOLD_SMALL, Colors.HOVER_LEFT);
   }

   public MenuElement_HoverElement_Type_Text_Desc(String sText, int fontID) {
      this.init(sText, fontID, Colors.HOVER_LEFT);
   }

   public MenuElement_HoverElement_Type_Text_Desc(String sText, Color nColor) {
      this.init(sText, CFG.FONT_BOLD_SMALL, nColor);
   }

   public MenuElement_HoverElement_Type_Text_Desc(String sText, int fontID, Color nColor) {
      this.init(sText, fontID, nColor);
   }

   public final void init(String sText, int nFontID, Color oColor) {
      this.oColor = oColor;
      this.fontID = nFontID;
      String[] words = sText.split(" ");
      int textPosX = 0;
      int maxW = (int)(ImageManager.getImage(Images.title1Red).getWidth() * 0.85F);
      String currentLine = "";
      int tTextWidth = 0;
      int i = 0;

      for (int iSize = words.length; i < iSize; i++) {
         Renderer.glyphLayout.setText(Renderer.fontMain.get(this.fontID), words[i] + " ");
         tTextWidth = (int)Renderer.glyphLayout.width;
         textPosX += tTextWidth;
         if (textPosX < maxW) {
            currentLine = currentLine + words[i] + " ";
            this.iTextWidth = Math.max(this.iTextWidth, Math.min(textPosX, maxW));
         } else {
            this.sLines.add(currentLine);
            currentLine = words[i] + " ";
            textPosX = tTextWidth;
         }
      }

      if (currentLine.length() > 0) {
         this.sLines.add(currentLine);
      }

      if (this.sLines.size() > 0 && this.sLines.get(0).length() > 0) {
         Renderer.glyphLayout.setText(Renderer.fontMain.get(this.fontID), this.sLines.get(0));
         this.iTextHeight = (int)Renderer.glyphLayout.height;
      }

      this.iLineSize = this.sLines.size();

      for (int ix = 0; ix < this.iLineSize; ix++) {
         GlyphLayout_Game glyphLayout = new GlyphLayout_Game();
         glyphLayout.setText(Renderer.fontMain.get(this.fontID), this.sLines.get(ix));
         if (glyphLayout.width > this.iTextWidth) {
            this.iTextWidth = (int)glyphLayout.width;
         }
      }
   }

   @Override
   public void draw(SpriteBatch oSB, int nPosX, int nPosY, float nAlpha, int iMaxWidth) {
      for (int i = 0; i < this.iLineSize; i++) {
         Renderer.drawTextWithShadow(
            oSB,
            this.fontID,
            this.sLines.get(i),
            nPosX,
            nPosY + CFG.PADDING + CFG.PADDING / 2 + (int)((CFG.TEXT_HEIGHT - CFG.TEXT_HEIGHT * 0.9F) / 2.0F) + (this.iTextHeight + CFG.PADDING * 2) * i,
            new Color(this.oColor.r, this.oColor.g, this.oColor.b, nAlpha)
         );
      }
   }

   @Override
   public int getWidth() {
      return this.iTextWidth;
   }

   @Override
   public int getHeight() {
      return CFG.PADDING + CFG.TEXT_HEIGHT_SMALL * this.iLineSize + CFG.PADDING * 2 * (this.iLineSize - 1);
   }
}
