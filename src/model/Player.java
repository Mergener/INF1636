package model;

import java.util.List;

import exceptions.InvalidContinentalSoldierExpenditure;
import exceptions.InvalidGlobalSoldierExpenditure;
import shared.PlayerColor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.io.Serializable;
import java.util.ArrayList;

class Player implements Serializable {
	
	private static final long serialVersionUID = -7195413765397743295L;
	
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
	public List<TerritoryCard> getTerritoryCardList() {
		return Collections.unmodifiableList(territoryCards);
	}
	
	public void addTerritoryCard(TerritoryCard card) {
		territoryCards.add(card);
	}
	
	public int getTerritoryCount() {
		return ownedTerritories.size();
	}
	
	public void removeTerritory(Territory t) {
		ownedTerritories.remove(t);
	}
		
	public int getSoldierCount() {
		int ret = unspentGlobalSoldierCount;
		
		for (Integer i : unspentContinentalSoldiers.values()) {
			ret += i;
		}
		
		for (Territory t : ownedTerritories) {
			ret += t.getSoldierCount();
		}
		
		return ret;
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
	
	private int unspentGlobalSoldierCount = 0;
	
	public int getUnspentGlobalSoldierCount() {
		return unspentGlobalSoldierCount;
	}
	
	public void spendGlobalSoldiers(Territory t, int amount) throws InvalidGlobalSoldierExpenditure {
		if (!ownsTerritory(t)) {
			throw new InvalidGlobalSoldierExpenditure("Não é possível posicionar territórios globais em territórios não possuídos pelo jogador.");
		}
		if (unspentGlobalSoldierCount < amount) {
			throw new InvalidGlobalSoldierExpenditure("O jogador não possui soldados globais suficientes para posicionar no território.");
		}
		
		unspentGlobalSoldierCount -= amount;		
		t.addSoldiers(amount);
	}
	
	public void addGlobalSoldiers(int amount) {
		unspentGlobalSoldierCount += amount;
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
		unspentContinentalSoldiers.put(c, i + amount);
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
	public void spendContinentalSoldiers(Territory t, int amount) throws InvalidContinentalSoldierExpenditure {
		if (t.getOwner() != this) {
			throw new InvalidContinentalSoldierExpenditure("Tentou posicionar tropas continentais bônus em um território que o jogador não possui.");
		}
		Integer continentalSoldierCount = unspentContinentalSoldiers.get(t.getContinent());
		if (continentalSoldierCount == null || continentalSoldierCount < amount) {
			throw new InvalidContinentalSoldierExpenditure("Tentou posicionar tropas continentais bônus demais.");
		}
		
		unspentContinentalSoldiers.put(t.getContinent(), continentalSoldierCount - amount);
		t.addSoldiers(amount);
	}
	
	/**
	 * Returns true if this player is currently dominating an entire continent.
	 * 
	 * @param c The specified continent.
	 */
	public boolean hasEntireContinent(Continent c) {
		for (Territory t : c.getTerritories()) {
			if (t.getOwner() != this) {
				return false;
			}
		}
		return true;
	}
	
	public Player(World world, String name, PlayerColor color) {
		this.name = name;
		this.color = color;
		
		Continent[] continents = world.getContinents();
		
		for (int i = 0; i < continents.length; ++i) {
			unspentContinentalSoldiers.put(continents[i], 0);
		}
	}
	public void removeTerritoryCard(TerritoryCard card) {
		territoryCards.remove(card);
	}
}
