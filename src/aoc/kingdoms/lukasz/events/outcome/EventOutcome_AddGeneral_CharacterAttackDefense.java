package aoc.kingdoms.lukasz.events.outcome;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.CharactersManager;
import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.textures.Images;

public class EventOutcome_AddGeneral_CharacterAttackDefense extends EventOutcome {
   public String sGeneral;
   public int Attack;
   public int Defense;

   public EventOutcome_AddGeneral_CharacterAttackDefense(String sGeneral, int Attack, int Defense) {
      this.sGeneral = sGeneral;
      this.Attack = Attack;
      this.Defense = Defense;
   }

   @Override
   public void updateCiv(int iCivID, int bonus_duration) {
      try {
         CharactersManager.loadGeneral(iCivID, this.sGeneral, this.Attack, this.Defense);
      } catch (Exception var4) {
         CFG.exceptionStack(var4);
      }
   }

   @Override
   public String getStringLeft() {
      return Game.lang.get("RecruitGeneral") + ": ";
   }

   @Override
   public String getStringRight() {
      return "" + this.sGeneral;
   }

   @Override
   public int getImage() {
      return Images.general;
   }
}
