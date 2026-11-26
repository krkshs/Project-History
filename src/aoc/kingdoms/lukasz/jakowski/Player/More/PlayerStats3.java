package aoc.kingdoms.lukasz.jakowski.Player.More;

import aoc.kingdoms.lukasz.jakowski.Game;
import aoc.kingdoms.lukasz.map.civilization.CivilizationRanking;
import java.util.ArrayList;
import java.util.List;

public class PlayerStats3 {
   public List<Integer> prestige = new ArrayList<>();
   public List<Integer> population = new ArrayList<>();

   public final void updateData() {
      this.addPrestige();
      this.addPopulation();
   }

   public void addPopulation() {
      this.population.add((int)Math.ceil(Game.getCiv(Game.player.iCivID).getPopulationTotal()));
   }

   public void addPrestige() {
      this.prestige.add((int)Math.ceil(CivilizationRanking.getCivilizationRanking_INFO(Game.player.iCivID)));
   }
}
