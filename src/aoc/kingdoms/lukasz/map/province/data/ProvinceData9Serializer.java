package aoc.kingdoms.lukasz.map.province.data;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Json.Serializer;

public class ProvinceData9Serializer implements Serializer<ProvinceData9> {
   public void write(Json json, ProvinceData9 data, Class knownType) {
      json.writeObjectStart();
      if (data.s != -1) {
         json.writeValue("s", data.s);
      }

      if (data.e != 0) {
         json.writeValue("e", data.e);
      }

      json.writeObjectEnd();
   }

   public ProvinceData9 read(Json json, JsonValue jsonData, Class type) {
      return new ProvinceData9();
   }
}
