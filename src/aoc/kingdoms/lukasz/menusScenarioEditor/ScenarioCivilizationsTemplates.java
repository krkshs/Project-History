package aoc.kingdoms.lukasz.menusScenarioEditor;

import age.of.civilizations2.jakowski.lukasz.Civilizations.CivTemplate;
import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.FileManager;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.civilization.CivilizationRegionsManager;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitle;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Minimap;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame2Sparks_Hovered;
import aoc.kingdoms.lukasz.menu_element.button.ButtonGame_Value;
import aoc.kingdoms.lukasz.menusMapEditor.EditorSelectProvinces;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class ScenarioCivilizationsTemplates extends Menu {
   public static int CIV_ID = 0;
   public CivTemplate civTemplate = null;

   public ScenarioCivilizationsTemplates() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2;
      int titleHeight = CFG.BUTTON_HEIGHT * 3 / 4;
      int menuX = CFG.PADDING * 4;
      int menuY = CFG.BUTTON_HEIGHT + CFG.PADDING * 6;
      int menuW = CFG.LEFT_MENU_WIDTH;
      int buttonYPadding = CFG.PADDING;
      int buttonY = buttonYPadding;
      int textPosX = CFG.PADDING * 4;

      try {
         if (Game.iActiveProvince >= 0
            && Game.getProvince(Game.iActiveProvince).getCivID() > 0
            && Game.getCiv(Game.getProvince(Game.iActiveProvince).getCivID()).getCapitalProvinceID() == Game.iActiveProvince) {
            CIV_ID = Game.getProvince(Game.iActiveProvince).getCivID();
            String realTag = Game.ideologiesManager.getRealTag(Game.getCiv(Game.getProvince(Game.iActiveProvince).getCivID()).getCivTag());
            FileHandle file = null;
            if (FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "civs_template/" + realTag).exists()) {
               try {
                  file = FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "civs_template/" + realTag);
                  this.civTemplate = (CivTemplate)CFG.deserialize(file.readBytes());
               } catch (Exception var13) {
                  CFG.exceptionStack(var13);
               }
            }

            if (this.civTemplate != null && !this.civTemplate.tag.isEmpty()) {
               menuElements.add(
                  new ButtonGame2Sparks_Hovered(Game.lang.get("Confirm"), 1, -1, CFG.PADDING * 2, buttonY, menuW - CFG.PADDING * 4, true) {
                     @Override
                     public void actionElement() {
                        try {
                           List<Integer> tempProvs = new ArrayList<>();

                           for (int b = 0; b < Game.getCiv(ScenarioCivilizationsTemplates.CIV_ID).getNumOfProvinces(); b++) {
                              if (Game.getCiv(ScenarioCivilizationsTemplates.CIV_ID).getProvinceID(b)
                                 != Game.getCiv(ScenarioCivilizationsTemplates.CIV_ID).getCapitalProvinceID()) {
                                 tempProvs.add(Game.getCiv(ScenarioCivilizationsTemplates.CIV_ID).getProvinceID(b));
                              }
                           }

                           for (int bx = tempProvs.size() - 1; bx >= 0; bx--) {
                              if (Game.getCiv(Game.getProvince(tempProvs.get(bx)).getCivID()).getCapitalProvinceID() != tempProvs.get(bx)) {
                                 Game.getProvince(tempProvs.get(bx)).setCivID_RemoveOldAddNewToCiv(0);
                                 Game.updateProvinceBorder(tempProvs.get(bx));
                              }
                           }

                           for (int i = EditorSelectProvinces.selectedProvinces.size() - 1; i >= 0; i--) {
                              if (Game.getCiv(Game.getProvince(EditorSelectProvinces.selectedProvinces.get(i)).getCivID()).getCapitalProvinceID()
                                 != EditorSelectProvinces.selectedProvinces.get(i)) {
                                 if (Game.getProvince(EditorSelectProvinces.selectedProvinces.get(i)).getCivID() > 0) {
                                    Game.gameThreadUpdate
                                       .addSimpleTask(
                                          new Game.SimpleTask(
                                             "buildCivilizationsRegion" + Game.getProvince(EditorSelectProvinces.selectedProvinces.get(i)).getCivID(),
                                             Game.getProvince(EditorSelectProvinces.selectedProvinces.get(i)).getCivID()
                                          ) {
                                             @Override
                                             public void update() {
                                                CivilizationRegionsManager.buildCivilizationsRegion(this.id);
                                                Game.updateProvincesInView = true;
                                                CivilizationRegionsManager.updateRegionsInView = true;
                                             }
                                          }
                                       );
                                 }

                                 Game.getProvince(EditorSelectProvinces.selectedProvinces.get(i))
                                    .setCivID_RemoveOldAddNewToCiv(ScenarioCivilizationsTemplates.CIV_ID);
                                 Game.updateProvinceBorder(EditorSelectProvinces.selectedProvinces.get(i));
                              }
                           }

                           if (ScenarioCivilizationsTemplates.CIV_ID > 0) {
                              Game.gameThreadUpdate
                                 .addSimpleTask(
                                    new Game.SimpleTask(
                                       "buildCivilizationsRegion" + ScenarioCivilizationsTemplates.CIV_ID, ScenarioCivilizationsTemplates.CIV_ID
                                    ) {
                                       @Override
                                       public void update() {
                                          CivilizationRegionsManager.buildCivilizationsRegion(this.id);
                                          Game.updateProvincesInView = true;
                                          CivilizationRegionsManager.updateRegionsInView = true;
                                       }
                                    }
                                 );
                           }

                           EditorSelectProvinces.selectedProvinces.clear();
                        } catch (Exception var3) {
                           CFG.exceptionStack(var3);
                        }
                     }
                  }
               );
               buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;

               for (int a = 0; a < this.civTemplate.tag.size(); a++) {
                  menuElements.add(
                     new ButtonGame_Value(
                        Game.lang.getCiv(this.civTemplate.tag.get(a))
                           + " "
                           + this.civTemplate.year.get(a)
                           + " | "
                           + CFG.getNumberWithSpaces("" + this.civTemplate.provinces.get(a).size())
                           + " "
                           + Game.lang.get("Provinces"),
                        1,
                        -1,
                        CFG.PADDING * 2,
                        buttonY,
                        menuW - CFG.PADDING * 4,
                        true,
                        a
                     ) {
                        @Override
                        public void actionElement() {
                           try {
                              if (ScenarioCivilizationsTemplates.this.civTemplate != null
                                 && ScenarioCivilizationsTemplates.this.civTemplate.provinces.get(this.id).size() > 1) {
                                 EditorSelectProvinces.selectedProvinces.clear();

                                 for (int b = ScenarioCivilizationsTemplates.this.civTemplate.provinces.get(this.id).size() - 1; b >= 0; b--) {
                                    if (Game.getCiv(Game.getProvince(ScenarioCivilizationsTemplates.this.civTemplate.provinces.get(this.id).get(b)).getCivID())
                                             .getCapitalProvinceID()
                                          != ScenarioCivilizationsTemplates.this.civTemplate.provinces.get(this.id).get(b)
                                       || Game.getProvince(ScenarioCivilizationsTemplates.this.civTemplate.provinces.get(this.id).get(b)).getCivID()
                                          == ScenarioCivilizationsTemplates.CIV_ID) {
                                       EditorSelectProvinces.selectedProvinces
                                          .add(ScenarioCivilizationsTemplates.this.civTemplate.provinces.get(this.id).get(b));
                                    }
                                 }
                              }
                           } catch (Exception var2) {
                              CFG.exceptionStack(var2);
                           }
                        }
                     }
                  );
                  buttonY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
               }
            }
         } else {
            menuElements.add(new ButtonGame_Value("--", 1, -1, 0, buttonY, menuW, true, 0) {
               @Override
               public void actionElement() {
               }
            });
         }
      } catch (Exception var14) {
         CFG.exceptionStack(var14);
      }

      this.initMenu(
         new MenuTitle("", 1.0F, titleHeight, true, false),
         menuX,
         titleHeight + menuY,
         menuW,
         Math.min(
            buttonY,
            CFG.GAME_HEIGHT
               - menuY
               - titleHeight
               - Game.mapBG.minimapOfCivilizations.getHeight()
               - Minimap.getPadding() * 2
               - CFG.TEXT_HEIGHT
               - CFG.PADDING * 7
         ),
         menuElements,
         this.civTemplate != null && !this.civTemplate.tag.isEmpty()
      );
   }

   public final void drawEditorMenuBG(SpriteBatch oSB, int iX, int iY, int iWidth, int iHeight, int iTranslateX, int iTranslateY) {
      Renderer.drawBoxCorner(oSB, iX + iTranslateX, iY - this.getTitle().getHeight() + iTranslateY, iWidth, iHeight + this.getTitle().getHeight() + CFG.PADDING);
      Renderer.drawBox_EDGE_TOP_LR(oSB, Images.mainBox, iX + iTranslateX, iY + iTranslateY, iWidth, iHeight + CFG.PADDING, true);
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      this.drawEditorMenuBG(oSB, this.getPosX(), this.getPosY(), this.getWidth(), this.getHeight(), iTranslateX, iTranslateY);
      super.beginClip(oSB, iTranslateX, iTranslateY, menuIsActive);
      super.drawMenu(oSB, iTranslateX, iTranslateY, menuIsActive);
      super.endClip(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public void updateLanguage() {
      super.updateLanguage();
      this.getTitle().setText(Game.lang.get("Templates"));
   }
}
