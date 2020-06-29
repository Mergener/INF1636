package model;

import java.util.List;
import java.util.ArrayList;

import exceptions.*;
import listeners.AttackListener;
import listeners.TerritoryListener;
import shared.AttackSummary;
import shared.GameState;
import shared.PlayerColor;
import shared.Point;

/**
 * Contains full featured functionality for running a War game match.
 */
public class WarGame {
	
	private ArrayList<Player> players = new ArrayList<Player>();

	private World world;
	private Match match;
	
	/**
	 * @return True if the game has already started, false otherwise.
	 */
	public boolean hasStarted() {
		return match != null && match.hasStarted();
	}
	
	/**
	 * @return The current game state.
	 */
	public GameState getCurrentState() {
		return (match != null) ? match.getCurrentState() : GameState.Nothing;
	}

	/**
	 * Finds a player with the specified color.
	 * 
	 * @return The player or null if none is found.
	 */
	private Player getPlayerByColorNoExcept(PlayerColor color) {
		for (int i = 0; i < players.size(); ++i) {
			Player p = players.get(i);
			
			if (p.getColor().equals(color)) {
				return p;
			}
		}
		return null;
	}

	/**
	 * Finds a player with the specified color. 
	 * Throws PlayerNotFound if none is found.
	 *  
	 * @return The player.
	 */
	private Player getPlayerByColor(PlayerColor color) throws PlayerNotFound {
		Player p = getPlayerByColorNoExcept(color);
		
		if (p == null) {
			throw new PlayerNotFound();
		}

		return p;		
	}
	
	/**
	 * @return The current player's color or null if the match hasn't started yet.
	 */
	public PlayerColor getCurrentPlayerColor() {
		return match.getCurrentPlayer().getColor();
	}
	
	/**
	 * @return An array that contains the colors of all registered players.
	 */
	public PlayerColor[] getRegisteredPlayersColors() {
		PlayerColor[] ret = new PlayerColor[players.size()];
		
		for (int i = 0; i < players.size(); ++i) {
			ret[i] = players.get(i).getColor();
		}
		
		return ret;
	}
	
	/**
	 * Registers a player into the game. 
	 * Players must be registered before the game has already started.
	 * Note that colors serve as an unique identifier for each player within a match, and thus,
	 * there cannot be more than one player with the same color in a single game.
	 * At least 3 players must be registered before the game starts.
	 * 
	 * @param playerName The desired player name.
	 * @param color The desired player color.
	 * @throws BadPlayerColorUsage if the specified color is already in use.
	 * @throws GameAlreadyStarted if trying to register a player after the game has already been started.
	 */
	public void registerPlayer(String playerName, PlayerColor color) throws BadPlayerColorUsage, GameAlreadyStarted {
		if (hasStarted()) {
			throw new GameAlreadyStarted();
		}
		
		// Check if the color is already being used
		for (int i = 0; i < players.size(); ++i) {
			Player p = players.get(i);
			
			if (p.getColor() == color) {
				throw new BadPlayerColorUsage();
			}
		}
	
		players.add(new Player(playerName, color));
	}
	
	/**
	 * Returns a detailed description of a player's objective.
	 * Objectives are sorted randomly for each player once the game starts.
	 * 
	 * @param color The specified player's color.
	 * @return The description.
	 * @throws PlayerNotFound Thrown if there's no player with the specified color.
	 * @throws GameNotStarted Thrown if the game hasn't started yet.
	 */
	public String getPlayerObjectiveDescription(PlayerColor color) throws PlayerNotFound, GameNotStarted {
		if (hasStarted()) {
			throw new GameNotStarted();
		}
		
		Player p = getPlayerByColor(color);
		
		return p.getObjective().getDescription();
	}
	
	/**
	 * @return A list containing all territories' names in this game's world.
	 */
	public List<String> getAllTerritoryNames() {
		ArrayList<String> ret = new ArrayList<String>();
		
		for (Continent c : world.getContinents()) {
			for (Territory t : c.getTerritories()) {
				ret.add(t.getName());
			}
		}
		
		return ret;
	}
	
