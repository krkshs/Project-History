package aoc.kingdoms.lukasz.map.moveUnits.other;

import aoc.kingdoms.lukasz.jakowski.Game;
import java.util.ArrayList;
import java.util.List;

public class MoveUnits_AI {
   public List<Integer> lRoute = new ArrayList<>();
   public int iRouteSize = 0;

   public MoveUnits_AI(int nCivID, int iFromProvinceID, int iToProvinceID) {
      this.buildRoute(nCivID, iFromProvinceID, iToProvinceID);
   }

   protected boolean buildRoute(int nCivID, int fromProvinceID, int toProvinceID) {
      this.lRoute.clear();
      if (fromProvinceID >= 0 && toProvinceID >= 0 && Game.getProvince(toProvinceID).getWasteland() < 0) {
         List<Integer> was = new ArrayList<>();
         was.add(fromProvinceID);

         for (int i = 0; i < Game.getProvincesSize(); i++) {
            Game.getProvince(i).wasAI = false;
         }

         Game.getProvince(fromProvinceID).wasAI = true;
         List<Integer> in = new ArrayList<>();
         List<List<Integer>> inPath = new ArrayList<>();

         for (int i = 0; i < Game.getProvince(fromProvinceID).getNeighboringProvincesSize(); i++) {
            if (this.canBeUsedInPath(nCivID, Game.getProvince(fromProvinceID).getNeighboringProvinces(i))) {
               in.add(Game.getProvince(Game.getProvince(fromProvinceID).getNeighboringProvinces(i)).getProvinceID());
               List<Integer> tP = new ArrayList<>();
               tP.add(Game.getProvince(Game.getProvince(fromProvinceID).getNeighboringProvinces(i)).getProvinceID());
               inPath.add(tP);
               was.add(Game.getProvince(Game.getProvince(fromProvinceID).getNeighboringProvinces(i)).getProvinceID());
               Game.getProvince(Game.getProvince(Game.getProvince(fromProvinceID).getNeighboringProvinces(i)).getProvinceID()).wasAI = true;
            }
         }

         if (!Game.getProvince(fromProvinceID).getSeaProvince()) {
            for (int ix = 0; ix < Game.getProvince(fromProvinceID).getNeighboringSeaProvincesSize(); ix++) {
               in.add(Game.getProvince(Game.getProvince(fromProvinceID).getNeighboringSeaProvinces(ix)).getProvinceID());
               List<Integer> tP = new ArrayList<>();
               tP.add(Game.getProvince(Game.getProvince(fromProvinceID).getNeighboringSeaProvinces(ix)).getProvinceID());
               inPath.add(tP);
               was.add(Game.getProvince(Game.getProvince(fromProvinceID).getNeighboringSeaProvinces(ix)).getProvinceID());
               Game.getProvince(Game.getProvince(Game.getProvince(fromProvinceID).getNeighboringSeaProvinces(ix)).getProvinceID()).wasAI = true;
            }
         }

         List<Integer> nIN = new ArrayList<>();
         List<List<Integer>> nINPath = new ArrayList<>();
         this.buildPath(nCivID, was, in, inPath, fromProvinceID, toProvinceID, true, nIN, nINPath);
         return true;
      } else {
         return false;
      }
   }

   public boolean isFriendlyProvince(int nCivID, int toProvinceID) {
      return Game.getProvince(toProvinceID).getCivID() == nCivID;
   }

   public boolean canBeUsedInPath(int nCivID, int nProvinceID) {
      return !Game.getCiv(Game.getProvince(nProvinceID).getCivID()).diplomacy.isRival(nCivID) && Game.getProvince(nProvinceID).getWasteland() < 0;
   }

