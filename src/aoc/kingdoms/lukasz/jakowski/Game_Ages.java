package aoc.kingdoms.lukasz.jakowski;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;
import java.util.ArrayList;
import java.util.List;

public class Game_Ages {
   public List<Game_Ages.Data_Ages> lAges = new ArrayList<>();
   public int iAgesSize;
   public String sBC;

   public final void loadAges() {
      try {
         FileHandle fileList = FileManager.loadFile("game/Ages.json");
         String fileContent = fileList.readString();
         Json json = new Json();
         json.setElementType(Game_Ages.ConfigAgesData.class, "Age", Game_Ages.Data_Ages.class);
         new Game_Ages.ConfigAgesData();
         Game_Ages.ConfigAgesData data = json.fromJson(Game_Ages.ConfigAgesData.class, fileContent);

         for (Object e : data.Age) {
            this.lAges.add((Game_Ages.Data_Ages)e);
         }

         data = null;
      } catch (GdxRuntimeException var7) {
         CFG.exceptionStack(var7);
      }

      this.sBC = Game.lang.get("BeforeChrist");
      this.iAgesSize = this.lAges.size();

      for (int i = 0; i < this.iAgesSize; i++) {
         this.lAges.get(i).Name = Game.lang.get(this.lAges.get(i).Name);
      }
   }

   protected final void updateLanguage() {
      this.loadAges();
   }

   public final String getYear(int nYear) {
      return nYear < 0 ? "" + -nYear + " " + this.getBC() : "" + nYear;
   }

   public final int getAgeOfYear(int nYear) {
      for (int i = 0; i < this.lAges.size() - 1; i++) {
         if (this.lAges.get(i).AGE_BeginningYear <= nYear && this.lAges.get(i).AGE_EndYear >= nYear) {
            return i;
         }
      }

      return this.lAges.size() - 1;
   }

   public final int getAge_TurnDays(int nAgeID) {
      return (int)(this.lAges.get(nAgeID).GAME_DAYS_PER_TURN * Game_Calendar.GAME_SPEED);
   }

   public final float getAge_DiseaseChance(int nAgeID) {
      return this.lAges.get(nAgeID).DISEASE_CHANCE;
   }

   protected final String getBC() {
      return this.sBC;
   }

   protected final int getAgesSize() {
      return this.iAgesSize;
   }

   public static String getVassal() {
      return Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).VASSALS ? "Vassal" : "Puppet";
   }

   public static String getVassals() {
      return Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).VASSALS ? "Vassals" : "PuppetStates";
   }

   public static String getManageVassals() {
      return Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).VASSALS ? "ManageVassals" : "ManageSubjects";
   }

   public static String getLord() {
      return Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).VASSALS ? "Lord" : "ControllingCivilization";
   }

   public static String getPlayAsAReleasedVassal() {
      return Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).VASSALS ? "PlayAsAReleasedVassal" : "PlayAsReleasedCivilization";
   }

   public static String getLiberateAVassal() {
      return Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).VASSALS ? "LiberateAVassal" : "LiberateCivilization";
   }

   public static String getReleaseAVassal() {
      return Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).VASSALS ? "ReleaseAVassal" : "ReleasePuppetState";
   }

   public static String getDemandVassalization() {
      return Game.gameAges.lAges.get(Game_Calendar.CURRENT_AGEID).VASSALS ? "DemandVassalization" : "DemandPuppetState";
   }

   public static class ConfigAgesData {
      public String Age_of_History;
      public ArrayList Age;
   }

   public static class Data_Ages {
      public String Name;
      public int AGE_BeginningYear;
      public int AGE_EndYear;
      public boolean ENABLE_NON_KINGS_IMG;
      public boolean FORCE_NON_KINGS_IMG;
      public int REGIMENT_SIZE;
      public int REGIMENTS_LIMIT_BASE_VALUE;
      public int POPULATION_GROWTH_PER_MONTH;
      public int[] FLAG_GROUP;
      public boolean FLAG_BELOW;
      public int OVER_IMAGE_ID;
      public int SHIP_GROUP;
      public int GAME_DAYS_PER_TURN;
      public boolean BATTLE_SOUND_2;
      public float DISEASE_CHANCE;
      public float RIVALS_DISTANCE;
      public boolean NEW_GAME_ADVANTAGES;
      public int CHARACTERS_EXTRA_LIFE_EXPECTANCY;
      public boolean VASSALS;
      public boolean UQ_UI;
      public int AI_CONQUER_OWN_VASSALS_IF_OVER;
   }
}
