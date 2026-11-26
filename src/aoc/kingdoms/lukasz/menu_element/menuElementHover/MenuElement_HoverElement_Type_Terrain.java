package aoc.kingdoms.lukasz.menu_element.menuElementHover;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuElement_HoverElement_Type_Terrain implements MenuElement_HoverElement_Type {
   private int iTerrainID;
   private int iTerrainID2;
   private int offsetLeft = 0;
   private int offsetRight = 0;
   private int iWidth = 0;
   private int iHeight = 0;

   public MenuElement_HoverElement_Type_Terrain(int iTerrainID, int provinceID, int offsetLeft, int offsetRight) {
      this.iTerrainID = iTerrainID;
      this.iTerrainID2 = Math.max(0, provinceID) % Game.terrainManager.terrainImages.get(iTerrainID).size();
      this.offsetLeft = offsetLeft;
      this.offsetRight = offsetRight;
      this.iWidth = (int)(
         Game.terrainManager.terrainImages.get(iTerrainID).get(this.iTerrainID2).getWidth()
            * (CFG.CIV_FLAG_HEIGHT * this.getImageScale())
            / Game.terrainManager.terrainImages.get(iTerrainID).get(this.iTerrainID2).getHeight()
      );
      this.iHeight = (int)(
         Game.terrainManager.terrainImages.get(iTerrainID).get(this.iTerrainID2).getHeight()
            * (CFG.CIV_FLAG_HEIGHT * this.getImageScale())
            / Game.terrainManager.terrainImages.get(iTerrainID).get(this.iTerrainID2).getHeight()
      );
   }

   @Override
   public void draw(SpriteBatch oSB, int nPosX, int nPosY, float nAlpha, int iMaxWidth) {
      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, nAlpha));
      Game.terrainManager
         .terrainImages
         .get(this.iTerrainID)
         .get(this.iTerrainID2)
         .draw(oSB, nPosX + this.offsetLeft, nPosY + CFG.PADDING + CFG.PADDING / 2 + CFG.TEXT_HEIGHT / 2 - this.iHeight / 2, this.iWidth, this.iHeight);
   }

   @Override
   public int getWidth() {
      return this.offsetRight + this.offsetLeft + this.iWidth;
   }

   @Override
   public int getHeight() {
      return CFG.TEXT_HEIGHT + CFG.PADDING;
   }

   private final float getImageScale() {
      return (float)CFG.TEXT_HEIGHT / CFG.CIV_FLAG_HEIGHT;
   }
}
