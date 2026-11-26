package aoc.kingdoms.lukasz.menusScenarioEditor.Wasteland;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.map.civilization.CivilizationRegionsManager;
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
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Line;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_Text;
import aoc.kingdoms.lukasz.menu_element.menuElementHover.MenuElement_HoverElement_Type_TextTitle;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Static;
import aoc.kingdoms.lukasz.menusEditor.GameCivs;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class ScenarioWasteland extends Menu {
   public static boolean bSetWasteland_AvailableProvinces = true;
   public static List<ScenarioWasteland.Undo> lUndo = new ArrayList<>();

   public ScenarioWasteland() {
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
         new Text_Static(Game.lang.get("CustomizeWasteland"), CFG.PADDING, CFG.BUTTON_HEIGHT + CFG.PADDING * 3) {
            @Override
            public int getPosX() {
               return CFG.GAME_WIDTH - CFG.PADDING * 4 - this.getTextWidth();
            }

            @Override
            public int getPosY() {
               return ScenarioWasteland.this.getMenuElement(0).getPosY() - CFG.PADDING - this.getHeight();
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

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Help")));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Line());
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("CustomizeWasteland"), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(
                  new MenuElement_HoverElement_Type_Text(Game.lang.get("SetWhichProvincesOfTheWorldAreWasteland"), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT2)
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      menuElements.add(new ButtonMain(null, 1, -1, CFG.PADDING, CFG.PADDING, CFG.BUTTON_WIDTH * 2, true) {
         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("Back"));
         }

         @Override
         public void actionElement() {
            CFG.brushTool = false;
            Game.menuManager.setViewID(View.SCENARIO_WASTELAND_CONTINENTS);
         }

         @Override
         public void buildElementHover() {
            List<MenuElement_HoverElement> nElements = new ArrayList<>();
            List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
            nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("SelectRegions"), Colors.HOVER_GOLD));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover(nElements);
         }
      });
      menuElements.add(
         new ButtonMain("", 1, -1, CFG.PADDING * 2 + CFG.BUTTON_WIDTH * 2, CFG.PADDING, CFG.BUTTON_WIDTH, true) {
            @Override
            protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
               oSB.setColor(Colors.getColorTopStats(isActive, this.getIsHovered()));
               ImageManager.getImage(Images.information)
                  .draw(
                     oSB,
                     this.getPosX() + this.getWidth() / 2 - ImageManager.getImage(Images.information).getWidth() / 2 + iTranslateX,
                     this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(Images.information).getHeight() / 2 + iTranslateY
                  );
               oSB.setColor(Color.WHITE);
            }

            @Override
            public void buildElementHover() {
               List<MenuElement_HoverElement> nElements = new ArrayList<>();
               List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("Help")));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Line());
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(new MenuElement_HoverElement_Type_Text(Game.lang.get("CustomizeWasteland"), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT));
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               nData.add(
                  new MenuElement_HoverElement_Type_Text(Game.lang.get("SetWhichProvincesOfTheWorldAreWasteland"), CFG.FONT_REGULAR_SMALL, Colors.HOVER_RIGHT2)
               );
               nElements.add(new MenuElement_HoverElement(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover(nElements);
            }
         }
      );
      menuElements.add(new ButtonMain(null, 1, -1, CFG.GAME_WIDTH - CFG.BUTTON_WIDTH * 2 - CFG.PADDING, CFG.PADDING, CFG.BUTTON_WIDTH * 2, true) {
         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("Next"));
         }

         @Override
         public void actionElement() {
            GameCivs.sSearch = "";
            ScenarioWasteland.lUndo.clear();
            Game.iActiveProvince = -1;
            CFG.brushTool = false;
            Game.menuManager.setViewID(View.SCENARIO_CIVILIZATIONS);
         }

         @Override
         public void buildElementHover() {
            List<MenuElement_HoverElement> nElements = new ArrayList<>();
            List<MenuElement_HoverElement_Type> nData = new ArrayList<>();
            nData.add(new MenuElement_HoverElement_Type_TextTitle(Game.lang.get("ManageCivilizations"), Colors.HOVER_GOLD));
            nElements.add(new MenuElement_HoverElement(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover(nElements);
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
            CFG.BUTTON_WIDTH * 3 + CFG.PADDING * 3 + Images.boxTitleBORDERWIDTH,
            CFG.BUTTON_HEIGHT + CFG.PADDING * 2 + Images.boxTitleBORDERWIDTH,
            true,
            true
         );
      ImageManager.getImage(Images.boxBIG)
         .draw2(
            oSB,
            CFG.GAME_WIDTH - CFG.BUTTON_WIDTH * 2 - CFG.PADDING * 2 - Images.boxTitleBORDERWIDTH + iTranslateX,
            iTranslateY,
            CFG.BUTTON_WIDTH * 2 + CFG.PADDING * 2 + Images.boxTitleBORDERWIDTH,
            CFG.BUTTON_HEIGHT + CFG.PADDING * 2 + Images.boxTitleBORDERWIDTH,
            false,
            true
         );
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   public static void actionUpdateData(boolean addUndo) {
      if (Game.iActiveProvince >= 0 && !Game.getProvince(Game.iActiveProvince).getSeaProvince()) {
         if (Game.getProvince(Game.iActiveProvince).isCapital && bSetWasteland_AvailableProvinces) {
            return;
         }

         if (addUndo) {
            addUndo(Game.iActiveProvince);
         }

         if (bSetWasteland_AvailableProvinces && Game.getProvince(Game.iActiveProvince).getCivID() > 0) {
            int tCivID = Game.getProvince(Game.iActiveProvince).getCivID();
            Game.getCiv(Game.getProvince(Game.iActiveProvince).getCivID()).removeProvince(Game.iActiveProvince);
            Game.getProvince(Game.iActiveProvince).setCivID_Just(0);
            CivilizationRegionsManager.buildCivilizationsRegion(tCivID);
         }

         Game.getProvince(Game.iActiveProvince).setWasteland(bSetWasteland_AvailableProvinces ? 1 : -1);
         Game.updateProvincesInView = true;
         CivilizationRegionsManager.updateRegionsInView = true;
      }
   }

   private static final void addUndo(int nProvinceID) {
      if (nProvinceID >= 0) {
         if (Game.getProvince(nProvinceID).getWasteland() >= 0 != bSetWasteland_AvailableProvinces) {
            if (lUndo.size() > 50) {
               lUndo.remove(0);
            }

            lUndo.add(new ScenarioWasteland.Undo(nProvinceID, Game.getProvince(nProvinceID).getWasteland()));
         }
      }
   }

   protected static void popUndo() {
      if (lUndo.size() > 0) {
         Game.iActiveProvince = lUndo.get(lUndo.size() - 1).iProvinceID;
         bSetWasteland_AvailableProvinces = lUndo.get(lUndo.size() - 1).iState >= 0;
         actionUpdateData(false);
         if (!Game.getProvince(Game.iActiveProvince).getDrawProvince()) {
            Game.mapCoords.centerToProvinceID(Game.iActiveProvince);
         }

         lUndo.remove(lUndo.size() - 1);
      }
   }

   public static class Undo {
      public int iProvinceID;
      public int iState;

      public Undo(int iProvinceID, int iState) {
         this.iProvinceID = iProvinceID;
         this.iState = iState;
      }
   }
}
