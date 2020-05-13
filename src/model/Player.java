package model;

import java.awt.Color;
import java.util.ArrayList;

class Player {
	private String name;
	private Color color;
	private IObjective objective;
	private ArrayList<TerritoryCard> cards = new ArrayList<TerritoryCard>();
	private int territoryCount;
	private int soldierCount;
 	
	public int getTerritoryCount() {
		return territoryCount;
	}

	public void addTerritory(Territory t) {
		if (t.getOwner() == this) {
			return;
		}
		
		territoryCount++;
		t.setOwner(this);
	}
	
	public int getSoldierCount() {
		return soldierCount;
	}
	
	public void addSoldiers(int v) {
		soldierCount += v;
	}
	
	public ArrayList<TerritoryCard> getCardList(){
		return cards;
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
	
	public String getName() {
		return name;
	}
	
	public IObjective getObjective() {
		return objective;
	}
	
	public void setObjective(IObjective objective) {
		this.objective = objective;
	}
	
	public Color getColor() {
		return color;
	}
	
	public Player(String name, Color color) {
		this.name = name;
		this.color = color;
	}
}
