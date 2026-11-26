package aoc.kingdoms.lukasz.map.civilization.stats;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Json.Serializer;

public class CivilizationEventsDataSerializer implements Serializer<CivilizationEventsData> {
   public void write(Json json, CivilizationEventsData object, Class knownType) {
      json.writeObjectStart();
      if (object.e != 0) {
         json.writeValue("e", object.e);
      }

      if (object.g != 0) {
         json.writeValue("g", object.g);
      }

      if (object.t != 0) {
         json.writeValue("t", object.t);
      }

      if (object.m != 0) {
         json.writeValue("m", object.m);
      }

      if (object.d != 0) {
         json.writeValue("d", object.d);
      }

      json.writeObjectEnd();
   }

   public CivilizationEventsData read(Json json, JsonValue jsonData, Class type) {
      CivilizationEventsData data = new CivilizationEventsData();
      data.e = jsonData.getInt("e", 0);
      data.g = jsonData.getInt("g", 0);
      data.t = jsonData.getInt("t", 0);
      data.m = jsonData.getInt("m", 0);
      data.d = jsonData.getInt("d", 0);
      return data;
   }
}
