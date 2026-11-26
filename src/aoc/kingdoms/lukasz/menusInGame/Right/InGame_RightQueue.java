package aoc.kingdoms.lukasz.menusInGame.Right;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.civilization.Civilization;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.Button_QueueBuilding;
import aoc.kingdoms.lukasz.menu_element.button.Button_QueueUnit;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class InGame_RightQueue extends Menu {
   public static int extraY = 0;

   public InGame_RightQueue() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = 0;
      int paddingRight = CFG.PADDING;
      int titleHeight = ImageManager.getImage(Images.title2).getHeight();
      int menuWidth = Game.mapBG.iMinimapWidth + Images.boxTitleBORDERWIDTH;
      int menuX = CFG.GAME_WIDTH - menuWidth;
      int menuY = ImageManager.getImage(Images.topStats).getHeight() + CFG.PADDING;
      menuWidth -= CFG.PADDING;
      int buttonY = CFG.PADDING;
      int buttonX = paddingLeft;
      int buttonH = ImageManager.getImage(Images.unitsFrameMap).getHeight();
      if (InGame_Right.outlinerInView) {
         try {
            Civilization civ = Game.getCiv(Game.player.iCivID);

            for (int i = 0; i < civ.getArmyRecruitSize(); i++) {
               for (int j = 0; j < civ.lArmyRecruit.get(i).size(); j++) {
                  menuElements.add(new Button_QueueUnit(buttonX, buttonY, civ.lArmyRecruit.get(i).get(j)));
                  buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
               }
            }

            for (int i = 0; i < civ.getNumOfProvinces(); i++) {
               if (Game.getProvince(civ.getProvinceID(i)).iBuildingsConstructionSize > 0) {
                  for (int j = 0; j < Game.getProvince(civ.getProvinceID(i)).iBuildingsConstructionSize; j++) {
                     menuElements.add(
                        new Button_QueueBuilding(buttonX, buttonY, civ.getProvinceID(i), Game.getProvince(civ.getProvinceID(i)).getBuildingsConstruction(j))
                     );
                     buttonX += menuElements.get(menuElements.size() - 1).getWidth() + CFG.PADDING;
                  }
               }
            }
         } catch (Exception var14) {
            CFG.exceptionStack(var14);
         }
      }

      int menuHeight = buttonH + CFG.PADDING * 2;
      this.initMenu(null, menuX + CFG.PADDING, menuY, menuWidth - CFG.PADDING * 2, menuHeight, menuElements, menuElements.size() > 0, false);
      if (this.getVisible()) {
         extraY = menuHeight + CFG.PADDING;
      } else {
         extraY = 0;
      }

      this.drawScrollPositionAlways = false;
      this.drawScrollPositionAlways2 = false;
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      int posX = this.getPosX() + iTranslateX - CFG.PADDING;
      int nWidth = this.getWidth() + CFG.PADDING * 2;
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_BG_BLUE.r, Colors.COLOR_GRADIENT_BG_BLUE.g, Colors.COLOR_GRADIENT_BG_BLUE.b, 0.9F));
      Renderer.drawBox(oSB, Images.statsRectBG, posX, this.getPosY() + iTranslateY, nWidth, this.getHeight(), 1.0F);
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.3F));
      Images.gradientFull.draw(oSB, posX, this.getPosY() + iTranslateY, nWidth, this.getHeight());
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.35F));
      Images.gradientFull.draw(oSB, posX, this.getPosY() + iTranslateY, nWidth, this.getHeight());
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.3F));
      Images.gradientXY.draw(oSB, posX, this.getPosY() + iTranslateY, nWidth, this.getHeight());
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.25F));
      Images.gradientXY.draw(oSB, posX, this.getPosY() + iTranslateY, nWidth, CFG.PADDING * 2, false, true);
      Images.gradientXY.draw(oSB, posX, this.getPosY() + this.getHeight() - CFG.PADDING * 2 + iTranslateY, nWidth, CFG.PADDING * 2);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.25F));
      Renderer.drawBox(oSB, Images.statsRectBGBorder, posX, this.getPosY() + iTranslateY, nWidth, this.getHeight(), 1.0F);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F));
      Images.gradientFull.draw(oSB, posX, this.getPosY() + this.getHeight() - 1 + iTranslateY, nWidth, 1);
      Images.gradientFull.draw(oSB, posX, this.getPosY() + iTranslateY, nWidth, 1);
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.85F));
      Images.gradientFull.draw(oSB, posX, this.getPosY() + this.getHeight() - 2 + iTranslateY, nWidth, 1);
      Images.gradientFull.draw(oSB, posX, this.getPosY() + 1 + iTranslateY, nWidth, 1);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.55F));
      Images.gradientFull.draw(oSB, posX, this.getPosY() + this.getHeight() - 1 + iTranslateY, nWidth, 1);
      Images.gradientFull.draw(oSB, posX, this.getPosY() + iTranslateY, nWidth, 1);
      oSB.setColor(new Color(Colors.COLOR_GRADIENT_OVER_BLUE.r, Colors.COLOR_GRADIENT_OVER_BLUE.g, Colors.COLOR_GRADIENT_OVER_BLUE.b, 0.9F));
      Images.gradientFull.draw(oSB, posX, this.getPosY() + this.getHeight() - 2 + iTranslateY, nWidth, 1);
      Images.gradientFull.draw(oSB, posX, this.getPosY() + 1 + iTranslateY, nWidth, 1);
      oSB.setColor(Color.WHITE);
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public boolean getVisible() {
      return super.getVisible() && Game.mapBG.getHideMenuZoomOut() && !Game.menuManager.getVisibleInGame_CivBonuses();
   }
}
