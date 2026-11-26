package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menus.MainMenu_Stats;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ButtonFlag_Stats extends Button {
   private int flagImgID = 0;
   private int statsFlagID = 0;

   public ButtonFlag_Stats(int statsFlagID, int iPosX, int iPosY) {
      this.init("", this.fontID, this.iTextPositionX, iPosX, iPosY, getButtonWidth(), getButtonHeight(), true, true, false, false);
      this.statsFlagID = statsFlagID;
      this.updateLanguage();
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      this.drawFlag(oSB, iTranslateX, iTranslateY, isActive);
   }

   public int getFlagCivID() {
      return this.statsFlagID;
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

      oSB.setShader(Renderer.shaderAlpha);

      try {
         MainMenu_Stats.lFlags.get(this.getFlagCivID()).getTexture().bind(1);
         Gdx.gl.glActiveTexture(33984);
         ImageManager.getImage(Images.flagMask.get(this.flagImgID))
            .draw(
               oSB,
               this.getPosX() + iTranslateX,
               this.getPosY() + iTranslateY,
               ImageManager.getImage(Images.flagMask.get(this.flagImgID)).getWidth(),
               ImageManager.getImage(Images.flagMask.get(this.flagImgID)).getHeight()
            );
      } catch (Exception var6) {
      }

      oSB.flush();
      oSB.setShader(Renderer.shaderDefault);
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
               ? (ButtonFlag.EXTRA_RANDOM + this.getFlagCivID())
                  % (Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).FLAG_GROUP[1] - Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).FLAG_GROUP[0])
               : 0
         );
   }

   @Override
   public void buildElementHover() {
      this.menuElementHover = MainMenu_Stats.getHover(this.getFlagCivID());
   }

   @Override
   public int getCurrent() {
      return this.statsFlagID;
   }
}
