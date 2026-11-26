package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.province.ProvinceBorderManager;
import aoc.kingdoms.lukasz.map.province.ProvinceDraw;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.MenuElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonusFlag;
import aoc.kingdoms.lukasz.menusInGame.InGame_CivBonuses;
import aoc.kingdoms.lukasz.menusInGame.Civ.InGame_Civ;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class ButtonFlagRect extends Button {
   public int iCivID;

   public ButtonFlagRect(int iCivID, int iPosX, int iPosY, boolean isClickable) {
      this.init(
         "",
         this.fontID,
         this.iTextPositionX,
         iPosX,
         iPosY,
         ImageManager.getImage(Images.flagRect2).getWidth(),
         ImageManager.getImage(Images.flagRect2).getHeight(),
         isClickable,
         true,
         false,
         false
      );
      this.typeOfElement = MenuElement_Type.BUTTON_FLAG;
      this.iCivID = iCivID;
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      oSB.setShader(Renderer.shaderAlpha);
      if (this.getFlagCivID() >= 0) {
         Game.getCiv(this.getFlagCivID()).getFlag().getTexture().bind(1);
      } else {
         ImageManager.getImage(Images.randomCivilizationFlag).getTexture().bind(1);
      }

      Gdx.gl.glActiveTexture(33984);
      ImageManager.getImage(Images.flagRect2Mask).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      oSB.flush();
      oSB.setShader(Renderer.shaderDefault);
      ImageManager.getImage(Images.flagRect2).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
   }

   public int getFlagCivID() {
      return this.iCivID;
   }

   @Override
   public void buildElementHover() {
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      nData.add(
         new MenuElement_HoverElement_Type_Button_TextBonusFlag(
            Game.getCiv(this.getFlagCivID()).getCivName(),
            "",
            this.getFlagCivID(),
            CFG.FONT_BOLD_SMALL,
            CFG.FONT_BOLD_SMALL,
            Colors.HOVER_GOLD,
            Colors.HOVER_GOLD
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      this.menuElementHover = new MenuElement_Hover(nElements);
   }

   @Override
   public int getCurrent() {
      return this.iCivID;
   }

   @Override
   public void actionElement() {
      if (this.iCivID >= 0 && this.iCivID != InGame_Civ.iActiveCivID) {
         InGame_Civ.iRebuildToCivID = this.iCivID;
         Game.menuManager.rebuildInGame_Civ();
         InGame_Civ.lTime = 0L;
         if (Game.menuManager.getVisibleInGame_CivBonuses() && InGame_Civ.iActiveCivID != 0 && InGame_CivBonuses.iCivID != InGame_Civ.iActiveCivID) {
            InGame_CivBonuses.iCivID = InGame_Civ.iActiveCivID;
            Game.menuManager.rebuildInGame_CivBonuses();
            Game.menuManager.setVisibleInGame_CivBonuses(true);
            InGame_CivBonuses.lTime = 0L;
         }
      } else if (Game.getCiv(this.iCivID).getCapitalProvinceID() >= 0
         && Game.getProvince(Game.getCiv(this.iCivID).getCapitalProvinceID()).getCivID() == this.iCivID) {
         Game.mapCoords.centerToProvinceID(Game.getCiv(this.iCivID).getCapitalProvinceID());
         Game.setActiveProvinceID(Game.getCiv(this.iCivID).getCapitalProvinceID());
         ProvinceBorderManager.action.setProvinceID(Game.getCiv(this.iCivID).getCapitalProvinceID());
      }
   }

   @Override
   public void setIsHovered(boolean isHovered) {
      super.setIsHovered(isHovered);
      if (isHovered) {
         ProvinceDraw.drawProvincesCiv_HoveredFlagID = this.iCivID;
      }
   }

   public static int getButtonWidth() {
      return ImageManager.getImage(Images.flagRect2).getWidth();
   }

   public static int getButtonHeight() {
      return ImageManager.getImage(Images.flagRect2).getHeight();
   }

   @Override
   public void actionElementPPM() {
      if (this.getCurrent() > 0
         && Game.getCiv(this.getCurrent()).getCapitalProvinceID() >= 0
         && this.getCurrent() == Game.getProvince(Game.getCiv(this.getCurrent()).getCapitalProvinceID()).getCivID()) {
         Game.mapCoords.centerToProvinceID(Game.getCiv(this.getCurrent()).getCapitalProvinceID());
      }
   }
}
