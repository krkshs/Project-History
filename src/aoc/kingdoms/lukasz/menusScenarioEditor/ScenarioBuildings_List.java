package aoc.kingdoms.lukasz.menusScenarioEditor;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.BuildingsManager;
import aoc.kingdoms.lukasz.map.province.ProvinceConstructedBuilding;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.View;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitle;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonBuilding;
import aoc.kingdoms.lukasz.menusMapEditor.EditorSelectProvinces;
import aoc.kingdoms.lukasz.menusScenarioEditor.Diplomacy.ScenarioDiplomacy;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class ScenarioBuildings_List extends Menu {
   public int iProvinceID = 0;

   public ScenarioBuildings_List() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2;
      int titleHeight = CFG.BUTTON_HEIGHT;
      int menuX = CFG.PADDING * 4;
      int menuY = CFG.BUTTON_HEIGHT + CFG.PADDING * 4 + Images.boxTitleBORDERWIDTH;
      int buttonYPadding = CFG.PADDING * 2;
      int buttonY = buttonYPadding;
      int menuWidth = ImageManager.getImage(Images.insideTop500).getWidth();
      int textPosX = CFG.PADDING * 4;
      if (Game.iActiveProvince >= 0) {
         this.iProvinceID = Game.iActiveProvince;
      } else {
         this.iProvinceID = -1;
      }

      if (this.iProvinceID >= 0) {
         try {
            for (int i = 0; i < BuildingsManager.buildingsSize; i++) {
               for (int j = 0; j < BuildingsManager.buildingSize.get(i); j++) {
                  if (!BuildingsManager.buildings.get(i).UniqueCapitalBuilding
                     && (
                        Game.getProvince(this.iProvinceID).buildingBuilt(i, j)
                           || BuildingsManager.buildings.get(i).RequiredTechID[j] < 0
                           || Game.getCiv(Game.getProvince(this.iProvinceID).getCivID()).getTechResearched(BuildingsManager.buildings.get(i).RequiredTechID[j])
                     )) {
                     menuElements.add(
                        new ButtonBuilding(
                           Game.getProvince(this.iProvinceID).buildingBuilt(i, j), i, j, paddingLeft, buttonY, menuWidth - paddingLeft * 2, true, true
                        ) {
                           @Override
                           public void actionElement() {
                              if (EditorSelectProvinces.selectedProvinces.size() > 0) {
                                 for (int ix = 0; ix < EditorSelectProvinces.selectedProvinces.size(); ix++) {
                                    int provID = EditorSelectProvinces.selectedProvinces.get(ix);
                                    if (provID >= 0) {
                                       if (ScenarioDiplomacy.goBackTo == View.IN_GAME) {
                                          if (Game.getProvince(provID).buildingBuilt(this.getValue1(), this.getValue2())) {
                                             Game.getProvince(provID).destroyBuilding(this.getValue1(), this.getValue2());
                                          } else {
                                             Game.getProvince(provID)
                                                .addNewBuilding_LoadScenario(new ProvinceConstructedBuilding(this.getValue1(), this.getValue2()));
                                          }
                                       } else if (Game.getProvince(provID).buildingBuilt(this.getValue1(), this.getValue2())) {
                                          Game.getProvince(provID).destroyBuilding_ScenarioEditor(this.getValue1(), this.getValue2());
                                       } else {
                                          Game.getProvince(provID).addNewBuilding(new ProvinceConstructedBuilding(this.getValue1(), this.getValue2()));
                                       }

                                       this.built = Game.getProvince(provID).buildingBuilt(this.getValue1(), this.getValue2());
                                    }
                                 }
                              } else if (ScenarioBuildings_List.this.iProvinceID >= 0) {
                                 if (ScenarioDiplomacy.goBackTo == View.IN_GAME) {
                                    if (Game.getProvince(ScenarioBuildings_List.this.iProvinceID).buildingBuilt(this.getValue1(), this.getValue2())) {
                                       Game.getProvince(ScenarioBuildings_List.this.iProvinceID).destroyBuilding(this.getValue1(), this.getValue2());
                                    } else {
                                       Game.getProvince(ScenarioBuildings_List.this.iProvinceID)
                                          .addNewBuilding_LoadScenario(new ProvinceConstructedBuilding(this.getValue1(), this.getValue2()));
                                    }
                                 } else if (Game.getProvince(ScenarioBuildings_List.this.iProvinceID).buildingBuilt(this.getValue1(), this.getValue2())) {
                                    Game.getProvince(ScenarioBuildings_List.this.iProvinceID)
                                       .destroyBuilding_ScenarioEditor(this.getValue1(), this.getValue2());
                                 } else {
                                    Game.getProvince(ScenarioBuildings_List.this.iProvinceID)
                                       .addNewBuilding(new ProvinceConstructedBuilding(this.getValue1(), this.getValue2()));
                                 }

                                 this.built = Game.getProvince(ScenarioBuildings_List.this.iProvinceID).buildingBuilt(this.getValue1(), this.getValue2());
                              }
                           }
                        }
                     );
                     buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
                  }
               }
            }
         } catch (Exception var12) {
            CFG.exceptionStack(var12);
         }
      }

      this.initMenu(
         new MenuTitle("", 1.0F, titleHeight, true, true),
         menuX,
         titleHeight + menuY,
         menuWidth,
         Math.min(buttonY, CFG.GAME_HEIGHT - titleHeight - menuY * 2),
         menuElements,
         this.iProvinceID >= 0
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
      this.getTitle().setText(Game.iActiveProvince >= 0 ? Game.getProvince(Game.iActiveProvince).getProvinceName() : Game.lang.get("None"));
   }
}
