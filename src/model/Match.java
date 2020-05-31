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
	
	public static Match getCurrentMatch() {
		return null;
	}
}
