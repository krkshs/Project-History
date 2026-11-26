package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.province.ProvinceDraw;
import aoc.kingdoms.lukasz.menu_element.MenuElement_Type;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ButtonFlag extends Button {
   public static int EXTRA_RANDOM = 0;
   private int flagImgID = 0;
   public int iCivID;

   public ButtonFlag(int iPosX, int iPosY, boolean isClickable) {
      this.init("", this.fontID, this.iTextPositionX, iPosX, iPosY, getButtonWidth(), getButtonHeight(), isClickable, true, false, false);
      this.typeOfElement = MenuElement_Type.BUTTON_FLAG;
   }

   public ButtonFlag(int iCivID, int iPosX, int iPosY, boolean isClickable) {
      this.init("", this.fontID, this.iTextPositionX, iPosX, iPosY, getButtonWidth(), getButtonHeight(), isClickable, true, false, false);
      this.typeOfElement = MenuElement_Type.BUTTON_FLAG;
      this.iCivID = iCivID;
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      this.drawFlag(oSB, iTranslateX, iTranslateY, isActive);
   }

   public int getFlagCivID() {
      return Game.iActiveProvince >= 0 ? Game.getProvince(Game.iActiveProvince).getCivID() : this.getCurrent();
   }

   @Override
   public int getCurrent() {
      return this.iCivID;
   }

   protected void drawFlag(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      if (Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).FLAG_BELOW) {
         ImageManager.getImage(Images.flagBelow)
            .draw(
               oSB,
               this.getPosX()
                  + (ImageManager.getImage(Images.flagOver.get(this.flagImgID)).getWidth() - ImageManager.getImage(Images.flagBelow).getWidth()) / 2
                  + iTranslateX,
               this.getPosY() + iTranslateY
            );
      }

      if (Game.getCiv(this.getFlagCivID()).isFlagNearest) {
         oSB.setShader(Renderer.shaderAlpha);
         Game.getCiv(this.getFlagCivID()).getFlag().getTexture().bind(1);
         Gdx.gl.glActiveTexture(33984);
         ImageManager.getImage(Images.flagMask.get(this.flagImgID))
            .draw(
               oSB,
               this.getPosX() + iTranslateX,
               this.getPosY() + iTranslateY,
               ImageManager.getImage(Images.flagMask.get(this.flagImgID)).getWidth(),
               ImageManager.getImage(Images.flagMask.get(this.flagImgID)).getHeight()
            );
      } else {
         Renderer.setShaderWater3(oSB);
         Renderer.shaderWater3.setUniformf("u_maskScale", 1.0F);
         Renderer.shaderWater3.setUniformf("u_maskScaleY", 1.0F);
         ImageManager.getImage(Images.flagMask.get(this.flagImgID)).getTexture().bind(1);
         Gdx.gl.glActiveTexture(33984);
         Game.getCiv(this.getFlagCivID())
            .getFlag()
            .draw(
               oSB,
               this.getPosX() + iTranslateX,
               this.getPosY() + iTranslateY,
               ImageManager.getImage(Images.flagMask.get(this.flagImgID)).getWidth(),
               ImageManager.getImage(Images.flagMask.get(this.flagImgID)).getHeight()
            );
      }

      oSB.flush();
      oSB.setShader(Renderer.shaderDefault);
      if (GameValues.inGame.ENABLE_VASSAL_LORD_FLAG && Game.getCiv(this.getFlagCivID()).getPuppetOfCivID() != this.getFlagCivID()) {
         if (Game.getCiv(Game.getCiv(this.getFlagCivID()).getPuppetOfCivID()).isFlagNearest) {
            oSB.setShader(Renderer.shaderAlpha);
            Game.getCiv(Game.getCiv(this.getFlagCivID()).getPuppetOfCivID()).getFlag().getTexture().bind(1);
            Gdx.gl.glActiveTexture(33984);
            ImageManager.getImage(Images.flagMaskLord.get(this.flagImgID))
               .draw(
                  oSB,
                  this.getPosX() + iTranslateX,
                  this.getPosY() + iTranslateY,
                  ImageManager.getImage(Images.flagMaskLord.get(this.flagImgID)).getWidth(),
                  ImageManager.getImage(Images.flagMaskLord.get(this.flagImgID)).getHeight()
               );
         } else {
            Renderer.setShaderWater3(oSB);
            Renderer.shaderWater3.setUniformf("u_maskScale", 1.0F);
            Renderer.shaderWater3.setUniformf("u_maskScaleY", 1.0F);
            ImageManager.getImage(Images.flagMaskLord.get(this.flagImgID)).getTexture().bind(1);
            Gdx.gl.glActiveTexture(33984);
            Game.getCiv(Game.getCiv(this.getFlagCivID()).getPuppetOfCivID())
               .getFlag()
               .draw(
                  oSB,
                  this.getPosX() + iTranslateX,
                  this.getPosY() + iTranslateY,
                  ImageManager.getImage(Images.flagMaskLord.get(this.flagImgID)).getWidth(),
                  ImageManager.getImage(Images.flagMaskLord.get(this.flagImgID)).getHeight()
               );
         }

         oSB.flush();
         oSB.setShader(Renderer.shaderDefault);
      }

      ImageManager.getImage(Images.flagOver.get(this.flagImgID))
         .draw(
            oSB,
            this.getPosX()
               + (ImageManager.getImage(Images.flagMask.get(this.flagImgID)).getWidth() - ImageManager.getImage(Images.flagOver.get(this.flagImgID)).getWidth())
                  / 2
               + iTranslateX,
            this.getPosY() + iTranslateY
         );
      if (this.getIsHovered() || isActive) {
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.3F));
         ImageManager.getImage(Images.flagOver.get(this.flagImgID))
            .draw(
               oSB,
               this.getPosX()
                  + (
                        ImageManager.getImage(Images.flagMask.get(this.flagImgID)).getWidth()
                           - ImageManager.getImage(Images.flagOver.get(this.flagImgID)).getWidth()
                     )
                     / 2
                  + iTranslateX,
               this.getPosY() + iTranslateY
            );
      }

      oSB.setColor(Color.WHITE);
   }

   public static int getButtonWidth() {
      return ImageManager.getImage(Images.flagOver.get(0)).getWidth();
   }

   public static int getButtonHeight() {
      return ImageManager.getImage(Images.flagOver.get(0)).getHeight();
   }

   @Override
   public void updateLanguage() {
      this.flagImgID = Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).FLAG_GROUP[0]
         + (
            Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).FLAG_GROUP[1] - Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).FLAG_GROUP[0] > 0
               ? (EXTRA_RANDOM + this.getFlagCivID())
                  % (Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).FLAG_GROUP[1] - Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).FLAG_GROUP[0])
               : 0
         );
   }

   @Override
   public void setIsHovered(boolean isHovered) {
      super.setIsHovered(isHovered);
      if (isHovered) {
         ProvinceDraw.drawProvincesCiv_HoveredFlagID = this.getFlagCivID();
      }
   }

   @Override
   public void actionElementPPM() {
      try {
         if (this.getFlagCivID() > 0
            && Game.getCiv(this.getFlagCivID()).getCapitalProvinceID() >= 0
            && this.getFlagCivID() == Game.getProvince(Game.getCiv(this.getFlagCivID()).getCapitalProvinceID()).getCivID()) {
            Game.mapCoords.centerToProvinceID(Game.getCiv(this.getFlagCivID()).getCapitalProvinceID());
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }
   }
}
