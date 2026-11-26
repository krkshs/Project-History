package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.textures.ImageManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ButtonBattleRegimentDefeated extends ButtonBattleRegiment {
   public ButtonBattleRegimentDefeated(int nCivID, int imageID, int iPosX, int iPosY, int id, int offsetY) {
      super(nCivID, imageID, iPosX, iPosY, id, offsetY, 0, false, false);
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, !this.getIsHovered() && !isActive ? 0.15F : 0.35F));
      ImageManager.getImage(this.imageID).draw(oSB, this.getPosX() + 1 + iTranslateX, this.getPosY() + 1 + iTranslateY);
      oSB.setColor(Color.WHITE);
      if (this.getIsHovered()) {
         oSB.setColor(Colors.HOVER_GOLD);
         Renderer.drawBox2(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
      }

      oSB.setColor(Color.WHITE);
   }
}
