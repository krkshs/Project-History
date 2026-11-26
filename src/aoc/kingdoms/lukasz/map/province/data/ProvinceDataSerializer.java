package aoc.kingdoms.lukasz.map.province.data;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Json.Serializer;

public class ProvinceDataSerializer implements Serializer<ProvinceData> {
   public void write(Json json, ProvinceData data, Class knownType) {
      json.writeObjectStart();
      if (data.c != 0) {
         json.writeValue("c", data.c);
      }

      if (data.o != 0) {
         json.writeValue("o", data.o);
      }

      if (data.w != -1) {
         json.writeValue("w", data.w);
      }

      json.writeObjectEnd();
   }

   public ProvinceData read(Json json, JsonValue jsonData, Class type) {
      return new ProvinceData();
   }
}