   protected boolean buildPath(
      int nCivID,
      List<Integer> was,
      List<Integer> in,
      List<List<Integer>> inPath,
      int from,
      int lookingFor,
      boolean forDirection,
      List<Integer> nIN,
      List<List<Integer>> nINPath
   ) {
      nIN.clear();
      nINPath.clear();

      for (int i = 0; i < in.size(); i++) {
         if (Game.getProvince(in.get(i)).getProvinceID() == lookingFor) {
            this.setPath(from, lookingFor, inPath.get(i), lookingFor, from);
            this.clearWas(was);
            return true;
         }
      }

      if (forDirection) {
         for (int ix = 0; ix < in.size(); ix++) {
            for (int j = 0; j < Game.getProvince(in.get(ix)).getNeighboringProvincesSize(); j++) {
               if (this.canBeUsedInPath(nCivID, Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringProvinces(j)).getProvinceID())
                  && !Game.getProvince(Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringProvinces(j)).getProvinceID()).wasAI) {
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
                  Game.getProvince(Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringProvinces(j)).getProvinceID()).wasAI = true;
                  was.add(Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringProvinces(j)).getProvinceID());
               }
            }

            if (!Game.getProvince(in.get(ix)).getSeaProvince()) {
               for (int jx = 0; jx < Game.getProvince(in.get(ix)).getNeighboringSeaProvincesSize(); jx++) {
                  if (!Game.getProvince(Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringSeaProvinces(jx)).getProvinceID()).wasAI) {
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
                     Game.getProvince(Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringSeaProvinces(jx)).getProvinceID()).wasAI = true;
                     was.add(Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringSeaProvinces(jx)).getProvinceID());
                  }
               }
            }
         }
      } else {
         for (int ix = 0; ix < in.size(); ix++) {
            for (int jxx = Game.getProvince(in.get(ix)).getNeighboringProvincesSize() - 1; jxx >= 0; jxx--) {
               if (this.canBeUsedInPath(nCivID, Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringProvinces(jxx)).getProvinceID())
                  && !Game.getProvince(Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringProvinces(jxx)).getProvinceID()).wasAI) {
                  if (Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringProvinces(jxx)).getProvinceID() == lookingFor) {
                     this.setPath(from, lookingFor, inPath.get(ix), lookingFor, from);
                     this.clearWas(was);
                     return true;
                  }

                  nIN.add(Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringProvinces(jxx)).getProvinceID());
                  List<Integer> tPL = new ArrayList<>();

                  for (int u = 0; u < inPath.get(ix).size(); u++) {
                     tPL.add(inPath.get(ix).get(u));
                  }

                  tPL.add(Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringProvinces(jxx)).getProvinceID());
                  nINPath.add(tPL);
                  Game.getProvince(Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringProvinces(jxx)).getProvinceID()).wasAI = true;
                  was.add(Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringProvinces(jxx)).getProvinceID());
               }
            }

            if (!Game.getProvince(in.get(ix)).getSeaProvince()) {
               for (int jxxx = Game.getProvince(in.get(ix)).getNeighboringSeaProvincesSize() - 1; jxxx >= 0; jxxx--) {
                  if (!Game.getProvince(Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringSeaProvinces(jxxx)).getProvinceID()).wasAI) {
                     if (Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringSeaProvinces(jxxx)).getProvinceID() == lookingFor) {
                        this.setPath(from, lookingFor, inPath.get(ix), lookingFor, from);
                        this.clearWas(was);
                        return true;
                     }

                     nIN.add(Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringSeaProvinces(jxxx)).getProvinceID());
                     List<Integer> tPL = new ArrayList<>();

                     for (int u = 0; u < inPath.get(ix).size(); u++) {
                        tPL.add(inPath.get(ix).get(u));
                     }

                     tPL.add(Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringSeaProvinces(jxxx)).getProvinceID());
                     nINPath.add(tPL);
                     Game.getProvince(Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringSeaProvinces(jxxx)).getProvinceID()).wasAI = true;
                     was.add(Game.getProvince(Game.getProvince(in.get(ix)).getNeighboringSeaProvinces(jxxx)).getProvinceID());
                  }
               }
            }
         }
      }

      if (nIN.isEmpty()) {
         return false;
      } else {
         try {
            return this.buildPath(nCivID, was, nIN, nINPath, from, lookingFor, !forDirection, in, inPath);
         } catch (StackOverflowError var14) {
            this.clearWas(was);
            return false;
         }
      }
   }

   protected final void clearWas(List<Integer> was) {
      for (int i = was.size() - 1; i >= 0; i--) {
         Game.getProvince(was.get(i)).wasAI = false;
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

   public int getFromProvinceID() {
      return this.lRoute.get(0);
   }

   public int getToProvinceID() {
      return this.lRoute.get(1);
   }

   public int getToProvinceLastID() {
      return this.lRoute.get(this.iRouteSize - 1);
   }
}
