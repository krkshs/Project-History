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

public class Text_StaticBG_ID_FlagCiv_SpecialCiv extends Text_StaticBG {
   public int id;
   public static int img;
   public static int imgW;
   public static int imgH;
   public String text;
   public int iTextW;
   public int iTextH;

   public Text_StaticBG_ID_FlagCiv_SpecialCiv(
      String sText, int fontID, int iTextPositionX, int iPosX, int iPosY, int iWidth, int iHeight, int id, int img, String text
   ) {
      super(sText, fontID, iTextPositionX, iPosX, iPosY, iWidth, iHeight);
      this.typeOfElement = MenuElement_Type.BUTTON_FLAG;
      this.id = id;
      float iconScale = this.getImageScale(Images.gold);
      this.text = text;
      Renderer.glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_REGULAR_SMALL), text);
      this.iTextW = (int)Renderer.glyphLayout.width;
      this.iTextH = (int)Renderer.glyphLayout.height;
      Text_StaticBG_ID_FlagCiv_SpecialCiv.img = img;
      imgW = (int)(ImageManager.getImage(img).getWidth() * iconScale);
      imgH = (int)(ImageManager.getImage(img).getHeight() * iconScale);
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
      oSB.setColor(
         new Color(
            Colors.COLOR_GRADIENT_BG_BLUE.r, Colors.COLOR_GRADIENT_BG_BLUE.g, Colors.COLOR_GRADIENT_BG_BLUE.b, !this.getIsHovered() && !isActive ? 0.3F : 0.5F
         )
      );
      Renderer.drawBox(
         oSB,
         Images.statsRectBG,
         this.getPosX() + iTranslateX,
         this.getPosY() + CFG.PADDING + iTranslateY,
         this.getWidth(),
         this.getHeight() - CFG.PADDING * 2,
         1.0F
      );
      oSB.setColor(Color.WHITE);
      ImageManager.getImage(img)
         .draw(
            oSB,
            this.getPosX() + this.getWidth() - CFG.PADDING * 2 - imgW + iTranslateX,
            this.getPosY() + this.getHeight() / 2 - imgH / 2 + iTranslateY,
            imgW,
            imgH
         );
      Renderer.drawTextWithShadow(
         oSB,
         CFG.FONT_REGULAR_SMALL,
         this.text,
         this.getPosX() + this.getWidth() - CFG.PADDING * 3 - this.iTextW - imgW + iTranslateX,
         this.getPosY() + this.getHeight() / 2 - this.iTextH / 2 + iTranslateY,
         this.getColor2(isActive)
      );
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
         this.sText,
         this.getPosX() + CFG.PADDING * 4 + ImageManager.getImage(Images.flag_rect).getWidth() + iTranslateX,
         this.getPosY() + (this.getHeight() - CFG.TEXT_HEIGHT) / 2 + iTranslateY,
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

   private final float getImageScale(int iImageID) {
      return Math.min(1.0F, (float)CFG.TEXT_HEIGHT / ImageManager.getImage(iImageID).getHeight() * 1.15F);
   }
}
