package aoc.kingdoms.lukasz.map.map.Ship;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.FileManager;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.textures.Image;
import aoc.kingdoms.lukasz.textures.ImageManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class ShipManager {
   public static List<ShipLine> shipLines = new ArrayList<>();
   public static int shipLinesSize = 0;
   public static List<Ship2> ships = new ArrayList<>();
   public static List<List<Image>> shipImg = new ArrayList<>();
   public static int limitOfShipsAtSea = 0;
   public static List<Integer> shipsAtSea = new ArrayList<>();
   public static int shipsAtSeaSize = 0;
   public static List<Integer> shipsInPort = new ArrayList<>();
   public static int shipsInPortSize = 0;

   public static final void update() {
      if (shipsAtSeaSize < limitOfShipsAtSea) {
         addShipAtSea();
      }
   }

   public static final void addShipAtSea() {
      if (shipsInPortSize > 0) {
         int tID = Game.oR.nextInt(shipsInPortSize);
         ships.get(shipsInPort.get(tID)).remove = false;
         shipsAtSea.add(shipsInPort.get(tID));
         shipsInPort.remove(tID);
         shipsAtSeaSize = shipsAtSea.size();
         shipsInPortSize = shipsInPort.size();
      }
   }

   public static final void drawCurrentScale(SpriteBatch oSB) {
      try {
         if (Game.settingsManager.SHIPS_ON_MAP > 0) {
            if (GameValues.ships.PAUSE_MOVE_SHIPS || Game.gameThread.play) {
               update();
            }

            int ageGroup = Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).SHIP_GROUP;
            oSB.setColor(Color.WHITE);

            for (int i = shipsAtSeaSize - 1; i >= 0; i--) {
               if (GameValues.ships.PAUSE_MOVE_SHIPS || Game.gameThread.play) {
                  ships.get(shipsAtSea.get(i)).update();
               }

               if (ships.get(shipsAtSea.get(i)).remove) {
                  shipsInPort.add(shipsAtSea.get(i));
                  shipsAtSea.remove(i);
                  shipsAtSeaSize = shipsAtSea.size();
                  shipsInPortSize = shipsInPort.size();
               } else {
                  ships.get(shipsAtSea.get(i)).drawCurrentScale(oSB, ageGroup);
               }
            }
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public static final void draw(SpriteBatch oSB) {
      try {
         if (Game.settingsManager.SHIPS_ON_MAP > 0) {
            if (GameValues.ships.PAUSE_MOVE_SHIPS || Game.gameThread.play) {
               update();
            }

            int ageGroup = Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).SHIP_GROUP;
            oSB.setColor(Color.WHITE);

            for (int i = shipsAtSeaSize - 1; i >= 0; i--) {
               if (GameValues.ships.PAUSE_MOVE_SHIPS || Game.gameThread.play) {
                  ships.get(shipsAtSea.get(i)).update();
               }

               if (ships.get(shipsAtSea.get(i)).remove) {
                  shipsInPort.add(shipsAtSea.get(i));
                  shipsAtSea.remove(i);
                  shipsAtSeaSize = shipsAtSea.size();
                  shipsInPortSize = shipsInPort.size();
               } else {
                  ships.get(shipsAtSea.get(i)).draw(oSB, ageGroup);
               }
            }
         }
      } catch (Exception var3) {
         CFG.exceptionStack(var3);
      }
   }

   public static final void loadShipLines() {
      try {
         if (FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "data/" + "Lines_Sea.txt").exists()) {
            FileHandle file = FileManager.loadFile("map/" + Game.map.getFile_ActiveMap_Path() + "data/" + "Lines_Sea.txt");
            String text = file.readString();
            String[] allLines = text.split("\n");

            try {
               if (allLines.length > 0) {
                  for (int i = allLines[0].length() > 0 ? 0 : 1; i < allLines.length; i += 2) {
                     String[] lineX = allLines[i].split(";");
                     String[] lineY = allLines[i + 1].split(";");
                     ShipLine nShipLine = new ShipLine();

                     for (int j = 0; j < lineX.length; j++) {
                        nShipLine.addNewPoint_Just(Integer.parseInt(lineX[j]) * Game.mapBG.iMapScale, Integer.parseInt(lineY[j]) * Game.mapBG.iMapScale);
                     }

                     nShipLine.buildData();
                     addShipLine(nShipLine);
                  }
               }
            } catch (Exception var9) {
               CFG.exceptionStack(var9);
            }
         }
      } catch (Exception var10) {
         CFG.exceptionStack(var10);
      }

      try {
         for (int a = 0; a < GameValues.ships.SHIP_AGES; a++) {
            List<Image> tImages = new ArrayList<>();

            for (int i = 0; i < GameValues.ships.SHIP_IMAGES; i++) {
               tImages.add(new Image(ImageManager.loadTexture("gfx/map/ship_" + a + "_" + i + ".png"), TextureFilter.Linear, TextureWrap.Repeat));
            }

            shipImg.add(tImages);
         }
      } catch (Exception var8) {
         CFG.exceptionStack(var8);
      }

      for (int i = 0; i < shipLinesSize; i++) {
         ships.add(new Ship2(i));
         shipsInPort.add(i);
      }

      shipsInPortSize = shipsInPort.size();
      updateLimitOfShipsAtSea();
   }

   public static final void loadShipLines_Provinces() {
      int paddingCheck = 2 * Game.mapBG.iMapScale;

      for (int i = 0; i < shipLinesSize; i++) {
         for (int j = 0; j < Game.getProvincesSize(); j++) {
            if (Game.getProvince(j).getMinX() <= shipLines.get(i).points.get(0).getPosX()
               && Game.getProvince(j).getMaxX() >= shipLines.get(i).points.get(0).getPosX()
               && Game.getProvince(j).getMinY() <= shipLines.get(i).points.get(0).getPosY()
               && Game.getProvince(j).getMaxY() >= shipLines.get(i).points.get(0).getPosY()
               && Game.pathContains(j, shipLines.get(i).points.get(0).getPosX(), shipLines.get(i).points.get(0).getPosY())) {
               shipLines.get(i).fromProvinceID = j;
               break;
            }

            if (Game.getProvince(j).getMinX() <= shipLines.get(i).points.get(0).getPosX() + paddingCheck
               && Game.getProvince(j).getMaxX() >= shipLines.get(i).points.get(0).getPosX() + paddingCheck
               && Game.getProvince(j).getMinY() <= shipLines.get(i).points.get(0).getPosY()
               && Game.getProvince(j).getMaxY() >= shipLines.get(i).points.get(0).getPosY()
               && Game.pathContains(j, shipLines.get(i).points.get(0).getPosX() + paddingCheck, shipLines.get(i).points.get(0).getPosY())) {
               shipLines.get(i).fromProvinceID = j;
               break;
            }

            if (Game.getProvince(j).getMinX() <= shipLines.get(i).points.get(0).getPosX() - paddingCheck
               && Game.getProvince(j).getMaxX() >= shipLines.get(i).points.get(0).getPosX() - paddingCheck
               && Game.getProvince(j).getMinY() <= shipLines.get(i).points.get(0).getPosY()
               && Game.getProvince(j).getMaxY() >= shipLines.get(i).points.get(0).getPosY()
               && Game.pathContains(j, shipLines.get(i).points.get(0).getPosX() - paddingCheck, shipLines.get(i).points.get(0).getPosY())) {
               shipLines.get(i).fromProvinceID = j;
               break;
            }

            if (Game.getProvince(j).getMinX() <= shipLines.get(i).points.get(0).getPosX()
               && Game.getProvince(j).getMaxX() >= shipLines.get(i).points.get(0).getPosX()
               && Game.getProvince(j).getMinY() <= shipLines.get(i).points.get(0).getPosY() + paddingCheck
               && Game.getProvince(j).getMaxY() >= shipLines.get(i).points.get(0).getPosY() + paddingCheck
               && Game.pathContains(j, shipLines.get(i).points.get(0).getPosX(), shipLines.get(i).points.get(0).getPosY() + paddingCheck)) {
               shipLines.get(i).fromProvinceID = j;
               break;
            }

            if (Game.getProvince(j).getMinX() <= shipLines.get(i).points.get(0).getPosX()
               && Game.getProvince(j).getMaxX() >= shipLines.get(i).points.get(0).getPosX()
               && Game.getProvince(j).getMinY() <= shipLines.get(i).points.get(0).getPosY() - paddingCheck
               && Game.getProvince(j).getMaxY() >= shipLines.get(i).points.get(0).getPosY() - paddingCheck
               && Game.pathContains(j, shipLines.get(i).points.get(0).getPosX(), shipLines.get(i).points.get(0).getPosY() - paddingCheck)) {
               shipLines.get(i).fromProvinceID = j;
               break;
            }
         }
      }

      for (int i = 0; i < shipLinesSize; i++) {
         for (int j = 0; j < Game.getProvincesSize(); j++) {
            if (Game.getProvince(j).getMinX() <= shipLines.get(i).points.get(shipLines.get(i).pointsSize - 1).getPosX()
               && Game.getProvince(j).getMaxX() >= shipLines.get(i).points.get(shipLines.get(i).pointsSize - 1).getPosX()
               && Game.getProvince(j).getMinY() <= shipLines.get(i).points.get(shipLines.get(i).pointsSize - 1).getPosY()
               && Game.getProvince(j).getMaxY() >= shipLines.get(i).points.get(shipLines.get(i).pointsSize - 1).getPosY()
               && Game.pathContains(
                  j,
                  shipLines.get(i).points.get(shipLines.get(i).pointsSize - 1).getPosX(),
                  shipLines.get(i).points.get(shipLines.get(i).pointsSize - 1).getPosY()
               )) {
               shipLines.get(i).toProvinceID = j;
               break;
            }

            if (Game.getProvince(j).getMinX() <= shipLines.get(i).points.get(shipLines.get(i).pointsSize - 1).getPosX() + paddingCheck
               && Game.getProvince(j).getMaxX() >= shipLines.get(i).points.get(shipLines.get(i).pointsSize - 1).getPosX() + paddingCheck
               && Game.getProvince(j).getMinY() <= shipLines.get(i).points.get(shipLines.get(i).pointsSize - 1).getPosY()
               && Game.getProvince(j).getMaxY() >= shipLines.get(i).points.get(shipLines.get(i).pointsSize - 1).getPosY()
               && Game.pathContains(
                  j,
                  shipLines.get(i).points.get(shipLines.get(i).pointsSize - 1).getPosX() + paddingCheck,
                  shipLines.get(i).points.get(shipLines.get(i).pointsSize - 1).getPosY()
               )) {
               shipLines.get(i).toProvinceID = j;
               break;
            }

            if (Game.getProvince(j).getMinX() <= shipLines.get(i).points.get(shipLines.get(i).pointsSize - 1).getPosX() - paddingCheck
               && Game.getProvince(j).getMaxX() >= shipLines.get(i).points.get(shipLines.get(i).pointsSize - 1).getPosX() - paddingCheck
               && Game.getProvince(j).getMinY() <= shipLines.get(i).points.get(shipLines.get(i).pointsSize - 1).getPosY()
               && Game.getProvince(j).getMaxY() >= shipLines.get(i).points.get(shipLines.get(i).pointsSize - 1).getPosY()
               && Game.pathContains(
                  j,
                  shipLines.get(i).points.get(shipLines.get(i).pointsSize - 1).getPosX() - paddingCheck,
                  shipLines.get(i).points.get(shipLines.get(i).pointsSize - 1).getPosY()
               )) {
               shipLines.get(i).toProvinceID = j;
               break;
            }

            if (Game.getProvince(j).getMinX() <= shipLines.get(i).points.get(shipLines.get(i).pointsSize - 1).getPosX()
               && Game.getProvince(j).getMaxX() >= shipLines.get(i).points.get(shipLines.get(i).pointsSize - 1).getPosX()
               && Game.getProvince(j).getMinY() <= shipLines.get(i).points.get(shipLines.get(i).pointsSize - 1).getPosY() + paddingCheck
               && Game.getProvince(j).getMaxY() >= shipLines.get(i).points.get(shipLines.get(i).pointsSize - 1).getPosY() + paddingCheck
               && Game.pathContains(
                  j,
                  shipLines.get(i).points.get(shipLines.get(i).pointsSize - 1).getPosX(),
                  shipLines.get(i).points.get(shipLines.get(i).pointsSize - 1).getPosY() + paddingCheck
               )) {
               shipLines.get(i).toProvinceID = j;
               break;
            }

            if (Game.getProvince(j).getMinX() <= shipLines.get(i).points.get(shipLines.get(i).pointsSize - 1).getPosX()
               && Game.getProvince(j).getMaxX() >= shipLines.get(i).points.get(shipLines.get(i).pointsSize - 1).getPosX()
               && Game.getProvince(j).getMinY() <= shipLines.get(i).points.get(shipLines.get(i).pointsSize - 1).getPosY() - paddingCheck
               && Game.getProvince(j).getMaxY() >= shipLines.get(i).points.get(shipLines.get(i).pointsSize - 1).getPosY() - paddingCheck
               && Game.pathContains(
                  j,
                  shipLines.get(i).points.get(shipLines.get(i).pointsSize - 1).getPosX(),
                  shipLines.get(i).points.get(shipLines.get(i).pointsSize - 1).getPosY() - paddingCheck
               )) {
               shipLines.get(i).toProvinceID = j;
               break;
            }
         }
      }
   }

   public static final void addShipLine(ShipLine nShipLine) {
      shipLines.add(nShipLine);
      shipLinesSize = shipLines.size();
   }

   public static final void updateLimitOfShipsAtSea() {
      limitOfShipsAtSea = (int)Math.min((float)shipLinesSize, Game.settingsManager.SHIPS_ON_MAP / 100.0F * shipLinesSize);
   }
}
