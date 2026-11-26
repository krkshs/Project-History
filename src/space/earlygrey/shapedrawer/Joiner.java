package space.earlygrey.shapedrawer;

import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;

class Joiner {
  static final Vector2 AB = new Vector2(), BC = new Vector2(), v = new Vector2();
  
  static float preparePointyJoin(Vector2 A, Vector2 B, Vector2 C, Vector2 D, Vector2 E, float halfLineWidth) {
    AB.set(B).sub(A);
    BC.set(C).sub(B);
    float angle = ShapeUtils.angleRad(AB, BC);
    if (ShapeUtils.epsilonEquals(angle, 0.0F) || ShapeUtils.epsilonEquals(angle, 6.2831855F)) {
      prepareStraightJoin(B, D, E, halfLineWidth);
      return angle;
    } 
    float len = (float)((double)halfLineWidth / Math.sin((double)angle));
    boolean bendsLeft = (angle < 0.0F);
    AB.setLength(len);
    BC.setLength(len);
    Vector<Vector2> insidePoint = bendsLeft ? D : E;
    Vector<Vector2> outsidePoint = bendsLeft ? E : D;
    insidePoint.set(B).sub(AB).add(BC);
    outsidePoint.set(B).add(AB).sub(BC);
    return angle;
  }
  
  static boolean prepareSmoothJoin(Vector2 A, Vector2 B, Vector2 C, Vector2 D, Vector2 E, float halfLineWidth, boolean startOfEdge) {
    AB.set(B).sub(A);
    BC.set(C).sub(B);
    float angle = ShapeUtils.angleRad(AB, BC);
    if (ShapeUtils.epsilonEquals(angle, 0.0F) || ShapeUtils.epsilonEquals(angle, 6.2831855F)) {
      prepareStraightJoin(B, D, E, halfLineWidth);
      return true;
    } 
    float len = (float)((double)halfLineWidth / Math.sin((double)angle));
    AB.setLength(len);
    BC.setLength(len);
    boolean bendsLeft = (angle < 0.0F);
    Vector<Vector2> insidePoint = bendsLeft ? D : E;
    Vector<Vector2> outsidePoint = bendsLeft ? E : D;
    insidePoint.set(B).sub(AB).add(BC);
    Vector2 edgeDirection = startOfEdge ? BC : AB;
    if (bendsLeft) {
      v.set(edgeDirection.y, -edgeDirection.x);
    } else
      v.set(-edgeDirection.y, edgeDirection.x); 
    v.setLength(halfLineWidth);
    outsidePoint.set(B).add(v);
    return bendsLeft;
  }
  
  static void prepareStraightJoin(Vector2 B, Vector2 D, Vector2 E, float halfLineWidth) {
    AB.setLength(halfLineWidth);
    D.set(-AB.y, AB.x).add(B);
    E.set(AB.y, -AB.x).add(B);
  }
  
  static void prepareFlatEndpoint(float pathPointX, float pathPointY, float endPointX, float endPointY, Vector2 D, Vector2 E, float halfLineWidth) {
    v.set(endPointX, endPointY).sub(pathPointX, pathPointY).setLength(halfLineWidth);
    D.set(v.y, -v.x).add(endPointX, endPointY);
    E.set(-v.y, v.x).add(endPointX, endPointY);
  }
  
  static void prepareFlatEndpoint(Vector2 pathPoint, Vector2 endPoint, Vector2 D, Vector2 E, float halfLineWidth) {
    prepareFlatEndpoint(pathPoint.x, pathPoint.y, endPoint.x, endPoint.y, D, E, halfLineWidth);
  }
  
  static void prepareRadialEndpoint(Vector2 A, Vector2 D, Vector2 E, float halfLineWidth) {
    v.set(A).setLength(halfLineWidth);
    D.set(A).sub(v);
    E.set(A).add(v);
  }
}
