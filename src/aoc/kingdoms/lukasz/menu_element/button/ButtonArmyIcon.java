package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ButtonArmyIcon extends Button {
   public int imageID;
   public static int maxWidth = 1;
   public boolean checkbox = false;
   public String armyKey;

   public ButtonArmyIcon(int imageID, int iPosX, int iPosY) {
      this.init("", CFG.FONT_REGULAR_SMALL, this.iTextPositionX, iPosX, iPosY, 20, getButtonHeight(), true, true, false, false);
      this.imageID = imageID;
      this.setWidth(getButtonWidth());
   }

   public ButtonArmyIcon(int imageID, int iPosX, int iPosY, boolean checkbox) {
      this.init("", CFG.FONT_REGULAR_SMALL, this.iTextPositionX, iPosX, iPosY, 20, getButtonHeight(), true, true, false, false);
      this.imageID = imageID;
      this.checkbox = checkbox;
      this.setWidth(getButtonWidth());
   }

   public ButtonArmyIcon(int imageID, int iPosX, int iPosY, boolean checkbox, String armyKey) {
      this.init("", CFG.FONT_REGULAR_SMALL, this.iTextPositionX, iPosX, iPosY, 20, getButtonHeight(), true, true, false, false);
      this.imageID = imageID;
      this.checkbox = checkbox;
      this.armyKey = armyKey;
      this.setWidth(getButtonWidth());
   }

   public ButtonArmyIcon(int imageID, int iPosX, int iPosY, boolean checkbox, String armyKey, int buttonH) {
      this.init("", CFG.FONT_REGULAR_SMALL, this.iTextPositionX, iPosX, iPosY, 20, buttonH, true, true, false, false);
      this.imageID = imageID;
      this.checkbox = checkbox;
      this.armyKey = armyKey;
      this.setWidth(getButtonWidth());
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      oSB.setColor(
         new Color(
            Colors.COLOR_STATS_RECT_BG.r,
            Colors.COLOR_STATS_RECT_BG.g,
            Colors.COLOR_STATS_RECT_BG.b,
            ButtonArmyID.getBoxAlpha(this.getClickable(), this.getIsHovered(), isActive)
         )
      );
      Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
      oSB.setColor(Color.WHITE);
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      ImageManager.getImage(this.getImageID())
         .draw(
            oSB,
            this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(this.getImageID()).getWidth() / 2 + iTranslateX,
            this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(this.getImageID()).getHeight() / 2 + iTranslateY
         );
   }

   public int getImageID() {
      return this.imageID;
   }

   public static int getButtonHeight() {
      return CFG.BUTTON_HEIGHT / 2 + CFG.PADDING;
   }

   public static int getButtonWidth() {
      return maxWidth + CFG.PADDING * 5;
   }

   @Override
   public boolean getCheckboxState() {
      return this.checkbox;
   }

   @Override
   public void setCheckboxState(boolean checkboxState) {
      this.checkbox = checkboxState;
   }
}
