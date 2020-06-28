package model;

import java.util.List;

import shared.PlayerColor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.awt.Color;
import java.util.ArrayList;

class Player {
	private String name;
	public String getName() {
		return name;
	}
	
	private PlayerColor color;
	public PlayerColor getColor() {
		return color;
	}
	
	private IObjective objective;
	public IObjective getObjective() {
		return objective;
	}
	public void setObjective(IObjective o) {
		objective = o;
	}
	
	private ArrayList<TerritoryCard> territoryCards = new ArrayList<TerritoryCard>();
	public List<TerritoryCard> getCardList() {
		return Collections.unmodifiableList(territoryCards);
	}
	
	private int territoryCount;
	public int getTerritoryCount() {
		return territoryCount;
	}
	
	private int soldierCount;	
	public int getSoldierCount() {
		return soldierCount;
	}
	
	private Hashtable<Continent, Integer> unspentContinentalSoldiers = new Hashtable<Continent, Integer>();
	private HashSet<Territory> ownedTerritories = new HashSet<Territory>();
 	
	/**
	 * Adds a territory to the list of territories owned by this player. Also changes
	 * the owner of the territory to this player.
	 * 
	 * @param t The territory being added.
	 */
	public void addTerritory(Territory t) {
		if (t == null) {
			return;
		}
		
		if (ownedTerritories.add(t)) {		
			t.setOwner(this);
		}
	}
	
	public boolean ownsTerritory(Territory t) {
		return ownedTerritories.contains(t);
	}
	
	public void transferTerritory(Territory t, Player p) throws NullPointerException {
		if (p == null) {
			return;
		}
		
		if (ownedTerritories.remove(t)) {
			p.addTerritory(t);
		}
	}
	
	/**
	 * Returns this player's number of unspent continental soldiers.
	 * 
	 * After every round, players are granted the right to add extra troops to territories
	 * under their domain. Some of those extra troops, however, must be placed at specific 
	 * continents. This function returns the number of troops this player still has the right to
	 * place at a specified continent.
	 * 
	 * @param c The continent to be evaluated.
	 */
	public int getUnspentContinentalSoldierCount(Continent c) {
		return unspentContinentalSoldiers.get(c);
	}

	/**
	 * Adds more unspent continental soldiers for this player in a specific continent.
	 * 
	 * View 'getUnspentContinentalSoldierCount' for more information about unspent continental soldiers.
	 * 
	 * @param c The continent to be evaluated.
	 * @param amount The number of unspent continental soldiers to be added.
	 */
	public void addContinentalSoldiers(Continent c, int amount) {
		unspentContinentalSoldiers.putIfAbsent(c, 0);
		
		Integer i = unspentContinentalSoldiers.get(c);
		i += amount;
	}
	
	/**
	 * Spends continental soldiers in a specific territory.
	 * 
	 * It is only possible to spend continental soldiers in a territory if the player has enough
	 * unspent continental soldiers in the continent the specified territory is located.
	 * 
	 * View 'getUnspentContinentalSoldierCount' for more information about unspent continental soldiers.
	 * 
	 * @param t The territory to place the soldiers.
	 * @param amount The number of unspent continental soldiers to be spent.
	 */
	public void spendContinentalSoldiers(Territory t, int amount) throws Exception {
		if (t.getOwner() != this) {
			throw new Exception("Tried spending continental soldiers in territory not owned by player.");
		}
		Integer continentalSoldierCount = unspentContinentalSoldiers.get(t.getContinent());
		if (continentalSoldierCount == null || continentalSoldierCount < amount) {
			throw new Exception("Tried spending too many continental soldiers in territory.");
		}
		
		continentalSoldierCount -= amount;
		t.addSoldiers(amount);
	}
	
	public void addSoldiers(int v) {
		soldierCount += v;
	}
	
	/**
	 * Returns true if this player is currently dominating an entire continent.
	 * 
	 * @param c The specified continent.
	 */
	public boolean hasEntireContinent(Continent c) {
		for (Territory t : c.getTerritories()) {
			if(t.getOwner() != this) {
				return false;
			}
		}
		return true;
	}
	
	public Player(String name, PlayerColor color) {
		this.name = name;
		this.color = color;
	}
}
