package aoc.kingdoms.lukasz.menusInGame;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.map.province.ProvinceDraw;
import aoc.kingdoms.lukasz.menu.ClickAnimation;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.MenuManager;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.MessageButton;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import java.util.ArrayList;
import java.util.List;

public class InGame_Messages extends Menu {
   public InGame_Messages() {
      List<MenuElement> menuElements = new ArrayList<>();
      int buttonX = 0;
      int buttonY = CFG.PADDING / 2;

      try {
         for (int i = Game.player.iMessagesSize - 1; i >= 0; i--) {
            menuElements.add(
               new MessageButton(
                  Game.player.messages.get(i).key,
                  Game.player.messages.get(i).fromCivID,
                  Game.player.messages.get(i).getImageID(),
                  Game.player.messages.get(i).expiresTurnID,
                  Game.player.messages.get(i).time,
                  buttonX,
                  buttonY,
                  true
               ) {
                  @Override
                  public void actionElement() {
                     super.actionElement();
                     MenuManager.addClickAnimation(
                        new ClickAnimation(
                           this.getPosX() + this.getWidth() / 2 + InGame_Messages.this.getMenuPosX(),
                           this.getPosY() + this.getHeight() / 2 + InGame_Messages.this.getMenuPosY(),
                           this.getWidth(),
                           this.getHeight()
                        ) {
                           @Override
                           public Color getColor() {
                              return Colors.HOVER_GOLD;
                           }
                        }
                     );
                  }
               }
            );
            buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
         }
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
      }

      this.initMenu(
         null,
         InGame.rankPosXW,
         ImageManager.getImage(Images.topStats).getHeight(),
         CFG.GAME_WIDTH - InGame.rankPosXW,
         ImageManager.getImage(Images.messageBG).getHeight() + CFG.PADDING * 3,
         menuElements,
         true
      );
   }

   @Override
   public void actionElement(int nMenuElementID) {
      super.actionElement(nMenuElementID);
      ProvinceDraw.drawProvincesCiv_HoveredFlagID = 0;
   }
}
