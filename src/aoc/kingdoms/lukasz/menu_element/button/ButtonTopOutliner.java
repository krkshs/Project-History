package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Touch;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menusInGame.InGame;
import aoc.kingdoms.lukasz.menusInGame.Right.InGame_Right;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class ButtonTopOutliner extends Button {
   public int imageID;

   public ButtonTopOutliner(int iPosY, int nHeight) {
      this.imageID = Images.outliner;
      this.init(this.sText, 0, this.iTextPositionX, 0, iPosY, ImageManager.getImage(this.imageID).getWidth() + getPaddingX(), nHeight, true, true, false, false);
   }

   public ButtonTopOutliner(int iPosX, int iPosY, int nHeight, int imageID) {
      this.imageID = imageID;
      this.init(this.sText, 0, this.iTextPositionX, iPosX, iPosY, ImageManager.getImage(imageID).getWidth() + getPaddingX(), nHeight, true, true, false, false);
   }

   public ButtonTopOutliner(int iPosX, int iPosY, int nWidth, int nHeight, int imageID) {
      this.imageID = imageID;
      this.init(this.sText, 0, this.iTextPositionX, iPosX, iPosY, nWidth, nHeight, true, true, false, false);
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.1F));
      Images.gradientFull
         .draw(oSB, this.getPosX() + InGame.outlinerExtraX + iTranslateX, this.getPosY() + 1 + iTranslateY, this.getWidth(), this.getHeight() - 2);
      Images.gradientXY
         .draw(oSB, this.getPosX() + InGame.outlinerExtraX + iTranslateX, this.getPosY() + 1 + iTranslateY, this.getWidth(), this.getHeight() - 2);
      Images.gradientXY
         .draw(oSB, this.getPosX() + InGame.outlinerExtraX + iTranslateX, this.getPosY() + 1 + iTranslateY, this.getWidth(), this.getHeight() - 2, false, true);
      oSB.setColor(Color.WHITE);
      if (this.getIsHovered() || isActive) {
         oSB.setColor(new Color(Colors.HOVER_GOLD.r, Colors.HOVER_GOLD.g, Colors.HOVER_GOLD.b, 0.5F));
         Images.gradientXY
            .draw(oSB, this.getPosX() + InGame.outlinerExtraX + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), CFG.PADDING, false, true);
         Images.gradientXY
            .draw(
               oSB,
               this.getPosX() + InGame.outlinerExtraX + iTranslateX,
               this.getPosY() + this.getHeight() - CFG.PADDING + iTranslateY,
               this.getWidth(),
               CFG.PADDING
            );
         oSB.setColor(Color.WHITE);
      }
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      if (!CFG.isDesktop()) {
         if (Touch.selectArmiesMode) {
            oSB.setColor(new Color(Colors.HOVER_GOLD.r, Colors.HOVER_GOLD.g, Colors.HOVER_GOLD.b, 0.15F));
         } else {
            oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.075F));
         }

         Images.pix
            .draw(
               oSB,
               this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(this.imageID).getHeight() / 2 + iTranslateX,
               this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(this.imageID).getHeight() / 2 + iTranslateY,
               ImageManager.getImage(this.imageID).getHeight(),
               ImageManager.getImage(this.imageID).getHeight()
            );
         if (Touch.selectArmiesMode) {
            oSB.setColor(new Color(Colors.HOVER_GOLD.r, Colors.HOVER_GOLD.g, Colors.HOVER_GOLD.b, 0.375F));
         } else {
            oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.275F));
         }

         Renderer.drawBox2(
            oSB,
            this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(this.imageID).getHeight() / 2 + iTranslateX,
            this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(this.imageID).getHeight() / 2 + iTranslateY,
            ImageManager.getImage(this.imageID).getHeight(),
            ImageManager.getImage(this.imageID).getHeight(),
            1.0F
         );
      } else {
         oSB.setColor(this.getColor(isActive));
         ImageManager.getImage(this.imageID)
            .draw(
               oSB,
               this.getPosX() + this.getWidth() / 2 + InGame.outlinerExtraX - ImageManager.getImage(this.imageID).getWidth() / 2 + iTranslateX,
               this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(this.imageID).getHeight() / 2 + iTranslateY
            );
      }

      oSB.setColor(Color.WHITE);
   }

   public static int getPaddingX() {
      return CFG.PADDING * 4;
   }

   @Override
   protected Color getColor(boolean isActive) {
      return Colors.getColorTopStats_Outliner(isActive, this.getIsHovered());
   }

   @Override
   public void buildElementHover() {
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      if (!CFG.isDesktop()) {
         nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("SelectMoreThanOneArmy"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
      } else {
         nData.add(
            new MenuElement_HoverElement_Type_TextTitle_BG(
               InGame_Right.outlinerInView ? Game.lang.get("HidePinned") : Game.lang.get("ShowPinned"), CFG.FONT_BOLD, Colors.HOVER_GOLD
            )
         );
      }

      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      this.menuElementHover = new MenuElement_Hover(nElements, true);
   }
}
