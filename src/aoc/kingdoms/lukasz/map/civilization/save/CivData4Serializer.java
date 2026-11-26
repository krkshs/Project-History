package aoc.kingdoms.lukasz.map.civilization.save;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Json.Serializer;

public class CivData4Serializer implements Serializer<CivData4> {
   public void write(Json json, CivData4 data, Class knownType) {
      json.writeObjectStart();
      if (data.c != 0) {
         json.writeValue("c", data.c);
      }

      if (data.g != 0) {
         json.writeValue("g", data.g);
      }

      if (data.m != 0) {
         json.writeValue("m", data.m);
      }

      if (data.s != 0) {
         json.writeValue("s", data.s);
      }

      if (data.n != 0) {
         json.writeValue("n", data.n);
      }

      if (data.u != 0) {
         json.writeValue("u", data.u);
      }

      if (data.b != 0) {
         json.writeValue("b", data.b);
      }

      if (data.t != 1) {
         json.writeValue("t", data.t);
      }

      if (data.r != 1) {
         json.writeValue("r", data.r);
      }

      if (data.y != 1) {
         json.writeValue("y", data.y);
      }

      if (data.e != -1) {
         json.writeValue("e", data.e);
      }

      if (data.d != 0) {
         json.writeValue("d", data.d);
      }

      if (data.v != 0) {
         json.writeValue("v", data.v);
      }

      json.writeObjectEnd();
   }

   public CivData4 read(Json json, JsonValue jsonData, Class type) {
      return new CivData4();
   }
}
