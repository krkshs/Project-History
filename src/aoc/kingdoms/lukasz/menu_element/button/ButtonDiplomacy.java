package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.SoundsManager;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.MenuManager;
import aoc.kingdoms.lukasz.menus.MainMenu;
import aoc.kingdoms.lukasz.menusInGame.Civ.InGame_Civ;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class ButtonDiplomacy extends Button {
   public int imageID;
   public List<String> sLines = new ArrayList<>();
   public List<Integer> iLinesWidth = new ArrayList<>();
   public int iLineSize = 0;
   public int iY_Text = 0;
   public int iconY = 0;
   public int iconH = 0;

   public ButtonDiplomacy(String sText, int imageID, int iPosX, int iPosY, int iWidth, int iHeight) {
      this.imageID = imageID;
      this.iconY = iHeight / 10 + InGame_Civ.iMaxH_DiplomacyIcon / 2 - ImageManager.getImage(imageID).getHeight() / 2;
      this.iconH = iHeight / 5 + InGame_Civ.iMaxH_DiplomacyIcon;
      this.init(sText, CFG.FONT_REGULAR_SMALL, this.iTextPositionX, iPosX, iPosY, iWidth, iHeight, true, true, false, false);
   }

   public ButtonDiplomacy(String sText, int imageID, int iPosX, int iPosY, int iWidth, int iHeight, boolean clickable) {
      this.imageID = imageID;
      this.iconY = iHeight / 10 + InGame_Civ.iMaxH_DiplomacyIcon / 2 - ImageManager.getImage(imageID).getHeight() / 2;
      this.iconH = iHeight / 5 + InGame_Civ.iMaxH_DiplomacyIcon;
      this.init(sText, CFG.FONT_REGULAR_SMALL, this.iTextPositionX, iPosX, iPosY, iWidth, iHeight, clickable, true, false, false);
   }

   public Color getColorHover1() {
      return new Color(Colors.COLOR_GRADIENT_OVER_BLUE3.r, Colors.COLOR_GRADIENT_OVER_BLUE3.g, Colors.COLOR_GRADIENT_OVER_BLUE3.b, 0.15F);
   }

   public Color getColorHover2() {
      return new Color(Colors.COLOR_GRADIENT_OVER_BLUE3.r, Colors.COLOR_GRADIENT_OVER_BLUE3.g, Colors.COLOR_GRADIENT_OVER_BLUE3.b, 0.5F);
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.5F));
      Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_BG_BLUE.r, Colors.COLOR_GRADIENT_BG_BLUE.g, Colors.COLOR_GRADIENT_BG_BLUE.b, 0.3F));
      Renderer.drawBox(
         oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + this.iconH + iTranslateY, this.getWidth(), this.getHeight() - this.iconH, 1.0F
      );
      if (!this.getIsHovered() && !isActive) {
         oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.35F));
         Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.iconH + iTranslateY, this.getWidth(), this.getHeight() - this.iconH);
         oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.3F));
         Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.iconH + iTranslateY, this.getWidth(), this.getHeight() - this.iconH);
      } else {
         oSB.setColor(this.getColorHover1());
         Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
      }

      if (this.getIsHovered() || isActive) {
         oSB.setColor(this.getColorHover2());
         Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.iconH + iTranslateY, this.getWidth(), this.getHeight() - this.iconH);
      }

      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.3F));
      Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.iconH + iTranslateY, this.getWidth(), CFG.PADDING * 2, false, true);
      Images.gradientXY
         .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - CFG.PADDING * 2 + iTranslateY, this.getWidth(), CFG.PADDING * 2);
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.25F));
      Renderer.drawBox(oSB, Images.statsRectBGBorder, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.25F));
      Renderer.drawBox(
         oSB, Images.statsRectBGBorder, this.getPosX() + 1 + iTranslateX, this.getPosY() + 1 + iTranslateY, this.getWidth() - 2, this.getHeight() - 2, 1.0F
      );
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.9F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 1 + iTranslateY, this.getWidth(), 1);
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), 1);
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.iconH - 1 + iTranslateY, this.getWidth(), 1);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.55F));
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 2 + iTranslateY, this.getWidth(), 1);
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + 1 + iTranslateY, this.getWidth(), 1);
      Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.iconH + iTranslateY, this.getWidth(), 1);
      if (this.getIsHovered() || isActive) {
         this.drawButtonSparks(oSB, iTranslateX, iTranslateY, isActive);
         oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 1.0F));
         Renderer.drawBox(oSB, Images.statsRectBGBorder, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
      }

      oSB.setColor(Color.WHITE);
   }

   protected void drawButtonSparks(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      oSB.setColor(MainMenu.sparksColors);
      MenuManager.sparksAnimationHover.draw2(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      if (this.getClickable()) {
         ImageManager.getImage(this.imageID)
            .draw(
               oSB,
               this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(this.imageID).getWidth() / 2 + iTranslateX,
               this.getPosY() + this.iconY + iTranslateY
            );
      } else {
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.33F));
         ImageManager.getImage(this.imageID)
            .draw(
               oSB,
               this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(this.imageID).getWidth() / 2 + iTranslateX,
               this.getPosY() + this.iconY + iTranslateY
            );
         oSB.setColor(Color.WHITE);
      }

      for (int i = 0; i < this.iLineSize; i++) {
         Renderer.drawText(
            oSB,
            this.fontID,
            this.sLines.get(i),
            this.getPosX() + this.getWidth() / 2 - this.iLinesWidth.get(i) / 2 + iTranslateX,
            this.getPosY() + this.iY_Text + (this.iTextHeight + CFG.PADDING * 2) * i + iTranslateY,
            this.getColor(isActive)
         );
      }
   }

   @Override
   public void setText(String sText) {
      this.sLines.clear();
      this.iLineSize = 0;
      this.iLinesWidth.clear();
      String[] words = sText.split(" ");
      int textPosX = 0;
      int maxW = this.getWidth() - CFG.PADDING * 2;
      String currentLine = "";
      int i = 0;

      for (int iSize = words.length; i < iSize; i++) {
         Renderer.glyphLayout.setText(Renderer.fontMain.get(this.fontID), words[i] + " ");
         this.iTextWidth = (int)Renderer.glyphLayout.width;
         textPosX += this.iTextWidth;
         if (textPosX < maxW) {
            currentLine = currentLine + words[i] + " ";
         } else {
            if (currentLine.length() <= 1) {
               currentLine = words[i] + " ";
               this.sLines.add(currentLine);
               currentLine = "";
            } else {
               this.sLines.add(currentLine);
               currentLine = words[i] + " ";
            }

            textPosX = this.iTextWidth;
         }
      }

      if (currentLine.length() > 0) {
         this.sLines.add(currentLine);
      }

      if (this.sLines.size() > 0 && this.sLines.get(0).length() > 0) {
         Renderer.glyphLayout.setText(Renderer.fontMain.get(this.fontID), this.sLines.get(0));
         this.iTextHeight = (int)Renderer.glyphLayout.height;
      }

      this.iLineSize = this.sLines.size();

      for (int ix = 0; ix < this.iLineSize; ix++) {
         Renderer.glyphLayout.setText(Renderer.fontMain.get(this.fontID), this.sLines.get(ix));
         this.iLinesWidth.add((int)Renderer.glyphLayout.width);
      }

      this.iY_Text = this.iconH + (this.getHeight() - this.iconH) / 2 - (this.iTextHeight * this.iLineSize + CFG.PADDING * 2 * (this.iLineSize - 1)) / 2;
   }

   @Override
   public int getSFX() {
      return SoundsManager.DIPLOMACY_CLICK;
   }
}
