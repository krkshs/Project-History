package aoc.kingdoms.lukasz.map.province.data;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Json.Serializer;

public class ProvinceData2Serializer implements Serializer<ProvinceData2> {
   public void write(Json json, ProvinceData2 data, Class knownType) {
      json.writeObjectStart();
      if (data.d != 0.0F) {
         json.writeValue("d", data.d);
      }

      if (data.l != 1.0F) {
         json.writeValue("l", data.l);
      }

      json.writeObjectEnd();
   }

   public ProvinceData2 read(Json json, JsonValue jsonData, Class type) {
      return new ProvinceData2();
   }
}
