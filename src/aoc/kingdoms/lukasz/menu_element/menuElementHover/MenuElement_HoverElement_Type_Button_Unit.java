package aoc.kingdoms.lukasz.menu_element.menuElementHover;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuElement_HoverElement_Type_Button_Unit implements MenuElement_HoverElement_Type {
   private String sText;
   private int iTextWidth;
   private int iTextHeight;
   private String sDefense;
   private int iDefenseWidth;
   private int iDefenseHeight;
   private String sAttack;
   private int iAttackWidth;
   private int iAttackHeight;
   private Color oColor;
   private int fontID = 0;
   public int imageID;
   public int iconWidth;
   public int iconHeight;
   public int iconAttackWidth;
   public int iconAttackHeight;
   public int iconDefenseWidth;
   public int iconDefenseHeight;
   public int maxIconWidth;

   public MenuElement_HoverElement_Type_Button_Unit(String sText, int imageID, int fontID, String sAttack, String sDefense) {
      this.init(sText, imageID, fontID, Colors.BUTTON_TEXT_BRIGHT);
      this.sAttack = sAttack;
      this.sDefense = sDefense;
      Renderer.glyphLayout.setText(Renderer.fontMain.get(fontID), sAttack);
      this.iAttackWidth = (int)Renderer.glyphLayout.width;
      this.iAttackHeight = (int)Renderer.glyphLayout.height;
      Renderer.glyphLayout.setText(Renderer.fontMain.get(fontID), sDefense);
      this.iDefenseWidth = (int)Renderer.glyphLayout.width;
      this.iDefenseHeight = (int)Renderer.glyphLayout.height;
   }

   public final void init(String sText, int imageID, int nFontID, Color oColor) {
      this.sText = sText;
      this.imageID = imageID;
      this.oColor = oColor;
      this.fontID = nFontID;
      Renderer.glyphLayout.setText(Renderer.fontMain.get(this.fontID), sText);
      this.iTextWidth = (int)Renderer.glyphLayout.width;
      this.iTextHeight = (int)Renderer.glyphLayout.height;
      this.maxIconWidth = ImageManager.getImage(Images.gold).getWidth();
      float iconScale = this.getImageScale(imageID) * 1.1F;
      this.iconWidth = (int)(ImageManager.getImage(imageID).getWidth() * iconScale);
      this.iconHeight = (int)(ImageManager.getImage(imageID).getHeight() * iconScale);
      this.iconAttackWidth = (int)(ImageManager.getImage(Images.attack).getWidth() * iconScale);
      this.iconAttackHeight = (int)(ImageManager.getImage(Images.attack).getHeight() * iconScale);
      this.iconDefenseWidth = (int)(ImageManager.getImage(Images.defense).getWidth() * iconScale);
      this.iconDefenseHeight = (int)(ImageManager.getImage(Images.defense).getHeight() * iconScale);
   }

   @Override
   public void draw(SpriteBatch oSB, int nPosX, int nPosY, float nAlpha, int iMaxWidth) {
      nPosY += this.getPaddingY();
      oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.5F * nAlpha));
      Renderer.drawBox(oSB, Images.statsRectBG, nPosX, nPosY, iMaxWidth, this.getHeight2(), 1.0F * nAlpha);
      oSB.setColor(Color.WHITE);
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_BG_BLUE.r, Colors.COLOR_GRADIENT_BG_BLUE.g, Colors.COLOR_GRADIENT_BG_BLUE.b, 0.35F * nAlpha));
      Renderer.drawBox(oSB, Images.statsRectBG, nPosX, nPosY, CFG.PADDING * 2 + this.maxIconWidth, this.getHeight2(), 1.0F * nAlpha);
      oSB.setColor(Color.WHITE);
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.35F * nAlpha));
      Images.gradientFull.draw(oSB, nPosX, nPosY, CFG.PADDING * 2 + this.maxIconWidth, this.getHeight2());
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.3F * nAlpha));
      Images.gradientXY.draw(oSB, nPosX, nPosY, CFG.PADDING * 2 + this.maxIconWidth, this.getHeight2());
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.55F));
      Images.gradientFull.draw(oSB, nPosX, nPosY + this.getHeight2() - 1, CFG.PADDING * 2 + this.maxIconWidth, 1);
      Images.gradientFull.draw(oSB, nPosX, nPosY, CFG.PADDING * 2 + this.maxIconWidth, 1);
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.9F * nAlpha));
      Images.gradientFull.draw(oSB, nPosX, nPosY + this.getHeight2() - 2, CFG.PADDING * 2 + this.maxIconWidth, 1);
      Images.gradientFull.draw(oSB, nPosX, nPosY + 1, CFG.PADDING * 2 + this.maxIconWidth, 1);
      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, nAlpha));
      ImageManager.getImage(this.imageID)
         .draw(
            oSB,
            nPosX + (this.maxIconWidth + CFG.PADDING * 2) / 2 - this.iconWidth / 2,
            nPosY + (this.getHeight2() - this.iconHeight) / 2,
            this.iconWidth,
            this.iconHeight
         );
      Renderer.drawText(
         oSB,
         this.fontID,
         this.sText,
         nPosX + this.maxIconWidth + CFG.PADDING * 4,
         nPosY + (this.getHeight2() - this.iTextHeight) / 2,
         new Color(this.oColor.r, this.oColor.g, this.oColor.b, nAlpha)
      );
      ImageManager.getImage(Images.attack)
         .draw(
            oSB,
            nPosX + this.maxIconWidth + this.iTextWidth + CFG.PADDING * 5,
            nPosY + (this.getHeight2() - this.iconAttackHeight) / 2,
            this.iconAttackWidth,
            this.iconAttackHeight
         );
      Renderer.drawText(
         oSB,
         this.fontID,
         this.sAttack,
         nPosX + this.maxIconWidth + this.iTextWidth + CFG.PADDING * 6 + this.iconAttackWidth,
         nPosY + (this.getHeight2() - this.iAttackHeight) / 2,
         new Color(Colors.BUTTON_TEXT.r, Colors.BUTTON_TEXT.g, Colors.BUTTON_TEXT.b, nAlpha)
      );
      ImageManager.getImage(Images.defense)
         .draw(
            oSB,
            nPosX + this.maxIconWidth + this.iTextWidth + CFG.PADDING * 7 + this.iAttackWidth + this.iconAttackWidth,
            nPosY + (this.getHeight2() - this.iconDefenseHeight) / 2,
            this.iconDefenseWidth,
            this.iconDefenseHeight
         );
      Renderer.drawText(
         oSB,
         this.fontID,
         this.sAttack,
         nPosX + this.maxIconWidth + this.iTextWidth + CFG.PADDING * 8 + this.iconDefenseWidth + this.iAttackWidth + this.iconAttackWidth,
         nPosY + (this.getHeight2() - this.iDefenseHeight) / 2,
         new Color(Colors.BUTTON_TEXT.r, Colors.BUTTON_TEXT.g, Colors.BUTTON_TEXT.b, nAlpha)
      );
      oSB.setColor(Color.WHITE);
   }

   @Override
   public int getWidth() {
      return this.iTextWidth
         + this.maxIconWidth
         + CFG.PADDING * 6
         + CFG.PADDING * 4
         + this.iconDefenseWidth
         + this.iDefenseWidth
         + this.iAttackWidth
         + this.iconAttackWidth;
   }

   @Override
   public int getHeight() {
      return CFG.TEXT_HEIGHT + CFG.PADDING * 4;
   }

   public int getPaddingY() {
      return CFG.PADDING;
   }

   public int getHeight2() {
      return CFG.TEXT_HEIGHT + CFG.PADDING * 4;
   }

   private final float getImageScale(int iImageID) {
      return (float)CFG.TEXT_HEIGHT / ImageManager.getImage(iImageID).getHeight();
   }
}
