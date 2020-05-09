package model;

import java.awt.Color;

class Player {
	private String name;
	private Color color;
	private IObjective objective;
	private int territoryCount;
	
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
	
	public void removeTerritory(Territory t) {
		
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
