package aoc.kingdoms.lukasz.map.army;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.GlyphLayout_Game;
import aoc.kingdoms.lukasz.jakowski.Renderer.Renderer;

public class ArmyDivision_Shadow {
   public String sArmy;
   public int iProvinceID;
   public int iArmyWidth;
   public int extraY = 0;

   public ArmyDivision_Shadow(String sArmy, int iProvinceID, int extraY) {
      this.sArmy = sArmy;
      this.iProvinceID = iProvinceID;
      this.extraY = extraY;
      this.updateArmyWidth_Just();
   }

   protected final void updateArmyWidth_Just() {
      try {
         GlyphLayout_Game glyphLayout = new GlyphLayout_Game();
         glyphLayout.setText(Renderer.fontArmy_GlyphLayout, this.sArmy);
         this.iArmyWidth = (int)glyphLayout.width;
      } catch (Exception var2) {
         CFG.exceptionStack(var2);
         this.iArmyWidth = CFG.PADDING;
      }
   }
}
