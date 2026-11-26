package aoc.kingdoms.lukasz.menu_element;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.GlyphLayout_Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class IconCourt_Notification extends IconCourt {
   public int value;
   public String number = "";
   public int numW;
   public int numH;
   public int boxW = 0;
   public int boxH = 0;

   public IconCourt_Notification(String sText, int imageID, int nPosX, int nPosY, int nWidth, int nHeight, int id, int widthDraw) {
      super(sText, imageID, nPosX, nPosY, nWidth, nHeight, id, widthDraw);
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      super.drawButtonBG(oSB, iTranslateX, iTranslateY, isActive);
      this.updateValue();
      if (this.value > 0) {
         oSB.setColor(
            new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, !this.getIsHovered() && !isActive ? 0.5F : 0.6F)
         );
         Renderer.drawBox(
            oSB,
            Images.statsRectBG,
            this.getPosX() + CFG.PADDING + iTranslateX,
            this.getPosY() + this.getHeight() - CFG.PADDING - this.boxH + iTranslateY,
            this.boxW,
            this.boxH,
            1.0F
         );
         oSB.setColor(Color.WHITE);
         Renderer.drawText(
            oSB,
            this.fontID,
            this.number,
            this.getPosX() + CFG.PADDING + this.boxW / 2 - this.numW / 2 + iTranslateX,
            this.getPosY() + this.getHeight() - CFG.PADDING - this.boxH / 2 - this.numH / 2 + iTranslateY,
            this.getColor(isActive)
         );
      }
   }

   public void updateValue() {
   }

   public void setNumber(int iNum) {
      this.value = iNum;
      if (this.value <= 0) {
         this.boxW = this.boxH = 0;
      } else {
         this.number = "" + Math.min(GameValues.court.SIDEBAR_MAX_NUMBER, this.value);

         try {
            if (this.number != null) {
               GlyphLayout_Game glyphLayout = new GlyphLayout_Game();
               glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_REGULAR_SMALL), this.number);
               this.numW = (int)glyphLayout.width;
               this.numH = (int)glyphLayout.height;
            } else {
               this.numW = this.numH = 0;
            }
         } catch (Exception var3) {
         }

         this.boxW = this.numW + CFG.PADDING * 2;
         this.boxH = this.numH + CFG.PADDING * 2;
      }
   }
}
