package aoc.kingdoms.lukasz.menusInGame.DrawOver;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.button.ButtonFlag;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class InGameDrawOver_RecruitArmy extends InGameDrawOver {
   public InGameDrawOver_RecruitArmy() {
      this.fontID = CFG.FONT_BOLD_SMALL;
      this.sText = Game.lang.get("ChooseAProvince");
      Renderer.glyphLayout.setText(Renderer.fontMain.get(this.fontID), this.sText);
      this.iTextWidth = (int)Renderer.glyphLayout.width;
      this.iTextHeight = (int)Renderer.glyphLayout.height;
      this.iPosY = ButtonFlag.getButtonHeight() + CFG.PADDING;
      drawAllTheTime = true;
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY) {
      if (drawAllTheTime) {
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.6F));
         int tX = CFG.GAME_WIDTH / 2 - this.iTextWidth / 2 - CFG.PADDING * 7 + iTranslateX;
         Renderer.drawBoxCorner2(oSB, tX, this.iPosY - CFG.PADDING * 2 + iTranslateY, this.iTextWidth + CFG.PADDING * 14, this.iTextHeight + CFG.PADDING * 4);
         oSB.setColor(Color.WHITE);
         Renderer.drawTextWithShadow(
            oSB,
            this.fontID,
            this.sText,
            CFG.GAME_WIDTH / 2 - this.iTextWidth / 2 + iTranslateX,
            this.iPosY + CFG.PADDING + iTranslateY,
            Colors.COLOR_TOP_STATS3
         );
      }
   }
}
