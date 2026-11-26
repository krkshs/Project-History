package aoc.kingdoms.lukasz.jakowski;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class GlyphLayout_Game {
   public float width = 1.0F;
   public float height = 1.0F;

   public boolean setText(BitmapFont font, CharSequence str) {
      try {
         if (str != null && str.length() > 0) {
            GlyphLayout glyphLayout = new GlyphLayout();
            glyphLayout.setText(font, str);
            this.width = glyphLayout.width;
            this.height = glyphLayout.height;
            return true;
         } else {
            this.width = 1.0F;
            this.height = CFG.TEXT_HEIGHT;
            return false;
         }
      } catch (Exception var4) {
         return false;
      }
   }

   public float getWidth() {
      return this.width;
   }

   public float getHeight() {
      return this.height;
   }
}
