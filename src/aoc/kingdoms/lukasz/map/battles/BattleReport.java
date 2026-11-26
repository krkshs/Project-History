package aoc.kingdoms.lukasz.map.battles;

public class BattleReport {
   public String key;
   public BattleReport.CivReport civReportLeft;
   public BattleReport.CivReport civReportRight;
   public float fWarScore;
   public boolean leftSideWon;
   public boolean playerWon;
   public int iProvinceID;
   public int iTurnID;

   public BattleReport(
      String key,
      int iProvinceID,
      float fWarScore,
      boolean leftSideWon,
      boolean playerWon,
      int iTurnID,
      BattleReport.CivReport civReportLeft,
      BattleReport.CivReport civReportRight
   ) {
      this.key = key;
      this.iProvinceID = iProvinceID;
      this.fWarScore = fWarScore;
      this.leftSideWon = leftSideWon;
      this.playerWon = playerWon;
      this.civReportLeft = civReportLeft;
      this.civReportRight = civReportRight;
      this.iTurnID = iTurnID;
   }

   public static class CivReport {
      public int iCivID;
      public int iSoldiers;
      public int iCasualties;
      public int iRetreated;

      public CivReport(int iCivID, int iSoldiers, int iCasualties, int iRetreated) {
         this.iCivID = iCivID;
         this.iSoldiers = iSoldiers;
         this.iCasualties = iCasualties;
         this.iRetreated = iRetreated;
      }
   }
}
