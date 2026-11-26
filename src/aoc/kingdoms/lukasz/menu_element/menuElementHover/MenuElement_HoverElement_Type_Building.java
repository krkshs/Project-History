package aoc.kingdoms.lukasz.menu_element.menuElementHover;

import aoc.kingdoms.lukasz.map.BuildingsManager;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuElement_HoverElement_Type_Building implements MenuElement_HoverElement_Type {
   public int building;
   public int buildingID;

   public MenuElement_HoverElement_Type_Building(int building, int buildingID) {
      this.building = building;
      this.buildingID = buildingID;
   }

   @Override
   public void draw(SpriteBatch oSB, int nPosX, int nPosY, float nAlpha, int iMaxWidth) {
      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, nAlpha));
      BuildingsManager.buildingImages.get(BuildingsManager.buildings.get(this.building).ImageID[this.buildingID]).draw(oSB, nPosX, nPosY);
      ImageManager.getImage(Images.buildingsFrame).draw(oSB, nPosX, nPosY);
      oSB.setColor(Color.WHITE);
   }

   @Override
   public int getWidth() {
      return ImageManager.getImage(Images.buildingsFrame).getWidth();
   }

   @Override
   public int getHeight() {
      return ImageManager.getImage(Images.buildingsFrame).getHeight();
   }
}
