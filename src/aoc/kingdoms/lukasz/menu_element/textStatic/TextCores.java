package aoc.kingdoms.lukasz.menu_element.textStatic;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.province.ProvinceDraw;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.button.Button;
import aoc.kingdoms.lukasz.menusInGame.Province.InGame_ProvinceInfo;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class TextCores extends Button {
   public List<Integer> lCivID = new ArrayList<>();
   public int iCivsSize = 0;
   public int flagWidth;
   public int flagHeight;
   public int iProvinceID;
   public boolean drawRed;

   public TextCores(String sText, List<Integer> tCivs, int nPosX, int nPosY, int nWidth, int nHeight, int iProvinceID, boolean drawRed) {
      this.init(sText, CFG.FONT_REGULAR_SMALL, 0, nPosX, nPosY, nWidth, nHeight, true, true, false, false);
      this.iProvinceID = iProvinceID;
      this.lCivID = tCivs;
      this.iCivsSize = this.lCivID.size();
      this.drawRed = drawRed;
      this.flagWidth = (int)(CFG.CIV_FLAG_WIDTH * this.getImageScale());
      this.flagHeight = (int)(CFG.CIV_FLAG_HEIGHT * this.getImageScale());
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.5F));
      Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
      if (this.drawRed) {
         oSB.setColor(
            new Color(
               Colors.HOVER_NEGATIVE.r,
               Colors.HOVER_NEGATIVE.g,
               Colors.HOVER_NEGATIVE.b,
               Game.getProvince(this.iProvinceID).getCivID() == Game.player.iCivID ? 0.5F : 0.3F
            )
         );
         Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      }

      if (this.iCivsSize != Game.getProvince(this.iProvinceID).iCoresSize) {
         this.lCivID.clear();

         for (int i = 0; i < Game.getProvince(this.iProvinceID).iCoresSize; i++) {
            this.lCivID.add(Game.getProvince(this.iProvinceID).getCore(i));
         }

         this.iCivsSize = this.lCivID.size();
      }

      try {
         if (Game.getProvince(this.iProvinceID).coreCreation != null) {
            oSB.setColor(new Color(ProvinceDraw.progressBar.r, ProvinceDraw.progressBar.g, ProvinceDraw.progressBar.b, 0.5F));
            Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
            oSB.setColor(new Color(ProvinceDraw.progressBar));
            Renderer.drawBoxProgress(
               oSB,
               Images.statsRectBG,
               this.getPosX() + iTranslateX,
               this.getPosY() + iTranslateY,
               (int)(
                  this.getWidth()
                     * (1.0F - (float)Game.getProvince(this.iProvinceID).coreCreation.daysLeft / Game.getProvince(this.iProvinceID).coreCreation.investTime)
               ),
               this.getHeight(),
               this.getWidth()
            );
            oSB.setColor(Color.WHITE);
            oSB.setColor(new Color(Colors.COLOR_GRADIENT_BG_BLUE.r, Colors.COLOR_GRADIENT_BG_BLUE.g, Colors.COLOR_GRADIENT_BG_BLUE.b, 0.2F));
            Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
            oSB.setColor(Color.WHITE);
         }
      } catch (Exception var8) {
         CFG.exceptionStack(var8);
      }

      if (this.getIsHovered() || isActive) {
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 1.0F));
         Renderer.drawBox(oSB, Images.statsRectBGBorder, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
         oSB.setColor(Color.WHITE);
      }

      oSB.setColor(Color.WHITE);
      Renderer.drawText(
         oSB,
         this.fontID,
         this.getTextToDraw(),
         this.getPosX() + CFG.PADDING + iTranslateX,
         this.getPosY() + this.getHeight() / 2 - this.iTextHeight / 2 + iTranslateY,
         this.getColor(isActive)
      );

      for (int i = 0; i < this.iCivsSize; i++) {
         try {
            if (this.lCivID.get(i) >= 0) {
               Game.getCiv(this.lCivID.get(i))
                  .getFlag()
                  .draw(
                     oSB,
                     this.getPosX() + (this.flagWidth + CFG.PADDING) * i + CFG.PADDING * 2 + this.getTextWidth() + iTranslateX,
                     this.getPosY() + this.getHeight() / 2 - (int)(CFG.CIV_FLAG_HEIGHT * this.getImageScale() / 2.0F) + iTranslateY,
                     this.flagWidth,
                     this.flagHeight
                  );
            } else {
               ImageManager.getImage(Images.randomCivilizationFlag)
                  .draw(
                     oSB,
                     this.getPosX() + (this.flagWidth + CFG.PADDING) * i + CFG.PADDING * 2 + this.getTextWidth() + iTranslateX,
                     this.getPosY() + this.getHeight() / 2 - (int)(CFG.CIV_FLAG_HEIGHT * this.getImageScale() / 2.0F) + iTranslateY,
                     this.flagWidth,
                     this.flagHeight
                  );
            }
         } catch (IndexOutOfBoundsException var7) {
            ImageManager.getImage(Images.randomCivilizationFlag)
               .draw(
                  oSB,
                  this.getPosX() + (this.flagWidth + CFG.PADDING) * i + CFG.PADDING * 2 + this.getTextWidth() + iTranslateX,
                  this.getPosY() + this.getHeight() / 2 - (int)(CFG.CIV_FLAG_HEIGHT * this.getImageScale() / 2.0F) + iTranslateY,
                  this.flagWidth,
                  this.flagHeight
               );
         }

         ImageManager.getImage(Images.flag_rect)
            .draw(
               oSB,
               this.getPosX() + (this.flagWidth + CFG.PADDING) * i + CFG.PADDING * 2 + this.getTextWidth() + iTranslateX,
               this.getPosY() + this.getHeight() / 2 - (int)(CFG.CIV_FLAG_HEIGHT * this.getImageScale() / 2.0F) + iTranslateY,
               this.flagWidth,
               this.flagHeight
            );
      }
   }

   @Override
   protected Color getColor(boolean isActive) {
      return Colors.getColorButtonHover(isActive, this.getIsHovered());
   }

   private final float getImageScale() {
      return (float)CFG.TEXT_HEIGHT / CFG.CIV_FLAG_HEIGHT;
   }

   @Override
   public void buildElementHover() {
      this.menuElementHover = InGame_ProvinceInfo.getHoverCores(this.iProvinceID, true);
   }
}
