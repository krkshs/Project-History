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
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Button_TextBonus;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Image;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ImageTitle_BG;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menusInGame.InGame_CivilizationAdvantages;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class Button_Advantage2 extends Button {
   public int advantageID;
   public int iLevel;
   protected static long lTimeAnimation = 0L;
   protected static int animationState = 0;
   protected static final int ANIMATION_T = 1000;
   public int imageID;
   public int iconWidth;
   public int iconHeight;
   public String sTextHover;
   public boolean haveAdvantage = false;
   public boolean canUnlock = false;

   public Button_Advantage2(int iPosX, int iPosY, int advantageID, int iLevel, String sTextHover, String sText, int imageID) {
      this.advantageID = advantageID;
      this.iLevel = iLevel;
      this.sTextHover = sTextHover;
      this.imageID = imageID;
      float iconScale = this.getImageScale(imageID) * 1.0F;
      this.iconWidth = (int)(ImageManager.getImage(imageID).getWidth() * iconScale);
      this.iconHeight = (int)(ImageManager.getImage(imageID).getHeight() * iconScale);
      this.init(
         sText,
         CFG.FONT_BOLD_SMALL,
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
      if (Game.getCiv(InGame_CivilizationAdvantages.iActiveCivID).haveAdvantage(advantageID, iLevel)) {
         this.haveAdvantage = true;
      } else if (Game.getCiv(InGame_CivilizationAdvantages.iActiveCivID).canUnlockAdvantage(advantageID, iLevel)) {
         this.canUnlock = true;
      }
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
         if (this.haveAdvantage) {
            AdvantagesManager.advantagesImages
               .get(AdvantagesManager.advantages.get(this.advantageID).ImageID[this.iLevel])
               .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY);
         } else {
            oSB.setShader(Renderer.shaderBlackWhite);

            try {
               AdvantagesManager.advantagesImages
                  .get(AdvantagesManager.advantages.get(this.advantageID).ImageID[this.iLevel])
                  .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY);
            } catch (Exception var6) {
               CFG.exceptionStack(var6);
            }

            oSB.setShader(Renderer.shaderDefault);
         }

         if (this.getIsHovered() || isActive) {
            AdvantagesManager.advantagesImages
               .get(AdvantagesManager.advantages.get(this.advantageID).ImageID[this.iLevel])
               .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY);
         } else if (this.canUnlock) {
            oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.1F));
            AdvantagesManager.advantagesImages
               .get(AdvantagesManager.advantages.get(this.advantageID).ImageID[this.iLevel])
               .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY);
            oSB.setColor(Color.WHITE);
         }
      } catch (Exception var7) {
         CFG.exceptionStack(var7);
      }

      oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.8F));
      Images.pix.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - this.getBonusH() + iTranslateY, this.getWidth(), this.getBonusH());
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.75F));
      Images.gradientFull
         .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - this.getBonusH() + iTranslateY, this.getWidth(), this.getBonusH());
      Images.gradientXY
         .draw(
            oSB,
            this.getPosX() + iTranslateX,
            this.getPosY() + this.getHeight() - this.getBonusH() + iTranslateY,
            this.getWidth(),
            this.getBonusH(),
            false,
            true
         );
      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.1F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - this.getBonusH() + iTranslateY, this.getWidth(), 1);
      oSB.setColor(Color.WHITE);
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
                  this.getPosY() + ImageManager.getImage(Images.advantagesOver).getHeight() - 2 - CFG.PADDING + iTranslateY,
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
                  this.getPosY() + ImageManager.getImage(Images.advantagesOver).getHeight() - 2 - CFG.PADDING + iTranslateY,
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

      Renderer.drawTextWithShadow(
         oSB,
         this.fontID,
         this.sText,
         this.getPosX() + this.getWidth() / 2 - (this.iTextWidth + this.iconWidth + CFG.PADDING) / 2 + iTranslateX,
         this.getPosY() + this.getHeight() - this.getBonusH() / 2 - this.iTextHeight / 2 + iTranslateY,
         this.haveAdvantage ? this.getColor(isActive) : this.getColor2(isActive)
      );
      ImageManager.getImage(this.imageID)
         .draw(
            oSB,
            this.getPosX() + this.getWidth() / 2 - (this.iTextWidth + this.iconWidth + CFG.PADDING) / 2 + this.iTextWidth + CFG.PADDING + iTranslateX,
            this.getPosY() + this.getHeight() - this.getBonusH() / 2 - this.iconHeight / 2 + iTranslateY,
            this.iconWidth,
            this.iconHeight
         );
      ImageManager.getImage(Images.advantagesOver).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY);
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
   }

   public static int getButtonWidth() {
      return ImageManager.getImage(Images.advantagesOver).getHeight();
   }

   public static int getButtonHeight() {
      return ImageManager.getImage(Images.advantagesOver).getHeight();
   }

   public int getBonusH() {
      return CFG.PADDING * 2 + CFG.TEXT_HEIGHT;
   }

   @Override
   public void buildElementHover() {
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      if (InGame_CivilizationAdvantages.iActiveCivID == Game.player.iCivID) {
         nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("UnlockAdvantage"), CFG.FONT_BOLD, Colors.HOVER_GOLD));
         nData.add(new MenuElement_HoverElement_Type_ImageTitle_BG(Images.advantages, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      nData.add(
         new MenuElement_HoverElement_Type_Button_TextBonus(
            this.sTextHover + ": ", this.getText(), this.imageID, CFG.FONT_REGULAR_SMALL, Colors.COLOR_TEXT_MODIFIER_POSITIVE
         )
      );
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      if (InGame_CivilizationAdvantages.iActiveCivID == Game.player.iCivID) {
         nData.add(new MenuElement_HoverElement_Type_Line());
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         if (AdvantagesManager.advantages.get(this.advantageID).RequiredTechID >= 0) {
            nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("UnlockedTechnologiesRequired") + ": ", CFG.FONT_REGULAR_SMALL));
            nData.add(
               new MenuElement_HoverElement_Type_Text(
                  TechnologyTree.lTechnology.get(AdvantagesManager.advantages.get(this.advantageID).RequiredTechID).Name,
                  CFG.FONT_BOLD_SMALL,
                  Colors.HOVER_GOLD
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
      }

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

   @Override
   public int getSFX() {
      return -1;
   }
}
