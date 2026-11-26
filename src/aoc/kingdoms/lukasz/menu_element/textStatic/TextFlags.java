package aoc.kingdoms.lukasz.menu_element.textStatic;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.button.Button;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Flag;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Image;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class TextFlags extends Button {
   public List<Integer> lCivID = new ArrayList<>();
   public int iCivsSize = 0;
   public int flagWidth;
   public int flagHeight;

   public TextFlags(String sText, List<Integer> tCivs, int nPosX, int nPosY, int nWidth, int nHeight) {
      this.init(sText, CFG.FONT_REGULAR_SMALL, -1, nPosX, nPosY, nWidth, nHeight, true, true, false, false);
      this.lCivID = tCivs;
      this.iCivsSize = this.lCivID.size();
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
         if (this.getPosX() + (this.flagWidth + CFG.PADDING) * i + CFG.PADDING * 2 + this.getTextWidth() + this.flagWidth + 2 < this.getWidth()) {
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
                  ImageManager.getImage(Images.rebelsFlag)
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
   }

   @Override
   protected Color getColor(boolean isActive) {
      return Colors.getColorTopStatsHover(isActive, this.getIsHovered());
   }

   private final float getImageScale() {
      return (float)CFG.TEXT_HEIGHT / CFG.CIV_FLAG_HEIGHT;
   }

   @Override
   public void buildElementHover() {
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      nData.add(new MenuElement_HoverElement_Type_Text(this.getText(), Colors.HOVER_GOLD));

      for (int i = 0; i < this.lCivID.size(); i++) {
         if (this.lCivID.get(i) < 0) {
            nData.add(new MenuElement_HoverElement_Type_Image(Images.rebelsFlag, CFG.PADDING, 0));
         } else {
            nData.add(new MenuElement_HoverElement_Type_Flag(this.lCivID.get(i), CFG.PADDING, 0));
         }
      }

      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Line());
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();

      for (int ix = 0; ix < this.lCivID.size(); ix++) {
         if (this.lCivID.get(ix) < 0) {
            nData.add(new MenuElement_HoverElement_Type_Image(Images.rebelsFlag, 0, CFG.PADDING));
            nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Rebels")));
         } else {
            nData.add(new MenuElement_HoverElement_Type_Flag(this.lCivID.get(ix), 0, CFG.PADDING));
            nData.add(new MenuElement_HoverElement_Type_Text(Game.getCiv(this.lCivID.get(ix)).getCivName()));
         }

         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      this.menuElementHover = new MenuElement_Hover(nElements);
   }
}
