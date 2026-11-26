package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.AdvantagesManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Image;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class Button_Advantage3 extends Button {
   public int advantageID;
   public int iLevel;
   protected static long lTimeAnimation = 0L;
   protected static int animationState = 0;
   protected static final int ANIMATION_T = 1000;
   public int imageID;
   public int iconWidth;
   public int iconHeight;
   public String sTextHover;

   public Button_Advantage3(int iPosX, int iPosY, int advantageID, int iLevel, String sTextHover, String sText, int imageID) {
      this.advantageID = advantageID;
      this.iLevel = iLevel;
      this.sTextHover = sTextHover;
      this.imageID = imageID;
      float iconScale = this.getImageScale(imageID) * 1.0F;
      this.iconWidth = (int)(ImageManager.getImage(imageID).getWidth() * iconScale);
      this.iconHeight = (int)(ImageManager.getImage(imageID).getHeight() * iconScale);
      this.init(
         sText,
         CFG.FONT_REGULAR_SMALL,
         this.iTextPositionX,
         iPosX,
         iPosY,
         ImageManager.getImage(Images.advantagesSmallOver).getWidth(),
         getButtonHeight(),
         true,
         true,
         false,
         false
      );
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      oSB.setShader(Renderer.shaderAlpha);

      try {
         AdvantagesManager.advantagesImages.get(AdvantagesManager.advantages.get(this.advantageID).ImageID[this.iLevel]).getTexture().bind(1);
         Gdx.gl.glActiveTexture(33984);
         ImageManager.getImage(Images.advantagesSmallMask)
            .draw(
               oSB,
               this.getPosX() + iTranslateX,
               this.getPosY() + iTranslateY,
               ImageManager.getImage(Images.advantagesSmallMask).getWidth(),
               ImageManager.getImage(Images.advantagesSmallMask).getHeight()
            );
      } catch (Exception var6) {
         CFG.exceptionStack(var6);
      }

      oSB.flush();
      oSB.setShader(Renderer.shaderDefault);
      if (this.getIsHovered() && animationState >= 0) {
         if (animationState == 0) {
            float drawPerc = Math.min(1.0F * (float)(CFG.currentTimeMillis - lTimeAnimation) / 1000.0F, 1.0F);
            oSB.setColor(ButtonGame.getColorLine());
            Images.gradientXY
               .draw(
                  oSB,
                  this.getPosX() + CFG.PADDING + iTranslateX,
                  this.getPosY() + 1 + iTranslateY,
                  (int)((this.getWidth() - CFG.PADDING * 2) * drawPerc),
                  CFG.PADDING,
                  false,
                  true
               );
            Images.gradientXY
               .draw(
                  oSB,
                  this.getPosX() + CFG.PADDING + iTranslateX,
                  this.getPosY() + ImageManager.getImage(Images.advantagesSmallOver).getHeight() - 2 - CFG.PADDING + iTranslateY,
                  (int)((this.getWidth() - CFG.PADDING * 2) * drawPerc),
                  CFG.PADDING
               );
            if (lTimeAnimation < CFG.currentTimeMillis - 1000L) {
               animationState++;
               lTimeAnimation = CFG.currentTimeMillis;
            }
         } else {
            float drawPerc = Math.min(1.0F * (float)(CFG.currentTimeMillis - lTimeAnimation) / 1000.0F, 1.0F);
            oSB.setColor(ButtonGame.getColorLine());
            Images.gradientXY
               .draw(
                  oSB,
                  this.getPosX() + CFG.PADDING + (int)((this.getWidth() - CFG.PADDING * 2) * drawPerc) + iTranslateX,
                  this.getPosY() + 1 + iTranslateY,
                  this.getWidth() - CFG.PADDING * 2 - (int)((this.getWidth() - CFG.PADDING * 2) * drawPerc),
                  CFG.PADDING,
                  false,
                  true
               );
            Images.gradientXY
               .draw(
                  oSB,
                  this.getPosX() + CFG.PADDING + (int)((this.getWidth() - CFG.PADDING * 2) * drawPerc) + iTranslateX,
                  this.getPosY() + ImageManager.getImage(Images.advantagesSmallOver).getHeight() - 2 - CFG.PADDING + iTranslateY,
                  this.getWidth() - CFG.PADDING * 2 - (int)((this.getWidth() - CFG.PADDING * 2) * drawPerc),
                  CFG.PADDING
               );
            if (lTimeAnimation < CFG.currentTimeMillis - 1000L) {
               animationState = 0;
               lTimeAnimation = CFG.currentTimeMillis;
            }
         }

         oSB.setColor(Color.WHITE);
      }

      ImageManager.getImage(Images.advantagesSmallOver).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY);
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
   }

   public static int getButtonWidth() {
      return ImageManager.getImage(Images.advantagesSmallOver).getHeight();
   }

   public static int getButtonHeight() {
      return ImageManager.getImage(Images.advantagesSmallOver).getHeight();
   }

   public int getBonusH() {
      return CFG.PADDING * 2 + CFG.TEXT_HEIGHT;
   }

   @Override
   public void buildElementHover() {
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      nData.add(
         new MenuElement_HoverElement_Type_Button_TextBonus(
            this.sTextHover + ": ", "" + this.getText(), this.imageID, CFG.FONT_REGULAR, CFG.FONT_BOLD, Colors.HOVER_LEFT, Colors.HOVER_GOLD
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Line());
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("CivilizationAdvantage"), CFG.FONT_BOLD_SMALL));
      nData.add(new MenuElement_HoverElement_Type_Image(Images.advantages, CFG.PADDING, 0));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      this.menuElementHover = new MenuElement_Hover(nElements);
   }

   @Override
   public void setIsHovered(boolean isHovered) {
      super.setIsHovered(isHovered);
      lTimeAnimation = CFG.currentTimeMillis;
      animationState = 0;
   }

   @Override
   public String getTextToDraw() {
      return this.sTextHover;
   }

   @Override
   public int getValue1() {
      return this.advantageID;
   }

   @Override
   public int getValue2() {
      return this.iLevel;
   }

   public final float getImageScale(int iImageID) {
      return (float)CFG.TEXT_HEIGHT / ImageManager.getImage(iImageID).getHeight();
   }

   @Override
   protected Color getColor(boolean isActive) {
      if (isActive) {
         return Colors.COLOR_TEXT_MODIFIER_POSITIVE_ACTIVE;
      } else {
         return this.getIsHovered() ? Colors.COLOR_TEXT_MODIFIER_POSITIVE_HOVER : Colors.COLOR_TEXT_MODIFIER_POSITIVE;
      }
   }

   protected Color getColor2(boolean isActive) {
      return Colors.getColorButtonHover2(isActive, this.getIsHovered());
   }
}
