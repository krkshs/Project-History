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
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Image;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menusInGame.InGame_CivBonuses;
import aoc.kingdoms.lukasz.menusInGame.Civ.InGame_Civ;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class ButtonFlag_Diplomacy extends Button {
   public int iCivID;
   public boolean drawArrow = false;
   public boolean drawArrows = false;
   public boolean arrowFlipX = true;

   public ButtonFlag_Diplomacy(int iCivID, int iPosX, int iPosY, boolean isClickable) {
      this.init(
         "",
         this.fontID,
         this.iTextPositionX,
         iPosX,
         iPosY,
         ImageManager.getImage(Images.flagDiplomacyOver).getWidth(),
         ImageManager.getImage(Images.flagDiplomacyOver).getHeight(),
         isClickable,
         true,
         false,
         false
      );
      this.typeOfElement = MenuElement_Type.BUTTON_FLAG;
      this.iCivID = iCivID;
   }

   public ButtonFlag_Diplomacy(int iCivID, int iPosX, int iPosY, boolean isClickable, boolean drawArrows) {
      this.init(
         "",
         this.fontID,
         this.iTextPositionX,
         iPosX,
         iPosY,
         ImageManager.getImage(Images.flagDiplomacyOver).getWidth(),
         ImageManager.getImage(Images.flagDiplomacyOver).getHeight(),
         isClickable,
         true,
         false,
         false
      );
      this.typeOfElement = MenuElement_Type.BUTTON_FLAG;
      this.iCivID = iCivID;
      this.drawArrow = drawArrows;
      this.drawArrows = drawArrows;
   }

   public ButtonFlag_Diplomacy(int iCivID, int iPosX, int iPosY, boolean isClickable, boolean drawArrow, boolean arrowFlipX) {
      this.init(
         "",
         this.fontID,
         this.iTextPositionX,
         iPosX,
         iPosY,
         ImageManager.getImage(Images.flagDiplomacyOver).getWidth(),
         ImageManager.getImage(Images.flagDiplomacyOver).getHeight(),
         isClickable,
         true,
         false,
         false
      );
      this.typeOfElement = MenuElement_Type.BUTTON_FLAG;
      this.iCivID = iCivID;
      this.drawArrow = drawArrow;
      this.arrowFlipX = !arrowFlipX;
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      oSB.setShader(Renderer.shaderAlpha);
      if (this.getFlagCivID() >= 0) {
         Game.getCiv(this.getFlagCivID()).getFlag().getTexture().bind(1);
      } else if (this.getFlagCivID() < 0) {
         ImageManager.getImage(Images.rebelsFlag).getTexture().bind(1);
      } else {
         ImageManager.getImage(Images.randomCivilizationFlag).getTexture().bind(1);
      }

      Gdx.gl.glActiveTexture(33984);
      ImageManager.getImage(Images.flagDiplomacyMask)
         .draw(
            oSB,
            this.getPosX()
               + iTranslateX
               + (ImageManager.getImage(Images.flagDiplomacyOver).getWidth() - ImageManager.getImage(Images.flagDiplomacyMask).getWidth()) / 2,
            this.getPosY()
               + iTranslateY
               + (ImageManager.getImage(Images.flagDiplomacyOver).getHeight() - ImageManager.getImage(Images.flagDiplomacyMask).getHeight()) / 2,
            ImageManager.getImage(Images.flagDiplomacyMask).getWidth(),
            ImageManager.getImage(Images.flagDiplomacyMask).getHeight()
         );
      oSB.flush();
      oSB.setShader(Renderer.shaderDefault);
      ImageManager.getImage(Images.flagDiplomacyOver).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY);
      if (this.drawArrow) {
         if (this.drawArrows) {
            ImageManager.getImage(Images.flagDiplomacyArrow)
               .draw(
                  oSB,
                  this.getPosX()
                     + ImageManager.getImage(Images.flagDiplomacyOver).getWidth()
                     - ImageManager.getImage(Images.flagDiplomacyArrow).getWidth()
                     - CFG.PADDING / 2
                     + iTranslateX,
                  this.getPosY() + CFG.PADDING / 2 + iTranslateY
               );
            ImageManager.getImage(Images.flagDiplomacyArrow)
               .draw(
                  oSB,
                  this.getPosX()
                     + ImageManager.getImage(Images.flagDiplomacyOver).getWidth()
                     - ImageManager.getImage(Images.flagDiplomacyArrow).getWidth()
                     - CFG.PADDING / 2
                     + iTranslateX,
                  this.getPosY() + CFG.PADDING + ImageManager.getImage(Images.flagDiplomacyArrow).getHeight() + iTranslateY,
                  true,
                  false
               );
         } else {
            ImageManager.getImage(Images.flagDiplomacyArrow)
               .draw(
                  oSB,
                  this.getPosX()
                     + ImageManager.getImage(Images.flagDiplomacyOver).getWidth()
                     - ImageManager.getImage(Images.flagDiplomacyArrow).getWidth()
                     - CFG.PADDING / 2
                     + iTranslateX,
                  this.getPosY() + CFG.PADDING / 2 + iTranslateY,
                  this.arrowFlipX,
                  false
               );
         }
      }
   }

   public int getFlagCivID() {
      return this.iCivID;
   }

   @Override
   public void buildElementHover() {
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      if (this.getFlagCivID() < 0) {
         nData.add(new MenuElement_HoverElement_Type_Image(Images.rebelsFlag, 0, CFG.PADDING));
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Rebels"), Colors.HOVER_LEFT));
      } else {
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
      }

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
   public void actionElementPPM() {
      if (Game.getCiv(this.getCurrent()).getCapitalProvinceID() > 0
         && this.getCurrent() == Game.getProvince(Game.getCiv(this.getCurrent()).getCapitalProvinceID()).getCivID()) {
         Game.mapCoords.centerToProvinceID(Game.getCiv(this.getCurrent()).getCapitalProvinceID());
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
      return ImageManager.getImage(Images.flagDiplomacyOver).getWidth();
   }

   public static int getButtonHeight() {
      return ImageManager.getImage(Images.flagDiplomacyOver).getHeight();
   }
}
