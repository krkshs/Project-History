package aoc.kingdoms.lukasz.map.province.data;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Json.Serializer;

public class ProvinceData8Serializer implements Serializer<ProvinceData8> {
   public void write(Json json, ProvinceData8 data, Class knownType) {
      json.writeObjectStart();
      if (data.w) {
         json.writeValue("w", data.w);
      }

      if (data.r != 0.0F) {
         json.writeValue("r", data.r);
      }

      json.writeObjectEnd();
   }

   public ProvinceData8 read(Json json, JsonValue jsonData, Class type) {
      return new ProvinceData8();
   }
}
