package aoc.kingdoms.lukasz.map.province.data;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Json.Serializer;

public class ProvinceData6Serializer implements Serializer<ProvinceData6> {
   public void write(Json json, ProvinceData6 data, Class knownType) {
      json.writeObjectStart();
      json.writeValue("e", data.e);
      if (data.n != 0) {
         json.writeValue("n", data.n);
      }

      json.writeObjectEnd();
   }

   public ProvinceData6 read(Json json, JsonValue jsonData, Class type) {
      return new ProvinceData6();
   }
}
