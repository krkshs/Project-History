package aoc.kingdoms.lukasz.map.moveUnits;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.GameValues;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;
import aoc.kingdoms.lukasz.map.diplomacy.DiplomacyManager;
import aoc.kingdoms.lukasz.map.province.Province;
import aoc.kingdoms.lukasz.map.province.ProvinceDrawArmy;
import aoc.kingdoms.lukasz.menu.Colors;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import space.earlygrey.shapedrawer.JoinType;

public class MoveUnits {
   public String key;
   private List<Integer> lRoute = new ArrayList<>();
   public int iRouteSize = 0;
   public float doneMovementProgressWidth = 0.0F;
   public float currentMovementProgressWidth = 0.0F;
   public float movementProgressOverWidth = 0.0F;
   public boolean inBattle = false;
   public boolean inRetreat = false;
   public List<Integer> iWidth = new ArrayList<>();
   public long lMovingTime = 0L;
   public float fMovingPercentage = 0.0F;
   public long lCurrentMovingTime = 0L;
   public float fCurrentMovingPercentage = 0.0F;
   public float fSpeed = 0.0F;
   public int extraArmyY = 0;
   public Color colorLine;
   private int iPrecision;
   private Vector2[] vPoints;
   public static final int PRECISION = 16;
   public boolean isBelowZero = false;
   public MoveUnits.LittleAnimation littleAnimationMainLine;
   public MoveUnits.LittleAnimation littleAnimationMovingArmy;

   public MoveUnits(int nCivID, int iFromProvinceID, int iToProvinceID, String key, int extraArmyY, boolean inRetreat, boolean landOnly) {
      this.key = key;
      this.extraArmyY = ProvinceDrawArmy.getArmyHeight() * extraArmyY + extraArmyY * 2;
      this.inRetreat = inRetreat;
      this.updateColorLine(nCivID, inRetreat);
      this.updateSpeed(nCivID, iFromProvinceID);
      this.buildRoute2(nCivID, iFromProvinceID, iToProvinceID, landOnly);
      if (this.iRouteSize > 1) {
         this.buildMoveUnitsLine(true, nCivID);
         int armyID = Game.getProvince(iFromProvinceID).getArmyKeyID(key);
         if (armyID >= 0) {
            Game.getProvince(iFromProvinceID).getArmy(armyID).iShiftX = Game.getProvince(iFromProvinceID).getArmy(armyID).defaultShiftX();
            Game.getProvince(iFromProvinceID).getArmy(armyID).iShiftY = Game.getProvince(iFromProvinceID).getArmy(armyID).defaultShiftY();
         }
      }
   }

   public MoveUnits(int nCivID, int iFromProvinceID, int iToProvinceID, String key, int extraArmyY, int iFromProvinceIDExtra) {
      this.key = key;
      this.extraArmyY = extraArmyY;
      this.updateColorLine(nCivID, this.inRetreat);
      this.updateSpeed(nCivID, iFromProvinceID);
      this.buildRoute2(nCivID, iFromProvinceID, iToProvinceID, false);
      if (this.iRouteSize > 1) {
         List<Integer> tRoute = new ArrayList<>();

         for (int i = 0; i < this.iRouteSize; i++) {
            tRoute.add(this.lRoute.get(i));
         }

         this.lRoute.clear();
         this.lRoute.add(iFromProvinceIDExtra);

         for (int i = 0; i < this.iRouteSize; i++) {
            this.lRoute.add(tRoute.get(i));
         }

         this.iRouteSize = this.lRoute.size();
         this.buildMoveUnitsLine_FromToTheSameProvince(true, nCivID);
      }
   }

   public void updateColorLine(int nCivID, boolean inRetreat) {
      if (inRetreat) {
         this.colorLine = Colors.HOVER_NEGATIVE;
      } else if (nCivID == Game.player.iCivID) {
         this.colorLine = Colors.HOVER_GOLD;
      } else {
         this.colorLine = CFG.getColorStep(Colors.HOVER_GOLD, Game.getCiv(nCivID).getColor(1.0F), 60, 100, 0.4F);
      }
   }

   public void updateSpeed(int nCivID, int iFromProvinceID) {
      int tArmyID = Game.getProvince(iFromProvinceID).getArmyKeyID(this.key);
      this.fSpeed = GameValues.army.MIN_ARMY_MOVEMENT_SPEED;

      try {
         if (tArmyID >= 0) {
            this.fSpeed = Math.max(
               GameValues.army.MIN_ARMY_MOVEMENT_SPEED,
               Game.getProvince(iFromProvinceID).getArmy(tArmyID).getArmyMovementSpeed()
                  * (this.inRetreat ? GameValues.army.RETREATING_ARMY_MOVEMENT_SPEED : 1.0F)
            );
         } else {
            Game.ArmyPos nArmyPos = Game.findArmy_FullCheck(nCivID, this.key);
            if (nArmyPos != null) {
               this.fSpeed = Math.max(
                  GameValues.army.MIN_ARMY_MOVEMENT_SPEED,
                  Game.getProvince(nArmyPos.iProvinceID).getArmy(nArmyPos.iID).getArmyMovementSpeed()
                     * (this.inRetreat ? GameValues.army.RETREATING_ARMY_MOVEMENT_SPEED : 1.0F)
               );
            } else if (this.fSpeed <= GameValues.army.MIN_ARMY_MOVEMENT_SPEED) {
               this.fSpeed = GameValues.army.MIN_ARMY_MOVEMENT_SPEED * (this.inRetreat ? GameValues.army.RETREATING_ARMY_MOVEMENT_SPEED : 1.0F);
            }
         }
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
         if (this.fSpeed <= GameValues.army.MIN_ARMY_MOVEMENT_SPEED) {
            this.fSpeed = GameValues.army.MIN_ARMY_MOVEMENT_SPEED * (this.inRetreat ? GameValues.army.RETREATING_ARMY_MOVEMENT_SPEED : 1.0F);
         }
      }
   }

