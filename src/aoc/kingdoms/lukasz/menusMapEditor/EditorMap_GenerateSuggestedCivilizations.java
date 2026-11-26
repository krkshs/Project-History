package aoc.kingdoms.lukasz.menusMapEditor;

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
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import java.util.ArrayList;
import java.util.List;

public class EditorMap_GenerateSuggestedCivilizations extends Menu {
   public int scenarioIDBefore = 0;

   public EditorMap_GenerateSuggestedCivilizations() {
      List<MenuElement> menuElements = new ArrayList<>();
      menuElements.add(new Text_Static(Game.lang.get("GenerateSuggestedCivilizations"), CFG.PADDING, CFG.PADDING));
      this.initMenu(null, 0, 0, CFG.GAME_WIDTH, CFG.GAME_HEIGHT, menuElements, true);
      this.scenarioIDBefore = Game.scenarioID;
      Game.scenarioID = 0;
      new Thread(new Runnable() {
         @Override
         public void run() {
            Gdx.app.postRunnable(new Runnable() {
               @Override
               public void run() {
                  EditorMap_GenerateSuggestedCivilizations.this.loadData();
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
         this.build_SuggestedOwners();
         Game.scenarioID++;
      }

      Game.scenarioID = this.scenarioIDBefore;
      Game.menuManager.setViewID(View.EDITOR_MAPS_EDIT);
   }

   public void build_SuggestedOwners() {
      try {
         FileHandle fileList = FileManager.loadFile(
            "map/" + Game.map.getFile_ActiveMap_Path() + "scenarios/" + Game.mapScenarios.lScenarios_TagsList.get(Game.scenarioID) + "/" + "Data.json"
         );
         Json json = new Json();
         ArrayList<JsonValue> tempArrayData = json.fromJson(ArrayList.class, fileList);
         int tCivID = 1;

         for (JsonValue jValue : tempArrayData) {
            MapScenarios.ScenarioCivData tempData = json.readValue(MapScenarios.ScenarioCivData.class, jValue);
            if (tempData.CPID >= 0) {
               if (FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "suggestedCivilizations/" + tempData.CPID + ".txt").exists()) {
                  FileHandle file = FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "suggestedCivilizations/" + tempData.CPID + ".txt");
                  String sOwners = file.readString();
                  String[] sRes = sOwners.split(";");
                  boolean add = true;

                  for (int a = 0; a < sRes.length; a++) {
                     if (sRes[a].equals(tempData.CivTAG)) {
                        add = false;
                     }
                  }

                  if (add) {
                     FileHandle fileWrite = FileManager.getSaveType(
                        "map/" + Game.map.getFile_ActiveMap_Path() + "suggestedCivilizations/" + tempData.CPID + ".txt"
                     );
                     fileWrite.writeString(tempData.CivTAG + ";", true);
                  }
               } else {
                  FileHandle fileWrite = FileManager.getSaveType(
                     "map/" + Game.map.getFile_ActiveMap_Path() + "suggestedCivilizations/" + tempData.CPID + ".txt"
                  );
                  fileWrite.writeString(tempData.CivTAG + ";", false);
               }
            }
         }
      } catch (Exception var13) {
         CFG.exceptionStack(var13);
      }
   }
}
