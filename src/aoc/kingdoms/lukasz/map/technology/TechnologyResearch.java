package aoc.kingdoms.lukasz.map.technology;

public class TechnologyResearch {
   public int iTechID;
   public float fProgress = 0.0F;

   public TechnologyResearch(int iTechID) {
      this.iTechID = iTechID;
   }

   public TechnologyResearch(int iTechID, float fProgress) {
      this.iTechID = iTechID;
      this.fProgress = fProgress;
   }
}
