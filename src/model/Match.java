package model;

import java.util.Collections;
import java.util.List;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Random;

class Match {
	
	/* @brief Array that contains all players in the match. 
	 * 
	 * @remarks The order of players within this array matches the order
	 * that each player will play.
	 */
	private ArrayList<Player> players = new ArrayList<Player>();
	
	private World world;
	
	public List<Player> getPlayers() {
		return Collections.unmodifiableList(players);
	}

	/*
	 * @brief Randomly sorts players array.
	 */
	private void randomizePlayersArray() {
		Random random = new Random();
		
		for (int i = 0; i < players.size(); ++i) {
			Player p = players.get(i);
			int rand = random.nextInt(players.size());
			players.set(i, players.get(rand));
			players.set(rand, p);
		}
	}
	
	private Player currentPlayer;
	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	public Match(List<Player> players) throws Exception {
		if (players.size() < 3 || players.size() > 6) {
			throw new Exception("Invalid number of players. Must have between 3 and 6 players, inclusive.");
		}
		
		for (Player p : players) {
			this.players.add(p);
		}
		randomizePlayersArray();
	}
	
	private void giveRandomObjectivesToPlayers(List<IObjective> availableObjectives) {
		ArrayList<IObjective> objectives = new ArrayList<IObjective>(availableObjectives);
		Random random = new Random();
		List<Player> players = getPlayers();
		
		for (int i = 0; i < players.size(); ++i) {
			int rand = random.nextInt(objectives.size());
			
			players.get(i).setObjective(objectives.get(rand));
			objectives.remove(rand);
		}
	}

	private void giveRandomTerritoriesToPlayers(World world) {
		List<Player> players = getPlayers();
		
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
	
	public void start() {
		world = World.generateDefaultWorld();
		
		giveRandomObjectivesToPlayers(DefaultObjectives.getAllDefaultObjectives(world));
		
		for (int i = 0; i < matchStartListeners.size(); ++i) {
			matchStartListeners.get(i).onMatchStart(this);
		}
	}
	
	private static ArrayList<MatchStartListener> matchStartListeners = new ArrayList<MatchStartListener>();
	
	public static void addMatchStartListener(MatchStartListener listener) {
		matchStartListeners.add(listener);
	}
	
	public static void removeMatchStartListener(MatchStartListener listener) {
		matchStartListeners.remove(listener);
	}
}
