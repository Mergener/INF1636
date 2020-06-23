package model;

import java.util.ArrayList;

class Territory {
	
	private String name;
	private Player owner = null;
	private Continent continent;
	
	private float centerX;
	private float centerY;
	
	private int soldierCount = 0;
	
	private ArrayList<Territory> neighbours = new ArrayList<Territory>();
	
	public float getCenterX() {
		return centerX;
	}
	
	public float getCenterY() {
		return centerY;
	}
	
	public String getName() {
		return name;
	}
	
	public Continent getContinent(){
		return continent;
	}
	
	public void setContinent(Continent c) {
		continent = c;
	}
	public Player getOwner() {
		return owner;
	}
	
	public void setOwner(Player newOwner) {
		if (newOwner != null && newOwner != owner) {
			owner = newOwner;
			
			if (owner != null) {
				owner.transferTerritory(this, newOwner);
			} else {
				newOwner.addTerritory(this);
			}			
		}
	}
	
	public int getSoldierCount() {
		return soldierCount;
	}
	
	public void addSoldiers(int count) {
		soldierCount += count;
	}
	
	public void removeSoldiers(int count) {
		soldierCount = Math.max(0, soldierCount - count);
	}
	
	void addNeighbour(Territory t) {
		if (neighbours.contains(t)) {
			return;
		}
		
		this.neighbours.add(t);
		t.neighbours.add(this);
	}
	
	public boolean isNeighbourOf(Territory t) {
		for (int i = 0; i < neighbours.size(); ++i) {
			if (neighbours.get(i) == t) {
				return true;
			}
		}
		return false;
	}
	
	public Territory(String name, float centerX, float centerY) {
		this.name = name;
		this.centerX = centerX;
		this.centerY = centerY;
	}
}