	/**
	 * Returns the current soldier count a specified territory.
	 * @param The name of the territory.
	 * @return The soldier count from the desired territory.
	 */
	public int getTerritorySoldierCount(String territoryName) {
		Territory t = world.findTerritory(territoryName);
		return t.getSoldierCount();
	}
	
	/**
	 * 
	 * @param The name of the territory.
	 * @return The territory owner's color.
	 */
	public PlayerColor getTerritoryOwnerColor(String territoryName) {
		Territory t = world.findTerritory(territoryName);
		return t.getOwner().getColor();
	}
	
	
	/**
	 * Returns the coordinates of the center of the specified territory.
	 * 
	 * Note that the returned each coordinate will be a value within the 0-1 range, being 0
	 * extreme-left for X-axis/extreme-top for Y-axis and 1 extreme-right for X-axis/extreme-bottom for
	 * Y axis.
	 * 
	 * @param territoryName The territory's name.
	 * @return The coordinates.
	 */
	public Point getTerritoryCenter(String territoryName) {
		return world.findTerritory(territoryName).getCenter();
	}
	
	/**
	 * Returns the coordinates of each vertex that composes the specified territory's
	 * shape.
	 * 
	 * Note that the returned each coordinate will be a value within the 0-1 range, being 0
	 * extreme-left for X-axis/extreme-top for Y-axis and 1 extreme-right for X-axis/extreme-bottom for
	 * Y axis.
	 * 
	 * @param territoryName The territory's name.
	 * @return The list of coordinates of each vertex.
	 */
	public Point[] getTerritoryVertices(String territoryName) {
		return world.findTerritory(territoryName).getVertices();
	}
	
	/**
	 * Adds a listener to a territory that will be notified of changes.
	 * 
	 * @param territoryName The territory to listen to.
	 * @param l The object that will listen to the changes.
	 */
	public void addTerritoryListener(String territoryName, TerritoryListener l) {
		world.findTerritory(territoryName).addListener(l);
	}
	
	/**
	 * Checks if two territories, A and B, are neighbours. 
	 * 
	 * @param tA Territory A.
	 * @param tB Territory B.
	 * @return True if tA is neighbour of tB (includes sea connected territories).
	 */
	public boolean isTerritoryNeighbourOf(String tA, String tB) {
		return world.findTerritory(tA).isNeighbourOf(world.findTerritory(tB));
	}
	
	/**
	 * Adds an attack listener. The attack listener will be notified when attacks
	 * occur.
	 * @param l The listener.
	 */
	public void addAttackListener(AttackListener l) {
		match.addAttackListener(l);
	}
	
	/**
	 * Performs an attack coming from a territory to another.
	 * 
	 * @param sourceTerritoryName The territory where the attack is come from.
	 * @param targetTerritoryName The territory being aimed.
	 * @return A detailed summary of the attack outcome.
	 * @throws InvalidAttack If any of the attack conditions aren't met.
	 */
	public AttackSummary performAttack(String sourceTerritoryName, String targetTerritoryName) throws InvalidAttack {
		return match.performAttack(world.findTerritory(sourceTerritoryName), world.findTerritory(targetTerritoryName));
	}
		
	/**
	 * Starts the game, performing all initial required setup - like sorting first territory cards and
	 * objectives for players.
	 * 
	 * Several methods from this become usable once this method is called, while many others also
	 * become unusable once the game has started.
	 * 
	 * Note that at least 3 players must be registered in order for a game to start.
	 */
	public void start() throws NotEnoughPlayers {
		match = new Match(players, world);

		match.start();
	}
		
	public String getWorldMapForegroundPath() {
		return world.getMapForegroundPath();
	}
	
	public String getWorldMapBackgroundPath() {
		return world.getMapBackgroundPath();
	}
	
	public WarGame() {	
		// The default constructor simply generates the default world and uses it.
		// In case custom worlds are implemented, there can be other constructors that may accept
		// paths to files that contain serialized worlds.
		world = World.generateDefaultWorld();
	}
}