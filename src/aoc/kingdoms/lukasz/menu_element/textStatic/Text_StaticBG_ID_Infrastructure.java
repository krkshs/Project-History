package aoc.kingdoms.lukasz.menu_element.textStatic;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.province.ProvinceDraw;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Text_StaticBG_ID_Infrastructure extends Text_StaticBG {
   public int id;
   public int lastValue = -997654;
   public int lastValue2 = -997654;

   public Text_StaticBG_ID_Infrastructure(String sText, int fontID, int iTextPositionX, int iPosX, int iPosY, int iWidth, int iHeight, int id) {
      super(sText, fontID, iTextPositionX, iPosX, iPosY, iWidth, iHeight);
      this.id = id;
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
      oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.5F));
      Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
      if (Game.getProvince(this.id).iProvinceDevelopInfrastructureSize > 0) {
         oSB.setColor(new Color(ProvinceDraw.progressBar.r, ProvinceDraw.progressBar.g, ProvinceDraw.progressBar.b, 0.5F));
         Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
         oSB.setColor(new Color(ProvinceDraw.progressBar));
         Renderer.drawBoxProgress(
            oSB,
            Images.statsRectBG,
            this.getPosX() + iTranslateX,
            this.getPosY() + iTranslateY,
            (int)(
               this.getWidth()
                  * (
                     1.0F
                        - (float)Game.getProvince(this.id).provinceDevelopInfrastructureDaysLeft.get(0).daysLeft
                           / Game.getProvince(this.id).provinceDevelopInfrastructureDaysLeft.get(0).investTime
                  )
            ),
            this.getHeight(),
            this.getWidth()
         );
         oSB.setColor(Color.WHITE);
         oSB.setColor(new Color(Colors.COLOR_GRADIENT_BG_BLUE.r, Colors.COLOR_GRADIENT_BG_BLUE.g, Colors.COLOR_GRADIENT_BG_BLUE.b, 0.2F));
         Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
         oSB.setColor(Color.WHITE);
      }

      oSB.setColor(Color.WHITE);
      Renderer.drawTextWithShadow(
         oSB,
         this.fontID,
         this.getTextToDraw(),
         this.getPosX() + this.textPosition.getTextPosition() + iTranslateX,
         this.getPosY() + (this.getHeight() - CFG.TEXT_HEIGHT) / 2 + iTranslateY,
         this.getColor(isActive)
      );
   }

   @Override
   public String getTextToDraw() {
      if (this.lastValue != Game.getProvince(this.id).getInfrastructure() || this.lastValue2 != Game.getProvince(this.id).iInfrastructureMax) {
         this.setText("" + Game.getProvince(this.id).getInfrastructure() + " / " + Game.getProvince(this.id).iInfrastructureMax);
         this.lastValue = Game.getProvince(this.id).getInfrastructure();
         this.lastValue2 = Game.getProvince(this.id).iInfrastructureMax;
      }

      return this.sText;
   }

   @Override
   public int getCurrent() {
      return this.id;
   }

   @Override
   protected Color getColor(boolean isActive) {
      if (Game.getProvince(this.id).getInfrastructure() + Game.getProvince(this.id).iProvinceDevelopInfrastructureSize
         == Game.getProvince(this.id).iInfrastructureMax) {
         if (isActive) {
            return Colors.COLOR_TEXT_MODIFIER_POSITIVE_ACTIVE;
         } else {
            return this.getIsHovered() ? Colors.COLOR_TEXT_MODIFIER_POSITIVE_HOVER : Colors.COLOR_TEXT_MODIFIER_POSITIVE;
         }
      } else {
         return super.getColor(isActive);
      }
   }
}
