package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.BuildingsManager;
import aoc.kingdoms.lukasz.map.province.ProvinceConstructionBuilding;
import aoc.kingdoms.lukasz.map.province.ProvinceDraw;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Flag;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.textures.Image;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class Button_QueueBuilding extends Button {
   public ProvinceConstructionBuilding building;
   public int provinceID;

   public Button_QueueBuilding(int nPosX, int nPosY, int provinceID, ProvinceConstructionBuilding building) {
      this.init(
         null,
         CFG.FONT_BOLD_SMALL,
         -1,
         nPosX,
         nPosY,
         ImageManager.getImage(Images.unitsFrameMap).getWidth(),
         ImageManager.getImage(Images.unitsFrameMap).getHeight(),
         true,
         true,
         false,
         false
      );
      this.provinceID = provinceID;
      this.building = building;
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      Image progressBarFrame = ImageManager.getImage(Images.progressBarFrame);
      Image progressBarFrameMask = ImageManager.getImage(Images.progressBarFrameMask);
      Image unitsFrameMap = ImageManager.getImage(Images.unitsFrameMap);
      oSB.setShader(Renderer.shaderAlpha);
      BuildingsManager.buildingImages
         .get(BuildingsManager.buildings.get(this.building.getBuilding()).ImageID[this.building.getBuildingID()])
         .getTexture()
         .bind(1);
      Gdx.gl.glActiveTexture(33984);
      ImageManager.getImage(Images.unitsFrameMapMask).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY);
      oSB.flush();
      oSB.setShader(Renderer.shaderDefault);
      unitsFrameMap.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY);
      int tCenterX = (progressBarFrame.getWidth() - progressBarFrameMask.getWidth()) / 2;
      int tCenterY = (progressBarFrame.getHeight() - progressBarFrameMask.getHeight()) / 2;
      oSB.setColor(new Color(ProvinceDraw.progressBarBG));
      progressBarFrameMask.draw(
         oSB, this.getPosX() + iTranslateX + tCenterX, this.getPosY() + iTranslateY + unitsFrameMap.getHeight() - progressBarFrame.getHeight() + tCenterY
      );
      oSB.setColor(ProvinceDraw.progressBar);
      progressBarFrameMask.draw(
         oSB,
         this.getPosX() + iTranslateX + tCenterX,
         this.getPosY() + iTranslateY + unitsFrameMap.getHeight() - progressBarFrame.getHeight() + tCenterY,
         (int)(progressBarFrameMask.getWidth() * (1.0F - (float)this.building.getConstructionTimeLeft() / this.building.getConstructionTime())),
         progressBarFrameMask.getHeight()
      );
      oSB.setColor(Color.WHITE);
      progressBarFrame.draw(
         oSB,
         this.getPosX() + iTranslateX + unitsFrameMap.getWidth() / 2 - progressBarFrame.getWidth() / 2,
         this.getPosY() + iTranslateY + unitsFrameMap.getHeight() - progressBarFrame.getHeight()
      );
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
   }

   @Override
   public void actionElement() {
      Game.mapCoords.centerToProvinceID(this.provinceID);
   }

   @Override
   public void actionElementPPM() {
      Game.getProvince(this.provinceID).cancelBuildingConstruction(Game.player.iCivID, this.building.getBuilding(), this.building.getBuildingID());
   }

   @Override
   public void buildElementHover() {
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      nData.add(new MenuElement_HoverElement_Type_Line());
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Text(Game.getProvince(this.provinceID).getProvinceName(), Colors.HOVER_GOLD));
      nData.add(new MenuElement_HoverElement_Type_Flag(Game.getProvince(this.provinceID).getCivID(), CFG.PADDING, 0));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      this.menuElementHover = new MenuElement_Hover(nElements);
   }
}
