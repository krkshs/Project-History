package aoc.kingdoms.lukasz.menusMapEditor;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.province.ProvinceDrawDetails;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.View;
import aoc.kingdoms.lukasz.menu.menuTitle.MenuTitle;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.button.ButtonMain;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class EditorMapEdit extends Menu {
   public EditorMapEdit() {
      List<MenuElement> menuElements = new ArrayList<>();
      int paddingLeft = CFG.PADDING * 2;
      int titleHeight = CFG.BUTTON_HEIGHT + CFG.BUTTON_HEIGHT / 2;
      int menuX = CFG.GAME_WIDTH / 10;
      int menuY = CFG.GAME_HEIGHT / 10;
      int buttonYPadding = CFG.PADDING * 2;
      int buttonY = buttonYPadding;
      int textPosX = CFG.PADDING * 4;
      menuElements.add(new ButtonMain(null, 1, -1, paddingLeft, buttonY, CFG.LEFT_MENU_WIDTH - paddingLeft * 2, true) {
         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("Back"));
         }

         @Override
         public void actionElement() {
            CFG.brushTool = false;
            Game.menuManager.setViewID(View.EDITOR_MAPS);
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(
         new ButtonMain("", 1, textPosX, paddingLeft, buttonY, CFG.LEFT_MENU_WIDTH - paddingLeft * 2, true) {
            @Override
            public void updateLanguage() {
               this.setText(
                  Game.map.lMaps.get(Game.map.getActiveMapID()).mapData.Name
                     + " | "
                     + Game.map.lMaps.get(Game.map.getActiveMapID()).mapData.NumOfProvinces
                     + " - "
                     + Game.lang.get("Scale")
                     + ": "
                     + Game.map.lMaps.get(Game.map.getActiveMapID()).mapData.DefaultMapScale
               );
            }

            @Override
            public void actionElement() {
            }

            @Override
            protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
               super.drawText(oSB, iTranslateX + Game.map.lMaps.get(Game.map.getActiveMapID()).Icon.getWidth(), iTranslateY, isActive);
               Game.map.lMaps.get(Game.map.getActiveMapID())
                  .Icon
                  .draw(
                     oSB,
                     iTranslateX + this.getPosX() + CFG.PADDING * 2,
                     iTranslateY + this.getPosY() + this.getHeight() / 2 - Game.map.lMaps.get(Game.map.getActiveMapID()).Icon.getHeight() / 2
                  );
            }
         }
      );
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(new ButtonMain("", 1, textPosX, paddingLeft, buttonY, CFG.LEFT_MENU_WIDTH - paddingLeft * 2, true) {
         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("FormableCivilizations"));
         }

         @Override
         public void actionElement() {
            Renderer.drawArmyInProvince = false;
            Game.menuManager.setViewID(View.EDITOR_MAPS_EDIT_FORMABLE_CIVS);
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(new ButtonMain("", 1, textPosX, paddingLeft, buttonY, CFG.LEFT_MENU_WIDTH - paddingLeft * 2, true) {
         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("EditConnectionsAndProvinces"));
         }

         @Override
         public void actionElement() {
            Renderer.drawArmyInProvince = false;
            ProvinceDrawDetails.updateDrawProvinceDetails_ProvinceID();
            Game.menuManager.setViewID(View.EDITOR_MAPS_EDIT_PROVINCE_CONNECTIONS);
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(new ButtonMain("", 1, textPosX, paddingLeft, buttonY, CFG.LEFT_MENU_WIDTH - paddingLeft * 2, true) {
         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("EditSeaProvinces"));
         }

         @Override
         public void actionElement() {
            Renderer.drawArmyInProvince = false;
            ProvinceDrawDetails.updateDrawProvinceDetails_SeaProvinces();
            Game.menuManager.setViewID(View.EDITOR_MAPS_EDIT_SEA_PROVINCES);
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(new ButtonMain("", 1, textPosX, paddingLeft, buttonY, CFG.LEFT_MENU_WIDTH - paddingLeft * 2, true) {
         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("EditTerrainTypes"));
         }

         @Override
         public void actionElement() {
            Game.menuManager.setViewID(View.EDITOR_MAPS_EDIT_TERRAIN);
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(new ButtonMain("", 1, textPosX, paddingLeft, buttonY, CFG.LEFT_MENU_WIDTH - paddingLeft * 2, true) {
         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("Resources"));
         }

         @Override
         public void actionElement() {
            Renderer.drawArmyInProvince = false;
            ProvinceDrawDetails.updateDrawProvinceDetails_Resource();
            Game.menuManager.setViewID(View.EDITOR_MAPS_EDIT_RESOURCE);
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(new ButtonMain("", 1, textPosX, paddingLeft, buttonY, CFG.LEFT_MENU_WIDTH - paddingLeft * 2, true) {
         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("GrowthRateEditor"));
         }

         @Override
         public void actionElement() {
            CFG.brushTool = false;
            Renderer.drawArmyInProvince = false;
            ProvinceDrawDetails.updateDrawProvinceDetails_GrowthRate();
            Game.menuManager.setViewID(View.EDITOR_MAPS_EDIT_GROWTH_RATE);
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(new ButtonMain("", 1, textPosX, paddingLeft, buttonY, CFG.LEFT_MENU_WIDTH - paddingLeft * 2, true) {
         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("BaseDevelopmentLevel"));
         }

         @Override
         public void actionElement() {
            CFG.brushTool = false;
            Renderer.drawArmyInProvince = false;
            ProvinceDrawDetails.updateDrawProvinceDetails_Economy();
            Game.menuManager.setViewID(View.EDITOR_MAPS_EDIT_ECONOMY);
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(new ButtonMain("", 1, textPosX, paddingLeft, buttonY, CFG.LEFT_MENU_WIDTH - paddingLeft * 2, true) {
         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("ContinentsEditor"));
         }

         @Override
         public void actionElement() {
            Renderer.drawArmyInProvince = false;
            ProvinceDrawDetails.updateDrawProvinceDetails_Continent();
            Game.menuManager.setViewID(View.EDITOR_MAPS_EDIT_CONTINENTS);
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(new ButtonMain("", 1, textPosX, paddingLeft, buttonY, CFG.LEFT_MENU_WIDTH - paddingLeft * 2, true) {
         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("RegionsEditor"));
         }

         @Override
         public void actionElement() {
            Renderer.drawArmyInProvince = false;
            ProvinceDrawDetails.updateDrawProvinceDetails_GeoRegion();
            Game.menuManager.setViewID(View.EDITOR_MAPS_EDIT_GEO_REGION);
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(new ButtonMain("", 1, textPosX, paddingLeft, buttonY, CFG.LEFT_MENU_WIDTH - paddingLeft * 2, true) {
         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("ArmyPositionEditor"));
         }

         @Override
         public void actionElement() {
            Renderer.drawArmyInProvince = false;
            ProvinceDrawDetails.updateDrawProvinceDetails_ProvinceID();
            Game.menuManager.setViewID(View.EDITOR_MAPS_EDIT_ARMY_POSITION);
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(new ButtonMain("", 1, textPosX, paddingLeft, buttonY, CFG.LEFT_MENU_WIDTH - paddingLeft * 2, true) {
         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("ProvinceName"));
         }

         @Override
         public void actionElement() {
            Game.menuManager.setViewID(View.EDITOR_MAPS_EDIT_PROVINCE_NAMES);
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(new ButtonMain("", 1, textPosX, paddingLeft, buttonY, CFG.LEFT_MENU_WIDTH - paddingLeft * 2, true) {
         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("OptimizationRegionsEditor"));
         }

         @Override
         public void actionElement() {
            Renderer.drawArmyInProvince = false;
            ProvinceDrawDetails.updateDrawProvinceDetails_OptimizationRegions();
            Game.menuManager.setViewID(View.EDITOR_MAPS_EDIT_OPTIMIZATION_REGIONS);
            EditorMapOptimizationRegions.lColors.clear();

            for (int i = 0; i < Game.regions.iRegionsSize; i++) {
               EditorMapOptimizationRegions.lColors.add(CFG.getRandomColor());
            }
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(new ButtonMain("", 1, textPosX, paddingLeft, buttonY, CFG.LEFT_MENU_WIDTH - paddingLeft * 2, true, true) {
         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("GenerateSuggestedCivilizations"));
         }

         @Override
         public void actionElement() {
            Game.menuManager.setViewID(View.EDITOR_MAPS_EDIT_SUGGESTED_CIVILIZATIONS);
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(new ButtonMain("", 1, textPosX, paddingLeft, buttonY, CFG.LEFT_MENU_WIDTH - paddingLeft * 2, true, true) {
         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("Generate") + ": " + Game.lang.get("Templates"));
         }

         @Override
         public void actionElement() {
            Game.menuManager.setViewID(View.EDITOR_MAPS_EDIT_TEMPLATES);
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(new ButtonMain("", 1, textPosX, paddingLeft, buttonY, CFG.LEFT_MENU_WIDTH - paddingLeft * 2, false, true) {
         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("GenerateSeaRoutes"));
         }

         @Override
         public void actionElement() {
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(new ButtonMain("", 1, textPosX, paddingLeft, buttonY, CFG.LEFT_MENU_WIDTH - paddingLeft * 2, true) {
         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("Lines"));
         }

         @Override
         public void actionElement() {
            Game.menuManager.setViewID(View.EDITOR_MAPS_EDIT_LINES);
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(new ButtonMain("", 1, textPosX, paddingLeft, buttonY, CFG.LEFT_MENU_WIDTH - paddingLeft * 2, false) {
         @Override
         public void updateLanguage() {
            this.setText(Game.lang.get("Waves"));
         }

         @Override
         public void actionElement() {
            Game.menuManager.setViewID(View.EDITOR_MAPS_EDIT_WAVES);
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(new ButtonMain(null, 1, -1, paddingLeft, buttonY, CFG.LEFT_MENU_WIDTH - paddingLeft * 2, CFG.isDesktop()) {
         @Override
         public void updateLanguage() {
            this.setText("Print Map - Scale 0.1");
         }

         @Override
         public void actionElement() {
            EditorMap_PrintMap.goBack = View.EDITOR_MAPS_EDIT;
            CFG.brushTool = false;
            EditorMap_PrintMap.scale = 12.5F;
            Game.menuManager.setViewIDWithoutAnimation(View.PRINT_A_MAP);
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(new ButtonMain(null, 1, -1, paddingLeft, buttonY, CFG.LEFT_MENU_WIDTH - paddingLeft * 2, CFG.isDesktop()) {
         @Override
         public void updateLanguage() {
            this.setText("Print Map - Scale 0.25");
         }

         @Override
         public void actionElement() {
            EditorMap_PrintMap.goBack = View.EDITOR_MAPS_EDIT;
            CFG.brushTool = false;
            EditorMap_PrintMap.scale = 4.166667F;
            Game.menuManager.setViewIDWithoutAnimation(View.PRINT_A_MAP);
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(new ButtonMain(null, 1, -1, paddingLeft, buttonY, CFG.LEFT_MENU_WIDTH - paddingLeft * 2, CFG.isDesktop()) {
         @Override
         public void updateLanguage() {
            this.setText("Print Map - Scale 0.5");
         }

         @Override
         public void actionElement() {
            EditorMap_PrintMap.goBack = View.EDITOR_MAPS_EDIT;
            CFG.brushTool = false;
            EditorMap_PrintMap.scale = 2.272727F;
            Game.menuManager.setViewIDWithoutAnimation(View.PRINT_A_MAP);
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      menuElements.add(new ButtonMain(null, 1, -1, paddingLeft, buttonY, CFG.LEFT_MENU_WIDTH - paddingLeft * 2, CFG.isDesktop()) {
         @Override
         public void updateLanguage() {
            this.setText("Print Map - Scale 1");
         }

         @Override
         public void actionElement() {
            EditorMap_PrintMap.goBack = View.EDITOR_MAPS_EDIT;
            CFG.brushTool = false;
            EditorMap_PrintMap.scale = 1.0F;
            Game.menuManager.setViewIDWithoutAnimation(View.PRINT_A_MAP);
         }
      });
      buttonY += menuElements.get(menuElements.size() - 1).getHeight() + buttonYPadding;
      this.initMenu(
         new MenuTitle("", 1.0F, titleHeight, true, true),
         menuX,
         titleHeight + menuY,
         CFG.LEFT_MENU_WIDTH,
         Math.min(buttonY, CFG.GAME_HEIGHT - titleHeight - menuY - CFG.PADDING * 2),
         menuElements,
         true
      );
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      Renderer.drawBoxCorner(
         oSB,
         this.getPosX() + iTranslateX,
         this.getPosY() - this.getTitle().getHeight() + iTranslateY,
         this.getWidth(),
         this.getHeight() + this.getTitle().getHeight() + CFG.PADDING
      );
      Renderer.drawEditorMenuBG(oSB, this.getPosX(), this.getPosY(), this.getWidth(), this.getHeight() + CFG.PADDING, iTranslateX, iTranslateY);
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   @Override
   public void updateLanguage() {
      super.updateLanguage();
      this.getTitle().setText(Game.lang.get("EditMap") + ": " + Game.map.lMaps.get(Game.map.getActiveMapID()).mapData.Name);
   }
}
