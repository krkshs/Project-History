package aoc.kingdoms.lukasz.events.outcome;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.textures.Images;
import java.util.List;

public class EventOutcome_AnnexProvinces extends EventOutcome {
   public List<Integer> lProvinces;

   public EventOutcome_AnnexProvinces(List<Integer> nProvinces) {
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
         for (int i = this.lProvinces.size() - 1; i >= 0; i--) {
            if (this.lProvinces.get(i) >= 0
               && this.lProvinces.get(i) < Game.getProvincesSize()
               && Game.getProvince(this.lProvinces.get(i)).getCivID() != iCivID) {
               Game.getProvince(this.lProvinces.get(i)).setCivID(iCivID);
            }
         }

         Game.gameThread.addCivUpdateTotalIncomePerMonth(iCivID);
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   @Override
   public String getStringLeft() {
      return Game.lang.get("Annexation") + ": ";
   }

   @Override
   public String getStringRight() {
      String out = "";

      for (int i = 0; i < this.lProvinces.size(); i++) {
         if (this.lProvinces.get(i) >= 0 && this.lProvinces.get(i) < Game.getProvincesSize()) {
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
