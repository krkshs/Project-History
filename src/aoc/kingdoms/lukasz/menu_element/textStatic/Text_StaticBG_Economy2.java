package aoc.kingdoms.lukasz.menu_element.textStatic;

import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Text_StaticBG_Economy2 extends Text_StaticBG_Economy {
   public Text_StaticBG_Economy2(String sText, int fontID, int iTextPositionX, int iPosX, int iPosY, int iWidth, int iHeight, int id) {
      super(sText, fontID, iTextPositionX, iPosX, iPosY, iWidth, iHeight, id);
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
      if (Game.canInvestInEconomy(this.id)) {
         oSB.setColor(new Color(Colors.HOVER_NEGATIVE.r, Colors.HOVER_NEGATIVE.g, Colors.HOVER_NEGATIVE.b, 0.4F));
         Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
         oSB.setColor(Color.WHITE);
      }

      super.draw(oSB, iTranslateX, iTranslateY, isActive, scrollableY);
   }

   @Override
   protected Color getColor(boolean isActive) {
      return Game.canInvestInEconomy(this.id) ? Colors.COLOR_TEXT_MODIFIER_NEGATIVE : super.getColor(isActive);
   }
}
