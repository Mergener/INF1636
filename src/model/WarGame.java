package model;

import java.util.ArrayList;

import data.PlayerColor;
import exceptions.*;

public class WarGame {
	
	private ArrayList<Player> players = new ArrayList<Player>();

	private Match match;
	
	private boolean started = false;
	public boolean hasStarted() {
		return started;
	}
	
	private Player getPlayerByColor(PlayerColor color) throws PlayerNotFound {
		for (int i = 0; i < players.size(); ++i) {
			Player p = players.get(i);
			
			if (p.getColor() == color) {
				return p;
			}
		}
		throw new PlayerNotFound();
	}
	
	public PlayerColor[] getRegisteredPlayersColors() {
		PlayerColor[] ret = new PlayerColor[players.size()];
		
		for (int i = 0; i < players.size(); ++i) {
			ret[i] = players.get(i).getColor();
		}
		
		return ret;
	}
	
	public void registerPlayer(String playerName, PlayerColor color) throws BadPlayerColorUsage {
		// Check if the color is already being used
		for (int i = 0; i < players.size(); ++i) {
			Player p = players.get(i);
			
			if (p.getColor() == color) {
				throw new BadPlayerColorUsage();
			}
		}
	
		players.add(new Player(playerName, color));
	}
	
	public String getPlayerObjectiveDescription(PlayerColor color) throws PlayerNotFound {
		Player p = getPlayerByColor(color);
		
		return p.getObjective().getDescription();
	}
	
	public void start() {
		try {
			match = new Match(players);

			match.start();
			
		} catch (Exception ex) {			
		}
	}
	
	public WarGame() {		
	}
}