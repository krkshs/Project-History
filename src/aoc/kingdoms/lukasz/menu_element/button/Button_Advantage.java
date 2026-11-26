package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.AdvantagesManager;
import aoc.kingdoms.lukasz.map.technology.TechnologyTree;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Image;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class Button_Advantage extends Button {
   public int imgID;
   public int advantageID;
   protected static long lTimeAnimation = 0L;
   protected static int animationState = 0;
   protected static final int ANIMATION_T = 1000;

   public Button_Advantage(int iPosX, int iPosY, int imgID, int advantageID) {
      this.imgID = imgID;
      this.advantageID = advantageID;
      this.init(
         "",
         CFG.FONT_REGULAR_SMALL,
         this.iTextPositionX,
         iPosX,
         iPosY,
         ImageManager.getImage(Images.advantagesOver).getWidth(),
         getButtonHeight(),
         true,
         true,
         false,
         false
      );
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      if (this.getIsHovered() || isActive) {
         Renderer.drawBoxCorner(
            oSB,
            this.getPosX() + iTranslateX,
            this.getPosY() + iTranslateY,
            ImageManager.getImage(Images.advantagesOver).getWidth(),
            ImageManager.getImage(Images.advantagesOver).getHeight()
         );
      }

      try {
         AdvantagesManager.advantagesImages.get(this.imgID).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY);
      } catch (Exception var6) {
         CFG.exceptionStack(var6);
      }

      if (this.getIsHovered() || isActive) {
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.75F));
         ImageManager.getImage(Images.gradientVertical).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), CFG.PADDING * 3);
         ImageManager.getImage(Images.gradientVertical)
            .draw(
               oSB,
               this.getPosX() + iTranslateX,
               this.getPosY() + this.getHeight() - CFG.PADDING * 3 + iTranslateY,
               this.getWidth(),
               CFG.PADDING * 3,
               false,
               true
            );
         oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.5F));
         Images.gradientXY
            .draw(
               oSB,
               this.getPosX() + iTranslateX,
               this.getPosY() + iTranslateY,
               ImageManager.getImage(Images.advantagesOver).getWidth(),
               ImageManager.getImage(Images.advantagesOver).getHeight() / 2
            );
         Images.gradientXY
            .draw(
               oSB,
               this.getPosX() + iTranslateX,
               this.getPosY() + ImageManager.getImage(Images.advantagesOver).getHeight() / 2 + iTranslateY,
               ImageManager.getImage(Images.advantagesOver).getWidth(),
               ImageManager.getImage(Images.advantagesOver).getHeight() / 2,
               false,
               true
            );
         oSB.setColor(Color.WHITE);
         ImageManager.getImage(Images.advantages)
            .draw(
               oSB,
               this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.advantages).getWidth() / 2 + iTranslateX,
               this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(Images.advantages).getHeight() / 2 + iTranslateY
            );
      }

      if (this.getClickable() && this.getIsHovered() && animationState >= 0) {
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
                  this.getPosY() + this.getHeight() - 2 - CFG.PADDING + iTranslateY,
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
                  this.getPosY() + this.getHeight() - 2 - CFG.PADDING + iTranslateY,
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

      ImageManager.getImage(Images.advantagesOver).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY);
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
   }

   public static int getButtonHeight() {
      return ImageManager.getImage(Images.advantagesOver).getHeight();
   }

   @Override
   public void buildElementHover() {
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("UnlockAdvantage"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
      nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.advantages, CFG.PADDING, 0));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      if (AdvantagesManager.advantages.get(this.advantageID).RequiredTechID >= 0) {
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("RequiredTechnology") + ": ", CFG.FONT_REGULAR_SMALL));
         nData.add(
            new MenuElement_HoverElement_Type_Text(
               TechnologyTree.lTechnology.get(AdvantagesManager.advantages.get(this.advantageID).RequiredTechID).Name, CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD
            )
         );
         nData.add(new MenuElement_HoverElement_Type_Image(Game_Calendar.IMG_TECHNOLOGY, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("Cost") + ": ", CFG.FONT_REGULAR_SMALL));
      nData.add(new MenuElement_HoverElement_Type_Text("1", CFG.FONT_BOLD_SMALL, Colors.HOVER_GOLD));
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
   public int getCurrent() {
      return this.advantageID;
   }
}
