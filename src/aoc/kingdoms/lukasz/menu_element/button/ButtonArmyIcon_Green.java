package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ButtonArmyIcon_Green extends ButtonArmyIcon {
   public ButtonArmyIcon_Green(int imageID, int iPosX, int iPosY) {
      super(imageID, iPosX, iPosY);
   }

   public ButtonArmyIcon_Green(int imageID, int iPosX, int iPosY, boolean checkbox) {
      super(imageID, iPosX, iPosY, checkbox);
   }

   public ButtonArmyIcon_Green(int imageID, int iPosX, int iPosY, boolean checkbox, String armyKey) {
      super(imageID, iPosX, iPosY, checkbox, armyKey);
   }

   public ButtonArmyIcon_Green(int imageID, int iPosX, int iPosY, boolean checkbox, String armyKey, int buttonH) {
      super(imageID, iPosX, iPosY, checkbox, armyKey, buttonH);
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      if (this.checkbox) {
         oSB.setColor(new Color(Colors.COLOR_NOTIFICATION_BG_GREEN.r, Colors.COLOR_NOTIFICATION_BG_GREEN.g, Colors.COLOR_NOTIFICATION_BG_GREEN.b, 0.5F));
         Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
         oSB.setColor(Color.WHITE);
         oSB.setColor(
            new Color(
               Colors.COLOR_NOTIFICATION_BG_GREEN.r,
               Colors.COLOR_NOTIFICATION_BG_GREEN.g,
               Colors.COLOR_NOTIFICATION_BG_GREEN.b,
               !this.getIsHovered() && !isActive ? 0.35F : 0.65F
            )
         );
         Renderer.drawBox(oSB, Images.statsRectBG, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
         oSB.setColor(Color.WHITE);
         oSB.setColor(new Color(Colors.COLOR_NOTIFICATION_OVER_GREEN.r, Colors.COLOR_NOTIFICATION_OVER_GREEN.g, Colors.COLOR_NOTIFICATION_OVER_GREEN.b, 0.3F));
         Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
         oSB.setColor(new Color(Colors.COLOR_NOTIFICATION_OVER_GREEN.r, Colors.COLOR_NOTIFICATION_OVER_GREEN.g, Colors.COLOR_NOTIFICATION_OVER_GREEN.b, 0.35F));
         Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
         oSB.setColor(new Color(Colors.COLOR_NOTIFICATION_OVER_GREEN.r, Colors.COLOR_NOTIFICATION_OVER_GREEN.g, Colors.COLOR_NOTIFICATION_OVER_GREEN.b, 0.3F));
         Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.25F));
         Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), CFG.PADDING * 2, false, true);
         Images.gradientXY
            .draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - CFG.PADDING * 2 + iTranslateY, this.getWidth(), CFG.PADDING * 2);
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.25F));
         Renderer.drawBox(oSB, Images.statsRectBGBorder, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F));
         Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 1 + iTranslateY, this.getWidth(), 1);
         Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), 1);
         oSB.setColor(new Color(Colors.COLOR_NOTIFICATION_OVER_GREEN.r, Colors.COLOR_NOTIFICATION_OVER_GREEN.g, Colors.COLOR_NOTIFICATION_OVER_GREEN.b, 0.85F));
         Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 2 + iTranslateY, this.getWidth(), 1);
         Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + 1 + iTranslateY, this.getWidth(), 1);
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.55F));
         Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 1 + iTranslateY, this.getWidth(), 1);
         Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), 1);
         oSB.setColor(new Color(Colors.COLOR_NOTIFICATION_OVER_GREEN.r, Colors.COLOR_NOTIFICATION_OVER_GREEN.g, Colors.COLOR_NOTIFICATION_OVER_GREEN.b, 0.9F));
         Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - 2 + iTranslateY, this.getWidth(), 1);
         Images.gradientFull.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + 1 + iTranslateY, this.getWidth(), 1);
         oSB.setColor(Color.WHITE);
         if (this.getIsHovered() || isActive) {
            oSB.setColor(new Color(Colors.COLOR_GRADIENT.r, Colors.COLOR_GRADIENT.g, Colors.COLOR_GRADIENT.b, 0.8F));
            Images.gradientXY.draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight());
            oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 1.0F));
            Renderer.drawBox(oSB, Images.statsRectBGBorder, this.getPosX() + iTranslateX, this.getPosY() + iTranslateY, this.getWidth(), this.getHeight(), 1.0F);
            oSB.setColor(Color.WHITE);
         }

         oSB.setColor(Color.WHITE);
      } else {
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
   }
}
