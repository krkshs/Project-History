package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.province.ProvinceDraw;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.MenuElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonusFlag;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Image;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class ButtonFlag2_CivName extends Button {
   public int iCivID;

   public ButtonFlag2_CivName(int iCivID, int iPosX, int iPosY, boolean isClickable) {
      this.init(
         Game.getCiv(iCivID).getCivName(),
         CFG.FONT_REGULAR_SMALL,
         -1,
         iPosX,
         iPosY,
         ImageManager.getImage(Images.flagOverDefault).getWidth(),
         ImageManager.getImage(Images.flagOverDefault).getHeight() + CFG.TEXT_HEIGHT + CFG.PADDING * 2,
         isClickable,
         true,
         false,
         false
      );
      this.typeOfElement = MenuElement_Type.BUTTON_FLAG;
      this.iCivID = iCivID;
      int tWMax = 0;

      while (this.iTextWidth >= this.getWidth() - (this.iTextPositionX > 0 ? this.iTextPositionX : 0) - CFG.PADDING && this.getText().length() > 5) {
         if (++tWMax >= 100) {
            break;
         }

         this.setText(this.getText().substring(0, Math.max(1, this.getText().length() - 3)) + ".");
      }
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      if (!this.getIsHovered() && !isActive) {
         int nY = this.getPosY() + CFG.TEXT_HEIGHT + CFG.PADDING * 2;
         int nH = this.getHeight() - CFG.TEXT_HEIGHT - CFG.PADDING * 2;
         oSB.setColor(
            new Color(
               Colors.COLOR_STATS_RECT_BG.r,
               Colors.COLOR_STATS_RECT_BG.g,
               Colors.COLOR_STATS_RECT_BG.b,
               getBoxAlpha(this.getClickable(), this.getIsHovered(), isActive)
            )
         );
         Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, nY + iTranslateY, this.getWidth(), nH, 1.0F);
         oSB.setColor(Color.WHITE);
         oSB.setShader(Renderer.shaderAlpha);
         if (this.getFlagCivID() < 0) {
            ImageManager.getImage(Images.rebelsFlag).getTexture().bind(1);
         } else {
            Game.getCiv(this.getFlagCivID()).getFlag().getTexture().bind(1);
         }

         Gdx.gl.glActiveTexture(33984);
         ImageManager.getImage(Images.flagMaskDefault)
            .draw(
               oSB,
               this.getPosX()
                  + iTranslateX
                  + (ImageManager.getImage(Images.flagOverDefault).getWidth() - ImageManager.getImage(Images.flagMaskDefault).getWidth()) / 2,
               nY + iTranslateY + (ImageManager.getImage(Images.flagOverDefault).getHeight() - ImageManager.getImage(Images.flagMaskDefault).getHeight()) / 2,
               ImageManager.getImage(Images.flagMaskDefault).getWidth(),
               ImageManager.getImage(Images.flagMaskDefault).getHeight()
            );
         oSB.flush();
         oSB.setShader(Renderer.shaderDefault);
         ImageManager.getImage(Images.flagOverDefault).draw(oSB, this.getPosX() + iTranslateX, nY + iTranslateY);
      } else {
         oSB.setColor(
            new Color(
               Colors.COLOR_STATS_RECT_BG.r,
               Colors.COLOR_STATS_RECT_BG.g,
               Colors.COLOR_STATS_RECT_BG.b,
               getBoxAlpha(this.getClickable(), this.getIsHovered(), isActive)
            )
         );
         Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
         oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.5F));
         Images.gradientXY
            .draw(
               oSB,
               this.getPosX() + iTranslateX,
               this.getPosY() + this.getHeight() - CFG.TEXT_HEIGHT - CFG.PADDING * 2 + iTranslateY,
               this.getWidth(),
               CFG.TEXT_HEIGHT + CFG.PADDING * 2
            );
         oSB.setColor(Color.WHITE);
         oSB.setShader(Renderer.shaderAlpha);
         if (this.getFlagCivID() < 0) {
            ImageManager.getImage(Images.rebelsFlag).getTexture().bind(1);
         } else {
            Game.getCiv(this.getFlagCivID()).getFlag().getTexture().bind(1);
         }

         Gdx.gl.glActiveTexture(33984);
         ImageManager.getImage(Images.flagMaskDefault)
            .draw(
               oSB,
               this.getPosX()
                  + iTranslateX
                  + (ImageManager.getImage(Images.flagOverDefault).getWidth() - ImageManager.getImage(Images.flagMaskDefault).getWidth()) / 2,
               this.getPosY()
                  + iTranslateY
                  + (ImageManager.getImage(Images.flagOverDefault).getHeight() - ImageManager.getImage(Images.flagMaskDefault).getHeight()) / 2,
               ImageManager.getImage(Images.flagMaskDefault).getWidth(),
               ImageManager.getImage(Images.flagMaskDefault).getHeight()
            );
         oSB.flush();
         oSB.setShader(Renderer.shaderDefault);
         ImageManager.getImage(Images.flagOverDefault).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY);
         Renderer.drawText(
            oSB,
            this.fontID,
            this.sText,
            this.getPosX() + this.textPosition.getTextPosition() + iTranslateX,
            this.getPosY()
               + ImageManager.getImage(Images.flagOverDefault).getHeight()
               + (CFG.TEXT_HEIGHT + CFG.PADDING * 2) / 2
               - this.getTextHeight() / 2
               + iTranslateY,
            this.getColor(isActive)
         );
      }
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
   }

   public static final float getBoxAlpha(boolean clickable, boolean isHovered, boolean isActive) {
      return clickable ? (isActive ? 0.85F : (isHovered ? 0.7F : 0.5F)) : 0.2F;
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
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Rebels"), Colors.HOVER_GOLD));
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
   public void setIsHovered(boolean isHovered) {
      super.setIsHovered(isHovered);
      if (isHovered && this.iCivID > 0) {
         ProvinceDraw.drawProvincesCiv_HoveredFlagID = this.iCivID;
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
