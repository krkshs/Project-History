package aoc.kingdoms.lukasz.map.plague;

public class ProvincePlague {
   public int id = 0;
   public int sinceTurnID = 0;
   public int deaths = 0;
   public float turnsLeft = 0.0F;

   public ProvincePlague() {
   }

   public ProvincePlague(int iPlagueID_InGame, int iSinceTurnID, float iDurationTurnsLeft, int iDeaths) {
      this.id = iPlagueID_InGame;
      this.sinceTurnID = iSinceTurnID;
      this.turnsLeft = iDurationTurnsLeft;
      this.deaths = iDeaths;
   }
}
