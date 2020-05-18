package model;

import java.util.List;
import java.util.Collections;
import java.util.Hashtable;
import java.awt.Color;
import java.util.ArrayList;

class Player {
	private String name;
	public String getName() {
		return name;
	}
	
	private Color color;
	public Color getColor() {
		return color;
	}
	
	private IObjective objective;
	public IObjective getObjective() {
		return objective;
	}
	
	private ArrayList<TerritoryCard> territoryCards = new ArrayList<TerritoryCard>();
	public List<TerritoryCard> getCardList(){
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
 	
	public void addTerritory(Territory t) {
		if (t.getOwner() == this) {
			return;
		}
		if (t.getOwner() != null) {
			t.getOwner().territoryCount--;
		}
		
		territoryCount++;
		t.setOwner(this);		
	}
	
	public int getUnspentContinentalSoldierCount(Continent c) {
		return unspentContinentalSoldiers.get(c);
	}

	public void addContinentalSoldiers(Continent c, int amount) {
		unspentContinentalSoldiers.putIfAbsent(c, 0);
		
		Integer i = unspentContinentalSoldiers.get(c);
		i += amount;
	}
	
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
	
	public boolean hasContinent(Continent c) {
		boolean ret = true;
		for (Territory t : c.getTerritories()) {
			if(t.getOwner() != this) {
				ret = false;
			}
		}
		return ret;
	}
	
	public void setObjective(IObjective objective) {
		this.objective = objective;
	}
	
	public Player(String name, Color color) {
		this.name = name;
		this.color = color;
	}
}
