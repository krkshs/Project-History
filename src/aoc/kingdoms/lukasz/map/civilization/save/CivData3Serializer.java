package aoc.kingdoms.lukasz.map.civilization.save;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Json.Serializer;

public class CivData3Serializer implements Serializer<CivData3> {
   public void write(Json json, CivData3 data, Class knownType) {
      json.writeObjectStart();
      if (data.a != 0) {
         json.writeValue("a", data.a);
      }

      if (data.w != 0.0F) {
         json.writeValue("w", data.w);
      }

      if (data.e != 0.0F) {
         json.writeValue("e", data.e);
      }

      if (data.t != 0.0F) {
         json.writeValue("t", data.t);
      }

      if (data.c != 0.0F) {
         json.writeValue("c", data.c);
      }

      json.writeObjectEnd();
   }

   public CivData3 read(Json json, JsonValue jsonData, Class type) {
      return new CivData3();
   }
}
