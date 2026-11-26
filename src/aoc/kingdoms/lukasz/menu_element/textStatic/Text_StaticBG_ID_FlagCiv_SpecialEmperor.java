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

public class Text_StaticBG_ID_FlagCiv_SpecialEmperor extends Text_StaticBG {
   public int id;
   public String text;
   public int iTextW;
   public int iTextH;

   public Text_StaticBG_ID_FlagCiv_SpecialEmperor(
      String text, String sText, int fontID, int iTextPositionX, int iPosX, int iPosY, int iWidth, int iHeight, int id
   ) {
      super(sText, fontID, iTextPositionX, iPosX, iPosY, iWidth, iHeight);
      this.typeOfElement = MenuElement_Type.BUTTON_FLAG;
      this.id = id;
      this.text = text;
      Renderer.glyphLayout.setText(Renderer.fontMain.get(fontID), text);
      this.iTextW = (int)Renderer.glyphLayout.width;
      this.iTextH = (int)Renderer.glyphLayout.height;
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
      oSB.setColor(
         new Color(
            Colors.COLOR_GRADIENT_BG_BLUE.r, Colors.COLOR_GRADIENT_BG_BLUE.g, Colors.COLOR_GRADIENT_BG_BLUE.b, !this.getIsHovered() && !isActive ? 0.3F : 0.5F
         )
      );
      Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
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
         this.text,
         this.getPosX() + CFG.PADDING * 4 + ImageManager.getImage(Images.flag_rect).getWidth() + iTranslateX,
         this.getPosY() + this.getHeight() / 2 - this.iTextH / 2 + iTranslateY,
         this.getColor2(isActive)
      );
      Renderer.drawTextWithShadow(
         oSB,
         this.fontID,
         this.sText,
         this.getPosX() + CFG.PADDING * 4 + this.iTextW + ImageManager.getImage(Images.flag_rect).getWidth() + iTranslateX,
         this.getPosY() + this.getHeight() / 2 - this.iTextH / 2 + iTranslateY,
         this.getColor(isActive)
      );
   }

   @Override
   public int getCurrent() {
      return this.id;
   }

   public Color getColor2(boolean isActive) {
      return Colors.getColorButtonHover(isActive, this.getIsHovered());
   }

   @Override
   protected Color getColor(boolean isActive) {
      return Colors.getColorButtonHover2(isActive, this.getIsHovered());
   }

   @Override
   public void setIsHovered(boolean isHovered) {
      super.setIsHovered(isHovered);
      if (isHovered) {
         ProvinceDraw.drawProvincesCiv_HoveredFlagID = this.id;
      }
   }
}
