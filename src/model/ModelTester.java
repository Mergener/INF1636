package model;

import java.awt.Color;
import java.util.ArrayList;

public class ModelTester {

	public static void main(String[] args) throws Exception {
		// Generate players:
		ArrayList<Player> players = new ArrayList<Player>();
		
		players.add(new Player("Eduardo", Color.RED));
		players.add(new Player("Thomas", Color.GREEN));
		players.add(new Player("Ivan", Color.BLUE));
		
		Match match = new Match(players);
		
		match.start();
		
		System.out.println("World has been created and territories have been distributed evenly amongst players! The world:\n");
		World world = World.getInstance();
		for (Continent c : world.getContinents()) {
			System.out.printf("Continent '%s' - territories:\n", c.getName());
			for (Territory t : c.getTerritories()) {
				System.out.printf("\t%s", t.getName());
				if (t.getOwner() != null) {
					System.out.printf(" (owned by %s)", t.getOwner().getName());
				}
				System.out.println();
			}
		}
		
		System.out.println("Objectives given to players:");
		for (Player p : players) {
			System.out.printf("%s:\n\t%s\n", p.getName(), p.getObjective().getDescription());
		}
		
		System.out.println("Players will be playing in the following order:");
		for (int i = 0; i < match.getPlayers().size(); ++i) {
			System.out.printf("%d: %s\n", i+1, match.getPlayers().get(i).getName());
		}

		System.out.println("Players will be playing in the following order:");
		for (int i = 0; i < match.getPlayers().size(); ++i) {
			System.out.printf("%d: %s\n", i+1, match.getPlayers().get(i).getName());
		}
	}

}
