package aoc.kingdoms.lukasz.menusMapEditor;

import age.of.civilizations2.jakowski.lukasz.Civilizations.CivTemplate;
import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.FileManager;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.map.map.MapScenarios;
import aoc.kingdoms.lukasz.menu.Menu;
import aoc.kingdoms.lukasz.menu.View;
import aoc.kingdoms.lukasz.menu_element.MenuElement;
import aoc.kingdoms.lukasz.menu_element.Status;
import aoc.kingdoms.lukasz.menu_element.textStatic.Text_Static;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EditorMap_GenerateCivTemplates extends Menu {
   public int scenarioIDBefore = 0;

   public EditorMap_GenerateCivTemplates() {
      List<MenuElement> menuElements = new ArrayList<>();
      menuElements.add(
         new Text_Static(Game.lang.get("Generate") + ": " + Game.lang.get("Civilizations") + ", " + Game.lang.get("Provinces"), CFG.PADDING, CFG.PADDING)
      );
      this.initMenu(null, 0, 0, CFG.GAME_WIDTH, CFG.GAME_HEIGHT, menuElements, true);
      this.scenarioIDBefore = Game.scenarioID;
      Game.scenarioID = 0;
      new Thread(new Runnable() {
         @Override
         public void run() {
            Gdx.app.postRunnable(new Runnable() {
               @Override
               public void run() {
                  EditorMap_GenerateCivTemplates.this.loadData();
               }
            });
         }
      }).start();
   }

   @Override
   public void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean menuIsActive, Status titleStatus) {
      super.draw(oSB, iTranslateX, iTranslateY, menuIsActive, titleStatus);
   }

   private final void loadData() {
      while (Game.scenarioID < Game.mapScenarios.SCENARIOS_SIZE) {
         this.buildTemplates();
         Game.scenarioID++;
      }

      Game.scenarioID = this.scenarioIDBefore;
      Game.menuManager.setViewID(View.EDITOR_MAPS_EDIT);
   }

   public final void buildTemplates() {
      List<String> civTags = new ArrayList<>();
      List<List<Integer>> civProvinces = new ArrayList<>();

      try {
         FileHandle fileList = FileManager.loadFile(
            "map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + Game.mapScenarios.lScenarios_TagsList.get(Game.scenarioID) + "/" + "Data.json"
         );
         Json json = new Json();

         for (JsonValue jValue : json.fromJson(ArrayList.class, fileList)) {
            MapScenarios.ScenarioCivData tempData = json.readValue(MapScenarios.ScenarioCivData.class, jValue);
            civTags.add(Game.ideologiesManager.getRealTag(tempData.CivTAG));
            List<Integer> tesProv = new ArrayList<>();
            if (tempData.CPID >= 0) {
               tesProv.add(tempData.CPID);
            }

            civProvinces.add(tesProv);
         }
      } catch (Exception var17) {
         CFG.exceptionStack(var17);
      }

      try {
         FileHandle fileList = FileManager.loadFile(
            "map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + Game.mapScenarios.lScenarios_TagsList.get(Game.scenarioID) + "/" + "DataProvinces.json"
         );
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);
         int tCivID = 0;

         for (JsonValue jValue : tempArrayData) {
            MapScenarios.ScenarioCivData_Provinces tempData = json.readValue(MapScenarios.ScenarioCivData_Provinces.class, jValue);
            int i = 0;

            for (int iSize = tempData.Provinces.length; i < iSize; i++) {
               if (!civProvinces.get(tCivID).contains(tempData.Provinces[i])) {
                  civProvinces.get(tCivID).add(tempData.Provinces[i]);
               }
            }

            tCivID++;
         }

         tempArrayData.clear();
         Object var24 = null;
      } catch (Exception var16) {
         CFG.exceptionStack(var16);
      }

      int year = 1;

      try {
         FileHandle file2 = FileManager.loadFile(
            "map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + Game.mapScenarios.lScenarios_TagsList.get(Game.scenarioID) + "/" + "Details.json"
         );
         Json json = new Json();
         MapScenarios.Details details = json.fromJson(MapScenarios.Details.class, file2);
         year = details.Year;
      } catch (Exception var15) {
         CFG.exceptionStack(var15);
      }

      for (int i = 0; i < civTags.size(); i++) {
         FileHandle file = null;
         CivTemplate civTemplate;
         if (FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "civs_template/" + civTags.get(i)).exists()) {
            try {
               file = FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "civs_template/" + civTags.get(i));
               civTemplate = (CivTemplate)CFG.deserialize(file.readBytes());
            } catch (Exception var12) {
               civTemplate = new CivTemplate();
               CFG.exceptionStack(var12);
            }
         } else {
            civTemplate = new CivTemplate();
         }

         if (civProvinces.get(i).size() > 1) {
            try {
               civTemplate.addNewTemplate(civTags.get(i), year, civProvinces.get(i));

               try {
                  FileHandle file4 = FileManager.getSaveType("map/" + Game.map.getFile_ActiveMap_Path() + "civs_template/" + civTags.get(i));
                  file4.writeBytes(CFG.serialize(civTemplate), false);
               } catch (IOException var13) {
                  CFG.exceptionStack(var13);
               }
            } catch (GdxRuntimeException var14) {
               CFG.exceptionStack(var14);
            }
         }
      }
   }
}
