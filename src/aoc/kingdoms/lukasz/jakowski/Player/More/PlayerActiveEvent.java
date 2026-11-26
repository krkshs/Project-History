package aoc.kingdoms.lukasz.jakowski.Player.More;

public class PlayerActiveEvent {
   public int eventType;
   public int id;
   public int iTurnID;
   public int value1;
   public float value2;

   public PlayerActiveEvent() {
   }

   public PlayerActiveEvent(int eventType, int id, int iTurnID) {
      this.eventType = eventType;
      this.id = id;
      this.iTurnID = iTurnID;
   }

   public PlayerActiveEvent(int eventType, int id, int iTurnID, int value1, float value2) {
      this.eventType = eventType;
      this.id = id;
      this.iTurnID = iTurnID;
      this.value1 = value1;
      this.value2 = value2;
   }
}
