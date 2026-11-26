package aoc.kingdoms.lukasz.events.triggers;

import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.jakowski.Game_Calendar;
import aoc.kingdoms.lukasz.textures.Images;

public class EventTrigger_ExactDay extends EventTrigger_Value {
   public int day;
   public int month;
   public int year;

   public EventTrigger_ExactDay(int day, int month, int year) {
      this.day = day;
      this.month = month;
      this.year = year;
   }

   @Override
   public boolean outCondition(int iCivID, int iProvinceID) {
      return Game_Calendar.currentYear > this.year
         || Game_Calendar.currentYear >= this.year && Game_Calendar.currentMonth > this.month
         || Game_Calendar.currentYear >= this.year && Game_Calendar.currentMonth >= this.month && Game_Calendar.currentDay >= this.day;
   }

   @Override
   public String getText() {
      return Game.lang.get("Date") + ": ";
   }

   @Override
   public String getText2() {
      return "" + this.day + " " + Game_Calendar.getMonthName(this.month) + " " + this.year;
   }

   @Override
   public String getText3() {
      return "";
   }

   @Override
   public int getImage() {
      return Images.time;
   }
}
