package aoc.kingdoms.lukasz.events.outcome;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.textures.Images;
import java.util.List;

public class EventOutcome_AnnexProvincesFromCiv extends EventOutcome {
   public String sFromCivTAG;
   public List<Integer> lProvinces;

   public EventOutcome_AnnexProvincesFromCiv(String sFromCivTAG, List<Integer> nProvinces) {
      this.sFromCivTAG = sFromCivTAG;
      this.lProvinces = nProvinces;

      for (int i = this.lProvinces.size() - 1; i >= 0; i--) {
         if (this.lProvinces.get(i) < 0 || this.lProvinces.get(i) >= Game.getProvincesSize()) {
            this.lProvinces.remove(i);
         }
      }
   }

   @Override
   public void updateCiv(int iCivID, int bonus_duration) {
      try {
         int annexCivID = Game.getCivID(this.sFromCivTAG);

         for (int i = this.lProvinces.size() - 1; i >= 0; i--) {
            if (this.lProvinces.get(i) >= 0
               && this.lProvinces.get(i) < Game.getProvincesSize()
               && Game.getProvince(this.lProvinces.get(i)).getCivID() != iCivID
               && Game.getProvince(this.lProvinces.get(i)).getCivID() == annexCivID) {
               Game.getProvince(this.lProvinces.get(i)).setCivID(iCivID);
            }
         }

         Game.gameThread.addCivUpdateTotalIncomePerMonth(iCivID);
         Game.gameThread.addCivUpdateTotalIncomePerMonth(annexCivID);
      } catch (Exception var5) {
         CFG.exceptionStack(var5);
      }
   }

   @Override
   public String getStringLeft() {
      return Game.lang.get("Annexation") + ": ";
   }

   @Override
   public String getStringRight() {
      String out = "";
      int annexCivID = Game.getCivID(this.sFromCivTAG);

      for (int i = 0; i < this.lProvinces.size(); i++) {
         if (this.lProvinces.get(i) >= 0
            && this.lProvinces.get(i) < Game.getProvincesSize()
            && Game.getProvince(this.lProvinces.get(i)).getCivID() == annexCivID) {
            out = out + Game.getProvince(this.lProvinces.get(i)).getProvinceName() + (i == this.lProvinces.size() - 1 ? "" : ", ");
         }
      }

      return out.length() > 0 ? out : Game.lang.get("None");
   }

   @Override
   public int getImage() {
      return Images.provinces;
   }
}
