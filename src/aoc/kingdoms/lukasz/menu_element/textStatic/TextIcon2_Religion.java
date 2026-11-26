package aoc.kingdoms.lukasz.menu_element.textStatic;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.province.ProvinceDraw;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.button.Button;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Image;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_ReligionTitle;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menusInGame.Province.InGame_ProvinceInfo;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class TextIcon2_Religion extends Button {
   protected static long lTimeAnimation = 0L;
   protected static int animationState = 0;
   protected static final int ANIMATION_T = 1000;
   public int religionID;
   private int imgWidth = 0;
   private int imgHeight = 0;
   public int iProvinceID;

   public TextIcon2_Religion(String sText, int religionID, int nPosX, int nPosY, int nWidth, int nHeight, int iProvinceID) {
      this.init(sText, CFG.FONT_REGULAR_SMALL, 0, nPosX, nPosY, nWidth, nHeight, true, true, false, false);
      this.religionID = religionID;
      this.iProvinceID = iProvinceID;
      this.imgWidth = (int)(Game.religionManager.religionImages.get(religionID).getWidth() * this.getImageScale());
      this.imgHeight = (int)(Game.religionManager.religionImages.get(religionID).getHeight() * this.getImageScale());
      int tWMax = 0;

      while (this.iTextWidth > this.getWidth() - CFG.PADDING && this.getText().length() > 5) {
         if (++tWMax >= 100) {
            break;
         }

         this.setText(this.getText().substring(0, Math.max(1, this.getText().length() - 3)) + "..");
      }
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
   }

   public static Color getColor_gradientXY() {
      return new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.7F);
   }

   public static Color getColor_gradientFull() {
      return new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.45F);
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      oSB.setColor(
         new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, !this.getIsHovered() && !isActive ? 0.5F : 0.6F)
      );
      Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
      oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.3F));
      Renderer.drawBox(
         oSB,
         Images.statsRectBG,
         this.getPosX() + iTranslateX,
         this.getPosY() + this.getHeight() - this.getTextH() + iTranslateY,
         this.getWidth(),
         this.getTextH(),
         1.0F
      );
      oSB.setColor(Color.WHITE);
      if (Game.getProvince(this.iProvinceID).religionConversion != null) {
         oSB.setColor(new Color(ProvinceDraw.progressBar.r, ProvinceDraw.progressBar.g, ProvinceDraw.progressBar.b, 0.5F));
         Renderer.drawBox(
            oSB,
            Images.statsRectBG,
            this.getPosX() + iTranslateX,
            this.getPosY() + this.getHeight() - this.getTextH() + iTranslateY,
            this.getWidth(),
            this.getTextH(),
            1.0F
         );
      }

      oSB.setColor(Color.WHITE);
      oSB.setColor(getColor_gradientXY());
      Images.gradientXY
         .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - this.getTextH() + iTranslateY, this.getWidth(), this.getTextH());
      oSB.setColor(getColor_gradientFull());
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - this.getTextH() + iTranslateY, this.getWidth(), 1);
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 1 + iTranslateY, this.getWidth(), 1);
      oSB.setColor(Color.WHITE);
      if (this.getIsHovered() || isActive) {
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 1.0F));
         Renderer.drawBox(oSB, Images.statsRectBGBorder, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
         oSB.setColor(Color.WHITE);
      }

      try {
         if (Game.getProvince(this.iProvinceID).religionConversion != null) {
            oSB.setColor(new Color(ProvinceDraw.progressBar));
            Renderer.drawBoxProgress(
               oSB,
               Images.statsRectBG,
               this.getPosX() + iTranslateX,
               this.getPosY() + this.getHeight() - this.getTextH() + iTranslateY,
               (int)(
                  this.getWidth()
                     * (
                        1.0F
                           - (float)Game.getProvince(this.iProvinceID).religionConversion.daysLeft
                              / Game.getProvince(this.iProvinceID).religionConversion.investTime
                     )
               ),
               this.getTextH(),
               this.getWidth()
            );
            oSB.setColor(Color.WHITE);
         }
      } catch (Exception var6) {
         CFG.exceptionStack(var6);
      }

      if (this.getClickable() && this.getIsHovered() && animationState >= 0) {
         if (animationState == 0) {
            float drawPerc = Math.min(1.0F * (float)(CFG.currentTimeMillis - lTimeAnimation) / 1000.0F, 1.0F);
            oSB.setColor(ButtonGame.getColorLine());
            Images.line_32_off1
               .draw(
                  oSB, this.getPosX() + CFG.PADDING + iTranslateX, this.getPosY() + 1 + iTranslateY, (int)((this.getWidth() - CFG.PADDING * 2) * drawPerc), 1
               );
            Images.line_32_off1
               .draw(
                  oSB,
                  this.getPosX() + CFG.PADDING + iTranslateX,
                  this.getPosY() + this.getHeight() - 2 + iTranslateY,
                  (int)((this.getWidth() - CFG.PADDING * 2) * drawPerc),
                  1
               );
            if (lTimeAnimation < CFG.currentTimeMillis - 1000L) {
               animationState++;
               lTimeAnimation = CFG.currentTimeMillis;
            }
         } else {
            float drawPerc = Math.min(1.0F * (float)(CFG.currentTimeMillis - lTimeAnimation) / 1000.0F, 1.0F);
            oSB.setColor(ButtonGame.getColorLine());
            Images.line_32_off1
               .draw(
                  oSB,
                  this.getPosX() + CFG.PADDING + (int)((this.getWidth() - CFG.PADDING * 2) * drawPerc) + iTranslateX,
                  this.getPosY() + 1 + iTranslateY,
                  this.getWidth() - CFG.PADDING * 2 - (int)((this.getWidth() - CFG.PADDING * 2) * drawPerc),
                  1
               );
            Images.line_32_off1
               .draw(
                  oSB,
                  this.getPosX() + CFG.PADDING + (int)((this.getWidth() - CFG.PADDING * 2) * drawPerc) + iTranslateX,
                  this.getPosY() + this.getHeight() - 2 + iTranslateY,
                  this.getWidth() - CFG.PADDING * 2 - (int)((this.getWidth() - CFG.PADDING * 2) * drawPerc),
                  1
               );
            if (lTimeAnimation < CFG.currentTimeMillis - 1000L) {
               animationState = 0;
               lTimeAnimation = CFG.currentTimeMillis;
            }
         }

         oSB.setColor(Color.WHITE);
      }

      Game.religionManager
         .religionImages
         .get(this.religionID)
         .draw(
            oSB,
            this.getPosX() + this.getWidth() / 2 - this.imgWidth / 2 + iTranslateX,
            this.getPosY() + (this.getHeight() - this.getTextH()) / 2 - this.imgHeight / 2 + iTranslateY,
            this.imgWidth,
            this.imgHeight
         );
      Renderer.drawText(
         oSB,
         this.fontID,
         this.getTextToDraw(),
         this.getPosX() + this.getWidth() / 2 - this.getTextWidth() / 2 + iTranslateX,
         this.getPosY() + this.getHeight() - CFG.PADDING - this.getTextHeight() + iTranslateY,
         this.getColor(isActive)
      );
   }

   public int getTextH() {
      return CFG.TEXT_HEIGHT + CFG.PADDING * 2;
   }

   @Override
   protected Color getColor(boolean isActive) {
      if (Game.getCiv(Game.getProvince(this.iProvinceID).getCivID()).getReligionID() != Game.getProvince(this.iProvinceID).getReligion()) {
         if (isActive) {
            return Colors.COLOR_TEXT_MODIFIER_NEGATIVE_ACTIVE;
         } else {
            return this.getIsHovered() ? Colors.COLOR_TEXT_MODIFIER_NEGATIVE_HOVER : Colors.COLOR_TEXT_MODIFIER_NEGATIVE;
         }
      } else {
         return Colors.getColorButtonHover(isActive, this.getIsHovered());
      }
   }

   @Override
   public void buildElementHover() {
      List<MenuElement_HoverElement> nElements = new ArrayList<>();
      List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
      nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Religion") + ": ", CFG.FONT_BOLD));
      nData.add(new MenuElement_HoverElement_Type_TextTitle(this.getText(), CFG.FONT_BOLD, Colors.HOVER_GOLD));
      nData.add(new MenuElement_HoverElement_Type_ReligionTitle(this.religionID, CFG.PADDING, 0));
      nElements.add(new MenuElement_HoverElement(nData));
      nData.clear();
      if (Game.getProvince(this.iProvinceID).getReligion() != Game.getCiv(Game.getProvince(this.iProvinceID).getCivID()).getReligionID()) {
         nData.add(new MenuElement_HoverElement_Type_Line());
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
         nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("DifferentReligion") + ": ", CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT3));
         nData.add(
            new MenuElement_HoverElement_Type_Text(
               "" + CFG.getPrecision2(GameValues.religion.BASE_INCOME_DIFFERENT_RELIGION * 100.0F, 100) + "%", CFG.FONT_BOLD_SMALL, Colors.HOVER_NEGATIVE
            )
         );
         nData.add(new MenuElement_HoverElement_Type_Image(Images.gold, CFG.PADDING, 0));
         nElements.add(new MenuElement_HoverElement(nData));
         nData.clear();
      }

      this.menuElementHover = new MenuElement_Hover(nElements) {
         @Override
         public int getMinPosX() {
            return InGame_ProvinceInfo.HOVER_POSX;
         }

         @Override
         public void draw(SpriteBatch oSB, int nPosX, int nPosY) {
            nPosY = InGame_ProvinceInfo.HOVER_POSY + (CFG.GAME_HEIGHT - InGame_ProvinceInfo.HOVER_POSY) / 2 - this.iHeight / 2;
            super.draw(oSB, nPosX, nPosY);
         }
      };
   }

   private final float getImageScale() {
      return (float)ImageManager.getImage(Images.gold).getHeight() / Game.religionManager.religionImages.get(this.religionID).getHeight();
   }

   @Override
   public void setIsHovered(boolean isHovered) {
      super.setIsHovered(isHovered);
      lTimeAnimation = CFG.currentTimeMillis;
      animationState = 0;
   }

   @Override
   public String getTextToDraw() {
      if (this.religionID != Game.getProvince(this.iProvinceID).getReligion()) {
         this.setText("" + Game.religionManager.getReligion(Game.getProvince(this.iProvinceID).getReligion()).Name);
         this.religionID = Game.getProvince(this.iProvinceID).getReligion();
         this.imgWidth = (int)(Game.religionManager.religionImages.get(this.religionID).getWidth() * this.getImageScale());
         this.imgHeight = (int)(Game.religionManager.religionImages.get(this.religionID).getHeight() * this.getImageScale());
      }

      return super.getTextToDraw();
   }
}
