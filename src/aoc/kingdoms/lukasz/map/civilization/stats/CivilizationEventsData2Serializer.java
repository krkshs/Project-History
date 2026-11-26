package aoc.kingdoms.lukasz.map.civilization.stats;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Json.Serializer;

public class CivilizationEventsData2Serializer implements Serializer<CivilizationEventsData2> {
   public void write(Json json, CivilizationEventsData2 object, Class knownType) {
      json.writeObjectStart();
      if (object.b != 0) {
         json.writeValue("b", object.b);
      }

      if (object.a != 0) {
         json.writeValue("a", object.a);
      }

      if (object.e != 0) {
         json.writeValue("e", object.e);
      }

      if (object.m != 0) {
         json.writeValue("m", object.m);
      }

      if (object.c != 0) {
         json.writeValue("c", object.c);
      }

      json.writeObjectEnd();
   }

   public CivilizationEventsData2 read(Json json, JsonValue jsonData, Class type) {
      CivilizationEventsData2 data = new CivilizationEventsData2();
      data.b = jsonData.getInt("b", 0);
      data.a = jsonData.getInt("a", 0);
      data.e = jsonData.getInt("e", 0);
      data.m = jsonData.getInt("m", 0);
      data.c = jsonData.getInt("c", 0);
      return data;
   }
}
