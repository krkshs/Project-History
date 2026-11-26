package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.LawsManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class Button_Law3 extends Button {
   public int imageID;
   public List<String> sLines = new ArrayList<>();
   public int iLineSize = 0;
   public int lawID;
   public int lawID2;

   public Button_Law3(String sText2, String sDesc, int iPosX, int iPosY, int nWidth, int imageID, int lawID, int lawID2) {
      this.init(sText2, CFG.FONT_REGULAR, 0, iPosX, iPosY, nWidth, getButtonHeight(), true, true, false, false);
      this.lawID = lawID;
      this.lawID2 = lawID2;
      this.imageID = imageID;
      String[] words = sDesc.split(" ");
      int textPosX = 0;
      int maxW = this.getInnerWidth() - CFG.PADDING * 4;
      String currentLine = "";
      int tWidth = 0;
      int tHeight = 0;
      int i = 0;

      for (int iSize = words.length; i < iSize; i++) {
         Renderer.glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_REGULAR_SMALL), words[i] + " ");
         tWidth = (int)Renderer.glyphLayout.width;
         textPosX += tWidth;
         if (textPosX < maxW) {
            currentLine = currentLine + words[i] + " ";
         } else {
            this.sLines.add(currentLine);
            currentLine = words[i] + " ";
            textPosX = tWidth;
         }
      }

      if (currentLine.length() > 0) {
         this.sLines.add(currentLine);
      }

      if (this.sLines.size() > 0 && this.sLines.get(0).length() > 0) {
         Renderer.glyphLayout.setText(Renderer.fontMain.get(CFG.FONT_REGULAR_SMALL), this.sLines.get(0));
         tHeight = (int)Renderer.glyphLayout.height;
      }

      this.iLineSize = this.sLines.size();
      this.setHeight(
         Math.max(getButtonHeight(), getButtonHeight() / 2 + CFG.PADDING * 3 + tHeight * this.sLines.size() + (this.sLines.size() - 1) * CFG.PADDING * 2)
      );
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
      LawsManager.lawsImages.get(this.imageID).getTexture().bind(1);
      Gdx.gl.glActiveTexture(33984);
      ImageManager.getImage(Images.lawMask).draw(oSB, this.getPosX() + getPaddingIMG() + iTranslateX, this.getPosY() + getPaddingIMG() + iTranslateY);
      oSB.flush();
      oSB.setShader(Renderer.shaderDefault);
      ImageManager.getImage(Images.lawOver).draw(oSB, this.getPosX() + getPaddingIMG() + iTranslateX, this.getPosY() + getPaddingIMG() + iTranslateY);
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

      for (int i = 0; i < this.iLineSize; i++) {
         Renderer.drawTextWithShadow(
            oSB,
            CFG.FONT_REGULAR_SMALL,
            this.sLines.get(i),
            this.getPosX() + this.getInnerPosX() + CFG.PADDING * 2 + iTranslateX,
            this.getPosY() + getButtonHeight() / 2 + CFG.PADDING + (this.iTextHeight + CFG.PADDING * 2) * i + iTranslateY,
            Colors.getColorButtonHover(isActive, this.getIsHovered())
         );
      }
   }

   public static int getButtonHeight() {
      return LawsManager.lawsImages.get(0).getHeight() + getPaddingIMG() * 2;
   }

   public static int getPaddingIMG() {
      return CFG.PADDING + CFG.PADDING / 2;
   }

   public int getInnerPosX() {
      return getPaddingIMG() * 2 + LawsManager.lawsImages.get(this.imageID).getWidth();
   }

   public int getInnerWidth() {
      return this.getWidth() - this.getInnerPosX();
   }

   @Override
   protected Color getColor(boolean isActive) {
      return Colors.getColorButtonHover2(isActive, this.getIsHovered());
   }

   @Override
   public int getValue1() {
      return this.lawID;
   }

   @Override
   public int getValue2() {
      return this.lawID2;
   }

   @Override
   public void buildElementHover() {
      this.menuElementHover = LawsManager.getHover(this.lawID, this.lawID2);
   }
}
