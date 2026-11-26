package aoc.kingdoms.lukasz.units;

import aoc.kingdoms.lukasz.jakowski.zOther.XY;
import java.util.ArrayList;
import java.util.List;

public class Path {
   public List<XY> pathPoints = new ArrayList<>();
   public int moveToPosX;
   public int moveToPosY;

   public Path(int posX, int posY, int toPosX, int toPosY) {
      this.moveToPosX = toPosX;
      this.moveToPosY = toPosY;
      int diffX = toPosX - posX;
      int diffY = toPosY - posY;
      this.pathPoints.add(new XY(toPosX, toPosY));
   }

   public void update(int i) {
   }
}
