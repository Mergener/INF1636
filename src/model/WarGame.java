package model;

import java.util.List;
import java.util.ArrayList;
import exceptions.*;
import listeners.AttackListener;
import listeners.TerritoryListener;
import shared.AttackSummary;
import shared.Geometry;
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
	 * Unregister all registered players from this game.
	 * 
	 * Cannot be called if the game has already started.
	 * 
	 * @throws GameAlreadyStarted Thrown if this method is called when the game has already started.
	 */
	public void unregisterAllPlayers() throws GameAlreadyStarted {
		if (hasStarted()) {
			throw new GameAlreadyStarted();
		}
		
		players.clear();
	}
	
	/**
	 * Returns the name of the player associated with the specified color.
	 * 
	 * @param c The player's color.
	 * @return The player's name.
	 * @throws PlayerNotFound Thrown if there's no registered player with the specified color.
	 */
	public String getPlayerName(PlayerColor c) throws PlayerNotFound {
		return getPlayerByColor(c).getName();
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
		if (!hasStarted()) {
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
	 * @param attacker - The player issuing the attack move.
	 * @param sourceTerritoryName The territory where the attack is come from.
	 * @param targetTerritoryName The territory being aimed.
	 * @return A detailed summary of the attack outcome.
	 * @throws InvalidAttack If any of the attack conditions aren't met.
	 * @throws PlayerNotFound The specified player was not found.
	 */
	public AttackSummary performAttack(PlayerColor attacker, String sourceTerritoryName, String targetTerritoryName) throws InvalidAttack, PlayerNotFound {
		return match.performAttack(getPlayerByColor(attacker), world.findTerritory(sourceTerritoryName), world.findTerritory(targetTerritoryName));
	}
		
	/**
	 * Returns the name of the continent in which a territory belongs.
	 * 
	 * @param territoryName The territory's name.
	 * @return The continent's name.
	 */
	public String getTerritoryContinentName(String territoryName) {
		return world.findTerritory(territoryName).getContinent().getName();
	}
	
	/**
	 * Returns the unspent continental soldier count for a player at a specified continent.
	 * 
	 * Unspent continental soldiers are continental soldiers in which a player can add to their territories
	 * in a single specific continent. Continental soldiers are given to players whenever a round finishes and
	 * they have conquered an entire continent.
	 * 
	 * @param player The color of the player to get unspent continental soldiers count.
	 * @param continentName The name of the continent to be evaluated.
	 * @return The unspent continental soldiers count.
	 * @throws PlayerNotFound Thrown if the player was not found.
	 */
	public int getPlayerUnspentContinentalSoldierCount(PlayerColor player, String continentName) throws PlayerNotFound {
		return getPlayerByColor(player).getUnspentContinentalSoldierCount(world.findContinent(continentName));
	}
	
	/**
	 * Spends player's unspent continental soldiers in a territory.
	 * 
	 * @param player The player to spend soldiers.
	 * @param territoryName The territory in which the soldiers will be spent on.
	 * @param amount The amount of soldiers to be spent.
	 * @throws InvalidContinentalSoldierExpenditure Thrown if the player either does not own the specified territory or the amount of soldiers requested surpass the
	 * number of unspent continental soldiers the player has.
	 * @throws PlayerNotFound Thrown if the specified player color is not registered.
	 */
	public void spendPlayerContinentalSoldiers(PlayerColor player, String territoryName, int amount) throws InvalidContinentalSoldierExpenditure, PlayerNotFound {
		getPlayerByColor(player).spendContinentalSoldiers(world.findTerritory(territoryName), amount);
	}
	
	/**
	 * Returns the ids of all territory cards owned by a player.
	 * 
	 * @param pc The player to get the cards ids from.
	 * 
	 * @return The array of ids.
	 * @throws PlayerNotFound Thrown if the specified player color is not registered.
	 */
	public int[] getTerritoryCardsIdsOwnedByPlayer(PlayerColor pc) throws PlayerNotFound {
		Player p = getPlayerByColor(pc);
		
		List<TerritoryCard> cards = p.getTerritoryCardList();
		
		int[] ids = new int[cards.size()];
		
		for (int i = 0; i < ids.length; ++i) {
			ids[i] = cards.get(i).getId();
		}
		
		return ids;
	}
	
	public void performCardsTrade(PlayerColor player, int card1, int card2, int card3) throws PlayerNotFound {
		match.performCardsTrade(getPlayerByColor(player),match.getCardById(card1),match.getCardById(card2),match.getCardById(card3));
	}
	
	public String getTerritoryCardDescription(int cardId) {
		return match.getCardById(cardId).getDescription();
	}
	
	public String getTerritoryCardTerritory(int id) {
		return match.getCardById(id).getTerritory().getName();
	}
	
	public Geometry getTerritoryCardGeometryById(int id) {
		return match.getCardById(id).getGeometry();
	}
	
	public PlayerColor getTerritoryCardOwner(int id) {
		List<Player> players = match.getPlayers();
		
		for (int i = 0; i < players.size(); ++i) {
			List<TerritoryCard> cards = players.get(i).getTerritoryCardList();
			
			for (int j = 0; j < cards.size(); ++j) {
				if (cards.get(j).getId() == id) {
					return players.get(i).getColor();
				}
			}
		}
		
		return null;
	}
		
	/**
	 * Returns an array containing the name for each of the world's continents.
	 * 
	 * @return The array of continents names.
	 */
	public String[] getAllContinentsNames() {
		Continent[] continents = world.getContinents();
		String[] continentsNames = new String[continents.length];
		
		for (int i = 0; i < continents.length; ++i) {
			continentsNames[i] = continents[i].getName();
		}
		
		return continentsNames;
	}
	
	/**
	 * Performs end-of-round activities, like granting each player unspent continental soldiers.
	 */
	public void terminateRound() {
		match.terminateRound();
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
};