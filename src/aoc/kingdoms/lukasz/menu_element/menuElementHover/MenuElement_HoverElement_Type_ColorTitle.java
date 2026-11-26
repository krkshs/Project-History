package aoc.kingdoms.lukasz.menu_element.menuElementHover;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuElement_HoverElement_Type_ColorTitle implements MenuElement_HoverElement_Type {
   private Color oColor;
   private int offsetLeft = 0;
   private int offsetRight = 0;
   private int iHeight = 0;

   public MenuElement_HoverElement_Type_ColorTitle(Color oColor) {
      this.init(oColor, 0, 0, (int)(CFG.TEXT_HEIGHT * 0.9F));
   }

   public MenuElement_HoverElement_Type_ColorTitle(Color oColor, int offsetLeft) {
      this.init(oColor, offsetLeft, CFG.PADDING, (int)(CFG.TEXT_HEIGHT * 0.9F));
   }

   public MenuElement_HoverElement_Type_ColorTitle(Color oColor, int offsetLeft, int offsetRight) {
      this.init(oColor, offsetLeft, offsetRight, (int)(CFG.TEXT_HEIGHT * 0.9F));
   }

   public MenuElement_HoverElement_Type_ColorTitle(Color oColor, int offsetLeft, int offsetRight, int nColorHeight) {
      this.init(oColor, offsetLeft, offsetRight, nColorHeight);
   }

   private final void init(Color oColor, int offsetLeft, int offsetRight, int nHeight) {
      this.oColor = oColor;
      this.offsetLeft = offsetLeft;
      this.offsetRight = offsetRight;
      this.iHeight = nHeight;
   }

   @Override
   public void draw(SpriteBatch oSB, int nPosX, int nPosY, float nAlpha, int iMaxWidth) {
      oSB.setColor(new Color(this.oColor.r, this.oColor.g, this.oColor.b, nAlpha));
      Images.pix.draw(oSB, nPosX + this.offsetLeft, nPosY + CFG.PADDING * 2, CFG.COLOR_WIDTH, CFG.TEXT_HEIGHT);
      oSB.setColor(Color.WHITE);
   }

   @Override
   public int getWidth() {
      return this.offsetRight + this.offsetLeft + CFG.COLOR_WIDTH;
   }

   @Override
   public int getHeight() {
      return CFG.TEXT_HEIGHT;
   }
}
