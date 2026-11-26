package aoc.kingdoms.lukasz.menu_element.graph;

import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Graph_Vertical_Data_Value {
   protected int iValue;
   protected int iHeight;
   protected static final float ALPHA = 0.35F;
   protected static final float ALPHA_GRADIENT = 0.7F;
   protected static final float ALPHA_GRADIENT2 = 0.35F;
   protected static final Color COLOR_VALUE_BORDER = new Color(0.9F, 0.9F, 0.9F, 0.1F);
   protected int iColorDataID = 0;

   protected Graph_Vertical_Data_Value(int iValue, int iColorDataID) {
      this.iValue = iValue;
      this.iColorDataID = iColorDataID;
   }

   protected void draw(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, int nHeight, Color nColor) {
      this.drawData(oSB, nPosX, nPosY, nWidth, nHeight, this.iHeight, nColor);
   }

   protected void draw(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, int nHeight, int nAnimationHeight, Color nColor) {
      this.drawData(oSB, nPosX, nPosY, nWidth, nHeight, nAnimationHeight, nColor);
   }

   private void drawData(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, int nHeight, int nHeight2, Color nColor) {
      oSB.setColor(new Color(nColor.r, nColor.g, nColor.b, 0.35F));
      Images.pix.draw(oSB, nPosX, nPosY - nHeight - nHeight2, nWidth, nHeight2);
      oSB.setColor(new Color(nColor.r, nColor.g, nColor.b, 0.7F));
      ImageManager.getImage(Images.gradientVertical).draw(oSB, nPosX, nPosY - nHeight - nHeight2, nWidth, nHeight2, false, true);
      oSB.setColor(new Color(nColor.r, nColor.g, nColor.b, 0.35F));
      ImageManager.getImage(Images.gradientHorizontal).draw(oSB, nPosX, nPosY - nHeight - nHeight2, nWidth / 3, nHeight2, false, false);
      ImageManager.getImage(Images.gradientHorizontal).draw(oSB, nPosX + nWidth - nWidth / 3, nPosY - nHeight - nHeight2, nWidth / 3, nHeight2, true, false);
   }

   protected final int getValue() {
      return this.iValue;
   }

   protected final int getHeight() {
      return this.iHeight;
   }

   protected final void setHeight(int iHeight) {
      this.iHeight = iHeight;
      if (this.iHeight < 1) {
         this.iHeight = 1;
      }
   }

   protected final int getDataTypeID() {
      return this.iColorDataID;
   }
}