   protected boolean buildRoute2(int nCivID, int fromProvinceID, int toProvinceID, boolean landOnly) {
      this.lRoute.clear();
      if (fromProvinceID >= 0 && toProvinceID >= 0 && Game.getProvince(toProvinceID).getWasteland() < 0) {
         PriorityQueue<MoveUnits.Node> openSet = new PriorityQueue<>(Comparator.comparingDouble(MoveUnits.Node::getFCost));
         Map<Integer, MoveUnits.Node> closedSet = new HashMap<>();
         MoveUnits.Node startNode = new MoveUnits.Node(fromProvinceID, null, 0.0F, 0.0F);
         openSet.add(startNode);
         closedSet.put(fromProvinceID, startNode);

         while (!openSet.isEmpty()) {
            MoveUnits.Node currentNode = openSet.poll();
            if (currentNode.provinceID == toProvinceID) {
               this.setPath(fromProvinceID, toProvinceID, reconstructPath(currentNode), toProvinceID, fromProvinceID);
               return true;
            }

            for (int neighbor : Game.getProvince(currentNode.provinceID).lNeighboringProvinces) {
               if (!closedSet.containsKey(neighbor)) {
                  if (canBeUsedInPath(nCivID, Game.getProvince(neighbor).getProvinceID(), this.inRetreat, fromProvinceID)) {
                     float gCost = currentNode.gCost + Game.getManhattanDistance(currentNode.provinceID, neighbor);
                     float hCost = Game.getManhattanDistance(neighbor, toProvinceID);
                     float fCost = gCost + hCost;
                     MoveUnits.Node neighborNode = new MoveUnits.Node(neighbor, currentNode, gCost, hCost);
                     if (!openSet.contains(neighborNode) || fCost < openSet.peek().getFCost()) {
                        if (openSet.contains(neighborNode)) {
                           openSet.remove(neighborNode);
                        }

                        openSet.add(neighborNode);
                     }

                     closedSet.put(neighbor, neighborNode);
                  } else {
                     closedSet.put(neighbor, null);
                  }
               }
            }

            if (!landOnly) {
               for (int neighborx : Game.getProvince(currentNode.provinceID).lNeighboringSeaProvinces) {
                  if (!closedSet.containsKey(neighborx)) {
                     float gCost = currentNode.gCost + Game.getDistanceFromProvinceToProvince(currentNode.provinceID, neighborx);
                     float hCost = Game.getDistanceFromProvinceToProvince(neighborx, toProvinceID);
                     float fCost = gCost + hCost;
                     MoveUnits.Node neighborNode = new MoveUnits.Node(neighborx, currentNode, gCost, hCost);
                     if (!openSet.contains(neighborNode)) {
                        openSet.add(neighborNode);
                     } else if (fCost < openSet.peek().getFCost()) {
                        if (openSet.contains(neighborNode)) {
                           openSet.remove(neighborNode);
                        }

                        openSet.add(neighborNode);
                     }

                     closedSet.put(neighborx, neighborNode);
                  }
               }
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private static List<Integer> reconstructPath(MoveUnits.Node node) {
      List<Integer> path = new ArrayList<>();

      while (node != null) {
         path.add(node.provinceID);
         node = node.parent;
      }

      Collections.reverse(path);
      path.remove(0);
      return path;
   }

   protected boolean buildRoute(int nCivID, int fromProvinceID, int toProvinceID, boolean landOnly) {
      this.lRoute.clear();
      if (fromProvinceID >= 0 && toProvinceID >= 0 && Game.getProvince(toProvinceID).getWasteland() < 0) {
         List<Integer> was = new ArrayList<>();
         was.add(fromProvinceID);

         for (int i = 0; i < Game.getProvincesSize(); i++) {
            this.setWas(i, false);
         }

         this.setWas(fromProvinceID, true);
         List<Integer> in = new ArrayList<>();
         List<List<Integer>> inPath = new ArrayList<>();
         Province provinceA = Game.getProvince(fromProvinceID);

         for (int i = 0; i < provinceA.getNeighboringProvincesSize(); i++) {
            if (canBeUsedInPath(nCivID, provinceA.getNeighboringProvinces(i), this.inRetreat, fromProvinceID)) {
               in.add(Game.getProvince(provinceA.getNeighboringProvinces(i)).getProvinceID());
               List<Integer> tP = new ArrayList<>();
               tP.add(Game.getProvince(provinceA.getNeighboringProvinces(i)).getProvinceID());
               inPath.add(tP);
               was.add(Game.getProvince(provinceA.getNeighboringProvinces(i)).getProvinceID());
               this.setWas(Game.getProvince(provinceA.getNeighboringProvinces(i)).getProvinceID(), true);
            }
         }

         if (!landOnly && !provinceA.getSeaProvince()) {
            for (int ix = 0; ix < provinceA.getNeighboringSeaProvincesSize(); ix++) {
               in.add(Game.getProvince(provinceA.getNeighboringSeaProvinces(ix)).getProvinceID());
               List<Integer> tP = new ArrayList<>();
               tP.add(Game.getProvince(provinceA.getNeighboringSeaProvinces(ix)).getProvinceID());
               inPath.add(tP);
               was.add(Game.getProvince(provinceA.getNeighboringSeaProvinces(ix)).getProvinceID());
               this.setWas(Game.getProvince(provinceA.getNeighboringSeaProvinces(ix)).getProvinceID(), true);
            }
         }

         for (int ix = 0; ix < in.size(); ix++) {
            if (Game.getProvince(in.get(ix)).getProvinceID() == toProvinceID) {
               this.setPath(fromProvinceID, toProvinceID, inPath.get(ix), toProvinceID, fromProvinceID);
               this.clearWas(was);
               return true;
            }
         }

         this.buildPath(nCivID, was, in, inPath, fromProvinceID, toProvinceID, true, landOnly);
         return true;
      } else {
         return false;
      }
   }

   protected boolean buildPath(
      int civID, List<Integer> was, List<Integer> in, List<List<Integer>> inPath, int from, int lookingFor, boolean forDirection, boolean landOnly
   ) {
      List<Integer> nIN = new ArrayList<>();
      List<List<Integer>> nINPath = new ArrayList<>();
      if (forDirection) {
         for (int i = 0; i < in.size(); i++) {
            for (int j = 0; j < Game.getProvince(in.get(i)).getNeighboringProvincesSize(); j++) {
               if (!this.getWas(Game.getProvince(Game.getProvince(in.get(i)).getNeighboringProvinces(j)).getProvinceID())
                  && canBeUsedInPath(civID, Game.getProvince(Game.getProvince(in.get(i)).getNeighboringProvinces(j)).getProvinceID(), this.inRetreat, from)) {
                  if (Game.getProvince(Game.getProvince(in.get(i)).getNeighboringProvinces(j)).getProvinceID() == lookingFor) {
                     this.setPath(from, lookingFor, inPath.get(i), lookingFor, from);
                     this.clearWas(was);
                     return true;
                  }

                  nIN.add(Game.getProvince(Game.getProvince(in.get(i)).getNeighboringProvinces(j)).getProvinceID());
                  List<Integer> tPL = new ArrayList<>(inPath.get(i));
                  tPL.add(Game.getProvince(Game.getProvince(in.get(i)).getNeighboringProvinces(j)).getProvinceID());
                  nINPath.add(tPL);
                  this.setWas(Game.getProvince(Game.getProvince(in.get(i)).getNeighboringProvinces(j)).getProvinceID(), true);
                  was.add(Game.getProvince(Game.getProvince(in.get(i)).getNeighboringProvinces(j)).getProvinceID());
               }
            }

            if (!landOnly && !Game.getProvince(in.get(i)).getSeaProvince()) {
               for (int jx = 0; jx < Game.getProvince(in.get(i)).getNeighboringSeaProvincesSize(); jx++) {
                  if (!this.getWas(Game.getProvince(Game.getProvince(in.get(i)).getNeighboringSeaProvinces(jx)).getProvinceID())) {
                     if (Game.getProvince(Game.getProvince(in.get(i)).getNeighboringSeaProvinces(jx)).getProvinceID() == lookingFor) {
                        this.setPath(from, lookingFor, inPath.get(i), lookingFor, from);
                        this.clearWas(was);
                        return true;
                     }

                     nIN.add(Game.getProvince(Game.getProvince(in.get(i)).getNeighboringSeaProvinces(jx)).getProvinceID());
                     List<Integer> tPL = new ArrayList<>(inPath.get(i));
                     tPL.add(Game.getProvince(Game.getProvince(in.get(i)).getNeighboringSeaProvinces(jx)).getProvinceID());
                     nINPath.add(tPL);
                     this.setWas(Game.getProvince(Game.getProvince(in.get(i)).getNeighboringSeaProvinces(jx)).getProvinceID(), true);
                     was.add(Game.getProvince(Game.getProvince(in.get(i)).getNeighboringSeaProvinces(jx)).getProvinceID());
                  }
               }
            }
         }
      } else {
         for (int i = 0; i < in.size(); i++) {
            for (int jxx = Game.getProvince(in.get(i)).getNeighboringProvincesSize() - 1; jxx >= 0; jxx--) {
               if (!this.getWas(Game.getProvince(Game.getProvince(in.get(i)).getNeighboringProvinces(jxx)).getProvinceID())
                  && canBeUsedInPath(civID, Game.getProvince(Game.getProvince(in.get(i)).getNeighboringProvinces(jxx)).getProvinceID(), this.inRetreat, from)) {
                  if (Game.getProvince(Game.getProvince(in.get(i)).getNeighboringProvinces(jxx)).getProvinceID() == lookingFor) {
                     this.setPath(from, lookingFor, inPath.get(i), lookingFor, from);
                     this.clearWas(was);
                     return true;
                  }

                  nIN.add(Game.getProvince(Game.getProvince(in.get(i)).getNeighboringProvinces(jxx)).getProvinceID());
                  List<Integer> tPL = new ArrayList<>(inPath.get(i));
                  tPL.add(Game.getProvince(Game.getProvince(in.get(i)).getNeighboringProvinces(jxx)).getProvinceID());
                  nINPath.add(tPL);
                  this.setWas(Game.getProvince(Game.getProvince(in.get(i)).getNeighboringProvinces(jxx)).getProvinceID(), true);
                  was.add(Game.getProvince(Game.getProvince(in.get(i)).getNeighboringProvinces(jxx)).getProvinceID());
               }
            }

            if (!landOnly && !Game.getProvince(in.get(i)).getSeaProvince()) {
               for (int jxxx = Game.getProvince(in.get(i)).getNeighboringSeaProvincesSize() - 1; jxxx >= 0; jxxx--) {
                  if (!this.getWas(Game.getProvince(Game.getProvince(in.get(i)).getNeighboringSeaProvinces(jxxx)).getProvinceID())) {
                     if (Game.getProvince(Game.getProvince(in.get(i)).getNeighboringSeaProvinces(jxxx)).getProvinceID() == lookingFor) {
                        this.setPath(from, lookingFor, inPath.get(i), lookingFor, from);
                        this.clearWas(was);
                        return true;
                     }

                     nIN.add(Game.getProvince(Game.getProvince(in.get(i)).getNeighboringSeaProvinces(jxxx)).getProvinceID());
                     List<Integer> tPL = new ArrayList<>(inPath.get(i));
                     tPL.add(Game.getProvince(Game.getProvince(in.get(i)).getNeighboringSeaProvinces(jxxx)).getProvinceID());
                     nINPath.add(tPL);
                     this.setWas(Game.getProvince(Game.getProvince(in.get(i)).getNeighboringSeaProvinces(jxxx)).getProvinceID(), true);
                     was.add(Game.getProvince(Game.getProvince(in.get(i)).getNeighboringSeaProvinces(jxxx)).getProvinceID());
                  }
               }
            }
         }
      }

      if (nIN.isEmpty()) {
         return false;
      } else {
         try {
            return this.buildPath(civID, was, nIN, nINPath, from, lookingFor, !forDirection, landOnly);
         } catch (StackOverflowError var14) {
            this.clearWas(was);
            return false;
         }
      }
   }

   protected final void clearWas(List<Integer> was) {
      for (int i = was.size() - 1; i >= 0; i--) {
         this.setWas(was.get(i), true);
      }
   }

   protected final void setPath(int p1, int p2, List<Integer> lPath, int toProvinceID, int fromProvinceID) {
      this.lRoute.add(fromProvinceID);

      for (int i = 0; i < lPath.size(); i++) {
         this.lRoute.add(lPath.get(i));
      }

      if (toProvinceID != this.lRoute.get(this.lRoute.size() - 1)) {
         this.lRoute.add(toProvinceID);
      }

      this.iRouteSize = this.lRoute.size();
   }

   public static final boolean isFriendlyProvince(int civID, int toProvinceID) {
      return GameValues.move.ENABLE_MOVE_UNITS_TO_ANY_PROVINCE
         || Game.getProvince(toProvinceID).getCivID() == civID
         || Game.getProvince(toProvinceID).getCivID() == 0
         || Game.getProvince(toProvinceID).getSeaProvince()
         || Game.getCiv(civID).diplomacy.haveAlliance(Game.getProvince(toProvinceID).getCivID())
         || Game.getCiv(civID).areInAllianceSpecial(Game.getProvince(toProvinceID).getCivID())
         || Game.getCiv(civID).getPuppetOfCivID() == Game.getProvince(toProvinceID).getCivID()
         || Game.getCiv(Game.getProvince(toProvinceID).getCivID()).getPuppetOfCivID() == civID
         || Game.getCiv(Game.getProvince(toProvinceID).getCivID()).getPuppetOfCivID() == Game.getCiv(civID).getPuppetOfCivID()
         || Game.getCiv(civID).diplomacy.militaryAccess.containsKey(Game.getProvince(toProvinceID).getCivID())
         || civID < 0;
   }

   public static final boolean isFriendlyProvince_OrAtWAr(int civID, int nProvinceID) {
      return isFriendlyProvince(civID, nProvinceID) || DiplomacyManager.isAtWar(civID, Game.getProvince(nProvinceID).getCivID());
   }

   public static boolean canBeUsedInPath(int nCivID, int nProvinceID, boolean inRetreat, int fromProvince) {
      return Game.getProvince(nProvinceID).getWasteland() >= 0
         ? false
         : inRetreat
            || isFriendlyProvince_OrAtWAr(nCivID, nProvinceID)
            || Game.getProvince(nProvinceID).getCivID() == Game.getProvince(fromProvince).getCivID();
   }

   public void update() {
      this.littleAnimationMainLine.update();
      this.littleAnimationMovingArmy.update();

      try {
         int armyID = Game.getProvince(this.lRoute.get(0)).getArmyKeyID(this.key);
         if (armyID < 0) {
            return;
         }

         Province provinceA = Game.getProvince(this.lRoute.get(0));
         Province provinceB = Game.getProvince(this.lRoute.get(1));
         if ((provinceA.getBelowZero() || provinceB.getBelowZero()) && (!provinceA.getBelowZero() || !provinceB.getBelowZero())) {
            if (provinceA.getBelowZero()) {
               if (provinceB.iCenterShiftX > Game.mapBG.getWidth() / 2) {
                  provinceA.getArmy(armyID).iShiftX_Scaled = (int)(
                     (provinceB.iCenterShiftX - Game.mapBG.getWidth() - provinceA.iCenterShiftX)
                        * (
                           this.doneMovementProgressWidth
                              + (this.currentMovementProgressWidth - this.doneMovementProgressWidth) * this.fCurrentMovingPercentage
                        )
                        / this.iWidth.get(0).intValue()
                  );
               } else {
                  provinceA.getArmy(armyID).iShiftX_Scaled = (int)(
                     (provinceB.iCenterShiftX - provinceA.iCenterShiftX)
                        * (
                           this.doneMovementProgressWidth
                              + (this.currentMovementProgressWidth - this.doneMovementProgressWidth) * this.fCurrentMovingPercentage
                        )
                        / this.iWidth.get(0).intValue()
                  );
               }
            } else if (provinceA.iCenterShiftX > Game.mapBG.getWidth() / 2) {
               provinceA.getArmy(armyID).iShiftX_Scaled = (int)(
                  (provinceB.iCenterShiftX + Game.mapBG.getWidth() - provinceA.iCenterShiftX)
                     * (this.doneMovementProgressWidth + (this.currentMovementProgressWidth - this.doneMovementProgressWidth) * this.fCurrentMovingPercentage)
                     / this.iWidth.get(0).intValue()
               );
            } else {
               provinceA.getArmy(armyID).iShiftX_Scaled = (int)(
                  (provinceB.iCenterShiftX - provinceA.iCenterShiftX)
                     * (this.doneMovementProgressWidth + (this.currentMovementProgressWidth - this.doneMovementProgressWidth) * this.fCurrentMovingPercentage)
                     / this.iWidth.get(0).intValue()
               );
            }
         } else {
            provinceA.getArmy(armyID).iShiftX_Scaled = (int)(
               (provinceB.iCenterShiftX - provinceA.iCenterShiftX)
                  * (this.doneMovementProgressWidth + (this.currentMovementProgressWidth - this.doneMovementProgressWidth) * this.fCurrentMovingPercentage)
                  / this.iWidth.get(0).intValue()
            );
         }

         provinceA.getArmy(armyID).iShiftY_Scaled = (int)(
               (provinceB.iCenterShiftY - provinceA.iCenterShiftY)
                  * (this.doneMovementProgressWidth + (this.currentMovementProgressWidth - this.doneMovementProgressWidth) * this.fCurrentMovingPercentage)
                  / this.iWidth.get(0).intValue()
            )
            + (int)(this.extraArmyY / Game.mapScale.getCurrentScale());
      } catch (Exception var4) {
      }
   }

   public void draw(SpriteBatch oSB, float nScale) {
      try {
         Array<Vector2> nPath = new Array<>();
         if (this.isBelowZero) {
            if (Game.getProvince(this.lRoute.get(0)).getDrawProvince()) {
               for (int j = 0; j < (int)((this.iPrecision - 2) * this.fMovingPercentage); j++) {
                  nPath.add(
                     new Vector2(
                        (this.vPoints[j].x + Game.getProvince(this.lRoute.get(0)).getTranslateProvincePosX() + Game.mapBG.getWidth()) * nScale,
                        (this.vPoints[j].y - Game.mapCoords.getPosY()) * nScale
                     )
                  );
               }
            } else {
               for (int j = 0; j < (int)(this.iPrecision * this.fMovingPercentage); j++) {
                  nPath.add(
                     new Vector2(
                        (this.vPoints[j].x + Game.mapCoords.getPosX() + Game.mapBG.getWidth()) * nScale,
                        (this.vPoints[j].y - Game.mapCoords.getPosY()) * nScale
                     )
                  );
               }
            }

            Renderer.shapeDrawer.setColor(new Color(this.colorLine.r, this.colorLine.g, this.colorLine.b, 0.4F));
            Renderer.shapeDrawer.path(nPath, 3.0F * (0.25F + 0.75F * this.fMovingPercentage), JoinType.SMOOTH, true);
            nPath.clear();
            if (Game.getProvince(this.lRoute.get(0)).getDrawProvince()) {
               for (int j = 0; j < (int)((this.iPrecision - 2) * this.fMovingPercentage); j++) {
                  nPath.add(
                     new Vector2(
                        (this.vPoints[j].x + Game.getProvince(this.lRoute.get(0)).getTranslateProvincePosX()) * nScale,
                        (this.vPoints[j].y - Game.mapCoords.getPosY()) * nScale
                     )
                  );
               }
            } else {
               for (int j = 0; j < (int)(this.iPrecision * this.fMovingPercentage); j++) {
                  nPath.add(new Vector2((this.vPoints[j].x + Game.mapCoords.getPosX()) * nScale, (this.vPoints[j].y - Game.mapCoords.getPosY()) * nScale));
               }
            }

            Renderer.shapeDrawer.setColor(new Color(this.colorLine.r, this.colorLine.g, this.colorLine.b, 0.4F));
            Renderer.shapeDrawer.path(nPath, 3.0F * (0.25F + 0.75F * this.fMovingPercentage), JoinType.SMOOTH, true);
         } else {
            if (Game.getProvince(this.lRoute.get(0)).getDrawProvince()) {
               for (int j = 0; j < (int)((this.iPrecision - 2) * this.fMovingPercentage); j++) {
                  nPath.add(
                     new Vector2(
                        (this.vPoints[j].x + Game.getProvince(this.lRoute.get(0)).getTranslateProvincePosX()) * nScale,
                        (this.vPoints[j].y - Game.mapCoords.getPosY()) * nScale
                     )
                  );
               }
            } else {
               for (int j = 0; j < (int)(this.iPrecision * this.fMovingPercentage); j++) {
                  nPath.add(new Vector2((this.vPoints[j].x + Game.mapCoords.getPosX()) * nScale, (this.vPoints[j].y - Game.mapCoords.getPosY()) * nScale));
               }
            }

            Renderer.shapeDrawer.setColor(new Color(this.colorLine.r, this.colorLine.g, this.colorLine.b, 0.4F));
            Renderer.shapeDrawer.path(nPath, 3.0F * (0.25F + 0.75F * this.fMovingPercentage), JoinType.SMOOTH, true);
         }
      } catch (Exception var5) {
      }
   }

   public void draw_Ally(SpriteBatch oSB, float nScale) {
      try {
         Array<Vector2> nPath = new Array<>();
         if (this.isBelowZero) {
            if (Game.getProvince(this.lRoute.get(0)).getDrawProvince()) {
               for (int j = 0; j < (int)((this.iPrecision - 2) * this.fMovingPercentage); j++) {
                  nPath.add(
                     new Vector2(
                        (this.vPoints[j].x + Game.getProvince(this.lRoute.get(0)).getTranslateProvincePosX() + Game.mapBG.getWidth()) * nScale,
                        (this.vPoints[j].y - Game.mapCoords.getPosY()) * nScale
                     )
                  );
               }
            } else {
               for (int j = 0; j < (int)(this.iPrecision * this.fMovingPercentage); j++) {
                  nPath.add(
                     new Vector2(
                        (this.vPoints[j].x + Game.mapCoords.getPosX() + Game.mapBG.getWidth()) * nScale,
                        (this.vPoints[j].y - Game.mapCoords.getPosY()) * nScale
                     )
                  );
               }
            }

            Renderer.shapeDrawer.setColor(new Color(this.colorLine.r, this.colorLine.g, this.colorLine.b, 0.4F));
            Renderer.shapeDrawer.path(nPath, 2.0F * (0.25F + 0.75F * this.fMovingPercentage), JoinType.SMOOTH, true);
            nPath.clear();
            if (Game.getProvince(this.lRoute.get(0)).getDrawProvince()) {
               for (int j = 0; j < (int)((this.iPrecision - 2) * this.fMovingPercentage); j++) {
                  nPath.add(
                     new Vector2(
                        (this.vPoints[j].x + Game.getProvince(this.lRoute.get(0)).getTranslateProvincePosX()) * nScale,
                        (this.vPoints[j].y - Game.mapCoords.getPosY()) * nScale
                     )
                  );
               }
            } else {
               for (int j = 0; j < (int)(this.iPrecision * this.fMovingPercentage); j++) {
                  nPath.add(new Vector2((this.vPoints[j].x + Game.mapCoords.getPosX()) * nScale, (this.vPoints[j].y - Game.mapCoords.getPosY()) * nScale));
               }
            }

            Renderer.shapeDrawer.setColor(new Color(this.colorLine.r, this.colorLine.g, this.colorLine.b, 0.4F));
            Renderer.shapeDrawer.path(nPath, 2.0F * (0.25F + 0.75F * this.fMovingPercentage), JoinType.SMOOTH, true);
         } else {
            if (Game.getProvince(this.lRoute.get(0)).getDrawProvince()) {
               for (int j = 0; j < (int)((this.iPrecision - 2) * this.fMovingPercentage); j++) {
                  nPath.add(
                     new Vector2(
                        (this.vPoints[j].x + Game.getProvince(this.lRoute.get(0)).getTranslateProvincePosX()) * nScale,
                        (this.vPoints[j].y - Game.mapCoords.getPosY()) * nScale
                     )
                  );
               }
            } else {
               for (int j = 0; j < (int)(this.iPrecision * this.fMovingPercentage); j++) {
                  nPath.add(new Vector2((this.vPoints[j].x + Game.mapCoords.getPosX()) * nScale, (this.vPoints[j].y - Game.mapCoords.getPosY()) * nScale));
               }
            }

            Renderer.shapeDrawer.setColor(new Color(this.colorLine.r, this.colorLine.g, this.colorLine.b, 0.4F));
            Renderer.shapeDrawer.path(nPath, 2.0F * (0.25F + 0.75F * this.fMovingPercentage), JoinType.SMOOTH, true);
         }
      } catch (Exception var5) {
      }
   }

   public final void updateToNextProvince(int civID) {
      this.lRoute.remove(0);
      this.iRouteSize = this.lRoute.size();
      this.buildMoveUnitsLine(false, civID);
   }

   public final void buildMoveUnitsLine(boolean updateAnimation, int civID) {
      this.iWidth.clear();
      this.isBelowZero = false;
      this.buildWidth();
      if (updateAnimation) {
         this.lMovingTime = CFG.currentTimeMillis;
         this.fMovingPercentage = 0.01F;
         this.littleAnimationMainLine = new MoveUnits.LittleAnimation() {
            @Override
            public void update() {
               MoveUnits.this.fMovingPercentage = MoveUnits.this.fMovingPercentage
                  + (float)(CFG.currentTimeMillis - MoveUnits.this.lMovingTime) / GameValues.inGame.MOVE_UNITS_ANIMATION_DURATION;
               MoveUnits.this.lMovingTime = CFG.currentTimeMillis;
               if (MoveUnits.this.fMovingPercentage >= 1.0F) {
                  MoveUnits.this.fMovingPercentage = 1.0F;
                  MoveUnits.this.littleAnimationMainLine = new MoveUnits.LittleAnimation() {
                     @Override
                     public void update() {
                     }
                  };
               }
            }
         };
      }

      this.updateLittleAnimationMovingArmy();
      this.buildLine(civID);
   }

   public final void buildLine(int civID) {
      if (civID == Game.player.iCivID || Game.getCiv(civID).isPlayerAlly) {
         this.iPrecision = 16 * this.iRouteSize;
         this.vPoints = new Vector2[this.iPrecision];
         Vector2[] dataSet = new Vector2[this.iRouteSize + 2];

         for (int i = 0; i < this.iRouteSize; i++) {
            if (Game.getProvince(this.lRoute.get(i)).getBelowZero()) {
               this.isBelowZero = true;
               break;
            }
         }

         if (this.isBelowZero) {
            for (int ix = 0; ix < this.iRouteSize; ix++) {
               dataSet[ix + 1] = new Vector2(
                  Game.getProvince(this.lRoute.get(ix)).iCenterShiftX
                     + (
                        !Game.getProvince(this.lRoute.get(ix)).getBelowZero()
                              && Game.getProvince(this.lRoute.get(ix)).iCenterShiftX > Game.mapBG.getWidth() / 2
                           ? -Game.mapBG.getWidth()
                           : 0
                     ),
                  -Game.getProvince(this.lRoute.get(ix)).iCenterShiftY
               );
            }

            dataSet[0] = new Vector2(
               Game.getProvince(this.lRoute.get(0)).iCenterShiftX
                  + (
                     !Game.getProvince(this.lRoute.get(0)).getBelowZero() && Game.getProvince(this.lRoute.get(0)).iCenterShiftX > Game.mapBG.getWidth() / 2
                        ? -Game.mapBG.getWidth()
                        : 0
                  ),
               -Game.getProvince(this.lRoute.get(0)).iCenterShiftY
            );
            dataSet[this.iRouteSize + 1] = new Vector2(
               Game.getProvince(this.lRoute.get(this.iRouteSize - 1)).iCenterShiftX
                  + (
                     !Game.getProvince(this.lRoute.get(this.iRouteSize - 1)).getBelowZero()
                           && Game.getProvince(this.lRoute.get(this.iRouteSize - 1)).iCenterShiftX > Game.mapBG.getWidth() / 2
                        ? -Game.mapBG.getWidth()
                        : 0
                  ),
               -Game.getProvince(this.lRoute.get(this.iRouteSize - 1)).iCenterShiftY
            );
         } else {
            for (int ix = 0; ix < this.iRouteSize; ix++) {
               dataSet[ix + 1] = new Vector2(Game.getProvince(this.lRoute.get(ix)).iCenterShiftX, -Game.getProvince(this.lRoute.get(ix)).iCenterShiftY);
            }

            dataSet[0] = new Vector2(Game.getProvince(this.lRoute.get(0)).iCenterShiftX, -Game.getProvince(this.lRoute.get(0)).iCenterShiftY);
            dataSet[this.iRouteSize + 1] = new Vector2(
               Game.getProvince(this.lRoute.get(this.iRouteSize - 1)).iCenterShiftX, -Game.getProvince(this.lRoute.get(this.iRouteSize - 1)).iCenterShiftY
            );
         }

         CatmullRomSpline<Vector2> oCatmull = new CatmullRomSpline<>(dataSet, false);

         for (int j = 0; j < this.iPrecision; j++) {
            this.vPoints[j] = new Vector2();
            oCatmull.valueAt(this.vPoints[j], j / (this.iPrecision - 1.0F));
         }

         dataSet = null;
      }
   }

   public final void buildWidth() {
      try {
         for (int i = 0; i < this.iRouteSize - 1; i++) {
            Province provinceA = Game.getProvince(this.lRoute.get(i));
            Province provinceB = Game.getProvince(this.lRoute.get(i + 1));
            this.iWidth
               .add(
                  Math.max(
                     1,
                     (int)Math.ceil(
                           Math.sqrt(
                              (provinceB.iCenterShiftX - provinceA.iCenterShiftX) * (provinceB.iCenterShiftX - provinceA.iCenterShiftX)
                                 + (provinceA.iCenterShiftY - provinceB.iCenterShiftY) * (provinceA.iCenterShiftY - provinceB.iCenterShiftY)
                           )
                        )
                        / Game.mapBG.iMapScale
                  )
               );
         }
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   public final void buildMoveUnitsLine_FromToTheSameProvince(boolean updateAnimation, int civID) {
      this.iWidth.clear();
      this.isBelowZero = false;
      this.buildWidth();
      if (updateAnimation) {
         this.lMovingTime = CFG.currentTimeMillis;
         this.fMovingPercentage = 0.01F;
         this.littleAnimationMainLine = new MoveUnits.LittleAnimation() {
            @Override
            public void update() {
               MoveUnits.this.fMovingPercentage = MoveUnits.this.fMovingPercentage
                  + (float)(CFG.currentTimeMillis - MoveUnits.this.lMovingTime) / GameValues.inGame.MOVE_UNITS_ANIMATION_DURATION;
               MoveUnits.this.lMovingTime = CFG.currentTimeMillis;
               if (MoveUnits.this.fMovingPercentage >= 1.0F) {
                  MoveUnits.this.fMovingPercentage = 1.0F;
                  MoveUnits.this.littleAnimationMainLine = new MoveUnits.LittleAnimation() {
                     @Override
                     public void update() {
                     }
                  };
               }
            }
         };
      }

      this.updateLittleAnimationMovingArmy();
      this.buildLine(civID);
   }

   public void updateLittleAnimationMovingArmy() {
      this.lCurrentMovingTime = CFG.currentTimeMillis;
      this.fCurrentMovingPercentage = 0.01F;
      this.littleAnimationMovingArmy = new MoveUnits.LittleAnimation() {
         @Override
         public void update() {
            MoveUnits.this.fCurrentMovingPercentage = MoveUnits.this.fCurrentMovingPercentage
               + (float)(CFG.currentTimeMillis - MoveUnits.this.lCurrentMovingTime) / Game.gameThread.playSpeedTIME;
            MoveUnits.this.lCurrentMovingTime = CFG.currentTimeMillis;
            if (MoveUnits.this.fCurrentMovingPercentage >= 1.0F) {
               MoveUnits.this.fCurrentMovingPercentage = 1.0F;
               MoveUnits.this.littleAnimationMovingArmy = new MoveUnits.LittleAnimation() {
                  @Override
                  public void update() {
                  }
               };
            }
         }
      };
   }

   public int getFromProvinceID() {
      return this.lRoute.get(0);
   }

   public int getToProvinceID() {
      return this.lRoute.get(1);
   }

   public int getToProvinceLastID() {
      return this.lRoute.get(this.iRouteSize - 1);
   }

   public final float getProgressPerc() {
      return this.currentMovementProgressWidth / this.iWidth.get(0).intValue();
   }

   public int getWidthTotal() {
      int out = 0;

      for (int i = this.iWidth.size() - 1; i >= 0; i--) {
         out += this.iWidth.get(i);
      }

      return out;
   }

   public boolean haveSeaProvince() {
      for (int i = 0; i < this.iRouteSize; i++) {
         if (Game.getProvince(this.lRoute.get(i)).getSeaProvince()) {
            return true;
         }
      }

      return false;
   }

   public boolean getWas(int nProvinceID) {
      return Game.getProvince(nProvinceID).was;
   }

   public void setWas(int nProvinceID, boolean nWas) {
      Game.getProvince(nProvinceID).was = nWas;
   }

   public interface LittleAnimation {
      void update();
   }

   public class Node {
      int provinceID;
      MoveUnits.Node parent;
      float gCost;
      float hCost;

      public Node(int provinceID, MoveUnits.Node parent, float gCost, float hCost) {
         this.provinceID = provinceID;
         this.parent = parent;
         this.gCost = gCost;
         this.hCost = hCost;
      }

      public float getFCost() {
         return this.gCost + this.hCost;
      }
   }
}
