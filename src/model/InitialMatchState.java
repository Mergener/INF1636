package model;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

public class InitialMatchState extends MatchState {

	private void giveRandomObjectivesToPlayers(List<IObjective> availableObjectives) {
		ArrayList<IObjective> objectives = new ArrayList<IObjective>(availableObjectives);
		Random random = new Random();
		List<Player> players = getMatch().getPlayers();
		
		for (int i = 0; i < players.size(); ++i) {
			int rand = random.nextInt(objectives.size());
			
			players.get(i).setObjective(objectives.get(rand));
			objectives.remove(rand);
		}
	}

	private void giveRandomTerritoriesToPlayers(World world) {
		List<Player> players = getMatch().getPlayers();
		
		// The following hashtables links players to the number of territories they can still receive during
		// this phase of territory distribution.
		Hashtable<Player, Integer> playersTerritoryCount = new Hashtable<Player, Integer>();
				
		// The following array list contains all players who are still receiving territories during this phase of
		// territory distribution.
		ArrayList<Player> sortingPlayers = new ArrayList<Player>(players);
		Random random = new Random();
		
		int territoriesPerPlayer = world.getTerritoryCount() / players.size();
		int remainder = world.getTerritoryCount() % players.size();
		
		// The number of territories in the world might not be a multiple of the number of players in the match. 
		// Thus, considering the fact that all territories must have an owner, the remainder 'r' of the division is
		// distributed among the first 'r' players. This might not be the fairmost solution, but it is chosen since
		// this is the approach taken by the official War game.
		for (int i = 0; i < players.size(); ++i) {
			int amt = territoriesPerPlayer;
			
			if (remainder > 0) {
				amt++;
				remainder--;
			}
			
			playersTerritoryCount.put(players.get(i), amt);
		}
		
		// For every territory in the world, set its owner to a random player that is still
		// eligible for receiving more territories.
		for (Continent c : world.getContinents()) {
			for (Territory t : c.getTerritories()) {
				int rand = random.nextInt(sortingPlayers.size());
				
				Player p = sortingPlayers.get(rand);
				int val = playersTerritoryCount.get(p);
				
				if (val == 1) {
					// If val == 1, this is the last territory this player is getting.
					sortingPlayers.remove(rand);
				} else {
					playersTerritoryCount.replace(p, val - 1);
				}
				
				p.addTerritory(t);
			}
		}
	}
	
	@Override
	protected void onBegin() {
		ArrayList<IObjective> availableObjectives = new ArrayList<IObjective>();
		World world = World.getInstance();

		availableObjectives.add(new DominateTerritoriesObjective(new Territory[] {
			world.findTerritory("Brazil"),
			world.findTerritory("Australia"),
			world.findTerritory("Sumatra")
		}));

		availableObjectives.add(new DominateTerritoriesObjective(new Territory[] {
			world.findTerritory("Colombia"),
			world.findTerritory("Chile"),
			world.findTerritory("Borneo")
		}));
		
		availableObjectives.add(new DominateTerritoriesObjective(new Territory[] {
				world.findTerritory("Argentina"),
				world.findTerritory("New Guinea"),
				world.findTerritory("Sumatra")
		}));
		
		giveRandomObjectivesToPlayers(availableObjectives);
		giveRandomTerritoriesToPlayers(World.getInstance());
		getMatch().setState(new MidGameState(getMatch()));
	}
	
	public InitialMatchState(Match match) {
		super(match);
	}

}
