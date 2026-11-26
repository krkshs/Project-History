package aoc.kingdoms.lukasz.map.province.data;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Json.Serializer;

public class ProvinceData4Serializer implements Serializer<ProvinceData4> {
   public void write(Json json, ProvinceData4 data, Class knownType) {
      json.writeObjectStart();
      if (data.u) {
         json.writeValue("u", data.u);
      }

      if (data.s != 0.0F) {
         json.writeValue("s", data.s);
      }

      json.writeObjectEnd();
   }

   public ProvinceData4 read(Json json, JsonValue jsonData, Class type) {
      return new ProvinceData4();
   }
}
