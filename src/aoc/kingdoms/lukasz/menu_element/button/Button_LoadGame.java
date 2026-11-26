package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Image;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menus.Menu_LoadGames_List;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class Button_LoadGame extends Button {
   public int id;
   public String sText2;
   public int iTextWidth2;
   public int iTextHeight2;

   public Button_LoadGame(String sText, String sText2, int iPosX, int iPosY, int nWidth, int id) {
      this.init(sText, CFG.FONT_REGULAR, 0, iPosX, iPosY, nWidth, getButtonHeight(), true, true, false, false);
      this.id = id;
      this.sText2 = sText2;
      Renderer.glyphLayout.setText(Renderer.fontMain.get(this.fontID), sText2);
      this.iTextWidth2 = (int)Renderer.glyphLayout.width;
      this.iTextHeight2 = (int)Renderer.glyphLayout.height;
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      if (this.getIsHovered() || isActive) {
         Renderer.drawBoxCorner(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      }

      oSB.setColor(
         new Color(
            Colors.COLOR_NOTIFICATION_BG.r, Colors.COLOR_NOTIFICATION_BG.g, Colors.COLOR_NOTIFICATION_BG.b, !this.getIsHovered() && !isActive ? 0.25F : 0.5F
         )
      );
      Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
      oSB.setColor(
         new Color(
            Colors.COLOR_GRADIENT_BG_BLUE.r, Colors.COLOR_GRADIENT_BG_BLUE.g, Colors.COLOR_GRADIENT_BG_BLUE.b, !this.getIsHovered() && !isActive ? 0.75F : 1.0F
         )
      );
      Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, getButtonHeight(), this.getHeight(), 1.0F);
      oSB.setColor(Color.WHITE);
      oSB.setColor(new Color(Colors.COLOR_NOTIFICATION_OVER.r, Colors.COLOR_NOTIFICATION_OVER.g, Colors.COLOR_NOTIFICATION_OVER.b, 0.3F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      oSB.setColor(new Color(Colors.COLOR_NOTIFICATION_OVER.r, Colors.COLOR_NOTIFICATION_OVER.g, Colors.COLOR_NOTIFICATION_OVER.b, 0.35F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      oSB.setColor(new Color(Colors.COLOR_NOTIFICATION_OVER.r, Colors.COLOR_NOTIFICATION_OVER.g, Colors.COLOR_NOTIFICATION_OVER.b, 0.3F));
      Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.25F));
      Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), CFG.PADDING * 2, false, true);
      Images.gradientXY
         .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - CFG.PADDING * 2 + iTranslateY, this.getWidth(), CFG.PADDING * 2);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.25F));
      Renderer.drawBox(oSB, Images.statsRectBGBorder, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
      oSB.setColor(new Color(Colors.COLOR_NOTIFICATION_BG.r, Colors.COLOR_NOTIFICATION_BG.g, Colors.COLOR_NOTIFICATION_BG.b, 0.25F));
      Renderer.drawBox(
         oSB, Images.statsRectBGBorder, this.getPosX() - 1 + iTranslateX, this.getPosY() - 1 + iTranslateY, this.getWidth() + 2, this.getHeight() + 2, 1.0F
      );
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 1 + iTranslateY, this.getWidth(), 1);
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), 1);
      oSB.setColor(new Color(Colors.COLOR_NOTIFICATION_OVER.r, Colors.COLOR_NOTIFICATION_OVER.g, Colors.COLOR_NOTIFICATION_OVER.b, 0.85F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 2 + iTranslateY, this.getWidth(), 1);
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + 1 + iTranslateY, this.getWidth(), 1);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.55F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 1 + iTranslateY, this.getWidth(), 1);
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), 1);
      oSB.setColor(new Color(Colors.COLOR_NOTIFICATION_OVER.r, Colors.COLOR_NOTIFICATION_OVER.g, Colors.COLOR_NOTIFICATION_OVER.b, 0.9F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 2 + iTranslateY, this.getWidth(), 1);
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + 1 + iTranslateY, this.getWidth(), 1);
      oSB.setColor(
         new Color(
            Colors.COLOR_GRADIENT_BG_BLUE.r, Colors.COLOR_GRADIENT_BG_BLUE.g, Colors.COLOR_GRADIENT_BG_BLUE.b, !this.getIsHovered() && !isActive ? 0.3F : 0.5F
         )
      );
      Renderer.drawBox(
         oSB,
         Images.statsRectBG,
         this.getPosX() + this.getInnerPosX() + CFG.PADDING + iTranslateX,
         this.getPosY() + CFG.PADDING + iTranslateY,
         this.getInnerWidth() - CFG.PADDING * 2,
         (getButtonHeight() - CFG.PADDING * 3) / 2,
         1.0F
      );
      oSB.setColor(Color.WHITE);
      this.drawImage(oSB, iTranslateX, iTranslateY, isActive);
   }

   protected void drawImage(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      oSB.setShader(Renderer.shaderAlpha);

      try {
         Menu_LoadGames_List.lFlags.get(this.id).getTexture().bind(1);
         Gdx.gl.glActiveTexture(33984);
         ImageManager.getImage(Images.flagMaskDefault)
            .draw(
               oSB,
               this.getPosX()
                  + getPaddingIMG()
                  + iTranslateX
                  + (ImageManager.getImage(Images.flagOverDefault).getWidth() - ImageManager.getImage(Images.flagMaskDefault).getWidth()) / 2,
               this.getPosY()
                  + getPaddingIMG()
                  + iTranslateY
                  + (ImageManager.getImage(Images.flagOverDefault).getHeight() - ImageManager.getImage(Images.flagMaskDefault).getHeight()) / 2,
               ImageManager.getImage(Images.flagMaskDefault).getWidth(),
               ImageManager.getImage(Images.flagMaskDefault).getHeight()
            );
      } catch (Exception var6) {
         CFG.exceptionStack(var6);
      }

      oSB.flush();
      oSB.setShader(Renderer.shaderDefault);
      ImageManager.getImage(Images.flagOverDefault).draw(oSB, this.getPosX() + getPaddingIMG() + iTranslateX, this.getPosY() + getPaddingIMG() + iTranslateY);
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      Renderer.drawText(
         oSB,
         this.fontID,
         this.sText,
         this.getPosX() + this.getInnerPosX() + CFG.PADDING * 3 + iTranslateX,
         this.getPosY() + getButtonHeight() / 4 - this.iTextHeight / 2 + iTranslateY,
         this.getColor(isActive)
      );
      Renderer.drawText(
         oSB,
         this.fontID,
         this.sText2,
         this.getPosX() + this.getInnerPosX() + CFG.PADDING * 3 + iTranslateX,
         this.getPosY() + getButtonHeight() / 2 + getButtonHeight() / 4 - this.iTextHeight2 / 2 - CFG.PADDING / 2 + iTranslateY,
         this.getColor(isActive)
      );
   }

   public static int getButtonHeight() {
      return ImageManager.getImage(Images.flagOverDefault).getHeight() + getPaddingIMG() * 2;
   }

   public static int getPaddingIMG() {
      return CFG.PADDING * 2;
   }

   public int getInnerPosX() {
      return getPaddingIMG() * 2 + ImageManager.getImage(Images.flagOverDefault).getWidth();
   }

   public int getInnerWidth() {
      return this.getWidth() - this.getInnerPosX();
   }

   @Override
   protected Color getColor(boolean isActive) {
      return Colors.getColorButtonHover2(isActive, this.getIsHovered());
   }

   @Override
   public void buildElementHover() {
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("LoadGame"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
      nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.save, CFG.PADDING, 0));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(
         new MenuElement_HoverElement_Type_Button_TextBonus(
            this.sText, "", Images.council, CFG.FONT_REGULAR_SMALL, CFG.FONT_BOLD_SMALL, Colors.HOVER_LEFT, Colors.HOVER_GOLD
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Line());
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Text(this.sText2, CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
      nData.add(new MenuElement_HoverElement_Type_Image(Images.time, CFG.PADDING, 0));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      this.menuElementHover = new MenuElement_Hover(nElements);
   }

   @Override
   public int getCurrent() {
      return this.id;
   }
}
