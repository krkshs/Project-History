package aoc.kingdoms.lukasz.menusScenarioEditor;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.menu.Colors;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.View;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Minimap;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonMain;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_Hover;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle_BG;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Static;
import aoc.kingdoms.lukasz.menusEditor.GameCivs;
import aoc.kingdoms.lukasz.menusScenarioEditor.Diplomacy.ScenarioDiplomacy;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class ScenarioArmies extends Menu {
   public ScenarioArmies() {
      List<MenuElement> menuElements = new ArrayList<>();
      menuElements.add(new Minimap(0, 0) {
         @Override
         public int getPosX() {
            return CFG.GAME_WIDTH - this.getWidth();
         }

         @Override
         public int getPosY() {
            return CFG.GAME_HEIGHT - this.getHeight();
         }
      });
      menuElements.add(
         new Text_Static(Game.lang.get("Armies"), CFG.PADDING, CFG.BUTTON_HEIGHT + CFG.PADDING * 3) {
            @Override
            public int getPosX() {
               return CFG.GAME_WIDTH - CFG.PADDING * 4 - this.getTextWidth();
            }

            @Override
            public int getPosY() {
               return ScenarioArmies.this.getMenuElement(0).getPosY() - CFG.PADDING - this.getHeight();
            }

            @Override
            public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
               Images.statsBG
                  .draw2(
                     oSB,
                     this.getPosX() - CFG.PADDING * 3 + iTranslateX,
                     this.getPosY() - CFG.PADDING * 2 + iTranslateY,
                     Images.statsBG.getWidth(),
                     this.getHeight() + CFG.PADDING * 4
                  );
               Images.statsBG2
                  .draw2(
                     oSB,
                     this.getPosX() - CFG.PADDING * 3 + Images.statsBG.getWidth() + iTranslateX,
                     this.getPosY() - CFG.PADDING * 2 + iTranslateY,
                     this.getWidth() + CFG.PADDING * 6 - Images.statsBG.getWidth(),
                     this.getHeight() + CFG.PADDING * 4,
                     true,
                     false
                  );
               super.draw(oSB, iTranslateX, iTranslateY, isActive, scrollableY);
            }
         }
      );
      menuElements.add(new ButtonMain(Game.lang.get("Save"), 1, -1, CFG.PADDING, CFG.PADDING, CFG.BUTTON_WIDTH * 2, true) {
         @Override
         public void actionElement() {
            GameCivs.sSearch = "";
            CFG.brushTool = false;
            Renderer.drawArmyInProvince = true;
            Game.menuManager.setViewID(ScenarioDiplomacy.goBackTo);
         }

         @Override
         public void buildElementHover() {
            List<MenuElement_HoverElement> nElements = new ArrayList<>();
            List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
            if (ScenarioDiplomacy.goBackTo == View.IN_GAME) {
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("Back"), Colors.HOVER_GOLD));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
            } else {
               nData.add(new MenuElement_HoverElement_Type_TextTitle_BG(Game.lang.get("ScenarioSettings"), Colors.HOVER_GOLD));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
            }

            this.menuElementHover = new MenuElement_Hover(nElements, true);
         }
      });
      this.initMenu(null, 0, 0, CFG.GAME_WIDTH, CFG.GAME_HEIGHT, menuElements, true);
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      ImageManager.getImage(Images.boxBIG)
         .draw2(
            oSB,
            iTranslateX,
            iTranslateY,
            CFG.BUTTON_WIDTH * 2 + CFG.PADDING * 2 + Images.boxTitleBORDERWIDTH,
            CFG.BUTTON_HEIGHT + CFG.PADDING * 2 + Images.boxTitleBORDERWIDTH,
            true,
            true
         );
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }
}
