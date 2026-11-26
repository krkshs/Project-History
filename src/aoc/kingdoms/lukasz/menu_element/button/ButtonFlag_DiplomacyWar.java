package aoc.kingdoms.lukasz.menu_element.button;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu_element.MessageWar;
import aoc.kingdoms.lukasz.menusInGame.InGame_War;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ButtonFlag_DiplomacyWar extends ButtonFlag_Diplomacy {
   public String warKey;
   public Color textColor;

   public ButtonFlag_DiplomacyWar(int iCivID, int iPosX, int iPosY, boolean isClickable, String warScore, Color textColor, String warKey) {
      super(iCivID, iPosX, iPosY, isClickable);
      this.textColor = textColor;
      this.warKey = warKey;
      this.setHeight(this.getHeight() + CFG.TEXT_HEIGHT + CFG.PADDING * 2);
      this.fontID = CFG.FONT_REGULAR_SMALL;
      this.setText(warScore);
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      oSB.setColor(new Color(Colors.COLOR_STATS_RECT_BG.r, Colors.COLOR_STATS_RECT_BG.g, Colors.COLOR_STATS_RECT_BG.b, 0.75F));
      Images.gradientFull
         .draw(
            oSB,
            this.getPosX() + CFG.PADDING + iTranslateX,
            this.getPosY() + ImageManager.getImage(Images.flagDiplomacyOver).getHeight() + CFG.PADDING / 2 + iTranslateY,
            this.getWidth() - CFG.PADDING * 2,
            this.iTextHeight + CFG.PADDING
         );
      oSB.setColor(Color.WHITE);
      Renderer.drawTextWithShadow(
         oSB,
         this.fontID,
         this.getTextToDraw(),
         this.getPosX() + this.getWidth() / 2 - this.iTextWidth / 2 + iTranslateX,
         this.getPosY() + ImageManager.getImage(Images.flagDiplomacyOver).getHeight() + CFG.PADDING + iTranslateY,
         this.getColor(isActive)
      );
   }

   @Override
   protected Color getColor(boolean isActive) {
      return this.getIsHovered() ? Colors.getColorTopStats3(isActive, this.getIsHovered()) : this.textColor;
   }

   @Override
   public void actionElement() {
      if (Game.menuManager.getVisibleInGame_War() && InGame_War.key.equals(this.warKey)) {
         Game.menuManager.setVisibleInGame_War(false);
      } else {
         Game.gameActiveProvince.resetLastActiveProvince();
         Game.setActiveProvinceID(-1);
         Game.clearActiveArmy();
         Game.menuManager.showInGame_Battle_HideMenus();
         InGame_War.key = this.warKey;
         Game.menuManager.rebuildInGame_War();
         if (Game.mapModes.iActiveMapModeID != Game.mapModes.MODE_WAR_VIEW) {
            Game.mapModes.setActiveViewID(Game.mapModes.MODE_WAR_VIEW);
         } else {
            Game.mapModes.updateWarView(InGame_War.key);
         }
      }
   }

   @Override
   public void buildElementHover() {
      this.menuElementHover = MessageWar.getHoverWar(this.warKey, this.iCivID);
   }
}
