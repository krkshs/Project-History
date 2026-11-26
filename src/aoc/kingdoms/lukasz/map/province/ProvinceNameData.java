package aoc.kingdoms.lukasz.map.province;

import aoc.kingdoms.lukasz.jakowski.zOther.Point_XY;
import com.badlogic.gdx.math.Matrix4;
import java.util.ArrayList;
import java.util.List;

public class ProvinceNameData {
   public float fX;
   public float fY;
   public float fX2;
   public float fY2;
   public float fCenterX;
   public float fCenterY;
   public List<Point_XY> drawPoints = new ArrayList<>();
   public float drawAngleLow = 0.0F;
   public List<Matrix4> drawMatrix4 = new ArrayList<>();
   public float fontScale = 1.0F;
}
