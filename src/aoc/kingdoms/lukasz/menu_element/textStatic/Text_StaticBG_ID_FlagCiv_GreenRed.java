package aoc.kingdoms.lukasz.menu_element.textStatic;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.province.ProvinceDraw;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.MenuElement_Type;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Text_StaticBG_ID_FlagCiv_GreenRed extends Text_StaticBG {
   public int id;
   public boolean isActiveRedGreen = false;

   public Text_StaticBG_ID_FlagCiv_GreenRed(String sText, int fontID, int iTextPositionX, int iPosX, int iPosY, int iWidth, int iHeight, int id) {
      super(sText, fontID, iTextPositionX, iPosX, iPosY, iWidth, iHeight);
      this.typeOfElement = MenuElement_Type.BUTTON_FLAG;
      this.id = id;
      int tWMax = 0;

      while (this.iTextWidth > this.getWidth() - (CFG.PADDING * 4 + ImageManager.getImage(Images.flag_rect).getWidth()) && this.getText().length() > 5) {
         if (++tWMax >= 100) {
            break;
         }

         this.setText(this.getText().substring(0, Math.max(1, this.getText().length() - 3)) + "..");
      }
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
      if (this.getCheckboxState()) {
         oSB.setColor(new Color(Colors.COLOR_NOTIFICATION_BG_GREEN.r, Colors.COLOR_NOTIFICATION_BG_GREEN.g, Colors.COLOR_NOTIFICATION_BG_GREEN.b, 0.5F));
         Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
         oSB.setColor(Color.WHITE);
         oSB.setColor(
            new Color(
               Colors.COLOR_NOTIFICATION_BG_GREEN.r,
               Colors.COLOR_NOTIFICATION_BG_GREEN.g,
               Colors.COLOR_NOTIFICATION_BG_GREEN.b,
               !this.getIsHovered() && !isActive ? 0.35F : 0.65F
            )
         );
         Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
         oSB.setColor(Color.WHITE);
         oSB.setColor(new Color(Colors.COLOR_NOTIFICATION_OVER_GREEN.r, Colors.COLOR_NOTIFICATION_OVER_GREEN.g, Colors.COLOR_NOTIFICATION_OVER_GREEN.b, 0.3F));
         Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
         oSB.setColor(new Color(Colors.COLOR_NOTIFICATION_OVER_GREEN.r, Colors.COLOR_NOTIFICATION_OVER_GREEN.g, Colors.COLOR_NOTIFICATION_OVER_GREEN.b, 0.35F));
         Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
         oSB.setColor(new Color(Colors.COLOR_NOTIFICATION_OVER_GREEN.r, Colors.COLOR_NOTIFICATION_OVER_GREEN.g, Colors.COLOR_NOTIFICATION_OVER_GREEN.b, 0.3F));
         Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.25F));
         Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), CFG.PADDING * 2, false, true);
         Images.gradientXY
            .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - CFG.PADDING * 2 + iTranslateY, this.getWidth(), CFG.PADDING * 2);
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.25F));
         Renderer.drawBox(oSB, Images.statsRectBGBorder, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F));
         Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 1 + iTranslateY, this.getWidth(), 1);
         Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), 1);
         oSB.setColor(new Color(Colors.COLOR_NOTIFICATION_OVER_GREEN.r, Colors.COLOR_NOTIFICATION_OVER_GREEN.g, Colors.COLOR_NOTIFICATION_OVER_GREEN.b, 0.85F));
         Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 2 + iTranslateY, this.getWidth(), 1);
         Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + 1 + iTranslateY, this.getWidth(), 1);
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.55F));
         Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 1 + iTranslateY, this.getWidth(), 1);
         Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), 1);
         oSB.setColor(new Color(Colors.COLOR_NOTIFICATION_OVER_GREEN.r, Colors.COLOR_NOTIFICATION_OVER_GREEN.g, Colors.COLOR_NOTIFICATION_OVER_GREEN.b, 0.9F));
         Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 2 + iTranslateY, this.getWidth(), 1);
         Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + 1 + iTranslateY, this.getWidth(), 1);
      } else {
         oSB.setColor(new Color(Colors.COLOR_NOTIFICATION_BG_RED.r, Colors.COLOR_NOTIFICATION_BG_RED.g, Colors.COLOR_NOTIFICATION_BG_RED.b, 0.5F));
         Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
         oSB.setColor(Color.WHITE);
         oSB.setColor(
            new Color(
               Colors.COLOR_NOTIFICATION_BG_RED.r,
               Colors.COLOR_NOTIFICATION_BG_RED.g,
               Colors.COLOR_NOTIFICATION_BG_RED.b,
               !this.getIsHovered() && !isActive ? 0.35F : 0.65F
            )
         );
         Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
         oSB.setColor(Color.WHITE);
         oSB.setColor(new Color(Colors.COLOR_NOTIFICATION_OVER_RED.r, Colors.COLOR_NOTIFICATION_OVER_RED.g, Colors.COLOR_NOTIFICATION_OVER_RED.b, 0.3F));
         Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
         oSB.setColor(new Color(Colors.COLOR_NOTIFICATION_OVER_RED.r, Colors.COLOR_NOTIFICATION_OVER_RED.g, Colors.COLOR_NOTIFICATION_OVER_RED.b, 0.35F));
         Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
         oSB.setColor(new Color(Colors.COLOR_NOTIFICATION_OVER_RED.r, Colors.COLOR_NOTIFICATION_OVER_RED.g, Colors.COLOR_NOTIFICATION_OVER_RED.b, 0.3F));
         Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.25F));
         Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), CFG.PADDING * 2, false, true);
         Images.gradientXY
            .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - CFG.PADDING * 2 + iTranslateY, this.getWidth(), CFG.PADDING * 2);
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.25F));
         Renderer.drawBox(oSB, Images.statsRectBGBorder, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F));
         Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 1 + iTranslateY, this.getWidth(), 1);
         Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), 1);
         oSB.setColor(new Color(Colors.COLOR_NOTIFICATION_OVER_RED.r, Colors.COLOR_NOTIFICATION_OVER_RED.g, Colors.COLOR_NOTIFICATION_OVER_RED.b, 0.85F));
         Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 2 + iTranslateY, this.getWidth(), 1);
         Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + 1 + iTranslateY, this.getWidth(), 1);
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.55F));
         Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 1 + iTranslateY, this.getWidth(), 1);
         Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), 1);
         oSB.setColor(new Color(Colors.COLOR_NOTIFICATION_OVER_RED.r, Colors.COLOR_NOTIFICATION_OVER_RED.g, Colors.COLOR_NOTIFICATION_OVER_RED.b, 0.9F));
         Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 2 + iTranslateY, this.getWidth(), 1);
         Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + 1 + iTranslateY, this.getWidth(), 1);
      }

      oSB.setColor(Color.WHITE);
      Game.getCiv(this.id)
         .getFlag()
         .draw(
            oSB,
            this.getPosX() + iTranslateX + CFG.PADDING * 2,
            this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(Images.flag_rect).getHeight() / 2 + iTranslateY,
            ImageManager.getImage(Images.flag_rect).getWidth(),
            ImageManager.getImage(Images.flag_rect).getHeight()
         );
      ImageManager.getImage(Images.flag_rect)
         .draw(
            oSB,
            this.getPosX() + iTranslateX + CFG.PADDING * 2,
            this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(Images.flag_rect).getHeight() / 2 + iTranslateY
         );
      Renderer.drawTextWithShadow(
         oSB,
         this.fontID,
         this.getTextToDraw(),
         this.getPosX() + CFG.PADDING * 4 + ImageManager.getImage(Images.flag_rect).getWidth() + iTranslateX,
         this.getPosY() + (this.getHeight() - CFG.TEXT_HEIGHT) / 2 + iTranslateY,
         this.getColor(isActive)
      );
   }

   @Override
   public boolean getCheckboxState() {
      return this.isActiveRedGreen;
   }

   @Override
   public void setCheckboxState(boolean checkboxState) {
      this.isActiveRedGreen = checkboxState;
   }

   @Override
   public String getTextToDraw() {
      return this.sText;
   }

   @Override
   public int getCurrent() {
      return this.id;
   }

   @Override
   public void setIsHovered(boolean isHovered) {
      super.setIsHovered(isHovered);
      if (isHovered) {
         ProvinceDraw.drawProvincesCiv_HoveredFlagID = this.id;
      }
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
