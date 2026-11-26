package aoc.kingdoms.lukasz.menu_element.textStatic;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.MenuElement_Type;
import aoc.kingdoms.lukasz.menusInGame.Civ.InGame_Civ;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Text_Static_Opinion extends Text_Static {
   public float lastValue = -945878.5F;
   public boolean textMode = false;

   public Text_Static_Opinion(String sText, int fontID, int iTextPositionX, int iPosX, int iPosY, int iWidth, int iHeight, int nCurrent, boolean textMode) {
      this.typeOfElement = MenuElement_Type.TEXT;
      this.textMode = textMode;
      this.fontID = fontID;
      this.iCurrent = nCurrent;
      this.lastValue = Game.getCiv(InGame_Civ.iActiveCivID).diplomacy.getRelation(Game.player.iCivID);
      this.iTextPositionX = iTextPositionX;
      this.setPosX(iPosX);
      this.setPosY(iPosY);
      this.setWidth(iWidth);
      this.setHeight(iHeight);
      this.setText(sText);
      this.iCurrent = nCurrent;
      this.updateTextPosition();
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.3F));
      Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), false, true);
      oSB.setColor(Color.WHITE);
      Renderer.drawText(
         oSB,
         this.fontID,
         this.getTextToDraw(),
         this.getPosX() + this.textPosition.getTextPosition() + iTranslateX,
         this.getPosY() + (this.getHeight() - CFG.TEXT_HEIGHT) / 2 + iTranslateY,
         this.getColor(isActive)
      );
   }

   @Override
   protected Color getColor(boolean isActive) {
      if (this.iCurrent >= GameValues.diplomacy.DIPLOMACY_RELATION_NEUTRAL) {
         if (isActive) {
            return Colors.COLOR_TEXT_MODIFIER_POSITIVE_ACTIVE;
         } else {
            return isActive ? Colors.COLOR_TEXT_MODIFIER_POSITIVE_HOVER : Colors.COLOR_TEXT_MODIFIER_POSITIVE;
         }
      } else if (this.iCurrent < GameValues.diplomacy.DIPLOMACY_RELATION_DETACHED) {
         if (isActive) {
            return Colors.COLOR_TEXT_MODIFIER_NEGATIVE_ACTIVE;
         } else {
            return isActive ? Colors.COLOR_TEXT_MODIFIER_NEGATIVE_HOVER : Colors.COLOR_TEXT_MODIFIER_NEGATIVE;
         }
      } else {
         return super.getColor(isActive);
      }
   }

   @Override
   public String getTextToDraw() {
      try {
         if (this.lastValue != Game.getCiv(InGame_Civ.iActiveCivID).diplomacy.getRelation(Game.player.iCivID)) {
            this.lastValue = Game.getCiv(InGame_Civ.iActiveCivID).diplomacy.getRelation(Game.player.iCivID);
            this.iCurrent = (int)this.lastValue;
            if (this.textMode) {
               this.setText(DiplomacyManager.getOpinion_String(this.iCurrent));
            } else {
               this.setText((this.iCurrent > 0 ? "+" : "") + CFG.getPrecision2(this.lastValue, 1));
            }
         }
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
      }

      return this.sText;
   }
}
