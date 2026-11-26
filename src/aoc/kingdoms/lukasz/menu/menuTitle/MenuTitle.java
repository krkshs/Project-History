package aoc.kingdoms.lukasz.menu.menuTitle;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.GlyphLayout_Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuTitle {
   private String sText;
   public float fontScale = 1.0F;
   public int iTextWidth = -1;
   private int iTextHeight = -1;
   private int iHeight;
   private boolean moveable = false;
   private boolean resizable = false;
   public int fontID = 0;
   protected static final int ANIMATION_TIME = 325;
   public static Color colorDefault = new Color(0.92941177F, 0.99607843F, 1.0F, 1.0F);
   public static Color colorHovered = new Color(0.78431374F, 0.78431374F, 0.7647059F, 1.0F);
   public static Color colorActive = new Color(0.54901963F, 0.5882353F, 0.60784316F, 1.0F);

   public MenuTitle(String sText, int iHeight, boolean moveable, boolean resizable) {
      this.initTitle(sText, 1.0F, iHeight, moveable, resizable);
   }

   public MenuTitle(String sText, int iHeight, int fontID, boolean moveable, boolean resizable) {
      this.fontID = fontID;
      this.initTitle(sText, 1.0F, iHeight, moveable, resizable);
   }

   public MenuTitle(String sText, float fontScale, int iHeight, boolean moveable, boolean resizable) {
      this.initTitle(sText, fontScale, iHeight, moveable, resizable);
   }

   private final void initTitle(String sText, float fontScale, int iHeight, boolean moveable, boolean resizable) {
      this.fontScale = fontScale;
      this.iHeight = iHeight;
      this.moveable = moveable;
      this.resizable = resizable;
      this.setText(sText);
   }

   public void draw(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, Status titleStatus) {
      Renderer.drawBox_EDGE_TOP_LR(oSB, Images.boxTitle, nPosX, nPosY - this.getHeight(), nWidth, this.getHeight());
      this.drawGradient(oSB, nPosX, nPosY, nWidth, titleStatus);
      oSB.setColor(new Color(0.57254905F, 0.49411765F, 0.29411766F, 1.0F));
      Images.gradientFull.draw(oSB, nPosX, nPosY - 1, nWidth, 1);
      oSB.setColor(Color.WHITE);
      this.drawText(oSB, nPosX, nPosY, nWidth, titleStatus);
   }

   public void drawGradient(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, Status titleStatus) {
      oSB.setColor(new Color(Colors.COLOR_TITLE.r, Colors.COLOR_TITLE.g, Colors.COLOR_TITLE.b, 0.225F));
      Images.gradientXY.draw(oSB, nPosX, nPosY - this.getHeight(), nWidth, this.getHeight());
      if (this.getTime() + 325L >= CFG.currentTimeMillis) {
         float tAlpha = 0.25F - 0.25F * ((float)(CFG.currentTimeMillis - this.getTime()) / 325.0F);
         oSB.setColor(new Color(Colors.COLOR_TITLE.r, Colors.COLOR_TITLE.g, Colors.COLOR_TITLE.b, tAlpha));
         ImageManager.getImage(Images.gradientVertical).draw(oSB, nPosX, nPosY - CFG.PADDING * 2, nWidth, CFG.PADDING * 2, false, true);
         Images.gradientXY.draw(oSB, nPosX, nPosY - this.getHeight(), nWidth, this.getHeight());
      }

      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F));
      Images.gradientFull.draw(oSB, nPosX, nPosY - 2, nWidth, 1);
      oSB.setColor(Color.WHITE);
   }

   public void drawText(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, Status titleStatus) {
      Renderer.drawText(
         oSB,
         this.fontID,
         this.getText(),
         this.fontScale,
         nPosX + (int)(nWidth / 2.0F - this.getTextWidth() / 2.0F),
         1 + nPosY + (int)(-this.getHeight() + this.getHeight() / 2.0F - this.getTextHeight() / 2.0F),
         this.getColorText(titleStatus)
      );
   }

   public Color getColorText(Status titleStatus) {
      switch (titleStatus) {
         case ACTIVE:
            return colorActive;
         case HOVERED:
            return colorHovered;
         default:
            return colorDefault;
      }
   }

   public String getText() {
      return this.sText;
   }

   public void setText(String sText) {
      this.sText = sText;
      this.setTextWidth(-1);
      if (sText != null && this.getTextWidth() < 0) {
         GlyphLayout_Game glyphLayout = new GlyphLayout_Game();
         glyphLayout.setText(Renderer.fontMain.get(this.fontID), sText);
         this.setTextWidth((int)(glyphLayout.width * this.fontScale));
         this.setTextHeight((int)(glyphLayout.height * this.fontScale));
      }
   }

   public final int getHeight() {
      return this.iHeight;
   }

   public final boolean getMoveable() {
      return this.moveable;
   }

   public final int getTextWidth() {
      return this.iTextWidth;
   }

   public final int getTextHeight() {
      return this.iTextHeight;
   }

   public final void setTextWidth(int iTextWidth) {
      this.iTextWidth = iTextWidth;
   }

   public final void setTextHeight(int iTextHeight) {
      this.iTextHeight = iTextHeight;
   }

   public final boolean getResizable() {
      return this.resizable;
   }

   public long getTime() {
      return 0L;
   }

   public void action() {
   }

   public void onHovered() {
   }
}
