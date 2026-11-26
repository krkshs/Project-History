package aoc.kingdoms.lukasz.menusInGame.Battle;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.map.battles.Battle;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class InGame_OngoingBattles {
   public static void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY) {
      if (GameValues.battle.DRAW_BATTLE_NOT_IN_VIEW) {
         int i = 0;

         for (int iSize = Game.getCiv(Game.player.iCivID).inBattles.size(); i < iSize; i++) {
            Battle battle = Game.battleManager.getBattle(Game.getCiv(Game.player.iCivID).inBattles.get(i));
            if (battle != null && !Game.getProvince(battle.provinceID).getDrawProvince()) {
               int posX = Math.max(
                  0, Math.min(CFG.GAME_WIDTH - CFG.BUTTON_HEIGHT4, Game.getProvince(battle.provinceID).iCenterShiftX + Game.mapCoords.getPosX())
               );
               int posY = Math.max(0, Math.min(CFG.GAME_HEIGHT - CFG.PADDING * 2, Game.getProvince(battle.provinceID).iCenterShiftY + Game.mapCoords.getPosY()));
               oSB.setColor(DiplomacyManager.COLOR_BATTLE);
               if (posY == 0) {
                  Images.gradientXY.draw(oSB, iTranslateX + posX, iTranslateY + posY, CFG.BUTTON_HEIGHT4, CFG.PADDING * 2, false, true);
               } else if (posY == CFG.GAME_HEIGHT - CFG.PADDING * 2) {
                  Images.gradientXY.draw(oSB, iTranslateX + posX, iTranslateY + posY, CFG.BUTTON_HEIGHT4, CFG.PADDING * 2, false, false);
               } else if (posX == 0) {
                  ImageManager.getImage(Images.gradientXYVertical)
                     .draw(oSB, iTranslateX + posX, iTranslateY + posY, CFG.PADDING * 2, CFG.BUTTON_HEIGHT4, false, false);
               } else {
                  ImageManager.getImage(Images.gradientXYVertical)
                     .draw(oSB, iTranslateX + CFG.GAME_WIDTH - CFG.PADDING * 2, iTranslateY + posY, CFG.PADDING * 2, CFG.BUTTON_HEIGHT4, true, false);
               }

               oSB.setColor(Color.WHITE);
            }
         }
      }
   }
}
