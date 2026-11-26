package aoc.kingdoms.lukasz.map.moveUnits.other;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.textures.ImageManager;
import aoc.kingdoms.lukasz.textures.Images;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class MoveUnits_OLD {
   protected List<Integer> lRoute = new ArrayList<>();
   protected int iRouteSize = 0;
   public String key;
   protected List<Integer> iWidth = new ArrayList<>();
   protected List<Integer> fAngle = new ArrayList<>();
   public long lMovingTime = 0L;
   public float fMovingPercentage = 0.0F;
   public int moveLineSrcX = 0;

   public MoveUnits_OLD(int nCivID, int iFromProvinceID, int iToProvinceID, String key) {
      this.key = key;
      this.buildRoute(nCivID, iFromProvinceID, iToProvinceID);
      this.buildMoveUnitsLine();
      int armyID = Game.getProvince(iFromProvinceID).getArmyKeyID(key);
      if (armyID >= 0) {
         Game.getProvince(iFromProvinceID).getArmy(armyID).iShiftX = Game.getProvince(iFromProvinceID).getArmy(armyID).defaultShiftX();
         Game.getProvince(iFromProvinceID).getArmy(armyID).iShiftY = Game.getProvince(iFromProvinceID).getArmy(armyID).defaultShiftY();
      }
   }

   protected boolean buildRoute(int nCivID, int fromProvinceID, int toProvinceID) {
      this.lRoute.clear();
      if (fromProvinceID < 0 || toProvinceID < 0 || Game.getProvince(toProvinceID).getWasteland() >= 0) {
         return false;
      } else if (!Game.getProvince(fromProvinceID).getSeaProvince()
         && Game.getProvince(fromProvinceID).getNeighboringProvincesSize() == 0
         && Game.getProvince(fromProvinceID).getLevelOfPort() <= 0) {
         return false;
      } else {
         List<Integer> was = new ArrayList<>();
         was.add(fromProvinceID);

         for (int i = 0; i < Game.getProvincesSize(); i++) {
            Game.getProvince(i).was = false;
         }

         Game.getProvince(fromProvinceID).was = true;
         List<Integer> in = new ArrayList<>();
         List<List<Integer>> inPath = new ArrayList<>();

         for (int i = 0; i < Game.getProvince(fromProvinceID).getNeighboringProvincesSize(); i++) {
            if (canBeUsedInPath(nCivID, Game.getProvince(fromProvinceID).getNeighboringProvinces(i), isFriendlyProvince(nCivID, toProvinceID), toProvinceID)) {
               in.add(Game.getProvince(Game.getProvince(fromProvinceID).getNeighboringProvinces(i)).getProvinceID());
               List<Integer> tP = new ArrayList<>();
               tP.add(Game.getProvince(Game.getProvince(fromProvinceID).getNeighboringProvinces(i)).getProvinceID());
               inPath.add(tP);
               was.add(Game.getProvince(Game.getProvince(fromProvinceID).getNeighboringProvinces(i)).getProvinceID());
               Game.getProvince(Game.getProvince(Game.getProvince(fromProvinceID).getNeighboringProvinces(i)).getProvinceID()).was = true;
            }
         }

         if (!Game.getProvince(fromProvinceID).getSeaProvince() && Game.getProvince(fromProvinceID).getLevelOfPort() > 0) {
            for (int ix = 0; ix < Game.getProvince(fromProvinceID).getNeighboringSeaProvincesSize(); ix++) {
               in.add(Game.getProvince(Game.getProvince(fromProvinceID).getNeighboringSeaProvinces(ix)).getProvinceID());
               List<Integer> tP = new ArrayList<>();
               tP.add(Game.getProvince(Game.getProvince(fromProvinceID).getNeighboringSeaProvinces(ix)).getProvinceID());
               inPath.add(tP);
               was.add(Game.getProvince(Game.getProvince(fromProvinceID).getNeighboringSeaProvinces(ix)).getProvinceID());
               Game.getProvince(Game.getProvince(Game.getProvince(fromProvinceID).getNeighboringSeaProvinces(ix)).getProvinceID()).was = true;
            }
         }

         this.buildPath(nCivID, was, in, inPath, fromProvinceID, toProvinceID);
         return true;
      }
   }

   public static final boolean isFriendlyProvince(int nCivID, int toProvinceID) {
      return true;
   }

   protected static boolean canBeUsedInPath(int nCivID, int nProvinceID, boolean moveToFriendlyProvince, int toProvinceID) {
      return Game.getProvince(nProvinceID).getWasteland() < 0;
   }

   protected boolean buildPath(int nCivID, List<Integer> was, List<Integer> in, List<List<Integer>> inPath, int from, int lookingFor) {
      List<Integer> nIN = new ArrayList<>();
      List<List<Integer>> nINPath = new ArrayList<>();

      for (int i = 0; i < in.size(); i++) {
         if (Game.getProvince(in.get(i)).getProvinceID() == lookingFor) {
            this.setPath(from, lookingFor, inPath.get(i), lookingFor, from);
            this.clearWas(was);
            return true;
         }
      }

      for (int ix = 0; ix < in.size(); ix++) {
         for (int j = 0; j < Game.getProvince(in.get(ix)).getNeighboringProvincesSize(); j++) {
            if (canBeUsedInPath(
                  nCivID,
                  Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringProvinces(j)).getProvinceID(),
                  isFriendlyProvince(nCivID, lookingFor),
                  lookingFor
               )
               && !Game.getProvince(Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringProvinces(j)).getProvinceID()).was) {
               if (Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringProvinces(j)).getProvinceID() == lookingFor) {
                  this.setPath(from, lookingFor, inPath.get(ix), lookingFor, from);
                  this.clearWas(was);
                  return true;
               }

               nIN.add(Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringProvinces(j)).getProvinceID());
               List<Integer> tPL = new ArrayList<>();

               for (int u = 0; u < inPath.get(ix).size(); u++) {
                  tPL.add(inPath.get(ix).get(u));
               }

               tPL.add(Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringProvinces(j)).getProvinceID());
               nINPath.add(tPL);
               Game.getProvince(Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringProvinces(j)).getProvinceID()).was = true;
               was.add(Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringProvinces(j)).getProvinceID());
            }
         }

         if (!Game.getProvince(in.get(ix)).getSeaProvince() && Game.getProvince(in.get(ix)).getLevelOfPort() > 0) {
            for (int jx = 0; jx < Game.getProvince(in.get(ix)).getNeighboringSeaProvincesSize(); jx++) {
               if (!Game.getProvince(Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringSeaProvinces(jx)).getProvinceID()).was) {
                  if (Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringSeaProvinces(jx)).getProvinceID() == lookingFor) {
                     this.setPath(from, lookingFor, inPath.get(ix), lookingFor, from);
                     this.clearWas(was);
                     return true;
                  }

                  nIN.add(Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringSeaProvinces(jx)).getProvinceID());
                  List<Integer> tPL = new ArrayList<>();

                  for (int u = 0; u < inPath.get(ix).size(); u++) {
                     tPL.add(inPath.get(ix).get(u));
                  }

                  tPL.add(Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringSeaProvinces(jx)).getProvinceID());
                  nINPath.add(tPL);
                  Game.getProvince(Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringSeaProvinces(jx)).getProvinceID()).was = true;
                  was.add(Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringSeaProvinces(jx)).getProvinceID());
               }
            }
         }
      }

      try {
         return this.buildPath(nCivID, was, nIN, nINPath, from, lookingFor);
      } catch (StackOverflowError var13) {
         this.clearWas(was);
         return false;
      }
   }

   protected final void clearWas(List<Integer> was) {
      for (int i = was.size() - 1; i >= 0; i--) {
         Game.getProvince(was.get(i)).was = false;
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

   public void draw(SpriteBatch oSB, float nScale) {
      this.fMovingPercentage = this.fMovingPercentage + (float)(CFG.currentTimeMillis - this.lMovingTime) / 15000.0F;
      this.lMovingTime = CFG.currentTimeMillis;
      if (this.fMovingPercentage >= 1.0F) {
         this.fMovingPercentage = 1.0F;
      }

      int armyID = Game.getProvince(this.lRoute.get(0)).getArmyKeyID(this.key);
      if (armyID >= 0) {
         Game.getProvince(this.lRoute.get(0)).getArmy(armyID).iShiftX_Scaled = (int)(
            (Game.getProvince(this.lRoute.get(1)).getCenterX() - Game.getProvince(this.lRoute.get(0)).getCenterX()) * this.fMovingPercentage
         );
         Game.getProvince(this.lRoute.get(0)).getArmy(armyID).iShiftY_Scaled = (int)(
            (Game.getProvince(this.lRoute.get(1)).getCenterY() - Game.getProvince(this.lRoute.get(0)).getCenterY()) * this.fMovingPercentage
         );
      }

      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.2F));
      ImageManager.getImage(Images.moveLine)
         .draw2(
            oSB,
            CFG.rotateXMoveUnits[this.fAngle.get(0)]
               + (int)((Game.getProvince(this.lRoute.get(0)).iCenterShiftX + Game.getProvince(this.lRoute.get(0)).getTranslateProvincePosX()) * nScale),
            CFG.rotateYMoveUnits[this.fAngle.get(0)] + (int)((Game.getProvince(this.lRoute.get(0)).iCenterShiftY + Game.mapCoords.getPosY()) * nScale),
            (int)(this.iWidth.get(0).intValue() * nScale),
            ImageManager.getImage(Images.moveLine).getHeight(),
            this.fAngle.get(0).intValue(),
            true,
            false
         );
      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.5F));
      int tempWidth = (int)(this.iWidth.get(0).intValue() * this.fMovingPercentage * nScale);
      ImageManager.getImage(Images.moveLine)
         .draw2(
            oSB,
            CFG.rotateXMoveUnits[this.fAngle.get(0)]
               + (int)((Game.getProvince(this.lRoute.get(0)).iCenterShiftX + Game.getProvince(this.lRoute.get(0)).getTranslateProvincePosX()) * nScale),
            CFG.rotateYMoveUnits[this.fAngle.get(0)] + (int)((Game.getProvince(this.lRoute.get(0)).iCenterShiftY + Game.mapCoords.getPosY()) * nScale),
            tempWidth,
            ImageManager.getImage(Images.moveLine).getHeight(),
            this.fAngle.get(0).intValue(),
            true,
            false,
            (int)(this.iWidth.get(0).intValue() * nScale - tempWidth)
         );
      oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.2F));

      for (int i = 1; i < this.iRouteSize - 1; i++) {
         Images.line_32_off1
            .draw2(
               oSB,
               (int)((Game.getProvince(this.lRoute.get(i)).iCenterShiftX + Game.getProvince(this.lRoute.get(i)).getTranslateProvincePosX()) * nScale),
               (int)((Game.getProvince(this.lRoute.get(i)).iCenterShiftY + Game.mapCoords.getPosY()) * nScale),
               (int)(this.iWidth.get(i).intValue() * nScale),
               Images.line_32_off1.getHeight(),
               this.fAngle.get(i).intValue(),
               true,
               false,
               ++this.moveLineSrcX
            );
      }

      oSB.setColor(Color.WHITE);
   }

   private final void buildMoveUnitsLine() {
      try {
         for (int i = 0; i < this.iRouteSize - 1; i++) {
            this.iWidth
               .add(
                  (int)Math.ceil(
                     Math.sqrt(
                        (
                                 Game.getProvince(this.lRoute.get(i + 1)).iCenterShiftX
                                    + Game.getProvince(this.lRoute.get(i + 1)).getTranslateProvincePosX()
                                    - (Game.getProvince(this.lRoute.get(i)).iCenterShiftX + Game.getProvince(this.lRoute.get(i)).getTranslateProvincePosX())
                              )
                              * (
                                 Game.getProvince(this.lRoute.get(i + 1)).iCenterShiftX
                                    + Game.getProvince(this.lRoute.get(i + 1)).getTranslateProvincePosX()
                                    - (Game.getProvince(this.lRoute.get(i)).iCenterShiftX + Game.getProvince(this.lRoute.get(i)).getTranslateProvincePosX())
                              )
                           + (Game.getProvince(this.lRoute.get(i)).iCenterShiftY - Game.getProvince(this.lRoute.get(i + 1)).iCenterShiftY)
                              * (Game.getProvince(this.lRoute.get(i)).iCenterShiftY - Game.getProvince(this.lRoute.get(i + 1)).iCenterShiftY)
                     )
                  )
               );
            this.fAngle
               .add(
                  (int)(
                     Math.atan2(
                           Game.getProvince(this.lRoute.get(i)).iCenterShiftY - Game.getProvince(this.lRoute.get(i + 1)).iCenterShiftY,
                           -(Game.getProvince(this.lRoute.get(i)).iCenterShiftX + Game.getProvince(this.lRoute.get(i)).getTranslateProvincePosX())
                              + Game.getProvince(this.lRoute.get(i + 1)).iCenterShiftX
                              + Game.getProvince(this.lRoute.get(i + 1)).getTranslateProvincePosX()
                        )
                        * 180.0
                        / Math.PI
                  )
               );
            if (this.fAngle.get(i) < 0) {
               this.fAngle.set(i, this.fAngle.get(i) + 360);
            }

            this.fAngle.set(i, this.fAngle.get(i) % 360);
         }

         this.lMovingTime = CFG.currentTimeMillis;
         this.fMovingPercentage = 0.15F;
      } catch (IndexOutOfBoundsException var2) {
         CFG.exceptionStack(var2);
      } catch (NullPointerException var3) {
         CFG.exceptionStack(var3);
      }
   }
}
