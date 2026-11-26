package aoc.kingdoms.lukasz.map.civilization.stats;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Json.Serializer;

public class CivilizationEventsData3Serializer implements Serializer<CivilizationEventsData3> {
   public void write(Json json, CivilizationEventsData3 object, Class knownType) {
      json.writeObjectStart();
      if (object.a != 0) {
         json.writeValue("a", object.a);
      }

      if (object.p != 0) {
         json.writeValue("p", object.p);
      }

      json.writeObjectEnd();
   }

   public CivilizationEventsData3 read(Json json, JsonValue jsonData, Class type) {
      CivilizationEventsData3 data = new CivilizationEventsData3();
      data.a = jsonData.getInt("a", 0);
      data.p = jsonData.getInt("p", 0);
      return data;
   }
}
