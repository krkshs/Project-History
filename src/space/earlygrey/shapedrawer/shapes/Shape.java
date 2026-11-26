package space.earlygrey.shapedrawer.shapes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.FloatArray;
import space.earlygrey.shapedrawer.JoinType;
import space.earlygrey.shapedrawer.ShapeUtils;

public interface Shape<T extends Shape> {
  void draw();
  
  T color(Color paramColor);
  
  public static interface OutlinedRectangle<T extends OutlinedRectangle<T>> extends FilledRectangle<OutlinedRectangle<T>> {
    OutlinedRectangle joinType(JoinType param1JoinType);
    
    OutlinedRectangle lineWidth(float param1Float);
  }
  
  public static interface FilledRectangle<T extends FilledRectangle<T>> extends Shape<T> {
    T position(float param1Float1, float param1Float2);
    
    T position(Vector2 param1Vector2);
    
    T size(float param1Float1, float param1Float2);
    
    T rotation(float param1Float);
    
    T rotate(float param1Float);
  }
  
  public static interface Arc<T extends Arc<T>> extends Sector<Arc<T>> {
    Arc joinType(JoinType param1JoinType);
    
    Arc lineWidth(float param1Float);
  }
  
  public static interface Sector<T extends Sector<T>> extends Shape<T> {
    T centre(float param1Float1, float param1Float2);
    
    T centre(Vector2 param1Vector2);
    
    T radius(float param1Float);
    
    T startAngle(float param1Float);
    
    T radians(float param1Float);
    
    T sides(int param1Int);
  }
  
  public static interface OutlinedRegularPolygon<T extends OutlinedRegularPolygon<T>> extends FilledRegularPolygon<OutlinedRegularPolygon<T>> {
    OutlinedRegularPolygon joinType(JoinType param1JoinType);
    
    OutlinedRegularPolygon lineWidth(float param1Float);
  }
  
  public static interface FilledRegularPolygon<T extends FilledRegularPolygon<T>> extends Shape<T> {
    T centre(Vector2 param1Vector2);
    
    T radiusX(float param1Float);
    
    T radiusY(float param1Float);
    
    T radius(float param1Float);
    
    T rotation(float param1Float);
    
    T rotate(float param1Float);
    
    T sides(int param1Int);
  }
  
  public static interface PolyLine<T extends PolyLine<T>> extends OutlinedPolygon<PolyLine<T>> {
    PolyLine lineWidth(ShapeUtils.LineWidthFunction param1LineWidthFunction);
  }
  
  public static interface OutlinedPolygon<T extends OutlinedPolygon<T>> extends FilledPolygon<T> {
    T joinType(JoinType param1JoinType);
    
    T lineWidth(float param1Float);
  }
  
  public static interface FilledPolygon<T extends FilledPolygon<T>> extends Shape<T> {
    <V extends Vector2> T vertices(Iterable<V> param1Iterable);
    
    T vertices(FloatArray param1FloatArray);
    
    T vertices(float[] param1ArrayOffloat);
    
    T addVertex(float param1Float1, float param1Float2);
    
    T addVertex(Vector2 param1Vector2);
    
    T offset(float param1Float1, float param1Float2);
    
    T scale(float param1Float1, float param1Float2);
  }
  
  public static interface OutlinedTriangle<T extends OutlinedTriangle<T>> extends FilledTriangle<OutlinedTriangle<T>> {
    OutlinedTriangle joinType(JoinType param1JoinType);
    
    OutlinedTriangle lineWidth(float param1Float);
  }
  
  public static interface FilledTriangle<T extends FilledTriangle<T>> extends Shape<T> {
    T a(float param1Float1, float param1Float2);
    
    T b(float param1Float1, float param1Float2);
    
    T c(float param1Float1, float param1Float2);
    
    T a(Vector2 param1Vector2);
    
    T b(Vector2 param1Vector2);
    
    T c(Vector2 param1Vector2);
  }
  
  public static interface Line<T extends Line<T>> extends Shape<Line<T>> {
    Line from(float param1Float1, float param1Float2);
    
    Line to(float param1Float1, float param1Float2);
    
    Line from(Vector2 param1Vector2);
    
    Line to(Vector2 param1Vector2);
    
    Line joinType(JoinType param1JoinType);
    
    Line lineWidth(float param1Float);
    
    Line startColor(Color param1Color);
    
    Line endColor(Color param1Color);
    
    Line snap(boolean param1Boolean);
  }
  
  public static interface OutlinedEllipse<T extends OutlinedEllipse<T>> extends FilledEllipse<OutlinedEllipse<T>> {
    OutlinedEllipse joinType(JoinType param1JoinType);
    
    OutlinedEllipse lineWidth(float param1Float);
  }
  
  public static interface FilledEllipse<T extends FilledEllipse<T>> extends Shape<T> {
    T centre(float param1Float1, float param1Float2);
    
    T centre(Vector2 param1Vector2);
    
    T radiusX(float param1Float);
    
    T radiusY(float param1Float);
    
    T rotation(float param1Float);
    
    T rotate(float param1Float);
  }
  
  public static interface OutlinedCircle<T extends OutlinedCircle<T>> extends FilledCircle<OutlinedCircle<T>> {
    OutlinedCircle joinType(JoinType param1JoinType);
    
    OutlinedCircle lineWidth(float param1Float);
  }
  
  public static interface FilledCircle<T extends FilledCircle<T>> extends Shape<T> {
    T centre(float param1Float1, float param1Float2);
    
    T centre(Vector2 param1Vector2);
    
    T radius(float param1Float);
  }
}
